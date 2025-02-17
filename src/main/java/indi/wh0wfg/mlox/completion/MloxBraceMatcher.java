package indi.wh0wfg.mlox.completion;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import indi.wh0wfg.mlox.language.psi.MloxTokenTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MloxBraceMatcher implements PairedBraceMatcher {
    private static final BracePair[] PAIRS = {
            new BracePair(MloxTokenTypes.LEFT_PAREN, MloxTokenTypes.RIGHT_PAREN, false),
            new BracePair(MloxTokenTypes.LEFT_SQUARE, MloxTokenTypes.RIGHT_SQUARE, false),
            new BracePair(MloxTokenTypes.LEFT_BRACE, MloxTokenTypes.RIGHT_BRACE, true)
    };

    @Override
    public BracePair @NotNull [] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType LEFT_BRACEType, @Nullable IElementType contextType) {
        return contextType == null
                || contextType == MloxTokenTypes.WHITE_SPACE
                || contextType == MloxTokenTypes.COMMA
                || contextType == MloxTokenTypes.RIGHT_PAREN
                || contextType == MloxTokenTypes.RIGHT_BRACE
                || contextType == MloxTokenTypes.RIGHT_SQUARE
                || contextType == MloxTokenTypes.EOL;
    }

    @Override
    public int getCodeConstructStart(@NotNull PsiFile file, int openingBraceOffset) {
        return openingBraceOffset;
    }
}
