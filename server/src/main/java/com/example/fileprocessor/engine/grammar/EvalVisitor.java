package com.example.fileprocessor.engine.grammar;

import com.example.fileprocessor.engine.grammar.gen.FileQueryBaseVisitor;
import com.example.fileprocessor.engine.grammar.gen.FileQueryParser;
import com.example.fileprocessor.entity.FileMetadata;
import com.example.fileprocessor.util.GenericUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class EvalVisitor extends FileQueryBaseVisitor<Object> {
    private final Map<String, Object> row;
    private final List<String> headers;
    private final FileMetadata.FileType fileType;

    public EvalVisitor(Map<String, Object> row, FileMetadata.FileType fileType) {
        this.row = row;
        this.headers = new ArrayList<>();
        this.fileType = fileType;
    }

    public EvalVisitor(List<String> headers, FileMetadata.FileType fileType) {
        this.headers = headers;
        this.row = new HashMap<>();
        this.fileType = fileType;
    }

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
    public List<String> visitColumnList(FileQueryParser.ColumnListContext ctx) {
        boolean isAll = Objects.equals(ctx.getText(), "*");
        List<String> columns = new ArrayList<>();
        if (isAll) {
            return headers;
        } else {
            ctx.identifier().forEach(identifierContext -> columns.add(identifierContext.getText()));
        }
        return columns;
    }

    private Object getValue(FileQueryParser.ValueContext literal) {
        if (literal.STRING() != null) {
            String text = literal.STRING().getText();
            return text.replace("\"", "");
        } else if (literal.NUMBER() != null) {
            String numStr = literal.NUMBER().getText();
            if (numStr.contains(".")) {
                return Double.parseDouble(numStr);
            } else {
                return Long.parseLong(numStr);
            }
        } else if (literal.getText().equals("true") || literal.getText().equals("false")) {
            return Boolean.parseBoolean(literal.getText());
        } else if (literal.jsonValue() != null) {
            Object json = visit(literal.jsonValue());
            if (fileType != FileMetadata.FileType.CSV) {
                return json;
            } else {
                try {
                    return GenericUtil.getObjectAsString(json);
                } catch (JsonProcessingException e) {
                    throw new UnsupportedOperationException("Failed to convert JSON value to string.", e);
                }
            }
        }
        return null;
    }

    @Override
    public Object visitValue(FileQueryParser.ValueContext ctx) {
        return getValue(ctx);
    }

    @Override
    public Object visitValueExpr(FileQueryParser.ValueExprContext ctx) {
        var literal = ctx.value();
        return getValue(literal);
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
            case "==" -> cmp == 0;
            case "!=" -> cmp != 0;
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
                case "AND", "and" -> leftVal && rightVal;
                case "OR", "or" -> leftVal || rightVal;
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
    public Object visitConditionExpr(FileQueryParser.ConditionExprContext ctx) {
        return visit(ctx.common());
    }

    @Override
    public Boolean visitComparisonExpr(FileQueryParser.ComparisonExprContext ctx) {
        var left = visitConditionExpr(ctx.conditionExpr(0));
        var right = visitConditionExpr(ctx.conditionExpr(1));
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

    protected Object visitPath(FileQueryParser.PathExpressionContext ctx) {
        if(fileType.equals(FileMetadata.FileType.CSV)) {
            throw new UnsupportedOperationException("Path traversal is not supported for CSV files.");
        }
        return new PathResolverVisitor(row).visitPathExpression(ctx, false).current;
    }

    @Override
    public Object visitPathExpr(FileQueryParser.PathExprContext ctx) {
        return this.visitPath(ctx.pathExpression());
    }

    @Override
    public Object visitFunctionPathExpr(FileQueryParser.FunctionPathExprContext ctx) {
        return this.visitPath(ctx.pathExpression());
    }

    @Override
    public Object visitParenExpr(FileQueryParser.ParenExprContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Object visitValueOrId(FileQueryParser.ValueOrIdContext ctx) {
        if (ctx.value() != null) {
            return getValue(ctx.value());
        } else if (ctx.identifier() != null) {
            return getIdentifier(ctx.identifier());
        }
        return null;
    }

    @Override
    public Map<String, Object> visitPair(FileQueryParser.PairContext ctx) {
        String key = ctx.STRING().getText().replace("\"", "");
        Object value = visit(ctx.valueOrId());
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    @Override
    public Map<String, Object> visitJsonPairExpr(FileQueryParser.JsonPairExprContext ctx) {
        Map<String, Object> map = new HashMap<>();
        for (var pairContext : ctx.pair()) {
            map.putAll(visitPair(pairContext));
        }
        return map;
    }

    @Override
    public List<Object> visitJsonValueExpr(FileQueryParser.JsonValueExprContext ctx) {
        List<Object> list = new ArrayList<>();
        for (var valueContext : ctx.valueOrId()) {
            list.add(visitValueOrId(valueContext));
        }
        return list;
    }
}
