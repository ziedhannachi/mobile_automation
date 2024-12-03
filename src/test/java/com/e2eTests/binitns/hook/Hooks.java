package com.e2eTests.binitns.hook;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.cucumber.java.Scenario;

public class Hooks {

	private static AppiumDriverLocalService service;
	private static AppiumDriver driver;

	public void startServer() throws Throwable {
		
	
		DesiredCapabilities cap = new DesiredCapabilities();

        cap.setCapability("platformName", "Android");
        cap.setCapability("appium:platformVersion", "15");
        cap.setCapability("appium:udid", "emulator-5554");
        cap.setCapability("appium:deviceName", "sdk_gphone64_x86_64");
        cap.setCapability("appium:automationName", "UiAutomator2");
        cap.setCapability("appium:noSign", true);
        cap.setCapability("appium:app", System.getProperty("user.dir") + "\\src\\test\\resources\\apk_files\\SauceLabs.apk");
        cap.setCapability("appium:appPackage", "com.swaglabsmobileapp");
        cap.setCapability("appium:appActivity", "com.swaglabsmobileapp.MainActivity");

        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AppiumDriver(url, cap);
	
	}


	public void tearDown(Scenario scenario) throws IOException {
		if (scenario.isFailed()) {
			final byte[] screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenShot, "image/png", "screenshot: " + scenario.getName());
		}

		if (driver != null) {
			try {
				driver.quit();
			} catch (Exception e) {
				System.err.println("Error quitting IOSDriver: " + e.getMessage());
			}
		}

		if (service != null) {
			service.stop();
		}
	}
	
	/* GETTER */
	public static AppiumDriver getDriver() {
		return driver;
	}
}
