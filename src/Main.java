import java.util.ArrayList;
import java.util.List;

import lexer.Lexer;
import lexer.Token;
import lexer.TokenDefinitions;
import lexer.TokenInfo;
import lexer.TokenPatterns;
import lexer.TokenType;

public class Main {
    public static void main(String args[]) {
        String input = "let d = 2 / 2; show x;";

        List<TokenInfo> tokenInfos = TokenDefinitions.getTokenDefinitions();

        Lexer lexer = new Lexer(input, tokenInfos);

        List<Token> tokens = lexer.tokenize();

        for (Token token : tokens)
            System.out.println(token);
    }
}