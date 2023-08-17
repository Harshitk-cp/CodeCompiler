package parser.nodes;

import java.util.ArrayList;
import java.util.List;

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
    public List<AstNode> getChildren() {
        List<AstNode> children = new ArrayList<>();
        children.add(expression);
        return children;
    }

    @Override
    public String toString() {
        return expression + "";
    }
}
