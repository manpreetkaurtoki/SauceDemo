package utils;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import managers.DriverManager;

public class WaitUtils {
	private static final ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();

	public static void initWait() throws NumberFormatException, IOException {
		wait.set(new WebDriverWait(DriverManager.getDriver(),
				Duration.ofSeconds(Integer.valueOf(BaseUtils.getConfigValue("wait")))));
	}

	public static WebDriverWait getWait() {
		return wait.get();

	}

	public static WebElement elementToBeClickable(By locator) {
		return getWait().until(ExpectedConditions.elementToBeClickable(locator));
	}

	public static WebElement visibilityOfElementLocated(By locator) {
		return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static List<WebElement> visibilityOfAllElementsLocatedBy(By locator) {
		return getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	public static Actions actions() {
		return new Actions(DriverManager.getDriver());
	}

	public static WebElement find(By locator) {
		return DriverManager.getDriver().findElement(locator);
	}

	public static void removeWait() {
		wait.remove();
	}

	public static String clickAndSwitchToNewTab(By locator, String url) {
		
		
		// Save parent window
	    String parentWindow = DriverManager.getDriver().getWindowHandle();

	    // Selenium 4: create new tab and switch to it
	    DriverManager.getDriver().switchTo().newWindow(WindowType.TAB);

	    // Open URL
	    DriverManager.getDriver().get(url);

	    // Capture title
	    String title = DriverManager.getDriver().getTitle();

	    System.out.println("New Tab Title: " + title);

	    // Close new tab
	    DriverManager.getDriver().close();

	    // Switch back to parent
	    DriverManager.getDriver().switchTo().window(parentWindow);

	    return title;
		
		

		
	}
}
