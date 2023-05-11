package audioTest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.musicg.fingerprint.FingerprintManager;
import com.musicg.fingerprint.FingerprintSimilarity;
import com.musicg.fingerprint.FingerprintSimilarityComputer;
import com.musicg.wave.Wave;
import basePages.AppPage;
import constants.confg.Config;
import init.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import listner.TestListener;

public class AudioTests2 {

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
	public void testAudioCapture() throws Exception {
		app.playMusic(driver, Config.SEARCH);
		
		JavaSoundRecorder.main(null);

		byte[] firstAudio = new FingerprintManager()
				.extractFingerprint(new Wave(System.getProperty("user.dir") + "\\RecordAudio.wav"));
		byte[] secondAudio = new FingerprintManager()
				.extractFingerprint(new Wave(System.getProperty("user.dir") + "\\SampleAudio.wav"));

		FingerprintSimilarityComputer fingerprint = new FingerprintSimilarityComputer(firstAudio, firstAudio);

		FingerprintSimilarity fingerprintSimilarity = fingerprint.getFingerprintsSimilarity();

		System.out.println("***** Similarity check of same audio *****");
		TestListener.logToReport("Similarity score (Number of features found in the fingerprints per frame) = "
				+ fingerprintSimilarity.getScore());
		System.out.println("Similarity score (Number of features found in the fingerprints per frame) = "
				+ fingerprintSimilarity.getScore());
		TestListener.logToReport(
				"Similarity (similarity from 0~1, which 0 means no similar feature is found and 1 means in average there is at least one match in every frame) = "
						+ fingerprintSimilarity.getSimilarity());
		System.out.println(
				"Similarity (similarity from 0~1, which 0 means no similar feature is found and 1 means in average there is at least one match in every frame) = "
						+ fingerprintSimilarity.getSimilarity());

		FingerprintSimilarityComputer fingerprint2 = new FingerprintSimilarityComputer(firstAudio, secondAudio);
		FingerprintSimilarity fingerprintSimilarity2 = fingerprint2.getFingerprintsSimilarity();

		System.out.println("\n***** Similarity check of two different audio *****");
		TestListener.logToReport("Similarity score (Number of features found in the fingerprints per frame) = "
				+ fingerprintSimilarity2.getScore());
		System.out.println("Similarity score (Number of features found in the fingerprints per frame) = "
				+ fingerprintSimilarity2.getScore());
		TestListener.logToReport(
				"Similarity (similarity from 0~1, which 0 means no similar feature is found and 1 means in average there is at least one match in every frame) = "
						+ fingerprintSimilarity2.getSimilarity());
		System.out.println(
				"Similarity (similarity from 0~1, which 0 means no similar feature is found and 1 means in average there is at least one match in every frame) = "
						+ fingerprintSimilarity2.getSimilarity());

	}

	public static byte[] readAudioFileData(final String filePath) {
		byte[] data = null;
		try {
			final ByteArrayOutputStream baout = new ByteArrayOutputStream();
			final File file = new File(filePath);
			final AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);

			byte[] buffer = new byte[4096];
			int c;
			while ((c = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
				baout.write(buffer, 0, c);
			}
			audioInputStream.close();
			baout.close();
			data = baout.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.closeApp();
	}
}
