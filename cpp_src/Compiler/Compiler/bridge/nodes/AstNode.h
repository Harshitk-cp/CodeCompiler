#pragma once
#include <string>
#include <vector>
#include "../token/TokenType.h"

class ASTNode {
public:
    virtual ~ASTNode() {}

    virtual std::vector<ASTNode*> getChildren() {
        return std::vector<ASTNode*>();
    }

    // Define a function to apply a function to each child
    void forEachChild(std::function<void(ASTNode*)> func) {
        std::vector<ASTNode*> children = getChildren();
        std::for_each(children.begin(), children.end(), func);
    }
};

class IdentifierNode : public ASTNode {
public:
    std::string name;

    IdentifierNode(const std::string& identifier) : name(identifier) {}
};

class AssignmentNode : public ASTNode {
public:
    std::string identifier;
    ASTNode* expression;

    AssignmentNode(std::string id, ASTNode* expr) : identifier(id), expression(expr) {}
    std::vector<ASTNode*> getChildren() override {
        return { expression };
    }
};

class BinaryOperationNode : public ASTNode {
public:
    TokenType op;
    ASTNode* left;
    ASTNode* right;

    std::vector<ASTNode*> getChildren() override {
        return { left, right };
    }

    BinaryOperationNode(TokenType operatorType, ASTNode* lhs, ASTNode* rhs)
        : op(operatorType), left(lhs), right(rhs) {}
};

class NumberLiteralNode : public ASTNode {
public:
    int intValue;
    double doubleValue;

    NumberLiteralNode(int value) : intValue(value), doubleValue(0) {}
    NumberLiteralNode(double value) : intValue(0), doubleValue(value) {}

    bool isDecimal() {
        return (typeid(doubleValue).name() == typeid(double).name());
    }
};

class StringLiteralNode : public ASTNode {
public:
    std::string value;

    StringLiteralNode(const std::string& str) : value(str) {}
};

class ShowStatementNode : public ASTNode {
public:
    ASTNode* expression;

    ShowStatementNode(ASTNode* expr) : expression(expr) {}
    std::vector<ASTNode*> getChildren() override {
        return { expression };
    }
};

class ProgramNode : public ASTNode {
public:
    std::vector<ASTNode*> statements;

    ProgramNode(const std::vector<ASTNode*>& stmts) : statements(stmts) {}

    std::vector<ASTNode*> getChildren() override {
        return statements;
    }
};
