package com.e2eTests.binitns.context;

import com.e2eTests.binitns.hook.Hooks;

public class TestContext {
	
    private Hooks hook;

    public TestContext() {
        hook = new Hooks();
    }

    public Hooks getHook() {
        return hook;
    }

}
