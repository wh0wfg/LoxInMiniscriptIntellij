package indi.wh0wfg.mlox.language.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import indi.wh0wfg.mlox.language.MloxLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class MloxElementType extends IElementType {
    private final @NotNull Function<? super ASTNode, ? extends PsiElement> myPsiCreator;

    public MloxElementType(@NotNull @NonNls String debugName) {
        super(debugName, MloxLanguage.INSTANCE);
        this.myPsiCreator =  node -> { throw new IllegalStateException("Cannot create an element for " + node.getElementType() + " without element class");};
    }

    public MloxElementType(@NotNull @NonNls String debugName, @NotNull Function<? super ASTNode, ? extends PsiElement> creator) {
        super(debugName, MloxLanguage.INSTANCE);
        this.myPsiCreator = creator;
    }

    public @NotNull PsiElement createElement(@NotNull ASTNode node) {
        return myPsiCreator.apply(node);
    }

    @Override
    public String toString() {
        return "Mlox:" + super.toString();
    }
}
