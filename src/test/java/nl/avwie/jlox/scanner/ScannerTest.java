package nl.avwie.jlox.scanner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScannerTest {

    @Test
    void scanEmptyToken() {
        var tokens = new Scanner("").scanTokens();
        assertEquals(1, tokens.size());
        assertEquals(TokenType.EOF, tokens.get(0).type);
    }

    @Test
    void scanComments() {
        var tokens = new Scanner("// this is a comment").scanTokens();
        assertEquals(1, tokens.size());
        assertEquals(TokenType.EOF, tokens.get(0).type);
    }

    @Test
    void scanGroupings() {
        var tokens = new Scanner("(( )){} // grouping stuff").scanTokens();
        assertEquals(7, tokens.size());
        assertEquals(TokenType.LEFT_PAREN, tokens.get(0).type);
        assertEquals(TokenType.LEFT_PAREN, tokens.get(1).type);
        assertEquals(TokenType.RIGHT_PAREN, tokens.get(2).type);
        assertEquals(TokenType.RIGHT_PAREN, tokens.get(3).type);

        assertEquals(TokenType.LEFT_BRACE, tokens.get(4).type);
        assertEquals(TokenType.RIGHT_BRACE, tokens.get(5).type);
        assertEquals(TokenType.EOF, tokens.get(6).type);
    }

    @Test
    void scanKeywordsAndLiterals() {
        var tokens = new Scanner("var x = \"Foobar\"").scanTokens();
        assertEquals(TokenType.VAR, tokens.get(0).type);
        assertEquals(TokenType.IDENTIFIER, tokens.get(1).type);
        assertEquals(TokenType.EQUAL, tokens.get(2).type);
        assertEquals(TokenType.STRING, tokens.get(3).type);
    }

    @Test
    void scanNumbers() {
        var tokens = new Scanner("123.45").scanTokens();
        assertEquals(TokenType.NUMBER, tokens.get(0).type);
    }
}