package com.e2eTests.binitns.screen;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseScreen {
	public final WebDriver driver;
	public final WebDriverWait wait;

	public BaseScreen(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	public void waitUntilElementVisible(By by) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public void tap(By by) {
		waitUntilElementVisible(by);
		driver.findElement(by).click();
	}

	public void inputText(By by, String text) {
		waitUntilElementVisible(by);
		driver.findElement(by).sendKeys(text);
	}

}
