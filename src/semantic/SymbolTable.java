package semantic;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import error.SemanticException;
import semantic.symbol.Symbol;

class Scope {
    Map<String, Symbol> symbols = new HashMap<>();
}

public class SymbolTable {

    private Stack<Scope> scopeStack = new Stack<>();

    public void enterScope() {
        scopeStack.push(new Scope());
    }

    public void exitScope() {
        if (!scopeStack.isEmpty()) {
            scopeStack.pop();
        }
    }

    public void printSymbolTable() {
        for (Scope scope : scopeStack) {
            System.out.println("Scope Start");
            for (Map.Entry<String, Symbol> entry : scope.symbols.entrySet()) {
                String name = entry.getKey();
                Symbol symbol = entry.getValue();
                System.out.println("Name: " + name + ", Type: " + symbol.getType());
            }
            System.out.println("Scope End");
        }
    }

    public void inset(Symbol symbol) {
        if (!scopeStack.isEmpty()) {
            if (isVariableDeclaredInCurrentScope(symbol.getName())) {
                throw new SemanticException("Variable already declared in current scope: " + symbol.getName());
            }
            scopeStack.peek().symbols.put(symbol.getName(), symbol);
        }
    }

    public Symbol lookup(String name) {
        for (Scope scope : scopeStack) {
            Symbol symbol = scope.symbols.get(name);
            if (symbol != null) {
                return symbol;
            }
        }
        return null;
    }

    public boolean isVariableDeclaredInCurrentScope(String name) {
        for (Scope scope : scopeStack) {
            System.out.println(name);
            return scope.symbols.containsKey(name);
        }
        return false;
    }

}
