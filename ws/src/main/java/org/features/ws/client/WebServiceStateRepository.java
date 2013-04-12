package org.features.ws.client;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.features.ws.server.Features;
import org.togglz.core.Feature;
import org.togglz.core.activation.UsernameActivationStrategy;
import org.togglz.core.repository.FeatureState;
import org.togglz.core.repository.StateRepository;


/**
 * An implementation of a Togglz {@link StateRepository} that retrieves the
 * feature state via a WebService.
 * 
 * @author Mauro Talevi
 */
public class WebServiceStateRepository implements StateRepository {

	private final URL wsdlURL;
	private final QName serviceName;
	private final String activationUser;
	private Features features;

	public WebServiceStateRepository(URL wsdlURL, QName serviceName) {
		this(wsdlURL, serviceName, "togglz-ws-user");
	}

	public WebServiceStateRepository(URL wsdlURL, QName serviceName, String activationUser) {
		this.wsdlURL = wsdlURL;
		this.serviceName = serviceName;
		this.activationUser = activationUser;
	}

	public FeatureState getFeatureState(Feature feature) {
		boolean active = features().isActive(feature.name());
		FeatureState state = new FeatureState(feature, active);
		state.setStrategyId(UsernameActivationStrategy.ID);
		state.setParameter(UsernameActivationStrategy.PARAM_USERS, activationUser);
		return state;
	}
	
	public void setFeatureState(FeatureState featureState) {
		throw new UnsupportedOperationException();
	}

	private Features features() {
		if (features == null) {
			features = create(Features.class);
		}
		return features;
	}

	private <T> T create(Class<T> serviceClass) {
		Service service = Service.create(wsdlURL, serviceName);
		return service.getPort(serviceClass);
	}

}
