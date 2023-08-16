package parser.nodes;

public class StringLiteralNode extends AstNode {
    private String value;

    public StringLiteralNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitStringLiteralNode(this);
    }

    @Override
    public String toString() {
        return value;
    }

}
