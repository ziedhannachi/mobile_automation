package com.e2eTests.binitns.hook;

import java.io.IOException;

import com.e2eTests.binitns.context.TestContext;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class CucumberHooks {
	
	    private TestContext testContext;

	    public CucumberHooks(TestContext testContext) {
	        this.testContext = testContext;
	    }

	    @Before
	    public void setUp() throws Throwable {
	        testContext.getHook().startServer();
	    }

	    @After
	    public void tearDown(Scenario scenario) throws IOException {
			testContext.getHook().tearDown(scenario);
	    }
	}


