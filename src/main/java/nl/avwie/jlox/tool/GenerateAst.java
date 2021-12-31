package nl.avwie.jlox.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GenerateAst {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }

        var outputDir = args[0];
        defineAst(outputDir, "Expr", List.of(
                "Binary   : Expr left, Token operator, Expr right",
                "Grouping : Expr expression",
                "Literal  : Object value",
                "Unary    : Token operator, Expr right"
        ));
    }

    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
        var path = outputDir + "/" + baseName + ".java";
        var writer = new PrintWriter(path, StandardCharsets.UTF_8);

        writer.println("package nl.avwie.jlox.parser;");
        writer.println();
        writer.println("import nl.avwie.jlox.scanner.Token;");
        writer.println();
        writer.println("public abstract class " + baseName + " {");

        defineVisitor(writer, baseName, types);
        writer.println();
        writer.println("  public abstract <R> R accept(Visitor<R> visitor);");
        writer.println();

        // the AST classes
        types.forEach(type -> {
            var className = type.split(":")[0].trim();
            var fields = type.split(":")[1].trim();
            defineType(writer, baseName, className, fields);
        });

        writer.println("}");
        writer.close();
    }

    private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {
        var fields = fieldList.split(", ");

        writer.println("  public static class " + className + " extends " + baseName + " {");
        writer.println();
        for (var field : fields) {
            writer.println("    public final " + field + ";");
        }
        writer.println();

        writer.println("    " + className + "(" + fieldList + ") {");
        for (var field : fields) {
            var name = field.split(" ")[1];
            writer.println("      this." + name + " = " + name + ";");
        }
        writer.println("    }");

        writer.println();
        writer.println("    @Override");
        writer.println("    public <R> R accept(Visitor<R> visitor) {");
        writer.println("      return visitor.visit(this);");
        writer.println("    }");

        writer.println("  }");
        writer.println();
    }

    private static void defineVisitor(PrintWriter writer, String baseName, List<String> types) {
        writer.println("  public interface Visitor<R> {");
        types.forEach(type -> {
            var typeName = type.split(":")[0].trim();
            writer.println("    R visit(" + typeName + " " + baseName.toLowerCase() + ");");
        });
        writer.println("  }");
    }
}
