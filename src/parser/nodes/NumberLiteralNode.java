package parser.nodes;

public class NumberLiteralNode extends AstNode {
    private int value;

    public NumberLiteralNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitNumberLiteralNode(this);
    }

    @Override
    public String toString() {
        return value + "";
    }
}
