package org.features.acceptance.ps;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.togglz.core.util.ConstructorBasedActiveFeatureMap;

public class SpringBoostrapTest {

	@Test
	@Ignore("Requires features-ws-server")
	public void canBoostrapClientUsingSpring() throws IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:META-INF/features/features-ws-client-togglz.xml");
		Map<String, Boolean> activeFeatures = context
				.getBean(ConstructorBasedActiveFeatureMap.class);
		assertThat(activeFeatures.get("ID_1"), is(true));
		assertThat(activeFeatures.get("ID_2"), is(false));
	}

}
