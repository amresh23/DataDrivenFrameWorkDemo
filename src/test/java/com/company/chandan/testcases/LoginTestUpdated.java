package com.company.chandan.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.company.chandan.base.TestBase;

public class LoginTestUpdated extends TestBase {

	@Test
	public void loginTestUpdated() throws InterruptedException, IOException {
		
		runMode("loginTestUpdated", excel);
		
		verificationTest("abc", "aaa");
		Thread.sleep(1000);
		click("BankManagerLoginButton_CSS");
		log.debug("Login page opened successfully !!!!");
		Thread.sleep(1000);
		Assert.assertTrue(isElementPresent(By.cssSelector(objectRepo.getProperty("AddCustomerButton_CSS"))),"Login is not successful");

	}

}
