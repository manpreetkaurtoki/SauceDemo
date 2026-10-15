package utils;

import org.openqa.selenium.By;

import managers.DriverManager;
import managers.ExtentTestManager;


public class UIActionsUtils {
	public static String getText(By locator) {
		String uitext = WaitUtils.visibilityOfElementLocated(locator).getText();

		return uitext;
	}
	public static void enterText(By locator, String text) {

		WaitUtils.visibilityOfElementLocated(locator).sendKeys(text);
		ExtentTestManager.getLog().info("Entered Text : " + text);
	}
	public static void enterPassword(By locator, String text) {
		WaitUtils.visibilityOfElementLocated(locator).sendKeys(text);
		ExtentTestManager.getLog().info("Entered Password : " + "********");
	}

	public static void click(By locator, String text) {
		WaitUtils.elementToBeClickable(locator).click();
		//JSUtils.jsClick(WaitUtils.elementToBeClickable(locator));
		ExtentTestManager.getLog().info(text + " has been clicked");
	}

	
	public static String openSocial(By locator,String url)
	{
		return WaitUtils.clickAndSwitchToNewTab(locator, url);
	}
	
}
