package com.e2eTests.binitns.driver;

public class AppData {

	public static String platform = System.getProperty("platform", "ios");
	public static String useGesturePlugin = System.getProperty("useGesturePlugin", "false");
	public static String chromeAutoDownloadDriver = System.getProperty("chromeAutoDownloadDriver", "false");

}
