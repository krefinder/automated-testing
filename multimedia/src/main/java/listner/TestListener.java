package listner;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.MediaType;
import com.aventstack.extentreports.model.ScreenCapture;
import init.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import utils.FileOperations;

public class TestListener extends ExtentManager implements ITestListener {
	// Extent Report Declarations
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static Map<String, AppiumDriver<MobileElement>> driverMap = new HashMap<String, AppiumDriver<MobileElement>>();
	private String extentScreen = null;
	private static Map<String, ExtentTest> extentTestMap = new HashMap<String, ExtentTest>();
	private static ThreadLocal<Integer> testMethodCount = new ThreadLocal<Integer>();
	private static ScreenCapture med;
	private static MediaEntityModelProvider mp;

	@Override
	public synchronized void onStart(ITestContext context) {
		System.out.println("Extent Reports Test Suite started!");
		extent = createInstance(context);
	}

	@Override
	public synchronized void onFinish(ITestContext context) {
		System.out.println(("Extent Reports Test Suite is ending!"));
		extent.flush();
	}

	@Override
	public synchronized void onTestStart(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " started!"));
		ExtentTest extentTest = startTest(result.getTestClass().getName().replace(".", " ")
				.split(" ")[result.getTestClass().getName().replace(".", " ").split(" ").length - 1].trim());
		FileOperations.cleanDir(System.getProperty("user.dir") + "/Extent Reports/Passed Screenshots/");
		FileOperations.cleanDir(System.getProperty("user.dir") + "/Extent Reports/Failure Screenshots/");
		FileOperations.cleanDir(System.getProperty("user.dir") + "/Extent Reports/Skip Screenshots/");
		FileOperations.cleanDir(System.getProperty("user.dir") + "/screenshots/app/");
		String testClassName = result.getTestClass().getRealClass().getSimpleName();
		driverMap.put(testClassName, DriverFactory.getDriver());
		test.set(extentTest);
		testMethodCount.set(-1);
		getTest(result.getInstanceName().replace(".", " ")
				.split(" ")[result.getInstanceName().replace(".", " ").split(" ").length - 1].trim())
						.createNode(result.getMethod().getMethodName(), result.getMethod().getDescription())
						.log(Status.INFO, "Test method " + result.getMethod().getMethodName() + " started");

		Integer testMethodCountValue = testMethodCount.get();
		testMethodCountValue = testMethodCountValue + 1;
		testMethodCount.set(testMethodCountValue);
	}

	@Override
	public synchronized void onTestSuccess(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " passed!"));
		test.get().pass("Test passed");
		String methodName = result.getName().trim();
		String filePath = System.getProperty("user.dir") + "/Extent Reports/Passed Screenshots/";
		takeScreenShot(filePath, methodName, driverMap.get(result.getTestClass().getRealClass().getSimpleName()));
		Log logObj = new Log(getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ")
				.split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length - 1]
						.trim()));
		logObj.setDetails("Test method " + result.getMethod().getMethodName() + " completed successfully");
		logObj.setStatus(Status.PASS);

		getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ")
				.split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length - 1]
						.trim()).getModel().getNodeContext().get(testMethodCount.get()).getLogContext().add(logObj);

		med = new ScreenCapture();
		med.setMediaType(MediaType.IMG);
		med.setPath(extentScreen);
		getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ")
				.split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length - 1]
						.trim()).getModel().getNodeContext().get(testMethodCount.get()).setScreenCapture(med);
	}

	@Override
	public synchronized void onTestFailure(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " failed!"));
		// below line is just to append the date format with the screenshot name to
		// avoid duplicate names
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		String destination = System.getProperty("user.dir") + "/Extent Reports/Failure Screenshots/"
				+ result.getMethod().getMethodName() + dateName + ".png";

		// Returns the captured file path

		test.get().fail(result.getThrowable());
		try {
			test.get().addScreenCaptureFromPath(destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String methodName = result.getName().trim();
		String filePath = System.getProperty("user.dir") + "/Extent Reports/Failure Screenshots/";
		takeScreenShot(filePath, methodName, driverMap.get(result.getTestClass().getRealClass().getSimpleName()));
		Log logObj = new Log(getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ")
				.split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length - 1]
						.trim()));
		logObj.setDetails("Test method " + result.getMethod().getMethodName() + " failed due to Exception -----> "
				+ Reporter.getCurrentTestResult().getThrowable().toString());
		logObj.setStatus(Status.FAIL);

		getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ")
				.split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length - 1]
						.trim()).getModel().getNodeContext().get(testMethodCount.get()).getLogContext().add(logObj);

		med = new ScreenCapture();
		med.setMediaType(MediaType.IMG);
		med.setPath(extentScreen);
		getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ")
				.split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length - 1]
						.trim()).getModel().getNodeContext().get(testMethodCount.get()).setScreenCapture(med);
	}

	@Override
	public synchronized void onTestSkipped(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " skipped!"));
		String methodName = result.getName().trim();
		String filePath = System.getProperty("user.dir") + "/Extent Reports/Skip Screenshots/";
		test.get().skip(result.getThrowable());
		takeScreenShot(filePath, methodName, driverMap.get(result.getTestClass().getRealClass().getSimpleName()));
		Log logObj = new Log(getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ")
				.split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length - 1]
						.trim()));
		logObj.setDetails("Test method " + result.getMethod().getMethodName() + " skip due to Exception -----> "
				+ Reporter.getCurrentTestResult().getThrowable().toString());
		logObj.setStatus(Status.SKIP);

		med = new ScreenCapture();
		med.setMediaType(MediaType.IMG);
		med.setPath(extentScreen);
		mp = new MediaEntityModelProvider(med);
		getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ")
				.split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length - 1]
						.trim()).createNode(result.getMethod().getMethodName(), result.getMethod().getDescription())
								.skip(MarkupHelper.createLabel("SKIP", ExtentColor.BLUE))
								.skip("Screenshot " + result.getMethod().getMethodName(), mp).log(Status.SKIP,
										"Test method " + result.getMethod().getMethodName()
												+ " skip due to Exception -----> "
												+ Reporter.getCurrentTestResult().getThrowable().toString());

		Integer testMethodCountValue = testMethodCount.get();
		testMethodCountValue = testMethodCountValue + 1;
		testMethodCount.set(testMethodCountValue);

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
	}

	public void takeScreenShot(String filePath, String methodName, AppiumDriver<MobileElement> driver) {
		try {
			TakesScreenshot d = ((TakesScreenshot) driver);
			File scrFile = d.getScreenshotAs(OutputType.FILE);
			File scrFile2 = new File(filePath + methodName + ".png");
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			FileUtils.copyFile(scrFile, scrFile2);
			System.out.println("***Placed screen shot in " + filePath + "***");
			extentScreen = scrFile2.toString();
			Reporter.setEscapeHtml(false);
			Reporter.log("<a href='" + scrFile2.toString() + "'><img src ='" + scrFile2.toString()
					+ "' width='200' height='200'></a>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized ExtentTest startTest(String testName) {
		ExtentTest classNode = getExtent().createTest(testName);
		extentTestMap.put(testName, classNode);
		return classNode;
	}

	public static synchronized ExtentTest getTest(String testName) {
		return extentTestMap.get(testName);
	}

	public static void logToReport(String msg) {
		Log logObj = new Log(getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ")
				.split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length - 1]
						.trim()));
		logObj.setDetails(msg);
		logObj.setStatus(Status.INFO);
		getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ")
				.split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length - 1]
						.trim()).getModel().getNodeContext().get(testMethodCount.get()).getLogContext().add(logObj);
	}
}