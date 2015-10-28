package com.example;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = { "pretty",
				"html:target/cucumber",
				"json:target/cucumber.json",
		"junit:target/cucumber.xml" },
		monochrome = true,
		tags = { "~@Ignore" },
		features = "src/test/java/com/example/" // refer to Feature file
		)
public class RunerTest {
}
