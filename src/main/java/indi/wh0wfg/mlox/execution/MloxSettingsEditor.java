package indi.wh0wfg.mlox.execution;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class MloxSettingsEditor extends SettingsEditor<MloxRunConfiguration> {

    private final JPanel myPanel;
    private final TextFieldWithBrowseButton scriptPathField;

    public MloxSettingsEditor() {
        scriptPathField = new TextFieldWithBrowseButton();
        scriptPathField.addBrowseFolderListener("Select Script File", null, null,
                FileChooserDescriptorFactory.createSingleFileDescriptor());
        myPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent("Script file", scriptPathField)
                .getPanel();
    }

    @Override
    protected void resetEditorFrom(MloxRunConfiguration demoRunConfiguration) {
        scriptPathField.setText(demoRunConfiguration.getScriptName());
    }

    @Override
    protected void applyEditorTo(@NotNull MloxRunConfiguration demoRunConfiguration) {
        demoRunConfiguration.setScriptName(scriptPathField.getText());
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return myPanel;
    }

}