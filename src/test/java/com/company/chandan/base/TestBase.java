package com.company.chandan.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.company.chandan.utilities.ExcelReader;
import com.company.chandan.utilities.ExtentManager;
import com.company.chandan.utilities.TestUtilNew;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase {
	
	/*
	 * WebDriver 
	 * Properties
	 * Logs - log4j.jar, .log, log4j.properties, Logger
	 * ExtentReport
	 * DB
	 * Excel
	 * Mail
	 * ReportNG, ExtentReports
	 * Jenkins
	 * 
	 */
	
	public static WebDriver driver = null;
	public static Properties config = new Properties();
	public static Properties objectRepo = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\resources\\excels\\TestData1.xlsx");
	public static WebDriverWait wait;
	public ExtentReports report1 = ExtentManager.getInstance();
	public static ExtentTest test;
	public static String browser;
	
	
	
	@BeforeSuite
	public void setUp(){
		
		
		if(driver ==null){
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Config File loaded successfully !!!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\ObjectRepository.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				objectRepo.load(fis);
				log.debug("Object Repository file loaded successfully !!!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if(System.getenv("browser")!= null && !System.getenv("browser").isEmpty())
			{
				
				browser = System.getenv("browser");
				
			}
			else
			{
				browser = config.getProperty("browser");
				
			}
			
			config.setProperty("browser", browser);
			
			if(config.getProperty("browser").equals("firefox")){
				driver = new FirefoxDriver();
				log.debug("Firefox browser loaded successfully !!!!");
			}
			else if(config.getProperty("browser").equals("chrome")){
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\chromedriver.exe");
				driver = new ChromeDriver();
				log.debug("Internet Explorer browser loaded successfully !!!!");
			}
			else if(config.getProperty("browser").equals("ie")){
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				log.debug("Chrome browser loaded successfully !!!!");
			}
			
			driver.manage().window().maximize();
			driver.get(config.getProperty("testsiteURL"));
			log.debug("Successfully opened the website : "+config.getProperty("testsiteURL") );
			driver.manage().timeouts().implicitlyWait((Integer.parseInt(config.getProperty("ImplicitlyWait"))),TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 5);
			
		}
		
		
		
	}
	
	public boolean isElementPresent(By by){
		
		try{
			driver.findElement(by);
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
		
	}
	
	public void click(String locator){
		
		if(locator.endsWith("_CSS")){
		
		driver.findElement(By.cssSelector(objectRepo.getProperty(locator))).click();
		test.log(LogStatus.INFO, "Clicking on : " + locator);
		}
		else if(locator.endsWith("_XPATH")){
			driver.findElement(By.xpath(objectRepo.getProperty(locator))).click();
			test.log(LogStatus.INFO, "Clicking on : " + locator);
		}
		else if(locator.endsWith("_ID")){
			driver.findElement(By.id(objectRepo.getProperty(locator))).click();
			test.log(LogStatus.INFO, "Clicking on : " + locator);
		}
		else if(locator.endsWith("_NAME")){
			driver.findElement(By.name(objectRepo.getProperty(locator))).click();
			test.log(LogStatus.INFO, "Clicking on : " + locator);
		}
		else if(locator.endsWith("_LINKTEXT")){
			driver.findElement(By.linkText(objectRepo.getProperty(locator))).click();
			test.log(LogStatus.INFO, "Clicking on : " + locator);
		}
		else if(locator.endsWith("_TAGNAME")){
			driver.findElement(By.tagName(objectRepo.getProperty(locator))).click();
			test.log(LogStatus.INFO, "Clicking on : " + locator);
		}
	}
	
	public void type(String locator, String value){
		if(locator.endsWith("_CSS")){
		driver.findElement(By.cssSelector(objectRepo.getProperty(locator))).sendKeys(value);
		test.log(LogStatus.INFO, "Typing in : " + locator + " and Entered value : " + value);
		}
		else if(locator.endsWith("_XPATH")){
			driver.findElement(By.xpath(objectRepo.getProperty(locator))).sendKeys(value);
			test.log(LogStatus.INFO, "Typing in : " + locator + " and Entered value : " + value);
		}
		else if(locator.endsWith("_ID")){
			driver.findElement(By.id(objectRepo.getProperty(locator))).sendKeys(value);
			test.log(LogStatus.INFO, "Typing in : " + locator + " and Entered value : " + value);
		}
		else if(locator.endsWith("_NAME")){
			driver.findElement(By.name(objectRepo.getProperty(locator))).sendKeys(value);
			test.log(LogStatus.INFO, "Typing in : " + locator + " and Entered value : " + value);
		}
		else if(locator.endsWith("_LINKTEXT")){
			driver.findElement(By.linkText(objectRepo.getProperty(locator))).sendKeys(value);
			test.log(LogStatus.INFO, "Typing in : " + locator + " and Entered value : " + value);
		}
		else if(locator.endsWith("_TAGNAME")){
			driver.findElement(By.tagName(objectRepo.getProperty(locator))).sendKeys(value);
			test.log(LogStatus.INFO, "Typing in : " + locator + " and Entered value : " + value);
		}

	}
	
	public void verificationTest(String expected, String Actual) throws IOException{
		
		try{
			Assert.assertEquals(expected, Actual, "Verification Test : ");
		} catch(Throwable t){
			
			TestUtilNew.captureScreenshot();
			//ReportNG
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			Reporter.log("Verification error found with exception : " + t.getMessage());
			Reporter.log("<br>");
			Reporter.log("<br>");
			Reporter.log("<a target=\"_blank\" href="+TestUtilNew.screenShotPath+"><img src="+TestUtilNew.screenShotPath+" height=300 width=300></img></a>");
			//ExtentReport
			test.log(LogStatus.FAIL, "Verification error found with exception : " + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtilNew.screenShotPath));
		}
	}

	public static WebElement element;
	public static Select select;
	
	
	public void dropDowns(String locator, String value){
		if(locator.endsWith("_CSS")){
			element = driver.findElement(By.cssSelector(objectRepo.getProperty(locator)));
			select = new Select(element);
			select.selectByVisibleText(value);
			test.log(LogStatus.INFO, "Selecting the : " + locator + " and  DropDown value : " + value);
			}
			else if(locator.endsWith("_XPATH")){
				element = driver.findElement(By.xpath(objectRepo.getProperty(locator)));
				select = new Select(element);
				select.selectByVisibleText(value);
				test.log(LogStatus.INFO, "Selecting the : " + locator + " and DropDown value : " + value);
			}
			else if(locator.endsWith("_ID")){
				element = driver.findElement(By.id(objectRepo.getProperty(locator)));
				select = new Select(element);
				select.selectByVisibleText(value);
				test.log(LogStatus.INFO, "Selecting the : " + locator + " and DropDown value : " + value);
			}
			else if(locator.endsWith("_NAME")){
				element = driver.findElement(By.name(objectRepo.getProperty(locator)));
				select = new Select(element);
				select.selectByVisibleText(value);
				test.log(LogStatus.INFO, "Selecting the : " + locator + " and DropDown value : " + value);
			}
			else if(locator.endsWith("_LINKTEXT")){
				element = driver.findElement(By.linkText(objectRepo.getProperty(locator)));
				select = new Select(element);
				select.selectByVisibleText(value);
				test.log(LogStatus.INFO, "Selecting the : " + locator + " and DropDown value : " + value);
			}
			else if(locator.endsWith("_TAGNAME")){
				element = driver.findElement(By.tagName(objectRepo.getProperty(locator)));
				select = new Select(element);
				select.selectByVisibleText(value);
				test.log(LogStatus.INFO, "Selecting the : " + locator + " and DropDown value : " + value);
			}
		
	}
	
	public static void runMode(String methodName, ExcelReader excel) {

		if (!TestUtilNew.isTestRunnable(methodName, excel)) {

			throw new SkipException("Skipping the test case : " + methodName+ " becasue run mode is NO");
		}
	}
	@AfterSuite
	public void tearDown(){
		
		if(driver!=null){
			driver.quit();
		}
		log.debug("Website Application completed successfully");
		
	}

}
