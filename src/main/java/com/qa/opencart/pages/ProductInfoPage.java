package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.EllementUtil;

public class ProductInfoPage {

	// page class/page Library/Page Object
	private WebDriver driver;
	private EllementUtil eleUtil;

	private Map<String, String> productMap = new HashMap<String, String>();

	// 1. By private Locators
	private By productHeader = By.tagName("h1");
	private By imageCount = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content'] //ul[@class='list-unstyled'])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content'] //ul[@class='list-unstyled'])[2]/li");
	private By quantity = By.name("quantity");
	private By addToCart = By.id("button-cart");
	private By sucessMsg = By.xpath("//div[@id='product-product']//div[contains(text(),'have added')]");
	private By shoppingCart = By.linkText("shopping cart");

	// 2. Create page class const...
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new EllementUtil(driver);
	}

	public String getProductHeader() {
		String header = eleUtil.doGetElementText(productHeader);
		System.out.println(header);
		return header;
	}

	public int getProductImagesCount() {
		int totalImages = eleUtil.waitForElementsVisible(imageCount, 10).size();
		System.out.println("Image count for " + getProductHeader() + " : " + totalImages);
		return totalImages;
	}

//	  Brand: Apple
//	  Product Code: Product 16
//	  Reward Points: 600
//	  Availability: In Stock

	public void getProductMetaData() {
		List<WebElement> metaList = eleUtil.getElements(productMetaData);
		for (WebElement e : metaList) {
			String text = e.getText();
			String metaKey = text.split(":")[0].trim();
			String metaValue = text.split(":")[1].trim();
			productMap.put(metaKey, metaValue);
		}

	}

//	  $602.00
//	  Ex Tax: $500.00

	public void getProductPriceData() {
		List<WebElement> priceList = eleUtil.getElements(productPriceData);
		String price = priceList.get(0).getText();
		String exTaxPrice = priceList.get(1).getText().split(":")[1].trim();
		productMap.put("productPrice", price);
		productMap.put("extaxprice", exTaxPrice);
	}

	public Map<String, String> getProductDetailsMap() {
		productMap.put("header", getProductHeader());
		productMap.put("productimages", String.valueOf(getProductImagesCount()));
		getProductMetaData();
		getProductPriceData();
		System.out.println("product Details: \n" + productMap);
		return productMap;

	}

	public void addQuantityAndClick(String value) {
		eleUtil.doSendkeys(quantity, value);
		eleUtil.doClick(addToCart);
		//return getSuccessMsg();

	}

	public String getSuccessMsg() {
		String message = eleUtil.waitForElementVisible(sucessMsg, 5).getText();
		return message;
	}

	public ShoppingCart clickOnShoppingCart() {
		eleUtil.doClick(addToCart);
		return new ShoppingCart(driver);
	}

}
