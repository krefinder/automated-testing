package audioTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ProcessBuilder.Redirect;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import basePages.AppPage;
import constants.path.Path;
import init.DriverFactoryBrowser;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import java.util.StringJoiner;

public class AudioTests {

	SoftAssert softAssert;
	public DriverFactoryBrowser driverFactory;
	public AppiumDriver<MobileElement> driver;
	AppPage app = new AppPage();

	@BeforeTest(alwaysRun = true)
	public void startUp() throws Exception {
		this.driverFactory = new DriverFactoryBrowser();
		this.driver = DriverFactoryBrowser.getDriver();
		driver.get("http://splendourhyaline.com/");
	}

	@Test
	public void testAudioCapture() throws Exception {
//		app.playMusic(driver);

		// start an ffmpeg audio capture of system audio. Replace with a path and device
		// id
		// appropriate for your system (list devices with `ffmpeg -f avfoundation
		// -list_devices true -i ""`
		File audioCapture = new File(Path.PROJECT_PATH + "/capture.wav");
		captureForDuration(audioCapture, 10000);

		// finally, we assert that the comparison is sufficiently strong
//		Assert.assertEquals(comparison, Matchers.greaterThanOrEqualTo(70.0));

		assert (audioCapture.exists());
	}

	protected void captureForDuration(File audioCapture, int durationMs) throws Exception {
		FFmpeg capture = new FFmpeg(audioCapture, 0);
		Thread t = new Thread(capture);
		t.start();

		// wait for sufficient amount of song to play
		Thread.sleep(durationMs);

		// tell ffmpeg to stop sampling
		capture.stopCollection();

		// wait for ffmpeg thread to end on its own
		t.join();
	}

	class FFmpeg implements Runnable {
		private Process proc;
		private File captureFile;
		private int deviceId;

		FFmpeg(File captureFile, int deviceId) {
			this.proc = null;
			this.captureFile = captureFile;
			this.deviceId = deviceId;
		}

		public void run() {
//	        ArrayList<String> cmd = new ArrayList<>();
//	        cmd.add("ffmpeg");       // binary should be on path
//	        cmd.add("-y");           // always overwrite files
//	        cmd.add("-f");           // format
//	        cmd.add("avfoundation"); // apple's system audio---something else for windows
//	        cmd.add("-i");           // input
//	        cmd.add(":" + deviceId); // device id returned by ffmpeg list
//	        cmd.add(captureFile.getAbsolutePath());
			ProcessBuilder pb = new ProcessBuilder("bash", "-c", "aplay " + captureFile.getAbsolutePath());
			System.out.println(captureFile.getAbsolutePath());
//	        ProcessBuilder pb = new ProcessBuilder(cmd);
			pb.redirectErrorStream(true);
			pb.redirectOutput(Redirect.PIPE);
			StringJoiner out = new StringJoiner("\n");
			try {
				proc = pb.start();
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {

					reader.lines().forEach(out::add);
				}
				proc.waitFor();
			} catch (Exception ign) {
			}
			System.out.println("FFMpeg output was: " + out.toString());
		}

		public void stopCollection() throws IOException {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream()));
			writer.write("q");
			writer.flush();
			writer.close();
		}
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
//		driver.closeApp();
	}
}
