package com.android.web.listeners;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.app.web.reports.ExtentReport;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.web.app.utility.Test_Utility;

public class TestListener implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {

		Map<String, String> params = new HashMap<String, String>();
		params = result.getTestContext().getCurrentXmlTest().getAllParameters();

		ExtentReport.startTest(result.getName(), result.getMethod().getDescription())

				.assignAuthor(params.get("Author"));

		ExtentReport.getTest().log(Status.INFO,
				"<B style= color:#0000FF>" + result.getName() + " - Test Starts" + "</B>");

	}

	@Override
	public void onTestFailure(ITestResult result) {

		try {
			String completeImagePath = Test_Utility.TakeScreenShot();
			ExtentReport.getTest().fail("Test Failed",
					MediaEntityBuilder.createScreenCaptureFromPath(completeImagePath).build());
		} catch (IOException e) {

			e.printStackTrace();
		}

		ExtentReport.getTest().log(Status.FAIL, "<B style= color:#FF0000>" + result.getName()
				+ "<B style= color:#FF5733>" + " - Test Failed - " + result.getThrowable() + "</B>");

	}

	@Override
	public void onTestSuccess(ITestResult result) {

		ExtentReport.getTest().log(Status.PASS, "<B style= color:#0000FF>" + result.getName()
				+ "<B style= color:#1DAB19>" + "- Test Pass" + "</B>" + "</B>");
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		ExtentReport.getTest().log(Status.SKIP, result.getName() + " - Test Skipped");

	}

	@Override
	public void onFinish(ITestContext context) {

		ExtentReport.getReporter().flush();
	}

}
