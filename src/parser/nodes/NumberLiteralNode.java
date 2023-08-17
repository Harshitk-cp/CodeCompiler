package parser.nodes;

import java.util.Collections;
import java.util.List;

public class NumberLiteralNode extends AstNode {
    private int value;

    public NumberLiteralNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
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
        return value + "";
    }
}
