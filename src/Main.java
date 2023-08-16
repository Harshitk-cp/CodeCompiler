import java.util.List;

import lexer.Lexer;
import lexer.Token;
import parser.Parser;
import parser.nodes.AstNode;
import parser.nodes.AstPrinter;
import parser.nodes.AstVisitor;

public class Main {
    public static void main(String args[]) {
        String input = "let variable = '2' / 2; show 4;";

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        Parser parse = new Parser(tokens);

        for (Token token : tokens)
            System.out.println(token);

        AstNode rootNode = parse.parse();

        AstVisitor printer = new AstPrinter();
        rootNode.accept(printer);

    }
}