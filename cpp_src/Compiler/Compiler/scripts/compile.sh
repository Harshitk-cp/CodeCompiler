#!/bin/sh

cd /path_to/CodeCompiler/cpp_src/Compiler/Compiler/
clang++ -c -target arm64-apple-macos -o output.o main.ll
clang -arch arm64 -o exe_program output.o
./exe_program