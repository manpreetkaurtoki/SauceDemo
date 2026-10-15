package managers;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {
	public static final ThreadLocal<ExtentTest> log = new ThreadLocal<ExtentTest>();

	public static void createTest(String methodName) {
		log.set(ExtentManager.extent.createTest(methodName));
	}

	public static ExtentTest getLog()
	{
		return log.get();
	}
	public static void removetest()
	{
		if(log.get()!=null)
		{
			log.remove();
		}
	}
}
