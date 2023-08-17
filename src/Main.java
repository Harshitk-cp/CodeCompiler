import java.util.List;

import error.SemanticException;
import lexer.Lexer;
import lexer.Token;
import parser.Parser;
import parser.nodes.AstNode;
import parser.nodes.AstPrinter;
import parser.nodes.AstVisitor;
import semantic.SemanticAnalyzer;
import semantic.SymbolTable;

public class Main {
    public static void main(String args[]) {
        String input = "let b = 2; let a = 2 ; let c = a + b; show a + b + c;";

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        SymbolTable symbolTable = new SymbolTable();
        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(symbolTable);

        // parse to token
        Parser parse = new Parser(tokens, symbolTable);

        for (Token token : tokens) {
            System.out.println(token);
        }

        // syntactical check
        AstNode rootNode = parse.parse();

        AstVisitor printer = new AstPrinter();
        rootNode.accept(printer);

        // semantical check
        try {
            semanticAnalyzer.analyze(rootNode);
            System.out.println("Semantic analysis completed successfully.");
        } catch (SemanticException e) {
            System.err.println("Semantic error: " + e.getMessage());
        }

    }
}