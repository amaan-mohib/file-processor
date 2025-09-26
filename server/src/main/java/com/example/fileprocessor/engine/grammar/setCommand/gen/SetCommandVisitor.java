// Generated from /home/amaan/projects/file-processor/server/src/main/java/com/example/fileprocessor/engine/grammar/setCommand/SetCommand.g4 by ANTLR 4.13.2
package com.example.fileprocessor.engine.grammar.setCommand.gen;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SetCommandParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SetCommandVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SetCommandParser#setStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetStatement(SetCommandParser.SetStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TargetIdentifier}
	 * labeled alternative in {@link SetCommandParser#target}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTargetIdentifier(SetCommandParser.TargetIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TargetPath}
	 * labeled alternative in {@link SetCommandParser#target}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTargetPath(SetCommandParser.TargetPathContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulExpr(SetCommandParser.MulExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(SetCommandParser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SubExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubExpr(SetCommandParser.SubExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GtExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGtExpr(SetCommandParser.GtExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LtExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLtExpr(SetCommandParser.LtExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddExpr(SetCommandParser.AddExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpr(SetCommandParser.OrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LteExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLteExpr(SetCommandParser.LteExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GteExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGteExpr(SetCommandParser.GteExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DivExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDivExpr(SetCommandParser.DivExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code JsonValueExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonValueExpr(SetCommandParser.JsonValueExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdentifierExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierExpr(SetCommandParser.IdentifierExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqExpr(SetCommandParser.EqExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NeqExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNeqExpr(SetCommandParser.NeqExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralExpr(SetCommandParser.LiteralExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(SetCommandParser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PathExpr}
	 * labeled alternative in {@link SetCommandParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathExpr(SetCommandParser.PathExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SetCommandParser#pathExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathExpression(SetCommandParser.PathExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code JsonObject}
	 * labeled alternative in {@link SetCommandParser#jsonValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonObject(SetCommandParser.JsonObjectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code JsonArray}
	 * labeled alternative in {@link SetCommandParser#jsonValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonArray(SetCommandParser.JsonArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link SetCommandParser#pair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPair(SetCommandParser.PairContext ctx);
	/**
	 * Visit a parse tree produced by {@link SetCommandParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(SetCommandParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link SetCommandParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(SetCommandParser.IdentifierContext ctx);
}