package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.EllementUtil;

import io.qameta.allure.Step;

public class RegistrationPage {

	//page class
	private WebDriver driver;
	private  EllementUtil eleUtil; 
	
	//Private locators
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmpassword = By.id("input-confirm");
	private By subscribeYes = By.xpath("//input[@name='newsletter' and @value='1']");
	private By subscribeNo = By.xpath("//input[@name='newsletter' and @value='0']");
	private By agreeCheckBox = By.name("agree");
	private By continueBtn = By.xpath("//input[@value='Continue']");
	
	private By sucessMsg = By.cssSelector("div#content h1");
	
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");

	//Create a const.....
	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new EllementUtil(driver);
	}
	
	@Step("User reg with: {0}, {1}, {2}, {3}, {4}, {5}")
	public boolean userRegister(String firstName, String lastName, String email, String telephone, String password,
			String subscribe) {
		
		eleUtil.waitForElementVisible(this.firstName, 10).sendKeys(firstName);
		eleUtil.doSendkeys(this.lastName, lastName);
		eleUtil.doSendkeys(this.email, email);
		eleUtil.doSendkeys(this.telephone, telephone);
		eleUtil.doSendkeys(this.password, password);
		eleUtil.doSendkeys(this.confirmpassword, password);
		
		if(subscribe.equalsIgnoreCase("yes")) {
		eleUtil.doClick(subscribeYes);
		}
		else {
			eleUtil.doClick(subscribeNo);
		}
		
		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueBtn);
		
		String regSuccessMessg = eleUtil.waitForElementVisible(sucessMsg, 5).getText();
	    System.out.println(regSuccessMessg);
		
	    if(regSuccessMessg.equals(AppConstants.USER_REG_SUCCESS_MESSG)) {
	    	eleUtil.doClick(logoutLink);
	    	eleUtil.doClick(registerLink);
	    	return true;
	    }
	    
	    return false;
	    
	    
	}
	
	
	
}
