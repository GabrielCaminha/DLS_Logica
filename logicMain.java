import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

class logicMain {
    static String generateCode(ParseTree tree) {
        String opName = opName(tree);

        switch (opName) {
            case "Program":
                LogicParser.ProgramContext program = (LogicParser.ProgramContext) tree;
                StringBuilder code = new StringBuilder();

                for (LogicParser.StatementContext statement : program.statement()) {
                    code.append(generateCode(statement)).append("\n");
                }

                return code.toString();
            case "Statement":
                LogicParser.StatementContext statement = (LogicParser.StatementContext) tree;
                String circuitName = statement.ID().getText();
                String expressionCode = generateCode(statement.expression());
                String ifCode = String.format("if (%s) {", expressionCode);
                String elseCode = "} else {\n  // Condição não atendida\n}";
                String conditionMet = "\n  // Condição atendida\n";
                return String.format("O circuito %s pode ser representado em:\n%s%s\n%s\n",
                        circuitName, ifCode, conditionMet, elseCode);
            case "And":
                String leftAnd = generateCode(tree.getChild(0));
                String rightAnd = generateCode(tree.getChild(2));
                return String.format("(%s && %s)", leftAnd, rightAnd);
            case "Or":
                String leftOr = generateCode(tree.getChild(0));
                String rightOr = generateCode(tree.getChild(2));
                return String.format("(%s || %s)", leftOr, rightOr);
            case "Xor":
                String leftXor = generateCode(tree.getChild(0));
                String rightXor = generateCode(tree.getChild(2));
                return String.format("(%s ^ %s)", leftXor, rightXor);
            case "Not":
                String notExpr = generateCode(tree.getChild(1)); // Processar a subexpressão após o "NOT"
                return String.format("(!%s)", notExpr);
            case "Ter":
                if (tree.getChildCount() == 1) {
                    return generateCode(tree.getChild(0));
                } else {
                    String leftTerm = generateCode(tree.getChild(0));
                    String rightTerm = generateCode(tree.getChild(2));
                    return String.format("(%s %s %s)", leftTerm, tree.getChild(1).getText(), rightTerm);
                }
            case "Term":
                LogicParser.TermContext term = (LogicParser.TermContext) tree;

                if (term.ID() != null) {
                    return term.ID().getText();
                } else {
                    return String.format("(%s)", generateCode(term.expression()));
                }
            default:
                return "";
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
