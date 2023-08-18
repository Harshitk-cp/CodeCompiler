package codegen.irnodes;

import java.util.Collections;
import java.util.List;

public class IdentifierIrNode extends IrNode {

    private String name;

    public IdentifierIrNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(IrVisitor visitor) {
        visitor.visitIdentifierIrNode(this);
    }

    @Override
    public List<IrNode> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return name;
    }

}
