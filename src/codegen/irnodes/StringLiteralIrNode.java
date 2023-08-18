package codegen.irnodes;

import java.util.Collections;
import java.util.List;

public class StringLiteralIrNode extends IrNode {

    private String value;

    public StringLiteralIrNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void accept(IrVisitor visitor) {
        visitor.visitStringLiteralIrNode(this);
    }

    @Override
    public List<IrNode> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return value;
    }

}
