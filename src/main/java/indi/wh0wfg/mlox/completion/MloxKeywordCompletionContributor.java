package indi.wh0wfg.mlox.completion;

import com.intellij.codeInsight.TailType;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.TailTypeDecorator;
import com.intellij.openapi.project.DumbAware;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import indi.wh0wfg.mlox.language.MloxLanguage;
import indi.wh0wfg.mlox.language.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import static com.intellij.patterns.StandardPatterns.or;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class MloxKeywordCompletionContributor extends CompletionContributor implements DumbAware {

//    private static final PsiElementPattern.Capture<PsiElement> IN_LOOP =
//            psiElement().inside(
//                    false,
//                    or(
//                            psiElement(MloxForStmt.class),
//                            psiElement(MloxForeachStmt.class),
//                            psiElement(MloxWhileStmt.class),
//                            psiElement(MloxDoWhileStmt.class)
//                    ), or(psiElement(MloxFunction.class), psiElement(MloxClassDecl.class)));


    public MloxKeywordCompletionContributor() {
//        addBreak();
    }

}
