package com.rd.utils.setup;

import java.io.IOException;
import java.math.BigInteger;

import javax.jcr.RepositoryException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rd.utils.constants.IConstantsSymbols;
import com.rd.utils.constants.IConstantsVersionHandler;

import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.BootstrapResourcesTask;
import info.magnolia.module.delta.TaskExecutionException;

/**
 * temporary fix for getting rid of ItemExistsException coming from JCR.
 * we need to call our custom BootStrap Utility handler, for calling the session.save()
 * 
 * @see https://issues.apache.org/jira/browse/JCR-3239
 */
public class ModuleBootstrapTask extends BootstrapResourcesTask {
    
    protected int collisionBehavior;
    
    
    private static final Logger log = LoggerFactory.getLogger(ModuleBootstrapTask.class);

    private String MODULE_NAME;
    private String INSTALL_BOOTSTRAP_PATH;
    
    public ModuleBootstrapTask(String name, String description, int importUUIDBehavior) {
        super(name, description, importUUIDBehavior);
        this.collisionBehavior = importUUIDBehavior;
        MODULE_NAME = name;
        INSTALL_BOOTSTRAP_PATH = IConstantsVersionHandler.INSTALL_BOOTSTRAP_ROOT_FOLDER + IConstantsSymbols.UNIX_PATH_SEPARATOR 
                + MODULE_NAME + IConstantsSymbols.UNIX_PATH_SEPARATOR + IConstantsVersionHandler.INSTALL_BOOTSTRAP_FINAL_FOLDER;
    }

    @Override
    public void execute(InstallContext installContext) throws TaskExecutionException {
        
    	try {
    		
            String[] resourcesToBootstrap = this.getResourcesToBootstrap(installContext);
            
            log.info(MODULE_NAME + " Module bootstrap task - collisionBehaviour: " + collisionBehavior);
            
            try {
            	
            	if (resourcesToBootstrap != null && resourcesToBootstrap.length > BigInteger.ZERO.intValue()) {
					
                    log.info("Install folder url: " + BootstrapResourcesTask.class.getClassLoader().getResource(INSTALL_BOOTSTRAP_PATH));
                    
                    ModuleBootstrapUtil.bootstrap(resourcesToBootstrap, collisionBehavior);
                    
				}else {
					log.debug("Install folder not found or empty");
				}
                
			} catch (NullPointerException e) {
				throw new TaskExecutionException("Could not bootstrap: " + e.getMessage(), e);
			}
            
        } catch (IOException e) {
            throw new TaskExecutionException("Could not bootstrap: " + e.getMessage(), e);
        } catch (RepositoryException e) {
            throw new TaskExecutionException("Could not bootstrap: " + e.getMessage(), e);
        }
    }

    /**
     * Filter our specific resources to bootstrap.
     */
    @Override
    protected boolean acceptResource(final InstallContext installContext, final String resourceName) {
    	
        return resourceName.startsWith(IConstantsSymbols.UNIX_PATH_SEPARATOR + INSTALL_BOOTSTRAP_PATH) && resourceName.endsWith(IConstantsVersionHandler.DOT_EXTENSION_BOOTSTRAP_FILE);
    }

}
