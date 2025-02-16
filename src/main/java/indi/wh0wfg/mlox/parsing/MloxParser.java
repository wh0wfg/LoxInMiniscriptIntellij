package indi.wh0wfg.mlox.parsing;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.lang.SyntaxTreeBuilder;
import com.intellij.lang.impl.PsiBuilderImpl;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.tree.IElementType;
import indi.wh0wfg.mlox.language.psi.MloxElementType;

import static indi.wh0wfg.mlox.language.psi.MloxElementTypes.*;
import static indi.wh0wfg.mlox.language.psi.MloxTokenTypes.*;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class MloxParser implements PsiParser {
    private void synchronize(@NotNull PsiBuilder builder) {

        while (!builder.eof()) {
            if (builder.getTokenType() == EOL) {
                builder.advanceLexer();
                return;
            }

            if (RECOVER_SET.contains(builder.getTokenType())) return;
            if (builder.getTokenType() == IDENTIFIER) return;
            if (builder.getTokenType() == RIGHT_BRACE) return;
            if (builder.getTokenType() == LEFT_BRACE) return;

            builder.advanceLexer();
        }
    }

    @Override
    public @NotNull ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
//        builder.setDebugMode(true);
        final SyntaxTreeBuilder.Marker rootMarker = builder.mark();

        while (!builder.eof()) {
            parseDeclaration(builder);
        }

        rootMarker.done(root);
        return builder.getTreeBuilt();
    }

    private void parseDeclaration(@NotNull PsiBuilder builder) {
//        SyntaxTreeBuilder.Marker declaration = builder.mark();
        IElementType currentToken = builder.getTokenType();
        if (currentToken == CLASS) {
            if (!parseClassDeclaration(builder)) synchronize(builder);
            return;
        }
        if (currentToken == FUN) {
            if (!parseFunction(builder, "function")) synchronize(builder);
            return;
        }
        if (currentToken == VAR) {
            if (!parseVarDeclaration(builder)) synchronize(builder);
            return;
        }

        if (!parseStatement(builder)) synchronize(builder);
//        declaration.drop();
    }

    private boolean parseClassDeclaration(@NotNull PsiBuilder builder) {
        builder.advanceLexer(); // skip class token
        SyntaxTreeBuilder.Marker classStmt = builder.mark();

        if (builder.getTokenType() != IDENTIFIER) {
            builder.error("Expect class name.");
            classStmt.drop();
            return false;
        }
        builder.advanceLexer();

        if (builder.getTokenType() == LESS) {
            builder.advanceLexer();
            SyntaxTreeBuilder.Marker variableExpr = builder.mark();
            if (builder.getTokenType() != IDENTIFIER) {
                builder.error("Expect superclass name.");
                variableExpr.drop();
                classStmt.drop();
                return false;
            }
            builder.advanceLexer();
            variableExpr.done(VARIABLE_EXPR);
        }

        if (builder.getTokenType() != LEFT_BRACE) {
            builder.error("Expect '{' before class body.");
            classStmt.drop();
            return false;
        }
        builder.advanceLexer();

        while (builder.getTokenType() != RIGHT_BRACE && !builder.eof()) {
            if (!parseFunction(builder, "method")) {
                classStmt.drop();
                return false;
            }
        }

        if (builder.getTokenType() != RIGHT_BRACE) {
            builder.error("Expect '}' after class body.");
            classStmt.drop();
            return false;
        }
        builder.advanceLexer();

        classStmt.done(CLASS_STMT);
        return true;
    }

    private boolean parseStatement(@NotNull PsiBuilder builder) {
        IElementType currentToken = builder.getTokenType();

        if (currentToken == FOR) return parseForStatement(builder);
        if (currentToken == IF) return parseIfStatement(builder);
        if (currentToken == PRINT) return parsePrintStatement(builder);
        if (currentToken == RETURN) return parseReturnStatement(builder);
        if (currentToken == WHILE) return parseWhileStatement(builder);
        if (currentToken == LEFT_BRACE) return parseBlock(builder);

        return parseExpressionStatement(builder);
    }

    private boolean parseForStatement(@NotNull PsiBuilder builder) {
        SyntaxTreeBuilder.Marker forStmt = builder.mark();
        builder.advanceLexer();

        if (builder.getTokenType() != LEFT_PAREN) {
            builder.error("Expect '(' after 'for'.");
            forStmt.drop();
            return false;
        }
        builder.advanceLexer();

        if (builder.getTokenType() == EOL) {
            builder.advanceLexer();
        } else if (builder.getTokenType() == VAR) {
            if (!parseVarDeclaration(builder)) {
                forStmt.drop();
                return false;
            }
        } else {
            if (!parseExpressionStatement(builder)) {
                forStmt.drop();
                return false;
            }
        }

        if (builder.getTokenType() != EOL) {
            if (!parseExpression(builder)) {
                forStmt.drop();
                return false;
            }
        }

        if (builder.getTokenType() != EOL) {
            builder.error("Expect ';' after loop condition.");
            forStmt.drop();
            return false;
        }
        builder.advanceLexer();

        if (builder.getTokenType() != RIGHT_PAREN) {
            if (!parseExpression(builder)) {
                forStmt.drop();
                return false;
            }
        }

        if (builder.getTokenType() != RIGHT_PAREN) {
            builder.error("Expect ')' after for clauses.");
            forStmt.drop();
            return false;
        }
        builder.advanceLexer();

        if (!parseStatement(builder)) {
            forStmt.drop();
            return false;
        }

        forStmt.done(FOR_STMT);
        return false;
    }

    private boolean parseIfStatement(@NotNull PsiBuilder builder) {
        SyntaxTreeBuilder.Marker ifStmt = builder.mark();
        builder.advanceLexer();

        if (builder.getTokenType() != LEFT_PAREN) {
            builder.error("Expect '(' after 'if'.");
            ifStmt.drop();
            return false;
        }
        builder.advanceLexer();

        if (!parseExpression(builder)) {
//            ifStmt.drop();
//            return false;
            if (builder.rawLookup(-1) != RIGHT_PAREN) {
                builder.error("Expect ')' after if condition.");
                ifStmt.drop();
                return false;
            }
        } else {
            if (builder.getTokenType() != RIGHT_PAREN) {
                builder.error("Expect ')' after if condition.");
                ifStmt.drop();
                return false;
            }
            builder.advanceLexer();
        }

        if (!parseStatement(builder)) {
            ifStmt.drop();
            return false;
        }

        if (builder.getTokenType() == ELSE) {
            builder.advanceLexer();
            if (!parseStatement(builder)) {
                ifStmt.drop();
                return false;
            }
        }

        ifStmt.done(IF_STMT);
        return true;
    }

    private boolean parsePrintStatement(@NotNull PsiBuilder builder) {
        SyntaxTreeBuilder.Marker printStmt = builder.mark();

        builder.advanceLexer();
        if (!parseExpression(builder)) {
            printStmt.drop();
            return false;
        }

        if (builder.getTokenType() != EOL) {
            builder.error("Expect ';' after value.");
            printStmt.drop();
            return false;
        }
        builder.advanceLexer();

        printStmt.done(PRINT_STMT);
        return true;
    }

    private boolean parseReturnStatement(@NotNull PsiBuilder builder) {
        SyntaxTreeBuilder.Marker returnStmt = builder.mark();

        builder.advanceLexer();
        if (builder.getTokenType() != EOL) {
            if (!parseExpression(builder)) {
                returnStmt.drop();
                return false;
            }
        }

        if (builder.getTokenType() != EOL) {
            builder.error("Expect ';' after return value.");
            returnStmt.drop();
            return false;
        }
        builder.advanceLexer();

        returnStmt.done(RETURN_STMT);
        return true;
    }

    private boolean parseVarDeclaration(@NotNull PsiBuilder builder) {
        SyntaxTreeBuilder.Marker varStmt = builder.mark();

        builder.advanceLexer();

        if (builder.getTokenType() != IDENTIFIER) {
            builder.error("Expect variable name.");
            varStmt.drop();
            return false;
        }
        builder.advanceLexer();

        if (builder.getTokenType() == ASSIGN) {
            builder.advanceLexer();
            if (!parseExpression(builder)) {
//                varStmt.drop();
//                return false;
            }
        }

        if (builder.getTokenType() != EOL) {
            builder.error("Expect ';' after variable declaration.");
            varStmt.drop();
            return false;
        }
        builder.advanceLexer();

        varStmt.done(VAR_STMT);
        return true;
    }

    private boolean parseWhileStatement(@NotNull PsiBuilder builder) {
        SyntaxTreeBuilder.Marker whileStmt = builder.mark();

        builder.advanceLexer();

        if (builder.getTokenType() != LEFT_PAREN) {
            builder.error("Expect '(' after 'while'.");
            whileStmt.drop();
            return false;
        }
        builder.advanceLexer();

        if (!parseExpression(builder)) {
//            whileStmt.drop();
//            return false;
        }

        if (builder.getTokenType() != RIGHT_PAREN) {
            builder.error("Expect ')' after condition.");
            whileStmt.drop();
            return false;
        }
        builder.advanceLexer();

        if (!parseStatement(builder)) {
            whileStmt.drop();
            return false;
        }

        whileStmt.done(WHILE_STMT);
        return true;
    }

    private boolean parseExpressionStatement(@NotNull PsiBuilder builder) {
        SyntaxTreeBuilder.Marker exprStmt = builder.mark();

        if (!parseExpression(builder)) {
            exprStmt.drop();
            return false;
        }

        if (builder.getTokenType() != EOL) {
            builder.error("Expect ';' after expression.");
            exprStmt.drop();
            return false;
        }

        builder.advanceLexer();
        exprStmt.done(EXPRESSION_STMT);
        return true;
    }

    private boolean parseExpression(@NotNull PsiBuilder builder) {
        SyntaxTreeBuilder.Marker expr = builder.mark();

        if (!parseAssignment(builder)) {
            expr.drop();
            return false;
        }

        expr.drop();
        return true;
    }

    private boolean parseFunction(@NotNull PsiBuilder builder, String kind) {
        SyntaxTreeBuilder.Marker func = builder.mark();

        if (builder.getTokenType() == FUN) builder.advanceLexer();

        if (builder.getTokenType() != IDENTIFIER) {
            builder.error("Expect " + kind + " name.");
            func.drop();
            return false;
        }
        builder.advanceLexer();

        if (builder.getTokenType() != LEFT_PAREN) {
            builder.error("Expect '(' after " + kind + " name.");
            func.drop();
            return false;
        }
        builder.advanceLexer();

        if (builder.getTokenType() != RIGHT_PAREN) {
            if (builder.getTokenType() != IDENTIFIER) {
                builder.error("Expect parameter name.");
                func.drop();
                return false;
            }
            builder.advanceLexer();

            while (builder.getTokenType() == COMMA) {
                builder.advanceLexer();

                if (builder.getTokenType() != IDENTIFIER) {
                    builder.error("Expect parameter name.");
                    func.drop();
                    return false;
                }
                builder.advanceLexer();
            }
        }

        if (builder.getTokenType() != RIGHT_PAREN) {
            builder.error("Expect ')' after parameters.");
            func.drop();
            return false;
        }
        builder.advanceLexer();

        if (builder.getTokenType() != LEFT_BRACE) {
            builder.error("Expect '{' before " + kind + " body.");
            func.drop();
            return false;
        }

        if (!parseBlock(builder)){
            func.drop();
            return false;
        }

        func.done(FUNCTION_STMT);
        return true;
    }

    private boolean parseBlock(@NotNull PsiBuilder builder) {
        SyntaxTreeBuilder.Marker blockStmt = builder.mark();
        builder.advanceLexer();

        while (builder.getTokenType() != RIGHT_BRACE && !builder.eof()) {
            parseDeclaration(builder);
        }

        if (builder.getTokenType() != RIGHT_BRACE) {
            builder.error("Expect '}' after block.");
            blockStmt.drop();
            return false;
        }
        builder.advanceLexer();

        blockStmt.done(BLOCK_STMT);
        return true;
    }

    private boolean parseAssignment(@NotNull PsiBuilder builder) {
        SyntaxTreeBuilder.Marker assignmentExpr = builder.mark();

        if (!parseOr(builder)) {
            assignmentExpr.drop();
            return false;
        }

        boolean isVariableExpr = Objects.requireNonNull(builder.getLatestDoneMarker()).getTokenType() == VARIABLE_EXPR;
        boolean isGetExpr = Objects.requireNonNull(builder.getLatestDoneMarker()).getTokenType() == GET_EXPR;


        if (builder.getTokenType() == ASSIGN) {
            builder.advanceLexer();

            if (!parseAssignment(builder)) {
                assignmentExpr.drop();
                return false;
            }
            if (isVariableExpr || isGetExpr) {
                assignmentExpr.done(ASSIGNMENT_EXPR);
                return true;
            }
//            variableExpr.errorBefore("Invalid assignment target.", targetExpr);
            assignmentExpr.rollbackTo();

            SyntaxTreeBuilder.Marker recover = builder.mark();
            parseOr(builder);
            recover.error("Invalid assignment target.");
            builder.advanceLexer(); // =
            parseAssignment(builder);
            return true;
        }

        assignmentExpr.drop();
        return true;
    }

    private boolean parseOr(@NotNull PsiBuilder builder) {
        SyntaxTreeBuilder.Marker rootOrExpr = builder.mark();

        if (!parseAnd(builder)) {
            rootOrExpr.drop();
            return false;
        }

        while (builder.getTokenType() == OR) {
            builder.advanceLexer();
            SyntaxTreeBuilder.Marker orExpr = builder.mark();

            if (!parseAnd(builder)) {
                orExpr.drop();
                rootOrExpr.drop();
                return false;
            }

            orExpr.done(LOGIC_EXPR);
        }

        if (Objects.requireNonNull(builder.getLatestDoneMarker()).getTokenType() == LOGIC_EXPR) {
            rootOrExpr.done(LOGIC_EXPR);
            return true;
        }

        rootOrExpr.drop();
        return true;
    }

    private boolean parseAnd(@NotNull PsiBuilder builder) {
        SyntaxTreeBuilder.Marker rootAndExpr = builder.mark();

        if (!parseEquality(builder)) {
            rootAndExpr.drop();
            return false;
        }

        while (builder.getTokenType() == AND) {
            builder.advanceLexer();
            SyntaxTreeBuilder.Marker andExpr = builder.mark();

            if (!parseEquality(builder)) {
                andExpr.drop();
                rootAndExpr.drop();
                return false;
            }

            andExpr.done(LOGIC_EXPR);
        }

        if (Objects.requireNonNull(builder.getLatestDoneMarker()).getTokenType() == LOGIC_EXPR) {
            rootAndExpr.done(LOGIC_EXPR);
            return true;
        }

        rootAndExpr.drop();
        return true;
    }

    private boolean parseEquality(@NotNull PsiBuilder builder) {
        SyntaxTreeBuilder.Marker rootEqualityExpr = builder.mark();
        if (!parseComparison(builder)) {
            rootEqualityExpr.drop();
            return false;
        }

        while (builder.getTokenType() == EQUAL) {
            builder.advanceLexer();
            SyntaxTreeBuilder.Marker equalityExpr = builder.mark();

            if (!parseComparison(builder)) {
                equalityExpr.drop();
                rootEqualityExpr.drop();
                return false;
            }

            equalityExpr.done(BINARY_EXPR);
        }

        if (Objects.requireNonNull(builder.getLatestDoneMarker()).getTokenType() == BINARY_EXPR) {
            rootEqualityExpr.done(BINARY_EXPR);
            return true;
        }

        rootEqualityExpr.drop();
        return true;
    }

    private boolean parseComparison(@NotNull PsiBuilder builder) {
        SyntaxTreeBuilder.Marker rootComparisonExpr = builder.mark();

        if (!parseTerm(builder)) {
            rootComparisonExpr.drop();
            return false;
        }

        while (COMPARISON_SET.contains(builder.getTokenType())) {
            builder.advanceLexer();
            SyntaxTreeBuilder.Marker comparisonExpr = builder.mark();

            if (!parseTerm(builder)) {
                comparisonExpr.drop();
                rootComparisonExpr.drop();
                return false;
            }

            comparisonExpr.done(BINARY_EXPR);
        }

        if (Objects.requireNonNull(builder.getLatestDoneMarker()).getTokenType() == BINARY_EXPR) {
            rootComparisonExpr.done(BINARY_EXPR);
            return true;
        }

        rootComparisonExpr.drop();
        return true;
    }

    private boolean parseTerm(@NotNull PsiBuilder builder) {
        SyntaxTreeBuilder.Marker rootTermExpr = builder.mark();

        if (!parseFactor(builder)) {
            rootTermExpr.drop();
            return false;
        }

        while (builder.getTokenType() == PLUS || builder.getTokenType() == MINUS) {
            builder.advanceLexer();
            SyntaxTreeBuilder.Marker termExpr = builder.mark();

            if (!parseFactor(builder)) {
                termExpr.drop();
                rootTermExpr.drop();
                return false;
            }

            termExpr.done(BINARY_EXPR);
        }

        if (Objects.requireNonNull(builder.getLatestDoneMarker()).getTokenType() == BINARY_EXPR) {
            rootTermExpr.done(BINARY_EXPR);
            return true;
        }

        rootTermExpr.drop();
        return true;
    }

    private boolean parseFactor(@NotNull PsiBuilder builder) {
        SyntaxTreeBuilder.Marker rootFactorExpr = builder.mark();

        if (!parseUnary(builder)) {
            rootFactorExpr.drop();
            return false;
        }

        while (builder.getTokenType() == STAR || builder.getTokenType() == SLASH) {
            builder.advanceLexer();
            SyntaxTreeBuilder.Marker termExpr = builder.mark();

            if (!parseUnary(builder)) {
                termExpr.drop();
                rootFactorExpr.drop();
                return false;
            }

            termExpr.done(BINARY_EXPR);
        }

        if (Objects.requireNonNull(builder.getLatestDoneMarker()).getTokenType() == BINARY_EXPR) {
            rootFactorExpr.done(BINARY_EXPR);
            return true;
        }

        rootFactorExpr.drop();
        return true;
    }

    private boolean parseUnary(@NotNull PsiBuilder builder) {
        SyntaxTreeBuilder.Marker expr = builder.mark();
        IElementType currentToken = builder.getTokenType();
        if (currentToken == BANG || currentToken == MINUS) {
            builder.advanceLexer();

            if (!parseUnary(builder)) {
                expr.drop();
                return false;
            }
            expr.done(UNARY_EXPR);
            return true;
        }

        if (!parseCall(builder)) {
            expr.drop();
            return false;
        }
        expr.drop();
        return true;
    }

    private boolean parseCall(@NotNull PsiBuilder builder) {
        SyntaxTreeBuilder.Marker rootCallExpr = builder.mark();
        if (!parsePrimary(builder)) {
            rootCallExpr.drop();
            return false;
        }

        while (true) {
            SyntaxTreeBuilder.Marker callExpr = builder.mark();
            if (builder.getTokenType() == LEFT_PAREN) {
                builder.advanceLexer();

                if (builder.getTokenType() != RIGHT_PAREN) {
                    if (!parseExpression(builder)) {
                        callExpr.drop();
                        rootCallExpr.drop();
                        return false;
                    }

                    while (builder.getTokenType() == COMMA) {
                        builder.advanceLexer();

                        if (!parseExpression(builder)) {
                            callExpr.drop();
                            rootCallExpr.drop();
                            return false;
                        }
                    }
                }

                if (builder.getTokenType() != RIGHT_PAREN) {
                    builder.error("Expect ')' after arguments.");
                    callExpr.drop();
                    rootCallExpr.drop();
                    return false;
                }
                builder.advanceLexer();

                callExpr.done(CALL_EXPR);
            } else if (builder.getTokenType() == DOT) {
                builder.advanceLexer();
                if (builder.getTokenType() != IDENTIFIER) {
                    builder.error("Expect property name after '.'.");
                    callExpr.drop();
                    rootCallExpr.drop();
                    return false;
                }
                builder.advanceLexer();

                callExpr.done(GET_EXPR);
            } else {
                callExpr.drop();
                break;
            }
        }

        if (Objects.requireNonNull(builder.getLatestDoneMarker()).getTokenType() == CALL_EXPR) {
            rootCallExpr.done(CALL_EXPR);
            return true;
        }

        rootCallExpr.drop();
        return true;
    }

    private boolean parsePrimary(@NotNull PsiBuilder builder) {
        IElementType currentToken  = builder.getTokenType();
        if (LITERAL_SET.contains(currentToken)) {
            buildTokenElement(LITERAL_EXPR, builder);
            return true;
        }

        if (currentToken == SUPER) {
            SyntaxTreeBuilder.Marker superMarker = builder.mark();
            builder.advanceLexer();
            if (builder.getTokenType() != DOT) {
                builder.error("Expect '.' after 'super'.");
                superMarker.drop();
                return false;
            }
            builder.advanceLexer();
            if (builder.getTokenType() != IDENTIFIER) {
                builder.error("Expect superclass method name.");
                superMarker.drop();
                return false;
            }
            builder.advanceLexer();
            superMarker.done(SUPER_EXPR);
            return true;
        }

        if (currentToken == THIS) {
            buildTokenElement(THIS_EXPR, builder);
            return true;
        }

        if (currentToken == IDENTIFIER) {
            buildTokenElement(VARIABLE_EXPR, builder);
            return true;
        }

        if (currentToken == LEFT_PAREN) {
            SyntaxTreeBuilder.Marker parenMarker = builder.mark();
            builder.advanceLexer();

            if (!parseExpression(builder)) {
                parenMarker.drop();
                return false;
            }

            if (builder.getTokenType() != RIGHT_PAREN) {
                builder.error("Expect ')' after expression.");
                parenMarker.drop();
                return false;
            }
            builder.advanceLexer();

            parenMarker.done(GROUPING_EXPR);
            return true;
        }

        builder.error("Expect expression.");
        builder.advanceLexer();
        return false;
    }

    private static void buildTokenElement(IElementType type, SyntaxTreeBuilder builder) {
        final SyntaxTreeBuilder.Marker marker = builder.mark();
        builder.advanceLexer();
        marker.done(type);

    }
}
