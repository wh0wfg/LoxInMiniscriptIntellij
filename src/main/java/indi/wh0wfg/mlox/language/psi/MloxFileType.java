package indi.wh0wfg.mlox.language.psi;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import indi.wh0wfg.mlox.icons.MloxIcons;
import indi.wh0wfg.mlox.language.MloxLanguage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class MloxFileType extends LanguageFileType {

    public static final MloxFileType INSTANCE = new MloxFileType();

    private MloxFileType() {
        this(MloxLanguage.INSTANCE);
    }

    protected MloxFileType(Language language) {
        super(language);
    }

    @Override
    public @NotNull String getName() {
        return "mlox";
    }

    @Override
    public @NotNull String getDescription() {
        return "Mlox";
    }

    @Override
    public @NotNull String getDefaultExtension() {
        return "mlox";
    }

    @Override
    public Icon getIcon() {
        return MloxIcons.MloxFile;
    }
}
