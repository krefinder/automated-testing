package basePages;

import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import constants.confg.Config;
import constants.path.Path;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.imagecomparison.SimilarityMatchingOptions;
import io.appium.java_client.imagecomparison.SimilarityMatchingResult;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import listner.ReportListenerWeb;
import listner.TestListener;

public class AppPage {

	By continueBtn = By.xpath("//button[contains(text(),'Continue')]");
	By amazonBtn = By.cssSelector("img[src='/img/store-amazon.png']");
	By playBtn = By.xpath("//div[@class='play-icon']");
	By music = By.xpath("//*[@text='Music']");
	By playMusic = By.xpath("//android.view.ViewGroup[@content-desc]");
	By ytPlayMusic = By.xpath("//android.widget.ImageView[@content-desc=\"Play video\"]");
	By ytVideoPlayer = By.xpath("//android.widget.FrameLayout[@content-desc=\"Video player\"]");
	By ytPauseMusic = By.xpath("//android.widget.ImageView[@content-desc=\"Pause video\"]");
	By search = By.xpath("//android.widget.ImageView[@content-desc='Search']");
	By searchField = By.xpath("//android.widget.EditText");
	By spotifyLoginBtn = By.xpath("//*[contains(@text,'Log in')]");
	By spotifyUsername = By.xpath("//*[contains(@text,'username')]/..//android.widget.EditText");
	By spotifySearchIcon = By.xpath("//*[contains(@text,'Search')]");
	By spotifySearch = By.xpath("//android.widget.TextView[@content-desc=\"Search for something to listen to\"]");
	By spotifyPlay = By.xpath("//*[@resource-id='com.spotify.music:id/title']");
	By spotifyPlayMusic = By.xpath("//android.widget.ImageButton[@content-desc=\"Play\"]");
	By spotifyPauseMusic = By.xpath("//android.widget.ImageButton[@content-desc=\"Pause\"]");
	By spotifySongs = By.xpath("//*[@text='Songs']");
	By wynkTry = By.xpath("//*[@text='Try it first']");
	By wynkSearch = By.xpath("//android.widget.ImageView[@content-desc=\"SEARCH\"]");
	By wynkSearchBar = By.xpath("//*[@resource-id='com.bsbportal.music:id/searchBar']");
	By wynkPlayMusic = By.xpath("//*[@resource-id='com.bsbportal.music:id/rv_searchv2_home']//*[@resource-id='com.bsbportal.music:id/title']");
	By wynkPlay = By.xpath("//*[@resource-id='com.bsbportal.music:id/btnPlay']");
	By scAccount = By.xpath("//*[contains(@text,'I already have an account')]");
	By scContinueBtn = By.xpath("//*[contains(@text,'Continue')]");
	By scUsername = By.xpath("//android.widget.EditText");
	By shazamSearch = By.xpath("//android.widget.ImageView[@content-desc=\"Search\"]");
	By shazamPlayMusic = By.xpath("//*[@resource-id='com.shazam.android:id/view_search_result_track_title']");
	By shazamPlay = By.xpath("//android.widget.ImageView[@content-desc]");
	By jioNext = By.xpath("//*[@text='Next']");
	By jioSkip = By.xpath("//*[contains(@text,'Skip')]");
	By jioHaryanvi = By.xpath("//*[@text='Haryanvi']");
	By jioDone = By.xpath("//*[@text='Done']");
	By jioSearchIcon = By.xpath("//*[@text='Search']");
	By allow = By.xpath("//*[@text='Allow']");
	By jioSearch = By.xpath("//*[@text='Music, Artists, and Podcasts']");
	By jioSong = By.xpath("//*[@text='Songs']/..//*[@content-desc='thumbnail']");

	private final static String VALIDATION_PATH = Path.PROJECT_PATH;
	private final static double MATCH_THRESHOLD = 0.99;

	private int i = 0;

	public int getValue() {
		return i;
	}

	// Setter
	public void setValue(int j) {
		this.i = j;
	}

	public void loginToSpotify(AppiumDriver<MobileElement> driver) {
		click(driver, spotifyLoginBtn);
		List<MobileElement> edit = driver.findElements(spotifyUsername);
		ArrayList<MobileElement> fields = new ArrayList<MobileElement>();
		for (int i = 0; i < edit.size(); i++) {
			fields.add(edit.get(i));
		}
		fields.get(0).sendKeys(Config.USERNAME);
		fields.get(1).sendKeys(Config.PASSWORD);
		click(driver, spotifyLoginBtn);
	}

	public void loginToSoundCloud(AppiumDriver<MobileElement> driver) {
		List<MobileElement> edit = driver.findElements(scUsername);
		ArrayList<MobileElement> fields = new ArrayList<MobileElement>();
		for (int i = 0; i < edit.size(); i++) {
			fields.add(edit.get(i));
		}
		fields.get(0).sendKeys(Config.USERNAME);
		fields.get(1).sendKeys(Config.PASSWORD);
		click(driver, scContinueBtn);
	}

	public void clickOnContinue(AppiumDriver<MobileElement> driver) {
		click(driver, continueBtn);
	}

	public void waitForElementClickability(AppiumDriver<MobileElement> driver, By locator) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void waitForElementVisibility(AppiumDriver<MobileElement> driver, By locator) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void waitUntilElementPresent(AppiumDriver<MobileElement> driver, By locator) {
		hardWait(5000);
		while (isElementPresent(driver, locator) == true) {
			hardWait(5000);
		}
	}

	public void verticalScrollByLocationUntilElementVisibility(AppiumDriver<MobileElement> driver, double x,
			double startOfY, double endOfY, By locator) {
		@SuppressWarnings("rawtypes")
		TouchAction action = new TouchAction(driver);
		Dimension size = driver.manage().window().getSize();
		int width = size.width;
		int height = size.height;
		int X = (int) (width / x);
		int startYCoordinate = (int) (height * startOfY);
		int endYCoordinate = (int) (height * endOfY);
		while (isElementPresent(driver, locator) == false) {
			action.press(PointOption.point(X, startYCoordinate))
					.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
					.moveTo(PointOption.point(X, endYCoordinate)).release().perform();
		}
		// verticalScrollByLocation(driver, 2, 0.6, 0.3);
	}

	public void verticalScrollByLocationUntilElementVisibility(AppiumDriver<MobileElement> driver, double x,
			double startOfY, double endOfY, MobileElement element) {
		@SuppressWarnings("rawtypes")
		TouchAction action = new TouchAction(driver);
		Dimension size = driver.manage().window().getSize();
		int width = size.width;
		int height = size.height;
		int X = (int) (width / x);
		int startYCoordinate = (int) (height * startOfY);
		int endYCoordinate = (int) (height * endOfY);
		while (isElementPresent(driver, element) == false) {
			action.press(PointOption.point(X, startYCoordinate))
					.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
					.moveTo(PointOption.point(X, endYCoordinate)).release().perform();
		}
	}

	public void verticalScrollByLocationUntilElementNotVisibile(AppiumDriver<MobileElement> driver, double x,
			double startOfY, double endOfY, By locator) {
		@SuppressWarnings("rawtypes")
		TouchAction action = new TouchAction(driver);
		Dimension size = driver.manage().window().getSize();
		int width = size.width;
		int height = size.height;
		int X = (int) (width / x);
		int startYCoordinate = (int) (height * startOfY);
		int endYCoordinate = (int) (height * endOfY);
		int i = 0;
		while (isElementPresent(driver, locator) == true && i < 5) {
			action.press(PointOption.point(X, startYCoordinate))
					.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
					.moveTo(PointOption.point(X, endYCoordinate)).release().perform();
			i = i + 1;
		}
	}

	public void verticalScrollByLocation(AppiumDriver<MobileElement> driver, double x, double startOfY, double endOfY) {
		@SuppressWarnings("rawtypes")
		TouchAction action = new TouchAction(driver);
		Dimension size = driver.manage().window().getSize();
		int width = size.width;
		int height = size.height;
		int X = (int) (width / x);
		int startYCoordinate = (int) (height * startOfY);
		int endYCoordinate = (int) (height * endOfY);
		action.press(PointOption.point(X, startYCoordinate)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
				.moveTo(PointOption.point(X, endYCoordinate)).release().perform();
	}

	public boolean isElementPresent(AppiumDriver<MobileElement> driver, By locators) {
		boolean status = false;
		try {
			driver.findElement(locators).isDisplayed();
			status = true;
		} catch (Exception e) {
			status = false;
		}
		return status;
	}

	public boolean isElementPresent(AppiumDriver<MobileElement> driver, MobileElement element) {
		boolean status = false;
		try {
			element.isDisplayed();
			status = true;
		} catch (Exception e) {
			status = false;
		}
		return status;
	}

	public void hardWait(int milliSeconds) {
		try {
			Thread.sleep(milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int generateRandomNumber() {
		Random random = new Random();
		return random.nextInt(10000);
	}

	public void hideKeyboard(AppiumDriver<MobileElement> driver) {
		hardWait(2000);
		driver.hideKeyboard();
	}

	public void enterData(AppiumDriver<MobileElement> driver, By locator, String value) {
		waitForElementVisibility(driver, locator);
		hardWait(1000);
		driver.findElement(locator).click();
		hardWait(1000);
		driver.findElement(locator).clear();
		hardWait(2000);
		driver.findElement(locator).sendKeys(value);
	}

	public void enterDataPressEnter(AppiumDriver<MobileElement> driver, By locator, String value) {
		waitForElementVisibility(driver, locator);
		driver.findElement(locator).sendKeys(value);
		new Actions(driver).sendKeys(Keys.ENTER).build().perform();
		driver.hideKeyboard();
	}

	public void click(AppiumDriver<MobileElement> driver, By locator) {
		try {
			waitForElementVisibility(driver, locator);
			waitForElementClickability(driver, locator);
			driver.findElement(locator).click();
		} catch (TimeoutException e) {
			hardWait(2000);
			driver.findElement(locator).click();
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			hardWait(2000);
			driver.findElement(locator).click();
			e.printStackTrace();
		} catch (StaleElementReferenceException e) {
			hardWait(2000);
			driver.findElement(locator).click();
			e.printStackTrace();
		}
	}

	public void swipeHorizontalAndClick(AppiumDriver<MobileElement>driver,String locatorType, String viewIdentificator, String attribute, String text)
	{
		hardWait(5000);
	    WebElement element = driver.findElement(MobileBy
	            .AndroidUIAutomator("new UiScrollable(new UiSelector()."+locatorType+"(\""+viewIdentificator+"\")).setAsHorizontalList()." +
	                    "scrollIntoView("
	                    + "new UiSelector()."+attribute+"(\""+text+"\"));"));
	    hardWait(5000);
	    element.click();
	}
	// public void doFeaturesCheck(AppiumDriver<MobileElement> driver, String
	// checkName) throws Exception {
	// FeaturesMatchingResult result = driver.matchImagesFeatures(screenshot,
	// originalImg,
	// new
	// FeaturesMatchingOptions().withDetectorName(FeatureDetector.ORB).withGoodMatchesFactor(40)
	// .withMatchFunc(MatchingFunction.BRUTE_FORCE_HAMMING).withEnabledVisualization());
	// Assert.assertTrue(result.getVisualization().length>0);
	// assertThat(result.getCount(), is(greaterThan(0)));
	// assertThat(result.getTotalCount(), is(greaterThan(0)));
	// assertFalse(result.getPoints1().isEmpty());
	// assertNotNull(result.getRect1());
	// assertFalse(result.getPoints2().isEmpty());
	// assertNotNull(result.getRect2());
	// }

	// public SimilarityMatchingResult doVisualCheck(AppiumDriver<MobileElement>
	// driver, String checkName)
	// throws Exception {
	// String baselineFilename = VALIDATION_PATH + "/screenshots/expected/" +
	// BASELINE + checkName + ".png";
	// File baselineImg = new File(baselineFilename);
	//
	// // If no baseline image exists for this check, we should create a baseline
	// image
	// if (!baselineImg.exists()) {
	// System.out.println(String.format("No baseline found for '%s' check; capturing
	// baseline instead of checking",
	// checkName));
	// File newBaseline = driver.getScreenshotAs(OutputType.FILE);
	// FileUtils.copyFile(newBaseline, new File(baselineFilename));
	// }
	//
	// // Otherwise, if we found a baseline, get the image similarity from Appium.
	// In
	// // getting the similarity,
	// // we also turn on visualization so we can see what went wrong if something
	// did.
	// SimilarityMatchingOptions opts = new SimilarityMatchingOptions();
	// opts.withEnabledVisualization();
	// File Img = driver.getScreenshotAs(OutputType.FILE);
	// FileUtils.copyFile(Img, new File(VALIDATION_PATH + "/screenshots/actual/" +
	// checkName + ".png"));
	// SimilarityMatchingResult res = driver.getImagesSimilarity(baselineImg, Img,
	// opts);
	// return res;
	// }
	//
	// public void compareResult(AppiumDriver<MobileElement> driver, String
	// checkName) throws Exception {
	// // If the similarity is not high enough, consider the check to have failed
	// if (doVisualCheck(driver, checkName).getScore() < MATCH_THRESHOLD) {
	// File failViz = new File(VALIDATION_PATH + "/FAIL_" + checkName + ".png");
	// doVisualCheck(driver, checkName).storeVisualization(failViz);
	// throw new Exception(String.format(
	// "Visual check of '%s' failed; similarity match was only %f, and below the
	// threshold of %f. Visualization written to %s.",
	// checkName, doVisualCheck(driver, checkName).getScore(), MATCH_THRESHOLD,
	// failViz.getAbsolutePath()));
	// }
	//
	// // Otherwise, it passed!
	// System.out.println(String.format("Visual check of '%s' passed; similarity
	// match was %f", checkName,
	// doVisualCheck(driver, checkName).getScore()));
	//
	// }

	public void switchToNativeContext(AppiumDriver<MobileElement> driver) {

		driver.getContextHandles();

		if (driver.getContext().equals("NATIVE_APP")) {

			// System.out.println("Was Already On Native");

		} else if (driver.getContext().equals("CHROMIUM")) {

			driver.context("NATIVE_APP");

			// System.out.println("Switched to Native");

		}

	}

	public void switchToWebContext(AppiumDriver<MobileElement> driver) {

		Set<String> a = driver.getContextHandles();

		System.out.println(a);

		if (driver.getContext().equals("NATIVE_APP")) {

			driver.context("CHROMIUM");

			System.out.println("Switched to WebView");

		} else if (driver.getContext().equals("CHROMIUM")) {

			System.out.println("Was Already On WebView");

		}
	}

	public MobileElement getImage(AppiumDriver<MobileElement> driver, String imgLoc) throws Exception {
		By img;
		if (imgLoc.equals("img1")) {
			img = By.xpath(
					"//*[contains(@text,'Stay ahead with curated content for your career')]/..//android.widget.ImageView");
		} else if (imgLoc.equals("img2")) {
			img = By.xpath("//*[contains(@text,'Build your network on the go')]/..//android.widget.ImageView");
		} else {
			img = By.xpath("//*[contains(@text,'Find and land your next job')]/..//android.widget.ImageView");
		}
		waitForElementVisibility(driver, img);

		return driver.findElement(img);
	}

	public int getImagesCount(AppiumDriver<MobileElement> driver) throws Exception {
		// try {
		// click(driver, By.xpath("//*[contains(@text,'Skip')]"));
		// } catch (Exception e) {
		// //
		// }
		By img = By.xpath("//android.widget.Image | //android.widget.ImageView");
		waitForElementVisibility(driver, img);
		List<MobileElement> imgs = driver.findElements(img);
		return imgs.size();
	}

	public MobileElement getAllImage(AppiumDriver<MobileElement> driver, int imgCount) throws Exception {
		By img = By.xpath("//android.widget.Image | //android.widget.ImageView");
		waitForElementVisibility(driver, img);
		MobileElement currentImg = null;
		List<MobileElement> imgs = driver.findElements(img);
		for (int i = 0; i < imgs.size(); i++) {
			if (imgCount == i) {
				currentImg = imgs.get(i);
			}
		}
		return currentImg;
	}

	public MobileElement getWebImage(AppiumDriver<MobileElement> driver) throws Exception {
		switchToNativeContext(driver);
		MobileElement imgs = driver.findElement(By.xpath("//android.widget.Image"));
		return imgs;
	}

	public MobileElement getWebImageList(AppiumDriver<MobileElement> driver, String imgLoc) throws Exception {
		switchToNativeContext(driver);
		List<MobileElement> imgs = driver.findElements(By.xpath("//android.widget.Image"));
		if (imgs.size() == 1) {
			throw new Exception();
		}
		int i;
		for (i = 0; i < imgs.size(); i++) {
			if (imgLoc.equals("img" + (i))) {
				break;
			}
		}
		return imgs.get(i);
	}

	public void scrollToWebImageList(AppiumDriver<MobileElement> driver, String imgLoc) throws Exception {
		switchToNativeContext(driver);
		List<MobileElement> imgs = driver.findElements(By.xpath("//android.widget.Image"));
		if (imgs.size() == 1) {
			throw new Exception();
		}
		int i;
		for (i = 0; i < imgs.size(); i++) {
			if (imgLoc.equals("img" + (i))) {
				if (i == 2) {
					verticalScrollByLocation(driver, 2, 0.6, 0.3);
				}
				break;
			}
		}
	}

	public void scrollToWebImage(AppiumDriver<MobileElement> driver) throws Exception {
		switchToNativeContext(driver);
		verticalScrollByLocationUntilElementVisibility(driver, 2, 0.6, 0.3, By.xpath("//android.widget.Image"));
	}

	public void scrollToNonWebImage(AppiumDriver<MobileElement> driver) throws Exception {
		switchToNativeContext(driver);
		verticalScrollByLocationUntilElementNotVisibile(driver, 2, 0.6, 0.4, By.xpath("//android.widget.Image"));
	}

	public void doVisualCheckAllImages(AppiumDriver<MobileElement> driver, int imgCount) throws Exception {
		try {
		String baselineFilename = VALIDATION_PATH + "/screenshots/app/img" + imgCount + ".png";
		
		File newBaseline = driver.getScreenshotAs(OutputType.FILE);
		BufferedImage fullImg = ImageIO.read(newBaseline);

		// Get the location of element on the page
		Point point = getAllImage(driver, imgCount).getLocation();

		// Get width and height of the element
		int eleWidth = getAllImage(driver, imgCount).getSize().getWidth();
		int eleHeight = getAllImage(driver, imgCount).getSize().getHeight();

		// Crop the entire page screenshot to get only element screenshot
		BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
		ImageIO.write(eleScreenshot, "png", newBaseline);

		FileUtils.copyFile(newBaseline, new File(baselineFilename));
		}catch(Exception e){
			//
		}

	}

	public File doVisualCheck(AppiumDriver<MobileElement> driver, String checkName) throws Exception {
		String baselineFilename = VALIDATION_PATH + "/screenshots/app/" + checkName + ".png";
		File baselineImg = new File(baselineFilename);

		File newBaseline = driver.getScreenshotAs(OutputType.FILE);
		BufferedImage fullImg = ImageIO.read(newBaseline);

		// Get the location of element on the page
		Point point = getImage(driver, checkName).getLocation();

		// Get width and height of the element
		int eleWidth = getImage(driver, checkName).getSize().getWidth();
		int eleHeight = getImage(driver, checkName).getSize().getHeight();

		// Crop the entire page screenshot to get only element screenshot
		BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
		ImageIO.write(eleScreenshot, "png", newBaseline);

		FileUtils.copyFile(newBaseline, new File(baselineFilename));

		return baselineImg;
	}

	public File doVisualWebCheck(AppiumDriver<MobileElement> driver, String checkName) throws Exception {
		String baselineFilename = VALIDATION_PATH + "/screenshots/web/" + checkName + ".png";
		File baselineImg = new File(baselineFilename);
		try {
			scrollToWebImageList(driver, "img" + getValue());
		} catch (Exception e) {
			scrollToWebImage(driver);
		}
		File newBaseline = driver.getScreenshotAs(OutputType.FILE);
		BufferedImage fullImg = ImageIO.read(newBaseline);
		MobileElement img;
		Boolean flag = true;
		try {
			img = getWebImageList(driver, "img" + getValue());
			int j = getValue() + 1;
			setValue(j);
		} catch (Exception e) {
			flag = false;
			img = getWebImage(driver);
		}
		// Get the location of element on the page
		Point point = img.getLocation();
		// Get width and height of the element
		int eleWidth = img.getSize().getWidth();
		int eleHeight = img.getSize().getHeight();

		// Crop the entire page screenshot to get only element screenshot
		BufferedImage eleScreenshot = null;
		eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
		ImageIO.write(eleScreenshot, "png", newBaseline);

		FileUtils.copyFile(newBaseline, new File(baselineFilename));
		System.out.println(checkName + " captured");
		if (flag == false)
			scrollToNonWebImage(driver);
		return baselineImg;
	}

	public void compareResult(AppiumDriver<MobileElement> driver, String checkName, String checkName2)
			throws Exception {
		SimilarityMatchingOptions opts = new SimilarityMatchingOptions();
		opts.withEnabledVisualization();
		File Img2 = new File(VALIDATION_PATH + "/screenshots/app/" + checkName2 + ".png");
		File Img1 = new File(VALIDATION_PATH + "/screenshots/app/" + checkName + ".png");
		try {
			SimilarityMatchingResult res = driver.getImagesSimilarity(Img1, Img2, opts);
			// If the similarity is not high enough, consider the check to have failed
			if (res.getScore() < MATCH_THRESHOLD) {
				File failViz = new File(VALIDATION_PATH + "/FAIL_APP_" + checkName + ".png");
				res.storeVisualization(failViz);
				System.err.println(String.format(
						"Visual check of '%s' with '%s' failed; similarity match was only %f, and below the threshold of %f. Visualization written to %s.",
						checkName, checkName2, res.getScore(), MATCH_THRESHOLD, failViz.getAbsolutePath()));
				TestListener.logToReport(String.format(
						"Visual check of '%s' with '%s' failed; similarity match was only %f, and below the threshold of %f. Visualization written to %s.",
						checkName, checkName2, res.getScore(), MATCH_THRESHOLD, failViz.getAbsolutePath()));
			} else
				// Otherwise, it passed!
				System.out.println(String.format("Visual check of '%s' with '%s' passed; similarity match was %f",
						checkName, checkName2, res.getScore()));
			TestListener.logToReport(String.format("Visual check of '%s' with '%s' passed; similarity match was %f",
					checkName, checkName2, res.getScore()));

		} catch (

		Exception e) {
			System.err.println("Visual check of " + checkName + " with " + checkName2
					+ " failed; Both images are expected to have the same size in order to calculate the similarity score.");
			TestListener.logToReport("Visual check of " + checkName + " with " + checkName2
					+ " failed; Both images are expected to have the same size in order to calculate the similarity score.");

		}
	}

	public void compareWebResult(AppiumDriver<MobileElement> driver, String checkName, String checkName2)
			throws Exception {
		SimilarityMatchingOptions opts = new SimilarityMatchingOptions();
		opts.withEnabledVisualization();
		File Img2 = new File(VALIDATION_PATH + "/screenshots/web/" + checkName2 + ".png");
		File Img1 = new File(VALIDATION_PATH + "/screenshots/web/" + checkName + ".png");
		try {
			SimilarityMatchingResult res = driver.getImagesSimilarity(Img1, Img2, opts);
			// If the similarity is not high enough, consider the check to have failed
			if (res.getScore() < MATCH_THRESHOLD) {
				File failViz = new File(VALIDATION_PATH + "/FAIL_WEB_" + checkName + ".png");
				res.storeVisualization(failViz);
				System.err.println(String.format(
						"Visual check of '%s' with '%s' failed; similarity match was only %f, and below the threshold of %f. Visualization written to %s.",
						checkName, checkName2, res.getScore(), MATCH_THRESHOLD, failViz.getAbsolutePath()));
				ReportListenerWeb.logToReport(String.format(
						"Visual check of '%s' with '%s' failed; similarity match was only %f, and below the threshold of %f. Visualization written to %s.",
						checkName, checkName2, res.getScore(), MATCH_THRESHOLD, failViz.getAbsolutePath()));
			} else
				// Otherwise, it passed!
				System.out.println(String.format("Visual check of '%s' with '%s' passed; similarity match was %f",
						checkName, checkName2, res.getScore()));
			ReportListenerWeb
					.logToReport(String.format("Visual check of '%s' with '%s' passed; similarity match was %f",
							checkName, checkName2, res.getScore()));
		} catch (Exception e) {
			System.err.println("Visual check of " + checkName + " with " + checkName2
					+ " failed; Both images are expected to have the same size in order to calculate the similarity score.");
			ReportListenerWeb.logToReport("Visual check of " + checkName + " with " + checkName2
					+ " failed; Both images are expected to have the same size in order to calculate the similarity score.");

		}

	}

	public void playMusic(AppiumDriver<MobileElement> driver, String name) throws Exception {
		if (Config.APP.contains("shazam")) {
			click(driver, shazamSearch);
			enterDataPressEnter(driver, searchField, name);
			click(driver, shazamPlayMusic);
			click(driver, shazamPlay);
		} else if (Config.APP.contains("jiosaavn") | Config.ACTIVITY.contains("jio")) {
			click(driver, jioSkip);
			click(driver, jioNext);
//			click(driver, jioHaryanvi);
			click(driver, jioNext);
			click(driver, jioSkip);
//			click(driver, jioDone);
			click(driver, jioSearchIcon);
			click(driver, jioSearch);
			enterDataPressEnter(driver, searchField, name);
			click(driver, jioSong);
		} else if (Config.APP.contains("wynk")) {
			click(driver, wynkTry);
			click(driver, wynkSearch);
			click(driver, wynkSearchBar);
			enterData(driver, searchField, name);
			click(driver, wynkPlayMusic);
			if(isElementPresent(driver, allow)) {
				click(driver, allow);
			}
		} else if (Config.APP.contains("spotify")) {
			loginToSpotify(driver);
			click(driver, spotifySearchIcon);
			click(driver, spotifySearch);
			enterData(driver, searchField, name);
			click(driver, spotifySongs);
			click(driver, spotifyPlay);
		} else {
			click(driver, search);
			enterDataPressEnter(driver, searchField, name);
			click(driver, playMusic);
		}
	}

	public void openMusicApp(AppiumDriver<MobileElement> driver, String name) throws Exception {
		if (Config.APP.contains("shazam")) {
			click(driver, shazamSearch);
			enterDataPressEnter(driver, searchField, name);
			click(driver, shazamPlayMusic);
		} else if (Config.APP.contains("jiosaavn")) {
			click(driver, jioNext);
			click(driver, jioHaryanvi);
			click(driver, jioDone);
			click(driver, jioSearchIcon);
			click(driver, jioSearch);
			enterDataPressEnter(driver, searchField, name);
		} else if (Config.APP.contains("wynk")) {
			click(driver, wynkTry);
			click(driver, wynkSearch);
			click(driver, wynkSearchBar);
			enterData(driver, searchField, name);
			click(driver, wynkPlayMusic);
			click(driver, wynkPlayMusic);
		} else if (Config.APP.contains("spotify")) {
			loginToSpotify(driver);
			click(driver, spotifySearchIcon);
			click(driver, spotifySearch);
			enterData(driver, searchField, name);
		   swipeHorizontalAndClick(driver, "className", "androidx.recyclerview.widget.RecyclerView", "text", "Songs");
			click(driver, spotifySongs);
			click(driver, spotifyPlay);
		} else {
			click(driver, search);
			enterDataPressEnter(driver, searchField, name);
			click(driver, playMusic);
		}
	}

	public void playMusicUsingSpeech(AppiumDriver<MobileElement> driver) throws Exception {
		if (Config.APP.contains("shazam")) {
			click(driver, shazamPlay);
		} else if (Config.APP.contains("jiosaavn")) {
			click(driver, jioSong);
		} else if (Config.APP.contains("wynk")) {
			click(driver, wynkPlay);
		} else if (Config.APP.contains("spotify")) {
			click(driver, spotifyPlayMusic);
		} else {
			click(driver, ytPlayMusic);
		}
	}
	
	public void pauseMusicUsingSpeech(AppiumDriver<MobileElement> driver) throws Exception {
		if (Config.APP.contains("shazam")) {
			click(driver, shazamPlay);
		} else if (Config.APP.contains("jiosaavn")) {
			click(driver, jioSong);
		} else if (Config.APP.contains("wynk")) {
			click(driver, wynkPlay);
		} else if (Config.APP.contains("spotify")) {
			click(driver, spotifyPauseMusic);
		} else {
			click(driver, ytVideoPlayer);
			driver.findElement(ytPauseMusic).click();
		}
	}

	public double[] fourierLowPassFilter(double[] data, double lowPass, double frequency) {
		// data: input data, must be spaced equally in time.
		// lowPass: The cutoff frequency at which
		// frequency: The frequency of the input data.

		// The apache Fft (Fast Fourier Transform) accepts arrays that are powers of 2.
		int minPowerOf2 = 1;
		while (minPowerOf2 < data.length)
			minPowerOf2 = 2 * minPowerOf2;

		// pad with zeros
		double[] padded = new double[minPowerOf2];
		for (int i = 0; i < data.length; i++)
			padded[i] = data[i];

		FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
		Complex[] fourierTransform = transformer.transform(padded, TransformType.FORWARD);

		// build the frequency domain array
		double[] frequencyDomain = new double[fourierTransform.length];
		for (int i = 0; i < frequencyDomain.length; i++)
			frequencyDomain[i] = frequency * i / (double) fourierTransform.length;

		// build the classifier array, 2s are kept and 0s do not pass the filter
		double[] keepPoints = new double[frequencyDomain.length];
		keepPoints[0] = 1;
		for (int i = 1; i < frequencyDomain.length; i++) {
			if (frequencyDomain[i] < lowPass)
				keepPoints[i] = 2;
			else
				keepPoints[i] = 0;
		}

		// filter the fft
		for (int i = 0; i < fourierTransform.length; i++)
			fourierTransform[i] = fourierTransform[i].multiply((double) keepPoints[i]);

		// invert back to time domain
		Complex[] reverseFourier = transformer.transform(fourierTransform, TransformType.INVERSE);

		// get the real part of the reverse
		double[] result = new double[data.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = reverseFourier[i].getReal();
		}

		return result;
	}

}
