#pragma once
#ifndef LLVMCodeGen_h
#define LLVMCodeGen_h

#include <llvm/IR/LLVMContext.h>
#include <llvm/IR/Module.h>
#include <llvm/IR/IRBuilder.h>
#include <llvm/IR/Verifier.h>
#include "../bridge/nodes/AstNode.h"

using namespace llvm;

class LLVMCodeGenerator {
private:
    LLVMContext context;
    Module module;
    IRBuilder<> builder;

    // Declare private helper functions
    Value* generateExpression(ASTNode* expression);
    void generateProgramNode(ProgramNode* node);
    void generateBinaryOperation(BinaryOperationNode* node);
    void generateShowStatement(ShowStatementNode* node);
    void generateAssignmentNode(AssignmentNode* node);
    // Add more private helper functions for other AST node types

public:
    LLVMCodeGenerator();

    void generateCode(ASTNode* astNode);
    void printIR();
};

#endif /* LLVMCodeGen_h */
