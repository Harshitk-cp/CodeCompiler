import java.util.List;

import lexer.Lexer;
import lexer.Token;
import lexer.TokenDefinitions;
import lexer.TokenInfo;

public class Main {
    public static void main(String args[]) {
        String input = "let d = 'd'; show x;";

        Lexer lexer = new Lexer(input);

        List<Token> tokens = lexer.tokenize();

        for (Token token : tokens)
            System.out.println(token);
    }
}