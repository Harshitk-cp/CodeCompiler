package lexer;

import java.util.*;
import java.util.regex.Matcher;

public class Lexer {

    private final String input;
    private int index = 0;
    private List<Token> tokens = new ArrayList<>();

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {

        while (index < input.length()) {

            boolean match = false;
            List<TokenInfo> tokenInfos = TokenDefinitions.getTokenDefinitions();

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
