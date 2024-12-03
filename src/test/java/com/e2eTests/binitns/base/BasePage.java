package com.e2eTests.binitns.base;

import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.e2eTests.binitns.hook.Hooks;
import com.e2eTests.binitns.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;

public class BasePage extends Hooks {

	protected static ThreadLocal<AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
	protected static ThreadLocal<Properties> props = new ThreadLocal<Properties>();
	protected static ThreadLocal<HashMap<String, String>> strings = new ThreadLocal<HashMap<String, String>>();
	protected static ThreadLocal<String> platform = new ThreadLocal<String>();
	protected static ThreadLocal<String> dateTime = new ThreadLocal<String>();
	protected static ThreadLocal<String> deviceName = new ThreadLocal<String>();
	TestUtils utils = new TestUtils();

	public BasePage() {
		PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
	}

	public void setDriver(AppiumDriver driver2) {
		driver.set(driver2);
	}

	public Properties getProps() {
		return props.get();
	}

	public void setProps(Properties props2) {
		props.set(props2);
	}

	public HashMap<String, String> getStrings() {
		return strings.get();
	}

	public void setStrings(HashMap<String, String> strings2) {
		strings.set(strings2);
	}

	public String getPlatform() {
		return platform.get();
	}

	public void setPlatform(String platform2) {
		platform.set(platform2);
	}

	public String getDateTime() {
		return dateTime.get();
	}

	public void setDateTime(String dateTime2) {
		dateTime.set(dateTime2);
	}

	public String getDeviceName() {
		return deviceName.get();
	}

	public void setDeviceName(String deviceName2) {
		deviceName.set(deviceName2);
	}

	public void waitForVisibility(WebElement e) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(e));
	}

	public void clear(WebElement e) {
		waitForVisibility(e);
		e.clear();
	}

	public void click(WebElement e) {
		waitForVisibility(e);
		e.click();
	}

	public void click(WebElement e, String msg) {
		waitForVisibility(e);
		utils.log().info(msg);
		e.click();
	}

	public void sendKeys(WebElement e, String txt) {
		waitForVisibility(e);
		e.sendKeys(txt);
	}

	public void sendKeys(WebElement e, String txt, String msg) {
		waitForVisibility(e);
		utils.log().info(msg);
		e.sendKeys(txt);
	}

	public String getAttribute(WebElement e, String attribute) {
		waitForVisibility(e);
		return e.getDomAttribute(attribute);
	}

	// get name
	public String getText(WebElement e, String msg) {
		waitForVisibility(e);
		utils.log().info(msg);
		return e.getText();
	}

	public void acceptAlert() {
		getDriver().switchTo().alert().accept();
	}

	public void acceptPhotoPermissionAlert() {
		// Define the locator for the "Allow" button on the permission alert
		By allowButton = By.id("Allow"); // You might need to adjust this locator based on the actual label

		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
			WebElement allowElement = wait.until(ExpectedConditions.visibilityOfElementLocated(allowButton));
			allowElement.click();
			utils.log().info("Photo permission alert accepted");
		} catch (Exception e) {
			utils.log().error("Photo permission alert was not found or could not be accepted", e);
		}
	}

}
