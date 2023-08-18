package parser.nodes;

import java.util.ArrayList;
import java.util.List;

import lexer.TokenType;

public class BinaryOperationNode extends AstNode {

    private TokenType operator;
    private AstNode leftOperand;
    private AstNode rightOperand;

    public BinaryOperationNode(TokenType operator, AstNode leftOperand, AstNode rightOperand) {
        this.operator = operator;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;

    }

    public TokenType getOperator() {
        return operator;
    }

    public AstNode getLeftOperand() {
        return leftOperand;
    }

    public AstNode getRightOperand() {
        return rightOperand;
    }

    public AstNode foldConstants() {
        if (leftOperand instanceof NumberLiteralNode && rightOperand instanceof NumberLiteralNode) {
            int leftValue = ((NumberLiteralNode) leftOperand).getValue();
            int rightValue = ((NumberLiteralNode) rightOperand).getValue();

            switch (operator) {
                case ADD:
                    return new NumberLiteralNode(leftValue + rightValue);
                case SUBTRACT:
                    return new NumberLiteralNode(leftValue - rightValue);
                case MULTIPLY:
                    return new NumberLiteralNode(leftValue * rightValue);
                case DIVIDE:
                    if (rightValue != 0) {
                        return new NumberLiteralNode(leftValue / rightValue);
                    } else {
                        throw new ArithmeticException("Division by zero");
                    }
                default:
                    return this;
            }
        }

        return this;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitBinaryOperationNode(this);
    }

    @Override
    public List<AstNode> getChildren() {
        List<AstNode> children = new ArrayList<>();
        children.add(leftOperand);
        children.add(rightOperand);
        return children;
    }

    @Override
    public String toString() {
        return leftOperand + " " + operator + " " + rightOperand;
    }

}
