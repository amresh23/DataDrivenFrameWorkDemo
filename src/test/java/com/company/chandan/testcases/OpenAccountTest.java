package com.company.chandan.testcases;

import java.util.HashMap;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.company.chandan.base.TestBase;
import com.company.chandan.utilities.TestUtilNew;

public class OpenAccountTest extends TestBase {

	@Test(dataProviderClass = TestUtilNew.class, dataProvider = "DP")
	public void openAccountTest(HashMap<String, String> data) throws InterruptedException
	
	//public void openAccountTest(String customer, String currency, String run_mode) throws InterruptedException
	{
		
		runMode("openAccountTest", excel);
		

		if(!data.get("Run_Mode").equalsIgnoreCase("Y")){
			
			throw new SkipException("Skipping the data set because run mode is selected as 'N'");
		}

		click("openaccount_CSS");
		dropDowns("customer_CSS", data.get("Customer"));

		/*
		 * List<WebElement> options = select.getOptions(); for(WebElement value
		 * : options){ if(value.getText().contains(objectRepo.getProperty(
		 * "customerSelectValue"))) {
		 * select.selectByVisibleText(value.getText());
		 * 
		 * } }
		 */
		Thread.sleep(1000);

		dropDowns("currency_CSS", data.get("Currency"));
		/*
		 * List<WebElement> options1 = select.getOptions(); for(WebElement value
		 * : options1){ if(value.getText().contains(objectRepo.getProperty(
		 * "customerSelectCurrency"))) {
		 * select.selectByVisibleText(value.getText());
		 * 
		 * } }
		 */ Thread.sleep(1000);
		click("process_CSS");
		Thread.sleep(2000);

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		Assert.assertTrue(alert.getText().contains(objectRepo.getProperty("OpenAccountMSG")), "Alert message is not as expected");

		alert.accept();

	}

}
