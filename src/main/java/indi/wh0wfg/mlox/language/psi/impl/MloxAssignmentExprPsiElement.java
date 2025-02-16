package indi.wh0wfg.mlox.language.psi.impl;

import com.intellij.lang.ASTNode;
import indi.wh0wfg.mlox.language.psi.MloxExprPsiElement;
import org.jetbrains.annotations.NotNull;

public class MloxAssignmentExprPsiElement extends MloxExprPsiElement {
    public MloxAssignmentExprPsiElement(@NotNull ASTNode node) {
        super(node);
    }
}
