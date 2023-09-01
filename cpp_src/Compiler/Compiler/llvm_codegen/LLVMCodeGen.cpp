#include "LLVMCodeGen.h"
#include "../bridge/token/TokenType.h"
#include <iostream>
using namespace std;
void LLVMCodeGenerator::generateProgramNode(ProgramNode* node) {
    for (ASTNode* statement : node->statements) {
        generateCode(statement);
    }
}

Value* LLVMCodeGenerator::generateExpression(ASTNode* expression) {
    if (auto* numberLiteralNode = dynamic_cast<NumberLiteralNode*>(expression)) {
        if (numberLiteralNode->isDecimal()) {
            return ConstantFP::get(context, APFloat(numberLiteralNode->doubleValue));
        } else {
            return ConstantInt::get(context, APInt(32, numberLiteralNode->intValue));
        }
    } else if (auto* stringLiteralNode = dynamic_cast<StringLiteralNode*>(expression)) {
        const std::string& stringValue = stringLiteralNode->value;
                Constant* stringConstant = ConstantDataArray::getString(context, stringValue, true);
                GlobalVariable* stringGlobal = new GlobalVariable(
                    module,
                    stringConstant->getType(),
                    true,
                    GlobalValue::PrivateLinkage,
                    stringConstant,
                    ".str"
                );
                Value* stringPtr = builder.CreatePointerCast(
                    stringGlobal,
                    Type::getInt8PtrTy(context)
                );
                return stringPtr;
    } else if (auto* identifierNode = dynamic_cast<IdentifierNode*>(expression)) {
        // Generate code for handling identifier expressions
        GlobalVariable* variable = module.getGlobalVariable(identifierNode->name);
        if (!variable) {
            // Handle error - identifier not found
            return nullptr;
        }
        return builder.CreateLoad(variable->getType(), variable);
    } else if (auto* binaryOperationNode = dynamic_cast<BinaryOperationNode*>(expression)) {
        // Generate code for handling binary operations
        Value* leftValue = generateExpression(binaryOperationNode->left);
        Value* rightValue = generateExpression(binaryOperationNode->right);
        if (!leftValue || !rightValue) {
            // Handle error
            return nullptr;
        }

        switch (binaryOperationNode->op) {
            case TokenType::ADD:
                return builder.CreateAdd(leftValue, rightValue);
            case TokenType::SUBTRACT:
                return builder.CreateSub(leftValue, rightValue);
            case TokenType::MULTIPLY:
                return builder.CreateMul(leftValue, rightValue);
            case TokenType::DIVIDE:
                return builder.CreateSDiv(leftValue, rightValue);  // Assuming integer division
            // Handle more operators as needed
            default:
                // Handle unsupported operator
                return nullptr;
        }
    } else if (auto* assignmentNode = dynamic_cast<AssignmentNode*>(expression)) {
        // Generate code for handling assignment expressions
        Value* expressionValue = generateExpression(assignmentNode->expression);
        if (!expressionValue) {
            // Handle error
            return nullptr;
        }
        GlobalVariable* variable = module.getGlobalVariable(assignmentNode->identifier);
        if (!variable) {
            // Handle error - identifier not found
            return nullptr;
        }
        builder.CreateStore(expressionValue, variable);
        return expressionValue;  // Return the assigned value
    }
    
    return nullptr;
}


void LLVMCodeGenerator::generateBinaryOperation(BinaryOperationNode* node) {
    Value* leftValue = generateExpression(node->left);
    Value* rightValue = generateExpression(node->right);
    if (!leftValue || !rightValue) {
        return;
    }

    Value* resultValue = nullptr;
    switch (node->op) {
        case TokenType::ADD:
            resultValue = builder.CreateFAdd(leftValue, rightValue);
            break;
        case TokenType::SUBTRACT:
            resultValue = builder.CreateFSub(leftValue, rightValue);
            break;
        case TokenType::MULTIPLY:
            resultValue = builder.CreateFMul(leftValue, rightValue);
            break;
        case TokenType::DIVIDE:
            resultValue = builder.CreateFDiv(leftValue, rightValue);
            break;
        default:
            break;
    }

//    if (resultValue) {
//        if (resultValue) {
//            Value* resultVariable = builder.CreateAlloca(resultValue->getType());
//            builder.CreateStore(resultValue, resultVariable);
//        }
//
//    }
}


void LLVMCodeGenerator::generateAssignmentNode(AssignmentNode* node) {
    Value* expressionValue = generateExpression(node->expression);
    
    GlobalVariable* variable = module.getNamedGlobal(node->identifier);
    if (!variable) {
        Type* valueType = expressionValue->getType();
        variable = new GlobalVariable(module, valueType, false, GlobalValue::ExternalLinkage, nullptr, node->identifier);
    }
    
    if (expressionValue->getType()->isPointerTy()) {
        // If expression value is a pointer, no need to store
        return;
    } else if (expressionValue->getType()->isIntegerTy()) {
        // If expression value is an integer, create and store integer
        Type* intType = expressionValue->getType();
        if (intType->isIntegerTy(32)) {
            builder.CreateStore(expressionValue, variable);
        } else {
            // Handle error - unsupported integer type
        }
    } else if (expressionValue->getType()->isFloatingPointTy()) {
        // If expression value is a float, create and store float
        builder.CreateStore(expressionValue, variable);
    }
    // Handle other types as needed
}






void LLVMCodeGenerator::generateShowStatement(ShowStatementNode* node) {
    Value* expressionValue = generateExpression(node->expression);
    
    if (expressionValue) {
        if (expressionValue->getType()->isIntegerTy()) {
            Function* printFunc = module.getFunction("printf");
            if (!printFunc) {
                Type* intType = IntegerType::getInt32Ty(context);
                Type* pointerType = PointerType::get(intType, 0);
                FunctionType* printfType = FunctionType::get(intType, pointerType, true);
                printFunc = Function::Create(printfType, GlobalValue::ExternalLinkage, "printf", &module);
            }
            
            Value* intFormatStr = builder.CreateGlobalStringPtr("%d\n");
            std::vector<Value*> intArgs = { intFormatStr, expressionValue };
            builder.CreateCall(printFunc, intArgs);
        } else if (expressionValue->getType()->isFloatingPointTy()) {
            Function* printFunc = module.getFunction("printf");
            if (!printFunc) {
                Type* floatType = Type::getFloatTy(context);
                Type* pointerType = PointerType::get(floatType, 0);
                FunctionType* printfType = FunctionType::get(Type::getInt32Ty(context), pointerType, true);
                printFunc = Function::Create(printfType, GlobalValue::ExternalLinkage, "printf", &module);
            }
            
            Value* floatFormatStr = builder.CreateGlobalStringPtr("%f\n");
            std::vector<Value*> floatArgs = { floatFormatStr, expressionValue };
            builder.CreateCall(printFunc, floatArgs);
        } else if (expressionValue->getType()->isPointerTy()) {
            Type* charType = Type::getInt8Ty(context);
            Type* pointerType = PointerType::get(charType, 0);
            Function* printFunc = module.getFunction("printf");
            if (!printFunc) {
                FunctionType* printfType = FunctionType::get(Type::getInt32Ty(context), pointerType, true);
                printFunc = Function::Create(printfType, GlobalValue::ExternalLinkage, "printf", &module);
            }
            
            // Create the format string for printing the pointer
            Constant* formatStrConstant = ConstantDataArray::getString(context, "%s\n");
            GlobalVariable* formatStrGlobal = new GlobalVariable(
                module, formatStrConstant->getType(), true,
                GlobalValue::PrivateLinkage, formatStrConstant, ".str"
            );
            Value* formatStrPtr = builder.CreatePointerCast(
                formatStrGlobal, Type::getInt8PtrTy(context)->getPointerTo()
            );
            
            std::vector<Value*> ptrArgs = { formatStrPtr, expressionValue };
            builder.CreateCall(printFunc, ptrArgs);
        }
    }
}

void LLVMCodeGenerator::generateCode(ASTNode* astNode) {
    if (auto* showStatementNode = dynamic_cast<ShowStatementNode*>(astNode)) {
        generateShowStatement(showStatementNode);
    } else if (auto* assignmentNode = dynamic_cast<AssignmentNode*>(astNode)) {
        cout << "generate assignment node" << endl;
        generateAssignmentNode(assignmentNode);
    } else if (auto* programNode = dynamic_cast<ProgramNode*>(astNode)) {
        generateProgramNode(programNode);
    }

    for (ASTNode* child : astNode->getChildren()) {
        generateCode(child);
    }
    
}

void LLVMCodeGenerator::printIR() {
    module.print(outs(), nullptr);
}

LLVMCodeGenerator::LLVMCodeGenerator() : module("main-module", context), builder(context) {
    module.setTargetTriple("arm64-apple-macos");
}

