#include <stdio.h>
#include <iostream>
#include "Token.h"
#include "TokenType.h"

Token::Token(TokenType type, std::string lexeme){
    this->type = type;
    this->lexeme = lexeme;
}

