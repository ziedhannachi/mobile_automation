package com.e2eTests.binitns;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import static io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/spec/features", 
		monochrome = true, 
		plugin = { "pretty",
		"html:target/report/cucumber-report.html" }, 
		tags = ("@login"), 
		snippets = CAMELCASE
		)

public class RunMobileSuiteTest {

}
