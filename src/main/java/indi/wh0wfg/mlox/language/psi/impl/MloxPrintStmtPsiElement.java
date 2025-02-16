package indi.wh0wfg.mlox.language.psi.impl;

import com.intellij.lang.ASTNode;
import indi.wh0wfg.mlox.language.psi.MloxExprPsiElement;
import org.jetbrains.annotations.NotNull;

public class MloxPrintStmtPsiElement extends MloxExprPsiElement {
    public MloxPrintStmtPsiElement(@NotNull ASTNode node) {
        super(node);
    }
}
