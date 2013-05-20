package org.features.ws.server;

import javax.jws.WebService;

import org.togglz.core.context.FeatureContext;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.FeatureMap;

@WebService(endpointInterface = "org.features.ws.server.Features", serviceName = "Features")
public class TogglzFeatures implements Features {
    
    private FeatureMap featureMap;
    
    public TogglzFeatures(){
        this(FeatureContext.getFeatureManager());
    }
    
	public TogglzFeatures(FeatureManager manager) {
        featureMap = new FeatureMap(manager);
    }

    public boolean isActive(String name) {
        return featureMap.get(name);
	}

}
