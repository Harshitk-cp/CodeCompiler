package codegen.irnodes;

public interface IrVisitor {

    void visitAssignmentIrNode(AssignmentIrNode node);

    void visitBinaryOperationIrNode(BinaryOperationIrNode node);

    void visitIdentifierIrNode(IdentifierIrNode node);

    void visitNumberLiteralIrNode(NumberLiteralIrNode node);

    void visitProgramIrNode(ProgramIrNode node);

    void visitShowStatementIrNode(ShowStatementIrNode node);

    void visitStringLiteralIrNode(StringLiteralIrNode node);

}
