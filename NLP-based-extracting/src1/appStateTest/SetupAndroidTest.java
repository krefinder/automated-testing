package appStateTest;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.net.MalformedURLException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
//Read csv imports

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SetupAndroidTest{
    public static AndroidDriver<MobileElement> driver;
	public static List<String> parsedActivities=new ArrayList<String>();
	static String activityName="";
	static String apkFile="";
	static String packageName="";
	static String appName="";
	static DesiredCapabilities cap;
	public static void readCSV() {
		String fileToParse = "C:\\\\Users\\\\hoang\\\\Desktop\\\\Project\\\\APKLists.csv";
        BufferedReader fileReader = null;
         
        //Delimiter used in CSV file
        final String DELIMITER = ",";
        try
        {
            String line = "";
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(fileToParse));
            //Read the file line by line
            while ((line = fileReader.readLine()) != null) 
            {
                //Get all tokens available in line
                String[] tokens = line.split(DELIMITER);
                if (tokens[0].equals(appName)) {
                		appName=tokens[0];
                		apkFile=tokens[1];
                		packageName=tokens[2];
                		activityName=tokens[3];
                		break;
                }
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally
        {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
    /**
     * initialization.
     * @throws InterruptedException 
     * @throws IOException 
     */
    @BeforeClass public static void setup() throws InterruptedException, IOException {
    	getAppName();
    	readCSV();
        File appDir = new File("C:\\\\Users\\\\hoang\\\\Desktop\\\\Project\\\\apks\\\\");
        File app = new File(appDir, apkFile);
        cap = new DesiredCapabilities();
		//cap.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "5200f8b38c0e16cf");
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME,MobilePlatform.ANDROID);
		cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        cap.setCapability("appPackage", packageName);
        cap.setCapability("appActivity", activityName);
		cap.setCapability("automationName", "uiautomator2");
		cap.setCapability("autoGrantPermissions",true);
		cap.setCapability("uiautomator2ServerInstallTimeout", 22000);
		cap.setCapability("autoDismissAlerts", true);
		cap.setCapability("adbExecTimeout", 20000);
		cap.setCapability("androidInstallTimeout", 15000);
		cap.setCapability("uiautomator2ServerLaunchTimeout", 22000);
		cap.setCapability("dontStopAppOnReset", true);
		cap.setCapability("noReset", true);
		
    }

    private static void getAppName() throws IOException {
		// TODO Auto-generated method stub
    	BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
    	System.out.println("Enter the App name you want to test:");
    	appName = reader.readLine();
	}
	/**
     * finishing.
     */
    @AfterClass public static void teardown() {
        if (driver != null) {
        	System.out.println("Entered teardown");
            driver.quit();
        }
    }
}

