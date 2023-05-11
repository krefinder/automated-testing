/*
 *  FileName :: Web_App_BaseTest.java
 *  Input :: Excelfile WebUrls.xlsx
 *  Author :: Divya Kolagani
 *  
 *  This program requires an app name and xpath of the element to be located  as input string and processes apps  in the file.
 *  Once the app url is copied, it will search reach the  browser of abd device or emulator and searches for the url.
 *  It will reach out to the exact element to be extracted using xpath defined in the input excel.  
 *  Once text is extracted, using advanced html reporting, displays the text for each application.
 *  It also sets the capabilities of the emulator to establish connection.
 */



package com.web.app.resources;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.android.web.config.Config;
import com.app.web.reports.ExtentReport;
import com.aventstack.extentreports.Status;
import com.web.app.utility.Test_Utility;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

public class Web_App_BaseTest {

	protected static HashMap<String, String> Strings = new HashMap<String, String>();

	InputStream Stringsis;

	protected static Properties props;

	InputStream inputstream;

	Test_Utility utils;

	public static String WebAppName;

	public Web_App_BaseTest() {

		PageFactory.initElements((Config.driver), this);
	}

	@BeforeTest
	public void beforeTest() throws Exception {

		String xmlFileName = "ExpectedResults_Strings/ExpectedResults_Strings.xml";

		Stringsis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
		utils = new Test_Utility();
		Strings = utils.parseStringXML(Stringsis);

		Load_TestData_PropertiesFile();

		WebAppName = props.getProperty("WebApp_Name");

		try {

			if (WebAppName.equalsIgnoreCase("gmail")) {

				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
				cap.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
				cap.setCapability("appPackage", "com.android.chrome");

				cap.setCapability("appActivity", "com.google.android.apps.chrome.Main");
			
				//cap.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");
				cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 250);
				System.out.println("Session Started");

				try {

					Config.driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), cap);

					Config.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

				} catch (MalformedURLException e) {
					e.printStackTrace();
				}

			} else if (WebAppName.equalsIgnoreCase("fb")) {
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
				cap.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
				cap.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");
				cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 250);
				System.out.println("Session Started");

				try {

					Config.driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), cap);

					Config.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(45));

				} catch (MalformedURLException e) {
					e.printStackTrace();
				}

				String url = props.getProperty("fb_Launch_URL");
				Config.driver.get(url);
				Config.driver.navigate().refresh();
				Thread.sleep(2000);

			} else if (WebAppName.equalsIgnoreCase("IE")) {

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			if (inputstream != null) {

				inputstream.close();

			}
			if (Stringsis != null) {

				Stringsis.close();
			}
		}

	}

	public void click(WebElement e) {

		waitForVisibilityOfElement(e);
		e.click();

	}

	public void click(WebElement e, String Msg) {

		waitForVisibilityOfElement(e);

		e.click();

		ExtentReport.getTest().log(Status.INFO, "<B>" + Msg + "</B>");

	}

	public void clear(WebElement e) {

		waitForVisibilityOfElement(e);
		e.clear();

	}

	public void clear(WebElement e, String Msg) {

		waitForVisibilityOfElement(e);
		e.clear();
		ExtentReport.getTest().log(Status.INFO, "<B>" + Msg + "</B>");

	}

	public void waitForVisibilityOfElement(WebElement e) {

		WebDriverWait wait = new WebDriverWait(Config.driver, Test_Utility.WAIT);
		wait.until(ExpectedConditions.visibilityOf(e));

	}

	public void sendKeys(WebElement e, String text) {

		waitForVisibilityOfElement(e);
		e.sendKeys(text);
		ExtentReport.getTest().log(Status.INFO, "<B>" + text + "</B>");

	}

	public boolean Boolean(WebElement e) {

		waitForVisibilityOfElement(e);
		return e.isDisplayed();

	}

	public void sendKeys(WebElement e, String text, String Msg) {

		waitForVisibilityOfElement(e);
		e.sendKeys(text);
		ExtentReport.getTest().log(Status.INFO, "<B>" + Msg + "</B>");

	}

	public String getAttribute(WebElement e, String attribute) {

		waitForVisibilityOfElement(e);

		return e.getAttribute(attribute);

	}

	public String getText(WebElement e) {

		waitForVisibilityOfElement(e);

		return e.getText();
	}

	public void Load_TestData_PropertiesFile() throws IOException {

		props = new Properties();

		inputstream = new FileInputStream(System.getProperty("user.dir") + "/Test-Data/TestData.properties");
		props.load(inputstream);
	}

	public static JSONObject Read_Data_From_Json(String FilePath) throws Exception {

		JSONObject jsonObject;

		JSONParser parser = new JSONParser();

		try (FileReader reader = new FileReader(FilePath)) {

			Object obj = parser.parse(reader);

			jsonObject = (JSONObject) obj;

			return jsonObject;

		}

	}

	public void GetURL() throws IOException, InterruptedException {

		Load_TestData_PropertiesFile();

		String url = props.getProperty("UAE_Launch_URL");
		Config.driver.get(url);
		Config.driver.navigate().refresh();
		Thread.sleep(1000);
		Config.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));

		try {

			Config.driver.findElement(By.xpath("//span[@class='closetab']")).click();

		} catch (Exception e) {

			System.out.println();

		}

	}

	@AfterTest
	public void AfterTest() {

		Config.driver.quit();

	}
}
