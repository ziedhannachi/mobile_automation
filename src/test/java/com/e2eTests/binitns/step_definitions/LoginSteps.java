package com.e2eTests.binitns.step_definitions;

import io.cucumber.java.en.When;

import com.e2eTests.binitns.base.BasePage;
import com.e2eTests.binitns.page_objects.LoginPage;

public class LoginSteps extends BasePage {

	LoginPage loginPage = new LoginPage();
	BasePage basePage = new BasePage();

	@When("i fill username {string}")
	public void iFillUsername(String username) {
		loginPage.fillUsername(username);
	}




}
