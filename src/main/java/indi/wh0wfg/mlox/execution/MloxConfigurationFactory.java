package indi.wh0wfg.mlox.execution;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.components.BaseState;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MloxConfigurationFactory extends ConfigurationFactory {

    protected MloxConfigurationFactory(ConfigurationType type) {
        super(type);
    }

    @Override
    public @NotNull String getId() {
        return MloxRunConfigurationType.ID;
    }

    @NotNull
    @Override
    public RunConfiguration createTemplateConfiguration(
            @NotNull Project project) {
        return new MloxRunConfiguration(project, this, "Mlox");
    }

    @Nullable
    @Override
    public Class<? extends BaseState> getOptionsClass() {
        return MloxRunConfigurationOptions.class;
    }

}