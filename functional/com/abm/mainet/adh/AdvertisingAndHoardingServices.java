package com.abm.mainet.adh;


import java.awt.Desktop.Action;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.security.Credentials;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import api.CommonUtilsAPI;
import api.PdfPatterns;
import clubbedapi.ClubbedAPI;

import com.abm.mainet.bnd.CustomListener;
import com.abm.mainet.rnl.RentAndLeaseCustomErrorMessages;
import com.google.common.base.Throwables;

import common.CommonMethods;
import common.ScrutinyProcess;
import errorhandling.MainetCustomExceptions;
import excelreader.ExcelReader;
import excelreader.ExcelToProperties;

/**
 * @author tejas.kotekar
 * @since  23-03-2016
 * This class contains scripts for test automation of Rent and Lease module of MAINet.
 *
 */

@Listeners(CustomListener.class)
public class AdvertisingAndHoardingServices extends CommonMethods{
	public static boolean LOiGenration=false;
	public static boolean Rejectionletter=false;
	String appSubmitMsg;
	String strippedString;
	ExcelToProperties excelToProp = new ExcelToProperties();
	PdfPatterns pdfpattern= new PdfPatterns();
	static ScrutinyProcess scrutiny = new ScrutinyProcess();
	ExcelReader ER = new ExcelReader();
	public String pageNo;
	public static int Flag=0;
	public  int jj=0;
	public static boolean isinspectionclear=false;
	//-----------------------------------------Start///////Piyush//------------	
	public static ArrayList<String> HoardingCode = new ArrayList<String>();
	public static String rrcapplName;
	public static String selectedBookingAmt;
	public static String selectedHoardingType;
	public static String selectedHoardingAddr;
	public static String selectedHoardingName;
	public static String selectedHoardingCode;
	public static String ADHserviceName;
	public static String boeapplName;
	public static String strDate;
	public static String startDate;
	public static String endDate;
	public static int diffMonthanddays;
	public static String ScrutinyServiceName;
	public static int noofmonths;
	public static int rwcount;
	public static int kk=0;
	public static int ll=0;
	public static int hearingorder=0;
	public static int HearingNo=0;

	//-----------------------------------------End///// Piyush-------------	

	// used in bill generation
	public static int num;

	// object for scrutiny method
	ScrutinyProcess scrutinyGenericMethod = new ScrutinyProcess ();

	ClubbedAPI ClubbedUtils = new ClubbedAPI();

	@BeforeSuite
	public void beforeSuite(){

		thisClassName=this.getClass().getCanonicalName();
		myClassName = thisClassName;
		mCreateModuleFolder("ADH_",myClassName);		
	}

	public static boolean conditionX = false; // For checklist Time rejection


	// @ Test :1 Booking Of Horading 
	@Test(enabled=false)
	public void adh_bookingOfHording() throws InterruptedException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.clear();
			mCreateArtefactsFolder("ADH_");
			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				applicationNo.clear();
				ApplicationNoADH.clear();
				adh_ApplicantFullname.clear();
				Adh_ModeOfPaymentAppTime.clear();
				ContractBillPayment_ContractNo.clear();
			}
			bookingOfHording();
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_bookingOfHording method");
		}
	}


	//@ Test :2 Booking Of Horading Checklist Verification
	@Test(enabled=false)
	public void adh_chekcListVerification() throws InterruptedException, IOException{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			mCreateArtefactsFolder("ADH_");

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

			}
			System.out.println("chekindexes===>"+chekindexes);
			System.out.println("approvechekindexes"+approvechekindexes);
			checkListVerification();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.clear();
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_chekcListVerification method");
		}
	}


	//@ Test :3 Booking Of Horading Scrutiny Process
	@Test(enabled=false)
	public void adh_scrutinyProcess() throws InterruptedException{
		try{
			jj=0;
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			mCreateArtefactsFolder("ADH_");



			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				scrutinyindexes.clear();	
			}




			if (!(chekindexes.contains(CurrentinvoCount))){



				if (mGetPropertyFromFile("adh_LOIGenFinalDecisiondata").equalsIgnoreCase("no")) {
					scrutinyindexes.add(CurrentinvoCount);
					System.out.println("scrutinyindexes==>"+scrutinyindexes);
				}




				for(scrutinylevelcounter=1;scrutinylevelcounter<=Integer.parseInt(mGetPropertyFromFile("noOfScrutinyLevels"));scrutinylevelcounter++)
				{
					scrutinyGenericMethod.ScrutinyProcess();
				}
			}
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.clear();
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_scrutinyProcess method");
		}
	}
	//@ Test :4 Booking Of Horading LOI Printer grid Counter
	@Test(enabled=false)
	public void adh_printergrid() throws InterruptedException, IOException{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.clear();
			mCreateArtefactsFolder("ADH_");
			if (!(chekindexes.contains(CurrentinvoCount))){

				CFCprintergrid(mGetPropertyFromFile("cfc_ADHprnGridUserName"),mGetPropertyFromFile("cfc_ADHprnGridPassword"));
				adh_Acceptance_letter_assertion();
			}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in ADH_printergrid method");
		}
	}
	//@ Test :5 Booking Of Horading LOI Payment
	@Test(enabled=false)
	public void adh_Loipayment() throws InterruptedException, IOException{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			mCreateArtefactsFolder("ADH_");
			BOE_LOI_Collection = true;
			if (!(chekindexes.contains(CurrentinvoCount))){
				if(!(scrutinyindexes.contains(CurrentinvoCount))) {

					loiPayment(mGetPropertyFromFile("adh_LoiPaymentModuleNameData"),mGetPropertyFromFile("cfc_challanVreBookingOfHoardingServiceName"));
				}}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_Loipayment method");
		}
	}

	//@ Test :6 Booking Of Horading Bill Generation 
	@Test(enabled=false)
	public void adh_BillGeneration() throws InterruptedException, IOException{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			if (!(chekindexes.contains(CurrentinvoCount))) {
				if(!(scrutinyindexes.contains(CurrentinvoCount))) {

					billGeneration();
				}}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_BillGeneration method");
		}
	}

	//@ Test :7 Booking Of Horading Bill Printing
	@Test(enabled=false)
	public void adh_BillPrinting() throws InterruptedException, IOException{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			if (!(chekindexes.contains(CurrentinvoCount))) {
				if(!(scrutinyindexes.contains(CurrentinvoCount))) {
					billPrinting();
				}
				CommonUtilsAPI.adhErrorMsg.assertAll();
			}}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_BillPrinting method");
		}
	}


	//@ Test :8 Booking Of Horading Contract Bill Payment
	@Test(enabled=false)
	public void adh_contractBillPayment() throws InterruptedException, IOException{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			if (!(chekindexes.contains(CurrentinvoCount))) {
				if(!(scrutinyindexes.contains(CurrentinvoCount))) {
					contractBillPayment();}}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_ContractBillPayment method");
		}
	}


	//@ Test :9 Booking Of Horading Approval Letter Printing
	@Test(enabled=false)
	public void adh_ApprovalLetterPrinting() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			if (!(chekindexes.contains(CurrentinvoCount))) {
				if(!(scrutinyindexes.contains(CurrentinvoCount))) {
					approvalLetterPrinting();}}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_ApprovalLetterPrinting method");
		}
	}


	// @ Test :10 Setup Of Hoarding for Display Of Advertisement form 
	@Test(enabled=false)
	public void adh_SetUpOfHoarding() throws InterruptedException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				applicationNo.clear();
				ApplicationNoADH.clear();
				adh_ApplicantFullname.clear();
				Adh_ModeOfPaymentAppTime.clear();
				ContractBillPayment_ContractNo.clear();
			}
			setUpOfHording();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.clear();
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_bookingOfHording method");
		}
	}


	//@ Test : 11 Setup Of Hoarding for Display Of Advertisement Checklist Verification
	@Test(enabled=false)
	public void adh_setUpOfHoardingchekcListVerification() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			mCreateArtefactsFolder("ADH_");
			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				chekindexes.clear();	
			}



			checkListVerification();
			if (mGetPropertyFromFile("wantToRejectAtChecklist").equalsIgnoreCase("yes")) {
				chekindexes.add(CurrentinvoCount);
				System.out.println("chekindexes"+chekindexes);
			} 
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.clear();
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_chekcListVerification method");
		}
	}

	//@ Test :12 Setup Of Hoarding for Display Of Advertisement Scrutiny Process
	@Test(enabled=false)
	public void adh_setUpOfHoardingscrutinyProcess() throws InterruptedException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			mCreateArtefactsFolder("ADH_");



			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				scrutinyindexes.clear();	
			}


			if (!(chekindexes.contains(CurrentinvoCount))) {



				if (mGetPropertyFromFile("adh_LOIGenFinalDecisiondata").equalsIgnoreCase("no")) {
					scrutinyindexes.add(CurrentinvoCount);
					System.out.println("scrutinyindexes==>"+scrutinyindexes);
				}







				/*if(!mGetPropertyFromFile("wantToRejectAtChecklist").equalsIgnoreCase("No")){
				System.out.println("adh_Loipayment Skipped as it is rejected");		
			}
			else{*/

				for(scrutinylevelcounter=1;scrutinylevelcounter<=Integer.parseInt(mGetPropertyFromFile("noOfScrutinyLevels"));scrutinylevelcounter++)
				{
					scrutinyGenericMethod.ScrutinyProcess();
				}

				AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.clear();
				CommonUtilsAPI.adhErrorMsg.assertAll();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_scrutinyProcess method");
		}
	}

	//@ Test : 13 Setup Of Hoarding for Display Of Advertisement LOI Printer grid Counter
	@Test(enabled=false)
	public void adh_setUpOfHoardingprintergrid() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.clear();
			mCreateArtefactsFolder("ADH_");
			/*	if(!mGetPropertyFromFile("wantToRejectAtChecklist").equalsIgnoreCase("No")){
				System.out.println("adh_Loipayment Skipped as it is rejected");		
			}
			else{*/


			if (!(chekindexes.contains(CurrentinvoCount))) {
				CFCprintergrid(mGetPropertyFromFile("cfc_ADHprnGridUserName"),mGetPropertyFromFile("cfc_ADHprnGridPassword"));
				api.PdfPatterns.ADH_Acceptance_Letter_Pdf(pdfoutput);
			}
			adh_Acceptance_letter_assertion();
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in ADH_printergrid method");
		}
	}

	//@ Test : 14 Setup Of Hoarding for Display Of Advertisement LOI Payment
	@Test(enabled=false)
	public void adh_setUpOfHoardingLoipayment() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			mCreateArtefactsFolder("ADH_");
			BOE_LOI_Collection = true;
			if (!(chekindexes.contains(CurrentinvoCount))) {

				if(!(scrutinyindexes.contains(CurrentinvoCount))) {
					loiPayment(mGetPropertyFromFile("adh_LoiPaymentModuleNameData"),mGetPropertyFromFile("cfc_challanVreBookingOfHoardingServiceName"));

				}}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_Loipayment method");
		}
	}

	//@ Test : 15 Setup Of Hoarding for Display Of Advertisement Bill Generation 
	@Test(enabled=false)
	public void adh_setUpOfHoardingBillGeneration() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			if (!(chekindexes.contains(CurrentinvoCount))) {

				if(!(scrutinyindexes.contains(CurrentinvoCount))) {
					billGeneration();
				}}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_BillGeneration method");
		}
	}

	//@ Test :16 Setup Of Hoarding for Display Of Advertisement Bill Printing
	@Test(enabled=false)
	public void adh_setUpOfHoardingBillPrinting() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			if (!(chekindexes.contains(CurrentinvoCount))) {

				if(!(scrutinyindexes.contains(CurrentinvoCount))) {
					billPrinting();
				}}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_BillPrinting method");
		}
	}

	//@ Test :17 Setup Of Hoarding for Display Of Advertisement Contract Bill Payment
	@Test(enabled=false)
	public void adh_setUpOfHoardingContractBillPayment() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				chekindexes.clear();	
			}


			if (!(chekindexes.contains(CurrentinvoCount))) {

				if(!(scrutinyindexes.contains(CurrentinvoCount))) {
					contractBillPayment();
				}}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_ContractBillPayment method");
		}
	}


	//@ Test :18 setup Of Hoarding for Display Of Advertisement Approval Letter Printing
	@Test(enabled=false)
	public void adh_setUpOfHoardingApprovalLetterPrinting() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			if (!(chekindexes.contains(CurrentinvoCount))) {

				if(!(scrutinyindexes.contains(CurrentinvoCount))) {
					approvalLetterPrinting();
				}}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_ApprovalLetterPrinting method");
		}
	}


	//@ Test : 19 Renewal of Advertisment contract form Add test
	@Test(enabled=false)
	public void adh_AdvtRenewalOfAdvtContract( ) throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				applicationNo.clear();
				ApplicationNoADH.clear();
				adh_ApplicantFullname.clear();
				Adh_ModeOfPaymentAppTime.clear();
				ContractBillPayment_ContractNo.clear();
			}
			renewalOfAdvtContract();
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_RenewalOfRentContract method");
		}
	}

	//@ Test :20 Renewal of Advertisment contract checkilist verification
	@Test(enabled=false)
	public void adh_AdvtRenewalContractChkLstVerifctn() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			mCreateArtefactsFolder("ADH_");
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
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_RentRenewalContractChkLstVerifctn method");
		}
	}
	//@ Test :21 Renewal of Advertisment contract Scrutiny Process
	@Test(enabled=false)
	public void adh_AdvtrenwlContrctScrutinyProcess() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			/*	if(!mGetPropertyFromFile("wantToRejectAtChecklist").equalsIgnoreCase("No")){
				System.out.println("adh_Loipayment Skipped as it is rejected");		
			}
			else{*/

			if (!(chekindexes.contains(CurrentinvoCount))) {
				if (mGetPropertyFromFile("adh_LOIGenFinalDecisiondata").equalsIgnoreCase("no")) {
					scrutinyindexes.add(CurrentinvoCount);
					System.out.println("scrutinyindexes==>"+scrutinyindexes);
				}




				for(scrutinylevelcounter=1;scrutinylevelcounter<=Integer.parseInt(mGetPropertyFromFile("noOfScrutinyLevels"));scrutinylevelcounter++)
				{
					scrutinyGenericMethod.ScrutinyProcess();
				}
			}

			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_RentrenwlContrctScrutinyProcess method");
		}
	}
	//@ Test : 22 Renewal of Advertisment contract Printer Counter
	@Test(enabled=false)
	public void adh_AdvtrenwlContrctPrinterCounter() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			if (!(chekindexes.contains(CurrentinvoCount))) {
				rentRenewalPrinter();
			}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_RentrenwlContrctPrinterCounter method");
		}
	}

	//@ Test :23 Renewal of Advertisment contract Loi Payment
	public void adh_AdvtrenwlContrctLOIPayment() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			if (!(chekindexes.contains(CurrentinvoCount))) {
				if(!(scrutinyindexes.contains(CurrentinvoCount))) {
					rentRenewalLOIPayment();
				}}
			CommonUtilsAPI.rnlErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_RentrenwlContrctLOIPayment method");
		}
	}

	//@ Test :24 Renewal of Advertisment contract Bill Generation 
	@Test(enabled=false)
	public void adh_AdvtrewalcontractBillGeneration() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			if (!(chekindexes.contains(CurrentinvoCount))) {
				if(!(scrutinyindexes.contains(CurrentinvoCount))) {
					billGeneration();
				}}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_BillGeneration method");
		}
	}

	//@ Test :25 Renewal of Advertisment contract Bill Printing
	@Test(enabled=false)
	public void adh_AdvtrenewalcontractBillPrinting() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			if (!(chekindexes.contains(CurrentinvoCount))) {
				if(!(scrutinyindexes.contains(CurrentinvoCount))) {
					billPrinting();
				}}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_BillPrinting method");
		}
	}

	//@ Test :26 Renewal of Advertisment  Contract Bill Payment
	@Test(enabled=false)
	public void adh_AdvtrenewalContractBillPayment() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			if (!(chekindexes.contains(CurrentinvoCount))) {
				if(!(scrutinyindexes.contains(CurrentinvoCount))) {
					contractBillPayment();
				}
			}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_ContractBillPayment method");
		}
	}



	//@ Test :27 Renewal of Advertisment contract Approval Letter Printing
	@Test(enabled=false)
	public void adh_AdvtrenewalContractApprovalLetterPrinting() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();

			if (!(chekindexes.contains(CurrentinvoCount))) {
				if(!(scrutinyindexes.contains(CurrentinvoCount))) {

					ContractLetterPrinting();
					CommonUtilsAPI.adhErrorMsg.assertAll();
				}}}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_RentrenewalContractApprovalLetterPrinting method");
		}
	}

	//@ Test :28 Agency Contract generation Piyush 27/03/2017
	@Test(enabled=false)
	public void adh_AgencyContract() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			agencyContractGenration();
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in agencyContractGenration method");
		}
	}


	//@ Test :29 Agency Contract Bill generation Piyush 29/03/2017
	@Test(enabled=false)
	public void adh_AgencyContractBillGeneration() throws InterruptedException, IOException{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			billGeneration();
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_BillGeneration method");
		}
	}

	//@ Test :30 Agency Contract Bill Printing
	@Test(enabled=false)
	public void adh_AgencyContractBillPrinting() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			AgencybillPrinting();
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in rnl_BillPrinting method");
		}
	}
	//@ Test :31 Agency Contract Contract Bill Payment
	@Test(enabled=false)
	public void adh_AgencyContractContractBillPayment() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			contractBillPayment();
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_ContractBillPayment method");
		}
	}

	//@ Test :32Agency Contract Approval Note Printing
	@Test(enabled=false)
	public void adh_contractLetterPrinting() throws InterruptedException, IOException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.entrySet().clear();
			approvalLetterPrinting();
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_ApprovalLetterPrinting method");
		}
	}


	// @ Test :33 Inspection Entry Form
	@Test(enabled = false)
	public void adh_AdvtHoardingInspection() throws InterruptedException {

		try {
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.clear();
			mCreateArtefactsFolder("ADH_");
			String Inspection=mGetPropertyFromFile("adh_InpectionClearYesNoData");
			if (Inspection.equalsIgnoreCase("Clear")) {
				chekindexesinspction.add("Clear");
				clearchekindexes.add(222);
				SKIPSTATUS.add("Clear");
				System.out.println("clearchekindexes==>"+clearchekindexes);
				System.out.println(" Clear chekindexesinspction"+ chekindexesinspction);
			}else {

				SKIPSTATUS.add("NotClear");
				chekindexesinspction.add("NotClear");
				System.out.println("clearchekindexes==>"+jj);
				clearchekindexes.add(jj);
				jj++;
				System.out.println("Not Clear chekindexesinspction"+ chekindexesinspction);

			}

			System.out.println("chekindexesinspction"+ chekindexesinspction);
			System.out.println("clearchekindexes"+ clearchekindexes);
			addHoardingInspection();
			CommonUtilsAPI.adhErrorMsg.assertAll();
			System.out.println("SKIPSTATUS=>"+SKIPSTATUS);
		}
		catch (Exception e) {
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_InspectionEntry method");
		}
	}


	// @ Test :34 Inspection Letter Printing 
	@Test(enabled = false)
	public void adh_AdvtHoardingInspectionLetter() throws InterruptedException {

		try {
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.clear();
			mCreateArtefactsFolder("ADH_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));

			inspectionEntryLetter_Script();
			logOut();
			finalLogOut();
			mCloseBrowser();
			CommonUtilsAPI.adhErrorMsg.assertAll();
		} 
		catch (Exception e) {
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_InspectionEntry method");
		}
	}




	// @ Test :35 Show Cause Notice entry form
	@Test(enabled = false)
	public void adh_AdvtShowCauseNotice() throws InterruptedException {

		try {
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.clear();
			if (!chekindexesinspction.get(CurrentinvoCount).equalsIgnoreCase("clear")) {
				addShowCauseNotice();
			}

			/*if (!(chekindexes.contains(CurrentinvoCount))) {
				addShowCauseNotice();
			}*/

			CommonUtilsAPI.adhErrorMsg.assertAll();
		} 
		catch (Exception e) {
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_InspectionEntry method");
		}
	}


	// @ Test :36 Show Cause NoticeLetter Printing 
	@Test(enabled = false)
	public void adh_AdvtShowCauseNoticeLetter() throws InterruptedException {

		try {
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.clear();
			if (!chekindexesinspction.get(CurrentinvoCount).equalsIgnoreCase("clear"))  {
				mCreateArtefactsFolder("ADH_");
				mOpenBrowser(mGetPropertyFromFile("browserName"));
				mGetURL(mGetPropertyFromFile("url"));
				selectUlb();
				login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));
				showCauseNoticeLetter_Script();
				logOut();
				finalLogOut();
				mCloseBrowser();
			}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		} 
		catch (Exception e) {
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_InspectionEntry method");
		}
	}

	// @ Test :37 Hearing Entry Form
	@Test(enabled = false)
	public void adh_AdvtHearingEntry() throws InterruptedException {

		try {
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.clear();
			if (!chekindexesinspction.get(CurrentinvoCount).equalsIgnoreCase("clear")) {
				addHearingEntry();
			}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		} 
		catch (Exception e) {
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_InspectionEntry method");
		}
	}

	// @ Test :38 Hearing Entry Letter Printing :Running
	@Test(enabled = false)
	public void adh_AdvtHearingEntryLetter() throws InterruptedException {

		try {
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.clear();
			if (!SKIPSTATUS.get(CurrentinvoCount).equalsIgnoreCase("clear"))  {
				mCreateArtefactsFolder("ADH_");
				mOpenBrowser(mGetPropertyFromFile("browserName"));
				mGetURL(mGetPropertyFromFile("url"));
				selectUlb();
				login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));
				hearingLetter_Script();
				logOut();
				finalLogOut();
			}
			mCloseBrowser();


			CommonUtilsAPI.adhErrorMsg.assertAll();
		} 
		catch (Exception e) {
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_InspectionEntry method");
		}
	}


	// @ Test :39 Cancellation of Contract/ Agency
	@Test(enabled=false)
	public void adh_cancellationOfContractNAgency() throws InterruptedException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.clear();
			System.out.println("skipcanssus"+skipcanssus);
			if (mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")) {
				skipcanssus.add("Hearing Unsuccessful");
				SKIPSTATUS.add("Hearing Unsuccessful");
			}		
			System.out.println("skipcanssus"+skipcanssus);

			if((SKIPSTATUS.get(CurrentinvoCount).equalsIgnoreCase("Hearing Unsuccessful"))){
				mCreateArtefactsFolder("ADH_");

				mOpenBrowser(mGetPropertyFromFile("browserName"));
				mGetURL(mGetPropertyFromFile("url"));
				selectUlb();
				login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));
				////	if(!chekindexesinspction.get(CurrentinvoCount).equalsIgnoreCase("Hearing Successful")){


				cancellationOfContractNAgency_Script();
				kk++;
				//	}
				logOut();
				finalLogOut();
				mCloseBrowser();
			}else {
				CoAC_CancellationNumber.add("ss");

			}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_bookingOfHording method");
		}
	}


	// @ Test :40 Cancellation of Contract/ Agency Letter Printing
	@Test(enabled=false)
	public void adh_cancellationOfContractNAgencyLetter() throws InterruptedException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.clear();
			if(SKIPSTATUS.get(CurrentinvoCount).equalsIgnoreCase("Hearing Unsuccessful")){
				mCreateArtefactsFolder("ADH_");
				mOpenBrowser(mGetPropertyFromFile("browserName"));
				mGetURL(mGetPropertyFromFile("url"));
				selectUlb();
				login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));
				if(SKIPSTATUS.get(CurrentinvoCount).equalsIgnoreCase("Hearing Unsuccessful")){
					cancellationOfContractLetterPrinting_Script();
				}
				mCloseBrowser();
			}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_bookingOfHording method");
		}
	}




	// @ Test :41 Suspension of Contract/ Agency
	@Test(enabled=false)
	public void adh_suspensionOfContractNAgency() throws InterruptedException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.clear();
			System.out.println("chekindexesinspction"+chekindexesinspction);
			if(SKIPSTATUS.get(CurrentinvoCount).equalsIgnoreCase("Hearing Unsuccessful")){
				mCreateArtefactsFolder("ADH_"); 
				mOpenBrowser(mGetPropertyFromFile("browserName"));
				mGetURL(mGetPropertyFromFile("url"));
				selectUlb();
				login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));
				suspensionOfContractNAgency_Script();
				logOut();
				finalLogOut();
				mCloseBrowser();
			}else {
				SoAC_SuspensionNumber.add("sss");
			}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_bookingOfHording method");
		}
	} 

	// @ Test :42 Suspension of Contract/ Agency Letter Printing
	@Test(enabled=false)
	public void adh_suspensionOfContractNAgencyLetter() throws InterruptedException{

		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.clear();
			if(SKIPSTATUS.get(CurrentinvoCount).equalsIgnoreCase("Hearing Unsuccessful")){
				mCreateArtefactsFolder("ADH_");
				mOpenBrowser(mGetPropertyFromFile("browserName"));
				mGetURL(mGetPropertyFromFile("url"));
				selectUlb();
				login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));
				suspensionOfContractLetterPrinting_Script();
				logOut();
				finalLogOut();
				mCloseBrowser();
			}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_bookingOfHording method");
		}
	}



	// @ Test :43 Intimation for Removal of Advertisment 
	@Test(enabled = false)
	public void adh_intimationforRemovalofAdvertisment() throws InterruptedException {

		try {
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			myClassName = thisClassName;
			thisMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.clear();
			if(chekindexesinspction.get(CurrentinvoCount).equalsIgnoreCase("Hearing Unsuccessful")){
				mCreateArtefactsFolder("ADH_");
				mOpenBrowser(mGetPropertyFromFile("browserName"));
				mGetURL(mGetPropertyFromFile("url"));
				selectUlb();
				login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));
				intimationforRemovalofAdvertisment_Script();
				logOut();
				finalLogOut();
				mCloseBrowser();
			}
			CommonUtilsAPI.adhErrorMsg.assertAll();
		} 
		catch (Exception e) {
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_InspectionEntry method");
		}
	}

	// @ Test :44 Challan Verification Method
	@Test(enabled = false)
	public void adh_challanVerification() throws InterruptedException {

		try {

			ER.datareader();
			AdvertisingAndHoardingCustomErrorMessages.adh_m_errors.clear();
			challanVerification("byBankOrULB", mGetPropertyFromFile("challanVer_ServiceName"), "chlanAtULBTypeOfPayModeId", "chlanAtULbNameOfBankId","rti_chlanVerftnAccNo","rti_chlanVerftnCheDDNo","rti_chlanVerftnDate");
			chllanVerReqForServices=false;

		} 
		catch (Exception e) {
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in adh_InspectionEntry method");
		}
	}



	//Booking of Hoarding Mehod
	public void bookingOfHording() throws InterruptedException{

		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));
		bookingOfHording_Script();
		bookingOfHordingProceed();
		logOut();
		finalLogOut();
		isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVreBookingOfHoardingServiceName"));
		String ModeofPayment=mGetPropertyFromFile("chlanAtULBTypeOfPayModeId");
		Adh_ModeOfPaymentAppTime.add(ModeofPayment);
		if(mGetPropertyFromFile("chlanAtULBTypeOfPayModeId").equalsIgnoreCase("Cash")){
			adh_ApplicationTimePaymentReceipt_Cash_assertion();

		}
		else{
			//adh_ApplicationTimePaymentReceipt_Cheque_assertion();

		}
		mCloseBrowser();

	}


	//Checklist Verification common for all
	public void checkListVerification() throws InterruptedException
	{
		try{
			mCustomWait(2000);
			String TypeOfKey=mGetPropertyFromFile("ClvAppApprovedata");
			System.out.println(TypeOfKey);
			ChecklistVerification(mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	// Removal of Advertisement Intimation Script
	public void intimationforRemovalofAdvertisment_Script(){

		try{
			mCustomWait(1000);;
			//Navigating to Intimation Letter form
			mNavigation("adh_intiLetterAdvtNHoardingLinkid", "adh_intiLetterTransactionLinkid", "adh_intiLetterintiLetteraringEntryLinkid");
			mCustomWait(500);
			//Capturing ULB name
			ULBName=mGetPropertyFromFile("municipality");
			System.out.println("ulb is : "+ULBName); 
			mCustomWait(500);

			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				mSendKeys("id", mGetPropertyFromFile("adh_intiLetterContractNoId"),adhContractNo.get(CurrentinvoCount));
			}
			else
			{
				mSendKeys("id", mGetPropertyFromFile("adh_intiLetterContractNoId"),mGetPropertyFromFile("ContractNo"));
			}
			mCustomWait(500);
			//Click on Search button
			mWaitForVisible("css", mGetPropertyFromFile("adh_intiLetterContractnoSeacrbtnId"));
			mClick("css", mGetPropertyFromFile("adh_intiLetterContractnoSeacrbtnId"));
			mCustomWait(500);
			//Selecting Check Box
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_intiLetterContractnoChkBoxnId"));
			mClick("xpath", mGetPropertyFromFile("adh_intiLetterContractnoChkBoxnId"));
			mCustomWait(500);
			//Click on Generate Button
			mWaitForVisible("id", mGetPropertyFromFile("adh_intiLetterGeneratebtnId"));
			mClick("id", mGetPropertyFromFile("adh_intiLetterGeneratebtnId"));

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in cancellationOfContractLetterPrinting_Script");
		}
	}



	public void	suspensionOfContractNAgency_Script(){
		try{
			//Navigating from Advertisment and Hoarding to Transactions to Suspension of Contract
			mCustomWait(1000);;
			mNavigation("adh_SoAAdvtNHoardingLinkid", "adh_SoATransactionLinkid", "adh_SoACancellationOfContractLinkid");

			//capturing ULB Name 
			ULBName=mGetPropertyFromFile("municipality");
			System.out.println("ulb is : "+ULBName); 

			//Click and wait on Add Suspension Button 
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_SoAAddSuspensionbtnid"));
			mClick("xpath", mGetPropertyFromFile("adh_SoAAddSuspensionbtnid"));

			//Click and wait on Hearing Order Button 
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_SoAsearchHearingOrderbtnid"));
			mClick("xpath", mGetPropertyFromFile("adh_SoAsearchHearingOrderbtnid"));

			mWaitForVisible("id", mGetPropertyFromFile("adh_SoAsearchHearingNoid"));
			//Search Criteria
			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				/*mSendKeys("id", mGetPropertyFromFile("adh_SoAsearchHearingNoid"),adhHearingOrdeNo.get(CurrentinvoCount));*/
				mSendKeys("id", mGetPropertyFromFile("adh_SoAsearchHearingNoid"),adhHearingOrdeNo_UNS.get(hearingorder));
				hearingorder++;

			}
			else
			{
				addSuspensionProcessSearchCriteriaGeneration();
			}

			//Search and wait on Search Hearing No 
			mWaitForVisible("css", mGetPropertyFromFile("adh_SoAHearingNosearchinsearchCriteriabtnid"));
			mClick("css", mGetPropertyFromFile("adh_SoAHearingNosearchinsearchCriteriabtnid"));


			//Select and wait on to select radio button id in grid 
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_SoAHearingNoradiobtnid"));
			mClick("xpath", mGetPropertyFromFile("adh_SoAHearingNoradiobtnid"));

			//Submit and wait
			mWaitForVisible("css", mGetPropertyFromFile("adh_SoAHearingNoSubmitbtnid"));
			mClick("css", mGetPropertyFromFile("adh_SoAHearingNoSubmitbtnid"));


			mWaitForVisible("xpath", mGetPropertyFromFile("adh_SoAReadHearingOrderNoid"));
			//Capturing Hearing Order No 
			String HearingOrderNo=mGetText("id", mGetPropertyFromFile("adh_SoAReadHearingOrderNoid"), "value");
			System.out.println("Hearing Order No is"+HearingOrderNo);
			SoAC_HearingOrderNo.add(HearingOrderNo);

			//Capturing Hearing Order Date 
			String HearingOrderDate=mGetText("id", mGetPropertyFromFile("adh_SoAReadHearingOrderDateid"), "value");
			System.out.println("Hearing Order Date is"+HearingOrderDate);
			SoAC_HearingOrderDate.add(HearingOrderDate);

			//Capturing Contract No
			String ContractNo=mGetText("id", mGetPropertyFromFile("adh_SoAReadContractNoid"), "value");
			System.out.println("Contract No is"+ContractNo);
			SoAC_contractNo.add(ContractNo);

			//Capturing Application No
			String ApplicationNo=mGetText("id", mGetPropertyFromFile("adh_SoAReadAgencyorAppliNoNoid"), "value");
			System.out.println("Application No is"+ApplicationNo);
			SoAC_AgencyorApplicationNo.add(ApplicationNo);

			//Capturing Applicant Name
			String ApplicantName=mGetText("id", mGetPropertyFromFile("adh_SoAReadAgencyorAppliNameid"), "value");
			System.out.println("Applicant Name is"+ApplicantName);
			SoAC_AgencyorApplicantName.add(ApplicantName);

			//Sending Suspension Date
			mDateSelect("id", mGetPropertyFromFile("adh_SoASuspensionDateid"), mGetPropertyFromFile("adh_SoASuspensionDateData"));
			String CancellationDate=mGetText("id", mGetPropertyFromFile("adh_SoASuspensionDateid"), "value");
			System.out.println("Suspension Date is"+CancellationDate);
			SoAC_SuspensionDate.add(CancellationDate);

			//Capturing Suspended By
			String CancelledBy=mGetText("id", mGetPropertyFromFile("adh_ReadSoASuspensionByid"), "value");
			System.out.println("Suspended By is"+CancelledBy);
			SoAC_SuspendedBy.add(CancelledBy);

			//Sending Suspension Decision
			mSelectDropDown("id", mGetPropertyFromFile("adh_SoASuspensionDecisionid"), mGetPropertyFromFile("adh_SoASuspensionDecisionData"));
			String  Cancellationdesicion=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_CoACancellationDateid"));
			System.out.println("Suspension Decision is"+Cancellationdesicion);
			SoAC_Suspensiondecision.add(Cancellationdesicion);

			//Sending Suspension from Date
			mDateSelect("id", mGetPropertyFromFile("adh_SoASuspensionfromDateid"), mGetPropertyFromFile("adh_SoASuspensionFromDateData"));
			String SuspensionFromDate=mGetText("id", mGetPropertyFromFile("adh_SoASuspensionfromDateid"), "value");
			System.out.println("Cancellation Date is"+SuspensionFromDate);
			SoAC_SuspensionFromDate.add(SuspensionFromDate);

			//Sending Suspension to Date
			mDateSelect("id", mGetPropertyFromFile("adh_SoASuspensiontoDateid"), mGetPropertyFromFile("adh_SoASuspensionToDateData"));
			String SuspensionToDate=mGetText("id", mGetPropertyFromFile("adh_SoASuspensiontoDateid"), "value");
			System.out.println("Cancellation Date is"+SuspensionToDate);
			SoAC_SuspensionToDate.add(SuspensionToDate);

			//Sending Suspension Remarks
			mSendKeys("id", mGetPropertyFromFile("adh_ReadSoASuspensionRemarksid"), mGetPropertyFromFile("adh_ReadSoASuspensionRemarksData"));
			String CancellationRemarks=mGetText("id", mGetPropertyFromFile("adh_ReadSoASuspensionRemarksid"), "value");
			System.out.println("Suspension Remarks is"+CancellationRemarks);
			SoAC_SuspensionRemarks.add(CancellationRemarks);

			//Submit and wait
			mWaitForVisible("css", mGetPropertyFromFile("adh_SoASuspensionformSubmitid"));
			mClick("css", mGetPropertyFromFile("adh_SoASuspensionformSubmitid"));

			//Submit and wait for proceed msg and button
			mWaitForVisible("css", mGetPropertyFromFile("adh_SoASuspensionProceedBtntid"));

			//Capturing Suspension No
			String SuspensionNo =mGetText("css",mGetPropertyFromFile("adh_SoASuspensionAssertMsgtid"));
			System.out.println("POP UP msg is::::"+SuspensionNo);

			//String Suspensionnumber= SuspensionNo.replaceAll("[^0-9]", "");
			String Suspensionnumber1[]= SuspensionNo.split(" : ");
			String Suspensionnumber=Suspensionnumber1[1].trim();
			SoAC_SuspensionNumber.add(Suspensionnumber);
			System.out.println("Suspension Number in proceed::::"+Suspensionnumber);
			System.out.println("Suspension Number in proceed Arraylist::::"+SoAC_SuspensionNumber);
			mTakeScreenShot();
			mWaitForVisible("id", mGetPropertyFromFile("adh_SoASuspensionProceedBtntid"));
			mClick("css", mGetPropertyFromFile("adh_SoASuspensionProceedBtntid"));

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in suspensionOfContractNAgency_Script");
		}
	}


	// Suspension Of Agency Contract Letter Printing
	public void suspensionOfContractLetterPrinting_Script(){

		try{
			mCustomWait(1000);;
			mNavigation("adh_SoAAdvtNHoardingLinkid", "adh_SoATransactionLinkid", "adh_SoACancellationOfContractLinkid");

			//Capturing ULB Name
			ULBName=mGetPropertyFromFile("municipality");
			System.out.println("ulb is : "+ULBName); 

			//Search Criteria
			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				mSendKeys("id", mGetPropertyFromFile("adh_SoALetterSuspensionNoid"),SoAC_SuspensionNumber.get(CurrentinvoCount));
			}
			else
			{
				addSuspensionProcessSearchCriteriaLetterGeneration();
			}

			//Click on Search Button
			mWaitForVisible("css", mGetPropertyFromFile("adh_SoACancellationNoSearchbtnid"));
			mClick("css", mGetPropertyFromFile("adh_SoACancellationNoSearchbtnid"));

			// Click on Print Button
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_SoACancellationLetterPrintbtnid"));
			mClick("xpath", mGetPropertyFromFile("adh_SoACancellationLetterPrintbtnid"));
			mCustomWait(500);

			// Downloading suspension Letter
			mChallanPDFReader();
			mCustomWait(500);
			// Downloading suspension Letter assertion Metthod
			//adh_Suspension_letter_assertion();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in cancellationOfContractLetterPrinting_Script");
		}
	}

	//Suspension Letter Assertion Method
	public void adh_Suspension_letter_assertion(){
		try{
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				ADH_Suspension_Letter_Pdf(pdfoutput);
				mAssert(Pdf_SoAC_AgencyorApplicantName,SoAC_AgencyorApplicantName, "Agency/Applicant Name in Suspension Letter  dose not match with Actual ::>>>>>"+ Pdf_SoAC_AgencyorApplicantName.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+SoAC_AgencyorApplicantName.get(CurrentinvoCount));
				mAssert(Pdf_SoAC_HearingOrderNo, SoAC_HearingOrderNo, "Hearing Order No in Suspension Letter  dose not match with Actual ::>>>>>"+ Pdf_SoAC_HearingOrderNo.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+SoAC_HearingOrderNo.get(CurrentinvoCount));
				mAssert(Pdf_SoAC_HearingOrderDate, SoAC_HearingOrderDate, "Hearing Order Date in Suspension Letter  dose not match with Actual ::>>>>>"+ Pdf_SoAC_HearingOrderDate.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+SoAC_HearingOrderDate.get(CurrentinvoCount));
				mAssert(Pdf_SoAC_SuspensionToDate,SoAC_SuspensionToDate, "Suspension To Date in Suspension Letter  dose not match with Actual ::>>>>>"+ Pdf_SoAC_SuspensionToDate.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+SoAC_SuspensionToDate.get(CurrentinvoCount));
				mAssert(Pdf_SoAC_SuspensionFromDate, SoAC_SuspensionFromDate, "Suspension To Date in Suspension Letter  dose not match with Actual ::>>>>>"+ Pdf_SoAC_SuspensionFromDate.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+SoAC_SuspensionFromDate.get(CurrentinvoCount));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in adh_Suspension_letter_assertion");
		}
	}


	// Cancellation Of Agency Contract Script
	public void cancellationOfContractNAgency_Script(){
		try{
			//Navigating from Rent & Lease to Transactions to Tenant Contract
			mCustomWait(1000);;
			mNavigation("adh_CoAAdvtNHoardingLinkid", "adh_CoATransactionLinkid", "adh_CoACancellationOfContractLinkid");

			ULBName=mGetPropertyFromFile("municipality");
			System.out.println("ulb is : "+ULBName); 

			//Click and wait on Add Cancellation Button 
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_CoAAddCancellationbtnid"));
			mClick("xpath", mGetPropertyFromFile("adh_CoAAddCancellationbtnid"));

			//Click and wait on Hearing Order Button 
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_CoAsearchHearingOrderbtnid"));
			mClick("xpath", mGetPropertyFromFile("adh_CoAsearchHearingOrderbtnid"));

			//Serach Criteria
			mWaitForVisible("id", mGetPropertyFromFile("adh_CoAsearchHearingNoid"));
			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				/*mSendKeys("id", mGetPropertyFromFile("adh_CoAsearchHearingNoid"),adhHearingOrdeNo.get(hearingorder));*/
				mSendKeys("id", mGetPropertyFromFile("adh_CoAsearchHearingNoid"),adhHearingOrdeNo_UNS.get(hearingorder)); 
				hearingorder++;
				//mSendKeys("id", mGetPropertyFromFile("adh_CoAsearchHearingNoid"),chekindexesinspction.get(CurrentinvoCount));
			}
			else
			{
				addCancellationProcessSearchCriteriaGeneration();
			}

			//Search and wait on Search Hearing No 
			mWaitForVisible("css", mGetPropertyFromFile("adh_CoAHearingNosearchinsearchCriteriabtnid"));
			mClick("css", mGetPropertyFromFile("adh_CoAHearingNosearchinsearchCriteriabtnid"));


			//Select and wait on to select radio button id in grid 
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_CoAHearingNoradiobtnid"));
			mClick("xpath", mGetPropertyFromFile("adh_CoAHearingNoradiobtnid"));

			//Submit and wait
			mWaitForVisible("css", mGetPropertyFromFile("adh_CoAHearingNoSubmitbtnid"));
			mClick("css", mGetPropertyFromFile("adh_CoAHearingNoSubmitbtnid"));


			mWaitForVisible("xpath", mGetPropertyFromFile("adh_CoAsearchHearingOrderbtnid"));
			//Capturing Hearing Order No 
			String HearingOrderNo=mGetText("id", mGetPropertyFromFile("adh_CoAReadHearingOrderNoid"), "value");
			System.out.println("Hearing Order No is"+HearingOrderNo);
			CoAC_HearingOrderNo.add(HearingOrderNo);


			//Capturing Hearing Order Date 
			String HearingOrderDate=mGetText("id", mGetPropertyFromFile("adh_CoAReadHearingOrderDateid"), "value");
			System.out.println("Hearing Order Date is"+HearingOrderDate);
			CoAC_HearingOrderDate.add(HearingOrderDate);



			//Capturing Contract No
			String ContractNo=mGetText("id", mGetPropertyFromFile("adh_CoAReadContractNoid"), "value");
			System.out.println("Contract No is"+ContractNo);
			CoAC_contractNo.add(ContractNo);



			//Capturing Application No
			String ApplicationNo=mGetText("id", mGetPropertyFromFile("adh_CoAReadAgencyorAppliNoNoid"), "value");
			System.out.println("Application No is"+ApplicationNo);
			CoAC_AgencyorApplicationNo.add(ApplicationNo);




			//Capturing Applicant Name
			String ApplicantName=mGetText("id", mGetPropertyFromFile("adh_CoAReadAgencyorAppliNameid"), "value");
			System.out.println("Applicant Name is"+ApplicantName);
			CoAC_AgencyorApplicantName.add(ApplicantName);


			//Capturing Cancellation Type
			String CancellationType=mGetText("id", mGetPropertyFromFile("adh_CoAReadCancellationTypeid"), "value");
			System.out.println("Cancellation Type is"+CancellationType);
			CoAC_CancellationType.add(CancellationType);


			//Sending Cancellation Date
			mDateSelect("id", mGetPropertyFromFile("adh_CoACancellationDateid"), mGetPropertyFromFile("adh_CoACancellationDateData"));
			String CancellationDate=mGetText("id", mGetPropertyFromFile("adh_CoACancellationDateid"), "value");
			System.out.println("Cancellation Date is"+CancellationDate);
			CoAC_CancellationDate.add(CancellationDate);


			//Sending Cancellation Decision
			mSelectDropDown("id", mGetPropertyFromFile("adh_CoACancellationDecisionid"), mGetPropertyFromFile("adh_CoACancellationDecisionData"));
			String  Cancellationdesicion=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_CoACancellationDecisionid"));
			System.out.println("Cancellation Decision is"+Cancellationdesicion);
			CoAC_Cancellationdecision.add(Cancellationdesicion);

			//Capturing Cancelled By
			String CancelledBy=mGetText("id", mGetPropertyFromFile("adh_ReadCoACancelledByid"), "value");
			System.out.println("Cancelled By is"+CancelledBy);
			CoAC_CanceledBy.add(CancelledBy);



			//Sending Cancellation Remarks
			mSendKeys("id", mGetPropertyFromFile("adh_ReadCoACancellationRemarksid"), mGetPropertyFromFile("adh_ReadCoACancellationRemarksData"));
			String CancellationRemarks=mGetText("id", mGetPropertyFromFile("adh_ReadCoACancellationRemarksid"), "value");
			System.out.println("Cancellation Date is"+CancellationRemarks);
			CoAC_CancellationRemarks.add(CancellationRemarks);

			//Submit and wait
			mWaitForVisible("css", mGetPropertyFromFile("adh_CoACancellationformSubmitid"));
			mClick("css", mGetPropertyFromFile("adh_CoACancellationformSubmitid"));

			//Submit and wait for proceed msg and button
			mWaitForVisible("css", mGetPropertyFromFile("adh_CoACancellationProceedBtntid"));

			// Capturing Cancellation No
			String CancellationNo =mGetText("css",mGetPropertyFromFile("adh_CoACancellationAssertMsgtid"));
			System.out.println("POP UP msg at Booking of Hoarding is::::"+CancellationNo);

			String Cancellationnumber1[]= CancellationNo.split(" : ");			
			String Cancellationnumber=Cancellationnumber1[1];
			CoAC_CancellationNumber.add(Cancellationnumber);


			System.out.println("Cancellation Number in proceed::::"+CancellationNo);
			System.out.println("Cancellation Number in proceed Arraylist::::"+CoAC_CancellationNumber);
			mTakeScreenShot();
			mWaitForVisible("id", mGetPropertyFromFile("adh_CoACancellationProceedBtntid"));
			mClick("css", mGetPropertyFromFile("adh_CoACancellationProceedBtntid"));

			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){



				mAssert(CoAC_HearingOrderNo.get(kk), He_HearingeNo.get(CurrentinvoCount), "Hearing Order No is in Cancellation of Agency Contract dose not match Actual::"+CoAC_HearingOrderNo.get(kk)+"in show cause notice Form,Expected::"+He_HearingeNo.get(CurrentinvoCount));



				mAssert(CoAC_HearingOrderDate.get(kk), He_HearingDate.get(CurrentinvoCount), "Hearing Date is in Cancellation of Agency Contract dose not match Actual::"+CoAC_HearingOrderDate.get(kk)+"in show cause notice Form,Expected::"+He_HearingDate.get(CurrentinvoCount));

				mAssert(CoAC_contractNo.get(kk), He_ContractNo.get(CurrentinvoCount), "Contract No is in Cancellation of Agency Contract dose not match Actual::"+CoAC_contractNo.get(kk)+"in show cause notice Form,Expected::"+He_ContractNo.get(CurrentinvoCount));


				mAssert(CoAC_AgencyorApplicationNo.get(kk), He_AgencyorApplicationNo.get(CurrentinvoCount), "Application No is in Cancellation of Agency Contract dose not match Actual::"+CoAC_AgencyorApplicationNo.get(kk)+"in show cause notice Form,Expected::"+He_AgencyorApplicationNo.get(CurrentinvoCount));

				mAssert(CoAC_AgencyorApplicantName.get(kk), He_AgencyorApplicantName.get(CurrentinvoCount), "Applicant Name is in Cancellation of Agency Contract dose not match Actual::"+CoAC_AgencyorApplicantName.get(kk)+"in show cause notice Form,Expected::"+He_AgencyorApplicantName.get(CurrentinvoCount));

				mAssert(CoAC_CancellationType.get(kk), He_NoticeType.get(CurrentinvoCount), "Cancellation Type is in Cancellation of Agency Contract dose not match Actual::"+CoAC_CancellationType.get(kk)+"in show cause notice Form,Expected::"+He_NoticeType.get(CurrentinvoCount));

			}




		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in cancellationOfContractNAgency_Script");
		}
	}


	// Cancellation Of Agency Contract letter printing Script 
	public void cancellationOfContractLetterPrinting_Script(){

		try{
			mCustomWait(1000);;
			mNavigation("adh_CoAAdvtNHoardingLinkid", "adh_CoATransactionLinkid", "adh_CoACancellationOfContractLinkid");

			//Capturing ULB Name
			ULBName=mGetPropertyFromFile("municipality");
			System.out.println("ulb is : "+ULBName); 

			//Search Criteria
			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				mSendKeys("id", mGetPropertyFromFile("adh_CoALetterCancellationNoid"),CoAC_CancellationNumber.get(CurrentinvoCount));

				//adhHearingOrdeNo_UNS.get(hearingorder)
			}
			else
			{
				addCancellationSearchCriteriaLetterGeneration();
			}

			//Click on Search Button
			mWaitForVisible("css", mGetPropertyFromFile("adh_CoACancellationNoSearchbtnid"));
			mClick("css", mGetPropertyFromFile("adh_CoACancellationNoSearchbtnid"));

			// Click on Cancellation Print Button
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_CoACancellationLetterPrintbtnid"));
			mClick("xpath", mGetPropertyFromFile("adh_CoACancellationLetterPrintbtnid"));
			mCustomWait(500);

			// Down loading Letter
			mChallanPDFReader();
			mCustomWait(500);
			// Cancellation of Agency Contract Assertion letter method
			adh_Cancellation_letter_assertion();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in cancellationOfContractLetterPrinting_Script");
		}
	}



	public void adh_Cancellation_letter_assertion(){
		try{
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				ADH_Cancellation_Letter_Pdf(pdfoutput);

			}
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in adh_Cancellation_letter_assertion");
		}
	}




	//Printer counter
	public void printerCounter() throws InterruptedException, IOException
	{
		mCreateArtefactsFolder("ADH_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		departmentLogin(mGetPropertyFromFile("cfc_RNLprnGridUserName"),mGetPropertyFromFile("cfc_RNLprnGridPassword"));
		cfcPrinterGrid();
		logOut();
		finalLogOut();
		mCloseBrowser();
	}



	//Agency Bill Generation Method
	public void AgencybillGeneration() throws InterruptedException, IOException
	{

		mCreateArtefactsFolder("ADH_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));
		Agency_billGeneration_Script();
		logOut();
		finalLogOut();
		mCloseBrowser();
	}


	//Bill Generation Method
	public void billGeneration() throws InterruptedException, IOException
	{
		mCreateArtefactsFolder("ADH_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));
		billGeneration_Script();
		logOut();
		finalLogOut();
		mCloseBrowser();
	}

	//Bill Printing Method
	public void billPrinting() throws InterruptedException, IOException
	{

		mCreateArtefactsFolder("ADH_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));
		billPrinting_Script();
		mChallanPDFReader();
		api.PdfPatterns.ADH_BillDues_Letter_Pdf(pdfoutput);
		logOut();
		finalLogOut();
		mCloseBrowser();
	}


	//Agency Contract Bill Printing
	public void AgencybillPrinting() throws InterruptedException, IOException
	{
		//beforebillPrinting();
		mCreateArtefactsFolder("ADH_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));
		Agency_Contract_billPrinting_Script();
		mChallanPDFReader();
		api.PdfPatterns.ADH_BillDues_Letter_Pdf(pdfoutput);
		logOut();
		finalLogOut();
		mCloseBrowser();
	}


	//Adverisment Contract Payment Schedule
	public void contractBillPayment() throws InterruptedException
	{
		mCustomWait(1000);
		mCreateArtefactsFolder("ADH_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));
		contractBillPayment_Script();
		logOut();
		finalLogOut();
		isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVreBookingOfHoardingServiceName"));
		if(mGetPropertyFromFile("chlanAtULBTypeOfPayModeId").equalsIgnoreCase("Cash")){
			api.PdfPatterns.ADH_Cash_ContractBillPayment_Receipt_Letter_Pdf(pdfoutput);
		}
		else{

		}
		mCloseBrowser();
	}



	//Renewal of Adverisment Contract
	public void renewalOfAdvtContract() throws InterruptedException
	{
		mCreateArtefactsFolder("ADH_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		departmentLogin(mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));
		renewalOfRentContract_Script();
		logOut();
		finalLogOut();
		isChallanVerftnRequired(mGetPropertyFromFile("cfc_chalanverFrRenewalOfRentContract"));
		if(mGetPropertyFromFile("chlanAtULBTypeOfPayModeId").equalsIgnoreCase("Cash")){
			adh_ApplicationTimePaymentReceipt_Cash_assertion();
		}
		else
		{	
			adh_ApplicationTimePaymentReceipt_Cheque_assertion();
		}
		mCloseBrowser();
	}




	//Method to print application after renewal of Adverisment contract
	public void rentRenewalPrinter() throws InterruptedException
	{		
		mCreateArtefactsFolder("ADH_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		departmentLogin(mGetPropertyFromFile("cfc_ADHprnGridUserName"),mGetPropertyFromFile("cfc_ADHprnGridPassword"));
		cfcPrinterGrid();
		adh_Acceptance_letter_assertion();
		mCustomWait(1000);
		logOut();
		finalLogOut();
		mCloseBrowser();
	}

	//LOI Payment for Adverisment renewal contract
	public void rentRenewalLOIPayment() throws InterruptedException, IOException
	{
		mCustomWait(2000);
		mCreateArtefactsFolder("ADH_");
		BOE_LOI_Collection = true;
		loiPayment(mGetPropertyFromFile("adh_LoiPaymentModuleNameData"), mGetPropertyFromFile("cfc_chalanverFrRenewalOfRentContract"));
	}



	//method for Bill Generation
	public void billGeneration_Script()
	{
		try
		{
			//Navigating link from Rent and lease to Transaction to Bill Generation
			mCustomWait(1000);
			mNavigation(mGetPropertyFromFile("adh_BgAdv&HordingLinkid"), mGetPropertyFromFile("adh_BgTransactionLinkid"), mGetPropertyFromFile("adh_BgBillingLinkid"),mGetPropertyFromFile("adh_BgBillingGenerationLinkid"));

			//Sending Contract Number
			mWaitForVisible("id", mGetPropertyFromFile("adh_BgAgencyorApplicaNumInputid"));
			mCustomWait(1000);
			mTakeScreenShot();
			if(Flag==0){
				billGenerationSearchCriria();
			}
			else
			{
				if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent")) 
				{
					mSendKeys("id", mGetPropertyFromFile("adh_BgContractNumInputid"),adhContractNo.get(CurrentinvoCount));
				}
				else
				{
					mSendKeys("id", mGetPropertyFromFile("adh_BgContractNumInputid"), mGetPropertyFromFile("AgencyContractNo"));
				}

			}

			//Searching application
			mWaitForVisible("id", mGetPropertyFromFile("adh_BgSearchApplicationid"));
			mClick("id", mGetPropertyFromFile("adh_BgSearchApplicationid"));
			mTakeScreenShot();
			//Sending Bill Date
			mDateSelect("id",mGetPropertyFromFile("adh_BgBillDateOfApplicationid"),mGetPropertyFromFile("adh_BgBillDateOfApplicationData"));
			//mTab("id", mGetPropertyFromFile("adh_BgBillDateOfApplicationid"));
			mCustomWait(2000);

			//Sending Remarks 
			mWaitForVisible("id", mGetPropertyFromFile("adh_BgRemarksOnAppctnid"));
			mSendKeys("id", mGetPropertyFromFile("adh_BgRemarksOnAppctnid"), mGetPropertyFromFile("adh_BgRemarksOnAppctnData"));

			ClubbedUtils.rnl_mDynamicReadbillgenGrid(mGetPropertyFromFile("adh_BgBillGenTableNameid"));

			//checkbox to select application
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("adh_BgChkAppSelctnid"));
			mTakeScreenShot();

			//Bill generate Button
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_BgBillGenerateBtnid"));
			mTakeScreenShot();
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("adh_BgBillGenerateBtnid"));
			mTakeScreenShot();
			//Pop up 
			mWaitForVisible("css", mGetPropertyFromFile("adh_BgBillGenClosePopUpid"));
			mCustomWait(1000);
			String msgAtBillGen = mGetText("css", mGetPropertyFromFile("adh_BgBillGenAssertMsgid"));	
			mCustomWait(1000);
			mAssert(msgAtBillGen, mGetPropertyFromFile("adh_BgBillGenAssertMsgData"),"Msg at Bill Generation Pop up. Actual :"+msgAtBillGen+" Expected :"+mGetPropertyFromFile("adh_BgBillGenAssertMsgData"));
			System.out.println(msgAtBillGen);

			String splitMsg = msgAtBillGen.replaceAll("[^0-9]+", " ");
			String[] splitMsg1=splitMsg.trim().split(" ");		   
			System.out.println("msg1 is = "+splitMsg1[0]); 
			System.out.println("msg2 is = "+splitMsg1[1]);
			mTakeScreenShot();
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("adh_BgBillGenClosePopUpid"));
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
				mClick("id", mGetPropertyFromFile("adh_BgShowErrorBtnid"));				
				mWaitForVisible("css", mGetPropertyFromFile("adh_BgShowErrorBackid"));
				mCustomWait(2000);
				mTakeScreenShot();
				ClubbedUtils.rnl_mDynamicReadShowErrorGrid(mGetPropertyFromFile("adh_BgShowErrorTableid"),num);					
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


	//method for Bill Generation
	public void Agency_billGeneration_Script()
	{
		try
		{
			//Navigating link from Rent and lease to Transaction to Bill Generation
			mCustomWait(1000);
			mNavigation(mGetPropertyFromFile("adh_BgAdv&HordingLinkid"), mGetPropertyFromFile("adh_BgTransactionLinkid"), mGetPropertyFromFile("adh_BgBillingLinkid"),mGetPropertyFromFile("adh_BgBillingGenerationLinkid"));

			//Sending Contract Number
			mWaitForVisible("id", mGetPropertyFromFile("adh_BgAgencyorApplicaNumInputid"));
			mCustomWait(1000);

			//Search Criteria
			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent")) 
			{
				mSendKeys("id", mGetPropertyFromFile("adh_BgContractNumInputid"),adhContractNo.get(CurrentinvoCount));
			}
			else
			{
				mSendKeys("id", mGetPropertyFromFile("adh_BgContractNumInputid"), mGetPropertyFromFile("AgencyContractNo"));
			}

			//Searching application
			mWaitForVisible("id", mGetPropertyFromFile("adh_BgSearchApplicationid"));
			mClick("id", mGetPropertyFromFile("adh_BgSearchApplicationid"));

			//Sending Bill Date
			mDateSelect("id",mGetPropertyFromFile("adh_BgBillDateOfApplicationid"),mGetPropertyFromFile("adh_BgBillDateOfApplicationData"));
			//mTab("id", mGetPropertyFromFile("adh_BgBillDateOfApplicationid"));
			mCustomWait(2000);

			//Sending Remarks 
			mWaitForVisible("id", mGetPropertyFromFile("adh_BgRemarksOnAppctnid"));
			mSendKeys("id", mGetPropertyFromFile("adh_BgRemarksOnAppctnid"), mGetPropertyFromFile("adh_BgRemarksOnAppctnData"));

			ClubbedUtils.rnl_mDynamicReadbillgenGrid(mGetPropertyFromFile("adh_BgBillGenTableNameid"));

			//checkbox to select application
			mCustomWait(1000);
			String checkbox = mStringManipulation(mGetPropertyFromFile("adh_BgChkAppSelctnid"),mGetPropertyFromFile("applicationNo"));
			mClick("id", checkbox);


			//Bill generate Button
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_BgBillGenerateBtnid"));
			mTakeScreenShot();
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("adh_BgBillGenerateBtnid"));

			//Pop up 
			mWaitForVisible("css", mGetPropertyFromFile("adh_BgBillGenClosePopUpid"));
			mCustomWait(1000);
			String msgAtBillGen = mGetText("css", mGetPropertyFromFile("adh_BgBillGenAssertMsgid"));	
			mCustomWait(1000);
			mAssert(msgAtBillGen, mGetPropertyFromFile("adh_BgBillGenAssertMsgData"),"Msg at Bill Generation Pop up. Actual :"+msgAtBillGen+" Expected :"+mGetPropertyFromFile("adh_BgBillGenAssertMsgData"));
			System.out.println(msgAtBillGen);

			String splitMsg = msgAtBillGen.replaceAll("[^0-9]+", " ");
			String[] splitMsg1=splitMsg.trim().split(" ");		   
			System.out.println("msg1 is = "+splitMsg1[0]); 
			System.out.println("msg2 is = "+splitMsg1[1]);

			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("adh_BgBillGenClosePopUpid"));
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
				mClick("id", mGetPropertyFromFile("adh_BgShowErrorBtnid"));				
				mWaitForVisible("css", mGetPropertyFromFile("adh_BgShowErrorBackid"));
				mCustomWait(2000);
				ClubbedUtils.rnl_mDynamicReadShowErrorGrid(mGetPropertyFromFile("adh_BgShowErrorTableid"),num);					
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



	//Bill Printing Script
	public void billPrinting_Script()
	{
		try
		{
			//Navigating from Rent And Lease link to Transaction to bill printing
			mCustomWait(1000);
			mNavigation(mGetPropertyFromFile("adh_BprRentNLeaseLinkid"), mGetPropertyFromFile("adh_BprTransactionsLinkid"), mGetPropertyFromFile("adh_BprRentLinkid"),mGetPropertyFromFile("adh_BprRentPrintingLinkid"));
			mCustomWait(1000);
			mTakeScreenShot();

			//sending contract Number
			mWaitForVisible("id", mGetPropertyFromFile("adh_BprContractid"));
			mCustomWait(1000);
			if(Flag==0){
				billPrintingSearchCriteria();
			}
			else
			{
				if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent")) 
				{
					mSendKeys("id", mGetPropertyFromFile("adh_BprContractid"),adhContractNo.get(CurrentinvoCount));
				}
				else
				{
					mSendKeys("id", mGetPropertyFromFile("adh_BprContractid"), mGetPropertyFromFile("AgencyContractNo"));
				}
			}
			mTakeScreenShot();
			//search Button
			mWaitForVisible("css", mGetPropertyFromFile("adh_BprContractSearchBtnid"));
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("adh_BprContractSearchBtnid"));
			mCustomWait(1000);
			//mClick("id", mGetPropertyFromFile("adh_BprPrintBillChkBoxid"));
			mCustomWait(1000);

			mTakeScreenShot();
			pageNo=mGetText("id", mGetPropertyFromFile("adh_BprGridTablePageCountid")); 
			System.out.println("total no of page is : "+pageNo); 



			List<WebElement> els = driver.findElements(By.xpath("//input[@type='checkbox']"));

			for ( WebElement el : els ) {
				if ( !el.isSelected() ) {
					mCustomWait(200);
					el.click();
					mCustomWait(100);
				}
			}
			mCustomWait(500);
			mClick("id", mGetPropertyFromFile("adh_BprGenerateBillBtnid"));
			//ClubbedUtils.rnl_mDynamicReadBillPrintGrid(mGetPropertyFromFile("adh_BprGridTableNameid"),pageNo);
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in billPrinting_Script");
		}
	}

	////////////////////////////////////////////////Bill Printing Script For Agency Contract//////////////////


	//Agency Contract Bill Printing Script
	public void Agency_Contract_billPrinting_Script()
	{
		try
		{
			//Navigating from Rent And Lease link to Transaction to bill printing
			mCustomWait(1000);
			mNavigation(mGetPropertyFromFile("adh_BprRentNLeaseLinkid"), mGetPropertyFromFile("adh_BprTransactionsLinkid"), mGetPropertyFromFile("adh_BprRentLinkid"),mGetPropertyFromFile("adh_BprRentPrintingLinkid"));
			mCustomWait(1000);
			mTakeScreenShot();

			//sending contract Number
			mWaitForVisible("id", mGetPropertyFromFile("adh_BprContractid"));

			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent")) 
			{
				mSendKeys("id", mGetPropertyFromFile("adh_BprContractid"),adhContractNo.get(CurrentinvoCount));
			}
			else
			{
				mSendKeys("id", mGetPropertyFromFile("adh_BprContractid"), mGetPropertyFromFile("AgencyContractNo"));
			}

			//search Button
			mWaitForVisible("css", mGetPropertyFromFile("adh_BprContractSearchBtnid"));
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("adh_BprContractSearchBtnid"));
			mCustomWait(1000);

			pageNo=mGetText("id", mGetPropertyFromFile("adh_BprGridTablePageCountid")); 
			System.out.println("total no of page is : "+pageNo); 

			List<WebElement> els = driver.findElements(By.xpath("//input[@type='checkbox']"));

			for ( WebElement el : els ) {
				if ( !el.isSelected() ) {
					mCustomWait(200);
					el.click();
					mCustomWait(100);
				}
			}

			mCustomWait(500);
			mClick("id", mGetPropertyFromFile("adh_BprGenerateBillBtnid"));
			//ClubbedUtils.rnl_mDynamicReadBillPrintGrid(mGetPropertyFromFile("adh_BprGridTableNameid"),pageNo);
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in billPrinting_Script");
		}
	}



	//Renewal Of Contract Script
	public void renewalOfRentContract_Script()
	{
		try
		{
			//navigating from Rent and lease to Renewal of new contract
			mNavigation("adh_RrAdvtNHoardOnlineDeptServiceLinkid","adh_RrAdvtNHoardLinkidxpath", "adh_RrRenewalOfRentContrctLinkid");
			mCustomWait(1000);

			//Capturing Service name from application form
			mWaitForVisible("css", mGetPropertyFromFile("Servicenametextrenewalid"));
			ADHserviceName=mGetText("css", mGetPropertyFromFile("Servicenametextrenewalid"));
			ARR_ServiceNameADH.add(ADHserviceName);
			System.out.println("Service name is" +ARR_ServiceNameADH);


			//Selecting and Capturing Applicant Title
			mWaitForVisible("id", mGetPropertyFromFile("adh_RrApplicantTitleid"));
			mCustomWait(1000);
			mSelectDropDown("id", mGetPropertyFromFile("adh_RrApplicantTitleid"), mGetPropertyFromFile("adh_RrApplicantTitleData"));
			String adh_RrApplicantTitle=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_RrApplicantTitleid"));
			adh_rc_ApplicantTitle.add(adh_RrApplicantTitle);

			//Selecting and Capturing Applicant First name
			mWaitForVisible("id", mGetPropertyFromFile("adh_RrAplcntFirstNameid"));
			mCustomWait(1000);
			mSendKeys("id",  mGetPropertyFromFile("adh_RrAplcntFirstNameid"),mGetPropertyFromFile("adh_RrAplcntFirstNameData"));
			String adh_RrAplcntFirstName=mGetText("id", mGetPropertyFromFile("adh_RrAplcntFirstNameid"),"value");
			adh_rc_ApplicantFname.add(adh_RrAplcntFirstName);

			//Selecting and Capturing Applicant Middle Name
			mWaitForVisible("id", mGetPropertyFromFile("adh_RrAplcntMiddleNameid"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("adh_RrAplcntMiddleNameid"), mGetPropertyFromFile("adh_RrAplcntMiddleNameData"));
			String adh_RrAplcntMiddleName=mGetText("id", mGetPropertyFromFile("adh_RrAplcntMiddleNameid"),"value");
			adh_rc_ApplicantMname.add(adh_RrAplcntMiddleName);

			//Selecting and Capturing Applicant Last Name
			mWaitForVisible("id", mGetPropertyFromFile("adh_RrAplcntLastNameid"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("adh_RrAplcntLastNameid"), mGetPropertyFromFile("adh_RrAplcntLastNameData"));
			String adh_RrAplcntLastName=mGetText("id", mGetPropertyFromFile("adh_RrAplcntLastNameid"),"value");
			adh_rc_ApplicantLname.add(adh_RrAplcntLastName);

			String Fname = mGetPropertyFromFile("adh_RrAplcntFirstNameData");
			String Mname = mGetPropertyFromFile("adh_RrAplcntMiddleNameData");
			String Lname = mGetPropertyFromFile("adh_RrAplcntLastNameData");
			//Selecting and Capturing Applicant full NAme
			rrcapplName=Fname+" "+Mname+" "+Lname;
			System.out.println("applName is : " + rrcapplName); 
			rrcontract_ApplicantFullname.add(rrcapplName);
			adh_ApplicantFullname.add(rrcapplName);

			//Selecting and Capturing Applicant Mobile Number
			mWaitForVisible("id", mGetPropertyFromFile("adh_RrAplcntMobileNoid"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("adh_RrAplcntMobileNoid"), mGetPropertyFromFile("adh_RrAplcntMobileNoData"));
			String adh_RrAplcntMobileNo=mGetText("id", mGetPropertyFromFile("adh_RrAplcntMobileNoid"),"value");
			adh_rc_ApplicantMbNo.add(adh_RrAplcntMobileNo);

			//Selecting and Capturing Applicant Email id
			mWaitForVisible("id", mGetPropertyFromFile("adh_RrAplcntEmailid"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("adh_RrAplcntEmailid"), mGetPropertyFromFile("adh_RrAplcntEmailData"));
			String adh_RrAplcntEmail=mGetText("id", mGetPropertyFromFile("adh_RrAplcntEmailid"),"value");
			adh_rc_ApplicantEmail.add(adh_RrAplcntEmail);

			//Selecting and Capturing Applicant Add1
			mWaitForVisible("id", mGetPropertyFromFile("adh_RrAplcntCorrAdd1id"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("adh_RrAplcntCorrAdd1id"), mGetPropertyFromFile("adh_RrAplcntCorrAdd1Data"));
			String adh_RrAplcntCorrAdd1=mGetText("id", mGetPropertyFromFile("adh_RrAplcntCorrAdd1id"),"value");
			adh_rc_ApplicantAddOne.add(adh_RrAplcntCorrAdd1);

			//Selecting and Capturing Applicant Add2
			mWaitForVisible("id", mGetPropertyFromFile("adh_RrAplcntCorrAdd2id"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("adh_RrAplcntCorrAdd2id"), mGetPropertyFromFile("adh_RrAplcntCorrAdd2Data"));
			String adh_RrAplcntCorrAdd2=mGetText("id", mGetPropertyFromFile("adh_RrAplcntCorrAdd2id"),"value");
			adh_rc_ApplicantAddTwo.add(adh_RrAplcntCorrAdd2);

			//Selecting and Capturing Applicant Pin code
			mWaitForVisible("id", mGetPropertyFromFile("adh_RrAplcntPincodeid"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("adh_RrAplcntPincodeid"), mGetPropertyFromFile("adh_RrAplcntPincodeData"));
			String adh_RrAplcntPincode=mGetText("id", mGetPropertyFromFile("adh_RrAplcntPincodeid"),"value");
			adh_rc_ApplicantPinCode.add(adh_RrAplcntPincode);

			//Selecting and Capturing  Renewal Date
			String adh_RrRenewalFromDate=mGetText("id",mGetPropertyFromFile("adh_RrAplcntRenewalDateid"), "value");
			adh_rc_RenewalDate.add(adh_RrRenewalFromDate);

			//Search Contract Buttom
			mWaitForVisible("css", mGetPropertyFromFile("adh_RrSearchContractBtnid"));
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("adh_RrSearchContractBtnid"));

			//sending contract number
			mWaitForVisible("id", mGetPropertyFromFile("adh_RrContractNoid"));
			mSendKeys("id", mGetPropertyFromFile("adh_RrContractNoid"), mGetPropertyFromFile("adh_ContractNoData"));

			//Search Button
			mWaitForVisible("css", mGetPropertyFromFile("adh_RrSearchPartContractBtnid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("adh_RrSearchPartContractBtnid"));

			//selecting Application
			mWaitForVisible("name", mGetPropertyFromFile("adh_RrSelectContrctRadioBtnid"));
			mCustomWait(1000);
			mClick("name", mGetPropertyFromFile("adh_RrSelectContrctRadioBtnid"));

			//Submit Button
			mWaitForVisible("css", mGetPropertyFromFile("adh_RrSelectContrctSubBtnid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("adh_RrSelectContrctSubBtnid"));

			////Selecting and Capturing Renewal From Date
			mCustomWait(500);
			mDateSelect("id",mGetPropertyFromFile("adh_RrRenewalFromDateid"), mGetPropertyFromFile("adh_RrRenewalFromDateData"));
			String adh_RcRenewalFromDate=mGetText("id",mGetPropertyFromFile("adh_RrRenewalFromDateid"), "value");
			adh_rc_BookingfromDate.add(adh_RcRenewalFromDate);

			try{
				mCustomWait(500);
				//Renewal To Date
				mDateSelect("id",mGetPropertyFromFile("adh_RrRenewalTodateid"), mGetPropertyFromFile("adh_RrRenewalTodateData"));
				adh_rc_BookingtoDate.add( mGetPropertyFromFile("adh_RrRenewalTodateData"));
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
				mDateSelect("id",mGetPropertyFromFile("adh_RrRenewalFromDateid"), mGetPropertyFromFile("adh_RenewalRepeatFromDateData"));
				String adh_RcRenewalFromDate1=mGetText("id",mGetPropertyFromFile("adh_RrRenewalFromDateid"), "value");
				adh_rc_BookingfromDate.add(adh_RcRenewalFromDate1);


				//Selecting and Capturing Renewal To Date
				mDateSelect("id",mGetPropertyFromFile("adh_RrRenewalTodateid"), mGetPropertyFromFile("adh_RenewalRepeatToDateData"));
				adh_rc_BookingtoDate.add( mGetPropertyFromFile("adh_RrRenewalTodateData"));

				//Selecting and Capturing Sending remarks
				mWaitForVisible("id", mGetPropertyFromFile("adh_RrTenantRemarksid"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("adh_RrTenantRemarksid"), mGetPropertyFromFile("adh_RrTenantRemarksData"));
				adh_rc_ApplicantRemarks.add(mGetPropertyFromFile("adh_RrTenantRemarksData"));	
			}

			//Selecting and Capturing Sending remarks
			mWaitForVisible("id", mGetPropertyFromFile("adh_RrTenantRemarksid"));
			mCustomWait(1000);
			mSendKeys("id", mGetPropertyFromFile("adh_RrTenantRemarksid"), mGetPropertyFromFile("adh_RrTenantRemarksData"));
			adh_rc_ApplicantRemarks.add(mGetPropertyFromFile("adh_RrTenantRemarksData"));

			//Reading View Details Hoarding
			ViewDetailsHoarding();

			mCustomWait(1000);

			//Adding Hoarding
			AddHoarding();

			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);
			mscroll(0,710);
			mCustomWait(1000);

			//Uploading Documents
			docUpload("xpath", mGetPropertyFromFile("adh_RrUploadtableID"), "upldDocStatus");

			mTakeScreenShot();
			mCustomWait(1000);
			payment("paymentMode","byBankOrULB");	

			if(!mGetPropertyFromFile("paymentMode").equalsIgnoreCase("online"))
			{
				//Submit Button
				mWaitForVisible("css", mGetPropertyFromFile("adh_RrFormSubBtnid"));
				mCustomWait(1000);
				mClick("css",  mGetPropertyFromFile("adh_RrFormSubBtnid"));

				//Proceed Button
				mWaitForVisible("id", mGetPropertyFromFile("adh_RrFormSubProcdBtnid"));
				mCustomWait(1000);
				// Capturing Pop Up Message
				String PopUpMsg = mGetText("css", mGetPropertyFromFile("adh_RrFormSubAssertMsgid"));
				System.out.println("Pop Up Message is :"+PopUpMsg);
				mAppNoArray("css",mGetPropertyFromFile("adh_RrFormSubAssertApplicationNoid"));
				System.out.println("Application Number of rent renewal is :"+appNo);
				clv_ApplicanyionNo.add(appNo);

				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				String finalmsg= PopUpMsg.replaceAll("[0-9]", "");

				finalmsg=finalmsg.trim();
				System.out.println("actual assert msg is : "+finalmsg);
				System.out.println("Expected assert msg is : "+mGetPropertyFromFile("adh_RrFormSubAssertFinalMsgData"));
				if(finalmsg.equals(mGetPropertyFromFile("adh_RrFormSubAssertFinalMsgData").toString().trim())) 
				{
					System.out.println(" assert msg matches : ");
				}
				mAssert(finalmsg,mGetPropertyFromFile("adh_RrFormSubAssertFinalMsgData").toString().trim(),"Final message does not match"+" Actual msg :"+finalmsg+"  Expected msg :"+mGetPropertyFromFile("adh_RrFormSubAssertFinalMsgData").toString().trim());
				mCustomWait(1000);

				// Click on Proceed button 
				mClick("id", mGetPropertyFromFile("adh_RrFormSubProcdBtnid"));
				mCustomWait(3000);			
				mChallanPDFReader();

			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in renewalOfRentContract_Script script");
		}
	}


	//Search Criteria View Details Hoarding
	public void ViewDetailsHoarding()
	{
		try
		{
			//Click on View Hoarding Button
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("adh_Scr_RenewalViewHoardingHoardingID"));

			//Search by Hoarding Code
			if(mGetPropertyFromFile("adh_RenewalVwSearchHoardingCodeYNData").equalsIgnoreCase("yes")){
				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("adh_Scr_RenewalViewHoardingHoardingCodeID"), mGetPropertyFromFile("adh_RenewalVwSendHoardingCodeData"));
			}

			//Search by Hoarding Type
			if(mGetPropertyFromFile("adh_RenewalVwSearchHoardingTypeYNData").equalsIgnoreCase("yes")){
				mCustomWait(500);
				mSelectDropDown("id", mGetPropertyFromFile("adh_RenewalViewHoardingHoardingTypeD"), mGetPropertyFromFile("adh_RenewalVwSendHoardingTypeData"));
			}
			//Search by Hoarding Address
			if(mGetPropertyFromFile("adh_RenewalVwSearchHoardingLocationAddressYNData").equalsIgnoreCase("yes")){
				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("adh_RenewalViewHoardingLocationAddressID"), mGetPropertyFromFile("adh_RenewalSendHoardingLocationAddressData"));
			}

			////Search by Hoarding Hoarding From To date
			if(mGetPropertyFromFile("adh_RenewalVwSearchHoardingFromToDateYNdata").equalsIgnoreCase("yes")){
				mCustomWait(1000);
				mDateSelect("id", mGetPropertyFromFile("adh_RenewalViewHoardingFromdateID"), mGetPropertyFromFile("adh_RenewalVwSendHoardingFromDateData"));
				mCustomWait(1000);
				mDateSelect("id", mGetPropertyFromFile("adh_RenewalViewHoardingToDateID"), mGetPropertyFromFile("adh_RenewalVwSendHoardingToDateData"));
			}

			// Click on Search button 
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_RenewalViewHoardingSearchBtnID"));
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("adh_RenewalViewHoardingSearchBtnID"));
			//Closing open Pop Up Box
			mWaitForVisible("css", mGetPropertyFromFile("adh_RenewalViewHoardingSearchPopUpCloseID"));
			mClick("css", mGetPropertyFromFile("adh_RenewalViewHoardingSearchPopUpCloseID"));
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in ViewDetailsHoarding script");
		}
	}


	//Method To add Multiple Hoarding
	public void AddHoarding()
	{
		try{
			if(mGetPropertyFromFile("adh_RenewalAddMultipleHoardingYNData").equalsIgnoreCase("yes")){
				//Add multiple Hoardings details button hoarding for booking
				String RAC_HoardingNo= mGetPropertyFromFile("adh_RenewalAddSendHoardingCodeData");
				String RAC_HoardingType= mGetPropertyFromFile("adh_RenewalAddSendHoardingTypeData");
				String RAC_HoardingType1= mGetPropertyFromFile("adh_RenewalAddSendHoardingType1Data");
				String RAC_HoardingAddress= mGetPropertyFromFile("adh_RenewalAddSendHoardingLocationAddressData");

				ArrayList HoardingNoList1 = new ArrayList(Arrays.asList(RAC_HoardingNo.split(",")));
				ArrayList HoardingTypeList1 = new ArrayList(Arrays.asList(RAC_HoardingType.split(",")));
				ArrayList HoardingType1List1 = new ArrayList(Arrays.asList(RAC_HoardingType1.split(",")));
				ArrayList HoardingAddressList1 = new ArrayList(Arrays.asList(RAC_HoardingAddress.split(",")));

				for(int i=0; i<HoardingNoList1.size();i++)
				{

					mCustomWait(1000);
					//Click on Add Hoarding Button
					mClick("xpath", mGetPropertyFromFile("adh_RenewalAddHoardingDetailsBtnID"));

					//Search Hoarding by Code
					if(mGetPropertyFromFile("adh_RenewalSearchAddHoardingCodeYNData").equalsIgnoreCase("yes")){

						mCustomWait(500);
						mSendKeys("id", mGetPropertyFromFile("adh_RenewalAddSendHoardingCodeID"),HoardingNoList1.get(i).toString());
					}

					//Search Hoarding by Type
					if(mGetPropertyFromFile("adh_RenewalSearchAddHoardingTypeYNData").equalsIgnoreCase("yes")){
						mCustomWait(500);
						mSelectDropDown("id", mGetPropertyFromFile("adh_RenewalAddSendHoardingTypeID"),HoardingTypeList1.get(i).toString());
					}
					//Search Hoarding by Type 1
					if(mGetPropertyFromFile("adh_RenewalSearchAddHoardingType1YNData").equalsIgnoreCase("yes")){
						mCustomWait(500);
						mSelectDropDown("id", mGetPropertyFromFile("adh_RenewalAddSendHoardingType1ID"),HoardingType1List1.get(i).toString());
					}
					//Search Hoarding by Address
					if(mGetPropertyFromFile("adh_RenewalAddSearchHoardingLocationAddressYNData").equalsIgnoreCase("yes")){
						mCustomWait(500);
						mSendKeys("id", mGetPropertyFromFile("adh_RenewalAddSendHoardingLocationAddressID"),HoardingAddressList1.get(i).toString());
					}
					// Click on Search button 
					mCustomWait(200);
					mWaitForVisible("xpath", mGetPropertyFromFile("adh_RenewalAddHoardingSearchBtnID"));
					mClick("xpath", mGetPropertyFromFile("adh_RenewalAddHoardingSearchBtnID"));

					//Selecting Check box
					mCustomWait(200);
					mWaitForVisible("xpath", mGetPropertyFromFile("adh_RenewalAddHoardingCheckBoxID"));
					mTakeScreenShot();
					mClick("xpath", mGetPropertyFromFile("adh_RenewalAddHoardingCheckBoxID"));
					mCustomWait(200);
					// Click on Submit button
					mWaitForVisible("xpath", mGetPropertyFromFile("adh_RenewalAddHoardingSubmitID"));
					mClick("xpath", mGetPropertyFromFile("adh_RenewalAddHoardingSubmitID"));
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in AddHoarding");
		}
	}



	//Contract Bill Payment Schedule flow Method
	public void contractBillPayment_Script()
	{
		try
		{
			//Navigating from RentAndLease to Rent Payment Schedule
			mNavigation("adh_ConbpOnlineServicesid", "adh_ConbpPaymentid","adh_ConbpContractBillPaymentid");

			mCustomWait(500);
			mTakeScreenShot();
			mCustomWait(500);

			// Click on Search Contract Button
			mWaitForVisible("id", mGetPropertyFromFile("adh_ConbpContractseactBtnid"));
			/*mWaitForVisible("xpath", mGetPropertyFromFile("adh_ConbpAgencyApplicationSearchbtnid"));*/
			mClick("id", mGetPropertyFromFile("adh_ConbpContractseactBtnid"));
			mCustomWait(1000);

			mWaitForVisible("css", mGetPropertyFromFile("adh_ConbpSearchPopUpid"));
			mCustomWait(2000);
			if(!ADHserviceName.equalsIgnoreCase("Agency Contract")){
				//#middle_right > h1
				mWaitForVisible("id", mGetPropertyFromFile("adh_ConbpAgencyApplicationNumid"));
				IndOrDep("id","adh_ConbpAgencyApplicationNumid","applicationNo");
			}
			else{
				if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent")) 
				{

					mSendKeys("id",mGetPropertyFromFile("adh_ConbpContractNumid"), adhContractNo.get(CurrentinvoCount));
				}
				else{
					mSendKeys("id",mGetPropertyFromFile("adh_ConbpContractNumid"), mGetPropertyFromFile("AgencyContractNo"));
				}
			}

			mCustomWait(1500);
			mClick("css", mGetPropertyFromFile("adh_ConbpSearchPopUpbtnid"));
			mCustomWait(1500);
			//Click on radio button
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_ConbpAgnApplicationRbtnSelBtnid"));
			mClick("xpath", mGetPropertyFromFile("adh_ConbpAgnApplicationRbtnSelBtnid"));
			mTakeScreenShot();
			mCustomWait(1500);
			//Click on Application Submit button
			mClick("xpath", mGetPropertyFromFile("adh_ConbpAgnApplicationSubmitBtnid"));
			mCustomWait(500);
			mWaitForVisible("id", mGetPropertyFromFile("adh_ConbpContractNumreadid"));
			//Capturing Contract No 
			String ContractNo=mGetText("id", mGetPropertyFromFile("adh_ConbpContractNumreadid"), "value");
			System.out.println("Contract No is in Contract Bill Payment:::"+ContractNo);
			ContractBillPayment_ContractNo.add(ContractNo);
			mCustomWait(500);

			//Capturing Application No 
			String ApplicationNo=mGetText("id", mGetPropertyFromFile("adh_ConbpAgencyReadApplicationNumid"), "value");
			System.out.println("Application No is in Contract Bill Payment:::"+ApplicationNo);
			mCustomWait(500);
			//Capturing Applicant Name
			String ApplicantName=mGetText("id", mGetPropertyFromFile("adh_ConbpAgencyApplicationNameid"), "value");
			System.out.println("Application Name is in Contract Bill Payment:::"+ApplicantName);
			mCustomWait(500);

			//Capturing Due Amt
			String DueAmt=mGetText("id", mGetPropertyFromFile("adh_ConbpDueAmountid"), "value");
			System.out.println("Due Amt in Rs is in Contract Bill Payment:::"+DueAmt);
			mCustomWait(500);

			//Capturing Intrest Amt
			String IntrestAmt=mGetText("id", mGetPropertyFromFile("adh_ConbpIntrestAmountid"), "value");
			System.out.println("Intrest Amt is in Contract Bill Payment:::"+IntrestAmt);
			mCustomWait(500);

			//Capturing Total Payble amt
			String TotalPayble=mGetText("id", mGetPropertyFromFile("adh_ConbpTotalAmountid"), "value");
			System.out.println("Total Payble amt is in Contract Bill Payment:::"+TotalPayble);
			ContractBillPayment_totalPaybleAmt.add(TotalPayble);
			mCustomWait(500);

			//Selection Criteria Based on Last Bill Due Amt yes
			if(mGetPropertyFromFile("adh_ConbpLastBillDueAmountYNData").equalsIgnoreCase("yes")){
				//Click on Radio Button
				mWaitForVisible("xpath", mGetPropertyFromFile("adh_ConbpLastBillDueRadiobtnid"));
				mClick("xpath", mGetPropertyFromFile("adh_ConbpLastBillDueRadiobtnid"));
				mCustomWait(500);
				//Capturing Last bill Amount
				String LastBillAmt=mGetText("id", mGetPropertyFromFile("adh_ConbpTotalAmountid"), "value");
				System.out.println("Last Bill Amount is in Contract Bill Payment:::"+LastBillAmt);
				mCustomWait(500);
				mAssert(LastBillAmt, TotalPayble, "Last Bill Due Amount dose not match with the payble amount");
			}
			else
			{
				//Click on Other radio button amount
				mClick("xpath", mGetPropertyFromFile("adh_ConbpOtherRadiobtnid"));
				// Sending other amt and capturing value
				mSendKeys("id", mGetPropertyFromFile("adh_ConbpOtherAmtid"), mGetPropertyFromFile("adh_ConbpOtherAmountData"));
				String OtherAmt=mGetText("id", mGetPropertyFromFile("adh_ConbpOtherAmtid"), "value");
				System.out.println("Other Amount is in Contract Bill Payment:::"+OtherAmt);
				mCustomWait(500);

			}
			mTakeScreenShot();
			payment("paymentMode","byBankOrULB");

			// Click on Contract form submit button
			mWaitForVisible("id",mGetPropertyFromFile("adh_ConbpFormSubBtnid"));
			mCustomWait(1000);
			mClick("id",mGetPropertyFromFile("adh_ConbpFormSubBtnid"));

			// Capturing Pop Up Message
			mWaitForVisible("css",mGetPropertyFromFile("adh_ConbpPopUpMsgid"));
			mCustomWait(1000);
			String PopUpMsg=mGetText("css",mGetPropertyFromFile("adh_ConbpPopUpMsgid"));
			System.out.println("Pop Up msg is"+PopUpMsg);
			mAssert(PopUpMsg, mGetPropertyFromFile("adh_ConbpPopUpMsgData"), "Pop Up msg dose not match");

			//Click on Proceed Button
			mWaitForVisible("css",mGetPropertyFromFile("adh_ConbpPopUpProceedBtnid"));
			mTakeScreenShot();
			mClick("id",mGetPropertyFromFile("adh_ConbpPopUpProceedBtnid"));
			mChallanPDFReader();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in contractBillPayment_Script");
		}
	}


	//Set Up Of Hoarding Method
	public void setUpOfHording() throws InterruptedException{

		mCreateArtefactsFolder("ADH_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));
		setUpOfHording_Script();
		setUpOfHordingProceed();
		logOut();
		finalLogOut();
		isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVreSetUpOfHoardingServiceName"));
		if(mGetPropertyFromFile("chlanAtULBTypeOfPayModeId").equalsIgnoreCase("Cash")){
			adh_ApplicationTimePaymentReceipt_Cash_assertion();
		}
		else{
			adh_ApplicationTimePaymentReceipt_Cheque_assertion();
		}
		mCloseBrowser();

	}


	public void scrutinyProcess(){
		mCreateArtefactsFolder("ADH_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		departmentLogin(mGetPropertyFromFile("cfc_RNLscruProcUserName"),mGetPropertyFromFile("cfc_RNLscruProcPassword"));
		scrutiny.scrutinyProcess();
		logOut();
		finalLogOut();
		mCloseBrowser();
	}



	//**Approval Letter Printing  for Agency Contract and All Services
	public void approvalLetterPrinting(){
		mCreateArtefactsFolder("ADH_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
		ApprovalLetter_script();
		logOut();
		finalLogOut();
		mCloseBrowser();		
	}

	//**Contract Letter Printing  for Agency Contract and All Services
	public void ContractLetterPrinting(){
		mCreateArtefactsFolder("ADH_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
		ContractLetter_script();
		logOut();
		finalLogOut();
		mCloseBrowser();		
	}



	//Agency Contract Generation
	public void agencyContractGenration() throws InterruptedException, IOException
	{
		mCreateArtefactsFolder("ADH_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));
		agencyContract();
		logOut();
		finalLogOut();
		mCloseBrowser();
	}



	public void bookingOfHording_Script() throws InterruptedException{

		try {				   
			LOIAPPLICABLE=false;
			mNavigation("adh_AdvertisingAndHoardingNavigationID", "adh_AdvertisingAndHoardingIDxpath", "adh_BookingOfHoardingID");


			//Capturing Service name from Application Form 
			ADHserviceName=mGetText("css", mGetPropertyFromFile("Servicenametextid"));
			System.out.println("Service name is" +ADHserviceName);
			if(mGetPropertyFromFile("wantToRejectAtChecklist").equalsIgnoreCase("No")){
				ARR_ServiceNameADH.add(ADHserviceName);
				System.out.println("Service name is" + ARR_ServiceNameADH);
			}
			System.out.println("Service name is" + ARR_ServiceNameADH);
			// Selecting and Capturing Applicant Title
			mWaitForVisible("id",  mGetPropertyFromFile("adh_BookingOfHoardingTitleID"));
			mSelectDropDown("id", mGetPropertyFromFile("adh_BookingOfHoardingTitleID"),mGetPropertyFromFile("adh_BookingOfHoardingTitleData"));
			String adh_BookingOfHoardingTitle=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_BookingOfHoardingTitleID"));
			adh_ApplicantTitle.add(adh_BookingOfHoardingTitle);
			System.out.println("Applicant Title in Booking of hoarding form"+adh_ApplicantTitle);
			mAssert(adh_BookingOfHoardingTitle, mGetPropertyFromFile("adh_BookingOfHoardingTitleData"), "Booking of Hoarding Applicant Title dose not match Actual::"+adh_BookingOfHoardingTitle+"Expected::"+mGetPropertyFromFile("adh_BookingOfHoardingTitleData"));


			// Sending and Capturing Applicant First Name
			mSendKeys("id", mGetPropertyFromFile("adh_BookingOfHoardingFnameID"),mGetPropertyFromFile("adh_BookingOfHoardingFnameData"));
			String adh_BookingOfHoardingFname=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingFnameID"), "value");
			adh_ApplicantFname.add(adh_BookingOfHoardingFname);
			System.out.println("Applicant First in Booking of hoarding form"+adh_ApplicantFname);
			mAssert(adh_BookingOfHoardingFname, mGetPropertyFromFile("adh_BookingOfHoardingFnameData"), "Booking of Hoarding Applicant First name dose not match Actual::"+adh_BookingOfHoardingFname+"Expected::"+mGetPropertyFromFile("adh_BookingOfHoardingFnameData"));


			// Sending and Capturing Applicant Middle Name
			mSendKeys("id", mGetPropertyFromFile("adh_BookingOfHoardingMidnameID"),mGetPropertyFromFile("adh_BookingOfHoardingMnameData"));
			String adh_BookingOfHoardingMname=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingMidnameID"), "value");

			adh_ApplicantMname.add(adh_BookingOfHoardingMname);

			mAssert(adh_BookingOfHoardingMname, mGetPropertyFromFile("adh_BookingOfHoardingMnameData"), "Booking of Hoarding Applicant Middle name dose not match Actual::"+adh_BookingOfHoardingMname+"Expected::"+mGetPropertyFromFile("adh_BookingOfHoardingMnameData"));


			// Sending and Capturing Applicant Last Name
			mSendKeys("id", mGetPropertyFromFile("adh_BookingOfHoardingLnameID"),mGetPropertyFromFile("adh_BookingOfHoardingLnameData"));
			String adh_BookingOfHoardingLname=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingLnameID"), "value");

			adh_ApplicantLname.add(adh_BookingOfHoardingLname);

			mAssert(adh_BookingOfHoardingLname, mGetPropertyFromFile("adh_BookingOfHoardingLnameData"), "Booking of Hoarding Applicant Last name dose not match Actual::"+adh_BookingOfHoardingLname+"Expected::"+mGetPropertyFromFile("adh_BookingOfHoardingLnameData"));			

			String Fname = mGetPropertyFromFile("adh_BookingOfHoardingFnameData");
			String Mname = mGetPropertyFromFile("adh_BookingOfHoardingMnameData");
			String Lname = mGetPropertyFromFile("adh_BookingOfHoardingLnameData");

			//Creating dyanamic Applicant Full Name for assertion Purpose
			boeapplName=Fname+" "+Mname+" "+Lname;
			System.out.println("applName is : " + boeapplName); 

			adh_ApplicantFullname.add(boeapplName);


			// Sending and Capturing Applicant Mobile No
			mSendKeys("id", mGetPropertyFromFile("adh_BookingOfHoardingMobNoID"),mGetPropertyFromFile("adh_BookingOfHoardingMobNoData"));
			String adh_BookingOfHoardingMobNo=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingMobNoID"), "value");

			adh_ApplicantMbNo.add(adh_BookingOfHoardingMobNo);

			mAssert(adh_BookingOfHoardingMobNo, mGetPropertyFromFile("adh_BookingOfHoardingMobNoData"), "Booking of Hoarding Applicant Mobile No dose not match Actual::"+adh_BookingOfHoardingMobNo+"Expected::"+mGetPropertyFromFile("adh_BookingOfHoardingMobNoData"));


			// Sending and Capturing Applicant Email Id
			mSendKeys("id", mGetPropertyFromFile("adh_BookingOfHoardingEmailID"),mGetPropertyFromFile("adh_BookingOfHoardingEmailData"));
			String adh_BookingOfHoardingEmail=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingEmailID"), "value");

			adh_ApplicantEmail.add(adh_BookingOfHoardingEmail);

			mAssert(adh_BookingOfHoardingEmail, mGetPropertyFromFile("adh_BookingOfHoardingEmailData"), "Booking of Hoarding Applicant Email ID dose not match Actual::"+adh_BookingOfHoardingEmail+"Expected::"+mGetPropertyFromFile("adh_BookingOfHoardingEmailData"));

			mTakeScreenShot();

			// Sending and Capturing Address Line 1
			mSendKeys("id", mGetPropertyFromFile("adh_BookingOfHoardingCorrAddOneID"),mGetPropertyFromFile("adh_BookingOfHoardingCorrAddOneData"));
			String adh_BookingOfHoardingCorrAddOne=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingCorrAddOneID"), "value");

			adh_ApplicantAddOne.add(adh_BookingOfHoardingCorrAddOne);
			mAssert(adh_BookingOfHoardingCorrAddOne, mGetPropertyFromFile("adh_BookingOfHoardingCorrAddOneData"), "Booking of Hoarding Applicant Address Line 1 dose not match Actual::"+adh_BookingOfHoardingCorrAddOne+"Expected::"+mGetPropertyFromFile("adh_BookingOfHoardingCorrAddOneData"));


			// Sending and Capturing Address Line 2
			mSendKeys("id", mGetPropertyFromFile("adh_BookingOfHoardingCorrAddTwoID"),mGetPropertyFromFile("adh_BookingOfHoardingCorrAddTwoData"));
			mCustomWait(200);
			String adh_BookingOfHoardingCorrAddTwo=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingCorrAddTwoID"), "value");

			adh_ApplicantAddTwo.add(adh_BookingOfHoardingCorrAddTwo);
			mAssert(adh_BookingOfHoardingCorrAddTwo, mGetPropertyFromFile("adh_BookingOfHoardingCorrAddTwoData"), "Booking of Hoarding Applicant Address Line 2 dose not match Actual::"+adh_BookingOfHoardingCorrAddTwo+"Expected::"+mGetPropertyFromFile("adh_BookingOfHoardingCorrAddTwoData"));


			// Sending and Capturing Pin Code
			mSendKeys("id", mGetPropertyFromFile("adh_BookingOfHoardingPincodeID"),mGetPropertyFromFile("adh_BookingOfHoardingPinCodeData"));
			String adh_BookingOfHoardingPincode=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingPincodeID"), "value");

			adh_ApplicantPinCode.add(adh_BookingOfHoardingPincode);

			mAssert(adh_BookingOfHoardingPincode, mGetPropertyFromFile("adh_BookingOfHoardingPinCodeData"), "Booking of Hoarding Applicant Pincode dose not match Actual::"+adh_BookingOfHoardingPincode+"Expected::"+mGetPropertyFromFile("adh_BookingOfHoardingPinCodeData"));

			mTakeScreenShot();

			// Sending and Capturing Hoarding Booking Date
			String BookingDate= mGetText("id",mGetPropertyFromFile("adh_BookingOfHoardingBookingDateID"),"value");

			adh_BookingtoDate.add(BookingDate);

			// Sending and Capturing Hoarding Remarks
			mSendKeys("id", mGetPropertyFromFile("adh_BookingOfHoardingRemarksID"),mGetPropertyFromFile("adh_BookingOfHoardingRemarksData"));
			String adh_BookingOfHoardingRemarks=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingRemarksID"), "value");

			adh_ApplicantRemarks.add(adh_BookingOfHoardingRemarks);
			mAssert(adh_BookingOfHoardingRemarks, mGetPropertyFromFile("adh_BookingOfHoardingRemarksData"), "Booking of Hoarding Applicant Remarks dose not match Actual::"+adh_BookingOfHoardingPincode+"Expected::"+mGetPropertyFromFile("adh_BookingOfHoardingRemarksData"));


			// Sending and Capturing Booking From date
			mDateSelect("id",mGetPropertyFromFile("adh_BookingOfHoardingFromDateID"),mGetPropertyFromFile("adh_BookingOfHoardingFromDateData"));
			String BookingfrDate=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingFromDateID"));
			System.out.println("Booking date is " +BookingfrDate);

			adh_BookingfromDate.add(mGetPropertyFromFile("adh_BookingOfHoardingFromDateData"));
			//mAssert(BookingfrDate, mGetPropertyFromFile("adh_BookingOfHoardingFromDateData"), "Booking of Hoarding Applicant Hoarding Booking From date dose not match Actual::"+BookingfrDate+"Expected::"+mGetPropertyFromFile("adh_BookingOfHoardingFromDateData"));

			// Sending and CapturingBooking To Date
			mCustomWait(1000);
			mDateSelect("id",mGetPropertyFromFile("adh_BookingOfHoardingToDateID"),mGetPropertyFromFile("adh_BookingOfHoardingToDateData"));
			String BookingtoDate=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingFromDateID"), "value");
			mAssert(BookingtoDate, mGetPropertyFromFile("adh_BookingOfHoardingToDateData"), "Booking of Hoarding Applicant Hoarding Booking To date dose not match Actual::"+BookingtoDate+"Expected::"+mGetPropertyFromFile("adh_BookingOfHoardingToDateData"));

			adh_BookingtoDate.add(mGetPropertyFromFile("adh_BookingOfHoardingToDateData"));

			mTakeScreenShot();


			//View and Click on button to view avalable hoarding hoarding for booking
			mCustomWait(500);
			mWaitForVisible("xpath",mGetPropertyFromFile("adh_Scr_BookingOfHoardingViewHoardingBtnID"));
			//click on Button
			mClick("xpath", mGetPropertyFromFile("adh_Scr_BookingOfHoardingViewHoardingBtnID"));
			mWaitForVisible("id",mGetPropertyFromFile("adh_Scr_BookingOfHoardingViewHoardingCodeID"));

			//Hoarding Search criteria on Hoarding Code
			if(mGetPropertyFromFile("adh_BookingOfHoardingViewHoardingCodeYNData").equalsIgnoreCase("yes")){
				mSendKeys("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingViewHoardingCodeID"),mGetPropertyFromFile("adh_ViewSeacrchHoardingNameData"));
				String HoardingNo=mGetText("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingViewHoardingCodeID"),"value");
				System.out.println(HoardingNo);
			}
			mCustomWait(1000);

			//Hoarding Search criteria on Hoarding Type
			if(mGetPropertyFromFile("adh_BookingOfHoardingViewHoradingTypeYNData").equalsIgnoreCase("yes")) 
			{
				mWaitForVisible("id",mGetPropertyFromFile("adh_Scr_BookingOfHoardingViewHoardingTypeID"));
				mSelectDropDown("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingViewHoardingTypeID"),mGetPropertyFromFile("adh_ViewSeacrchHoardingTypeData"));
				String HoardingType=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingViewHoardingTypeID"));
				System.out.println(HoardingType);
			}

			mCustomWait(1000);
			//Hoarding Search criteria on Hoarding Address/Location
			if(mGetPropertyFromFile("adh_BookingOfHoardingHoardingAddressYNData").equalsIgnoreCase("yes")) 
			{
				mSendKeys("id", mGetPropertyFromFile("adh_BookingOfHoardingHoardingAddressID"),mGetPropertyFromFile("adh_BookingOfHoardingHoardingAddressData"));
				String HoardingAddress=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingHoardingAddressID"), "value");
				System.out.println(HoardingAddress);
			}
			mCustomWait(1000);

			//Hoarding Search criteria on From Date to To Date
			if(
					mGetPropertyFromFile("adh_BookingOfHoardingFromdtToDateYNData").equalsIgnoreCase("yes")){
				mDateSelect("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingViewBookFromDateID"),mGetPropertyFromFile("adh_BookingOfHoardingFromDateData"));
				mCustomWait(1000);
				mDateSelect("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingViewBookToDateID"),mGetPropertyFromFile("adh_BookingOfHoardingToDateData"));
				mCustomWait(1000);
			}
			mTakeScreenShot();
			mCustomWait(1000);
			mWaitForVisible("xpath",mGetPropertyFromFile("adh_BookingOfHoardingViewSearchBtnID"));
			//Click on Search Button
			mClick("xpath", mGetPropertyFromFile("adh_BookingOfHoardingViewSearchBtnID"));
			mWaitForVisible("css",mGetPropertyFromFile("adh_Scr_BookingOfHoardingLabelDescriptionID"));


			mWaitForVisible("css",mGetPropertyFromFile("adh_Scr_BookingOfHoardingViewPopUpCloseID"));
			mCustomWait(1000);
			//Click on Pop Up to Close
			mClick("css", mGetPropertyFromFile("adh_Scr_BookingOfHoardingViewPopUpCloseID"));
			mCustomWait(1000);


			//Add multiple Hoardings details button hoarding for booking
			String BoH_HoardingNo= mGetPropertyFromFile("adh_BookingOfHoardingHoardingNameData");
			String BoH_HoardingType= mGetPropertyFromFile("adh_BookingOfHoardingHoardingTypeData");
			String BoH_HoardingType1= mGetPropertyFromFile("adh_BookingOfHoardingHoardingType1Data");
			String BoH_HoardingAddress= mGetPropertyFromFile("adh_BookingOfHoardingHoardingAddressData");

			ArrayList HoardingNoList = new ArrayList(Arrays.asList(BoH_HoardingNo.split(",")));
			ArrayList HoardingTypeList = new ArrayList(Arrays.asList(BoH_HoardingType.split(",")));
			ArrayList HoardingType1List = new ArrayList(Arrays.asList(BoH_HoardingType1.split(",")));
			ArrayList HoardingAddressList = new ArrayList(Arrays.asList(BoH_HoardingAddress.split(",")));

			for(int i=0; i<HoardingNoList.size();i++)
			{
				mWaitForVisible("linkText",mGetPropertyFromFile("adh_BookingOfHoardingAddDetID"));
				mCustomWait(1000);
				//Click on Add Hoarding Detail button 
				mClick("linkText", mGetPropertyFromFile("adh_BookingOfHoardingAddDetID"));

				//Hoarding Search criteria on Hoarding Code
				if(mGetPropertyFromFile("adh_BookingOfHoardingHoardingCodeYNData").equalsIgnoreCase("yes")){
					mSendKeys("id", mGetPropertyFromFile("adh_BookingOfHoardingHoardingNameID"),HoardingNoList.get(i).toString());
					String HoardingNo=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingHoardingNameID"),"value");

					adh_HoardingNo.add(HoardingNo);
				}

				//Hoarding Search criteria on Hoarding Type
				if(mGetPropertyFromFile("adh_BookingOfHoardingHoardingTypeYNData").equalsIgnoreCase("yes")) 
				{
					mWaitForVisible("id",mGetPropertyFromFile("adh_BookingOfHoardingHoardingTypeID"));
					mSelectDropDown("id", mGetPropertyFromFile("adh_BookingOfHoardingHoardingTypeID"),HoardingTypeList.get(i).toString());
					String HoardingType=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_BookingOfHoardingHoardingTypeID"));

					adh_HoardingType.add(HoardingType);
				}

				//Hoarding Search criteria on Hoarding Type 1
				if(mGetPropertyFromFile("adh_BookingOfHoardingHoardingType1YNData").equalsIgnoreCase("yes")){
					mSelectDropDown("id", mGetPropertyFromFile("adh_BookingOfHoardingHoardingType1ID"),HoardingType1List.get(i).toString());

					adh_HoardingType1.add(mGetPropertyFromFile("adh_BookingOfHoardingHoardingType1ID"));
					mCustomWait(1000);
				}

				//Hoarding Search criteria on Hoarding Address/Location
				if(mGetPropertyFromFile("adh_BookingOfHoardingHoardingAddressYNData").equalsIgnoreCase("yes")) 
				{
					mSendKeys("id", mGetPropertyFromFile("adh_BookingOfHoardingHoardingAddressID"),HoardingAddressList.get(i).toString());
					String HoardingAddress=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingHoardingAddressID"), "value");

					adh_HoardingAddress.add(HoardingAddress);
				}

				mWaitForVisible("xpath",mGetPropertyFromFile("adh_BookingOfHoardingSearchBtnID"));
				mCustomWait(1000);
				//Click on search Button
				mClick("xpath", mGetPropertyFromFile("adh_BookingOfHoardingSearchBtnID"));
				mWaitForVisible("css",mGetPropertyFromFile("adh_BookingOfHoardingSearchIconID"));

				//Selecting Check Box
				mWaitForVisible("name",mGetPropertyFromFile("adh_BookingOfHoardingCheckBoxID"));
				mCustomWait(1000);
				mClick("name", mGetPropertyFromFile("adh_BookingOfHoardingCheckBoxID"));
				mTakeScreenShot();
				mWaitForVisible("name",mGetPropertyFromFile("adh_BookingoOfHoardingHoardingSearchID"));
				mCustomWait(1000);
				mClick("name", mGetPropertyFromFile("adh_BookingoOfHoardingHoardingSearchID"));

				mscroll(0,800);

				mCustomWait(1000);

			}
			mCustomWait(1000);

			//Uploading Checklist Document
			docUpload("xpath",mGetPropertyFromFile("adh_BookingOfHoardingDocUploadtableID"),"upldDocStatus");


			mGenericWait();
			mTakeScreenShot();
			//Logic for application Time payment Yes or no
			if(mGetPropertyFromFile("adh_ApplicationTimePaymentYesNoData").equalsIgnoreCase("yes"))  
			{
				payment("paymentMode","byBankOrULB");

				mWaitForVisible("css",mGetPropertyFromFile("adh_BookingOfHoardingSubmitBtnID"));
				mCustomWait(1000);
				mClick("css",mGetPropertyFromFile("adh_BookingOfHoardingSubmitBtnID"));
			}

			else
			{	
				mWaitForVisible("css",mGetPropertyFromFile("adh_BookingOfHoardingSubmitBtnID"));
				mCustomWait(1000);
				mClick("css",mGetPropertyFromFile("adh_BookingOfHoardingSubmitBtnID"));
			}

		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in bookingOfHoarding_Script");
		}

	}



	public void bookingOfHordingProceed(){
		try{
			//Caputuring Application No from Pop message 
			String appNum =mGetApplicationNo("css",mGetPropertyFromFile("adh_BookingOfHoardingAppNoID"));
			mAppNoArray("css",mGetPropertyFromFile("adh_BookingOfHoardingAppNoID"));
			System.out.println("POP UP msg at Booking of Hoarding is::::"+appNum);

			System.out.println("application Number in proceed Arraylist::::"+applicationNo);
			mCustomWait(1000);
			System.out.println("application Number in proceed ::::"+appNo);
			ADHserviceName =appNo;
			System.out.println("application Number (ADHserviceName) ::::"+appNo);
			ApplicationNoADH.add(appNo);
			System.out.println("application Number (ApplicationNoADH) ::::"+ApplicationNoADH);

			mWaitForVisible("id", mGetPropertyFromFile("adh_BookingOfHoardingProceedID"));
			mTakeScreenShot();
			mCustomWait(1000);
			appSubmitMsg = mGetText("css", mGetPropertyFromFile("adh_BookingOfHoardingAppNoID"));
			System.out.println("App submit msg"+appSubmitMsg);
			strippedString = appSubmitMsg.replace(appNo, "");
			System.out.println("Stripped msg"+strippedString);
			mAssert(mGetPropertyFromFile("adh_BookingOfHoardingActualMsg"),strippedString,"Booking of Hoarding proceed message does not match, actual : "+mGetPropertyFromFile("adh_BookingOfHoardingActualMsg")+"expected : "+strippedString);
			mClick("id", mGetPropertyFromFile("adh_BookingOfHoardingProceedID"));
			mCustomWait(2000);		
			//downloading Challan If Application Time payment is yes
			if(mGetPropertyFromFile("adh_ApplicationTimePaymentYesNoData").equalsIgnoreCase("yes"))  {
				mChallanPDFReader();
			}
		}
		catch(Exception e )
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in bookingOfHoardingProceed script");
		}	
	}


	// Set Up Of Hoarding form Method
	public void setUpOfHording_Script() throws InterruptedException{

		try {
			LOIAPPLICABLE=false;
			mNavigation("adh_AdvertisingAndHoardingNavigationID", "adh_AdvertisingAndHoardingIDxpath", "adh_SetUpOfHoardingID");

			//Capturing Service name from Application Form 
			ADHserviceName=mGetText("css", mGetPropertyFromFile("Servicenametextid"));
			System.out.println("Service name is" +ADHserviceName);
			ARR_ServiceNameADH.add(ADHserviceName);
			System.out.println("Service name is" +ARR_ServiceNameADH);


			//Selecting and Capturing Applicant Title
			mWaitForVisible("id",  mGetPropertyFromFile("adh_SetUpOfHoardingTitleID"));
			mSelectDropDown("id", mGetPropertyFromFile("adh_SetUpOfHoardingTitleID"),mGetPropertyFromFile("adh_SetUpOfHoardingTitleData"));
			String adh_SetUpOfHoardingTitle=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_SetUpOfHoardingTitleID"));
			suoh_ApplicantTitle.add(adh_SetUpOfHoardingTitle);
			mAssert(adh_SetUpOfHoardingTitle, mGetPropertyFromFile("adh_SetUpOfHoardingTitleData"), "Set Up Of Hoarding Applicant Title dose not match Actual::"+adh_SetUpOfHoardingTitle+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingTitleData"));


			//Sending and Capturing Applicant First Name
			mSendKeys("id",mGetPropertyFromFile("adh_SetUpOfHoardingFnameID"),mGetPropertyFromFile("adh_SetUpOfHoardingFnameData"));
			String adh_SetUpOfHoardingFname=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingFnameID"), "value");
			suoh_ApplicantFname.add(adh_SetUpOfHoardingFname);
			mAssert(adh_SetUpOfHoardingFname,mGetPropertyFromFile("adh_SetUpOfHoardingFnameData"), "Set Up Of Hoarding Applicant First name dose not match Actual::"+adh_SetUpOfHoardingFname+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingFnameData"));


			// Sending and Capturing Applicant Middle Name
			mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingMidnameID"),mGetPropertyFromFile("adh_SetUpOfHoardingMnameData"));
			String adh_SetUpOfHoardingMname=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingMidnameID"), "value");
			suoh_ApplicantMname.add(adh_SetUpOfHoardingMname);
			mAssert(adh_SetUpOfHoardingMname, mGetPropertyFromFile("adh_SetUpOfHoardingMnameData"), "Set Up Of Hoarding Applicant Middle name dose not match Actual::"+adh_SetUpOfHoardingMname+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingMnameData"));

			// Sending and Capturing Applicant Last Name
			mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingLnameID"),mGetPropertyFromFile("adh_SetUpOfHoardingLnameData"));
			String adh_SetUpOfHoardingLname=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingLnameID"), "value");
			suoh_ApplicantLname.add(adh_SetUpOfHoardingLname);
			mAssert(adh_SetUpOfHoardingLname, mGetPropertyFromFile("adh_SetUpOfHoardingLnameData"), "Set Up Of Hoarding Applicant Last name dose not match Actual::"+adh_SetUpOfHoardingLname+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingLnameData"));			

			String Fname = mGetPropertyFromFile("adh_SetUpOfHoardingFnameData");
			String Mname = mGetPropertyFromFile("adh_SetUpOfHoardingMnameData");
			String Lname = mGetPropertyFromFile("adh_SetUpOfHoardingLnameData");

			//Creating dyanamic Applicant Full Name for assertion Purpose
			boeapplName=Fname+" "+Mname+" "+Lname;
			System.out.println("applName is : " + boeapplName); 
			suoh_ApplicantFullname.add(boeapplName);
			adh_ApplicantFullname.add(boeapplName);

			// Sending and Capturing Applicant Mobile No
			mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingMobNoID"),mGetPropertyFromFile("adh_SetUpOfHoardingMobNoData"));
			String adh_SetUpOfHoardingMobNo=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingMobNoID"), "value");
			suoh_ApplicantMbNo.add(adh_SetUpOfHoardingMobNo);
			mAssert(adh_SetUpOfHoardingMobNo, mGetPropertyFromFile("adh_SetUpOfHoardingMobNoData"), "Set Up Of Hoarding Applicant Mobile No dose not match Actual::"+adh_SetUpOfHoardingMobNo+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingMobNoData"));

			// Sending and Capturing Applicant Email Id
			mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingEmailID"),mGetPropertyFromFile("adh_SetUpOfHoardingEmailData"));
			String adh_SetUpOfHoardingEmail=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingEmailID"), "value");
			suoh_ApplicantEmail.add(adh_SetUpOfHoardingEmail);
			mAssert(adh_SetUpOfHoardingEmail, mGetPropertyFromFile("adh_SetUpOfHoardingEmailData"), "Set Up Of Hoarding Applicant Email ID dose not match Actual::"+adh_SetUpOfHoardingEmail+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingEmailData"));

			mTakeScreenShot();
			// Sending and Capturing Address Line 1
			mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingCorrAddOneID"),mGetPropertyFromFile("adh_SetUpOfHoardingCorrAddOneData"));
			String adh_SetUpOfHoardingCorrAddOne=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingCorrAddOneID"), "value");
			suoh_ApplicantAddOne.add(adh_SetUpOfHoardingCorrAddOne);
			mAssert(adh_SetUpOfHoardingCorrAddOne, mGetPropertyFromFile("adh_SetUpOfHoardingCorrAddOneData"), "Set Up Of Hoarding Applicant Address Line 1 dose not match Actual::"+adh_SetUpOfHoardingCorrAddOne+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingCorrAddOneData"));

			// Sending and Capturing Address Line 2
			mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingCorrAddTwoID"),mGetPropertyFromFile("adh_SetUpOfHoardingCorrAddTwoData"));
			mCustomWait(200);
			String adh_SetUpOfHoardingCorrAddTwo=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingCorrAddTwoID"), "value");
			suoh_ApplicantAddTwo.add(adh_SetUpOfHoardingCorrAddTwo);
			mAssert(adh_SetUpOfHoardingCorrAddTwo, mGetPropertyFromFile("adh_SetUpOfHoardingCorrAddTwoData"), "Set Up Of Hoarding Applicant Address Line 2 dose not match Actual::"+adh_SetUpOfHoardingCorrAddTwo+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingCorrAddTwoData"));

			// Sending and Capturing Pin Code
			mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingPincodeID"),mGetPropertyFromFile("adh_SetUpOfHoardingPinCodeData"));
			String adh_SetUpOfHoardingPincode=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingPincodeID"), "value");
			suoh_ApplicantPinCode.add(adh_SetUpOfHoardingPincode);
			mAssert(adh_SetUpOfHoardingPincode, mGetPropertyFromFile("adh_SetUpOfHoardingPinCodeData"), "Set Up Of Hoarding Applicant Pincode dose not match Actual::"+adh_SetUpOfHoardingPincode+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingPinCodeData"));

			//Capturing Booking Date
			String BookingDate= mGetText("id",mGetPropertyFromFile("adh_SetUpOfHoardingBookingDateID"),"value");
			suoh_BookingtoDate.add(BookingDate);
			//mAssert(BookingDate,strDate, "Set Up Of Hoarding Applicant Hoarding Booking Date dose not match Actual::"+BookingDate+"Expected::"+strDate);

			mTakeScreenShot();
			//Logic For to click  on radio Button weather hoarding set up is "New" or "Existing"
			if(mGetPropertyFromFile("adh_SetUpOfHoardingLocationTypeData").equalsIgnoreCase("New")){


				mWaitForVisible("id",  mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocationNewID"));
				//Selecting Radio Button New
				mClick("id", mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocationNewID"));

				// Selecting and Capturing Ward
				mWaitForVisible("id",  mGetPropertyFromFile("adh_SetUpOfHoardingWardID"));
				mSelectDropDown("id", mGetPropertyFromFile("adh_SetUpOfHoardingWardID"),mGetPropertyFromFile("adh_SetUpOfHoardingWardData"));
				String ward=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_SetUpOfHoardingWardID"));
				suoh_Ward.add(ward);
				mAssert(ward, mGetPropertyFromFile("adh_SetUpOfHoardingWardData"), "Set Up Of Hoarding Ward dose not match Actual::"+ward+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingWardData"));

				// Selecting and Capturing Road Type
				mWaitForVisible("id",  mGetPropertyFromFile("adh_SetUpOfHoardingRoadTypeID"));
				mSelectDropDown("id", mGetPropertyFromFile("adh_SetUpOfHoardingRoadTypeID"),mGetPropertyFromFile("adh_SetUpOfHoardingRoadTypeData"));
				String roadtype=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_SetUpOfHoardingRoadTypeID"));
				suoh_RoadType.add(roadtype);
				mAssert(roadtype, mGetPropertyFromFile("adh_SetUpOfHoardingRoadTypeData"), "Set Up Of Hoarding Road Type dose not match Actual::"+roadtype+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingRoadTypeData"));

				// Sending and Capturing East Landmark
				mWaitForVisible("id",  mGetPropertyFromFile("adh_SetUpOfHoardingEastLandmarkID"));
				mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingEastLandmarkID"),mGetPropertyFromFile("adh_SetUpOfHoardingEastLandmarkData"));
				String eastlandmark=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingEastLandmarkID"),"value");
				suoh_EastLandmark.add(eastlandmark);
				mAssert(eastlandmark, mGetPropertyFromFile("adh_SetUpOfHoardingEastLandmarkData"), "Set Up Of Hoarding East Landmark dose not match Actual::"+eastlandmark+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingEastLandmarkData"));

				// Sending and Capturing West Landmark
				mWaitForVisible("id",  mGetPropertyFromFile("adh_SetUpOfHoardingWestLandmarkID"));
				mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingWestLandmarkID"),mGetPropertyFromFile("adh_SetUpOfHoardingWestLandmarkData"));
				String westlandmark=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingWestLandmarkID"),"value");
				suoh_WestLandmark.add(westlandmark);
				mAssert(westlandmark, mGetPropertyFromFile("adh_SetUpOfHoardingWestLandmarkData"), "Set Up Of Hoarding West Landmark dose not match Actual::"+westlandmark+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingWestLandmarkData"));

				// Sending and Capturing North Landmark
				mWaitForVisible("id",  mGetPropertyFromFile("adh_SetUpOfHoardingNorthLandmarkID"));
				mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingNorthLandmarkID"),mGetPropertyFromFile("adh_SetUpOfHoardingNorthLandmarkData"));
				String northlandmark=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingNorthLandmarkID"),"value");
				suoh_NorthLandmark.add(northlandmark);
				mAssert(northlandmark, mGetPropertyFromFile("adh_SetUpOfHoardingNorthLandmarkData"), "Set Up Of Hoarding North Landmark dose not match Actual::"+northlandmark+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingNorthLandmarkData"));

				// Sending and Capturing South Landmark
				mWaitForVisible("id",  mGetPropertyFromFile("adh_SetUpOfHoardingSouthLandmarkID"));
				mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingSouthLandmarkID"),mGetPropertyFromFile("adh_SetUpOfHoardingSouthLandmarkData"));
				String southlandmark=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingSouthLandmarkID"),"value");
				suoh_SouthLandmark.add(southlandmark);
				mAssert(southlandmark, mGetPropertyFromFile("adh_SetUpOfHoardingSouthLandmarkData"), "Set Up Of Hoarding South Landmark dose not match Actual::"+southlandmark+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingSouthLandmarkData"));

				// Sending and Capturing GIS No
				mWaitForVisible("id",  mGetPropertyFromFile("adh_SetUpOfHoardingGISNoID"));
				mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingGISNoID"),mGetPropertyFromFile("adh_SetUpOfHoardingGISNoData"));
				String GISNo=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingGISNoID"),"value");
				suoh_GISNo.add(GISNo);
				mAssert(GISNo, mGetPropertyFromFile("adh_SetUpOfHoardingGISNoData"), "Set Up Of Hoarding GIS No dose not match Actual::"+GISNo+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingGISNoData"));

				// Sending and Capturing Plot No
				mWaitForVisible("id",  mGetPropertyFromFile("adh_SetUpOfHoardingPlotNoID"));
				mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingPlotNoID"),mGetPropertyFromFile("adh_SetUpOfHoardingPlotNoData"));
				String plotNo=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingPlotNoID"),"value");
				suoh_PlotNo.add(plotNo);
				mAssert(plotNo, mGetPropertyFromFile("adh_SetUpOfHoardingPlotNoData"), "Set Up Of Hoarding Plot No dose not match Actual::"+plotNo+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingPlotNoData"));

				// Sending and Capturing House no
				mWaitForVisible("id",  mGetPropertyFromFile("adh_SetUpOfHoardingHouseNoID"));
				mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingHouseNoID"),mGetPropertyFromFile("adh_SetUpOfHoardingHouseNoData"));
				String houseNo=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingHouseNoID"),"value");
				suoh_HouseNo.add(houseNo);
				mAssert(houseNo, mGetPropertyFromFile("adh_SetUpOfHoardingHouseNoData"), "Set Up Of Hoarding House No dose not match Actual::"+houseNo+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingHouseNoData"));

				// Sending and Capturing Hoarding Area
				mWaitForVisible("id",  mGetPropertyFromFile("adh_SetUpOfHoardingAreaID"));
				mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingAreaID"),mGetPropertyFromFile("adh_SetUpOfHoardingAreaData"));
				String area=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingAreaID"),"value");
				suoh_Area.add(area);
				mAssert(area, mGetPropertyFromFile("adh_SetUpOfHoardingAreaData"), "Set Up Of Hoarding Area dose not match Actual::"+area+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingAreaData"));

				// Sending and Capturing Society Name
				mWaitForVisible("id",  mGetPropertyFromFile("adh_SetUpOfHoardingSocietyNameID"));
				mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingSocietyNameID"),mGetPropertyFromFile("adh_SetUpOfHoardingSocietyNameData"));
				String societyname=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingSocietyNameID"),"value");
				suoh_SocietyName.add(societyname);
				mAssert(societyname, mGetPropertyFromFile("adh_SetUpOfHoardingSocietyNameData"), "Set Up Of Hoarding Society Name dose not match Actual::"+societyname+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingSocietyNameData"));

				// Sending and Capturing Road Name
				mWaitForVisible("id",  mGetPropertyFromFile("adh_SetUpOfHoardingRoadNameID"));
				mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingRoadNameID"),mGetPropertyFromFile("adh_SetUpOfHoardingRoadNameData"));
				String RoadName=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingRoadNameID"),"value");
				suoh_RoadName.add(RoadName);
				mAssert(RoadName, mGetPropertyFromFile("adh_SetUpOfHoardingRoadNameData"), "Set Up Of Hoarding Road Name dose not match Actual::"+RoadName+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingRoadNameData"));

				// Sending and Capturing Building Name
				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingBuildingNameID"),mGetPropertyFromFile("adh_SetUpOfHoardingBuildingNameData"));
				String BuildingName=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingBuildingNameID"),"value");
				suoh_BuildingName.add(BuildingName);
				mAssert(BuildingName, mGetPropertyFromFile("adh_SetUpOfHoardingBuildingNameData"), "Set Up Of Hoarding Building Name dose not match Actual::"+BuildingName+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingBuildingNameData"));

				// Sending and Capturing New Pin Code
				mWaitForVisible("id",  mGetPropertyFromFile("adh_SetUpOfHoardingNewPinCodeID"));
				mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingNewPinCodeID"),mGetPropertyFromFile("adh_SetUpOfHoardingNewPinCodeData"));
				String newPinCode=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingNewPinCodeID"),"value");
				suoh_NewPinCode.add(newPinCode);
				mAssert(newPinCode, mGetPropertyFromFile("adh_SetUpOfHoardingNewPinCodeData"), "Set Up Of Hoarding New Pincode dose not match Actual::"+newPinCode+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingNewPinCodeData"));


				// Uploading Image by using robot class
				mClick("id", mGetPropertyFromFile("adh_SetUpOfHoardingUploadPhotoBtnID"));
				mCustomWait(1000);

				StringSelection ss=new StringSelection("D:\\AutomationFramework\\ABMSmartScript\\uploads\\Khagaul.jpg");
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);	

				Robot robot=new Robot();
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				mCustomWait(2000);


			}
			else
			{
				// Selecting Existing Radio Button
				mWaitForVisible("id",  mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocationExistingID"));
				mClick("id", mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocationExistingID"));

				// Click on Add Location Button
				mWaitForVisible("linkText",  mGetPropertyFromFile("adh_SetUpOfHoardingBookingSearchLocationbtnID"));
				mClick("linkText", mGetPropertyFromFile("adh_SetUpOfHoardingBookingSearchLocationbtnID"));
				mCustomWait(1000);
				mWaitForVisible("linkText",  mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocSearchbtnPoPUpID"));
				mTakeScreenShot();
				// Search Criteria for Hoarding Selection
				SetUpOfHoardingLocationSearchCriria();
				mCustomWait(500);

				//Capturing hoarding Location
				mWaitForVisible("id",  mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocationAddressID"));
				String readLocationAddress=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocationAddressID"),"value");
				mAssert(readLocationAddress, mGetPropertyFromFile("adh_SetUpOfHoardingLocationAddressData"), "Set Up Of Hoarding New Pincode dose not match Actual::"+readLocationAddress+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingLocationAddressData"));

			}

			// Sending and Capturing Booking from date
			mDateSelect("id",mGetPropertyFromFile("adh_SetUpOfHoardingFromDateID"),mGetPropertyFromFile("adh_SetUpOfHoardingFromDateData"));
			String BookingfrDate=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingFromDateID"));
			System.out.println("Booking date is " +BookingfrDate);
			suoh_BookingfromDate.add(mGetPropertyFromFile("adh_SetUpOfHoardingFromDateData"));
			//mAssert(BookingfrDate, mGetPropertyFromFile("adh_SetUpOfHoardingFromDateData"), "Set Up Of Hoarding Applicant Hoarding Booking From date dose not match Actual::"+BookingfrDate+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingFromDateData"));

			// Sending and Capturing Booking to date
			mCustomWait(1000);
			mDateSelect("id",mGetPropertyFromFile("adh_SetUpOfHoardingToDateID"),mGetPropertyFromFile("adh_SetUpOfHoardingToDateData"));
			String BookingtoDate=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingFromDateID"), "value");
			mAssert(BookingtoDate, mGetPropertyFromFile("adh_SetUpOfHoardingToDateData"), "Set Up Of Hoarding Applicant Hoarding Booking To date dose not match Actual::"+BookingtoDate+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingToDateData"));
			suoh_BookingtoDate.add(mGetPropertyFromFile("adh_SetUpOfHoardingToDateData"));

			// Sending and Capturing Remarks
			mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingRemarksID"),mGetPropertyFromFile("adh_SetUpOfHoardingRemarksData"));
			String adh_SetUpOfHoardingRemarks=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingRemarksID"), "value");
			suoh_ApplicantRemarks.add(adh_SetUpOfHoardingRemarks);
			mAssert(adh_SetUpOfHoardingPincode, mGetPropertyFromFile("adh_SetUpOfHoardingRemarksData"), "Set Up Of Hoarding Applicant Remarks dose not match Actual::"+adh_SetUpOfHoardingPincode+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingRemarksData"));

			mTakeScreenShot();

			// Click in Add Hoarding Button
			mWaitForVisible("linkText",mGetPropertyFromFile("adh_SetUpOfHoardingAddDetID"));
			mClick("linkText", mGetPropertyFromFile("adh_SetUpOfHoardingAddDetID"));

			// Sending and Capturing Hoarding description
			mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingHoardingDescriptionID"),mGetPropertyFromFile("adh_SetUpOfHoardingHoardingDescriptionData"));
			String HoardingDescription=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingHoardingDescriptionID"),"value");
			suoh_HoardingDescription.add(HoardingDescription);
			mAssert(HoardingDescription, mGetPropertyFromFile("adh_SetUpOfHoardingHoardingDescriptionData"), "Set Up Of Hoarding Hoarding Description dose not match Actual::"+HoardingDescription+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingHoardingDescriptionData"));

			// Selecting and Capturing Hoarding Type
			mSelectDropDown("id", mGetPropertyFromFile("adh_SetUpOfHoardingHoardingTypeID"),mGetPropertyFromFile("adh_SetUpOfHoardingHoardingTypeData"));
			String HoardingType=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_SetUpOfHoardingHoardingTypeID"));
			suoh_HoardingType.add(HoardingType);
			mAssert(HoardingType, mGetPropertyFromFile("adh_SetUpOfHoardingHoardingTypeData"), "Set Up Of Hoarding Type dose not match Actual::"+HoardingType+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingHoardingTypeData"));

			// Selecting and Capturing Hoarding Type1
			mSelectDropDown("id", mGetPropertyFromFile("adh_SetUpOfHoardingHoardingType1ID"),mGetPropertyFromFile("adh_SetUpOfHoardingHoardingType1Data"));
			String HoardingType1=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_SetUpOfHoardingHoardingType1ID"));
			suoh_HoardingType1.add(HoardingType1);
			mAssert(HoardingType1, mGetPropertyFromFile("adh_SetUpOfHoardingHoardingType1Data"), "Set Up Of Hoarding Type 1 dose not match Actual::"+HoardingType1+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingHoardingType1Data"));

			// Sending and Capturing Hoarding Length
			mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingHoardingLengthID"),mGetPropertyFromFile("adh_SetUpOfHoardingHoardingLengthData"));
			String lenghth=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingHoardingLengthID"), "value");
			suoh_Length.add(lenghth);
			mAssert(lenghth, mGetPropertyFromFile("adh_SetUpOfHoardingHoardingLengthData"), "Set Up Of Hoarding Lenghth dose not match Actual::"+lenghth+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingHoardingBreadthData"));

			// Sending and Capturing Hoarding Breadth
			mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingHoardingBreadthID"),mGetPropertyFromFile("adh_SetUpOfHoardingHoardingLengthData"));
			String Breadth=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingHoardingBreadthID"), "value");
			suoh_Breadth.add(Breadth);
			mAssert(Breadth, mGetPropertyFromFile("adh_SetUpOfHoardingHoardingBreadthData"), "Set Up Of Hoarding Breadth dose not match Actual::"+Breadth+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingHoardingBreadthData"));

			// Sending and Capturing Hoarding Elevation
			mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingHoardingElevationID"),mGetPropertyFromFile("adh_SetUpOfHoardingHoardingElevationData"));
			String elevation=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingHoardingElevationID"), "value");
			suoh_Elevation.add(elevation);
			mAssert(elevation, mGetPropertyFromFile("adh_SetUpOfHoardingHoardingElevationData"), "Set Up Of Hoarding Elevation dose not match Actual::"+elevation+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingHoardingElevationData"));


			/*mWaitForVisible("name", mGetPropertyFromFile("adh_SetUpOfHoardingHoardingPopUpGISnoID"));
		mSendKeys("name", mGetPropertyFromFile("adh_SetUpOfHoardingHoardingPopUpGISnoID"),mGetPropertyFromFile("adh_SetUpOfHoardingHoardingPopUpGISnoData"));
		String GisNo=mGetText("name", mGetPropertyFromFile("adh_SetUpOfHoardingHoardingPopUpGISnoID"), "value");
		suoh_GISNOPOPUP.add(GisNo);
		mAssert(GisNo, mGetPropertyFromFile("adh_SetUpOfHoardingHoardingPopUpGISnoData"), "Set Up Of Hoarding GIS No dose not match Actual::"+GisNo+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingHoardingPopUpGISnoData"));*/

			// capturing Hoarding amount
			String amt=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingHoardingHoradingAmountID"), "value");
			System.out.println("Amt is"+amt);
			suoh_AmountPopUp.add(amt);
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("adh_SetUpOfHoardingsubmitBtnID"));
			mCustomWait(1000);

			// Reading Hoarding details Tabel
			adh_mDynamicAddHoardingDeatilsTableGrid();

			// Capturing Pop up message  
			mWaitForVisible("css",mGetPropertyFromFile("adh_SetUpOfHoardingsubmitPopUpCloseID"));
			String PopUpmsg=mGetText("css",mGetPropertyFromFile("adh_SetUpOfHoardingsubmitPopUpMsgID"));
			System.out.println("Msg is ::"+PopUpmsg);
			mAssert(PopUpmsg, mGetPropertyFromFile("adh_SetUpOfHoardingHoardingPopUpmsgData"), "Set Up Of Hoarding Add Horading Detail Pop Up Message not match Actual::"+PopUpmsg+"Expected::"+mGetPropertyFromFile("adh_SetUpOfHoardingHoardingPopUpmsgData"));

			// Click to close Pop up
			mCustomWait(1000);
			mClick("css",mGetPropertyFromFile("adh_SetUpOfHoardingsubmitPopUpCloseID"));

			mscroll(0, 250);
			mCustomWait(500);

			// Uploading Document
			docUpload("xpath", mGetPropertyFromFile("adh_SetUpOfHoardingUploadtableID"), "upldDocStatus");
			mCustomWait(1500);

			mGenericWait();
			mTakeScreenShot();

			//Logic For Application Time Payment yes or no
			if(mGetPropertyFromFile("adh_ApplicationTimePaymentYesNoData").equalsIgnoreCase("yes"))  
			{
				payment("paymentMode","byBankOrULB");

				mWaitForVisible("css",mGetPropertyFromFile("adh_SetUpOfHoardingSubmitBtnID"));
				mCustomWait(1000);
				mClick("css",mGetPropertyFromFile("adh_SetUpOfHoardingSubmitBtnID"));
				mTakeScreenShot();
			}

			else

			{	
				mWaitForVisible("css",mGetPropertyFromFile("adh_SetUpOfHoardingSubmitBtnID"));
				mCustomWait(1000);
				mClick("css",mGetPropertyFromFile("adh_SetUpOfHoardingSubmitBtnID"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in setUpOfHording_Script");
		}

	}


	// Set Up Of Hoarding Proceed Method Script 
	public void setUpOfHordingProceed(){
		try{

			//Capturing Application No
			String appNum =mGetApplicationNo("css",mGetPropertyFromFile("adh_SetUpOfHoardingAppNoID"));
			mAppNoArray("css",mGetPropertyFromFile("adh_SetUpOfHoardingAppNoID"));
			System.out.println("POP UP msg at Set Up of Hoarding is::::"+appNum);

			System.out.println("application Number in proceed Arraylist::::"+applicationNo);
			mCustomWait(1000);
			System.out.println("application Number in proceed ::::"+appNo);
			System.out.println("application Number (ADHserviceName) ::::"+appNo);
			ApplicationNoADH.add(appNo);
			System.out.println("application Number (ApplicationNoADH) ::::"+ApplicationNoADH);

			mWaitForVisible("id", mGetPropertyFromFile("adh_SetUpOfHoardingProceedID"));
			mTakeScreenShot();
			mCustomWait(1000);
			appSubmitMsg = mGetText("css", mGetPropertyFromFile("adh_SetUpOfHoardingAppNoID"));
			System.out.println("App submit msg"+appSubmitMsg);
			strippedString = appSubmitMsg.replace(appNo, "");
			System.out.println("Stripped msg"+strippedString);
			mAssert(mGetPropertyFromFile("adh_SetUpOfHoardingActualMsg"),strippedString,"Booking of Hoarding proceed message does not match, actual : "+mGetPropertyFromFile("adh_SetUpOfHoardingActualMsg")+"expected : "+strippedString);
			mClick("id", mGetPropertyFromFile("adh_SetUpOfHoardingProceedID"));
			mCustomWait(2000);	
			//Logic for downloading Challan if Application Time payment Yes
			if(mGetPropertyFromFile("adh_ApplicationTimePaymentYesNoData").equalsIgnoreCase("yes"))  {
				mChallanPDFReader();
			}
		}
		catch(Exception e )
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in bookingOfHoardingProceed script");
		}	
	}




	//**Approval Letter Scripting for  Printing  for Agency Contract and All Services
	public void ApprovalLetter_script(){
		try{
			mGenericWait();
			mNavigation("adh_Advertising&HoardingID","adh_ReportsID","adh_ApprovalLetterID");
			mGenericWait();
			//Logic For If Agency Contract to Click and Print Contract Note on basis on Type of Execution is Dependent
			if(Flag==1) 
			{			
				if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent")){

					mWaitForVisible("id", mGetPropertyFromFile("adh_ApprradioContractNoID"));					
					mClick("id", mGetPropertyFromFile("adh_ApprradioContractNoID"));
					mSendKeys("id", mGetPropertyFromFile("adh_ApprContractNoID"), adhContractNo.get(CurrentinvoCount));
					mGenericWait();

					mTakeScreenShot();
					mClick("css",mGetPropertyFromFile("adh_ApprPrintButtonID"));
					//Downloading Contract Note
					mChallanPDFReader();

					//Reading PDF Pattrn for Assertion of Contract Note
					api.PdfPatterns.ADH_Agency_Contract_Note_Pdf(pdfoutput);
				}
				else{
					//Logic For If Agency Contract to Click and Print Contract Note on basis on Type of Execution is Individual
					mWaitForVisible("id", mGetPropertyFromFile("adh_ApprradioContractNoID"));					
					mClick("id", mGetPropertyFromFile("adh_ApprradioContractNoID"));
					mSendKeys("id", mGetPropertyFromFile("adh_ApprContractNoID"), mGetPropertyFromFile("contractNo"));
					mGenericWait();

					mTakeScreenShot();
					mClick("css",mGetPropertyFromFile("adh_ApprPrintButtonID"));
					//Downloading Contract Note
					mChallanPDFReader();
					//Reading PDF Pattrn for Assertion of Contract Note
					api.PdfPatterns.ADH_Agency_Contract_Note_Pdf(pdfoutput);

				}
			}
			else {

				//Logic For Services to Click and Print Approval Letter on basis on Type of Execution is Dependent and Individual

				mWaitForVisible("id", mGetPropertyFromFile("adh_ApprRadioAppNoID"));					
				mClick("id", mGetPropertyFromFile("adh_ApprRadioAppNoID"));
				IndOrDep("id", "adh_ApprApplicationNoID", "applicationNo"); ;
				mGenericWait();

				mTakeScreenShot();
				mClick("css",mGetPropertyFromFile("adh_ApprPrintButtonID"));
				//Downloading Approval Letter
				mChallanPDFReader();
				//Logic For Letter Pdf Pattrn Reading on The basis of Service name
				if(ARR_ServiceNameADH.get(CurrentinvoCount).equalsIgnoreCase("Renewal Of Advertisement Contract")){
					api.PdfPatterns.ADH_RenewalOfAdvertisment_Approval_Letter_Pdf(pdfoutput);
				}
				else if(ARR_ServiceNameADH.get(CurrentinvoCount).equalsIgnoreCase("Booking of Hoarding for Display of Advertisement"))
				{
					api.PdfPatterns.ADH_Approval_Letter_Pdf(pdfoutput);
				}
				else if(ARR_ServiceNameADH.get(CurrentinvoCount).equalsIgnoreCase("Setup of Hoarding for Display of Advertisement"))
				{
					api.PdfPatterns.ADH_SetUp_Advertisment_Approval_Letter_Pdf(pdfoutput);
				}
				else{
					System.out.println(ARR_ServiceNameADH);
				}
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in ApprovalLetter_script");

		}	
	}	


	//**Approval Letter Scripting for  Printing  for Agency Contract and All Services
	public void ContractLetter_script(){
		try{
			mGenericWait();
			mNavigation("adh_Advertising&HoardingID","adh_ReportsID","adh_ApprovalLetterID");
			mGenericWait();
			//Logic For If Agency Contract to Click and Print Contract Note on basis on Type of Execution is Dependent

			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent")){

				mWaitForVisible("id", mGetPropertyFromFile("adh_ApprradioContractNoID"));					
				mClick("id", mGetPropertyFromFile("adh_ApprradioContractNoID"));
				mSendKeys("id", mGetPropertyFromFile("adh_ApprContractNoID"), adhContractNo.get(CurrentinvoCount));
				mGenericWait();

				mTakeScreenShot();
				mClick("css",mGetPropertyFromFile("adh_ApprPrintButtonID"));
				//Downloading Contract Note
				mChallanPDFReader();

				//Reading PDF Pattrn for Assertion of Contract Note
				api.PdfPatterns.ADH_Agency_Contract_Note_Pdf(pdfoutput);
			}
			else{
				//Logic For If Agency Contract to Click and Print Contract Note on basis on Type of Execution is Individual
				mWaitForVisible("id", mGetPropertyFromFile("adh_ApprradioContractNoID"));					
				mClick("id", mGetPropertyFromFile("adh_ApprradioContractNoID"));
				mSendKeys("id", mGetPropertyFromFile("adh_ApprContractNoID"), mGetPropertyFromFile("contractNo"));
				mGenericWait();

				mTakeScreenShot();
				mClick("css",mGetPropertyFromFile("adh_ApprPrintButtonID"));
				//Downloading Contract Note
				mChallanPDFReader();
				//Reading PDF Pattrn for Assertion of Contract Note
				api.PdfPatterns.ADH_Agency_Contract_Note_Pdf(pdfoutput);
			}							
		}
		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in ApprovalLetter_script");

		}	
	}	 



	// Bill Printing Search criteria	
	public void billPrintingSearchCriteria(){
		try{
			// Search Criteria if on Basis on Application No if Type of Execution is Dependent
			if(mGetPropertyFromFile("adh_BprApplicationNo").equalsIgnoreCase("yes"))
			{
				if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent")) 
				{
					mSendKeys("id", mGetPropertyFromFile("adh_BprApplicantionNoid"),applicationNo.get(CurrentinvoCount));
				}

				else
				{
					// Search Criteria if on Basis on Application No if Type of Execution is Individual
					IndOrDep("id", "adh_BprApplicantionNoid", "applicationNo"); 
				}
			}

			// Search Criteria if on Basis on Tenant No if Type of Execution is Dependent
			if(mGetPropertyFromFile("adh_BprTenantNo").equalsIgnoreCase("yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("adh_BprTenantNoid"), mGetPropertyFromFile("adh_BprTenantNoData")); 
			}
			// Search Criteria if on Basis on Tenant Name if Type of Execution is Dependent
			if(mGetPropertyFromFile("adh_BprTenantName").equalsIgnoreCase("yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("adh_BprTenantNameid"), mGetPropertyFromFile("adh_BprTenantNameData"));
			}
			// Search Criteria if on Basis on From date to To date if Type of Execution is Dependent
			if(mGetPropertyFromFile("adh_BprFrom&ToDate").equalsIgnoreCase("yes"))
			{
				mDateSelect("id", mGetPropertyFromFile("adh_BprFromDateid"), mGetPropertyFromFile("adh_BprFromDateData"));
				mCustomWait(1000);
				mDateSelect("id", mGetPropertyFromFile("adh_BprToDateid"), mGetPropertyFromFile("adh_BprToDateData"));
				mCustomWait(1000);
			}
			// Search Criteria if on Basis on Contract No if Type of Execution is Individual
			if(mGetPropertyFromFile("adh_BprContratNo").equalsIgnoreCase("yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("adh_BprContractid"), mGetPropertyFromFile("AgencyContractNo"));
			}
			mTakeScreenShot();
			mCustomWait(1000);
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in billPrintingSearchCriteria");
		}			
	}

	// Bill Generation Search criteria		
	public void billGenerationSearchCriria(){
		try{

			if(mGetPropertyFromFile("adh_BgApplicationNumInput").equalsIgnoreCase("yes"))
			{
				// Search Criteria if on Basis on Application No if Type of Execution is Dependent
				if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent")) 
				{
					mSendKeys("id", mGetPropertyFromFile("adh_BgAgencyorApplicaNumInputid"),applicationNo.get(CurrentinvoCount));
				}
				else
				{
					// Search Criteria if on Basis on Application No if Type of Execution is Individual
					IndOrDep("id", "adh_BgAgencyorApplicaNumInputid","applicationNo");
				}
			}
			// Search Criteria if on Basis on Tenant No if Type of Execution is Dependent
			if(mGetPropertyFromFile("adh_BgTenantNumInput").equalsIgnoreCase("yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("adh_BgTenantNumInputid"),mGetPropertyFromFile("adh_BgTenantNumInputData"));
			}
			// Search Criteria if on Basis on Tenant Name if Type of Execution is Dependent
			if(mGetPropertyFromFile("adh_BgTenantNameInput").equalsIgnoreCase("yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("adh_BgTenantNameInputid"),mGetPropertyFromFile("adh_BgTenantNameInputData"));
			}

			if(mGetPropertyFromFile("adh_BgContractNoInput").equalsIgnoreCase("yes"))
			{
				if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent")) 
				{// Search Criteria if on Basis on Contract No if Type of Execution is Dependent
					mSendKeys("id", mGetPropertyFromFile("adh_BgContractNumInputid"),adhContractNo.get(CurrentinvoCount));
				}
				else
				{// Search Criteria if on Basis on Contract No if Type of Execution is Individual
					mSendKeys("id", mGetPropertyFromFile("adh_BgContractNumInputid"), mGetPropertyFromFile("AgencyContractNo"));
				}
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in billGenerationSearchCriria");
		}			
	}	


	public void adh_checklist_verification_asserion(){
		try{

			// Capturing Applicant name at Checklist verification form
			String clvApplicantName=mGetText("css", mGetPropertyFromFile("ClvApplicantNameid"));
			System.out.println("Name of Applicant in checklist verification form is : " +clvApplicantName);
			clv_ApplicantName.add(clvApplicantName.trim());

			// Capturing Applicantion No at Checklist verification form
			String clvApplicantionNo=mGetText("css", mGetPropertyFromFile("ClvApplicantionNoid"));
			System.out.println("Application No in checklist verification form is : " +clvApplicantionNo);
			clv_ApplicanyionNo.add(clvApplicantionNo.trim());

			// Capturing Service Name at Checklist verification form
			String clvServicenameName=mGetText("css", mGetPropertyFromFile("ClvAServiceNameid"));
			System.out.println("Service Name in checklist verification form is : " +clvServicenameName);
			clv_ServicenameName.add(clvServicenameName.trim());

			// Capturing Application Date at Checklist verification form
			String clvApplicantDate=mGetText("css", mGetPropertyFromFile("ClvApplicantionDateid"));
			System.out.println("Application date in checklist verification form is : " +clvApplicantDate);
			clv_ApplicantionDate.add(clvApplicantDate.trim());

			//Logic For Assertion at checklist Verification Form
			switch(clvServicenameName) 
			{
			case "Booking of Hoarding for Display of Advertisement" :
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					mAssert(clvServicenameName, ADHserviceName, "Name of Service in checklist verification form doen not match with the expected::: "+clvServicenameName+"Actual:::"+ADHserviceName);					
					mAssert(clv_ApplicantName.get(CurrentinvoCount),adh_ApplicantFullname.get(CurrentinvoCount), "Applicant Full Name in checklist verification form does not match with the expected::: "+adh_ApplicantFullname.get(CurrentinvoCount)+"Actual:::"+clv_ApplicantName.get(CurrentinvoCount));
					mAssert(clv_ApplicanyionNo.get(CurrentinvoCount), ApplicationNoADH.get(CurrentinvoCount), "Application No in checklist verification form does not match with the Expected"+ApplicationNoADH+",Actual"+clv_ApplicanyionNo);	
				}
				break;

			case  "Renewal Of Advertisement Contract" :
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					mAssert(clvServicenameName, ADHserviceName, "Name of Service in checklist verification form doen not match with the expected::: "+clvServicenameName+"Actual:::"+ADHserviceName);					
					mAssert(clv_ApplicantName.get(CurrentinvoCount),adh_ApplicantFullname.get(CurrentinvoCount), "Applicant Full Name in checklist verification form does not match with the expected::: "+adh_ApplicantFullname.get(CurrentinvoCount)+"Actual:::"+clv_ApplicantName.get(CurrentinvoCount));
					mAssert(clv_ApplicanyionNo.get(CurrentinvoCount), ApplicationNoADH.get(CurrentinvoCount), "Application No in checklist verification form does not match with the Expected"+ApplicationNoADH+",Actual"+clv_ApplicanyionNo);
				}
				break;

			case "Setup of Hoarding for Display of Advertisement" :
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					mAssert(clvServicenameName, ADHserviceName, "Name of Service in checklist verification form doen not match with the expected::: "+clvServicenameName+"Actual:::"+ADHserviceName);					
					mAssert(clv_ApplicantName.get(CurrentinvoCount),adh_ApplicantFullname.get(CurrentinvoCount), "Applicant Full Name in checklist verification form does not match with the expected::: "+adh_ApplicantFullname.get(CurrentinvoCount)+"Actual:::"+clv_ApplicantName.get(CurrentinvoCount));
					mAssert(clv_ApplicanyionNo.get(CurrentinvoCount), ApplicationNoADH.get(CurrentinvoCount), "Application No in checklist verification form does not match with the Expected"+ApplicationNoADH+",Actual"+clv_ApplicanyionNo);	
				}

				break;
			}


		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();	
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Checklist Verification(Assertion) adh_checklist_verification_asserion method failed");
		}
	}



	//Agency Contract Script 
	public void agencyContract(){
		try {


			//Navigating from Rent & Lease to Transactions to Tenant Contract
			mCustomWait(1000);;
			mNavigation("adh_AcgAdvtNHoardingLinkid", "adh_AcgTransactionLinkid", "adh_AcgAgencyContractLinkid");

			//Capturing ULB Name
			ULBName=mGetPropertyFromFile("municipality");
			System.out.println("ulb is : "+ULBName); 
			ADHserviceName=mGetText("css", mGetPropertyFromFile("adh_AcgServiceNameTitleid"));
			System.out.println("Service Name  : "+ADHserviceName); 

			// logic to add Agency Contract
			if(mGetPropertyFromFile("adh_AcgAddAgencyContract").equalsIgnoreCase("Yes") && mGetPropertyFromFile("adh_AcgEditAgencyContract").equalsIgnoreCase("No") && mGetPropertyFromFile("adh_AcgViewAgencyContract").equalsIgnoreCase("No"))
			{	
				//Add Agency Contract Button
				mWaitForVisible("css", mGetPropertyFromFile("adh_AcgAddAgencyContrctBtnid"));
				mClick("css", mGetPropertyFromFile("adh_AcgAddAgencyContrctBtnid"));

				//Search Agency
				mWaitForVisible("xpath", mGetPropertyFromFile("adh_AcgfinalSearchAgencyBtnid"));
				mClick("xpath", mGetPropertyFromFile("adh_AcgfinalSearchAgencyBtnid"));


				//Sending Agency Number
				mWaitForVisible("id", mGetPropertyFromFile("adh_AcgAgencyAgencyNoid"));
				mSendKeys("id", mGetPropertyFromFile("adh_AcgAgencyAgencyNoid"), mGetPropertyFromFile("adh_AcgAgencyNoData"));

				//click on Agency Button
				mWaitForVisible("css", mGetPropertyFromFile("adh_AcgSearchAgencyBtnid"));
				mClick("css", mGetPropertyFromFile("adh_AcgSearchAgencyBtnid"));

				//Selecting Agency no
				mWaitForVisible("xpath", mGetPropertyFromFile("adh_AcgAgencyRadioid"));
				mClick("xpath", mGetPropertyFromFile("adh_AcgAgencyRadioid"));
				mTakeScreenShot();

				//Agency Selection Submit Button
				mWaitForVisible("css", mGetPropertyFromFile("adh_AcgAgencySubBtnid"));
				mClick("css", mGetPropertyFromFile("adh_AcgAgencySubBtnid"));

				// Capturing Agency Name
				String AgencyName=mGetText("id", mGetPropertyFromFile("adh_AcgAgencyNameid"), "value");
				System.out.println("Agency name is : "+AgencyName);
				Agc_AgencyName.add(AgencyName);


				// Capturing Agency No
				String AgencyNo=mGetText("id", mGetPropertyFromFile("adh_AcgAgencyNoid"), "value");
				System.out.println("Agency No is : "+AgencyNo);	
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					mAssert(AgencyNo, mGetPropertyFromFile("adh_AcgAgencyNoData"), "Agency No does not match in agency Contract Details with actual"+AgencyNo + "Expected:::"+mGetPropertyFromFile("adh_AcgAgencyNoData"));
				}
				log.info("Agency no is : "+AgencyNo);


				//Sending and Capturing Tender Reference
				mSendKeys("id", mGetPropertyFromFile("adh_AcgTenderRefid"), mGetPropertyFromFile("adh_AcgTenderRefData"));
				String TenderRef= mGetText("id", mGetPropertyFromFile("adh_AcgTenderRefid"), "value");
				System.out.println("Tender Reference no is"+TenderRef);
				mAssert(TenderRef, mGetPropertyFromFile("adh_AcgTenderRefData"), "Tender Reference No dose not match with the actual ::"+TenderRef+"Expected ::"+mGetPropertyFromFile("adh_AcgTenderRefData"));
				Acg_TenderRef.add(TenderRef);


				//Sending and Capturing Tender Date
				mDateSelect("id",mGetPropertyFromFile("adh_AcgTenderDateid"), mGetPropertyFromFile("adh_AcgTenderDateData"));
				String TenderDate=mGetText("id", mGetPropertyFromFile("adh_AcgTenderDateid"), "value");
				System.out.println("Tender Date is"+TenderDate);
				Acg_TenderDate.add(TenderDate);
				mAssert(TenderDate, mGetPropertyFromFile("adh_AcgTenderDateData"), "Tender Date dose not match with the actual ::"+TenderDate+"Expected ::"+mGetPropertyFromFile("adh_AcgTenderDateData"));

				//Sending and Capturing Contract Date
				startDate=mGetPropertyFromFile("adh_AcgContractDateData");
				mDateSelect("id",mGetPropertyFromFile("adh_AcgContractDateid"), mGetPropertyFromFile("adh_AcgContractDateData"));
				String ContractDate=mGetText("id", mGetPropertyFromFile("adh_AcgContractDateid"), "value");
				System.out.println("Contract Date is"+ContractDate);
				Acg_ContractDate.add(ContractDate);
				mAssert(ContractDate, mGetPropertyFromFile("adh_AcgContractDateData"), "Contract Date dose not match with the actual ::"+TenderRef+"Expected ::"+mGetPropertyFromFile("adh_AcgContractDateData"));

				//Sending and Capturing Contract From Date
				endDate=mGetPropertyFromFile("adh_AcgContractFromDateData");
				mDateSelect("id",mGetPropertyFromFile("adh_AcgContractFromDateid"), mGetPropertyFromFile("adh_AcgContractFromDateData"));
				String ContractFrDate=mGetText("id", mGetPropertyFromFile("adh_AcgContractFromDateid"), "value");
				System.out.println("Contract From Date is"+ContractFrDate);
				mAssert(ContractFrDate, mGetPropertyFromFile("adh_AcgContractFromDateData"), "Contract From Date dose not match with the actual ::"+ContractFrDate+"Expected ::"+mGetPropertyFromFile("adh_AcgContractFromDateData"));
				Acg_ContractFromDate.add(ContractFrDate);

				//Sending and Capturing Contract To Date
				mDateSelect("id",mGetPropertyFromFile("adh_AcgContractToDateid"), mGetPropertyFromFile("adh_AcgContractToDateData"));
				String ContractToDate=mGetText("id", mGetPropertyFromFile("adh_AcgContractToDateid"), "value");
				System.out.println("Contract To Date is"+ContractToDate);
				mAssert(ContractToDate, mGetPropertyFromFile("adh_AcgContractToDateData"), "Contract to Date dose not match with the actual ::"+TenderRef+"Expected ::"+mGetPropertyFromFile("adh_AcgContractToDateData"));
				Acg_ContractToDate.add(ContractToDate);
				mCustomWait(1000);


				differenceDays();


				//Sending and Capturing Contract Amount
				if(mGetPropertyFromFile("adh_AcgTaxDescrptnYesN0Data").equalsIgnoreCase("yes")){

					mCustomWait(1000);
					mSendKeys("id", mGetPropertyFromFile("adh_AcgContractAmountid"), mGetPropertyFromFile("adh_AcgContractAmountData"));
					String ContractAmt=mGetText("id", mGetPropertyFromFile("adh_AcgContractAmountid"), "value");
					System.out.println("Contract Amount is"+ContractAmt);
					mAssert(ContractAmt, mGetPropertyFromFile("adh_AcgContractAmountData"), "Contract Amount dose not match with the actual ::"+TenderRef+"Expected ::"+mGetPropertyFromFile("adh_AcgContractAmountData"));
					Acg_ContractAmount.add(ContractAmt);
				}


				//Logic for Sending and Capturing Contract Discount Amount
				if(mGetPropertyFromFile("adh_AcgTaxDiscountYesNoData").equalsIgnoreCase("yes")){
					mCustomWait(1000);
					mSendKeys("id", mGetPropertyFromFile("adh_AcgContractDiscountValueid"), mGetPropertyFromFile("adh_AcgContractDiscountValueData"));
					String ContractDiscountAmt=mGetText("id", mGetPropertyFromFile("adh_AcgContractDiscountValueid"), "value");
					System.out.println("Contract Discount Amount is"+ContractDiscountAmt);
					Acg_DiscountAmount.add(ContractDiscountAmt);
					mAssert(ContractDiscountAmt, mGetPropertyFromFile("adh_AcgContractDiscountValueData"), "Contract Discount Amount dose not match with the actual ::"+TenderRef+"Expected ::"+mGetPropertyFromFile("adh_AcgContractDiscountValueData"));

				}


				//Capturing Round Off Amount
				String ROA=mGetText("id",mGetPropertyFromFile("adh_AcgContractRoundoffAmountid"),"value");
				TC_RoundOffAmount.add(ROA);
				System.out.println("round off amount is : "+ ROA);


				//Sending and Capturing Contract Remarks
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("adh_AcgContractRemarksid"), mGetPropertyFromFile("adh_AcgContractRemarksData"));
				String ContractRemarks=mGetText("id", mGetPropertyFromFile("adh_AcgContractRemarksid"), "value");
				TC_ContractRemarks.add(ContractRemarks);
				mCustomWait(1000);
				mTakeScreenShot();

				//Capturing Payable Amount
				String Payable=mGetText("id",mGetPropertyFromFile("adh_AcgContractPayableAmountid"),"value");
				Payable=Payable.replace(".00", " ");
				TC_PayableAmount.add(Payable);
				System.out.println("payable amount is : "+ Payable);
				int payble1=Integer.parseInt(Payable.trim());
				updatedpayble_list=payble1;
				System.out.println("updatedpayble_list=>"+updatedpayble_list);
				mCustomWait(1000);
				mscroll(0,500);			
				//Click on Amount Bifurcation Button
				mCustomWait(1000);
				mClick("xpath",  mGetPropertyFromFile("adh_AcgAmountBifurcationBtnid"));

				//Logic for Add multiple Tax descriptions and Amount data
				String TaxDescription= mGetPropertyFromFile("adh_AcgTaxDescrptnData");
				String TaxAmt= mGetPropertyFromFile("adh_AcgTaxAmountData");

				ArrayList TaxDescriptionList = new ArrayList(Arrays.asList(TaxDescription.split(",")));
				ArrayList TaxAmtList = new ArrayList(Arrays.asList(TaxAmt.split(",")));


				for(int i=0; i<TaxDescriptionList.size();i++)
				{
					// Click on Add Button
					mWaitForVisible("id", mGetPropertyFromFile("adh_AcgTaxDescrptnAddBtnid"));
					mClick("id",  mGetPropertyFromFile("adh_AcgTaxDescrptnAddBtnid"));


					//Sending  Tax Description
					mWaitForVisible("id", mGetPropertyFromFile("adh_AcgTaxDescrptnid"));
					mCustomWait(1000);
					mSelectDropDown("id",  mGetPropertyFromFile("adh_AcgTaxDescrptnid"),TaxDescriptionList.get(i).toString());
					String TaxDescription1=mCaptureSelectedValue("id",  mGetPropertyFromFile("adh_AcgTaxDescrptnid"));
					Agc_TaxDescription.add(TaxDescription1);

					//Sending Tax amount
					mCustomWait(1000);
					mSendKeys("id",  mGetPropertyFromFile("adh_AcgTaxAmountid"), TaxAmtList.get(i).toString());
					String TaxDescAmt=mGetText("id",  mGetPropertyFromFile("adh_AcgTaxAmountid"), "value");
					System.out.println("Tax Amount is"+TaxDescAmt);
					Agc_TaxAmount.add(TaxDescAmt);
					mTakeScreenShot();
					mTab("id", mGetPropertyFromFile("adh_AcgTaxAmountid")); 

				}

				//Logic For Adding Discount if Applicable in Amount Bifurcation
				if(mGetPropertyFromFile("adh_AcgTaxDiscountYesNoData").equalsIgnoreCase("yes")){

					//Click on Add Button
					mWaitForVisible("id", mGetPropertyFromFile("adh_AcgTaxDescrptnAddBtnid"));
					mCustomWait(1000);
					mTakeScreenShot();
					mClick("id",  mGetPropertyFromFile("adh_AcgTaxDescrptnAddBtnid"));

					//Selecting and Capturing Discount Description
					mWaitForVisible("id", mGetPropertyFromFile("adh_AcgTaxDescrptnid"));
					mCustomWait(1000);
					mSelectDropDown("id",  mGetPropertyFromFile("adh_AcgTaxDescrptnid"), mGetPropertyFromFile("adh_AcgTaxDiscountDescData"));
					String TaxDescription2=mCaptureSelectedValue("id",  mGetPropertyFromFile("adh_AcgTaxDescrptnid"));
					AgC_DiscountDescription.add(TaxDescription2);

					//Sending Discount amount
					mCustomWait(1000);
					mSendKeys("id",  mGetPropertyFromFile("adh_AcgTaxAmountid"), mGetPropertyFromFile("adh_AcgContractDiscountValueData"));
					String TaxDescAmt1=mGetText("id",  mGetPropertyFromFile("adh_AcgTaxAmountid"), "value");
					System.out.println("Tax Amount is"+TaxDescAmt1);
					Agc_TaxAmount.add(TaxDescAmt1);
					mTakeScreenShot();
					mTab("id", mGetPropertyFromFile("adh_AcgTaxAmountid")); 


				}

				// Capturing Amount Bifurcation total Amount
				String abta=mGetText("id",mGetPropertyFromFile("adh_AcgAmountBifurcationTotalAmountid"),"value");
				System.out.println("total amount is : "+ abta);	
				System.out.println("totalamount is " +mGetPropertyFromFile("adh_AcgTotalAmountData")); 
				mAssert(abta, mGetPropertyFromFile("adh_AcgTotalAmountData"), "Actual  :"+abta+" Expected  :"+mGetPropertyFromFile("adh_AcgTotalAmountData"));
				mTakeScreenShot();

				//Click on Amount Bifurcation Submit button
				mWaitForVisible("css", mGetPropertyFromFile("adh_AcgAmountBifurcationSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("adh_AcgAmountBifurcationSubBtnid"));

				//Click on Payment Schedule Button
				mWaitForVisible("xpath", mGetPropertyFromFile("adh_AcgSelctPaymentScheduleBtnid"));
				mCustomWait(1000);
				mClick("xpath",  mGetPropertyFromFile("adh_AcgSelctPaymentScheduleBtnid"));

				//Selecting Payment Schedule
				mWaitForVisible("id", mGetPropertyFromFile("adh_AcgPaymentScheduleDropDwnid"));
				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("adh_AcgPaymentScheduleDropDwnid"), mGetPropertyFromFile("adh_AcgPaymentScheduleDropDwnData"));
				String PaymeSchedule=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_AcgPaymentScheduleDropDwnid"));		
				System.out.println("The Payment Scedule method is::"+PaymeSchedule);
				Agc_PaymentSceduleMethod.add(PaymeSchedule);

				//Logic For Payment Scedule Type if Monthly/Bimonthly etc
				String	schedule=mGetPropertyFromFile("adh_AcgPaymentScheduleDropDwnData");
				if (schedule.equalsIgnoreCase("Monthly")||schedule.equalsIgnoreCase("Bi Monthly")||schedule.equalsIgnoreCase("Quarterly")||schedule.equalsIgnoreCase("Half Yearly")||schedule.equalsIgnoreCase("Yearwise")) {
					System.out.println("<====i m in payment shedule for"+schedule+"Pattern====>" );

					if (mGetPropertyFromFile("Mod_paymentschedule").equalsIgnoreCase("yes")) {
						PaymentSchedule();
					}

					// Capturing Payment Schedule Due Date
					String PaymentDuedate=mGetText("id",mGetPropertyFromFile("adh_AcgPaymentScheduleTotalAmountid"),"value");
					System.out.println("Payment Due Date is : "+ PaymentDuedate);			
					Agc_PaymentDueDate.add(PaymentDuedate);

					//Capturing  Amount
					mWaitForVisible("id", mGetPropertyFromFile("adh_AcgPaymentScheduleAmountid"));
					mCustomWait(1000);
					String PaymentSAmt=mGetText("id", mGetPropertyFromFile("adh_AcgPaymentScheduleAmountid"), "value");
					Agc_PayAmount.add(PaymentSAmt);

					// Payment Schedule total Amount reading
					String psta=mGetText("id",mGetPropertyFromFile("adh_AcgPaymentScheduleTotalAmountid"),"value");
					System.out.println("total amount is : "+ psta);			
					Agc_PaymentScheduleAmount.add(psta);
					mAssert(psta, mGetPropertyFromFile("adh_AcgTotalAmountData"), "assert fails Actual  :"+psta+" Expected  :"+mGetPropertyFromFile("adh_AcgTotalAmountData"));
					mTakeScreenShot();


				}else {
					System.out.println("<====i m in payment shedule for"+"Other mode"+"Pattern====>" );
					PaymentSchedule_Other();
				}


				//Click on Paymnet Schedule Submit button 
				mWaitForVisible("name", mGetPropertyFromFile("adh_AcgPaymentScheduleSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("name", mGetPropertyFromFile("adh_AcgPaymentScheduleSubBtnid"));

				//Click on Contract Terms button
				mWaitForVisible("xpath", mGetPropertyFromFile("adh_AcgSelectContrctTermSubBtnid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adh_AcgSelectContrctTermSubBtnid"));
				mCustomWait(2000);

				// Logic For Selection Of Contract Terms
				String no_of_cheks=mGetPropertyFromFile("adh_AcgContTermsSelectTermCount");
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

				//Click on Contract Terms Submit button
				mWaitForVisible("name", mGetPropertyFromFile("adh_AcgContractTermSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mClick("name", mGetPropertyFromFile("adh_AcgContractTermSubBtnid"));
				mCustomWait(1000);


				//Click on Select Hoarding Button
				mWaitForVisible("css", mGetPropertyFromFile("adh_AcgSelectHoardingBtnid"));
				mClick("css", mGetPropertyFromFile("adh_AcgSelectHoardingBtnid"));


				//Sending and Capturing Hoarding Code
				mWaitForVisible("id", mGetPropertyFromFile("adh_AcgHoardingCodeid"));
				mSendKeys("id", mGetPropertyFromFile("adh_AcgHoardingCodeid"),mGetPropertyFromFile("adh_AcgHoardingNoData"));
				String HoardingCode=mGetText("id", mGetPropertyFromFile("adh_AcgHoardingCodeid"), "value");
				System.out.println("Hoarding Code is"+HoardingCode);
				Agc_HoardingCode.add(HoardingCode);


				//Click on Hoarding Code Search Button
				mCustomWait(1000);
				mWaitForVisible("xpath", mGetPropertyFromFile("adh_AcgSearchHoardingBtnid"));
				mClick("xpath",  mGetPropertyFromFile("adh_AcgSearchHoardingBtnid"));

				//Selecting Hoarding with check box
				mWaitForVisible("name", mGetPropertyFromFile("adh_AcgSelectHoardingCheckBoxid"));
				mClick("name", mGetPropertyFromFile("adh_AcgSelectHoardingCheckBoxid"));

				//Select Hoarding Submit Button
				mWaitForVisible("css", mGetPropertyFromFile("adh_AcgSelectHoardingSubBtnid"));
				mTakeScreenShot();
				mClick("css", mGetPropertyFromFile("adh_AcgSelectHoardingSubBtnid"));


				//Capturing Pop Up Msg  and Assert
				mCustomWait(1000);
				String MsgAftrEstateSub = mGetText("css", mGetPropertyFromFile("adh_AcgHoardingPopUpAssertMsgid"));
				mCustomWait(1000);
				mAssert(MsgAftrEstateSub, mGetPropertyFromFile("adh_AcgEstatePopUpAssertMsgData"), "Actual msg : "+MsgAftrEstateSub+" Expected msg :"+mGetPropertyFromFile("adh_AcgEstatePopUpAssertMsgData")+"at Hoarding selection.");
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				System.out.println(MsgAftrEstateSub);
				mCustomWait(1000);
				mWaitForVisible("css", mGetPropertyFromFile("adh_AcgHoardingFancyBoxCloseid"));
				mClick("css", mGetPropertyFromFile("adh_AcgHoardingFancyBoxCloseid"));
				mCustomWait(1000);

				mWaitForVisible("css", mGetPropertyFromFile("adh_AcgAgencyContractFinalSubBtnid"));					

				//Uploading Document
				mUpload("id", mGetPropertyFromFile("adh_AcgUploadDocumentid"), mGetPropertyFromFile("adh_AcgUploadDocumentData"));
				mCustomWait(2000);
				//Logic for Security Deposite
				if(mGetPropertyFromFile("adh_AcgPaySecurityAmount").equalsIgnoreCase("yes"))
				{	

					mCustomWait(1000);
					mSendKeys("id", mGetPropertyFromFile("adh_AcgDepositNumid"), mGetPropertyFromFile("adh_AcgDepositNumData"));
					String adh_AcgDepositNum=mGetText("id", mGetPropertyFromFile("adh_AcgDepositNumid"), "value");
					System.out.println("Deposit No. :"+adh_AcgDepositNum);
					mAssert(adh_AcgDepositNum, mGetPropertyFromFile("adh_AcgDepositNumData"), "Deposit Num Dose not match");
					DepositNum.add(adh_AcgDepositNum);

					//Sending Deposit Date
					mDateSelect("id",mGetPropertyFromFile("adh_AcgDepositDateid"), mGetPropertyFromFile("adh_AcgDepositDateData"));
					String adh_AcgDepositDate=mGetText("id",mGetPropertyFromFile("adh_AcgDepositDateid"), "value");
					mAssert(adh_AcgDepositDate, mGetPropertyFromFile("adh_AcgDepositDateData"), "Deposit Date Dose not match");
					DepositDate.add(adh_AcgDepositDate);


					//Selecting Deposit Type
					mSelectDropDown("id",mGetPropertyFromFile("adh_AcgSecurityDepositTypeid"), mGetPropertyFromFile("adh_AcgSecurityDepositTypeData"));
					String adh_AcgSecurityDepositType=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_AcgSecurityDepositTypeid"));
					SecurityDepositType.add(adh_AcgSecurityDepositType);
					mAssert(adh_AcgSecurityDepositType, mGetPropertyFromFile("adh_AcgSecurityDepositTypeData"), "Security Deposit Type Dose not match");

					if(mGetPropertyFromFile("adh_AcgSecurityDepositTypeData").equalsIgnoreCase("cash"))
					{

						//Cheque/DDDate/cashdate
						mDateSelect("id",mGetPropertyFromFile("adh_AcgChequeDDDateid"), mGetPropertyFromFile("adh_AcgChequeDDDateData"));
						String adh_AcgChequeDDDate=mGetText("id",mGetPropertyFromFile("adh_AcgChequeDDDateid"), "value");
						System.out.println("Cash/Cheque/DD Date is:"+adh_AcgChequeDDDate);
						ChequeDDDate.add(adh_AcgChequeDDDate);

						//Security Deposit Amount
						mWaitForVisible("id", mGetPropertyFromFile("adh_AcgDepositAmountid"));
						mCustomWait(1000);
						mSendKeys("id", mGetPropertyFromFile("adh_AcgDepositAmountid"), mGetPropertyFromFile("adh_AcgDepositAmountData"));
						String adh_AcgDepositAmount=mGetText("id", mGetPropertyFromFile("adh_AcgDepositAmountid"), "value");
						DepositAmount.add(adh_AcgDepositAmount);
					}	

					else	
					{
						//Drawee Bank Name
						mWaitForVisible("id", mGetPropertyFromFile("adh_AcgDraweeBankNameid"));
						mCustomWait(1000);
						mSendKeys("id", mGetPropertyFromFile("adh_AcgDraweeBankNameid"), mGetPropertyFromFile("adh_AcgDraweeBankNameData"));
						String adh_AcgDraweeBankName=mGetText("id", mGetPropertyFromFile("adh_AcgDraweeBankNameid"), "value");
						DraweeBankName.add(adh_AcgDraweeBankName);

						//Cheque/DD Number
						mWaitForVisible("id", mGetPropertyFromFile("adh_AcgChequeDDNoid"));
						mCustomWait(1000);
						mSendKeys("id", mGetPropertyFromFile("adh_AcgChequeDDNoid"), mGetPropertyFromFile("adh_AcgChequeDDNoData"));
						ChequeDDNo.add(mGetPropertyFromFile("adh_AcgChequeDDNoData"));

						//Cheque/DDDate/cashdate
						mDateSelect("id",mGetPropertyFromFile("adh_AcgChequeDDDateid"), mGetPropertyFromFile("adh_AcgChequeDDDateData"));
						ChequeDDDate.add(mGetPropertyFromFile("adh_AcgChequeDDDateData"));

						//MICR Number
						mWaitForVisible("id", mGetPropertyFromFile("adh_AcgMicrNumid"));
						mCustomWait(1000);
						mSendKeys("id", mGetPropertyFromFile("adh_AcgMicrNumid"), mGetPropertyFromFile("adh_AcgMicrNumData"));
						MicrNum.add(mGetPropertyFromFile("adh_AcgMicrNumData"));

						//Security Deposit Amount
						mWaitForVisible("id", mGetPropertyFromFile("adh_AcgDepositAmountid"));
						mCustomWait(1000);
						mSendKeys("id", mGetPropertyFromFile("adh_AcgDepositAmountid"), mGetPropertyFromFile("adh_AcgDepositAmountData"));
						SecurityDepositamt.add(mGetPropertyFromFile("adh_AcgDepositAmountData"));
					}					

					//Security Deposit Remarks
					mWaitForVisible("id", mGetPropertyFromFile("adh_AcgDepositRemarkid"));
					mCustomWait(1000);
					mSendKeys("id", mGetPropertyFromFile("adh_AcgDepositRemarkid"), mGetPropertyFromFile("adh_AcgDepositRemarkData"));
					DepositRemarks.add(mGetPropertyFromFile("adh_AcgDepositRemarkData"));
					mCustomWait(1000);
					mTakeScreenShot();
					mCustomWait(1000);
				}


				//Click on Agency Contract Generation Submit button
				mCustomWait(2000);
				mWaitForVisible("css", mGetPropertyFromFile("adh_AcgAgencyContractFinalSubBtnid"));
				mClick("css", mGetPropertyFromFile("adh_AcgAgencyContractFinalSubBtnid"));


				mCustomWait(1000);
				mWaitForVisible("id", mGetPropertyFromFile("adh_AcgAgencyContractProcdBtnid"));

				//Capturing Agency Contract No
				String ContractNumber = mGetText("css", mGetPropertyFromFile("adh_AcgAgencyContractProceedContractNoMsgid"));
				adhContractNo.add(ContractNumber);
				System.out.println("Contract Number is: "+ContractNumber);
				System.out.println("TCcontractNo is : "+adhContractNo);
				Flag=1;
				log.info("TCcontractNo is : "+adhContractNo);


				//Pop Up Msg Assert			
				String fancymsg = mGetText("css", mGetPropertyFromFile("adh_AcgEstatePopUpAssertMsgid"));
				System.out.println(fancymsg);
				strippedString = fancymsg.replace(ContractNumber, "");
				System.out.println("Stripped msg:: "+strippedString);	
				mCustomWait(1000);
				mAssert(mGetPropertyFromFile("adh_AcgSubmitPopUpAssertMsgData"),strippedString.trim(),"actual : "+mGetPropertyFromFile("adh_AcgSubmitPopUpAssertMsgData").trim()+"expected : "+strippedString.trim());
				mTakeScreenShot();
				mCustomWait(1000);
				// Click on Proceed Button Id
				mClick("id",  mGetPropertyFromFile("adh_AcgAgencyContractProcdBtnid"));

			}
			// Logic to View Agency Contract
			else if(mGetPropertyFromFile("adh_AcgAddAgencyContract").equalsIgnoreCase("No") && mGetPropertyFromFile("adh_AcgEditAgencyContract").equalsIgnoreCase("No") && mGetPropertyFromFile("adh_AcgViewAgencyContract").equalsIgnoreCase("Yes"))
			{
				// Logic to Select radio button if Contract type is Agency
				if(mGetPropertyFromFile("adh_AcgTypeOfContractData").equalsIgnoreCase("Agency")){
					mClick("id", mGetPropertyFromFile("adh_AcgContrctTypeAgencyrbtnid"));
				}
				else{
					// Logic to Select radio button if Contract type is Private
					mClick("id", mGetPropertyFromFile("adh_AcgContrctTypePrivaterbtnid"));
				}

				//Sending Contract No 
				mWaitForVisible("id", mGetPropertyFromFile("adh_AcgSearchAgencyContractNoid"));
				mSendKeys("id", mGetPropertyFromFile("adh_AcgSearchAgencyContractNoid"), mGetPropertyFromFile("adh_AcgContractNoData"));

				//Click on Search button
				mCustomWait(1000);
				mWaitForVisible("css", mGetPropertyFromFile("adh_AcgSearchAgencyContractSrchBtnid"));
				mClick("css", mGetPropertyFromFile("adh_AcgSearchAgencyContractSrchBtnid"));
				mCustomWait(2000);
				mWaitForVisible("css", mGetPropertyFromFile("adh_AcgContractViewBtnID"));

				//Click on Contract No View Button 
				mClick("css", mGetPropertyFromFile("adh_AcgContractViewBtnID"));
				mWaitForVisible("xpath", mGetPropertyFromFile("adh_AcgContractViewBackBtnID"));
				mCustomWait(3000);
				mscroll(0, 450);
				mCustomWait(3000);

				//Click on Back Button
				mWaitForVisible("xpath", mGetPropertyFromFile("adh_AcgContractViewBackBtnID"));
				mClick("xpath", mGetPropertyFromFile("adh_AcgContractViewBackBtnID"));

			}
			else if(mGetPropertyFromFile("adh_AcgAddAgencyContract").equalsIgnoreCase("No") && mGetPropertyFromFile("adh_AcgEditAgencyContract").equalsIgnoreCase("Yes") && mGetPropertyFromFile("adh_AcgViewAgencyContract").equalsIgnoreCase("No"))
			{

				// Logic to Select radio button if Contract type is Agency
				if(mGetPropertyFromFile("adh_AcgTypeOfContractData").equalsIgnoreCase("Agency")){
					mClick("id", mGetPropertyFromFile("adh_AcgContrctTypeAgencyrbtnid"));
				}
				else{
					// Logic to Select radio button if Contract type is Private
					mClick("id", mGetPropertyFromFile("adh_AcgContrctTypePrivaterbtnid"));
				}

				//Sending Contract No 
				mWaitForVisible("id", mGetPropertyFromFile("adh_AcgSearchAgencyContractNoid"));
				mSendKeys("id", mGetPropertyFromFile("adh_AcgSearchAgencyContractNoid"), mGetPropertyFromFile("adh_AcgContractNoData"));

				//Click on Search button
				mCustomWait(1000);
				mWaitForVisible("css", mGetPropertyFromFile("adh_AcgSearchAgencyContractSrchBtnid"));
				mClick("css", mGetPropertyFromFile("adh_AcgSearchAgencyContractSrchBtnid"));
				mCustomWait(2000);

				//Click on Contract No edit Button 
				mWaitForVisible("xpath", mGetPropertyFromFile("adh_AcgContractEditBtnID"));
				mClick("xpath", mGetPropertyFromFile("adh_AcgContractEditBtnID"));

				mWaitForVisible("css", mGetPropertyFromFile("adh_AcgfinalSearchAgencyBtnid"));

				if(mGetPropertyFromFile("adh_AcgEditAgencyInAgencyContract").equalsIgnoreCase("Yes")){


					//Search Agency
					mWaitForVisible("xpath", mGetPropertyFromFile("adh_AcgfinalSearchAgencyBtnid"));
					mClick("xpath", mGetPropertyFromFile("adh_AcgfinalSearchAgencyBtnid"));


					//Sending Agency Number
					mWaitForVisible("id", mGetPropertyFromFile("adh_AcgAgencyAgencyNoid"));
					mSendKeys("id", mGetPropertyFromFile("adh_AcgAgencyAgencyNoid"), mGetPropertyFromFile("adh_AcgAgencyNoData"));

					//click on Agency Button
					mWaitForVisible("css", mGetPropertyFromFile("adh_AcgSearchAgencyBtnid"));
					mClick("css", mGetPropertyFromFile("adh_AcgSearchAgencyBtnid"));

					//Selecting Agency no
					mCustomWait(500);
					mClick("xpath", mGetPropertyFromFile("adh_AcgEditAgencyRadioid"));
					mTakeScreenShot();

					//Agency Selection Submit Button
					mWaitForVisible("css", mGetPropertyFromFile("adh_AcgAgencySubBtnid"));
					mClick("css", mGetPropertyFromFile("adh_AcgAgencySubBtnid"));


				}

				// Capturing Agency No
				String ContractNo=mGetText("id", mGetPropertyFromFile("adh_AcgEditAgencyContractNoid"), "value");
				System.out.println("Agency No is : "+ContractNo);


				// Capturing Agency Name
				String AgencyName=mGetText("id", mGetPropertyFromFile("adh_AcgAgencyNameid"), "value");
				System.out.println("Agency name is : "+AgencyName);
				Agc_AgencyName.add(AgencyName);


				// Capturing Agency No
				String AgencyNo=mGetText("id", mGetPropertyFromFile("adh_AcgAgencyNoid"), "value");
				System.out.println("Agency No is : "+AgencyNo);	
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					mAssert(AgencyNo, mGetPropertyFromFile("adh_AcgAgencyNoData"), "Agency No does not match in agency Contract Details with actual"+AgencyNo + "Expected:::"+mGetPropertyFromFile("adh_AcgAgencyNoData"));
				}
				log.info("Agency no is : "+AgencyNo);


				//Sending and Capturing Tender Reference
				mSendKeys("id", mGetPropertyFromFile("adh_AcgTenderRefid"), mGetPropertyFromFile("adh_AcgTenderRefData"));
				String TenderRef= mGetText("id", mGetPropertyFromFile("adh_AcgTenderRefid"), "value");
				System.out.println("Tender Reference no is"+TenderRef);

				//Sending and Capturing Tender Date
				mDateSelect("id",mGetPropertyFromFile("adh_AcgTenderDateid"), mGetPropertyFromFile("adh_AcgTenderDateData"));
				String TenderDate=mGetText("id", mGetPropertyFromFile("adh_AcgTenderDateid"), "value");
				System.out.println("Tender Date is"+TenderDate);

				//Sending and Capturing Contract Date
				startDate=mGetPropertyFromFile("adh_AcgContractDateData");
				mDateSelect("id",mGetPropertyFromFile("adh_AcgContractDateid"), mGetPropertyFromFile("adh_AcgContractDateData"));
				String ContractDate=mGetText("id", mGetPropertyFromFile("adh_AcgContractDateid"), "value");
				System.out.println("Contract Date is"+ContractDate);

				//Sending and Capturing Contract From Date
				String ContractFrDate=mGetText("id", mGetPropertyFromFile("adh_AcgContractFromDateid"), "value");
				System.out.println("Contract From Date is"+ContractFrDate);

				//Sending and Capturing Contract To Date
				String ContractToDate=mGetText("id", mGetPropertyFromFile("adh_AcgContractToDateid"), "value");
				System.out.println("Contract To Date is"+ContractToDate);
				mCustomWait(1000);

				String ContractAmtold=mGetText("id", mGetPropertyFromFile("adh_AcgContractAmountid"), "value");
				System.out.println("Contract Amount before edit is"+ContractAmtold);
				//Sending and Capturing Contract Amount
				if(mGetPropertyFromFile("adh_AcgTaxDescrptnYesN0Data").equalsIgnoreCase("yes")){

					mCustomWait(1000);
					mSendKeys("id", mGetPropertyFromFile("adh_AcgContractAmountid"), mGetPropertyFromFile("adh_AcgContractAmountData"));
					String ContractAmt=mGetText("id", mGetPropertyFromFile("adh_AcgContractAmountid"), "value");
					System.out.println("Contract Amount is"+ContractAmt);

				}

				String ContractDiscountAmtold=mGetText("id", mGetPropertyFromFile("adh_AcgContractDiscountValueid"), "value");
				System.out.println("Contract Discount Amount is"+ContractDiscountAmtold);
				//Logic for Sending and Capturing Contract Discount Amount
				if(mGetPropertyFromFile("adh_AcgTaxDiscountYesNoData").equalsIgnoreCase("yes")){
					mCustomWait(1000);
					mSendKeys("id", mGetPropertyFromFile("adh_AcgContractDiscountValueid"), mGetPropertyFromFile("adh_AcgContractDiscountValueData"));
					mTab("id", mGetPropertyFromFile("adh_AcgContractDiscountValueid"));
					String ContractDiscountAmt=mGetText("id", mGetPropertyFromFile("adh_AcgContractDiscountValueid"), "value");
					System.out.println("Contract Discount Amount is"+ContractDiscountAmt);

				}
				else{
					driver.findElement(By.id("acrContractDiscountValue")).clear();
					mTab("id", mGetPropertyFromFile("adh_AcgContractDiscountValueid"));
					mCustomWait(1000);
				}

				//Capturing Round Off Amount
				String ROA=mGetText("id",mGetPropertyFromFile("adh_AcgContractRoundoffAmountid"),"value");
				TC_RoundOffAmount.add(ROA);
				System.out.println("round off amount is : "+ ROA);

				//Capturing Payable Amount
				String Payable=mGetText("id",mGetPropertyFromFile("adh_AcgContractPayableAmountid"),"value");
				Payable=Payable.replace(".00", " ");
				System.out.println("payable amount is : "+ Payable);
				int payble1=Integer.parseInt(Payable.trim());
				updatedpayble_list=payble1;
				System.out.println("updatedpayble_list=>"+updatedpayble_list);
				mCustomWait(1000);
				mscroll(0,500);		

				String ContractStatus=mGetText("css", mGetPropertyFromFile("adh_AcgContractStatusid"));
				System.out.println("=>"+ContractStatus);

				//Sending and Capturing Contract Remarks
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("adh_AcgContractRemarksid"), mGetPropertyFromFile("adh_AcgContractRemarksData"));
				String ContractRemarks=mGetText("id", mGetPropertyFromFile("adh_AcgContractRemarksid"), "value");
				System.out.println("=>"+ContractRemarks);
				mCustomWait(1000);
				mTakeScreenShot();

				//Click on Amount Bifurcation Button
				mCustomWait(1000);
				mClick("css",  mGetPropertyFromFile("adh_AcgEditAmountBifurcationBtnid"));
				mCustomWait(1000);
				if(mGetPropertyFromFile("adh_AcgEditAmtBifurcationInAgencyContract").equalsIgnoreCase("Yes")){

					List<WebElement> deleteButton=driver.findElements(By.id("delButton"));
					int no=deleteButton.size();

					for(int s=0;s<no;s++){
						mCustomWait(500);
						deleteButton.get(s).click();

					}



					mCustomWait(1000);	



					//Logic for Add multiple Tax descriptions and Amount data
					String TaxDescription= mGetPropertyFromFile("adh_AcgTaxDescrptnData");
					String TaxAmt= mGetPropertyFromFile("adh_AcgTaxAmountData");

					ArrayList TaxDescriptionList = new ArrayList(Arrays.asList(TaxDescription.split(",")));
					ArrayList TaxAmtList = new ArrayList(Arrays.asList(TaxAmt.split(",")));


					for(int i=0; i<TaxDescriptionList.size();i++)
					{
						mCustomWait(1000);


						if(driver.findElement(By.xpath(".//*[@id='addButton']")).isEnabled())
						{
							System.out.println("Element is clickable");
						}
						else
						{
							System.out.println("Element is not clickable");
						}
						mCustomWait(1000);
						/*JavascriptExecutor js = (JavascriptExecutor)driver;
						WebElement button= driver.findElement(By.xpath(".//*[@id='addButton']"));
						js.executeScript("arguments[0].click();", button);
						 */
						// Click on Add Button
						mWaitForVisible("linkText", mGetPropertyFromFile("adh_AcgTaxDescrptnAddBtnid1"));
						mClick("linkText", mGetPropertyFromFile("adh_AcgTaxDescrptnAddBtnid1"));

						//Sending  Tax Description
						mWaitForVisible("id", mGetPropertyFromFile("adh_AcgTaxDescrp6tnid"));
						mCustomWait(1000);
						mSelectDropDown("id",  mGetPropertyFromFile("adh_AcgTaxDescrp6tnid"),TaxDescriptionList.get(i).toString());
						String TaxDescription1=mCaptureSelectedValue("id",  mGetPropertyFromFile("adh_AcgTaxDescrp6tnid"));
						Agc_TaxDescription.add(TaxDescription1);

						//Sending Tax amount
						mCustomWait(1000);
						mSendKeys("id",  mGetPropertyFromFile("adh_AcgTaxAmt6tnid"), TaxAmtList.get(i).toString());
						String TaxDescAmt1=mGetText("id",  mGetPropertyFromFile("adh_AcgTaxAmt6tnid"), "value");
						System.out.println("Tax Amount is"+TaxDescAmt1);
						Agc_TaxAmount.add(TaxDescAmt1);
						mTakeScreenShot();
						mTab("id", mGetPropertyFromFile("adh_AcgTaxAmt6tnid")); 



					}

					//Logic For Adding Discount if Applicable in Amount Bifurcation
					if(mGetPropertyFromFile("adh_AcgTaxDiscountYesNoData").equalsIgnoreCase("yes")){

						//Click on Add Button
						// Click on Add Button
						mWaitForVisible("linkText", mGetPropertyFromFile("adh_AcgTaxDescrptnAddBtnid1"));
						mClick("linkText", mGetPropertyFromFile("adh_AcgTaxDescrptnAddBtnid1"));

						//Selecting and Capturing Discount Description
						mWaitForVisible("id", mGetPropertyFromFile("adh_AcgTaxDescrp6tnid"));
						mCustomWait(1000);
						mSelectDropDown("id",  mGetPropertyFromFile("adh_AcgTaxDescrp6tnid"), mGetPropertyFromFile("adh_AcgTaxDiscountDescData"));
						String TaxDescription2=mCaptureSelectedValue("id",  mGetPropertyFromFile("adh_AcgTaxDescrp6tnid"));
						AgC_DiscountDescription.add(TaxDescription2);

						//Sending Discount amount
						mCustomWait(1000);
						mSendKeys("id",  mGetPropertyFromFile("adh_AcgTaxAmt6tnid"), mGetPropertyFromFile("adh_AcgContractDiscountValueData"));
						String TaxDescAmt1=mGetText("id",  mGetPropertyFromFile("adh_AcgTaxAmt6tnid"), "value");
						System.out.println("Tax Amount is"+TaxDescAmt1);
						Agc_TaxAmount.add(TaxDescAmt1);
						mTakeScreenShot();
						mTab("id", mGetPropertyFromFile("adh_AcgTaxAmt6tnid")); 
					}
				}

				// Capturing Amount Bifurcation total Amount
				String abta=mGetText("id",mGetPropertyFromFile("adh_AcgAmountBifurcationTotalAmountid"),"value");
				System.out.println("total amount is : "+ abta);	
				System.out.println("totalamount is " +mGetPropertyFromFile("adh_AcgTotalAmountData")); 
				mAssert(abta, mGetPropertyFromFile("adh_AcgTotalAmountData"), "Actual  :"+abta+" Expected  :"+mGetPropertyFromFile("adh_AcgTotalAmountData"));
				mTakeScreenShot();

				//Click on Amount Bifurcation Submit button
				mWaitForVisible("css", mGetPropertyFromFile("adh_AcgAmountBifurcationSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("adh_AcgAmountBifurcationSubBtnid"));

				//Click on Payment Schedule Button
				mWaitForVisible("xpath", mGetPropertyFromFile("adh_AcgSelctPaymentScheduleBtnid"));
				mCustomWait(1000);
				mClick("xpath",  mGetPropertyFromFile("adh_AcgSelctPaymentScheduleBtnid"));

				if(mGetPropertyFromFile("adh_AcgEditPaymentSchduleInAgencyContract").equalsIgnoreCase("Yes")){
					//Selecting Payment Schedule
					mWaitForVisible("id", mGetPropertyFromFile("adh_AcgPaymentScheduleDropDwnid"));
					mCustomWait(1000);
					mSelectDropDown("id", mGetPropertyFromFile("adh_AcgPaymentScheduleDropDwnid"), mGetPropertyFromFile("adh_AcgPaymentScheduleDropDwnData"));
					String PaymeSchedule=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_AcgPaymentScheduleDropDwnid"));		
					System.out.println("The Payment Scedule method is::"+PaymeSchedule);

					//Logic For Payment Scedule Type if Monthly/Bimonthly etc
					String	schedule=mGetPropertyFromFile("adh_AcgPaymentScheduleDropDwnData");
					if (schedule.equalsIgnoreCase("Monthly")||schedule.equalsIgnoreCase("Bi Monthly")||schedule.equalsIgnoreCase("Quarterly")||schedule.equalsIgnoreCase("Half Yearly")||schedule.equalsIgnoreCase("Yearwise")) {
						System.out.println("<====i m in payment shedule for"+schedule+"Pattern====>" );

						if (mGetPropertyFromFile("Mod_paymentschedule").equalsIgnoreCase("yes")) {
							PaymentSchedule();
						}

						// Capturing Payment Schedule Due Date
						String PaymentDuedate=mGetText("id",mGetPropertyFromFile("adh_AcgPaymentScheduleTotalAmountid"),"value");
						System.out.println("Payment Due Date is : "+ PaymentDuedate);			
						Agc_PaymentDueDate.add(PaymentDuedate);

						//Capturing  Amount
						mWaitForVisible("id", mGetPropertyFromFile("adh_AcgPaymentScheduleAmountid"));
						mCustomWait(1000);
						String PaymentSAmt=mGetText("id", mGetPropertyFromFile("adh_AcgPaymentScheduleAmountid"), "value");
						Agc_PayAmount.add(PaymentSAmt);

						// Payment Schedule total Amount reading
						String psta=mGetText("id",mGetPropertyFromFile("adh_AcgPaymentScheduleTotalAmountid"),"value");
						System.out.println("total amount is : "+ psta);			
						Agc_PaymentScheduleAmount.add(psta);
						mAssert(psta, mGetPropertyFromFile("adh_AcgTotalAmountData"), "assert fails Actual  :"+psta+" Expected  :"+mGetPropertyFromFile("adh_AcgTotalAmountData"));
						mTakeScreenShot();


					}else {
						System.out.println("<====i m in payment shedule for"+"Other mode"+"Pattern====>" );
						PaymentSchedule_Other();
					}

				}
				//Click on Paymnet Schedule Submit button 
				mWaitForVisible("name", mGetPropertyFromFile("adh_AcgPaymentScheduleSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("name", mGetPropertyFromFile("adh_AcgPaymentScheduleSubBtnid"));


				//Click on Contract Terms button
				mWaitForVisible("xpath", mGetPropertyFromFile("adh_AcgSelectContrctTermSubBtnid"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adh_AcgSelectContrctTermSubBtnid"));
				mCustomWait(2000);

				if(mGetPropertyFromFile("adh_AcgEditContractTermseInAgencyContract").equalsIgnoreCase("Yes")){
					// Logic For Selection Of Contract Terms
					String no_of_cheks=mGetPropertyFromFile("adh_AcgContTermsSelectTermCount");
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
				}
				mCustomWait(2000);

				//Click on Contract Terms Submit button
				mWaitForVisible("name", mGetPropertyFromFile("adh_AcgContractTermSubBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mClick("name", mGetPropertyFromFile("adh_AcgContractTermSubBtnid"));
				mCustomWait(1000);


				if(mGetPropertyFromFile("adh_AcgEditHoardingToBeDeleteInAgencyContract").equalsIgnoreCase("Yes")){
					mWaitForVisible("xpath", mGetPropertyFromFile("adh_AcgDeleteSelectedHoardingBtnID"));
					mClick("xpath", mGetPropertyFromFile("adh_AcgDeleteSelectedHoardingBtnID"));
					mCustomWait(500);
					mWaitForVisible("xpath", mGetPropertyFromFile("adh_AcgDeleteYesBtnID"));
					mClick("xpath", mGetPropertyFromFile("adh_AcgDeleteYesBtnID"));
					mCustomWait(500);
					mWaitForVisible("xpath", mGetPropertyFromFile("adh_AcgDeletePopUpCloseCancelBtnID"));
					mClick("xpath", mGetPropertyFromFile("adh_AcgDeletePopUpCloseCancelBtnID"));

				}


				if(mGetPropertyFromFile("adh_AcgEditHoardingToBeAddInAgencyContract").equalsIgnoreCase("Yes")){

					mCustomWait(1000);
					//Click on Select Hoarding Button
					mWaitForVisible("css", mGetPropertyFromFile("adh_AcgSelectHoardingBtnid"));
					mClick("css", mGetPropertyFromFile("adh_AcgSelectHoardingBtnid"));


					//Sending and Capturing Hoarding Code
					mWaitForVisible("id", mGetPropertyFromFile("adh_AcgHoardingCodeid"));
					mSendKeys("id", mGetPropertyFromFile("adh_AcgHoardingCodeid"),mGetPropertyFromFile("adh_AcgHoardingNoData"));
					String HoardingCode=mGetText("id", mGetPropertyFromFile("adh_AcgHoardingCodeid"), "value");
					System.out.println("Hoarding Code is"+HoardingCode);


					//Click on Hoarding Code Search Button
					mCustomWait(1000);
					mWaitForVisible("xpath", mGetPropertyFromFile("adh_AcgSearchHoardingBtnid"));
					mClick("xpath",  mGetPropertyFromFile("adh_AcgSearchHoardingBtnid"));

					//Selecting Hoarding with check box
					mWaitForVisible("name", mGetPropertyFromFile("adh_AcgSelectHoardingCheckBoxid"));
					mClick("name", mGetPropertyFromFile("adh_AcgSelectHoardingCheckBoxid"));

					//Select Hoarding Submit Button
					mWaitForVisible("css", mGetPropertyFromFile("adh_AcgSelectHoardingSubBtnid"));
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("adh_AcgSelectHoardingSubBtnid"));

					//Capturing Pop Up Msg  and Assert
					mCustomWait(1000);
					String MsgAftrEstateSub = mGetText("css", mGetPropertyFromFile("adh_AcgHoardingPopUpAssertMsgid"));
					mCustomWait(1000);
					mAssert(MsgAftrEstateSub, mGetPropertyFromFile("adh_AcgEstatePopUpAssertMsgData"), "Actual msg : "+MsgAftrEstateSub+" Expected msg :"+mGetPropertyFromFile("adh_AcgEstatePopUpAssertMsgData")+"at Hoarding selection.");
					mCustomWait(1000);
					mTakeScreenShot();
					mCustomWait(1000);
					System.out.println(MsgAftrEstateSub);
					mCustomWait(1000);
					mWaitForVisible("css", mGetPropertyFromFile("adh_AcgHoardingFancyBoxCloseid"));
					mClick("css", mGetPropertyFromFile("adh_AcgHoardingFancyBoxCloseid"));
					mCustomWait(1000);
					mWaitForVisible("css", mGetPropertyFromFile("adh_AcgAgencyContractFinalSubBtnid"));										
				}


				if(mGetPropertyFromFile("adh_AcgEditDocumentToBeUploadInAgencyContract").equalsIgnoreCase("Yes")){
					//Uploading Document
					mUpload("id", mGetPropertyFromFile("adh_AcgUploadDocumentid"), mGetPropertyFromFile("adh_AcgUploadDocumentData"));
					mCustomWait(2000);
				}

				//Logic for Security Deposite
				if(mGetPropertyFromFile("adh_AcgPaySecurityAmount").equalsIgnoreCase("yes"))
				{	

					mCustomWait(1000);
					mSendKeys("id", mGetPropertyFromFile("adh_AcgDepositNumid"), mGetPropertyFromFile("adh_AcgDepositNumData"));
					String adh_AcgDepositNum=mGetText("id", mGetPropertyFromFile("adh_AcgDepositNumid"), "value");
					System.out.println("Deposit No. :"+adh_AcgDepositNum);
					mAssert(adh_AcgDepositNum, mGetPropertyFromFile("adh_AcgDepositNumData"), "Deposit Num Dose not match");
					DepositNum.add(adh_AcgDepositNum);

					//Sending Deposit Date
					mDateSelect("id",mGetPropertyFromFile("adh_AcgDepositDateid"), mGetPropertyFromFile("adh_AcgDepositDateData"));
					String adh_AcgDepositDate=mGetText("id",mGetPropertyFromFile("adh_AcgDepositDateid"), "value");
					mAssert(adh_AcgDepositDate, mGetPropertyFromFile("adh_AcgDepositDateData"), "Deposit Date Dose not match");
					DepositDate.add(adh_AcgDepositDate);


					//Selecting Deposit Type
					mSelectDropDown("id",mGetPropertyFromFile("adh_AcgSecurityDepositTypeid"), mGetPropertyFromFile("adh_AcgSecurityDepositTypeData"));
					String adh_AcgSecurityDepositType=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_AcgSecurityDepositTypeid"));
					SecurityDepositType.add(adh_AcgSecurityDepositType);
					mAssert(adh_AcgSecurityDepositType, mGetPropertyFromFile("adh_AcgSecurityDepositTypeData"), "Security Deposit Type Dose not match");

					if(mGetPropertyFromFile("adh_AcgSecurityDepositTypeData").equalsIgnoreCase("cash"))
					{

						//Cheque/DDDate/cashdate
						mDateSelect("id",mGetPropertyFromFile("adh_AcgChequeDDDateid"), mGetPropertyFromFile("adh_AcgChequeDDDateData"));
						String adh_AcgChequeDDDate=mGetText("id",mGetPropertyFromFile("adh_AcgChequeDDDateid"), "value");
						System.out.println("Cash/Cheque/DD Date is:"+adh_AcgChequeDDDate);
						ChequeDDDate.add(adh_AcgChequeDDDate);

						//Security Deposit Amount
						mWaitForVisible("id", mGetPropertyFromFile("adh_AcgDepositAmountid"));
						mCustomWait(1000);
						mSendKeys("id", mGetPropertyFromFile("adh_AcgDepositAmountid"), mGetPropertyFromFile("adh_AcgDepositAmountData"));
						String adh_AcgDepositAmount=mGetText("id", mGetPropertyFromFile("adh_AcgDepositAmountid"), "value");
						DepositAmount.add(adh_AcgDepositAmount);
					}	

					else	
					{
						//Drawee Bank Name
						mWaitForVisible("id", mGetPropertyFromFile("adh_AcgDraweeBankNameid"));
						mCustomWait(1000);
						mSendKeys("id", mGetPropertyFromFile("adh_AcgDraweeBankNameid"), mGetPropertyFromFile("adh_AcgDraweeBankNameData"));
						String adh_AcgDraweeBankName=mGetText("id", mGetPropertyFromFile("adh_AcgDraweeBankNameid"), "value");
						DraweeBankName.add(adh_AcgDraweeBankName);

						//Cheque/DD Number
						mWaitForVisible("id", mGetPropertyFromFile("adh_AcgChequeDDNoid"));
						mCustomWait(1000);
						mSendKeys("id", mGetPropertyFromFile("adh_AcgChequeDDNoid"), mGetPropertyFromFile("adh_AcgChequeDDNoData"));
						ChequeDDNo.add(mGetPropertyFromFile("adh_AcgChequeDDNoData"));

						//Cheque/DDDate/cashdate
						mDateSelect("id",mGetPropertyFromFile("adh_AcgChequeDDDateid"), mGetPropertyFromFile("adh_AcgChequeDDDateData"));
						ChequeDDDate.add(mGetPropertyFromFile("adh_AcgChequeDDDateData"));

						//MICR Number
						mWaitForVisible("id", mGetPropertyFromFile("adh_AcgMicrNumid"));
						mCustomWait(1000);
						mSendKeys("id", mGetPropertyFromFile("adh_AcgMicrNumid"), mGetPropertyFromFile("adh_AcgMicrNumData"));
						MicrNum.add(mGetPropertyFromFile("adh_AcgMicrNumData"));

						//Security Deposit Amount
						mWaitForVisible("id", mGetPropertyFromFile("adh_AcgDepositAmountid"));
						mCustomWait(1000);
						mSendKeys("id", mGetPropertyFromFile("adh_AcgDepositAmountid"), mGetPropertyFromFile("adh_AcgDepositAmountData"));
						SecurityDepositamt.add(mGetPropertyFromFile("adh_AcgDepositAmountData"));
					}					

					//Security Deposit Remarks
					mWaitForVisible("id", mGetPropertyFromFile("adh_AcgDepositRemarkid"));
					mCustomWait(1000);
					mSendKeys("id", mGetPropertyFromFile("adh_AcgDepositRemarkid"), mGetPropertyFromFile("adh_AcgDepositRemarkData"));
					DepositRemarks.add(mGetPropertyFromFile("adh_AcgDepositRemarkData"));
					mCustomWait(1000);
					mTakeScreenShot();
					mCustomWait(1000);
				}


				//Click on Agency Contract Generation Submit button
				mCustomWait(2000);
				mWaitForVisible("css", mGetPropertyFromFile("adh_AcgAgencyContractFinalSubBtnid"));
				mClick("css", mGetPropertyFromFile("adh_AcgAgencyContractFinalSubBtnid"));


				mCustomWait(1000);
				mWaitForVisible("id", mGetPropertyFromFile("adh_AcgAgencyContractProcdBtnid"));
				mClick("css", mGetPropertyFromFile("adh_AcgAgencyContractProcdBtnid"));



			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in agencyContract method");
		}
	}

	// Booking Of Hoarding Scrutiny View Form Assertion with Application Form Method
	public void adh_Booking_Of_Hoarding_ScrutinyView_Assertion()
	{
		try {
			mCustomWait(1500);
			ScrutinyServiceName = driver.findElement(By.cssSelector("#middle_right > h1")).getText();
			System.out.println("Service Name in Scrutiny view form is"+ScrutinyServiceName);
			mWaitForVisible("id", mGetPropertyFromFile("adh_BookingOfHoardingTitleID"));
			// Capturing Applicant Title and Assertion

			String scr_adhApptitle=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_BookingOfHoardingTitleID"));
			System.out.println("Applicant Title is in Scutiny : "+scr_adhApptitle);
			BH_Scr_adhApptitle.add(scr_adhApptitle);
			// Capturing Applicant First Name and Assertion
			String scr_adhHoardingFname=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingFnameID"),"value");
			System.out.println("Applicant First Name is in Scutiny : "+scr_adhHoardingFname);
			BH_scr_adhHoardingFname.add(scr_adhHoardingFname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//System.out.println("Captured Applicant First Name arraylist is in Scutiny ::: "+BH_scr_adhHoardingFname.get(CurrentinvoCount)+" "+",Expected from Application form is"+adh_ApplicantFname.get(CurrentinvoCount));
				mAssert(adh_ApplicantFname.get(CurrentinvoCount), BH_scr_adhHoardingFname.get(approvechekindexes.get(CurrentinvoCount)), "Applicant First Name does not match with the actual::"+BH_scr_adhHoardingFname.get(approvechekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_ApplicantFname.get(CurrentinvoCount));
			}

			// Capturing Applicant Middle Name and Assertion
			String scr_adhHoardingMname=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingMidnameID"),"value");
			System.out.println("Applicant Middle Name is in Scutiny : "+scr_adhHoardingMname);
			BH_scr_adhHoardingMname.add(scr_adhHoardingMname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//System.out.println("Captured Applicant Middle Name arraylist is in Scutiny ::: "+BH_scr_adhHoardingMname.get(CurrentinvoCount)+" "+",Expected from Application form is"+adh_ApplicantMname.get(CurrentinvoCount));
				mAssert(adh_ApplicantMname.get(CurrentinvoCount), BH_scr_adhHoardingMname.get(approvechekindexes.get(CurrentinvoCount)), "Applicant  Middle Name does not match with the actual::"+BH_scr_adhHoardingMname.get(approvechekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_ApplicantMname.get(CurrentinvoCount));
			}

			// Capturing Applicant Last Name and Assertion
			String scr_adhHoardingLname=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingLnameID"),"value");
			System.out.println("Applicant Last Name is in Scutiny : "+scr_adhHoardingLname);
			BH_scr_adhHoardingLname.add(scr_adhHoardingLname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//System.out.println("Captured Applicant Last Name arraylist is in Scutiny ::: "+BH_scr_adhHoardingLname.get(CurrentinvoCount)+" "+",Expected from Application form is"+adh_ApplicantLname.get(CurrentinvoCount));
				mAssert(adh_ApplicantLname.get(CurrentinvoCount), BH_scr_adhHoardingLname.get(approvechekindexes.get(CurrentinvoCount)), "Applicant Last Name does not match with the actual::"+BH_scr_adhHoardingLname.get(approvechekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_ApplicantLname.get(CurrentinvoCount));
			}

			// Dyanamically creating  Applicant Full  Name and Assertion
			String scr_adhHoardingLFullname=scr_adhApptitle+" "+scr_adhHoardingFname+" "+scr_adhHoardingMname+" "+scr_adhHoardingLname;
			System.out.println("Applicant Full Name is in Scutiny : "+scr_adhHoardingLFullname);
			BH_scr_adhHoardingLFullname.add(scr_adhHoardingLFullname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//System.out.println("Captured Applicant Full Name arraylist is in Scutiny ::: "+BH_scr_adhHoardingLFullname.get(CurrentinvoCount)+" "+",Expected from Application form is"+adh_ApplicantLname.get(CurrentinvoCount));
				mAssert(adh_ApplicantFullname.get(CurrentinvoCount), BH_scr_adhHoardingLFullname.get(approvechekindexes.get(CurrentinvoCount)), "Applicant Full Name does not match with the actual::"+BH_scr_adhHoardingLFullname.get(approvechekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_ApplicantFullname.get(CurrentinvoCount));
			}

			// Capturing Applicant Mobile No and Assertion
			String scr_adhHoardingMobNo=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingMobNoID"),"value");
			System.out.println("Applicant Mobile No is in Scutiny : "+scr_adhHoardingMobNo);
			BH_scr_adhHoardingMobNo.add(scr_adhHoardingMobNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//System.out.println("Captured Applicant Mobile No arraylist is in Scutiny ::: "+BH_scr_adhHoardingMobNo.get(CurrentinvoCount)+" "+",Expected from Application form is"+adh_ApplicantMbNo.get(CurrentinvoCount));
				mAssert(adh_ApplicantMbNo.get(CurrentinvoCount), BH_scr_adhHoardingMobNo.get(approvechekindexes.get(CurrentinvoCount)), "Applicant Mobile No does not match with the actual::"+BH_scr_adhHoardingMobNo.get(approvechekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_ApplicantMbNo.get(CurrentinvoCount));
			}

			// Capturing Applicant Email Id and Assertion
			String scr_adhHoardingEmail=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingEmailID"),"value");
			System.out.println("Applicant Email Id is in Scutiny : "+scr_adhHoardingEmail);
			BH_scr_adhHoardingEmail.add(scr_adhHoardingEmail);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//System.out.println("Captured Applicant Email Id arraylist is in Scutiny ::: "+BH_scr_adhHoardingEmail.get(CurrentinvoCount)+" "+",Expected from Application form is"+adh_ApplicantEmail.get(CurrentinvoCount));
				mAssert(adh_ApplicantEmail.get(CurrentinvoCount), BH_scr_adhHoardingEmail.get(approvechekindexes.get(CurrentinvoCount)), "Applicant Email Id does not match with the actual::"+BH_scr_adhHoardingEmail.get(approvechekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_ApplicantEmail.get(CurrentinvoCount));
			}

			// Capturing Application No and Assertion
			String scr_adhHoardingApplicationNo=mGetText("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingRondOffAmtID"),"value");
			System.out.println("Applicant No is in Scutiny : "+scr_adhHoardingApplicationNo);
			BH_scr_adhHoardingApplicationNo.add(scr_adhHoardingApplicationNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//System.out.println("Captured Applicant No arraylist is in Scutiny ::: "+BH_scr_adhHoardingApplicationNo.get(CurrentinvoCount)+" "+",Expected from Application form is"+applicationNo.get(CurrentinvoCount));
				mAssert(applicationNo.get(CurrentinvoCount), BH_scr_adhHoardingApplicationNo.get(approvechekindexes.get(CurrentinvoCount)), "Applicantation No of Booking of Hoarding  does not match with the actual::"+BH_scr_adhHoardingApplicationNo.get(approvechekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+applicationNo.get(CurrentinvoCount));
			}

			// Capturing Hoarding from date and Assertion
			String scr_adhHoardingFromDate=mGetText("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingFromDateID"),"value");
			System.out.println("Applicant Hoarding Booking From date is in Scutiny : "+scr_adhHoardingFromDate);
			BH_scr_adhHoardingFromDate.add(scr_adhHoardingFromDate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//System.out.println("Captured Hoarding Booking From date arraylist is in Scutiny ::: "+BH_scr_adhHoardingFromDate.get(CurrentinvoCount)+" "+",Expected from Application form is"+adh_BookingfromDate.get(CurrentinvoCount));
				mAssert(adh_BookingfromDate.get(CurrentinvoCount), BH_scr_adhHoardingFromDate.get(approvechekindexes.get(CurrentinvoCount)), "Applicant Hoarding Booking From does not match with the actual::"+BH_scr_adhHoardingFromDate.get(approvechekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_BookingtoDate.get(CurrentinvoCount));
			}

			// Capturing Hoarding To date and Assertion
			String scr_adhHoardingToDate=mGetText("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingToDateID"),"value");
			BH_scr_adhHoardingToDate.add(scr_adhHoardingToDate);
			System.out.println("Applicant Hoarding Booking To Date is in Scutiny : "+scr_adhHoardingToDate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//System.out.println("Captured Hoarding Booking To Date arraylist is in Scutiny ::: "+BH_scr_adhHoardingToDate.get(CurrentinvoCount)+" "+",Expected from Application form is"+adh_BookingtoDate.get(CurrentinvoCount));
				mAssert(adh_BookingtoDate.get(CurrentinvoCount), BH_scr_adhHoardingToDate.get(approvechekindexes.get(CurrentinvoCount)), "Applicant Hoarding Booking to does not match with the actual::"+BH_scr_adhHoardingToDate.get(approvechekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_BookingtoDate.get(CurrentinvoCount));
			}
			// Capturing Hoarding Remarks and Assertion
			String scr_adhHoardingRemarks=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingRemarksID"),"value");
			System.out.println("Applicant Remarks is in Scutiny : "+scr_adhHoardingRemarks);
			BH_scr_adhHoardingRemarks.add(scr_adhHoardingRemarks);

			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//System.out.println("Captured Applicant Last Name arraylist is in Scutiny ::: "+BH_scr_adhHoardingRemarks.get(CurrentinvoCount)+" "+",Expected from Application form is"+adh_ApplicantRemarks.get(CurrentinvoCount));
				mAssert(adh_ApplicantRemarks.get(CurrentinvoCount), BH_scr_adhHoardingRemarks.get(approvechekindexes.get(CurrentinvoCount)), "Applicant Booking of Hoarding Remarks does not match with the actual::"+BH_scr_adhHoardingRemarks.get(approvechekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_ApplicantRemarks.get(CurrentinvoCount));
			}
			// Capturing Contract Amount and Assertion
			String scr_adhHoardingContractAmt=mGetText("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingContractAmtID"),"value");
			System.out.println("Booking Of Hoarding Contract Amount is in Scutiny : "+scr_adhHoardingContractAmt);
			BH_scr_adhHoardingContractAmt.add(scr_adhHoardingContractAmt);

			// Capturing Discount Amount and Assertion
			String scr_adhHoardingDiscountAmt=mGetText("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingDiscountAmtID"),"value");
			System.out.println("Booking Of Hoarding Discount Amount is in Scutiny : "+scr_adhHoardingDiscountAmt);
			BH_scr_adhHoardingDiscountAmt.add(scr_adhHoardingDiscountAmt);


			// Capturing Payble Amount and Assertion
			String scr_adhHoardingPaybleAmt=mGetText("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingPaybleAmtID"),"value");
			System.out.println("Booking Of Hoarding Payble Amount is in Scutiny : "+scr_adhHoardingPaybleAmt);
			BH_scr_adhHoardingPaybleAmt.add(scr_adhHoardingPaybleAmt);

			// Capturing Payble Amount and Assertion
			String Payable=mGetText("id",mGetPropertyFromFile("adh_Scr_BookingOfHoardingPaybleAmtID"),"value");
			Acg_PayableAmount.add(Payable);

			// Capturing Rounding off  Amount and Assertion
			String scr_adhHoardingRondOffAmt=mGetText("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingRondOffAmtID"),"value");
			System.out.println("Booking Of Hoarding RoundOff Amount is in Scutiny : "+scr_adhHoardingRondOffAmt);
			BH_scr_adhHoardingRoundOffAmt.add(scr_adhHoardingRondOffAmt);

			mCustomWait(1000);
			//Reading Table Booking Details at scrutiny view form
			Tblread_adh_Scrutiny_Booking_Details();
			mCustomWait(1000);
		}

		catch (Exception e) 
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in adh_ScrutinyView_Assertion method");
		}
	}

	//ADH Scrutiny table Reading for Booking of Hoarding 01 March 2017 
	public  void  Tblread_adh_Scrutiny_Booking_Details()
	{
		try {
			// Capturing searched Booking of Hoarding Details 
			WebElement table = driver.findElement(By.id("gview_gridAdvertiseBookingEdit"));

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
						if(b==1)
						{
							selectedHoardingCode = rows.get(a).findElement(By.xpath("td[2]")).getText();
							System.out.println("Hoarding Code is : "+selectedHoardingCode);
						}
						if(b==2)
						{
							selectedHoardingName = rows.get(a).findElement(By.xpath("td[3]")).getText();
							System.out.println("Hoarding Name(Description) is : : "+selectedHoardingName);
						}
						if(b==3)
						{
							selectedHoardingAddr = rows.get(a).findElement(By.xpath("td[4]")).getText();
							System.out.println("Hoarding Location Address is : "+selectedHoardingAddr);
						}

						if(b==4)
						{
							selectedHoardingType = rows.get(a).findElement(By.xpath("td[5]")).getText();
							System.out.println("Hoarding Type is : "+selectedHoardingType);
						}
						if(b==5)
						{
							selectedBookingAmt = rows.get(a).findElement(By.xpath("td[6]")).getText();
							System.out.println("Hoarding Amt is : "+selectedBookingAmt);
						}
					}
				}

			}
		}
		catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in Tblread_RNL_Scrutiny_Booking_Details method");
		}		

	}



	// View And Modify Application Scrutiny for Booking Of Hoarding service
	public void adh_View_ModifyApp(){
		try{
			//Click on Button
			mClick("css", imageid);
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_PaymentShceduleButtonid"));

			// Method For Reading Scutiny View Form
			adh_Booking_Of_Hoarding_ScrutinyView_Assertion();

			if(mGetPropertyFromFile("adh_BookingOfHoardingDiscountYesNoData").equalsIgnoreCase("Yes")){
				// Capturing Discount Amount and Assertion
				mSendKeys("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingDiscountAmtID"), mGetPropertyFromFile("adh_Scr_BookingHoardingDiscountAmtData"));
				String scr_adhHoardingDiscountAmt=mGetText("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingDiscountAmtID"),"value");
				System.out.println("Booking Of Hoarding Discount Amount is in Scutiny : "+scr_adhHoardingDiscountAmt);
				BH_scr_adhHoardingDiscountAmt.add(scr_adhHoardingDiscountAmt);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					mAssert(scr_adhHoardingDiscountAmt, mGetPropertyFromFile("adh_Scr_BookingHoardingDiscountAmtData"), "Booking Of Hoarding Discount Amount does not match with the actual::"+scr_adhHoardingDiscountAmt+"in scrutiny View form,Expected::"+mGetPropertyFromFile("adh_Scr_BookingHoardingDiscountAmtData"));
				}
			}
			// Capturing Payble Amount and Assertion
			String scr_adhHoardingPaybleAmt=mGetText("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingPaybleAmtID"),"value");
			System.out.println("Booking Of Hoarding Payble Amount is in Scutiny : "+scr_adhHoardingPaybleAmt);
			mTab("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingPaybleAmtID"));
			BH_scr_adhHoardingPaybleAmt.add(scr_adhHoardingPaybleAmt);
			// Capturing Payble Amount and Assertion
			String Payable1=mGetText("id",mGetPropertyFromFile("adh_Scr_BookingOfHoardingPaybleAmtID"),"value");
			String Payable=Payable1.replace(".00", " ");
			String Payableamt=Payable.trim();

			Acg_PayableAmount.add(Payableamt);
			System.out.println("payable amount is : "+ Payableamt);
			int payble1=Integer.parseInt(Payableamt); 

			System.out.println("payable amount is : "+ payble1);
			scrupdatedpayble_list=payble1;
			System.out.println("updatedpayble_list=>"+scrupdatedpayble_list);


			//Click on Payment Scedule Button
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_PaymentShceduleButtonid"));
			mClick("xpath", mGetPropertyFromFile("adh_PaymentShceduleButtonid"));

			//Selecting Payment Schedule 
			mWaitForVisible("css", mGetPropertyFromFile("adh_PaymentShcedulePopUpBoxid"));
			mCustomWait(1000);
			mSelectDropDown("id", mGetPropertyFromFile("adh_PaymentShceduleid"), mGetPropertyFromFile("adh_View_ModifyPaySchSelectPaySchdata"));
			String PaymentSchedule=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_PaymentShceduleid"));
			System.out.println("Payment Schedule method  is :::"+PaymentSchedule);

			//Capturing Payment Schedule Data 
			String	schedule=mGetPropertyFromFile("adh_View_ModifyPaySchSelectPaySchdata");
			if (schedule.equalsIgnoreCase("Monthly")||schedule.equalsIgnoreCase("Bi Monthly")||schedule.equalsIgnoreCase("Quarterly")||schedule.equalsIgnoreCase("Half Yearly")||schedule.equalsIgnoreCase("Yearwise")) {
				System.out.println("<====i m in payment shedule for"+schedule+"Pattern====>" );
				if (mGetPropertyFromFile("Mod_paymentschedule").equalsIgnoreCase("yes")) {
					ScrutinyPaymentSchedule();
				}

			}else {
				System.out.println("<====i m in payment shedule for"+"Other mode"+"Pattern====>" );
				ScrutinyPaymentSchedule_Other();
			}

			//Click on Submit button id 
			mCustomWait(1000);
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_View_ModifyPaySchSubmitBtnid"));
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("adh_View_ModifyPaySchSubmitBtnid"));
			mCustomWait(1000);

			//Click on Select Contract Terms Button
			mClick("xpath", mGetPropertyFromFile("adh_View_ModifyContTermsBtnid"));
			mWaitForVisible("css", mGetPropertyFromFile("adh_View_ModifyContTermsPopUpBoxid")); 	
			mCustomWait(1000);
			//Select Multiple Contract Terms based on user  
			adh_mDynamicContractTermsTableGrid();

			//Click on Contract Terms submit button
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("adh_View_ModifyContTermsSubmitBtnid"));
			mCustomWait(1000);
			mscroll(0,300);
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_PaymentShceduleButtonid"));
			//Click on Approve Check Box
			mClick("id", mGetPropertyFromFile("adh_View_ModifyApprovedChkboxid"));

			//Capturing Approved By Name
			String ApproovedBy=mGetText("id", mGetPropertyFromFile("adh_View_ApprovedByid"),"value");
			System.out.println("Approved By::"+ApproovedBy);

			//Capturing Approve Date
			String ApprooveDate=mGetText("id", mGetPropertyFromFile("adh_View_ApprovedDateid"),"value");
			System.out.println("Approved Date::"+ApprooveDate);
			mCustomWait(1000);

			//Sending Approval Remarks
			mSendKeys("id",mGetPropertyFromFile("adh_ApprovedRemarkid"),mGetPropertyFromFile("adh_ApprovedRemarkData"));
			mCustomWait(1000);
			//Click on Submit Button
			mClick("xpath", mGetPropertyFromFile("adh_View_ModifyFormSubmitBtnid"));
			mCustomWait(1000);

		}
		catch(Exception e){
			e.printStackTrace();
			exception = e.toString();	
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Current date method adh_View_ModifyApp() failed");
		}
	}


	//View and Modify Application  for Set up Of Hoarding service
	public void adh_View_ModifyApp_For_SetUpOfHoarding(){
		try{
			mClick("css", imageid);
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_PaymentShceduleButtonid"));
			//Scrutiny View Form Assertion Method
			adh_Set_Up_Of_Hoarding_ScrutinyView_Assertion();

			if(mGetPropertyFromFile("adh_SetUpOfHoardingDiscountYesNoData").equalsIgnoreCase("Yes"))	{
				mSendKeys("id", mGetPropertyFromFile("adh_Scr_SetUpOfHoardingBookingDiscountID"),mGetPropertyFromFile("adh_Scr_SetUpOfHoardingDiscountAmtData"));
				String Discount=mGetText("id", mGetPropertyFromFile("adh_Scr_SetUpOfHoardingBookingDiscountID"),"value");
				mTab("id", mGetPropertyFromFile("adh_Scr_SetUpOfHoardingBookingDiscountID"));
				scr_suoh_Discount.add(Discount);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					System.out.println("Captured Set Up Of Hoarding Remarks in scrutiny ::: "+mGetPropertyFromFile("adh_Scr_SetUpOfHoardingDiscountAmtData")+" "+",Expected from Application form is"+Discount);
					mAssert(Discount,mGetPropertyFromFile("adh_Scr_SetUpOfHoardingDiscountAmtData"), "Set Up Of Hoarding Remarks dose not match Actual::"+mGetPropertyFromFile("adh_Scr_SetUpOfHoardingDiscountAmtData")+"Expected::"+Discount);
				}
			}

			String PaybleAmt=mGetText("id", mGetPropertyFromFile("adh_Scr_SetUpOfHoardingBookingPaybleAmountID"),"value");
			PaybleAmt=PaybleAmt.replace(".00", " ");
			System.out.println("payable amount is : "+ PaybleAmt);
			scr_suoh_PaybleAmount.add(PaybleAmt.trim());
			System.out.println("payable amount is : "+ scr_suoh_PaybleAmount);
			int payble1=Integer.parseInt(PaybleAmt.trim());
			scrupdatedpayble_list=payble1;
			System.out.println("updatedpayble_list=>"+scrupdatedpayble_list);
			mCustomWait(1000);
			//Click on Payment Schedule Button 
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_PaymentShceduleButtonid"));
			mClick("xpath", mGetPropertyFromFile("adh_PaymentShceduleButtonid"));
			mCustomWait(1000);
			mWaitForVisible("css", mGetPropertyFromFile("adh_PaymentShcedulePopUpBoxid"));
			//Selecting and capturing Payment Schedule 
			mCustomWait(1500);
			mSelectDropDown("id", mGetPropertyFromFile("adh_PaymentShceduleid"), mGetPropertyFromFile("adh_View_ModifyPaySchSelectPaySchdata"));
			String PaymentSchedule=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_PaymentShceduleid"));
			System.out.println("Payment Schedule method  is :::"+PaymentSchedule);
			mCustomWait(1000);

			//Capturing Payment Schedule Data 
			String	schedule=mGetPropertyFromFile("adh_View_ModifyPaySchSelectPaySchdata");
			mCustomWait(500);
			if (schedule.equalsIgnoreCase("Monthly")||schedule.equalsIgnoreCase("Bi Monthly")||schedule.equalsIgnoreCase("Quarterly")||schedule.equalsIgnoreCase("Half Yearly")||schedule.equalsIgnoreCase("Yearwise")) {
				System.out.println("<====i m in payment shedule for"+ schedule +"Pattern====>" );
				if (mGetPropertyFromFile("Mod_paymentschedule").equalsIgnoreCase("yes")) {
					ScrutinyPaymentSchedule();
				}

			}else {
				System.out.println("<====i m in payment shedule for"+"Other mode"+"Pattern====>" );
				ScrutinyPaymentSchedule_Other();
				mCustomWait(1000);
			}

			//Click on Payment Scedule submit Button 
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_View_ModifyPaySchSubmitBtnid"));
			mClick("xpath", mGetPropertyFromFile("adh_View_ModifyPaySchSubmitBtnid"));
			mCustomWait(1000);
			// Click on Select Contract Terms Button
			mCustomWait(1000);
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_View_ModifyContTermsBtnid"));
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("adh_View_ModifyContTermsBtnid"));
			mWaitForVisible("css", mGetPropertyFromFile("adh_View_ModifyContTermsPopUpBoxid")); 	
			mCustomWait(1000);

			//Method to select multiple Contract Terms based on user criteria
			adh_mDynamicContractTermsTableGrid();


			mCustomWait(1000);

			mClick("css", mGetPropertyFromFile("adh_View_ModifyContTermsSubmitBtnid"));
			mCustomWait(1000);
			mscroll(0,300);
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_PaymentShceduleButtonid"));
			mClick("id", mGetPropertyFromFile("adh_View_ModifyApprovedChkboxid"));


			String ApproovedBy=mGetText("id", mGetPropertyFromFile("adh_View_ApprovedByid"),"value");
			System.out.println("Approved By::"+ApproovedBy);

			String ApprooveDate=mGetText("id", mGetPropertyFromFile("adh_View_ApprovedDateid"),"value");
			System.out.println("Approved Date::"+ApprooveDate);
			mCustomWait(1000);
			mSendKeys("id",mGetPropertyFromFile("adh_ApprovedRemarkid"),mGetPropertyFromFile("adh_ApprovedRemarkData"));
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("adh_View_ModifyFormSubmitBtnid"));
			mCustomWait(1000);

		}
		catch(Exception e){
			e.printStackTrace();
			exception = e.toString();	
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Current date method adh_View_ModifyApp() failed");
		}
	}



	public void adh_Gen_RejectionLetter()
	{

		try
		{   

			mSendKeys("id", textid, mGetPropertyFromFile("adh_LOIGenGenerateRejLetterdata"));

			if(mGetPropertyFromFile("adh_LOIGenGenerateRejLetterdata").equalsIgnoreCase("no")) 
			{
				mCustomWait(1200);
				mClick("css", imageid);

				//selecting rejection list
				mWaitForVisible("id", mGetPropertyFromFile("adh_LOIGenRejRemarksDocListid"));
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("adh_LOIGenRejRemarksDocListid"));
				mCustomWait(1000);				

				mSendKeys("id", mGetPropertyFromFile("adh_LOIGenRejRemarkid"), mGetPropertyFromFile("adh_LOIGenRejRemarkdata"));
				SCR_RejectionRemarks.add(mGetPropertyFromFile("adh_LOIGenRejRemarkdata"));

				//Submit Button
				mWaitForVisible("xpath", mGetPropertyFromFile("adh_LOIGenRejAppSubBtnid"));
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adh_LOIGenRejAppSubBtnid"));
				mCustomWait(1000);



			}

		}

		catch(Exception e)
		{
			e.printStackTrace();

		}

	}	



	public void adh_Gen_LOILetter()
	{
		try
		{   
			mSendKeys("id", textid, mGetPropertyFromFile("adh_LOIGenGenerateLoidata"));


			if(mGetPropertyFromFile("adh_LOIGenGenerateLoidata").equalsIgnoreCase("yes")) 
			{
				mCustomWait(1000);	
				mClick("css", imageid);
				System.out.println("Service Name is:::"+ADHserviceName);		
				mCustomWait(2500);

				try{
					String number=mGetText("id", mGetPropertyFromFile("adh_LOIGenAppNoid"), "value");
					System.out.println("The application no. is : "+number);

					if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
					{
						mAssert(number, applicationNo.get(CurrentinvoCount).toString(), "Application Number Does Not Matches at adh_loi generation.    Actual  :"+number+"     Expected     :"+applicationNo.get(CurrentinvoCount).toString());
					}
					else
					{
						mAssert(number, mGetPropertyFromFile("applicationNo"), "Application Number Does Not Matches at adh_loi generation.    Actual  :"+number+"     Expected     :"+mGetPropertyFromFile("applicationNo"));
					}

					String loidate=mGetText("id",  mGetPropertyFromFile("adh_LOIGenDateNoid"), "value");
					Scrutiny_LoiDate.add(loidate);
					System.out.println("The LOI date in Scrutiny form. is : "+Scrutiny_LoiDate);
					Adh_LOIDate.add(loidate);

					mCustomWait(1000);
					mSendKeys("id",mGetPropertyFromFile("adh_LOIGenLOIRemarksid"),mGetPropertyFromFile("adh_LOIGenLOIRemarksdata"));
					System.out.println("The LOI date in Scrutiny form. is : "+Scrutiny_LoiDate);

					mCustomWait(1000);
					mClick("id", mGetPropertyFromFile("adh_LOIGenSelectChargesid"));

					mCustomWait(1000);
					mTakeScreenShot();
					mCustomWait(1000);
					mClick("css", mGetPropertyFromFile("adh_LOIGenFormSubBtnid"));
					mCustomWait(2000);		
				}
				catch(Exception e){

					String loiPendingMsg=mGetText("css", mGetPropertyFromFile("adh_LOIGenPendingForContractMsgid"));
					System.out.println("Bill generation is pending Msg Is: "+loiPendingMsg);


					mWaitForVisible("css", mGetPropertyFromFile("adh_LOIGenBackBtnid"));
					mClick("css", mGetPropertyFromFile("adh_LOIGenBackBtnid"));
				}
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();

		}

	}	





	// Multiple Contract terms Selection Based on User Input for Scrutiny Process
	private void adh_mDynamicContractTermsTableGrid(){
		try{

			String no_of_cheks=mGetPropertyFromFile("adh_Scr_ContTermsSelectTermCount");
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

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}




	public void adh_Renewal_of_Advertising_Contract_ScrutinyView_Assertion(){
		try{

			ScrutinyServiceName = driver.findElement(By.cssSelector("#heading_wrapper > h1")).getText();
			System.out.println("Service Name in Scrutiny view form is"+ScrutinyServiceName);

			mWaitForVisible("id", mGetPropertyFromFile("adh_RrApplicantTitleid"));
			String adh_RcApplicantTitle=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_RrApplicantTitleid"));
			System.out.println("Applicant Title is in Scutiny : "+adh_RcApplicantTitle);
			RAC_Scr_adhApptitle.add(adh_RcApplicantTitle);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Applicant Title arraylist is in Scutiny ::: "+BH_scr_adhHoardingRemarks.get(CurrentinvoCount)+" "+",Expected from Application form is"+adh_rc_ApplicantTitle.get(CurrentinvoCount));
				mAssert(adh_rc_ApplicantTitle.get(CurrentinvoCount), RAC_Scr_adhApptitle.get(CurrentinvoCount), "Applicant Title in Renewal of Advertising Contract does not match with the actual::"+RAC_Scr_adhApptitle.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+adh_rc_ApplicantTitle.get(CurrentinvoCount));
			}


			//First name
			String adh_RrAplcntFirstName=mGetText("id", mGetPropertyFromFile("adh_RrAplcntFirstNameid"),"value");
			System.out.println("Applicant First Name is in Scutiny : "+adh_RrAplcntFirstName);
			RAC_scr_adhHoardingFname.add(adh_RrAplcntFirstName);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Applicant First name arraylist is in Scutiny ::: "+RAC_scr_adhHoardingFname.get(CurrentinvoCount)+" "+",Expected from Application form is"+adh_rc_ApplicantFname.get(CurrentinvoCount));
				mAssert(adh_rc_ApplicantFname.get(CurrentinvoCount), RAC_scr_adhHoardingFname.get(CurrentinvoCount), "Applicant First name in Renewal of Advertising Contract does not match with the actual::"+RAC_scr_adhHoardingFname.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+adh_rc_ApplicantFname.get(CurrentinvoCount));
			}
			//Middle Name
			mWaitForVisible("id", mGetPropertyFromFile("adh_RrAplcntMiddleNameid"));
			String adh_RrAplcntMiddleName=mGetText("id", mGetPropertyFromFile("adh_RrAplcntMiddleNameid"),"value");
			System.out.println("Applicant Middle Name is in Scutiny : "+adh_RrAplcntMiddleName);
			RAC_scr_adhHoardingMname.add(adh_RrAplcntMiddleName);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Applicant Middle name arraylist is in Scutiny ::: "+RAC_scr_adhHoardingMname.get(CurrentinvoCount)+" "+",Expected from Application form is"+adh_rc_ApplicantMname.get(CurrentinvoCount));
				mAssert(adh_rc_ApplicantMname.get(CurrentinvoCount), RAC_scr_adhHoardingMname.get(CurrentinvoCount), "Applicant Middle name in Renewal of Advertising Contract does not match with the actual::"+RAC_scr_adhHoardingMname.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+adh_rc_ApplicantMname.get(CurrentinvoCount));
			}


			//Last Name
			mWaitForVisible("id", mGetPropertyFromFile("adh_RrAplcntLastNameid"));
			mCustomWait(1000);
			String adh_RrAplcntLastName=mGetText("id", mGetPropertyFromFile("adh_RrAplcntLastNameid"),"value");
			RAC_scr_adhHoardingLname.add(adh_RrAplcntLastName);
			System.out.println("Applicant Last Name is in Scutiny : "+adh_RrAplcntLastName);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Applicant Last name arraylist is in Scutiny ::: "+RAC_scr_adhHoardingLname.get(CurrentinvoCount)+" "+",Expected from Application form is"+adh_rc_ApplicantLname.get(CurrentinvoCount));
				mAssert(adh_rc_ApplicantLname.get(CurrentinvoCount), RAC_scr_adhHoardingLname.get(CurrentinvoCount), "Applicant Last name in Renewal of Advertising Contract does not match with the actual::"+RAC_scr_adhHoardingLname.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+adh_rc_ApplicantLname.get(CurrentinvoCount));
			}
			String Fname = mGetPropertyFromFile("adh_RrAplcntFirstNameData");
			String Mname = mGetPropertyFromFile("adh_RrAplcntMiddleNameData");
			String Lname = mGetPropertyFromFile("adh_RrAplcntLastNameData");

			rrcapplName=Fname+" "+Mname+" "+Lname;
			System.out.println("applicant Full Name is : " + rrcapplName); 
			RAC_scr_adhHoardingLFullname.add(rrcapplName);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Applicant Full name arraylist is in Scutiny ::: "+RAC_scr_adhHoardingLFullname.get(CurrentinvoCount)+" "+",Expected from Application form is"+adh_rc_ApplicantFullname.get(CurrentinvoCount));
				mAssert(adh_rc_ApplicantFullname.get(CurrentinvoCount), RAC_scr_adhHoardingLFullname.get(CurrentinvoCount), "Applicant Full name in Renewal of Advertising Contract does not match with the actual::"+RAC_scr_adhHoardingLFullname.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+adh_rc_ApplicantFullname.get(CurrentinvoCount));
			}


			//Mobile Number
			mWaitForVisible("id", mGetPropertyFromFile("adh_RrAplcntMobileNoid"));
			mCustomWait(1000);
			String adh_RrAplcntMobileNo=mGetText("id", mGetPropertyFromFile("adh_RrAplcntMobileNoid"),"value");
			RAC_scr_adhHoardingMobNo.add(adh_RrAplcntMobileNo);
			System.out.println("Applicant Mobile No is in Scrutiny process : " + adh_RrAplcntMobileNo); 
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Applicant Mobile arraylist is in Scutiny ::: "+RAC_scr_adhHoardingMobNo.get(CurrentinvoCount)+" "+",Expected from Application form is"+adh_rc_ApplicantMbNo.get(CurrentinvoCount));
				mAssert(adh_rc_ApplicantMbNo.get(CurrentinvoCount), RAC_scr_adhHoardingMobNo.get(CurrentinvoCount), "Applicant Mobile in Renewal of Advertising Contract does not match with the actual::"+RAC_scr_adhHoardingMobNo.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+adh_rc_ApplicantMbNo.get(CurrentinvoCount));
			}

			//Email id
			mWaitForVisible("id", mGetPropertyFromFile("adh_RrAplcntEmailid"));
			String adh_RrAplcntEmail=mGetText("id", mGetPropertyFromFile("adh_RrAplcntEmailid"),"value");
			RAC_src_ApplicantEmail.add(adh_RrAplcntEmail);
			System.out.println("Applicant Email Id is in Scrutiny process : " + adh_RrAplcntEmail); 
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Applicant Email arraylist is in Scutiny ::: "+RAC_scr_adhHoardingEmail.get(CurrentinvoCount)+" "+",Expected from Application form is"+adh_rc_ApplicantEmail.get(CurrentinvoCount));
				mAssert(adh_rc_ApplicantEmail.get(CurrentinvoCount), RAC_scr_adhHoardingEmail.get(CurrentinvoCount), "Applicant Email in Renewal of Advertising Contract does not match with the actual::"+RAC_scr_adhHoardingEmail.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+adh_rc_ApplicantEmail.get(CurrentinvoCount));
			}

			//Renewal Date
			mWaitForVisible("id", mGetPropertyFromFile("adh_Scrrenewaldateid"));
			String adh_RrRenewalDate=mGetText("id", mGetPropertyFromFile("adh_Scrrenewaldateid"),"value");
			RAC_scr_adhHoardingRenewalDate.add(adh_RrRenewalDate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Renwal date arraylist is in Scutiny ::: "+RAC_scr_adhHoardingRenewalDate.get(CurrentinvoCount)+" "+",Expected from Application form is"+adh_rc_RenewalDate.get(CurrentinvoCount));
				mAssert(RAC_scr_adhHoardingRenewalDate.get(CurrentinvoCount), adh_rc_RenewalDate.get(CurrentinvoCount), "Renwal date in Renewal of Advertising Contract does not match with the actual::"+RAC_scr_adhHoardingRenewalDate.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+adh_rc_RenewalDate.get(CurrentinvoCount));
			}


			// Renewal From Date
			mCustomWait(500);
			String adh_RrRenewalFromDate=mGetText("id",mGetPropertyFromFile("adh_Scr_RenewalFromDateID"), "value");
			RAC_scr_adhHoardingFromDate.add(adh_RrRenewalFromDate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Renwal From date arraylist is in Scutiny ::: "+RAC_scr_adhHoardingFromDate.get(CurrentinvoCount)+" "+",Expected from Application form is"+adh_rc_BookingfromDate.get(CurrentinvoCount));
				mAssert(RAC_scr_adhHoardingFromDate.get(CurrentinvoCount), adh_rc_BookingfromDate.get(CurrentinvoCount), "Renwal from date in Renewal of Advertising Contract does not match with the actual::"+RAC_scr_adhHoardingFromDate.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+adh_rc_BookingfromDate.get(CurrentinvoCount));
			}

			mCustomWait(500);
			//Renewal To Date
			String adh_RrRenewaTODate=mGetText("id",mGetPropertyFromFile("adh_Scr_RenewalToDateID"), "value");
			RAC_scr_adhHoardingToDate.add(adh_RrRenewaTODate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Renwal to date arraylist is in Scutiny ::: "+RAC_scr_adhHoardingToDate.get(CurrentinvoCount)+" "+",Expected from Application form is"+adh_rc_BookingtoDate.get(CurrentinvoCount));
				mAssert(RAC_scr_adhHoardingToDate.get(CurrentinvoCount), adh_rc_BookingtoDate.get(CurrentinvoCount), "Renwal to date in Renewal of Advertising Contract does not match with the actual::"+RAC_scr_adhHoardingToDate.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+adh_rc_BookingtoDate.get(CurrentinvoCount));
			}

			//Sending remarks
			mWaitForVisible("id", mGetPropertyFromFile("adh_ScrRemarksid"));
			String adh_RrRemarks=mGetText("id",mGetPropertyFromFile("adh_ScrRemarksid"), "value");
			RAC_scr_adhHoardingRemarks.add(adh_RrRemarks);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Renwal date arraylist is in Scutiny ::: "+RAC_scr_adhHoardingRemarks.get(CurrentinvoCount)+" "+",Expected from Application form is"+adh_rc_ApplicantRemarks.get(CurrentinvoCount));
				mAssert(RAC_scr_adhHoardingRemarks.get(CurrentinvoCount), adh_rc_ApplicantRemarks.get(CurrentinvoCount), "Remarks in Renewal of Advertising Contract does not match with the actual::"+RAC_scr_adhHoardingRemarks.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+adh_rc_ApplicantRemarks.get(CurrentinvoCount));
			}



		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	// Scrutiny Process View and modify method for renewal of Advertisement contract
	public void adh_View_ModifyApp_For_renewalofAdvtContract(){
		try{
			mClick("css", imageid);
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_Scr_ViewnModiPaymentScedulebtnID"));

			adh_Renewal_of_Advertising_Contract_ScrutinyView_Assertion();

			String adh_RCContractAmt=mGetText("id",mGetPropertyFromFile("adh_ScrContractAmtid"), "value");
			double Amt = Double.parseDouble(adh_RCContractAmt);
			System.out.println("Renewal of Advertisiment contract Amount is in Scutiny for condition : "+Amt);
			if(Amt!=0.0){

				String adh_RCContractAmt1=mGetText("id",mGetPropertyFromFile("adh_ScrContractAmtid"), "value");
				System.out.println("Renewal of Advertisiment contract Amount is in Scutiny : "+adh_RCContractAmt1);
				RAC_scr_adhHoardingContractAmt.add(adh_RCContractAmt1);

				if(mGetPropertyFromFile("adh_RenewalContractDiscountYesNoData").equalsIgnoreCase("Yes"))

				{
					mSendKeys("id", mGetPropertyFromFile("adh_Scr_RenewalDiscountAmtID"), mGetPropertyFromFile("adh_Scr_RenewalDiscountAmtData"));
					String scr_adhHoardingDiscountAmt=mGetText("id", mGetPropertyFromFile("adh_Scr_RenewalDiscountAmtID"),"value");
					System.out.println("Renewal of Advertisiment contract Discount Amount is in Scutiny : "+scr_adhHoardingDiscountAmt);
					mTab("id", mGetPropertyFromFile("adh_Scr_RenewalDiscountAmtID"));
					RAC_scr_adhHoardingDiscountAmt.add(scr_adhHoardingDiscountAmt);
					if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
						mAssert(scr_adhHoardingDiscountAmt, mGetPropertyFromFile("adh_Scr_RenewalDiscountAmtData"), "Renewal of Advt Contract Discount Amount does not match with the actual::"+scr_adhHoardingDiscountAmt+"in scrutiny View form,Expected::"+mGetPropertyFromFile("adh_Scr_RenewalDiscountAmtData"));
					}
				}

				// Capturing Service Tax Amount
				String scr_adhHoardingServiceTaxAmt=mGetText("id", mGetPropertyFromFile("adh_Scr_RenewalServiceTaxAmtID"),"value");
				System.out.println("Renewal of Advertisiment contract Service Tax Amount is in Scutiny : "+scr_adhHoardingServiceTaxAmt);


				// Capturing Payble Amount and Assertion
				String scr_adhHoardingPaybleAmt=mGetText("id", mGetPropertyFromFile("adh_Scr_RenewalPaybleAmtID"),"value");
				System.out.println("Renewal of Advertisiment contract Payble Amount is in Scutiny111 : "+scr_adhHoardingPaybleAmt);
				scr_adhHoardingPaybleAmt=scr_adhHoardingPaybleAmt.replace(".00", " ");
				System.out.println("Renewal of Advertisiment contract Payble Amount is in Scutiny : "+scr_adhHoardingPaybleAmt);
				RAC_scr_adhHoardingPaybleAmt.add(scr_adhHoardingPaybleAmt);
				int payble1=Integer.parseInt(scr_adhHoardingPaybleAmt.trim());
				scrupdatedpayble_list=payble1;
				System.out.println("updatedpayble_list=>"+scrupdatedpayble_list);

				mClick("xpath", mGetPropertyFromFile("adh_Scr_ViewnModiPaymentScedulebtnID"));

				mWaitForVisible("id", mGetPropertyFromFile("adh_Scr_ViewnModiPaymentSchduleDescID"));
				mSelectDropDown("id", mGetPropertyFromFile("adh_Scr_ViewnModiPaymentSchduleDescID"), mGetPropertyFromFile("adh_Scr_ViewnModiPaymentSchduleDescData"));
				mCustomWait(1000);

				//Capturing Payment Schedule Data 
				String	schedule=mGetPropertyFromFile("adh_Scr_ViewnModiPaymentSchduleDescData");
				mCustomWait(500);
				if (schedule.equalsIgnoreCase("Monthly")||schedule.equalsIgnoreCase("Bi Monthly")||schedule.equalsIgnoreCase("Quarterly")||schedule.equalsIgnoreCase("Half Yearly")||schedule.equalsIgnoreCase("Yearwise")) {
					System.out.println("<====i m in payment shedule for"+ schedule +"Pattern====>" );
					if (mGetPropertyFromFile("Mod_paymentschedule").equalsIgnoreCase("yes")) {
						ScrutinyPaymentSchedule();
					}

				}else {
					System.out.println("<====i m in payment shedule for"+"Other mode"+"Pattern====>" );
					ScrutinyPaymentSchedule_Other();
				}

				mWaitForVisible("css", mGetPropertyFromFile("adh_Scr_ViewnModiPaymentScheduleSubmitbtnID"));
				mClick("css", mGetPropertyFromFile("adh_Scr_ViewnModiPaymentScheduleSubmitbtnID"));

				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adh_Scr_ViewnModiContractTermsbtnID"));
				mCustomWait(1000);
				mWaitForVisible("css", mGetPropertyFromFile("adh_Scr_ViewnModiContractTermsSubbtnID"));


				adh_mDynamicContractTermsTableGrid();

				mWaitForVisible("css", mGetPropertyFromFile("adh_Scr_ViewnModiContractTermsSubbtnID"));
				mClick("css", mGetPropertyFromFile("adh_Scr_ViewnModiContractTermsSubbtnID"));

				mWaitForVisible("id", mGetPropertyFromFile("adh_Scr_ViewnModiApprovedByID"));
				mClick("id", mGetPropertyFromFile("adh_Scr_ViewnModiApprovedByID"));

				String ApproverName=mGetText("id", mGetPropertyFromFile("adh_Scr_ViewnModiApprovedByNameID"), "value");
				System.out.println("Approver Name is"+ApproverName);


				String ApprovalDate=mGetText("id", mGetPropertyFromFile("adh_Scr_ViewnModiApprovedByDateID"), "value");
				System.out.println("Approver Date is"+ApprovalDate);

				mClick("css", mGetPropertyFromFile("adh_Scr_ViewnModiFormSubmitbtnID"));


			}
			else{

				mSendKeys("id",mGetPropertyFromFile("adh_ScrContractAmtid"), mGetPropertyFromFile("adh_RenewalContractAmtData"));
				mTab("id",mGetPropertyFromFile("adh_ScrContractAmtid"));
				String adh_RCContractAmt1=mGetText("id",mGetPropertyFromFile("adh_ScrContractAmtid"), "value");
				System.out.println("Renewal of Advertisiment contract Amount is in Scutiny : "+adh_RCContractAmt1);
				RAC_scr_adhHoardingContractAmt.add(adh_RCContractAmt1);

				if(mGetPropertyFromFile("adh_RenewalContractDiscountYesNoData").equalsIgnoreCase("Yes"))

				{
					mSendKeys("id", mGetPropertyFromFile("adh_Scr_RenewalDiscountAmtID"), mGetPropertyFromFile("adh_Scr_RenewalDiscountAmtData"));
					String scr_adhHoardingDiscountAmt=mGetText("id", mGetPropertyFromFile("adh_Scr_RenewalDiscountAmtID"),"value");
					mTab("id", mGetPropertyFromFile("adh_Scr_RenewalDiscountAmtID"));
					System.out.println("Renewal of Advertisiment contract Discount Amount is in Scutiny : "+scr_adhHoardingDiscountAmt);
					RAC_scr_adhHoardingDiscountAmt.add(scr_adhHoardingDiscountAmt);
					if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
						mAssert(scr_adhHoardingDiscountAmt, mGetPropertyFromFile("adh_Scr_RenewalDiscountAmtData"), "Renewal of Advt Contract Discount Amount does not match with the actual::"+scr_adhHoardingDiscountAmt+"in scrutiny View form,Expected::"+mGetPropertyFromFile("adh_Scr_RenewalDiscountAmtData"));
					}
				}

				// Capturing Service Tax Amount
				String scr_adhHoardingServiceTaxAmt1=mGetText("id", mGetPropertyFromFile("adh_Scr_RenewalServiceTaxAmtID"),"value");
				System.out.println("Renewal of Advertisiment contract Service Tax Amount is in Scutiny : "+scr_adhHoardingServiceTaxAmt1);


				// Capturing Payble Amount and Assertion
				String scr_adhHoardingPaybleAmt=mGetText("id", mGetPropertyFromFile("adh_Scr_RenewalPaybleAmtID"),"value");
				System.out.println("Renewal of Advertisiment contract Payble Amount is in Scutiny : "+scr_adhHoardingPaybleAmt);

				scr_adhHoardingPaybleAmt=scr_adhHoardingPaybleAmt.replace(".00", " ");
				System.out.println("Renewal of Advertisiment contract Payble Amount is in Scutiny : "+scr_adhHoardingPaybleAmt);
				RAC_scr_adhHoardingPaybleAmt.add(scr_adhHoardingPaybleAmt);
				int payble1=Integer.parseInt(scr_adhHoardingPaybleAmt.trim());
				scrupdatedpayble_list=payble1;
				System.out.println("updatedpayble_list=>"+scrupdatedpayble_list);
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				mWaitForVisible("xpath", mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurcationbtnID"));
				mClick("xpath", mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurcationbtnID"));


				//Logic for Add multiple Tax descriptions and Amount data
				String TaxDescription= mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurTax1DescData");
				String TaxAmt= mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurTax1AmtData");

				ArrayList TaxDescriptionList = new ArrayList(Arrays.asList(TaxDescription.split(",")));
				ArrayList TaxAmtList = new ArrayList(Arrays.asList(TaxAmt.split(",")));


				for(int i=0; i<TaxDescriptionList.size();i++)
				{


					// Click on Add Button
					//mWaitForVisible("linkText", mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurcaAddbtnID"));
					mCustomWait(1000);
					/*WebElement we = driver.findElements(By.xpath("path "));

					Actions a = new Actions();
					a.doubleClick(we).perform();
					a.doubleClick(we).preform();*/


					List<WebElement> a=driver.findElements(By.linkText("Add")) ;
					mCustomWait(500);
					a.get(1).click();
					mCustomWait(500);

					//driver.findElement(By.linkText("Add")).click();
					mCustomWait(1000);
					//mClick("linkText",  mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurcaAddbtnID"));


					//Sending  Tax Description
					mWaitForVisible("id", mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurTaxDescID"));
					mCustomWait(1000);
					mSelectDropDown("id",  mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurTaxDescID"),TaxDescriptionList.get(i).toString());
					String TaxDescription1=mCaptureSelectedValue("id",  mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurTaxDescID"));
					System.out.println("Tax Description is"+TaxDescription1);

					//Sending Tax amount
					mCustomWait(1000);
					mSendKeys("id",  mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurTaxAmtID"), TaxAmtList.get(i).toString());
					String TaxDescAmt=mGetText("id",  mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurTaxAmtID"), "value");
					System.out.println("Tax Amount is"+TaxDescAmt);
					mTakeScreenShot();
					mTab("id", mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurTaxAmtID")); 

				}

				//Logic For Adding Discount if Applicable in Amount Bifurcation
				if(mGetPropertyFromFile("adh_RenewalContractDiscountYesNoData").equalsIgnoreCase("yes")){

					// Click on Add Button
					/*mWaitForVisible("id", mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurcaAddbtnID"));
					mClick("id",  mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurcaAddbtnID"));
					 */
					List<WebElement> a=driver.findElements(By.linkText("Add")) ;
					mCustomWait(500);
					a.get(1).click();
					mCustomWait(500);
					//Selecting and Capturing Discount Description
					mWaitForVisible("id", mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurTaxDescID"));
					mCustomWait(1000);
					mSelectDropDown("id",  mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurTaxDescID"), mGetPropertyFromFile("adh_Scr_RenewalViewAndModifyTaxDiscountDescData"));
					String TaxDescription2=mCaptureSelectedValue("id",  mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurTaxDescID"));
					System.out.println("Tax Decsription is"+TaxDescription2);

					//Sending Discount amount
					mCustomWait(1000);
					mSendKeys("id",  mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurTaxAmtID"), mGetPropertyFromFile("adh_Scr_RenewalDiscountAmtData"));
					String TaxDescAmt1=mGetText("id",  mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurTaxAmtID"), "value");
					System.out.println("Tax Amount is"+TaxDescAmt1);
					mTakeScreenShot();
					mTab("id", mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurTaxAmtID")); 
				}

				// Capturing Amount Bifurcation total Amount
				String abta=mGetText("id",mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurTotalAmtID"),"value");
				System.out.println("total amount is : "+ abta);	
				mTakeScreenShot();

				//Click on Amount Bifurcation Submit button
				mWaitForVisible("css", mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurSubmitBtnID"));
				mClick("css", mGetPropertyFromFile("adh_Scr_ViewnModiAmtBifurSubmitBtnID"));

				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				mCustomWait(1500);
				mWaitForVisible("xpath", mGetPropertyFromFile("adh_Scr_ViewnModiPaymentScedulebtnID"));
				mClick("xpath", mGetPropertyFromFile("adh_Scr_ViewnModiPaymentScedulebtnID"));
				mCustomWait(1500);
				mWaitForVisible("id", mGetPropertyFromFile("adh_Scr_ViewnModiPaymentSchduleDescID"));
				mSelectDropDown("id", mGetPropertyFromFile("adh_Scr_ViewnModiPaymentSchduleDescID"), mGetPropertyFromFile("adh_Scr_ViewnModiPaymentSchduleDescData"));
				mCustomWait(1000);

				//Capturing Payment Schedule Data 
				String	schedule=mGetPropertyFromFile("adh_Scr_ViewnModiPaymentSchduleDescData");
				mCustomWait(500);
				if (schedule.equalsIgnoreCase("Monthly")||schedule.equalsIgnoreCase("Bi Monthly")||schedule.equalsIgnoreCase("Quarterly")||schedule.equalsIgnoreCase("Half Yearly")||schedule.equalsIgnoreCase("Yearwise")) {
					System.out.println("<====i m in payment shedule for"+ schedule +"Pattern====>" );
					if (mGetPropertyFromFile("Mod_paymentschedule").equalsIgnoreCase("yes")) {
						ScrutinyPaymentSchedule();
					}

				}else {
					System.out.println("<====i m in payment shedule for"+"Other mode"+"Pattern====>" );
					ScrutinyPaymentSchedule_Other();
				}

				mWaitForVisible("css", mGetPropertyFromFile("adh_Scr_ViewnModiPaymentScheduleSubmitbtnID"));
				mClick("css", mGetPropertyFromFile("adh_Scr_ViewnModiPaymentScheduleSubmitbtnID"));

				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("adh_Scr_ViewnModiContractTermsbtnID"));
				mCustomWait(1000);
				mWaitForVisible("css", mGetPropertyFromFile("adh_Scr_ViewnModiContractTermsSubbtnID"));


				adh_mDynamicContractTermsTableGrid();
				mCustomWait(1000);
				mWaitForVisible("css", mGetPropertyFromFile("adh_Scr_ViewnModiContractTermsSubbtnID"));
				mClick("css", mGetPropertyFromFile("adh_Scr_ViewnModiContractTermsSubbtnID"));
				mCustomWait(2000);
				mWaitForVisible("id", mGetPropertyFromFile("adh_Scr_ViewnModiApprovedByID"));
				mClick("id", mGetPropertyFromFile("adh_Scr_ViewnModiApprovedByID"));

				String ApproverName=mGetText("id", mGetPropertyFromFile("adh_Scr_ViewnModiApprovedByNameID"), "value");
				System.out.println("Approver Name is"+ApproverName);


				String ApprovalDate=mGetText("id", mGetPropertyFromFile("adh_Scr_ViewnModiApprovedByDateID"), "value");
				System.out.println("Approver Date is"+ApprovalDate);

				mClick("css", mGetPropertyFromFile("adh_Scr_ViewnModiFormSubmitbtnID"));

			}

		}
		catch(Exception e){
			e.printStackTrace();
			exception = e.toString();	
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Current date method adh_View_ModifyApp() failed");
		}
	}








	//Setup of Hoarding for display of Advertisement search criteria

	public void SetUpOfHoardingLocationSearchCriria(){
		try{
			mWaitForVisible("linkText", mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocSearchbtnPoPUpID"));
			if(mGetPropertyFromFile("adh_SetUpHoardingLocationSearchByData").equalsIgnoreCase("LocationCode"))
			{
				mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocationCodePoPUpID"),mGetPropertyFromFile("adh_SetUpHoardingLocationCodeData"));
				mWaitForVisible("linkText", mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocSearchbtnPoPUpID"));
				mCustomWait(1000);
				mClick("linkText", mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocSearchbtnPoPUpID"));

			}

			else if(mGetPropertyFromFile("adh_SetUpHoardingLocationSearchByData").equalsIgnoreCase("LocationAddress"))
			{
				mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocationAddPoPUpID"),mGetPropertyFromFile("adh_SetUpHoardingLocationAddressData"));
				mWaitForVisible("linkText", mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocSearchbtnPoPUpID"));
				mCustomWait(1000);
				mClick("linkText", mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocSearchbtnPoPUpID"));
			}
			else if(mGetPropertyFromFile("adh_SetUpHoardingLocationSearchByData").equalsIgnoreCase("Both"))
			{
				mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocationCodePoPUpID"),mGetPropertyFromFile("adh_SetUpHoardingLocationCodeData"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocationAddPoPUpID"),mGetPropertyFromFile("adh_SetUpHoardingLocationAddressData"));
			}

			else{
				//Searching application
				mWaitForVisible("linkText", mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocSearchbtnPoPUpID"));

				mClick("linkText", mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocSearchbtnPoPUpID"));

			}


			mCustomWait(1000);
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocradiobtnID"));
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocradiobtnID"));
			mTakeScreenShot();

			mWaitForVisible("xpath", mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocSubmitbtnPoPUpID"));
			mClick("xpath", mGetPropertyFromFile("adh_SetUpOfHoardingBookingLocSubmitbtnPoPUpID"));

			mCustomWait(500);

		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in billGenerationSearchCriria");
		}			
	}	

	//Setup of Hoarding for display of Advertisement Hoarding Detail reading table at application form
	private void adh_mDynamicAddHoardingDeatilsTableGrid(){

		try{
			WebElement table = driver.findElement(By.id("gview_gridHoardingAndAdvertisementSetupDetails"));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount =rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

			List<WebElement> cols = rows.get(1).findElements(By.xpath("td"));
			int colcount= cols.size();
			System.out.println(colcount);

			for(int a=0;a<rwcount;a++)			
			{
				// Iterate to get the value of each cell in table along with element id
				for(int b=0;b<colcount;b++)
				{
					if(a>=1)
					{
						if(b==0)
						{
							String srNo = rows.get(a).findElement(By.xpath("td[1]")).getText();
							System.out.println("Sr No : "+srNo);
						}
						if(b==1)
						{
							String hoardingDescription = rows.get(a).findElement(By.xpath("td[2]")).getText();
							System.out.println("Hoarding Description is : "+hoardingDescription);
						}
						if(b==2)
						{
							String lenght = rows.get(a).findElement(By.xpath("td[3]")).getText();
							System.out.println("Hoarding length is : "+lenght);
						}
						if(b==3)
						{
							String breadth = rows.get(a).findElement(By.xpath("td[4]")).getText();
							System.out.println("Hoarding Breadth Type is : "+breadth);
						}
						if(b==4)
						{
							String elevation = rows.get(a).findElement(By.xpath("td[5]")).getText();
							System.out.println("Hoarding Elevation is : "+elevation);
						}
						if(b==5)
						{
							String gisNo = rows.get(a).findElement(By.xpath("td[6]")).getText();
							System.out.println("Hoarding GIS no is : "+gisNo);													
						}

						if(b==6)
						{
							String HoardingType = rows.get(a).findElement(By.xpath("td[7]")).getText();
							System.out.println("Hoarding Type is : "+HoardingType);
						}


						if(b==7)
						{
							String HoardingAmt = rows.get(a).findElement(By.xpath("td[8]")).getText();
							System.out.println("Hoarding Amount is : "+HoardingAmt);
						}
					}

				}
			}
		}


		catch(Exception e){
			e.printStackTrace();
		}
	}



	public void adh_Set_Up_Of_Hoarding_ScrutinyView_Assertion(){
		try{

			ScrutinyServiceName = driver.findElement(By.cssSelector("#middle_right > h1")).getText();
			System.out.println("Service Name in Scrutiny view form is"+ScrutinyServiceName);

			String adh_SetUpOfHoardingTitle=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_SetUpOfHoardingTitleID"));
			scr_suoh_ApplicantTitle.add(adh_SetUpOfHoardingTitle);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Set Up Of Hoarding Applicant Title in scrutiny ::: "+scr_suoh_ApplicantTitle.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_ApplicantTitle.get(CurrentinvoCount));
				mAssert(scr_suoh_ApplicantTitle.get(CurrentinvoCount), suoh_ApplicantTitle.get(CurrentinvoCount), "Set Up Of Hoarding Applicant Title in scrutiny process dose not match Actual::"+scr_suoh_ApplicantTitle.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+suoh_ApplicantTitle.get(CurrentinvoCount));
			}


			String adh_SetUpOfHoardingFname=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingFnameID"), "value");
			scr_suoh_ApplicantFname.add(adh_SetUpOfHoardingFname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Set Up Of Hoarding Applicant First Name in scrutiny ::: "+scr_suoh_ApplicantFname.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_ApplicantFname.get(CurrentinvoCount));
				mAssert(scr_suoh_ApplicantFname.get(CurrentinvoCount), suoh_ApplicantFname.get(CurrentinvoCount), "Set Up Of Hoarding Applicant First Name in scrutiny process dose not match Actual::"+scr_suoh_ApplicantFname.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+suoh_ApplicantFname.get(CurrentinvoCount));
			}


			String adh_SetUpOfHoardingMname=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingMidnameID"), "value");
			scr_suoh_ApplicantMname.add(adh_SetUpOfHoardingMname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Set Up Of Hoarding Applicant Middle Name in scrutiny ::: "+scr_suoh_ApplicantMname.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_ApplicantMname.get(CurrentinvoCount));
				mAssert(scr_suoh_ApplicantMname.get(CurrentinvoCount), suoh_ApplicantMname.get(CurrentinvoCount), "Set Up Of Hoarding Applicant Middle Name in scrutiny process dose not match Actual::"+scr_suoh_ApplicantMname.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+suoh_ApplicantMname.get(CurrentinvoCount));
			}

			String adh_SetUpOfHoardingLname=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingLnameID"), "value");
			scr_suoh_ApplicantLname.add(adh_SetUpOfHoardingLname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Set Up Of Hoarding Applicant Last Name in scrutiny ::: "+scr_suoh_ApplicantLname.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_ApplicantLname.get(CurrentinvoCount));
				mAssert(scr_suoh_ApplicantLname.get(CurrentinvoCount), suoh_ApplicantLname.get(CurrentinvoCount), "Set Up Of Hoarding Applicant Last Name in scrutiny process dose not match Actual::"+scr_suoh_ApplicantLname.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+suoh_ApplicantLname.get(CurrentinvoCount));
			}

			String Fname = mGetPropertyFromFile("adh_SetUpOfHoardingFnameData");
			String Mname = mGetPropertyFromFile("adh_SetUpOfHoardingMnameData");
			String Lname = mGetPropertyFromFile("adh_SetUpOfHoardingLnameData");

			boeapplName=Fname+" "+Mname+" "+Lname;
			System.out.println("applName is : " + boeapplName); 
			suoh_ApplicantFullname.add(boeapplName);

			String adh_SetUpOfHoardingMobNo=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingMobNoID"), "value");
			scr_suoh_ApplicantMbNo.add(adh_SetUpOfHoardingMobNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Set Up Of Hoarding Applicant Mobile No in scrutiny ::: "+suoh_ApplicantMbNo.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_ApplicantMbNo.get(CurrentinvoCount));
				mAssert(suoh_ApplicantMbNo.get(CurrentinvoCount), suoh_ApplicantMbNo.get(CurrentinvoCount), "Set Up Of Hoarding Applicant Mobile No in scrutiny process dose not match Actual::"+scr_suoh_ApplicantMbNo.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+suoh_ApplicantMbNo.get(CurrentinvoCount));
			}


			String adh_SetUpOfHoardingEmail=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingEmailID"), "value");
			scr_suoh_ApplicantEmail.add(adh_SetUpOfHoardingEmail);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Set Up Of Hoarding Applicant Email ID in scrutiny ::: "+scr_suoh_ApplicantEmail.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_ApplicantEmail.get(CurrentinvoCount));
				mAssert(scr_suoh_ApplicantEmail.get(CurrentinvoCount), suoh_ApplicantEmail.get(CurrentinvoCount), "Set Up Of Hoarding Applicant Email ID in scrutiny process dose not match Actual::"+scr_suoh_ApplicantEmail.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+suoh_ApplicantEmail.get(CurrentinvoCount));
			}


			mTakeScreenShot();

			String adh_SetUpOfHoardingApplicationNo=mGetText("id", mGetPropertyFromFile("adh_Scr_SetUpOfHoardingAppNoID"), "value");
			scr_suoh_ApplicantionNo.add(adh_SetUpOfHoardingApplicationNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Set Up Of Hoarding Applicantion No in scrutiny ::: "+scr_suoh_ApplicantionNo.get(CurrentinvoCount)+" "+",Expected from Application form is"+ApplicationNoADH.get(CurrentinvoCount));
				mAssert(scr_suoh_ApplicantionNo.get(CurrentinvoCount), ApplicationNoADH.get(CurrentinvoCount), "Set Up Of Hoarding Applicantion No in scrutiny process dose not match Actual::"+scr_suoh_ApplicantionNo.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+ApplicationNoADH.get(CurrentinvoCount));
			}


			String BookingfrDate=mGetText("id", mGetPropertyFromFile("adh_Scr_SetUpOfHoardingFromDateID"),"value");
			System.out.println("Booking From date is " +BookingfrDate);
			scr_suoh_BookingfromDate.add(mGetPropertyFromFile("adh_SetUpOfHoardingFromDateData"));
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Set Up Of Hoarding Applicant Hoarding Booking To date in scrutiny ::: "+scr_suoh_BookingfromDate.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_BookingfromDate.get(CurrentinvoCount));
				mAssert(scr_suoh_BookingfromDate.get(CurrentinvoCount), suoh_BookingfromDate.get(CurrentinvoCount), "Set Up Of Hoarding Applicant Hoarding Booking To date in scrutiny process dose not match Actual::"+scr_suoh_BookingfromDate.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+suoh_BookingfromDate.get(CurrentinvoCount));
			}

			mCustomWait(1000);
			String BookingtoDate=mGetText("id", mGetPropertyFromFile("adh_Scr_SetUpOfHoardingToDateID"), "value");
			scr_suoh_BookingtoDate.add(BookingtoDate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Set Up Of Hoarding Applicant Hoarding Booking To date in scrutiny ::: "+scr_suoh_BookingtoDate.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_BookingtoDate.get(CurrentinvoCount));
				mAssert(scr_suoh_BookingtoDate.get(CurrentinvoCount), suoh_BookingtoDate.get(CurrentinvoCount), "Set Up Of Hoarding Applicant Hoarding Booking To date in scrutiny process dose not match Actual::"+scr_suoh_BookingtoDate.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+suoh_BookingtoDate.get(CurrentinvoCount));
			}
			//For Existing Location
			if(driver.findElement(By.id("ckj")).isSelected())
			{
				mWaitForVisible("css",  mGetPropertyFromFile("adh_Scr_SetUpOfHoardingBookingLocAddressID"));
				String readLocationAddress=mGetText("css", mGetPropertyFromFile("adh_Scr_SetUpOfHoardingBookingLocAddressID"));
				scr_suoh_LocationAddress.add(readLocationAddress);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					//System.out.println("Captured Set Up Of Hoarding Location Address in scrutiny ::: "+scr_suoh_LocationAddress.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_LocationAddress.get(CurrentinvoCount));
					//mAssert(scr_suoh_LocationAddress.get(CurrentinvoCount),suoh_LocationAddress.get(CurrentinvoCount), "Set Up Of Hoarding Location Address dose not match Actual::"+scr_suoh_LocationAddress+"Expected::"+suoh_LocationAddress);
				}

				String readLocationCode=mGetText("css", mGetPropertyFromFile("adh_Scr_SetUpOfHoardingBookingLocCodeID"));
				scr_suoh_LocationAddress.add(readLocationCode);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					System.out.println("Captured Set Up Of Hoarding Location Code in scrutiny ::: "+scr_suoh_LocationCode.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_LocationCode.get(CurrentinvoCount));
					//mAssert(scr_suoh_LocationCode.get(CurrentinvoCount),suoh_LocationCode.get(CurrentinvoCount), "Set Up Of Hoarding Location Code dose not match Actual::"+scr_suoh_LocationCode+"Expected::"+suoh_LocationCode);
				}


			}
			//For New Location
			else
			{
				mWaitForVisible("css", mGetPropertyFromFile("adh_Scr_SetUpOfHoardingWardID"));
				String ward=mGetText("css", mGetPropertyFromFile("adh_Scr_SetUpOfHoardingWardID"));
				scr_suoh_Ward.add(ward);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					System.out.println("Captured Set Up Of Hoarding ward in scrutiny ::: "+scr_suoh_Ward.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_Ward.get(CurrentinvoCount));
					//mAssert(scr_suoh_Ward.get(CurrentinvoCount),suoh_Ward.get(CurrentinvoCount), "Set Up Of Hoarding Location ward dose not match Actual::"+scr_suoh_Ward.get(CurrentinvoCount)+"Expected::"+suoh_Ward.get(CurrentinvoCount));
				}


				String roadtype=mGetText("css", mGetPropertyFromFile("adh_Scr_SetUpOfHoardingRoadTypeID"));
				scr_suoh_RoadType.add(roadtype);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					System.out.println("Captured Set Up Of Hoarding Road Type in scrutiny ::: "+scr_suoh_RoadType.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_RoadType.get(CurrentinvoCount));
					//mAssert(scr_suoh_RoadType.get(CurrentinvoCount),suoh_RoadType.get(CurrentinvoCount), "Set Up Of Hoarding Location Road Type dose not match Actual::"+scr_suoh_RoadType.get(CurrentinvoCount)+"Expected::"+suoh_RoadType.get(CurrentinvoCount));
				}


				String eastlandmark=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingEastLandmarkID"),"value");
				scr_suoh_EastLandmark.add(eastlandmark);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					System.out.println("Captured Set Up Of Hoarding East Landmark in scrutiny ::: "+scr_suoh_EastLandmark.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_EastLandmark.get(CurrentinvoCount));
					//mAssert(scr_suoh_EastLandmark.get(CurrentinvoCount),suoh_EastLandmark.get(CurrentinvoCount), "Set Up Of Hoarding Location East Landmark dose not match Actual::"+scr_suoh_EastLandmark.get(CurrentinvoCount)+"Expected::"+suoh_EastLandmark.get(CurrentinvoCount));
				}



				String westlandmark=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingWestLandmarkID"),"value");
				scr_suoh_WestLandmark.add(westlandmark);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					System.out.println("Captured Set Up Of Hoarding West Landmark in scrutiny ::: "+scr_suoh_WestLandmark.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_WestLandmark.get(CurrentinvoCount));
					mAssert(scr_suoh_WestLandmark.get(CurrentinvoCount),suoh_WestLandmark.get(CurrentinvoCount), "Set Up Of Hoarding Location West Landmark dose not match Actual::"+scr_suoh_WestLandmark.get(CurrentinvoCount)+"Expected::"+suoh_WestLandmark.get(CurrentinvoCount));
				}

				String northlandmark=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingNorthLandmarkID"),"value");
				scr_suoh_NorthLandmark.add(northlandmark);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					System.out.println("Captured Set Up Of Hoarding North Landmark in scrutiny ::: "+scr_suoh_NorthLandmark.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_NorthLandmark.get(CurrentinvoCount));
					mAssert(scr_suoh_NorthLandmark.get(CurrentinvoCount),suoh_NorthLandmark.get(CurrentinvoCount), "Set Up Of Hoarding Location North Landmark dose not match Actual::"+scr_suoh_NorthLandmark.get(CurrentinvoCount)+"Expected::"+suoh_NorthLandmark.get(CurrentinvoCount));
				}

				String southlandmark=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingSouthLandmarkID"),"value");
				scr_suoh_SouthLandmark.add(southlandmark);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					System.out.println("Captured Set Up Of Hoarding South Landmark in scrutiny ::: "+scr_suoh_SouthLandmark.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_SouthLandmark.get(CurrentinvoCount));
					mAssert(scr_suoh_SouthLandmark.get(CurrentinvoCount),suoh_SouthLandmark.get(CurrentinvoCount), "Set Up Of Hoarding Location South Landmark dose not match Actual::"+scr_suoh_SouthLandmark.get(CurrentinvoCount)+"Expected::"+suoh_SouthLandmark.get(CurrentinvoCount));
				}

				String GISNo=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingGISNoID"),"value");
				scr_suoh_GISNo.add(GISNo);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					System.out.println("Captured Set Up Of Hoarding GIS NO in scrutiny ::: "+scr_suoh_GISNo.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_GISNo.get(CurrentinvoCount));
					mAssert(scr_suoh_GISNo.get(CurrentinvoCount),suoh_GISNo.get(CurrentinvoCount), "Set Up Of Hoarding Location GIS NO dose not match Actual::"+scr_suoh_GISNo.get(CurrentinvoCount)+"Expected::"+suoh_GISNo.get(CurrentinvoCount));
				}

				String plotNo=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingPlotNoID"),"value");
				scr_suoh_PlotNo.add(plotNo);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					System.out.println("Captured Set Up Of Hoarding Plot No in scrutiny ::: "+scr_suoh_PlotNo.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_PlotNo.get(CurrentinvoCount));
					mAssert(scr_suoh_PlotNo.get(CurrentinvoCount),suoh_PlotNo.get(CurrentinvoCount), "Set Up Of Hoarding Location Plot No dose not match Actual::"+scr_suoh_PlotNo.get(CurrentinvoCount)+"Expected::"+suoh_PlotNo.get(CurrentinvoCount));
				}

				String houseNo=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingHouseNoID"),"value");
				scr_suoh_HouseNo.add(houseNo);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					System.out.println("Captured Set Up Of Hoarding House No in scrutiny ::: "+scr_suoh_HouseNo.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_HouseNo.get(CurrentinvoCount));
					mAssert(scr_suoh_HouseNo.get(CurrentinvoCount),suoh_HouseNo.get(CurrentinvoCount), "Set Up Of Hoarding Location House No dose not match Actual::"+scr_suoh_HouseNo.get(CurrentinvoCount)+"Expected::"+suoh_HouseNo.get(CurrentinvoCount));
				}


				String area=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingAreaID"),"value");
				scr_suoh_Area.add(area);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					System.out.println("Captured Set Up Of Hoarding area in scrutiny ::: "+scr_suoh_Area.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_Area.get(CurrentinvoCount));
					mAssert(scr_suoh_Area.get(CurrentinvoCount),suoh_Area.get(CurrentinvoCount), "Set Up Of Hoarding Location area dose not match Actual::"+scr_suoh_HouseNo.get(CurrentinvoCount)+"Expected::"+suoh_Area.get(CurrentinvoCount));
				}


				String societyname=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingSocietyNameID"),"value");
				scr_suoh_SocietyName.add(societyname);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					System.out.println("Captured Set Up Of Hoarding Society Name in scrutiny ::: "+scr_suoh_SocietyName.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_SocietyName.get(CurrentinvoCount));
					mAssert(scr_suoh_SocietyName.get(CurrentinvoCount),suoh_SocietyName.get(CurrentinvoCount), "Set Up Of Hoarding Location Society Name dose not match Actual::"+scr_suoh_SocietyName.get(CurrentinvoCount)+"Expected::"+suoh_SocietyName.get(CurrentinvoCount));
				}


				String RoadName=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingRoadNameID"),"value");
				scr_suoh_RoadName.add(RoadName);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					System.out.println("Captured Set Up Of Hoarding Society Name in scrutiny ::: "+scr_suoh_RoadName.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_RoadName.get(CurrentinvoCount));
					mAssert(scr_suoh_RoadName.get(CurrentinvoCount),suoh_RoadName.get(CurrentinvoCount), "Set Up Of Hoarding Location Society Name dose not match Actual::"+scr_suoh_RoadName.get(CurrentinvoCount)+"Expected::"+suoh_RoadName.get(CurrentinvoCount));
				}

				mCustomWait(500);

				String BuildingName=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingBuildingNameID"),"value");
				scr_suoh_BuildingName.add(BuildingName);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					System.out.println("Captured Set Up Of Hoarding Society Name in scrutiny ::: "+scr_suoh_BuildingName.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_BuildingName.get(CurrentinvoCount));
					mAssert(scr_suoh_BuildingName.get(CurrentinvoCount),suoh_BuildingName.get(CurrentinvoCount), "Set Up Of Hoarding Location Society Name dose not match Actual::"+scr_suoh_BuildingName.get(CurrentinvoCount)+"Expected::"+suoh_BuildingName.get(CurrentinvoCount));
				}


				String newPinCode=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingNewPinCodeID"),"value");
				scr_suoh_NewPinCode.add(newPinCode);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					System.out.println("Captured Set Up Of Hoarding Society Name in scrutiny ::: "+scr_suoh_NewPinCode.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_NewPinCode.get(CurrentinvoCount));
					mAssert(scr_suoh_NewPinCode.get(CurrentinvoCount),suoh_NewPinCode.get(CurrentinvoCount), "Set Up Of Hoarding Location Society Name dose not match Actual::"+scr_suoh_NewPinCode.get(CurrentinvoCount)+"Expected::"+suoh_NewPinCode.get(CurrentinvoCount));
				}
			}

			String readRemarks=mGetText("id", mGetPropertyFromFile("adh_SetUpOfHoardingRemarksID"),"value");
			scr_suoh_ApplicantRemarks.add(readRemarks);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Set Up Of Hoarding Remarks in scrutiny ::: "+scr_suoh_ApplicantRemarks.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_ApplicantRemarks.get(CurrentinvoCount));
				mAssert(scr_suoh_ApplicantRemarks.get(CurrentinvoCount),suoh_ApplicantRemarks.get(CurrentinvoCount), "Set Up Of Hoarding Remarks dose not match Actual::"+scr_suoh_ApplicantRemarks+"Expected::"+suoh_ApplicantRemarks);
			}

			String amount=mGetText("id", mGetPropertyFromFile("adh_Scr_SetUpOfHoardingBookingAmountID"),"value");
			scr_suoh_Amount.add(amount);
			/*if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Set Up Of Hoarding Remarks in scrutiny ::: "+scr_suoh_Amount.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_Amount.get(CurrentinvoCount));
				mAssert(scr_suoh_Amount.get(CurrentinvoCount),suoh_Amount.get(CurrentinvoCount), "Set Up Of Hoarding Remarks dose not match Actual::"+scr_suoh_Amount+"Expected::"+suoh_Amount);
			}*/

			String Discount=mGetText("id", mGetPropertyFromFile("adh_Scr_SetUpOfHoardingBookingDiscountID"),"value");
			scr_suoh_Discount.add(Discount);
			/*if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Set Up Of Hoarding Remarks in scrutiny ::: "+scr_suoh_Amount.get(CurrentinvoCount)+" "+",Expected from Application form is"+suoh_Amount.get(CurrentinvoCount));
				mAssert(scr_suoh_Amount.get(CurrentinvoCount),suoh_Amount.get(CurrentinvoCount), "Set Up Of Hoarding Remarks dose not match Actual::"+scr_suoh_Amount+"Expected::"+suoh_Amount);
			}*/

			String PaybleAmt=mGetText("id", mGetPropertyFromFile("adh_Scr_SetUpOfHoardingBookingPaybleAmountID"),"value");
			scr_suoh_PaybleAmount.add(PaybleAmt);
			System.out.println("payable amount is : "+ scr_suoh_PaybleAmount);
			/*int payble1=Integer.parseInt(PaybleAmt);
			scrupdatedpayble_list=payble1;
			System.out.println("updatedpayble_list=>"+scrupdatedpayble_list);*/

			String RoundOffAmt=mGetText("id", mGetPropertyFromFile("adh_Scr_SetUpOfHoardingBookingRoundoffAmountID"),"value");
			scr_suoh_RoundOffAmount.add(RoundOffAmt);

			mTakeScreenShot();

			adh_mDynamicReadingHoardingDeatilsTableGrid();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	//Setup of Hoarding for display of Advertisement Hoarding Detail reading table at application form
	private void adh_mDynamicReadingHoardingDeatilsTableGrid(){

		try{
			WebElement table = driver.findElement(By.id("gview_gridHoardingAndAdvertisementSetupDetails"));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount =rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

			List<WebElement> cols = rows.get(1).findElements(By.xpath("td"));
			int colcount= cols.size();
			System.out.println(colcount);

			for(int a=0;a<rwcount;a++)			
			{
				// Iterate to get the value of each cell in table along with element id
				for(int b=0;b<colcount;b++)
				{
					if(a>=1)
					{
						if(b==0)
						{
							String srNo = rows.get(a).findElement(By.xpath("td[1]")).getText();
							System.out.println("Sr No : "+srNo);
						}
						if(b==1)
						{
							String hoardingDescription = rows.get(a).findElement(By.xpath("td[2]")).getText();
							System.out.println("Hoarding Description is : "+hoardingDescription);
						}
						if(b==2)
						{
							String lenght = rows.get(a).findElement(By.xpath("td[3]")).getText();
							System.out.println("Hoarding length is : "+lenght);
						}
						if(b==3)
						{
							String breadth = rows.get(a).findElement(By.xpath("td[4]")).getText();
							System.out.println("Hoarding Breadth Type is : "+breadth);
						}
						if(b==4)
						{
							String elevation = rows.get(a).findElement(By.xpath("td[5]")).getText();
							System.out.println("Hoarding Elevation is : "+elevation);
						}
						if(b==5)
						{
							String gisNo = rows.get(a).findElement(By.xpath("td[6]")).getText();
							System.out.println("Hoarding GIS no is : "+gisNo);													
						}

						if(b==6)
						{
							String HoardingType = rows.get(a).findElement(By.xpath("td[7]")).getText();
							System.out.println("Hoarding Type is : "+HoardingType);
						}


						if(b==7)
						{
							String HoardingAmt = rows.get(a).findElement(By.xpath("td[8]")).getText();
							System.out.println("Hoarding Amount is : "+HoardingAmt);
						}
					}

				}
			}
		}


		catch(Exception e){
			e.printStackTrace();
		}
	}

	//////////////////////////////////ABHISHEK JAIN///////////START////////INSPECTION CODE////////////////////////	


	//Hoarding Inspection 
	public void addHoardingInspection() throws InterruptedException {
		try{

			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));
			addHoardingInspection_Script();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}



	public void addShowCauseNotice() throws InterruptedException {
		try{
			mCreateArtefactsFolder("ADH_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));
			showCauseNotice_Script();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}


		catch(Exception e){
			e.printStackTrace();
		}

	}

	public void addHearingEntry() throws InterruptedException {
		try{
			mCreateArtefactsFolder("ADH_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("adh_MBADeptName"),mGetPropertyFromFile("adh_MBADeptPassword"));
			hearingEntry_Script();
			System.out.println("SKIPSTATUS==>"+SKIPSTATUS);
			logOut();
			finalLogOut();
			mCloseBrowser();
		}


		catch(Exception e){
			e.printStackTrace();
		}
	}



	///ABM
	public void addHoardingInspection_Script() throws InterruptedException

	{
		try{

			//mWaitForVisible("linkText",mGetPropertyFromFile("adh_BgAdv&HordingLinkid"));
			mNavigation("adh_BgAdv&HordingLinkid", "adh_BgTransactionLinkid","adh_InspectionLinkid");

			mWaitForVisible("linkText",mGetPropertyFromFile("adh_InpectionEntrylinkText"));
			mClick("linkText", mGetPropertyFromFile("adh_InpectionEntrylinkText"));

			mWaitForVisible("css",mGetPropertyFromFile("adh_AddHoardingInspectiovcss"));
			mClick("css", mGetPropertyFromFile("adh_AddHoardingInspectiovcss"));

			mWaitForVisible("css",mGetPropertyFromFile("adh_SelectHoardingcss"));
			mClick("css", mGetPropertyFromFile("adh_SelectHoardingcss"));

			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("Continue")){
				if(Flag==1){
					mCustomWait(300);
					mSendKeys("id",mGetPropertyFromFile("adh_ContactNoid"),adhContractNo.get(CurrentinvoCount));
				}
				else{
					mCustomWait(300);
					mSendKeys("id",mGetPropertyFromFile("adh_AgencyApplicationNoid"),applicationNo.get(CurrentinvoCount));
				}
			}
			else{
				addInspectionProcessSearchCriria();
			}




			mWaitForVisible("id",mGetPropertyFromFile("adh_ReadHoardingNo"));
			String hoardingNo=mGetText("id", mGetPropertyFromFile("adh_ReadHoardingNo"),"value");
			System.out.println("Hoarding No is:"+hoardingNo);	

			Ins_hoardingNo.add(hoardingNo);
			System.out.println("Hoarding No is:"+Ins_hoardingNo);




			mWaitForVisible("id",mGetPropertyFromFile("adh_HoardingDescriptionID"));
			String hoardingDesc=mGetText("id", mGetPropertyFromFile("adh_HoardingDescriptionID"),"value");
			System.out.println("Hoarding Description is:"+hoardingDesc);	

			Ins_hoardingDesc.add(hoardingDesc);


			mCustomWait(350);
			String contractID=mGetText("id", mGetPropertyFromFile("adh_ConID"),"value");
			System.out.println("Contract ID is:"+contractID);		

			Ins_ContractID.add(contractID);


			mCustomWait(350);
			String contractStatus=mGetText("id", mGetPropertyFromFile("adh_contactStatusId"),"value");
			System.out.println("Contract Status is:"+contractStatus);

			Ins_Contractstatus.add(contractStatus);


			mCustomWait(350);
			String applicationNo=mGetText("id", mGetPropertyFromFile("adh_ANoId"),"value");
			System.out.println("Application No is:"+applicationNo);

			Ins_applicationNo.add(applicationNo);	


			mCustomWait(350);
			String agencyName=mGetText("id", mGetPropertyFromFile("adh_ANameID"),"value");
			System.out.println("Agency Name is:"+agencyName);

			Ins_agencyName.add(agencyName);

			mCustomWait(350);
			String contractFormDate=mGetText("id", mGetPropertyFromFile("adh_contractfromDateID"),"value");
			System.out.println("Contract From Date is:"+contractFormDate);     

			Ins_contractFormDate.add(contractFormDate);


			String contractToDate=mGetText("id", mGetPropertyFromFile("adh_ContractToDateID"),"value");
			System.out.println("Contract To Date is:"+contractToDate);			

			Ins_contractToDate.add(contractToDate);	         


			mCustomWait(350);
			mSelectDropDown("id", mGetPropertyFromFile("adh_SelectInpectionTypeid"), mGetPropertyFromFile("adh_InspectionTypeData"));
			String applicationtype=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_SelectInpectionTypeid"));
			System.out.println("Inspectin type is"+applicationtype);

			Ins_Inspectiontype.add(applicationtype);


			mCustomWait(1000);
			mWaitForVisible("id",mGetPropertyFromFile("adh_InpectionDateid"));
			mDateSelect("id",mGetPropertyFromFile("adh_InpectionDateid"),mGetPropertyFromFile("adh_InpectionDateData"));
			String Inspectiondate=mGetText("id",mGetPropertyFromFile("adh_InpectionDateid"), "value");
			System.out.println("Application Inspection date is"+Inspectiondate);

			Ins_Inspectiondate.add(Inspectiondate);


			String InspectedBy=mGetText("id", mGetPropertyFromFile("adh_InpectedByid"),"value");
			System.out.println("Inspected By is"+InspectedBy);

			Ins_InspectedBy.add(InspectedBy);

			mSendKeys("id", mGetPropertyFromFile("adh_InpectionInPresenceOfId"),mGetPropertyFromFile("adh_InpectionInPresenceOfData"));
			String InpectionInPresenceOfId=mGetText("id", mGetPropertyFromFile("adh_InpectionInPresenceOfId"), "value");
			System.out.println("Inspection In Presence Of is"+InpectionInPresenceOfId);

			Ins_Inpresenceof.add(InpectionInPresenceOfId);

			mTakeScreenShot();
			mCustomWait(1000);
			mscroll(0, 300);
			mCustomWait(1000);

			mWaitForVisible("xpath",mGetPropertyFromFile("adh_addInspectDetailsID"));
			mClick("xpath", mGetPropertyFromFile("adh_addInspectDetailsID"));
			mCustomWait(1000);
			mWaitForVisible("name",mGetPropertyFromFile("adh_ContactTermscss"));
			mCustomWait(1000);

			WebElement table = driver.findElement(By.className("gridtable"));
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			rwcount = rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

			mWaitForVisible("css",mGetPropertyFromFile("adh_AddInspectionPopUpCloseId"));
			mClick("css", mGetPropertyFromFile("adh_AddInspectionPopUpCloseId"));
			mCustomWait(1000);
			if(mGetPropertyFromFile("adh_InpectionClearYesNoData").equalsIgnoreCase("Clear")){
				Tblread_adh_add_inspectionClearDetails();
			}
			else{

				Tblread_adh_add_inspectionnotClearDetails();

			}


			mTakeScreenShot();
			mCustomWait(1000);
			mWaitForVisible("id",mGetPropertyFromFile("adh_InspectEntrySubmitId"));
			mClick("id", mGetPropertyFromFile("adh_InspectEntrySubmitId"));

			mCustomWait(2000);
			mWaitForVisible("css",mGetPropertyFromFile("adh_SubmitMessagecss"));
			String Msg=mGetText("css",mGetPropertyFromFile("adh_SubmitMessagecss"));
			System.out.println("Inspection Button Submit Msg is::"+Msg);
			mTakeScreenShot();
			String InspectionNo=mGetText("css",mGetPropertyFromFile("adh_SubmitMsgInspectionNoId"));
			System.out.println("Inspection No is::"+InspectionNo);
			if(!(mGetPropertyFromFile("adh_InpectionClearYesNoData").equalsIgnoreCase("Clear"))){
				chekindexesinspction.set(CurrentinvoCount,InspectionNo);
			}
			Ins_inpectionNo.add(InspectionNo);

			mWaitForVisible("xpath",mGetPropertyFromFile("adh_InspectProceedxpath"));
			mClick("xpath", mGetPropertyFromFile("adh_InspectProceedxpath"));
			mCustomWait(1000);

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	// Inepsction Letter Genration Script
	public void inspectionEntryLetter_Script() throws InterruptedException {

		try{
			mWaitForVisible("linkText",mGetPropertyFromFile("adh_BgAdv&HordingLinkid"));
			mNavigation("adh_BgAdv&HordingLinkid", "adh_BgTransactionLinkid","adh_InspectionLinkid");

			mWaitForVisible("linkText",mGetPropertyFromFile("adh_InpectionEntrylinkText"));
			mClick("linkText", mGetPropertyFromFile("adh_InpectionEntrylinkText"));


			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				mWaitForVisible("id",mGetPropertyFromFile("adh_LetterPrintSearchInspectionNoID"));
				mSendKeys("id", mGetPropertyFromFile("adh_LetterPrintSearchInspectionNoID"),Ins_inpectionNo.get(CurrentinvoCount));
			}
			else
			{
				addInspectionProcessSearchCririaLetterGeneration();
			}


			mCustomWait(1000);
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_InspectionNotoPrintletterSearchBtnId"));
			mClick("xpath", mGetPropertyFromFile("adh_InspectionNotoPrintletterSearchBtnId"));

			mCustomWait(500);
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_InpectionLetterPrintID"));
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("adh_InpectionLetterPrintID"));
			mCustomWait(4000);
			mChallanPDFReader();
			mCustomWait(1000);
			//adh_Inspection_letter_assertion();
			mCustomWait(2000);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	// Search Criteria For Inspection Entry
	public void addInspectionProcessSearchCriria() {
		try {

			if(mGetPropertyFromFile("adh_IESearchContactNoYNData").equalsIgnoreCase("Yes")){
				mCustomWait(300);
				mSendKeys("id",mGetPropertyFromFile("adh_ContactNoid"),mGetPropertyFromFile("adh_ContactNoData"));
			}

			if (mGetPropertyFromFile("adh_IESearchAgencyApplicationNoYNData").equalsIgnoreCase("Yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_AgencyApplicationNoid"),mGetPropertyFromFile("adh_AgencyApplicationNoData"));

			}

			if (mGetPropertyFromFile("adh_IESearchAgencyApplicantNameYNData").equalsIgnoreCase("Yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_AgencyApplicantNameid"),mGetPropertyFromFile("adh_AgencyApplicantNameData"));

			}


			mCustomWait(500);
			mClick("css", mGetPropertyFromFile("adh_SearchButtoncss"));


			mWaitForVisible("xpath", mGetPropertyFromFile("adh_SelectHoardingRadioButtoncss"));
			mClick("xpath", mGetPropertyFromFile("adh_SelectHoardingRadioButtoncss"));

			mWaitForVisible("xpath", mGetPropertyFromFile("adh_SearchSubmitButtonxpath"));
			mClick("xpath", mGetPropertyFromFile("adh_SearchSubmitButtonxpath"));

		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in addHoardingEntrySearchCriria SearchCriria");
		}
	}


	// Search Criteria For Inspection Letter Printing
	public void addInspectionProcessSearchCririaLetterGeneration() {
		try {

			if(mGetPropertyFromFile("adh_ILSearchContactNoYNData").equalsIgnoreCase("Yes")){
				mCustomWait(300);
				mSendKeys("id",mGetPropertyFromFile("adh_LetterPrintSearchContractID"),mGetPropertyFromFile("adh_LetterContactNoData"));
			}

			if (mGetPropertyFromFile("adh_ILSearchAgencyApplicationNoYNData").equalsIgnoreCase("Yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_LetterPrintSearchAgencyApplicationNoID"),mGetPropertyFromFile("adh_LetterAgencyApplicationNoData"));

			}

			if (mGetPropertyFromFile("adh_ILSearchrAgencyApplicantNameYNData").equalsIgnoreCase("Yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_LetterPrintSearchgencyApplicantNameID"),mGetPropertyFromFile("adh_LetterAgencyApplicantNameData"));

			}

			if (mGetPropertyFromFile("adh_ILSearchInspectionNoYNData").equalsIgnoreCase("Yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_LetterPrintSearchInspectionNoID"),mGetPropertyFromFile("adh_LetterInspectionNoData"));

			}

		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in addInspectionProcessSearchCririaLetterGeneration SearchCriria");
		}
	}


	// Search Criteria For Show Cause Notice
	public void addshowcauseProcessSearchCriria() {
		try {

			if(mGetPropertyFromFile("adh_SCNSearchContactNoYNData").equalsIgnoreCase("Yes")){
				mCustomWait(300);
				mSendKeys("id",mGetPropertyFromFile("adh_ContactNoid"),mGetPropertyFromFile("adh_ContactNoData"));
			}

			if (mGetPropertyFromFile("adh_SCNSearchAgencyNoYNData").equalsIgnoreCase("Yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_AgencyApplicationNoid"),mGetPropertyFromFile("adh_AgencyApplicationNoData"));

			}

			if (mGetPropertyFromFile("adh_SCNSearchAgencyNameYNData").equalsIgnoreCase("Yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_AgencyApplicantNameid"),mGetPropertyFromFile("adh_AgencyApplicantNameData"));

			}


		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in addHoardingEntrySearchCriria SearchCriria");
		}
	}

	// Search Criteria For Show Cause Letter Printing
	public void addShowCauseNoticeSearchCriteriaLetterGeneration() {
		try {

			if(mGetPropertyFromFile("adh_SCNSearchLetterContactNoYNData").equalsIgnoreCase("Yes")){
				mCustomWait(300);
				mSendKeys("id",mGetPropertyFromFile("adh_LetterPrintSearchContractID"),mGetPropertyFromFile("adh_LetterContactNoData"));
			}

			if (mGetPropertyFromFile("adh_SCNSearchLetterAgencyNoYNData").equalsIgnoreCase("Yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_LetterPrintSearchAgencyApplicationNoID"),mGetPropertyFromFile("adh_LetterAgencyApplicationNoData"));

			}

			if (mGetPropertyFromFile("adh_SCNSearchLetterAgencyNameYNData").equalsIgnoreCase("yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_LetterPrintSearchgencyApplicantNameID"),mGetPropertyFromFile("adh_LetterAgencyApplicantNameData"));

			}

			if (mGetPropertyFromFile("adh_SCNSearchLetterNoticeNoYNData").equalsIgnoreCase("Yes")) 
			{
				mSendKeys("id", mGetPropertyFromFile("adh_LetterPrintSearchgNoticeNoID"),mGetPropertyFromFile("adh_LetterNoticeNoData"));
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in addHoardingEntrySearchCriria SearchCriria");
		}
	}


	// Search Criteria For Hearing entry form 
	public void addHearingEntryProcessSearchCriria() {
		try {
			//Search By Contract No
			if(mGetPropertyFromFile("adh_HESearchContractNoYNData").equalsIgnoreCase("Yes")){
				mCustomWait(300);
				mSendKeys("id",mGetPropertyFromFile("adh_HesearchContractNoid"),mGetPropertyFromFile("adh_ContactNoData"));
			}
			//Search By Agency/Application No
			if (mGetPropertyFromFile("adh_HESearchAgencyNoYNData").equalsIgnoreCase("Yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_HesearchApplicationNoid"),mGetPropertyFromFile("adh_AgencyApplicationNoData"));

			}
			//Search By Agency/Application Name
			if (mGetPropertyFromFile("adh_HESearchAgencyNameYNData").equalsIgnoreCase("Yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_HesearchApplicantNameid"),mGetPropertyFromFile("adh_AgencyApplicantNameData"));

			}
			//Search By Notice No
			if (mGetPropertyFromFile("adh_HESearchNoticeNoYNData").equalsIgnoreCase("NoticeNo")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_HesearchNoticeNoid"),mGetPropertyFromFile("adh_NoticeNoData"));

			}
			//Search By Notice Type
			if (mGetPropertyFromFile("adh_HESearchNoticeTypeYNData").equalsIgnoreCase("NoticeType")) 
			{   mCustomWait(300);
			mSelectDropDown("id",mGetPropertyFromFile("adh_HesearchNoticeTypeid"),mGetPropertyFromFile("adh_NoticeTypeData"));

			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in addHoardingEntrySearchCriria SearchCriria");
		}
	}


	// Search Criteria For Hearing Entry Letter Printing
	public void addHearingEntryProcessSearchCririaLetterGeneration() {
		try {
			//Search by Contract No
			if(mGetPropertyFromFile("adh_HELetterSearchContractNoYNData").equalsIgnoreCase("Yes")){
				mCustomWait(300);
				mSendKeys("id",mGetPropertyFromFile("adh_HeLetterHearingContractNoId"),mGetPropertyFromFile("adh_HeHearingLetterContactNoData"));
			}
			//Search by Agency/ Application  No
			if (mGetPropertyFromFile("adh_HELetterSearchAgencyNoYNData").equalsIgnoreCase("Yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_HeLetterHearingAgencyApplicationNoId"),mGetPropertyFromFile("adh_HeHearingLetterAgencyApplicationNoData"));

			}
			//Search by Agency/ Application Name
			if (mGetPropertyFromFile("adh_HELetterSearchAgencyNameYNData").equalsIgnoreCase("Yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_HeLetterHearingAgencyApplicantNameId"),mGetPropertyFromFile("adh_HeHearingLetterAgencyApplicantNameData"));

			}
			//Search by Noticed Type
			if (mGetPropertyFromFile("adh_HELetterSearchNoticeTypeYNData").equalsIgnoreCase("Yes")) 
			{  
				mCustomWait(300);
				mSelectDropDown("id",mGetPropertyFromFile("adh_HeLetterHearingNoticedTypeId"),mGetPropertyFromFile("adh_HeHearingLetterNoticeTypeData"));

			}

			//Search by Noticed No
			if (mGetPropertyFromFile("adh_HELetterSearchNoticeNoYNData").equalsIgnoreCase("Yes")) 
			{
				mSendKeys("id", mGetPropertyFromFile("adh_HeLetterHearingNoticedNoeId"),mGetPropertyFromFile("adh_HeHearingLetterNoticeNoData"));
			}
			//Search by Hearing No
			if (mGetPropertyFromFile("adh_HELetterSearchHearingNoYNData").equalsIgnoreCase("Yes")) 
			{
				mSendKeys("id", mGetPropertyFromFile("adh_HeLetterHearingNoId"),mGetPropertyFromFile("adh_HeHearingLetterHearingNoData"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in addHearingEntryProcessSearchCririaLetterGeneration Search Criteria");
		}

	}




	//table Reading 
	//Inspect Entry reading table at Search Criteria page
	private void dynamicaddInspectionEntryHoardingReadingTableGrid(){

		try{
			WebElement table = driver.findElement(By.xpath("//*[@id='gbox_gridHoardingInspectionDetails']"));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount =rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

			List<WebElement> cols = rows.get(1).findElements(By.xpath("td"));
			int colcount= cols.size();
			System.out.println(colcount);

			for(int a=0;a<3;a++)			
			{
				// Iterate to get the value of each cell in table along with element id
				for(int b=0;b<colcount;b++)
				{
					if(a>=1)
					{
						if(b==0)
						{
							String contractTerms = rows.get(a).findElement(By.xpath("td[1]")).getText();
							System.out.println("Sr No : "+contractTerms);
						}
						if(b==1)
						{
							String inspectionRemarks = rows.get(a).findElement(By.xpath("td[2]")).getText();
							System.out.println("Hoarding Description is : "+inspectionRemarks);
						}

						if(b==2)
						{
							String inspectionStatus = rows.get(a).findElement(By.xpath("td[3]")).getText();
							System.out.println("Inpected Status Type is : "+inspectionStatus);
						}

					}

				}
			}

		}


		catch(Exception e){
			e.printStackTrace();
		}
	}


	//Show Cause Notice Entry Script 
	public void showCauseNotice_Script()
	{
		try{

			//Navigating from Advt and Hoarding To Show Cause Notice
			mCustomWait(1000);;
			mNavigation("adh_ScNAdvtNHoardingLinkid", "adh_ScNTransactionLinkid", "adh_ScNInspectionLinkid");

			mWaitForVisible("linkText", mGetPropertyFromFile("adh_ScNShowCauseNoticeLinkid"));
			mClick("linkText", mGetPropertyFromFile("adh_ScNShowCauseNoticeLinkid"));

			ULBName=mGetPropertyFromFile("municipality");
			System.out.println("ulb is : "+ULBName); 

			//Click and wait on Add Show Cause Details Button 
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_ScNsearchShowCauseNoticebtnid"));
			mClick("xpath", mGetPropertyFromFile("adh_ScNsearchShowCauseNoticebtnid"));

			//Click and wait on Contract No Button 
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_ScNsearchContractNobtnid"));
			mClick("xpath", mGetPropertyFromFile("adh_ScNsearchContractNobtnid"));



			mWaitForVisible("id", mGetPropertyFromFile("adh_ScNsearchContractNoid"));
			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				mSendKeys("id", mGetPropertyFromFile("adh_ScNsearchContractNoid"),Ins_ContractID.get(CurrentinvoCount));
			}
			else
			{
				addshowcauseProcessSearchCriria();
			}

			//Search and wait on Search Conract No 
			mWaitForVisible("css", mGetPropertyFromFile("adh_ScNContractNosearchCriteriabtnid"));
			mClick("css", mGetPropertyFromFile("adh_ScNContractNosearchCriteriabtnid"));

			mCustomWait(500);
			//Select and wait on to select radio button id in grid 
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_ScNContractNoradiobtnid"));
			mClick("xpath", mGetPropertyFromFile("adh_ScNContractNoradiobtnid"));

			//Submit and wait
			mWaitForVisible("css", mGetPropertyFromFile("adh_ScNContractNoSubmitbtnid"));
			mClick("css", mGetPropertyFromFile("adh_ScNContractNoSubmitbtnid"));

			//Capturing Contract No
			String ContractNo=mGetText("id", mGetPropertyFromFile("adh_ScNReadContractNoid"), "value");
			System.out.println("Contract No is"+ContractNo);
			Show_contractNo.add(ContractNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Show_contractNo.get(clearchekindexes.get(CurrentinvoCount)), Ins_ContractID.get(CurrentinvoCount), "contract No is in show cause Notice dose not match Actual::"+Show_contractNo.get(clearchekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+Ins_ContractID.get(CurrentinvoCount));
			}


			//Capturing Applicant Name
			String ApplicantName=mGetText("id", mGetPropertyFromFile("adh_ScNReadAgencyorAppliNameid"), "value");
			System.out.println("Applicant Name is"+ApplicantName);
			Show_AgencyorApplicantName.add(ApplicantName);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){

				mAssert(Show_AgencyorApplicantName.get(clearchekindexes.get(CurrentinvoCount)), Ins_agencyName.get(CurrentinvoCount), "Agency/Applicant Name is in show cause Notice dose not match Actual::"+Show_AgencyorApplicantName.get(clearchekindexes.get(CurrentinvoCount))+"in inspection form,Expected::"+Ins_agencyName.get(CurrentinvoCount));
			}


			//Capturing Application No
			String ApplicationNo=mGetText("id", mGetPropertyFromFile("adh_ScNReadAgencyorAppliNoNoid"), "value");
			System.out.println("Application No is"+ApplicationNo);
			Show_AgencyorApplicationNo.add(ApplicationNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){

				mAssert(Show_AgencyorApplicationNo.get(clearchekindexes.get(CurrentinvoCount)), Ins_applicationNo.get(CurrentinvoCount), "Applicantion No is in show cause Notice dose not match Actual::"+Show_AgencyorApplicationNo.get(clearchekindexes.get(CurrentinvoCount))+"in inspection  form,Expected::"+Ins_applicationNo.get(CurrentinvoCount));
			}


			//Sending Contract from Date
			String ContractFromDate=mGetText("id", mGetPropertyFromFile("adh_ScNReadContractfromDateid"), "value");
			System.out.println("Contract From Date is"+ContractFromDate);
			Show_ContractFromDate.add(ContractFromDate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){

				mAssert(Show_ContractFromDate.get(clearchekindexes.get(CurrentinvoCount)), Ins_contractFormDate.get(CurrentinvoCount), "Contract From Date is in show cause Notice dose not match Actual::"+Show_ContractFromDate.get(clearchekindexes.get(CurrentinvoCount))+"in inspection form,Expected::"+Ins_contractFormDate.get(CurrentinvoCount));
			}

			//Sending Contract to Date
			String ContractToDate=mGetText("id", mGetPropertyFromFile("adh_ScNReadContracttoDateid"), "value");
			System.out.println("Contract To Date is"+ContractToDate);
			Show_ContractToDate.add(ContractToDate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Show_ContractToDate.get(clearchekindexes.get(CurrentinvoCount)), Ins_contractToDate.get(CurrentinvoCount), "Contract to Date is in show cause Notice dose not match Actual::"+Show_ContractToDate.get(clearchekindexes.get(CurrentinvoCount))+"in inspection  form,Expected::"+Ins_contractToDate.get(CurrentinvoCount));
			}


			//Capturing Contract Status 
			String ContractStatus=mGetText("id", mGetPropertyFromFile("adh_ScNContractStatusid"), "value");
			System.out.println("Contract Status  No is"+ContractStatus);
			Show_ContrcatStatus.add(ContractStatus);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){

				mAssert(Show_ContrcatStatus.get(clearchekindexes.get(CurrentinvoCount)), Ins_Contractstatus.get(CurrentinvoCount), "Contract Status is in show cause Notice dose not match Actual::"+Show_ContrcatStatus.get(clearchekindexes.get(CurrentinvoCount))+"in inspection  form,Expected::"+Ins_Contractstatus.get(CurrentinvoCount));
			}

			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent")){
				//Sending Notice Type

				mSelectDropDown("id", mGetPropertyFromFile("adh_ScNNoticeTypeid"), mGetPropertyFromFile("adh_ScNNoticeTypeData"));
				String  NoticeType=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_ScNNoticeTypeid"));
				System.out.println("Notice Type is"+NoticeType);
				Show_NoticeType.add(NoticeType);
			}
			else
			{
				mSelectDropDown("id", mGetPropertyFromFile("adh_ScNNoticeTypeid"), mGetPropertyFromFile("adh_ScNNoticeTypeData"));
				String  NoticeType=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_ScNNoticeTypeid"));
				System.out.println("Notice Type is"+NoticeType);
				Show_NoticeType.add(NoticeType);

			}
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){

				mAssert(Show_NoticeType.get(clearchekindexes.get(CurrentinvoCount)), Ins_Inspectiontype.get(CurrentinvoCount), "Notice Type is in show cause Notice dose not match Actual::"+Show_NoticeType.get(clearchekindexes.get(CurrentinvoCount))+"in inspection  form,Expected::"+Ins_Inspectiontype.get(CurrentinvoCount));
			}


			//Search and wait on to checkd Box
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_ScNInspectionDetailtablechkBoxid"));
			mClick("xpath", mGetPropertyFromFile("adh_ScNInspectionDetailtablechkBoxid"));

			//Sending Notice Date
			mDateSelect("id", mGetPropertyFromFile("adh_ScNNoticedateid"), mGetPropertyFromFile("adh_ScNNoticedateData"));
			String CancellationDate=mGetText("id", mGetPropertyFromFile("adh_ScNNoticedateid"), "value");
			System.out.println("Notice Date is"+CancellationDate);
			Show_NoticeDate.add(CancellationDate);

			//Capturing Notice send By
			String NoticesendBy=mGetText("css", mGetPropertyFromFile("adh_ScNNoticeSentByid"), "value");
			System.out.println("Notice send By is"+NoticesendBy);
			Show_NoticesendBy.add(NoticesendBy);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){

				mAssert(Show_NoticesendBy.get(clearchekindexes.get(CurrentinvoCount)), Ins_InspectedBy.get(CurrentinvoCount), "Notice Send By is in show cause Notice dose not match Actual::"+Show_NoticesendBy.get(clearchekindexes.get(CurrentinvoCount))+"in inspection  form,Expected::"+Ins_InspectedBy.get(CurrentinvoCount));
			}


			//Sending Notice Remarks
			mSendKeys("id", mGetPropertyFromFile("adh_ScNSRemarksid"), mGetPropertyFromFile("adh_ScNSRemarksData"));
			String NoticeRemarks=mGetText("id", mGetPropertyFromFile("adh_ScNSRemarksid"), "value");
			System.out.println("Notice Remarks is"+NoticeRemarks);
			Show_NoticeRemarks.add(NoticeRemarks);


			//Sending Hearing Date
			mDateSelect("id", mGetPropertyFromFile("adh_ScNSHearingDateid"),mGetPropertyFromFile("adh_ScNHearningdateData"));
			mCustomWait(200);
			mTab("id", mGetPropertyFromFile("adh_ScNSHearingDateid"));
			String hearingDateDate=mGetText("id", mGetPropertyFromFile("adh_ScNSHearingDateid"), "value");
			System.out.println("Hearing Date is"+hearingDateDate);
			Show_HearingDate.add(hearingDateDate);
			mCustomWait(500);

			//Sending Officiated By
			mSelectDropDown("id", mGetPropertyFromFile("adh_ScNSOfficiatedByid"), mGetPropertyFromFile("adh_ScNSOfficiatedByData"));
			String  OfficiatedBy=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_ScNSOfficiatedByid"));
			System.out.println("Officiated By"+OfficiatedBy);
			Show_OfficiatedBy.add(OfficiatedBy);
			mCustomWait(1000);
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_ScNformSubmitid"));
			mClick("xpath", mGetPropertyFromFile("adh_ScNformSubmitid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);

			mWaitForVisible("css",mGetPropertyFromFile("adh_ScNAssertMsgtid"));
			String Msg=mGetText("css",mGetPropertyFromFile("adh_ScNAssertMsgtid"));
			System.out.println("Notice Button Submit Msg is::"+Msg);
			mCustomWait(1000);
			String NoticeNo=mGetText("css",mGetPropertyFromFile("adh_ScNNoticeNo"));
			System.out.println("Notice No is::"+NoticeNo);
			chekindexesinspction.set(CurrentinvoCount,NoticeNo);
			Show_NoticeNo.add(NoticeNo);
			System.out.println("Notice No in arraylist is::"+Show_NoticeNo);
			mCustomWait(1000);
			mWaitForVisible("id",mGetPropertyFromFile("adh_ScNProceedBtntid"));
			mClick("id", mGetPropertyFromFile("adh_ScNProceedBtntid"));
			mCustomWait(1000);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in showCauseNotice_Script");
		}
	}



	//Show cause Notice Letter Priniting Method
	public void showCauseNoticeLetter_Script() throws InterruptedException {

		try{
			mCustomWait(1000);;
			mNavigation("adh_ScNAdvtNHoardingLinkid", "adh_ScNTransactionLinkid", "adh_ScNInspectionLinkid");

			mWaitForVisible("linkText", mGetPropertyFromFile("adh_ScNShowCauseNoticeLinkid"));
			mClick("linkText", mGetPropertyFromFile("adh_ScNShowCauseNoticeLinkid"));

			ULBName=mGetPropertyFromFile("municipality");
			System.out.println("ulb is : "+ULBName); 


			//Sending Notice Type
			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent")){
				mSelectDropDown("css", mGetPropertyFromFile("adh_LetterPrintSearchgNoticeTypeid"), mGetPropertyFromFile("adh_ScNNoticeTypeData"));
				String  adh_ScNShowCauseNoticeType=mCaptureSelectedValue("css", mGetPropertyFromFile("adh_LetterPrintSearchgNoticeTypeid"));
				System.out.println("Notice Type "+adh_ScNShowCauseNoticeType);
			}
			else{
				mSelectDropDown("css", mGetPropertyFromFile("adh_LetterPrintSearchgNoticeTypeid"), mGetPropertyFromFile("adh_ScNNoticeTypeData"));
			}

			//Sending Search Criteria
			mWaitForVisible("id",mGetPropertyFromFile("adh_ScNNoticeNoid"));
			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				mSendKeys("id", mGetPropertyFromFile("adh_ScNNoticeNoid"),chekindexesinspction.get(CurrentinvoCount));
			}
			else
			{
				addInspectionProcessSearchCririaLetterGeneration();
			}

			mWaitForVisible("css", mGetPropertyFromFile("adh_ScNNoticeNoSearchbtnid"));
			mClick("css", mGetPropertyFromFile("adh_ScNNoticeNoSearchbtnid"));


			mWaitForVisible("xpath", mGetPropertyFromFile("adh_ScNNoticeLetterPrintbtnid"));
			//Click on Selected Print Button
			mClick("xpath", mGetPropertyFromFile("adh_ScNNoticeLetterPrintbtnid"));
			mCustomWait(5000);
			//Downloding Letter
			mChallanPDFReader();
			mCustomWait(1000);

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}



	public void hearingEntry_Script()
	{
		try{

			//Navigating from Advt and Hoarding To Hearing Entry
			mCustomWait(1000);;
			mNavigation("adh_HeAdvtNHoardingLinkid", "adh_HeTransactionLinkid", "adh_HeInspectionLinkid");

			mWaitForVisible("linkText", mGetPropertyFromFile("adh_HeHearingEntryLinkid"));
			mClick("linkText", mGetPropertyFromFile("adh_HeHearingEntryLinkid"));

			ULBName=mGetPropertyFromFile("municipality");
			System.out.println("ulb is : "+ULBName); 

			//Click and wait on Add Hearing Entry Button 
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_HeAddHearingEntrybtnid"));
			mClick("xpath", mGetPropertyFromFile("adh_HeAddHearingEntrybtnid"));

			//Click and wait on Notice No Button 
			mWaitForVisible("linkText", mGetPropertyFromFile("adh_HeSearchNoticeNobtnid"));
			mClick("linkText", mGetPropertyFromFile("adh_HeSearchNoticeNobtnid"));

			mWaitForVisible("id", mGetPropertyFromFile("adh_HesearchNoticeNoid"));
			mWaitForVisible("id", mGetPropertyFromFile("adh_HesearchNoticeTypeid"));


			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				mWaitForVisible("id", mGetPropertyFromFile("adh_HesearchNoticeTypeid"));
				mSelectDropDown("id", mGetPropertyFromFile("adh_HesearchNoticeTypeid"), mGetPropertyFromFile("adh_NoticeTypeData"));
				mSendKeys("id", mGetPropertyFromFile("adh_HesearchNoticeNoid"),chekindexesinspction.get(CurrentinvoCount));
			}
			else
			{
				mWaitForVisible("id", mGetPropertyFromFile("adh_HesearchNoticeTypeid"));
				addHearingEntryProcessSearchCriria();
			}

			//Search and wait on Search Notice No 
			mWaitForVisible("css", mGetPropertyFromFile("adh_HeNoticeNosearchCriteriabtnid"));
			mClick("css", mGetPropertyFromFile("adh_HeNoticeNosearchCriteriabtnid"));

			mCustomWait(500);
			//Select and wait on to select radio button id in grid 
			mWaitForVisible("xpath", mGetPropertyFromFile("adh_HeNoticeNoradiobtnid"));
			mClick("xpath", mGetPropertyFromFile("adh_HeNoticeNoradiobtnid"));

			//Submit and wait
			mWaitForVisible("css", mGetPropertyFromFile("adh_HeNoticeNoSubmitbtnid"));
			mClick("css", mGetPropertyFromFile("adh_HeNoticeNoSubmitbtnid"));
			//Capturing Noticed No
			String NoticedNo=mGetText("id", mGetPropertyFromFile("adh_HeReadNoticeNoid"), "value");
			System.out.println("Noticed No is"+NoticedNo);
			He_NoticeNo.add(NoticedNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){

				mAssert(He_NoticeNo.get(clearchekindexes.get(CurrentinvoCount)), Show_NoticeNo.get(clearchekindexes.get(CurrentinvoCount)), "Hearing entry is in show cause Notice No dose not match Actual::"+He_NoticeNo.get(clearchekindexes.get(CurrentinvoCount))+"in show cause notice Form,Expected::"+Show_NoticeNo.get(clearchekindexes.get(CurrentinvoCount)));
			}



			//Capturing Noticed Type
			String Noticetype=mGetText("id", mGetPropertyFromFile("adh_HeReadNoticeTypeid"), "value");
			System.out.println("Noticed type is"+Noticetype);
			He_NoticeType.add(Noticetype);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){

				mAssert(He_NoticeType.get(clearchekindexes.get(CurrentinvoCount)), Show_NoticeType.get(clearchekindexes.get(CurrentinvoCount)), "Hearing entry is in show cause Notice Type dose not match Actual::"+He_NoticeType.get(clearchekindexes.get(CurrentinvoCount))+"in show cause notice Form,Expected::"+Show_NoticeType.get(clearchekindexes.get(CurrentinvoCount)));
			}

			//Capturing Hearing Noticed Date
			String CapturingDate=mGetText("id", mGetPropertyFromFile("adh_HeReadNoticeDateid"), "value");
			System.out.println("Noticed Date is"+CapturingDate);
			He_NoticedDate.add(CapturingDate);
			////////////////////////////////////
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(He_NoticedDate.get(clearchekindexes.get(CurrentinvoCount)), Show_NoticeDate.get(clearchekindexes.get(CurrentinvoCount)), "Hearing entry is in show cause Notice Date dose not match Actual::"+He_NoticedDate.get(clearchekindexes.get(CurrentinvoCount))+"in show cause notice Form,Expected::"+Show_NoticeDate.get(clearchekindexes.get(CurrentinvoCount)));
			}

			//Capturing Contract No
			String ContractNo=mGetText("id", mGetPropertyFromFile("adh_HeReadContractNoid"), "value");
			System.out.println("contract No is"+ContractNo);
			He_ContractNo.add(ContractNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){

				mAssert(He_ContractNo.get(clearchekindexes.get(CurrentinvoCount)), Show_contractNo.get(clearchekindexes.get(CurrentinvoCount)), "Hearing entry is in show cause Contract No dose not match Actual::"+He_ContractNo.get(clearchekindexes.get(CurrentinvoCount))+"in show cause notice Form,Expected::"+Show_contractNo.get(clearchekindexes.get(CurrentinvoCount)));
			}


			//Capturing Noticed Hearing Date
			String NoticedHearingDate=mGetText("id", mGetPropertyFromFile("adh_HeReadNoticeHearingDateid"), "value");
			System.out.println("Noticed Hearing Date is"+NoticedHearingDate);
			He_NoticedhearingDate.add(NoticedHearingDate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){

				mAssert(He_NoticedhearingDate.get(clearchekindexes.get(CurrentinvoCount)), Show_HearingDate.get(clearchekindexes.get(CurrentinvoCount)), "Hearing entry is in show cause Hearing Date dose not match Actual::"+He_NoticedhearingDate.get(clearchekindexes.get(CurrentinvoCount))+"in show cause notice Form,Expected::"+Show_HearingDate.get(clearchekindexes.get(CurrentinvoCount)));
			}


			//Capturing Applicant Name
			String ApplicantName=mGetText("id", mGetPropertyFromFile("adh_HeReadAgencyorAppliNameid"), "value");
			System.out.println("Applicant Name is"+ApplicantName);
			He_AgencyorApplicantName.add(ApplicantName);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(He_AgencyorApplicantName.get(clearchekindexes.get(CurrentinvoCount)), Show_AgencyorApplicantName.get(clearchekindexes.get(CurrentinvoCount)), "Hearing entry is in show cause Applicant Name dose not match Actual::"+He_AgencyorApplicantName.get(clearchekindexes.get(CurrentinvoCount))+"in show cause notice Form,Expected::"+Show_AgencyorApplicantName.get(clearchekindexes.get(CurrentinvoCount)));
			}


			//Capturing Application No
			String ApplicationNo=mGetText("id", mGetPropertyFromFile("adh_HeReadAgencyorAppliNoNoid"), "value");
			System.out.println("Application No is"+ApplicationNo);
			He_AgencyorApplicationNo.add(ApplicationNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){

				mAssert(He_AgencyorApplicationNo.get(clearchekindexes.get(CurrentinvoCount)), Show_AgencyorApplicationNo.get(clearchekindexes.get(CurrentinvoCount)), "Hearing entry is in show cause Application No dose not match Actual::"+He_AgencyorApplicationNo.get(clearchekindexes.get(CurrentinvoCount))+"in show cause notice Form,Expected::"+Show_AgencyorApplicationNo.get(clearchekindexes.get(CurrentinvoCount)));
			}


			//Sending and Capturing Hearing date
			mCustomWait(300);
			mDateSelect("id", mGetPropertyFromFile("adh_HeHearingDateDateid"), mGetPropertyFromFile("adh_HeHearingDateData"));
			//mTab("id", mGetPropertyFromFile("adh_HeHearingDateDateid"));
			String HearingDate=mGetText("id", mGetPropertyFromFile("adh_HeHearingDateDateid"), "value");
			System.out.println("Hearing Date is"+HearingDate);
			He_HearingDate.add(HearingDate);

			//Capturing Hearing By
			String HearingdBy=mGetText("id", mGetPropertyFromFile("adh_HeHearingByeid"), "value");
			System.out.println("Hearing  By is"+HearingdBy);
			He_HearingdBy.add(HearingdBy);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){

				mAssert(He_HearingdBy.get(clearchekindexes.get(CurrentinvoCount)), Show_NoticesendBy.get(clearchekindexes.get(CurrentinvoCount)), "Hearing entry is in show cause Hearing  By dose not match Actual::"+He_HearingdBy.get(clearchekindexes.get(CurrentinvoCount))+"in show cause notice Form,Expected::"+Show_NoticesendBy.get(clearchekindexes.get(CurrentinvoCount)));
			}

			//Sending Hearing Desicion By 
			mSelectDropDown("id", mGetPropertyFromFile("adh_HeHearingDecisionid"), mGetPropertyFromFile("adh_HeHearingDecisionData"));
			String HeHearingDesicion=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_HeHearingDecisionid"));
			System.out.println("Hearing Desicion"+HeHearingDesicion);
			He_HearingDesicion.add(HeHearingDesicion);


			//Sending Hearing Remarks
			mSendKeys("id", mGetPropertyFromFile("adh_HeHearingRemarksid"), mGetPropertyFromFile("adh_HeHearingRemarksData"));
			String HearingRemarks=mGetText("id", mGetPropertyFromFile("adh_HeHearingRemarksid"), "value");
			System.out.println("Hearing Remarks is"+HearingRemarks);
			He_HearingRemarks.add(HearingRemarks);


			//Sending Hearing Status :: Suceessgul and Not Sucessful
			mSelectDropDown("css", mGetPropertyFromFile("adh_HeHearingStatusid"), mGetPropertyFromFile("adh_HeHearingStatusData"));
			String  OfficiatedBy=mCaptureSelectedValue("css", mGetPropertyFromFile("adh_HeHearingStatusid"));
			System.out.println("Hearing Status"+OfficiatedBy);
			He_HearingStatus.add(OfficiatedBy);


			//Wait And Submit
			mWaitForVisible("id", mGetPropertyFromFile("adh_HeHearingEntryFormSubmitid"));
			mClick("id", mGetPropertyFromFile("adh_HeHearingEntryFormSubmitid"));

			mTakeScreenShot();

			mCustomWait(2000);
			mWaitForVisible("css",mGetPropertyFromFile("adh_HeHearingNoAssertMsgid"));
			String Msg=mGetText("css",mGetPropertyFromFile("adh_HeHearingNoAssertMsgid"));
			System.out.println("Haering Button Submit Msg is::"+Msg);
			mTakeScreenShot();
			String NoticeNo=mGetText("css",mGetPropertyFromFile("adh_HeHearingNoPopUpd"));
			System.out.println("Hearing No is::"+NoticeNo);
			He_HearingeNo.add(NoticeNo);
			chekindexesinspction.set(CurrentinvoCount,NoticeNo);
			SKIPSTATUS.set(CurrentinvoCount,mGetPropertyFromFile("adh_HeHearingStatusData"));
			skipcanssus.add(mGetPropertyFromFile("adh_HeHearingStatusData"));
			adhHearingOrdeNo.add(NoticeNo);

			if (mGetPropertyFromFile("adh_HeHearingStatusData").equalsIgnoreCase("Hearing Unsuccessful")) {
				adhHearingOrdeNo_UNS.add(NoticeNo);
			}

			mWaitForVisible("id",mGetPropertyFromFile("adh_HeHearingProceedBtnId"));
			mClick("id", mGetPropertyFromFile("adh_HeHearingProceedBtnId"));
			mCustomWait(1000);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in showCauseNotice_Script");
		}
	}


	//Hearing Letter Script Method
	public void hearingLetter_Script() throws InterruptedException {

		try{
			//Navigating from Advt and Hoarding To Hearing Entry
			mCustomWait(1000);;
			mNavigation("adh_HeAdvtNHoardingLinkid", "adh_HeTransactionLinkid", "adh_HeInspectionLinkid");

			mWaitForVisible("linkText", mGetPropertyFromFile("adh_HeHearingEntryLinkid"));
			mClick("linkText", mGetPropertyFromFile("adh_HeHearingEntryLinkid"));

			ULBName=mGetPropertyFromFile("municipality");
			System.out.println("ulb is : "+ULBName); 

			mWaitForVisible("id",mGetPropertyFromFile("adh_HeLetterHearingNoId"));
			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{

				mCustomWait(500);
				mSendKeys("id", mGetPropertyFromFile("adh_HeLetterHearingNoId"),He_HearingeNo.get(HearingNo));
				HearingNo++;
				//mSendKeys("id", mGetPropertyFromFile("adh_HeLetterHearingNoId"),chekindexesinspction.get(CurrentinvoCount));
			}
			else
			{
				addHearingEntryProcessSearchCririaLetterGeneration();

			}

			mWaitForVisible("linkText", mGetPropertyFromFile("adh_HeHearingNoSearichBtnId"));
			mClick("linkText", mGetPropertyFromFile("adh_HeHearingNoSearichBtnId"));


			mWaitForVisible("xpath", mGetPropertyFromFile("adh_HeHearingLetterPrintBtnId"));
			mClick("xpath", mGetPropertyFromFile("adh_HeHearingLetterPrintBtnId"));
			mCustomWait(4000);
			mChallanPDFReader();
			mCustomWait(1000);


		}
		catch(Exception e){
			e.printStackTrace();
		}
	}






	//////////////////////////////////ABHISHEK JAIN///////////END////////INSPECTION CODE////////////////////////	
	////////////////////////////PDF Patterns/////////////////Inspection Letter/////////////////////////////////
	public static void ADH_Inspection_Letter_Pdf(String output){
		try {
			Pattern data=Pattern.compile("(?<=Advertising and Hoarding)\\s*(.*)",Pattern.DOTALL);
			Matcher matcher657=data.matcher(pdfoutput);
			if (matcher657.find()) {
				String data1=matcher657.group(1).trim();
				String [] CollectionDate=data1.split("\\n");
				for(int i=0;i<CollectionDate.length;i++)
				{
					//System.out.println(i+"===>"+CollectionDate[i]);
				}

				System.out.println("Inspection Letter Print Date==>"+CollectionDate[1]);
				System.out.println("Inspection Letter generated Applicant Name is ==>"+CollectionDate[2]);
				Ins_Pdf_agencyName.add(CollectionDate[2]);
				System.out.println("Inspection Letter generated Applicant Address is==>"+CollectionDate[3]);
				System.out.println("Letter Subject is==>"+CollectionDate[14]);
				//System.out.println("ULB Name is==>"+CollectionDate[21]);
			}


			Pattern Contractno = Pattern.compile("(?<= Contract No. )\\s*(.+?)\\s*(?= as per Bihar Municipal Act 2007)");
			Matcher matcher1 = Contractno.matcher(pdfoutput);

			if (matcher1.find())
			{
				String Contractno1 = matcher1.group(1).trim();
				System.out.println("Contract No is==>"+Contractno1);
				Ins_Pdf_ContractID.add(Contractno1);
			} 
			else
			{
				System.out.println("no result");
			}

			Pattern inspectedBy = Pattern.compile("(?<=was inspected by )\\s*(.+?)\\s*(?= on)");
			Matcher matcher2 = inspectedBy.matcher(pdfoutput);

			if (matcher2.find())
			{
				String inspectedBy1 = matcher2.group(1).trim();
				System.out.println("Inspected By employee Name is==>"+inspectedBy1);	
				Ins_Pdf_InspectedBy.add(inspectedBy1);
			} 
			else
			{
				System.out.println("no result");
			}

			Pattern inspectionDate = Pattern.compile("(?<=on )\\s*(.+?)\\s*(?= under Inspection No.)");
			Matcher matcher3 = inspectionDate.matcher(pdfoutput);
			if (matcher3.find())
			{
				String inspectionDate1 = matcher3.group(1).trim();
				System.out.println("Inspected Date is==>"+inspectionDate1);	
				Ins_Pdf_Inspectiondate.add(inspectionDate1);
			} 
			else
			{
				System.out.println("no result");
			}

			Pattern inspectionNo = Pattern.compile("(?<=Inspection No. )\\s*(.*)");
			Matcher matcher4 = inspectionNo.matcher(pdfoutput);
			if (matcher4.find())
			{
				String inspectionNo1 = matcher4.group(1).trim();
				System.out.println("Inspection No is==>"+inspectionNo1);	
				Ins_Pdf_inpectionNo.add(inspectionNo1);
			} 
			else
			{
				System.out.println("no result");
			}


			Pattern HoardingNo = Pattern.compile("(?<=that Hoarding \\()\\s*(.+?)\\s*(?= - Hanging Advertisement \\))");
			Matcher matcher5 = HoardingNo.matcher(pdfoutput);
			if (matcher5.find())
			{
				String HoardingNo1 = matcher5.group(1).trim();
				System.out.println("Hoarding No is==>"+HoardingNo1);
				Ins_Pdf_hoardingNo.add(HoardingNo1);
			} 
			else
			{
				System.out.println("no result");
			}
		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in ADH_Inspection_Letter_Pdf pattrn method");
		}
	}




	public static void ADH_ShowCause_Letter_Pdf(String output){
		try {
			Pattern data=Pattern.compile("(?<=Advertising and Hoarding)\\s*(.*)",Pattern.DOTALL);
			Matcher matcher657=data.matcher(pdfoutput);
			if (matcher657.find()) {
				String data1=matcher657.group(1).trim();
				String [] CollectionDate=data1.split("\\n");
				for(int i=0;i<CollectionDate.length;i++)
				{
					//System.out.println(i+"===>"+CollectionDate[i]);
				}

				System.out.println("Show Cause Notice Date==>"+CollectionDate[13]);
				Pdf_NoticeDate.add(CollectionDate[13]);
			}


			Pattern NoticeNo = Pattern.compile("(?<=Notice No. : )\\s*(.*)");
			Matcher matcher4 = NoticeNo.matcher(pdfoutput);
			if (matcher4.find())
			{
				String NoticeNo1 = matcher4.group(1).trim();
				System.out.println("Notice No is==>"+NoticeNo1);
				Pdf_Show_NoticeNo.add(NoticeNo1);
			} 
			else
			{
				System.out.println("no result");
			}

			Pattern nos = Pattern.compile("(?<=Inspection No.)\\s*(.*)\\s*(|\n)*(?=Notice No. :)",Pattern.DOTALL);
			Matcher matcher1 = nos.matcher(pdfoutput);
			if (matcher1.find())
			{
				String nos1 = matcher1.group(1).trim();
				String[] array=nos1.split(" ");
				System.out.println("Inspection No is==>"+array[0]);
				Pdf_Show_InspectionNo.add(array[0]);
				System.out.println("Hoarding No is==>"+array[1]);
				Pdf_Show_HoardingNo.add(array[1]);
			} 
			else
			{
				System.out.println("no result");
			}

			Pattern ContractNo = Pattern.compile("(?<=Contract No.)\\s*(.+?)\\s*(|\n)*(?= on)");
			Matcher matcher2 = ContractNo.matcher(pdfoutput);

			if (matcher2.find())
			{
				String ContractNo1 = matcher2.group(1).trim();
				System.out.println("Contract No is==>"+ContractNo1);
				Pdf_Show_contractNo.add(ContractNo1);

			} 
			else
			{
				System.out.println("no result");
			}

			Pattern inspectionType = Pattern.compile("(?<=during )\\s*(.+?)\\s*(?=carried out for)");
			Matcher matcher3 = inspectionType.matcher(pdfoutput);

			if (matcher3.find())
			{
				String inspectionType1 = matcher3.group(1).trim();
				System.out.println("Inspected Type is==>"+inspectionType1);	
				Pdf_Show_NoticeType.add(inspectionType1);
			} 
			else
			{
				System.out.println("no result");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}



	public static void ADH_Hearing_Letter_Pdf(String output){

		try {
			Pattern data=Pattern.compile("(?<=Advertising and Hoarding)\\s*(.*)",Pattern.DOTALL);
			Matcher matcher657=data.matcher(pdfoutput);
			if (matcher657.find()) {
				String data1=matcher657.group(1).trim();
				String [] CollectionDate=data1.split("\\n");
				for(int i=0;i<CollectionDate.length;i++)
				{
					//System.out.println(i+"===>"+CollectionDate[i]);
				}
				System.out.println("Hearing Date==>"+CollectionDate[16]);

			}


			Pattern HearingNo = Pattern.compile("(?<=Hearing No. )\\s*(.*)");
			Matcher matcher4 = HearingNo.matcher(pdfoutput);
			if (matcher4.find())
			{
				String HearingNo1 = matcher4.group(1).trim();
				System.out.println("Hearing No is==>"+HearingNo1);	
				Pdf_He_HearingeNo.add(HearingNo1);
			} 
			else
			{
				System.out.println("no result");
			}

			Pattern nos = Pattern.compile("(?<=Inspection No.)\\s*(.*)\\s*(|\n)*(?=Hearing No.)",Pattern.DOTALL);
			Matcher matcher1 = nos.matcher(pdfoutput);
			if (matcher1.find())
			{
				String nos1 = matcher1.group(1).trim();
				String[] array=nos1.split(" ");
				System.out.println("Inspection No is==>"+array[0]);
				Pdf_He_InspectionNo.add(array[0]);

				System.out.println("Hoarding No is==>"+array[1]);
				Pdf_He_HoardingNo.add(array[1]);
			} 
			else
			{
				System.out.println("no result");
			}

			Pattern NoticeNo = Pattern.compile("(?<=Notice No. )\\s*(.+?)\\s*(?= dated)");
			Matcher matcher2 = NoticeNo.matcher(pdfoutput);
			if (matcher2.find())
			{
				String NoticeNo1 = matcher2.group(1).trim();
				System.out.println("Notice No is==>"+NoticeNo1);	
				Pdf_He_NoticeNo.add(NoticeNo1);
			} 
			else
			{
				System.out.println("no result");
			}


			Pattern NoticeDate = Pattern.compile("(?<=dated)\\s*(.+?)\\s*(?=. Below)");
			Matcher matcher6 = NoticeDate.matcher(pdfoutput);
			if (matcher6.find())
			{
				String NoticeDate1 = matcher6.group(1).trim();
				System.out.println("Notice Date is==>"+NoticeDate1);	
				Pdf_He_NoticedDate.add(NoticeDate1);
			} 
			else
			{
				System.out.println("no result");
			}

			Pattern FinalDecision = Pattern.compile("(?<=this hearing is )\\s*(.*)");
			Matcher matcher3 = FinalDecision.matcher(pdfoutput);
			if (matcher3.find())
			{
				String FinalDecision1 = matcher3.group(1).trim();
				System.out.println("final Decision of this hearing is==>"+FinalDecision1);	
				Pdf_He_HearingDesicion.add(FinalDecision1);
			} 
			else
			{
				System.out.println("no result");
			}


			Pattern DataSec=Pattern.compile("\\s*(.*)\\s*(|\n)*(?=To,)",Pattern.DOTALL);
			Matcher matcher7=DataSec.matcher(pdfoutput);
			if (matcher7.find()) {
				String DataSec1=matcher7.group(1).trim();
				String [] CollectionDate=DataSec1.split("\\n");
				for(int i=0;i<CollectionDate.length;i++)
				{
					//System.out.println(i+"===>"+CollectionDate[i]);

				}

				System.out.println("Agency/Applicant Name==>"+CollectionDate[0]);
				Pdf_He_AgencyorApplicantName.add(CollectionDate[0]);
				System.out.println("Agency/Applicant Address is==>"+CollectionDate[1]);		
				System.out.println("ULB Name is==>"+CollectionDate[2]);
			}


		} catch (Exception e) {

			e.printStackTrace();
		}
	}


	public static void ADH_Cancellation_Letter_Pdf(String output){
		try {
			Pattern data=Pattern.compile("(?<=To,)\\s*(.*)",Pattern.DOTALL);
			Matcher matcher657=data.matcher(pdfoutput);
			if (matcher657.find()) {
				String data1=matcher657.group(1).trim();
				String [] CollectionDate=data1.split("\\n");
				for(int i=0;i<CollectionDate.length;i++)
				{
					//System.out.println(i+"===>"+CollectionDate[i]);
				}

				System.out.println("Applicant Name==>"+CollectionDate[1]);
				String AppNAme=CollectionDate[1];
				Pdf_CoAC_AgencyorApplicantName.add(AppNAme);
				System.out.println("ULB Name==>"+CollectionDate[2]);
				System.out.println("Letter Type==>"+CollectionDate[4]);


			}

			Pattern HearingNo = Pattern.compile("(?<=against Hearing Order No. )\\s*(.*)");
			Matcher matcher4 = HearingNo.matcher(pdfoutput);
			if (matcher4.find())
			{
				String HearingNo1 = matcher4.group(1).trim();
				System.out.println("Hearing No is==>"+HearingNo1.replace(".", ""));	
				String HON=HearingNo1.replace(".", "");
				Pdf_CoAC_HearingOrderNo.add(HON);
			} 
			else
			{
				System.out.println("no result");
			}



			Pattern HearingorderDate = Pattern.compile("(?<=dated )\\s*(.+?)\\s*(?=, your Contract No.)");
			Matcher matcher6 = HearingorderDate.matcher(pdfoutput);
			if (matcher6.find())
			{
				String HearingorderDate1 = matcher6.group(1).trim();
				System.out.println("Hearing Order Date is==>"+HearingorderDate1);	
				Pdf_CoAC_HearingOrderDate.add(HearingorderDate1);
			} 
			else
			{
				System.out.println("no result");
			}


			Pattern Contractnos = Pattern.compile("(?<=Contract No.)\\s*(.*)\\s*(|\n)*(?=  dated)");
			Matcher matcher1 = Contractnos.matcher(pdfoutput);
			if (matcher1.find())
			{
				String Contractnos1 = matcher1.group(1).trim();
				System.out.println("Contract No is==>"+Contractnos1);
				Pdf_CoAC_contractNo.add(Contractnos1);
			} 
			else
			{
				System.out.println("no result");
			}



			Pattern ContractDate = Pattern.compile("(?<=dated )\\s*(.*)\\s*(|\n)*(?= has been)");
			Matcher matcher7 = ContractDate.matcher(pdfoutput);
			if (matcher7.find())
			{
				String ContractDate1 = matcher7.group(1).trim();
				System.out.println("Contract Date is==>"+ContractDate1);
				Pdf_CoAC_ContractDate.add(ContractDate1);
			} 
			else
			{
				System.out.println("no result");
			}


			Pattern CancellaedDate = Pattern.compile("(?<=cancelled on )\\s*(.*)\\s*(|\n)*(?=.Kindly remove your)");
			Matcher matcher8 = CancellaedDate.matcher(pdfoutput);
			if (matcher8.find())
			{
				String CancellaedDate1 = matcher8.group(1).trim();
				System.out.println("Cancelled Date Date is==>"+CancellaedDate1);
				Pdf_CoAC_CancellationDate.add(CancellaedDate1);
			} 
			else
			{
				System.out.println("no result");
			}


			Pattern HoardingNo=Pattern.compile("\\s*(.*)\\s*(|\n)*(?=Cancelled contracts of agency are as)");
			Matcher matcher2 = HoardingNo.matcher(pdfoutput);
			if (matcher2.find())
			{
				String HoardingNo1 = matcher2.group(1).trim();
				String[] string = HoardingNo1.split("\\s");
				System.out.println("Hoarding No is==>"+(string[0]).trim());
				String HN=string[0].trim();
				Pdf_CoAC_HearingOrderNo.add(HN);
				System.out.println("Hoarding Description is==>"+(string[1]+" "+string[2]));
				String HD=(string[1]+" "+string[2]).trim();
				Pdf_CoAC_HoardingDescription.add(HD);
			} 
			else
			{
				System.out.println("no result");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}


	public static void ADH_Suspension_Letter_Pdf(String output){
		try {
			Pattern data=Pattern.compile("(?<=To,)\\s*(.*)",Pattern.DOTALL);
			Matcher matcher657=data.matcher(pdfoutput);
			if (matcher657.find()) {
				String data1=matcher657.group(1).trim();
				String [] CollectionDate=data1.split("\\n");
				for(int i=0;i<CollectionDate.length;i++)
				{
					//System.out.println(i+"===>"+CollectionDate[i]);
				}

				System.out.println("Applicant Name==>"+CollectionDate[1]);
				Pdf_SoAC_AgencyorApplicantName.add(CollectionDate[1]);
				System.out.println("ULB Name==>"+CollectionDate[2]);
				System.out.println("Letter Type==>"+CollectionDate[4]);
			}

			Pattern HearingNo = Pattern.compile("(?<=Hearing Order No.)\\s*(.*)");
			Matcher matcher4 = HearingNo.matcher(pdfoutput);
			if (matcher4.find())
			{
				String HearingNo1 = matcher4.group(1).trim();
				System.out.println("Hearing No is==>"+HearingNo1.replace(".", ""));	
				String SHN=HearingNo1.replace(".", "");
				Pdf_SoAC_HearingOrderNo.add(SHN);
			} 
			else
			{
				System.out.println("no result");
			}

			Pattern HearingorderDate = Pattern.compile("(?<=dated )\\s*(.+?)\\s*(?=, your contract)");
			Matcher matcher6 = HearingorderDate.matcher(pdfoutput);
			if (matcher6.find())
			{
				String HearingorderDate1 = matcher6.group(1).trim();
				System.out.println("Hearing Order Date is==>"+HearingorderDate1);
				Pdf_SoAC_HearingOrderDate.add(HearingorderDate1);
			} 
			else
			{
				System.out.println("no result");
			}

			Pattern Contractnos = Pattern.compile("(?<=your contract)\\s*(.*)\\s*(|\n)*(?= dated)");
			Matcher matcher1 = Contractnos.matcher(pdfoutput);
			if (matcher1.find())
			{
				String Contractnos1 = matcher1.group(1).trim();
				System.out.println("Contract No is==>"+Contractnos1);
				Pdf_SoAC_contractNo.add(Contractnos1);
			} 
			else
			{
				System.out.println("no result");
			}

			Pattern ContractDate = Pattern.compile("(?<=dated )\\s*(.*)\\s*(|\n)*(?= has been suspended)");
			Matcher matcher7 = ContractDate.matcher(pdfoutput);
			if (matcher7.find())
			{
				String ContractDate1 = matcher7.group(1).trim();
				System.out.println("Contract Date is==>"+ContractDate1);
				Pdf_SoAC_contractDate.add(ContractDate1);
			} 
			else
			{
				System.out.println("no result");
			}


			Pattern CancellaedfromDate = Pattern.compile("(?<=the period )\\s*(.*)\\s*(|\n)*(?= to)");
			Matcher matcher8 = CancellaedfromDate.matcher(pdfoutput);
			if (matcher8.find())
			{
				String CancellaedfromDate1 = matcher8.group(1).trim();
				System.out.println("Cancelled From Date Date is==>"+CancellaedfromDate1);
				Pdf_SoAC_SuspensionFromDate.add(CancellaedfromDate1);
			} 
			else
			{
				System.out.println("no result");
			}


			Pattern CancellaedToDate = Pattern.compile("(?<=to )\\s*(.*)\\s*(|\n)*(?=. You are)");
			Matcher matcher9 = CancellaedToDate.matcher(pdfoutput);
			if (matcher9.find())
			{
				String CancellaedToDate1 = matcher9.group(1).trim();
				System.out.println("Cancelled To Date Date is==>"+CancellaedToDate1);
				Pdf_SoAC_SuspensionToDate.add(CancellaedToDate1);
			} 
			else
			{
				System.out.println("no result");
			}

			Pattern HoardingNo5=Pattern.compile("(?<=To,)\\s*(.*)\\s*(|\n)*(?=Reference :  )",Pattern.DOTALL);
			Matcher matcher2 = HoardingNo5.matcher(pdfoutput);
			if (matcher2.find())
			{
				String HoardingNo1 = matcher2.group(1).trim();
				String[] string = HoardingNo1.split("\\s");
				System.out.println("Applicant Address is==>"+(string[0]+" "+string[1]+" "+string[2]+" "+string[3]));
			} 
			else
			{
				System.out.println("no result");
			}


			Pattern HoardingNo=Pattern.compile("(?<=Suspension of  Contract)\\s*(.*)\\s*(|\\n)*(?=Subject    : )",Pattern.DOTALL);
			Matcher matcher102 = HoardingNo.matcher(pdfoutput);
			if (matcher102.find())
			{
				String HoardingNo1 = matcher102.group(1).trim();
				String[] string = HoardingNo1.split("\\s");
				System.out.println("Hoarding No is==>"+string[4].trim());
				String SHON=string[4].trim();
				Pdf_SoAC_HoardingNo.add(SHON);
				System.out.println("Hoarding Description is==>"+(string[5]+" "+string[6]));
				String SHDesc=(string[5]+" "+string[6]).trim();
				Pdf_SoAC_HoardingDescription.add(SHDesc);
			} 
			else
			{
				System.out.println("no result");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	//// Approval Letter For All Services in ADH
	public static void ADH_Approval_Letter_Pdf(String output){

		try {
			Pattern data=Pattern.compile("(?<=Sir/Madam,)\\s*(.*)",Pattern.DOTALL);
			Matcher matcher657=data.matcher(pdfoutput);
			if (matcher657.find()) {
				String data1=matcher657.group(1).trim();
				String [] CollectionDate=data1.split("\\n");
				for(int i=0;i<CollectionDate.length;i++)
				{
					//System.out.println(i+"===>"+CollectionDate[i]);
				}
				System.out.println("Applicant Name==>"+CollectionDate[13].trim());
				Pdf_Approval_ApplicantName.add(CollectionDate[13].trim());
				System.out.println("Service Name==>"+CollectionDate[24].trim());
				String[] AppNo=CollectionDate[0].split(" ");
				System.out.println("Application No ==>"+AppNo[8].trim());
				Pdf_Approval_ApplicantionNo.add(AppNo[8].trim());
				String[] Data=CollectionDate[3].split(" ");
				System.out.println("Contract No ==>"+Data[0].trim());
				Pdf_Approval_ContractNo.add(Data[0].trim());
			}

			Pattern ContractDate = Pattern.compile("(?<=dated )\\s*(.*)\\s*(|\n)*(?=.Contract)");
			Matcher matcher9 = ContractDate.matcher(pdfoutput);
			if (matcher9.find())
			{
				String ContractDate1 = matcher9.group(1).trim();
				System.out.println("Contract Date is==>"+ContractDate1);
				Pdf_Approval_ContractDate.add(ContractDate1);
			} 
			else
			{
				System.out.println("no result");
			}

			Pattern ContractFromDate = Pattern.compile("(?<=from )\\s*(.*)\\s*(|\n)*(?= to)");
			Matcher matcher10 = ContractFromDate.matcher(pdfoutput);
			if (matcher10.find())
			{
				String ContractFromDate1 = matcher10.group(1).trim();
				System.out.println("Contract from Date is==>"+ContractFromDate1);
				Pdf_Approval_ContractFromDate.add(ContractFromDate1);
			} 
			else
			{
				System.out.println("no result");
			}


			Pattern ContractToDate = Pattern.compile("(?<=to )\\s*(.*)\\s*(|\n)*(?=Refer)");
			Matcher matcher3 = ContractToDate.matcher(pdfoutput);
			if (matcher3.find())
			{
				String ContractToDate1 = matcher3.group(1).trim();
				System.out.println("Contract To Date is==>"+ContractToDate1);
				Pdf_Approval_ContractToDate.add(ContractToDate1);
			} 
			else
			{
				System.out.println("no result");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}




	////Booking of Hoarding for Display of Advertisement Acceptance Letter
	public static void ADH_Acceptance_Letter_Pdf(String output){

		try {
			Pattern data=Pattern.compile("(?<=Sir/Madam,)\\s*(.*)",Pattern.DOTALL);
			Matcher matcher657=data.matcher(pdfoutput);
			if (matcher657.find()) {
				String data1=matcher657.group(1).trim();
				String [] CollectionDate=data1.split("\\n");
				for(int i=0;i<CollectionDate.length;i++)
				{
					//System.out.println(i+"===>"+CollectionDate[i]);

				}
				System.out.println("ULB Name==>"+CollectionDate[5].trim());
				System.out.println("LOI No==>"+CollectionDate[15].trim());
				Pdf_Acceptance_LOINo.add(CollectionDate[15].trim());
				System.out.println("LOI Date==>"+CollectionDate[16].trim());
				Pdf_Acceptance_LOIDate.add(CollectionDate[16].trim());
				System.out.println("Applicant Name==>"+CollectionDate[20].trim());
				Pdf_Acceptance_AppliantName.add(CollectionDate[20].trim());
				String[] Data=CollectionDate[53].split(":");
				System.out.println("Total Amount ==>"+Data[1].trim());
				Pdf_Acceptance_TotalAmt.add(Data[1].trim());
			}

			Pattern ContractDate = Pattern.compile("(?<=mentioned below :)\\s*(.*)\\s*(|\n)*(?=Total Payable Amount)");
			Matcher matcher9 = ContractDate.matcher(pdfoutput);
			if (matcher9.find())
			{
				String ContractDate1 = matcher9.group(1).trim();
				System.out.println("Total Payble Amount==>"+ContractDate1);
				Pdf_Acceptance_TotalPaybleAmt.add(ContractDate1);
			} 
			else
			{
				System.out.println("no result");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	// Loi Payment Receipt ::: Cash ::: Booking of Hoarding:::: LOI PAYMENT METHOD RECEIPT
	public static void ADH_LOiPaymentReceipt_Letter_Pdf(String output){
		try {
			Pattern data=Pattern.compile("\\s*(.*)\\s*(|\n)*(?=Bihar Municipal Act 2007)",Pattern.DOTALL);
			Matcher matcher657=data.matcher(pdfoutput);
			if (matcher657.find()) {
				String data1=matcher657.group(1).trim();
				String [] CollectionDate=data1.split("\\n");
				for(int i=0;i<CollectionDate.length;i++)
				{
					//System.out.println(i+"===>"+CollectionDate[i]);
				}

				String[] Data=CollectionDate[0].split(" ");
				System.out.println("Receipt No ==>"+Data[0].trim());
				System.out.println("Receipt Date==>"+Data[1].trim());
				System.out.println("Applicant Name===>"+CollectionDate[1]);
				pdf_Loi_Receipt_ApplicantName.add(CollectionDate[1]);
				System.out.println("Total Received Amount===>"+CollectionDate[13].trim());
				pdf_Loi_Receipt_TotalReceivedAmt.add(CollectionDate[13].trim());
				String[] Data1=CollectionDate[24].split(" ");
				System.out.println("Mode Of Payment ==>"+Data1[0].trim());
				pdf_Loi_Receipt_ModeOfPayment.add(Data1[0].trim());
			}


			Pattern data2=Pattern.compile("(?<=LOI Date )\\s*(.*)",Pattern.DOTALL);
			Matcher matcher6=data2.matcher(pdfoutput);
			if (matcher6.find()) {
				String data3=matcher6.group(1).trim();
				String [] CollectionDate=data3.split("\\n");
				for(int i=0;i<CollectionDate.length;i++)
				{
					//System.out.println(i+"===>"+CollectionDate[i]);
				}

				System.out.println("Loi date==>"+CollectionDate[0].trim());
				pdf_Loi_Receipt_LOIDate.add(CollectionDate[0].trim());
				System.out.println("LOI No===>"+CollectionDate[1]);
				pdf_Loi_Receipt_LOINo.add(CollectionDate[1]);
				System.out.println("Booking No ==>"+CollectionDate[10].trim());
				pdf_Loi_Receipt_BookingNo.add(CollectionDate[10].trim());
				System.out.println("Contract No ==>"+CollectionDate[12].trim());
				pdf_Loi_Receipt_ContractNo.add(CollectionDate[12].trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}




	////////////////////////// Method For Payment Scedule Date comparison///////////

	public  void differenceDays() throws ParseException{
		String date1 = startDate;
		String date2 = endDate;
		String format = "dd/MMM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date fromDate = sdf.parse(date1);
		Date toDate = sdf.parse(date2);

		long diff = toDate.getTime() - fromDate.getTime();
		String dateFormat="duration: ";
		int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
		if(diffDays>0){
			dateFormat+=diffDays+" day ";
		}
		System.out.println(diffDays);
		System.out.println(dateFormat);
		diffMonthanddays=(diffDays/30);
		System.out.println((diffDays/30)+" months and "+(diffDays%30)+" days");
	}


	////Payment Schedule method For Agency Contract having Payment Methods "Monthly/Bimonthly Etc"
	public void PaymentSchedule() throws InterruptedException{
		try{

			int noofmonths=((driver.findElements(By.xpath("//*[@id=\"frmHoardingAndAdvertisementSetup1\"]/table/tbody/tr")).size())-1)/4;

			System.out.println("no of months==>+"+noofmonths);
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


	//Payment Schedule method For Agency Contract Payment Schedule Mode is "Other"
	public void PaymentSchedule_Other() throws InterruptedException{
		try{

			int noofmonths=((driver.findElements(By.xpath("//*[@id=\"advPayScheduleTab\"]/tbody")).size())-2)/3;
			//int noofmonths=((driver.findElements(By.xpath("//*[@id=\"frmHoardingAndAdvertisementSetup1\"]/table/tbody/tr")).size())-2)/3;
			System.out.println("Integer no of Months =>"+noofmonths);
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
					if(NO_OF_EMI>1){
						if (i<(NO_OF_EMI-1)) {
							driver.findElement(By.linkText("Add")).click();	
						}
					}
				}else {
					mDateSelect("id", payDueDate+i, mGetPropertyFromFile("Date_row"+ii));
					driver.findElement(By.id(payAmount+i)).sendKeys(mGetPropertyFromFile(("Amount_row"+ii)));
					if(NO_OF_EMI>1){
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

			if(ScrutinyServiceName.equalsIgnoreCase("Booking of Hoarding for display of Advertisement"))
			{
				noofmonths=((driver.findElements(By.xpath("//*[@id=\"frmBookingOfHoardingAdvertisebookoing\"]/table/tbody/tr")).size())-1)/3;
				System.out.println("no of noofmonths==>+"+noofmonths);
			}
			if(ScrutinyServiceName.equalsIgnoreCase("Setup of Hoarding for display of Advertisement"))
			{
				noofmonths=((driver.findElements(By.xpath("//*[@id=\"frmHoardingAndAdvertisementSetupdet\"]/table/tbody/tr")).size())-1)/3;
				System.out.println("no of noofmonths==>+"+noofmonths);
			}

			if(ScrutinyServiceName.equalsIgnoreCase("Renewal of Advertisement Contract"))
			{
				noofmonths=((driver.findElements(By.xpath("//*[@id=\"frmRenewalOfContract2\"]/table/tbody/tr")).size())-1)/4;
				System.out.println("no of noofmonths==>+"+noofmonths);
			}

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

	//*[@id="advPayScheduleTab"]/tbody/tr
	//Scrutiny Payment Schedule method For All Services Payment Schedule Mode is "Other"
	public void ScrutinyPaymentSchedule_Other() throws InterruptedException{
		try{
			if(ScrutinyServiceName.equalsIgnoreCase("Booking of Hoarding for display of Advertisement"))
			{
				//noofmonths=((driver.findElements(By.xpath("//*[@id=\"frmBookingOfHoardingAdvertisebookoing\"]/table/tbody/tr")).size())-1)/3;
				//*[@id="advPayScheduleTab"]/tbody/tr/
				noofmonths=((driver.findElements(By.xpath("//*[@id=\"advPayScheduleTab\"]/tbody/tr")).size())-1)/3;
				System.out.println("no of noofmonths==>+"+noofmonths);
			}
			if(ScrutinyServiceName.equalsIgnoreCase("Setup of Hoarding for display of Advertisement"))
			{
				noofmonths=((driver.findElements(By.xpath("//*[@id=\"frmHoardingAndAdvertisementSetupdet\"]/table/tbody/tr")).size())-1)/3;
				System.out.println("no of noofmonths==>+"+noofmonths);
			}

			if(ScrutinyServiceName.equalsIgnoreCase("Renewal of Advertisement Contract"))
			{
				noofmonths=((driver.findElements(By.xpath("//*[@id=\"frmRenewalOfContract2\"]/table/tbody/tr")).size())-1)/3;
				System.out.println("no of noofmonths==>+"+noofmonths);
			}


			//int noofmonths=((driver.findElements(By.xpath("//*[@id=\"frmHoardingAndAdvertisementSetup1\"]/table/tbody/tr")).size())-2)/3;
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
					mCustomWait(1000);
					if (i<(NO_OF_EMI-1)) {
						driver.findElement(By.linkText("Add")).click();	
					}

				}else {
					mDateSelect("id", payDueDate+i, mGetPropertyFromFile("Date_row"+ii));
					driver.findElement(By.id(payAmount+i)).sendKeys(mGetPropertyFromFile(("Amount_row"+ii)));
					if (NO_OF_EMI==i+1){}else {
						if (NO_OF_EMI>1){
							driver.findElement(By.linkText("Add")).click();
						}
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

	//Piyush on 02/03/2017 Get Cutternt date/Booking date
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




	////////Assertions Methods For reports///////////////////////////////////////////////////////////////////////////////////

	//Accepatnce Letter Assertion Method
	public void adh_Acceptance_letter_assertion(){
		try{
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				api.PdfPatterns.ADH_Acceptance_Letter_Pdf(pdfoutput);
				mAssert(Pdf_Acceptance_AppliantName.get(CurrentinvoCount), adh_ApplicantFullname.get(CurrentinvoCount), "Applicant Name in Acceptance Letter   dose not match with Actual ::>>>>>"+ Pdf_Acceptance_AppliantName.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+adh_ApplicantFullname.get(CurrentinvoCount));

			}
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in adh_Acceptance_letter_assertion");
		}
	}




	//Booking of Hoarding Approval Letter  Assertion Method
	public void adh_BookingOfHoarding_Approval_letter_assertion(){
		try{
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				api.PdfPatterns.ADH_Acceptance_Letter_Pdf(pdfoutput);
				mAssert(Pdf_Approval_ApplicantName.get(CurrentinvoCount),adh_ApplicantFullname.get(CurrentinvoCount), "Applicant Name in Booking of Hoarding for Display of Advertisement Letter dose not match with Actual ::>>>>>"+ Pdf_Approval_ApplicantName.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+adh_ApplicantFullname.get(CurrentinvoCount));
				mAssert(Pdf_Approval_ApplicantionNo.get(CurrentinvoCount), ApplicationNoADH.get(CurrentinvoCount), "Application No in Booking of Hoarding for Display of Advertisement Letter dose not match with Actual ::>>>>>"+ Pdf_Approval_ApplicantionNo.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+ApplicationNoADH.get(CurrentinvoCount));
				mAssert(Pdf_Approval_ContractFromDate.get(CurrentinvoCount), adh_BookingfromDate.get(CurrentinvoCount), "Booking From Date in Booking of Hoarding for Display of Advertisement Letter dose not match with Actual ::>>>>>"+ Pdf_Approval_ContractFromDate.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+adh_BookingfromDate.get(CurrentinvoCount));
				mAssert(Pdf_Approval_ContractToDate.get(CurrentinvoCount), adh_BookingtoDate.get(CurrentinvoCount), "Booking To Date in Booking of Hoarding for Display of Advertisement Letter dose not match with Actual ::>>>>>"+ Pdf_Approval_ContractToDate.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+adh_BookingtoDate.get(CurrentinvoCount));
				mAssert(Pdf_Approval_ContractNo.get(CurrentinvoCount), ContractBillPayment_ContractNo.get(CurrentinvoCount), "Contract No in Booking of Hoarding for Display of Advertisement Letter  dose not match with Actual ::>>>>>"+ Pdf_Approval_ContractNo.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+ContractBillPayment_ContractNo.get(CurrentinvoCount));
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in adh_BookingOfHoarding_Approval_letter_assertion Method");
		}
	}


	//Set Up Of Hoarding  Approval Letter  Assertion Method
	public void adh_SetUpOfHoarding_Approval_letter_assertion(){
		try{
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				api.PdfPatterns.ADH_Approval_Letter_Pdf(pdfoutput);
				mAssert(Pdf_SetUp_Approval_ApplicantName.get(CurrentinvoCount),adh_ApplicantFullname.get(CurrentinvoCount), "Applicant Name in Setup of Hoarding for Display of Advertisement Letter  dose not match with Actual ::>>>>>"+ Pdf_SetUp_Approval_ApplicantName.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+adh_ApplicantFullname.get(CurrentinvoCount));
				mAssert(Pdf_SetUp_Approval_ApplicationNo.get(CurrentinvoCount), ApplicationNoADH.get(CurrentinvoCount), "Application Number in Setup of Hoarding for Display of Advertisement Letter dose not match with Actual ::>>>>>"+ Pdf_SetUp_Approval_ApplicationNo.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+ApplicationNoADH.get(CurrentinvoCount));
				mAssert(Pdf_SetUp_Approval_ContractNo.get(CurrentinvoCount), ContractBillPayment_ContractNo.get(CurrentinvoCount), "Contract No in Setup of Hoarding for Display of Advertisement Letter   dose not match with Actual ::>>>>>"+ Pdf_SetUp_Approval_ContractNo.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+ContractBillPayment_ContractNo.get(CurrentinvoCount));

			}
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in adh_SetUpOfHoarding_Approval_letter_assertion");
		}
	}


	//Booking of Hoarding Approval Letter  Assertion Method
	public void adh_RenewalOFAdvertsment_Approval_letter_assertion(){
		try{
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				api.PdfPatterns.ADH_RenewalOfAdvertisment_Approval_Letter_Pdf(pdfoutput);
				mAssert(Pdf_RoA_Approval_ApplicantName.get(CurrentinvoCount),adh_ApplicantFullname.get(CurrentinvoCount), "Applicant Name in Renewal of Advertisement Contract Letter  dose not match with Actual ::>>>>>"+Pdf_RoA_Approval_ApplicantName.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+adh_ApplicantFullname.get(CurrentinvoCount));
				mAssert(Pdf_RoA_Approval_ApplicantionNo.get(CurrentinvoCount), ApplicationNoADH.get(CurrentinvoCount), "Application Number in Renewal of Advertisement Contract Letter dose not match with Actual ::>>>>>"+ Pdf_RoA_Approval_ApplicantionNo.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+ApplicationNoADH.get(CurrentinvoCount));
				mAssert(Pdf_RoA_Approval_RenewalDate.get(CurrentinvoCount), adh_rc_RenewalDate.get(CurrentinvoCount), "Renewal Date in Renewal of Advertisement Contract Letter dose not match with Actual ::>>>>>"+ Pdf_RoA_Approval_RenewalDate.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+adh_rc_RenewalDate.get(CurrentinvoCount));
				mAssert(Pdf_RoA_Approval_RenewedfromDate.get(CurrentinvoCount),adh_rc_BookingfromDate.get(CurrentinvoCount), "Renewal/Booking From Date Amount in Renewal of Advertisement Contract Letter   dose not match with Actual ::>>>>>"+ Pdf_RoA_Approval_RenewedfromDate.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+adh_rc_BookingfromDate.get(CurrentinvoCount));
				mAssert(Pdf_RoA_Approval_RenewedToDate.get(CurrentinvoCount), adh_rc_BookingtoDate.get(CurrentinvoCount), "Renewal/Booking To Date in Renewal of Advertisement Contract Letter dose not match with Actual ::>>>>>"+ Pdf_RoA_Approval_RenewedToDate.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+adh_rc_BookingtoDate.get(CurrentinvoCount));
				mAssert(Pdf_RoA_Approval_ContractNo.get(CurrentinvoCount), ContractBillPayment_ContractNo.get(CurrentinvoCount), "Contract No in Renewal of Advertisement Contract Letter dose not match with Actual ::>>>>>"+ Pdf_RoA_Approval_ContractNo.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+ContractBillPayment_ContractNo.get(CurrentinvoCount));
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in adh_RenewalOFAdvertsment_Approval_letter_assertion");
		}
	}


	//Agency Contract Note Letter  Assertion Method
	public void adh_AgencyContractNote_letter_assertion(){
		try{
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				api.PdfPatterns.ADH_Agency_Contract_Note_Pdf(pdfoutput);
				mAssert(pdf_ContractNote_ApplicantName.get(CurrentinvoCount),Agc_AgencyName.get(CurrentinvoCount), "Applicant/agency Name in Advertisement Contract Note Letter  dose not match with Actual ::>>>>>"+ pdf_ContractNote_ApplicantName.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+Agc_AgencyName.get(CurrentinvoCount));
				mAssert(pdf_ContractNote_ContractAmt.get(CurrentinvoCount), Acg_ContractAmount.get(CurrentinvoCount), "Contract Amount in Advertisement Contract Note Letter   dose not match with Actual ::>>>>>"+pdf_ContractNote_ContractAmt.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+Acg_ContractAmount.get(CurrentinvoCount));
				mAssert(pdf_ContractNote_ContractNo.get(CurrentinvoCount), adhContractNo.get(CurrentinvoCount), "Contract No in Advertisement Contract Note Letter dose not match with Actual ::>>>>>"+ pdf_ContractNote_ContractNo.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+adhContractNo.get(CurrentinvoCount));
				mAssert(pdf_ContractNote_ContractFrom.get(CurrentinvoCount), Acg_ContractFromDate.get(CurrentinvoCount), "Contract From date in Advertisement Contract Note Letter dose not match with Actual ::>>>>>"+pdf_ContractNote_ContractFrom.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+ Acg_ContractFromDate.get(CurrentinvoCount));

			}
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in adh_RenewalOFAdvertsment_Approval_letter_assertion");
		}
	}



	//Application Time Payment Payment recipt Cash Assertion Method
	public void adh_ApplicationTimePaymentReceipt_Cash_assertion(){
		try{
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				api.PdfPatterns.ADH_ApplicationTimePaymentReceipt_Letter_Pdf(pdfoutput);
				mAssert(pdf_ApplicationTime_Receipt_ApplicantName.get(CurrentinvoCount),adh_ApplicantFullname.get(CurrentinvoCount), "Applicant Name in Application Time Payment Receipt  dose not match with Actual ::>>>>>"+ pdf_ApplicationTime_Receipt_ApplicantName.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+adh_ApplicantFullname.get(CurrentinvoCount));
				mAssert(pdf_ApplicationTime_Receipt_ModeOfPayment.get(CurrentinvoCount), Adh_ModeOfPaymentAppTime.get(CurrentinvoCount), "Mode of Payment in  Application Time Payment Receipt  dose not match with Actual ::>>>>>"+ pdf_ApplicationTime_Receipt_ModeOfPayment.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+Adh_ModeOfPaymentAppTime.get(CurrentinvoCount));

			}
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in adh_ApplicationTimePaymentReceipt_Cash_assertion");
		}
	}


	//Application Time Payment Payment recipt Cheque Assertion Method
	public void adh_ApplicationTimePaymentReceipt_Cheque_assertion(){
		try{
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				api.PdfPatterns.ADH_ChecqueApplicationTimePaymentReceipt_Letter_Pdf(pdfoutput);
				mAssert(pdf_ApplicationTime_Receipt_ApplicantName.get(CurrentinvoCount),adh_ApplicantFullname.get(CurrentinvoCount), "Applicant Name in Application Time Payment Receipt  dose not match with Actual ::>>>>>"+ pdf_ApplicationTime_Receipt_ApplicantName.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+adh_ApplicantFullname.get(CurrentinvoCount));
				//mAssert(pdf_ApplicationTime_Receipt_ModeOfPayment.get(CurrentinvoCount), Adh_ModeOfPaymentAppTime.get(CurrentinvoCount), "Mode of Payment in  Application Time Payment Receipt  dose not match with Actual ::>>>>>"+ pdf_ApplicationTime_Receipt_ModeOfPayment.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+Adh_ModeOfPaymentAppTime.get(CurrentinvoCount));

			}
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in adh_ApplicationTimePaymentReceipt_Cheque_assertion");
		}
	}

	//LOI Time Payment Payment recipt Cheque Assertion Method
	public void adh_LOITimePaymentReceipt_Cheque_assertion(){
		try{
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				api.PdfPatterns.ADH_ChecqueApplicationTimePaymentReceipt_Letter_Pdf(pdfoutput);
				mAssert(pdf_Loi_Receipt_ApplicantName.get(CurrentinvoCount),adh_ApplicantFullname.get(CurrentinvoCount), "Applicant Name in LOI Payment Receipt dose not match with Actual ::>>>>>"+ pdf_Loi_Receipt_ApplicantName.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+adh_ApplicantFullname.get(CurrentinvoCount));
				mAssert(pdf_Loi_Receipt_LOIDate.get(CurrentinvoCount), Adh_LOIDate.get(CurrentinvoCount), "LOI Date in Acceptance Letter  dose not match with Actual ::>>>>>"+ pdf_Loi_Receipt_LOIDate.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+Adh_LOIDate.get(CurrentinvoCount));
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in adh_LOITimePaymentReceipt_Cheque_assertion");
		}
	}

	//Contract Bill Payment Letter Assertion Method
	public void adh_ContractBillPaymentCash_letter_assertion(){
		try{
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				api.PdfPatterns.ADH_Cash_ContractBillPayment_Receipt_Letter_Pdf(pdfoutput);
				mAssert(pdf_ContractBillpayment_receivedFrom.get(CurrentinvoCount),adh_ApplicantFullname.get(CurrentinvoCount), "Received from/Applicant Name in contract Bill Payment receipt  dose not match with Actual ::>>>>>"+ pdf_ContractBillpayment_receivedFrom.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+adh_ApplicantFullname.get(CurrentinvoCount));
				mAssert(pdf_ContractBillpayment_receivedAmt.get(CurrentinvoCount), ContractBillPayment_totalPaybleAmt.get(CurrentinvoCount), "Received Amount in contract Bill Payment receipt dose not match with Actual ::>>>>>"+ pdf_ContractBillpayment_receivedAmt.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+ContractBillPayment_totalPaybleAmt.get(CurrentinvoCount));
				mAssert(pdf_ContractBillpayment_contractNo.get(CurrentinvoCount), ContractBillPayment_ContractNo.get(CurrentinvoCount), "Contract No in contract Bill Payment receipt  dose not match with Actual ::>>>>>"+ pdf_ContractBillpayment_contractNo.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+ContractBillPayment_ContractNo.get(CurrentinvoCount));
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in adh_ContractBillPaymentCash_letter_assertion");
		}
	}


	//Bill Dues  Letter Assertion Method
	public void adh_BillDues_letter_assertion(){
		try{
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				api.PdfPatterns.ADH_BillDues_Letter_Pdf(pdfoutput);
				mAssert(Pdf_BP_ApplicantName.get(CurrentinvoCount),adh_ApplicantFullname.get(CurrentinvoCount), "Applicant Name in Bill Printing Bill Dues Letter dose not match with Actual ::>>>>>"+ Pdf_BP_ApplicantName.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+adh_ApplicantFullname.get(CurrentinvoCount));
				mAssert(Pdf_BP_ContractFromDate, SoAC_HearingOrderDate, "Applicant Name in Acceptance Letter   dose not match with Actual ::>>>>>"+ Pdf_Acceptance_AppliantName.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+SoAC_HearingOrderDate.get(CurrentinvoCount));
				mAssert(Pdf_BP_ContractToDate,SoAC_AgencyorApplicantName, "LOI Number in Acceptance Letter  dose not match with Actual ::>>>>>"+ Pdf_Acceptance_LOINo.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+SoAC_AgencyorApplicantName.get(CurrentinvoCount));
				mAssert(Pdf_BP_TotalPaybleAmt, SoAC_HearingOrderNo, "LOI Date in Acceptance Letter  dose not match with Actual ::>>>>>"+ Pdf_Acceptance_LOIDate.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+SoAC_HearingOrderNo.get(CurrentinvoCount));
				mAssert(Pdf_BP_DiscountAmount, SoAC_HearingOrderDate, "Applicant Name in Acceptance Letter   dose not match with Actual ::>>>>>"+ Pdf_Acceptance_AppliantName.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+SoAC_HearingOrderDate.get(CurrentinvoCount));	
				mAssert(Pdf_BP_ContractNo,ContractBillPayment_ContractNo, "LOI Number in Acceptance Letter  dose not match with Actual ::>>>>>"+ Pdf_Acceptance_LOINo.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+SoAC_AgencyorApplicantName.get(CurrentinvoCount));
				mAssert(Pdf_BP_AgencyNo, SoAC_HearingOrderNo, "LOI Date in Acceptance Letter  dose not match with Actual ::>>>>>"+ Pdf_Acceptance_LOIDate.get(CurrentinvoCount)+" "+"Expected::>>>>>>"+SoAC_HearingOrderNo.get(CurrentinvoCount));

			}
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in adh_Acceptance_letter_assertion");
		}
	}


	/*public void adh_Inspection_letter_assertion(){
		try{
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				ADH_Inspection_Letter_Pdf(pdfoutput);
				//mAssert(Ins_Pdf_hoardingNo.get(CurrentinvoCount),Ins_hoardingNo.get(CurrentinvoCount),"Hoarding No in Inspection Letter dose not match with actual::"+Ins_Pdf_hoardingNo.get(CurrentinvoCount)+"Expected::"+Ins_hoardingNo.get(CurrentinvoCount));
				mAssert(Ins_Pdf_agencyName.get(CurrentinvoCount),Ins_agencyName.get(CurrentinvoCount),"Agency/Applicant name in Inspection Letter dose not match with actual::"+Ins_Pdf_agencyName.get(CurrentinvoCount)+"Expected::"+Ins_agencyName.get(CurrentinvoCount));
				mAssert(Ins_Pdf_ContractID.get(CurrentinvoCount),Ins_ContractID.get(CurrentinvoCount),"Contract No in Inspection Letter dose not match with actual::"+Ins_Pdf_ContractID.get(CurrentinvoCount)+"Expected::"+Ins_ContractID.get(CurrentinvoCount));
				mAssert(Ins_Pdf_Inspectiondate.get(CurrentinvoCount),Ins_Inspectiondate.get(CurrentinvoCount),"Contract No in Inspection Letter dose not match with actual::"+Ins_Pdf_Inspectiondate.get(CurrentinvoCount)+"Expected::"+Ins_Inspectiondate.get(CurrentinvoCount));
				mAssert(Ins_Pdf_InspectedBy.get(CurrentinvoCount),Ins_InspectedBy.get(CurrentinvoCount),"Inspected By employee in Inspection Letter dose not match with actual::"+Ins_Pdf_InspectedBy.get(CurrentinvoCount)+"Expected::"+Ins_InspectedBy.get(CurrentinvoCount));
				mAssert(Ins_Pdf_inpectionNo.get(CurrentinvoCount), Ins_inpectionNo.get(CurrentinvoCount),"Inspection No in Inspection Letter dose not match with actual::"+Ins_Pdf_inpectionNo.get(CurrentinvoCount)+"Expected::"+Ins_inpectionNo.get(CurrentinvoCount));
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in adh_Inspection_letter_assertion");
		}
	}*/


	/*public void adh_ShowCause_letter_assertion(){
		try{
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				ADH_ShowCause_Letter_Pdf(pdfoutput);
				mAssert(Pdf_Show_HoardingNo.get(CurrentinvoCount),Ins_hoardingNo.get(CurrentinvoCount),"Hoarding No in Show Cause notice dose not match with actual::"+Pdf_Show_HoardingNo.get(CurrentinvoCount)+"Expected::"+Ins_hoardingNo.get(CurrentinvoCount));
				mAssert(Pdf_Show_NoticeType.get(CurrentinvoCount),Show_NoticeType.get(CurrentinvoCount),"Agency/Applicant name in Inspection Letter dose not match with actual::"+Pdf_Show_NoticeType.get(CurrentinvoCount)+"Expected::"+Show_NoticeType.get(CurrentinvoCount));
				mAssert(Pdf_Show_contractNo.get(CurrentinvoCount),Ins_ContractID.get(CurrentinvoCount),"Contract No in Show Cause notice dose not match with actual::"+Pdf_Show_contractNo.get(CurrentinvoCount)+"Expected::"+Ins_ContractID.get(CurrentinvoCount));
				mAssert(Pdf_NoticeDate.get(CurrentinvoCount),Show_NoticeDate.get(CurrentinvoCount),"Notice date in Show Cause notice Letter dose not match with actual::"+Pdf_NoticeDate.get(CurrentinvoCount)+"Expected::"+Show_NoticeDate.get(CurrentinvoCount));
				mAssert(Pdf_Show_NoticeNo.get(CurrentinvoCount),Show_NoticeNo.get(CurrentinvoCount),"Notice No in Show Cause notice Letter dose not match with actual::"+Pdf_Show_NoticeNo.get(CurrentinvoCount)+"Expected::"+Show_NoticeNo.get(CurrentinvoCount));
				mAssert(Pdf_Show_InspectionNo.get(CurrentinvoCount), Ins_inpectionNo.get(CurrentinvoCount),"Inspection No in Show Cause notice dose not match with actual::"+Pdf_Show_InspectionNo.get(CurrentinvoCount)+"Expected::"+Ins_inpectionNo.get(CurrentinvoCount));
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in adh_ShowCause_letter_assertion");
		}
	}*/


	/*public void adh_Hearing_letter_assertion(){
		try{
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				ADH_Hearing_Letter_Pdf(pdfoutput);
				mAssert(Pdf_He_HoardingNo.get(CurrentinvoCount),Ins_hoardingNo.get(CurrentinvoCount),"Hoarding No in Hearing Letter dose not match with actual::"+Pdf_He_HoardingNo.get(CurrentinvoCount)+"Expected::"+Ins_hoardingNo.get(CurrentinvoCount));
				mAssert(Pdf_He_HearingDesicion.get(CurrentinvoCount),He_HearingDesicion.get(CurrentinvoCount),"Final Hearing Decision in Hearing Letter dose not match with actual::"+Pdf_He_HearingDesicion.get(CurrentinvoCount)+"Expected::"+He_HearingDesicion.get(CurrentinvoCount));
				mAssert(Pdf_He_NoticedDate.get(CurrentinvoCount),Show_NoticeDate.get(CurrentinvoCount),"Notice date in Hearing Letter dose not match with actual::"+Pdf_NoticeDate.get(CurrentinvoCount)+"Expected::"+Show_NoticeDate.get(CurrentinvoCount));
				mAssert(Pdf_He_NoticeNo.get(CurrentinvoCount),Show_NoticeNo.get(CurrentinvoCount),"Notice No in Hearing Letter dose not match with actual::"+Pdf_Show_NoticeNo.get(CurrentinvoCount)+"Expected::"+Show_NoticeNo.get(CurrentinvoCount));
				mAssert(Pdf_He_InspectionNo.get(CurrentinvoCount), Ins_inpectionNo.get(CurrentinvoCount),"Inspection No in Hearing Letter dose not match with actual::"+Pdf_Show_InspectionNo.get(CurrentinvoCount)+"Expected::"+Ins_inpectionNo.get(CurrentinvoCount));
				mAssert(Pdf_He_AgencyorApplicantName.get(CurrentinvoCount), He_AgencyorApplicantName.get(CurrentinvoCount),"Agency/Applicant Name in Hearing Letter dose not match with actual::"+Pdf_He_AgencyorApplicantName.get(CurrentinvoCount)+"Expected::"+He_AgencyorApplicantName.get(CurrentinvoCount));
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in adh_ShowCause_letter_assertion");
		}
	}
	 */


	public void adh_Loi_Receiptletter_ChequePayorder_assertion(){
		try{
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				ADH_Inspection_Letter_Pdf(pdfoutput);
				mAssert(Ins_Pdf_hoardingNo.get(CurrentinvoCount),Ins_hoardingNo.get(CurrentinvoCount),"Hoarding No in Inspection Letter dose not match with actual::"+Ins_Pdf_hoardingNo.get(CurrentinvoCount)+"Expected::"+Ins_hoardingNo.get(CurrentinvoCount));
				mAssert(Ins_Pdf_agencyName.get(CurrentinvoCount),Ins_agencyName.get(CurrentinvoCount),"Agency/Applicant name in Inspection Letter dose not match with actual::"+Ins_Pdf_agencyName.get(CurrentinvoCount)+"Expected::"+Ins_agencyName.get(CurrentinvoCount));
				mAssert(Ins_Pdf_ContractID.get(CurrentinvoCount),Ins_ContractID.get(CurrentinvoCount),"Contract No in Inspection Letter dose not match with actual::"+Ins_Pdf_ContractID.get(CurrentinvoCount)+"Expected::"+Ins_ContractID.get(CurrentinvoCount));
				mAssert(Ins_Pdf_Inspectiondate.get(CurrentinvoCount),Ins_Inspectiondate.get(CurrentinvoCount),"Contract No in Inspection Letter dose not match with actual::"+Ins_Pdf_Inspectiondate.get(CurrentinvoCount)+"Expected::"+Ins_Inspectiondate.get(CurrentinvoCount));
				mAssert(Ins_Pdf_InspectedBy.get(CurrentinvoCount),Ins_InspectedBy.get(CurrentinvoCount),"Inspected By employee in Inspection Letter dose not match with actual::"+Ins_Pdf_InspectedBy.get(CurrentinvoCount)+"Expected::"+Ins_InspectedBy.get(CurrentinvoCount));
				mAssert(Ins_Pdf_inpectionNo.get(CurrentinvoCount), Ins_inpectionNo.get(CurrentinvoCount),"Inspection No in Inspection Letter dose not match with actual::"+Ins_Pdf_inpectionNo.get(CurrentinvoCount)+"Expected::"+Ins_inpectionNo.get(CurrentinvoCount));
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();	
			throw new MainetCustomExceptions("Error in adh_Inspection_letter_assertion");
		}
	}



	// Search Criteria For Suspension of Contract Form
	public void addSuspensionProcessSearchCriteriaGeneration() {
		try {
			//Search by Hearing No
			if(mGetPropertyFromFile("adh_SoASearchHearingNoYNData").equalsIgnoreCase("Yes")){
				mCustomWait(300);
				mSendKeys("id",mGetPropertyFromFile("adh_SoAsearchHearingNoid"),mGetPropertyFromFile("adh_SoASearchHearingNoData"));
			}

			//Search by Contract No
			if (mGetPropertyFromFile("adh_SoASearchContractNoYNData").equalsIgnoreCase("Yes")) 
			{
				mSendKeys("id", mGetPropertyFromFile("adh_SoAsearchContractNoid"),mGetPropertyFromFile("adh_SoASearchContractNoData"));
			}

			//Search by Agency/ Application  No
			if (mGetPropertyFromFile("adh_SoASearchAgencyApplicationNoYNData").equalsIgnoreCase("Yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_SoAsearchApplicationNoid"),mGetPropertyFromFile("adh_SoASearchAgencyApplicationNoData"));

			}
			//Search by Agency/ Application Name
			if (mGetPropertyFromFile("adh_SoASearchAgencyApplicationNameYNData").equalsIgnoreCase("Yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_SoAsearchApplicantNameoid"),mGetPropertyFromFile("adh_SoASearchAgencyApplicationNameData"));

			}


		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in addSuspensionProcessSearchCriteriaGeneration Search Criteria");
		}

	}

	// Search Criteria For Suspension of Contract letter Printing 
	public void addSuspensionProcessSearchCriteriaLetterGeneration() {
		try {
			//Search by Suspension No
			if(mGetPropertyFromFile("adh_SoALetterearchSuspensionNoYNData").equalsIgnoreCase("Yes")){
				mCustomWait(300);
				mSendKeys("id",mGetPropertyFromFile("adh_SoALetterSuspensionNoid"),mGetPropertyFromFile("adh_SoASearchSuspensionNoData"));
			}

			//Search by Contract No
			if (mGetPropertyFromFile("adh_SoALetterSearchContractNoYNData").equalsIgnoreCase("Yes")) 
			{
				mSendKeys("id", mGetPropertyFromFile("adh_SoALetterContractNoid"),mGetPropertyFromFile("adh_SoASearchContractNoData"));
			}

			//Search by Agency/ Application  No
			if (mGetPropertyFromFile("adh_SoALetterSearchAgencyApplicationNoYNData").equalsIgnoreCase("Yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_SoALetterAgencyNoid"),mGetPropertyFromFile("adh_SoASearchAgencyApplicationNoData"));

			}
			//Search by Agency/ Application Name
			if (mGetPropertyFromFile("adh_SoALetterSearchAgencyApplicationNameYNData").equalsIgnoreCase("Yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_SoALetterAgencyNameid"),mGetPropertyFromFile("adh_SoASearchAgencyApplicationNameData"));

			}


		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in addSuspensionProcessSearchCriteriaLetterGeneration Search Criteria");
		}

	}

	// Search Criteria For Cancellation of Contract Form
	public void addCancellationProcessSearchCriteriaGeneration() {
		try {
			//Search by Hearing No
			if(mGetPropertyFromFile("adh_CoASearchHearingNoYNData").equalsIgnoreCase("Yes")){
				mCustomWait(300);
				mSendKeys("id",mGetPropertyFromFile("adh_CoAsearchHearingNoid"),mGetPropertyFromFile("adh_CoASearchHearingNoData"));
			}

			//Search by Contract No
			if (mGetPropertyFromFile("adh_CoASearchContractNoYNData").equalsIgnoreCase("Yes")) 
			{
				mSendKeys("id", mGetPropertyFromFile("adh_CoAsearchContractNoid"),mGetPropertyFromFile("adh_CoASearchContractNoData"));
			}

			//Search by Agency/ Application  No
			if (mGetPropertyFromFile("adh_CoASearchAgencyApplicationNoYNData").equalsIgnoreCase("Yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_CoAsearchApplicationNoid"),mGetPropertyFromFile("adh_CoASearchAgencyApplicationNoData"));
			}


			//Search by Agency/ Application Name
			if (mGetPropertyFromFile("adh_CoASearchAgencyApplicationNameYNData").equalsIgnoreCase("Yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_CoAsearchApplicantNameoid"),mGetPropertyFromFile("adh_CoASearchAgencyApplicationNameData"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in addCancellationProcessSearchCriteriaGeneration Search Criteria");
		}

	}

	// Search Criteria For Cancellation of Contract letter Printing 
	public void addCancellationSearchCriteriaLetterGeneration() {
		try {
			//Search by Suspension No
			if(mGetPropertyFromFile("adh_CoALetterearchCancellationNoYNData").equalsIgnoreCase("Yes")){
				mCustomWait(300);
				mSendKeys("id",mGetPropertyFromFile("adh_CoALetterCancellationNoid"),mGetPropertyFromFile("adh_CoASearchCancellationNoData"));
			}

			//Search by Contract No
			if (mGetPropertyFromFile("adh_CoALetterSearchContractNoYNData").equalsIgnoreCase("Yes")) 
			{
				mSendKeys("id", mGetPropertyFromFile("adh_CoALetterContractNoid"),mGetPropertyFromFile("adh_CoASearchContractNoData"));
			}

			//Search by Agency/ Application  No
			if (mGetPropertyFromFile("adh_CoALetterSearchAgencyApplicationNoYNData").equalsIgnoreCase("Yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_CoALetterAgencyAppNoid"),mGetPropertyFromFile("adh_CoASearchAgencyApplicationNoData"));

			}
			//Search by Agency/ Application Name
			if (mGetPropertyFromFile("adh_CoALetterSearchAgencyApplicationNameYNData").equalsIgnoreCase("Yes")) 
			{   mCustomWait(300);
			mSendKeys("id",mGetPropertyFromFile("adh_CoALetterAgencyAppNameid"),mGetPropertyFromFile("adh_CoASearchAgencyApplicationNameData"));

			}

		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in addSuspensionProcessSearchCriteriaLetterGeneration Search Criteria");
		}

	}





	//ADH Inspection Entry to be clear
	public  void  Tblread_adh_add_inspectionClearDetails()
	{
		try {
			for(int k=1;k<rwcount;k++){

				mWaitForVisible("xpath",mGetPropertyFromFile("adh_addInspectDetailsID"));
				mClick("xpath", mGetPropertyFromFile("adh_addInspectDetailsID"));
				mCustomWait(1000);

				driver.findElement(By.xpath("//*[@id=\"atcid\"]/table/tbody/tr["+(k+1)+"]/td[3]/input[1]")).click();


				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("adh_InpectionRemrksId"),mGetPropertyFromFile("adh_InpectionRemarksData"));
				String Inspectionremarks=mGetText("id", mGetPropertyFromFile("adh_InpectionRemrksId"), "value");
				System.out.println("Inspection rtemarks is"+Inspectionremarks);
				Ins_InspectionERemark.add(Inspectionremarks);

				mWaitForVisible("id",mGetPropertyFromFile("adh_SelectInspectclearId"));
				mClick("id",mGetPropertyFromFile("adh_SelectInspectclearId"));
				mTakeScreenShot();

				String InspectionStatus=mGetText("id", mGetPropertyFromFile("adh_SelectInspectStatusId"), "value");
				System.out.println("Inspection rtemarks is"+InspectionStatus);
				Ins_Status.add(InspectionStatus);



				mWaitForVisible("xpath",mGetPropertyFromFile("adh_TermSubmitxpath"));
				mClick("xpath", mGetPropertyFromFile("adh_TermSubmitxpath"));

				mWaitForVisible("xpath",mGetPropertyFromFile("adh_InspectiondetailsAddAssertmsgId"));
				String InspectionAddMsg=mGetText("css", mGetPropertyFromFile("adh_InspectiondetailsAddAssertmsgId"));
				System.out.println("Inspection rtemarks is"+InspectionAddMsg);
				mAssert(InspectionAddMsg, mGetPropertyFromFile("adh_InpectionDeatilsAssertmsgData"), "Inspection Add details Failed");

				mWaitForVisible("css",mGetPropertyFromFile("adh_CloseCss"));
				mClick("css", mGetPropertyFromFile("adh_CloseCss"));
				mCustomWait(2000);

			}
		}
		catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in Tblread_RNL_Scrutiny_Booking_Details method");
		}		

	}



	//ADH Inspection Entry to be Not clear
	public  void  Tblread_adh_add_inspectionnotClearDetails()
	{
		try {
			for(int k=1;k<rwcount;k++){

				mWaitForVisible("xpath",mGetPropertyFromFile("adh_addInspectDetailsID"));
				mClick("xpath", mGetPropertyFromFile("adh_addInspectDetailsID"));
				mCustomWait(1000);

				driver.findElement(By.xpath("//*[@id=\"atcid\"]/table/tbody/tr["+(k+1)+"]/td[3]/input[1]")).click();


				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("adh_InpectionRemrksId"),mGetPropertyFromFile("adh_InpectionRemarksData"));
				String Inspectionremarks=mGetText("id", mGetPropertyFromFile("adh_InpectionRemrksId"), "value");
				System.out.println("Inspection rtemarks is"+Inspectionremarks);
				Ins_InspectionERemark.add(Inspectionremarks);

				if(k<rwcount-1){

					mWaitForVisible("id",mGetPropertyFromFile("adh_SelectInspectclearId"));
					mClick("id",mGetPropertyFromFile("adh_SelectInspectclearId"));
					mTakeScreenShot();
				}
				else {

				}


				mTakeScreenShot();

				String InspectionStatus=mGetText("id", mGetPropertyFromFile("adh_SelectInspectStatusId"), "value");
				System.out.println("Inspection rtemarks is"+InspectionStatus);
				Ins_Status.add(InspectionStatus);

				mWaitForVisible("xpath",mGetPropertyFromFile("adh_TermSubmitxpath"));
				mClick("xpath", mGetPropertyFromFile("adh_TermSubmitxpath"));

				mWaitForVisible("xpath",mGetPropertyFromFile("adh_InspectiondetailsAddAssertmsgId"));
				String InspectionAddMsg=mGetText("css", mGetPropertyFromFile("adh_InspectiondetailsAddAssertmsgId"));
				System.out.println("Inspection rtemarks is"+InspectionAddMsg);
				mAssert(InspectionAddMsg, mGetPropertyFromFile("adh_InpectionDeatilsAssertmsgData"), "Inspection Add details Failed");

				mWaitForVisible("css",mGetPropertyFromFile("adh_CloseCss"));
				mClick("css", mGetPropertyFromFile("adh_CloseCss"));
				mCustomWait(2000);

			}

		}
		catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in Tblread_adh_add_inspectionnotClearDetails method");
		}		

	}


	/*public void adh_Booking_Of_Hoarding_ScrutinyView_Assertion()
	{
		try {

			ScrutinyServiceName = driver.findElement(By.cssSelector("#middle_right > h1")).getText();
			System.out.println("Service Name in Scrutiny view form is"+ScrutinyServiceName);

			// Capturing Applicant Title and Assertion
			String scr_adhApptitle=mCaptureSelectedValue("id", mGetPropertyFromFile("adh_BookingOfHoardingTitleID"));
			System.out.println("Applicant Title is in Scutiny : "+scr_adhApptitle);
			BH_Scr_adhApptitle.add(scr_adhApptitle);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){

				mAssert(adh_ApplicantTitle.get(CurrentinvoCount), BH_Scr_adhApptitle.get(CurrentinvoCount), "Applicant Title does not match with the actual::"+BH_Scr_adhApptitle.get((CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_ApplicantTitle.get(CurrentinvoCount));
			    //System.out.println("Captured Applicant Title arraylist is in Scutiny ::: "+BH_Scr_adhApptitle.get(chekindexes.get(CurrentinvoCount))+" "+",Expected from Application form is"+adh_ApplicantTitle.get(chekindexes.get(CurrentinvoCount)));
			}

			// Capturing Applicant First Name and Assertion
			String scr_adhHoardingFname=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingFnameID"),"value");
			System.out.println("Applicant First Name is in Scutiny : "+scr_adhHoardingFname);
			BH_scr_adhHoardingFname.add(scr_adhHoardingFname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Applicant First Name arraylist is in Scutiny ::: "+BH_scr_adhHoardingFname.get(chekindexes.get(CurrentinvoCount))+" "+",Expected from Application form is"+adh_ApplicantFname.get(chekindexes.get(CurrentinvoCount)));
				mAssert(adh_ApplicantFname.get(chekindexes.get(CurrentinvoCount)), BH_scr_adhHoardingFname.get(chekindexes.get(CurrentinvoCount)), "Applicant First Name does not match with the actual::"+BH_scr_adhHoardingFname.get(chekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_ApplicantFname.get(chekindexes.get(CurrentinvoCount)));
			}

			// Capturing Applicant Middle Name and Assertion
			String scr_adhHoardingMname=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingMidnameID"),"value");
			System.out.println("Applicant Middle Name is in Scutiny : "+scr_adhHoardingMname);
			BH_scr_adhHoardingMname.add(scr_adhHoardingMname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Applicant Middle Name arraylist is in Scutiny ::: "+BH_scr_adhHoardingMname.get(chekindexes.get(CurrentinvoCount))+" "+",Expected from Application form is"+adh_ApplicantMname.get(chekindexes.get(CurrentinvoCount)));
				mAssert(adh_ApplicantMname.get(chekindexes.get(CurrentinvoCount)), BH_scr_adhHoardingMname.get(chekindexes.get(CurrentinvoCount)), "Applicant  Middle Name does not match with the actual::"+BH_scr_adhHoardingMname.get(chekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_ApplicantMname.get(chekindexes.get(CurrentinvoCount)));
			}

			// Capturing Applicant Last Name and Assertion
			String scr_adhHoardingLname=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingLnameID"),"value");
			System.out.println("Applicant Last Name is in Scutiny : "+scr_adhHoardingLname);
			BH_scr_adhHoardingLname.add(scr_adhHoardingLname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Applicant Last Name arraylist is in Scutiny ::: "+BH_scr_adhHoardingLname.get(chekindexes.get(CurrentinvoCount))+" "+",Expected from Application form is"+adh_ApplicantLname.get(chekindexes.get(CurrentinvoCount)));
				mAssert(adh_ApplicantLname.get(chekindexes.get(CurrentinvoCount)), BH_scr_adhHoardingLname.get(chekindexes.get(CurrentinvoCount)), "Applicant Last Name does not match with the actual::"+BH_scr_adhHoardingLname.get(chekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_ApplicantLname.get(chekindexes.get(CurrentinvoCount)));
			}

			// Dyanamically creating  Applicant Full  Name and Assertion
			String scr_adhHoardingLFullname=scr_adhApptitle+" "+scr_adhHoardingFname+" "+scr_adhHoardingMname+" "+scr_adhHoardingLname;
			System.out.println("Applicant Full Name is in Scutiny : "+scr_adhHoardingLFullname);
			BH_scr_adhHoardingLFullname.add(scr_adhHoardingLFullname);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Applicant Full Name arraylist is in Scutiny ::: "+BH_scr_adhHoardingLFullname.get(chekindexes.get(CurrentinvoCount))+" "+",Expected from Application form is"+adh_ApplicantLname.get(chekindexes.get(CurrentinvoCount)));
				mAssert(adh_ApplicantFullname.get(chekindexes.get(CurrentinvoCount)), BH_scr_adhHoardingLFullname.get(chekindexes.get(CurrentinvoCount)), "Applicant Full Name does not match with the actual::"+BH_scr_adhHoardingLFullname.get(chekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_ApplicantFullname.get(chekindexes.get(CurrentinvoCount)));
			}

			// Capturing Applicant Mobile No and Assertion
			String scr_adhHoardingMobNo=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingMobNoID"),"value");
			System.out.println("Applicant Mobile No is in Scutiny : "+scr_adhHoardingMobNo);
			BH_scr_adhHoardingMobNo.add(scr_adhHoardingMobNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Applicant Mobile No arraylist is in Scutiny ::: "+BH_scr_adhHoardingMobNo.get(chekindexes.get(CurrentinvoCount))+" "+",Expected from Application form is"+adh_ApplicantMbNo.get(chekindexes.get(CurrentinvoCount)));
				mAssert(adh_ApplicantMbNo.get(chekindexes.get(CurrentinvoCount)), BH_scr_adhHoardingMobNo.get(chekindexes.get(CurrentinvoCount)), "Applicant Mobile No does not match with the actual::"+BH_scr_adhHoardingMobNo.get(chekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_ApplicantMbNo.get(chekindexes.get(CurrentinvoCount)));
			}

			// Capturing Applicant Email Id and Assertion
			String scr_adhHoardingEmail=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingEmailID"),"value");
			System.out.println("Applicant Email Id is in Scutiny : "+scr_adhHoardingEmail);
			BH_scr_adhHoardingEmail.add(scr_adhHoardingEmail);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Applicant Email Id arraylist is in Scutiny ::: "+BH_scr_adhHoardingEmail.get(chekindexes.get(CurrentinvoCount))+" "+",Expected from Application form is"+adh_ApplicantEmail.get(chekindexes.get(CurrentinvoCount)));
				mAssert(adh_ApplicantEmail.get(chekindexes.get(CurrentinvoCount)), BH_scr_adhHoardingEmail.get(chekindexes.get(CurrentinvoCount)), "Applicant Email Id does not match with the actual::"+BH_scr_adhHoardingEmail.get(chekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_ApplicantEmail.get(chekindexes.get(CurrentinvoCount)));
			}

			// Capturing Application No and Assertion
			String scr_adhHoardingApplicationNo=mGetText("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingRondOffAmtID"),"value");
			System.out.println("Applicant No is in Scutiny : "+scr_adhHoardingApplicationNo);
			BH_scr_adhHoardingApplicationNo.add(scr_adhHoardingApplicationNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Applicant No arraylist is in Scutiny ::: "+BH_scr_adhHoardingApplicationNo.get(chekindexes.get(CurrentinvoCount))+" "+",Expected from Application form is"+applicationNo.get(chekindexes.get(CurrentinvoCount)));
				mAssert(applicationNo.get(chekindexes.get(CurrentinvoCount)), BH_scr_adhHoardingApplicationNo.get(chekindexes.get(CurrentinvoCount)), "Applicantation No of Booking of Hoarding  does not match with the actual::"+BH_scr_adhHoardingApplicationNo.get(chekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+applicationNo.get(chekindexes.get(CurrentinvoCount)));
			}

			// Capturing Hoarding from date and Assertion
			String scr_adhHoardingFromDate=mGetText("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingFromDateID"),"value");
			System.out.println("Applicant Hoarding Booking From date is in Scutiny : "+scr_adhHoardingFromDate);
			BH_scr_adhHoardingFromDate.add(scr_adhHoardingFromDate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Hoarding Booking From date arraylist is in Scutiny ::: "+BH_scr_adhHoardingFromDate.get(chekindexes.get(CurrentinvoCount))+" "+",Expected from Application form is"+adh_BookingfromDate.get(chekindexes.get(CurrentinvoCount)));
				mAssert(adh_BookingfromDate.get(chekindexes.get(CurrentinvoCount)), BH_scr_adhHoardingFromDate.get(chekindexes.get(CurrentinvoCount)), "Applicant Hoarding Booking From does not match with the actual::"+BH_scr_adhHoardingFromDate.get(chekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_BookingtoDate.get(chekindexes.get(CurrentinvoCount)));
			}

			// Capturing Hoarding To date and Assertion
			String scr_adhHoardingToDate=mGetText("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingToDateID"),"value");
			BH_scr_adhHoardingToDate.add(scr_adhHoardingToDate);
			System.out.println("Applicant Hoarding Booking To Date is in Scutiny : "+scr_adhHoardingToDate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Hoarding Booking To Date arraylist is in Scutiny ::: "+BH_scr_adhHoardingToDate.get(chekindexes.get(CurrentinvoCount))+" "+",Expected from Application form is"+adh_BookingtoDate.get(chekindexes.get(CurrentinvoCount)));
				mAssert(adh_BookingtoDate, BH_scr_adhHoardingToDate, "Applicant Hoarding Booking to does not match with the actual::"+BH_scr_adhHoardingToDate.get(chekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_BookingtoDate.get(chekindexes.get(CurrentinvoCount)));
			}
			// Capturing Hoarding Remarks and Assertion
			String scr_adhHoardingRemarks=mGetText("id", mGetPropertyFromFile("adh_BookingOfHoardingRemarksID"),"value");
			System.out.println("Applicant Remarks is in Scutiny : "+scr_adhHoardingRemarks);
			BH_scr_adhHoardingRemarks.add(scr_adhHoardingRemarks);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Applicant Last Name arraylist is in Scutiny ::: "+BH_scr_adhHoardingRemarks.get(chekindexes.get(CurrentinvoCount))+" "+",Expected from Application form is"+adh_ApplicantRemarks.get(chekindexes.get(CurrentinvoCount)));
				mAssert(adh_ApplicantRemarks.get(chekindexes.get(CurrentinvoCount)), BH_scr_adhHoardingRemarks.get(chekindexes.get(CurrentinvoCount)), "Applicant Booking of Hoarding Remarks does not match with the actual::"+BH_scr_adhHoardingRemarks.get(chekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_ApplicantRemarks.get(chekindexes.get(CurrentinvoCount)));
			}
			// Capturing Contract Amount and Assertion
			String scr_adhHoardingContractAmt=mGetText("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingContractAmtID"),"value");
			System.out.println("Booking Of Hoarding Contract Amount is in Scutiny : "+scr_adhHoardingContractAmt);
			BH_scr_adhHoardingContractAmt.add(scr_adhHoardingContractAmt);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Applicant Last Name arraylist is in Scutiny ::: "+BH_scr_adhHoardingRemarks.get(chekindexes.get(CurrentinvoCount))+" "+",Expected from Application form is"+adh_ApplicantRemarks.get(chekindexes.get(CurrentinvoCount)));
				mAssert(adh_ApplicantRemarks.get(chekindexes.get(CurrentinvoCount)), BH_scr_adhHoardingRemarks.get(chekindexes.get(CurrentinvoCount)), "Applicant Booking of Hoarding Remarks does not match with the actual::"+BH_scr_adhHoardingRemarks.get(chekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_ApplicantRemarks.get(chekindexes.get(CurrentinvoCount)));
			}

			// Capturing Discount Amount and Assertion
			String scr_adhHoardingDiscountAmt=mGetText("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingDiscountAmtID"),"value");
			System.out.println("Booking Of Hoarding Discount Amount is in Scutiny : "+scr_adhHoardingDiscountAmt);
			BH_scr_adhHoardingDiscountAmt.add(scr_adhHoardingDiscountAmt);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Applicant Last Name arraylist is in Scutiny ::: "+BH_scr_adhHoardingRemarks.get(chekindexes.get(CurrentinvoCount))+" "+",Expected from Application form is"+adh_ApplicantRemarks.get(chekindexes.get(CurrentinvoCount)));
				mAssert(adh_ApplicantRemarks.get(chekindexes.get(CurrentinvoCount)), BH_scr_adhHoardingRemarks.get(chekindexes.get(CurrentinvoCount)), "Applicant Booking of Hoarding Remarks does not match with the actual::"+BH_scr_adhHoardingRemarks.get(chekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_ApplicantRemarks.get(chekindexes.get(CurrentinvoCount)));
			}

			// Capturing Payble Amount and Assertion
			String scr_adhHoardingPaybleAmt=mGetText("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingPaybleAmtID"),"value");
			System.out.println("Booking Of Hoarding Payble Amount is in Scutiny : "+scr_adhHoardingPaybleAmt);
			BH_scr_adhHoardingPaybleAmt.add(scr_adhHoardingPaybleAmt);

			// Capturing Payble Amount and Assertion
			String Payable=mGetText("id",mGetPropertyFromFile("adh_Scr_BookingOfHoardingPaybleAmtID"),"value");
			Acg_PayableAmount.add(Payable);

			// Capturing Rounding off  Amount and Assertion
			String scr_adhHoardingRondOffAmt=mGetText("id", mGetPropertyFromFile("adh_Scr_BookingOfHoardingRondOffAmtID"),"value");
			System.out.println("Booking Of Hoarding RoundOff Amount is in Scutiny : "+scr_adhHoardingRondOffAmt);
			BH_scr_adhHoardingRoundOffAmt.add(scr_adhHoardingRondOffAmt);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Captured Applicant Last Name arraylist is in Scutiny ::: "+BH_scr_adhHoardingRemarks.get(chekindexes.get(CurrentinvoCount))+" "+",Expected from Application form is"+adh_ApplicantRemarks.get(chekindexes.get(CurrentinvoCount)));
				mAssert(adh_ApplicantRemarks.get(chekindexes.get(CurrentinvoCount)), BH_scr_adhHoardingRemarks.get(chekindexes.get(CurrentinvoCount)), "Applicant Booking of Hoarding Remarks does not match with the actual::"+BH_scr_adhHoardingRemarks.get(chekindexes.get(CurrentinvoCount))+"in scrutiny View form,Expected::"+adh_ApplicantRemarks.get(chekindexes.get(CurrentinvoCount)));
			}

			mCustomWait(1000);
			//Reading Table Booking Details at scrutiny view form
			Tblread_adh_Scrutiny_Booking_Details();
			mCustomWait(1000);
		}

		catch (Exception e) 
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in adh_ScrutinyView_Assertion method");
		}
	}
	 */



}




