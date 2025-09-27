// Generated from /home/amaan/projects/file-processor/server/src/main/java/com/example/fileprocessor/engine/grammar/FileQuery.g4 by ANTLR 4.13.2
package com.example.fileprocessor.engine.grammar.gen;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FileQueryParser}.
 */
public interface FileQueryListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(FileQueryParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(FileQueryParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(FileQueryParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(FileQueryParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TargetIdentifier}
	 * labeled alternative in {@link FileQueryParser#target}.
	 * @param ctx the parse tree
	 */
	void enterTargetIdentifier(FileQueryParser.TargetIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TargetIdentifier}
	 * labeled alternative in {@link FileQueryParser#target}.
	 * @param ctx the parse tree
	 */
	void exitTargetIdentifier(FileQueryParser.TargetIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TargetPath}
	 * labeled alternative in {@link FileQueryParser#target}.
	 * @param ctx the parse tree
	 */
	void enterTargetPath(FileQueryParser.TargetPathContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TargetPath}
	 * labeled alternative in {@link FileQueryParser#target}.
	 * @param ctx the parse tree
	 */
	void exitTargetPath(FileQueryParser.TargetPathContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#pathExpression}.
	 * @param ctx the parse tree
	 */
	void enterPathExpression(FileQueryParser.PathExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#pathExpression}.
	 * @param ctx the parse tree
	 */
	void exitPathExpression(FileQueryParser.PathExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#selectStatement}.
	 * @param ctx the parse tree
	 */
	void enterSelectStatement(FileQueryParser.SelectStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#selectStatement}.
	 * @param ctx the parse tree
	 */
	void exitSelectStatement(FileQueryParser.SelectStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#setStatement}.
	 * @param ctx the parse tree
	 */
	void enterSetStatement(FileQueryParser.SetStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#setStatement}.
	 * @param ctx the parse tree
	 */
	void exitSetStatement(FileQueryParser.SetStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#removeStatement}.
	 * @param ctx the parse tree
	 */
	void enterRemoveStatement(FileQueryParser.RemoveStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#removeStatement}.
	 * @param ctx the parse tree
	 */
	void exitRemoveStatement(FileQueryParser.RemoveStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#deleteStatement}.
	 * @param ctx the parse tree
	 */
	void enterDeleteStatement(FileQueryParser.DeleteStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#deleteStatement}.
	 * @param ctx the parse tree
	 */
	void exitDeleteStatement(FileQueryParser.DeleteStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#filterStatement}.
	 * @param ctx the parse tree
	 */
	void enterFilterStatement(FileQueryParser.FilterStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#filterStatement}.
	 * @param ctx the parse tree
	 */
	void exitFilterStatement(FileQueryParser.FilterStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#insertStatement}.
	 * @param ctx the parse tree
	 */
	void enterInsertStatement(FileQueryParser.InsertStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#insertStatement}.
	 * @param ctx the parse tree
	 */
	void exitInsertStatement(FileQueryParser.InsertStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(FileQueryParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(FileQueryParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#uppercase}.
	 * @param ctx the parse tree
	 */
	void enterUppercase(FileQueryParser.UppercaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#uppercase}.
	 * @param ctx the parse tree
	 */
	void exitUppercase(FileQueryParser.UppercaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#lowercase}.
	 * @param ctx the parse tree
	 */
	void enterLowercase(FileQueryParser.LowercaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#lowercase}.
	 * @param ctx the parse tree
	 */
	void exitLowercase(FileQueryParser.LowercaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#length}.
	 * @param ctx the parse tree
	 */
	void enterLength(FileQueryParser.LengthContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#length}.
	 * @param ctx the parse tree
	 */
	void exitLength(FileQueryParser.LengthContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionIdExpr}
	 * labeled alternative in {@link FileQueryParser#functionArgs}.
	 * @param ctx the parse tree
	 */
	void enterFunctionIdExpr(FileQueryParser.FunctionIdExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionIdExpr}
	 * labeled alternative in {@link FileQueryParser#functionArgs}.
	 * @param ctx the parse tree
	 */
	void exitFunctionIdExpr(FileQueryParser.FunctionIdExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionStrExpr}
	 * labeled alternative in {@link FileQueryParser#functionArgs}.
	 * @param ctx the parse tree
	 */
	void enterFunctionStrExpr(FileQueryParser.FunctionStrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionStrExpr}
	 * labeled alternative in {@link FileQueryParser#functionArgs}.
	 * @param ctx the parse tree
	 */
	void exitFunctionStrExpr(FileQueryParser.FunctionStrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionPathExpr}
	 * labeled alternative in {@link FileQueryParser#functionArgs}.
	 * @param ctx the parse tree
	 */
	void enterFunctionPathExpr(FileQueryParser.FunctionPathExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionPathExpr}
	 * labeled alternative in {@link FileQueryParser#functionArgs}.
	 * @param ctx the parse tree
	 */
	void exitFunctionPathExpr(FileQueryParser.FunctionPathExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#columnList}.
	 * @param ctx the parse tree
	 */
	void enterColumnList(FileQueryParser.ColumnListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#columnList}.
	 * @param ctx the parse tree
	 */
	void exitColumnList(FileQueryParser.ColumnListContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(FileQueryParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(FileQueryParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#valueList}.
	 * @param ctx the parse tree
	 */
	void enterValueList(FileQueryParser.ValueListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#valueList}.
	 * @param ctx the parse tree
	 */
	void exitValueList(FileQueryParser.ValueListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ComparisonExpr}
	 * labeled alternative in {@link FileQueryParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparisonExpr(FileQueryParser.ComparisonExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ComparisonExpr}
	 * labeled alternative in {@link FileQueryParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparisonExpr(FileQueryParser.ComparisonExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ValueExpr}
	 * labeled alternative in {@link FileQueryParser#common}.
	 * @param ctx the parse tree
	 */
	void enterValueExpr(FileQueryParser.ValueExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ValueExpr}
	 * labeled alternative in {@link FileQueryParser#common}.
	 * @param ctx the parse tree
	 */
	void exitValueExpr(FileQueryParser.ValueExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdentifierExpr}
	 * labeled alternative in {@link FileQueryParser#common}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpr(FileQueryParser.IdentifierExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdentifierExpr}
	 * labeled alternative in {@link FileQueryParser#common}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpr(FileQueryParser.IdentifierExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PathExpr}
	 * labeled alternative in {@link FileQueryParser#common}.
	 * @param ctx the parse tree
	 */
	void enterPathExpr(FileQueryParser.PathExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PathExpr}
	 * labeled alternative in {@link FileQueryParser#common}.
	 * @param ctx the parse tree
	 */
	void exitPathExpr(FileQueryParser.PathExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionExpr}
	 * labeled alternative in {@link FileQueryParser#common}.
	 * @param ctx the parse tree
	 */
	void enterFunctionExpr(FileQueryParser.FunctionExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionExpr}
	 * labeled alternative in {@link FileQueryParser#common}.
	 * @param ctx the parse tree
	 */
	void exitFunctionExpr(FileQueryParser.FunctionExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#conditionExpr}.
	 * @param ctx the parse tree
	 */
	void enterConditionExpr(FileQueryParser.ConditionExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#conditionExpr}.
	 * @param ctx the parse tree
	 */
	void exitConditionExpr(FileQueryParser.ConditionExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CommonExpr}
	 * labeled alternative in {@link FileQueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCommonExpr(FileQueryParser.CommonExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CommonExpr}
	 * labeled alternative in {@link FileQueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCommonExpr(FileQueryParser.CommonExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link FileQueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(FileQueryParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link FileQueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(FileQueryParser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OpExpr}
	 * labeled alternative in {@link FileQueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOpExpr(FileQueryParser.OpExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OpExpr}
	 * labeled alternative in {@link FileQueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOpExpr(FileQueryParser.OpExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(FileQueryParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(FileQueryParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(FileQueryParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(FileQueryParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code JsonPairExpr}
	 * labeled alternative in {@link FileQueryParser#jsonValue}.
	 * @param ctx the parse tree
	 */
	void enterJsonPairExpr(FileQueryParser.JsonPairExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code JsonPairExpr}
	 * labeled alternative in {@link FileQueryParser#jsonValue}.
	 * @param ctx the parse tree
	 */
	void exitJsonPairExpr(FileQueryParser.JsonPairExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code JsonValueExpr}
	 * labeled alternative in {@link FileQueryParser#jsonValue}.
	 * @param ctx the parse tree
	 */
	void enterJsonValueExpr(FileQueryParser.JsonValueExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code JsonValueExpr}
	 * labeled alternative in {@link FileQueryParser#jsonValue}.
	 * @param ctx the parse tree
	 */
	void exitJsonValueExpr(FileQueryParser.JsonValueExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#pair}.
	 * @param ctx the parse tree
	 */
	void enterPair(FileQueryParser.PairContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#pair}.
	 * @param ctx the parse tree
	 */
	void exitPair(FileQueryParser.PairContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#comparator}.
	 * @param ctx the parse tree
	 */
	void enterComparator(FileQueryParser.ComparatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#comparator}.
	 * @param ctx the parse tree
	 */
	void exitComparator(FileQueryParser.ComparatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#arithmetic}.
	 * @param ctx the parse tree
	 */
	void enterArithmetic(FileQueryParser.ArithmeticContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#arithmetic}.
	 * @param ctx the parse tree
	 */
	void exitArithmetic(FileQueryParser.ArithmeticContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#logical}.
	 * @param ctx the parse tree
	 */
	void enterLogical(FileQueryParser.LogicalContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#logical}.
	 * @param ctx the parse tree
	 */
	void exitLogical(FileQueryParser.LogicalContext ctx);
	/**
	 * Enter a parse tree produced by {@link FileQueryParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterOperator(FileQueryParser.OperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link FileQueryParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitOperator(FileQueryParser.OperatorContext ctx);
}