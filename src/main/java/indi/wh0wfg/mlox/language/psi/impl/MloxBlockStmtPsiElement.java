package indi.wh0wfg.mlox.language.psi.impl;

import com.intellij.lang.ASTNode;
import indi.wh0wfg.mlox.language.psi.MloxExprPsiElement;
import org.jetbrains.annotations.NotNull;

public class MloxBlockStmtPsiElement extends MloxExprPsiElement {
    public MloxBlockStmtPsiElement(@NotNull ASTNode node) {
        super(node);
    }
}
