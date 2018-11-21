package com.company.chandan.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.company.chandan.base.TestBase;

public class LoginTest extends TestBase {

	@Test
	public void login() throws InterruptedException {
		
		driver.findElement(By.cssSelector(objectRepo.getProperty("BankManagerLoginButton"))).click();
		log.debug("Login page opened successfully !!!!");
		Thread.sleep(1000);
		
		Assert.assertTrue(isElementPresent(By.cssSelector(objectRepo.getProperty("AddCustomerButton"))),"Login is not successful");

	}

}
