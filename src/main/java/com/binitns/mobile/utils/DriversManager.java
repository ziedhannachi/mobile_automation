package com.binitns.mobile.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.io.IOException;

public class DriversManager {
    private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    TestsUtils utils = new TestsUtils();

    public AppiumDriver getDriver(){
        return driver.get();
    }

    public void setDriver(AppiumDriver aDriver){
        driver.set(aDriver);
    }

    public void initializeDriver() throws Exception {
        AppiumDriver driver = null;
        GlobalParams params = new GlobalParams();
        PropertiesManager props = new PropertiesManager();

        if(driver == null){
            try{
                utils.log().info("-> Initialisation du driver Appium 🤩");
                switch(params.getPlatformName()){
                    case "Android":
                        driver = new AndroidDriver(new ServerManager().getServer().getUrl(), new CapabilitiesManager().getCapabilities());
                        break;
                    case "iOS":
                        driver = new IOSDriver(new ServerManager().getServer().getUrl(), new CapabilitiesManager().getCapabilities());
                        break;
                }
                if(driver == null){
                    throw new Exception("-> Erreur lors de l'initialisation du driver 🥹");
                }
                utils.log().info("-> Le Driver a bien été initialisé et prêt 🥳");
                this.driver.set(driver);
            } catch (IOException error) {
                utils.log().fatal("-> Error 😱 ! L'initialisation du Driver a échouée. 🥹" + error.toString());
                error.printStackTrace();
                throw error;
            }
        }

    }


}

