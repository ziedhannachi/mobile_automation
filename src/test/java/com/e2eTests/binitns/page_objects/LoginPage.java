package com.e2eTests.binitns.page_objects;

import org.openqa.selenium.WebElement;
import com.e2eTests.binitns.base.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPage extends BasePage{
	
	@AndroidFindBy(xpath = "//android.widget.EditText[@content-desc=\"test-Username\"]")
	//@iOSXCUITFindBy (xpath = "(//XCUIElementTypeOther[@name=\"Allow calls and messages You'll get a notification when someone calls you or sends you a text message Required for the app to work properly\"])[3]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeButton")
	private WebElement username;
	
	
	public LoginPage fillUsername(String name) {
		sendKeys(username, name);
		return new LoginPage();
	}

}
