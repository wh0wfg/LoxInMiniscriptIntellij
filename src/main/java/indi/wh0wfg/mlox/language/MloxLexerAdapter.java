package indi.wh0wfg.mlox.language;

import com.intellij.lexer.FlexAdapter;

public class MloxLexerAdapter extends FlexAdapter {

    public MloxLexerAdapter() {
        super(new MloxLexer(null));
    }

}