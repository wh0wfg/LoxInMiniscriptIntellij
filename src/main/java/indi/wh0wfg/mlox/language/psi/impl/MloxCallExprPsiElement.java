package indi.wh0wfg.mlox.language.psi.impl;

import com.intellij.lang.ASTNode;
import indi.wh0wfg.mlox.language.psi.MloxExprPsiElement;
import org.jetbrains.annotations.NotNull;

public class MloxCallExprPsiElement extends MloxExprPsiElement {
    public MloxCallExprPsiElement(@NotNull ASTNode node) {
        super(node);
    }
}
