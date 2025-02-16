package indi.wh0wfg.mlox.language.psi.impl;

import com.intellij.lang.ASTNode;
import indi.wh0wfg.mlox.language.psi.MloxExprPsiElement;
import org.jetbrains.annotations.NotNull;

public class MloxVariableExprPsiElement extends MloxExprPsiElement {
    public MloxVariableExprPsiElement(@NotNull ASTNode node) {
        super(node);
    }
}
