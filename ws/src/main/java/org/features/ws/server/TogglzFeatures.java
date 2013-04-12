package org.features.ws.server;

import java.util.NoSuchElementException;

import javax.jws.WebService;

import org.togglz.core.Feature;
import org.togglz.core.context.FeatureContext;
import org.togglz.core.manager.FeatureManager;

@WebService(endpointInterface = "org.features.ws.server.Features", serviceName = "Features")
public class TogglzFeatures implements Features {

	public boolean isActive(String name) {
		FeatureManager manager = FeatureContext.getFeatureManager();
		try {
			Feature feature = feature(manager, name);
			return manager.isActive(feature);
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private Feature feature(FeatureManager featureManager, String name) {
		for (Feature feature : featureManager.getFeatures()) {
			if (name.equals(feature.name())) {
				return feature;
			}
		}
		throw new NoSuchElementException(name);
	}

}
