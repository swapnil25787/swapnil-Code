package com.abm.mainet.ts;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import common.CommonMethods;

import errorhandling.MainetCustomExceptions;
import excelreader.ExcelReader;
import excelreader.ExcelWriting;

public class TSMastersAndTransactions extends CommonMethods {

	ExcelReader ER = new ExcelReader();
	ExcelWriting EW =new ExcelWriting();
	CommonMethods common=new CommonMethods();
	

	@BeforeSuite
	public void beforeSuite(){

		System.out.println("Starting before suite");
		thisClassName=this.getClass().getCanonicalName();
		myClassName = thisClassName;
		System.out.println("This is the current class name: "+myClassName );
		

	}

	
	////////////////redminetimesheet/////////////////////////////////////////////////////////////////


	@Test(enabled=false)  
	public void TS_redmine()
	{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();

		
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			mWaitForVisible("id", mGetPropertyFromFile("redmineid"));
			driver.findElement(By.id(mGetPropertyFromFile("redmineid"))).sendKeys(mGetPropertyFromFile("redminedata"));
			driver.findElement(By.id(mGetPropertyFromFile("redminepassid"))).sendKeys(mGetPropertyFromFile("redminepassdata"));	
			mClick("name", mGetPropertyFromFile("redlogin"));
			mWaitForVisible("id", mGetPropertyFromFile("dropdownredmine"));
			mSelectDropDown("id", mGetPropertyFromFile("dropdownredmine"), mGetPropertyFromFile("projectname"));
			mWaitForVisible("linkText", mGetPropertyFromFile("logtimelink"));
			mClick("linkText", mGetPropertyFromFile("logtimelink"));
			mWaitForVisible("css", mGetPropertyFromFile("continuesubmit"));
			mCustomWait(2000); 



		}catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in EIP_redmine method");
		}
	}
	@Test(enabled=false)
	public void TS_redminets()
	{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();



			EIP_redmine_script();

		}catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in EIP_redmine method");
		}
	}
	public void EIP_redmine_script()
	{
		try{


			/////////////////////////////////////////////
			mWaitForVisible("css", mGetPropertyFromFile("continuesubmit"));
			String s=mGetPropertyFromFile("Issue").trim();
			System.out.println("s==>"+s);
			String s1=" ";
			if(s.equalsIgnoreCase("?")){
				driver.findElement(By.id(mGetPropertyFromFile("issueid"))).clear();
				driver.findElement(By.id(mGetPropertyFromFile("issueid"))).sendKeys(s1);	
			}else {
				driver.findElement(By.id(mGetPropertyFromFile("issueid"))).clear();
				driver.findElement(By.id(mGetPropertyFromFile("issueid"))).sendKeys(s);		
			}

			//
			driver.findElement(By.id(mGetPropertyFromFile("dateid"))).clear();
 
			/*String s=mGetPropertyFromFile("Date");
String[] ss=s.split("-");
for (int i = 0; i < ss.length; i++) {
	System.out.println(i+"S=>"+ss[i]);
} 
			 */
			driver.findElement(By.id(mGetPropertyFromFile("dateid"))).sendKeys(mGetPropertyFromFile("Date"));
			
			driver.findElement(By.id(mGetPropertyFromFile("hoursid"))).clear();
			driver.findElement(By.id(mGetPropertyFromFile("hoursid"))).sendKeys(mGetPropertyFromFile("Hours"));
			driver.findElement(By.id(mGetPropertyFromFile("commentsid"))).clear();
			driver.findElement(By.id(mGetPropertyFromFile("commentsid"))).sendKeys(mGetPropertyFromFile("comment"));

			mSelectDropDown("id", mGetPropertyFromFile("activityid"), mGetPropertyFromFile("activity"));

				
			mClick("css", mGetPropertyFromFile("continuesubmit"));

			mCustomWait(1000);	
			if(driver.findElement(By.id("flash_notice")).isDisplayed())
			{

				EW.excelWriting(mGetPropertyFromFile("refpath"),"Reshedule","DONE","9");	
			}

		}catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;


			throw new MainetCustomExceptions("Error in EIP_redmine_script method");
		}
	}



}


