#pragma once
#ifndef LLVMCodeGen_h
#define LLVMCodeGen_h

#include <llvm/IR/LLVMContext.h>
#include <llvm/IR/Module.h>
#include <llvm/IR/IRBuilder.h>
#include <llvm/IR/Verifier.h>
#include "../bridge/nodes/AstNode.h"
#include <llvm/Support/raw_ostream.h>
#include <llvm/ADT/Twine.h>

using namespace llvm;

class LLVMCodeGenerator {
private:
    
    void moduleInit() {
        ctx = std::make_unique<LLVMContext>();
        module = std::make_unique<Module>("Main Module", *ctx);
        module->setTargetTriple("arm64-apple-macosx14.0.0");
        builder = std::make_unique<IRBuilder<>>(*ctx);
    }

    std::unique_ptr<LLVMContext> ctx;
    std::unique_ptr<Module> module;
    std::unique_ptr<IRBuilder<>> builder;
    
    
    Value* generateCode(ASTNode* astNode);
    Value* generateProgramNode(ProgramNode* node);
    Value* generateExpression(ASTNode* expression);
    Value* generateBinaryOperation(BinaryOperationNode* node);
    Value* generateAssignmentNode(AssignmentNode* node);
    Value* generateShowStatement(ShowStatementNode* node);
    
    
    Function* strlenFunc;
    Function* memcpyFunc;
    Function* mallocFunc;
    
    
    void setupExternalFunctions(){
        //printf function
        auto bytePtrTy = builder->getInt8Ty()->getPointerTo();
        module->getOrInsertFunction("printf", FunctionType::get(builder->getInt32Ty(),bytePtrTy, true));
        
        
        //str concatenation funcitons
        FunctionType* strlenFuncType = FunctionType::get(Type::getInt64Ty(*ctx), {Type::getInt8PtrTy(*ctx)}, false);
        
        FunctionType* memcpyFuncType = FunctionType::get(Type::getInt8PtrTy(*ctx), {
            Type::getInt8PtrTy(*ctx),
            Type::getInt8PtrTy(*ctx),
            Type::getInt64Ty(*ctx),
            Type::getInt32Ty(*ctx),
            Type::getInt1Ty(*ctx)
        }, false);

        FunctionType* mallocFuncType = FunctionType::get(
            Type::getInt8PtrTy(*ctx),
            {Type::getInt64Ty(*ctx)},
            false
        );

        strlenFunc = Function::Create(strlenFuncType, Function::ExternalLinkage, "strlen", *module);
        memcpyFunc = Function::Create(memcpyFuncType, Function::ExternalLinkage, "memcpy", *module);
        mallocFunc = Function::Create(mallocFuncType, Function::ExternalLinkage, "malloc", *module);
    }
    
    Function* createFunction(const std::string& fnName, FunctionType* fnType){
        auto fn = module->getFunction(fnName);
        
        if(fn == nullptr){
            fn = createFunctionProto(fnName, fnType);
        }
        
        createFunctionBlock(fn);
        return fn;
    }
    
    Function* fn;
    
    Function* createFunctionProto(const std::string& fnName, FunctionType* fnType){
        auto fn = Function::Create(fnType, Function::ExternalLinkage, fnName, *module);
        verifyFunction(*fn);
        return fn;
    }
    
    void createFunctionBlock(Function* fn){
        auto entry = createBB("entry", fn);
        builder->SetInsertPoint(entry);
    }
    
    BasicBlock* createBB(std::string name, Function* fn = nullptr){
        return BasicBlock::Create(*ctx, name, fn);
    }
     
    void saveModuleToFile(const std::string& fileName){
        std::error_code errorCode;
        raw_fd_ostream outLL(fileName, errorCode);
        module->print(outLL, nullptr);
    }
    
    
    
    Function* createStringConcatenationFunction() {
        
        std::vector<Type*> paramTypes = {Type::getInt8PtrTy(*ctx), Type::getInt8PtrTy(*ctx)};
        FunctionType* funcType = FunctionType::get(Type::getInt8PtrTy(*ctx), paramTypes, false);
        Function* concatStringsFunc = Function::Create(funcType, Function::ExternalLinkage, "concatStrings", *module);
        
        BasicBlock* entryBB = BasicBlock::Create(*ctx, "entry", concatStringsFunc);
        IRBuilder<> builder(entryBB);
        
        Argument* str1Arg = concatStringsFunc->arg_begin();
        Argument* str2Arg = std::next(concatStringsFunc->arg_begin());
        
        Value* len1 = builder.CreateCall(strlenFunc, str1Arg);
        Value* len2 = builder.CreateCall(strlenFunc, str2Arg);
        
        Value* totalLen = builder.CreateAdd(len1, len2);
        
        Value* resultPtr = builder.CreateCall(mallocFunc, totalLen);
        
        builder.CreateCall(memcpyFunc, {resultPtr, str1Arg, len1, builder.getInt32(0), builder.getInt1(false)});
        
        Value* str2Start = builder.CreateGEP(Type::getInt8Ty(*ctx), resultPtr, len1);
        
        builder.CreateCall(memcpyFunc, {str2Start, str2Arg, len2, builder.getInt32(0), builder.getInt1(false)});
        
        Value* nullTerminator = builder.CreateGEP(Type::getInt8Ty(*ctx), resultPtr, totalLen);
        builder.CreateStore(builder.getInt8(0), nullTerminator);
        
        builder.CreateRet(resultPtr);
        
        return concatStringsFunc;
    }

public:
    LLVMCodeGenerator() {
        moduleInit();
        setupExternalFunctions();
    }
    void compile(ASTNode* astNode);
    void printIR();
};

#endif /* LLVMCodeGen_h */
