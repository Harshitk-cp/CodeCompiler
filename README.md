# LLVM-Based Code Compiler

This project is a code compiler that utilizes LLVM (Low-Level Virtual Machine) to generate low-level intermediate representation (IR) of code and compiles it to run on a target architecture machine. The compiler goes through several stages, including lexical analysis, syntax parsing, semantic analysis, and IR generation, before applying basic optimizations and generating machine code for execution on a specific architecture. Here is an in-depth overview of the project:

## Overview

The LLVM-Based Code Compiler is designed to process input expressions following a specific grammar and perform various stages of compilation to generate and execute machine code. The key stages involved in this compiler are as follows:

- Lexical Analysis: This initial step tokenizes the input string, breaking it down into tokens such as keywords, identifiers, numbers, strings, and operators. This tokenization is necessary for the subsequent parsing stages.

- Syntax Parsing: The tokenized input is parsed into an Abstract Syntax Tree (AST) using a simple grammar. This AST serves as the basis for subsequent analysis and code generation.

- Semantic Analysis: Semantic checks are performed to ensure that variables are declared before use and to enforce type compatibility. This stage helps maintain the correctness and safety of the code.

- Intermediate Representation (IR) Generation: Instead of generating Java code, the project now converts the AST into LLVM IR. LLVM IR is a low-level representation that is closer to machine code, making it an ideal format for further processing and compilation.

- Optimization: Basic optimizations, such as constant folding and dead code elimination, are applied to the LLVM IR. These optimizations can improve the efficiency and performance of the generated machine code.

- Code Generation: The LLVM IR is used to generate machine code that is specific to a target architecture. LLVM's versatility allows it to support various hardware platforms, making it suitable for cross-platform compilation.

- Execution: The generated machine code is then compiled ahead of time (AOT) to produce an executable binary. This binary is tailored for a particular target architecture, ensuring efficient execution.

## Language Grammer

The Code Compiler is designed to process input expressions following a specific grammar, which includes the following language elements:

- Show for Printing: You can use the Show statement to display values and results in your code.
  ```
  show "Hello World!";
  ```

- Let for Assigning Variables: The Let statement allows you to declare and assign values to variables in your code.
  ```
  let a = 123;
  ```

- +, -, *, / for Basic Arithmetic Operations: These operators are supported for performing arithmetic operations in your code.
  ```
  let result = 10 + 5 * 2 / 10 - 2;
  show result;

  output : 9.000000
  ```

- ; for ending statements and = for assigning variables/.



The supported language grammar allows you to create expressions and statements using these elements, making it suitable for a variety of coding tasks.

## Getting Started

### If you simply wish to run the program to see output follow these simple steps

> [!NOTE]
> You must have an Apple Silicon chip Mac to run this.

#### For other operating systems go to the Setup Project Section.


- Clone the repository
- Change the /input/input.txt
- Open terminal in project's root directory and run following command and you should see the output along with all the intermediate representation like Lexing, AST Nodes and LLVM IR.

  ```
  ./run.sh
  ```
#### Sample output

  Input
  ```
  let a = 222;
  let b = 2;
  show a + b;
  ```

  Output
  ```
  [LET | let]
  [IDENTIFIER | a]
  [ASSIGN | =]
  [NUMBER | 222]
  [ENDLINE | ;]
  [LET | let]
  [IDENTIFIER | b]
  [ASSIGN | =]
  [NUMBER | 2]
  [ENDLINE | ;]
  [SHOW | show]
  [IDENTIFIER | a]
  [ADD | +]
  [IDENTIFIER | b]
  [ENDLINE | ;]
  a
  b
  Scope Start
  Name: a, Type: NUMBER
  Name: b, Type: NUMBER
  Scope End
  (
     =
     a
  (
        222.0
     )
  )
  (
     =
     b
  (
        2.0
     )
  )
  (
     show
  (
        ADD
  (
           a
        )
  (
           b
        )
     )
  )
  Semantic analysis completed successfully.
  Generated Ir: 
  (
   a 
   222 
  )
  
  (
   b 
   2 
  )
  
  (
   a ADD b 
  )
  
  [ 5 | let ]
  [ 2 | a ]
  [ 11 | = ]
  [ 0 | 222 ]
  [ 4 | ; ]
  [ 5 | let ]
  [ 2 | b ]
  [ 11 | = ]
  [ 0 | 2 ]
  [ 4 | ; ]
  [ 6 | show ]
  [ 2 | a ]
  [ 7 | ADD ]
  [ 2 | b ]
  [ 4 | ; ]
  ; ModuleID = 'Main Module'
  source_filename = "Main Module"
  target triple = "arm64-apple-macosx14.0.0"
  
  @a = global i32 222
  @b = global i32 2
  @.str = private constant [4 x i8] c"%d\0A\00"
  
  declare i32 @printf(ptr, ...)
  
  declare i64 @strlen(ptr)
  
  declare ptr @memcpy(ptr, ptr, i64, i32, i1)
  
  declare ptr @malloc(i64)
  
  define i32 @main() {
  entry:
    %0 = load i32, ptr @a, align 4
    %1 = load i32, ptr @b, align 4
    %2 = add i32 %0, %1
    %3 = call i32 (ptr, ...) @printf(ptr @.str, i32 %2)
    ret i32 0
  }
  224
  ```

### Setup Project
#### Prerequisites

- Install LLVM: Download and install the latest version of LLVM onto your machine. You can get LLVM from the official website: [llvm.org](https://releases.llvm.org/download.html). Make sure to follow the installation instructions for your specific platform.

- Install CMake: Ensure that you have CMake installed on your machine. CMake is often used to build projects that use LLVM. You can download CMake from the official website: [cmake.org](https://cmake.org) or use some package manager.

Clone this repository to your local machine.

#### Configuration

Setting Paths: 
- After installing LLVM, locate the llvm-config executable on your system. Update the path to llvm-config in the ../cpp_src/Compiler/run.sh script. Open the script and replace the placeholder LLVM_CONFIG_PATH with the actual path to your llvm-config executable along with other paths mentioned as /path_to/.
- In the same ../cpp_src/Compiler/run.sh file change below line to create your own shared library,

  For example -> In windows you would name your shared file like this: *libNodeBridge.dll*
  ```
  g++ -shared -o libNodeBridge.dylib \
  ```

- set correct path in
  - cpp_src/Compiler/Compiler/scripts/compile.sh
  - cpp_src/Compiler/Compiler/bridge/bridge/JavaBridge.cpp
  - cpp_src/Compiler/Compiler/llvm_codegen/LLVMCodeGen.cpp & LLVMCodeGen.h

Running the Compiler
Once you've completed the configuration steps, you're ready to compile and execute code using this project:

Edit Your Code: Modify the code you want to compile and execute in the input/input.txt file. Expressions and statements can be added or modified in this file as needed.

Run the Compiler: Open a terminal and navigate to the root directory of the project. To compile and execute the code, simply run the run.sh script. You can do this by entering the following command:

```
./run.sh
```

View the Result: The compiler will process the code in input/input.txt, generate LLVM IR, and compile it. You will see the result for the code in input/input.txt printed to your terminal.

With these steps, you can clone and run this project on your machine. By providing the necessary configuration and editing the input file, you can compile and execute code using LLVM's capabilities, which offer flexibility and efficiency in code compilation and execution.


