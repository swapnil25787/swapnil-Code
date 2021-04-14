package com.abm.mainet.rnl;


import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.security.Credentials;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import api.CommonUtilsAPI;
import api.PdfPatterns;
import clubbedapi.ClubbedAPI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.abm.mainet.bnd.CustomListener;
import com.abm.mainet.mkt.MarketCustomErrorMessages;
import com.google.common.base.Throwables;

import common.CommonMethods;
import common.ScrutinyProcess;
import errorhandling.MainetCustomExceptions;
import excelreader.ExcelReader;
import excelreader.ExcelToProperties;
import excelreader.ExcelWriting;

/**
 * @author tejas.kotekar
 * @since  23-03-2016
 * This class contains scripts for test automation of Rent and Lease module of MAINet.
 *
 */

@Listeners(CustomListener.class)
public class RentAndLeaseServices extends CommonMethods{
	public static String strDate;
	public static String RnlServiceName;
	public static boolean Rejectionletter=false;
	String appSubmitMsg;
	String strippedString;
	public static String tenantno;
	ExcelToProperties excelToProp = new ExcelToProperties();
	//	static CommonMethods common =new CommonMethods();
	PdfPatterns pdfpattern= new PdfPatterns();
	static ScrutinyProcess scrutiny = new ScrutinyProcess();
	ExcelReader ER = new ExcelReader();
	public String pageNo;
	public static PdfPatterns pdf123=new PdfPatterns();
	public static ArrayList<String> EstateNo = new ArrayList<String>();
	public static ArrayList<String> TenantNo = new ArrayList<String>();
	public static ArrayList<String> RNLContractNo = new ArrayList<String>();

	public static String rrcapplName;
	public static String boeapplName;
	public static ArrayList<String> estatename = new ArrayList<String>();
	public static String name;

	public  int jj=0;
	//variables used in booking of estate
	String selectedLandEstateName;
	String selectedLandEstateCode;
	String selectedLandEstateAddr;
	String selectedLandEstateType;
	String selectedLandEstateSubType;
	String selectedBookingAmt;

	String addedLandEstateName;
	String addedLandEstateCode;
	String addedLandEstateAddr;
	String addedLandEstateType;
	String addedLandEstateSubType;
	String addedBookingAmt;

	//variables used for dbtesting	

	public static Map<String, ArrayList<String>> datawritten = new LinkedHashMap<String, ArrayList<String>>();

	public static ArrayList<String> datatobewritten = new ArrayList<String>();
	int NoOfColumns;
	ResultSet rs1;
	public static boolean LOiGenration=false;
	public static String db_contno;
	public static String db_etateno;
	public static String db_orgid;
	public static String db_acrid="";
	public static XSSFRow row1 =null;
	public static int noofmonths;
	public static String ScrutinyServiceName;

	String db_landEstateName;
	String db_landEstateCode;
	String db_landEstateAddr;
	String db_landEstateType;
	String db_landEstateSubType;


	// used in bill generation
	public static int num;

	// object for scrutiny method
	ScrutinyProcess scrutinyGenericMethod = new ScrutinyProcess ();


	ClubbedAPI ClubbedUtils = new ClubbedAPI();

	@BeforeSuite
	public void beforeSuite(){
		GetCurrentdate();
		thisClassName=this.getClass().getCanonicalName();
		myClassName = thisClassName;
		mCreateModuleFolder("RNL_",myClassName);
	}


	@Test(enabled=false)
	public void rnl_landAndEstateMasterEntry() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			landEstateMasterEntry();
			RentAndLeaseCustomErrorMessages.rnl_m_errors.clear();
			CommonUtilsAPI.rnlErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_landAndEstateMasterEntry method");
		}
	}

	@Test(enabled=false)
	public void rnl_bookingOfEstate() throws InterruptedException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				applicationNo.clear();	
			}
			bookingOfEstate();
			RentAndLeaseCustomErrorMessages.rnl_m_errors.clear();
			CommonUtilsAPI.rnlErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_bookingOfEstate method");
		}
	}



	@Test(enabled=false)
	public void rnl_chekcListVerification() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			mCreateArtefactsFolder("RNL_");
			
			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				chekindexes.clear();	
			}
			
			newcheckListVerifn=mGetPropertyFromFile("ClvAppApprovedata").split(",");
			for (int i = 0; i < newcheckListVerifn.length; i++) {
				if (newcheckListVerifn[i].equalsIgnoreCase("reject")) {
					chekindexes.add(CurrentinvoCount);
					approvechekindexes.add(222);
					System.out.println("chekindexes==>"+chekindexes);
				}else {

					if (newcheckListVerifn[i].equalsIgnoreCase("approve")) {
						approvechekindexes.add(jj);
						jj++;	
					}
				}
				//
				
				
			}
			System.out.println("approvechekindexes"+approvechekindexes);
			checkListVerification();
			RentAndLeaseCustomErrorMessages.rnl_m_errors.clear();
			CommonUtilsAPI.rnlErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_chekcListVerification method");
		}
	}

	@Test(enabled=false)
	public void rnl_scrutinyProcess() throws InterruptedException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			mCreateArtefactsFolder("RNL_");	
			/*String Scrutiny=mGetPropertyFromFile("wantToRejectAtScrutiny");
			if (Scrutiny.equalsIgnoreCase("yes")) {
				scrutinychekindexes.add(CurrentinvoCount);
				scrutinyapprovechekindexes.add(111);
				System.out.println("chekindexes==>"+scrutinyapprovechekindexes);
			}else {
				scrutinyapprovechekindexes.add(jj);
				jj++;	
				System.out.println("scrutinyapprovechekindexes==>"+scrutinyapprovechekindexes);
			}
*/
			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				scrutinyindexes.clear();	
			}
			
			
			if (!(chekindexes.contains(CurrentinvoCount))) {
				
				
				
				if (mGetPropertyFromFile("rnl_LOIGenFinalDecisiondata").equalsIgnoreCase("no")) {
					scrutinyindexes.add(CurrentinvoCount);
					System.out.println("scrutinyindexes==>"+scrutinyindexes);
				}
				
				
				
				
				
				for(scrutinylevelcounter=1;scrutinylevelcounter<=Integer.parseInt(mGetPropertyFromFile("noOfScrutinyLevels"));scrutinylevelcounter++)
				{
					scrutinyGenericMethod.ScrutinyProcess();
				}
			}
			RentAndLeaseCustomErrorMessages.rnl_m_errors.clear();
			CommonUtilsAPI.rnlErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_scrutinyProcess method");
		}
	}

	@Test(enabled=false)
	public void rnl_printergrid() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			RentAndLeaseCustomErrorMessages.rnl_m_errors.clear();
		 	mCreateArtefactsFolder("RNL_");
			if (!(chekindexes.contains(CurrentinvoCount))) {
				CFCprintergrid(mGetPropertyFromFile("cfc_RNLprnGridUserName"),mGetPropertyFromFile("cfc_RNLprnGridPassword"));
			}
			CommonUtilsAPI.rnlErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_printergrid method");
		}
	}

	@Test(enabled=false)
	public void rnl_Loipayment() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			RentAndLeaseCustomErrorMessages.rnl_m_errors.clear();
			mCreateArtefactsFolder("RNL_");
			System.out.println("CurrentinvoCount+"+CurrentinvoCount);
			if (!(chekindexes.contains(CurrentinvoCount))) {
				if(!(scrutinyindexes.contains(CurrentinvoCount))) {
					
					Loi_ModeofPayment.add(mGetPropertyFromFile("ulbCounterPaymntMode"));
					Loi_BankName.add(mGetPropertyFromFile("payAtULBCounterBank"));
					Loi_ModeofPayment.add(mGetPropertyFromFile("payAtULBCounterAccNo"));
					Loi_BankName.add(mGetPropertyFromFile("payAtULBCounterChqNo"));
					Loi_BankName.add(mGetPropertyFromFile("payAtULBCounterChqDt"));
					loiPayment(mGetPropertyFromFile("rnl_LoiPaymentModuleNameData"),mGetPropertyFromFile("cfc_chalanverFrPaymentFrRentContractBill"));
					System.out.println("no further action can be performed"); 	
					
					
				}
				
			}
			CommonUtilsAPI.rnlErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_Loipayment method");
		}
	}

	@Test(enabled=false)
	public void rnl_ApprovalLetterPrinting() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			RentAndLeaseCustomErrorMessages.rnl_m_errors.clear();
			if (!(chekindexes.contains(CurrentinvoCount))) {
				if(!(scrutinyindexes.contains(CurrentinvoCount))) {
				
				approvalLetterPrinting();
				api.PdfPatterns.RNL_ApprovalLetter(pdfoutput);
				System.out.println("no further action can be performed"); 
				}
			}
			CommonUtilsAPI.rnlErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_ApprovalLetterPrinting method");
		}
	}



	@Test(enabled=false)
	public void rnl_TenantMaster() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			RentAndLeaseCustomErrorMessages.rnl_m_errors.entrySet().clear();
			tenantMaster();
			CommonUtilsAPI.rnlErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_TenantContractGeneration method");
		}
	}



	@Test(enabled=false)
	public void rnl_TenantContractGeneration() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			RentAndLeaseCustomErrorMessages.rnl_m_errors.entrySet().clear();
			tenantContractGeneration();
			CommonUtilsAPI.rnlErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_TenantContractGeneration method");
		}
	}

	@Test(enabled=false)
	public void rnl_BillGeneration() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			RentAndLeaseCustomErrorMessages.rnl_m_errors.entrySet().clear();
			billGeneration();
			CommonUtilsAPI.rnlErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_BillGeneration method");
		}
	}

	@Test(enabled=false)
	public void rnl_BillPrinting() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			RentAndLeaseCustomErrorMessages.rnl_m_errors.entrySet().clear();
			billPrinting();
			CommonUtilsAPI.rnlErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_BillPrinting method");
		}
	}

	@Test(enabled=false)
	public void rnl_RentPaymentSchedule() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			RentAndLeaseCustomErrorMessages.rnl_m_errors.entrySet().clear();
			rentPaymentSchedule();
			CommonUtilsAPI.rnlErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_RentPaymentSchedule method");
		}
	}


/*	@Test(enabled=false)
	public void rnl_RentPaymentScheduleChallanVerftn() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			RentPaymentScheduleChallanVerftn();				
			RentAndLeaseCustomErrorMessages.rnl_m_errors.clear();
			CommonUtilsAPI.rnlErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_RentPaymentSchedule method");
		}
	}*/


	@Test(enabled=false)
	public void rnl_RenewalOfRentContract( ) throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			RentAndLeaseCustomErrorMessages.rnl_m_errors.entrySet().clear();
			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				applicationNo.clear();	
			}
			renewalOfRentContract();
			CommonUtilsAPI.rnlErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_RenewalOfRentContract method");
		}
	}

	@Test(enabled=false)
	public void rnl_RentRenewalContractChkLstVerifctn() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			RentAndLeaseCustomErrorMessages.rnl_m_errors.entrySet().clear();
			mCreateArtefactsFolder("RNL_");
			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				chekindexes.clear();	
			}
			
			
			
			newcheckListVerifn=mGetPropertyFromFile("ClvAppApprovedata").split(",");
			for (int i = 0; i < newcheckListVerifn.length; i++) {
				if (newcheckListVerifn[i].equalsIgnoreCase("reject")) {
					chekindexes.add(CurrentinvoCount);
					approvechekindexes.add(222);
					System.out.println("chekindexes==>"+chekindexes);
				}else {

					if (newcheckListVerifn[i].equalsIgnoreCase("approve")) {
						approvechekindexes.add(jj);
						jj++;	
					}
				}
				//
				
				
			}
			System.out.println("approvechekindexes"+approvechekindexes);
			
			
			checkListVerification();
			CommonUtilsAPI.rnlErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_RentRenewalContractChkLstVerifctn method");
		}
	}

	@Test(enabled=false)
	public void rnl_RentrenwlContrctScrutinyProcess() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			RentAndLeaseCustomErrorMessages.rnl_m_errors.entrySet().clear();
		/*	if(!mGetPropertyFromFile("wantToRejectAtChecklist").equalsIgnoreCase("No")){
				System.out.println("rnl_scrutinyProcess method skipped as rejected");
			}
			else
			{*/
			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				scrutinyindexes.clear();	
			}
			
			
			if (!(chekindexes.contains(CurrentinvoCount))) {
				
				if (mGetPropertyFromFile("rnl_LOIGenFinalDecisiondata").equalsIgnoreCase("no")) {
					scrutinyindexes.add(CurrentinvoCount);
					System.out.println("scrutinyindexes==>"+scrutinyindexes);
				}
				
				
				
				for(scrutinylevelcounter=1;scrutinylevelcounter<=Integer.parseInt(mGetPropertyFromFile("noOfScrutinyLevels"));scrutinylevelcounter++)
				{
					scrutinyGenericMethod.ScrutinyProcess();
				}
			}

			CommonUtilsAPI.rnlErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_RentrenwlContrctScrutinyProcess method");
		}
	}

	@Test(enabled=false)
	public void rnl_RentrenwlContrctPrinterCounter() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			RentAndLeaseCustomErrorMessages.rnl_m_errors.entrySet().clear();
			/*if(!mGetPropertyFromFile("wantToRejectAtChecklist").equalsIgnoreCase("No")){
				System.out.println("rnl_scrutinyProcess method skipped as rejected");
			}
			else
			{	*/	
			if (!(chekindexes.contains(CurrentinvoCount))) {
				CFCprintergrid(mGetPropertyFromFile("cfc_RNLprnGridUserName"),mGetPropertyFromFile("cfc_RNLprnGridPassword"));
				System.out.println("no further action can be performed");
			}

			CommonUtilsAPI.rnlErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_RentrenwlContrctPrinterCounter method");
		}
	}

	@Test(enabled=false)
	public void rnl_RentrenwlContrctLOIPayment() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			/*RentAndLeaseCustomErrorMessages.rnl_m_errors.entrySet().clear();
			if(!mGetPropertyFromFile("wantToRejectAtScrutiny").equalsIgnoreCase("No")||!mGetPropertyFromFile("wantToRejectAtChecklist").equalsIgnoreCase("No")){	
				System.out.println("no further action can be performed");
			}
			else
			{*/
			
			
			if (!(chekindexes.contains(CurrentinvoCount))) {
				if(!(scrutinyindexes.contains(CurrentinvoCount))) {
				rentRenewalLOIPayment();
			}
			}
			CommonUtilsAPI.rnlErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_RentrenwlContrctLOIPayment method");
		}
	}

	@Test(enabled=false)
	public void rnl_RentrenewalContractApprovalLetterPrinting() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			RentAndLeaseCustomErrorMessages.rnl_m_errors.clear();
		/*	if(!mGetPropertyFromFile("wantToRejectAtScrutiny").equalsIgnoreCase("No")||!mGetPropertyFromFile("wantToRejectAtChecklist").equalsIgnoreCase("No")){					
				System.out.println("no further action can be performed"); 
			}
			else
			{*/
			
			if (!(chekindexes.contains(CurrentinvoCount))) {
				if(!(scrutinyindexes.contains(CurrentinvoCount))) {

				approvalLetterPrinting();
			}
			}
			CommonUtilsAPI.rnlErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_RentrenewalContractApprovalLetterPrinting method");
		}
	}


	//Land Estate Master Entry
	public void landEstateMasterEntry() throws IOException{

		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		mCreateArtefactsFolder("RNL_");
		//		beforeLandEstateMasterEntry();
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		departmentLogin(mGetPropertyFromFile("rnl_MBADeptName"),mGetPropertyFromFile("rnl_MBADeptPassword"));
		//		login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("rnl_MBADeptName"),mGetPropertyFromFile("rnl_MBADeptPassword"));
		landAndEstateMasterEntry_Script();
		landEstateProceed_Script();
		logOut();
		finalLogOut();
		mCloseBrowser();

	}

	//Rent and Lease Scrutiny process
	public void rentAndLeaseScrutinyProcess() throws InterruptedException, IOException
	{
		mCreateArtefactsFolder("RNL_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));	
		selectUlb();
		departmentLogin(mGetPropertyFromFile("cfc_RNLscruProcUserName"),mGetPropertyFromFile("cfc_RNLscruProcPassword"));
		scrutiny.scrutinyProcess();
		mTakeScreenShot();
		logOut();
		finalLogOut();
		mCloseBrowser();
	}

	//Printer counter
	public void printerCounter() throws InterruptedException, IOException
	{
		//		beforeRNLPrinterCounter();
		mCreateArtefactsFolder("RNL_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		departmentLogin(mGetPropertyFromFile("cfc_RNLprnGridUserName"),mGetPropertyFromFile("cfc_RNLprnGridPassword"));
		cfcPrinterGrid();
		logOut();
		finalLogOut();
		mCloseBrowser();
	}

	//Tenant Contract Generation
	public void tenantContractGeneration() throws InterruptedException, IOException
	{
		//		beforetenantContractGeneration();
		mCreateArtefactsFolder("RNL_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		//		departmentLogin(mGetPropertyFromFile("rnl_MBADeptName"),mGetPropertyFromFile("rnl_MBADeptPassword"));
		login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("rnl_MBADeptName"),mGetPropertyFromFile("rnl_MBADeptPassword"));
		tenantContractGen();
		logOut();
		finalLogOut();
		mCloseBrowser();
	}

	//Bill Generation
	public void billGeneration() throws InterruptedException, IOException
	{
		//		beforebillGeneration();
		mCreateArtefactsFolder("RNL_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		//		departmentLogin(mGetPropertyFromFile("rnl_MBADeptName"),mGetPropertyFromFile("rnl_MBADeptPassword"));
		login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("rnl_MBADeptName"),mGetPropertyFromFile("rnl_MBADeptPassword"));
		billGeneration_Script();
		logOut();
		finalLogOut();
		mCloseBrowser();
	}

	//Bill Printing
	public void billPrinting() throws InterruptedException, IOException
	{
		//		beforebillPrinting();
		mCreateArtefactsFolder("RNL_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		//		departmentLogin(mGetPropertyFromFile("rnl_MBADeptName"),mGetPropertyFromFile("rnl_MBADeptPassword"));
		login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("rnl_MBADeptName"),mGetPropertyFromFile("rnl_MBADeptPassword"));
		billPrinting_Script();
		logOut();
		finalLogOut();
		mCloseBrowser();
	}

	//Rent Payment Schedule
	public void rentPaymentSchedule() throws InterruptedException
	{
		//		beforerentPaymetSchedule();
		mCustomWait(1000);
		mCreateArtefactsFolder("RNL_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("rnl_MBADeptName"),mGetPropertyFromFile("rnl_MBADeptPassword"));
		rentPaymentSchedule_Script();
		api.PdfPatterns.rnlpatternChallanPdf(pdfoutput);
		logOut();
		finalLogOut();
		isrentPaymentChallanVerftnRequired(mGetPropertyFromFile("cfc_chalanverFrPaymentFrRentContractBill"));
		mCloseBrowser();
	}


/*	public void RentPaymentScheduleChallanVerftn() throws InterruptedException
	{
		mCreateArtefactsFolder("RNL_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		isChallanVerftnRequired(mGetPropertyFromFile("cfc_chalanverFrPaymentFrRentContractBill"));
		mCloseBrowser();
	}
*/


	//Renewal of Rent Contract
	public void renewalOfRentContract() throws InterruptedException
	{
		mCreateArtefactsFolder("RNL_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		departmentLogin(mGetPropertyFromFile("rnl_MBADeptName"),mGetPropertyFromFile("rnl_MBADeptPassword"));
		renewalOfRentContract_Script();
		logOut();
		finalLogOut();
		isChallanVerftnRequired(mGetPropertyFromFile("cfc_chalanverFrRenewalOfRentContract"));
		mCloseBrowser();
	}

	//Checklist Verification for Rent renewal Contract
	public void checkListVerification() throws InterruptedException
	{
		mCustomWait(2000);
		ChecklistVerification(mGetPropertyFromFile("rnl_MBADeptName"),mGetPropertyFromFile("rnl_MBADeptPassword"));	
	}

	//Method to print application after renewal of rent contract
	public void rentRenewalPrinter() throws InterruptedException
	{
		//		beforeRNLPrinterCounter();
		mCreateArtefactsFolder("RNL_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		departmentLogin(mGetPropertyFromFile("cfc_RNLprnGridUserName"),mGetPropertyFromFile("cfc_RNLprnGridPassword"));
		cfcPrinterGrid();
		mCustomWait(1000);
		pdfpattern.rentRenewalContract(pdfoutput);
		mCustomWait(1000);
		logOut();
		finalLogOut();
		mCloseBrowser();
	}

	//LOI Payment for Rent renewal contract
	public void rentRenewalLOIPayment() throws InterruptedException, IOException
	{
		mCustomWait(2000);
		mCreateArtefactsFolder("RNL_");
		//		beforeRnl_loipayment();    
		//		LOIAPPLICABLE = true;
		BOE_LOI_Collection = true;
		Loi_ModeofPayment.add(mGetPropertyFromFile("ulbCounterPaymntMode"));
		Loi_BankName.add(mGetPropertyFromFile("payAtULBCounterBank"));
		Loi_ModeofPayment.add(mGetPropertyFromFile("payAtULBCounterAccNo"));
		Loi_BankName.add(mGetPropertyFromFile("payAtULBCounterChqNo"));
		Loi_BankName.add(mGetPropertyFromFile("payAtULBCounterChqDt"));
		loiPayment(mGetPropertyFromFile("rnl_LoiPaymentModuleNameData"), mGetPropertyFromFile("cfc_chalanverFrRenewalOfRentContract"));
	}

	//Tenant Contract Generation
	public void tenantContractGen()
	{
		try
		{
			//Navigating from Rent & Lease to Transactions to Tenant Contract
			mCustomWait(1000);;
			mNavigation("rnl_TgRentNLeaseLinkid", "rnl_TgTransactionLinkid", "rnl_TgTenantContractLinkid");


			ULBName=mGetPropertyFromFile("municipality");
			System.out.println("ulb is : "+ULBName); 

			// add Tenant Contract
			if(mGetPropertyFromFile("rnl_TgAddTenantContract").equalsIgnoreCase("Yes") && mGetPropertyFromFile("rnl_TgEditTenantContract").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_TgViewTenantContract").equalsIgnoreCase("No"))
			{	
				//Add Tenant Contract Button
				mWaitForVisible("css", mGetPropertyFromFile("rnl_TgAddTenantContrctBtnid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_TgAddTenantContrctBtnid"));

				//Search Tenant
				mWaitForVisible("xpath", mGetPropertyFromFile("rnl_TgfinalSearchTenantBtnid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("rnl_TgfinalSearchTenantBtnid"));

				//Sending Tenant Number
				mWaitForVisible("id", mGetPropertyFromFile("rnl_TgAgencyTenantNoid"));
				mCustomWait(1000);

				if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent")) 
				{
					mSendKeys("id", mGetPropertyFromFile("rnl_TgAgencyTenantNoid"),TenantNo.get(CurrentinvoCount));
				}

				else
				{
					mSendKeys("id", mGetPropertyFromFile("rnl_TgAgencyTenantNoid"), mGetPropertyFromFile("rnl_TgAgencyTenantNoData"));
				}

				mTakeScreenShot();
				mCustomWait(1000);

				//Search Tenant Button
				mWaitForVisible("css", mGetPropertyFromFile("rnl_TgSearchTenantBtnid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_TgSearchTenantBtnid"));

				//Selecting a Tenant
				mWaitForVisible("name", mGetPropertyFromFile("rnl_TgSearchTenantRadioid"));
				mCustomWait(1000);
				mClick("name", mGetPropertyFromFile("rnl_TgSearchTenantRadioid"));
				mTakeScreenShot();
				mCustomWait(1000);

				//Tenant Selection Submit Button
				mWaitForVisible("css", mGetPropertyFromFile("rnl_TgSearchTenantSubBtnid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_TgSearchTenantSubBtnid"));

				mCustomWait(1000);
				////////////////////////////////////till search and submit/////////////////////////////////////
				//TCtenantName=mGetText("id", mGetPropertyFromFile("rnl_TgTenantNameid"), "value");
				TCtenantName.add(mGetText("id", mGetPropertyFromFile("rnl_TgTenantNameid"), "value"));
				System.out.println("tenant name is : "+TCtenantName);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					mAssert(TCtenantName, TM_TenantName, "Tenant Name does not match in Contract Details with actual"+TCtenantName + "Expected:::"+TM_TenantName);
				}
				log.info("tenant name is : "+TCtenantName);

				//TCtenantNo=mGetText("id", mGetPropertyFromFile("rnl_TgTenantNoid"), "value");
				TCtenantNo.add(mGetText("id", mGetPropertyFromFile("rnl_TgTenantNoid"), "value"));
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					mAssert(TCtenantNo, TM_TenantNo, "Tenant No does not match in Contract Details with actual"+TCtenantNo + "Expected:::"+TM_TenantNo);
				}
				System.out.println("tenant no is : "+TCtenantNo);
				log.info("tenant no is : "+TCtenantNo);

				//Tender Reference
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TgTenderRefid"), mGetPropertyFromFile("rnl_TgTenderRefData"));
				String TenderRef= mGetText("id", mGetPropertyFromFile("rnl_TgTenderRefid"), "value");
				System.out.println("Tender Reference no is"+TenderRef);
				mAssert(TenderRef, mGetPropertyFromFile("rnl_TgTenderRefData"), "Tender Reference No dose not match with the actual ::"+TenderRef+"Expected ::"+mGetPropertyFromFile("rnl_TgTenderRefData"));
				TC_TenderRef.add(TenderRef);

				//Tender Date
				mDateSelect("id",mGetPropertyFromFile("rnl_TgTenderDateid"), mGetPropertyFromFile("rnl_TgTenderDateData"));
				String TenderDate=mGetText("id", mGetPropertyFromFile("rnl_TgTenderDateid"), "value");
				System.out.println("Tender Date is"+TenderDate);
				//mAssert(TenderDate, mGetPropertyFromFile("rnl_TgTenderDateData"), "Tender Date dose not match with the actual ::"+TenderDate+"Expected ::"+mGetPropertyFromFile("rnl_TgTenderDateData"));
				TC_TenderDate.add(TenderDate);
				//TC_TenderDate.add(mGetPropertyFromFile("rnl_TgTenderDateData"));

				//Contract Date
				mDateSelect("id",mGetPropertyFromFile("rnl_TgContractDateid"), mGetPropertyFromFile("rnl_TgContractDateData"));
				String ContractDate=mGetText("id", mGetPropertyFromFile("rnl_TgContractDateid"), "value");
				System.out.println("Contract Date is"+ContractDate);
				//mAssert(ContractDate, mGetPropertyFromFile("rnl_TgContractDateData"), "Contract Date dose not match with the actual ::"+TenderRef+"Expected ::"+mGetPropertyFromFile("rnl_TgContractDateData"));
				TC_ContractDate.add(ContractDate);
				//TC_ContractDate.add(mGetPropertyFromFile("rnl_TgContractDateData"));

				//Contract From Date
				mDateSelect("id",mGetPropertyFromFile("rnl_TgContractFromDateid"), mGetPropertyFromFile("rnl_TgContractFromDateData"));
				String ContractFrDate=mGetText("id", mGetPropertyFromFile("rnl_TgContractFromDateid"), "value");
				System.out.println("Contract From Date is"+ContractFrDate);
				mAssert(ContractFrDate, mGetPropertyFromFile("rnl_TgContractFromDateData"), "Contract From Date dose not match with the actual ::"+ContractFrDate+"Expected ::"+mGetPropertyFromFile("rnl_TgContractFromDateData"));
				TC_ContractFromDate.add(ContractFrDate);
				//TC_ContractFromDate.add(mGetPropertyFromFile("rnl_TgContractFromDateData"));

				//Contract To Date
				mDateSelect("id",mGetPropertyFromFile("rnl_TgContractToDateid"), mGetPropertyFromFile("rnl_TgContractToDateData"));
				String ContractToDate=mGetText("id", mGetPropertyFromFile("rnl_TgContractToDateid"), "value");
				System.out.println("Contract To Date is"+ContractToDate);
				mAssert(ContractToDate, mGetPropertyFromFile("rnl_TgContractToDateData"), "Contract to Date dose not match with the actual ::"+TenderRef+"Expected ::"+mGetPropertyFromFile("rnl_TgContractToDateData"));
				TC_ContractToDate.add(ContractToDate);
				//TC_ContractToDate.add(mGetPropertyFromFile("rnl_TgContractToDateData"));
				mCustomWait(1000);

				//Sending Contract Value
				if(mGetPropertyFromFile("rnl_TgTaxDescrptnYesN0Data").equalsIgnoreCase("yes")){

					mCustomWait(1000);
					mSendKeys("id", mGetPropertyFromFile("rnl_TgContractAmountid"), mGetPropertyFromFile("rnl_TgContractAmountData"));
					String ContractAmt=mGetText("id", mGetPropertyFromFile("rnl_TgContractAmountid"), "value");
					System.out.println("Contract Amount is"+ContractAmt);
					mAssert(ContractAmt, mGetPropertyFromFile("rnl_TgContractAmountData"), "Contract Amount dose not match with the actual ::"+TenderRef+"Expected ::"+mGetPropertyFromFile("rnl_TgContractAmountData"));
					TC_ContractAmount.add(ContractAmt);
					//TC_ContractAmount.add(mGetPropertyFromFile("rnl_TgContractAmountData"));
				}


				//Sending Contract Discount Value
				if(mGetPropertyFromFile("rnl_TgTaxDiscountYesNoData").equalsIgnoreCase("yes")){
					mCustomWait(1000);
					mSendKeys("id", mGetPropertyFromFile("rnl_TgContractDiscountValueid"), mGetPropertyFromFile("rnl_TgTotalDiscAmount"));
					String ContractDiscountAmt=mGetText("id", mGetPropertyFromFile("rnl_TgContractDiscountValueid"), "value");
					System.out.println("Contract Discount Amount is"+ContractDiscountAmt);
					mAssert(ContractDiscountAmt, mGetPropertyFromFile("rnl_TgContractDiscountValueData"), "Contract Discount Amount dose not match with the actual ::"+TenderRef+"Expected ::"+mGetPropertyFromFile("rnl_TgTotalDiscAmount"));
					TC_DiscountAmount.add(ContractDiscountAmt);
				}
				mCustomWait(1000);

				//TCcontractFromDate=mGetText("id", mGetPropertyFromFile("rnl_TgContractFromDateid"),"value");
				//TCcontractFromDate.add(mGetText("id", mGetPropertyFromFile("rnl_TgContractFromDateid"),"value"));
				System.out.println("contract From Date is : "+TCcontractFromDate);
				//log.info("contract From Date is : "+TCcontractFromDate);

				//				TCcontractToDate=mGetText("id", mGetPropertyFromFile("rnl_TgContractToDateid"),"value");
				//TCcontractToDate.add(mGetText("id", mGetPropertyFromFile("rnl_TgContractToDateid"),"value"));
				System.out.println("contract to Date is : "+TCcontractToDate);
				//log.info("contract to Date is : "+TCcontractToDate);

				//				TCcontractAmount=mGetText("id", mGetPropertyFromFile("rnl_TgContractAmountid"),"value");
				//TCcontractAmount.add(mGetText("id", mGetPropertyFromFile("rnl_TgContractAmountid"),"value"));
				System.out.println("contractAmount is : "+TCcontractAmount);
				///log.info("contractAmount is : "+TCcontractAmount);

				//				TCdiscountAmount=mGetText("id", mGetPropertyFromFile("rnl_TgContractDiscountValueid"),"value");
				//TCdiscountAmount.add(mGetText("id", mGetPropertyFromFile("rnl_TgContractDiscountValueid"),"value"));
				System.out.println("TCdiscountAmount is : "+TCdiscountAmount);
				//log.info("TCdiscountAmount is : "+TCdiscountAmount);


				// Round Off Amount
				String ROA=mGetText("id",mGetPropertyFromFile("rnl_TgContractRoundoffAmountid"),"value");
				TC_RoundOffAmount.add(ROA);
				System.out.println("round off amount is : "+ ROA);


				// Payable Amount
				String Payable=mGetText("id",mGetPropertyFromFile("rnl_TgContractPayableAmountid"),"value");
				TC_PayableAmount.add(Payable);
				System.out.println("payable amount is : "+ Payable);

				////swap

				System.out.println("updatedpayble_list==>"+updatedpayble_list);
				/*//				TCtotalPayableAmount=mGetText("id",mGetPropertyFromFile("rnl_TgContractPayableAmountid"),"value");
				TCtotalPayableAmount.add(mGetText("id",mGetPropertyFromFile("rnl_TgContractPayableAmountid"),"value"));
				System.out.println("TCtotalPayableAmount is : "+TCtotalPayableAmount);
				log.info("TCtotalPayableAmount is : "+TCtotalPayableAmount);*/

				//Contract Remarks
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TgContractRemarksid"), mGetPropertyFromFile("rnl_TgContractRemarksData"));
				String ContractRemarks=mGetText("id", mGetPropertyFromFile("rnl_TgContractRemarksid"), "value");
				TC_ContractRemarks.add(ContractRemarks);
				mCustomWait(1000);
				String Payable1=mGetText("id",mGetPropertyFromFile("rnl_payment_id"),"value");


				int updatedpayble = Integer.parseInt(Payable1);
				System.out.println("updatedpayble=>"+updatedpayble);
				updatedpayble_list=updatedpayble;
				mTakeScreenShot();
				mCustomWait(1000);
				mscroll(0,500);			

				//Amount Bifurcation
				mCustomWait(1000);
				mClick("xpath",  mGetPropertyFromFile("rnl_TgAmountBifurcationBtnid"));

				mWaitForVisible("id", mGetPropertyFromFile("rnl_TgTaxDescrptnAddBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mClick("id",  mGetPropertyFromFile("rnl_TgTaxDescrptnAddBtnid"));

				if(mGetPropertyFromFile("rnl_TgTaxDescrptnYesN0Data").equalsIgnoreCase("yes")){
					//Discount Description
					mWaitForVisible("id", mGetPropertyFromFile("rnl_TgTaxDescrptnid1"));
					mCustomWait(1000);
					mSelectDropDown("id",  mGetPropertyFromFile("rnl_TgTaxDescrptnid1"), mGetPropertyFromFile("rnl_TgTaxDescrptnData1"));
					String TaxDescription=mCaptureSelectedValue("id",  mGetPropertyFromFile("rnl_TgTaxDescrptnid1"));
					//TC_DiscountDescription2.add(TaxDescription);

					//Sending Discount amount
					mCustomWait(1000);
					mSendKeys("id",  mGetPropertyFromFile("rnl_TgTaxAmountid1"), mGetPropertyFromFile("rnl_TgTaxAmountData1"));
					String TaxDescAmt=mGetText("id",  mGetPropertyFromFile("rnl_TgTaxAmountid1"), "value");
					System.out.println("Tax Amount is"+TaxDescAmt);
					//TC_TaxAmount2.add(TaxDescAmt);
					mTakeScreenShot();
					mTab("id", mGetPropertyFromFile("rnl_TgTaxAmountid1")); 

				}

				if(mGetPropertyFromFile("rnl_TgTaxDiscountYesNoData").equalsIgnoreCase("yes")){

					mWaitForVisible("id", mGetPropertyFromFile("rnl_TgTaxDescrptnAddBtnid"));
					mCustomWait(1000);
					mTakeScreenShot();
					mClick("id",  mGetPropertyFromFile("rnl_TgTaxDescrptnAddBtnid"));

					//Discount Description
					mWaitForVisible("id", mGetPropertyFromFile("rnl_TgTaxDescrptnid1"));
					mCustomWait(1000);
					mSelectDropDown("id",  mGetPropertyFromFile("rnl_TgTaxDescrptnid1"), mGetPropertyFromFile("rnl_TgTaxDescrptnData2"));
					String TaxDescription=mCaptureSelectedValue("id",  mGetPropertyFromFile("rnl_TgTaxDescrptnid1"));
					TC_DiscountDescription.add(TaxDescription);

					//Sending Discount amount
					mCustomWait(1000);
					mSendKeys("id",  mGetPropertyFromFile("rnl_TgTaxAmountid1"), mGetPropertyFromFile("rnl_TgTotalDiscAmount"));
					String TaxDescAmt=mGetText("id",  mGetPropertyFromFile("rnl_TgTaxAmountid1"), "value");
					System.out.println("Tax Amount is"+TaxDescAmt);
					TC_TaxAmount.add(TaxDescAmt);
					mTakeScreenShot();
					mTab("id", mGetPropertyFromFile("rnl_TgTaxAmountid1")); 


				}

				// Amount Bifurcation total Amount
				String abta=mGetText("id",mGetPropertyFromFile("rnl_TgAmountBifurcationTotalAmountid"),"value");
				System.out.println("total amount is : "+ abta);	
				System.out.println("totalamount is " +mGetPropertyFromFile("rnl_TgTotalAmountData")); 
				mAssert(abta, mGetPropertyFromFile("rnl_TgTotalAmountData"), "Actual  :"+abta+" Expected  :"+mGetPropertyFromFile("rnl_TgTotalAmountData"));
				mTakeScreenShot();

				//Amount Bifurcation Submit
				mWaitForVisible("css", mGetPropertyFromFile("rnl_TgAmountBifurcationSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_TgAmountBifurcationSubBtnid"));
				//////////////////////////////////////////////////swapnil payment schedule/////////////////////////////
				//Select Payment Schedule Button
				mWaitForVisible("xpath", mGetPropertyFromFile("rnl_TgSelctPaymentScheduleBtnid"));
				mCustomWait(1000);
				mClick("xpath",  mGetPropertyFromFile("rnl_TgSelctPaymentScheduleBtnid"));

				//Payment Schedule
				mWaitForVisible("id", mGetPropertyFromFile("rnl_TgPaymentScheduleDropDwnid"));
				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_TgPaymentScheduleDropDwnid"), mGetPropertyFromFile("rnl_TgPaymentScheduleDropDwnData"));


				String	schedule=mGetPropertyFromFile("rnl_TgPaymentScheduleDropDwnData");
				if (schedule.equalsIgnoreCase("Monthly")||schedule.equalsIgnoreCase("Bi Monthly")||schedule.equalsIgnoreCase("Quarterly")||schedule.equalsIgnoreCase("Half Yearly")||schedule.equalsIgnoreCase("Yearly")) {
					System.out.println("<====i m in payment shedule for"+schedule+"Pattern====>" );
					if (mGetPropertyFromFile("Mod_paymentschedule").equalsIgnoreCase("yes")) {
						PaymentSchedule();
					}
					//updatedpayble
					String PaymeSchedule=mCaptureSelectedValue("id", mGetPropertyFromFile("rnl_TgPaymentScheduleDropDwnid"));		
					System.out.println("The Payment Scedule method is::"+PaymeSchedule);
					TC_PaymentSceduleMethod.add(PaymeSchedule);

					// Payment Schedule Due Date
					String PaymentDuedate=mGetText("id",mGetPropertyFromFile("rnl_TgPaymentScheduleTotalAmountid"),"value");
					System.out.println("Payment Due Date is : "+ PaymentDuedate);			
					TC_PaymentDueDate.add(PaymentDuedate);

					//Sending Amount
					mWaitForVisible("id", mGetPropertyFromFile("rnl_TgPaymentScheduleAmountid"));
					mCustomWait(1000);
					//mSendKeys("id", mGetPropertyFromFile("rnl_TgPaymentScheduleAmountid"), mGetPropertyFromFile("rnl_TgPaymentScheduleAmountData"));
					String PaymentSAmt=mGetText("id", mGetPropertyFromFile("rnl_TgPaymentScheduleAmountid"), "value");
					TC_PayAmount.add(PaymentSAmt);

					// Payment Schedule total Amount reading
					String psta=mGetText("id",mGetPropertyFromFile("rnl_TgPaymentScheduleTotalAmountid"),"value");
					System.out.println("total amount is : "+ psta);			
					TC_PaymentScheduleAmount.add(psta);
					mAssert(psta, mGetPropertyFromFile("rnl_TgTotalAmountData"), "assert fails Actual  :"+psta+" Expected  :"+mGetPropertyFromFile("rnl_TgTotalAmountData"));
					mTakeScreenShot();
				}else {
					System.out.println("<====i m in payment shedule for"+"Other mode"+"Pattern====>" );
					PaymentSchedule_Other();
				}
				//Submit 
				mWaitForVisible("name", mGetPropertyFromFile("rnl_TgPaymentScheduleSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("name", mGetPropertyFromFile("rnl_TgPaymentScheduleSubBtnid"));


				//////////////////////////////////////////end of swappppp code ////////////////


				//Select Contract Terms
				mWaitForVisible("xpath", mGetPropertyFromFile("rnl_TgSelectContrctTermSubBtnid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("rnl_TgSelectContrctTermSubBtnid"));


				mCustomWait(2000);
				/*int count=Integer.parseInt(mGetPropertyFromFile("adh_AcgContTermsSelectTermCount"));	
						mWaitForVisible("name", mGetPropertyFromFile("adh_AcgContractTermSubBtnid"));*/

				String no_of_cheks=mGetPropertyFromFile("rnl_TgContTermsSelectTermCount");
				System.out.println("no_of_cheks==>"+no_of_cheks);

				String [] j=no_of_cheks.split(",");
				for (int i = 0; i < j.length; i++)
				{
					int	k=Integer.parseInt(j[i])-1;

					System.out.println("k==>"+k);
					String chkbox="ctChkBox"+k;
					System.out.println("chkbox==>"+chkbox);
					driver.findElement(By.id(chkbox)).click();

				}

				mCustomWait(2000);


				mTakeScreenShot();

				//Contract Terms Submit
				mWaitForVisible("name", mGetPropertyFromFile("rnl_TgContractTermSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mClick("name", mGetPropertyFromFile("rnl_TgContractTermSubBtnid"));

				//Select Estate Button
				mWaitForVisible("css", mGetPropertyFromFile("rnl_TgSelectEstateBtnid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_TgSelectEstateBtnid"));

				//sending Estate Code
				mWaitForVisible("id", mGetPropertyFromFile("rnl_TgEstateCodeid"));
				mCustomWait(1000);

				mSendKeys("id", mGetPropertyFromFile("rnl_TgEstateCodeid"),mGetPropertyFromFile("rnl_TgEstateNoData"));
				String EstateCode=mGetText("id", mGetPropertyFromFile("rnl_TgEstateCodeid"), "value");
				System.out.println("Estate Code is"+EstateCode);
				TC_EstateCode.add(EstateCode);

				//Estate Code Search Button
				mWaitForVisible("xpath", mGetPropertyFromFile("rnl_TgSearchEstateBtnid"));
				mCustomWait(1000);
				mClick("xpath",  mGetPropertyFromFile("rnl_TgSearchEstateBtnid"));

				//Selecting Estate with check box
				mWaitForVisible("name", mGetPropertyFromFile("rnl_TgSelectEstateCheckBoxid"));
				mCustomWait(1000);
				mClick("name", mGetPropertyFromFile("rnl_TgSelectEstateCheckBoxid"));

				//Select Estate Submit Button
				mWaitForVisible("name", mGetPropertyFromFile("rnl_TgSelectEstateSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mClick("name", mGetPropertyFromFile("rnl_TgSelectEstateSubBtnid"));
				mCustomWait(1000);
				//Pop Up Msg Assert
				mCustomWait(1000);
				mWaitForVisible("css", mGetPropertyFromFile("rnl_TgEstateFancyBoxCloseid"));
				//mClick("css", mGetPropertyFromFile("rnl_TgEstateFancyBoxCloseid"));
				mCustomWait(1000);
				String MsgAftrEstateSub = mGetText("css", mGetPropertyFromFile("rnl_TgEstatePopUpAssertMsgid"));
				mCustomWait(1000);
				mAssert(MsgAftrEstateSub, mGetPropertyFromFile("rnl_TgEstatePopUpAssertMsgData"), "Actual msg : "+MsgAftrEstateSub+" Expected msg :"+mGetPropertyFromFile("rnl_TgEstatePopUpAssertMsgData")+"at Estate selection.");
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				System.out.println(MsgAftrEstateSub);
				//mWaitForVisible("css", mGetPropertyFromFile("rnl_TgEstateFancyBoxCloseid"));
				mClick("css", mGetPropertyFromFile("rnl_TgEstateFancyBoxCloseid"));
				mCustomWait(1000);
				//Sending Deposit Number
				mWaitForVisible("css", mGetPropertyFromFile("rnl_TgTenantContractFinalSubBtnid"));					

				mUpload("id", mGetPropertyFromFile("rnl_TgUploadDocumentid"), mGetPropertyFromFile("rnl_TgUploadDocumentData"));
				mCustomWait(2000);
				if(mGetPropertyFromFile("rnl_TgPaySecurityAmount").equalsIgnoreCase("yes"))
				{	

					mCustomWait(1000);
					mSendKeys("id", mGetPropertyFromFile("rnl_TgDepositNumid"), mGetPropertyFromFile("rnl_TgDepositNumData"));
					String rnl_TgDepositNum=mGetText("id", mGetPropertyFromFile("rnl_TgDepositNumid"), "value");
					System.out.println("Deposit No. :"+rnl_TgDepositNum);
					mAssert(rnl_TgDepositNum, mGetPropertyFromFile("rnl_TgDepositNumData"), "Deposit Num Dose not match");
					DepositNum.add(rnl_TgDepositNum);

					//Sending Deposit Date
					mDateSelect("id",mGetPropertyFromFile("rnl_TgDepositDateid"), mGetPropertyFromFile("rnl_TgDepositDateData"));
					String rnl_TgDepositDate=mGetText("id",mGetPropertyFromFile("rnl_TgDepositDateid"), "value");
					mAssert(rnl_TgDepositDate, mGetPropertyFromFile("rnl_TgDepositDateData"), "Deposit Date Dose not match");
					DepositDate.add(rnl_TgDepositDate);


					//Selecting Deposit Type
					mSelectDropDown("id",mGetPropertyFromFile("rnl_TgSecurityDepositTypeid"), mGetPropertyFromFile("rnl_TgSecurityDepositTypeData"));
					String rnl_TgSecurityDepositType=mCaptureSelectedValue("id", mGetPropertyFromFile("rnl_TgSecurityDepositTypeid"));
					SecurityDepositType.add(rnl_TgSecurityDepositType);
					mAssert(rnl_TgSecurityDepositType, mGetPropertyFromFile("rnl_TgSecurityDepositTypeData"), "Security Deposit Type Dose not match");

					if(mGetPropertyFromFile("rnl_TgSecurityDepositTypeData").equalsIgnoreCase("cash"))
					{

						//Cheque/DDDate/cashdate
						mDateSelect("id",mGetPropertyFromFile("rnl_TgChequeDDDateid"), mGetPropertyFromFile("rnl_TgChequeDDDateData"));
						String rnl_TgChequeDDDate=mGetText("id",mGetPropertyFromFile("rnl_TgChequeDDDateid"), "value");
						System.out.println("Cash/Cheque/DD Date is:"+rnl_TgChequeDDDate);
						ChequeDDDate.add(rnl_TgChequeDDDate);

						//Security Deposit Amount
						mWaitForVisible("id", mGetPropertyFromFile("rnl_TgDepositAmountid"));
						mCustomWait(1000);
						mSendKeys("id", mGetPropertyFromFile("rnl_TgDepositAmountid"), mGetPropertyFromFile("rnl_TgDepositAmountData"));
						String rnl_TgDepositAmount=mGetText("id", mGetPropertyFromFile("rnl_TgDepositAmountid"), "value");
						DepositAmount.add(rnl_TgDepositAmount);
					}	

					else	
					{
						//Drawee Bank Name
						mWaitForVisible("id", mGetPropertyFromFile("rnl_TgDraweeBankNameid"));
						mCustomWait(1000);
						mSendKeys("id", mGetPropertyFromFile("rnl_TgDraweeBankNameid"), mGetPropertyFromFile("rnl_TgDraweeBankNameData"));
						String rnl_TgDraweeBankName=mGetText("id", mGetPropertyFromFile("rnl_TgDraweeBankNameid"), "value");
						DraweeBankName.add(rnl_TgDraweeBankName);

						//Cheque/DD Number
						mWaitForVisible("id", mGetPropertyFromFile("rnl_TgChequeDDNoid"));
						mCustomWait(1000);
						mSendKeys("id", mGetPropertyFromFile("rnl_TgChequeDDNoid"), mGetPropertyFromFile("rnl_TgChequeDDNoData"));
						ChequeDDNo.add(mGetPropertyFromFile("rnl_TgChequeDDNoData"));

						//Cheque/DDDate/cashdate
						mDateSelect("id",mGetPropertyFromFile("rnl_TgChequeDDDateid"), mGetPropertyFromFile("rnl_TgChequeDDDateData"));
						ChequeDDDate.add(mGetPropertyFromFile("rnl_TgChequeDDDateData"));

						//MICR Number
						mWaitForVisible("id", mGetPropertyFromFile("rnl_TgMicrNumid"));
						mCustomWait(1000);
						mSendKeys("id", mGetPropertyFromFile("rnl_TgMicrNumid"), mGetPropertyFromFile("rnl_TgMicrNumData"));
						MicrNum.add(mGetPropertyFromFile("rnl_TgMicrNumData"));

						//Security Deposit Amount
						mWaitForVisible("id", mGetPropertyFromFile("rnl_TgDepositAmountid"));
						mCustomWait(1000);
						mSendKeys("id", mGetPropertyFromFile("rnl_TgDepositAmountid"), mGetPropertyFromFile("rnl_TgDepositAmountData"));
						SecurityDepositamt.add(mGetPropertyFromFile("rnl_TgDepositAmountData"));
					}					

					//Security Deposit Remarks
					mWaitForVisible("id", mGetPropertyFromFile("rnl_TgDepositRemarkid"));
					mCustomWait(1000);
					mSendKeys("id", mGetPropertyFromFile("rnl_TgDepositRemarkid"), mGetPropertyFromFile("rnl_TgDepositRemarkData"));
					DepositRemarks.add(mGetPropertyFromFile("rnl_TgDepositRemarkData"));
					mCustomWait(1000);
					mTakeScreenShot();
					mCustomWait(1000);
				}


				//Tenant Contract Generation Submit
				mCustomWait(2000);
				mWaitForVisible("css", mGetPropertyFromFile("rnl_TgTenantContractFinalSubBtnid"));
				mClick("css", mGetPropertyFromFile("rnl_TgTenantContractFinalSubBtnid"));


				mCustomWait(1000);
				mWaitForVisible("id", mGetPropertyFromFile("rnl_TgTenantContractProcdBtnid"));

				String ContractNumber = mGetText("css", mGetPropertyFromFile("rnl_TgTenantContractProceedContractNoMsgid"));
				RNLContractNo.add(ContractNumber);
				System.out.println("Contract Number is: "+ContractNumber);



				TCcontractNo.add(ContractNumber);
				System.out.println("TCcontractNo is : "+TCcontractNo);
				log.info("TCcontractNo is : "+TCcontractNo);


				//Pop Up Msg Assert			
				String fancymsg = mGetText("css", mGetPropertyFromFile("rnl_TgEstatePopUpAssertMsgid"));
				System.out.println(fancymsg);
				strippedString = fancymsg.replace(ContractNumber, "");
				System.out.println("Stripped msg:: "+strippedString);	
				mCustomWait(1000);
				mAssert(mGetPropertyFromFile("rnl_TgSubmitPopUpAssertMsgData"),strippedString.trim(),"actual : "+mGetPropertyFromFile("rnl_TgSubmitPopUpAssertMsgData").trim()+"expected : "+strippedString.trim());
				mTakeScreenShot();
				mCustomWait(1000);

				mClick("id",  mGetPropertyFromFile("rnl_TgTenantContractProcdBtnid"));
			}


			// edit Tenant Contract

			else if(mGetPropertyFromFile("rnl_TgEditTenantContract").equalsIgnoreCase("Yes") && mGetPropertyFromFile("rnl_TgAddTenantContract").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_TgViewTenantContract").equalsIgnoreCase("No"))
			{
				mCustomWait(1000);

				// to search tenant contract no. to edit				

				tenantContractSearchCriteria();

				mWaitForVisible("xpath", mGetPropertyFromFile("rnl_TgContractEditBtnID"));
				mCustomWait(1000);

				mTakeScreenShot();
				mClick("xpath", mGetPropertyFromFile("rnl_TgContractEditBtnID"));

				//Tender Reference
				mWaitForVisible("id", mGetPropertyFromFile("rnl_TgTenderRefid"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TgTenderRefid"), mGetPropertyFromFile("rnl_TgEditTenderRefData"));
				TC_TenderRef.add(mGetPropertyFromFile("rnl_TgEditTenderRefData"));

				//Tender Date
				mDateSelect("id",mGetPropertyFromFile("rnl_TgTenderDateid"), mGetPropertyFromFile("rnl_TgEditTenderDateData"));
				TC_TenderDate.add(mGetPropertyFromFile("rnl_TgEditTenderDateData"));

				//Contract Date
				mDateSelect("id",mGetPropertyFromFile("rnl_TgContractDateid"), mGetPropertyFromFile("rnl_TgEditContractDateData"));
				TC_ContractDate.add(mGetPropertyFromFile("rnl_TgEditContractDateData"));

				//Sending Contract Value
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TgContractAmountid"), mGetPropertyFromFile("rnl_TgEditContractAmountData"));
				TC_ContractAmount.add(mGetPropertyFromFile("rnl_TgEditContractAmountData"));

				//Sending Contract Discount Value
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TgContractDiscountValueid"), mGetPropertyFromFile("rnl_TgEditContractDiscountValueData"));
				TC_DiscountAmount.add(mGetPropertyFromFile("rnl_TgEditContractDiscountValueData"));

				mTab("id", mGetPropertyFromFile("rnl_TgContractDiscountValueid")); 

				// Round Off Amount
				String ROA=mGetText("id",mGetPropertyFromFile("rnl_TgContractRoundoffAmountid"),"value");
				TC_RoundOffAmount.add(ROA);
				System.out.println("round off amount is : "+ ROA);

				// payable Amount
				String Payable=mGetText("id",mGetPropertyFromFile("rnl_TgContractPayableAmountid"),"value");
				TC_PayableAmount.add(Payable);
				System.out.println("payable amount is : "+ Payable);


				//Contract Remarks
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TgContractRemarksid"), mGetPropertyFromFile("rnl_TgEditContractRemarksData"));
				TC_ContractRemarks.add(mGetPropertyFromFile("rnl_TgEditContractRemarksData"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mscroll(0,700);			

				//Amount Bifurcation
				mCustomWait(1000);
				mClick("xpath",  mGetPropertyFromFile("rnl_TgAmountBifurcationBtnid"));

				mWaitForVisible("id", mGetPropertyFromFile("rnl_TgNxtDescrptnAddBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();

				mSendKeys("id", mGetPropertyFromFile("rnl_TgTaxAmountid"), mGetPropertyFromFile("rnl_TgEditContractAmountData"));
				TC_TaxAmount.add(mGetPropertyFromFile("rnl_TgEditContractAmountData"));

				mClick("id",  mGetPropertyFromFile("rnl_TgNxtDescrptnAddBtnid"));

				//Discount Description
				mWaitForVisible("id", mGetPropertyFromFile("rnl_TgTaxDescrptnid1"));
				mCustomWait(1000);
				mSelectDropDown("id",  mGetPropertyFromFile("rnl_TgTaxDescrptnid1"), mGetPropertyFromFile("rnl_TgEditTaxDescrptnData1"));
				TC_DiscountDescription.add(mGetPropertyFromFile("rnl_TgEditTaxDescrptnData1"));
				//Sending Discount amount
				mCustomWait(1000);
				mSendKeys("id",  mGetPropertyFromFile("rnl_TgTaxAmountid1"), mGetPropertyFromFile("rnl_TgEditContractDiscountValueData"));
				mTab("id", mGetPropertyFromFile("rnl_TgTaxAmountid1")); 
				TC_DiscountAmount.add(mGetPropertyFromFile("rnl_TgEditContractDiscountValueData"));

				// Amount Bifurcation total Amount
				String abta=mGetText("id",mGetPropertyFromFile("rnl_TgAmountBifurcationTotalAmountid"),"value");
				System.out.println("total amount is : "+ abta);			
				mAssert(abta, mGetPropertyFromFile("rnl_TgEditTotalAmountData"), "Actual  :"+abta+" Expected  :"+mGetPropertyFromFile("rnl_TgEditTotalAmountData"));


				//Amount Bifurcation Submit
				mWaitForVisible("css", mGetPropertyFromFile("rnl_TgAmountBifurcationSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_TgAmountBifurcationSubBtnid"));

				//Select Payment Schedule Button
				mWaitForVisible("xpath", mGetPropertyFromFile("rnl_TgSelctPaymentScheduleBtnid"));
				mCustomWait(1000);
				mClick("xpath",  mGetPropertyFromFile("rnl_TgSelctPaymentScheduleBtnid"));

				//Payment Schedule
				mWaitForVisible("id", mGetPropertyFromFile("rnl_TgPaymentScheduleDropDwnid"));
				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_TgPaymentScheduleDropDwnid"), mGetPropertyFromFile("rnl_TgEditPaymentScheduleDropDwnData"));


				//Sending Amount
				mWaitForVisible("id", mGetPropertyFromFile("rnl_TgPaymentScheduleAmountid"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TgPaymentScheduleAmountid"), Payable);
				mTab("id", mGetPropertyFromFile("rnl_TgPaymentScheduleAmountid")); 

				// Payment Schedule total Amount
				String psta=mGetText("id",mGetPropertyFromFile("rnl_TgPaymentScheduleTotalAmountid"),"value");
				System.out.println("total amount is : "+ psta);			
				mAssert(psta, mGetPropertyFromFile("rnl_TgTotalEditAmountData"), "Actual  :"+psta+" Expected  :"+mGetPropertyFromFile("rnl_TgEditTotalAmountData"));


				//Search
				mWaitForVisible("name", mGetPropertyFromFile("rnl_TgPaymentScheduleSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("name", mGetPropertyFromFile("rnl_TgPaymentScheduleSubBtnid"));


				//Select Contract Terms
				mWaitForVisible("xpath", mGetPropertyFromFile("rnl_TgSelectContrctTermSubBtnid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("rnl_TgSelectContrctTermSubBtnid"));

				//Selecting Contracts
				//Contract_1
				mWaitForVisible("id", mGetPropertyFromFile("rnl_TgContractTerm0id"));
				mCustomWait(1000);
				mClick("id",  mGetPropertyFromFile("rnl_TgContractTerm2id"));

				//Contract_2
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("rnl_TgContractTerm3id"));

				//Contract Terms Submit
				mWaitForVisible("name", mGetPropertyFromFile("rnl_TgContractTermSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("name", mGetPropertyFromFile("rnl_TgContractTermSubBtnid"));

				mCustomWait(2000);	
				if(mGetPropertyFromFile("rnl_TgDeleteAddedEstate").equalsIgnoreCase("yes"))
				{
					mCustomWait(1500);
					mClick("css", mGetPropertyFromFile("rnl_TgEstateDeleteBtnID"));
					mWaitForVisible("css", mGetPropertyFromFile("rnl_TgEstateDeletePopUpBoxID"));
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("rnl_TgEstateDeletePopUpBtnID"));
					mWaitForVisible("css", mGetPropertyFromFile("rnl_TgEstateDeletePopUpMsgBoxID"));

					String fancymsg = mGetText("css", mGetPropertyFromFile("rnl_TgEstateDeletePopUpMsgID"));
					System.out.println(fancymsg);					
					mCustomWait(1000);
					mAssert(fancymsg,mGetPropertyFromFile("rnl_TgAssertDeletePopUpMsgData"),"actual : "+fancymsg+"expected : " + mGetPropertyFromFile("rnl_TgAssertDeletePopUpMsgData"));
					mTakeScreenShot();

					mClick("css", mGetPropertyFromFile("rnl_TgEstateDeletePopUpBoxCloseID"));
				}

				//Select Estate Button
				mWaitForVisible("css", mGetPropertyFromFile("rnl_TgSelectEstateBtnid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_TgSelectEstateBtnid"));

				//sending Estate Code
				mWaitForVisible("id", mGetPropertyFromFile("rnl_TgEstateCodeid"));
				mCustomWait(1000);
				//				mSendKeys("id", mGetPropertyFromFile("rnl_TgEstateCodeid"), EstateNo.get(CurrentinvoCount));
				mSendKeys("id", mGetPropertyFromFile("rnl_TgEstateCodeid"),mGetPropertyFromFile("rnl_TgEditEstateNoData"));
				TC_EstateCode.add(mGetPropertyFromFile("rnl_TgEditEstateNoData"));

				//Estate Code Search Button
				mWaitForVisible("xpath", mGetPropertyFromFile("rnl_TgSearchEstateBtnid"));
				mCustomWait(1000);
				mClick("xpath",  mGetPropertyFromFile("rnl_TgSearchEstateBtnid"));

				//Selecting Estate with check box
				mWaitForVisible("name", mGetPropertyFromFile("rnl_TgSelectEstateCheckBoxid"));
				mCustomWait(1000);
				mClick("name", mGetPropertyFromFile("rnl_TgSelectEstateCheckBoxid"));

				//Select Estate Submit Button
				mWaitForVisible("name", mGetPropertyFromFile("rnl_TgSelectEstateSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("name", mGetPropertyFromFile("rnl_TgSelectEstateSubBtnid"));

				//Pop Up Msg Assert
				mWaitForVisible("css", mGetPropertyFromFile("rnl_TgEstateFancyBoxCloseid"));
				mCustomWait(1000);
				String MsgAftrEstateSub = mGetText("css", mGetPropertyFromFile("rnl_TgEstatePopUpAssertMsgid"));
				mCustomWait(1000);
				mAssert(MsgAftrEstateSub, mGetPropertyFromFile("rnl_TgEstatePopUpAssertMsgData"), "Actual msg : "+MsgAftrEstateSub+" Expected msg :"+mGetPropertyFromFile("rnl_TgEstatePopUpAssertMsgData")+"at Estate selection.");
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				System.out.println(MsgAftrEstateSub);
				mClick("css", mGetPropertyFromFile("rnl_TgEstateFancyBoxCloseid"));

				//Sending Deposit Number
				mWaitForVisible("id", mGetPropertyFromFile("rnl_TgDepositNumid"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TgDepositNumid"), mGetPropertyFromFile("rnl_TgEditDepositNumData"));
				DepositNum.add(mGetPropertyFromFile("rnl_TgEditDepositNumData"));

				//Sending Deposit Date
				mDateSelect("id",mGetPropertyFromFile("rnl_TgDepositDateid"), mGetPropertyFromFile("rnl_TgEditDepositDateData"));
				DepositDate.add(mGetPropertyFromFile("rnl_TgEditDepositDateData"));
				//Drawee Bank Name
				mWaitForVisible("id", mGetPropertyFromFile("rnl_TgDraweeBankNameid"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TgDraweeBankNameid"), mGetPropertyFromFile("rnl_TgEditDraweeBankNameData"));
				DraweeBankName.add(mGetPropertyFromFile("rnl_TgEditDraweeBankNameData"));

				//Cheque/DD Number
				mWaitForVisible("id", mGetPropertyFromFile("rnl_TgChequeDDNoid"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TgChequeDDNoid"), mGetPropertyFromFile("rnl_TgEditChequeDDNoData"));
				ChequeDDNo.add(mGetPropertyFromFile("rnl_TgEditChequeDDNoData"));

				//Cheque/DDDate
				mDateSelect("id",mGetPropertyFromFile("rnl_TgChequeDDDateid"), mGetPropertyFromFile("rnl_TgEditChequeDDDateData"));
				ChequeDDDate.add(mGetPropertyFromFile("rnl_TgEditChequeDDDateData"));

				//MICR Number
				mWaitForVisible("id", mGetPropertyFromFile("rnl_TgMicrNumid"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TgMicrNumid"), mGetPropertyFromFile("rnl_TgEditMicrNumData"));
				MicrNum.add(mGetPropertyFromFile("rnl_TgEditMicrNumData"));

				//Security Deposit Amount
				mWaitForVisible("id", mGetPropertyFromFile("rnl_TgDepositAmountid"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TgDepositAmountid"), mGetPropertyFromFile("rnl_TgEditDepositAmountData"));
				SecurityDepositamt.add(mGetPropertyFromFile("rnl_TgEditDepositAmountData"));

				//Security Deposit Remarks
				mWaitForVisible("id", mGetPropertyFromFile("rnl_TgDepositRemarkid"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TgDepositRemarkid"), mGetPropertyFromFile("rnl_TgEditDepositRemarkData"));
				DepositRemarks.add(mGetPropertyFromFile("rnl_TgEditDepositRemarkData"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);

				//Tenant Contract Generation Submit
				mWaitForVisible("css", mGetPropertyFromFile("rnl_TgTenantContractFinalSubBtnid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_TgTenantContractFinalSubBtnid"));
				mGenericWait();
				mWaitForVisible("id", mGetPropertyFromFile("rnl_TgTenantContractProcdBtnid"));
				mCustomWait(1000);

				//Pop Up Msg Assert			
				String fancymsg = mGetText("css", mGetPropertyFromFile("rnl_TgEditTenantContractProceedMsgid"));
				System.out.println(fancymsg);					
				mCustomWait(1000);
				mAssert(fancymsg,mGetPropertyFromFile("rnl_TgEditSubmitPopUpAssertMsgData"),"actual : "+fancymsg+"expected : " + mGetPropertyFromFile("rnl_TgEditSubmitPopUpAssertMsgData"));
				mTakeScreenShot();
				mCustomWait(1000);				
				mClick("id",  mGetPropertyFromFile("rnl_TgTenantContractProcdBtnid"));
			}


			// view Tenant Contract

			else if(mGetPropertyFromFile("rnl_TgViewTenantContract").equalsIgnoreCase("Yes") && mGetPropertyFromFile("rnl_TgAddTenantContract").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_TgEditTenantContract").equalsIgnoreCase("No"))
			{
				mCustomWait(1000);
				//				mSendKeys("id", mGetPropertyFromFile("rnl_TgSearchTenantContractNoid"), mGetPropertyFromFile("rnl_TgAgencyTenantNoData"));


				// to search tenant contract no. to view				

				tenantContractSearchCriteria();				

				mWaitForVisible("xpath", mGetPropertyFromFile("rnl_TgContractViewBtnID"));
				mCustomWait(1000);

				mTakeScreenShot();
				mClick("xpath", mGetPropertyFromFile("rnl_TgContractViewBtnID"));

				//Tender Reference
				mWaitForVisible("id", mGetPropertyFromFile("rnl_TgTenderRefid"));
				mCustomWait(1000);
				mTakeScreenShot();						
			}

			else
			{
				System.out.println("No further action can be performed as Add,view and Edit flag are either true or false");
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in tenantContractGen script");
		}
	}

	//method for Bill Generation
	public void billGeneration_Script()
	{
		try
		{
			//Navigating link from Rent and lease to Transaction to Bill Generation

			mNavigation(mGetPropertyFromFile("rnl_BgRentNLeaseLinkid"),mGetPropertyFromFile("rnl_BgTransactionLinkid"),mGetPropertyFromFile("rnl_BgRentLinkid"),mGetPropertyFromFile("rnl_BgRentGenerationLinkid"));

			//Sending Contract Number to search contract no. for bill generation
			mWaitForVisible("id", mGetPropertyFromFile("rnl_BgContractNumInputid"));
			mCustomWait(1000);

			billGenerationSearchCriria();

			//Sending Bill Date
			mCustomWait(2000);
			mDateSelect("id",mGetPropertyFromFile("rnl_BgBillDateOfApplicationid"),mGetPropertyFromFile("rnl_BgBillDateOfApplicationData"));
			mCustomWait(2000);
			String rnl_BgBillDateOfApplication=mGetText("id",mGetPropertyFromFile("rnl_BgBillDateOfApplicationid"), "value");
			TM_BillGenerationdate.add(rnl_BgBillDateOfApplication);

			//Sending Remarks
			mWaitForVisible("id", mGetPropertyFromFile("rnl_BgRemarksOnAppctnid"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("rnl_BgRemarksOnAppctnid"), mGetPropertyFromFile("rnl_BgRemarksOnAppctnData"));
			String rnl_BgRemarksOnAppctn=mGetText("id",mGetPropertyFromFile("rnl_BgRemarksOnAppctnid"), "value");
			TM_BillGenerationRemarks.add(rnl_BgRemarksOnAppctn);

			ClubbedUtils.rnl_mDynamicReadbillgenGrid(mGetPropertyFromFile("rnl_BgBillGenTableNameid"));

			//checkbox to select application
			mCustomWait(1000);
			mClick("id", mGetPropertyFromFile("rnl_BgChkBoxSelctnid"));

			//Bill generate Button
			mWaitForVisible("xpath", mGetPropertyFromFile("rnl_BgBillGenerateBtnid"));
			mTakeScreenShot();
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("rnl_BgBillGenerateBtnid"));

			//Pop up 
			mWaitForVisible("css", mGetPropertyFromFile("rnl_BgBillGenClosePopUpid"));
			mCustomWait(1000);
			String msgAtBillGen = mGetText("css", mGetPropertyFromFile("rnl_BgBillGenAssertMsgid"));	
			mCustomWait(1000);
			mAssert(msgAtBillGen, mGetPropertyFromFile("rnl_BgBillGenAssertMsgData"),"Msg at Bill Generation Pop up. Actual :"+msgAtBillGen+" Expected :"+mGetPropertyFromFile("rnl_BgBillGenAssertMsgData"));
			System.out.println(msgAtBillGen);

			String splitMsg = msgAtBillGen.replaceAll("[^0-9]+", " ");
			String[] splitMsg1=splitMsg.trim().split(" ");		   
			System.out.println("msg1 is = "+splitMsg1[0]); 
			System.out.println("msg2 is = "+splitMsg1[1]);

			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("rnl_BgBillGenClosePopUpid"));
			mCustomWait(2000);

			if(!splitMsg1[0].equals(splitMsg1[1]))
			{
				int[] numbers = new int[splitMsg1.length];
				for(int i = 0;i < splitMsg1.length;i++)
				{				   
					numbers[i] = Integer.parseInt(splitMsg1[i]);
				}
				num=numbers[0]-numbers[1];

				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("rnl_BgShowErrorBtnid"));				
				mWaitForVisible("css", mGetPropertyFromFile("rnl_BgShowErrorBackid"));
				mCustomWait(2000);
				ClubbedUtils.rnl_mDynamicReadShowErrorGrid(mGetPropertyFromFile("rnl_BgShowErrorTableid"),num);					
				mCustomWait(1000);
				mTakeScreenShot();								
			}

		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in billGeneration_Script");
		}
	}

	//Bill Printing
	public void billPrinting_Script()
	{
		try
		{
			//Navigating from Rent And Lease link to Transaction to bill printing

			mWaitForVisible("linkText", mGetPropertyFromFile("rnl_BprRentNLeaseLinkid"));
			mNavigation(mGetPropertyFromFile("rnl_BprRentNLeaseLinkid"), mGetPropertyFromFile("rnl_BprTransactionsLinkid"), mGetPropertyFromFile("rnl_BprRentLinkid"), mGetPropertyFromFile("rnl_BprRentPrintingLinkid"));
			mCustomWait(1000);

			//sending contract Number
			mWaitForVisible("id", mGetPropertyFromFile("rnl_BprContractid"));
			mTakeScreenShot();
			mCustomWait(1000);

			// to search contract no. for bill printing

			billPrintingSearchCriteria();

			ClubbedUtils.rnl_mDynamicReadBillPrintGrid(mGetPropertyFromFile("rnl_BprGridTableNameid"),pageNo);

			//pdfpattern.rnl_BillPrintingPDF(pdfoutput,TCtenantName,TCcontractNo,TCcontractFromDate,TCtenantNo,TCcontractToDate,TCcontractAmount);
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in billPrinting_Script");
		}
	}

	//Renewal Of Rent Contract
	public void renewalOfRentContract_Script()
	{
		try
		{
			//navigating from Rent and lease to Renewal of new contract
			mNavigation("rnl_RrRentNLeaseOnlineDeptServiceLinkid","rnl_RrRentNLeaseLinkid", "rnl_RrRenewalOfRentContrctLinkid");
			mCustomWait(1000);

			mWaitForVisible("css", mGetPropertyFromFile("Servicenametextrenewalid"));
			RnlServiceName=mGetText("css", mGetPropertyFromFile("Servicenametextrenewalid"));
			ServiceNameRNL.add(RnlServiceName);
			System.out.println("Service name is" +ServiceNameRNL);
			RNLserviceName=mGetText("css", mGetPropertyFromFile("Servicenametextrenewalid"));

			//Applicant Details
			mWaitForVisible("id", mGetPropertyFromFile("rnl_RrApplicantTitleid"));
			mCustomWait(1000);
			mSelectDropDown("id", mGetPropertyFromFile("rnl_RrApplicantTitleid"), mGetPropertyFromFile("rnl_RrApplicantTitleData"));
			String rnl_RrApplicantTitle=mCaptureSelectedValue("id", mGetPropertyFromFile("rnl_RrApplicantTitleid"));
			Rnl_ApplicantTitle.add(rnl_RrApplicantTitle);

			//First name
			mWaitForVisible("id", mGetPropertyFromFile("rnl_RrAplcntFirstNameid"));
			mCustomWait(1000);
			mSendKeys("id",  mGetPropertyFromFile("rnl_RrAplcntFirstNameid"),mGetPropertyFromFile("rnl_RrAplcntFirstNameData"));
			String rnl_RrAplcntFirstName=mGetText("id", mGetPropertyFromFile("rnl_RrAplcntFirstNameid"),"value");
			Rnl_ApplicantFname.add(rnl_RrAplcntFirstName);

			//Middle Name
			mWaitForVisible("id", mGetPropertyFromFile("rnl_RrAplcntMiddleNameid"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("rnl_RrAplcntMiddleNameid"), mGetPropertyFromFile("rnl_RrAplcntMiddleNameData"));
			String rnl_RrAplcntMiddleName=mGetText("id", mGetPropertyFromFile("rnl_RrAplcntMiddleNameid"),"value");
			Rnl_ApplicantMname.add(rnl_RrAplcntMiddleName);

			//Last Name
			mWaitForVisible("id", mGetPropertyFromFile("rnl_RrAplcntLastNameid"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("rnl_RrAplcntLastNameid"), mGetPropertyFromFile("rnl_RrAplcntLastNameData"));
			String rnl_RrAplcntLastName=mGetText("id", mGetPropertyFromFile("rnl_RrAplcntLastNameid"),"value");
			Rnl_ApplicantLname.add(rnl_RrAplcntLastName);

			String Fname = mGetPropertyFromFile("rnl_RrAplcntFirstNameData");
			String Mname = mGetPropertyFromFile("rnl_RrAplcntMiddleNameData");
			String Lname = mGetPropertyFromFile("rnl_RrAplcntLastNameData");

			rrcapplName=Fname+" "+Mname+" "+Lname;
			System.out.println("applName is : " + rrcapplName); 
			Rnl_ApplicantFullname.add(rrcapplName);

			//Mobile Number
			mWaitForVisible("id", mGetPropertyFromFile("rnl_RrAplcntMobileNoid"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("rnl_RrAplcntMobileNoid"), mGetPropertyFromFile("rnl_RrAplcntMobileNoData"));
			String rnl_RrAplcntMobileNo=mGetText("id", mGetPropertyFromFile("rnl_RrAplcntMobileNoid"),"value");
			Rnl_ApplicantMbNo.add(rnl_RrAplcntMobileNo);

			//Email id
			mWaitForVisible("id", mGetPropertyFromFile("rnl_RrAplcntEmailid"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("rnl_RrAplcntEmailid"), mGetPropertyFromFile("rnl_RrAplcntEmailData"));
			String rnl_RrAplcntEmail=mGetText("id", mGetPropertyFromFile("rnl_RrAplcntEmailid"),"value");
			Rnl_ApplicantEmail.add(rnl_RrAplcntEmail);

			//Add1
			mWaitForVisible("id", mGetPropertyFromFile("rnl_RrAplcntCorrAdd1id"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("rnl_RrAplcntCorrAdd1id"), mGetPropertyFromFile("rnl_RrAplcntCorrAdd1Data"));
			String rnl_RrAplcntCorrAdd1=mGetText("id", mGetPropertyFromFile("rnl_RrAplcntCorrAdd1id"),"value");
			Rnl_ApplicantAddOne.add(rnl_RrAplcntCorrAdd1);

			//Add2
			mWaitForVisible("id", mGetPropertyFromFile("rnl_RrAplcntCorrAdd2id"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("rnl_RrAplcntCorrAdd2id"), mGetPropertyFromFile("rnl_RrAplcntCorrAdd2Data"));
			String rnl_RrAplcntCorrAdd2=mGetText("id", mGetPropertyFromFile("rnl_RrAplcntCorrAdd2id"),"value");
			Rnl_ApplicantAddTwo.add(rnl_RrAplcntCorrAdd2);

			//Pin code
			mWaitForVisible("id", mGetPropertyFromFile("rnl_RrAplcntPincodeid"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("rnl_RrAplcntPincodeid"), mGetPropertyFromFile("rnl_RrAplcntPincodeData"));
			String rnl_RrAplcntPincode=mGetText("id", mGetPropertyFromFile("rnl_RrAplcntPincodeid"),"value");
			Rnl_ApplicantPinCode.add(rnl_RrAplcntPincode);


			//Search Contract Buttom
			mWaitForVisible("css", mGetPropertyFromFile("rnl_RrSearchContractBtnid"));
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("rnl_RrSearchContractBtnid"));

			mWaitForVisible("id", mGetPropertyFromFile("rnl_RrContractNoid"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("rnl_RrContractNoid"), mGetPropertyFromFile("rnl_ContractNoData"));
			RNLContractNo.add(mGetPropertyFromFile("rnl_ContractNoData"));
			System.out.println("Contract no is::"+RNLContractNo);

			//Search Button
			mWaitForVisible("css", mGetPropertyFromFile("rnl_RrSearchPartContractBtnid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("rnl_RrSearchPartContractBtnid"));



			//selecting Application
			mWaitForVisible("name", mGetPropertyFromFile("rnl_RrSelectContrctRadioBtnid"));
			mCustomWait(1000);
			mClick("name", mGetPropertyFromFile("rnl_RrSelectContrctRadioBtnid"));

			//Submit Button
			mWaitForVisible("css", mGetPropertyFromFile("rnl_RrSelectContrctSubBtnid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("rnl_RrSelectContrctSubBtnid"));

			//From Date
			mCustomWait(500);
			mDateSelect("id",mGetPropertyFromFile("rnl_RrRenewalFromDateid"), mGetPropertyFromFile("rnl_RrRenewalFromDateData"));
			String rnl_RrRenewalFromDate=mGetText("id",mGetPropertyFromFile("rnl_RrRenewalFromDateid"), "value");
			Rnl_BookingfromDate.add(rnl_RrRenewalFromDate);

			////////////////////////////////////////////06062017///////////////////

			try{
				mCustomWait(500);
				//Renewal To Date
				mDateSelect("id",mGetPropertyFromFile("rnl_RrRenewalTodateid"), mGetPropertyFromFile("rnl_RrRenewalTodateData"));
				Rnl_BookingtoDate.add( mGetPropertyFromFile("rnl_RrRenewalTodateData"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				mCustomWait(500);
				mTakeScreenShot();
				//If hoarding Not available
				mWaitForVisible("css", mGetPropertyFromFile("adh_Scr_RenewalHoardingNotAvailableIPopUpID"));
				mClick("css", mGetPropertyFromFile("adh_Scr_RenewalHoardingNotAvailableIPopUpID"));

				//Deleting Hoarding from grid 
				mWaitForVisible("xpath", mGetPropertyFromFile("adh_Scr_RenewalHoardingDeleteID"));
				mClick("xpath", mGetPropertyFromFile("adh_Scr_RenewalHoardingDeleteID"));
				mCustomWait(300);	
				//Click on on deleting confirmation message
				mWaitForVisible("xpath", mGetPropertyFromFile("adh_Scr_RenewalHoardingDeleteConfirmationID"));
				mTakeScreenShot();
				mClick("xpath", mGetPropertyFromFile("adh_Scr_RenewalHoardingDeleteConfirmationID"));
				mCustomWait(300);
				mWaitForVisible("xpath", mGetPropertyFromFile("adh_Scr_RenewalHoardingDeletePopUpMsgID"));
				mTakeScreenShot();

				// Capturing Pop Up message 
				String text=mGetText("css", mGetPropertyFromFile("adh_Scr_RenewalHoardingDeletePopUpMsgID"));
				System.out.println(text);
				mCustomWait(300);
				mWaitForVisible("css", mGetPropertyFromFile("adh_Scr_RenewalHoardingDeletePopUpCloseID"));
				mTakeScreenShot();
				mClick("css", mGetPropertyFromFile("adh_Scr_RenewalHoardingDeletePopUpCloseID"));
				mCustomWait(500);
				// Renewal From Date
				mCustomWait(500);

				//Selecting and Capturing From date
				mDateSelect("id",mGetPropertyFromFile("rnl_RrRenewalFromDateid"), mGetPropertyFromFile("rnl_RenewalRepeatFromDateData"));
				String adh_RcRenewalFromDate1=mGetText("id",mGetPropertyFromFile("rnl_RrRenewalFromDateid"), "value");

				//Selecting and Capturing Renewal To Date
				mDateSelect("id",mGetPropertyFromFile("rnl_RrRenewalTodateid"), mGetPropertyFromFile("rnl_RenewalRepeatToDateData"));
				adh_rc_BookingtoDate.add( mGetPropertyFromFile("adh_RrRenewalTodateData"));

				AddHoarding();				
			}

			//Sending remarks
			mWaitForVisible("id", mGetPropertyFromFile("rnl_RrTenantRemarksid"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("rnl_RrTenantRemarksid"), mGetPropertyFromFile("rnl_RrTenantRemarksData"));
			Rnl_ApplicantRemarks.add(mGetPropertyFromFile("rnl_RrTenantRemarksData"));

			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);

			mscroll(0,710);
			mCustomWait(1000);

			EstateDb_ContractDetails();


			//Uploading Documents
			mUpload("id", mGetPropertyFromFile("rnl_RrMandatoryDoc1id"), mGetPropertyFromFile("rnl_RrMandatoryDocData1"));
			mCustomWait(1000);
			mUpload("id", mGetPropertyFromFile("rnl_RrMandatoryDoc2id"), mGetPropertyFromFile("rnl_RrMandatoryDocData2"));
			mCustomWait(1000);

			mTakeScreenShot();
			mCustomWait(1000);


			payment("paymentMode","byBankOrULB");	

			if(!mGetPropertyFromFile("paymentMode").equalsIgnoreCase("online"))
			{
				//Submit Button
				mWaitForVisible("css", mGetPropertyFromFile("rnl_RrFormSubBtnid"));
				mCustomWait(1000);
				mClick("css",  mGetPropertyFromFile("rnl_RrFormSubBtnid"));

				//Proceed Button
				mWaitForVisible("id", mGetPropertyFromFile("rnl_RrFormSubProcdBtnid"));
				mCustomWait(1000);

				String PopUpMsg = mGetText("css", mGetPropertyFromFile("rnl_RrFormSubAssertMsgid"));
				System.out.println("Pop Up Message is :"+PopUpMsg);
				mAppNoArray("css",mGetPropertyFromFile("rnl_RrFormSubAssertApplicationNoid"));
				System.out.println("Application Number of rent renewal is :"+appNo);

				clv_ApplicanyionNo.add(appNo);

				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				String finalmsg= PopUpMsg.replaceAll("[0-9]", "");

				finalmsg=finalmsg.trim();
				System.out.println("actual assert msg is : "+finalmsg);
				System.out.println("Expected assert msg is : "+mGetPropertyFromFile("rnl_RrFormSubAssertFinalMsgData"));
				if(finalmsg.equals(mGetPropertyFromFile("rnl_RrFormSubAssertFinalMsgData").toString().trim())) 
				{
					System.out.println(" assert msg matches : ");
				}
				mAssert(finalmsg,mGetPropertyFromFile("rnl_RrFormSubAssertFinalMsgData").toString().trim(),"Final message does not match"+" Actual msg :"+finalmsg+"  Expected msg :"+mGetPropertyFromFile("rnl_RrFormSubAssertFinalMsgData").toString().trim());
				mCustomWait(1000);


				mClick("id", mGetPropertyFromFile("rnl_RrFormSubProcdBtnid"));
				mCustomWait(3000);			
				mChallanPDFReader();
				api.PdfPatterns.patternChallanPdf(pdfoutput, appNo, rrcapplName);
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in renewalOfRentContract_Script script");
		}
	}

	//Rent Payment Schedule
	public void rentPaymentSchedule_Script()
	{
		try
		{
			//Navigating from RentAndLease to Rent Payment Schedule

			mWaitForVisible("linkText", mGetPropertyFromFile("rnl_RentNLeaseOnlineDeptServiceLinkid"));
			mNavigation("rnl_RentNLeaseOnlineDeptServiceLinkid","rnl_RentNLeaseLinkid","rnl_RentPaymentScheduleLinkid");

			mCustomWait(1500);
			mTakeScreenShot();
			mCustomWait(1000);

			mWaitForVisible("id", mGetPropertyFromFile("rnl_RentSearchContractBtnid"));
			mCustomWait(1000);

			if(mGetPropertyFromFile("RNL_rpsSearchCriteria").equalsIgnoreCase("yes"))
			{
				//Search Contract button			
				mClick("id",  mGetPropertyFromFile("rnl_RentSearchContractBtnid"));

				//sending contract Number
				mWaitForVisible("id", mGetPropertyFromFile("rnl_RentSearchContractTxtid"));
				mCustomWait(1000);

				if(mGetPropertyFromFile("RNL_rpsSearchContractNo").equalsIgnoreCase("yes")) 
				{
					if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent")) 
					{
						mSendKeys("id", mGetPropertyFromFile("rnl_RentSearchContractTxtid"),RNLContractNo.get(CurrentinvoCount));
					}			
					else
					{
						mSendKeys("id", mGetPropertyFromFile("rnl_RentSearchContractTxtid"),mGetPropertyFromFile("applicationNo"));
					}
				} 

				if(mGetPropertyFromFile("RNL_rpsSearchTenantNo").equalsIgnoreCase("yes")) 
				{
					mSendKeys("id", mGetPropertyFromFile("RNL_rpsSearchTenantNoid"),mGetPropertyFromFile("RNL_rpsSearchTenantNoData"));
					mCustomWait(1000);
				}

				if(mGetPropertyFromFile("RNL_rpsSearchTenantName").equalsIgnoreCase("yes")) 
				{
					mSendKeys("id", mGetPropertyFromFile("RNL_rpsSearchTenantNameid"),mGetPropertyFromFile("RNL_rpsSearchTenantNameData"));
					mCustomWait(1000);
				}

				//Pop-Up Search Button
				mWaitForVisible("css", mGetPropertyFromFile("rnl_PopUpSearchAppBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_PopUpSearchAppBtnid"));

				//selecting one application
				mWaitForVisible("name", mGetPropertyFromFile("rnl_SelectAppRadioBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("name", mGetPropertyFromFile("rnl_SelectAppRadioBtnid"));

				//PopUp Submit
				mWaitForVisible("css", mGetPropertyFromFile("rnl_PopUpSelectAppSubBtnid"));
				mCustomWait(1000);
				mClick("css",  mGetPropertyFromFile("rnl_PopUpSelectAppSubBtnid"));
			}


			if(mGetPropertyFromFile("RNL_rpsDirectSearch").equalsIgnoreCase("yes"))
			{
				if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent")) 
				{
					mSendKeys("id", mGetPropertyFromFile("rnl_RentContractNoid"),RNLContractNo.get(CurrentinvoCount));
				}			
				else
				{
					mSendKeys("id", mGetPropertyFromFile("rnl_RentContractNoid"),mGetPropertyFromFile("applicationNo"));
				}
			}

			//Search Application
			mWaitForVisible("css", mGetPropertyFromFile("rnl_AftrAppNoSubSearchBtnid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("rnl_AftrAppNoSubSearchBtnid"));


			mCustomWait(3000);
			mTakeScreenShot();
			ClubbedUtils.rnl_mDynamicRentPayScdhGrid(mGetPropertyFromFile("rnl_RentPaymentScheduleTableid"));
			mCustomWait(1000);
			mClick("id", mGetPropertyFromFile("rnl_PaymentHistoryBtnid"));
			mWaitForVisible("css", mGetPropertyFromFile("rnl_rpsPaymentHistoryPopupBoxid"));
			mCustomWait(1000);			
			ClubbedUtils.rnl_mDynamicRPSPaymentHistoryGrid(mGetPropertyFromFile("rnl_rpsPaymentHistoryTableid"));
			if(rwcountRPSPaymentHistory>1)
			{
				dbtest();
				mCustomWait(2000);	

				mAssert(PaymentHistory,datatobewritten, "Actual msg::::"+PaymentHistory+" Expected::::"+datatobewritten);
			}
			mCustomWait(2000);
			mClick("css", mGetPropertyFromFile("rnl_rpsPaymentHistoryPopupCloseid"));

			//Submit Button
			mWaitForVisible("id",mGetPropertyFromFile("rnl_PaymentDuePaymentSubBtnid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);
			mClick("id", mGetPropertyFromFile("rnl_PaymentDuePaymentSubBtnid"));


			mCustomWait(1000);
			payment("paymentMode","byBankOrULB");
			mCustomWait(1000);
			if(!mGetPropertyFromFile("paymentMode").equalsIgnoreCase("online"))
			{	
				mWaitForVisible("id",mGetPropertyFromFile("rnl_PaymentDuePaymentSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("rnl_PaymentDuePaymentSubBtnid"));


				//proceed Button
				mWaitForVisible("id", mGetPropertyFromFile("rnl_PaymentDuePaymentProcdBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("rnl_PaymentDuePaymentProcdBtnid"));

				//Waiting for challan to get generate
				mCustomWait(3000);
				mChallanPDFReader();
				mCustomWait(1000);
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in rentPaymentSchedule_Script");
		}
	}

	public void bookingOfEstate() throws InterruptedException{

		mCreateArtefactsFolder("RNL_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("rnl_MBADeptName"),mGetPropertyFromFile("rnl_MBADeptPassword"));
		bookingOfEstate_Script();
		bookingOfEstateProceed();
		logOut();
		Ass_ULBName.add(mGetPropertyFromFile("Khagaul Nagar Parishad"));
		finalLogOut();
		if(mGetPropertyFromFile("municipality").equalsIgnoreCase("Khagaul Nagar Parishad"))  
		{		
			System.out.println("the ulname is : "+mGetPropertyFromFile("municipality"));	 
		}
		else
		{
			isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVreBookingOfEstateServiceName"));
		}
		mCloseBrowser();
	}




	public void scrutinyProcess(){
		mCreateArtefactsFolder("RNL_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		departmentLogin(mGetPropertyFromFile("cfc_RNLscruProcUserName"),mGetPropertyFromFile("cfc_RNLscruProcPassword"));
		//		departmentLogin(mGetPropertyFromFile("cfc_TPLev_2scruProcUserName"),mGetPropertyFromFile("cfc_TPLev_2scruProcPassword"));
		scrutiny.scrutinyProcess();
		logOut();
		finalLogOut();
		mCloseBrowser();
	}


	public void approvalLetterPrinting(){
		mCreateArtefactsFolder("RNL_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("rnl_MBADeptName"),mGetPropertyFromFile("rnl_MBADeptPassword"));
		bookingOfEstateApprovalLetter_script();
		logOut();
		finalLogOut();
		mCloseBrowser();		
	}


	public void tenantMaster()  throws IOException{
		mCreateArtefactsFolder("RNL_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		//		departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
		login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("rnl_MBADeptName"),mGetPropertyFromFile("rnl_MBADeptPassword"));
		//		beforeRnl_ApprovalLetter();
		tenantMaster_script();
		tenantMasterProceed_Script();
		logOut();
		finalLogOut();
		mCloseBrowser();
	}




	public void landAndEstateMasterEntry_Script() {

		try {

			mGenericWait();
			mNavigation("rnl_RentAndLeaseID","rnl_MasterID","rnl_Land/EstateMasterID");

			// to add estate

			if(mGetPropertyFromFile("rnl_LEMAddEstate").equalsIgnoreCase("Yes") && mGetPropertyFromFile("rnl_LEMEditEstate").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_LEMCreateGroup").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_LEMEditGroup").equalsIgnoreCase("No"))	
			{

				mWaitForVisible("css", mGetPropertyFromFile("rnl_LEMAddEstateBtnID"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_LEMAddEstateBtnID"));

				mWaitForVisible("id",  mGetPropertyFromFile("rnl_LEMEstateTypeID"));
				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_LEMEstateTypeID"),mGetPropertyFromFile("rnl_LEMEstateTypeData"));

				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_LEMEstateSubTypeID"),mGetPropertyFromFile("rnl_LEMEstateSubTypeData"));

				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_LEMWardID"),mGetPropertyFromFile("rnl_LEMWardData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMEstateDescID"),mGetPropertyFromFile("rnl_LEMEstateDescData"));

				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_LEMRoadID"),mGetPropertyFromFile("rnl_LEMRoadData"));

				mCustomWait(1000);
				mDateSelect("id",mGetPropertyFromFile("rnl_LEMRegistrationDateID"), mGetPropertyFromFile("rnl_LEMRegistrationDateData"));


				//storing the estate name in arraylist
				mCustomWait(2000);
				name=mGetText("id", mGetPropertyFromFile("rnl_LEMEstateDescID"),"value");
				System.out.println("the estatename is :: "+name); 
				estatename.add(name); 
				System.out.println("the estatenamearray is :: "+estatename); 
				mCustomWait(1000); 

				if(mGetPropertyFromFile("rnl_LEMSelectGroup").equalsIgnoreCase("Yes"))
				{
					mWaitForVisible("xpath",  mGetPropertyFromFile("rnl_LEMSearchGroupID"));
					mCustomWait(1000);
					mClick("xpath", mGetPropertyFromFile("rnl_LEMSearchGroupID"));

					mWaitForVisible("css",  mGetPropertyFromFile("rnl_LEMSelectGroupID"));
					mCustomWait(1000);
					mClick("css", mGetPropertyFromFile("rnl_LEMSelectGroupID"));		
				}

				mWaitForVisible("xpath",  mGetPropertyFromFile("rnl_LEMSearchPropertyID"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("rnl_LEMSearchPropertyID"));

				mWaitForVisible("id",mGetPropertyFromFile("rnl_LEMPropertyNoFieldID"));
				mSendKeys("id",mGetPropertyFromFile("rnl_LEMPropertyNoFieldID"),mGetPropertyFromFile("rnl_LEMPropertyNoFieldData"));

				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_LEMSearchPropertyBtnID"));
				mCustomWait(2000);
				mTakeScreenShot(); 

				mWaitForVisible("css",  mGetPropertyFromFile("rnl_LEMPropertyChkBoxID"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_LEMPropertyChkBoxID"));

				mWaitForVisible("id",  mGetPropertyFromFile("rnl_LEMAddressLineOneID"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMAddressLineOneID"),mGetPropertyFromFile("rnl_LEMAddressLineOneData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMAddressLineTwoID"),mGetPropertyFromFile("rnl_LEMAddressLineTwoData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMPincodeID"),mGetPropertyFromFile("rnl_LEMPincodeData"));

				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_LEMEstateCategoryID"),mGetPropertyFromFile("rnl_LEMEstateCategoryData"));

				mGenericWait();
				mDateSelect("id",mGetPropertyFromFile("rnl_LEMEstateHandoverDateID"), mGetPropertyFromFile("rnl_LEMEstateHandoverDateData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMEstateLengthID"),mGetPropertyFromFile("rnl_LEMEstateLengthData"));

				mCustomWait(1000);;
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMEstateWidthID"),mGetPropertyFromFile("rnl_LEMEstateWidthData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMEstateHeightID"),mGetPropertyFromFile("rnl_LEMEstateHeightData"));

				mCustomWait(1000);
				mSendKeys("name", mGetPropertyFromFile("rnl_LEMEstateAreaID"),mGetPropertyFromFile("rnl_LEMEstateAreaData"));

				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_LEMFloorNoID"),mGetPropertyFromFile("rnl_LEMFloorNoData"));
				mTakeScreenShot();

				mWaitForVisible("css",  mGetPropertyFromFile("rnl_LEMSubmitBtnID"));
				mClick("css", mGetPropertyFromFile("rnl_LEMSubmitBtnID"));
			}


			// to edit estate

			else if(mGetPropertyFromFile("rnl_LEMEditEstate").equalsIgnoreCase("Yes") && mGetPropertyFromFile("rnl_LEMAddEstate").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_LEMCreateGroup").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_LEMEditGroup").equalsIgnoreCase("No"))	
			{

				landAndEstateMasterEntrySearchCriteria(); 				

				mWaitForVisible("xpath",  mGetPropertyFromFile("LGL_LEMEditDetailsIconid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("LGL_LEMEditDetailsIconid"));

				mWaitForVisible("id",  mGetPropertyFromFile("rnl_LEMEstateTypeID"));
				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_LEMEstateTypeID"),mGetPropertyFromFile("rnl_LEMEditEstateTypeData"));

				mWaitForVisible("id",  mGetPropertyFromFile("rnl_LEMEstateSubTypeID"));
				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_LEMEstateSubTypeID"),mGetPropertyFromFile("rnl_LEMEditEstateSubTypeData"));

				mWaitForVisible("id",  mGetPropertyFromFile("rnl_LEMWardID"));
				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_LEMWardID"),mGetPropertyFromFile("rnl_LEMEditWardData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMEstateDescID"),mGetPropertyFromFile("rnl_LEMEditEstateDescData"));

				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_LEMRoadID"),mGetPropertyFromFile("rnl_LEMEditRoadData"));
				mTakeScreenShot(); 

				mCustomWait(1000);
				mDateSelect("id",mGetPropertyFromFile("rnl_LEMRegistrationDateID"), mGetPropertyFromFile("rnl_LEMEditRegistrationDateData"));

				if(mGetPropertyFromFile("rnl_LEMSelectGroup").equalsIgnoreCase("Yes"))
				{
					mCustomWait(1000);
					mClick("xpath", mGetPropertyFromFile("rnl_LEMSearchGroupID"));

					mWaitForVisible("css",  mGetPropertyFromFile("rnl_LEMSelectGroupID"));
					mCustomWait(1000);
					mClick("css", mGetPropertyFromFile("rnl_LEMSelectGroupID"));		
				}

				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("rnl_LEMSearchPropertyID"));

				mWaitForVisible("linkText",  mGetPropertyFromFile("rnl_LEMSearchPropertyBtnID"));
				mCustomWait(1000);
				mClick("linkText", mGetPropertyFromFile("rnl_LEMSearchPropertyBtnID"));

				mWaitForVisible("id",  mGetPropertyFromFile("rnl_LEMPropertyChkBoxID"));
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("rnl_LEMPropertyChkBoxID"));
				mCustomWait(2000);
				mTakeScreenShot(); 

				mWaitForVisible("id",  mGetPropertyFromFile("rnl_LEMAddressLineOneID"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMAddressLineOneID"),mGetPropertyFromFile("rnl_LEMEditAddressLineOneData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMAddressLineTwoID"),mGetPropertyFromFile("rnl_LEMEditAddressLineTwoData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMPincodeID"),mGetPropertyFromFile("rnl_LEMEditPincodeData"));

				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_LEMEstateCategoryID"),mGetPropertyFromFile("rnl_LEMEditEstateCategoryData"));

				mGenericWait();
				mDateSelect("id",mGetPropertyFromFile("rnl_LEMEstateHandoverDateID"), mGetPropertyFromFile("rnl_LEMEditEstateHandoverDateData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMEstateLengthID"),mGetPropertyFromFile("rnl_LEMEditEstateLengthData"));

				mCustomWait(1000);;
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMEstateWidthID"),mGetPropertyFromFile("rnl_LEMEditEstateWidthData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMEstateHeightID"),mGetPropertyFromFile("rnl_LEMEditEstateHeightData"));

				mCustomWait(1000);
				mSendKeys("name", mGetPropertyFromFile("rnl_LEMEstateAreaID"),mGetPropertyFromFile("rnl_LEMEstateAreaData"));

				mWaitForVisible("id",  mGetPropertyFromFile("rnl_LEMFloorNoID"));
				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_LEMFloorNoID"),mGetPropertyFromFile("rnl_LEMEditFloorNoData"));
				mTakeScreenShot();

				mWaitForVisible("css",  mGetPropertyFromFile("rnl_LEMSubmitBtnID"));
				mClick("css", mGetPropertyFromFile("rnl_LEMSubmitBtnID"));
			}


			// to create group

			else if(mGetPropertyFromFile("rnl_LEMCreateGroup").equalsIgnoreCase("Yes") && mGetPropertyFromFile("rnl_LEMEditEstate").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_LEMAddEstate").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_LEMEditGroup").equalsIgnoreCase("No"))	
			{

				mWaitForVisible("css", mGetPropertyFromFile("rnl_LEMAddGroupBtnID"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_LEMAddGroupBtnID"));

				mWaitForVisible("css",  mGetPropertyFromFile("rnl_LEMAddCreateGrpBtnID"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_LEMAddCreateGrpBtnID"));

				mWaitForVisible("id",  mGetPropertyFromFile("rnl_LEMGrpWardID"));
				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_LEMGrpWardID"),mGetPropertyFromFile("rnl_LEMGrpWardData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMGrpNameID"),mGetPropertyFromFile("rnl_LEMGrpNameData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMGrpAddrLineOneID"),mGetPropertyFromFile("rnl_LEMGrpAddrLineOneData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMGrpAddrLineTwoID"),mGetPropertyFromFile("rnl_LEMGrpAddrLineTwoData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMGrpPincodeID"),mGetPropertyFromFile("rnl_LEMGrpPincodeData"));

				mCustomWait(1000);
				if(mGetPropertyFromFile("rnl_LEMGrpPincodeID").equalsIgnoreCase("yes")) 
				{
					ClubbedUtils.rnl_mDynamicAddRowObjects( mGetPropertyFromFile("rnl_LEMGrpTableID"), mGetPropertyFromFile("rnl_LEMGrpEstDescpData"), mGetPropertyFromFile("rnl_LEMGrpEstStartDateData"),mGetPropertyFromFile("rnl_LEMGrpEstEndDateData"),mGetPropertyFromFile("rnl_LEMEditGroup"),mGetPropertyFromFile("rnl_AddRowCount"));
				}

				mWaitForVisible("css",  mGetPropertyFromFile("rnl_LEMSubmitBtnID"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_LEMSubmitBtnID"));
				mCustomWait(1000);							
			}


			// to edit group

			else if(mGetPropertyFromFile("rnl_LEMEditGroup").equalsIgnoreCase("Yes") && mGetPropertyFromFile("rnl_LEMEditEstate").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_LEMCreateGroup").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_LEMAddEstate").equalsIgnoreCase("No"))	
			{
				mWaitForVisible("css", mGetPropertyFromFile("rnl_LEMAddGroupBtnID"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_LEMAddGroupBtnID"));

				mWaitForVisible("css",  mGetPropertyFromFile("rnl_LEMGrpSearchBtnID"));


				// done by hiren kathiria on 29/08/2016 

				if(mGetPropertyFromFile("rnl_LEMSearchByWard").equalsIgnoreCase("yes"))
				{
					mSelectDropDown("id", mGetPropertyFromFile("rnl_LEMSearchByWardID"), mGetPropertyFromFile("rnl_LEMSearchByWardData"));
				}

				if(mGetPropertyFromFile("rnl_LEMSearchByGroupName").equalsIgnoreCase("yes"))
				{
					mSendKeys("id", mGetPropertyFromFile("rnl_LEMSearchByGroupNameID"), mGetPropertyFromFile("rnl_LEMSearchByGroupNameData"));
				}

				if(mGetPropertyFromFile("rnl_LEMSearchByGroupEstateNo").equalsIgnoreCase("yes"))
				{
					mSendKeys("id", mGetPropertyFromFile("rnl_LEMSearchByGroupEstateNoID"), mGetPropertyFromFile("rnl_LEMSearchByGroupEstateNoData"));
				}

				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_LEMGrpSearchBtnID"));

				mWaitForVisible("css",  mGetPropertyFromFile("rnl_LEMGrpSearchIconID"));
				mCustomWait(2000);
				mClick("css", mGetPropertyFromFile("rnl_LEMGrpSearchIconID"));

				mWaitForVisible("id",  mGetPropertyFromFile("rnl_LEMGrpNoSearchId"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMGrpNoSearchId"),mGetPropertyFromFile("rnl_LEMGrpNoSearchData"));

				mWaitForVisible("id",  mGetPropertyFromFile("rnl_LEMGrpGridSearchBtnId"));
				mCustomWait(2000);				
				mClick("id", mGetPropertyFromFile("rnl_LEMGrpGridSearchBtnId"));

				mWaitForVisible("xpath",  mGetPropertyFromFile("rnl_LEMGrpEditBtnId"));
				mCustomWait(2000);
				mClick("xpath", mGetPropertyFromFile("rnl_LEMGrpEditBtnId"));

				mCustomWait(2000);
				ClubbedUtils.rnl_mDynamicAddRowObjects( mGetPropertyFromFile("rnl_LEMGrpTableID"), mGetPropertyFromFile("rnl_LEMGrpEstDescpData"), mGetPropertyFromFile("rnl_LEMGrpEstStartDateData"),mGetPropertyFromFile("rnl_LEMGrpEstEndDateData"),mGetPropertyFromFile("rnl_LEMEditGroup"),mGetPropertyFromFile("rnl_AddRowCount"));

				mWaitForVisible("css",  mGetPropertyFromFile("rnl_LEMSubmitBtnID"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_LEMSubmitBtnID"));
				mCustomWait(1000);
			}


			else
			{
				System.out.println("No further action can be performed as both Add and Edit flag are either true or false");
			}

		}		
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in landAndEstateMasterEntry_Script");
		}

	}

	public void landEstateProceed_Script() throws IOException{

		try {

			if(mGetPropertyFromFile("rnl_LEMAddEstate").equalsIgnoreCase("Yes") && mGetPropertyFromFile("rnl_LEMCreateGroup").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_LEMEditEstate").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_LEMEditGroup").equalsIgnoreCase("No"))
			{
				mGenericWait();
				String estateProceedMsg = mGetText("css",mGetPropertyFromFile("rnl_LEMProceedMsgID"));
				//		EstateNo = estateProceedMsg.replaceAll("[^0-9]", "");

				Pattern landOrEstateNo = Pattern.compile("(?<=Estate Created successfully estate No is:)(.*)\\s*");
				Matcher matcher = landOrEstateNo.matcher(estateProceedMsg);
				if (matcher.find())
				{
					System.out.println(matcher.group(1));
					appNo=matcher.group(1).trim();
					EstateNo.add(appNo);  
					//	        mAppNoArray(matcher.group(1).trim());
				}

				//		System.out.println("Land/Estate number::::"+landOrEstateNo);
				System.out.println(" Land/Estate number::::"+appNo);		
				System.out.println("POP UP Msg is::::"+estateProceedMsg);

				mWaitForVisible("id", mGetPropertyFromFile("rnl_LEMProceedBtnID"));

				mGenericWait();		
				appSubmitMsg  =  mGetText("css", mGetPropertyFromFile("rnl_LEMProceedMsgID"));
				System.out.println("App submit msg"+appSubmitMsg);
				//		strippedString = appSubmitMsg.replace(landOrEstateNo, "");
				strippedString = appSubmitMsg.replace(appNo, "");
				System.out.println("Stripped msg:: "+strippedString);		
				mAssert(mGetPropertyFromFile("rnl_LEMEstProceedMsgData").trim(),strippedString.trim(),"Land/Estate Master entry proceed message does not match, actual : "+mGetPropertyFromFile("rnl_LEMEstProceedMsgData").trim()+"expected : "+strippedString.trim());
				mTakeScreenShot();
				mClick("id", mGetPropertyFromFile("rnl_LEMProceedBtnID"));
			}

			else if(mGetPropertyFromFile("rnl_LEMCreateGroup").equalsIgnoreCase("Yes") && mGetPropertyFromFile("rnl_LEMAddEstate").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_LEMEditEstate").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_LEMEditGroup").equalsIgnoreCase("No"))	
			{
				mGenericWait();
				String estateProceedMsg = mGetText("css",mGetPropertyFromFile("rnl_LEMProceedMsgID"));

				Pattern landOrEstateNo = Pattern.compile("(?<=Group Created successfully Group No is::)(.*)\\s*");
				Matcher matcher = landOrEstateNo.matcher(estateProceedMsg);
				if (matcher.find())
				{
					System.out.println(matcher.group(1));
					appNo=matcher.group(1).trim();
					EstateNo.add(appNo);  
				}

				System.out.println(" Land/Estate number::::"+appNo);		
				System.out.println("POP UP Msg is::::"+estateProceedMsg);

				mWaitForVisible("id", mGetPropertyFromFile("rnl_LEMProceedBtnID"));

				mGenericWait();		
				appSubmitMsg  =  mGetText("css", mGetPropertyFromFile("rnl_LEMProceedMsgID"));
				System.out.println("App submit msg"+appSubmitMsg);
				strippedString = appSubmitMsg.replace(appNo, "");
				System.out.println("Stripped msg:: "+strippedString);		
				mAssert(mGetPropertyFromFile("rnl_LEMGroupProceedMsgData").trim(),strippedString.trim(),"Land/Estate Master entry proceed message does not match, actual : "+mGetPropertyFromFile("rnl_LEMGroupProceedMsgData").trim()+"expected : "+strippedString.trim());
				mTakeScreenShot();
				mClick("id", mGetPropertyFromFile("rnl_LEMProceedBtnID"));
			}


			else if(mGetPropertyFromFile("rnl_LEMEditEstate").equalsIgnoreCase("Yes") && mGetPropertyFromFile("rnl_LEMEditGroup").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_LEMCreateGroup").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_LEMAddEstate").equalsIgnoreCase("No"))
			{    	
				mWaitForVisible("id", mGetPropertyFromFile("rnl_LEMProceedBtnID"));		
				mGenericWait();		
				appSubmitMsg  =  mGetText("css", mGetPropertyFromFile("rnl_LEMProceedMsgID"));
				System.out.println("App submit msg"+appSubmitMsg);			
				mAssert(appSubmitMsg,mGetPropertyFromFile("rnl_LEMEditEstProceedMsgData"),"Land/Estate Master entry proceed message does not match, actual : " + appSubmitMsg +"expected : " + mGetPropertyFromFile("rnl_LEMEditEstProceedMsgData"));
				mTakeScreenShot();
				mClick("id", mGetPropertyFromFile("rnl_LEMProceedBtnID"));    	
			}

			else if(mGetPropertyFromFile("rnl_LEMEditGroup").equalsIgnoreCase("Yes") && mGetPropertyFromFile("rnl_LEMEditEstate").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_LEMCreateGroup").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_LEMAddEstate").equalsIgnoreCase("No"))
			{
				mWaitForVisible("id", mGetPropertyFromFile("rnl_LEMProceedBtnID"));		
				mGenericWait();		
				appSubmitMsg  =  mGetText("css", mGetPropertyFromFile("rnl_LEMProceedMsgID"));
				System.out.println("App submit msg"+appSubmitMsg);			
				mAssert(appSubmitMsg,mGetPropertyFromFile("rnl_LEMEditGroupProceedMsgData"),"Land/Estate Master entry proceed message does not match, actual : " + appSubmitMsg +"expected : " + mGetPropertyFromFile("rnl_LEMEditGroupProceedMsgData"));
				mTakeScreenShot();
				mClick("id", mGetPropertyFromFile("rnl_LEMProceedBtnID"));      	
			}  

			else
			{
				System.out.println("No further action can be performed, check the landEstateProceed_Script");
			}

		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in landEstateProceed_Script");
		}

	}


	public void bookingOfEstate_Script() throws InterruptedException{

		try {				   
			LOIAPPLICABLE=false;
			//mNavigation("rnl_RentAndLeaseID", "rnl_BookingOfEstateID");

			Rnl_EstateDesciption.add(mGetPropertyFromFile("rnl_BookingOfEstateEstateNameData"));

			//mNavigation("rnl_RentAndLeaseNavigationID", "rnl_RentAndLeaseID1", "rnl_BookingOfEstateID");
			mNavigation("rnl_RentAndLeaseNavigationID","rnl_RentAndLeaseID", "rnl_BookingOfEstateID");

			RnlServiceName=mGetText("css", mGetPropertyFromFile("Servicenametextid"));
			System.out.println("Service name is" +RnlServiceName);
			ServiceNameRNL.add(RnlServiceName);
			System.out.println("Service name is" +ServiceNameRNL);

			mWaitForVisible("id",  mGetPropertyFromFile("rnl_BookingOfEstateTitleID"));
			mCustomWait(200); 
			mSelectDropDown("id", mGetPropertyFromFile("rnl_BookingOfEstateTitleID"),mGetPropertyFromFile("rnl_BookingOfEstateTitleData"));
			String rnl_BookingOfEstateTitle=mCaptureSelectedValue("id", mGetPropertyFromFile("rnl_BookingOfEstateTitleID"));
			Rnl_ApplicantTitle.add(rnl_BookingOfEstateTitle);
			mAssert(rnl_BookingOfEstateTitle, mGetPropertyFromFile("rnl_BookingOfEstateTitleData"), "Booking of Estate Applicant Title dose not match Actual::"+rnl_BookingOfEstateTitle+"Expected::"+mGetPropertyFromFile("rnl_BookingOfEstateTitleData"));
			//Rnl_ApplicantTitle.add(mGetPropertyFromFile("rnl_BookingOfEstateTitleData"));

			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("rnl_BookingOfEstateFnameID"),mGetPropertyFromFile("rnl_BookingOfEstateFnameData"));
			//Rnl_ApplicantFname.add(mGetPropertyFromFile("rnl_BookingOfEstateFnameData"));
			String rnl_BookingOfEstateFname=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateFnameID"), "value");
			Rnl_ApplicantFname.add(rnl_BookingOfEstateFname);
			mAssert(rnl_BookingOfEstateFname, mGetPropertyFromFile("rnl_BookingOfEstateFnameData"), "Booking of Estate Applicant First name dose not match Actual::"+rnl_BookingOfEstateFname+"Expected::"+mGetPropertyFromFile("rnl_BookingOfEstateFnameData"));

			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("rnl_BookingOfEstateMidnameID"),mGetPropertyFromFile("rnl_BookingOfEstateMnameData"));
			//Rnl_ApplicantMname.add(mGetPropertyFromFile("rnl_BookingOfEstateMnameData"));
			String rnl_BookingOfEstateMname=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateMidnameID"), "value");
			Rnl_ApplicantMname.add(rnl_BookingOfEstateMname);
			mAssert(rnl_BookingOfEstateMname, mGetPropertyFromFile("rnl_BookingOfEstateMnameData"), "Booking of Estate Applicant Middle name dose not match Actual::"+rnl_BookingOfEstateMname+"Expected::"+mGetPropertyFromFile("rnl_BookingOfEstateMnameData"));

			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("rnl_BookingOfEstateLnameID"),mGetPropertyFromFile("rnl_BookingOfEstateLnameData"));
			//Rnl_ApplicantLname.add(mGetPropertyFromFile("rnl_BookingOfEstateLnameData"));
			String rnl_BookingOfEstateLname=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateLnameID"), "value");
			Rnl_ApplicantLname.add(rnl_BookingOfEstateLname);
			mAssert(rnl_BookingOfEstateLname, mGetPropertyFromFile("rnl_BookingOfEstateLnameData"), "Booking of Estate Applicant Last name dose not match Actual::"+rnl_BookingOfEstateLname+"Expected::"+mGetPropertyFromFile("rnl_BookingOfEstateLnameData"));


			String Fname = mGetPropertyFromFile("rnl_BookingOfEstateFnameData");
			String Mname = mGetPropertyFromFile("rnl_BookingOfEstateMnameData");
			String Lname = mGetPropertyFromFile("rnl_BookingOfEstateLnameData");

			boeapplName=Fname+" "+Mname+" "+Lname;
			System.out.println("applName is : " + boeapplName); 
			Rnl_ApplicantFullname.add(boeapplName);

			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("rnl_BookingOfEstateMobNoID"),mGetPropertyFromFile("rnl_BookingOfEstateMobNoData"));
			String rnl_BookingOfEstateMobNo=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateMobNoID"), "value");
			Rnl_ApplicantMbNo.add(rnl_BookingOfEstateMobNo);
			mAssert(rnl_BookingOfEstateMobNo, mGetPropertyFromFile("rnl_BookingOfEstateMobNoData"), "Booking of Estate Applicant Mobile No dose not match Actual::"+rnl_BookingOfEstateMobNo+"Expected::"+mGetPropertyFromFile("rnl_BookingOfEstateMobNoData"));
			//Rnl_ApplicantMbNo.add(mGetPropertyFromFile("rnl_BookingOfEstateMobNoData"));

			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("rnl_BookingOfEstateEmailID"),mGetPropertyFromFile("rnl_BookingOfEstateEmailData"));
			String rnl_BookingOfEstateEmail=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateEmailID"), "value");
			Rnl_ApplicantEmail.add(rnl_BookingOfEstateEmail);
			mAssert(rnl_BookingOfEstateEmail, mGetPropertyFromFile("rnl_BookingOfEstateEmailData"), "Booking of Estate Applicant Email ID dose not match Actual::"+rnl_BookingOfEstateEmail+"Expected::"+mGetPropertyFromFile("rnl_BookingOfEstateEmailData"));

			mTakeScreenShot();

			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("rnl_BookingOfEstateCorrAddOneID"),mGetPropertyFromFile("rnl_BookingOfEstateCorrAddOneData"));
			String rnl_BookingOfEstateCorrAddOne=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateCorrAddOneID"), "value");
			Rnl_ApplicantAddOne.add(rnl_BookingOfEstateCorrAddOne);
			mAssert(rnl_BookingOfEstateCorrAddOne, mGetPropertyFromFile("rnl_BookingOfEstateCorrAddOneData"), "Booking of Estate Applicant Address Line 1 dose not match Actual::"+rnl_BookingOfEstateCorrAddOne+"Expected::"+mGetPropertyFromFile("rnl_BookingOfEstateCorrAddOneData"));

			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("rnl_BookingOfEstateCorrAddTwoID"),mGetPropertyFromFile("rnl_BookingOfEstateCorrAddTwoData"));
			mCustomWait(200);
			String rnl_BookingOfEstateCorrAddTwo=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateCorrAddTwoID"), "value");
			Rnl_ApplicantAddTwo.add(rnl_BookingOfEstateCorrAddTwo);
			mAssert(rnl_BookingOfEstateCorrAddTwo, mGetPropertyFromFile("rnl_BookingOfEstateCorrAddTwoData"), "Booking of Estate Applicant Address Line 2 dose not match Actual::"+rnl_BookingOfEstateCorrAddTwo+"Expected::"+mGetPropertyFromFile("rnl_BookingOfEstateCorrAddTwoData"));

			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("rnl_BookingOfEstatePincodeID"),mGetPropertyFromFile("rnl_BookingOfEstatePinCodeData"));
			String rnl_BookingOfEstatePincode=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstatePincodeID"), "value");
			Rnl_ApplicantPinCode.add(rnl_BookingOfEstatePincode);
			mAssert(rnl_BookingOfEstatePincode, mGetPropertyFromFile("rnl_BookingOfEstatePinCodeData"), "Booking of Estate Applicant Pincode dose not match Actual::"+rnl_BookingOfEstatePincode+"Expected::"+mGetPropertyFromFile("rnl_BookingOfEstatePinCodeData"));

			mTakeScreenShot();
			String BookingDate= mGetText("id",mGetPropertyFromFile("rnl_BookingOfEstateBookingDateID"),"value");
			Rnl_BookingtoDate.add(BookingDate);
			mAssert(BookingDate,strDate, "Booking of Estate Applicant Estate Booking Date dose not match Actual::"+BookingDate+"Expected::"+strDate);

			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("rnl_BookingOfEstateRemarksID"),mGetPropertyFromFile("rnl_BookingOfEstateRemarksData"));
			String rnl_BookingOfEstateRemarks=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateRemarksID"), "value");
			Rnl_ApplicantRemarks.add(rnl_BookingOfEstateRemarks);
			mAssert(rnl_BookingOfEstatePincode, mGetPropertyFromFile("rnl_BookingOfEstateRemarksData"), "Booking of Estate Applicant Remarks dose not match Actual::"+rnl_BookingOfEstatePincode+"Expected::"+mGetPropertyFromFile("rnl_BookingOfEstateRemarksData"));

			mCustomWait(500);
			mDateSelect("id",mGetPropertyFromFile("rnl_BookingOfEstateFromDateID"),mGetPropertyFromFile("rnl_BookingOfEstateFromDateData"));
			String BookingfrDate=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateFromDateID"));
			System.out.println("Booking date is " +BookingfrDate);
			//mAssert(BookingfrDate, mGetPropertyFromFile("rnl_BookingOfEstateFromDateData"), "Booking of Estate Applicant Estate Booking From date dose not match Actual::"+BookingfrDate+"Expected::"+mGetPropertyFromFile("rnl_BookingOfEstateFromDateData"));
			Rnl_BookingfromDate.add(mGetPropertyFromFile("rnl_BookingOfEstateFromDateData"));

			mCustomWait(500);
			mDateSelect("id",mGetPropertyFromFile("rnl_BookingOfEstateToDateID"),mGetPropertyFromFile("rnl_BookingOfEstateToDateData"));
			String BookingtoDate=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateFromDateID"), "value");
			mAssert(BookingtoDate, mGetPropertyFromFile("rnl_BookingOfEstateToDateData"), "Booking of Estate Applicant Estate Booking To date dose not match Actual::"+BookingtoDate+"Expected::"+mGetPropertyFromFile("rnl_BookingOfEstateToDateData"));
			Rnl_BookingtoDate.add(mGetPropertyFromFile("rnl_BookingOfEstateToDateData"));

			mTakeScreenShot();

			mWaitForVisible("linkText",mGetPropertyFromFile("rnl_BookingOfEstateAddDetID"));
			mCustomWait(1000);
			mClick("linkText", mGetPropertyFromFile("rnl_BookingOfEstateAddDetID"));

			if(mGetPropertyFromFile("rnl_BookingOfEstateEstateName").equalsIgnoreCase("yes")) 
			{
				mWaitForVisible("id",mGetPropertyFromFile("rnl_BookingOfEstateEstateNameID"));

				if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
				{

					mSelectAutoCompleteText("id",mGetPropertyFromFile("rnl_BookingOfEstateEstateNameID"),estatename.get(CurrentinvoCount).trim());
				}

				else
				{
					//	mSelectAutoCompleteText("id",mGetPropertyFromFile("rnl_BookingOfEstateEstateNameID"),mGetPropertyFromFile("rnl_BookingOfEstateEstateNameData"));
					mSendKeys("id",mGetPropertyFromFile("rnl_BookingOfEstateEstateNameID"),mGetPropertyFromFile("rnl_BookingOfEstateEstateNameData"));
					String EstateName=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateEstateNameID"), "value");
					Rnl_EstateName.add(EstateName);
					mGenericWait();
					mCustomWait(2000);
					driver.findElement(By.id(mGetPropertyFromFile("rnl_BookingOfEstateEstateNameID"))).sendKeys(Keys.DOWN);
					mGenericWait();
					driver.findElement(By.id(mGetPropertyFromFile("rnl_BookingOfEstateEstateNameID"))).sendKeys(Keys.ENTER);
					mCustomWait(1000);
				}

			}

			if(mGetPropertyFromFile("rnl_BookingOfEstateEstateAddress").equalsIgnoreCase("yes")) 
			{
				mSendKeys("id", mGetPropertyFromFile("rnl_BookingOfEstateEstateAddressID"),mGetPropertyFromFile("rnl_BookingOfEstateEstateAddressData"));
				String EstateAddress=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateEstateAddressID"), "value");
				Rnl_EstateAddress.add(EstateAddress);
				mCustomWait(1000);
			}

			mSelectDropDown("id", mGetPropertyFromFile("rnl_BookingOfEstateEstateTypeID"),mGetPropertyFromFile("rnl_BookingOfEstateEstateTypeData"));
			Rnl_EstateType.add(mGetPropertyFromFile("rnl_BookingOfEstateEstateTypeID"));
			mCustomWait(1000);

			mSelectDropDown("id", mGetPropertyFromFile("rnl_BookingOfEstateEstateID"),mGetPropertyFromFile("rnl_BookingOfEstateEstateData"));
			Rnl_EstateNo.add(mGetPropertyFromFile("rnl_BookingOfEstateEstateID"));

			mWaitForVisible("xpath",mGetPropertyFromFile("rnl_BookingOfEstateSearchBtnID"));
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("rnl_BookingOfEstateSearchBtnID"));

			mWaitForVisible("name",mGetPropertyFromFile("rnl_BookingOfEstateCheckBoxID"));
			mCustomWait(1000);
			mClick("name", mGetPropertyFromFile("rnl_BookingOfEstateCheckBoxID"));

			mscroll(0,800);
			mCustomWait(1000);

			// Fetching Land/Estate details from database
			try {

				try
				{
					Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
				}
				catch(ClassNotFoundException ex) {
					System.out.println("Error: unable to load driver class! " +ex);
					System.exit(1);
				}
				catch(IllegalAccessException ex) {
					System.out.println("Error: access problem while loading!");
					System.exit(2);
				}
				catch(InstantiationException ex) {
					System.out.println("Error: unable to instantiate driver!");
					System.exit(3);
				}

				//String URL = "jdbc:oracle:thin:@103.241.183.246:1521:rac";
				String URL = "jdbc:oracle:thin:@192.168.100.169:1521:ORCLUNI";
				String USER = "mainet_prod_phase2";
				String PASS = "mainet_prod_phase2";
				Connection conn = DriverManager.getConnection(URL, USER, PASS);

				Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


				if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
				{
					db_etateno= EstateNo.get(CurrentinvoCount);	
				}	
				else
				{
					db_etateno=mGetPropertyFromFile("applicationNo");
				}


				db_orgid=mGetPropertyFromFile("db_orgid");

				String query1 ="SELECT l.lem_estate_description,\r\n" + 
						"       l.lem_estate_no,\r\n" + 
						"       l.lem_address,\r\n" + 
						"       l.cod_id1_ety,a.cod_desc as Land_Estate_Type,\r\n" + 
						"       l.cod_id2_ety,b.cod_desc as Land_Estate_SubType\r\n" + 
						"FROM tb_le_estate_mas l\r\n" + 
						"INNER JOIN \r\n" + 
						"   tb_comparent_det a on l.cod_id1_ety=a.cod_id\r\n" + 
						"INNER JOIN \r\n" + 
						"   tb_comparent_det b on l.cod_id2_ety=b.cod_id\r\n" + 
						" where l.orgid = "+db_orgid + " and l.lem_estate_no= '"+db_etateno+"'";


				System.out.println("Query :: " + query1);

				rs1 = st.executeQuery(query1);			

				System.out.println();

				while (rs1.next()) {

					System.out.println(rs1.getString(1) +"    "+ rs1.getString(2)+"    "+ rs1.getString(3)+"    "+ rs1.getString(5)+"    "+ rs1.getString(7));
					db_landEstateName=rs1.getString(1);
					db_landEstateCode=rs1.getString(2);
					db_landEstateAddr=rs1.getString(3);
					db_landEstateType=rs1.getString(5);
					db_landEstateSubType=rs1.getString(7);
					System.out.println("DB landEstateName is : " +db_landEstateName);
					System.out.println("DB landEstateCode is : " +db_landEstateCode);
					System.out.println("DB landEstateAddr is : " +db_landEstateAddr);
					System.out.println("DB landEstateType is : " +db_landEstateType);
					System.out.println("DB landEstateSubType is : " +db_landEstateSubType);
				}

				conn.close();		

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			mGenericWait();


			// Capturing searched Land/Estate details
			WebElement table = driver.findElement(By.id("gridEstateBookingDetailRnL1"));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount = rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

			int Rowno = 0;
			for (int a=0;a<rwcount;a++) 
			{
				List<WebElement> cols = rows.get(a).findElements(By.xpath("td"));
				int colcount = cols.size();
				System.out.println("NUMBER OF COLUMNS IN THIS TABLE = " + colcount);
				for(int b=0;b<colcount;b++)
				{
					if(a>=1)
					{
						if(b==0)
						{
							selectedLandEstateName = rows.get(a).findElement(By.xpath("td[1]")).getText();
							System.out.println("Land/Estate Name is : "+selectedLandEstateName);
						}
						if(b==1)
						{
							selectedLandEstateCode = rows.get(a).findElement(By.xpath("td[2]")).getText();
							System.out.println("Land/Estate Code is : "+selectedLandEstateCode);
						}
						if(b==2)
						{
							selectedLandEstateAddr = rows.get(a).findElement(By.xpath("td[3]")).getText();
							System.out.println("Land Estate Address is : "+selectedLandEstateAddr);
						}
						if(b==3)
						{
							selectedLandEstateType = rows.get(a).findElement(By.xpath("td[4]")).getText();
							System.out.println("Land Estate Type is : "+selectedLandEstateType);
						}
						if(b==4)
						{
							selectedLandEstateSubType = rows.get(a).findElement(By.xpath("td[5]")).getText();
							System.out.println("Land Estate Sub Type is : "+selectedLandEstateSubType);
						}
						if(b==5)
						{
							selectedBookingAmt = rows.get(a).findElement(By.xpath("td[6]")).getText();
							System.out.println("Booking Amount is : "+selectedBookingAmt);
						}

					}
				}

			}


			mWaitForVisible("name",mGetPropertyFromFile("rnl_BookingoOfEstateEstateSearchID"));
			mCustomWait(1000);
			mClick("name", mGetPropertyFromFile("rnl_BookingoOfEstateEstateSearchID"));

			// Capturing added Land/Estate details
			WebElement table2 = driver.findElement(By.id("gridEstateBookingDetailRnL"));

			List<WebElement> rows2 = table2.findElements(By.tagName("tr"));
			int rwcount2 = rows2.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount2);

			int Rowno2 = 0;
			for (int a=0;a<rwcount2;a++) 
			{
				List<WebElement> cols2 = rows2.get(a).findElements(By.xpath("td"));
				int colcount2 = cols2.size();
				for(int b=0;b<colcount2;b++)
				{
					if(a>=1)
					{
						if(b==0)
						{
							String srNo = rows2.get(a).findElement(By.xpath("td[1]")).getText();
							System.out.println("Sr. No. is : "+srNo);
						}
						if(b==1)
						{
							addedLandEstateName = rows2.get(a).findElement(By.xpath("td[2]")).getText();
							System.out.println("Land/Estate Name is : "+addedLandEstateName);
						}
						if(b==2)
						{
							addedLandEstateCode = rows2.get(a).findElement(By.xpath("td[3]")).getText();
							System.out.println("Land/Estate Code is : "+addedLandEstateCode);
						}
						if(b==3)
						{
							addedLandEstateAddr = rows2.get(a).findElement(By.xpath("td[4]")).getText();
							System.out.println("Land Estate Address is : "+addedLandEstateAddr);
						}
						if(b==4)
						{
							addedLandEstateType = rows2.get(a).findElement(By.xpath("td[5]")).getText();
							System.out.println("Land Estate Type is : "+addedLandEstateType);
						}
						if(b==5)
						{
							addedLandEstateSubType = rows2.get(a).findElement(By.xpath("td[6]")).getText();
							System.out.println("Land Estate Sub Type is : "+addedLandEstateSubType);
						}
						if(b==6)
						{
							addedBookingAmt = rows2.get(a).findElement(By.xpath("td[7]")).getText();
							System.out.println("Booking Amount is : "+addedBookingAmt);
						}

					}
				}

			}


			mUpload("id",mGetPropertyFromFile("rnl_BookingOfEstateUploadID"),mGetPropertyFromFile("rnl_BookingOfEstateUploadData"));


			mUpload("id",mGetPropertyFromFile("rnl_BookingOfEstateUploadID1"),mGetPropertyFromFile("rnl_BookingOfEstateUploadData"));

			//docUpload("xpath",mGetPropertyFromFile("rnl_BookingOfEstateTableNameID"), "upldDocStatus");

			mGenericWait();
			mTakeScreenShot();
//code added by swapnil 30-11-2017
			if(!mGetPropertyFromFile("rnl_BOE_PaymentApplicable").equalsIgnoreCase("YES"))
			{	
				System.out.println("payment not applicable");
				mWaitForVisible("css",mGetPropertyFromFile("rnl_BookingOfEstateSubmitBtnID"));
				mCustomWait(1000);
				mClick("css",mGetPropertyFromFile("rnl_BookingOfEstateSubmitBtnID"));
			}else
			{
				System.out.println("payment applicable"); 
				payment("paymentMode","byBankOrULB");

				mWaitForVisible("css",mGetPropertyFromFile("rnl_BookingOfEstateSubmitBtnID"));
				mCustomWait(1000);
				mClick("css",mGetPropertyFromFile("rnl_BookingOfEstateSubmitBtnID"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in bookingOfEstate_Script");
		}

	}

	public void bookingOfEstateProceed(){
		try{
			String appNum =mGetApplicationNo("css",mGetPropertyFromFile("rnl_BookingOfEstateAppNoID"));

			System.out.println("POP UP msg at Booking of Estate is::::"+appNum);

			String numberOnly= appNum.replaceAll("[^0-9]", "");			
			mAppNoArray(numberOnly);
			//appNo = numberOnly;

			mCustomWait(1000);
			System.out.println("application Number in proceed ::::"+appNo);
			ApplicationNoRNL.add(appNo);
			mWaitForVisible("id", mGetPropertyFromFile("rnl_BookingOfEstateProceedID"));
			mTakeScreenShot();
			mCustomWait(1000);
			appSubmitMsg = mGetText("css", mGetPropertyFromFile("rnl_BookingOfEstateAppNoID"));
			System.out.println("App submit msg"+appSubmitMsg);
			strippedString = appSubmitMsg.replace(appNo, "");
			System.out.println("Stripped msg"+strippedString);
			mAssert(mGetPropertyFromFile("rnl_BookingOfEstateActualMsg"),strippedString,"Booking of Estate proceed message does not match, actual : "+mGetPropertyFromFile("rnl_BookingOfEstateActualMsg")+"expected : "+strippedString);

			mClick("id", mGetPropertyFromFile("rnl_BookingOfEstateProceedID"));
		//	if(mGetPropertyFromFile("municipality").equalsIgnoreCase("Khagaul Nagar Parishad"))  
			if(!mGetPropertyFromFile("rnl_BOE_PaymentApplicable").equalsIgnoreCase("YES")) 
			{
				System.out.println("name of ulb is : "+mGetPropertyFromFile("municipality"));	 
			}
			else
			{ 
				mCustomWait(2000);			
 				mChallanPDFReader();
			}

			//			RNLBOEServices = true;
		}
		catch(Exception e )
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in bookingOfEstateProceed script");
		}	
	}



	public void tenantMaster_script() {

		try {

			mGenericWait();
			mNavigation("rnl_RentAndLeaseID","rnl_MasterID","rnl_TenantMasterID");

			mWaitForVisible("css", mGetPropertyFromFile("rnl_TMAddTenantBtnID"));

			// to add tenant 

			if(mGetPropertyFromFile("rnl_TMAddTenant").equalsIgnoreCase("Yes") && mGetPropertyFromFile("rnl_TMEditTenant").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_TMViewTenant").equalsIgnoreCase("No"))	
			{				
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_TMAddTenantBtnID"));

				mWaitForVisible("id",  mGetPropertyFromFile("rnl_TMRegistrationDateID"));
				mDateSelect("id",mGetPropertyFromFile("rnl_TMRegistrationDateID"), mGetPropertyFromFile("rnl_TMRegistrationDateData")); 
				String rnl_TMRegistrationDate=mGetText("id",mGetPropertyFromFile("rnl_TMRegistrationDateID"), "value");
				mAssert(rnl_TMRegistrationDate, mGetPropertyFromFile("rnl_TMRegistrationDateData"), "Tenant Master Form: Registration date dose not match Actual::"+rnl_TMRegistrationDate+"Expected::"+mGetPropertyFromFile("rnl_TMRegistrationDateData"));
				TM_RegDate.add(rnl_TMRegistrationDate);
				System.out.println("Tenant Master Form: Registration date dose not match Actual::"+rnl_TMRegistrationDate);

				mCustomWait(500);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_TMTenantTitleID"),mGetPropertyFromFile("rnl_TMTenantTitleData"));
				String rnl_TMTenantTitle=mCaptureSelectedValue("id", mGetPropertyFromFile("rnl_TMTenantTitleID"));
				mAssert(rnl_TMTenantTitle, mGetPropertyFromFile("rnl_TMTenantTitleData"), "Tenant Master Form: Tenant Title dose not match Actual::"+rnl_TMTenantTitle+"Expected::"+mGetPropertyFromFile("rnl_TMTenantTitleData"));	
				TM_TenantTitle.add(rnl_TMTenantTitle);
				System.out.println("Tenant Master Form: Tenant Title :::"+rnl_TMTenantTitle);

				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMTenantNameID"),mGetPropertyFromFile("rnl_TMTenantNameData"));
				String rnl_TMTenantName=mGetText("id", mGetPropertyFromFile("rnl_TMTenantNameID"),"value");
				mAssert(rnl_TMTenantName, mGetPropertyFromFile("rnl_TMTenantTitleData"), "Tenant Master Form: Tenant name dose not match Actual::"+rnl_TMTenantName+"Expected::"+mGetPropertyFromFile("rnl_TMTenantNameData"));	
				System.out.println("Tenant Master Form: Tenant Name :::"+rnl_TMTenantName);
				TM_TenantName.add(rnl_TMTenantName);

				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMTenantAddressID"),mGetPropertyFromFile("rnl_TMTenantAddressData"));
				String rnl_TMTenantAddress=mGetText("id", mGetPropertyFromFile("rnl_TMTenantAddressID"),"value");
				mAssert(rnl_TMTenantAddress, mGetPropertyFromFile("rnl_TMTenantAddressData"), "Tenant Master Form: Tenant Address dose not match Actual::"+rnl_TMTenantAddress+"Expected::"+mGetPropertyFromFile("rnl_TMTenantAddressData"));	
				System.out.println("Tenant Master Form: Tenant Address :::"+rnl_TMTenantAddress);
				TM_TenantAddress.add(rnl_TMTenantAddress);
				mTakeScreenShot(); 
				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMTenantContactNoID"),mGetPropertyFromFile("rnl_TMTenantContactNoData"));
				String rnl_TMTenantContactNo=mGetText("id", mGetPropertyFromFile("rnl_TMTenantContactNoID"),"value");
				mAssert(rnl_TMTenantContactNo, mGetPropertyFromFile("rnl_TMTenantContactNoData"), "Tenant Master Form: Tenant Contact No dose not match Actual::"+rnl_TMTenantContactNo+"Expected::"+mGetPropertyFromFile("rnl_TMTenantContactNoData"));	
				System.out.println("Tenant Master Form: Tenant Contact No :::"+rnl_TMTenantContactNo);
				TM_TenantContactNo.add(rnl_TMTenantContactNo);

				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMTenantMobileNoID"),mGetPropertyFromFile("rnl_TMTenantMobileNoData"));
				String rnl_TMTenantMobileNo=mGetText("id", mGetPropertyFromFile("rnl_TMTenantMobileNoID"),"value");
				mAssert(rnl_TMTenantMobileNo, mGetPropertyFromFile("rnl_TMTenantMobileNoData"), "Tenant Master Form: Tenant Mobile No dose not match Actual::"+rnl_TMTenantMobileNo+"Expected::"+mGetPropertyFromFile("rnl_TMTenantMobileNoData"));	
				System.out.println("Tenant Master Form: Tenant Mobile No :::"+rnl_TMTenantContactNo);
				TM_TenantMobileNo.add(rnl_TMTenantMobileNo);

				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMTenantMailID"),mGetPropertyFromFile("rnl_TMTenantMailData"));
				String rnl_TMTenantMail=mGetText("id", mGetPropertyFromFile("rnl_TMTenantMailID"),"value");
				mAssert(rnl_TMTenantMail, mGetPropertyFromFile("rnl_TMTenantMailData"), "Tenant Master Form: Tenant Email dose not match Actual::"+rnl_TMTenantMail+"Expected::"+mGetPropertyFromFile("rnl_TMTenantMailData"));	
				System.out.println("Tenant Master Form: Tenant Email :::"+rnl_TMTenantMail);
				TM_TenantMail.add(rnl_TMTenantMail);

				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMTenantPanNoID"),mGetPropertyFromFile("rnl_TMTenantPanNoData"));
				String rnl_TMTenantPanNo=mGetText("id", mGetPropertyFromFile("rnl_TMTenantPanNoID"),"value");
				mAssert(rnl_TMTenantPanNo, mGetPropertyFromFile("rnl_TMTenantPanNoData"), "Tenant Master Form: Tenant Pan No dose not match Actual::"+rnl_TMTenantPanNo+"Expected::"+mGetPropertyFromFile("rnl_TMTenantPanNoData"));	
				System.out.println("Tenant Master Form: Tenant Pan No :::"+rnl_TMTenantPanNo);
				TM_TenantPanNo.add(rnl_TMTenantPanNo);

				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMTenantAadharNoID"),mGetPropertyFromFile("rnl_TMTenantAadharNoData"));
				String rnl_TMTenantAadharNo=mGetText("id", mGetPropertyFromFile("rnl_TMTenantAadharNoID"),"value");
				mAssert(rnl_TMTenantAadharNo, mGetPropertyFromFile("rnl_TMTenantAadharNoData"), "Tenant Master Form: Tenant Aadhar No dose not match Actual::"+rnl_TMTenantAadharNo+"Expected::"+mGetPropertyFromFile("rnl_TMTenantAadharNoData"));	
				System.out.println("Tenant Master Form: Tenant Aadhar No :::"+rnl_TMTenantAadharNo);
				TM_TenantAadharNo.add(mGetPropertyFromFile("rnl_TMTenantAadharNoData"));
				mCustomWait(1000);

				mTakeScreenShot(); 

				mGenericWait();
				mClick("linkText", mGetPropertyFromFile("rnl_TMAddAdd'nalTenantBtnID"));

				mCustomWait(500);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_TMOwnerTitleID"),mGetPropertyFromFile("rnl_TMOwnerTitleData"));
				String rnl_TMOwnerTitle=mCaptureSelectedValue("id", mGetPropertyFromFile("rnl_TMOwnerTitleID"));
				mAssert(rnl_TMOwnerTitle, mGetPropertyFromFile("rnl_TMOwnerTitleData"), "Tenant Master Form: Additional Tenant Title dose not match Actual::"+rnl_TMOwnerTitle+"Expected::"+mGetPropertyFromFile("rnl_TMOwnerTitleData"));	
				System.out.println("Tenant Master Form:  Additional Tenant Title  :::"+rnl_TMOwnerTitle);
				TM_AddTenantTitle.add(rnl_TMOwnerTitle);

				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMOwnerNameID"),mGetPropertyFromFile("rnl_TMOwnerNameData"));
				String rnl_TMOwnerName=mGetText("id", mGetPropertyFromFile("rnl_TMOwnerNameID"),"value");
				mAssert(rnl_TMOwnerTitle, mGetPropertyFromFile("rnl_TMOwnerNameData"), "Tenant Master Form: Additional Tenant Name dose not match Actual::"+rnl_TMOwnerTitle+"Expected::"+mGetPropertyFromFile("rnl_TMOwnerNameData"));	
				System.out.println("Tenant Master Form:  Additional Tenant Name  :::"+rnl_TMOwnerName);
				TM_AddTenantName.add(rnl_TMOwnerName);

				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMOwnerAddressID"),mGetPropertyFromFile("rnl_TMOwnerAddressData"));
				String rnl_TMOwnerAddress=mGetText("id", mGetPropertyFromFile("rnl_TMOwnerAddressID"),"value");
				mAssert(rnl_TMOwnerAddress, mGetPropertyFromFile("rnl_TMOwnerAddressData"), "Tenant Master Form: Additional Tenant Address dose not match Actual::"+rnl_TMOwnerAddress+"Expected::"+mGetPropertyFromFile("rnl_TMOwnerAddressData"));	
				System.out.println("Tenant Master Form:  Additional Tenant Address  :::"+rnl_TMOwnerAddress);
				TM_AddTenantAddress.add(rnl_TMOwnerAddress);

				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMOwnerContactNoID"),mGetPropertyFromFile("rnl_TMOwnerContactNoData"));
				String rnl_TMOwnerContactNo=mGetText("id", mGetPropertyFromFile("rnl_TMOwnerContactNoID"),"value");
				mAssert(rnl_TMOwnerContactNo, mGetPropertyFromFile("rnl_TMOwnerContactNoData"), "Tenant Master Form: Additional Tenant Contact No dose not match Actual::"+rnl_TMOwnerContactNo+"Expected::"+mGetPropertyFromFile("rnl_TMOwnerContactNoData"));	
				System.out.println("Tenant Master Form:  Additional Tenant Contact No  :::"+rnl_TMOwnerContactNo);
				TM_AddTenantContactNo.add(rnl_TMOwnerContactNo);

				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMOwnerMobileNoID"),mGetPropertyFromFile("rnl_TMOwnerMobileNoData"));
				String rnl_TMOwnerMobileNo=mGetText("id", mGetPropertyFromFile("rnl_TMOwnerMobileNoID"),"value");
				mAssert(rnl_TMOwnerMobileNo, mGetPropertyFromFile("rnl_TMOwnerMobileNoData"), "Tenant Master Form: Additional Tenant Mobile No not match Actual::"+rnl_TMOwnerAddress+"Expected::"+mGetPropertyFromFile("rnl_TMOwnerMobileNoData"));	
				System.out.println("Tenant Master Form:  Additional Tenant Mobile No  :::"+rnl_TMOwnerMobileNo);
				TM_AddTenantMobileNo.add(rnl_TMOwnerMobileNo);

				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMOwnerMailID"),mGetPropertyFromFile("rnl_TMOwnerMailData"));
				String rnl_TMOwnerMail=mGetText("id", mGetPropertyFromFile("rnl_TMOwnerMailID"),"value");
				mAssert(rnl_TMOwnerMail, mGetPropertyFromFile("rnl_TMOwnerMailData"), "Tenant Master Form: Additional Tenant Email not match Actual::"+rnl_TMOwnerMail+"Expected::"+mGetPropertyFromFile("rnl_TMOwnerMailData"));	
				System.out.println("Tenant Master Form:  Additional Tenant Email  :::"+rnl_TMOwnerMail);
				TM_AddTenantMail.add(rnl_TMOwnerMail);

				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMOwnerPanNoID"),mGetPropertyFromFile("rnl_TMOwnerPanNoData"));
				String rnl_TMOwnerPanNo=mGetText("id", mGetPropertyFromFile("rnl_TMOwnerPanNoID"),"value");
				mAssert(rnl_TMOwnerPanNo, mGetPropertyFromFile("rnl_TMOwnerPanNoData"), "Tenant Master Form: Additional Tenant PAN No not match Actual::"+rnl_TMOwnerMail+"Expected::"+mGetPropertyFromFile("rnl_TMOwnerPanNoData"));	
				System.out.println("Tenant Master Form:  Additional Tenant PAN No  :::"+rnl_TMOwnerPanNo);
				TM_AddTenantPanNo.add(rnl_TMOwnerPanNo);

				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMOwnerAadharNoID"),mGetPropertyFromFile("rnl_TMOwnerAadharNoData"));
				String rnl_TMOwnerAadharNo=mGetText("id", mGetPropertyFromFile("rnl_TMOwnerPanNoID"),"value");
				mAssert(rnl_TMOwnerAadharNo, mGetPropertyFromFile("rnl_TMOwnerAadharNoData"), "Tenant Master Form: Additional Tenant Addhar No not match Actual::"+rnl_TMOwnerAadharNo+"Expected::"+mGetPropertyFromFile("rnl_TMOwnerAadharNoData"));	
				System.out.println("Tenant Master Form:  Additional Tenant Addhar No  :::"+rnl_TMOwnerAadharNo);
				TM_AddTenantAadharNo.add(rnl_TMOwnerAadharNo);

				mTakeScreenShot();

				mCustomWait(750);
				mClick("css", mGetPropertyFromFile("rnl_TMTenantDetailsSubmitID"));
				mTakeScreenShot();
				mWaitForVisible("css",  mGetPropertyFromFile("rnl_TMTenantMasterSubmitID"));

				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_TMTenantMasterSubmitID"));

				/*mWaitForVisible("css",  mGetPropertyFromFile("rnl_TMTenantMasterProceedBtnID"));
				mCustomWait(1000);
				mClick("css",  mGetPropertyFromFile("rnl_TMTenantMasterProceedBtnID"));*/
			}


			// to edit tenant master

			else if(mGetPropertyFromFile("rnl_TMEditTenant").equalsIgnoreCase("Yes") && mGetPropertyFromFile("rnl_TMAddTenant").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_TMViewTenant").equalsIgnoreCase("No"))	
			{
				mCustomWait(1500);
				if(mGetPropertyFromFile("rnl_TMSearchByTenantNo").equalsIgnoreCase("Yes"))
				{
					mSendKeys("id", mGetPropertyFromFile("rnl_TMTenantNoSrchID"),mGetPropertyFromFile("rnl_TMTenantNo"));
					TM_TenantNo.add(mGetPropertyFromFile("rnl_TMTenantNo"));
				}

				if(mGetPropertyFromFile("rnl_TMSearchByTenantName").equalsIgnoreCase("Yes"))
				{
					mSendKeys("css", mGetPropertyFromFile("rnl_TMTenantNameSrchID"),mGetPropertyFromFile("rnl_TMTenantName"));
					TM_TenantName.add(mGetPropertyFromFile("rnl_TMTenantName"));
				}

				mCustomWait(1000);
				mTakeScreenShot();
				mClick("css", mGetPropertyFromFile("rnl_TMTenantMasSrchBtnID"));

				mWaitForVisible("xpath",  mGetPropertyFromFile("rnl_TMTenantEditBtnID"));
				mCustomWait(1000);
				mTakeScreenShot();
				mClick("xpath", mGetPropertyFromFile("rnl_TMTenantEditBtnID"));

				mWaitForVisible("id",  mGetPropertyFromFile("rnl_TMRegistrationDateID"));
				mCustomWait(500);
				mDateSelect("id",mGetPropertyFromFile("rnl_TMRegistrationDateID"), mGetPropertyFromFile("rnl_TMRegistrationDateData")); 
				String rnl_TMRegistrationDate=mGetText("id",mGetPropertyFromFile("rnl_TMRegistrationDateID"), "value");
				mAssert(rnl_TMRegistrationDate, mGetPropertyFromFile("rnl_TMRegistrationDateData"), "Tenant Master Form: Registration date dose not match Actual::"+rnl_TMRegistrationDate+"Expected::"+mGetPropertyFromFile("rnl_TMRegistrationDateData"));
				TM_RegDate.add(rnl_TMRegistrationDate);
				System.out.println("Tenant Master Form: Registration date dose not match Actual::"+rnl_TMRegistrationDate);



				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_TMTenantTitleID"),mGetPropertyFromFile("rnl_TMTenantTitleData"));
				String rnl_TMTenantTitle=mCaptureSelectedValue("id", mGetPropertyFromFile("rnl_TMTenantTitleID"));
				mAssert(rnl_TMTenantTitle, mGetPropertyFromFile("rnl_TMTenantTitleData"), "Tenant Master Form: Tenant Title dose not match Actual::"+rnl_TMTenantTitle+"Expected::"+mGetPropertyFromFile("rnl_TMTenantTitleData"));	
				TM_TenantTitle.add(rnl_TMTenantTitle);
				System.out.println("Tenant Master Form: Tenant Title :::"+rnl_TMTenantTitle);

				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMTenantNameID"),mGetPropertyFromFile("rnl_TMTenantNameData"));
				String rnl_TMTenantName=mGetText("id", mGetPropertyFromFile("rnl_TMTenantNameID"),"value");
				mAssert(rnl_TMTenantName, mGetPropertyFromFile("rnl_TMTenantTitleData"), "Tenant Master Form: Tenant name dose not match Actual::"+rnl_TMTenantName+"Expected::"+mGetPropertyFromFile("rnl_TMTenantNameData"));	
				System.out.println("Tenant Master Form: Tenant Name :::"+rnl_TMTenantName);
				TM_TenantName.add(rnl_TMTenantName);

				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMTenantAddressID"),mGetPropertyFromFile("rnl_TMTenantAddressData"));
				String rnl_TMTenantAddress=mGetText("id", mGetPropertyFromFile("rnl_TMTenantAddressID"),"value");
				mAssert(rnl_TMTenantAddress, mGetPropertyFromFile("rnl_TMTenantAddressData"), "Tenant Master Form: Tenant Address dose not match Actual::"+rnl_TMTenantAddress+"Expected::"+mGetPropertyFromFile("rnl_TMTenantAddressData"));	
				System.out.println("Tenant Master Form: Tenant Address :::"+rnl_TMTenantAddress);
				TM_TenantAddress.add(rnl_TMTenantAddress);

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMTenantContactNoID"),mGetPropertyFromFile("rnl_TMTenantContactNoData"));
				String rnl_TMTenantContactNo=mGetText("id", mGetPropertyFromFile("rnl_TMTenantContactNoID"),"value");
				mAssert(rnl_TMTenantContactNo, mGetPropertyFromFile("rnl_TMTenantContactNoData"), "Tenant Master Form: Tenant Contact No dose not match Actual::"+rnl_TMTenantContactNo+"Expected::"+mGetPropertyFromFile("rnl_TMTenantContactNoData"));	
				System.out.println("Tenant Master Form: Tenant Contact No :::"+rnl_TMTenantContactNo);
				TM_TenantContactNo.add(rnl_TMTenantContactNo);


				mCustomWait(1000);
				mSendKeys("css", mGetPropertyFromFile("rnl_TMTenantPanNoID"),mGetPropertyFromFile("rnl_TMTenantPanNoData"));
				String rnl_TMTenantPanNo=mGetText("id", mGetPropertyFromFile("rnl_TMTenantPanNoID"));
				mAssert(rnl_TMTenantPanNo, mGetPropertyFromFile("rnl_TMTenantPanNoData"), "Tenant Master Form: Tenant Pan No dose not match Actual::"+rnl_TMTenantPanNo+"Expected::"+mGetPropertyFromFile("rnl_TMTenantPanNoData"));	
				System.out.println("Tenant Master Form: Tenant Pan No :::"+rnl_TMTenantPanNo);
				TM_TenantPanNo.add(rnl_TMTenantPanNo);

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMTenantAadharNoID"),mGetPropertyFromFile("rnl_TMTenantAadharNoData"));
				String rnl_TMTenantAadharNo=mGetText("id", mGetPropertyFromFile("rnl_TMTenantAadharNoID"),"value");
				mAssert(rnl_TMTenantAadharNo, mGetPropertyFromFile("rnl_TMTenantAadharNoData"), "Tenant Master Form: Tenant Aadhar No dose not match Actual::"+rnl_TMTenantAadharNo+"Expected::"+mGetPropertyFromFile("rnl_TMTenantAadharNoData"));	
				System.out.println("Tenant Master Form: Tenant Aadhar No :::"+rnl_TMTenantAadharNo);
				TM_TenantAadharNo.add(mGetPropertyFromFile("rnl_TMTenantAadharNoData"));
				mCustomWait(1000);

				mTakeScreenShot();

				ClubbedUtils.rnl_mDynamicEditRowObjects(mGetPropertyFromFile("rnl_TMAdd'nalTenantTableID"));

				mWaitForVisible("css",  mGetPropertyFromFile("rnl_TMTenantMasterSubmitID"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_TMTenantMasterSubmitID"));
				mCustomWait(1000);

				mWaitForVisible("css",  mGetPropertyFromFile("rnl_TMTenantMasterProceedBtnID"));
				mCustomWait(1000);
				mClick("css",  mGetPropertyFromFile("rnl_TMTenantMasterProceedBtnID"));
			}

			else if(mGetPropertyFromFile("rnl_TMViewTenant").equalsIgnoreCase("Yes") && mGetPropertyFromFile("rnl_TMAddTenant").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_TMEditTenant").equalsIgnoreCase("No"))	
			{
				mCustomWait(1500);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMTenantNoSrchID"),mGetPropertyFromFile("rnl_TMTenantNo"));
				TM_TenantNo.add(mGetPropertyFromFile("rnl_TMTenantNo"));

				mCustomWait(1000);
				mSendKeys("css", mGetPropertyFromFile("rnl_TMTenantNameSrchID"),mGetPropertyFromFile("rnl_TMTenantNameData"));
				TM_TenantName.add(mGetPropertyFromFile("rnl_TMTenantNameData"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_TMTenantMasSrchBtnID"));

				mWaitForVisible("xpath",  mGetPropertyFromFile("rnl_TMTenantViewBtnID"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("rnl_TMTenantViewBtnID"));

				mWaitForVisible("id",  mGetPropertyFromFile("rnl_TMTenantContactNoID"));
				mCustomWait(1000);
				mTakeScreenShot();

				mscroll(0,700);	
				mCustomWait(1000);
				mTakeScreenShot();
			}						
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in tenantMaster_script");

		}	
	}


	public void tenantMasterProceed_Script() throws IOException{

		try {

			if(mGetPropertyFromFile("rnl_TMAddTenant").equalsIgnoreCase("Yes") && mGetPropertyFromFile("rnl_TMEditTenant").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_TMViewTenant").equalsIgnoreCase("No"))
			{
				mGenericWait();
				tenantno = mGetText("css",mGetPropertyFromFile("rnl_TMProceedMsgTenNoID"));
				TenantNo.add(tenantno);
				TM_TenantNo.add(tenantno);
				//TenantNo.add(appNo);  

				System.out.println("Land/Estate number::::"+tenantno);		

				mWaitForVisible("css", mGetPropertyFromFile("rnl_TMTenantMasterProceedBtnID"));

				mGenericWait();		
				appSubmitMsg  =  mGetText("css", mGetPropertyFromFile("rnl_TMProceedMsgID"));
				System.out.println("App submit msg"+appSubmitMsg);
				strippedString = appSubmitMsg.replace(tenantno, "");
				System.out.println("Stripped msg:: "+strippedString);		
				mAssert(mGetPropertyFromFile("rnl_TMProceedMsgData").trim(),strippedString.trim(),"Land/Estate Master entry proceed message does not match, actual : "+mGetPropertyFromFile("rnl_TMProceedMsgData").trim()+"expected : "+strippedString.trim());
				mTakeScreenShot();
				mClick("css", mGetPropertyFromFile("rnl_TMTenantMasterProceedBtnID"));	
			}

			else if(mGetPropertyFromFile("rnl_TMEditTenant").equalsIgnoreCase("Yes") && mGetPropertyFromFile("rnl_TMAddTenant").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_TMViewTenant").equalsIgnoreCase("No"))	
			{
				mWaitForVisible("css", mGetPropertyFromFile("rnl_TMTenantMasterProceedBtnID"));		
				mGenericWait();		
				appSubmitMsg  =  mGetText("css", mGetPropertyFromFile("rnl_TMProceedMsgID"));
				System.out.println("App submit msg"+appSubmitMsg);			
				mAssert(appSubmitMsg,mGetPropertyFromFile("rnl_LEMEditEstProceedMsgData"),"Land/Estate Master entry proceed message does not match, actual : " + appSubmitMsg +"expected : " + mGetPropertyFromFile("rnl_LEMEditEstProceedMsgData"));
				mTakeScreenShot();
				mClick("css", mGetPropertyFromFile("rnl_TMTenantMasterProceedBtnID"));  
			}

			else if(mGetPropertyFromFile("rnl_TMViewTenant").equalsIgnoreCase("Yes") && mGetPropertyFromFile("rnl_TMAddTenant").equalsIgnoreCase("No") && mGetPropertyFromFile("rnl_TMEditTenant").equalsIgnoreCase("No"))
			{
				System.out.println("the tenant master viewing is done");
			}

			else
			{
				System.out.println("No further action can be performed, check the tenantMasterProceed_Script");
			}

		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in tenantMasterProceed_Script");
		}

	}



	public void bookingOfEstateApprovalLetter_script(){
		try{
			mGenericWait();
			mNavigation("rnl_RentAndLeaseID","rnl_ReportsID","rnl_ApprovalLetterID");
			mGenericWait();

			if(RnlServiceName.equalsIgnoreCase("Booking of Land/Estate"))
			{

				//mWaitForVisible("id",  mGetPropertyFromFile("rnl_ApprRadioAppNoID"));
				mClick("id",  mGetPropertyFromFile("rnl_ApprRadioAppNoID"));
				if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
				{
					mSendKeys("id", mGetPropertyFromFile("rnl_ApprApplicationNoID"), applicationNo.get(CurrentinvoCount));
				}
				else
				{
					mSendKeys("id", mGetPropertyFromFile("rnl_ApprApplicationNoID"), mGetPropertyFromFile("applicationNo"));
				}

			}
			else
			{
				mWaitForVisible("id",  mGetPropertyFromFile("rnl_ApprradioContractNoID"));
				mClick("id",  mGetPropertyFromFile("rnl_ApprradioContractNoID"));
				mSendKeys("id", mGetPropertyFromFile("rnl_ApprContractNoID"),RNLContractNo.get(CurrentinvoCount));


			}
			mGenericWait();
			mTakeScreenShot();
			mClick("css",mGetPropertyFromFile("rnl_ApprPrintButtonID"));
			mChallanPDFReader();
			api.PdfPatterns.RNL_ApprovalLetter(pdfoutput);

		}
		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in bookingOfEstateApprovalLetter_script");

		}	
	}	

	// done by hiren kathiria on 29/08/2016			

	public void landAndEstateMasterEntrySearchCriteria(){

		try{

			mCustomWait(1000);

			if(mGetPropertyFromFile("rnl_LEMSearchByType").equalsIgnoreCase("yes"))
			{
				mCustomWait(2000);
				mSelectDropDown("xpath", mGetPropertyFromFile("rnl_LEMSearchByTypeID"),mGetPropertyFromFile("rnl_LEMSearchByTypeData"));
			}

			if(mGetPropertyFromFile("rnl_LEMSearchByWard").equalsIgnoreCase("yes"))
			{
				mCustomWait(2000);
				mSelectDropDown("xpath", mGetPropertyFromFile("rnl_LEMSearchByWardID"),mGetPropertyFromFile("rnl_LEMSearchByWardData"));
			}		

			if(mGetPropertyFromFile("rnl_LEMSearchByEstateNo").equalsIgnoreCase("yes"))
			{
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMSearchByEstateNoID"),mGetPropertyFromFile("rnl_LEMEstateNoSearchData"));
			}

			if(mGetPropertyFromFile("rnl_LEMSearchByGroupName").equalsIgnoreCase("yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMSearchByGroupNameID"),mGetPropertyFromFile("rnl_LEMSearchByGroupNameData"));
			}

			if(mGetPropertyFromFile("rnl_LEMSearchByEstateName").equalsIgnoreCase("yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("rnl_LEMSearchByEstateNameID"),mGetPropertyFromFile("rnl_LEMSearchByEstateNameData"));
			}

			mTakeScreenShot();
			mCustomWait(2000);			
			mClick("css", mGetPropertyFromFile("rnl_LEMSearchBtnID"));				
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in landAndEstateMasterEntrySearchCriteria");
		}		
	}

	// done by hiren kathiria on 29/08/2016			

	public void tenantContractSearchCriteria(){
		try{

			mCustomWait(1000);
			if(mGetPropertyFromFile("rnl_TgSearchTenantContractNo").equalsIgnoreCase("Yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("rnl_TgSearchTenantContractNoid"), mGetPropertyFromFile("rnl_TgSearchTenantContractNoData"));
			}				

			if(mGetPropertyFromFile("rnl_TgSearchTenantNo").equalsIgnoreCase("Yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("rnl_TgSearchTenantNoid"), mGetPropertyFromFile("rnl_TgSearchTenantNoData"));
			}				

			if(mGetPropertyFromFile("rnl_TgSearchTenantName").equalsIgnoreCase("Yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("rnl_TgSearchTenantNameid"), mGetPropertyFromFile("rnl_TgSearchTenantNameData"));
			}

			//Search Tenant contract
			mWaitForVisible("css", mGetPropertyFromFile("rnl_TgSearchTenantContractSrchBtnid"));
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("rnl_TgSearchTenantContractSrchBtnid"));		
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in tenantContractSearchCriteria");
		}		
	}

	// done by hiren kathiria on 30/08/2016			

	public void billPrintingSearchCriteria(){
		try{


			if(mGetPropertyFromFile("rnl_BprContract").equalsIgnoreCase("yes"))
			{
				if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent")){

					mSendKeys("id", mGetPropertyFromFile("rnl_BprContractid"), TCcontractNo.get(CurrentinvoCount)); 
				}
				else{
					IndOrDep("id", "rnl_BprContractid", "applicationNo"); 
				}
			}
			if(mGetPropertyFromFile("rnl_BprTenantNo").equalsIgnoreCase("yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("rnl_BprTenantNoid"), mGetPropertyFromFile("rnl_BprTenantNoData")); 
			}

			if(mGetPropertyFromFile("rnl_BprTenantName").equalsIgnoreCase("yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("rnl_BprTenantNameid"), mGetPropertyFromFile("rnl_BprTenantNameData"));
			}

			if(mGetPropertyFromFile("rnl_BprFrom&ToDate").equalsIgnoreCase("yes"))
			{
				mDateSelect("id", mGetPropertyFromFile("rnl_BprFromDateid"), mGetPropertyFromFile("rnl_BprFromDateData"));
				mCustomWait(1000);
				mDateSelect("id", mGetPropertyFromFile("rnl_BprToDateid"), mGetPropertyFromFile("rnl_BprToDateData"));
				mCustomWait(1000);
			}

			mTakeScreenShot();
			mCustomWait(1000);

			//search Button
			mWaitForVisible("css", mGetPropertyFromFile("rnl_BprContractSearchBtnid"));
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("rnl_BprContractSearchBtnid"));
			mCustomWait(1000);
			pageNo=mGetText("id", mGetPropertyFromFile("rnl_BprGridTablePageCountid")); 
			System.out.println("total no of page is : "+pageNo); 
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in billPrintingSearchCriteria");
		}			
	}

	// done by hiren kathiria on 30/08/2016			

	public void billGenerationSearchCriria(){
		try{

			if(mGetPropertyFromFile("rnl_BgContractNumInput").equalsIgnoreCase("yes"))
			{

				if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent")) 
				{
					mSendKeys("id", mGetPropertyFromFile("rnl_BgContractNumInputid"),RNLContractNo.get(CurrentinvoCount));
				}

				else
				{
					mSendKeys("id", mGetPropertyFromFile("rnl_BgContractNumInputid"),mGetPropertyFromFile("applicationNo"));
				}

			}

			if(mGetPropertyFromFile("rnl_BgTenantNumInput").equalsIgnoreCase("yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("rnl_BgTenantNumInputid"),mGetPropertyFromFile("rnl_BgTenantNumInputData"));
			}

			if(mGetPropertyFromFile("rnl_BgTenantNameInput").equalsIgnoreCase("yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("rnl_BgTenantNameInputid"),mGetPropertyFromFile("rnl_BgTenantNameInputData"));
			}

			//Searching application
			mWaitForVisible("id", mGetPropertyFromFile("rnl_BgSearchApplicationid"));
			mCustomWait(1000);
			mClick("id", mGetPropertyFromFile("rnl_BgSearchApplicationid"));

		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in billGenerationSearchCriria");
		}			
	}	


	//written by hiren kathiria for dbtesting on 9/11/2016
	//last modified on 11/11/2016

	public ResultSet dbtest()
	{
		try {

			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			}
			catch(ClassNotFoundException ex) {
				System.out.println("Error: unable to load driver class! " +ex);
				System.exit(1);
			}
			catch(IllegalAccessException ex) {
				System.out.println("Error: access problem while loading!");
				System.exit(2);
			}
			catch(InstantiationException ex) {
				System.out.println("Error: unable to instantiate driver!");
				System.exit(3);
			}

			//String URL = "jdbc:oracle:thin:@103.241.183.246:1521:rac";
			String URL = "jdbc:oracle:thin:@192.168.100.169:1521:ORCLUNI";
			String USER = "mainet_prod_phase2";
			String PASS = "mainet_prod_phase2";
			Connection conn = DriverManager.getConnection(URL, USER, PASS);

			Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				db_contno= TenantNo.get(CurrentinvoCount);	
			}	
			else
			{
				db_contno=mGetPropertyFromFile("applicationNo");
			}


			db_orgid=mGetPropertyFromFile("db_orgid");


			String query1 ="select \r\n" +
					"acr_id, acr_contract_no \r\n" + 
					"from tb_rl_contract_mas" +
					"\r\n" + 
					" where orgid = "+db_orgid + " and acr_contract_no= '"+db_contno+"'"; 			


			System.out.println("Query :: " + query1);

			rs1 = st.executeQuery(query1);			

			System.out.println();

			while (rs1.next()) {
				System.out.println(rs1.getString(1) +"    "+ rs1.getString(2));	
				db_acrid=rs1.getString(1);
				System.out.println("acrid is : " +db_acrid); 
			}


			String query ="select \r\n" +
					"receipt_no, receipt_dt, receipt_amount \r\n" + 
					"from tb_rl_contract_mas n, TB_RL_RCPT_MAS a" +
					"\r\n" + 
					" where n.orgid = a.orgid" +
					" and n.acr_id = a.acr_id" +
					" and a.orgid = "+db_orgid +
					" and a.acr_id = "+db_acrid;			


			System.out.println("Query :: " + query);

			rs1 = st.executeQuery(query); 			
			int xyz=1;
			System.out.println();
			int count = 0;

			while (rs1.next()) {
				System.out.println(rs1.getString(1) +"    "+ rs1.getString(2)+"   " + rs1.getString(3));


				System.out.println("count :: " + count++);
				datatobewritten = new ArrayList<>();


				//Adding  receipt no to arraylist

				String rcptno = rs1.getString("RECEIPT_NO");
				datatobewritten.add(rcptno);
				System.out.println("the data of string1 is : "+datatobewritten); 

				//Adding date to arraylist

				String[] rcptdate = rs1.getString("RECEIPT_DT").split("\\s");			

				DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
				DateFormat inputFormat = new SimpleDateFormat("yyyy-M-dd");

				Date date = inputFormat.parse(rcptdate[0]);

				rcptdate[0]=outputFormat.format(date);

				System.out.println("date is :: "+rcptdate[0]);

				datatobewritten.add(rcptdate[0]);	
				System.out.println("the data of string2 is : "+datatobewritten); 			

				//Adding amount to arraylist

				String rcptamt = rs1.getString("RECEIPT_AMOUNT");					
				datatobewritten.add(rcptamt);
				System.out.println("the data of string3 is : "+datatobewritten); 

				datawritten.put(Integer.toString(xyz).trim(), datatobewritten);

				System.out.println("the written data is : "+datawritten);
				xyz++;
			}

			System.out.println("the result is : " +rs1);

			int size= 0;
			if (rs1 != null)   
			{  
				rs1.beforeFirst();  
				rs1.last();  
			}  


			ResultSetMetaData rsmd = rs1.getMetaData();
			int columnsNumber = rsmd.getColumnCount(); 
			System.out.println("columnsNumber:"+columnsNumber);
			FileInputStream fis = new FileInputStream(new File("C:\\Users\\Hiren.Kathiria\\Desktop\\Sheet02forComparison.xlsx"));
			XSSFWorkbook workbook = new XSSFWorkbook (fis);
			XSSFSheet sheet = workbook.getSheetAt(0);

			System.out.println();

			String name = rsmd.getColumnLabel(1);
			System.out.println(name);

			System.out.println("size is:::::::::::"+rs1.getRow());


			for (Entry<String, ArrayList<String>> entry : datawritten.entrySet()) {
				String key1 = entry.getKey();
				ArrayList<String> value1 = entry.getValue();

				row1= sheet.createRow(Integer.valueOf(key1)-1);
				System.out.println("Key is ::"+key1 );
				System.out.println("Value is::::::: "+value1);

				for(int i=0;i<datatobewritten.size();i++)
				{	
					XSSFCellStyle cellStyle = workbook.createCellStyle();
					cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
					cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
					cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);

					XSSFCell r1c1 = row1.createCell(i);
					r1c1.setCellValue(value1.get(i));
					r1c1.setCellStyle(cellStyle);
				}
			}

			fis.close();
			FileOutputStream fos =new FileOutputStream(new File("C:\\Users\\Hiren.Kathiria\\Desktop\\Sheet02forComparison.xlsx"));
			workbook.write(fos);
			fos.close();
			System.out.println();
			System.out.println("Done"); 
			conn.close();		

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return rs1;
	}


	// Fetching Land/Estate details from database Piyush 07 Jan 2017
	public void EstateDb_ContractDetails(){
		try {

			try {

				try
				{
					Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
				}
				catch(ClassNotFoundException ex) {
					System.out.println("Error: unable to load driver class! " +ex);
					System.exit(1);
				}
				catch(IllegalAccessException ex) {
					System.out.println("Error: access problem while loading!");
					System.exit(2);
				}
				catch(InstantiationException ex) {
					System.out.println("Error: unable to instantiate driver!");
					System.exit(3);
				}

				//String URL = "jdbc:oracle:thin:@103.241.183.246:1521:rac";
				String URL = "jdbc:oracle:thin:@192.168.100.169:1521:ORCLUNI";
				String USER = "mainet_prod_phase2";
				String PASS = "mainet_prod_phase2";
				Connection conn = DriverManager.getConnection(URL, USER, PASS);

				Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


				if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
				{
					db_etateno=RNLContractNo.get(CurrentinvoCount);

				}	
				else
				{

					db_etateno= EstateNo.get(CurrentinvoCount);	
				}
				db_orgid=mGetPropertyFromFile("db_orgid");

				String query1 ="SELECT l.lem_estate_description,\r\n" + 
						"       l.lem_estate_no,\r\n" + 
						"       l.lem_address,\r\n" + 
						"       l.cod_id1_ety,a.cod_desc as Land_Estate_Type,\r\n" + 
						"       l.cod_id2_ety,b.cod_desc as Land_Estate_SubType\r\n" + 
						"FROM tb_le_estate_mas l\r\n" + 
						"INNER JOIN \r\n" + 
						"   tb_comparent_det a on l.cod_id1_ety=a.cod_id\r\n" + 
						"INNER JOIN \r\n" + 
						"   tb_comparent_det b on l.cod_id2_ety=b.cod_id\r\n" + 
						" where l.orgid = "+db_orgid + " and l.lem_estate_no= '"+db_etateno+"'";


				System.out.println("Query :: " + query1);

				rs1 = st.executeQuery(query1);			

				System.out.println();

				while (rs1.next()) {

					System.out.println(rs1.getString(1) +"    "+ rs1.getString(2)+"    "+ rs1.getString(3)+"    "+ rs1.getString(5)+"    "+ rs1.getString(7));
					db_landEstateName=rs1.getString(1);
					db_landEstateCode=rs1.getString(2);
					db_landEstateAddr=rs1.getString(3);
					db_landEstateType=rs1.getString(5);
					db_landEstateSubType=rs1.getString(7);
					System.out.println("DB landEstateName is : " +db_landEstateName);
					System.out.println("DB landEstateCode is : " +db_landEstateCode);
					System.out.println("DB landEstateAddr is : " +db_landEstateAddr);
					System.out.println("DB landEstateType is : " +db_landEstateType);
					System.out.println("DB landEstateSubType is : " +db_landEstateSubType);
				}

				conn.close();		

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			mGenericWait();


			// Capturing searched Land/Estate details
			WebElement table = driver.findElement(By.id("gbox_gridEstateDetailEdit"));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount = rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

			int Rowno = 0;
			for (int a=0;a<rwcount;a++) 
			{
				List<WebElement> cols = rows.get(a).findElements(By.xpath("td"));
				int colcount = cols.size();
				System.out.println("NUMBER OF COLUMNS IN THIS TABLE = " + colcount);
				for(int b=0;b<colcount;b++)
				{
					if(a>=1)
					{
						if(b==0)
						{
							selectedLandEstateName = rows.get(a).findElement(By.xpath("td[1]")).getText();
							System.out.println("Land/Estate Name is : "+selectedLandEstateName);
						}
						if(b==1)
						{
							selectedLandEstateCode = rows.get(a).findElement(By.xpath("td[2]")).getText();
							System.out.println("Land/Estate Code is : "+selectedLandEstateCode);
						}
						if(b==2)
						{
							selectedLandEstateAddr = rows.get(a).findElement(By.xpath("td[3]")).getText();
							System.out.println("Land Estate Address is : "+selectedLandEstateAddr);
						}
						if(b==3)
						{
							selectedLandEstateType = rows.get(a).findElement(By.xpath("td[4]")).getText();
							System.out.println("Land Estate Type is : "+selectedLandEstateType);
						}
						if(b==4)
						{
							selectedLandEstateSubType = rows.get(a).findElement(By.xpath("td[5]")).getText();
							System.out.println("Land Estate Sub Type is : "+selectedLandEstateSubType);
						}
						if(b==5)
						{
							selectedBookingAmt = rows.get(a).findElement(By.xpath("td[6]")).getText();
							System.out.println("Booking Amount is : "+selectedBookingAmt);
						}

					}
				}

			}


		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in BOE_LOILetterPDF");
		}			
	}


	//Report Assertion method for acceptance letter Piyush 11 jan 2017
	public void rnl_Acceptance_letter_assertion(){
		try{

			api.PdfPatterns.RNL_Acceptance_Letter_PDF(pdfoutput);
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in BOE_LOILetterPDF");
		}
	}



	//Report Assertion method for Billing Payment Piyush 11 jan 2017
	public void rnl_BillingPayment_letter_assertion(){
		try{

			api.PdfPatterns.RNL_Bill_Pdf(pdfoutput);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(pdf_ApplicantName.get(CurrentinvoCount),Rnl_ApplicantLname.get(CurrentinvoCount),"Applicant Name in Bill payment receipt dose not match with actual::"+pdf_ApplicantName.get(CurrentinvoCount)+"Expected::"+Rnl_ApplicantFullname.get(CurrentinvoCount));
				mAssert(pdf_ApplicationNo.get(CurrentinvoCount), applicationNo.get(CurrentinvoCount),"Applicant No in Bill payment receipt dose not match with actual::"+pdf_ApplicationNo.get(CurrentinvoCount)+"Expected::"+applicationNo.get(CurrentinvoCount));
				mAssert(pdf_ULBName.get(CurrentinvoCount),Ass_ULBName.get(CurrentinvoCount),"ULB Name in Bill payment receipt dose not match with actual::"+pdf_ServiceName.get(CurrentinvoCount)+"Expected::"+Rnl_ApplicantLname.get(CurrentinvoCount));
				mAssert(pdf_ServiceName.get(CurrentinvoCount),RNL_Array_ServiceName.get(CurrentinvoCount),"Service Name in Bill payment receipt dose not match with actual::"+pdf_ServiceName.get(CurrentinvoCount)+"Expected::"+RNL_Array_ServiceName.get(CurrentinvoCount));
				mAssert(pdf_BillDate.get(CurrentinvoCount),TM_BillGenerationdate.get(CurrentinvoCount),"Bill Generation Bill payment receipt dose not match with actual::"+pdf_BillDate.get(CurrentinvoCount)+"Expected::"+TM_BillGenerationdate.get(CurrentinvoCount));
				mAssert(pdf_TenantName.get(CurrentinvoCount),TCtenantName.get(CurrentinvoCount),"Tenant Name in Bill payment receipt dose not match with actual::"+pdf_TenantName.get(CurrentinvoCount)+"Expected::"+TCtenantName.get(CurrentinvoCount));
				mAssert(pdf_ContractNo.get(CurrentinvoCount),TCcontractNo.get(CurrentinvoCount),"Contract No in Bill payment receipt dose not match with actual::"+pdf_ContractNo.get(CurrentinvoCount)+"Expected::"+TCcontractNo.get(CurrentinvoCount));
				mAssert(pdf_TenantNo.get(CurrentinvoCount),TCtenantNo.get(CurrentinvoCount),"Tenant No in Bill payment receipt dose not match with actual::"+pdf_TenantNo.get(CurrentinvoCount)+"Expected::"+TCtenantNo.get(CurrentinvoCount));

			}
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in BOE_LOILetterPDF");
		}
	}

	//Report Assertion method for Rent Contract Note Piyush 11 jan 2017
	public void rnl_Rent_Contract_note_letter_assertion(){
		try{

			api.PdfPatterns.RNL_RentContractNote_PDF(pdfoutput);
			mAssert(pdf_ApplicantName,Rnl_ApplicantLname,"Applicant Name in Acceptance Letter dose not match with actual::"+pdf_ApplicantName+"Expected::"+Rnl_ApplicantFullname);
			mAssert(pdf_ApplicationNo, applicationNo,"Applicant No in Acceptance Letter dose not match with actual::"+pdf_ApplicationNo+"Expected::"+applicationNo);
			mAssert(pdf_ULBName,Ass_ULBName,"ULB Name in Acceptance Letter dose not match with actual::"+pdf_ServiceName+"Expected::"+Rnl_ApplicantLname);
			mAssert(pdf_ServiceName,RNL_Array_ServiceName,"Service Name in Acceptance Letter dose not match with actual::"+pdf_ServiceName+"Expected::"+RNL_Array_ServiceName);


		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in BOE_LOILetterPDF");
		}
	}

	//Report Assertion method for Approval Letter Piyush 11 jan 2017
	public void rnl_Approval_letter_assertion(){
		try{

              api.PdfPatterns.RNL_ApprovalLetter(pdfoutput);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(pdf_ApplicantName.get(CurrentinvoCount),Rnl_ApplicantFullname.get(CurrentinvoCount),"Applicant Name in Acceptance Letter dose not match with actual::"+pdf_ApplicantName.get(CurrentinvoCount)+"Expected::"+Rnl_ApplicantFullname.get(CurrentinvoCount));
				mAssert(pdf_ApplicationNo.get(CurrentinvoCount), applicationNo.get(CurrentinvoCount),"Applicant No in Acceptance Letter dose not match with actual::"+pdf_ApplicationNo.get(CurrentinvoCount)+"Expected::"+applicationNo);
				mAssert(pdf_ServiceName.get(CurrentinvoCount),ServiceNameRNL.get(CurrentinvoCount),"Service Name in Acceptance Letter dose not match with actual::"+pdf_ServiceName.get(CurrentinvoCount)+"Expected::"+ServiceNameRNL.get(CurrentinvoCount));
				mAssert(pdf_FromDate.get(CurrentinvoCount),Rnl_BookingfromDate.get(CurrentinvoCount),"LOI Date in Acceptance Letter dose not match with actual::"+pdf_FromDate.get(CurrentinvoCount)+"Expected::"+Rnl_BookingfromDate.get(CurrentinvoCount));
				mAssert(pdf_ToDate.get(CurrentinvoCount),Rnl_BookingtoDate.get(CurrentinvoCount),"LOI Date in Acceptance Letter dose not match with actual::"+pdf_ToDate.get(CurrentinvoCount)+"Expected::"+Rnl_BookingtoDate.get(CurrentinvoCount));
				mAssert(pdf_EstateNo.get(CurrentinvoCount),Rnl_EstateNo.get(CurrentinvoCount),"LOI Date in Acceptance Letter dose not match with actual::"+pdf_EstateNo.get(CurrentinvoCount)+"Expected::"+Rnl_EstateNo.get(CurrentinvoCount));
				mAssert(pdf_EstateDescription.get(CurrentinvoCount),Rnl_EstateDesciption.get(CurrentinvoCount),"LOI Date in Acceptance Letter dose not match with actual::"+pdf_EstateDescription.get(CurrentinvoCount)+"Expected::"+Rnl_EstateDesciption.get(CurrentinvoCount));
			}

		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in BOE_LOILetterPDF");
		}
	}


	//Report Assertion method for LOI receipt for cash Piyush 11 jan 2017
	public  void rnl_LOI_Receipt_assertion(){
		try{

			api.PdfPatterns.RNL_Common_LOi_Receipt_Cash(pdfoutput);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(pdf_ApplicantName.get(CurrentinvoCount),Rnl_ApplicantFullname.get(CurrentinvoCount),"Applicant Name in LOI Payment generated receipt dose not match with actual::"+pdf_ApplicantName.get(CurrentinvoCount)+"Expected::"+Rnl_ApplicantFullname.get(CurrentinvoCount));
				mAssert(pdf_ApplicationNo.get(CurrentinvoCount), applicationNo.get(CurrentinvoCount),"Applicant No in LOI Payment generated receipt dose not match with actual::"+pdf_ApplicationNo.get(CurrentinvoCount)+"Expected::"+applicationNo.get(CurrentinvoCount));
				//mAssert(pdf_Applicationdate.get(CurrentinvoCount),Rnl_BookingDate.get(CurrentinvoCount),"Application Date in LOI Payment generated receipt dose not match with actual::"+pdf_LOIDate.get(CurrentinvoCount)+"Expected::"+Rnl_ApplicantLname.get(CurrentinvoCount));
				mAssert(pdf_ServiceName.get(CurrentinvoCount),ServiceNameRNL.get(CurrentinvoCount),"Service Name in LOI Payment generated receipt dose not match with actual::"+pdf_ServiceName.get(CurrentinvoCount)+"Expected::"+ServiceNameRNL.get(CurrentinvoCount));
				mAssert(pdf_TotalPaybleAmt.get(CurrentinvoCount),Loi_TotalPaybleamt.get(CurrentinvoCount),"LOI Total Payble amount in LOI Payment generated receipt dose not match with actual::"+pdf_TotalPaybleAmt.get(CurrentinvoCount)+"Expected::"+Loi_TotalPaybleamt.get(CurrentinvoCount));
				mAssert(pdf_PaymentMode,Loi_ModeofPayment,"LOI Payment mode in LOI Payment generated receipt dose not match with actual::"+pdf_PaymentMode.get(CurrentinvoCount)+"Expected::"+Loi_ModeofPayment.get(CurrentinvoCount));
				mAssert(pdf_LOINo.get(CurrentinvoCount),Loi_LoiNo.get(CurrentinvoCount),"LOI No in LOI Payment generated receipt dose not match with actual::"+pdf_LOIDate.get(CurrentinvoCount)+"Expected::"+Rnl_ApplicantLname.get(CurrentinvoCount));
				mAssert(pdf_LOIDate.get(CurrentinvoCount),Scrutiny_LoiDate.get(CurrentinvoCount),"LOI Date in Acceptance Letter dose not match with actual::"+pdf_LOIDate.get(CurrentinvoCount)+"Expected::"+Rnl_ApplicantLname.get(CurrentinvoCount));
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in BOE_LOILetterPDF");
		}
	}

	public void loiPayment(String module, String Service) throws InterruptedException, IOException
	{
		try
		{
			//mCreateArtefactsFolder(module);
			//chllanForTpServices = true;
			//before_LoiPayment();
			mOpenBrowser("Chrome");
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("rnl_MBADeptName"),mGetPropertyFromFile("rnl_MBADeptPassword"));
			LOIPayment(module);
			logOut();
			finalLogOut();
			System.out.println("This is service for which loi payment will be made"+Service);
			isChallanVerftnRequired(Service);
			//rnl_LOI_Receipt_assertion();
			System.out.println("after challan verification of loi");
			mCloseBrowser();
			System.out.println("browser closed");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in loiPayment method");
		}
	}
	//LOI Payment
	public void LOIPayment(String module)
	{
		try
		{
			//Payment Link
			mGenericWait();
			mWaitForVisible("linkText", mGetPropertyFromFile("LOIPaymentOnlineServiceLinkid"));
			mClick("linkText", mGetPropertyFromFile("LOIPaymentOnlineServiceLinkid"));
			mCustomWait(2000);

			//Payment Link
			mGenericWait();
			mWaitForVisible("linkText", mGetPropertyFromFile("LOIPaymentLinkid"));
			mClick("linkText", mGetPropertyFromFile("LOIPaymentLinkid"));
			mCustomWait(2000);

			//LOI payment Services link
			mWaitForVisible("linkText", mGetPropertyFromFile("LOIPaymentServicesLinkid"));
			mClick("linkText", mGetPropertyFromFile("LOIPaymentServicesLinkid"));
			mCustomWait(2000);

			//Select department from dropdown
			mWaitForVisible("id", mGetPropertyFromFile("LOIPaymentdeptId"));
			mSelectDropDown("id", mGetPropertyFromFile("LOIPaymentdeptId"), module);


			//sending application number
			mWaitForVisible("id", mGetPropertyFromFile("LpSearchByAppNumid"));
			mCustomWait(1000);

			IndOrDep("id", "LpSearchByAppNumid", "applicationNo");
			mCustomWait(1000);

			mCustomWait(2000);
			mTakeScreenShot();
			//mSendKeys("id", mGetPropertyFromFile("LpSearchByAppNumid"),"71116050300011");

			//Search Button
			mWaitForVisible("xpath", mGetPropertyFromFile("LpSearchAppBtnid"));
			mClick("xpath", mGetPropertyFromFile("LpSearchAppBtnid"));
			mCustomWait(2000);
			mTakeScreenShot();

			//View Details Button
			mWaitForVisible("css", mGetPropertyFromFile("LpViewDetailsImgid"));
			mClick("css", mGetPropertyFromFile("LpViewDetailsImgid"));
			mCustomWait(2000);
			mTakeScreenShot();

			//getting application number for assert
			String ApplicationNum =  driver.findElement(By.id(mGetPropertyFromFile("LpAssertAppNoid"))).getAttribute("value");
			System.out.println("Application Number is::::"+ApplicationNum);
			mCustomWait(2000);

			mAssert(ApplicationNum, appNo, "Application Number does not match at LOI Payment.  Actual  :"+ApplicationNum+"    Expected    :"+appNo);


			//String ApplicationNum = driver.findElement(By.id("applicationNo")).getText();
			String LOINUm = driver.findElement(By.id(mGetPropertyFromFile("LpAssertLOINoid"))).getAttribute("value");
			Loi_LoiNo.add(LOINUm);
			System.out.println(LOINUm);
			mCustomWait(2000);


			//mAssert(LOINUm, "100", "LOI Number does not match at LOI Payment.  Actual  :"+LOINUm+"   Expected   :100");
			//mAssert(LOINUm, LOINumber, "LOI Number does not match at LOI Payment.  Actual  :"+LOINUm+"   Expected   :"+LOINumber);

			//      check assert for LOI
			//		mAssert(LOINUm, "100", "LOI Number does not match at LOI Payment.  Actual  :"+LOINUm+"   Expected   :100");

			//mAssert(LOINUm, LOINumber, "LOI Number does not match at LOI Payment.  Actual  :"+LOINUm+"   Expected   :"+LOINumber);

			String Paybleamt = mGetText("id",mGetPropertyFromFile("rnl_LpLOIAmountid"),"value");
			Loi_TotalPaybleamt.add(Paybleamt);
			System.out.println(Paybleamt);
			mCustomWait(2000);

			mscroll(0, 250);


			//new
			//LOIAPPLICABLE= true; -- Commented by Madhuri on 12-01-2017

			payment("paymentMode","byBankOrULB");
			//String LOINUm = driver.findElement(By.id("loiNo")).getText();

			if(!mGetPropertyFromFile("paymentMode").equalsIgnoreCase("online")){
				//submit button
				mWaitForVisible("xpath", mGetPropertyFromFile("LpLOIPaymentSubBtnid"));
				mTakeScreenShot();
				mClick("xpath", mGetPropertyFromFile("LpLOIPaymentSubBtnid"));
				mCustomWait(2000);

				//proceed button
				mWaitForVisible("id", mGetPropertyFromFile("LpLOIPaymentProcdBtnid"));
				mCustomWait(2000);
				String msg = mGetText("css", mGetPropertyFromFile("LpLOIPaymentProcdMsgid"));

				mAssert(msg, mGetPropertyFromFile("LpLOIPaymentProcdMsgdata"), "Message Does not match at LOI Payment POPup.   Actual   :"+msg+"   Expected   :"+mGetPropertyFromFile("LpLOIPaymentProcdMsgdata"));
				System.out.println(msg);
				mCustomWait(2000);

				//String msg = driver.findElement(By.cssSelector("div.msg-dialog-box.ok-msg > p")).getText();
				mClick("id", mGetPropertyFromFile("LpLOIPaymentProcdBtnid"));
				mCustomWait(2000);

				//to be commented
				/*mCustomWait(12000);
			mSwitchTabs();*/
				//Not to be commented
				mChallanPDFReader();
				mGenericWait();

			}
			//Not to be commented
			//LOI Payment verification with assertions
			//LOIVerification();
			//mGenericWait();

		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in LOIPayment method");
		}
	}


	//Piyush on 20/Jan/2017 Get Cutternt date/Booking date
	public static void GetCurrentdate()
	{
		try{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

			//Eg: 02/16/2017
			String strDate = sdf.format(date);
			System.out.println("formatted date in mm/dd/yy : " + strDate);

			sdf = new SimpleDateFormat("dd-MMM-yyyy");
			strDate = sdf.format(date);
			System.out.println("formatted date in dd/MMM/yyyy : " + strDate);
			ApplicationdateinYear.add(strDate);
			System.out.println("Application date in arraylist is ::::"+ApplicationdateinYear);


			//Format eg: 18/01/2017
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			strDate = sdf.format(date);
			System.out.println("formatted date in dd/MM/yyyy : " + strDate);
			Applicationdate.add(strDate);
			System.out.println("Application date in arraylist is ::::"+Applicationdate);

		}
		catch(Exception e){
			e.printStackTrace();
			exception = e.toString();	
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Current date method GetCurrentdate() failed");
		}
	}



	/*//RNL Scrutiny View Assertion for Booking of Estate 06 Jan 2017
	public  void  RNL_ScrutinyView_Assertion()
	{
		try {

			String scr_rnlApptitle=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateTitleID"),"value");
			System.out.println("Applicant Title is in Scutiny : "+scr_rnlApptitle);
			BE_Scr_rnlApptitle.add(scr_rnlApptitle);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Rnl_ApplicantTitle.get(CurrentinvoCount), BE_Scr_rnlApptitle.get(CurrentinvoCount), "Applicant Title does not match with the actual::"+BE_Scr_rnlApptitle.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Rnl_ApplicantTitle.get(CurrentinvoCount));
			}

			String scr_rnlEstateFname=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateFnameID"),"value");
			System.out.println("Applicant First Name is in Scutiny : "+scr_rnlEstateFname);
			BE_scr_rnlEstateFname.add(scr_rnlEstateFname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Rnl_ApplicantFname.get(CurrentinvoCount), BE_scr_rnlEstateFname.get(CurrentinvoCount), "Applicant First Name does not match with the actual::"+BE_scr_rnlEstateFname.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Rnl_ApplicantFname.get(CurrentinvoCount));
			}

			String scr_rnlEstateMname=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateMidnameID"),"value");
			System.out.println("Applicant Middle Name is in Scutiny : "+scr_rnlEstateMname);
			BE_scr_rnlEstateMname.add(scr_rnlEstateMname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Rnl_ApplicantMname.get(CurrentinvoCount), BE_scr_rnlEstateMname.get(CurrentinvoCount), "Applicant  Middle Name does not match with the actual::"+BE_scr_rnlEstateMname.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Rnl_ApplicantMname.get(CurrentinvoCount));
			}


			String scr_rnlEstateLname=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateLnameID"),"value");
			System.out.println("Applicant Last Name is in Scutiny : "+scr_rnlEstateLname);
			BE_scr_rnlEstateLname.add(scr_rnlEstateLname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Rnl_ApplicantLname.get(CurrentinvoCount), BE_scr_rnlEstateLname.get(CurrentinvoCount), "Applicant Last Name does not match with the actual::"+BE_scr_rnlEstateLname.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Rnl_ApplicantLname.get(CurrentinvoCount));
			}

			String scr_rnlEstateLFullname=scr_rnlApptitle+" "+scr_rnlEstateFname+" "+scr_rnlEstateMname+" "+scr_rnlEstateLname;
			System.out.println("Applicant Full Name is in Scutiny : "+scr_rnlEstateLFullname);
			BE_scr_rnlEstateLFullname.add(scr_rnlEstateLFullname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(BE_scr_rnlEstateLFullname.get(CurrentinvoCount), BE_scr_rnlEstateLFullname.get(CurrentinvoCount), "Applicant Full Name does not match with the actual::"+BE_scr_rnlEstateLFullname.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+BE_scr_rnlEstateLFullname.get(CurrentinvoCount));
			}

			String scr_rnlEstateMobNo=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateMobNoID"),"value");
			System.out.println("Applicant Mobile No is in Scutiny : "+scr_rnlEstateMobNo);
			BE_scr_rnlEstateMobNo.add(scr_rnlEstateMobNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Rnl_ApplicantMbNo.get(CurrentinvoCount), BE_scr_rnlEstateMobNo.get(CurrentinvoCount), "Applicant Mobile No does not match with the actual::"+BE_scr_rnlEstateMobNo.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Rnl_ApplicantMbNo.get(CurrentinvoCount));
			}

			String scr_rnlEstateEmail=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateEmailID"),"value");
			System.out.println("Applicant Email Id is in Scutiny : "+scr_rnlEstateEmail);
			BE_scr_rnlEstateEmail.add(scr_rnlEstateEmail);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Rnl_ApplicantEmail.get(CurrentinvoCount), BE_scr_rnlEstateEmail.get(CurrentinvoCount), "Applicant Email Id does not match with the actual::"+BE_scr_rnlEstateEmail.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Rnl_ApplicantEmail.get(CurrentinvoCount));
			}

			String scr_rnlEstateRemarks=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateRemarksID"),"value");
			System.out.println("Applicant Remarks is in Scutiny : "+scr_rnlEstateRemarks);
			BE_scr_rnlEstateRemarks.add(scr_rnlEstateRemarks);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Rnl_ApplicantRemarks.get(CurrentinvoCount), BE_scr_rnlEstateRemarks.get(CurrentinvoCount), "Applicant Booking of Estate Remarks does not match with the actual::"+BE_scr_rnlEstateRemarks.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Rnl_ApplicantRemarks.get(CurrentinvoCount));
			}

			String scr_rnlEstateFromDate=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateFromDateID"),"value");
			System.out.println("Applicant Estate Booking From date is in Scutiny : "+scr_rnlEstateFromDate);
			BE_scr_rnlEstateFromDate.add(scr_rnlEstateFromDate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Rnl_BookingfromDate.get(CurrentinvoCount), BE_scr_rnlEstateFromDate.get(CurrentinvoCount), "Applicant Estate Booking From does not match with the actual::"+BE_scr_rnlEstateFromDate.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Rnl_BookingtoDate.get(CurrentinvoCount));
			}

			String scr_rnlEstateToDate=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateToDateID"),"value");
			BE_scr_rnlEstateToDate.add(scr_rnlEstateToDate);
			System.out.println("Applicant Estate Booking To Date is in Scutiny : "+scr_rnlEstateToDate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Rnl_BookingtoDate, BE_scr_rnlEstateToDate, "Applicant Estate Booking to does not match with the actual::"+BE_scr_rnlEstateToDate.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Rnl_BookingtoDate.get(CurrentinvoCount));
			}

			mCustomWait(1000);
			Tblread_RNL_Scrutiny_Booking_Details();
			mCustomWait(1000);

			if(abc.equalsIgnoreCase("Renewal of Rent Contract") && scrutinylevelcounter==2)
				{
					String scr_rnlApprovedBy=mGetText("id", mGetPropertyFromFile("rnl_View_ApprovedByid"),"value");
					System.out.println("Approved By : "+scr_rnlApprovedBy);


					String scr_rnlApprovedDate=mGetText("id", mGetPropertyFromFile("rnl_View_ApprovedDateid"),"value");
					System.out.println("Approved By : "+scr_rnlApprovedDate);

					String scr_rnlApprovedRemarks=mGetText("id", mGetPropertyFromFile("rnl_View_ApprovedRemarkid"),"value");
					System.out.println("Approved By : "+scr_rnlApprovedRemarks);

				}

		}

		catch (Exception e) 
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in RNL_ScrutinyView_Assertion method");
		}
	}

	 */

	//RNL Scrutiny Assertion Form for Renewal of Rent Contract 07 Jan 2017
	//changes done dated 23/2/2017
	public  void  RNL_RenewalofRent_ScrutinyView_Assertion()
	{
		try {

			ScrutinyServiceName=mGetText("css", mGetPropertyFromFile("Sc_scrutinyProcessServiceName1id"));
			System.out.println(ScrutinyServiceName);


			String scr_rnlApptitle=mGetText("id", mGetPropertyFromFile("rnl_RrApplicantTitleid"),"value");
			System.out.println("Applicant Title is in Scutiny : "+scr_rnlApptitle);
			Scr_Rnl_ApplicantTitle.add(scr_rnlApptitle);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(Rnl_ApplicantTitle.get(CurrentinvoCount), Scr_Rnl_ApplicantTitle.get(CurrentinvoCount), "Applicant Title does not match with the actual::"+Scr_Rnl_ApplicantTitle.get(CurrentinvoCount)+ "in scrutiny View form, Expected ::"+Rnl_ApplicantTitle.get(CurrentinvoCount));
			}

			String scr_rnlEstateFname=mGetText("id", mGetPropertyFromFile("rnl_RrAplcntFirstNameid"),"value");
			System.out.println("Applicant Title is in Scutiny : "+scr_rnlEstateFname);
			Scr_Rnl_ApplicantFname.add(scr_rnlEstateFname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(Rnl_ApplicantFname.get(CurrentinvoCount), Scr_Rnl_ApplicantFname.get(CurrentinvoCount), "Applicant First Name does not match with the actual::"+Scr_Rnl_ApplicantFname.get(CurrentinvoCount)+ "in scrutiny View form, Expected ::"+Rnl_ApplicantFname.get(CurrentinvoCount));
			}

			String scr_rnlEstateMname=mGetText("id", mGetPropertyFromFile("rnl_RrAplcntMiddleNameid"),"value");
			System.out.println("Applicant Title is in Scutiny : "+scr_rnlEstateMname);
			Scr_Rnl_ApplicantMname.add(scr_rnlEstateMname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(Rnl_ApplicantMname.get(CurrentinvoCount), Scr_Rnl_ApplicantMname.get(CurrentinvoCount), "Applicant Middle does not match with the actual::"+Scr_Rnl_ApplicantMname.get(CurrentinvoCount)+ "in scrutiny View form, Expected ::"+Rnl_ApplicantMname.get(CurrentinvoCount));
			}

			String scr_rnlEstateLname=mGetText("id", mGetPropertyFromFile("rnl_RrAplcntLastNameid"),"value");
			System.out.println("Applicant Title is in Scutiny : "+scr_rnlEstateLname);
			Scr_Rnl_ApplicantLname.add(scr_rnlEstateLname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(Rnl_ApplicantLname.get(CurrentinvoCount), Scr_Rnl_ApplicantLname.get(CurrentinvoCount), "Applicant Last does not match with the actual::"+Scr_Rnl_ApplicantLname.get(CurrentinvoCount)+ "in scrutiny View form, Expected ::"+Rnl_ApplicantLname.get(CurrentinvoCount));
			}

			String scr_rnlEstateLFullname=scr_rnlApptitle+" "+scr_rnlEstateFname+" "+scr_rnlEstateMname+" "+scr_rnlEstateLname;
			Scr_Rnl_ApplicantFullname.add(scr_rnlEstateLFullname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(Rnl_ApplicantLname.get(CurrentinvoCount), Scr_Rnl_ApplicantFullname.get(CurrentinvoCount), "Applicant Full Name does not match with the actual::"+Scr_Rnl_ApplicantFullname.get(CurrentinvoCount)+ "in scrutiny View form, Expected ::"+Rnl_ApplicantLname.get(CurrentinvoCount));
			}


			String scr_rnlEstateMobNo=mGetText("id", mGetPropertyFromFile("rnl_RrAplcntMobileNoid"),"value");
			System.out.println("Applicant Mobile No is in Scutiny : "+scr_rnlEstateMobNo);
			Scr_Rnl_ApplicantMbNo.add(scr_rnlEstateMobNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(Rnl_ApplicantMbNo.get(CurrentinvoCount), Scr_Rnl_ApplicantMbNo.get(CurrentinvoCount), "Applicant Mobile No does not match with the actual in scrutiny View form");
			}

			String scr_rnlEstateEmail=mGetText("id", mGetPropertyFromFile("rnl_RrAplcntEmailid"),"value");
			System.out.println("Applicant Email Id is in Scutiny : "+scr_rnlEstateEmail);
			Scr_Rnl_ApplicantEmail.add(scr_rnlEstateEmail);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(Rnl_ApplicantEmail, Scr_Rnl_ApplicantEmail, "Applicant Email Id does not match with the actual in scrutiny View form");
			}


			String SCR_renewaldate=mGetText("id", mGetPropertyFromFile("rrl_Scrrenewaldateid"),"value");
			System.out.println("Applicant Email Id is in Scutiny : "+SCR_renewaldate);
			Scr_Rnl_RenewalDate.add(SCR_renewaldate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(Rnl_ApplicantEmail, Scr_Rnl_RenewalDate, "Applicant Email Id does not match with the actual in scrutiny View form");
			}


			String SCR_ContractNo=mGetText("id", mGetPropertyFromFile("rrl_ScrContractNoid"),"value");;
			System.out.println("Applicant Email Id is in Scutiny : "+SCR_renewaldate);
			Scr_Rnl_ContractNo.add(SCR_ContractNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(contractno.get(CurrentinvoCount), Scr_Rnl_ContractNo.get(CurrentinvoCount), "Applicant Email Id does not match with the actual in scrutiny View form");
			}

			String scr_rnlEstateFromDate=mGetText("id", mGetPropertyFromFile("rnl_RrRenewalFromDateid"),"value");
			System.out.println("Applicant Title is in Scutiny : "+scr_rnlEstateFromDate);
			Scr_Rnl_BookingfromDate.add(scr_rnlEstateFromDate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(Rnl_BookingfromDate.get(CurrentinvoCount), Scr_Rnl_BookingfromDate.get(CurrentinvoCount), "Applicant Address Line One does not match with the actual in scrutiny View form");
			}

			String scr_rnlEstateToDate=mGetText("id", mGetPropertyFromFile("rnl_RrRenewalTodateid"),"value");
			System.out.println("Applicant Title is in Scutiny : "+scr_rnlEstateToDate);
			Scr_Rnl_BookingtoDate.add(scr_rnlEstateToDate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(Rnl_BookingtoDate, Scr_Rnl_BookingtoDate, "Applicant Address Line One does not match with the actual in scrutiny View form");
				mCustomWait(1000);
			}

			String scr_rnlEstateRemarks=mGetText("id", mGetPropertyFromFile("rrl_ScrRemarksid"),"value");
			System.out.println("Applicant Title is in Scutiny : "+scr_rnlEstateRemarks);
			Scr_Rnl_ApplicantRemarks.add(scr_rnlEstateRemarks);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(Rnl_ApplicantRemarks, Scr_Rnl_ApplicantRemarks, "Applicant Address Line One does not match with the actual in scrutiny View form");
			}

			Tblread_RNL_Scrutiny_Renewal_Details();
			mCustomWait(1000);


			String scr_rnlContractAmt=mGetText("id", mGetPropertyFromFile("rrl_ScrContractAmtid"),"value");
			System.out.println("Applicant Title is in Scutiny : "+scr_rnlContractAmt);
			Scr_TC_ContractAmount.add(scr_rnlContractAmt);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(TC_ContractAmount.get(CurrentinvoCount), Scr_TC_ContractAmount.get(CurrentinvoCount), "Applicant Address Line One does not match with the actual in scrutiny View form");
			}

			String scr_rnlContractDiscount=mGetText("id", mGetPropertyFromFile("rnl_TgContractDiscountValueid"),"value");
			System.out.println("Applicant Title is in Scutiny : "+scr_rnlContractDiscount);
			Scr_TC_DiscountAmount.add(scr_rnlContractDiscount);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(TC_DiscountAmount.get(CurrentinvoCount), Scr_TC_DiscountAmount.get(CurrentinvoCount), "Applicant Address Line One does not match with the actual in scrutiny View form");
			}

			String scr_rnlPaybleAmt=mGetText("id", mGetPropertyFromFile("rnl_TgContractPayableAmountid"),"value");
			System.out.println("Applicant Title is in Scutiny : "+scr_rnlPaybleAmt);
			Scr_TC_PayableAmount.add(scr_rnlPaybleAmt);
			int scrupdatedpayble = Integer.parseInt(scr_rnlPaybleAmt);
			System.out.println("updatedpayble=>"+scrupdatedpayble);
			scrupdatedpayble_list=scrupdatedpayble;


			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(TC_PayableAmount, Scr_TC_PayableAmount, "Applicant Address Line One does not match with the actual in scrutiny View form");
			}

			String scr_rnlRoundOffAmt=mGetText("id", mGetPropertyFromFile("rnl_TgContractRoundoffAmountid"),"value");
			System.out.println("Applicant Title is in Scutiny : "+scr_rnlRoundOffAmt);
			Scr_TC_RoundOffAmount.add(scr_rnlRoundOffAmt);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(TC_RoundOffAmount, Scr_TC_RoundOffAmount, "Applicant Address Line One does not match with the actual in scrutiny View form");
			}

			Tblread_RNL_Scrutiny_Renewal_Details();
			mCustomWait(1000);



			/*if(abc.equalsIgnoreCase("Renewal of Rent Contract") && scrutinylevelcounter==2)
				{
					String scr_rnlApprovedBy=mGetText("id", mGetPropertyFromFile("rnl_View_ApprovedByid"),"value");
					System.out.println("Approved By : "+scr_rnlApprovedBy);


					String scr_rnlApprovedDate=mGetText("id", mGetPropertyFromFile("rnl_View_ApprovedDateid"),"value");
					System.out.println("Approved By : "+scr_rnlApprovedDate);

					String scr_rnlApprovedRemarks=mGetText("id", mGetPropertyFromFile("rnl_View_ApprovedRemarkid"),"value");
					System.out.println("Approved By : "+scr_rnlApprovedRemarks);

				}*/


		}
		catch (Exception e) 
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in RNL_ScrutinyView_Assertion method");
		}
	}

	//RNL Scrutiny table Reading for Booking of Estate 06 Jan 2017 
	public  void  Tblread_RNL_Scrutiny_Booking_Details()
	{
		try {
			// Capturing searched Land/Estate details
			WebElement table = driver.findElement(By.id("gview_gridEstateBookingRnL"));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount = rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

			int Rowno = 0;
			for (int a=0;a<rwcount;a++) 
			{
				List<WebElement> cols = rows.get(a).findElements(By.xpath("td"));
				int colcount = cols.size();
				System.out.println("NUMBER OF COLUMNS IN THIS TABLE = " + colcount);
				for(int b=0;b<colcount;b++)
				{
					if(a>=1)
					{
						if(b==0)
						{
							selectedLandEstateName = rows.get(a).findElement(By.xpath("td[1]")).getText();
							System.out.println("Land/Estate Name is : "+selectedLandEstateName);
						}
						if(b==1)
						{
							selectedLandEstateCode = rows.get(a).findElement(By.xpath("td[2]")).getText();
							System.out.println("Land/Estate Code is : "+selectedLandEstateCode);
						}
						if(b==2)
						{
							selectedLandEstateAddr = rows.get(a).findElement(By.xpath("td[3]")).getText();
							System.out.println("Land Estate Address is : "+selectedLandEstateAddr);
						}
						if(b==3)
						{
							selectedLandEstateType = rows.get(a).findElement(By.xpath("td[4]")).getText();
							System.out.println("Land Estate Type is : "+selectedLandEstateType);
						}
						if(b==4)
						{
							selectedLandEstateSubType = rows.get(a).findElement(By.xpath("td[5]")).getText();
							System.out.println("Land Estate Sub Type is : "+selectedLandEstateSubType);
						}
						if(b==5)
						{
							selectedBookingAmt = rows.get(a).findElement(By.xpath("td[6]")).getText();
							System.out.println("Booking Amount is : "+selectedBookingAmt);
						}

					}
				}

			}

			/*mAssert(addedLandEstateName, selectedLandEstateName, "Land/Estate name does not matches, Actual is : "+db_landEstateName+"Expected is : "+selectedLandEstateName);
			mAssert(addedLandEstateCode, selectedLandEstateCode, "Land/Estate code does not matches, Actual is : "+db_landEstateCode+"Expected is : "+selectedLandEstateCode);
			mAssert(addedLandEstateAddr, selectedLandEstateAddr, "Land/Estate address does not matches, Actual is : "+db_landEstateAddr+"Expected is : "+selectedLandEstateAddr);
			mAssert(addedLandEstateType, selectedLandEstateType, "Land/Estate type does not matches, Actual is : "+db_landEstateType+"Expected is : "+selectedLandEstateType);
			mAssert(db_landEstateSubType, selectedLandEstateSubType, "Land/Estate sub type does not matches, Actual is : "+db_landEstateSubType+"Expected is : "+selectedLandEstateSubType);
			 */
		}
		catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in Tblread_RNL_Scrutiny_Booking_Details method");
		}		

	}

	//RNL Scrutiny table Reading for Renewal of Rent Contract 06 Jan 2017 
	public  void  Tblread_RNL_Scrutiny_Renewal_Details()
	{
		try {
			// Capturing searched Land/Estate details
			WebElement table = driver.findElement(By.id("gview_gridEstateDetailEdit"));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount = rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

			int Rowno = 0;
			for (int a=0;a<rwcount;a++) 
			{
				List<WebElement> cols = rows.get(a).findElements(By.xpath("td"));
				int colcount = cols.size();
				System.out.println("NUMBER OF COLUMNS IN THIS TABLE = " + colcount);
				for(int b=0;b<colcount;b++)
				{
					if(a>=1)
					{
						if(b==0)
						{
							selectedLandEstateName = rows.get(a).findElement(By.xpath("td[1]")).getText();
							System.out.println("Land/Estate Name is : "+selectedLandEstateName);
						}
						if(b==1)
						{
							selectedLandEstateCode = rows.get(a).findElement(By.xpath("td[2]")).getText();
							System.out.println("Land/Estate Code is : "+selectedLandEstateCode);
						}
						if(b==2)
						{
							selectedLandEstateAddr = rows.get(a).findElement(By.xpath("td[3]")).getText();
							System.out.println("Land Estate Address is : "+selectedLandEstateAddr);
						}
						if(b==3)
						{
							selectedLandEstateType = rows.get(a).findElement(By.xpath("td[4]")).getText();
							System.out.println("Land Estate Type is : "+selectedLandEstateType);
						}
						if(b==4)
						{
							selectedLandEstateSubType = rows.get(a).findElement(By.xpath("td[5]")).getText();
							System.out.println("Land Estate Sub Type is : "+selectedLandEstateSubType);
						}
						if(b==5)
						{
							selectedBookingAmt = rows.get(a).findElement(By.xpath("td[6]")).getText();
							System.out.println("Booking Amount is : "+selectedBookingAmt);
						}

					}
				}

			}

			/*mAssert(addedLandEstateName, selectedLandEstateName, "Land/Estate name does not matches, Actual is : "+db_landEstateName+"Expected is : "+selectedLandEstateName);
				mAssert(addedLandEstateCode, selectedLandEstateCode, "Land/Estate code does not matches, Actual is : "+db_landEstateCode+"Expected is : "+selectedLandEstateCode);
				mAssert(addedLandEstateAddr, selectedLandEstateAddr, "Land/Estate address does not matches, Actual is : "+db_landEstateAddr+"Expected is : "+selectedLandEstateAddr);
				mAssert(addedLandEstateType, selectedLandEstateType, "Land/Estate type does not matches, Actual is : "+db_landEstateType+"Expected is : "+selectedLandEstateType);
				mAssert(db_landEstateSubType, selectedLandEstateSubType, "Land/Estate sub type does not matches, Actual is : "+db_landEstateSubType+"Expected is : "+selectedLandEstateSubType);
			 */
		}
		catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in Tblread_RNL_Scrutiny_Booking_Details method");
		}		

	}

	public void RNL_checklist_verification_asserion(){
		try{

			String clvApplicantName=mGetText("css", mGetPropertyFromFile("ClvApplicantNameid"));
			System.out.println("Name of Applicant in checklist verification form is : " +clvApplicantName);
			clv_ApplicantName.add(clvApplicantName.trim());
			//mAssert(clv_ApplicantName, AppfullnmWdTitle, "Name of Applicant in checklist verification form doen not match with the expected");

			String clvApplicantionNo=mGetText("css", mGetPropertyFromFile("ClvApplicantionNoid"));
			System.out.println("Application No in checklist verification form is : " +clvApplicantionNo);
			clv_ApplicanyionNo.add(clvApplicantionNo.trim());
			//mAssert(clv_ApplicanyionNo, ApplicationNoforLandDev, "Application No in checklist verification form does not match with the Expected"+ApplicationNoforLandDev+",Actual"+clv_ApplicanyionNo);

			String clvServicenameName=mGetText("css", mGetPropertyFromFile("ClvAServiceNameid"));
			System.out.println("Service Name in checklist verification form is : " +clvServicenameName);
			clv_ServicenameName.add(clvServicenameName.trim());
			//mAssert(clv_ServicenameName,ServiceNameforTownPlanning, "Name of Applicant in checklist verification form doen not match with the expected");

			String clvApplicantDate=mGetText("css", mGetPropertyFromFile("ClvApplicantionDateid"));
			System.out.println("Application date in checklist verification form is : " +clvApplicantDate);
			clv_ApplicantionDate.add(clvApplicantDate.trim());
			//mAssert(clv_ApplicantName,clv_ApplicantionDate,"Application date in checklist verification form doen not match with the expected");



			switch(clvServicenameName) 
			{
			case "Booking of Estate" :


				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					mAssert(clvServicenameName, RnlServiceName, "Name of Service in checklist verification form doen not match with the expected::: "+clvServicenameName+"Actual:::"+RnlServiceName);					
					mAssert(clvApplicantDate,strDate,"Application date in checklist verification form does not match with the expected::: "+clvApplicantDate+"Actual:::"+strDate);
					mAssert(clv_ApplicantName.get(CurrentinvoCount),Rnl_ApplicantFullname.get(CurrentinvoCount), "Service Name in checklist verification form does not match with the expected::: "+Rnl_ApplicantFullname.get(CurrentinvoCount)+"Actual:::"+clv_ApplicantName.get(CurrentinvoCount));
					//mAssert(clv_ApplicanyionNo.get(CurrentinvoCount), ApplicationNoRNL.get(CurrentinvoCount), "Application No in checklist verification form does not match with the Expected"+ApplicationNoRNL+",Actual"+clv_ApplicanyionNo);
				}

				break;
			case  "Rent Renewal Contract" :

				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					mAssert(clvServicenameName, RnlServiceName, "Name of Service in checklist verification form doen not match with the expected::: "+clvServicenameName+"Actual:::"+RnlServiceName);					
					mAssert(clvApplicantDate,strDate,"Application date in checklist verification form does not match with the expected::: "+clvApplicantDate+"Actual:::"+strDate);
					mAssert(clv_ApplicantName.get(CurrentinvoCount),Rnl_ApplicantFullname.get(CurrentinvoCount), "Service Name in checklist verification form does not match with the expected::: "+Rnl_ApplicantFullname.get(CurrentinvoCount)+"Actual:::"+clv_ApplicantName.get(CurrentinvoCount));
					//mAssert(clv_ApplicanyionNo.get(CurrentinvoCount), ApplicationNoRNL.get(CurrentinvoCount), "Application No in checklist verification form does not match with the Expected"+ApplicationNoRNL+",Actual"+clv_ApplicanyionNo);
				}
				break;

			}


		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();	
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Checklist Verification(Assertion) method failed");
		}
	}


	public void Scrutiny_assertion()
	{
		try{

			//getting Applicant name and assertion
			String scrutiny_appName=mGetText("css", mGetPropertyFromFile("Sc_scrutinyProcessAppNameid"));
			System.out.println(Scrutiny_ApplicantName.add(scrutiny_appName));
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Scrutiny_ApplicantName.get(CurrentinvoCount),Rnl_ApplicantFullname.get(CurrentinvoCount), "Name of Applicant in Scrutiny view form is form doen not match with the expected");
			}
			//getting Applicantion No and assertion
			String scrutiny_appNo=mGetText("css", mGetPropertyFromFile("Sc_scrutinyProcessAppNoid"));
			System.out.println("Applicantion no is in Scutiny : "+scrutiny_appNo);
			Scrutiny_ApplicantionNo.add(scrutiny_appNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Scrutiny_ApplicantionNo.get(CurrentinvoCount), ApplicationNoRNL.get(CurrentinvoCount), "In Scrutiny form Applicantion No dose not match with the Expected ::"+ApplicationNoRNL.get(CurrentinvoCount) + "Actual::"+Scrutiny_ApplicantionNo.get(CurrentinvoCount));

			}
			else
			{
				mAssert(scrutiny_appNo,mGetPropertyFromFile("applicationNo"), "In Scrutiny form Applicantion No dose not match with the Expected ::"+scrutiny_appNo+ "Actual::"+mGetPropertyFromFile("applicationNo"));
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();	
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Checklist Verification(Assertion) method failed");
		}
	}


	///////////////////////////////////////////////////Scrutiny Process//////////////////////////////////////////////////////////////////////////////	
	public void rnl_Gen_LOILetter()
	{
		try
		{   
			mSendKeys("id", textid, mGetPropertyFromFile("rnl_LOIGenGenerateLoidata"));


			if(mGetPropertyFromFile("rnl_LOIGenGenerateLoidata").equalsIgnoreCase("yes")) 
			{
				mCustomWait(1000);	
				mClick("css", imageid);
				System.out.println("Service Name is:::"+RnlServiceName);
				/*mCustomWait(2500);
				if(RnlServiceName.equalsIgnoreCase("Renewal Of Rent Contract"))
				{   mCustomWait(2500);
					System.out.println("Service Name is:::"+RnlServiceName);
					mCustomWait(1000);
					mWaitForVisible("css", mGetPropertyFromFile("rnl_LOIGenBackBtnid"));
					mClick("css", mGetPropertyFromFile("rnl_LOIGenBackBtnid"));
					mCustomWait(2000); 
					LOiGenration=false;
				}

				else
				{	*/
				System.out.println("Service Name is:::"+RnlServiceName);		
				mCustomWait(2500);
				String number=mGetText("id", mGetPropertyFromFile("rnl_LOIGenAppNoid"), "value");
				System.out.println("The application no. is : "+number);

				if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
				{
					mAssert(number, applicationNo.get(CurrentinvoCount).toString(), "Application Number Does Not Matches at rnl_loi generation.    Actual  :"+number+"     Expected     :"+applicationNo.get(CurrentinvoCount).toString());
				}
				else
				{
					mAssert(number, mGetPropertyFromFile("applicationNo"), "Application Number Does Not Matches at rnl_loi generation.    Actual  :"+number+"     Expected     :"+mGetPropertyFromFile("applicationNo"));
				}

				String loidate=mGetText("id",  mGetPropertyFromFile("rnl_LOIGenDateNoid"), "value");
				Scrutiny_LoiDate.add(loidate);
				System.out.println("The LOI date in Scrutiny form. is : "+Scrutiny_LoiDate);

				mCustomWait(1000);
				mSendKeys("id",mGetPropertyFromFile("rnl_LOIGenLOIRemarksid"),mGetPropertyFromFile("rnl_LOIGenLOIRemarksdata"));
				System.out.println("The LOI date in Scrutiny form. is : "+Scrutiny_LoiDate);

				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("rnl_LOIGenSelectChargesid"));

				/*String loiamt=mGetText("id", mGetPropertyFromFile("rnl_LpLOIAmountid"), "value");
                    Scrutiny_Totalamt.add(loiamt);*/
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("rnl_LOIGenFormSubBtnid"));
				mCustomWait(2000);		
			}

			/*	}*/
			/*else{
				System.out.println(RNLserviceName);
			}*/

		}			

		catch(Exception e)
		{
			e.printStackTrace();

		}

	}	

	public void rnl_Gen_RejectionLetter()
	{
		try
		{
			mSendKeys("id", textid, mGetPropertyFromFile("rnl_LOIGenGenerateRejLetterdata"));

			if(mGetPropertyFromFile("rnl_LOIGenGenerateRejLetterdata").equalsIgnoreCase("no")) 
			{
				mCustomWait(1200);
				mClick("css", imageid);

				//selecting rejection list
				mWaitForVisible("id", mGetPropertyFromFile("rnl_LOIGenRejRemarksDocListid"));
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("rnl_LOIGenRejRemarksDocListid"));
				mCustomWait(1000);				

				mSendKeys("id", mGetPropertyFromFile("rnl_LOIGenRejRemarkid"), mGetPropertyFromFile("rnl_LOIGenRejRemarkdata"));
				SCR_RejectionRemarks.add(mGetPropertyFromFile("rnl_LOIGenRejRemarkdata"));

				//Submit Button
				mWaitForVisible("xpath", mGetPropertyFromFile("rnl_LOIGenRejAppSubBtnid"));
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("rnl_LOIGenRejAppSubBtnid"));
				mCustomWait(1000);


			}

		}

		catch(Exception e)
		{
			e.printStackTrace();

		}

	}	



	public void rnl_View_ModifyApp()
	{
		try



		{
			mClick("css", imageid);

			if(mGetPropertyFromFile("rnl_View_ModifyContractYesAmountdata").equalsIgnoreCase("Yes")){
				mWaitForVisible("id", mGetPropertyFromFile("rnl_View_ModifyContractAmountid")); 			
				mSendKeys("id",mGetPropertyFromFile("rnl_View_ModifyContractAmountid"),mGetPropertyFromFile("rnl_View_ModifyContractAmountdata"));
			}
			mCustomWait(1000);
			if(mGetPropertyFromFile("rnl_View_ModifyGiveDiscount").equalsIgnoreCase("Yes")){
				mSendKeys("id",mGetPropertyFromFile("rnl_View_ModifyDiscountAmountid"),mGetPropertyFromFile("rnl_View_ModifyDiscountAmountdata"));
			}
			mCustomWait(1000);


			mClick("css", mGetPropertyFromFile("rnl_View_ModifyAmtBifurBtnid"));
			mWaitForVisible("css", mGetPropertyFromFile("rnl_View_ModifyAmtBifurPopUpBoxid")); 			
			mWaitForVisible("css", mGetPropertyFromFile("rnl_View_ModifyAmtBifurAddBtnid")); 
			mClick("id", mGetPropertyFromFile("rnl_View_ModifyAmtBifurAddBtnid"));

			mCustomWait(1000);
			if(mGetPropertyFromFile("rnl_View_ModifyContractYesAmountdata").equalsIgnoreCase("Yes")){
				mWaitForVisible("id", mGetPropertyFromFile("rnl_View_ModifyAmtBifurSelectTaxDescid")); 	
				mSelectDropDown("id",mGetPropertyFromFile("rnl_View_ModifyAmtBifurSelectTaxDescid"),mGetPropertyFromFile("rnl_View_ModifyAmtBifurSelectTaxDescdata"));
				mCustomWait(1000);
				mSendKeys("id",mGetPropertyFromFile("rnl_View_ModifyAmtBifurTaxAmountid"),mGetPropertyFromFile("rnl_View_ModifyAmtBifurTaxAmountdata"));
				mCustomWait(1000);
			}

			if(mGetPropertyFromFile("rnl_View_ModifyGiveDiscount").equalsIgnoreCase("yes"))
			{
				mClick("id", mGetPropertyFromFile("rnl_View_ModifyAmtBifurAddBtnid"));
				mCustomWait(1000);
				mWaitForVisible("id", mGetPropertyFromFile("rnl_View_ModifyAmtBifurSelectTaxDescid")); 	
				mSelectDropDown("id",mGetPropertyFromFile("rnl_View_ModifyAmtBifurSelectTaxDescid"),mGetPropertyFromFile("rnl_View_ModifyAmtBifurSelectTaxDescdata1"));
				mCustomWait(1000);
				mSendKeys("id",mGetPropertyFromFile("rnl_View_ModifyAmtBifurTaxAmountid"),mGetPropertyFromFile("rnl_View_ModifyAmtBifurTaxAmountdata1"));
				mCustomWait(1000);
			}


			mClick("css", mGetPropertyFromFile("rnl_View_ModifyAmtBifurSubmitBtnid"));
			mCustomWait(1000);

			mWaitForVisible("css", mGetPropertyFromFile("rnl_View_ModifyPaySchBtnid")); 	
			mClick("css", mGetPropertyFromFile("rnl_View_ModifyPaySchBtnid"));
			mCustomWait(3000);
			mSelectDropDown("id",mGetPropertyFromFile("rnl_View_ModifyPaySchSelectPaySchid"),mGetPropertyFromFile("rnl_View_ModifyPaySchSelectPaySchdata"));
			mCustomWait(2000);

			//Capturing Payment Schedule Data 
			String	schedule=mGetPropertyFromFile("rnl_View_ModifyPaySchSelectPaySchdata");

			mCustomWait(500);
			if (schedule.equalsIgnoreCase("Monthly")||schedule.equalsIgnoreCase("Bi Monthly")||schedule.equalsIgnoreCase("Quarterly")||schedule.equalsIgnoreCase("Half Yearly")||schedule.equalsIgnoreCase("Yearly")) {
				System.out.println("<====i m in payment shedule for"+ schedule +"Pattern====>" );
				if (mGetPropertyFromFile("Mod_paymentschedule").equalsIgnoreCase("yes")) {
					ScrutinyPaymentSchedule();
				}

			}else {
				System.out.println("<====i m in payment shedule for"+"Other mode"+"Pattern====>" );
				ScrutinyPaymentSchedule_Other();
			}
			mCustomWait(500);
			mClick("css", mGetPropertyFromFile("rnl_View_ModifyPaySchSubmitBtnid"));
			mCustomWait(1000);
			mWaitForVisible("css", mGetPropertyFromFile("rnl_View_ModifyContTermsBtnid")); 	
			mClick("css", mGetPropertyFromFile("rnl_View_ModifyContTermsBtnid"));


			mWaitForVisible("css", mGetPropertyFromFile("rnl_View_ModifyContTermsPopUpBoxid")); 	

			/////////////////////swap table chekbox/////////////////
			String no_of_cheks=mGetPropertyFromFile("rnl_View_ModifyContTermsSelectTermCount");
			System.out.println("no_of_cheks==>"+no_of_cheks);

			String [] j=no_of_cheks.split(",");
			for (int i = 0; i < j.length; i++)
			{
				int	k=Integer.parseInt(j[i])-1;

				System.out.println("k==>"+k);
				String chkbox="ctChkBox"+k;
				System.out.println("chkbox==>"+chkbox);
				mCustomWait(500);
				driver.findElement(By.id(chkbox)).click();
				mCustomWait(500);
			}

			mCustomWait(2000);	
			////////////////////////end of chek bpoox///////////////


			mClick("css", mGetPropertyFromFile("rnl_View_ModifyContTermsSubmitBtnid"));
			mCustomWait(1000);

			mscroll(0,300);

			mClick("id", mGetPropertyFromFile("rnl_View_ModifyApprovedChkboxid"));
			mCustomWait(1000);

			mSendKeys("id",mGetPropertyFromFile("rnl_View_ModifyRemarkid"),mGetPropertyFromFile("rnl_View_ModifyRemarkdata"));
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("rnl_View_ModifySubmitBtnid"));
			mCustomWait(1000);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	////////////////////////////////////////////////////////////Payment Challan verification RNL//////////////////////////
	public void RnlchallanVerification(String mode,String Service,String payMode ,String nameOfBank,String accountNo,String ChequeDDNo,String cDate) throws InterruptedException, IOException
	{

		try
		{
			System.out.println(mGetPropertyFromFile(cDate));
			boolean RTI_Application_Fee = false;
			boolean RTI_LOI_Collection = false;
			String TransctnId = null;
			//modeForChallan=mode;
			// for bank
			System.out.println("Class Name is:::"+myClassName);
			if(mGetPropertyFromFile(mode).contains("BANK")||mGetPropertyFromFile(mode).contains("bank")||mGetPropertyFromFile(mode).contains("Bank"))
			{

				mNavigation("chlanVerifctnLinkId", "chlanTransactionsId", "challanVerificationId");

				//new code
				mGenericWait();
				mWaitForVisible("id", mGetPropertyFromFile("chlanServiceId"));

				//mSelectDropDown("id", mGetPropertyFromFile("chlanServiceId"), Service);
				mSendKeys("id", mGetPropertyFromFile("cfc_challanVerChallanNoDirectid"), ChallannoRNL.get(CurrentinvoCount));

				if(Service.equals("RTI LOI Collection")) 
				{ 
					RTI_LOI_Collection=true;
				}

				mWaitForVisible("css",mGetPropertyFromFile("chlanSearchBtnId"));
				mGenericWait();
				mClick("css", mGetPropertyFromFile("chlanSearchBtnId"));

				mCustomWait(8000);

				mTakeScreenShot();

				mGenericWait();
				System.out.println("RTI_LOI_Collection value is:: "+RTI_LOI_Collection);
				mGenericWait();
				mTakeScreenShot();

				mGenericWait();

				mCustomWait(2000);
				mTakeScreenShot();

				mClick("xpath", mGetPropertyFromFile("chlanViewDetailimgId"));

				mGenericWait();
				mSendKeys("id",mGetPropertyFromFile("chlanBankTransId"),ChallannoRNL.get(CurrentinvoCount));

				mTab("id",mGetPropertyFromFile("chlanBankTransId"));
				mGenericWait();
				String tokens[] = mGetPropertyFromFile(cDate).split("/");
				System.out.println("i m selecting a date");
				String strtempdate= tokens[0];

				int cuDate=Integer.parseInt(strtempdate);
				String strnDate=Integer.toString(cuDate);
				//entity.challanRcvdDate
				System.out.println(tokens[2]);
				System.out.println(tokens[1]);
				System.out.println(strnDate);
				mGdatePicker(mGetPropertyFromFile("chlanDatPickrByBankId"),tokens[2],tokens[1],strnDate);

				mTab("id",mGetPropertyFromFile("chlanDatPickrByBankId"));
				mTakeScreenShot();

				//submit button
				mWaitForVisible("css",mGetPropertyFromFile("chlanSubBtnId"));
				mGenericWait();
				mClick("css",mGetPropertyFromFile("chlanSubBtnId"));

				mGenericWait();
				mTakeScreenShot();
				if(chllanForRTIServices)
				{

				}
				//proceed button
				mWaitForVisible("id",mGetPropertyFromFile("chalanProceedBtnId"));
				mGenericWait();
				mClick("id",mGetPropertyFromFile("chalanProceedBtnId"));

			}
			else if(mGetPropertyFromFile(mode).contains("ULB")||mGetPropertyFromFile(mode).contains("ulb")||mGetPropertyFromFile(mode).contains("Ulb"))
			{
				mNavigation("chlanVerifctnLinkId","chlanTransactionsId","chlanAtULBCounterlinkId");

				//searching for RTI Services
				System.out.println(chllanForRTIServices);
				if(chllanForRTIServices){
					System.out.println(LOIAPPLICABLE);
					if(LOIAPPLICABLE)
					{
						if(mGetPropertyFromFile("challanAtULBCounterSearchByAppNo").equalsIgnoreCase("Yes"))
						{
							System.out.println("Please 1211");
							mSendKeys("id",mGetPropertyFromFile("chlanAtUlbAppNoId"),LOINO);
							LOIAPPLICABLE=false;
							mGenericWait();
						}
						else if(mGetPropertyFromFile("challanAtULBCounterSearchByChallanNo").equalsIgnoreCase("Yes"))
						{
							if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
							{
								System.out.println("Please 1");
								mGenericWait();
								mSendKeys("id",mGetPropertyFromFile("cfc_challanVerChallanNoid"),challanno.get(CurrentinvoCount));
								LOIAPPLICABLE=false;
								mGenericWait();
								System.out.println("Please 1");

							}
							else
							{
								System.out.println("Please 2");
								mGenericWait();
								mSendKeys("id",mGetPropertyFromFile("cfc_challanVerChallanNoid"),mGetPropertyFromFile("applicationNo"));
								LOIAPPLICABLE=false;
								mGenericWait();
								System.out.println("Please 2");
							}
						}
						else
						{

							System.out.println("Please select search criteria");
						}
					}
					else
					{	System.out.println("I am here");
					IndOrDep("id", "chlanAtUlbAppNoId", "applicationNo");
					mGenericWait();


					}
				}


				//Searching for BND Services
				if(chllanVerReqForServices){
					mGenericWait();
					System.out.println("I BND am here");
					//IndOrDep("id", "chlanAtUlbAppNoId", "applicationNo");
					mSendKeys("id",mGetPropertyFromFile("cfc_challanVerChallanNoid"),ChallannoRNL.get(CurrentinvoCount));
					mGenericWait();
				}

				mClick("css", mGetPropertyFromFile("chlanAtULBsearchBtnId"));
				mCustomWait(8000);
				mGenericWait();
				mTakeScreenShot();

				//selecting payment mode for ULB
				mGenericWait();
				mSelectDropDown("id",mGetPropertyFromFile("chlanAtULBpayModeInId"),mGetPropertyFromFile(payMode));
				String paymentMode=mGetPropertyFromFile(payMode);
				mGenericWait();
				if(mGetPropertyFromFile(payMode).equals("Cash"))
				{
					System.out.println("no data required for cash payment");
				}
				else
				{
					// for selcting a bank
					mGenericWait();

					//selecting a name of bank
					mGenericWait();
					mSelectDropDown("id", mGetPropertyFromFile("chlanAtULBBanklinkId"),mGetPropertyFromFile(nameOfBank));
					mGenericWait();
					String drawnOnbank = mGetPropertyFromFile(nameOfBank);

					//for selecting account number
					mGenericWait();
					mSendKeys("id",mGetPropertyFromFile("chlanAtULBAccNoId"),mGetPropertyFromFile(accountNo));
					String accntNo=driver.findElement(By.id(mGetPropertyFromFile("chlanAtULBAccNoId"))).getAttribute("value");

					mGenericWait();
					mSendKeys("id",mGetPropertyFromFile("chlanAtULBChkId"),mGetPropertyFromFile(ChequeDDNo));
					String chqDDPONo=driver.findElement(By.id(mGetPropertyFromFile("chlanAtULBChkId"))).getAttribute("value");

					/// for selecting date

					mTab("id",mGetPropertyFromFile("chlanAtULBChkId"));

					mCustomWait(2000);
					mDateSelect("name",mGetPropertyFromFile("chlanDatPickrByULBId"), mGetPropertyFromFile(cDate));
					String chqDDPODate=driver.findElement(By.name(mGetPropertyFromFile("chlanDatPickrByULBId"))).getAttribute("value");
					driver.findElement(By.name("offline.bmChqDDDate")).sendKeys(Keys.TAB);
					mGenericWait();
				}
				mTakeScreenShot();
				//clicking submit button
				mWaitForVisible("xpath", mGetPropertyFromFile("chlanAtULBSubmitBtnId"));
				mClick("xpath", mGetPropertyFromFile("chlanAtULBSubmitBtnId"));

				mCustomWait(1000);
				mTakeScreenShot();

				//proceed button
				mWaitForVisible("id",mGetPropertyFromFile("chlanAtULBProceedBtnId"));
				mTakeScreenShot();
				mGenericWait();
				mClick("id", mGetPropertyFromFile("chlanAtULBProceedBtnId"));

				mGenericWait();

				mGenericWait();
				//mSwitchTabs();
				mCustomWait(10000);
				mChallanPDFReader();
				//api.PdfPatterns.patternULBChallanReceiptPdf(pdfoutput);
				//mAssert(Rcptamt, challanAmt, "Amount does not match, Expected:"+ challanAmt+" || Actual:"+Rcptamt);

			}
			else
			{
				System.out.println("invalid input");
			}
			//mSwitchTabs();
			System.out.println(	mode);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in challanVerification method");
		}
	}

	public void isrentPaymentChallanVerftnRequired(String Service) throws InterruptedException
	{
		try
		{

			//For Rent and lease services
			//if(challanForRNLServices)
			if(chllanVerReqForServices)
			{
				departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
				RnlchallanVerification(modeForChallan, Service, "chlanAtULBTypeOfPayModeId", "chlanAtULbNameOfBankId","rti_chlanVerftnAccNo","rti_chlanVerftnCheDDNo","rti_chlanVerftnDate");
                if(mGetPropertyFromFile("byBankOrULB ").equalsIgnoreCase("Pay by Challan@Ulb")){
				api.PdfPatterns.rnlpatternChallanPdf(pdfoutput);
				}
				System.out.println("modeForChallan"+modeForChallan);
				chllanVerReqForServices=false;
				logOut();
				finalLogOut();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in isChallanVerftnRequired method");
		}

	}


	//Payment Scedule Method for All Services having Monthly /BiMonthly etc
	public void PaymentSchedule() throws InterruptedException{
		try{

			//*[@id=\"frmHoardingAndAdvertisementSetup1\"]/table/tbody/tr
			noofmonths=((driver.findElements(By.xpath("//*[@id=\"frmHoardingAndAdvertisementSetup1\"]/table/tbody/tr")).size())-2)/3;
			//noofmonths=((driver.findElements(By.xpath("//*[@id=\"frmRenewalOfContract2\"]/table/tbody/tr")).size())-1)/3;
			System.out.println("no of noofmonths==>+"+noofmonths);

			System.out.println("no of noofmonths==>+"+noofmonths);
			/*	System.out.println("no of months==>+"+noofmonths);
					System.out.println("no of months==>+"+noofmonths);*/
			int sum=0;
			int sum1=0;
			int bf_amt=0;
			int lastamt=0;
			int noofvalues_entered=0;
			int updatedremainnig_no=0;
			String divisible="no";
			for (int i = 0; i < noofmonths; i++) {

				String dateid="entity.contractPaySchedule"+i+".acsScheduleTodate";
				String amount="amount_"+i;
				System.out.println("dateid==============>"+dateid);
				int ii=i+1;
				String s=mGetPropertyFromFile("Amount_row"+ii);
				System.out.println("s==============>"+s);
				if (s.equalsIgnoreCase("?")||s.equalsIgnoreCase("")) {
					adh_rowstobefilled.add(0);
					driver.findElement(By.id(amount)).clear();
					System.out.println("adh_rowstobefilled=>"+adh_rowstobefilled);


				}else {

					int s1 = Integer.parseInt(s);

					adh_rowstobefilled.add(s1);
					driver.findElement(By.id(amount)).clear();
					driver.findElement(By.id(amount)).sendKeys(s);
					System.out.println("adh_rowstobefilled=>"+adh_rowstobefilled);
					noofvalues_entered++;
					indexneedtobesckipped.add(i);
				}
				sum=sum+adh_rowstobefilled.get(i);
				System.out.println("sum==>"+sum);

			}
			System.out.println("Final adh_rowstobefilled=>"+adh_rowstobefilled);

			//int remaing=updatedpayble_list.get(CurrentinvoCount)-sum;
			int remaing=updatedpayble_list-sum;
			int remainnig_no=noofmonths-noofvalues_entered;
			System.out.println("remaing==>"+remaing);
			System.out.println("remainnig_no==>"+remainnig_no);
			//remainnig_no

			//////////////////////////// bifergation /// of amount 

			for (int j = 1; j < remaing; j++) {


				if (remaing%remainnig_no==0) {

					bf_amt=(remaing/remainnig_no);
					lastamt=bf_amt;
					divisible="Yes";
					break;
				}else {
					updatedremainnig_no=remainnig_no-1;
					sum1=updatedremainnig_no*j;
					int a=j;
					int l=remaing-sum1;
					if ((sum1>remaing)) {

						break;
					}else {
						if (l>0) {
							bf_amt=a;
							lastamt=l;
						}


					}
				}
			}
			System.out.println("final bifergation amount==>"+bf_amt);
			System.out.println("diffrence amount==>"+lastamt);
			int count=0;
			int sno=noofmonths-indexneedtobesckipped.size();

			System.out.println("indexneedtobesckipped.size()==>"+indexneedtobesckipped.size());

			System.out.println("sno====>"+sno);
			for (int i = 0; i < noofmonths; i++) {

				String dateid="entity.contractPaySchedule"+i+".acsScheduleTodate";
				String amount="amount_"+i;
				if (indexneedtobesckipped.contains(i)) {
					System.out.println("skip iteration i=>"+i);
				}else {
					String str = Integer.toString(bf_amt);
					String str1 = Integer.toString(lastamt);
					//driver.findElement(By.id(amount)).clear();
					if (divisible.equalsIgnoreCase("yes")) {

						driver.findElement(By.id(amount)).sendKeys(str);
					}else {
						count++;
						System.out.println("count====>"+count);
						System.out.println("sno====>"+sno);

						if(sno==count) {
							System.out.println("count====>"+count);
							driver.findElement(By.id(amount)).sendKeys(str1);
							break;
						}
						driver.findElement(By.id(amount)).sendKeys(str);
					}
				}
			}

			indexneedtobesckipped.clear();
			mCustomWait(3000);
			adh_rowstobefilled.clear();


		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in PaymentSchedule_monthly method");
		}
	}	

	public void PaymentSchedule_Other() throws InterruptedException{
		try{
			//*[@id="advPayScheduleTab"]/tbody/tr[1]/th[2]
			//int noofmonths=((driver.findElements(By.xpath("//*[@id=\"frmHoardingAndAdvertisementSetup1\"]/table/tbody/tr")).size())-2)/3;
			int noofmonths=((driver.findElements(By.xpath("//*[@id=\"advPayScheduleTab\"]/tbody/tr")).size())-2)/3;
			String noofemi=mGetPropertyFromFile("No_of_Installments");
			System.out.println("no of installments==>+"+noofemi);
			int NO_OF_EMI=Integer.parseInt(noofemi);
			System.out.println("Integer no of installments==>"+NO_OF_EMI);
			String payDueDate="payDueDate";
			String payAmount="payAmount";

			for (int i = 0; i < NO_OF_EMI; i++) {
				int ii=i+1;
				if (i>=1) {
					mDateSelect("id", payDueDate+1, mGetPropertyFromFile("Date_row"+ii));
					driver.findElement(By.id(payAmount+1)).sendKeys(mGetPropertyFromFile(("Amount_row"+ii)));
					if (i<(NO_OF_EMI-1)) {
						driver.findElement(By.linkText("Add")).click();	
					}

				}else {
					mDateSelect("id", payDueDate+i, mGetPropertyFromFile("Date_row"+ii));
					driver.findElement(By.id(payAmount+i)).sendKeys(mGetPropertyFromFile(("Amount_row"+ii)));
					if (NO_OF_EMI>1) {
						driver.findElement(By.linkText("Add")).click();
					}
				}

			}



		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in PaymentSchedule_Other method");
		}
	}


	//Payment Scedule Method for All Services having Monthly /BiMonthly etc
	public void ScrutinyPaymentSchedule() throws InterruptedException{
		try{


			noofmonths=((driver.findElements(By.xpath("//*[@id=\"frmRenewalOfRentContract2\"]/table/tbody/tr")).size())-1)/3;
			System.out.println("no of noofmonths==>+"+noofmonths);

			System.out.println("no of noofmonths==>+"+noofmonths);
			int sum=0;
			int sum1=0;
			int bf_amt=0;
			int lastamt=0;
			int noofvalues_entered=0;
			int updatedremainnig_no=0;
			String divisible="no";
			for (int i = 0; i < noofmonths; i++) {

				String dateid="entity.contractPaySchedule"+i+".acsScheduleTodate";
				String amount="amount_"+i;
				System.out.println("dateid==============>"+dateid);
				int ii=i+1;
				String s=mGetPropertyFromFile("Amount_row"+ii);
				System.out.println("s==============>"+s);
				if (s.equalsIgnoreCase("?")||s.equalsIgnoreCase("")) {
					adh_rowstobefilled.add(0);
					driver.findElement(By.id(amount)).clear();
					System.out.println("adh_rowstobefilled=>"+adh_rowstobefilled);


				}else {

					int s1 = Integer.parseInt(s);

					adh_rowstobefilled.add(s1);
					driver.findElement(By.id(amount)).clear();
					driver.findElement(By.id(amount)).sendKeys(s);
					System.out.println("adh_rowstobefilled=>"+adh_rowstobefilled);
					noofvalues_entered++;
					indexneedtobesckipped.add(i);
				}
				sum=sum+adh_rowstobefilled.get(i);
				System.out.println("sum==>"+sum);

			}
			System.out.println("Final adh_rowstobefilled=>"+adh_rowstobefilled);

			//int remaing=updatedpayble_list.get(CurrentinvoCount)-sum;
			int remaing=scrupdatedpayble_list-sum;
			int remainnig_no=noofmonths-noofvalues_entered;
			System.out.println("remaing==>"+remaing);
			System.out.println("remainnig_no==>"+remainnig_no);
			//remainnig_no

			//////////////////////////// bifergation /// of amount 

			for (int j = 1; j < remaing; j++) {


				if (remaing%remainnig_no==0) {

					bf_amt=(remaing/remainnig_no);
					lastamt=bf_amt;
					divisible="Yes";
					break;
				}else {
					updatedremainnig_no=remainnig_no-1;
					sum1=updatedremainnig_no*j;
					int a=j;
					int l=remaing-sum1;
					if ((sum1>remaing)) {

						break;
					}else {
						if (l>0) {
							bf_amt=a;
							lastamt=l;
						}


					}
				}
			}
			System.out.println("final bifergation amount==>"+bf_amt);
			System.out.println("diffrence amount==>"+lastamt);
			int count=0;
			int sno=noofmonths-indexneedtobesckipped.size();

			System.out.println("indexneedtobesckipped.size()==>"+indexneedtobesckipped.size());

			System.out.println("sno====>"+sno);
			for (int i = 0; i < noofmonths; i++) {

				String dateid="entity.contractPaySchedule"+i+".acsScheduleTodate";
				String amount="amount_"+i;
				if (indexneedtobesckipped.contains(i)) {
					System.out.println("skip iteration i=>"+i);
				}else {
					String str = Integer.toString(bf_amt);
					String str1 = Integer.toString(lastamt);
					//driver.findElement(By.id(amount)).clear();
					if (divisible.equalsIgnoreCase("yes")) {

						driver.findElement(By.id(amount)).sendKeys(str);
					}else {
						count++;
						System.out.println("count====>"+count);
						System.out.println("sno====>"+sno);

						if(sno==count) {
							System.out.println("count====>"+count);
							driver.findElement(By.id(amount)).sendKeys(str1);
							break;
						}
						driver.findElement(By.id(amount)).sendKeys(str);
					}
				}
			}

			indexneedtobesckipped.clear();
			mCustomWait(3000);
			adh_rowstobefilled.clear();


		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in PaymentSchedule_monthly method");
		}
	}


	//Scrutiny Payment Schedule method For All Services Payment Schedule Mode is "Other"
	public void ScrutinyPaymentSchedule_Other() throws InterruptedException{
		try{
			//*[@id="advPayScheduleTab"]/tbody/tr[1]/th[2]
			//*[@id="frmRenewalOfRentContract2"]/table/tbody/tr
			//int noofmonths=((driver.findElements(By.xpath("//*[@id=\"frmRenewalOfRentContract2\"]/table/tbody/tr")).size())-2)/3;
			int noofmonths=((driver.findElements(By.xpath("//*[@id=\"advPayScheduleTab\"]/tbody/tr")).size())-2)/3;
			System.out.println("Integer no of Months =>"+noofmonths);
			String noofemi=mGetPropertyFromFile("No_of_Installments");
			System.out.println("no of installments==>+"+noofemi);
			int NO_OF_EMI=Integer.parseInt(noofemi);
			System.out.println("Integer no of installments==>"+NO_OF_EMI);
			String payDueDate="payDueDate";
			String payAmount="payAmount";

			for (int i = 0; i < NO_OF_EMI; i++) {
				int ii=i+1;
				if (i>1) {
					mDateSelect("id", payDueDate+1, mGetPropertyFromFile("Date_row"+ii));
					driver.findElement(By.id(payAmount+1)).sendKeys(mGetPropertyFromFile(("Amount_row"+ii)));
					if (i<(NO_OF_EMI-1)) {
						driver.findElement(By.linkText("Add")).click();	
					}

				}else {
					mDateSelect("id", payDueDate+i, mGetPropertyFromFile("Date_row"+ii));
					driver.findElement(By.id(payAmount+i)).sendKeys(mGetPropertyFromFile(("Amount_row"+ii)));
					if (NO_OF_EMI>1) {
						driver.findElement(By.linkText("Add")).click();
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in PaymentSchedule_Other method");
		}
	}

	//Method To add Multiple Hoarding
	public void AddHoarding()
	{
		try{

			mCustomWait(1000);
			//Click on Add Hoarding Button
			mClick("xpath", mGetPropertyFromFile("rnl_RenewalAddHoardingDetailsBtnID"));

			//Search Hoarding by Type
			if(mGetPropertyFromFile("rnl_RenewalSearchAddHoardingTypeYNData").equalsIgnoreCase("yes")){
				mCustomWait(500);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_RenewalAddSendHoardingTypeID"),mGetPropertyFromFile("rnl_RenewalAddSendHoardingTypeData"));
			}
			//Search Hoarding by Type 1
			if(mGetPropertyFromFile("rnl_RenewalSearchAddHoardingType1YNData").equalsIgnoreCase("yes")){
				mCustomWait(500);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_RenewalAddSendHoardingType1ID"),mGetPropertyFromFile("rnl_RenewalAddSendHoardingType1Data"));
			}
			//Search Hoarding by Address
			if(mGetPropertyFromFile("rnl_RenewalAddSearchHoardingLocationAddressYNData").equalsIgnoreCase("yes")){
				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("rnl_RenewalAddSendHoardingLocationAddressID"),mGetPropertyFromFile("rnl_RenewalAddSendHoardingLocationAddressData"));
			}


			//Search Hoarding by Code
			if(mGetPropertyFromFile("rnl_RenewalSearchAddHoardingCodeYNData").equalsIgnoreCase("yes")){
				mCustomWait(500);
				mSelectAutoCompleteText("id", mGetPropertyFromFile("rnl_RenewalAddSendHoardingCodeID"),mGetPropertyFromFile("rnl_RenewalAddSendHoardingCodeData"));								 								
			}



			// Click on Search button 
			mCustomWait(500);
			mWaitForVisible("xpath", mGetPropertyFromFile("rnl_RenewalAddHoardingSearchBtnID"));
			mClick("xpath", mGetPropertyFromFile("rnl_RenewalAddHoardingSearchBtnID"));

			//Selecting Check box
			mCustomWait(200);
			mWaitForVisible("xpath", mGetPropertyFromFile("rnl_RenewalAddHoardingCheckBoxID"));
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("rnl_RenewalAddHoardingCheckBoxID"));
			mCustomWait(500);
			// Click on Submit button
			mWaitForVisible("xpath", mGetPropertyFromFile("rnl_RenewalAddHoardingSubmitID"));
			mClick("xpath", mGetPropertyFromFile("rnl_RenewalAddHoardingSubmitID"));
			/*}*/

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in AddHoarding");
		}
	}



	public  void  RNL_ScrutinyView_Assertion()
	{
		try {

			String scr_rnlApptitle=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateTitleID"),"value");
			System.out.println("Applicant Title is in Scutiny : "+scr_rnlApptitle);
			BE_Scr_rnlApptitle.add(scr_rnlApptitle);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Rnl_ApplicantTitle.get(CurrentinvoCount), BE_Scr_rnlApptitle.get(approvechekindexes.get(CurrentinvoCount)), "Applicant Title does not match with the actual::"+BE_Scr_rnlApptitle.get(approvechekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+Rnl_ApplicantTitle.get(CurrentinvoCount));
			}

			String scr_rnlEstateFname=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateFnameID"),"value");
			System.out.println("Applicant First Name is in Scutiny : "+scr_rnlEstateFname);
			BE_scr_rnlEstateFname.add(scr_rnlEstateFname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Rnl_ApplicantFname.get(CurrentinvoCount), BE_scr_rnlEstateFname.get(approvechekindexes.get(CurrentinvoCount)), "Applicant First Name does not match with the actual::"+BE_scr_rnlEstateFname.get(approvechekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+Rnl_ApplicantFname.get(CurrentinvoCount));
			}

			String scr_rnlEstateMname=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateMidnameID"),"value");
			System.out.println("Applicant Middle Name is in Scutiny : "+scr_rnlEstateMname);
			BE_scr_rnlEstateMname.add(scr_rnlEstateMname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Rnl_ApplicantMname.get(CurrentinvoCount), BE_scr_rnlEstateMname.get(approvechekindexes.get(CurrentinvoCount)), "Applicant  Middle Name does not match with the actual::"+BE_scr_rnlEstateMname.get(approvechekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+Rnl_ApplicantMname.get(CurrentinvoCount));
			}


			String scr_rnlEstateLname=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateLnameID"),"value");
			System.out.println("Applicant Last Name is in Scutiny : "+scr_rnlEstateLname);
			BE_scr_rnlEstateLname.add(scr_rnlEstateLname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Rnl_ApplicantLname.get(CurrentinvoCount), BE_scr_rnlEstateLname.get(approvechekindexes.get(CurrentinvoCount)), "Applicant Last Name does not match with the actual::"+BE_scr_rnlEstateLname.get(approvechekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+Rnl_ApplicantLname.get(CurrentinvoCount));
			}

			String scr_rnlEstateLFullname=scr_rnlApptitle+" "+scr_rnlEstateFname+" "+scr_rnlEstateMname+" "+scr_rnlEstateLname;
			System.out.println("Applicant Full Name is in Scutiny : "+scr_rnlEstateLFullname);
			BE_scr_rnlEstateLFullname.add(scr_rnlEstateLFullname);


			String scr_rnlEstateMobNo=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateMobNoID"),"value");
			System.out.println("Applicant Mobile No is in Scutiny : "+scr_rnlEstateMobNo);
			BE_scr_rnlEstateMobNo.add(scr_rnlEstateMobNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Rnl_ApplicantMbNo.get(CurrentinvoCount), BE_scr_rnlEstateMobNo.get(approvechekindexes.get(CurrentinvoCount)), "Applicant Mobile No does not match with the actual::"+BE_scr_rnlEstateMobNo.get(approvechekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+Rnl_ApplicantMbNo.get(CurrentinvoCount));
			}

			String scr_rnlEstateEmail=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateEmailID"),"value");
			System.out.println("Applicant Email Id is in Scutiny : "+scr_rnlEstateEmail);
			BE_scr_rnlEstateEmail.add(scr_rnlEstateEmail);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Rnl_ApplicantEmail.get(CurrentinvoCount), BE_scr_rnlEstateEmail.get(approvechekindexes.get(CurrentinvoCount)), "Applicant Email Id does not match with the actual::"+BE_scr_rnlEstateEmail.get(approvechekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+Rnl_ApplicantEmail.get(CurrentinvoCount));
			}

			String scr_rnlEstateRemarks=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateRemarksID"),"value");
			System.out.println("Applicant Remarks is in Scutiny : "+scr_rnlEstateRemarks);
			BE_scr_rnlEstateRemarks.add(scr_rnlEstateRemarks);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Rnl_ApplicantRemarks.get(CurrentinvoCount), BE_scr_rnlEstateRemarks.get(approvechekindexes.get(CurrentinvoCount)), "Applicant Booking of Estate Remarks does not match with the actual::"+BE_scr_rnlEstateRemarks.get(approvechekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+Rnl_ApplicantRemarks.get(CurrentinvoCount));
			}

			String scr_rnlEstateFromDate=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateFromDateID"),"value");
			System.out.println("Applicant Estate Booking From date is in Scutiny : "+scr_rnlEstateFromDate);
			BE_scr_rnlEstateFromDate.add(scr_rnlEstateFromDate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Rnl_BookingfromDate.get(CurrentinvoCount), BE_scr_rnlEstateFromDate.get(approvechekindexes.get(CurrentinvoCount)), "Applicant Estate Booking From does not match with the actual::"+BE_scr_rnlEstateFromDate.get(approvechekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+Rnl_BookingtoDate.get(CurrentinvoCount));
			}

			String scr_rnlEstateToDate=mGetText("id", mGetPropertyFromFile("rnl_BookingOfEstateToDateID"),"value");
			BE_scr_rnlEstateToDate.add(scr_rnlEstateToDate);
			System.out.println("Applicant Estate Booking To Date is in Scutiny : "+scr_rnlEstateToDate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Rnl_BookingtoDate.get(CurrentinvoCount), BE_scr_rnlEstateToDate.get(approvechekindexes.get(CurrentinvoCount)), "Applicant Estate Booking to does not match with the actual::"+BE_scr_rnlEstateToDate.get(approvechekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+Rnl_BookingtoDate.get(CurrentinvoCount));
			}

			mCustomWait(1000);
			Tblread_RNL_Scrutiny_Booking_Details();
			mCustomWait(1000);

			/*if(abc.equalsIgnoreCase("Renewal of Rent Contract") && scrutinylevelcounter==2)
						{
							String scr_rnlApprovedBy=mGetText("id", mGetPropertyFromFile("rnl_View_ApprovedByid"),"value");
							System.out.println("Approved By : "+scr_rnlApprovedBy);


							String scr_rnlApprovedDate=mGetText("id", mGetPropertyFromFile("rnl_View_ApprovedDateid"),"value");
							System.out.println("Approved By : "+scr_rnlApprovedDate);

							String scr_rnlApprovedRemarks=mGetText("id", mGetPropertyFromFile("rnl_View_ApprovedRemarkid"),"value");
							System.out.println("Approved By : "+scr_rnlApprovedRemarks);

						}*/

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in RNL_ScrutinyView_Assertion");
		}
	}
}
