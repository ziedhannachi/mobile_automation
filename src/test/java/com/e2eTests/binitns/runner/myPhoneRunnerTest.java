package com.e2eTests.binitns.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.testng.CucumberOptions;
import org.junit.runner.RunWith;
import static io.cucumber.testng.CucumberOptions.SnippetType.CAMELCASE;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"
                , "html:target/cucumber/report.html"
                , "summary"
        }
        ,features = {"src/spec/features"}
        ,glue = {"com.e2eTests.binitns.step_definitions"}
        ,snippets = CAMELCASE
        ,dryRun=false
        ,monochrome=true
        ,tags = "@REFELUS-6135"
)

public class myPhoneRunnerTest {

}
