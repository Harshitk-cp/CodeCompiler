package codegen;

import java.util.ArrayList;
import java.util.List;

import codegen.irnodes.AssignmentIrNode;
import codegen.irnodes.BinaryOperationIrNode;
import codegen.irnodes.IdentifierIrNode;
import codegen.irnodes.IrNode;
import codegen.irnodes.NumberLiteralIrNode;
import codegen.irnodes.ProgramIrNode;
import codegen.irnodes.ShowStatementIrNode;
import codegen.irnodes.StringLiteralIrNode;
import lexer.TokenType;
import optimization.DeadCodeEliminator;
import parser.nodes.AssignmentNode;
import parser.nodes.AstNode;
import parser.nodes.BinaryOperationNode;
import parser.nodes.IdentifierNode;
import parser.nodes.NumberLiteralNode;
import parser.nodes.ProgramNode;
import parser.nodes.ShowStatementNode;
import parser.nodes.StringLiteralNode;

public class IrGenerator {
    public IrNode generateIr(AstNode node) {

        if (node instanceof AssignmentNode) {

            AssignmentNode assignmentNode = (AssignmentNode) node;
            String target = assignmentNode.getIdentifier();
            IrNode expression = generateIr(assignmentNode.getExpression());
            return new AssignmentIrNode(target, expression);

        } else if (node instanceof BinaryOperationNode) {

            BinaryOperationNode binaryOperationNode = (BinaryOperationNode) node;
            AstNode foldedNode = binaryOperationNode.foldConstants();

            if (foldedNode instanceof NumberLiteralNode) {
                NumberLiteralNode numberLiteralNode = (NumberLiteralNode) foldedNode;
                if (numberLiteralNode.isDecimal()) {
                    return new NumberLiteralIrNode(numberLiteralNode.getDoubleValue());
                } else {
                    return new NumberLiteralIrNode(numberLiteralNode.getIntValue());
                }
            } else {
                TokenType operator = binaryOperationNode.getOperator();
                IrNode leftOperand = generateIr(binaryOperationNode.getLeftOperand());
                IrNode rightOperand = generateIr(binaryOperationNode.getRightOperand());
                return new BinaryOperationIrNode(operator, leftOperand, rightOperand);
            }

        } else if (node instanceof IdentifierNode) {

            IdentifierNode identifierNode = (IdentifierNode) node;
            String name = identifierNode.getName();
            return new IdentifierIrNode(name);

        } else if (node instanceof NumberLiteralNode) {

            NumberLiteralNode numberLiteralNode = (NumberLiteralNode) node;
            if (numberLiteralNode.isDecimal()) {
                return new NumberLiteralIrNode(numberLiteralNode.getDoubleValue());

            } else {
                return new NumberLiteralIrNode(numberLiteralNode.getIntValue());

            }

        } else if (node instanceof ProgramNode) {

            DeadCodeEliminator.eliminateDeadCode((ProgramNode) node);
            ProgramNode programNode = (ProgramNode) node;
            List<IrNode> irStatements = new ArrayList<>();
            for (AstNode statement : programNode.getStatements()) {
                IrNode irStatement = generateIr(statement);
                irStatements.add(irStatement);
            }

            return new ProgramIrNode(irStatements);
        } else if (node instanceof ShowStatementNode) {

            ShowStatementNode showStatementNode = (ShowStatementNode) node;
            IrNode expression = generateIr(showStatementNode.getExpression());
            return new ShowStatementIrNode(expression);

        } else if (node instanceof StringLiteralNode) {

            StringLiteralNode stringLiteralNode = (StringLiteralNode) node;
            String value = stringLiteralNode.getValue();
            return new StringLiteralIrNode(value);

        }
        return null;
    }

}
