#include <iostream>
#include "../token/Token.h"
#include "../token/TokenType.h"
#include "ParseTokenToAst.h"

Parser::Parser(const std::vector<Token>& tokens)
    : tokens(tokens), curTokenIndex(0) {}

ASTNode* Parser::parse() {
    return pasreProgram();
}

ASTNode* Parser::pasreProgram() {
    std::vector<ASTNode*> statements = parseStatements();
    return new ProgramNode(statements);
}

std::vector<ASTNode*> Parser::parseStatements() {
    std::vector<ASTNode*> statements;
    while (curTokenIndex < tokens.size()) {
        if (getCurrentTokenType() == SHOW) {
            statements.push_back(parseShowStatement());
        } else if (getCurrentTokenType() == LET) {
            statements.push_back(parseLetStatement());
        } else {
            return statements;
        }
    }
    return statements;
}

ASTNode* Parser::parseShowStatement() {
    consumeToken(SHOW);
    ASTNode* expression = parseExpression();
    consumeToken(ENDLINE);
    return new ShowStatementNode(expression);
}

ASTNode* Parser::parseLetStatement() {
    consumeToken(LET);
    std::string identifier = consumeToken(IDENTIFIER).lexeme;
    consumeToken(ASSIGN);
    ASTNode* expression = parseExpression();
    consumeToken(ENDLINE);
    return new AssignmentNode(identifier, expression);
}


ASTNode* Parser::parseExpression() {
    ASTNode* left = parseTerm();

    while (getCurrentTokenType() == ADD || getCurrentTokenType() == SUBTRACT) {
        TokenType operatorType = getCurrentTokenType();
        consumeToken(operatorType);
        ASTNode* right = parseTerm();
        left = new BinaryOperationNode(operatorType, left, right);
    }

    return left;
}

ASTNode* Parser::parseTerm() {
    ASTNode* left = parseFactor();

    while (getCurrentTokenType() == MULTIPLY || getCurrentTokenType() == DIVIDE) {
        TokenType operatorType = getCurrentTokenType();
        consumeToken(operatorType);
        ASTNode* right = parseFactor();
        left = new BinaryOperationNode(operatorType, left, right);
    }

    return left;
}

ASTNode* Parser::parseFactor() {
    if (getCurrentTokenType() == NUMBER) {
        std::string valueStr = consumeToken(NUMBER).lexeme;
        if (valueStr.find('.') != std::string::npos) {
            double value = std::stod(valueStr);
            return new NumberLiteralNode(value);
        } else {
            int value = std::stoi(valueStr);
            return new NumberLiteralNode(value);
        }
    } else if (getCurrentTokenType() == STRING) {
        std::string valueStr = consumeToken(STRING).lexeme;
        return new StringLiteralNode(valueStr);
    } else if (getCurrentTokenType() == IDENTIFIER) {
        std::string identifier = consumeToken(IDENTIFIER).lexeme;
        return new IdentifierNode(identifier);
    } else {
        return new NumberLiteralNode(5);
    }
}

TokenType Parser::getCurrentTokenType() {
    if (curTokenIndex < tokens.size()) {
        return tokens[curTokenIndex].type;
    } else {
        return UNKNOWN_TOKEN_TYPE;
    }
}

Token Parser::consumeToken(TokenType expectedType) {
    Token currentToken = tokens[curTokenIndex];

    if (currentToken.type == expectedType) {
        advance();
        return currentToken;
    } else {
        return currentToken;
    }
}

void Parser::advance() {
    if (curTokenIndex < tokens.size() - 1) {
        curTokenIndex++;
    } else {
        return;
    }
}
