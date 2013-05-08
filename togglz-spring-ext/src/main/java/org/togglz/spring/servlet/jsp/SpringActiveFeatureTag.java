package org.togglz.spring.servlet.jsp;

import org.springframework.web.context.WebApplicationContext;
import org.togglz.core.util.FeatureMap;
import org.togglz.jsp.ActiveFeatureTag;

import static org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext;

@SuppressWarnings("serial")
public class SpringActiveFeatureTag extends ActiveFeatureTag {
    
	@Override
    protected boolean isFeatureActive() {
		WebApplicationContext context = getWebApplicationContext(pageContext
				.getServletContext());
		FeatureMap map = context.getBean(FeatureMap.class);
		return map.containsKey(name);
	}

}
