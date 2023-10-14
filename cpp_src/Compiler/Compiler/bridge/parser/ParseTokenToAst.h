#pragma once
#ifndef ParseTokenToAst_h
#define ParseTokenToAst_h

#include "../nodes/AstNode.h"
#include <vector>
#include <string>

class Parser {
private:
    std::vector<Token> tokens;
    int curTokenIndex;

public:
    Parser(const std::vector<Token>& tokens);
    ASTNode* parse();

private:
    ASTNode* pasreProgram();
    std::vector<ASTNode*> parseStatements();
    ASTNode* parseShowStatement();
    ASTNode* parseLetStatement();
    ASTNode* parseExpression();
    ASTNode* parseTerm();
    ASTNode* parseFactor();
    TokenType getCurrentTokenType();
    Token consumeToken(TokenType expectedType);
    void advance();
};

#endif /* ParseTokenToAst_h */
