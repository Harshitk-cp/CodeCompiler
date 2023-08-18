package parser.nodes;

import java.util.List;

public class AstPrinter implements AstVisitor {

    private int indentationLevel = 0;

    private void printIndentation() {
        for (int i = 0; i < indentationLevel; i++) {
            System.out.print("   ");
        }
    }

    private void increaseIndentation() {
        indentationLevel++;
    }

    private void decreaseIndentation() {
        indentationLevel--;
    }

    @Override
    public void visitBinaryOperationNode(BinaryOperationNode node) {
        System.out.println("(");
        increaseIndentation();

        printIndentation();
        System.out.println(node.getOperator());

        node.getLeftOperand().accept(this);
        node.getRightOperand().accept(this);

        decreaseIndentation();
        printIndentation();
        System.out.println(")");
    }

    @Override
    public void visitShowStatementNode(ShowStatementNode node) {
        System.out.println("(");
        increaseIndentation();

        printIndentation();
        System.out.println("show");

        node.getExpression().accept(this);

        decreaseIndentation();
        printIndentation();
        System.out.println(")");
    }

    @Override
    public void visitAssignmentNode(AssignmentNode node) {
        System.out.println("(");
        increaseIndentation();

        printIndentation();
        System.out.println("=");

        printIndentation();
        System.out.println(node.getIdentifier());

        node.getExpression().accept(this);

        decreaseIndentation();
        printIndentation();
        System.out.println(")");
    }

    @Override
    public void visitNumberLiteralNode(NumberLiteralNode node) {
        System.out.println("(");
        increaseIndentation();

        printIndentation();
        System.out.println(node.getValue());

        decreaseIndentation();
        printIndentation();
        System.out.println(")");
    }

    @Override
    public void visitIdentifierNode(IdentifierNode node) {
        System.out.println("(");
        increaseIndentation();

        printIndentation();
        System.out.println(node.getName());

        decreaseIndentation();
        printIndentation();
        System.out.println(")");
    }

    @Override
    public void visitProgramNode(ProgramNode node) {
        List<AstNode> statements = node.getStatements();
        for (AstNode statement : statements) {
            statement.accept(this);
        }
    }

    @Override
    public void visitStringLiteralNode(StringLiteralNode node) {
        System.out.println("(");
        increaseIndentation();

        printIndentation();
        System.out.println(node.getValue());

        decreaseIndentation();
        printIndentation();
        System.out.println(")");
    }
}
