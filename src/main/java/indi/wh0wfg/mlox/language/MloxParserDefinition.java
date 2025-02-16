package indi.wh0wfg.mlox.language;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import indi.wh0wfg.mlox.language.psi.MloxElementType;
import indi.wh0wfg.mlox.language.psi.MloxFile;
import indi.wh0wfg.mlox.language.psi.MloxTokenTypes;
import indi.wh0wfg.mlox.parsing.MloxParser;
import org.jetbrains.annotations.NotNull;

public class MloxParserDefinition implements ParserDefinition {
    public static final IFileElementType FILE = new IFileElementType(MloxLanguage.INSTANCE);

    @Override
    public @NotNull Lexer createLexer(Project project) {
        return new MloxLexerAdapter();
    }

    @Override
    public @NotNull PsiParser createParser(Project project) {
        return new MloxParser();
    }

    @Override
    public @NotNull IFileElementType getFileNodeType() {
        return FILE;
    }

    @Override
    public @NotNull TokenSet getCommentTokens() {
        return MloxTokenTypes.COMMENT_SET;
    }

    @Override
    public @NotNull TokenSet getStringLiteralElements() {
        return MloxTokenTypes.STRING_SET;
    }

    @Override
    public @NotNull PsiElement createElement(ASTNode node) {
        final IElementType type = node.getElementType();
        if (type instanceof MloxElementType pyElType) {
            return pyElType.createElement(node);
        }
        return new ASTWrapperPsiElement(node);
    }


    @Override
    public @NotNull PsiFile createFile(@NotNull FileViewProvider viewProvider) {
        return new MloxFile(viewProvider);
    }
}
