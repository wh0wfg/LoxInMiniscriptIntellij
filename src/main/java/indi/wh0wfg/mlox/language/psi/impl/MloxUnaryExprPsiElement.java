package indi.wh0wfg.mlox.language.psi.impl;

import com.intellij.lang.ASTNode;
import indi.wh0wfg.mlox.language.psi.MloxExprPsiElement;
import org.jetbrains.annotations.NotNull;

public class MloxUnaryExprPsiElement extends MloxExprPsiElement {
    public MloxUnaryExprPsiElement(@NotNull ASTNode node) {
        super(node);
    }
}
