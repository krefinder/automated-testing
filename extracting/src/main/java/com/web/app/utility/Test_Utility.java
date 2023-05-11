package com.web.app.utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.android.web.config.Config;

public class Test_Utility {

	public static final Duration WAIT = Duration.ofSeconds(15);

	public HashMap<String, String> parseStringXML(InputStream file) throws Exception {

		HashMap<String, String> StringMap = new HashMap<String, String>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder = factory.newDocumentBuilder();

		Document document = builder.parse(file);

		document.getDocumentElement().normalize();

		Element root = document.getDocumentElement();

		NodeList nList = root.getElementsByTagName("String");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node node = nList.item(temp);

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) node;

				StringMap.put(eElement.getAttribute("name"), eElement.getTextContent());

			}

		}
		return StringMap;

	}
	
	public static String TakeScreenShot() throws IOException {

		TakesScreenshot ts = (TakesScreenshot) Config.driver;

		File source = ts.getScreenshotAs(OutputType.FILE);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date();
		String systemtime = dateFormat.format(date);

		String dest = System.getProperty("user.dir") + "/All_ScreenShots/Script_FAIL_" + systemtime + ".png";

		System.err.println(dest);

		File destination = new File(dest);

		FileUtils.copyFile(source, destination);

		System.out.println("Screenshot taken");

		return dest;

	}

}
