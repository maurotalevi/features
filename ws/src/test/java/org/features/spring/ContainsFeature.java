package org.features.spring;

import java.util.Set;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.togglz.core.Feature;
import org.togglz.core.manager.FeatureManager;

public class ContainsFeature extends BaseMatcher<Set<Feature>> {

    private FeatureManager featureManager;
    private Feature feature;

	public ContainsFeature(FeatureManager featureManager, Feature feature) {
		this.featureManager = featureManager;
        this.feature = feature;
	}

	@Override
	public boolean matches(Object item) {
		boolean found = false;
		@SuppressWarnings("unchecked")
		Set<Feature> features = (Set<Feature>) item;
		for (Feature f : features) {
			if (f.name().equals(feature.name()) && featureManager.isActive(f) == featureManager.isActive(feature) ) {
				found = true;
				continue;
			}
		}
		return found;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("a set of features containing " + feature);
	}

	public static ContainsFeature containsFeature(FeatureManager featureManager, Feature feature) {
		return new ContainsFeature(featureManager, feature);
	}

}
