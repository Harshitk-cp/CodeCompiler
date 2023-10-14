#!/bin/sh

cd ..
clang++ -c -target arm64-apple-macos -o output.o main.ll
clang -arch arm64 -o exe_program output.o
./exe_program