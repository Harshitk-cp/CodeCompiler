#include "LLVMCodeGen.h"
#include "../bridge/token/TokenType.h"
#include <iostream>
using namespace std;

void LLVMCodeGenerator::compile(ASTNode* astNode){
    fn = createFunction("main", FunctionType::get(builder->getInt32Ty(), false));
    
    //generates llvm IR recursively
    generateCode(astNode);
    
    builder->CreateRet(builder->getInt32(0));
    
    saveModuleToFile("/Users/kagelol/_development/Compiler/Compiler/main.ll");
}

Value* LLVMCodeGenerator::generateCode(ASTNode* astNode) {
    
    if (auto* showStatementNode = dynamic_cast<ShowStatementNode*>(astNode)) {
        return generateShowStatement(showStatementNode);
    } else if (auto* assignmentNode = dynamic_cast<AssignmentNode*>(astNode)) {
        return generateAssignmentNode(assignmentNode);
    } else if (auto* programNode = dynamic_cast<ProgramNode*>(astNode)) {
        return generateProgramNode(programNode);
    }

    for (ASTNode* child : astNode->getChildren()) {
        generateCode(child);
    }
    
    return builder->getInt32(0);
    
}

Value* LLVMCodeGenerator::generateProgramNode(ProgramNode* node) {
    for (ASTNode* statement : node->statements) {
        generateCode(statement);
    }
    return nullptr;
}


Value* LLVMCodeGenerator::generateExpression(ASTNode* expression) {
    if (auto* numberLiteralNode = dynamic_cast<NumberLiteralNode*>(expression)) {
        
        if (numberLiteralNode->isDecimal()) {
            //return float value
            return ConstantFP::get(*ctx, APFloat(numberLiteralNode->doubleValue));
            
        } else if(numberLiteralNode->isInteger()) {
            //return int32
            return builder->getInt32(numberLiteralNode->intValue);
        }
    } else if (auto* stringLiteralNode = dynamic_cast<StringLiteralNode*>(expression)) {
        std::string stringValue = stringLiteralNode->value;
        stringValue = stringValue.substr(1, stringValue.length() - 2);
        return builder->CreateGlobalStringPtr(stringValue);
        
    } else if (auto* identifierNode = dynamic_cast<IdentifierNode*>(expression)) {
        GlobalVariable* variable = module->getGlobalVariable(identifierNode->name);
        if (!variable) {
            return nullptr;
        }
        Value* loadedValue = builder->CreateLoad(variable->getValueType(), variable);
        if (loadedValue->getType()->isFloatTy()) {
            return builder->CreateFPExt(loadedValue, builder->getFloatTy());
        } else if (loadedValue->getType()->isIntegerTy()) {
            return builder->CreatePtrToInt(loadedValue, builder->getInt32Ty());
        } else if (loadedValue->getType()->isPointerTy()) {
            return loadedValue;
        } else if (loadedValue->getType()->isDoubleTy()) {
            return loadedValue;
        }
        return nullptr;
        
    } else if (auto* binaryOperationNode = dynamic_cast<BinaryOperationNode*>(expression)) {
        Value* leftValue = generateExpression(binaryOperationNode->left);
        Value* rightValue = generateExpression(binaryOperationNode->right);
        
        if (!leftValue || !rightValue) {
            return nullptr;
          }
        
        switch (binaryOperationNode->op) {
            case TokenType::ADD:
                
                if (leftValue->getType()->isDoubleTy() && rightValue->getType()->isDoubleTy()) {
                    
                    return builder->CreateFAdd(leftValue, rightValue);
                    
                }
                
                if (leftValue->getType()->isIntegerTy() && rightValue->getType()->isIntegerTy()) {
                    
                    return builder->CreateAdd(leftValue, rightValue);
                    
                }
                
                if (leftValue->getType()->isPointerTy() && rightValue->getType()->isPointerTy()) {

                    Function* concatStringsFunc = createStringConcatenationFunction();
                    Value* concatenatedStr = builder->CreateCall(concatStringsFunc, {leftValue, rightValue});
                    return concatenatedStr;
                }
                cout << "Invalid Operands" << endl;
                return nullptr;
            case TokenType::SUBTRACT:
                if (leftValue->getType()->isDoubleTy() && rightValue->getType()->isDoubleTy()) {
                    
                    return builder->CreateFSub(leftValue, rightValue);
                    
                }
                
                if (leftValue->getType()->isIntegerTy() && rightValue->getType()->isIntegerTy()) {
                    
                    return builder->CreateSub(leftValue, rightValue);
                    
                }
                
                cout << "Invalid Operands" << endl;
                return nullptr;
            case TokenType::MULTIPLY:
                if (leftValue->getType()->isDoubleTy() && rightValue->getType()->isDoubleTy()) {
                    
                    return builder->CreateFMul(leftValue, rightValue);
                    
                }
                
                if (leftValue->getType()->isIntegerTy() && rightValue->getType()->isIntegerTy()) {
                    
                    return builder->CreateMul(leftValue, rightValue);
                    
                }
                
                cout << "Invalid Operands" << endl;
                return nullptr;
            case TokenType::DIVIDE:
                if (leftValue->getType()->isDoubleTy() && rightValue->getType()->isDoubleTy()) {
                    
                    return builder->CreateFDiv(leftValue, rightValue);
                    
                }
                
                if (leftValue->getType()->isIntegerTy() && rightValue->getType()->isIntegerTy()) {
                    
                    return builder->CreateSDiv(leftValue, rightValue);
                    
                }
                
                cout << "Invalid Operands" << endl;
                return nullptr;
            default:
                return nullptr;
        }
    }
    
    return nullptr;
}


Value* LLVMCodeGenerator::generateAssignmentNode(AssignmentNode* node) {
    
    Value* expressionValue = generateExpression(node->expression);
    if (expressionValue) {
        
        GlobalVariable* variable = module->getNamedGlobal(node->identifier);
        Type* valueType = expressionValue->getType();
        
        if (valueType->isIntegerTy()) {
            Constant* initialValue = ConstantInt::get(valueType, cast<ConstantInt>(expressionValue)->getSExtValue());
            
            if (variable) {
                variable->setInitializer(initialValue);
            } else {
                variable = new GlobalVariable(*module, valueType, false, GlobalValue::ExternalLinkage, initialValue, node->identifier);
            }
            
        } else if (valueType->isFloatingPointTy()) {
            Constant* initialValue = ConstantFP::get(valueType, cast<ConstantFP>(expressionValue)->getValueAPF().convertToDouble());
            
            if (variable) {
                variable->setInitializer(initialValue);
            } else {
                variable = new GlobalVariable(*module, valueType, false, GlobalValue::ExternalLinkage, initialValue, node->identifier);
            }
            
        } else if (valueType->isPointerTy()) {
            if (variable) {
                Constant* initializer = dyn_cast<Constant>(expressionValue);
                variable->setInitializer(initializer);
               
            } else {
                Constant* initializer = dyn_cast<Constant>(expressionValue);
                variable = new GlobalVariable(*module, valueType, false, GlobalValue::ExternalLinkage, initializer, node->identifier);
            }
        }
        
        return variable;
    }
    
    return nullptr;
}

Value* LLVMCodeGenerator::generateShowStatement(ShowStatementNode* node) {
    Value* expressionValue = generateExpression(node->expression);
    
    
    
    if (expressionValue) {
        if (expressionValue->getType()->isIntegerTy()) {
            
            Constant* intFormatStrConstant = ConstantDataArray::getString(*ctx, "%d\n");
            GlobalVariable* intFormatStrGlobal = new GlobalVariable(
                *module, intFormatStrConstant->getType(), true,
                GlobalValue::PrivateLinkage, intFormatStrConstant, ".str"
            );
            
            Value* intFormatStr = builder->CreatePointerCast(
                intFormatStrGlobal, Type::getInt8PtrTy(*ctx)->getPointerTo()
            );
            
            std::vector<Value*> intArgs = { intFormatStr, expressionValue };
            
            auto printfn = module->getFunction("printf");
            return builder->CreateCall(printfn, intArgs);
            
        } else if (expressionValue->getType()->isFloatingPointTy()) {
            
            Constant* floatFormatStrConstant = ConstantDataArray::getString(*ctx, "%f\n");
            GlobalVariable* floatFormatStrGlobal = new GlobalVariable(
                *module, floatFormatStrConstant->getType(), true,
                GlobalValue::PrivateLinkage, floatFormatStrConstant, ".str"
            );
            
            Value* floatFormatStr = builder->CreatePointerCast(
                floatFormatStrGlobal, Type::getInt8PtrTy(*ctx)->getPointerTo()
            );
            
            std::vector<Value*> floatArgs = { floatFormatStr, expressionValue };
            
            auto printfn = module->getFunction("printf");
            return builder->CreateCall(printfn, floatArgs);
            
        } else if (expressionValue->getType()->isPointerTy()) {
            
            Type* charType = Type::getInt8Ty(*ctx);
            Type* pointerType = PointerType::get(charType, 0);
            
            Constant* formatStrConstant = ConstantDataArray::getString(*ctx, "%s\n");
            GlobalVariable* formatStrGlobal = new GlobalVariable(
                *module, formatStrConstant->getType(), true,
                GlobalValue::PrivateLinkage, formatStrConstant, ".str"
            );
            Value* formatStrPtr = builder->CreatePointerCast(
                formatStrGlobal, Type::getInt8PtrTy(*ctx)->getPointerTo()
            );
               
            std::vector<Value*> ptrArgs = { formatStrPtr, expressionValue };
            auto printfn = module->getFunction("printf");
            
            return builder->CreateCall(printfn, ptrArgs);
        } else if (expressionValue->getType()->isDoubleTy()) {
            cout << "double recieved" << endl;
            Constant* doubleFormatStrConstant = ConstantDataArray::getString(*ctx, "%lf\n");
            GlobalVariable* doubleFormatStrGlobal = new GlobalVariable(
                *module, doubleFormatStrConstant->getType(), true,
                GlobalValue::PrivateLinkage, doubleFormatStrConstant, ".str"
            );
            
            Value* doubleFormatStr = builder->CreatePointerCast(
                doubleFormatStrGlobal, Type::getInt8PtrTy(*ctx)->getPointerTo()
            );
            
            std::vector<Value*> doubleArgs = { doubleFormatStr, expressionValue };
            
            auto printfn = module->getFunction("printf");
            return builder->CreateCall(printfn, doubleArgs);
            
        }
    }
    return nullptr;
}





void LLVMCodeGenerator::printIR() {
    module->print(outs(), nullptr);
}
