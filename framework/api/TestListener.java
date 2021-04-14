package api;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
	//WebDriver driver=null;
//	String filePath = "D:\\SCREENSHOTS";
    @Override
    public void onTestFailure(ITestResult result) {
    	System.out.println("***** Error "+result.getName()+" test has failed *****");
    	/*String methodName=result.getName().toString().trim();
    	//takeScreenShot(methodName);
    	CommonUtilsAPI sss=new CommonUtilsAPI();
    	try {
			//sss.errorTakeScreenShot();
			sss.mTakeScreenShot();
    		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    }
    
   
	public void onFinish(ITestContext context) {
		
		
		System.out.println("+++++++++++++++++++imintestlistner onFinish+++++++++++++++++++++++++++++++++");	}
  
    public void onTestStart(ITestResult result) {  System.out.println("+++++++++++++++++++imintestlistner onTestStart+++++++++++++++++++++++++++++++++"); }
  
    public void onTestSuccess(ITestResult result) {  System.out.println("+++++++++++++++++++imintestlistner onTestSuccess+++++++++++++++++++++++++++++++++"); }

    public void onTestSkipped(ITestResult result) {System.out.println("+++++++++++++++++++imintestlistner onTestSkipped+++++++++++++++++++++++++++++++++");   }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {  System.out.println("+++++++++++++++onTestFailedButWithinSuccessPercentage++++imintestlistner+++++++++++++++++++++++++++++++++"); }

    public void onStart(ITestContext context) {  System.out.println("+++++++++++++++++++imintestlistner++++++++onStart+++++++++++++++++++++++++"); }
    
}  