package indi.wh0wfg.mlox.language.psi.impl;

import com.intellij.lang.ASTNode;
import indi.wh0wfg.mlox.language.psi.MloxExprPsiElement;
import org.jetbrains.annotations.NotNull;

public class MloxClassStmtPsiElement extends MloxExprPsiElement {
    public MloxClassStmtPsiElement(@NotNull ASTNode node) {
        super(node);
    }
}
