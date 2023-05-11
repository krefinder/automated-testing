package com.app.web.reports;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport {
	 public static ExtentReports extent;
    
    final static String filePath = System.getProperty("user.dir")+"/AutomationReports/AutomationReport.html";
    static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
    
    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
        //	ExtentHtmlReporter html = new ExtentHtmlReporter(filePath);
        	ExtentSparkReporter html = new ExtentSparkReporter(filePath);
        	html.config().setDocumentTitle("Appium Framework");
        	html.config().setReportName("Web_privacy_policy");
        	html.config().setTheme(Theme.STANDARD);
            extent = new ExtentReports();
            extent.attachReporter(html);
                   }
        
        return extent;	
    }
    
    public static synchronized ExtentTest getTest() {
        return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized ExtentTest startTest(String testName, String desc) {
    	
        ExtentTest test = getReporter().createTest("<B>"+testName+"</B>", desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }
}
