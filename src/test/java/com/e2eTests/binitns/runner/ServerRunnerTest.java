package com.e2eTests.binitns.runner;

import com.binitns.mobile.utils.ServerManager;
import com.e2eTests.binitns.base.RetryRule;
import com.binitns.mobile.utils.DriversManager;
import com.binitns.mobile.utils.GlobalParams;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.apache.logging.log4j.ThreadContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import static io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"
                , "html:target/cucumber/report.html"
                , "summary"
        }
        ,features = { "src/test/resources/features/agFrais.feature"}
        ,glue = {"com.groupama.mobile.stepDefinitions"}
        ,snippets = CAMELCASE
        ,dryRun=false
        ,monochrome=true
        ,tags = "@test"
)
public class ServerRunnerTest {

    @BeforeClass
    public static void initialize() throws Exception {
        GlobalParams params = new GlobalParams();
        params.initializeGlobalParams();

        ThreadContext.put("ROUTINGKEY", params.getPlatformName() + "_"
                + params.getDeviceName());

        new ServerManager().startServer();
        new DriversManager().initializeDriver();
    }

    @Rule
    public TestRule retryRule = new RetryRule(3);

    @AfterClass
    public static void quit(){
        DriversManager driverManager = new DriversManager();
        if(driverManager.getDriver() != null){
            driverManager.getDriver().quit();
            driverManager.setDriver(null);
        }
        ServerManager serverManager = new ServerManager();
        if(serverManager.getServer() != null){
            serverManager.getServer().stop();
        }
    }
}

