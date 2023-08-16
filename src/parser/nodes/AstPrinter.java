package parser.nodes;

import java.util.List;

public class AstPrinter implements AstVisitor {

    @Override
    public void visitBinaryOperationNode(BinaryOperationNode node) {
        System.out.println("(");
        node.getLeftOperand().accept(this);
        System.out.println(" " + node.getOperator() + " ");
        node.getRightOperand().accept(this);
        System.out.println(")");
    }

    @Override
    public void visitShowStatementNode(ShowStatementNode node) {
        System.out.println("(");
        System.out.println(" " + node.getExpression() + " ");
        System.out.println(")");
    }

    @Override
    public void visitAssignmentNode(AssignmentNode node) {
        System.out.println("(");
        System.out.println(" " + node.getIdentifier() + " ");
        System.out.println(" " + node.getExpression() + " ");
        System.out.println(")");
    }

    @Override
    public void visitNumberLiteralNode(NumberLiteralNode node) {
        System.out.println("(");
        System.out.println(" " + node.getValue() + " ");
        System.out.println(")");
    }

    @Override
    public void visitIdentifierNode(IdentifierNode node) {
        System.out.println("(");
        System.out.println(" " + node.getName() + " ");
        System.out.println(")");
    }

    @Override
    public void visitProgramNode(ProgramNode node) {
        List<AstNode> statements = node.getStatements();
        for (AstNode statement : statements) {
            statement.accept(this);
            System.out.println();
        }
    }

    @Override
    public void visitStringLiteralNode(StringLiteralNode node) {
        System.out.println("(");
        System.out.println(" " + node.getValue() + " ");
        System.out.println(")");
    }

}
