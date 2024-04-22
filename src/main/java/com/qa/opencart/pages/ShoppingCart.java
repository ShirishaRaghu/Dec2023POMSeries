package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.EllementUtil;

public class ShoppingCart {

	//page class
	private WebDriver driver;
	private EllementUtil eleUtil;
	
	//private locators
	
	private By enterCupon = By.name("coupon"); 
	private By applyCupon = By.id("button-coupon");
	private By estimateShipping = By.xpath("//a[@class='accordion-toggle']");
	
	
	//constr....
	public ShoppingCart(WebDriver driver) {
		this.driver = driver;
		eleUtil = new EllementUtil(driver);
	}
	
	//Actins
	public void useCuponCode(String value) {
		//eleUtil.doClick(cuponCode);
		eleUtil.doSendkeys(enterCupon, value);
		eleUtil.doClick(applyCupon);
	}
	
	
	
}
