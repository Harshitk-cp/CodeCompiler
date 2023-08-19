package codegen.irnodes;

import java.util.Collections;
import java.util.List;

public class NumberLiteralIrNode extends IrNode {

    private int intValue;
    private double doubleValue;
    private boolean isDecimal;

    public NumberLiteralIrNode(int value) {
        this.intValue = value;
        this.isDecimal = false;
    }

    public NumberLiteralIrNode(double value) {
        this.doubleValue = value;
        this.isDecimal = true;
    }

    public boolean isDecimal() {
        return isDecimal;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public int getIntValue() {
        return intValue;
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
        return isDecimal ? doubleValue + "" : intValue + "";
    }

}
