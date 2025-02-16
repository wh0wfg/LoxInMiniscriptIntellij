package indi.wh0wfg.mlox.completion;

import com.intellij.codeInsight.TailType;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.AutoCompletionPolicy;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.TailTypeDecorator;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.DumbAware;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.*;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.filters.position.FilterPattern;
import com.intellij.psi.impl.FreeThreadedFileViewProvider;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.ProcessingContext;
import indi.wh0wfg.mlox.language.MloxLanguage;
import indi.wh0wfg.mlox.language.psi.*;
import indi.wh0wfg.mlox.language.psi.impl.*;
import org.apache.tools.ant.types.resources.Tokens;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import static com.intellij.patterns.StandardPatterns.or;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class MloxKeywordCompletionContributor extends CompletionContributor implements DumbAware {

    private static final PsiElementPattern.Capture<PsiElement> IN_LOOP =
            psiElement().inside(
                    false,
                    or(
                            psiElement(MloxForStmtPsiElement.class),
                            psiElement(MloxWhileStmtPsiElement.class)
                    ), or(psiElement(MloxFunctionStmtPsiElement.class), psiElement(MloxClassStmtPsiElement.class)));

    private static final FilterPattern AFTER_IF = new FilterPattern(new AfterIfFilter());

    private static class AfterIfFilter implements ElementFilter {
        @Override
        public boolean isAcceptable(Object element, PsiElement context) {
            if (!(element instanceof PsiElement psiElement)) {
                return false;
            }

            PsiElement parent = psiElement.getParent();
            if (parent == null) {
                return false;
            }

            PsiElement prevSibling = parent.getPrevSibling();

            while ((prevSibling instanceof PsiWhiteSpace || prevSibling instanceof PsiComment)) {
                prevSibling = prevSibling.getPrevSibling();
            }

            return prevSibling instanceof MloxIfStmtPsiElement;
        }

        @Override
        public boolean isClassAcceptable(Class hintClass) {
            return true;
        }
    }


    public static final FilterPattern FIRST_ON_LINE = new FilterPattern(new StartOfLineFilter());

    private static class StartOfLineFilter implements ElementFilter {
        @Override
        public boolean isAcceptable(Object what, PsiElement context) {
            if (!(what instanceof PsiElement p)) return false;
            if (p instanceof PsiComment) return false; // just in case
            int point = p.getTextOffset();
            PsiDocumentManager docMgr = PsiDocumentManager.getInstance(p.getProject());
            final PsiFile file = p.getContainingFile().getOriginalFile();
            Document doc = docMgr.getDocument(file);
            String indentCharacters = file.getViewProvider() instanceof FreeThreadedFileViewProvider ? " \t>" : " \t";

            if (doc != null) {
                CharSequence chs = doc.getCharsSequence();
                char c;
                do { // scan to the left for a EOL
                    point -= 1;
                    if (point < 0) return true; // we're at BOF
                    c = chs.charAt(point);
                    if (c == '\n') return true;
                }
                while (indentCharacters.indexOf(c) >= 0);
            }
            return false;
        }

        @Override
        public boolean isClassAcceptable(Class hintClass) {
            return true;
        }
    }

    public MloxKeywordCompletionContributor() {
        addBreak();
        addFor();
        addIf();
        addWhile();
        addFun();
        addElse();
    }

    private void addFor() {
        extend(
                CompletionType.BASIC,
                psiElement()
                        .withLanguage(MloxLanguage.INSTANCE)
                        .and(FIRST_ON_LINE),
                new CompletionProvider<>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
                        result.addElement(
                                LookupElementBuilder.create("for ")
                                        .withInsertHandler((context1, item) -> {
                                            String template = "for (;;) {\n     \n}";
                                            int cursorOffset = template.indexOf(";;") + 2;
                                            context1.getDocument().replaceString(context1.getStartOffset(), context1.getSelectionEndOffset(), template);
                                            context1.getEditor().getCaretModel().moveToOffset(context1.getStartOffset() + cursorOffset);

                                        })
                                        .withBoldness(true)
                        );
                    }
                }
        );
    }

    private void addFun() {
        extend(
                CompletionType.BASIC,
                psiElement()
                        .withLanguage(MloxLanguage.INSTANCE)
                        .and(FIRST_ON_LINE),
                new CompletionProvider<>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
                        result.addElement(
                                LookupElementBuilder.create("fun ")
                                        .withInsertHandler((context1, item) -> {
                                            String template = "fun name() {\n    \n}";
                                            int cursorOffset = template.indexOf("name()") + 4;
                                            context1.getDocument().replaceString(context1.getStartOffset(), context1.getSelectionEndOffset(), template);
                                            context1.getEditor().getCaretModel().moveToOffset(context1.getStartOffset() + cursorOffset);
                                        })
                                        .withBoldness(true)
                        );
                    }
                }
        );
    }


    private void addIf() {
        extend(
                CompletionType.BASIC,
                psiElement()
                        .withLanguage(MloxLanguage.INSTANCE)
                        .and(FIRST_ON_LINE),
                new CompletionProvider<>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
                        result.addElement(
                                LookupElementBuilder.create("if ")
                                        .withInsertHandler((context1, item) -> {
                                            String template = "if () {\n     \n}";
                                            int cursorOffset = template.indexOf("()") + 1;
                                            context1.getDocument().replaceString(context1.getStartOffset(), context1.getSelectionEndOffset(), template);
                                            context1.getEditor().getCaretModel().moveToOffset(context1.getStartOffset() + cursorOffset);
                                        })
                                        .withBoldness(true)
                        );
                    }
                }
        );
    }

    private void addElse() {
        extend(
                CompletionType.BASIC,
                psiElement()
                        .withLanguage(MloxLanguage.INSTANCE)
                        .and(AFTER_IF),
                new CompletionProvider<>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
                        result.addElement(
                                LookupElementBuilder.create("else ")
                                        .withInsertHandler((context1, item) -> {
                                            String template = "else {\n    \n}";
                                            int cursorOffset = template.indexOf("{") + 2;
                                            context1.getDocument().replaceString(context1.getStartOffset(), context1.getSelectionEndOffset(), template);
                                            context1.getEditor().getCaretModel().moveToOffset(context1.getStartOffset() + cursorOffset);
                                        })
                                        .withBoldness(true)
                        );
                    }
                }
        );
    }


    private void addWhile() {
        extend(
                CompletionType.BASIC,
                psiElement()
                        .withLanguage(MloxLanguage.INSTANCE)
                        .and(FIRST_ON_LINE),
                new CompletionProvider<>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
                        result.addElement(
                                LookupElementBuilder.create("while ")
                                        .withInsertHandler((context1, item) -> {
                                            String template = "while () {\n     \n}";
                                            int cursorOffset = template.indexOf("()") + 1;
                                            context1.getDocument().replaceString(context1.getStartOffset(), context1.getSelectionEndOffset(), template);
                                            context1.getEditor().getCaretModel().moveToOffset(context1.getStartOffset() + cursorOffset);
                                        })
                                        .withBoldness(true)
                        );
                    }
                }
        );
    }

    private void addBreak() {
        extend(
                CompletionType.BASIC, psiElement()
                        .withLanguage(MloxLanguage.INSTANCE)
                        .and(IN_LOOP)
                        .and(FIRST_ON_LINE)
                ,
                new MloxKeywordCompletionProvider(TailType.NONE, "break;")
        );
    }

    private static final class MloxKeywordCompletionProvider extends CompletionProvider<CompletionParameters> {
        private final String[] myKeywords;
        private final TailType myTailType;
        private final InsertHandler<MloxLookupElement> myInsertHandler;

        private MloxKeywordCompletionProvider(String... keywords) {
            this(TailType.SPACE, keywords);
        }

        private MloxKeywordCompletionProvider(TailType tailType, String... keywords) {
            this(tailType, null, keywords);
        }

        private MloxKeywordCompletionProvider(TailType tailType, @Nullable InsertHandler<MloxLookupElement> insertHandler, String... keywords) {
            myKeywords = keywords;
            myTailType = tailType;
            myInsertHandler = insertHandler;
        }

        @Override
        protected void addCompletions(final @NotNull CompletionParameters parameters, final @NotNull ProcessingContext context,
                                      final @NotNull CompletionResultSet result) {
            for (String s : myKeywords) {
                final MloxLookupElement element = new MloxLookupElement(s, true, null);
                if (myInsertHandler != null) {
                    element.setHandler(myInsertHandler);
                }
                result.addElement(TailTypeDecorator.withTail(element, myTailType));
            }
        }
    }
}
