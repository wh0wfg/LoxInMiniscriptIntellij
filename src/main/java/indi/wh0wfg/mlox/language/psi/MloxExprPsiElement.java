package indi.wh0wfg.mlox.language.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public abstract class MloxExprPsiElement extends ASTWrapperPsiElement {
    public MloxExprPsiElement(@NotNull ASTNode node) {
        super(node);
    }
}
