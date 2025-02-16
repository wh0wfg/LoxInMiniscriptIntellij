package indi.wh0wfg.mlox.language.psi;


import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

public final class MloxTokenTypes {
    private MloxTokenTypes() {
    }

    public static final IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
    public static final IElementType WHITE_SPACE = TokenType.WHITE_SPACE;

    // keywords
    public static final MloxElementType IMPORT = new MloxElementType("IMPORT");
    public static final MloxElementType AND = new MloxElementType("AND");
    public static final MloxElementType CLASS = new MloxElementType("CLASS");
    public static final MloxElementType ELSE = new MloxElementType("ELSE");
    public static final MloxElementType FALSE = new MloxElementType("FALSE");
    public static final MloxElementType FUN = new MloxElementType("FUN");
    public static final MloxElementType LAMBDA = new MloxElementType("LAMBDA");
    public static final MloxElementType FOREACH = new MloxElementType("FOREACH");
    public static final MloxElementType IN = new MloxElementType("IN");
    public static final MloxElementType FOR = new MloxElementType("FOR");
    public static final MloxElementType SWITCH = new MloxElementType("SWITCH");
    public static final MloxElementType CASE = new MloxElementType("CASE");
    public static final MloxElementType DEFAULT = new MloxElementType("DEFAULT");
    public static final MloxElementType TRY = new MloxElementType("TRY");
    public static final MloxElementType CATCH = new MloxElementType("CATCH");
    public static final MloxElementType FINALLY = new MloxElementType("FINALLY");
    public static final MloxElementType THROW = new MloxElementType("THROW");
    public static final MloxElementType IF = new MloxElementType("IF");
    public static final MloxElementType NIL = new MloxElementType("NIL");
    public static final MloxElementType OR = new MloxElementType("OR");
    public static final MloxElementType PRINT = new MloxElementType("PRINT");
    public static final MloxElementType RETURN = new MloxElementType("RETURN");
    public static final MloxElementType BREAK = new MloxElementType("BREAK");
    public static final MloxElementType CONTINUE = new MloxElementType("CONTINUE");
    public static final MloxElementType SUPER = new MloxElementType("SUPER");
    public static final MloxElementType THIS = new MloxElementType("THIS");
    public static final MloxElementType TRUE = new MloxElementType("TRUE");
    public static final MloxElementType VAR = new MloxElementType("VAR");
    public static final MloxElementType DO = new MloxElementType("DO");
    public static final MloxElementType WHILE = new MloxElementType("WHILE");

    // brackets
    public static final MloxElementType LEFT_PAREN = new MloxElementType("LEFT_PAREN");
    public static final MloxElementType RIGHT_PAREN = new MloxElementType("RIGHT_PAREN");
    public static final MloxElementType LEFT_SQUARE = new MloxElementType("LEFT_SQUARE");
    public static final MloxElementType RIGHT_SQUARE = new MloxElementType("RIGHT_SQUARE");
    public static final MloxElementType LEFT_BRACE = new MloxElementType("LEFT_BRACE");
    public static final MloxElementType RIGHT_BRACE = new MloxElementType("RIGHT_BRACE");

    // separators
    public static final MloxElementType COMMA = new MloxElementType("COMMA");
    public static final MloxElementType DOT = new MloxElementType("DOT");
    public static final MloxElementType COLON = new MloxElementType("COLON");
    public static final MloxElementType EOL = new MloxElementType("EOL");

    // aug assigns
    public static final MloxElementType PLUS_ASSIGN = new MloxElementType("PLUS_ASSIGN");
    public static final MloxElementType MINUS_ASSIGN = new MloxElementType("MINUS_ASSIGN");
    public static final MloxElementType MUL_ASSIGN = new MloxElementType("MUL_ASSIGN");
    public static final MloxElementType DIV_ASSIGN = new MloxElementType("DIV_ASSIGN");
    public static final MloxElementType MOD_ASSIGN = new MloxElementType("MOD_ASSIGN");

    // comparisons
    public static final MloxElementType NOT_EQUAL = new MloxElementType("NOT_EQUAL");
    public static final MloxElementType EQUAL = new MloxElementType("EQUAL");
    public static final MloxElementType GREAT_EQUAL = new MloxElementType("GREAT_EQUAL");
    public static final MloxElementType LESS_EQUAL = new MloxElementType("LESS_EQUAL");
    public static final MloxElementType GREATER = new MloxElementType("GREATER");
    public static final MloxElementType LESS = new MloxElementType("LESS");

    // operators
    public static final MloxElementType SLASH = new MloxElementType("SLASH");
    public static final MloxElementType STAR = new MloxElementType("STAR");
    public static final MloxElementType BANG = new MloxElementType("BANG");
    public static final MloxElementType MINUS = new MloxElementType("MINUS");
    public static final MloxElementType PLUS = new MloxElementType("PLUS");
    public static final MloxElementType MOD = new MloxElementType("MOD");

    // misc
    public static final MloxElementType ASSIGN = new MloxElementType("ASSIGN");
    public static final MloxElementType QUESTION = new MloxElementType("QUESTION");
    public static final MloxElementType PIPE = new MloxElementType("PIPE");

    // literals
    public static final MloxElementType NUMBER = new MloxElementType("NUMBER");
    public static final MloxElementType STRING = new MloxElementType("STRING");
    public static final MloxElementType IDENTIFIER = new MloxElementType("IDENTIFIER");
    public static final MloxElementType COMMENT = new MloxElementType("COMMENT");

    // token sets
    public static final TokenSet COMMENT_SET = TokenSet.create(COMMENT);
    public static final TokenSet STRING_SET = TokenSet.create(STRING);
    public static final TokenSet RECOVER_SET = TokenSet.create(
            //            IMPORT,
            AND, CLASS,
//            ELSE,
            FALSE, FUN,
//            LAMBDA, FOREACH, IN,
            FOR,
//            SWITCH, CASE, DEFAULT, TRY, CATCH, FINALLY, THROW,
            IF, NIL, OR,
            PRINT, RETURN,
//            BREAK, CONTINUE,
            SUPER, THIS, TRUE, VAR,
//            DO,
            WHILE,
            IDENTIFIER,
            RIGHT_BRACE,
            LEFT_BRACE
    );
    public static final TokenSet KEYWORD_SET = TokenSet.create(
            IMPORT,
            AND, CLASS, ELSE, FALSE, FUN,
            LAMBDA, FOREACH, IN,
            FOR,
            SWITCH, CASE, DEFAULT, TRY, CATCH, FINALLY, THROW,
            IF, NIL, OR,
            PRINT, RETURN,
            BREAK, CONTINUE,
            SUPER, THIS, TRUE, VAR,
            DO,
            WHILE
    );
    public static final TokenSet OPERATOR_SET = TokenSet.create(
            PLUS_ASSIGN, MINUS_ASSIGN, MUL_ASSIGN, DIV_ASSIGN,
            MOD_ASSIGN, NOT_EQUAL, EQUAL, GREAT_EQUAL, LESS_EQUAL, GREATER, LESS,
            SLASH, STAR, BANG, MINUS, PLUS, MOD, ASSIGN, QUESTION, PIPE
    );
    public static final TokenSet LITERAL_SET = TokenSet.create(
            TRUE, FALSE, NIL, NUMBER, STRING
    );
    public static final TokenSet COMPARISON_SET = TokenSet.create(
            NOT_EQUAL, GREAT_EQUAL, LESS_EQUAL, GREATER, LESS
    );
}
