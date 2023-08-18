package codegen.irnodes;

import java.util.ArrayList;
import java.util.List;

public class ShowStatementIrNode extends IrNode {

    private IrNode expression;

    public ShowStatementIrNode(IrNode expression) {
        this.expression = expression;
    }

    public IrNode getExpression() {
        return expression;
    }

    @Override
    public void accept(IrVisitor visitor) {
        visitor.visitShowStatementIrNode(this);
    }

    @Override
    public List<IrNode> getChildren() {
        List<IrNode> children = new ArrayList<>();
        children.add(expression);
        return children;
    }

    @Override
    public String toString() {
        return expression + "";
    }

}
