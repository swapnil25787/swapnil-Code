package com.abm.mainet.audit;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.abm.mainet.tp.TPCustomErrorMessages;

import api.CommonUtilsAPI;
import common.CommonMethods;
import errorhandling.MainetCustomExceptions;
import excelreader.ExcelReader;
import excelreader.ExcelToProperties;

public class AuditServices extends CommonMethods{

	ExcelToProperties excelToProp = new ExcelToProperties();

	//	String   auditNumber;
	static String   memoNumber;
	static boolean editInformation = false;
	static boolean sendback = false;
	static boolean approved = false;	
	//	static boolean  objectionRaised = false;
	public static ArrayList<String> objectionRaised = new ArrayList<String>(); 
	public static ArrayList<String> MemoNo = new ArrayList<String>();

	ExcelReader ER = new ExcelReader();

	@BeforeSuite
	public void beforeSuite(){
		thisClassName=this.getClass().getCanonicalName();
		myClassName = thisClassName;
		mCreateModuleFolder("ADT_",myClassName);			
	}


	//1.Assign H.O.D.
	@Test(enabled=false)
	public void adt_AssignHOD() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AUDITCustomErrorMessages.audit_m_errors.entrySet().clear();
			assignHOD();
			CommonUtilsAPI.adtErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adt_AssignHOD method");
		}
	}


	//2.plan Audit
	@Test(enabled=false)
	public void adt_PlanAudit() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AUDITCustomErrorMessages.audit_m_errors.entrySet().clear();
			planAudit();
			CommonUtilsAPI.adtErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adt_PlanAudit method");
		}
	}


	//3.schedule Audit
	@Test(enabled=false)
	public void adt_ScheduleAudit() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AUDITCustomErrorMessages.audit_m_errors.entrySet().clear();
			scheduleAudit();
			CommonUtilsAPI.adtErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adt_ScheduleAudit method");
		}
	}


	//4.audit Memo
	@Test(enabled=false)
	public void adt_AuditMemo() throws InterruptedException,IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AUDITCustomErrorMessages.audit_m_errors.entrySet().clear();
			auditMemo("adt_AmAuditMemoStatusdata");
			CommonUtilsAPI.adtErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adt_AuditMemo method");
		}
	}


	//5.response to audit memo
	@Test(enabled=false)
	public void adt_ResponseToAuditMemo() throws InterruptedException,IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AUDITCustomErrorMessages.audit_m_errors.entrySet().clear();
			//		objectionRaised=true;
			responseToAuditMemo();
			CommonUtilsAPI.adtErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adt_ResponseToAuditMemo method");
		}
	}


	//6.response by HOD
	@Test(enabled=false)
	public void adt_ResponseByHOD() throws InterruptedException,IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AUDITCustomErrorMessages.audit_m_errors.entrySet().clear();
			responseByHOD("adt_HODOkay");
			CommonUtilsAPI.adtErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adt_ResponseByHOD method");
		}
	}


	//8. Response to audit memo after HOD Rejection
	@Test(enabled=false)
	public void adt_ResponseToAuditMemoAfterHODRejection() throws InterruptedException,IOException{

		try{

			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AUDITCustomErrorMessages.audit_m_errors.entrySet().clear();	
			responseToAuditMemoAftrHODRjctn();
			CommonUtilsAPI.adtErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adt_ResponseToAuditMemoAfterHODRejection method");
		}
	}


	//9. HOD Response after HOD Rejection
	@Test(enabled=false)
	public void adt_ResponseByHODAfterHODRejection() throws InterruptedException,IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AUDITCustomErrorMessages.audit_m_errors.entrySet().clear();
			responseByHODAftrHODRjctn();
			CommonUtilsAPI.adtErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adt_ResponseByHODAfterHODRejection method");
		}
	}


	//7.audit Memo after objection Raised
	@Test(enabled=false)
	public void adt_AuditMemoAfterObjectionRaised() throws InterruptedException,IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AUDITCustomErrorMessages.audit_m_errors.entrySet().clear();
			auditMemo("adt_AuditorOkay");
			CommonUtilsAPI.adtErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adt_AuditMemoAfterObjectionRaised method");
		}
	}


	// author: hiren kathiria 	
	//  response to audit memo, response by HOD(looping)
	@Test(enabled=false)
	public void adt_HodResp_resptoAuditMemoCombn() throws InterruptedException,IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AUDITCustomErrorMessages.audit_m_errors.entrySet().clear();
			HodResp_resptoAuditMemo();
			CommonUtilsAPI.adtErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adt_HodResp_resptoAuditMemoCombn method");
		}
	}

	// author: hiren kathiria 	
	//  response to audit memo, response by HOD(looping)
	@Test(enabled=false)
	public void adt_HodResp_AuditMemoCombn() throws InterruptedException,IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AUDITCustomErrorMessages.audit_m_errors.entrySet().clear();
			HodResp_AuditMemo();
			CommonUtilsAPI.adtErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adt_HodResp_AuditMemoCombn method");
		}
	}



	// author: hiren kathiria 	
	//  response to audit memo, response by HOD(looping)
	@Test(enabled=false)
	public void adt_HodResp_AuditMemoCombn_loop() throws InterruptedException,IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AUDITCustomErrorMessages.audit_m_errors.entrySet().clear();
			HodResp_AuditMemo_loop();
			CommonUtilsAPI.adtErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adt_HodResp_AuditMemoCombn_loop method");
		}
	}






	// author: hiren kathiria 	
	// to delete  assign HOD,Audit plan,schedule,Memo details
	@Test(enabled=false)
	public void adt_DeleteAuditplan_schedule_memo() throws InterruptedException,IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AUDITCustomErrorMessages.audit_m_errors.entrySet().clear();
			DeleteHodAuditplan_schedule_memo();
			CommonUtilsAPI.adtErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adt_DeleteAuditplan_schedule_memo method");
		}
	}




	//Assign H.O.D.
	public void assignHOD() throws InterruptedException, IOException
	{

		try{
			mCreateArtefactsFolder("ADT_");
			//		beforeAssignHOD();
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("adt_AsgnHODName"),mGetPropertyFromFile("adt_AsgnHodPassword"));
			AssignHOD();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();			
			throw new MainetCustomExceptions("Error in assignHOD method");
		}
	}

	//Plan Audit
	public void planAudit()  throws InterruptedException, IOException
	{
		try{
			mCreateArtefactsFolder("ADT_"); 
			//		beforePlanAudit();
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("adt_AsgnHODName"),mGetPropertyFromFile("adt_AsgnHodPassword"));
			PlanAudit();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();			
			throw new MainetCustomExceptions("Error in planAudit method");
		}
	}

	//Schedule Audit
	public void scheduleAudit() throws InterruptedException, IOException
	{
		try{
			mCreateArtefactsFolder("ADT_"); 
			//		beforeScheduleAudit();
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("adt_AsgnHODName"),mGetPropertyFromFile("adt_AsgnHodPassword"));
			ScheduleAudit();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();			
			throw new MainetCustomExceptions("Error in scheduleAudit method");
		}
	}

	//Audit Memo
	public void auditMemo(String data)throws InterruptedException, IOException
	{
		try{
			mCreateArtefactsFolder("ADT_");
			//		beforeAuditMemo();
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("adt_AsgnHODName"),mGetPropertyFromFile("adt_AsgnHodPassword"));
			AuditMemo(data);
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();			
			throw new MainetCustomExceptions("Error in auditMemo method");
		}
	}

	//Response To Audit Memo
	public void responseToAuditMemo()throws InterruptedException, IOException
	{
		try{

			if(objectionRaised.get(CurrentinvoCount).toString().equalsIgnoreCase("true"))
			{
				mCreateArtefactsFolder("ADT_");
				//			beforeResponseToAuditMemo();
				mOpenBrowser(mGetPropertyFromFile("browserName"));
				mGetURL(mGetPropertyFromFile("url"));
				selectUlb();
				departmentLogin(mGetPropertyFromFile("adt_AuditeeName"), mGetPropertyFromFile("adt_AuditeePassword"));
				ResponseToAuditMemo();
				logOut();
				finalLogOut();
				mCloseBrowser();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();			
			throw new MainetCustomExceptions("Error in responseToAuditMemo method");
		}
	}

	//HOD Response
	public void responseByHOD(String data)throws InterruptedException, IOException
	{
		try{

			if(objectionRaised.get(CurrentinvoCount).toString().equalsIgnoreCase("true"))
			{
				mCreateArtefactsFolder("ADT_");
				//			beforeResponseByHOD();
				mOpenBrowser(mGetPropertyFromFile("browserName"));
				mGetURL(mGetPropertyFromFile("url"));
				selectUlb();
				departmentLogin(mGetPropertyFromFile("adt_HODName"), mGetPropertyFromFile("adt_HODPassword"));
				ResponseByHOD(data);
				editInformation = true;
				logOut();
				finalLogOut();
				mCloseBrowser();			
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();			
			throw new MainetCustomExceptions("Error in responseByHOD method");
		}
	}

	//Response to audit memo after HOD Rejection
	public void responseToAuditMemoAftrHODRjctn()throws InterruptedException, IOException
	{
		try{			
			if(sendback)
			{	
				mCreateArtefactsFolder("ADT_");
				//			beforeResponseToAuditMemoAftrHODRjctn();
				mOpenBrowser(mGetPropertyFromFile("browserName"));
				mGetURL(mGetPropertyFromFile("url"));
				selectUlb();
				departmentLogin(mGetPropertyFromFile("adt_AuditeeName"), mGetPropertyFromFile("adt_AuditeePassword"));
				ResponseToAuditMemo();
				logOut();
				finalLogOut();
				mCloseBrowser();			
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();			
			throw new MainetCustomExceptions("Error in responseToAuditMemoAftrHODRjctn method");
		}

	}

	//HOD Response after HOD Rejection
	public void responseByHODAftrHODRjctn()throws InterruptedException, IOException
	{
		try{
			if(approved)
			{			
				mCreateArtefactsFolder("ADT_");
				//			beforeResponseByHODAftrHODRjctn();
				mOpenBrowser(mGetPropertyFromFile("browserName"));
				mGetURL(mGetPropertyFromFile("url"));
				selectUlb();
				departmentLogin(mGetPropertyFromFile("adt_HODName"),mGetPropertyFromFile("adt_HODPassword"));
				ResponseByHOD("adt_HODOkay");
				logOut();		
				finalLogOut();
				mCloseBrowser();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();			
			throw new MainetCustomExceptions("Error in responseByHODAftrHODRjctn method");
		}
	}

	//author hiren kathiria
	// to delete  assign HOD,Audit plan,schedule,Memo details
	public void DeleteHodAuditplan_schedule_memo()throws InterruptedException, IOException
	{
		try{
			mCreateArtefactsFolder("ADT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("adt_AsgnHODName"),mGetPropertyFromFile("adt_AsgnHodPassword"));
			delHodAuditplan_schedule_memo();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();			
			throw new MainetCustomExceptions("Error in DeleteHodAuditplan_schedule_memo method");
		}
	}


	//author hiren kathiria	
	//  response to audit memo, response by HOD and Auditor's response 
	public void HodResp_resptoAuditMemo()throws InterruptedException, IOException
	{
		try{

			int count=Integer.parseInt(mGetPropertyFromFile("adt_HODCount"));

			if(objectionRaised.get(CurrentinvoCount).toString().equalsIgnoreCase("true")) 
			{	

				for(int i=0;i<count;i++)
				{						
					if((i==0 && count==0) || i==count)
					{
						mCreateArtefactsFolder("ADT_");					
						responseToAuditMemo();
						responseByHOD("adt_HODOkay");
						auditMemo("adt_AuditorOkay");
						editInformation =false;
						System.out.println("value of i is : "+i); 
					}						
					else 
					{
						mCreateArtefactsFolder("ADT_");	
						System.out.println("value of i is : "+i); 
						responseToAuditMemo();								
						responseByHOD("adt_HODNotOkay");	
					}						
				}						
			}	
		}

		catch(Exception e)
		{
			e.printStackTrace();			
			throw new MainetCustomExceptions("Error in HodResp_resptoAuditMemo method");
		}
	}


	//author hiren kathiria
	//  response by HOD and Auditor's response(looping) 
	public void HodResp_AuditMemo()throws InterruptedException, IOException
	{
		try{

			int count=Integer.parseInt(mGetPropertyFromFile("adt_AuditorCount"));

			if(objectionRaised.get(CurrentinvoCount).toString().equalsIgnoreCase("true")) 
			{		
				{	

					for(int i=0;i<count;i++)
					{						
						if((i==0 && count==0) || i==count)
						{
							mCreateArtefactsFolder("ADT_");					
							responseToAuditMemo();
							responseByHOD("adt_HODOkay");
							auditMemo("adt_AuditorOkay");
							editInformation =false;
						}						
						else 
						{
							mCreateArtefactsFolder("ADT_");	
							responseToAuditMemo();								
							responseByHOD("adt_HODOkay");
							auditMemo("adt_AuditorNotOkay");										
						}						
					}						
				}	
			}	
		}

		catch(Exception e)
		{
			e.printStackTrace();			
			throw new MainetCustomExceptions("Error in HodResp_AuditMemo method");
		}
	}





	//author hiren kathiria done on 6/12/2016
	//  response by HOD and Auditor's response(multiplelooping) 
	public void HodResp_AuditMemo_loop()throws InterruptedException, IOException
	{
		try{

			HODReply1=mGetPropertyFromFile("adt_HODReply1").split(",");
			HODReply2=mGetPropertyFromFile("adt_HODReply2").split(",");
			HODReply3=mGetPropertyFromFile("adt_HODReply3").split(",");
			AuditorReply=mGetPropertyFromFile("adt_AuditorReply").split(",");

				
					
					if(AuditorReply.length<4) 
					{	
						for(int i=0;i<AuditorReply.length;i++)
						{								
							if(AuditorReply[i].equalsIgnoreCase("yes"))
							{	
								auditMemo("adt_AuditorOkay");
								editInformation =false;								
								break;								
							}
							else
							{
								auditMemo("adt_AuditorNotOkay");	
							}

							if(i==0)
							{
								for(int a=0;a<HODReply1.length;a++)
								{
									responseToAuditMemo();									
									if(HODReply1[a].equalsIgnoreCase("yes"))
									{
										responseByHOD("adt_HODOkay");
									}
									else
									{
										responseByHOD("adt_HODNotOkay");	
									}
								}	
							}
							else if(i==1)
							{
								for(int a=0;a<HODReply2.length;a++)
								{
									responseToAuditMemo();									
									if(HODReply2[a].equalsIgnoreCase("yes"))
									{
										responseByHOD("adt_HODOkay");
									}
									else
									{
										responseByHOD("adt_HODNotOkay");	
									}
								}	
							}

							else if(i==2)
							{
								for(int a=0;a<HODReply3.length;a++)
								{
									responseToAuditMemo();									
									if(HODReply3[a].equalsIgnoreCase("yes"))
									{
										responseByHOD("adt_HODOkay");
									}
									else
									{
										responseByHOD("adt_HODNotOkay");	
									}
								}	
							}	
							
							else
							{
								System.out.println("out of inner for loop of HodResp_AuditMemo_loop"); 
							}
						}						
					}					


					else
					{
						System.out.println("the required count is greater than 3");
					}
				
			}	
	

		catch(Exception e)
		{
			e.printStackTrace();			
			throw new MainetCustomExceptions("Error in HodResp_AuditMemo_loop method");
		}
	}

	// method to assign HOD
	public void AssignHOD() throws InterruptedException
	{
		try {

			//Navigate Audit Assign HOD Link				 		
			mWaitForVisible("linkText", mGetPropertyFromFile("ADT_AuditLinkid"));
			mNavigation("ADT_AuditLinkid","ADT_MasterLinkid","ADT_AssignHODLinkid");
			mGenericWait();
			mTakeScreenShot();
			mCustomWait(1000);

			//to Add Assign Hod 

			if(mGetPropertyFromFile("ADT_AhAddHod").equalsIgnoreCase("Yes") && mGetPropertyFromFile("ADT_AhEditHod").equalsIgnoreCase("No"))
			{

				//Add Link
				mWaitForVisible("linkText", mGetPropertyFromFile("ADT_AhAddHodLinkid"));
				mCustomWait(1000);
				mClick("linkText", mGetPropertyFromFile("ADT_AhAddHodLinkid"));
				mCustomWait(1500);

				if(mGetPropertyFromFile("ADT_AhCheckMandatory").equalsIgnoreCase("No")) 
				{

					//select department
					mWaitForVisible("id", mGetPropertyFromFile("ADT_AhDeptAtAssignHODid"));
					mCustomWait(1000);
					mSelectDropDown("id", mGetPropertyFromFile("ADT_AhDeptAtAssignHODid"), mGetPropertyFromFile("ADT_AhDeptAtAssignHODdata"));
					mCustomWait(1000);

					//Employee name					
					mCustomWait(1000);
					mSelectDropDown("id", mGetPropertyFromFile("ADT_AhEmployeeNameid"), mGetPropertyFromFile("ADT_AhEmployeeNamedata"));
					mCustomWait(1000);

					// selecting From date
					mDateSelect("id",mGetPropertyFromFile("ADT_AhFromDateid"),mGetPropertyFromFile("ADT_AhFromDatedata"));
					mCustomWait(1000);

					// selecting To Date
					mDateSelect("id",mGetPropertyFromFile("ADT_AhToDateid"),mGetPropertyFromFile("ADT_AhToDatedata"));
					mCustomWait(1000);

					mTakeScreenShot();
					mCustomWait(1000);

					mClick("css",mGetPropertyFromFile("ADT_AhFormSubBtnid"));

					mWaitForVisible("id", mGetPropertyFromFile("ADT_AhFormProcdBtnid"));
					mCustomWait(1000);
					mTakeScreenShot();
					mCustomWait(1000);
					String msg = mGetText("css", mGetPropertyFromFile("ADT_AssignHODPrcdMsgid"));
					mAssert(msg, mGetPropertyFromFile("ADT_AssignHODPrcdMsgdata"), "Actual  : "+msg+"   Expected   : "+mGetPropertyFromFile("ADT_AssignHODPrcdMsgdata"));
					mCustomWait(1000);

					//proceed Button
					mClick("id", mGetPropertyFromFile("ADT_AhFormProcdBtnid"));
					mCustomWait(1500);
					mTakeScreenShot();
					mCustomWait(1000);
				}

				else{

					mCustomWait(1500);
					mClick("css",mGetPropertyFromFile("ADT_AhFormSubBtnid"));

					//error msg Box
					mCustomWait(1000);  
					mWaitForVisible("css",mGetPropertyFromFile("ADT_AhErrorPopUpBoxAssertMsgid"));

					String FancyMsg = mGetText("css", mGetPropertyFromFile("ADT_AhErrorPopUpBoxAssertMsgid"));
					mTakeScreenShot();
					mCustomWait(1000);
					System.out.println(FancyMsg);
					mAssert(FancyMsg, mGetPropertyFromFile("ADT_AhErrorPopUpBoxAssertMsgdata"),"Actual   :"+FancyMsg+"   Expected   :"+mGetPropertyFromFile("ADT_AhErrorPopUpBoxAssertMsgdata"));
					System.out.println("error is " +mGetPropertyFromFile("ADT_AhErrorPopUpBoxAssertMsgdata"));
					mCustomWait(1000);					
				}

			}

			else if(mGetPropertyFromFile("ADT_AhEditHod").equalsIgnoreCase("Yes") && mGetPropertyFromFile("ADT_AhAddHod").equalsIgnoreCase("No"))
			{
				mCustomWait(1000);

				//to edit Assign Hod 

				mSelectDropDown("id", mGetPropertyFromFile("adt_PaAuditNoSearchid"), mGetPropertyFromFile("adt_PaAuditNoSearchdata"));

				mCustomWait(1000);
				mClick("linkText", mGetPropertyFromFile("adt_PaSearchBtnid"));

				mWaitForVisible("xpath", mGetPropertyFromFile("adt_PaViewDetailsIconid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_PaViewDetailsIconid"));

				mWaitForVisible("xpath", mGetPropertyFromFile("adt_PaEditDetailsIconid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_PaEditDetailsIconid"));

				mWaitForVisible("css", mGetPropertyFromFile("adt_PaPopUpBoxid"));
				mCustomWait(1000);

				mTakeScreenShot();

				//selecting department
				mWaitForVisible("id", mGetPropertyFromFile("adt_PaDeptid"));
				mCustomWait(1000);
				mSelectDropDown("id",  mGetPropertyFromFile("adt_PaDeptid"), mGetPropertyFromFile("adt_EditPaDeptdata"));
				mCustomWait(1000);

				// selecting From date
				mDateSelect("id",mGetPropertyFromFile("adt_PaAuditPlanFrmDateid"), mGetPropertyFromFile("adt_EditPaAuditPlanFrmDatedata"));
				mCustomWait(1000);

				// selecting To Date				
				mDateSelect("id",mGetPropertyFromFile("adt_PaAuditPlanToDateid"), mGetPropertyFromFile("adt_EditPaAuditPlanToDatedata"));
				mCustomWait(1000);				
				mTakeScreenShot();

				mWaitForVisible("xpath", mGetPropertyFromFile("adt_PaAuditPlanSubBtnid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_PaAuditPlanSubBtnid"));

				//Fancy Box
				mCustomWait(1000);  
				mWaitForVisible("css",mGetPropertyFromFile("adt_PaFancyBoxAtPlanSubid"));

				mCustomWait(1000);
				String FancyMsg = mGetText("css", mGetPropertyFromFile("adt_PaFancyBoxAssertMsgid"));
				mTakeScreenShot();
				mCustomWait(1000);
				System.out.println(FancyMsg);
				mAssert(FancyMsg, mGetPropertyFromFile("adt_PaFancyBoxAssertMsgdata"),"Actual   :"+FancyMsg+"   Expected   :"+mGetPropertyFromFile("adt_PaFancyBoxAssertMsgdata"));
				mCustomWait(1000);	
				mClick("css",mGetPropertyFromFile("adt_PaFancyBoxAtPlanSubid"));

				//Final submit button
				mWaitForVisible("css", mGetPropertyFromFile("adt_PaFinalSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("adt_PaFinalSubBtnid"));
				mCustomWait(1000);
				mGenericWait();

				//Proceed button
				mWaitForVisible("id", mGetPropertyFromFile("adt_PaPlanAuditProcdBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("adt_PaPlanAuditProcdBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
			}


			else if(mGetPropertyFromFile("ADT_AhDeleteHod").equalsIgnoreCase("Yes"))
			{
				mCustomWait(1000);

				//to delete Assign Hod 

				mSelectDropDown("id", mGetPropertyFromFile("adt_PaAuditNoSearchid"), mGetPropertyFromFile("adt_PaAuditNoSearchdata"));

				mCustomWait(1000);
				mClick("linkText", mGetPropertyFromFile("adt_PaSearchBtnid"));

				mWaitForVisible("xpath", mGetPropertyFromFile("adt_PaViewDetailsIconid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_PaViewDetailsIconid"));

				mWaitForVisible("xpath", mGetPropertyFromFile("adt_PaDeleteIconid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_PaDeleteIconid"));

				mWaitForVisible("css", mGetPropertyFromFile("adt_PaDeletePopUpBoxid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("adt_PaDeleteBtnid"));

				mWaitForVisible("css", mGetPropertyFromFile("adt_PaDeletePopUpBoxid"));
				mCustomWait(1000);

				String FancyMsg = mGetText("css", mGetPropertyFromFile("adt_PaAfterDelAssertMsgid"));
				mTakeScreenShot();
				mCustomWait(1000);
				System.out.println(FancyMsg);
				mAssert(FancyMsg, mGetPropertyFromFile("adt_PaAfterDelAssertMsgdata"),"Actual   :"+FancyMsg+"   Expected   :"+mGetPropertyFromFile("adt_PaAfterDelAssertMsgdata"));
				mCustomWait(1000);	
				mClick("css",mGetPropertyFromFile("adt_PaDeletePopUpBoxid"));
				mCustomWait(1000);
				mTakeScreenShot();

				//Final submit button
				mWaitForVisible("css", mGetPropertyFromFile("adt_PaFinalSubBtnid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("adt_PaFinalSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();

				//Proceed button
				mWaitForVisible("id", mGetPropertyFromFile("adt_PaPlanAuditProcdBtnid"));
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("adt_PaPlanAuditProcdBtnid"));
				mCustomWait(2000);
				mTakeScreenShot();		
			}

			else
			{
				System.out.println("No further action can be performed as both Add and Edit flag are either true or false"); 
			}		

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in AssignHOD script");
		}
	}

	//method to plan Audit
	public void PlanAudit()
	{
		try
		{
			//navigate to Plan Audit Link
			mWaitForVisible("linkText", mGetPropertyFromFile("adt_PaAuditLinkid"));
			mNavigation(("adt_PaAuditLinkid"),("adt_PaTransactionLinkid"),("adt_PaAuditPlanLinkid"));
			mCustomWait(1000);

			if(mGetPropertyFromFile("adt_AuditPlanAdd").equalsIgnoreCase("Yes") && mGetPropertyFromFile("adt_AuditPlanEdit").equalsIgnoreCase("No"))
			{							 	
				mCustomWait(1000);
				mTakeScreenShot();
				//Add Button
				mWaitForVisible("linkText", mGetPropertyFromFile("adt_PaAddBtnid"));
				mCustomWait(1000);
				mClick("linkText", mGetPropertyFromFile("adt_PaAddBtnid"));

				//Add Details Link
				mWaitForVisible("linkText", mGetPropertyFromFile("adt_PaAddDetailsLinkid"));
				mCustomWait(1000);
				mClick("linkText", mGetPropertyFromFile("adt_PaAddDetailsLinkid"));

				mCustomWait(1500);
				mTakeScreenShot();

				if(mGetPropertyFromFile("adt_AuditPlanCheckMandatory").equalsIgnoreCase("No")) 
				{		

					//selecting department
					mWaitForVisible("id", mGetPropertyFromFile("adt_PaDeptid"));
					mCustomWait(1000);
					mSelectDropDown("id",  mGetPropertyFromFile("adt_PaDeptid"), mGetPropertyFromFile("adt_PaDeptdata"));
					mCustomWait(1000);

					// selecting From date					
					mDateSelect("id",mGetPropertyFromFile("adt_PaAuditPlanFrmDateid"),mGetPropertyFromFile("adt_PaAuditPlanFrmDatedata"));
					mCustomWait(1000);

					// selecting To Date			
					mDateSelect("id",mGetPropertyFromFile("adt_PaAuditPlanToDateid"),mGetPropertyFromFile("adt_PaAuditPlanToDatedata"));
					mCustomWait(1000);					

					mSendKeys("id",mGetPropertyFromFile("adt_PaAuditPlanRefNoid"),mGetPropertyFromFile("adt_PaAuditPlanRefNodata"));
					mCustomWait(1000);					
					mTakeScreenShot();

					mWaitForVisible("xpath", mGetPropertyFromFile("adt_PaAuditPlanSubBtnid"));
					mCustomWait(1000);
					mClick("xpath", mGetPropertyFromFile("adt_PaAuditPlanSubBtnid"));

					//Fancy Box
					mCustomWait(1000);  
					mWaitForVisible("css",mGetPropertyFromFile("adt_PaFancyBoxAtPlanSubid"));

					mCustomWait(1000);
					String FancyMsg = mGetText("css", mGetPropertyFromFile("adt_PaFancyBoxAssertMsgid"));
					mTakeScreenShot();
					mCustomWait(1000);
					System.out.println(FancyMsg);
					mAssert(FancyMsg, mGetPropertyFromFile("adt_PaFancyBoxAssertMsgdata"),"Actual   :"+FancyMsg+"   Expected   :"+mGetPropertyFromFile("adt_PaFancyBoxAssertMsgdata"));
					mCustomWait(1000);	
					mClick("css",mGetPropertyFromFile("adt_PaFancyBoxAtPlanSubid"));

					//Final submit button
					mWaitForVisible("css", mGetPropertyFromFile("adt_PaFinalSubBtnid"));
					mCustomWait(1000);
					mTakeScreenShot();
					mCustomWait(1000);
					mClick("css", mGetPropertyFromFile("adt_PaFinalSubBtnid"));
					mCustomWait(1000);
					mGenericWait();

					String msgAtFinalSub = mGetText("css", mGetPropertyFromFile("adt_PaFinalSubAssertMsgid"));			
					String numberOnly= msgAtFinalSub.replaceAll("[^0-9]", "");
					mAppNoArray(numberOnly);

					//			auditNumber = numberOnly;
					//			System.out.println(auditNumber);
					System.out.println("audit no is "+appNo);

					//Proceed button
					mWaitForVisible("id", mGetPropertyFromFile("adt_PaPlanAuditProcdBtnid"));
					mCustomWait(1000);
					mTakeScreenShot();
					mCustomWait(1000);
					mClick("id", mGetPropertyFromFile("adt_PaPlanAuditProcdBtnid"));
					mCustomWait(1000);
					mTakeScreenShot();
				}

				else{

					mWaitForVisible("xpath", mGetPropertyFromFile("adt_PaAuditPlanSubBtnid"));
					mCustomWait(1000);
					mClick("xpath", mGetPropertyFromFile("adt_PaAuditPlanSubBtnid"));

					//error msg Box
					mCustomWait(1000);  
					mWaitForVisible("css",mGetPropertyFromFile("adt_PaErrorPopUpBoxAssertMsgid"));

					String FancyMsg = mGetText("css", mGetPropertyFromFile("adt_PaErrorPopUpBoxAssertMsgid"));
					mTakeScreenShot();
					mCustomWait(1000);
					System.out.println(FancyMsg);
					mAssert(FancyMsg, mGetPropertyFromFile("adt_PaErrorPopUpBoxAssertMsgdata"),"Actual   :"+FancyMsg+"   Expected   :"+mGetPropertyFromFile("adt_PaErrorPopUpBoxAssertMsgdata"));
					System.out.println(mGetPropertyFromFile("adt_PaErrorPopUpBoxAssertMsgdata"));
					mCustomWait(1000);	
					mClick("css",mGetPropertyFromFile("adt_PaPopUpCloseid"));
					mCustomWait(1500);	
				}

			}


			else if(mGetPropertyFromFile("adt_AuditPlanEdit").equalsIgnoreCase("Yes") && mGetPropertyFromFile("adt_AuditPlanAdd").equalsIgnoreCase("No"))
			{
				mCustomWait(1000);

				//to edit Audit Plan Details 

				mSelectDropDown("id", mGetPropertyFromFile("adt_PaAuditNoSearchid"), mGetPropertyFromFile("adt_PaAuditNoSearchdata"));

				mCustomWait(1000);
				mClick("linkText", mGetPropertyFromFile("adt_PaSearchBtnid"));

				mWaitForVisible("xpath", mGetPropertyFromFile("adt_PaViewDetailsIconid"));
				mTakeScreenShot();
				mCustomWait(1000);

				mClick("xpath", mGetPropertyFromFile("adt_PaViewDetailsIconid"));

				mWaitForVisible("xpath", mGetPropertyFromFile("adt_PaEditDetailsIconid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_PaEditDetailsIconid"));

				mWaitForVisible("css", mGetPropertyFromFile("adt_PaPopUpBoxid"));
				mCustomWait(1000);
				mTakeScreenShot();

				//selecting department
				mWaitForVisible("id", mGetPropertyFromFile("adt_PaDeptid"));
				mCustomWait(1000);
				mSelectDropDown("id",  mGetPropertyFromFile("adt_PaDeptid"), mGetPropertyFromFile("adt_EditPaDeptdata"));
				mCustomWait(1000);

				// selecting From date
				mDateSelect("id",mGetPropertyFromFile("adt_PaAuditPlanFrmDateid"),mGetPropertyFromFile("adt_EditPaAuditPlanFrmDatedata") );
				mCustomWait(1000);

				// selecting To Date				
				mDateSelect("id",mGetPropertyFromFile("adt_PaAuditPlanToDateid"),mGetPropertyFromFile("adt_EditPaAuditPlanToDatedata") );
				mCustomWait(1000);			
				mTakeScreenShot();

				mWaitForVisible("xpath", mGetPropertyFromFile("adt_PaAuditPlanSubBtnid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_PaAuditPlanSubBtnid"));

				//Fancy Box
				mCustomWait(1000);  
				mWaitForVisible("css",mGetPropertyFromFile("adt_PaFancyBoxAtPlanSubid"));

				mCustomWait(1000);
				String FancyMsg = mGetText("css", mGetPropertyFromFile("adt_PaFancyBoxAssertMsgid"));
				mTakeScreenShot();
				mCustomWait(1000);
				System.out.println(FancyMsg);
				mAssert(FancyMsg, mGetPropertyFromFile("adt_PaFancyBoxAssertMsgdata"),"Actual   :"+FancyMsg+"   Expected   :"+mGetPropertyFromFile("adt_PaFancyBoxAssertMsgdata"));
				mCustomWait(1000);	
				mClick("css",mGetPropertyFromFile("adt_PaFancyBoxAtPlanSubid"));

				//Final submit button
				mWaitForVisible("css", mGetPropertyFromFile("adt_PaFinalSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("adt_PaFinalSubBtnid"));
				mCustomWait(1000);

				//Proceed button
				mWaitForVisible("id", mGetPropertyFromFile("adt_PaPlanAuditProcdBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("adt_PaPlanAuditProcdBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
			}


			else if(mGetPropertyFromFile("adt_AuditPlanDelete").equalsIgnoreCase("Yes"))
			{
				mCustomWait(1000);

				//to delete Audit Plan Details 

				mSelectDropDown("id", mGetPropertyFromFile("adt_PaAuditNoSearchid"), mGetPropertyFromFile("adt_PaAuditNoSearchdata"));

				mCustomWait(1000);
				mClick("linkText", mGetPropertyFromFile("adt_PaSearchBtnid"));

				mWaitForVisible("xpath", mGetPropertyFromFile("adt_PaViewDetailsIconid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_PaViewDetailsIconid"));

				mWaitForVisible("xpath", mGetPropertyFromFile("adt_PaDeleteIconid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_PaDeleteIconid"));

				mWaitForVisible("css", mGetPropertyFromFile("adt_PaDeletePopUpBoxid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("adt_PaDeleteBtnid"));

				mWaitForVisible("css", mGetPropertyFromFile("adt_PaDeletePopUpBoxid"));
				mCustomWait(1000);

				String FancyMsg = mGetText("css", mGetPropertyFromFile("adt_PaAfterDelAssertMsgid"));
				mTakeScreenShot();
				mCustomWait(1000);
				System.out.println(FancyMsg);
				mAssert(FancyMsg, mGetPropertyFromFile("adt_PaAfterDelAssertMsgdata"),"Actual   :"+FancyMsg+"   Expected   :"+mGetPropertyFromFile("adt_PaAfterDelAssertMsgdata"));
				mCustomWait(1000);	
				mClick("css",mGetPropertyFromFile("adt_PaDeletePopUpBoxid"));
				mCustomWait(1000);
				mTakeScreenShot();

				//Final submit button
				mWaitForVisible("css", mGetPropertyFromFile("adt_PaFinalSubBtnid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("adt_PaFinalSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();

				//Proceed button
				mWaitForVisible("id", mGetPropertyFromFile("adt_PaPlanAuditProcdBtnid"));
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("adt_PaPlanAuditProcdBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();		
			}

			else
			{
				System.out.println("No further action can be performed as both Add and Edit flag are either true or false"); 
			}		

		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in PlanAudit script");
		}

	}

	// Schedule Audit
	public void ScheduleAudit()
	{
		try
		{
			// navigate Schedule Audit Link
			mWaitForVisible("linkText", mGetPropertyFromFile("adt_SaAuditLinkid"));
			mNavigation(("adt_SaAuditLinkid"),("adt_SaTransactionsLinkid"),("adt_SaAuditScheduleLinkid"));

			//Search Audit Number
			mWaitForVisible("id", mGetPropertyFromFile("adt_SaAutoTxtSearchAppid"));
			mCustomWait(2000);
			//			mSelectDropDown("id",  mGetPropertyFromFile("adt_SaAutoTxtSearchAppid"), auditNumber);
			IndOrDep("id", "adt_SaAutoTxtSearchAppid", "applicationNo");

			mTakeScreenShot();
			mCustomWait(1000);

			//Search Button
			mWaitForVisible("linkText", mGetPropertyFromFile("adt_SaSearchAppBtnid"));
			mCustomWait(1000);
			mClick("linkText", mGetPropertyFromFile("adt_SaSearchAppBtnid"));

			//View Details
			mWaitForVisible("xpath", mGetPropertyFromFile("adt_SaViewAppImgid"));
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("adt_SaViewAppImgid"));

			//getting text
			mWaitForVisible("xpath", mGetPropertyFromFile("adt_SaNoAtScheduleAuditid"));
			mTakeScreenShot();
			mCustomWait(1000);
			String auditNo = mGetText("xpath", mGetPropertyFromFile("adt_SaNoAtScheduleAuditid"));
			mCustomWait(1000);
			//			mAssert(auditNo, auditNumber, "Actual  :"+auditNo+" Expected  :"+auditNumber);
			mAssert(auditNo, appNo, "Actual  :"+auditNo+" Expected  :"+appNo);
			mTakeScreenShot();

			if(mGetPropertyFromFile("adt_AuditScheduleAdd").equalsIgnoreCase("Yes") && mGetPropertyFromFile("adt_AuditScheduleEdit").equalsIgnoreCase("No"))
			{
				mCustomWait(1000);
				//Add Details Link
				mWaitForVisible("linkText", mGetPropertyFromFile("adt_SaAddDetailsLinkAtsaid"));
				mCustomWait(1000);
				mClick("linkText", mGetPropertyFromFile("adt_SaAddDetailsLinkAtsaid"));

				// selecting from date
				mWaitForVisible("id", mGetPropertyFromFile("adt_SaFromDateid"));
				mCustomWait(1000);

				mDateSelect("id",mGetPropertyFromFile("adt_SaFromDateid"),mGetPropertyFromFile("adt_SaFromDatedata"));
				mCustomWait(1000);

				// selecting To Date
				mDateSelect("id",mGetPropertyFromFile("adt_SaToDateid"),mGetPropertyFromFile("adt_SaToDatedata"));
				mCustomWait(1000);

				//Selecting Auditor name
				mSelectDropDown("id", mGetPropertyFromFile("adt_SaAuditornameid"), mGetPropertyFromFile("adt_SaAuditornamedata"));

				//reference Number
				mWaitForVisible("id", mGetPropertyFromFile("adt_SaRefNumid"));
				mCustomWait(1000);
				mSendKeys("id",  mGetPropertyFromFile("adt_SaRefNumid"), mGetPropertyFromFile("adt_SaRefNumdata"));

				//description
				mWaitForVisible("id", mGetPropertyFromFile("adt_SaDescriptionid"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("adt_SaDescriptionid"), mGetPropertyFromFile("adt_SaDescriptiondata"));

				//upload files
				mCustomWait(1000);
				mUpload("id", mGetPropertyFromFile("adt_SaAuditorSchdleUploadid"), mGetPropertyFromFile("adt_AuditorSchdleUploaddata"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);

				//pop up submit
				mWaitForVisible("css", mGetPropertyFromFile("adt_SaAuditorScheduleSubBtnid"));
				mCustomWait(1000);
				mClick("css",  mGetPropertyFromFile("adt_SaAuditorScheduleSubBtnid"));

				//fancy box
				mWaitForVisible("css", mGetPropertyFromFile("adt_SaAuditorSchedleFancyBoxSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);

				String msgAtSchdlPlnSub = mGetText("css", mGetPropertyFromFile("adt_SaSchdlePlnPopUpSubAssertid"));
				mAssert(msgAtSchdlPlnSub, mGetPropertyFromFile("adt_SaSchdlePlnPopUpSubAssertdata"), "Actual  :"+msgAtSchdlPlnSub+"  Expected  :"+mGetPropertyFromFile("adt_SaSchdlePlnPopUpSubAssertdata"));
				mCustomWait(1000);

				System.out.println(msgAtSchdlPlnSub);
				mClick("css", mGetPropertyFromFile("adt_SaAuditorSchedleFancyBoxSubBtnid"));

				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);

				//final submit button
				mWaitForVisible("css", mGetPropertyFromFile("adt_SaAuditorSchedleAftrSubInputBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("adt_SaAuditorSchedleAftrSubInputBtnid"));

				//Proceed Button
				mWaitForVisible("id", mGetPropertyFromFile("adt_SaAuditorSchdleProcdBtnid"));
				mCustomWait(1000);

				String msgAtFinlSubBtn = mGetText("css", mGetPropertyFromFile("adt_SaSchdlePlnFinalSubAssertid"));
				mAssert(msgAtFinlSubBtn, mGetPropertyFromFile("adt_SaSchdlePlnFinalSubAssertdata"), " Actual  :"+msgAtFinlSubBtn+"  Expected  :"+ mGetPropertyFromFile("adt_SaSchdlePlnFinalSubAssertdata"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("adt_SaAuditorSchdleProcdBtnid"));
				mCustomWait(1000);
				System.out.println(msgAtFinlSubBtn);
				mTakeScreenShot();
			}

			else if(mGetPropertyFromFile("adt_AuditScheduleEdit").equalsIgnoreCase("Yes") && mGetPropertyFromFile("adt_AuditScheduleAdd").equalsIgnoreCase("No"))
			{
				mCustomWait(1000);

				// to Edit Audit Schedule Details 

				mWaitForVisible("xpath", mGetPropertyFromFile("adt_SaEditAppImgid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_SaEditAppImgid"));

				// selecting from date
				mDateSelect("id",mGetPropertyFromFile("adt_SaFromDateid"),mGetPropertyFromFile("adt_EditSaFromDatedata"));
				mCustomWait(1000);

				// selecting To Date
				mDateSelect("id",mGetPropertyFromFile("adt_SaToDateid"),mGetPropertyFromFile("adt_EditSaToDatedata"));
				mCustomWait(1000);

				//Selecting Auditor name
				mSelectDropDown("id", mGetPropertyFromFile("adt_SaAuditornameid"), mGetPropertyFromFile("adt_EditSaAuditornamedata"));

				//reference Number
				mCustomWait(1000);
				mSendKeys("id",  mGetPropertyFromFile("adt_SaRefNumid"), mGetPropertyFromFile("adt_EditSaRefNumdata"));

				//description
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("adt_SaDescriptionid"), mGetPropertyFromFile("adt_EditSaDescriptiondata"));

				// to remove uploaded file
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_SaAuditorSchdleDeleteUploadFileid"));

				//upload files
				mCustomWait(1000);
				mUpload("id", mGetPropertyFromFile("adt_SaAuditorSchdleUploadid"), mGetPropertyFromFile("adt_EditAuditorSchdleUploaddata"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);

				//pop up submit
				mWaitForVisible("css", mGetPropertyFromFile("adt_SaAuditorScheduleSubBtnid"));
				mCustomWait(1000);
				mClick("css",  mGetPropertyFromFile("adt_SaAuditorScheduleSubBtnid"));

				//fancy box
				mWaitForVisible("css", mGetPropertyFromFile("adt_SaAuditorSchedleFancyBoxSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);

				String msgAtSchdlPlnSub = mGetText("css", mGetPropertyFromFile("adt_SaSchdlePlnPopUpSubAssertid"));
				mAssert(msgAtSchdlPlnSub, mGetPropertyFromFile("adt_SaSchdlePlnPopUpSubAssertdata"), "Actual  :"+msgAtSchdlPlnSub+"  Expected  :"+mGetPropertyFromFile("adt_SaSchdlePlnPopUpSubAssertdata"));
				mCustomWait(1000);

				System.out.println(msgAtSchdlPlnSub);
				mClick("css", mGetPropertyFromFile("adt_SaAuditorSchedleFancyBoxSubBtnid"));

				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);

				//final submit button
				mWaitForVisible("css", mGetPropertyFromFile("adt_SaAuditorSchedleAftrSubInputBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("adt_SaAuditorSchedleAftrSubInputBtnid"));

				//Proceed Button
				mWaitForVisible("id", mGetPropertyFromFile("adt_SaAuditorSchdleProcdBtnid"));
				mCustomWait(1000);

				String msgAtFinlSubBtn = mGetText("css", mGetPropertyFromFile("adt_SaSchdlePlnFinalSubAssertid"));
				mAssert(msgAtFinlSubBtn, mGetPropertyFromFile("adt_SaSchdlePlnFinalSubAssertdata"), " Actual  :"+msgAtFinlSubBtn+"  Expected  :"+ mGetPropertyFromFile("adt_SaSchdlePlnFinalSubAssertdata"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("adt_SaAuditorSchdleProcdBtnid"));
				mCustomWait(1000);
				System.out.println(msgAtFinlSubBtn);	  
				mTakeScreenShot();
			}

			if(mGetPropertyFromFile("adt_AuditScheduleDelete").equalsIgnoreCase("Yes"))
			{
				mCustomWait(1000);

				// to delete Audit Schedule Details 

				mWaitForVisible("xpath", mGetPropertyFromFile("adt_SaDeleteIconid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_SaDeleteIconid"));

				mWaitForVisible("css", mGetPropertyFromFile("adt_SaDeletePopUpBoxid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("adt_SaDeleteBtnid"));

				mWaitForVisible("css", mGetPropertyFromFile("adt_SaDeletePopUpBoxid"));
				mCustomWait(1000);

				String FancyMsg = mGetText("css", mGetPropertyFromFile("adt_SaAfterDelAssertMsgid"));
				mTakeScreenShot();
				mCustomWait(1000);
				System.out.println(FancyMsg);
				mAssert(FancyMsg, mGetPropertyFromFile("adt_SaAfterDelAssertMsgdata"),"Actual   :"+FancyMsg+"   Expected   :"+mGetPropertyFromFile("adt_SaAfterDelAssertMsgdata"));
				mCustomWait(1000);	
				mClick("css",mGetPropertyFromFile("adt_SaDeletePopUpBoxid"));
				mCustomWait(1000);
				mTakeScreenShot();

				//Final submit button
				mWaitForVisible("css", mGetPropertyFromFile("adt_SaAuditorSchedleAftrSubInputBtnid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("adt_SaAuditorSchedleAftrSubInputBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();

				//Proceed button
				mWaitForVisible("id", mGetPropertyFromFile("adt_SaAuditorSchdleProcdBtnid"));
				mCustomWait(1000);

				String msgAtFinlSubBtn = mGetText("css", mGetPropertyFromFile("adt_SaSchdlePlnFinalSubAssertid"));
				mAssert(msgAtFinlSubBtn, mGetPropertyFromFile("adt_SaSchdlePlnFinalSubAssertdata"), " Actual  :"+msgAtFinlSubBtn+"  Expected  :"+ mGetPropertyFromFile("adt_SaSchdlePlnFinalSubAssertdata"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("adt_SaAuditorSchdleProcdBtnid"));
				mCustomWait(1000);
				System.out.println(msgAtFinlSubBtn);
				mTakeScreenShot();
			}

			else
			{
				System.out.println("No further action can be performed as both Add and Edit flag are either true or false");
			}

		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in ScheduleAudit script");
		}
	}

	//Audit Memo
	public void AuditMemo(String data)
	{
		try
		{
			//navigate audit memo Link 
			mWaitForVisible("linkText",mGetPropertyFromFile("adt_AmAuditLinkid"));
			mNavigation(("adt_AmAuditLinkid"),("adt_AmTransactionsLinkid"),("adt_AmAuditMemoLinkid"));

			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);

			//Searching Audit Application
			mWaitForVisible("css",mGetPropertyFromFile("adt_AmSearchAuditNoIconid"));
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("adt_AmSearchAuditNoIconid"));

			//Sending application Number
			mWaitForVisible("id", mGetPropertyFromFile("adt_AmSearchAuditByTextid"));
			mCustomWait(1000);

			//			mSendKeys("id",mGetPropertyFromFile("adt_AmSearchAuditByTextid"), auditNumber);
			IndOrDep("id", "adt_AmSearchAuditByTextid", "applicationNo");
			//mSendKeys("id",mGetPropertyFromFile("adt_AmSearchAuditByTextid"), "35");

			Robot robot=new Robot();
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);			

			mTakeScreenShot();
			mCustomWait(1000);

			//fancy search button
			mWaitForVisible("id", mGetPropertyFromFile("adt_AmSearchAuditAtFancyBtnid"));
			mCustomWait(1000);
			mClick("id", mGetPropertyFromFile("adt_AmSearchAuditAtFancyBtnid"));

			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);

			//View Details Img Link
			mWaitForVisible("xpath", mGetPropertyFromFile("adt_AmViewDetailsImgid"));
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("adt_AmViewDetailsImgid"));

			//getting audit Number
			String auditNoAtMemo = mGetText("css", mGetPropertyFromFile("adt_AmAuditNoAtAuditMemoid"));
			mCustomWait(1000);

			//			mAssert(auditNoAtMemo, auditNumber," Actual  :"+ auditNoAtMemo+"  Expected  :"+auditNumber);
			mAssert(auditNoAtMemo, appNo," Actual  :"+ auditNoAtMemo+"  Expected  :"+appNo);
			System.out.println(auditNoAtMemo);
			mCustomWait(1000);

			//Edit Information
			if(editInformation)
			{
				//Edit Memo Details
				mCustomWait(1000);
				mWaitForVisible("xpath", mGetPropertyFromFile("adt_AmEditMemoDetailsid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_AmEditMemoDetailsid"));

				//selecting audit status
				mWaitForVisible("id", mGetPropertyFromFile("adt_AmAuditMemoComStatusid"));
				mCustomWait(1000);
				//				mSelectDropDown("id", mGetPropertyFromFile("adt_AmAuditMemoComStatusid"), mGetPropertyFromFile("adt_AmAuditMemoComStatusData"));
				mSelectDropDown("id", mGetPropertyFromFile("adt_AmAuditMemoComStatusid"), mGetPropertyFromFile(data));				


				//sending audit status description
				mWaitForVisible("id", mGetPropertyFromFile("adt_AmAuditMemoDescrptnid"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("adt_AmAuditMemoDescrptnid"), mGetPropertyFromFile("adt_AmAuditMemoDescrptnData"));

				//save button
				mWaitForVisible("xpath", mGetPropertyFromFile("adt_AmAuditMemoDescrptnSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_AmAuditMemoDescrptnSubBtnid"));

				//Proceed Button
				mWaitForVisible("css", mGetPropertyFromFile("adt_AmAuditMemoDescrptnMsgCloseid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				String auditReMemoSubMsg = mGetText("css", mGetPropertyFromFile("adt_AmAuditMemoDescrptnPopMsgid"));
				mCustomWait(1000);
				mAssert(auditReMemoSubMsg, mGetPropertyFromFile("adt_AmAuditMemoDescrptnPopMsgData"),"Msg at audit memo details submit:::: Actual : "+auditReMemoSubMsg+" Expected : "+mGetPropertyFromFile("adt_AmAuditMemoDescrptnPopMsgData"));
				System.out.println(auditReMemoSubMsg);
				mClick("css", mGetPropertyFromFile("adt_AmAuditMemoDescrptnMsgCloseid"));

				//final submit
				mWaitForVisible("xpath", mGetPropertyFromFile("adt_AmAuditMemoDescrPtnFinalSubid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_AmAuditMemoDescrPtnFinalSubid"));

				//PopUp Button
				mWaitForVisible("id", mGetPropertyFromFile("adt_AmAuditDesFinalPopUpBtnid"));
				mCustomWait(1000);
				String FinalAuditMemoPopMsg = mGetText("css", mGetPropertyFromFile("adt_AmAuditDesFinalPopAssertMsgid"));
				mCustomWait(1000);
				mAssert(FinalAuditMemoPopMsg, mGetPropertyFromFile("adt_AmAuditDesFinalPopAssertMsgData"), "Msg at Audit memo final submit:::: Actual : "+FinalAuditMemoPopMsg+" Expected : "+mGetPropertyFromFile("adt_AmAuditDesFinalPopAssertMsgData"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				System.out.println(FinalAuditMemoPopMsg);
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("adt_AmAuditDesFinalPopUpBtnid"));
				mCustomWait(1000);

			}

			else
			{		
				// to add Audit Memo details

				AddAuditMemo(data);

				if(mGetPropertyFromFile("adt_AmAuditMemodelete").equalsIgnoreCase("Yes"))
				{
					mCustomWait(1000);

					// to delete Audit Memo list 

					mWaitForVisible("xpath", mGetPropertyFromFile("adt_AmDeleteIconid"));
					mCustomWait(1000);
					mClick("xpath", mGetPropertyFromFile("adt_AmDeleteIconid"));

					mWaitForVisible("css", mGetPropertyFromFile("adt_AmDeletePopUpBoxid"));
					mCustomWait(1000);
					mClick("css", mGetPropertyFromFile("adt_AmDeleteBtnid"));

					mWaitForVisible("css", mGetPropertyFromFile("adt_AmDeletePopUpBoxid"));
					mCustomWait(1000);

					String FancyMsg = mGetText("css", mGetPropertyFromFile("adt_AmAfterDelAssertMsgid"));
					mTakeScreenShot();
					mCustomWait(1000);
					System.out.println(FancyMsg);
					mAssert(FancyMsg, mGetPropertyFromFile("adt_AmAfterDelAssertMsgdata"),"Actual   :"+FancyMsg+"   Expected   :"+mGetPropertyFromFile("adt_AmAfterDelAssertMsgdata"));
					mCustomWait(1000);	
					mClick("css",mGetPropertyFromFile("adt_AmDeletePopUpBoxid"));
					mCustomWait(1000);
					mTakeScreenShot();	

					AddAuditMemo(data);
				}


				// final submit button
				mWaitForVisible("xpath",mGetPropertyFromFile("adt_AmAuditMemoAppSubBtnid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_AmAuditMemoAppSubBtnid"));

				// Proceed Button
				mWaitForVisible("id",mGetPropertyFromFile("adt_AmAuditMemoAppProcdBtnid"));
				mCustomWait(1000);

				String msgAtAuditMemoFinlSub = mGetText("css",mGetPropertyFromFile("adt_AmAuditMemoProcdAssertMsgid"));
				mAssert(msgAtAuditMemoFinlSub,mGetPropertyFromFile("adt_AmAuditMemoProcdAssertMsgdata")," Actual  :"+ msgAtAuditMemoFinlSub+ " Expected  :"+ mGetPropertyFromFile("adt_AmAuditMemoProcdAssertMsgdata"));
				mCustomWait(1000);
				System.out.println(msgAtAuditMemoFinlSub);

				mClick("id", mGetPropertyFromFile("adt_AmAuditMemoAppProcdBtnid"));
				mCustomWait(2000);

				// getting memo number
				// Searching Audit Application
				mWaitForVisible("css",mGetPropertyFromFile("adt_AmSearchAuditNoIconid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("adt_AmSearchAuditNoIconid"));

				// Sending application Number
				mWaitForVisible("id",mGetPropertyFromFile("adt_AmSearchAuditByTextid"));
				mCustomWait(1000);

				// mSendKeys("id",mGetPropertyFromFile("adt_AmSearchAuditByTextid"),auditNumber);
				IndOrDep("id", "adt_AmSearchAuditByTextid", "applicationNo");
				// mSendKeys("id",mGetPropertyFromFile("adt_AmSearchAuditByTextid"),"26");
				mTab("id", mGetPropertyFromFile("adt_AmSearchAuditByTextid"));
				mTakeScreenShot();
				mCustomWait(1000);

				// fancy search button
				mWaitForVisible("id",mGetPropertyFromFile("adt_AmSearchAuditAtFancyBtnid"));
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("adt_AmSearchAuditAtFancyBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();

				// View Details Img Link
				mWaitForVisible("xpath",mGetPropertyFromFile("adt_AmViewDetailsImgid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_AmViewDetailsImgid"));
				System.out.println("hello");
				mGenericWait();
				mTakeScreenShot();

				memoNumber = mGetText("xpath",mGetPropertyFromFile("adt_AmMemoNumberAtPageid"));
				MemoNo.add(memoNumber);
				//				mAppNoArray("xpath",mGetPropertyFromFile("adt_AmMemoNumberAtPageid"));

				mCustomWait(1000);
				System.out.println("memo number is" + memoNumber);			
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in AuditMemo script");
		}
	}

	//Response to audit memo
	public void ResponseToAuditMemo()
	{
		try
		{
			//navigate to Response To Audit Memo Link
			mWaitForVisible("linkText", mGetPropertyFromFile("adt_RaAuditLinkid"));
			mNavigation(("adt_RaAuditLinkid"),("adt_RaTransactionsLinkid"),("adt_RaResponseToAuditMemoLinkid"));
			mCustomWait(2000);

			//Grid table search icon
			mWaitForVisible("css", mGetPropertyFromFile("adt_RaAppSearchIconid"));
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("adt_RaAppSearchIconid"));

			mCustomWait(1000);
			mWaitForVisible("css", mGetPropertyFromFile("adt_RaSearchAppByMemoNoid"));
			mSelectDropDown("css", mGetPropertyFromFile("adt_RaSearchAppByMemoNoid"), mGetPropertyFromFile("adt_RaSearchAppByMemoNodata"));

			//sending Application Number
			mWaitForVisible("id", mGetPropertyFromFile("adt_RaAppSearchTextid"));
			mCustomWait(1000);
			//			mSendKeys("id", mGetPropertyFromFile("adt_RaAppSearchTextid"), memoNumber);
			//mSendKeys("id", mGetPropertyFromFile("adt_RaAppSearchTextid"), "31");

			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{ 
				mSendKeys("id", mGetPropertyFromFile("adt_RaAppSearchTextid"), MemoNo.get(CurrentinvoCount).trim());	
			}
			else
			{
				mSendKeys("id", mGetPropertyFromFile("adt_RaAppSearchTextid"), mGetPropertyFromFile("applicationNo").trim());	
			}

			Robot robot=new Robot();
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			mCustomWait(1000);
			mTakeScreenShot();			

			//fancy Box search icon
			mWaitForVisible("id", mGetPropertyFromFile("adt_RaAppSearchFancyBoxBtnid"));
			mCustomWait(1000);
			mClick("id", mGetPropertyFromFile("adt_RaAppSearchFancyBoxBtnid"));
			mCustomWait(1000);

			//view Details img link
			mWaitForVisible("xpath", mGetPropertyFromFile("adt_RaAppViewDetailsImgid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("adt_RaAppViewDetailsImgid"));

			//getting memo number
			memoNumber =  driver.findElement(By.id(mGetPropertyFromFile("adt_RaMemoNoAtResponseid"))).getAttribute("value");
			mCustomWait(1000);
			System.out.println("Captured Memo Number is :"+memoNumber);

			//downloading file for verification
			mWaitForVisible("css", mGetPropertyFromFile("adt_RaDownloadDocid"));
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("adt_RaDownloadDocid"));
			mCustomWait(1000);

			mDownloadFile(myClassName);
			mCustomWait(1000);
			mSwitchTabs();
			mCustomWait(1000);

			//Reply Link
			mWaitForVisible("linkText", mGetPropertyFromFile("adt_RaReplyLinkid"));
			mCustomWait(1000);
			mClick("linkText", mGetPropertyFromFile("adt_RaReplyLinkid"));

			//Response
			mWaitForVisible("id", mGetPropertyFromFile("adt_RaResByAuditeeid"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("adt_RaResByAuditeeid"), mGetPropertyFromFile("adt_RaResByAuditeedata"));

			//uploading file
			mCustomWait(1000);
			mUpload("id", mGetPropertyFromFile("adt_RaUploadDocid"), mGetPropertyFromFile("adt_RaUploadDocdata"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);

			//submit Button
			mWaitForVisible("xpath", mGetPropertyFromFile("adt_RaResByAuditeeSubBtnid"));
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("adt_RaResByAuditeeSubBtnid"));

			//Waiting for Fancy Close
			mWaitForVisible("css", mGetPropertyFromFile("adt_RaResByAuditeeFancyCloseid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);

			String msgAtResToAuditPopUp = mGetText("css", mGetPropertyFromFile("adt_RamsgAtResToAuditFancyPopUpid"));
			mAssert(msgAtResToAuditPopUp, mGetPropertyFromFile("adt_RamsgAtResToAuditFancyPopUpdata"), "  Actual  :"+msgAtResToAuditPopUp+"   Expected   :"+mGetPropertyFromFile("adt_RamsgAtResToAuditFancyPopUpdata"));
			System.out.println(msgAtResToAuditPopUp);
			mCustomWait(1000);

			mClick("css", mGetPropertyFromFile("adt_RaResByAuditeeFancyCloseid"));

			//Final submit button
			mWaitForVisible("css", mGetPropertyFromFile("adt_RaResByAuditeFinalSubBtnid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("adt_RaResByAuditeFinalSubBtnid"));

			//Proceed Button
			mWaitForVisible("id", mGetPropertyFromFile("adt_RaResByAuditeProcdBtnid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);

			String msgAtFinalSubOfRes = mGetText("css", mGetPropertyFromFile("adt_RaResByAuditeAssertPopid"));
			mAssert(msgAtFinalSubOfRes, mGetPropertyFromFile("adt_RaResByAuditeAssertPopdata"), " Actual  :"+msgAtFinalSubOfRes+"   Expected   :"+mGetPropertyFromFile("adt_RaResByAuditeAssertPopdata"));
			System.out.println(msgAtFinalSubOfRes);
			mCustomWait(1000);

			mClick("id", mGetPropertyFromFile("adt_RaResByAuditeProcdBtnid"));
			mCustomWait(1000);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in ResponseToAuditMemo script");
		}
	}

	// Response by H.O.D.
	public void ResponseByHOD(String data)
	{
		try
		{
			// navigate to Response By HOD Link
			mWaitForVisible("linkText", mGetPropertyFromFile("adt_RhAuditLinkid"));
			mNavigation(("adt_RhAuditLinkid"),("adt_RhTransactionsLinkid"),("adt_RhHODResponseLinkid"));

			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);

			//Grid search icon
			mWaitForVisible("css", mGetPropertyFromFile("adt_RhSearchAppIconid"));
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("adt_RhSearchAppIconid"));

			//sending memo number
			mWaitForVisible("id", mGetPropertyFromFile("adt_RhSearchAppTextid"));
			mCustomWait(1000);
			//			mSendKeys("id", mGetPropertyFromFile("adt_RhSearchAppTextid"), memoNumber);
			mSendKeys("id", mGetPropertyFromFile("adt_RhSearchAppTextid"), MemoNo.get(CurrentinvoCount));
			//			IndOrDep("id", "adt_RhSearchAppTextid", "applicationNo");

			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);

			//grid find button
			mWaitForVisible("id", mGetPropertyFromFile("adt_RhFinalSearchAtIconid"));
			mCustomWait(1000);
			mClick("id", mGetPropertyFromFile("adt_RhFinalSearchAtIconid"));

			//View Details img icon
			mWaitForVisible("xpath", mGetPropertyFromFile("adt_RhViewDetailsImgIconid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("adt_RhViewDetailsImgIconid"));

			mCustomWait(1000);
			String memoNum = driver.findElement(By.id(mGetPropertyFromFile("adt_RhMemoNoAtResByHODid"))).getAttribute("value");
			mCustomWait(1000);
			//			mAssert(memoNum, memoNumber, "Memo Number at response by HOD:::: Actual :"+memoNum+" Expected :"+memoNumber);
			mAssert(memoNum, MemoNo.get(CurrentinvoCount), "Memo Number at response by HOD:::: Actual :"+memoNum+" Expected :"+MemoNo.get(CurrentinvoCount));
			//			mAssert(memoNum, appNo, "Memo Number at response by HOD:::: Actual :"+memoNum+" Expected :"+appNo);

			mCustomWait(1000);
			String docDownload = mGetText("css", mGetPropertyFromFile("adt_RhDownloadDocid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);
			System.out.println(docDownload);
			mCustomWait(1000);
			//mClick("linkText", docDownload);
			driver.findElement(By.linkText(docDownload)).click();
			mGenericWait();
			mCustomWait(1000);
			mDownloadFile(myClassName);
			mSwitchTabs();

			//Reply Link
			mWaitForVisible("linkText", mGetPropertyFromFile("adt_RhReplyLinkid"));
			mCustomWait(1000);
			mClick("linkText", mGetPropertyFromFile("adt_RhReplyLinkid"));

			//Audit Status
			mWaitForVisible("id", mGetPropertyFromFile("adt_RhAuditStatusid"));
			mCustomWait(1000);

			mSelectDropDown("id", mGetPropertyFromFile("adt_RhAuditStatusid"), mGetPropertyFromFile(data));


			/*			//acceptance or rejection of application at HOD
			if(approved)
			{
				mSelectDropDown("id", mGetPropertyFromFile("adt_RhAuditStatusid"), mGetPropertyFromFile("adt_RhAuditApprovedStatusData"));
				objectionRaised=false;
			}
			else
			{			
			    mSelectDropDown("id", mGetPropertyFromFile("adt_RhAuditStatusid"), mGetPropertyFromFile("adt_RhAuditStatusData"));
			}
			 */			

			/*			String SendBack =mCaptureSelectedValue("id",mGetPropertyFromFile("adt_RhAuditStatusid"));

			if (SendBack.equalsIgnoreCase("Send Back")) {
				sendback = true;
			}
			 */						
			//Response By HOD
			mWaitForVisible("id", mGetPropertyFromFile("adt_RhResponseByHODid"));
			mCustomWait(1000);

			mSendKeys("id", mGetPropertyFromFile("adt_RhResponseByHODid"), mGetPropertyFromFile("adt_RhResponseByHODData"));


			/*			if(approved)
			{
				mSendKeys("id", mGetPropertyFromFile("adt_RhResponseByHODid"), mGetPropertyFromFile("adt_RhResponseApprovedByHODData"));
			}

			else
			{	
			   mSendKeys("id", mGetPropertyFromFile("adt_RhResponseByHODid"), mGetPropertyFromFile("adt_RhResponseByHODData"));
			}
			 */			
			//File Upload
			mCustomWait(1000);
			mUpload("id", mGetPropertyFromFile("adt_RhUploadDocid"),mGetPropertyFromFile("adt_RhUploadDocData"));
			mCustomWait(1000);

			//Response Details Submit
			mWaitForVisible("xpath", mGetPropertyFromFile("adt_RhDetailsSubBtnid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("adt_RhDetailsSubBtnid"));
			mCustomWait(1000);

			mWaitForVisible("css", mGetPropertyFromFile("adt_RhDetailsPopCloseid"));
			mCustomWait(1000);
			String HODResPopUpMsg = mGetText("css", mGetPropertyFromFile("adt_RhDetailsSubPopUpMsgid"));
			mCustomWait(1000);
			mAssert(HODResPopUpMsg, mGetPropertyFromFile("adt_RhDetailsSubPopUpMsgData"),"Msg at HOD Response Details:::: Actual : "+HODResPopUpMsg+" Expected : "+mGetPropertyFromFile("adt_RhDetailsSubPopUpMsgData"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);
			System.out.println(HODResPopUpMsg);
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("adt_RhDetailsPopCloseid"));

			//Response By HOD final submit
			mGenericWait();
			mWaitForVisible("css",mGetPropertyFromFile("adt_RhResByHODFinalSubBtnid"));
			mCustomWait(1000);

			//final Submit Button
			mWaitForVisible("css", mGetPropertyFromFile("adt_RhResByHODFinalSubBtnid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("adt_RhResByHODFinalSubBtnid"));

			//Proceed Button
			mWaitForVisible("id", mGetPropertyFromFile("adt_RhResByHODSubPopUpid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);
			String ResFinalPopUpMsgid = mGetText("css", mGetPropertyFromFile("adt_RhResByHODFinalPopAssertMsgid"));
			mCustomWait(1000);
			mAssert(ResFinalPopUpMsgid, mGetPropertyFromFile("adt_RhResByHODFinalPopAssertMsgData"), "Msg at Final submit of Response by HOD:::: Actual :"+ResFinalPopUpMsgid+" Expected : "+mGetPropertyFromFile("adt_RhResByHODFinalPopAssertMsgData"));
			mCustomWait(1000);
			System.out.println(ResFinalPopUpMsgid);
			mCustomWait(1000);
			mClick("id", mGetPropertyFromFile("adt_RhResByHODSubPopUpid"));
			mCustomWait(1000);
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in ResponseByHOD script");
		}
	}

	// to add Audit Memo details

	public void AddAuditMemo(String data) {

		try{
			mCustomWait(1000);
			// getting department Name
			String deptNameAtMemo = mGetText("xpath",mGetPropertyFromFile("adt_AmDeptNmeAtAuditMemoid"));
			System.out.println(deptNameAtMemo);

			// from date
			String fromDate = mGetText("xpath",mGetPropertyFromFile("adt_AmFrmDateAtAuditMemoid"));
			System.out.println(fromDate);
			mCustomWait(1000);

			// To date
			String toDate = mGetText("xpath",mGetPropertyFromFile("adt_AmToDateAtAuditMemoid"));
			System.out.println(toDate);

			// downloading file
			mClick("css", mGetPropertyFromFile("adt_AmDwnldFileAtAuditMemoid"));
			mDownloadFile(myClassName);
			mCustomWait(1000);
			mSwitchTabs();

			mCustomWait(1000);

			// Add Details Link
			mWaitForVisible("linkText",mGetPropertyFromFile("adt_AmAddDetailsLinkid"));
			mCustomWait(1000);
			mClick("linkText", mGetPropertyFromFile("adt_AmAddDetailsLinkid"));
			mCustomWait(1000);

			// selecting memo date

			mWaitForVisible("id", mGetPropertyFromFile("adt_AmMemoDateid"));
			mDateSelect("id",mGetPropertyFromFile("adt_AmMemoDateid"),mGetPropertyFromFile("adt_AmMemoDatedata"));
			mCustomWait(1000);

			// selecting auditee name
			mSelectDropDown("id", mGetPropertyFromFile("adt_AmAudtieeNameid"),mGetPropertyFromFile("adt_AmAudtieeNamedata"));
			mCustomWait(1000);

			// Selecting audit status
			mSelectDropDown("id",mGetPropertyFromFile("adt_AmAuditMemoStatusid"),mGetPropertyFromFile(data));
			mCustomWait(2000);

			String IfObjection =mCaptureSelectedValue("id",mGetPropertyFromFile("adt_AmAuditMemoStatusid"));

			if (IfObjection.equals("Objection Raised")) {
				objectionRaised.add("true");				
			}
			else
			{
				objectionRaised.add("false");
				//auditMemoStatusCompleted=true;
			}

			// Description
			mSendKeys("id",mGetPropertyFromFile("adt_AmAuditMemoDescriptionid"),mGetPropertyFromFile("adt_AmAuditMemoDescriptiondata"));
			mCustomWait(1000);
			mSendKeys("id",mGetPropertyFromFile("adt_AmAuditMemoRemarksid"),mGetPropertyFromFile("adt_AmAuditMemoRemarksdata"));


			/*			// Remarks
			if (objectionRaised) {
				mWaitForVisible("id",mGetPropertyFromFile("adt_AmAuditMemoRemarksid"));
				mCustomWait(1000);
				mSendKeys("id",mGetPropertyFromFile("adt_AmAuditMemoRemarksid"),mGetPropertyFromFile("adt_AmAuditMemoRemarksdata"));
			}
			 */


			// uploading files			
			mUpload("id", mGetPropertyFromFile("adt_AmAuditMemoUploadid"),mGetPropertyFromFile("adt_AmAuditMemoUploaddata"));

			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);

			// save Button
			mWaitForVisible("xpath",mGetPropertyFromFile("adt_AmAuditMemoDetailsSaveid"));
			mCustomWait(1000);
			mClick("xpath",mGetPropertyFromFile("adt_AmAuditMemoDetailsSaveid"));

			// getting audit memo save message
			mWaitForVisible("css",mGetPropertyFromFile("adt_AmAuditMemoSaveFancyPopUpid"));
			String msgAftrAudtMemoSve = mGetText("css",mGetPropertyFromFile("adt_AmAuditMemoSaveAssertMsgid"));
			mAssert(msgAftrAudtMemoSve,mGetPropertyFromFile("adt_AmAuditMemoSaveAssertMsgdata")," Actual  :"+msgAftrAudtMemoSve+ " Expected  :"+mGetPropertyFromFile("adt_AmAuditMemoSaveAssertMsgdata"));
			mCustomWait(1000);
			System.out.println(msgAftrAudtMemoSve);

			// closing Fancy Popup
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);
			mClick("css",mGetPropertyFromFile("adt_AmAuditMemoSaveFancyPopUpid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);	

			// Actual Dates to be passed
			// selecting Start Date
			mWaitForVisible("id",mGetPropertyFromFile("adt_AmAuditMemoStartDateid"));
			mCustomWait(1000);
			mDateSelect("id",mGetPropertyFromFile("adt_AmAuditMemoStartDateid"),mGetPropertyFromFile("adt_AmAuditMemoStartDatedata"));
			mCustomWait(1000);

			// selecting End Date			
			mDateSelect("id",mGetPropertyFromFile("adt_AmAuditMemoEndDateid"),mGetPropertyFromFile("adt_AmAuditMemoEndDatedata"));
			mCustomWait(1000);		
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in AddAuditMemo script");
		}

	}	



	// to delete  assign HOD,Audit plan,schedule,Memo details

	public void delHodAuditplan_schedule_memo() {

		try{
			/*				
				// to delete audit memo				
				//navigate audit memo Link 

				if(mGetPropertyFromFile("adt_deleteAuditMemo").equalsIgnoreCase("yes"))
			{	
				mNavigation(("adt_AmAuditLinkid"),("adt_AmTransactionsLinkid"),("adt_AmAuditMemoLinkid"));

				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);

				//Searching Audit Application
				mWaitForVisible("css",mGetPropertyFromFile("adt_AmSearchAuditNoIconid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("adt_AmSearchAuditNoIconid"));

				//Sending application Number
				mWaitForVisible("id", mGetPropertyFromFile("adt_AmSearchAuditByTextid"));
				mCustomWait(1000);

				//			mSendKeys("id",mGetPropertyFromFile("adt_AmSearchAuditByTextid"), auditNumber);
				IndOrDep("id", "adt_AmSearchAuditByTextid", "applicationNo");

				mTab("id",mGetPropertyFromFile("adt_AmSearchAuditByTextid"));
				mTakeScreenShot();
				mCustomWait(1000);

				//fancy search button
				mWaitForVisible("id", mGetPropertyFromFile("adt_AmSearchAuditAtFancyBtnid"));
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("adt_AmSearchAuditAtFancyBtnid"));

				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);

				//View Details Img Link
				mWaitForVisible("xpath", mGetPropertyFromFile("adt_AmViewDetailsImgid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_AmViewDetailsImgid"));
				mCustomWait(1000);

				// to delete Audit Memo list 

				mWaitForVisible("xpath", mGetPropertyFromFile("adt_AmDeleteIconid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_AmDeleteIconid"));

				mWaitForVisible("css", mGetPropertyFromFile("adt_AmDeletePopUpBoxid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("adt_AmDeleteBtnid"));

				mWaitForVisible("css", mGetPropertyFromFile("adt_AmDeletePopUpBoxid"));
				mCustomWait(1000);

				String FancyMsg = mGetText("css", mGetPropertyFromFile("adt_AmAfterDelAssertMsgid"));
				mTakeScreenShot();
				mCustomWait(1000);
				System.out.println(FancyMsg);
				mAssert(FancyMsg, mGetPropertyFromFile("adt_AmAfterDelAssertMsgdata"),"Actual   :"+FancyMsg+"   Expected   :"+mGetPropertyFromFile("adt_AmAfterDelAssertMsgdata"));
				mCustomWait(1000);	
				mClick("css",mGetPropertyFromFile("adt_AmDeletePopUpBoxid"));
				mCustomWait(1000);
				mTakeScreenShot();

				// final submit button
				mWaitForVisible("xpath",mGetPropertyFromFile("adt_AmAuditMemoAppSubBtnid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_AmAuditMemoAppSubBtnid"));

				// Proceed Button
				mWaitForVisible("id",mGetPropertyFromFile("adt_AmAuditMemoAppProcdBtnid"));
				mCustomWait(1000);

				String msgAtAuditMemoFinlSub = mGetText("css",mGetPropertyFromFile("adt_AmAuditMemoProcdAssertMsgid"));
				mAssert(msgAtAuditMemoFinlSub,mGetPropertyFromFile("adt_AmAuditMemoProcdAssertMsgdata")," Actual  :"+ msgAtAuditMemoFinlSub+ " Expected  :"+ mGetPropertyFromFile("adt_AmAuditMemoProcdAssertMsgdata"));
				mCustomWait(1000);
				System.out.println(msgAtAuditMemoFinlSub);

				mClick("id", mGetPropertyFromFile("adt_AmAuditMemoAppProcdBtnid"));
				mCustomWait(1000);
				mWaitForVisible("linkText", mGetPropertyFromFile("logoutid"));
			}	


				// to delete Schedule audit 
				// navigate Schedule Audit Link


				if(mGetPropertyFromFile("adt_DeleteAuditSchedule").equalsIgnoreCase("yes"))
			{	
				mCustomWait(1000);
				mClick("linkText", mGetPropertyFromFile("adt_SaAuditScheduleLinkid"));

				//Search Audit Number
				mWaitForVisible("id", mGetPropertyFromFile("adt_SaAutoTxtSearchAppid"));
				mCustomWait(1000);
				//			mSelectDropDown("id",  mGetPropertyFromFile("adt_SaAutoTxtSearchAppid"), auditNumber);
				IndOrDep("id", "adt_SaAutoTxtSearchAppid", "applicationNo");
				mCustomWait(1000);
				mTakeScreenShot();

				//Search Button
				mWaitForVisible("linkText", mGetPropertyFromFile("adt_SaSearchAppBtnid"));
				mCustomWait(1000);
				mClick("linkText", mGetPropertyFromFile("adt_SaSearchAppBtnid"));

				//View Details
				//			mWaitForVisible("css", mGetPropertyFromFile("adt_SaViewAppImgid"));
				mWaitForVisible("xpath", mGetPropertyFromFile("adt_SaViewAppImgid"));
				mCustomWait(1000);
				//			mClick("css", mGetPropertyFromFile("adt_SaViewAppImgid"));
				mClick("xpath", mGetPropertyFromFile("adt_SaViewAppImgid"));

				//getting text
				mWaitForVisible("xpath", mGetPropertyFromFile("adt_SaNoAtScheduleAuditid"));
				mCustomWait(1000);



				// to delete Audit Schedule Details 

				mWaitForVisible("xpath", mGetPropertyFromFile("adt_SaDeleteIconid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_SaDeleteIconid"));

				mWaitForVisible("css", mGetPropertyFromFile("adt_SaDeletePopUpBoxid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("adt_SaDeleteBtnid"));

				mWaitForVisible("css", mGetPropertyFromFile("adt_SaDeletePopUpBoxid"));
				mCustomWait(1000);

				String FanMsg = mGetText("css", mGetPropertyFromFile("adt_SaAfterDelAssertMsgid"));
				mTakeScreenShot();
				mCustomWait(1000);
				System.out.println(FanMsg);
				mAssert(FanMsg, mGetPropertyFromFile("adt_SaAfterDelAssertMsgdata"),"Actual   :"+FanMsg+"   Expected   :"+mGetPropertyFromFile("adt_SaAfterDelAssertMsgdata"));
				mCustomWait(1000);	
				mClick("css",mGetPropertyFromFile("adt_SaDeletePopUpBoxid"));
				mCustomWait(1000);
				mTakeScreenShot();

				//Final submit button
				mWaitForVisible("css", mGetPropertyFromFile("adt_SaAuditorSchedleAftrSubInputBtnid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("adt_SaAuditorSchedleAftrSubInputBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();

				//Proceed button
				mWaitForVisible("id", mGetPropertyFromFile("adt_SaAuditorSchdleProcdBtnid"));
				mCustomWait(1000);

				String msgAtFinlSubBtn = mGetText("css", mGetPropertyFromFile("adt_SaSchdlePlnFinalSubAssertid"));
				mAssert(msgAtFinlSubBtn, mGetPropertyFromFile("adt_SaSchdlePlnFinalSubAssertdata"), " Actual  :"+msgAtFinlSubBtn+"  Expected  :"+ mGetPropertyFromFile("adt_SaSchdlePlnFinalSubAssertdata"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("adt_SaAuditorSchdleProcdBtnid"));
				mCustomWait(1000);
				System.out.println(msgAtFinlSubBtn);
				mTakeScreenShot();
                mWaitForVisible("linkText", mGetPropertyFromFile("logoutid"));
			}	
			 */               




			// to delete audit plan
			// navigate Audit plan Link

			if(mGetPropertyFromFile("adt_DeleteAuditPlan").equalsIgnoreCase("yes"))
			{   
				mNavigation(("adt_PaAuditLinkid"),("adt_PaTransactionLinkid"),("adt_PaAuditPlanLinkid"));
				mCustomWait(1000);

				//to delete Audit Plan Details 

				IndOrDep("id", "adt_PaAuditNoSearchid", "applicationNo");
				//				mSelectDropDown("id", mGetPropertyFromFile("adt_PaAuditNoSearchid"), mGetPropertyFromFile("adt_PaAuditNoSearchdata"));

				mCustomWait(1000);
				mClick("linkText", mGetPropertyFromFile("adt_PaSearchBtnid"));

				mWaitForVisible("xpath", mGetPropertyFromFile("adt_PaViewDetailsIconid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_PaViewDetailsIconid"));

				mWaitForVisible("xpath", mGetPropertyFromFile("adt_PaDeleteIconid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_PaDeleteIconid"));

				mWaitForVisible("css", mGetPropertyFromFile("adt_PaDeletePopUpBoxid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("adt_PaDeleteBtnid"));

				mWaitForVisible("css", mGetPropertyFromFile("adt_PaDeletePopUpBoxid"));
				mCustomWait(1000);

				String FaMsg = mGetText("css", mGetPropertyFromFile("adt_PaAfterDelAssertMsgid"));
				mTakeScreenShot();
				mCustomWait(1000);
				System.out.println(FaMsg);
				mAssert(FaMsg, mGetPropertyFromFile("adt_PaAfterDelAssertMsgdata"),"Actual   :"+FaMsg+"   Expected   :"+mGetPropertyFromFile("adt_PaAfterDelAssertMsgdata"));
				mCustomWait(1000);	
				mClick("css",mGetPropertyFromFile("adt_PaDeletePopUpBoxid"));
				mCustomWait(1000);
				mTakeScreenShot();

				//Final submit button
				mWaitForVisible("css", mGetPropertyFromFile("adt_PaFinalSubBtnid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("adt_PaFinalSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();

				//Proceed button
				mWaitForVisible("id", mGetPropertyFromFile("adt_PaPlanAuditProcdBtnid"));
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("adt_PaPlanAuditProcdBtnid"));
				mCustomWait(2000);
				mTakeScreenShot();		
				mWaitForVisible("linkText", mGetPropertyFromFile("logoutid"));
			}	


			/*                				
				// to delete assign HOD  
				// navigate assign HOD Link

				if(mGetPropertyFromFile("adt_deleteAssignHod").equalsIgnoreCase("yes"))
			{
                mNavigation(("ADT_MasterLinkid"),("ADT_AssignHODLinkid"));
				mCustomWait(1000);

				mCustomWait(1000);

				//to delete Assign Hod 

				mSelectDropDown("id", mGetPropertyFromFile("adt_PaAuditNoSearchid"), mGetPropertyFromFile("adt_PaAuditNoSearchdata"));

				mCustomWait(1000);
				mClick("linkText", mGetPropertyFromFile("adt_PaSearchBtnid"));

				mWaitForVisible("xpath", mGetPropertyFromFile("adt_PaViewDetailsIconid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_PaViewDetailsIconid"));

				mWaitForVisible("xpath", mGetPropertyFromFile("adt_PaDeleteIconid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adt_PaDeleteIconid"));

				mWaitForVisible("css", mGetPropertyFromFile("adt_PaDeletePopUpBoxid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("adt_PaDeleteBtnid"));

				mWaitForVisible("css", mGetPropertyFromFile("adt_PaDeletePopUpBoxid"));
				mCustomWait(1000);

				String delMsg = mGetText("css", mGetPropertyFromFile("adt_PaAfterDelAssertMsgid"));
				mTakeScreenShot();
				mCustomWait(1500);
				System.out.println(delMsg);
				mAssert(delMsg, mGetPropertyFromFile("adt_PaAfterDelAssertMsgdata"),"Actual   :"+delMsg+"   Expected   :"+mGetPropertyFromFile("adt_PaAfterDelAssertMsgdata"));
				mCustomWait(1500);	
				mClick("css",mGetPropertyFromFile("adt_PaDeletePopUpBoxid"));
				mCustomWait(1500);
				mTakeScreenShot();

				//Final submit button
				mWaitForVisible("css", mGetPropertyFromFile("adt_PaFinalSubBtnid"));
				mCustomWait(1500);
				mClick("css", mGetPropertyFromFile("adt_PaFinalSubBtnid"));
				mCustomWait(1500);
				mTakeScreenShot();

				//Proceed button
				mWaitForVisible("id", mGetPropertyFromFile("adt_PaPlanAuditProcdBtnid"));
				mCustomWait(1500);
				mClick("id", mGetPropertyFromFile("adt_PaPlanAuditProcdBtnid"));
				mCustomWait(2000);
				mTakeScreenShot();		
			}
			 */			
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in DelHodAuditplan_schedule_memo script");
		}

	}

}

