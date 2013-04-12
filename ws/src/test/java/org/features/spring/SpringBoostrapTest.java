package org.features.spring;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.togglz.core.Feature;
import org.togglz.core.bootstrap.TogglzBootstrap;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.repository.FeatureState;
import org.togglz.core.util.ConstructorBasedActiveFeatureMap;
import org.togglz.core.util.PropertyBasedFeature;

public class SpringBoostrapTest {

	@Test
	public void canBoostrapServerUsingSpring() throws IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:META-INF/features/features-ws-server-togglz.xml");
		TogglzBootstrap bootstrap = context.getBean(TogglzBootstrap.class);
		FeatureManager featureManager = bootstrap.createFeatureManager();
		Set<Feature> features = featureManager.getFeatures();
		assertFeaturesExist(features);
		assertFeaturesCanBeEnabled(featureManager, features);
	}

	@Test
	public void canBoostrapClientUsingSpring() throws IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:META-INF/features/features-ws-client-togglz.xml");
		FeatureManager featureManager = context.getBean(FeatureManager.class);
		assertThat(featureManager, notNullValue());
		Map<String, Boolean> map = context.getBean(ConstructorBasedActiveFeatureMap.class);
		assertThat(map, notNullValue());
	}

	private void assertFeaturesExist(Set<Feature> features) {
		assertThat(features,
				ContainsFeature.containsFeature(new PropertyBasedFeature(
						"ID_1", "ID 1;true")));
		assertThat(features,
				ContainsFeature.containsFeature(new PropertyBasedFeature(
						"ID_2", "ID 2;false")));
	}

	private void assertFeaturesCanBeEnabled(FeatureManager featureManager,
			Set<Feature> features) {
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
