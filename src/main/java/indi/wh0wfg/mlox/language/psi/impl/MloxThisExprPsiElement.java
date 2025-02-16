package indi.wh0wfg.mlox.language.psi.impl;

import com.intellij.lang.ASTNode;
import indi.wh0wfg.mlox.language.psi.MloxExprPsiElement;
import org.jetbrains.annotations.NotNull;

public class MloxThisExprPsiElement extends MloxExprPsiElement {
    public MloxThisExprPsiElement(@NotNull ASTNode node) {
        super(node);
    }
}
