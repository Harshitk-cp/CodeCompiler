package semantic;

import error.SemanticException;
import lexer.TokenType;
import parser.nodes.AstNode;
import parser.nodes.BinaryOperationNode;
import parser.nodes.IdentifierNode;
import parser.nodes.NumberLiteralNode;
import parser.nodes.StringLiteralNode;
import semantic.symbol.Symbol;

public class SemanticAnalyzer {
    private SymbolTable symbolTable;

    public SemanticAnalyzer(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public void analyze(AstNode node) {
        if (node instanceof IdentifierNode) {
            analyzeIdentifier((IdentifierNode) node);
        } else if (node instanceof NumberLiteralNode || node instanceof StringLiteralNode) {

        } else if (node instanceof BinaryOperationNode) {
            analyzeBinaryOperation((BinaryOperationNode) node);
        }

        for (AstNode child : node.getChildren()) {
            analyze(child);
        }
    }

    private void analyzeIdentifier(IdentifierNode identifierNode) {
        String name = identifierNode.getName();
        Symbol symbol = symbolTable.lookup(name);

        if (symbol == null) {
            throw new SemanticException("Undeclared variable: " + name);
        }
    }

    private void analyzeBinaryOperation(BinaryOperationNode binaryOpNode) {
        String operator = binaryOpNode.getOperator().toString();
        AstNode left = binaryOpNode.getLeftOperand();
        AstNode right = binaryOpNode.getRightOperand();

        if (!isNumericExpression(left) || !isNumericExpression(right)) {
            throw new SemanticException("Invalid operands for operator " + operator);
        }

    }

    private boolean isNumericExpression(AstNode node) {
        if (node instanceof NumberLiteralNode) {
            return true;
        } else if (node instanceof IdentifierNode) {

            symbolTable.enterScope();
            Symbol symbolInfo = symbolTable.lookup(((IdentifierNode) node).getName());

            return symbolInfo != null && isNumericType(symbolInfo.getType());
        } else if (node instanceof BinaryOperationNode) {
            BinaryOperationNode binaryOpNode = (BinaryOperationNode) node;
            return isNumericExpression(binaryOpNode.getLeftOperand()) &&
                    isNumericExpression(binaryOpNode.getRightOperand());
        }
        return false;
    }

    private boolean isNumericType(String string) {
        return string == TokenType.NUMBER.toString();
    }

}
