import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import codegen.IrGenerator;
import codegen.IrToJava;
import codegen.irnodes.IrNode;
import codegen.irnodes.IrPrinter;
import codegen.irnodes.IrVisitor;
import error.SemanticException;
import lexer.Lexer;
import lexer.Token;
import parser.Parser;
import parser.nodes.AstNode;
import parser.nodes.AstPrinter;
import parser.nodes.AstVisitor;
import semantic.SemanticAnalyzer;
import semantic.SymbolTable;

public class Main {
    public static void main(String args[]) {
        String inputFilePath = "input/input.txt";
        String input = readInputFromFile(inputFilePath);

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        SymbolTable symbolTable = new SymbolTable();
        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(symbolTable);

        // parse to token
        Parser parse = new Parser(tokens, symbolTable);

        for (Token token : tokens) {
            System.out.println(token);
        }

        // syntactical check
        AstNode rootNode = parse.parse();

        AstVisitor astPrinter = new AstPrinter();
        rootNode.accept(astPrinter);

        // semanticl check
        try {
            semanticAnalyzer.analyze(rootNode);
            System.out.println("Semantic analysis completed successfully.");

            IrGenerator irGenerator = new IrGenerator();
            IrNode irRootNode = irGenerator.generateIr(rootNode);

            System.out.println("Generated Ir: ");
            IrVisitor irPrinter = new IrPrinter();
            irRootNode.accept(irPrinter);

            String javaCode = IrToJava.convertIrToJava(irRootNode);

            try (FileWriter writer = new FileWriter("output/GeneratedCode.java")) {
                writer.write(javaCode.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Runnning output script
            ProcessBuilder processBuilder = new ProcessBuilder("./src/script/run_generated_code.sh");
            processBuilder.redirectErrorStream(true);
            try {
                Process process = processBuilder.start();
                int exitCode = process.waitFor();
                System.out.println("Process exited with code: " + exitCode);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        } catch (SemanticException e) {
            System.err.println("Semantic error: " + e.getMessage());
        }

    }

    private static String readInputFromFile(String filePath) {
        StringBuilder input = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                input.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input.toString();
    }
}