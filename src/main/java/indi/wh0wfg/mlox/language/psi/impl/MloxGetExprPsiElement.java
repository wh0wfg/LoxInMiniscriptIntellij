package indi.wh0wfg.mlox.language.psi.impl;

import com.intellij.lang.ASTNode;
import indi.wh0wfg.mlox.language.psi.MloxExprPsiElement;
import org.jetbrains.annotations.NotNull;

public class MloxGetExprPsiElement extends MloxExprPsiElement {
    public MloxGetExprPsiElement(@NotNull ASTNode node) {
        super(node);
    }
}
