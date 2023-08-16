package parser.nodes;

public abstract class AstNode {

    public abstract void accept(AstVisitor visitor);

}
