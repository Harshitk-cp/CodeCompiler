package codegen;

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

public class IrToJava {

    public static String convertIrToJava(IrNode irNode) {
        StringBuilder javaCode = new StringBuilder();
        javaCode.append("public class GeneratedCode {\n");
        javaCode.append("    public static void main(String[] args) {\n");

        String irJavaCode = convertIrNodeToJava(irNode);
        javaCode.append(irJavaCode);

        javaCode.append("    }\n");
        javaCode.append("}\n");

        return javaCode.toString();
    }

    public static String convertIrNodeToJava(IrNode irNode) {
        if (irNode instanceof BinaryOperationIrNode) {
            BinaryOperationIrNode binaryNode = (BinaryOperationIrNode) irNode;
            String left = convertIrNodeToJava(binaryNode.getLeftOperand());
            String right = convertIrNodeToJava(binaryNode.getRightOperand());

            if (binaryNode.getOperator() == TokenType.ADD && left.contains("\"")
                    && right.contains("\"")) {
                return left + " + " + right;
            } else if (left.contains("\"")
                    && right.contains("\"") || binaryNode.getOperator() == TokenType.SUBTRACT
                    || binaryNode.getOperator() == TokenType.MULTIPLY || binaryNode.getOperator() == TokenType.DIVIDE) {

                return "\"Can not perform " + binaryNode.getOperator().toString() + " operation on String\"";

            }

            switch (binaryNode.getOperator()) {
                case ADD:
                    return left + " + " + right;
                case SUBTRACT:
                    return left + " - " + right;
                case MULTIPLY:
                    return left + " * " + right;
                case DIVIDE:
                    return left + " / " + right;
                default:
                    return "(" + left + " " + binaryNode.getOperator() + " " + right + ")";
            }
        }
        if (irNode instanceof AssignmentIrNode) {
            AssignmentIrNode assignmentNode = (AssignmentIrNode) irNode;
            String target = assignmentNode.getIdentifier();
            String expression = convertIrNodeToJava(assignmentNode.getExpression());

            if (assignmentNode.getExpression() instanceof StringLiteralIrNode) {
                return "String " + target + " = " + expression + ";";
            } else if (assignmentNode.getExpression() instanceof NumberLiteralIrNode) {
                NumberLiteralIrNode numberLiteralIrNode = (NumberLiteralIrNode) assignmentNode.getExpression();
                if (numberLiteralIrNode.isDecimal()) {
                    return "double " + target + " = " + expression + ";";
                } else {
                    return "int " + target + " = " + expression + ";";
                }

            }
            return "";
        } else if (irNode instanceof IdentifierIrNode) {
            return ((IdentifierIrNode) irNode).getName();
        } else if (irNode instanceof NumberLiteralIrNode) {
            NumberLiteralIrNode numberNode = (NumberLiteralIrNode) irNode;
            if (numberNode.isDecimal()) {
                return Double.toString(numberNode.getDoubleValue());
            } else {
                return Integer.toString(numberNode.getIntValue());
            }
        } else if (irNode instanceof StringLiteralIrNode) {
            return ((StringLiteralIrNode) irNode).getValue();
        } else if (irNode instanceof ShowStatementIrNode) {
            ShowStatementIrNode showNode = (ShowStatementIrNode) irNode;
            String expression = convertIrNodeToJava(showNode.getExpression());
            return "System.out.println(" + expression + ");";
        } else if (irNode instanceof ProgramIrNode) {
            ProgramIrNode programNode = (ProgramIrNode) irNode;
            StringBuilder javaCode = new StringBuilder();
            List<IrNode> statements = programNode.getStatements();
            for (IrNode statement : statements) {
                javaCode.append(convertIrNodeToJava(statement)).append("\n");
            }
            return javaCode.toString();
        } else {
            return "";
        }
    }

}
