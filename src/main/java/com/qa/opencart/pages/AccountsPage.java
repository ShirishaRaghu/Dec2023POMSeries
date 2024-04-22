package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.EllementUtil;

public class AccountsPage {

	//Page class/Page library/Page object
	private WebDriver driver;
	private EllementUtil eleUtil;
	
	
	//1. Private By Locators
	private By logoutLink = By.linkText("Logout");
	private By myAccountLink = By.linkText("My Account");
	private By headers = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");
	
	//2. //2. Public Page Class Const....
	public AccountsPage(WebDriver driver) {
		this.driver=driver;
		eleUtil = new  EllementUtil(driver);
	}
	
	
	//3. Public Page Actions/Method
	public String getAccPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.ACCOUNTS_PAGE_TITLE, 5);
		System.out.println("Account page title : " + title);
		return title;
	}
	
	public String getAccPageURL() {
		String url = eleUtil.waitForURLContains(AppConstants.ACC_PAGE_URL_FRACTION, 5);
	    System.out.println("Account page url : " + url);
	    return url;
	}
	
	public boolean isLogoutLinkExist() {
		return eleUtil.waitForElementVisible(logoutLink, 10).isDisplayed();
	}
	
	public boolean isMyAccountLinkExist() {
		return eleUtil.waitForElementVisible(myAccountLink, 10).isDisplayed();
	}
	
	
	
	public List<String> getAccountsPageHeaderList() {
		List<WebElement> headerEleList = eleUtil.getElements(headers);
		List<String> hedersList = new ArrayList<String>();
		for(WebElement e : headerEleList) {
			String header = e.getText();
			hedersList.add(header);
		}
		return hedersList;
	}
	
	public SearchResultsPage doSearch(String searchKey) {
		System.out.println("searching for : " + searchKey);
		eleUtil.doSendkeys(search, searchKey);
		eleUtil.doClick(searchIcon);
		return new SearchResultsPage(driver);
	}
	
	
}
