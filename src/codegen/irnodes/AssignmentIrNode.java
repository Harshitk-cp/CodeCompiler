package codegen.irnodes;

import java.util.ArrayList;
import java.util.List;

public class AssignmentIrNode extends IrNode {
    private String identifier;
    private IrNode expression;

    public AssignmentIrNode(String target, IrNode expression) {
        this.identifier = target;
        this.expression = expression;
    }

    public String getIdentifier() {
        return identifier;
    }

    public IrNode getExpression() {
        return expression;
    }

    @Override
    public void accept(IrVisitor visitor) {
        visitor.visitAssignmentIrNode(this);
    }

    @Override
    public List<IrNode> getChildren() {
        List<IrNode> children = new ArrayList<>();
        children.add(expression);
        return children;
    }

    @Override
    public String toString() {
        return "Variable Declaration";
    }

}
