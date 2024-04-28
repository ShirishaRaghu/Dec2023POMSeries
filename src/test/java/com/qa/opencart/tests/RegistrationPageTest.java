package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.StringUtils;

import io.qameta.allure.Step;

public class RegistrationPageTest extends BaseTest{

	
	@BeforeClass
	public void regSetup() {
		registrationPage = 	loginPage.navigateToRegisterPage();
	}
	
	@DataProvider
	public Object[][] getUserRegTestData() {
		return new Object[][] {
			{"gaurav", "gupta", "3456789765", "gg@123", "yes"},
			//{"shilpa", "jy",  "3456789543", "shilpa@123", "yes"},
			//{"om", "sharma",  "3456789678", "om@123", "no"}
		};
	}
	
	
	@Step("checking user registration")
	@Test(dataProvider="getUserRegTestData")
	public void userRegTest(String firstName, String lastName, String telephone, String password, String subscribe){
		Assert.assertTrue(registrationPage.userRegister(firstName, lastName,StringUtils.getRandomEmailId(),  telephone, password, subscribe));
	
	
	}	
	
	
}
