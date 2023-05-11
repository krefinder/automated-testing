package screenTest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import basePages.AppPage;
import init.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class AppTests {

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
    	
    	app.doVisualCheck(driver,"img1");
    	app.doVisualCheck(driver,"img2");
    	app.doVisualCheck(driver,"img3");
    	
    	app.compareResult(driver, "img1","img1");
    	app.compareResult(driver, "img2","img2");
    	app.compareResult(driver, "img3","img3");
    	
    	app.compareResult(driver, "img1","img2");
    	app.compareResult(driver, "img2","img3");
    	app.compareResult(driver, "img3","img1");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
//		driver.closeApp();
    }
}
