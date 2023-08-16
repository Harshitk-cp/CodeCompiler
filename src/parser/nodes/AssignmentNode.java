package parser.nodes;

public class AssignmentNode extends AstNode {

    private String identifier;
    private AstNode expression;

    public AssignmentNode(String identifier, AstNode expression) {

        this.identifier = identifier;
        this.expression = expression;

    }

    public String getIdentifier() {
        return identifier;
    }

    public AstNode getExpression() {
        return expression;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitAssignmentNode(this);
    }

    @Override
    public String toString() {
        return "Variable Declaration";
    }

}
