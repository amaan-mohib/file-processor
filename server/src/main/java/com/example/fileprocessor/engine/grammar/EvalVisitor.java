package com.example.fileprocessor.engine.grammar;

import com.example.fileprocessor.engine.grammar.gen.FileQueryBaseVisitor;
import com.example.fileprocessor.engine.grammar.gen.FileQueryParser;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class EvalVisitor extends FileQueryBaseVisitor<Object> {
    private final Map<String, Object> row;

    private Object getIdentifier(FileQueryParser.IdentifierContext ctx) {
        return row.get(ctx.ID().getText());
    }

    @Override
    public Object visitIdentifierExpr(FileQueryParser.IdentifierExprContext ctx) {
        return getIdentifier(ctx.identifier());
    }

    @Override
    public Object visitTargetIdentifier(FileQueryParser.TargetIdentifierContext ctx) {
        return getIdentifier(ctx.identifier());
    }

    @Override
    public Object visitValueExpr(FileQueryParser.ValueExprContext ctx) {
        var literal = ctx.value();
        if (literal.STRING() != null) {
            String text = literal.STRING().getText();
            return text.replace("\"", "");
        } else if (literal.NUMBER() != null) {
            String numStr = literal.NUMBER().getText();
            if (numStr.contains(".")) {
                return Double.parseDouble(numStr);
            } else {
                return Integer.parseInt(numStr);
            }
        } else if (literal.getText().equals("true") || literal.getText().equals("false")) {
            return Boolean.parseBoolean(ctx.getText());
        }
        return null;
    }

    private Object arithmeticOperation(Object left, Object right, String operator) {
        if (left instanceof Number && right instanceof Number) {
            double leftVal = ((Number) left).doubleValue();
            double rightVal = ((Number) right).doubleValue();
            return switch (operator) {
                case "+" -> leftVal + rightVal;
                case "-" -> leftVal - rightVal;
                case "*" -> leftVal * rightVal;
                case "/" -> {
                    if (rightVal == 0) throw new ArithmeticException("Division by zero.");
                    yield leftVal / rightVal;
                }
                default -> throw new IllegalArgumentException("Unknown operator: " + operator);
            };
        } else if (operator.equals("+")) {
            return left.toString() + right.toString();
        } else {
            throw new IllegalArgumentException("Operator " + operator + " is only supported for numeric types.");
        }
    }

    private Boolean comparisonOperation(Object left, Object right, String operator) {
        int cmp;
        if (left instanceof Number && right instanceof Number) {
            double leftVal = ((Number) left).doubleValue();
            double rightVal = ((Number) right).doubleValue();
            cmp = Double.compare(leftVal, rightVal);
        } else if (left instanceof String && right instanceof String) {
            cmp = ((String) left).compareTo((String) right);
        } else if (operator.equals("==")) {
            return left.equals(right);
        } else if (operator.equals("!=")) {
            return !left.equals(right);
        } else {
            throw new IllegalArgumentException("Comparison is only supported between same types of Number or String.");
        }
        return switch (operator) {
            case ">" -> cmp > 0;
            case "<" -> cmp < 0;
            case ">=" -> cmp >= 0;
            case "<=" -> cmp <= 0;
            default -> throw new IllegalArgumentException("Unknown comparator: " + operator);
        };
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

    private Boolean logicalOperation(Object left, Object right, String operator) {
        if (left instanceof Boolean && right instanceof Boolean) {
            boolean leftVal = (Boolean) left;
            boolean rightVal = (Boolean) right;
            return switch (operator) {
                case "AND" -> leftVal && rightVal;
                case "OR" -> leftVal || rightVal;
                default -> throw new IllegalArgumentException("Unknown logical operator: " + operator);
            };
        } else {
            throw new IllegalArgumentException("Logical operations are only supported for boolean types.");
        }
    }

    @Override
    public Object visitOpExpr(FileQueryParser.OpExprContext ctx) {
        var left = visit(ctx.expression(0));
        var right = visit(ctx.expression(1));
        var operator = ctx.operator();
        if (operator.arithmetic() != null) {
            return arithmeticOperation(left, right, operator.arithmetic().getText());
        }
        if (operator.comparator() != null) {
            return comparisonOperation(left, right, operator.comparator().getText());
        }
        if (operator.logical() != null) {
            return logicalOperation(getBooleanLike(left), getBooleanLike(right), operator.logical().getText());
        }
        return super.visitOpExpr(ctx);
    }

    @Override
    public Object visitComparisonExpr(FileQueryParser.ComparisonExprContext ctx) {
        var left = visit(ctx.conditionExpr(0));
        var right = visit(ctx.conditionExpr(1));
        return comparisonOperation(left, right, ctx.comparator().getText());
    }

    @Override
    public Object visitCommonExpr(FileQueryParser.CommonExprContext ctx) {
        return visit(ctx.common());
    }



    @Override
    public Object visitUppercase(FileQueryParser.UppercaseContext ctx) {
        return visit(ctx.functionArgs()).toString().toUpperCase();
    }

    @Override
    public Object visitLowercase(FileQueryParser.LowercaseContext ctx) {
        return visit(ctx.functionArgs()).toString().toLowerCase();
    }

    @Override
    public Object visitLength(FileQueryParser.LengthContext ctx) {
        return visit(ctx.functionArgs()).toString().length();
    }

    @Override
    public Object visitFunctionIdExpr(FileQueryParser.FunctionIdExprContext ctx) {
        return getIdentifier(ctx.identifier());
    }

    @Override
    public Object visitFunctionStrExpr(FileQueryParser.FunctionStrExprContext ctx) {
        return ctx.STRING().getText().replace("\"", "");
    }

    @Override
    public Object visitFunctionPathExpr(FileQueryParser.FunctionPathExprContext ctx) {
        // TODO
        return super.visitFunctionPathExpr(ctx);
    }

    @Override
    public Object visitParenExpr(FileQueryParser.ParenExprContext ctx) {
        return visit(ctx.expression());
    }
}
