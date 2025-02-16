package indi.wh0wfg.mlox.execution;

import com.intellij.execution.configurations.LocatableRunConfigurationOptions;
import com.intellij.execution.configurations.RunConfigurationOptions;
import com.intellij.openapi.components.StoredProperty;

    public class MloxRunConfigurationOptions extends LocatableRunConfigurationOptions {

    private final StoredProperty<String> myScriptName =
            string("").provideDelegate(this, "scriptName");

    public String getScriptName() {
        return myScriptName.getValue(this);
    }

    public void setScriptName(String scriptName) {
        myScriptName.setValue(this, scriptName);
    }
}
