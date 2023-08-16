package parser.nodes;

public class IdentifierNode extends AstNode {
    private String name;

    public IdentifierNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitIdentifierNode(this);
    }

    @Override
    public String toString() {
        return name;
    }
}
