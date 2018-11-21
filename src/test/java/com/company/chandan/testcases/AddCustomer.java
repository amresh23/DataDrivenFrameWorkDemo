package com.company.chandan.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.company.chandan.base.TestBase;

public class AddCustomer extends TestBase{
	
	@Test(dataProvider = "getdata")
	public void AddCust(String fname, String lname, String postcode) throws InterruptedException{
		
		driver.findElement(By.cssSelector(objectRepo.getProperty("AddCustomerButton"))).click();
		
		driver.findElement(By.cssSelector(objectRepo.getProperty("FirstName"))).sendKeys(fname);
		driver.findElement(By.cssSelector(objectRepo.getProperty("LastName"))).sendKeys(lname);
		driver.findElement(By.cssSelector(objectRepo.getProperty("PostCode"))).sendKeys(postcode);
		driver.findElement(By.cssSelector(objectRepo.getProperty("AddcusButton"))).click();
		
		Thread.sleep(1000);
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		
		Assert.assertTrue(alert.getText().contains(objectRepo.getProperty("AlertMessage")), "Alert message is not as expected");
		
		alert.accept();
		
		}
	
	@DataProvider
	public Object[][] getdata(){
		
		String sheetname = config.getProperty("ExcelPathSheetname");
		int rows = excel.getRowCount(sheetname);
		int cols = excel.getColumnCount(sheetname);
		
		Object[][] data = new Object[rows-1][cols];
		
		for(int rownum=2; rownum<=rows; rownum++){
			
			for(int colnum=0; colnum<cols; colnum++){
				
				
				data[rownum-2][colnum] = excel.getCellData(sheetname, colnum, rownum);
			}
			
			
		}
		return data;
		
		
	}

}
