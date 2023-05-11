package screenTest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import basePages.AppPage;
import init.DriverFactoryBrowser;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import listner.TestListener;

public class WebTests {

    SoftAssert softAssert;
    public DriverFactoryBrowser driverFactory;
    public AppiumDriver<MobileElement> driver;
    AppPage app = new AppPage();


    @BeforeTest(alwaysRun = true)
    public void startUp() throws Exception {
        this.driverFactory = new DriverFactoryBrowser();
        this.driver = DriverFactoryBrowser.getDriver();
        driver.get("https://en.ryte.com/wiki/LinkedIn");
        TestListener.logToReport("Redirected to https://en.ryte.com/wiki/LinkedIn");
    }


    @Test
    public void verifyPictures() throws Exception {
    	app.doVisualWebCheck(driver,"img1");
    	app.doVisualWebCheck(driver,"img2");
    	app.doVisualWebCheck(driver,"img3");
    	
    	app.compareWebResult(driver, "img1","img1");
    	app.compareWebResult(driver, "img2","img2");
    	app.compareWebResult(driver, "img3","img3");
    	
    	app.compareWebResult(driver, "img1","img2");
    	app.compareWebResult(driver, "img2","img3");
    	app.compareWebResult(driver, "img3","img1");
    	
    }


    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
//		driver.closeApp();
    }
}
