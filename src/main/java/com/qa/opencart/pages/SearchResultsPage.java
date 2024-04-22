package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.EllementUtil;

public class SearchResultsPage {

	//page class/page Library/Page Object
	private WebDriver driver;
	private EllementUtil eleUtil;
	
	//1. By private Locators
	private By searchProducts = By.cssSelector("div.product-thumb");
	
	
	
	//2. Create page class const...
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new EllementUtil(driver);
	}
	
	public int getSearchProductCount() {
		return eleUtil.waitForElementsVisible(searchProducts, 10).size();
	}
	
	public ProductInfoPage selectProduct(String productName) {
		System.out.println("saerching for product: " + productName);
		eleUtil.waitForElementVisible(By.linkText(productName), 10).click();
		return new ProductInfoPage(driver);
	}
	
	
	
	
}
