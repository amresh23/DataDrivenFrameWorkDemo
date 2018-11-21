package com.company.chandan.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.company.chandan.base.TestBase;



public class TestUtilNew extends TestBase{
	public static String screenShotName;
	public static String screenShotPath;
	
	
	public static void captureScreenshot() throws IOException{
		Date date = new Date();
		screenShotName = date.toString().replace(":", "_").replace(" ", "_")+".jpg";
		screenShotPath = (System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\"+screenShotName);
		
		 		
		TakesScreenshot ts = (TakesScreenshot)driver;
		File scrFile = ts.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(screenShotPath));
		//FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\"+screenShotName));
		/* REportNG Report
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"\\test-output\\html\\"+screenShotName));
			*/	
	}
	
	@DataProvider(name="DP")
	public Object[][] getdata(Method m){
		
		String sheetname = m.getName();
		int rows = excel.getRowCount(sheetname);
		int cols = excel.getColumnCount(sheetname);
		
		Object[][] data = new Object[rows-1][1];
		HashMap<String, String> table = null;
		
		for(int rownum=2; rownum<=rows; rownum++){
			
			table = new HashMap<String, String>();
			
			
			for(int colnum=0; colnum<cols; colnum++){
				
				table.put(excel.getCellData(sheetname, colnum, 1), excel.getCellData(sheetname, colnum, rownum));
				data[rownum-2][0] = table;
			}
			
			
		}
		return data;
		
		}
	
	/*@DataProvider(name="DP")
	public Object[][] getdata(Method m){
		
		String sheetname = m.getName();
		int rows = excel.getRowCount(sheetname);
		int cols = excel.getColumnCount(sheetname);
		
		Object[][] data = new Object[rows-1][cols];
		
		for(int rownum=2; rownum<=rows; rownum++){
			
			for(int colnum=0; colnum<cols; colnum++){
				
				
				data[rownum-2][colnum] = excel.getCellData(sheetname, colnum, rownum);
			}
			
			
		}
		return data;
		
		}*/
	
	public static boolean isTestRunnable(String testName, ExcelReader excel){
		
		String sheetname = "test_suite";
		int rowcount = excel.getRowCount(sheetname);
		
		for(int row=2; row<=rowcount; row++){
			String sheetTestName = excel.getCellData(sheetname, "TestID", row);
			if(sheetTestName.equalsIgnoreCase(testName)){
				String runMode = excel.getCellData(sheetname, "RunnableMode", row);
				
				if(runMode.equalsIgnoreCase("Y")){
					return true;
				}
				
				else{
					return false;
				}
				
			}
			
		}
		
		return false;
	}

}
