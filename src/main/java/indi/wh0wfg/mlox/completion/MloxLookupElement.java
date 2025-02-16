package indi.wh0wfg.mlox.completion;

import com.intellij.codeInsight.completion.BasicInsertHandler;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MloxLookupElement extends LookupElement implements Comparable<LookupElement>{
    protected final String myLookupString;
    protected final String myTypeText;
    protected final boolean isBold;
    protected final Icon myIcon;
    private final Icon myTypeIcon;
    protected final String myTailText;
    protected InsertHandler<MloxLookupElement> myHandler;

    public MloxLookupElement(final @NotNull String lookupString,
                               final @Nullable String tailText,
                               final @Nullable String typeText, final boolean bold,
                               final @Nullable Icon icon,
                               final @Nullable Icon typeIcon,
                               final @NotNull InsertHandler<MloxLookupElement> handler) {
        myLookupString = lookupString;
        myTailText = tailText;
        myTypeText = typeText;
        isBold = bold;
        myIcon = icon;
        myTypeIcon = typeIcon;
        myHandler = handler;
    }

    public MloxLookupElement(final @NotNull String lookupString,
                               final @Nullable String tailText,
                               final @Nullable String typeText, final boolean bold,
                               final @Nullable Icon icon,
                               final @Nullable Icon typeIcon) {
        this(lookupString, tailText, typeText, bold, icon, typeIcon, new BasicInsertHandler<>());
    }

    public MloxLookupElement(
            final @NotNull String lookupString,
            final boolean bold,
            final @Nullable Icon icon
    ) {
        this(lookupString, null, null, bold, icon, null, new BasicInsertHandler<>());
    }

    @Override
    public @NotNull String getLookupString() {
        return myLookupString;
    }

    public @Nullable String getTailText() {
        return !StringUtil.isEmpty(myTailText) ? myTailText : null;
    }

    protected @Nullable String getTypeText() {
        return !StringUtil.isEmpty(myTypeText) ? myTypeText : null;
    }

    public Icon getIcon() {
        return myIcon;
    }


    public Icon getTypeIcon() {
        return myTypeIcon;
    }

    @Override
    public void handleInsert(@NotNull InsertionContext context) {
        myHandler.handleInsert(context, this);
    }

    public void setHandler(InsertHandler<MloxLookupElement> handler) {
        myHandler = handler;
    }

    @Override
    public void renderElement(@NotNull LookupElementPresentation presentation) {
        presentation.setItemText(getLookupString());
        presentation.setItemTextBold(isBold);
        presentation.setTailText(getTailText());
        presentation.setTypeText(getTypeText(), getTypeIcon());
        presentation.setIcon(getIcon());
    }

    @Override
    public int compareTo(final LookupElement o) {
        return myLookupString.compareTo(o.getLookupString());
    }

}