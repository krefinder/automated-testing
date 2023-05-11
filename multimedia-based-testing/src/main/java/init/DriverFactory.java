package init;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import constants.confg.Config;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class DriverFactory {
	public final static int TIMEOUT_SECONDS = 4000;
	public DesiredCapabilities capability;
	private static ThreadLocal<AppiumDriver<MobileElement>> mobileDriver = new ThreadLocal<AppiumDriver<MobileElement>>();

	public DriverFactory() throws IOException {

		switch (Config.PLATFORM_NAME.toLowerCase()) {

		case "android":
			capability = new DesiredCapabilities();
			capability.setCapability("platformName", Config.PLATFORM_NAME);
			capability.setCapability("platformVersion", Config.PLATFORM_VERSION);
			capability.setCapability("deviceName", Config.DEVICE_NAME);
			capability.setCapability("udid", Config.DEVICE_UDID);
			capability.setCapability("appWaitPackage", Config.WAIT_PACKAGE);
			capability.setCapability("appWaitActivity", Config.WAIT_ACTIVITY);
			if (!Config.APP.contains("null"))
				capability.setCapability("app", Config.APP);
			else {
				capability.setCapability("appPackage", Config.PACKAGE);
				capability.setCapability("appActivity", Config.ACTIVITY);
			}
			capability.setCapability("resetApp", true);
			capability.setCapability("autoGrantPermissions", true);
			capability.setCapability("autoAcceptAlerts", true);
			capability.setCapability("gpsEnabled", true);
			capability.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 8000);
			capability.setCapability("newCommandTimeout", TIMEOUT_SECONDS);
			try {
				mobileDriver.set(new AndroidDriver<MobileElement>(
						new URL("http://127.0.0.1:" + Config.PORT_NUMBER + "/wd/hub"), capability));
			} catch (Exception e) {
				capability = new DesiredCapabilities();
				capability.setCapability("platformName", Config.PLATFORM_NAME);
				capability.setCapability("platformVersion", Config.PLATFORM_VERSION);
				capability.setCapability("deviceName", Config.DEVICE_NAME);
				capability.setCapability("udid", Config.DEVICE_UDID);
				capability.setCapability("appPackage", Config.PACKAGE);
				capability.setCapability("appActivity", Config.ACTIVITY);
				capability.setCapability("resetApp", true);
				capability.setCapability("autoGrantPermissions", true);
				capability.setCapability("autoAcceptAlerts", true);
				capability.setCapability("gpsEnabled", true);
				capability.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 8000);
				capability.setCapability("newCommandTimeout", TIMEOUT_SECONDS);
				mobileDriver.set(new AndroidDriver<MobileElement>(
						new URL("http://127.0.0.1:" + Config.PORT_NUMBER + "/wd/hub"), capability));
			}
			getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			break;

		case "ios":
			capability = new DesiredCapabilities();
			capability.setCapability("platformName", Config.PLATFORM_NAME);
			capability.setCapability("platformVersion", Config.PLATFORM_VERSION);
			capability.setCapability("deviceName", Config.DEVICE_NAME);
			capability.setCapability("udid", Config.DEVICE_UDID);
			capability.setCapability("app", Config.APP);
			capability.setCapability("newCommandTimeout", TIMEOUT_SECONDS);
			mobileDriver.set(new IOSDriver<MobileElement>(new URL("http://127.0.0.1:" + Config.PORT_NUMBER + "/wd/hub"),
					capability));
			getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		default:
			throw new RuntimeException("Please provide correct platform...");
		}
	}

	public static synchronized AppiumDriver<MobileElement> getDriver() {
		return mobileDriver.get();
	}

}
