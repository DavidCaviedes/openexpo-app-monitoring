
package com.rd.monitoring.setup;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rd.monitoring.constants.IConstantsVersionHandler;

import info.magnolia.cms.core.Path;
import info.magnolia.cms.util.StringLengthComparator;
import info.magnolia.context.MgnlContext;
import info.magnolia.importexport.BootstrapUtil;
import info.magnolia.importexport.DataTransporter;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.objectfactory.Components;
import info.magnolia.repository.DefaultRepositoryManager;
import info.magnolia.repository.RepositoryManager;


public class ModuleBootstrapUtil extends BootstrapUtil {
    
    private static final Logger log = LoggerFactory.getLogger(ModuleBootstrapUtil.class);
    
    /**
     * Like the original, but call session.save() after deletion
     * 
     * @param resourceNames
     * @param importUUIDBehavior
     * @throws IOException
     * @throws RepositoryException
     */
    public static void bootstrap(String[] resourceNames, int importUUIDBehavior) throws IOException, RepositoryException {
        // sort by length --> import parent node first
        List<String> list = new ArrayList<String>(Arrays.asList(resourceNames));
        if (list.contains(null)) {
            throw new IllegalArgumentException("Resource names contain a <null> entry that cannot be processed.");
        }

        Collections.sort(list, new StringLengthComparator());

        for (Iterator<String> iter = list.iterator(); iter.hasNext(); ) {
            String resourceName = iter.next();

            String name = getFilenameFromResource(resourceName, IConstantsVersionHandler.DOT_EXTENSION_BOOTSTRAP_FILE);
            String workspace = getWorkspaceNameFromResource(resourceName);
            String pathName = getPathnameFromResource(resourceName);
            String fullPath = getFullpathFromResource(resourceName);

            log.debug("Will bootstrap {}", resourceName);

            final InputStream stream = BootstrapUtil.class.getResourceAsStream(resourceName);
            if (stream == null) {
                throw new IOException("Can't find resource to bootstrap at " + resourceName);
            }

            // if the node already exists we will keep the order
            String nameOfNodeAfterTheImportedNode = null;

            // check clustered workspace
            RepositoryManager repoManager = Components.getComponent(RepositoryManager.class);
            if (repoManager instanceof DefaultRepositoryManager) {
                boolean isClusteredWorkspace = ((DefaultRepositoryManager) repoManager).isClusteredWorkspace(workspace);
                boolean isMasterCluster = ((DefaultRepositoryManager) repoManager).isClusterMaster();

                if (isClusteredWorkspace && !isMasterCluster) {
                    log.info("Skipping bootstrapping of resource '{}' because workspace '{}' is clustered and node is not cluster master ", resourceName, workspace);
                    continue;
                }
            }

            final Session session = MgnlContext.getJCRSession(workspace);
            // if the path already exists --> delete it
            try {

                // session can be null if module is not properly registered and the repository has not been created
                if (session != null && StringUtils.isNotEmpty(fullPath) && session.nodeExists(fullPath)) {
                    // but keep the order
                    Node node = session.getNode(fullPath);
                    if (!NodeUtil.isLastSibling(node)) {
                        nameOfNodeAfterTheImportedNode = NodeUtil.getSiblingAfter(node).getName();
                    }
                    if (node.getDepth() == 1) { // deletion on the first level doesn't sometimes refresh cache and leads into ItemExistsException on import, see JCR-3279/MAGNOLIA-4544
                        Node parent = node.getParent();
                        Node tmp = NodeUtil.createPath(parent, Path.getUniqueLabel(parent, "tmp"), NodeTypes.Content.NAME);
                        NodeUtil.moveNode(node, tmp);
                        tmp.remove();
                    } else {
                        node.remove();
                    }
                    
                    session.save();
                    
                    log.warn("Deleted already existing node for bootstrapping: {}", fullPath);
                }
            } catch (RepositoryException e) {
                IOUtils.closeQuietly(stream);
                throw new RepositoryException("Can't check existence of node for bootstrap file: [" + name + "]", e);
            }
            DataTransporter.importXmlStream(stream, workspace, pathName, name, false, importUUIDBehavior, false, true);

            if (nameOfNodeAfterTheImportedNode != null) {
                Node newNode = session.getNode(fullPath);
                NodeUtil.orderBefore(newNode, nameOfNodeAfterTheImportedNode);
            }

        }
    }

}
