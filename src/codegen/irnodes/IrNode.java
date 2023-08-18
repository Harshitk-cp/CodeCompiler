package codegen.irnodes;

import java.util.List;

public abstract class IrNode {
    public abstract void accept(IrVisitor visitor);

    public abstract List<IrNode> getChildren();
}
