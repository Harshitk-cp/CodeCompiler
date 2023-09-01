#ifndef Token_h
#define Token_h

#include "TokenType.h"

class Token{
public:
    TokenType type;
    std::string lexeme;
    
    Token(TokenType type, std::string lexeme);
    
};

#endif /* Token_h */
