package bridge;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import codegen.irnodes.AssignmentIrNode;
import codegen.irnodes.BinaryOperationIrNode;
import codegen.irnodes.IdentifierIrNode;
import codegen.irnodes.IrNode;
import codegen.irnodes.NumberLiteralIrNode;
import codegen.irnodes.ProgramIrNode;
import codegen.irnodes.ShowStatementIrNode;
import codegen.irnodes.StringLiteralIrNode;
import lexer.Token;
import lexer.TokenType;

public class IRToStringConverter {

    public static List<Token> convertIrNodeToTokens(IrNode irNode) {
        List<Token> tokens = new ArrayList<>();
        convertIrNodeToTokens(irNode, tokens);
        return tokens;
    }

    private static void convertIrNodeToTokens(IrNode irNode, List<Token> tokens) {

        if (irNode instanceof BinaryOperationIrNode) {

            BinaryOperationIrNode binaryNode = (BinaryOperationIrNode) irNode;
            tokens.addAll(convertIrNodeToTokens(binaryNode.getLeftOperand()));
            tokens.add(new Token(getBinaryOperatorTokenType(binaryNode.getOperator()),
                    binaryNode.getOperator().toString()));
            tokens.addAll(convertIrNodeToTokens(binaryNode.getRightOperand()));

        } else if (irNode instanceof AssignmentIrNode) {

            AssignmentIrNode assignmentNode = (AssignmentIrNode) irNode;
            tokens.add(new Token(TokenType.LET, "let"));
            tokens.add(new Token(TokenType.IDENTIFIER, assignmentNode.getIdentifier()));
            tokens.add(new Token(TokenType.ASSIGN, "="));
            convertIrNodeToTokens(assignmentNode.getExpression(), tokens);
            tokens.add(new Token(TokenType.ENDLINE, ";"));

        } else if (irNode instanceof IdentifierIrNode) {

            IdentifierIrNode identifierNode = (IdentifierIrNode) irNode;
            tokens.add(new Token(TokenType.IDENTIFIER, identifierNode.getName()));

        } else if (irNode instanceof NumberLiteralIrNode) {

            NumberLiteralIrNode numberNode = (NumberLiteralIrNode) irNode;
            String lexeme = numberNode.isDecimal()
                    ? Double.toString(numberNode.getDoubleValue())
                    : Integer.toString(numberNode.getIntValue());
            tokens.add(new Token(TokenType.NUMBER, lexeme));

        } else if (irNode instanceof StringLiteralIrNode) {

            StringLiteralIrNode stringNode = (StringLiteralIrNode) irNode;
            tokens.add(new Token(TokenType.STRING, stringNode.getValue()));

        } else if (irNode instanceof ShowStatementIrNode) {

            ShowStatementIrNode showNode = (ShowStatementIrNode) irNode;
            tokens.add(new Token(TokenType.SHOW, "show"));
            tokens.addAll(convertIrNodeToTokens(showNode.getExpression()));
            tokens.add(new Token(TokenType.ENDLINE, ";"));

        } else if (irNode instanceof ProgramIrNode) {

            ProgramIrNode programNode = (ProgramIrNode) irNode;
            for (IrNode statement : programNode.getStatements()) {
                convertIrNodeToTokens(statement, tokens);
            }

        }

    }

    private static TokenType getBinaryOperatorTokenType(TokenType operator) {
        switch (operator) {
            case ADD:
                return TokenType.ADD;
            case SUBTRACT:
                return TokenType.SUBTRACT;
            case MULTIPLY:
                return TokenType.MULTIPLY;
            case DIVIDE:
                return TokenType.DIVIDE;
            default:
                return TokenType.UNKNOWN_TOKEN_TYPE;
        }
    }

    public static String convertIrNodeToTokenString(IrNode irNode) {
        List<Token> tokens = convertIrNodeToTokens(irNode);
        return tokens.stream().map(Token::toString).collect(Collectors.joining("\n"));
    }
}
