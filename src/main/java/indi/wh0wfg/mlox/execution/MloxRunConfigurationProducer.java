package indi.wh0wfg.mlox.execution;

import com.intellij.execution.Location;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.LazyRunConfigurationProducer;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.LightVirtualFile;
import indi.wh0wfg.mlox.language.MloxLanguage;
import indi.wh0wfg.mlox.language.psi.MloxFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class MloxRunConfigurationProducer extends LazyRunConfigurationProducer<MloxRunConfiguration> implements DumbAware {
    @Override
    public @NotNull ConfigurationFactory getConfigurationFactory() {
        return MloxRunConfigurationType.getInstance().getFactory();
    }

    @Override
    protected boolean setupConfigurationFromContext(@NotNull MloxRunConfiguration configuration, @NotNull ConfigurationContext context, @NotNull Ref<PsiElement> sourceElement) {
        final Location location = context.getLocation();
        if (location == null) return false;

        final PsiFile script = location.getPsiElement().getContainingFile();
        if (!isAvailable(location, script)) return false;

        final VirtualFile vFile = script.getVirtualFile();
        if (vFile == null) return false;
        configuration.setScriptName(vFile.getPath());

//        final Module module = ModuleUtilCore.findModuleForPsiElement(script);
//        if (module != null) {
//            configuration.setUseModuleSdk(true);
//            configuration.setModule(module);
//        }
        configuration.setGeneratedName();
        return true;
    }

    @Override
    public boolean isConfigurationFromContext(@NotNull MloxRunConfiguration configuration, @NotNull ConfigurationContext context) {
        final Location location = context.getLocation();
        if (location == null) return false;
        final PsiFile script = location.getPsiElement().getContainingFile();
        if (!isAvailable(location, script)) return false;
        final VirtualFile virtualFile = script.getVirtualFile();
        if (virtualFile == null) return false;
        if (virtualFile instanceof LightVirtualFile) return false;

        return true;
    }

    private static boolean isAvailable(final @NotNull Location location, final @Nullable PsiFile script) {
        if (script == null || script.getFileType() != MloxFileType.INSTANCE ||
                !script.getViewProvider().getBaseLanguage().isKindOf(MloxLanguage.INSTANCE)) {
            return false;
        }
        return true;
    }
}
