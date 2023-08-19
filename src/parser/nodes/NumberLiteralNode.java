package parser.nodes;

import java.util.Collections;
import java.util.List;

public class NumberLiteralNode extends AstNode {
    private double doubleValue;
    private int intValue;
    private boolean isDecimal;

    public NumberLiteralNode(int value) {
        this.intValue = value;
        this.isDecimal = false;
    }

    public NumberLiteralNode(double value) {
        this.doubleValue = value;
        this.isDecimal = true;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public boolean isDecimal() {
        return isDecimal;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitNumberLiteralNode(this);
    }

    @Override
    public List<AstNode> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return isDecimal ? doubleValue + "" : intValue + "";
    }
}
