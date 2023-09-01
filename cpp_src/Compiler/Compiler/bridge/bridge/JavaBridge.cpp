#include "jni.h"
#include "bridge_CustomIRBridge.h"
#include <iostream>
#include <cstdlib>
#include "../token/TokenType.h"
#include "../token/Token.h"
#include "../parser/ParseTokenToAst.h"
#include "../../llvm_codegen/LLVMCodeGen.h"
extern "C" JNIEXPORT void JNICALL Java_bridge_CustomIRBridge_processBinaryTree(JNIEnv* env, jclass cls, jobjectArray javaStringArray) {

    jsize arrayLength = env->GetArrayLength(javaStringArray);
    std::vector<Token> tokens;
    for (jsize i = 0; i < arrayLength; ++i) {
        jobject tokenObject = env->GetObjectArrayElement(javaStringArray, i);
        jclass tokenClass = env->GetObjectClass(tokenObject);
       
        jfieldID typeFieldId = env->GetFieldID(tokenClass, "type", "Llexer/TokenType;");
        jobject typeValue = env->GetObjectField(tokenObject, typeFieldId);
        jclass typeClass = env->GetObjectClass(typeValue);
        jmethodID ordinalMethodId = env->GetMethodID(typeClass, "ordinal", "()I");
        jint typeValueOrdinal = env->CallIntMethod(typeValue, ordinalMethodId);
        
    
        jfieldID lexemeFieldId = env->GetFieldID(tokenClass, "lexeme", "Ljava/lang/String;");
        jstring lexeme = (jstring) env->GetObjectField(tokenObject, lexemeFieldId);
        const char* clexeme = env->GetStringUTFChars(lexeme, NULL);
        const std::string lexemeString(clexeme);
           
        TokenType tokenType;
        switch (typeValueOrdinal) {
            case 0: tokenType = NUMBER; break;
            case 1: tokenType = STRING; break;
            case 2: tokenType = IDENTIFIER; break;
            case 3: tokenType = WHITESPACE; break;
            case 4: tokenType = ENDLINE; break;
            case 5: tokenType = LET; break;
            case 6: tokenType = SHOW; break;
            case 7: tokenType = ADD; break;
            case 8: tokenType = SUBTRACT; break;
            case 9: tokenType = MULTIPLY; break;
            case 10: tokenType = DIVIDE; break;
            case 11: tokenType = ASSIGN; break;
            default: tokenType = UNKNOWN_TOKEN_TYPE; break;;
        }
        
        Token token(tokenType, clexeme);
        
        tokens.push_back(token);

        std::cout << "[ " << tokens[i].type << " | " << tokens[i].lexeme << " ]" << std::endl;

        env->DeleteLocalRef(tokenObject);
   }
    
    Parser p(tokens);
    ASTNode* astNode =  p.parse();
    
    LLVMCodeGenerator llvmCodeGen;
    
    llvmCodeGen.generateCode(astNode);
    
    llvmCodeGen.printIR();
    
}
