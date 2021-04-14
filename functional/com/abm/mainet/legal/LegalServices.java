package com.abm.mainet.legal;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.output.TeeOutputStream;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import api.CommonUtilsAPI;
import clubbedapi.ClubbedAPI;

import com.abm.mainet.bnd.CustomListener;

import common.CommonMethods;
import errorhandling.MainetCustomExceptions;
import excelreader.ExcelReader;
import excelreader.ExcelToProperties;
import excelreader.ExcelWriting;

/**
 * @author tejas.kotekar
 * @ since 03-03-2016
 *
 */

@Listeners(CustomListener.class)
public class LegalServices extends CommonUtilsAPI{

//	static String suiteNo="lgl0028";   
	
	ExcelToProperties excelToProp = new ExcelToProperties();
	
	static CommonMethods common =new CommonMethods();
	//CommonMethods common =new CommonMethods();
	ClubbedAPI ClubbedUtils = new ClubbedAPI();
	public static boolean reschedule = false;

	ExcelReader ER = new ExcelReader();
	ExcelWriting EW =new ExcelWriting();
	//ExcelWriting EW1 =new ExcelWriting();
	@BeforeSuite
	public void beforeSuite(){

		thisClassName=this.getClass().getCanonicalName();

		//using myClassName in download method
		myClassName = thisClassName;

		mCreateModuleFolder("Legal_",myClassName);
	}	

	@AfterSuite
	public void after() throws FileNotFoundException 
	{	
		
		    String f="d:\\consoleoutput.txt";
			FileOutputStream fos = new FileOutputStream(f);
		    //we will want to print in standard "System.out" and in "file"
		    TeeOutputStream myOut=new TeeOutputStream(System.out, fos);
		    PrintStream ps = new PrintStream(myOut, true); //true - auto-flush after println
		    System.setOut(ps);
	}


	@Test(enabled=false)
	public void lgl_caseMasterEntry() throws InterruptedException{

		try{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		LegalCustomErrorMessages.legal_m_errors.clear();
	caseMasterEntry(mGetPropertyFromFile("lgl_EndToEndPending"));
		

		 
		 

		CommonUtilsAPI.lglErrorMsg.assertAll();
		System.out.println("Currwnt invocation count"+CurrentinvoCount);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in lgl_caseMasterEntry method");
		}
	}

	@Test(enabled=false)
	public void lgl_casePartyDetails() throws InterruptedException{
		
		try{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		LegalCustomErrorMessages.legal_m_errors.clear();
		casePartyDetails();
		CommonUtilsAPI.lglErrorMsg.assertAll();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in lgl_casePartyDetails method");
		}
	}

	

	@Test(enabled=false)
	public void lgl_caseHearingEntry() throws InterruptedException, IOException{
		
		try{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		LegalCustomErrorMessages.legal_m_errors.entrySet().clear();
		if(mGetPropertyFromFile("lgl_EndToEndPending").equalsIgnoreCase("Pending")||mGetPropertyFromFile("lgl_EndToEndPending").equalsIgnoreCase("Stay Order"))
		{
		CaseHearingEntry();
		}
		CommonUtilsAPI.lglErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in lgl_caseHearingEntry method");
		}
	}



		@Test(enabled=false)
	public void lgl_caseHearingEntryDetails() throws InterruptedException, IOException{
		
		try{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		LegalCustomErrorMessages.legal_m_errors.entrySet().clear();
		//CaseHearingEntryDetails(mGetPropertyFromFile("LGL_EndToEndConcluded"));
		String state;
		if(mGetPropertyFromFile("LGL_EndToEndReScheduled").equalsIgnoreCase("Concluded")||mGetPropertyFromFile("lgl_EndToEndPending").equalsIgnoreCase("Won")||mGetPropertyFromFile("lgl_EndToEndPending").equalsIgnoreCase("Lost"))
		{	
			
			state="NO";
			EW.excelWriting(mGetPropertyFromFile("refpath"),"Reshedule",state,"98");	
		
		
		}else
		{
			
			state="YES";
			EW.excelWriting(mGetPropertyFromFile("refpath"),"Reshedule",state,"98");	
		}
		if(mGetPropertyFromFile("lgl_EndToEndPending").equalsIgnoreCase("Pending")||mGetPropertyFromFile("lgl_EndToEndPending").equalsIgnoreCase("Stay Order"))
		{
			
			
				CaseHearingEntryDetails(mGetPropertyFromFile("LGL_EndToEndReScheduled"));
				
				if(mGetPropertyFromFile("LGL_EndToEndReScheduled").equalsIgnoreCase("Rescheduled"))
				{	
					
				reschedule=true;
				//lgl_caseHearingEntry();
				//caseHearingEntry_Script(); 
				//lgl_caseHearingEntry();
				CaseHearingEntry();
				
				}
		}
		
		
		
		CommonUtilsAPI.lglErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in lgl_caseHearingEntryDetails method");
		}
	}

		@Test(enabled=false)
		public void lgl_concludecaseHearingEntryDetails() throws InterruptedException, IOException{
			
			try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			LegalCustomErrorMessages.legal_m_errors.entrySet().clear();
			//CaseHearingEntryDetails(mGetPropertyFromFile("LGL_EndToEndConcluded"));
			
			
			if(mGetPropertyFromFile("lgl_EndToEndPending").equalsIgnoreCase("Pending")||mGetPropertyFromFile("lgl_EndToEndPending").equalsIgnoreCase("Stay Order"))
			{
				if(mGetPropertyFromFile("Reshedule").equalsIgnoreCase("yes"))
				{
				
					//CaseHearingEntryDetails(mGetPropertyFromFile("LGL_EndToEndConcluded"));
					CaseHearingEntryDetails(mGetPropertyFromFile("LGL_EndToEndReScheduled"));
				}
			}
			
			CommonUtilsAPI.lglErrorMsg.assertAll();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				inAtTest = true;
				throw new MainetCustomExceptions("Error in lgl_caseHearingEntryDetails method");
			}
		}
		
		
		

	@Test(enabled=false)
	public void lgl_refEvidencesNLegalOpinion() throws InterruptedException, IOException{
		
		try{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader(); 	
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		LegalCustomErrorMessages.legal_m_errors.entrySet().clear();
		RefEvidencesNLegalOpinion();
		CommonUtilsAPI.lglErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in lgl_refEvidencesNLegalOpinion method");
		}
	}


   @Test(enabled=false)
	public void lgl_endToEndNonPending() throws InterruptedException, IOException{
		
		try{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		LegalCustomErrorMessages.legal_m_errors.entrySet().clear();
		caseMasterEntry(mGetPropertyFromFile("lgl_EndToEndNonPending"));
		casePartyDetails();
		RefEvidencesNLegalOpinion();
		CommonUtilsAPI.lglErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in lgl_endToEndNonPending method");
		}
	}

	   @Test(enabled=false)
	public void lgl_endToEndReSchedule() throws InterruptedException, IOException{ 		
		
		try{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		LegalCustomErrorMessages.legal_m_errors.entrySet().clear();
		CaseHearingEntryDetails(mGetPropertyFromFile("LGL_EndToEndReScheduled"));
		if(mGetPropertyFromFile("LGL_EndToEndReScheduled").equalsIgnoreCase("Rescheduled"))
		{	
		reschedule=true;
		}
		CaseHearingEntry();
CommonUtilsAPI.lglErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in lgl_endToEndReSchedule method");
		}
	}


	   @Test(enabled=false)
	public void lgl_courtDetails() throws InterruptedException, IOException{
		
		try{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		LegalCustomErrorMessages.legal_m_errors.entrySet().clear();
		CourtDetails();
		caseMasterEntry(mGetPropertyFromFile("lgl_EndToEndPending"));
		CommonUtilsAPI.lglErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in lgl_courtDetails method");
		}
	}


	   @Test(enabled=false)
	public void lgl_judgeMaster() throws InterruptedException, IOException{
		
		try{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		LegalCustomErrorMessages.legal_m_errors.entrySet().clear();
		JudgeMaster();
		CaseHearingEntryDetails(mGetPropertyFromFile("LGL_EndToEndConcluded"));
		CommonUtilsAPI.lglErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in lgl_judgeMaster method");
		}
	}


	   @Test(enabled=false)
	public void lgl_reset() throws InterruptedException, IOException{
		
		try{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		LegalCustomErrorMessages.legal_m_errors.entrySet().clear();
		Reset();
		CommonUtilsAPI.lglErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in lgl_reset method");
		}
	}


	public void caseMasterEntry(String status) throws InterruptedException{

		
		
		
		
		 
		
		mCreateArtefactsFolder("Legal_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		common.selectUlb();
		common.departmentLogin(mGetPropertyFromFile("lgl_DeptLoginNameData"),mGetPropertyFromFile("lgl_DeptLoginNamePwd"));
		caseMasterEntry_Script(status);
		proceedCaseMasterEntry_Script();
		common.logOut();
		common.finalLogOut();
		mCloseBrowser();
	}

	public void casePartyDetails() throws InterruptedException{

		mCreateArtefactsFolder("Legal_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		common.selectUlb();
		common.departmentLogin(mGetPropertyFromFile("lgl_DeptLoginNameData"),mGetPropertyFromFile("lgl_DeptLoginNamePwd"));
		casePartyDetails_Script();
		proceedCasePartyDetails_Script();
		common.logOut();
		common.finalLogOut();
		mCloseBrowser();
	}

	public void CaseHearingEntry() throws InterruptedException, IOException
	{

		mCreateArtefactsFolder("LEGAL_");
		common.mOpenBrowser("Chrome");
		common.mGetURL(mGetPropertyFromFile("url"));
		common.selectUlb();
		common.departmentLogin(mGetPropertyFromFile("lgl_DeptLoginNameData"), mGetPropertyFromFile("lgl_DeptLoginNamePwd"));
		caseHearingEntry_Script();
		common.logOut();
		common.finalLogOut();
		mCloseBrowser();
	}

	public void CaseHearingEntryDetails(String status) throws InterruptedException, IOException
	{
		lgl_SuiteNo=mGetSequenceNo("seqLegal");
		System.out.println("sequence number is : "+lgl_SuiteNo);
		mCreateArtefactsFolder("LEGAL_");
		common.mOpenBrowser("Chrome");
		common.mGetURL(mGetPropertyFromFile("url"));
		common.selectUlb();
		common.departmentLogin(mGetPropertyFromFile("lgl_DeptLoginNameData"), mGetPropertyFromFile("lgl_DeptLoginNamePwd"));
		caseHearingEntryDetails_Script(status);
		common.logOut();
		common.finalLogOut();
		mCloseBrowser();
	}

	public void RefEvidencesNLegalOpinion()
	{
		//System.out.println("legal number at references : "+lgl_SuiteNo);
		//lgl_RefCaseNo=mGetSequenceNo("seqReference");
	//	System.out.println("sequence number is : "+lgl_RefCaseNo);
		mCreateArtefactsFolder("LEGAL_");
		common.mOpenBrowser("Chrome");
		common.mGetURL(mGetPropertyFromFile("url"));
		common.selectUlb();
		common.departmentLogin(mGetPropertyFromFile("lgl_DeptLoginNameData"),mGetPropertyFromFile("lgl_DeptLoginNamePwd"));
		refEvidencesNLegalOpinion_Script();
		common.logOut();
		common.finalLogOut();
		mCloseBrowser();
	}

	public void CourtDetails() {

		mCreateArtefactsFolder("Legal_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		common.selectUlb();
		common.departmentLogin(mGetPropertyFromFile("lgl_DeptLoginNameData"),mGetPropertyFromFile("lgl_DeptLoginNamePwd"));
		courtDetails_Script();
		common.logOut();
		common.finalLogOut();
		mCloseBrowser();
	}


	public void JudgeMaster() {

		mCreateArtefactsFolder("Legal_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		common.selectUlb();
		common.departmentLogin(mGetPropertyFromFile("lgl_DeptLoginNameData"),mGetPropertyFromFile("lgl_DeptLoginNamePwd"));
		judgeMaster_Script();
		common.logOut();
		common.finalLogOut();
		mCloseBrowser();
	}

	public void Reset() {

		mCreateArtefactsFolder("Legal_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		common.selectUlb();
		common.departmentLogin(mGetPropertyFromFile("lgl_DeptLoginNameData"),mGetPropertyFromFile("lgl_DeptLoginNamePwd"));
		Reset_Script();
		common.logOut();
		common.finalLogOut();
		mCloseBrowser();
	}

	//Tejas Kotekar
	//Case Master Entry with editing
	public void caseMasterEntry_Script(String status) throws InterruptedException{

		try{

			//	changes done by Hiren Kathiria on 2/6/2016
			// to add new case to case master
			//Navigation from Legal link to Transaction link to Case master link to Add case master link
			if(mGetPropertyFromFile("lgl_CaseMasAdd").equalsIgnoreCase("Yes") && mGetPropertyFromFile("lgl_CaseMasEdit").equalsIgnoreCase("No"))
			{
				mWaitForVisible("linkText",mGetPropertyFromFile("lgl_LegalLinkId"));	
				mCustomWait(1000);	
				mNavigation(mGetPropertyFromFile("lgl_LegalLinkId"), mGetPropertyFromFile("lgl_TransactionLinkId"), mGetPropertyFromFile("lgl_CaseMasterLinkId"), mGetPropertyFromFile("lgl_AddCaseMasterId"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_SuiteNoId"));
				mCustomWait(1500);
				mTakeScreenShot();;

				//mSendKeys("id",mGetPropertyFromFile("lgl_SuiteNoId"), mGetPropertyFromFile("lgl_SuiteNoData"));
				//lgl_SuiteNo=mGenerateAplhanumericString(mGetPropertyFromFile("lgl_alphanumericPrefix"), 8);

//				lgl_SuiteNo=mGetSequenceNo("seqLegal");
				//				mSendKeys("id",mGetPropertyFromFile("lgl_SuiteNoId"), lgl_SuiteNo);
			
				String suiteNo=mGetPropertyFromFile("lgl_SuiteNoData");
				mSendKeys("id",mGetPropertyFromFile("lgl_SuiteNoId"), suiteNo);
				
			//	String suiteNo=mGetPropertyFromFile("LGL_hedAppNoAtGridSearchdata");
		
				mWaitForVisible("id",mGetPropertyFromFile("lgl_CaseRefSuiteNoId"));
				mCustomWait(1500);			
				mSendKeys("id", mGetPropertyFromFile("lgl_CaseRefSuiteNoId"),mGetPropertyFromFile("lgl_CaseRefSuiteNoData"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_CaseTypeId"));
				mCustomWait(1500);
				mSelectDropDown("id", mGetPropertyFromFile("lgl_CaseTypeId"), mGetPropertyFromFile("lgl_CaseTypeData"));
				
				mWaitForVisible("id",mGetPropertyFromFile("lgl_CaseMasCategoryId"));
				mCustomWait(1500);
				mSelectDropDown("id", mGetPropertyFromFile("lgl_CaseMasCategoryId"),mGetPropertyFromFile("lgl_CaseMasCategoryData"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_OrgAsId"));
				mSelectDropDown("id", mGetPropertyFromFile("lgl_OrgAsId"), mGetPropertyFromFile("lgl_OrgAsData"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_CaseMasStatusId"));
				mCustomWait(1500);
//                mSelectDropDown("id", mGetPropertyFromFile("lgl_CaseStatusId"), mGetPropertyFromFile("lgl_CaseStatusData"));
				mSelectDropDown("id", mGetPropertyFromFile("lgl_CaseMasStatusId"),status);

				mWaitForVisible("id", mGetPropertyFromFile("lgl_CaseCourtId"));
				mCustomWait(1500);

				if(mGetPropertyFromFile("lgl_AddNewCourt").equalsIgnoreCase("yes")) 
				{
					mSelectDropDown("id", mGetPropertyFromFile("lgl_CaseCourtId"), mGetPropertyFromFile("lgl_CourtNameData"));
				}

				else
				{
					mSelectDropDown("id", mGetPropertyFromFile("lgl_CaseCourtId"), mGetPropertyFromFile("lgl_CaseCourtData"));
				}

				// Changes made by Hiren Kathiria on 27-05-2016

				
				if(status.equalsIgnoreCase("Pending") || status.equalsIgnoreCase("Stay Order"))
				{
					mDateSelect("id",mGetPropertyFromFile("lgl_CaseMasDateId"), mGetPropertyFromFile("lgl_CaseMasDateData"));
				}

				else
				{
					mDateSelect("id",mGetPropertyFromFile("lgl_CaseMasDateId"), mGetPropertyFromFile("lgl_CaseMasDateData"));
					mDateSelect("id",mGetPropertyFromFile("lgl_CaseClosedDateId"), mGetPropertyFromFile("lgl_CaseClosedDateData"));
				}

				mWaitForVisible("id",mGetPropertyFromFile("lgl_CaseDeptId"));
				mCustomWait(1500);
				mSelectDropDown("id", mGetPropertyFromFile("lgl_CaseDeptId"), mGetPropertyFromFile("lgl_CaseDeptData"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_AdvocateID"));
				mCustomWait(1500);
				mSelectDropDown("id", mGetPropertyFromFile("lgl_AdvocateID"), mGetPropertyFromFile("lgl_AdvocateData"));

				mWaitForVisible("id", mGetPropertyFromFile("lgl_MatterDetailOneId"));
				mCustomWait(1500);
				mSendKeys("id", mGetPropertyFromFile("lgl_MatterDetailOneId"),mGetPropertyFromFile("lgl_MatterDetailOneData"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_MatterDetailTwoId"));
				mCustomWait(1500);
				mSendKeys("id", mGetPropertyFromFile("lgl_MatterDetailTwoId"), mGetPropertyFromFile("lgl_MatterDetailTwoData"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_ActAppliedID"));
				mCustomWait(1500);
				mSendKeys("id", mGetPropertyFromFile("lgl_ActAppliedID"), mGetPropertyFromFile("lgl_ActAppliedData"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_CaseRemarkId"));
				mCustomWait(1500);
				mSendKeys("id", mGetPropertyFromFile("lgl_CaseRemarkId"), mGetPropertyFromFile("lgl_CaseRemarkData"));


				//mWaitForVisible("xpath",mGetPropertyFromFile("lgl_UploadId"));
				mCustomWait(1500);
				mUpload("xpath", mGetPropertyFromFile("lgl_UploadId"), mGetPropertyFromFile("lgl_UploadData"));
				mTakeScreenShot();

				/*ClubbedUtils.lgl_mDynamicAddRowObjects(mGetPropertyFromFile("lgl_CaseMasPlaintiffTabId"),
						mGetPropertyFromFile("lgl_CaseMasPlaintiffAddBtnId"),
						mGetPropertyFromFile("lgl_CaseMasPlaintiffNameData"),
						mGetPropertyFromFile("lgl_CaseMasPlaintiffAddrData"),mGetPropertyFromFile("lgl_CaseMasPlaintiffDetailsCount"));
				ClubbedUtils.lgl_mDynamicAddRowObjects(mGetPropertyFromFile("lgl_CaseMasDefndTabId"),
						mGetPropertyFromFile("lgl_CaseMasDefndAddBtnId"),
						mGetPropertyFromFile("lgl_CaseMasDefendantNameData"),
						mGetPropertyFromFile("lgl_CaseMasDefendantAddrData"),mGetPropertyFromFile("lgl_CaseMasDefendantDetailsCount"));
*/
				
			

				int pcount = Integer.parseInt(mGetPropertyFromFile("lgl_plaintiff_count"));
			//	int pcount = Integer.valueOf(mGetPropertyFromFile("lgl_plaintiff_count"));
				int dcount = Integer.parseInt(mGetPropertyFromFile("lgl_diffendent_count"));
				for(int i=0;i<=pcount;i++)
				{
						
						mSendKeys("id", (mGetPropertyFromFile("lgl_add_id")+i), (mGetPropertyFromFile("plantiff")+i));
						mSendKeys("id", (mGetPropertyFromFile("lgl_paddress_id")+i), (mGetPropertyFromFile("lgl_paddress_data")+i));
						
					   if (i >= 0 && i < pcount) {
						   System.out.println(i);
						   mClick("id", mGetPropertyFromFile("add"));
					}       
					   
					
				}
				int removecount = Integer.parseInt(mGetPropertyFromFile("lgl_plantiff_remove_count"));
				
		          for(int rp=0;rp<removecount;rp++)
		          {
				mClick("id", mGetPropertyFromFile("Remove"));
		          }
				for(int j=0;j<=dcount;j++)
				{
					mSendKeys("id", (mGetPropertyFromFile("lgl_defadd_id")+j), (mGetPropertyFromFile("defendent")+j));
					mSendKeys("id", (mGetPropertyFromFile("lgl_daddress_id")+j), (mGetPropertyFromFile("lgl_daddress_data")+j));
					
				//        mSendKeys("id", mGetPropertyFromFile("lgl_add_id")+i, mGetPropertyFromFile("plantiff")+i);
				          
					   if (j >= 0 && j < dcount) {
						   System.out.println(j);
							mClick("id", mGetPropertyFromFile("dadd"));
					}       
							               
	        
				}
				
				int dremovecount = Integer.parseInt(mGetPropertyFromFile("lgl_defendent_remove_count"));
				
		          for(int rp=0;rp<dremovecount;rp++)
		          {
				mClick("id", mGetPropertyFromFile("dRemove"));
		          }
			
		          mWaitForVisible("css",mGetPropertyFromFile("lgl_SaveCaseMasterEntryId"));
					mCustomWait(2000);
					mClick("css", mGetPropertyFromFile("lgl_SaveCaseMasterEntryId"));	
					mCustomWait(2000);
					
					/*if(mGetPropertyFromFile("lgl_EndToEndPending").equalsIgnoreCase("Lost")||mGetPropertyFromFile("lgl_EndToEndPending").equalsIgnoreCase("Won")||mGetPropertyFromFile("lgl_EndToEndPending").equalsIgnoreCase("Closed"))
					{
					EW.excelWriting(mGetPropertyFromFile("refpath"), "LGL_refAppNoAtGridSearchdata", suiteNo);
					
					}else
					{
						System.out.println("========================================>datatobewritten"+EW.datatobewritten);
						EW.excelWriting(mGetPropertyFromFile("dpath"), "LGL_hedateAppNoAtGridSearchdata", suiteNo);
						
					//	System.out.println("========================================>after dpathdatatobewritten"+EW.datatobewritten);
						
						EW.excelWriting(mGetPropertyFromFile("refpath"), "LGL_refAppNoAtGridSearchdata", suiteNo);
						System.out.println("========================================>after refathdatatobewritten"+EW.datatobewritten);
								
						
					}*/
					mTakeScreenShot();
			}
				
//				
			//Navigation from Legal link to Transaction link to Case master link for editing case master 
			// to edit existing case from case master

			else if(mGetPropertyFromFile("lgl_CaseMasEdit").equalsIgnoreCase("Yes") && mGetPropertyFromFile("lgl_CaseMasAdd").equalsIgnoreCase("No"))
			{			
				mNavigation(mGetPropertyFromFile("lgl_LegalLinkId"), mGetPropertyFromFile("lgl_TransactionLinkId"), mGetPropertyFromFile("lgl_CaseMasterLinkId"));

				mcaseSearchBy();

				//search
				mWaitForVisible("linkText", mGetPropertyFromFile("lgl_CaseMasSearchBtnId"));
				mCustomWait(2000);
				mTakeScreenShot();;
				mCustomWait(2000);
				mClick("linkText", mGetPropertyFromFile("lgl_CaseMasSearchBtnId"));
				mCustomWait(2000);

				//Grid search icon
				mWaitForVisible("css", mGetPropertyFromFile("lgl_GridSearchIconid"));
				mCustomWait(2000);
				mClick("css", mGetPropertyFromFile("lgl_GridSearchIconid"));

				//sending suite number
				mWaitForVisible("id", mGetPropertyFromFile("lgl_GridSearchid"));
				mCustomWait(2000);
				//				mSendKeys("id", mGetPropertyFromFile("lgl_GridSearchid"), lgl_SuiteNo);
				String suiteNo=mGetPropertyFromFile("lgl_SuiteNoData");
				mSendKeys("id", mGetPropertyFromFile("lgl_GridSearchid"), suiteNo);

				//fancy Box search Button
				mWaitForVisible("id", mGetPropertyFromFile("lgl_SearchFindBtnid"));
				mCustomWait(2000);
				mTakeScreenShot();;
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("lgl_SearchFindBtnid"));

				//Edit Details Icon
				mWaitForVisible("xpath", mGetPropertyFromFile("lgl_EditDetailsIconid"));
				mCustomWait(2000);
				mTakeScreenShot();;
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("lgl_EditDetailsIconid"));

				mWaitForVisible("id", mGetPropertyFromFile("lgl_CaseRefSuiteNoId"));
				mCustomWait(2000);

				if(mGetPropertyFromFile("lgl_CaseMasEditRefSuiteNo").equalsIgnoreCase("Yes"))
				{
					mWaitForVisible("id",mGetPropertyFromFile("lgl_CaseRefSuiteNoId"));
					mCustomWait(2000);			
					mSendKeys("id", mGetPropertyFromFile("lgl_CaseRefSuiteNoId"),mGetPropertyFromFile("lgl_CaseMasEditRefSuiteNoData"));
				}

				else {
					System.out.println("No change in ref suite no" );				
				}

				if(mGetPropertyFromFile("lgl_CaseMasEditMattDetailOne").equalsIgnoreCase("Yes"))
				{
					mWaitForVisible("id", mGetPropertyFromFile("lgl_MatterDetailOneId"));
					mCustomWait(2000);
					mSendKeys("id", mGetPropertyFromFile("lgl_MatterDetailOneId"),mGetPropertyFromFile("lgl_CaseMasEditMattDetailOneData"));
					mTakeScreenShot();
				}

				else {
					System.out.println("No change in Matter Detail One" );				
				}

				if(mGetPropertyFromFile("lgl_CaseMasEditMattDetailTwo").equalsIgnoreCase("Yes"))
				{
					mWaitForVisible("id",mGetPropertyFromFile("lgl_MatterDetailTwoId"));
					mCustomWait(2000);
					mSendKeys("id", mGetPropertyFromFile("lgl_MatterDetailTwoId"), mGetPropertyFromFile("lgl_CaseMasEditMattDetailTwoData"));
				}

				else {
					System.out.println("No change in Matter Detail Two" );				
				}

				if(mGetPropertyFromFile("lgl_CaseMasEditActApplied").equalsIgnoreCase("Yes"))
				{
					mWaitForVisible("id",mGetPropertyFromFile("lgl_ActAppliedID"));
					mCustomWait(2000);
					mSendKeys("id", mGetPropertyFromFile("lgl_ActAppliedID"), mGetPropertyFromFile("lgl_CaseMasEditActAppData"));
				}

				else {
					System.out.println("No change in act applied" );				
				}

				if(mGetPropertyFromFile("lgl_CaseMasEditRemark").equalsIgnoreCase("Yes"))
				{
					mWaitForVisible("id",mGetPropertyFromFile("lgl_CaseRemarkId"));
					mCustomWait(2000);
					mSendKeys("id", mGetPropertyFromFile("lgl_CaseRemarkId"), mGetPropertyFromFile("lgl_CaseMasEditRemarkData"));
				}

				else {
					System.out.println("No change in remark" );				
				}

				if(mGetPropertyFromFile("lgl_CaseMasEditUpload").equalsIgnoreCase("Yes"))
				{
					//mWaitForVisible("xpath",mGetPropertyFromFile("lgl_UploadId"));
					mCustomWait(2000);
					mUpload("xpath", mGetPropertyFromFile("lgl_UploadId"), mGetPropertyFromFile("lgl_CaseMasEditUploadData"));
					mTakeScreenShot();
				}

				else {
					System.out.println("No change in upload file" );				
				}

				if(mGetPropertyFromFile("lgl_CaseMasEditPartyDetails").equalsIgnoreCase("Yes"))
				{
					ClubbedUtils.lgl_mDynamicAddRowObjects(mGetPropertyFromFile("lgl_CaseMasPlaintiffTabId"),
							mGetPropertyFromFile("lgl_CaseMasPlaintiffAddBtnId"),
							mGetPropertyFromFile("lgl_CaseMasEditPlaintiffNameData"),
							mGetPropertyFromFile("lgl_CaseMasEditPlaintiffAddrData"),mGetPropertyFromFile("lgl_CaseMasEditPlaintiffDetailsCount"));
					ClubbedUtils.lgl_mDynamicAddRowObjects(mGetPropertyFromFile("lgl_CaseMasDefndTabId"),
							mGetPropertyFromFile("lgl_CaseMasDefndAddBtnId"),
							mGetPropertyFromFile("lgl_CaseMasEditDefendantNameData"),
							mGetPropertyFromFile("lgl_CaseMasEditDefendantAddrData"),mGetPropertyFromFile("lgl_CaseMasEditDefendantDetailsCount"));
				}

				else {
					System.out.println("No change in party details" );				
				}

				mWaitForVisible("css",mGetPropertyFromFile("lgl_SaveCaseMasterEntryId"));
				mCustomWait(2000);
				mClick("css", mGetPropertyFromFile("lgl_SaveCaseMasterEntryId"));	
				mCustomWait(2000);
				mTakeScreenShot();

			}

			else
			{
				System.out.println("No further action can be performed as both Add and Edit flag are true or false"); 
			}
			}


		catch(Exception e){

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in lgl_caseMasterEntry script");
		}
	}


	public void proceedCaseMasterEntry_Script(){

		try{

			if(mGetPropertyFromFile("lgl_CaseMasAdd").equalsIgnoreCase("Yes") && mGetPropertyFromFile("lgl_CaseMasEdit").equalsIgnoreCase("No"))
			{
				mWaitForVisible("id",mGetPropertyFromFile("lgl_ProceedBtnId"));
				mCustomWait(2000);
				String proceedMsg=mGetText("xpath", mGetPropertyFromFile("lgl_ProceedMsgId"));
				System.out.println("Proceed msg::"+proceedMsg);
				mAssert(proceedMsg,mGetPropertyFromFile("lgl_ProceedMsgData"),"Message does not match, actual: "+proceedMsg+"expected: "+mGetPropertyFromFile("lgl_ProceedMsgData"));
				mTakeScreenShot();;
				mClick("id", mGetPropertyFromFile("lgl_ProceedBtnId"));
				mCustomWait(2000);
				mTakeScreenShot();;
			}

			else if(mGetPropertyFromFile("lgl_CaseMasAdd").equalsIgnoreCase("no") && mGetPropertyFromFile("lgl_CaseMasEdit").equalsIgnoreCase("yes"))
			{
				mWaitForVisible("id",mGetPropertyFromFile("lgl_EditProceedBtnId"));
				mCustomWait(2000);
				String proceedMsg=mGetText("xpath", mGetPropertyFromFile("lgl_EditProceedMsgId"));
				System.out.println("Proceed msg::"+proceedMsg);
				mAssert(proceedMsg,mGetPropertyFromFile("lgl_EditProceedMsgData"),"Message does not match, actual: "+proceedMsg+"expected: "+mGetPropertyFromFile("lgl_EditProceedMsgData"));
				mTakeScreenShot();;
				mClick("id", mGetPropertyFromFile("lgl_EditProceedBtnId"));
				mCustomWait(2000);
				mTakeScreenShot();;
			}


		}catch(Exception e){

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in proceedCaseMasterEntry_Script");
		}

	}


	//	  Case searchBy option

	/**
	 * @author Hiren Kathiria
	 * @since 25-05-2016
	 *
	 */

	public void mcaseSearchBy()
	{
		try
		{     // changes done on 9-6-2016 by Hiren Kathiria 
			// changes done on 8-6-2016 by Hiren Kathiria  
			//	changes done on 3-6-2016 by Hiren Kathiria  

			// Search by Category 
			

			if(mGetPropertyFromFile("lgl_CaseCategorySrch").equalsIgnoreCase("yes"))
			{
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("lgl_CaseCategoryId"));
				mSelectDropDown("id", mGetPropertyFromFile("lgl_CaseCategoryId"), mGetPropertyFromFile("lgl_CaseCategoryData"));
			}

			// Search by Status

			if(mGetPropertyFromFile("lgl_CaseStatusSrch").equalsIgnoreCase("yes"))
			{ 
				mCustomWait(4000);
				//mClick("id", mGetPropertyFromFile("lgl_CaseStatusId"));
				mCustomWait(1500);
				////change done to maintain search crir=teria same throuout application 
				//mSelectDropDown("id", mGetPropertyFromFile("lgl_CaseStatusId"), mGetPropertyFromFile("lgl_CaseStatusData"));
				mSelectDropDown("id", mGetPropertyFromFile("lgl_CaseStatusId"), mGetPropertyFromFile("lgl_EndToEndPending"));
				
				mCustomWait(4000);
			}  

			// Search by Case Date

			if(mGetPropertyFromFile("lgl_CaseDateSrch").equalsIgnoreCase("yes"))
			{
				mCustomWait(1000);
				mDateSelect("id",mGetPropertyFromFile("lgl_CaseDateId"), mGetPropertyFromFile("lgl_CaseDateData"));
			}
			/*else {
				mSendKeys("id",mGetPropertyFromFile("lgl_CaseDateId"),"Keys.TAB");
			}*/			
			
			// Search by Attorney Name

			if(mGetPropertyFromFile("lgl_CaseAttorNameSrch").equalsIgnoreCase("yes"))
			{
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("lgl_CasePAttorNameId"));
				mSelectDropDown("id", mGetPropertyFromFile("lgl_CaseAttorNameId"), mGetPropertyFromFile("lgl_CaseAttorNameData"));
				mCustomWait(1000);
			}  
			mCustomWait(4000);
		}


		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mcaseSearchBy Script");
		}

	}



	//Case Party Details
	public void casePartyDetails_Script(){


		try{

			mWaitForVisible("linkText", mGetPropertyFromFile("lgl_LegalLinkId"));
			mCustomWait(2000);
			//Navigation from Legal link to transaction link to Case party details link
			mNavigation(mGetPropertyFromFile("lgl_LegalLinkId"), mGetPropertyFromFile("lgl_TransactionLinkId"), mGetPropertyFromFile("lgl_CasePartyDetLinkId"));

			
			// changes done by Hiren Kathiria on 26-05-2016

			mcaseSearchBy();

			mCustomWait(2000);
			mTakeScreenShot();;
			mWaitForVisible("linkText",mGetPropertyFromFile("lgl_CasePartyDetSearchBtnId"));
			mCustomWait(1000);
			mClick("linkText",mGetPropertyFromFile("lgl_CasePartyDetSearchBtnId"));
			mCustomWait(2000);

			mWaitForVisible("css",mGetPropertyFromFile("lgl_CasePartyDetSearchIconID"));
			mCustomWait(1000);
			mClick("css",mGetPropertyFromFile("lgl_CasePartyDetSearchIconID"));

			mWaitForVisible("id",mGetPropertyFromFile("lgl_CasePartyDetSuiteSearchId"));
			mCustomWait(2000);
			//			mSendKeys("id", mGetPropertyFromFile("lgl_CasePartyDetSuiteSearchId"),lgl_SuiteNo);
			String suiteNo=mGetPropertyFromFile("lgl_SuiteNoData");
			mSendKeys("id", mGetPropertyFromFile("lgl_CasePartyDetSuiteSearchId"),suiteNo);
  
			mWaitForVisible("id",mGetPropertyFromFile("lgl_CasePartyDetSuiteSearchBtnId"));
			mCustomWait(1000);
			mTakeScreenShot();
			mClick("id",mGetPropertyFromFile("lgl_CasePartyDetSuiteSearchBtnId"));

			mWaitForVisible("xpath",mGetPropertyFromFile("lgl_CasePartyDetEditBtnId"));
			mCustomWait(1000);
			mClick("xpath",mGetPropertyFromFile("lgl_CasePartyDetEditBtnId"));
			mCustomWait(2000);
			mTakeScreenShot();;
			mWaitForVisible("css",mGetPropertyFromFile("lgl_CasePartyDetSaveBtnId"));


			// to verify the fetch data with originally entered data
			verifyCasePartyDetData();
			verifyCaseHeDateData(mGetPropertyFromFile("lgl_VerifyCasePartyDateId"));


			// to add the party details rows

			ClubbedUtils.lgl_mDynamicAddRowObjects(mGetPropertyFromFile("lgl_CasePartyDetPlaintiffTabId"),
					mGetPropertyFromFile("lgl_CasePartyDetPlaintiffAddBtnId"),
					mGetPropertyFromFile("lgl_CasePartyDetPlaintiffNameData01"),
					mGetPropertyFromFile("lgl_CasePartyDetPlaintiffAddrData01"),mGetPropertyFromFile("lgl_CasePartyDetPlaintiffDetailsAddCount"));
			ClubbedUtils.lgl_mDynamicAddRowObjects(mGetPropertyFromFile("lgl_CasePartyDetDefndTabId"),
					mGetPropertyFromFile("lgl_CasePartyDetDefndAddBtnId"),
					mGetPropertyFromFile("lgl_CasePartyDetDefendantNameData01"),
					mGetPropertyFromFile("lgl_CasePartyDetDefendantAddrData01"),mGetPropertyFromFile("lgl_CasePartyDetDefendantDetailsAddCount"));

			// to remove the party details rows

			ClubbedUtils.lgl_mDynamicRemoveRowObjects(mGetPropertyFromFile("lgl_CasePartyDetPlaintiffTabId"),
					mGetPropertyFromFile("lgl_CasePartyDetPlaintiffRemoveBtnId"),
					mGetPropertyFromFile("lgl_CasePartyDetPlaintiffDetailsRemoveCount"));		
			ClubbedUtils.lgl_mDynamicRemoveRowObjects(mGetPropertyFromFile("lgl_CasePartyDetDefndTabId"),
					mGetPropertyFromFile("lgl_CasePartyDetDefndRemoveBtnId"),
					mGetPropertyFromFile("lgl_CasePartyDetDefendantDetailsRemoveCount"));	

			mWaitForVisible("css",mGetPropertyFromFile("lgl_CasePartyDetSaveBtnId"));
			mCustomWait(2000);
			mClick("css",mGetPropertyFromFile("lgl_CasePartyDetSaveBtnId"));
			mCustomWait(2000);
			mTakeScreenShot();;

		}
		catch(Exception e){

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in casePartyDetails_Script");
		}	
	}


	public void proceedCasePartyDetails_Script(){

		try{

			mWaitForVisible("id",mGetPropertyFromFile("lgl_CasePartyDetProceedBtnId"));
			mCustomWait(2000);
			String proceedMsg=mGetText("xpath", mGetPropertyFromFile("lgl_CasePartyDetProceedMsgId"));
			mAssert(proceedMsg,mGetPropertyFromFile("lgl_CasePartyDetProceedMsgData"),"Message does not match, actual: "+proceedMsg+"expected: "+mGetPropertyFromFile("lgl_CasePartyDetProceedMsgData"));
			mTakeScreenShot();
			mClick("id", mGetPropertyFromFile("lgl_CasePartyDetProceedBtnId"));
			mCustomWait(2000);
			mTakeScreenShot();;

		}catch(Exception e){

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in proceedCasePartyDetails_Script");
		}

	}



	//	Case Hearing Entry Details searchBy

	/**
	 * @author Hiren Kathiria
	 * @since 25-05-2016
	 *
	 */


	public void mcaseHeEntryDetSearchBy()
	{
		try 
		{                                 // change done by Hiren Kathiria on 9-6-2016
			// changes done on 8-6-2016 by Hiren Kathiria
			//  changes done on 3-6-2016 by Hiren Kathiria
			// Search by Category 

			if(mGetPropertyFromFile("LGL_CaseHeDetCategorySrch").equalsIgnoreCase("yes"))
			{
				mCustomWait(1000);				
				mSelectDropDown("id", mGetPropertyFromFile("LGL_CaseHeDetCategoryId"), mGetPropertyFromFile("LGL_CaseHeDetCategoryData"));
			}

			// Search by Status

			if(mGetPropertyFromFile("LGL_CaseHeDetStatusSrch").equalsIgnoreCase("yes"))
			{
				mCustomWait(1000);
				//mSelectDropDown("id", mGetPropertyFromFile("LGL_CaseHeDetStatusId"), mGetPropertyFromFile("LGL_CaseHeDetStatusData"));
				
				mSelectDropDown("id", mGetPropertyFromFile("LGL_CaseHeDetStatusId"), mGetPropertyFromFile("lgl_EndToEndPending"));
			}

			// Search by Attorney Name

			if(mGetPropertyFromFile("LGL_CaseHeDetAttorNameSrch").equalsIgnoreCase("yes"))
			{
				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("LGL_CaseHeDetAttorNameId"), mGetPropertyFromFile("LGL_CaseHeDetAttorNameData"));
			}

			// Search by Case Date

			if(mGetPropertyFromFile("LGL_CaseHeDetCaseDateSrch").equalsIgnoreCase("yes"))
			{
				mCustomWait(1000);			 
				mDateSelect("id",mGetPropertyFromFile("LGL_CaseHeDetDateId"), mGetPropertyFromFile("LGL_CaseHeDetCaseDateData"));
			}

			// Search by Suite No

			if(mGetPropertyFromFile("LGL_CaseSuiteNoSrch").equalsIgnoreCase("yes"))
			{
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("LGL_CaseSuiteNoid"),mGetPropertyFromFile("LGL_CaseSuiteNoData"));			  
			}

			// Search by Hearing Date

			if(mGetPropertyFromFile("LGL_CaseHedateSrch").equalsIgnoreCase("yes"))
			{
				mCustomWait(1000);
				mDateSelect("id",mGetPropertyFromFile("LGL_CaseHedateid"), mGetPropertyFromFile("LGL_CaseHedateData"));
				mCustomWait(1500);	
			}
			mCustomWait(4000);
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mcaseHeEntryDetSearchBy script");
		}

	}




	/**
	 * @author sneha.solaskar
	 *
	 */
	//04/03/2016
	//method for Case Hearing Entry Details
	public void caseHearingEntryDetails_Script(String status)   // changes done by Hiren Kathiria on 8-6-2016
	{
		try
		{
		//	mWaitForVisible("linkText", mGetPropertyFromFile("LGL_LegalLinkid_hd"));
			mGenericWait();
			//Navigation from Legal link to Transaction link to Case hearing entry details link
	//		mNavigation("id",mGetPropertyFromFile("LGL_LegalLinkid"), mGetPropertyFromFile("LGL_TransactionLinkid"), mGetPropertyFromFile("LGL_CseHearEntryDetailsLinkid"));
	//*//
			mNavigation("LGL_LegalLinkid_hd","LGL_TransactionLinkid", "LGL_CseHearEntryDetailsLinkid");

			mCustomWait(2000);
			mTakeScreenShot();;

			// changes done by Hiren Kathiria on 26-05-2016

			mcaseHeEntryDetSearchBy();

			//Search Button
			mWaitForVisible("linkText", mGetPropertyFromFile("LGL_AppSearchBtnid"));
			mCustomWait(1500);
			mTakeScreenShot();;
			mCustomWait(1500);
			mClick("linkText", mGetPropertyFromFile("LGL_AppSearchBtnid"));

			mCustomWait(1000);

			//Search Icon on grid
			mWaitForVisible("css", mGetPropertyFromFile("LGL_GridSearchIconid"));
			mCustomWait(2000);
			mClick("css", mGetPropertyFromFile("LGL_GridSearchIconid"));

			//sending Suite Number
			mWaitForVisible("id", mGetPropertyFromFile("LGL_SearchAppTextid"));
			mCustomWait(2000);
			//			mSendKeys("id", mGetPropertyFromFile("LGL_SearchAppTextid"),lgl_SuiteNo);
			//String suiteNo=mGetPropertyFromFile("LGL_hedateAppNoAtGridSearchdata");
			String suiteNo=mGetPropertyFromFile("lgl_SuiteNoData");
			mSendKeys("id", mGetPropertyFromFile("LGL_SearchAppTextid"),suiteNo);

			//mSendKeys("id", mGetPropertyFromFile("LGL_SearchAppTextid"), mGetPropertyFromFile("LGL_SearchAppTextData"));

			mCustomWait(2000);
			mTakeScreenShot();;

			//search on grid
			mWaitForVisible("id", mGetPropertyFromFile("LGL_SearchAppBtntid"));
			mCustomWait(2000);
			mClick("id", mGetPropertyFromFile("LGL_SearchAppBtntid"));

			mCustomWait(1000);

			//Edit
			mWaitForVisible("xpath", mGetPropertyFromFile("LGL_ChViewDetailsid"));
			mCustomWait(2000);
			mTakeScreenShot();;
			mClick("xpath", mGetPropertyFromFile("LGL_ChViewDetailsid"));

		
			//Detail Edit
			mWaitForVisible("xpath", mGetPropertyFromFile("LGL_ChDetailViewDetailsid"));
			mCustomWait(2000);

			// to verify the fetch data with originally entered data
			verifyCaseHeDateData(mGetPropertyFromFile("lgl_VerifyCseHearEntryDetDateId"));
			mCustomWait(3000);
			 
			///////////////////////swapcode to select scheduled////////////////////
			
			
			int hearingrowcount=driver.findElements(By.xpath("//*[@id=\"gridLegalCaseHearingDetailsEntryPopup\"]/tbody/tr")).size();
			for (int j = 1; j <= hearingrowcount; j++) {
				String scheduled1="//*[@id=\"gridLegalCaseHearingDetailsEntryPopup\"]/tbody/tr["+j+"]/td[7]";
				String view="//*[@id=\"gridLegalCaseHearingDetailsEntryPopup\"]/tbody/tr["+j+"]/td[8]/form/a/img";
				String scheduled=driver.findElement(By.xpath(scheduled1)).getAttribute("title");
				if (scheduled.equalsIgnoreCase("Scheduled")) {
					
					driver.findElement(By.xpath(view)).click();
				}
			}
				
			//mClick("xpath", mGetPropertyFromFile("LGL_ChDetailViewDetailsid"));	
	          //////////////////////END swapcode to select scheduled////////////////////
			
			//selecting judge name
			mWaitForVisible("id", mGetPropertyFromFile("LGL_JudgeNameid"));
			mCustomWait(2000);

			if(mGetPropertyFromFile("lgl_AddNewJudge").equalsIgnoreCase("Yes"))
			{
				String Fname = mGetPropertyFromFile("lgl_JudgeFirstNameData");
				String Mname = mGetPropertyFromFile("lgl_JudgeMiddleNameData");
				String Lname = mGetPropertyFromFile("lgl_JudgeLastNameData");

				String NewJudgeName = Fname+" "+Mname+" "+Lname;					
				System.out.println("NewJudgeName is : " + NewJudgeName); 

				mClick("id", mGetPropertyFromFile("LGL_JudgeNameid"));
				mSelectDropDown("id", mGetPropertyFromFile("LGL_JudgeNameid"), NewJudgeName);
			}

			else
			{
				mClick("id", mGetPropertyFromFile("LGL_JudgeNameid"));
				mSelectDropDown("id", mGetPropertyFromFile("LGL_JudgeNameid"), mGetPropertyFromFile("LGL_JudgeNameData"));				
			}

			//selecting Attorney Name
			mWaitForVisible("id", mGetPropertyFromFile("LGL_AtronamyNameid"));
			mCustomWait(2000);
			mSelectDropDown("id", mGetPropertyFromFile("LGL_AtronamyNameid"), mGetPropertyFromFile("LGL_AtronamyNameData"));


			// changes done by Hiren Kathiria on 6-6-2016  

			if(mGetPropertyFromFile("LGL_AtronamyNameData").equalsIgnoreCase("others"))
			{
				mSendKeys("id", mGetPropertyFromFile("LGL_AtronamyNameOthersid"), mGetPropertyFromFile("LGL_AtronamyNameOthersData"));		
			}
			////////////////////////
			

			//selecting rating id
			mWaitForVisible("id", mGetPropertyFromFile("LGL_Ratingid"));
			mCustomWait(2000);
			mSelectDropDown("id", mGetPropertyFromFile("LGL_Ratingid"), mGetPropertyFromFile("LGL_RatingData"));


			//selecting Hearing status
			mWaitForVisible("id", mGetPropertyFromFile("LGL_HearingStatusid"));
			mCustomWait(2000);
			mSelectDropDown("id", mGetPropertyFromFile("LGL_HearingStatusid"),status);

			//Remarks
			mWaitForVisible("id", mGetPropertyFromFile("LGL_Remarksid"));
			mCustomWait(2000);
			mSendKeys("id", mGetPropertyFromFile("LGL_Remarksid"), mGetPropertyFromFile("LGL_RemarksData"));

			//selecting multiple Attendees
		/*	ClubbedUtils.lgl_mDynamicAddAttendees(mGetPropertyFromFile("LGL_AttendeesTabid"),
					mGetPropertyFromFile("LGL_AddRowAttendeesid"),
					mGetPropertyFromFile("LGL_AddAttendees0Data"),
					mGetPropertyFromFile("LGL_AddAttendeesCount"));*/

			//removing multiple Attendees
			/*ClubbedUtils.lgl_mDynamicRemoveAttendees(mGetPropertyFromFile("LGL_AttendeesTabid"),
					mGetPropertyFromFile("LGL_RemoveRowAttendeesid"),
					mGetPropertyFromFile("LGL_RemoveAttendeesCount"));		*/					
			
		
			int atcount = Integer.parseInt(mGetPropertyFromFile("LGL_AddAttendeesCount"));
			for(int l=0;l<=atcount;l++)
						{
					Select listbox = new Select(driver.findElement(By.id("mempEmpid"+l)));
				listbox.selectByIndex(l+1);
				if(l<atcount)
				{
				mClick("id", "addRow");
			
				}
				
				}
			mCustomWait(2000);
			String rt = mGetPropertyFromFile("LGL_RemoveAttendeesCount");
			if(rt!= "字")
			{
				int rcount=Integer.parseInt(mGetPropertyFromFile("LGL_RemoveAttendeesCount"));
				if(rcount>0)
				{
				for(int r=0;r<rcount;r++)
				{
			mClick("id", "removeRow");
			}
			}
			}
			mCustomWait(2000);
			mTakeScreenShot();

			//hearing submit commited for dropdown select
			mWaitForVisible("css", mGetPropertyFromFile("LGL_HearingDetailsSubBtnid"));
			mCustomWait(2000);
			mClick("css", mGetPropertyFromFile("LGL_HearingDetailsSubBtnid"));
			mCustomWait(2000);
			mTakeScreenShot();;

			//Pop UP
			mWaitForVisible("css", mGetPropertyFromFile("LGL_FancyMsgCloseid"));
			mCustomWait(1500);

			//getting pop up text message
			String PopUpMsg = mGetText("css", mGetPropertyFromFile("LGL_HearingDetailPopAssertMsgid"));
			mCustomWait(2000);
			mAssert(PopUpMsg, mGetPropertyFromFile("LGL_HearingDetailPopAssertMsgData"), "At Case Hearing Entry Details :::: Actual msg:"+PopUpMsg+" Expected msg:"+mGetPropertyFromFile("LGL_HearingDetailPopAssertMsgData"));
			mCustomWait(2000);

			mClick("css", mGetPropertyFromFile("LGL_FancyMsgCloseid"));
			
			
		///	if(mGetPropertyFromFile("LGL_EndToEndConcluded").equalsIgnoreCase("Concluded"))
				if(mGetPropertyFromFile("LGL_EndToEndReScheduled").equalsIgnoreCase("Concluded"))
			{
				mCustomWait(2000);
				mSelectDropDown("id", mGetPropertyFromFile("lgl_case_finalstatus_id"), mGetPropertyFromFile("lgl_case_finalstatus_data"));
				
			}
			

			
			//saving application
			mWaitForVisible("css", mGetPropertyFromFile("LGL_CseHerinEntrySaveBtnid"));
			mCustomWait(2000);
			mTakeScreenShot();
			mCustomWait(2000);
			mClick("css", mGetPropertyFromFile("LGL_CseHerinEntrySaveBtnid"));
			mCustomWait(1000);
			//EW.excelWriting(mGetPropertyFromFile("refpath"), "LGL_refAppNoAtGridSearchdata", suiteNo);
			mCustomWait(2000);
			mWaitForVisible("id", mGetPropertyFromFile("LGL_CseHerinEntrySavePopUpBtnid"));
			mCustomWait(2000);
			String caseHearingEntryDetailPopMsg = mGetText("css", mGetPropertyFromFile("LGL_CseHerinEntrySaveAssertMsgid"));
			mCustomWait(1000);
			mAssert(caseHearingEntryDetailPopMsg, mGetPropertyFromFile("LGL_CseHerinEntrySaveAssertMsgData"), "Msg At Case Hearing Entry Details Save Button:::: Actual :"+caseHearingEntryDetailPopMsg+" Expected : "+mGetPropertyFromFile("LGL_CseHerinEntrySaveAssertMsgData"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);

			System.out.println(caseHearingEntryDetailPopMsg);
			mClick("id", mGetPropertyFromFile("LGL_CseHerinEntrySavePopUpBtnid"));
			
			
			mTakeScreenShot();
			
			
			
			


		}
 
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in caseHearingEntryDetails_Script");
		}
	}



	//05/032016
	public void refEvidencesNLegalOpinion_Script()
	{
		try
		{
			// changes done by Hiren Kathiria on 10-6-2016
			// to add new refEvidences and LegalOpinion for the case

			
			if(mGetPropertyFromFile("LGL_roAddRefEvi").equalsIgnoreCase("Yes") && mGetPropertyFromFile("LGL_roEditRefEvi").equalsIgnoreCase("No") || mGetPropertyFromFile("LGL_roAddLglOpi").equalsIgnoreCase("Yes") && mGetPropertyFromFile("LGL_roEditLglOpi").equalsIgnoreCase("No"))
			{
				//mWaitForVisible("linkText", mGetPropertyFromFile("LGL_roLegalLinkid"));
				mCustomWait(2000);	
				// two dependency to be done
				//Navigation from Legal link to Transaction link
				//mNavigation("LGL_roLegalLinkid", "LGL_roTransactionLinkid");
				mNavigation("LGL_roLegalLinkid", "LGL_roTransactionLinkid", "LGL_roRefNLglOpinionLinkid");

				//Reference Evidences And Legal Opinion Link
				mWaitForVisible("linkText", mGetPropertyFromFile("LGL_roRefNLglOpinionLinkid"));
				mCustomWait(2000);
				mClick("linkText", mGetPropertyFromFile("LGL_roRefNLglOpinionLinkid"));

				mCustomWait(2500);
				mTakeScreenShot();

				
				// changes done by Hiren Kathiria on 26-05-2016

				mcaseSearchBy();

				//searching Applications
				mWaitForVisible("linkText", mGetPropertyFromFile("LGL_roApplicationSearchid"));
				mCustomWait(2000);
				mTakeScreenShot();
				mCustomWait(1500);
				mClick("linkText", mGetPropertyFromFile("LGL_roApplicationSearchid"));

				//Grid search icon
				mWaitForVisible("css", mGetPropertyFromFile("LGL_roGridSearchIconid"));
				mCustomWait(2000);
				mClick("css", mGetPropertyFromFile("LGL_roGridSearchIconid"));

				//sending suit number
				mWaitForVisible("id", mGetPropertyFromFile("LGL_roGridSearchTextid"));
				mCustomWait(2000);
				mGenericWait();
				mClick("id", mGetPropertyFromFile("LGL_roGridSearchTextid"));
//				mSendKeys("id", mGetPropertyFromFile("LGL_roGridSearchTextid"),lgl_SuiteNo);
				//String suiteNo=mGetPropertyFromFile("LGL_refAppNoAtGridSearchdata");
				String suiteNo=mGetPropertyFromFile("lgl_SuiteNoData");
				mSendKeys("id", mGetPropertyFromFile("LGL_roGridSearchTextid"),suiteNo);

				mCustomWait(2000);
				mTakeScreenShot();
				mCustomWait(1000);

				//fancy Box grid search icon
				mWaitForVisible("id", mGetPropertyFromFile("LGL_roGridFindAppIconid"));
				mCustomWait(2000);
				mClick("id", mGetPropertyFromFile("LGL_roGridFindAppIconid"));

				mCustomWait(2000);
				mTakeScreenShot();;
				mCustomWait(1000);

				mWaitForVisible("xpath", mGetPropertyFromFile("LGL_roEditApplmgid"));
				mCustomWait(2000);
				mClick("xpath", mGetPropertyFromFile("LGL_roEditApplmgid"));
				//driver.findElement(By.cssSelector("img[alt=\"Edit Details\"]")).click();


				// to verify the fetch data with originally entered data
				verifyCaseHeDateData(mGetPropertyFromFile("LGL_VerifyroCaseDateId"));
				mCustomWait(3000);
				 

				// changes done by Hiren Kathiria on 9-6-2016
				
				// to add reference evidence 

				if(mGetPropertyFromFile("LGL_roAddRefEvi").equalsIgnoreCase("Yes"))
				{
				ClubbedUtils.lgl_mDynamicAddRefEvi(mGetPropertyFromFile("LGL_roAddRefEviCount"));
			//	mClick("css",mGetPropertyFromFile("lgl_ref_close"));
				}
								
				// to add legal opinion
				if(mGetPropertyFromFile("LGL_roAddLglOpi").equalsIgnoreCase("Yes"))
				{
					ClubbedUtils.lgl_mDynamicAddLglOpi(mGetPropertyFromFile("LGL_roAddLglOpiCount"));
				}		

	
				//Reference Evidences and Legal Opinion final submit
				mWaitForVisible("xpath", mGetPropertyFromFile("LGL_roRefNOpinionFinalSubBtnid"));
				mCustomWait(2000);
				mTakeScreenShot();;
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("LGL_roRefNOpinionFinalSubBtnid"));
				mTakeScreenShot();;

				//Proceed Button
				mWaitForVisible("id", mGetPropertyFromFile("LGL_roRefNOpnFinalPopUpBtnid"));
				mCustomWait(1000);
				String msgAtFinalSubOfRefEnv = mGetText("css", mGetPropertyFromFile("LGL_roRefNOpnFinalSubAssertMsgid"));
				mCustomWait(1000);
				mAssert(msgAtFinalSubOfRefEnv, mGetPropertyFromFile("LGL_roRefNOpnFinalSubAssertMsgData"), "Msg at References and Legal Opinion final submit pop up:::: Actual : "+msgAtFinalSubOfRefEnv+" Expected : "+mGetPropertyFromFile("LGL_roRefNOpnFinalSubAssertMsgData"));
				mCustomWait(1000);
				mTakeScreenShot();;
				mCustomWait(1000);
				System.out.println(msgAtFinalSubOfRefEnv);
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("LGL_roRefNOpnFinalPopUpBtnid"));

				//updating sequence Number
				mCustomWait(2000);
				mTakeScreenShot();
 //			    mUpdateSequenceNo();
			}	


			// to edit the refEvidences and LegalOpinion for the case

			else if(mGetPropertyFromFile("LGL_roEditRefEvi").equalsIgnoreCase("Yes") && mGetPropertyFromFile("LGL_roAddRefEvi").equalsIgnoreCase("No") || mGetPropertyFromFile("LGL_roEditLglOpi").equalsIgnoreCase("Yes") && mGetPropertyFromFile("LGL_roAddLglOpi").equalsIgnoreCase("No"))
			{ 
				mCustomWait(2000);
				//Navigation from Legal link to Transaction link
				mNavigation(mGetPropertyFromFile("LGL_roLegalLinkid"), mGetPropertyFromFile("LGL_roTransactionLinkid"));

				
				//Reference Evidences And Legal Opinion Link
				mWaitForVisible("linkText", mGetPropertyFromFile("LGL_roRefNLglOpinionLinkid"));
				mCustomWait(2000);
				mClick("linkText", mGetPropertyFromFile("LGL_roRefNLglOpinionLinkid"));

				mCustomWait(2000);
				mTakeScreenShot();;
				mCustomWait(1000);

				
				// changes done by Hiren Kathiria on 26-05-2016

				mcaseSearchBy();

				//searching Applications
				mWaitForVisible("linkText", mGetPropertyFromFile("LGL_roApplicationSearchid"));
				mCustomWait(2000);
				mTakeScreenShot();;
				mCustomWait(1000);
				mClick("linkText", mGetPropertyFromFile("LGL_roApplicationSearchid"));

				//Grid search icon
				mWaitForVisible("css", mGetPropertyFromFile("LGL_roGridSearchIconid"));
				mCustomWait(2000);
				mClick("css", mGetPropertyFromFile("LGL_roGridSearchIconid"));

				//sending suit number
				mWaitForVisible("id", mGetPropertyFromFile("LGL_roGridSearchTextid"));
				mCustomWait(2000);
				mGenericWait();
				mClick("id", mGetPropertyFromFile("LGL_roGridSearchTextid"));
//				mSendKeys("id", mGetPropertyFromFile("LGL_roGridSearchTextid"),lgl_SuiteNo);
				String suiteNo=mGetPropertyFromFile("lgl_SuiteNoData");
				mSendKeys("id", mGetPropertyFromFile("LGL_roGridSearchTextid"),suiteNo);

//              mSendKeys("id", mGetPropertyFromFile("LGL_roGridSearchTextid"), mGetPropertyFromFile("LGL_roGridSearchTextData"));

				mCustomWait(3000);
				mTakeScreenShot();;
				mCustomWait(1000);

				//fancy Box grid search icon
				mWaitForVisible("id", mGetPropertyFromFile("LGL_roGridFindAppIconid"));
				mCustomWait(2000);
				mClick("id", mGetPropertyFromFile("LGL_roGridFindAppIconid"));

				mCustomWait(2000);
				mTakeScreenShot();;
				mCustomWait(1000);

				mWaitForVisible("xpath", mGetPropertyFromFile("LGL_roEditApplmgid"));
				mCustomWait(2000);
				mClick("xpath", mGetPropertyFromFile("LGL_roEditApplmgid"));
				//driver.findElement(By.cssSelector("img[alt=\"Edit Details\"]")).click();

				// edit ref evidence
				mWaitForVisible("xpath", mGetPropertyFromFile("LGL_roGridRefEviSearchIconid"));
				mCustomWait(2000);

				// edit lgl opinion
				mWaitForVisible("xpath", mGetPropertyFromFile("LGL_roGridLglOpiSearchIconid"));
				mCustomWait(2000);


				//Reference Evidences and Legal Opinion final submit
				mWaitForVisible("xpath", mGetPropertyFromFile("LGL_roRefNOpinionFinalSubBtnid"));
				mCustomWait(2000);
				mTakeScreenShot();;
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("LGL_roRefNOpinionFinalSubBtnid"));
				mTakeScreenShot();;

				//Proceed Button
				mWaitForVisible("id", mGetPropertyFromFile("LGL_roRefNOpnFinalPopUpBtnid"));
				mCustomWait(1000);
				String msgAtFinalSubOfRefEnv = mGetText("css", mGetPropertyFromFile("LGL_roRefNOpnFinalSubAssertMsgid"));
				mCustomWait(1000);
				mAssert(msgAtFinalSubOfRefEnv, mGetPropertyFromFile("LGL_roRefNOpnFinalSubAssertMsgData"), "Msg at References and Legal Opinion final submit pop up:::: Actual : "+msgAtFinalSubOfRefEnv+" Expected : "+mGetPropertyFromFile("LGL_roRefNOpnFinalSubAssertMsgData"));
				mCustomWait(1000);
				mTakeScreenShot();;
				mCustomWait(1000);
				System.out.println(msgAtFinalSubOfRefEnv);
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("LGL_roRefNOpnFinalPopUpBtnid"));

				//updating sequence Number
				mCustomWait(2000);
				mTakeScreenShot();;
//				mUpdateSequenceNo();
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in refEvidencesNLegalOpinion_Script");
		}

	}


	//Sneha Solaskar
	//09/03/2016
	public void caseHearingEntry_Script()
	{
		try
		{
			mWaitForVisible("linkText", mGetPropertyFromFile("LGL_heLegalLinkid"));
			mCustomWait(2000);
			//Navigation from Legal link to Transaction link to Case hearing Entry Link
			mNavigation("LGL_heLegalLinkid", "LGL_heTransactionLinkid", "LGL_heCaseHearingEntryLinkid");

			
			// changes done by Hiren Kathiria on 26-05-2016

			mcaseSearchBy();

			//search
			mWaitForVisible("linkText", mGetPropertyFromFile("LGL_heSearchAppBtnid"));
			mCustomWait(2000);
			mClick("linkText", mGetPropertyFromFile("LGL_heSearchAppBtnid"));

			//Grid search icon
			mWaitForVisible("css", mGetPropertyFromFile("LGL_heGridSearchIconid"));
			mCustomWait(2000);
			mTakeScreenShot();;
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("LGL_heGridSearchIconid"));

			//sending suite number
			mWaitForVisible("id", mGetPropertyFromFile("LGL_heAppNoAtGridSearchid"));
			mCustomWait(2000);
//			mSendKeys("id", mGetPropertyFromFile("LGL_heAppNoAtGridSearchid"), lgl_SuiteNo); swapnil
			//String suiteNo=mGetPropertyFromFile("LGL_hedateAppNoAtGridSearchdata");
			
			String suiteNo=mGetPropertyFromFile("lgl_SuiteNoData");
			mSendKeys("id", mGetPropertyFromFile("LGL_heAppNoAtGridSearchid"), suiteNo);

			//mSendKeys("id", mGetPropertyFromFile("LGL_heAppNoAtGridSearchid"), mGetPropertyFromFile("LGL_heAppNoAtGridSearchdata"));

			//fancy Box search Button
			mWaitForVisible("id", mGetPropertyFromFile("LGL_heGridSearchFindBtnid"));
			mCustomWait(2000);
			mTakeScreenShot();;
			mCustomWait(1000);
			mClick("id", mGetPropertyFromFile("LGL_heGridSearchFindBtnid"));

			//Edit Details Icon
			mWaitForVisible("xpath", mGetPropertyFromFile("LGL_heEditDetailsIconid"));
			mCustomWait(2000);
			mTakeScreenShot();;
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("LGL_heEditDetailsIconid"));
			mWaitForVisible("id", mGetPropertyFromFile("LGL_heHearingDateid"));


			// to verify the fetch data with originally entered data
			verifyCaseHeDateData("lgl_VerifyCseHearingDateId");
			mCustomWait(3000);

			
			
			
			
			

			////change done by Hiren Kathiria on 8-6-2016
			////LGL_heHearingReSchDateData

			//swapnil added to close dialogue
			
			////swapnil date code hearing
			
			
			DateFormat df = new SimpleDateFormat("dd/MM/yy");
			DateFormat df1 = new SimpleDateFormat("dd/MMM/yyyy");
			 Date dateobj = new Date();
			// dateobj=dateobj;
			 System.out.println(df.format(dateobj));
			 System.out.println(df1.format(dateobj));
			 
			 SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
			 Calendar c = Calendar.getInstance();
			 c.setTime(new Date());
			
			/* c.add(Calendar.DATE, a); // Adding 5 days
			 String output = sdf.format(c.getTime());
			 System.out.println("sysout newdate=>"+output);*/
			
			
			
if(reschedule)           
{
	
	String no=mGetPropertyFromFile("No_of_days_afterhearig");
	int nodays = Integer.parseInt(no);
	
	 c.add(Calendar.DATE, nodays); // Adding 5 days
	 String output = sdf.format(c.getTime());
	 System.out.println("sysout newdate=>"+output);
	 ClubbedUtils.lgl_mDynamicAddRow( mGetPropertyFromFile("LGL_heHearingDateTabid"), mGetPropertyFromFile("LGL_heHearingAddBtnid"),output , mGetPropertyFromFile("LGL_heImpNoteData"),mGetPropertyFromFile("LGL_AddRowCount"));
	 	ClubbedUtils.lgl_mDynamicRemoveRow( mGetPropertyFromFile("LGL_heHearingDateTabid"), mGetPropertyFromFile("LGL_heHearingRemoveBtnid"),mGetPropertyFromFile("LGL_RemoveRowCount"));
	 
	 /*ClubbedUtils.lgl_mDynamicAddRow( mGetPropertyFromFile("LGL_heHearingDateTabid"), mGetPropertyFromFile("LGL_heHearingAddBtnid"), mGetPropertyFromFile("LGL_heHearingReSchDateData"), mGetPropertyFromFile("LGL_heImpNoteData"),mGetPropertyFromFile("LGL_AddRowCount"));
 	ClubbedUtils.lgl_mDynamicRemoveRow( mGetPropertyFromFile("LGL_heHearingDateTabid"), mGetPropertyFromFile("LGL_heHearingRemoveBtnid"),mGetPropertyFromFile("LGL_RemoveRowCount"));
 */	//to make it false
 	
}

else
{
	String no=mGetPropertyFromFile("No_of_days");
	int nodays = Integer.parseInt(no);
	
	 c.add(Calendar.DATE, nodays); // Adding 5 days
	 String output = sdf.format(c.getTime());
	 System.out.println("sysout newdate=>"+output);
	
/*	ClubbedUtils.lgl_mDynamicAddRow( mGetPropertyFromFile("LGL_heHearingDateTabid"), mGetPropertyFromFile("LGL_heHearingAddBtnid"), mGetPropertyFromFile("LGL_heHearingReSchDateData"), mGetPropertyFromFile("LGL_heImpNoteData"),mGetPropertyFromFile("LGL_AddRowCount"));
 	ClubbedUtils.lgl_mDynamicRemoveRow( mGetPropertyFromFile("LGL_heHearingDateTabid"), mGetPropertyFromFile("LGL_heHearingRemoveBtnid"),mGetPropertyFromFile("LGL_RemoveRowCount"));	
*/
 	ClubbedUtils.lgl_mDynamicAddRow( mGetPropertyFromFile("LGL_heHearingDateTabid"), mGetPropertyFromFile("LGL_heHearingAddBtnid"), output, mGetPropertyFromFile("LGL_heImpNoteData"),mGetPropertyFromFile("LGL_AddRowCount"));
 	ClubbedUtils.lgl_mDynamicRemoveRow( mGetPropertyFromFile("LGL_heHearingDateTabid"), mGetPropertyFromFile("LGL_heHearingRemoveBtnid"),mGetPropertyFromFile("LGL_RemoveRowCount"));	

 	
 	/* 	String s="/html/body/div[9]/div/div/a";
	driver.findElement(By.xpath(s)).click();*/
 	
}
	
 	
			mCustomWait(2000);

			//Save Button
			mWaitForVisible("css", mGetPropertyFromFile("LGL_heCseHearingEntrySaveBtnid"));
			mCustomWait(1000);
			mTakeScreenShot();;
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("LGL_heCseHearingEntrySaveBtnid"));
			mCustomWait(1000);
			mTakeScreenShot();;

			//Proceed Button
			mWaitForVisible("id", mGetPropertyFromFile("LGL_heCseHearinProcdBtnid"));
			mCustomWait(1000);
			String CseHeraingEntryAssertMsg = mGetText("css", mGetPropertyFromFile("LGL_heCseHearinSaveAssertMsgid"));
			mCustomWait(1000);
			mAssert(CseHeraingEntryAssertMsg, mGetPropertyFromFile("LGL_heCseHearinSaveAssertMsgData"), "Msg at case Hearing entry.:::: Actual : "+CseHeraingEntryAssertMsg+" Expected : "+mGetPropertyFromFile("LGL_heCseHearinSaveAssertMsgData"));
			mCustomWait(1000);
			mTakeScreenShot();;
			mCustomWait(1000);
			System.out.println(CseHeraingEntryAssertMsg);
			mCustomWait(1000);
			mClick("id", mGetPropertyFromFile("LGL_heCseHearinProcdBtnid"));
			mGenericWait();
			//EW.excelWriting(mGetPropertyFromFile("hearing_details_path"), "LGL_hedateAppNoAtGridSearchdata", suiteNo);
			mTakeScreenShot();;
			if(reschedule)           
			{	
			//	lgl_caseHearingEntryDetails();
				reschedule=false;
			 	
			}
			
			
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in caseHearingEntry_Script");
		}
	}



	/**
	 * @author Hiren Kathiria
	 * @since 21-06-2016
	 *
	 */


	public void courtDetails_Script() {

		try{ 
			mCustomWait(2000);				
			//Navigation from Legal link to Master link to Court details Link
			mNavigation(mGetPropertyFromFile("lgl_LegalLinkId"), mGetPropertyFromFile("lgl_MasterLinkId"), mGetPropertyFromFile("lgl_CourtDetailsLinkId"));


			if(mGetPropertyFromFile("lgl_AddNewCourt").equalsIgnoreCase("Yes") && mGetPropertyFromFile("lgl_EditNewCourt").equalsIgnoreCase("No") )
			{
				mCustomWait(2000);
				mClick("xpath", mGetPropertyFromFile("lgl_AddCourtNamesLinkId"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_CourtNameId"),mGetPropertyFromFile("lgl_CourtNameData"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_CourtAddressid"),mGetPropertyFromFile("lgl_CourtAddressData"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_districtid"),mGetPropertyFromFile("lgl_districtData"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_talukaId"),mGetPropertyFromFile("lgl_talukaData"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_City/TownId"),mGetPropertyFromFile("lgl_City/TownData"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_PincodeId"),mGetPropertyFromFile("lgl_PincodeData"));

				mCustomWait(1000);
				//			mClick("id", mGetPropertyFromFile("lgl_StatusId"));

				mCustomWait(1500);
				mClick("xpath", mGetPropertyFromFile("lgl_SubmitbtnId"));
				proceedCourtDetails_Script();
				SearchByCourtName();
			}	

			else if(mGetPropertyFromFile("lgl_AddNewCourt").equalsIgnoreCase("No") && mGetPropertyFromFile("lgl_EditNewCourt").equalsIgnoreCase("Yes"))
			{
				mWaitForVisible("css", mGetPropertyFromFile("lgl_CourtMasterSearchIconID"));
				mCustomWait(1500);
				mClick("css", mGetPropertyFromFile("lgl_CourtMasterSearchIconID"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_CourtMasterSearchId"));
				mCustomWait(1500);
				mSendKeys("id", mGetPropertyFromFile("lgl_CourtMasterSearchId"),mGetPropertyFromFile("lgl_CourtNameData"));

				mTab("id", mGetPropertyFromFile("lgl_CourtMasterSearchId"));
				mCustomWait(1500);
				mTakeScreenShot();;
				mClick("id",mGetPropertyFromFile("lgl_CourtMasterSearchBtnId"));

				mWaitForVisible("xpath",mGetPropertyFromFile("lgl_CourtMasterEditBtnId"));
				mCustomWait(1500);
				mClick("xpath",mGetPropertyFromFile("lgl_CourtMasterEditBtnId"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_CourtNameId"));
				mCustomWait(1500);			
				mSendKeys("id", mGetPropertyFromFile("lgl_CourtNameId"),mGetPropertyFromFile("lgl_EditCourtNameData"));

				mCustomWait(1500);			
				mSendKeys("id", mGetPropertyFromFile("lgl_CourtAddressid"),mGetPropertyFromFile("lgl_EditCourtAddressData"));

				mCustomWait(1500);
				mTakeScreenShot();;
				mClick("xpath", mGetPropertyFromFile("lgl_SubmitbtnId"));
				proceedCourtDetails_Script();
				SearchByCourtName();
			}
			else
			{
				System.out.println("No Action is been Performed");    
			}

		}


		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in courtDetails_Script");
		}
	}


	public void proceedCourtDetails_Script(){		

		try{
			mWaitForVisible("id", mGetPropertyFromFile("lgl_ProceedbtnId"));
			mCustomWait(1500);
			String CourtDetailsAssertMsg = mGetText("css", mGetPropertyFromFile("lgl_CourtDetailsProceedMsgId"));
			mCustomWait(1000);
			mAssert(CourtDetailsAssertMsg, mGetPropertyFromFile("lgl_CourtDetailsProceedMsgData"), "Msg at court details :::: Actual : "+CourtDetailsAssertMsg+" Expected : "+mGetPropertyFromFile("lgl_CourtDetailsProceedMsgData"));
			mCustomWait(1000);
			mClick("id", mGetPropertyFromFile("lgl_ProceedbtnId"));
			mTakeScreenShot();;

		}catch(Exception e){
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in proceedCourtDetails_Script");
		}
	}


	public void SearchByCourtName(){

		try{
			mWaitForVisible("css", mGetPropertyFromFile("lgl_CourtMasterSearchIconID"));
			mCustomWait(1500);
			mClick("css", mGetPropertyFromFile("lgl_CourtMasterSearchIconID"));

			mWaitForVisible("id",mGetPropertyFromFile("lgl_CourtMasterSearchId"));
			mCustomWait(1500);

			if(mGetPropertyFromFile("lgl_AddNewCourt").equalsIgnoreCase("Yes") && mGetPropertyFromFile("lgl_EditNewCourt").equalsIgnoreCase("No"))
			{
				mSendKeys("id", mGetPropertyFromFile("lgl_CourtMasterSearchId"),mGetPropertyFromFile("lgl_CourtNameData"));
			}
			else if(mGetPropertyFromFile("lgl_AddNewCourt").equalsIgnoreCase("No") && mGetPropertyFromFile("lgl_EditNewCourt").equalsIgnoreCase("Yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("lgl_CourtMasterSearchId"),mGetPropertyFromFile("lgl_EditCourtNameData"));
			}

			mTab("id", mGetPropertyFromFile("lgl_CourtMasterSearchId"));
			mCustomWait(1500);
			mTakeScreenShot();;
			mClick("id",mGetPropertyFromFile("lgl_CourtMasterSearchBtnId"));

			mWaitForVisible("xpath",mGetPropertyFromFile("lgl_CourtMasterEditBtnId"));
			mCustomWait(1500);
			mClick("xpath",mGetPropertyFromFile("lgl_CourtMasterEditBtnId"));

			mWaitForVisible("id",mGetPropertyFromFile("lgl_CourtNameId"));	
			mCustomWait(1000);
			mTakeScreenShot();;
			String CourtNameAssertMsg = mGetText("id",mGetPropertyFromFile("lgl_CourtNameId"),"value");
			System.out.println("CourtNameAssertMsg is :" +CourtNameAssertMsg+"entered name is :" +mGetPropertyFromFile("lgl_CourtNameData"));
			mCustomWait(1000);
			mAssert(CourtNameAssertMsg, mGetPropertyFromFile("lgl_CourtNameData"), "court name is :::: Actual : "+CourtNameAssertMsg+" Expected : "+mGetPropertyFromFile("lgl_CourtNameData"));

			String CourtAddAssertMsg = mGetText("id",mGetPropertyFromFile("lgl_CourtAddressid"),"value");
			System.out.println("CourtAddAssertMsg is :" +CourtAddAssertMsg+"entered address is :" +mGetPropertyFromFile("lgl_CourtAddressData"));
			mCustomWait(1000);
			mAssert(CourtAddAssertMsg, mGetPropertyFromFile("lgl_CourtAddressData"), "court address is :::: Actual : "+CourtAddAssertMsg+" Expected : "+mGetPropertyFromFile("lgl_CourtAddressData"));
			mCustomWait(1000);	
		}
		catch(Exception e){
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in SearchByCourtName Script");
		}

	}


	/**
	 * @author Hiren Kathiria
	 * -since 21-06-2016
	 *
	 */

	public void judgeMaster_Script() {

		try{ 
			mCustomWait(2000);
			//Navigation from Legal link to Master link to Judge master Link
			mNavigation(mGetPropertyFromFile("lgl_LegalLinkId"), mGetPropertyFromFile("lgl_MasterLinkId"), mGetPropertyFromFile("lgl_JudgeMasterLinkId"));

			if(mGetPropertyFromFile("lgl_AddNewJudge").equalsIgnoreCase("Yes") && mGetPropertyFromFile("lgl_EditNewJudge").equalsIgnoreCase("No"))
			{			
				mCustomWait(2000);
				mClick("xpath", mGetPropertyFromFile("lgl_AddJudgeDetailsLinkId"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_JudgeFirstNameId"),mGetPropertyFromFile("lgl_JudgeFirstNameData"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_JudgeMiddleNameId"),mGetPropertyFromFile("lgl_JudgeMiddleNameData"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_JudgeLastNameId"),mGetPropertyFromFile("lgl_JudgeLastNameData"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_Addressid"),mGetPropertyFromFile("lgl_AddressData"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_ContactNoid"),mGetPropertyFromFile("lgl_ContactNoData"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_EmailId"),mGetPropertyFromFile("lgl_EmailData"));

				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("lgl_CourtNameId"), mGetPropertyFromFile("lgl_CourtNameData"));

				mCustomWait(1000);
				//			mClick("id", mGetPropertyFromFile("lgl_ActiveId"));

				mCustomWait(1500);
				mClick("xpath", mGetPropertyFromFile("lgl_SavebtnId"));
				proceedJudgeMaster_Script();
				SearchByJudgeName();
			}				

			else if(mGetPropertyFromFile("lgl_AddNewJudge").equalsIgnoreCase("No") && mGetPropertyFromFile("lgl_EditNewJudge").equalsIgnoreCase("Yes"))
			{
				mWaitForVisible("css", mGetPropertyFromFile("lgl_JudgeMasterSearchIconID"));
				mCustomWait(1500);
				mClick("css", mGetPropertyFromFile("lgl_JudgeMasterSearchIconID"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_JudgeMasterSearchId"));
				mCustomWait(1500);
				mSendKeys("id", mGetPropertyFromFile("lgl_JudgeMasterSearchId"),mGetPropertyFromFile("lgl_JudgeFirstNameData"));

				mTab("id", mGetPropertyFromFile("lgl_JudgeMasterSearchId"));
				mCustomWait(1500);
				mTakeScreenShot();;
				mClick("id",mGetPropertyFromFile("lgl_JudgeMasterSearchBtnId"));

				mWaitForVisible("xpath",mGetPropertyFromFile("lgl_JudgeMasterEditBtnId"));
				mCustomWait(1500);
				mClick("xpath",mGetPropertyFromFile("lgl_JudgeMasterEditBtnId"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_Addressid"));
				mCustomWait(1500);			
				mSendKeys("id", mGetPropertyFromFile("lgl_Addressid"),mGetPropertyFromFile("lgl_EditAddressData"));

				mCustomWait(1500);			
				mSendKeys("id", mGetPropertyFromFile("lgl_ContactNoid"),mGetPropertyFromFile("lgl_EditContactNoData"));

				mCustomWait(1500);			
				mSendKeys("id", mGetPropertyFromFile("lgl_EmailId"),mGetPropertyFromFile("lgl_EditEmailData"));

				mCustomWait(1500);
				mSelectDropDown("id", mGetPropertyFromFile("lgl_CourtNameId"), mGetPropertyFromFile("lgl_EditCourtNameData"));

				mCustomWait(1500);
				mClick("xpath", mGetPropertyFromFile("lgl_SavebtnId"));
				proceedJudgeMaster_Script();	
				SearchByJudgeName();
			}	

			else
			{
				System.out.println("No Action is been Performed");    
			}

		}	

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in judgeMaster_Script");
		}
	}



	public void proceedJudgeMaster_Script(){		

		try{
			mWaitForVisible("id", mGetPropertyFromFile("lgl_ProceedbtnId"));
			mCustomWait(1500);
			String JudgeMasterAssertMsg = mGetText("css", mGetPropertyFromFile("lgl_JudgeMasterProceedMsgId"));
			mCustomWait(1000);
			mAssert(JudgeMasterAssertMsg, mGetPropertyFromFile("lgl_ProceedMsgData"), "Msg at proceed :::: Actual : "+JudgeMasterAssertMsg+" Expected : "+mGetPropertyFromFile("lgl_ProceedMsgData"));
			mCustomWait(1000);
			mClick("id", mGetPropertyFromFile("lgl_ProceedbtnId"));
			mTakeScreenShot();;

		}catch(Exception e){
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in proceedJudgeMaster_Script");
		}

	}


	public void SearchByJudgeName(){

		try{
			mWaitForVisible("css", mGetPropertyFromFile("lgl_JudgeMasterSearchIconID"));
			mCustomWait(1500);
			mClick("css", mGetPropertyFromFile("lgl_JudgeMasterSearchIconID"));

			mWaitForVisible("id",mGetPropertyFromFile("lgl_JudgeMasterSearchId"));
			mCustomWait(1500);
			mSendKeys("id", mGetPropertyFromFile("lgl_JudgeMasterSearchId"),mGetPropertyFromFile("lgl_JudgeFirstNameData"));

			mTab("id", mGetPropertyFromFile("lgl_JudgeMasterSearchId"));
			mCustomWait(1500);
			mTakeScreenShot();;
			mClick("id",mGetPropertyFromFile("lgl_JudgeMasterSearchBtnId"));

			mWaitForVisible("xpath",mGetPropertyFromFile("lgl_JudgeMasterEditBtnId"));
			mCustomWait(1500);
			mClick("xpath",mGetPropertyFromFile("lgl_JudgeMasterEditBtnId"));

			mWaitForVisible("id",mGetPropertyFromFile("lgl_JudgeFirstNameId"));	
			mCustomWait(1000);
			mTakeScreenShot();;
			String JudgeFirstNameAssertMsg = mGetText("id", mGetPropertyFromFile("lgl_JudgeFirstNameId"),"value");
			System.out.println("JudgeFirstName is " +JudgeFirstNameAssertMsg);
			mCustomWait(1000);
			mAssert(JudgeFirstNameAssertMsg, mGetPropertyFromFile("lgl_JudgeFirstNameData"), "judge first name is :::: Actual : "+JudgeFirstNameAssertMsg+" Expected : "+mGetPropertyFromFile("lgl_JudgeFirstNameData"));

			String JudgeMiddleNameAssertMsg = mGetText("id", mGetPropertyFromFile("lgl_JudgeMiddleNameId"),"value");
			System.out.println("JudgeMiddleName is " +JudgeMiddleNameAssertMsg);
			mCustomWait(1000);
			mAssert(JudgeMiddleNameAssertMsg, mGetPropertyFromFile("lgl_JudgeMiddleNameData"), "judge middle name is :::: Actual : "+JudgeMiddleNameAssertMsg+" Expected : "+mGetPropertyFromFile("lgl_JudgeMiddleNameData"));

			String JudgeLastNameAssertMsg = mGetText("id", mGetPropertyFromFile("lgl_JudgeLastNameId"),"value");
			System.out.println("JudgeLastName is " +JudgeLastNameAssertMsg);
			mCustomWait(1000);
			mAssert(JudgeLastNameAssertMsg, mGetPropertyFromFile("lgl_JudgeLastNameData"), "judge last name is :::: Actual : "+JudgeLastNameAssertMsg+" Expected : "+mGetPropertyFromFile("lgl_JudgeLastNameData"));

			String JudgeAddAssertMsg = mGetText("id", mGetPropertyFromFile("lgl_AddressId"),"value");
			System.out.println("JudgeAddress is " +JudgeAddAssertMsg);
			mCustomWait(1000);

			if(mGetPropertyFromFile("lgl_AddNewJudge").equalsIgnoreCase("Yes") && mGetPropertyFromFile("lgl_EditNewJudge").equalsIgnoreCase("No"))
			{
				mAssert(JudgeAddAssertMsg, mGetPropertyFromFile("lgl_AddressData"), "court address is :::: Actual : "+JudgeAddAssertMsg+" Expected : "+mGetPropertyFromFile("lgl_AddressData"));
			}

			else if(mGetPropertyFromFile("lgl_AddNewJudge").equalsIgnoreCase("No") && mGetPropertyFromFile("lgl_EditNewJudge").equalsIgnoreCase("Yes"))
			{
				mAssert(JudgeAddAssertMsg, mGetPropertyFromFile("lgl_EditAddressData"), "court address is :::: Actual : "+JudgeAddAssertMsg+" Expected : "+mGetPropertyFromFile("lgl_EditAddressData"));
			}	

			String ContactNoAssertMsg = mGetText("id",mGetPropertyFromFile("lgl_ContactNoid"));
			System.out.println("CourtName is " +ContactNoAssertMsg);
			mCustomWait(1000);

			if(mGetPropertyFromFile("lgl_AddNewJudge").equalsIgnoreCase("Yes") && mGetPropertyFromFile("lgl_EditNewJudge").equalsIgnoreCase("No"))
			{
				mAssert(ContactNoAssertMsg, mGetPropertyFromFile("lgl_ContactNoData"), "contact no is :::: Actual : "+ContactNoAssertMsg+" Expected : "+mGetPropertyFromFile("lgl_ContactNoData"));
			}

			else if(mGetPropertyFromFile("lgl_AddNewJudge").equalsIgnoreCase("No") && mGetPropertyFromFile("lgl_EditNewJudge").equalsIgnoreCase("Yes"))
			{
				mAssert(ContactNoAssertMsg, mGetPropertyFromFile("lgl_EditContactNoData"), "contact no is :::: Actual : "+ContactNoAssertMsg+" Expected : "+mGetPropertyFromFile("lgl_EditContactNoData"));
			}	

			String EmailIdAssertMsg = mGetText("id",mGetPropertyFromFile("lgl_EmailId"));
			System.out.println("CourtName is " +EmailIdAssertMsg);
			mCustomWait(1000);

			if(mGetPropertyFromFile("lgl_AddNewJudge").equalsIgnoreCase("Yes") && mGetPropertyFromFile("lgl_EditNewJudge").equalsIgnoreCase("No"))
			{
				mAssert(EmailIdAssertMsg, mGetPropertyFromFile("lgl_EmailData"), "email id is :::: Actual : "+EmailIdAssertMsg+" Expected : "+mGetPropertyFromFile("lgl_EmailData"));
			}

			else if(mGetPropertyFromFile("lgl_AddNewJudge").equalsIgnoreCase("No") && mGetPropertyFromFile("lgl_EditNewJudge").equalsIgnoreCase("Yes"))
			{
				mAssert(EmailIdAssertMsg, mGetPropertyFromFile("lgl_EditEmailData"), "email id is :::: Actual : "+EmailIdAssertMsg+" Expected : "+mGetPropertyFromFile("lgl_EditEmailData"));
			}	

			String CourtNameAssertMsg = mGetText("id",mGetPropertyFromFile("lgl_CourtNameId"));
			System.out.println("CourtName is " +CourtNameAssertMsg);
			mCustomWait(1000);

			if(mGetPropertyFromFile("lgl_AddNewJudge").equalsIgnoreCase("Yes") && mGetPropertyFromFile("lgl_EditNewJudge").equalsIgnoreCase("No"))
			{
				mAssert(CourtNameAssertMsg, mGetPropertyFromFile("lgl_CourtNameData"), "court name is :::: Actual : "+CourtNameAssertMsg+" Expected : "+mGetPropertyFromFile("lgl_CourtNameData"));
			}

			else if(mGetPropertyFromFile("lgl_AddNewJudge").equalsIgnoreCase("No") && mGetPropertyFromFile("lgl_EditNewJudge").equalsIgnoreCase("Yes"))
			{
				mAssert(CourtNameAssertMsg, mGetPropertyFromFile("lgl_EditCourtNameData"), "court name is :::: Actual : "+CourtNameAssertMsg+" Expected : "+mGetPropertyFromFile("lgl_EditCourtNameData"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in SearchByJudgeName script");
		}
	}	


	public void Reset_Script() {

		try{

			//	created by Hiren Kathiria on 29/6/2016

			// reset the Case master entry form

			if(mGetPropertyFromFile("lgl_CaseMasReset").equalsIgnoreCase("Yes")&&mGetPropertyFromFile("lgl_JudgeMasReset").equalsIgnoreCase("No")&&mGetPropertyFromFile("lgl_CourtDetailsReset").equalsIgnoreCase("No"))
			{
				mWaitForVisible("linkText",mGetPropertyFromFile("lgl_LegalLinkId"));	
				mCustomWait(1000);	

				//Navigation from Legal link to Transaction link to Case master link to Add case master link

				mNavigation(mGetPropertyFromFile("lgl_LegalLinkId"), mGetPropertyFromFile("lgl_TransactionLinkId"), mGetPropertyFromFile("lgl_CaseMasterLinkId"), mGetPropertyFromFile("lgl_AddCaseMasterId"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_SuiteNoId"));
				mCustomWait(1500);
				mTakeScreenShot();;

				String suiteNo=mGetPropertyFromFile("lgl_SuiteNoData");
				mSendKeys("id",mGetPropertyFromFile("lgl_SuiteNoId"), suiteNo);

				mWaitForVisible("id",mGetPropertyFromFile("lgl_CaseRefSuiteNoId"));
				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_CaseRefSuiteNoId"),mGetPropertyFromFile("lgl_CaseRefSuiteNoData"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_CaseTypeId"));
				mCustomWait(1000);
				mSelectDropDown("id",mGetPropertyFromFile("lgl_CaseTypeId"),mGetPropertyFromFile("lgl_CaseTypeData"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_CaseMasCategoryId"));
				mCustomWait(1000);
				mSelectDropDown("id",mGetPropertyFromFile("lgl_CaseMasCategoryId"),mGetPropertyFromFile("lgl_CaseMasCategoryData"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_OrgAsId"));
				mCustomWait(1000);
				mSelectDropDown("id",mGetPropertyFromFile("lgl_OrgAsId"),mGetPropertyFromFile("lgl_OrgAsData"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_CaseMasStatusId"));
				mCustomWait(1000);
				mSelectDropDown("id",mGetPropertyFromFile("lgl_CaseMasStatusId"),mGetPropertyFromFile("lgl_ResetCaseMasStatusData"));

				mWaitForVisible("id", mGetPropertyFromFile("lgl_CaseCourtId"));
				mCustomWait(1000);
				mSelectDropDown("id",mGetPropertyFromFile("lgl_CaseCourtId"),mGetPropertyFromFile("lgl_CaseCourtData"));

				if(mGetPropertyFromFile("lgl_ResetCaseMasStatusData").equalsIgnoreCase("Pending")||mGetPropertyFromFile("lgl_ResetCaseMasStatusData").equalsIgnoreCase("Stay Order"))
				{
					mDateSelect("id",mGetPropertyFromFile("lgl_CaseMasDateId"), mGetPropertyFromFile("lgl_CaseMasDateData"));
				}

				else
				{
					mDateSelect("id",mGetPropertyFromFile("lgl_CaseMasDateId"), mGetPropertyFromFile("lgl_CaseMasDateData"));
					mDateSelect("id",mGetPropertyFromFile("lgl_CaseClosedDateId"), mGetPropertyFromFile("lgl_CaseClosedDateData"));
					mCustomWait(1000);
				}

				mWaitForVisible("id",mGetPropertyFromFile("lgl_CaseDeptId"));
				mCustomWait(1000);
				mSelectDropDown("id",mGetPropertyFromFile("lgl_CaseDeptId"),mGetPropertyFromFile("lgl_CaseDeptData"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_AdvocateID"));
				mCustomWait(1000);
				mSelectDropDown("id",mGetPropertyFromFile("lgl_AdvocateID"),mGetPropertyFromFile("lgl_AdvocateData"));

				mWaitForVisible("id", mGetPropertyFromFile("lgl_MatterDetailOneId"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("lgl_MatterDetailOneId"),mGetPropertyFromFile("lgl_MatterDetailOneData"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_MatterDetailTwoId"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("lgl_MatterDetailTwoId"), mGetPropertyFromFile("lgl_MatterDetailTwoData"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_ActAppliedID"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("lgl_ActAppliedID"), mGetPropertyFromFile("lgl_ActAppliedData"));
				mTakeScreenShot();;

				mWaitForVisible("id",mGetPropertyFromFile("lgl_CaseRemarkId"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("lgl_CaseRemarkId"), mGetPropertyFromFile("lgl_CaseRemarkData"));

				mCustomWait(1000);
				mUpload("xpath", mGetPropertyFromFile("lgl_UploadId"), mGetPropertyFromFile("lgl_UploadData"));
				mTakeScreenShot();

				ClubbedUtils.lgl_mDynamicAddRowObjects(mGetPropertyFromFile("lgl_CaseMasPlaintiffTabId"),
						mGetPropertyFromFile("lgl_CaseMasPlaintiffAddBtnId"),
						mGetPropertyFromFile("lgl_CaseMasPlaintiffNameData"),
						mGetPropertyFromFile("lgl_CaseMasPlaintiffAddrData"),mGetPropertyFromFile("lgl_CaseMasPlaintiffDetailsCount"));
				ClubbedUtils.lgl_mDynamicAddRowObjects(mGetPropertyFromFile("lgl_CaseMasDefndTabId"),
						mGetPropertyFromFile("lgl_CaseMasDefndAddBtnId"),
						mGetPropertyFromFile("lgl_CaseMasDefendantNameData"),
						mGetPropertyFromFile("lgl_CaseMasDefendantAddrData"),mGetPropertyFromFile("lgl_CaseMasDefendantDetailsCount"));

				//Reset Button
				mWaitForVisible("id",mGetPropertyFromFile("lgl_ResetCaseMasterEntryId"));
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("lgl_ResetCaseMasterEntryId"));	
				mCustomWait(2000);

				String SuiteNo = mGetText("id", mGetPropertyFromFile("lgl_SuiteNoId"));
				mAssert(SuiteNo, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual Number :"+SuiteNo+" Expected Number :"+ "");
				System.out.println("Actual SuiteNo is : " +SuiteNo+ " Expected SuiteNo is : " + "");

				String Casetype = mCaptureSelectedValue("id", mGetPropertyFromFile("lgl_CaseTypeId"));
				mAssert(Casetype, "-- Select Type --",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Casetype+" Expected :"+ "-- Select Type --");
				System.out.println("Actual is : " +Casetype+  " Expected is : " + "-- Select Type --");

				String Casecategory = mCaptureSelectedValue("id", mGetPropertyFromFile("lgl_CaseMasCategoryId"));
				mAssert(Casecategory, "-- Select Category --",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Casecategory+" Expected :"+ "-- Select Category --");
				System.out.println("Actual is : " +Casecategory+  " Expected is : " + "-- Select Category --");

				String Organization = mCaptureSelectedValue("id", mGetPropertyFromFile("lgl_OrgAsId"));
				mAssert(Organization, "-- Select Organisation --",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Organization+" Expected :"+ "-- Select Organisation --");
				System.out.println("Actual is : " +Organization+  " Expected is : " + "-- Select Organisation --");

				String Casestatus = mCaptureSelectedValue("id", mGetPropertyFromFile("lgl_CaseMasStatusId"));
				mAssert(Casestatus, "-- Select Case Status --",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Casestatus+" Expected :"+ "-- Select Case Status --");
				System.out.println("Actual is : " +Casestatus+  " Expected is : " + "-- Select Case Status --");

				String Court = mCaptureSelectedValue("id", mGetPropertyFromFile("lgl_CaseCourtId"));
				mAssert(Court, "-- Select Court --",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Court+" Expected :"+ "-- Select Court --");
				System.out.println("Actual is : " +Court+  " Expected is : " + "-- Select Court --");

				String Department = mCaptureSelectedValue("id", mGetPropertyFromFile("lgl_CaseDeptId"));
				mAssert(Department, "-- Select Department --",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Department+" Expected :"+ "-- Select Department --");
				System.out.println("Actual is : " +Department+  " Expected is : " + "-- Select Department --");

				String Attorneyname = mCaptureSelectedValue("id", mGetPropertyFromFile("lgl_AdvocateID"));
				mAssert(Attorneyname, "-- Select Attorney --",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Attorneyname+" Expected :"+ "-- Select Attorney --");
				System.out.println("Actual is : " +Attorneyname+  " Expected is : " + "-- Select Attorney --");

				String Matterdet1 = mGetText("id", mGetPropertyFromFile("lgl_MatterDetailOneId"));
				mAssert(Matterdet1, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Matterdet1+" Expected :"+ "");
				System.out.println("Actual is : " +Matterdet1+ " Expected is : " + "");

				String Actapplied = mGetText("id", mGetPropertyFromFile("lgl_ActAppliedID"));
				mAssert(Actapplied, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Actapplied+" Expected :"+ "");
				System.out.println("Actual is : " +Actapplied+ " Expected is : " + "");

				String Plaintiffname = mGetText("id", mGetPropertyFromFile("lgl_PlaintiffnameId"));
				mAssert(Plaintiffname, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Plaintiffname+" Expected :"+ "");
				System.out.println("Actual is : " +Plaintiffname+ " Expected is : " + "");

				String Plaintiffadd = mGetText("id", mGetPropertyFromFile("lgl_PlaintiffaddId"));
				mAssert(Plaintiffadd, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Plaintiffadd+" Expected :"+ "");
				System.out.println("Actual is : " +Plaintiffadd+ " Expected is : " + "");

				String Defendantname = mGetText("id", mGetPropertyFromFile("lgl_DefendantnameId"));
				mAssert(Defendantname, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Defendantname+" Expected :"+ "");
				System.out.println("Actual is : " +Defendantname+ " Expected is : " + "");

				String Defendantadd = mGetText("id", mGetPropertyFromFile("lgl_DefendantaddId"));
				mAssert(Defendantadd, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Defendantadd+" Expected :"+ "");
				System.out.println("Actual is : " +Defendantadd+ " Expected is : " + "");

				mTakeScreenShot();		
				mscroll(0,-250);
				mCustomWait(2000);
				mTakeScreenShot();	
			}	

			// reset the Case party details form

			if(mGetPropertyFromFile("lgl_CasePartyReset").equalsIgnoreCase("Yes"))
			{
				//Navigation from Legal link to transaction link to Case party details link

				mWaitForVisible("linkText", mGetPropertyFromFile("lgl_LegalLinkId"));
				mCustomWait(1000);					
				mNavigation(mGetPropertyFromFile("lgl_LegalLinkId"), mGetPropertyFromFile("lgl_TransactionLinkId"), mGetPropertyFromFile("lgl_CasePartyDetLinkId"));

				mcaseSearchBy();
				mCustomWait(1000);
				mTakeScreenShot();;
				mWaitForVisible("linkText",mGetPropertyFromFile("lgl_CasePartyDetSearchBtnId"));
				mCustomWait(1500);
				mClick("linkText",mGetPropertyFromFile("lgl_CasePartyDetSearchBtnId"));
				mCustomWait(1500);

				mWaitForVisible("css",mGetPropertyFromFile("lgl_CasePartyDetSearchIconID"));
				mCustomWait(1500);
				mClick("css",mGetPropertyFromFile("lgl_CasePartyDetSearchIconID"));

				mWaitForVisible("id",mGetPropertyFromFile("lgl_CasePartyDetSuiteSearchId"));
				mCustomWait(1500);
				String suiteNo=mGetPropertyFromFile("lgl_SuiteNoData");
				mSendKeys("id", mGetPropertyFromFile("lgl_CasePartyDetSuiteSearchId"),suiteNo);

				mWaitForVisible("id",mGetPropertyFromFile("lgl_CasePartyDetSuiteSearchBtnId"));
				mCustomWait(1500);
				mTakeScreenShot();;
				mClick("id",mGetPropertyFromFile("lgl_CasePartyDetSuiteSearchBtnId"));

				mWaitForVisible("xpath",mGetPropertyFromFile("lgl_CasePartyDetEditBtnId"));
				mCustomWait(1500);
				mClick("xpath",mGetPropertyFromFile("lgl_CasePartyDetEditBtnId"));
				mTakeScreenShot();;

				// to add the party details rows

				ClubbedUtils.lgl_mDynamicAddRowObjects(mGetPropertyFromFile("lgl_CasePartyDetPlaintiffTabId"),
						mGetPropertyFromFile("lgl_CasePartyDetPlaintiffAddBtnId"),
						mGetPropertyFromFile("lgl_CasePartyDetPlaintiffNameData01"),
						mGetPropertyFromFile("lgl_CasePartyDetPlaintiffAddrData01"),mGetPropertyFromFile("lgl_CasePartyDetPlaintiffDetailsAddCount"));
				ClubbedUtils.lgl_mDynamicAddRowObjects(mGetPropertyFromFile("lgl_CasePartyDetDefndTabId"),
						mGetPropertyFromFile("lgl_CasePartyDetDefndAddBtnId"),
						mGetPropertyFromFile("lgl_CasePartyDetDefendantNameData01"),
						mGetPropertyFromFile("lgl_CasePartyDetDefendantAddrData01"),mGetPropertyFromFile("lgl_CasePartyDetDefendantDetailsAddCount"));

				//Reset Button
				mWaitForVisible("id",mGetPropertyFromFile("lgl_ResetCasePartyDetailsId"));
				mCustomWait(1500);
				mClick("id",mGetPropertyFromFile("lgl_ResetCasePartyDetailsId"));
				mCustomWait(1500);
				mTakeScreenShot();;

				
				// reset the Case hearing entry form

				if(mGetPropertyFromFile("lgl_CaseHearingReset").equalsIgnoreCase("Yes"))
				{				
					mWaitForVisible("linkText", mGetPropertyFromFile("LGL_heLegalLinkid"));
					mCustomWait(1500);
					//Navigation from Legal link to Transaction link to Case hearing Entry Link
					mNavigation(mGetPropertyFromFile("LGL_heLegalLinkid"), mGetPropertyFromFile("LGL_heTransactionLinkid"), mGetPropertyFromFile("LGL_heCaseHearingEntryLinkid"));

					mcaseSearchBy();

					//search
					mWaitForVisible("linkText", mGetPropertyFromFile("LGL_heSearchAppBtnid"));
					mCustomWait(1500);
					mTakeScreenShot();;
					mCustomWait(1000);
					mClick("linkText", mGetPropertyFromFile("LGL_heSearchAppBtnid"));

					//Grid search icon
					mWaitForVisible("css", mGetPropertyFromFile("LGL_heGridSearchIconid"));
					mCustomWait(1500);
					mClick("css", mGetPropertyFromFile("LGL_heGridSearchIconid"));

					//sending suite number
					mWaitForVisible("id", mGetPropertyFromFile("LGL_heAppNoAtGridSearchid"));
					mCustomWait(1500);
					mSendKeys("id", mGetPropertyFromFile("LGL_heAppNoAtGridSearchid"), suiteNo);

					//fancy Box search Button
					mWaitForVisible("id", mGetPropertyFromFile("LGL_heGridSearchFindBtnid"));
					mCustomWait(1500);
					mTakeScreenShot();;
					mCustomWait(1000);
					mClick("id", mGetPropertyFromFile("LGL_heGridSearchFindBtnid"));

					//Edit Details Icon
					mWaitForVisible("xpath", mGetPropertyFromFile("LGL_heEditDetailsIconid"));
					mCustomWait(1500);
					mTakeScreenShot();;
					mCustomWait(1000);
					mClick("xpath", mGetPropertyFromFile("LGL_heEditDetailsIconid"));
					mWaitForVisible("id", mGetPropertyFromFile("LGL_heHearingDateid"));

					//LGL_heHearingReSchDateData

					ClubbedUtils.lgl_mDynamicAddRow( mGetPropertyFromFile("LGL_heHearingDateTabid"), mGetPropertyFromFile("LGL_heHearingAddBtnid"), mGetPropertyFromFile("LGL_heHearingReSchDateData"), mGetPropertyFromFile("LGL_heImpNoteData"),mGetPropertyFromFile("LGL_AddRowCount"));
					mCustomWait(1500);

					//Reset Button
					mWaitForVisible("id", mGetPropertyFromFile("LGL_heCseHearingEntryResetBtnid"));
					mCustomWait(1000);
					mClick("id", mGetPropertyFromFile("LGL_heCseHearingEntryResetBtnid"));
					mCustomWait(1000);
					mTakeScreenShot();;

				}
			}

			
			// Reset Court Details form
			// created by hiren Kathiria on 4/7/2016

			if(mGetPropertyFromFile("lgl_CourtDetailsReset").equalsIgnoreCase("Yes") && mGetPropertyFromFile("lgl_CaseMasReset").equalsIgnoreCase("No") && mGetPropertyFromFile("lgl_JudgeMasReset").equalsIgnoreCase("No"))
			{
				mCustomWait(2000);		

				//Navigation from Legal link to Master link to Court details Link
				mNavigation(mGetPropertyFromFile("lgl_LegalLinkId"), mGetPropertyFromFile("lgl_MasterLinkId"), mGetPropertyFromFile("lgl_CourtDetailsLinkId"));

				mCustomWait(2000);
				mClick("xpath", mGetPropertyFromFile("lgl_AddCourtNamesLinkId"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_CourtNameId"),mGetPropertyFromFile("lgl_CourtNameData"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_CourtAddressid"),mGetPropertyFromFile("lgl_CourtAddressData"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_districtid"),mGetPropertyFromFile("lgl_districtData"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_talukaId"),mGetPropertyFromFile("lgl_talukaData"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_City/TownId"),mGetPropertyFromFile("lgl_City/TownData"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_PincodeId"),mGetPropertyFromFile("lgl_PincodeData"));

				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("lgl_CourtDetResetbtnId"));
				mCustomWait(3000);

				String Courtname = mGetText("id", mGetPropertyFromFile("lgl_CourtNameId"));
				mAssert(Courtname, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Courtname+" Expected :"+ "");
				System.out.println("Actual is : " +Courtname+ " Expected is : " + "");

				String Courtadd = mGetText("id", mGetPropertyFromFile("lgl_CourtAddressid"));
				mAssert(Courtadd, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Courtadd+" Expected :"+ "");
				System.out.println("Actual is : " +Courtadd+ " Expected is : " + "");

				String District = mGetText("id", mGetPropertyFromFile("lgl_districtid"));
				mAssert(District, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+District+" Expected :"+ "");
				System.out.println("Actual is : " +District+ " Expected is : " + "");

				String Block = mGetText("id", mGetPropertyFromFile("lgl_talukaId"));
				mAssert(Block, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Block+" Expected :"+ "");
				System.out.println("Actual is : " +Block+ " Expected is : " + "");

				String City_Town = mGetText("id", mGetPropertyFromFile("lgl_City/TownId"));
				mAssert(City_Town, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+City_Town+" Expected :"+ "");
				System.out.println("Actual is : " +City_Town+ " Expected is : " + "");

				String Pincode = mGetText("id", mGetPropertyFromFile("lgl_PincodeId"));
				mAssert(Pincode, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Pincode+" Expected :"+ "");
				System.out.println("Actual is : " +Pincode+ " Expected is : " + "");

				mTakeScreenShot();
				mCustomWait(2000);					
			}

			
			
			// Reset Judge Master form
			// created by hiren Kathiria on 4/7/2016
			
			if(mGetPropertyFromFile("lgl_JudgeMasReset").equalsIgnoreCase("Yes") && mGetPropertyFromFile("lgl_CourtDetailsReset").equalsIgnoreCase("No") && mGetPropertyFromFile("lgl_CaseMasReset").equalsIgnoreCase("No"))
			{
				mCustomWait(2000);		

				mNavigation(mGetPropertyFromFile("lgl_LegalLinkId"), mGetPropertyFromFile("lgl_MasterLinkId"), mGetPropertyFromFile("lgl_JudgeMasterLinkId"));

				mCustomWait(2000);
				mClick("xpath", mGetPropertyFromFile("lgl_AddJudgeDetailsLinkId"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_JudgeFirstNameId"),mGetPropertyFromFile("lgl_JudgeFirstNameData"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_JudgeMiddleNameId"),mGetPropertyFromFile("lgl_JudgeMiddleNameData"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_JudgeLastNameId"),mGetPropertyFromFile("lgl_JudgeLastNameData"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_Addressid"),mGetPropertyFromFile("lgl_AddressData"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_ContactNoid"),mGetPropertyFromFile("lgl_ContactNoData"));

				mCustomWait(1000);			
				mSendKeys("id", mGetPropertyFromFile("lgl_EmailId"),mGetPropertyFromFile("lgl_EmailData"));

				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("lgl_CourtNameId"), mGetPropertyFromFile("lgl_CourtNameData"));

				mCustomWait(1500);
				mClick("id", mGetPropertyFromFile("lgl_JudgeMstResetbtnId"));
				mCustomWait(3000);

				String Firstname = mGetText("id", mGetPropertyFromFile("lgl_JudgeFirstNameId"));
				mAssert(Firstname, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Firstname+" Expected :"+ "");
				System.out.println("Actual is : " +Firstname+ " Expected is : " + "");

				String Middlename = mGetText("id", mGetPropertyFromFile("lgl_JudgeMiddleNameId"));
				mAssert(Middlename, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Middlename+" Expected :"+ "");
				System.out.println("Actual is : " +Middlename+ " Expected is : " + "");

				String Lastname = mGetText("id", mGetPropertyFromFile("lgl_JudgeLastNameId"));
				mAssert(Lastname, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Lastname+" Expected :"+ "");
				System.out.println("Actual is : " +Lastname+ " Expected is : " + "");

				String Address = mGetText("id", mGetPropertyFromFile("lgl_Addressid"));
				mAssert(Address, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Address+" Expected :"+ "");
				System.out.println("Actual is : " +Address+ " Expected is : " + "");

				String ContactNo = mGetText("id", mGetPropertyFromFile("lgl_ContactNoid"));
				mAssert(ContactNo, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+ContactNo+" Expected :"+ "");
				System.out.println("Actual is : " +ContactNo+ " Expected is : " + "");

				String EmailId = mGetText("id", mGetPropertyFromFile("lgl_EmailId"));
				mAssert(EmailId, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+EmailId+" Expected :"+ "");
				System.out.println("Actual is : " +EmailId+ " Expected is : " + "");
				
				String Courtname = mCaptureSelectedValue("id", mGetPropertyFromFile("lgl_CourtNameId"));
						//driver.findElement(By.id("cseCrtId")).getAttribute("value");
				mAssert(Courtname, "-- Select Court --",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual :"+Courtname+" Expected :"+ "-- Select Court --");
				System.out.println("Actual is : " +Courtname+  " Expected is : " + "-- Select Court --");

				mTakeScreenShot();
				mCustomWait(2000);					
			}
			
			else
			{
				System.out.println("Something is wrong, please check the conditions"); 
			}

		}	

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Reset_Script");
		}
	}


	// created by Hiren Kathiria on 1/7/2016

	public void verifyCasePartyDetData() {

		try {
			mCustomWait(3000);

			String RefSuiteNo = mGetText("id", mGetPropertyFromFile("lgl_VerifyRefSuiteNoId"),"value");
			mAssert(RefSuiteNo, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual Number :"+RefSuiteNo+" Expected Number : "+ "");
			System.out.println("Actual is : " +RefSuiteNo+ " Expected is : " + "");

			String Organization = mCaptureSelectedValue("id", mGetPropertyFromFile("lgl_VerifyOrgAsId"));
			mAssert(Organization,mGetPropertyFromFile("lgl_OrgAsData"),mGetPropertyFromFile("lgl_IsDataMatch")+"Actual : "+Organization+" Expected : "+mGetPropertyFromFile("lgl_OrgAsData"));
			System.out.println("Actual is : " +Organization+  " Expected is : " + mGetPropertyFromFile("lgl_OrgAsData"));

			String Department  = mCaptureSelectedValue("id", mGetPropertyFromFile("lgl_VerifyCaseDeptId"));
			mAssert(Department , mGetPropertyFromFile("lgl_CaseDeptData"),mGetPropertyFromFile("lgl_IsDataMatch")+" Actual : "+Department +" Expected : "+ mGetPropertyFromFile("lgl_CaseDeptData"));
			System.out.println("Actual is : " +Department +  " Expected is : " + mGetPropertyFromFile("lgl_CaseDeptData"));

			String MatterDetail1 = mGetText("id", mGetPropertyFromFile("lgl_VerifyMatterDetailOneId"),"value");
			mAssert(MatterDetail1, mGetPropertyFromFile("lgl_MatterDetailOneData"),mGetPropertyFromFile("lgl_IsDataMatch")+" Actual : "+MatterDetail1+" Expected :"+ mGetPropertyFromFile("lgl_MatterDetailOneData"));
			System.out.println("Actual is : " +MatterDetail1+ " Expected is : " + mGetPropertyFromFile("lgl_MatterDetailOneData"));

			String MatterDetail2 = mGetText("id", mGetPropertyFromFile("lgl_VerifyMatterDetailTwoId"),"value");
			mAssert(MatterDetail2, mGetPropertyFromFile("lgl_MatterDetailTwoData"),mGetPropertyFromFile("lgl_IsDataMatch")+" Actual : "+MatterDetail2+" Expected :"+ mGetPropertyFromFile("lgl_MatterDetailTwoData"));
			System.out.println("Actual is : " +MatterDetail2+ " Expected is : " + mGetPropertyFromFile("lgl_MatterDetailTwoData"));

			String ActApplied  = mGetText("id", mGetPropertyFromFile("lgl_VerifyActAppliedID"),"value");
			mAssert(ActApplied, mGetPropertyFromFile("lgl_ActAppliedData"),mGetPropertyFromFile("lgl_IsDataMatch")+" Actual : "+ActApplied+" Expected :"+ mGetPropertyFromFile("lgl_ActAppliedData"));
			System.out.println("Actual is : " +ActApplied+ " Expected is : " + mGetPropertyFromFile("lgl_ActAppliedData"));

			String Remark = mGetText("id", mGetPropertyFromFile("lgl_VerifyCaseRemarkId"),"value");
			mAssert(Remark, mGetPropertyFromFile("lgl_CaseRemarkData"),mGetPropertyFromFile("lgl_IsDataMatch")+" Actual : "+Remark+" Expected :"+ mGetPropertyFromFile("lgl_CaseRemarkData"));
			System.out.println("Actual is : " +Remark+ " Expected is : " + mGetPropertyFromFile("lgl_CaseRemarkData"));
		}	

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in verifyCasePartyDetData script");
		}

	}


	// created by Hiren Kathiria on 1/7/2016

	public void verifyCaseHeDateData(String locator) {

		try {
			mCustomWait(3000);

			String SuiteNo = mGetText("id", mGetPropertyFromFile("lgl_VerifySuiteNoId"),"value");
			String suiteNo=mGetPropertyFromFile("lgl_SuiteNoData");
			mAssert(SuiteNo, suiteNo,mGetPropertyFromFile("lgl_IsDataMatch")+" Actual Number :"+SuiteNo+" Expected Number : "+ suiteNo);
			System.out.println("Actual SuiteNo is : " +SuiteNo+ " Expected SuiteNo is : " + suiteNo);

			String Casetype = mCaptureSelectedValue("id", mGetPropertyFromFile("lgl_VerifyCaseTypeId"));
			mAssert(Casetype,mGetPropertyFromFile("lgl_CaseTypeData"),mGetPropertyFromFile("lgl_IsDataMatch")+"Actual : "+Casetype+" Expected : "+mGetPropertyFromFile("lgl_CaseTypeData"));
			System.out.println("Actual is : " +Casetype+  " Expected is : " + mGetPropertyFromFile("lgl_CaseTypeData"));

			String Casecategory = mCaptureSelectedValue("id", mGetPropertyFromFile("lgl_VerifyCaseMasCategoryId"));
			mAssert(Casecategory, mGetPropertyFromFile("lgl_CaseMasCategoryData"),mGetPropertyFromFile("lgl_IsDataMatch")+" Actual : "+Casecategory+" Expected : "+ mGetPropertyFromFile("lgl_CaseMasCategoryData"));
			System.out.println("Actual is : " +Casecategory+  " Expected is : " + mGetPropertyFromFile("lgl_CaseMasCategoryData"));


			String Court = mCaptureSelectedValue("id", mGetPropertyFromFile("lgl_VerifyCaseCourtId"));
			mAssert(Court, mGetPropertyFromFile("lgl_CaseCourtData"),mGetPropertyFromFile("lgl_IsDataMatch")+" Actual : "+Court+" Expected : "+ mGetPropertyFromFile("lgl_CaseCourtData"));
			System.out.println("Actual is : " +Court+  " Expected is : " + mGetPropertyFromFile("lgl_CaseCourtData"));

			String Attorneyname = mCaptureSelectedValue("id", mGetPropertyFromFile("lgl_VerifyAdvocateID")).replaceAll("\\s+","");
			String Attorname=mGetPropertyFromFile("lgl_AdvocateData").replaceAll("\\s+","");
			mAssert(Attorneyname, Attorname,mGetPropertyFromFile("lgl_IsDataMatch")+" Actual : "+Attorneyname+" Expected : "+ Attorname);
			System.out.println("Actual is : " +Attorneyname+  " Expected is : " + Attorname);

			if(mGetPropertyFromFile("lgl_CaseStatusData").equalsIgnoreCase("Pending"))
			{
				/*String Casedate = mGetText("css", mGetPropertyFromFile(locator),"value").trim();
				mAssert(Casedate, mGetPropertyFromFile("lgl_CaseMasDateDataStd"),mGetPropertyFromFile("lgl_IsDataMatch")+" Actual : "+Casedate+" Expected :"+ mGetPropertyFromFile("lgl_CaseMasDateDataStd"));
				System.out.println("Actual is : " +Casedate+ " Expected is : " + mGetPropertyFromFile("lgl_CaseMasDateDataStd"));*/
			}

			else {
				/*String Casedate = mGetText("css", locator,"value").trim();
				mAssert(Casedate, mGetPropertyFromFile("lgl_CaseMasDateDataStd"),mGetPropertyFromFile("lgl_IsDataMatch")+" Actual : "+Casedate+" Expected :"+ mGetPropertyFromFile("lgl_CaseMasDateDataStd"));
				System.out.println("Actual is : " +Casedate+ " Expected is : " + mGetPropertyFromFile("lgl_CaseMasDateDataStd"));*/

			//swap	String CaseClosedate = mGetText("id", mGetPropertyFromFile("lgl_VerifyCaseClosedDateId"),"value");
			//swap	mAssert(CaseClosedate, mGetPropertyFromFile("lgl_CaseClosedDateDataStd"),mGetPropertyFromFile("lgl_IsDataMatch")+" Actual : "+CaseClosedate+" Expected :"+ mGetPropertyFromFile("lgl_CaseClosedDateDataStd"));
				//swapSystem.out.println("Actual is : " +CaseClosedate+ " Expected is : " + mGetPropertyFromFile("lgl_CaseClosedDateDataStd"));
			}
		}	

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in verifyCaseHeDateData script");
		}

	}


	
	

	
}

