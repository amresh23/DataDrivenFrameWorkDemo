package com.company.chandan.listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
 import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.company.chandan.base.TestBase;
import com.company.chandan.utilities.MonitoringMail;
import com.company.chandan.utilities.TestConfig;
import com.company.chandan.utilities.TestUtilNew;
import com.relevantcodes.extentreports.LogStatus;

public class CustomListeners extends TestBase implements ITestListener, ISuiteListener{
	public static String messageBody;

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		

		

		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult result) {
		
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		
			
			try {
				TestUtilNew.captureScreenshot();
			} catch (IOException e) {
				e.printStackTrace();
			}

			//String snapshotname = (System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\"+TestUtilNew.screenShotName);
			
			test.log(LogStatus.FAIL, result.getName().toUpperCase()+" FAILED with execption : "+result.getThrowable());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtilNew.screenShotPath));
			//test.log(LogStatus.FAIL, test.addScreenCapture(snapshotname));
			
			Reporter.log("Click to open the failed test case screen shot: ");
			
			
			
			Reporter.log("<a target=\"_blank\" href="+TestUtilNew.screenShotPath+">Screenshot</a>");
			//Reporter.log("<a target=\"_blank\" href="+snapshotname+">Screenshot</a>");

			Reporter.log("<br>");
			Reporter.log("<br>");
			Reporter.log("<a target=\"_blank\" href="+TestUtilNew.screenShotPath+"><img src="+TestUtilNew.screenShotPath+" height=300 width=300></img></a>");
			//Reporter.log("<a target=\"_blank\" href="+snapshotname+"><img src="+snapshotname+" height=300 width=300></img></a>");
			
			report1.endTest(test);
			report1.flush();
		
	}

	public void onTestSkipped(ITestResult result) {


		test.log(LogStatus.SKIP, result.getName().toUpperCase()+ ": SKIPPED");
		report1.endTest(test);
		report1.flush();
	}

	public void onTestStart(ITestResult result) {
		
		test= report1.startTest(result.getName().toUpperCase(), "Finallly got it");
		
		
		

	}

	public void onTestSuccess(ITestResult result) {
		
		test.log(LogStatus.PASS, result.getName().toUpperCase()+": PASS");
		report1.endTest(test);
		report1.flush();

		
	}

	public void onFinish(ISuite arg0) {
		MonitoringMail mail = new MonitoringMail();
		try {
			messageBody = "http://" + InetAddress.getLocalHost().getHostAddress()
					+ ":8080/job/DataDrivenLiveProject/Extent_Reports/";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(messageBody);
		
		try {
			mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void onStart(ISuite arg0) {
		// TODO Auto-generated method stub
		
	}

}
