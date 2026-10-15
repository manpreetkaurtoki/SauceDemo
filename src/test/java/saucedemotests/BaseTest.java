package saucedemotests;

import java.io.IOException;
import java.lang.reflect.Method;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.beust.jcommander.Parameter;

import managers.DriverManager;
import managers.ExtentManager;
import managers.ExtentTestManager;
import utils.BaseUtils;
import utils.WaitUtils;

public class BaseTest {
	WebDriverWait wait;

	@BeforeSuite
	public void initialise() throws IOException {
		ExtentManager.initReport();

	}

	
	@BeforeMethod
	@Parameters("browser")
	public void preReq(Method method,@Optional("chrome") String browser) throws IOException {
		ExtentTestManager.createTest(method.getName());
		DriverManager.initDriver(browser);
		DriverManager.getDriver();
		WaitUtils.initWait();
		DriverManager.goToUrl(BaseUtils.getConfigValue("url"));
		System.out.println(
				"using thread" + Thread.currentThread().getId() + " Driver =" + DriverManager.getDriver().hashCode());
	}

	@AfterMethod
	public void endTest(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.SUCCESS) {
			ExtentTestManager.getLog().pass("Test Passed");
		} else if (result.getStatus() == ITestResult.FAILURE) {
			ExtentTestManager.getLog().fail(result.getThrowable());
			ExtentTestManager.getLog().fail(result.getThrowable(),
					MediaEntityBuilder
							.createScreenCaptureFromPath(BaseUtils.getScreenShotPath(DriverManager.getDriver(),
									result.getInstance().getClass().getSimpleName() + "." + result.getMethod()))
							.build());
		} else if (result.getStatus() == ITestResult.SKIP) {
			ExtentTestManager.getLog().skip("Test Skipped");
		}
		//DriverManager.quitDriver();
		System.out.println("entered in closing browser method");

	}

	@AfterSuite
	public void tearDown() {
		ExtentManager.flushReport();
	}

}
