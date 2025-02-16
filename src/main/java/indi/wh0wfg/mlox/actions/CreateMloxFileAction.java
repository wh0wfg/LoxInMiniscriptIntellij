package indi.wh0wfg.mlox.actions;

import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import indi.wh0wfg.mlox.icons.MloxIcons;
import org.jetbrains.annotations.NotNull;

public class CreateMloxFileAction extends CreateFileFromTemplateAction implements DumbAware{
    public CreateMloxFileAction() {
        super("Mlox File", "Creates a Mlox file from the specified template", MloxIcons.MloxFile);
    }

    @Override
    protected void buildDialog(@NotNull Project project, @NotNull PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder) {
        builder
                .setTitle("Mlox File")
                .addKind("Mlox file", MloxIcons.MloxFile, "Mlox Script");
    }

    @Override
    protected String getActionName(PsiDirectory directory, @NotNull String newName, String templateName) {
        return "Create Mlox script " + newName;
    }
}