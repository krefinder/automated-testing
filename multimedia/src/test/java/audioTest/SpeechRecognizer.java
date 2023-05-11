package audioTest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;

import basePages.AppPage;
import constants.confg.Config;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import init.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class SpeechRecognizer {

	// EnglishNumberToWords
	EnglishNumberToString numberToString = new EnglishNumberToString();
	EnglishStringToNumber stringToNumber = new EnglishStringToNumber();
	public DriverFactory driverFactory;
	public AppiumDriver<MobileElement> driver;
	AppPage app = new AppPage();

	// Logger
	private Logger logger = Logger.getLogger(getClass().getName());

	// Variables
	private String result;

	// Threads
	Thread speechThread;
	Thread resourcesThread;

	// LiveRecognizer
	private LiveSpeechRecognizer recognizer;

	/**
	 * Constructor
	 * 
	 * @throws Exception
	 */
	public SpeechRecognizer() throws Exception {

		// Loading Message
		logger.log(Level.INFO, "Loading..\n");

		// Configuration
		Configuration configuration = new Configuration();

		// Load model from the jar
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");

		// if you want to use LanguageModelPath disable the 3 lines after which
		// are setting a custom grammar->

		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

		// Grammar
		configuration.setGrammarPath("resource:/grammars");
		configuration.setGrammarName("grammar");
		configuration.setUseGrammar(true);

		openApp();

		try {
			recognizer = new LiveSpeechRecognizer(configuration);
		} catch (IOException ex) {
			logger.log(Level.SEVERE, null, ex);
		}

		// Start recognition process pruning previously cached data.
		recognizer.startRecognition(true);

		// Start the Thread
		startSpeechThread();
		startResourcesThread();
	}

	public void openApp() {
		try {
			this.driverFactory = new DriverFactory();
			this.driver = DriverFactory.getDriver();
			app.openMusicApp(driver, Config.SEARCH);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Starting the main Thread of speech recognition
	 */
	protected void startSpeechThread() {

		// alive?
		if (speechThread != null && speechThread.isAlive())
			return;

		// initialise
		speechThread = new Thread(() -> {
			logger.log(Level.INFO, "You can start to speak...\n");
			System.out.println("Say 'PLAY SONG' to start playing song \n");
			System.out.println("Say 'PAUSE SONG' to stop playing song \n");
			System.out.println("Say 'EXIT APP' to terminate application \n");
			try {
				while (true) {
					/*
					 * This method will return when the end of speech is reached. Note that the end
					 * pointer will determine the end of speech.
					 */
					SpeechResult speechResult = recognizer.getResult();
					if (speechResult != null) {

						result = speechResult.getHypothesis();
						System.out.println("You said: [" + result + "]\n");
						makeDecision(result);
						// logger.log(Level.INFO, "You said: " + result + "\n")

					} else
						logger.log(Level.INFO, "I can't understand what you said.\n");

				}
			} catch (Exception ex) {
				logger.log(Level.WARNING, null, ex);
			}

			logger.log(Level.INFO, "SpeechThread has exited...");
		});

		// Start
		speechThread.start();

	}

	/**
	 * Starting a Thread that checks if the resources needed to the
	 * SpeechRecognition library are available
	 */
	protected void startResourcesThread() {

		// alive?
		if (resourcesThread != null && resourcesThread.isAlive())
			return;

		resourcesThread = new Thread(() -> {
			try {

				// Detect if the microphone is available
				while (true) {
					if (AudioSystem.isLineSupported(Port.Info.MICROPHONE)) {
						// logger.log(Level.INFO, "Microphone is available.\n");
					} else {
						// logger.log(Level.INFO, "Microphone is not available.\n");

					}

					// Sleep some period
					Thread.sleep(350);
				}

			} catch (InterruptedException ex) {
				logger.log(Level.WARNING, null, ex);
				resourcesThread.interrupt();
			}
		});

		// Start
		resourcesThread.start();
	}

	/**
	 * Takes a decision based on the given result
	 * 
	 * @throws Exception
	 */
	public void makeDecision(String speech) throws Exception {

		if (speech.contains("play song")) {
			try {
				app.playMusicUsingSpeech(driver);
				System.out.println("Playing Song....\n");
				System.out.println("Say 'PAUSE SONG' to stop playing song \n");
				System.out.println("Say 'EXIT APP' to terminate application \n");
			} catch (Exception e) {
				System.err.println("Song is already playing");
			}
			return;
		} else if (speech.contains("pause song")) {
			try {
				app.pauseMusicUsingSpeech(driver);
				System.out.println("Song Paused....\n");
				System.out.println("Say 'PLAY SONG' to start playing song \n");
				System.out.println("Say 'EXIT APPLICATION' to terminate application \n");
			} catch (Exception e) {
				System.err.println("Song is already paused");
			}
			return;
		} else if (speech.contains("exit application")) {
			try {
				driver.closeApp();
				System.out.println("Exited....\n");
				System.out.println("Say 'LAUNCH APPLICATION' to LAUNCH application \n");
			} catch (Exception e) {
				System.err.println("App is already closed");
			}
			return;
		} else if (speech.contains("launch application")) {
			try {
				openApp();
				System.out.println("App Launched....\n");
				System.out.println("Say 'PLAY SONG' to start playing song \n");
				System.out.println("Say 'PAUSE SONG' to stop playing song \n");
				System.out.println("Say 'EXIT APP' to terminate application \n");
			} catch (Exception e) {
				System.err.println("App is already launched");
			}
			return;
		}
	}

	/**
	 * Java Main Application Method
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		// Be sure that the user can't start this application by not giving
		// the
		// correct entry string
		// if (args.length == 1 && "SPEECH".equalsIgnoreCase(args[0]))
		new SpeechRecognizer();
		// else
		// Logger.getLogger(AudioRecognizer.class.getName()).log(Level.WARNING, "Give me
		// the correct entry string..");

	}

}