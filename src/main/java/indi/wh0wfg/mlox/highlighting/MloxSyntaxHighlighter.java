package indi.wh0wfg.mlox.highlighting;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import indi.wh0wfg.mlox.language.MloxLexerAdapter;
import indi.wh0wfg.mlox.language.psi.MloxTokenTypes;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.intellij.openapi.editor.DefaultLanguageHighlighterColors.*;

public class MloxSyntaxHighlighter extends SyntaxHighlighterBase {
    private final Map<IElementType, TextAttributesKey> keys;

    public static final TextAttributesKey MLOX_KEYWORD = TextAttributesKey.createTextAttributesKey("MLOX.KEYWORD", KEYWORD);

    public static final TextAttributesKey MLOX_NUMBER = TextAttributesKey.createTextAttributesKey("MLOX.NUMBER", NUMBER);
    public static final TextAttributesKey MLOX_STRING = TextAttributesKey.createTextAttributesKey("MLOX.STRING", STRING);

    public static final TextAttributesKey MLOX_OPERATION_SIGN = TextAttributesKey.createTextAttributesKey("MLOX.OPERATION_SIGN", OPERATION_SIGN);

    public static final TextAttributesKey MLOX_PARENTHS = TextAttributesKey.createTextAttributesKey("MLOX.PARENTHS", PARENTHESES);

    public static final TextAttributesKey MLOX_BRACKETS = TextAttributesKey.createTextAttributesKey("MLOX.BRACKETS", BRACKETS);

    public static final TextAttributesKey MLOX_BRACES = TextAttributesKey.createTextAttributesKey("MLOX.BRACES", BRACES);

    public static final TextAttributesKey MLOX_COMMA = TextAttributesKey.createTextAttributesKey("MLOX.COMMA", COMMA);

    public static final TextAttributesKey MLOX_DOT = TextAttributesKey.createTextAttributesKey("MLOX.DOT", DOT);

    public static final TextAttributesKey MLOX_LINE_COMMENT = TextAttributesKey.createTextAttributesKey("MLOX.LINE_COMMENT", LINE_COMMENT);

    public static final TextAttributesKey MLOX_CLASS_DEFINITION = TextAttributesKey.createTextAttributesKey("MLOX.CLASS_DEFINITION", CLASS_NAME);

    public static final TextAttributesKey MLOX_FUNC_DEFINITION = TextAttributesKey.createTextAttributesKey("MLOX.FUNC_DEFINITION", FUNCTION_DECLARATION);

    public static final TextAttributesKey MLOX_NESTED_FUNC_DEFINITION = TextAttributesKey.createTextAttributesKey("MLOX.NESTED_FUNC_DEFINITION", MLOX_FUNC_DEFINITION);

    public static final TextAttributesKey MLOX_PREDEFINED_DEFINITION = TextAttributesKey.createTextAttributesKey("MLOX.PREDEFINED_DEFINITION", PREDEFINED_SYMBOL);

    public static final TextAttributesKey MLOX_PREDEFINED_USAGE = TextAttributesKey.createTextAttributesKey("MLOX.PREDEFINED_USAGE", PREDEFINED_SYMBOL);

    public static final TextAttributesKey MLOX_BUILTIN_NAME = TextAttributesKey.createTextAttributesKey("MLOX.BUILTIN_NAME", PREDEFINED_SYMBOL);

    public static final TextAttributesKey MLOX_PARAMETER = TextAttributesKey.createTextAttributesKey("MLOX.PARAMETER", PARAMETER);
    public static final TextAttributesKey MLOX_KEYWORD_ARGUMENT = TextAttributesKey.createTextAttributesKey("MLOX.KEYWORD_ARGUMENT", PARAMETER);

    public static final TextAttributesKey MLOX_FUNCTION_CALL = TextAttributesKey.createTextAttributesKey("MLOX.FUNCTION_CALL", FUNCTION_CALL);
    public static final TextAttributesKey MLOX_METHOD_CALL = TextAttributesKey.createTextAttributesKey("MLOX.METHOD_CALL", MLOX_FUNCTION_CALL);

    public static final TextAttributesKey MLOX_LOCAL_VARIABLE = TextAttributesKey.createTextAttributesKey("MLOX.LOCAL_VARIABLE", LOCAL_VARIABLE);

    public MloxSyntaxHighlighter() {
        this.keys = new HashMap<>();

        fillMap(keys, MloxTokenTypes.KEYWORD_SET, MLOX_KEYWORD);
        fillMap(keys, MloxTokenTypes.OPERATOR_SET, MLOX_OPERATION_SIGN);

        keys.put(MloxTokenTypes.NUMBER, MLOX_NUMBER);
        keys.put(MloxTokenTypes.STRING, MLOX_STRING);

        keys.put(MloxTokenTypes.LEFT_PAREN, MLOX_PARENTHS);
        keys.put(MloxTokenTypes.RIGHT_PAREN, MLOX_PARENTHS);

        keys.put(MloxTokenTypes.LEFT_BRACE, MLOX_BRACES);
        keys.put(MloxTokenTypes.RIGHT_BRACE, MLOX_BRACES);

        keys.put(MloxTokenTypes.LEFT_SQUARE, MLOX_BRACKETS);
        keys.put(MloxTokenTypes.RIGHT_SQUARE, MLOX_BRACKETS);

        keys.put(MloxTokenTypes.COMMA, MLOX_COMMA);
        keys.put(MloxTokenTypes.DOT, MLOX_DOT);

        keys.put(MloxTokenTypes.COMMENT, MLOX_LINE_COMMENT);
        keys.put(TokenType.BAD_CHARACTER, HighlighterColors.BAD_CHARACTER);
    }

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new MloxLexerAdapter();
    }

    @Override
    public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
        return pack(this.keys.get(tokenType));
    }

}