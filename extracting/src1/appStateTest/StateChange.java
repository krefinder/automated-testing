/*
 *  FileName :: StateChange.java
 *  Input :: App name to be tested
 *  Author :: Anusha Konduru
 *  
 *  This program requires an app name as input string and searches for the app in the APKLists file.
 *  Once the app is found, it will fetch the capabilities required to start Appium with the app.
 *  It installs the application on the device and starts navigating from one activity to other and 
 *  searches for the text fields, checkbox and search boxes and fills them with test data and tests
 *  onPause state and onResume to check if the data is persistent after UI State transition.
 *  Assertion fails if one of the type of errors are present in the app.
 *  
 */
package appStateTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;


public class StateChange extends SetupAndroidTest {
	private static Queue<String> parsedPagesQueue = new LinkedList<String>();
	private static Hashtable<String, MobileElement> checkBoxes = new Hashtable<String, MobileElement>();
	private static Hashtable<String, MobileElement> searchBars = new Hashtable<String, MobileElement>();
	private static Hashtable<String, MobileElement> textFields = new Hashtable<String, MobileElement>();
	private static String homePage="";
	public StateChange() {
		homePage = activityName;
	}
	public static List<MobileElement> getClickableElementsOfCurrentPage() {
		List<MobileElement> elementsList = driver.findElementsByAndroidUIAutomator("new UiSelector().clickable(true)");
		return elementsList;
	}
	public String getPageSource() {
		return driver.getPageSource();
	}
	private void createDriver() throws MalformedURLException, InterruptedException {
		driver = new AndroidDriver<MobileElement>(new URL("http://localhost:4723/wd/hub"),cap);
		driver.manage().timeouts().implicitlyWait(250, TimeUnit.SECONDS);
		Thread.sleep(20000);
	}
	public boolean isPageParsed(String activity) {
		//check from the list of activities if the current activity is already parsed.
		if (parsedPagesQueue.contains(activity))
			return true;
		return false;
	}

	public void testOnPause() {
		driver.pressKey(new KeyEvent(AndroidKey.HOME));
		driver.launchApp();
		
	}

	public void getCheckboxes(String activity) {
		// parse the activity window and store the checkboxes activity pages in the map or queue
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		if (activity != null) {
			try {
				MobileElement checkboxSelection =driver.findElementByClassName("android.widget.CheckBox");
				if (checkboxSelection != null ) {
					checkboxSelection.click();
					Thread.sleep(4000);
					testOnPause();
					String curActivity = driver.currentActivity();
					if(!curActivity.equals(activity)) {
						try {
							assertEquals("Type 4 error:: UI state mismatch", true, false);
							} catch (Exception e)    {
							}
							assertEquals("Type 2:: Checkbox status mismatch", true, false);
					}
					boolean out = checkboxSelection.isSelected();
					assertEquals("Type 2 :: Checkbox status mismatch",true,out);
				}
			}
			catch (Exception e) {
				driver.manage().timeouts().implicitlyWait(250, TimeUnit.SECONDS);
			}
		}
	}
	public void getSearchBar(String activity) throws InterruptedException {
		if (activity != null && !searchBars.containsKey(activity)) {
			//parse the activity window and store the searchbar activity pages in the map or queue
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			try {
				String testString = " search";
				MobileElement element = driver.findElementByAndroidUIAutomator(
						"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
								+ testString + "\").instance(0))");
				element.click();
				Thread.sleep(1000);
				searchBars.put(activity,element);
				String sample = "cc";
				element.sendKeys(sample);
				Thread.sleep(10000);
				testOnPause();

				String out = element.getText();
				try {
					assertEquals("Type 3:: Search results not persistent",sample, out);
				}
				catch(Exception e) {
				}
			} catch (Exception e) {
				driver.manage().timeouts().implicitlyWait(250, TimeUnit.SECONDS);
			} 
		}
	}
	public void getSignup(String activity) {
		//parse the activity window and store the signup window activity pages in the map or queue
		if (activity != null && !textFields.containsKey(activity)) {
			//parse the activity window and store the searchbar activity pages in the map or queue
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			try {
				String testString = "Dataloss";
				List<MobileElement> tfl=driver.findElements(By.className("android.widget.EditText"));
				tfl.get(0).sendKeys(testString);
				Thread.sleep(10000);
				textFields.put(activity,tfl.get(0));
				testOnPause();
				String curActivity = driver.currentActivity();
				if(!curActivity.equals(activity)) {
					try {
						assertEquals("Type 4 error:: UI state mismatch", true, false);
						} catch (Exception e)    {
						}
						assertEquals("Type 1:: Text data not persistent", true, false);
				}
				List<MobileElement> tfl2=driver.findElements(By.className("android.widget.EditText"));
				String out = tfl2.get(0).getText();
				try {
					assertEquals("Type 1:: Text data not persistent",testString, out);
				}
				catch(Exception e) {
					System.out.println("");
				}
			} catch (Exception e) {
				driver.manage().timeouts().implicitlyWait(250, TimeUnit.SECONDS);
			} 
		}
	}	
	public void parseCheckboxes(String activity) {
		getCheckboxes(activity);
	}
	public void parseSearchbars(String activity) throws InterruptedException {
		getSearchBar(activity);
	}
	public void parseTexts(String activity) {
		getSignup(activity);
	}

	@Test public void testDataLoss() throws MalformedURLException, InterruptedException {
		createDriver();
		//homePage = driver.currentActivity();
		//activityName= driver.currentActivity();
		parsePages(homePage);
	}
	public void parsePages(String mainActivityName) throws InterruptedException {
		String currentActivity=mainActivityName;
		if ( currentActivity != null && !parsedPagesQueue.contains(currentActivity)) {
			parsedPagesQueue.add(currentActivity);

			List<MobileElement> elementsList = getClickableElementsOfCurrentPage();
			parseCheckboxes(currentActivity);
			parseSearchbars(currentActivity);
			parseTexts(currentActivity);
			for (int i=0;i<elementsList.size();i++) {
				elementsList.get(i).click();
				String newActivity = driver.currentActivity();
				if (!newActivity.equals(currentActivity)) {
					parsePages(newActivity);
				}
				else {
					return;
				}
			}
		}

	}
	
 }
