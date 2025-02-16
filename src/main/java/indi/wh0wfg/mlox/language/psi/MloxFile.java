package indi.wh0wfg.mlox.language.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import indi.wh0wfg.mlox.language.MloxLanguage;
import org.jetbrains.annotations.NotNull;

public class MloxFile extends PsiFileBase {

    public MloxFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, MloxLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return MloxFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Mlox File";
    }

}