package nl.avwie.jlox.parser.visitor;

import nl.avwie.jlox.parser.Expr;
import nl.avwie.jlox.scanner.Token;
import nl.avwie.jlox.scanner.TokenType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AstPrinterTest {

    @Test
    void basicTest() {
        var expression = new Expr.Binary(
            new Expr.Unary(
                new Token(TokenType.MINUS, "-", null, 1),
                new Expr.Literal(123)
            ),
            new Token(TokenType.STAR, "*", null, 1),
            new Expr.Grouping(new Expr.Literal(45.67))
        );

        var result = new AstPrinter().print(expression);
        assertEquals("(* (- 123) (group 45.67))", result);
    }
}
