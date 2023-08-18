package optimization;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import parser.nodes.AssignmentNode;
import parser.nodes.AstNode;
import parser.nodes.IdentifierNode;
import parser.nodes.ProgramNode;

public class DeadCodeEliminator {
    public static void eliminateDeadCode(ProgramNode programNode) {
        Set<String> usedVariables = new HashSet<>();

        identifyUsedVariables(programNode, usedVariables);

        eliminateUnusedAssignments(programNode, usedVariables);

    }

    private static void eliminateUnusedAssignments(ProgramNode programNode, Set<String> usedVariables) {
        List<AstNode> statements = programNode.getStatements();
        statements.removeIf(statement -> {
            if (statement instanceof AssignmentNode) {
                String target = ((AssignmentNode) statement).getIdentifier();
                return !usedVariables.contains(target);
            }
            return false;
        });
    }

    private static void identifyUsedVariables(AstNode node, Set<String> usedVariables) {

        if (node instanceof IdentifierNode) {
            usedVariables.add(((IdentifierNode) node).getName());
        } else {
            for (AstNode child : node.getChildren()) {
                identifyUsedVariables(child, usedVariables);
            }
        }

    }
}
