package parser.nodes;

import java.util.Collections;
import java.util.List;

public class StringLiteralNode extends AstNode {
    private String value;

    public StringLiteralNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitStringLiteralNode(this);
    }

    @Override
    public List<AstNode> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return value;
    }

}
