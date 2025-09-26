package com.example.fileprocessor.engine.grammar.setCommand;

import com.example.fileprocessor.engine.grammar.setCommand.gen.SetCommandBaseVisitor;
import com.example.fileprocessor.engine.grammar.setCommand.gen.SetCommandParser;
import com.example.fileprocessor.exception.SyntaxException;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class EvalVisitor extends SetCommandBaseVisitor<Object> {
    public final Map<String, Object> row;

    @Override
    public Object visitIdentifierExpr(SetCommandParser.IdentifierExprContext ctx) {
        return row.get(ctx.identifier().getText());
    }

    @Override
    public Object visitAddExpr(SetCommandParser.AddExprContext ctx) {
        Object left = visit(ctx.expression(0));
        Object right = visit(ctx.expression(1));
        if (left instanceof Number && right instanceof Number) {
            return (((Number) left).doubleValue()) + (((Number) right).doubleValue());
        }
        return left.toString() + right.toString();
    }

    @Override
    public Object visitSubExpr(SetCommandParser.SubExprContext ctx) throws SyntaxException {
        Object left = visit(ctx.expression(0));
        Object right = visit(ctx.expression(1));
        if (left instanceof Number && right instanceof Number) {
            return (((Number) left).doubleValue()) - (((Number) right).doubleValue());
        }
        throw new SyntaxException("Subtraction is only supported for numeric types.");
    }

    @Override
    public Object visitMulExpr(SetCommandParser.MulExprContext ctx) throws SyntaxException {
        Object left = visit(ctx.expression(0));
        Object right = visit(ctx.expression(1));
        if (left instanceof Number && right instanceof Number) {
            return (((Number) left).doubleValue()) * (((Number) right).doubleValue());
        }
        throw new SyntaxException("Multiplication is only supported for numeric types.");
    }

    @Override
    public Object visitDivExpr(SetCommandParser.DivExprContext ctx) {
        Object left = visit(ctx.expression(0));
        Object right = visit(ctx.expression(1));
        if (left instanceof Number && right instanceof Number) {
            if (((Number) right).doubleValue() == 0) {
                throw new ArithmeticException("Division by zero.");
            }
            return (((Number) left).doubleValue()) / (((Number) right).doubleValue());
        }
        throw new SyntaxException("Division is only supported for numeric types.");
    }

    private Object getBooleanLike(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue() != 0;
        }
        if (obj instanceof String) {
            return !((String) obj).isEmpty();
        }
        return obj;
    }

    @Override
    public Boolean visitAndExpr(SetCommandParser.AndExprContext ctx) throws SyntaxException {
        Object left = getBooleanLike(visit(ctx.expression(0)));
        Object right = getBooleanLike(visit(ctx.expression(1)));
        if (left instanceof Boolean && right instanceof Boolean) {
            return (Boolean) left && (Boolean) right;
        }
        throw new SyntaxException("Logical AND is only supported for boolean like types.");
    }

    @Override
    public Boolean visitOrExpr(SetCommandParser.OrExprContext ctx) throws SyntaxException {
        Object left = getBooleanLike(visit(ctx.expression(0)));
        Object right = getBooleanLike(visit(ctx.expression(1)));
        if (left instanceof Boolean && right instanceof Boolean) {
            return (Boolean) left || (Boolean) right;
        }
        throw new SyntaxException("Logical OR is only supported for boolean like types.");
    }

    @Override
    public Boolean visitGtExpr(SetCommandParser.GtExprContext ctx) throws SyntaxException {
        Object left = visit(ctx.expression(0));
        Object right = visit(ctx.expression(1));
        if (left instanceof Number && right instanceof Number) {
            return ((Number) left).doubleValue() > ((Number) right).doubleValue();
        }
        if (left instanceof String && right instanceof String) {
            return ((String) left).compareTo((String) right) > 0;
        }
        throw new SyntaxException("Greater than comparison is only supported for numeric and string types.");
    }

    @Override
    public Boolean visitLtExpr(SetCommandParser.LtExprContext ctx) throws SyntaxException {
        Object left = visit(ctx.expression(0));
        Object right = visit(ctx.expression(1));
        if (left instanceof Number && right instanceof Number) {
            return ((Number) left).doubleValue() < ((Number) right).doubleValue();
        }
        if (left instanceof String && right instanceof String) {
            return ((String) left).compareTo((String) right) < 0;
        }
        throw new SyntaxException("Less than comparison is only supported for numeric and string types.");
    }

    @Override
    public Boolean visitGteExpr(SetCommandParser.GteExprContext ctx) throws SyntaxException {
        Object left = visit(ctx.expression(0));
        Object right = visit(ctx.expression(1));
        if (left instanceof Number && right instanceof Number) {
            return ((Number) left).doubleValue() >= ((Number) right).doubleValue();
        }
        if (left instanceof String && right instanceof String) {
            return ((String) left).compareTo((String) right) >= 0;
        }
        throw new SyntaxException("Greater than equal comparison is only supported for numeric and string types.");
    }

    @Override
    public Boolean visitLteExpr(SetCommandParser.LteExprContext ctx) throws SyntaxException {
        Object left = visit(ctx.expression(0));
        Object right = visit(ctx.expression(1));
        if (left instanceof Number && right instanceof Number) {
            return ((Number) left).doubleValue() <= ((Number) right).doubleValue();
        }
        if (left instanceof String && right instanceof String) {
            return ((String) left).compareTo((String) right) <= 0;
        }
        throw new SyntaxException("Less than equal comparison is only supported for numeric and string types.");
    }

    @Override
    public Boolean visitEqExpr(SetCommandParser.EqExprContext ctx) {
        Object left = visit(ctx.expression(0));
        Object right = visit(ctx.expression(1));
        return left.equals(right);
    }

    @Override
    public Boolean visitNeqExpr(SetCommandParser.NeqExprContext ctx) {
        Object left = visit(ctx.expression(0));
        Object right = visit(ctx.expression(1));
        return !left.equals(right);
    }

    @Override
    public Object visitLiteralExpr(SetCommandParser.LiteralExprContext ctx) {
        var literal = ctx.literal();
        if (literal.STRING() != null) {
            String text = literal.STRING().getText();
            return text.replace("\"", "").replace("'", "");
        } else if (literal.NUMBER() != null) {
            String numStr = literal.NUMBER().getText();
            if (numStr.contains(".")) {
                return Double.parseDouble(numStr);
            } else {
                return Integer.parseInt(numStr);
            }
        } else if (literal.getText().equals("true") || literal.getText().equals("false")) {
            return Boolean.parseBoolean(literal.getText());
        }
        return null;
    }

    @Override
    public Object visitParenExpr(SetCommandParser.ParenExprContext ctx) {
        return visit(ctx.expression());
    }
}
