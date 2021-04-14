
package com.abm.mainet.mkt;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

//import com.sun.java.util.jar.pack.Instruction.Switch;
import api.CommonUtilsAPI;

import com.sun.corba.se.spi.orbutil.fsm.Action;
import common.CommonMethods;
import common.ScrutinyProcess;

import errorhandling.MainetCustomExceptions;
import excelreader.ExcelReader;
import excelreader.ExcelToProperties;
import excelreader.ExcelWriting;

//public class MarketLicenseServices extends CommonMethods {
public class MarketLicenseServices extends CommonUtilsAPI {
	String clv_Service_Name;
	String scrutinyapproval;
	String serviceName;
	String busiName;
	String orgName;
	String licHolderName;
	String strippedSaveString;
	String LOINum;

	String license_printing="no";
	ResultSet rs1;
	String trade_holdername;
	String holder_Address;
	String holder_BusinessName;
	String holder_OrganizationName;
	boolean bplselected;
	/*boolean permanent=false;*/


	static CommonMethods common =new CommonMethods();
	ExcelToProperties excelToProp = new ExcelToProperties();
	ScrutinyProcess scrutinyGenericMethod = new ScrutinyProcess ();
	ExcelReader ER=new ExcelReader();
	Date myDate = new Date();
	String Application_date=new SimpleDateFormat("dd/MM/yyyy").format(myDate);
	ExcelWriting EW =new ExcelWriting();


	@BeforeSuite
	public void beforeSuite()
	{
//System.gc();
		thisClassName=this.getClass().getCanonicalName();
		myClassName = thisClassName;
		//mReadFromInputStream(mGetPropertyFromFile("CommonID_Properties_Path"), mGetPropertyFromFile("CommonData_Properties_Path"));

		mCreateModuleFolder("MKT_",myClassName);
	}


	/////////////////////
	//New trade license
	//////////////////////

	@Test(enabled=false)
	public void new_MarketLicense() throws Exception
	{
		try{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			System.out.println("CurrentinvoCount nt==========>"+CurrentinvoCount);
			newMarketLic();
			System.out.println("====================>"+myDate);
String dd=new SimpleDateFormat("dd/MM/yyyy").format(myDate);
			System.out.println(new SimpleDateFormat("dd/MM/yyyy").format(myDate));
			System.out.println("myDate1====>"+dd);
			System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(myDate));
			System.out.println("myDate2====>"+myDate);
			CommonUtilsAPI.marketErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in new_MarketLicense method");
		}
	}




	//Checklist Verification

	//////////////////////////
	@Test(enabled=false)
	public void market_CheckListVerification() throws InterruptedException, IOException
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			mCreateArtefactsFolder("MKT_");
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			//common.ChecklistVerification(mGetPropertyFromFile("cfc_chcklistverfn_username"), mGetPropertyFromFile("cfc_chcklistverfn_pwd"));
			//ChecklistVerification(mGetPropertyFromFile("cfc_chcklistverfn_username"), mGetPropertyFromFile("cfc_chcklistverfn_pwd"));
			//	mAssert(clvapplicationdate,Application_date,"Application date is not same");
			System.out.println("CurrentinvoCount nt cheklist==========>"+CurrentinvoCount);
			checklistVerification();
			CommonUtilsAPI.marketErrorMsg.assertAll();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_CheckListVerification method");
		}
	}

	//Market license scrutiny
	/////////////////////////
	@Test(enabled=false)
	public void marketLicense_Scrutiny() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			System.out.println("CurrentinvoCount nt Scrutiny==========>"+CurrentinvoCount);

			//			marketScrutinyProcess();
			for(scrutinylevelcounter=1;scrutinylevelcounter<=Integer.parseInt(mGetPropertyFromFile("noOfScrutinyLevels"));scrutinylevelcounter++)
			{
				scrutinyGenericMethod.ScrutinyProcess();
			}
			CommonUtilsAPI.marketErrorMsg.assertAll();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in marketLicense_Scrutiny method");
		}
	}


	/// printer grid loi printing
	////////////////////////////
	@Test(enabled=false)
	public void market_printergrid() throws InterruptedException, IOException
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			mCreateArtefactsFolder("MKT_");
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			System.out.println("CurrentinvoCount nt printergrid==========>"+CurrentinvoCount);
			common.CFCprintergrid(mGetPropertyFromFile("mkt_PrinterGrid_username"), mGetPropertyFromFile("mkt_PrinterGrid_password"));
			PrinterGrid_Assertions();
			CommonUtilsAPI.marketErrorMsg.assertAll();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_printergrid method");
		}
	}

	///loi payment
	/////////////
	@Test(enabled=false)
	public void market_loipayment() throws InterruptedException, IOException
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			mCreateArtefactsFolder("MKT_");
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			challanForMARKETServices=true;
			System.out.println("CurrentinvoCount nt Loipayment==========>"+CurrentinvoCount);
			common.loiPayment(mGetPropertyFromFile("cfc_LOIpayment_market"), mGetPropertyFromFile("CFC_ChallanVerfcnNewTradeLicenseServices"));
			
			CommonUtilsAPI.marketErrorMsg.assertAll();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_loipayment method");
		}

	}

	///License Printing
	////////////////
	@Test(enabled=false)
	public void marketLicense_Printing() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();

			license_printing_servicename="new_trade_license";
			license_printing="yes";
			//String s=applicationNo.get(CurrentinvoCount).toString();
			//EW.excelWriting(mGetPropertyFromFile("refpath"),"apppno",s,"5");
			//System.out.println("CurrentinvoCount nt LICENCE PRINTING==========>"+CurrentinvoCount);
			licPrinting();
			CommonUtilsAPI.marketErrorMsg.assertAll();
		} 

		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in marketLicense_Printing method");
		}
	}

	/////

	// TRANSFER LICENSE UNDER NOMINATION

	@Test(enabled=false)
	public void market_TransferLicenseUnderNomn() throws Exception
	{

		try
		{

			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();


			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			/// To clear Array List of Previous Service
			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				applicationNo.clear();	
			}
			licTranUnderNom();
			CommonUtilsAPI.marketErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_TransferLicenseUnderNomn method");
		}
	}

	//Checklist verification
	//////////////////////////
	@Test(enabled=false)
	public void market_TLUNCheckListVerification() throws InterruptedException, IOException
	{
		try
		{

			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			mCreateArtefactsFolder("MKT_");
			//beforeMarket_ChecklistVerification();
			common.ChecklistVerification(mGetPropertyFromFile("cfc_chcklistverfn_username"), mGetPropertyFromFile("cfc_chcklistverfn_pwd"));
			CommonUtilsAPI.marketErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_TLUNCheckListVerification method");
		}
	}

	//Market license scrutiny
	/////////////////////////
	@Test(enabled=false)
	public void market_TLUNScrutiny() throws Exception
	{
		try
		{

			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			mCreateArtefactsFolder("MKT_");
			for(scrutinylevelcounter=1;scrutinylevelcounter<=Integer.parseInt(mGetPropertyFromFile("noOfScrutinyLevels"));scrutinylevelcounter++)
			{
				scrutinyGenericMethod.ScrutinyProcess();
			}
			CommonUtilsAPI.marketErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_TLUNScrutiny method");
		}

	}

	/// printer grid loi printing
	////////////////////////////
	@Test(enabled=false)
	public void market_TLUNPrintergrid() throws InterruptedException, IOException
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			mCreateArtefactsFolder("MKT_");
			common.CFCprintergrid(mGetPropertyFromFile("mkt_PrinterGrid_username"), mGetPropertyFromFile("mkt_PrinterGrid_password"));
			CommonUtilsAPI.marketErrorMsg.assertAll();
			PrinterGrid_Assertions();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_TLUNPrintergrid method");
		}
	}

	///loi payment
	/////////////
	@Test(enabled=false)
	public void market_TLUNLoipayment() throws InterruptedException, IOException
	{
		try
		{

			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			mCreateArtefactsFolder("MKT_");
			//beforeMarket_market_loipayment();
			challanForMARKETServices=true;
			common.loiPayment(mGetPropertyFromFile("cfc_LOIpayment_market"),mGetPropertyFromFile("CFC_ChallanVerfcnTranLicUnderNomnServices"));
			CommonUtilsAPI.marketErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_TLUNLoipayment method");
		}
	}

	///License Printing
	////////////////
	@Test(enabled=false)

	public void market_TLUNLicensePrinting() throws Exception
	{
		try
		{

			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			mCreateArtefactsFolder("MKT_");
			//beforeMarketLicensePrintingTest();
			license_printing_servicename="transfer_under_nomination";

			licPrinting();
			CommonUtilsAPI.marketErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_TLUNLicensePrinting method");
		}
	}





	////////////////////////////////
	////////////////////////////////
	//Cancellation of license

	@Test(enabled=false)
	public void market_cancellationOfLicense() throws InterruptedException, IOException
	{
		try
		{			
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			//beforeNewMarketLicenseTest();
			////////// To clear Array List of previous services
			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				applicationNo.clear();	
			}
			cancellationOfLicense();
			CommonUtilsAPI.marketErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//inAtTest=true;
			throw new MainetCustomExceptions("Error in market_cancellationOfLicense method");
		}
	}


	//Checklist verification
	//////////////////////////
	@Test(enabled=false)
	public void market_cancellationOfLicenseCheckListVerification() throws InterruptedException, IOException
	{
		try
		{

			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			mCreateArtefactsFolder("MKT_");
			//beforeMarket_ChecklistVerification();
		//5	common.ChecklistVerification(mGetPropertyFromFile("cfc_chcklistverfn_username"), mGetPropertyFromFile("cfc_chcklistverfn_pwd"));
			common.ChecklistVerification(mGetPropertyFromFile("cfc_chcklistverfn_username"), mGetPropertyFromFile("cfc_chcklistverfn_pwd"));
			CommonUtilsAPI.marketErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//inAtTest=true;
			throw new MainetCustomExceptions("Error in market_cancellationOfLicenseCheckListVerification method");
		}
	}

	//Market license scrutiny
	/////////////////////////
	@Test(enabled=false)
	public void market_CancellationOfLicenseScrutiny() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			mCreateArtefactsFolder("MKT_");
			//marketScrutinyProcess();
			for(scrutinylevelcounter=1;scrutinylevelcounter<=Integer.parseInt(mGetPropertyFromFile("noOfScrutinyLevels"));scrutinylevelcounter++)
			{
				scrutinyGenericMethod.ScrutinyProcess();
			}
			CommonUtilsAPI.marketErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//inAtTest=true;
			throw new MainetCustomExceptions("Error in market_CancellationOfLicenseScrutiny method");
		}
	}

	/// printer grid loi printing
	@Test(enabled=false)
	public void market_CancellationOfLicensePrintergrid() throws InterruptedException, IOException
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			mCreateArtefactsFolder("MKT_");
			//beforeMarket_printergrid();
			common.CFCprintergrid(mGetPropertyFromFile("mkt_PrinterGrid_username"), mGetPropertyFromFile("mkt_PrinterGrid_password"));
			PrinterGrid_Assertions();
			CommonUtilsAPI.marketErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			//inAtTest=true;
			throw new MainetCustomExceptions("Error in market_CancellationOfLicensePrintergrid method");
		}
	}

	///loi payment
	@Test(enabled=false)
	public void market_CancellationOfLicenseLoipayment() throws InterruptedException, IOException
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			mCreateArtefactsFolder("MKT_");
			//	beforeMarket_market_loipayment();
			challanForMARKETServices=true;
			common.loiPayment(mGetPropertyFromFile("cfc_LOIpayment_market"), mGetPropertyFromFile("CFC_ChallanVerfcnNewTradeLicenseServices"));
			CommonUtilsAPI.marketErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//inAtTest=true;
			throw new MainetCustomExceptions("Error in market_CancellationOfLicenseLoipayment method");
		}
	}

	///License Printing
	////////////////
	@Test(enabled=false)
	public void market_CancellationOfLicenseLicensePrinting() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			//mCreateArtefactsFolder("MKT_");
			//beforeMarketLicensePrintingTest();
			licPrinting();
			CommonUtilsAPI.marketErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			//inAtTest=true;
			throw new MainetCustomExceptions("Error in market_CancellationOfLicenseLicensePrinting method");
		}
	}


	/////////////////////////////////
	///////////////////////////////////////

	//// Renewal of trade license	
	///
	/////////////////////////////////

	@Test(enabled=false)
	public void market_renewalofTradeLicense() throws InterruptedException, IOException
	{
		try
		{

			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			//beforeRenewalofTradeLicense();
			////////// To clear Arraylist of previous service
			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				applicationNo.clear();	
			}
			renewalofTradeLicense();
			CommonUtilsAPI.marketErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_renewalofTradeLicense method");
		}
	}

	//Checklist verification
	
	@Test(enabled=false)
	public void market_renLicCheckListVerification() throws InterruptedException, IOException
	{
		try
		{

			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			mCreateArtefactsFolder("MKT_");
			//beforeMarket_ChecklistVerification();
			common.ChecklistVerification(mGetPropertyFromFile("cfc_chcklistverfn_username"), mGetPropertyFromFile("cfc_chcklistverfn_pwd"));
			CommonUtilsAPI.marketErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_renLicCheckListVerification method");
		}
	}

	//Market license scrutiny

	@Test(enabled=false)
	public void market_renewalLicenseScrutiny() throws Exception
	{
		try
		{


			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();



			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			mCreateArtefactsFolder("MKT_");
			//	marketScrutinyProcess();



			for(scrutinylevelcounter=1;scrutinylevelcounter<=Integer.parseInt(mGetPropertyFromFile("noOfScrutinyLevels"));scrutinylevelcounter++)
			{
				scrutinyGenericMethod.ScrutinyProcess();
			}


			CommonUtilsAPI.marketErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_renewalLicenseScrutiny method");
		}
	}


	/// printer grid loi printing
	@Test(enabled=false)
	public void market_renewalLicensePrintergrid() throws InterruptedException, IOException
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			mCreateArtefactsFolder("MKT_");
			//beforeMarket_printergrid();
			common.CFCprintergrid(mGetPropertyFromFile("mkt_PrinterGrid_username"), mGetPropertyFromFile("mkt_PrinterGrid_password"));
			PrinterGrid_Assertions();
			CommonUtilsAPI.marketErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_renewalLicensePrintergrid method");
		}
	}

	///loi payment
	@Test(enabled=false)
	public void market_renewalLicenseLoipayment() throws InterruptedException, IOException
	{
		try
		{


			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();


			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			mCreateArtefactsFolder("MKT_");
			//beforeMarket_market_loipayment();
			challanForMARKETServices=true;
			//common.loiPayment(mGetPropertyFromFile("cfc_LOIpayment_market"), mGetPropertyFromFile("CFC_ChallanVerfcnNewTradeLicenseServices"));
			common.loiPayment(mGetPropertyFromFile("cfc_LOIpayment_market"), mGetPropertyFromFile("CFC_ChallanVerfcnRenewalTradeLicenseServices"));
			CommonUtilsAPI.marketErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_renewalLicenseLoipayment method");
		}
	}

	///////License Printing	////////////////
	@Test(enabled=false)
	public void market_renewalLicenseLicensePrinting() throws Exception
	{
		try
		{

			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			//mCreateArtefactsFolder("MKT_");
			//beforeMarketLicensePrintingTest();
			license_printing_servicename="Renewal_of_license";
			licPrinting();
			CommonUtilsAPI.marketErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_renewalLicenseLicensePrinting method");
		}
	}

	///////////////////////////////

	/////

	//1.Application For Duplicate license

	@Test(enabled=false)
	public void market_issuanceofDuplicateLicense() throws InterruptedException, IOException
	{
		try
		{

			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();

			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			//////// To clear Array List of previous services
			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				applicationNo.clear();	
			}
			issuanceofDuplicateLicense();
			CommonUtilsAPI.marketErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_issuanceofDuplicateLicense method");
		}
	}


	//Checklist verification
	@Test(enabled=false)
	public void market_dupLicCheckListVerification() throws InterruptedException, IOException
	{
		try
		{

			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();



			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			mCreateArtefactsFolder("MKT_");
			//beforeMarket_ChecklistVerification();
			common.ChecklistVerification(mGetPropertyFromFile("cfc_chcklistverfn_username"), mGetPropertyFromFile("cfc_chcklistverfn_pwd"));
			CommonUtilsAPI.marketErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_dupLicCheckListVerification method");
		}
	}

	//Market license scrutiny
	/////////////////////////
	@Test(enabled=false)
	public void market_dupLicenseScrutiny() throws Exception
	{

		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			mCreateArtefactsFolder("MKT_");
			//marketScrutinyProcess();
			for(scrutinylevelcounter=1;scrutinylevelcounter<=Integer.parseInt(mGetPropertyFromFile("noOfScrutinyLevels"));scrutinylevelcounter++)
			{
				scrutinyGenericMethod.ScrutinyProcess();
			}
			CommonUtilsAPI.marketErrorMsg.assertAll();



		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in marketLicense_Scrutiny method");
		}
	}

	/// printer grid loi printing
	@Test(enabled=false)
	public void market_dupLicPrintergrid() throws InterruptedException, IOException
	{
		try
		{

			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();


			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			mCreateArtefactsFolder("MKT_");
			//beforeMarket_printergrid();
			common.CFCprintergrid(mGetPropertyFromFile("mkt_PrinterGrid_username"), mGetPropertyFromFile("mkt_PrinterGrid_password"));
			//PrinterGrid_Assertions();
			CommonUtilsAPI.marketErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_dupLicPrintergrid method");
		}
	}

	///loi payment
	@Test(enabled=false)
	public void market_DupLicLoipayment() throws InterruptedException, IOException
	{
		try
		{

			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			//MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			mCreateArtefactsFolder("MKT_");
			if(mGetPropertyFromFile("mkt_duplicate_LOI_data").equalsIgnoreCase("yes"))
			{
				System.out.println("im in loi payment");
				//beforeMarket_market_loipayment();
				challanForMARKETServices=true;
				//common.loiPayment(mGetPropertyFromFile("cfc_LOIpayment_market"), mGetPropertyFromFile("CFC_ChallanVerfcnIssueDupLicenseServices"));
				common.loiPayment(mGetPropertyFromFile("cfc_LOIpayment_market"), mGetPropertyFromFile("CFC_ChallanVerfcnNewTradeLicenseServices"));
				
				CommonUtilsAPI.marketErrorMsg.assertAll();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_DupLicLoipayment method");
		}
	}

	///License Printing
	////////////////
	@Test(enabled=false)
	public void market_DupLicLicensePrinting() throws Exception
	{
		try
		{

			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
        	thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			//mCreateArtefactsFolder("MKT_");
			//beforeMarketLicensePrintingTest();
			if(mGetPropertyFromFile("mkt_duplicate_LOI_data").equalsIgnoreCase("yes"))
			{
				license_printing_servicename="Duplicate_license";
				licPrinting();
				CommonUtilsAPI.marketErrorMsg.assertAll();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_DupLicLicensePrinting method");
		}
	}



	///////////////////////////////
	//////////////////////////////
	// CHANGE IN NAME OF BUSINESS 

	@Test(enabled=false)
	public void market_ChangeInBusinessName() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			System.out.println("CurrentinvoCount cb==>"+CurrentinvoCount);

			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				applicationNo.clear();	
			}

			changeInBusinessName();
			CommonUtilsAPI.marketErrorMsg.assertAll();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_ChangeInBusinessName method");
		}
	}

	//Checklist verification
	//////////////////////////
	@Test(enabled=false)
	public void market_CIBNCheckListVerification() throws InterruptedException, IOException
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			mCreateArtefactsFolder("MKT_");
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			common.ChecklistVerification(mGetPropertyFromFile("cfc_chcklistverfn_username"), mGetPropertyFromFile("cfc_chcklistverfn_pwd"));
			CommonUtilsAPI.marketErrorMsg.assertAll();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_CIBNCheckListVerification method");
		}
	}

	//Market license scrutiny
	/////////////////////////
	@Test(enabled=false)
	public void market_CIBNScrutiny() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			//marketScrutinyProcess();

			for(scrutinylevelcounter=1;scrutinylevelcounter<=Integer.parseInt(mGetPropertyFromFile("noOfScrutinyLevels"));scrutinylevelcounter++)
			{
				scrutinyGenericMethod.ScrutinyProcess();
			}			

			CommonUtilsAPI.marketErrorMsg.assertAll();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_CIBNScrutiny method");
		}

	}

	/// printer grid loi printing
	////////////////////////////
	@Test(enabled=false)
	public void market_CIBNPrintergrid() throws InterruptedException, IOException
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			mCreateArtefactsFolder("MKT_");
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			common.CFCprintergrid(mGetPropertyFromFile("mkt_PrinterGrid_username"), mGetPropertyFromFile("mkt_PrinterGrid_password"));
			PrinterGrid_Assertions();
			CommonUtilsAPI.marketErrorMsg.assertAll();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_CIBNPrintergrid method");
		}
	}

	///loi payment
	/////////////
	@Test(enabled=false)
	public void market_CIBNLoipayment() throws InterruptedException, IOException
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			mCreateArtefactsFolder("MKT_");
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			challanForMARKETServices=true;
			common.loiPayment(mGetPropertyFromFile("cfc_LOIpayment_market"), mGetPropertyFromFile("CFC_ChallanVerfcnChngBusiNameServices"));
			CommonUtilsAPI.marketErrorMsg.assertAll();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_CIBNLoipayment method");
		}
	}

	///License Printing
	////////////////
	@Test(enabled=false)
	public void market_CIBNLicensePrinting() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			mCreateArtefactsFolder("MKT_");
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			license_printing_servicename="change";
			licPrinting();
			CommonUtilsAPI.marketErrorMsg.assertAll();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_CIBNLicensePrinting method");
		}
	}



	///////////////////////////////////
	///////////////////////////////////
	////Transfer of license OTHER MODES
	//////////////////////////

	@Test(enabled=false)
	public void market_transferLicenseOtherModes() throws InterruptedException, IOException
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			////////// To clear Array list of previous services
			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				applicationNo.clear();	
			}
			transferOfLicenseunderOtherModes();
			CommonUtilsAPI.marketErrorMsg.assertAll();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_transferLicenseOtherModes method");
		}
	}

	//Checklist verification
	//////////////////////////
	@Test(enabled=false)
	public void market_transferOfLicenseOtherModesCheckListVerification() throws InterruptedException, IOException
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			common.ChecklistVerification(mGetPropertyFromFile("cfc_chcklistverfn_username"), mGetPropertyFromFile("cfc_chcklistverfn_pwd"));
			CommonUtilsAPI.marketErrorMsg.assertAll();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_transferOfLicenseOtherModesCheckListVerification method");
		}
	}

	//Market license scrutiny
	/////////////////////////
	@Test(enabled=false)
	public void market_transferOfLicenseOtherModesScrutiny() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			//marketScrutinyProcess();

			for(scrutinylevelcounter=1;scrutinylevelcounter<=Integer.parseInt(mGetPropertyFromFile("noOfScrutinyLevels"));scrutinylevelcounter++)
			{
				scrutinyGenericMethod.ScrutinyProcess();
			}

			CommonUtilsAPI.marketErrorMsg.assertAll();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_transferOfLicenseOtherModesScrutiny method");
		}
	}

	/// printer grid loi printing
	@Test(enabled=false)
	public void market_transferOfLicenseOtherModesPrintergrid() throws InterruptedException, IOException
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			common.CFCprintergrid(mGetPropertyFromFile("mkt_PrinterGrid_username"), mGetPropertyFromFile("mkt_PrinterGrid_password"));
			PrinterGrid_Assertions();
			CommonUtilsAPI.marketErrorMsg.assertAll();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_transferOfLicenseOtherModesPrintergrid method");
		}
	}

	///loi payment
	@Test(enabled=false)
	public void market_transferOfLicenseOtherModesLoipayment() throws InterruptedException, IOException
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			challanForMARKETServices=true;
			common.loiPayment(mGetPropertyFromFile("cfc_LOIpayment_market"), mGetPropertyFromFile("CFC_ChallanVerfcnTransferOtherModesLicenseServices"));
			CommonUtilsAPI.marketErrorMsg.assertAll();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_transferOfLicenseOtherModesLoipayment method");
		}
	}

	///License Printing
	////////////////
	@Test(enabled=false)
	public void market_transferOfLicenseOtherModesLicensePrinting() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			licPrinting();
			CommonUtilsAPI.marketErrorMsg.assertAll();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in market_transferOfLicenseOtherModesLicensePrinting method");
		}
	}




	////////////////////////////////////
	/// At Test Methods
	////////////////////////////////////
	public void renewalofTradeLicense()  throws InterruptedException, IOException
	{
		try
		{
			mCreateArtefactsFolder("MKT_");
			challanForMARKETServices = true;
			//SLandDevelopment= true;
			//beforeMarket_IssuanceOfDuplicateLicense();
			common.mOpenBrowser("Chrome");
			common.mGetURL(mGetPropertyFromFile("url"));
			common.selectUlb();
			//common.citizenLogin();
			common.departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));

			RenewalofTradeLicense();
			common.logOut();
			common.finalLogOut();
			mCloseBrowser();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in renewalofTradeLicense method");
		}
	}



	public void newMarketLic() throws Exception
	{
		try
		{
			//String licensenum_newtrade="swap44";





			mCreateArtefactsFolder("MKT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			common.selectUlb();
			//common.citizenLogin();
			common.departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			mGenericWait();			
			System.out.println("Enter into New Trade License before navigation");
			mNavigation("mkt_deptonlserviceLinkid","mkt_marketLicenseLinkid", "mkt_newMarketLicenseServiceLinkid");  // 12-10-2016
			newMarketLicense();
			common.logOut();
			common.finalLogOut();
			mCloseBrowser();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in newMarketLic method");
		}


	}


	public void licPrinting() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("MKT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			common.selectUlb();
			common.departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			licensePrinting();
		
					if (license_printing.equalsIgnoreCase("yes")) {
					EW.excelWriting(mGetPropertyFromFile("licecepath"),"mkt_Licencenumber",licensenum_newtrade,"97");
					license_printing="false";
				}
				System.out.println("license_printing flag===>"+license_printing);
		
			
			

			//EW.excelWriting(mGetPropertyFromFile("licecepath"),"mkt_Licencenumber",licensenum_newtrade);

			common.logOut();
			common.finalLogOut();
			mCloseBrowser();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in licPrinting method");
		}
	}

	public void licTranUnderNom() throws Exception
	{
		try
		{

			mCreateModuleFolder("MKT_",myClassName);
			mCreateArtefactsFolder("MKT_");
			//beforeLicTranUnderNominationTest();
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			common.selectUlb();
			//common.citizenLogin();
			//navigation("Market License","Transfer of License Under Nomination");
			common.departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			licenseTransferUnderNomination();
			common.logOut();
			common.finalLogOut();
			mCloseBrowser();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in licTranUnderNom method");
		}
	}







	public void changeInBusinessName() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("MKT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			common.selectUlb();
			//common.citizenLogin();
			common.departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword")); // 11-11-2016
			mGenericWait();
			mNavigation("mkt_deptonlserviceLinkid","mkt_chngBusiNameLinkid", "mkt_chngBusiNameServiceLinkid");  // 11-11-2016
			changeInNameOfBusiness();
			common.logOut();
			common.finalLogOut();
			mCloseBrowser();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in changeInBusinessName method");
		}
	}




	//Sunil D Sonmale 15-04-2016
	//Transfer of License under other modes

	public void transferOfLicenseunderOtherModes()  throws InterruptedException, IOException
	{
		try
		{
			mCreateArtefactsFolder("MKT_");
			challanForMARKETServices = true;
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			common.mGetURL(mGetPropertyFromFile("url"));
			common.selectUlb();
			//common.citizenLogin();			
			common.departmentLogin(mGetPropertyFromFile("deptUserName"), mGetPropertyFromFile("deptPassword"));
			mGenericWait();			
			mNavigation("mkt_deptonlserviceid", "mkt_marketLicenseid", "mkt_TransferMarketLicenseServiceLinkid");
			mGenericWait();			
			TransferOfLicenseunderOtherModes();
			common.logOut();
			common.finalLogOut();
			mCloseBrowser();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in transferOfLicenseunderOtherModes method");
		}
	}


	public void issuanceofDuplicateLicense()  throws InterruptedException, IOException

	{
		try
		{
			mCreateArtefactsFolder("MKT_");
			challanForMARKETServices = true;
			//SLandDevelopment= true;
			//beforeMarket_IssuanceOfDuplicateLicense();
			common.mOpenBrowser("Chrome");
			common.mGetURL(mGetPropertyFromFile("url"));
			common.selectUlb();
			//common.citizenLogin();
			common.departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			IssuanceofDuplicateLicense();
			common.logOut();
			common.finalLogOut();
			mCloseBrowser();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in issuanceofDuplicateLicense method");
		}

	}
	public void cancellationOfLicense()  throws InterruptedException, IOException
	{
		try
		{

			challanForMARKETServices = true;
			//SLandDevelopment= true;
			//beforeMarket_IssuanceOfDuplicateLicense();
			mCreateArtefactsFolder("MKT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			common.selectUlb();
			//common.citizenLogin();

			common.departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));

			CancellationOfLicense();
			common.logOut();
			common.finalLogOut();
			mCloseBrowser();

		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in cancellationOfLicense method");
		}
	}


	public void IssuanceofDuplicateLicense() throws InterruptedException
	{
		try
		{

			System.out.println(myDate);

			System.out.println(new SimpleDateFormat("dd/MM/yyyy").format(myDate));
			System.out.println("myDate====>"+myDate);
			System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(myDate));
			System.out.println("myDate====>"+myDate);

			//	HolderName.get(CurrentinvoCount);
			//Issuance of Duplicate License
			/*mWaitForVisible("linkText", mGetPropertyFromFile("MKT_moduleID"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("MKT_moduleID"));

			mTakeScreenShot();

			//Issuance of Duplicate License link
			mWaitForVisible("linkText", mGetPropertyFromFile("MKT_issueDupLicID"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("MKT_issueDupLicID"));*/

			mTakeScreenShot();
			mNavigation("mkt_duplicatedeptonlserviceLinkid", "mkt_duplicatemarketLicenseLinkid", "mkt_duplicateMarketLicenseServiceLinkid");
			//Click List
			mWaitForVisible("id", mGetPropertyFromFile("MKT_issueDupLicListButtonID"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("MKT_issueDupLicListButtonID"));



			//Enter license no
			mWaitForVisible("name", mGetPropertyFromFile("MKT_issueDupLicLicNoTextboxID"));
			mGenericWait();
			mGenericWait();
			//mClick("id", "licenseNo");
			//mSendKeys("name", mGetPropertyFromFile("MKT_issueDupLicLicNoTextboxID"), licenseNo);
			//mSendKeys("name", mGetPropertyFromFile("MKT_issueDupLicLicNoTextboxID"),mGetPropertyFromFile("MKT_issueDupLicLicNoTextbox_Data"));
			//	mSendKeys("name", mGetPropertyFromFile("MKT_issueDupLicLicNoTextboxID"), "TLA00093/00");

			
			
			if(mGetPropertyFromFile("Execution_All").equalsIgnoreCase("ALL"))
			{
				licenseNo = mGetPropertyFromFile("mkt_Licencenumber");
				//mSendKeys("name", mGetPropertyFromFile("mkt_licenseNoid"),licenseNo );
			mSendKeys("name", mGetPropertyFromFile("MKT_issueDupLicLicNoTextboxID"),licenseNo);
			licenseNo="";
			}
			else
			{
				licenseNo = mGetPropertyFromFile("MKT_issueDupLicLicNoTextbox_Data");
				mWaitForVisible("name", mGetPropertyFromFile("MKT_issueDupLicLicNoTextboxID"));
				mGenericWait();
				mSendKeys("name", mGetPropertyFromFile("MKT_issueDupLicLicNoTextboxID"),licenseNo);
				//IndOrDep("name", "mkt_licenseNoid", "applicationNo");
				//IndOrDep("name", "mkt_licenseNoid", "MKT_issueDupLicLicNoTextbox_Data");
				
				licenseNo="";
			}

			
			
			
			
			
			
			
			
			
			
			mTakeScreenShot();
			//Click Search
			mClick("css", mGetPropertyFromFile("MKT_issueDupLicSearchbuttonID"));

			///Wait for search result to appear
			mWaitForVisible("css", mGetPropertyFromFile("MKT_issueDupLicViewDetailsID"));	

			mTakeScreenShot();

			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("MKT_issueDupLicViewDetailsID"));

			//Capture licensee details

			mWaitForVisible("id", mGetPropertyFromFile("MKT_issueDupLicHoldNameID"));
			mGenericWait();


			mTakeScreenShot();

			String  holdername= mGetText("id", mGetPropertyFromFile("MKT_issueDupLicHoldNameID"),"value");
			System.out.println("Holder name is::"+holdername);
			//assert new   
			duplicate_HolderName.add(holdername);

			//mAssert(holdername, trade_holdername, "holdername assertion fail  Actual Message :"+holdername+" Expected Message :"+trade_holdername);

			String businessname = mGetText("id", mGetPropertyFromFile("MKT_issueDupLicbusinessNameID"),"value");
			System.out.println("businessname is::"+businessname);

			duplicate_HolderBusinessName.add(businessname);

			String orgname= mGetText("id", mGetPropertyFromFile("MKT_issueDupLicorganizationID"),"value");

			System.out.println("orgname is::"+orgname);
			duplicate_HolderOrganizationName.add(orgname);

			String propdues = mGetText("id", mGetPropertyFromFile("MKT_issueDupLicpropertyID"),"value");
			System.out.println("propdues is::"+propdues);

			String marketdues = mGetText("id", mGetPropertyFromFile("MKT_issueDupLicmarketID"),"value");
			System.out.println("marketdues is::"+marketdues);


			//Upload 
			//	mWaitForVisible("id", "entity.cfcAttachments0.attPath");
			mGenericWait();
			mUpload("id", mGetPropertyFromFile("MKT_issueDupLicUploadID"), mGetPropertyFromFile("MKT_issueDupLicUpload_Data"));
			mTakeScreenShot();
			//mGenericWait();
			mCustomWait(2000);
			mUpload("id", mGetPropertyFromFile("MKT_issueDupLicUpload1ID"), mGetPropertyFromFile("MKT_issueDupLicUpload1_Data"));
			mTakeScreenShot();

			//Save
			mWaitForVisible("css", mGetPropertyFromFile("MKT_issueDupLicSaveID"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("MKT_issueDupLicSaveID"));

			//Get pop up save msg
			mWaitForVisible("css", mGetPropertyFromFile("MKT_issueDupLicProceedButtonID"));			
			String popupmsg = mGetText("css", mGetPropertyFromFile("MKT_issueDupLicPopUpMsgID"));
			System.out.println("Pop up message::: "+popupmsg);

			//Capturing appno and asserting pop up msg
			appNo = popupmsg.replaceAll("\\D+","");
			System.out.println("Application No is::: "+appNo);
			appNo = appNo.trim();
			mAppNoArray(appNo); 
			mCustomWait(5000);
			mktdbtest();
			popupmsg = popupmsg.replaceAll("[0-9]","");			
			System.out.println(popupmsg);
			mTakeScreenShot();




			//Wait for proceed and click on proceed
			//mWaitForVisible("css", mGetPropertyFromFile("MKT_issueDupLicProceedButtonID"));
			//mGenericWait();
			mTakeScreenShot();
			mAssert(popupmsg, mGetPropertyFromFile("MKT_issueDupLicAssertMsg_Data"), mGetPropertyFromFile("MKT_issueDupLicAssertMsgFail_Data")+" Expected::: "+mGetPropertyFromFile("MKT_issueDupLicAssertMsg_Data")+ " Actual::: " +popupmsg);
			mClick("css", mGetPropertyFromFile("MKT_issueDupLicProceedButtonID"));
			/*if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				if(HolderName.equals(duplicate_HolderName))
				{
					mAssert(HolderName, duplicate_HolderBusinessName, "Holder name of new trade is not matching with holder name of duplicate");
					mAssert(HolderBusinessName , duplicate_HolderBusinessName, "HolderBusinessName name of new trade is not matching with duplicate_HolderBusinessName name of duplicate");
					mAssert(HolderOrganizationName , duplicate_HolderOrganizationName, "HolderOrganizationName name of new trade is not matching with duplicate_HolderOrganizationName name of duplicate");

				}
			}*/

		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in IssuanceofDuplicateLicense method");
		}

	}






	///Transfer of license under other modes
	////////////////////////////////////

	public void TransferOfLicenseunderOtherModes() throws InterruptedException, IOException
	{
		try
		{

			//Wait for form to load license no textbox to be visible
			mWaitForVisible("id", mGetPropertyFromFile("mkt_transfrOMlicNoTextBox_ID"));
			//Click list
			mWaitForVisible("css", mGetPropertyFromFile("mkt_transfrOMlistBtn_ID"));
			mClick("css", mGetPropertyFromFile("mkt_transfrOMlistBtn_ID"));

			//Wait for popup and enter license no
			//mWaitForVisible("css", mGetPropertyFromFile("mkt_transfrOMpopUplicNotextBox_ID"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("mkt_transfrOMsearchBtn_ID"));
			mWaitForVisible("css", mGetPropertyFromFile("MKT_tlunom_find_id"));
			mClick("css", mGetPropertyFromFile("MKT_tlunom_find_id"));
			
////////////////newswap
			if(mGetPropertyFromFile("Execution_All").equalsIgnoreCase("ALL"))
			{
				licenseNo = mGetPropertyFromFile("mkt_Licencenumber");
				//mSendKeys("name", mGetPropertyFromFile("mkt_licenseNoid"),licenseNo );
			mSendKeys("name", mGetPropertyFromFile("mkt_transfrOMpopUplicNotextBox_ID"),licenseNo);
			licenseNo="";
			}
			else
			{
				licenseNo = mGetPropertyFromFile("mkt_transfrOMpopUplicNotextBox_data");
				mWaitForVisible("name", mGetPropertyFromFile("mkt_transfrOMpopUplicNotextBox_ID"));
				mGenericWait();
				mSendKeys("css", mGetPropertyFromFile("mkt_transfrOMpopUplicNotextBox_ID"),licenseNo);
				//IndOrDep("name", "mkt_licenseNoid", "applicationNo");
				//IndOrDep("name", "mkt_licenseNoid", "MKT_issueDupLicLicNoTextbox_Data");
				
				licenseNo="";
			}

			
		///////////////end swap
			
			
			
			
			
			
			//IndOrDep("css", mGetPropertyFromFile("mkt_transfrOMpopUplicNotextBox_ID"), "applicationNo");

			//IndOrDep("xpath", mGetPropertyFromFile("mkt_transfrOMpopUplicNotextBox_ID"), "TLA00153/00");

			//mSendKeys("css", "input[id='pnbutton'][value='List']", licenseNo);
			//mSendKeys("css", mGetPropertyFromFile("mkt_transfrOMpopUplicNotextBox_ID"), mGetPropertyFromFile("mkt_transfrOMpopUplicNotextBox_data"));

			//Click search

			mWaitForVisible("xpath", mGetPropertyFromFile("MKT_find_id1"));
			mClick("xpath", mGetPropertyFromFile("MKT_find_id1"));

			//Wait for view details box

			mWaitForVisible("xpath", mGetPropertyFromFile("mkt_transfrOMViewDetailsImg_ID"));
			mClick("xpath", mGetPropertyFromFile("mkt_transfrOMViewDetailsImg_ID"));

			//Wait for record to get fetched and popup to disappear

			mWaitForVisible("id", mGetPropertyFromFile("mkt_transfrOMbusNameTextBox_ID"));

			mGenericWait();

			//Transfer Other Mode Business Name assertion by Jyoti

			String Transferothermode_LicenseNo = mGetText("id", mGetPropertyFromFile("mkt_transfrOMlicNo_ID"), "value");
			System.out.println("Transfer Other Mode License no is:::: "+Transferothermode_LicenseNo);
			TransferOtherModeLicenseNo.add(Transferothermode_LicenseNo);

			String Transferothermode_BusinessName = mGetText("css", mGetPropertyFromFile("mkt_transferOtherMode_Businessnameid"), "value");
			System.out.println("Business Name in Transfer Other Mode is ::: "+Transferothermode_BusinessName);		     
			TransferOtherModeBusinessName.add(Transferothermode_BusinessName);


			//String propertyDues = mGetText("id", mGetPropertyFromFile("mkt_transfrOMpropDuesBox_ID"), "value");
			//System.out.println("property Dues are:::: "+propertyDues);

			//String marketDues = mGetText("id", mGetPropertyFromFile("mkt_transfrOMmarketDuesBox_ID"), "value");
			//System.out.println("market Dues are:::: "+marketDues);

			//Add new holder details

			mWaitForVisible("xpath", mGetPropertyFromFile("mkt_transfrOMaddDetailsLink_ID"));
			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("mkt_transfrOMaddDetailsLink_ID"));

			// Wait for add details form to open

			mWaitForVisible("id", mGetPropertyFromFile("mkt_transfrOMfirstName_ID"));
			mGenericWait();

			//Enter new holder details
			///title
			mWaitForVisible("id", mGetPropertyFromFile("mkt_transfrOMTitle_ID"));
			mSelectDropDown("id", mGetPropertyFromFile("mkt_transfrOMTitle_ID"), mGetPropertyFromFile("mkt_transfrOMTitle_data"));
			mCustomWait(1000);
			//Firstname
			mWaitForVisible("id", mGetPropertyFromFile("mkt_transfrOMfirstName_ID"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("mkt_transfrOMfirstName_ID"), mGetPropertyFromFile("mkt_transfrOMfirstName_data"));
			//middle name
			mWaitForVisible("id", mGetPropertyFromFile("mkt_transfrOMmiddlename_ID"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("mkt_transfrOMmiddlename_ID"), mGetPropertyFromFile("mkt_transfrOMmiddlename_data"));
			//last name
			mWaitForVisible("id", mGetPropertyFromFile("mkt_transfrOMlastName_ID"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("mkt_transfrOMlastName_ID"), mGetPropertyFromFile("mkt_transfrOMlastName_data"));
			//gender
			mWaitForVisible("id", mGetPropertyFromFile("mkt_transfrOMgender_ID"));
			mSelectDropDown("id", mGetPropertyFromFile("mkt_transfrOMgender_ID"), mGetPropertyFromFile("mkt_transfrOMgender_data"));
			mCustomWait(1000);
			//age
			mWaitForVisible("id", mGetPropertyFromFile("mkt_transfrOMage_ID"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("mkt_transfrOMage_ID"), mGetPropertyFromFile("mkt_transfrOMage_data"));
			///address
			mWaitForVisible("id", mGetPropertyFromFile("mkt_transfrOMaddress_ID"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("mkt_transfrOMaddress_ID"), mGetPropertyFromFile("mkt_transfrOMaddress_data"));
			///phone no
			mWaitForVisible("id", mGetPropertyFromFile("mkt_transfrOMphone_ID"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("mkt_transfrOMphone_ID"), mGetPropertyFromFile("mkt_transfrOMphone_data"));
			//pincode
			mWaitForVisible("id", mGetPropertyFromFile("mkt_transfrOMpincode_ID"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("mkt_transfrOMpincode_ID"), mGetPropertyFromFile("mkt_transfrOMpincode_data"));
			//email id
			mWaitForVisible("id", mGetPropertyFromFile("mkt_transfrOMemail_ID"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("mkt_transfrOMemail_ID"), mGetPropertyFromFile("mkt_transfrOMemail_data"));
			//mobile no
			mWaitForVisible("id", mGetPropertyFromFile("mkt_transfrOMmobNo_ID"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("mkt_transfrOMmobNo_ID"), mGetPropertyFromFile("mkt_transfrOMmobNo_data"));
			//adhar no
			mWaitForVisible("id", mGetPropertyFromFile("mkt_transfrOMadharNo_ID"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("mkt_transfrOMadharNo_ID"), mGetPropertyFromFile("mkt_transfrOMadharNo_data"));
			//upload image
			//Commenting temporarily due to defect on upload functionality for this form
			/*mWaitForVisible("id", "customEntity.imagePath");
		mCustomWait(1000);
		mUpload("id", "customEntity.imagePath", "\\uploads\\TestAutomationDocument_Two.pdf");*/
			//click submit
			mWaitForVisible("css", mGetPropertyFromFile("mkt_transfrOMsubmitBtn_ID"));
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("mkt_transfrOMsubmitBtn_ID"));
			//Wait for save button
			mWaitForVisible("css", mGetPropertyFromFile("mkt_transfrOMSaveBtn_ID"));
			mCustomWait(1000);


			//Select transfer type and subtype.. 

			mWaitForVisible("id", mGetPropertyFromFile("mkt_transfrOMTransferType_ID"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("mkt_transfrOMTransferType_ID"), mGetPropertyFromFile("mkt_transfrOMTransferType_data"));

			mWaitForVisible("id", mGetPropertyFromFile("mkt_transfrOMTransferSubType_ID"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("mkt_transfrOMTransferSubType_ID"), mGetPropertyFromFile("mkt_transfrOMTransferSubType_data"));

			//Upload document
			//mWaitForVisible("id", "entity.cfcAttachments0.attPath");
			mGenericWait();
			mUpload("id", mGetPropertyFromFile("mkt_transfrOMUpload_ID"), mGetPropertyFromFile("mkt_transfrOMUpload_data"));


			// Transfer Other Mode Assertion for Holder Details (New & Old)

			/*WebElement table2 = driver.findElement(By.id("gridPersonalDetails"));
			List<WebElement> rows2 = table2.findElements(By.tagName("tr"));

			int rowcount2 = rows2.size();
			System.out.println("NUMBER OF ROWS IN Transfer Other Mode Application time ="+rowcount2);

			// Iterate To get the value of each cell in table along with element id

			System.out.println("NUMBER OF ROWS IN Transfer Other Mode Application time= "+rows2.size());

			int row_num2 = 1;
			for (WebElement rowElement : rows2)
			{
				List<WebElement> cols2 = rowElement.findElements(By.xpath("td"));
				int col_num2 = 1;
				for(WebElement colElement : cols2)
				{
					if(row_num2 > 1)
					{
						if(col_num2 == 0)
						{

							String SrNo2 = rowElement.findElement(By.xpath("./td")).getText();
							mGenericWait();
							System.out.println("Transfer Other Mode Sr. No. is== "+SrNo2);
						}

						if(col_num2 == 1)
						{
							String trade_holdername2 = rowElement.findElement(By.xpath("./td[2]")).getText();
							System.out.println("Transfer Other Mode Application time Holder Name is == "+trade_holdername2);

							TransferOtherModeHolderName.add(trade_holdername2);

						}
						//col_num = col_num + 1;	
					}
				}
				//row_num = row_num + 1;
			}




*/

			//Click save
			//Wait for save button
			mWaitForVisible("css", mGetPropertyFromFile("mkt_transfrOMSaveBtn_ID"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("mkt_transfrOMSaveBtn_ID"));
			//Wait for popup
			//body > div.fancybox-overlay.fancybox-overlay-fixed > div > div > div > div > div > p
			mWaitForVisible("id", mGetPropertyFromFile("mkt_transfrOMproceedBtn_ID"));
			mGenericWait();

			String popupmsg = mGetText("css", mGetPropertyFromFile("mkt_transfrOMpopUpMsg_ID"));
			System.out.println("This is the popup msg::::" +popupmsg);

			//Capturing appno and asserting popup msg
			appNo = popupmsg.replaceAll("\\D+","");
			System.out.println("Application No is::: "+appNo);
			appNo = appNo.trim();

			popupmsg = popupmsg.replaceAll("[0-9]","");	
			mAppNoArray(appNo);

			System.out.println(popupmsg);
			mTakeScreenShot();

			mGenericWait();
			mClick("id", mGetPropertyFromFile("mkt_transfrOMproceedBtn_ID"));

			mWaitForVisible("id", mGetPropertyFromFile("mkt_transfrOMlicNoTextBox_ID"));
			mGenericWait();

		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in TransferOfLicenseunderOtherModes method");
		}

	}





	////////////////////////////////////////////////
	// Main Methods
	///////////////////////////////////////////


	public void newMarketLicense() throws IOException
	{
		try
		{

			mGenericWait();
			mWaitForVisible("css", mGetPropertyFromFile("mkt_submitBtnid"));
			mGenericWait();

			// Business Details

			mSelectDropDown("id", mGetPropertyFromFile("mkt_wardid"), mGetPropertyFromFile("mkt_warddata"));
			mGenericWait();

			mSelectDropDown("id", mGetPropertyFromFile("mkt_wardid"), mGetPropertyFromFile("mkt_warddata"));
			mGenericWait();

			if(mGetPropertyFromFile("licType").equals("Temporary License"))
			{

				mGenericWait();
				mClick("css", mGetPropertyFromFile("mkt_temporaryLicenseid"));
				mGenericWait();

				//mGdatePicker(mGetPropertyFromFile("mkt_tempLicFromDateid"), mGetPropertyFromFile("mkt_tempLicFromYear"), mGetPropertyFromFile("mkt_tempLicFromMonth"), mGetPropertyFromFile("mkt_tempLicFromDate"));

				mDateSelect("id", mGetPropertyFromFile("temp_from_date_id"),mGetPropertyFromFile("temp_from_date"));

				mGenericWait();
				//mGdatePicker(mGetPropertyFromFile("mkt_tempLicToDateid"), mGetPropertyFromFile("mkt_tempLicToYear"), mGetPropertyFromFile("mkt_tempLicToMonth"), mGetPropertyFromFile("mkt_tempLicToDate"));

				mDateSelect("id", mGetPropertyFromFile("temp_to_date_id"),mGetPropertyFromFile("temp_to_date"));

			}
			else if(mGetPropertyFromFile("licType").equals("Permanent License"))

			{
				mClick("css", mGetPropertyFromFile("mkt_permanentLicenseid"));

			}
			else
			{
				System.out.println("Please check 'License Type'");
			}

			mGenericWait();

			if(mGetPropertyFromFile("bplType").equals("Yes"))
			{
				mClick("id", mGetPropertyFromFile("mkt_bplTypeYesid"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("mkt_bplNoid"), mGetPropertyFromFile("mkt_bplNo"));

				bplselected=true;
			}
			else if (mGetPropertyFromFile("bplType").equals("No"))
			{
				mClick("id", mGetPropertyFromFile("mkt_bplTypeNoid"));
			}


			if(mGetPropertyFromFile("licType").equals("Permanent License"))
			{
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("mkt_ LicenseTenureid"), mGetPropertyFromFile("mkt_ LicenseTenure"));
			}


			mGenericWait();

			mSelectDropDown("id", mGetPropertyFromFile("mkt_marketGrpCategoryid"), mGetPropertyFromFile("mkt_marketGrpCategory"));
			mGenericWait();

			mSelectDropDown("id", mGetPropertyFromFile("mkt_marketTypeid"), mGetPropertyFromFile("mkt_marketType"));
			mGenericWait();

			mSendKeys("id", mGetPropertyFromFile("mkt_areaid"), mGetPropertyFromFile("mkt_area"));
			mGenericWait();

			mSendKeys("id", mGetPropertyFromFile("mkt_businessNameid"), mGetPropertyFromFile("mkt_businessName"));
			mGenericWait();

			//busiName = mGetText("id", mGetPropertyFromFile("mkt_businessNameid"));
			//busiName = driver.findElement(By.id(mGetPropertyFromFile("mkt_businessNameid"))).getAttribute("value");

			busiName = mGetText("id", mGetPropertyFromFile("mkt_businessNameid"), "value");
			System.out.println("Entered Business name is : "+busiName);

			mSendKeys("id", mGetPropertyFromFile("mkt_orgNameid"), mGetPropertyFromFile("mkt_orgName"));
			mGenericWait();

			//orgName = mGetText("id", mGetPropertyFromFile("mkt_orgNameid"));
			//orgName = driver.findElement(By.id(mGetPropertyFromFile("mkt_orgNameid"))).getAttribute("value");

			orgName = mGetText("id", mGetPropertyFromFile("mkt_orgNameid"), "value");

			System.out.println("Entered Organizaion name is : "+orgName);
			mTakeScreenShot();
			mscroll(1170, 630);

			// Add Details
			//mSendKeys("id", mGetPropertyFromFile("mkt_propertyNoid"), mGetPropertyFromFile("mkt_propertyNo"));
			mGenericWait();
		//	mSendKeys("id", mGetPropertyFromFile("mkt_flatNoid"), mGetPropertyFromFile("mkt_flatNo"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_businessAddrid"), mGetPropertyFromFile("mkt_businessAddr"));
			mGenericWait();
			mTakeScreenShot();
			//mSendKeys("id", mGetPropertyFromFile("mkt_businessAddr2id"), mGetPropertyFromFile("mkt_businessAddr2"));
			mGenericWait();
	
			mTakeScreenShot();
			//mSelectAutoCompleteText("id", mGetPropertyFromFile("mkt_districtid"), mGetPropertyFromFile("mkt_district"));
			
			mTakeScreenShot();
			
			mGenericWait();
		//	mSendKeys("id", mGetPropertyFromFile("mkt_busiAddPinCodeid"), mGetPropertyFromFile("mkt_busiAddPinCode"));
			mTakeScreenShot();
			/*if(driver.findElement(By.className("error-div")).isDisplayed())
			{
				String Er=driver.findElement(By.className("error-div")).getText();
				System.out.println("=======================================>"+Er);
			}*/
			
			mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("mkt_csnNumberid"), mGetPropertyFromFile("mkt_csnNumber"));
			mGenericWait();

			mTakeScreenShot();
			// License Detail

			//To add multiple License Details - code added by Jyoti dated 02-09-2016 - Can't Test due to defect D-10032
			mClick("linkText", mGetPropertyFromFile("mkt_licenseAddDetailsLinkid"));//*[@id="licenseDetailsId"]/ul/li/a
			mGenericWait();
			mWaitForVisible("xpath", mGetPropertyFromFile("mkt_licenseDtlsSubmitBtnid"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("mkt_tradeTypeid"), mGetPropertyFromFile("mkt_tradeType"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("mkt_licenseTypeid"), mGetPropertyFromFile("mkt_licenseType"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("mkt_licenseSubTypeid"), mGetPropertyFromFile("mkt_licenseSubType"));
			
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("mkt_feesTypeid"), mGetPropertyFromFile("mkt_feesType"));
			mGenericWait();
		//	mSelectDropDown("id", mGetPropertyFromFile("mkt_licenseCategoryid"), mGetPropertyFromFile("mkt_licenseCategory"));
			//mGenericWait();
			//mSelectDropDown("id", mGetPropertyFromFile("mkt_licenseSubCategoryid"), mGetPropertyFromFile("mkt_licenseSubCategory"));
			//mGenericWait();
			


			if(bplselected)
			{
				System.out.println("Bpl is selected measuring paramter and measuing units not required");
			}	
			else{
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("mkt_measuringParameterid"), mGetPropertyFromFile("mkt_measuringParameter"));
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("mkt_unitsid"), mGetPropertyFromFile("mkt_units"));
				mGenericWait();
			}


			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("mkt_licenseDtlsSubmitBtnid"));

			mCustomWait(4000);

			// New Trade Application Business Assertion

			String holder_BusinessName = mGetText("css", mGetPropertyFromFile("mkt_NewLicensebusiid"),"value");

			System.out.println("Applicant New Holder Business Name is ::: "+holder_BusinessName);	
			HolderBusinessName.add(holder_BusinessName);

			System.out.println("App time Business address of Holder is == "+HolderBusinessName);

			/*String TotPayAmt = mGetText("css", mGetPropertyFromFile("mkt_TotPayAmtid"), "value");
			System.out.println("New Trade Total Payable Amount =="+TotPayAmt);
			TotalPayAmountList.add(TotPayAmt);
			System.out.println("Total Payable Amount List=="+TotalPayAmountList);*/


			// TABLE Read for Assertion of License FEES Details

			WebElement table1 = driver.findElement(By.id("main-table"));
			List<WebElement> rows1 = table1.findElements(By.tagName("tr"));

			int rowcount1 = rows1.size();
			System.out.println("NUMBER OF ROWS IN PERSONAL DETAILS TABLE of Application ="+rowcount1);

			// Iterate To get the value of each cell in table along with element id

			//System.out.println("NUMBER OF ROWS IN THIS TABLE of Application= "+rows1.size());

			int row_num1 = 1;
			for (WebElement rowElement : rows1)
			{
				List<WebElement> cols1 = rowElement.findElements(By.xpath("td"));
				System.out.println("NUMBER OF columns IN THIS TABLE of Application= "+cols1.size());
				int col_num1 = 0;
				for(WebElement colElement : cols1)
				{
					if(row_num1 > 1)
					{
						if(col_num1 == 0)
						{

							String SrNo1 = rowElement.findElement(By.xpath("./td[1]")).getText();
							mGenericWait();
							System.out.println("License Sr. No. is == "+SrNo1);
						}



						if(col_num1 == 1)
						{
							if(row_num1 == 2)
							{
								String license_MKTlicensefeeType = rowElement.findElement(By.xpath("./td[2]")).getText();
								System.out.println("License Fees Type is Market License Fee == "+license_MKTlicensefeeType);

								MarketLicense_HolderFeeType.add(license_MKTlicensefeeType);

							}
						}


						if(col_num1 == 1)
						{
							if(row_num1 == 3)
							{
								String license_RebatefeeType = rowElement.findElement(By.xpath("./td[2]")).getText();
								System.out.println("License Fees Type is Rebate == "+license_RebatefeeType);

								Rebate_HolderFeeType.add(license_RebatefeeType);

							}
						}

						//**********************************************
						if(col_num1 == 2)
						{
							if(row_num1 == 2)
							{
								String license_MKTlicensefee = rowElement.findElement(By.xpath("./td[3]")).getText();
								System.out.println("Market License Fees in Market License Fee == "+license_MKTlicensefee);

								MarketLicense_HolderFee.add(license_MKTlicensefee);

							}
						}

						if(col_num1 == 2)
						{
							if(row_num1 == 3)
							{
								String license_Rebatefee = rowElement.findElement(By.xpath("./td[3]")).getText();
								System.out.println("Rebate in Market License Fee == "+license_Rebatefee);

								Rebate_HolderFee.add(license_Rebatefee);

							}
						}
						//**********************************************

						if(col_num1 == 3)
						{
							if(row_num1 == 2)
							{
								String MKTLicense_totalAmount = rowElement.findElement(By.xpath("./td[4]")).getText();
								System.out.println("Total Amount of Market License is == "+MKTLicense_totalAmount);

								Total_MarketLicenseFee.add(MKTLicense_totalAmount);

							}
						}


						if(col_num1 == 3)
						{
							if(row_num1 == 3)
							{
								String Rebate_totalAmount = rowElement.findElement(By.xpath("./td[4]")).getText();
								System.out.println("Total Amount of Rebate is == "+Rebate_totalAmount);

								Total_RebateFee.add(Rebate_totalAmount);
							}
						}

						if(col_num1 == 3)
						{
							if(row_num1 == 4)
							{
								String license_totalPayAmount = rowElement.findElement(By.xpath("./td[4]")).getText();
								System.out.println("License Total Payable Amount is == "+license_totalPayAmount);

								TotalPayableAmount.add(license_totalPayAmount);
								System.out.println("Total Payable Amount List is== "+TotalPayableAmount);
							}
						}

					}
					col_num1 = col_num1 + 1;

				}
				row_num1 = row_num1 + 1;


			}

			/*String[] TradeType = mGetPropertyFromFile("mkt_tradeType").split(":");
			System.out.println(TradeType);
			String[] LicenseType = mGetPropertyFromFile("mkt_licenseType").split(":");
			String[] LicenseSubType = mGetPropertyFromFile("mkt_licenseSubType").split(":");
			String[] LicenseCategory = mGetPropertyFromFile("mkt_licenseCategory").split(":");
			//String[] LicenseSubCategory = mGetPropertyFromFile("mkt_licenseSubCategory").split(":");
			String[] FeesType = mGetPropertyFromFile("mkt_feesType").split(":");
			String[] MeasuringParameter = mGetPropertyFromFile("mkt_measuringParameter").split(":");
			String[] Units = mGetPropertyFromFile("mkt_units").split(":");*/



			/*for(int i=0;i<LicenseType.length;i++)
			{


				mClick("linkText", mGetPropertyFromFile("mkt_licenseAddDetailsLinkid"));
				mGenericWait();

				mWaitForVisible("xpath", mGetPropertyFromFile("mkt_licenseDtlsSubmitBtnid"));
				mGenericWait();

				mSelectDropDown("id", mGetPropertyFromFile("mkt_tradeTypeid"), TradeType[i]);
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("mkt_licenseTypeid"), LicenseType[i]);
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("mkt_licenseSubTypeid"), LicenseSubType[i]);
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("mkt_licenseCategoryid"), LicenseCategory[i]);
				mGenericWait();
				//mSelectDropDown("id", mGetPropertyFromFile("mkt_licenseSubCategoryid"), LicenseSubCategory[i]);
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("mkt_feesTypeid"), FeesType[i]);


				if(bplselected)
				{
					System.out.println("Bpl is selected measuring paramter and measuing units not required");


				}	
				else{
					mGenericWait();
					mSelectDropDown("id", mGetPropertyFromFile("mkt_measuringParameterid"), MeasuringParameter[i]);
					mGenericWait();
					mSelectDropDown("id", mGetPropertyFromFile("mkt_unitsid"), Units[i]);
					mGenericWait();
				}


				mTakeScreenShot();
				mClick("xpath", mGetPropertyFromFile("mkt_licenseDtlsSubmitBtnid"));

				mWaitForVisible("linkText", mGetPropertyFromFile("mkt_licenseAddDetailsLinkid")); // JYOTI COMMENT
				mGenericWait();

			}*/

			mTakeScreenShot();
			mscroll(1222, 650);



			// Can't add 'Tax Details due to defect D-10791

			//Tax Details
			if(mGetPropertyFromFile("taxPayer").equals("Yes"))
			{
				mWaitForVisible("id", mGetPropertyFromFile("mkt_taxPayerYesid"));
				mGenericWait();
				mClick("id", mGetPropertyFromFile("mkt_taxPayerYesid"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("mkt_panNoid"), mGetPropertyFromFile("mkt_panNo"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("mkt_tinNoid"), mGetPropertyFromFile("mkt_tinNo"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("mkt_vatNoid"), mGetPropertyFromFile("mkt_vatNo"));
				mGenericWait();
			}
			else if(mGetPropertyFromFile("taxPayer").equals("No"))
			{
				mWaitForVisible("id", mGetPropertyFromFile("mkt_taxPayerNoid"));
				mGenericWait();
				mClick("id", mGetPropertyFromFile("mkt_taxPayerNoid"));	
				mGenericWait();
				//mSendKeys("id", mGetPropertyFromFile("mkt_panNoid"), mGetPropertyFromFile("mkt_panNo"));
				//mGenericWait();
				//mSendKeys("id", mGetPropertyFromFile("mkt_tinNoid"), mGetPropertyFromFile("mkt_tinNo"));
				//mGenericWait();
				//mSendKeys("id", mGetPropertyFromFile("mkt_vatNoid"), mGetPropertyFromFile("mkt_vatNo"));
				//mGenericWait();
			}


			// Personal Details

			///////////////////////////////////
			////////////////////////////////////
			//To add multiple Joint Owners - code added by Jyoti dated 01-09-2016
			////////////////////////////////////////
			///////////////////////////////////////

			String[] NameTitle =  mGetPropertyFromFile("mkt_applicantTitle").split(":");
			String[] FirstName =  mGetPropertyFromFile("mkt_firstName").split(":");
			String[] MiddleName =  mGetPropertyFromFile("mkt_middleName").split(":");
			String[] LastName =  mGetPropertyFromFile("mkt_lastName").split(":");
			String[] Gender =  mGetPropertyFromFile("mkt_gender").split(":");
			String[] Age =  mGetPropertyFromFile("mkt_licenseHolderAge").split(":");
			String[] Address =  mGetPropertyFromFile("mkt_licenseHolderAddrs").split(":");
			String[] PhoneNo =  mGetPropertyFromFile("mkt_licenseHolderPhoneNo").split(":");
			String[] Pincode =  mGetPropertyFromFile("mkt_licenseHolderPincode").split(":");
			String[] Email =  mGetPropertyFromFile("mkt_licenseHolderEmail").split(":");
			String[] MobNo =  mGetPropertyFromFile("mkt_licenseHolderMobNo").split(":");
			String[] AdhaarNo =  mGetPropertyFromFile("mkt_licenseHolderAdhaarNo").split(":");
			String[] UploadPhoto =  mGetPropertyFromFile("mkt_licenseHolderUploadPhoto").split(":");

			//int NT = FirstName.length;



			mWaitForVisible("css", mGetPropertyFromFile("mkt_personalAddDetailsLinkid"));
			mGenericWait();
			int i;
			//int j=Integer.parseInt(mGetPropertyFromFile("noOfLicJointOwner"));

			for (i=0; i<FirstName.length;i++)  // First Name is used to get size for iteration of for loop.
			{
				mWaitForVisible("css",mGetPropertyFromFile("mkt_personalAddDetailsLinkid"));
				mGenericWait();

				mClick("css", mGetPropertyFromFile("mkt_personalAddDetailsLinkid"));
				mGenericWait();

				mWaitForVisible("xpath", mGetPropertyFromFile("mkt_personalDtlsSubmitBtnid"));
				mGenericWait();
				//	mSelectDropDown("id", mGetPropertyFromFile("mkt_applicantTitleid"), mGetPropertyFromFile("mkt_applicantTitle"));
				mGenericWait();

				mSelectDropDown("id", mGetPropertyFromFile("mkt_applicantTitleid"), NameTitle[i]);
				String licHldrTtl = mGetPropertyFromFile("mkt_applicantTitle").toUpperCase();

				mSendKeys("id", mGetPropertyFromFile("mkt_firstNameid"), FirstName[i]);
				mGenericWait();

				//String licHldrFName = driver.findElement(By.id(mGetPropertyFromFile("mkt_firstNameid"))).getAttribute("value");

				String licHldrFName = mGetText("id", mGetPropertyFromFile("mkt_firstNameid"), "value");

				mSendKeys("id", mGetPropertyFromFile("mkt_middleNameid"), MiddleName[i]);
				mGenericWait();
				//	String licHldrMName = driver.findElement(By.id(mGetPropertyFromFile("mkt_middleNameid"))).getAttribute("value");

				String licHldrMName = mGetText("id", mGetPropertyFromFile("mkt_middleNameid"), "value");

				mSendKeys("id", mGetPropertyFromFile("mkt_lastNameid"), LastName[i]);
				mGenericWait();
				//String licHldrLName = driver.findElement(By.id(mGetPropertyFromFile("mkt_lastNameid"))).getAttribute("value");

				String licHldrLName = mGetText("id", mGetPropertyFromFile("mkt_lastNameid"), "value");

				licHolderName = licHldrTtl+licHldrFName+licHldrMName+licHldrLName;
				System.out.println("Entered license holder name is : "+licHolderName);

				mSelectDropDown("id", mGetPropertyFromFile("mkt_genderid"), Gender[i]);
				mGenericWait();

				mSendKeys("id", mGetPropertyFromFile("mkt_licenseHolderAgeid"), Age[i]);
				mGenericWait();

				mSendKeys("id", mGetPropertyFromFile("mkt_licenseHolderAddrsid"), Address[i]);
				mGenericWait();

				mSendKeys("id", mGetPropertyFromFile("mkt_licenseHolderPhoneNoid"), PhoneNo[i]);
				mGenericWait();

				mSendKeys("id", mGetPropertyFromFile("mkt_licenseHolderPincodeid"), Pincode[i]);
				mGenericWait();

				mSendKeys("id", mGetPropertyFromFile("mkt_licenseHolderEmailid"), Email[i]);
				mGenericWait();

				mSendKeys("id", mGetPropertyFromFile("mkt_licenseHolderMobNoid"), MobNo[i]);
				mGenericWait();

				mSendKeys("id", mGetPropertyFromFile("mkt_licenseHolderAdhaarNoid"), AdhaarNo[i]);
				mGenericWait();

				mUpload("id", mGetPropertyFromFile("mkt_licenseHolderUploadPhotoid"), UploadPhoto[i]);
				mGenericWait();

				mTakeScreenShot();
				mClick("xpath", mGetPropertyFromFile("mkt_personalDtlsSubmitBtnid"));
				mGenericWait();

				if(i>=FirstName.length)
				{
					break;
				}else
				{
					System.out.println("Joint owner(s) added");
				}
			}

			System.out.println("I m out of Joint Owner for loop"); 


			/*
			mWaitForVisible("css", mGetPropertyFromFile("mkt_personalAddDetailsLinkid"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("mkt_personalAddDetailsLinkid"));
			mGenericWait();
			mWaitForVisible("xpath", mGetPropertyFromFile("mkt_personalDtlsSubmitBtnid"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("mkt_applicantTitleid"), mGetPropertyFromFile("mkt_applicantTitle"));
			mGenericWait();
			//String licHldrTtl = mGetText("id", mGetPropertyFromFile("mkt_applicantTitleid"));
			//String licHldrTtl = driver.findElement(By.id(mGetPropertyFromFile("mkt_applicantTitleid"))).getAttribute("value");
			String licHldrTtl = mGetPropertyFromFile("mkt_applicantTitle").toUpperCase();
			//String licHldrTitle = licHldrTtl.toUpperCase();
			mSendKeys("id", mGetPropertyFromFile("mkt_firstNameid"), mGetPropertyFromFile("mkt_firstName"));
			mGenericWait();
			//String licHldrFName = mGetText("id", mGetPropertyFromFile("mkt_firstNameid"));
			String licHldrFName = driver.findElement(By.id(mGetPropertyFromFile("mkt_firstNameid"))).getAttribute("value");
			mSendKeys("id", mGetPropertyFromFile("mkt_middleNameid"), mGetPropertyFromFile("mkt_middleName"));
			mGenericWait();
			//String licHldrMName = mGetText("id", mGetPropertyFromFile("mkt_middleNameid"));
			String licHldrMName = driver.findElement(By.id(mGetPropertyFromFile("mkt_middleNameid"))).getAttribute("value");
			mSendKeys("id", mGetPropertyFromFile("mkt_lastNameid"), mGetPropertyFromFile("mkt_lastName"));
			mGenericWait();
			//String licHldrLName = mGetText("id", mGetPropertyFromFile("mkt_lastNameid"));
			String licHldrLName = driver.findElement(By.id(mGetPropertyFromFile("mkt_lastNameid"))).getAttribute("value");

			licHolderName = licHldrTtl+licHldrFName+licHldrMName+licHldrLName;
			System.out.println("Entered license holder name is : "+licHolderName);

			mSelectDropDown("id", mGetPropertyFromFile("mkt_genderid"), mGetPropertyFromFile("mkt_gender"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_licenseHolderAgeid"), mGetPropertyFromFile("mkt_licenseHolderAge"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_licenseHolderAddrsid"), mGetPropertyFromFile("mkt_licenseHolderAddrs"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_licenseHolderPhoneNoid"), mGetPropertyFromFile("mkt_licenseHolderPhoneNo"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_licenseHolderPincodeid"), mGetPropertyFromFile("mkt_licenseHolderPincode"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_licenseHolderEmailid"), mGetPropertyFromFile("mkt_licenseHolderEmail"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_licenseHolderMobNoid"), mGetPropertyFromFile("mkt_licenseHolderMobNo"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_licenseHolderAdhaarNoid"), mGetPropertyFromFile("mkt_licenseHolderAdhaarNo"));
			mGenericWait();
			mUpload("id", mGetPropertyFromFile("mkt_licenseHolderUploadPhotoid"), mGetPropertyFromFile("mkt_licenseHolderUploadPhoto"));
			mGenericWait();
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("mkt_personalDtlsSubmitBtnid"));*/
			mGenericWait();
			mUpload("id", mGetPropertyFromFile("mkt_uploadDocid"), mGetPropertyFromFile("mkt_uploadDoc"));
			mGenericWait();

			mUpload("id", mGetPropertyFromFile("mkt_uploadDocid1"), mGetPropertyFromFile("mkt_uploadDoc1"));
			mGenericWait();

			//@
         	mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("mkt_submitBtnid"));

			mWaitForVisible("id", mGetPropertyFromFile("mkt_proceedBtnid"));
			mGenericWait();

			String saveMsg=mGetText("css", mGetPropertyFromFile("mkt_newMarketLicenseSaveMsgid"));
			System.out.println(saveMsg);
			Pattern appNum = Pattern.compile("[0-9]+");
			Matcher appNumber = appNum.matcher(saveMsg);

			if(appNumber.find()) {				
				appNo = appNumber.group();
				System.out.println("Application No is: "+appNo);
				appNo=appNo.trim();
			}

			///mAppNoArray(locatortype, locator);
			mAppNoArray(appNo);

			strippedSaveString = saveMsg.replace(appNo, "");
			System.out.println(strippedSaveString);
			System.out.println("submit message:::::::::::: "+mGetPropertyFromFile("mkt_newLicenseSaveMsg"));
			ChhngBusiNm_Submit_Msg.add(mGetPropertyFromFile("mkt_newLicenseSaveMsg"));

			mAssert(mGetPropertyFromFile("mkt_newLicenseSaveMsg"), strippedSaveString, "Message does not match, Expected: "+mGetPropertyFromFile("mkt_newLicenseSaveMsg")+" || Actual: "+strippedSaveString);

			//mAssert(strippedSaveString, ChhngBusiNm_Submit_Msg, "Chng Busi Name Save Msg Assertion F ail  Actual Message :"+strippedSaveString+" Expected Message :"+ChhngBusiNm_Submit_Msg);

			mTakeScreenShot();


			//	Arraylist Assertion START by Jyoti

			// TABLE Read for Assertion of New Holder Details

			WebElement table = driver.findElement(By.id("gridPersonalDetails"));
			List<WebElement> rows = table.findElements(By.tagName("tr"));

			int rowcount = rows.size();
			System.out.println("NUMBER OF ROWS IN Application time License Fees Details ="+rowcount);

			// Iterate To get the value of each cell in table along with element id

			System.out.println("NUMBER OF ROWS IN IN Application time License Fees Details= "+rows.size());

			int row_num = 1;
			for (WebElement rowElement : rows)
			{
				List<WebElement> cols = rowElement.findElements(By.xpath("td"));
				int col_num = 1;
				for(WebElement colElement : cols)
				{
					if(row_num > 1)
					{
						if(col_num == 0)
						{

							String SrNo = rowElement.findElement(By.xpath("./td")).getText();
							mGenericWait();
							System.out.println("Sr. No. is== "+SrNo);
						}

						if(col_num == 1)
						{
							String trade_holdername = rowElement.findElement(By.xpath("./td[2]")).getText();
							System.out.println("Application time Holder Name is == "+trade_holdername);

							HolderName.add(trade_holdername);

							System.out.println("Application time Holder Name List is == "+HolderName);

						}
						col_num = col_num + 1;	
					}
				}
				row_num = row_num + 1;
			}

			//String  trade_holdername=(mGetPropertyFromFile("mkt_applicantTitle")+" "+mGetPropertyFromFile("mkt_firstName")+" "+mGetPropertyFromFile("mkt_middleName")+" "+mGetPropertyFromFile("mkt_lastName"));

			mGenericWait();

			//mWaitForVisible("id", mGetPropertyFromFile("mkt_proceedBtnid"));
			mClick("id", mGetPropertyFromFile("mkt_proceedBtnid"));
			mCustomWait(3000);
			mTakeScreenShot();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			mTakeScreenShot();

			throw new MainetCustomExceptions("Error in newMarketLicense method");
		}
	}





	public void licensePrinting()
	{		
		try
		{
			
			mWaitForVisible("linkText", mGetPropertyFromFile("mkt_marketLicenseLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("mkt_marketLicenseLinkid"));

			mWaitForVisible("xpath", mGetPropertyFromFile("mkt_reportsLinkid"));
			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("mkt_reportsLinkid"));

			mWaitForVisible("linkText", mGetPropertyFromFile("mkt_issuanceOfNewLicenseLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("mkt_issuanceOfNewLicenseLinkid"));
			//applicationNo
			

			mWaitForVisible("linkText", mGetPropertyFromFile("mkt_licensePrintingid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("mkt_licensePrintingid"));
 
			mWaitForVisible("xpath", mGetPropertyFromFile("mkt_listBtnid"));
			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("mkt_listBtnid"));

			mWaitForVisible("name", mGetPropertyFromFile("mkt_licenseNoid"));
			mGenericWait();


			//mSendKeys("name", mGetPropertyFromFile("mkt_licenseNoid"), "71116032100004");

		IndOrDep("name","mkt_licenseNoid","applicationNo");
		//	IndOrDep("id","mkt_directnoid","applicationNo");
			mGenericWait(); 
		//	mSendKeys("id", mGetPropertyFromFile("mkt_directnoid"), mGetPropertyFromFile("apppno"));
			mGenericWait(); 

			mClick("linkText", mGetPropertyFromFile("mkt_searchBtnid"));
		mGenericWait();
///
			mWaitForVisible("css", mGetPropertyFromFile("mkt_addBtnid"));
			//mGenericWait();
			//mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("mkt_addBtnid"));

			//mWaitForVisible("linkText", mGetPropertyFromFile("mkt_printBtnid"));
			//mGenericWait();
			mTakeScreenShot();
			mClick("linkText", mGetPropertyFromFile("mkt_printBtnid"));
					mCustomWait(10000);
			mChallanPDFReader();

			////////////////// Market License Print Assertion Method Call

			
			LicensePrinting_Assertion();
		
			 // store the current session
		
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in changeInNameOfBusiness method");
		}

	}

	public void changeInNameOfBusiness()
	{
		try
		{
			mWaitForVisible("xpath", mGetPropertyFromFile("mkt_listBtnid"));
			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("mkt_listBtnid"));
			mGenericWait();

			///////////////SWAP code for Excel Writing

			if(mGetPropertyFromFile("Execution_All").equalsIgnoreCase("ALL"))
			{
				licenseNo = mGetPropertyFromFile("mkt_Licencenumber");
				//mSendKeys("name", mGetPropertyFromFile("mkt_licenseNoid"),licenseNo );
			mSendKeys("name", mGetPropertyFromFile("mkt_licenseNoid"),licenseNo);
			licenseNo="";
			}
			else
			{
				licenseNo = mGetPropertyFromFile("mkt_licenseNo");
				mWaitForVisible("name", mGetPropertyFromFile("mkt_licenseNoid"));
				mGenericWait();
				mSendKeys("name", mGetPropertyFromFile("mkt_licenseNoid"),licenseNo);
				//IndOrDep("name", "mkt_licenseNoid", "applicationNo");
				//IndOrDep("name", "mkt_licenseNoid", "MKT_issueDupLicLicNoTextbox_Data");
				
				licenseNo="";
			}

			
			
			
			
			
			
			
			
			
			
			
			
			
			
	/*d		if(mGetPropertyFromFile("Execution_All").equalsIgnoreCase("ALL"))
			{
				licenseNo = mGetPropertyFromFile("mkt_Licencenumber");
				mSendKeys("name", mGetPropertyFromFile("mkt_licenseNoid"),licenseNo );
				chngBusiNm_licenseno_list.add(licenseNo);
			}
			else
			{

				mWaitForVisible("name", mGetPropertyFromFile("mkt_licenseNoid"));
				mGenericWait();

				IndOrDep("name", "mkt_licenseNoid", "applicationNo");
			}*/



			//mSendKeys("name", mGetPropertyFromFile("mkt_licenseNoid"), mGetPropertyFromFile("mkt_licenseNo"));
			//mSendKeys("name", mGetPropertyFromFile("mkt_licenseNoid"), licenseNo);

			//mSendKeys("name", mGetPropertyFromFile("mkt_licenseNoid"), "TLB00037/00");

			/*mWaitForVisible("name", mGetPropertyFromFile("mkt_licenseNoid"));
			mGenericWait();

			IndOrDep("name", "mkt_licenseNoid", "applicationNo");*/

			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("mkt_licNoSearchBtnid"));

			mTakeScreenShot();
			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("mkt_licNoAddBtnid"));

			mWaitForVisible("name", mGetPropertyFromFile("mkt_chngBusiNmSaveid"));
			mGenericWait();
			mSendKeys("name", mGetPropertyFromFile("mkt_newBusinessNameid"), mGetPropertyFromFile("mkt_newBusinessName"));
			mGenericWait();
			mUpload("id", mGetPropertyFromFile("mkt_chngBusiNmUploadid"), mGetPropertyFromFile("mkt_chngBusiNmUpload"));
			mGenericWait();

			mUpload("id", mGetPropertyFromFile("mkt_chngBusiNmUploadid1"), mGetPropertyFromFile("mkt_chngBusiNmUpload1"));
			mGenericWait();

			mTakeScreenShot();
			mClick("name", mGetPropertyFromFile("mkt_chngBusiNmSaveid"));

			mWaitForVisible("id", mGetPropertyFromFile("mkt_chngBusiNmProceedid"));
			mGenericWait();

			String saveMsg=mGetText("css", mGetPropertyFromFile("mkt_ChngInBusinessNameSaveMsgid"));
			System.out.println(saveMsg);
			Pattern appNum = Pattern.compile("[0-9]+");
			Matcher appNumber = appNum.matcher(saveMsg);

			if(appNumber.find()) {				
				appNo = appNumber.group();
				System.out.println("Application No is: "+appNo);
				appNo.trim();
				mAppNoArray(appNo); // To store application no in array
			}

			strippedSaveString = saveMsg.replace(appNo, "");
			System.out.println(strippedSaveString);
			mAssert(mGetPropertyFromFile("mkt_chngInBusiNameSaveMsg"), strippedSaveString, "Message does not match, Expected: "+mGetPropertyFromFile("mkt_chngInBusiNameSaveMsg")+" || Actual: "+strippedSaveString);
			mGenericWait();


			////////// Holder Name Assertion in Change in Business Name service


			String ChngInBusiness_HolderName = mGetText("css", mGetPropertyFromFile("MKT_chngBusi_holderNameid"), "value");
			System.out.println("Holder Name in Change in Business Name is ::: "+ChngInBusiness_HolderName);	

			ChangeBusinessNameHolderName.add(ChngInBusiness_HolderName);
			System.out.println("Application time Chng Busi Nm Holder Name List== "+ChangeBusinessNameHolderName);


			//Old Business Name assertion

			String ChngInBusiness_oldBusinessName = mGetText("css", mGetPropertyFromFile("mkt_oldBusinessNameid"), "value");
			System.out.println("Old Business Name in Change in Business Name is ::: "+ChngInBusiness_oldBusinessName);	

			ChangeBusinessOldBusinessName.add(ChngInBusiness_oldBusinessName);
			System.out.println("Application time Old Business Name is=="+ChangeBusinessOldBusinessName);


			//New Business Name assertion

			String ChngInBusiness_newBusinessName = mGetText("css", mGetPropertyFromFile("mkt_oldBusinessNameid"), "value");
			System.out.println("New Business Name in Change in Business Name is ::: "+ChngInBusiness_newBusinessName);		     
			ChangeBusinessNewBusinessName.add(ChngInBusiness_newBusinessName);


			mGenericWait();
			mTakeScreenShot();

			mClick("id", mGetPropertyFromFile("mkt_chngBusiNmProceedid"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in changeInNameOfBusiness method");
		}
	}






	//CANCELLATION By FORCE by Jyoti 21-11-2016

	@Test(enabled=false)
	public void market_CancellationByForce_1stFlow() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			InspectionDtls();
			Showcause();
			CancellationForce();
			CommonUtilsAPI.marketErrorMsg.assertAll();


		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in market_CancellationByForce method");
		}
	}


	//CANCELLATION By FORCE 2nd Flow by hiren 28-11-2016
	// changes done on 29-11-2016

	@Test(enabled=false)
	public void market_CancellationByForce_2ndFlow() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			MarketCustomErrorMessages.market_m_errors.entrySet().clear();
			//InspectionDtls();
			//Showcause();
			Cancellation_Hearing_Date();
			//Cancellation_Hearing_Details_Entry();
			//CancellationForce();
			CommonUtilsAPI.marketErrorMsg.assertAll();  
		} 

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in market_CancellationByForce_2ndFlow method");
		}
	}



	// INSPECTION DETAILS by Jyoti 19-11-2016


	public void InspectionDtls() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("MKT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			common.selectUlb();
			common.departmentLogin(mGetPropertyFromFile("deptUserName"), mGetPropertyFromFile("deptPassword"));
			InspectionDetails();
			common.logOut();
			common.finalLogOut();
			mCloseBrowser();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in InspectionDtls method");
		}

	}


	public void InspectionDetails() throws Exception
	{
		try
		{
			mGenericWait();
			mNavigation(mGetPropertyFromFile("mkt_deptonlserviceLinkid"), mGetPropertyFromFile("mkt_marketLicenseLinkid"), mGetPropertyFromFile("mkt_newMarketLicenseSubModuleLinkid"), mGetPropertyFromFile("mkt_newMarketLicenseServiceLinkid"));

			mWaitForVisible("css", mGetPropertyFromFile("mkt_InspAddDetailsid"));
			mTakeScreenShot();
			mCustomWait(1000); 
			mClick("css", mGetPropertyFromFile("mkt_InspAddDetailsid"));

			mWaitForVisible("css", mGetPropertyFromFile("mkt_InspListBtnid"));
			mTakeScreenShot();			
			mGenericWait();

			mClick("css", mGetPropertyFromFile("mkt_InspListBtnid"));
			mGenericWait();

			mWaitForVisible("css", mGetPropertyFromFile("mkt_InspSearchBtnid"));
			mClick("css", mGetPropertyFromFile("mkt_InspSearchBtnid"));
			mCustomWait(1000); 
			mTakeScreenShot();

			mWaitForVisible("xpath", mGetPropertyFromFile("mkt_InspFindRecordsBtnid"));
			mClick("xpath", mGetPropertyFromFile("mkt_InspFindRecordsBtnid"));

			mWaitForVisible("id", mGetPropertyFromFile("mkt_InspFindBtnid"));
			mGenericWait();

		///	dIndOrDep("id", "mkt_InspLicenseNoid", "applicationNo");
			
			///////
			
			
			if(mGetPropertyFromFile("Execution_All").equalsIgnoreCase("ALL"))
			{
				licenseNo = mGetPropertyFromFile("mkt_Licencenumber");
				//mSendKeys("name", mGetPropertyFromFile("mkt_licenseNoid"),licenseNo );
			mSendKeys("id", mGetPropertyFromFile("mkt_InspLicenseNoid"),licenseNo);
			licenseNo="";
			}
			else
			{
				licenseNo = mGetPropertyFromFile("mkt_InspLicenseNodata");
				mWaitForVisible("id", mGetPropertyFromFile("mkt_InspLicenseNoid"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("mkt_InspLicenseNoid"),licenseNo);
				//IndOrDep("name", "mkt_licenseNoid", "applicationNo");
				//IndOrDep("name", "mkt_licenseNoid", "MKT_issueDupLicLicNoTextbox_Data");
				
				licenseNo="";
			}
			
			
			
			
			
			
			
			////
			
			
			
			
			

			mGenericWait();

			mClick("id", mGetPropertyFromFile("mkt_InspFindBtnid"));
			mCustomWait(2500);
			mTakeScreenShot();
			mGenericWait();

			mClick("css", mGetPropertyFromFile("mkt_AddLicenseBtnid"));
			mGenericWait();

			mSendKeys("name", mGetPropertyFromFile("mkt_InspectionInPresenceOfid"), mGetPropertyFromFile("mkt_InspectionInPresenceOfdata"));
			mGenericWait();

			mSendKeys("name", mGetPropertyFromFile("mkt_InspectionRemarksid"), mGetPropertyFromFile("mkt_InspectionRemarksdata"));
			mCustomWait(2000);
			mTakeScreenShot();			

			mClick("css", mGetPropertyFromFile("mkt_InspectionInspectorNameListid"));
			mWaitForVisible("id", mGetPropertyFromFile("mkt_InspectionInspectorNameid"));
			mGenericWait();

			mSendKeys("name", mGetPropertyFromFile("mkt_InspectionInspectorNameid"), mGetPropertyFromFile("mkt_InspectionInspectorNamedata"));
			mGenericWait();

			mClick("xpath", mGetPropertyFromFile("mkt_InspectorNameSearchBtnid"));
			mCustomWait(1500);

			//capturing License No.:
			String inspection_licenseno=mGetText("name",mGetPropertyFromFile("mkt_inspection_licenseno_id"),"values");
			inspection_licenseno_list.add(inspection_licenseno);
System.out.println("inspection_licenseno_list->"+inspection_licenseno_list);
			//capturing inspection date 
			String inspection_date=mGetText("name",mGetPropertyFromFile("mkt_inspection_date_id"),"values");
			inspection_date_list.add(inspection_date);
			//capturing Remarks
			System.out.println("inspection_date_list->"+inspection_date_list);
			String inspection_Remarks=mGetText("name",mGetPropertyFromFile("mkt_inspection_Remark_id"),"values");
			inspection_Remarks_list.add(inspection_Remarks);
			//inspecter name
			System.out.println("inspection_Remarks_list->"+inspection_Remarks_list);
			
			String Inspection_inspecter_name=mGetText("name",mGetPropertyFromFile("mkt_inspection_Inspector_id"),"values");


			Inspection_inspecter_name_list.add(Inspection_inspecter_name);
			System.out.println("Inspection_inspecter_name_list->"+Inspection_inspecter_name_list);
			//inspection presense of 
			String Inspection_presense_name=mGetText("name",mGetPropertyFromFile("mkt_inspection_presense_id"),"values");


			Inspection_presense_name_list.add(Inspection_presense_name);

			System.out.println("Inspection_presense_name_list->"+Inspection_presense_name_list);


			mTakeScreenShot(); 
			mClick("css", mGetPropertyFromFile("mkt_AddInspectorNameid"));
			mGenericWait();
			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("mkt_SaveInspectionDetailsid"));
			mGenericWait();

			String msg=mGetText("css",  mGetPropertyFromFile("mkt_InspectionProceedMsgid"));
			mAssert(msg,mGetPropertyFromFile("mkt_InspectionProceedMsgdata"), "Message does not match, Expected: "+mGetPropertyFromFile("mkt_InspectionProceedMsgdata")+" || Actual: "+msg);
			mCustomWait(1000); 
			mTakeScreenShot();  

			mClick("css", mGetPropertyFromFile("mkt_ProceedInspectionDetailsid"));
			mChallanPDFReader();	
			api.PdfPatterns.NtlInspectionDetailsPDF(pdfoutput);
		/*	mAssert(report_inspection_licenseno_list, inspection_licenseno_list, "License number is not matching with inspection");
			mAssert(report_inspection_date_list, inspection_date_list, "Inspection date is not matching");
			mAssert(report_inspection_Remarks_list, inspection_Remarks_list, "Inspection remarks are not matched");*/
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in InspectionDetails method");
		}
	}




	// done by hiren kathiria on 19/11/2016			


	public void Showcause() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("MKT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			common.selectUlb();
			common.departmentLogin(mGetPropertyFromFile("deptUserName"), mGetPropertyFromFile("deptPassword"));
			showcausenotice();
			common.logOut();
			common.finalLogOut();
			mCloseBrowser();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in InspectionDtls method");
		}

	}


	public void showcausenotice()
	{
		try{
			mNavigation(mGetPropertyFromFile("mkt_Registration&LicensingLinkid"),mGetPropertyFromFile("mkt_TransactionLinkid"),mGetPropertyFromFile("mkt_MarketInspectionLinkid"),mGetPropertyFromFile("mkt_ShowCauseNoticeLinkid"));

			mWaitForVisible("id", mGetPropertyFromFile("mkt_shCaNoDivisionid"));
			mCustomWait(1000); 
			mSelectDropDown("id",mGetPropertyFromFile("mkt_shCaNoDivisionid"),mGetPropertyFromFile("mkt_shCaNoDivisiondata"));
			mCustomWait(1000);
			mTakeScreenShot();

			mCustomWait(1000); 
			mClick("linkText", mGetPropertyFromFile("mkt_shCaNoAddDetailsLinkid"));

			mWaitForVisible("css", mGetPropertyFromFile("mkt_shCaNoLicNoListid"));
			mClick("css", mGetPropertyFromFile("mkt_shCaNoLicNoListid"));

			mWaitForVisible("linkText", mGetPropertyFromFile("mkt_shCaNoLicNoSearchBtnid"));
			mCustomWait(1500); 
			mClick("linkText", mGetPropertyFromFile("mkt_shCaNoLicNoSearchBtnid"));

			mWaitForVisible("css", mGetPropertyFromFile("mkt_shCaNoLicNoSearchIconid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("mkt_shCaNoLicNoSearchIconid"));

			mWaitForVisible("id", mGetPropertyFromFile("mkt_shCaNoLicNoGridSearchid"));
			mCustomWait(1000);
			//IndOrDep("id", "mkt_shCaNoLicNoGridSearchid", "applicationNo");
////////////////////////////////////////////////////////
			
			if(mGetPropertyFromFile("Execution_All").equalsIgnoreCase("ALL"))
			{
				licenseNo = mGetPropertyFromFile("mkt_Licencenumber");
				//mSendKeys("name", mGetPropertyFromFile("mkt_licenseNoid"),licenseNo );
			mSendKeys("id", mGetPropertyFromFile("mkt_shCaNoLicNoGridSearchid"),licenseNo);
			licenseNo="";
			}
			else
			{
				licenseNo = mGetPropertyFromFile("mkt_InspLicenseNodata");
				mWaitForVisible("id", mGetPropertyFromFile("mkt_shCaNoLicNoGridSearchid"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("mkt_shCaNoLicNoGridSearchid"),licenseNo);
				//IndOrDep("name", "mkt_licenseNoid", "applicationNo");
				//IndOrDep("name", "mkt_licenseNoid", "MKT_issueDupLicLicNoTextbox_Data");
				
				licenseNo="";
			}
			
			
			
			/////////////////////////////
			
			
			
			
			mCustomWait(1000);
			mTakeScreenShot();
			mClick("id", mGetPropertyFromFile("mkt_shCaNoLicNoFindBtnid"));

			mWaitForVisible("css", mGetPropertyFromFile("mkt_shCaNoAddLicNoid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("mkt_shCaNoAddLicNoid"));

			mWaitForVisible("id", mGetPropertyFromFile("mkt_shCaNoNoticeTypeid"));
			mSelectDropDown("id",mGetPropertyFromFile("mkt_shCaNoNoticeTypeid"),mGetPropertyFromFile("mkt_shCaNoNoticeTypedata"));

			//mDateSelect("id",mGetPropertyFromFile("mkt_shCaNoNoticeDateid"),mGetPropertyFromFile("mkt_shCaNoNoticeDatedata"));				
			mSendKeys("id",mGetPropertyFromFile("mkt_shCaNoNoticeDateid"),mGetPropertyFromFile("mkt_shCaNoNoticeDatedata"));
			mSendKeys("id", mGetPropertyFromFile("mkt_shCaNoRemarksid"),mGetPropertyFromFile("mkt_shCaNoRemarksdata"));
			mCustomWait(1000);
			mTakeScreenShot();

			mCustomWait(1500); 
			mClick("id", mGetPropertyFromFile("mkt_shCaNoInspectorNameid"));

			mWaitForVisible("id", mGetPropertyFromFile("mkt_shCaNoInspectorNameSearchBtnid"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("mkt_shCaNoEmployeeNameid"),mGetPropertyFromFile("mkt_shCaNoEmployeeNamedata"));
			mCustomWait(1500); 
			mClick("linkText", mGetPropertyFromFile("mkt_shCaNoInspectorNameSearchBtnid"));

			mWaitForVisible("css", mGetPropertyFromFile("mkt_shCaNoAddInspectorNameid"));
			mCustomWait(1000); 
			mTakeScreenShot(); 
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("mkt_shCaNoAddInspectorNameid"));
 
			mWaitForVisible("id", mGetPropertyFromFile("mkt_shCaNoReasonDateid"));
			mCustomWait(1000);
		//	mDateSelect("id",mGetPropertyFromFile("mkt_shCaNoReasonDateid"),mGetPropertyFromFile("mkt_shCaNoReasonDatedata"));				
			mSendKeys("id",mGetPropertyFromFile("mkt_shCaNoReasonDateid"),mGetPropertyFromFile("mkt_shCaNoReasonDatedata"));
			mCustomWait(1500);
			mSendKeys("id", mGetPropertyFromFile("mkt_shCaNoReasonRemarkid"),mGetPropertyFromFile("mkt_shCaNoReasonRemarkdata"));

			mCustomWait(1000);
			mscroll(0, 150); 
			mCustomWait(1000);
			mTakeScreenShot(); 
			///capturing 
			String showcause_license_no=mGetText("name",mGetPropertyFromFile("mkt_showcause_licenseno"),"value");
			showcause_license_no_list.add(showcause_license_no);

			String showcause_noticetype=mGetText("name",mGetPropertyFromFile("mkt_showcause_notictype"),"value");
			showcause_noticetype_list.add(showcause_noticetype);


			String showcause_holdername=mGetText("name",mGetPropertyFromFile("mkt_showcause_HolderName"),"value");
			showcause_holdername_list.add(showcause_holdername);


			String showcause_BusinessName=mGetText("name",mGetPropertyFromFile("mkt_showcause_BusinessName"),"value");
			showcause_businessname_list.add(showcause_BusinessName);



			String showcause_orgname=mGetText("name",mGetPropertyFromFile("mkt_showcause_orgname"),"value");
			showcause_orgname_list.add(showcause_orgname);


			String showcase_Noticedate=mGetText("name",mGetPropertyFromFile("mkt_showcase_Noticedate_id"),"value");
			showcase_Noticedate_list.add(showcase_Noticedate);


			String showcase_remarks=mGetText("name",mGetPropertyFromFile("mkt_showcase_remarks_id"),"value");
			showcase_remarks_list.add(showcase_remarks);

			String showcase_inspector_name=mGetText("name",mGetPropertyFromFile("mkt_showcase_inspector_name"),"value");
			showcase_inspector_name_list.add(showcase_inspector_name);


			String showcase_date=mGetText("name",mGetPropertyFromFile("mkt_showcase_date"),"value");
			showcase_date_list.add(showcase_date);


			String showcase_Reason=mGetText("name",mGetPropertyFromFile("mkt_showcase_Reason"),"value");
			showcase_Reason_list.add(showcase_Reason);



			mClick("css", mGetPropertyFromFile("mkt_shCaNoSubmitBtnid"));

			mWaitForVisible("id", mGetPropertyFromFile("mkt_shCaNoProceedBtnid"));
			mCustomWait(2000);

			String msg=mGetText("css",  mGetPropertyFromFile("mkt_shCaNoProceedMsgid"));
			mAssert(msg,mGetPropertyFromFile("mkt_shCaNoProceedMsgdata"), "Message does not match, Expected: "+mGetPropertyFromFile("mkt_shCaNoProceedMsgdata")+" || Actual: "+msg);
			mCustomWait(1000); 
			mTakeScreenShot(); 
			mClick("id", mGetPropertyFromFile("mkt_shCaNoProceedBtnid"));
			mCustomWait(3000);
			mChallanPDFReader();
			api.PdfPatterns.NtlShowCauseNoticePDF(pdfoutput);
			mAssert(showcause_holdername_pdf, showcause_holdername_list, "holder name in showacause is not matching");
			mAssert(showcause_businessname_pdf, showcause_businessname_list, "business namename in showacause is not matching");
			mAssert(showcause_InspectorName_pdf, showcase_inspector_name_list, "inspector name in showacause is not matching");
			mAssert(showcause_noticereason_pdf, showcase_Reason_list, "reason is not matched in showacause is not matching");
			mAssert(showcause_noticedate_pdf, showcase_Noticedate_list, "reason is not matched in showacause is not matching");





		}

		catch(Exception e)
		{
			e.printStackTrace();	

		}			
	}	


	public void CancellationForce() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("MKT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			common.selectUlb();
			common.departmentLogin(mGetPropertyFromFile("deptUserName"), mGetPropertyFromFile("deptPassword"));			
			CancellationByForce();
			common.logOut();
			common.finalLogOut();
			mCloseBrowser();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in CancellationForce method");
		}

	}




	public void Cancellation_Hearing_Date() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("MKT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			common.selectUlb();
			common.departmentLogin(mGetPropertyFromFile("deptUserName"), mGetPropertyFromFile("deptPassword"));			
			cancellation_hearing_date();
			common.logOut();
			common.finalLogOut();
			mCloseBrowser();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Cancellation_Hearing_Date method");
		}

	}


	public void Cancellation_Hearing_Details_Entry() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("MKT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			common.selectUlb();
			common.departmentLogin(mGetPropertyFromFile("deptUserName"), mGetPropertyFromFile("deptPassword"));			
			cancellation_hearing_details_entry();
			common.logOut();
			common.finalLogOut();
			mCloseBrowser();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Cancellation_Hearing_Details_Entry method");
		}

	}




	public void CancellationByForce()
	{
		try
		{
			mGenericWait();
		//	s mNavigation("mkt_onlServicesid", "mkt_moduleNameid", "mkt_serviceNameid");
			mNavigation("mkt_onlServicesid","mkt_moduleNameid", "mkt_serviceNameid");

			mWaitForVisible("css", mGetPropertyFromFile("mkt_cancelSave&Proceedid"));
			mCustomWait(1500);
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("mkt_cancelLicenseListid"));

			mWaitForVisible("css", mGetPropertyFromFile("mkt_cancelLicenseSearchBtnid"));
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("mkt_cancelLicenseSearchBtnid"));

			mWaitForVisible("css", mGetPropertyFromFile("mkt_cancelLicenseSearchIconid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("mkt_cancelLicenseSearchIconid"));

			mWaitForVisible("css", mGetPropertyFromFile("mkt_cancelLicenseGridSearchid"));
			mCustomWait(1000); 			
			//IndOrDep("css", "mkt_cancelLicenseGridSearchid", "applicationNo");
			/////////////
			
			if(mGetPropertyFromFile("Execution_All").equalsIgnoreCase("ALL"))
			{
				licenseNo = mGetPropertyFromFile("mkt_Licencenumber");
				//mSendKeys("name", mGetPropertyFromFile("mkt_licenseNoid"),licenseNo );
			mSendKeys("css", mGetPropertyFromFile("mkt_cancelLicenseGridSearchid"),licenseNo);
			licenseNo="";
			}
			else
			{
				licenseNo = mGetPropertyFromFile("mkt_InspLicenseNodata");
				mWaitForVisible("css", mGetPropertyFromFile("mkt_cancelLicenseGridSearchid"));
				mGenericWait();
				mSendKeys("css", mGetPropertyFromFile("mkt_cancelLicenseGridSearchid"),licenseNo);
				//IndOrDep("name", "mkt_licenseNoid", "applicationNo");
				//IndOrDep("name", "mkt_licenseNoid", "MKT_issueDupLicLicNoTextbox_Data");
				
				licenseNo="";
			}
			
			
			
			
			
			
			
			
			
			
			//////////////////
			mGenericWait();
			mTakeScreenShot();
			mClick("id", mGetPropertyFromFile("mkt_cancelLicenseFindIconid"));

			mWaitForVisible("css", mGetPropertyFromFile("mkt_cancelAddLicenseImageid"));
			mClick("css", mGetPropertyFromFile("mkt_cancelAddLicenseImageid"));
			mGenericWait();

			mDateSelect("name", mGetPropertyFromFile("mkt_cancelDateid"), mGetPropertyFromFile("mkt_cancelDatedata"));
			mGenericWait();
			mSendKeys("name", mGetPropertyFromFile("mkt_cancelReasonid"), mGetPropertyFromFile("mkt_cancelReasondata"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("mkt_cancelSave&Proceedid"));
			mGenericWait();

			String msg=mGetText("css",  mGetPropertyFromFile("mkt_cancelProceedMsgid"));

			mAssert(msg,mGetPropertyFromFile("mkt_cancelProceedMsgdata"), "Message does not match, Expected: "+mGetPropertyFromFile("mkt_cancelProceedMsgdata")+" || Actual: "+msg);
			mCustomWait(1000); 
			mTakeScreenShot(); 
			// cancelation by force capturing
		/*	String cbf_licenseno=mGetText("name", mGetPropertyFromFile("mkt_cbf_licenceno"), "value");

			cbf_licenseno_list.add(cbf_licenseno);

			mAssert(CBF_LicenceNo_pdf_list, cbf_licenseno_list, "license no is not matching");

			String cbf_canclationdate=mGetText("name", mGetPropertyFromFile("mkt_cbf_canclationdate"), "value");

			cbf_canclationdate_list.add(cbf_canclationdate);



			String cbf_cancereason=mGetText("name", mGetPropertyFromFile("mkt_cbf_cancereason"), "value");

			cbf_cancereason_list.add(cbf_cancereason);


			String cbf_canceledby=mGetText("css", mGetPropertyFromFile("mkt_cbf_canceledby"), "value");

			cbf_canceledby_list.add(cbf_canceledby);
*/



mWaitForVisible("id", mGetPropertyFromFile("mkt_cancelProceedBtnid"));
			

			mClick("id", mGetPropertyFromFile("mkt_cancelProceedBtnid"));
			mCustomWait(1000);
			mChallanPDFReader();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in CancellationForce method");
		}

	}




	public void cancellation_hearing_date()
	{

		try
		{
			mNavigation(mGetPropertyFromFile("mkt_cbf_mainlink_id"),mGetPropertyFromFile("mkt_cbf_mid_id"), mGetPropertyFromFile("mkt_cbf_end_id"),mGetPropertyFromFile("mkt_final_link_id"));
			mWaitForVisible("id", mGetPropertyFromFile("mkt_single&multiple_id"));
			mCustomWait(1000);

			mClick("id", mGetPropertyFromFile("mkt_single&multiple_id"));
			mCustomWait(1000);
			mSelectDropDown("id",mGetPropertyFromFile("mkt_division_id"),mGetPropertyFromFile("mkt_division_data"));
			mCustomWait(1000);

			mWaitForVisible("css", mGetPropertyFromFile("mkt_list_id"));		
			mClick("css", mGetPropertyFromFile("mkt_list_id"));
			mCustomWait(1000);

			mWaitForVisible("id", mGetPropertyFromFile("mkt_list_licensenotextbox_id"));
		//	IndOrDep("id", "mkt_list_licensenotextbox_id", "applicationNo");

			/////////////////////////////////
			
			
			if(mGetPropertyFromFile("Execution_All").equalsIgnoreCase("ALL"))
			{
				licenseNo = mGetPropertyFromFile("mkt_Licencenumber");
				//mSendKeys("name", mGetPropertyFromFile("mkt_licenseNoid"),licenseNo );
			mSendKeys("id", mGetPropertyFromFile("mkt_list_licensenotextbox_id"),licenseNo);
			licenseNo="";
			}
			else
			{
				licenseNo = mGetPropertyFromFile("mkt_InspLicenseNodata");
				mWaitForVisible("id", mGetPropertyFromFile("mkt_list_licensenotextbox_id"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("mkt_list_licensenotextbox_id"),licenseNo);
				//IndOrDep("name", "mkt_licenseNoid", "applicationNo");
				//IndOrDep("name", "mkt_licenseNoid", "MKT_issueDupLicLicNoTextbox_Data");
				
				licenseNo="";
			}
			
			
			
			
			
			
			
			
			
			
			
			
			///////////////////////////////
			//		mSendKeys("id",mGetPropertyFromFile("mkt_list_licensenotextbox_id"),mGetPropertyFromFile("mkt_list_licensenotextbox_data"));
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("mkt_list_search_id"));
			mWaitForVisible("css", mGetPropertyFromFile("mkt_selectbox_id"));	
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("mkt_selectbox_id"));
			mCustomWait(1000);

			mClick("css", mGetPropertyFromFile("mkt_list_add_id"));
			mWaitForVisible("css", mGetPropertyFromFile("mkt_message_close_id"));
			mCustomWait(1000);
            
			/*
			String msg=mGetText("css",mGetPropertyFromFile("mkt_hearing_date_AddLicenseNoMsg_id"));
			System.out.println("msg is :::: "+msg );
			mAssert(msg,mGetPropertyFromFile("mkt_hearing_date_AddLicenseNoMsg_data"), "Message does not match, Expected: "+mGetPropertyFromFile("mkt_hearing_date_AddLicenseNoMsg_data")+" || Actual: "+msg);
			mCustomWait(1000); 			
			mTakeScreenShot();
			//capturing

			String hearing_date_licensceno=mGetText("name",mGetPropertyFromFile("mkt_hearingdate_licenseno"),"value");
			hearing_date_licensceno_list.add(hearing_date_licensceno);



			String hearingdate_noticeno=mGetText("name",mGetPropertyFromFile("mkt_hearingdate_noticeno"),"value");
			hearingdate_noticeno_list.add(hearingdate_noticeno);


			String hearingdate_noticedate=mGetText("name",mGetPropertyFromFile("mkt_hearingdate_noticedate"),"value");
			hearingdate_noticedate_list.add(hearingdate_noticedate);

			String hearingdate_hearingdate=mGetText("name",mGetPropertyFromFile("mkt_hearingdate_hearingdate"),"value");
			hearingdate_hearingdate_list.add(hearingdate_hearingdate);


			String hearingdate_Inspection_Date=mGetText("name",mGetPropertyFromFile("mkt_hearingdate_Inspection_Date"),"value");
			hearingdate_Inspection_Date_list.add(hearingdate_Inspection_Date);




			String hearingdate_Show_Cuase_reason=mGetText("name",mGetPropertyFromFile("mkt_hearingdate_Show_Cuase_reason"),"value");
			hearingdate_Show_Cuase_reason_list.add(hearingdate_Show_Cuase_reason);
*/



			mClick("css", mGetPropertyFromFile("mkt_message_close_id"));

			mWaitForVisible("css", mGetPropertyFromFile("mkt_view_id"));				
			mCustomWait(1000);			
			mClick("css", mGetPropertyFromFile("mkt_view_id"));
			mCustomWait(1000);

			mClick("css", mGetPropertyFromFile("mkt_hearing_date_id"));
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("mkt_hearing_date_id"));
			mCustomWait(500); 
		//	driver.findElement(By.id("entity.hearingDate")).sendKeys(mGetPropertyFromFile("mkt_hearingdate_data"));
			////code to select date and time also with sheet
			// driver.switchTo().frame(0);
			String datetime=mGetPropertyFromFile("mkt_hearingdate_data");
			String dates="";
			String ampm="";
			String []s=datetime.split(" ");
			String time="";
			for (int i = 0; i < s.length; i++) {
				System.out.println("s["+i+"]==>"+s[i]);
			dates=s[0];	
		 	time=s[1];	
		 	ampm=s[2];
			
			}
		String t[]=time.split(":");
		String hour=t[0];
		String minutes=t[1];
				
		int hr=Integer.parseInt(hour);
		int min=Integer.parseInt(minutes);
		System.out.println("hr=>"+hr);
		System.out.println("min=>"+min);
		if (ampm.equalsIgnoreCase("pm")) {
hr=hr+12;			
		}else {
			hr=hr;
		}
		
		
			 WebElement hslider = driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div[2]/dl/dd[2]/div/a"));
			 WebElement mslider = driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div[2]/dl/dd[3]/div/a"));
		        Actions move = new Actions(driver);
		        int width=hslider.getSize().getWidth();
		        System.out.println("width of slider=>"+width);
		       // Action action = (Action) move.dragAndDropBy(slider,0,100).build();
		   	 Actions mmove = new Actions(driver);
				
				if (!(min==0)) {
					for (int j = 1; j <= min; j++) {		
						if(j<=34)
						{
						 mmove.dragAndDropBy(mslider,1,0).build().perform();
						}else {
							mmove.dragAndDropBy(mslider,2,0).build().perform();
						}
						
					}
				}
				
						        
		        for (int i = 1; i <=hr; i++) {
			move.dragAndDropBy(hslider,4,0).build().perform();
			
		        }
			
			
			mDateSelect("id", "entity.hearingDate", dates); 
				String times=driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div[2]/dl/dd[1]")).getText();
				System.out.println("times====>"+times);
				
			//move.dragAndDropBy(hslider,4,0).build().perform();
			// mmove.dragAndDropBy(mslider,100,0).build().perform();
			////////////////////////////////////////////////////////////
			
			 
			 mCustomWait(1000);
					mClick("css", mGetPropertyFromFile("mkt_hearing_date_submit_id"));
					mCustomWait(1000);

				/*	String msg1=mGetText("css",mGetPropertyFromFile("mkt_hearing_date_proceedMsg_id"));
					System.out.println("msg is :::: "+msg1);
					mAssert(msg1,mGetPropertyFromFile("mkt_hearing_date_proceedMsg_data"), "Message does not match, Expected: "+mGetPropertyFromFile("mkt_hearing_date_proceedMsg_data")+" || Actual: "+msg1);
					mCustomWait(1000); 
*/				
					mTakeScreenShot();

					mClick("css", mGetPropertyFromFile("mkt_hearing_date_proceed_id"));
					mCustomWait(1500);
					mChallanPDFReader();		
					api.PdfPatterns.NtlHearingNoticePDF(pdfoutput);
					mAssert(hearingdate_pdf_NoticeNo, hearingdate_noticeno_list, "notice no is not getting matched");
					mAssert(hearingdate_pdf_Noticedate, hearingdate_noticedate_list, "notice date is not getting matched");
					mAssert(HDE_HearingDate_list, hearingdate_hearingdate_list, "hearing date is not getting matched");
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in cancellation_hearing_date method");
		}


	}


	public void cancellation_hearing_details_entry()
	{

		try{
			mNavigation(mGetPropertyFromFile("mkt_cbf_mainlink_id"),mGetPropertyFromFile("mkt_cbf_mid_id"),mGetPropertyFromFile("mkt_cbf_end_id"),mGetPropertyFromFile("mkt_final1_link_id"));
			mCustomWait(1000);
			mTakeScreenShot();  
			mSelectDropDown("id",mGetPropertyFromFile("mkt_division_details_id"),mGetPropertyFromFile("mkt_division_details_data"));
			mCustomWait(2500);
			mClick("css", mGetPropertyFromFile("mkt_list_details_id"));
			mWaitForVisible("css",mGetPropertyFromFile("mkt_searchIcon_id"));
			mCustomWait(1000);
			mTakeScreenShot(); 
			mClick("css", mGetPropertyFromFile("mkt_searchIcon_id"));
			mWaitForVisible("css",mGetPropertyFromFile("mkt_add_dropdown_id"));
			mCustomWait(1000);

			mSelectDropDown("css",mGetPropertyFromFile("mkt_add_dropdown_id"),mGetPropertyFromFile("mkt_add_dropdown_data"));
			mCustomWait(1000);

			//IndOrDep("css", "mkt_licenseno_id", "applicationNo");

			
			//////////////////////////
			

			if(mGetPropertyFromFile("Execution_All").equalsIgnoreCase("ALL"))
			{
				licenseNo = mGetPropertyFromFile("mkt_Licencenumber");
				//mSendKeys("name", mGetPropertyFromFile("mkt_licenseNoid"),licenseNo );
			mSendKeys("css", mGetPropertyFromFile("mkt_licenseno_id"),licenseNo);
			licenseNo="";
			}
			else
			{
				licenseNo = mGetPropertyFromFile("mkt_InspLicenseNodata");
				mWaitForVisible("css", mGetPropertyFromFile("mkt_licenseno_id"));
				mGenericWait();
				mSendKeys("css", mGetPropertyFromFile("mkt_licenseno_id"),licenseNo);
				//IndOrDep("name", "mkt_licenseNoid", "applicationNo");
				//IndOrDep("name", "mkt_licenseNoid", "MKT_issueDupLicLicNoTextbox_Data");
				
				licenseNo="";
			}
			
			
			
			
			
			
			
			
			
			///////////////////////
			
			
			
			
			
			Robot robot=new Robot();
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);


			mCustomWait(1000);
			mWaitForVisible("linkText",mGetPropertyFromFile("mkt_licenseno_find_id"));
			mTakeScreenShot();
			mClick("linkText",mGetPropertyFromFile("mkt_licenseno_find_id"));
			mCustomWait(1000);

			mWaitForVisible("css",mGetPropertyFromFile("mkt_view_id"));
			mCustomWait(1000); 
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("mkt_view_id"));
			mWaitForVisible("css",mGetPropertyFromFile("mkt_complete_status_id"));
			mClick("css", mGetPropertyFromFile("mkt_complete_status_id"));
			mCustomWait(1000);
			mSendKeys("css",mGetPropertyFromFile("mkt_remark_id"),mGetPropertyFromFile("mkt_remark_data"));
			mCustomWait(1000);
			mTakeScreenShot(); 
			// capturing

			/*String hearingdetails_notice_type=mGetText("name",mGetPropertyFromFile("mkt_clbf_hearingdetails_notice_type"),"value");
			hearingdetails_notice_type_list.add(hearingdetails_notice_type);

			String hearingdetails_hearingdate=mGetText("name",mGetPropertyFromFile("mkt_clbf_hearingdetails_hearingdate"),"value");
			hearingdetails_hearingdate_list.add(hearingdetails_hearingdate);

			String hearingdetails_License_no=mGetText("name",mGetPropertyFromFile("mkt_clbf_hearingdetails_License_no"),"value");
			hearingdetails_License_no_list.add(hearingdetails_License_no);

			mAssert(hearingdetails_License_no_list, hearing_date_licensceno_list, "License no is not matching");

			String hearingdetails_noticeno_no=mGetText("name",mGetPropertyFromFile("mkt_clbf_hearingdetails_noticeno_no"),"value");
			hearingdetails_noticeno_no_list.add(hearingdetails_noticeno_no);

			mAssert(hearingdetails_noticeno_no_list, hearingdate_noticeno_list, "notice no on hearing date & details is not matching");
			String hearingdetails_ownername=mGetText("name",mGetPropertyFromFile("mkt_clbf_hearingdetails_ownername"),"value");
			hearingdetails_ownername_list.add(hearingdetails_ownername);


			String hearingdetails_hearingstatus=mGetText("name",mGetPropertyFromFile("mkt_clbf_hearingdetails_hearingstatus"),"value");
			hearingdetails_hearingstatus_list.add(hearingdetails_hearingstatus);


			String clbf_hearingdetails_inspector=mGetText("css",mGetPropertyFromFile("mkt_clbf_hearingdetails_inspector"),"value");
			mkt_clbf_hearingdetails_inspector_list.add(hearingdetails_hearingstatus);
			mAssert(mkt_clbf_hearingdetails_inspector_list, showcase_inspector_name_list, "inspector name is not matching");
			String mkt_clbf_hearingdetails_Reason=mGetText("css",mGetPropertyFromFile("mkt_clbf_hearingdetails_Reason"),"value");
			mkt_clbf_hearingdetails_Reason_list.add(mkt_clbf_hearingdetails_Reason);

			mAssert(hearingdate_Show_Cuase_reason_list, mkt_clbf_hearingdetails_Reason_list, "Hearing details entry reason is not matching with reason");
			String mkt_clbf_hearingdetails_remark=mGetText("css",mGetPropertyFromFile("mkt_clbf_hearingdetails_Remark"),"value");
			mkt_clbf_hearingdetails_Remark_list.add(mkt_clbf_hearingdetails_remark);


*/


			mClick("css", mGetPropertyFromFile("mkt_submit_id"));
			mCustomWait(2000); 
		/*	String msg=mGetText("css", mGetPropertyFromFile("mkt_proceed_button_msgid"));
			System.out.println(msg);
			mAssert(msg,mGetPropertyFromFile("mkt_proceed_button_msgdata"), "Message does not match, Expected: "+mGetPropertyFromFile("mkt_proceed_button_msgdata")+" || Actual: "+msg);
*/
			mWaitForVisible("css",mGetPropertyFromFile("mkt_proceed_button_id"));
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("mkt_proceed_button_id"));
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in cancellation_hearing_details_entry method");
		}
	}
	public void RenewalofTradeLicense ()
	{
		try 
		{
			//mNavigation(mGetPropertyFromFile("mkt_renoftradeLicModule_data"), mGetPropertyFromFile("mkt_renoftradeLicService_data"));
			//mNavigation("mkt_deptonlserviceLinkid","mkt_marketLicenseLinkid", "mkt_newMarketLicenseServiceLinkid");  // 12-10-2016
			//Click list
			mNavigation("mkt_deptonlserviceLinkid", "mkt_marketLicenseLinkid", "mkt_RenewMarketLicenseServiceLinkid");  // 12-10-2016
			//mNavigation("mkt_deptonlserviceLinkid", "mkt_marketLicenseLinkid", "mkt_RenewMarketLicenseServiceLinkid");  // 12-10-2016
			mWaitForVisible("id", mGetPropertyFromFile("mkt_renoftradeLicListBtn_id"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("mkt_renoftradeLicListBtn_id"));

			//Wait for Search button
			mWaitForVisible("xpath", mGetPropertyFromFile("mkt_renoftradeLicSearchBtn_id"));

			mTakeScreenShot();

			//enter license no

			mWaitForVisible("name", mGetPropertyFromFile("mkt_renoftradeLicLicNotextbox_id"));
			mGenericWait();

			//mSendKeys("name", mGetPropertyFromFile("mkt_renoftradeLicLicNotextbox_id"), licenseNo);
			/*licenseNo=mGetPropertyFromFile("mkt_renewallicnum_data");
			Renewal_licenseNo_list.add(licenseNo);
			mSendKeys("name", mGetPropertyFromFile("mkt_renoftradeLicLicNotextbox_id"), licenseNo);*/

			
			///////////
			
			
			if(mGetPropertyFromFile("Execution_All").equalsIgnoreCase("ALL"))
			{
				licenseNo = mGetPropertyFromFile("mkt_Licencenumber");
				//mSendKeys("name", mGetPropertyFromFile("mkt_licenseNoid"),licenseNo );
			mSendKeys("name", mGetPropertyFromFile("mkt_renoftradeLicLicNotextbox_id"),licenseNo);
			licenseNo="";
			}
			else
			{
				/*licenseNo = mGetPropertyFromFile("mkt_renewallicnum_data");
				mWaitForVisible("name", mGetPropertyFromFile("mkt_renoftradeLicLicNotextbox_id"));
				mGenericWait();
				mSendKeys("css", mGetPropertyFromFile("mkt_renoftradeLicLicNotextbox_id"),licenseNo);*/
				
				
				licenseNo=mGetPropertyFromFile("mkt_renewallicnum_data");
				Renewal_licenseNo_list.add(licenseNo);
				mSendKeys("name", mGetPropertyFromFile("mkt_renoftradeLicLicNotextbox_id"), licenseNo);
				//IndOrDep("name", "mkt_licenseNoid", "applicationNo");
				//IndOrDep("name", "mkt_licenseNoid", "MKT_issueDupLicLicNoTextbox_Data");
				
				licenseNo="";
			}
			
			
			
			
			////////
			
			
			mTakeScreenShot();

			//Click search
			mWaitForVisible("xpath", mGetPropertyFromFile("mkt_renoftradeLicSearchBtn_id"));
			mClick("xpath", mGetPropertyFromFile("mkt_renoftradeLicSearchBtn_id")); 

			//Click View details
			mWaitForVisible("xpath", mGetPropertyFromFile("mkt_renoftradeLicViewDetails_id"));

			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("mkt_renoftradeLicViewDetails_id"));
			mGenericWait();
			//assert swapnil
			// capturin for assert
			//capturing holder name in array list  holder name 
			String Renew_holdername = mGetText("id", mGetPropertyFromFile("mkt_renoftradeLicHolderName_id"), "value");
			System.out.println("Holder name is::"+Renew_holdername);
			Renewal_Holder_Name_list.add(Renew_holdername);
			String Renew_businessname = mGetText("id", mGetPropertyFromFile("mkt_renoftradeLicBusinessName_id"), "value");
			System.out.println("businessname is::"+Renew_businessname);
			Renew_businessname_list.add(Renew_businessname);
			//String ren_hname=mGetText("id", "holderNameId");
			/*ArrayList ren_ho_name = new ArrayList();
			ren_ho_name.add(Renew_holdername);
			//arraylistAssert(ren_ho_name, ho_name, "holder name matched");

			// capturing business name in array list 
			//String ren_business_name=mGetText("id", "businessNameId");
			ArrayList ren_bu_name = new ArrayList();
			ren_bu_name.add(Renew_businessname);*/
			//	arraylistAssert(ren_bu_name, bn_name, "busness name matched");

			//capturing org name 

			String ren_org_name=mGetText("id", mGetPropertyFromFile("mkt_renewal_orgname_id"), "value");
			renew_orgname.add(ren_org_name);
			//	arraylistAssert(renew_orgname, new_orgName, "org name matched");

			//capturing address line

			String ren_address_line=mGetText("id",mGetPropertyFromFile("mkt_renewal_address_new_id"), "value");

			renewal_address.add(ren_address_line);
			//arraylistAssert(renewal_address, address_n, "address matched");

			//Wait for details to be fetched... entity.address1

			//Capture licensee details

			mWaitForVisible("id", mGetPropertyFromFile("mkt_renoftradeLicHolderName_id"));
			mGenericWait();


			mTakeScreenShot();



			String orgname= mGetText("id", mGetPropertyFromFile("mkt_renoftradeLicOrg_id"), "value");
			System.out.println("orgname is::"+orgname);

			String addressline1 = mGetText("id", mGetPropertyFromFile("mkt_renoftradeLicAddrs1_id"), "value");
			System.out.println("address is::"+addressline1);


			//Wait for history button and click on it

			mWaitForVisible("css", mGetPropertyFromFile("mkt_renoftradeLicHistBtn_id"));
			mClick("css", mGetPropertyFromFile("mkt_renoftradeLicHistBtn_id"));

			//Wait for grid table to appear

			//mWaitForVisible("xpath", "//*[@id=\"adjustmentMainTable\"]/tbody/tr[1]/th[1]");

			mGenericWait();
			mCustomWait(5000);

			try{

				WebElement table = driver.findElement(By.className("gridtable"));


				List<WebElement> rows = table.findElements(By.tagName("tr"));

				int rwcount =rows.size();

				System.out.println("No of rows is::: " +rwcount);

				for(int i=2;i<=rwcount;i++)
				{
					for(int j=1;j<=6;j++)
					{
						if(j==1)
						{
							String SerialNo = mGetText("xpath","//*[@id=\"taxDescRunTime\"]/td["+j+"]");
							System.out.println("Serial No is:: "+SerialNo);
						}
						if(j==2)
						{
							String FromDate = mGetText("xpath", "//*[@id=\"taxDescRunTime\"]/td["+j+"]/input", "value");
							System.out.println("FromDate is:: "+FromDate);
						}
						if(j==3)
						{
							String ToDate = mGetText("xpath", "//*[@id=\"taxDescRunTime\"]/td["+j+"]/input", "value");
							System.out.println("To Date is:: "+ToDate);
						}
						if(j==4)
						{
							String Fees = mGetText("xpath", "//*[@id=\"taxDescRunTime\"]/td["+j+"]/input", "value");
							System.out.println("Fees is:: "+Fees);
						}
						if(j==5)
						{
							String renewalDate = mGetText("xpath", "//*[@id=\"taxDescRunTime\"]/td["+j+"]/input", "value");
							System.out.println("Renewal Date is:: "+renewalDate);
						}
						if(j==6)
						{
							String remarks = mGetText("xpath", "//*[@id=\"taxDescRunTime\"]/td["+j+"]/input", "value");
							System.out.println("Remarks are:: "+remarks);
						}
					}
				}
			}

			catch (Exception e)
			{
				e.printStackTrace();
				throw new MainetCustomExceptions("Error in RenewalofTradeLicense method");
			}


			mGenericWait();
			//Click close
			////////////
			mWaitForVisible("xpath", mGetPropertyFromFile("mkt_renoftradeLicHistClose_id"));
			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("mkt_renoftradeLicHistClose_id"));

			//swap renewal
			String RenewalFromDate = mGetText("id", mGetPropertyFromFile("mkt_renoftradeLicRenFrmDte_id"), "value");
			System.out.println("RenewalFromDate is:: "+RenewalFromDate);
			RenewalFromDate_list.add(RenewalFromDate);
			String RenewalToDate = mGetText("id", mGetPropertyFromFile("mkt_renoftradeLicRenToDte_id"), "value");
			System.out.println("RenewalToDate is:: "+RenewalToDate);
			RenewalToDate_list.add(RenewalToDate);
			String Fees = mGetText("id", mGetPropertyFromFile("mkt_renoftradeLicFees_id"), "value");
			System.out.println("Fees is:: "+Fees);
			Fees_list.add(Fees);


			String RenewalDate = mGetText("id", mGetPropertyFromFile("mkt_renoftradeLicRenDte_id"), "value");
			System.out.println("RenewalDate is:: "+RenewalDate);
			RenewalDate_list.add(RenewalDate);
			String Remarks = mGetText("name", mGetPropertyFromFile("mkt_renoftradeLicRemarks_id"), "value");
			System.out.println("Remarks is:: "+Remarks);
			Remarks_list.add(Remarks);
			mAssert(Remarks, "Renewal Period from "+RenewalFromDate+" to "+RenewalToDate+".", "Remarks Do not Match");

			String [] parts = RenewalFromDate.split("/");
			String frmyear = parts[2];
			int fromyear = Integer.parseInt(frmyear);

			String [] parts1 = RenewalToDate.split("/");
			String tyear = parts1[2];
			int toyear = Integer.parseInt(tyear);

			//Getting renewal period duration in years
			System.out.println(toyear-fromyear);


			//Selecting renewal iterator 
			//("css", "input[id='pnbutton'][value='Show History']") 

			//mscroll(0, 250);

			mWaitForVisible("name", mGetPropertyFromFile("mkt_renoftradeLicNoOfYrs_id"));
			mSelectDropDown("name", mGetPropertyFromFile("mkt_renoftradeLicNoOfYrs_id"), mGetPropertyFromFile("mkt_renoftradeLicNoOfYrs_data"));
			mGenericWait();

			String rentrenperiod = mGetText("name", mGetPropertyFromFile("mkt_renoftradeLicTotRenPer_id"), "value");
			System.out.println(rentrenperiod);

			Integer rntrenperiod = Integer.valueOf(rentrenperiod);
			System.out.println(rntrenperiod);

			System.out.println(toyear);
			System.out.println(fromyear);
			int Updted_toperiod=fromyear+rntrenperiod;
			System.out.println("todate="+Updted_toperiod);



			//System.out.println(rntrenperiod.compareTo(toyear-fromyear));

			/*if (rntrenperiod.compareTo(toyear-fromyear) < 0)
			{
				System.out.println("difference is there");
			}

			else
			{			
				System.out.println("No Difference");
			}

			mUpload("id", mGetPropertyFromFile("mkt_renoftradeLicUpload_id"), mGetPropertyFromFile("mkt_renoftradeLicUpload_data"));
			 */

			mscroll(0, 250);

			String markrenwalfee = mGetText("name", mGetPropertyFromFile("mkt_renoftradeLicMktRenFee_id"), "value");
			System.out.println(markrenwalfee);



			String totalamt = mGetText("name", mGetPropertyFromFile("mkt_renoftradeLicTotAmt_id"), "value");
			System.out.println(totalamt);

			totalamt_list.add(totalamt);
			System.out.println("toatalamount"+totalamt_list);

			String Fees_new = mGetText("id", mGetPropertyFromFile("mkt_renoftradeLicFees_id"), "value");
			System.out.println("Fees is:: "+Fees);
			Fees_new_list.add(Fees_new);

			System.out.println("@@"+Fees_new_list);
			System.out.println("@@"+totalamt_list);

			mAssert(Fees_new_list, totalamt_list, "Amounts are matched");


			//Click Save
			mGenericWait();
			mClick("css", mGetPropertyFromFile("mkt_renoftradeLicSaveBtn_id"));

			//Click Proceed
			mGenericWait();
			mWaitForVisible("css", mGetPropertyFromFile("mkt_renoftradeLicPrcdBtn_id"));

			mGenericWait();
			String popupmsg = mGetText("css", mGetPropertyFromFile("mkt_renoftradeLicPopUpMsg_id"));

			System.out.println(popupmsg);

			//Capturing appno and asserting popup msg
			appNo = popupmsg.replaceAll("\\D+","");
			System.out.println("Application No is::: "+appNo);
			appNo = appNo.trim();
			mAppNoArray(appNo); 

			popupmsg = popupmsg.replaceAll("[0-9]","");			
			System.out.println(popupmsg);
			mTakeScreenShot();


			mClick("css", mGetPropertyFromFile("mkt_renoftradeLicPrcdBtn_id"));

			mGenericWait();

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in RenewalofTradeLicense method");
		}
	}

	//MKT Nom

	public void licenseTransferUnderNomination()
	{

		try
		{

			mNavigation("mkt_tonundeptonlserviceLinkid","mkt_tonunmarketLicenseLinkid","mkt_tonunMarketLicenseServiceLinkid");
			//mNavigation("mkt_deptonlserviceLinkid","mkt_marketLicenseLinkid", "mkt_newMarketLicenseServiceLinkid");  // 12-10-2016
			//mWaitForVisible("id", mGetPropertyFromFile("mkt_TLUNListButtonid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("mkt_TLUNListButtonid"));

			//mWaitForVisible("name", mGetPropertyFromFile("mkt_TLUNLicNoid"));
			mGenericWait();
			//mClick("xpath", mGetPropertyFromFile("mkt_TLUNLicNoid"));




			//mSendKeys("xpath", mGetPropertyFromFile("mkt_TLUNLicNoid"),mGetPropertyFromFile("mkt_TLUNLicNo"));
			mCustomWait(2000); 
			//mSendKeys("xpath", mGetPropertyFromFile("mkt_TLUNLicNoid"),licenseNo);


			mGenericWait();
			mClick("css", mGetPropertyFromFile("mkt_TLUNSearchButtonid"));

			mGenericWait();
			mClick("css", mGetPropertyFromFile("tlun_grid_id"));
			mGenericWait();


			/*			// Jyoti
///////////////SWAP code for Excel Writing

			if(mGetPropertyFromFile("Execution_All").equalsIgnoreCase("ALL"))
			{

			mSendKeys("name", mGetPropertyFromFile("tlun_grid_id"),licenseNo );
			chngBusiNm_licenseno_list.add(licenseNo);
			}
			else
			{

				mWaitForVisible("name", mGetPropertyFromFile("tlun_grid_id"));
				mGenericWait();

				IndOrDep("name", "mkt_licenseNoid", "applicationNo");
			}
			 */

			if(mGetPropertyFromFile("Execution_All").equalsIgnoreCase("ALL"))
			{
				licenseNo = mGetPropertyFromFile("mkt_Licencenumber");
				//mSendKeys("name", mGetPropertyFromFile("mkt_licenseNoid"),licenseNo );
			mSendKeys("name", mGetPropertyFromFile("no_id"),licenseNo);
			licenseNo="";
			}
			else
			{
				licenseNo = mGetPropertyFromFile("mkt_TLUNLicNo");
				mWaitForVisible("name", mGetPropertyFromFile("no_id"));
				mGenericWait();
				mSendKeys("name", mGetPropertyFromFile("no_id"),licenseNo);
				//IndOrDep("name", "mkt_licenseNoid", "applicationNo");
				//IndOrDep("name", "mkt_licenseNoid", "MKT_issueDupLicLicNoTextbox_Data");
				
				licenseNo="";
			}



			


			mClick("css",mGetPropertyFromFile("mkt_findButtonid"));
			mGenericWait();
			mCustomWait(1000);
			mClick("css",mGetPropertyFromFile("mkt_viewButtonid"));


			//mWaitForVisible("css", mGetPropertyFromFile("mkt_TLUNAddLicNoid")); 
			mTakeScreenShot();
			//mClick("css", mGetPropertyFromFile("mkt_TLUNAddLicNoid"));

			//	mWaitForVisible("linkText", mGetPropertyFromFile("mkt_TLUNAddDtlsOfLicHldrid"));
			mGenericWait();
			mTakeScreenShot();

			//String busiName = driver.findElement(By.id("busiName =")).getAttribute("value");
			/*
			busiName = mGetText("id", mGetPropertyFromFile("busiName"), "value");
			System.out.println("Property Dues are :"+busiName);

			marketErrorMsg.assertNotNull(busiName,"Business name is null");*/

			//String propDues = driver.findElement(By.id(mGetPropertyFromFile("mkt_TLUNPropDuesid"))).getAttribute("value");
			String propDues = mGetText("id", mGetPropertyFromFile("mkt_TLUNPropDuesid"), "value");

			System.out.println("Property Dues are :"+propDues);

			//String marketDues = driver.findElement(By.id(mGetPropertyFromFile("mkt_TLUNMarketDuesid"))).getAttribute("value");

			String marketDues = mGetText("id", mGetPropertyFromFile("mkt_TLUNMarketDuesid"), "value");

			System.out.println("Market Dues are :"+marketDues);

			mClick("linkText", mGetPropertyFromFile("mkt_TLUNAddDtlsOfLicHldrid"));

			mWaitForVisible("xpath", mGetPropertyFromFile("mkt_TLUNLicHldrDtlsSubmitid"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_TLUNFirstNmid"),mGetPropertyFromFile("mkt_TLUNFirstNm"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_TLUNMiddleNmid"),mGetPropertyFromFile("mkt_TLUNMiddleNm"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_TLUNLastNmid"),mGetPropertyFromFile("mkt_TLUNLastNm"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_TLUNLicHldrAgeid"),mGetPropertyFromFile("mkt_TLUNLicHldrAge"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_TLUNLicHldrAddrid"),mGetPropertyFromFile("mkt_TLUNLicHldrAddr"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_TLUNLicHldrPhoneid"),mGetPropertyFromFile("mkt_TLUNLicHldrPhone"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_TLUNLicHldrAddpinid"),mGetPropertyFromFile("mkt_TLUNLicHldrAddpin"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_TLUNLicHldrEmailid"),mGetPropertyFromFile("mkt_TLUNLicHldrEmail"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_TLUNLicHldrMobNoid"),mGetPropertyFromFile("mkt_TLUNLicHldrMobNo"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_TLUNLicHldrAdharNoid"),mGetPropertyFromFile("mkt_TLUNLicHldrAdharNo"));
			mGenericWait();
			/*mUpload("id", mGetPropertyFromFile("mkt_TLUNLicHldrUpldPhtoid"),mGetPropertyFromFile("mkt_TLUNLicHldrUpldPhto"));
			mGenericWait();*/
			//mClick("css", mGetPropertyFromFile("mkt_TLUNCloseBtnid"));
			mTakeScreenShot();

			mClick("xpath", mGetPropertyFromFile("mkt_TLUNLicHldrDtlsSubmitid"));
			//mWaitForVisible("id", mGetPropertyFromFile("mkt_TLUNUploadDocid"));
			mGenericWait();
			mUpload("id", mGetPropertyFromFile("mkt_TLUNUploadDocid"),mGetPropertyFromFile("mkt_TLUNUploadDoc"));
			mGenericWait();
			mTakeScreenShot();

			//table reading

			tluntableread();
			//

			//capturing business name 
			String tlun_busineess_name=mGetText("name",mGetPropertyFromFile("mkt_tlun_service_business_name"),"value");
			tlun_busineess_name_list.add(tlun_busineess_name);
			System.out.println(tlun_busineess_name_list);
			mClick("name", mGetPropertyFromFile("mkt_TLUNSaveid"));
			mWaitForVisible("id", mGetPropertyFromFile("mkt_TLUNProceedid"));
			mGenericWait();

			String saveMsg=mGetText("css", mGetPropertyFromFile("mkt_tranLicUnderNomnSaveMsgid"));
			System.out.println(saveMsg);
			Pattern appNum = Pattern.compile("[0-9]+");
			Matcher appNumber = appNum.matcher(saveMsg);

			if(appNumber.find()) {				
				appNo = appNumber.group();
				System.out.println("Application No is: "+appNo);
				appNo.trim();
			}
			mAppNoArray(appNo);
			strippedSaveString = saveMsg.replace(appNo, "");
			System.out.println(strippedSaveString);
			mAssert(mGetPropertyFromFile("mkt_tranOfLicUnderNomnSaveMsg"), strippedSaveString, "Message does not match, Expected: "+mGetPropertyFromFile("mkt_tranOfLicUnderNomnSaveMsg")+" || Actual: "+strippedSaveString);


			mTakeScreenShot();
			mClick("id", mGetPropertyFromFile("mkt_TLUNProceedid"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in licenseTransferUnderNomination method");
		}

	}

	public void CancellationOfLicense()  throws InterruptedException, IOException


	{
		try 
		{
			//navigation(mGetPropertyFromFile("mkt_CancOfLicModule_data"), mGetPropertyFromFile("mkt_CancOfLicService_data"));
			mNavigation("mkt_canceldeptonlserviceLinkid", "mkt_cancelmarketLicenseLinkid","mkt_cancelMarketLicenseServiceLinkid");  // 12-10-2016
			//Wait for page to load
			//	mWaitForVisible("id", mGetPropertyFromFile("mkt_cancOfLicAppNoTextBox_id"));

			//Wait for, and click on list button			
			mWaitForVisible("id", mGetPropertyFromFile("mkt_cancOfLicListBtn_id"));			
			mClick("id", mGetPropertyFromFile("mkt_cancOfLicListBtn_id"));

			//Wait for popup and enter license no

			mWaitForVisible("css", mGetPropertyFromFile("mkt_cancOfLicLicenseNoTextBox_id"));
			mGenericWait();
			
			
			if(mGetPropertyFromFile("Execution_All").equalsIgnoreCase("ALL"))
			{
				licenseNo = mGetPropertyFromFile("mkt_Licencenumber");
				//mSendKeys("name", mGetPropertyFromFile("mkt_licenseNoid"),licenseNo );
			mSendKeys("name", mGetPropertyFromFile("mkt_cancOfLicLicenseNoTextBox_id"),licenseNo);
			licenseNo="";
			}
			else
			{
				licenseNo=mGetPropertyFromFile("mkt_cancellation_no_data");
				mWaitForVisible("name", mGetPropertyFromFile("mkt_cancOfLicLicenseNoTextBox_id"));
				mGenericWait();
				mSendKeys("css", mGetPropertyFromFile("mkt_cancOfLicLicenseNoTextBox_id"), licenseNo);
				//IndOrDep("name", "mkt_licenseNoid", "applicationNo");
				//IndOrDep("name", "mkt_licenseNoid", "MKT_issueDupLicLicNoTextbox_Data");
				
				licenseNo="";
			}


			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
	
			
			mGenericWait();

			//Click on search
			mClick("css", mGetPropertyFromFile("mkt_cancOfLicSearchBtn_id"));

			//Wait for search result to appear and click
			mWaitForVisible("css", mGetPropertyFromFile("mkt_cancOfLicViewDetailsImg_id"));			
			mClick("css", mGetPropertyFromFile("mkt_cancOfLicViewDetailsImg_id"));

			//Wait for search result to get loaded

			mWaitForVisible("id", mGetPropertyFromFile("mkt_cancOfLicCancelationDate_id"));
			mGenericWait();
			mWaitForVisible("id", mGetPropertyFromFile("mkt_cancOfLicCancelationReason_id"));

			//Enter cancellation date and cancellation reason

			mDateSelect("id",mGetPropertyFromFile("mkt_cancOfLicCancelationDate_id"), mGetPropertyFromFile("mkt_cancOfLicCancelationDate_data"));

			//	mSendKeys("id", "entity.canDate", "11/04/2016");
			mGenericWait();
			//mDatePicker(dtPickerId, year, month, day);
			//mDatePicker("entity.canDate", "2016", "Apr", "06");
			//mGdatePicker("entity.canDate", "2016", "Apr", "06");

			mSendKeys("id", mGetPropertyFromFile("mkt_cancOfLicCancelationReason_id"), mGetPropertyFromFile("mkt_cancOfLicCancelationReason_data"));
			mGenericWait();

			//Upload mandatory documents
			//mSendKeys("id", mGetPropertyFromFile("mkt_cancOfLicUpload_id"), mGetPropertyFromFile("mkt_cancOfLicUpload_data"));			
			mCustomWait(5000);

			mUpload("id", mGetPropertyFromFile("mkt_cancOfLicUpload_id"), mGetPropertyFromFile("mkt_cancOfLicUpload_data"));
			mCustomWait(5000);
			mUpload("id", mGetPropertyFromFile("mkt_cancOfLicUpload1_id"), mGetPropertyFromFile("mkt_cancOfLicUpload_data1"));
			//reading table for document upload //swapnil
			/*	int Row_count = driver.findElements(By.xpath("//*[@id=\"results\"]/table/tbody/tr")).size();
		System.out.println(Row_count);
		int Col_count = driver.findElements(By.xpath("//*[@id=\"results\"]/table/tbody/tr[1]/td")).size();
		//int Col_count = 3;
		System.out.println(Col_count);
		 String first_part = "//*[@id=\"results\"]/table/tbody/tr[";
		 String second_part = "]/td[";
		 String third_part = "]";

		 for (int i=1; i<=Row_count; i++){
			  //Used for loop for number of columns.
			  for(int j=1; j<=Col_count; j++){
			   //Prepared final xpath of specific cell as per values of i and j.
			   String final_xpath = first_part+i+second_part+j+third_part;
			   System.out.println(final_xpath);
			   //Will retrieve value from located cell and print It.
			   String Table_data = driver.findElement(By.xpath(final_xpath)).getText();
			   System.out.print(Table_data +"  ");   
			  }
			 */

			//Click on submit
			//mWaitForVisible("css", mGetPropertyFromFile("mkt_cancOfLicSubmitBtn_id"));

			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("mkt_cancOfLicSubmitBtn_id"));

			// swapnil capture proceed button & application number

			String saveMsg=mGetText("css", mGetPropertyFromFile("cancel_newMarketLicenseSaveMsgid"));
			System.out.println(saveMsg);
			Pattern appNum = Pattern.compile("[0-9]+");
			Matcher appNumber = appNum.matcher(saveMsg);

			if(appNumber.find()) {				
				appNo = appNumber.group();
				System.out.println("Application No is: "+appNo);
				appNo=appNo.trim();
			}

			///mAppNoArray(locatortype, locator);
			mAppNoArray(appNo);
			mCustomWait(3000);

			mClick("id", mGetPropertyFromFile("mkt_cancOfLicproceedBtn_id"));





		}

		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in CancellationOfLicense method");
		} 
	}

	public ResultSet mktdbtest()
	{
		try{

			System.out.println("started");
			String db_orgid1;
			String db_lic_no;

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
			db_lic_no=mGetPropertyFromFile("MKT_issueDupLicLicNoTextbox_Data");

			db_orgid1=mGetPropertyFromFile("db_orgid");

			String query1 ="select \r\n" +
					"lm_no,lm_businame \r\n" + 
					"from tb_mtl_license_mas" +
					"\r\n" + 
					"where orgid = "+db_orgid1 + " and lm_no='"+db_lic_no+"'"; 	



			System.out.println("Query :: " + query1);

			String URL = "jdbc:oracle:thin:@192.168.100.169:1521:ORCLUNI";
			String USER = "mainet_prod_phase2";
			String PASS = "mainet_prod_phase2";
			Connection conn = DriverManager.getConnection(URL,USER,PASS);
			Statement st=conn.createStatement();
			//Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);


			try{
				rs1 = st.executeQuery(query1);
			}catch (Exception e)
			{
				e.printStackTrace();
			}

			System.out.println("result se output="+rs1);



			while(rs1.next()) {
				System.out.println("dbvalue");
				System.out.println(rs1.getString(1) +"    "+ rs1.getString(2));	
				//db_acrid=rs1.getString(1);
				//System.out.println("acrid is : " +db_acrid); 

			}

		}catch (Exception e)
		{
			e.printStackTrace();
		}

		return rs1;

	}


	public void tluntableread()
	{

		WebElement table = driver.findElement(By.className("ui-jqgrid-btable"));
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
					if (Columnno == 1) 
					{
						String tlunholdername = rowElement.findElement(By.xpath("./td[2]")).getText();
						mGenericWait();
						System.out.println("holdername is :"+tlunholdername);

						tlunholdername_list.add(tlunholdername);

						System.out.println("tlun holderlist"+tlunholdername_list);
					}
					if (Columnno == 2) 
					{
						String tlunholdername_age = rowElement.findElement(By.xpath("./td[3]")).getText();
						mGenericWait();
						System.out.println("holdername age :"+tlunholdername_age);

					}
					if (Columnno == 3) 
					{
						String tlunholdername_gender = rowElement.findElement(By.xpath("./td[4]")).getText();
						mGenericWait();
						System.out.println("holdername's gender :"+tlunholdername_gender);
					}
					if (Columnno == 4) 
					{
						String tlunholdername_mobile = rowElement.findElement(By.xpath("./td[5]")).getText();
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

	//CheckList Verification
	public void checklistVerification(){
		try{
			mCreateArtefactsFolder("MKT_");
			common.ChecklistVerification(mGetPropertyFromFile("cfc_chcklistverfn_username"), mGetPropertyFromFile("cfc_chcklistverfn_pwd"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in checklistVerification method");
		}

	}
	public void mktassertchecklistVerification()
	{
		try{ 

			String clv_ApplicantName=mGetText("xpath",mGetPropertyFromFile("clv_ApplicantName_id"));
			System.out.println("Checklist Verification Applicant Name is::"+clv_Service_Name);
			clv_ApplicantName_list.add(clv_ApplicantName);
			System.out.println("Checklist Verification Applicant Name List is::"+clv_ApplicantName_list);


			String clv_Application=mGetText("xpath",mGetPropertyFromFile("clv_Application_Id"));
			System.out.println(clv_Application);
			clv_Application_list.add(clv_Application);
			String clv_ApplicationDate=mGetText("xpath",mGetPropertyFromFile("clv_ApplicationDate_id"));
			System.out.println(clv_ApplicationDate);
			clv_ApplicationDate_list.add(clv_ApplicationDate);
			System.out.println("========================");
			System.out.println("clv_ApplicantName_list"+clv_ApplicantName_list);
			System.out.println("clv_Application_list"+clv_Application_list);
			System.out.println("clv_ApplicationDate_list"+clv_ApplicationDate_list);
			String mktcheklist=clv_Service_Name_list.get(CurrentinvoCount).toString();
		
			String todaysdate=new SimpleDateFormat("dd/MM/yyyy").format(myDate);

			switch(mktcheklist) 
			{

			case "New Trade License" :
				System.out.println("im in new trade Licence");
				System.out.println("Ready for cheklist verification for Service name New Trade License-->"+clv_Service_Name);
				mAssert(HolderName.get(CurrentinvoCount),clv_ApplicantName_list.get(CurrentinvoCount),"Applicant name Not"+HolderName+" matched of service New Trade License"+clv_ApplicantName_list);
				
				System.out.println("Chk List Verification Application Time List"+HolderName);
				System.out.println("Chk List Verification Time List"+clv_ApplicantName_list);
				break;


			case "Transfer of License Under Nomination" :
				System.out.println("Transfer of License Under Nomination");
				System.out.println("Ready for cheklist verification for Service name Transfer of License Under Nomination-->"+clv_Service_Name);

				mAssert(tlun_mainholder_list.get(CurrentinvoCount),clv_ApplicantName_list.get(CurrentinvoCount),"Applicant name matched of service Transfer of License Under Nomination");
				break;


			case "Cancellation of Trade License":
				System.out.println("Cancellation of Trade License");
				System.out.println("Ready for cheklist verification for Service name Cancellation of Trade License-->"+clv_Service_Name);

				//mAssert(tlun_mainholder_list.get(CurrentinvoCount),clv_ApplicantName_list.get(CurrentinvoCount),"Applicant name matched of service Transfer of License Under Nomination");
				break;
				//Not applicable for khagual
			case "Renewal of Trade License" :
				System.out.println("Renewal of Trade License");
				System.out.println("Ready for cheklist verification for Service name Renewal of Trade License-->"+clv_Service_Name);

				mAssert(Renewal_Holder_Name_list.get(CurrentinvoCount),clv_ApplicantName_list.get(CurrentinvoCount),"Applicant name matched of service Transfer of License Under Nomination");
				break;

			case "Duplicate Trade License" :
				System.out.println("Ready for cheklist verification for Service name Duplicate Trade License-->"+clv_Service_Name);

				mAssert(duplicate_HolderName.get(CurrentinvoCount),clv_ApplicantName_list.get(CurrentinvoCount),"Applicant name matched of service Transfer of License Under Nomination");
				break;


			}

		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in cheklist verification assert method method");			
		}
	}

	// New Trade Scrutiny Assertions

	///////////////////////////////////
	////Market license Scrutiny Methods by Jyoti

	///////////////////////////////

	////jyoti methods for new trade license.. 


	// Can't test View & Modify Assertins due to defect D-11595
	public void ViewandModify_Application()
	{

		try
		{

			if(mGetPropertyFromFile("mkt_ViewApplicationdata").equalsIgnoreCase("Yes"))
			{
				mSendKeys("id", textid,mGetPropertyFromFile("mkt_ViewApplicationdata") ); // mSendkeys --To write Text in Textbox				mTakeScreenShot();
				mGenericWait();
				mClick("css", imageid); // mClick -- To click on image

				mWaitForVisible("css", mGetPropertyFromFile("mkt_ViewModifyAppSubmitBtnid")); 
				mGenericWait();
				mTakeScreenShot();

				mSelectDropDown("id", mGetPropertyFromFile("mkt_Divisionid"),mGetPropertyFromFile ("mkt_Divisiondata"));
				mGenericWait();

				mSelectDropDown("id", mGetPropertyFromFile("mkt_GroupCategoryid"), mGetPropertyFromFile("mkt_GroupCategorydata"));
				mGenericWait();

				mSendKeys("id", mGetPropertyFromFile("mkt_GisIDid"), mGetPropertyFromFile("mkt_GisIDdata")); // To read value from excel sheet -- mGetPropertyFromFile
				mGenericWait();

				mSendKeys("css", mGetPropertyFromFile("mkt_ViewApplicationRemarkid"), mGetPropertyFromFile("mkt_ViewApplicationRemarkdata"));

				mGenericWait();

				mTakeScreenShot();

				//////////// Scrutiny View & Modify TABLE Read for Assertion of Holder / Personal Details

				WebElement table = driver.findElement(By.id("gridPersonalDetails"));
				List<WebElement> rows = table.findElements(By.tagName("tr"));

				int rowcount = rows.size();
				System.out.println("NUMBER OF ROWS IN PERSONAL DETAILS TABLE of Scrutiny ="+rowcount);

				// Iterate To get the value of each cell in table along with element id

				System.out.println("NUMBER OF ROWS IN THIS TABLE of Scrutiny = "+rows.size());

				int row_no = 1;


				for (WebElement rowElement : rows)
				{
					List<WebElement> cols = rowElement.findElements(By.xpath("td"));
					int col_no = 0;
					for(WebElement colElement : cols)
					{
						if(row_no > 1)
						{
							if(col_no == 0)
							{

								String SrNo = rowElement.findElement(By.xpath("./td")).getText();
								mGenericWait();
								System.out.println("Sr. No. of Scrutiny is == "+SrNo);
							}

							if(col_no == 1)
							{
								NewTradeHolderList.add(HolderName.get(CurrentinvoCount));
								System.out.println("Invo Count of New Trade Holder"+NewTradeHolderList);

								//String Scrutiny_HolderName = mGetText("css", mGetPropertyFromFile("MKT_Scrutiny_holderNameid"), "value");

								String ScrutinyName = rowElement.findElement(By.xpath("./td[2]")).getText();
								System.out.println("Scrutiny Holder Name is == "+ScrutinyName);

								ScrutinyNewHolderList.add(ScrutinyName);
								System.out.println("Invo Count of Scrutiny New Trade Holder"+ScrutinyNewHolderList);

								mAssert(NewTradeHolderList, ScrutinyNewHolderList, "New Trade Scrutiny Holdername Assertion Fail  Actual Message :"+NewTradeHolderList+" Expected Message :"+ScrutinyNewHolderList);


							}
							col_no = col_no + 1;	
						}
					}
					row_no = row_no + 1;
				}


				// Scrutiny View & Modify Business Assertion

				NewTradeHolderBusiness.add(HolderBusinessName.get(CurrentinvoCount));

				System.out.println("Invo Count of New Trade Business is ::"+NewTradeHolderBusiness);

				String Scrutiny_business = mGetText("css", mGetPropertyFromFile("mkt_scrutinybusiid"), "value");

				System.out.println("Scrutiny Business Name is == "+Scrutiny_business);

				ScrutinyBusinessName.add(Scrutiny_business);
				System.out.println("Invo Count of Scrutiny Business Name is :::"+ScrutinyBusinessName);

				mAssert(NewTradeHolderBusiness, ScrutinyBusinessName, "New Trade Scrutiny Business Name Assertion Fail  Actual Message :"+NewTradeHolderBusiness+" Expected Message :"+ScrutinyBusinessName);

				//////////////////////////////////////
				//////////////////////////////////////				
				// Scrutiny View & Modify TABLE Read for Assertion of License FEES Details
				//////////////////////////////////////
				//////////////////////////////////////


				WebElement table1 = driver.findElement(By.id("main-table"));
				List<WebElement> rows1 = table1.findElements(By.tagName("tr"));

				int rowcount1 = rows1.size();
				System.out.println("NUMBER OF ROWS IN License Fees DETAILS TABLE of Scrutiny ="+rowcount1);

				// Iterate To get the value of each cell in table along with element id

				System.out.println("NUMBER OF ROWS IN License Fees TABLE of Scrutiny = "+rows1.size());

				int row_no1 = 1;


				for (WebElement rowElement : rows1)
				{
					List<WebElement> cols1 = rowElement.findElements(By.xpath("td"));
					int col_no1 = 0;
					for(WebElement colElement : cols1)
					{
						if(row_no1 > 1)
						{
							if(col_no1 == 0)
							{

								String SrNo1 = rowElement.findElement(By.xpath("./td")).getText();
								mGenericWait();
								System.out.println("Sr. No. of Scrutiny is == "+SrNo1);
							}

							//Write New Code For View & Modify Application of Scrutiny



							if(col_no1 == 1)
							{
								if(row_no1 == 2)
								{
									String scrutiny_MKTlicensefeeType = rowElement.findElement(By.xpath("./td[2]")).getText();
									System.out.println("Scrutiny License Fees Type is Market License Fee == "+scrutiny_MKTlicensefeeType);
									Scrutiny_MKT_HolderFeeType.add(scrutiny_MKTlicensefeeType);

									MKTLicense_FeeTypeList_app.add(MarketLicense_HolderFeeType.get(CurrentinvoCount));
									System.out.println("Invo Count of Market License Fee Type App time =="+MKTLicense_FeeTypeList_app);

									mAssert(Scrutiny_MKT_HolderFeeType, MKTLicense_FeeTypeList_app, "New Trade Scrutiny Market License Fees Type Assertion Fail  Actual Message :"+Scrutiny_MKT_HolderFeeType+" Expected Message :"+MKTLicense_FeeTypeList_app);

								}
							}


							if(col_no1 == 1)
							{
								if(row_no1 == 3)
								{
									String scrutiny_RebatefeeType = rowElement.findElement(By.xpath("./td[2]")).getText();
									System.out.println("Scrutiny License Fees Type is Rebate == "+scrutiny_RebatefeeType);
									Scrutiny_Rebate_HolderFeeType.add(scrutiny_RebatefeeType);

									Rebate_FeeTypeList_app.add(Rebate_HolderFeeType.get(CurrentinvoCount));
									System.out.println("Invo Count of Rebate Fee Type App time =="+Rebate_FeeTypeList_app);

									mAssert(Scrutiny_Rebate_HolderFeeType, Rebate_FeeTypeList_app, "New Trade Scrutiny Rebate Fees Type Assertion Fail  Actual Message :"+Scrutiny_MKT_HolderFeeType+" Expected Message :"+Rebate_FeeTypeList_app);
								}
							}


							/*if(col_no1 == 1)
							{
								NewTradeFeeTypeList.add(HolderFeeType.get(CurrentinvoCount));
								System.out.println("Invo Count of Holder Fee Type"+NewTradeFeeTypeList);

								String ScrutinyFeeType = rowElement.findElement(By.xpath("./td[2]")).getText();
								System.out.println("Scrutiny Fee Type is == "+ScrutinyFeeType);

								ScrutinyFeeTypeList.add(ScrutinyFeeType);
								System.out.println("Invo Count of Scrutiny Fee Type"+ScrutinyFeeTypeList);

								mAssert(NewTradeFeeTypeList, ScrutinyFeeTypeList, "New Trade Scrutiny Fees Type Assertion Fail  Actual Message :"+NewTradeFeeTypeList+" Expected Message :"+ScrutinyFeeTypeList);


							}*/

							/*if(col_no1 == 2)
							{


								String ScrutinyFee = rowElement.findElement(By.xpath("./td[3]")).getText();
								System.out.println("Scrutiny Fee is == "+ScrutinyFee);

								ScrutinyFeesList.add(ScrutinyFee);
								System.out.println("Invo Count of Scrutiny Fee "+ScrutinyFeesList);

							}*/

							if(col_no1 == 2)
							{
								if(row_no1 == 2)
								{
									String scrutiny_MKTlicenseFee = rowElement.findElement(By.xpath("./td[3]")).getText();
									System.out.println("Scrutiny Market License Fees is == "+scrutiny_MKTlicenseFee);
									Scrutiny_MKT_HolderFee.add(scrutiny_MKTlicenseFee);

									MKTLicense_FeeList_app.add(Total_MarketLicenseFee.get(CurrentinvoCount));
									System.out.println("Invo Count of Market License Fee App time =="+MKTLicense_FeeList_app);

									mAssert(Scrutiny_MKT_HolderFee, MKTLicense_FeeList_app, "New Trade Scrutiny Market License Fee Assertion Fail  Actual Message :"+Scrutiny_MKT_HolderFee+" Expected Message :"+MKTLicense_FeeList_app);


								}
							}

							//******************************
							if(col_no1 == 2)
							{
								if(row_no1 == 3)
								{
									String license_Rebatefee = rowElement.findElement(By.xpath("./td[3]")).getText();
									System.out.println("Rebate in Market License Fee == "+license_Rebatefee);
									Rebate_HolderFee.add(license_Rebatefee);

									Rebate_FeeList_app.add(Total_RebateFee.get(CurrentinvoCount));
									System.out.println("Invo Count of Rebate Fee App time =="+Rebate_FeeList_app);

									mAssert(Rebate_HolderFee, Rebate_FeeList_app, "New Trade Scrutiny Rebate Fee Assertion Fail  Actual Message :"+Rebate_HolderFee+" Expected Message :"+Rebate_FeeList_app);


								}
							}

							//*********************************

							if(col_no1 == 3)
							{
								NewTradeTotalAmountList.add(HolderTotalAmount.get(CurrentinvoCount));
								System.out.println("Invo Count of Holder Total Amount == "+NewTradeTotalAmountList);

								String ScrutinyTotalAmount = rowElement.findElement(By.xpath("./td[4]")).getText();
								System.out.println("Scrutiny Total Amount is == "+ScrutinyTotalAmount);

								ScrutinyTotalAmountList.add(ScrutinyTotalAmount);
								System.out.println("Invo Count of Scrutiny Total Amount "+ScrutinyTotalAmountList);

								mAssert(NewTradeTotalAmountList, ScrutinyTotalAmountList, "New Trade Scrutiny Total Amount Assertion Fail  Actual Message :"+NewTradeTotalAmountList+" Expected Message :"+ScrutinyTotalAmountList);
							}

						}
						col_no1 = col_no1 + 1;	
					}
					row_no1 = row_no1 + 1;
				}


				//NewTradeFeesList.add(MarketLicense_HolderFee.get(CurrentinvoCount));
				//System.out.println("Invo Count of Holder Fee == "+NewTradeFeesList);

				//mAssert(NewTradeFeesList, ScrutinyFeesList, "New Trade Scrutiny Fees Assertion Fail  Actual Message :"+NewTradeFeesList+" Expected Message :"+ScrutinyFeesList);


				mClick("css", mGetPropertyFromFile("mkt_ViewModifyAppSubmitBtnid"));

				//mWaitForVisible("id", textid);
				//mWaitForVisible("css", imageid);


			}


			else
			{
				mSendKeys("id", textid, mGetPropertyFromFile("mkt_ViewApplicationdata"));


				System.out.println("Modification in application form does not required");
			}
		}
		catch(Exception e)
		{

		}

	}



	public void Final_Decision()
	{
		try
		{
			if(mGetPropertyFromFile("mkt_FinalDecisiondata").equalsIgnoreCase("Yes"))
			{
				mSendKeys("id", textid, mGetPropertyFromFile("mkt_FinalDecisiondata"));
				mGenericWait();

				//Generate_LOI();
				System.out.println("LOI Generation Process is Applicable");

			}
			else if(mGetPropertyFromFile("mkt_FinalDecisiondata").equalsIgnoreCase("No"))
			{
				mSendKeys("id", textid, "No");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	public void Generate_LOI()
	{
		try
		{
			if(mGetPropertyFromFile("mkt_GenerateLOIdata").equalsIgnoreCase("Yes"))
			{
				mSendKeys("id", textid, mGetPropertyFromFile("mkt_GenerateLOIdata")); 
				mGenericWait();
				mClick("css", imageid); 

				mWaitForVisible("css", mGetPropertyFromFile("mkt_GenerateLOISubmitBtnid"));
				mGenericWait();
				mTakeScreenShot();

				mWaitForVisible("name", mGetPropertyFromFile("mkt_LOIRemarkid"));
				mGenericWait();
				mSendKeys("name", mGetPropertyFromFile("mkt_LOIRemarkid"), mGetPropertyFromFile("mkt_LOIRemarkdata"));
				mGenericWait();

				///If else to be added for authorizing or not authorizing???????

				mClick("id", mGetPropertyFromFile("mkt_Authorizeid"));
				mGenericWait();
				mTakeScreenShot();

				//**********************************************************

				//////////////////////LOI Scrutiny Holder Name Assertion


				NewLOITradeHolderName.add(HolderName.get(CurrentinvoCount));
				System.out.println("Invo count of New Trade Holder for LOI is :: "+NewLOITradeHolderName);

				String Scrutiny_LOIholdername = mGetText("name", mGetPropertyFromFile("mkt_scrutinyLOIholdernameid"), "value");
				System.out.println("Scrutiny LOI Holder Name is:: "+Scrutiny_LOIholdername);

				ScrutinyLOIHolderNameList.add(Scrutiny_LOIholdername);
				System.out.println("Invo Count of Scrutiny LOIHolder Name is :::"+ScrutinyLOIHolderNameList);

				mAssert(NewLOITradeHolderName, ScrutinyLOIHolderNameList, "New Trade LOI Scrutiny Holder Name Assertion Fail  Actual Message :"+NewLOITradeHolderName+" Expected Message :"+ScrutinyLOIHolderNameList);


				/////////////////////LOI Scrutiny Business Assertion

				NewLOITradeHolderBusiness.add(HolderBusinessName.get(CurrentinvoCount));
				System.out.println("Invo Count of New Trade Business for LOI is ::"+NewLOITradeHolderBusiness);

				String Scrutiny_LOIbusiness = mGetText("css", mGetPropertyFromFile("mkt_scrutinyLOIbusiid"), "value");
				System.out.println("Scrutiny LOI Business Name is == "+Scrutiny_LOIbusiness);

				ScrutinyLOIBusinessName.add(Scrutiny_LOIbusiness);
				System.out.println("Invo Count of Scrutiny LOI Business Name is :::"+ScrutinyBusinessName);

				mAssert(NewLOITradeHolderBusiness, ScrutinyLOIBusinessName, "New Trade Scrutiny Business Name Assertion Fail  Actual Message :"+NewLOITradeHolderBusiness+" Expected Message :"+ScrutinyLOIBusinessName);

				/////////////////////LOI Scrutiny Total Payable Amount Assertion

				NewLOITotalPayableAmt.add(TotalPayableAmount.get(CurrentinvoCount));
				System.out.println("Invo count of Total Payable at App is :: "+NewLOITotalPayableAmt);

				String Scrutiny_LOITotalPayable = mGetText("css", mGetPropertyFromFile("mkt_LOITotPayableid"), "value");
				System.out.println("Scrutiny LOI Total Payable is:: "+Scrutiny_LOITotalPayable);

				ScrutinyLOITotalPayableList.add(Scrutiny_LOITotalPayable);
				System.out.println("Invo Count of Scrutiny LOI Total Payable is :::"+ScrutinyLOITotalPayableList);

				mAssert(ScrutinyLOITotalPayableList, NewLOITotalPayableAmt, "New Trade LOI Scrutiny Total Payable Amount Assertion Fail  Actual Message :"+ScrutinyLOITotalPayableList+" Expected Message :"+NewLOITotalPayableAmt);

				///////////////////LOI Scrutiny Total Payable Amount Assertion

				NewLOITotalAmt.add(Total_MarketLicenseFee.get(CurrentinvoCount));
				System.out.println("Invo count of Total Amount at App is :: "+NewLOITotalAmt);

				String Scrutiny_LOITotalAmount = mGetText("css", mGetPropertyFromFile("mkt_LOITotalAmtid"), "value");
				System.out.println("Scrutiny LOI Total Amount is:: "+Scrutiny_LOITotalAmount);

				Scrutiny_LOITotalAmountList.add(Scrutiny_LOITotalAmount);
				System.out.println("Invo Count of Scrutiny LOI Total Amount is :::"+Scrutiny_LOITotalAmountList);

				mAssert(Scrutiny_LOITotalAmountList, Scrutiny_LOITotalAmountList, "New Trade LOI Scrutiny Total Amount Assertion Fail  Actual Message :"+Scrutiny_LOITotalAmountList+" Expected Message :"+Scrutiny_LOITotalAmountList);


				///////////////////LOI Scrutiny Rebate Amount Assertion

				NewLOIRebateAmt.add(Total_RebateFee.get(CurrentinvoCount));
				System.out.println("Invo count of Rebate at App is :: "+NewLOIRebateAmt);

				String Scrutiny_LOIRebate = mGetText("css", mGetPropertyFromFile("mkt_LOIRebateid"), "value");
				System.out.println("Scrutiny LOI Rebate is:: "+Scrutiny_LOIRebate);

				Scrutiny_LOIRebateList.add(Scrutiny_LOIRebate);
				System.out.println("Invo Count of Scrutiny LOI Rebate is :::"+Scrutiny_LOIRebateList);

				mAssert(Scrutiny_LOIRebateList, NewLOIRebateAmt, "New Trade LOI Scrutiny Rebate Amount Assertion Fail  Actual Message :"+Scrutiny_LOIRebateList+" Expected Message :"+NewLOIRebateAmt);

				//**********************************************************

				mWaitForVisible("css",mGetPropertyFromFile("mkt_GenerateLOISubmitBtnid"));
				mClick("css",mGetPropertyFromFile("mkt_GenerateLOISubmitBtnid"));
				mGenericWait();
				//mCustomWait(3000);		
				mTakeScreenShot();
				//mWaitForVisible("id", textid);
				//mWaitForVisible("css", imageid);

			}


		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}


	public void Reject_Scrutiny()
	{

		try
		{
			mSendKeys("id", textid, mGetPropertyFromFile("mkt_RejectionLetterdata"));
			mGenericWait();
			mTakeScreenShot(); 
			mClick("css", imageid);
			mWaitForVisible("css", mGetPropertyFromFile("mkt_RejectionSubmitbtnid"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_RejectionRemarkid"), mGetPropertyFromFile("mkt_RejectionRemarkdata"));
			mGenericWait();
			licensenum_newtrade="Reject";

			EW.excelWriting(mGetPropertyFromFile("licecepath"),"License_Number",licensenum_newtrade);

			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("mkt_RejectionSubmitbtnid"));
			mGenericWait();
			mTakeScreenShot();
			//mWaitForVisible("id", textid);
			//mWaitForVisible("css", imageid);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	////Change in Business Name Common Methods

	public void ChngBusiName_Decision()
	{
		try
		{
			if(mGetPropertyFromFile("mkt_ChngBusiName_Decisiondata").equalsIgnoreCase("Yes"))
			{
				mSendKeys("id", textid, mGetPropertyFromFile("mkt_ChngBusiName_Decisiondata"));
				mGenericWait();

				System.out.println("LOI Generation Process is Applicable");
			}

			else if(mGetPropertyFromFile("mkt_ChngBusiName_Decisiondata").equalsIgnoreCase("No"))
			{
				mSendKeys("id", textid, "No");
			}

		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	/// View Application Form = YES can't execute due to defect D-10826
	public void ChngBusiName_ViewAppForm()
	{
		try
		{

			if (mGetPropertyFromFile("mkt_ChngBusiName_ViewAppFormdata").equalsIgnoreCase("Yes"))
			{

				mSendKeys("id", textid, mGetPropertyFromFile("mkt_ChngBusiName_ViewAppFormdata"));
				mGenericWait();
				mClick("css", imageid);

				mWaitForVisible("xpath", mGetPropertyFromFile("mkt_ChngBusiNameVewModifyAppBackBtnid"));
				mGenericWait();



				// Change in Business View Application assertion

				//////////////
				// Scrutiny View Application Holder Name assertion
				///////////////

				ChangeinBusinessHolderList.add(ChangeBusinessNameHolderName.get(CurrentinvoCount));
				System.out.println("Invo count of Change in Business Holder Name is :: "+ChangeinBusinessHolderList);

				String ChngBusi_Scrutiny_holdername = mGetText("css", mGetPropertyFromFile("mkt_chngbusiname_scrutinyholdernameid"), "value");
				System.out.println("Scrutiny Change in Business Name is:: "+ChngBusi_Scrutiny_holdername);

				ChangeinBusinessScrutinyHolderList.add(ChngBusi_Scrutiny_holdername);
				System.out.println("Invo Count of Change in Business Scrutiny Holder Name is :::"+ChangeinBusinessScrutinyHolderList);

				mAssert(ChangeinBusinessHolderList, ChangeinBusinessScrutinyHolderList, "Change in Business Name Scrutiny Holder Name Assertion Fail  Actual Message :"+ChangeinBusinessHolderList+" Expected Message :"+ChangeinBusinessScrutinyHolderList);

				//////////////////
				// Scrutiny View Application Old Holder Name assertion
				//////////////////

				ChangeBusinessScrutinyOldBusinessName.add(ChangeBusinessNameHolderName.get(CurrentinvoCount));
				System.out.println("Invo count of Change in Business Old Business Name is :: "+ChangeBusinessScrutinyOldBusinessName);

				String ChngBusi_Old_holdername = mGetText("css", mGetPropertyFromFile("mkt_chngbusiname_oldBusiNameid"), "value");
				System.out.println("Scrutiny Old Business Name is:: "+ChngBusi_Old_holdername);

				ChangeBusinessScrutinyOldBusinessNameList.add(ChngBusi_Old_holdername);
				System.out.println("Invo Count of Change in Business Old Business Name is :::"+ChangeBusinessScrutinyOldBusinessNameList);

				mAssert(ChangeBusinessScrutinyOldBusinessName, ChangeBusinessScrutinyOldBusinessNameList, "Chane in Business Old Business Name Assertion Fail  Actual Message :"+ChangeBusinessScrutinyOldBusinessName+" Expected Message :"+ChangeBusinessScrutinyOldBusinessNameList);

				//////////////////////
				// Scrutiny View Application New Holder Name assertion
				//////////////////////

				ChangeBusinessScrutinyNewBusinessName.add(ChangeBusinessNameHolderName.get(CurrentinvoCount));
				System.out.println("Invo count of Change in Business New Business Name is :: "+ChangeBusinessScrutinyNewBusinessName);

				String ChngBusi_New_holdername = mGetText("css", mGetPropertyFromFile("mkt_chngbusiname_newBusiNameid"), "value");
				System.out.println("Scrutiny New Business Name is:: "+ChngBusi_New_holdername);

				ChangeBusinessScrutinyNewBusinessNameList.add(ChngBusi_New_holdername);
				System.out.println("Invo Count of Change in Business New Business Name is :::"+ChangeBusinessScrutinyNewBusinessNameList);

				mAssert(ChangeBusinessScrutinyNewBusinessName, ChangeBusinessScrutinyNewBusinessNameList, "Chane in Business New Business Name Assertion Fail  Actual Message :"+ChangeBusinessScrutinyNewBusinessName+" Expected Message :"+ChangeBusinessScrutinyNewBusinessNameList);


				/*
				mUpload("id", mGetPropertyFromFile("mkt_chngBusiNameUploadid"), mGetPropertyFromFile("mkt_chngBusiNameUpload"));
				mGenericWait();

				mUpload("id", mGetPropertyFromFile("mkt_chngBusiNameUploadid1"), mGetPropertyFromFile("mkt_chngBusiNameUpload1"));
				mGenericWait();
				 */
				mClick("xpath", mGetPropertyFromFile("mkt_ChngBusiNameVewModifyAppBackBtnid"));
				mGenericWait();

			}
			else if(mGetPropertyFromFile("mkt_ChngBusiName_ViewAppFormdata").equalsIgnoreCase("No"))
			{
				mSendKeys("id", textid, "No");
			}

		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	/// Can't execute due to defect D-10979 -- LOI number is not generated & while sending to Printer Grid appears an error message 
	//so ca't execute LOI Payment & License Printing also

	public void ChngBusiName_GenerateLOI()
	{
		try
		{
			if(mGetPropertyFromFile("mkt_ChngBusiName_GenerateLOIdata").equalsIgnoreCase("Yes"))
			{
				mSendKeys("id", textid, mGetPropertyFromFile("mkt_ChngBusiName_GenerateLOIdata")); 
				mGenericWait();

				mClick("css", imageid);

				mGenericWait();
				mWaitForVisible("css", mGetPropertyFromFile("mkt_chngBusiName_GenerateLOISubmitBtnid"));
				mGenericWait();
				mTakeScreenShot();

				mWaitForVisible("id", mGetPropertyFromFile("mkt_chngBusiName_LOIRemarkid"));
				mGenericWait();
				mSendKeys("name", mGetPropertyFromFile("mkt_chngBusiName_LOIRemarkid"), mGetPropertyFromFile("mkt_chngBusiName_LOIRemarkdata"));
				mGenericWait();

				///If else to be added for authorizing or not authorizing???????

				mClick("id", mGetPropertyFromFile("mkt_chngBusiName_Authorizeid"));

				//*******************************************************************************
				//////////////
				// Scrutiny LOI Application Holder Name assertion
				///////////////

				ChangeinBusinessLOIHolderList.add(ChangeBusinessNameHolderName.get(CurrentinvoCount));
				System.out.println("Invo count of Change in Business LOI change in Business name is :: "+ChangeinBusinessLOIHolderList);

				String ChngBusi_LOI_holdername = mGetText("css", mGetPropertyFromFile("mkt_chngbusiname_LOIholdernameid"), "value");
				System.out.println("Scrutiny LOI Change in Business Name is:: "+ChngBusi_LOI_holdername);

				ChangeinBusinessScrutinyLOIHolderList.add(ChngBusi_LOI_holdername);
				System.out.println("Invo Count of Change in Business LOI Holder Name is :::"+ChangeinBusinessScrutinyLOIHolderList);
				System.out.println("Invo count of Change in Business LOI change in Business name is :: "+ChangeinBusinessLOIHolderList);
				//mAssert(ChangeinBusinessLOIHolderList,ChangeinBusinessScrutinyHolderList, "Change in Business Name LOI Holder Name Assertion Fail  Actual Message :"+ChangeinBusinessLOIHolderList+" Expected Message :"+ChangeinBusinessScrutinyLOIHolderList);

				//////////////
				// Scrutiny LOI Application Business Name (old name) assertion
				///////////////

				ChangeinBusinessLOIoldBusinessnameList.add(ChangeBusinessOldBusinessName.get(CurrentinvoCount));
				System.out.println("Invo count of Change in Business LOI old Business name is :: "+ChangeinBusinessLOIoldBusinessnameList);

				String ChngBusi_LOI_oldBusiName = mGetText("css", mGetPropertyFromFile("mkt_chngbusiname_LOIoldholdernameid"), "value");
				System.out.println("Scrutiny time LOI old Business Name is:: "+ChngBusi_LOI_oldBusiName);

				ChangeBusinessScrutinyLOIOldBusinessNameList.add(ChngBusi_LOI_oldBusiName);
				System.out.println("Invo Count of Change in Business LOI old business Name is :::"+ChangeBusinessScrutinyLOIOldBusinessNameList);

				mAssert(ChangeBusinessScrutinyLOIOldBusinessNameList, ChangeinBusinessLOIoldBusinessnameList, "Change in Business Name LOI old business Name Assertion Fail  Actual Message :"+ChangeBusinessScrutinyLOIOldBusinessNameList+" Expected Message :"+ChangeinBusinessLOIoldBusinessnameList);


				//////////////
				// Scrutiny LOI Total Amount
				///////////////

				String ChngBusi_LOI_TotalAmt = mGetText("css", mGetPropertyFromFile("mkt_chngbusiname_LOI_TotalAmountid"), "value");
				System.out.println("Scrutiny LOI Total Amount is:: "+ChngBusi_LOI_TotalAmt);

				ChangeinBusinessScrutinyLOITotalAmount.add(ChngBusi_LOI_TotalAmt);
				System.out.println("Invo Count of Change in Business LOI Holder Name is :::"+ChangeinBusinessScrutinyLOITotalAmount);
				//*******************************************************************************

				mWaitForVisible("css",mGetPropertyFromFile("mkt_chngBusiName_GenerateLOISubmitBtnid"));
				mClick("css",mGetPropertyFromFile("mkt_chngBusiName_GenerateLOISubmitBtnid"));

				mCustomWait(3000);		
				mTakeScreenShot();
				//mWaitForVisible("id", textid);
				//mWaitForVisible("css", imageid);

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	public void ChngBusiName_Reject_Scrutiny()
	{
		try
		{
			mSendKeys("id", textid, mGetPropertyFromFile("mkt_chngBusiName_RejectionLetterdata"));
			mGenericWait();
			mTakeScreenShot(); 

			mClick("css", imageid);

			mWaitForVisible("css", mGetPropertyFromFile("mkt_chngBusiName_RejectionSubmitbtnid"));
			mGenericWait();
			mSendKeys("xpath", mGetPropertyFromFile("mkt_chngBusiName_RejectionRemarkid"), mGetPropertyFromFile("mkt_chngBusiName_RejectionRemarkdata"));
			mGenericWait();

			mClick("css", mGetPropertyFromFile("mkt_chngBusiName_RejectionSubmitbtnid"));
			mGenericWait();
			mTakeScreenShot();
			//mWaitForVisible("id", textid);
			//mWaitForVisible("css", imageid);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}




	//// Transfer of License Other Mode Common Methods

	public void transfr_Decision()
	{
		try
		{
			if(mGetPropertyFromFile("mkt_transfrDecisiondata").equalsIgnoreCase("Yes"))
			{
				mSendKeys("id", textid, mGetPropertyFromFile("mkt_transfrDecisiondata"));
				System.out.println("LOI Generation Process is Applicable");
			}
			else

				if(mGetPropertyFromFile("mkt_transfrDecisiondata").equalsIgnoreCase("No"))

					mSendKeys("id", textid, mGetPropertyFromFile("mkt_transfrDecisiondata"));

			System.err.println("Rejecting the Transfer License Other Mode Application");

		}


		catch(Exception e)
		{
			e.printStackTrace();
		}

	}


	public void transfr_GenerateLOI()
	{
		try
		{

			if(mGetPropertyFromFile("mkt_transfrGenerateLOIdata").equalsIgnoreCase("Yes"))
				mSendKeys("id", textid, mGetPropertyFromFile("mkt_transfrGenerateLOIdata"));
			mGenericWait();
			mClick("css", imageid);

			mWaitForVisible("name", mGetPropertyFromFile("mkt_transfrLOIRemarkid"));
			mGenericWait();

			mSendKeys("name", mGetPropertyFromFile("mkt_transfrLOIRemarkid"), mGetPropertyFromFile("mkt_transfrLOIRemarkdata"));
			mGenericWait();

			mClick("id", mGetPropertyFromFile("mkt_transfrAuthorizeid"));
			mGenericWait();



			// Transfer LOI License No.Assertion by Jyoti

			TransferOtherModeLOILicenseList.add(TransferOtherModeLicenseNo.get(CurrentinvoCount));
			System.out.println("Invo count of Transfer Other Mode LOI License no is :: "+TransferOtherModeLOILicenseList);

			String TransferOtherMode_LOI_LicenseNo = mGetText("css", mGetPropertyFromFile("mkt_transferOtherMode_LOILicensenoid"), "value");
			System.out.println("Scrutiny LOI License No is:: "+TransferOtherMode_LOI_LicenseNo);

			TransferOtherModeScrutinyLOILicenseNoList.add(TransferOtherMode_LOI_LicenseNo);
			System.out.println("Invo Count of Transfer Other Mode LOI License No is :::"+TransferOtherModeScrutinyLOILicenseNoList);

			mAssert(TransferOtherModeLOILicenseList, TransferOtherModeScrutinyLOILicenseNoList, "Transfer Other Mode LOI License No Assertion Fail  Actual Message :"+TransferOtherModeLOILicenseList+" Expected Message :"+TransferOtherModeScrutinyLOILicenseNoList);

			// Transfer Other Mode LOI Holder Name Assertion by Jyoti

			TransferOtherModeLOIHoldername.add(TransferOtherModeLicenseNo.get(CurrentinvoCount));
			System.out.println("Invo count of Transfer Other Mode LOI Holder Name is :: "+TransferOtherModeLOIHoldername);

			String TransferOtherMode_LOI_Holdername = mGetText("css", mGetPropertyFromFile("TransferOtherMode_LOI_Holdernameid"), "value");
			System.out.println("Scrutiny LOI Holder Name is:: "+TransferOtherMode_LOI_Holdername);

			TransferOtherModeScrutinyLOIHolderNameList.add(TransferOtherMode_LOI_Holdername);
			System.out.println("Invo Count of Transfer Other Mode LOI Holder Name is :::"+TransferOtherModeScrutinyLOIHolderNameList);

			mAssert(TransferOtherModeLOIHoldername, TransferOtherModeScrutinyLOIHolderNameList, "Transfer Other Mode LOI Holder Name Assertion Fail  Actual Message :"+TransferOtherModeLOIHoldername+" Expected Message :"+TransferOtherModeScrutinyLOIHolderNameList);

			String TransferOtherMode_LOI_TotalFees = mGetText("css", mGetPropertyFromFile("TransferOtherMode_LOI_TotalFeesid"), "value");
			System.out.println("Transfer Other Mode LOI Total Fees :: "+TransferOtherMode_LOI_TotalFees);

			TransferOtherModeScrutinyLOITotalFeesList.add(TransferOtherMode_LOI_TotalFees);
			System.out.println("Invo Count of Transfer Other Mode LOI Total Amount is :::"+TransferOtherModeScrutinyLOITotalFeesList);

			mClick("xpath", mGetPropertyFromFile("mkt_transfrLOISubmitid"));
			mGenericWait();
			mTakeScreenShot();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	public void transfr_Reject_Scrutiny()
	{
		try
		{
			if(mGetPropertyFromFile("mkt_transfrDecisiondata").equalsIgnoreCase("No"))
			{
				mSendKeys("id", textid, mGetPropertyFromFile("mkt_transfrDecisiondata"));
				mGenericWait();
				mClick("css", imageid);

				mWaitForVisible("xpath", mGetPropertyFromFile("mkt_transfrRejectSubmitBtnid"));

				mSendKeys("id", mGetPropertyFromFile("mkt_transfrRejectRemarkid"), mGetPropertyFromFile("mkt_transfrRejectRemarkdata"));
				mGenericWait();

				mClick("xpath", mGetPropertyFromFile("mkt_transfrRejectSubmitBtnid"));
				mCustomWait(2000);
				mTakeScreenShot();
			}

		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	// Duplicate of License Scrutiny Assertions
	public void duplicate_LOI()
	{
		try
		{
			//mWaitForVisible("css", mGetPropertyFromFile("mkt_loiGenSubmitBtnid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("mkt_loiAuthorisedYesid"));
			mGenericWait();
			mSendKeys("name", mGetPropertyFromFile("mkt_loiRemarkid"), mGetPropertyFromFile("mkt_loiRemark"));
			mGenericWait();
			mTakeScreenShot();
			String duplicate_lcno=mGetText("name",mGetPropertyFromFile("MKT_issueduplicateLicenseNoid"),"value");

			String duplicate_bname=mGetText("name",mGetPropertyFromFile("MKT_BusinessNameid"),"value");
			System.out.println("**@"+duplicate_HolderName.get(CurrentinvoCount));
			duplicate_holder_list.add(duplicate_HolderName.get(CurrentinvoCount));
			String duplicate_loi_holdername=mGetText("name", mGetPropertyFromFile("mkt_duplicate_loi_holdername_id"),"value");
			duplicate_loi_holdernamelist.add(duplicate_loi_holdername);
			System.out.println("new arraylist***"+duplicate_holder_list);
			System.out.println("loi holder names arraylist***"+duplicate_loi_holdernamelist);
			duplicate_bname_list.add(duplicate_bname);
			mAssert(duplicate_holder_list, duplicate_loi_holdernamelist, "Duplicate holder name not matched");
			System.out.println("***duplicate***"+duplicate_bname_list);
			
			mClick("css", mGetPropertyFromFile("mkt_loiGenSubmitBtnid"));
			//duplicate_HolderBusinessName
			duplicate_loiHolderBusinessName.add(duplicate_HolderBusinessName.get(CurrentinvoCount));
			mGenericWait();
			System.out.println("duplicate holder name:============="+duplicate_bname_list);
			System.out.println("duplicate holder name:============="+duplicate_loiHolderBusinessName);
			mAssert(duplicate_bname_list, duplicate_loiHolderBusinessName, "Holder name is not matching ");
			/*mWaitForVisible("css", mGetPropertyFromFile("mkt_loiGenFinalSubmitBtnid"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("mkt_loiGenFinalSubmitBtnid"));
			mWaitForVisible("id", mGetPropertyFromFile("mkt_loiGenProceedBtnid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("mkt_loiGenProceedBtnid"));*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in marketLOIGeneration method");
		}
	}

	public void Duplicate_Rejection()
	{
		try
		{
			//mWaitForVisible("css", mGetPropertyFromFile("mkt_rejectionSubmitBtnid"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_rejectionRemarksid"), mGetPropertyFromFile("mkt_rejectionRemarks"));
			duplicate_rejHolderBusinessName.add(duplicate_HolderName.get(CurrentinvoCount));
			System.out.println("duplicate_rejHolderBusinessName list===========:"+duplicate_rejHolderBusinessName);
			String duplicate_rej_holdername=mGetText("name",mGetPropertyFromFile("MKT_reg_holdername"),"value");
			duplicate_rejformHolderBusinessName.add(duplicate_rej_holdername);
			System.out.println("duplicate_rejformHolderBusinessName list:======================"+duplicate_rejformHolderBusinessName);
			mAssert(duplicate_rejHolderBusinessName, duplicate_rejformHolderBusinessName, "Holder name appearing on rejection form is not match with the service name form");
			mGenericWait();
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("mkt_rejectionSubmitBtnid"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in marketRejection method");
		}
	}

	// Cancellation of License Scrutiny Assertions
	public void  viewandmodifi()
	{
		try
		{
			if(mGetPropertyFromFile("mkt_cancellation_viewmodifi_data").equalsIgnoreCase("YES"))
			{
				//mWaitForVisible("id", mGetPropertyFromFile("mkt_scrutyPrcessCanOfLicViewImg_id"));
				mClick("id", mGetPropertyFromFile("mkt_scrutyPrcessCanOfLicViewImg_id"));

				//Wait for form to open and click on back
				//	mWaitForVisible("id", mGetPropertyFromFile("mkt_scrutyPrcessCanOfLicAppNo_id"));				
				mCustomWait(2000);

				mClick("css", mGetPropertyFromFile("mkt_scrutyPrcessCanOfLicBckBtn_id"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in marketLOIGeneration method");
		}

	}
	public void cancellation_marketLOIGeneration()
	{
		try
		{
			mCustomWait(2000);
			mClick("css", imageid);
			//mClick("id", mGetPropertyFromFile("mkt_scrutyPrcessCanOfLicViewImg_id"));

			//mWaitForVisible("css", mGetPropertyFromFile("mkt_loiGenSubmitBtnid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("mkt_loiAuthorisedYesid"));
			mGenericWait();
			mSendKeys("name", mGetPropertyFromFile("mkt_loiRemarkid"), mGetPropertyFromFile("mkt_loiRemark"));
			mGenericWait();
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("mkt_loiGenSubmitBtnid"));
			mGenericWait();
			/*mWaitForVisible("css", mGetPropertyFromFile("mkt_loiGenFinalSubmitBtnid"));
		mGenericWait();
		mClick("css", mGetPropertyFromFile("mkt_loiGenFinalSubmitBtnid"));
		mWaitForVisible("id", mGetPropertyFromFile("mkt_loiGenProceedBtnid"));
		mGenericWait();
		mClick("id", mGetPropertyFromFile("mkt_loiGenProceedBtnid"));*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in marketLOIGeneration method");
		}

	}
	public void Renewal_rejection_letter()
	{
		try
		{

			/*mWaitForVisible("id", mGetPropertyFromFile("mkt_renTradeLicScrtyFinalDecision_id"));
				mSendKeys("id", mGetPropertyFromFile("mkt_renTradeLicScrtyFinalDecision_id"), mGetPropertyFromFile("mkt_renTradeLicScrtyFinalDecision_data"));
				mGenericWait();

				mWaitForVisible("id", mGetPropertyFromFile("mkt_renTradeLicScrtyGenRejDec_id"));
				mSendKeys("id", mGetPropertyFromFile("mkt_renTradeLicScrtyGenRejDec_id"), mGetPropertyFromFile("mkt_renTradeLicScrtyGenRejDec_data"));*/
			mGenericWait();

			marketRejection();

			mWaitForVisible("css", mGetPropertyFromFile("mkt_scrutinyProcessSubmitBtnid"));
			mGenericWait();
			//asserting applicant name
			String applicant_name=mGetText("name",mGetPropertyFromFile("MKT_Rej_applicantname"),"value");
			applicant_name_list.add(applicant_name);
			applicant_name_service_list.add(Renewal_Holder_Name_list.get(CurrentinvoCount));

			mAssert(applicant_name_list, applicant_name_service_list, "Holder name on service page is not matching with rejection form page");

			//capturing rejection remark field
			String Scrutiny_rej_remark=mGetText("name",mGetPropertyFromFile("MKT_Rej_Remark"),"value");
			Scrutiny_rej_remark_list.add(Scrutiny_rej_remark);
			System.out.println("Scrutiny remarks rejection"+Scrutiny_rej_remark_list);


			mClick("css", mGetPropertyFromFile("mkt_scrutinyProcessSubmitBtnid"));

			mWaitForVisible("id", mGetPropertyFromFile("mkt_scrutinyProcessProceedBtnid"));
			mGenericWait();

			String saveMsg = mGetText("css", mGetPropertyFromFile("mkt_scrutinyProcessRejectionMsgid"));
			System.out.println(saveMsg);
			mAssert(mGetPropertyFromFile("mkt_newLicenseRejectionSaveMsg"), saveMsg, "Message does not match, Expected: "+mGetPropertyFromFile("mkt_newLicenseRejectionSaveMsg")+" || Actual: "+saveMsg);


			mClick("id", mGetPropertyFromFile("mkt_scrutinyProcessProceedBtnid"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Scrutiny_ChangeInNameOfBusiness method");
		}
	}
	public void marketRejection()
	{
		try
		{
			//mWaitForVisible("css", mGetPropertyFromFile("mkt_rejectionSubmitBtnid"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_rejectionRemarksid"), mGetPropertyFromFile("mkt_rejectionRemarks"));
			mGenericWait();
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("mkt_rejectionSubmitBtnid"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in marketRejection method");
		}
	}
	public void Renewal_Market_LOI_Generation()
	{
		try
		{
			//mWaitForVisible("id", mGetPropertyFromFile("mkt_renTradeLicScrtyFinalDecision_id"));
			//mSendKeys("id", mGetPropertyFromFile("mkt_renTradeLicScrtyFinalDecision_id"), mGetPropertyFromFile("mkt_renTradeLicScrtyFinalDecision_data"));
			mGenericWait();

			//Generate LOI
			//mWaitForVisible("id", mGetPropertyFromFile("mkt_renTradeLicScrtyGenLoiDec_id"));
			//mSendKeys("id", mGetPropertyFromFile("mkt_renTradeLicScrtyGenLoiDec_id"), mGetPropertyFromFile("mkt_renTradeLicScrtyGenLoiDec_data"));
			//mGenericWait();



			//mClick("xpath", mGetPropertyFromFile("mkt_renTradeLicScrtyGenLoiViewDetImg_id"));

			marketLOIGeneration();

			mWaitForVisible("css", mGetPropertyFromFile("mkt_scrutinyProcessSubmitBtnid"));
			mGenericWait();

			mClick("css", mGetPropertyFromFile("mkt_scrutinyProcessSubmitBtnid"));

			mWaitForVisible("id", mGetPropertyFromFile("mkt_scrutinyProcessProceedBtnid"));
			mGenericWait();

			String loiNumber = mGetText("css", mGetPropertyFromFile("mkt_scrutinyPRocessLOINoid"),"Value");
			System.out.println("Generated LOI number is : "+loiNumber);

			Pattern loiNo = Pattern.compile("[0-9]+");
			Matcher LOINo = loiNo.matcher(loiNumber);

			if(LOINo.find()) {				
				LOINum = LOINo.group();
				System.out.println("LOI No is: "+LOINum);
			}

			strippedSaveString = loiNumber.replace(LOINum, "");
			System.out.println(strippedSaveString);
			mAssert(mGetPropertyFromFile("mkt_newLicenseLOISaveMsg"), strippedSaveString, "Message does not match, Expected: "+mGetPropertyFromFile("mkt_newLicenseLOISaveMsg")+" || Actual: "+strippedSaveString);


			mClick("id", mGetPropertyFromFile("mkt_scrutinyProcessProceedBtnid"));
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Scrutiny_ChangeInNameOfBusiness method");
		}

	}
	public void marketLOIGeneration()
	{
		try
		{
			//mWaitForVisible("css", mGetPropertyFromFile("mkt_loiGenSubmitBtnid"));



			mGenericWait();
			mClick("id", mGetPropertyFromFile("mkt_loiAuthorisedYesid"));
			mGenericWait();
			mSendKeys("name", mGetPropertyFromFile("mkt_loiRemarkid"), mGetPropertyFromFile("mkt_loiRemark"));
			mGenericWait();
			mTakeScreenShot();

			// getiing application number from loi page
			String application_number=mGetText("name", mGetPropertyFromFile("MKT_Renewal_applicationid"),"value");
			application_number_list.add(application_number);
			//auto list of application number
			String appnumber_list=applicationNo.get(CurrentinvoCount);
			application_number_list.add(appnumber_list);
			//asserting application number
			mAssert(application_number_list, application_number_list, "application number is not matching");

			//asserting license number
			String Licenseno=mGetText("name", mGetPropertyFromFile("MKT_Renewal_License_noid"),"value");
			Ren_scrutiny_Licensenolist.add(Licenseno);
			mAssert(Renewal_licenseNo_list, Ren_scrutiny_Licensenolist, "Renewal lisense no matched");

			//asserting holder name
			String Ren_loi_holdername=mGetText("name", mGetPropertyFromFile("MKT_Renewal_scrutiny_holder nameid"),"value");
			Ren_loi_holdername_list.add(Ren_loi_holdername);
			Renewal_loi_Holder_Name_list.add(Renewal_Holder_Name_list.get(CurrentinvoCount));
			mAssert(Ren_loi_holdername_list, Renewal_loi_Holder_Name_list, "Holder name of renewal is not matching with loi page holder name");

			//asserting business name
			Renew_loi_businessname_list.add(Renew_businessname_list.get(CurrentinvoCount));
			String 	Ren_loi_business_name=mGetText("name", mGetPropertyFromFile("MKT_Renewal_Business_Nameid"),"value");
			Ren_loi_business_name_list.add(Ren_loi_business_name);
			mAssert(Renew_loi_businessname_list, Ren_loi_business_name_list, "Business is not matching with service business name of renewal of license");

			//asserting total amount
			Ren_loi_totalamt_list.add(totalamt_list.get(CurrentinvoCount)); 			
			String tax_amount=mGetText("name", mGetPropertyFromFile("MKT_Renewal_Amount _id"),"value");	
			tax_amount_list.add(tax_amount);
			mAssert(Ren_loi_totalamt_list, tax_amount_list, "amount is not matching with taxamount");





			mClick("css", mGetPropertyFromFile("mkt_loiGenSubmitBtnid"));
			mGenericWait();
			/*mWaitForVisible("css", mGetPropertyFromFile("mkt_loiGenFinalSubmitBtnid"));
		mGenericWait();
		mClick("css", mGetPropertyFromFile("mkt_loiGenFinalSubmitBtnid"));
		mWaitForVisible("id", mGetPropertyFromFile("mkt_loiGenProceedBtnid"));
		mGenericWait();
		mClick("id", mGetPropertyFromFile("mkt_loiGenProceedBtnid"));*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in marketLOIGeneration method");
		}
	}

	/////////////
	//Transfer Under Nomination Scrutiny Assertions
	///////////////

	public void tlun_loi_generation()
	{
		try
		{
			//mWaitForVisible("css", mGetPropertyFromFile("mkt_loiGenSubmitBtnid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("mkt_loiAuthorisedYesid"));
			mGenericWait();
			mSendKeys("name", mGetPropertyFromFile("mkt_loiRemarkid"), mGetPropertyFromFile("mkt_loiRemark"));
			mGenericWait();
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("mkt_loiGenSubmitBtnid"));
			mGenericWait();
			//asserting license number
			String tlun_scutiny_licenseno=mGetText("name",mGetPropertyFromFile("mkt_scrutiny_license_no"),"value");
			tlun_scrutiny_licenseno_list.add(tlun_scutiny_licenseno);

			tlun_scrutiny_loi_list.add(tlun_licenseno_list.get(CurrentinvoCount));

			mAssert(tlun_scrutiny_licenseno_list, tlun_scrutiny_loi_list, "license no is matched with license number of scrutin");

			//capturing holder name 
			String tlun_scrutiny_loi_holdename=mGetText("name",mGetPropertyFromFile("mkt_scrutiny_loi_holdername_no"),"value");
			tlun_scrutiny_loi_holdename_list.add(tlun_scrutiny_loi_holdename);
			//capturing business name 
			String tlun_scrutiny_loi_businessname=mGetText("name",mGetPropertyFromFile("mkt_scrutiny_loi_businessname_no"),"value");
			mkt_scrutiny_loi_businessname_no_list.add(tlun_scrutiny_loi_businessname);
			//asserting holder name 
			mkt_service_holdername_no_list.add(tlunholdername_list.get(CurrentinvoCount));
			mAssert(mkt_service_holdername_no_list, tlun_scrutiny_loi_holdename_list,"holder name is getting matched");
			//asserting business name 
			mkt_service_businessname_no_list.add(tlun_busineess_name_list.get(CurrentinvoCount));
			mAssert(mkt_scrutiny_loi_businessname_no_list, mkt_service_businessname_no_list, "business name is not getting matched");


			/*mWaitForVisible("css", mGetPropertyFromFile("mkt_loiGenFinalSubmitBtnid"));
mGenericWait();
mClick("css", mGetPropertyFromFile("mkt_loiGenFinalSubmitBtnid"));
mWaitForVisible("id", mGetPropertyFromFile("mkt_loiGenProceedBtnid"));
mGenericWait();
mClick("id", mGetPropertyFromFile("mkt_loiGenProceedBtnid"));*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in tlun marketLOIGeneration method");
		}


	}

	public void tlun_rejection()
	{
		try
		{
			mWaitForVisible("css", mGetPropertyFromFile("mkt_rejectionSubmitBtnid"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("mkt_rejectionRemarksid"), mGetPropertyFromFile("mkt_rejectionRemarks"));
			mGenericWait();
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("mkt_rejectionSubmitBtnid"));  
			//capturing holder name 
			String tlun_reg_holdername=mGetText("name",mGetPropertyFromFile("tlun_reg_holdername_id") ,"value");
			tlun_reg_holdername_list.add(tlun_reg_holdername);

			tlun_reg_holdername_scrutiny_list.add(tlunholdername_list.get(CurrentinvoCount));
			mAssert(tlun_reg_holdername_list, tlun_reg_holdername_scrutiny_list, "holdername on rejection flow is not matching");
			//capturing mobileno
			String tlun_reg_mobileno=mGetText("name",mGetPropertyFromFile("tlun_reg_mobileno_id") ,"value");
			tlun_reg_mobileno_list.add(tlun_reg_mobileno);

			tlunholdername_mobile__loreglist.add(tlunholdername_mobile_list.get(CurrentinvoCount));
			mAssert(tlun_reg_mobileno_list, tlunholdername_mobile__loreglist, "mobile no of holder is not matching");

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in tlun marketLOIGeneration method");
		}


	}

	////////////////////////////////////////////////////////////

	////  Printer Grid - Intimation Letter Assertions

	//////////////////////////////////////////////////////////
	public void PrinterGrid_Assertions()
	{
		
		api.PdfPatterns.tlunmpdploi1(pdfoutput);

		System.out.println("prceding for assertion :--> " + servicenameprintergrid);
		switch (servicenameprintergrid)
		{
		case "New Trade License":
			///////////////// Holder Name

			System.out.println("START Intimation Letter - Printer Grid Assertion");
			api.PdfPatterns.pattern_NewTrade_IntimationPdf(pdfoutput);

			System.out.println("proceding for assertion :--> " + servicenameprintergrid+"service wise assertion");

			//int IntiArraySize = Inti_NewLicense_HolderNm_List.size();
			//System.out.println("Inti Array List=="+IntiArraySize);
			//int appArraySize = Application_NewLicense_HolderNm.size();
			//System.out.println("App Array Size== "+appArraySize);

			Application_NewLicense_HolderNm.add(HolderName.get(CurrentinvoCount));
			System.out.println("New Trade License Application time Holder Name list"+Application_NewLicense_HolderNm);

			Inti_NewLicense_HolderNm_List.add(Inti_NewLicense_HolderNm.get(CurrentinvoCount));
			System.out.println("New Trade License Intimation time Holder Name List"+Inti_NewLicense_HolderNm_List);

			mAssert(Application_NewLicense_HolderNm, Inti_NewLicense_HolderNm_List, "Wrong Holder Name on Intimation Letter of New Trade License :"+Application_NewLicense_HolderNm+"Expected Message :"+Inti_NewLicense_HolderNm_List);

			//////////////////////// Total Amount

			Application_NewLicense_TotalAmount.add(TotalPayableAmount.get(CurrentinvoCount));

			System.out.println("New Trade License Application time Total Payable Amount"+Application_NewLicense_TotalAmount);

			Intimation_NewTrade_TotalAmountList.get(CurrentinvoCount);
			System.out.println("New Trade License Intimation time Total Payable Amount"+Intimation_NewTrade_TotalAmountList);

		//	mAssert(Application_NewLicense_TotalAmount, Intimation_NewTrade_TotalAmountList, "Wrong Total Payable Amount on Intimation Letter of New Trade License :"+Application_NewLicense_TotalAmount+"Expected Message :"+Intimation_NewTrade_TotalAmountList);

			//////////////////////// Total Market License Fee


			Application_NewLicense_MarketLicenseFee.add(Total_MarketLicenseFee.get(CurrentinvoCount));

			System.out.println("New Trade License Application time Total Market License Fee"+Application_NewLicense_MarketLicenseFee);

			Intimation_MKTLicenseFee.get(CurrentinvoCount);
			System.out.println("New Trade License Intimation time Total Market License Fee Amount"+Intimation_MKTLicenseFee);

		//	mAssert(Intimation_MKTLicenseFee, Application_NewLicense_MarketLicenseFee, "Wrong Total Market License Fee on Intimation Letter of New Trade License :"+Intimation_MKTLicenseFee+"Expected Message :"+Application_NewLicense_MarketLicenseFee);


			////////////////////////Total Rebate Fee


			Application_NewLicense_RebateFee.add(Total_RebateFee.get(CurrentinvoCount));

			System.out.println("New Trade License Application time Total Rebate Fee"+Application_NewLicense_RebateFee);


			Intimation_MKTRebate.get(CurrentinvoCount);
			System.out.println("New Trade License Intimation time Total Market License Fee Amount"+Intimation_MKTRebate);

		//	mAssert(Intimation_MKTRebate, Application_NewLicense_RebateFee, "Wrong Total Rebate Fee on Intimation Letter of New Trade License :"+Intimation_MKTRebate+"Expected Message :"+Application_NewLicense_RebateFee);

			////////////////////////LOI No

			///////////////At Scrutiny Submission time LOI is not Authorized so can't Assert LOI no.//////////////////////

			//	System.out.println("New Trade License Application time LOI No is:: "+Application_NewLicense_RebateFee);

			Inti_LOINo.get(CurrentinvoCount);
			System.out.println("New Trade License Intimation time LOI no is:: "+Inti_LOINo);

			Scrutiny_Msg_LOINoList.get(CurrentinvoCount);
			System.out.println("After Final Scrutiny generated LOI No List is::: "+Scrutiny_Msg_LOINoList);
		//	mAssert(Inti_LOINo, Scrutiny_Msg_LOINoList, "Actual msg::::"+Inti_LOINo+" Expected::::"+mGetPropertyFromFile("Scrutiny_Msg_LOINoList"));

			//mAssert(Inti_LOINo, Application_NewLicense_RebateFee, "Wrong LOI No on Intimation Letter of New Trade License :"+Inti_LOINo+"Expected Message :"+Application_NewLicense_RebateFee);
			break;
		case "Change In Name Of Business":

			///////////////// Holder Nmae
			api.PdfPatterns.pattern_ChngBusiNm_IntimationPdf(pdfoutput);

			Intimation_ChngBusiNm_HolderNm.add(IntiLetter_ChngBusiNm_HolderNm.get(CurrentinvoCount));
			System.out.println("Change in Business Name Intimation Letter Holder Name"+Intimation_ChngBusiNm_HolderNm);

			ChangeBusinessNameHolderName.get(CurrentinvoCount); 
			System.out.println("Change in Business Name Application time Holder Name : "+ChangeBusinessNameHolderName);

		//	mAssert(Intimation_ChngBusiNm_HolderNm, ChangeBusinessNameHolderName, "Wrong Holder Name on Intimation Letter ofChange in Business Name is::"+Intimation_ChngBusiNm_HolderNm+"Expected Message :"+ChangeBusinessNameHolderName);

			////////////Total Amount

			IntiLetter_ChngBusiNm_LOITotalAmount.add(ChangeinBusinessScrutinyLOITotalAmount.get(CurrentinvoCount));
			System.out.println("Change in Business Name LOI time Total Amount :: "+IntiLetter_ChngBusiNm_LOITotalAmount);

			IntiLetter_ChngBusiNameTotalAmount.get(CurrentinvoCount);
			System.out.println("Change in Business Name Intimation Letter Total Amount : "+IntiLetter_ChngBusiNameTotalAmount);

			//mAssert(IntiLetter_ChngBusiNm_LOITotalAmount, IntiLetter_ChngBusiNameTotalAmount, "New Trade LOI Scrutiny Total Amount Assertion Fail  Actual Message :"+IntiLetter_ChngBusiNm_LOITotalAmount+" Expected Message :"+IntiLetter_ChngBusiNameTotalAmount);
			break;

		case "Transfer Of License Other Mode":

			api.PdfPatterns.pattern_InitLetterOtherModePDF(pdfoutput);

			////////////// Holder Name
			IntiLetter_TOM_HolderName.add(TransferOtherModeHolderName.get(CurrentinvoCount));
			System.out.println("Transfer Other Mode Application time Holder Name:: "+IntiLetter_TOM_HolderName);

			IntiLetter_HolderNameList.get(CurrentinvoCount);
			System.out.println("Transfer Other Mode Intimation time Holder Name:: "+IntiLetter_HolderNameList);

		//	mAssert(IntiLetter_TOM_HolderName, IntiLetter_HolderNameList, "Transfer Other Mode Intimation Holder Name Assertion Fail Actual Message :"+IntiLetter_TOM_HolderName+"Expected Message :"+IntiLetter_HolderNameList);
			//////////// Total Amount

			IntiLetter_TOM_TotalFees.add(TransferOtherModeScrutinyLOITotalFeesList.get(CurrentinvoCount));
			System.out.println("Transfer Other Mode LOI time Total fees:: "+IntiLetter_TOM_TotalFees);	

			IntiLetter_TotalAmountList.get(CurrentinvoCount);
			System.out.println("Transfer Other Mode Intimation time Total Fees:: "+IntiLetter_TotalAmountList);
		//	mAssert(IntiLetter_TOM_TotalFees, IntiLetter_TotalAmountList, "Transfer Other Mode Intimation Total Amount Assertion Fail Actual Message :"+IntiLetter_TOM_TotalFees+"Expected Message :"+IntiLetter_TotalAmountList);
			break;
		case "Renewal Of Trade License":

			api.PdfPatterns.pattern_RenewalLicIntimationLetter(pdfoutput);
			////////////////// Holder Name

			IntiLetter_Renew_HolderName.add(Renewal_Holder_Name_list.get(CurrentinvoCount));
			System.out.println("Renewal of License Application time Holder Name:: "+IntiLetter_Renew_HolderName);

			IntimationLetter_Renew_HolderName.get(CurrentinvoCount);
			System.out.println("Renewal of License Intimation time Holder Name:: "+IntimationLetter_Renew_HolderName);

		//	mAssert(IntiLetter_Renew_HolderName, IntimationLetter_Renew_HolderName, "Renewal Intimation Holder Name Assertion Fail Actual Message :"+IntiLetter_Renew_HolderName+"Expected Message :"+IntimationLetter_Renew_HolderName);

			/////////////////// Total Amount

			IntiLetter_Renew_TotalAmountApp.add(Renewal_Holder_Name_list.get(CurrentinvoCount));
			System.out.println("Renewal of License Application time Total Amount:: "+IntiLetter_Renew_TotalAmountApp);

			IntimationLetter_Renew_TotalAmount.get(CurrentinvoCount);
			System.out.println("Renewal of License Intimation time Total Amount:: "+IntimationLetter_Renew_TotalAmount);

		//	mAssert(IntiLetter_Renew_TotalAmountApp, IntimationLetter_Renew_TotalAmount, "Renewal Intimation Total Amount Assertion Fail Actual Message :"+IntiLetter_Renew_TotalAmountApp+"Expected Message :"+IntimationLetter_Renew_TotalAmount);
			break;

		case "Transfer Of License Under Nomination":

			api.PdfPatterns.patterntlunMarketLicensePdf(pdfoutput);
			//////////////////////// Holder Name
//InvoCount
			IntiLetter_tun_HolderNameApp.add(tlunholdername_list.get(CurrentinvoCount));
			System.out.println("Transfer Under Nomination Application time Holder Name:: "+IntiLetter_tun_HolderNameApp);

			IntimationLetter_tun_HolderName.get(CurrentinvoCount);
			System.out.println("Transfer Under Nomination Intimation time Holder Name:: "+IntimationLetter_tun_HolderName);

		//	mAssert(IntiLetter_tun_HolderNameApp, IntimationLetter_tun_HolderName, "Transfer Under Nomination Intimation Holder Name Assertion Fail Actual Message :"+IntiLetter_tun_HolderNameApp+"Expected Message :"+IntimationLetter_tun_HolderName);
			break;
		case "Duplicate Trade License":

			////////////////////Holder Name
System.out.println("================================================>Duplicate Trade License");
			api.PdfPatterns.patternduplicateMarketLicensePdf(pdfoutput);
			IntiLetter_Duplicate_HolderNameApp.add(pdf_license_duplicate_holdername.get(CurrentinvoCount));
			System.out.println("Duplicate License Application time Holder Name:: "+IntiLetter_Duplicate_HolderNameApp);

			Inti_Duplicate_HolderNm.get(CurrentinvoCount);
			System.out.println("Duplicate Intimation time Holder Name"+Inti_Duplicate_HolderNm);

		//	mAssert(IntiLetter_Duplicate_HolderNameApp, Inti_Duplicate_HolderNm, "Wrong Holder Name on Intimation Letter of Duplicate License :"+IntiLetter_Duplicate_HolderNameApp+"Expected Message :"+Inti_Duplicate_HolderNm);

			/////////////////////Total Amount

			IntiLetter_Duplicate_TotalFeesApp.add(DupTotalFeesList.get(CurrentinvoCount));
			System.out.println("Duplicate License Application time Total Fees:: "+IntiLetter_Duplicate_TotalFeesApp);

			IntiLetter_Duplicate_TotalFees.get(CurrentinvoCount);
			System.out.println("Duplicate License Intimation time Total Fees:: "+IntiLetter_Duplicate_TotalFees);	
		//	mAssert(IntiLetter_Duplicate_TotalFeesApp, IntiLetter_Duplicate_TotalFees, "Wrong Duplicate License on Intimation Letter of Duplicate License :"+IntiLetter_Duplicate_TotalFeesApp+"Expected Message :"+IntiLetter_Duplicate_TotalFees);

			break;

		default:
			break;
		}

	}

	////////////////Market LOI Application Form Assertion

	public void LOI_Application_Assert()
	{
		try
		{
			// LOI no
			//String LOINumber = mGetText("css", mGetPropertyFromFile("LOI_LOINoid"));
			//LOI_LOINoList.add(LOINumber);
			//System.out.println("LOI Application Time License No is::"+LOI_LOINoList);

			// LOI discount
			//String LOI_Discount = mGetText("css",mGetPropertyFromFile("LOI_Discountid"));

			/////// LOI Discount is applicable only for New Trade License Service but not for rest of services so LOI_Discountid Assertion Fail

			/*
			String LOI_Discount = mGetText("css", mGetPropertyFromFile("LOI_Discountid"), "value");
			LOI_DiscountList.add(LOI_Discount);
			System.out.println("LOI Application Time Discount::"+LOI_DiscountList);
			 */
			// LOI Payment Mode
			String LOI_PaymentMode = mGetPropertyFromFile("ulbCounterPaymntMode");
			LOI_PaymentModeList.add(LOI_PaymentMode);
			System.out.println("LOI Application Time Payment Mode::"+LOI_PaymentModeList);

			/////// LOI Total Received Amount is applicable only for New Trade License Service but not for rest of services so LOI_TotalReceivedAmountid Assertion Fail
			// LOI Total Received Amount
			/*String LOI_TotalReceivedAmt = mGetText("css", mGetPropertyFromFile("LOI_TotalReceivedAmountid"), "value");
			LOI_TotalReceivedAmtList.add(LOI_TotalReceivedAmt);
			System.out.println("LOI Application Time Total Received Amount::"+LOI_TotalReceivedAmtList);
			 */
			//LOI Holder Name

			String LOI_HolderName = mGetText("css", mGetPropertyFromFile("LOI_HolderNameid"), "value");
			LOI_HolderNameList.add(LOI_HolderName);
			System.out.println("LOI Application Time Holder Name::"+LOI_HolderName);

 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in cheklist verification assert method method");			
		}
	}

	//////////////////Market LOI CASH/DD/PO Payment Receipt Assertion

	public void Receipt_Assertion()
	{
		try
		{
			//***********************************************
			// Market CASH/DD/PO Payment Receipt Assertion

			// Holder Name (Received From)
			LOIReceipt_ReceivedFromList.add(LOI_ReceivedFrom.get(CurrentinvoCount));
			System.out.println("Invo count of LOI Receipt Holder Name::"+LOIReceipt_ReceivedFromList);

			mAssert(LOI_HolderNameList, LOI_HolderNameList, "LOI Receipt Cash Holder Name Assertion Fail  Actual Message :"+LOI_HolderNameList+" Expected Message :"+LOI_HolderNameList);


			// Total Received Amount
			LOIReceipt_TotalReceivedAMountList.add(LOI_TotalReceivedAmt.get(CurrentinvoCount));
			System.out.println("Invo count of LOI Receipt Total Received Amount::"+LOIReceipt_TotalReceivedAMountList);

			mAssert(LOIReceipt_TotalReceivedAMountList, LOI_TotalReceivedAmtList, "LOI Receipt Cash Totsl Received Amount Assertion Fail  Actual Message :"+LOIReceipt_TotalReceivedAMountList+" Expected Message :"+LOI_TotalReceivedAmtList);

			// Payment Mode Cash/DD/PO

			LOIReceipt_PaymentModeList.add(LOI_PaymentMode.get(CurrentinvoCount));
			System.out.println("Invo count of LOI Receipt Payment Mode::"+LOIReceipt_PaymentModeList);

			mAssert(LOIReceipt_PaymentModeList, LOI_PaymentModeList, "LOI Receipt Cash Payment Mode Assertion Fail  Actual Message :"+LOIReceipt_PaymentModeList+" Expected Message :"+LOI_PaymentModeList);

			// LOI Discount

			LOIReceipt_DiscountList.add(LOI_Discount.get(CurrentinvoCount));
			System.out.println("Invo count of LOI Discount::"+LOIReceipt_DiscountList);

			mAssert(LOIReceipt_DiscountList, LOI_DiscountList, "LOI Receipt Cash Discount Assertion Fail  Actual Message :"+LOIReceipt_DiscountList+" Expected Message :"+LOI_DiscountList);

			//LOI No.
			/*LOIReceipt_LOINoList.add(LOI_LOINo.get(CurrentinvoCount));
			System.out.println("Invo count of LOI No is::"+LOIReceipt_LOINoList); // In pdfPattern value of 'LOIReceipt_LOINoList' is wrong so Assertion is Fail.

			mAssert(LOIReceipt_LOINoList, LOINumberList, "LOI Number does not match at LOI Payment Receipt.  Actual  :"+LOIReceipt_LOINoList+"   Expected   :"+LOINumberList);

			 */		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in cheklist verification assert method");			
		}
	}

	//////////////////////////
	///////////// Market License Print Assertion Method
	//////////////////////////

	public void LicensePrinting_Assertion()
	{
		//api.PdfPatterns.tlunmpdploi(pdfoutput); //09-01-2017

		System.out.println("License Printing Assertion START");

		switch (license_printing_servicename) 
		{

		case "new_trade_license":
			System.out.println("Entered in License Printing Assertion START");

			//api.PdfPatterns.patternNewMarketLicensePdf(output);

			api.PdfPatterns.patternNewMarketLicensePdf(output,licHolderName,busiName);
			//////////////// Holder Name

			/*mAssert(pdf_new_holdername, HolderName, "New Trade Holder Name of License Printing Assertion Fail  Actual Message :"+pdf_new_holdername+" Expected Message :"+HolderName);

			////////////////Holder Total Fee

			mAssert(pdf_Total_Fees_List, Total_MarketLicenseFee, "New Trade Total Fee of License Printing Assertion Fail  Actual Message :"+pdf_Total_Fees_List+" Expected Message :"+Total_MarketLicenseFee);

			////////////////Holder Business Name

			mAssert(pdf_new_holder_BusinessName, HolderBusinessName, "New Trade Holder Business Name of License Printing Assertion Fail  Actual Message :"+pdf_new_holder_BusinessName+" Expected Message :"+HolderBusinessName);
*/
			license_printing_servicename="";
			break;
		case "transfer_under_nomination":
			api.PdfPatterns.patterntlunMarketLicensePdf(pdfoutput);
			//mAssert(pdf_tlun_holdername,tlun_mainholder_list,"Holder name is not matching");
		//	mAssert(pdf_tlun_businessname,tlun_busineess_name_list ,"business name is not matching");

			break;
		case "Renewal_of_license":
			api.PdfPatterns.patternrenewalMarketLicensePdf(pdfoutput);

		//	mAssert(pdf_renewal_holdername,Renewal_Holder_Name_list,"Holder name is not matching");
		//	mAssert(pdf_renwal_businessname,Renew_businessname_list ,"business name is not matching");
			break;
		case "Duplicate_license":
			api.PdfPatterns.patternduplicateMarketLicensePdf(pdfoutput);
			//mAssert(pdf_license_duplicate_holdername, duplicate_HolderName, "Holder name appearing on report is not matching with  service holder name ");
		//	mAssert(pdf_license_duplicate_businessname, duplicate_HolderBusinessName, "business name appearing on report is not matching with  service business name ");
			break;
		default:
			//System.out.println("license_printing_servicename value blank;"+license_printing_servicename);
		//	System.out.println("Assertion are not applicable");
			break; 
		}


	}



} // Final End








