package com.binitns.mobile.utils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;
import java.util.HashMap;

public class ServerManager {
    TestsUtils utils = new TestsUtils();
    private static ThreadLocal<AppiumDriverLocalService> server = new ThreadLocal<>();

    // Return le serveur d'écoute Appium
    public AppiumDriverLocalService getServer(){
        return server.get();
    }

    public void startServer(){
        utils.log().info("-> Démarrage du serveur Appium 🙂");
        AppiumDriverLocalService server = WindowsGetAppiumService();
        server.start();
        if(server == null || !server.isRunning()){
            utils.log().fatal("-> Erreur le serveur Appium n'est pas démarré 🥹.");
            throw new AppiumServerHasNotBeenStartedLocallyException("Le serveur Appium n'est pas démarré");
        }
        server.clearOutPutStreams();
        this.server.set(server);
        utils.log().info("-> Le serveur Appium est maintenant accessible et à l'écoute 😍");
        utils.log().info(getServer().getUrl());
    }

    public AppiumDriverLocalService getAppiumServerDefault() {
        return AppiumDriverLocalService.buildDefaultService();
    }

    public AppiumDriverLocalService WindowsGetAppiumService() {
        GlobalParams params = new GlobalParams();
        // Créer une instance du service Appium en utilisant les options de ligne de commande
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                //.usingPort(4723)
                        .usingAnyFreePort()
                .withArgument(GeneralServerFlag.LOG_LEVEL, "info")
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                //.withArgument(GeneralServerFlag.USE_PLUGINS, "device-farm,appium-dashboard")
                .withLogFile(new File(params.getPlatformName() + "_"
                        + params.getDeviceName() + File.separator + "Server.log")));
    }

    public AppiumDriverLocalService MacGetAppiumService() {
        GlobalParams params = new GlobalParams();
        HashMap<String, String> environment = new HashMap<String, String>();
       // environment.put("PATH", "enter_your_path_here" + System.getenv("PATH"));
        environment.put("ANDROID_HOME", "/Users/chamsoudine/Library/Android/sdk");
        environment.put("JAVA_HOME", "/usr/local/Cellar/openjdk/19/libexec/openjdk.jdk/Contents/Home");
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(new File("/usr/local/bin/node"))
                .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                .usingAnyFreePort()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withEnvironment(environment)
                .withLogFile(new File(params.getPlatformName() + "_"
                        + params.getDeviceName() + File.separator + "Server.log")));
    }


}

