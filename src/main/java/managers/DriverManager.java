package managers;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.Parameters;

import com.beust.jcommander.Parameter;

import utils.BaseUtils;

public class DriverManager {

	private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();


	public static void initDriver(String browser) throws IOException {

	//String browser = BaseUtils.getConfigValue("browser");
		switch (browser) {
		case "chrome":
			driver.set(new ChromeDriver());
		 ExtentTestManager.getLog().info("Chrome browser has launched");
			break;
		case "safari":
			driver.set(new SafariDriver());
		 ExtentTestManager.getLog().info("Firefox browser has launched");
			break;

		}
		getDriver().manage().window().maximize();
	 ExtentTestManager.getLog().info("Browser has been maximized");
	}
public static void setDriver(WebDriver driverInstance)
{
	driver.set(driverInstance);
}

	public static WebDriver getDriver() {

		return driver.get();
	}

	public static void quitDriver() {
		if(getDriver()!=null)
		{
			getDriver().quit();
			driver.remove();
		}
		 ExtentTestManager.getLog().info("All Browsers has been closed ");
	}

	public static void goToUrl(String url) {
		getDriver().get(url);
	 ExtentTestManager.getLog().info(url + " has opened");
	}
}
