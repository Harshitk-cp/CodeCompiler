; ModuleID = 'Main Module'
source_filename = "Main Module"
target triple = "arm64-apple-macosx14.0.0"

@.str = private constant [4 x i8] c"%f\0A\00"

declare i32 @printf(ptr, ...)

define i32 @main() {
entry:
  %0 = call i32 (ptr, ...) @printf(ptr @.str, double 6.200000e+00)
  ret i32 0
}
