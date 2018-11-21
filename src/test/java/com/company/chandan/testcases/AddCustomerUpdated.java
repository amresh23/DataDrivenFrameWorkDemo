package com.company.chandan.testcases;

import java.util.HashMap;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.company.chandan.base.TestBase;
import com.company.chandan.utilities.TestUtilNew;

public class AddCustomerUpdated extends TestBase{
	
	@Test(dataProviderClass = TestUtilNew.class, dataProvider="DP")
	public void addCustomerUpdated(HashMap<String, String> data) throws InterruptedException
	{
	//public void addCustomerUpdated(String fname, String lname, String postcode, String run_Mode) throws InterruptedException
		runMode("addCustomerUpdated", excel);
		
        if(!data.get("Run_Mode").equalsIgnoreCase("Y")){
			
			throw new SkipException("Skipping the data set because run mode is selected as 'N'");
		}
		
		click("AddCustomerButton_CSS");
		type("FirstName_CSS", data.get("FirstName"));
		
		type("LastName_XPATH", data.get("LastName"));
		
		type("PostCode_CSS", data.get("PostCode"));
		
		click("AddcusButton_CSS");
		
		Thread.sleep(500);
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		
		Assert.assertTrue(alert.getText().contains(objectRepo.getProperty("AlertMessage")), "Alert message is not as expected");
		
		alert.accept();
		
		}
	
	
}
