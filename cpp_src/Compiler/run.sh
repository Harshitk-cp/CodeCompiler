 cd path_to/CodeCompiler/cpp_src/Compiler
 g++ -shared -o libNodeBridge.dylib \
Compiler/bridge/bridge/JavaBridge.cpp \
Compiler/bridge/token/Token.cpp \
Compiler/bridge/parser/ParseTokenToAst.cpp \
Compiler/llvm_codegen/LLVMCodeGen.cpp \
$(LLVM_CONFIG_PATH --cxxflags) \
$(LLVM_CONFIG_PATH --ldflags) -frtti \
-I/path_to_jdk/Contents/Home/include \
-I/path_to_jdk/Home/include/darwin \
-I/path_to/CodeCompiler/cpp_src/Compiler/include -L/path_to_your_llvm_install/llvm-project/build/lib \
 -lLLVMCore -lLLVMSupport -lLLVMAArch64CodeGen -lLLVMCodeGen -lLLVMARMAsmParser -lLLVMAArch64AsmParser -lLLVMAArch64Desc -lLLVMAArch64Disassembler -lLLVMAArch64Info -lLLVMAArch64Utils -lLLVMARMCodeGen -lLLVMARMDesc -lLLVMARMDisassembler -lLLVMARMInfo -lLLVMARMUtils -lLLVMAggressiveInstCombine -lLLVMAnalysis -lLLVMAsmParser -lLLVMAsmPrinter -lLLVMBinaryFormat -lLLVMBitReader -lLLVMBitWriter -lLLVMMCA -lLLVMBitstreamReader -lLLVMCFGuard -lLLVMCFIVerify -lLLVMCodeGenTypes -lLLVMCoroutines -lLLVMCoverage -lLLVMDWARFLinker -lLLVMDWARFLinkerParallel -lLLVMDWP -lLLVMDebugInfoBTF -lLLVMDebugInfoCodeView -lLLVMDebugInfoDWARF -lLLVMDebugInfoGSYM -lLLVMDebugInfoLogicalView -lLLVMDebugInfoMSF -lLLVMDebugInfoPDB -lLLVMDebuginfod -lLLVMDemangle -lLLVMDiff -lLLVMDlltoolDriver -lLLVMExecutionEngine -lLLVMExegesis -lLLVMExegesisAArch64 -lLLVMExtensions -lLLVMFileCheck -lLLVMFrontendHLSL -lLLVMFrontendOpenACC -lLLVMFrontendOpenMP -lLLVMFuzzMutate -lLLVMFuzzerCLI -lLLVMGlobalISel -lLLVMIRPrinter -lLLVMIRReader -lLLVMInstCombine -lLLVMInstrumentation -lLLVMInterfaceStub -lLLVMInterpreter -lLLVMJITLink -lLLVMLTO -lLLVMLibDriver -lLLVMLineEditor -lLLVMLinker -lLLVMMCDisassembler -lLLVMMC -lLLVMMCJIT -lLLVMMCParser -lLLVMMIRParser -lLLVMObjCARCOpts -lLLVMObjCopy -lLLVMObject -lLLVMObjectYAML -lLLVMOption -lLLVMOrcJIT -lLLVMOrcShared -lLLVMOrcTargetProcess -lLLVMPasses -lLLVMProfileData -lLLVMRemarks -lLLVMRuntimeDyld -lLLVMScalarOpts -lLLVMSelectionDAG -lLLVMSymbolize -lLLVMTableGen -lLLVMTableGenCommon -lLLVMTableGenGlobalISel -lLLVMTarget -lLLVMTargetParser -lLLVMTextAPI -lLLVMTransformUtils -lLLVMVectorize -lLLVMWindowsDriver -lLLVMWindowsManifest -lLLVMXRay -lcurses -lLLVMipo
