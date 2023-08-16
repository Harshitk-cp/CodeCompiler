package lexer;

import java.util.regex.Pattern;

public class TokenInfo {
    private final Pattern pattern;
    private final TokenType tokenType;
    private final boolean ignored;

    public TokenInfo(String regex, TokenType tokenType, boolean ignored) {
        this.pattern = Pattern.compile("^(" + regex + ")");
        this.tokenType = tokenType;
        this.ignored = ignored;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public boolean isIgnored() {
        return ignored;
    }
}
