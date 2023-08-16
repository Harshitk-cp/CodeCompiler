package parser.nodes;

public interface AstVisitor {
    void visitBinaryOperationNode(BinaryOperationNode node);

    void visitShowStatementNode(ShowStatementNode node);

    void visitAssignmentNode(AssignmentNode node);

    void visitNumberLiteralNode(NumberLiteralNode node);

    void visitStringLiteralNode(StringLiteralNode node);

    void visitIdentifierNode(IdentifierNode node);

    void visitProgramNode(ProgramNode node);
}
