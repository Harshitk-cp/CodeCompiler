package codegen.irnodes;

import java.util.List;

public class IrPrinter implements IrVisitor {

    @Override
    public void visitBinaryOperationIrNode(BinaryOperationIrNode node) {
        System.out.println("(");
        node.getLeftOperand().accept(this);
        System.out.println(" " + node.getOperator() + " ");
        node.getRightOperand().accept(this);
        System.out.println(")");
    }

    @Override
    public void visitShowStatementIrNode(ShowStatementIrNode node) {
        System.out.println("(");
        System.out.println(" " + node.getExpression() + " ");
        System.out.println(")");
    }

    @Override
    public void visitAssignmentIrNode(AssignmentIrNode node) {
        System.out.println("(");
        System.out.println(" " + node.getIdentifier() + " ");
        System.out.println(" " + node.getExpression() + " ");
        System.out.println(")");
    }

    @Override
    public void visitNumberLiteralIrNode(NumberLiteralIrNode node) {
        System.out.println("(");
        System.out.println(" " + node.getValue() + " ");
        System.out.println(")");
    }

    @Override
    public void visitIdentifierIrNode(IdentifierIrNode node) {
        System.out.println("(");
        System.out.println(" " + node.getName() + " ");
        System.out.println(")");
    }

    @Override
    public void visitProgramIrNode(ProgramIrNode node) {
        List<IrNode> statements = node.getStatements();
        for (IrNode statement : statements) {
            statement.accept(this);
            System.out.println();
        }
    }

    @Override
    public void visitStringLiteralIrNode(StringLiteralIrNode node) {
        System.out.println("(");
        System.out.println(" " + node.getValue() + " ");
        System.out.println(")");
    }

}
