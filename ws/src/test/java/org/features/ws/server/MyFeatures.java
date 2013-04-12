package org.features.ws.server;

import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

enum MyFeatures implements Feature {

    @EnabledByDefault
    @Label("Feature 1")
    ONE,

    @Label("Feature 2")
    TWO;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }

}