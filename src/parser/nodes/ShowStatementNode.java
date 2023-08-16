package parser.nodes;

public class ShowStatementNode extends AstNode {

    private AstNode expression;

    public ShowStatementNode(AstNode expression) {
        this.expression = expression;
    }

    public AstNode getExpression() {
        return expression;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitShowStatementNode(this);
    }

    @Override
    public String toString() {
        return expression + "";
    }
}
