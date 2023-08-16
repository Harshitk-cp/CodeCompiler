package parser.nodes;

import lexer.TokenType;

public class BinaryOperationNode extends AstNode {

    private TokenType operater;
    private AstNode leftOperand;
    private AstNode rightOperand;

    public BinaryOperationNode(TokenType operater, AstNode leftOperand, AstNode rightOperand) {
        this.operater = operater;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;

    }

    public TokenType getOperator() {
        return operater;
    }

    public AstNode getLeftOperand() {
        return leftOperand;
    }

    public AstNode getRightOperand() {
        return rightOperand;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitBinaryOperationNode(this);
    }

    @Override
    public String toString() {
        return leftOperand + " " + operater + " " + rightOperand;
    }

}
