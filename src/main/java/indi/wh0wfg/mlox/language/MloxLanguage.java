package indi.wh0wfg.mlox.language;

import com.intellij.lang.Language;

public class MloxLanguage extends Language {
    public static final MloxLanguage INSTANCE = new MloxLanguage();

    private MloxLanguage() {
        super("mlox");
    }

    @Override
    public boolean isCaseSensitive() {
        return true;
    }
}
