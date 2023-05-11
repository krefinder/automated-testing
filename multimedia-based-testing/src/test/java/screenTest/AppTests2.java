package screenTest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import basePages.AppPage;
import init.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class AppTests2 {

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
	public void verifyPictures() throws Exception {
		int count = 0;
		app.hardWait(3000);
		int imgCount=app.getImagesCount(driver);
		for (int total = 0; total < imgCount; total++) {
			app.doVisualCheckAllImages(driver, total);
		}

		for (int total = 0; total < imgCount; total++) {
			app.compareResult(driver, "img" + total, "img" + total);
			count = count + 1;
		}

		for (int total = 0; total < imgCount; total++) {
			if (total < count)
				app.compareResult(driver, "img" + total, "img" + (total + 1));
		}
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
//		driver.closeApp();
	}
}
