package codegen.irnodes;

import java.util.Collections;
import java.util.List;

public class NumberLiteralIrNode extends IrNode {

    private int value;

    public NumberLiteralIrNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void accept(IrVisitor visitor) {
        visitor.visitNumberLiteralIrNode(this);
    }

    @Override
    public List<IrNode> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return value + "";
    }

}
