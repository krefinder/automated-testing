package init;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.google.common.collect.ImmutableMap;
import constants.confg.Config;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class DriverFactoryBrowser {
	public final static int TIMEOUT_SECONDS = 8000;
	public DesiredCapabilities capability;
	public static AppiumDriverLocalService service;
	public static AppiumServiceBuilder builder;
	private static ThreadLocal<AppiumDriver<MobileElement>> mobileDriver = new ThreadLocal<AppiumDriver<MobileElement>>();
	static Map<String, AppiumDriver<MobileElement>> map = new HashMap<String, AppiumDriver<MobileElement>>();

	public DriverFactoryBrowser() throws IOException {

		DesiredCapabilities capability = DesiredCapabilities.android();
		capability.setCapability("platformName", Config.PLATFORM_NAME);
		capability.setCapability("platformVersion", Config.PLATFORM_VERSION);
		capability.setCapability("deviceName", Config.DEVICE_NAME);
		capability.setCapability("udid", Config.DEVICE_UDID);
		capability.setCapability("browserName", "chrome");
		capability.setCapability("autoGrantPermissions", true);
		capability.setCapability("gpsEnabled", true);
		capability.setCapability("locationServicesAuthorized", true);
		capability.setCapability("autoAcceptAlerts", true);
		capability.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 8000);
		capability.setCapability("newCommandTimeout", TIMEOUT_SECONDS);
		capability.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
		mobileDriver.set(new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:" + Config.PORT_NUMBER + "/wd/hub"),
				capability));
		getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public static synchronized void setDriver(AppiumDriver<MobileElement> remote) {
		mobileDriver.set(remote);
	}

	public static synchronized AppiumDriver<MobileElement> getDriver() {
		return mobileDriver.get();

	}

	public static void unloadDriver() {
		getDriver().quit();
		mobileDriver.remove();
	}

}
