package audioTest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import basePages.AppPage;
import constants.confg.Config;
import init.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class SpeechTests{
	
	SoftAssert softAssert;
	public DriverFactory driverFactory;
	public AppiumDriver<MobileElement> driver;
	AppPage app = new AppPage();

	int randomNumber = app.generateRandomNumber();

	@BeforeTest(alwaysRun = true)
	public void startUp() throws Exception {
		this.driverFactory = new DriverFactory();
		this.driver = DriverFactory.getDriver();
	}

	@Test
	public void speechRecognition() throws Exception {
		app.playMusic(driver, Config.SEARCH);
		AudioRecognizer.main(null);
	}
	
	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.closeApp();
	}

}