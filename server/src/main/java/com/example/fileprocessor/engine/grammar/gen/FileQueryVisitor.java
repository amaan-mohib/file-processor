// Generated from /home/amaan/projects/file-processor/server/src/main/java/com/example/fileprocessor/engine/grammar/FileQuery.g4 by ANTLR 4.13.2
package com.example.fileprocessor.engine.grammar.gen;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FileQueryParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface FileQueryVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuery(FileQueryParser.QueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(FileQueryParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TargetIdentifier}
	 * labeled alternative in {@link FileQueryParser#target}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTargetIdentifier(FileQueryParser.TargetIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TargetPath}
	 * labeled alternative in {@link FileQueryParser#target}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTargetPath(FileQueryParser.TargetPathContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#pathExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathExpression(FileQueryParser.PathExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#selectStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectStatement(FileQueryParser.SelectStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#setStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetStatement(FileQueryParser.SetStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#removeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRemoveStatement(FileQueryParser.RemoveStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#deleteStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeleteStatement(FileQueryParser.DeleteStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#filterStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterStatement(FileQueryParser.FilterStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#insertStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertStatement(FileQueryParser.InsertStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(FileQueryParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#uppercase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUppercase(FileQueryParser.UppercaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#lowercase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLowercase(FileQueryParser.LowercaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#length}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLength(FileQueryParser.LengthContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionIdExpr}
	 * labeled alternative in {@link FileQueryParser#functionArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionIdExpr(FileQueryParser.FunctionIdExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionStrExpr}
	 * labeled alternative in {@link FileQueryParser#functionArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionStrExpr(FileQueryParser.FunctionStrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionPathExpr}
	 * labeled alternative in {@link FileQueryParser#functionArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionPathExpr(FileQueryParser.FunctionPathExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#columnList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnList(FileQueryParser.ColumnListContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(FileQueryParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#valueList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueList(FileQueryParser.ValueListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ComparisonExpr}
	 * labeled alternative in {@link FileQueryParser#comparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonExpr(FileQueryParser.ComparisonExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ValueExpr}
	 * labeled alternative in {@link FileQueryParser#common}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueExpr(FileQueryParser.ValueExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdentifierExpr}
	 * labeled alternative in {@link FileQueryParser#common}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierExpr(FileQueryParser.IdentifierExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PathExpr}
	 * labeled alternative in {@link FileQueryParser#common}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathExpr(FileQueryParser.PathExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionExpr}
	 * labeled alternative in {@link FileQueryParser#common}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionExpr(FileQueryParser.FunctionExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#conditionExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionExpr(FileQueryParser.ConditionExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CommonExpr}
	 * labeled alternative in {@link FileQueryParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommonExpr(FileQueryParser.CommonExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link FileQueryParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(FileQueryParser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OpExpr}
	 * labeled alternative in {@link FileQueryParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpExpr(FileQueryParser.OpExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(FileQueryParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(FileQueryParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code JsonPairExpr}
	 * labeled alternative in {@link FileQueryParser#jsonValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonPairExpr(FileQueryParser.JsonPairExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code JsonValueExpr}
	 * labeled alternative in {@link FileQueryParser#jsonValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonValueExpr(FileQueryParser.JsonValueExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#pair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPair(FileQueryParser.PairContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#comparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparator(FileQueryParser.ComparatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#arithmetic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmetic(FileQueryParser.ArithmeticContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#logical}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical(FileQueryParser.LogicalContext ctx);
	/**
	 * Visit a parse tree produced by {@link FileQueryParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperator(FileQueryParser.OperatorContext ctx);
}