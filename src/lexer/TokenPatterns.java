package lexer;

public class TokenPatterns {
    public static final String NUMBER = "\\d+(\\.\\d+)?";
    public static final String STRING = "\"[^\"']*\"";
    public static final String IDENTIFIER = "[a-zA-Z_][a-zA-Z0-9_]*";
    public static final String WHITESPACE = "\\s+";
    public static final String ENDLINE = ";";
    public static final String LET = "let";
    public static final String SHOW = "show";
    public static final String ADD = "\\+";
    public static final String SUBTRACT = "-";
    public static final String MULTIPLY = "\\*";
    public static final String DIVIDE = "/";
    public static final String ASSIGN = "=";
}
