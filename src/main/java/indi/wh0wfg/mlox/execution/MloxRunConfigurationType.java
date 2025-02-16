package indi.wh0wfg.mlox.execution;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.execution.configurations.ConfigurationTypeUtil;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.NotNullLazyValue;
import indi.wh0wfg.mlox.icons.MloxIcons;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

final class MloxRunConfigurationType extends ConfigurationTypeBase {
    static final String ID = "MloxRunConfiguration";
    private final MloxConfigurationFactory myFactory = new MloxConfigurationFactory(this);

    MloxRunConfigurationType() {
        super(ID, "Mlox", "Mlox run configuration type",
                NotNullLazyValue.createValue(() -> MloxIcons.MloxFile));
        addFactory(new MloxConfigurationFactory(this));
    }

    public static MloxRunConfigurationType getInstance() {
        return ConfigurationTypeUtil.findConfigurationType(MloxRunConfigurationType.class);
    }

    public MloxConfigurationFactory getFactory() {
        return myFactory;
    }
}
