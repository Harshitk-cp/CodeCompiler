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

## Getting Started

#### Prerequisites

- Install LLVM: Download and install the latest version of LLVM onto your machine. You can get LLVM from the official website: [llvm.org](https://releases.llvm.org/download.html). Make sure to follow the installation instructions for your specific platform.

- Install CMake: Ensure that you have CMake installed on your machine. CMake is often used to build projects that use LLVM. You can download CMake from the official website: [cmake.org](https://cmake.org) or use some package manager.

Clone this repository to your local machine.

#### Configuration

Set LLVM Path: After installing LLVM, locate the llvm-config executable on your system. Update the path to llvm-config in the cpp_src/Compiler/run.sh script. Open the script and replace the placeholder LLVM_CONFIG_PATH with the actual path to your llvm-config executable.

Running the Compiler
Once you've completed the configuration steps, you're ready to compile and execute code using this project:

Edit Your Code: Modify the code you want to compile and execute in the input/input.txt file. Expressions and statements can be added or modified in this file as needed.

Run the Compiler: Open a terminal and navigate to the root directory of the project. To compile and execute the code, simply run the run.sh script. You can do this by entering the following command:

```
./run.sh
```

View the Result: The compiler will process the code in input/input.txt, generate LLVM IR, and compile it. You will see the result for the code in input/input.txt printed to your terminal.

With these steps, you can clone and run this project on your machine. By providing the necessary configuration and editing the input file, you can compile and execute code using LLVM's capabilities, which offer flexibility and efficiency in code compilation and execution.


## Possible Failure Solutions

- While running this project, you may encounter certain issues or failures. Here are some common problems and their solutions:

Permission Error for Running run.sh: If you encounter a permission error when trying to execute the run.sh script, you can grant execute permissions to the script using the chmod command. Open your terminal and navigate to the project directory, then run:

```
chmod +x run.sh
```

After running this command, you should be able to execute the script without permission issues.


- Incorrect LLVM IR Output or Errors: If the generated LLVM IR is not producing the expected results or is throwing errors, you may need to adjust the target architecture settings in the project. To do this:

a. Open the cpp_src/Compiler/Compiler/llvm_codegen/LLVMCodeGen.h file in a text editor.

b. Look for the following line in the file:

```cpp
module->setTargetTriple("set_target_architecture_here");
```

c. Replace "set_target_architecture_here" with the appropriate LLVM target triple for your target architecture. You can find the correct target triple for your architecture on the LLVM website or in LLVM documentation.

d. Save the file and re-run the run.sh script to regenerate and compile the code with the corrected target architecture settings.

By addressing these possible failures and following the provided solutions, you should be able to successfully use the LLVM-Based Code Compiler and resolve common issues that may arise during the compilation and execution process.

