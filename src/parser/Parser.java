package parser;

import java.util.ArrayList;
import java.util.List;

import error.ParseException;
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
import semantic.SymbolTable;
import semantic.symbol.Symbol;

public class Parser {

    private SymbolTable symbolTable;
    private final List<Token> tokens;
    private int curTokenIndex;
    // private List<String> grammerRules;

    public Parser(List<Token> tokens, SymbolTable symbolTable) {
        this.tokens = tokens;
        this.curTokenIndex = 0;
        this.symbolTable = symbolTable;
        // this.grammerRules = readGrammerFromFile("grammer.txt");
    }

    public AstNode parse() {
        symbolTable.enterScope();
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
        symbolTable.printSymbolTable();

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

        Symbol symbol = new Symbol(identifier, getTypeFromExpression(expression));
        symbolTable.inset(symbol);
        // symbolTable.exitScope();
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
            String valueStr = consumeToken(TokenType.NUMBER).getLexeme();
            if (valueStr.contains(".")) {
                double value = Double.parseDouble(valueStr);
                return new NumberLiteralNode(value);
            } else {
                int value = Integer.parseInt(valueStr);
                return new NumberLiteralNode(value);
            }
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

    private String getTypeFromExpression(AstNode expression) {
        if (expression instanceof BinaryOperationNode) {
            BinaryOperationNode binaryOpNode = (BinaryOperationNode) expression;
            TokenType operator = binaryOpNode.getOperator();
            if (operator == TokenType.ADD || operator == TokenType.SUBTRACT ||
                    operator == TokenType.MULTIPLY || operator == TokenType.DIVIDE) {
                return "NUMBER";
            }
        } else if (expression instanceof NumberLiteralNode) {
            return "NUMBER";
        } else if (expression instanceof StringLiteralNode) {
            return "STRING";
        }
        return "UNKNOWN"; // Default type if not able to determine
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

    // private List<String> readGrammerFromFile(String filename) {
    // try {

    // return Files.readAllLines(Paths.get("src/parser/grammer.txt"));

    // } catch (IOException e) {
    // throw new RuntimeException("Error reading file.", e);
    // }
    // }

}
