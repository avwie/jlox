package nl.avwie.jlox.parser;

import nl.avwie.jlox.scanner.Token;

public abstract class Expr {
  public interface Visitor<R> {
    R visit(Binary expr);
    R visit(Grouping expr);
    R visit(Literal expr);
    R visit(Unary expr);
  }

  public abstract <R> R accept(Visitor<R> visitor);

  public static class Binary extends Expr {

    public final Expr left;
    public final Token operator;
    public final Expr right;

    public Binary(Expr left, Token operator, Expr right) {
      this.left = left;
      this.operator = operator;
      this.right = right;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
      return visitor.visit(this);
    }
  }

  public static class Grouping extends Expr {

    public final Expr expression;

    public Grouping(Expr expression) {
      this.expression = expression;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
      return visitor.visit(this);
    }
  }

  public static class Literal extends Expr {

    public final Object value;

    public Literal(Object value) {
      this.value = value;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
      return visitor.visit(this);
    }
  }

  public static class Unary extends Expr {

    public final Token operator;
    public final Expr right;

    public Unary(Token operator, Expr right) {
      this.operator = operator;
      this.right = right;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
      return visitor.visit(this);
    }
  }

}
