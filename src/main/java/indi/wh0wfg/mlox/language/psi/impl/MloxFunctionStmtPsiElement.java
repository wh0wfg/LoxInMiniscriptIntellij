package indi.wh0wfg.mlox.language.psi.impl;

import com.intellij.lang.ASTNode;
import indi.wh0wfg.mlox.language.psi.MloxExprPsiElement;
import org.jetbrains.annotations.NotNull;

public class MloxFunctionStmtPsiElement extends MloxExprPsiElement {
    public MloxFunctionStmtPsiElement(@NotNull ASTNode node) {
        super(node);
    }
}
