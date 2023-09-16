import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.HashSet;
import java.util.Set;

class LogicMain {
    static StringBuilder classCode = new StringBuilder();
    static Set<String> declaredCircuits = new HashSet<>();
    static Set<String> declaredVariables = new HashSet<>();

    static String generateCode(ParseTree tree) {
        String opName = opName(tree);

        switch (opName) {
            case "Program":
                classCode.append("public class Circuit {\n");

                LogicParser.ProgramContext program = (LogicParser.ProgramContext) tree;

                StringBuilder variables = new StringBuilder();

                for (LogicParser.StatementContext statement : program.statement()) {
                    String circuitName = statement.ID().getText();
                    if (!declaredCircuits.contains(circuitName)) {
                        variables.append(circuitName).append(", ");
                        declaredCircuits.add(circuitName);
                    }
                }

                for (LogicParser.StatementContext statement : program.statement()) {
                    findVariables(statement.expression());
                }

                for (String variable : declaredVariables) {
                    if (!declaredCircuits.contains(variable)) {
                        variables.append(variable).append(", ");
                    }
                }

                if (variables.length() > 0) {
                    variables.setLength(variables.length() - 2); // Remove a vírgula extra no final
                    classCode.append("    public static boolean ").append(variables).append(";\n");
                }

                for (LogicParser.StatementContext statement : program.statement()) {
                    generateCode(statement);
                }

                classCode.append("}\n");

                return classCode.toString();
            case "Statement":
                LogicParser.StatementContext statement = (LogicParser.StatementContext) tree;
                String circuitName = statement.ID().getText();
                String expressionCode = generateCode(statement.expression());
                String ifCode = String.format("if (%s) {", expressionCode);
                String elseCode = "} else {\n";
                elseCode += "            " + circuitName + " = false;\n";
                elseCode += "        }\n";

                classCode.append("    static void ").append("Circuito").append(circuitName).append("() {\n");
                classCode.append("        ").append(ifCode).append("\n");
                classCode.append("            ").append(circuitName).append(" = true;\n");
                classCode.append(elseCode);
                classCode.append("        }\n\n");

                return classCode.toString();
            case "And":
                String leftAnd = generateCode(tree.getChild(0));
                String rightAnd = generateCode(tree.getChild(2));
                return String.format("(%s && %s)", leftAnd, rightAnd);
            case "Or":
                String leftOr = generateCode(tree.getChild(0));
                String rightOr = generateCode(tree.getChild(2));
                return String.format("(%s || %s)", leftOr, rightOr);
            case "Xor":
                String left = generateCode(tree.getChild(0));
                String right = generateCode(tree.getChild(2));
                return String.format("(%s ^ %s)", left, right);
            case "Not":
                String notExpr = generateCode(tree.getChild(1));
                return String.format("(!%s)", notExpr);
            case "Ter":
                if (tree.getChildCount() == 1) {
                    return generateCode(tree.getChild(0));
                } else {
                    String leftTerm = generateCode(tree.getChild(0));
                    String rightTerm = generateCode(tree.getChild(2));
                    String operatorTerm = tree.getChild(1).getText();
                    return String.format("(%s %s %s)", leftTerm, operatorTerm, rightTerm);
                }
            case "Term":
                LogicParser.TermContext term = (LogicParser.TermContext) tree;

                if (term.ID() != null) {
                    String termName = term.ID().getText();
                    if (!declaredVariables.contains(termName) && !declaredCircuits.contains(termName)) {
                        declaredVariables.add(termName);
                    }
                    return termName;
                } else {
                    return String.format("(%s)", generateCode(term.expression()));
                }
            default:
                return "";
        }
    }

    static void findVariables(ParseTree tree) {
        String opName = opName(tree);

        switch (opName) {
            case "And":
                findVariables(tree.getChild(0));
                findVariables(tree.getChild(2));
            case "Or":
                findVariables(tree.getChild(0));
                findVariables(tree.getChild(2));
            case "Xor":
                findVariables(tree.getChild(0));
                findVariables(tree.getChild(2));
                break;
            case "Not":
                findVariables(tree.getChild(1));
                break;
            case "Ter":
                if (tree.getChildCount() == 1) {
                    findVariables(tree.getChild(0));
                } else {
                    findVariables(tree.getChild(0));
                    findVariables(tree.getChild(2));
                }
                break;
            case "Term":
                LogicParser.TermContext term = (LogicParser.TermContext) tree;

                if (term.ID() != null) {
                    String termName = term.ID().getText();
                    if (!declaredVariables.contains(termName) && !declaredCircuits.contains(termName)) {
                        declaredVariables.add(termName);
                    }
                } else {
                    findVariables(term.expression());
                }
                break;
            default:
                break;
        }
    }

    static String opName(ParseTree t) {
        String name = t.getClass().getName();

        if (name.contains("$")) {
            name = name.substring(name.indexOf("$") + 1);
            name = name.substring(0, name.indexOf("Context"));
            return name;
        } else {
            return "";
        }
    }

    public static void main(String args[]) throws Exception {
        CharStream stream = CharStreams.fromFileName("exemplo.logic"); // Substitua "exemplo.logic" pelo nome do seu arquivo de entrada
        LogicLexer lexer = new LogicLexer(stream);
        TokenStream tkStream = new CommonTokenStream(lexer);
        LogicParser parser = new LogicParser(tkStream);
        ParseTree tree = parser.program();

        if (tree != null) {
            System.out.println("Código gerado:");
            String code = generateCode(tree);
            System.out.println(code);
        } else {
            System.out.println("Erro ao obter a árvore de análise sintática.");
        }
    }
}
