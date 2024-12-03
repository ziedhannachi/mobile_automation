package com.binitns.mobile.utils;

public class GlobalParams {
    // Default
    private String defaultPlatformName = "Android"; // "iOS"; //
    private String defaultDeviceName = "Pixel_5_API_33"; // "iPhone 15"; //
    private String defaultUuid = "emulator-5554"; // "83D146A1-5A8A-4D3D-B294-1A7E37363F91"; //

    // Général
    private static ThreadLocal<String> platformName = new ThreadLocal<String>();
    private static ThreadLocal<String> udid = new ThreadLocal<String>();
    private static ThreadLocal<String> deviceName = new ThreadLocal<String>();
    // Pour Android
    private static ThreadLocal<String> chromeDriverPort = new ThreadLocal<String>();
    private static ThreadLocal<String> systemPort = new ThreadLocal<String>();
    // Pour iOS
    private static ThreadLocal<String> wdaLocalPort = new ThreadLocal<String>();
    private static ThreadLocal<String> webkitDebugProxyPort = new ThreadLocal<String>();
    private static ThreadLocal<String> platformVersion = new ThreadLocal<String>();

    public void setPlatformName(String aPlatformName){
        platformName.set(aPlatformName);
    }

    public String getPlatformName(){
        return platformName.get();
    }

    public String getUDID() {
        return udid.get();
    }

    public void setUDID(String aUdid) {
        udid.set(aUdid);
    }

    public String getDeviceName() {
        return deviceName.get();
    }

    public void setDeviceName(String aDeviceName) {
        deviceName.set(aDeviceName);
    }

    public String getSystemPort() {
        return systemPort.get();
    }

    public void setSystemPort(String aSystemPort) {
        systemPort.set(aSystemPort);
    }

    public String getChromeDriverPort() {
        return chromeDriverPort.get();
    }

    public void setChromeDriverPort(String aChromeDriverPort) {
        chromeDriverPort.set(aChromeDriverPort);
    }

    public String getWdaLocalPort() {
        return wdaLocalPort.get();
    }

    public void setWdaLocalPort(String aWdaLocalPort) {
        wdaLocalPort.set(aWdaLocalPort);
    }

    public String getWebkitDebugProxyPort() {
        return webkitDebugProxyPort.get();
    }

    public void setWebkitDebugProxyPort(String aWebkitDebugProxyPort) {
        webkitDebugProxyPort.set(aWebkitDebugProxyPort);
    }

    public String getPlatformVersion() {
        return platformVersion.get();
    }

    public void setPlatformVersion(String aPlatformVersion) {
        platformVersion.set(aPlatformVersion);
    }

    public void initializeGlobalParams(){
        GlobalParams params = new GlobalParams();

        params.setPlatformName(System.getProperty("platformName", defaultPlatformName));
        params.setUDID(System.getProperty("udid", defaultUuid));
        params.setDeviceName(System.getProperty("deviceName", defaultDeviceName));

        switch(params.getPlatformName()){
            case "Android":
                params.setSystemPort(System.getProperty("systemPort", "10000"));
                params.setChromeDriverPort(System.getProperty("chromeDriverPort", "11000"));
                break;
            case "iOS":
                params.setWdaLocalPort(System.getProperty("wdaLocalPort", "10002"));
                params.setWebkitDebugProxyPort(System.getProperty("webkitDebugProxyPort", "11002"));
                break;
            default:
                throw new IllegalStateException("-> Erreur le platformName n'est pas spécifié ou inconnu 🥹");
        }
    }

}

