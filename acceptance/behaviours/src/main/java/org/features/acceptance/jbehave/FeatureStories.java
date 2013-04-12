package org.features.acceptance.jbehave;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML_TEMPLATE;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML_TEMPLATE;

import java.util.Arrays;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.spring.SpringApplicationContextFactory;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.springframework.context.ApplicationContext;

public class FeatureStories extends JUnitStories {
	
	public FeatureStories() {
		 configuredEmbedder().useMetaFilters(Arrays.asList("-skip"));
	}

	@Override
	public Configuration configuration() {
		StoryReporterBuilder reporterBuilder = new StoryReporterBuilder()
				.withCodeLocation(codeLocationFromClass(FeatureStories.class))
				.withDefaultFormats()
				.withFormats(CONSOLE, TXT, HTML_TEMPLATE, XML_TEMPLATE)
				.withFailureTrace(true).withFailureTraceCompression(true);
		return new MostUsefulConfiguration().useStoryLoader(
				new LoadFromClasspath(FeatureStories.class))
				.useStoryReporterBuilder(reporterBuilder);
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		ApplicationContext context = new SpringApplicationContextFactory(
				"META-INF/jbehave/jbehave-steps.xml").createApplicationContext();
		return new SpringStepsFactory(configuration(), context);
	}

	@Override
	protected List<String> storyPaths() {
		return new StoryFinder().findPaths(
				codeLocationFromClass(this.getClass()).getFile(), asList("**/"
						+ System.getProperty("story.filter", "*") + ".story"),
				null);
	}

}
