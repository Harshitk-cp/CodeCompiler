package parser.nodes;

import java.util.ArrayList;
import java.util.List;

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
    public List<AstNode> getChildren() {
        List<AstNode> children = new ArrayList<>();
        children.add(expression);
        return children;
    }

    @Override
    public String toString() {
        return "Variable Declaration";
    }

}
