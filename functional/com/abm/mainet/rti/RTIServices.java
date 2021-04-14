package com.abm.mainet.rti;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import api.CommonUtilsAPI;

import common.CommonMethods;

import errorhandling.MainetCustomExceptions;
import excelreader.ExcelReader;
import excelreader.ExcelToProperties;

/**
 * @author Sneha.Solaskar
 *
 */

public class RTIServices extends CommonMethods{

	boolean isEmail=false;

	static String date;
	long datIncr;
	//static boolean aplbpl= false;
	// for pdf readers of RTI Application
	URL url;
	BufferedInputStream fileToParse;
	PDFParser parser;
	String output;
	public static String deliveryMode = null;

	//rejected location 
	public boolean rejectedAtSecondAppeal=false;
	public boolean rejectedByAPA = false;
	public boolean rejectedAtPIO = false;
	public boolean frwdToRelvntOfficer = false;


	//public static String LOINO;
	public static boolean aprvdFrmPIOFrDisDtls =false;
	static boolean goForFirstAppeal= false;
	static boolean isAcceptedToAPA=false;
	static boolean isAcceptedAftrSecApl = false;
	//	static boolean isApprovedAftrSecApl = false;
	static boolean isAcceptedOrRejected= false;
	static boolean firstAppealFromCitizen=false;
	static boolean isAcceptedForHearinProcess=false;

	static Properties prop = new Properties();
	//WebDriver driver;
	static CommonMethods common =new CommonMethods();
	ExcelToProperties excelToProp = new ExcelToProperties();
	ExcelReader ER = new ExcelReader();

	public static ArrayList<String> isAcceptedByAPA = new ArrayList<String>();
	public static ArrayList<String> isEmailArray = new ArrayList<String>();
	public static int c=0;

	@BeforeSuite
	public void beforeSuite(){

		thisClassName=this.getClass().getCanonicalName();
		myClassName = thisClassName;
		mCreateModuleFolder("RTI_",myClassName);
	}



	//RTI Application Entry
	@Test(enabled=false)
	public void rti_ApplicationEntry() throws InterruptedException, IOException{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			aplbpl=false;
			rtiApplicationEntry();
		//	robot();
			//System.out.println("swapchallnnolist====>"+swapchallnnolist);
			CommonUtilsAPI.rtiAppErrorMsg.assertAll();
			
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in rti_ApplicationEntry method");
		}
	}

	//RTI Response
	@Test(enabled=false)
	public void rti_Response() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			RTIAppCustomErrorMessages.rtiApp_m_errors.entrySet().clear();
			rtiResponse();
			//System.out.println("swapchallnnolist====>"+swapchallnnolist);
			CommonUtilsAPI.rtiAppErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in rti_Response method");
		}
	}

	//First appeal from citizen login
	@Test(enabled=false) 
	public void rti_FirstAppealEntry() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			RTIAppCustomErrorMessages.rtiApp_m_errors.entrySet().clear();
			//		goForFirstAppeal= true;
			rtiFirstAppealEntry();
			CommonUtilsAPI.rtiAppErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in rti_FirstAppealEntry method");
		}
	}

	//First appeal from APA login
	@Test(enabled=false)
	public void rti_FirstAppealResponse() throws InterruptedException, IOException{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			RTIAppCustomErrorMessages.rtiApp_m_errors.entrySet().clear();
			rtiFirstAppealResponse();
			CommonUtilsAPI.rtiAppErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in rti_FirstAppealResponse method");
		}
	}

	//Hearing process from APA login
	@Test(enabled=false)
	public void rti_HearingProcess() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			RTIAppCustomErrorMessages.rtiApp_m_errors.entrySet().clear();
			HearingProcess();
			CommonUtilsAPI.rtiAppErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in rti_HearingProcess method");
		}
	}

	//RTI Response after first appeal
	@Test(enabled=false)
	public void rti_ResponseAftrFirstAppeal() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			RTIAppCustomErrorMessages.rtiApp_m_errors.entrySet().clear();
			//			isAcceptedToAPA=true;
			rtiResponseAftrFirstAppeal();
			CommonUtilsAPI.rtiAppErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in rti_ResponseAftrFirstAppeal method");
		}
	}

	//Second appeal from PIO login
	@Test(enabled=false)
	public void rti_secondAppealEntry() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			RTIAppCustomErrorMessages.rtiApp_m_errors.entrySet().clear();
			aprvdFrmPIOFrDisDtls=true;
			secondAppealEntry();
			CommonUtilsAPI.rtiAppErrorMsg.assertAll();
		}catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in rti_secondAppealEntry method");
		}

	} 
	

	// Reset the application form
	@Test(enabled=false)
	public void rti_reset() throws InterruptedException, IOException{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		RTIAppCustomErrorMessages.rtiApp_m_errors.entrySet().clear();
		Reset();
		CommonUtilsAPI.rtiAppErrorMsg.assertAll();
	}

    // To Verify appropriate application status
	@Test(enabled=false)
	public void rti_verAppStatus() throws InterruptedException, IOException{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		RTIAppCustomErrorMessages.rtiApp_m_errors.entrySet().clear();
		rtiVerAppStatus(); 
		CommonUtilsAPI.rtiAppErrorMsg.assertAll();
	}


	//End of @tests

	public void rtiVerAppStatus() throws InterruptedException, IOException{
		mCreateArtefactsFolder("RTI_");
		common.mOpenBrowser("Chrome");
		common.mGetURL(mGetPropertyFromFile("url"));
		common.selectUlb();
		VerAppStatus();
		mCloseBrowser();
	}


	public void rtiApplicationEntry() throws InterruptedException, IOException{

		try{
			mCreateArtefactsFolder("RTI_");
			
			//		beforeRTIApplicationEntry();
			//		chllanForRTIServices = true;
			common.mOpenBrowser(mGetPropertyFromFile("browserName"));
			common.mGetURL(mGetPropertyFromFile("url"));
			common.selectUlb();
			mTakeScreenShot();
			//common.citizenLogin();
			//login(TypeOfExecution,deptUserName,deptPassword)
			common.login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			
			
			
			
			
				RTIApplicationEntry();
			common.logOut();
			common.finalLogOut();
			rtiapplication="true";
			common.isChallanVerftnRequired();
			mCloseBrowser();
			c++;
			System.out.println("c="+c);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in rtiApplicationEntry method"+CurrentinvoCount);
		}
	} 

	public void rtiResponse() throws InterruptedException, IOException{

		try{
			LOIAPPLICABLE=false;
			chllanForRTIServices=false;
			System.out.println("Application Number is : "+appNo);
			mCreateArtefactsFolder("RTI_");
			//		beforeLOIPayment();
			//		beforeRTIResponse();	
			common.mOpenBrowser(mGetPropertyFromFile("browserName"));
			common.mGetURL(mGetPropertyFromFile("url"));
			common.selectUlb();
			common.departmentLogin(mGetPropertyFromFile("PIODeptLogin"),mGetPropertyFromFile("PIODeptPassword"));
			RTIResponse();
			mWaitForVisible("css",mGetPropertyFromFile("viewbuttonid"));
			common.logOut();
			common.finalLogOut();
		
			///swap
			mCloseBrowser();
			common.mOpenBrowser(mGetPropertyFromFile("browserName"));
			common.mGetURL(mGetPropertyFromFile("url"));
			common.selectUlb();
			rtiapplication="false";///swap
			/////
			isLOIPaymentRequired("byBankOrULBAtRes");
			//dispatch commented due to defect
			///swap
			mCloseBrowser();
			common.mOpenBrowser(mGetPropertyFromFile("browserName"));
			common.mGetURL(mGetPropertyFromFile("url"));
			common.selectUlb();
			
			/////

			RTIDispatchDetails();
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in rtiResponse method");
		}
	}

	public void rtiFirstAppealEntry() throws InterruptedException, IOException{

		try{

			
			//		beforeCtznFirstAppeal();
			isFirstAppealRequired("rti_goForFirstAppleal");
			if(goForFirstAppeal)
			{
				mCreateArtefactsFolder("RTI_");
				common.mOpenBrowser(mGetPropertyFromFile("browserName"));
				//common.mOpenBrowser("Chrome");
				common.mGetURL(mGetPropertyFromFile("url"));
				common.selectUlb();
				common.login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
				firstAppealApplication();
				common.logOut();
				common.finalLogOut();
				mCloseBrowser();
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in rtiFirstAppealEntry method");
		}
	}

	public void rtiFirstAppealResponse() throws InterruptedException, IOException{

		try{
			mCreateArtefactsFolder("RTI_");
			//		beforeApaFirstAppeal();
			common.mOpenBrowser("Chrome");
			common.mGetURL(mGetPropertyFromFile("url"));
			common.selectUlb();
			common.departmentLogin(mGetPropertyFromFile("rti_APADeptLogin"),mGetPropertyFromFile("rti_APADeptPwd"));
			firstAppeal();
			common.logOut();
			common.finalLogOut();
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in rtiFirstAppealResponse method");
		}
	}

	public void HearingProcess() throws InterruptedException, IOException
	{
		try{
			mCreateArtefactsFolder("RTI_");
			//		beforeApaFirstAppeal();
			common.mOpenBrowser(mGetPropertyFromFile("browserName"));
			//common.mOpenBrowser("Chrome");
			common.mGetURL(mGetPropertyFromFile("url"));
			common.selectUlb();
			hearingProcess();
			common.logOut();
			common.finalLogOut();
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in HearingProcess method");
		}

	}

	public void rtiResponseAftrFirstAppeal() throws InterruptedException, IOException{

		try{
			mCreateArtefactsFolder("RTI_");
			//		beforeLOIPayment();
			//		beforeRTIResponseAftrFirstApl();


			if((isAcceptedByAPA.get(CurrentinvoCount).toString()).equalsIgnoreCase("true"))
			{
				//common.mOpenBrowser("Chrome");


				LOIAPPLICABLE=false;
				chllanForRTIServices=false;


				common.mOpenBrowser(mGetPropertyFromFile("browserName"));
				common.mGetURL(mGetPropertyFromFile("url"));
				common.selectUlb();
				common.departmentLogin(mGetPropertyFromFile("PIODeptLogin"),mGetPropertyFromFile("PIODeptPassword"));
				RTIResponseAfterApproval();
				common.logOut();
				common.finalLogOut();
				isLOIPaymentRequired("byBankOrULBAtFirstApl");
				RTIDispatchDetails();
				isAcceptedToAPA=false;
				mCloseBrowser();
			}
			else{
				System.out.println("Rejected at Hearing Process hence no response after First Appeal");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in rtiResponseAftrFirstAppeal method");
		}
	}

	public void secondAppealEntry() throws InterruptedException, IOException{

		try{
			LOIAPPLICABLE=false;
			chllanForRTIServices=false;
			mCreateArtefactsFolder("RTI_");		
			common.mOpenBrowser(mGetPropertyFromFile("browserName"));
			common.mGetURL(mGetPropertyFromFile("url"));
			common.selectUlb();
			common.departmentLogin(mGetPropertyFromFile("PIODeptLogin"),mGetPropertyFromFile("PIODeptPassword"));
			secondAppeal();
			common.logOut();
			common.finalLogOut();
			common.departmentLogin(mGetPropertyFromFile("PIODeptLogin"),mGetPropertyFromFile("PIODeptPassword"));
			RTIResponseAfterSecondAppeal();
			common.logOut();
			common.finalLogOut();
			isLOIPaymentRequired("byBankOrULBAtSecApl");
			RTIDispatchDetails();
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in secondAppealEntry method");
		}
	}


	public void Reset() {

		mCreateArtefactsFolder("RTI_");
		//common.mOpenBrowser("Chrome");
		common.mOpenBrowser(mGetPropertyFromFile("browserName"));
		common.mGetURL(mGetPropertyFromFile("url"));
		common.selectUlb();
		common.citizenLogin();
		Reset_Script();
		common.logOut();
		common.finalLogOut();
		mCloseBrowser();
	}	


	// method for RTI Application Entry
	public void RTIApplicationEntry() throws InterruptedException
	{
		try
		{

			//waiting for RTI linkText

			chllanForRTIServices = true;
			//mWaitForVisible("linkText", "RTI_onlserviceLinkid");
			//Navigation from RTI to RTI Application Entry
			mCustomWait(5000);
			
			
     mscroll(0,250);
			
			//mWaitForVisible("linkText", "RTI_onlserviceLinkid");
	//		Online Services
			mNavigation("RTI_onlserviceLinkid", "rti_linkId", "rti_rtiAppLinkId");
		//	mNavigation("rti_linkId", "rti_rtiAppLinkId");
			//calling isOrganization name

			isOrganization(mGetPropertyFromFile("rti_individualOrOrgnData"),mGetPropertyFromFile("rti_organizationName"));

			//selecting title mr. or miss. etc
			mSelectDropDown("id",mGetPropertyFromFile("rti_applicantTitleId"),mGetPropertyFromFile("rti_TitleData"));
			//mGenericWait();

			// printing name
			mSendKeys("id",mGetPropertyFromFile("rti_applicantNameId"),mGetPropertyFromFile("rti_Name"));
			
			
			
			//capturing name 
			String title=mGetText("id", mGetPropertyFromFile("rti_applicantTitleId"));
			
			//first address line
			mSendKeys("id",mGetPropertyFromFile("rti_Address1Id"),mGetPropertyFromFile("rti_Add1"));
			//mGenericWait();

			//second address line
			mSendKeys("id",mGetPropertyFromFile("rti_Address2Id"),mGetPropertyFromFile("rti_Add2"));
			//mGenericWait();
			
			String Applicant_title=mCaptureSelectedValue("id",mGetPropertyFromFile("rti_applicantTitleId"));
			System.out.println("title_id=>"+Applicant_title);
		
			String Applicant_name=mGetText("id",mGetPropertyFromFile("rti_applicantNameId"),"value");
			System.out.println("Applicant_name=>"+Applicant_name);
			
			String RTI_fullname=Applicant_title+Applicant_name;
			System.out.println("RTI_fullname"+RTI_fullname);
			RTI_fullname_list.add(RTI_fullname);
			System.out.println("RTI_fullname_list"+RTI_fullname_list);
			
String RTI_pincode;
			//new pincode 
			if(mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("department") || mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("agency") )			
			{
				mSendKeys("id",mGetPropertyFromFile("rti_pincodeId _department"),mGetPropertyFromFile("rti_pin"));
				RTI_pincode=mGetText("id", mGetPropertyFromFile("rti_pincodeId _department"),"value");
				RTI_pincode_list.add(RTI_pincode);
				System.out.println("RTI_pincode_list->"+RTI_pincode_list);
			}				
			else
			{
				mSendKeys("id",mGetPropertyFromFile("rti_pincodeId_citizen"),mGetPropertyFromFile("rti_pin"));
				RTI_pincode=mGetText("id", mGetPropertyFromFile("rti_pincodeId_citizen"));
				RTI_pincode_list.add(RTI_pincode);
				System.out.println("RTI_pincode_list->"+RTI_pincode_list);
			}


			//checking whether the address is same
			isSameAdd("rti_sameOrDiffAddId","rti_Add","rti_pin");

			//checking whether below poverty line
			isAblBplType("rti_bplYesNoId","rti_bplNum");

			//checking subscription type i.e. email ,sms,or both
			subScription("rti_SubScriptnData");
			/*String subScription_type=mGetPropertyFromFile("rti_SubScriptnData");
			subScription_type_list.add(subScription_type);
			System.out.println("subScription_type_list->"+subScription_type_list);*/
			mTakeScreenShot();

			mscroll(0,350);
			mSendKeys("id", mGetPropertyFromFile("rti_mobileId"),  mGetPropertyFromFile("rti_mobile"));
			
			String mobile=mGetText("id", mGetPropertyFromFile("rti_mobileId"),"value");
			mobile_list.add(mobile);
			System.out.println("mobile_list->"+mobile_list);
			//mGenericWait();
			mSendKeys("name", mGetPropertyFromFile("rti_emailId"),  mGetPropertyFromFile("rti_email"));
			String email=mGetText("name", mGetPropertyFromFile("rti_emailId"),"value");
			email_list.add(email);
			System.out.println("email_list->"+email_list);
			//mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("rti_uidNoId"),  mGetPropertyFromFile("rti_uidNo"));
			//mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("rti_circleId"), mGetPropertyFromFile("rti_circle"));
			String circle=mCaptureSelectedValue("id", mGetPropertyFromFile("rti_circleId"));
			
			circle_list.add(circle);
			
			System.out.println("circle_list->"+circle_list);
			//calling method for delivery i.e. post or office or email
			deliveryMode("rti_deliveryModeId");
			String RTI_deliveryMode=mGetPropertyFromFile("rti_deliveryModeId");
			RTI_deliveryMode_list.add(RTI_deliveryMode);
			System.out.println("RTI_deliveryMode_list->"+RTI_deliveryMode_list);
			//rti subject and details
			mTakeScreenShot();
			mSendKeys("id", mGetPropertyFromFile("rti_rtisubId"),  mGetPropertyFromFile("rti_subject"));
			//mGenericWait();
			String rti_subject=mGetText("id", mGetPropertyFromFile("rti_rtisubId"),"value");
			rti_subject_list.add(rti_subject);
			System.out.println("rti_subject"+rti_subject);
			mSendKeys("id", mGetPropertyFromFile("rti_rtiappdetail"),  mGetPropertyFromFile("rti_detail"));
			//mGenericWait();
			String rti_rtiappdetail=mGetText("id", mGetPropertyFromFile("rti_rtiappdetail"),"value");
			rti_rtiappdetail_list.add(rti_rtiappdetail);
			System.out.println("rti_rtiappdetail_list"+rti_rtiappdetail_list);
			//calling method to upload files 1 optional &1 depending on below poverty line
			rtiUpload();
			mscroll(350,500);
			mTakeScreenShot();

			//03-12-2015
			//Code by Sunil D Sonmale to capture entered applicant name

			entcompname = driver.findElement(By.id("entity.applicantName")).getAttribute("value");

			entcompname = entcompname.replaceAll("\\s", "");

			//selecting media type
			mediaType("rti_typeOfMediaId");
			
			if(mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("department") || mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("agency"))
			{
				mSelectDropDown("id", mGetPropertyFromFile("rti_apptypeId"), mGetPropertyFromFile("rti_apptype"));
				String rti_apptype_form=mCaptureSelectedValue("id", mGetPropertyFromFile("rti_apptypeId"));
				rti_apptype_form_list.add(rti_apptype_form);
				System.out.println("rti_apptype_form_list=>"+rti_apptype_form_list);
				if(mGetPropertyFromFile("rti_apptype").equalsIgnoreCase("Form-E"))
				{

					formE();
					mTakeScreenShot();
					isForme=true;
					
					System.out.println("isForme:::: "+isForme);
					chllanForRTIServices=false;
					System.out.println("chllanForRTIServices:::: "+chllanForRTIServices);
				}else
				{
					paymentForRTIApplication("byBankOrULB");


					///submit & proceed

					//mWaitForVisible("css",mGetPropertyFromFile("ULBSubmitButtonId"));
					//mGenericWait();
					//mClick("css", mGetPropertyFromFile("ULBSubmitButtonId"));

					//taking screenshot & assert to check application is saved or not
					mCustomWait(1000);
					mTakeScreenShot();
					mCustomWait(1000);
				}
			}else
			{
				paymentForRTIApplication("byBankOrULB");
				///submit & proceed

				/*mWaitForVisible("css",mGetPropertyFromFile("ULBSubmitButtonId"));
				mGenericWait();
				mClick("css", mGetPropertyFromFile("ULBSubmitButtonId"));*/

				//taking screenshot & assert to check application is saved or not
				mCustomWait(3000);
				mTakeScreenShot();
				mCustomWait(2000);
			}
			//mGenericWait();
			//selecting payment mode i.e. bank or ulb



			//not to be commented
			/*String SavedMsg = mGetApplicationNo("css",mGetPropertyFromFile("rti_isApplnSavedAssert"));
		String Actmsg = "Your RTI Application is saved successfully.";
		mAssert(SavedMsg, Actmsg, "Actual Message :"+SavedMsg+" Expected Message :"+Actmsg);*/

			/*//proceed button
			mWaitForVisible("id",mGetPropertyFromFile("proceedButtonId"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("proceedButtonId"));
			mGenericWait();

			//					appNo=mGetApplicationNo("css",mGetPropertyFromFile("rti_ApplicationNumId"));
			mAppNoArray("css",mGetPropertyFromFile("rti_ApplicationNumId"));
			System.out.println(appNo);

			//checking whether application number is empty or not
			if(appNo.length()==0)
			{
				System.out.println("Application Number is not created");
			}

			//finish button
			mWaitForVisible("linkText",mGetPropertyFromFile("rti_submitBtnId"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("rti_submitBtnId"));
			mGenericWait();
			///
			 */			
			mGenericWait();
		}
		catch(Exception e)
		{
			
			//e.printStackTrace();
			throw new MainetCustomExceptions("Error in RTIApplicationEntry method");


		}
	}
	//method for    and its challan verification
	public void isLOIPaymentRequired(String bankOrUlb) throws InterruptedException, IOException
	{
		try
		{
			if(LOIAPPLICABLE)
			{
				/*common.citizenLogin();*/
				common.login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
				LoiPayment(bankOrUlb);
				common.logOut();
				common.finalLogOut();

				if(mGetPropertyFromFile("paymentmodeLOIrti").equalsIgnoreCase("offline"))
				{				
					
					if(isAcceptedAftrSecApl)
					{
						
						common.departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
						common.challanVerification(modeForChallan,mGetPropertyFromFile("rtiLOIColId"), mGetPropertyFromFile("chlanTypeOfPayModeAftrSecAprvlId"), mGetPropertyFromFile("chlanNameOfBankAftrSecAprvlId"),mGetPropertyFromFile("rti_chlanVerftnAccNoAftrSecondAprval"),mGetPropertyFromFile("rti_chlanVerftnCheDDNoAftrSecondAprval"),mGetPropertyFromFile("rti_chlanVerftnDateAftrSecondAprval"));
						LOIAPPLICABLE=false;
						isAcceptedAftrSecApl=false;
						common.logOut();
						common.finalLogOut();
					}
					//method for challan verification of LOI Payment
					else if(isAcceptedToAPA)
					{
						System.out.println("Sunil:::::::::::"+mGetPropertyFromFile("rtiLOIColId"));
						common.departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
						common.challanVerification(modeForChallan,mGetPropertyFromFile("rtiLOIColId"), mGetPropertyFromFile("chlanTypeOfPayModeAftrAprvlId"), mGetPropertyFromFile("chlanNameOfBankAftrAprvlId"),mGetPropertyFromFile("rti_chlanVerftnAccNoAftrAprval"),mGetPropertyFromFile("rti_chlanVerftnCheDDNoAftrAprval"),/*mGetPropertyFromFile(*/"rti_chlanVerftnDateAftrAprval"/*)*/);
						
						LOIAPPLICABLE=false;
						isAcceptedToAPA=false;
						common.logOut();
						common.finalLogOut();
					}
					else
					{
						//RTI Response Challan Verification
						common.departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
					
						System.out.println("chlanTypeOfPayModeAftrResId=>"+ mGetPropertyFromFile("chlanTypeOfPayModeAftrResId"));
						//common.challanVerification(modeForChallan,mGetPropertyFromFile("rtiLOIColId"), mGetPropertyFromFile("chlanTypeOfPayModeAftrResId"), mGetPropertyFromFile("chlanNameOfBankAftrResId"),mGetPropertyFromFile("rti_chlanVerftnAccNoAftrRes"),mGetPropertyFromFile("rti_chlanVerftnCheDDNoAftrRes"),/*mGetPropertyFromFile(*/"rti_chlanVerftnDateAftrRes"/*)*/);
					common.challanVerification(modeForChallan, mGetPropertyFromFile("rtiLOIColId"), "chlanTypeOfPayModeAftrResId", "chlanNameOfBankAftrResId", "rti_chlanVerftnAccNoAftrRes", "rti_chlanVerftnCheDDNoAftrRes", "rti_chlanVerftnDateAftrRes");
	 				//	common.challanVerification(modeForChallan,mGetPropertyFromFile("rtiLOIColId"), mGetPropertyFromFile("chlanTypeOfPayModeAftrResId"), mGetPropertyFromFile("chlanNameOfBankAftrResId"),mGetPropertyFromFile("rti_chlanVerftnAccNoAftrRes"),mGetPropertyFromFile("rti_chlanVerftnCheDDNoAftrRes"));
						 
						LOIAPPLICABLE=false;
						common.logOut();
						common.finalLogOut();
					}
				}
				else
				{
					System.out.println("Payment done through other methods");
				}


			}
			else
			{
				System.out.println("LOI Payment is not required");

			}

		}
		catch(Exception e)
		{

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in isLOIPaymentRequired method");
		}
	}

	//method to check whether organization or not
	public void isOrganization(String applicantType,String organizationName) throws InterruptedException
	{
		try
		{
			mCustomWait(3000);
			
			if(applicantType.equalsIgnoreCase("Organization"))
			{	
			mClick("id", mGetPropertyFromFile("rti_OrgnId"));
			RTIApplication_orgind.add("Organization");
			System.out.println("RTIApplication_orgind"+RTIApplication_orgind);
			
			isOrganiztn=true;//swap code for org 03-05-2017
			}
			
			else
			{
			mClick("id", mGetPropertyFromFile("rti_individualId"));	
			RTIApplication_orgind.add("Individual");
			System.out.println("RTIApplication_orgind"+RTIApplication_orgind);
			isOrganiztn=false;//swap code for org 03-05-2017
			}
			
			if((applicantType).equalsIgnoreCase("Organization"))
			{
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("rti_organizationNameId"), (organizationName));
				String orgname=mGetText("id",mGetPropertyFromFile("rti_organizationNameId"),"value");
				System.out.println("orgname==>"+orgname);
				RTIApplication_orgname.add(orgname);
				System.out.println("RTIApplication_orgname"+RTIApplication_orgname);
				isOrganiztn=true;
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in isOrganization method");

		}
	}

	//method to check whether address is same
	public void isSameAdd(String yn,String add,String pincode) throws InterruptedException
	{
		try
		{
			mClick("id", mGetPropertyFromFile(yn));

			if(mGetPropertyFromFile(yn).equals("isCorrespondence2"))
			{
				mGenericWait();
				mSendKeys("id",  mGetPropertyFromFile("rti_newAddId"),  mGetPropertyFromFile("rti_nAdd"));
				mGenericWait();
				mSendKeys("name",  mGetPropertyFromFile("rti_newPinId"),  mGetPropertyFromFile("rti_npin"));
				mGenericWait();
			}

		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in isSameAdd method");
		}

	}

	//method to check subScription type i.e. sms or email or both
	public void subScription(String type) throws InterruptedException
	{
		try
		{
			mGenericWait();
			mClick("id", mGetPropertyFromFile(type));

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in subScription method");
		}

	}

	//method to check whether below poverty line
	public void isAblBplType(String yn,String bplNo) throws InterruptedException
	{
		try{
			if(!isOrganiztn)
			{

				/*if(mGetPropertyFromFile(yn).equals("No"))
				{
					mGenericWait();
					mSelectDropDown("id", mGetPropertyFromFile("rti_aplBplTypeId"), mGetPropertyFromFile("rti_bplNoId"));
				}
				else if(mGetPropertyFromFile(yn).equals("Yes"))
				{
					mGenericWait();
					mSelectDropDown("id", mGetPropertyFromFile("rti_aplBplTypeId"),mGetPropertyFromFile("rti_bplYesId"));
					mGenericWait();
					mSendKeys("id",mGetPropertyFromFile("rti_bplNumId"),  mGetPropertyFromFile("rti_bplNum"));
					mGenericWait();
					aplbpl= true;
				}
				else
				{
					System.out.println("invalid input");

				}*/
				mGenericWait(); 
				mSelectDropDown("id", mGetPropertyFromFile("rti_aplBplTypeId"), mGetPropertyFromFile(yn));
				String RTI_APLBPL_status=mCaptureSelectedValue("id", mGetPropertyFromFile("rti_aplBplTypeId"));
				
				RTI_BPL_num_list.add(RTI_APLBPL_status);
				System.out.println("RTI_BPL_num_list->"+RTI_BPL_num_list);
				mGenericWait();
				if(mGetPropertyFromFile(yn).equals("Yes"))
				{
					mGenericWait();
					mSendKeys("id",mGetPropertyFromFile("rti_bplNumId"),  mGetPropertyFromFile("rti_bplNum"));
					String RTI_BPL_num=mGetText("id", mGetPropertyFromFile("rti_bplNumId"));
					RTI_BPL_num_list.add(RTI_BPL_num);
					System.out.println("RTI_BPL_num_list->"+RTI_BPL_num_list);
					mGenericWait();
					aplbpl= true;
					chllanForRTIServices=false;
				}
				//aplbpl= false;////swap dated 03-05-2017 commented for bpl yament @05052017

			}else {
				RTI_BPL_num_list.add("No");
				aplbpl= false;//swap 03-05-2017
				chllanForRTIServices=true;
				System.out.println("RTI_BPL_num_list====>"+RTI_BPL_num_list);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in isAblBplType method");
		}
	}

	//method for delivery mode i.e.post or collect at office or email
	public void deliveryMode(String mode) throws InterruptedException
	{
		try
		{
			isEmail=false;
			mGenericWait();
			mClick("id", mGetPropertyFromFile("rti_modeOfDeliveryId"));
			mWaitForVisible("id",mGetPropertyFromFile("rti_modeOfDeliveryId"));
			mSelectDropDown("id",mGetPropertyFromFile("rti_modeOfDeliveryId"),mGetPropertyFromFile(mode));
			deliveryMode = mGetPropertyFromFile(mode);

			if(mGetPropertyFromFile(mode).equals("Email"))
			{
				isEmail=true;
				isEmailArray.add("true");
			}
			else{
				isEmailArray.add("false");
			}


		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in deliveryMode method");
		}
	}

	//method to upload optional and mandatory documents depending upon below poverty line
	public void rtiUpload() throws InterruptedException
	{
		try
		{
			mGenericWait();
			mUpload("id",mGetPropertyFromFile("rti_optUpload"),mGetPropertyFromFile("uploadPath"));

			if(aplbpl)
			{
				mGenericWait();
				mUpload("id",mGetPropertyFromFile("rti_manUpload"),mGetPropertyFromFile("uploadPath2"));


				//To display list of files uploaded
				mGenericWait();
				System.out.println("uploaded files are :");
				mGenericWait();
				List<WebElement> allElements1 = driver.findElements(By.id("file_list_1")); 
				for (WebElement element1: allElements1) {
					System.out.println(element1.getText());
				}
			}

			List<WebElement> allElements0 = driver.findElements(By.id("file_list_0")); 
			for (WebElement element0: allElements0) {
				System.out.println(element0.getText());
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in rtiUpload method");
		}
	}

	//method to select mediaType i.e CD/DVD or Paper Hard copy or Email
	public void mediaType(String media) throws InterruptedException
	{
		try
		{
			if(isEmail)
			{
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("rti_mediaTypeId"),mGetPropertyFromFile("rti_mediaEmailId"));
			}
			else
			{
				mSelectDropDown("id",mGetPropertyFromFile("rti_mediaTypeId"), mGetPropertyFromFile(media));
				
			}
		String rti_mediaType=mCaptureSelectedValue("id", mGetPropertyFromFile("rti_mediaTypeId"));
		rti_mediaType_list.add(rti_mediaType);
		System.out.println("rti_mediaType_list=>"+rti_mediaType_list);
		
			
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mediaType method");
		}

	}

	//method for RTI response from after second appeal
	public void RTIResponseAfterSecondAppeal() throws InterruptedException, IOException
	{
		try
		{
			//Navigation from RTI link to Transaction link to RTI Response link
			mNavigation("rti_linkId","rti_PIOTransactionId","rti_PIOResponseLinkId");

			//Search Icon
			mWaitForVisible("css",mGetPropertyFromFile("rti_ResponseSearchIconId"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("rti_ResponseSearchIconId"));
			mGenericWait();

			//entering application number to be searched
			mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("rti_ResponseSearchByNoId"),"71120160000023");

			//			mSendKeys("id", mGetPropertyFromFile("rti_ResponseSearchByNoId"),appNo);
			mCustomWait(8000);
			IndOrDep("id", "rti_ResponseSearchByNoId", "applicationNo");
			mGenericWait();
			mTab("id",mGetPropertyFromFile("rti_ResponseSearchByNoId"));
			mGenericWait();

			mTakeScreenShot();
			//find Icon  
			mWaitForVisible("id",mGetPropertyFromFile("rti_ResponseFindIconId"));
			mClick("id", mGetPropertyFromFile("rti_ResponseFindIconId"));
			mGenericWait();

			mTakeScreenShot();
			//Img Icon
			mWaitForVisible("xpath",mGetPropertyFromFile("rti_ViewImgIconId"));
			mClick("xpath", mGetPropertyFromFile("rti_ViewImgIconId"));
			mGenericWait();

			String RTINoAftrSecAprval = mGetText("xpath",mGetPropertyFromFile("rti_RTINoAftrSecAprval"));
			mAssert(RTINoAftrSecAprval, appNo,"Actual:"+RTINoAftrSecAprval+" Expected:"+appNo+"  "+mGetPropertyFromFile("rti_IsNoMatchesAftrSecAprvalId"));
			mGenericWait();
			mTakeScreenShot();
			mGenericWait();
			mscroll(0,550);
			mTakeScreenShot();

			/*//first
			//Downloading the file to verify it at RTI Response
			if(	driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/table")).size() != 0)
			{
				try{
					WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/table"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");

					//to get text inside document td
					String frstDoc1 = mGetText("xpath","//*[@id=\"content\"]/div/div/table/tbody/tr/td[2]");
					System.out.println(frstDoc1);
					String[] doc = frstDoc1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("No of documents in cell :"+countofDoc);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));

								System.out.println("No of elements in cell :"+Columns.size());
								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(4000);
									mSwitchTabs();
									mCustomWait(4000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}

			//second
			//Downloading the file to verify it at RTI Response
			if(	driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/table")).size() != 0)
			{
				try{
					WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/table[2]"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");

					//to get text inside document td
					String frstDoc1 = mGetText("xpath","//*[@id=\"content\"]/div/div/table[2]/tbody/tr/td[2]");
					System.out.println(frstDoc1);
					String[] doc = frstDoc1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("No of documents in cell :"+countofDoc);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));

								System.out.println("No of elements in cell :"+Columns.size());
								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(4000);
									mSwitchTabs();
									mCustomWait(4000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}

			//third
			//Downloading the file to verify it at RTI Response
			if(	driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/table")).size() != 0)
			{
				try{
					WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/table[3]"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");

					//to get text inside document td
					String frstDoc1 = mGetText("xpath","//*[@id=\"content\"]/div/div/table[3]/tbody/tr/td[2]");
					System.out.println(frstDoc1);
					String[] doc = frstDoc1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("No of documents in cell :"+countofDoc);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));

								System.out.println("No of elements in cell :"+Columns.size());
								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(4000);
									mSwitchTabs();
									mCustomWait(4000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}

			//fourth
			//Downloading the file to verify it at RTI Response
			if(	driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/table")).size() != 0)
			{
				try{
					WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/table[4]"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");

					//to get text inside document td
					String frstDoc1 = mGetText("xpath","//*[@id=\"content\"]/div/div/table[4]/tbody/tr/td[2]");
					System.out.println(frstDoc1);
					String[] doc = frstDoc1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("No of documents in cell :"+countofDoc);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));

								System.out.println("No of elements in cell :"+Columns.size());
								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(4000);
									mSwitchTabs();
									mCustomWait(4000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}

			//fifth
			//Downloading the file to verify it at RTI Response
			if(	driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/table")).size() != 0)
			{
				try{
					WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/table[5]"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");

					//to get text inside document td
					String frstDoc1 = mGetText("xpath","//*[@id=\"content\"]/div/div/table[5]/tbody/tr/td[2]");
					System.out.println(frstDoc1);
					String[] doc = frstDoc1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("No of documents in cell :"+countofDoc);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));

								System.out.println("No of elements in cell :"+Columns.size());
								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(4000);
									mSwitchTabs();
									mCustomWait(4000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
*/
			//selecting Approval id
			mSelectDropDown("id",mGetPropertyFromFile("rti_actionStatusId"),mGetPropertyFromFile("rti_AppApprovedId"));

			//For RTI Dispatch Details
			aprvdFrmPIOFrDisDtls=true;
			isAcceptedAftrSecApl=true;

			// for uploading document
			mUpload("css",mGetPropertyFromFile("rti_OptDocUploadId"),mGetPropertyFromFile("uploadPath"));
			mGenericWait();

			isLOIApplicable("rti_LOIAplNotAplAftrSecAprvlId","rti_PIOResForSkipLOIAftrSecApl","rti_PIOAppRemarkaftsecApl");

		//	isFullyApprovedAftrAprval("rti_AppPartlyOrFulyAprovAftrSecAprvlId");

			mGenericWait();

			mTakeScreenShot();
			mGenericWait();
			//submit button
			mWaitForVisible("xpath",mGetPropertyFromFile("rti_PIOAppSubmitBtnId"));
			mClick("xpath",mGetPropertyFromFile("rti_PIOAppSubmitBtnId"));

			mCustomWait(1000);

			//geting details updated msg
			String RTIResponseSavdMsg = mGetText("xpath",mGetPropertyFromFile("rti_ResponseMsgId"));
			mGenericWait();

			//assert to check RTI Response details are updated.
			mAssert(RTIResponseSavdMsg,"Details Updated Successfully","Actual Message :"+RTIResponseSavdMsg+"Expected Message:"+"Details Updated Successfully");

			//custom wait for proceed button to take screen shot
			mGenericWait();

			//proceed button
			mWaitForVisible("id",mGetPropertyFromFile("proceedButtonId"));
			mCustomWait(1000);
			mTakeScreenShot();
			mClick("id",mGetPropertyFromFile("proceedButtonId"));
			mGenericWait();

			//if LOIdoubt
			if(LOIAPPLICABLE)
			{

				getQuantity("rti_QuantityCountAftrSecApl","rti_ApplicationAmountAftrSecApl");
				mGenericWait();

				mClick("xpath",mGetPropertyFromFile("rtiQuantitySaveBtnId"));
				mCustomWait(8000);
				mTakeScreenShot();
				String txtForLOINo = mGetApplicationNo("css",mGetPropertyFromFile("rti_LOINoId"));
				mGenericWait();
				LOINO= txtForLOINo.replaceAll("[^0-9]", "");
				loiNumber.add(LOINO);

				String delimiter = ":";
				String[] LOIMsg = txtForLOINo.split(delimiter);
				mAssert(LOIMsg[0],"The generated LOI Number is","The Actual Messaage should be:"+"The generated LOI Number is");

				System.out.println(LOINO);
				//checking whether LOI Number is generated ornot 
				if(LOINO.length()==0)
				{
					System.out.println("LOI Number Is not generated after APA app");
				}
				mCustomWait(1000);
				mTakeScreenShot();

				//proceed button
				mWaitForVisible("id",mGetPropertyFromFile("proceedButtonId"));
				mCustomWait(1000);
				mTakeScreenShot();
				mClick("id",mGetPropertyFromFile("proceedButtonId"));
				mGenericWait();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in RTIResponseAfterSecondAppeal method");
		}
	}

	//method for RTI response from PIO login after APA's Approal
	public void RTIResponseAfterApproval() throws InterruptedException, IOException
	{
		try
		{

			//Navigation from RTI Link to Transaction Link to RTI Response link
			System.err.println("in rti response aftera approval");

			//mNavigation(mGetPropertyFromFile("rti_linkId"), mGetPropertyFromFile("rti_PIOTransactionId"), mGetPropertyFromFile("rti_PIOResponseLinkId"));
			mNavigation("rti_linkId","rti_PIOTransactionId","rti_PIOResponseLinkId");


			//Search Icon
			mWaitForVisible("css",mGetPropertyFromFile("rti_ResponseSearchIconId"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("rti_ResponseSearchIconId"));
			mGenericWait();

			//entering application number to be searched
			mCustomWait(9000);




			//mSendKeys("id", mGetPropertyFromFile("rti_ResponseSearchByNoId"),appNo);
			IndOrDep("id", "rti_ResponseSearchByNoId", "applicationNo");
			mGenericWait();
			mTab("id",mGetPropertyFromFile("rti_ResponseSearchByNoId"));
			mGenericWait();

			mTakeScreenShot();
			mWaitForVisible("id",mGetPropertyFromFile("rti_ResponseFindIconId"));
			mClick("id", mGetPropertyFromFile("rti_ResponseFindIconId"));
			mGenericWait();

			mTakeScreenShot();
			//Img Icon
			mWaitForVisible("xpath",mGetPropertyFromFile("rti_ViewImgIconId"));
			mClick("xpath", mGetPropertyFromFile("rti_ViewImgIconId"));
			mGenericWait();

			String RTINoAtAftrAprval = mGetText("xpath",mGetPropertyFromFile("rti_RTINoAtAftrAprvalAssertid"));
			mAssert(RTINoAtAftrAprval, appNo, "RTI Number does not matches at Response after APA Approval"+"Actual Number :"+RTINoAtAftrAprval+" Expected Number :"+appNo);
			mGenericWait();
			mTakeScreenShot();
			mGenericWait();
			mscroll(0,450);
			mTakeScreenShot();
			mGenericWait();

			//Downloading the file to verify it at RTI Response
			if(	driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/table")).size() != 0)
			{
				try{
					WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/table"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");

					//to get text inside document td
					String frstDoc1 = mGetText("xpath","//*[@id=\"content\"]/div/div/table/tbody/tr/td[2]");
					System.out.println(frstDoc1);
					String[] doc = frstDoc1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("No of documents in cell :"+countofDoc);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));

								System.out.println("No of elements in cell :"+Columns.size());
								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(4000);
									mSwitchTabs();
									mCustomWait(4000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}

			//second
			//Downloading the file to verify it at RTI Response
			if(	driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/table[2]")).size() != 0)
			{
				try{
					WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/table[2]"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");

					//to get text inside document td
					String frstDoc1 = mGetText("xpath","//*[@id=\"content\"]/div/div/table[2]/tbody/tr/td[2]");
					System.out.println(frstDoc1);
					String[] doc = frstDoc1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("No of documents in cell :"+countofDoc);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));

								System.out.println("No of elements in cell :"+Columns.size());
								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(4000);
									mSwitchTabs();
									mCustomWait(4000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}

			//third
			//Downloading the file to verify it at RTI Response
			if(	driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/table[3]")).size() != 0)
			{
				try{
					WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/table[3]"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");

					//to get text inside document td
					String frstDoc1 = mGetText("xpath","//*[@id=\"content\"]/div/div/table[3]/tbody/tr/td[2]");
					System.out.println(frstDoc1);
					String[] doc = frstDoc1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("No of documents in cell :"+countofDoc);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));

								System.out.println("No of elements in cell :"+Columns.size());
								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(4000);
									mSwitchTabs();
									mCustomWait(4000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}

			//fourth
			//Downloading the file to verify it at RTI Response
			if(	driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/table[4]")).size() != 0)
			{
				try{
					WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/table[4]"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");

					//to get text inside document td
					String frstDoc1 = mGetText("xpath","//*[@id=\"content\"]/div/div/table[4]/tbody/tr/td[2]");
					System.out.println(frstDoc1);
					String[] doc = frstDoc1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("No of documents in cell :"+countofDoc);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));

								System.out.println("No of elements in cell :"+Columns.size());
								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(4000);
									mSwitchTabs();
									mCustomWait(4000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}

			//fifth
			//Downloading the file to verify it at RTI Response
			if(	driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/table[5]")).size() != 0)
			{
				try{
					WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/table[5]"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");

					//to get text inside document td
					String frstDoc1 = mGetText("xpath","//*[@id=\"content\"]/div/div/table[5]/tbody/tr/td[2]");
					System.out.println(frstDoc1);
					String[] doc = frstDoc1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("No of documents in cell :"+countofDoc);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));

								System.out.println("No of elements in cell :"+Columns.size());
								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(4000);
									mSwitchTabs();
									mCustomWait(4000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}

			//selecting Approval id
			mSelectDropDown("id",mGetPropertyFromFile("rti_actionStatusId"),mGetPropertyFromFile("rti_AppApprovedId"));

			//For RTI Dispatch Details
			aprvdFrmPIOFrDisDtls=true;

			//for uploading document
			mUpload("css",mGetPropertyFromFile("rti_OptDocUploadId"),mGetPropertyFromFile("uploadPath"));
			mGenericWait();

			isLOIApplicable("rti_LOIAplNotAplAftrAprvlId","rti_ReasonForSkipaftraprval","rti_AppRemarkaftraprval");
			mGenericWait();

			isFullyApprovedAftrAprval("rti_AppPartlyOrFulyAprovAftrAprvlId");

			mGenericWait();
			mTakeScreenShot();
			mGenericWait();
			//submit button
			mWaitForVisible("css",mGetPropertyFromFile("rti_secondAppealsubmitBtnId"));
			mClick("css",mGetPropertyFromFile("rti_secondAppealsubmitBtnId"));
			
			
	//		rti_secondAppealsubmitBtnId
			mCustomWait(1000);

			//geting details updated msg
			String RTIResponseSavdMsg = mGetText("xpath",mGetPropertyFromFile("rti_ResponseMsgId"));
			mGenericWait();

			//assert to check RTI Response details are updated.
			mAssert(RTIResponseSavdMsg,"Details Updated Successfully","Actual Message: "+RTIResponseSavdMsg+" Expected Message :"+"Details Updated Successfully");

			//custom wait for proceed button to take screen shot
			mGenericWait();

			//proceed button
			mWaitForVisible("id",mGetPropertyFromFile("proceedButtonId"));
			mCustomWait(1000);
			mTakeScreenShot();
			mClick("id",mGetPropertyFromFile("proceedButtonId"));
			mGenericWait();

			//if LOIdoubt
			if(LOIAPPLICABLE)
			{
				getQuantity("rti_QuantityCountAftrAprval","rti_ApplicationAmountAftrAprval");
				mGenericWait();

				mClick("xpath",mGetPropertyFromFile("rtiQuantitySaveBtnId"));
				mCustomWait(10000);
				mTakeScreenShot();
				String txtForLOINo = mGetApplicationNo("css",mGetPropertyFromFile("rti_LOINoId"));
				mGenericWait();
				LOINO= txtForLOINo.replaceAll("[^0-9]", "");
				loiNumber.add(LOINO);

				String delimiter = ":";
				String[] LOIMsg = txtForLOINo.split(delimiter);
				mAssert(LOIMsg[0],"The generated LOI Number is","Actual Messaage :"+LOIMsg[0]+" Expected Message :"+"The generated LOI Number is");

				System.out.println(LOINO);
				//checking whether LOI Number is generated ornot 
				if(LOINO.length()==0)
				{
					System.out.println("LOI Number Is not generated after APA approval");
				}

				mCustomWait(1000);
				mTakeScreenShot();
				//proceed button
				mWaitForVisible("id",mGetPropertyFromFile("proceedButtonId"));
				mCustomWait(1000);
				mTakeScreenShot();
				mClick("id",mGetPropertyFromFile("proceedButtonId"));				
				mCustomWait(5000);
			}
		}catch(Exception e)
		{

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in RTIResponseAfterApproval method");
		}
	}

	//method for RTI response from PIO login
	public void RTIResponse() throws InterruptedException, IOException
	{
		try
		{
			mCustomWait(2000);
			//Navigation from RTI Link to Transaction Link to RTI Response link
			mNavigation("rti_linkId", "rti_PIOTransactionId","rti_PIOResponseLinkId");
			mCustomWait(5000);
			//Search Icon
			
			mWaitForVisible("css",mGetPropertyFromFile("viewbuttonid"));
			mWaitForVisible("css",mGetPropertyFromFile("rti_ResponseSearchIconId"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("rti_ResponseSearchIconId"));
			mGenericWait();

			//entering application number to be searched
			mCustomWait(8000);

			//mSendKeys("id", mGetPropertyFromFile("rti_ResponseSearchByNoId"),appNo);
			IndOrDep("id", "rti_ResponseSearchByNoId","applicationNo");
			//mSendKeys("id", mGetPropertyFromFile("rti_ResponseSearchByNoId"),"71120160000329");



			mGenericWait();
			mTab("id",mGetPropertyFromFile("rti_ResponseSearchByNoId"));
			mCustomWait(5000);
			//find Icon  
			mWaitForVisible("id",mGetPropertyFromFile("rti_ResponseFindIconId"));
			mClick("id", mGetPropertyFromFile("rti_ResponseFindIconId"));
			mGenericWait();
			mTakeScreenShot();

			//Img Icon
			mWaitForVisible("xpath",mGetPropertyFromFile("rti_ViewImgIconId"));
			mClick("xpath", mGetPropertyFromFile("rti_ViewImgIconId"));
			mTakeScreenShot();
			//mGenericWait();
			mscroll(0,550);
			mGenericWait();
			mTakeScreenShot();
			//mGenericWait();
	
			String capturedex = null;
			
		//	String InvoCountexecution_index = String.valueOf(InvoCount);
		//	RTI_Application_list.add(InvoCountexecution_index);
	
			
			String currentexecution_index = String.valueOf(CurrentinvoCount);
			RTI_Application_list.add(currentexecution_index);
			int ii=2 ;
			int Row_count = driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/div[2]/table/tbody/tr")).size();
			System.out.println("rows===>"+Row_count);
			for(ii = 2;ii<=Row_count;)
			{
				
				
				String s="//*[@id=\"content\"]/div/div/div[2]/table/tbody/";
				String d="tr[";
				String e="]/td[2]";
				String f="]/td[1]";
				String outputtable=s+d+ii+e;
				String outputtablef=s+d+ii+f;
				     
				try
				{
					String output_1o=driver.findElement(By.xpath(outputtablef)).getText();
					String output_o=driver.findElement(By.xpath(outputtable)).getText();
					/*String output_1o=mGetText("xpath",outputtablef);
					String output_o=mGetText("xpath",outputtable);*/
					System.out.println(output_1o+"====>"+output_o);
					RTI_Application_list.add(output_o);
					
					switch (output_1o) {
					case "RTI Application No..":
						RTIResponse_RTIapplicationno.add(output_o);
						System.out.println("RTIResponse_RTIapplicationno==>"+RTIResponse_RTIapplicationno);
						break;
					case "Date":
						RTIResponse_Date.add(output_o);
						System.out.println("RTIResponse_Date==>"+RTIResponse_Date);
						break;
					case "Type of Applicant":
						RTIResponse_TypeofApplicant.add(output_o);
						System.out.println("RTIResponse_TypeofApplicant==>"+RTIResponse_TypeofApplicant);
						break;
					case "Applicant Name":
						RTIResponse_ApplicantName.add(output_o);
						System.out.println("RTIResponse_ApplicantName==>"+RTIResponse_ApplicantName);
						break;
		
					case "Mobile No.":
						RTIResponse_MobileNo.add(output_o);
						System.out.println("RTIResponse_MobileNo==>"+RTIResponse_MobileNo);
						break;
					

					case "Email ID":
						RTIResponse_Email.add(output_o);
						System.out.println("RTIResponse_Email==>"+RTIResponse_Email);
						break;
						
					case "Alert Subscription":
						RTIResponse_AlertS.add(output_o);
						System.out.println("RTIResponse_AlertS==>"+RTIResponse_AlertS);
						break;
					case "Is the Applicant below Poverty Line":
						RTIResponse_bpl.add(output_o);
						System.out.println("RTIResponse_bpl==>"+RTIResponse_bpl);
						break;
					
					case "Address Details":
						RTIResponse_Adddet.add(output_o);
						System.out.println("RTIResponse_Adddet==>"+RTIResponse_Adddet);
						break;
					
					
					case "Pincode":
						RTIResponse_Pincode.add(output_o);
						System.out.println("RTIResponse_Pincode==>"+RTIResponse_Pincode);
						break;
					
					/*case "Date":
						RTIResponse_Adddet1.add(output_o);
						System.out.println("RTIResponse_Adddet1==>"+RTIResponse_Adddet1);
						break;
						
					case "Date":
						RTIResponse_Pincode1.add(output_o);
						System.out.println("RTIResponse_Pincode1==>"+RTIResponse_Pincode1);
						break;
						*/
					case "Subject":
						RTIResponse_Subject.add(output_o);
						System.out.println("RTIResponse_Subject==>"+RTIResponse_Subject);
						break;
						
					case "Particular information required (in brief)":
						RTIResponse_partiinfo.add(output_o);
						System.out.println("RTIResponse_partiinfo==>"+RTIResponse_partiinfo);
						break;
					case "Media for Receiving Information":
						RTIResponse_mediatype.add(output_o);
						System.out.println("RTIResponse_mediatype==>"+RTIResponse_mediatype);
						break;
						
					case "Circle":
						RTIResponse_Circle.add(output_o);
						System.out.println("RTIResponse_Circle==>"+RTIResponse_Circle);
						break;
						
						
					default:
						break;
					}
		 			
				throw new Exception(); 
				//throw new java.lang.Exceptions;
				}
				catch(Exception ex)
				{
					capturedex =ex.toString();
					System.out.println("===================================================================");
					
				}
		
				ii=ii+1;
				
			}
			
			RTI_Application_list.add(currentexecution_index);
		//	RTI_Application_list.add(InvoCountexecution_index);
			
		responseassertion();
			System.out.println("RTI_Application_list===>"+RTI_Application_list);
			
			
			mTakeScreenShot();
			
			
			//RTItableread();
			//Downloading the file to verify it at RTI Response
			if(	driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/table")).size() != 0)
			{
				try
				{
					WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/table"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");

					//to get text inside document td
					String frstDoc1 = mGetText("xpath","//*[@id=\"content\"]/div/div/table/tbody/tr/td[2]");
					System.out.println(frstDoc1);
					String[] doc = frstDoc1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("No of documents in cell :"+countofDoc);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));

								System.out.println("No of elements in cell :"+Columns.size());
								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mGenericWait();
									mCustomWait(2000);
									mDownloadFile(myClassName);
									mCustomWait(2500);
									mSwitchTabs();
									mCustomWait(2500);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}
				
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			String RTI_response_status=mGetPropertyFromFile("rti_AprvRejectOrFrwdId");	
			RTI_response_status_list.add(RTI_response_status);
			isApproved("rti_AprvRejectOrFrwdId");
			mGenericWait();
			
			}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in RTIResponse method");
		}
	}

	//method for hearing process
	public void hearingProcess() throws InterruptedException, IOException
	{

		try
		{
		//APADeptLogin = 9470488744
		//PIODeptPassword = Pass@123
			mGenericWait();
			common.departmentLogin(mGetPropertyFromFile("rti_APADeptLogin"),mGetPropertyFromFile("rti_APADeptPwd"));

			//Navigation from RTI Link to Transaction link to Hearing process link
			mNavigation("rti_linkId","rti_PIOTransactionId","rti_HearingProcessLinkId");
			mGenericWait();

			//sending RTI application number to search application
			mGenericWait();
			//mSendKeys("id",  mGetPropertyFromFile("rti_HAPASearchApppByRTINoId"),"71120160000183");
			//mSendKeys("id",  mGetPropertyFromFile("rti_HAPASearchApppByRTINoId"),appNo);
			IndOrDep("id", "rti_HAPASearchApppByRTINoId", "applicationNo");
			//mTab("id",mGetPropertyFromFile("rti_HAPASearchApppByRTINoId"));
			mGenericWait();

			//submit button
			mGenericWait();
			mWaitForVisible("css", mGetPropertyFromFile("rti_HAPAFAppSubBtnId"));
			mClick("css", mGetPropertyFromFile("rti_HAPAFAppSubBtnId"));
			mGenericWait();

			//clicking on img to get application
			mWaitForVisible("xpath", mGetPropertyFromFile("rti_HAPAViewImgBtnIId"));
			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("rti_HAPAViewImgBtnIId"));
			mGenericWait();
/////////////////////////////////////
			
			
			int jjj=2 ;
			int Row_count = driver.findElements(By.xpath("//*[@id=\"command\"]/table/tbody/tr")).size();
			System.out.println("rows===>"+Row_count);
			for(jjj = 2;jjj<=Row_count;jjj++)
			{


			String s="//*[@id=\"command\"]/table/tbody/";
			String d="tr[";
			String e="]/td[2]";
			String f="]/td[1]";
			String outputtable=s+d+jjj+e;
			String outputtablef=s+d+jjj+f;

			try
			{
				String output_1o=driver.findElement(By.xpath(outputtablef)).getText();
				String output_o=driver.findElement(By.xpath(outputtable)).getText();
			/*String output_1o=mGetText("xpath",outputtablef);
			String output_o=mGetText("xpath",outputtable);*/
			System.out.println(output_1o+"====>"+output_o);
			RTI_Application_list.add(output_o);
			System.out.println("RTI_Application_list==>"+RTI_Application_list);
			switch (output_1o) {
			case "RTI Application No..":
			RTIhearing_RTIapplicationno.add(output_o);
			System.out.println("RTIhearing_RTIapplicationno==>"+RTIhearing_RTIapplicationno);
			break;
			case "Date":
			RTIhearing_Date.add(output_o);
			System.out.println("RTIhearing_Date==>"+RTIhearing_Date);
			break;
			case "Type of Applicant":
			RTIhearing_TypeofApplicant.add(output_o);
			System.out.println("RTIhearing_TypeofApplicant==>"+RTIhearing_TypeofApplicant);
			break;
			case "Applicant Name":
			RTIhearing_ApplicantName.add(output_o);
			System.out.println("RTIhearing_ApplicantName==>"+RTIhearing_ApplicantName);
			break;

			case "Mobile No.":
			RTIhearing_MobileNo.add(output_o);
			System.out.println("RTIhearing_MobileNo==>"+RTIhearing_MobileNo);
			break;


			case "Email ID":
			RTIhearing_Email.add(output_o);
			System.out.println("RTIhearing_Email==>"+RTIhearing_Email);
			break;

			case "Alert Subscription":
			RTIhearing_AlertS.add(output_o);
			System.out.println("RTIhearing_AlertS==>"+RTIhearing_AlertS);
			break;
			case "Is the Applicant below Poverty Line":
			RTIhearing_bpl.add(output_o);
			System.out.println("RTIhearing_bpl==>"+RTIhearing_bpl);
			break;

			case "Address Details":
			RTIhearing_Adddet.add(output_o);
			System.out.println("RTIhearing_Adddet==>"+RTIhearing_Adddet);
			break;


			case "Pincode":
			RTIhearing_Pincode.add(output_o);
			System.out.println("RTIhearing_Pincode==>"+RTIhearing_Pincode);
			break;

			/*case "Date":
			RTIResponse_Adddet1.add(output_o);
			System.out.println("RTIResponse_Adddet1==>"+RTIResponse_Adddet1);
			break;

			case "Date":
			RTIResponse_Pincode1.add(output_o);
			System.out.println("RTIResponse_Pincode1==>"+RTIResponse_Pincode1);
			break;
			*/
			case "Subject":
			RTIhearing_Subject.add(output_o);
			System.out.println("RTIhearing_Subject==>"+RTIhearing_Subject);
			break;

			case "Particular information required (in brief)":
			RTIhearing_partiinfo.add(output_o);
			System.out.println("RTIhearing_partiinfo==>"+RTIhearing_partiinfo);
			break;
			case "Media for Receiving Information":
			RTIhearing_mediatype.add(output_o);
			System.out.println("RTIhearing_mediatype==>"+RTIhearing_mediatype);
			break;

			case "Circle":
			RTIhearing_Circle.add(output_o);
			System.out.println("RTIhearing_Circle==>"+RTIhearing_Circle);
			break;


			default:
			break;
			}




			throw new Exception(); 
			//throw new java.lang.Exceptions;
			}
			catch(Exception ex)
			{

			System.out.println("===================================================================");

			}



			}
			System.out.println("RTI_Application_list==>"+RTI_Application_list);
			System.out.println("RTI_Application_list==>"+RTI_Application_list.size());
			hearingassertion();
			//RTI_Application_list.add(InvoCountexecution_index);


			///////////////////////////////////////


			/*String RTINoAtHearing = mGetText("xpath", mGetPropertyFromFile("rti_RTINoAtHearing"));
			mAssert(RTINoAtHearing, appNo,mGetPropertyFromFile("rti_IsRTINoMatchesAtHearing")+" Actual Number :"+RTINoAtHearing+" Expected Number :"+appNo);

*/			
			mTakeScreenShot();
			mscroll(0,650);
			mGenericWait();
			mTakeScreenShot();

			//first download
			//Downloading the file to verify it at Hearing Process
			if(	driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/div/div/table")).size() != 0)
			{
				try
				{
					WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div/div/table"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");

					//to get text inside document td
					String frstDoc1 = mGetText("xpath","//*[@id=\"content\"]/div/div/div/div/table/tbody/tr/td[2]");
					System.out.println(frstDoc1);
					String[] doc = frstDoc1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("No of documents in cell :"+countofDoc);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));

								System.out.println("No of elements in cell :"+Columns.size());
								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(4000);
									mSwitchTabs();
									mCustomWait(4000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}

			//second download
			//Downloading the file to verify it at Hearing Process
			if(	driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/div/div/table[2]")).size() != 0)
			{
				try
				{
					WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div/div/table[2]"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");

					//to get text inside document td
					String frstDoc1 = mGetText("xpath","//*[@id=\"content\"]/div/div/div/div/table[2]/tbody/tr[2]/td[2]");
					System.out.println(frstDoc1);
					String[] doc = frstDoc1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("No of documents in cell :"+countofDoc);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));

								System.out.println("No of elements in cell :"+Columns.size());
								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(4000);
									mSwitchTabs();
									mCustomWait(4000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}

			//third download
			//Downloading the file to verify it at Hearing Process
			if(	driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/div/div/table[3]")).size() != 0)
			{
				try
				{
					WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div/div/table[3]"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");

					//to get text inside document td
					String frstDoc1 = mGetText("xpath","//*[@id=\"content\"]/div/div/div/div/table[3]/tbody/tr/td[2]");
					System.out.println(frstDoc1);
					String[] doc = frstDoc1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("No of documents in cell :"+countofDoc);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));

								System.out.println("No of elements in cell :"+Columns.size());
								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(4000);
									mSwitchTabs();
									mCustomWait(4000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}

			//	isApproved("rti_AppApprovedId");
			//	mGenericWait();
			// calling method for the decision of APA
			mGenericWait();

			appealDecision("rti_hearStatus");

			mGenericWait();
			mSendKeys("id",  mGetPropertyFromFile("rti_remarkForHearingId"),mGetPropertyFromFile("rti_remarkForHearing"));
			mTab("id",mGetPropertyFromFile("rti_remarkForHearingId"));

			mGenericWait();
			//uploading documents
			mUpload("css",mGetPropertyFromFile("rti_HAPAUploadDocId"),mGetPropertyFromFile("uploadPath2"));

			mGenericWait();
			mSendKeys("id",  mGetPropertyFromFile("rti_WitnessNameId"),mGetPropertyFromFile("rti_WitnessName"));

			// calling method for forwarding application

			sendTo();

			// submit button
			mWaitForVisible("css", mGetPropertyFromFile("rti_HAPAAppSubBtnId"));
			mClick("css", mGetPropertyFromFile("rti_HAPAAppSubBtnId"));

			mCustomWait(1000);
			mTakeScreenShot();
			String SavdMsg = mGetText("xpath",mGetPropertyFromFile("rti_HAPAAppSubBtnAssertMsg"));
			mAssert(SavdMsg,"Details Updated Successfully","Actual message :"+SavdMsg+" Expected Message :"+"Details Updated Successfully");
			mGenericWait();

			//proceed button
			mWaitForVisible("id", mGetPropertyFromFile("rti_HAPAPrcodBtnId"));
			mClick("id", mGetPropertyFromFile("rti_HAPAPrcodBtnId"));

			mGenericWait();
		}
		catch(Exception e)
		{

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in hearingProcess method");
		}

	}
	// method for sending application to pio or applicant
	public void sendTo() throws InterruptedException
	{
		try
		{
			if((isAcceptedByAPA.get(CurrentinvoCount).toString()).equalsIgnoreCase("true"))
			{
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("rti_HAPAhearingStatusesId"),mGetPropertyFromFile("rti_HAPAhearinsntToPIO"));
				mGenericWait();

				mSendKeys("id",  mGetPropertyFromFile("rti_HAPAAppealauthremarkId"),mGetPropertyFromFile("rti_HAPAAppealauthremark"));
				mGenericWait();
			}
			else
			{
				mGenericWait(); 
				mSelectDropDown("id", mGetPropertyFromFile("rti_HAPAhearingStatusesId"),mGetPropertyFromFile("rti_HAPAhearinsntToApplcnt"));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in sendTo method");
		}
	}

	// method for the decision of APA after hearing
	public void appealDecision(String isAcceptedAfterHearing)throws InterruptedException
	{
		try
		{
			mGenericWait();
			mSelectDropDown("id",mGetPropertyFromFile("rti_hearingStatusId"),mGetPropertyFromFile(isAcceptedAfterHearing));
			if(mGetPropertyFromFile(isAcceptedAfterHearing).equals("Accept"))
			{
				mGenericWait();
				//	mSelectDropDown("id", mGetPropertyFromFile("rti_hearingStatusId"),mGetPropertyFromFile("rti_hearStatusAccept"));
				isAcceptedToAPA=true;

				isAcceptedByAPA.add("true");
				RTIfirstappeallist_hearStatus_list.add("Accept");
			}
			else if(mGetPropertyFromFile(isAcceptedAfterHearing).equals("Reject"))
			{
				mGenericWait();
				//mSelectDropDown("id", mGetPropertyFromFile("rti_hearingStatusId"),mGetPropertyFromFile("rti_hearStatusReject"));
				isAcceptedByAPA.add("false");
				RTIfirstappeallist_hearStatus_list.add("Reject");
			}
			else
			{
				System.out.println("please enter valid approval");
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in appealDecision method");
		}
	}

	//method for first appeal entry from citizen
	public  void  firstAppealApplication() throws InterruptedException, IOException 
	{
		try
		{
			mGenericWait();
			mCustomWait(2000);
			//Navigation from RTI Link to First Appeal Entry
			mNavigation("rti_linkId", "rti_FirstAppealEntryLinkId");
			//mNavigation(mGetPropertyFromFile("rti_linkId"), mGetPropertyFromFile("rti_FirstAppealEntryLinkId"));
			mGenericWait();
			//First Appeal Entry
			mWaitForVisible("css",mGetPropertyFromFile("rti_FirstAppealViewFormId"));
			mGenericWait();
			//icon to find application
			mWaitForVisible("css",mGetPropertyFromFile("rti_AppSearchIconId"));
			mClick("css", mGetPropertyFromFile("rti_AppSearchIconId"));
			mGenericWait();
			mCustomWait(5000);
			//mSendKeys("id",mGetPropertyFromFile("rti_FrstAplEntryFindByNoId"),"71120160000530");

			//			mSendKeys("id",mGetPropertyFromFile("rti_FrstAplEntryFindByNoId"),appNo);
			IndOrDep("id", "rti_FrstAplEntryFindByNoId", "applicationNo");
			mTab("id",mGetPropertyFromFile("rti_FrstAplEntryFindByNoId"));
			mGenericWait();
			System.out.println(appNo);
			//find application icon
			mWaitForVisible("id",mGetPropertyFromFile("rti_FindApplIconId"));
			mGenericWait();
			mClick("id",mGetPropertyFromFile("rti_FindApplIconId"));
		String rowstat = RTI_response_status_list.get(CurrentinvoCount).toString();
			//System.out.println("rti status==>"+rowstat);
			
			//img view icon
			mWaitForVisible("xpath",mGetPropertyFromFile("rti_ViewImgIconId"));
			mGenericWait();
			mClick("xpath",mGetPropertyFromFile("rti_ViewImgIconId"));	
			mGenericWait();
			mCustomWait(1000);
			mTakeScreenShot();


			
			
			
			
			
			
			
			//verifyFirstApp();
		//	String firstAplRTINO = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealNoAssertId"));
			mscroll(0,350);
			//assert to check RTI Response of the same number
			//mAssert(firstAplRTINO, applicationNo.get(CurrentinvoCount).toString(), "Actual Number :"+firstAplRTINO+" Expected Number :"+applicationNo.get(CurrentinvoCount).toString());
			mGenericWait();
			if(driver.findElements(By.xpath("//*[@id='RTIFirstAppeal']/table[2]")).size() != 0)
			{			
				try{

					//Downloading the file to verify it at RTI Application
					WebElement table3 = driver.findElement(By.xpath("//*[@id='RTIFirstAppeal']/table[2]"));
					List<WebElement> rows3 = table3.findElements(By.tagName("tr"));

					int rwcount3 =rows3.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount3);
					System.out.println("hello");
					//to get text inside document td
					String frstDocs3 = mGetText("xpath","//*[@id='RTIFirstAppeal']/table[2]/tbody/tr/td[2]");
					System.out.println(frstDocs3);
					String[] doc3 = frstDocs3.split(".pdf");
					int countofDoc3 = doc3.length;
					System.out.println("no of cdocument in cell :"+countofDoc3);
					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows3)
					{
						List<WebElement> cols3=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols3)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns3 = cols3.get(1).findElements(By.tagName("a"));
								System.out.println("No of elements in cell :"+Columns3.size());

								for(int i=0;i<countofDoc3;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc3[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(5000);
									mSwitchTabs();
									mCustomWait(5000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}

			//downloading files
			if(driver.findElements(By.xpath("//*[@id='RTIFirstAppeal']/table[3]")).size() != 0)
			{
				try{
					//Downloading the file to verify it at RTI Application
					WebElement table = driver.findElement(By.xpath("//*[@id='RTIFirstAppeal']/table[3]"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));

					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");

					//to get text inside document td
					String frstDocs1 = mGetText("xpath","//*[@id='RTIFirstAppeal']/table[3]/tbody/tr/td[2]");
					System.out.println(frstDocs1);
					String[] doc = frstDocs1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("no of cdocument in cell :"+countofDoc);
					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));
								System.out.println("No of elements in cell :"+Columns.size());

								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(5000);
									mSwitchTabs();
									mCustomWait(5000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}

			//Appeal button
			//mWaitForVisible("xpath",mGetPropertyFromFile("rti_FirstAppealBtnId"));
			mCustomWait(1000);

			mGenericWait();
			mClick("xpath",mGetPropertyFromFile("rti_FirstAppealBtnId"));
			mGenericWait();
			mCustomWait(1000);
			mTakeScreenShot();
			
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("rti_FirstAppealDetailsId"),mGetPropertyFromFile("rti_firstAppealDetails"));
         mCustomWait(2000);
			//mGenericWait();
			mUpload("id",mGetPropertyFromFile("rti_FirstAppealUploadId"),mGetPropertyFromFile("uploadPath"));		
			mCustomWait(2500);

			// checking whether accepted or not
			//isAccept("rti_FirstAppealIsAcceptedId");
			mClick("id",mGetPropertyFromFile("rti_FirstAppealIsAcceptedId"));

			/////////////////////////////////////////////////////////////////////
			System.out.println("===============table assertioncaptured====================firstAppealApplication");
			String capturedex = null;
		/*String response_Pincode = mGetText("xpath",mGetPropertyFromFile("RTI_response_Pincode"));
		System.out.println("RTI_response_Pincode==>"+response_Pincode);*/
			int length=RTI_Application_list.size();
			//all fields on first appeal list 
			String firstappealcurrentexecution_index = String.valueOf(CurrentinvoCount);
			RTI_firstappeal_Application_list.add(firstappealcurrentexecution_index);
		int jj=2 ;
		int First_Row_count = driver.findElements(By.xpath("//*[@id=\"RTIFirstAppeal\"]/table/tbody/tr")).size();
		System.out.println("rows===>"+First_Row_count);
		System.out.println("rowstat=>"+rowstat);
		//for(jj = 2;jj<=First_Row_count;)
		for(jj = 2;jj<=First_Row_count;jj++)	
		{
			
		if(rowstat.equalsIgnoreCase("Rejected"))
		{
			
					System.out.println("===============Rejected=============");
					String s="//*[@id=\"RTIFirstAppeal\"]/table[1]/tbody/";
					String d="tr[";
					String e="]/td[2]";
					String f="]/td[1]";
					String outputtable=s+d+jj+e;
					System.out.println("outputtable=>"+outputtable);
					String outputtablef=s+d+jj+f;
					System.out.println("outputtablef=>"+outputtablef);
					try
					{
						
						String output_1o=driver.findElement(By.xpath(outputtablef)).getText();
					 String output_o=driver.findElement(By.xpath(outputtable)).getText();
						//String output_1o=mGetText("xpath",outputtablef);
						//String output_o=mGetText("xpath",outputtable);
						System.out.println(output_1o+"====>"+output_o);
						RTI_firstappeal_Application_list.add(output_o);
						System.out.println("=======================");
						switch (output_1o) {
						case "RTI Application No.":
							RTIfirstappeal_RTIapplicationno.add(output_o);
							System.out.println("RTIfirstappeal_RTIapplicationno==>"+RTIfirstappeal_RTIapplicationno);
							break;
						case "Date":
							RTIfirstappeal_Date.add(output_o);
							System.out.println("RTIfirstappeal_Date==>"+RTIfirstappeal_Date);
							break;
						case "Type of Applicant":
							RTIfirstappeal_TypeofApplicant.add(output_o);
							System.out.println("RTIfirstappeal_TypeofApplicant==>"+RTIfirstappeal_TypeofApplicant);
							break;
						case "Applicant Name":
							RTIfirstappeal_ApplicantName.add(output_o);
							System.out.println("RTIfirstappeal_ApplicantName==>"+RTIfirstappeal_ApplicantName);
							break;
			
						case "Mobile No.":
							RTIfirstappeal_MobileNo.add(output_o);
							System.out.println("RTIfirstappeal_MobileNo==>"+RTIfirstappeal_MobileNo);
							break;
						

						case "Email ID":
							RTIfirstappeal_Email.add(output_o);
							System.out.println("RTIfirstappeal_Email==>"+RTIfirstappeal_Email);
							break;
							
						case "Alert Subscription":
							RTIfirstappeal_AlertS.add(output_o);
							System.out.println("RTIfirstappeal_AlertS==>"+RTIfirstappeal_AlertS);
							break;
						case "Is the Applicant below Poverty Line":
							RTIfirstappeal_bpl.add(output_o);
							System.out.println("RTIfirstappeal_bpl==>"+RTIfirstappeal_bpl);
							break;
						
						case "Address Details":
							RTIfirstappeal_Adddet.add(output_o);
							System.out.println("RTIfirstappeal_Adddet==>"+RTIfirstappeal_Adddet);
							break;
						
						
						case "Pincode":
							RTIfirstappeal_Pincode.add(output_o);
							System.out.println("RTIfirstappeal_Pincode==>"+RTIfirstappeal_Pincode);
							break;
						
						/*case "Date":
							RTIfirstappeal_Adddet1.add(output_o);
							System.out.println("RTIResponse_Adddet1==>"+RTIResponse_Adddet1);
							break;
							
						case "Date":
							RTIfirstappeal_Pincode1.add(output_o);
							System.out.println("RTIResponse_Pincode1==>"+RTIResponse_Pincode1);
							break;
							*/
						case "Subject":
							RTIfirstappeal_Subject.add(output_o);
							System.out.println("RTIfirstappeal_Subject==>"+RTIfirstappeal_Subject);
							break;
							
						case "Particular information required (in brief)":
							RTIfirstappeal_partiinfo.add(output_o);
							System.out.println("RTIfirstappeal_partiinfo==>"+RTIfirstappeal_partiinfo);
							break;
						case "Media for Receiving Information":
							RTIfirstappeal_mediatype.add(output_o);
							System.out.println("RTIfirstappeal_mediatype==>"+RTIfirstappeal_mediatype);
							break;
							
						case "Circle":
							RTIfirstappeal_Circle.add(output_o);
							System.out.println("RTIfirstappeal_Circle==>"+RTIfirstappeal_Circle);
							break;
							
							
						default:
							break;
						}
							
						
								
					throw new Exception(); 
					//throw new java.lang.Exceptions;
					}
					catch(Exception ex)
					{
						capturedex =ex.toString();
						System.out.println("===================================================================");
						
					}
			
		}else {
System.out.println("===============Approved=============");
			
			String s="//*[@id=\"RTIFirstAppeal\"]/table/tbody/";
			String d="tr[";
			String e="]/td[2]";
			String f="]/td[1]";
			String outputtable=s+d+jj+e;
			System.out.println("outputtable=>"+outputtable);
			String outputtablef=s+d+jj+f;
			System.out.println("outputtablef=>"+outputtablef);
			try
			{
				String output_1o=driver.findElement(By.xpath(outputtablef)).getText();
				 String output_o=driver.findElement(By.xpath(outputtable)).getText();
				/*String output_1o=mGetText("xpath",outputtablef);
				String output_o=mGetText("xpath",outputtable);*/
				System.out.println(output_1o+"====>"+output_o);
				RTI_firstappeal_Application_list.add(output_o);
				switch (output_1o) {
				case "RTI Application No.":
					RTIfirstappeal_RTIapplicationno.add(output_o);
					System.out.println("RTIfirstappeal_RTIapplicationno==>"+RTIfirstappeal_RTIapplicationno);
					break;
				case "Date":
					RTIfirstappeal_Date.add(output_o);
					System.out.println("RTIfirstappeal_Date==>"+RTIfirstappeal_Date);
					break;
				case "Type of Applicant":
					RTIfirstappeal_TypeofApplicant.add(output_o);
					System.out.println("RTIfirstappeal_TypeofApplicant==>"+RTIfirstappeal_TypeofApplicant);
					break;
				case "Applicant Name":
					RTIfirstappeal_ApplicantName.add(output_o);
					System.out.println("RTIfirstappeal_ApplicantName==>"+RTIfirstappeal_ApplicantName);
					break;
	
				case "Mobile No.":
					RTIfirstappeal_MobileNo.add(output_o);
					System.out.println("RTIfirstappeal_MobileNo==>"+RTIfirstappeal_MobileNo);
					break;
				

				case "Email ID":
					RTIfirstappeal_Email.add(output_o);
					System.out.println("RTIfirstappeal_Email==>"+RTIfirstappeal_Email);
					break;
					
				case "Alert Subscription":
					RTIfirstappeal_AlertS.add(output_o);
					System.out.println("RTIfirstappeal_AlertS==>"+RTIfirstappeal_AlertS);
					break;
				case "Is the Applicant below Poverty Line":
					RTIfirstappeal_bpl.add(output_o);
					System.out.println("RTIfirstappeal_bpl==>"+RTIfirstappeal_bpl);
					break;
				
				case "Address Details":
					RTIfirstappeal_Adddet.add(output_o);
					System.out.println("RTIfirstappeal_Adddet==>"+RTIfirstappeal_Adddet);
					break;
				
				
				case "Pincode":
					RTIfirstappeal_Pincode.add(output_o);
					System.out.println("RTIfirstappeal_Pincode==>"+RTIfirstappeal_Pincode);
					break;
				
				/*case "Date":
					RTIfirstappeal_Adddet1.add(output_o);
					System.out.println("RTIResponse_Adddet1==>"+RTIResponse_Adddet1);
					break;
					
				case "Date":
					RTIfirstappeal_Pincode1.add(output_o);
					System.out.println("RTIResponse_Pincode1==>"+RTIResponse_Pincode1);
					break;
					*/
				case "Subject":
					RTIfirstappeal_Subject.add(output_o);
					System.out.println("RTIfirstappeal_Subject==>"+RTIfirstappeal_Subject);
					break;
					
				case "Particular information required (in brief)":
					RTIfirstappeal_partiinfo.add(output_o);
					System.out.println("RTIfirstappeal_partiinfo==>"+RTIfirstappeal_partiinfo);
					break;
				case "Media for Receiving Information":
					RTIfirstappeal_mediatype.add(output_o);
					System.out.println("RTIfirstappeal_mediatype==>"+RTIfirstappeal_mediatype);
					break;
					
				case "Circle":
					RTIfirstappeal_Circle.add(output_o);
					System.out.println("RTIfirstappeal_Circle==>"+RTIfirstappeal_Circle);
					break;
					
					
				default:
					break;
				}
						
			throw new Exception(); 
			//throw new java.lang.Exceptions;
			}
			catch(Exception ex)
			{
				capturedex =ex.toString();
				System.out.println("===================================================================");
				
			}
		
		}
			
			
			
		
			
			
			
			
			
			
		}
		mAssert(RTIResponse_RTIapplicationno, RTIfirstappeal_RTIapplicationno, "is not matching with response form field ");
		firstappealassertion();


			
			//submit button
			mGenericWait();
			mClick("xpath",mGetPropertyFromFile("rti_FirstAppealSubBtnId"));
			mGenericWait();

			mTakeScreenShot();
			mCustomWait(1000);
			String firstAplSavdMsg = mGetApplicationNo("xpath",mGetPropertyFromFile("rti_FirstAppealMsgAssertId"));
			mGenericWait();
			mAssert(firstAplSavdMsg,"Appeal Details Saved Successfully","Actual message :"+firstAplSavdMsg+" Expected Message :"+"Appeal Details Saved Successfully");

			//Proceed button
			mWaitForVisible("id",mGetPropertyFromFile("rti_FirstAppealProcdBtnId"));
			mGenericWait();
			mClick("id",mGetPropertyFromFile("rti_FirstAppealProcdBtnId"));
			mGenericWait();
			mGenericWait();

			String FirstaplAckMsg= mGetText("xpath",mGetPropertyFromFile("rti_firstAppealAckMsg"));
			String delimiter = "[0-9] ";
			String[] firstAplAckMsg = FirstaplAckMsg.split(delimiter);

			System.out.println(firstAplAckMsg[1]);
			mAssert(firstAplAckMsg[1],"has been filed successfully.","Actual Message :"+firstAplAckMsg[1]+" Expected Message :"+"has been filed successfully.");
			// generation of appeal number
			String applicationNoForFirstAppeal = mGetApplicationNo("css",mGetPropertyFromFile("rti_AppealNoGetTextId"));
			//just changed
			System.out.println(applicationNoForFirstAppeal);
			applicationNoForFirstAppeal_list.add(applicationNoForFirstAppeal);
			if(applicationNoForFirstAppeal.length()==0)
			{
				System.out.println("LOI Number is not generated");
			}

			mWaitForVisible("css",mGetPropertyFromFile("rti_AcknowledgementBtnId"));
			mGenericWait();
			mClick("css",mGetPropertyFromFile("rti_AcknowledgementBtnId"));
			mCustomWait(3000);
			//not to be commented
			mChallanPDFReader();
			mGenericWait();
			//mSwitchTabs();
 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in firstAppealApplication method");
		}
	}
//method to check accepted or not
	public void isAccept(String accept) throws InterruptedException
	{
		try
		{			
			mGenericWait();
			mClick("id",mGetPropertyFromFile(accept));

			if(mGetPropertyFromFile(accept).equals("acceptOrDecline1"))
			{
				mGenericWait();
				//mClick("id",mGetPropertyFromFile("rti_FirstAppealAcceptedId"));
				firstAppealFromCitizen=true;
			}
			else if(mGetPropertyFromFile(accept).equals("acceptOrDecline2"))
			{
				mGenericWait();
				//mClick("id",mGetPropertyFromFile("rti_FirstAppealNotAcceptedId"));
			}
			else
			{
				System.out.println("please select yes or no for acception");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in isAccept method");
		}
	}

	//method for LOI 
	public  void  LoiPayment(String mode)throws InterruptedException, IOException {

		try
		{
			modeForChallan=mode;
			mGenericWait();
			//Navigation from Payment link to RTI LOI Payment to
			//mNavigation("rti_paymentLinkId", "rti_RtiLoiPaymentLinkId");
			mscroll(294, 671);
			mNavigation("RTI_onlserviceLinkid","RTI_PaymentLinkid", "RTI_loiPaymentid");
			//sending LOI number
			mGenericWait();

			mSendKeys("name", mGetPropertyFromFile("rti_FindAppByLOINoId"),LOINO);

			//mSendKeys("id", mGetPropertyFromFile("rti_FindAppByLOINoId"),RTIServices.LOINO);
			//mSendKeys("id", mGetPropertyFromFile("rti_FindAppByLOINoId"),/*loiNumber.get(CurrentinvoCount)*/LOINO);

		//	mTab("id",mGetPropertyFromFile("rti_FindAppByLOINoId"));
			mGenericWait();

			//searching after sending number  
			mClick("css",mGetPropertyFromFile("rti_LOISearchId"));
			mGenericWait();
			mTakeScreenShot();
			
			
			String loigridsxpath="//*[@id=\"gridLoiPayment\"]/tbody/tr";
			
			
			
			int rowCount=driver.findElements(By.xpath(loigridsxpath)).size();
			
			for (int i = 2; i <=rowCount; i++) {
	 			
				String loix="//*[@id=\"gridLoiPayment\"]/tbody/tr["+i+"]/td[4]";
				String ano=driver.findElement(By.xpath(loix)).getText();
				String loiview="//*[@id=\"gridLoiPayment\"]/tbody/tr["+i+"]/td[5]/form/a/img";
				
				System.out.println("ano=>"+ano);
				System.out.println("applicationNo.get(CurrentinvoCount)==>"+applicationNo.get(CurrentinvoCount));
				System.out.println("loiview=>"+loiview);
				if (ano.equalsIgnoreCase(applicationNo.get(CurrentinvoCount))) {
					
					driver.findElement(By.xpath(loiview)).click();
					break;
					
				}
				
			}
			
			
			
			
			
			
			
			// calling payment method i.e. bank or ulb

			
			/*if(mGetPropertyFromFile("paymentMode"))*/
			common.payment("paymentmodeLOIrti",mode);


			if(mGetPropertyFromFile("paymentmodeLOIrti").equalsIgnoreCase("Pay @ ULB Counter"))
			{

				//mClick("css", "input[type='submit'][value='Submit']");
				mClick("css", mGetPropertyFromFile("rti_loipaymentsubmits"));
				mWaitForVisible("id", "btnNo");
				mGenericWait();
				mClick("id", "btnNo");
			}
			else{
				//mWaitForVisible("css", "input[type='submit'][value='Submit']");
				
				//mClick("css", "input[type='submit'][value='Submit']");
				mClick("css", mGetPropertyFromFile("rti_loipaymentsubmits"));
				//mWaitForVisible("id", "btnNo");
				mWaitForVisible("css", mGetPropertyFromFile("rti_loipaymentproceed"));
				mGenericWait();
				mClick("css", mGetPropertyFromFile("rti_loipaymentproceed"));
			}
			
			//new code
			//Switching tab
			mChallanPDFReader();
api.PdfPatterns.patternChallanPdf(output);

			//mSwitchTabs();
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in rti LoiPayment method");
		}
	}
	// method to check whether application is approved ,rejected or forwarded to officer
	public void isApproved(String action) throws InterruptedException
	{
		try
		{
			mGenericWait();
			mSelectDropDown("id",mGetPropertyFromFile("rti_actionStatusId"),mGetPropertyFromFile(action));
			if(mGetPropertyFromFile(action).equals("Approved"))
			{
				aprvdFrmPIOFrDisDtls=true;
				//mGenericWait();
				RTIResponse_action_application.add("Approved");
				System.out.println("RTIResponse_action_application"+RTIResponse_action_application);
				// for uploading document
				mUpload("css",mGetPropertyFromFile("rti_OptDocUploadId"),mGetPropertyFromFile("uploadPath"));
				mGenericWait();

				// checking whether LOI is applicable or not
				isLOIApplicable("rti_LOIAplNotAplId","rti_ReasonForSkip","rti_AppRemark");
				isFullyApproved("rti_AppPartlyOrFulyAprovId");
				mGenericWait();
				mTakeScreenShot();

				//submit button
				mWaitForVisible("xpath",mGetPropertyFromFile("rti_PIOAppSubmitBtnId"));
				mClick("xpath",mGetPropertyFromFile("rti_PIOAppSubmitBtnId"));
				mCustomWait(1000);

				//geting details updated msg
				String RTIResponseSavdMsg = mGetText("xpath",mGetPropertyFromFile("rti_ResponseMsgId"));
				mGenericWait();

				//assert to check RTI Response details are updated.
				mAssert(RTIResponseSavdMsg,"Details Updated Successfully","Actual Message :"+RTIResponseSavdMsg+" Expected Message :"+"Details Updated Successfully");

				//proceed button
				mWaitForVisible("id",mGetPropertyFromFile("proceedButtonId"));
				mCustomWait(1000);
				mTakeScreenShot();
				mClick("id",mGetPropertyFromFile("proceedButtonId"));
				mGenericWait();

				//if LOI doubt
				if(LOIAPPLICABLE)
				{
					getQuantity("rti_QuantityCount","rti_ApplicationAmount");
					mGenericWait();
					mClick("xpath",mGetPropertyFromFile("rtiQuantitySaveBtnId"));
					mCustomWait(1000);
					String txtForLOINo = mGetApplicationNo("css",mGetPropertyFromFile("rti_LOINoId"));
					String delimiter = ":";
					String[] LOIMsg = txtForLOINo.split(delimiter);
					mAssert(LOIMsg[0],"The generated LOI Number is","Actual Messaage :"+LOIMsg[0]+" Expected Message :"+"The generated LOI Number is");
					LOINO= txtForLOINo.replaceAll("[^0-9]", "");
					loiNumber.add(LOINO);
					System.out.println(loiNumber);

					//checking whether LOI Number is generated ornot 
					if(LOINO.length()==0)
					{
						System.out.println("LOI Number Is not generated");
					}
					mCustomWait(1000);
					mTakeScreenShot();

					//proceed button
					mWaitForVisible("id",mGetPropertyFromFile("proceedButtonId"));
					mCustomWait(1000);
					mTakeScreenShot();
					mClick("id",mGetPropertyFromFile("proceedButtonId"));
					mGenericWait();
					
					//////loi print reprint 
					//PRINTREPRINT();
					
				}

				//declaring accepted or rejected for first appeal entry
				isAcceptedOrRejected=true;
			}
			else if(mGetPropertyFromFile(action).equals("Forward To Relevant Officer"))
			{
				mGenericWait();
				frwdToRelvntOfficer=true;
				RTIResponse_action_application.add("Forward To Relevant Officer");
				System.out.println("forward to relevent officer"+frwdToRelvntOfficer);
				forwardToOfficer("rti_frwdPIOName","rti_frwdPIODept", "rti_frwdPIOMobNo","rti_frwdPIOEmail","rti_frwdPIOAdd");
				mGenericWait();

				mTakeScreenShot();
				//submit button
				mWaitForVisible("xpath",mGetPropertyFromFile("rti_PIOResponseSubBtnId"));
				mGenericWait();
				mClick("xpath", mGetPropertyFromFile("rti_PIOResponseSubBtnId"));
				mCustomWait(1000);

				//geting details updated msg
				String RTIResponseSavdMsg = mGetText("xpath",mGetPropertyFromFile("rti_ResponseMsgId"));
				mGenericWait();
				//assert to check RTI Response details are updated.
				mAssert(RTIResponseSavdMsg,"Details Updated Successfully","Actual Message :"+RTIResponseSavdMsg+" Expected Message :"+"Details Updated Successfully");
				mGenericWait();
				//proceed button
				mWaitForVisible("id",mGetPropertyFromFile("proceedButtonId"));
				mCustomWait(1000);
				mTakeScreenShot();
				mClick("id",mGetPropertyFromFile("proceedButtonId"));
				mGenericWait();

			}
			else if(mGetPropertyFromFile(action).equals("Rejected"))
			{
				aprvdFrmPIOFrDisDtls=false;
				RTIResponse_action_application.add("Rejected");
				System.out.println("RTIResponse_action_application"+RTIResponse_action_application);
				mGenericWait();
				rejectedAtPIO=true;
				causeOfRejection("rti_RejectionCause","rti_AppStatus8","rti_AppStatus9");
				mGenericWait();

				mTakeScreenShot();
				//submit button
				mWaitForVisible("xpath",mGetPropertyFromFile("rti_PIOResponseSubBtnId"));
				mGenericWait();
				mClick("xpath", mGetPropertyFromFile("rti_PIOResponseSubBtnId"));
				mCustomWait(1000);

				//geting details updated msg
				String RTIResponseSavdMsg = mGetText("xpath",mGetPropertyFromFile("rti_ResponseMsgId"));
				mGenericWait();
				//assert to check RTI Response details are updated.
				mAssert(RTIResponseSavdMsg,"Details Updated Successfully","Actual Message :"+RTIResponseSavdMsg+" Expected Message :"+"Details Updated Successfully");
				mGenericWait();

				//proceed button
				mWaitForVisible("id",mGetPropertyFromFile("proceedButtonId"));
				mCustomWait(1000);
				mTakeScreenShot();
				mClick("id",mGetPropertyFromFile("proceedButtonId"));
				mGenericWait();
				//declaring accepted or rejected for first appeal entry
				isAcceptedOrRejected=true;
			}
			else
			{
				mTakeScreenShot();
				System.out.println("invalid :should be Approved ,rejected or forward to relevent officer");
				mGenericWait();
			}

		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in isApproved method");
		}
	}

	// method to forward rti application to respective office
	public void forwardToOfficer(String officerName,String department,String mobileNo,String email,String add) throws InterruptedException
	{
		try
		{
			mGenericWait();

			mSendKeys("id", mGetPropertyFromFile("rti_frwdPIONameId"),mGetPropertyFromFile(officerName));
			mGenericWait();

			mSendKeys("id", mGetPropertyFromFile("rti_frwdPIODeptId"),mGetPropertyFromFile(department));
			mGenericWait();

			mSendKeys("id", mGetPropertyFromFile("rti_frwdPIOMobNoId"),mGetPropertyFromFile(mobileNo));
			mGenericWait();

			mSendKeys("id", mGetPropertyFromFile("rti_frwdPIOEmailId"),mGetPropertyFromFile(email));
			mGenericWait();

			mSendKeys("id", mGetPropertyFromFile("rti_frwdPIOAddId"),mGetPropertyFromFile(add));
			mGenericWait();

			mGenericWait();
			mTab("id",mGetPropertyFromFile("rti_frwdPIOAddId"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in forwardToOfficer method");
		}
	}

	// method for approval i.e. fully or partially
	public void isFullyApproved(String yn) throws InterruptedException
	{
		try{
			mSelectDropDown("id", mGetPropertyFromFile("rti_AppApprovalTypeId"),mGetPropertyFromFile(yn));
			RTIResponse_fullpartial.add(mGetPropertyFromFile(yn));
			System.out.println("RTIResponse_fullpartial"+RTIResponse_fullpartial);
			if(mGetPropertyFromFile(yn).equals("Partially"))
			{
				
				mGenericWait();
				partiallyApproved("rti_PartlyApprovDoc","rti_PartlyApprovRes");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in isFullyApproved method");
		}
	}


	// method for approval after APA Approval i.e. fully or partially
	public void isFullyApprovedAftrAprval(String yn) throws InterruptedException
	{
		try{
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("rti_AppApprovalTypeId"),mGetPropertyFromFile(yn));

			if(mGetPropertyFromFile(yn).equals("Partially"))
			{
				mGenericWait();
				partiallyApproved("rti_PartlyApprovDocAftrAprval","rti_PartlyApprovResAftrAprval");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in isFullyApprovedAftrAprval method");

		}
	}

	//method for documents in partially approved application
	//dynamic method
	public void partiallyApproved(String document,String Reason) throws InterruptedException
	{
		try
		{
			mGenericWait();
			mSendKeys("id",  mGetPropertyFromFile("rti_PartlyApprovDocId"),  mGetPropertyFromFile(document));

			mGenericWait();
			mSendKeys("id",  mGetPropertyFromFile("rti_PartlyApprovResId"), mGetPropertyFromFile(Reason));
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in partiallyApproved method");
		}

	}

	// method to get quantity in case of LOI applicable
	public void getQuantity(String count,String amount) throws InterruptedException
	{
		try
		{
			String mediatypeatloi=driver.findElement(By.xpath("//*[@id=\"command\"]/div[3]/div")).getText();
			System.out.println("captured text after response is ==>"+mediatypeatloi);
			String []a=mediatypeatloi.split("\n");
			/*for (int i = 0; i < a.length; i++) {
				System.out.println("a["+i+"]==>"+a[i]);
			}*/
			Thread.sleep(1000);
		//	if(isEmailArray.get(CurrentinvoCount).equalsIgnoreCase("true"))
			
			if(a[1].equalsIgnoreCase("email"))
			{
				//sending amount required for providing information
				mWaitForVisible("id", mGetPropertyFromFile("rti_ApplictnAmountId"));

				mClick("id", mGetPropertyFromFile("rti_ApplictnAmountId"));
				mSendKeys("id", mGetPropertyFromFile("rti_ApplictnAmountId"), mGetPropertyFromFile(amount));
			}
			else
			{
				//sending number of quantity required
			//	mWaitForVisible("id", mGetPropertyFromFile("rti_NumberOfQuantityId"));

				mClick("id", mGetPropertyFromFile("rti_NumberOfQuantityId"));
				mSendKeys("id", mGetPropertyFromFile("rti_NumberOfQuantityId"), mGetPropertyFromFile(count));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in getQuantity method");
		}
	}

	// method to check whether LOI is applicable or not
	public void isLOIApplicable(String yn,String reasonForNonLOI,String remarks) throws InterruptedException
	{
		try
		{
			mSelectDropDown("id",mGetPropertyFromFile("rti_isLOIApplicableId"),mGetPropertyFromFile(yn));
			mGenericWait();
			if(mGetPropertyFromFile(yn).equals("LOI not applicable"))
			{
				mGenericWait();
				mSendKeys("id",mGetPropertyFromFile("rti_ReasonForSkipId"),mGetPropertyFromFile(reasonForNonLOI));
				mGenericWait();
			}
			else if(mGetPropertyFromFile(yn).equals("LOI Applicable"))
			{
				mGenericWait();
				LOIAPPLICABLE=true;
				mGenericWait();
			}
			else
			{
				System.out.println("invalid data for loi applicable");
			}
			mGenericWait();
			mSendKeys("id",mGetPropertyFromFile("rti_AppRemarkId"), mGetPropertyFromFile(remarks));
			mGenericWait();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in isLOIApplicable method");

		}
	}


	// method for cause of rejection
	public void causeOfRejection(String cause,String subSection8,String subSection9) throws InterruptedException
	{
		try
		{
			mGenericWait();
			mSendKeys("id",mGetPropertyFromFile("rti_RejectionCauseId"), mGetPropertyFromFile(cause));

			mGenericWait();
			mSendKeys("id",mGetPropertyFromFile("rti_AppStatus8Id"), mGetPropertyFromFile(subSection8));

			mGenericWait();
			mSendKeys("id",mGetPropertyFromFile("rti_AppStatus9Id"), mGetPropertyFromFile(subSection9));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in causeOfRejection method");
		}
	}

	//RTI payment method to select offline payment mode i.e. bank or ulb
	public void paymentForRTIApplication(String mode) throws InterruptedException
	{
		modeForChallan=mode;
		chllanForRTIServices = true;
		try
		{
			if(isOrganiztn)
			{
				//calling payment mode for organization
				common.payment("paymentMode",mode);
				mGenericWait();
				mTakeScreenShot();

				//	mGenericWait();
				mTakeScreenShot();
				//mWaitForVisible("css",mGetPropertyFromFile("ULBSubmitButtonId"));
				mWaitForVisible("css",mGetPropertyFromFile("bankSubmitButtonId"));
				mGenericWait();
				mscroll(766, 704);
				mClick("css", mGetPropertyFromFile("bankSubmitButtonId"));

				//mClick("css", mGetPropertyFromFile("ULBSubmitButtonId"));
				mGenericWait();					

				mTakeScreenShot();
				String SavedMsg = mGetApplicationNo("css",mGetPropertyFromFile("rti_isApplnSavedAssert"));
				String Actmsg = "Your RTI Application is saved successfully.";
				mAssert(SavedMsg, Actmsg, "Actual Message :"+SavedMsg+"  Expected Message :"+Actmsg);

				// proceed button
				mWaitForVisible("id",mGetPropertyFromFile("proceedButtonId"));
				mGenericWait();
				mClick("id", mGetPropertyFromFile("proceedButtonId"));
 
				mGenericWait();
				mWaitForVisible("css",mGetPropertyFromFile("rti_ApplicationNumId"));
				//				appNo=mGetApplicationNo("css",mGetPropertyFromFile("rti_ApplicationNumId"));
				mAppNoArray("css",mGetPropertyFromFile("rti_ApplicationNumId"));
				System.out.println("application no is "+appNo);
				//mAssert("5555555555",appNo,"Application number does not match, browser: 5555555555 || PDF: "+appNo);

				if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
				{
					mTakeScreenShot();
					//print challan button
					mWaitForVisible("css",mGetPropertyFromFile("rti_printChallanBtnId"));
					mGenericWait();
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("rti_printChallanBtnId"));
					mCustomWait(6000);
					common.newChallanReader();
					rtiapplication="true";
					//aplbpl=false;/////swap dated 03-05-2017
					
				}
				else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
				{
					//print challan button
					mWaitForVisible("css",mGetPropertyFromFile("rti_printChallanBtnId"));
					mTakeScreenShot();
					mGenericWait();
					mClick("css", mGetPropertyFromFile("rti_printChallanBtnId"));
					mTakeScreenShot();
					mCustomWait(6000);
					mChallanPDFReader();
					
				}

				// not to be commented	
				//mAssert(appNo,printedAppNo,"Application number does not match"+"Actual Number :"+appNo+"  Expected Number :"+printedAppNo);
				//mAssert(ApplicantName,applcntName,"Application name does not match"+" Actual Name :"+ApplicantName+"  Expected Name :"+applcntName);
				//	mAssert("ABMCEfg",applcntName,"Application number does not match, browser: ABMCEfg || PDF: "+applcntName);
				mCustomWait(5000);

				//switching window after printing challan
				//mSwitchTabs();

				//finish button
				mWaitForVisible("linkText",mGetPropertyFromFile("rti_finishTxtId"));
				mGenericWait();
				mClick("linkText", mGetPropertyFromFile("rti_finishTxtId"));
				mGenericWait();
				isOrganiztn=false;
			}
			else
			{
				if(aplbpl)
				{
					isOrganiztn=false;					
					
					System.out.println("no payment required");
					mGenericWait();
					mTakeScreenShot();
					mGenericWait();

					// submit button
					mWaitForVisible("css",mGetPropertyFromFile("ULBSubmitButtonId"));
					mGenericWait();
					mClick("css", mGetPropertyFromFile("ULBSubmitButtonId"));

					//taking screenshot & assert to check application is saved or not
					mCustomWait(3000);
					mTakeScreenShot();
					mCustomWait(2000);
					//not to be commented
					/*String SavedMsg = mGetApplicationNo("css",mGetPropertyFromFile("rti_isApplnSavedAssert"));
					String Actmsg = "Your RTI Application is saved successfully.";
					mAssert(SavedMsg, Actmsg, "Actual Message :"+SavedMsg+" Expected Message :"+Actmsg);*/

					//proceed button
					mWaitForVisible("id",mGetPropertyFromFile("proceedButtonId"));
					mGenericWait();
					mClick("id", mGetPropertyFromFile("proceedButtonId"));
					mGenericWait();
					mTakeScreenShot();

					//					appNo=mGetApplicationNo("css",mGetPropertyFromFile("rti_ApplicationNumId"));
					mAppNoArray("css",mGetPropertyFromFile("rti_ApplicationNumId"));
					System.out.println(appNo);

					//checking whether application number is empty or not
					if(appNo.length()==0)
					{
						System.out.println("Application Number is not created");
					}

					//finish button
					mWaitForVisible("css",mGetPropertyFromFile("rti_finishBtnId"));
					mGenericWait();
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("rti_finishBtnId"));
					mGenericWait();
					//aplbpl=false;
				}
				else
				{
					mGenericWait();
					common.payment("paymentMode",mode);
					// storing application number
					mGenericWait();
					mTakeScreenShot();
					mGenericWait();

					//Clicking on submit
					// submit button
					mWaitForVisible("css",mGetPropertyFromFile("ULBSubmitButtonId"));
					mGenericWait();
					mClick("css", mGetPropertyFromFile("ULBSubmitButtonId"));

					//Click proceed
					//proceed button
					mWaitForVisible("id",mGetPropertyFromFile("proceedButtonId"));
					mGenericWait();
					mClick("id", mGetPropertyFromFile("proceedButtonId"));
					mGenericWait();

					mTakeScreenShot();

					//					appNo=mGetApplicationNo("css",mGetPropertyFromFile("rti_ApplicationNumId"));
					mAppNoArray("css",mGetPropertyFromFile("rti_ApplicationNumId"));
					System.out.println("application no is"+appNo);
					mGenericWait();

					//clicking on print challan
					mWaitForVisible("css",mGetPropertyFromFile("rti_printChallanBtnId"));
					mGenericWait();
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("rti_printChallanBtnId"));
					mCustomWait(6000);
					if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
					{
						common.newChallanReader();
					}
					else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
					{
						mChallanPDFReader();
						
					}

					/*//print challan button
					mWaitForVisible("css",mGetPropertyFromFile("rti_printChallanBtnId"));
					mGenericWait();
					mClick("css", mGetPropertyFromFile("rti_printChallanBtnId"));
					mCustomWait(4000);
					//not to be commented

					mChallanPDFReader(appNo, ApplicantName);*/
					//mAssert(appNo,printedAppNo,"Application number does not match"+"Actual Number:"+appNo+" Expected Number :"+printedAppNo);
					mGenericWait();
					//mAssert(ApplicantName,applcntName,"Application name does not match"+"Actual Name :"+ApplicantName+"  Expected Name :"+applcntName);
					mCustomWait(2000);
					//switching window after printing challan
					/*mSwitchTabs();*/

					//finish button
					mWaitForVisible("linkText",mGetPropertyFromFile("rti_finishTxtId"));
					mGenericWait();
					mClick("linkText", mGetPropertyFromFile("rti_finishTxtId"));
					mGenericWait();
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in paymentForRTIApplication method");
		}
	}


	//method for first appeal accepted or not from APA


	public void firstAppeal() throws InterruptedException, IOException{
		try
		{
			//Navigation from RTI Link to Transaction Link to First Appeal Link
			//	mNavigation("rti_linkId", mGetPropertyFromFile("rti_APATransactionLinkId"), mGetPropertyFromFile("rti_APAFirstAppLstLinkId"));
			mNavigation("rti_linkId","rti_APATransactionLinkId","rti_APAFirstAppLstLinkId");
			mGenericWait();
			mGenericWait();
			mGenericWait();
			//clicking on icon to search application
			mWaitForVisible("css",mGetPropertyFromFile("rti_APAFindAppIconId"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("rti_APAFindAppIconId"));
			mGenericWait();

			//sending RTI number to be searched 
			//mSendKeys("id",mGetPropertyFromFile("rti_APAFindAppByNoId"),"5703");

			mWaitForVisible("id", mGetPropertyFromFile("rti_APAFindAppByNoId"));
			mCustomWait(2000);
			//mSendKeys("id",mGetPropertyFromFile("rti_APAFindAppByNoId"), "71120160000183");
			mCustomWait(5000);
			IndOrDep("id", "rti_APAFindAppByNoId", "applicationNo");
			mTab("id",mGetPropertyFromFile("rti_APAFindAppByNoId"));
			mGenericWait();

			mClick("id", mGetPropertyFromFile("rti_APAFinalFindIconId"));
			mGenericWait();

			mTakeScreenShot();
			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("rti_APAViewImgIconId"));
			mGenericWait();
			mCustomWait(2000);
/////////////////////////////////////
			
			
int iii=2 ;
int Row_count = driver.findElements(By.xpath("//*[@id=\"command\"]/table/tbody/tr")).size();
System.out.println("rows===>"+Row_count);
for(iii = 2;iii<=Row_count;iii++)
{


String s="//*[@id=\"command\"]/table/tbody/";
String d="tr[";
String e="]/td[2]";
String f="]/td[1]";
String outputtable=s+d+iii+e;
String outputtablef=s+d+iii+f;

try
{
	String output_1o=driver.findElement(By.xpath(outputtablef)).getText(); 
	String output_o=driver.findElement(By.xpath(outputtable)).getText();
/*String output_1o=mGetText("xpath",outputtablef);
String output_o=mGetText("xpath",outputtable);*/
System.out.println(output_1o+"====>"+output_o);
RTI_Application_list.add(output_o);
System.out.println("RTI_Application_list==>"+RTI_Application_list);
switch (output_1o) {
case "RTI Application No.":
	RTIfirstappeallist_RTIapplicationno.add(output_o);
System.out.println("RTIfirstappeallist_RTIapplicationno==>"+RTIfirstappeallist_RTIapplicationno);
break;
case "Date":
	RTIfirstappeallist_Date.add(output_o);
System.out.println("RTIfirstappeallist_Date==>"+RTIfirstappeallist_Date);
break;
case "Type of Applicant":
	RTIfirstappeallist_TypeofApplicant.add(output_o);
System.out.println("RTIfirstappeallist_TypeofApplicant==>"+RTIfirstappeallist_TypeofApplicant);
break;
case "Applicant Name":
	RTIfirstappeallist_ApplicantName.add(output_o);
System.out.println("RTIfirstappeallist_ApplicantName==>"+RTIfirstappeallist_ApplicantName);
break;

case "Mobile No.":
	RTIfirstappeallist_MobileNo.add(output_o);
System.out.println("RTIfirstappeallist_MobileNo==>"+RTIfirstappeallist_MobileNo);
break;


case "Email ID":
	RTIfirstappeallist_Email.add(output_o);
System.out.println("RTIfirstappeallist_Email==>"+RTIfirstappeallist_Email);
break;

case "Alert Subscription":
	RTIfirstappeallist_AlertS.add(output_o);
System.out.println("RTIfirstappeallist_AlertS==>"+RTIfirstappeallist_AlertS);
break;
case "Is the Applicant below Poverty Line":
	RTIfirstappeallist_bpl.add(output_o);
System.out.println("RTIfirstappeallist_bpl==>"+RTIfirstappeallist_bpl);
break;

case "Address Details":
	RTIfirstappeallist_Adddet.add(output_o);
System.out.println("RTIfirstappeallist_Adddet==>"+RTIfirstappeallist_Adddet);
break;


case "Pincode":
	RTIfirstappeallist_Pincode.add(output_o);
System.out.println("RTIfirstappeallist_Pincode==>"+RTIfirstappeallist_Pincode);
break;

/*case "Date":
RTIResponse_Adddet1.add(output_o);
System.out.println("RTIResponse_Adddet1==>"+RTIResponse_Adddet1);
break;

case "Date":
RTIResponse_Pincode1.add(output_o);
System.out.println("RTIResponse_Pincode1==>"+RTIResponse_Pincode1);
break;
*/
case "Subject":
	RTIfirstappeallist_Subject.add(output_o);
System.out.println("RTIfirstappeallist_Subject==>"+RTIfirstappeallist_Subject);
break;

case "Particular information required (in brief)":
	RTIfirstappeallist_partiinfo.add(output_o);
System.out.println("RTIfirstappeallist_partiinfo==>"+RTIfirstappeallist_partiinfo);
break;
case "Media for Receiving Information":
	RTIfirstappeallist_mediatype.add(output_o);
System.out.println("RTIfirstappeallist_mediatype==>"+RTIfirstappeallist_mediatype);
break;

case "Circle":
	RTIfirstappeallist_Circle.add(output_o);
System.out.println("RTIfirstappeallist_Circle==>"+RTIfirstappeallist_Circle);
break;


default:
break;
}




throw new Exception(); 
//throw new java.lang.Exceptions;
}
catch(Exception ex)
{

System.out.println("===================================================================");

}



}
System.out.println("RTI_Application_list==>"+RTI_Application_list);
System.out.println("RTI_Application_list==>"+RTI_Application_list.size());

//	RTI_Application_list.add(InvoCountexecution_index);


///////////////////////////////////////

			mscroll(0,820);
			/*String RTINoAtFirstAplFrmAPA = mGetText("xpath",mGetPropertyFromFile("rti_RTINoAtFirstAplFrmAPA"));
			mAssert(RTINoAtFirstAplFrmAPA, applicationNo.get(CurrentinvoCount).toString(), mGetPropertyFromFile("rti_IsNoMatchesFrFirstAplResponse")+"Actual Number :"+RTINoAtFirstAplFrmAPA+"  Expected Number :"+applicationNo.get(CurrentinvoCount).toString());*/
			mTakeScreenShot();
			mGenericWait();

			//getting RTI Number to verify RTI number is same
			/*String RTINoAtAPA = mGetText("xpath",mGetPropertyFromFile("rti_FirstAplFrmAPANoAssertId"));
			mAssert(RTINoAtAPA, applicationNo.get(CurrentinvoCount).toString(), " RTI number does not matches"+"Actual Number :"+RTINoAtAPA+" Expected Number :"+applicationNo.get(CurrentinvoCount).toString());
*/
			System.out.println("i m above first download");

			if(driver.findElements(By.xpath("//*[@id=\"content\"]/div/table[1]")).size() != 0)
			{
				try{

					//Downloading the file to verify it at RTI Application
					WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/table[1]"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));

					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");

					//to get text inside document td
					String frstDocs1 = mGetText("xpath","//*[@id=\"content\"]/div/table[1]/tbody/tr/td[2]");
					System.out.println(frstDocs1);
					String[] doc = frstDocs1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("no of cdocument in cell :"+countofDoc);
					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));
								System.out.println("No of elements in cell :"+Columns.size());

								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(3000);
									mSwitchTabs();
									mCustomWait(3000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}

			//downloading files uploaded at first appeal
			if(driver.findElements(By.xpath("//*[@id=\"content\"]/div/table[2]")).size() != 0)
			{
				try
				{
					System.out.println("i m above second download");
					mGenericWait();
					//mTab("xpath","//*[@id=\\\"content\\\"]/div/h4[2]");
					try
					{
						WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/table[2]"));
						List<WebElement> rows1 = table.findElements(By.tagName("tr"));

						int rwcount =rows1.size();
						System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
						System.out.println("hello");
						//to get text inside document td

						String frstDocs2 = mGetText("xpath","//*[@id=\"content\"]/div/table[2]/tbody/tr/td[2]");
						System.out.println(frstDocs2);
						String[] doc1 = frstDocs2.split(".pdf");
						int countofDoc1 = doc1.length;
						System.out.println("no of cdocument in cell :"+countofDoc1);
						// Iterate to get the value of each cell in table along with element id
						int Rowno=0;
						for(WebElement rowElement:rows1)
						{
							List<WebElement> cols1=rowElement.findElements(By.xpath("td"));
							int Columnno=0;
							for(WebElement colElement:cols1)
							{
								if(Columnno==1)
								{
									List<WebElement> Columns = cols1.get(1).findElements(By.tagName("a"));
									System.out.println("No of elements in cell :"+Columns.size());

									for(int i=0;i<countofDoc1;i++)
									{
										// trim to ignore the first space of document
										String pdfDoc= doc1[i].trim()+".pdf";	
										System.out.println(pdfDoc);
										driver.findElement(By.linkText(pdfDoc)).click();
										mDownloadFile(myClassName);
										mCustomWait(3000);
										mSwitchTabs();
										mCustomWait(3000);
									}
								}
								Columnno=Columnno+1;
							}
							Rowno=Rowno+1;
						}
					}catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}


			//downloading files uploaded at first appeal
			if(driver.findElements(By.xpath("//*[@id=\"content\"]/div/table[3]")).size() != 0)
			{
				try
				{
					WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/table[3]"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));

					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");
					//to get text inside document td

					String frstDocs1 = mGetText("xpath","//*[@id=\"content\"]/div/table[3]/tbody/tr/td[2]");
					System.out.println(frstDocs1);
					String[] doc = frstDocs1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("no of cdocument in cell :"+countofDoc);
					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));
								System.out.println("No of elements in cell :"+Columns.size());

								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(3000);
									mSwitchTabs();
									mCustomWait(3000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}


			//checking that accepted from APA or not

			mGenericWait();
			isAcceptedFromAPA("rti_isAcceptedFrmAPAId");

			mGenericWait();
			//mClick("id", mGetPropertyFromFile("rti_ApointmntDateFrmAPAId"));

			mGenericWait();
			//selecting date for appointment

			String tokens[] =mGetPropertyFromFile("rti_ApointmntDateFrmAPA").split("/");
			System.out.println("i m selecting a date");
			String strtempdate= tokens[0];



			int cuDate=Integer.parseInt(strtempdate);
			String strnDate=Integer.toString(cuDate);
			//entity.challanRcvdDate
			System.out.println(tokens[2]);
			System.out.println(tokens[1]);
			System.out.println(strnDate);
			mGdatePicker(mGetPropertyFromFile("rti_ApointmntDateFrmAPAId"),tokens[2],tokens[1],strnDate);

			
			
			//	mClick("linkText", mGetPropertyFromFile("rti_ApointmntDateFrmAPA"));

			//clicking on done button after selecting a date
			mClick("xpath",mGetPropertyFromFile("rti_APAAppealAfterSubBtnid"));
			mGenericWait();
			//sending Appeal remark
			mSendKeys("id",mGetPropertyFromFile("rti_APAAppealRemarkId"), mGetPropertyFromFile("rti_APAAppealRemark"));
			mGenericWait();

			mTakeScreenShot();

			mClick("css", mGetPropertyFromFile("rti_APAAppealSubBtnId"));
			mGenericWait();
			mTakeScreenShot();
		/////assertion method///
					firstappeallistassertion();
					
			
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in firstAppeal method");
		}
	}
	//method for response from APA for first Appeal


	public void isAcceptedFromAPA(String AcceptedFromAPA) throws InterruptedException
	{
		try
		{
			mGenericWait();
			mClick("id", mGetPropertyFromFile(AcceptedFromAPA));

			if (mGetPropertyFromFile(AcceptedFromAPA).equals("acceptOrDecline1"))
			{
				mGenericWait();
				mClick("id", mGetPropertyFromFile("rti_AcceptedFrmAPAId"));

				isAcceptedForHearinProcess=true;



			}
			else if (mGetPropertyFromFile(AcceptedFromAPA).equals("acceptOrDecline2"))
			{
				mGenericWait();
				mClick("id", mGetPropertyFromFile("rti_NotAcceptedFrmAPAId"));
				rejectedByAPA=true;
			}
			else
			{
				System.out.println("please enter valid approval");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in isAcceptedFromAPA method");
		}
	}

	// method to know whether citizen wants to apply for first appeal entry or not
	public void isFirstAppealRequired(String yesNo)
	{
		try
		{
			if(mGetPropertyFromFile(yesNo).equals("yes"))
			{
				goForFirstAppeal= true;
			}
			else
			{
				goForFirstAppeal= false;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in isFirstAppealRequired method");
		}
	}

	// method for second appeal from PIO login
	public void secondAppeal() throws InterruptedException, IOException
	{
		try
		{
			//Navigation from RTI Link to Transaction link to Second Appeal link
			//mNavigation("rti_linkId","rti_APATransactionLinkId", "rti_secondAppealId");
			mNavigation("rti_linkId", "rti_APATransactionLinkId", "rti_secondAppealId");
			//sending rti No to search for second appeal
			mGenericWait();
			mWaitForVisible("id",mGetPropertyFromFile("rti_secondAppealSearchNoId"));
			mGenericWait();
			mCustomWait(2000);
			//mSendKeys("id",mGetPropertyFromFile("rti_secondAppealSearchNoId"),"71120160000210");

			//			mSendKeys("id",mGetPropertyFromFile("rti_secondAppealSearchNoId"),appNo);
			IndOrDep("id", "rti_secondAppealSearchNoId", "applicationNo");

			mTakeScreenShot();

			//driver.findElement(By.id("entity.rtiNo")).sendKeys("5157");
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("rti_secondAppealSearchId"));

			//clicking on img after getting application
			mWaitForVisible("css",mGetPropertyFromFile("rti_secondAppealViewImgId"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("rti_secondAppealViewImgId"));
			mGenericWait();
mCustomWait(3000);		
//RTI_Application_list.add(InvoCountexecution_index);


			mTakeScreenShot();

			/*//	driver.findElement(By.cssSelector("img[alt=\"View Details\"]")).click();
			String RTINoAtSecondAPl = mGetText("xpath", mGetPropertyFromFile("rti_RTINoAtSecondAPl"));
			mAssert(RTINoAtSecondAPl, appNo, mGetPropertyFromFile("rti_IsNoMatchesAtSecondAplAsrt")+"Actual Number :"+RTINoAtSecondAPl+" Expected Number :"+appNo);
			//mGenericWait();
			

			//Downloading the file to verify it at Second Appeal
			//first download at second appeal
			if(	driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/table[1]")).size() != 0)
			{
				try
				{
					WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/table[1]"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");

					//to get text inside document td
					String frstDoc1 = mGetText("xpath","//*[@id=\"content\"]/div/div/table[1]/tbody/tr/td[2]");
					System.out.println(frstDoc1);
					String[] doc = frstDoc1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("No of documents in cell :"+countofDoc);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));

								System.out.println("No of elements in cell :"+Columns.size());
								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(4000);
									mSwitchTabs();
									mCustomWait(4000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}


			//second download at second appeal

			if(	driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/table[2]")).size() != 0)
			{
				try
				{
					WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/table[2]"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");

					//to get text inside document td
					String frstDoc1 = mGetText("xpath","//*[@id=\"content\"]/div/div/table[2]/tbody/tr/td[2]");
					System.out.println(frstDoc1);
					String[] doc = frstDoc1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("No of documents in cell :"+countofDoc);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));

								System.out.println("No of elements in cell :"+Columns.size());
								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(4000);
									mSwitchTabs();
									mCustomWait(4000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			 

			//third download at second appeal
			if(	driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/table[3]")).size() != 0)
			{
				try
				{
					WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/table[3]"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");

					//to get text inside document td
					String frstDoc1 = mGetText("xpath","//*[@id=\"content\"]/div/div/table[3]/tbody/tr/td[2]");
					//*[@id="content"]/div/div/table[3]/tbody/tr/td[2]
				//	System.out.println(frstDoc1);
					String[] doc = frstDoc1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("No of documents in cell :"+countofDoc);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));

								System.out.println("No of elements in cell :"+Columns.size());
								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(4000);
									mSwitchTabs();
									mCustomWait(4000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}




			//fourth download at second appeal
			if(	driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/table[4]")).size() != 0)
			{
				try
				{
					WebElement table = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/table[4]"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
					System.out.println("hello");

					//to get text inside document td
					String frstDoc1 = mGetText("xpath","//*[@id=\"content\"]/div/div/table[4]/tbody/tr/td[2]");
					//*[@id="content"]/div/div/table[3]/tbody/tr/td[2]
					System.out.println(frstDoc1);
					String[] doc = frstDoc1.split(".pdf");
					int countofDoc = doc.length;
					System.out.println("No of documents in cell :"+countofDoc);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int Columnno=0;
						for(WebElement colElement:cols)
						{
							if(Columnno==1)
							{
								List<WebElement> Columns = cols.get(1).findElements(By.tagName("a"));

								System.out.println("No of elements in cell :"+Columns.size());
								for(int i=0;i<countofDoc;i++)
								{
									// trim to ignore the first space of document
									String pdfDoc= doc[i].trim()+".pdf";	
									System.out.println(pdfDoc);
									driver.findElement(By.linkText(pdfDoc)).click();
									mDownloadFile(myClassName);
									mCustomWait(4000);
									mSwitchTabs();
									mCustomWait(4000);
								}
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}

			 */
			mCustomWait(4000);
			//======//
			//checking for the approval
			secondAppealAprval("rti_IsSecondAppealAprv");

			//new Select(driver.findElement(By.id("secondappealstatus"))).selectByVisibleText("Approved");
			mCustomWait(2000);
			//sending appeal remark
			mSendKeys("id",mGetPropertyFromFile("rti_secondappealremarkId"), mGetPropertyFromFile("rti_secondappealremarkdata"));

			//	driver.findElement(By.id("secondappealremark")).clear();
			//	driver.findElement(By.id("secondappealremark")).sendKeys("sdhyghfdifuiytgkiyhkoy");
			mCustomWait(2000);
			int currentyear = Calendar.getInstance().get(Calendar.YEAR);
			int currentmonth = Calendar.getInstance().get(Calendar.MONTH)+1;
			// int month = 8;
			String smonth;
			switch (currentmonth) {
			case 1:  smonth = "Jan";
			break;
			case 2:  smonth = "Feb";
			break;
			case 3:  smonth = "Mar";
			break;
			case 4:  smonth = "Apr";
			break;
			case 5:  smonth = "May";
			break;
			case 6:  smonth = "Jun";
			break;
			case 7:  smonth = "Jul";
			break;
			case 8:  smonth = "Aug";
			break;
			case 9:  smonth = "Sep";
			break;
			case 10: smonth = "Oct";
			break;
			case 11: smonth = "Nov";
			break;
			case 12: smonth = "Dec";
			break;
			default: smonth = "Invalid month";
			break;
			}

			int currentday = Calendar.getInstance().get(Calendar.DATE);
			String syear=Integer.toString(currentyear);
			String sday = Integer.toString(currentday);

			System.out.println(syear);
			System.out.println(smonth);
			System.out.println(sday);

			mGenericWait();
			//mClick("xpath","rti_secondAppealDateId");
			//mWaitForVisible("id",mGetPropertyFromFile("rti_secondAppealDateId"));

			mGdatePicker(mGetPropertyFromFile("rti_secondAppealDateId"),syear,smonth,sday);
			//driver.findElement(By.id("entity.secondAppealDate")).click();
			mTab("id", mGetPropertyFromFile("rti_secondAppealDateId"));
			//driver.findElement(By.linkText("13")).click();

			//uploading files at second appeal
			mGenericWait();
			mUpload("id",mGetPropertyFromFile("rti_secondAppealUploadId"),mGetPropertyFromFile("uploadPath2"));
			//driver.findElement(By.id("entity.cfcAttachments0.attPath")).clear();
			//driver.findElement(By.id("entity.cfcAttachments0.attPath")).sendKeys("D:\\Workspace-SeleniumAutomation\\MastekLtd-SwiftLite-16d58e0\\Swift-Selenium UserGuide.pdf");
/////////////////////////////////////


int kkk=2 ;
int Row_count = driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/div[2]/table/tbody/tr")).size();
System.out.println("rows===>"+Row_count);
for(kkk = 2;kkk<=Row_count;kkk++)
{

String s="//*[@id=\"content\"]/div/div/div[2]/table/";
String d="tr[";
String e="]/td[2]";
String f="]/td[1]";
String outputtable=s+d+kkk+e;
String outputtablef=s+d+kkk+f;

try
{
	String output_1o=driver.findElement(By.xpath(outputtablef)).getText();
	String output_o=driver.findElement(By.xpath(outputtable)).getText();
/*String output_1o=mGetText("xpath",outputtablef);
String output_o=mGetText("xpath",outputtable);*/
System.out.println(output_1o+"====>"+output_o);

switch (output_1o) {
case "RTI Application No..":
RTIsecondappeal_RTIapplicationno.add(output_o);
System.out.println("RTIsecondappeal_RTIapplicationno==>"+RTIsecondappeal_RTIapplicationno);
break;
case "Date":
RTIsecondappeal_Date.add(output_o);
System.out.println("RTIsecondappeal_Date==>"+RTIsecondappeal_Date);
break;
case "Type of Applicant":
RTIsecondappeal_TypeofApplicant.add(output_o);
System.out.println("RTIsecondappeal_TypeofApplicant==>"+RTIsecondappeal_TypeofApplicant);
break;
case "Applicant Name":
RTIsecondappeal_ApplicantName.add(output_o);
System.out.println("RTIsecondappeal_ApplicantName==>"+RTIsecondappeal_ApplicantName);
break;

case "Mobile No.":
RTIsecondappeal_MobileNo.add(output_o);
System.out.println("RTIsecondappeal_MobileNo==>"+RTIsecondappeal_MobileNo);
break;


case "Email ID":
RTIsecondappeal_Email.add(output_o);
System.out.println("RTIsecondappeal_Email==>"+RTIsecondappeal_Email);
break;

case "Alert Subscription":
RTIsecondappeal_AlertS.add(output_o);
System.out.println("RTIsecondappeal_AlertS==>"+RTIsecondappeal_AlertS);
break;
case "Is the Applicant below Poverty Line":
RTIsecondappeal_bpl.add(output_o);
System.out.println("RTIsecondappeal_bpl==>"+RTIsecondappeal_bpl);
break;

case "Address Details":
RTIsecondappeal_Adddet.add(output_o);
System.out.println("RTIsecondappeal_Adddet==>"+RTIsecondappeal_Adddet);
break;


case "Pincode":
RTIsecondappeal_Pincode.add(output_o);
System.out.println("RTIsecondappeal_Pincode==>"+RTIsecondappeal_Pincode);
break;

/*case "Date":
RTIResponse_Adddet1.add(output_o);
System.out.println("RTIResponse_Adddet1==>"+RTIResponse_Adddet1);
break;

case "Date":
RTIResponse_Pincode1.add(output_o);
System.out.println("RTIResponse_Pincode1==>"+RTIResponse_Pincode1);
break;
*/
case "Subject":
RTIsecondappeal_Subject.add(output_o);
System.out.println("RTIsecondappeal_Subject==>"+RTIsecondappeal_Subject);
break;

case "Particular information required (in brief)":
RTIsecondappeal_partiinfo.add(output_o);
System.out.println("RTIsecondappeal_partiinfo==>"+RTIsecondappeal_partiinfo);
break;
case "Media for Receiving Information":
RTIsecondappeal_mediatype.add(output_o);
System.out.println("RTIsecondappeal_mediatype==>"+RTIsecondappeal_mediatype);
break;

case "Circle":
RTIsecondappeal_Circle.add(output_o);
System.out.println("RTIsecondappeal_Circle==>"+RTIsecondappeal_Circle);
break;


default:
break;
}




throw new Exception(); 
//throw new java.lang.Exceptions;
}
catch(Exception ex)
{

System.out.println("===================================================================");

}



}



			mGenericWait();
			mTakeScreenShot();
			mGenericWait();
			mClick("css", mGetPropertyFromFile("rti_secondAppealSubBtnId"));
			//driver.findElement(By.cssSelector("input.button.css_btn")).click();

			mGenericWait();
			mClick("id", mGetPropertyFromFile("rti_secondAppealProcdBtnId"));
			mGenericWait();
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in secondAppeal method");
		}
	}

	//method to chechk whether approved from second appeal
	public void secondAppealAprval(String approval)
	{
		try
		{
			mGenericWait();
			mSelectDropDown("id",mGetPropertyFromFile("rti_secondAppealStatusId"),mGetPropertyFromFile(approval));
			mGenericWait();
			if(mGetPropertyFromFile(approval).equals("Rejected"))
			{
				rejectedAtSecondAppeal=true;
			}
			else
			{
				rejectedAtSecondAppeal=false;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in secondAppealAprval method");
		}
	}

	//print-reprint Notifications
	public void PrintReprintNotifctonLetrs() throws IOException
	{
		try
		{
			//Navigation from RTI Link to Transaction link to Print-Reprint Notification letters link
			mNavigation("rti_linkId","rti_PIOTransactionId", "rti_printReprintLetrsId");

			/*//RTI Link
			mWaitForVisible("linkText",mGetPropertyFromFile("rti_linkId"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("rti_linkId"));
			mGenericWait();

			//Transaction Link
			mWaitForVisible("linkText",mGetPropertyFromFile("rti_PIOTransactionId"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("rti_PIOTransactionId"));
			mGenericWait();

			//driver.findElement(By.linkText("RTI")).click();
			//driver.findElement(By.linkText("Transactions")).click();

			//Print-Reprint Notification Letters Link
			mWaitForVisible("linkText",mGetPropertyFromFile("rti_printReprintLetrsId"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("rti_printReprintLetrsId"));
			mGenericWait();*/

			//	driver.findElement(By.linkText("Print-Reprint Notification Letters")).click();
			//public boolean rejectedAtSecondAppeal=false;
			//	public boolean rejectedByAPA = false;
			//	rejectedAtPIO = true;
			//	public boolean frwdToRelvntOfficer = false;

			mGenericWait();
			if(rejectedAtSecondAppeal)
			{
				mClick("id",mGetPropertyFromFile("rti_printReprintrequestTypeId"));
				mSelectDropDown("id",mGetPropertyFromFile("rti_printReprintrequestTypeId"), mGetPropertyFromFile("rti_secAplRejctnData"));
			}
			else if(rejectedByAPA)
			{
				mClick("id",mGetPropertyFromFile("rti_printReprintrequestTypeId"));
				mSelectDropDown("id",mGetPropertyFromFile("rti_printReprintrequestTypeId"), mGetPropertyFromFile("rti_rejctdByAPAData"));
			}
			else if(frwdToRelvntOfficer)
			{
				mClick("id",mGetPropertyFromFile("rti_printReprintrequestTypeId"));
				mSelectDropDown("id",mGetPropertyFromFile("rti_printReprintrequestTypeId"), mGetPropertyFromFile("rti_frwdToRevlntOfficerData"));
			}
			else if(rejectedAtPIO)
			{
				mClick("id",mGetPropertyFromFile("rti_printReprintrequestTypeId"));
				mSelectDropDown("id",mGetPropertyFromFile("rti_printReprintrequestTypeId"), mGetPropertyFromFile("rti_rejctdAtPIOData"));
			}
			else
			{
				System.out.println("invalid status of Rejection");
			}

			//sending application number for printing
			mGenericWait();
			//mSendKeys("id",mGetPropertyFromFile("rti_printReprintrtiNoId"),"515403");

			//			mSendKeys("id",mGetPropertyFromFile("rti_printReprintrtiNoId"),appNo);
			IndOrDep("id", "rti_printReprintrtiNoId", "applicationNo");
			mTab("id",mGetPropertyFromFile("rti_printReprintrtiNoId"));
			mGenericWait();
			mTakeScreenShot();

			//new Select(driver.findElement(By.id("requestType"))).selectByVisibleText("Rejected");
			//driver.findElement(By.id("rtiNo")).clear();
			//driver.findElement(By.id("rtiNo")).sendKeys("515403");

			//submit button
			mWaitForVisible("css",mGetPropertyFromFile("rti_printReprintSubId"));
			mClick("css",mGetPropertyFromFile("rti_printReprintSubId"));

			//proceed button
			mWaitForVisible("id",mGetPropertyFromFile("rti_printReprintProcdBTnId"));
			mClick("id",mGetPropertyFromFile("rti_printReprintProcdBTnId"));

			mCustomWait(10000);
			mTakeScreenShot();

			//	mChallanPDFReader(appNo, RTIServices.ApplicantName);

			mCustomWait(5000);
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in PrintReprintNotifctonLetrs method");
		}

	}


	public void RTIDispatchDetails() throws IOException
	{
		try
		{
			System.out.println("aprvdFrmPIOFrDisDtls:::" +aprvdFrmPIOFrDisDtls);
			System.out.println("isEmail::::: "+isEmail);
			mGenericWait();
			System.out.println("approval flag==>"+aprvdFrmPIOFrDisDtls);
			System.out.println("email flag==>"+isEmailArray.get(CurrentinvoCount));
			
			if(aprvdFrmPIOFrDisDtls && isEmailArray.get(CurrentinvoCount)=="false")			   
			{
				mGenericWait();
				common.departmentLogin(mGetPropertyFromFile("PIODeptLogin"),mGetPropertyFromFile("PIODeptPassword"));
				mGenericWait();

				//Navigation from RTI Link to Transaction link to RTI Dispatch Details link
				mNavigation("rti_linkId","rti_PIOTransactionId","rti_RTIDispatchDetailsLinkId");

				//sending RTI Number to search
				mWaitForVisible("id", mGetPropertyFromFile("rti_RTIDispatchDetailsNoId"));
				mCustomWait(1000);
				//				mSendKeys("id",mGetPropertyFromFile("rti_RTIDispatchDetailsNoId"),appNo);
				IndOrDep("id", "rti_RTIDispatchDetailsNoId", "applicationNo");
  
				//				mSendKeys("id",mGetPropertyFromFile("rti_RTIDispatchDetailsNoId"),"71142");

				mGenericWait();
				//searching application for dispatch details
				mClick("css",mGetPropertyFromFile("rti_RTIDisDetAplnSearchId"));
				mGenericWait();
				mTakeScreenShot();
				mGenericWait();

				//viewing the form
				mGenericWait();
				mClick("xpath",mGetPropertyFromFile("rti_ViewAppAtRTIDispatchDetId"));

				mGenericWait();
				String RTINoAtDispatchDetails = mGetText("css",mGetPropertyFromFile("rti_RTINoAtDispatchDetails"));
				System.out.println(RTINoAtDispatchDetails);

				mAssert(RTINoAtDispatchDetails, applicationNo.get(CurrentinvoCount).toString(), mGetPropertyFromFile("rti_IsNoMatchesAtDispatchDetails")+"Actual Number :"+RTINoAtDispatchDetails+" Expected Number :"+appNo);
				//	mClick("xpath","//*[@id=\\\"command\\\"]/table/tbody/tr[2]/td[2]");
				mGenericWait();
				mscroll(0,500);
				mGenericWait();
				mTakeScreenShot();

				//closing the form
				mGenericWait();
				mClick("css",mGetPropertyFromFile("rti_CloseFormAtDispatchId"));
				mGenericWait();

				//choosing the date for dispatch details
				mGenericWait();

				// to be continued
				int currentyear = Calendar.getInstance().get(Calendar.YEAR);
				int currentmonth = Calendar.getInstance().get(Calendar.MONTH)+1;

				// int month = 8;
				String smonth;
				switch (currentmonth) {
				case 1:  smonth = "Jan";
				break;
				case 2:  smonth = "Feb";
				break;
				case 3:  smonth = "Mar";
				break;
				case 4:  smonth = "Apr";
				break;
				case 5:  smonth = "May";
				break;
				case 6:  smonth = "Jun";
				break;
				case 7:  smonth = "Jul";
				break;
				case 8:  smonth = "Aug";
				break;
				case 9:  smonth = "Sep";
				break;
				case 10: smonth = "Oct";
				break;
				case 11: smonth = "Nov";
				break;
				case 12: smonth = "Dec";
				break;
				default: smonth = "Invalid month";
				break;
				}
				int currentday = Calendar.getInstance().get(Calendar.DATE);
				String syear=Integer.toString(currentyear);

				//String smonth = Integer.toString(currentmonth);
				String sday = Integer.toString(currentday);

				System.out.println(syear);
				System.out.println(smonth);
				System.out.println(sday);

				mGenericWait();
				mGdatePicker("name",mGetPropertyFromFile("rti_RTIDisDetDateId"),syear,smonth,sday);
				mGenericWait();
				//				deliveryMode="Collect at Office";
				mSelectDropDown("id",mGetPropertyFromFile("rti_DisDetActionStatusId"), deliveryMode);

				mGenericWait();

				if(deliveryMode.equals("By Post/Courier"))
				{
					//mClick("css",mGetPropertyFromFile("rti_DispatchDetailsOptValId"));

					mGenericWait();
					mSendKeys("id",mGetPropertyFromFile("rti_DispatchDetpostalId"),mGetPropertyFromFile("rti_DispatchDetpostalData"));

					mGenericWait();
					mClick("xpath",mGetPropertyFromFile("rti_DispatchPostSubBtnId"));

					//	mClick("css",mGetPropertyFromFile("rti_DispatchPostSubBtnId"));

					mGenericWait();
				}
				mGenericWait();
				mClick("id",mGetPropertyFromFile("rti_DispatchStatusId"));				
				mTakeScreenShot();

				//submit button
				mGenericWait();
				mClick("xpath",mGetPropertyFromFile("rti_DispatchDetailsSubId"));

				//	mClick("css",mGetPropertyFromFile("rti_DispatchDetailsSubId"));
				mCustomWait(3000);
				String SavdMsgAtDispatchDetails = mGetText("xpath",mGetPropertyFromFile("rti_SavdMsgAtDispatchDetails"));
				mAssert(SavdMsgAtDispatchDetails,mGetPropertyFromFile("rti_SavedMsgAtRTIDispatchDetails"),"Actual Message :"+SavdMsgAtDispatchDetails+" Expected Message :"+mGetPropertyFromFile("rti_SavedMsgAtRTIDispatchDetails"));
				mCustomWait(3000);
				//proceed Button
				mGenericWait();
				aprvdFrmPIOFrDisDtls=false;
				mClick("id",mGetPropertyFromFile("rti_DispatchDetailsProcdBtnId"));
				mTakeScreenShot();



				common.logOut();
				mTakeScreenShot();

				common.finalLogOut();
			}
			else
			{
				System.out.println("RTI Dispatch Details is not allowed for this applications");
			}

			aprvdFrmPIOFrDisDtls=false;

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in RTIDispatchDetails method");
		}
	}



	public void Reset_Script() {

		try{

			if(mGetPropertyFromFile("lgl_RtiAppReset").equalsIgnoreCase("Yes")){
				//waiting for RTI linkText
				chllanForRTIServices = true;

				//Navigation from RTI to RTI Application Entry
				mNavigation("rti_linkId", "rti_rtiAppLinkId");

				//calling isOrganization name

				isOrganization(mGetPropertyFromFile("rti_individualOrOrgnData"),mGetPropertyFromFile("rti_organizationName"));

				//selecting title mr. or miss. etc
				mSelectDropDown("id",mGetPropertyFromFile("rti_applicantTitleId"),mGetPropertyFromFile("rti_TitleData"));
				mGenericWait();

				// printing name
				mSendKeys("id",mGetPropertyFromFile("rti_applicantNameId"),mGetPropertyFromFile("rti_Name"));
				mGenericWait();

				//first address line
				mSendKeys("id",mGetPropertyFromFile("rti_Address1Id"),mGetPropertyFromFile("rti_Add1"));
				mGenericWait();

				//second address line
				mSendKeys("id",mGetPropertyFromFile("rti_Address2Id"),mGetPropertyFromFile("rti_Add2"));
				mGenericWait();

				//new pincode 
				mSendKeys("id",mGetPropertyFromFile("rti_pincodeId"),mGetPropertyFromFile("rti_pin"));
				mGenericWait();

				//checking whether the address is same
				isSameAdd("rti_sameOrDiffAddId","rti_Add","rti_pin");

				//checking whether below poverty line
				isAblBplType("rti_bplYesNoId","rti_bplNum");

				//checking subscription type i.e. email ,sms,or both
				subScription("rti_SubScriptnData");
				mTakeScreenShot();

				mscroll(0,350);
				mSendKeys("id", mGetPropertyFromFile("rti_mobileId"),  mGetPropertyFromFile("rti_mobile"));
				mGenericWait();
				mSendKeys("name", mGetPropertyFromFile("rti_emailId"),  mGetPropertyFromFile("rti_email"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("rti_uidNoId"),  mGetPropertyFromFile("rti_uidNo"));
				mGenericWait();

				//calling method for delivery i.e. post or office or email
				deliveryMode("rti_deliveryModeId");

				//rti subject and details
				mTakeScreenShot();
				mSendKeys("id", mGetPropertyFromFile("rti_rtisubId"),  mGetPropertyFromFile("rti_subject"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("rti_rtiappdetail"),  mGetPropertyFromFile("rti_detail"));
				mGenericWait();

				//calling method to upload files 1 optional &1 depending on below poverty line
				rtiUpload();
				mscroll(350,500);
				mTakeScreenShot();

				//selecting media type
				mediaType("rti_typeOfMediaId");
				//selecting payment mode i.e. bank or ulb
				mGenericWait();

				mClick("id", mGetPropertyFromFile("offlinePaymentId"));
				mGenericWait();

				mSelectDropDown("id", mGetPropertyFromFile("offlinePaymentModeId"), mGetPropertyFromFile("byBankOrULB"));
				mGenericWait();

				if(mGetPropertyFromFile("byBankOrULB").equals("Pay by Challan@Bank"))
				{   
					mSelectDropDown("id", mGetPropertyFromFile("widOutLOIBankId"), mGetPropertyFromFile("widOutLOINameOfBank"));					
					mGenericWait();					
				}

				mTakeScreenShot();

				//Reset Button
				mWaitForVisible("id",mGetPropertyFromFile("ResetButtonId"));
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("ResetButtonId"));	
				mCustomWait(2000);

				String Apl_Bpl = mCaptureSelectedValue("id", mGetPropertyFromFile("rti_aplBplTypeId"));
				mAssert(Apl_Bpl, "-- Select --",mGetPropertyFromFile("rti_IsDataMatch")+" Actual Number :"+Apl_Bpl+" Expected Number :"+ "-- Select --");
				System.out.println("Actual is : " +Apl_Bpl+ " Expected is : " + "-- Select --");

				String Delmode = mCaptureSelectedValue("id", mGetPropertyFromFile("rti_modeOfDeliveryId"));
				mAssert(Delmode, "--Select Mode--",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Delmode+" Expected :"+ "--Select Mode--");
				System.out.println("Actual is : " +Delmode+  " Expected is : " + "--Select Mode--");

				String Medtype = mCaptureSelectedValue("id", mGetPropertyFromFile("rti_mediaTypeId"));
				mAssert(Medtype, "--Select Media Type--",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Medtype+" Expected :"+ "--Select Media Type--");
				System.out.println("Actual is : " +Medtype+  " Expected is : " + "--Select Media Type--");

				String Paymentmode = mCaptureSelectedValue("id", mGetPropertyFromFile("offlinePaymentModeId"));
				mAssert(Paymentmode, "--Select Payment Mode--",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Paymentmode+" Expected :"+ "--Select Payment Mode--");
				System.out.println("Actual is : " +Paymentmode+  " Expected is : " + "--Select Payment Mode--");

				if(mGetPropertyFromFile("byBankOrULB").equals("Pay by Challan@Bank"))
				{
					String Bankname = mCaptureSelectedValue("id", mGetPropertyFromFile("widOutLOIBankId"));
					mAssert(Bankname, "Select Bank",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Bankname+" Expected :"+ "Select Bank");
					System.out.println("Actual is : " +Bankname+  " Expected is : " + "Select Bank");
				}

				String Aadhaarno = mGetText("id", mGetPropertyFromFile("rti_uidNoId"));
				mAssert(Aadhaarno, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Aadhaarno+" Expected :"+ "");
				System.out.println("Actual is : " +Aadhaarno+  " Expected is : " + "");

				String Subject = mGetText("id", mGetPropertyFromFile("rti_rtisubId"));
				mAssert(Subject, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Subject+" Expected :"+ "");
				System.out.println("Actual is : " +Subject+  " Expected is : " + "");

				String Info = mGetText("id", mGetPropertyFromFile("rti_rtiappdetail"));
				mAssert(Info, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Info+" Expected :"+ "");
				System.out.println("Actual is : " +Info+  " Expected is : " + "");

				mTakeScreenShot();		
				mscroll(0,-250);
				mCustomWait(2000);
				mTakeScreenShot();										
			}

			else if(mGetPropertyFromFile("lgl_Rti1stAppReset").equalsIgnoreCase("Yes")){

				mGenericWait();
				//Navigation from RTI Link to First Appeal Entry
				mNavigation("rti_linkId","rti_FirstAppealEntryLinkId");
				mGenericWait();

				//First Appeal Entry
				mWaitForVisible("css",mGetPropertyFromFile("rti_FirstAppealViewFormId"));
				mGenericWait();

				//icon to find application
				mWaitForVisible("css",mGetPropertyFromFile("rti_AppSearchIconId"));
				mClick("css", mGetPropertyFromFile("rti_AppSearchIconId"));

				mGenericWait();

				mSendKeys("id",mGetPropertyFromFile("rti_FrstAplEntryFindByNoId"),"71120160000198");

				//					mSendKeys("id",mGetPropertyFromFile("rti_FrstAplEntryFindByNoId"),appNo);
				mTab("id",mGetPropertyFromFile("rti_FrstAplEntryFindByNoId"));
				mGenericWait();

				//find application icon
				mWaitForVisible("id",mGetPropertyFromFile("rti_FindApplIconId"));
				mGenericWait();
				mClick("id",mGetPropertyFromFile("rti_FindApplIconId"));

				//img view icon
				mWaitForVisible("xpath",mGetPropertyFromFile("rti_ViewImgIconId"));
				mGenericWait();
				mClick("xpath",mGetPropertyFromFile("rti_ViewImgIconId"));
				mGenericWait();
				mTakeScreenShot();

				//Appeal button
				mWaitForVisible("xpath",mGetPropertyFromFile("rti_FirstAppealBtnId"));
				mCustomWait(1000);

				mGenericWait();
				mClick("xpath",mGetPropertyFromFile("rti_FirstAppealBtnId"));
				mGenericWait();
				mTakeScreenShot();

				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("rti_FirstAppealDetailsId"),mGetPropertyFromFile("rti_firstAppealDetails"));

				mGenericWait();
				mUpload("css",mGetPropertyFromFile("rti_FirstAppealUploadId"),mGetPropertyFromFile("uploadPath"));		
				mCustomWait(2500);

				// checking whether accepted or not
				isAccept("rti_FirstAppealIsAcceptedId");

				//submit button
				mGenericWait();
				mClick("css",mGetPropertyFromFile("rti_FirstAppealResetBtnId"));
				mGenericWait();

				mTakeScreenShot();
				mCustomWait(1000);

				String grdofApp = mGetText("id", mGetPropertyFromFile("rti_FirstAppealDetailsId"));
				mAssert(grdofApp, "",mGetPropertyFromFile("rti_IsDataMatch")+" Actual Number :"+grdofApp+" Expected Number :"+ "");
				System.out.println("Actual is : " +grdofApp+ " Expected is : " + "");

				String declaration = mGetText("id", mGetPropertyFromFile("rti_FirstAppealIsAcceptedId"),"value");
				mAssert(declaration, "",mGetPropertyFromFile("rti_IsDataMatch")+" Actual Number :"+declaration+" Expected Number :"+ "");
				System.out.println("Actual is : " +declaration+ " Expected is : " + "");
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Reset_Script method");
		}

	}

	// to verify the fetch data with originally entered data

	public void verifyFirstApp() 
	{

		mGenericWait();
		String firstAplRTINO = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealNoAssertId"));
		mAssert(firstAplRTINO, appNo, "Actual Number :"+firstAplRTINO+" Expected Number :"+appNo);
		System.out.println("Actual is : " +firstAplRTINO+  " Expected is : " +appNo);

		String appType = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealAppTypeAssertId"));
		mAssert(appType, mGetPropertyFromFile("rti_individualOrOrgnAssertData"), "Actual :"+appType+" Expected :"+mGetPropertyFromFile("rti_individualOrOrgnAssertData"));
		System.out.println("Actual is : " +appType+  " Expected is : " + mGetPropertyFromFile("rti_individualOrOrgnAssertData"));

		if(mGetPropertyFromFile("rti_individualOrOrgnId").equalsIgnoreCase("organisation"))
		{
			String orgName = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealOrgNameAssertId"));
			mAssert(orgName, mGetPropertyFromFile("rti_organizationName"), "Actual :"+orgName+" Expected :"+mGetPropertyFromFile("rti_organizationName"));
			System.out.println("Actual is : " +orgName+  " Expected is : " + mGetPropertyFromFile("rti_organizationName"));
		}




		String Title = mGetPropertyFromFile("rti_TitleData");
		String name = mGetPropertyFromFile("rti_Name");
		String Name = Title+name;					
		System.out.println("Applicant name is : " + Name); 			

		if(mGetPropertyFromFile("rti_individualOrOrgnId").equalsIgnoreCase("individual"))
		{
			String appName = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealAppNameAssertId"));
			mAssert(appName,Name , "Actual :"+appName+" Expected :"+ Name);
			System.out.println("Actual is : " +appName+  " Expected is : " + Name);	
		}
		else
		{
			String appName1 = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealAppNameIfOrgAssertId"));
			mAssert(appName1, Name, "Actual :"+appName1+" Expected :"+Name);
			System.out.println("Actual is : " +appName1+  " Expected is : " + Name);	
		}

		if(mGetPropertyFromFile("rti_individualOrOrgnId").equalsIgnoreCase("individual"))
		{
			String aadharNo = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealAadharNoAssertId"));
			mAssert(aadharNo, mGetPropertyFromFile("rti_uidNo"), "Actual :"+aadharNo+" Expected :"+mGetPropertyFromFile("rti_uidNo"));
			System.out.println("Actual is : " +aadharNo+  " Expected is : " + mGetPropertyFromFile("rti_uidNo"));	
		}

		else
		{
			String aadharNo = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealAadharNoIfOrgAssertId"));
			mAssert(aadharNo, mGetPropertyFromFile("rti_uidNo"), "Actual :"+aadharNo+" Expected :"+mGetPropertyFromFile("rti_uidNo"));
			System.out.println("Actual is : " +aadharNo+  " Expected is : " + mGetPropertyFromFile("rti_uidNo"));	
		}

		if(mGetPropertyFromFile("rti_individualOrOrgnId").equalsIgnoreCase("individual"))
		{
			String alertSub = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealAlertSubAssertId"));
			mAssert(alertSub, mGetPropertyFromFile("rti_SubScriptnAssertData"), "Actual :"+alertSub+" Expected :"+mGetPropertyFromFile("rti_SubScriptnAssertData"));
			System.out.println("Actual is : " +alertSub+  " Expected is : " + mGetPropertyFromFile("rti_SubScriptnAssertData"));
		}

		else
		{
			String alertSub = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealAlertSubIfOrgAssertId"));
			mAssert(alertSub, mGetPropertyFromFile("rti_SubScriptnAssertData"), "Actual :"+alertSub+" Expected :"+mGetPropertyFromFile("rti_SubScriptnAssertData"));
			System.out.println("Actual is : " +alertSub+  " Expected is : " + mGetPropertyFromFile("rti_SubScriptnAssertData"));
		}

		if(mGetPropertyFromFile("rti_individualOrOrgnId").equalsIgnoreCase("individual"))
		{
			String aplBpl = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealAplBplAssertId"));
			mAssert(aplBpl, mGetPropertyFromFile("rti_bplYesNoId"), "Actual :"+aplBpl+" Expected :"+mGetPropertyFromFile("rti_bplYesNoId"));
			System.out.println("Actual is : " +aplBpl+  " Expected is : " + mGetPropertyFromFile("rti_bplYesNoId"));
		}

		else
		{
			String aplBpl = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealAplBplIfOrgAssertId"));
			mAssert(aplBpl, mGetPropertyFromFile("rti_bplYesNoId"), "Actual :"+aplBpl+" Expected :"+mGetPropertyFromFile("rti_bplYesNoId"));
			System.out.println("Actual is : " +aplBpl+  " Expected is : " + mGetPropertyFromFile("rti_bplYesNoId"));
		}

		if(mGetPropertyFromFile("rti_bplYesNoId").equalsIgnoreCase("yes"))
		{
			String bplNo = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealBplNoAssertId"));
			mAssert(bplNo, mGetPropertyFromFile("rti_bplNum"), "Actual Number :"+bplNo+" Expected Number :"+mGetPropertyFromFile("rti_bplNum"));
			System.out.println("Actual is : " +bplNo+  " Expected is : " + mGetPropertyFromFile("rti_bplNum"));						
		}

		/*			String addDet1 = mGetText("xpath",mGetPropertyFromFile("rti_aplBplTypeId"));
			mAssert(addDet1, mGetPropertyFromFile("rti_bplYesNoId"), "Actual :"+addDet1+" Expected :"+mGetPropertyFromFile("rti_bplYesNoId"));
			System.out.println("Actual is : " +addDet1+  " Expected is : " + mGetPropertyFromFile("rti_bplYesNoId"));	

			if(mGetPropertyFromFile("rti_sameOrDiffAddId").equalsIgnoreCase("isCorrespondence1"))
			{
			String addDet2 = mGetText("xpath",mGetPropertyFromFile("rti_aplBplTypeId"));
			mAssert(addDet2, mGetPropertyFromFile("rti_bplYesNoId"), "Actual :"+addDet2+" Expected :"+mGetPropertyFromFile("rti_bplYesNoId"));
			System.out.println("Actual is : " +addDet2+  " Expected is : " + mGetPropertyFromFile("rti_bplYesNoId"));
			}
			else
			{					
			String addDet2 = mGetText("xpath",mGetPropertyFromFile("rti_aplBplTypeId"));
			mAssert(addDet2, mGetPropertyFromFile("rti_bplYesNoId"), "Actual :"+addDet2+" Expected :"+mGetPropertyFromFile("rti_bplYesNoId"));
			System.out.println("Actual is : " +addDet2+  " Expected is : " + mGetPropertyFromFile("rti_bplYesNoId"));
			}
		 */	
		String subject = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealSubjectAssertId"));
		mAssert(subject, mGetPropertyFromFile("rti_subject"), "Actual :"+subject+" Expected :"+mGetPropertyFromFile("rti_subject"));
		System.out.println("Actual is : " +subject+  " Expected is : " + mGetPropertyFromFile("rti_subject"));	

		if(mGetPropertyFromFile("rti_individualOrOrgnId").equalsIgnoreCase("individual") && mGetPropertyFromFile("rti_aplBplTypeId").equalsIgnoreCase("no") )
		{
			String subject1 = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealSubjectIfAplAssertId"));
			mAssert(subject1, mGetPropertyFromFile("rti_subject"), "Actual :"+subject1+" Expected :"+mGetPropertyFromFile("rti_subject"));
			System.out.println("Actual is : " +subject1+  " Expected is : " + mGetPropertyFromFile("rti_subject"));	
		}

		String detail = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealDetailAssertId"));
		mAssert(detail, mGetPropertyFromFile("rti_detail"), "Actual :"+detail+" Expected :"+mGetPropertyFromFile("rti_detail"));
		System.out.println("Actual is : " +detail+  " Expected is : " + mGetPropertyFromFile("rti_detail"));

		if(mGetPropertyFromFile("rti_individualOrOrgnId").equalsIgnoreCase("individual") && mGetPropertyFromFile("rti_aplBplTypeId").equalsIgnoreCase("no") )
		{
			String detail1 = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealDetailIfAplAssertId"));
			mAssert(detail1, mGetPropertyFromFile("rti_detail"), "Actual :"+detail1+" Expected :"+mGetPropertyFromFile("rti_detail"));
			System.out.println("Actual is : " +detail1+  " Expected is : " + mGetPropertyFromFile("rti_detail"));	
		}

		String mediaType = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealMediaTypeAssertId"));
		mAssert(mediaType, mGetPropertyFromFile("rti_typeOfMediaId"), "Actual :"+mediaType+" Expected :"+mGetPropertyFromFile("rti_typeOfMediaId"));
		System.out.println("Actual is : " +mediaType+  " Expected is : " + mGetPropertyFromFile("rti_typeOfMediaId"));	

		if(mGetPropertyFromFile("rti_individualOrOrgnId").equalsIgnoreCase("individual") && mGetPropertyFromFile("rti_aplBplTypeId").equalsIgnoreCase("no") )
		{
			String mediaType1 = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealMediaTypeIfAplAssertId"));
			mAssert(mediaType1, mGetPropertyFromFile("rti_typeOfMediaId"), "Actual :"+mediaType1+" Expected :"+mGetPropertyFromFile("rti_typeOfMediaId"));
			System.out.println("Actual is : " +mediaType1+  " Expected is : " + mGetPropertyFromFile("rti_typeOfMediaId"));	
		}

		String pioResponse = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealPioRespAssertId"));
		System.out.println("Action on Application is : " +pioResponse);

		if(mGetPropertyFromFile("rti_individualOrOrgnId").equalsIgnoreCase("individual") && mGetPropertyFromFile("rti_aplBplTypeId").equalsIgnoreCase("no") )
		{
			String pioResponse1 = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealPioRespIfAplAssertId"));
			System.out.println("Action on Application is : " +pioResponse1);	
		}

		/*if(mGetPropertyFromFile("rti_aplBplTypeId").equalsIgnoreCase("yes"))
		{
			String uploadfile1 = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealUploadfile1IfBplAssertId")).trim();
			mAssert(uploadfile1, mGetPropertyFromFile("uploadfile1AssertData"), "Actual :"+uploadfile1+" Expected :"+mGetPropertyFromFile("uploadfile1AssertData"));
			System.out.println("Actual is : " +uploadfile1+  " Expected is : " + mGetPropertyFromFile("uploadfile1AssertData"));

			String uploadfile2 = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealUploadfile2IfBplAssertId")).trim();
			mAssert(uploadfile2, mGetPropertyFromFile("uploadfile2AssertData"), "Actual :"+uploadfile2+" Expected :"+mGetPropertyFromFile("uploadfile2AssertData"));
			System.out.println("Actual is : " +uploadfile2+  " Expected is : " + mGetPropertyFromFile("uploadfile2AssertData"));	
		}

		else
		{
			String uploadfile1 = mGetText("xpath",mGetPropertyFromFile("rti_1stAppealUploadfile1IfAplAssertId")).trim();
			mAssert(uploadfile1, mGetPropertyFromFile("uploadfile1AssertData"), "Actual :"+uploadfile1+" Expected :"+mGetPropertyFromFile("uploadfile1AssertData"));
			System.out.println("Actual is : " +uploadfile1+  " Expected is : " + mGetPropertyFromFile("uploadfile1AssertData"));
		}	*/
	}


	public void VerAppStatus() 
	{
		try{
			mWaitForVisible("css",mGetPropertyFromFile("applSearchId"));
			mGenericWait();
			mSendKeys("css",mGetPropertyFromFile("applSearchId"),mGetPropertyFromFile("applSearchData"));
			mGenericWait();
			mClick("css",mGetPropertyFromFile("applSearchBtnId"));
			mCustomWait(2000);
			mTakeScreenShot();
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("closeFormAtApplSearchId"));
			mGenericWait();
			mTakeScreenShot();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in VerAppStatus method");
		}
	}

	public void fail(String string) {
		// TODO Auto-generated method stub
	}


	public void formE() 
	{
		try{

			System.out.println("no payment required");
			mGenericWait();
			mTakeScreenShot();
			mGenericWait();

			// submit button
			mWaitForVisible("css",mGetPropertyFromFile("ULBSubmitButtonId"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("ULBSubmitButtonId"));

			//taking screenshot & assert to check application is saved or not
			mCustomWait(3000);
			mTakeScreenShot();
			mCustomWait(2000);
			//not to be commented
			/*String SavedMsg = mGetApplicationNo("css",mGetPropertyFromFile("rti_isApplnSavedAssert"));
		String Actmsg = "Your RTI Application is saved successfully.";
		mAssert(SavedMsg, Actmsg, "Actual Message :"+SavedMsg+" Expected Message :"+Actmsg);*/

			//proceed button
			mWaitForVisible("id",mGetPropertyFromFile("proceedButtonId"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("proceedButtonId"));
			mGenericWait();
			chllanForRTIServices=false;

			//					appNo=mGetApplicationNo("css",mGetPropertyFromFile("rti_ApplicationNumId"));
			mWaitForVisible("css",mGetPropertyFromFile("rti_ApplicationNumId"));
			mAppNoArray("css",mGetPropertyFromFile("rti_ApplicationNumId"));
			mTakeScreenShot();
			System.out.println(appNo);

			//checking whether application number is empty or not
			if(appNo.length()==0)
			{
				System.out.println("Application Number is not created");
			}

			/*//finish button
			mWaitForVisible("css",mGetPropertyFromFile("rti_submitBtnId"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("rti_submitBtnId"));
			mGenericWait();*/

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in formE method");
		}
	}

	
	
	
	public void RTItableread()
	{
		
		WebElement table = driver.findElement(By.className("gridtable clear margin_top_10"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		int rwcount = rows.size();
		System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);
		
		// Iterate to get the value of each cell in table along with element
					// id
					int Rowno = 1;
					for (WebElement rowElement : rows) {
						List<WebElement> cols = rowElement.findElements(By.xpath("td"));
						int Columnno = 0;
						for (WebElement colElement : cols) {
							
							if(Rowno>1)
							{
							
							if (Columnno == 0) 
							{
								String srNo = rowElement.findElement(By.xpath("./td")).getText();
								mGenericWait();
								System.out.println("Sr. No. is :"+srNo);
							}
							if (Columnno == 2) 
							{
								String tlunholdername = rowElement.findElement(By.xpath("./td[2]")).getText();
								mGenericWait();
								System.out.println("holdername is :"+tlunholdername);
							//	String tlunholdername = rowElement.findElement(By.xpath("./td[2]")).getText();
								tlunholdername_list.add(tlunholdername);
								
								System.out.println("tlun holderlist"+tlunholdername_list);
							}
							if (Columnno == 2) 
							{
								String tlunholdername_age = rowElement.findElement(By.xpath("./td[2]")).getText();
								mGenericWait();
								System.out.println("holdername age :"+tlunholdername_age);
							
							}
							if (Columnno == 3) 
							{
								String tlunholdername_gender = rowElement.findElement(By.xpath("./td[2]")).getText();
								mGenericWait();
								System.out.println("holdername's gender :"+tlunholdername_gender);
									}
							if (Columnno == 4) 
							{
								String tlunholdername_mobile = rowElement.findElement(By.xpath("./td[2]")).getText();
								mGenericWait();
								System.out.println("holdername mobile :"+tlunholdername_mobile);
								tlunholdername_mobile_list.add(tlunholdername_mobile);
							
							}
						
							
							
							Columnno = Columnno + 1;
	}
						}
						Rowno = Rowno + 1;
	
					}
					tlun_mainholder_list.add(tlunholdername_list.get(0));
					
					System.out.println("----------------------------------------------------"+tlunholdername_list);
					}
	





////////print reprint LOI/////
public void PRINTREPRINT() 
{
	try{
		common.mOpenBrowser(mGetPropertyFromFile("browserName"));
		common.mGetURL(mGetPropertyFromFile("url"));
		common.selectUlb();
		common.departmentLogin(mGetPropertyFromFile("PIODeptLogin"),mGetPropertyFromFile("PIODeptPassword"));
		mNavigation("rti_linkId", "rti_PIOTransactionId","rti_PIOprintreprint");
		mCustomWait(1000);
		
		mSendKeys("id", mGetPropertyFromFile("RTI_loi_numbersearch_id"), loiNumber.get(CurrentinvoCount));
		mCustomWait(1000); 
		for(int k=0;k<2;k++)
		{
		mClick("css", mGetPropertyFromFile("LOI_printreprint_search")); 
		}
		mTakeScreenShot();
		mCustomWait(3000); 
		mClick("css", mGetPropertyFromFile("LOI_printview_id")); 
		mCustomWait(2000);
		String loi_form_RTINO=mGetText("id",mGetPropertyFromFile("loiformrtinumberid"),"value");
		System.out.println("loi_form_RTINO"+loi_form_RTINO);
		loi_form_RTINO_list.add(loi_form_RTINO);
		String loi_form_loiNO=mGetText("id",mGetPropertyFromFile("loiformnoloi_id"),"value");
		System.out.println("loi_form_loiNO"+loi_form_loiNO);
		loi_form_LOINO_list.add(loi_form_loiNO);
		String loi_form_applicanname=mGetText("id",mGetPropertyFromFile("loi_applicantname_id"),"value");
		System.out.println("loi_form_applicanname"+loi_form_applicanname);
		loi_form_applicantname_list.add(loi_form_applicanname);
		String loi_form_LOIAMOUNT=mGetText("id",mGetPropertyFromFile("loi_LOIAMOUNT_id"),"value");
		System.out.println("loi_form_LOIAMOUNT"+loi_form_LOIAMOUNT);
		loi_form_loiamount_list.add(loi_form_applicanname);
		mCustomWait(2000);
		mClick("css",mGetPropertyFromFile("loi_loiprintbtn_id"));
		mChallanPDFReader();
		api.PdfPatterns.PIOLOI(); 
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		throw new MainetCustomExceptions("Error in PRINT LOI  method");
	}
}
public void responseassertion()
{
	try{
		String thisMethodName11=Thread.currentThread().getStackTrace()[1].getMethodName();
		String thisMethodName1=thisMethodName11+". invocation count==>"+CurrentinvoCount;
		System.out.println("thisMethodName1=>"+thisMethodName1);
		//mAssert(RTIfirstappeallist_Date.get(CurrentinvoCount), RTIfirstappeal_Date.get(CurrentinvoCount), RTIfirstappeallist_Date.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_Date.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
		System.out.println("===============================");
		System.out.println("===============================");
		System.out.println("===============================");
	//mAssert(RTIApplication_orgind.get(CurrentinvoCount), RTIResponse_ApplicantName.get(CurrentinvoCount),  RTIApplication_orgind.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_Date.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	//mAssert(RTIApplication_orgname.get(CurrentinvoCount), RTIResponse_ApplicantName.get(CurrentinvoCount), "RTI application name is not matching with response rti application name");
	//mAssert(RTI_fullname_list.get(CurrentinvoCount), RTIResponse_ApplicantName.get(CurrentinvoCount),RTI_fullname_list.get(CurrentinvoCount)+"is not matching with"+RTIResponse_ApplicantName.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	mAssert(RTI_pincode_list.get(CurrentinvoCount), RTIResponse_Pincode.get(CurrentinvoCount),RTI_pincode_list.get(CurrentinvoCount)+"is not matching with"+RTIResponse_Pincode.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	mAssert(rti_subject_list.get(CurrentinvoCount), RTIResponse_Subject.get(CurrentinvoCount),rti_subject_list.get(CurrentinvoCount)+"is not matching with"+RTIResponse_Subject.get(CurrentinvoCount)+"of method==>"+thisMethodName1); 
	mAssert(RTIResponse_partiinfo.get(CurrentinvoCount), rti_rtiappdetail_list.get(CurrentinvoCount), rti_rtiappdetail_list.get(CurrentinvoCount)+"is not matching with"+RTIResponse_partiinfo.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	mAssert(rti_mediaType_list.get(CurrentinvoCount), RTIResponse_mediatype.get(CurrentinvoCount),rti_mediaType_list.get(CurrentinvoCount)+"is not matching with"+RTIResponse_mediatype.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	throw new Exception();
	}catch (Exception e) { 
		// TODO: handle exception
		e.printStackTrace();
	} 	  
}


public void firstappealassertion() {
	try{
		System.out.println("===========================firstappealassertion============================");
		String thisMethodName11=Thread.currentThread().getStackTrace()[1].getMethodName();
		String thisMethodName1=thisMethodName11+". invocation count=="+CurrentinvoCount;
		System.out.println("thisMethodName1=>"+thisMethodName1);
		//mAssert(RTIfirstappeallist_Date.get(CurrentinvoCount), RTIfirstappeal_Date.get(CurrentinvoCount), RTIfirstappeallist_Date.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_Date.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
		mAssert(RTIResponse_RTIapplicationno.get(CurrentinvoCount), RTIfirstappeal_RTIapplicationno.get(CurrentinvoCount), RTIResponse_RTIapplicationno.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_RTIapplicationno.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIResponse_Date.get(CurrentinvoCount), RTIfirstappeal_Date.get(CurrentinvoCount),  RTIResponse_Date.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_Date.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIResponse_TypeofApplicant.get(CurrentinvoCount), RTIfirstappeal_TypeofApplicant.get(CurrentinvoCount), RTIResponse_TypeofApplicant.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_TypeofApplicant.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		//mAssert(RTIResponse_ApplicantName.get(CurrentinvoCount), RTIfirstappeal_ApplicantName.get(CurrentinvoCount), RTIResponse_ApplicantName.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_ApplicantName.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIResponse_MobileNo.get(CurrentinvoCount), RTIfirstappeal_MobileNo.get(CurrentinvoCount), RTIResponse_MobileNo.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_MobileNo.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIResponse_Email.get(CurrentinvoCount), RTIfirstappeal_Email.get(CurrentinvoCount),RTIResponse_Email.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_Email.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIResponse_AlertS.get(CurrentinvoCount), RTIfirstappeal_AlertS.get(CurrentinvoCount),RTIResponse_AlertS.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_AlertS.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIResponse_bpl.get(CurrentinvoCount), RTIfirstappeal_bpl.get(CurrentinvoCount), RTIResponse_bpl.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_bpl.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		//mAssert(RTIResponse_Adddet.get(CurrentinvoCount), RTIfirstappeal_bpl.get(CurrentinvoCount), "RTI application name is not matching with response rti application name");
	
		mAssert(RTIResponse_Pincode.get(CurrentinvoCount), RTIfirstappeal_Pincode.get(CurrentinvoCount), RTIResponse_Pincode.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_Pincode.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIResponse_Subject.get(CurrentinvoCount), RTIfirstappeal_Subject.get(CurrentinvoCount), RTIResponse_Subject.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_Subject.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIResponse_partiinfo.get(CurrentinvoCount), RTIfirstappeal_partiinfo.get(CurrentinvoCount),  RTIResponse_partiinfo.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_partiinfo.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIResponse_mediatype.get(CurrentinvoCount), RTIfirstappeal_mediatype.get(CurrentinvoCount),  RTIResponse_mediatype.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_mediatype.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
	//	mAssert(RTIResponse_Circle.get(CurrentinvoCount), RTIfirstappeal_Circle.get(CurrentinvoCount),RTIResponse_Circle.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_Circle.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIResponse_action_application.get(CurrentinvoCount), RTIfirstappeal_action_application.get(CurrentinvoCount), RTIResponse_action_application.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_action_application.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIResponse_fullpartial.get(CurrentinvoCount), RTIfirstappeal_fullpartial.get(CurrentinvoCount), RTIResponse_fullpartial.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_fullpartial.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTI_response_status_list.get(CurrentinvoCount), RTIfirstappeal_status_list.get(CurrentinvoCount), RTI_response_status_list.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_status_list.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	throw new Exception(); 
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	} 
	
}

public void firstappeallistassertion() {
	try{
		System.out.println("===========================firstappeallistassertion============================");
		String thisMethodName11=Thread.currentThread().getStackTrace()[1].getMethodName();
		String thisMethodName1=thisMethodName11+". invocation count=="+CurrentinvoCount;
		System.out.println("thisMethodName1=>"+thisMethodName1);
		
		mAssert(RTIfirstappeallist_Date.get(CurrentinvoCount), RTIfirstappeal_Date.get(CurrentinvoCount), RTIfirstappeallist_Date.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_Date.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
    
		mAssert(RTIfirstappeallist_TypeofApplicant.get(CurrentinvoCount), RTIfirstappeal_TypeofApplicant.get(CurrentinvoCount),  RTIfirstappeallist_TypeofApplicant.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_TypeofApplicant.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		//mAssert(RTIfirstappeallist_ApplicantName.get(CurrentinvoCount), RTIfirstappeal_ApplicantName.get(CurrentinvoCount),  RTIfirstappeallist_ApplicantName.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_ApplicantName.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIfirstappeallist_MobileNo.get(CurrentinvoCount), RTIfirstappeal_MobileNo.get(CurrentinvoCount),  RTIfirstappeallist_MobileNo.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_MobileNo.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIfirstappeallist_Email.get(CurrentinvoCount), RTIfirstappeal_Email.get(CurrentinvoCount),RTIfirstappeallist_Email.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_Email.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIfirstappeallist_AlertS.get(CurrentinvoCount), RTIfirstappeal_AlertS.get(CurrentinvoCount),RTIfirstappeallist_AlertS.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_AlertS.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIfirstappeallist_bpl.get(CurrentinvoCount), RTIfirstappeal_bpl.get(CurrentinvoCount), RTIfirstappeallist_bpl.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_bpl.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
	//	mAssert(RTIfirstappeallist_Adddet.get(CurrentinvoCount), RTIfirstappeal_bpl.get(CurrentinvoCount),  RTIfirstappeallist_Adddet.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_bpl.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIfirstappeallist_Pincode.get(CurrentinvoCount), RTIfirstappeal_Pincode.get(CurrentinvoCount),  RTIfirstappeallist_Pincode.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_Pincode.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIfirstappeallist_Subject.get(CurrentinvoCount), RTIfirstappeal_Subject.get(CurrentinvoCount), RTIfirstappeallist_Subject.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_Subject.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIfirstappeallist_partiinfo.get(CurrentinvoCount), RTIfirstappeal_partiinfo.get(CurrentinvoCount), RTIfirstappeallist_partiinfo.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_partiinfo.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIfirstappeallist_mediatype.get(CurrentinvoCount), RTIfirstappeal_mediatype.get(CurrentinvoCount),RTIfirstappeallist_mediatype.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_mediatype.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIfirstappeallist_Circle.get(CurrentinvoCount), RTIfirstappeal_Circle.get(CurrentinvoCount),RTIfirstappeallist_Circle.get(CurrentinvoCount)+"is not matching with"+RTIfirstappeal_Circle.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	

	
		mAssert(RTIfirstappeallist_fullpartial.get(CurrentinvoCount), RTIfirstappeal_fullpartial.get(CurrentinvoCount), "RTI application name is not matching with "+thisMethodName1+" response rti application name");
	
		mAssert(RTIfirstappeallist_status_list.get(CurrentinvoCount), RTIfirstappeal_status_list.get(CurrentinvoCount), "RTI application name is not matching with "+thisMethodName1+ "response rti application name");
	 
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	} 
	
}

///hearing process assertion////
public void hearingassertion() {
	try{
		System.out.println("===========================hearingassertion============================");
		String thisMethodName11=Thread.currentThread().getStackTrace()[1].getMethodName();
		String thisMethodName1=thisMethodName11+"invocation count=="+CurrentinvoCount;
		System.out.println("thisMethodName1=>"+thisMethodName1);

		mAssert(RTIfirstappeallist_RTIapplicationno.get(CurrentinvoCount), RTIhearing_RTIapplicationno.get(CurrentinvoCount), RTIfirstappeallist_RTIapplicationno.get(CurrentinvoCount)+"is not matching with"+RTIhearing_RTIapplicationno.get(CurrentinvoCount)+"of method==>"+thisMethodName1);

		mAssert(RTIfirstappeallist_Date.get(CurrentinvoCount), RTIhearing_Date.get(CurrentinvoCount), RTIfirstappeallist_Date.get(CurrentinvoCount)+"is not matching with"+RTIhearing_Date.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
  
		mAssert(RTIfirstappeallist_TypeofApplicant.get(CurrentinvoCount), RTIhearing_TypeofApplicant.get(CurrentinvoCount), RTIfirstappeallist_TypeofApplicant.get(CurrentinvoCount)+"is not matching with"+RTIhearing_TypeofApplicant.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
	//	mAssert(RTIfirstappeallist_ApplicantName.get(CurrentinvoCount), RTIhearing_ApplicantName.get(CurrentinvoCount),  RTIfirstappeallist_ApplicantName.get(CurrentinvoCount)+"is not matching with"+RTIhearing_ApplicantName.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIfirstappeallist_MobileNo.get(CurrentinvoCount), RTIhearing_MobileNo.get(CurrentinvoCount),  RTIfirstappeallist_MobileNo.get(CurrentinvoCount)+"is not matching with"+RTIhearing_MobileNo.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIfirstappeallist_Email.get(CurrentinvoCount), RTIhearing_Email.get(CurrentinvoCount),  RTIfirstappeallist_Email.get(CurrentinvoCount)+"is not matching with"+RTIhearing_Email.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIfirstappeallist_AlertS.get(CurrentinvoCount), RTIhearing_AlertS.get(CurrentinvoCount),  RTIfirstappeallist_AlertS.get(CurrentinvoCount)+"is not matching with"+RTIhearing_AlertS.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIfirstappeallist_bpl.get(CurrentinvoCount), RTIhearing_bpl.get(CurrentinvoCount), RTIfirstappeallist_bpl.get(CurrentinvoCount)+"is not matching with"+RTIhearing_bpl.get(CurrentinvoCount)+"of method==>"+thisMethodName1);

		mAssert(RTIfirstappeallist_Adddet.get(CurrentinvoCount), RTIhearing_Adddet.get(CurrentinvoCount),  RTIfirstappeallist_Adddet.get(CurrentinvoCount)+"is not matching with"+RTIhearing_Adddet.get(CurrentinvoCount)+"of method==>"+thisMethodName1);

		mAssert(RTIfirstappeallist_Pincode.get(CurrentinvoCount), RTIhearing_Pincode.get(CurrentinvoCount),  RTIfirstappeallist_Pincode.get(CurrentinvoCount)+"is not matching with"+RTIhearing_Pincode.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIfirstappeallist_Subject.get(CurrentinvoCount), RTIhearing_Subject.get(CurrentinvoCount), RTIfirstappeallist_Subject.get(CurrentinvoCount)+"is not matching with"+RTIhearing_Subject.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIfirstappeallist_partiinfo.get(CurrentinvoCount), RTIhearing_partiinfo.get(CurrentinvoCount), RTIfirstappeallist_partiinfo.get(CurrentinvoCount)+"is not matching with"+RTIhearing_partiinfo.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		
		mAssert(RTIfirstappeallist_mediatype.get(CurrentinvoCount), RTIhearing_mediatype.get(CurrentinvoCount),  RTIfirstappeallist_mediatype.get(CurrentinvoCount)+"is not matching with"+RTIhearing_mediatype.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
		mAssert(RTIfirstappeallist_Circle.get(CurrentinvoCount), RTIhearing_Circle.get(CurrentinvoCount),  RTIfirstappeallist_Circle.get(CurrentinvoCount)+"is not matching with"+RTIhearing_Circle.get(CurrentinvoCount)+"of method==>"+thisMethodName1);
	
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace(); 
	} 
	
}

 ///////////////////////////////////////////rorbot//////
public void robot() {
	try{
		
		mCreateArtefactsFolder("RTI_");
		
		//		beforeRTIApplicationEntry();
		//		chllanForRTIServices = true;
		common.mOpenBrowser(mGetPropertyFromFile("browserName"));
		common.mGetURL(mGetPropertyFromFile("url"));
		common.selectUlb();
		mTakeScreenShot();
		//common.citizenLogin();
		//login(TypeOfExecution,deptUserName,deptPassword)
		common.login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
		
		System.out.println("===========================robot============================");
		
		
         //driver.findElement(By.xpath(".//a[@href=contains(text(),'yearly-calendar.xls')]")).click();	
         Robot robot = new Robot();  // Robot class throws AWT Exception	
         Thread.sleep(2000); // Thread.sleep throws InterruptedException	
         for (int i = 0; i <= i;) {
        /* robot.keyPress(KeyEvent.VK_DOWN);  // press arrow down key of keyboard to navigate and select Save radio button	
         robot.keyPress(KeyEvent.VK_DOWN);
         Thread.sleep(1000);  // sleep has only been used to showcase each event separately	
         robot.keyPress(KeyEvent.VK_TAB);	
         Thread.sleep(1000);	
         robot.keyPress(KeyEvent.VK_TAB);	
         Thread.sleep(1000);	
         robot.keyPress(KeyEvent.VK_TAB);	
         Thread.sleep(1000);	
         robot.keyPress(KeyEvent.VK_UP);
         robot.mouseWheel(5);
         robot.keyPress(KeyEvent.VK_ENTER);	*/
     // press enter key of keyboard to perform above selected action
        
        	 robot.mouseWheel(1);
        	  Thread.sleep(1000);
        	 
        	  robot.mouseWheel(2);
        	  Thread.sleep(100);
        	  robot.mouseWheel(1);
        	  Thread.sleep(1000);
        	  robot.mouseWheel(-1);
        	  Thread.sleep(1000);
        	 
        	  robot.mouseWheel(-2);
        	  Thread.sleep(100);
        	  robot.mouseWheel(-1);
        	  Thread.sleep(100);
        	  robot.mouseWheel(1);
        	  Thread.sleep(100);
        	 
        	  robot.mouseWheel(2);
        	  Thread.sleep(1000);
        	  robot.mouseWheel(1);
        	  Thread.sleep(100);
        	  robot.mouseWheel(-1);
        	  Thread.sleep(100);
        	 
        	  robot.mouseWheel(-2);
        	  Thread.sleep(100);
        	  robot.mouseWheel(-1);
        	  Thread.sleep(100);
        	  
        	 i++;
		}
		
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace(); 
	} 
	
}





}