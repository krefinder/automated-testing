package listner;

import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.Parameters;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import constants.confg.Config;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;


public class ExtentManager {

	private static ExtentReports extent;
	private static Platform platform;
	static String fileName = System.getProperty("user.dir") + "/Extent Reports/extentreport.html";
	
	public static ExtentReports getInstance(ITestContext context) {
		if (extent == null)
			createInstance(context);
		return extent;
	}
	
	// Create an extent report instance
	public static ExtentReports createInstance(ITestContext context) {
		return getExtent();
	}
	public static ExtentReports getExtent() {
		if (extent == null) {
			extent = new ExtentReports();
			extent.setSystemInfo("Name", "Vimalan");
			extent.setSystemInfo("Browser", Config.PLATFORM_NAME);
			extent.attachReporter(getHtmlReporter());
		}
		return extent;
	}
	private static ExtentHtmlReporter getHtmlReporter() {
		ExtentHtmlReporter	htmlReporter = new ExtentHtmlReporter(fileName);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setDocumentTitle("OpenCv Automation Report");
		htmlReporter.config().setReportName("Test Automation Report");
		return htmlReporter;
	}

	// Get current platform
	public static Platform getCurrentPlatform() {
		if (platform == null) {
			String operSys = System.getProperty("os.name").toLowerCase();
			if (operSys.contains("win")) {
				platform = Platform.WINDOWS;
			} else if (operSys.contains("nix") || operSys.contains("nux")
					|| operSys.contains("aix")) {
				platform = Platform.LINUX;
			} else if (operSys.contains("mac")) {
				platform = Platform.MAC;
			}
		}
		return platform;
	}
//	public void onStart(ISuite suite) {
//		// TODO Auto-generated method stub
//		this.extent = getExtent();
//		suite.setAttribute("extent", this.extent);
//	}
	
}
