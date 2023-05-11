/*
 *  FileName :: Get_privacy_policy_text_from_webapps_jsoup.java
 *  Author :: Divya Kolagani
 *  
 *  This program requires an app name and xpath of the element to be located  as input string and processes apps  in the file.
 *  Once the app url is copied, it will search reach the  browser of abd device or emulator and searches for the url.
 *  It will reach out to the exact element to be extracted using xpath defined in the input excel.  
 *  Once text is extracted, using advanced html reporting, displays the text for each application.
 *  It also sets the capabilities of the emulator to establish connection.
 */

package com.web.app.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import javax.mail.MessagingException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.android.web.config.Config;
import com.android.web.pageobjects.Gmail_Android_Web_Login_Objects;
import com.app.web.reports.ExtentReport;
import com.aventstack.extentreports.Status;
import com.web.app.resources.Web_App_BaseTest;

public class Get_privacy_policy_text_from_webapps_jsoup extends Web_App_BaseTest {

	Gmail_Android_Web_Login_Objects LoginPage;

	InputStream detais;

	JSONObject Login_User;

	SoftAssert Asert;

	String privacyText = null;

	@BeforeMethod
	public void beforeMethod(Method Method_Name) throws IOException {

		LoginPage = new Gmail_Android_Web_Login_Objects();

		Load_TestData_PropertiesFile();

		System.out.println("\n  ---- Test Started :-  " + Method_Name.getName());

		Asert = new SoftAssert();

	}

	@BeforeClass
	public void BeforeClass() throws Exception {

		try {

			String DataFileName = "/TestData_Jsons/Web_Login_TestData.json";

			detais = Get_privacy_policy_text_from_webapps_jsoup.class.getResourceAsStream(DataFileName);

			JSONTokener tokener = new JSONTokener(detais);

			Login_User = new JSONObject(tokener);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			if (detais != null) {

				detais.close();

			}

		}
	}

	@Test(priority = 1)
	public void Get_Privacy_PolicyText_From_WebApps() throws IOException, MessagingException, InterruptedException {

		File src = new File(System.getProperty("user.dir") + "/Test-Data//WebUrls_jsoup.xlsx");

		System.out.println("Excel Located");

		FileInputStream fis = new FileInputStream(src);

		XSSFWorkbook wb = new XSSFWorkbook(fis);

		XSSFSheet Sheet1 = wb.getSheetAt(0);

		XSSFCell cell = null;

		XSSFCell cell1 = null;

		int s = Sheet1.getLastRowNum() + 1;

		for (int i = 1; i < s; i++) {

			cell = Sheet1.getRow(i).getCell(0);

			cell1 = Sheet1.getRow(i).getCell(1);

			if (cell == null || cell1 == null) {

				break;

			} else {

				ExtentReport.startTest(cell.toString(), null);

				String url = cell1.toString();

				if (url.contains("walmart")) {

					Connection connection = Jsoup.connect(url);

					Document document = connection.get();

					Document doc = Jsoup.parse(document.toString());

					Elements links = doc.getElementsByTag("article");

					for (int j = 0; j < links.size(); j++) {

						if (links.get(j).tagName().equals("article")
								&& links.get(j).attr("id").contains("what-type-of-information-do-we-collect")) {
							privacyText = links.get(j).text();

							System.out.println(cell.toString() + " = " + privacyText);

							ExtentReport.getTest().log(Status.INFO,

									"<B style= color:#0000FF>" + "App_Name = " + cell.toString() + "</B>");

							ExtentReport.getTest().log(Status.PASS,

									"<B style= color:#0000FF>" + "PrivacyInformationText = " + "</B>" + "<B>"+ privacyText+"</B>");

							break;

						}

					}

				}

				else if (url.contains("remitly")) {

					Connection connection = Jsoup.connect(url);

					Document document = connection.get();

					Document doc = Jsoup.parse(document.toString());

					Elements links = doc.getElementsByTag("div");

					String str_ProductName = null;
					for (int j = 0; j < links.size(); j++) {

						if (links.get(j).tagName().equals("div")
								&& links.get(j).attr("class").contains("rm-col-12 rm-col-lg-8 rm-offset-lg-2")) {
							str_ProductName = links.get(j).text();

							privacyText = str_ProductName.substring(str_ProductName.indexOf(" Basic Identifying") + 1,
									str_ProductName.indexOf("pay slip)"));

							System.out.println(cell.toString() + " = " + privacyText);

							ExtentReport.getTest().log(Status.INFO,

									"<B style= color:#0000FF>" + "App_Name = " + cell.toString() + "</B>");

							ExtentReport.getTest().log(Status.PASS,

									"<B style= color:#0000FF>" + "PrivacyInformationText = " + "</B>" + "<B>"+ privacyText+"</B>");

						}

					}

				} else if (url.contains("netflix")) {

					Connection connection = Jsoup.connect(url);

					Document document = connection.get();

					Document doc = Jsoup.parse(document.toString());

					Elements links = doc.getElementsByTag("div");

					String str_ProductName = null;
					for (int j = 0; j < links.size(); j++) {

						if (links.get(j).tagName().equals("div") && links.get(j).attr("class").contains("c-wrapper")) {
							str_ProductName = links.get(j).text();

							privacyText = str_ProductName.substring(
									str_ProductName.indexOf(" Information you provide to us:") + 1,
									str_ProductName.indexOf("information when you choose"));

							System.out.println(cell.toString() + " = " + privacyText);

							ExtentReport.getTest().log(Status.INFO,

									"<B style= color:#0000FF>" + "App_Name = " + cell.toString() + "</B>");

							ExtentReport.getTest().log(Status.PASS,

									"<B style= color:#0000FF>" + "PrivacyInformationText = " + "</B>" + "<B>"+ privacyText+"</B>");

						}

					}

				} else if (url.contains("bankofamerica")) {

					Connection connection = Jsoup.connect(url);

					Document document = connection.get();

					Document doc = Jsoup.parse(document.toString());

					Elements links = doc.getElementsByTag("div");

					String str_ProductName = null;

					for (int j = 0; j < links.size(); j++) {

						if (links.get(i).tagName().equals("div")
								&& links.get(j).attr("class").contains("squeezebox-panel")) {
							str_ProductName = links.get(j).text();

							try {

								privacyText = str_ProductName.substring(
										str_ProductName.indexOf(" Contact Information") + 1,
										str_ProductName.indexOf("Information from your computer "));

								System.out.println(cell.toString() + " = " + privacyText);

								ExtentReport.getTest().log(Status.INFO,

										"<B style= color:#0000FF>" + "App_Name = " + cell.toString() + "</B>");

								ExtentReport.getTest().log(Status.PASS,

										"<B style= color:#0000FF>" + "PrivacyInformationText = " + "</B>"
												+ "<B>"+ privacyText+"</B>");

								break;

							} catch (Exception e) {

							}

						}

					}

				} else if (url.contains("policies.google.com")) {

					Connection connection = Jsoup.connect(url);

					Document document = connection.get();

					Document doc = Jsoup.parse(document.toString());

					Elements links = doc.getElementsByTag("p");

					for (int j = 108; j < links.size(); j++) {

						if (links.get(j).tagName().equals("p")) {
							privacyText = links.get(j).text();

							System.out.println(cell.toString() + " = " + privacyText);

							ExtentReport.getTest().log(Status.INFO,

									"<B style= color:#0000FF>" + "App_Name = " + cell.toString() + "</B>");

							ExtentReport.getTest().log(Status.PASS,

									"<B style= color:#0000FF>" + "PrivacyInformationText = " + "</B>" + "<B>"+ privacyText+"</B>");

							break;

						}

					}

				} else if (url.contains("paypal")) {

					Connection connection = Jsoup.connect(url);

					Document document = connection.get();

					Document doc = Jsoup.parse(document.toString());

					Elements links = doc.getElementsByTag("li");

					for (int j = 0; j < links.size(); j++) {

						if (links.get(j).tagName().equals("li")) {
							privacyText = links.get(j).text();

							System.out.println(cell.toString() + " = " + privacyText);

							ExtentReport.getTest().log(Status.INFO,

									"<B style= color:#0000FF>" + "App_Name = " + cell.toString() + "</B>");

							ExtentReport.getTest().log(Status.PASS,

									"<B style= color:#0000FF>" + "PrivacyInformationText = " + "</B>" + "<B>"+ privacyText+"</B>");

							break;

						}

					}

				} else if (url.contains("explore.zoom")) {
					Connection connection = Jsoup.connect(url);

					Document document = connection.get();

					Document doc = Jsoup.parse(document.toString());

					Elements links = doc.getElementsByTag("li");

					for (int j = 119; j < links.size(); j++) {

						if (links.get(j).tagName().equals("li")) {
							privacyText = links.get(j).text();

							System.out.println(cell.toString() + " = " + privacyText);

							ExtentReport.getTest().log(Status.INFO,

									"<B style= color:#0000FF>" + "App_Name = " + cell.toString() + "</B>");

							ExtentReport.getTest().log(Status.PASS,

									"<B style= color:#0000FF>" + "PrivacyInformationText = " + "</B>" +"<B>"+ "<B>"+ privacyText+"</B>"+"</B>");

							break;

						}

					}

				}

			}
		}

	}

}