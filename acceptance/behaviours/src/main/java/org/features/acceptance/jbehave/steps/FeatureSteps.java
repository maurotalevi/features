package org.features.acceptance.jbehave.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.core.io.support.PropertiesLoaderUtils.loadAllProperties;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;

import org.features.ws.client.WebServiceStateRepository;
import org.jbehave.core.annotations.AsParameterConverter;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.model.OutcomesTable;
import org.jbehave.core.steps.Parameters;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.manager.FeatureManagerBuilder;
import org.togglz.core.manager.PropertyBasedFeatureProvider;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.spi.FeatureProvider;
import org.togglz.core.util.ConstructorBasedActiveFeatureMap;


public class FeatureSteps {

	private FeatureManager featureManager;

	public FeatureSteps(FeatureManager featureManager) {
		this.featureManager = featureManager;
	}

	@Given("the features web service")
	public void givenTheWebService() {
		assertThat(featureManager, notNullValue());
	}

	@Given("the web service with WSDL url $url, service name $serviceName and activation user $activationUser")
	public void givenTheWebService(URL wsdlURL, QName serviceName, String activationUser)
			throws IOException {
		featureManager = buildFeatureManager(wsdlURL, serviceName, activationUser);
	}

	private FeatureManager buildFeatureManager(URL wsdlURL, QName serviceName, String activationUser)
			throws IOException {
		FeatureProvider featureProvider = new PropertyBasedFeatureProvider(
				loadAllProperties("META-INF/features/features.properties"));
		StateRepository stateRepository = new WebServiceStateRepository(
				wsdlURL, serviceName, activationUser);
		return new FeatureManagerBuilder().featureProvider(featureProvider)
				.stateRepository(stateRepository).build();
	}

	@Then("the features are: $table")
	public void thenTheFeaturesAre(ExamplesTable table) {
		Map<String,Boolean> activeFeatures = new ConstructorBasedActiveFeatureMap(featureManager);
		OutcomesTable outcomes = new OutcomesTable();
		for (Parameters row : table.getRowsAsParameters()) {
			String name = row.valueAs("name", String.class);
			String active = row.valueAs("active", String.class);
			outcomes.addOutcome(name, active,
					equalTo(Boolean.toString(activeFeatures.get(name))));
		}
		outcomes.verify();
	}

	@AsParameterConverter
	public URL toURL(String value) {
		try {
			return new URL(value);
		} catch (MalformedURLException e) {
			throw new RuntimeException(value);
		}
	}

	@AsParameterConverter
	public QName toQName(String value) {
		return QName.valueOf(value);
	}

}
