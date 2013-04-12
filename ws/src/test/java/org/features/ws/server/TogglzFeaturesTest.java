package org.features.ws.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.features.ws.server.Features;
import org.features.ws.server.TogglzFeatures;
import org.junit.Test;
import org.togglz.core.context.ThreadLocalFeatureManagerProvider;
import org.togglz.core.manager.EnumBasedFeatureProvider;
import org.togglz.core.manager.FeatureManagerBuilder;


public class TogglzFeaturesTest {

    @Test
    public void canDetermineIfFeatureIsActive() {
        ThreadLocalFeatureManagerProvider.bind(new FeatureManagerBuilder().featureProvider(new EnumBasedFeatureProvider(MyFeatures.class)).build());
        Features features = new TogglzFeatures();
        assertTrue(features.isActive(MyFeatures.ONE.name()));
        assertFalse(features.isActive(MyFeatures.TWO.name()));
    }

}
