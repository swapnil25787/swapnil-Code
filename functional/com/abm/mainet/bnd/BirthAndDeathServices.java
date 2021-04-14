package com.abm.mainet.bnd;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.abm.mainet.mkt.MarketCustomErrorMessages;

import common.CommonMethods;
import api.CommonUtilsAPI;
import errorhandling.MainetCustomExceptions;
import excelreader.ExcelReader;
import excelreader.ExcelToProperties;

/**
 * @author Madhuri.dawande
 * @since 24-08-2015
 *
 */


@Listeners(CustomListener.class)
public class BirthAndDeathServices extends CommonMethods {

	//public static String appNo;
	String BirthRegNo;
	String DeathRegNo;
	URL url;
	BufferedInputStream fileToParse;
	PDFParser parser;
	String output;
	String value;
	boolean stillBirthReg = false;
	public static String appcntName;
	String strippedString;
	String birthRegChildName;
	String childName;
	String birthRegFatherName;
	String fatherName;
	String birthRegMotherName;
	String motherName;
	String birthDate;
	String authoBirthDate;
	String bDate;
	String parentsName;
	String birthdtstr;
	String birthDatestr;
	String deathDate;
	String authoDeathDate;
	String dDate;
	String deceasedName;
	String authoDeceasedName;
	String relativeName;
	String authoRelativeName;
	String deceasedMotherName;
	String authoDeceasedMotherName;
	String deceasedRelativeName;
	String deathDatestr;
	String birthPlaceType;
	String parentAddrAtBirth;
	//String used in Death correction form
	String deathCorrDate;
	String deathCorrDeceasedNme;
	String deathCorrFatherNme;
	String deathCorrMotherNme;
	String appSubmitMsg;
	String bndBPLTypeValue;
	boolean bndBPLRegAutho = false;
	String srvcName;
	boolean bndResubmit = false;
	String birthGender;
	String birthWeight;
	String[] appAuthorization;
	String[] docAuthorization;
	public static boolean appStatusRejectedDueToInadequateInformation;

	JavascriptExecutor jse;
	ExcelReader ER = new ExcelReader();

	public BirthAndDeathServices(){


	}

	static CommonMethods common =new CommonMethods();
	//static ExcelReader excel = new ExcelReader();
	ExcelToProperties excelToProp = new ExcelToProperties();
	String thisServiceName;

	@BeforeSuite
	public void beforeSuite(){

		System.out.println("Starting before suite");
		thisClassName=this.getClass().getCanonicalName();
		myClassName = thisClassName;
		System.out.println("This is the current class name: "+myClassName );
		mCreateModuleFolder("BND_",myClassName);
	}


	/////// Birth Registration with Certificate ///////////

	@Test(enabled=false)
	public void birth_RegistrationWithCertificate() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			birthRegistrationCertificate();

			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in birth_RegistrationWithCertificate method");
		}
	}

	@Test(enabled=false)
	public void birthRegWithCert_ChallanVerf() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			mCreateArtefactsFolder("BND_");
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			common.selectUlb();
			common.isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerBirthRegCertServiceName"));
			mCloseBrowser();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in birthRegWithCert_ChallanVerf method");
		}
	}
	
	//Checklist verification
		//////////////////////////
		@Test(enabled=false)
		public void birthRegWithCert_CheckListVerification() throws InterruptedException, IOException
		{
			try
			{
				currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
				ER.datareader();
				thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
				BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
				if(mGetPropertyFromFile("bnd_chkListVrfnApplicable").equals("Yes"))
				{
					mCreateArtefactsFolder("BND_");
					//beforeBND_ChecklistVerification();
					common.ChecklistVerification(mGetPropertyFromFile("cfc_chcklistverfn_username"), mGetPropertyFromFile("cfc_chcklistverfn_pwd"));
				}
				else
				{
					System.out.println("Checklist Verification is not required");
				}
				CommonUtilsAPI.bndRegErrorMsg.assertAll();
			}
			catch(Exception e)
			{
				inAtTest=true;
				throw new MainetCustomExceptions("Error in birthCert_CheckListVerification method");	
			}
		}

	@Test(enabled=false)
	public void birthRegWithCert_BirthRegAuthorization() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			appAuthorization = mGetPropertyFromFile("bnd_isAppnAuthorized").split(",");
			docAuthorization = mGetPropertyFromFile("bnd_isDocAuthorized").split(",");
			mCreateArtefactsFolder("BND_");
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			common.selectUlb();
			common.departmentLogin(mGetPropertyFromFile("bndAuthoDeptUserName"),mGetPropertyFromFile("bndAuthoDeptPassword"));
			if(appAuthorization[0].equalsIgnoreCase("Yes"))
			{
				searchForBirthRegAuthorization(mGetPropertyFromFile("bnd_authoRegBirthRegCertService"));
				birthRegAuthorization(appAuthorization[0],docAuthorization[0]);
				common.logOut();
				common.finalLogOut();
			}
			else
			{
				searchForBirthRegAuthorization(mGetPropertyFromFile("bnd_authoRegBirthRegCertService"));
				birthRegAuthorization(appAuthorization[0],docAuthorization[0]);
				common.logOut();
				common.finalLogOut();
				common.citizenLogin();
				reSubmit();
				common.logOut();
				common.finalLogOut();
				common.departmentLogin(mGetPropertyFromFile("bndAuthoDeptUserName"),mGetPropertyFromFile("bndAuthoDeptPassword"));
				searchForBirthRegAuthorization(mGetPropertyFromFile("bnd_authoRegBirthRegCertService"));
				birthRegAuthorization(appAuthorization[1],docAuthorization[1]);
				common.logOut();
				common.finalLogOut();
			}
			bnd_docAuthorised=false;
			mCloseBrowser();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in birthRegWithCert_BirthRegAuthorization method");
		}
	}

	@Test(enabled=false)
	public void birthRegWithCert_Resubmit() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			if(mGetPropertyFromFile("bnd_appStatusRejectedDueToInadequateInformation").equalsIgnoreCase("Yes")||mGetPropertyFromFile("bnd_appStatusRejectedDueToInadequateDocuments").equalsIgnoreCase("Yes")||mGetPropertyFromFile("bnd_appStatusRejectedDueToInadequateInformationAndDocuments").equalsIgnoreCase("Yes"))
			{

				thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
				BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
				mCreateArtefactsFolder("BND_");
				mOpenBrowser(wGetPropertyFromFile("browserName"));
				mGetURL(wGetPropertyFromFile("url"));
				common.selectUlb();
				common.citizenLogin();
				reSubmit();
				common.logOut();
				common.finalLogOut();
				mCloseBrowser();
				birthRegWithCert_BirthRegAuthorization();
			}
			else
			{
				System.out.println("Application authorised");
			}
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in birthRegWithCert_Resubmit method");	
		}
	}

	@Test(enabled=false)
	public void birthRegWithCert_BirthCertPrinting() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			bndCertPrinting();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in birthRegWithCert_BirthCertPrinting method");
		}
	}

	///////////////////////////////////////////////////////////////////////

	/////// Death Registration with Certificate ///////////

	@Test(enabled=false)
	public void death_RegistrationWithCertificate() throws InterruptedException
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			deathRegistrationCertificate();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in death_RegistrationWithCertificate method");
		}
	}

	@Test(enabled=false)
	public void deathRegWithCert_ChallanVerf() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			mCreateArtefactsFolder("BND_");
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			common.selectUlb();
			common.isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerDeathRegCertServiceName"));
			mCloseBrowser();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in deathRegWithCert_ChallanVerf method");
		}
	}

	@Test(enabled=false)
	public void deathRegWithCert_DeathRegAuthorization() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			appAuthorization = mGetPropertyFromFile("bnd_isAppnAuthorized").split(",");
			docAuthorization = mGetPropertyFromFile("bnd_isDocAuthorized").split(",");
			mCreateArtefactsFolder("BND_");
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			common.selectUlb();
			common.departmentLogin(mGetPropertyFromFile("bndAuthoDeptUserName"),mGetPropertyFromFile("bndAuthoDeptPassword"));
			if(appAuthorization[0].equalsIgnoreCase("Yes"))
			{
				searchForDeathRegAuthorization(mGetPropertyFromFile("bnd_authoRegDeathRegCertService"));
				deathRegAuthorization(appAuthorization[0],docAuthorization[0]);
				common.logOut();
				common.finalLogOut();
			}
			else
			{
				searchForDeathRegAuthorization(mGetPropertyFromFile("bnd_authoRegDeathRegCertService"));
				deathRegAuthorization(appAuthorization[0],docAuthorization[0]);
				common.logOut();
				common.finalLogOut();
				common.citizenLogin();
				reSubmit();
				common.logOut();
				common.finalLogOut();
				common.departmentLogin(mGetPropertyFromFile("bndAuthoDeptUserName"),mGetPropertyFromFile("bndAuthoDeptPassword"));
				searchForDeathRegAuthorization(mGetPropertyFromFile("bnd_authoRegBirthRegCertService"));
				deathRegAuthorization(appAuthorization[1],docAuthorization[1]);
				common.logOut();
				common.finalLogOut();
			}
			bnd_docAuthorised=false;
			mCloseBrowser();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in deathRegWithCert_DeathRegAuthorization method");
		}
	}

	@Test(enabled=false)
	public void deathRegWithCert_DeathCertPrinting() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			bndCertPrinting();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in deathRegWithCert_DeathCertPrinting method");
		}
	}

	///////////////////////////////////////////////////////////////////////

	/////// Birth Certificate ///////////

	@Test(enabled=false)
	public void birth_Certificate() throws InterruptedException
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			birthCert();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in birth_Certificate method");	
		}
	} 

	@Test(enabled=false)
	public void birthCert_ChallanVerf() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			mCreateArtefactsFolder("BND_");
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			common.selectUlb();
			common.isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerBirthCertServiceName"));
			mCloseBrowser();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in birthCert_ChallanVerf method");	
		}
	}

	//Checklist verification
	//////////////////////////
	@Test(enabled=false)
	public void birthCert_CheckListVerification() throws InterruptedException, IOException
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			if(mGetPropertyFromFile("bnd_chkListVrfnApplicable").equals("Yes"))
			{
				mCreateArtefactsFolder("BND_");
				//beforeBND_ChecklistVerification();
				common.ChecklistVerification(mGetPropertyFromFile("cfc_chcklistverfn_username"), mGetPropertyFromFile("cfc_chcklistverfn_pwd"));
			}
			else
			{
				System.out.println("Checklist Verification is not required");
			}
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in birthCert_CheckListVerification method");	
		}
	}

	@Test(enabled=false)
	public void birthCert_BirthRegAuthorization() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			if(bndBPLRegAutho==true)
			{
				mCreateArtefactsFolder("BND_");
				mOpenBrowser(wGetPropertyFromFile("browserName"));
				mGetURL(wGetPropertyFromFile("url"));
				common.selectUlb();
				common.departmentLogin(mGetPropertyFromFile("bndAuthoDeptUserName"),mGetPropertyFromFile("bndAuthoDeptPassword"));
				searchForBirthRegAuthorization(mGetPropertyFromFile("bnd_authoRegBirthCertService"));
				birthRegAuthorization("","");
				common.logOut();
				common.finalLogOut();
				mCloseBrowser();
			}
			else
			{
				System.out.println("Birth registration authorization is not required");
			}
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in birthCert_BirthRegAuthorization method");		
		}
	}


	@Test(enabled=false)
	public void birthCert_Printing() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			bndCertPrinting();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in birthCert_Printing method");		
		}
	}

	///////////////////////////////////////////////////////////////////////

	/////// Death Certificate ///////////

	@Test(enabled=false)
	public void death_Certificate() throws InterruptedException
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			deathCert();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in death_Certificate method");	
		}
	}

	@Test(enabled=false)
	public void deathCert_ChallanVerf() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			mCreateArtefactsFolder("BND_");
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			common.selectUlb();
			common.isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerDeathCertServiceName"));
			mCloseBrowser();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in deathCert_ChallanVerf method");	
		}
	}

	//Checklist verification
	//////////////////////////
	@Test(enabled=false)
	public void deathCert_CheckListVerification() throws InterruptedException, IOException
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			if(mGetPropertyFromFile("bnd_chkListVrfnApplicable").equals("Yes"))
			{
				mCreateArtefactsFolder("BND_");
				//beforeBND_ChecklistVerification();
				common.ChecklistVerification(mGetPropertyFromFile("cfc_chcklistverfn_username"), mGetPropertyFromFile("cfc_chcklistverfn_pwd"));
			}
			else
			{
				System.out.println("Checklist Verification is not required");
			}
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in deathCert_CheckListVerification method");	
		}
	}

	@Test(enabled=false)
	public void deathCert_DeathRegAuthorization() throws Exception{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			if(bndBPLRegAutho==true)
			{
				mCreateArtefactsFolder("BND_");
				mOpenBrowser(wGetPropertyFromFile("browserName"));
				mGetURL(wGetPropertyFromFile("url"));
				common.selectUlb();
				common.departmentLogin(mGetPropertyFromFile("bndAuthoDeptUserName"),mGetPropertyFromFile("bndAuthoDeptPassword"));
				searchForDeathRegAuthorization(mGetPropertyFromFile("bnd_authoRegDeathCertService"));
				deathRegAuthorization("","");
				common.logOut();
				common.finalLogOut();
				mCloseBrowser();
			}
			else
			{
				System.out.println("Death registration authorization is not required");
			}
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in deathCert_DeathRegAuthorization method");	
		}
	}

	@Test(enabled=false)
	public void deathCert_Printing() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			bndCertPrinting();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in deathCert_Printing method");
		}
	}

	///////////////////////////////////////////////////////////////////////

	/////// Birth Registration Correction and Issue Certificate ///////////

	@Test(enabled=false)
	public void birth_RegCorrNdIsuCert() throws InterruptedException, IOException
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			BirthRegCorrNdIsuCert();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in birth_RegCorrNdIsuCert method");
		}
	}

	@Test(enabled=false)
	public void birthRegCorrNdIsuCert_ChallanVerf() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			mCreateArtefactsFolder("BND_");
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			common.selectUlb();
			common.isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerBirthRegCorrNdIsuCertServiceName"));
			mCloseBrowser();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in birthRegCorrNdIsuCert_ChallanVerf method");
		}
	}

	@Test(enabled=false)
	public void birthRegCorrNdIsuCert_Authorization() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			appAuthorization = mGetPropertyFromFile("bnd_isAppnAuthorized").split(",");
			docAuthorization = mGetPropertyFromFile("bnd_isDocAuthorized").split(",");
			mCreateArtefactsFolder("BND_");
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			common.selectUlb();
			common.departmentLogin(mGetPropertyFromFile("bndAuthoDeptUserName"),mGetPropertyFromFile("bndAuthoDeptPassword"));
			if(appAuthorization[0].equalsIgnoreCase("Yes"))
			{
				searchForBirthRegAuthorization(mGetPropertyFromFile("bnd_birthRegCorrBirthAuthrzn"));
				birthRegAuthorization(appAuthorization[0],docAuthorization[0]);
				common.logOut();
				common.finalLogOut();
			}
			else
			{
				searchForBirthRegAuthorization(mGetPropertyFromFile("bnd_authoRegBirthRegCertService"));
				birthRegAuthorization(appAuthorization[0],docAuthorization[0]);
				common.logOut();
				common.finalLogOut();
				common.citizenLogin();
				reSubmit();
				common.logOut();
				common.finalLogOut();
				common.departmentLogin(mGetPropertyFromFile("bndAuthoDeptUserName"),mGetPropertyFromFile("bndAuthoDeptPassword"));
				searchForBirthRegAuthorization(mGetPropertyFromFile("bnd_birthRegCorrBirthAuthrzn"));
				birthRegAuthorization(appAuthorization[1],docAuthorization[1]);
				common.logOut();
				common.finalLogOut();
			}
			mCloseBrowser();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in birthRegCorrNdIsuCert_Authorization method");
		}
	}

	@Test(enabled=false)
	public void birthRegCorrNdIsuCert_Printing() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			bndCertPrinting();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in birthRegCorrNdIsuCert_Printing method");
		}
	}

	///////////////////////////////////////////////////////////////////////

	/////// Death Registration Correction and Issue Certificate ///////////

	@Test(enabled=false)
	public void death_RegCorrNdIsuCert() throws InterruptedException, IOException{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			DeathRegCorrNdIsuCert();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in death_RegCorrNdIsuCert method");
		}
	}

	@Test(enabled=false)
	public void deathRegCorrNdIsuCert_ChallanVerf() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			mCreateArtefactsFolder("BND_");
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			common.selectUlb();
			common.isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVreDeathRegCorrServiceName"));
			mCloseBrowser();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in deathRegCorrNdIsuCert_ChallanVerf method");
		}
	}

	@Test(enabled=false)
	public void deathRegCorrNdIsuCert_Authorization() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			appAuthorization = mGetPropertyFromFile("bnd_isAppnAuthorized").split(",");
			docAuthorization = mGetPropertyFromFile("bnd_isDocAuthorized").split(",");
			mCreateArtefactsFolder("BND_");
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			common.selectUlb();
			common.departmentLogin(mGetPropertyFromFile("bndAuthoDeptUserName"),mGetPropertyFromFile("bndAuthoDeptPassword"));
			if(appAuthorization[0].equalsIgnoreCase("Yes"))
			{
				searchForDeathRegAuthorization(mGetPropertyFromFile("bnd_DrcnicSearchByServiceid"));
				deathRegcorrNIsuCertAuthorization(appAuthorization[0],docAuthorization[0]);
				common.logOut();
				common.finalLogOut();
			}
			else
			{
				searchForDeathRegAuthorization(mGetPropertyFromFile("bnd_DrcnicSearchByServiceid"));
				deathRegcorrNIsuCertAuthorization(appAuthorization[0],docAuthorization[0]);
				common.logOut();
				common.finalLogOut();
				common.citizenLogin();
				reSubmit();
				common.logOut();
				common.finalLogOut();
				common.departmentLogin(mGetPropertyFromFile("bndAuthoDeptUserName"),mGetPropertyFromFile("bndAuthoDeptPassword"));
				searchForDeathRegAuthorization(mGetPropertyFromFile("bnd_DrcnicSearchByServiceid"));
				deathRegcorrNIsuCertAuthorization(appAuthorization[1],docAuthorization[1]);
				common.logOut();
				common.finalLogOut();
			}
			mCloseBrowser();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in deathRegCorrNdIsuCert_Authorization method");
		}
	}

	@Test(enabled=false)
	public void deathRegCorrNdIsuCert_Printing() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			bndCertPrinting();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in deathRegCorrNdIsuCert_Printing method");
		}
	}

	///////////////////////////////////////////////////////////////////////	

	/////// Inclusion of Child Name ///////////

	@Test(enabled=false)
	public void birth_InclnOfChldName() throws InterruptedException, IOException
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			incOfChld();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in birth_InclnOfChldName method");
		}
	}

	@Test(enabled=false)
	public void inclnOfChldName_ChallanVerf() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			mCreateArtefactsFolder("BND_");
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			common.selectUlb();
			common.isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerincOfChldServiceName"));
			mCloseBrowser();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in inclnOfChldName_ChallanVerf method");
		}
	}

	@Test(enabled=false)
	public void inclnOfChldName_Authorization() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			appAuthorization = mGetPropertyFromFile("bnd_isAppnAuthorized").split(",");
			docAuthorization = mGetPropertyFromFile("bnd_isDocAuthorized").split(",");
			mCreateArtefactsFolder("BND_");
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			common.selectUlb();
			common.departmentLogin(mGetPropertyFromFile("bndAuthoDeptUserName"),mGetPropertyFromFile("bndAuthoDeptPassword"));
			if(appAuthorization[0].equalsIgnoreCase("Yes"))
			{
				searchForBirthRegAuthorization(mGetPropertyFromFile("bnd_IncOfChldNmeAutherzid"));
				incOfChldNmeAuthorization(appAuthorization[0],docAuthorization[0]);
				common.logOut();
				common.finalLogOut();
			}
			else
			{
				searchForBirthRegAuthorization(mGetPropertyFromFile("bnd_IncOfChldNmeAutherzid"));
				birthRegAuthorization(appAuthorization[0],docAuthorization[0]);
				common.logOut();
				common.finalLogOut();
				common.citizenLogin();
				reSubmit();
				common.logOut();
				common.finalLogOut();
				common.departmentLogin(mGetPropertyFromFile("bndAuthoDeptUserName"),mGetPropertyFromFile("bndAuthoDeptPassword"));
				searchForBirthRegAuthorization(mGetPropertyFromFile("bnd_IncOfChldNmeAutherzid"));
				incOfChldNmeAuthorization(appAuthorization[1],docAuthorization[1]);
				common.logOut();
				common.finalLogOut();
			}
			mCloseBrowser();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in inclnOfChldName_Authorization method");
		}
	}

	@Test(enabled=false)
	public void inclnOfChldName_CertPrinting() throws Exception
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			bndCertPrinting();
			CommonUtilsAPI.bndRegErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in inclnOfChldName_CertPrinting method");
		}
	}

	///////////////////////////////////////////////////////////////////////

	/////// Still Birth Registration ///////////

	@Test(enabled=false)
	public void birth_StillBirthRegistration() throws InterruptedException, IOException{

		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
		stillBirthRegstn();
		CommonUtilsAPI.bndRegErrorMsg.assertAll();
	}

	@Test(enabled=false)
	public void stillBirthReg_Authorization() throws Exception
	{
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
		mCreateArtefactsFolder("BND_");
		mOpenBrowser(wGetPropertyFromFile("browserName"));
		mGetURL(wGetPropertyFromFile("url"));
		common.selectUlb();
		common.departmentLogin(mGetPropertyFromFile("bndAuthoDeptUserName"),mGetPropertyFromFile("bndAuthoDeptPassword"));
		searchForBirthRegAuthorization(mGetPropertyFromFile("bnd_stlBirthRegAutherznid"));
		birthRegAuthorization("","");
		common.logOut();
		common.finalLogOut();
		mCloseBrowser();
		CommonUtilsAPI.bndRegErrorMsg.assertAll();
	}

	@Test(enabled=false)
	public void stillBirthReg_CertPrinting() throws Exception
	{
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
		bndCertPrinting();
		CommonUtilsAPI.bndRegErrorMsg.assertAll();
	}

	///////////////////////////////////////////////////////////////////////

	/////// Birth Registration///////////

	@Test(enabled=false)
	public void birth_Registration() throws Exception
	{
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
		birthRegn();
		CommonUtilsAPI.bndRegErrorMsg.assertAll();
	}

	@Test(enabled=false)
	public void birthReg_ChallanVerf() throws Exception
	{
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
		mCreateArtefactsFolder("BND_");
		mOpenBrowser(wGetPropertyFromFile("browserName"));
		mGetURL(wGetPropertyFromFile("url"));
		common.selectUlb();
		common.isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerBirthRegServiceName"));
		mCloseBrowser();
		CommonUtilsAPI.bndRegErrorMsg.assertAll();
	}

	@Test(enabled=false)
	public void birthReg_Authorization() throws Exception
	{
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
		mCreateArtefactsFolder("BND_");
		mOpenBrowser(wGetPropertyFromFile("browserName"));
		mGetURL(wGetPropertyFromFile("url"));
		common.selectUlb();
		common.departmentLogin(mGetPropertyFromFile("bndAuthoDeptUserName"),mGetPropertyFromFile("bndAuthoDeptPassword"));
		searchForBirthRegAuthorization(mGetPropertyFromFile("bnd_authoRegBirthRegService"));
		birthRegAuthorization("","");
		common.logOut();
		common.finalLogOut();
		mCloseBrowser();
		CommonUtilsAPI.bndRegErrorMsg.assertAll();
	}

	///////////////////////////////////////////////////////////////////////	

	/////// Death Registration///////////

	@Test(enabled=false)
	public void death_Registration() throws Exception
	{
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
		deathRegn();
		CommonUtilsAPI.bndRegErrorMsg.assertAll();
	}

	@Test(enabled=false)
	public void deathReg_ChallanVerf() throws Exception
	{
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
		mCreateArtefactsFolder("BND_");
		mOpenBrowser(wGetPropertyFromFile("browserName"));
		mGetURL(wGetPropertyFromFile("url"));
		common.selectUlb();
		common.isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerDeathRegServiceName"));
		mCloseBrowser();
		CommonUtilsAPI.bndRegErrorMsg.assertAll();
	}

	@Test(enabled=false)
	public void deathReg_Authorization() throws Exception
	{
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
		mCreateArtefactsFolder("BND_");
		mOpenBrowser(wGetPropertyFromFile("browserName"));
		mGetURL(wGetPropertyFromFile("url"));
		common.selectUlb();
		common.departmentLogin(mGetPropertyFromFile("bndAuthoDeptUserName"),mGetPropertyFromFile("bndAuthoDeptPassword"));
		searchForDeathRegAuthorization(mGetPropertyFromFile("bnd_authoRegDeathRegService"));
		deathRegAuthorization("","");
		common.logOut();
		common.finalLogOut();
		mCloseBrowser();
		CommonUtilsAPI.bndRegErrorMsg.assertAll();
	}

	///////////////////////////////////////////////////////////////////////	


	//Ritesh
	//14-01-2016
	//Date comparison test

	//@Test(priority=1)
	public void dateCheckerTest()
	{
		/*excelToProp.convertExcelToProperties(mGetPropertyFromFile("CommonData"),mGetPropertyFromFile("CommonData"),mGetPropertyFromFile("excelColumn2"));
		mReadFromInputStream(mGetPropertyFromFile("CommonData_Properties_Path"));
		String challanDate= mGetPropertyFromFile("rti_chlanVerftnDate");
		System.out.println("#challanDate: " + challanDate);*/

		DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		Calendar calender = Calendar.getInstance();
		System.out.println("#getting time : " + dateFormat.format(calender.getTime()));
		String sytemDate = dateFormat.format(calender.getTime());
		System.out.println("#getting time dt : " + sytemDate);

		if (sytemDate.equals("15/Jan/2016"))
		{
			System.out.println("Both date are not matched");

			excelToProp.convertExcelToProperties(mGetPropertyFromFile("CommonData"),mGetPropertyFromFile("CommonData"),mGetPropertyFromFile("excelColumn2"));
			mReadFromInputStream(mGetPropertyFromFile("CommonData_Properties_Path"));
			String challanDate= mGetPropertyFromFile("rti_chlanVerftnDate");
			System.out.println("#challanDate: " + challanDate);


		}
		else //if (sytemDate.equals("15/Jan/2016"))
		{
			System.out.println("Both date are matched");

			excelToProp.convertExcelToProperties(mGetPropertyFromFile("CommonData"),mGetPropertyFromFile("CommonData"),mGetPropertyFromFile("excelColumn"));
			mReadFromInputStream(mGetPropertyFromFile("CommonData_Properties_Path"));
			String challanDate= mGetPropertyFromFile("rti_chlanVerftnDate");
			System.out.println("#challanDate: " + challanDate);


		}
	}


	/*@Test(priority=1)
	public void dateCheckerTest()
	{
		excelToProp.convertExcelToProperties(mGetPropertyFromFile("CommonData"),mGetPropertyFromFile("CommonData"),mGetPropertyFromFile("excelColumn2"));
		mReadFromInputStream(mGetPropertyFromFile("CommonData_Properties_Path"));
		String challanDate= mGetPropertyFromFile("rti_chlanVerftnDate");
		System.out.println("#challanDate: " + challanDate);

		DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		Calendar calender = Calendar.getInstance();
		System.out.println("#getting time : " + dateFormat.format(calender.getTime()));
		String sytemDate = dateFormat.format(calender.getTime());
		System.out.println("#getting time dt : " + sytemDate);

		if (sytemDate.equals(challanDate))
		{
			//excelToProp.convertExcelToProperties(mGetPropertyFromFile("CommonData"),mGetPropertyFromFile("CommonData"),mGetPropertyFromFile("excelColumn2"));
			System.out.println("Both date are matched");

		}
		else
		{
			//excelToProp.convertExcelToProperties(mGetPropertyFromFile("CommonData"),mGetPropertyFromFile("CommonData"),mGetPropertyFromFile("excelColumn"));
			System.out.println("Both date are different");			
		}
	}*/


	public void birthRegistrationCertificate()
	{
		try
		{
			//beforeBirthRegCertTest();
			mCreateArtefactsFolder("BND_");
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			//LOIAPPLICABLE=true;
			//chllanForBNDServices=true;
			common.selectUlb();
			common.login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			birthRegCertificate();
			if(!mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Online"))
			{
				birthRegCertProceed();
			}
			common.logOut();
			common.finalLogOut();
			common.isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerBirthRegCertServiceName"));
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in birthRegistrationCertificate method");

		}
	}

	public void bndCertPrinting()
	{
		try
		{
			mCreateArtefactsFolder("BND_");
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			common.selectUlb();
			common.departmentLogin(mGetPropertyFromFile("printerUserName"),mGetPropertyFromFile("printerPassword"));
			common.cfcPrinterGrid();
			common.logOut();
			common.finalLogOut();
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in bndCertPrinting method");
		}
	}

	public void deathRegistrationCertificate() throws InterruptedException
	{
		try
		{
			//mCreateModuleFolder("BND_",myClassName);
			mCreateArtefactsFolder("BND_");
			//beforeDeathRegCertTest();
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			//LOIAPPLICABLE=true;
			//chllanForBNDServices=true;
			common.selectUlb();
			//common.citizenLogin();
			common.login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			deathRegCertificate();
			if(!mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Online"))
			{
				deathRegCertProceed();
			}
			common.logOut();
			common.finalLogOut();
			common.isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerDeathRegCertServiceName"));
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in deathRegistrationCertificate method");
		}
	}

	public void birthRegn() throws InterruptedException{
		//myClassName = thisClassName;
		myClassName = thisClassName;
		//mCreateModuleFolder("BND_",myClassName);
		mCreateArtefactsFolder("BND_");
		mOpenBrowser(wGetPropertyFromFile("brwoserName"));
		mGetURL(wGetPropertyFromFile("url"));
		LOIAPPLICABLE=true;
		chllanForBNDServices=true;
		common.selectUlb();
		common.citizenLogin();
		birthRegistration();
		birthRegProceed();
		common.logOut();
		common.finalLogOut();		
		mCloseBrowser();
	}

	public void deathRegn() throws InterruptedException{
		myClassName = thisClassName;
		//mCreateModuleFolder("BND_",myClassName);
		mCreateArtefactsFolder("BND_");
		mOpenBrowser(wGetPropertyFromFile("brwoserName"));
		mGetURL(wGetPropertyFromFile("url"));
		LOIAPPLICABLE=true;
		chllanForBNDServices=true;
		common.selectUlb();
		common.citizenLogin();
		deathRegistration();
		deathRegProceed();
		common.logOut();
		common.finalLogOut();
		mCloseBrowser();
	}


	public void birthCert() throws InterruptedException{
		try
		{
			myClassName = thisClassName;
			//mCreateModuleFolder("BND_",myClassName);
			mCreateArtefactsFolder("BND_");
			//beforeBirthCertTest();
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			//LOIAPPLICABLE=true;
			//chllanForBNDServices=true;
			common.selectUlb();
			//common.citizenLogin();
			common.login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			birthCertificate();
			if(!mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Online"))
			{
				birthCertProceed();
			}
			common.logOut();
			common.finalLogOut();
			common.isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerBirthCertServiceName"));
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in birthCert method");
		}
	}


	public void deathCert() throws InterruptedException{
		try
		{
			myClassName = thisClassName;
			//mCreateModuleFolder("BND_",myClassName);
			mCreateArtefactsFolder("BND_");
			//beforeDeathCertTest();
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			//LOIAPPLICABLE=true;
			//chllanForBNDServices=true;
			common.selectUlb();
			//common.citizenLogin();
			common.login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			deathCertificate();
			if(!mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Online"))
			{
				deathCertProcced();
			}
			common.logOut();
			common.finalLogOut();
			common.isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerDeathCertServiceName"));
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in deathCert method");
		}
	}


	public void stillBirthRegstn() throws InterruptedException, IOException
	{
		//mCreateModuleFolder("BND_",myClassName);
		mCreateArtefactsFolder("BND_");
		LOIAPPLICABLE=true;
		chllanForBNDServices=true;
		stillBirthReg=true;		
		//before_BND_stillBirthRegstn();
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		common.selectUlb();
		common.citizenLogin();
		birthRegistration();
		stillBirthPrcd();
		common.logOut();
		common.finalLogOut();
		stillBirthReg=false;
		mCloseBrowser();
	}



	public void incOfChld() throws InterruptedException, IOException
	{
		try
		{
			myClassName = thisClassName;
			//mCreateModuleFolder("BND_",myClassName);
			mCreateArtefactsFolder("BND_");
			//beforeInclusionOfChildNmTest();
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			//LOIAPPLICABLE=true;
			//chllanForBNDServices=true;
			common.selectUlb();
			//common.citizenLogin();
			common.login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			IncOfChildName();
			common.logOut(); 
			common.finalLogOut();
			common.isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerincOfChldServiceName"));
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in incOfChld method");
		}
	}


	public void BirthRegCorrNdIsuCert() throws InterruptedException, IOException{
		try
		{
			myClassName = thisClassName;
			//mCreateModuleFolder("BND_",myClassName);
			mCreateArtefactsFolder("BND_");
			//beforeBirthRegCorrNCertTest();
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			//chllanForBNDServices=true;
			common.selectUlb();
			//common.citizenLogin();
			common.login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			//LOIAPPLICABLE=true;
			birthRegCorrNdIsuCert();
			common.logOut(); 
			common.finalLogOut();
			common.isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerBirthRegCorrNdIsuCertServiceName"));
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in BirthRegCorrNdIsuCert method");
		}
	}

	//Death registration and issue certificate method
	public void DeathRegCorrNdIsuCert() throws InterruptedException, IOException{
		try
		{
			//mCreateModuleFolder("BND_",myClassName);
			mCreateArtefactsFolder("BND_");
			//beforeDeathRegCorrNIsuCertTest();
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			//LOIAPPLICABLE=true;
			//chllanForBNDServices=true;
			common.selectUlb();
			//common.citizenLogin();
			common.login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			deathRegCorrNdIsuCert();
			common.logOut(); 
			common.finalLogOut();
			common.isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVreDeathRegCorrServiceName"));
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in DeathRegCorrNdIsuCert method");
		}
	}


	/*@Test(priority=1)
	public void AdoptionOfChild() throws InterruptedException{
		myClassName = thisClassName;
		mCreateModuleFolder("BND_","com.abm.mainet.bnd.BirthAndDeathServices");
		mCreateArtefactsFolder("BND_");
		mOpenBrowser(wGetPropertyFromFile("brwoserName"));
		mGetURL(wGetPropertyFromFile("url"));
		chllanForBNDServices=true;
		common.selectUlb();
		common.citizenLogin();

		common.logOut();
		common.finalLogOut();
		common.isChallanVerftnRequired("cfc_challanVerDeathRegServiceName");
		common.departmentLogin("deptUserName","deptPassword" );
		searchForDeathRegAuthorization("bnd_authoRegDeathRegService");
		deathRegAuthorization();
		common.logOut();
		common.finalLogOut();
		mCloseBrowser();

	}*/

	//Birth Registation Correction and Issue Certificate
	public void birthRegCorrNdIsuCert() throws IOException
	{
		try
		{
			// Wait for visible links

			/*mWaitForVisible("linkText",mGetPropertyFromFile("bnd_BNDLinkid"));
		mClick("linkText", mGetPropertyFromFile("bnd_BNDLinkid"));

		// Wait for visible links
		mWaitForVisible("linkText",mGetPropertyFromFile("bnd_birthRegCorrNdIsueCertLinkid"));
		mClick("linkText", mGetPropertyFromFile("bnd_birthRegCorrNdIsueCertLinkid"));*/

			mNavigation("bnd_BNDLinkid", "bnd_birthRegCorrNdIsueCertLinkid");

			mWaitForVisible("xpath", mGetPropertyFromFile("bnd_BrcnicSearchBtnid"));
			//sending application number
			//mSendKeys("id",mGetPropertyFromFile("bnd_BrcnicsearchByAppNoid"), BirthAndDeathServices.appNo);
			//mSendKeys("id",mGetPropertyFromFile("bnd_BrcnicsearchByAppNoid"), "57015112500003");

			//sending registration number
			//mSendKeys("id", mGetPropertyFromFile("bnd_BrcnicSearchByRegid"), mGetPropertyFromFile("bnd_BrcnicSearchByReg"));
			mGenericWait();

			searchByRegNoRegAppnNoCertNoYear(mGetPropertyFromFile("bnd_BrcnicRegNoid"),mGetPropertyFromFile("bnd_BrcnicCertNoid"));

			//sending year 
			//mSendKeys("id", mGetPropertyFromFile("bnd_BrcnicSearchByYrid"), "2015");
			mGenericWait();

			//clicking on search button
			mClick("xpath", mGetPropertyFromFile("bnd_BrcnicSearchBtnid"));
			mGenericWait();
			mTakeScreenShot();
			mWaitForVisible("xpath",mGetPropertyFromFile("bnd_BrcnicSearchByImg"));
			mClick("xpath",mGetPropertyFromFile("bnd_BrcnicSearchByImg"));

			mGenericWait();
			mTakeScreenShot();


			// change in date
			changeInDate("bnd_BrcnicchangeDate");
			chngeGender("bnd_BrcnicChngeSexData");
			chngeBirthPlace("bnd_BrcnicCngeInBirthPlace");
			mTakeScreenShot();
			mscroll(0,400);

			chngefatherName("bnd_BrcnicChngeFathrNmeData");
			chngemotherName("bnd_BrcnicChngeMothrNmeData");
			chngeParentAddess("bnd_BrcnicChngeAddData");
			mTakeScreenShot();
			mscroll(400, 600);

			birthDate = driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegDOBid"))).getAttribute("value");
			birthDatestr = birthDate.replaceAll("/", "");
			System.out.println("DOB is : "+birthDatestr);

			//	birthGender = driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegGenderid"))).getAttribute("value");
			birthGender = mCaptureSelectedValue("id", mGetPropertyFromFile("bnd_birthRegGenderid"));
			System.out.println("Gender is : "+birthGender);
			
			birthRegChildName=driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegChildNameEngid"))).getAttribute("value");
			System.out.println("Child name is : "+birthRegChildName);

			birthPlaceType = driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegBirthPlaceTypeid"))).getAttribute("value");
			System.out.println("Birth Place Type is : "+birthPlaceType);

			birthRegFatherName = driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegFatherNmEngid"))).getAttribute("value");
			System.out.println("Entered father name is :"+birthRegFatherName);

			birthRegMotherName = driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegMotherNmEngid"))).getAttribute("value");
			System.out.println("Entered mother name is :"+birthRegMotherName);

			parentAddrAtBirth = driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegParentAddrsid"))).getAttribute("value");
			System.out.println("Address of parent at the time of Birth is :"+parentAddrAtBirth);

			// BPL Details

			if(mGetPropertyFromFile("bnd_bplApplicable").equals("Yes"))
			{
				if (driver.findElements(By.id(mGetPropertyFromFile("bnd_bplYesNoid"))).size() != 0)
				{
					if(mGetPropertyFromFile("bplFlag").equals("Yes"))
					{
						mSelectDropDown("id", mGetPropertyFromFile("bnd_bplYesNoid"), mGetPropertyFromFile("bnd_bplYes"));
						mGenericWait();
						mSendKeys("id", mGetPropertyFromFile("bnd_bplNumid"), mGetPropertyFromFile("bnd_bplNum"));
					}
					else
					{
						mSelectDropDown("id", mGetPropertyFromFile("bnd_bplYesNoid"), mGetPropertyFromFile("bnd_bplNo"));
					}
				}
				else
				{
					System.out.println("BPL is not applicable");
				}
			}
			else
			{
				System.out.println("BPL is not configured");
			}

			mSelectDropDown("id", mGetPropertyFromFile("bnd_BrcnicTitleid"), mGetPropertyFromFile("bnd_BrcnicTitleData"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_BrcnicFirstNmeid"), mGetPropertyFromFile("bnd_BrcnicFirstNmeData"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_BrcnicMidleNmeid"), mGetPropertyFromFile("bnd_BrcnicMidleNmeData"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_BrcniclastNmeid"), mGetPropertyFromFile("bnd_BrcniclastNmeData"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_BrcnicmobileNoid"),mGetPropertyFromFile("bnd_BrcnicMobileNo"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_BrcnicOwnerEmailid"), mGetPropertyFromFile("bnd_BrcnicEmailid"));

			mTakeScreenShot();
			mscroll(600,850);

			mSelectDropDown("id",mGetPropertyFromFile("bnd_BrcnicpostalModeid"),mGetPropertyFromFile("bnd_BrcnicpostalModeData"));
			//common.uploaddocument();

			docUpload("className",mGetPropertyFromFile("bnd_birthRegCorrUpldDocTableid"),"uploadDocFlag");

			mSendKeys("id", mGetPropertyFromFile("bnd_BrcnicnoOfCopiesid"), mGetPropertyFromFile("bnd_BrcnicnoOfCopiesdata"));
			//mTab("id", mGetPropertyFromFile("bnd_BrcnicnoOfCopiesid"));
			Robot robot=new Robot();

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			//  isChallanVerificationRequired() method to be changed after no of already issued copies solved
			//i.e noOfCopy =  mGetPropertyFromFile("bnd_BrcnicnoOfCopiesdata")+ already issued Copies
			noOfCopyForBNDCombo = Integer.parseInt((mGetPropertyFromFile("bnd_BrcnicnoOfCopiesdata")));

			/*if(mGetPropertyFromFile("bnd_BrcnicpostalModeData").equals("By Post/Courier"))
			{	
				common.payment("paymentMode","byBankOrULB");
			}*/

			common.payment("paymentMode","byBankOrULB");

			mTakeScreenShot();
			if(!mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Online"))
			{
				//submit button
				mWaitForVisible("xpath", mGetPropertyFromFile("bnd_BrcnicSubmitBtnid"));
				mClick("xpath",mGetPropertyFromFile("bnd_BrcnicSubmitBtnid"));



				mCustomWait(5000);
				String appNoAftrBirthCorr = mGetText("xpath",mGetPropertyFromFile("bnd_BrcnicStordNoAftrCorrid"));
				//	appNo = appNoAftrBirthCorr;
				mAppNoArray("xpath",mGetPropertyFromFile("bnd_BrcnicStordNoAftrCorrid"));

				System.out.println("number after :"+appNoAftrBirthCorr);

				String msgAftrBirthCorr = mGetText("xpath", mGetPropertyFromFile("bnd_BrcnicStordMsgAftrCorrid"));
				System.out.println(msgAftrBirthCorr);

				String delimiter = appNoAftrBirthCorr;
				String[] LOIMsg = msgAftrBirthCorr.split(delimiter);
				System.out.println("msg after birth correction :"+LOIMsg[1].trim());

				//assertion for payment and without payment pop-up
				if(mGetPropertyFromFile("bnd_BrcnicpostalModeData").equals("By Post/Courier"))
				{
					mAssert(LOIMsg[1].trim(), mGetPropertyFromFile("bnd_BrcnicMsgAftrWidPayBirthCorr"), "Actual Msg:"+LOIMsg[1].trim()+"  Expected Msg :"+mGetPropertyFromFile("bnd_BrcnicMsgAftrWidPayBirthCorr"));
				}
				else
				{
					mAssert(LOIMsg[1].trim(), mGetPropertyFromFile("bnd_BrcnicMsgAftrWidOutPaybirthCorr"), "Actual Msg:"+LOIMsg[1].trim()+"  Expected Msg :"+mGetPropertyFromFile("bnd_BrcnicMsgAftrWidOutPaybirthCorr"));

				}
				mTakeScreenShot();
				//proceed button
				mWaitForVisible("id", mGetPropertyFromFile("bnd_BrcnicPrcodBtnid"));
				mClick("id", mGetPropertyFromFile("bnd_BrcnicPrcodBtnid"));

				/*if(mGetPropertyFromFile("bnd_BrcnicpostalModeData").equals("By Post/Courier"))
				{*/
				mCustomWait(6000);
				//mSwitchTabs();
				if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
				{
					newChallanReader();
				}
				else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
				{
					mChallanPDFReader();
				}
				//}

				/*if(mGetPropertyFromFile("bnd_BrcnicpostalModeData").equals("Collect at Office"))
				{
					mGenericWait();
					mClick("xpath", mGetPropertyFromFile("bnd_BrcnicFinishBtnid"));
				}*/
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in birthRegCorrNdIsuCert method");
		}
	}






	//change in date at correction
	public void changeInDate(String date)
	{
		try
		{
			if((mGetPropertyFromFile(date).length()==0)||mGetPropertyFromFile(date).equals("?"))
			{
				System.out.println("No Change in date");
			}
			else
			{

				/*String BrcnicDate[] =mGetPropertyFromFile(date).split("/");
				String strtempdate= BrcnicDate[0];
				int cuDate=Integer.parseInt(strtempdate);
				String strnDate=Integer.toString(cuDate);
				//entity.challanRcvdDate
				System.out.println(BrcnicDate[2]);
				System.out.println(BrcnicDate[1]);
				System.out.println(strnDate);
				mGdatePicker(mGetPropertyFromFile("bnd_BrcnicChngeDateid"), BrcnicDate[2],BrcnicDate[1],strnDate);*/
				mDateSelect("id", mGetPropertyFromFile("bnd_BrcnicChngeDateid"), mGetPropertyFromFile(date));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in changeInDate method");
		}
	}

	//change in birth place
	public void chngeBirthPlace(String homeOrHos)
	{
		try
		{
			if(mGetPropertyFromFile(homeOrHos).length()==0||mGetPropertyFromFile(homeOrHos).equals("?"))
			{
				System.out.println("No change in birth place");
			}
			else
			{
				mSelectDropDown("id",mGetPropertyFromFile("bnd_BrcnicPlaceChngeid"),mGetPropertyFromFile(homeOrHos));
				mGenericWait();
				if(mGetPropertyFromFile(homeOrHos).equals("Hospital")||mGetPropertyFromFile(homeOrHos).equals("Hospital/Institute"))
				{
					mSelectDropDown("id",mGetPropertyFromFile("bnd_BrcnicHosLstid"),mGetPropertyFromFile("bnd_BrcnicHosName"));
					mGenericWait();
				}
				else
				{
					//mSelectDropDown("id", mGetPropertyFromFile("bnd_BrcnicPlaceChngeid"), mGetPropertyFromFile("bnd_BrcnicCngeBpHome"));
					mSendKeys("id",mGetPropertyFromFile("bnd_BrcnicBrthPlceid"),"");
					mHindiTextConversion("id",mGetPropertyFromFile("bnd_BrcnicBrthPlceid"),mGetPropertyFromFile("bnd_BrcnicBrthPlceData"));
					//mSendKeys("id",mGetPropertyFromFile("bnd_BrcnicBrthPlceid"),mGetPropertyFromFile("bnd_BrcnicBrthPlceData"));
					mGenericWait();
					//mSendKeys("id",mGetPropertyFromFile("bnd_BrcnicBirthAddid"),mGetPropertyFromFile("bnd_BrcnicBirthAddData"));
					mSendKeys("id",mGetPropertyFromFile("bnd_BrcnicBirthAddid"),"");
					mHindiTextConversion("id",mGetPropertyFromFile("bnd_BrcnicBirthAddid"),mGetPropertyFromFile("bnd_BrcnicBirthAddData"));
					mGenericWait();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in chngeBirthPlace method");
		}
	}

	//change in gender
	public void chngeGender(String gen)
	{
		try
		{
			if(mGetPropertyFromFile(gen).length()==0||mGetPropertyFromFile(gen).equals("?"))
			{
				System.out.println("No change in gender");
			}
			else
			{
				mSelectDropDown("id", mGetPropertyFromFile("bnd_BrcnicChngeSexid"), mGetPropertyFromFile(gen));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in chngeGender method");
		}
	}

	//change in father name
	public void chngefatherName(String fatherName)
	{
		try
		{
			if(mGetPropertyFromFile(fatherName).length()==0||mGetPropertyFromFile(fatherName).equals("?"))
			{
				System.out.println("no change in father name");
			}else
			{
				mSendKeys("id",mGetPropertyFromFile("bnd_BrcnicChngeFathrNmeid"), "");
				mHindiTextConversion("id",mGetPropertyFromFile("bnd_BrcnicChngeFathrNmeid"), mGetPropertyFromFile(fatherName));
				//mSendKeys("id",mGetPropertyFromFile("bnd_BrcnicChngeFathrNmeid"), mGetPropertyFromFile(fatherName));
				mGenericWait();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in chngefatherName method");
		}
	}


	//change in mother name
	public void chngemotherName(String motherName)
	{
		try
		{
			if(mGetPropertyFromFile(motherName).length()==0||mGetPropertyFromFile(motherName).equals("?"))
			{
				System.out.println("no change in father name");
			}else
			{
				mSendKeys("id",mGetPropertyFromFile("bnd_BrcnicChngeMothrNmeid"),"");
				mHindiTextConversion("id",mGetPropertyFromFile("bnd_BrcnicChngeMothrNmeid"), mGetPropertyFromFile(motherName));
				//mSendKeys("id",mGetPropertyFromFile("bnd_BrcnicChngeMothrNmeid"), mGetPropertyFromFile(motherName));
				mGenericWait();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in chngemotherName method");
		}
	}

	//change in parent address
	public void chngeParentAddess(String address)
	{
		try
		{
			if(mGetPropertyFromFile(address).length()==0||mGetPropertyFromFile(address).equals("?"))
			{
				System.out.println("no change in parent address");
			}
			else
			{
				mSendKeys("id",mGetPropertyFromFile("bnd_BrcnicChngeAddid"),"");
				mHindiTextConversion("id",mGetPropertyFromFile("bnd_BrcnicChngeAddid"), mGetPropertyFromFile("bnd_BrcnicChngeAddData"));
				//mSendKeys("id",mGetPropertyFromFile("bnd_BrcnicChngeAddid"), mGetPropertyFromFile("bnd_BrcnicChngeAddData"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in chngeParentAddess method");
		}
	}



	//Death Registation Correction and Issue Certificate
	public void deathRegCorrNdIsuCert() throws IOException
	{
		try
		{
			// Wait for visible links

			/*mWaitForVisible("linkText",mGetPropertyFromFile("bnd_BNDLinkid"));
		mClick("linkText", mGetPropertyFromFile("bnd_BNDLinkid"));

		// Wait for visible links
		mWaitForVisible("linkText",mGetPropertyFromFile("bnd_DeathRegCorrNIssuCertLinkid"));
		mClick("linkText", mGetPropertyFromFile("bnd_DeathRegCorrNIssuCertLinkid"));*/

			mNavigation("bnd_BNDLinkid", "bnd_DeathRegCorrNIssuCertLinkid");

			//sending application number
			//mSendKeys("id",mGetPropertyFromFile("bnd_DeathRegCorrNIssuCertFindByAppNoid"), BirthAndDeathServices.appNo);
			//mWaitForVisible("id", mGetPropertyFromFile("bnd_DeathRegCorrNIssuCertFindByAppNoid"));
			mGenericWait();
			//mSendKeys("id",mGetPropertyFromFile("bnd_DeathRegCorrNIssuCertFindByAppNoid"), appNo);
			//mSendKeys("id",mGetPropertyFromFile("bnd_DeathRegCorrNIssuCertFindByAppNoid"), "51516021500001");

			//sending registration number
			/*mSendKeys("id", mGetPropertyFromFile("bnd_DeathRegCorrNIssuCertFindByRegid"), mGetPropertyFromFile("bnd_DeathRegCorrNIssuCertFindByReg"));*/
			searchByRegNoRegAppnNoCertNoYear(mGetPropertyFromFile("bnd_DeathRegCorrNIssuCertFindByRegid"),mGetPropertyFromFile("bnd_DeathRegCorrNIssuCertFindByCertid"));
			mGenericWait();

			//sending year 
			//mSendKeys("id", mGetPropertyFromFile("bnd_DeathRegCorrNIssuCertFindByYrid"), "2015");
			mGenericWait();

			//clicking on search button
			mWaitForVisible("css", mGetPropertyFromFile("bnd_DrcnicSearchAppBtnid"));
			mClick("css", mGetPropertyFromFile("bnd_DrcnicSearchAppBtnid"));

			//clicking on image after finding
			mWaitForVisible("xpath",mGetPropertyFromFile("bnd_DrcnicSearchAppByImgid"));
			mGenericWait();
			mTakeScreenShot();
			mClick("xpath",mGetPropertyFromFile("bnd_DrcnicSearchAppByImgid"));

			mGenericWait();
			mTakeScreenShot();

			// change in date
			DeathchangeInDate("bnd_DrcnicchangeDate");
			mGenericWait();

			//common.uploaddocument();

			//change in gender
			deathChngeGender("bnd_DrcnicChngeSexData");
			mGenericWait();
			//change in deceased name
			changeDeceasedName("bnd_DrcnicChngeDeceasdNmeData");
			mGenericWait();
			//change in father name
			changeFatherNme("bnd_DrcnicChngeFatherNmeData");
			mGenericWait();
			//change in mother name
			changeMotherNme("bnd_DrcnicChngeMotherNmeData");
			mGenericWait();
			//change in death place type
			deathChngeBirthPlace("bnd_DrcnicCngeInBirthPlace");
			mGenericWait();
			//change in place of death
			changedDeathPlace("bnd_DrcnicAddAtDeathData");
			mGenericWait();

			mTakeScreenShot();
			mscroll(0,400);
			mTakeScreenShot();
			mscroll(400, 600);

			deathCorrDeceasedNme=driver.findElement(By.id(mGetPropertyFromFile("bnd_DrcnicChngeDeceasdNmeid"))).getAttribute("value");
			System.out.println("Deceased Name is : "+deathCorrDeceasedNme);
			
			deathCorrFatherNme=driver.findElement(By.id(mGetPropertyFromFile("bnd_DrcnicChngeFatherNmeid"))).getAttribute("value");
			System.out.println("Deceased's Husband/ Father Name is : "+deathCorrDeceasedNme);
			
			deathCorrMotherNme=driver.findElement(By.id(mGetPropertyFromFile("bnd_DrcnicChngeMotherNmeid"))).getAttribute("value");
			System.out.println("Deceased's Mother Name is : "+deathCorrDeceasedNme);
			
			// BPL Details

			if(mGetPropertyFromFile("bnd_bplApplicable").equals("Yes"))
			{
				if (driver.findElements(By.id(mGetPropertyFromFile("bnd_bplYesNoid"))).size() != 0)
				{
					if(mGetPropertyFromFile("bplFlag").equals("Yes"))
					{
						mSelectDropDown("id", mGetPropertyFromFile("bnd_bplYesNoid"), mGetPropertyFromFile("bnd_bplYes"));
						mGenericWait();
						mSendKeys("id", mGetPropertyFromFile("bnd_bplNumid"), mGetPropertyFromFile("bnd_bplNum"));
					}
					else
					{
						mSelectDropDown("id", mGetPropertyFromFile("bnd_bplYesNoid"), mGetPropertyFromFile("bnd_bplNo"));
					}
				}
				else
				{
					System.out.println("BPL is not applicable");
				}
			}
			else
			{
				System.out.println("BPL is not configured");
			}

			mSelectDropDown("id", mGetPropertyFromFile("bnd_DrcnicTitleid"), mGetPropertyFromFile("bnd_DrcnicTitleData"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_DrcnicFirstNmeid"), mGetPropertyFromFile("bnd_DrcnicFirstNmeData"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_DrcnicMidleNmeid"), mGetPropertyFromFile("bnd_DrcnicMidleNmeData"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_DrcniclastNmeid"), mGetPropertyFromFile("bnd_DrcniclastNmeData"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_DrcnicmobileNoid"),mGetPropertyFromFile("bnd_DrcnicMobileNo"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_DrcnicOwnerEmailid"), mGetPropertyFromFile("bnd_DrcnicEmailid"));

			mTakeScreenShot();
			mscroll(600,850);

			mSelectDropDown("id",mGetPropertyFromFile("bnd_DrcnicpostalModeid"),mGetPropertyFromFile("bnd_DrcnicpostalModeData"));
			//uploading documents
			//common.uploaddocument();
			docUpload("xpath",mGetPropertyFromFile("bnd_deathRegCorrUpldDocTableid"),"uploadDocFlag");

			mSendKeys("id", mGetPropertyFromFile("bnd_DrcnicnoOfCopiesid"), mGetPropertyFromFile("bnd_DrcnicnoOfCopiesData"));
			mTab("id", mGetPropertyFromFile("bnd_DrcnicnoOfCopiesid"));
			String StringNoOFCopy = driver.findElement(By.id(mGetPropertyFromFile("bnd_DrcnicnoOfCopiesid"))).getAttribute("value");
			noOfCopyForBNDCombo = Integer.parseInt(StringNoOFCopy);

			//payment for correction
			/*if(mGetPropertyFromFile("bnd_DrcnicpostalModeData").equals("By Post/Courier"))
			{	
				common.payment("paymentMode","byBankOrULB");
			}*/
			payment("paymentMode","byBankOrULB");

			mTakeScreenShot();

			if(!mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Online"))
			{
				//submit button
				mWaitForVisible("id", mGetPropertyFromFile("bnd_DrcnicSubmitBtnid"));
				mClick("id",mGetPropertyFromFile("bnd_DrcnicSubmitBtnid"));
				mCustomWait(5000);


				mWaitForVisible("id", mGetPropertyFromFile("bnd_DrcnicPrcodBtnid"));
				mGenericWait();
				// msg after death reg correction 
				String msgAftrDeathCorr = mGetText("css", mGetPropertyFromFile("bnd_DrcnicStordMsgAftrCorrid"));
				System.out.println(msgAftrDeathCorr);

				String numberOnly= msgAftrDeathCorr.replaceAll("[^0-9]", "");
				//appNo = numberOnly;
				mAppNoArray("css", "body > div.fancybox-overlay.fancybox-overlay-fixed > div > div > div > div > div > p > b");

				String delimiter = appNo;
				String[] LOIMsg = msgAftrDeathCorr.split(delimiter);
				System.out.println("msg after birth correction :"+LOIMsg[1].trim());

				//assertion for payment and without payment pop-up
				if(mGetPropertyFromFile("bnd_DrcnicpostalModeData").equals("By Post/Courier"))
				{
					mAssert(LOIMsg[1].trim(), mGetPropertyFromFile("bnd_DrcnicMsgAftrWidPayDeathCorr"), "Actual Msg :"+LOIMsg[1].trim()+"   Expected Msg :"+mGetPropertyFromFile("bnd_DrcnicMsgAftrWidPayDeathCorr"));
				}
				else
				{
					mAssert(LOIMsg[1].trim(), mGetPropertyFromFile("bnd_DrcnicMsgAftrWidOutPayDeathCorr"), "Actual Msg:"+LOIMsg[1].trim()+"  Expected Msg :"+mGetPropertyFromFile("bnd_DrcnicMsgAftrWidOutPayDeathCorr"));
				}
				mTakeScreenShot();
				//proceed button

				mClick("id", mGetPropertyFromFile("bnd_DrcnicPrcodBtnid"));

				if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
				{
					newChallanReader();
				}
				else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
				{
					mChallanPDFReader();
				}

				/*if(mGetPropertyFromFile("bnd_DrcnicpostalModeData").equals("By Post/Courier"))
			{
				mCustomWait(6000);
				//mSwitchTabs();
				if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
				{
					newChallanReader();
				}
				else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
				{
					mChallanPDFReader();
				}
			}

			if(mGetPropertyFromFile("bnd_DrcnicpostalModeData").equals("Collect at Office"))
			{
				mGenericWait();
				mWaitForVisible("xpath", mGetPropertyFromFile("bnd_DrcnicFinishBtnid"));
				mClick("xpath", mGetPropertyFromFile("bnd_DrcnicFinishBtnid"));
			}*/
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in deathRegCorrNdIsuCert method");
		}

	}

	//change in date at correction in death corr
	public void DeathchangeInDate(String date)
	{
		try
		{
			if((mGetPropertyFromFile(date).length()==0)||mGetPropertyFromFile(date).equals("?"))
			{
				System.out.println("No Change in date");
			}
			else
			{


				/*String DrcnicDate[] =mGetPropertyFromFile(date).split("/");
				String strtempdate= DrcnicDate[0];
				int cuDate=Integer.parseInt(strtempdate);
				String strnDate=Integer.toString(cuDate);
				//entity.challanRcvdDate
				System.out.println(DrcnicDate[2]);
				System.out.println(DrcnicDate[1]);
				System.out.println(strnDate);
				mGdatePicker(mGetPropertyFromFile("bnd_DrcnicChngeDateid"), DrcnicDate[2],DrcnicDate[1],strnDate);*/
				mDateSelect("id", mGetPropertyFromFile("bnd_DrcnicChngeDateid"), mGetPropertyFromFile(date));
				deathCorrDate = driver.findElement(By.id(mGetPropertyFromFile("bnd_DrcnicChngeDateid"))).getAttribute("value");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in DeathchangeInDate method");
		}
	}


	//change in gender for death registration
	public void deathChngeGender(String gen)
	{
		try
		{
			if(mGetPropertyFromFile(gen).length()==0||mGetPropertyFromFile(gen).equals("?"))
			{
				System.out.println("No change in gender");
			}
			else
			{
				mSelectDropDown("id", mGetPropertyFromFile("bnd_DrcnicChngeSexid"), mGetPropertyFromFile(gen));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in deathChngeGender method");
		}
	}


	//change in Deceased Name in death correction form
	public void changeDeceasedName(String name)
	{
		try
		{
			if(mGetPropertyFromFile(name).length()==0||mGetPropertyFromFile(name).equals("?"))
			{
				System.out.println("No change in Deceased Name");
			}
			else
			{
				mSendKeys("id", mGetPropertyFromFile("bnd_DrcnicChngeDeceasdNmeid"), "");
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_DrcnicChngeDeceasdNmeid"), mGetPropertyFromFile(name));
				deathCorrDeceasedNme = driver.findElement(By.id(mGetPropertyFromFile("bnd_DrcnicChngeDeceasdNmeid"))).getAttribute("value");
				//deathCorrDeceasedNme = mGetText("id", mGetPropertyFromFile("bnd_DrcnicChngeDeceasdNmeid"));
				System.out.println(deathCorrDeceasedNme);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in changeDeceasedName method");
		}
	}

	//change in father name in death reg correction 
	public void changeFatherNme(String name)
	{
		try
		{
			if(mGetPropertyFromFile(name).length()==0||mGetPropertyFromFile(name).equals("?"))
			{
				System.out.println("No change in father Name");
			}
			else
			{
				mSendKeys("id", mGetPropertyFromFile("bnd_DrcnicChngeFatherNmeid"), "");
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_DrcnicChngeFatherNmeid"), mGetPropertyFromFile(name));
				deathCorrFatherNme = driver.findElement(By.id( mGetPropertyFromFile("bnd_DrcnicChngeFatherNmeid"))).getAttribute("value");
				//deathCorrFatherNme = mGetText("id", mGetPropertyFromFile("bnd_DrcnicChngeFatherNmeid"));
				System.out.println(deathCorrFatherNme);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in changeFatherNme method");
		}
	}

	//change in mother name in death reg correction 
	public void changeMotherNme(String name)
	{
		try
		{
			if(mGetPropertyFromFile(name).length()==0||mGetPropertyFromFile(name).equals("?"))
			{
				System.out.println("No change in father Name");
			}
			else
			{
				mSendKeys("id", mGetPropertyFromFile("bnd_DrcnicChngeMotherNmeid"), "");
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_DrcnicChngeMotherNmeid"), mGetPropertyFromFile(name));
				//deathCorrMotherNme = mGetText("id", mGetPropertyFromFile("bnd_DrcnicChngeMotherNmeid"));
				deathCorrMotherNme =driver.findElement(By.id( mGetPropertyFromFile("bnd_DrcnicChngeMotherNmeid"))).getAttribute("value");

				System.out.println(deathCorrMotherNme);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in changeMotherNme method");
		}
	}

	//change in birth place
	public void deathChngeBirthPlace(String homeOrHos)
	{
		try
		{
			if(mGetPropertyFromFile(homeOrHos).length()==0||mGetPropertyFromFile(homeOrHos).equals("?"))
			{
				System.out.println("No change in birth place");
			}
			else
			{
				mSelectDropDown("id",mGetPropertyFromFile("bnd_DrcnicHidenDeathPlaceid"),mGetPropertyFromFile(homeOrHos));
				mGenericWait();
				if(mGetPropertyFromFile(homeOrHos).equals("Hospital/Institute")||mGetPropertyFromFile(homeOrHos).equals("Hospital"))
				{
					mSelectDropDown("id",mGetPropertyFromFile("bnd_DrcnicHosLstid"),mGetPropertyFromFile("bnd_DrcnicHosName"));
					mGenericWait();
				}
				else
				{

					mSendKeys("id",mGetPropertyFromFile("bnd_DrcnicDeathPlceid"),"");
					mHindiTextConversion("id",mGetPropertyFromFile("bnd_DrcnicDeathPlceid"),mGetPropertyFromFile("bnd_DrcnicBrthPlceData"));

					mGenericWait();

					mSendKeys("id",mGetPropertyFromFile("bnd_DrcnicDeathAddid"),"");
					mHindiTextConversion("id",mGetPropertyFromFile("bnd_DrcnicDeathAddid"),mGetPropertyFromFile("bnd_DrcnicBirthAddData"));
					mGenericWait();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in deathChngeBirthPlace method");
		}
	}


	//delivery mode
	public void changedDeathPlace(String place)
	{
		try
		{
			if(mGetPropertyFromFile(place).length()==0||mGetPropertyFromFile(place).equals("?"))
			{
				System.out.println("No change in Death place");
			}
			else
			{
				mSendKeys("id", mGetPropertyFromFile("bnd_DrcnicAddAtDeathid"), "");
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_DrcnicAddAtDeathid"), mGetPropertyFromFile(place));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in changedDeathPlace method");
		}
	}


	// Birth Registration service
	public void birthRegistration()

	{
		try 
		{

			// Wait for visible links
			mWaitForVisible("linkText",mGetPropertyFromFile("bnd_BNDLinkid"));
			mClick("linkText", mGetPropertyFromFile("bnd_BNDLinkid"));

			if(stillBirthReg)
			{
				mWaitForVisible("linkText", mGetPropertyFromFile("bnd_stlBirthReglinkid"));
				mClick("linkText", mGetPropertyFromFile("bnd_stlBirthReglinkid"));
			}
			else
			{
				mWaitForVisible("linkText", mGetPropertyFromFile("bnd_birthRegLinkid"));
				mClick("linkText", mGetPropertyFromFile("bnd_birthRegLinkid"));
			}

			// Wait for visible 'Submit' button
			mWaitForVisible("id", mGetPropertyFromFile("bnd_birthRegSubmitid"));
			mGenericWait();

			// Birth Details
			//mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegTypeid"), mGetPropertyFromFile("bnd_birthRegType"));
			//mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("bnd_birthRegDOBid"), mGetPropertyFromFile("bnd_birthRegDOB"));
			//WebElement toClearDate = mFindElement("id",mGetPropertyFromFile("bnd_birthRegDOBid"));
			//toClearDate.sendKeys(Keys.TAB);

			mDateSelect("id",mGetPropertyFromFile("bnd_birthRegDOBid"),mGetPropertyFromFile("bnd_birthRegDOBDate"));
			mGenericWait();

			birthDate = driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegDOBid"))).getAttribute("value");
			birthDatestr = birthDate.replaceAll("/", "");
			System.out.println("DOB is : "+birthDatestr);

			mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegGenderid"), mGetPropertyFromFile("bnd_birthRegGender"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_birthRegWeightid"), mGetPropertyFromFile("bnd_birthRegWeight"));
			mGenericWait();

			// Child Birth Details
			//mSendKeys("id", mGetPropertyFromFile("bnd_birthRegChildNameEngid"), mGetPropertyFromFile("bnd_birthRegChildNameEng"));

			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegChildNameEngid"), mGetPropertyFromFile("bnd_birthRegChildNameEng"));
			//mGenericWait();

			//birthRegChildName = driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegChildNameEngid"))).getAttribute("value");
			//System.out.println("Entered child name is :"+birthRegChildName);

			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegChildNameHinid"), mGetPropertyFromFile("bnd_birthRegChildNameHin"));
			//mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegPlaceTypeid"), mGetPropertyFromFile("bnd_birthRegPlaceType"));
			//mWaitForVisible("id",mGetPropertyFromFile("bnd_hospitalid"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegHospitalid"), mGetPropertyFromFile("bnd_birthRegHospital"));
			mGenericWait();
			//mSendKeys("name", mGetPropertyFromFile("bnd_birthRegInformantNmEngid"), mGetPropertyFromFile("bnd_birthRegInformantNmEng"));
			mHindiTextConversion("name", mGetPropertyFromFile("bnd_birthRegInformantNmEngid"), mGetPropertyFromFile("bnd_birthRegInformantNmEng"));
			mGenericWait();
			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegInformantNmHinid"), mGetPropertyFromFile("bnd_birthRegInformantNmHin"));
			//mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("bnd_birthRegInformantAddrEngid"), mGetPropertyFromFile("bnd_birthRegInformantAddrEng"));
			//mGenericWait();
			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegInformantAddrHinid"), mGetPropertyFromFile("bnd_birthRegInformantAddrHin"));
			//mGenericWait();

			mTakeScreenShot();

			mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegAttentionTypeid"), mGetPropertyFromFile("bnd_birthRegAttentionType"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegDeliveryMethodid"), mGetPropertyFromFile("bnd_birthRegDeliveryMethod"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_birthRegPregnancyDurationid"), mGetPropertyFromFile("bnd_birthRegPregnancyDuration"));
			mGenericWait();
			if(stillBirthReg)
			{
				mSendKeys("id", mGetPropertyFromFile("bnd_brFdeathCauseid"), mGetPropertyFromFile("bnd_brFdeathCauseData"));
				mGenericWait();
			}
			// Scroll
			mscroll(0,250);

			// Parent Details
			//mSendKeys("id", mGetPropertyFromFile("bnd_birthRegFatherNmEngid"), mGetPropertyFromFile("bnd_birthRegFatherNmEng"));
			mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegFatherNmEngid"), mGetPropertyFromFile("bnd_birthRegFatherNmEng"));
			mGenericWait();

			birthRegFatherName = driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegFatherNmEngid"))).getAttribute("value");
			System.out.println("Entered father name is :"+birthRegFatherName);

			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegFatherNmHinid"), mGetPropertyFromFile("bnd_birthRegFatherNmHin"));
			//mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegFatherEducationid"), mGetPropertyFromFile("bnd_birthRegFatherEducation"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegFatherOccupationid"), mGetPropertyFromFile("bnd_birthRegFatherOccupation"));
			mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("bnd_birthRegMotherNmEngid"), mGetPropertyFromFile("bnd_birthRegMotherNmEng"));
			mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegMotherNmEngid"), mGetPropertyFromFile("bnd_birthRegMotherNmEng"));
			mGenericWait();

			birthRegMotherName = driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegMotherNmEngid"))).getAttribute("value");
			System.out.println("Entered mother name is :"+birthRegMotherName);

			// Parents name
			parentsName = birthRegMotherName+birthRegFatherName;
			System.out.println("Parents name is :"+parentsName);

			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegMotherNmHinid"), mGetPropertyFromFile("bnd_birthRegMotherNmHin"));
			//mGenericWait();
			mSelectDropDown("id",mGetPropertyFromFile("bnd_birthRegMotherEducationid"), mGetPropertyFromFile("bnd_birthRegMotherEducation"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegMotherOccupationid"), mGetPropertyFromFile("bnd_birthRegMotherOccupation"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_birthRegAgeAtMarriageid"), mGetPropertyFromFile("bnd_birthRegAgeAtMarriage"));	
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_birthRegAgeAtBirthid"), mGetPropertyFromFile("bnd_birthRegAgeAtBirth"));	
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_birthRegNoLiveChildrenid"), mGetPropertyFromFile("bnd_birthRegNoLiveChildren"));	
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegReligionid"), mGetPropertyFromFile("bnd_birthRegReligion"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_birthRegParentAddrAtBirthEngid"), mGetPropertyFromFile("bnd_birthRegParentAddrAtBirthEng"));
			mGenericWait();
			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegParentAddrAtBirthHinid"), mGetPropertyFromFile("bnd_birthRegParentAddrAtBirthHin"));
			//mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("bnd_birthRegPermanentAddrEngid"), mGetPropertyFromFile("bnd_birthRegPermanentAddrEng"));
			//mGenericWait();
			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegPermanentAddrHinid"), mGetPropertyFromFile("bnd_birthRegPermanentAddrHin"));
			//mGenericWait();

			mTakeScreenShot();

			// Mother Address
			mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_birthRegDistrictid"), mGetPropertyFromFile("bnd_birthRegDistrict"));
			mGenericWait();
			mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_birthRegBlockid"), mGetPropertyFromFile("bnd_birthRegBlock"));
			mGenericWait();
			mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_birthRegCityid"), mGetPropertyFromFile("bnd_birthRegCity"));
			mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegRuralUrbanAreaid"), mGetPropertyFromFile("bnd_birthRegRuralUrbanArea"));
			mGenericWait();

			// Scroll
			mscroll(250, 350);

			// Applicant Details
			mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegAppcntTitleid"), mGetPropertyFromFile("bnd_birthRegAppcntTitle"));
			mSendKeys("id", mGetPropertyFromFile("bnd_appcntFNameid"), mGetPropertyFromFile("bnd_appcntFName"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_appcntMNameid"), mGetPropertyFromFile("bnd_appcntMName"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_appcntLNameid"), mGetPropertyFromFile("bnd_appcntLName"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_birthRegAppcntMobNoid"), mGetPropertyFromFile("bnd_birthRegAppcntMobNo"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_birthRegAppcntEmailid"), mGetPropertyFromFile("bnd_birthRegAppcntEmail"));
			mGenericWait();


			// Upload Documents

			if(!stillBirthReg)
			{
				//mUpload("id", mGetPropertyFromFile("bnd_birthRegUploadDoc4id"), mGetPropertyFromFile("bnd_birthRegUploadDoc4"));
				//mUploadBirthRegistration(mGetPropertyFromFile("bnd_birthRegUploadDoc2"));
				docUpload("xpath","tableName", "upldDocStatus");
				mGenericWait();
			}
			if(stillBirthReg)
			{
				mUpload("id", mGetPropertyFromFile("bnd_stlBirthReguploadid"),mGetPropertyFromFile("stillBirthUploadPath"));
				//	mUploadBirthRegistration(mGetPropertyFromFile("bnd_stlBirthReguploadid"));
				mGenericWait();
			}

			// Offline 

			/*mClick("id", mGetPropertyFromFile("offlinePaymentModeid"));

			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("payByChallanid"), mGetPropertyFromFile("payByChallan"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bankNameid"), mGetPropertyFromFile("bankName"));
			mGenericWait();*/

			if(!stillBirthReg)
			{
				if((dateIncr>=0 && dateIncr<=21))
				{

					System.out.println("Payment is not required");
				}
				else
				{
					System.out.println("Payment is required");
					common.payment("paymentMode","byBankOrULB");
				}
			}


			mTakeScreenShot();

			// Click 'Submit' button
			mClick("id", mGetPropertyFromFile("bnd_birthRegSubmitid"));
			//Assert.assertTrue(mGetText("css", "BODY").matches("^[\\s\\S]*Birth Registrationn[\\s\\S]*$"), "Text Not Present");

			mAssert("","","");


			//Reporter.log("Text not present", true);
		}
		catch(Exception e)
		{
			e. printStackTrace();
		}

	}

	//still Birth Registration proceed 
	public void stillBirthPrcd() throws IOException
	{
		mWaitForVisible("id", mGetPropertyFromFile("bnd_stlBirthRegPrcdBtn"));
		String msgAftrStillBirthReg = mGetText("xpath",mGetPropertyFromFile("bnd_msgAftrStlBirthReg"));
		System.out.println(msgAftrStillBirthReg);

		String nogAftrStillBirthReg = mGetText("xpath", mGetPropertyFromFile("bnd_noAftrStlBirthReg"));
		appNo=nogAftrStillBirthReg; 
		System.out.println(nogAftrStillBirthReg);
		String delimiter = nogAftrStillBirthReg+" ";
		String[] LOIMsg = msgAftrStillBirthReg.split(delimiter);
		System.out.println(LOIMsg[0].trim());
		System.out.println(LOIMsg[1].trim());

		//Assert not used
		mAssert("hello", LOIMsg[1].trim(), "message does not match at still  birth registration"+"Actual :"+LOIMsg[1].trim()+" Expected :"+mGetPropertyFromFile("bnd_msgAftrStlBirthregAssrt"));

		//	mAssert(mGetPropertyFromFile("bnd_msgAftrStlBirthregAssrt"), LOIMsg[1].trim(), "message does not match at still  birth registration"+"Actual :"+LOIMsg[1].trim()+" Expected :"+mGetPropertyFromFile("bnd_msgAftrStlBirthregAssrt"));
		mTakeScreenShot();
		mClick("id", mGetPropertyFromFile("bnd_stlBirthRegPrcdBtn"));
		mGenericWait();
		mTakeScreenShot();
		mClick("xpath",mGetPropertyFromFile("bnd_stlBirthRegFinishBtnid"));


	}

	// Birth Registration proceed

	public void birthRegProceed()
	{
		try
		{
			appNo=mGetApplicationNo("css",mGetPropertyFromFile("bnd_birthRegAppNoid"));
			appcntName = mGetPropertyFromFile("bnd_appcntFName")+" "+mGetPropertyFromFile("bnd_appcntMName")+" "+mGetPropertyFromFile("bnd_appcntLName");

			appSubmitMsg = mGetText("css", mGetPropertyFromFile("bnd_appSubmitMsgid"));
			strippedString = appSubmitMsg.replace(appNo, "");
			System.out.println(strippedString);
			//mAssert("Your Application No.: for Registration of Birth has been saved successfully. Proceed for payment.", strippedString,"Message does not match, Expected: Your Application No.: for Registration of Birth has been saved successfully. Proceed for payment. || Actual: "+strippedString);

			//Use loop for checking application generation pop-up message -Ritesh 28-10-2015
			if((dateIncr>=0 && dateIncr<=21))
			{

				mAssert("Your Application No.: for Registration of Birth has been saved successfully.", strippedString,"Message does not match, Expected: Your Application No.: for Registration of Birth has been saved successfully. || Actual: "+strippedString);
			}
			else
			{
				mAssert("Your Application No.: for Registration of Birth has been saved successfully. Proceed for payment.", strippedString,"Message does not match, Expected: Your Application No.: for Registration of Birth has been saved successfully. Proceed for payment. || Actual: "+strippedString);
			}



			// Wait for visible 'Proceed' button
			mWaitForVisible("id", mGetPropertyFromFile("bnd_birthRegProceedid"));
			mGenericWait();

			mTakeScreenShot();

			// Click 'Proceed' button
			mClick("id", mGetPropertyFromFile("bnd_birthRegProceedid"));
			mCustomWait(10000);



			if((dateIncr>=0 && dateIncr<=21))
			{



				pdfService="challanPdf";
				//mChallanPDFReader(pdfService,appNo,appcntName,"","","","");


				//mChallanPDFReader(appNo,appcntName);



				System.out.println("Challan PDF reader is not required");
			}
			else
			{
				System.out.println("Challan PDF reader is required");
				mChallanPDFReader();
			}


			/*mAssert(appNo,printedAppNo,"Application number does not match, browser: "+appNo+" || PDF: "+printedAppNo);
			mAssert(appcntName, applcntName,"Applicant name does not match, browser: "+appcntName+" || PDF: "+applcntName);*/
			// Switch to original window
			//mSwitchTabs();

			//Assert.assertTrue(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @author Madhuri.dawande
	 * @since 27-08-2015
	 *
	 */


	// Method for search application number for Birth Registration Authorization
	public void searchForBirthRegAuthorization(String serviceName)
	{
		try
		{

			/*mWaitForVisible("linkText", mGetPropertyFromFile("bnd_BNDLinkid"));
			mClick("linkText", mGetPropertyFromFile("bnd_BNDLinkid"));
			mGenericWait();

			//Wait for transaction--Ritesh 28-10-2015
			mWaitForVisible("linkText", mGetPropertyFromFile("bnd_authoRegBndTransactionLinkid"));
			mClick("linkText", mGetPropertyFromFile("bnd_authoRegBndTransactionLinkid"));
			mGenericWait();

			//Wait for Autho of BnD link
			mWaitForVisible("linkText", mGetPropertyFromFile("bnd_authoRegBndRegLinkid"));

			//Authorization of Birth and Death registration
			mClick("linkText", mGetPropertyFromFile("bnd_authoRegBndRegLinkid"));
			mGenericWait();



			//Wait for Birth Reg link
			//mWaitForVisible("xpath", mGetPropertyFromFile("bnd_authoRegBirthRegLinkid"));

			//Birth Registration link
			//	mClick("xpath", mGetPropertyFromFile("bnd_authoRegBirthRegLinkid"));

			//Wait for Autho of Birth Reg link
			mWaitForVisible("linkText", mGetPropertyFromFile("bnd_authoRegOfBirthRegLinkid"));
			mGenericWait();
			//Authorization of Birth registration
			mClick("linkText", mGetPropertyFromFile("bnd_authoRegOfBirthRegLinkid"));
			mGenericWait();*/

			mNavigation(mGetPropertyFromFile("bnd_BNDLinkid"), mGetPropertyFromFile("bnd_authoRegBndTransactionLinkid"), mGetPropertyFromFile("bnd_authoRegBndRegLinkid"), mGetPropertyFromFile("bnd_authoRegOfBirthRegLinkid"));

			/*mWaitForVisible("id", mGetPropertyFromFile("bnd_authoRegRegistrationTypeid"));
			mSelectDropDown("id", mGetPropertyFromFile("bnd_authoRegRegistrationTypeid"), mGetPropertyFromFile("bnd_authoRegRegistrationType"));
			mGenericWait();

			mWaitForVisible("id", mGetPropertyFromFile("bnd_authoRegServiceNameid"));
			mSelectDropDown("id", mGetPropertyFromFile("bnd_authoRegServiceNameid"), mGetPropertyFromFile(serviceName));
			mGenericWait();*/

			searchByTypeOfRegn(serviceName);
			/*srvcName = driver.findElement(By.id(mGetPropertyFromFile("bnd_authoRegServiceNameid")))
			System.out.println("Service name is : "+srvcName);*/

			/*Select sel = new Select(driver.findElement(By.id(mGetPropertyFromFile("bnd_authoRegServiceNameid"))));
			WebElement abc = sel.getFirstSelectedOption();
			String srvcName = abc.toString();
			System.out.println("Service name is : "+srvcName);*/


			//Wait
			mWaitForVisible("css", mGetPropertyFromFile("bnd_authoRegSearchid"));

			//WebElement toclear = mFindElement("id", mGetPropertyFromFile("bnd_authoRegServiceNameid"));
			//toclear.sendKeys(Keys.TAB);

			mClick("css", mGetPropertyFromFile("bnd_authoRegSearchid"));
			mCustomWait(10000);

			mWaitForVisible("xpath", mGetPropertyFromFile("bnd_authoRegViewDetailsid"));

			// search
			mClick("css", mGetPropertyFromFile("bnd_authoRegFindRecordsid"));
			mGenericWait();

			mSelectDropDown("css", mGetPropertyFromFile("bnd_authoRegSelectAppNoid"), mGetPropertyFromFile("bnd_authoRegSelectAppNo"));
			mGenericWait();
			mSelectDropDown("css", mGetPropertyFromFile("bnd_authoRegSelectEqualid"), mGetPropertyFromFile("bnd_authoRegSelectEqual"));
			mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("bnd_authoRegEnterAppNoid"),BirthAndDeathServices.appNo);
			//mSendKeys("id", mGetPropertyFromFile("bnd_authoRegEnterAppNoid"),"78616051900008");

			IndOrDep("id", "bnd_authoRegEnterAppNoid", "applicationNo");

			mGenericWait();

			// Wait for visible 'Search' button

			mWaitForVisible("id", mGetPropertyFromFile("bnd_authoRegFindButtonid"));
			mGenericWait();

			WebElement toClear = mFindElement("id",mGetPropertyFromFile("bnd_authoRegEnterAppNoid"));
			toClear.sendKeys(Keys.TAB);

			mClick("id", mGetPropertyFromFile("bnd_authoRegFindButtonid"));
			mGenericWait();

			mWaitForVisible("xpath", mGetPropertyFromFile("bnd_authoRegViewDetailsid"));

			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("bnd_authoRegViewDetailsid"));
			mGenericWait();

			thisServiceName =serviceName;
			System.out.println("Service name::"+thisServiceName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in searchForBirthRegAuthorization method");
		}
	}

	// Method for search application number for Death Registration Authorization
	public void searchForDeathRegAuthorization(String serviceName)
	{
		try
		{
			/*mWaitForVisible("linkText", mGetPropertyFromFile("bnd_BNDLinkid"));
			mClick("linkText", mGetPropertyFromFile("bnd_BNDLinkid"));
			mGenericWait();

			//transaction link
			mWaitForVisible("linkText", mGetPropertyFromFile("bnd_authoRegBndTransactionLinkid"));
			mClick("linkText", mGetPropertyFromFile("bnd_authoRegBndTransactionLinkid"));

			//Wait for Autho of BnD link
			mWaitForVisible("linkText", mGetPropertyFromFile("bnd_authoRegBndRegLinkid"));

			//Wait for transaction--Ritesh 28-10-2015
			mWaitForVisible("linkText", mGetPropertyFromFile("bnd_authoRegBndTransactionLinkid"));
			mClick("linkText", mGetPropertyFromFile("bnd_authoRegBndTransactionLinkid"));

			mGenericWait();


			//Authorization of Birth and Death registration
			mClick("linkText", mGetPropertyFromFile("bnd_authoRegBndRegLinkid"));
			mGenericWait();



			//Wait for Autho of Death Reg link
			mWaitForVisible("linkText", mGetPropertyFromFile("bnd_authoRegOfDeathRegLinkid"));

			//Authorization of Death registration
			mClick("linkText", mGetPropertyFromFile("bnd_authoRegOfDeathRegLinkid"));
			mGenericWait();*/

			mNavigation(mGetPropertyFromFile("bnd_BNDLinkid"), mGetPropertyFromFile("bnd_authoRegBndTransactionLinkid"), mGetPropertyFromFile("bnd_authoRegBndRegLinkid"), mGetPropertyFromFile("bnd_authoRegOfDeathRegLinkid"));

			//
			mWaitForVisible("id", mGetPropertyFromFile("bnd_authoRegRegistrationTypeid"));
			mSelectDropDown("id", mGetPropertyFromFile("bnd_authoRegRegistrationTypeid"), mGetPropertyFromFile("bnd_authoRegRegistrationType"));
			mGenericWait();

			mWaitForVisible("id", mGetPropertyFromFile("bnd_authoRegServiceNameid"));
			mSelectDropDown("id", mGetPropertyFromFile("bnd_authoRegServiceNameid"), serviceName);
			mGenericWait();

			thisServiceName =serviceName;
			System.out.println("Service name::"+thisServiceName);

			/*srvcName = driver.findElement(By.id(mGetPropertyFromFile("bnd_authoRegServiceNameid"))).getAttribute("value");
			System.out.println("Service name is : "+srvcName);*/

			/*	WebElement mySelectElm = driver.findElement(By.id(mGetPropertyFromFile("bnd_authoRegServiceNameid"))); 
			Select sel = new Select(mySelectElm);

			WebElement srvcName = sel.getFirstSelectedOption();
			System.out.println("Service name is : "+srvcName.getText());*/

			//Wait
			mWaitForVisible("css", mGetPropertyFromFile("bnd_authoRegSearchid"));

			WebElement toclear = mFindElement("id", mGetPropertyFromFile("bnd_authoRegServiceNameid"));
			toclear.sendKeys(Keys.TAB);

			mClick("css", mGetPropertyFromFile("bnd_authoRegSearchid"));
			mCustomWait(10000);

			mWaitForVisible("xpath", mGetPropertyFromFile("bnd_authoRegViewDetailsid"));

			// search
			mClick("id", mGetPropertyFromFile("bnd_authoRegDeathRegFindRecordsid"));
			mGenericWait();
			mSelectDropDown("css", mGetPropertyFromFile("bnd_authoRegSelectAppNoid"), mGetPropertyFromFile("bnd_authoRegSelectAppNo"));
			mGenericWait();
			mSelectDropDown("css", mGetPropertyFromFile("bnd_authoRegSelectEqualid"), mGetPropertyFromFile("bnd_authoRegSelectEqual"));
			mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("bnd_authoRegEnterAppNoid"),"57015120100002");
			//mSendKeys("id", mGetPropertyFromFile("bnd_authoRegEnterAppNoid"),BirthAndDeathServices.appNo);
			IndOrDep("id", "bnd_authoRegEnterAppNoid","");
			//mSendKeys("id", mGetPropertyFromFile("bnd_authoRegEnterAppNoid"),"57015092400005");

			mGenericWait();


			// Wait for visible 'Search' button

			mWaitForVisible("id", mGetPropertyFromFile("bnd_authoRegDeathRegFindButtonid"));
			mGenericWait();

			WebElement toClear = mFindElement("id",mGetPropertyFromFile("bnd_authoRegEnterAppNoid"));
			toClear.sendKeys(Keys.TAB);

			mClick("id", mGetPropertyFromFile("bnd_authoRegDeathRegFindButtonid"));
			mGenericWait();

			mWaitForVisible("xpath", mGetPropertyFromFile("bnd_authoRegViewDetailsid"));
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("bnd_authoRegViewDetailsid"));
			mGenericWait();
			/*mTakeScreenShot();
			mscroll(0,300);
			mGenericWait();
			mTakeScreenShot();
			mscroll(300,600);
			mGenericWait();
			mTakeScreenShot();
			mscroll(600,900);
			mGenericWait();
			mTakeScreenShot();*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in searchForDeathRegAuthorization method");
		}
	}


	/**
	 * @author Madhuri.dawande
	 * @since 27-08-2015
	 *
	 */
	// Method for Birth Registration Authorization
	public void birthRegAuthorization(String appAutho,String docAutho)
	{
		try
		{
			mTakeScreenShot();
			mGenericWait();
			mscroll(1173, 663);
			mGenericWait();
			mTakeScreenShot();
			mGenericWait();
			mscroll(1205, 649);
			mGenericWait();
			mTakeScreenShot();
			mGenericWait();

			//mWaitForVisible("css", mGetPropertyFromFile("bnd_authoRegBirthRegSubmitid"));
			if(mGetPropertyFromFile("bnd_chkListVrfnApplicable").equals("No"))
			{
				// work in progress
			}
			System.out.println("appStatusRejectedDueToInadequateInformation :"+appStatusRejectedDueToInadequateInformation);
			System.out.println("bnd_appStatusRejectedDueToInadequateInformationAndDocuments :"+bnd_appStatusRejectedDueToInadequateInformationAndDocuments);
			if(appStatusRejectedDueToInadequateInformation==true || bnd_appStatusRejectedDueToInadequateInformationAndDocuments==false)

			{
				/*if(thisServiceName!=(mGetPropertyFromFile("bnd_authoRegBirthCertService")))
				{*/
					//For select District
					mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_authoRegDistrictid"), mGetPropertyFromFile("bnd_authoRegDistrict"));
					mGenericWait();

					//For select Taluka
					mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_authoRegTalukaid"), mGetPropertyFromFile("bnd_authoRegTaluka"));
					mGenericWait();

					//For select city
					mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_authoRegCityid"), mGetPropertyFromFile("bnd_authoRegCity"));
					mGenericWait();

					mSelectDropDown("id", mGetPropertyFromFile("bnd_authoRegRegnUnitid"), mGetPropertyFromFile("bnd_authoRegRegnUnit"));
					mGenericWait();
					//if(bnd_docAuthorised==false && docAutho.equalsIgnoreCase("Yes"))
					if(bnd_docAuthorised==false && mGetPropertyFromFile("bnd_chkListVrfnApplicable").equalsIgnoreCase("No"))
					{
						mBndViewAndVerifyDocs(docAutho);
					}
					else
					{
						bnd_docAuthorised=false;
					}
				//}
			}
			if(appAutho.equalsIgnoreCase("Yes"))
			{
				if(docAutho.equalsIgnoreCase("Yes"))
				{
					/*if(mGetPropertyFromFile("bnd_appStatusAuthorized").equalsIgnoreCase("yes"))
					{*/
					mClick("id", mGetPropertyFromFile("bnd_authoRegAppcnAuthorizedid"));
					//}

				}
			}
			else if(appAutho.equalsIgnoreCase("No"))
			{
				if(docAutho.equalsIgnoreCase("Yes"))
				{
					/*if(mGetPropertyFromFile("bnd_appStatusRejectedDueToInadequateInformation").equalsIgnoreCase("yes"))
					{*/
					mClick("id", mGetPropertyFromFile("bnd_authoRegAppcnRejectedDueToInadequateInfoid"));
					appStatusRejectedDueToInadequateInformation=true;
					//}
				}
				else if(docAutho.equalsIgnoreCase("No"))
				{
					if(mGetPropertyFromFile("bnd_appStatusRejectedDueToInadequateDocuments").equalsIgnoreCase("yes"))
					{
						mClick("id", mGetPropertyFromFile("bnd_authoRegAppcnRejectedDueToInadequateDocid"));
						bnd_appStatusRejectedDueToInadequateInformationAndDocuments=true;
					}
					else if(mGetPropertyFromFile("bnd_appStatusRejectedDueToInadequateInformationAndDocuments").equalsIgnoreCase("yes"))
					{
						mClick("id", mGetPropertyFromFile("bnd_authoRegAppcnRejectedDueToInadequateInfo&Docid"));
						bnd_appStatusRejectedDueToInadequateInformationAndDocuments=true;
					}
				}
			}

			mscroll(1235, 652);
			//mTakeScreenShot();
			mCustomWait(5000);

			if(thisServiceName!=(mGetPropertyFromFile("bnd_authoRegBirthCertService")))
			{
				authoBirthDate=driver.findElement(By.id(mGetPropertyFromFile("bnd_authoBirthRegDateid"))).getAttribute("value");
				bDate = authoBirthDate.substring(0, 10);
				//authoBirthDate = mGetText("id", mGetPropertyFromFile("bnd_authoBirthRegDateid"));
				System.out.println("Birth date is : "+bDate);
				mAssert(birthDate, bDate,"Date of birth does not match, Expected: "+birthDate+" || Actual: "+bDate);

				if(thisServiceName.equalsIgnoreCase(mGetPropertyFromFile("bnd_birthRegCorrBirthAuthrzn")))
				{
					String authoGender=driver.findElement(By.cssSelector("#frmMasterForm > div.table.clear > div:nth-child(2) > fieldset:nth-child(18) > input.disablefield.float-right")).getAttribute("value");
					System.out.println("Gender is : "+authoGender);
					mAssert(birthGender, authoGender,"Gender does not match, Expected: "+birthGender+" || Actual: "+authoGender);
				}
				else
				{
					String authoGender=driver.findElement(By.cssSelector(mGetPropertyFromFile("bnd_authoBirthRegGenderid"))).getAttribute("value");
					System.out.println("Gender is : "+authoGender);
					mAssert(birthGender, authoGender,"Gender does not match, Expected: "+birthGender+" || Actual: "+authoGender);
				}

				if(thisServiceName!=(mGetPropertyFromFile("bnd_birthRegCorrBirthAuthrzn")))
				{
					String authoBirthWt = driver.findElement(By.id("brBirthwt")).getAttribute("value");
					mAssert(authoBirthWt, birthWeight,"Birth weight does not match, Expected: "+birthWeight+" || Actual: "+authoBirthWt);
				}
				//childName = mGetText("id", mGetPropertyFromFile("bnd_authoBirthRegChildNameEngid"));
				childName = driver.findElement(By.id(mGetPropertyFromFile("bnd_authoBirthRegChildNameEngid"))).getAttribute("value");
				System.out.println("Child name is : "+childName);
				if(childName.equals(""))
				{
					System.out.println("Child name not entered");
				}
				else
				{
					mAssert(birthRegChildName, childName,"Child name does not match, Expected: "+birthRegChildName+" || Actual: "+childName);   
				}

				//fatherName = mGetText("id", mGetPropertyFromFile("bnd_authoBirthRegFatherNameEngid"));
				fatherName = driver.findElement(By.id(mGetPropertyFromFile("bnd_authoBirthRegFatherNameEngid"))).getAttribute("value");
				System.out.println("Father name is : "+fatherName);
				if(fatherName.equals(""))
				{
					System.out.println("Father name not entered");
				}
				else
				{
					mAssert(birthRegFatherName, fatherName,"Father name does not match, Expected: "+birthRegFatherName+" || Actual: "+fatherName);   
				}

				//motherName = mGetText("id", mGetPropertyFromFile("bnd_authoBirthRegMotherNameEngid"));
				motherName = driver.findElement(By.id(mGetPropertyFromFile("bnd_authoBirthRegMotherNameEngid"))).getAttribute("value");
				System.out.println("Mother name is : "+motherName);
				mAssert(birthRegMotherName, motherName,"Mother name does not match, Expected: "+birthRegMotherName+" || Actual: "+motherName);

				/*// BPL Type assertion
			if(mGetPropertyFromFile("bnd_bplApplicable").equals("Yes"))
			{
			String bplType = driver.findElement(By.id(mGetPropertyFromFile("bnd_BirthCorrAuthrBPLTypeid"))).getAttribute("value");
			System.out.println("BPL Type is : "+bplType);
			mAssert(bndBPLTypeValue, bplType,"BPL type does not match, Expected: "+bndBPLTypeValue+" || Actual: "+bplType);
			}*/
			}
			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("bnd_authoRegBirthRegSubmitid"));
			bndResubmit=false;

			//Wait for BirthRegNo PopUp
			mWaitForVisible("id", mGetPropertyFromFile("bnd_authoRegProceedid"));
			mGenericWait();

			if(appAutho.equalsIgnoreCase("Yes"))
			{
				//Get Registration No
				BirthRegNo = mGetText("css", mGetPropertyFromFile("bnd_authoRegRegistrationNoid"));
				System.out.println("BND Birth Reg No ="+BirthRegNo);

				mTakeScreenShot();

				String authoSubmitMsg = mGetText("css", mGetPropertyFromFile("bnd_authoSubmitMsgid"));
				String strippedStr = authoSubmitMsg.replace(BirthRegNo, "");
				System.out.println(strippedStr);
				mAssert("Registration Number No.: has been Authorized successfully", strippedStr,"Birth Registration authorization message does not match, Expected: Registration Number No.: has been Authorized successfully || Actual: "+strippedStr);

			}
			else if(appAutho.equalsIgnoreCase("No"))
			{
				mTakeScreenShot();
				String authoSubmitMsg = mGetText("css", mGetPropertyFromFile("bnd_authoSubmitMsgid"));
				mAssert(authoSubmitMsg, mGetPropertyFromFile("bnd_unAuthoSubmitMsg"),"Birth Registration unauthorization message does not match, Expected: "+mGetPropertyFromFile("bnd_unAuthoSubmitMsg")+"Actual: "+authoSubmitMsg);
			}
			mClick("id", mGetPropertyFromFile("bnd_authoRegProceedid"));

			//Wait for Home Page load
			mGenericWait();

			mWaitForVisible("css", mGetPropertyFromFile("bnd_authoRegSearchid"));
			//softAssert.assertEquals(BirthRegNo, "149");
			//mAssert(BirthRegNo, "150", "Registration number does not match");
			//softAssert.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in birthRegAuthorization method");
		}
	}

	//sneha solaskar
	// Method for Birth Registration Authorization
	public void incOfChldNmeAuthorization(String appAutho,String docAutho)
	{
		try
		{
			//mWaitForVisible("css", mGetPropertyFromFile("bnd_authoRegBirthRegSubmitid"));
			if(appStatusRejectedDueToInadequateInformation==true || bnd_appStatusRejectedDueToInadequateInformationAndDocuments==false)

			{

				//For select District
				mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_authoRegDistrictid"), mGetPropertyFromFile("bnd_authoRegDistrict"));
				//	mSendKeys("id", "district", "Patna");
				mGenericWait();

				//For select Taluka
				mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_authoRegTalukaid"), mGetPropertyFromFile("bnd_authoRegTaluka"));
				mGenericWait();

				//For select city
				mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_authoRegCityid"), mGetPropertyFromFile("bnd_authoRegCity"));
				mGenericWait();

				mSelectDropDown("id", mGetPropertyFromFile("bnd_authoRegRegnUnitid"), mGetPropertyFromFile("bnd_authoRegRegnUnit"));
				mGenericWait();

				if(bnd_docAuthorised==false)
				{
					mBndViewAndVerifyDocs(docAutho);
				}
				else
				{
					bnd_docAuthorised=false;
				}

			}


			//mClick("id", mGetPropertyFromFile("bnd_authoRegAppcnAuthorizedid"));
			if(appAutho.equalsIgnoreCase("Yes"))
			{
				if(docAutho.equalsIgnoreCase("Yes"))
				{
					/*if(mGetPropertyFromFile("bnd_appStatusAuthorized").equalsIgnoreCase("yes"))
					{*/
					mClick("id", mGetPropertyFromFile("bnd_authoRegAppcnAuthorizedid"));
					//}

				}
			}
			else if(appAutho.equalsIgnoreCase("No"))
			{
				if(docAutho.equalsIgnoreCase("Yes"))
				{
					/*if(mGetPropertyFromFile("bnd_appStatusRejectedDueToInadequateInformation").equalsIgnoreCase("yes"))
					{*/
					mClick("id", mGetPropertyFromFile("bnd_authoRegAppcnRejectedDueToInadequateInfoid"));
					appStatusRejectedDueToInadequateInformation=true;
					//}
				}
				else if(docAutho.equalsIgnoreCase("No"))
				{
					if(mGetPropertyFromFile("bnd_appStatusRejectedDueToInadequateDocuments").equalsIgnoreCase("yes"))
					{
						mClick("id", mGetPropertyFromFile("bnd_authoRegAppcnRejectedDueToInadequateDocid"));
						bnd_appStatusRejectedDueToInadequateInformationAndDocuments=true;
					}
					else if(mGetPropertyFromFile("bnd_appStatusRejectedDueToInadequateInformationAndDocuments").equalsIgnoreCase("yes"))
					{
						mClick("id", mGetPropertyFromFile("bnd_authoRegAppcnRejectedDueToInadequateInfo&Docid"));
						bnd_appStatusRejectedDueToInadequateInformationAndDocuments=true;
					}
				}
			}

			mTakeScreenShot();
			mCustomWait(5000);


			authoBirthDate=driver.findElement(By.id(mGetPropertyFromFile("bnd_IncOfChldNmeAuthrDobid"))).getAttribute("value");
			bDate = authoBirthDate.substring(0, 10);
			//authoBirthDate = mGetText("id", mGetPropertyFromFile("bnd_authoBirthRegDateid"));
			System.out.println("Birth date is : "+bDate);
			mAssert(birthDate, bDate,"Date of birth does not match, Expected: "+birthDate+" || Actual: "+bDate);

			//childName = mGetText("id", mGetPropertyFromFile("bnd_authoBirthRegChildNameEngid"));
			childName = driver.findElement(By.id(mGetPropertyFromFile("bnd_IncOfChldNmeAuthrChNmeid"))).getAttribute("value");
			System.out.println("Child name is : "+childName);
			if(childName.equals(""))
			{
				System.out.println("Child name not entered");
			}
			else
			{
				mAssert(birthRegChildName, childName,"Child name does not match, Expected: "+birthRegChildName+" || Actual: "+childName);   
			}

			//fatherName = mGetText("id", mGetPropertyFromFile("bnd_authoBirthRegFatherNameEngid"));
			fatherName = driver.findElement(By.name(mGetPropertyFromFile("bnd_IncOfChldNmeAuthrFathrNmeid"))).getAttribute("value");
			System.out.println("Father name is : "+fatherName);
			if(fatherName.equals(""))
			{
				System.out.println("Father name not entered");
			}
			else
			{
				mAssert(birthRegFatherName, fatherName,"Father name does not match, Expected: "+birthRegFatherName+" || Actual: "+fatherName);   
			}

			//motherName = mGetText("id", mGetPropertyFromFile("bnd_authoBirthRegMotherNameEngid"));
			motherName = driver.findElement(By.name(mGetPropertyFromFile("bnd_IncOfChldNmeAuthrMothrNmeid"))).getAttribute("value");
			System.out.println("Mother name is : "+motherName);
			mAssert(birthRegMotherName, motherName,"Mother name does not match, Expected: "+birthRegMotherName+" || Actual: "+motherName);

			mGenericWait();

			mClick("xpath", mGetPropertyFromFile("bnd_IncOfChldNmeauthoRegBirthRegSubmitid"));


			//Wait for BirthRegNo PopUp
			mWaitForVisible("id", mGetPropertyFromFile("bnd_authoRegProceedid"));
			mGenericWait();
			if(appAutho.equalsIgnoreCase("Yes"))
			{
				//Get Registration No
				BirthRegNo = mGetText("css", mGetPropertyFromFile("bnd_authoRegRegistrationNoid"));
				System.out.println("BND Birth Reg No ="+BirthRegNo);

				mTakeScreenShot();

				String authoSubmitMsg = mGetText("css", mGetPropertyFromFile("bnd_authoSubmitMsgid"));
				String strippedStr = authoSubmitMsg.replace(BirthRegNo, "");
				System.out.println(strippedStr);
				mAssert("Registration Number No.: has been Authorized successfully", strippedStr,"Birth Registration authorization message does not match, Expected: Registration Number No.: has been Authorized successfully || Actual: "+strippedStr);
			}
			else if(appAutho.equalsIgnoreCase("No"))
			{
				mTakeScreenShot();
				String authoSubmitMsg = mGetText("css", mGetPropertyFromFile("bnd_authoSubmitMsgid"));
				mAssert(authoSubmitMsg, mGetPropertyFromFile("bnd_unAuthoSubmitMsg"),"Inclusion of Child Name unauthorization message does not match, Expected: "+mGetPropertyFromFile("bnd_unAuthoSubmitMsg")+"Actual: "+authoSubmitMsg);
			}
			mClick("id", mGetPropertyFromFile("bnd_authoRegProceedid"));

			//Wait for Home Page load
			mGenericWait();

			mWaitForVisible("css", mGetPropertyFromFile("bnd_authoRegSearchid"));
			//softAssert.assertEquals(BirthRegNo, "149");
			//mAssert(BirthRegNo, "150", "Registration number does not match");



			//softAssert.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in incOfChldNmeAuthorization method");
		}
	}

	// Method for Birth Registration Authorization
	public void birthCorrAuthorization()
	{
		try
		{
			//mWaitForVisible("css", mGetPropertyFromFile("bnd_authoRegBirthRegSubmitid"));


			//For select District
			mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_authoRegDistrictid"), mGetPropertyFromFile("bnd_authoRegDistrict"));
			//	mSendKeys("id", "district", "Patna");
			mGenericWait();

			//For select Taluka
			mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_authoRegTalukaid"), mGetPropertyFromFile("bnd_authoRegTaluka"));
			mGenericWait();

			//For select city
			mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_authoRegCityid"), mGetPropertyFromFile("bnd_authoRegCity"));
			mGenericWait();

			mSelectDropDown("id", mGetPropertyFromFile("bnd_authoRegRegnUnitid"), mGetPropertyFromFile("bnd_authoRegRegnUnit"));
			mGenericWait();

			mBndViewAndVerifyDocs("");
			//verifyDoc();

			mClick("id", mGetPropertyFromFile("bnd_authoRegAppcnAuthorizedid"));

			//mTakeScreenShot();
			mCustomWait(5000);
			authoBirthDate=driver.findElement(By.name(mGetPropertyFromFile("bnd_BirthCorrAuthrDateid"))).getAttribute("value");
			if(mGetPropertyFromFile("bnd_BirthCorrAuthrDateid").length()==0)
			{
				System.out.println("No change in date");
			}

			/*bDate = authoBirthDate.substring(0, 10);
			//authoBirthDate = mGetText("id", mGetPropertyFromFile("bnd_authoBirthRegDateid"));
			System.out.println("Birth date is : "+bDate);
			mAssert(birthDate, bDate,"Date of birth does not match, Expected: "+birthDate+" || Actual: "+bDate);*/

			//childName = mGetText("id", mGetPropertyFromFile("bnd_authoBirthRegChildNameEngid"));
			childName = driver.findElement(By.name(mGetPropertyFromFile("bnd_BirthCorrAuthrChildNmeid"))).getAttribute("value");
			System.out.println("Child name is : "+childName);
			if(childName.length()==0)
			{
				System.out.println("Child name not entered");
			}
			else
			{
				mAssert(birthRegChildName, childName,"Child name does not match, Expected: "+birthRegChildName+" || Actual: "+childName);   
			}

			//fatherName = mGetText("id", mGetPropertyFromFile("bnd_authoBirthRegFatherNameEngid"));
			fatherName = driver.findElement(By.name(mGetPropertyFromFile("bnd_BirthCorrAuhtrFatherNmeid"))).getAttribute("value");
			System.out.println("Father name is : "+fatherName);
			if(fatherName.length()==0)
			{
				System.out.println("Father name not entered");
			}
			else
			{
				mAssert(birthRegFatherName, fatherName,"Father name does not match, Expected: "+birthRegFatherName+" || Actual: "+fatherName);   
			}

			//motherName = mGetText("id", mGetPropertyFromFile("bnd_authoBirthRegMotherNameEngid"));
			motherName = driver.findElement(By.name(mGetPropertyFromFile("bnd_BirthCorrAuthrMotherNmeid"))).getAttribute("value");
			System.out.println("Mother name is : "+motherName);
			mAssert(birthRegMotherName, motherName,"Mother name does not match, Expected: "+birthRegMotherName+" || Actual: "+motherName);


			//submit button
			mGenericWait();
			mWaitForVisible("xpath", mGetPropertyFromFile("bnd_BirthCorrAuthrSubmitBtnid"));
			mClick("xpath", mGetPropertyFromFile("bnd_BirthCorrAuthrSubmitBtnid"));


			//Wait for BirthRegNo PopUp
			mWaitForVisible("id", mGetPropertyFromFile("bnd_BirthCorrAuthrPrcdBtnid"));
			mGenericWait();

			//Get Registration No
			BirthRegNo = mGetText("xpath", mGetPropertyFromFile("bnd_BirthCorrAuthrRegNo"));
			System.out.println("BND Birth Reg No ="+BirthRegNo);

			mTakeScreenShot();

			String authoSubmitMsg = mGetText("xpath", mGetPropertyFromFile("bnd_BirthCorrAuthrMsg"));


			String delimiter = BirthRegNo;
			String[] LOIMsg = authoSubmitMsg.split(delimiter);
			System.out.println("msg after birth correction :"+LOIMsg[1].trim());

			// assertion for the msg after birth correction
			mAssert(LOIMsg[1].trim(), mGetPropertyFromFile("bnd_BirthCorrAuthrMsgData"),"Actual  :"+LOIMsg[1].trim()+"  Expected  :"+mGetPropertyFromFile("bnd_BirthCorrAuthrMsgData"));


			//proceedbutton

			mClick("id", mGetPropertyFromFile("bnd_BirthCorrAuthrPrcdBtnid"));

			//Wait for Home Page load
			mGenericWait();

			mWaitForVisible("css", mGetPropertyFromFile("bnd_authoRegSearchid"));
			//softAssert.assertEquals(BirthRegNo, "149");
			//mAssert(BirthRegNo, "150", "Registration number does not match");



			//softAssert.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	// Method for Birth Registration Authorization
	public void deathRegAuthorization(String appAutho,String docAutho)
	{
		try
		{

			//mWaitForVisible("xpath", mGetPropertyFromFile("bnd_authoRegDeathRegSubmitid"));
			mTakeScreenShot();
			mGenericWait();
			mscroll(1173, 663);
			mGenericWait();
			mTakeScreenShot();
			mGenericWait();
			mscroll(1205, 649);
			mGenericWait();
			mTakeScreenShot();
			mGenericWait();
			if(appStatusRejectedDueToInadequateInformation==true || bnd_appStatusRejectedDueToInadequateInformationAndDocuments==false)

			{
				if(thisServiceName!=(mGetPropertyFromFile("bnd_authoRegDeathCertService")))
				{
					//For select District
					mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_authoRegDistrictid"), mGetPropertyFromFile("bnd_authoRegDistrict"));
					mGenericWait();

					//For select Taluka
					mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_authoRegTalukaid"), mGetPropertyFromFile("bnd_authoRegTaluka"));
					mGenericWait();



					//DynamicObjects();
					//mClick("css", mGetPropertyFromFile("bnd_authoRegdocid"));
					//mDownloadPDFFile("com.abm.mainet.bnd.BirthAndDeathServices");
					//mClick("id", mGetPropertyFromFile("bnd_authoRegDocAuthorizedid"));
					//mSendKeys("id", mGetPropertyFromFile("bnd_authoRegRemarksid"), mGetPropertyFromFile("bnd_authoRegRemarks"));


					//For select city
					mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_authoRegCityid"), mGetPropertyFromFile("bnd_authoRegCity"));
					mGenericWait();

					mSelectDropDown("id", mGetPropertyFromFile("bnd_authoDeathRegRegnUnitid"), mGetPropertyFromFile("bnd_authoRegRegnUnit"));
					mGenericWait();

					if(bnd_docAuthorised==false && mGetPropertyFromFile("bnd_chkListVrfnApplicable").equalsIgnoreCase("No"))
					{
						mBndViewAndVerifyDocs(docAutho);
					}
					else
					{
						bnd_docAuthorised=false;
					}
				}
			}
			//mClick("id", mGetPropertyFromFile("bnd_authoRegAppcnAuthorizedid"));
			mscroll(1235, 652);
			mTakeScreenShot();
			mGenericWait();

			if(appAutho.equalsIgnoreCase("Yes"))
			{
				if(docAutho.equalsIgnoreCase("Yes"))
				{
					/*if(mGetPropertyFromFile("bnd_appStatusAuthorized").equalsIgnoreCase("yes"))
					{*/
					mClick("id", mGetPropertyFromFile("bnd_authoRegAppcnAuthorizedid"));
					//}

				}
			}
			else if(appAutho.equalsIgnoreCase("No"))
			{
				if(docAutho.equalsIgnoreCase("Yes"))
				{
					/*if(mGetPropertyFromFile("bnd_appStatusRejectedDueToInadequateInformation").equalsIgnoreCase("yes"))
					{*/
					mClick("id", mGetPropertyFromFile("bnd_authoRegAppcnRejectedDueToInadequateInfoid"));
					appStatusRejectedDueToInadequateInformation=true;
					//}
				}
				else if(docAutho.equalsIgnoreCase("No"))
				{
					if(mGetPropertyFromFile("bnd_appStatusRejectedDueToInadequateDocuments").equalsIgnoreCase("yes"))
					{
						mClick("id", mGetPropertyFromFile("bnd_authoRegAppcnRejectedDueToInadequateDocid"));
						bnd_appStatusRejectedDueToInadequateInformationAndDocuments=true;
					}
					else if(mGetPropertyFromFile("bnd_appStatusRejectedDueToInadequateInformationAndDocuments").equalsIgnoreCase("yes"))
					{
						mClick("id", mGetPropertyFromFile("bnd_authoRegAppcnRejectedDueToInadequateInfo&Docid"));
						bnd_appStatusRejectedDueToInadequateInformationAndDocuments=true;
					}
				}
			}
			
			if(thisServiceName!=(mGetPropertyFromFile("bnd_authoRegDeathCertService")))
			{
				authoDeathDate=driver.findElement(By.id(mGetPropertyFromFile("bnd_authoDeathRegDateid"))).getAttribute("value");
				dDate = authoDeathDate.substring(0, 10);
				//authoBirthDate = mGetText("id", mGetPropertyFromFile("bnd_authoBirthRegDateid"));
				System.out.println("Date of death is : "+dDate);
				mAssert(deathDate, dDate,"Date of death does not match, Expected: "+deathDate+" || Actual: "+dDate);

				authoDeceasedName=driver.findElement(By.id(mGetPropertyFromFile("bnd_authoDeathRegDeceasedNmEngid"))).getAttribute("value");
				System.out.println("Deceased name is : "+authoDeceasedName);
				mAssert(deceasedName, authoDeceasedName,"Deceased name does not match, Expected: "+deceasedName+" || Actual: "+authoDeceasedName);

				authoRelativeName=driver.findElement(By.id(mGetPropertyFromFile("bnd_authoDeathRegRelativeNmEngid"))).getAttribute("value");
				System.out.println("Husband/ Father name is : "+authoRelativeName);
				mAssert(relativeName, authoRelativeName,"Husband/ Father name does not match, Expected: "+relativeName+" || Actual: "+authoRelativeName);

				authoDeceasedMotherName=driver.findElement(By.id(mGetPropertyFromFile("bnd_authoDeathRegMotherNmEngid"))).getAttribute("value");
				System.out.println("Deceased's mother name is : "+authoDeceasedMotherName);
				mAssert(deceasedMotherName, authoDeceasedMotherName,"Deceased's mother name does not match, Expected: "+deceasedMotherName+" || Actual: "+authoDeceasedMotherName);
			}
			mClick("css", mGetPropertyFromFile("bnd_authoRegDeathRegSubmitid"));
			//mClick("xpath", mGetPropertyFromFile("bnd_authoRegDeathRegSubmitid"));
			//Wait for BirthRegNo PopUp
			mWaitForVisible("id", mGetPropertyFromFile("bnd_authoRegProceedid"));
			mGenericWait();

			if(appAutho.equalsIgnoreCase("Yes"))
			{
			//Get Registration No
			DeathRegNo = mGetText("css", mGetPropertyFromFile("bnd_authoRegRegistrationNoid"));
			System.out.println("Death Reg No ="+DeathRegNo);
			mTakeScreenShot();
			mGenericWait();

			String authoSubmitMsg = mGetText("css", mGetPropertyFromFile("bnd_authoSubmitMsgid"));
			String strippedStr = authoSubmitMsg.replace(DeathRegNo, "");
			System.out.println(strippedStr);
			mAssert("Registration Number No.: has been Authorized successfully", strippedStr,"Death Registration authorization message does not match, Expected: Registration Number No.: has been Authorized successfully || Actual: "+strippedStr);
			}
			else if(appAutho.equalsIgnoreCase("No"))
			{
				mTakeScreenShot();
				mGenericWait();
				String authoSubmitMsg = mGetText("css", mGetPropertyFromFile("bnd_authoSubmitMsgid"));
				mAssert(authoSubmitMsg, mGetPropertyFromFile("bnd_unAuthoSubmitMsg"),"Death Registration unauthorization message does not match, Expected: "+mGetPropertyFromFile("bnd_unAuthoSubmitMsg")+"Actual: "+authoSubmitMsg);

			}
			mClick("id", mGetPropertyFromFile("bnd_authoRegProceedid"));

			//Wait for Home Page load
			mGenericWait();

			mWaitForVisible("css", mGetPropertyFromFile("bnd_authoRegSearchid"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in deathRegAuthorization method");
		}
	}


	//sneha solaskar
	// Method for Birth Registration Authorization
	public void deathRegcorrNIsuCertAuthorization(String appAutho,String docAutho)
	{
		try
		{
			if(appStatusRejectedDueToInadequateInformation==true || bnd_appStatusRejectedDueToInadequateInformationAndDocuments==false)

			{
				//mWaitForVisible("xpath", mGetPropertyFromFile("bnd_authoRegDeathRegSubmitid"));
				//For select District
				mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_authoRegDistrictid"), mGetPropertyFromFile("bnd_authoRegDistrict"));
				mGenericWait();

				//For select Taluka
				mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_authoRegTalukaid"), mGetPropertyFromFile("bnd_authoRegTaluka"));
				mGenericWait();

				//For select city
				mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_authoRegCityid"), mGetPropertyFromFile("bnd_authoRegCity"));
				mGenericWait();

				mSelectDropDown("id", mGetPropertyFromFile("bnd_authoDeathRegRegnUnitid"), mGetPropertyFromFile("bnd_authoRegRegnUnit"));
				mGenericWait();

				if(bnd_docAuthorised==false)
				{
					mBndViewAndVerifyDocs(docAutho);
				}
				else
				{
					bnd_docAuthorised=false;
				}

			}

			//	mClick("id", mGetPropertyFromFile("bnd_authoRegAppcnAuthorizedid"));
			if(appAutho.equalsIgnoreCase("Yes"))
			{
				if(docAutho.equalsIgnoreCase("Yes"))
				{
					/*if(mGetPropertyFromFile("bnd_appStatusAuthorized").equalsIgnoreCase("yes"))
					{*/
					mClick("id", mGetPropertyFromFile("bnd_authoRegAppcnAuthorizedid"));
					//}

				}
			}
			else if(appAutho.equalsIgnoreCase("No"))
			{
				if(docAutho.equalsIgnoreCase("Yes"))
				{
					/*if(mGetPropertyFromFile("bnd_appStatusRejectedDueToInadequateInformation").equalsIgnoreCase("yes"))
					{*/
					mClick("id", mGetPropertyFromFile("bnd_authoRegAppcnRejectedDueToInadequateInfoid"));
					appStatusRejectedDueToInadequateInformation=true;
					//}
				}
				else if(docAutho.equalsIgnoreCase("No"))
				{
					if(mGetPropertyFromFile("bnd_appStatusRejectedDueToInadequateDocuments").equalsIgnoreCase("yes"))
					{
						mClick("id", mGetPropertyFromFile("bnd_authoRegAppcnRejectedDueToInadequateDocid"));
						bnd_appStatusRejectedDueToInadequateInformationAndDocuments=true;
					}
					else if(mGetPropertyFromFile("bnd_appStatusRejectedDueToInadequateInformationAndDocuments").equalsIgnoreCase("yes"))
					{
						mClick("id", mGetPropertyFromFile("bnd_authoRegAppcnRejectedDueToInadequateInfo&Docid"));
						bnd_appStatusRejectedDueToInadequateInformationAndDocuments=true;
					}
				}
			}

			mTakeScreenShot();
			mGenericWait();
			mTakeScreenShot();
			//assertion for date of death
			authoDeathDate=driver.findElement(By.name(mGetPropertyFromFile("bnd_DrcnicChngeDateAuthrid"))).getAttribute("value");
			if(authoDeathDate.length()==0)
			{
				System.out.println("No change in date");

			}
			else
			{
				mAssert(authoDeathDate, deathCorrDate, "Death of date does not match, Actual date :"+authoDeathDate+"  Expected Date :"+deathCorrDate);
			}
			// assertion for deceasedname
			authoDeceasedName=driver.findElement(By.id(mGetPropertyFromFile("bnd_DrcnicChngeDeceasdNmeAuthrid"))).getAttribute("value");
			System.out.println("Deceased name is : "+authoDeceasedName);
			if(authoDeceasedName.length()==0)
			{
				System.out.println("No change in deceased name");
			}
			else
			{
				mAssert(authoDeceasedName, deathCorrDeceasedNme, "Deceased Name does not match, Actual :"+authoDeceasedName+"  Expected :"+deathCorrDeceasedNme);
			}
			//assertion for father name
			authoRelativeName=driver.findElement(By.id(mGetPropertyFromFile("bnd_DrcnicChngeFathrNmeAuthrid"))).getAttribute("value");
			System.out.println("Husband/ Father name is : "+authoRelativeName);
			if(authoRelativeName.length()==0)
			{
				System.out.println("No change in father name");
			}
			else
			{
				mAssert(authoRelativeName, deathCorrFatherNme,  "Deceased's Husband/ Father Name does not match, Actual :"+authoRelativeName+"  Expected :"+deathCorrFatherNme);
			}
			//assertion for mother name
			authoDeceasedMotherName=driver.findElement(By.id(mGetPropertyFromFile("bnd_DrcnicChngeMothrNmeAuthrid"))).getAttribute("value");
			System.out.println("Deceased's mother name is : "+authoDeceasedMotherName);
			if(authoDeceasedMotherName.length()==0)
			{
				System.out.println("No change in mother name");
			}
			else
			{
				mAssert(authoDeceasedMotherName, deathCorrMotherNme,  "Deceased's mother Name does not match, Actual :"+authoDeceasedMotherName+"  Expected :"+deathCorrMotherNme);
			}

			//mClick("css", mGetPropertyFromFile("bnd_authoRegDeathRegSubmitid"));
			//submit button
			mWaitForVisible("css", mGetPropertyFromFile("bnd_DrcnicAutherSubmitBtnid"));
			mCustomWait(2000);
			mClick("css", mGetPropertyFromFile("bnd_DrcnicAutherSubmitBtnid"));

			//Wait for BirthRegNo PopUp
			mWaitForVisible("id", mGetPropertyFromFile("bnd_DeathCorrauthoRegProceedid"));
			mGenericWait();

			/*//Get Message after autherization of death Correction
			String msgAftrAuthrztnOfDeathCorr = mGetText("css",mGetPropertyFromFile("bnd_DrcnicMsgAftrAuthrztnOfDeathCorrid"));
			String numberOnly = msgAftrAuthrztnOfDeathCorr.replaceAll("[^0-9]", "");
			appNo = numberOnly;

			String delimiter = appNo;
			String[] LOIMsg = msgAftrAuthrztnOfDeathCorr.split(delimiter);
			System.out.println("msg after birth correction :"+LOIMsg[1].trim());
			DeathRegNo = appNo;
			mTakeScreenShot();
			mGenericWait();*/

			if(appAutho.equalsIgnoreCase("Yes"))
			{
				//Get Message after autherization of death Correction
				String msgAftrAuthrztnOfDeathCorr = mGetText("css",mGetPropertyFromFile("bnd_DrcnicMsgAftrAuthrztnOfDeathCorrid"));
				String numberOnly = msgAftrAuthrztnOfDeathCorr.replaceAll("[^0-9]", "");
				String regNo = numberOnly;
				//appNo = numberOnly;

				String delimiter = regNo;
				String[] LOIMsg = msgAftrAuthrztnOfDeathCorr.split(delimiter);
				System.out.println("msg after birth correction :"+LOIMsg[1].trim());
				DeathRegNo = regNo;
				mTakeScreenShot();
				mGenericWait();

				String authoSubmitMsg = mGetText("css", mGetPropertyFromFile("bnd_authoSubmitMsgid"));
				String strippedStr = authoSubmitMsg.replace(DeathRegNo, "");
				System.out.println(strippedStr);
				mAssert(LOIMsg[1].trim(), mGetPropertyFromFile("bnd_MsgAftrAuthrztnOfDeathCorrid"),"Actual Msg :"+LOIMsg[1].trim()+"  Expected Msg  :"+mGetPropertyFromFile("bnd_MsgAftrAuthrztnOfDeathCorrid"));
			}
			else if(appAutho.equalsIgnoreCase("No"))
			{
				mTakeScreenShot();
				mGenericWait();
				String authoSubmitMsg = mGetText("css", mGetPropertyFromFile("bnd_authoSubmitMsgid"));
				mAssert(authoSubmitMsg, mGetPropertyFromFile("bnd_unAuthoSubmitMsg"),"Death Registration Correction unauthorization message does not match, Expected: "+mGetPropertyFromFile("bnd_unAuthoSubmitMsg")+"Actual: "+authoSubmitMsg);

			}
			mClick("id", mGetPropertyFromFile("bnd_DeathCorrauthoRegProceedid"));

			//Wait for Home Page load
			mGenericWait();
			mWaitForVisible("css", mGetPropertyFromFile("bnd_authoRegSearchid"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in deathRegcorrNIsuCertAuthorization method");
		}
	}


	/**
	 * @author Madhuri.dawande
	 * @since 27-08-2015
	 *
	 */
	// Birth Certificate service
	public void birthCertificate()
	{
		try
		{

			mNavigation("bnd_BNDLinkid", "bnd_birthCertLinkid");

			//Wait to page load - submit button
			mWaitForVisible("xpath", mGetPropertyFromFile("bnd_birthCertSubmitid"));
			mGenericWait();

			searchByCertNoORRegAppnNoORYrAndRegNo(mGetPropertyFromFile("bnd_birthCertCertificateNoid"),mGetPropertyFromFile("bnd_birthCertRegYearid"),mGetPropertyFromFile("bnd_birthCertRegNoid"));

			/*//Enter Year
			mSendKeys("id", mGetPropertyFromFile("bnd_birthCertRegYearid"), mGetPropertyFromFile("bnd_birthCertRegYear"));	
			mGenericWait();

			//Enter Reg No
			//mSendKeys("id", mGetPropertyFromFile("bnd_birthCertRegNoid"), BirthRegNo);	
			mSendKeys("id", mGetPropertyFromFile("bnd_birthCertRegNoid"), mGetPropertyFromFile("bnd_birthCertRegNo"));
			mGenericWait();*/

			//Search Button
			mClick("id", mGetPropertyFromFile("bnd_birthCertSearchid"));
			mGenericWait();

			mTakeScreenShot();
			mscroll(0, 250);

			birthDate = driver.findElement(By.id(mGetPropertyFromFile("bnd_birthCertDOBid"))).getAttribute("value");
			birthDatestr = birthDate.replaceAll("/", "");
			System.out.println("DOB is : "+birthDatestr);


			birthRegChildName = driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegChildNameEngid"))).getAttribute("value");
			System.out.println("Entered child name is :"+birthRegChildName);


			birthRegFatherName = driver.findElement(By.name(mGetPropertyFromFile("bnd_birthCertFatherNmEngid"))).getAttribute("value");
			System.out.println("Entered father name is :"+birthRegFatherName);


			birthRegMotherName = driver.findElement(By.name(mGetPropertyFromFile("bnd_birthCertMotherNmEngid"))).getAttribute("value");
			System.out.println("Entered mother name is :"+birthRegMotherName);

			// Parents name

			parentsName = birthRegMotherName+birthRegFatherName;
			System.out.println("Parents name is :"+parentsName);


			//Applicants Details
			mSelectDropDown("id", mGetPropertyFromFile("bnd_birthCertAppcntTitleid"), mGetPropertyFromFile("bnd_birthCertAppcntTitle"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_appcntFNameid"), mGetPropertyFromFile("bnd_appcntFName"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_appcntMNameid"), mGetPropertyFromFile("bnd_appcntMName"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_appcntLNameid"), mGetPropertyFromFile("bnd_appcntLName"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_birthCertAppcntMobNoid"), mGetPropertyFromFile("bnd_birthCertAppcntMobNo"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_birthCertAppcntEmailid"), mGetPropertyFromFile("bnd_birthCertAppcntEmail"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_birthCertDelModeid"), mGetPropertyFromFile("bnd_birthCertDelMode"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_birthCertNoOfCopiesid"), mGetPropertyFromFile("bnd_birthCertNoOfCopies"));
			mGenericWait();

			String FirstName=driver.findElement(By.id(mGetPropertyFromFile("bnd_appcntFNameid"))).getAttribute("value");
			String MiddleName=driver.findElement(By.id(mGetPropertyFromFile("bnd_appcntMNameid"))).getAttribute("value");
			String LastName=driver.findElement(By.id(mGetPropertyFromFile("bnd_appcntLNameid"))).getAttribute("value");
			entcompname = FirstName+MiddleName+LastName;
			entcompname = entcompname.replaceAll("\\s", "");
			System.out.println(entcompname);

			//calculating no of copies
			//String strnoOfCopy= mGetText("name",mGetPropertyFromFile("bnd_noOfIssuedCopiesid"));
			String strnoOfCopy= driver.findElement(By.name(mGetPropertyFromFile("bnd_noOfIssuedCopiesid"))).getAttribute("value");
			int intnoOfCopy =Integer.valueOf(strnoOfCopy);
			System.out.println("int no copy::"+intnoOfCopy);
			int issuedCopy = Integer.parseInt(mGetPropertyFromFile("bnd_birthCertNoOfCopies"));
			System.out.println("Issued copy::"+issuedCopy);
			noOfCopyForBNDCombo= intnoOfCopy+issuedCopy;
			System.out.println(noOfCopyForBNDCombo);
			//Wait before Radio Button
			mGenericWait();

			// BPL Details

			if(mGetPropertyFromFile("bnd_bplApplicable").equals("Yes"))
			{
				bndBPLRegAutho = true;
				if (driver.findElements(By.id(mGetPropertyFromFile("bnd_bplYesNoid"))).size() != 0)
				{
					if(mGetPropertyFromFile("bplFlag").equals("Yes"))
					{
						mSelectDropDown("id", mGetPropertyFromFile("bnd_bplYesNoid"), mGetPropertyFromFile("bnd_bplYes"));
						mGenericWait();
						mSendKeys("id", mGetPropertyFromFile("bnd_bplNumid"), mGetPropertyFromFile("bnd_bplNum"));
					}
					else
					{
						mSelectDropDown("id", mGetPropertyFromFile("bnd_bplYesNoid"), mGetPropertyFromFile("bnd_bplNo"));
					}
				}
				else
				{
					System.out.println("BPL is not applicable");
				}
			}
			else
			{
				System.out.println("BPL is not configured");
			}

			/*if(mGetPropertyFromFile("bnd_bplApplicable").equals("Yes"))
			{
				if (driver.findElements(By.id(mGetPropertyFromFile("bnd_bplYesNoid"))).size() != 0)
				{
					if(mGetPropertyFromFile("bplFlag").equals("Yes"))
					{
						mGenericWait();
						mUpload("id", mGetPropertyFromFile("bnd_birthCertBPLCardUpldid"), mGetPropertyFromFile("bnd_birthCertBPLCardUpld"));
					}
				}
			}*/	

			if(mGetPropertyFromFile("bnd_bplApplicable").equals("Yes"))
			{
				docUpload("xpath",mGetPropertyFromFile("bnd_birthCertUpldDocTableid"),"uploadDocFlag");
			}
			//Online Offline Radio Check
			/*mClick("id", mGetPropertyFromFile("offlinePaymentModeid"));
			mGenericWait();

			//Bank Details
			mSelectDropDown("id", mGetPropertyFromFile("payByChallanid"), mGetPropertyFromFile("payByChallan"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bankNameid"), mGetPropertyFromFile("bankName"));*/

			if(noOfCopyForBNDCombo<=1)
			{
				System.out.println("Payment is not required");
			}
			else
			{
				common.payment("paymentMode","byBankOrULB");
			}

			//Wait before Submit
			mGenericWait();

			mTakeScreenShot();
			if(!mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Online"))
			{
				//Submit Button
				mClick("xpath", mGetPropertyFromFile("bnd_birthCertSubmitid"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in birthCertificate method");
		}

	}

	// method to check 
	// Birth Certificate proceed
	public void birthCertProceed()
	{
		try
		{
			//appNo=mGetApplicationNo("css",mGetPropertyFromFile("bnd_birthCertAppNoid"));
			mAppNoArray("css",mGetPropertyFromFile("bnd_birthCertAppNoid"));
			appcntName = mGetPropertyFromFile("bnd_appcntFName")+" "+mGetPropertyFromFile("bnd_appcntMName")+" "+mGetPropertyFromFile("bnd_appcntLName");
			//Wait Application no PopUp
			mWaitForVisible("id", mGetPropertyFromFile("bnd_birthCertProceedid"));
			mGenericWait();

			mTakeScreenShot();

			appSubmitMsg = mGetText("css", mGetPropertyFromFile("bnd_appSubmitMsgid"));
			System.out.println(appSubmitMsg);
			//appSubmitMsg = appSubmitMsg.replaceAll("\\D+","");
			strippedString = appSubmitMsg.replace(appNo, "");
			System.out.println(appSubmitMsg);
			//mAssert("Your Application No.: for Birth Certificate has been saved successfully.", strippedString,"Message does not match, Expected: Your Application No.: for Birth Certificate has been saved successfully. || Actual: "+strippedString);

			//Use loop for checking application generation pop-up message -Ritesh 28-10-2015
			if(noOfCopyForBNDCombo<=1)
			{
				mAssert("Your Application No.: for Birth Certificate has been saved successfully.", strippedString,"Message does not match, Expected: Your Application No.: for Birth Certificate has been saved successfully. || Actual: "+strippedString);
			}
			else
			{
				mAssert("Your Application No.: for Birth Certificate has been saved successfully. Proceed for payment.", strippedString,"Message does not match, Expected: Your Application No.: for Birth Certificate has been saved successfully. Proceed for payment. || Actual: "+strippedString);
			}

			//Get Birth Certi Application No
			//		String BirthCertiAppNo = mGetText("css", "b");
			//		System.out.println("Birth Certificate Appln No ="+BirthCertiAppNo);

			//Proceed Button
			mClick("id", mGetPropertyFromFile("bnd_birthCertProceedid"));

			//Wait to Challan Print
			mCustomWait(10000);

			pdfService="challanPdf";
			//mChallanPDFReader(pdfService,appNo, appcntName,"","","","");

			//mChallanPDFReader(appNo, appcntName);
			//ChallanPDFreader();
			mGenericWait();
			//mSwitchTabs();

			if(noOfCopyForBNDCombo<=1)
			{
				System.out.println("Challan PDF reader is not required");
				//mAssert("Your Application No.: for Birth Certificate has been saved successfully.", strippedString,"Message does not match, Expected: Your Application No.: for Birth Certificate has been saved successfully. || Actual: "+strippedString);
			}
			else
			{
				System.out.println("Challan PDF reader is required");
				//mAssert("Your Application No.: for Birth Certificate has been saved successfully. Proceed for payment.", strippedString,"Message does not match, Expected: Your Application No.: for Birth Certificate has been saved successfully. Proceed for payment. || Actual: "+strippedString);
				if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
				{
					newChallanReader();
				}
				else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
				{
					mChallanPDFReader();
				}
			}

			//common.newChallanReader();
			mGenericWait();
			//ULBChallanPDFReader(appNo, appcntName);

			/*mAssert(appNo,printedAppNo,"Application number does not match, browser: "+appNo+" || PDF: "+printedAppNo);
			mAssert(appcntName, applcntName,"Applicant name does not match, browser: "+appcntName+" || PDF: "+applcntName);*/

			//ChallanPDFreader();
			//Switch to original window
			//mSwitchTabs();
			//mGenericWait();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in birthCertProceed method");
		}
	}



	//Printer Grid
	/*public void cfcPrinterGrid()
	{
		try
		{
			mWaitForVisible("linkText", mGetPropertyFromFile("cfc_CFCLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("cfc_CFCLinkid"));
			mWaitForVisible("linkText", mGetPropertyFromFile("cfc_transactionsLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("cfc_transactionsLinkid"));
			//	mWaitForVisible("linkText", mGetPropertyFromFile("cfc_servicesCounterLinkid"));
			//	mGenericWait();
			//	mClick("linkText", mGetPropertyFromFile("cfc_servicesCounterLinkid"));
			mWaitForVisible("linkText", mGetPropertyFromFile("cfc_printerCounterLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("cfc_printerCounterLinkid"));

			//wait for find records 
			mWaitForVisible("css", mGetPropertyFromFile("cfc_printerFindRecordsid"));
			mGenericWait();

			//Click on find records
			mClick("css", mGetPropertyFromFile("cfc_printerFindRecordsid"));
			mGenericWait();
			mSelectDropDown("css", mGetPropertyFromFile("cfc_printerSelectAppNoid"), mGetPropertyFromFile("cfc_printerSelectAppNo"));
			mSelectDropDown("css", mGetPropertyFromFile("cfc_printerSelectEqualid"), mGetPropertyFromFile("cfc_printerSelectEqual"));
			//mSendKeys("id",mGetPropertyFromFile("cfc_printerEnterAppNoid"),appNo);

			IndOrDep("id","cfc_printerEnterAppNoid", "TypeOfExecution");
			//mSendKeys("id",mGetPropertyFromFile("cfc_printerEnterAppNoid"),"78616051900008"); 

			//mSendKeys("id",mGetPropertyFromFile("cfc_printerEnterAppNoid"),"57015100900006"); 

			//wait for find
			mWaitForVisible("id", mGetPropertyFromFile("cfc_printerFindButtonid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("cfc_printerFindButtonid"));

			//wait for print report
			mGenericWait();
			mWaitForVisible("xpath", mGetPropertyFromFile("cfc_printerPrintReportid"));
			mGenericWait();
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("cfc_printerPrintReportid"));
			mCustomWait(10000);


			//mChallanPDFReader(appNo, applicantName);


			birthCertPDFReader(BirthRegNo, birthDatestr, birthRegChildName, parentsName);
			//deathCertPDFReader(DeathRegNo,deathDatestr,deceasedName,deceasedRelativeName);

			//Switch to originl window
			mSwitchTabs();


			//mWaitForVisible("linkText", mGetPropertyFromFile("logoutid"));
			mGenericWait();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	 */
	/**
	 * @author Madhuri.dawande
	 * @since 28-08-2015
	 *
	 */
	// Death Registration service
	public void deathRegistration()
	{
		try
		{
			mWaitForVisible("linkText", mGetPropertyFromFile("bnd_BNDLinkid"));
			mClick("linkText", mGetPropertyFromFile("bnd_BNDLinkid"));
			mWaitForVisible("linkText", mGetPropertyFromFile("bnd_deathRegLinkid"));
			mClick("linkText", mGetPropertyFromFile("bnd_deathRegLinkid"));
			// wait for visible sumbit button
			mWaitForVisible("id", mGetPropertyFromFile("bnd_deathRegSubmitid"));
			//mClick("id", mGetPropertyFromFile("bnd_DODid"));
			//	mClick("linkText", "1");
			//mSendKeys("id", mGetPropertyFromFile("bnd_DODid"), mGetPropertyFromFile("bnd_DOD"));
			mDateSelect("id",mGetPropertyFromFile("bnd_DODid"),mGetPropertyFromFile("bnd_DODDate"));
			//WebElement toClearDOD = mFindElement("id",mGetPropertyFromFile("bnd_DODid"));
			//toClearDOD.sendKeys(Keys.TAB);

			deathDate = driver.findElement(By.id(mGetPropertyFromFile("bnd_DODid"))).getAttribute("value");
			deathDatestr = deathDate.replaceAll("/", "");
			System.out.println("DOD is : "+deathDatestr);

			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegGenderid"), mGetPropertyFromFile("bnd_deathRegGender"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_deathRegDeceasedAgeid"), mGetPropertyFromFile("bnd_deathRegDeceasedAge"));
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegAgePeriodid"), mGetPropertyFromFile("bnd_deathRegAgePeriod"));
			mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("bnd_deathRegDeceasedNmEngid"), mGetPropertyFromFile("bnd_deathRegDeceasedNmEng"));
			mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegDeceasedNmEngid"), mGetPropertyFromFile("bnd_deathRegDeceasedNmEng"));
			mGenericWait();

			deceasedName = driver.findElement(By.id(mGetPropertyFromFile("bnd_deathRegDeceasedNmEngid"))).getAttribute("value");
			System.out.println("Deceased name is : "+deceasedName);

			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegDeceasedNmHinid"), mGetPropertyFromFile("bnd_deathRegDeceasedNmHin"));
			//mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("bnd_deathRegRelativeNmEngid"), mGetPropertyFromFile("bnd_deathRegRelativeNmEng"));
			mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegRelativeNmEngid"), mGetPropertyFromFile("bnd_deathRegRelativeNmEng"));
			mGenericWait();

			relativeName = driver.findElement(By.id(mGetPropertyFromFile("bnd_deathRegRelativeNmEngid"))).getAttribute("value");
			System.out.println("Husband/ Father name is : "+relativeName);

			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegRelativeNmHinid"), mGetPropertyFromFile("bnd_deathRegRelativeNmHin"));
			//mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("bnd_deathRegMotherNmEngid"), mGetPropertyFromFile("bnd_deathRegMotherNmEng"));
			mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegMotherNmEngid"), mGetPropertyFromFile("bnd_deathRegMotherNmEng"));
			mGenericWait();

			deceasedMotherName = driver.findElement(By.id(mGetPropertyFromFile("bnd_deathRegMotherNmEngid"))).getAttribute("value");
			System.out.println("Mother name is : "+deceasedMotherName);

			deceasedRelativeName = relativeName+deceasedMotherName;
			System.out.println("Deceased Relative Name is :"+deceasedRelativeName);

			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegMotherNmHinid"), mGetPropertyFromFile("bnd_deathRegMotherNmHin"));
			//mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegDeathplaceTypeid"), mGetPropertyFromFile("bnd_deathRegDeathplaceType"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegHospitalListid"), mGetPropertyFromFile("bnd_deathRegHospitalList"));
			mGenericWait();

			mTakeScreenShot();

			mscroll(0, 250);

			//mSendKeys("id", mGetPropertyFromFile("bnd_deathRegPermanentAddrEngid"), mGetPropertyFromFile("bnd_deathRegPermanentAddrEng"));
			mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegPermanentAddrEngid"), mGetPropertyFromFile("bnd_deathRegPermanentAddrEng"));
			mGenericWait();
			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegPermanentAddrHinid"), mGetPropertyFromFile("bnd_deathRegPermanentAddrHin"));
			//mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("bnd_deathRegDeathplaceEngid"), mGetPropertyFromFile("bnd_deathRegDeathplaceEng"));
			//mGenericWait();
			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegDeathplaceHinid"), mGetPropertyFromFile("bnd_deathRegDeathplaceHin"));
			//mGenericWait();
			mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_deathRegDistrictid"), mGetPropertyFromFile("bnd_deathRegDistrict"));
			//	mClick("id", "ui-id-8");
			mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_deathRegTalukaid"), mGetPropertyFromFile("bnd_deathRegTaluka"));
			//	mClick("id", "ui-id-9");
			mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_deathRegCityid"), mGetPropertyFromFile("bnd_deathRegCity"));
			mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("bnd_deathRegInformantNmEngid"), mGetPropertyFromFile("bnd_deathRegInformantNmEng"));
			mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegInformantNmEngid"), mGetPropertyFromFile("bnd_deathRegInformantNmEng"));
			mGenericWait();
			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegInformantNmHinid"), mGetPropertyFromFile("bnd_deathRegInformantNmHin"));
			//mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("bnd_deathRegInformantAddrEngid"), mGetPropertyFromFile("bnd_deathRegInformantAddrEng"));
			//mGenericWait();
			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegInformantAddrHinid"), mGetPropertyFromFile("bnd_deathRegInformantAddrHin"));
			//mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegReligionid"), mGetPropertyFromFile("bnd_deathRegReligion"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegMaritalStatid"), mGetPropertyFromFile("bnd_deathRegMaritalStat"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegEducationid"), mGetPropertyFromFile("bnd_deathRegEducation"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegDeathcauseid"), mGetPropertyFromFile("bnd_deathRegDeathcause"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegOccupationid"), mGetPropertyFromFile("bnd_deathRegOccupation"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegRegnUnitid"), mGetPropertyFromFile("bnd_deathRegRegnUnit"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegAttentionTypeid"), mGetPropertyFromFile("bnd_deathRegAttentionType"));
			mGenericWait();

			mTakeScreenShot();
			mscroll(250, 320);

			mClick("id", mGetPropertyFromFile("bnd_deathRegMedicallyCertid"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegDeathcauseListid"), mGetPropertyFromFile("bnd_deathRegDeathcauseList"));
			mGenericWait();
			mSelectDropDown("name", mGetPropertyFromFile("bnd_deathRegDeathMannerid"), mGetPropertyFromFile("bnd_deathRegDeathManner"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_deathRegDeathMediSuprid"), mGetPropertyFromFile("bnd_deathRegDeathMediSupr"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_deathRegIntervalOnsetid"), mGetPropertyFromFile("bnd_deathRegIntervalOnset"));
			mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("bnd_deathRegPostmortemDtid"), mGetPropertyFromFile("bnd_deathRegPostmortemDt"));
			//WebElement toClear = mFindElement("id",mGetPropertyFromFile("bnd_deathRegPostmortemDtid"));
			//toClear.sendKeys(Keys.TAB);
			mGdatePicker(mGetPropertyFromFile("bnd_deathRegPostmortemDtid"),mGetPropertyFromFile("bnd_deathRegPostmortemDtYear"),mGetPropertyFromFile("bnd_deathRegPostmortemDtMonth"),mGetPropertyFromFile("bnd_deathRegPostmortemDtDate"));
			mGenericWait();
			//driver.findElement(By.id("bnd_deathRegPostmortemDtid")).sendKeys(Keys.TAB);
			mGenericWait();
			mSendKeys("name", mGetPropertyFromFile("bnd_deathRegAttendantNmid"), mGetPropertyFromFile("bnd_deathRegAttendantNm"));
			mGenericWait();
			mSendKeys("name", mGetPropertyFromFile("bnd_deathRegMedCertOtherid"), mGetPropertyFromFile("bnd_deathRegMedCertOther"));
			mGenericWait();
			//mCustomWait(10000);
			//WebElement element = mFindElement("xpath", "//fieldset[4]/div/div/label[2]/input");
			//	WebElement element = driver.findElement(By("element_path"));
			//Actions actions = new Actions(driver);

			//actions.moveToElement(element).click().perform();
			//	mClick("id", "within");
			//	mClick("xpath", "//fieldset[4]/div/div/label/input");
			// Within ULB Area
			mClick("xpath", mGetPropertyFromFile("bnd_deathRegWithinULBAreaid"));
			// Outside ULB Area
			//	mClick("xpath", "//fieldset[4]/div/div/label[2]/input");

			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegCemetryListid"), mGetPropertyFromFile("bnd_deathRegCemetryList"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegTitleid"), mGetPropertyFromFile("bnd_deathRegTitle"));
			mSendKeys("id", mGetPropertyFromFile("bnd_appcntFNameid"), mGetPropertyFromFile("bnd_appcntFName"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_appcntMNameid"), mGetPropertyFromFile("bnd_appcntMName"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_appcntLNameid"), mGetPropertyFromFile("bnd_appcntLName"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_deathRegMobNoid"), mGetPropertyFromFile("bnd_deathRegMobNo"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_deathRegEmailid"), mGetPropertyFromFile("bnd_deathRegEmail"));
			mGenericWait();

			mTakeScreenShot();
			mscroll(320, 400);

			//mUpload("id", mGetPropertyFromFile("bnd_deathRegUploadDoc6id"), mGetPropertyFromFile("bnd_deathRegUploadDoc6"));
			//mUploadDeathRegistration(mGetPropertyFromFile("bnd_deathRegUploadDoc6"));
			docUpload("xpath","tableName", "upldDocStatus");
			mGenericWait();
			//	mUpload("id", mGetPropertyFromFile("bnd_deathRegUploadDoc2id"), mGetPropertyFromFile("bnd_deathRegUploadDoc2"));
			//	mGenericWait();

			/*mClick("id", mGetPropertyFromFile("offlinePaymentModeid"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("payByChallanid"), mGetPropertyFromFile("payByChallan"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bankNameid"), mGetPropertyFromFile("bankName"));
			mGenericWait();*/

			if((dateIncr>=0 && dateIncr<=21))
			{

				System.out.println("Payment is not required");
			}
			else
			{
				System.out.println("Payment is required");
				common.payment("paymentMode","byBankOrULB");
			}

			mTakeScreenShot();
			mClick("id", mGetPropertyFromFile("bnd_deathRegSubmitid"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	// Death Registration proceed
	public void deathRegProceed()
	{
		try
		{
			mWaitForVisible("id", mGetPropertyFromFile("bnd_deathRegProceedid"));
			appNo=mGetApplicationNo("css",mGetPropertyFromFile("bnd_deathRegAppNoid"));
			appcntName = mGetPropertyFromFile("bnd_appcntFName")+" "+mGetPropertyFromFile("bnd_appcntMName")+" "+mGetPropertyFromFile("bnd_appcntLName");
			mTakeScreenShot();

			appSubmitMsg = mGetText("css", mGetPropertyFromFile("bnd_appSubmitMsgid"));
			strippedString = appSubmitMsg.replace(appNo, "");
			System.out.println(strippedString);
			//mAssert("Your Application No.: for Registration of Death has been saved successfully. Proceed for payment.", strippedString,"Message does not match, Expected: Your Application No.: for Registration of Death has been saved successfully. Proceed for payment. || Actual: "+strippedString);

			//Ritesh
			if((dateIncr>=0 && dateIncr<=21))
			{

				mAssert("Your Application No.: for Registration of Death has been saved successfully.", strippedString,"Message does not match, Expected: Your Application No.: for Registration of Death has been saved successfully. || Actual: "+strippedString);
			}
			else
			{
				mAssert("Your Application No.: for Registration of Death has been saved successfully. Proceed for payment.", strippedString,"Message does not match, Expected: Your Application No.: for Registration of Death has been saved successfully. Proceed for payment. || Actual: "+strippedString);
			}

			mClick("id", mGetPropertyFromFile("bnd_deathRegProceedid"));


			//Wait to Challan Print
			mCustomWait(10000);

			pdfService="challanPdf";
			//	mChallanPDFReader(pdfService,appNo,appcntName,"","","","");

			//mChallanPDFReader(appNo,appcntName);


			/*mAssert(appNo,printedAppNo,"Application number does not match, browser: "+appNo+" || PDF: "+printedAppNo);
			mAssert(appcntName, applcntName,"Applicant name does not match, browser: "+appcntName+" || PDF: "+applcntName);*/

			//ChallanPDFreader();
			//Switch to original window
			//mSwitchTabs();
			mGenericWait();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @author Madhuri.dawande
	 * @since 29-08-2015
	 *
	 */
	// Death Certificate service
	public void deathCertificate()
	{
		try
		{

			/*mWaitForVisible("linkText", mGetPropertyFromFile("bnd_BNDLinkid"));
			mClick("linkText", mGetPropertyFromFile("bnd_BNDLinkid"));
			mWaitForVisible("linkText", mGetPropertyFromFile("bnd_deathCertLinkid"));
			mClick("linkText", mGetPropertyFromFile("bnd_deathCertLinkid"));*/

			mNavigation("bnd_BNDLinkid", "bnd_deathCertLinkid");

			//	wait for submit button
			//mWaitForVisible("css", mGetPropertyFromFile("bnd_deathCertSubmitid"));
			mWaitForVisible("xpath", mGetPropertyFromFile("bnd_deathCertSubmitid"));

			searchByCertNoORRegAppnNoORYrAndRegNo(mGetPropertyFromFile("bnd_deathCertCertificateNoid"), mGetPropertyFromFile("bnd_deathCertRegYearid"),mGetPropertyFromFile("bnd_deathCertRegNoid"));

			/*mSendKeys("id", mGetPropertyFromFile("bnd_deathCertRegYearid"), mGetPropertyFromFile("bnd_deathCertRegYear"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_deathCertRegNoid"), mGetPropertyFromFile("bnd_deathCertRegNo"));*/
			//mSendKeys("id", mGetPropertyFromFile("bnd_deathCertRegNoid"), DeathRegNo);
			mWaitForVisible("id", mGetPropertyFromFile("bnd_deathCertSearchid"));
			mClick("id", mGetPropertyFromFile("bnd_deathCertSearchid"));

			mTakeScreenShot();
			mscroll(0, 250);

			deathDate = driver.findElement(By.id(mGetPropertyFromFile("bnd_deathCertDODid"))).getAttribute("value");
			deathDatestr = deathDate.replaceAll("/", "");
			System.out.println("DOD is : "+deathDatestr);

			deceasedName = driver.findElement(By.id(mGetPropertyFromFile("bnd_deathRegDeceasedNmEngid"))).getAttribute("value");
			System.out.println("Deceased name is : "+deceasedName);

			relativeName = driver.findElement(By.id(mGetPropertyFromFile("bnd_deathRegRelativeNmEngid"))).getAttribute("value");
			System.out.println("Husband/ Father name is : "+relativeName);

			deceasedMotherName = driver.findElement(By.id(mGetPropertyFromFile("bnd_deathRegMotherNmEngid"))).getAttribute("value");
			System.out.println("Mother name is : "+deceasedMotherName);

			deceasedRelativeName = relativeName+deceasedMotherName;
			System.out.println("Deceased Relative Name is :"+deceasedRelativeName);

			mSendKeys("id", mGetPropertyFromFile("bnd_deathCertNoOfCopiesid"), mGetPropertyFromFile("bnd_deathCertNoOfCopies"));	
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathCertAppcntTitleid"), mGetPropertyFromFile("bnd_deathCertAppcntTitle"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_appcntFNameid"), mGetPropertyFromFile("bnd_appcntFName"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_appcntMNameid"), mGetPropertyFromFile("bnd_appcntMName"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_appcntLNameid"), mGetPropertyFromFile("bnd_appcntLName"));
			mGenericWait();

			//Ri
			String FirstName=driver.findElement(By.id(mGetPropertyFromFile("bnd_appcntFNameid"))).getAttribute("value");
			String MiddleName=driver.findElement(By.id(mGetPropertyFromFile("bnd_appcntMNameid"))).getAttribute("value");
			String LastName=driver.findElement(By.id(mGetPropertyFromFile("bnd_appcntLNameid"))).getAttribute("value");
			entcompname = FirstName+MiddleName+LastName;
			entcompname = entcompname.replaceAll("\\s", "");
			System.out.println(entcompname);
			//

			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_deathCertAppcntMobNoid"), mGetPropertyFromFile("bnd_deathCertAppcntMobNo"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_deathCertAppcntEmailid"), mGetPropertyFromFile("bnd_deathCertAppcntEmail"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathCertDelModeid"), mGetPropertyFromFile("bnd_deathCertDelMode"));
			mGenericWait();
			/*mClick("id", mGetPropertyFromFile("offlinePaymentModeid"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("payByChallanid"), mGetPropertyFromFile("payByChallan"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bankNameid"), mGetPropertyFromFile("bankName"));
			mGenericWait();*/

			/*String strnoOfCopy= mGetText("name",mGetPropertyFromFile("bnd_noOfIssuedCopiesid"));
			int intnoOfCopy =Integer.parseInt(strnoOfCopy);
			int issuedCopy = Integer.parseInt(mGetPropertyFromFile("bnd_deathCertNoOfCopies"));
			noOfCopyForBNDCombo= intnoOfCopy+issuedCopy;
			System.out.println(noOfCopyForBNDCombo);*/


			String strnoOfCopy= driver.findElement(By.name(mGetPropertyFromFile("bnd_noOfIssuedCopiesid"))).getAttribute("value");
			int intnoOfCopy =Integer.valueOf(strnoOfCopy);
			System.out.println("int no copy::"+intnoOfCopy);
			int issuedCopy = Integer.parseInt(mGetPropertyFromFile("bnd_deathCertNoOfCopies"));
			System.out.println("Issued copy::"+issuedCopy);
			noOfCopyForBNDCombo= intnoOfCopy+issuedCopy;
			System.out.println(noOfCopyForBNDCombo);

			// BPL Details

			if(mGetPropertyFromFile("bnd_bplApplicable").equals("Yes"))
			{
				bndBPLRegAutho=true;
				if (driver.findElements(By.id(mGetPropertyFromFile("bnd_bplYesNoid"))).size() != 0)
				{
					if(mGetPropertyFromFile("bplFlag").equals("Yes"))
					{
						mSelectDropDown("id", mGetPropertyFromFile("bnd_bplYesNoid"), mGetPropertyFromFile("bnd_bplYes"));
						mGenericWait();
						//mSendKeys("name", mGetPropertyFromFile("bnd_bplNumid"), mGetPropertyFromFile("bnd_bplNum"));
						//mSendKeys("id", "alphaNumBplNO", mGetPropertyFromFile("bnd_bplNum"));
						driver.findElement(By.name("bplNo")).sendKeys("BPL123456789");
					}
					else
					{
						mSelectDropDown("id", mGetPropertyFromFile("bnd_bplYesNoid"), mGetPropertyFromFile("bnd_bplNo"));
					}
				}
				else
				{
					System.out.println("BPL is not applicable");
				}
			}
			else
			{
				System.out.println("BPL is not configured");
			}

			/*if(mGetPropertyFromFile("bnd_bplApplicable").equals("Yes"))
			{
				if (driver.findElements(By.id(mGetPropertyFromFile("bnd_bplYesNoid"))).size() != 0)
				{
					if(mGetPropertyFromFile("bplFlag").equals("Yes"))
					{
						mGenericWait();
						mUpload("id", mGetPropertyFromFile("bnd_deathCertBPLCardUpldid"), mGetPropertyFromFile("bnd_deathCertBPLCardUpld"));
						mGenericWait();
						mUpload("id", mGetPropertyFromFile("bnd_deathCertRationCardUpldid"), mGetPropertyFromFile("bnd_deathCertRationCardUpld"));
					}
				}
			}*/

			if(mGetPropertyFromFile("bnd_bplApplicable").equals("Yes"))
			{
				docUpload("xpath",mGetPropertyFromFile("bnd_deathCertUpldDocTableid"),"uploadDocFlag");
			}

			if(noOfCopyForBNDCombo<=1)
			{
				System.out.println("Payment is not required");
			}
			else
			{
				common.payment("paymentMode","byBankOrULB");
			}


			mTakeScreenShot();
			if(!mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Online"))
			{
				//mClick("css", mGetPropertyFromFile("bnd_deathCertSubmitid"));
				mClick("xpath", mGetPropertyFromFile("bnd_deathCertSubmitid"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in deathCertificate method");
		}
	}

	// Death Certificate proceed
	public void deathCertProcced()
	{
		try
		{
			mWaitForVisible("id", mGetPropertyFromFile("bnd_deathCertProceedid"));
			//appNo=mGetApplicationNo("css",mGetPropertyFromFile("bnd_deathCertAppNoid"));
			mAppNoArray("css",mGetPropertyFromFile("bnd_deathCertAppNoid"));
			appcntName = mGetPropertyFromFile("bnd_appcntFName")+" "+mGetPropertyFromFile("bnd_appcntMName")+" "+mGetPropertyFromFile("bnd_appcntLName");

			mTakeScreenShot();

			appSubmitMsg = mGetText("css", mGetPropertyFromFile("bnd_appSubmitMsgid"));
			strippedString = appSubmitMsg.replace(appNo, "");
			System.out.println(strippedString);
			//mAssert("Your Application No.: for Death Certificate has been saved successfully.", strippedString,"Message does not match, Expected: Your Application No.: for Death Certificate has been saved successfully. || Actual: "+strippedString);

			//Ritesh
			if(noOfCopyForBNDCombo<=1)
			{
				mAssert("Your Application No.: for Death Certificate has been saved successfully.", strippedString,"Message does not match, Expected: Your Application No.: for Death Certificate has been saved successfully. || Actual: "+strippedString);
			}
			else
			{
				mAssert("Your Application No.: for Death Certificate has been saved successfully. Proceed for payment.", strippedString,"Message does not match, Expected: Your Application No.: for Death Certificate has been saved successfully. Proceed for payment. || Actual: "+strippedString);
			}


			mClick("id", mGetPropertyFromFile("bnd_deathCertProceedid"));

			//Wait to Challan Print
			mCustomWait(10000);

			pdfService="challanPdf";
			//mChallanPDFReader(pdfService,appNo,appcntName,"","","","");

			//mChallanPDFReader(appNo,appcntName);


			/*mAssert(appNo,printedAppNo,"Application number does not match, browser: "+appNo+" || PDF: "+printedAppNo);
			mAssert(appcntName, applcntName,"Applicant name does not match, browser: "+appcntName+" || PDF: "+applcntName);*/
			//ChallanPDFreader();
			//Switch to original window
			//mSwitchTabs();
			//mGenericWait();

			if(noOfCopyForBNDCombo<=1)
			{
				System.out.println("Challan PDF reader is not required");
			}
			else
			{
				System.out.println("Challan PDF reader is required");

				if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
				{
					newChallanReader();
				}
				else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
				{
					mChallanPDFReader();
				}
			}		


		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in deathCertProcced method");
		}

	}


	// Birth Registration Certificate service
	public void birthRegCertificate(){
		try
		{
			mNavigation("bnd_BNDLinkid", "bnd_birthRegCertLinkid");

			// Wait for visible 'Submit' button
			mWaitForVisible("id", mGetPropertyFromFile("bnd_birthRegSubmitid"));

			mGenericWait();
			//mClick("css",mGetPropertyFromFile("bnd_leftShift"));

			// Birth Details
			//mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegTypeid"), mGetPropertyFromFile("bnd_birthRegType"));
			//mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("bnd_birthRegDOBid"), mGetPropertyFromFile("bnd_birthRegDOB"));
			//WebElement toClearDate = mFindElement("id",mGetPropertyFromFile("bnd_birthRegDOBid"));
			//toClearDate.sendKeys(Keys.TAB);


			//mDatePicker(mGetPropertyFromFile("bnd_birthRegDOBid"),mGetPropertyFromFile("bnd_birthRegCertDOBYear"),mGetPropertyFromFile("bnd_birthRegCertDOBMonth"),mGetPropertyFromFile("bnd_birthRegCertDOBDate"));
			mDateSelect("id",mGetPropertyFromFile("bnd_birthRegDOBid"),mGetPropertyFromFile("bnd_birthRegDOBDate"));

			//birthDate = mGetText("id", mGetPropertyFromFile("bnd_birthRegDOBid"));
			birthDate = driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegDOBid"))).getAttribute("value");
			birthDatestr = birthDate.replaceAll("/", "");
			System.out.println("DOB is : "+birthDatestr);


			//	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			//	birthdtstr = dateFormat.format(birthDate);


			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegGenderid"), mGetPropertyFromFile("bnd_birthRegGender"));
			//birthGender = driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegGenderid"))).getText();
			birthGender =mCaptureSelectedValue("id", mGetPropertyFromFile("bnd_birthRegGenderid"));
			System.out.println("Gender is : "+birthGender);
			mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("bnd_birthRegWeightid"), mGetPropertyFromFile("bnd_birthRegWeight"));

			// Birth Weight validation
			String birthwtmsg;
			mSendKeys("id", mGetPropertyFromFile("bnd_birthRegWeightid"), mGetPropertyFromFile("bnd_birthRegWeight"));  
			mTab("id", mGetPropertyFromFile("bnd_birthRegWeightid"));
			if((Float.parseFloat(mGetPropertyFromFile("bnd_birthRegWeight"))>(Float.parseFloat("0.5")) && ((Float.parseFloat(mGetPropertyFromFile("bnd_birthRegWeight"))<(Float.parseFloat("10"))))))
			{	
				System.out.println("The entered weight is allowed");
				birthWeight=driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegWeightid"))).getAttribute("value");
			}
			else if((Float.parseFloat(mGetPropertyFromFile("bnd_birthRegWeight"))<(Float.parseFloat("0.5"))))
			{	
				birthwtmsg = driver.findElement(By.cssSelector("p")).getText();
				System.out.println(birthwtmsg);
				System.out.println("Enter the correct weight ");
				mWaitForVisible("css", "body > div.fancybox-overlay.fancybox-overlay-fixed > div > div > a");
				driver.findElement(By.cssSelector("body > div.fancybox-overlay.fancybox-overlay-fixed > div > div > a")).click();
				System.out.println("The correct weight is getting entered" ); 
				mSendKeys("id", mGetPropertyFromFile("bnd_birthRegWeightid"), "3");
				birthWeight=driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegWeightid"))).getAttribute("value");
			}
			else if((Float.parseFloat(mGetPropertyFromFile("bnd_birthRegWeight"))>(Float.parseFloat("10"))))
			{	  
				birthwtmsg = driver.findElement(By.cssSelector("p")).getText();
				System.out.println(birthwtmsg);
				System.out.println("Enter the correct weight ");
				mWaitForVisible("css", "body > div.fancybox-overlay.fancybox-overlay-fixed > div > div > a");
				driver.findElement(By.cssSelector("body > div.fancybox-overlay.fancybox-overlay-fixed > div > div > a")).click();
				System.out.println("The correct weight is getting entered" ); 
				mSendKeys("id", mGetPropertyFromFile("bnd_birthRegWeightid"), "4.5");
				birthWeight=driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegWeightid"))).getAttribute("value");
			}

			mGenericWait();

			// Child Birth Details
			//mSendKeys("id", mGetPropertyFromFile("bnd_birthRegChildNameEngid"), mGetPropertyFromFile("bnd_birthRegChildNameEng"));
			mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegChildNameEngid"), mGetPropertyFromFile("bnd_birthRegChildNameEng"));
			//birthRegChildName = mGetText("id", mGetPropertyFromFile("bnd_birthRegChildNameEngid"));
			birthRegChildName = driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegChildNameEngid"))).getAttribute("value");
			System.out.println("Entered child name is :"+birthRegChildName);
			String name=mGetPropertyFromFile("bnd_birthRegChildNameEng");

			/*char[] cArray = name.toCharArray();
			//name[0];
			int i;
			System.out.println("Length of String is: " + mGetPropertyFromFile("bnd_birthRegChildNameEng").length());

			for(i=0;i<= mGetPropertyFromFile("bnd_birthRegChildNameEng").length()-1; i++)
			{
				System.out.println(cArray[i]);
				String str=Character.toString(cArray[i]);
				Thread.sleep(200);
				driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegChildNameEngid"))).sendKeys(str);

			}*/


			mGenericWait();
			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegChildNameHinid"), mGetPropertyFromFile("bnd_birthRegChildNameHin"));
			//mGenericWait();
			//mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegPlaceTypeid"), mGetPropertyFromFile("bnd_birthRegPlaceType"));
			//mWaitForVisible("id",mGetPropertyFromFile("bnd_hospitalid"));
			//mGenericWait();
			//mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegHospitalid"), mGetPropertyFromFile("bnd_birthRegHospital"));

			if(mGetPropertyFromFile("bnd_birthRegPlaceType").equals("Hospital")||mGetPropertyFromFile("bnd_birthRegPlaceType").equals("Hospital/Institute"))
			{
				mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegPlaceTypeid"), mGetPropertyFromFile("bnd_birthRegPlaceType"));
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegHospitalid"), mGetPropertyFromFile("bnd_birthRegHospital"));
			}
			else if(mGetPropertyFromFile("bnd_birthRegPlaceType").equals("Not Stated"))
			{
				mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegPlaceTypeid"), mGetPropertyFromFile("bnd_birthRegPlaceType"));
				mGenericWait();
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegPlaceOfBirthid"), mGetPropertyFromFile("bnd_birthRegPlaceOfBirth"));
				mGenericWait();
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegBirthAddrid"), mGetPropertyFromFile("bnd_birthRegBirthAddr"));
				mGenericWait();
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegInformantAddrid"), mGetPropertyFromFile("bnd_birthRegInformantAddr"));

			}
			else
			{
				mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegPlaceTypeid"), mGetPropertyFromFile("bnd_birthRegPlaceType"));
				mGenericWait();
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegPlaceOfBirthid"), mGetPropertyFromFile("bnd_birthRegPlaceOfBirth"));
				mGenericWait();
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegBirthAddrid"), mGetPropertyFromFile("bnd_birthRegBirthAddr"));
			}


			mGenericWait();
			//mSendKeys("name", mGetPropertyFromFile("bnd_birthRegInformantNmEngid"), mGetPropertyFromFile("bnd_birthRegInformantNmEng"));
			mHindiTextConversion("name", mGetPropertyFromFile("bnd_birthRegInformantNmEngid"), mGetPropertyFromFile("bnd_birthRegInformantNmEng"));
			mGenericWait();
			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegInformantNmHinid"), mGetPropertyFromFile("bnd_birthRegInformantNmHin"));
			//mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("bnd_birthRegInformantAddrEngid"), mGetPropertyFromFile("bnd_birthRegInformantAddrEng"));
			//mGenericWait();
			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegInformantAddrHinid"), mGetPropertyFromFile("bnd_birthRegInformantAddrHin"));
			//mGenericWait();
			System.out.println("Class name in birth reg cert"+myClassName);
			mTakeScreenShot();

			mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegAttentionTypeid"), mGetPropertyFromFile("bnd_birthRegAttentionType"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegDeliveryMethodid"), mGetPropertyFromFile("bnd_birthRegDeliveryMethod"));
			mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("bnd_birthRegPregnancyDurationid"), mGetPropertyFromFile("bnd_birthRegPregnancyDuration"));
			//mGenericWait();

			// Pregnancy Duration validation
			String pregDurnMsg;
			mSendKeys("id", mGetPropertyFromFile("bnd_birthRegPregnancyDurationid"), mGetPropertyFromFile("bnd_birthRegPregnancyDuration"));  
			mTab("id", mGetPropertyFromFile("bnd_birthRegPregnancyDurationid"));
			if((Integer.parseInt(mGetPropertyFromFile("bnd_birthRegPregnancyDuration"))<(Integer.parseInt("45")) && ((Integer.parseInt(mGetPropertyFromFile("bnd_birthRegPregnancyDuration"))>(Integer.parseInt("24"))))))

			{	
				System.out.println("The entered Pregnancy Duration is allowed");
			}

			else if((Integer.parseInt(mGetPropertyFromFile("bnd_birthRegPregnancyDuration"))<(Integer.parseInt("24"))))


			{	

				pregDurnMsg = driver.findElement(By.cssSelector("body > div.fancybox-overlay.fancybox-overlay-fixed > div > div > div > div > div > p")).getText();
				System.out.println(pregDurnMsg);
				System.out.println("Enter the correct Pregnancy Duration");
				mWaitForVisible("css", "body > div.fancybox-overlay.fancybox-overlay-fixed > div > div > a");
				driver.findElement(By.cssSelector("body > div.fancybox-overlay.fancybox-overlay-fixed > div > div > a")).click();
				System.out.println("The correct Pregnancy Duration is getting entered" ); 
				mSendKeys("id", mGetPropertyFromFile("bnd_birthRegPregnancyDurationid"), "30");
			}

			else if((Integer.parseInt(mGetPropertyFromFile("bnd_birthRegPregnancyDuration"))>(Integer.parseInt("45"))))
			{	  

				pregDurnMsg = driver.findElement(By.cssSelector("body > div.fancybox-overlay.fancybox-overlay-fixed > div > div > div > div > div > p")).getText();
				System.out.println(pregDurnMsg);
				System.out.println("Enter the correct Pregnancy Duration");
				mWaitForVisible("css", "body > div.fancybox-overlay.fancybox-overlay-fixed > div > div > a");
				driver.findElement(By.cssSelector("body > div.fancybox-overlay.fancybox-overlay-fixed > div > div > a")).click();
				System.out.println("The correct Pregnancy Duration is getting entered" ); 
				mSendKeys("id", mGetPropertyFromFile("bnd_birthRegPregnancyDurationid"), "43");
			}

			//mscroll(0, 250);

			// Parent Details
			mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegFatherNmEngid"), mGetPropertyFromFile("bnd_birthRegFatherNmEng"));
			birthRegFatherName = driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegFatherNmEngid"))).getAttribute("value");
			System.out.println("Entered father name is :"+birthRegFatherName);
			mGenericWait();
			if(mGetPropertyFromFile("bnd_birthRegFatherNmEngid")!=(""))
			{
				mSendKeys("id", mGetPropertyFromFile("bnd_birthRegFatherUIDNoid"), mGetPropertyFromFile("bnd_birthRegFatherUIDNo"));
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegFatherEducationid"), mGetPropertyFromFile("bnd_birthRegFatherEducation"));
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegFatherOccupationid"), mGetPropertyFromFile("bnd_birthRegFatherOccupation"));
				mGenericWait();
			}

			mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegMotherNmEngid"), mGetPropertyFromFile("bnd_birthRegMotherNmEng"));
			birthRegMotherName = driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegMotherNmEngid"))).getAttribute("value");
			System.out.println("Entered mother name is :"+birthRegMotherName);

			// Parents name
			parentsName = birthRegMotherName+birthRegFatherName;
			System.out.println("Parents name is :"+parentsName);

			mGenericWait();

			mSendKeys("id", mGetPropertyFromFile("bnd_birthRegMotherUIDNoid"), mGetPropertyFromFile("bnd_birthRegMotherUIDNo"));
			mGenericWait();
			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegMotherNmHinid"), mGetPropertyFromFile("bnd_birthRegMotherNmHin"));
			//mGenericWait();
			mSelectDropDown("id",mGetPropertyFromFile("bnd_birthRegMotherEducationid"), mGetPropertyFromFile("bnd_birthRegMotherEducation"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegMotherOccupationid"), mGetPropertyFromFile("bnd_birthRegMotherOccupation"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_birthRegAgeAtMarriageid"), mGetPropertyFromFile("bnd_birthRegAgeAtMarriage"));	
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_birthRegAgeAtBirthid"), mGetPropertyFromFile("bnd_birthRegAgeAtBirth"));	
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_birthRegNoLiveChildrenid"), mGetPropertyFromFile("bnd_birthRegNoLiveChildren"));	
			mGenericWait();
			//mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegReligionid"), mGetPropertyFromFile("bnd_birthRegReligion"));
			//mGenericWait();

			// Select/Enter Religion

			if(mGetPropertyFromFile("bnd_birthRegReligion").equalsIgnoreCase("Other"))
			{
				mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegReligionid"), mGetPropertyFromFile("bnd_birthRegReligion"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("bnd_birthRegOtherReligionid"), mGetPropertyFromFile("bnd_birthRegOtherReligion"));
			}
			else
			{
				mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegReligionid"), mGetPropertyFromFile("bnd_birthRegReligion"));
				mGenericWait();
			}

			//mSendKeys("id", mGetPropertyFromFile("bnd_birthRegParentAddrAtBirthEngid"), mGetPropertyFromFile("bnd_birthRegParentAddrAtBirthEng"));
			if(mGetPropertyFromFile("bnd_birthRegPlaceType").equals("Hospital")||mGetPropertyFromFile("bnd_birthRegPlaceType").equals("Hospital/Institute"))
			{
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegParentAddrAtBirthEngid"), mGetPropertyFromFile("bnd_birthRegParentAddrAtBirthEng"));
				mGenericWait();
			}
			else if(mGetPropertyFromFile("bnd_birthRegPlaceType").equals("Not Stated"))
			{
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegParentAddrAtBirthEngid"), mGetPropertyFromFile("bnd_birthRegParentAddrAtBirthEng"));
				mGenericWait();
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegPermanentAddrid"), mGetPropertyFromFile("bnd_birthRegPermanentAddr"));
				mGenericWait();
			}

			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegParentAddrAtBirthHinid"), mGetPropertyFromFile("bnd_birthRegParentAddrAtBirthHin"));
			//mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("bnd_birthRegPermanentAddrEngid"), mGetPropertyFromFile("bnd_birthRegPermanentAddrEng"));
			//mGenericWait();
			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_birthRegPermanentAddrHinid"), mGetPropertyFromFile("bnd_birthRegPermanentAddrHin"));
			//mGenericWait();
			mscroll(0, 350);
			//mTakeScreenShot();

			// Mother Address

			if (mGetPropertyFromFile("motherAddrFlag").equals("In India"))
			{
				mClick("id", mGetPropertyFromFile("bnd_birthRegMotherAddrInIndiaid"));
				mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_birthRegDistrictid"), mGetPropertyFromFile("bnd_birthRegDistrict"));
				mGenericWait();
				mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_birthRegBlockid"), mGetPropertyFromFile("bnd_birthRegBlock"));
				mGenericWait();
				mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_birthRegCityid"), mGetPropertyFromFile("bnd_birthRegCity"));
				mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegRuralUrbanAreaid"), mGetPropertyFromFile("bnd_birthRegRuralUrbanArea"));
				mGenericWait();
			}
			else
			{
				mClick("id", mGetPropertyFromFile("bnd_birthRegMotherAddrOutOfIndiaid"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("bnd_birthRegMotherAddrOutOfIndiaTextid"), mGetPropertyFromFile("bnd_birthRegMotherAddrOutOfIndiaText"));
			}
			mscroll(250, 350);

			// BPL Details

			if(mGetPropertyFromFile("bnd_bplApplicable").equals("Yes"))
			{
				bndBPLRegAutho = true;
				String bndBPLNum;
				if (driver.findElements(By.id(mGetPropertyFromFile("bnd_bplYesNoid"))).size() != 0)
				{
					if(mGetPropertyFromFile("bplFlag").equals("Yes"))
					{
						mSelectDropDown("id", mGetPropertyFromFile("bnd_bplYesNoid"), mGetPropertyFromFile("bnd_bplYes"));
						mGenericWait();
						bndBPLTypeValue = driver.findElement(By.id(mGetPropertyFromFile("bnd_bplYesNoid"))).getAttribute("value");
						System.out.println("Selected BPL type is : "+bndBPLTypeValue);
						mSendKeys("id", mGetPropertyFromFile("bnd_bplNumid"), mGetPropertyFromFile("bnd_bplNum"));
						bndBPLNum = driver.findElement(By.id(mGetPropertyFromFile("bnd_bplNumid"))).getAttribute("value");
						System.out.println("Entered BPL number is : "+bndBPLNum);

					}
					else
					{
						mSelectDropDown("id", mGetPropertyFromFile("bnd_bplYesNoid"), mGetPropertyFromFile("bnd_bplNo"));
						bndBPLTypeValue = driver.findElement(By.id(mGetPropertyFromFile("bnd_bplYesNoid"))).getAttribute("value");
						System.out.println("Selected BPL type is "+bndBPLTypeValue);
					}
				}
				else
				{
					System.out.println("BPL is not applicable");
				}
			}
			else
			{
				System.out.println("BPL is not configured");
			}

			// Applicant Details
			mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegAppcntTitleid"), mGetPropertyFromFile("bnd_birthRegAppcntTitle"));
			mSendKeys("id", mGetPropertyFromFile("bnd_appcntFNameid"), mGetPropertyFromFile("bnd_appcntFName"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_appcntMNameid"), mGetPropertyFromFile("bnd_appcntMName"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_appcntLNameid"), mGetPropertyFromFile("bnd_appcntLName"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_birthRegAppcntMobNoid"), mGetPropertyFromFile("bnd_birthRegAppcntMobNo"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_birthRegAppcntEmailid"), mGetPropertyFromFile("bnd_birthRegAppcntEmail"));
			mGenericWait();

			//Ri
			String FirstName=driver.findElement(By.id(mGetPropertyFromFile("bnd_appcntFNameid"))).getAttribute("value");
			String MiddleName=driver.findElement(By.id(mGetPropertyFromFile("bnd_appcntMNameid"))).getAttribute("value");
			String LastName=driver.findElement(By.id(mGetPropertyFromFile("bnd_appcntLNameid"))).getAttribute("value");
			entcompname = FirstName+MiddleName+LastName;
			entcompname = entcompname.replaceAll("\\s", "");
			System.out.println(entcompname);
			//

			mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegCertDeliveryModeid"), mGetPropertyFromFile("bnd_birthRegCertDeliveryMode"));
			mGenericWait();

			if(mGetPropertyFromFile("bnd_birthRegCertDeliveryMode").equals("By Post/Courier"))
			{
				isByPost = true;
			}

			docUpload("xpath",mGetPropertyFromFile("bnd_birthRegCertUpldDocTableid"),"uploadDocFlag");

			// Upload Documents
			//	mUploadBirthRegAndCert(mGetPropertyFromFile("bnd_birthRegUploadDoc2"));

			mscroll(350,450);
			/*
			if(mGetPropertyFromFile("bnd_bplApplicable").equals("Yes"))
			{
				if (driver.findElements(By.id(mGetPropertyFromFile("bnd_bplYesNoid"))).size() != 0)
				{
					if(mGetPropertyFromFile("bplFlag").equals("Yes"))
					{
						mGenericWait();
						mUpload("id", mGetPropertyFromFile("bnd_birthRegCertBPLCardUpldid"), mGetPropertyFromFile("bnd_birthRegCertBPLCardUpld"));
					}
				}
			}
			 */
			//mUpload("id", mGetPropertyFromFile("bnd_birthRegUploadDoc2id"), mGetPropertyFromFile("bnd_birthRegUploadDoc2"));
			mGenericWait();
			//mUpload("id", mGetPropertyFromFile("bnd_birthRegUploadDoc4id"), mGetPropertyFromFile("bnd_birthRegUploadDoc4"));
			//	mGenericWait();

			// Enter number of copies for birth certificate
			//mSendKeys("id", mGetPropertyFromFile("bnd_birthRegCertNoOfCopiesid"), mGetPropertyFromFile("bnd_birthRegCertNoOfCopies"));

			/*WebElement toClear = mFindElement("id",mGetPropertyFromFile("bnd_birthRegCertNoOfCopiesid"));
			toClear.sendKeys(Keys.TAB);*/
			/*Robot robot=new Robot();

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);*/
			
			driver.findElement(By.id("noOfCopies")).sendKeys(Keys.BACK_SPACE);
			mGenericWait();
			driver.findElement(By.id("noOfCopies")).sendKeys(mGetPropertyFromFile("bnd_birthRegCertNoOfCopies"));
			driver.findElement(By.id("noOfCopies")).sendKeys(Keys.TAB);

			String noOfCopyInBirth =mGetPropertyFromFile("bnd_birthRegCertNoOfCopies");
			noOfCopyForBNDCombo= Integer.valueOf(noOfCopyInBirth);

			System.out.println("Class Name is:::"+thisClassName);
			// Offline payment
			/*	mWaitForVisible("id", mGetPropertyFromFile("offlinePaymentModeid"));
			mClick("id", mGetPropertyFromFile("offlinePaymentModeid"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("payByChallanid"), mGetPropertyFromFile("payByChallan"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bankNameid"), mGetPropertyFromFile("bankName"));
			mGenericWait();*/



			/*if(isByPost)
			{
				System.out.println("Payment is required");
				common.payment("paymentMode","byBankOrULB");
			}
			if((dateIncr>=0 && dateIncr<=21))
			{
				if(noOfCopyForBNDCombo<2)
				{
					System.out.println("Payment is not required");
				}
				else
				{
					System.out.println("Payment is required");
					common.payment("paymentMode","byBankOrULB");
				}

			}
			else
			{
				System.out.println("Payment is required");
				common.payment("paymentMode","byBankOrULB");

			}*/



			if(isByPost||(dateIncr>21)||(noOfCopyForBNDCombo>1))
			{
				System.out.println("Payment is required");
				common.payment("paymentMode","byBankOrULB");
			}
			else
			{
				System.out.println("Payment is not required");
			}


			mCustomWait(3000);
			mTakeScreenShot();
			mCustomWait(3000);

			if(!mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Online"))
			{
				// Click 'Submit' button
				mClick("id", mGetPropertyFromFile("bnd_birthRegSubmitid"));


				/*WebElement element = driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegSubmitid")));

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);*/

				//	driver.findElement(By.id(mGetPropertyFromFile("bnd_birthRegSubmitid"))).sendKeys(Keys.ENTER);

				mCustomWait(1000);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in birthRegCertificate method");
		}

	}


	// Birth Registration Certificate proceed
	public void birthRegCertProceed()
	{
		try
		{
			mWaitForVisible("css",mGetPropertyFromFile("bnd_birthRegAppNoid"));
			//	appNo=mGetApplicationNo("css",mGetPropertyFromFile("bnd_birthRegAppNoid"));
			mAppNoArray("css",mGetPropertyFromFile("bnd_birthRegAppNoid"));
			appcntName = mGetPropertyFromFile("bnd_appcntFName")+" "+mGetPropertyFromFile("bnd_appcntMName")+" "+mGetPropertyFromFile("bnd_appcntLName");
			mCustomWait(5000);
			appSubmitMsg = mGetText("css", mGetPropertyFromFile("bnd_appSubmitMsgid"));
			//appSubmitMsg = mGetText("css", mGetPropertyFromFile("bnd_appSubmitMsgid"),"value");
			strippedString = appSubmitMsg.replace(appNo, "");
			System.out.println(strippedString);

			//mAssert("Your Application No.: for Birth Registration and Certificate Issuance has been saved successfully. Proceed for payment.", strippedString,"Message does not match, Expected: Your Application No.: for Birth Registration and Certificate Issuance has been saved successfully. Proceed for payment. || Actual: "+strippedString);

			//Assert Ritesh
			/*if((dateIncr>=0 && dateIncr<=21))
			{
				if(noOfCopyForBNDCombo<2)*/
			if(isByPost||(dateIncr>21)||(noOfCopyForBNDCombo>1))
			{
				/*System.out.println("Payment is required");
				common.payment("byBank");*/
				mAssert("Your Application No.: for Birth Registration and Certificate Issuance has been saved successfully. Proceed for payment.", strippedString,"Message does not match, Expected: Your Application No.: for Birth Registration and Certificate Issuance has been saved successfully. Proceed for payment. || Actual: "+strippedString);

			}
			else
			{
				//System.out.println("Payment is not required");
				mAssert("Your Application No.: for Birth Registration and Certificate Issuance has been saved successfully.", strippedString,"Message does not match, Expected: Your Application No.: for Birth Registration and Certificate Issuance has been saved successfully. || Actual: "+strippedString);
			}


			/*}
			else
			{
				System.out.println("Payment is required");
				common.payment("byBank");
				mAssert("Your Application No.: for Birth Registration and Certificate Issuance has been saved successfully. Proceed for payment.", strippedString,"Message does not match, Expected: Your Application No.: for Birth Registration and Certificate Issuance has been saved successfully. Proceed for payment. || Actual: "+strippedString);

			}*/

			// Wait for visible 'Proceed' button
			mWaitForVisible("id", mGetPropertyFromFile("bnd_birthRegProceedid"));
			mGenericWait();

			System.out.println("Class name in birht reg cert proceed"+myClassName);
			mTakeScreenShot();

			//String submitMsg =appSubmitMsg.substring(40,133);
			//System.out.println(submitMsg);
			//mAssert("Birth Registration and Certificate Issuance has been saved successfully. Proceed for payment.", submitMsg,"Message does not match, Expected: Birth Registration and Certificate Issuance has been saved successfully. Proceed for payment. || Actual: "+submitMsg);

			// Click 'Proceed' button			
			mClick("id", mGetPropertyFromFile("bnd_birthRegProceedid"));
			mCustomWait(10000);

			// Click 'Print Reciept/Challan' button		
			if(mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("department"))
			{
				mTakeScreenShot();
				mGenericWait();
				mClick("css", mGetPropertyFromFile("bnd_printRecieptChallanid"));
			}
			else
			{
				System.out.println("Citizen or Agency login");
			}
			
			
			pdfService="challanPdf";

			if((dateIncr>=0 && dateIncr<=21))
			{
				if(noOfCopyForBNDCombo<2 && isByPost==false)
				{
					System.out.println("Challan PDF reader is not required");
					//mAssert("Your Application No.: for Birth Registration and Certificate Issuance has been saved successfully.", strippedString,"Message does not match, Expected: Your Application No.: for Birth Registration and Certificate Issuance has been saved successfully. || Actual: "+strippedString);
				}
				else
				{
					System.out.println("Challan PDF reader is required");
					//common.payment("byBank");
					//mAssert("Your Application No: for Birth Registration and Certificate Issuance has been saved successfully. Proceed for payment.", strippedString,"Message does not match, Expected: Your Application No.: for Birth Registration and Certificate Issuance has been saved successfully. Proceed for payment. || Actual: "+strippedString);
					if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
					{
						newChallanReader();
					}
					else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
					{
						mChallanPDFReader();
					}
				}
			}
			else
			{
				System.out.println("Challan PDF reader is required");
				//common.payment("byBank");
				//mAssert("Your Application No.: for Birth Registration and Certificate Issuance has been saved successfully. Proceed for payment.", strippedString,"Message does not match, Expected: Your Application No.: for Birth Registration and Certificate Issuance has been saved successfully. Proceed for payment. || Actual: "+strippedString);
				if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
				{
					newChallanReader();
				}
				else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
				{
					mChallanPDFReader();
				}
			}
			
			// Click 'Print Form' button		
			if(mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("department"))
			{
				mClick("css", mGetPropertyFromFile("bnd_printFormid"));
				mGenericWait();
				Set<String> winids = driver.getWindowHandles();

				Iterator<String> iterate = winids.iterator();

				System.out.println(winids);

				String firstwindow=iterate.next();
				String secondwindow = iterate.next();
				String thirdwindow = iterate.next();
				System.out.println(firstwindow);  
				System.out.println(secondwindow);
				System.out.println(thirdwindow);
				//switch to pdf window 
				driver.switchTo().window(thirdwindow);
				System.err.println("In HTML Reader");
				Thread.sleep(3000);	

				mTakeScreenShot();

				Thread.sleep(3000);
				
				driver.close();

				driver.switchTo().window(secondwindow);
				
			}
			else
			{
				System.out.println("Citizen or Agency login");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in birthRegCertProceed method");
		}
	}



	// Death Registration Certificate service
	public void deathRegCertificate()
	{
		try
		{
			mNavigation("bnd_BNDLinkid", "bnd_deathRegCertLinkid");

			// wait for visible sumbit button
			mWaitForVisible("id", mGetPropertyFromFile("bnd_deathRegSubmitid"));
			//mClick("id", mGetPropertyFromFile("bnd_DODid"));
			//	mClick("linkText", "1");
			//mSendKeys("id", mGetPropertyFromFile("bnd_DODid"), mGetPropertyFromFile("bnd_DOD"));
			//WebElement toClearDOD = mFindElement("id",mGetPropertyFromFile("bnd_DODid"));
			//toClearDOD.sendKeys(Keys.TAB);
			mDateSelect("id",mGetPropertyFromFile("bnd_DODid"),mGetPropertyFromFile("bnd_DODCertDate"));
			mGenericWait();

			deathDate = driver.findElement(By.id(mGetPropertyFromFile("bnd_DODid"))).getAttribute("value");
			deathDatestr = deathDate.replaceAll("/", "");
			System.out.println("DOD is : "+deathDatestr);

			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegGenderid"), mGetPropertyFromFile("bnd_deathRegGender"));
			mGenericWait();
			/*mSendKeys("id", mGetPropertyFromFile("bnd_deathRegDeceasedAgeid"), mGetPropertyFromFile("bnd_deathRegDeceasedAge"));
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegAgePeriodid"), mGetPropertyFromFile("bnd_deathRegAgePeriod"));
			mGenericWait();*/

			deceasedAge(mGetPropertyFromFile("bnd_deathRegDeceasedAge"),mGetPropertyFromFile("bnd_deathRegAgePeriod"));
			mGenericWait();

			//mSendKeys("id", mGetPropertyFromFile("bnd_deathRegDeceasedNmEngid"), mGetPropertyFromFile("bnd_deathRegDeceasedNmEng"));
			mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegDeceasedNmEngid"), mGetPropertyFromFile("bnd_deathRegDeceasedNmEng"));
			mGenericWait();

			deceasedName = driver.findElement(By.id(mGetPropertyFromFile("bnd_deathRegDeceasedNmEngid"))).getAttribute("value");
			System.out.println("Deceased name is : "+deceasedName);

			mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegDeceasedUIDNoid"), mGetPropertyFromFile("bnd_deathRegDeceasedUIDNo"));
			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegDeceasedNmHinid"), mGetPropertyFromFile("bnd_deathRegDeceasedNmHin"));
			//mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("bnd_deathRegRelativeNmEngid"), mGetPropertyFromFile("bnd_deathRegRelativeNmEng"));
			mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegRelativeNmEngid"), mGetPropertyFromFile("bnd_deathRegRelativeNmEng"));
			mGenericWait();
			mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegRelativeUIDNoid"), mGetPropertyFromFile("bnd_deathRegRelativeUIDNo"));
			relativeName = driver.findElement(By.id(mGetPropertyFromFile("bnd_deathRegRelativeNmEngid"))).getAttribute("value");
			System.out.println("Husband/ Father name is : "+relativeName);

			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegRelativeNmHinid"), mGetPropertyFromFile("bnd_deathRegRelativeNmHin"));
			//mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("bnd_deathRegMotherNmEngid"), mGetPropertyFromFile("bnd_deathRegMotherNmEng"));
			mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegMotherNmEngid"), mGetPropertyFromFile("bnd_deathRegMotherNmEng"));
			mGenericWait();
			mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegMotherUIDNoid"), mGetPropertyFromFile("bnd_deathRegMotherUIDNo"));
			mTakeScreenShot();
			deceasedMotherName = driver.findElement(By.id(mGetPropertyFromFile("bnd_deathRegMotherNmEngid"))).getAttribute("value");
			System.out.println("Mother name is : "+deceasedMotherName);

			deceasedRelativeName = relativeName+deceasedMotherName;
			System.out.println("Deceased Relative Name is :"+deceasedRelativeName);

			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegMotherNmHinid"), mGetPropertyFromFile("bnd_deathRegMotherNmHin"));
			//mGenericWait();


			/*mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegDeathplaceTypeid"), mGetPropertyFromFile("bnd_deathRegDeathplaceType"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegHospitalListid"), mGetPropertyFromFile("bnd_deathRegHospitalList"));
			mGenericWait();*/

			if(mGetPropertyFromFile("bnd_deathRegDeathplaceType").equals("Hospital")||mGetPropertyFromFile("bnd_deathRegDeathplaceType").equals("Hospital/Institute"))
			{
				mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegDeathplaceTypeid"), mGetPropertyFromFile("bnd_deathRegDeathplaceType"));
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegHospitalListid"), mGetPropertyFromFile("bnd_deathRegHospitalList"));
				mGenericWait();
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegPermanentAddrEngid"), mGetPropertyFromFile("bnd_deathRegPermanentAddrEng"));

			}
			else if(mGetPropertyFromFile("bnd_deathRegDeathplaceType").equals("Not Stated"))
			{
				mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegDeathplaceTypeid"), mGetPropertyFromFile("bnd_deathRegDeathplaceType"));
				mGenericWait();
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegDeathPlaceid"), mGetPropertyFromFile("bnd_deathRegDeathPlace"));
				mGenericWait();
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegDeathAddrid"), mGetPropertyFromFile("bnd_deathRegDeathAddr"));
				mGenericWait();
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegPermanentAddrEngid"), mGetPropertyFromFile("bnd_deathRegPermanentAddrEng"));
				mGenericWait();
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegAddrAtDeathTimeid"), mGetPropertyFromFile("bnd_deathRegAddrAtDeathTime"));
				mGenericWait();
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegInformantAddrEngid"), mGetPropertyFromFile("bnd_deathRegInformantAddrEng"));

			}
			else
			{
				mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegDeathplaceTypeid"), mGetPropertyFromFile("bnd_deathRegDeathplaceType"));
				mGenericWait();
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegDeathPlaceid"), mGetPropertyFromFile("bnd_deathRegDeathPlace"));
				mGenericWait();
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegDeathAddrid"), mGetPropertyFromFile("bnd_deathRegDeathAddr"));
			}


			mTakeScreenShot();
			mscroll(0, 250);

			//mSendKeys("id", mGetPropertyFromFile("bnd_deathRegPermanentAddrEngid"), mGetPropertyFromFile("bnd_deathRegPermanentAddrEng"));




			//mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegPermanentAddrHinid"), mGetPropertyFromFile("bnd_deathRegPermanentAddrHin"));
			//mGenericWait();
			//	mSendKeys("id", mGetPropertyFromFile("bnd_deathRegDeathplaceEngid"), mGetPropertyFromFile("bnd_deathRegDeathplaceEng"));
			//	mGenericWait();
			//	mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegDeathplaceHinid"), mGetPropertyFromFile("bnd_deathRegDeathplaceHin"));
			//	mGenericWait();
			mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_deathRegDistrictid"), mGetPropertyFromFile("bnd_deathRegDistrict"));
			//	mClick("id", "ui-id-8");
			mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_deathRegTalukaid"), mGetPropertyFromFile("bnd_deathRegTaluka"));
			//	mClick("id", "ui-id-9");
			mSelectAutoCompleteText("id", mGetPropertyFromFile("bnd_deathRegCityid"), mGetPropertyFromFile("bnd_deathRegCity"));
			mGenericWait();
			//	mSendKeys("id", mGetPropertyFromFile("bnd_deathRegInformantNmEngid"), mGetPropertyFromFile("bnd_deathRegInformantNmEng"));
			mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegInformantNmEngid"), mGetPropertyFromFile("bnd_deathRegInformantNmEng"));
			mGenericWait();
			//	mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegInformantNmHinid"), mGetPropertyFromFile("bnd_deathRegInformantNmHin"));
			//	mGenericWait();
			//	mSendKeys("id", mGetPropertyFromFile("bnd_deathRegInformantAddrEngid"), mGetPropertyFromFile("bnd_deathRegInformantAddrEng"));
			//	mGenericWait();
			//	mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegInformantAddrHinid"), mGetPropertyFromFile("bnd_deathRegInformantAddrHin"));
			//	mGenericWait();
			/*mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegReligionid"), mGetPropertyFromFile("bnd_deathRegReligion"));
			mGenericWait();*/

			// Select/Enter Religion
			if(mGetPropertyFromFile("bnd_deathRegReligion").equalsIgnoreCase("Other"))
			{
				mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegReligionid"), mGetPropertyFromFile("bnd_deathRegReligion"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("bnd_deathRegOtherReligionid"), mGetPropertyFromFile("bnd_deathRegOtherReligion"));
			}
			else
			{
				mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegReligionid"), mGetPropertyFromFile("bnd_deathRegReligion"));
				mGenericWait();
			}

			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegMaritalStatid"), mGetPropertyFromFile("bnd_deathRegMaritalStat"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegEducationid"), mGetPropertyFromFile("bnd_deathRegEducation"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegDeathcauseid"), mGetPropertyFromFile("bnd_deathRegDeathcause"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegOccupationid"), mGetPropertyFromFile("bnd_deathRegOccupation"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegRegnUnitid"), mGetPropertyFromFile("bnd_deathRegRegnUnit"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegAttentionTypeid"), mGetPropertyFromFile("bnd_deathRegAttentionType"));
			mGenericWait();

			mTakeScreenShot();
			mscroll(250, 320);

			patientHistory();

			/*mClick("id", mGetPropertyFromFile("bnd_deathRegMedicallyCertid"));
			mGenericWait();

			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegDeathcauseListid"), mGetPropertyFromFile("bnd_deathRegDeathcauseList"));
			mGenericWait();
			mSelectDropDown("name", mGetPropertyFromFile("bnd_deathRegDeathMannerid"), mGetPropertyFromFile("bnd_deathRegDeathManner"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_deathRegDeathMediSuprid"), mGetPropertyFromFile("bnd_deathRegDeathMediSupr"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_deathRegIntervalOnsetid"), mGetPropertyFromFile("bnd_deathRegIntervalOnset"));
			mGenericWait();*/
			//mSendKeys("id", mGetPropertyFromFile("bnd_deathRegPostmortemDtid"), mGetPropertyFromFile("bnd_deathRegPostmortemDt"));
			//mGenericWait();
			//WebElement toClear = mFindElement("id",mGetPropertyFromFile("bnd_deathRegPostmortemDtid"));
			//toClear.sendKeys(Keys.TAB);
			//	mGdatePicker(mGetPropertyFromFile("bnd_deathRegPostmortemDtid"),mGetPropertyFromFile("bnd_deathRegPostmortemDtYear"),mGetPropertyFromFile("bnd_deathRegPostmortemDtMonth"),mGetPropertyFromFile("bnd_deathRegPostmortemDtDate"));
			mDateSelect("id", mGetPropertyFromFile("bnd_deathRegPostmortemDtid"), mGetPropertyFromFile("bnd_deathRegPostmortemDtDate"));
			driver.findElement(By.id("entity.medicalMaster.mcVerifnDate")).sendKeys(Keys.TAB);
			mGenericWait();
			mSendKeys("name", mGetPropertyFromFile("bnd_deathRegAttendantNmid"), mGetPropertyFromFile("bnd_deathRegAttendantNm"));
			mGenericWait();
			mSendKeys("name", mGetPropertyFromFile("bnd_deathRegMedCertOtherid"), mGetPropertyFromFile("bnd_deathRegMedCertOther"));
			mGenericWait();
			mTakeScreenShot();
			//	WebElement element = mFindElement("xpath", "//fieldset[4]/div/div/label[2]/input");
			//	WebElement element = driver.findElement(By("element_path"));
			//	Actions actions = new Actions(driver);

			//	actions.moveToElement(element).click().perform();
			// Within ULB Area
			//mClick("xpath", mGetPropertyFromFile("bnd_deathRegWithinULBAreaid"));
			if(mGetPropertyFromFile("bnd_cemeteryDetails").equals("Within ULB Area"))
			{
				mClick("id", mGetPropertyFromFile("bnd_deathRegWithinULBAreaid"));
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegCemetryListid"), mGetPropertyFromFile("bnd_deathRegCemetryList"));
			}
			else
			{
				mClick("id", mGetPropertyFromFile("bnd_deathRegOutsideULBAreaid"));
				mGenericWait();
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegCemeteryNameid"), mGetPropertyFromFile("bnd_deathRegCemeteryName"));
				mGenericWait();
				mHindiTextConversion("id", mGetPropertyFromFile("bnd_deathRegCemeteryAddressid"), mGetPropertyFromFile("bnd_deathRegCemeteryAddress"));
			}
			// Outside ULB Area
			//	mClick("xpath", "//fieldset[4]/div/div/label[2]/input");

			/*mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegCemetryListid"), mGetPropertyFromFile("bnd_deathRegCemetryList"));*/
			mGenericWait();

			// BPL Details

			if(mGetPropertyFromFile("bnd_bplApplicable").equals("Yes"))
			{
				if (driver.findElements(By.id(mGetPropertyFromFile("bnd_bplYesNoid"))).size() != 0)
				{
					if(mGetPropertyFromFile("bplFlag").equals("Yes"))
					{
						mSelectDropDown("id", mGetPropertyFromFile("bnd_bplYesNoid"), mGetPropertyFromFile("bnd_bplYes"));
						mGenericWait();
						mSendKeys("id", mGetPropertyFromFile("bnd_bplNumid"), mGetPropertyFromFile("bnd_bplNum"));
					}
					else
					{
						mSelectDropDown("id", mGetPropertyFromFile("bnd_bplYesNoid"), mGetPropertyFromFile("bnd_bplNo"));
					}
				}
				else
				{
					System.out.println("BPL is not applicable");
				}
			}
			else
			{
				System.out.println("BPL is not configured");
			}

			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegTitleid"), mGetPropertyFromFile("bnd_deathRegTitle"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_appcntFNameid"), mGetPropertyFromFile("bnd_appcntFName"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_appcntMNameid"), mGetPropertyFromFile("bnd_appcntMName"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_appcntLNameid"), mGetPropertyFromFile("bnd_appcntLName"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_deathRegMobNoid"), mGetPropertyFromFile("bnd_deathRegMobNo"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_deathRegEmailid"), mGetPropertyFromFile("bnd_deathRegEmail"));
			mGenericWait();

			//Ri
			String FirstName=driver.findElement(By.id(mGetPropertyFromFile("bnd_appcntFNameid"))).getAttribute("value");
			String MiddleName=driver.findElement(By.id(mGetPropertyFromFile("bnd_appcntMNameid"))).getAttribute("value");
			String LastName=driver.findElement(By.id(mGetPropertyFromFile("bnd_appcntLNameid"))).getAttribute("value");
			entcompname = FirstName+MiddleName+LastName;
			entcompname = entcompname.replaceAll("\\s", "");
			System.out.println(entcompname);
			//

			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegCertDelModeid"), mGetPropertyFromFile("bnd_deathRegCertDelMode"));
			mGenericWait();

			mTakeScreenShot();
			mscroll(320, 400);

			//mUpload("id", mGetPropertyFromFile("bnd_deathRegUploadDoc2id"), mGetPropertyFromFile("bnd_deathRegUploadDoc2"));
			//mUploadDeathRegAndCert(mGetPropertyFromFile("bnd_deathRegUploadDoc2"));
			mGenericWait();
			/*
			if(mGetPropertyFromFile("bnd_bplApplicable").equals("Yes"))
			{
				if (driver.findElements(By.id(mGetPropertyFromFile("bnd_bplYesNoid"))).size() != 0)
				{
					if(mGetPropertyFromFile("bplFlag").equals("Yes"))
					{
						mGenericWait();
						mUpload("id", mGetPropertyFromFile("bnd_deathRegCertBPLCardUpldid"), mGetPropertyFromFile("bnd_deathRegCertBPLCardUpld"));
					}
				}
			}*/

			docUpload("xpath",mGetPropertyFromFile("bnd_deathRegCertUpldDocTableid"),"uploadDocFlag");

			mSendKeys("id", mGetPropertyFromFile("bnd_deathRegCertNoOfCopiesid"), mGetPropertyFromFile("bnd_deathRegCertNoOfCopies"));
			mGenericWait();

			String noOfCopyInBirth =mGetPropertyFromFile("bnd_deathRegCertNoOfCopies");
			noOfCopyForBNDCombo= Integer.valueOf(noOfCopyInBirth);

			/*mClick("id", mGetPropertyFromFile("offlinePaymentModeid"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("payByChallanid"), mGetPropertyFromFile("payByChallan"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bankNameid"), mGetPropertyFromFile("bankName"));
			mGenericWait();*/

			/*if((dateIncr>=0 && dateIncr<=21))
			{
				if(noOfCopyForBNDCombo<2)
				{
					System.out.println("Payment is not required");
				}
				else
				{
					System.out.println("Payment is required");
					common.payment("paymentMode","byBankOrULB");
				}

			}
			else
			{
				System.out.println("Payment is required");
				common.payment("paymentMode","byBankOrULB");

			}*/

			if(isByPost||(dateIncr>21)||(noOfCopyForBNDCombo>1))
			{
				System.out.println("Payment is required");
				common.payment("paymentMode","byBankOrULB");
			}
			else
			{
				System.out.println("Payment is not required");
			}

			mTakeScreenShot();
			if(!mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Online"))
			{
				mClick("id", mGetPropertyFromFile("bnd_deathRegSubmitid"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in deathRegCertificate method");
		}
	}

	// Death Registration proceed
	public void deathRegCertProceed()
	{
		try
		{
			mWaitForVisible("id", mGetPropertyFromFile("bnd_deathRegProceedid"));


			//appNo=mGetApplicationNo("css",mGetPropertyFromFile("bnd_deathRegAppNoid"));
			mAppNoArray("css",mGetPropertyFromFile("bnd_deathRegAppNoid"));
			appcntName = mGetPropertyFromFile("bnd_appcntFName")+" "+mGetPropertyFromFile("bnd_appcntMName")+" "+mGetPropertyFromFile("bnd_appcntLName");

			appSubmitMsg = mGetText("css", mGetPropertyFromFile("bnd_appSubmitMsgid"));
			strippedString = appSubmitMsg.replace(appNo, "");
			System.out.println(strippedString);

			//mAssert("Your Application No.: for Death Registration and Certificate Issuance has been saved successfully. Proceed for payment.", strippedString,"Message does not match, Expected: Your Application No.: for Death Registration and Certificate Issuance has been saved successfully. Proceed for payment. || Actual: "+strippedString);

			/*if((dateIncr>=0 && dateIncr<=21))
			{
				if(noOfCopyForBNDCombo<2)*/
			if(isByPost||(dateIncr>21)||(noOfCopyForBNDCombo>1))
			{
				/*System.out.println("Payment is required");
				common.payment("byBank");*/
				mAssert("Your Application No.: for Death Registration and Certificate Issuance has been saved successfully. Proceed for payment.", strippedString,"Message does not match, Expected: Your Application No.: for Death Registration and Certificate Issuance has been saved successfully. Proceed for payment. || Actual: "+strippedString);
			}
			else
			{
				//System.out.println("Payment is not required");
				mAssert("Your Application No.: for Death Registration and Certificate Issuance has been saved successfully.", strippedString,"Message does not match, Expected: Your Application No.: for Death Registration and Certificate Issuance has been saved successfully. || Actual: "+strippedString);
			}

			/*}
			else
			{
				System.out.println("Payment is required");
				common.payment("byBank");
				mAssert("Your Application No.: for Death Registration and Certificate Issuance has been saved successfully. Proceed for payment.", strippedString,"Message does not match, Expected: Your Application No.: for Death Registration and Certificate Issuance has been saved successfully. Proceed for payment. || Actual: "+strippedString);
			}*/


			mTakeScreenShot();

			mClick("id", mGetPropertyFromFile("bnd_deathRegProceedid"));

			//Wait to Challan Print
			mCustomWait(10000);
			
			// Click 'Print Reciept/Challan' button		
						if(mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("department"))
						{
							mTakeScreenShot();
							mGenericWait();
							mClick("css", mGetPropertyFromFile("bnd_printRecieptChallanid"));
						}
						else
						{
							System.out.println("Citizen or Agency login");
						}

			pdfService="challanPdf";
			//mChallanPDFReader(pdfService,appNo,appcntName,"","","","");


			//mChallanPDFReader(appNo,appcntName);


			if((dateIncr>=0 && dateIncr<=21))
			{
				if(noOfCopyForBNDCombo<2)
				{
					System.out.println("Challan PDF reader is not required");
					//mAssert("Your Application No.: for Birth Registration and Certificate Issuance has been saved successfully.", strippedString,"Message does not match, Expected: Your Application No.: for Birth Registration and Certificate Issuance has been saved successfully. || Actual: "+strippedString);
				}
				else
				{
					System.out.println("Challan PDF reader is required");
					//common.payment("byBank");
					//mAssert("Your Application No.: for Birth Registration and Certificate Issuance has been saved successfully. Proceed for payment.", strippedString,"Message does not match, Expected: Your Application No.: for Birth Registration and Certificate Issuance has been saved successfully. Proceed for payment. || Actual: "+strippedString);

					if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
					{
						newChallanReader();
					}
					else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
					{
						mChallanPDFReader();
					}
				}


			}
			else
			{
				System.out.println("Challan PDF reader is required");
				//common.payment("byBank");
				//mAssert("Your Application No.: for Birth Registration and Certificate Issuance has been saved successfully. Proceed for payment.", strippedString,"Message does not match, Expected: Your Application No.: for Birth Registration and Certificate Issuance has been saved successfully. Proceed for payment. || Actual: "+strippedString);

				if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
				{
					newChallanReader();
				}
				else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
				{
					mChallanPDFReader();
				}
			}
			
			// Click 'Print Form' button		
						if(mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("department"))
						{
							mClick("css", mGetPropertyFromFile("bnd_printFormid"));
							mGenericWait();
							Set<String> winids = driver.getWindowHandles();

							Iterator<String> iterate = winids.iterator();

							System.out.println(winids);

							String firstwindow=iterate.next();
							String secondwindow = iterate.next();
							String thirdwindow = iterate.next();
							System.out.println(firstwindow);  
							System.out.println(secondwindow);
							System.out.println(thirdwindow);
							//switch to pdf window 
							driver.switchTo().window(thirdwindow);
							System.err.println("In HTML Reader");
							Thread.sleep(3000);	

							mTakeScreenShot();

							Thread.sleep(3000);
							
							driver.close();

							driver.switchTo().window(secondwindow);
							
						}
						else
						{
							System.out.println("Citizen or Agency login");
						}


			/*mAssert(appNo,printedAppNo,"Application number does not match, browser: "+appNo+" || PDF: "+printedAppNo);
			mAssert(appcntName, applcntName,"Applicant name does not match, browser: "+appcntName+" || PDF: "+applcntName);*/


			//ChallanPDFreader();
			//Switch to original window
			//mSwitchTabs();
			mGenericWait();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in deathRegCertProceed method");
		}
	}

	/*<<<<<<< .mine
	public void ChallanPDFreaderold()
	{
=======
	//Sunil D Sonmale 05-08-2015
		//Challan PDF reader
		public void ChallanPDFreader()
		{
			//MethodName="ChallanPDFreader";
			try{
				//New Code for Reading from pdf
				//Start
				//Get window handles
				Set<String> winids = driver.getWindowHandles();  
				Iterator<String> iterate = winids.iterator();  
				String firstwindow=iterate.next();  
				String secondwindow = iterate.next();  
				System.out.println(firstwindow);  
				System.out.println(secondwindow);  
				//switch to pdf window 
				driver.switchTo().window(secondwindow);
>>>>>>> .r30
		String	name = "";
				Thread.sleep(3000);

				//Print Url of PDF window
				System.out.println("Current URL is "+driver.getCurrentUrl());  

				//get url of pdf to parse
				url = new URL(driver.getCurrentUrl());

				//get file of pdf to parse
				fileToParse = new BufferedInputStream(url.openStream());

				//parse()  --  This will parse the stream and populate the COSDocument object.   
				//COSDocument object --  This is the in-memory representation of the PDF document  

				//parse pdf
				parser = new PDFParser(fileToParse);

				parser.parse();

				//getPDDocument() -- This will get the PD document that was parsed. When you are done with this document you must call    close() on it to release resources  
				//PDFTextStripper() -- This class will take a pdf document and strip out all of the text and ignore the formatting and           such.  

				//get parsed output as string
				output = new PDFTextStripper().getText(parser.getPDDocument());

				System.out.println(output);

				//Define patterns where expected data is expected
				Pattern chalappno = Pattern.compile("(?<=Application No. )\\s*([0-9]+)\\s*(|\n)");

<<<<<<< .mine
		Pattern appcntname = Pattern.compile("(?<=Applicant Name : )\\s*(.+?)\\s*(?=Applicant Name )");
=======
				Pattern appchalno = Pattern.compile("(?<=Challan No. : )\\s*([0-9]+)\\s*(|\n)");
>>>>>>> .r30

<<<<<<< .mine
		//Pattern actappcntname = Pattern.compile("(.+?)(Applicant Name : )");

		Matcher matcher = chalappno.matcher(output);
		Matcher matcher1 = appchalno.matcher(output);
=======
				Matcher matcher = chalappno.matcher(output);
				Matcher matcher1 = appchalno.matcher(output);
>>>>>>> .r30
		Matcher matcher2 = appcntname.matcher(output);
		//String apnamestring = matcher2.group(1);
		//Matcher matcher3 = actappcntname.matcher(apnamestring);

<<<<<<< .mine
		while(matcher2.find()){
			name = matcher.group(1);
			System.out.println("Applicant name is " + name);
			if (name.equals(appcntName))
			{
				System.out.println("Applicant name is " +appcntName);
			}

		}

//		String[] splited = name.split(" ");
//
//	    String split_one=splited[0];
//	    String split_second=splited[1];
//	    String split_three=splited[2];
//
//		System.out.println(split_one);
//		System.out.println(split_second);
//		System.out.println(split_three);
=======
				if (matcher.find() & matcher1.find()) {
>>>>>>> .r30

<<<<<<< .mine

		if (matcher.find() & matcher1.find()) {
=======
					String	printdappno=matcher.group(1);
					System.out.println("Captured String for Application no is "+printdappno);
					String challanno=matcher1.group(1);
					int stringlength = printdappno.length();
					System.out.println("Length of the captured string is "+stringlength);
					stringlength = stringlength/2;
					System.out.println("String length divided by two is "+stringlength);
>>>>>>> .r30

<<<<<<< .mine
			String	printdappno=matcher.group(1);
			System.out.println("Captured String for Application no is "+printdappno);
			String challanno=matcher1.group(1);


		//	String applcntname = matcher2.group(1);
		//	System.out.println();


		//	Pattern actappcntname = Pattern.compile("^.+?(?=\\s*\\b(?:Applicant Name :))");

		//	Matcher matcher3 = actappcntname.matcher(applcntname);


			int stringlength = printdappno.length();
			System.out.println("Length of the captured string is "+stringlength);
			stringlength = stringlength/2;
			System.out.println("String length divided by two is "+stringlength);
=======
					printdappno = printdappno.substring(0, (stringlength));
					System.out.println("Application no printed in Challan is " +printdappno);
					System.out.println("Challan no printed in Challan pdf is " +challanno);
>>>>>>> .r30

					if (appNo.equals(printdappno))
					{
						System.out.println("Application Number Matches");				
					}
					else
					{
						System.out.println("Application Number Does Not Match");
					}		
				}
		//	System.out.println(applcntname);
		//	System.out.println(matcher3.group(1));

			appcntName = mGetPropertyFromFile("bnd_birthRegAppcntFstNm")+" "+mGetPropertyFromFile("bnd_birthRegAppcntMdlNm")+" "+mGetPropertyFromFile("bnd_birthRegAppcntLstNm");
			System.out.println("Entered Applicant Name is ="+appcntName);

			while(matcher2.find()){
				name = matcher2.group(1);
				System.out.println("Applicant name printed in Challan is : " + name);
				if (appcntName.equals(name))
				{
					System.out.println("Applicant name matches");
				}

			}

				else{
					System.out.println("Regex failed");
				}

<<<<<<< .mine
			//	System.out.println("Applicant name is : "+appname);


			if (appNo.equals(printdappno))
			{
				System.out.println("Application Number Matches");				
			}
			else
			{
				System.out.println("Application Number Does Not Match");
			}		
		}
=======
				parser.getPDDocument().close();
>>>>>>> .r30

				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);  

				Thread.sleep(5000);

				//Take Screenshot

				//captureScreen("Challan");

<<<<<<< .mine
		driver.switchTo().window(firstwindow);


		try {
			parser.getPDDocument().close();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}   
=======
				Thread.sleep(4000);
>>>>>>> .r30

<<<<<<< .mine
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);  
=======
				driver.close();
>>>>>>> .r30

				Thread.sleep(2000);

				//Switch to 
				driver.switchTo().window(firstwindow);

<<<<<<< .mine
		//Take Screenshot
		//			try {
//				captureScreen("Challan");
//			} catch (Exception e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}
//
//			try {
//				Thread.sleep(4000);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}

		//	driver.close();
=======
				for (int second = 0;; second++) {
					if (second >= 60) fail("timeout");
					try { if (driver.findElement(By.linkText("Logout")).isDisplayed()) break; } catch (Exception e) {}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
>>>>>>> .r30

			}
			catch (Exception e){
				try {
					//captureScreen("Challan");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		private void fail(String string) {
			// TODO Auto-generated method stub

		}

<<<<<<< .mine
		mWaitForVisible("linkText", mGetPropertyFromFile("logoutid"));
=======
>>>>>>> .r30
	 */

	//Inclusion of child name

	public void IncOfChildName() throws IOException
	{
		try
		{
			/*//Birth and Death link
		mWaitForVisible("linkText", mGetPropertyFromFile("bnd_BNDLinkid"));
		mClick("linkText", mGetPropertyFromFile("bnd_BNDLinkid"));
		mGenericWait();

		//inclusion of Child Name link
		mWaitForVisible("linkText", mGetPropertyFromFile("bnd_incOfChildNmelink"));
		mClick("linkText", mGetPropertyFromFile("bnd_incOfChildNmelink"));
		mGenericWait();*/

			mNavigation("bnd_BNDLinkid", "bnd_incOfChildNmelink");

			//sending application number
			mGenericWait();	

			//mSendKeys("id", mGetPropertyFromFile("bnd_incOfCHildAppid"),"57015111700008");	

			//mSendKeys("id", mGetPropertyFromFile("bnd_incOfCHildAppid"), mGetPropertyFromFile("bnd_incOfCHildAppNo"));

			//mSendKeys("id", mGetPropertyFromFile("bnd_incOfCHildAppid"),BirthAndDeathServices.appNo);

			// Search by Year and Registration No.
			/*mSendKeys("id", mGetPropertyFromFile("bnd_incOfCHildYearid"), mGetPropertyFromFile("bnd_incOfCHildYear"));
		mGenericWait();	
		mSendKeys("id", mGetPropertyFromFile("bnd_incOfCHildRegNoid"), mGetPropertyFromFile("bnd_incOfCHildRegNo"));*/

			searchByCertNoORRegAppnNoORYrAndRegNo(mGetPropertyFromFile("bnd_incOfChildCertificateNoid"), mGetPropertyFromFile("bnd_incOfChildYearid"), mGetPropertyFromFile("bnd_incOfChildRegNoid"));

			//clicking on search button
			mWaitForVisible("id", mGetPropertyFromFile("bnd_incOfChildNmeSerchid"));
			mClick("id", mGetPropertyFromFile("bnd_incOfChildNmeSerchid"));
			mGenericWait();

			//sending name of child
			mGenericWait();	

			//mSendKeys("id", mGetPropertyFromFile("bnd_incOfChildNme"),"57015111600002");

			//	mSendKeys("id", mGetPropertyFromFile("bnd_incOfChildNme"),"57015111600002");



			//driver.findElement(By.id("brChildname")).clear();

			//	driver.findElement(By.id("brChildname")).clear();

			mGenericWait();
			mHindiTextConversion("id", mGetPropertyFromFile("bnd_incOfChildNme"), mGetPropertyFromFile("bnd_incOfChildNmeData"));
			//driver.findElement(By.id("brChildname")).sendKeys("chirag");
			mGenericWait();
			mTakeScreenShot();
			mscroll(0,400);
			mTakeScreenShot();
			
			birthRegChildName= driver.findElement(By.id(mGetPropertyFromFile("bnd_incOfChildNme"))).getAttribute("value");
			System.out.println("child name is :"+birthRegChildName);
			
			String birthDateAtIncChild = driver.findElement(By.id(mGetPropertyFromFile("bnd_DatOfBirthAtIncofCHildNme"))).getAttribute("value");
			birthDate= driver.findElement(By.id(mGetPropertyFromFile("bnd_DatOfBirthAtIncofCHildNme"))).getAttribute("value");
			System.out.println("date of birth is :"+birthDate);
			mGenericWait();	
			System.out.println("date of birth is :"+birthDateAtIncChild);

			birthRegFatherName= driver.findElement(By.id(mGetPropertyFromFile("bnd_FatherNmeAtIncOfChildNme"))).getAttribute("value");
			System.out.println("child's father name is :"+birthRegFatherName);
			String fatherNameAtIncChild = driver.findElement(By.id(mGetPropertyFromFile("bnd_FatherNmeAtIncOfChildNme"))).getAttribute("value");
			mGenericWait();	

			System.out.println(" father name is :"+fatherNameAtIncChild);
			
			birthRegMotherName=driver.findElement(By.name(mGetPropertyFromFile("bnd_MotherNmeAtIncOfChildNme"))).getAttribute("value");
			System.out.println("child's father name is :"+birthRegMotherName);
			String motherNameAtIncChild = driver.findElement(By.name(mGetPropertyFromFile("bnd_MotherNmeAtIncOfChildNme"))).getAttribute("value");
			mGenericWait();		
			System.out.println("mother name is :"+motherNameAtIncChild);

			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_IncOfChildNmeInfrmntPrvdr"), mGetPropertyFromFile("bnd_incOfChildNmeAppcntTtl"));

			//sending first name
			mSendKeys("id", mGetPropertyFromFile("bnd_firstNameAtIncOfChildNme"),mGetPropertyFromFile("bnd_firstNameAtIncOfChildNmeData"));

			//sending middle name
			mSendKeys("id", mGetPropertyFromFile("bnd_middleNameAtIncOfChildNme"),mGetPropertyFromFile("bnd_middleNameAtIncOfChildNmeData"));

			//sending last name
			mSendKeys("id", mGetPropertyFromFile("bnd_lastNameAtIncOfChildNme"),mGetPropertyFromFile("bnd_lastNameAtIncOfChildNmeData"));
			mGenericWait();

			//sending mobile number
			mSendKeys("id", mGetPropertyFromFile("bnd_mobileNoAtIncOfChild"),mGetPropertyFromFile("bnd_mobileNoAtIncOfChildData"));
			mGenericWait();	
			//sending email id
			mSendKeys("id", mGetPropertyFromFile("bnd_OwnerEmailIdAtIncOfChildNme"),mGetPropertyFromFile("bnd_OwnerEmailIdAtIncOfChildNmeData"));

			mGenericWait();	
			deliveryMode("bnd_CollectatOfficeAtIncOfChild");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in IncOfChildName method");
		}
	}


	public void paymentAtIncOfChildNme(String mode)
	{
		try
		{
			modeForChallan=mode;

			mSelectDropDown("id", mGetPropertyFromFile("bnd_oflPaymentModeAtIncOfChild"), mGetPropertyFromFile(mode));
			if(mGetPropertyFromFile(mode).equals("Pay by Challan@Bank"))
			{
				mSelectDropDown("id", mGetPropertyFromFile("bnd_bankAccIdAtIncOfChildnme"), mGetPropertyFromFile("bnd_bnkNmeAtChildNme"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in paymentAtIncOfChildNme method");
		}
	}

	public void deliveryMode(String mode) throws IOException
	{
		try
		{
			mSelectDropDown("id", mGetPropertyFromFile("bnd_IncOfChildDeliveryMode"), mGetPropertyFromFile(mode));
			if(mGetPropertyFromFile(mode).equals("By Post/Courier"))
			{
				String deliveryMode = driver.findElement(By.id("postalMode")).getAttribute("value");
				System.out.println(" delivery mode is: "+deliveryMode);

				String noOfAlreadyCopies = driver.findElement(By.id(mGetPropertyFromFile("bnd_oldCopynumAtIncOfChild"))).getAttribute("value");
				System.out.println("No Of already issued copies are : "+noOfAlreadyCopies);


				//sending new copies required
				mSendKeys("id", mGetPropertyFromFile("bnd_newCopiesAtIncOfChild"),mGetPropertyFromFile("bnd_newCopiesAtIncOfChildData"));

				noOfCopyForBNDCombo = Integer.parseInt(noOfAlreadyCopies)+Integer.parseInt(mGetPropertyFromFile("bnd_newCopiesAtIncOfChildData"));

				System.out.println(" copies for cert : "+noOfCopyForBNDCombo);
				mGenericWait();

				// BPL Details

				if(mGetPropertyFromFile("bnd_bplApplicable").equals("Yes"))
				{
					if (driver.findElements(By.id(mGetPropertyFromFile("bnd_bplYesNoid"))).size() != 0)
					{
						if(mGetPropertyFromFile("bplFlag").equals("Yes"))
						{
							mSelectDropDown("id", mGetPropertyFromFile("bnd_bplYesNoid"), mGetPropertyFromFile("bnd_bplYes"));
							mGenericWait();
							mSendKeys("id", mGetPropertyFromFile("bnd_bplNumid"), mGetPropertyFromFile("bnd_bplNum"));
						}
						else
						{
							mSelectDropDown("id", mGetPropertyFromFile("bnd_bplYesNoid"), mGetPropertyFromFile("bnd_bplNo"));
						}
					}
					else
					{
						System.out.println("BPL is not applicable");
					}
				}
				else
				{
					System.out.println("BPL is not configured");
				}


				/*mUpload("id", mGetPropertyFromFile("bnd_UploadAtIncOfChildNme"), mGetPropertyFromFile("stillBirthUploadPath"));

			if(mGetPropertyFromFile("bnd_bplApplicable").equals("Yes"))
			{
				if (driver.findElements(By.id(mGetPropertyFromFile("bnd_bplYesNoid"))).size() != 0)
				{
					if(mGetPropertyFromFile("bplFlag").equals("Yes"))
					{
						mGenericWait();
						mUpload("id", mGetPropertyFromFile("bnd_InclOfChildNameBPLCardUpldid"), mGetPropertyFromFile("bnd_InclOfChildNameBPLCardUpld"));
					}
				}
			}*/

				docUpload("xpath",mGetPropertyFromFile("bnd_InclOfChildNameUpldDocTableid"),"uploadDocFlag");
				mGenericWait();	
				//clicking offline payment mode  
				mClick("id",mGetPropertyFromFile("bnd_oflinePaymntModeAtIncOfChild"));
				mGenericWait();	

				//selecting bank for payment
				//paymentAtIncOfChildNme("byBankOrULB");
				payment("paymentMode","byBankOrULB");

				mTakeScreenShot();
				if(!mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Online"))
				{
					//submit button
					mClick("css", mGetPropertyFromFile("bnd_submitBtnAtIncOfChild"));
					mGenericWait();	


					//appNo = mGetText("css", mGetPropertyFromFile("bnd_NoAftrIncOfChildNme"));
					mAppNoArray("css", mGetPropertyFromFile("bnd_NoAftrIncOfChildNme"));

					System.out.println(" no after Inclusion of child name :"+appNo);

					//proceed button
					mTakeScreenShot();
					mGenericWait();	
					mClick("id", mGetPropertyFromFile("bnd_PrcdBtnAtIncOfChilNme"));
					mGenericWait();
					mCustomWait(5000);
					//common.newChallanReader();
					if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
					{
						newChallanReader();
					}
					else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
					{
						mChallanPDFReader();
					}
				}
			}
			else
			{
				String deliveryMode = driver.findElement(By.id("postalMode")).getAttribute("value");
				System.out.println(" delivery mode is: "+deliveryMode);

				String noOfAlreadyCopies = driver.findElement(By.id(mGetPropertyFromFile("bnd_oldCopynumAtIncOfChild"))).getAttribute("value");

				int NoOfOlrdyIssdCopy = Integer.parseInt(noOfAlreadyCopies);

				System.out.println("No Of already issued copies are : "+noOfAlreadyCopies);
				//sending new copies required
				mSendKeys("id", mGetPropertyFromFile("bnd_newCopiesAtIncOfChild"),mGetPropertyFromFile("bnd_newCopiesAtIncOfChildData"));

				noOfCopyForBNDCombo = NoOfOlrdyIssdCopy+Integer.parseInt(mGetPropertyFromFile("bnd_newCopiesAtIncOfChildData"));

				System.out.println("Total Number of Copies are : "+noOfCopyForBNDCombo);

				mGenericWait();

				// BPL Details

				if(mGetPropertyFromFile("bnd_bplApplicable").equals("Yes"))
				{
					if (driver.findElements(By.id(mGetPropertyFromFile("bnd_bplYesNoid"))).size() != 0)
					{
						if(mGetPropertyFromFile("bplFlag").equals("Yes"))
						{
							mSelectDropDown("id", mGetPropertyFromFile("bnd_bplYesNoid"), mGetPropertyFromFile("bnd_bplYes"));
							mGenericWait();
							mSendKeys("id", mGetPropertyFromFile("bnd_bplNumid"), mGetPropertyFromFile("bnd_bplNum"));
						}
						else
						{
							mSelectDropDown("id", mGetPropertyFromFile("bnd_bplYesNoid"), mGetPropertyFromFile("bnd_bplNo"));
						}
					}
					else
					{
						System.out.println("BPL is not applicable");
					}
				}
				else
				{
					System.out.println("BPL is not configured");
				}

				mUpload("id", mGetPropertyFromFile("bnd_UploadAtIncOfChildNme"), mGetPropertyFromFile("stillBirthUploadPath"));

				if(mGetPropertyFromFile("bnd_bplApplicable").equals("Yes"))
				{
					if (driver.findElements(By.id(mGetPropertyFromFile("bnd_bplYesNoid"))).size() != 0)
					{
						if(mGetPropertyFromFile("bplFlag").equals("Yes"))
						{
							mGenericWait();
							mUpload("id", mGetPropertyFromFile("bnd_InclOfChildNameBPLCardUpldid"), mGetPropertyFromFile("bnd_InclOfChildNameBPLCardUpld"));
						}
					}
				}

				int NoOfNewCopy = Integer.parseInt(mGetPropertyFromFile("bnd_newCopiesAtIncOfChildData"));

				int TotalNoOfCopy = NoOfOlrdyIssdCopy+NoOfNewCopy;
				if(TotalNoOfCopy>=2)
				{
					//clicking offline payment mode  
					mGenericWait();
					mClick("id",mGetPropertyFromFile("bnd_oflinePaymntModeAtIncOfChild"));
					mGenericWait();	

					//selecting bank for payment
					//paymentAtIncOfChildNme("byBankOrULB");
					payment("paymentMode","byBankOrULB");

					mTakeScreenShot();
					if(!mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Online"))
					{
						//submit button
						mClick("css", mGetPropertyFromFile("bnd_submitBtnAtIncOfChild"));
						mGenericWait();	

						//appNo = mGetText("css", mGetPropertyFromFile("bnd_NoAftrIncOfChildNme"));
						mAppNoArray("css", mGetPropertyFromFile("bnd_NoAftrIncOfChildNme"));
						System.out.println(" no after Inclusion of child name :"+appNo);

						//proceed button
						mTakeScreenShot();
						mGenericWait();	
						mClick("id", mGetPropertyFromFile("bnd_PrcdBtnAtIncOfChilNme"));
						mGenericWait();	
						//switching tabs
						mCustomWait(5000);
						//common.newChallanReader();
						if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
						{
							newChallanReader();
						}
						else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
						{
							mChallanPDFReader();
						}
					}
				}
				else
				{

					mTakeScreenShot();
					mGenericWait();
					if(!mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Online"))
					{
						//submit button
						mClick("css", mGetPropertyFromFile("bnd_submitBtnAtIncOfChild"));
						mGenericWait();	

						//appNo = mGetText("css", mGetPropertyFromFile("bnd_NoAftrIncOfChildNme"));
						mAppNoArray("css", mGetPropertyFromFile("bnd_NoAftrIncOfChildNme"));
						System.out.println(" no after Inclusion of child name :"+appNo);

						//proceed button
						mGenericWait();	
						mTakeScreenShot();
						mClick("id", mGetPropertyFromFile("bnd_PrcdBtnAtIncOfChilNme"));
						mGenericWait();	

						//finish button
						mWaitForVisible("xpath",mGetPropertyFromFile("bnd_incOfChildNmeFinish"));
						mClick("xpath",mGetPropertyFromFile("bnd_incOfChildNmeFinish"));
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in deliveryMode method");
		}
	}

	//Sunil D Sonmale 05-08-2015
	//Challan PDF reader
	public void ChallanPDFreader()
	{
		String name =" ";

		//MethodName="ChallanPDFreader";
		try{
			//New Code for Reading from pdf
			//Start
			//Get window handles
			Set<String> winids = CommonUtilsAPI.driver.getWindowHandles();  
			Iterator<String> iterate = winids.iterator();  
			String firstwindow=iterate.next();  
			String secondwindow = iterate.next();  
			System.out.println(firstwindow);  
			System.out.println(secondwindow);  
			//switch to pdf window 
			CommonUtilsAPI.driver.switchTo().window(secondwindow);
			System.err.println("IN PDF READER");
			Thread.sleep(3000);

			//Print Url of PDF window
			System.out.println("Current URL is "+CommonUtilsAPI.driver.getCurrentUrl());  

			//get url of pdf to parse
			url = new URL(CommonUtilsAPI.driver.getCurrentUrl());

			//get file of pdf to parse
			fileToParse = new BufferedInputStream(url.openStream());

			//parse()  --  This will parse the stream and populate the COSDocument object.   
			//COSDocument object --  This is the in-memory representation of the PDF document  

			//parse pdf
			parser = new PDFParser(fileToParse);

			parser.parse();

			//getPDDocument() -- This will get the PD document that was parsed. When you are done with this document you must call    close() on it to release resources  
			//PDFTextStripper() -- This class will take a pdf document and strip out all of the text and ignore the formatting and           such.  

			//get parsed output as string
			output = new PDFTextStripper().getText(parser.getPDDocument());

			System.out.println(output);

			//Define patterns where expected data is expected
			Pattern chalappno = Pattern.compile("(?<=Application No. )\\s*([0-9]+)\\s*(|\n)");

			Pattern appchalno = Pattern.compile("(?<=Challan No. : )\\s*([0-9]+)\\s*(|\n)");

			Pattern appcntname = Pattern.compile("(?<=Applicant Name : )\\s*(.+?)\\s*(?=Applicant Name )");

			//	Pattern chalnamt = Pattern.compile("(?<=Amount Payable In Words :)\\s*([0-9]+)\\s*(|\n)");

			Pattern chalnamt = Pattern.compile("(?<=Amount Payable In Words :)\\s*([0-9]+(\\.[0-9][0-9]?)?)\\s*(|\n)");



			Matcher matcher = chalappno.matcher(output);
			Matcher matcher1 = appchalno.matcher(output);
			Matcher matcher2 = appcntname.matcher(output);
			Matcher matcher3 = chalnamt.matcher(output);


			if (matcher.find() & matcher1.find()) {

				String	printdappno=matcher.group(1);
				System.out.println("Captured String for Application no is "+printdappno);
				String challanno=matcher1.group(1);
				int stringlength = printdappno.length();
				System.out.println("Length of the captured string is "+stringlength);
				stringlength = stringlength/2;
				System.out.println("String length divided by two is "+stringlength);

				printdappno = printdappno.substring(0, (stringlength));
				System.out.println("Application no printed in Challan is " +printdappno);
				System.out.println("Challan no printed in Challan pdf is " +challanno);

				if (appNo.equals(printdappno))
				{
					System.out.println("Application Number Matches");				
				}
				else
				{
					System.out.println("Application Number Does Not Match");
				}	


				mAssert(appNo,"57015090800036","application number does not match");


				appcntName = mGetPropertyFromFile("bnd_birthRegAppcntFstNm")+" "+mGetPropertyFromFile("bnd_birthRegAppcntMdlNm")+" "+mGetPropertyFromFile("bnd_birthRegAppcntLstNm");
				System.out.println("Entered Applicant Name is ="+appcntName);

				while(matcher2.find())
				{
					name = matcher2.group(1);
					System.out.println("Applicant name printed in Challan is : " + name);
					if (appcntName.equals(name))
					{
						System.out.println("Applicant name matches");
					}

				}

				while(matcher3.find())
				{
					String challanamt=matcher3.group(1);
					System.out.println("Challan amount printed in Challan pdf is " +challanamt);
				}


			}




			else{
				System.out.println("Regex failed");
			}

			parser.getPDDocument().close();

			CommonUtilsAPI.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);  


			Thread.sleep(5000);

			//Take Screenshot

			//captureScreen("Challan");

			Thread.sleep(4000);

			CommonUtilsAPI.driver.close();

			Thread.sleep(2000);

			//Switch to 
			CommonUtilsAPI.driver.switchTo().window(firstwindow);

			mWaitForVisible("linkText", mGetPropertyFromFile("logoutid"));


		}
		catch (Exception e){
			try {
				//captureScreen("Challan");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}


	//Method for Birth Certificate PDF reader
	public void birthCertPDFReader(String birthRegNo, String dateofBirth, String childNm, String parentsNm)
	{

		String name =" ";
		int flag=0;
		//MethodName="ChallanPDFreader";
		try{
			//New Code for Reading from pdf
			//Start
			//Get window handles
			Set<String> winids = driver.getWindowHandles();
			Iterator<String> iterate = winids.iterator();  
			String firstwindow=iterate.next();
			String secondwindow = iterate.next();  
			System.out.println(firstwindow);  
			System.out.println(secondwindow);  
			//switch to pdf window 
			driver.switchTo().window(secondwindow);
			System.err.println("IN PDF READER");
			Thread.sleep(3000);

			mDownloadFile("com.abm.mainet.bnd.BirthAndDeathServices");

			//Print Url of PDF window
			System.out.println("Current URL is "+driver.getCurrentUrl());  

			//get url of pdf to parse
			url = new URL(driver.getCurrentUrl());

			//get file of pdf to parse
			fileToParse = new BufferedInputStream(url.openStream());

			//parse()  --  This will parse the stream and populate the COSDocument object.   
			//COSDocument object --  This is the in-memory representation of the PDF document  

			//parse pdf
			parser = new PDFParser(fileToParse);

			parser.parse();

			//getPDDocument() -- This will get the PD document that was parsed. When you are done with this document you must call    close() on it to release resources  
			//PDFTextStripper() -- This class will take a pdf document and strip out all of the text and ignore the formatting and           such.  

			//get parsed output as string
			output = new PDFTextStripper().getText(parser.getPDDocument());

			System.out.println(output);

			//Define patterns where expected data is expected

			Pattern chldnm = Pattern.compile("(?<=FemaleName: Sex:)(.*)\\s*");

			Pattern parentsnm = Pattern.compile("(?<=Name of Father:Name of Mother :)(.*)\\s*");

			Pattern birthdt = Pattern.compile("(?<=Date of Birth:)\\s*([0-9]+.[0-9]+.[0-9]+)\\s*(|\n)");

			Pattern birthregno = Pattern.compile("\\s*([0-9]+.[0-9]+.[0-9]+)\\s*");

			//	Pattern birthcertno = Pattern.compile("(?<=/)\\s*([0-9]+)\\s*");

			//Pattern birthcertno = Pattern.compile("/(.*?)/");
			Pattern birthcertno = Pattern.compile("(.*?)Certificate No.");




			Matcher matcher4 = chldnm.matcher(output);
			Matcher matcher5 = parentsnm.matcher(output);
			Matcher matcher6 = birthdt.matcher(output);
			Matcher matcher7 = birthregno.matcher(output);
			Matcher matcher8 = birthcertno.matcher(output);


			if (matcher4.find() & matcher5.find())
			{
				String childname = matcher4.group(1);
				System.out.println("Chlid name printed in Birth Certificate is : " + childname);
				if (childNm.equals(childname))
				{
					System.out.println("Child name matches");
				}
				else
				{
					System.out.println("Child name does not matches");
				}

				String parentsname = matcher5.group(1);
				String parentsnamestr = parentsname.replaceAll("\\s", "");
				System.out.println("Parents name printed in Birth Certificate is : " + parentsnamestr);
				if (parentsNm.equals(parentsnamestr))
				{
					System.out.println("Parents name matches");
				}
				else
				{
					System.out.println("Parents name does not matches");
				}

				while(matcher6.find())
				{
					String birthdate = matcher6.group(1);

					String brdate = birthdate.replaceAll("-", "");
					System.out.println("Date of birth printed in Birth Certificate is : " + birthdate);
					if (dateofBirth.equals(brdate))
					{
						System.out.println("DOB matches");
					}
					else
					{
						System.out.println("DOB does not matches");
					}
				}

				while(matcher7.find())
				{

					flag = 1;
					String birthregnumber = matcher7.group(1);
					String birthregnum = birthregnumber.substring(10);
					//System.out.println("Birth registration number printed in Birth Certificate is : " + birthregnumber);
					System.out.println("Birth registration number printed in Birth Certificate is : "+birthregnum);
					if (birthRegNo.equals(birthregnum))
					{


						System.out.println("Birth registration number matches");
					}
					else
					{
						System.out.println("Birth registration number does not matches");
					}

					if (flag == 1)
						break;

				}

				if (matcher8.find())
				{
					//System.out.println(matcher8.group(1));		

					String xxx = matcher8.group(1);

					Pattern pattern1 = Pattern.compile("([0-9]+)");
					Matcher matcher9 = pattern1.matcher(xxx);

					if (matcher9.find())
					{
						System.out.println("Certificate no. is "+matcher9.group(1));
					}
				}


			}

			else
			{
				System.out.println("Regex failed");
			}

			parser.getPDDocument().close();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			Thread.sleep(5000);

			//Take Screenshot

			//captureScreen("Challan");

			Thread.sleep(4000);

			driver.close();

			Thread.sleep(2000);

			//Switch to 
			driver.switchTo().window(firstwindow);

			mWaitForVisible("linkText", mGetPropertyFromFile("logoutid"));


		}
		catch (Exception e){
			try {
				//captureScreen("Challan");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}

	}


	//Method for Death Certificate PDF reader
	public void deathCertPDFReader(String deathRegNo, String dateofDeath, String deceasedNm, String relativeNm)
	{

		String name =" ";
		int flag=0;
		//MethodName="ChallanPDFreader";
		try{
			//New Code for Reading from pdf
			//Start
			//Get window handles
			Set<String> winids = driver.getWindowHandles();
			Iterator<String> iterate = winids.iterator();  
			String firstwindow=iterate.next();
			String secondwindow = iterate.next();  
			System.out.println(firstwindow);  
			System.out.println(secondwindow);  
			//switch to pdf window 
			driver.switchTo().window(secondwindow);
			System.err.println("IN PDF READER");
			Thread.sleep(3000);

			mDownloadFile("com.abm.mainet.bnd.BirthAndDeathServices");

			//Print Url of PDF window
			System.out.println("Current URL is "+driver.getCurrentUrl());  

			//get url of pdf to parse
			url = new URL(driver.getCurrentUrl());

			//get file of pdf to parse
			fileToParse = new BufferedInputStream(url.openStream());

			//parse()  --  This will parse the stream and populate the COSDocument object.   
			//COSDocument object --  This is the in-memory representation of the PDF document  

			//parse pdf
			parser = new PDFParser(fileToParse);

			parser.parse();

			//getPDDocument() -- This will get the PD document that was parsed. When you are done with this document you must call    close() on it to release resources  
			//PDFTextStripper() -- This class will take a pdf document and strip out all of the text and ignore the formatting and           such.  

			//get parsed output as string
			output = new PDFTextStripper().getText(parser.getPDDocument());

			System.out.println(output);

			//Define patterns where expected data is expected

			Pattern deceasednm = Pattern.compile("\\s*(.+?)\\s*(?=MaleName: Sex:)");

			Pattern relativenm = Pattern.compile("\\s*(.+?)\\s*(?=Name of Father / Husband:Name of Mother:)");

			Pattern deathdt = Pattern.compile("\\s*(.+?)\\s*(?=Date of Death:)");

			Pattern deathregno = Pattern.compile("\\s*([0-9]+)\\s*(?=([0-9]+.[0-9]+.[0-9]+))");

			Pattern deathcertno = Pattern.compile("(?<=Certificate No.)\\s*(.*)");


			Matcher matcher9 = deceasednm.matcher(output);
			Matcher matcher10 = relativenm.matcher(output);
			Matcher matcher11 = deathdt.matcher(output);
			Matcher matcher12 = deathregno.matcher(output);
			Matcher matcher13 = deathcertno.matcher(output);



			if (matcher9.find() & matcher10.find())
			{
				String deceasedname = matcher9.group(1);
				System.out.println("Deceased name printed in Death Certificate is : " + deceasedname);
				if (deceasedNm.equals(deceasedname))
				{
					System.out.println("Deceased name matches");
				}
				else
				{
					System.out.println("Deceased name does not matches");
				}

				String relativename = matcher10.group(1);
				System.out.println("Deceased relative name printed in Death Certificate is : " + relativename);
				if (relativeNm.equals(relativename))
				{
					System.out.println("Relative name matches");
				}
				else
				{
					System.out.println("Relative name does not matches");
				}

				if (matcher11.find())
				{
					String deathdate = matcher11.group(1);
					String dhdate = deathdate.replaceAll("-", "");
					System.out.println("Date of death printed in Death Certificate is : " + deathdate);
					if (dateofDeath.equals(dhdate))
					{
						System.out.println("Date of death matches");
					}
					else
					{
						System.out.println("Date of death does not matches");
					}
				}



				if (matcher12.find())
				{
					String deathregnumber = matcher12.group(1);
					System.out.println("Registration number printed in Death Certificate is : " + deathregnumber);
					if (deathRegNo.equals(deathregnumber))
					{
						System.out.println("Registration number matches");
					}
					else
					{
						System.out.println("Registration number does not matches");
					}
				}

				if (matcher13.find())
				{
					//System.out.println(matcher13.group(1));		

					String xxx = matcher13.group(1);

					Pattern pattern1 = Pattern.compile("(?<=/)\\s*([0-9]+)");
					Matcher matcher14 = pattern1.matcher(xxx);

					if (matcher14.find())
					{
						System.out.println("Certificate no. is "+matcher14.group(1));
					}
				}

			}
			else
			{
				System.out.println("Regex failed");
			}

			parser.getPDDocument().close();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			Thread.sleep(5000);

			//Take Screenshot

			//captureScreen("Challan");

			Thread.sleep(4000);

			driver.close();

			Thread.sleep(2000);

			//Switch to 
			driver.switchTo().window(firstwindow);

			mWaitForVisible("linkText", mGetPropertyFromFile("logoutid"));


		}
		catch (Exception e){
			try {
				//captureScreen("Challan");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}

	}


	//Method for ULB challan PDF reader
	public void ULBChallanPDFReader(String appNo, String applicantName){

		String name =" ";
		int flag=0;
		//MethodName="ChallanPDFreader";
		try{
			//New Code for Reading from pdf
			//Start
			//Get window handles
			Set<String> winids = driver.getWindowHandles();
			Iterator<String> iterate = winids.iterator();  
			String firstwindow=iterate.next();
			String secondwindow = iterate.next();  
			System.out.println(firstwindow);  
			System.out.println(secondwindow);  
			//switch to pdf window 
			driver.switchTo().window(secondwindow);
			System.err.println("IN PDF READER");
			Thread.sleep(3000);
			/*
			mDownloadFile(myClassName);

			//Print Url of PDF window
			System.out.println("Current URL is "+driver.getCurrentUrl());

			//get url of pdf to parse
			url = new URL(driver.getCurrentUrl());

			//get file of pdf to parse
			fileToParse = new BufferedInputStream(url.openStream());

			//parse()  --  This will parse the stream and populate the COSDocument object.   
			//COSDocument object --  This is the in-memory representation of the PDF document  

			//parse pdf
			parser = new PDFParser(fileToParse);

			parser.parse();

			//getPDDocument() -- This will get the PD document that was parsed. When you are done with this document you must call    close() on it to release resources  
			//PDFTextStripper() -- This class will take a pdf document and strip out all of the text and ignore the formatting and           such.  

			//get parsed output as string
			output = new PDFTextStripper().getText(parser.getPDDocument());

			System.out.println(output);

			//Define patterns where expected data is expected
			Pattern chalappno = Pattern.compile("(?<=Application No./LOI No. :)\\s*([0-9]+)\\s*(|\n)");

			Pattern appchalno = Pattern.compile("(?<=Challan No. :)\\s*([0-9]+)\\s*(|\n)");

			Pattern appcntname = Pattern.compile("(?<=Applicant Name :)\\s*(.+?)\\s*(?=Applicant Name :)");

			//	Pattern chalnamt = Pattern.compile("(?<=Amount Payable In Words :)\\s*([0-9]+)\\s*(|\n)");

			Pattern chalnamt = Pattern.compile("(?<=Amount Payable In Words :)\\s*([0-9]+(\\.[0-9][0-9]?)?)\\s*(|\n)");



			Matcher matcher = chalappno.matcher(output);
			Matcher matcher1 = appchalno.matcher(output);
			Matcher matcher2 = appcntname.matcher(output);
			Matcher matcher3 = chalnamt.matcher(output);


			if (matcher.find() & matcher1.find()) {

				printedAppNo = matcher.group(1);
				System.out.println("Captured String for Application no is "+printedAppNo);
				String challanno=matcher1.group(1);
				int stringlength = printedAppNo.length();
				System.out.println("Length of the captured string is "+stringlength);
				stringlength = stringlength/2;
				System.out.println("String length divided by two is "+stringlength);

				printedAppNo = printedAppNo.substring(0, (stringlength));
				System.out.println("Application no printed in Challan is " +printedAppNo);
				System.out.println("Challan no printed in Challan pdf is " +challanno);

				if (appNo.equals(printedAppNo))
				{
					System.out.println("Application Number Matches");				
				}
				else
				{
					System.out.println("Application Number Does Not Match");
				}	

				//Assertion on application number

				//mAssert("5555555555",printedAppNo,"Application number does not match, browser: 5555555555 || PDF: "+printedAppNo);


				//appcntName = mGetPropertyFromFile("bnd_birthRegAppcntFstNm")+" "+mGetPropertyFromFile("bnd_birthRegAppcntMdlNm")+" "+mGetPropertyFromFile("bnd_birthRegAppcntLstNm");
				System.out.println("Entered Applicant Name is ="+applicantName);

				while(matcher2.find())
				{
					applcntName = matcher2.group(1);
					System.out.println("Applicant name printed in Challan is : " + applcntName);
					if (applicantName.equals(applcntName))
					{
						System.out.println("Applicant name matches");
					}
				}

				//Assertion on applicant name
				//mAssert("ABCDefg", name,"Applicant name does not match, browser: ABCDefg || PDF: "+name);

				while(matcher3.find())
				{
					String challanamt=matcher3.group(1);
					System.out.println("Challan amount printed in Challan pdf is " +challanamt);
				}



			}

			else{
				System.out.println("Regex failed");
			}

			parser.getPDDocument().close();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			Thread.sleep(5000);
			 */
			//Take Screenshot

			//captureScreen("Challan");

			Thread.sleep(4000);

			driver.close();

			Thread.sleep(2000);

			//Switch to 
			driver.switchTo().window(firstwindow);

			mWaitForVisible("linkText", mGetPropertyFromFile("logoutid"));


		}
		catch (Exception e){
			try {
				//captureScreen("Challan");
			} catch (Exception e1) {

				e1.printStackTrace();
			}	
		}

	}

	public void verifyDoc()
	{
		if(	driver.findElements(By.xpath("//*[@id=\"1\"]/td[2]")).size() != 0)
		{
			try
			{
				WebElement table = driver.findElement(By.xpath("//*[@id=\"1\"]/td[2]"));
				List<WebElement> rows = table.findElements(By.tagName("tr"));
				int rwcount =rows.size();
				System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
				System.out.println("hello");



				// Method for dynamic objects by Madhuri D started on 13/08/2015
				/*public void DynamicObjects()
	{
		String pltfnm = "";
		String pltfaddr="";
		String plaintiffname = "";  
		String plaintiffaddr = ""; 
		String plaintiffnameval = "";
		String plaintiffaddrval= "";


		try{
			WebElement table = driver.findElement(By.className("gridtable"));


			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount =rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

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
					cols.get(1).findElement(By.tagName("a")).click();
					mDownloadPDFFile("com.abm.mainet.bnd.BirthAndDeathServices");
					}
					if(Columnno==2)
					{
					String abc=cols.get(2).findElement(By.tagName("input")).getAttribute("id");
					driver.findElement(By.id(abc)).click();
					}
					if(Columnno==3)
					{
					String xyz=cols.get(3).findElement(By.tagName("input")).getAttribute("id");
					driver.findElement(By.id(xyz)).sendKeys("Approved");
					}

					Columnno=Columnno+1;
				}
				Rowno=Rowno+1;
			}
			// Check for 1st row and then enter values
			if ((rwcount<=2) && (plaintiffnameval.isEmpty()))
			{
				driver.findElement(By.id(plaintiffname)).clear();
				driver.findElement(By.id(plaintiffname)).sendKeys("Mr. Om Prakash Mehra");
				driver.findElement(By.id(plaintiffaddr)).clear();
				driver.findElement(By.id(plaintiffaddr)).sendKeys("Bhagalpur");
			}
			// If it is not 1st row then click on add and then enter values
			else
			{
				Rowno=Rowno-1;
				String plaintiffnameid=pltfnm+Rowno;
				String plaintiffaddrid=pltfaddr+Rowno;
				driver.findElement(By.id(elementId)).click();
				driver.findElement(By.id(plaintiffnameid)).sendKeys("Mr. Rakesh Sharma");
				driver.findElement(By.id(plaintiffaddrid)).sendKeys("Bhagalpur");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}*/

				/*public void onlinePayment ()
	{

	driver.findElement(By.id("entity.onlineOfflineCheck1")).click();
	driver.findElement(By.xpath("//input[@value='Submit']")).click();
	driver.findElement(By.id("btnNo")).click();
	new Select(driver.findElement(By.id("cbBankid"))).selectByVisibleText("PAYU");
	driver.findElement(By.cssSelector("div.btn_fld.margin_top_10 > input.css_btn")).click();
	driver.findElement(By.id("creditlink")).click();
	driver.findElement(By.id("ccard_number")).clear();
	driver.findElement(By.id("ccard_number")).sendKeys("5123 4567 8901 2346");
	driver.findElement(By.id("cname_on_card")).clear();
	driver.findElement(By.id("cname_on_card")).sendKeys("Tushar Gutgutia");
	driver.findElement(By.id("ccvv_number")).clear();
	driver.findElement(By.id("ccvv_number")).sendKeys("123");
	new Select(driver.findElement(By.id("cexpiry_date_month"))).selectByVisibleText("May (5)");
	new Select(driver.findElement(By.id("cexpiry_date_year"))).selectByVisibleText("2017");
	driver.findElement(By.id("pay_button")).click();
	driver.findElement(By.linkText("Finish")).click();
	driver.findElement(By.linkText("Logout")).click();
	driver.findElement(By.id("alertify-ok")).click();
	// ERROR: Caught exception [ERROR: Unsupported command [waitForPopUp | _self | 30000]]
	driver.findElement(By.linkText("Change Your City")).click();
	driver.close();
	}*/


				//to get text inside document td
				String frstDoc1 = mGetText("xpath","//*[@id=\"1\"]/td[2]");
				System.out.println(frstDoc1);
				String[] doc = frstDoc1.split(".pdf");
				int countofDoc = doc.length;
				System.out.println("No of documents in cell :"+countofDoc);

				/*public void birthCorrectionWithCertificate()
	{

		mClick("linkText", mGetPropertyFromFile("bnd_BNDLinkid"));

		mClick("linkText", mGetPropertyFromFile("bnd_birthRegCorrnLinkid"));

		mSendKeys("id", mGetPropertyFromFile("bnd_birthRegCorrnRegNoid"), mGetPropertyFromFile("bnd_birthRegCorrnRegNo"));

		mClick("xpath", mGetPropertyFromFile("bnd_birthRegCorrnSearchid"));

		mClick("css", mGetPropertyFromFile("bnd_birthRegCorrnEditDetailsid"));

		mSendKeys("id", mGetPropertyFromFile("bnd_birthRegCorrnChildNameid"), mGetPropertyFromFile("bnd_birthRegCorrnChildName"));

		mSelectDropDown("id", mGetPropertyFromFile("bnd_birthRegCorrnCertDilModeid"), mGetPropertyFromFile("bnd_birthRegCorrnCertDilMode"));

		mUpload("id", "entity.cfcAttachments0.attPath", "D:\\Automation_Doc_AadharCard.pdf");

		mUpload("id", "entity.cfcAttachments1.attPath", "D:\\Automation_Doc_Affidavit.pdf");

		mUpload("id", "entity.cfcAttachments3.attPath", "D:\\Automation_Doc_Previous Issued Birth Certificate in Original.pdf");

		driver.findElement(By.id("entity.onlineOfflineCheck2")).click();
		new Select(driver.findElement(By.id("oflPaymentMode"))).selectByVisibleText("Pay by Challan@Bank");
		new Select(driver.findElement(By.id("bankAccId"))).selectByVisibleText("HDFC BANK>>MAIN BRANCH>>50200005678979");
		driver.findElement(By.id("mouse")).click();
		driver.findElement(By.id("btnNo")).click();

	}*/


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
								mDownloadFile("com.abm.mainet.rti.RTIServices");
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
	}

	public void challandate1()

	{

	}

	/*	 public void Birthweight(String weight) throws InterruptedException {
			String birthwtmsg;
	      driver.findElement(By.id("brBirthwt")).clear();
         Thread.sleep(1000);
         driver.findElement(By.id("brBirthwt")).sendKeys(weight,Keys.TAB);
         Thread.sleep(1000);	

	if((Float.parseFloat(weight)>(Float.parseFloat("0.5")) && ((Float.parseFloat(weight)<(Float.parseFloat("10"))))))

	{	
		  System.out.println("The entered weight is allowed");
   }

	else if((Float.parseFloat(weight)<(Float.parseFloat("0.5"))))


	{	

		  birthwtmsg = driver.findElement(By.cssSelector("p")).getText();
		  System.out.println(birthwtmsg);
		  System.out.println("Enter the correct weight ");
		  driver.findElement(By.cssSelector("body > div.fancybox-overlay.fancybox-overlay-fixed > div > div > a")).click();
		  System.out.println("The correct weight is getting entered" ); 
		  Birthweight("3"); 
   }

	else if((Float.parseFloat(weight)>(Float.parseFloat("10"))))
	{	  

		  birthwtmsg = driver.findElement(By.cssSelector("p")).getText();
		  System.out.println(birthwtmsg);
		  System.out.println("Enter the correct weight ");
		  driver.findElement(By.cssSelector("body > div.fancybox-overlay.fancybox-overlay-fixed > div > div > a")).click();
		  System.out.println("The correct weight is getting entered" ); 
		  Birthweight("4.5");
   }

	else {}


}*/

	public void deceasedAge(String age,String period) throws InterruptedException 
	{

		try	
		{	    	
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegAgePeriodid"), period);

			if((Float.parseFloat(age)!=0) && ((Float.parseFloat(age)>=(Float.parseFloat("1")) && ((Float.parseFloat(age)<=(Float.parseFloat("24")))) && (period.equals("Hours")))))
			{ 
				mSendKeys("id", mGetPropertyFromFile("bnd_deathRegDeceasedAgeid"), age);
				System.out.println("Deceased age is : "+ age + "" + period);

			}

			else if((Float.parseFloat(age)!=0) && (Float.parseFloat(age)>=(Float.parseFloat("1")) && ((Float.parseFloat(age)<=(Float.parseFloat("31")))) && (period.equals("Days"))))
			{  	
				mSendKeys("id", mGetPropertyFromFile("bnd_deathRegDeceasedAgeid"), age);
				System.out.println("Deceased age is : "+ age + "" + period);        	
			}

			else if((Float.parseFloat(age)!=0) && (Float.parseFloat(age)>=(Float.parseFloat("1")) && ((Float.parseFloat(age)<=(Float.parseFloat("12")))) && (period.equals("Months"))))
			{  	
				mSendKeys("id", mGetPropertyFromFile("bnd_deathRegDeceasedAgeid"), age);
				System.out.println("Deceased age is : "+ age + "" + period);        	
			}

			else if((Float.parseFloat(age)!=0) && (Float.parseFloat(age)>=(Float.parseFloat("1")) && ((Float.parseFloat(age)<=(Float.parseFloat("120")))) && (period.equals("Years"))))  
			{  	
				mSendKeys("id", mGetPropertyFromFile("bnd_deathRegDeceasedAgeid"), age);
				System.out.println("Deceased age is : "+ age + "" + period);
			}

			else 
			{   log.info("Check the entered period");   	
			}

		}   

		catch(NumberFormatException e)
		{
			System.out.println("The entered data is string");
			System.out.println("Enter only the numeric values");
		}

	} 

	public void enterUidNumber(String name,String locator) throws InterruptedException {

		char[] split = name.toCharArray();
		driver.findElement(By.id(locator)).clear();
		Thread.sleep(1000);
		for(int i=0;i<= name.length()-1; i++)
		{
			String finalData=Character.toString(split[i]);
			Thread.sleep(100);
			driver.findElement(By.id(locator)).sendKeys(finalData);
			Thread.sleep(500);         
		}

		if(locator.equals("pdFuid"))  {
			String uidNo=driver.findElement(By.id(locator)).getAttribute("value");
			System.out.println("The UID number of father is : "+uidNo);       
		}

		else  {
			String uidNo=driver.findElement(By.id(locator)).getAttribute("value");
			System.out.println("The UID number of mother is : "+uidNo);      
		}


	}

	// *** Selection of patientHistory  ***

	public void patientHistory() throws InterruptedException 
	{

		if(mGetPropertyFromFile("bnd_deathRegMedicallyCert").equalsIgnoreCase("yes")) 
		{	
			mClick("id", mGetPropertyFromFile("bnd_deathRegMedicallyCertid"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("bnd_deathRegDeathcauseListid"), mGetPropertyFromFile("bnd_deathRegDeathcauseList"));
			mGenericWait();
			mSelectDropDown("name", mGetPropertyFromFile("bnd_deathRegDeathMannerid"), mGetPropertyFromFile("bnd_deathRegDeathManner"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_deathRegDeathMediSuprid"), mGetPropertyFromFile("bnd_deathRegDeathMediSupr"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_deathRegIntervalOnsetid"), mGetPropertyFromFile("bnd_deathRegIntervalOnset"));
			mGenericWait();
			System.out.println("Deceased person is Medically Certified");
		}

		if((mGetPropertyFromFile("bnd_deathRegPregnancyRel").equalsIgnoreCase("yes")) && (mGetPropertyFromFile("bnd_deathRegGender").equalsIgnoreCase("female")))
		{	
			mClick("id", mGetPropertyFromFile("bnd_deathRegPregnancyRelid"));
			System.out.println("Deceased person had pregnancy related issue");
		}

		if(mGetPropertyFromFile("bnd_deathRegChewTobacco").equalsIgnoreCase("yes")) 
		{   
			mClick("id", mGetPropertyFromFile("bnd_deathRegChewTobaccoid"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_deathRegChewTobaccoYrsid"), mGetPropertyFromFile("bnd_deathRegChewTobaccoYrs"));
		}

		if(mGetPropertyFromFile("bnd_deathRegAlcoholic").equalsIgnoreCase("yes"))
		{   
			mClick("id", mGetPropertyFromFile("bnd_deathRegAlcoholicid"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_deathRegAlcoholicYrsid"), mGetPropertyFromFile("bnd_deathRegAlcoholicYrs"));
		}

		if(mGetPropertyFromFile("bnd_deathRegSmoke").equalsIgnoreCase("yes"))
		{   
			mClick("id", mGetPropertyFromFile("bnd_deathRegSmokeid"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_deathRegSmokeYrsid"), mGetPropertyFromFile("bnd_deathRegSmokeYrs"));
		}

		if(mGetPropertyFromFile("bnd_deathRegChewBetelNut").equalsIgnoreCase("yes"))
		{   
			mClick("id", mGetPropertyFromFile("bnd_deathRegChewBetelNutid"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("bnd_deathRegChewBetelNutYrsid"), mGetPropertyFromFile("bnd_deathRegChewBetelNutYrs"));
		}

	}

	// Method for search by Certificate No. OR by Registration Application No. OR by Year and Registration No.
	public void searchByCertNoORRegAppnNoORYrAndRegNo(String bnd_certificateNoid,String bnd_regYearid,String bnd_regNoid)
	{
		try
		{
			// Search by Certificate No
			if(mGetPropertyFromFile("bnd_searchByCertNo").equalsIgnoreCase("Yes"))
			{
				mSendKeys("id", bnd_certificateNoid, mGetPropertyFromFile("bnd_certificateNo"));
				mGenericWait();
			} 

			// Search by Registration Application No
			else if(mGetPropertyFromFile("bnd_searchByRegAppNo").equalsIgnoreCase("Yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("bnd_regAppNoid"), mGetPropertyFromFile("bnd_regAppNo"));
				mGenericWait();
			} 

			// Search by Year and Registration No.
			else if(mGetPropertyFromFile("bnd_searchByYear&RegNo").equalsIgnoreCase("Yes"))
			{
				//Enter Year
				mSendKeys("id", bnd_regYearid, mGetPropertyFromFile("bnd_regYear"));	
				mGenericWait();

				//Enter Reg No
				//mSendKeys("id", mGetPropertyFromFile("bnd_birthCertRegNoid"), BirthRegNo);	
				mSendKeys("id", bnd_regNoid, mGetPropertyFromFile("bnd_regNo"));
				mGenericWait();
			} 

		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	public void searchByRegNoRegAppnNoCertNoYear(String bnd_registrationNoid,String bnd_certificateNoid)
	{
		try
		{
			// Search by Registration No.
			if(mGetPropertyFromFile("bnd_searchByRegNo").equalsIgnoreCase("Yes"))
			{
				mSendKeys("id", bnd_registrationNoid, mGetPropertyFromFile("bnd_registrationNo"));
				mGenericWait();
			}
			// Search by Registration Application No.
			if(mGetPropertyFromFile("bnd_searchByRegAppNo").equalsIgnoreCase("Yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("bnd_regAppNoid"), mGetPropertyFromFile("bnd_regAppNo"));
				mGenericWait();
			}
			// Search by Certificate No.
			if(mGetPropertyFromFile("bnd_searchByCertNo").equalsIgnoreCase("Yes"))
			{
				mSendKeys("id", bnd_certificateNoid, mGetPropertyFromFile("bnd_certificateNo"));
				mGenericWait();
			}
			// Search by Year
			if(mGetPropertyFromFile("bnd_searchByYear").equalsIgnoreCase("Yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("bnd_yearid"), mGetPropertyFromFile("bnd_year"));
				mGenericWait();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void searchByTypeOfRegn(String serviceName)
	{
		try
		{
			if(mGetPropertyFromFile("bnd_authoRegRegistrationType").equalsIgnoreCase("Online"))
			{
				mWaitForVisible("id", mGetPropertyFromFile("bnd_authoRegRegistrationTypeid"));
				mSelectDropDown("id", mGetPropertyFromFile("bnd_authoRegRegistrationTypeid"), mGetPropertyFromFile("bnd_authoRegRegistrationType"));
				mGenericWait();

				mWaitForVisible("id", mGetPropertyFromFile("bnd_authoRegServiceNameid"));
				mSelectDropDown("id", mGetPropertyFromFile("bnd_authoRegServiceNameid"), serviceName);
				mGenericWait();
			}
			else
			{
				mWaitForVisible("id", mGetPropertyFromFile("bnd_authoRegRegistrationTypeid"));
				mSelectDropDown("id", mGetPropertyFromFile("bnd_authoRegRegistrationTypeid"), mGetPropertyFromFile("bnd_authoRegRegistrationType"));
				mGenericWait();	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void reSubmit()
	{
		try
		{
			mWaitForVisible("xpath", mGetPropertyFromFile("eip_resubmitid"));
			mGenericWait();	
			mClick("xpath", mGetPropertyFromFile("eip_resubmitid"));
			mGenericWait();
			mWaitForVisible("xpath", mGetPropertyFromFile("eip_resubmitFindRecordsid"));
			mClick("xpath", mGetPropertyFromFile("eip_resubmitFindRecordsid"));

			mSelectDropDown("css", mGetPropertyFromFile("eip_resubmitSelectAppNoid"), mGetPropertyFromFile("eip_resubmitSelectAppNo"));
			mGenericWait();
			mSelectDropDown("css", mGetPropertyFromFile("eip_resubmitSelectEqualid"), mGetPropertyFromFile("eip_resubmitSelectEqual"));
			mGenericWait();
			IndOrDep("id","eip_resubmitEnterAppNoid", "applicationNo");
			//mSendKeys("id",mGetPropertyFromFile("eip_resubmitEnterAppNoid"), "78616062400004");

			mWaitForVisible("id", mGetPropertyFromFile("eip_resubmitFindBtnid"));
			mClick("id", mGetPropertyFromFile("eip_resubmitFindBtnid"));
			mGenericWait();

			//String mad=/tr[@id='applicationNo.get(CurrentinvoCount)']/td[5]/a/img;
			mWaitForVisible("xpath", mGetPropertyFromFile("eip_resubmitViewDtlsid"));
			//mWaitForVisible("css", mGetPropertyFromFile("eip_resubmitViewDtlsid"));
			mGenericWait();

			WebElement table = driver.findElement(By.id("gridApplicationCompDetails"));
			String servcName = "";
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount =rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

			// Iterate to get the value of each cell in table along with element id
			int Rowno=0;
			for(WebElement rowElement:rows)
			{
				List<WebElement> cols=rowElement.findElements(By.xpath("td"));
				int clcount =cols.size();
				System.out.println("NUMBER OF columns IN THIS TABLE = "+clcount);
				int Columnno=0;
				for(WebElement colElement:cols)
				{
					if(Columnno==2)
					{
						servcName = rowElement.findElement(By.xpath("./td[3]")).getText();
						System.out.println("Service name is :"+servcName);
					}
					Columnno=Columnno+1;
				}
				Rowno=Rowno+1;
			}

			//	mClick("css", mGetPropertyFromFile("eip_resubmitViewDtlsid"));
			mClick("xpath", mGetPropertyFromFile("eip_resubmitViewDtlsid"));
			//	mClick("xpath", "//img[@ src='css/images/view.png']");

			if(appStatusRejectedDueToInadequateInformation)
			{
				switch (servcName)
				{
				case "Birth Registration and Certificate Issuance":
					chngeBirthOrDeathPlaceInResubmit("bnd_chngPlaceType","bnd_chngBirthPlaceTypeid","bnd_chngBirthPlaceid","bnd_chngBirthAddrid");
					appStatusRejectedDueToInadequateInformation=false;
					break;
				case "Death Registration and Certificate Issuance":
					chngeBirthOrDeathPlaceInResubmit("bnd_chngPlaceType","bnd_chngDeathPlaceTypeid","bnd_chngDeathPlaceid","bnd_chngDeathAddrid");
					appStatusRejectedDueToInadequateInformation=false;
					break;
				case "Birth Registration Correction":
					chngeBirthOrDeathPlaceInResubmit("bnd_chngPlaceType","bnd_chngBirthPlaceTypeid","bnd_chngBirthPlaceid","bnd_chngBirthAddrid");
					appStatusRejectedDueToInadequateInformation=false;
					break;
				case "Death Registration Correction":
					chngeBirthOrDeathPlaceInResubmit("bnd_chngPlaceType","bnd_chngDeathPlaceTypeid","bnd_chngDeathPlaceid","bnd_chngDeathAddrid");
					appStatusRejectedDueToInadequateInformation=false;
					break;
				case "Inclusion of Child Name":
					chngeChildNameInResubmit("bnd_childNm");
					appStatusRejectedDueToInadequateInformation=false;
					break;
				case "Birth Certificate":
					chngeBPLNoInResubmit("bnd_bplNo");
					appStatusRejectedDueToInadequateInformation=false;
					break;
				case "Death Certificate":
					chngeBPLNoInResubmit("bnd_bplNo");
					appStatusRejectedDueToInadequateInformation=false;
					break;
				}
			}
			else if(mGetPropertyFromFile("bnd_appStatusRejectedDueToInadequateDocuments").equalsIgnoreCase("yes"))
			{

				switch (servcName)
				{
				case "Birth Registration and Certificate Issuance":

					docUpload("xpath",mGetPropertyFromFile("bnd_birthRegCertUpldDocTableid"),"uploadDocFlag");
					bnd_appStatusRejectedDueToInadequateInformationAndDocuments=false;
					break;
				case "Death Registration and Certificate Issuance":

					docUpload("xpath",mGetPropertyFromFile("bnd_deathRegCertUpldDocTableid"),"uploadDocFlag");
					bnd_appStatusRejectedDueToInadequateInformationAndDocuments=false;
					break;
				case "Birth Registration Correction":
					docUpload("xpath",mGetPropertyFromFile("bnd_birthRegCorrUpldDocTableid"),"uploadDocFlag");
					bnd_appStatusRejectedDueToInadequateInformationAndDocuments=false;
					break;
				case "Death Registration Correction":
					docUpload("xpath",mGetPropertyFromFile("bnd_deathRegCorrUpldDocTableid"),"uploadDocFlag");
					bnd_appStatusRejectedDueToInadequateInformationAndDocuments=false;
					break;
				case "Inclusion of Child Name":
					docUpload("xpath",mGetPropertyFromFile("bnd_InclOfChildNameUpldDocTableid"),"uploadDocFlag");
					bnd_appStatusRejectedDueToInadequateInformationAndDocuments=false;
					break;
				case "Birth Certificate":
					docUpload("xpath",mGetPropertyFromFile("bnd_birthCertUpldDocTableid"),"uploadDocFlag");
					bnd_appStatusRejectedDueToInadequateInformationAndDocuments=false;
					break;
				case "Death Certificate":
					docUpload("xpath",mGetPropertyFromFile("bnd_deathCertUpldDocTableid"),"uploadDocFlag");
					bnd_appStatusRejectedDueToInadequateInformationAndDocuments=false;
					break;
				}
			}
			else if(mGetPropertyFromFile("bnd_appStatusRejectedDueToInadequateInformationAndDocuments").equalsIgnoreCase("yes"))
			{
				switch (servcName)
				{
				case "Birth Registration and Certificate Issuance":

					chngeBirthOrDeathPlaceInResubmit("bnd_chngPlaceType","bnd_chngBirthPlaceTypeid","bnd_chngBirthPlaceid","bnd_chngBirthAddrid");
					docUpload("xpath",mGetPropertyFromFile("bnd_birthRegCertUpldDocTableid"),"uploadDocFlag");
					bnd_appStatusRejectedDueToInadequateInformationAndDocuments=false;
					break;
				case "Death Registration and Certificate Issuance":

					chngeBirthOrDeathPlaceInResubmit("bnd_chngPlaceType","bnd_chngDeathPlaceTypeid","bnd_chngDeathPlaceid","bnd_chngDeathAddrid");
					docUpload("xpath",mGetPropertyFromFile("bnd_deathRegCertUpldDocTableid"),"uploadDocFlag");
					bnd_appStatusRejectedDueToInadequateInformationAndDocuments=false;
					break;
				case "Birth Registration Correction":
					chngeBirthOrDeathPlaceInResubmit("bnd_chngPlaceType","bnd_chngBirthPlaceTypeid","bnd_chngBirthPlaceid","bnd_chngBirthAddrid");
					docUpload("xpath",mGetPropertyFromFile("bnd_birthRegCorrUpldDocTableid"),"uploadDocFlag");
					bnd_appStatusRejectedDueToInadequateInformationAndDocuments=false;
					break;
				case "Death Registration Correction":
					chngeBirthOrDeathPlaceInResubmit("bnd_chngPlaceType","bnd_chngDeathPlaceTypeid","bnd_chngDeathPlaceid","bnd_chngDeathAddrid");
					docUpload("xpath",mGetPropertyFromFile("bnd_deathRegCorrUpldDocTableid"),"uploadDocFlag");
					bnd_appStatusRejectedDueToInadequateInformationAndDocuments=false;
					break;
				case "Inclusion of Child Name":
					chngeChildNameInResubmit("bnd_childNm");
					docUpload("xpath",mGetPropertyFromFile("bnd_InclOfChildNameUpldDocTableid"),"uploadDocFlag");
					bnd_appStatusRejectedDueToInadequateInformationAndDocuments=false;
					break;
				case "Birth Certificate":
					chngeBPLNoInResubmit("bnd_bplNo");
					docUpload("xpath",mGetPropertyFromFile("bnd_birthCertUpldDocTableid"),"uploadDocFlag");
					bnd_appStatusRejectedDueToInadequateInformationAndDocuments=false;
					break;
				case "Death Certificate":
					chngeBPLNoInResubmit("bnd_bplNo");
					docUpload("xpath",mGetPropertyFromFile("bnd_deathCertUpldDocTableid"),"uploadDocFlag");
					bnd_appStatusRejectedDueToInadequateInformationAndDocuments=false;
					break;
				}

			}
			else
			{
				System.out.println("Application authorised already");
			}
			mWaitForVisible("css", mGetPropertyFromFile("eip_resubmitBtnid"));
			mGenericWait();
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("eip_resubmitBtnid"));
			bndResubmit=true;
			mWaitForVisible("id", mGetPropertyFromFile("eip_resubmitProceedBtnid"));
			mTakeScreenShot();
			mClick("id", mGetPropertyFromFile("eip_resubmitProceedBtnid"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in reSubmit method");

		}
	}


	//change in birth or death place in resubmission
	public void chngeBirthOrDeathPlaceInResubmit(String placeType,String placeTypeId,String placeId,String addrsId)
	{
		try
		{
			if(mGetPropertyFromFile(placeType).length()==0)
			{
				System.out.println("No change in birth or death place");
			}
			else
			{
				mGenericWait();
				mTakeScreenShot();
				mSelectDropDown("id",mGetPropertyFromFile(placeTypeId),mGetPropertyFromFile(placeType));
				mGenericWait();

				if(mGetPropertyFromFile(placeType).equals("Hospital")||mGetPropertyFromFile(placeType).equals("Hospital/Institute"))
				{
					mSelectDropDown("id",mGetPropertyFromFile("bnd_chngHospid"),mGetPropertyFromFile("bnd_chngHosp"));
					mGenericWait();
					mTakeScreenShot();
				}
				else
				{
					mSendKeys("id",mGetPropertyFromFile(placeId),"");
					mHindiTextConversion("id",mGetPropertyFromFile(placeId),mGetPropertyFromFile("bnd_chngPlace"));
					mGenericWait();

					mSendKeys("id",mGetPropertyFromFile(addrsId),"");
					mHindiTextConversion("id",mGetPropertyFromFile(addrsId),mGetPropertyFromFile("bnd_chngAddr"));
					mGenericWait();
					mTakeScreenShot();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in chngeBirthOrDeathPlaceInResubmit method");
		}
	}

	//change in child name in resubmission
	public void chngeChildNameInResubmit(String childNm)
	{
		if(mGetPropertyFromFile(childNm).length()==0)
		{
			System.out.println("No change in child name");
		}
		else
		{
			mSendKeys("id",mGetPropertyFromFile("bnd_childNmid"),"");
			mHindiTextConversion("id",mGetPropertyFromFile("bnd_childNmid"),mGetPropertyFromFile(childNm));
			mGenericWait();
		}
	}

	//change in BPL number in resubmission
	public void chngeBPLNoInResubmit(String bplNo)
	{
		if(mGetPropertyFromFile(bplNo).length()==0)
		{
			System.out.println("No change in BPL number");
		}
		else
		{
			mSendKeys("id",mGetPropertyFromFile("bnd_bplNoid"),"");
			mSendKeys("id",mGetPropertyFromFile("bnd_bplNoid"),mGetPropertyFromFile(bplNo));
			mGenericWait();
		}
	}



}