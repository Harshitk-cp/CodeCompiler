package parser.nodes;

import java.util.Collections;
import java.util.List;

public class IdentifierNode extends AstNode {
    private String name;

    public IdentifierNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitIdentifierNode(this);
    }

    @Override
    public List<AstNode> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return name;
    }
}
