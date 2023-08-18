package codegen.irnodes;

import java.util.ArrayList;
import java.util.List;
import lexer.TokenType;

public class BinaryOperationIrNode extends IrNode {

    private TokenType operator;
    private IrNode leftOperand;
    private IrNode rightOperand;

    public BinaryOperationIrNode(TokenType operator, IrNode leftOperand, IrNode rightOperand) {
        this.operator = operator;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public TokenType getOperator() {
        return operator;
    }

    public IrNode getLeftOperand() {
        return leftOperand;
    }

    public IrNode getRightOperand() {
        return rightOperand;
    }

    @Override
    public void accept(IrVisitor visitor) {
        visitor.visitBinaryOperationIrNode(this);
    }

    @Override
    public List<IrNode> getChildren() {
        List<IrNode> children = new ArrayList<>();
        children.add(leftOperand);
        children.add(rightOperand);
        return children;
    }

    @Override
    public String toString() {
        return leftOperand + " " + operator + " " + rightOperand;
    }
}
