package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductPageInfoTest extends BaseTest {
//AAA --> Arrange Act Assert
//TC --> One hard assertion or Multiple soft assertion

	@BeforeClass
	public void infoPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@DataProvider
	public Object[][] getProductSearchData() {
		return new Object[][] { { "macbook", "MacBook Pro" }, { "imac", "iMac" },
				{ "samsung", "Samsung SyncMaster 941BW" }, { "samsung", "Samsung Galaxy Tab 10.1" } };
	}

	@Test(dataProvider = "getProductSearchData")
	public void productHeaderTest(String searchKey, String productName) {
		searchResultsPage = accPage.doSearch(searchKey);
		productinfopage = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(productinfopage.getProductHeader(), productName);
	}

	@DataProvider
	public Object[][] getProductImagesData() {
		return new Object[][] { { "macbook", "MacBook Pro", 4 }, { "imac", "iMac", 3 },
				{ "samsung", "Samsung SyncMaster 941BW", 1 }, { "samsung", "Samsung Galaxy Tab 10.1", 7 } };
	}

	@Test(dataProvider = "getProductImagesData")
	public void productImageCountTest(String searchKey, String productName, int imagesCount) {
		searchResultsPage = accPage.doSearch(searchKey);
		productinfopage = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(productinfopage.getProductImagesCount(), imagesCount);
	}

	@Test
	public void productInfoTest() {
		searchResultsPage = accPage.doSearch("macbook");
		productinfopage = searchResultsPage.selectProduct("MacBook Pro");
		Map<String, String> productActDetailsMap = productinfopage.getProductDetailsMap();
		softAssert.assertEquals(productActDetailsMap.get("Brand"), "Apple");
		softAssert.assertEquals(productActDetailsMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(productActDetailsMap.get("Availability"), "In Stock");
		softAssert.assertEquals(productActDetailsMap.get("productPrice"), "$2,000.00");
		softAssert.assertEquals(productActDetailsMap.get("extaxprice"), "$2,000.00");
		softAssert.assertAll();

	}

	@Test
	public void addToCartTest() {
		searchResultsPage = accPage.doSearch("macbook");
		productinfopage = searchResultsPage.selectProduct("MacBook Pro");
		productinfopage.addQuantityAndClick("1");
		Assert.assertTrue(productinfopage.getSuccessMsg().contains("Success"));
	}

}
