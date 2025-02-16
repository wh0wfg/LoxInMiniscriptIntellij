package indi.wh0wfg.mlox.language.psi.impl;

import com.intellij.lang.ASTNode;
import indi.wh0wfg.mlox.language.psi.MloxExprPsiElement;
import org.jetbrains.annotations.NotNull;

public class MloxForStmtPsiElement extends MloxExprPsiElement {
    public MloxForStmtPsiElement(@NotNull ASTNode node) {
        super(node);
    }
}
