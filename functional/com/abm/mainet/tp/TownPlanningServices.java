package com.abm.mainet.tp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.google.common.base.Throwables;

//import sun.security.krb5.ServiceName;
import api.CommonUtilsAPI;
import api.PdfPatterns;
import clubbedapi.ClubbedAPI;
import common.CommonMethods;
import common.ScrutinyProcess;
import errorhandling.MainetCustomExceptions;
import excelreader.ExcelReader;
import excelreader.ExcelToProperties;


//@Listeners(CustomListener.class)
public class TownPlanningServices  extends CommonMethods
{
	String LOIDate;
	String LOINumber;
	String CaseNumber;
	String ApplicationDate;
	String VisitDatestr;
	String VisitDate;
	String strDate;
	public static String scrutinyLOInum;
	public static String TpServiceName;
	public static String clv_Service_Name;
	public static String LoiNum;
	public static int jj=0;
	ClubbedAPI ClubbedUtils = new ClubbedAPI();
	PdfPatterns pdfpattern= new PdfPatterns();
	Date myDate = new Date();

	// object for scrutiny method
	ScrutinyProcess scrutinyGenericMethod = new ScrutinyProcess ();


	//public TownPlanningServices(){}
	static CommonMethods common =new CommonMethods();
	ExcelToProperties excelToProp = new ExcelToProperties();
	ExcelReader ER = new ExcelReader();
	//common.ScrutinyProcess scrutiny =new common.ScrutinyProcess();

	@BeforeSuite
	public void beforeSuite(){
		System.out.println("Starting before suite");
		thisClassName=this.getClass().getCanonicalName();
		myClassName = thisClassName;
		System.out.println("This is the current class name: "+myClassName );
		mCreateModuleFolder("TP_",myClassName);
		tpandrnl="true";
	}

	//1.Application For landDevelopment
	@Test(enabled=false)
	public void tp_ApplicationForLandDevp(){

		try{
			GetCurrentdate();
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			TPCustomErrorMessages.tp_m_errors.entrySet().clear();
			mCreateArtefactsFolder("TP_");	
			GetCurrentdate();
			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				applicationNo.clear();	
				ApplicationNoforLandDev.clear();
			}
			applicationForLandDevp();
			CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_ApplicationForLandDevp method");
		}
	}

	//2.CheckList Verification
	@Test(enabled=false)
	public void tp_ChecklistVerification(){
		try{
			GetCurrentdate();
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();  
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			TPCustomErrorMessages.tp_m_errors.entrySet().clear();
			mCreateArtefactsFolder("TP_");
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
			
			checklistVerification();
			CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		catch(Exception e){
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_ChecklistVerification method");
		}
	}

	//3.Scrutiny process
	//truti patra generation
	@Test(enabled=false)
	public void tp_ScrutinyProcess() throws InterruptedException, IOException{
		try{
			GetCurrentdate();
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			TPCustomErrorMessages.tp_m_errors.entrySet().clear();
			mCreateArtefactsFolder("TP_");
			/*if(!mGetPropertyFromFile("wantToRejectAtChecklist").equalsIgnoreCase("No")){
				System.out.println("Method is skipped due to rejected");

			}else{*/

			if (CurrentinvoCount==0)
					{
						//Application No List CLear for End to End Execution
						scrutinyindexes.clear();	
					}
			
			
			if (!(chekindexes.contains(CurrentinvoCount))) {
				

				if (mGetPropertyFromFile("tp_LOIGenFinalDecisiondata").equalsIgnoreCase("no")) {
					scrutinyindexes.add(CurrentinvoCount);
					System.out.println("scrutinyindexes==>"+scrutinyindexes);
				}
				
				
				
				
				for(scrutinylevelcounter=1;scrutinylevelcounter<=Integer.parseInt(mGetPropertyFromFile("noOfScrutinyLevels"));scrutinylevelcounter++)
				{
					if(TrutiPatraGenerated  && !HearingDated)
					{
						scrutinylevelcounter--;
						scrutinyGenericMethod.ScrutinyProcess();
						System.out.println( "TrutiPatraGenerated value is :: "+TrutiPatraGenerated);
					}
					else
					{
						if(scrutinylevelcounter==2  && !HearingDated)
						{				
							System.out.println( "TrutiPatraGenerated value is :: "+TrutiPatraGenerated);
							scrutinyGenericMethod.ScrutinyProcess();
						}
						else
						{
							scrutinyGenericMethod.ScrutinyProcess();
						}
					}
				}
			}

			CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		catch(Exception e){
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_ScrutinyProcess method");
		}
	}

	//4.print counter
	//printing application
	@Test(enabled=false)
	public void tp_PrinterCounter() throws InterruptedException, IOException{
		try{
			GetCurrentdate();
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			TPCustomErrorMessages.tp_m_errors.entrySet().clear();
			if (!(chekindexes.contains(CurrentinvoCount))) {
				printerCounter();
			}

			CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		catch(Exception e){
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_PrinterCounter method");
		}

	}

	//5.LOI Payment
	//Loi payment and its verification
	@Test(enabled=false)
	public void tp_LoiPayment() throws InterruptedException, IOException{
		try{
			GetCurrentdate();
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			TPCustomErrorMessages.tp_m_errors.entrySet().clear();
			/*if(!mGetPropertyFromFile("wantToRejectAtScrutiny").equalsIgnoreCase("No") || !mGetPropertyFromFile("wantToRejectAtChecklist").equalsIgnoreCase("No")){
				System.out.println("Method is skipped due to rejected");
			}else{*/
			if (!(chekindexes.contains(CurrentinvoCount))) {
				if (!(scrutinyindexes.contains(CurrentinvoCount))) {
				
				loiPayment();
			}
			}
			CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		catch(Exception e){
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_LoiPayment method");
		}
	}

	//6.Application Approval
	//Application Approval and printing
	@Test(enabled=false)
	public void tp_ApplicationApproval() throws InterruptedException, IOException{
		try{
			GetCurrentdate();
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			TPCustomErrorMessages.tp_m_errors.entrySet().clear();
			/*if(!mGetPropertyFromFile("wantToRejectAtScrutiny").equalsIgnoreCase("No") || !mGetPropertyFromFile("wantToRejectAtChecklist").equalsIgnoreCase("No")){
				System.out.println("Method is skipped due to rejected");
			}else{*/
			if (!(chekindexes.contains(CurrentinvoCount))) {
				if (!(scrutinyindexes.contains(CurrentinvoCount))) {
				applicationApproval();
				Land_dev_Approval_Assertion();
			}
			}
			CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_ApplicationApproval method");
		}
	}

	//7.Application for building permission
	@Test(enabled=false)
	public void tp_ApplctnForBuildinPermisn() throws InterruptedException, IOException{
		try{
			GetCurrentdate();
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			TPCustomErrorMessages.tp_m_errors.entrySet().clear();
			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				applicationNo.clear();	
				ApplicationNoforLandDev.clear();
			}
			applctnForBuildinPermisn();
			CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_ApplctnForBuildinPermisn method");
		}		
	}

	//8.Checklist verification of building permission
	@Test(enabled=false)
	public void tp_BuildingPermissionChcklstVerifction() throws InterruptedException, IOException{
		try{
			GetCurrentdate();
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			TPCustomErrorMessages.tp_m_errors.entrySet().clear();
			mCreateArtefactsFolder("TP_");
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

		checklistVerification();
			CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_BuildingPermissionChcklstVerifction method");
		}	
	}

	//9.Scrutiny process for building permission
	@Test(enabled=false)
	public void tp_BuildingPermissionScrutinyProcess() throws InterruptedException, IOException{
		try{
			GetCurrentdate();
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			TPCustomErrorMessages.tp_m_errors.entrySet().clear();
			mCreateArtefactsFolder("TP_");
			/*if(!mGetPropertyFromFile("wantToRejectAtChecklist").equalsIgnoreCase("No")){
				System.out.println("Method is skipped due to rejected");
			}else
			{*/
			
			if (CurrentinvoCount==0)
			{
				//Application No List CLear for End to End Execution
				scrutinyindexes.clear();	
			}		
			
			if (!(chekindexes.contains(CurrentinvoCount))) {
				
				
				if (mGetPropertyFromFile("tp_LOIGenFinalDecisiondata").equalsIgnoreCase("no")) {
					scrutinyindexes.add(CurrentinvoCount);
					System.out.println("scrutinyindexes==>"+scrutinyindexes);
				}
				
				for(scrutinylevelcounter=1;scrutinylevelcounter<=Integer.parseInt(mGetPropertyFromFile("noOfScrutinyLevels"));scrutinylevelcounter++){
					if(TrutiPatraGenerated  && !HearingDated)
					{
						scrutinylevelcounter--;
						scrutinyGenericMethod.ScrutinyProcess();
						System.out.println( "TrutiPatraGenerated value is :: "+TrutiPatraGenerated);
					}
					else
					{
						if(scrutinylevelcounter==2  && !HearingDated)
						{				
							System.out.println( "TrutiPatraGenerated value is :: "+TrutiPatraGenerated);
							scrutinyGenericMethod.ScrutinyProcess();
						}
						else
						{
							scrutinyGenericMethod.ScrutinyProcess();
						}
					}
				}
			}

			CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_BuildingPermissionScrutinyProcess method");
		}	
	}

	//10.Printer counter after Building permission
	@Test(enabled=false)
	public void tp_BuildingPermissionPrinterCounter() throws InterruptedException, IOException{
		try{GetCurrentdate();
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		TPCustomErrorMessages.tp_m_errors.entrySet().clear();
		/*if(!mGetPropertyFromFile("wantToRejectAtChecklist").equalsIgnoreCase("No")){
			System.out.println("Method is skipped due to rejected");
		}else{*/

		if (!(chekindexes.contains(CurrentinvoCount))) {
		printerCounter();
		}

		CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_BuildingPermissionPrinterCounter method");
		}	

	}

	//11.LOI Payment
	//Loi payment and its verification after Building permission
	@Test(enabled=false)
	public void tp_BuildingPermissionLoiPayment() throws InterruptedException, IOException{
		try{GetCurrentdate();
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		TPCustomErrorMessages.tp_m_errors.entrySet().clear();
		/*if(!mGetPropertyFromFile("wantToRejectAtScrutiny").equalsIgnoreCase("No") || !mGetPropertyFromFile("wantToRejectAtChecklist").equalsIgnoreCase("No")){
			System.out.println("Method is skipped due to rejected");
		}else{*/
		

if (!(chekindexes.contains(CurrentinvoCount))) {
				if(!(scrutinyindexes.contains(CurrentinvoCount))) {
		
		
			loiPayment();
		}
}
		CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_BuildingPermissionLoiPayment method");
		}

	}

	//12.Application Approval
	//Application Approval after building permission
	@Test(enabled=false)
	public void tp_BuildingPermissionApplicationApproval() throws InterruptedException, IOException{
		try{GetCurrentdate();
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		TPCustomErrorMessages.tp_m_errors.entrySet().clear();
/*		if(!mGetPropertyFromFile("wantToRejectAtScrutiny").equalsIgnoreCase("No") || !mGetPropertyFromFile("wantToRejectAtChecklist").equalsIgnoreCase("No")){
			System.out.println("Method is skipped due to rejected");
		}else{*/
		if (!(chekindexes.contains(CurrentinvoCount))) {
			if(!(scrutinyindexes.contains(CurrentinvoCount))) {	
		applicationApproval();
			Building_Permit_Assertion();
		}
		}
		CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_BuildingPermissionApplicationApproval method");
		}
	}

	//13.Occupancy Certificate
	@Test(enabled=false)
	public void tp_OccupancyCertificate() throws InterruptedException, IOException{
		try{GetCurrentdate();
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		TPCustomErrorMessages.tp_m_errors.entrySet().clear();
		if (CurrentinvoCount==0)
		{
			//Application No List CLear for End to End Execution
			applicationNo.clear();	
			ApplicationNoforLandDev.clear();
		}
		occupancyCertificate();
		CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_OccupancyCertificate method");
		}
	}

	//14.Checklist verification of Occupancy Certificate
	@Test(enabled=false)
	public void tp_OccupancyCertificateChcklstVerifction() throws InterruptedException, IOException{
		try{GetCurrentdate();
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		TPCustomErrorMessages.tp_m_errors.entrySet().clear();
		mCreateArtefactsFolder("TP_");
		
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
		
		
		
		
		checklistVerification();
		CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_OccupancyCertificateChcklstVerifction method");
		}
	}

	//15.Scrutiny process for Occupancy Certificate
	@Test(enabled=false)
	public void tp_OccupancyCertificateScrutinyProcess() throws InterruptedException, IOException{
		try{GetCurrentdate();
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		TPCustomErrorMessages.tp_m_errors.entrySet().clear();
		mCreateArtefactsFolder("TP_");

		if (CurrentinvoCount==0)
				{
					//Application No List CLear for End to End Execution
					scrutinyindexes.clear();	
				}

		
		
		
	/*	if(!mGetPropertyFromFile("wantToRejectAtChecklist").equalsIgnoreCase("No")){
			System.out.println("Method is skipped due to rejected");
		}else{*/
		if (!(chekindexes.contains(CurrentinvoCount))) {
			

			if (mGetPropertyFromFile("tp_LOIGenFinalDecisiondata").equalsIgnoreCase("no")) {
				scrutinyindexes.add(CurrentinvoCount);
				System.out.println("scrutinyindexes==>"+scrutinyindexes);
			}
			
			for(scrutinylevelcounter=1;scrutinylevelcounter<=Integer.parseInt(mGetPropertyFromFile("noOfScrutinyLevels"));scrutinylevelcounter++)
			{
				if(TrutiPatraGenerated  && !HearingDated)
				{
					scrutinylevelcounter--;
					scrutinyGenericMethod.ScrutinyProcess();
					System.out.println( "TrutiPatraGenerated value is :: "+TrutiPatraGenerated);
				}
				else
				{
					if(scrutinylevelcounter==2  && !HearingDated)
					{				
						System.out.println( "TrutiPatraGenerated value is :: "+TrutiPatraGenerated);
						scrutinyGenericMethod.ScrutinyProcess();
					}
					else
					{
						scrutinyGenericMethod.ScrutinyProcess();
					}
				}
			}
		}
		CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_OccupancyCertificateScrutinyProcess method");
		}
	}

	//16.Printer counter after Occupancy Certificate
	@Test(enabled=false)
	public void tp_OccupancyCertificatePrinterCounter() {
		try{GetCurrentdate();
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		TPCustomErrorMessages.tp_m_errors.entrySet().clear();
		/*if(!mGetPropertyFromFile("wantToRejectAtChecklist").equalsIgnoreCase("No")){
			System.out.println("Method is skipped due to rejected");
		}else{*/
		if (!(chekindexes.contains(CurrentinvoCount))) {
			printerCounter();
		}
		CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_OccupancyCertificatePrinterCounter method");
		}
	}

	//17.LOI Payment
	//Loi payment and its verification Occupancy Certificate
	@Test(enabled=false)
	public void tp_OccupancyCertificateLoiPayment(){
		try{GetCurrentdate();
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		TPCustomErrorMessages.tp_m_errors.entrySet().clear();
		/*if(!mGetPropertyFromFile("wantToRejectAtScrutiny").equalsIgnoreCase("No") || !mGetPropertyFromFile("wantToRejectAtChecklist").equalsIgnoreCase("No")){
			System.out.println("Method is skipped due to rejected");
		}else{*/
		if (!(chekindexes.contains(CurrentinvoCount))) {
			if(!(scrutinyindexes.contains(CurrentinvoCount))) {
			loiPayment();
		}
		}
		CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_OccupancyCertificateLoiPayment method");
		}
	}

	//18.Application Approval
	//Application Approval after Occupancy Certificate
	@Test(enabled=false)
	public void tp_OccupancyCertificateApplicationApproval() {
		try{GetCurrentdate();
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		TPCustomErrorMessages.tp_m_errors.entrySet().clear();
		/*if(!mGetPropertyFromFile("wantToRejectAtScrutiny").equalsIgnoreCase("No") || !mGetPropertyFromFile("wantToRejectAtChecklist").equalsIgnoreCase("No")){
			System.out.println("Method is skipped due to rejected");
		}else{*/
		if (!(chekindexes.contains(CurrentinvoCount))) {
			if(!(scrutinyindexes.contains(CurrentinvoCount))) {
		
			applicationApproval();
			Occupancy_Certificate_Assertion();
		}
		}CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_OccupancyCertificateApplicationApproval method");
		}
	}

	//19.Cancellation of Building pemission

	@Test(enabled=false)
	public void tp_cancellationOfBuildingPermission() {
		try{GetCurrentdate();
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		TPCustomErrorMessages.tp_m_errors.entrySet().clear();
		CancellationBuildingPermission();
		CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_OccupancyCertificateApplicationApproval method");
		}
	}


	//20.Upload photographs & Periodic progress report
	@Test(enabled=false)
	public void tp_UploadphotographsAndndPeriodicProgress() {
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			TPCustomErrorMessages.tp_m_errors.entrySet().clear();
			UploadPhotographProgessReport();
			CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_UploadphotographsAndndPeriodicProgress method");
		}
	}

	//21.Verify Upload photographs & Periodic progress report
	@Test(enabled=false)
	public void tp_VerifyUploadphotographsAndndPeriodicProgress() {
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			TPCustomErrorMessages.tp_m_errors.entrySet().clear();
			VerifyUploadPhotographProgessReport();
			CommonUtilsAPI.tpErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in tp_VerifyUploadphotographsAndndPeriodicProgress method");
		}
	}


	//Application For landDevelopment
	public void applicationForLandDevp()
	{
		try
		{

			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserNameData"),mGetPropertyFromFile("deptPasswordData"));
			ApplicationForLandDevp();
			logOut();
			finalLogOut();
			/*common.isChallanVerftnRequired();*/
			mCloseBrowser();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in applicationForLandDevp method");
		}
	}

	//CheckList Verification
	public void checklistVerification(){
		try{
			mCustomWait(2000);
			ChecklistVerification(mGetPropertyFromFile("tp_MBADeptName"),mGetPropertyFromFile("tp_MBADeptPassword"));//Use your login key which should be in Common data sheet	
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in checklistVerification method");
		}

	}

	//Scrutiny Process
	public void scrutinyProcess()
	{
		try{
			mCreateArtefactsFolder("TP_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("tp_FirstScrutinyHeadName"),mGetPropertyFromFile("tp_FirstScrutinyPassword"));
			scrutinyGenericMethod.scrutinyProcess();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in scrutinyProcess method");
		}
	}


	//Printer Counter for LOI printing
	public void printerCounter(){
		try{
			mCreateArtefactsFolder("TP_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("tp_TPDPHeadName"),mGetPropertyFromFile("tp_TPDPHeadPassword"));
			cfcPrinterCounter();
			switch(TpServiceName)
			{
			case "Application for Land Development" :
				Land_Dev_TP_Intimation_Letter_PDF();

			case "Application for Building Permit" :
				Building_per_TP_Intimation_Letter_PDF();

			case "Occupancy Certificate" :
				Occupancy_Certificate_TP_Intimation_Letter_PDF();
			default: break;
			}

			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in printerCounter method");
		}
	}

	//LOI Payement by Citizen Login and Challan Verification by department login
	public void loiPayment(){
		try
		{
			mCreateArtefactsFolder("TP_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserNameData"),mGetPropertyFromFile("deptPasswordData"));
			paymentdata();
			LOIPayment();
			logOut();
			finalLogOut();
			isChallanVerftnRequired(mGetPropertyFromFile("tpCvFrTownPlaninServicesid"));
			switch(TpServiceName)
			{
			case "Application for Land Development" :
				ALD_TPLOIReceiptPDF();
				break;
			case "Occupancy Certificate" :
				OC_TPLOIReceiptPDF();
				break;
			case "Application for Building Permit" :
				ALD_TPLOIReceiptPDF();
				break;

			default: break;
			}

			mCloseBrowser();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in loiPayment method");
		}
	}

	//Application Approval
	public void applicationApproval(){
		try{
			mCreateArtefactsFolder("TP_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("tp_MBADeptName"), mGetPropertyFromFile("tp_MBADeptPassword"));
			ApplicationApproval();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in applicationApproval method");
		}
	}

	//Application for building permission
	public void applctnForBuildinPermisn()
	{
		try{
			mCreateArtefactsFolder("TP_");			
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserNameData"),mGetPropertyFromFile("deptPasswordData"));
			ApplctnForBuildinPermisn();
			logOut();
			finalLogOut();
			isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVreBuildingPermitServiceName"));


			mCloseBrowser();	
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in applctnForBuildinPermisn method");
		}

	}

	//Occupancy Certificate
	public void occupancyCertificate()
	{
		try
		{
			mCreateArtefactsFolder("TP_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserNameData"),mGetPropertyFromFile("deptPasswordData"));
			OccupancyCertificate();
			logOut();
			finalLogOut();
			mCloseBrowser();	
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in occupancyCertificate method");
		}
	}

	//Cancellation of Building Permit Method
	public void CancellationBuildingPermission()	{
		try{
			mCreateArtefactsFolder("TP_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("tp_MBADeptName"), mGetPropertyFromFile("tp_MBADeptPassword"));
			CancelBuildingPermission();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in applicationApproval method");
		}

	}

	//Upload Photograph and Periodic Progress Report Method
	public void UploadPhotographProgessReport()
	{
		try
		{
			mCreateArtefactsFolder("TP_");			
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserNameData"),mGetPropertyFromFile("deptPasswordData"));
			upphotoprogessrpt();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in applicationForLandDevp method");
		}
	}

	//Verify Upload Photograph and Periodic Progress Report Method
	public void VerifyUploadPhotographProgessReport()
	{
		try
		{
			mCreateArtefactsFolder("TP_");			
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserNameData"),mGetPropertyFromFile("deptPasswordData"));
			verifyupphotoprogessrpt();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in applicationForLandDevp method");
		}
	}


	// method for Application For Land Development
	public void ApplicationForLandDevp(){
		try
		{
			mCustomWait(500); 
			mNavigation("tp_buildinPermsnNRegultnLinkid", "tp_applctnForLandDevpLinkid");

			TpServiceName=mGetText("css", mGetPropertyFromFile("Servicenametextid"));
			ServiceNameforTownPlanning.add(TpServiceName.trim());
			System.out.println("Service name is" +ServiceNameforTownPlanning);

			//selecting applicant details
			mWaitForVisible("id", mGetPropertyFromFile("tp_ApplicantTitleid")); 
			mSelectDropDown("id", mGetPropertyFromFile("tp_ApplicantTitleid"), mGetPropertyFromFile("tp_ApplicantTitledata"));
			String tpApplicantTitle =mCaptureSelectedValue("id", mGetPropertyFromFile("tp_ApplicantTitleid"));
			mAssert(tpApplicantTitle, mGetPropertyFromFile("tp_ApplicantTitledata"), "Applicant Title dose not match");
			System.out.println(Applictitle.add(tpApplicantTitle));

			//InputTextReadWrite("id", mGetPropertyFromFile("tp_ApplicantFNameid"), mGetPropertyFromFile("tp_ApplicantFNamedata"));


			mSendKeys("id", mGetPropertyFromFile("tp_ApplicantFNameid"), mGetPropertyFromFile("tp_ApplicantFNamedata"));
			String tpApplicantFName=mGetText("id", mGetPropertyFromFile("tp_ApplicantFNameid"), "value");
			mAssert(tpApplicantFName, mGetPropertyFromFile("tp_ApplicantFNamedata"), "Applicant First Name dose not match");
			System.out.println(Applicfname.add(tpApplicantFName));


			mSendKeys("id", mGetPropertyFromFile("tp_ApplicantMNameid"), mGetPropertyFromFile("tp_ApplicantMNamedata"));
			String tpApplicantMName=mGetText("id", mGetPropertyFromFile("tp_ApplicantMNameid"), "value");
			mAssert(tpApplicantMName, mGetPropertyFromFile("tp_ApplicantMNamedata"), "Applicant Middle Name dose not match");
			System.out.println(Applicmname.add(tpApplicantMName));

			// Applicant Last name

			mSendKeys("id", mGetPropertyFromFile("tp_ApplicantLNameid"), mGetPropertyFromFile("tp_ApplicantLNamedata"));
			String tpApplicantLName=mGetText("id", mGetPropertyFromFile("tp_ApplicantLNameid"), "value");
			mAssert(tpApplicantLName, mGetPropertyFromFile("tp_ApplicantLNamedata"), "Applicant Last Name dose not match");
			System.out.println(Appliclname.add(tpApplicantLName));

			//Applicant full name with title
			String ApplicantFullnameApd=tpApplicantTitle+" "+tpApplicantFName+" "+tpApplicantMName+" "+tpApplicantLName;
			System.out.println(ApplicantFullname.add(ApplicantFullnameApd));

			//Applicant full name without title
			String ApplicantFullnameWdouttitle=tpApplicantFName+" "+tpApplicantMName+" "+tpApplicantLName;
			System.out.println(AppfullnmWdTitle.add(ApplicantFullnameWdouttitle));

			String  TP_appname=(mGetPropertyFromFile("tp_ApplicantTitledata")+" "+mGetPropertyFromFile("tp_ApplicantFNamedata")+" "+mGetPropertyFromFile("tp_ApplicantMNamedata")+" "+mGetPropertyFromFile("tp_ApplicantLNamedata"));
			AppName.add(TP_appname);
			System.out.println("Applicant Name - Arraylist Assertion Pass:::" +TP_appname);
			System.out.println("Applicant Name is "+TP_appname);

			mSendKeys("id", mGetPropertyFromFile("tp_ApplicantMobileNoid"), mGetPropertyFromFile("tp_ApplicantMobileNodata"));
			String tp_ApplicantMobileNo=mGetText("id", mGetPropertyFromFile("tp_ApplicantMobileNoid"), "value");
			System.out.println("The Applicant Mobile no:::"+tp_ApplicantMobileNo);
			mAssert(tp_ApplicantMobileNo, mGetPropertyFromFile("tp_ApplicantMobileNodata"), "Applicant Mobile No failed");
			ApplicantMobileNo.add(tp_ApplicantMobileNo);
			//ApplicantMobileNo.add(mGetPropertyFromFile("tp_ApplicantMobileNodata"));

			mSendKeys("id", mGetPropertyFromFile("tp_ApplicantEmailid"), mGetPropertyFromFile("tp_ApplicantEmaildata"));
			String ApplicantEmail=mGetText("id", mGetPropertyFromFile("tp_ApplicantEmailid"), "value");
			ApplicantEmailId.add(ApplicantEmail);
			mAssert(ApplicantEmail, mGetPropertyFromFile("tp_ApplicantEmaildata"), "Applicant Email ID failed");
			//ApplicantEmailId.add(mGetPropertyFromFile("tp_ApplicantEmaildata"));


			//sending owner details
			mSelectDropDown("id", mGetPropertyFromFile("tp_OwnerTitleid"), mGetPropertyFromFile("tp_OwnerTitledata"));
			String OwnerTitle=mCaptureSelectedValue("id", mGetPropertyFromFile("tp_OwnerTitleid"));
			ownertitle.add(OwnerTitle);
			mAssert(OwnerTitle, mGetPropertyFromFile("tp_OwnerTitledata"), "Owner Title failed");

			//Owner First name
			mSendKeys("id", mGetPropertyFromFile("tp_OwnerFNameid"), mGetPropertyFromFile("tp_OwnerFNamedata"));
			String OwnerFName=mGetText("id", mGetPropertyFromFile("tp_OwnerFNameid"), "value");
			ownerfname.add(OwnerFName);
			mAssert(OwnerFName, mGetPropertyFromFile("tp_OwnerFNamedata"), "Owner First name failed");


			//Owner Middle name
			mSendKeys("id", mGetPropertyFromFile("tp_OwnerMNameid"), mGetPropertyFromFile("tp_OwnerMNamedata"));
			String OwnerMName=mGetText("id", mGetPropertyFromFile("tp_OwnerMNameid"), "value");
			mAssert(OwnerMName, mGetPropertyFromFile("tp_OwnerMNamedata"), "Owner Middle name failed");
			ownermname.add(OwnerMName);


			mSendKeys("id", mGetPropertyFromFile("tp_OwnerLNameid"), mGetPropertyFromFile("tp_OwnerLNamedata"));
			String OwnerLName=mGetText("id", mGetPropertyFromFile("tp_OwnerLNameid"), "value");
			ownermname.add(OwnerLName);
			mAssert(OwnerLName, mGetPropertyFromFile("tp_OwnerLNamedata"), "Owner Last name failed");
			//ownermname.add(mGetPropertyFromFile("tp_OwnerLNamedata"));

			String title = mCaptureSelectedValue("id", mGetPropertyFromFile("tp_OwnerTitleid"));
			String fname = 	mGetText("id", mGetPropertyFromFile("tp_OwnerFNameid"), "value");
			String mname = 	mGetText("id", mGetPropertyFromFile("tp_OwnerMNameid"), "value");
			String lname = 	mGetText("id", mGetPropertyFromFile("tp_OwnerLNameid"), "value");
			System.out.println("title is: "+title+"\n"+"fname is: "+fname+"\n"+"mname is: "+mname+"\n"+"lname is: "+lname);			
			String ownername=title+" "+fname+" "+mname+" "+lname;			
			System.out.println("Name of Owner is : "+ownername);

			// used in Approval Letter
			AlD_Nameofowner.add(ownername);
			System.out.println("Name of Owner in arraylist is : "+AlD_Nameofowner);

			mSendKeys("id", mGetPropertyFromFile("tp_OwnerMobileNoid"), mGetPropertyFromFile("tp_OwnerMobileNodata"));
			String OwnerMobileNo1=mGetText("id", mGetPropertyFromFile("tp_OwnerMobileNoid"), "value");
			mAssert(OwnerMobileNo1, mGetPropertyFromFile("tp_OwnerMobileNodata"), "Owner Mobile No failed");
			OwnerMobileNo.add(OwnerMobileNo1);
			System.out.println("Owner Mobile No is : "+OwnerMobileNo);

			mSendKeys("id", mGetPropertyFromFile("tp_OwnerEmailid"), mGetPropertyFromFile("tp_OwnerEmaildata"));
			String OwnerEmail1=mGetText("id", mGetPropertyFromFile("tp_OwnerEmailid"), "value");
			System.out.println(OwnerEmailId.add(OwnerEmail1));
			mAssert(OwnerEmail1, mGetPropertyFromFile("tp_OwnerEmaildata"), "Owner Email ID failed");
			OwnerEmailId.add(OwnerEmail1);

			mSendKeys("id", mGetPropertyFromFile("tp_AddLine1id"), mGetPropertyFromFile("tp_AddLine1data"));
			String ldAddLine1=mGetText("id", mGetPropertyFromFile("tp_AddLine1id"), "value");
			Addline1.add(ldAddLine1);
			mAssert(ldAddLine1, mGetPropertyFromFile("tp_AddLine1data"), "Owner Address Line 1 failed");
			//Addline1.add(mGetPropertyFromFile("tp_AddLine1data"));

			mSendKeys("id", mGetPropertyFromFile("tp_AddLine2id"), mGetPropertyFromFile("tp_AddLine2data"));
			String ldAddLine2=mGetText("id", mGetPropertyFromFile("tp_AddLine2id"), "value");
			Addline2.add(ldAddLine2);
			mAssert(ldAddLine2, mGetPropertyFromFile("tp_AddLine2data"), "Owner Address Line 2 failed");
			//Addline2.add(mGetPropertyFromFile("tp_AddLine2data").trim());

			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_OwnerPinCodeid"), mGetPropertyFromFile("tp_OwnerPinCodedata"));
			String OwnerPinCode=mGetText("id", mGetPropertyFromFile("tp_OwnerPinCodeid"), "value");
			Pincode.add(OwnerPinCode);
			mAssert(OwnerPinCode, mGetPropertyFromFile("tp_OwnerPinCodedata"), "Owner Pincode failed");
			//Pincode.add(mGetPropertyFromFile("tp_OwnerPinCodedata").trim());

			mCustomWait(200);
			mTakeScreenShot();
			mscroll(0,450);
			mCustomWait(200);

			//Adding multiple additional owner details
			String Addtitle= mGetPropertyFromFile("tp_AddOwnerTitledata");
			String Addfame= mGetPropertyFromFile("tp_AddOwnerFNamedata");
			String Addmname= mGetPropertyFromFile("tp_AddOwnerMNamedata");
			String Addlname= mGetPropertyFromFile("tp_AddOwnerLNamedata");
			String Addmbno= mGetPropertyFromFile("tp_AddOwnerMobileNodata");
			String Addemail= mGetPropertyFromFile("tp_AddOwnerEmaildata");

			ArrayList AddTitleList = new ArrayList(Arrays.asList(Addtitle.split(",")));
			ArrayList AddFNameList = new ArrayList(Arrays.asList(Addfame.split(",")));
			ArrayList AddMNameList = new ArrayList(Arrays.asList(Addmname.split(",")));
			ArrayList AddLNameList = new ArrayList(Arrays.asList(Addlname.split(",")));
			ArrayList AddMbNoList = new ArrayList(Arrays.asList(Addmbno.split(",")));
			ArrayList AddEmailList = new ArrayList(Arrays.asList(Addemail.split(",")));

			for(int i=0; i<AddTitleList.size();i++)
			{

				//additional owner link
				mCustomWait(200);
				mWaitForVisible("linkText", mGetPropertyFromFile("tp_AddtnlOwnerDetailid"));
				mClick("linkText", mGetPropertyFromFile("tp_AddtnlOwnerDetailid"));

				//sending additional owner Title
				mWaitForVisible("id", mGetPropertyFromFile("tp_AddOwnerTitleid"));
				mSelectDropDown("id", mGetPropertyFromFile("tp_AddOwnerTitleid"),AddTitleList.get(i).toString());
				String AddOwnerTitle = mCaptureSelectedValue("id", mGetPropertyFromFile("tp_AddOwnerTitleid"));
				tpAddOwnertitle.add(AddOwnerTitle);

				//sending Additional Owner First Name
				mCustomWait(200);
				mSendKeys("id", mGetPropertyFromFile("tp_AddOwnerFNameid"),AddFNameList.get(i).toString());
				String AddOwnerFName = mGetText("id", mGetPropertyFromFile("tp_AddOwnerFNameid"),"value");
				tpAddOwnerFName.add(AddOwnerFName);

				//sending Additional Owner Middle Name
				mCustomWait(200);
				mSendKeys("id", mGetPropertyFromFile("tp_AddOwnerMNameid"), AddMNameList.get(i).toString());
				String AddOwnerMName = mGetText("id", mGetPropertyFromFile("tp_AddOwnerMNameid"),"value");
				tpAddOwnerMName.add(AddOwnerMName);

				//sending Additional Owner Last Name
				mCustomWait(200);
				mSendKeys("id", mGetPropertyFromFile("tp_AddOwnerLNameid"),AddLNameList.get(i).toString());
				String AddOwnerLName = mGetText("id", mGetPropertyFromFile("tp_AddOwnerLNameid"),"value");
				tpAddOwnerlName.add(AddOwnerLName);

				//Additional Owner Name
				String additionalownerFullname=AddOwnerTitle+" "+AddOwnerFName+" "+AddOwnerMName+" "+AddOwnerMName;
				AddownerFullname.add(additionalownerFullname);

				//sending Additional Owner Mobile No
				mCustomWait(200);
				mSendKeys("id", mGetPropertyFromFile("tp_AddOwnerMobileNoid"),AddMbNoList.get(i).toString());
				String AddOwnerMobileNo = mGetText("id", mGetPropertyFromFile("tp_AddOwnerMobileNoid"),"value");
				AddMobileNo.add(AddOwnerMobileNo);


				//sending Additional Owner Email ID
				mCustomWait(200);
				mSendKeys("id", mGetPropertyFromFile("tp_AddOwnerEmailid"),AddEmailList.get(i).toString());
				String AddOwnerEmail = mGetText("id", mGetPropertyFromFile("tp_AddOwnerEmailid"),"value");
				AddEmailId.add(AddOwnerEmail);

				mCustomWait(200);
				mTakeScreenShot();

				//additional owner detail submit button
				mWaitForVisible("xpath", mGetPropertyFromFile("tp_AddOwnerSubBtnid"));
				mCustomWait(200);
				mClick("xpath", mGetPropertyFromFile("tp_AddOwnerSubBtnid"));

				//closing popup
				mWaitForVisible("css", mGetPropertyFromFile("tp_AddOwnerClosePopUpid"));
				mCustomWait(500);
				String AddOwnerPopupMsg = mGetText("css", mGetPropertyFromFile("tp_AddOwnerPopUpMsgid"));
				mAssert(AddOwnerPopupMsg, mGetPropertyFromFile("tp_AddOwnerPopUpMsgdata"), "Actual:  "+AddOwnerPopupMsg+"  Expected  :"+mGetPropertyFromFile("tp_AddOwnerPopUpMsgdata"));
				mTakeScreenShot();
				mCustomWait(500);
				mClick("css", mGetPropertyFromFile("tp_AddOwnerClosePopUpid"));
				mCustomWait(500);
			}

			System.out.println(AddownerFullname);
			System.out.println("Additional owner email id"+AddEmailId);
			System.out.println("Additional owner email id"+AddMobileNo);

			mTakeScreenShot();
			mCustomWait(500);
			mscroll(0,650);

			//sending Ward No
			mSelectDropDown("id",mGetPropertyFromFile("tp_WardNoid"), mGetPropertyFromFile("tp_WardNodata"));
			String ldWard=mCaptureSelectedValue("id",mGetPropertyFromFile("tp_WardNoid"));
			ward.add(ldWard);
			mAssert(ldWard, mGetPropertyFromFile("tp_WardNodata"), "Ward No failed");



			//sending Plot No
			mSendKeys("id", mGetPropertyFromFile("tp_PlotNoid"), mGetPropertyFromFile("tp_PlotNodata"));
			String tp_PlotNo=mGetText("id",mGetPropertyFromFile("tp_PlotNoid"),"value");
			PlotKhasraNo.add(tp_PlotNo);
			mAssert(tp_PlotNo, mGetPropertyFromFile("tp_PlotNodata"), "Plot No Khasara failed");



			//sending MSP/ Khasara No
			mSendKeys("id", mGetPropertyFromFile("tp_MSPNoid"), mGetPropertyFromFile("tp_MSPNodata"));
			String tp_MSPNo=mGetText("id",mGetPropertyFromFile("tp_MSPNoid"),"value");
			PlotNoMSP.add(tp_MSPNo);
			mAssert(tp_MSPNo, mGetPropertyFromFile("tp_MSPNodata"), "Plot No MSP failed");
			//PlotNoMSP.add(mGetPropertyFromFile("tp_MSPNodata"));

			//sending Mauja No
			mSendKeys("id", mGetPropertyFromFile("tp_MaujaNoid"), mGetPropertyFromFile("tp_MaujaNodata"));
			String tp_MaujaNo=mGetText("id",mGetPropertyFromFile("tp_MaujaNoid"),"value");
			MaujaNo.add(tp_MaujaNo);
			mAssert(tp_MaujaNo, mGetPropertyFromFile("tp_MaujaNodata"), "Mauja No failed");
			//MaujaNo.add(mGetPropertyFromFile("tp_MaujaNodata"));

			//sending Holding No
			mSendKeys("id", mGetPropertyFromFile("tp_HoldingNoid"), mGetPropertyFromFile("tp_HoldingNodata"));
			String tp_HoldingNo=mGetText("id",mGetPropertyFromFile("tp_HoldingNoid"),"value");
			HoldingNo.add(tp_HoldingNo);
			mAssert(tp_HoldingNo, mGetPropertyFromFile("tp_HoldingNodata"), "Holding No MSP failed");
			//HoldingNo.add(mGetPropertyFromFile("tp_HoldingNodata"));

			//sending Khata No
			mSendKeys("id", mGetPropertyFromFile("tp_KhataNoid"), mGetPropertyFromFile("tp_KhataNodata"));
			String tp_KhataNo=mGetText("id",mGetPropertyFromFile("tp_KhataNoid"),"value");
			KhataNo.add(tp_KhataNo);
			mAssert(tp_KhataNo, mGetPropertyFromFile("tp_KhataNodata"), "Khata No MSP failed");
			//KhataNo.add(mGetPropertyFromFile("tp_KhataNodata"));

			//sending Toji No
			mSendKeys("id", mGetPropertyFromFile("tp_TojiNoid"), mGetPropertyFromFile("tp_TojiNodata"));
			String tp_TojiNo=mGetText("id",mGetPropertyFromFile("tp_TojiNoid"),"value");
			System.out.println(TojiNo.add(tp_TojiNo));


			System.out.println("TojiNo=>"+TojiNo);
			mAssert(tp_TojiNo, mGetPropertyFromFile("tp_TojiNodata"), "Khata No MSP failed");
			//TojiNo.add(mGetPropertyFromFile("tp_TojiNodata"));

			//sending Village No
			mSendKeys("id", mGetPropertyFromFile("tp_VillageNoid"), mGetPropertyFromFile("tp_VillageNodata"));
			String tp_Village=mGetText("id",mGetPropertyFromFile("tp_VillageNoid"),"value");
			System.out.println(Village.add(tp_Village));
			mAssert(tp_Village, mGetPropertyFromFile("tp_VillageNodata"), "Khata No MSP failed");
			//Village.add(mGetPropertyFromFile("tp_VillageNodata"));

			// Adding Usage type details
			String usgId= mGetPropertyFromFile("tp_UsageTypedata");
			String CoverAr= mGetPropertyFromFile("tp_CoveredAreadata");
			String CoverAreaInMt=mGetPropertyFromFile("tp_CoverAreaInMtdata");

			ArrayList usgIdList= new ArrayList(Arrays.asList(usgId.split(",")));
			ArrayList CoverArList= new ArrayList(Arrays.asList(CoverAr.split(",")));
			ArrayList CoverArInMtList = new ArrayList(Arrays.asList(CoverAreaInMt.split(",")));

			for(int i=0;i<usgIdList.size();i++)
			{
				System.out.println(" Usage Type are as  "+usgIdList.get(i));
				System.out.println(" Usage Type Cover area in hecter are as  "+CoverArList.get(i));
				System.out.println(" Usage Type Cover area in Meter square are as  "+CoverArInMtList.get(i));

				//Sending Usage Type
				mSelectDropDown("id", (mGetPropertyFromFile("tp_UsageTypeid")+(i+0)+(i+1)), usgIdList.get(i).toString());
				String tp_UsageType=mCaptureSelectedValue("id", (mGetPropertyFromFile("tp_UsageTypeid")+(i+0)+(i+1)));
				UsageType.add(tp_UsageType);
				System.out.println("Usage Type(Arryalist) @ Application Form :::"+UsageType);

				//Sending Covered Area
				mSendKeys("id", (mGetPropertyFromFile("tp_CoveredAreaid")+i) , CoverArList.get(i).toString());
				String tp_CoveredArea=mGetText("id", (mGetPropertyFromFile("tp_CoveredAreaid")+i), "value");
				System.out.println("Covered Area in meter::"+CoveredAreainHect);
				CoveredAreainHect.add(tp_CoveredArea);

				//Reading Value from input text box
				String CoverAreaVerify= mGetText("id", (mGetPropertyFromFile("tp_CoverAreaInMtid")+i),"value"/*CoverArInMtList.get(i).toString()*/);
				CoveredAreainft2.add("Covered Area in feet::"+CoverAreaVerify);
				System.out.println(CoveredAreainft2);
				//Comparing values with text value and read value
				//mAssert(CoverAreaVerify, mGetPropertyFromFile("tp_CoverAreaInMtdata"), "Actual :"+CoverAreaVerify+"  Expected  :"+mGetPropertyFromFile("tp_CoverAreaInMtdata"));
				//String CoverArHect=mGetText("id", mGetPropertyFromFile("tp_CoveredAreaHecterid"),"value"); 
				//mAssert(CoverArHect, mGetPropertyFromFile("tp_CoveredAreaHecterdata"), "The Cover Area in Hector (Read only) does not match :::"+"Actual :"+CoverArHect+"  Expected  :"+mGetPropertyFromFile("tp_CoveredAreaHecterdata"));

				//	String CoverArInfeet=mGetText("id", mGetPropertyFromFile("tp_CoveredAreaInfeetid"),"value");
				//mAssert(CoverArInfeet, mGetPropertyFromFile("tp_CoveredAreaInfeetdata"),"The Cover Area in Hector in Feet  (Read only) does not match"+"Actual :"+CoverArInfeet+"  Expected  :"+mGetPropertyFromFile("tp_CoveredAreaInfeetdata"));

				if(i==((usgIdList.size())-1))
				{
					System.out.println("Nothing to execute::: ");
				}
				else{
					mClick("id", mGetPropertyFromFile("tp_AddUsageTypeBtnid"));
				}
			}

			/*String CoverArHect=mGetText("id", mGetPropertyFromFile("tp_CoveredAreaHecterid"),"value"); 

			mAssert(CoverArHect, mGetPropertyFromFile("tp_CoveredAreaHecterdata"),"CoveredArea Hectare fails Actual :"+CoverArHect+"  Expected  :"+mGetPropertyFromFile("tp_CoveredAreaHecterdata"));
			String CoverArInfeet=mGetText("id", mGetPropertyFromFile("tp_CoveredAreaInfeetid"),"value");
			mAssert(CoverArInfeet, mGetPropertyFromFile("tp_CoveredAreaInfeetdata"),"CoveredArea in Feet fails Actual :"+CoverArInfeet+"  Expected  :"+mGetPropertyFromFile("tp_CoveredAreaInfeetdata"));
			 */

			mTakeScreenShot();
			mCustomWait(500);

			//Sending Parking Area
			mSendKeys("id", mGetPropertyFromFile("tp_ParkingAreaid"), mGetPropertyFromFile("tp_ParkingAreadata"));
			String tp_ParkingArea=mGetText("id", mGetPropertyFromFile("tp_ParkingAreaid"),"value");
			System.out.println(ParkingAreamt2.add(tp_ParkingArea));
			mAssert(tp_ParkingArea, mGetPropertyFromFile("tp_ParkingAreadata"), "Parking Area in meter failed");
			//ParkingAreamt2.add(mGetPropertyFromFile("tp_ParkingAreadata"));

			//Reading Parking area in feet 
			String ParkArInfeet=mGetText("id", mGetPropertyFromFile("tp_ParkingAreaFtid"),"value");
			mAssert(ParkArInfeet, mGetPropertyFromFile("tp_ParkingAreaFtdata"), "parking area in feet fails Actual :"+ParkArInfeet+"  Expected  :"+mGetPropertyFromFile("tp_ParkingAreaFtdata"));
			System.out.println(" Parking Area in feet  "+ParkArInfeet);
			System.out.println(ParkingAreaft2.add(ParkArInfeet));

			//Sending Free Area
			mSendKeys("id", mGetPropertyFromFile("tp_FreeAreaid"), mGetPropertyFromFile("tp_FreeAreadata"));
			String tp_FreeArea=mGetText("id", mGetPropertyFromFile("tp_FreeAreaid"), "value");
			System.out.println(wideningofroadmt.add(tp_FreeArea));
			mAssert(tp_FreeArea, mGetPropertyFromFile("tp_FreeAreadata"), "Parking Area in meter failed");
			//wideningofroadmt.add(mGetPropertyFromFile("tp_FreeAreadata"));

			//Reading Free area in feet 
			String FreeArInfeet=mGetText("id", mGetPropertyFromFile("tp_FreeAreaFtid"),"value");
			mAssert(FreeArInfeet, mGetPropertyFromFile("tp_FreeAreaFtdata"), "free area in feet fails Actual :"+FreeArInfeet+"  Expected  :"+mGetPropertyFromFile("tp_FreeAreaFtdata"));
			System.out.println(wideningofroadft.add(FreeArInfeet));
			mTakeScreenShot();

			//Sending 500 name
			mSendKeys("id", mGetPropertyFromFile("tp_ProjectNameid"), mGetPropertyFromFile("tp_ProjectNamedata"));
			String tp_ProjectName=mGetText("id", mGetPropertyFromFile("tp_ProjectNameid"), "value");
			mAssert(tp_ProjectName, mGetPropertyFromFile("tp_ProjectNamedata"), "Project Name fails");
			System.out.println(ProjectName.add(tp_ProjectName));
			//ProjectName.add(mGetPropertyFromFile("tp_ProjectNamedata").trim());


			//Sending Scheme Name
			mSendKeys("id", mGetPropertyFromFile("tp_SchemeNameid"), mGetPropertyFromFile("tp_SchemeNamedata"));
			String tp_SchemeName=mGetText("id", mGetPropertyFromFile("tp_SchemeNameid"), "value");
			mAssert(tp_SchemeName, mGetPropertyFromFile("tp_SchemeNamedata"), "Project Name fails");
			System.out.println(SchemeName.add(tp_SchemeName));
			//SchemeName.add(mGetPropertyFromFile("tp_SchemeNamedata").trim());

			mCustomWait(500);
			mscroll(0,400);

			//Add technical person detail 
			String perType= mGetPropertyFromFile("tp_TechPrsnTypedata");
			String perName= mGetPropertyFromFile("tp_TechPrsnNamedata");

			ArrayList techPertypeList = new ArrayList(Arrays.asList(perType.split(",")));
			ArrayList techPernameList = new ArrayList(Arrays.asList(perName.split(",")));

			for(int i=0;i<techPertypeList.size();i++)

			{
				//Clicking on hyperlink Add Technical person details
				mCustomWait(500);
				mClick("linkText", mGetPropertyFromFile("tp_AddTechPersnid"));

				//Printing Tech person Name and type on console
				System.out.println(" Technical Person Type List is   "+techPertypeList.get(i));
				System.out.println(" Technical Person Name List is  "+techPernameList.get(i));


				//Sending Technical Person Type
				mWaitForVisible("id", mGetPropertyFromFile("tp_TechPrsnTypeid"));
				mSelectDropDown("id", mGetPropertyFromFile("tp_TechPrsnTypeid"),techPertypeList.get(i).toString());
				String TechPrsntype=mCaptureSelectedValue("id",  mGetPropertyFromFile("tp_TechPrsnTypeid"));
				//TechPerType.add(mGetPropertyFromFile("tp_TechPrsnTypedata"));
				TechPerType.add(TechPrsntype);


				//Sending Technical Person Name
				mWaitForVisible("id", mGetPropertyFromFile("tp_TechPrsnNameid"));
				mSelectDropDown("id", mGetPropertyFromFile("tp_TechPrsnNameid"), techPernameList.get(i).toString());
				String TechPrsn=mCaptureSelectedValue("id", mGetPropertyFromFile("tp_TechPrsnNameid"));
				TechPerName.add(TechPrsn);
				//TechPerName.add(mGetPropertyFromFile("tp_TechPrsnNamedata"));

				//Submitting the record
				mCustomWait(500);
				mTakeScreenShot();
				mClick("xpath", mGetPropertyFromFile("tp_TechPrsnSubBtnid"));

				//close pop up button
				mWaitForVisible("css", mGetPropertyFromFile("tp_TechPrsnPopUpBtnid"));
				mCustomWait(500);
				String TechPrsnPOpUPMsg = mGetText("css", mGetPropertyFromFile("tp_TechPrsnPopUpMsgid"));
				mAssert(TechPrsnPOpUPMsg, mGetPropertyFromFile("tp_TechPrsnPopUpMsgdata"),"Technical Person not matches::"+"Actual :"+TechPrsnPOpUPMsg+"  Expected  :"+mGetPropertyFromFile("tp_TechPrsnPopUpMsgdata"));
				mCustomWait(500);
				mTakeScreenShot();
				mClick("css", mGetPropertyFromFile("tp_TechPrsnPopUpBtnid"));
			}
			System.out.println("The Tech Person Name are"+TechPerName);
			System.out.println("The Tech Person Type are"+TechPerType);
			mscroll(0, 500);
			mTakeScreenShot();
			//Uploading the documen
			mCustomWait(1000);
			docUpload("xpath", mGetPropertyFromFile("tp_UploadDocumentTableId"), "upldDocStatus");

			mTakeScreenShot();

			mCustomWait(500);
			mscroll(0,720);
			mTakeScreenShot();

			//Submitting the Application
			mWaitForVisible("xpath", mGetPropertyFromFile("tp_AppForLanDevSubBtnid"));
			mClick("xpath", mGetPropertyFromFile("tp_AppForLanDevSubBtnid"));

			//String msgAftrTpApp = driver.findElement(By.cssSelector("p > b")).getText();
			mWaitForVisible("id", mGetPropertyFromFile("tp_AppForLanDevProcdBtnid"));
			mCustomWait(500);
			String msgAftrTpApp =mGetText("css", mGetPropertyFromFile("tp_MsgAftrAppForLanDevid"));

			mCustomWait(500);
			Pattern CaseNo = Pattern.compile("(?<=Your Case No. is : )(.*)");
			java.util.regex.Matcher matcher = CaseNo.matcher(msgAftrTpApp);

			if (matcher.find()) {

				String caseNum = matcher.group(1);
				CaseNumber = caseNum.split("\\.", 2)[0];
				System.out.println();
				System.out.println("Case Number is :"+CaseNumber);
			}

			System.out.println();
			System.out.println(" Your initial message is :"+msgAftrTpApp);
			String delimiter = "Your Case No. is : ";
			String[] AssertMsg = msgAftrTpApp.split(delimiter);
			//System.out.println(AssertMsg[0]);

			mCustomWait(1000);
			Pattern ApplicationMsg = Pattern.compile("\\s*(.+?)\\s*(?=Your Case No.)");
			java.util.regex.Matcher matcher1 = ApplicationMsg.matcher(msgAftrTpApp);
			String AppleMsg = null;
			if (matcher1.find()) 
			{
				String msg = matcher1.group(1);
				AppleMsg = msg.split("\\.", 2)[0];
				System.out.println();
				System.out.println(AppleMsg);
			}
			mAssert(AppleMsg, mGetPropertyFromFile("tp_MsgAftrAppForLanDevdata"),"Actual  :"+AppleMsg+"  Expected   :"+mGetPropertyFromFile("tp_MsgAftrAppForLanDevdata"));	

			String msg = AssertMsg[0];
			mCustomWait(500);
			System.out.println(AssertMsg[0]);
			System.out.println(AppleMsg);
			mTakeScreenShot();

			/*appNo = mGetText("css", mGetPropertyFromFile("tp_AppForLanDevAppNoid"));
			System.out.println(" Application no for Land development is :" + appNo);*/

			mClick("id", mGetPropertyFromFile("tp_AppForLanDevProcdBtnid"));

			mWaitForVisible("css", mGetPropertyFromFile("tp_AppforLanDevFinishBtnid"));
			mTakeScreenShot();
			appNo = mGetText("css", mGetPropertyFromFile("tp_AppNoAtFinishOfAppForLanDev"));
			mAppNoArray("css",mGetPropertyFromFile("tp_AppNoAtFinishOfAppForLanDev"));

			ApplicationNoforLandDev.add(appNo);

			System.out.println(" Application no for Land development after finish is "+ appNo);
			mClick("css", mGetPropertyFromFile("tp_AppforLanDevFinishBtnid"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in ApplicationForLandDevp script");
		}
	}

	//ChecklistVerification fromMBA Login
	public void ChecklistVerification()
	{
		try
		{
			mCustomWait(1000);
			//CFC link
			mWaitForVisible("linkText", mGetPropertyFromFile("tp_ChcklstVerfctnLinkid"));
			mGenericWait();

			mNavigation("tp_ChcklstVerfctnLinkid","tp_ClvTransactionLinkid","tp_ClvChecklistVerificationLinkid");

			//sending application number
			mWaitForVisible("id", mGetPropertyFromFile("tp_ClvApplctnNoid"));
			mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("tp_ClvApplctnNoid"), "57116083100001");
			IndOrDep("id", "tp_ClvApplctnNoid", "applicationNo");
			mCustomWait(500);
			mTakeScreenShot();

			//finding application
			mWaitForVisible("css", mGetPropertyFromFile("tp_ClvFindAppBtnid"));
			mClick("css", mGetPropertyFromFile("tp_ClvFindAppBtnid"));
			mCustomWait(500);

			//finding application image ink
			mWaitForVisible("xpath", mGetPropertyFromFile("tp_ClvFindAppImgid"));
			mCustomWait(500);
			mTakeScreenShot();
			mCustomWait(500);
			mClick("xpath", mGetPropertyFromFile("tp_ClvFindAppImgid"));
			mCustomWait(1000);
			mTakeScreenShot();

			try{
				WebElement table = driver.findElement(By.className("gridtable"));

				List<WebElement> rows = table.findElements(By.tagName("tr"));
				int rwcount =rows.size();
				System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
				for(int i=2;i<=rwcount;i++)
				{
					for(int j=4;j<=6;j++)
					{
						if(j==4)
						{
							String frstDoc1 = driver.findElement(By.xpath("//*[@id=\"frmMaster\"]/table/tbody/tr["+i+"]/td["+j+"]/div/a")).getText();
							System.out.println(frstDoc1);
							driver.findElement(By.linkText(frstDoc1)).click();
							mDownloadFile(myClassName);
							mCustomWait(3000);
							mSwitchTabs();
						}
						if(j==5)
						{
							mUpload("css", mGetPropertyFromFile("tp_ClvUploadid")+(i-2)+"']", mGetPropertyFromFile("tp_ClvUploaddata"+i));
							mCustomWait(1000);
						}
						if(j==6)
						{
							mClick("id", mGetPropertyFromFile("tp_ClvVeificationCheckBoxid")+(i-2));
							mCustomWait(1000);
						}
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			mscroll(0,350);

			//approve hold or reject
			mClick("id", mGetPropertyFromFile("tp_ClvAppApproveid"));
			mCustomWait(500);
			mTakeScreenShot();

			//submit button
			mClick("xpath", mGetPropertyFromFile("tp_ClvChcklistVerifictnSubBtnid"));	
			mCustomWait(500);

			//waiing for proceed button
			mWaitForVisible("id", mGetPropertyFromFile("tp_ClvChcklistVerifictnProcdBtnid"));
			String MsgAftrChcklstVerfctn = mGetText("css", mGetPropertyFromFile("tp_ClvChcklistVerifictnMsgid"));
			System.out.println(MsgAftrChcklstVerfctn);
			mTakeScreenShot();
			mCustomWait(500);
			mAssert(MsgAftrChcklstVerfctn, mGetPropertyFromFile("tp_ClvProcdAssertData"), "Actual   :"+MsgAftrChcklstVerfctn+"  Expected  :"+mGetPropertyFromFile("tp_ClvProcdAssertData"));
			mCustomWait(500);
			mClick("id", mGetPropertyFromFile("tp_ClvChcklistVerifictnProcdBtnid"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in ChecklistVerification script");
		}
	}


	//LOI Payment
	public void LOIPayment()
	{
		try
		{
			LOIAPPLICABLE= false;

			mWaitForVisible("linkText", mGetPropertyFromFile("tp_LOIPaymentServiceLinkid"));
			//mNavigation("tp_LOIPaymentLinkid","tp_LOIPaymentLinkid", "tp_LpForBuildinpermsnServicesLinkid");
			mNavigation("tp_LOIPaymentServiceLinkid", "tp_LOIPaymentLinkid","tp_LpForBuildinpermsnServicesLinkid"); 

			//sending LOI number
			mWaitForVisible("id", mGetPropertyFromFile("tp_LpSearchByLOINumid"));
			mCustomWait(500);
			//mSendKeys("id", mGetPropertyFromFile("tp_LpSearchByLOINumid"), LOINumber);
			//mSendKeys("id", mGetPropertyFromFile("tp_LpSearchByAppNumid"), "20");

			//sending application number
			IndOrDep("id", "tp_LpSearchByAppNumid","applicationNo" );
			mCustomWait(500);

			//Search Button
			mWaitForVisible("css", mGetPropertyFromFile("tp_LpSearchAppBtnid"));
			mTakeScreenShot();

			mClick("css", mGetPropertyFromFile("tp_LpSearchAppBtnid"));
			mCustomWait(500);

			//View Details Button
			mWaitForVisible("xpath", mGetPropertyFromFile("tp_LpViewDetailsImgid"));
			mClick("xpath", mGetPropertyFromFile("tp_LpViewDetailsImgid"));
			mCustomWait(500);

			//getting application number for assert
			String ApplicationNum =  driver.findElement(By.id(mGetPropertyFromFile("tp_LpAssertAppNoid"))).getAttribute("value");
			System.out.println(ApplicationNum);
			mCustomWait(500);

			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{	
				mAssert(ApplicationNum, appNo, "Application Number Does Not Matches at loi payment.    Actual  :"+ApplicationNum+"     Expected     :"+applicationNo.get(CurrentinvoCount).toString());
			}
			else
			{
				mAssert(ApplicationNum, mGetPropertyFromFile("applicationNo"), "Application Number Does Not Matches at loi payment.    Actual  :"+ApplicationNum+"     Expected     :"+mGetPropertyFromFile("applicationNo"));
			}

			String LOINUm = driver.findElement(By.id(mGetPropertyFromFile("tp_LpAssertLOINoid"))).getAttribute("value");
			System.out.println(LOINUm);
			mCustomWait(500);
			//mAssert(LoiNum, scrutinyLOInum, "LOI Number does not match at LOI Payment.  Actual  :"+LOINUm+"   Expected   :"+LOINumber);
			mCustomWait(500);
			mTakeScreenShot();

			payment("paymentMode","byBankOrULB");

			if(!mGetPropertyFromFile("paymentMode").equalsIgnoreCase("online")){
				//submit button
				mWaitForVisible("xpath", mGetPropertyFromFile("tp_LpLOIPaymentSubBtnid"));
				mTakeScreenShot();
				mClick("xpath", mGetPropertyFromFile("tp_LpLOIPaymentSubBtnid"));
				mCustomWait(500);

				//proceed button
				mWaitForVisible("id", mGetPropertyFromFile("tp_LpLOIPaymentProcdBtnid"));
				mCustomWait(500);
				String msg = mGetText("css", mGetPropertyFromFile("tp_LpLOIPaymentProcdMsgid"));

				mAssert(msg, mGetPropertyFromFile("tp_LpLOIPaymentProcdMsgdata"), "Message Does not match at LOI Payment POPup.   Actual   :"+msg+"   Expected   :"+mGetPropertyFromFile("tp_LpLOIPaymentProcdMsgdata"));
				System.out.println(msg);
				mCustomWait(500);
				mTakeScreenShot();
				mClick("id", mGetPropertyFromFile("tp_LpLOIPaymentProcdBtnid"));
				//newChallanReader();
				//mChallanPDFReader();
				//api.PdfPatterns.TPLOIReceiptPDF(pdfoutput);
				mCustomWait(1000);
				LOIVerification();
				mCustomWait(1000);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in LOIPayment script");
		}
	}

	//Application Approval
	public void ApplicationApproval()
	{
		try
		{
			//Building Permission & Regulations
			mWaitForVisible("linkText", mGetPropertyFromFile("tp_AaBuildinPermsnNRegultnLinkid"));
			mGenericWait();			
			mNavigation("tp_AaBuildinPermsnNRegultnLinkid","tp_AaReportsLinkid","tp_AaApprovalLinkid");

			//find Application Icon
			mWaitForVisible("css", mGetPropertyFromFile("tp_AaFindAppSearchBtnid"));
			mClick("css", mGetPropertyFromFile("tp_AaFindAppSearchBtnid"));
			mCustomWait(500);

			//finding Application by application No.
			mWaitForVisible("css", mGetPropertyFromFile("tp_AaSearchByTypeid"));
			mSelectDropDown("css", mGetPropertyFromFile("tp_AaSearchByTypeid"), mGetPropertyFromFile("tp_AaSearchByTypedata"));
			mCustomWait(500);

			//sending Application Number
			IndOrDep("id", "tp_AaSeandinNoToSearchAppid","applicationNo");
			mTab("id", mGetPropertyFromFile("tp_AaSeandinNoToSearchAppid"));
			mCustomWait(500);
			mTakeScreenShot();

			//search Application Button
			mWaitForVisible("id", mGetPropertyFromFile("tp_AaFinalSearchAftrSendinAppNoid"));
			mCustomWait(500);		
			mClick("id", mGetPropertyFromFile("tp_AaFinalSearchAftrSendinAppNoid"));			

			//View Details
			mWaitForVisible("xpath", mGetPropertyFromFile("tp_AaViewDetailsBtnid"));			
			mCustomWait(500);
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("tp_AaViewDetailsBtnid"));

			//String applicationNumber = driver.findElement(By.id("apmApplicationId")).getText();
			String applicationNumber = driver.findElement(By.id(mGetPropertyFromFile("tp_AaAppNoAtApprovalid"))).getAttribute("value");
			System.out.println(applicationNumber);
			if(mGetPropertyFromFile("tp_TwlsTrutiPatraVerfyImgid").equalsIgnoreCase("dependent"))
			{	
				mAssert(applicationNumber, applicationNo.get(CurrentinvoCount).toString(), "Application Number does not match at Approval.    Actual  :"+applicationNumber+"     Expected     :"+applicationNo.get(CurrentinvoCount).toString());
				mCustomWait(500);
			}
			else
			{
				//mAssert(applicationNumber, mGetPropertyFromFile("applicationNo"), "Application Number does not match at Approval.    Actual  :"+applicationNumber+"     Expected     :"+mGetPropertyFromFile("applicationNo"));
				mCustomWait(500);
			}


			//		mAssert(applicationNumber, appNo, "Application Number does not match at Approval.  Actual   :"+applicationNumber+"    Expected   :+"+appNo);

			//String ApplicantNme = driver.findElement(By.id("applicantName")).getText();
			String ApplicantNme = driver.findElement(By.id(mGetPropertyFromFile("tp_AaAplcntNameAtApprovalid"))).getAttribute("value");
			System.out.println(ApplicantNme);

			//selecting all approval checkbox if visible
			if(	driver.findElements(By.id(mGetPropertyFromFile("tp_AaApprovalAllid"))).size() != 0)
			{
				mClick("id", mGetPropertyFromFile("tp_AaApprovalAllid"));
				mCustomWait(500);	
			}

			//sending Approval Remarks
			if(	driver.findElements(By.id(mGetPropertyFromFile("tp_AaAppApprovalRemarksid"))).size() != 0)
			{
				mSendKeys("id", mGetPropertyFromFile("tp_AaAppApprovalRemarksid"), mGetPropertyFromFile("tp_AaAppApprovalRemarksdata"));
				mCustomWait(500);
			}

			//Print Button
			mWaitForVisible("css", mGetPropertyFromFile("tp_AaAppApprovalSubBtnid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);

			mClick("css", mGetPropertyFromFile("tp_AaAppApprovalSubBtnid"));

			mWaitForVisible("id", mGetPropertyFromFile("tp_AaAppApprovalProcdBtnid"));
			mTakeScreenShot();

			String msg = mGetText("css", mGetPropertyFromFile("tp_AaMsgAtAppApprovalid"));
			//String msg = driver.findElement(By.cssSelector("div.msg-dialog-box.ok-msg > p")).getText();
			System.out.println(msg);
			mClick("id", mGetPropertyFromFile("tp_AaAppApprovalProcdBtnid"));
			mGenericWait();
			mChallanPDFReader();
			mGenericWait();
			mCustomWait(1000);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in ApplicationApproval script");
		}
	}

	//Application for building permission
	public void ApplctnForBuildinPermisn()
	{
		try
		{

			//Building Permission and Regulations Link
			mNavigation("tp_BuildingPermissionNRegulationLinkid", "tp_ApplicationForBuildingPermissionLinkid");
			mCustomWait(500);

			TpServiceName=mGetText("css", mGetPropertyFromFile("Servicenametextid"));
			ServiceNameforTownPlanning.add(TpServiceName.trim());
			System.out.println("Service name is" +ServiceNameforTownPlanning);

			//Nature Of Construction
			mWaitForVisible("id", mGetPropertyFromFile("tp_BprNatureOfConstructionid"));
			mSelectDropDown("id", mGetPropertyFromFile("tp_BprNatureOfConstructionid"), mGetPropertyFromFile("tp_BprNatureOfConstructiondata"));
			String tp_BprNatureOfConstruction=mCaptureSelectedValue("id", mGetPropertyFromFile("tp_BprNatureOfConstructionid"));
			NatureofConstr.add(tp_BprNatureOfConstruction);
			System.out.println("Nature of Construction in arraylist::"+NatureofConstr+"Nature of Construction Captured text is::"+tp_BprNatureOfConstruction);
			mAssert(tp_BprNatureOfConstruction, mGetPropertyFromFile("tp_BprNatureOfConstructiondata"), "Nature of Construction failed");
			//NatureofConstr.add(mGetPropertyFromFile("tp_BprNatureOfConstructiondata"));

			//case number search hyperlink
			mCustomWait(500);
			mWaitForVisible("linkText", mGetPropertyFromFile("tp_BprCaseNoSearchRdid"));
			mClick("linkText", mGetPropertyFromFile("tp_BprCaseNoSearchRdid"));

			searchByVariousCriteria(mGetPropertyFromFile("tp_BprSearchbyFNameid"),mGetPropertyFromFile("tp_BprSearchbyLNameid"),mGetPropertyFromFile("tp_BprSearchbyMNameid"),mGetPropertyFromFile("tp_BprSearchbyCaseid"),mGetPropertyFromFile("tp_BprUsageTypeid"),mGetPropertyFromFile("tp_BprCoverArInHecterid"),mGetPropertyFromFile("tp_BprMaujaNoid"),mGetPropertyFromFile("tp_BprPlotKhasaraNoid"),mGetPropertyFromFile("tp_BprManualRefNoid"));

			//General search Button retriving all data
			mWaitForVisible("name", mGetPropertyFromFile("tp_BprAplByCaseNoSearchid"));
			mClick("name", mGetPropertyFromFile("tp_BprAplByCaseNoSearchid"));


			//select radio icon
			mCustomWait(500);
			mWaitForVisible("name", mGetPropertyFromFile("tp_BprSelectApplicationRadioid"));
			mClick("name", mGetPropertyFromFile("tp_BprSelectApplicationRadioid"));
			mTakeScreenShot();

			//submit after selecting application
			mCustomWait(500);
			mWaitForVisible("xpath", mGetPropertyFromFile("tp_BprFancySubmitBtnid"));
			mClick("xpath", mGetPropertyFromFile("tp_BprFancySubmitBtnid"));

			//Sending Principal usage type
			mWaitForVisible("id", mGetPropertyFromFile("tp_BprprincipalUsageTypeid"));
			mSelectDropDown("id", mGetPropertyFromFile("tp_BprprincipalUsageTypeid"), mGetPropertyFromFile("tp_BprprincipalUsageTypedata"));
			String BprprincipalUsageType=mCaptureSelectedValue("id", mGetPropertyFromFile("tp_BprprincipalUsageTypeid"));
			PrincipalUsageType.add(BprprincipalUsageType);
			System.out.println("Princple usage type in arraylist::"+PrincipalUsageType+"Princple usage type Captured text is::"+BprprincipalUsageType);
			mAssert(BprprincipalUsageType, mGetPropertyFromFile("tp_BprprincipalUsageTypedata"), "Princple usage type failed Actual::"+BprprincipalUsageType+",Expected::"+mGetPropertyFromFile("tp_BprprincipalUsageTypedata"));


			//Sending Category of Building 
			mWaitForVisible("id", mGetPropertyFromFile("tp_BprBuildinCatid"));
			mSelectDropDown("id", mGetPropertyFromFile("tp_BprBuildinCatid"), mGetPropertyFromFile("tp_BprBuildinCatdata"));
			String tp_BprBuildinCat=mCaptureSelectedValue("id", mGetPropertyFromFile("tp_BprBuildinCatid"));
			CategoryofBuilding.add(tp_BprBuildinCat);
			System.out.println("Category of Building  in arraylist::"+CategoryofBuilding+ "Category of Building  Captured text is::"+tp_BprBuildinCat);
			mAssert(tp_BprBuildinCat, mGetPropertyFromFile("tp_BprBuildinCatdata"), "Category of Building  failed Actual::"+tp_BprBuildinCat+",Expected::"+mGetPropertyFromFile("tp_BprBuildinCatdata"));

			//sending area type
			mCustomWait(200);
			mWaitForVisible("id", mGetPropertyFromFile("tp_BprAreaTypeid"));
			mSelectDropDown("id", mGetPropertyFromFile("tp_BprAreaTypeid"), mGetPropertyFromFile("tp_BprAreaTypedata"));
			String tp_BprAreaType=mCaptureSelectedValue("id", mGetPropertyFromFile("tp_BprAreaTypeid"));
			Area.add(tp_BprAreaType);
			System.out.println("Area type in arraylist::"+Area+ "Area type  Captured text is::"+tp_BprAreaType);
			mAssert(tp_BprAreaType,mGetPropertyFromFile("tp_BprAreaTypedata"), "Area  failed Actual::"+tp_BprAreaType+",Expected::"+mGetPropertyFromFile("tp_BprAreaTypedata"));

			//Sending Plot width in meter
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprPlotWidthid"), mGetPropertyFromFile("tp_BprPlotWidthdata"));
			String BprPlotWidth=mGetText("id", mGetPropertyFromFile("tp_BprPlotWidthid"),"value");
			Bpr_PlotWidthavg.add(BprPlotWidth);
			System.out.println("Plot width in meter in arraylist::"+Bpr_PlotWidthavg+ "Plot width in meter Captured text is::"+BprPlotWidth);
			mAssert(BprPlotWidth,mGetPropertyFromFile("tp_BprPlotWidthdata"), "Failed ::: Actual Plot width in meter :"+BprPlotWidth+"  Expected Plot width in meter :"+mGetPropertyFromFile("tp_BprPlotWidthdata"));

			//Verifying Plot width in Feet and output
			mCustomWait(200);
			String PlotWidthInfeet=mGetText("id", mGetPropertyFromFile("tp_BprPlotWidthInFtid"),"value");
			System.out.println(PlotWidthavgft.add(PlotWidthInfeet));
			PlotWidthavgft.add(PlotWidthInfeet);
			System.out.println("Plot width in Feet in arraylist::"+PlotWidthavgft+ "Plot width in Feet Captured text is::"+PlotWidthInfeet);
			mAssert(PlotWidthInfeet, mGetPropertyFromFile("tp_BprPlotWidthInFtdata"), "Failed ::: Actual Plot width in Feet :"+PlotWidthInfeet+"  Expected Plot width in Feet :"+mGetPropertyFromFile("tp_BprPlotWidthInFtdata"));

			//Sending Height of Building in Meter
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprBuildingHeightid"), mGetPropertyFromFile("tp_BprBuildingHeightdata"));
			String tp_BprBuildingHeight=mGetText("id", mGetPropertyFromFile("tp_BprBuildingHeightid"),"value");
			HeightofBuilding.add(tp_BprBuildingHeight);
			System.out.println("Height of Building in Meter in arraylist::"+HeightofBuilding+ "Height of Building in Meter Captured text is::"+tp_BprBuildingHeight);
			mAssert(tp_BprBuildingHeight,mGetPropertyFromFile("tp_BprBuildingHeightdata"), "Height of Building in Meter failed Actual Plot width in Feet :"+tp_BprBuildingHeight+"  Expected Plot width in Feet :"+mGetPropertyFromFile("tp_BprBuildingHeightdata"));

			//Verifying Height of Building in Feet and output
			String BuildHeightInfeet=mGetText("id", mGetPropertyFromFile("tp_BprBuildingHeightInFtid"),"value");
			HeightofBuildingft.add(BuildHeightInfeet);
			System.out.println("Height of Building in Feet in arraylist::"+HeightofBuildingft+ "Height of Building in Feet Captured text is::"+BuildHeightInfeet);
			mAssert(BuildHeightInfeet, mGetPropertyFromFile("tp_BprBuildingHeightInFtdata"), "Height of Building in Feet is Failed Actual :"+BuildHeightInfeet+"  Expected  :"+mGetPropertyFromFile("tp_BprBuildingHeightInFtdata"));
			mCustomWait(200);
			mscroll(0, 300);

			//Sending Plot Length in meter
			mSendKeys("id", mGetPropertyFromFile("tp_BprPlotLengthid"), mGetPropertyFromFile("tp_BprPlotLengthdata"));
			String tp_BprPlotLength=mGetText("id", mGetPropertyFromFile("tp_BprPlotLengthid"),"value");
			PlotLengthAvg.add(tp_BprPlotLength);
			System.out.println("Plot Length in meter in arraylist::"+PlotLengthAvg+ "Plot Length in meter Captured text is::"+tp_BprPlotLength);
			mAssert(tp_BprPlotLength,mGetPropertyFromFile("tp_BprPlotLengthdata"), "Plot Length in meter failed Actual :"+tp_BprPlotLength+"  Expected  :"+mGetPropertyFromFile("tp_BprPlotLengthdata"));

			//Verifying Plot Length in Feet and output
			String PlotLengthInfeet=mGetText("id", mGetPropertyFromFile("tp_BprPlotLengthInFtid"),"value");
			PlotLengthAvgft.add(PlotLengthInfeet);
			System.out.println("Plot Length in Feet in arraylist::"+PlotLengthAvgft+ "Plot Length in Feet Captured text is::"+PlotLengthInfeet);
			mAssert(PlotLengthInfeet, mGetPropertyFromFile("tp_BprPlotLengthInFtdata"), "Plot Length in Feet Failed Actual :"+PlotLengthInfeet+"  Expected  :"+mGetPropertyFromFile("tp_BprPlotLengthInFtdata"));


			//Sending Plot Area in meter
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprPlotAreaid"), mGetPropertyFromFile("tp_BprPlotAreadata"));
			String tp_BprPlotArea=mGetText("id", mGetPropertyFromFile("tp_BprPlotAreaid"),"value");
			PlotArea.add(tp_BprPlotArea);
			System.out.println("Plot Area in meter in arraylist::"+PlotArea+ "Plot Area in meter Captured text is::"+tp_BprPlotArea);
			mAssert(tp_BprPlotArea,mGetPropertyFromFile("tp_BprPlotAreadata"), "Plot Area in meter failed Actual :"+tp_BprPlotArea+"  Expected  :"+mGetPropertyFromFile("tp_BprPlotAreadata"));

			//Verifying Plot Area in Feet and output
			String PlotAreaInfeet=mGetText("id", mGetPropertyFromFile("tp_BprPlotAreaInFtid"),"value");
			bprPlotAreaFt.add(PlotAreaInfeet);
			System.out.println("Plot Area in Feet in arraylist::"+bprPlotAreaFt+ "Plot Area in Feet Captured text is::"+PlotAreaInfeet);
			mAssert(PlotAreaInfeet, mGetPropertyFromFile("tp_BprPlotAreaInFtdata"), "Plot Area in Feet Failed Actual :"+PlotAreaInfeet+"  Expected  :"+mGetPropertyFromFile("tp_BprPlotAreaInFtdata"));

			//Sending Holding Rights 
			mCustomWait(200);
			mWaitForVisible("id", mGetPropertyFromFile("tp_BprHoldingRightsid"));
			mSelectDropDown("id", mGetPropertyFromFile("tp_BprHoldingRightsid"), mGetPropertyFromFile("tp_BprHoldingRightsdata"));
			String BprHoldingRights=mCaptureSelectedValue("id", mGetPropertyFromFile("tp_BprHoldingRightsid"));
			HoldingRights.add(BprHoldingRights);
			System.out.println("Holding Rights in arraylist::"+HoldingRights+ "Holding Rights Captured text is::"+BprHoldingRights);
			mAssert(BprHoldingRights, mGetPropertyFromFile("tp_BprHoldingRightsdata"), "Holding Rights Failed Actual::"+BprHoldingRights+",Expected::"+mGetPropertyFromFile("tp_BprHoldingRightsdata"));

			//Sending Construction cost : Modified on 27/02/2017 
			mCustomWait(200);
			mWaitForVisible("id", mGetPropertyFromFile("tp_BprConstructionCostid"));
			mSendKeys("id", mGetPropertyFromFile("tp_BprConstructionCostid"), mGetPropertyFromFile("tp_BprConstructionCostdata"));
			String BprConstructionCost=mGetText("id", mGetPropertyFromFile("tp_BprConstructionCostid"),"value");
			int y = Integer.parseInt(BprConstructionCost);
			Bpr_ConstructionCost.add(BprConstructionCost);
			System.out.println("Construction cost in arraylist::"+Bpr_ConstructionCost+ "Holding Rights Captured text is::"+BprConstructionCost);
			mAssert(BprConstructionCost, mGetPropertyFromFile("tp_BprConstructionCostdata"), "Construction Cost Failed Actual::"+BprConstructionCost+",Expected::"+mGetPropertyFromFile("tp_BprConstructionCostdata"));


			// Downloading GIS from Linktext
			/*mCustomWait(200);
			mWaitForVisible("linkText", mGetPropertyFromFile("tp_BprGISRoadDownloadid"));
			mClick("linkText", mGetPropertyFromFile("tp_BprGISRoadDownloadid"));
			mCustomWait(1000);*/

			//Sending GIS Road ID 
			mSendKeys("id", mGetPropertyFromFile("tp_BprGISRoadid"), mGetPropertyFromFile("tp_BprGISRoaddata"));
			String tp_BprGISRoad=mGetText("id", mGetPropertyFromFile("tp_BprGISRoadid"),"value");
			GISRoadId.add(tp_BprGISRoad);
			System.out.println("GIS Road ID  in arraylist::"+GISRoadId+ "GIS Road ID  Captured text is::"+tp_BprGISRoad);
			mAssert(tp_BprGISRoad,mGetPropertyFromFile("tp_BprGISRoaddata"), "GIS Road failed Actual::"+tp_BprGISRoad+",Expected::"+mGetPropertyFromFile("tp_BprGISRoaddata"));


			//Sending Existing road Width in Meter
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprExtRoadWidthid"), mGetPropertyFromFile("tp_BprExtRoadWidthdata"));
			String BprExistingRoadWidth=mGetText("id", mGetPropertyFromFile("tp_BprExtRoadWidthid"),"value");
			ExistingRoadWidth.add(BprExistingRoadWidth);
			System.out.println("Existing road Width in Meter in arraylist::"+ExistingRoadWidth+ "Existing road Width in Meter Captured text is::"+BprExistingRoadWidth);
			mAssert(BprExistingRoadWidth,mGetPropertyFromFile("tp_BprExtRoadWidthdata"), "Existing road Width in Meter failed Actual::"+BprExistingRoadWidth+",Expected::"+mGetPropertyFromFile("tp_BprExtRoadWidthdata"));



			//Verifying Existing road Width in Feet
			String ExtRoadWidth=mGetText("id", mGetPropertyFromFile("tp_BprExtRoadWidthInFtid"),"value");
			System.out.println(ExistingRoadWidthft.add(ExtRoadWidth));
			mAssert(ExtRoadWidth, mGetPropertyFromFile("tp_BprExtRoadWidthInFtdata"), "Actual :"+ExtRoadWidth+"  Expected  :"+mGetPropertyFromFile("tp_BprExtRoadWidthInFtdata"));
			System.out.println("Capture Existing road Width in Feet is :"+ExtRoadWidth+" and Expected Existing road Width in Feet is :"+mGetPropertyFromFile("tp_BprExtRoadWidthInFtdata"));	

			//Sending Road Width as per Master Plan in Meter
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprRoadWidthAsprMastrPlnid"), mGetPropertyFromFile("tp_BprRoadWidthAsprMastrPlndata"));
			String tp_BprRoadWidthAsprMastrPln=mGetText("id", mGetPropertyFromFile("tp_BprRoadWidthAsprMastrPlnid"),"value");
			System.out.println(RWidthasperMasterPlan.add(tp_BprRoadWidthAsprMastrPln));
			mAssert(tp_BprRoadWidthAsprMastrPln,mGetPropertyFromFile("tp_BprRoadWidthAsprMastrPlndata"), "Road Width as per Master Plan in Meter failed");
			//RWidthasperMasterPlan.add(mGetPropertyFromFile("tp_BprRoadWidthAsprMastrPlndata"));

			//Verifying Road Width as per Master Plan in Feet
			String RoadWidthAsprMastrPln=mGetText("id", mGetPropertyFromFile("tp_BprRoadWidthAsprMastrPlnInFtid"),"value");
			System.out.println("Capture Road Width as per Master Plan in feet is :"+RoadWidthAsprMastrPln);
			System.out.println(RWidthasperMasterPlanft.add(RoadWidthAsprMastrPln));
			mAssert(RoadWidthAsprMastrPln, mGetPropertyFromFile("tp_BprRoadWidthAsprMastrPlnInFTdata"), "Actual :"+RoadWidthAsprMastrPln+"  Expected  :"+mGetPropertyFromFile("tp_BprRoadWidthAsprMastrPlnInFTdata"));
			System.out.println("Capture Road Width as per Master Plan in feet is :"+RoadWidthAsprMastrPln+" and Expected Road Width as per Master Plan in feet is  :"+mGetPropertyFromFile("tp_BprRoadWidthAsprMastrPlnInFTdata"));			

			//Checking that is Area left for Road Widening 
			if(mGetPropertyFromFile("tp_BprArearLeftFrRoadWideningYNdata").equalsIgnoreCase("Yes"))
			{
				if ( !driver.findElement(By.id("roadWidth")).isSelected() )
				{
					driver.findElement(By.id("roadWidth")).click();

					mSendKeys("id", mGetPropertyFromFile("tp_BprArearLeftFrRoadWideningid"), mGetPropertyFromFile("tp_BprArearLeftFrRoadWideningdata"));
					String tp_BprArearLeftFrRoadWidening=mGetText("id", mGetPropertyFromFile("tp_BprArearLeftFrRoadWideningid"),"value");
					System.out.println(ArealeftforRoadWidening.add(tp_BprArearLeftFrRoadWidening));
					mAssert(tp_BprArearLeftFrRoadWidening,mGetPropertyFromFile("tp_BprArearLeftFrRoadWideningdata"), "Area left for Road Widening failed");
					//ArealeftforRoadWidening.add(mGetPropertyFromFile("tp_BprArearLeftFrRoadWideningdata"));

					String ArearLeftFrRoadWindringft=mGetText("id", mGetPropertyFromFile("tp_BprArearLeftFrRoadWideningid"),"value");
					ArealeftforRoadWideningft.add(ArearLeftFrRoadWindringft);
					mAssert(ArearLeftFrRoadWindringft, mGetPropertyFromFile("tp_BprArearLeftFrRoadWideningInFtdata"), "Actual :"+ArearLeftFrRoadWindringft+"  Expected  :"+mGetPropertyFromFile("tp_BprArearLeftFrRoadWindringInFtdata"));
					System.out.println("Capture Road Width as per Master Plan in feet is :"+ArearLeftFrRoadWindringft+" and Expected Road Width as per Master Plan in feet is  :"+mGetPropertyFromFile("tp_BprArearLeftFrRoadWideningInFtdata"));	

				}
			}
			mCustomWait(500);
			//Checking that is Parking Area
			if(mGetPropertyFromFile("tp_BprParkingAreaYNdata").equalsIgnoreCase("Yes")){
				if ( !driver.findElement(By.id("prAr")).isSelected() )
				{
					driver.findElement(By.id("prAr")).click();

					mSendKeys("id", mGetPropertyFromFile("tp_BprParkingDetailAreaid"), mGetPropertyFromFile("tp_BprParkingDetailAreadata"));
					String tp_BprParkingDetailArea=mGetText("id", mGetPropertyFromFile("tp_BprParkingDetailAreaid"),"value");
					System.out.println(ParkingArea.add(tp_BprParkingDetailArea));
					mAssert(tp_BprParkingDetailArea,mGetPropertyFromFile("tp_BprParkingDetailAreadata"), "Parking Area failed");
					//ParkingArea.add(mGetPropertyFromFile("tp_BprParkingDetailAreadata"));

					String ParkingDetailArea=mGetText("id", mGetPropertyFromFile("tp_BprParkingDetailAreaInFtid"),"value");
					System.out.println(ParkingAreaft.add(ParkingDetailArea));
					mAssert(ParkingDetailArea, mGetPropertyFromFile("tp_BprParkingDetailAreaInFtdata"), "Actual :"+RoadWidthAsprMastrPln+"  Expected  :"+mGetPropertyFromFile("tp_BprParkingDetailAreaInFtdata"));
					System.out.println("Capture Parking Area in feet is :"+ParkingDetailArea+" and Expected Parking Area in feet is  :"+mGetPropertyFromFile("tp_BprParkingDetailAreaInFtdata"));	

				}
			}
			mGenericWait();
			//Checking Whether falls in airport zone ?
			if(mGetPropertyFromFile("tp_BprNearRunWayYNdata").equalsIgnoreCase("Yes"))
			{
				if ( !driver.findElement(By.id("airZone")).isSelected() )
				{
					driver.findElement(By.id("airZone")).click();

					mSendKeys("id", mGetPropertyFromFile("tp_BprNearRunWayDistanceid"), mGetPropertyFromFile("tp_BprNearRunWayDistancedata"));
					String tp_BprNearRunWayDistance=mGetText("id", mGetPropertyFromFile("tp_BprNearRunWayDistanceid"),"value");
					System.out.println(DistfrmnearestrunwayEnd.add(tp_BprNearRunWayDistance));
					mAssert(tp_BprNearRunWayDistance,mGetPropertyFromFile("tp_BprNearRunWayDistancedata"), "Runway Disstance data failed");
					//DistfrmnearestrunwayEnd.add(mGetPropertyFromFile("tp_BprNearRunWayDistancedata"));

					/*String PakingDetailArea=mGetText("id", mGetPropertyFromFile("tp_BprNearRunWayDistanceInFtid"),"value");
					DistfrmnearestrunwayEnd.add(BprDistfrmnearestrunwayEnd);
					mAssert(PakingDetailArea, mGetPropertyFromFile("tp_BprNearRunWayDistanceInFtdata"), "Actual :"+RoadWidthAsprMastrPln+"  Expected  :"+mGetPropertyFromFile("tp_BprNearRunWayDistanceInFtdata"));
					System.out.println("Capture Distance from nearest runway End in feet is :"+PakingDetailArea+" and Expected Distance from nearest runway End in feet is  :"+mGetPropertyFromFile("tp_BprNearRunWayDistanceInFtdata"));	
					 */


					mSendKeys("id", mGetPropertyFromFile("tp_BprInnBoundryDistanceid"), mGetPropertyFromFile("tp_BprInnBoundryDistancedata"));
					String tp_BprInnBoundryDistance=mGetText("id", mGetPropertyFromFile("tp_BprInnBoundryDistanceid"),"value");
					System.out.println(DistofInnerboundary.add(tp_BprInnBoundryDistance));
					mAssert(tp_BprInnBoundryDistance,mGetPropertyFromFile("tp_BprInnBoundryDistancedata"), "Runway Disstance data failed");
					//DistofInnerboundary.add(mGetPropertyFromFile("tp_BprInnBoundryDistancedata"));
					/*
					String InnBoundryDistance=mGetText("id", mGetPropertyFromFile("tp_BprInnBoundryDistanceInFtid"),"value");
					mAssert(InnBoundryDistance, mGetPropertyFromFile("tp_BprInnBoundryDistanceInFtdata"), "Actual :"+RoadWidthAsprMastrPln+"  Expected  :"+mGetPropertyFromFile("tp_BprInnBoundryDistanceInFtdata"));
					System.out.println("Capture Distance of the Inner boundary of Transitional Area in feet is :"+InnBoundryDistance+" and Expected Distance of the Inner boundary of Transitional Area in feet is  :"+mGetPropertyFromFile("tp_BprInnBoundryDistanceInFtdata"));	*/
				}
			}
			mTakeScreenShot();
			mCustomWait(200);
			mscroll(0, 300);
			mCustomWait(200);




			String Floor = mGetPropertyFromFile("tp_BprAddFloorNodata");
			String BuiltUpArea = mGetPropertyFromFile("tp_BprtppSplitAreadata");
			String BuiltUpAreaInFt = mGetPropertyFromFile("tp_BprtppSplitAreaInFtdata");
			String TypeOfOccupancy = mGetPropertyFromFile("tp_BprtppUtp1data");

			ArrayList FloorList = new ArrayList(Arrays.asList(Floor.split(",")));
			ArrayList BuiltUpAreaList = new ArrayList(Arrays.asList(BuiltUpArea.split(",")));
			ArrayList BuiltUpAreaInFtList = new ArrayList(Arrays.asList(BuiltUpAreaInFt.split(",")));
			ArrayList TypeOfOccupancyList = new ArrayList(Arrays.asList(TypeOfOccupancy.split(",")));

			double sum1= 0;

			for (int i=0; i<FloorList.size();i++)
			{

				//add Floor Details Link
				mCustomWait(750);
				mWaitForVisible("linkText", mGetPropertyFromFile("tp_BprAddFloorDetailsLinkid"));
				mClick("linkText", mGetPropertyFromFile("tp_BprAddFloorDetailsLinkid"));

				mCustomWait(200);
				mWaitForVisible("id", mGetPropertyFromFile("tp_BprAddFloorNoid"));
				mSelectDropDown("id", mGetPropertyFromFile("tp_BprAddFloorNoid"), FloorList.get(i).toString());
				String tp_BprAddFloorNo=mCaptureSelectedValue("id", mGetPropertyFromFile("tp_BprAddFloorNoid"));
				BPR_FloorNo.add(tp_BprAddFloorNo);


				mCustomWait(200);
				mSendKeys("id", mGetPropertyFromFile("tp_BprtppSplitAreaid"), BuiltUpAreaList.get(i).toString());
				String BuiltupArea=mGetText("id", mGetPropertyFromFile("tp_BprtppSplitAreaid"));
				BPR_Builtuparea.add(BuiltupArea);

				mCustomWait(200);
				mWaitForVisible("id", mGetPropertyFromFile("tp_BprtppUtp1id"));
				mSelectDropDown("id", mGetPropertyFromFile("tp_BprtppUtp1id"), TypeOfOccupancyList.get(i).toString());
				String tp_BprUsagetype=mCaptureSelectedValue("id", mGetPropertyFromFile("tp_BprtppUtp1id"));
				BPR_UsageType.add(tp_BprUsagetype);

				//submit
				mCustomWait(200);
				mWaitForVisible("xpath", mGetPropertyFromFile("tp_BprAddFloorSubBtnid"));
				mClick("xpath", mGetPropertyFromFile("tp_BprAddFloorSubBtnid"));

				//Floor details Pop up msg
				mWaitForVisible("css", mGetPropertyFromFile("tp_BprAddFloorFancyCloseid"));
				mCustomWait(200);

				//Asserting Message on Popup
				String msgAtFloorDetails = mGetText("css", mGetPropertyFromFile("tp_BprAddFloorFancyMsgid"));

				mCustomWait(200);
				System.out.println(msgAtFloorDetails);
				mAssert(msgAtFloorDetails, mGetPropertyFromFile("tp_BprAddFloorFancyMsgdata"), "Actual   :"+msgAtFloorDetails+"  Expected   :"+mGetPropertyFromFile("tp_BprAddFloorFancyMsgdata"));

				//Closing Message box
				mCustomWait(300);
				mClick("css", mGetPropertyFromFile("tp_BprAddFloorFancyCloseid"));
				mCustomWait(300);

				/*//Asserting Total Built Up Area in Grid Value 
				String TotalBuiltUpArGrid=mGetText("css", mGetPropertyFromFile("tp_BprTotalBuiltUpArGrid"),"value");
				mAssert(TotalBuiltUpArGrid, mGetPropertyFromFile("tp_BprTotalBuiltUpArGrdata"), "Actual   :"+TotalBuiltUpArGrid+"  Expected   :"+mGetPropertyFromFile("tp_BprTotalBuiltUpArGrdata"));
				System.out.println("Capture Total Built Up Area in Grid Value is :"+TotalBuiltUpArGrid+" and Expected Total Built Up Area in Grid Value from Excel is  :"+mGetPropertyFromFile("tp_BprTotalBuiltUpArGrdata"));	*/


			}


			String BuiltUpAreaInMt=mGetText("id", mGetPropertyFromFile("tp_BprBuiltUpAreaInMtid"),"value");
			BPR_BuiltupareaSqMt2.add(BuiltUpAreaInMt);
			mAssert(BuiltUpAreaInMt, mGetPropertyFromFile("tp_BprBuiltUpAreaInMtdata"), "Actual Built Up area in meter  :"+BuiltUpAreaInMt+"  Expected  Built Up area in meter :"+mGetPropertyFromFile("tp_BprBuiltUpAreaInMtdata"));
			System.out.println("Capture Total built up area in Square Meter is :"+BuiltUpAreaInMt);	


			String BuiltUpAreaInFtRead=mGetText("id", mGetPropertyFromFile("tp_BprBuiltUpAreaInFtid"),"value");
			BPR_BuiltupareaSqft2.add(BuiltUpAreaInFtRead);
			mAssert(BuiltUpAreaInFtRead, mGetPropertyFromFile("tp_BprBuiltUpAreaInFtdata"), "Actual Built Up area in feet(Read Only/Autocalculated) is  :"+BuiltUpAreaInFtRead+"  Expected Built Up area in feet(Read Only/Autocalculated)  :"+mGetPropertyFromFile("tp_BprBuiltUpAreaInFtdata"));
			System.out.println("Capture Total Built Up Area feet in Read only field is :"+BuiltUpAreaInFtRead);	
			mCustomWait(300);

			//No Of Floor reading
			String Bpr_NoofFloorsinform=mGetText("id", mGetPropertyFromFile("tp_BprNoOfFloorid"),"value");
			BPR_NoofFloors.add(Bpr_NoofFloorsinform);
			//mAssert(RoadWidthAsprMastrPln, mGetPropertyFromFile("tp_BprRoadWidthAsprMastrPlnInFTdata"), "Actual :"+RoadWidthAsprMastrPln+"  Expected  :"+mGetPropertyFromFile("tp_BprRoadWidthAsprMastrPlnInFTdata"));
			System.out.println("No of of Floor is auto read only field :"+BPR_NoofFloors);		

			// View permissible FAR in new window
			mCustomWait(200);
			mWaitForVisible("linkText", mGetPropertyFromFile("tp_BprFarViewLinkid"));
			mClick("linkText", mGetPropertyFromFile("tp_BprFarViewLinkid"));

			mCustomWait(1500);
			ArrayList<String> tabs;
			tabs = new ArrayList<String>(driver.getWindowHandles());

			driver.switchTo().window(tabs.get(2));
			driver.manage().window().maximize();
			mCustomWait(1000);
			driver.close();

			mGenericWait();
			driver.switchTo().window(tabs.get(1));

			//Sending FAR applied for Details
			mCustomWait(200);
			mWaitForVisible("id", mGetPropertyFromFile("tp_BprFarAppliedid"));
			mSendKeys("id", mGetPropertyFromFile("tp_BprFarAppliedid"), mGetPropertyFromFile("tp_BprFarApplieddata"));
			String tp_BprFarApplied=mGetText("id", mGetPropertyFromFile("tp_BprFarAppliedid"), "value");
			mAssert(tp_BprFarApplied, mGetPropertyFromFile("tp_BprFarApplieddata"), "FAR applied for Details Failed");
			FARAppliedfor.add(tp_BprFarApplied);
			System.out.println("FAR applied for Details"+tp_BprFarApplied);


			//Sending Permissible FAR 
			mCustomWait(200);
			mWaitForVisible("id", mGetPropertyFromFile("tp_BprFarPermissibleid"));
			mSendKeys("id", mGetPropertyFromFile("tp_BprFarPermissibleid"), mGetPropertyFromFile("tp_BprFarPermissibledata"));
			String tp_BprFarPermissible=mGetText("id", mGetPropertyFromFile("tp_BprFarPermissibleid"), "value");
			mAssert(tp_BprFarPermissible, mGetPropertyFromFile("tp_BprFarPermissibledata"), "Permissible FAR  Failed");
			PermissibleFAR.add(tp_BprFarPermissible);
			//PermissibleFAR.add(mGetPropertyFromFile("tp_BprFarPermissibledata"));

			mCustomWait(200);
			mscroll(0, 300);
			mCustomWait(200);

			//Sending Location Of North Plot 
			mWaitForVisible("id", mGetPropertyFromFile("tp_BprNorthPlotid"));
			mSendKeys("id", mGetPropertyFromFile("tp_BprNorthPlotid"), mGetPropertyFromFile("tp_BprNorthPlotdata"));
			String tp_BprNorthPlot=mGetText("id", mGetPropertyFromFile("tp_BprNorthPlotid"), "value");
			mAssert(tp_BprNorthPlot, mGetPropertyFromFile("tp_BprNorthPlotdata"), " Location Of North Plot  Failed");
			LocPlotNorth.add(tp_BprNorthPlot);
			//LocPlotNorth.add(mGetPropertyFromFile("tp_BprprincipalUsageTypedata"));

			//Sending Location Of East Plot
			mWaitForVisible("id", mGetPropertyFromFile("tp_BprEastPlotid"));
			mSendKeys("id", mGetPropertyFromFile("tp_BprEastPlotid"), mGetPropertyFromFile("tp_BprEastPlotdata"));
			String tp_BprEastPlot=mGetText("id", mGetPropertyFromFile("tp_BprEastPlotid"), "value");
			mAssert(tp_BprEastPlot, mGetPropertyFromFile("tp_BprEastPlotdata"), "Location Of East Plot  Failed");
			LocPlotEast.add(tp_BprEastPlot);
			//LocPlotEast.add(mGetPropertyFromFile("tp_BprEastPlotdata"));


			//Sending Location Of South Plot
			mCustomWait(200);
			mWaitForVisible("id", mGetPropertyFromFile("tp_BprSouthPlotid"));
			mSendKeys("id", mGetPropertyFromFile("tp_BprSouthPlotid"), mGetPropertyFromFile("tp_BprSouthPlotdata"));
			String tp_BprSouthPlot=mGetText("id", mGetPropertyFromFile("tp_BprSouthPlotid"), "value");
			mAssert(tp_BprSouthPlot, mGetPropertyFromFile("tp_BprSouthPlotdata"), "Location Of South Plot Failed");
			LocPlotSouth.add(tp_BprSouthPlot);
			//LocPlotSouth.add(mGetPropertyFromFile("tp_BprSouthPlotdata"));

			//Sending Location Of West Plot
			mCustomWait(200);
			mWaitForVisible("id", mGetPropertyFromFile("tp_BprWestPlotid"));
			mSendKeys("id", mGetPropertyFromFile("tp_BprWestPlotid"), mGetPropertyFromFile("tp_BprWestPlotdata"));
			String tp_BprWestPlot=mGetText("id", mGetPropertyFromFile("tp_BprWestPlotid"), "value");
			mAssert(tp_BprWestPlot, mGetPropertyFromFile("tp_BprWestPlotdata"), "Location Of West Plot Failed");
			LocPlotWest.add(tp_BprWestPlot);
			//LocPlotWest.add(mGetPropertyFromFile("tp_BprWestPlotdata"));

			// View permissible FAR in new window
			mCustomWait(200);
			mWaitForVisible("linkText", mGetPropertyFromFile("tp_BprViewPerSetBackid"));
			mClick("linkText", mGetPropertyFromFile("tp_BprViewPerSetBackid"));

			mCustomWait(200);
			ArrayList<String> tabs1;
			tabs1 = new ArrayList<String>(driver.getWindowHandles());

			driver.switchTo().window(tabs1.get(2));
			driver.manage().window().maximize();
			mCustomWait(1500);
			driver.close();
			mCustomWait(200);
			driver.switchTo().window(tabs1.get(1));

			//Sending Applied Avg. Depth of Plot in meter
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprAvgDepthid"), mGetPropertyFromFile("tp_BprAvgDepthdata"));
			String tp_BprAvgDepth=mGetText("id", mGetPropertyFromFile("tp_BprAvgDepthid"), "value");
			mAssert(tp_BprAvgDepth, mGetPropertyFromFile("tp_BprAvgDepthdata"), "Applied Avg. Depth of Plot in meter Failed");
			AvgDepthofPlot.add(tp_BprAvgDepth);
			//AvgDepthofPlot.add(BprAvgDepthofPlot);

			//Verifying Applied Avg. Depth of Plot in Feet
			String AvgDepthInFt=mGetText("id", mGetPropertyFromFile("tp_BprAvgDepthInFtid"),"value");
			System.out.println(AvgDepthofPlotft.add(AvgDepthInFt));
			mAssert(AvgDepthInFt, mGetPropertyFromFile("tp_BprAvgDepthInFtdata"), "Average depth conversion does not match "+"Actual :"+AvgDepthInFt+"  Expected  :"+mGetPropertyFromFile("tp_BprAvgDepthInFtdata"));
			System.out.println("Capture Applied Avg. Depth of Plot in feet is :"+AvgDepthInFt+" and Expected Applied Avg. Depth of Plot in feet is  :"+mGetPropertyFromFile("tp_BprAvgDepthInFtdata"));	

			//Sending Applied Avg. Width of Plot in Meter
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprAvgWidthid"), mGetPropertyFromFile("tp_BprAvgWidthdata"));
			String tp_BprAvgWidth=mGetText("id", mGetPropertyFromFile("tp_BprAvgWidthid"), "value");
			mAssert(tp_BprAvgWidth, mGetPropertyFromFile("tp_BprAvgWidthdata"), "Applied Avg. Width of Plot in Meter Failed");
			System.out.println(AvgWidthofPlot.add(tp_BprAvgWidth));


			//Verifying Applied Avg. Width of Plot in Feet
			String AvgWidthInFt=mGetText("id", mGetPropertyFromFile("tp_BprAvgWidthInFtid"),"value");
			AvgWidthofPlotft.add(AvgWidthInFt);
			mAssert(AvgWidthInFt, mGetPropertyFromFile("tp_BprAvgWidthInFtdata"), "Actual :"+AvgWidthInFt+"  Expected  :"+mGetPropertyFromFile("tp_BprAvgWidthInFtdata"));
			System.out.println("Capture Applied Avg. Width of Plot in feet is :"+AvgWidthInFt+" and Expected Applied Avg. Width of Plot in feet is  :"+mGetPropertyFromFile("tp_BprAvgWidthInFtdata"));	

			//Sending Applied Front Set Back in Meter
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprFrontSetBackid"), mGetPropertyFromFile("tp_BprFrontSetBackdata"));
			String tp_BprFrontSetBack=mGetText("id", mGetPropertyFromFile("tp_BprFrontSetBackid"), "value");
			mAssert(tp_BprFrontSetBack, mGetPropertyFromFile("tp_BprFrontSetBackdata"), "Applied Avg. Width of Plot in Meter Failed");
			System.out.println(FrontSetBackApplied.add(tp_BprFrontSetBack));

			//Verifying Applied Avg. Width of Plot in Feet
			String FrontSetBackInFt=mGetText("id", mGetPropertyFromFile("tp_BprFrontSetBackInFtid"),"value");
			System.out.println(FrontSetBackAppliedft.add(FrontSetBackInFt));
			mAssert(FrontSetBackInFt, mGetPropertyFromFile("tp_BprFrontSetBackInFtdata"), "Actual :"+FrontSetBackInFt+"  Expected  :"+mGetPropertyFromFile("tp_BprFrontSetBackInFtdata"));
			System.out.println("Capture Applied Avg. Width of Plot in feet is :"+FrontSetBackInFt+" and Expected Applied Avg. Width of Plot in feet is  :"+mGetPropertyFromFile("tp_BprFrontSetBackInFtdata"));	

			/*	//Sending Permissible Front Set Back in Meter
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprFrontSetBackDetailid"), mGetPropertyFromFile("tp_BprFrontSetBackDetaildata"));
			String tp_BprFrontSetBackDetail=mGetText("id", mGetPropertyFromFile("tp_BprFrontSetBackDetailid"), "value");
			mAssert(tp_BprFrontSetBackDetail, mGetPropertyFromFile("tp_BprFrontSetBackDetaildata"), "Applied Avg. Width of Plot in Meter Failed");
			System.out.println(FrontSetBackPermissible.add(tp_BprFrontSetBackDetail));


			//Verifying Permissible Front Set Back in Feet
			String FrontSetBackDetailInFt=mGetText("id", mGetPropertyFromFile("tp_BprFrontSetBackDetailInFtid"),"value");
			System.out.println(FrontSetBackPermissibleft.add(FrontSetBackDetailInFt));
			mAssert(FrontSetBackDetailInFt, mGetPropertyFromFile("tp_BprFrontSetBackDetailInFtdata"), "Actual :"+FrontSetBackDetailInFt+"  Expected  :"+mGetPropertyFromFile("tp_BprFrontSetBackDetailInFtdata"));
			System.out.println("Capture Applied Avg. Width of Plot in feet is :"+FrontSetBackDetailInFt+" and Expected Applied Avg. Width of Plot in feet is  :"+mGetPropertyFromFile("tp_BprFrontSetBackDetailInFtdata"));	*/


			//Sending Applied Left Set Back in Meter
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprLeftSideSetBackid"), mGetPropertyFromFile("tp_BprLeftSideSetBackdata"));
			String tp_BprLeftSideSetBack=mGetText("id", mGetPropertyFromFile("tp_BprLeftSideSetBackid"), "value");
			mAssert(tp_BprLeftSideSetBack, mGetPropertyFromFile("tp_BprLeftSideSetBackdata"), "Applied Avg. Width of Plot in Meter Failed");
			System.out.println(LeftSetBackApplied.add(tp_BprLeftSideSetBack));


			//Verifying Applied Left Set Back in Feet
			String LeftSideSetBackInFt=mGetText("id", mGetPropertyFromFile("tp_BprLeftSideSetBackInFtid"),"value");
			System.out.println(LeftSetBackAppliedft.add(LeftSideSetBackInFt));
			mAssert(LeftSideSetBackInFt, mGetPropertyFromFile("tp_BprLeftSideSetBackInFtdata"), "Actual :"+LeftSideSetBackInFt+"  Expected  :"+mGetPropertyFromFile("tp_BprLeftSideSetBackInFtdata"));
			System.out.println("Capture Applied Avg. Width of Plot in feet is :"+LeftSideSetBackInFt+" and Expected Applied Avg. Width of Plot in feet is  :"+mGetPropertyFromFile("tp_BprLeftSideSetBackInFtdata"));	

			/*//Sending Permissible Left Set Back in Meter
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprLeftSideSetBackDetailid"), mGetPropertyFromFile("tp_BprLeftSideSetBackDetaildata"));
			String tp_BprLeftSideSetBackDetail=mGetText("id", mGetPropertyFromFile("tp_BprLeftSideSetBackDetailid"), "value");
			mAssert(tp_BprLeftSideSetBackDetail, mGetPropertyFromFile("tp_BprLeftSideSetBackDetaildata"), "Applied Avg. Width of Plot in Meter Failed");
			System.out.println(LeftSetBackPermissible.add(tp_BprLeftSideSetBackDetail));*/



			/*//Verifying Permissible Left Set Back in Feet
			String LeftSideSetBackDetailInFt=mGetText("id", mGetPropertyFromFile("tp_BprLeftSideSetBackDetailInFtid"),"value");
			System.out.println(LeftSetBackPermissibleft.add(LeftSideSetBackDetailInFt));
			mAssert(LeftSideSetBackDetailInFt, mGetPropertyFromFile("tp_BprLeftSideSetBackDetailInFtdata"), "Actual :"+LeftSideSetBackDetailInFt+"  Expected  :"+mGetPropertyFromFile("tp_BprLeftSideSetBackDetailInFtdata"));
			System.out.println("Capture Permissible Left Set Back in feet is :"+LeftSideSetBackDetailInFt+" and Expected Permissible Left Set Back in feet is  :"+mGetPropertyFromFile("tp_BprLeftSideSetBackDetailInFtdata"));	*/

			//Sending Applied Rear Set Back in Meter
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprRearSetBackid"), mGetPropertyFromFile("tp_BprRearSetBackdata"));
			String tp_BprRearSetBack=mGetText("id", mGetPropertyFromFile("tp_BprRearSetBackid"), "value");
			mAssert(tp_BprRearSetBack, mGetPropertyFromFile("tp_BprRearSetBackdata"), "Applied Rear Set Back in Meter Failed");
			System.out.println(RearSetBackApplied.add(tp_BprRearSetBack));


			//Verifying Applied Rear Set Back in Feet
			String RearSetBackInFt=mGetText("id", mGetPropertyFromFile("tp_BprRearSetBackInFtid"),"value");
			System.out.println(RearSetBackAppliedft.add(RearSetBackInFt));
			mAssert(RearSetBackInFt, mGetPropertyFromFile("tp_BprRearSetBackInFtdata"), "Actual :"+RearSetBackInFt+"  Expected  :"+mGetPropertyFromFile("tp_BprRearSetBackInFtdata"));
			System.out.println("Capture Applied Rear Set Back in feet is :"+RearSetBackInFt+" and Expected Applied Rear Set Back in feet is  :"+mGetPropertyFromFile("tp_BprRearSetBackInFtdata"));	

			/*//Sending Permissible Rear Set Back deatils in Meter
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprRearSetBackDetailid"), mGetPropertyFromFile("tp_BprRearSetBackDetaildata"));
			String tp_BprRearSetBackDetail=mGetText("id", mGetPropertyFromFile("tp_BprRearSetBackDetailid"), "value");
			mAssert(tp_BprRearSetBackDetail, mGetPropertyFromFile("tp_BprRearSetBackDetaildata"), "Permissible Rear Set Back deatils in Meter Failed");
			System.out.println(RearSetBackPermissible.add(tp_BprRearSetBackDetail));


			//Verifying Permissible Rear Set Back deatils in Feet
			String RearSetBackDetailInFt=mGetText("id", mGetPropertyFromFile("tp_BprRearSetBackDetailInFtid"),"value");
			System.out.println(RearSetBackPermissibleft.add(RearSetBackDetailInFt));
			mAssert(RearSetBackDetailInFt, mGetPropertyFromFile("tp_BprRearSetBackDetailInFtdata"), "Actual :"+RearSetBackDetailInFt+"  Expected  :"+mGetPropertyFromFile("tp_BprRearSetBackDetailInFtdata"));
			System.out.println("Capture Applied Avg. Width of Plot in feet is :"+RearSetBackDetailInFt+" and Expected Applied Avg. Width of Plot in feet is  :"+mGetPropertyFromFile("tp_BprRearSetBackDetailInFtdata"));	*/
			//Sending Applied Right Set Back in Meter
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprRightSideSetBackid"), mGetPropertyFromFile("tp_BprRightSideSetBackdata"));
			String tp_BprRightSideSetBack=mGetText("id", mGetPropertyFromFile("tp_BprRightSideSetBackid"), "value");
			mAssert(tp_BprRightSideSetBack, mGetPropertyFromFile("tp_BprRightSideSetBackdata"), "Applied Right Set Back in Meter Failed");
			System.out.println(RightSetBackApplied.add(tp_BprRightSideSetBack));

			//Verifying Applied Right Set Back in Feet
			String RightSideSetBackInFt=mGetText("id", mGetPropertyFromFile("tp_BprRightSideSetBackInFtid"),"value");
			System.out.println(RightSetBackAppliedft.add(RightSideSetBackInFt));
			mAssert(RightSideSetBackInFt, mGetPropertyFromFile("tp_BprRightSideSetBackInFtdata"), "Actual :"+RightSideSetBackInFt+"  Expected  :"+mGetPropertyFromFile("tp_BprRightSideSetBackInFtdata"));
			System.out.println("Capture Applied Right Set Back in feet is :"+RightSideSetBackInFt+" and Expected Applied Right Set Back in feet is  :"+mGetPropertyFromFile("tp_BprRightSideSetBackInFtdata"));	

			/*//Sending Permissible Right Set Back Details in Meter
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprRightSideSetBackDetailid"), mGetPropertyFromFile("tp_BprRightSideSetBackDetaildata"));
			String tp_BprRightSideSetBackDetail=mGetText("id", mGetPropertyFromFile("tp_BprRightSideSetBackDetailid"), "value");
			mAssert(tp_BprRightSideSetBackDetail, mGetPropertyFromFile("tp_BprRightSideSetBackDetaildata"), "Applied Right Set Back in Meter Failed");
			RightSetBackPermissible.add(tp_BprRightSideSetBackDetail);

			//Verifying Permissible Right Set Back Details in Feet
			String RightSideSetBackDetailInFt=mGetText("id", mGetPropertyFromFile("tp_BprRightSideSetBackDetailInFtid"),"value");
			System.out.println(RightSetBackPermissibleft.add(RightSideSetBackDetailInFt));
			mAssert(RightSideSetBackDetailInFt, mGetPropertyFromFile("tp_BprRightSideSetBackDetailInFtdata"), "Actual :"+RightSideSetBackDetailInFt+"  Expected  :"+mGetPropertyFromFile("tp_BprRightSideSetBackDetailInFtdata"));
			System.out.println("Capture Permissible Right Set Back Details in feet is :"+RightSideSetBackDetailInFt+" and Expected Permissible Right Set Back Details in feet is  :"+mGetPropertyFromFile("tp_BprRightSideSetBackDetailInFtdata"));	*/

			mCustomWait(500);
			mscroll(0, 500);

			//Sending Applicant title Details
			mCustomWait(200);
			mWaitForVisible("id", mGetPropertyFromFile("tp_BprApplicantTitleid"));
			mSelectDropDown("id", mGetPropertyFromFile("tp_BprApplicantTitleid"), mGetPropertyFromFile("tp_BprApplicantTitledata"));
			String tp_BprApplicantTitle =mCaptureSelectedValue("id", mGetPropertyFromFile("tp_BprApplicantTitleid"));
			mAssert(tp_BprApplicantTitle,  mGetPropertyFromFile("tp_BprApplicantTitledata"), "Applicant title Failed");
			System.out.println(Bpr_Applicanttitle.add(tp_BprApplicantTitle));

			//Sending Applicant First Name Details
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprApplicantFnameid"), mGetPropertyFromFile("tp_BprApplicantFnamedata"));
			String tp_BprApplicantFname=mGetText("id", mGetPropertyFromFile("tp_BprApplicantFnameid"), "value");
			mAssert(tp_BprApplicantFname, mGetPropertyFromFile("tp_BprApplicantFnamedata"), "Applicant First Name Failed");
			System.out.println(Bpr_ApplicantFName.add(tp_BprApplicantFname));


			//Sending Applicant Middle Name Details
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprApplicantMNameid"), mGetPropertyFromFile("tp_BprApplicantMNamedata"));
			String tp_BprApplicantMname=mGetText("id", mGetPropertyFromFile("tp_BprApplicantMNameid"), "value");
			mAssert(tp_BprApplicantMname, mGetPropertyFromFile("tp_BprApplicantMNamedata"), "Applicant Middle Name Failed");
			Bpr_ApplicantMName.add(tp_BprApplicantMname);

			//Sending Applicant Last Name Details
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprApplicantLNameid"), mGetPropertyFromFile("tp_BprApplicantLNamedata"));
			String tp_BprApplicantLname=mGetText("id", mGetPropertyFromFile("tp_BprApplicantLNameid"), "value");
			mAssert(tp_BprApplicantLname, mGetPropertyFromFile("tp_BprApplicantLNamedata"), "Applicant Last Name Failed");
			Bpr_ApplicantLName.add(tp_BprApplicantLname);

			String Fullname=tp_BprApplicantTitle+" "+tp_BprApplicantFname+" "+tp_BprApplicantFname+" "+tp_BprApplicantFname;
			ApplicantFullname.add(Fullname);
			String Fullwithoutname = tp_BprApplicantFname+" "+tp_BprApplicantMname+" "+tp_BprApplicantLname;
			System.out.println(Fullwithoutname);
			Bpr_AppfullnmWdTitle.add(Fullwithoutname);
			Nameofowner.add(Fullwithoutname);
			System.out.println("Name of Applicant for Assertion"+Nameofowner);
			//Sending Applicant Mobile No
			mSendKeys("id", mGetPropertyFromFile("tp_BprApplicantMobileNoid"), mGetPropertyFromFile("tp_BprApplicantMobileNodata"));
			String tp_BprApplicantMobileNo=mGetText("id", mGetPropertyFromFile("tp_BprApplicantMobileNoid"), "value");
			mAssert(tp_BprApplicantMobileNo, mGetPropertyFromFile("tp_BprApplicantMobileNodata"), "Applicant Mobile No Failed");
			Bpr_ApplicantMobileNo.add(tp_BprApplicantMobileNo);

			//Sending Applicant Email Id
			mSendKeys("id", mGetPropertyFromFile("tp_BprApplicantEmailid"), mGetPropertyFromFile("tp_BprApplicantEmaildata"));
			String tp_BprApplicantEmail=mGetText("id", mGetPropertyFromFile("tp_BprApplicantEmailid"), "value");
			mAssert(tp_BprApplicantEmail, mGetPropertyFromFile("tp_BprApplicantEmaildata"), "Applicant Email Id Failed");
			Bpr_ApplicantEmailId.add(tp_BprApplicantEmail);


			//Sending Owner title Details
			mCustomWait(200);
			mWaitForVisible("id", mGetPropertyFromFile("tp_BprOwnerTitleid"));
			mSelectDropDown("id", mGetPropertyFromFile("tp_BprOwnerTitleid"), mGetPropertyFromFile("tp_BprOwnerTitledata"));
			String tp_BprOwnerTitle=mCaptureSelectedValue("id", mGetPropertyFromFile("tp_BprOwnerTitleid"));
			mAssert(tp_BprOwnerTitle, mGetPropertyFromFile("tp_BprOwnerTitledata"), "Owner title Failed");
			Bpr_Ownertitle.add(tp_BprOwnerTitle);

			//Sending Owner First Name
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprOwnerFNameid"), mGetPropertyFromFile("tp_BprOwnerFNamedata"));
			String tp_BprOwnerFName=mGetText("id", mGetPropertyFromFile("tp_BprOwnerFNameid"), "value");
			mAssert(tp_BprOwnerFName, mGetPropertyFromFile("tp_BprOwnerFNamedata"), "Owner First Name Failed");
			Bpr_OwnerFName.add(tp_BprOwnerFName);


			//Sending Owner Middle Name
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprOwnerMNameid"), mGetPropertyFromFile("tp_BprOwnerMNamedata"));
			String tp_BprOwnerMName=mGetText("id", mGetPropertyFromFile("tp_BprOwnerMNameid"), "value");
			mAssert(tp_BprOwnerMName, mGetPropertyFromFile("tp_BprOwnerMNamedata"), "Owner Middle Name Failed");
			Bpr_OwnerMName.add(tp_BprOwnerMName);


			//Sending Owner Last Name
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprOwnerLNameid"), mGetPropertyFromFile("tp_BprOwnerLNamedata"));
			String tp_BprOwnerLName=mGetText("id", mGetPropertyFromFile("tp_BprOwnerLNameid"), "value");
			mAssert(tp_BprOwnerLName, mGetPropertyFromFile("tp_BprOwnerLNamedata"), "Owner last Name Failed");
			Bpr_OwnerLName.add(tp_BprOwnerLName);

			String OwnerFullNameforLetter=tp_BprOwnerTitle+" "+tp_BprOwnerFName+" "+tp_BprOwnerMName+" "+tp_BprOwnerLName;
			System.out.println("Owner Full name in application form is");
			Bpr_OwnerFullName.add(OwnerFullNameforLetter);



			//Sending Owner Mobile No
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprOwnerMobileNoid"), mGetPropertyFromFile("tp_BprOwnerMobileNodata"));
			String tp_BprOwnerMobileNo=mGetText("id", mGetPropertyFromFile("tp_BprOwnerMobileNoid"), "value");
			mAssert(tp_BprOwnerMobileNo, mGetPropertyFromFile("tp_BprOwnerMobileNodata"), "Owner Mobile No Failed");
			Bpr_OwnerMobileNo.add(tp_BprOwnerMobileNo);

			//Sending Owner Email Id
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprOwnerEmailid"), mGetPropertyFromFile("tp_BprOwnerEmaildata"));
			String tp_BprOwnerEmail=mGetText("id", mGetPropertyFromFile("tp_BprOwnerEmailid"), "value");
			mAssert(tp_BprOwnerEmail, mGetPropertyFromFile("tp_BprOwnerEmaildata"), "Owner Mobile No Failed");
			System.out.println(Bpr_OwnerEmailId.add(tp_BprOwnerEmail));


			String Fname = mGetText("id",mGetPropertyFromFile("tp_BprApplicantFnameid"),"value"); 					
			String Mname = mGetText("id",mGetPropertyFromFile("tp_BprApplicantMNameid"),"value"); 
			String Lname = mGetText("id",mGetPropertyFromFile("tp_BprApplicantLNameid"),"value"); 

			String applicantName = Fname+" "+Mname+" "+Lname;					
			System.out.println("applicant Name is : " + applicantName); 
			entcompname=applicantName;
			System.out.println("entcompname is : "+entcompname); 
			APPlicantNameForAssertion.add(applicantName); 

			//Sending Applicant Address Line 1
			mCustomWait(200);
			mWaitForVisible("id", mGetPropertyFromFile("tp_BprOwnerCAddLine1id"));
			mSendKeys("id", mGetPropertyFromFile("tp_BprOwnerCAddLine1id"), mGetPropertyFromFile("tp_BprOwnerCAddLine1data"));
			String tp_BprOwnerCAddLine1=mGetText("id", mGetPropertyFromFile("tp_BprOwnerCAddLine1id"), "value");
			mAssert(tp_BprOwnerCAddLine1, mGetPropertyFromFile("tp_BprOwnerCAddLine1data"), "Owner Address Line 1 Failed");
			System.out.println(Bpr_Addline1.add(tp_BprOwnerCAddLine1));

			//Sending Applicant Address Line 2
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprOwnerCAddLine2id"), mGetPropertyFromFile("tp_BprOwnerCAddLine2data"));
			String tp_BprOwnerCAddLine2=mGetText("id", mGetPropertyFromFile("tp_BprOwnerCAddLine2id"), "value");
			mAssert(tp_BprOwnerCAddLine2, mGetPropertyFromFile("tp_BprOwnerCAddLine2data"), "Owner Address Line 1 Failed");
			System.out.println(Bpr_Addline1.add(tp_BprOwnerCAddLine1));
			Bpr_Addline2.add(tp_BprOwnerCAddLine2);

			//Sending Applicant Pin code
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprOwnerPinCodeid"), mGetPropertyFromFile("tp_BprOwnerPinCodedata"));
			String tp_BprOwnerPinCode=mGetText("id", mGetPropertyFromFile("tp_BprOwnerPinCodeid"), "value");
			mAssert(tp_BprOwnerPinCode, mGetPropertyFromFile("tp_BprOwnerPinCodedata"), "Applicant Pin code Failed");
			System.out.println(Bpr_Pincode.add(tp_BprOwnerPinCode));

			mscroll(0, 300);

			if(mGetPropertyFromFile("tp_BprAddOwnerYNdata").equalsIgnoreCase("Yes")){
				//Sending Multiple Additional owner details
				String Addtitle= mGetPropertyFromFile("tp_BprAddOwnerTitledata");
				String Addfame= mGetPropertyFromFile("tp_BprAddOwnerFNamedata");
				String Addmname= mGetPropertyFromFile("tp_BprAddOwnerMNamedata");
				String Addlname= mGetPropertyFromFile("tp_BprAddOwnerLNamedata");
				String Addmbno= mGetPropertyFromFile("tp_BprAddOwnerMobileNodata");
				String Addemail= mGetPropertyFromFile("tp_BprAddOwnerEmaildata");

				ArrayList AddTitleList = new ArrayList(Arrays.asList(Addtitle.split(",")));
				ArrayList AddFNameList = new ArrayList(Arrays.asList(Addfame.split(",")));
				ArrayList AddMNameList = new ArrayList(Arrays.asList(Addmname.split(",")));
				ArrayList AddLNameList = new ArrayList(Arrays.asList(Addlname.split(",")));
				ArrayList AddMbNoList = new ArrayList(Arrays.asList(Addmbno.split(",")));
				ArrayList AddEmailList = new ArrayList(Arrays.asList(Addemail.split(",")));

				for(int i=0; i<AddTitleList.size();i++){

					//Click to add Additional owner Details Link
					mCustomWait(500);
					mWaitForVisible("linkText", mGetPropertyFromFile("tp_BprAddOwneRDetailsLinkid"));
					mClick("linkText", mGetPropertyFromFile("tp_BprAddOwneRDetailsLinkid"));	

					//Sending Additional owner title
					mCustomWait(500);
					mWaitForVisible("id", mGetPropertyFromFile("tp_BprAddOwnerTitleid"));
					mSelectDropDown("id", mGetPropertyFromFile("tp_BprAddOwnerTitleid"), AddTitleList.get(i).toString());	
					String BprTiltl=mCaptureSelectedValue("id", mGetPropertyFromFile("tp_BprAddOwnerTitleid"));

					//Sending Additional owner First Name
					mCustomWait(500);
					mSendKeys("id", mGetPropertyFromFile("tp_BprAddOwnerFNameid"), AddFNameList.get(i).toString());
					String BprAddOwnerFName=mGetText("id", mGetPropertyFromFile("tp_BprAddOwnerFNameid"),"value");

					//Sending Additional owner Middle Name
					mCustomWait(500);
					mSendKeys("id", mGetPropertyFromFile("tp_BprAddOwnerMNameid"), AddMNameList.get(i).toString());
					String BprAddOwnerMName=mGetText("id", mGetPropertyFromFile("tp_BprAddOwnerMNameid"),"value");
					//Sending Additional owner Last Name
					mCustomWait(500);

					mSendKeys("id", mGetPropertyFromFile("tp_BprAddOwnerLNameid"), AddLNameList.get(i).toString());
					String BprAddOwnerLName=mGetText("id", mGetPropertyFromFile("tp_BprAddOwnerLNameid"),"value");

					String FullName=BprTiltl+" "+BprAddOwnerFName+" "+BprAddOwnerMName+" "+BprAddOwnerLName;
					Bpr_AdditionalownerName.add(FullName);

					//Sending Additional owner Mobile No
					mCustomWait(500);
					mSendKeys("id", mGetPropertyFromFile("tp_BprAddOwnerMobileNoid"), AddMbNoList.get(i).toString());
					String tp_BprAddOwnerMbNo=mGetText("id", mGetPropertyFromFile("tp_BprAddOwnerMobileNoid"), "value");
					Bpr_AdditionalownermbNo.add(tp_BprAddOwnerMbNo);

					//Sending Additional owner Email Id
					mCustomWait(500);
					mSendKeys("id", mGetPropertyFromFile("tp_BprAddOwnerEmailid"), AddEmailList.get(i).toString());
					String tp_BprAddOwnerEmail=mGetText("id", mGetPropertyFromFile("tp_BprAddOwnerEmailid"), "value");
					Bpr_AdditionalownerEmail.add(tp_BprAddOwnerEmail);

					//Click To submit owner Deatils
					mCustomWait(500);
					mWaitForVisible("xpath", mGetPropertyFromFile("tp_BprAddOwnerDetailid"));
					mClick("xpath", mGetPropertyFromFile("tp_BprAddOwnerDetailid"));
					mCustomWait(300);
					//Closing Pop Up messagebox and capturing the message
					mWaitForVisible("css", mGetPropertyFromFile("tp_BprAddOwnerPopUpCloseid"));
					String msgAtAddOwnrDetail = mGetText("css", mGetPropertyFromFile("tp_BprAddOwnerMsgid"));
					System.out.println(msgAtAddOwnrDetail);
					mAssert(msgAtAddOwnrDetail, mGetPropertyFromFile("tp_BprAddOwnerMsgdata"), "Actual  :"+msgAtAddOwnrDetail+"  Expected  :"+mGetPropertyFromFile("tp_BprAddOwnerMsgdata"));
					mCustomWait(300);
					mGenericWait();
					mClick("css", mGetPropertyFromFile("tp_BprAddOwnerPopUpCloseid"));
					mCustomWait(300);
				}
				System.out.println(Bpr_AdditionalownerName);
				System.out.println(Bpr_AdditionalownermbNo);
				System.out.println(Bpr_AdditionalownerEmail);
			}
			// Sending Ward
			mWaitForVisible("id", mGetPropertyFromFile("tp_BprWardid"));
			mSelectDropDown("id", mGetPropertyFromFile("tp_BprWardid"), mGetPropertyFromFile("tp_BprWarddata"));
			String Bprward =mCaptureSelectedValue("id", mGetPropertyFromFile("tp_BprWardid"));
			Bpr_ward.add(Bprward);
			mAssert(Bprward, mGetPropertyFromFile("tp_BprWarddata"), "Ward Failed");

			//Sending Plot Khasara no
			mSendKeys("id", mGetPropertyFromFile("tp_BprPlotNoid"), mGetPropertyFromFile("tp_BprPlotNodata"));
			String BprPlotKhasraNo =mGetText("id", mGetPropertyFromFile("tp_BprPlotNoid"),"value");
			System.out.println("Plot/Khasara no"+BprPlotKhasraNo);
			Bpr_PlotKhasraNo.add(BprPlotKhasraNo);
			mAssert(BprPlotKhasraNo, mGetPropertyFromFile("tp_BprPlotNodata"), "Plot/Khasara no Failed");


			//Sending MSP plot No
			mSendKeys("id", mGetPropertyFromFile("tp_BprMspPlotNoid"), mGetPropertyFromFile("tp_BprMspPlotNodata"));
			String tp_BprMspPlotNo =mGetText("id", mGetPropertyFromFile("tp_BprMspPlotNoid"),"value");
			System.out.println("MSP plot No"+tp_BprMspPlotNo);
			Bpr_PlotNoMSP.add(tp_BprMspPlotNo);
			mAssert(tp_BprMspPlotNo, mGetPropertyFromFile("tp_BprMspPlotNodata"), "MSP plot No Failed");


			//Sending Mauja No
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprMaujaNoid"), mGetPropertyFromFile("tp_BprMaujaNodata"));
			String BprMaujaNo =mGetText("id", mGetPropertyFromFile("tp_BprMaujaNoid"),"value");
			System.out.println("Mauja No"+BprMaujaNo);
			Bpr_MaujaNo.add(BprMaujaNo);
			mAssert(BprMaujaNo, mGetPropertyFromFile("tp_BprMaujaNodata"), "Mauja No Failed");

			//Sending Holding No
			mSendKeys("id", mGetPropertyFromFile("tp_BprHoldingNoid"), mGetPropertyFromFile("tp_BprHoldingNodata"));
			String BprHoldingNo =mGetText("id", mGetPropertyFromFile("tp_BprHoldingNoid"),"value");
			System.out.println(Bpr_HoldingNo.add(BprHoldingNo));
			mAssert(BprHoldingNo, mGetPropertyFromFile("tp_BprHoldingNodata"), "Holding No Failed");

			//Sending Khata No
			mSendKeys("id", mGetPropertyFromFile("tp_BprKhataNoid"), mGetPropertyFromFile("tp_BprKhataNodata"));
			String BprKhataNo =mGetText("id", mGetPropertyFromFile("tp_BprKhataNoid"),"value");
			Bpr_KhataNo.add(BprKhataNo);
			mAssert(BprKhataNo, mGetPropertyFromFile("tp_BprKhataNodata"), "Khata No Failed");


			//Sending Toji No
			mSendKeys("id", mGetPropertyFromFile("tp_BprTojiNoid"), mGetPropertyFromFile("tp_BprTojiNodata"));
			String BprTojiNo =mGetText("id", mGetPropertyFromFile("tp_BprTojiNoid"),"value");
			System.out.println("Toji No"+BprTojiNo);
			Bpr_TojiNo.add(BprTojiNo);
			mAssert(BprTojiNo, mGetPropertyFromFile("tp_BprTojiNodata"), "Toji No Failed");


			//Sending Village 
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprVillageid"), mGetPropertyFromFile("tp_BprVillagedata"));
			String BprVillage =mGetText("id", mGetPropertyFromFile("tp_BprVillageid"),"value");
			System.out.println(Bpr_Village.add(BprVillage));
			mAssert(BprVillage, mGetPropertyFromFile("tp_BprVillagedata"), "Village Failed");


			//Sending Project Name
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprProjectNameid"), mGetPropertyFromFile("tp_BprProjectNamedata"));
			String BprProjectName =mGetText("id", mGetPropertyFromFile("tp_BprProjectNameid"),"value");
			System.out.println(Bpr_ProjectName.add(BprProjectName));
			mAssert(BprProjectName, mGetPropertyFromFile("tp_BprProjectNamedata"), "Project Name Failed");

			//Sending Scheme Name
			mCustomWait(200);
			mSendKeys("id", mGetPropertyFromFile("tp_BprSchemeNameid"), mGetPropertyFromFile("tp_BprSchemeNamedata"));
			String BprSchemeName =mGetText("id", mGetPropertyFromFile("tp_BprSchemeNameid"),"value");
			System.out.println(Bpr_SchemeName.add(BprSchemeName));
			mAssert(BprSchemeName, mGetPropertyFromFile("tp_BprSchemeNamedata"), "Scheme Name Failed");


			if(mGetPropertyFromFile("tp_BprTechPrsnType").equalsIgnoreCase("Yes")){
				//Adding Multiple Technical Person
				String perType= mGetPropertyFromFile("tp_BprTechPrsnTypedata");
				String perName= mGetPropertyFromFile("tp_BprTechPersonNamedata");

				ArrayList techPertypeList = new ArrayList(Arrays.asList(perType.split(",")));
				ArrayList techPernameList = new ArrayList(Arrays.asList(perName.split(",")));

				for(int i=0;i<techPertypeList.size();i++)
				{
					//Clicked on to Add technical person detail Link			
					mCustomWait(1000);
					mWaitForVisible("linkText", mGetPropertyFromFile("tp_BprAddTechPrsnDetailsLinkid"));
					mClick("linkText", mGetPropertyFromFile("tp_BprAddTechPrsnDetailsLinkid"));

					//Sending Technical Person type
					mCustomWait(200);
					mWaitForVisible("id", mGetPropertyFromFile("tp_BprTechPrsnTypeid"));
					mSelectDropDown("id", mGetPropertyFromFile("tp_BprTechPrsnTypeid"),techPertypeList.get(i).toString());
					String BprTechPrsnType=mCaptureSelectedValue("id", mGetPropertyFromFile("tp_BprTechPrsnTypeid"));
					Bpr_TechPerType.add(BprTechPrsnType);

					//Sending Technical Person Name
					mCustomWait(200);
					mWaitForVisible("id", mGetPropertyFromFile("tp_BprTechPersonNameid"));
					mSelectDropDown("id", mGetPropertyFromFile("tp_BprTechPersonNameid"),techPernameList.get(i).toString());
					String BprTechPrsnName=mCaptureSelectedValue("id", mGetPropertyFromFile("tp_BprTechPersonNameid"));
					Bpr_TechPerName.add(BprTechPrsnName);

					//Submitting Technical Person Details 
					mCustomWait(200);
					mWaitForVisible("xpath", mGetPropertyFromFile("tp_BprAddTechPrsnDetailsSubBtnid"));
					mClick("xpath", mGetPropertyFromFile("tp_BprAddTechPrsnDetailsSubBtnid"));

					//Closing Message Box and capturing Pop-Up message
					mWaitForVisible("css", mGetPropertyFromFile("tp_BprAddTechPrsnDetailsPopUpBtnid"));
					String msgAtTechPersnDetails = mGetText("css", mGetPropertyFromFile("tp_BprAddTechPrsnDetailsMsgid"));
					System.out.println(msgAtTechPersnDetails);
					mCustomWait(200);
					mAssert(msgAtTechPersnDetails, mGetPropertyFromFile("tp_BprAddTechPrsnDetailsMsgdata"),"Actual   :"+msgAtTechPersnDetails+" Expected   :"+ mGetPropertyFromFile("tp_BprAddTechPrsnDetailsMsgdata"));
					mTakeScreenShot();
					mCustomWait(200);
					mClick("css", mGetPropertyFromFile("tp_BprAddTechPrsnDetailsPopUpBtnid"));
				}
			}
			//Upload Documents
			mCustomWait(700);
			docUpload("xpath", mGetPropertyFromFile("tp_UploadDocumentTableId"), "upldDocStatus");



			String y1= mGetText("id",mGetPropertyFromFile("tp_BprAppliChargedAmountid"),"value");
			int ChargedAmount=Integer.parseInt(y1);

			System.out.println("Charged Amount is::"+ChargedAmount);
			mAssert(y1, mGetPropertyFromFile("tp_BprAppliChargedAmountdata "), "Charged Amount on construsction cost failed Actual is::"+y1+" "+"Expected is"+mGetPropertyFromFile("tp_BprAppliChargedAmountdata"));

			mTakeScreenShot();
			if(mGetPropertyFromFile("municipality").equalsIgnoreCase("Gaya Municipal Corporation") && ChargedAmount>0) 
			{

				payment("paymentMode","byBankOrULB");


				mWaitForVisible("xpath", mGetPropertyFromFile("tp_BprFInalSubmitid"));
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("tp_BprFInalSubmitid"));
				mCustomWait(1000);

				//Pop-Up
				mWaitForVisible("id", mGetPropertyFromFile("tp_BprApplicationProcdBtnid"));
				mTakeScreenShot();
				mCustomWait(1000);
				String firstMsg = mGetText("css", mGetPropertyFromFile("tp_BprApplicationProcdPopUpMsgid"));
				System.out.println(firstMsg);

				//Catching case number
				Pattern CaseNo = Pattern.compile("(?<=Your Case No is : )(.*)");
				java.util.regex.Matcher matcher = CaseNo.matcher(firstMsg);

				if (matcher.find()) {
					String caseNum = matcher.group(1);
					CaseNumber = caseNum.split("\\.", 2)[0];
					System.out.println("Your Case Number is:"+CaseNumber);
					Bpr_CaseNo.add(CaseNumber);
				}

				//Catching apllication Number
				Pattern AppNo = Pattern.compile("(?<=Your Application No. is : )(.*)");
				java.util.regex.Matcher matcher1 = AppNo.matcher(firstMsg);

				if (matcher1.find()) {
					appNo = matcher1.group(1);
					System.out.println("Your Application Number is:"+appNo);	
				}


				//Assert Message
				Pattern msgAssert = Pattern.compile("\\s*(.+?)\\s*(?=Your Application No. is : )");
				java.util.regex.Matcher matcher11 = msgAssert.matcher(firstMsg);

				appNo = mGetText("css", mGetPropertyFromFile("tp_AppNoAtFinishOfBpr"));
				mAppNoArray("css",mGetPropertyFromFile("tp_AppNoAtFinishOfBpr"));
				System.out.println("The Application for Building Persmission is ::: "+appNo);
				ApplicationNoforLandDev.add(appNo);
				System.out.println("The Application for Building Persmission Arraylist is ::: "+ApplicationNoforLandDev);

				if (matcher11.find()) {
					String msgatApp = matcher11.group(1);
					System.out.println(msgatApp);	
					mAssert(msgatApp, mGetPropertyFromFile("tp_BprApplicationProcdPopUpMsgdata"),"Actual  :"+msgatApp+"  Expected   :"+ mGetPropertyFromFile("tp_BprApplicationProcdPopUpMsgdata"));
				}

				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("tp_BprApplicationProcdBtnid"));
				mCustomWait(1000);
				//newChallanReader();
				if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
				{
					newChallanReader();
				}
				else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
				{
					mChallanPDFReader();
					api.PdfPatterns.patternChallanPdf(output);
					System.out.println("swapchallnno==>"+swapchallnno);
				}
				mCustomWait(1000);
			}

			else
			{
				//submit Button
				mWaitForVisible("xpath", mGetPropertyFromFile("tp_BprFInalSubmitid"));
				mClick("xpath", mGetPropertyFromFile("tp_BprFInalSubmitid"));
				mCustomWait(200);

				//Pop-Up
				mWaitForVisible("id", mGetPropertyFromFile("tp_BprApplicationProcdBtnid"));
				mTakeScreenShot();
				mCustomWait(1000);
				String firstMsg = mGetText("css", mGetPropertyFromFile("tp_BprApplicationProcdPopUpMsgid"));
				System.out.println(firstMsg);

				//Catching case number
				Pattern CaseNo = Pattern.compile("(?<=Your Case No is : )(.*)");
				java.util.regex.Matcher matcher = CaseNo.matcher(firstMsg);

				if (matcher.find()) {
					String caseNum = matcher.group(1);
					CaseNumber = caseNum.split("\\.", 2)[0];
					System.out.println("Your Case Number is:"+CaseNumber);
					Bpr_CaseNo.add(CaseNumber);
				}
				//Catching apllication Number
				Pattern AppNo = Pattern.compile("(?<=Your Application No. is : )(.*)");
				java.util.regex.Matcher matcher1 = AppNo.matcher(firstMsg);

				if (matcher1.find()) {
					appNo = matcher1.group(1);
					System.out.println("Your Application Number is:"+appNo);	
				}
				mCustomWait(1000);
				//Assert Message
				Pattern msgAssert = Pattern.compile("\\s*(.+?)\\s*(?=Your Application No. is : )");
				java.util.regex.Matcher matcher11 = msgAssert.matcher(firstMsg);
				mCustomWait(1000);
				appNo = mGetText("css", mGetPropertyFromFile("tp_AppNoAtFinishOfBpr"));
				mAppNoArray("css",mGetPropertyFromFile("tp_AppNoAtFinishOfBpr"));
				System.out.println("The Application for Building Persmission is ::: "+appNo);
				ApplicationNoforLandDev.add(appNo);

				if (matcher11.find()) {
					String msgatApp = matcher11.group(1);
					System.out.println(msgatApp);	
					mAssert(msgatApp, mGetPropertyFromFile("tp_BprApplicationProcdPopUpMsgdata"),"Actual  :"+msgatApp+"  Expected   :"+ mGetPropertyFromFile("tp_BprApplicationProcdPopUpMsgdata"));
				}
				mCustomWait(2000);
				mClick("id", mGetPropertyFromFile("tp_BprApplicationProcdBtnid"));
				mCustomWait(1000);
				mWaitForVisible("css",mGetPropertyFromFile("tp_BprCommandSubBtnid"));
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("css",mGetPropertyFromFile("tp_BprCommandSubBtnid"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in ApplctnForBuildinPermisn script");
		}
	}


	public void OccupancyCertificate()
	{
		try
		{		


			mNavigation("tp_BuildingPrmsnNRegulationLinkid", "tp_OccupancyCertificateLinkid");

			TpServiceName=mGetText("css", mGetPropertyFromFile("Servicenametextid"));
			ServiceNameforTownPlanning.add(TpServiceName.trim());
			System.out.println("Service name is" +ServiceNameforTownPlanning);

			Occupancy_SearchBy(mGetPropertyFromFile("tp_OcFullNamSearchdata"),mGetPropertyFromFile("tp_OcCaseNoSearchdata"));


			//selecting Application
			mWaitForVisible("name", mGetPropertyFromFile("tp_OcSelectApplicationRadioid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mCustomWait(1000);
			mClick("name", mGetPropertyFromFile("tp_OcSelectApplicationRadioid"));
			mCustomWait(1000);

			//getting case Number
			//String cseNum = driver.findElement(By.id(mGetPropertyFromFile("tp_OcCaseNoAtOcpncyCertid"))).getAttribute("value");
			String cseNum=mGetText("id", mGetPropertyFromFile("tp_OcCaseNoAtOcpncyCertid"), "value");
			mCustomWait(1000);
			OC_Caseid.add(cseNum);
			mCustomWait(1000);
			System.out.println("Case No in Application Form Is::"+cseNum);

			String Ntrcntr=mGetText("id", mGetPropertyFromFile("tp_OcNatureOfConstructionid"), "value");
			System.out.println("Nature of construction is::"+Ntrcntr);
			OC_NatureOfConstruction.add(Ntrcntr);


			//getting application Number
			String AppleNo = driver.findElement(By.id(mGetPropertyFromFile("tp_OcAppNoAtOcpncyCertid"))).getAttribute("value");
			mCustomWait(1000);
			//mAssert(AppleNo, appNo, "Actual  :"+AppleNo+"   Expected   :"+appNo);
			OC_AppNo.add(AppleNo);
			mCustomWait(1000);
			System.out.println(AppleNo);
			mGenericWait();

			String OriUseType=mGetText("id", mGetPropertyFromFile("tp_OcPrinCipleUsageTypeid"), "value");
			System.out.println("Principle usage type is::"+OriUseType);
			Oc_PrincipleUsagetYpe.add(OriUseType);

			String OcAppName=mGetText("css", mGetPropertyFromFile("tp_OcReadApplicantNameId"));
			System.out.println("Principle usage type is::"+OcAppName);
			Oc_ApplicantNamewdoutTitle.add(OcAppName);


			String MbNo=mGetText("id", mGetPropertyFromFile("tp_OcMobileNoid"), "value");
			System.out.println("Mobile No is is::"+MbNo);
			Oc_MobileNo.add(MbNo);

			String ApplicationDate=mGetText("id", mGetPropertyFromFile("tp_OcApplicationDateid"), "value");
			System.out.println("Application date is::"+ApplicationDate);
			OC_Applicationdate.add(ApplicationDate);

			String ApplicationAppDate=mGetText("id", mGetPropertyFromFile("tp_OcBuildingApprovaldateDateid"), "value");
			System.out.println("Application Approval date is::"+ApplicationAppDate);
			Oc_BuildingApprovalDate.add(ApplicationAppDate);


			String ApplAppValidDate=mGetText("id", mGetPropertyFromFile("tp_OcBuildingApprovaldateDateid"), "value");
			System.out.println("Building Approval Validity  date is::"+ApplAppValidDate);
			Oc_BuildingApprovalValidityDate.add(ApplAppValidDate);

			//DropdownReadWrite("id",mGetPropertyFromFile("tp_OcNatureOfConstructionid"),mGetPropertyFromFile("tp_OcNatureOfConstructiondata"));

			mGenericWait();
			mWaitForVisible("id", mGetPropertyFromFile("tp_OcEmailid"));
			mSendKeys("id", mGetPropertyFromFile("tp_OcEmailid"), mGetPropertyFromFile("tp_OcEmaildata"));
			String tp_OcEmail=mGetText("id", mGetPropertyFromFile("tp_OcEmailid"), "value");
			System.out.println("Email Id is ::"+tp_OcEmail);
			Oc_EmailID.add(tp_OcEmail);

			//selecting Completion date
			mGenericWait();
			mWaitForVisible("id", mGetPropertyFromFile("tp_OcCompletionDateid"));
			mDateSelect("id", mGetPropertyFromFile("tp_OcCompletionDateid"), mGetPropertyFromFile("tp_OcCompletionDatedata"));
			String tp_OcCompletionDate=mGetText("id", mGetPropertyFromFile("tp_OcCompletionDateid"), "value");
			System.out.println("Email Id is ::"+tp_OcCompletionDate);
			Oc_CompletionDate.add(tp_OcCompletionDate);

			//Uploading Document
						mCustomWait(1000);
			mUpload("id",mGetPropertyFromFile("cmUploadDoc0id"), mGetPropertyFromFile("uploadPath1"));
			
			mCustomWait(1000);

			//submit Button
			mWaitForVisible("css",mGetPropertyFromFile("tp_OcOccupancyCertificateSubBtnid"));
			mCustomWait(2000);
			mTakeScreenShot();
			mCustomWait(2000);
			mClick("css",mGetPropertyFromFile("tp_OcOccupancyCertificateSubBtnid"));
			mCustomWait(2000);

			//proceed button
			mWaitForVisible("id", mGetPropertyFromFile("tp_OcOccupancyCertificateProcdBtnid"));
			String msgAtOucpyCert = mGetText("css", mGetPropertyFromFile("tp_OcMsgAtPopUpOfOcpncyCertid"));
			System.out.println(msgAtOucpyCert);
			mCustomWait(2000);
			mTakeScreenShot();
			mCustomWait(2000);

			//Getting Application Number
			Pattern AppNo = Pattern.compile("(?<=Your Application No. is : )(.*)");
			java.util.regex.Matcher matcher = AppNo.matcher(msgAtOucpyCert);
			if (matcher.find()) {
				appNo = matcher.group(1);
				System.out.println("Application Number is:"+appNo);
			}

			appNo = mGetText("css", mGetPropertyFromFile("tp_OcAppNoPopUpid"));
			mAppNoArray("css",mGetPropertyFromFile("tp_OcAppNoPopUpid"));
			System.out.println("The Application for Occupancy Certificate is :::"+appNo);
			OC_AppNo.add(appNo);
			ApplicationNoforLandDev.add(appNo);


			//Getting Case Number
			Pattern AppNum = Pattern.compile("(?<=Your Case No is : )(.*)");
			java.util.regex.Matcher matcher1 = AppNum.matcher(msgAtOucpyCert);
			if (matcher1.find()) {
				String caseNum = matcher1.group(1);
				CaseNumber = caseNum.split("\\.", 2)[0];
				System.out.println("Case Number is:"+CaseNumber);
				mAssert(cseNum, CaseNumber, "Actual  :"+cseNum+"   Expected   :"+CaseNumber);
			}

			mClick("id", mGetPropertyFromFile("tp_OcOccupancyCertificateProcdBtnid"));
			//Getting text at fon page
			mCustomWait(2000);
			String appNoAtCss = mGetText("css", mGetPropertyFromFile("tp_OcAppNoAtFontid"));
			System.out.println(appNoAtCss);

			//Finish Button
			mWaitForVisible("css", mGetPropertyFromFile("tp_OcOccupancyCertificateFinishid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("tp_OcOccupancyCertificateFinishid"));
			mCustomWait(1000);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in OccupancyCertificate script");
		}
	}

	public void LOIVerification()
	{
		try
		{			
			if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
			{
				newChallanReader();
			}
			else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
			{
				mChallanPDFReader();
				api.PdfPatterns.patternChallanPdf(output);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in LOIVerification script");
		}
	}

	//Truti patra verificartion
	public void TrutiPatraVerificationp()
	{
		try
		{
			Pattern appleNum = Pattern.compile("(?<=application no. )\\s*(.+?)\\s*(?=dated)");
			java.util.regex.Matcher matcher = appleNum.matcher(pdfoutput);

			if (matcher.find()) {
				mCustomWait(2000);
				String appNum = matcher.group(1);
				System.out.println();
				System.out.println();
				System.out.println("Application Number is:"+appNum);
				mCustomWait(2000);
				if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
				{
					mAssert(appNum, applicationNo.get(CurrentinvoCount).toString(), "Actual  :"+appNum+"  Expected   :"+applicationNo.get(CurrentinvoCount).toString()+"at truti patra verification");
					mCustomWait(1000);
				}
				else
				{
					mAssert(appNum, mGetPropertyFromFile("applicationNo"), "Actual  :"+appNum+"  Expected   :"+mGetPropertyFromFile("applicationNo")+"at truti patra verification");
					mCustomWait(1000);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in TrutiPatraVerificationp script");
		}
	}



	public void searchByVariousCriteria(String tp_BprByUsageTypeid,String tp_BprSearchbyCaseid,String tp_BprSearchbyFNameid,String tp_BprSearchbyMNameid,String tp_BprSearchbyLNameid,String tp_BprMaujaNoid,String tp_BprPlotKhasaraNoid,String tp_BprManualRefNoid,String tp_BprCoverArInHecterid)
	{
		try
		{
			// Search by First, Middle & Last Name
			if(mGetPropertyFromFile("tp_BprSearchbyFNameYNdata").equalsIgnoreCase("Yes") && mGetPropertyFromFile("tp_BprSearchbyLNameYNdata").equalsIgnoreCase("Yes"))
			{
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("tp_BprSearchbyFNameid"), mGetPropertyFromFile("tp_BprSearchbyFNamedata"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("tp_BprSearchbyMNameid"), mGetPropertyFromFile("tp_BprSearchbyMNamedata"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("tp_BprSearchbyLNameid"), mGetPropertyFromFile("tp_BprSearchbyLNamedata"));
			} 
			// Search by First,Last Name and Usage Type
			else if( mGetPropertyFromFile("tp_BprSearchbyFNameYNdata").equalsIgnoreCase("Yes") && mGetPropertyFromFile("tp_BprSearchbyLNameYNdata").equalsIgnoreCase("Yes") && mGetPropertyFromFile("tp_BprByUsageTypeYNdata").equalsIgnoreCase("Yes")){
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("tp_BprSearchbyFNameid"), mGetPropertyFromFile("tp_BprSearchbyFNamedata"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("tp_BprSearchbyMNameid"), mGetPropertyFromFile("tp_BprSearchbyMNamedata"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("tp_BprByUsageTypeid"), mGetPropertyFromFile("tp_BprByUsageTypedata"));
			}	
			// Search By Case No:
			else if(mGetPropertyFromFile("tp_BprSearchbyCaseYNdata").equalsIgnoreCase("Yes"))
			{
				if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent")) 
				{
					mSendKeys("id", mGetPropertyFromFile("tp_BprSearchbyCaseid"), CaseID.get(CurrentinvoCount));
					mTakeScreenShot();
				}
				else{
					mGenericWait();
					mSendKeys("id", mGetPropertyFromFile("tp_BprSearchbyCaseid"), mGetPropertyFromFile("tp_BprSearchbyCasedata"));
					mTakeScreenShot();
				}

			} 
			// Search by Covered Area in Hecters and Usage Type
			else if(mGetPropertyFromFile("tp_BprCoverArInHecterYNdata").equalsIgnoreCase("Yes") && mGetPropertyFromFile("tp_BprByUsageTypeYNdata").equalsIgnoreCase("Yes"))
			{
				//Enter Usage Type
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("tp_BprByUsageTypeid"), mGetPropertyFromFile("tp_BprByUsageTypedata"));

				//Enter Covered Area in hecter
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("tp_BprCoverArInHecterid"), mGetPropertyFromFile("tp_BprCoverArInHecterdata"));
			} 
			// Search By Mauja No,Plot/khasara no And Usage type
			else if(mGetPropertyFromFile("tp_BprMaujaNoYNdata").equalsIgnoreCase("Yes") && mGetPropertyFromFile("tp_BprPlotKhasaraNoYNdata").equalsIgnoreCase("Yes") && mGetPropertyFromFile("tp_BprByUsageTypeYNdata").equalsIgnoreCase("Yes"))
			{
				//Enter Mauja No
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("tp_BprMaujaNoid"), mGetPropertyFromFile("tp_BprMaujaNodata"));

				//Enter Khasara No
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("tp_BprPlotKhasaraNoid"), mGetPropertyFromFile("tp_BprPlotKhasaraNodata"));

				//Enter Usage Type
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("tp_BprByUsageTypeid"), mGetPropertyFromFile("tp_BprByUsageTypedata"));
			} 
			//Search By Manual reference Number
			else if(mGetPropertyFromFile("tp_BprManualRefNoYNdata").equalsIgnoreCase("Yes"))
			{
				//Enter Manual reference no
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("tp_BprManualRefNoid"), mGetPropertyFromFile("tp_BprManualRefNodata"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in searchByVariousCriteria script");
		}
	}

	//Piyush on 24 Nov 2016
	public void Occupancy_SearchBy(String tp_OcFullNamSearchid,String tp_OcCaseNoSearchid)
	{
		try
		{
			if(mGetPropertyFromFile("tp_OcCaseNoSearchdirectYNdata").equalsIgnoreCase("No"))
			{
				// Search by Name
				if(mGetPropertyFromFile("tp_OcFullNameSearchYNdata").equalsIgnoreCase("Yes"))
				{
					mGenericWait();
					mSendKeys("id", mGetPropertyFromFile("tp_OcFullNamSearchid"), mGetPropertyFromFile("tp_OcFullNamSearchdata"));
				} 
				// Search By Case No:
				else if(mGetPropertyFromFile("tp_OcCaseNoSearchYNdata").equalsIgnoreCase("Yes"))
				{
					mGenericWait();
					mSendKeys("id", mGetPropertyFromFile("tp_OcCaseNoSearchid"), mGetPropertyFromFile("tp_OcCaseNoSearchdata"));
				} 
				mClick("css", mGetPropertyFromFile("tp_OcSearchBtnid"));
			}
			else
			{
				mClick("css", mGetPropertyFromFile("tp_OcSearchBtnid"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Occupancy_SearchBy script");
		}
	}


	//Piyush on 24 Nov 2016
	public void CancelBuildingPermission()
	{
		try{

			mWaitForVisible("linkText", mGetPropertyFromFile("tp_AaBuildinPermsnNRegultnLinkid"));
			mGenericWait();	
			//Cancellation Of Building Permit Link
			mNavigation("tp_AaBuildinPermsnNRegultnLinkid","tp_CancellationBprTrasnactionLinkid", "tp_CancellationBprCancellationLinkid");
			mCustomWait(1000);

			mTakeScreenShot();
			//case number search hyperlink
			mCustomWait(1000);
			mWaitForVisible("linkText", mGetPropertyFromFile("tp_CancellationBprCancelSearchLinkid"));
			mClick("linkText", mGetPropertyFromFile("tp_CancellationBprCancelSearchLinkid"));

			searchByVariousCriteria(mGetPropertyFromFile("tp_BprSearchbyFNameid"),mGetPropertyFromFile("tp_BprSearchbyLNameid"),mGetPropertyFromFile("tp_BprSearchbyMNameid"),mGetPropertyFromFile("tp_BprSearchbyCaseid"),mGetPropertyFromFile("tp_BprUsageTypeid"),mGetPropertyFromFile("tp_BprCoverArInHecterid"),mGetPropertyFromFile("tp_BprMaujaNoid"),mGetPropertyFromFile("tp_BprPlotKhasaraNoid"),mGetPropertyFromFile("tp_BprManualRefNoid"));
			//General search Button retriving all data
			mWaitForVisible("name", mGetPropertyFromFile("tp_BprAplByCaseNoSearchid"));
			mClick("name", mGetPropertyFromFile("tp_BprAplByCaseNoSearchid"));


			//select radio icon
			mCustomWait(1000);
			mWaitForVisible("css", mGetPropertyFromFile("tp_BprSelectApplicationRadioid"));
			mClick("css", mGetPropertyFromFile("tp_BprSelectApplicationRadioid"));
			mCustomWait(500);
			mTakeScreenShot();
			mCustomWait(500);
			//submit after selecting application

			mCustomWait(500);

			String CaseNo=mGetText("id", mGetPropertyFromFile("tp_CancellationBprReadCaseNoid"), "value");
			System.out.println("Case No is :::"+CaseNo);

			mCustomWait(500);
			String ApplicationNoread=mGetText("id", mGetPropertyFromFile("tp_CancellationBprReadApplicationid"), "value");
			System.out.println("Application No is :::"+ApplicationNoread);
			mCustomWait(500);
			String ApplicationDate=mGetText("id", mGetPropertyFromFile("tp_CancellationBprReadApplicationDateid"), "value");
			System.out.println("Application Date is :::"+ApplicationDate);
			mCustomWait(500);
			mTakeScreenShot();
			mCustomWait(500);
			String NatureOfConstruction=mGetText("id", mGetPropertyFromFile("tp_CancellationBprReadNatureOfConstructionid"), "value");
			System.out.println("Nature Of Construction is :::"+NatureOfConstruction);
			mCustomWait(500);
			String UsageType=mGetText("id", mGetPropertyFromFile("tp_CancellationBprReadUsageTypeid"), "value");
			System.out.println("Usage Type is :::"+UsageType);
			mCustomWait(500);
			mTakeScreenShot();
			mCustomWait(500);
			String SubUsageType=mGetText("id", mGetPropertyFromFile("tp_CancellationBprReadSubUsageTypeid"), "value");
			System.out.println("Sub Usage Type is :::"+SubUsageType);
			mCustomWait(500);
			String CoverArea=mGetText("id", mGetPropertyFromFile("tp_CancellationBprReadCoverAreaid"), "value");
			System.out.println("Cover Area is :::"+CoverArea);

			String ApplicantTitle=mGetText("id", mGetPropertyFromFile("tp_CancellationBprReadApplicantTitleid"), "value");
			System.out.println("Applicant Title is :::"+ApplicantTitle);

			String ApplicantFName=mGetText("id", mGetPropertyFromFile("tp_CancellationBprReadApplicantFNameid"), "value");
			System.out.println("Applicant first Name No is :::"+ApplicantFName);

			String ApplicantMName=mGetText("id", mGetPropertyFromFile("tp_CancellationBprReadApplicantMNameid"), "value");
			System.out.println("Applicant Middele Name is :::"+ApplicantMName);
			mTakeScreenShot();
			String ApplicantLName=mGetText("id", mGetPropertyFromFile("tp_CancellationBprReadApplicantLNameid"), "value");
			System.out.println("Applicant Last Name is :::"+ApplicantLName);

			String OwnerTitle=mGetText("id", mGetPropertyFromFile("tp_CancellationBprReadOwnerTitleid"), "value");
			System.out.println("Owner Title Name :::"+OwnerTitle);

			String OwnerFName=mGetText("id", mGetPropertyFromFile("tp_CancellationBprReadOwnerFNameid"), "value");
			System.out.println("Owner First Name is :::"+ApplicantFName);

			String OwnerMName=mGetText("id", mGetPropertyFromFile("tp_CancellationBprReadOwnerMNameid"), "value");
			System.out.println("Owner Middle Name is :::"+OwnerMName);

			String OwnerLName=mGetText("id", mGetPropertyFromFile("tp_CancellationBprReadOwnerLNameid"), "value");
			System.out.println("Owner Last Name is :::"+OwnerLName);

			String SanctionDate=mGetText("id", mGetPropertyFromFile("tp_CancellationBprReadSanctionDateid"), "value");
			System.out.println("Sanction date is :::"+SanctionDate);
			mCustomWait(500);
			mTakeScreenShot();
			mCustomWait(500);
			String ValidTillDate=mGetText("id", mGetPropertyFromFile("tp_CancellationBprReadValidTillDateid"), "value");
			System.out.println("Valid Till date is :::"+ValidTillDate);

			mWaitForVisible("id", mGetPropertyFromFile("tp_CancellationBprOrderNoid"));
			mSendKeys("id",mGetPropertyFromFile("tp_CancellationBprOrderNoid"),mGetPropertyFromFile("tp_CancellationBprOrderNoData"));

			mWaitForVisible("id", mGetPropertyFromFile("tp_CancellationBprOderDateid"));
			mDateSelect("id",mGetPropertyFromFile("tp_CancellationBprOderDateid"),mGetPropertyFromFile("tp_CancellationBprOderDateData"));
			mTakeScreenShot();
			mWaitForVisible("id", mGetPropertyFromFile("tp_CancellationBprCancelReasonid"));
			mSendKeys("id",mGetPropertyFromFile("tp_CancellationBprCancelReasonid"),mGetPropertyFromFile("tp_CancellationBprCancelReasonData"));
			mTakeScreenShot();
			String CancelBy=mGetText("id", mGetPropertyFromFile("tp_CancellationBprReadCancelByid"), "value");
			System.out.println("Owner First Name is"+CancelBy);
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("tp_CancellationBprSubmitCancelid"));
			mCustomWait(1000);
			String cancelMessage = mGetText("css", mGetPropertyFromFile("tp_CancellationBprCancelMessageid"));
			mTakeScreenShot();
			mAssert(cancelMessage, mGetPropertyFromFile("tp_CancellationBprCancelMessageData"), "Assert is true");
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("tp_CancellationBprProceedBtneid"));
			mTakeScreenShot();
			mCustomWait(1000);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in CancelBuildingPermission method");
		}
	}

	//Piyush on 25 Nov 2016
	public void verifyupphotoprogessrpt()
	{
		try
		{
			mWaitForVisible("linkText", mGetPropertyFromFile("tp_AaBuildinPermsnNRegultnLinkid"));
			mGenericWait();	
			mNavigation("tp_AaBuildinPermsnNRegultnLinkid","tp_VerifyTrasnactionLinkid", "tp_VerifyphotographsLinkid");
			mCustomWait(1000);
			mTakeScreenShot();
			if(mGetPropertyFromFile("tp_VerifyRetriveAll").equalsIgnoreCase("Yes"))
			{
				mCustomWait(1000);
				//General search Button retriving all data
				mClick("xpath", mGetPropertyFromFile("tp_VerifySearchcriteriabtnid"));

				mCustomWait(1000);
				driver.findElement(By.cssSelector("#search_gridTPprogressReportUploadView > div > span")).click();
				mCustomWait(500);
				mTakeScreenShot();
				mSendKeys("id", mGetPropertyFromFile("tp_BprSearchCaseidid"), mGetPropertyFromFile("tp_VerifybyCasedata"));
				mCustomWait(1000);
				mTakeScreenShot();
				driver.findElement(By.id("fbox_gridTPprogressReportUploadView_search")).click();
				mTakeScreenShot();
			}
			else
			{
				VerifyPhotographsearchCriteria(mGetPropertyFromFile("tp_VerifySearchbyFNameid"),mGetPropertyFromFile("tp_VerifySearchbyMNameid"),mGetPropertyFromFile("tp_VerifySearchbyLNameid"),mGetPropertyFromFile("tp_VerifySearchbyFromDateid"),mGetPropertyFromFile("tp_VerifySearchByToDateid"),mGetPropertyFromFile("tp_VerifySearchbyMaujaNoid"),mGetPropertyFromFile("tp_VerifySearchbyPlotKhasaraNoid"),mGetPropertyFromFile("tp_VerifySearchbyManualRefNoid"),mGetPropertyFromFile("tp_VerifySearchbyPlotMspNoid"),mGetPropertyFromFile("tp_VerifySearchbyStatusid"),mGetPropertyFromFile("tp_VerifySearchbyconstructionLevelid"));
				mCustomWait(1000);
				//General search Button retriving all data
				mClick("xpath", mGetPropertyFromFile("tp_VerifySearchcriteriabtnid"));
			}
			//select Application for edit Details
			mCustomWait(2000);
			mWaitForVisible("xpath", mGetPropertyFromFile("tp_VerifySelectEditDetailsid"));
			mClick("xpath", mGetPropertyFromFile("tp_VerifySelectEditDetailsid"));
			mCustomWait(2000);
			mTakeScreenShot();
			String UpApplicantTilte=mGetText("id", mGetPropertyFromFile("tp_VerifyReadApplicantTitleid"), "value");
			System.out.println("Applicant Title is :::"+UpApplicantTilte);

			String upApplicantFName=mGetText("id", mGetPropertyFromFile("tp_VerifyReadApplicantFNameid"), "value");
			System.out.println("Applicant First Name is :::"+upApplicantFName);

			String upApplicantMName=mGetText("id", mGetPropertyFromFile("tp_VerifyReadApplicantMNameid"), "value");
			System.out.println("Applicant First Name is :::"+upApplicantMName);

			String upApplicantLName=mGetText("id", mGetPropertyFromFile("tp_VerifyReadApplicantLNameid"), "value");
			System.out.println("Applicant First Name is :::"+upApplicantLName);

			String UpOwnerTilte=mGetText("id", mGetPropertyFromFile("tp_VerifyReadOwnerTitleid"), "value");
			System.out.println("Applicant Title is :::"+UpOwnerTilte);

			String upOwnerFName=mGetText("id", mGetPropertyFromFile("tp_VerifyReadOwnerFNameid"), "value");
			System.out.println("Applicant First Name is :::"+upOwnerFName);

			String upOwnerMName=mGetText("id", mGetPropertyFromFile("tp_VerifyReadOwnerMNameid"), "value");
			System.out.println("Applicant First Name is :::"+upOwnerMName);

			String upOwnerLName=mGetText("id", mGetPropertyFromFile("tp_VerifyReadOwnerLNameid"), "value");
			System.out.println("Applicant First Name is :::"+upOwnerLName);

			String upMobileNo=mGetText("id", mGetPropertyFromFile("tp_VerifyReadMobileNoid"), "value");
			System.out.println("Applicant First Name is :::"+upMobileNo);

			String upEmail=mGetText("id", mGetPropertyFromFile("tp_VerifyReadEmailid"), "value");
			System.out.println("Applicant First Name is :::"+upEmail);

			String upPlotKhasara=mGetText("id", mGetPropertyFromFile("tp_VerifyReadPlotKhasaraNoid"), "value");
			System.out.println("Applicant First Name is :::"+upPlotKhasara);

			String upPlotMSPNo=mGetText("id", mGetPropertyFromFile("tp_VerifyReadPlotMSPNoid"), "value");
			System.out.println("Applicant First Name is :::"+upPlotMSPNo);

			String upMaujaNo=mGetText("id", mGetPropertyFromFile("tp_VerifyReadMaujaNoid"), "value");
			System.out.println("Applicant First Name is :::"+upMaujaNo);
			mTakeScreenShot();
			String upHoldingNo=mGetText("id", mGetPropertyFromFile("tp_VerifyReadHoldingNoid"), "value");
			System.out.println("Applicant First Name is :::"+upHoldingNo);

			String upVillageMohallah=mGetText("id", mGetPropertyFromFile("tp_VerifyReadVillageMohallahid"), "value");
			System.out.println("Applicant First Name is :::"+upVillageMohallah);
			mTakeScreenShot();
			mCustomWait(2000);
			mClick("css", mGetPropertyFromFile("tp_VerifyDownloadProgressReportid"));
			mTakeScreenShot();
			mCustomWait(2000);
			mDownloadFileImg(myClassName);
			mCustomWait(2000);
			mscroll(0, 500);
			mCustomWait(2000);
			WebElement table = driver.findElement(By.className("gridtable"));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount = rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

			// Iterate to get the value of each cell in table along with element id
			int Rowno = 0;
			for (int a=0;a<rwcount;a++) 		
			{
				List<WebElement> cols = rows.get(a).findElements(By.xpath("td"));
				System.out.println("NUMBER OF columns IN THIS TABLE = " + cols.size());
				int Columnno = 0;

				for (int b=0;b<cols.size();b++) 		
				{
					if(a==1)
					{
						if (b == 1)
						{
							String cnstrnLvl = rows.get(a).findElement(By.xpath("./td[2]")).getText();
							mGenericWait();
							System.out.println("Construction Level. is :"+cnstrnLvl);
						}
						if (b == 3)
						{
							rows.get(a).findElement(By.xpath("./td[4]/input")).click();
							mGenericWait();

							mWaitForVisible("id", mGetPropertyFromFile("tp_Verifyfrmid"));

							String dwnloadImg=	driver.findElement(By.cssSelector("div > ul > li:nth-child(1) > i")).getAttribute("id");
							System.out.println("id is :"+dwnloadImg);
							driver.findElement(By.id(dwnloadImg)).click();			

							mDownloadFileImg(myClassName);
							mTakeScreenShot();

							mCustomWait(2000);
							driver.findElement(By.cssSelector("input[class='css_btn'][value='Ok']")).click();
							mTakeScreenShot();
							mCustomWait(2000);
						}
					}
				}
				for (int b=0;b<cols.size();b++) 		
				{
					if(a>1)
					{
						if (b == 1) 
						{						
							rows.get(a).findElement(By.xpath("./td[2]/input")).click();
							mGenericWait();

							mWaitForVisible("id", mGetPropertyFromFile("tp_Verifyfrmid"));

							String dwnloadImg=	driver.findElement(By.cssSelector("div > ul > li:nth-child(1) > i")).getAttribute("id");
							System.out.println("id is :"+dwnloadImg);
							driver.findElement(By.id(dwnloadImg)).click();			

							mDownloadFileImg(myClassName);
							mTakeScreenShot();

							mCustomWait(2000);
							driver.findElement(By.cssSelector("input[class='css_btn'][value='Ok']")).click();
							mCustomWait(2000);
							mTakeScreenShot();

						}
					}
				}
			}
			for (int a=0;a<rwcount;a++) 		
			{
				List<WebElement> cols = rows.get(a).findElements(By.xpath("td"));
				System.out.println("NUMBER OF columns IN THIS TABLE = " + cols.size());

				for (int b=0;b<cols.size();b++) 		
				{
					WebElement table1 = driver.findElement(By.className("gridtable"));
					if(a==1)
					{
						if (b == 0) 
						{
							System.out.println("I am in loop a>2");

							String SiteInspection = rows.get(a).findElement(By.xpath("th/a/img")).getAttribute("src");
							System.out.println(SiteInspection);
							driver.findElement(By.xpath("//img[@src='css/images/view.png']")).click();

							mGenericWait();
							mSelectDropDown("id", mGetPropertyFromFile("tp_VerifySiteInspectorEmployeeid"), mGetPropertyFromFile("tp_VerifySiteInspectorEmployeedata"));
							mGenericWait();
							mDateSelect("id", mGetPropertyFromFile("tp_VerifyInspectiondateid"), mGetPropertyFromFile("tp_VerifyInspectiondatedata"));
							mGenericWait();
							mTakeScreenShot();
							mClick("xpath", mGetPropertyFromFile("tp_VerifyInspectionSubmitBtnid"));
							mChallanPDFReader();
							mCustomWait(6000);
						}
						if (b ==1) 
						{
							String CheckBox = rows.get(a).findElement(By.xpath("th[2]/input")).getAttribute("id");
							driver.findElement(By.id(CheckBox)).click();
							mGenericWait();
							mTakeScreenShot();
						}
						else
						{
							System.out.println("column no not found in 2nd loop");
						}
					}
				}
			}
			mCustomWait(2000);
			VerifyPhotographsearchCriteria(mGetPropertyFromFile("tp_VerifySearchbyFNameid"),mGetPropertyFromFile("tp_VerifySearchbyMNameid"),mGetPropertyFromFile("tp_VerifySearchbyLNameid"),mGetPropertyFromFile("tp_VerifySearchbyFromDateid"),mGetPropertyFromFile("tp_VerifySearchByToDateid"),mGetPropertyFromFile("tp_VerifySearchbyMaujaNoid"),mGetPropertyFromFile("tp_VerifySearchbyPlotKhasaraNoid"),mGetPropertyFromFile("tp_VerifySearchbyManualRefNoid"),mGetPropertyFromFile("tp_VerifySearchbyPlotMspNoid"),mGetPropertyFromFile("tp_VerifySearchbyStatusid"),mGetPropertyFromFile("tp_VerifySearchbyconstructionLevelid"));
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("tp_VerifySearchcriteriabtnid"));
			mTakeScreenShot();
			mCustomWait(1000);
			mWaitForVisible("xpath", mGetPropertyFromFile("tp_VerifySelectEditDetailsid"));
			mClick("xpath", mGetPropertyFromFile("tp_VerifySelectEditDetailsid"));
			mCustomWait(1000);
			mTakeScreenShot();
			WebElement table1 = driver.findElement(By.className("gridtable"));

			List<WebElement> rows1 = table1.findElements(By.tagName("tr"));
			int rwcount1 = rows1.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount1);

			// Iterate to get the value of each cell in table along with element
			// id
			int Rowno1 = 0;
			for (int a=0;a<rwcount1;a++) 		
			{
				List<WebElement> cols = rows1.get(a).findElements(By.xpath("td"));
				System.out.println("NUMBER OF columns IN THIS TABLE = " + cols.size());
				int Columnno = 0;

				for (int b=0;b<cols.size();b++) 		
				{
					if(a==1)
					{
						if (b == 0)
						{
							mCustomWait(2000);
							String CheckBox = rows1.get(a).findElement(By.xpath("th[2]/input")).getAttribute("id");
							driver.findElement(By.id(CheckBox)).click();
							mTakeScreenShot();
							mGenericWait();
						}
					}
				}
			}
			mCustomWait(1000);
			mUpload("id", mGetPropertyFromFile("tp_VerifySiteInspectionReportUploadid"), mGetPropertyFromFile("tp_VerifySiteInspectionReportUploaddata"));
			mCustomWait(1000);
			mTakeScreenShot();
			mSendKeys("id", mGetPropertyFromFile("tp_VerifyFinalRemarksid"), mGetPropertyFromFile("tp_VerifyFinalRemarksdata"));
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("tp_VerifyFormSubmitbtnid"));
			mCustomWait(1000);
			String msg =mGetText("css", mGetPropertyFromFile("tp_VerifySubmitPopUpMsgid"), "value");
			System.out.println(msg);
			mCustomWait(1000);
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("tp_VerifypopUpProceedbtnid"));
			mTakeScreenShot();
			mCustomWait(2000);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in applicationForLandDevp method");
		}
	}

	//Piyush on 26 Nov 2016
	//Upload Photographs and Periodic Progress Report
	public void upphotoprogessrpt()
	{
		try
		{
			mCustomWait(1000);
			mNavigation("tp_UploadBuildingPermissionNRegulationLinkid", "tp_UploadphotographsLinkid");
			mCustomWait(1000);


			mClick("css",mGetPropertyFromFile("tp_UploadReadSearchid"));


			//General search Button retriving all data
			mWaitForVisible("css", mGetPropertyFromFile("tp_BprSearchcriteriabtnid"));
			mClick("css", mGetPropertyFromFile("tp_BprSearchcriteriabtnid"));
			mTakeScreenShot();
			mCustomWait(1000);
			
			driver.findElement(By.cssSelector("#search_gridTPProgressReportUpload > div > span")).click();
			mCustomWait(500);
			mTakeScreenShot();
			mSendKeys("id", mGetPropertyFromFile("tp_BprSearchCaseidid"), mGetPropertyFromFile("tp_BprSearchbyCasedata"));
			mCustomWait(1000);
			driver.findElement(By.id("fbox_gridTPProgressReportUpload_search")).click();
			mTakeScreenShot();
			/*searchByVariousCriteria(mGetPropertyFromFile("tp_BprSearchbyFNameid"),mGetPropertyFromFile("tp_BprSearchbyLNameid"),mGetPropertyFromFile("tp_BprSearchbyMNameid"),mGetPropertyFromFile("tp_BprSearchbyCaseid"),mGetPropertyFromFile("tp_BprUsageTypeid"),mGetPropertyFromFile("tp_BprCoverArInHecterid"),mGetPropertyFromFile("tp_BprMaujaNoid"),mGetPropertyFromFile("tp_BprPlotKhasaraNoid"),mGetPropertyFromFile("tp_BprManualRefNoid"));

			 */
			mCustomWait(1000);
			//select radio icon
			mCustomWait(2000);
			mWaitForVisible("css", mGetPropertyFromFile("tp_BprSelectApplicationRadioid"));
			mClick("css", mGetPropertyFromFile("tp_BprSelectApplicationRadioid"));
			mCustomWait(2000);
			mTakeScreenShot();
			mWaitForVisible("css", mGetPropertyFromFile("tp_BprSelectApplicationRadioPopupid"));
			mClick("css", mGetPropertyFromFile("tp_BprSelectApplicationRadioPopupid"));
			mTakeScreenShot();
			mCustomWait(1000);
			mTakeScreenShot();

			String CaseId=mGetText("id", mGetPropertyFromFile("tp_UploadReadCaseid"), "value");
			System.out.println("Case Id is :::"+CaseId);

			mWaitForVisible("id", mGetPropertyFromFile("tp_UploadReadCaseid"));

			String ApplicationNumber=mGetText("id", mGetPropertyFromFile("tp_UploadReadCaseid"), "value");
			System.out.println("Your Application Number is :::"+ApplicationNumber);

			String UpApplicantTilte=mGetText("id", mGetPropertyFromFile("tp_UploadReadApplicantTitleid"), "value");
			System.out.println("Applicant Title is :::"+UpApplicantTilte);

			String upApplicantFName=mGetText("id", mGetPropertyFromFile("tp_UploadReadApplicantFNameid"), "value");
			System.out.println("Applicant First Name is :::"+upApplicantFName);

			String upApplicantMName=mGetText("id", mGetPropertyFromFile("tp_UploadReadApplicantMNameid"), "value");
			System.out.println("Applicant First Name is :::"+upApplicantMName);

			String upApplicantLName=mGetText("id", mGetPropertyFromFile("tp_UploadReadApplicantLNameid"), "value");
			System.out.println("Applicant First Name is :::"+upApplicantLName);

			String UpOwnerTilte=mGetText("id", mGetPropertyFromFile("tp_UploadReadOwnerTitleid"), "value");
			System.out.println("Applicant Title is :::"+UpOwnerTilte);

			String upOwnerFName=mGetText("id", mGetPropertyFromFile("tp_UploadReadOwnerFNameid"), "value");
			System.out.println("Applicant First Name is :::"+upOwnerFName);

			String upOwnerMName=mGetText("id", mGetPropertyFromFile("tp_UploadReadOwnerMNameid"), "value");
			System.out.println("Applicant First Name is :::"+upOwnerMName);
			mTakeScreenShot();
			String upOwnerLName=mGetText("id", mGetPropertyFromFile("tp_UploadReadOwnerLNameid"), "value");
			System.out.println("Applicant First Name is :::"+upOwnerLName);

			String upMobileNo=mGetText("id", mGetPropertyFromFile("tp_UploadReadMobileNoid"), "value");
			System.out.println("Applicant First Name is :::"+upMobileNo);

			String upEmail=mGetText("id", mGetPropertyFromFile("tp_UploadReadEmailid"), "value");
			System.out.println("Applicant First Name is :::"+upEmail);
			mTakeScreenShot();
			String upPlotKhasara=mGetText("id", mGetPropertyFromFile("tp_UploadReadPlotKhasaraNoid"), "value");
			System.out.println("Applicant First Name is :::"+upPlotKhasara);

			String upPlotMSPNo=mGetText("id", mGetPropertyFromFile("tp_UploadReadPlotMSPNoid"), "value");
			System.out.println("Applicant First Name is :::"+upPlotMSPNo);

			String upMaujaNo=mGetText("id", mGetPropertyFromFile("tp_UploadReadMaujaNoid"), "value");
			System.out.println("Applicant First Name is :::"+upMaujaNo);

			String upHoldingNo=mGetText("id", mGetPropertyFromFile("tp_UploadReadHoldingNoid"), "value");
			System.out.println("Applicant First Name is :::"+upHoldingNo);

			String upVillageMohallah=mGetText("id", mGetPropertyFromFile("tp_UploadReadVillageMohallahid"), "value");
			System.out.println("Applicant First Name is :::"+upVillageMohallah);
			mTakeScreenShot();
			mUpload("id", mGetPropertyFromFile("tp_UploadUploadbtnid"), mGetPropertyFromFile("tp_UploadUploadbtndata"));
			mTakeScreenShot();
			String[] ConstLevelList= mGetPropertyFromFile("tp_UploadConstructionLevelData").split(",");

			for(int i=0; i<ConstLevelList.length;i++)
			{
				WebElement table = driver.findElement(By.cssSelector("table[class='gridtable margin_top_10 clear']"));

				List<WebElement> rows = table.findElements(By.tagName("tr"));
				int rwcount = rows.size();
				System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

				// Iterate to get the value of each cell in table along with element id
				int Rowno = 0;
				for (int a=0;a<rwcount;a++) 		
				{
					List<WebElement> cols = rows.get(a).findElements(By.xpath("td"));
					System.out.println("NUMBER OF columns IN THIS TABLE = " + cols.size());
					int Columnno = 0;
					for (int b=0;b<cols.size();b++) 		
					{
						if(a==1)
						{
							if (b == 1)
							{
								String cnstrnLvl = rows.get(a).findElement(By.xpath("./td[2]/select")).getAttribute("id");
								mGenericWait();
								System.out.println("Sr. No. is :"+cnstrnLvl);
								new Select(driver.findElement(By.id(cnstrnLvl))).selectByVisibleText(ConstLevelList[i]);
							}
							if (b == 3) 
							{
								rows.get(a).findElement(By.xpath("./td[4]/input")).click();
								mGenericWait();

								mWaitForVisible("id", mGetPropertyFromFile("tp_Uploadfrmid"));
								mTakeScreenShot();
								mUpload("id", "uploadDoc"+(i+1)+".pudPath", "DeputyEntry.jpg");
								mCustomWait(4000);

								driver.findElement(By.cssSelector("input[class='css_btn'][value='Ok']")).click();
								mCustomWait(4000);
								mTakeScreenShot();
							}
							if (b == 4) 
							{
								String rem = rows.get(a).findElement(By.xpath("./td[5]/input")).getAttribute("id");
								mGenericWait();
								mSendKeys("id", rem, "Remark_1");
								mTakeScreenShot();
							}
							else
							{
								System.out.println("column no not found");
							}
						}

					}
					for (int b=0;b<cols.size();b++) 		
					{
						if(a>1)
						{
							if (b == 0) 
							{
								rows.get(a).findElement(By.xpath("./td[2]/input")).click();
								mGenericWait();

								mWaitForVisible("id", mGetPropertyFromFile("tp_Uploadfrmid"));
								mTakeScreenShot();
								mUpload("id", "uploadDoc"+(a)+".pudPath", "Image1.png");
								mCustomWait(4000);

								driver.findElement(By.cssSelector("input[class='css_btn'][value='Ok']")).click();
								mCustomWait(4000);
								mTakeScreenShot();
							}
							if (b == 1) 
							{
								String rem = rows.get(a).findElement(By.xpath("./td[3]/input")).getAttribute("id");
								mGenericWait();
								mSendKeys("id", rem, "Remark_1");
								mTakeScreenShot();

							}
							else
							{
								System.out.println("column no not found in 2nd loop");
							}
						}
					}

				}

			}

			mClick("css", mGetPropertyFromFile("tp_UploadFormSubmitbtnid"));
			mTakeScreenShot();
			mCustomWait(1000);
			mClick("id", mGetPropertyFromFile("tp_BprProceedBtnid"));
			//mTakeScreenShot();

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in applicationForLandDevp method");
		}
	}

	//Piyush on 27 Nov 2016
	//Verify Photograph and Progress Report search Criteria
	public void VerifyPhotographsearchCriteria(String tp_VerifySearchbyFNameid,String tp_VerifySearchbyMNameid,String tp_VerifySearchbyLNameid,String tp_VerifySearchbyFromDateid,String tp_VerifySearchByToDateid,String tp_VerifySearchbyMaujaNoid,String tp_VerifySearchbyPlotKhasaraNoid,String tp_VerifySearchbyManualRefNoid,String tp_VerifySearchbyPlotMspNoid,String tp_VerifySearchbyStatusid,String tp_VerifySearchbyconstructionLevelid)
	{
		try
		{
			// Search by First, Middle & Last Name
			if(mGetPropertyFromFile("tp_VerifySearchbyFNameYNdata").equalsIgnoreCase("Yes") && mGetPropertyFromFile("tp_VerifySearchbyMNameYNdata").equalsIgnoreCase("Yes") && mGetPropertyFromFile("tp_VerifySearchbyLNameYNdata").equalsIgnoreCase("Yes"))
			{
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("tp_VerifySearchbyFNameid"), mGetPropertyFromFile("tp_VerifySearchbyFNamedata"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("tp_VerifySearchbyMNameid"), mGetPropertyFromFile("tp_VerifySearchbyMNamedata"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("tp_VerifySearchbyLNameid"), mGetPropertyFromFile("tp_VerifySearchbyLNamedata"));
			} 
			// Search by From Date,To Date, Status and Construction Level
			else if(mGetPropertyFromFile("tp_VerifySearchbyStatusYNdata").equalsIgnoreCase("Yes") && mGetPropertyFromFile("tp_VerifySearchbyconstructionLevelYNdata").equalsIgnoreCase("Yes")){

				mSelectDropDown("id", mGetPropertyFromFile("tp_VerifySearchbyStatusid"), mGetPropertyFromFile("tp_VerifySearchbyStatusdata"));
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("tp_VerifySearchbyconstructionLevelid"), mGetPropertyFromFile("tp_VerifySearchbyconstructionLeveldata"));
				mGenericWait();
			}
			else if(mGetPropertyFromFile("tp_VerifySearchbyconstructionLevelYNdata").equalsIgnoreCase("Yes") && mGetPropertyFromFile("tp_VerifySearchbyFromDateYNdata").equalsIgnoreCase("Yes") && mGetPropertyFromFile("tp_VerifySearchByToDateYNdata").equalsIgnoreCase("Yes"))
			{
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("tp_VerifySearchbyconstructionLevelid"), mGetPropertyFromFile("tp_VerifySearchbyconstructionLeveldata"));

				mDateSelect("id", mGetPropertyFromFile("tp_VerifySearchbyFromDateid"), mGetPropertyFromFile("tp_VerifySearchbyFromDatedata"));
				mGenericWait();
				mDateSelect("id", mGetPropertyFromFile("tp_VerifySearchbyToDateid"), mGetPropertyFromFile("tp_VerifySearchByToDatedata"));
				mGenericWait();			
			}	
			// Search By Status :
			else if(mGetPropertyFromFile("tp_VerifySearchbyStatusYNdata").equalsIgnoreCase("Yes"))
			{
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("tp_VerifySearchbyStatusid"), mGetPropertyFromFile("tp_VerifySearchbyStatusdata"));
			} 
			// Search By Mauja No,Plot/khasara no And Plot MSP
			else if(mGetPropertyFromFile("tp_VerifyMaujaNoYNdata").equalsIgnoreCase("Yes") && mGetPropertyFromFile("tp_VerifyPlotKhasaraNoYNdata").equalsIgnoreCase("Yes") && mGetPropertyFromFile("tp_VerifySearchbyPlotMspNoYNdata").equalsIgnoreCase("Yes"))
			{
				//Enter Mauja No
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("tp_VerifySearchbyMaujaNoid"), mGetPropertyFromFile("tp_VerifyMaujaNodata"));

				//Enter Khasara No
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("tp_VerifySearchbyPlotKhasaraNoid"), mGetPropertyFromFile("tp_VerifyPlotKhasaraNodata"));

				//Enter Usage Type
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("tp_VerifySearchbyPlotMspNoid"), mGetPropertyFromFile("tp_VerifySearchbyPlotMspNodata"));
			} 
			//Search By Manual reference Number
			else if(mGetPropertyFromFile("tp_VerifyManualRefNoYNdata").equalsIgnoreCase("Yes"))
			{
				//Enter Manual reference no
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("tp_VerifySearchbyManualRefNoid"), mGetPropertyFromFile("tp_VerifyManualRefNodata"));
			}

			// Search by  Construction Level
			else if(mGetPropertyFromFile("tp_VerifySearchbyconstructionLevelYNdata").equalsIgnoreCase("Yes"))
			{
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("tp_VerifySearchbyconstructionLevelid"), mGetPropertyFromFile("tp_VerifySearchbyconstructionLeveldata"));
			} 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in searchByVariousCriteria script");
		}
	}


	public  void PrintercounterreportAssertion()
	{
		try{
			{
				api.PdfPatterns.IntimationTownPlanningPDF(pdfoutput);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in searchByVariousCriteria script");
		}
	}


	//Code for Land development plan Approval Letter Assertion report
	public void Land_dev_Approval_Assertion()
	{
		try{
			api.PdfPatterns.LandDevApprovalPDF(pdfoutput);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(LDAL_APPLNO.get(CurrentinvoCount), ApplicationNoforLandDev.get(CurrentinvoCount), "Application No does not match in Approval letter Actual :::"+LDAL_APPLNO +",Expected is :::"+ApplicationNoforLandDev);
				mAssert(LDAL_OWNERNAME.get(CurrentinvoCount),AlD_Nameofowner.get(CurrentinvoCount), "Owner Name does not match in Approval letter Actual :::"+LDAL_OWNERNAME +",Expected is :::"+AlD_Nameofowner);
				mAssert(LDAP_WARD.get(CurrentinvoCount), ward.get(CurrentinvoCount), "Actual Ward No is :::"+LDAP_WARD +",Expected is :::"+ward);
				mAssert(LDAP_KHATANO.get(CurrentinvoCount), KhataNo.get(CurrentinvoCount), "Actual Khata No is :::"+LDAP_KHATANO +",Expected is:::"+KhataNo);
				mAssert(LDAP_HOLDINGNO.get(CurrentinvoCount), HoldingNo.get(CurrentinvoCount), "Actual Holding No is:::"+LDAP_HOLDINGNO +",Expected is:::"+HoldingNo);
				mAssert(LDAP_MSPKHASARA.get(CurrentinvoCount), PlotKhasraNo.get(CurrentinvoCount), "Actual Plot No/Khasara No is :::"+LDAP_MSPKHASARA +",Expected is:::"+PlotKhasraNo);
				mAssert(LDAP_PLOTMSP.get(CurrentinvoCount), PlotNoMSP.get(CurrentinvoCount), "Actual Plot No(MSP) is:::"+LDAP_PLOTMSP +",Expected is:::"+PlotNoMSP);
				mAssert(LDAP_VILLAGE.get(CurrentinvoCount), Village.get(CurrentinvoCount), "Actual Village/Mohalla is :::"+LDAP_VILLAGE +",Expected is:::"+Village);
				mAssert(LDAP_MSPKHASARA.get(CurrentinvoCount), PlotKhasraNo.get(CurrentinvoCount), "Actual Plot No/Khasara No is :::"+LDAP_MSPKHASARA +",Expected is:::"+PlotKhasraNo);
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in searchByVariousCriteria script");
		}
	}

	//Code for Building Permit Approval Letter Assertion report
	public  void Building_Permit_Assertion()
	{
		try{
			api.PdfPatterns.BuildingPermitApprovalPDF(pdfoutput);
			{
				mAssert(BPAL_APPLICATIONNO.get(CurrentinvoCount), ApplicationNoforLandDev.get(CurrentinvoCount), "Actual Application No is :::"+BPAL_APPLICATIONNO +",Expected is :::"+ApplicationNoforLandDev);
				mAssert(BPAL_OWNERNAME.get(CurrentinvoCount), Bpr_OwnerFullName.get(CurrentinvoCount), "Actual Owner Name is :::"+BPAL_OWNERNAME +",Expected is :::"+Bpr_OwnerFullName);
				mAssert(BPAL_WARD.get(CurrentinvoCount), Bpr_ward.get(CurrentinvoCount), "Actual Ward No is :::"+BPAL_WARD +",Expected is :::"+Bpr_ward);
				mAssert(BPAL_KHATANO.get(CurrentinvoCount), Bpr_KhataNo.get(CurrentinvoCount), "Actual Khata No is :::"+BPAL_KHATANO +",Expected is:::"+Bpr_KhataNo);
				mAssert(BPAL_HOLDINGNO.get(CurrentinvoCount), Bpr_HoldingNo.get(CurrentinvoCount), "Actual Holding No is:::"+BPAL_HOLDINGNO +",Expected is:::"+Bpr_HoldingNo);
				mAssert(BPAL_VILLAGE.get(CurrentinvoCount), Bpr_Village.get(CurrentinvoCount), "Actual Village/Mohalla is :::"+BPAL_VILLAGE +",Expected is:::"+Bpr_Village);

			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in searchByVariousCriteria script");
		}
	}

	//Code for Occupancy Certificate Approval Letter Assertion report
	public void Occupancy_Certificate_Assertion() 
	{
		try{
			api.PdfPatterns.OccupancyCertificateApprovalPDF(pdfoutput);
			{
				mAssert(OCAL_APPLNO.get(CurrentinvoCount), ApplicationNoforLandDev.get(CurrentinvoCount), "Actual Application No is :::"+OCAL_APPLNO +",Expected is :::"+applicationNo);
				//	mAssert(OCAL_OWNERNAME.get(CurrentinvoCount), Nameofowner.get(CurrentinvoCount), "Actual Owner Name is :::"+OCAL_OWNERNAME +",Expected is :::"+Bpr_Nameofowner);
				/*mAssert(OCAL_WARD.get(CurrentinvoCount), Bpr_ward.get(CurrentinvoCount), "Actual Ward No is :::"+LDAP_WARD +",Expected is :::"+Bpr_ward);
				mAssert(OCAL_KHATANO.get(CurrentinvoCount), Bpr_KhataNo.get(CurrentinvoCount), "Actual Khata No is :::"+OCAL_KHATANO +",Expected is:::"+Bpr_KhataNo);
				mAssert(OCAL_HOLDINGNO.get(CurrentinvoCount), Bpr_HoldingNo.get(CurrentinvoCount), "Actual Holding No is:::"+LDAP_HOLDINGNO +",Expected is:::"+Bpr_HoldingNo);
				mAssert(OCAL_PLOTNOMSP.get(CurrentinvoCount), Bpr_PlotNoMSP.get(CurrentinvoCount), "Actual Plot No(MSP) is:::"+OCAL_PLOTNOMSP +",Expected is:::"+Bpr_PlotNoMSP);
				mAssert(OCAL_VILLAGE.get(CurrentinvoCount), Bpr_Village.get(CurrentinvoCount), "Actual Village/Mohalla is :::"+LDAP_VILLAGE +",Expected is:::"+Village);
				mAssert(OCAL_PLOTNOCS.get(CurrentinvoCount), Bpr_Village.get(CurrentinvoCount), "Actual Village/Mohalla is :::"+OCAL_PLOTNOCS +",Expected is:::"+Village);*/

			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in searchByVariousCriteria script");
		}
	}



	//Code for Letter Of Intimation receipt (LOI) Assertion report
	public void ALD_TPLOIReceiptPDF() 
	{
		try{
			api.PdfPatterns.TPLOIReceiptPDF(pdfoutput);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(TPLOI_AppliNo.get(CurrentinvoCount), ApplicationNoforLandDev.get(CurrentinvoCount), "Actual Application No is :::"+TPLOI_AppliNo +",Expected is :::"+applicationNo);
				mAssert(TPLOI_ReceivedFromName.get(CurrentinvoCount), Nameofowner.get(CurrentinvoCount), "Actual Owner Name is :::"+TPLOI_ReceivedFromName.get(CurrentinvoCount) +",Expected is :::"+Bpr_OwnerFullName.get(CurrentinvoCount));
				mAssert(TPLOI_LOIDate.get(CurrentinvoCount), Scrutiny_LoiDate.get(CurrentinvoCount), "Actual LOI Date is:::"+TPLOI_LOIDate +",Expected is:::"+Scrutiny_LoiDate);
				mAssert(TPLOI_LOINo.get(CurrentinvoCount),Scrutiny_LoiNo.get(CurrentinvoCount), "Actual LOI No is :::"+TPLOI_LOINo +",Expected is:::"+Scrutiny_LoiNo);
				mAssert(TPLOI_TotalRcvdAmt1.get(CurrentinvoCount),Scrutiny_Totalamt.get(CurrentinvoCount), "Actual total LOI Amount is:::"+TPLOI_TotalRcvdAmt1 +",Expected is:::"+Scrutiny_Totalamt);
				mAssert(TPLOI_ModeBankName.get(CurrentinvoCount),BankName.get(CurrentinvoCount), "Actual Holding No is:::"+TPLOI_ModeBankName +",Expected is:::"+BankName);
				mAssert(TPLOI_ModeNo.get(CurrentinvoCount), chqDdNo.get(CurrentinvoCount), "Actual Plot No(MSP) is:::"+TPLOI_ModeNo +",Expected is:::"+chqDdNo);
				mAssert(TPLOI_ModeDate.get(CurrentinvoCount), ChqDDDate.get(CurrentinvoCount), "Actual Village/Mohalla is :::"+TPLOI_ModeDate +",Expected is:::"+Village);
				mAssert(TPLOI_PaymentMode.get(CurrentinvoCount), PaymentMode.get(CurrentinvoCount), "Actual Village/Mohalla is :::"+TPLOI_PaymentMode +",Expected is:::"+Village);
			}
			//mAssert(TPLOI_TotalRcvdAmt1, Bpr_Village, "Total RcvdAmt1 is :::"+TPLOI_TotalRcvdAmt1 +",Expected is:::"+Village);

		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in searchByVariousCriteria script");
		}
	}

	public void OC_TPLOIReceiptPDF() 
	{
		try{
			api.PdfPatterns.TPLOIReceiptPDF(pdfoutput);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(TPLOI_AppliNo.get(CurrentinvoCount), ApplicationNoforLandDev.get(CurrentinvoCount), "Actual Application No is :::"+TPLOI_AppliNo +",Expected is :::"+applicationNo);
				mAssert(TPLOI_ReceivedFromName.get(CurrentinvoCount), Nameofowner.get(CurrentinvoCount), "Actual Owner Name is :::"+TPLOI_ReceivedFromName.get(CurrentinvoCount) +",Expected is :::"+Bpr_OwnerFullName.get(CurrentinvoCount));
				mAssert(TPLOI_LOIDate.get(CurrentinvoCount), Scrutiny_LoiDate.get(CurrentinvoCount), "Actual LOI Date is:::"+TPLOI_LOIDate +",Expected is:::"+Scrutiny_LoiDate);
				mAssert(TPLOI_LOINo.get(CurrentinvoCount),Scrutiny_LoiNo.get(CurrentinvoCount), "Actual LOI No is :::"+TPLOI_LOINo +",Expected is:::"+Scrutiny_LoiNo);
				mAssert(TPLOI_TotalRcvdAmt1.get(CurrentinvoCount),Scrutiny_Totalamt.get(CurrentinvoCount), "Actual total LOI Amount is:::"+TPLOI_TotalRcvdAmt1 +",Expected is:::"+Scrutiny_Totalamt);
				mAssert(TPLOI_ModeBankName.get(CurrentinvoCount),BankName.get(CurrentinvoCount), "Actual Holding No is:::"+TPLOI_ModeBankName +",Expected is:::"+BankName);
				mAssert(TPLOI_ModeNo.get(CurrentinvoCount), chqDdNo.get(CurrentinvoCount), "Actual Plot No(MSP) is:::"+TPLOI_ModeNo +",Expected is:::"+chqDdNo);
				mAssert(TPLOI_ModeDate.get(CurrentinvoCount), ChqDDDate.get(CurrentinvoCount), "Actual Village/Mohalla is :::"+TPLOI_ModeDate +",Expected is:::"+Village);
				mAssert(TPLOI_PaymentMode.get(CurrentinvoCount), PaymentMode.get(CurrentinvoCount), "Actual Village/Mohalla is :::"+TPLOI_PaymentMode +",Expected is:::"+Village);
			}
			//mAssert(TPLOI_TotalRcvdAmt1, Bpr_Village, "Total RcvdAmt1 is :::"+TPLOI_TotalRcvdAmt1 +",Expected is:::"+Village);

		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in searchByVariousCriteria script");
		}
	}



	//Code for Letter Of Intimation Letter Assertion report
	public void Land_Dev_TP_Intimation_Letter_PDF() 
	{
		try{
			api.PdfPatterns.IntimationTownPlanningPDF(pdfoutput);
			
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in searchByVariousCriteria script");
		}
	}


	public void Building_per_TP_Intimation_Letter_PDF() 
	{
		try{
			api.PdfPatterns.IntimationTownPlanningPDF(pdfoutput);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(BP_Intim_AppNo.get(CurrentinvoCount), ApplicationNoforLandDev.get(CurrentinvoCount), "Actual Application No is ::"+BP_Intim_AppNo.get(CurrentinvoCount) +",Expected is:::"+ApplicationNoforLandDev.get(CurrentinvoCount));
				mAssert(BP_Intim_AppName.get(CurrentinvoCount), Bpr_AppfullnmWdTitle.get(CurrentinvoCount), "Actual Applicant Full name without title No is :::"+BP_Intim_AppName.get(CurrentinvoCount) +",Expected is :::"+Bpr_AppfullnmWdTitle.get(CurrentinvoCount));
				mAssert(BP_Intim_Print_date.get(CurrentinvoCount), Applicationdate.get(CurrentinvoCount), "Actual Intimation Print Date :::"+BP_Intim_Print_date.get(CurrentinvoCount) +",Expected is :::"+Applicationdate.get(CurrentinvoCount));
				//mAssert(BP_Intim_TotalPaybleamt.get(CurrentinvoCount), Scrutiny_Totalamt.get(CurrentinvoCount), "Actual Intimation letter Total payble amount is:::"+BP_Intim_TotalPaybleamt.get(CurrentinvoCount) +",Expected is:::"+Scrutiny_Totalamt.get(CurrentinvoCount));
				//mAssert(BP_Intim_AppDate.get(CurrentinvoCount),Applicationdate.get(CurrentinvoCount), "Actual Application date is :::"+BP_Intim_AppDate.get(CurrentinvoCount) +",Expected is:::"+Applicationdate.get(CurrentinvoCount));
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in searchByVariousCriteria script");
		}
	}

	public void Occupancy_Certificate_TP_Intimation_Letter_PDF() 
	{
		try{
			api.PdfPatterns.IntimationTownPlanningPDF(pdfoutput);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(BP_Intim_AppNo.get(CurrentinvoCount), OC_AppNo.get(CurrentinvoCount), "Actual Application No is ::"+BP_Intim_AppNo.get(CurrentinvoCount) +",Expected is:::"+OC_AppNo.get(CurrentinvoCount));
				mAssert(BP_Intim_AppDate.get(CurrentinvoCount),OC_Applicationdate.get(CurrentinvoCount), "Actual Application date is :::"+BP_Intim_AppDate.get(CurrentinvoCount) +",Expected is:::"+OC_Applicationdate.get(CurrentinvoCount));
				mAssert(BP_Intim_AppName.get(CurrentinvoCount), Oc_ApplicantNamewdoutTitle.get(CurrentinvoCount), "Actual Applicant Full name without title No is :::"+BP_Intim_AppName.get(CurrentinvoCount) +",Expected is :::"+Oc_ApplicantNamewdoutTitle.get(CurrentinvoCount));
				mAssert(BP_Intim_Print_date.get(CurrentinvoCount), OC_Applicationdate.get(CurrentinvoCount), "Actual Intimation Print Date :::"+BP_Intim_Print_date.get(CurrentinvoCount) +",Expected is :::"+OC_Applicationdate.get(CurrentinvoCount));
				mAssert(BP_Intim_TotalPaybleamt.get(CurrentinvoCount), Scrutiny_Totalamt.get(CurrentinvoCount), "Actual Intimation letter Total payble amount is:::"+BP_Intim_TotalPaybleamt.get(CurrentinvoCount) +",Expected is:::"+Scrutiny_Totalamt.get(CurrentinvoCount));
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in searchByVariousCriteria script");
		}
	}


	//Code for Site Inspection report Assertion report for Land Development Plan
	public  void TPSiteInspectionLetter_Application_Dev_plan() 
	{
		try{

			SCR_SiteInspectEmpName.add(mGetPropertyFromFile("tp_SiteInspectEmpNmdata"));
			SCR_SiteInspectionEmpDate.add(mGetPropertyFromFile("tp_SiteInspectDatedata"));
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				/*mAssert(SIL_appname.get(CurrentinvoCount), AppfullnmWdTitle.get(CurrentinvoCount), "Actual Applicant name in site inspection letter is:::"+SIL_appname.get(CurrentinvoCount) +",Expected is:::"+AppfullnmWdTitle.get(CurrentinvoCount));
				mAssert(SIL_serviceName.get(CurrentinvoCount),ServiceNameforTownPlanning.get(CurrentinvoCount), "Actual Service Name in site inspection letter is :::"+SIL_serviceName.get(CurrentinvoCount) +",Expected is:::"+ServiceNameforTownPlanning.get(CurrentinvoCount));
				mAssert(SIL_ApplicationNo.get(CurrentinvoCount), ApplicationNoforLandDev.get(CurrentinvoCount), "Actual Application No in site inspection letter is :::"+SIL_ApplicationNo.get(CurrentinvoCount) +",Expected is :::"+ApplicationNoforLandDev.get(CurrentinvoCount));
				mAssert(SIL_ward1.get(CurrentinvoCount), ward.get(CurrentinvoCount), "Actual Ward No is:::"+SIL_ward1.get(CurrentinvoCount) +",Expected is:::"+ward.get(CurrentinvoCount));
				mAssert(SIL_KhataNo.get(CurrentinvoCount), PlotKhasraNo.get(CurrentinvoCount), "Actual Plot Khasara no  is :::"+SIL_KhataNo.get(CurrentinvoCount)+",Expected is:::"+PlotKhasraNo.get(CurrentinvoCount));
				mAssert(SIL_maujano.get(CurrentinvoCount), MaujaNo.get(CurrentinvoCount), "Actual Mauja No is :::"+SIL_maujano.get(CurrentinvoCount) +",Expected is:::"+MaujaNo.get(CurrentinvoCount));
				mAssert(SIL_plotkhasaranocs.get(CurrentinvoCount), KhataNo.get(CurrentinvoCount), "Actual Khata No is:::"+SIL_plotkhasaranocs.get(CurrentinvoCount) +",Expected is:::"+KhataNo.get(CurrentinvoCount));
				mAssert(SIL_HoldingNo.get(CurrentinvoCount), HoldingNo.get(CurrentinvoCount), "Actual Holding No is :::"+SIL_HoldingNo.get(CurrentinvoCount) +",Expected is:::"+HoldingNo.get(CurrentinvoCount));
				mAssert(SIL_Locality.get(CurrentinvoCount), Village.get(CurrentinvoCount), "Actual Village/Mohalla is :::"+SIL_Locality.get(CurrentinvoCount) +",Expected is:::"+Village.get(CurrentinvoCount));
				mAssert(SIL_TojiNo.get(CurrentinvoCount), TojiNo.get(CurrentinvoCount), "Actual Toji No is :::"+SIL_TojiNo.get(CurrentinvoCount) +",Expected is:::"+TojiNo.get(CurrentinvoCount));*/

			}

		}


		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in searchByVariousCriteria script");
		}
	}



	//Code for Site Inspection report Assertion report for Application for Building permit
	public  void TPSiteInspectionLetter_Building_Permit() 
	{
		try{
			System.out.println("======================");

			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(SIL_appname.get(CurrentinvoCount), Bpr_AppfullnmWdTitle.get(CurrentinvoCount), "Actual Applicant name in site inspection letter is:::"+SIL_appname.get(CurrentinvoCount) +",Expected is:::"+Bpr_AppfullnmWdTitle.get(CurrentinvoCount));
				mAssert(SIL_serviceName.get(CurrentinvoCount),ServiceNameforTownPlanning.get(CurrentinvoCount), "Actual Service Name in site inspection letter is :::"+SIL_serviceName.get(CurrentinvoCount) +",Expected is:::"+ServiceNameforTownPlanning.get(CurrentinvoCount));
				mAssert(SIL_ApplicationNo.get(CurrentinvoCount), ApplicationNoforLandDev.get(CurrentinvoCount), "Actual Application No in site inspection letter is :::"+SIL_ApplicationNo.get(CurrentinvoCount) +",Expected is :::"+ApplicationNoforLandDev.get(CurrentinvoCount));
				mAssert(SIL_ward1.get(CurrentinvoCount), Bpr_ward.get(CurrentinvoCount), "Actual Ward No is:::"+SIL_ward1.get(CurrentinvoCount) +",Expected is:::"+Bpr_ward.get(CurrentinvoCount));
				mAssert(SIL_KhataNo.get(CurrentinvoCount), Bpr_PlotKhasraNo.get(CurrentinvoCount), "Actual Plot Khasara no  is :::"+SIL_KhataNo.get(CurrentinvoCount)+",Expected is:::"+Bpr_PlotKhasraNo.get(CurrentinvoCount));
				mAssert(SIL_maujano.get(CurrentinvoCount), Bpr_MaujaNo.get(CurrentinvoCount), "Actual Mauja No is :::"+SIL_maujano.get(CurrentinvoCount) +",Expected is:::"+Bpr_MaujaNo.get(CurrentinvoCount));
				mAssert(SIL_plotkhasaranocs.get(CurrentinvoCount), Bpr_KhataNo.get(CurrentinvoCount), "Actual Khata No is:::"+SIL_plotkhasaranocs.get(CurrentinvoCount) +",Expected is:::"+Bpr_KhataNo.get(CurrentinvoCount));
				mAssert(SIL_HoldingNo.get(CurrentinvoCount), Bpr_HoldingNo.get(CurrentinvoCount), "Actual Holding No is :::"+SIL_HoldingNo.get(CurrentinvoCount) +",Expected is:::"+Bpr_HoldingNo.get(CurrentinvoCount));
				mAssert(SIL_Locality.get(CurrentinvoCount), Bpr_Village.get(CurrentinvoCount), "Actual Village/Mohalla is :::"+SIL_Locality.get(CurrentinvoCount) +",Expected is:::"+Bpr_Village.get(CurrentinvoCount));
				mAssert(SIL_TojiNo.get(CurrentinvoCount), Bpr_TojiNo.get(CurrentinvoCount), "Actual Toji No is :::"+SIL_TojiNo.get(CurrentinvoCount) +",Expected is:::"+Bpr_TojiNo.get(CurrentinvoCount));


			}
			System.out.println("Name is ::"+SIL_officer + SCR_SiteInspectEmpName);
			System.out.println("date is ::"+SIL_Inspectiondate + SCR_SiteInspectionEmpDate);

			mAssert(SIL_officer.get(CurrentinvoCount).toString(), SCR_SiteInspectEmpName.get(CurrentinvoCount).toString(), "Actual Site Inspection inspector name is:::"+SIL_officer.get(CurrentinvoCount).toString() +",Expected is:::"+SCR_SiteInspectEmpName.get(CurrentinvoCount).toString());
			mAssert(SIL_Inspectiondate.get(CurrentinvoCount),SCR_SiteInspectionEmpDate.get(CurrentinvoCount), "Actual Site Inspection Date is :::"+SIL_Inspectiondate.get(CurrentinvoCount) +",Expected is :::"+SCR_SiteInspectionEmpDate.get(CurrentinvoCount));		
		}


		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in searchByVariousCriteria script");
		}
	}










	//Code for Site Inspection report Assertion report
	public void TPtrutiPatraLetter() 
	{
		try{
			api.PdfPatterns.TP_trutipatraPdf(pdfoutput);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in searchByVariousCriteria script");
		}
	}


	public void Ald_ViewandAssertform()
	{

		String scrutiny_appmbno=mGetText("id", mGetPropertyFromFile("tp_ApplicantMobileNoid"),"value");
		SCRLD_appmbno.add(scrutiny_appmbno);
		System.out.println("Applicant Mobile No is in Scutiny : "+scrutiny_appmbno);
		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			mAssert(ApplicantMobileNo.get(CurrentinvoCount), SCRLD_appmbno.get(CurrentinvoCount), " Applicant Mobile No Does not match with the actual::"+SCRLD_appmbno.get(CurrentinvoCount)+ "in scrutiny View form,Expected is::"+ApplicantMobileNo.get(CurrentinvoCount));
		}

		String scrutiny_appEmailId=mGetText("id", mGetPropertyFromFile("tp_ApplicantEmailid"),"value");
		SCRLD_appEmailId.add(scrutiny_appEmailId);
		System.out.println("Applicant Email Id is in Scutiny : "+scrutiny_appEmailId);
		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			mAssert(ApplicantEmailId.get(CurrentinvoCount), SCRLD_appEmailId.get(CurrentinvoCount), "Applicant Email ID Does not match with the actual::"+SCRLD_appEmailId.get(CurrentinvoCount)+"in scrutiny View form,Expected is::"+ApplicantEmailId.get(CurrentinvoCount));
		}

		/*String scrutiny_ownname=mGetText("id", mGetPropertyFromFile(""),"value");
		System.out.println("Applicant Name is in Scutiny : "+scrutiny_ownname);
		mAssert(AlD_Nameofowner, scrutiny_ownname, "Owner Name Does not match with the actual in scrutiny View form");*/

		String scrutiny_OwnMbNo=mGetText("id", mGetPropertyFromFile("tp_OwnerMobileNoid"),"value");
		SCRLD_OwnMbNo.add(scrutiny_OwnMbNo);
		System.out.println("Owner Mobile No is in Scutiny : "+SCRLD_OwnMbNo);
		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			mAssert(OwnerMobileNo.get(CurrentinvoCount), SCRLD_OwnMbNo.get(CurrentinvoCount), "Owner Mobile No"+ OwnerMobileNo +" "+"Does not match with the actual in scrutiny View form"+SCRLD_OwnMbNo);
		}

		String scrutiny_OwnEmailId=mGetText("id", mGetPropertyFromFile("tp_OwnerEmailid"),"value");
		SCRLD_OwnEmailId.add(scrutiny_OwnEmailId);
		System.out.println("Owner Email ID is in Scutiny : "+scrutiny_OwnEmailId);
		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			mAssert(OwnerEmailId.get(CurrentinvoCount), SCRLD_OwnEmailId.get(CurrentinvoCount), "Owner Email ID Does not match with the actual in scrutiny View form");
		}

		String scrutiny_AddLine1=mGetText("id", mGetPropertyFromFile("tp_AddLine1id"),"value");
		System.out.println("Address Line 1 is in Scutiny : "+scrutiny_AddLine1);
		SCRLD_AddLine1.add(scrutiny_AddLine1);
		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			mAssert(Addline1.get(CurrentinvoCount), SCRLD_AddLine1.get(CurrentinvoCount), "Address Line 1 Does not match with the actual in scrutiny View form");
		}

		String scrutiny_AddLine2=mGetText("id", mGetPropertyFromFile("tp_AddLine2id"),"value");
		System.out.println("Address Line 2 is in Scutiny : "+scrutiny_AddLine2);
		SCRLD_AddLine2.add(scrutiny_AddLine2);
		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			mAssert(Addline2.get(CurrentinvoCount), SCRLD_AddLine2.get(CurrentinvoCount), "Address Line 2 Does not match with the actual in scrutiny View form");
		}


		String scrutiny_Pincode=mGetText("id", mGetPropertyFromFile("tp_OwnerPinCodeid"),"value");
		SCRLD_Pincode.add(scrutiny_Pincode);
		System.out.println("Pincode is in Scutiny : "+scrutiny_Pincode);
		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			mAssert(Pincode.get(CurrentinvoCount), SCRLD_Pincode.get(CurrentinvoCount), "Pincode Does not match with the actual in scrutiny View form");
		}

		readingownerdetails();


		String scrutiny_Ward=mGetText("id", mGetPropertyFromFile("tp_WardNoid"),"value");
		SCRLD_Ward.add(scrutiny_Ward);
		System.out.println("Ward is in Scutiny : "+scrutiny_Ward);
		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			mAssert(ward.get(CurrentinvoCount), SCRLD_Ward.get(CurrentinvoCount), "Ward Does not match with the actual in scrutiny View form");
		}

		String scrutiny_PlotKhasaraNo=mGetText("id", mGetPropertyFromFile("tp_PlotNoid"),"value");
		SCRLD_PlotKhasaraNo.add(scrutiny_PlotKhasaraNo);
		System.out.println("Plot (CS)/ Khasra No  is in Scutiny : "+scrutiny_PlotKhasaraNo);
		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			mAssert(PlotKhasraNo.get(CurrentinvoCount), SCRLD_PlotKhasaraNo.get(CurrentinvoCount), "Plot (CS)/ Khasra No  Does not match with the actual in scrutiny View form");
		}

		String scrutiny_PlotMSP=mGetText("id", mGetPropertyFromFile("tp_MSPNoid"),"value");
		SCRLD_PlotMSP.add(scrutiny_PlotMSP);
		System.out.println("Plot (CS)/ Khasra No  is in Scutiny : "+scrutiny_PlotMSP);
		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			mAssert(PlotNoMSP.get(CurrentinvoCount), SCRLD_PlotMSP.get(CurrentinvoCount), "Plot No. (MSP) Does not match with the actual in scrutiny View form");
		}

		String scrutiny_MaujaNo=mGetText("id", mGetPropertyFromFile("tp_MaujaNoid"),"value");
		SCRLD_MaujaNo.add(scrutiny_MaujaNo);
		System.out.println("Mauja No  is in Scutiny : "+scrutiny_MaujaNo);
		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			mAssert(MaujaNo.get(CurrentinvoCount), SCRLD_MaujaNo.get(CurrentinvoCount), "Mauja No Does not match with the actual,"+MaujaNo+""+"in scrutiny View form"+SCRLD_MaujaNo);
		}

		String scrutiny_HoldingNo=mGetText("id", mGetPropertyFromFile("tp_HoldingNoid"),"value");
		SCRLD_HoldingNo.add(scrutiny_HoldingNo);
		System.out.println("Holding No  is in Scutiny : "+scrutiny_HoldingNo);
		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			mAssert(HoldingNo.get(CurrentinvoCount), SCRLD_HoldingNo.get(CurrentinvoCount), "Holding No Does not match with the actual in scrutiny View form");
		}

		String scrutiny_KhataNo=mGetText("id", mGetPropertyFromFile("tp_KhataNoid"),"value");
		SCRLD_KhataNo.add(scrutiny_KhataNo);
		System.out.println("Khata No is in Scutiny : "+scrutiny_KhataNo);
		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			mAssert(KhataNo.get(CurrentinvoCount), SCRLD_KhataNo.get(CurrentinvoCount), "Khata No Does not match with the actual in scrutiny View form");
		}

		String scrutiny_Tojino=mGetText("id", mGetPropertyFromFile("tp_TojiNoid"),"value");
		SCRLD_Tojino.add(scrutiny_Tojino);
		System.out.println("Toji No is in Scutiny : "+scrutiny_Tojino);
		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			mAssert(TojiNo.get(CurrentinvoCount), SCRLD_Tojino.get(CurrentinvoCount), "Toji No Does not match with the actual in scrutiny View form");
		}

		String scrutiny_Village=mGetText("id", mGetPropertyFromFile("tp_VillageNoid"),"value");
		SCRLD_Village.add(scrutiny_Village);
		System.out.println("Village/Mohallah is in Scutiny : "+scrutiny_Village);
		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			mAssert(Village.get(CurrentinvoCount), SCRLD_Village.get(CurrentinvoCount), "Village/Mohallah Does not match with the actual in scrutiny View form");
		}

		String scrutiny_TotalCoverHect=mGetText("id", mGetPropertyFromFile("tp_CoveredAreaHecterid"),"value");
		SCRLD_TotalCoverHect.add(scrutiny_TotalCoverHect);
		System.out.println("Total Covered Area in Hecter is in Scutiny : "+scrutiny_TotalCoverHect);

		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			System.out.println("Total Covered Area in Hecter is in Scutiny Process Arraylis is::"+SCRLD_TotalCoverHect.get(CurrentinvoCount)+" "+"Total Covered Area in Hecter is at Application form  Arraylist is::"+CoveredAreainHect.get(CurrentinvoCount));
			mAssert(CoveredAreainHect.get(CurrentinvoCount), SCRLD_TotalCoverHect.get(CurrentinvoCount), "Total Covered Area in Hecter Does not match with the actual in scrutiny View form actual::"+SCRLD_TotalCoverHect.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+CoveredAreainHect.get(CurrentinvoCount));
		}

		String scrutiny_TotalCoverft2=mGetText("id", mGetPropertyFromFile("tp_CoveredAreaInfeetid"),"value");
		SCRLD_TotalCoverft2.add(scrutiny_TotalCoverft2);
		System.out.println("Total Covered Area in feet square is in Scutiny : "+scrutiny_TotalCoverft2);
		//mAssert(CoveredAreainTotalft2.get(CurrentinvoCount), SCRLD_TotalCoverft2.get(CurrentinvoCount), "Total Covered Area in feet square Does not match with the actual in scrutiny View form");


		//Usage Type table reading to be write
		readingUsagetype();
		mCustomWait(2000);

		String scrutiny_ParkingAreamt2=mGetText("id", mGetPropertyFromFile("tp_ParkingAreaid"),"value");
		SCRLD_ParkingAreamt2.add(scrutiny_ParkingAreamt2);
		System.out.println("Parking Area in Meter square is in Scutiny : "+scrutiny_ParkingAreamt2);

		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			System.out.println("Parking Area in Meter is in Scutiny Process Arraylis is::"+SCRLD_ParkingAreamt2.get(CurrentinvoCount)+" "+"Parking Area in Meter is at Application form  Arraylist is::"+ParkingAreamt2.get(CurrentinvoCount));
			mAssert(ParkingAreamt2.get(CurrentinvoCount), SCRLD_ParkingAreamt2.get(CurrentinvoCount), "Parking Area in Meter square Does not match with the actual in scrutiny View form actual::"+SCRLD_ParkingAreamt2.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+ParkingAreamt2.get(CurrentinvoCount));
		}

		String scrutiny_ParkingAreaft2=mGetText("id", mGetPropertyFromFile("tp_ParkingAreaFtid"),"value");
		SCRLD_ParkingAreaft2.add(scrutiny_ParkingAreaft2);
		System.out.println("Parking Area in feet square is in Scutiny : "+scrutiny_ParkingAreaft2);

		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			System.out.println("Parking Area in feet square is in Scutiny Process Arraylis is::"+SCRLD_ParkingAreaft2.get(CurrentinvoCount)+" "+"Parking Area in feet square is at Application form  Arraylist is::"+ParkingAreaft2.get(CurrentinvoCount));
			mAssert(ParkingAreaft2.get(CurrentinvoCount), SCRLD_ParkingAreaft2.get(CurrentinvoCount), "Parking Area in feet square Does not match with the actual in scrutiny View form actual::"+SCRLD_ParkingAreaft2.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+ParkingAreaft2.get(CurrentinvoCount));
		}

		String scrutiny_wideningofroadmt=mGetText("id", mGetPropertyFromFile("tp_FreeAreaid"),"value");
		SCRLD_wideningofroadmt.add(scrutiny_wideningofroadmt);
		System.out.println("Area left for widening of road(If applicable) in meter is in Scutiny : "+scrutiny_wideningofroadmt);

		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			System.out.println("Area left for widening of road(If applicable) in meter is in Scutiny Process Arraylis is::"+SCRLD_wideningofroadmt.get(CurrentinvoCount)+" "+"Area left for widening of road(If applicable) in meter is at Application form  Arraylist is::"+wideningofroadmt.get(CurrentinvoCount));
			mAssert(wideningofroadmt.get(CurrentinvoCount), SCRLD_wideningofroadmt.get(CurrentinvoCount), "Area left for widening of road(If applicable) in meter Does not match with the actual in scrutiny View form actual::"+SCRLD_wideningofroadmt.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+wideningofroadmt.get(CurrentinvoCount));
		}

		String scrutiny_wideningofroadft=mGetText("id", mGetPropertyFromFile("tp_FreeAreaid"),"value");
		SCRLD_wideningofroadft.add(scrutiny_wideningofroadft);
		System.out.println("Area left for widening of road(If applicable) in Feet is in Scutiny : "+scrutiny_wideningofroadft);

		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			System.out.println("Area left for widening of road(If applicable) in Feet is in Scutiny Process Arraylis is::"+SCRLD_wideningofroadft.get(CurrentinvoCount)+" "+"Area left for widening of road(If applicable) in Feet is at Application form  Arraylist is::"+wideningofroadft.get(CurrentinvoCount));
			mAssert(wideningofroadft.get(CurrentinvoCount), SCRLD_wideningofroadft.get(CurrentinvoCount), "Area left for widening of road(If applicable) in meter Does not match with the actual in scrutiny View form actual::"+SCRLD_wideningofroadft.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+wideningofroadft.get(CurrentinvoCount));
		}

		String scrutiny_projectName=mGetText("id", mGetPropertyFromFile("tp_ProjectNameid"),"value");
		SCRLD_projectName.add(scrutiny_projectName);
		System.out.println(" Project Name is in Scutiny : "+scrutiny_projectName);
		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			mAssert(ProjectName.get(CurrentinvoCount), SCRLD_projectName.get(CurrentinvoCount), "Project Name Does not match with the actual in scrutiny View form");
		}

		String scrutiny_SchemeName=mGetText("id", mGetPropertyFromFile("tp_SchemeNameid"),"value");
		SCRLD_SchemeName.add(scrutiny_SchemeName);
		System.out.println(" Project Name is in Scutiny : "+scrutiny_SchemeName);
		if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
			mAssert(SchemeName.get(CurrentinvoCount), SCRLD_SchemeName.get(CurrentinvoCount), "Scheme Name Does not match with the actual in scrutiny View form");
		}

		//Technical person Tabel reading for Building Permission
		if(mGetPropertyFromFile("agencyType").equalsIgnoreCase("Architect"))
		{

			AgencyreadingtechnicalPerson();
		}
		else
		{
			CitizenreadingtechnicalPerson();

		}
		mCustomWait(2000);

	}


	//Additional Owner details table reading
	public  void  readingownerdetails()
	{
		try {
			WebElement table = driver.findElement(By.id("gview_gridDAPAdditionalOwner"));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount = rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

			// Iterate to get the value of each cell in table along with element id
			//int Rowno = 0;
			for (int a=0;a<rwcount;a++) 		
			{
				List<WebElement> cols = rows.get(a).findElements(By.xpath("td"));
				System.out.println("NUMBER OF columns IN THIS TABLE = " + cols.size());
				//	int Columnno = 0;

				for (int b=0;b<cols.size();b++) 		
				{
					if(a>1)
					{

						if (b ==1) 
						{
							String NameofOwner = rows.get(a).findElement(By.xpath("./td[2]")).getText();
							mGenericWait();
							Nameofowner1.add(NameofOwner);
							System.out.println("Name of owner is :"+NameofOwner);

						}
						if (b == 2) 
						{
							String EmailId = rows.get(a).findElement(By.xpath("./td[3]")).getText();
							mGenericWait();
							System.out.println("Email Id is :"+EmailId.trim());
							OwnerEmailId1.add(EmailId);
						}
						if (b == 3) 
						{
							String MobileNo = rows.get(a).findElement(By.xpath("./td[4]")).getText();
							mGenericWait();
							System.out.println("Mobile No is :"+MobileNo);
							OwnerMobileNo1.add(MobileNo);

						}

					}
				}
			}
			System.out.println("Additional Owners are in scrutiny process :: "+Nameofowner1);
			System.out.println("Additional Owners email id are in scrutiny process  :: "+OwnerEmailId1);
			System.out.println("Additional Owners mobile No are in scrutiny process   :: "+OwnerMobileNo1);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//System.out.println("Additional Owners is in Scutiny Process Arraylis is::"+Nameofowner1.get(CurrentinvoCount)+" "+"Additional Owner is at Application form  Arraylist is::"+AddownerFullname.get(CurrentinvoCount));
				//System.out.println("Additional Owners email id in Scutiny Process Arraylis is::"+OwnerEmailId1.get(CurrentinvoCount)+" "+"Additional Owners email id is at Application form  Arraylist is::"+AddEmailId.get(CurrentinvoCount));
				//System.out.println("Additional Owners mobile No is in Scutiny Process Arraylis is::"+OwnerMobileNo1.get(CurrentinvoCount)+" "+"Additional Owners mobile No is at Application form  Arraylist is::"+AddMobileNo.get(CurrentinvoCount));
			}


			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(tpAddOwnerlName.get(CurrentinvoCount), Nameofowner1.get(CurrentinvoCount), "Additional Owner name does not match with each other actual::"+Nameofowner1.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+tpAddOwnerlName.get(CurrentinvoCount));
			}

			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(AddEmailId.get(CurrentinvoCount), OwnerEmailId1.get(CurrentinvoCount), "Additional Owner Email Id does not match with each other actual::"+OwnerEmailId1.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+AddEmailId.get(CurrentinvoCount));
			}

			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(AddMobileNo.get(CurrentinvoCount), OwnerMobileNo1.get(CurrentinvoCount), "Additional Owner Mobile No does not match with each other actual::"+OwnerMobileNo1.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+AddMobileNo.get(CurrentinvoCount));
			}


		}
		catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in readingownerdetails  method");
		}
	}




	//Additional Owner details table reading for Building Permission
	public  void  BuildingPermissionreadingownerdetails(String tabelid)
	{
		try {
			WebElement table = driver.findElement(By.id(tabelid));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount = rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

			// Iterate to get the value of each cell in table along with element id
			//int Rowno = 0;
			for (int a=0;a<rwcount;a++) 		
			{
				List<WebElement> cols = rows.get(a).findElements(By.xpath("td"));
				System.out.println("NUMBER OF columns IN THIS TABLE = " + cols.size());
				//	int Columnno = 0;

				for (int b=0;b<cols.size();b++) 		
				{
					if(a>1)
					{

						if (b ==1) 
						{
							String NameofOwner = rows.get(a).findElement(By.xpath("./td[2]")).getText();
							mGenericWait();
							Nameofowner1.add(NameofOwner);
							System.out.println("Name of owner is :"+NameofOwner.trim());

						}
						if (b == 2) 
						{
							String EmailId = rows.get(a).findElement(By.xpath("./td[3]")).getText();
							mGenericWait();
							System.out.println("Email Id is :"+EmailId);
							OwnerEmailId1.add(EmailId);
						}
						if (b == 3) 
						{
							String MobileNo = rows.get(a).findElement(By.xpath("./td[4]")).getText();
							mGenericWait();
							System.out.println("Mobile No is :"+MobileNo);
							OwnerMobileNo1.add(MobileNo);

						}

					}
				}
			}
			System.out.println("Additional Owners are in scrutiny process :: "+Nameofowner1);
			System.out.println("Additional Owners email id are in scrutiny process  :: "+OwnerEmailId1);
			System.out.println("Additional Owners mobile No are in scrutiny process   :: "+OwnerMobileNo1);

			/*if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Additional Owners is in Scutiny Process Arraylis is::"+Nameofowner1.get(CurrentinvoCount)+" "+"Additional Owner is at Application form  Arraylist is::"+Bpr_AdditionalownerName.get(CurrentinvoCount));
				System.out.println("Additional Owners email id in Scutiny Process Arraylis is::"+OwnerEmailId1.get(CurrentinvoCount)+" "+"Additional Owners email id is at Application form  Arraylist is::"+Bpr_AdditionalownerEmail.get(CurrentinvoCount));
				System.out.println("Additional Owners mobile No is in Scutiny Process Arraylis is::"+OwnerMobileNo1.get(CurrentinvoCount)+" "+"Additional Owners mobile No is at Application form  Arraylist is::"+Bpr_AdditionalownermbNo.get(CurrentinvoCount));

				//mAssert(Bpr_AdditionalownerName.get(CurrentinvoCount), Nameofowner1.get(CurrentinvoCount), "Name of owner does not match with each other");

			}//System.out.println("Additional owner Email Id  :: "+compareList(OwnerEmailId,OwnerEmailId1));
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(Bpr_AdditionalownerEmail.get(CurrentinvoCount), OwnerEmailId1.get(CurrentinvoCount), "Owner Email Id does not match with each other");

			}//System.out.println("Additional owner Mobile No  :: "+compareList(OwnerMobileNo,OwnerMobileNo1));
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(Bpr_AdditionalownermbNo.get(CurrentinvoCount), OwnerMobileNo1.get(CurrentinvoCount), "Owner Mobile No does not match with each other");
			}*/
		}
		catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in readingownerdetails  method");
		}
	}


	//Scrutiny View Form tabel reading of Usage type details
	public  void  readingUsagetype()
	{
		try {
			WebElement table = driver.findElement(By.id("buildingdetid"));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount = rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

			// Iterate to get the value of each cell in table along with element id
			int Rowno = 0;
			for (int a=0;a<rwcount;a++) 		
			{
				List<WebElement> cols = rows.get(a).findElements(By.xpath("td"));
				System.out.println("NUMBER OF columns IN THIS TABLE = " + cols.size());
				int Columnno = 0;

				for (int b=0;b<cols.size();b++) 		
				{
					if(a>0)
					{
						if (b == 0)
						{
							String UsageType = rows.get(a).findElement(By.xpath("./td[1]")).getText();
							mGenericWait();
							System.out.println("Usage Type. is :"+UsageType);
							UsageType1.add(UsageType);
						}						
						if (b == 1) 
						{String CoverAreaInHect = rows.get(a).findElement(By.xpath("./td[2]")).getText();
						mGenericWait();
						System.out.println("Cover Area in hecter is :"+CoverAreaInHect);
						CoveredAreainHect1.add(CoverAreaInHect);
						}
						if (b == 2) 
						{
							String CoverAreaInMt = rows.get(a).findElement(By.xpath("./td[3]")).getText();
							mGenericWait();
							System.out.println("Cover Area in Meter is :"+CoverAreaInMt);
							CoveredAreainft21.add(CoverAreaInMt);
						}
					}
				}
			}
			System.out.println("Usage type ain scrutiny are :: "+UsageType1);
			System.out.println("Covered Area In Hecter are  :: "+CoveredAreainHect1);
			System.out.println("Covered Area In Meter Square are  :: "+CoveredAreainft21);

			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				//mAssert(UsageType.get(CurrentinvoCount),UsageType1.get(CurrentinvoCount), "Usage Type dose not match with each other actual::"+UsageType1.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+UsageType.get(CurrentinvoCount));
			}
			System.out.println("Covered Area In Hecter  :: "+compareList(CoveredAreainHect,CoveredAreainHect1));

			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Covered Area In Hecter is in Scutiny Process Arraylis is::"+CoveredAreainHect1.get(CurrentinvoCount)+" "+"Covered Area In Hecter is at Application form  Arraylist is::"+CoveredAreainHect.get(CurrentinvoCount));
				////mAssert(CoveredAreainHect.get(CurrentinvoCount), CoveredAreainHect1.get(CurrentinvoCount), "Covered Area in Hecter dose not match with each other actual::"+CoveredAreainHect1.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+CoveredAreainHect.get(CurrentinvoCount));
			}

			System.out.println("Covered Area In Meter Square  :: "+compareList(CoveredAreainft2,CoveredAreainft21));
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Covered Area In Meter Square is in Scutiny Process Arraylis is::"+CoveredAreainft21.get(CurrentinvoCount)+" "+"Covered Area In Meter Square is at Application form  Arraylist is::"+CoveredAreainft2.get(CurrentinvoCount));
				System.out.println("Covered Area In Meter Square is in Scutiny Process Arraylis is::"+CoveredAreainft21.get(CurrentinvoCount)+" "+"Covered Area In Meter Square is at Application form  Arraylist is::"+CoveredAreainft2.get(CurrentinvoCount));//mAssert(CoveredAreainft2.get(CurrentinvoCount), CoveredAreainft21.get(CurrentinvoCount), "Covered Area in feet square dose not match with each other actual::"+CoveredAreainft21.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+CoveredAreainft2.get(CurrentinvoCount));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in readingUsagetype  method");
		}

	}		



	public  void  CitizenreadingtechnicalPerson()
	{
		try {
			WebElement table = driver.findElement(By.id("gview_gridDAPAdditionalTechPerson"));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount = rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

			// Iterate to get the value of each cell in table along with element id
			int Rowno = 0;
			for (int a=0;a<rwcount;a++) 		
			{
				List<WebElement> cols = rows.get(a).findElements(By.xpath("td"));
				System.out.println("NUMBER OF columns IN THIS TABLE = " + cols.size());
				int Columnno = 0;
				for (int b=0;b<cols.size();b++) 		
				{
					if(a>1)
					{
						if (b == 1)
						{
							String TechPersonType = rows.get(a).findElement(By.xpath("./td[2]")).getText();
							mGenericWait();
							System.out.println("Technical Person Type is :"+TechPersonType);
							TechPerType1.add(TechPersonType);
						}	

						if (b == 2) 
						{
							String TechPersonName = rows.get(a).findElement(By.xpath("./td[3]")).getText();
							mGenericWait();
							System.out.println("Technical Person Name is :"+TechPersonName);
							TechPerName1.add(TechPersonName);
							mCustomWait(1000);

						}
						if (b == 3) 
						{
							String LicenceNo = rows.get(a).findElement(By.xpath("./td[4]")).getText();
							mGenericWait();
							System.out.println("License/Empanelment No is :"+LicenceNo);
						}
						if (b == 4) 
						{
							String RegistartionNo = rows.get(a).findElement(By.xpath("./td[5]")).getText();
							mGenericWait();
							System.out.println("Registration No. is :"+RegistartionNo);
						}
						if (b == 5) 
						{
							String techPerMbNo = rows.get(a).findElement(By.xpath("./td[6]")).getText();
							mGenericWait();
							System.out.println("Mobile No. is :"+techPerMbNo);
						}
						if (b == 6) 
						{
							String techPeremailId = rows.get(a).findElement(By.xpath("./td[7]")).getText();
							mGenericWait();
							System.out.println("Email ID is :"+techPeremailId);
						}
					}

				}
			}

			System.out.println("Technical Person Name are :: "+TechPerName1);
			System.out.println("Technical Person Types are  :: "+TechPerType1);
			System.out.println("Technical Person Types are  :: "+compareList(TechPerType,TechPerType1));
			System.out.println("Technical Person Name are :: "+compareList(TechPerName,TechPerName1));
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(TechPerType1.get(CurrentinvoCount), TechPerType.get(CurrentinvoCount), "Technical Person type Dose not match");
			}
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(TechPerName1.get(CurrentinvoCount), TechPerName.get(CurrentinvoCount), "Technical Person Name Dose not match");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in AgencyreadingtechnicalPerson  method");
		}

	}		


	//Technical Person table reading when type of login is depart/Citizen
	public  void AgencyreadingtechnicalPerson()
	{
		try {
			WebElement table = driver.findElement(By.id("gview_gridDAPAdditionalTechPerson"));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount = rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

			// Iterate to get the value of each cell in table along with element id
			int Rowno = 0;
			for (int a=1;a<rwcount;a++) 		
			{
				List<WebElement> cols = rows.get(a).findElements(By.xpath("td"));
				System.out.println("NUMBER OF columns IN THIS TABLE = " + cols.size());
				int Columnno = 0;
				for (int b=0;b<cols.size();b++) 		
				{
					if(a>2)
					{
						if (b == 1)
						{
							String TechPersonType = rows.get(a).findElement(By.xpath("./td[2]")).getText();
							mGenericWait();
							System.out.println("Technical Person Type is :"+TechPersonType);
							TechPerType1.add(TechPersonType);
						}	

						if (b == 2) 
						{
							String TechPersonName = rows.get(a).findElement(By.xpath("./td[3]")).getText();
							mGenericWait();
							System.out.println("Technical Person Name is :"+TechPersonName);
							TechPerName1.add(TechPersonName);
							mCustomWait(1000);

						}
						if (b == 3) 
						{
							String LicenceNo = rows.get(a).findElement(By.xpath("./td[4]")).getText();
							mGenericWait();
							System.out.println("License/Empanelment No is :"+LicenceNo);
						}
						if (b == 4) 
						{
							String RegistartionNo = rows.get(a).findElement(By.xpath("./td[5]")).getText();
							mGenericWait();
							System.out.println("Registration No. is :"+RegistartionNo);
						}
						if (b == 5) 
						{
							String techPerMbNo = rows.get(a).findElement(By.xpath("./td[6]")).getText();
							mGenericWait();
							System.out.println("Mobile No. is :"+techPerMbNo);
						}
						if (b == 6) 
						{
							String techPeremailId = rows.get(a).findElement(By.xpath("./td[7]")).getText();
							mGenericWait();
							System.out.println("Email ID is :"+techPeremailId);
						}
					}

				}
			}

			System.out.println("Technical Person Name are :: "+TechPerName1);
			System.out.println("Technical Person Types 1 are  :: "+TechPerType1);
			System.out.println("Technical Person Types are  :: "+TechPerType);
			System.out.println("Technical Person Types are  :: "+compareList(TechPerType,TechPerType1));
			System.out.println("Technical Person Name are :: "+compareList(TechPerName,TechPerName1));
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(TechPerType.get(CurrentinvoCount),TechPerType1.get(CurrentinvoCount),"Technical Person type Assert Failed");
			}
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(TechPerName.get(CurrentinvoCount),TechPerName1.get(CurrentinvoCount), "Technical Person Name Assert Failed");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in readingtechnicalPerson  method");
		}

	}		

	//16/01/2017:Piyush For Building Permission
	public  void  BPR_AgencyreadingtechnicalPerson(String tabelid)
	{
		try {
			WebElement table = driver.findElement(By.id(tabelid));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount = rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

			// Iterate to get the value of each cell in table along with element id
			int Rowno = 0;
			for (int a=1;a<rwcount;a++) 		
			{
				List<WebElement> cols = rows.get(a).findElements(By.xpath("td"));
				System.out.println("NUMBER OF columns IN THIS TABLE = " + cols.size());
				int Columnno = 0;
				for (int b=0;b<cols.size();b++) 		
				{
					if(a>2)
					{
						if (b == 1)
						{
							String TechPersonType = rows.get(a).findElement(By.xpath("./td[2]")).getText();
							mGenericWait();
							System.out.println("Technical Person Type is :"+TechPersonType);
							TechPerType1.add(TechPersonType);
						}	

						if (b == 2) 
						{
							String TechPersonName = rows.get(a).findElement(By.xpath("./td[3]")).getText();
							mGenericWait();
							System.out.println("Technical Person Name is :"+TechPersonName);
							TechPerName1.add(TechPersonName);
							mCustomWait(1000);

						}
						if (b == 3) 
						{
							String LicenceNo = rows.get(a).findElement(By.xpath("./td[4]")).getText();
							mGenericWait();
							System.out.println("License/Empanelment No is :"+LicenceNo);
						}
						if (b == 4) 
						{
							String RegistartionNo = rows.get(a).findElement(By.xpath("./td[5]")).getText();
							mGenericWait();
							System.out.println("Registration No. is :"+RegistartionNo);
						}
						if (b == 5) 
						{
							String techPerMbNo = rows.get(a).findElement(By.xpath("./td[6]")).getText();
							mGenericWait();
							System.out.println("Mobile No. is :"+techPerMbNo);
						}
						if (b == 6) 
						{
							String techPeremailId = rows.get(a).findElement(By.xpath("./td[7]")).getText();
							mGenericWait();
							System.out.println("Email ID is :"+techPeremailId);
						}
					}

				}
			}

			System.out.println("Technical Person Name are :: "+TechPerName1);
			System.out.println("Technical Person Types are  :: "+TechPerType1);
			System.out.println("Technical Person Types are  :: "+compareList(Bpr_TechPerType,TechPerType1));
			System.out.println("Technical Person Name are :: "+compareList(Bpr_TechPerName,TechPerName1));

			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Bpr_TechPerType.get(CurrentinvoCount),TechPerType1.get(CurrentinvoCount),"Technical Person type Assert Failed");
			}
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Bpr_TechPerName.get(CurrentinvoCount),TechPerName1.get(CurrentinvoCount), "Technical Person Name Assert Failed");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in AgencyreadingtechnicalPerson  method");
		}

	}		


	//16/01/2017:Piyush For Building Permission For Citizen
	public  void  BPR_CitizenreadingtechnicalPerson(String tabelid)
	{
		try {
			WebElement table = driver.findElement(By.id(tabelid));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount = rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

			// Iterate to get the value of each cell in table along with element id
			int Rowno = 0;
			for (int a=0;a<rwcount;a++) 		
			{
				List<WebElement> cols = rows.get(a).findElements(By.xpath("td"));
				System.out.println("NUMBER OF columns IN THIS TABLE = " + cols.size());
				int Columnno = 0;
				for (int b=0;b<cols.size();b++) 		
				{
					if(a>1)
					{
						if (b == 1)
						{
							String TechPersonType = rows.get(a).findElement(By.xpath("./td[2]")).getText();
							mGenericWait();
							System.out.println("Technical Person Type is :"+TechPersonType);
							TechPerType1.add(TechPersonType);
						}	

						if (b == 2) 
						{
							String TechPersonName = rows.get(a).findElement(By.xpath("./td[3]")).getText();
							mGenericWait();
							System.out.println("Technical Person Name is :"+TechPersonName);
							TechPerName1.add(TechPersonName);
							mCustomWait(1000);

						}
						if (b == 3) 
						{
							String LicenceNo = rows.get(a).findElement(By.xpath("./td[4]")).getText();
							mGenericWait();
							System.out.println("License/Empanelment No is :"+LicenceNo);
						}
						if (b == 4) 
						{
							String RegistartionNo = rows.get(a).findElement(By.xpath("./td[5]")).getText();
							mGenericWait();
							System.out.println("Registration No. is :"+RegistartionNo);
						}
						if (b == 5) 
						{
							String techPerMbNo = rows.get(a).findElement(By.xpath("./td[6]")).getText();
							mGenericWait();
							System.out.println("Mobile No. is :"+techPerMbNo);
						}
						if (b == 6) 
						{
							String techPeremailId = rows.get(a).findElement(By.xpath("./td[7]")).getText();
							mGenericWait();
							System.out.println("Email ID is :"+techPeremailId);
						}
					}

				}
			}

			System.out.println("Technical Person Name are :: "+TechPerName1);
			System.out.println("Technical Person Types are  :: "+TechPerType1);
			System.out.println("Technical Person Types are  :: "+compareList(TechPerType,TechPerType1));
			System.out.println("Technical Person Name are :: "+compareList(TechPerName,TechPerName1));

			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Bpr_TechPerType.get(CurrentinvoCount),TechPerType1.get(CurrentinvoCount),"Technical Person type Assert Failed");
			}
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Bpr_TechPerName.get(CurrentinvoCount),TechPerName1.get(CurrentinvoCount), "Technical Person Name Assert Failed");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in AgencyreadingtechnicalPerson  method");
		}

	}		




	// Building Permission scrutiny view form assertion
	public  void bpr_ViewandAssertform()
	{
		try {


			String scrutiny_NatureofConstr=mGetText("id", mGetPropertyFromFile("tp_BprNatureOfConstructionid"),"value");
			System.out.println("Nature of Construction is in Scutiny : "+scrutiny_NatureofConstr);
			SCRBPR_NatureofConstr.add(scrutiny_NatureofConstr);

			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Nature of Construction is in Scutiny Process Arraylis is::"+SCRBPR_NatureofConstr.get(CurrentinvoCount)+" "+"Nature of Construction is at Application form  Arraylist is::"+NatureofConstr.get(CurrentinvoCount));
				mAssert(NatureofConstr.get(CurrentinvoCount), SCRBPR_NatureofConstr.get(CurrentinvoCount), "Nature of Construction does not match with the actual::"+SCRBPR_NatureofConstr.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+NatureofConstr.get(CurrentinvoCount));
			}

			String scrutiny_CaseNo=mGetText("css", mGetPropertyFromFile("tp_BprCaseNuIntextid"));
			SCRBPR_CaseNo.add(scrutiny_CaseNo);
			System.out.println(" Case Id is in Scutiny : "+scrutiny_CaseNo);	
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Case No is in Scutiny Process Arraylis is::"+SCRBPR_CaseNo.get(CurrentinvoCount)+" "+"Case No is at Application form  Arraylist is::"+Bpr_CaseNo.get(CurrentinvoCount));
				mAssert(Bpr_CaseNo.get(CurrentinvoCount), SCRBPR_CaseNo.get(CurrentinvoCount), "Case Id does not match with the actual in scrutiny View form actual::"+SCRBPR_CaseNo.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Bpr_CaseNo.get(CurrentinvoCount));
			}

			String scrutiny_PrinUsagetype=mGetText("id", mGetPropertyFromFile("tp_BprprincipalUsageTypeid"),"value");
			SCRBPR_PrinUsagetype.add(scrutiny_PrinUsagetype);
			System.out.println(" Principal Usage Type is in Scutiny : "+scrutiny_PrinUsagetype);

			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Principal Usage Type is in Scutiny Process Arraylis is::"+SCRBPR_PrinUsagetype.get(CurrentinvoCount)+" "+"Principal Usage Type is at Application form  Arraylist is::"+PrincipalUsageType.get(CurrentinvoCount));
				mAssert(PrincipalUsageType.get(CurrentinvoCount), SCRBPR_PrinUsagetype.get(CurrentinvoCount), "Principal Usage Type does not match with the actual in scrutiny View form actual::"+SCRBPR_PrinUsagetype.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+PrincipalUsageType.get(CurrentinvoCount));
			}

			String scrutiny_CategoryofBuilding=mCaptureSelectedValue("id", mGetPropertyFromFile("tp_BprBuildinCatid"));
			SCRBPR_CategoryofBuilding.add(scrutiny_CategoryofBuilding);
			System.out.println("Category of Building is in Scutiny : "+scrutiny_CategoryofBuilding);

			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Category of Building is in Scutiny Process Arraylis is::"+SCRBPR_CategoryofBuilding.get(CurrentinvoCount)+" "+"Category of Building is at Application form  Arraylist is::"+CategoryofBuilding.get(CurrentinvoCount));
				mAssert(CategoryofBuilding.get(CurrentinvoCount), SCRBPR_CategoryofBuilding.get(CurrentinvoCount), "Category of Building does not match with the actual in scrutiny View form actual::"+SCRBPR_CategoryofBuilding.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+CategoryofBuilding.get(CurrentinvoCount));
			}

			String scrutiny_Areatype=mCaptureSelectedValue("id", mGetPropertyFromFile("tp_BprAreaTypeid"));
			SCRBPR_Areatype.add(scrutiny_Areatype);
			System.out.println("Area type is in Scutiny : "+scrutiny_Areatype);

			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Area type is in Scutiny Process Arraylis is::"+SCRBPR_Areatype.get(CurrentinvoCount)+" "+"Area type is at Application form  Arraylist is::"+Area.get(CurrentinvoCount));
				mAssert(Area.get(CurrentinvoCount), SCRBPR_Areatype.get(CurrentinvoCount), "Area type does not match with the actual in scrutiny View form actual::"+SCRBPR_Areatype.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Area.get(CurrentinvoCount));
			}
			mTakeScreenShot();

			String scrutiny_PlotWidth=mGetText("id", mGetPropertyFromFile("tp_BprPlotWidthid"),"value");
			SCRBPR_PlotWidth.add(scrutiny_PlotWidth);
			System.out.println("Plot Width in is in Scutiny : "+scrutiny_PlotWidth);

			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Plot Width is in Scutiny Process Arraylis is::"+SCRBPR_PlotWidth.get(CurrentinvoCount)+" "+"Plot Width is at Application form  Arraylist is::"+Bpr_PlotWidthavg.get(CurrentinvoCount));
				mAssert(Bpr_PlotWidthavg.get(CurrentinvoCount), SCRBPR_PlotWidth.get(CurrentinvoCount), "Plot Width does not match with the actual in scrutiny View form actual::"+SCRBPR_PlotWidth.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Bpr_PlotWidthavg.get(CurrentinvoCount));
			}

			String scrutiny_PlotWidthft=mGetText("id", mGetPropertyFromFile("tp_BprPlotWidthInFtid"),"value");
			SCRBPR_PlotWidthft.add(scrutiny_PlotWidthft);
			System.out.println("Plot Width in feet in Scutiny : "+scrutiny_PlotWidthft);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Plot Width is in feet Scutiny Process Arraylis is::"+SCRBPR_PlotWidthft.get(CurrentinvoCount)+" "+"Plot Width in feet is at Application form  Arraylist is::"+PlotWidthavgft.get(CurrentinvoCount));
				mAssert(PlotWidthavgft.get(CurrentinvoCount), SCRBPR_PlotWidthft.get(CurrentinvoCount), "Plot Width in feet does not match with the actual in scrutiny View form actual::"+SCRBPR_PlotWidthft.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+PlotWidthavgft.get(CurrentinvoCount));
			}

			String scrutiny_BuildingHeight=mGetText("id", mGetPropertyFromFile("tp_BprBuildingHeightid"),"value");
			SCRBPR_BuildingHeight.add(scrutiny_BuildingHeight);
			System.out.println("Building Height is in Scutiny : "+scrutiny_BuildingHeight);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Building Height is in Scutiny Process Arraylis is::"+SCRBPR_BuildingHeight.get(CurrentinvoCount)+" "+"Building Height is at Application form  Arraylist is::"+HeightofBuilding.get(CurrentinvoCount));

				mAssert(HeightofBuilding.get(CurrentinvoCount), SCRBPR_BuildingHeight.get(CurrentinvoCount), "Building Height does not match with the actual in scrutiny View form actual::"+SCRBPR_BuildingHeight.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+HeightofBuilding.get(CurrentinvoCount));
			}

			String scrutiny_BuildingHeightft=mGetText("id", mGetPropertyFromFile("tp_BprBuildingHeightInFtid"),"value");
			SCRBPR_BuildingHeightft.add(scrutiny_BuildingHeightft);
			System.out.println("Building Height is in Scutiny : "+scrutiny_BuildingHeightft);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Building Height is in Scutiny Process Arraylis is::"+SCRBPR_BuildingHeightft.get(CurrentinvoCount)+" "+"Building Height is at Application form  Arraylist is::"+HeightofBuildingft.get(CurrentinvoCount));

				mAssert(HeightofBuildingft.get(CurrentinvoCount), SCRBPR_BuildingHeightft.get(CurrentinvoCount), "Building Height in feet does not match with the actual in scrutiny View form actual::"+SCRBPR_BuildingHeightft.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+HeightofBuildingft.get(CurrentinvoCount));
			}

			String scrutiny_PlotLength=mGetText("id", mGetPropertyFromFile("tp_BprPlotLengthid"),"value");
			SCRBPR_PlotLength.add(scrutiny_PlotLength);
			System.out.println("Plot Length is in Scutiny : "+scrutiny_PlotLength);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Plot Length(Meter) is in Scutiny Process Arraylis is::"+SCRBPR_PlotLength.get(CurrentinvoCount)+" "+"Plot Length(Meter) is at Application form  Arraylist is::"+PlotLengthAvg.get(CurrentinvoCount));

				mAssert(PlotLengthAvg.get(CurrentinvoCount), SCRBPR_PlotLength.get(CurrentinvoCount), "Plot Length(Meter) does not match with the actual in scrutiny View form actual::"+SCRBPR_PlotLength.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+PlotLengthAvg.get(CurrentinvoCount));
			}

			String scrutiny_PlotLengthInFt=mGetText("id", mGetPropertyFromFile("tp_BprPlotLengthInFtid"),"value");
			SCRBPR_PlotLengthInFt.add(scrutiny_PlotLengthInFt);
			System.out.println("Plot Length in Feet is in Scutiny : "+scrutiny_PlotLengthInFt);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Plot Length(in Feet) is in Scutiny Process Arraylis is::"+SCRBPR_PlotLengthInFt.get(CurrentinvoCount)+" "+"Plot Length(Meter) is at Application form  Arraylist is::"+PlotLengthAvgft.get(CurrentinvoCount));

				mAssert(PlotLengthAvgft.get(CurrentinvoCount), SCRBPR_PlotLengthInFt.get(CurrentinvoCount), "Plot Length in Feet does not match with the actual in scrutiny View form actual::"+SCRBPR_PlotLengthInFt.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+PlotLengthAvgft.get(CurrentinvoCount));
			}

			String scrutiny_PlotArea=mGetText("id", mGetPropertyFromFile("tp_BprPlotAreaid"),"value");
			SCRBPR_PlotArea.add(scrutiny_PlotArea);
			System.out.println("Plot Area in Meter is in Scutiny : "+scrutiny_PlotArea);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Plot Area(in Meter) is in Scutiny Process Arraylis is::"+SCRBPR_PlotArea.get(CurrentinvoCount)+" "+"Plot Area Meter is at Application form  Arraylist is::"+PlotArea.get(CurrentinvoCount));

				mAssert(PlotArea.get(CurrentinvoCount), SCRBPR_PlotArea.get(CurrentinvoCount), "Plot Area does not match with the actual in scrutiny View form actual::"+SCRBPR_PlotArea.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+PlotArea.get(CurrentinvoCount));
			}

			String scrutiny_PlotAreaft=mGetText("id", mGetPropertyFromFile("tp_BprPlotAreaInFtid"),"value");
			SCRBPR_PlotAreaft.add(scrutiny_PlotAreaft);
			System.out.println("Plot Area in Feet is in Scutiny : "+scrutiny_PlotAreaft);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Plot Area(Meter) is in Scutiny Process Arraylis is::"+SCRBPR_PlotAreaft.get(CurrentinvoCount)+" "+"Plot Area feet is at Application form  Arraylist is::"+bprPlotAreaFt.get(CurrentinvoCount));

				mAssert(bprPlotAreaFt.get(CurrentinvoCount), SCRBPR_PlotAreaft.get(CurrentinvoCount), "Plot Area in feet does not match with the actual in scrutiny View form actual::"+SCRBPR_PlotAreaft.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+bprPlotAreaFt.get(CurrentinvoCount));
			}
			mTakeScreenShot();
			mscroll(0, 300);
			String scrutiny_HoldingRights=mCaptureSelectedValue("id", mGetPropertyFromFile("tp_BprHoldingRightsid"));
			SCRBPR_HoldingRights.add(scrutiny_HoldingRights);
			System.out.println("Holding Rights is in Scutiny : "+scrutiny_HoldingRights);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Holding RightPlot Area(Meter) is in Scutiny Process Arraylis is::"+SCRBPR_HoldingRights.get(CurrentinvoCount)+" "+"Holding Right is at Application form  Arraylist is::"+HoldingRights.get(CurrentinvoCount));

				mAssert(HoldingRights.get(CurrentinvoCount), SCRBPR_HoldingRights.get(CurrentinvoCount), "Holding Rights does not match with the actual in scrutiny View form actual::"+SCRBPR_HoldingRights.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+HoldingRights.get(CurrentinvoCount));
			}


			String scrutiny_ConstructionCost=mGetText("id", mGetPropertyFromFile("tp_BprConstructionCostid"),"value");
			SCRBPR_ConstructionCost.add(scrutiny_ConstructionCost);
			System.out.println("Construction Cost is in Scutiny : "+scrutiny_ConstructionCost);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Construction Cost is in Scutiny Process Arraylis is::"+Bpr_ConstructionCost.get(CurrentinvoCount)+" "+"Construction Cost is at Application form  Arraylist is::"+Bpr_ConstructionCost.get(CurrentinvoCount));
				mAssert(Bpr_ConstructionCost.get(CurrentinvoCount), SCRBPR_ConstructionCost.get(CurrentinvoCount), "Construction Cost does not match with the actual in scrutiny View form actual::"+SCRBPR_ConstructionCost.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Bpr_ConstructionCost.get(CurrentinvoCount));
			}

			String scrutiny_GISRoad=mGetText("id", mGetPropertyFromFile("tp_BprGISRoadid"),"value");
			SCRBPR_GISRoad.add(scrutiny_GISRoad);
			System.out.println("GIS Road is in Scutiny : "+scrutiny_GISRoad);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("GIS Road is in Scutiny Process Arraylis is::"+SCRBPR_GISRoad.get(CurrentinvoCount)+" "+"GIS Road is at Application form  Arraylist is::"+GISRoadId.get(CurrentinvoCount));
				mAssert(GISRoadId.get(CurrentinvoCount), SCRBPR_GISRoad.get(CurrentinvoCount), "GIS Road does not match with the actual in scrutiny View form actual::"+SCRBPR_GISRoad.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+GISRoadId.get(CurrentinvoCount));
			}

			String scrutiny_ExtRoadWidth=mGetText("id", mGetPropertyFromFile("tp_BprExtRoadWidthAsserid"),"value");
			SCRBPR_ExtRoadWidth.add(scrutiny_ExtRoadWidth);
			System.out.println("Existing Road Width is in Scutiny : "+scrutiny_ExtRoadWidth);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Existing Road Width is in Scutiny Process Arraylis is::"+SCRBPR_ExtRoadWidth.get(CurrentinvoCount)+" "+"Existing Road Width is at Application form  Arraylist is::"+ExistingRoadWidth.get(CurrentinvoCount));

				mAssert(ExistingRoadWidth.get(CurrentinvoCount), SCRBPR_ExtRoadWidth.get(CurrentinvoCount), "Existing Road Width does not match with the actual in scrutiny View form actual::"+SCRBPR_ExtRoadWidth.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+ExistingRoadWidth.get(CurrentinvoCount) );
			}

			String scrutiny_ExtRoadWidthft=mGetText("id", mGetPropertyFromFile("tp_BprExtRoadWidthInFtid"),"value");
			SCRBPR_ExtRoadWidthft.add(scrutiny_ExtRoadWidthft);
			System.out.println("Existing Road Width in Feet is in Scutiny : "+scrutiny_ExtRoadWidthft);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Existing Road Width in feet is in Scutiny Process Arraylis is::"+SCRBPR_ExtRoadWidthft.get(CurrentinvoCount)+" "+"Existing Road Width in feet is at Application form  Arraylist is::"+ExistingRoadWidthft.get(CurrentinvoCount));

				mAssert(ExistingRoadWidthft.get(CurrentinvoCount), SCRBPR_ExtRoadWidthft.get(CurrentinvoCount), "Existing Road in feet  does not match with the actual in scrutiny View form actual::"+SCRBPR_ExtRoadWidthft.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+ExistingRoadWidthft.get(CurrentinvoCount));
			}

			String scrutiny_WidthAsprMastrPln=mGetText("id", mGetPropertyFromFile("tp_BprRoadWidthAsprMastrPlnid"),"value");
			SCRBPR_WidthAsprMastrPln.add(scrutiny_WidthAsprMastrPln);
			System.out.println("Road Width as per Master Plan  is in Scutiny : "+scrutiny_WidthAsprMastrPln);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Road Width as per Master Plan  is in Scutiny Process Arraylis is::"+SCRBPR_WidthAsprMastrPln.get(CurrentinvoCount)+" "+"Road Width as per Master Plan is at Application form  Arraylist is::"+RWidthasperMasterPlan.get(CurrentinvoCount));

				mAssert(RWidthasperMasterPlan.get(CurrentinvoCount), SCRBPR_WidthAsprMastrPln.get(CurrentinvoCount), "Road Width as per Master Plan does not match with the actual in scrutiny View form actual::"+SCRBPR_WidthAsprMastrPln.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+RWidthasperMasterPlan.get(CurrentinvoCount));
			}

			String scrutiny_WidthAsprMastrPlnft=mGetText("id", mGetPropertyFromFile("tp_BprRoadWidthAsprMastrPlnInFtid"),"value");
			SCRBPR_WidthAsprMastrPlnft.add(scrutiny_WidthAsprMastrPlnft);
			System.out.println("Road Width as per Master Plan in feet is in Scutiny : "+scrutiny_WidthAsprMastrPlnft);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Road Width as per Master Plan in feet is in Scutiny Process Arraylis is::"+SCRBPR_WidthAsprMastrPlnft.get(CurrentinvoCount)+" "+"Road Width as per Master Plan in feet is at Application form  Arraylist is::"+RWidthasperMasterPlanft.get(CurrentinvoCount));

				mAssert(RWidthasperMasterPlanft.get(CurrentinvoCount), SCRBPR_WidthAsprMastrPlnft.get(CurrentinvoCount), "Road Width as per Master Plan in feet does not match with the actual in scrutiny View form actual::"+SCRBPR_WidthAsprMastrPlnft.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+RWidthasperMasterPlanft.get(CurrentinvoCount));
			}

			//Area left for Road Widening,Parking Area,Whether falls in airport zone ? chk box and other assertion to be write
			String scrutiny_NoOfFloor=mGetText("id", mGetPropertyFromFile("tp_BprNoOfFloorid"),"value");
			SCRBPR_NoOfFloor.add(scrutiny_NoOfFloor);
			System.out.println("No of floor is in Scutiny : "+scrutiny_NoOfFloor);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("No of floor is in Scutiny Process Arraylis is::"+SCRBPR_NoOfFloor.get(CurrentinvoCount)+" "+"No of floor is at Application form  Arraylist is::"+BPR_NoofFloors.get(CurrentinvoCount));

				mAssert(BPR_NoofFloors.get(CurrentinvoCount), SCRBPR_NoOfFloor.get(CurrentinvoCount), "No of floor does not match with the actual in scrutiny View form actual::"+SCRBPR_NoOfFloor.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+BPR_NoofFloors.get(CurrentinvoCount));
			}

			String scrutiny_BuiltupareaSqMt2=mGetText("id", mGetPropertyFromFile("tp_BprBuiltUpAreaInMtid"),"value");
			SCRBPR_BuiltupareaSqMt2.add(scrutiny_BuiltupareaSqMt2);
			System.out.println("Built up area(Square Meter) is in Scutiny : "+scrutiny_BuiltupareaSqMt2);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Built up area(Square Meter) is in Scutiny Process Arraylis is::"+SCRBPR_BuiltupareaSqMt2.get(CurrentinvoCount)+" "+"Built up area(Square Meter) is at Application form  Arraylist is::"+BPR_BuiltupareaSqMt2.get(CurrentinvoCount));

				mAssert(BPR_BuiltupareaSqMt2.get(CurrentinvoCount), SCRBPR_BuiltupareaSqMt2.get(CurrentinvoCount), "Built up area(Square Meter) does not match with the actual in scrutiny View form actual::"+SCRBPR_BuiltupareaSqMt2.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+BPR_BuiltupareaSqMt2.get(CurrentinvoCount));
			}



			String scrutiny_BuiltupareaSqft2=mGetText("id", mGetPropertyFromFile("tp_BprBuiltUpAreaInFtid"),"value");
			SCRBPR_BuiltupareaSqft2.add(scrutiny_BuiltupareaSqft2);
			System.out.println("Built up area(Square Feet) is in Scutiny : "+scrutiny_BuiltupareaSqft2);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Built up area(Square Feet) is in Scutiny Process Arraylis is::"+SCRBPR_BuiltupareaSqft2.get(CurrentinvoCount)+" "+"Built up area(Square Feet) is at Application form  Arraylist is::"+BPR_BuiltupareaSqft2.get(CurrentinvoCount));

				mAssert(BPR_BuiltupareaSqft2.get(CurrentinvoCount), SCRBPR_BuiltupareaSqft2.get(CurrentinvoCount), "Built up area(Square Feet) does not match with the actual in scrutiny View form actual::"+SCRBPR_BuiltupareaSqft2.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+BPR_BuiltupareaSqft2.get(CurrentinvoCount));
			}

			mTakeScreenShot();

			String scrutiny_FARAppliedfor=mGetText("id", mGetPropertyFromFile("tp_BprFarAppliedAsserid"),"value");
			SCRBPR_FARAppliedfor.add(scrutiny_FARAppliedfor);
			System.out.println("FAR Applied is in Scutiny : "+scrutiny_FARAppliedfor);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("FAR Applied is in Scutiny Process Arraylis is::"+SCRBPR_FARAppliedfor.get(CurrentinvoCount)+" "+"FAR Applied is at Application form  Arraylist is::"+FARAppliedfor.get(CurrentinvoCount));

				mAssert(FARAppliedfor.get(CurrentinvoCount), SCRBPR_FARAppliedfor.get(CurrentinvoCount), "FAR Applied for does not match with the actual in scrutiny View form actual::"+SCRBPR_FARAppliedfor.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+FARAppliedfor.get(CurrentinvoCount));
			}

			String scrutiny_PermissibleFAR=mGetText("id", mGetPropertyFromFile("tp_BprFarPermissibleid"),"value");
			SCRBPR_PermissibleFAR.add(scrutiny_PermissibleFAR);
			System.out.println("Permissible FAR is in Scutiny : "+scrutiny_PermissibleFAR);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Permissible FAR is in Scutiny Process Arraylis is::"+SCRBPR_PermissibleFAR.get(CurrentinvoCount)+" "+"Permissible FAR is at Application form  Arraylist is::"+PermissibleFAR.get(CurrentinvoCount));

				mAssert(PermissibleFAR.get(CurrentinvoCount), SCRBPR_PermissibleFAR.get(CurrentinvoCount), "Permissible  FAR for does not match with the actual in scrutiny View form actual::"+SCRBPR_PermissibleFAR.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+PermissibleFAR.get(CurrentinvoCount));
			}

			String scrutiny_LocPlotNorth=mGetText("id", mGetPropertyFromFile("tp_BprNorthPlotid"),"value");
			SCRBPR_LocPlotNorth.add(scrutiny_LocPlotNorth);
			System.out.println("Location of Plot North is in Scutiny : "+scrutiny_LocPlotNorth);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Location of Plot North is in Scutiny Process Arraylis is::"+SCRBPR_LocPlotNorth.get(CurrentinvoCount)+" "+"Location of Plot North is at Application form  Arraylist is::"+LocPlotNorth.get(CurrentinvoCount));

				mAssert(LocPlotNorth.get(CurrentinvoCount), SCRBPR_LocPlotNorth.get(CurrentinvoCount), "Location of Plot North for does not match with the actual in scrutiny View form actual::"+SCRBPR_LocPlotNorth.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+LocPlotNorth.get(CurrentinvoCount));
			}

			String scrutiny_LocPlotEast=mGetText("id", mGetPropertyFromFile("tp_BprEastPlotid"),"value");
			SCRBPR_LocPlotEast.add(scrutiny_LocPlotEast);
			System.out.println("Location of Plot East is in Scutiny : "+scrutiny_LocPlotEast);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Location of Plot East is in Scutiny Process Arraylis is::"+SCRBPR_LocPlotEast.get(CurrentinvoCount)+" "+"Location of Plot East is at Application form  Arraylist is::"+LocPlotEast.get(CurrentinvoCount));

				mAssert(LocPlotEast.get(CurrentinvoCount), SCRBPR_LocPlotEast.get(CurrentinvoCount), "Location of Plot East for does not match with the actual in scrutiny View form actual::"+SCRBPR_LocPlotEast.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+LocPlotEast.get(CurrentinvoCount));
			}

			String scrutiny_LocPlotWest=mGetText("id", mGetPropertyFromFile("tp_BprWestPlotid"),"value");
			SCRBPR_LocPlotWest.add(scrutiny_LocPlotWest);
			System.out.println("Location of Plot West is in Scutiny : "+scrutiny_LocPlotWest);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Location of Plot West is in Scutiny Process Arraylis is::"+SCRBPR_LocPlotWest.get(CurrentinvoCount)+" "+"Location of Plot West is at Application form  Arraylist is::"+LocPlotWest.get(CurrentinvoCount));

				mAssert(LocPlotWest.get(CurrentinvoCount), SCRBPR_LocPlotWest.get(CurrentinvoCount), "Location of Plot West for does not match with the actual in scrutiny View form actual::"+SCRBPR_LocPlotWest.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+LocPlotWest.get(CurrentinvoCount));
			}

			String scrutiny_LocPlotSouth=mGetText("id", mGetPropertyFromFile("tp_BprSouthPlotid"),"value");
			SCRBPR_LocPlotSouth.add(scrutiny_LocPlotSouth);
			System.out.println("Location of Plot Sonth is in Scutiny : "+scrutiny_LocPlotSouth);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Location of Plot Sonth is in Scutiny Process Arraylis is::"+SCRBPR_LocPlotSouth.get(CurrentinvoCount)+" "+"Location of Plot Sonth is at Application form  Arraylist is::"+LocPlotSouth.get(CurrentinvoCount));

				mAssert(LocPlotSouth.get(CurrentinvoCount), SCRBPR_LocPlotSouth.get(CurrentinvoCount), "Location of Plot South for does not match with the actual in scrutiny View form actual::"+SCRBPR_LocPlotSouth.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+LocPlotSouth.get(CurrentinvoCount));
			}

			String scrutiny_AvgDepthofPlot=mGetText("id", mGetPropertyFromFile("tp_BprAvgDepAsserthid"),"value");
			SCRBPR_AvgDepthofPlot.add(scrutiny_AvgDepthofPlot);
			System.out.println("Avg. Depth of Plot is in Scutiny : "+scrutiny_AvgDepthofPlot);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Avg. Depth of Plot is in Scutiny Process Arraylis is::"+SCRBPR_AvgDepthofPlot.get(CurrentinvoCount)+" "+"Avg. Depth of Plot is at Application form  Arraylist is::"+AvgDepthofPlot.get(CurrentinvoCount));

				mAssert(AvgDepthofPlot.get(CurrentinvoCount), SCRBPR_AvgDepthofPlot.get(CurrentinvoCount), "Avg. Depth of Plot  does not match with the actual in scrutiny View form actual::"+SCRBPR_AvgDepthofPlot.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+AvgDepthofPlot.get(CurrentinvoCount));
			}

			String scrutiny_AvgDepthofPlotft=mGetText("id", mGetPropertyFromFile("tp_BprAvgDepthInFtid"),"value");
			SCRBPR_AvgDepthofPlotft.add(scrutiny_AvgDepthofPlotft);
			System.out.println("Avg. Depth of Plot in feet is in Scutiny : "+scrutiny_AvgDepthofPlotft);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Avg. Depth of Plot in feet is in Scutiny Process Arraylis is::"+SCRBPR_AvgDepthofPlotft.get(CurrentinvoCount)+" "+"Avg. Depth of Plot in feet is at Application form  Arraylist is::"+AvgDepthofPlotft.get(CurrentinvoCount));

				mAssert(AvgDepthofPlotft.get(CurrentinvoCount), SCRBPR_AvgDepthofPlotft.get(CurrentinvoCount), "Avg. Depth of Plot in feet  does not match with the actual in scrutiny View form actual::"+SCRBPR_AvgDepthofPlotft.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+AvgDepthofPlotft.get(CurrentinvoCount));
			}

			String scrutiny_AvgWidthofPlot=mGetText("id", mGetPropertyFromFile("tp_BprAvgWidthAsserid"),"value");
			SCRBPR_AvgWidthofPlot.add(scrutiny_AvgWidthofPlot);
			System.out.println("Avg. Width of Plot is in Scutiny : "+scrutiny_AvgWidthofPlot);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Avg. Width of Plot in feet is in Scutiny Process Arraylis is::"+SCRBPR_AvgWidthofPlot.get(CurrentinvoCount)+" "+"Avg. Width of Plot in feet is at Application form  Arraylist is::"+AvgWidthofPlot.get(CurrentinvoCount));

				mAssert(AvgWidthofPlot.get(CurrentinvoCount), SCRBPR_AvgWidthofPlot.get(CurrentinvoCount), "Avg. Width of Plot does not match with the actual in scrutiny View form actual::"+SCRBPR_AvgWidthofPlot.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+AvgWidthofPlot.get(CurrentinvoCount));
			}

			mTakeScreenShot();
			mscroll(0, 300);

			String scrutiny_AvgWidthofPlotft=mGetText("id", mGetPropertyFromFile("tp_BprAvgWidthInFtid"),"value");
			SCRBPR_AvgWidthofPlotft.add(scrutiny_AvgWidthofPlotft);
			System.out.println("Avg. Width of Plot in feet is in Scutiny : "+scrutiny_AvgWidthofPlotft);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Avg. Width of Plot in feet is in Scutiny Process Arraylis is::"+SCRBPR_AvgWidthofPlotft.get(CurrentinvoCount)+" "+"Avg. Width of Plot in feet is at Application form  Arraylist is::"+AvgWidthofPlotft.get(CurrentinvoCount));

				mAssert(AvgWidthofPlotft.get(CurrentinvoCount), SCRBPR_AvgWidthofPlotft.get(CurrentinvoCount), "Avg. Width of Plot in feet does not match with the actual in scrutiny View form actual::"+SCRBPR_AvgWidthofPlotft.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+AvgWidthofPlotft.get(CurrentinvoCount));
			}

			//Front Set Back Applied
			String scrutiny_FrontSetBackApplied=mGetText("id", mGetPropertyFromFile("tp_BprFrontSetBackAsserid"),"value");
			SCRBPR_FrontSetBackApplied.add(scrutiny_FrontSetBackApplied);
			System.out.println("Front Set Back applied for is in Scutiny : "+scrutiny_FrontSetBackApplied);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Front Set Back applied for is in Scutiny Process Arraylis is::"+SCRBPR_FrontSetBackApplied.get(CurrentinvoCount)+" "+"Front Set Back applied for is at Application form  Arraylist is::"+FrontSetBackApplied.get(CurrentinvoCount));

				mAssert(FrontSetBackApplied.get(CurrentinvoCount), SCRBPR_FrontSetBackApplied.get(CurrentinvoCount), "Front Set Back applied for does not match with the actual in scrutiny View form actual::"+SCRBPR_FrontSetBackApplied.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+FrontSetBackApplied.get(CurrentinvoCount));
			}

			String scrutiny_FrontSetBackAppliedft=mGetText("id", mGetPropertyFromFile("tp_BprFrontSetBackInFtid"),"value");
			SCRBPR_FrontSetBackAppliedft.add(scrutiny_FrontSetBackAppliedft);
			System.out.println("Front Set Back applied for in feet is in Scutiny : "+scrutiny_FrontSetBackAppliedft);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Front Set Back applied for in feet is in Scutiny Process Arraylis is::"+SCRBPR_FrontSetBackAppliedft.get(CurrentinvoCount)+" "+"Front Set Back applied for in feet is at Application form  Arraylist is::"+FrontSetBackAppliedft.get(CurrentinvoCount));

				mAssert(FrontSetBackAppliedft.get(CurrentinvoCount), SCRBPR_FrontSetBackAppliedft.get(CurrentinvoCount), "Front Set Back applied for in feet does not match with the actual in scrutiny View form actual::"+SCRBPR_FrontSetBackAppliedft.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+FrontSetBackAppliedft.get(CurrentinvoCount));
			}

			//Left Side Set Back Applied 
			String scrutiny_LeftSetBackApplied=mGetText("id", mGetPropertyFromFile("tp_BprLeftSideSetBackAsserid"),"value");
			SCRBPR_LeftSetBackApplied.add(scrutiny_LeftSetBackApplied);
			System.out.println("Left Side Set Back Applied for is in Scutiny : "+scrutiny_LeftSetBackApplied);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Left Side Set Back Applied is in Scutiny Process Arraylis is::"+SCRBPR_LeftSetBackApplied.get(CurrentinvoCount)+" "+"Left Side Set Back Applied is at Application form  Arraylist is::"+LeftSetBackApplied.get(CurrentinvoCount));

				mAssert(LeftSetBackApplied.get(CurrentinvoCount), SCRBPR_LeftSetBackApplied.get(CurrentinvoCount), "Left Side Set Back applied for does not match with the actual in scrutiny View form actual::"+SCRBPR_LeftSetBackApplied.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+LeftSetBackApplied.get(CurrentinvoCount));
			}

			String scrutiny_LeftSetBackAppliedft=mGetText("id", mGetPropertyFromFile("tp_BprLeftSideSetBackInFtid"),"value");
			SCRBPR_LeftSetBackAppliedft.add(scrutiny_LeftSetBackAppliedft);
			System.out.println("Left Side Set Back Applied in feet is in Scutiny : "+scrutiny_LeftSetBackAppliedft);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Left Side Set Back Applied feet is in Scutiny Process Arraylis is::"+SCRBPR_LeftSetBackAppliedft.get(CurrentinvoCount)+" "+"Left Side Set Back Applied feet is at Application form  Arraylist is::"+LeftSetBackAppliedft.get(CurrentinvoCount));

				mAssert(LeftSetBackAppliedft.get(CurrentinvoCount), SCRBPR_LeftSetBackAppliedft.get(CurrentinvoCount), "Left Side Set Back applied for in feet does not match with the actual in scrutiny View form actual::"+SCRBPR_LeftSetBackAppliedft.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+LeftSetBackAppliedft.get(CurrentinvoCount));
			}


			mTakeScreenShot();
			mscroll(0, 300);

			//Rear Set Back Applied
			String scrutiny_RearSetBackApplied=mGetText("id", mGetPropertyFromFile("tp_BprRearSetBackAsserid"),"value");
			SCRBPR_RearSetBackApplied.add(scrutiny_RearSetBackApplied);
			System.out.println("Rear Set Back applied for is in Scutiny : "+scrutiny_RearSetBackApplied);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Rear Set Back applied is in Scutiny Process Arraylis is::"+SCRBPR_RearSetBackApplied.get(CurrentinvoCount)+" "+"Rear Set Back applied is at Application form  Arraylist is::"+RearSetBackApplied.get(CurrentinvoCount));

				mAssert(RearSetBackApplied.get(CurrentinvoCount), SCRBPR_RearSetBackApplied.get(CurrentinvoCount), "Rear Set Back applied for does not match with the actual in scrutiny View form actual::"+SCRBPR_RearSetBackApplied.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+RearSetBackApplied.get(CurrentinvoCount));
			}

			String scrutiny_RearSetBackAppliedft=mGetText("id", mGetPropertyFromFile("tp_BprRearSetBackInFtid"),"value");
			SCRBPR_RearSetBackAppliedft.add(scrutiny_RearSetBackAppliedft);
			System.out.println("Rear Set Back applied for in feet is in Scutiny : "+scrutiny_RearSetBackAppliedft);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Rear Set Back applied for in feet is in Scutiny Process Arraylis is::"+SCRBPR_RearSetBackAppliedft.get(CurrentinvoCount)+" "+"Rear Set Back applied for in feet is at Application form  Arraylist is::"+RearSetBackAppliedft.get(CurrentinvoCount));

				mAssert(RearSetBackAppliedft.get(CurrentinvoCount), SCRBPR_RearSetBackAppliedft.get(CurrentinvoCount), "Rear Set Back applied for in feet does not match with the actual in scrutiny View form actual::"+SCRBPR_RearSetBackAppliedft.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+RearSetBackAppliedft.get(CurrentinvoCount));
			}



			//Right Side Set Back Applied and permissible
			String scrutiny_RightSetBackApplied=mGetText("id", mGetPropertyFromFile("tp_BprRightSideSetBackAsserid"),"value");
			SCRBPR_RightSetBackApplied.add(scrutiny_RightSetBackApplied);
			System.out.println("Right Side Set Back applied for is in Scutiny : "+scrutiny_RightSetBackApplied);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Right Side Set Back applied is in Scutiny Process Arraylis is::"+SCRBPR_RightSetBackApplied.get(CurrentinvoCount)+" "+"Right Side Set Back applied is at Application form  Arraylist is::"+RightSetBackApplied.get(CurrentinvoCount));

				mAssert(RightSetBackApplied.get(CurrentinvoCount), SCRBPR_RightSetBackApplied.get(CurrentinvoCount), "Right Side Set Back applied for does not match with the actual in scrutiny View form actual::"+SCRBPR_RightSetBackApplied.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+RightSetBackApplied.get(CurrentinvoCount));
			}

			String scrutiny_RightSetBackAppliedft=mGetText("id", mGetPropertyFromFile("tp_BprRightSideSetBackInFtid"),"value");
			SCRBPR_RightSetBackAppliedft.add(scrutiny_RightSetBackAppliedft);
			System.out.println("Right Side Set Back applied for in feet is in Scutiny : "+scrutiny_RightSetBackAppliedft);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Right Side Set Back applied for in feet is in Scutiny Process Arraylis is::"+SCRBPR_RightSetBackAppliedft.get(CurrentinvoCount)+" "+"Right Side Set Back applied for in feet is at Application form  Arraylist is::"+RightSetBackAppliedft.get(CurrentinvoCount));

				mAssert(RightSetBackAppliedft.get(CurrentinvoCount), SCRBPR_RightSetBackAppliedft.get(CurrentinvoCount), "Right Side Set Back applied for in feet does not match with the actual in scrutiny View form actual::"+SCRBPR_RightSetBackAppliedft.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+RightSetBackAppliedft.get(CurrentinvoCount));
			}



			//Application Details
			String scrutiny_appmbno=mGetText("id", mGetPropertyFromFile("tp_BprApplicantMobileNoid"),"value");
			System.out.println("Applicant Mobile No is in Scutiny : "+scrutiny_appmbno);
			SCRBPR_appmbno.add(scrutiny_appmbno);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Applicant Mobile No is in Scutiny Process Arraylis is::"+SCRBPR_appmbno.get(CurrentinvoCount)+" "+"Applicant Mobile No is at Application form  Arraylist is::"+Bpr_ApplicantMobileNo.get(CurrentinvoCount));

				mAssert(Bpr_ApplicantMobileNo.get(CurrentinvoCount), SCRBPR_appmbno.get(CurrentinvoCount), " Applicant Mobile No Does not match with the actual in scrutiny View form actual::"+SCRBPR_appmbno.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Bpr_ApplicantMobileNo.get(CurrentinvoCount));
			}

			String scrutiny_appEmailId=mGetText("id", mGetPropertyFromFile("tp_BprApplicantEmailid"),"value");
			System.out.println("Applicant Email Id is in Scutiny : "+scrutiny_appEmailId);
			SCRBPR_appEmailId.add(scrutiny_appEmailId);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Applicant Email Id is in Scutiny Process Arraylis is::"+SCRBPR_appEmailId.get(CurrentinvoCount)+" "+"Applicant Email Id is at Application form  Arraylist is::"+Bpr_ApplicantEmailId.get(CurrentinvoCount));

				mAssert(Bpr_ApplicantEmailId.get(CurrentinvoCount), SCRBPR_appEmailId.get(CurrentinvoCount), "Applicant Email ID Does not match with the actual in scrutiny View form actual::"+SCRBPR_appEmailId.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Bpr_ApplicantEmailId.get(CurrentinvoCount));
			}

			mTakeScreenShot();
			mscroll(0, 300);


			String scrutiny_OwnMbNo=mGetText("id", mGetPropertyFromFile("tp_BprOwnerMobileNoid"),"value");
			System.out.println("Owner Mobile No is in Scutiny : "+scrutiny_OwnMbNo);
			SCRBPR_OwnMbNo.add(scrutiny_OwnMbNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Owner Mobile No is in Scutiny Process Arraylis is::"+SCRBPR_OwnMbNo.get(CurrentinvoCount)+" "+"Owner Mobile No is at Application form  Arraylist is::"+Bpr_OwnerMobileNo.get(CurrentinvoCount));

				mAssert(Bpr_OwnerMobileNo.get(CurrentinvoCount), SCRBPR_OwnMbNo.get(CurrentinvoCount), "Owner Mobile No Does not match with the actual in scrutiny View form actual::"+SCRBPR_OwnMbNo.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Bpr_OwnerMobileNo.get(CurrentinvoCount));
			}

			String scrutiny_OwnEmailId=mGetText("id", mGetPropertyFromFile("tp_BprOwnerEmailid"),"value");
			System.out.println("Owner Email ID is in Scutiny : "+scrutiny_OwnEmailId);
			SCRBPR_OwnEmailId.add(scrutiny_OwnEmailId);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Owner Email ID is in Scutiny Process Arraylis is::"+SCRBPR_OwnEmailId.get(CurrentinvoCount)+" "+"Owner Email ID is at Application form  Arraylist is::"+Bpr_OwnerEmailId.get(CurrentinvoCount));

				mAssert(Bpr_OwnerEmailId.get(CurrentinvoCount), SCRBPR_OwnEmailId.get(CurrentinvoCount), "Owner Email ID Does not match with the actual in scrutiny View form actual::"+SCRBPR_OwnEmailId.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Bpr_OwnerEmailId.get(CurrentinvoCount));
			}

			String scrutiny_AddLine1=mGetText("id", mGetPropertyFromFile("tp_BprOwnerCAddLine1id"),"value");
			System.out.println("Address Line 1 is in Scutiny : "+scrutiny_AddLine1);
			SCRBPR_AddLine1.add(scrutiny_AddLine1);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Address Line 1 is in Scutiny Process Arraylis is::"+SCRBPR_AddLine1.get(CurrentinvoCount)+" "+"Address Line 1 is at Application form  Arraylist is::"+Bpr_Addline1.get(CurrentinvoCount));

				mAssert(Bpr_Addline1.get(CurrentinvoCount), SCRBPR_AddLine1.get(CurrentinvoCount), "Address Line 1 Does not match with the actual in scrutiny View form actual::"+SCRBPR_AddLine1.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Bpr_Addline1.get(CurrentinvoCount));
			}

			String scrutiny_AddLine2=mGetText("id", mGetPropertyFromFile("tp_BprOwnerCAddLine2id"),"value");
			System.out.println("Address Line 2 is in Scutiny : "+scrutiny_AddLine2);
			SCRBPR_AddLine2.add(scrutiny_AddLine2);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Address Line 2  is in Scutiny Process Arraylis is::"+SCRBPR_AddLine2.get(CurrentinvoCount)+" "+"Address Line 2  is at Application form  Arraylist is::"+Bpr_Addline2.get(CurrentinvoCount));

				mAssert(Bpr_Addline2.get(CurrentinvoCount), SCRBPR_AddLine2.get(CurrentinvoCount), "Address Line 2 Does not match with the actual in scrutiny View form actual::"+SCRBPR_AddLine2.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Bpr_Addline2.get(CurrentinvoCount));
			}

			mTakeScreenShot();
			mscroll(0, 350);

			String scrutiny_Pincode=mGetText("id", mGetPropertyFromFile("tp_BprOwnerPinCodeid"),"value");
			System.out.println("Pincode is in Scutiny : "+scrutiny_Pincode);
			SCRBPR_Pincode.add(scrutiny_Pincode);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Pincode is in Scutiny Process Arraylis is::"+SCRBPR_Pincode.get(CurrentinvoCount)+" "+"Pincode is at Application form  Arraylist is::"+Bpr_Pincode.get(CurrentinvoCount));

				mAssert(Bpr_Pincode.get(CurrentinvoCount), SCRBPR_Pincode.get(CurrentinvoCount), "Pincode Does not match with the actual in scrutiny View form actual::"+SCRBPR_Pincode.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Bpr_Pincode.get(CurrentinvoCount));
			}

			TownPlanningServices tp1=new TownPlanningServices();
			tp1.BuildingPermissionreadingownerdetails(mGetPropertyFromFile("tp_BprAdditionalOwnerdetailsTabelAsserid"));

			String scrutiny_Ward=mGetText("id", mGetPropertyFromFile("tp_BprWardid"),"value");
			System.out.println("Ward is in Scutiny : "+scrutiny_Ward);
			SCRBPR_Ward.add(scrutiny_Ward);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Ward is in Scutiny Process Arraylis is::"+SCRBPR_Ward.get(CurrentinvoCount)+" "+"Ward is at Application form  Arraylist is::"+SCRBPR_Ward.get(CurrentinvoCount));

				mAssert(Bpr_ward.get(CurrentinvoCount), SCRBPR_Ward.get(CurrentinvoCount), "Ward Does not match with the actual in scrutiny View form actual::"+SCRBPR_Ward.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+SCRBPR_Ward.get(CurrentinvoCount));
			}


			String scrutiny_PlotKhasaraNo=mGetText("id", mGetPropertyFromFile("tp_BprPlotNoid"),"value");
			System.out.println("Plot (CS)/ Khasra No  is in Scutiny : "+scrutiny_PlotKhasaraNo);
			SCRBPR_PlotKhasaraNo.add(scrutiny_PlotKhasaraNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Plot (CS)/ Khasra No is in Scutiny Process Arraylis is::"+SCRBPR_PlotKhasaraNo.get(CurrentinvoCount)+" "+"Plot (CS)/ Khasra No is at Application form  Arraylist is::"+Bpr_PlotKhasraNo.get(CurrentinvoCount));

				mAssert(Bpr_PlotKhasraNo.get(CurrentinvoCount), SCRBPR_PlotKhasaraNo.get(CurrentinvoCount), "Plot (CS)/ Khasra No  Does not match with the actual in scrutiny View form actual::"+SCRBPR_PlotKhasaraNo.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Bpr_PlotKhasraNo.get(CurrentinvoCount));
			}


			String scrutiny_PlotMSP=mGetText("id", mGetPropertyFromFile("tp_BprMspPlotNoid"),"value");
			System.out.println("Plot No MSP  is in Scutiny : "+scrutiny_PlotMSP);
			SCRBPR_PlotMSP.add(scrutiny_PlotMSP);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Plot No MSP is in Scutiny Process Arraylis is::"+SCRBPR_PlotMSP.get(CurrentinvoCount)+" "+"Plot No MSP is at Application form  Arraylist is::"+Bpr_PlotNoMSP.get(CurrentinvoCount));

				mAssert(Bpr_PlotNoMSP.get(CurrentinvoCount), SCRBPR_PlotMSP.get(CurrentinvoCount), "Plot No. (MSP) Does not match with the actual in scrutiny View form actual::"+SCRBPR_PlotMSP.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Bpr_PlotNoMSP.get(CurrentinvoCount));
			}


			String scrutiny_MaujaNo=mGetText("id", mGetPropertyFromFile("tp_BprMaujaAsserNoid"),"value");
			System.out.println("Mauja No  is in Scutiny : "+scrutiny_MaujaNo);
			SCRBPR_MaujaNo.add(scrutiny_MaujaNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Mauja No is in Scutiny Process Arraylis is::"+SCRBPR_MaujaNo.get(CurrentinvoCount)+" "+"Mauja No is at Application form  Arraylist is::"+Bpr_MaujaNo.get(CurrentinvoCount));

				mAssert(Bpr_MaujaNo.get(CurrentinvoCount), SCRBPR_MaujaNo.get(CurrentinvoCount), "Mauja No Does not match with the actual in scrutiny View form actual::"+SCRBPR_MaujaNo.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Bpr_MaujaNo.get(CurrentinvoCount));
			}

			String scrutiny_HoldingNo=mGetText("id", mGetPropertyFromFile("tp_BprHoldingNoid"),"value");
			System.out.println("Holding No  is in Scutiny : "+scrutiny_HoldingNo);
			SCRBPR_HoldingNo.add(scrutiny_HoldingNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Holding No is in Scutiny Process Arraylis is::"+SCRBPR_HoldingNo.get(CurrentinvoCount)+" "+"Holding No is at Application form  Arraylist is::"+Bpr_HoldingNo.get(CurrentinvoCount));

				mAssert(Bpr_HoldingNo.get(CurrentinvoCount), SCRBPR_HoldingNo.get(CurrentinvoCount), "Holding No Does not match with the actual in scrutiny View form actual::"+SCRBPR_HoldingNo.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Bpr_HoldingNo.get(CurrentinvoCount));
			}

			String scrutiny_KhataNo=mGetText("id", mGetPropertyFromFile("tp_BprKhataNoid"),"value");
			System.out.println("Khata No is in Scutiny : "+scrutiny_KhataNo);
			SCRBPR_KhataNo.add(scrutiny_KhataNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Khata No is in Scutiny Process Arraylis is::"+SCRBPR_KhataNo.get(CurrentinvoCount)+" "+"Khata No is at Application form  Arraylist is::"+Bpr_KhataNo.get(CurrentinvoCount));

				mAssert(Bpr_KhataNo.get(CurrentinvoCount), SCRBPR_KhataNo.get(CurrentinvoCount), "Khata No Does not match with the actual in scrutiny View form actual::"+SCRBPR_KhataNo.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Bpr_KhataNo.get(CurrentinvoCount));
			}
			mCustomWait(1000);
			mTakeScreenShot();
			mscroll(0, 350);
			String scrutiny_Tojino=mGetText("id", mGetPropertyFromFile("tp_BprTojiNoid"),"value");
			System.out.println("Toji No is in Scutiny : "+scrutiny_Tojino);
			SCRBPR_TojiNo.add(scrutiny_Tojino);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Toji Nois in Scutiny Process Arraylis is::"+SCRBPR_TojiNo.get(CurrentinvoCount)+" "+"Toji No is at Application form  Arraylist is::"+Bpr_TojiNo.get(CurrentinvoCount));

				mAssert(Bpr_TojiNo.get(CurrentinvoCount), SCRBPR_TojiNo.get(CurrentinvoCount), "Toji No Does not match with the actual in scrutiny View form actual::"+SCRBPR_TojiNo.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Bpr_TojiNo.get(CurrentinvoCount));
			}

			String scrutiny_Village=mGetText("id", mGetPropertyFromFile("tp_BprVillageAsserid"),"value");
			System.out.println("Village/Mohallah is in Scutiny : "+scrutiny_Village);
			SCRBPR_Village.add(scrutiny_Village);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Village/Mohallah is in Scutiny Process Arraylis is::"+SCRBPR_Village.get(CurrentinvoCount)+" "+"Village/Mohallah is at Application form  Arraylist is::"+Bpr_Village.get(CurrentinvoCount));

				mAssert(Bpr_Village.get(CurrentinvoCount), SCRBPR_Village.get(CurrentinvoCount), "Village/Mohallah Does not match with the actual in scrutiny View form actual::"+SCRBPR_Village.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Bpr_Village.get(CurrentinvoCount));
			}

			String scrutiny_projectName=mGetText("id", mGetPropertyFromFile("tp_BprProjectNameid"),"value");
			System.out.println(" Project Name is in Scutiny : "+scrutiny_projectName);
			SCRBPR_projectName.add(scrutiny_projectName);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Project Name is in Scutiny Process Arraylis is::"+SCRBPR_projectName.get(CurrentinvoCount)+" "+"Project Name is at Application form  Arraylist is::"+Bpr_ProjectName.get(CurrentinvoCount));

				mAssert(Bpr_ProjectName.get(CurrentinvoCount), SCRBPR_projectName.get(CurrentinvoCount), "Project Name Does not match with the actual in scrutiny View form actual::"+SCRBPR_projectName.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Bpr_ProjectName.get(CurrentinvoCount));
			}
			mTakeScreenShot();
			String scrutiny_SchemeName=mGetText("id", mGetPropertyFromFile("tp_BprSchemeNameid"),"value");
			SCRBPR_SchemeName.add(scrutiny_SchemeName);
			System.out.println("Scheme Name is in Scutiny : "+scrutiny_SchemeName);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				System.out.println("Scheme Name is in Scutiny Process Arraylis is::"+SCRBPR_SchemeName.get(CurrentinvoCount)+" "+"Scheme Name at Application form  Arraylist is::"+Bpr_SchemeName.get(CurrentinvoCount));

				mAssert(Bpr_SchemeName.get(CurrentinvoCount), SCRBPR_SchemeName.get(CurrentinvoCount), "Scheme Name Does not match with the actual in scrutiny View form actual::"+SCRBPR_SchemeName.get(CurrentinvoCount)+"in scrutiny View form,Expected::"+Bpr_SchemeName.get(CurrentinvoCount));
			}


			//Technical person Tabel reading for land development
			if(mGetPropertyFromFile("agencyType").equalsIgnoreCase("Architect"))
			{

				BPR_AgencyreadingtechnicalPerson(mGetPropertyFromFile("tp_BprTechdetailsTabelAsserid"));
			}
			else
			{
				BPR_CitizenreadingtechnicalPerson(mGetPropertyFromFile("tp_BprTechdetailsTabelAsserid"));

			}
			mTakeScreenShot();

			mCustomWait(2000);
		}
		catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();	
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in bpr_ViewandAssertform method"+scrutinylevelcounter);
		}
	}

	////6666
	public void OccupancyCertificateViewScrutinyAssertion(){
		try{

			String Scr_CaseId=mGetText("css", mGetPropertyFromFile("tp_OcReadCaseId"),"value");
			System.out.println("Case Id is::"+Scr_CaseId);
			SCr_OC_Caseid.add(Scr_CaseId);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(SCr_OC_Caseid.get(CurrentinvoCount),OC_Caseid.get(CurrentinvoCount), "Case Id dose not match in Scrutiny Process");
			}

			String Scr_AppliNo=mGetText("css", mGetPropertyFromFile("tp_OcReadApplicationNoId"),"value");
			System.out.println("Application No is::"+Scr_AppliNo);
			SCr_OC_AppNo.add(Scr_AppliNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(SCr_OC_AppNo.get(CurrentinvoCount), OC_AppNo.get(CurrentinvoCount), "Application No dose not match in Scrutiny form");
			}

			String Scr_Ntrcntr=mGetText("id", mGetPropertyFromFile("tp_OcNatureOfConstructionid"), "value");
			System.out.println("Nature of construction is::"+Scr_Ntrcntr);
			Scr_OC_NatureOfConstruction.add(Scr_Ntrcntr);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(Scr_OC_NatureOfConstruction, OC_NatureOfConstruction, "Nature of Constrution dose not match in Scrutiny form");
			}

			String Scr_OriUseType=mGetText("id", mGetPropertyFromFile("tp_OcPrinCipleUsageTypeid"), "value");
			System.out.println("Principle usage type is::"+Scr_OriUseType);
			SCr_Oc_PrincipleUsagetYpe.add(Scr_OriUseType);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(SCr_Oc_PrincipleUsagetYpe, Oc_PrincipleUsagetYpe, "Principle usage type dose not match in Scrutiny form");
			}

			String Scr_MbNo=mGetText("id", mGetPropertyFromFile("tp_OcMobileNoid"), "value");
			System.out.println("Mobile No is is::"+Scr_MbNo);
			SCr_Oc_MobileNo.add(Scr_MbNo);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(SCr_Oc_MobileNo, Oc_MobileNo, "Applicant Mobile No Dose not match in Scrutiny form");
			}

			String Scr_ApplicationDate=mGetText("id", mGetPropertyFromFile("tp_OcApplicationDateid"), "value");
			System.out.println("Application date is::"+Scr_ApplicationDate);
			SCr_OC_Applicationdate.add(Scr_ApplicationDate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(SCr_OC_Applicationdate, OC_Applicationdate, "Application Date Dose not match in Scrutiny form in Scrutiny form");
			}

			String Scr_ApplicationAppDate=mGetText("id", mGetPropertyFromFile("tp_OcBuildingApprovaldateDateid"), "value");
			System.out.println("Application Approval date is::"+Scr_ApplicationAppDate);
			SCr_Oc_BuildingApprovalDate.add(Scr_ApplicationAppDate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(SCr_Oc_BuildingApprovalDate, Oc_BuildingApprovalDate, "Building Permission Sanction Date Dose not match in Scrutiny form");
			}

			String Scr_ApplAppValidDate=mGetText("id", mGetPropertyFromFile("tp_OcBuildingApprovaldateDateid"), "value");
			System.out.println("Building Approval Validity  date is::"+Scr_ApplAppValidDate);
			SCr_Oc_BuildingApprovalValidityDate.add(Scr_ApplAppValidDate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(SCr_Oc_BuildingApprovalValidityDate, Oc_BuildingApprovalValidityDate, "Building Permission valid Till Date Dose not match in Scrutiny form");
			}

			String Scr_ComplitionDateDate=mGetText("id", mGetPropertyFromFile("tp_OcCompletionDateid"), "value");
			System.out.println("Building CompletionDate  date is::"+Scr_ComplitionDateDate);
			SCr_Oc_ComplitiondateDate.add(Scr_ComplitionDateDate);
			if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
				mAssert(SCr_Oc_ComplitiondateDate, Oc_CompletionDate, "Completion Date Dose not match in Scrutiny form");
			}
		}
		catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();	
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in OccupancyCertificateViewScrutinyAssertion method"+scrutinylevelcounter);
		}
	}


	public static boolean compareList(List ls1,List ls2){
		return ls1.toString().contentEquals(ls2.toString())?true:false;
	}


	public void TP_checklist_verification_asserion(){
		try{
			String clvApplicantName=mGetText("css", mGetPropertyFromFile("ClvApplicantNameid"));
			System.out.println("Name of Applicant in checklist verification form is : " +clvApplicantName);
			clv_ApplicantName.add(clvApplicantName);
			//mAssert(clv_ApplicantName, AppfullnmWdTitle, "Name of Applicant in checklist verification form doen not match with the expected");

			String clvApplicantionNo=mGetText("css", mGetPropertyFromFile("ClvApplicantionNoid"));
			System.out.println("Application No in checklist verification form is : " +clvApplicantionNo);
			clv_ApplicanyionNo.add(clvApplicantionNo);
			//mAssert(clv_ApplicanyionNo, ApplicationNoforLandDev, "Application No in checklist verification form does not match with the Expected"+ApplicationNoforLandDev+",Actual"+clv_ApplicanyionNo);

			String clvServicenameName=mGetText("css", mGetPropertyFromFile("ClvAServiceNameid"));
			System.out.println("Service Name in checklist verification form is : " +clvServicenameName);
			clv_ServicenameName.add(clvServicenameName);
			//mAssert(clv_ServicenameName,ServiceNameforTownPlanning, "Name of Applicant in checklist verification form doen not match with the expected");

			String clvApplicantDate=mGetText("css", mGetPropertyFromFile("ClvApplicantionDateid"));
			System.out.println("Application date in checklist verification form is : " +clvApplicantDate);
			clv_ApplicantionDate.add(clvApplicantDate);
			//mAssert(clv_ApplicantName,clv_ApplicantionDate,"Application date in checklist verification form doen not match with the expected");

			switch(TpServiceName) 
			{
			case "Application for Development Plan" :

				//mAssert(clvApplicantDate,strDate,"Application date in checklist verification form does not match with the expected::: "+strDate+"Actual:::"+clvApplicantDate);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					mAssert(clv_ApplicanyionNo.get(CurrentinvoCount), ApplicationNoforLandDev.get(CurrentinvoCount), "Application No in checklist verification form does not match with the Expected"+ApplicationNoforLandDev.get(CurrentinvoCount)+",Actual"+clv_ApplicanyionNo.get(CurrentinvoCount));
				}
				else
				{
					mAssert(mGetPropertyFromFile("applicationNo"), clvApplicantionNo, "Application No in checklist verification form does not match with the Expected"+mGetPropertyFromFile("applicationNo")+",Actual"+clvApplicantionNo);
				}
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					mAssert(clv_ServicenameName.get(CurrentinvoCount),ServiceNameforTownPlanning.get(CurrentinvoCount), "Service Name in checklist verification form does not match with the expected::: "+ServiceNameforTownPlanning.get(CurrentinvoCount)+"Actual:::"+clv_ServicenameName.get(CurrentinvoCount));
					mAssert(clv_ApplicantName.get(CurrentinvoCount), AppfullnmWdTitle.get(CurrentinvoCount), "Name of Applicant in checklist verification form doen not match with the expected::: "+AppfullnmWdTitle.get(CurrentinvoCount)+"Actual:::"+clv_ApplicantName.get(CurrentinvoCount));
				}
				break;
			case "Application for Building Permit" :
				//mAssert(clvApplicantDate,strDate,"Application date in checklist verification form does not match with the expected::: "+strDate+"Actual:::"+clvApplicantDate);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual"))
				{
					mAssert(clv_ApplicanyionNo.get(CurrentinvoCount), ApplicationNoforLandDev.get(CurrentinvoCount), "Application No in checklist verification form does not match with the Expected"+ApplicationNoforLandDev.get(CurrentinvoCount)+",Actual"+clv_ApplicanyionNo.get(CurrentinvoCount));
				}
				else
				{
					mAssert(mGetPropertyFromFile("applicationNo"), clvApplicantionNo, "Application No in checklist verification form does not match with the Expected"+mGetPropertyFromFile("applicationNo")+",Actual"+clvApplicantionNo);
				}
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					mAssert(clv_ServicenameName.get(CurrentinvoCount),ServiceNameforTownPlanning.get(CurrentinvoCount), "Service Name in checklist verification form does not match with the expected::: "+ServiceNameforTownPlanning.get(CurrentinvoCount)+"Actual:::"+clv_ServicenameName.get(CurrentinvoCount));
					System.out.println("Applicant name at checklist------"+clv_ApplicantName   + "----------&& Applicant Name at application Form"+Bpr_AppfullnmWdTitle);
					mAssert(clv_ApplicantName.get(CurrentinvoCount), APPlicantNameForAssertion.get(CurrentinvoCount), "Name of Applicant in checklist verification form doen not match with the expected::: "+APPlicantNameForAssertion.get(CurrentinvoCount)+"Actual:::"+clv_ApplicantName.get(CurrentinvoCount));
				}
				break;

			case "Occupancy Certificate" :
				//mAssert(clvApplicantDate,strDate,"Application date in checklist verification form does not match with the expected::: "+strDate+"Actual:::"+clvApplicantDate);
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual"))
				{
					mAssert(clv_ApplicanyionNo.get(CurrentinvoCount), ApplicationNoforLandDev.get(CurrentinvoCount), "Application No in checklist verification form does not match with the Expected"+ApplicationNoforLandDev.get(CurrentinvoCount)+",Actual"+clv_ApplicanyionNo.get(CurrentinvoCount));
				}
				else
				{
					mAssert(mGetPropertyFromFile("applicationNo"), clvApplicantionNo, "Application No in checklist verification form does not match with the Expected"+mGetPropertyFromFile("applicationNo")+",Actual"+clvApplicantionNo);
				}
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					mAssert(clv_ServicenameName.get(CurrentinvoCount),ServiceNameforTownPlanning.get(CurrentinvoCount), "Service Name in checklist verification form does not match with the expected::: "+ServiceNameforTownPlanning.get(CurrentinvoCount)+"Actual:::"+clv_ServicenameName.get(CurrentinvoCount));
					System.out.println("Applicant name at checklist------"+clv_ApplicantName   + "----------&& Applicant Name at application Form"+Bpr_AppfullnmWdTitle);
					mAssert(clv_ApplicantName.get(CurrentinvoCount), Oc_ApplicantNamewdoutTitle.get(CurrentinvoCount), "Name of Applicant in checklist verification form doen not match with the expected::: "+Oc_ApplicantNamewdoutTitle.get(CurrentinvoCount)+"Actual:::"+clv_ApplicantName.get(CurrentinvoCount));
				}
			default: break;
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

			//getting Applicantion No and assertion
			String scrutiny_appNo=mGetText("css", mGetPropertyFromFile("Sc_scrutinyProcessAppNoid"));
			System.out.println("Applicantion no is in Scutiny : "+scrutiny_appNo);
			Scrutiny_ApplicantionNo.add(scrutiny_appNo);

			switch(TpServiceName)
			{
			case "Application for Development Plan" :

				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					{
						mAssert(Scrutiny_ApplicantionNo.get(CurrentinvoCount), ApplicationNoforLandDev.get(CurrentinvoCount), "In Scrutiny form Applicantion No dose not match with the Expected ::"+ApplicationNoforLandDev.get(CurrentinvoCount) + "Actual::"+Scrutiny_ApplicantionNo.get(CurrentinvoCount));
						mAssert(Scrutiny_ApplicantName.get(CurrentinvoCount),AppfullnmWdTitle.get(CurrentinvoCount), "Name of Applicant in Scrutiny view form is form doen not match with the expected");
					}
				}
				break;

			case "Occupancy Certificate" :
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					{
						mAssert(Scrutiny_ApplicantionNo.get(CurrentinvoCount), ApplicationNoforLandDev.get(CurrentinvoCount), "In Scrutiny form Applicantion No dose not match with the Expected ::"+ApplicationNoforLandDev.get(CurrentinvoCount) + "Actual::"+Scrutiny_ApplicantionNo.get(CurrentinvoCount));
						mAssert(Scrutiny_ApplicantName.get(CurrentinvoCount),AppfullnmWdTitle.get(CurrentinvoCount), "Name of Applicant in Scrutiny view form is form doen not match with the expected");
					}
				}
				break;

			case "Application for Building Permit" :
				if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){
					{
						mAssert(Scrutiny_ApplicantionNo.get(CurrentinvoCount), ApplicationNoforLandDev.get(CurrentinvoCount), "In Scrutiny form Applicantion No dose not match with the Expected ::"+ApplicationNoforLandDev.get(CurrentinvoCount) + "Actual::"+Scrutiny_ApplicantionNo.get(CurrentinvoCount));
						mAssert(Scrutiny_ApplicantName.get(CurrentinvoCount),Bpr_AppfullnmWdTitle.get(CurrentinvoCount), "Name of Applicant in Scrutiny view form is form doen not match with the expected");
					}
				}
				break;
			}

			/*if(!mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual")){

			}*/

		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();	
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Checklist Verification(Assertion) method failed");
		}
	}


	public static void GetCurrentdate()
	{
		try{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

			String strDate = sdf.format(date);
			System.out.println("formatted date in mm/dd/yy : " + strDate);

			sdf = new SimpleDateFormat("dd-MMM-yyyy");
			strDate = sdf.format(date);
			System.out.println("formatted date in dd/MMM/yyyy : " + strDate);
			ApplicationdateinYear.add(strDate);
			System.out.println("Application date in arraylist is ::::"+Applicationdate);


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

	public void paymentdata()
	{
		try{

			PaymentMode.add(mGetPropertyFromFile("chlanAtULBTypeOfPayModeId"));
			PayAt.add(mGetPropertyFromFile("byBankOrULB"));
			ChallanVerfiDate.add(mGetPropertyFromFile("rti_chlanVerftnDate"));
			BankName.add(mGetPropertyFromFile("chlanAtULbNameOfBankId"));
			chqDdNo.add(mGetPropertyFromFile("rti_chlanVerftnCheDDNo"));
			AccountNo.add(mGetPropertyFromFile("rti_chlanVerftnAccNo"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	//////////////////////////////////////////////////////Scrutiny Process Method//////////////////////////////////////////


	public void tp_Gen_SiteInsp_Letter()
	{
		try
		{
			mSendKeys("id",textid, mGetPropertyFromFile("tp_FirstlsSiteInspectionYNdata"));
			mCustomWait(1200); 
			mTakeScreenShot();			

			if(mGetPropertyFromFile("tp_FirstlsSiteInspectionYNdata").equalsIgnoreCase("yes"))
			{
				mClick("css", imageid);
				siteInspectionLetterGenratn();
			}			
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in tp_Gen_SiteInsp_Letter method"+scrutinylevelcounter);
		}
	}


	public void tp_Gen_TrutiPatra()
	{
		try
		{
			mSendKeys("id", textid, mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraYesdata"));
			mCustomWait(1200); 

			if(TrutiPatraGenerated==false){
				if(mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraYesdata").equalsIgnoreCase("yes"))
				{
					mClick("css",imageid);
					trutiPatraGenratn("tp_TrutiPatraGenTrutiPatraYNdata");
				}
			}
			else
			{
				mClick("css",imageid);
				trutiPatraVerification();			
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in tp_Gen_TrutiPatra method"+scrutinylevelcounter);
		}
	}


	public void tp_InfoasPer_TrutiPatra()
	{
		try
		{
			mSendKeys("id",textid, mGetPropertyFromFile("tp_LOIGenTrutiPatraVerifddata"));
			mCustomWait(1200); 		

			if(mGetPropertyFromFile("tp_LOIGenTrutiPatraVerifddata").equalsIgnoreCase("yes")) 
			{
				mClick("css", imageid);
				trutiPatraVerification();
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in tp_InfoasPer_TrutiPatra method"+scrutinylevelcounter);
		}
	}



	public void tp_EditServiceInfo()
	{
		try
		{
			mSendKeys("id", textid, mGetPropertyFromFile("tp_LOIGenEditServiceYesdata"));
			mCustomWait(1200); 
			if(mGetPropertyFromFile("tp_LOIGenEditServiceYesdata").equalsIgnoreCase("yes")) 
			{
				mClick("css", imageid);	
				mWaitForVisible("id", mGetPropertyFromFile("tp_LOIGenEditServiceAppNameid"));
				mCustomWait(1000);
				ClubbedUtils.tp_mDynamicUsageTypeTableGrid(mGetPropertyFromFile("tp_LOIGenEditServiceUsageTypeTableid"),mGetPropertyFromFile("tp_LOIGenEditUsageTypedata"),mGetPropertyFromFile("tp_LOIGenEditCoveredAreadata"));
				mCustomWait(1000);
				mSendKeys("id",mGetPropertyFromFile("tp_LOIGenEditServiceParkingAreaid"),mGetPropertyFromFile("tp_LOIGenEditServiceParkingAreadata"));
				mCustomWait(1000);
				mSendKeys("id",mGetPropertyFromFile("tp_LOIGenEditServiceAreaLeftid"),mGetPropertyFromFile("tp_LOIGenEditServiceAreaLeftdata"));
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("tp_LOIGenEditServiceSubmitBtnid"));
				mCustomWait(2000);
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in tp_EditServiceInfo method"+scrutinylevelcounter);
		}
	}



	public void tp_Gen_LOILetter()
	{
		try
		{/*
			mCustomWait(1200); 
			if(mGetPropertyFromFile("municipality").equalsIgnoreCase("Gaya Municipal Corporation") && mGetPropertyFromFile("tp_LOIGenFinalDecisiondata").equalsIgnoreCase("yes") && mGetPropertyFromFile("tp_NameofService").equalsIgnoreCase("BuildingPermit"))  
			{			
				mSendKeys("id", textid, mGetPropertyFromFile("tp_LOIGenGenerateLoidata"));
				mCustomWait(1000);
				mTakeScreenShot();
			}

			else 
			{*/				
			mSendKeys("id", textid, mGetPropertyFromFile("tp_LOIGenGenerateLoidata"));
			if(mGetPropertyFromFile("tp_LOIGenFinalDecisiondata").equalsIgnoreCase("yes") && mGetPropertyFromFile("tp_LOIGenGenerateLoidata").equalsIgnoreCase("yes") ) 		
			{

				//clicking img to check LOI generation form
				mClick("css", imageid);
				mCustomWait(1000);

				String applicationNumber = mGetText("xpath", mGetPropertyFromFile("tp_LOIGenNoAtFinalDecisionFormid"));
				mCustomWait(1000);

				if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
				{
					mAssert(applicationNumber, applicationNo.get(CurrentinvoCount), "Application  number does not matches at final decision form.     Actual   :"+applicationNumber+"     Expected     :"+applicationNo.get(CurrentinvoCount));
					System.out.println(applicationNumber);
				}
				else
				{
					mAssert(applicationNumber, mGetPropertyFromFile("applicationNo"), "Application  number does not matches at final decision form.     Actual   :"+applicationNumber+"     Expected     :"+mGetPropertyFromFile("applicationNo"));
					System.out.println(applicationNumber);
				}

				String loiDate = driver.findElement(By.id(mGetPropertyFromFile("tp_LOIGenLOIDateAtFInalDecisionFormid"))).getAttribute("value");
				mCustomWait(1000);
				System.out.println(loiDate);

				//sending LOI Remarks
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("tp_LOIGenLOIRemarksid"), mGetPropertyFromFile("tp_LOIGenLOIRemarksdata"));
				mCustomWait(2000);

				//clicking on verify document
				ClubbedUtils.tp_mDynamicLOIChargeTableGrid(mGetPropertyFromFile("tp_LOIGenLOIFormChargeDetailsTableid"));


				//final decision form submit Button
				mTakeScreenShot();
				mCustomWait(1000);
				mWaitForVisible("css", mGetPropertyFromFile("tp_LOIGenFormSubBtnid"));
				mscroll(0, 200);				 	
				mCustomWait(1000);
				mTakeScreenShot();
				mClick("css", mGetPropertyFromFile("tp_LOIGenFormSubBtnid"));
				mCustomWait(2000);
				mscroll(0, 670);
				mCustomWait(1000);
			}

			/*}*/
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in tp_Gen_LOILetter method"+scrutinylevelcounter);
		}
	}

	public void tp_Gen_RejectionLetter()
	{
		try
		{
			if(mGetPropertyFromFile("tp_LOIGenFinalDecisiondata").equalsIgnoreCase("no")) 
			{
				if(mGetPropertyFromFile("tp_LOIGenFinalDecisiondata").equalsIgnoreCase("no")){

				}
				mSendKeys("id", textid, mGetPropertyFromFile("tp_LOIGenGenerateRejLetterdata"));
				mCustomWait(1200);

				mClick("css", imageid);

				String ApplicationNum =   driver.findElement(By.id(mGetPropertyFromFile("tp_LOIGenAppNoAtRejectnOfAppid"))).getAttribute("value");
				System.out.println(ApplicationNum);

				if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
				{
					mAssert(ApplicationNum, applicationNo.get(CurrentinvoCount), "Application Number does not match at rejection.    Actual   :"+ApplicationNum+"     Expected     :"+applicationNo.get(CurrentinvoCount));
					System.out.println(ApplicationNum);
				}
				else
				{
					mAssert(ApplicationNum, mGetPropertyFromFile("applicationNo"), "Application Number does not match at rejection.     Actual   :"+ApplicationNum+"     Expected     :"+mGetPropertyFromFile("applicationNo"));
					System.out.println(ApplicationNum);
				}

				//to be start with rejection ltr selenium ide file
				mWaitForVisible("id", mGetPropertyFromFile("tp_LOIGenRejRemarkid"));
				mSendKeys("id",  mGetPropertyFromFile("tp_LOIGenRejRemarkid"), mGetPropertyFromFile("tp_LOIGenRejRemarkdata"));

				//sending letter Number
				mWaitForVisible("id", mGetPropertyFromFile("tp_LOIGenRejLetterNoid"));
				mSendKeys("id", mGetPropertyFromFile("tp_LOIGenRejLetterNoid"), mGetPropertyFromFile("tp_LOIGenRejLetterNodata"));
				mCustomWait(1000);

				//selecting rejection letter date
				mDateSelect("id", mGetPropertyFromFile("tp_LOIGenRejDateid"), mGetPropertyFromFile("tp_LOIGenRejDatedata"));

				//selecting rejection list
				mWaitForVisible("id", mGetPropertyFromFile("tp_LOIGenRejDocListid"));
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("tp_LOIGenRejDocListid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);

				//Submit Button
				mWaitForVisible("xpath", mGetPropertyFromFile("tp_LOIGenRejAppSubBtnid"));
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("tp_LOIGenRejAppSubBtnid"));
				mCustomWait(1000);


			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in tp_Gen_RejectionLetter method"+scrutinylevelcounter);
		}
	}


	//first level ScrutinyProcess(TP)
	public void siteInspectionLetterGenratn()
	{
		try
		{

			//sending employee Name
			mWaitForVisible("id", mGetPropertyFromFile("tp_SiteInspectEmpNmid"));
			mSelectDropDown("id",mGetPropertyFromFile("tp_SiteInspectEmpNmid"),mGetPropertyFromFile("tp_SiteInspectEmpNmdata"));
			String a=mCaptureSelectedValue("id",mGetPropertyFromFile("tp_SiteInspectEmpNmid"));
			SCR_SiteInspectEmpName.add(a.trim());
			System.out.println("Inspector name is ::"+SCR_SiteInspectEmpName);

			System.out.println("SCR_SiteInspectEmpName"+SCR_SiteInspectEmpName);
			//sending employee inspection date
			mDateSelect("id",mGetPropertyFromFile("tp_SiteInspectDateid"), mGetPropertyFromFile("tp_SiteInspectDatedata"));
			mCustomWait(1000);
			String date_s = mGetPropertyFromFile("tp_SiteInspectDatedata"); 
			SimpleDateFormat dt = new SimpleDateFormat("dd/MMM/yyyy"); 
			Date date = dt.parse(date_s); 
			SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
			System.out.println(dt1.format(date));
			String Inspectiondate=dt1.format(date);
			System.out.println(Inspectiondate);
			SCR_SiteInspectionEmpDate.add(Inspectiondate);
			System.out.println(SCR_SiteInspectionEmpDate);
			//Click on submit button
			mWaitForVisible("css", mGetPropertyFromFile("tp_SiteInspectSubmitid"));
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("tp_SiteInspectSubmitid"));
			mCustomWait(1000);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in siteInspectionLetterGenratn method"+scrutinylevelcounter);

		}

	}	



	// for verifying the trupti patra gridtable
	// created by hiren on 28/10/2016
	// changes done on 2/11/2016

	private void tp_TrutiPatraverify(String tableId){

		try{			
			WebElement table = driver.findElement(By.xpath(tableId));
			List<WebElement> rows = null;
			rows = table.findElements(By.tagName("tr"));
			System.out.println(rows);
			int rwcount =rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

			// Iterate to get the value of each cell in table along with element id
			int Rowno=0;

			for(WebElement rowElement:rows)

			{
				System.out.println("row incremented"+Rowno);
				List<WebElement> cols=rowElement.findElements(By.xpath("td"));
				int colCount=cols.size();
				System.out.println("Columns are : "+colCount);
				int Columnno=0;

				List<WebElement> selectElements= driver.findElements(By.cssSelector("input[type='checkbox'][value='Y']"));
				System.out.println("the list is : "+selectElements); 
				int a=selectElements.size();
				System.out.println("the size of table is ::" +a);

				for(WebElement colElement:cols)
				{
					System.out.println("inside loop");					
					if(Columnno==2)
					{						
						for(int b=0;b<a;b++) 
						{
							if( !selectElements.get(b).isSelected())
							{
								mCustomWait(500);
								selectElements.get(b).click();
								mCustomWait(500);
							}
						}			
					}

					else
					{
						System.out.println("inside else");
					}
					Columnno=Columnno+1;
					System.out.println("column no is :"+Columnno);
				}
				Rowno=Rowno+1;
				System.out.println("row no is :"+Rowno);
			}	
			mTakeScreenShot(); 
		}

		catch(Exception e){
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in tp_TrutiPatraverify method"+scrutinylevelcounter);
		}
	}

	//Second level ScrutinyProcess(TP)
	public void trutiPatraGenratn(String YesNo)
	{
		try
		{

			//waiting for truti patra to be open
			mWaitForVisible("xpath", mGetPropertyFromFile("tp_TrutiPatraGenApplicationNoAtFormid"));
			String ApplicationNo = mGetText("xpath", mGetPropertyFromFile("tp_TrutiPatraGenApplicationNoAtFormid"));
			mCustomWait(1000);
			System.out.println(ApplicationNo);
			mTakeScreenShot();
			//String ApplicationNo = driver.findElement(By.xpath("//form[@id='frmTrutiPatra']/div[3]/div/div[2]")).getText();

			//selecting All remarks
			mCustomWait(1000);
			mWaitForVisible("id",  mGetPropertyFromFile("tp_TrutiPatraGenDetailRemarksid"));
			mClick("id", mGetPropertyFromFile("tp_TrutiPatraGenDetailRemarksid"));
			mCustomWait(1000);

			mSendKeys("id", mGetPropertyFromFile("tp_TrutiPatraGenRemarkFieldid"),mGetPropertyFromFile("tp_TrutiPatraGenRemarkFielddata"));
			SCR_TrutiPatraRemarks.add(mGetPropertyFromFile("tp_TrutiPatraGenRemarkFielddata"));
			mCustomWait(1000);
			mTakeScreenShot();

			//selecting hearing yes or no
			if(mGetPropertyFromFile("tp_TrutiPatraGenHearingYesNodata").equalsIgnoreCase("Yes"))
			{

				HearingDated=true;

				mClick("id", mGetPropertyFromFile("tp_TrutiPatraGenHearingYesid"));
				mCustomWait(1000);

				//sending hearing date
				mWaitForVisible("id", mGetPropertyFromFile("tp_TrutiHearingScheduleDateid"));
				mDateSelect("id",mGetPropertyFromFile("tp_TrutiHearingScheduleDateid"), mGetPropertyFromFile("tp_TrutiHearingScheduleDatedata"));
				//mClick("xpath", mGetPropertyFromFile("tp_TrutiPatraGenHearingDateTimeDoneid"));
				TrutiHearingScheduleDate.add(mGetPropertyFromFile("tp_TrutiHearingScheduleDatedata"));
				mCustomWait(1000);

				//selcting approve id
				mClick("id", mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraApprvid"));
				mTakeScreenShot();

				//sending detail remark
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("tp_TrutiPatraGenViewDocRemarksid"), mGetPropertyFromFile("tp_TrutiPatraGenViewDocRemarksdata"));
				TrutiViewdocRemarks.add(mGetPropertyFromFile("tp_TrutiPatraGenViewDocRemarksdata"));
				mCustomWait(1000);
				mTakeScreenShot();

				//truti patra form submit button
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraApplnSubBtnid"));
				mGenericWait();
			}

			if(mGetPropertyFromFile("tp_TrutiPatraGenHearingYesNodata").equalsIgnoreCase("no"))
			{
				mWaitForVisible("id", mGetPropertyFromFile("tp_TrutiPatraGenHearingNoid"));
				mClick("id", mGetPropertyFromFile("tp_TrutiPatraGenHearingNoid"));
				mCustomWait(1000);

				//sending reason for no hearing
				mSendKeys("id", mGetPropertyFromFile("tp_TrutiPatraGenHearingNoReasonid"), mGetPropertyFromFile("tp_TrutiPatraGenYesNoHearinReasondata"));
				HearingReasonRemarks.add(mGetPropertyFromFile("tp_TrutiPatraGenYesNoHearinReasondata"));
				//selcting approve id
				mCustomWait(2000);
				mClick("id", mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraApprvid"));
				mTakeScreenShot();

				//sending detail remark
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("tp_TrutiPatraGenViewDocRemarksid"), mGetPropertyFromFile("tp_TrutiPatraGenViewDocRemarksdata"));
				TrutiViewdocRemarks.add(mGetPropertyFromFile("tp_TrutiPatraGenViewDocRemarksdata"));
				mCustomWait(1000);
				mTakeScreenShot();

				//truti patra form submit button
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraApplnSubBtnid"));
				mGenericWait();
			}		

			TrutiPatraGenerated=true;
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in trutiPatraGenratn method"+scrutinylevelcounter);
		}
	}

	// Third level ScrutinyProcess(TP)
	public void trutiPatraVerification()
	{
		try
		{

			tp_TrutiPatraverify(mGetPropertyFromFile("tp_TrutiPatraGenVerifyTableid"));


			if(HearingDated)
			{
				mCustomWait(1500); 
				mDateSelect("id", mGetPropertyFromFile("tp_TrutiActualHearingDateid"), mGetPropertyFromFile("tp_TrutiActualHearingDatedata"));
				TrutiHearingScheduleDate.add(mGetPropertyFromFile("tp_TrutiActualHearingDatedata"));
				mCustomWait(5000);

				mSelectDropDown("id",mGetPropertyFromFile("tp_TrutiPatraGenHearingStatusid"), mGetPropertyFromFile("tp_TrutiPatraGenHearingStatusdata"));
				HearingScheduleSchedule.add(mGetPropertyFromFile("tp_TrutiPatraGenHearingStatusdata"));
				mCustomWait(2000);
				mTakeScreenShot();

				mClick("css", mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraApplnSubBtnid"));
				mCustomWait(1000);
			}

			else
			{
				mCustomWait(2000);
				mTakeScreenShot();

				mClick("css", mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraApplnSubBtnid"));
				mCustomWait(1000);				
			}


			TrutiPatraGenerated=false;	
			HearingDated=false;

			/*			

			//checking hearing yes or no
			if(mGetPropertyFromFile("tp_LOIGenHearingYesNodata").equalsIgnoreCase("no")) 
			{
				//No Hearing
				mWaitForVisible("id", mGetPropertyFromFile("tp_TrutiPatraGenHearingNoid"));
				mClick("id", mGetPropertyFromFile("tp_TrutiPatraGenHearingNoid"));
				mCustomWait(1000);

				//sending remarks
				mSendKeys("id", mGetPropertyFromFile("tp_TrutiPatraGenHearingNoReasonid"), mGetPropertyFromFile("tp_LOIGenYesNoHearinReasondata"));
				mCustomWait(1000);					

				//verify Doc radio
				mClick("id", mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraApprvid"));
				mCustomWait(1000);

				//sending remarks
				mSendKeys("id", mGetPropertyFromFile("tp_TrutiPatraGenViewDocRemarksid"), mGetPropertyFromFile("tp_LOIGenViewDocRemarksdata"));
				mCustomWait(1000);

				mTakeScreenShot();
				//Truti patra verification submit button
				mWaitForVisible("css", mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraApplnSubBtnid"));
				mClick("css", mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraApplnSubBtnid"));
				mCustomWait(1000);
			}
			else if(mGetPropertyFromFile("tp_LOIGenHearingYesNodata").equalsIgnoreCase("yes")) 
			{

				//to be completed after configuration
				// hearing yes

				mWaitForVisible("id", mGetPropertyFromFile("tp_TrutiPatraGenHearingYesid"));
				mClick("id", mGetPropertyFromFile("tp_TrutiPatraGenHearingYesid"));
				mCustomWait(1000);

				//sending hearing date ane time
				mDateSelect("id",mGetPropertyFromFile("tp_TrutiPatraGenYesHearingDateid"),mGetPropertyFromFile("tp_LOIGenYesHearingDatedata"));
				mCustomWait(1000);

				//Done Button
				mWaitForVisible("xpath", mGetPropertyFromFile("tp_TrutiPatraGenHearingDateTimeDoneid"));
				mClick("xpath", mGetPropertyFromFile("tp_TrutiPatraGenHearingDateTimeDoneid"));
				mCustomWait(1000);

				//document rejection checkbox
				mWaitForVisible("id",mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraRejectid"));
				mClick("id",mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraRejectid"));
				mCustomWait(1000);

				//sending rejection remark
				mSendKeys("id", mGetPropertyFromFile("tp_TrutiPatraGenViewDocRemarksid"), mGetPropertyFromFile("tp_LOIGenViewDocRemarksdata"));

				//submit button
				mCustomWait(1000);
				mTakeScreenShot();
				mClick("css", mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraApplnSubBtnid"));
				mCustomWait(1000);
			}	
			 */

		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in trutiPatraVerification method"+scrutinylevelcounter);

		}
	}
	public void scrutinyPopUpmsg()
	{
		try{
			String scrutinyPopUpMsg1 = mGetText("css", mGetPropertyFromFile("Sc_AppMsgid"));
			System.out.println("Pop Up message :" +scrutinyPopUpMsg1.replace("\n", ""));
			mCustomWait(2000);
			String scrutinyPopUpMsg=scrutinyPopUpMsg1.replaceAll("[0-9]", "");
			System.out.println("Pop Up message without number :"+scrutinyPopUpMsg.replace("\n", ""));


			String scrutinyPopUpMsg2 = mGetText("css", mGetPropertyFromFile("rnl_LOIGenAppMsgid"));
			mCustomWait(1000);
			scrutinyPopUpMsg2=scrutinyPopUpMsg2.replaceAll("[^0-9]", "");
			LoiNum=scrutinyPopUpMsg2;
			Scrutiny_Msg_LOINoList.add(LoiNum);
			System.out.println("Final Scrutiny LOI Generation Message LOI No is =="+LoiNum);
			System.out.println("Final Scrutiny LOI Generation Message LOI No is =="+scrutinyPopUpMsg2);

			mTakeScreenShot();
			// pop up messages at different level scrutiny///////Piyush// Town Planning

			if(!mGetPropertyFromFile("tp_FirstlsSiteInspectionYNdata").equalsIgnoreCase("yes") && scrutinylevelcounter==1)
			{	
				mAssert(scrutinyPopUpMsg, mGetPropertyFromFile("tp_SiteInspnNAppNoMsgdata"), "Msg at First Scrutiny Level for site inspection letter generation scrutiny submit Actual msg::::"+scrutinyPopUpMsg+" Expected::::"+mGetPropertyFromFile("tp_SiteInspnNAppNoMsgdata"));
			}
			else
			{

				mAssert(scrutinyPopUpMsg, mGetPropertyFromFile("tp_SiteInspnWidoutInspnLetterMsgdata"), "Msg at First Scrutiny Level for site inspection letter generation scrutiny submit Actual msg::::"+scrutinyPopUpMsg+" Expected::::"+mGetPropertyFromFile("tp_SiteInspnWidoutInspnLetterMsgdata"));
			}

			if(mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraYNdata").equalsIgnoreCase("yes") && scrutinylevelcounter==2)
			{	
				mAssert(scrutinyPopUpMsg, mGetPropertyFromFile("tp_TrutiPatraGenMsgdata"), "Msg at Second Level Scrutiny(Truti Patra) submit Actual msg::::"+scrutinyPopUpMsg+" Expected::::"+mGetPropertyFromFile("tp_TrutiPatraGenMsgdata"));
			}

			if(mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraYNdata").equalsIgnoreCase("yes") && mGetPropertyFromFile("tp_LOIGenEditServicedata").equalsIgnoreCase("yes") && mGetPropertyFromFile("tp_LOIGenGenerateLoidata").equalsIgnoreCase("yes") && scrutinylevelcounter==3)
			{	
				mAssert(scrutinyPopUpMsg.replaceAll("\n", ""), mGetPropertyFromFile("tp_LOIGenWidAllMsgdata"), "Msg at final scrutiny(LOI/Rejection) submit Actual msg::::"+scrutinyPopUpMsg+" Expected::::"+mGetPropertyFromFile("tp_LOIGenWidAllMsgdata"));
			}

			System.out.println(scrutinyPopUpMsg1.replaceAll("\n", ""));
		}
		catch(Exception e){
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Scrutiny pop up message assertion scrutinyPopUpmsg method");
		}
	}

	public void tp_BuildingPermitEditServiceInfo()
	{
		try
		{
			mSendKeys("id", textid, mGetPropertyFromFile("tp_LOIGenEditServiceYesdata"));
			mCustomWait(1200); 
			if(mGetPropertyFromFile("tp_LOIGenEditServiceYesdata").equalsIgnoreCase("yes")) 
			{
				mClick("css", imageid);	
				mWaitForVisible("id", mGetPropertyFromFile("tp_LOIGenEditServiceAppNameid"));
				mCustomWait(1000);
				mCustomWait(200);
				//Sending Principal usage type
				mWaitForVisible("id", mGetPropertyFromFile("tp_BprprincipalUsageTypeid"));
				mSelectDropDown("id", mGetPropertyFromFile("tp_BprprincipalUsageTypeid"), mGetPropertyFromFile("tp_BprprincipalUsageTypedata"));
				String BprprincipalUsageType=mCaptureSelectedValue("id", mGetPropertyFromFile("tp_BprprincipalUsageTypeid"));
				System.out.println(BprprincipalUsageType);
				mCustomWait(200);
				//Sending Plot width in meter
				mCustomWait(200);
				mSendKeys("id", mGetPropertyFromFile("tp_BprPlotWidthid"), mGetPropertyFromFile("tp_BprPlotWidthdata"));
				String BprPlotWidth=mGetText("id", mGetPropertyFromFile("tp_BprPlotWidthid"),"value");
				System.out.println(BprPlotWidth);
				mCustomWait(200);
				//Sending Height of Building in Meter
				mCustomWait(200);
				mSendKeys("id", mGetPropertyFromFile("tp_BprBuildingHeightid"), mGetPropertyFromFile("tp_BprBuildingHeightdata"));
				String tp_BprBuildingHeight=mGetText("id", mGetPropertyFromFile("tp_BprBuildingHeightid"),"value");
				System.out.println(tp_BprBuildingHeight);
				mCustomWait(200);
				//Sending Plot Length in meter
				mSendKeys("id", mGetPropertyFromFile("tp_BprPlotLengthid"), mGetPropertyFromFile("tp_BprPlotLengthdata"));
				String tp_BprPlotLength=mGetText("id", mGetPropertyFromFile("tp_BprPlotLengthid"),"value");
				System.out.println(tp_BprPlotLength);
				mCustomWait(200);
				//Sending Plot Area in meter
				mCustomWait(200);
				mSendKeys("id", mGetPropertyFromFile("tp_BprPlotAreaid"), mGetPropertyFromFile("tp_BprPlotAreadata"));
				String tp_BprPlotArea=mGetText("id", mGetPropertyFromFile("tp_BprPlotAreaid"),"value");
				System.out.println(tp_BprPlotArea);

				//Sending Construction cost :
				mCustomWait(200);
				mWaitForVisible("id", mGetPropertyFromFile("tp_BprConstructionCostid"));
				mSendKeys("id", mGetPropertyFromFile("tp_BprConstructionCostid"), mGetPropertyFromFile("tp_BprConstructionCostdata"));
				String BprConstructionCost=mGetText("id", mGetPropertyFromFile("tp_BprConstructionCostid"),"value");
				System.out.println(BprConstructionCost);
				mCustomWait(600);
				mscroll(0, 500);
				mCustomWait(600);
				//Sending GIS Road ID 
				mSendKeys("id", mGetPropertyFromFile("tp_BprGISRoadid"), mGetPropertyFromFile("tp_BprGISRoaddata"));
				String tp_BprGISRoad=mGetText("id", mGetPropertyFromFile("tp_BprGISRoadid"),"value");
				System.out.println(tp_BprGISRoad);
				mCustomWait(200);

				//Sending Existing road Width in Meter
				mCustomWait(200);
				mSendKeys("id", mGetPropertyFromFile("tp_BprExtRoadWidthid"), mGetPropertyFromFile("tp_BprExtRoadWidthdata"));
				String BprExistingRoadWidth=mGetText("id", mGetPropertyFromFile("tp_BprExtRoadWidthid"),"value");
				System.out.println(BprExistingRoadWidth);

				//Sending Road Width as per Master Plan in Meter
				mCustomWait(200);
				mSendKeys("id", mGetPropertyFromFile("tp_BprRoadWidthAsprMastrPlnid"), mGetPropertyFromFile("tp_BprRoadWidthAsprMastrPlndata"));
				String tp_BprRoadWidthAsprMastrPln=mGetText("id", mGetPropertyFromFile("tp_BprRoadWidthAsprMastrPlnid"),"value");
				System.out.println(tp_BprRoadWidthAsprMastrPln);
				mCustomWait(1000);
				//Checking that is Area left for Road Widening 
				/*if(mGetPropertyFromFile("tp_BprArearLeftFrRoadWideningYNdata").equalsIgnoreCase("Yes"))
				{
					if ( !driver.findElement(By.id("roadWidth")).isSelected() )
					{
						driver.findElement(By.id("roadWidth")).click();

						mSendKeys("id", mGetPropertyFromFile("tp_BprArearLeftFrRoadWideningid"), mGetPropertyFromFile("tp_BprArearLeftFrRoadWideningdata"));
						String tp_BprArearLeftFrRoadWidening=mGetText("id", mGetPropertyFromFile("tp_BprArearLeftFrRoadWideningid"),"value");
						System.out.println(tp_BprArearLeftFrRoadWidening);


						String ArearLeftFrRoadWindringft=mGetText("id", mGetPropertyFromFile("tp_BprArearLeftFrRoadWideningid"),"value");
						System.out.println(ArearLeftFrRoadWindringft);	

					}
				}*/

				/*mCustomWait(1000);
				//Checking that is Parking Area
				if(mGetPropertyFromFile("tp_BprParkingAreaYNdata").equalsIgnoreCase("Yes")){
					if ( !driver.findElement(By.id("prAr")).isSelected() )
					{
						driver.findElement(By.id("prAr")).click();
						mSendKeys("id", mGetPropertyFromFile("tp_BprParkingDetailAreaid"), mGetPropertyFromFile("tp_BprParkingDetailAreadata"));
						String tp_BprParkingDetailArea=mGetText("id", mGetPropertyFromFile("tp_BprParkingDetailAreaid"),"value");
						System.out.println(tp_BprParkingDetailArea);

						String ParkingDetailArea=mGetText("id", mGetPropertyFromFile("tp_BprParkingDetailAreaInFtid"),"value");
						System.out.println(ParkingDetailArea);


					}
				}*/
				mCustomWait(1000);
				//Checking Whether falls in airport zone ?
				/*if(mGetPropertyFromFile("tp_BprNearRunWayYNdata").equalsIgnoreCase("Yes"))
				{
					if ( !driver.findElement(By.id("airZone")).isSelected() )
					{
						driver.findElement(By.id("airZone")).click();

						mSendKeys("id", mGetPropertyFromFile("tp_BprNearRunWayDistanceid"), mGetPropertyFromFile("tp_BprNearRunWayDistancedata"));
						String tp_BprNearRunWayDistance=mGetText("id", mGetPropertyFromFile("tp_BprNearRunWayDistanceid"),"value");
						System.out.println(tp_BprNearRunWayDistance);

						mSendKeys("id", mGetPropertyFromFile("tp_BprInnBoundryDistanceid"), mGetPropertyFromFile("tp_BprInnBoundryDistancedata"));
						String tp_BprInnBoundryDistance=mGetText("id", mGetPropertyFromFile("tp_BprInnBoundryDistanceid"),"value");
						System.out.println(tp_BprInnBoundryDistance);

					}
				}
				 */
				mTakeScreenShot();
				mCustomWait(200);

				WebElement table = driver.findElement(By.id("gridFloorDetails"));

				List<WebElement> rows = table.findElements(By.tagName("tr"));
				int rwcount = rows.size();
				System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

				// Iterate to get the value of each cell in table along with element id
				int Rowno = 0;
				for (int a=0;a<rwcount-1;a++) 		
				{
					mCustomWait(500);
					mWaitForVisible("xpath", mGetPropertyFromFile("tp_BprDeleteNoOfFloorid"));
					mClick("xpath", mGetPropertyFromFile("tp_BprDeleteNoOfFloorid"));
					mCustomWait(800);
					mWaitForVisible("xpath", mGetPropertyFromFile("tp_BprDeleteNoOfFloorYesbtnid"));
					mClick("xpath", mGetPropertyFromFile("tp_BprDeleteNoOfFloorYesbtnid"));
					mCustomWait(800);
					mWaitForVisible("css", mGetPropertyFromFile("tp_BprDeleteNoOfFloorPopUpclosebtnid"));
					mClick("css", mGetPropertyFromFile("tp_BprDeleteNoOfFloorPopUpclosebtnid"));


				}

				mCustomWait(3000);
				String Floor = mGetPropertyFromFile("tp_BprAddFloorNodata");
				String BuiltUpArea = mGetPropertyFromFile("tp_BprtppSplitAreadata");
				String BuiltUpAreaInFt = mGetPropertyFromFile("tp_BprtppSplitAreaInFtdata");
				String TypeOfOccupancy = mGetPropertyFromFile("tp_BprtppUtp1data");

				ArrayList FloorList = new ArrayList(Arrays.asList(Floor.split(",")));
				ArrayList BuiltUpAreaList = new ArrayList(Arrays.asList(BuiltUpArea.split(",")));
				ArrayList BuiltUpAreaInFtList = new ArrayList(Arrays.asList(BuiltUpAreaInFt.split(",")));
				ArrayList TypeOfOccupancyList = new ArrayList(Arrays.asList(TypeOfOccupancy.split(",")));

				double sum1= 0;

				for (int i=0; i<FloorList.size();i++)
				{

					//add Floor Details Link
					mCustomWait(750);
					mWaitForVisible("linkText", mGetPropertyFromFile("tp_BprAddFloorDetailsLinkid"));
					mClick("linkText", mGetPropertyFromFile("tp_BprAddFloorDetailsLinkid"));

					mCustomWait(700);
					mWaitForVisible("id", mGetPropertyFromFile("tp_BprAddFloorNoid"));
					mSelectDropDown("id", mGetPropertyFromFile("tp_BprAddFloorNoid"), FloorList.get(i).toString());
					String tp_BprAddFloorNo=mCaptureSelectedValue("id", mGetPropertyFromFile("tp_BprAddFloorNoid"));



					mCustomWait(700);
					mSendKeys("id", mGetPropertyFromFile("tp_BprtppSplitAreaid"), BuiltUpAreaList.get(i).toString());
					String BuiltupArea=mGetText("id", mGetPropertyFromFile("tp_BprtppSplitAreaid"));


					mCustomWait(700);
					mWaitForVisible("id", mGetPropertyFromFile("tp_BprtppUtp1id"));
					mSelectDropDown("id", mGetPropertyFromFile("tp_BprtppUtp1id"), TypeOfOccupancyList.get(i).toString());
					String tp_BprUsagetype=mCaptureSelectedValue("id", mGetPropertyFromFile("tp_BprtppUtp1id"));


					//submit
					mCustomWait(500);
					mWaitForVisible("xpath", mGetPropertyFromFile("tp_BprAddFloorSubBtnid"));
					mClick("xpath", mGetPropertyFromFile("tp_BprAddFloorSubBtnid"));

					//Floor details Pop up msg
					mWaitForVisible("css", mGetPropertyFromFile("tp_BprAddFloorFancyCloseid"));
					mCustomWait(500);

					//Asserting Message on Popup
					String msgAtFloorDetails = mGetText("css", mGetPropertyFromFile("tp_BprAddFloorFancyMsgid"));

					mCustomWait(500);
					System.out.println(msgAtFloorDetails);
					mAssert(msgAtFloorDetails, mGetPropertyFromFile("tp_BprAddFloorFancyMsgdata"), "Actual   :"+msgAtFloorDetails+"  Expected   :"+mGetPropertyFromFile("tp_BprAddFloorFancyMsgdata"));

					//Closing Message box
					mCustomWait(700);
					mClick("css", mGetPropertyFromFile("tp_BprAddFloorFancyCloseid"));
					mCustomWait(700);

				}

				mCustomWait(1000);
				mscroll(0, 500);
				mCustomWait(1000);
				//Sending FAR applied for Details
				mCustomWait(200);
				mWaitForVisible("id", mGetPropertyFromFile("tp_BprFarAppliedid"));
				mSendKeys("id", mGetPropertyFromFile("tp_BprFarAppliedid"), mGetPropertyFromFile("tp_BprFarApplieddata"));
				String tp_BprFarApplied=mGetText("id", mGetPropertyFromFile("tp_BprFarAppliedid"), "value");
				System.out.println(tp_BprFarApplied);


				//Sending Permissible FAR 
				mCustomWait(200);
				mWaitForVisible("id", mGetPropertyFromFile("tp_BprFarPermissibleid"));
				mSendKeys("id", mGetPropertyFromFile("tp_BprFarPermissibleid"), mGetPropertyFromFile("tp_BprFarPermissibledata"));
				String tp_BprFarPermissible=mGetText("id", mGetPropertyFromFile("tp_BprFarPermissibleid"), "value");
				System.out.println(tp_BprFarPermissible);


				//Sending Applied Avg. Depth of Plot in meter
				mCustomWait(200);
				mSendKeys("id", mGetPropertyFromFile("tp_BprAvgDepthid"), mGetPropertyFromFile("tp_BprAvgDepthdata"));
				String tp_BprAvgDepth=mGetText("id", mGetPropertyFromFile("tp_BprAvgDepthid"), "value");
				System.out.println(tp_BprAvgDepth);

				//Sending Applied Avg. Width of Plot in Meter
				mCustomWait(200);
				mSendKeys("id", mGetPropertyFromFile("tp_BprAvgWidthid"), mGetPropertyFromFile("tp_BprAvgWidthdata"));
				String tp_BprAvgWidth=mGetText("id", mGetPropertyFromFile("tp_BprAvgWidthid"), "value");
				System.out.println(tp_BprAvgWidth);


				//Sending Applied Front Set Back in Meter
				mCustomWait(200);
				mSendKeys("id", mGetPropertyFromFile("tp_BprFrontSetBackid"), mGetPropertyFromFile("tp_BprFrontSetBackdata"));
				String tp_BprFrontSetBack=mGetText("id", mGetPropertyFromFile("tp_BprFrontSetBackid"), "value");
				System.out.println(tp_BprFrontSetBack);


				//Sending Applied Left Set Back in Meter
				mCustomWait(200);
				mSendKeys("id", mGetPropertyFromFile("tp_BprLeftSideSetBackid"), mGetPropertyFromFile("tp_BprLeftSideSetBackdata"));
				String tp_BprLeftSideSetBack=mGetText("id", mGetPropertyFromFile("tp_BprLeftSideSetBackid"), "value");
				System.out.println(tp_BprLeftSideSetBack);


				//Sending Applied Rear Set Back in Meter
				mCustomWait(200);
				mSendKeys("id", mGetPropertyFromFile("tp_BprRearSetBackid"), mGetPropertyFromFile("tp_BprRearSetBackdata"));
				String tp_BprRearSetBack=mGetText("id", mGetPropertyFromFile("tp_BprRearSetBackid"), "value");
				System.out.println(tp_BprRearSetBack);

				//Sending Applied Right Set Back in Meter
				mCustomWait(200);
				mSendKeys("id", mGetPropertyFromFile("tp_BprRightSideSetBackid"), mGetPropertyFromFile("tp_BprRightSideSetBackdata"));
				String tp_BprRightSideSetBack=mGetText("id", mGetPropertyFromFile("tp_BprRightSideSetBackid"), "value");
				System.out.println(tp_BprRightSideSetBack);
				mCustomWait(500);
				mscroll(0, 1550);
				mCustomWait(500);
				driver.findElement(By.xpath("//*[@id=\"frmBuildingPermitApplicationid\"]/div[21]/input[1]")).click();
				//mWaitForVisible("xpath", mGetPropertyFromFile("tp_LOIGenEditServiceSubmitBtnid"));
				//mClick("xpath", mGetPropertyFromFile("tp_LOIGenEditServiceSubmitBtnid"));
				mCustomWait(2000);
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in tp_EditServiceInfo method"+scrutinylevelcounter);
		}
	}




}
