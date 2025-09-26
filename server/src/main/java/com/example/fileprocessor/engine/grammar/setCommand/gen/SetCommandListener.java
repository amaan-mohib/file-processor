// Generated from /home/amaan/projects/file-processor/server/src/main/java/com/example/fileprocessor/engine/grammar/setCommand/SetCommand.g4 by ANTLR 4.13.2
package com.example.fileprocessor.engine.grammar.setCommand.gen;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SetCommandParser}.
 */
public interface SetCommandListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SetCommandParser#setStatement}.
	 * @param ctx the parse tree
	 */
	void enterSetStatement(SetCommandParser.SetStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SetCommandParser#setStatement}.
	 * @param ctx the parse tree
	 */
	void exitSetStatement(SetCommandParser.SetStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TargetIdentifier}
	 * labeled alternative in {@link SetCommandParser#target}.
	 * @param ctx the parse tree
	 */
	void enterTargetIdentifier(SetCommandParser.TargetIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TargetIdentifier}
	 * labeled alternative in {@link SetCommandParser#target}.
	 * @param ctx the parse tree
	 */
	void exitTargetIdentifier(SetCommandParser.TargetIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TargetPath}
	 * labeled alternative in {@link SetCommandParser#target}.
	 * @param ctx the parse tree
	 */
	void enterTargetPath(SetCommandParser.TargetPathContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TargetPath}
	 * labeled alternative in {@link SetCommandParser#target}.
	 * @param ctx the parse tree
	 */
	void exitTargetPath(SetCommandParser.TargetPathContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMulExpr(SetCommandParser.MulExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMulExpr(SetCommandParser.MulExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(SetCommandParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(SetCommandParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SubExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubExpr(SetCommandParser.SubExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SubExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubExpr(SetCommandParser.SubExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GtExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterGtExpr(SetCommandParser.GtExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GtExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitGtExpr(SetCommandParser.GtExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LtExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLtExpr(SetCommandParser.LtExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LtExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLtExpr(SetCommandParser.LtExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddExpr(SetCommandParser.AddExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddExpr(SetCommandParser.AddExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(SetCommandParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(SetCommandParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LteExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLteExpr(SetCommandParser.LteExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LteExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLteExpr(SetCommandParser.LteExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GteExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterGteExpr(SetCommandParser.GteExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GteExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitGteExpr(SetCommandParser.GteExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DivExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDivExpr(SetCommandParser.DivExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DivExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDivExpr(SetCommandParser.DivExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code JsonValueExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterJsonValueExpr(SetCommandParser.JsonValueExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code JsonValueExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitJsonValueExpr(SetCommandParser.JsonValueExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdentifierExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpr(SetCommandParser.IdentifierExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdentifierExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpr(SetCommandParser.IdentifierExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EqExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEqExpr(SetCommandParser.EqExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EqExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEqExpr(SetCommandParser.EqExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NeqExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNeqExpr(SetCommandParser.NeqExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NeqExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNeqExpr(SetCommandParser.NeqExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLiteralExpr(SetCommandParser.LiteralExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLiteralExpr(SetCommandParser.LiteralExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(SetCommandParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(SetCommandParser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PathExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPathExpr(SetCommandParser.PathExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PathExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPathExpr(SetCommandParser.PathExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link SetCommandParser#pathExpression}.
	 * @param ctx the parse tree
	 */
	void enterPathExpression(SetCommandParser.PathExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SetCommandParser#pathExpression}.
	 * @param ctx the parse tree
	 */
	void exitPathExpression(SetCommandParser.PathExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code JsonObject}
	 * labeled alternative in {@link SetCommandParser#jsonValue}.
	 * @param ctx the parse tree
	 */
	void enterJsonObject(SetCommandParser.JsonObjectContext ctx);
	/**
	 * Exit a parse tree produced by the {@code JsonObject}
	 * labeled alternative in {@link SetCommandParser#jsonValue}.
	 * @param ctx the parse tree
	 */
	void exitJsonObject(SetCommandParser.JsonObjectContext ctx);
	/**
	 * Enter a parse tree produced by the {@code JsonArray}
	 * labeled alternative in {@link SetCommandParser#jsonValue}.
	 * @param ctx the parse tree
	 */
	void enterJsonArray(SetCommandParser.JsonArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code JsonArray}
	 * labeled alternative in {@link SetCommandParser#jsonValue}.
	 * @param ctx the parse tree
	 */
	void exitJsonArray(SetCommandParser.JsonArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link SetCommandParser#pair}.
	 * @param ctx the parse tree
	 */
	void enterPair(SetCommandParser.PairContext ctx);
	/**
	 * Exit a parse tree produced by {@link SetCommandParser#pair}.
	 * @param ctx the parse tree
	 */
	void exitPair(SetCommandParser.PairContext ctx);
	/**
	 * Enter a parse tree produced by {@link SetCommandParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(SetCommandParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link SetCommandParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(SetCommandParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link SetCommandParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(SetCommandParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SetCommandParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(SetCommandParser.IdentifierContext ctx);
}