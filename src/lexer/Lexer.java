package lexer;

import java.util.*;
import java.util.regex.Matcher;

public class Lexer {

    private final String input;
    private int index = 0;
    private List<Token> tokens = new ArrayList<>();
    private List<TokenInfo> tokenInfos;

    public Lexer(String input, List<TokenInfo> tokenInfos) {
        this.input = input;
        this.tokenInfos = tokenInfos;
    }

    public List<Token> tokenize() {

        while (index < input.length()) {
            boolean match = false;

            for (TokenInfo tokenInfo : tokenInfos) {
                Matcher matcher = tokenInfo.getPattern().matcher(input.substring(index));

                if (matcher.lookingAt()) {
                    match = true;
                    String lexeme = matcher.group();
                    index += lexeme.length();

                    if (!tokenInfo.isIgnored()) {
                        tokens.add(new Token(tokenInfo.getTokenType(), lexeme));
                    }

                    break;

                }
            }

            if (!match) {

                index++;
                throw new RuntimeException("Unknown Characters");

            }

        }
        return tokens;
    }

}
