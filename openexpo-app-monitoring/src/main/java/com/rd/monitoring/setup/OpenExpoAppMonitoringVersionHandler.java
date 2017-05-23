package com.rd.monitoring.setup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.jcr.ImportUUIDBehavior;

import com.rd.monitoring.constants.IConstantsOpenExpoMonitoring;
import com.rd.monitoring.constants.IConstantsVersionHandler;

import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.Condition;
import info.magnolia.module.delta.Delta;
import info.magnolia.module.delta.DeltaBuilder;
import info.magnolia.module.delta.ModuleFilesExtraction;
import info.magnolia.module.delta.RegisterModuleServletsTask;
import info.magnolia.module.delta.SetupModuleRepositoriesTask;
import info.magnolia.module.delta.Task;
import info.magnolia.module.model.Version;

/**
 * This class is optional and lets you manage the versions of your module,
 * by registering "deltas" to maintain the module's configuration, or other type of content.
 * If you don't need this, simply remove the reference to this class in the module descriptor xml.
 *
 * @see info.magnolia.module.DefaultModuleVersionHandler
 * @see info.magnolia.module.ModuleVersionHandler
 * @see info.magnolia.module.delta.Task
 */
public class OpenExpoAppMonitoringVersionHandler extends DefaultModuleVersionHandler {
	
	private static String MODULE_NAME = IConstantsOpenExpoMonitoring.MODULE_NAME;
	private static String MODULE_DESCRIPTION = IConstantsVersionHandler.MAGNOLIA_VERSION_TEXT  + " - " + MODULE_NAME;
	
	public OpenExpoAppMonitoringVersionHandler() {
		super();
		
	}
	
	
	@Override
    protected List<Task> getBasicInstallTasks(InstallContext installContext) {
		
        final List<Task> basicInstallTasks = new ArrayList<Task>();
        basicInstallTasks.add(new SetupModuleRepositoriesTask());
        basicInstallTasks.add(new ModuleBootstrapTask(MODULE_NAME, MODULE_DESCRIPTION, ImportUUIDBehavior.IMPORT_UUID_CREATE_NEW));
        basicInstallTasks.add(new ModuleFilesExtraction());
        basicInstallTasks.add(new RegisterModuleServletsTask());
        
        return basicInstallTasks;
    }
	
	
	
	
	
	@Override
	public List<Delta> getDeltas(InstallContext installContext, Version from) {
		
        if (from == null) {
            return Collections.singletonList(getInstall(installContext));
        }

        return getUpdateDeltas(installContext, from);
    }

	@Override
    protected List<Delta> getUpdateDeltas(InstallContext installContext, Version from) {
		
		final Version toVersion = installContext.getCurrentModuleDefinition().getVersion();
		List<Delta> deltas = new LinkedList<Delta>();
		
		if(toVersion.isStrictlyAfter(from)){
			deltas = super.getUpdateDeltas(installContext, from);
		}
		
        return deltas;
    }
	
	
	@Override
	protected Delta getDefaultUpdate(InstallContext installContext) {
		
        final Version toVersion = installContext.getCurrentModuleDefinition().getVersion();
        final List<Task> defaultUpdateTasks = this.getDefaultUpdateTasks(toVersion);
        final List<Condition> defaultUpdateConditions = getDefaultUpdateConditions(toVersion);
        return DeltaBuilder.update(toVersion, "").addTasks(defaultUpdateTasks).addConditions(defaultUpdateConditions);
    }
	
	@Override
	protected List<Task> getDefaultUpdateTasks(Version forVersion) {
		
        List<Task> defaultUpdates = new ArrayList<Task>();
        defaultUpdates = super.getDefaultUpdateTasks(forVersion);
        defaultUpdates.add(new SetupModuleRepositoriesTask());
        defaultUpdates.add(new ModuleBootstrapTask(MODULE_NAME, MODULE_DESCRIPTION, ImportUUIDBehavior.IMPORT_UUID_CREATE_NEW));
        defaultUpdates.add(new ModuleFilesExtraction());
        defaultUpdates.add(new RegisterModuleServletsTask());
        return defaultUpdates;
    }
}
