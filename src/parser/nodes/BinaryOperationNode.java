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
        AstNode newLeftOperand = leftOperand;
        AstNode newRightOperand = rightOperand;

        if (leftOperand instanceof BinaryOperationNode) {
            newLeftOperand = ((BinaryOperationNode) leftOperand).foldConstants();
        }

        if (rightOperand instanceof BinaryOperationNode) {
            newRightOperand = ((BinaryOperationNode) rightOperand).foldConstants();
        }

        boolean isLeftDecimal = (newLeftOperand instanceof NumberLiteralNode)
                && ((NumberLiteralNode) newLeftOperand).isDecimal();
        boolean isRightDecimal = (newRightOperand instanceof NumberLiteralNode)
                && ((NumberLiteralNode) newRightOperand).isDecimal();

        if (isLeftDecimal || isRightDecimal) {
            double leftValue = isLeftDecimal ? ((NumberLiteralNode) newLeftOperand).getDoubleValue()
                    : ((NumberLiteralNode) newLeftOperand).getIntValue();
            double rightValue = isRightDecimal ? ((NumberLiteralNode) newRightOperand).getDoubleValue()
                    : ((NumberLiteralNode) newRightOperand).getIntValue();

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
                    return new BinaryOperationNode(operator, newLeftOperand, newRightOperand);
            }
        } else {
            int leftValue = ((NumberLiteralNode) newLeftOperand).getIntValue();
            int rightValue = ((NumberLiteralNode) newRightOperand).getIntValue();

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
                    return new BinaryOperationNode(operator, newLeftOperand, newRightOperand);
            }
        }
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
