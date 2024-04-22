package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.logger.Log;
import com.qa.opencart.utils.EllementUtil;
import com.qa.opencart.utils.TimeUtil;

import io.qameta.allure.Step;

public class LoginPage {

	
	//Page class/Page library/Page object
	private WebDriver driver;
	private EllementUtil eleUtil;
	
	//1. Private By Locators
	
	private By  emailId = By.id("input-email");
	private By  password = By.id("input-password");
    private By loginButton = By.xpath("//input[@value='Login']");
    private By forgotPWDLink = 	By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");
	
	//2. Public Page Class Const....
    public LoginPage(WebDriver driver) {
    	this.driver=driver;
    	eleUtil = new EllementUtil(driver);
    }
    
    
	//3. Public Page Actions/Method
    @Step("getting login page title")
	public String getLoginPageTitle() {
		
		String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, TimeUtil.DEFAULT_MEDIUM_TIME);
		//System.out.println("login page title : " + title);
		Log.info("login page title : " + title);
		return title;
	}
	
    
    @Step("getting login page url....")
	public String getLoginPageURL() {
		
		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_URL_FRACTION, TimeUtil.DEFAULT_MEDIUM_TIME);
		System.out.println("login page url : " + url);
		return url;
	}
	
	@Step("getting the state for forgot pwd link exist....")
    public boolean isForgotPwdLinkExist() {
		return eleUtil.isElementDisplayed(forgotPWDLink);
	}
	
	
	@Step("login with username {0} and password {1}")
	public AccountsPage doLogin(String username, String pwd) {
		System.out.println("user cred : " + username + " : " + pwd);
		eleUtil.waitForElementVisible(emailId, TimeUtil.DEFAULT_LONG_TIME).sendKeys(username);
		eleUtil.doSendkeys(password, pwd);
		eleUtil.doClick(loginButton);
		
		return new AccountsPage(driver);//Landing page object(TDD)
		
	}
	
	
	@Step("navigating to registrtion page....")
	public RegistrationPage navigateToRegisterPage() {
		eleUtil.waitForElementVisible(registerLink, 10).click();
	 return new RegistrationPage(driver);
	}
	
	
//	deac2023@gmail.com
//	Automation@12345
	
	
	//https://mkyong.com/maven/how-to-install-maven-in-windows/
}
