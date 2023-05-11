package audioTest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import com.google.api.gax.longrunning.OperationFuture;
import com.google.api.gax.longrunning.OperationTimedPollAlgorithm;
import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.retrying.TimedRetryAlgorithm;
import com.google.api.gax.rpc.ApiStreamObserver;
import com.google.api.gax.rpc.BidiStreamingCallable;
import com.google.api.gax.rpc.ClientStream;
import com.google.api.gax.rpc.ResponseObserver;
import com.google.api.gax.rpc.StreamController;
import com.google.cloud.speech.v1.LongRunningRecognizeMetadata;
import com.google.cloud.speech.v1.LongRunningRecognizeResponse;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.cloud.speech.v1.SpeechSettings;
import com.google.cloud.speech.v1.StreamingRecognitionConfig;
import com.google.cloud.speech.v1.StreamingRecognitionResult;
import com.google.cloud.speech.v1.StreamingRecognizeRequest;
import com.google.cloud.speech.v1.StreamingRecognizeResponse;
import com.google.cloud.speech.v1.WordInfo;
import com.google.common.util.concurrent.SettableFuture;
import com.google.protobuf.ByteString;

import constants.confg.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.TargetDataLine;
import org.threeten.bp.Duration;

public class AudioRecognizer {

//	// EnglishNumberToWords
//	EnglishNumberToString	numberToString	= new EnglishNumberToString();
//	EnglishStringToNumber	stringToNumber	= new EnglishStringToNumber();
//
//	// Logger
//	private Logger logger = Logger.getLogger(getClass().getName());
//
//	// Variables
//	private String result;
//
//	// Threads
//	Thread	speechThread;
//	Thread	resourcesThread;
//
//	// LiveRecognizer
//	private LiveSpeechRecognizer recognizer;
//
//	/**
//	 * Constructor
//	 */
//	public AudioRecognizer() {
//
//		// Loading Message
//		logger.log(Level.INFO, "Loading..\n");
//
//		// Configuration
//		Configuration configuration = new Configuration();
//
//		// Load model from the jar
//		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
//		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
//
//		// if you want to use LanguageModelPath disable the 3 lines after which
//		// are setting a custom grammar->
//
//		// configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin")
//
//		// Grammar
//		configuration.setGrammarPath("resource:/grammars");
//		configuration.setGrammarName("grammar");
//		configuration.setUseGrammar(true);
//
//		try {
//			recognizer = new LiveSpeechRecognizer(configuration);
//		} catch (IOException ex) {
//			logger.log(Level.SEVERE, null, ex);
//		}
//
//		// Start recognition process pruning previously cached data.
//		recognizer.startRecognition(true);
//
//		// Start the Thread
//		startSpeechThread();
//		startResourcesThread();
//	}
//
//	/**
//	 * Starting the main Thread of speech recognition
//	 */
//	protected void startSpeechThread() {
//
//		// alive?
//		if (speechThread != null && speechThread.isAlive())
//			return;
//
//		// initialise
//		speechThread = new Thread(() -> {
//			logger.log(Level.INFO, "You can start to speak...\n");
//			try {
//				while (true) {
//					/*
//					 * This method will return when the end of speech is
//					 * reached. Note that the end pointer will determine the end
//					 * of speech.
//					 */
//					SpeechResult speechResult = recognizer.getResult();
//					if (speechResult != null) {
//
//						result = speechResult.getHypothesis();
//						System.out.println("You said: [" + result + "]\n");
//						makeDecision(result);
//						// logger.log(Level.INFO, "You said: " + result + "\n")
//
//					} else
//						logger.log(Level.INFO, "I can't understand what you said.\n");
//
//				}
//			} catch (Exception ex) {
//				logger.log(Level.WARNING, null, ex);
//			}
//
//			logger.log(Level.INFO, "SpeechThread has exited...");
//		});
//
//		// Start
//		speechThread.start();
//
//	}
//
//	/**
//	 * Starting a Thread that checks if the resources needed to the
//	 * SpeechRecognition library are available
//	 */
//	protected void startResourcesThread() {
//
//		// alive?
//		if (resourcesThread != null && resourcesThread.isAlive())
//			return;
//
//		resourcesThread = new Thread(() -> {
//			try {
//
//				// Detect if the microphone is available
//				while (true) {
//					if (AudioSystem.isLineSupported(Port.Info.MICROPHONE)) {
//						// logger.log(Level.INFO, "Microphone is available.\n")
//					} else {
//						// logger.log(Level.INFO, "Microphone is not
//						// available.\n")
//
//					}
//
//					// Sleep some period
//					Thread.sleep(350);
//				}
//
//			} catch (InterruptedException ex) {
//				logger.log(Level.WARNING, null, ex);
//				resourcesThread.interrupt();
//			}
//		});
//
//		// Start
//		resourcesThread.start();
//	}
//
//	/**
//	 * Takes a decision based on the given result
//	 */
//	public void makeDecision(String speech) {
//
//		// Split the sentence
//		String[] array = speech.split(" ");
//
//		// return if user said only one number
//		if (array.length != 3)
//			return;
//
//		// Find the two numbers
//		int number1 = stringToNumber.convert(array[0]);
//		int number2 = stringToNumber.convert(array[2]);
//
//		// Calculation result in int representation
//		int calculationResult = 0;
//		String symbol = "?";
//
//		// Find the mathematical symbol
//		if ("plus".equals(array[1])) {
//			calculationResult = number1 + number2;
//			symbol = "+";
//		} else if ("minus".equals(array[1])) {
//			calculationResult = number1 - number2;
//			symbol = "-";
//		} else if ("multiply".equals(array[1])) {
//			calculationResult = number1 * number2;
//			symbol = "*";
//		} else if ("division".equals(array[1])) {
//			calculationResult = number1 / number2;
//			symbol = "/";
//		}
//
//		String res = numberToString.convert(Math.abs(calculationResult));
//
//		// With words
//		System.out.println("Said:[ " + speech + " ]\n\t\t which after calculation is:[ "
//				+ (calculationResult >= 0 ? "" : "minus ") + res + " ] \n");
//
//		// With numbers and math
//		System.out.println("Said:[ " + number1 + " " + symbol + " " + number2 + "]\n\t\t which after calculation is:[ "
//				+ calculationResult + " ]");
//
//	}
//
//	/**
//	 * Java Main Application Method
//	 * 
//	 * @param args
//	 */
//	public static void main(String[] args) {
//
//		 // Be sure that the user can't start this application by not giving
////		 the
//		 // correct entry string
////		 if (args.length == 1 && "SPEECH".equalsIgnoreCase(args[0]))
//		new AudioRecognizer();
////		 else
////		 Logger.getLogger(AudioRecognizer.class.getName()).log(Level.WARNING, "Give me the correct entry string..");
//
//	}

	/** Performs microphone streaming speech recognition with a duration of 1 minute. */
	public static void streamingMicRecognize() throws Exception {

	  ResponseObserver<StreamingRecognizeResponse> responseObserver = null;
	  try (SpeechClient client = SpeechClient.create()) {

	    responseObserver =
	        new ResponseObserver<StreamingRecognizeResponse>() {
	          ArrayList<StreamingRecognizeResponse> responses = new ArrayList<>();

	          public void onStart(StreamController controller) {}

	          public void onResponse(StreamingRecognizeResponse response) {
	            responses.add(response);
	          }

	          public void onComplete() {
	            for (StreamingRecognizeResponse response : responses) {
	              StreamingRecognitionResult result = response.getResultsList().get(0);
	              SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
	              System.out.printf("Transcript : %s\n", alternative.getTranscript());
	            }
	          }

	          public void onError(Throwable t) {
	            System.out.println(t);
	          }
	        };

	    ClientStream<StreamingRecognizeRequest> clientStream =
	        client.streamingRecognizeCallable().splitCall(responseObserver);

	    RecognitionConfig recognitionConfig =
	        RecognitionConfig.newBuilder()
	            .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
	            .setLanguageCode("en-US")
	            .setSampleRateHertz(16000)
	            .build();
	    StreamingRecognitionConfig streamingRecognitionConfig =
	        StreamingRecognitionConfig.newBuilder().setConfig(recognitionConfig).build();

	    StreamingRecognizeRequest request =
	        StreamingRecognizeRequest.newBuilder()
	            .setStreamingConfig(streamingRecognitionConfig)
	            .build(); // The first request in a streaming call has to be a config

	    clientStream.send(request);
	    // SampleRate:16000Hz, SampleSizeInBits: 16, Number of channels: 1, Signed: true,
	    // bigEndian: false
	    AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false);
	    DataLine.Info targetInfo =
	        new Info(
	            TargetDataLine.class,
	            audioFormat); // Set the system information to read from the microphone audio stream

	    if (!AudioSystem.isLineSupported(targetInfo)) {
	      System.out.println("Microphone not supported");
	      System.exit(0);
	    }
	    // Target data line captures the audio stream the microphone produces.
	    TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
	    targetDataLine.open(audioFormat);
	    targetDataLine.start();
	    System.out.println("Start speaking");
	    long startTime = System.currentTimeMillis();
	    // Audio Input Stream
	    AudioInputStream audio = new AudioInputStream(targetDataLine);
	    while (true) {
	      long estimatedTime = System.currentTimeMillis() - startTime;
	      byte[] data = new byte[6400];
	      audio.read(data);
	      if (estimatedTime > Long.parseLong(Config.RECORD_TIME.trim())*1000) { 
	        System.out.println("Stop speaking.");
	        targetDataLine.stop();
	        targetDataLine.close();
	        break;
	      }
	      request =
	          StreamingRecognizeRequest.newBuilder()
	              .setAudioContent(ByteString.copyFrom(data))
	              .build();
	      clientStream.send(request);
	    }
	  } catch (Exception e) {
	    System.out.println(e);
	  }
	  responseObserver.onComplete();
	}
	
	public static void main(String[] args) throws Exception {
		streamingMicRecognize();

	}
}