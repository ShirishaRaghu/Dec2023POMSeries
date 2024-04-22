package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.ElementException;
import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

//SRP: Single Responsible principle
public class EllementUtil {

	private WebDriver driver;
	private JavaScriptUtil jsUtil;

	private final String DEFAULT_ELEMENT_TIME_OUT_MESSAGE = "Time out...Element is not found";

	private final String DEFAULT_ALERT_TIME_OUT_MESSAGE = "Time out...Alert is not found";

	private void nullBlankCheck(String value) {
		if (value == null || value.length() == 0) {
			throw new ElementException(value + "-----visible text cannot be null or blank");
		}
	}

	public EllementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}

	public By getBy(String locatorType, String locatorValve) {
		By locator = null;

		switch (locatorType.toLowerCase().trim()) {
		case "id":
			locator = By.id(locatorValve);
			break;
		case "name":
			locator = By.name(locatorValve);
			break;
		case "classname":
			locator = By.className(locatorValve);
			break;
		case "xpath":
			locator = By.xpath(locatorValve);
			break;
		case "css":
			locator = By.cssSelector(locatorValve);
			break;

		case "linkText":
			locator = By.linkText(locatorValve);
			break;
		case "partiallinkText":
			locator = By.partialLinkText(locatorValve);
			break;
		case "tagname":
			locator = By.tagName(locatorValve);
			break;

		default:
			break;
		}

		return locator;
	}

	private void checkHighlight(WebElement element) {
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
	}
	
	
	public WebElement getElement(String locatorType, String locatorValue) {
		WebElement element = driver.findElement(getBy(locatorType, locatorValue));
		checkHighlight(element);
		return element;
	}

	@Step("getting web element using locator {0}")
	public WebElement getElement(By locator) {

		WebElement element = null;
		try {
			element = driver.findElement(locator);

			checkHighlight(element);

		} catch (NoSuchElementException e) {
			System.out.println("Element is not present on the page");
			e.printStackTrace();

		}
		return element;
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
		// Here null check is not written if value is null it returns empty arraylist
	}

	@Step("entering value {1} in element : {0}")
	public void doSendkeys(By locator, String value) {
		nullBlankCheck(value);
		getElement(locator).clear();
		getElement(locator).sendKeys(value);
	}

	public void doSendkeys(By locator, String value, int timeOut) {
		nullBlankCheck(value);
		waitForElementPresence(locator, timeOut).sendKeys(value);
	}

	public void doSendkeys(String locatorType, String locatorValue, String value) {
		getElement(locatorType, locatorValue).sendKeys(value);
	}

	@Step("Clicking on {0}")
	public void doClick(By locator) {
		getElement(locator).click();
	}

	public void doClick(By locator, int timeOut) {
		waitForElementVisible(locator, timeOut);
	}

	public String doGetElementText(By locator) {
		return getElement(locator).getText();
	}

	public String doElementGetAttribute(By locator, String attributeName) {
		return getElement(locator).getAttribute(attributeName);
	}

	// ---------------isDisplayed()-------------------
	@Step("checking element {0} is displayed....")
	public boolean isElementDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public boolean isElementExist(By locator) {
		if (getElements(locator).size() == 1) {
			return true;
		}
		return false;
	}

	public boolean multipleElementExist(By locator) {
		if (getElements(locator).size() > 0) {
			return true;
		}
		return false;
	}

	public boolean multipleElementExist(By locator, int elemenetCount) {
		if (getElements(locator).size() == elemenetCount) {
			return true;
		}
		return false;
	}

	// -------------------------------------------

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	public ArrayList<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		ArrayList<String> eleTextList = new ArrayList<String>();

		for (WebElement e : eleList) {
			String text = e.getText();
			if (text.length() != 0) {
				eleTextList.add(text);
			}
		}

		return eleTextList;
	}

	public ArrayList<String> getAttribueList(By locator, String attrName) {
		List<WebElement> eleList = getElements(locator);
		ArrayList<String> eleAtrrList = new ArrayList<String>();

		for (int i = 0; i < eleList.size(); i++) {
			String attrValue = eleList.get(i).getAttribute(attrName);
			if (attrValue.length() != 0) {
				eleAtrrList.add(attrValue);
			}
		}

		return eleAtrrList;
	}

//*****************Select based Drop down Utils************

	public void doSlectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSlectByIndex(By locator, String value) {

		nullBlankCheck(value);
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(value);
	}

	public void doSlectByVisibleText(By locator, String visibleText) {
		nullBlankCheck(visibleText);
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibleText);
	}

	// **************Dropdowns by getOptions*************
	public List<String> getDropDownOptionsTextList(By locator) {
		List<WebElement> optionsList = getDropDowmOptionsList(locator);
		List<String> optionsTextList = new ArrayList<String>();

		for (WebElement e : optionsList) {
			String optionText = e.getText();
			optionsTextList.add(optionText);
		}
		return optionsTextList;

	}

	public List<WebElement> getDropDowmOptionsList(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions();
	}

	public int getDropDownValuesCount(By locator) {
		WebElement element = getElement(locator);
		Select select = new Select(element);
		return select.getOptions().size();

	}

	public void doSelectDropdownValue(By locator, String value) {
		nullBlankCheck(value);
		List<WebElement> countryOptions = getDropDowmOptionsList(locator);
		for (WebElement e : countryOptions) {
			String text = e.getText();
			System.out.println(text);

			if (text.equals(value)) {
				e.click();
				break;
			}

		}

	}

	// without any select class -- drop downs
	public void DoSelectValueFromDropDown(By locator, String value) {
		List<WebElement> optionsList = getElements(locator);

		System.out.println(optionsList.size());

		for (WebElement e : optionsList) {
			String text = e.getText();
			if (text.equals("India")) {
				e.click();
				break;
			}

		}

	}

//*********************search suggestions********************
	public void doSearch(By searchlocator, By searchSuggestions, String searchKey, String value)
			throws InterruptedException {

		driver.findElement(searchlocator).sendKeys(searchKey);
		doSendkeys(searchlocator, searchKey);
		Thread.sleep(3000);

		List<WebElement> suggList = getElements(searchSuggestions);

		System.out.println(suggList.size());

		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(value)) {
				e.click();
				break;
			}
		}

	}

	// **********Actions util***********//

	// Actions class are more powerfull because they are following w3protocols

	public void handleMenuSubMenu2(By parentMenuLocator, By SubMenuLocator) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentMenuLocator)).perform();
		Thread.sleep(1500);
		doClick(SubMenuLocator);

	}

	public void handleMenuSubMenuLevel4(By level1Menu, By level2Menu, By level3Menu, By level4Menu)
			throws InterruptedException {
		doClick(level1Menu);
		Thread.sleep(1500);
		Actions act = new Actions(driver);
		act.moveToElement(getElement(level2Menu)).perform();
		;
		Thread.sleep(1500);
		act.moveToElement(getElement(level3Menu)).perform();
		;
		Thread.sleep(1500);
		doClick(level4Menu);

	}

	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).perform();

	}

	public void doActionsSendkeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).perform();

	}

	// ***************************Wait utils***********************

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeOut
	 */
	public void clickWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element =wait.until(ExpectedConditions.elementToBeClickable(locator));
		checkHighlight(element);
		element.click();
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		checkHighlight(element);
		return element;
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut default interval time = 500 ms
	 * @return
	 */
	@Step("waiting for element {0} within time out of {1}")
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		checkHighlight(element);
		return element;
	}

	public WebElement waitForElementVisible(By locator, int timeOut, int intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(intervalTime));
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		checkHighlight(element);
		return element;
	}

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */

	public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	public List<WebElement> waitForElementsPresenceWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.withMessage(DEFAULT_ELEMENT_TIME_OUT_MESSAGE);
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locatorare visible. Visibility means that the elements are not only
	 * displayed but also have a heightand width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */

	public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	// ***********Wait for the Title***********

	public String waitForTitleContains(String titleFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
				return driver.getTitle();
			}
		} catch (Exception e) {
			System.out.println("title fraction is not found within : " + timeOut);
		}

		return driver.getTitle();

	}

	
	@Step("waiting for expected title")
	public String waitForTitleIs(String title, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleIs(title))) {
				return driver.getTitle();
			}
		} catch (Exception e) {
			System.out.println("title is not found within : " + timeOut);
		}

		return driver.getTitle();

	}

	// *********************Wait for the url***************
	@Step("waiting for the expected url....")
	public String waitForURLContains(String urlFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
				return driver.getCurrentUrl();
			}
		} catch (Exception e) {
			System.out.println("URL fraction  is not found within : " + timeOut);
		}

		return driver.getCurrentUrl();

	}

	public String waitForURLIs(String URL, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.urlToBe(URL))) {
				return driver.getCurrentUrl();
			}
		} catch (Exception e) {
			System.out.println("URL  is not found within : " + timeOut);
		}

		return driver.getCurrentUrl();

	}

	// ****************Wait Alerts**************

	public Alert waitForJSAlertWithFluentWait(int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.withMessage(DEFAULT_ALERT_TIME_OUT_MESSAGE);
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public Alert waitForJSAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String getAlertText(int timeOut) {
		return waitForJSAlert(timeOut).getText();
	}

	public void acceptAlert(int timeOut) {
		waitForJSAlert(timeOut).accept();
	}

	public void dismissAlert(int timeOut) {
		waitForJSAlert(timeOut).dismiss();
		;
	}

	public void alertSendKeys(int timeOut, String value) {
		waitForJSAlert(timeOut).sendKeys(value);
		;
	}

	public Boolean waitForWindow(int totalNumberOfWindowsToBe, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.numberOfWindowsToBe(totalNumberOfWindowsToBe));
	}

	// ******************Wait Frames***************

	public void waitForFrameAndSwitchToIt(By frameLocator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	public void waitForFrameAndSwitchToIt(int frameIndex, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	public void waitForFrameAndSwitchToIt(WebElement frameElement, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

	// *************Custom wait***************

	public WebElement retryingElement(By locator, int timeOut) {
		WebElement element = null;
		int attempts = 0;

		while (attempts < timeOut) {
			try {
				element = driver.findElement(locator);
				System.out.println("element is found..." + locator + " in attempts " + attempts);
				break;
			} catch (NoSuchElementException e) {
				System.out.println("element is not found..." + "in attempts " + attempts);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

			}
			attempts++;
		}

		if (element == null) {
			System.out.println("element is not found..... tried for " + timeOut + " times " + " with the interval of "
					+ 500 + " milliseconds.... ");
			throw new ElementException("No such Element");
		}

		checkHighlight(element);
		return element;

	}

	public WebElement retryingElement(By locator, int timeOut, int intervelTime) {
		WebElement element = null;
		int attempts = 0;

		while (attempts < timeOut) {
			try {
				element = driver.findElement(locator);
				System.out.println("element is found..." + locator + " in attempts " + attempts);
				break;
			} catch (NoSuchElementException e) {
				System.out.println("element is not found..." + "in attempts " + attempts);
				try {
					Thread.sleep(intervelTime * 2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

			}
			attempts++;
		}

		if (element == null) {
			System.out.println("element is not found..... tried for " + timeOut + " times " + " with the interval of "
					+ intervelTime + " milliseconds.... ");
			throw new ElementException("No such Element");
		}
		
		checkHighlight(element);
		return element;

	}

}
