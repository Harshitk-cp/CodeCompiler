package parser.nodes;

import java.util.List;

public abstract class AstNode {

    public abstract void accept(AstVisitor visitor);

    public abstract List<AstNode> getChildren();

}
