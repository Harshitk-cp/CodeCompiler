package lexer;

import java.util.ArrayList;
import java.util.List;

public class TokenDefinitions {
    public static List<TokenInfo> getTokenDefinitions() {
        List<TokenInfo> tokenInfos = new ArrayList<>();

        tokenInfos.add(new TokenInfo(TokenPatterns.LET, TokenType.LET, false));
        tokenInfos.add(new TokenInfo(TokenPatterns.SHOW, TokenType.SHOW, false));
        tokenInfos.add(new TokenInfo(TokenPatterns.NUMBER, TokenType.NUMBER, false));
        tokenInfos.add(new TokenInfo(TokenPatterns.IDENTIFIER, TokenType.IDENTIFIER, false));
        tokenInfos.add(new TokenInfo(TokenPatterns.ADD, TokenType.ADD, false));
        tokenInfos.add(new TokenInfo(TokenPatterns.SUBTRACT, TokenType.SUBTRACT, false));
        tokenInfos.add(new TokenInfo(TokenPatterns.MULTIPLY, TokenType.MULTIPLY, false));
        tokenInfos.add(new TokenInfo(TokenPatterns.DIVIDE, TokenType.DIVIDE, false));
        tokenInfos.add(new TokenInfo(TokenPatterns.ASSIGN, TokenType.ASSIGN, false));
        tokenInfos.add(new TokenInfo(TokenPatterns.WHITESPACE, TokenType.WHITESPACE, true));
        tokenInfos.add(new TokenInfo(TokenPatterns.ENDLINE, TokenType.ENDLINE, false));

        return tokenInfos;
    }
}
