package org.features.spring;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.togglz.core.Feature;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.repository.FeatureState;
import org.togglz.core.util.FeatureMap;
import org.togglz.core.util.NamedFeature;

import static org.hamcrest.CoreMatchers.notNullValue;

import static org.hamcrest.MatcherAssert.assertThat;

public class SpringFactoryTest {

    @Test
    public void canCreateServerFeatureManager() throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:META-INF/features/features-ws-server-togglz.xml");
        FeatureManager featureManager = context.getBean(FeatureManager.class);
        Set<Feature> features = featureManager.getFeatures();
        assertFeaturesExist(featureManager, features);
        assertFeaturesCanBeEnabled(featureManager, features);
    }

    @Test
    public void canCreateClientFeatureManager() throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:META-INF/features/features-ws-client-togglz.xml");
        FeatureManager featureManager = context.getBean(FeatureManager.class);
        assertThat(featureManager, notNullValue());
        Map<Object, Boolean> map = context.getBean(FeatureMap.class);
        assertThat(map, notNullValue());
    }

    private void assertFeaturesExist(FeatureManager featureManager, Set<Feature> features) {
        assertThat(features, ContainsFeature.containsFeature(featureManager, new NamedFeature("ID_1")));
        assertThat(features, ContainsFeature.containsFeature(featureManager, new NamedFeature("ID_2")));
    }

    private void assertFeaturesCanBeEnabled(FeatureManager featureManager, Set<Feature> features) {
        for (Feature feature : features) {
            FeatureState state = featureManager.getFeatureState(feature);
            if (!state.isEnabled()) {
                state.setEnabled(true);
            }
            featureManager.setFeatureState(state);
        }
        for (Feature feature : features) {
            FeatureState state = featureManager.getFeatureState(feature);
            assertThat(state.isEnabled(), CoreMatchers.is(true));
        }
    }

}
