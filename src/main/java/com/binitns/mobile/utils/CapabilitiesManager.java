package com.binitns.mobile.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;

public class CapabilitiesManager {
    TestsUtils utils = new TestsUtils();

    // Configuration des capability pour les deux platform Androi et iOS
    public DesiredCapabilities getCapabilities() throws IOException {
        GlobalParams params = new GlobalParams();
        Properties props = new PropertiesManager().getProps();

        try {

            utils.log().info("-> Configutation des capabilities 🙂");
            DesiredCapabilities capability = new DesiredCapabilities();
            capability.setCapability("platformName", params.getPlatformName());
            capability.setCapability("udid", params.getUDID());
            capability.setCapability("deviceName", params.getDeviceName());

            switch(params.getPlatformName()){
                case "Android":
                    capability.setCapability("automationName", props.getProperty("androidAutomationName"));
                    capability.setCapability("appPackage", props.getProperty("androidAppPackage"));
                    capability.setCapability("appActivity", props.getProperty("androidAppActivity"));
                    capability.setCapability("systemPort", params.getSystemPort());
                    capability.setCapability("chromeDriverPort", params.getChromeDriverPort());
                    String androidAppUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                            + File.separator + "resources" + File.separator + "apps" + File.separator + "app-pprod_1.87.01.apk";
                    utils.log().info("appUrl is : " + androidAppUrl);
                    capability.setCapability("app", androidAppUrl);
                    capability.setCapability("autoGrantPermissions", true);
                    utils.log().info("capability is : " + capability);
                    break;
                case "iOS":
                    capability.setCapability("automationName", props.getProperty("iOSAutomationName"));
                    String iOSAppUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                            + File.separator + "resources" + File.separator + "apps" + File.separator + "pprod.app";
                    utils.log().info("appUrl is : " + iOSAppUrl);
                    capability.setCapability("bundleId", props.getProperty("iOSBundleId"));
                    //capability.setCapability("wdaLocalPort", params.getWdaLocalPort());
                    //capability.setCapability("webkitDebugProxyPort", params.getWebkitDebugProxyPort());
                    capability.setCapability("wdaConnectionTimeout", 240000);
                    capability.setCapability("platformVersion", params.getPlatformVersion());
                    capability.setCapability("app", iOSAppUrl);
                    break;
            }

            return capability;

        } catch (Exception error) {
            error.printStackTrace();
            utils.log().fatal("-> Error lors de la récupération et configuration des capabilities 🥹" + error.toString());
            throw error;
        }
    }
}
