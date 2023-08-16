package parser.nodes;

import java.util.List;

public class ProgramNode extends AstNode {

    private List<AstNode> statements;

    public ProgramNode(List<AstNode> statements) {
        this.statements = statements;
    }

    public List<AstNode> getStatements() {
        return statements;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitProgramNode(this);
    }

}
