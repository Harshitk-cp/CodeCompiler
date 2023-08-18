# CodeCompiler
This project implements a simple code compiler. It demonstrates the process of lexing, parsing, semantic analysis, intermediate representation (IR) generation, and code generation for arithmetic expressions.

Overview
The compiler takes input expressions in a specific grammar and processes them through various stages to generate and execute Java code. The stages involved are:

Lexical Analysis: Tokenizes the input string into tokens such as keywords, identifiers, numbers, String and operators.
Syntax Parsing: Parses the tokens into an Abstract Syntax Tree (AST) using a simple grammar.
Semantic Analysis: Performs semantic checks to ensure that variables are declared before use and enforces type compatibility.
Intermediate Representation (IR) Generation: Converts the AST into an intermediate representation (IR) for further processing.
Optimization: Applies basic optimizations like constant folding, dead code elimination.
Code Generation: Generates Java code from the optimized IR.
Execution: Compiles and executes the generated Java code.

Getting Started
To run this project, follow these steps:

Clone this repository to your local machine.
Open a terminal and navigate to the project directory.

Usage
Modify the input expressions in the input.txt file to suit your needs.
Run the Main.java file using your preferred Java compiler or IDE.
The compiler will output the generated IR and Java code, and it will execute the generated Java code.
