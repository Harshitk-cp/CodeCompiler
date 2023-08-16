package parser;

import java.util.ArrayList;
import java.util.List;

import lexer.Token;
import lexer.TokenType;
import parser.nodes.AssignmentNode;
import parser.nodes.AstNode;
import parser.nodes.BinaryOperationNode;
import parser.nodes.IdentifierNode;
import parser.nodes.NumberLiteralNode;
import parser.nodes.ProgramNode;
import parser.nodes.ShowStatementNode;
import parser.nodes.StringLiteralNode;

public class Parser {

    private final List<Token> tokens;
    private int curTokenIndex;
    // private List<String> grammerRules;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.curTokenIndex = 0;
        // this.grammerRules = readGrammerFromFile("grammer.txt");
    }

    public AstNode parse() {
        return pasreProgram();
    }

    private AstNode pasreProgram() {
        List<AstNode> statements = parseStatements();
        return new ProgramNode(statements);
    }

    private List<AstNode> parseStatements() {
        List<AstNode> statements = new ArrayList<>();
        while (curTokenIndex < tokens.size()) {
            if (currentTokenIs(TokenType.SHOW)) {
                statements.add(parseShowStatement());
            } else if (currentTokenIs(TokenType.LET)) {
                statements.add(parseLetStatement());
            } else {
                throw new ParseException("Expected show or let statement");
            }
        }
        return statements;
    }

    private AstNode parseShowStatement() {
        consumeToken(TokenType.SHOW);
        AstNode expression = parseExpression();
        consumeToken(TokenType.ENDLINE);
        return new ShowStatementNode(expression);
    }

    private AstNode parseLetStatement() {
        consumeToken(TokenType.LET);
        String identifier = consumeToken(TokenType.IDENTIFIER).getLexeme();
        consumeToken(TokenType.ASSIGN);
        AstNode expression = parseExpression();
        consumeToken(TokenType.ENDLINE);
        return new AssignmentNode(identifier, expression);
    }

    private AstNode parseExpression() {
        AstNode left = parseTerm();

        while (currentTokenIs(TokenType.ADD) || currentTokenIs(TokenType.SUBTRACT)) {
            TokenType operator = consumeToken(getCurrentToken().getType()).getType();
            AstNode right = parseTerm();
            left = new BinaryOperationNode(operator, left, right);
        }

        return left;
    }

    private AstNode parseTerm() {
        AstNode left = parseFactor();

        while (currentTokenIs(TokenType.MULTIPLY) ||
                currentTokenIs(TokenType.DIVIDE)) {
            TokenType operator = consumeToken(getCurrentToken().getType()).getType();
            AstNode right = parseFactor();
            left = new BinaryOperationNode(operator, left, right);
        }

        return left;
    }

    private AstNode parseFactor() {
        if (currentTokenIs(TokenType.NUMBER)) {
            int value = Integer.parseInt(consumeToken(TokenType.NUMBER).getLexeme());
            return new NumberLiteralNode(value);
        } else if (currentTokenIs(TokenType.STRING)) {
            String string = consumeToken(TokenType.STRING).getLexeme();
            return new StringLiteralNode(string);
        } else if (currentTokenIs(TokenType.IDENTIFIER)) {
            String identifier = consumeToken(TokenType.IDENTIFIER).getLexeme();
            return new IdentifierNode(identifier);
        } else {
            throw new ParseException("Unexpected token");
        }
    }

    private Token getCurrentToken() {
        if (curTokenIndex < tokens.size()) {
            return tokens.get(curTokenIndex);
        } else {
            throw new ParseException("Reached end of input");
        }
    }

    private boolean currentTokenIs(TokenType expectedType) {

        return getCurrentToken().getType() == expectedType;

    }

    private Token consumeToken(TokenType expectedType) {

        Token currentToken = getCurrentToken();

        if (currentToken.getType() == expectedType) {
            advance();
            return currentToken;
        } else {
            throw new ParseException("Expected token of type " + expectedType);
        }

    }

    private void advance() {
        if (curTokenIndex < tokens.size() - 0) {
            curTokenIndex++;
        } else {
            throw new ParseException("Reached end of input");
        }
    }

    public class ParseException extends RuntimeException {
        public ParseException(String message) {
            super(message);
        }

        public ParseException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    // private List<String> readGrammerFromFile(String filename) {
    // try {

    // return Files.readAllLines(Paths.get("src/parser/grammer.txt"));

    // } catch (IOException e) {
    // throw new RuntimeException("Error reading file.", e);
    // }
    // }

}
