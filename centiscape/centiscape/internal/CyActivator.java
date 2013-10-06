package org.cytoscape.centiscape.internal;

import java.util.Properties;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator {

    public CyApplicationManager cyApplicationManager;
    public CySwingApplication cyDesktopService;
    public CyServiceRegistrar cyServiceRegistrar;
    public CentiScaPeMenuAction menuaction;

    @Override
    public void start(BundleContext context) throws Exception {
        String version = new String("2.4");
        this.cyApplicationManager = getService(context, CyApplicationManager.class);
        this.cyDesktopService = getService(context, CySwingApplication.class);
        this.cyServiceRegistrar = getService(context, CyServiceRegistrar.class);
        menuaction = new CentiScaPeMenuAction(cyApplicationManager, "Centiscape" + version, this);
        Properties properties = new Properties();
        registerAllServices(context, menuaction, properties);
    }

    public CyServiceRegistrar getcyServiceRegistrar() {
        return cyServiceRegistrar;
    }

    public CyApplicationManager getcyApplicationManager() {
        return cyApplicationManager;
    }

    public CySwingApplication getcytoscapeDesktopService() {
        return cyDesktopService;
    }

    public CentiScaPeMenuAction getmenuaction() {
        return menuaction;
    }
}
