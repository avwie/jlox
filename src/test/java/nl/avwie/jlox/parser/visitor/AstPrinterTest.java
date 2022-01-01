package nl.avwie.jlox.parser.visitor;

import nl.avwie.jlox.parser.Expr;
import nl.avwie.jlox.parser.Parser;
import nl.avwie.jlox.scanner.Scanner;
import nl.avwie.jlox.scanner.Token;
import nl.avwie.jlox.scanner.TokenType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AstPrinterTest {

    @Test
    void rawTest() {
        var expression = new Expr.Binary(
            new Expr.Unary(
                new Token(TokenType.MINUS, "-", null, 1),
                new Expr.Literal(123.0)
            ),
            new Token(TokenType.STAR, "*", null, 1),
            new Expr.Grouping(new Expr.Literal(45.67))
        );

        var result = new AstPrinter().print(expression);
        assertEquals("(* (- 123.0) (group 45.67))", result);
    }

    @Test
    void parserTest() {
        var tokens = new Scanner("-123 * (45.67)").scanTokens();
        var parser = new Parser(tokens);
        var expression = parser.parse();
        var result = new AstPrinter().print(expression);
        assertEquals("(* (- 123.0) (group 45.67))", result);
    }
}
