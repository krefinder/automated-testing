package com.android.web.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.web.app.resources.Web_App_BaseTest;

public class Gmail_Android_Web_Login_Objects extends Web_App_BaseTest {

	
	

	@FindBy(xpath = "//*[@content-desc='Sign in']")
	private WebElement Click_On_Sign_in_Button;
	
	
	public Gmail_Android_Web_Login_Objects Click_On_Sign_in_Button() {

		click(Click_On_Sign_in_Button, "Clicked_On_Sign_in_Button");

		return this;

	}

	//----------------------UserName---------------------
	
	@FindBy(id = "identifierId")
	private WebElement email_or_mobile_number_text_Field;
	
	
	

	
	public Gmail_Android_Web_Login_Objects  Click_text_email_or_mobile_number() {

		click(email_or_mobile_number_text_Field, "Clicked_in_email_or_mobile_number_text_Field");

		return this;

	}
	

	
	public Gmail_Android_Web_Login_Objects enter_email_or_mobile_number(String email) {

		sendKeys(email_or_mobile_number_text_Field, email);

		return this;

		
	}
	
	//----------------------NextButton---------------------
	
	@FindBy(xpath = "//*[contains(text(),'Continue as']")
	private WebElement Click_On_Next_Button;
	
	
	public Gmail_Android_Web_Login_Objects Click_On_Continue_Button() {

		click(Click_On_Next_Button, "Clicked_On_LoginPage_Continue_Button");

		return this;

	}

}
