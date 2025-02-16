package indi.wh0wfg.mlox.execution;

import com.intellij.execution.DefaultExecutionResult;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.process.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import indi.wh0wfg.mlox.language.psi.MloxFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Objects;

public class MloxRunConfiguration extends LocatableConfigurationBase<MloxRunConfigurationOptions> {

    protected MloxRunConfiguration(Project project,
                                   ConfigurationFactory factory,
                                   String name) {
        super(project, factory, name);
    }

    @Override
    protected @NotNull MloxRunConfigurationOptions getOptions() {
        return (MloxRunConfigurationOptions) super.getOptions();
    }

    public String getScriptName() {
        return getOptions().getScriptName();
    }

    public void setScriptName(String scriptName) {
        getOptions().setScriptName(scriptName);
    }

    @Override
    public String suggestedName() {
        final String scriptName = getScriptName();
        if (scriptName == null) return null;
        String name = new File(scriptName).getName();
        if (name.endsWith(".mlox")) {
            return name.substring(0, name.length() - 5);
        }
        return name;
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new MloxSettingsEditor();
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor,
                                    @NotNull ExecutionEnvironment environment) {
        return new RunProfileState() {
            @Override
            public @NotNull ExecutionResult execute(Executor executor, @NotNull ProgramRunner<?> runner) throws ExecutionException {
                Project project = environment.getProject();

                VirtualFile virtualFile = Objects.requireNonNull(environment.getDataContext()).getData(CommonDataKeys.VIRTUAL_FILE);
                if (virtualFile == null) {
                    throw new ExecutionException("No file to execute.");
                }
                PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
                if (!(psiFile instanceof MloxFile)) {
                    throw new ExecutionException("Invalid file type.");
                }

                ProcessHandler handler = new NopProcessHandler();
                handler.startNotify();

                MloxFile mloxFile = (MloxFile) psiFile;
                String output = mloxFile.getNode().toString();

                TextConsoleBuilder consoleBuilder = TextConsoleBuilderFactory.getInstance().createBuilder(project);
                ConsoleView consoleView = consoleBuilder.getConsole();
                consoleView.print(output, ConsoleViewContentType.NORMAL_OUTPUT);
                consoleView.print("\nProcess finished with exit code 0", ConsoleViewContentType.NORMAL_OUTPUT);
                handler.destroyProcess();
                return new DefaultExecutionResult(consoleView, handler);
            }
        };
    }

}