package codegen.irnodes;

import java.util.List;

public class ProgramIrNode extends IrNode {
    private List<IrNode> statements;

    public ProgramIrNode(List<IrNode> statements) {
        this.statements = statements;
    }

    public List<IrNode> getStatements() {
        return statements;
    }

    @Override
    public List<IrNode> getChildren() {
        return statements;
    }

    @Override
    public void accept(IrVisitor visitor) {
        visitor.visitProgramIrNode(this);
    }

}
