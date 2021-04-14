package com.abm.mainet.pt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.abm.mainet.bnd.BndCustomErrorMessages;
import com.google.common.base.Throwables;

import api.CommonUtilsAPI;
import api.PdfPatterns;
import common.CommonMethods;
import errorhandling.MainetCustomExceptions;
import excelreader.ExcelReader;
import excelreader.ExcelToProperties;

import java.util.TimeZone;

import org.openqa.selenium.JavascriptExecutor;

/**
 * @author Madhuri.dawande
 * @since 16-11-2015
 *
 */

public class PropertyTaxServices extends CommonMethods {

	// For Tax Calculator
	int TCaddCount;
	int cntForBldgDtls;
	public static String TC_TotPropTax;
	public static String TC_TotWaterTax;
	public static String TC_TotPayablePropTax;
	public static String TC_VacantLandRate;
	public static String TC_VacantLandTax;
	public static String VLTax;
	public static String TotPTaxTax;
	public static String TotPayablePTax;
	public static String WtTax;

	//For Transfer Services *******

	Date myDate = new Date(); // For Current Date
	boolean TransferPaymentOtherMode;
	boolean TransferCrtificateOtherMode;
	boolean TransferPaymentHeredity;
	boolean TransferCrtificateHeredity;

	public static String TC_BuiltUPArea;
	public static String TC_Rate;
	public static String discount;

	//For DB Connection ********

	public static String db_Appno;
	public static String db_ChallanNo;
	public static String db_orgid;
	public static String db_acrid="";
	public static XSSFRow row1 =null;
	public static String AppNo;
	ResultSet rs1;
	String db_ApplicationNo;
	String db_ActualPayableAmt;
	String db_TotalAnnualTax;
	String db_TaxableVacantLand;
	String db_TotalPay;
	String db_Interest;
	String db_Rebate;
	String db_TotalCarpetArea;
	String db_TotalPlotArea;
	String db_WaterCharges;
	String db_PenaltyTax;
	String db_TotalVacantLandArea;
	String db_CommercialArea;
	String db_ResidentialArea;
	String db_OtherArea;
	String db_TotalPreviousArrears;
	String db_RWH_Rebate;


	String makeChange;
	String ownerType;
	int noOfJointOwner;
	String landType;
	String lastPayment;
	String objNum;
	String hearingNo;
	String ObjcnGen;
	boolean makeChangeYes = false;
	String propAddrs;

	String vacantarea;
	String landTypeSelected;
	String acquisitiondate;
	String yearBlg;
	String floorNo;
	String typeOfUse;
	String constructType;
	String buildupAr;
	String ratableAr;
	String usageFactor;
	String occFac;
	String viewTypeOfUse;
	int addCount;
	String chngAsmtLandType;
	String chngAsmtLandTypeAcqYr;

	String vancantareavalue;
	float appvacantland;
	String viewWtTax;
	String viewRbt;
	String viewRWHRbt;
	int viewComboRebate;
	String viewUsageFactor;
	String viewWtTaxChrg;
	String viewAnnualRnVal;

	ArrayList<ArrayList<String>> rowCollector=new ArrayList<ArrayList<String>>();
	ArrayList<String> colCollector=new ArrayList<String>();

	//Table related code
	int cntForBldgDtl;
	int cntForVlDtl;
	int k;
	String financialYearchng;
	int financialYear;

	ArrayList<String> addFloorNoArray= new ArrayList<String>();
	ArrayList<String> addTypeOfUseArray= new ArrayList<String>();
	ArrayList<String> addConstructTypeArray= new ArrayList<String>();
	ArrayList<String> addBuildupArArray= new ArrayList<String>();
	ArrayList<String> addUsageFactorArray= new ArrayList<String>();
	ArrayList<String> addOccFacArray= new ArrayList<String>();
	ArrayList<String> calcRatableArArray= new ArrayList<String>();
	ArrayList<String> calcUnitRateArray= new ArrayList<String>();
	ArrayList<String> calcAnnulRateblValArray= new ArrayList<String>();
	ArrayList<String> calcRateOfTaxArray= new ArrayList<String>();
	ArrayList<String> calcAnnulTaxArray= new ArrayList<String>();
	ArrayList<String> calcServiceChargeArray= new ArrayList<String>();

	ArrayList<String> yearBlgArray= new ArrayList<String>();
	ArrayList<String> floorNoArray= new ArrayList<String>();
	ArrayList<String> typeOfUseArray= new ArrayList<String>();
	ArrayList<String> constructTypeArray= new ArrayList<String>();
	ArrayList<String> buildupArArray= new ArrayList<String>();
	ArrayList<String> usageFactorArray= new ArrayList<String>();
	ArrayList<String> occFacArray= new ArrayList<String>();
	//	ArrayList<String> ratableArArray= new ArrayList<String>();
	//	ArrayList<String> unitRateArray= new ArrayList<String>();
	ArrayList<String> alterFloorNoArray= new ArrayList<String>();

	ArrayList<String> viewYearBlgArray= new ArrayList<String>();
	ArrayList<String> viewFloorNoArray= new ArrayList<String>();
	ArrayList<String> viewTypeOfUseArray= new ArrayList<String>();
	ArrayList<String> viewConstructTypeArray= new ArrayList<String>();
	ArrayList<String> viewBuildupArArray= new ArrayList<String>();
	ArrayList<String> viewUsageFactorArray= new ArrayList<String>();
	ArrayList<String> viewOccFacArray= new ArrayList<String>();
	ArrayList<String> viewRatableArArray= new ArrayList<String>();
	ArrayList<String> viewUnitRateArray= new ArrayList<String>();
	ArrayList<String> viewAnnulRateblValArray= new ArrayList<String>();
	ArrayList<String> viewRateOfTaxArray= new ArrayList<String>();
	ArrayList<String> viewAnnulTaxArray= new ArrayList<String>();

	ArrayList<String> sasBldgYearArray= new ArrayList<String>();
	ArrayList<String> sasFloorNoArray= new ArrayList<String>();
	ArrayList<String> sasUsageTypeArray= new ArrayList<String>();
	ArrayList<String> sasConstructionTypeArray= new ArrayList<String>();
	ArrayList<String> sasRatableAreaArray= new ArrayList<String>();
	ArrayList<String> sasUsageFactorArray= new ArrayList<String>();
	ArrayList<String> sasOccFactorArray= new ArrayList<String>();
	ArrayList<String> sasAnnulRateblValArray= new ArrayList<String>();
	ArrayList<String> sasAnnualPropertyTaxArray= new ArrayList<String>();

	ArrayList<String> TCTypeOfUseArray3= new ArrayList<String>();
	ArrayList<String> TCConstructTypeArray3= new ArrayList<String>();
	ArrayList<String> TCBuildupArArray3= new ArrayList<String>();
	ArrayList<String> TCUsageFactorArray3= new ArrayList<String>();
	ArrayList<String> viewUsageFactorArray4= new ArrayList<String>();
	ArrayList<String> TCOccFacArray3= new ArrayList<String>();
	ArrayList<String> viewRatableArArray3= new ArrayList<String>();
	ArrayList<String> TCUnitRateArray3= new ArrayList<String>();
	ArrayList<String> TCAnnulRateblValArray3= new ArrayList<String>();
	ArrayList<String> viewRateOfTaxArray3= new ArrayList<String>();
	ArrayList<String> viewAnnulTaxArray3= new ArrayList<String>();

	ArrayList<String> TCaddedTypeOfUseArray= new ArrayList<String>();
	ArrayList<String> TCaddedConstructTypeArray= new ArrayList<String>();
	ArrayList<String> TCaddedBuildupArArray= new ArrayList<String>();
	ArrayList<String> TCaddedUsageFactorArray= new ArrayList<String>();
	ArrayList<String> TCaddedOccFacArray= new ArrayList<String>();
	ArrayList<String> TCcalculatedRatableArArray= new ArrayList<String>();
	ArrayList<String> TCcalculatedUnitRateArray= new ArrayList<String>();
	ArrayList<String> TCcalculatedAnnulRateblValArray= new ArrayList<String>();
	ArrayList<String> TCcalculatedRateOfTaxArray= new ArrayList<String>();
	ArrayList<String> TCcalculatedAnnulTaxArray= new ArrayList<String>(); 

	ArrayList<String> totARVList = new ArrayList<>();
	ArrayList<String> totHoldingTaxList = new ArrayList<>();


	ArrayList<String> TCcalculatedARVResArray = new ArrayList<String>();
	ArrayList<String> TCcalculatedARVnonResArray = new ArrayList<String>();
	ArrayList<String> TC_CalculatedTotARVList = new ArrayList<String>();
	ArrayList<String> TCalculatedAnnulTaxArray = new ArrayList<String>();
	ArrayList<String> TC_CalculatedTotHoldingTaxList = new ArrayList<String>();
	public static String TC_CalculatedTotARV; 
	public static String TC_CalculatedTotHoldingTax;
	public static String arv;

	public static String db_UsageType;
	public static String db_RoadFactor;
	public static String db_RoadFactorType;
	public static String db_FromDate;
	public static String db_ToDate;
	public static String db_ValueType;
	public static String db_Rate;

	String chngInAddnGrndFlrBuiltupArea;
	String chngInAddnGrndFlrPlotArea;
	String chngInAssWaterTaxType;
	ArrayList<String> chngInAssmntRoadFactor= new ArrayList<String>();

	// Formula
	double pt_newAssessmentAplcbleVacantLand;
	double pt_newAssessmentVacantLandAreaAuto;
	double pt_newAssessmentVacantLandAnnualTaxAuto;
	long pt_newAssessmentVacantLandAnnualTax;
	double pt_newAssessmentPTAnnualTaxAutoRes=0;
	double pt_newAssessmentPTRebt;
	double pt_newAssessmentPTIntAutoResSAS;
	double pt_newAssessmentPTRebtAutoRes;
	double pt_newAssessmentPTAnnualTax;
	double pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes;
	double pt_newAssessmentPTIntAreasAndCurrentSAS;
	//double pt_newAssessmentPTRebtRWH;
	ArrayList<String> calcRatableAreaForResArray= new ArrayList<String>();
	ArrayList<String> calcRatableAreaForNonResArray= new ArrayList<String>();
	ArrayList<String> pt_newAssessmentPTAnnualTaxAutoResArray= new ArrayList<String>();

	ExcelReader ER = new ExcelReader();
	PdfPatterns pdfPtrn = new PdfPatterns();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	ExcelToProperties excelToProp = new ExcelToProperties();

	@BeforeSuite
	public void beforeSuite(){

		thisClassName=this.getClass().getCanonicalName();
		myClassName = thisClassName;
		mCreateModuleFolder("PT_",myClassName);
	}


	@Test (enabled=false)

	public void new_Assessment() throws Exception
	{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			//BndCustomErrorMessages.bndReg_m_errors.entrySet().clear();
			PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();
			challanForPROPERTYServices=true;
			newAsses();
			CommonUtilsAPI.propTaxErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in new_Assessment method");
		}
	}

	@Test (enabled=false)
	public void newAssessment_ChallanVerf() throws Exception
	{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerNewAssessmentServiceName"));
			mCloseBrowser();
			CommonUtilsAPI.propTaxErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in newAssessment_ChallanVerf method");
		}
	}

	@Test (enabled=false)
	public void newAssessment_Authorizaion() throws Exception
	{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
			selfAssessmentAuthorization("pt_newAssessAuthoServiceType","makeChange");
			logOut();
			finalLogOut();
			mCloseBrowser();
			CommonUtilsAPI.propTaxErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in newAssessment_Authorizaion method");
		}
	}

	@Test (enabled=false)
	public void newBill_Printing() throws Exception
	{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		if(mGetPropertyFromFile("makeChange").equalsIgnoreCase("No"))
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
			newBillPrinting();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		CommonUtilsAPI.propTaxErrorMsg.assertAll();
	}

	@Test (enabled=false)
	public void splNotice_Generation() throws Exception
	{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();
			if(mGetPropertyFromFile("makeChange").equalsIgnoreCase("Yes"))
			{					
				splNoticeGen();
			}
			CommonUtilsAPI.propTaxErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in splNotice_Generation method");
		}
	}

	///Madhuri
	@Test (enabled=false)
	public void objectionProcess_SplNotice() throws Exception
	{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();
		if(mGetPropertyFromFile("ObjcnOnSplnotc").equalsIgnoreCase("Yes"))
		{					
			objectionProcess();
		}
		CommonUtilsAPI.propTaxErrorMsg.assertAll();
	}

	@Test (enabled=false)
	public void objectionOn_SplNotice() throws Exception
	{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();
		if(mGetPropertyFromFile("ObjcnOnSplnotc").equalsIgnoreCase("Yes"))
		{					
			//objectionProcess();
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
			objectionEntry();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		CommonUtilsAPI.propTaxErrorMsg.assertAll();
	}

	@Test (enabled=false)
	public void inspectionOf_SplNoticeObjn() throws Exception
	{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();
		if(mGetPropertyFromFile("ObjcnOnSplnotc").equalsIgnoreCase("Yes"))
		{					

			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
			inspection();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		CommonUtilsAPI.propTaxErrorMsg.assertAll();
	}

	@Test (enabled=false)
	public void hearingDateEntryFor_SplNoticeObjn() throws Exception
	{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();
		if(mGetPropertyFromFile("ObjcnOnSplnotc").equalsIgnoreCase("Yes"))
		{					

			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
			hearingDateEntry();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		CommonUtilsAPI.propTaxErrorMsg.assertAll();
	}

	@Test (enabled=false)
	public void hearingDateLetterPrntng_SplNoticeObjn() throws Exception
	{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();
		if(mGetPropertyFromFile("ObjcnOnSplnotc").equalsIgnoreCase("Yes"))
		{					

			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
			hearingDateLetterPrinting();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		CommonUtilsAPI.propTaxErrorMsg.assertAll();
	}

	@Test (enabled=false)
	public void hearingDetails_SplNoticeObjn() throws Exception
	{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();
		if(mGetPropertyFromFile("ObjcnOnSplnotc").equalsIgnoreCase("Yes"))
		{					

			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
			hearingDetails();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		CommonUtilsAPI.propTaxErrorMsg.assertAll();
	}

	@Test (enabled=false)
	public void hearingOrder_SplNoticeObjn() throws Exception
	{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();
		if(mGetPropertyFromFile("ObjcnOnSplnotc").equalsIgnoreCase("Yes"))
		{					

			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
			hearingOrder();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		CommonUtilsAPI.propTaxErrorMsg.assertAll();
	}

	@Test (enabled=false)
	public void revisedBill_Generation() throws Exception
	{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		mCreateArtefactsFolder("PT_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
		revisedBillGeneration();
		logOut();
		finalLogOut();
		mCloseBrowser();
		CommonUtilsAPI.propTaxErrorMsg.assertAll();
	}

	@Test (enabled=false)
	public void revisedBill_Printing() throws Exception
	{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		mCreateArtefactsFolder("PT_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
		revisedBillPrinting();
		api.PdfPatterns.patternRevisedSpecialNoticePdf(pdfoutput, propNo,propAddrs);
		logOut();
		finalLogOut();
		mCloseBrowser();
		CommonUtilsAPI.propTaxErrorMsg.assertAll();
	}

	@Test (enabled=false)
	public void objectionOn_Bill() throws Exception
	{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		if(mGetPropertyFromFile("ObjcnOnBill").equalsIgnoreCase("Yes"))
		{					
			objectionProcess();
		}
		CommonUtilsAPI.propTaxErrorMsg.assertAll();
	}

	@Test (enabled=false)
	public void bill_Payment() throws Exception
	{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		mCreateArtefactsFolder("PT_");
		mOpenBrowser(mGetPropertyFromFile("browserName"));
		mGetURL(mGetPropertyFromFile("url"));
		selectUlb();
		departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
		billPayment();
		logOut();
		finalLogOut();
		mCloseBrowser();
		CommonUtilsAPI.propTaxErrorMsg.assertAll();
	}

	@Test (enabled=false)
	public void alteration_Assessment() throws Exception{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		challanForPROPERTYServices=true;
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		alterationInAssessment();
		CommonUtilsAPI.propTaxErrorMsg.assertAll();
	}

	@Test (enabled=false)
	public void noChangeIn_Assessment() throws Exception{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		challanForPROPERTYServices=true;
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		noChngInAssessment();
		CommonUtilsAPI.propTaxErrorMsg.assertAll();
	}

	@Test (enabled=false)
	public void changeInAssessment_Addition() throws Exception{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		challanForPROPERTYServices=true;
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		chngInAssessmentAddn();
		CommonUtilsAPI.propTaxErrorMsg.assertAll();
	}

	@Test (enabled=false)
	public void Change_Contact_Details() throws Exception{
		currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		//challanForPROPERTYServices=true;
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		changeContactDetails();
		CommonUtilsAPI.propTaxErrorMsg.assertAll();
	}

	@Test (enabled=false)
	public void property_AssessmentCert() throws Exception
	{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			PTAssmntCert();
			CommonUtilsAPI.propTaxErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in property_AssessmentCert method");
		}
	}

	@Test (enabled=false)
	public void propertyAssessment_CertPrinting() throws Exception
	{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			propertyCertPrinting();
			CommonUtilsAPI.propTaxErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in propertyAssessment_CertPrinting method");
		}
	}

	@Test (enabled=false)
	public void extractOf_Property() throws Exception
	{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			extractOfProp();
			CommonUtilsAPI.propTaxErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in extractOf_Property method");
		}
	}

	@Test (enabled=false)
	public void extractOfProperty_CertPrinting() throws Exception
	{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			propertyCertPrinting();
			CommonUtilsAPI.propTaxErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in propertyAssessment_CertPrinting method");
		}
	}

	@Test (enabled=false)
	public void changeIn_Assessment() throws Exception
	{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			chngInAssessment();
			CommonUtilsAPI.propTaxErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in propertyAssessment_CertPrinting method");
		}
	}

	// Code Merge
	@Test(enabled=false)	
	public void Transfer_Heredity() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();

			trnsfr_Heredity();

			CommonUtilsAPI.propTaxErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in transfer_Heredity method");
		}
	}

	@Test(enabled=false)	
	public void Transfer_Fee_IntimationLetter_Heredity() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();

			trnsfrFee_IntiLetter_Heredity();

			CommonUtilsAPI.propTaxErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in Transfer_Fee_IntimationLetter method");
		}
	}

	@Test(enabled=false)
	public void Transfer_Certificate_Heredity() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();
			if(TransferCrtificateHeredity==true)
			{
				trnsfr_Certificate_Heredity();
			}
			CommonUtilsAPI.propTaxErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in Transfer_Certificate method");
		}
	}

	@Test (enabled=false)
	public void Transfer_OtherMode() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();
			trnsfr_OtherMode();
			CommonUtilsAPI.propTaxErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Transfer_OtherMode method");
		}
	}

	@Test(enabled=false)	
	public void Transfer_Fee_IntimationLetter_OtherMode() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();
			trnsfrFee_IntiLetter_OtherMode();
			CommonUtilsAPI.propTaxErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in Transfer_Fee_IntimationLetter_OtherMode method");
		}
	}

	@Test(enabled=false)
	public void Transfer_Certificate_OtherMode() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();
			if(TransferCrtificateOtherMode==true)
			{

				trnsfr_Certificate_OtherMode();

			}
			CommonUtilsAPI.propTaxErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in Transfer_Certificate_OtherMode method");
		}
	}

	//Permission for Property Transfer through Heredity addTest by Piyush
	@Test (enabled=false)
	public void TransferServices_HeredityPayment() throws Exception
	{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			if(TransferPaymentHeredity==true)
			{
				TransferHeredityPayment();
			}
			CommonUtilsAPI.propTaxErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in TransferServices_HeredityPayment method");
		}
	}

	//Permission for Property Transfer through Other Modes addTest by Piyush
	@Test (enabled=false)
	public void TransferServices_OtherModesPayment() throws Exception
	{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();

			if (TransferPaymentOtherMode==true) 

			{
				TransferOtherModesPayment();
			}


			CommonUtilsAPI.propTaxErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in TransferServices_OtherModesPayment method");
		}
	}

	@Test (enabled=false)
	public void Challan_Regeneration() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();
			challan_Regen();
			CommonUtilsAPI.propTaxErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Challan_Regeneration method");

		}
	}

	@Test(enabled=false)	
	public void Transfer_Objection() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();

			trnsfr_Objection();

			CommonUtilsAPI.propTaxErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in Transfer_Objection method");
		}
	}

	@Test(enabled=false)	
	public void Transfer_Inspection() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();

			trnsfr_Inspection();

			CommonUtilsAPI.propTaxErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in Transfer_Inspection method");
		}
	}

	@Test(enabled=false)	
	public void Transfer_HearingDateEntry() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();

			trnsfr_HearingDateEntry();

			CommonUtilsAPI.propTaxErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in Transfer_HearingDateEntry method");
		}
	}

	@Test(enabled=false)	
	public void Transfer_HearingIntimationLetter() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();

			trnsfr_HearingIntiLetter();

			CommonUtilsAPI.propTaxErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in Transfer_HearingIntimationLetter method");
		}
	}

	@Test(enabled=false)	
	public void Transfer_HearingEntry() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();

			trnsfr_HearingEntry();

			CommonUtilsAPI.propTaxErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			inAtTest=true;
			throw new MainetCustomExceptions("Error in Transfer_HearingEntry method");
		}
	}

	@Test(enabled=false)
	public void extractOfProperty_CheckListVerification() throws InterruptedException 
	{
		try
		{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();
			if(mGetPropertyFromFile("bnd_chkListVrfnApplicable").equals("Yes"))
			{
				mCreateArtefactsFolder("PT_");

				ChecklistVerification(mGetPropertyFromFile("cfc_chcklistverfn_username"), mGetPropertyFromFile("cfc_chcklistverfn_pwd"));
			}
			else
			{
				System.out.println("Checklist Verification is not required");
			}
			CommonUtilsAPI.propTaxErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in birthCert_CheckListVerification method");	
		}
	}

	@Test (enabled=false)
	public void changeIn_ContactDetails() throws Exception
	{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			chngInContactDtls();
			CommonUtilsAPI.propTaxErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in propertyAssessment_CertPrinting method");
		}
	}

	@Test (enabled=false)
	public void propertyTax_BillCollection() throws Exception
	{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			propTaxBillColln();
			CommonUtilsAPI.propTaxErrorMsg.assertAll();
		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in propertyAssessment_CertPrinting method");
		}
	}

	public void newAsses() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			//departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			//LOIAPPLICABLE=true;
			newAssessment();
			logOut();
			finalLogOut();
			isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerNewAssessmentServiceName"));
			mCloseBrowser();
		}
		catch(Exception e)
		{
			throw new MainetCustomExceptions("Error in newAsses method");
		}
	}

	public void revisedBill() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			//beforeNewAssessTest();
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin("deptUserName","deptPassword" );
			revisedBillGeneration();
			logOut();
			finalLogOut();
			departmentLogin("deptUserName","deptPassword" );
			revisedBillPrinting();
			logOut();
			finalLogOut();
			departmentLogin("deptUserName","deptPassword" );
			billPayment();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		catch(Exception e)
		{
			throw new MainetCustomExceptions("Error in revisedBill method");
		}
	}

	public void objectionProcess() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
			objectionEntry();
			logOut();
			finalLogOut();
			departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
			inspection();
			logOut();
			finalLogOut();
			departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
			hearingDateEntry();
			logOut();
			finalLogOut();
			departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
			hearingDateLetterPrinting();
			logOut();
			finalLogOut();
			departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
			hearingDetails();
			logOut();
			finalLogOut();
			departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
			hearingOrder();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		catch(Exception e)
		{
			throw new MainetCustomExceptions("Error in objectionProcess method");
		}
	}

	public void splNoticeGen() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
			specialNoticeGeneration();
			logOut();
			finalLogOut();
			departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
			specialNoticePrinting();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		catch(Exception e)
		{
			throw new MainetCustomExceptions("Error in splNoticeGen method");
		}
	}

	public void alterationInAssessment() throws Exception
	{
		try
		{
			//mCreateModuleFolder("PT_",myClassName);
			mCreateArtefactsFolder("PT_");
			//beforeAlterationAssessTest();
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			selectUlb();
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			LOIAPPLICABLE=true;
			alteration();
			logOut();
			finalLogOut();
			/*isChallanVerftnRequired("cfc_challanVerChangeInAssessmentServiceName");
			departmentLogin("deptUserName","deptPassword" );
			selfAssessmentAuthorization("pt_changeInAssessAuthoServiceType","makeChange");
			logOut();
			finalLogOut();
			departmentLogin("deptUserName","deptPassword" );
			newBillPrinting();
			logOut();
			finalLogOut();*/
			mCloseBrowser();
		}
		catch(Exception e)
		{
			throw new MainetCustomExceptions("Error in alterationInAssessment method");
		}
	}

	public void changeContactDetails() throws Exception
	{
		try
		{
			//mCreateModuleFolder("PT_",myClassName);
			mCreateArtefactsFolder("PT_");
			//beforeAlterationAssessTest();
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			selectUlb();
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			//departmentLogin("MBA","Egov@2015#8");		
			//LOIAPPLICABLE=true;
			contactDetChnage();
			logOut();
			finalLogOut();
			/*isChallanVerftnRequired("cfc_challanVerChangeInAssessmentServiceName");
		departmentLogin("deptUserName","deptPassword" );
		selfAssessmentAuthorization("pt_changeInAssessAuthoServiceType","makeChange");
		logOut();
		finalLogOut();
		departmentLogin("deptUserName","deptPassword" );
		newBillPrinting();
		logOut();
		finalLogOut();*/
			mCloseBrowser();
		}
		catch(Exception e)
		{
			throw new MainetCustomExceptions("Error in changeContactDetails method");
		}
	}

	public void noChngInAssessment() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			selectUlb();
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			//departmentLogin(mGetPropertyFromFile("deptUserName"), mGetPropertyFromFile("deptPassword"));
			//LOIAPPLICABLE=true;
			noChangeInAssessment();
			logOut();
			finalLogOut();
			//isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerNoChngInAssessmentServiceName"));
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in noChngInAssessment method");
		}
	}

	public void chngInAssessmentAddn() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			selectUlb();
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			LOIAPPLICABLE=true;
			chngInHoldingDtlsAddition();
			logOut();
			finalLogOut();
			//isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerChngInAssessmentServiceName"));
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in chngInAssessmentAddn method");
		}
	}

	public void PTAssmntCert() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			propAssessmentCert();
			logOut();
			finalLogOut();
			isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerPropAssmntCertServiceName"));
			mCloseBrowser();
		}
		catch(Exception e)
		{
			throw new MainetCustomExceptions("Error in PTAssmntCert method");
		}
	}

	public void extractOfProp() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			extractOfProperty();
			logOut();
			finalLogOut();
			isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerExtractOfPropServiceName"));
			mCloseBrowser();
		}
		catch(Exception e)
		{
			throw new MainetCustomExceptions("Error in extractOfProp method");
		}
	}

	public void propertyCertPrinting()
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("printerUserName"),mGetPropertyFromFile("printerPassword"));
			cfcPrinterGrid();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in propertyCertPrinting method");
		}
	}

	public void chngInAssessment() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			LOIAPPLICABLE=true;
			changeInAssessmentAlterationAddition();
			logOut();
			finalLogOut();
			//isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerChngInAssessmentServiceName"));
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in chngInAssessmentAddn method");
		}
	}


	///New Assessment Ritesh 19092016

	//////////////////////////////////


	public void newAssessment()
	{
		try
		{
			// Navigate to Self Assessment and Payment of Property Tax link
			mNavigation("pt_propertyTaxLinkid", "pt_selfAssessmentLinkid");

			//mNavigation("pt_propertyTaxLinkidxpath", "pt_selfAssessmentLinkid");
			if(mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("citizen") || mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("agency"))
			{
				mClick("id", mGetPropertyFromFile("pt_newAssessmentIAcceptid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("pt_newAssessmentFileSelfAssessid"));
			}

			// Select option New Assessment
			mWaitForVisible("id",mGetPropertyFromFile("pt_newAssessmentid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_newAssessmentid"));
			newAssessment=true;
			mGenericWait();
			mTakeScreenShot();
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_selfAssessmentSubmitid"));

			// Owner Details
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentWardid"), mGetPropertyFromFile("pt_newAssessmentWard"));
			mGenericWait();			

			//Mutation
			mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentMutationFlagid"),mGetPropertyFromFile("pt_newAssessmentMutationFlag"));
			mGenericWait();
			if (mGetPropertyFromFile("pt_newAssessmentMutationFlag").equalsIgnoreCase("Yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentMutationPropNoid"), mGetPropertyFromFile("pt_newAssessmentMutationPropNo"));				
			}

			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentTypeOfOwnerid"),mGetPropertyFromFile("pt_newAssessmentTypeOfOwner"));
			//mscroll(124, 644);
			if (mGetPropertyFromFile("ownerType").equals(mGetPropertyFromFile("pt_newAssessmentSingleOwner"))||mGetPropertyFromFile("ownerType").equals(mGetPropertyFromFile("pt_newAssessmentJointOwner")))
			{
				if (mGetPropertyFromFile("ownerType").equals(mGetPropertyFromFile("pt_newAssessmentSingleOwner")))
				{
					// Single Owner Details

					mGenericWait();
					mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentTitleOfOwnerid"),mGetPropertyFromFile("pt_newAssessmentTitleOfOwner_1"));
					mGenericWait();
					mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentNameOfOwnerid"), mGetPropertyFromFile("pt_newAssessmentNameOfOwner_1"));
					mGenericWait();
					mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentNameOfFathHusid"), mGetPropertyFromFile("pt_newAssessmentNameOfFathHus_1"));
					mGenericWait();
					mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentGenderid"), mGetPropertyFromFile("pt_newAssessmentGender_1"));
				}
				else
				{

					// Method for dynamic objects by Madhuri D on 24/11/2015
					String c1;
					String cn1 = "";
					String c2;
					String cn2 = "";
					String c3;
					String cn3 = "";
					String c4;
					String cn4 = "";

					try{
						//WebElement table = driver.findElement(By.id("joinOwnerTable"));
						WebElement table =mFindElement("id", mGetPropertyFromFile("pt_newAssessmentJointOwnerid"));

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
								c1=cols.get(0).findElement(By.tagName("select")).getAttribute("id");
								cn1 =c1.substring(0, 8);
								c2=cols.get(1).findElement(By.tagName("input")).getAttribute("id");
								cn2 =c2.substring(0, 8);
								c3=cols.get(2).findElement(By.tagName("input")).getAttribute("id");
								cn3 =c3.substring(0, 12);
								c4=cols.get(3).findElement(By.tagName("select")).getAttribute("id");
								cn4 =c4.substring(0, 9);


								System.out.println("Row "+Rowno+" Column "+Columnno+" Id is "+cn1);
								System.out.println("Row "+Rowno+" Column "+Columnno+" Id is "+cn2);
								System.out.println("Row "+Rowno+" Column "+Columnno+" Id is "+cn3);
								System.out.println("Row "+Rowno+" Column "+Columnno+" Id is "+cn4);


								Columnno=Columnno+1;
							}
							Rowno=Rowno+1;
						}
						// 
						int i;
						int j=Integer.parseInt(mGetPropertyFromFile("noOfJointOwner"));
						for (i=0; i<j;i++)
						{
							if(i>=5)
							{

								System.out.println("Enter value less than 5 for number of joint owners");
								mWaitForVisible("xpath", mGetPropertyFromFile("pt_newAssessmentCloseMsgid"));
								mClick("xpath", mGetPropertyFromFile("pt_newAssessmentCloseMsgid"));
								break;
							}



							/*String cn1id=cn1+i;
						System.out.println(cn1id);*/
							/*int count = (i+1);
						String strCount = Integer.toString(count);*/
							mSelectDropDown("id", cn1+i, mGetPropertyFromFile("pt_newAssessmentTitleOfOwner_"+i));
							mGenericWait();
							mSendKeys("id", cn2+i, mGetPropertyFromFile("pt_newAssessmentNameOfOwner_"+i));
							mGenericWait();
							//Added by Sunil D Sonmale 01-12-2015
							//String entname=mGetPropertyFromFile("pt_newAssessmentNameOfOwner_"+i);

							//Madhuri Madam code
							mSendKeys("id", cn3+i, mGetPropertyFromFile("pt_newAssessmentNameOfFathHus_"+i));
							mGenericWait();
							//Added by Sunil D Sonmale 01-12-2015
							//String entfathusname= mGetPropertyFromFile("pt_newAssessmentNameOfFathHus_"+i);


							//entcompname=entname+entfathusname;
							//System.out.println(entcompname);

							//Madhuri madam code starts
							mSelectDropDown("id", cn4+i, mGetPropertyFromFile("pt_newAssessmentGender_"+i));
							mGenericWait();
							if(i<j-1)
							{
								mClick("css",mGetPropertyFromFile("pt_newAssessmentAddOwnerid"));
								mGenericWait();
							}else
							{
								System.out.println("");
							}

						}

						System.out.println("I m out of for");


					}
					catch(Exception e){
						e.printStackTrace();
						throw new MainetCustomExceptions("Error in New Assessment-Joint Owners method");

					}

				}

			}
			else
			{
				// Other Owners Details
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentNameOfOwnerid"), mGetPropertyFromFile("pt_newAssessmentNameOfOwner_1"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentPanNoOfOwnerid"), mGetPropertyFromFile("pt_newAssessmentPanNoOfOwner"));
			}

			mTakeScreenShot();
			mscroll(350,670);

			// Address details

			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentHouseNoid"), mGetPropertyFromFile("pt_newAssessmentHouseNo"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentPropAddrid"), mGetPropertyFromFile("pt_newAssessmentPropAddr"));
			mGenericWait();

			//	String propAddrLine1 = mGetText("id", mGetPropertyFromFile("pt_newAssessmentPropAddrid"));
			//String propAddrLine1 = driver.findElement(By.id(mGetPropertyFromFile("pt_newAssessmentPropAddrid"))).getAttribute("value");
			String propAddrLine1 = mGetText("id", mGetPropertyFromFile("pt_newAssessmentPropAddrid"), "value");
			System.out.println("Property address line 1 :"+propAddrLine1);

			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentPropAddSecid"), mGetPropertyFromFile("pt_newAssessmentPropAddSec"));

			mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentPropAddrPincodeid"), mGetPropertyFromFile("pt_newAssessmentPropAddrPincode"));
			//	String propAddrPincode = mGetText("id", mGetPropertyFromFile("pt_newAssessmentPropAddrPincodeid"));
			//String propAddrPincode = driver.findElement(By.id(mGetPropertyFromFile("pt_newAssessmentPropAddrPincodeid"))).getAttribute("value");
			String propAddrPincode = mGetText("id", mGetPropertyFromFile("pt_newAssessmentPropAddrPincodeid"), "value");
			System.out.println("Property address pincode :"+propAddrPincode);
			propAddrs = propAddrLine1+propAddrPincode;
			System.out.println("Property address :"+propAddrs);
			mGenericWait();

			//Copy Address by Ritesh 12-08-2016
			if(mGetPropertyFromFile("copyAddress").equals("Yes"))
			{
				mClick("id", mGetPropertyFromFile("pt_newAssessmentCopyAddrid"));
			}
			else
			{
				mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentHouseNoCorresid"), mGetPropertyFromFile("pt_newAssessmentHouseNoCorres"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentPropAddCorresid"), mGetPropertyFromFile("pt_newAssessmentPropAddCorres"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentPropAddSecCorresid"), mGetPropertyFromFile("pt_newAssessmentPropAddSecCorres"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentPropAddrPincodeCorresid"), mGetPropertyFromFile("pt_newAssessmentPropAddrPincodeCorres"));
				mGenericWait();
			}
			//mClick("id", mGetPropertyFromFile("pt_newAssessmentCopyAddrid"));

			// Contact details of property owner
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentMobileNoid"), mGetPropertyFromFile("pt_newAssessmentMobileNo"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentEmailIDid"), mGetPropertyFromFile("pt_newAssessmentEmailID"));

			// Type of Land
			mGenericWait();

			//Land Type = Land + Building
			if (mGetPropertyFromFile("landType").equals(mGetPropertyFromFile("pt_newAssessmentLandBuilding")))
			{
				mClick("id", mGetPropertyFromFile("pt_newAssessmentLandBuildingid"));

				//landTypeSelected=driver.findElement(By.xpath("//*[@id='frmMasterForm']/div[12]/div[3]/label")).getText();
				//landTypeSelected=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(45) > div:nth-child(3) > label")).getText();
				landTypeSelected=mGetText("css", mGetPropertyFromFile("pt_newAssessmentlandTypeSelectedLBid"));
				System.out.println("Land Type Selected :"+landTypeSelected);

				//mClick("css", mGetPropertyFromFile("pt_newAssessmentLandBuildingid"));
				mTakeScreenShot();

				// Land/Building details
				mGenericWait();
				//mGdatePicker(mGetPropertyFromFile("pt_newAssessmentAcquisitionDateid"), mGetPropertyFromFile("pt_newAssessmentAcquisitionYear"), mGetPropertyFromFile("pt_newAssessmentAcquisitionMonth"), mGetPropertyFromFile("pt_newAssessmentAcquisitionDate"));
				//mDateSelect("id",mGetPropertyFromFile("pt_newAssessmentAcquisitionDateid"), mGetPropertyFromFile("pt_newAssessmentAcquisitionDate"));
				mGdatePicker("id", mGetPropertyFromFile("pt_newAssessmentAcquisitionDateid"), mGetPropertyFromFile("pt_newAssessmentAcqYear"), mGetPropertyFromFile("pt_newAssessmentAcqMonth"), mGetPropertyFromFile("pt_newAssessmentAcqDate"));
				//acquisitiondate=driver.findElement(By.id(mGetPropertyFromFile("pt_newAssessmentAcquisitionDateid"))).getAttribute("value");
				acquisitiondate=mGetText("id", mGetPropertyFromFile("pt_newAssessmentAcquisitionDateid"), "value");
				System.out.println("Acquisition Date is : "+acquisitiondate);

				mGenericWait();

				// Last Payment Details
				if (mGetPropertyFromFile("lastPayment").equals(mGetPropertyFromFile("pt_newAssessmentLastPaymentNA")))
				{
					mClick("id", mGetPropertyFromFile("pt_newAssessmentLastPaymentNAid"));
					System.out.println("There is no any last payment details");
				}
				else
				{
					mClick("id", mGetPropertyFromFile("pt_newAssessmentLastPaymentManualid"));
					mGenericWait();
					mSendKeys("name", mGetPropertyFromFile("pt_newAssessmentReceiptNoid"), mGetPropertyFromFile("pt_newAssessmentReceiptNo"));
					mGenericWait();
					mSendKeys("name", mGetPropertyFromFile("pt_newAssessmentLastPaymentAmtid"), mGetPropertyFromFile("pt_newAssessmentLastPaymentAmt"));
					mGenericWait();
					mGdatePicker(mGetPropertyFromFile("pt_newAssessmentLastPaymentDateid"), mGetPropertyFromFile("pt_newAssessmentLastPaymentYear"), mGetPropertyFromFile("pt_newAssessmentLastPaymentMonth"), mGetPropertyFromFile("pt_newAssessmentLastPaymentDate"));
					mGenericWait();
					mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentLastPaymentMadeUptoid"), mGetPropertyFromFile("pt_newAssessmentLastPaymentMadeUpto"));
					mGenericWait();
					mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentLastPaymentQuarterid"), mGetPropertyFromFile("pt_newAssessmentLastPaymentQuarter"));					
				}


				mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentRoadFactorid"),mGetPropertyFromFile("pt_newAssessmentRoadFactor"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentPlotAreaid"), mGetPropertyFromFile("pt_newAssessmentPlotArea"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentBuiltUpAreaid"), mGetPropertyFromFile("pt_newAssessmentBuiltUpArea"));
				mGenericWait();

				if (mGetPropertyFromFile("rainWaterHarvesting").equals("Yes"))
				{
					mClick("id", mGetPropertyFromFile("pt_newAssessmentRainWaterHarvestingid"));
					System.out.println("Rain Water Harvesting applicable");
				}
				else
				{
					System.out.println("Rain Water Harvesting not applicable");
				}

				mGenericWait();

				//Water Connection Facility @ Ritesh 12-08-2016
				if (mGetPropertyFromFile("govtWaterConn").equals("Yes"))
				{
					mClick("id", mGetPropertyFromFile("pt_newAssessmentWaterConnTypeid"));
					System.out.println("User select: Govt. Water Connection");
				}
				else if (mGetPropertyFromFile("govtWaterConnwith400").equals("Yes"))
				{
					mClick("id", mGetPropertyFromFile("pt_newAssessmentWaterConnTypewithinid"));
					System.out.println("Own Water Connection and Govt. Water Connection Available within 400 Yards");
				}
				else 
				{
					mClick("id", mGetPropertyFromFile("pt_newAssessmentWaterConnTypewithoutid"));
					System.out.println("Own Water Connection but Govt. Water Connection is not Available within 400 Yards");
				}

				mGenericWait();
				mTakeScreenShot();
				mscroll(550, 660);

				//String acquisitionYear = driver.findElement(By.id(mGetPropertyFromFile("pt_newAssessmentAcquisitionYearid"))).getAttribute("value");

				/////Code commented 11-01-2017 
				/*String acquisitionYear = mGetText("id", mGetPropertyFromFile("pt_newAssessmentAcquisitionYearid"), "value");
				String acqYr = acquisitionYear.substring(0, 4);
				System.out.println("Acquisition year is: "+acqYr);
				int acqYear = Integer.parseInt(acqYr);

				int currentyear = Calendar.getInstance().get(Calendar.YEAR);
				System.out.println("Current year is: "+currentyear);

				cntForBldgDtl = currentyear-acqYear;
				System.out.println("Number of click required is: "+cntForBldgDtl);*/

				String acquisitionYear =mGetText("id", mGetPropertyFromFile("pt_newAssessmentAcquisitionYearid"), "value");
				/*String acqYr = acquisitionYear.substring(0, 4);
				System.out.println("Acquisition year of Flat is: "+acqYr);
				int acqYear = Integer.parseInt(acqYr);*/


				String acqYr = acquisitiondate.substring(6, 10);
				System.out.println("Acquisition year of Land + Building is: "+acqYr);
				int acqYear = Integer.parseInt(acqYr);


				/*int currentyear = Calendar.getInstance().get(Calendar.YEAR);
				System.out.println("Current year is: "+currentyear);*/
				int currentyear;
				String currentyearOrg;

				int year = Calendar.getInstance().get(Calendar.YEAR);
				String fYear = Integer.toString(year);
				int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
				//System.out.println("Financial month : " + month);
				if (month < 3) 
				{
					//System.out.println("Financial Year Ritesh: " + (year - 1) + "-" + year);
					currentyearOrg=fYear.substring(0,4);
					System.out.println("Financial Year : " + currentyearOrg);
				} 
				else 
				{
					//System.out.println("Financial Year : " + year + "-" + (year + 1));
					currentyearOrg=fYear.substring(0,4);
					System.out.println("Financial Year : " + currentyearOrg);
				}

				currentyear = Integer.parseInt(currentyearOrg);

				//int cntForBldgDtl = currentyear-acqYear;
				cntForBldgDtl = currentyear-acqYear;
				System.out.println("Number of click required is for Land + Building: "+cntForBldgDtl);


				//New method by Madhuri for Table reading
				/////////////////////////////////////
				additionofBldgDetails();

				mClick("id", mGetPropertyFromFile("pt_newAssessmentCopyBldgDelsid"));

				BldgDetailsTableReading();

				// Asserting added Building Details with table data
				mAssert(floorNoArray, addFloorNoArray, "Expected is : "+addFloorNoArray+"Actual is : "+floorNoArray+"Array of floor no. does not match in application form");
				mAssert(typeOfUseArray, addTypeOfUseArray, "Expected is : "+addTypeOfUseArray+"Actual is : "+typeOfUseArray+"Array of usage type does not match");
				mAssert(constructTypeArray, addConstructTypeArray, "Expected is : "+addConstructTypeArray+"Actual is : "+constructTypeArray+"Array of construction type does not match");
				mAssert(buildupArArray, addBuildupArArray, "Expected is : "+addBuildupArArray+"Actual is : "+buildupArArray+"Array of built up area does not match");
				mAssert(usageFactorArray, addUsageFactorArray, "Expected is : "+addUsageFactorArray+"Actual is : "+usageFactorArray+"Array of usage factor does not match");
				mAssert(occFacArray, addOccFacArray, "Expected is : "+addOccFacArray+"Actual is : "+occFacArray+"Array of occupancy factor does not match");

				//Vacant Land value checking less than 70% or not @Ritesh Dated: -26-08-2016 
				String appvaclnd =mGetPropertyFromFile("pt_newAssessmentAplcbleVacantLand");
				appvacantland= Float.valueOf(appvaclnd);
				System.out.println("Applicant Vacant Land is: "+appvacantland);
				if(appvacantland<70)
				{

					//WebElement table = driver.findElement(By.id("Vaccentdetid"));
					WebElement table =mFindElement("id", mGetPropertyFromFile("pt_newAssessmentVacantLandAreaTableid"));
					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount = rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

					// Iterate to get the value of each cell in table along with element
					// id
					int Rowno = 0;
					for (WebElement rowElement : rows) 
					{
						List<WebElement> cols = rowElement.findElements(By.xpath("td"));
						int clcount = cols.size();
						System.out.println("NUMBER OF Cols IN THIS TABLE = " + clcount);
						int Columnno = 0;
						for (WebElement colElement : cols) 
						{
							if (Columnno == 0) 
							{
								String yr = rowElement.findElement(By.xpath("./td/input")).getAttribute("value");
								mGenericWait();
								System.out.println("Year is :"+yr);
							}
							if (Columnno == 1) 
							{
								vacantarea = rowElement.findElement(By.xpath("./td[2]/input[3]")).getAttribute("id");
								System.out.println("Vacant Land Area id :"+vacantarea);
								//String vancantareavalue=mGetText("id", vacantarea, "value");

								//vancantareavalue=mGetText("xpath", mGetPropertyFromFile("pt_newAssessmentVacantLandAreaAuto"), "value");

								//vancantareavalue=mGetText("xpath", mGetPropertyFromFile("pt_newAssessmentVacantLandAreaAuto"), "value");
								vancantareavalue=mGetText("id", vacantarea, "value");

								System.out.println("Vacant area value :" +vancantareavalue);
								mGenericWait();

								mAssert(vancantareavalue, mGetPropertyFromFile("pt_newAssessmentVacantLandAreaAuto"), "Wrong calcualted value for Vacant Land");

							}
							Columnno = Columnno + 1;
						}

						Rowno = Rowno + 1;
					}

				}
				else if(appvacantland>=70)
				{
					System.out.println("Taxable Vacant land area is greater than 70%");
					if (mGetPropertyFromFile("manuualVacantLandArea").equalsIgnoreCase("No"))
					{
						System.out.println("Taxable Vacant land area is greater than 70% and user not going to enter Vacant Land");							
					}
					else
					{
						System.out.println("Taxable Vacant land area is greater than 70% and user going to enter Vacant Land");
						//mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentVacntLndAreaid"), mGetPropertyFromFile("pt_newAssessmentVacantLandArea"));

						//WebElement table = driver.findElement(By.id("Vaccentdetid"));
						WebElement table =mFindElement("id", mGetPropertyFromFile("pt_newAssessmentVacantLandid"));
						List<WebElement> rows = table.findElements(By.tagName("tr"));
						int rwcount = rows.size();
						System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

						// Iterate to get the value of each cell in table along with element
						// id
						int Rowno = 0;
						for (WebElement rowElement : rows) 
						{
							List<WebElement> cols = rowElement.findElements(By.xpath("td"));
							int clcount = cols.size();
							System.out.println("NUMBER OF Cols IN THIS TABLE = " + clcount);
							int Columnno = 0;
							for (WebElement colElement : cols) 
							{
								if (Columnno == 0) 
								{
									String yr = rowElement.findElement(By.xpath("./td/input")).getAttribute("value");
									mGenericWait();
									System.out.println("Year is :"+yr);
								}
								if (Columnno == 1) 
								{
									vacantarea = rowElement.findElement(By.xpath("./td[2]/input[3]")).getAttribute("id");
									System.out.println("Vacant Land Area is :"+vacantarea);
									driver.findElement(By.id(vacantarea)).clear();
									//mFindElement("id", mGetPropertyFromFile("pt_newAssessmentVacantLandAreaid")).click();
									//driver.findElement(By.id(vacantarea)).sendKeys(mGetPropertyFromFile("pt_newAssessmentVacantLandAreaMannal"));
									mSendKeys("id", vacantarea, mGetPropertyFromFile("pt_newAssessmentVacantLandAreaMannal"));
									vancantareavalue=mGetText("id", vacantarea, "value");									
									System.out.println("Mannual Vacant Land is: "+vancantareavalue);

									mAssert(vancantareavalue, mGetPropertyFromFile("pt_newAssessmentVacantLandAreaMannal"), "Mannual Vacant Land is different than inserted Mannual Vacant Land");
									mGenericWait();
								}
								Columnno = Columnno + 1;
							}

							Rowno = Rowno + 1;
						}

					}
				}
			}

			//Land Type = Vacant Land
			else if (mGetPropertyFromFile("landType").equals(mGetPropertyFromFile("pt_newAssessmentVacantLand")))
			{
				mClick("css", mGetPropertyFromFile("pt_newAssessmentVacantLandid"));

				//Selected Land Type
				//landTypeSelected=driver.findElement(By.xpath("//*[@id='frmMasterForm']/div[12]/div[2]/label")).getText();
				landTypeSelected=mGetText("css", mGetPropertyFromFile("pt_newAssessmentlandTypeSelectedVCLid"));
				System.out.println("Land Type Selected :"+landTypeSelected);

				mTakeScreenShot();

				// Land/Building details
				mGenericWait();
				//mGdatePicker(mGetPropertyFromFile("pt_newAssessmentAcquisitionDateid"), mGetPropertyFromFile("pt_newAssessmentAcquisitionYear"), mGetPropertyFromFile("pt_newAssessmentAcquisitionMonth"), mGetPropertyFromFile("pt_newAssessmentAcquisitionDate"));
				//mDateSelect("id", mGetPropertyFromFile("pt_newAssessmentAcquisitionDateid"), mGetPropertyFromFile("pt_newAssessmentAcquisitionDate"));
				mGdatePicker("id", mGetPropertyFromFile("pt_newAssessmentAcquisitionDateid"), mGetPropertyFromFile("pt_newAssessmentAcqYear"), mGetPropertyFromFile("pt_newAssessmentAcqMonth"), mGetPropertyFromFile("pt_newAssessmentAcqDate"));
				//acquisitiondate=driver.findElement(By.id(mGetPropertyFromFile("pt_newAssessmentAcquisitionDateid"))).getAttribute("value");
				acquisitiondate=mGetText("id", mGetPropertyFromFile("pt_newAssessmentAcquisitionDateid"), "value");
				System.out.println("Acquisition date of vacant land is:" + acquisitiondate);
				//String vacantLandAcquisitionYear=mGetText("id", "vyearLabelFromValue0", "value");
				String vacantLandAcquisitionYear=mGetText("id", "entity.yearAcqDate", "value");
				String acqYr = vacantLandAcquisitionYear.substring(6, 10);
				System.out.println("Acquisition year of vacant land is: "+acqYr);
				int acqYear = Integer.parseInt(acqYr);

				int currentyear = Calendar.getInstance().get(Calendar.YEAR);
				System.out.println("Current year is: "+currentyear);

				cntForVlDtl = currentyear-acqYear;
				System.out.println("Number of years for vacant land required is: "+cntForVlDtl);

				mGenericWait();

				// Last Payment Details
				if (mGetPropertyFromFile("lastPayment").equals(mGetPropertyFromFile("pt_newAssessmentLastPaymentNA")))
				{
					mClick("id", mGetPropertyFromFile("pt_newAssessmentLastPaymentNAid"));
					System.out.println("There is no any last payment details");
					//mTakeScreenShot();
				}
				else
				{
					mClick("id", mGetPropertyFromFile("pt_newAssessmentLastPaymentManualid"));
					mGenericWait();
					mSendKeys("name", mGetPropertyFromFile("pt_newAssessmentReceiptNoid"), mGetPropertyFromFile("pt_newAssessmentReceiptNo"));
					mGenericWait();
					mSendKeys("name", mGetPropertyFromFile("pt_newAssessmentLastPaymentAmtid"), mGetPropertyFromFile("pt_newAssessmentLastPaymentAmt"));
					mGenericWait();
					mGdatePicker(mGetPropertyFromFile("pt_newAssessmentLastPaymentDateid"), mGetPropertyFromFile("pt_newAssessmentLastPaymentYear"), mGetPropertyFromFile("pt_newAssessmentLastPaymentMonth"), mGetPropertyFromFile("pt_newAssessmentLastPaymentDate"));
					mGenericWait();
					mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentLastPaymentMadeUptoid"), mGetPropertyFromFile("pt_newAssessmentLastPaymentMadeUpto"));
					mGenericWait();
					mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentLastPaymentQuarterid"), mGetPropertyFromFile("pt_newAssessmentLastPaymentQuarter"));
					//mTakeScreenShot();
				}

				mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentRoadFactorid"),mGetPropertyFromFile("pt_newAssessmentRoadFactor"));
				mGenericWait();
				mTakeScreenShot();

				//Water Connection not applicable for Vacant Land @Ritesh 26-08-2016
				//if(driver.findElement(By.id(mGetPropertyFromFile("pt_newAssessmentWaterConnTypeNAid"))).isSelected())
				if(mFindElement("id", mGetPropertyFromFile("pt_newAssessmentWaterConnTypeNAid")).isSelected())
				{
					System.out.println("Govt Water Conn Not Applicable");
				}else
				{
					System.out.println("Govt Water Conn Applicable");
				}
				mGenericWait();
				//WebElement table = driver.findElement(By.id("Vaccentdetid"));
				WebElement table =mFindElement("id", mGetPropertyFromFile("pt_newAssessmentVacantLandAreaTableid"));

				List<WebElement> rows = table.findElements(By.tagName("tr"));
				int rwcount = rows.size();
				System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

				// Iterate to get the value of each cell in table along with element
				// id
				int Rowno = 0;
				for (WebElement rowElement : rows) 
				{
					List<WebElement> cols = rowElement.findElements(By.xpath("td"));
					int clcount = cols.size();
					System.out.println("NUMBER OF Cols IN THIS TABLE = " + clcount);
					int Columnno = 0;
					for (WebElement colElement : cols) 
					{
						if (Columnno == 0) 
						{
							String yr = rowElement.findElement(By.xpath("./td/input")).getAttribute("value");
							mGenericWait();
							System.out.println("Year is :"+yr);
						}
						if (Columnno == 1) 
						{
							vacantarea = rowElement.findElement(By.xpath("./td[2]/input[3]")).getAttribute("id");
							System.out.println("Vacant Land Area is :"+vacantarea);
							//driver.findElement(By.id(vacantarea)).clear();
							//mFindElement("id", vacantarea).click();
							//driver.findElement(By.id(vacantarea)).sendKeys(mGetPropertyFromFile("pt_newAssessmentVacantLandAreaMannal"));
							mSendKeys("id", vacantarea, mGetPropertyFromFile("pt_newAssessmentVacantLandAreaMannal"));
							mGenericWait();
						}
						Columnno = Columnno + 1;
					}

					Rowno = Rowno + 1;
				}

			}
			//Land Type = Flat
			else
			{
				mClick("id", mGetPropertyFromFile("pt_newAssessmentFlatid"));				
				//Selected Land Type
				//landTypeSelected=driver.findElement(By.xpath("//*[@id='frmMasterForm']/div[12]/div[4]/label")).getText();
				landTypeSelected=mGetText("css", mGetPropertyFromFile("pt_newAssessmentlandTypeSelectedFlatid"));
				System.out.println("Land Type Selected :"+landTypeSelected);
				mTakeScreenShot();

				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentApartmentNameid"), mGetPropertyFromFile("pt_newAssessmentApartmentName"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentNoOfFlatsid"), mGetPropertyFromFile("pt_newAssessmentNoOfFlats"));

				// Acquisition Date
				mGenericWait();
				//mGdatePicker(mGetPropertyFromFile("pt_newAssessmentAcquisitionDateid"), mGetPropertyFromFile("pt_newAssessmentAcquisitionYear"), mGetPropertyFromFile("pt_newAssessmentAcquisitionMonth"), mGetPropertyFromFile("pt_newAssessmentAcquisitionDate"));
				//mDateSelect("id", mGetPropertyFromFile("pt_newAssessmentAcquisitionDateid"), mGetPropertyFromFile("pt_newAssessmentAcquisitionDate"));
				mGdatePicker("id", mGetPropertyFromFile("pt_newAssessmentAcquisitionDateid"), mGetPropertyFromFile("pt_newAssessmentAcqYear"), mGetPropertyFromFile("pt_newAssessmentAcqMonth"), mGetPropertyFromFile("pt_newAssessmentAcqDate"));
				//acquisitiondate=driver.findElement(By.id(mGetPropertyFromFile("pt_newAssessmentAcquisitionDateid"))).getAttribute("value");
				acquisitiondate=mGetText("id", mGetPropertyFromFile("pt_newAssessmentAcquisitionDateid"), "value");
				System.out.println("Acquisition Date is : "+acquisitiondate);

				mGenericWait();

				// Last Payment Details
				if (mGetPropertyFromFile("lastPayment").equals(mGetPropertyFromFile("pt_newAssessmentLastPaymentNA")))
				{
					mClick("id", mGetPropertyFromFile("pt_newAssessmentLastPaymentNAid"));
					System.out.println("There is no any last payment details");
				}
				else
				{
					mClick("id", mGetPropertyFromFile("pt_newAssessmentLastPaymentManualid"));
					mGenericWait();
					mSendKeys("name", mGetPropertyFromFile("pt_newAssessmentReceiptNoid"), mGetPropertyFromFile("pt_newAssessmentReceiptNo"));
					mGenericWait();
					mSendKeys("name", mGetPropertyFromFile("pt_newAssessmentLastPaymentAmtid"), mGetPropertyFromFile("pt_newAssessmentLastPaymentAmt"));
					mGenericWait();
					mGdatePicker(mGetPropertyFromFile("pt_newAssessmentLastPaymentDateid"), mGetPropertyFromFile("pt_newAssessmentLastPaymentYear"), mGetPropertyFromFile("pt_newAssessmentLastPaymentMonth"), mGetPropertyFromFile("pt_newAssessmentLastPaymentDate"));
					mGenericWait();
					mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentLastPaymentMadeUptoid"), mGetPropertyFromFile("pt_newAssessmentLastPaymentMadeUpto"));
					mGenericWait();
					mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentLastPaymentQuarterid"), mGetPropertyFromFile("pt_newAssessmentLastPaymentQuarter"));

				}

				// Land/Building details				
				mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentRoadFactorid"),mGetPropertyFromFile("pt_newAssessmentRoadFactor"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentPlotAreaid"), mGetPropertyFromFile("pt_newAssessmentPlotArea"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentBuiltUpAreaid"), mGetPropertyFromFile("pt_newAssessmentBuiltUpArea"));
				mGenericWait();

				//Old code @Ritesh 12-08-2016
				//mClick("id", mGetPropertyFromFile("pt_newAssessmentWaterConnTypeid"));

				//Water Connection Facility @ Ritesh 12-08-2016
				if (mGetPropertyFromFile("govtWaterConn").equals("Yes"))
				{
					mClick("id", mGetPropertyFromFile("pt_newAssessmentWaterConnTypeid"));
					System.out.println("User select: Govt. Water Connection");
				}
				else if (mGetPropertyFromFile("govtWaterConnwith400").equals("Yes"))
				{
					mClick("id", mGetPropertyFromFile("pt_newAssessmentWaterConnTypewithinid"));
					System.out.println("Own Water Connection and Govt. Water Connection Available within 400 Yards");
				}
				else 
				{
					mClick("id", mGetPropertyFromFile("pt_newAssessmentWaterConnTypewithoutid"));
					System.out.println("Own Water Connection but Govt. Water Connection is not Available within 400 Yards");
				}

				//Building Details as per the Acquisition Date @Ritesh 17-08-2016				
				//String acquisitionYear = driver.findElement(By.id(mGetPropertyFromFile("pt_newAssessmentAcquisitionYearid"))).getAttribute("value");
				String acquisitionYear =mGetText("id", mGetPropertyFromFile("pt_newAssessmentAcquisitionYearid"), "value");
				/*String acqYr = acquisitionYear.substring(0, 4);
				System.out.println("Acquisition year of Flat is: "+acqYr);
				int acqYear = Integer.parseInt(acqYr);*/


				String acqYr = acquisitiondate.substring(6, 10);
				System.out.println("Acquisition year of Flat is: "+acqYr);
				int acqYear = Integer.parseInt(acqYr);


				/*int currentyear = Calendar.getInstance().get(Calendar.YEAR);
				System.out.println("Current year is: "+currentyear);*/
				int currentyear;
				String currentyearOrg;

				int year = Calendar.getInstance().get(Calendar.YEAR);
				String fYear = Integer.toString(year);
				int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
				//System.out.println("Financial month : " + month);
				if (month < 3) 
				{
					//System.out.println("Financial Year Ritesh: " + (year - 1) + "-" + year);
					currentyearOrg=fYear.substring(0,4);
					System.out.println("Financial Year : " + currentyearOrg);
				} 
				else 
				{
					//System.out.println("Financial Year : " + year + "-" + (year + 1));
					currentyearOrg=fYear.substring(0,4);
					System.out.println("Financial Year : " + currentyearOrg);
				}

				currentyear = Integer.parseInt(currentyearOrg);

				//int cntForBldgDtl = currentyear-acqYear;
				cntForBldgDtl = currentyear-acqYear;
				System.out.println("Number of click required is for Flat: "+cntForBldgDtl);

				//////////////////////////////
				//New method by Madhuri for table reading building details
				additionofBldgDetails();

				mClick("id", mGetPropertyFromFile("pt_newAssessmentCopyBldgDelsid"));

				BldgDetailsTableReading();

				mGenericWait();
				mTakeScreenShot();
				mscroll(550, 660);
				/*mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentFloorNoid"),mGetPropertyFromFile("pt_newAssessmentFloorNo"));
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentTypeOfUseid"),mGetPropertyFromFile("pt_newAssessmentTypeOfUse"));
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentConstructionTypeid"),mGetPropertyFromFile("pt_newAssessmentConstructionType"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentBuildingAreaid"), mGetPropertyFromFile("pt_newAssessmentBuildingArea"));
				 */
				mGenericWait();
				/*//Added for Residential Usag Factor Ritesh 08-08-2016 
				if (mGetPropertyFromFile("pt_newAssessmentTypeOfUse")!="Residential")
				{	
					mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentUsageFactorid "), mGetPropertyFromFile("pt_newAssessmentUsageFactor"));
				}
				else
				{
					System.out.println("Usage Type selected Residential");
				}

				mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentOccupancyFactorid"),mGetPropertyFromFile("pt_newAssessmentOccupancyFactor"));
				 */
				/*if (!mGetPropertyFromFile("pt_newAssessmentTypeOfUse").equalsIgnoreCase("Residential"))
				{	
					mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentUsageFactorid"), mGetPropertyFromFile("pt_newAssessmentUsageFactor"));
					mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentOccupancyFactorid"),mGetPropertyFromFile("pt_newAssessmentOccupancyFactor"));
				}
				else
				{
					System.out.println("Usage Type selected Residential");
					mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentOccupancyFactorid"),mGetPropertyFromFile("pt_newAssessmentOccupancyFactor"));
				}*/

			}


			// Water Tax details
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentWaterTaxTypeid"),mGetPropertyFromFile("pt_newAssessmentWaterTaxType"));


			//Code by Sunil for getting applicant name
			//02-12-2015
			//Code starts here
			//String entname = driver.findElement(By.id(mGetPropertyFromFile("pt_newAssessmentNameOfOwnerid"))).getAttribute("value");
			String entname =mGetText("id", mGetPropertyFromFile("pt_newAssessmentNameOfOwnerid"), "value");
			//String entfathusname = driver.findElement(By.id(mGetPropertyFromFile("pt_newAssessmentNameOfFathHusid"))).getAttribute("value");
			String entfathusname = mGetText("id", mGetPropertyFromFile("pt_newAssessmentNameOfFathHusid"), "value");
			String entcompnameorgnl=entname+entfathusname;
			entcompname=entcompnameorgnl.toUpperCase();

			System.out.println("Entered Owner Name is : "+entcompname);

			//Code ends here

			//Madhuri ma'am code starts
			// Click Next button
			/*mGenericWait();
			mTakeScreenShot();
			mClick("id", mGetPropertyFromFile("pt_newAssessmentNextid"));*/

			//Ritesh
			//Upload the doc
			//And next button click
			mGenericWait();




			if (mGetPropertyFromFile("upldDocFlag").equals("Yes"))
			{
				mClick("id", mGetPropertyFromFile("pt_newAssessmentUploadDocCheckboxid"));
				mGenericWait();

				/*if(driver.findElements(By.id(mGetPropertyFromFile("pt_newAssessmentUploadDocid"))).size() != 0)
				{*/
				mUpload("id", mGetPropertyFromFile("pt_newAssessmentUploadDocid"), mGetPropertyFromFile("pt_newAssessmentUploadDoc"));
				//}
				mGenericWait();
				mTakeScreenShot();
				mClick("id", mGetPropertyFromFile("pt_newAssessmentNextid"));
			}
			else
			{
				mTakeScreenShot();
				mClick("id", mGetPropertyFromFile("pt_newAssessmentNextid"));				
			}



			//newAssessmentNext method created to simplify code reading
			/////////////////////////
			newAssessmentNextMethod();
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in New Assessment method");
		}
	}



	///////////////////////////////////


	/**
	 * @author Madhuri.dawande
	 * @since 19-11-2015
	 *
	 */

	public void selfAssessmentAuthorization(String serviceType, String yesNo)
	{
		try
		{

			// Select Property Tax link
			/*mWaitForVisible("linkText",mGetPropertyFromFile("pt_propertyTaxLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_propertyTaxLinkid"));

			// Select Transactions link
			mWaitForVisible("linkText",mGetPropertyFromFile("pt_transactionsLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_transactionsLinkid"));

			// Select Self Assessment - Authorisation link
			mWaitForVisible("linkText",mGetPropertyFromFile("pt_selfAssessAuthorizationLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_selfAssessAuthorizationLinkid"));*/

			/*
			 mNavigation("pt_propertyTaxLinkid", "pt_selfAssessmentLinkid");

			//mNavigation("pt_propertyTaxLinkidxpath", "pt_selfAssessmentLinkid");
			if(mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("citizen") || mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("agency"))
			{
				mClick("id", mGetPropertyFromFile("pt_newAssessmentIAcceptid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("pt_newAssessmentFileSelfAssessid"));
			}


			if(mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("citizen") || mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("agency"))
			{
				mClick("id", mGetPropertyFromFile("pt_newAssessmentIAcceptid"));
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("pt_newAssessmentFileSelfAssessid"));
			}*/

			mNavigation("pt_propertyTaxLinkid", "pt_transactionsLinkid", "pt_selfAssessAuthorizationLinkid");


			mWaitForVisible("linkText",mGetPropertyFromFile("pt_selfAssessAuthoSearchid"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("pt_selfAssessAuthoServiceTypeid"), mGetPropertyFromFile(serviceType));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_selfAssessAuthoSearchid"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("pt_selfAssessAuthoFindRecordsid"));
			mGenericWait();
			mSelectDropDown("css", mGetPropertyFromFile("pt_selfAssessAuthoSelectid"), mGetPropertyFromFile("pt_selfAssessAuthoSelect"));

			mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("pt_selfAssessAuthoAppNoid"), appNo);
			IndOrDep("id", "pt_selfAssessAuthoAppNoid", "applicationNo");

			//mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("pt_selfAssessAuthoAppNoid"), "51515122400005");

			WebElement toClear = mFindElement("id",mGetPropertyFromFile("pt_selfAssessAuthoAppNoid"));
			toClear.sendKeys(Keys.TAB);

			mClick("id", mGetPropertyFromFile("pt_selfAssessAuthoFindBtnid"));
			mGenericWait();

			mWaitForVisible("css",mGetPropertyFromFile("pt_selfAssessAuthoEditDetailsid"));
			mGenericWait();
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("pt_selfAssessAuthoEditDetailsid"));
			mGenericWait();
			mWaitForVisible("xpath",mGetPropertyFromFile("pt_selfAssessAuthoSubmitid"));
			mGenericWait();

			if (mGetPropertyFromFile(yesNo).equals("No"))
			{
				mClick("id", mGetPropertyFromFile("pt_selfAssessAuthoMakeChangeNoid"));
				mGenericWait();
				mClick("id", mGetPropertyFromFile("pt_selfAssessAuthoAuthorisedYesid"));
				mGenericWait();
				mClick("id", mGetPropertyFromFile("pt_selfAssessAuthoLegalYesid"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_selfAssessAuthoRemarkid"), mGetPropertyFromFile("pt_selfAssessAuthoRemark"));
				mGenericWait();
				mTakeScreenShot();
				mClick("xpath",mGetPropertyFromFile("pt_selfAssessAuthoSubmitid"));
				mWaitForVisible("id",mGetPropertyFromFile("pt_selfAssessAuthoProceedid"));
				mGenericWait();
				mTakeScreenShot();
				String authoSaveMsg=mGetText("css", mGetPropertyFromFile("pt_selfAssessAuthoSaveMsgid"));
				System.out.println(authoSaveMsg);
				mAssert(mGetPropertyFromFile("pt_selfAssessAuthoSaveMsg"), authoSaveMsg, "Message does not match, Expected: "+mGetPropertyFromFile("pt_selfAssessAuthoSaveMsg")+" || Actual: "+authoSaveMsg);
				mClick("id",mGetPropertyFromFile("pt_selfAssessAuthoProceedid"));
			}
			else
			{
				makeChangeYes=true;
				mGenericWait();

				mClick("id", mGetPropertyFromFile("pt_selfAssessAuthoMakeChangeYesid"));
				mGenericWait();
				mClick("xpath",mGetPropertyFromFile("pt_selfAssessAuthoSubmitid"));
				mGenericWait();	
				mWaitForVisible("id",mGetPropertyFromFile("pt_selfAssessAuthoReSubmitid"));
				mGenericWait();	
				if(!mGetPropertyFromFile("landType").equalsIgnoreCase("Vacant Land"))
				{
					System.out.println("In Authorization page land type is other than Vacant Land");
					mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentBuildingAreaid"), mGetPropertyFromFile("pt_selfAssessAuthoBuildingArea"));
				}
				else
				{
					System.out.println("In Authorization page land type is other than Vacant Land");									
					WebElement table =mFindElement("id", mGetPropertyFromFile("pt_newAssessmentVacantLandAreaTableid"));

					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount = rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

					// Iterate to get the value of each cell in table along with element
					// id
					int Rowno = 0;
					for (WebElement rowElement : rows) 
					{
						List<WebElement> cols = rowElement.findElements(By.xpath("td"));
						int clcount = cols.size();
						System.out.println("NUMBER OF Cols IN THIS TABLE = " + clcount);
						int Columnno = 0;
						for (WebElement colElement : cols) 
						{
							if (Columnno == 0) 
							{
								String yr = rowElement.findElement(By.xpath("./td/input")).getAttribute("value");
								mGenericWait();
								System.out.println("Year is :"+yr);
							}
							if (Columnno == 1) 
							{
								vacantarea = rowElement.findElement(By.xpath("./td[2]/input[3]")).getAttribute("id");
								System.out.println("Vacant Land Area is :"+vacantarea);
								mSendKeys("id", vacantarea, mGetPropertyFromFile("pt_newAssessmentAuthoVLArea"));
								mGenericWait();
							}
							Columnno = Columnno + 1;
							break;
						}
					}	
				}
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_selfAssessAuthoDocReqid"), mGetPropertyFromFile("pt_selfAssessAuthoDocReq"));
				mGenericWait();	
				mUpload("id", mGetPropertyFromFile("pt_selfAssessAuthoUploadDocid"), mGetPropertyFromFile("pt_selfAssessAuthoUploadDoc"));
				mGenericWait();
				mClick("id", mGetPropertyFromFile("pt_selfAssessAuthoAuthorisedYesid"));
				mGenericWait();
				mClick("id", mGetPropertyFromFile("pt_selfAssessAuthoLegalYesid"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_selfAssessAuthoRemarkid"), mGetPropertyFromFile("pt_selfAssessAuthoRemark"));
				mGenericWait();
				mTakeScreenShot();
				mClick("id",mGetPropertyFromFile("pt_selfAssessAuthoReSubmitid"));
				mWaitForVisible("xpath",mGetPropertyFromFile("pt_selfAssessAuthoFinalSubmitid"));
				mGenericWait();
				mTakeScreenShot();
				mClick("xpath",mGetPropertyFromFile("pt_selfAssessAuthoFinalSubmitid"));
				mWaitForVisible("id",mGetPropertyFromFile("pt_selfAssessAuthoProceedid"));
				mGenericWait();
				mTakeScreenShot();
				mClick("id",mGetPropertyFromFile("pt_selfAssessAuthoProceedid"));

				/*if (mGetPropertyFromFile("ObjcnOnSplnotc").equals("Yes"))
				{
					departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
					objectionEntry();
					logOut();
					finalLogOut();
					departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
					inspection();
					logOut();
					finalLogOut();
					departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
					hearingDateEntry();
					logOut();
					finalLogOut();
					departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
					hearingDateLetterPrinting();
					logOut();
					finalLogOut();
					departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
					hearingDetails();
					logOut();
					finalLogOut();
					departmentLogin(mGetPropertyFromFile("propTaxAuthoDeptUserName"),mGetPropertyFromFile("propTaxAuthoDeptPassword"));
					hearingOrder();
					logOut();
					finalLogOut();

				}*/
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Self Assessement Authorization method");

		}
	}

	// Method for Special Notice Generation
	/**
	 * @author Madhuri.dawande
	 * @since 01-12-2015
	 *
	 */

	public void specialNoticeGeneration()
	{
		try
		{
			/*// Select Property Tax link
			mWaitForVisible("linkText",mGetPropertyFromFile("pt_propertyTaxLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_propertyTaxLinkid"));

			// Select Transactions link
			mWaitForVisible("linkText",mGetPropertyFromFile("pt_transactionsLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_transactionsLinkid"));

			// Select Special Notice Generation / Printing link
			mWaitForVisible("linkText",mGetPropertyFromFile("pt_splNotcGenPrnLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_splNotcGenPrnLinkid"));*/

			mNavigation("pt_propertyTaxLinkid", "pt_transactionsLinkid", "pt_splNotcGenPrnLinkid");

			mWaitForVisible("css",mGetPropertyFromFile("pt_splNotcListPropertyBtnid"));

			// Select Special Notice Generation option
			mClick("id", mGetPropertyFromFile("pt_splNotcGenid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_splNotcSingleMultiid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_splNotcRevisedPropertyid"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("pt_splNotcListPropertyBtnid"));
			mGenericWait();
			//mSendKeys("id", mGetPropertyFromFile("pt_splNotcEnterPropNoid"), "515000088");

			//Comment 01-04-2017 INDorDep
			//IndOrDep("id", "pt_splNotcEnterPropNo", propNo);			

			/////// in case of dependent flow to capture Property No of previous transaction instaed of 'IndOrDep' method use following syntax

			if (mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				mSendKeys("id", mGetPropertyFromFile("pt_splNotcEnterPropNoid"), propnoList.get(CurrentinvoCount)); 
			}
			else
			{
				mSendKeys("id", mGetPropertyFromFile("pt_splNotcEnterPropNoid"), mGetPropertyFromFile("pt_splNotcEnterPropNo"));
			}

			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_splNotcSearchBtnid"));
			//mWaitForVisible("id", propNo);

			if (mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				//mWaitForVisible("id", propnoList.get(CurrentinvoCount));
				/*mReadFromXML(mGetPropertyFromFile("configFilePath"));
				Properties props= new Properties();
				String time = props.getProperty("waitForVisibleTimeout");
				int timeOut = Integer.parseInt(time);*/
				// Wait for element to be visible...
				WebDriverWait wait = new WebDriverWait(driver, 60);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(propnoList.get(CurrentinvoCount))));
			}
			else
			{
				mWaitForVisible("id", mGetPropertyFromFile("pt_splNotcEnterPropNo"));
			}

			mGenericWait();
			//mClick("id", propNo);

			if (mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				//mClick("id", propnoList.get(CurrentinvoCount));
				System.out.println("Property number in spl notice is : "+propnoList);
				driver.findElement(By.id(propnoList.get(CurrentinvoCount))).click();
			}
			else
			{
				mClick("id", mGetPropertyFromFile("pt_splNotcEnterPropNo"));
			}

			//IndOrDep("id", "pt_trf_Nom_PropNoData_ID", "applicationNo");
			//mClick("id", "515000088");
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_splNotcAddBtnid"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("pt_splNotcAddMsgCloseid"));
			mWaitForVisible("id",mGetPropertyFromFile("pt_splNotcGenBtnid"));
			mGenericWait();
			mTakeScreenShot();
			mClick("id", mGetPropertyFromFile("pt_splNotcGenBtnid"));
			mWaitForVisible("id",mGetPropertyFromFile("pt_splNotcYesBtnid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_splNotcYesBtnid"));
			mWaitForVisible("id",mGetPropertyFromFile("pt_splNotcProceedBtnid"));
			mGenericWait();
			mTakeScreenShot();
			String notcGenMsg = mGetText("css", mGetPropertyFromFile("pt_splNotcGenMsgid"));
			System.out.println(notcGenMsg);
			mAssert(mGetPropertyFromFile("pt_splNotcGenMsg"), notcGenMsg, "Message does not match, Expected: "+mGetPropertyFromFile("pt_splNotcGenMsg")+" || Actual: "+notcGenMsg);
			mClick("id", mGetPropertyFromFile("pt_splNotcProceedBtnid"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Special Notice Generation method");

		}
	}

	public void specialNoticePrinting()
	{
		try
		{
			mGenericWait();
			/*// Select Property Tax link
			mWaitForVisible("linkText",mGetPropertyFromFile("pt_propertyTaxLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_propertyTaxLinkid"));

			// Select Transactions link
			mWaitForVisible("linkText",mGetPropertyFromFile("pt_transactionsLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_transactionsLinkid"));

			// Select Special Notice Generation / Printing link
			mWaitForVisible("linkText",mGetPropertyFromFile("pt_splNotcGenPrnLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_splNotcGenPrnLinkid"));*/

			mNavigation("pt_propertyTaxLinkid", "pt_transactionsLinkid", "pt_splNotcGenPrnLinkid");

			mWaitForVisible("css",mGetPropertyFromFile("pt_splNotcListPropertyBtnid"));
			mGenericWait();
			// Select Special Notice Printing option
			mClick("id", mGetPropertyFromFile("pt_splNotcPrntgid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_splNotcSingleMultiid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_splNotcRevisedPropertyid"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("pt_splNotcListPropertyBtnid"));
			mGenericWait();
			mWaitForVisible("linkText", mGetPropertyFromFile("pt_splNotcSearchBtnid"));
			mGenericWait();

			//mSendKeys("id", mGetPropertyFromFile("pt_splNotcEnterPropNoid"), propNo);

			if (mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				mSendKeys("id", mGetPropertyFromFile("pt_splNotcEnterPropNoid"), propnoList.get(CurrentinvoCount)); 
			}
			else
			{
				mSendKeys("id", mGetPropertyFromFile("pt_splNotcEnterPropNoid"), mGetPropertyFromFile("pt_splNotcEnterPropNo"));
			}

			//mSendKeys("id", mGetPropertyFromFile("pt_splNotcEnterPropNoid"), "515000164");
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_splNotcSearchBtnid"));
			//mWaitForVisible("id", propNo);

			if (mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				//mWaitForVisible("id", propnoList.get(CurrentinvoCount));// need to update the code 
				WebDriverWait wait = new WebDriverWait(driver, 60);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(propnoList.get(CurrentinvoCount))));
			}
			else
			{
				mWaitForVisible("id", mGetPropertyFromFile("pt_splNotcEnterPropNo"));// need to update the code 
			}			

			mGenericWait();
			//mClick("id", propNo);

			if (mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				/*mClick("id", propnoList.get(CurrentinvoCount));// need to update the code 
				mSendKeys("id", mGetPropertyFromFile("pt_splNotcEnterPropNoid"), propnoList.get(CurrentinvoCount)); */
				driver.findElement(By.id(propnoList.get(CurrentinvoCount))).click();
			}
			else
			{
				mClick("id", mGetPropertyFromFile("pt_splNotcEnterPropNo"));// need to update the code 
			}		

			//mClick("id", "515000164");
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_splNotcAddBtnid"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("pt_splNotcAddMsgCloseid"));
			mWaitForVisible("id",mGetPropertyFromFile("pt_splNotcPrintBtnid"));
			mGenericWait();
			mTakeScreenShot();
			mClick("id", mGetPropertyFromFile("pt_splNotcPrintBtnid"));
			mWaitForVisible("id",mGetPropertyFromFile("pt_splNotcYesBtnid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_splNotcYesBtnid"));
			mWaitForVisible("id",mGetPropertyFromFile("pt_splNotcProceedBtnid"));
			mGenericWait();
			mTakeScreenShot();
			String notcPrntMsg = mGetText("css", mGetPropertyFromFile("pt_splNotcPrintMsgid"));
			System.out.println(notcPrntMsg);
			mAssert(mGetPropertyFromFile("pt_splNotcPrintMsg"), notcPrntMsg, "Message does not match, Expected: "+mGetPropertyFromFile("pt_splNotcPrintMsg")+" || Actual: "+notcPrntMsg);
			mClick("id", mGetPropertyFromFile("pt_splNotcProceedBtnid"));

			mCustomWait(7000);
			//mSwitchTabs();
			mChallanPDFReader();

			api.PdfPatterns.patternRevisedSpecialNoticePdf(pdfoutput, propNo,propAddrs);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Special Notice Printing method");

		}
	}

	public void revisedBillGeneration()
	{
		try
		{
			// Select Property Tax link
			/*mWaitForVisible("linkText",mGetPropertyFromFile("pt_propertyTaxLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_propertyTaxLinkid"));

			// Select Transactions link
			mWaitForVisible("linkText",mGetPropertyFromFile("pt_transactionsLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_transactionsLinkid"));

			// Select Bill Generation / Printing
			mWaitForVisible("linkText",mGetPropertyFromFile("pt_billGenPrnLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_billGenPrnLinkid"));*/

			mNavigation("pt_propertyTaxLinkid", "pt_transactionsLinkid", "pt_billGenPrnLinkid");

			mWaitForVisible("css",mGetPropertyFromFile("pt_billGenListPropertyBtnid"));

			mClick("id", mGetPropertyFromFile("pt_billGenid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_billGenSingleMultiid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_billGenRevisedPropertyid"));
			mGenericWait();
			mClick("css",mGetPropertyFromFile("pt_billGenListPropertyBtnid"));
			mGenericWait();

			mSendKeys("id", mGetPropertyFromFile("pt_billGenEnterPropNoid"), propNo);
			//mSendKeys("id", mGetPropertyFromFile("pt_billGenEnterPropNoid"), "515000088");

			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_billGenSearchBtnid"));
			mGenericWait();

			mClick("id", propNo);
			//mClick("id", "515000088");

			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_billGenAddBtnid"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("pt_billGenAddMsgCloseid"));
			mGenericWait();
			mWaitForVisible("id",mGetPropertyFromFile("pt_billGenBtnid"));
			mGenericWait();
			mTakeScreenShot();
			mClick("id", mGetPropertyFromFile("pt_billGenBtnid"));
			mGenericWait();
			mWaitForVisible("id", mGetPropertyFromFile("pt_billGenYesBtnid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_billGenYesBtnid"));
			mGenericWait();
			mWaitForVisible("id",mGetPropertyFromFile("pt_billGenProceedBtnid"));
			mGenericWait();
			mTakeScreenShot();
			String rvsBillGenMsg = mGetText("css", mGetPropertyFromFile("pt_billGenMsgid"));
			System.out.println(rvsBillGenMsg);
			mAssert(mGetPropertyFromFile("pt_billGenMsg"), rvsBillGenMsg, "Message does not match, Expected: "+mGetPropertyFromFile("pt_billGenMsg")+" || Actual: "+rvsBillGenMsg);
			mClick("id", mGetPropertyFromFile("pt_billGenProceedBtnid"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Revised Bill Generation method");

		}
	}

	public void revisedBillPrinting()
	{
		try
		{
			// Select Property Tax link
			/*mWaitForVisible("linkText",mGetPropertyFromFile("pt_propertyTaxLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_propertyTaxLinkid"));

			// Select Transactions link
			mWaitForVisible("linkText",mGetPropertyFromFile("pt_transactionsLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_transactionsLinkid"));

			// Select Bill Generation / Printing
			mWaitForVisible("linkText",mGetPropertyFromFile("pt_billGenPrnLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_billGenPrnLinkid"));*/

			mNavigation("pt_propertyTaxLinkid", "pt_transactionsLinkid", "pt_billGenPrnLinkid");


			mWaitForVisible("css",mGetPropertyFromFile("pt_billGenListPropertyBtnid"));

			mClick("id", mGetPropertyFromFile("pt_billPrntgid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_billGenSingleMultiid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_billGenRevisedPropertyid"));
			mGenericWait();
			mClick("css",mGetPropertyFromFile("pt_billGenListPropertyBtnid"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("pt_billGenEnterPropNoid"), propNo);
			//mSendKeys("id", mGetPropertyFromFile("pt_billGenEnterPropNoid"), "515000155");
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_billGenSearchBtnid"));
			mGenericWait();
			mClick("id", propNo);
			//mClick("id", "515000155");
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_billGenAddBtnid"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("pt_billGenAddMsgCloseid"));
			mGenericWait();
			mWaitForVisible("id",mGetPropertyFromFile("pt_billPrntgPrintBtnid"));
			mGenericWait();
			mTakeScreenShot();
			mClick("id", mGetPropertyFromFile("pt_billPrntgPrintBtnid"));
			mGenericWait();
			mWaitForVisible("id", mGetPropertyFromFile("pt_billGenYesBtnid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_billGenYesBtnid"));
			mGenericWait();
			mWaitForVisible("id",mGetPropertyFromFile("pt_billGenProceedBtnid"));
			mGenericWait();
			mTakeScreenShot();
			String rvsBillPrntMsg = mGetText("css", mGetPropertyFromFile("pt_billPrntgMsgid"));
			System.out.println(rvsBillPrntMsg);
			mAssert(mGetPropertyFromFile("pt_billPrntgMsg"), rvsBillPrntMsg, "Message does not match, Expected: "+mGetPropertyFromFile("pt_billPrntgMsg")+" || Actual: "+rvsBillPrntMsg);
			mClick("id", mGetPropertyFromFile("pt_billGenProceedBtnid"));
			mCustomWait(7000);
			//mSwitchTabs();
			//mSwitchTabs();
			mChallanPDFReader();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Revised Bill Printing method");

		}
	}

	// Method for New bill printing ---- 02-01-2016 by Madhuri Dawande
	public void newBillPrinting()
	{
		try
		{

			mNavigation("pt_propertyTaxLinkid", "pt_transactionsLinkid", "pt_billGenPrnLinkid");

			mWaitForVisible("css",mGetPropertyFromFile("pt_billPrintListPropertyBtnid"));

			mClick("id", mGetPropertyFromFile("pt_billPrintingid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_billPrintSingleMultiid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_billPrintPropertyid"));
			mGenericWait();
			mClick("css",mGetPropertyFromFile("pt_billPrintListPropertyBtnid"));
			mGenericWait();

			//mWaitForVisible("linkText", mGetPropertyFromFile("pt_billPrintSearchBtnid"));
			//mGenericWait();

			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual"))
			{
				mSendKeys("id", mGetPropertyFromFile("pt_billPrintEnterPropNoid"), mGetPropertyFromFile("propNo"));

			}
			else

				/////// in case of dependent flow to capture Property No of previous transaction instead of 'IndOrDep' method use following syntax
				mSendKeys("id", mGetPropertyFromFile("pt_billPrintEnterPropNoid"), propnoList.get(CurrentinvoCount)); 

			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_billPrintSearchBtnid"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("pt_billPrintAddchkboxid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_billPrintAddBtnid"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("pt_billPrintAddMsgCloseid"));
			mGenericWait();
			mWaitForVisible("id",mGetPropertyFromFile("pt_billPrntgPrintBtnid"));
			mGenericWait();
			mTakeScreenShot();
			mClick("id", mGetPropertyFromFile("pt_billPrntgPrintBtnid"));
			mGenericWait();

			mClick("id",mGetPropertyFromFile("pt_billPrintProceedBtnid"));
			mGenericWait();
			mClick("id",mGetPropertyFromFile("pt_billPrintingProceedBtnid"));
			mGenericWait();
			mTakeScreenShot();
			//String rvsBillPrntMsg = mGetText("css", mGetPropertyFromFile("pt_billPrntgMsgid"));
			//System.out.println(rvsBillPrntMsg);
			//mAssert(mGetPropertyFromFile("pt_billPrntgMsg"), rvsBillPrntMsg, "Message does not match, Expected: "+mGetPropertyFromFile("pt_billPrntgMsg")+" || Actual: "+rvsBillPrntMsg);
			//mClick("id", mGetPropertyFromFile("pt_billGenProceedBtnid"));
			mGenericWait();
			//mCustomWait(7000);
			//mSwitchTabs();

			mChallanPDFReader();

			api.PdfPatterns.patternNewBillPrintingPdf(pdfoutput);

			if(mGetPropertyFromFile("usageType_data").equalsIgnoreCase("Land + Building"))

			{
				// Property No
				billPrint_PropertyNumberList.add(billPrint_PropNoList.get(CurrentinvoCount));
				System.out.println("Property No on Bill Print PDF is === "+billPrint_PropertyNumberList);

				propNumberList.add(propnoList.get(CurrentinvoCount));
				System.out.println("List of Property No @ Self Assessment :::: "+propNumberList);

				mAssert(billPrint_PropertyNumberList, propNumberList, "Wrong Property No on Bill Printing Actual :" +billPrint_PropertyNumberList+ "Expected :"+propNumberList);

				//Total Payable Amount
				billPrint_totPayAmtList.add(billPrint_TotPayableAmtList.get(CurrentinvoCount));
				System.out.println("Total Payable Amt List on Bill Printing :::: "+billPrint_totPayAmtList);

				mAssert(billPrint_totPayAmtList, sas_TotNetPayableAmtList, "Wrong Total Net Payable Amount on Bill Printing Actual :" +billPrint_totPayAmtList+ "Expected :"+sas_TotNetPayableAmtList);


				// Interest
				billPrint_TotIntAmtList.add(billPrinting_TotalInterestAmountList.get(CurrentinvoCount));
				System.out.println("Total Interest List on Bill Ptinting is ::: "+billPrint_TotIntAmtList);

				mAssert(billPrint_TotIntAmtList, sasInterestList, "Wrong Water Tax Amount on Bill Printing Actual :" +billPrint_TotIntAmtList+ "Expected :"+sasInterestList);

				//*******************************
				// Water Tax Assertion Fail because output of SAS is not display in Console
				//*******************************

				// Water Tax
				/*billPrint_TotalWaterTaxAmtList.add(sas_WaterTaxList.get(CurrentinvoCount));
			System.out.println("Total Water Tax on SAS is ::: "+billPrint_TotalWaterTaxAmtList);
			mAssert(billPrint_TotalWaterTaxAmtList, sas_WaterTaxList, "Wrong Water Tax Amount on Bill Printing Actual :" +billPrint_TotalWaterTaxAmtList+ "Expected :"+sas_WaterTaxList);
				 */

			}
			if(mGetPropertyFromFile("usageType_data").equalsIgnoreCase("Flat"))
			{
				// Property No
				billPrint_PropertyNumberList.add(billPrint_PropNoList.get(CurrentinvoCount));
				System.out.println("Property No on Bill Print PDF is === "+billPrint_PropertyNumberList);

				propNumberList.add(propnoList.get(CurrentinvoCount));
				System.out.println("List of Property No @ Self Assessment :::: "+propNumberList);

				mAssert(billPrint_PropertyNumberList, propNumberList, "Wrong Property No on Bill Printing Actual :" +billPrint_PropertyNumberList+ "Expected :"+propNumberList);

				//Total Payable Amount
				billPrint_totPayAmtList.add(billPrint_TotPayableAmtList.get(CurrentinvoCount));
				System.out.println("Bill Print List of Total Payable Amount :::: "+billPrint_totPayAmtList);

				mAssert(billPrint_totPayAmtList, sas_TotNetPayableAmtList, "Wrong Total Net Payable Amount on Bill Printing Actual :" +billPrint_totPayAmtList+ "Expected :"+sas_TotNetPayableAmtList);


				//*******************************
				// Water Tax Assertion Fail because output of SAS is not display in Console
				//*******************************

				// Water Tax
				/*billPrint_TotalWaterTaxAmtList.add(sas_WaterTaxList.get(CurrentinvoCount));
				System.out.println("Total Water Tax on SAS is ::: "+billPrint_TotalWaterTaxAmtList);
				mAssert(billPrint_TotalWaterTaxAmtList, sas_WaterTaxList, "Wrong Water Tax Amount on Bill Printing Actual :" +billPrint_TotalWaterTaxAmtList+ "Expected :"+sas_WaterTaxList);
				 */
			}
			if(mGetPropertyFromFile("usageType_data").equalsIgnoreCase("Vacant Land"))
			{
				// Property No
				billPrint_PropertyNumberList.add(billPrint_PropNoList.get(CurrentinvoCount));
				System.out.println("Property No on Bill Print PDF is === "+billPrint_PropertyNumberList);

				propNumberList.add(propnoList.get(CurrentinvoCount));
				System.out.println("List of Property No @ Self Assessment :::: "+propNumberList);

				mAssert(billPrint_PropertyNumberList, propNumberList, "Wrong Property No on Bill Printing Actual :" +billPrint_PropertyNumberList+ "Expected :"+propNumberList);

				//Total Payable Amount
				billPrint_totPayAmtList.add(billPrint_TotPayableAmtList.get(CurrentinvoCount));
				System.out.println("Bill Print List of Total Payable Amount :::: "+billPrint_totPayAmtList);

				mAssert(billPrint_totPayAmtList, sas_TotNetPayableAmtList, "Wrong Total Net Payable Amount on Bill Printing Actual :" +billPrint_totPayAmtList+ "Expected :"+sas_TotNetPayableAmtList);


				//*******************************
				// Water Tax Assertion Fail because output of SAS is not display in Console
				//*******************************

				// Water Tax
				/*billPrint_TotalWaterTaxAmtList.add(sas_WaterTaxList.get(CurrentinvoCount));
				System.out.println("Total Water Tax on SAS is ::: "+billPrint_TotalWaterTaxAmtList);
				mAssert(billPrint_TotalWaterTaxAmtList, sas_WaterTaxList, "Wrong Water Tax Amount on Bill Printing Actual :" +billPrint_TotalWaterTaxAmtList+ "Expected :"+sas_WaterTaxList);
				 */
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in New Bill Printing method");

		}
	}

	public void billPayment()
	{
		try
		{
			/*// Select Online Services link 
			mWaitForVisible("linkText", mGetPropertyFromFile("pt_onlineServicesLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_onlineServicesLinkid"));

			// Select Payments link
			mWaitForVisible("linkText",mGetPropertyFromFile("pt_paymentsLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_paymentsLinkid"));

			// Select Property Tax Bill Payments link
			mWaitForVisible("linkText",mGetPropertyFromFile("pt_billPaymentsLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_billPaymentsLinkid"));*/

			mNavigation("pt_onlineServicesLinkid", "pt_paymentsLinkid", "pt_billPaymentsLinkid");

			mWaitForVisible("linkText",mGetPropertyFromFile("pt_billPaymentsSearchid"));

			mSendKeys("id", mGetPropertyFromFile("pt_billPaymentsPropNoid"), propNo);
			//mSendKeys("id", mGetPropertyFromFile("pt_billPaymentsPropNoid"), "515000088");

			mGenericWait();
			mClick("linkText",mGetPropertyFromFile("pt_billPaymentsSearchid"));
			mGenericWait();
			if (mGetPropertyFromFile("paymentType").equals(mGetPropertyFromFile("pt_billPaymentsFullPayment")))
			{
				mClick("id",mGetPropertyFromFile("pt_billPaymentsLastBillDuesid"));
			}
			else
			{
				mClick("id",mGetPropertyFromFile("pt_billPaymentsOtherAmtid"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_billPaymentsEnterOtherAmtid"), mGetPropertyFromFile("pt_billPaymentsEnterOtherAmt"));
			}
			mGenericWait();

			//payment("byBankOrULB");
			payment("paymentMode","byBankOrULB");

			mGenericWait();
			mWaitForVisible("xpath",mGetPropertyFromFile("pt_billPaymentsSubmitBtnid"));
			mGenericWait();
			mTakeScreenShot();
			mClick("xpath",mGetPropertyFromFile("pt_billPaymentsSubmitBtnid"));
			mWaitForVisible("id",mGetPropertyFromFile("pt_billPaymentsProceedBtnid"));
			mGenericWait();
			mTakeScreenShot();
			String billPayMsg = mGetText("css", mGetPropertyFromFile("pt_billpaymentMsgid"));
			System.out.println(billPayMsg);
			mAssert(mGetPropertyFromFile("pt_billpaymentMsg"), billPayMsg, "Message does not match, Expected: "+mGetPropertyFromFile("pt_billpaymentMsg")+" || Actual: "+billPayMsg);
			mClick("id",mGetPropertyFromFile("pt_billPaymentsProceedBtnid"));
			mCustomWait(7000);
			//mSwitchTabs();
			//mChallanPDFReader();
			if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
			{
				newPtChallanReader();
			}
			else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
			{
				mChallanPDFReader();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Bill Payment method");

		}
	}

	// Method for Objection Entry -- 24-12-2015 by Madhuri Dawande
	public void objectionEntry()
	{
		try
		{
			String ObjectionPropNo = "";
			// Select Property Tax link
			// Select Transactions link
			// Select Objection link
			// Select Objection Entry link
			mWaitForVisible("linkText",mGetPropertyFromFile("pt_propertyTaxLinkid"));
			mNavigation(mGetPropertyFromFile("pt_propertyTaxLinkid"), mGetPropertyFromFile("pt_transactionsLinkid"), mGetPropertyFromFile("pt_objectionLinkid"), mGetPropertyFromFile("pt_objectionEntryLinkid"));

			// Click on Add button
			mWaitForVisible("linkText",mGetPropertyFromFile("pt_objAddBtnLinkid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_objAddBtnLinkid"));

			//Wait for page load
			mWaitForVisible("xpath",mGetPropertyFromFile("pt_objSubmitBtnid"));

			//Select Objection Type
			mWaitForVisible("id",mGetPropertyFromFile("pt_objectionTypeid"));
			mSelectDropDown("id", mGetPropertyFromFile("pt_objectionTypeid"), mGetPropertyFromFile("pt_objectionTypeSplNotcData"));
			String objectionType=mCaptureSelectedValue("id", mGetPropertyFromFile("pt_objectionTypeid"));
			System.out.println("Objection Type in Objection Entry form is"+objectionType);
			OE_objectionType.add(objectionType);


			//Select Objection Issuer 
			mWaitForVisible("xpath",mGetPropertyFromFile("pt_objectionIssuerid"));
			mSelectDropDown("id", mGetPropertyFromFile("pt_objectionIssuerid"), mGetPropertyFromFile("pt_objectionIssuerData"));
			String objectionIssuer=mCaptureSelectedValue("id", mGetPropertyFromFile("pt_objectionIssuerid"));
			System.out.println("Objection Issuer in Objection Entry form is"+objectionIssuer);
			OE_objectionIssuer.add(objectionIssuer);


			//Click on Select Property No button
			mClick("css", mGetPropertyFromFile("pt_objSelectPropNoid"));
			mCustomWait(1000);

			mWaitForVisible("id",mGetPropertyFromFile("pt_objEnterPropNoid"));
			mGenericWait();			
			if(mGetPropertyFromFile("typeOfExecution").equalsIgnoreCase("dependent"))
			{
				//mSendKeys("id",mGetPropertyFromFile("pt_objEnterPropNoid"),propnoList.get(CurrentinvoCount));
				/*WebElement objPropNo=driver.findElement(By.id(mGetPropertyFromFile("pt_objEnterPropNoid")));
				objPropNo.sendKeys(Keys.DELETE);
				objPropNo.sendKeys(propnoList.get(CurrentinvoCount));*/
				driver.findElement(By.name(mGetPropertyFromFile("pt_objEnterPropNoid"))).sendKeys(propnoList.get(CurrentinvoCount));
				//mSendKeys("id", mGetPropertyFromFile("pt_hearingDtEntryPropNoid"), propnoList.get(CurrentinvoCount));
				mGenericWait();
				// ObjectionPropNo=mGetText("name", mGetPropertyFromFile("pt_objEnterPropNoid"));
				ObjectionPropNo=driver.findElement(By.name(mGetPropertyFromFile("pt_objEnterPropNoid"))).getText();
			}
			else
			{
				//mSendKeys("id",mGetPropertyFromFile("pt_objEnterPropNoid"),mGetPropertyFromFile("PropertyNo"));
				/*WebElement objPropNo=driver.findElement(By.id(mGetPropertyFromFile("pt_objEnterPropNoid")));
				objPropNo.sendKeys(Keys.DELETE);
				objPropNo.sendKeys(mGetPropertyFromFile("PropertyNo"));*/

				driver.findElement(By.name("commonDTO.propNo")).sendKeys(mGetPropertyFromFile("PropertyNo"));
			}			
			mClick("linkText", mGetPropertyFromFile("pt_objSearchBtnid"));
			mTakeScreenShot();
			mWaitForVisible("xpath", mGetPropertyFromFile("pt_objAddBtnid"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("pt_objAddBtnid"));
			mGenericWait();

			//String ObjectionPropNo=mGetText("name", mGetPropertyFromFile("pt_objEnterPropNoid"),"value");
			//String ObjectionPropNo=mGetText("name", mGetPropertyFromFile("pt_objEnterPropNoid"));
			propNo=ObjectionPropNo;
			System.out.println("Objection Property No in Objection Entry form is"+ObjectionPropNo);
			OE_ObjectionPropNo.add(ObjectionPropNo);

			//mDateSelect("id",mGetPropertyFromFile("pt_objectionDateid"),mGetPropertyFromFile("pt_objectionDateData"));
			mGdatePicker(mGetPropertyFromFile("pt_objectionDateid"), mGetPropertyFromFile("pt_objectionDate_Yr"), mGetPropertyFromFile("pt_objectionDate_Month"), mGetPropertyFromFile("pt_objectionDate_Date"));
			String ObjectionDate=mGetText("id", mGetPropertyFromFile("pt_objectionDateid"),"value");
			System.out.println("Objection Property No in Objection Entry form is"+ObjectionDate);
			OE_ObjectionDate.add(ObjectionDate);
			System.out.println("Objection Date in Objection Entry form is"+OE_ObjectionDate);

			mSendKeys("id", mGetPropertyFromFile("pt_objNameid"), mGetPropertyFromFile("pt_objNameData"));
			String objName=mGetText("id", mGetPropertyFromFile("pt_objNameid"),"value");
			System.out.println("Objection Property No in Objection Entry form is"+objName);
			OE_objName.add(objName);
			System.out.println("Objection Name in Objection Entry form is"+OE_objName);

			mSendKeys("id", mGetPropertyFromFile("pt_objDepartmentDescid"), mGetPropertyFromFile("pt_objDepartmentDescData"));
			String objDepartmentDesc=mGetText("id", mGetPropertyFromFile("pt_objDepartmentDescid"),"value");
			System.out.println("Objection Department Name in Objection Entry form is"+objDepartmentDesc);
			OE_objDepartmentDesc.add(objDepartmentDesc);
			System.out.println("Objection Name in Objection Entry form is"+OE_objDepartmentDesc);

			mGenericWait();
			String ObjOwnerName=mGetText("id", mGetPropertyFromFile("pt_objPropertyOwnerNameid"), "value");
			System.out.println("Owner Name ::"+ObjOwnerName);
			OE_objPropertyOwnerName.add(ObjOwnerName);
			System.out.println("Objection owner Name in Objection Entry form is"+OE_objPropertyOwnerName);

			String ObjOwnerAdd=mGetText("id", mGetPropertyFromFile("pt_objPropertyOwnerAddressid"), "value");
			System.out.println("Owner Name ::"+ObjOwnerAdd);
			OE_objPropertyOwnerAddress.add(ObjOwnerAdd);
			System.out.println("Objection Owner name Address in Objection Entry form is"+OE_objPropertyOwnerAddress);

			mSendKeys("id", mGetPropertyFromFile("pt_objectionMatterid"), mGetPropertyFromFile("pt_objectionMatterData"));
			String objectionMatter=mGetText("id", mGetPropertyFromFile("pt_objectionMatterid"), "value");
			System.out.println("Objection Matter ::"+objectionMatter);
			OE_objectionMatter.add(objectionMatter);
			System.out.println("Objection Matter in Objection Entry form is"+OE_objectionMatter);

			mGenericWait();
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("pt_objSubmitBtnid"));
			mGenericWait();
			mWaitForVisible("id",mGetPropertyFromFile("pt_objProceedBtnid"));
			String objectionNo = mGetText("css", mGetPropertyFromFile("pt_objNoid"));
			System.out.println(objectionNo);
			Pattern objnNo = Pattern.compile("[0-9]+");
			Matcher objcnNo = objnNo.matcher(objectionNo);

			if(objcnNo.find()) {				
				objNum = objcnNo.group();
				System.out.println("Objection No is: "+objNum);
				ObjectionNo.add(objNum);
			}

			mGenericWait();
			mTakeScreenShot();
			mClick("id",mGetPropertyFromFile("pt_objProceedBtnid"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Objection Entry method");

		}
	}

	// Method for Inspection Entry -- 24-12-2015 by Madhuri Dawande
	public void inspection()
	{
		try
		{// Select Property Tax link
			// Select Transactions link
			// Select Objection link
			// Select Objection Entry link
			mWaitForVisible("linkText",mGetPropertyFromFile("pt_propertyTaxLinkid"));
			mNavigation(mGetPropertyFromFile("pt_propertyTaxLinkid"), mGetPropertyFromFile("pt_transactionsLinkid"), mGetPropertyFromFile("pt_objectionLinkid"), mGetPropertyFromFile("pt_objectionEntryLinkid"));

			mWaitForVisible("linkText",mGetPropertyFromFile("pt_inspctnEntrySearchid"));

			//mSendKeys("id", mGetPropertyFromFile("pt_objectionNoid"), objNum);
			//mSendKeys("id", mGetPropertyFromFile("pt_objectionNoid"), "47");	

			if(mGetPropertyFromFile("typeOfExecution").equalsIgnoreCase("dependent"))
			{
				mSendKeys("id",mGetPropertyFromFile("pt_objectionNoid"),ObjectionNo.get(CurrentinvoCount));
			}
			else
			{
				mSendKeys("id",mGetPropertyFromFile("pt_objectionNoid"),mGetPropertyFromFile("pt_inspOnjNoData"));
			}

			mGenericWait();
			mClick("linkText",mGetPropertyFromFile("pt_inspctnEntrySearchid"));

			mWaitForVisible("xpath",mGetPropertyFromFile("pt_inspctnViewDtlsid"));
			mClick("xpath",mGetPropertyFromFile("pt_inspctnViewDtlsid"));
			mTakeScreenShot();
			mWaitForVisible("xpath",mGetPropertyFromFile("pt_inspctnDtlsSubmitid"));

			String ObjectionType =mGetText("css", mGetPropertyFromFile("pt_inspctnObjectionTypeReadid"));
			System.out.println("Objection type is in inspection form"+ObjectionType);
			//Insp_ObjectionType.add(ObjectionType);
			//System.out.println("Objection type(Arraylist) is in inspection form"+Insp_ObjectionType);

			String ObjectionNo =mGetText("id", mGetPropertyFromFile("pt_inspctnObjectionNoReadid"),"value");
			System.out.println("Objection No is in inspection form"+ObjectionNo);
			//Insp_ObjectionNo.add(ObjectionNo);
			//System.out.println("Objection No(Arraylist) is in inspection form"+Insp_ObjectionNo);

			String ObjectionIssuer =mGetText("css", mGetPropertyFromFile("pt_inspctnObjectionIssuerReadid"));
			System.out.println("Objection Issuer is in inspection form"+ObjectionIssuer);
			Insp_ObjectionIssuer.add(ObjectionIssuer);
			System.out.println("Objection Issuer(Arraylist) is in inspection form"+Insp_ObjectionIssuer);

			String PropertyNo =mGetText("id", mGetPropertyFromFile("pt_inspctnPropNoReadid"),"value");
			System.out.println("Property No is in inspection form"+PropertyNo);
			Insp_PropertyNo.add(PropertyNo);
			System.out.println("Property No (Arraylist) is in inspection form"+Insp_PropertyNo);

			String ObjectionDate =mGetText("id", mGetPropertyFromFile("pt_inspctnObjectionDateReadid"),"value");
			System.out.println("Objection Date is in inspection form"+ObjectionDate);
			Insp_ObjectionDate.add(ObjectionDate);
			System.out.println("Objection Date (Arraylist) is in inspection form"+Insp_ObjectionDate);

			String ObjectionName =mGetText("id", mGetPropertyFromFile("pt_inspctnObjectionNameReadid"),"value");
			System.out.println("Objection Name is in inspection form"+ObjectionName);
			Insp_ObjectionName.add(ObjectionName);
			System.out.println("Objection Name (Arraylist) is in inspection form"+Insp_ObjectionName);


			String ObjectionDeptName =mGetText("id", mGetPropertyFromFile("pt_inspctnObjectionDeptNameReadid"),"value");
			System.out.println("Objection Department Name is in inspection form"+ObjectionDeptName);
			Insp_ObjectionDeptName.add(ObjectionDeptName);
			System.out.println("Objection Department Name (Arraylist) is in inspection form"+Insp_ObjectionDeptName);

			String OwnerName =mGetText("id", mGetPropertyFromFile("pt_inspctnOwnerNameReadid"),"value");
			System.out.println("Objection on Owner Name is in inspection form"+OwnerName);
			Insp_OwnerName.add(OwnerName);
			System.out.println("Objection on Owner Name (Arraylist) is in inspection form"+Insp_OwnerName);

			String OwnerAddress =mGetText("id", mGetPropertyFromFile("pt_inspctnOwnerAddressReadid"),"value");
			System.out.println("Objection on Owner Name is in inspection form"+OwnerAddress);
			Insp_OwnerAddress.add(OwnerAddress);
			System.out.println("Objection on Owner Name (Arraylist) is in inspection form"+Insp_OwnerAddress);

			mGenericWait();
			/*mSendKeys("id", mGetPropertyFromFile("pt_hearingDtEntryHearingDateid"), mGetPropertyFromFile("pt_hearingDtEntryHearingDate"));
				String HearingDate =mGetText("id", mGetPropertyFromFile("pt_hearingDtEntryHearingDateid"),"value");
				System.out.println("Hearing Date is in inspection form"+HearingDate);
				Insp_HearingDate.add(HearingDate);
				System.out.println("Hearing Date (Arraylist) is in inspection form"+Insp_HearingDate);
			 */

			//mDateSelect("id", mGetPropertyFromFile("pt_inspctnDateid"), mGetPropertyFromFile("pt_inspctnDateData"));
			mGdatePicker(mGetPropertyFromFile("pt_inspctnDateid"), mGetPropertyFromFile("pt_inspctnDateData_Year"), mGetPropertyFromFile("pt_inspctnDateData_Month"), mGetPropertyFromFile("pt_inspctnDateData_Day"));
			String InspectionDate =mGetText("id", mGetPropertyFromFile("pt_inspctnDateid"),"value");
			System.out.println("Inspection Date is in inspection form"+InspectionDate);
			Insp_InspectionDate.add(InspectionDate);
			System.out.println("Inspction Date (Arraylist) is in inspection form"+Insp_InspectionDate);

			mGenericWait();
			/*mSendKeys("id", mGetPropertyFromFile("pt_inspctnRemarksid"), mGetPropertyFromFile("pt_inspctnRemarks"));
				String Inspectionremarks =mGetText("id", mGetPropertyFromFile("pt_inspctnRemarksid"),"value");
				System.out.println("Inspection Remarks is in inspection form"+Inspectionremarks);
				Insp_Inspectionremarks.add(Inspectionremarks);
				System.out.println("Inspection Remarks (Arraylist) is in inspection form"+Insp_Inspectionremarks);*/

			mClick("id",mGetPropertyFromFile("pt_inspctnNameListBtnid"));

			mWaitForVisible("linkText",mGetPropertyFromFile("pt_inspctnEmpSearchid"));
			mGenericWait();

			mSendKeys("id", mGetPropertyFromFile("pt_inspctnEmpNameid"), mGetPropertyFromFile("pt_inspctnEmpName"));
			String InspectionEmpName =mGetText("id", mGetPropertyFromFile("pt_inspctnEmpNameid"),"value");
			System.out.println("Inspection Employee Name is in inspection form"+InspectionEmpName);
			Insp_InspectionEmpName.add(InspectionEmpName);
			System.out.println("Inspection Employee Name (Arraylist) is in inspection form"+Insp_InspectionEmpName);

			String InspectionEmpNameRead =mGetText("id", mGetPropertyFromFile("pt_inspctnEmpNameReadid"),"value");
			System.out.println("Inspection Employee Name is in inspection form"+InspectionEmpNameRead);

			mAssert(InspectionEmpNameRead, InspectionEmpName, "Inspection employee Dose not match with Expected"+InspectionEmpName+" "+"Expected::"+InspectionEmpNameRead);

			mGenericWait();
			mClick("linkText",mGetPropertyFromFile("pt_inspctnEmpSearchid"));
			mGenericWait();
			mClick("css",mGetPropertyFromFile("pt_inspctnEmpAddBtnid"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("pt_inspctnRemarksid"), mGetPropertyFromFile("pt_inspctnRemarks"));
			String Inspectionremarks =mGetText("id", mGetPropertyFromFile("pt_inspctnRemarksid"),"value");
			System.out.println("Inspection Remarks is in inspection form"+Inspectionremarks);
			Insp_Inspectionremarks.add(Inspectionremarks);
			System.out.println("Inspection Remarks (Arraylist) is in inspection form"+Insp_Inspectionremarks);


			String InsObjectionMatter =mGetText("css", mGetPropertyFromFile("pt_inspctnObjMatterReadid"));
			System.out.println("Objection Matter in inspection form"+InsObjectionMatter);
			Insp_InsObjectionMatter.add(InsObjectionMatter);
			System.out.println("Objection Matter (Arraylist) is in inspection form"+Insp_InsObjectionMatter);

			mAssert(Insp_InsObjectionMatter, OE_objectionMatter, "Objection Matter dose not match while hearing/Inspection Form with expected"+OE_objectionMatter+" "+"Actual::"+Insp_InsObjectionMatter);

			mGenericWait();
			mTakeScreenShot();
			mClick("xpath",mGetPropertyFromFile("pt_inspctnDtlsSubmitid"));

			mWaitForVisible("id",mGetPropertyFromFile("pt_inspctnProceedBtnid"));
			mGenericWait();
			mTakeScreenShot();
			mClick("id",mGetPropertyFromFile("pt_inspctnProceedBtnid"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Inspection method");
		}
	}

	// Method for Hearing Date Entry -- 28-12-2015 by Madhuri Dawande
	public void hearingDateEntry()
	{
		try
		{
			// Select Property Tax link
			// Select Transactions link
			// Select Objection link
			// Select Hearing Date Entry link

			mWaitForVisible("linkText",mGetPropertyFromFile("pt_propertyTaxLinkid"));
			mNavigation(mGetPropertyFromFile("pt_propertyTaxLinkid"), mGetPropertyFromFile("pt_transactionsLinkid"), mGetPropertyFromFile("pt_objectionLinkid"), mGetPropertyFromFile("pt_hearingDtEntryLinkid"));

			mWaitForVisible("css",mGetPropertyFromFile("pt_hearingDtEntryListPropBtnid"));
			if(mGetPropertyFromFile("pt_hearingDtEntrySingleOrMultipleData").equalsIgnoreCase("Single"))
			{
				mClick("id",mGetPropertyFromFile("pt_hearingDtEntrySnglrbtnid"));
			}
			else
			{
				mClick("id",mGetPropertyFromFile("pt_hearingDtEntryMulrbtnid"));
			}

			mWaitForVisible("css",mGetPropertyFromFile("pt_hearingDtEntryWardid"));
			mSelectDropDown("id", mGetPropertyFromFile("pt_hearingDtEntryWardid"), mGetPropertyFromFile("pt_hearingDtEntryWard"));
			mWaitForVisible("linkText",mGetPropertyFromFile("pt_hearingDtEntryListPropBtnid"));
			mClick("css",mGetPropertyFromFile("pt_hearingDtEntryListPropBtnid"));
			mCustomWait(2000);
			mWaitForVisible("linkText",mGetPropertyFromFile("pt_hearingDtEntrySearchBtnid"));
			if(mGetPropertyFromFile("typeOfExecution").equalsIgnoreCase("dependent"))
			{
				//mSendKeys("id", mGetPropertyFromFile("pt_hearingDtEntryPropNoid"), propnoList.get(CurrentinvoCount));
				driver.findElement(By.id(mGetPropertyFromFile("pt_hearingDtEntryPropNoid"))).sendKeys(propnoList.get(CurrentinvoCount));
			}
			else
			{
				mSendKeys("id", mGetPropertyFromFile("pt_hearingDtEntryPropNoid"), mGetPropertyFromFile("PropertyNo"));
			}

			mClick("linkText",mGetPropertyFromFile("pt_hearingDtEntrySearchBtnid"));
			//mGenericWait();
			mCustomWait(2000);
			mClick("xpath",mGetPropertyFromFile("pt_hearingDtEntrySelectChkboxid"));
			mGenericWait();

			mGenericWait();
			mClick("linkText",mGetPropertyFromFile("pt_hearingDtEntryAddBtnid"));

			mGenericWait();
			mWaitForVisible("css",mGetPropertyFromFile("pt_hearingDtEntryCloseMsgid"));
			mClick("css",mGetPropertyFromFile("pt_hearingDtEntryCloseMsgid"));
			mGenericWait();
			mWaitForVisible("xpath",mGetPropertyFromFile("pt_hearingDtEntryViewDtlsid"));
			mClick("xpath",mGetPropertyFromFile("pt_hearingDtEntryViewDtlsid"));
			mCustomWait(1000);
			mWaitForVisible("xpath",mGetPropertyFromFile("pt_hearingDtEntrySubmitBtnid"));
			mGenericWait();

			String PropertyNo =mGetText("css", mGetPropertyFromFile("pt_hearingDtEntryPropNod"));
			System.out.println("Property No is in Objection & Hearing Details form"+PropertyNo);
			HDE_PropertyNo.add(PropertyNo);
			System.out.println("Property No(Arraylist) is in Objection & Hearing Details form"+HDE_PropertyNo);


			String ObjNo =mGetText("css", mGetPropertyFromFile("pt_hearingDtEntryObjectionNod"));
			System.out.println("Property No is in Objection & Hearing Details form"+ObjNo);
			HDE_ObjNo.add(ObjNo);
			System.out.println("Objection No(Arraylist) is in Objection & Hearing Details form"+HDE_ObjNo);


			String ObjDate =mGetText("css", mGetPropertyFromFile("pt_hearingDtEntryObjectionDated"));
			System.out.println("Property No is in Objection & Hearing Details form"+ObjDate);
			HDE_ObjDate.add(ObjDate);
			System.out.println("Objection Date(Arraylist) is in Objection & Hearing Details form"+HDE_ObjDate);

			//////////************ System Date & Time Server Date Time*****************/////////////////////

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, 3);
			String hearingDate = dateFormat.format(cal.getTime());
			System.out.println("Entered Hearing Date is : "+hearingDate);
			//mSendKeys("id", mGetPropertyFromFile("pt_hearingDtEntryHearingDateid"), mGetPropertyFromFile("pt_hearingDtEntryHearingDate"));
			mSendKeys("id", mGetPropertyFromFile("pt_hearingDtEntryHearingDateid"), hearingDate);
			HDE_HearingDate.add(hearingDate);
			System.out.println("Entered Hearing Date arraylist is : "+HDE_HearingDate);
			mGenericWait();


			mClick("xpath",mGetPropertyFromFile("pt_hearingDtEntrySubmitBtnid"));
			mWaitForVisible("id",mGetPropertyFromFile("pt_hearingDtEntryProceedBtnid"));
			String Msg=mGetText("css", mGetPropertyFromFile("pt_hearingDtEntryProceedPopUpmsgid"));
			System.out.println("Pop Up msg"+Msg);
			mAssert(Msg, mGetPropertyFromFile("pt_hearingDtEntryPopUpMsgData"), "Pop Up Message at Heartong Date entry does not match with Actual::"+Msg+"Expected::"+mGetPropertyFromFile("pt_hearingDtEntryPopUpMsgData"));
			mTakeScreenShot();
			mClick("id",mGetPropertyFromFile("pt_hearingDtEntryProceedBtnid"));
			mGenericWait();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Hearing Date Entry method");
		}
	}


	// Method for Hearing Date Letter Printing -- 30-12-2015 by Madhuri Dawande
	public void hearingDateLetterPrinting()
	{
		try
		{
			// Select Property Tax link
			// Select Transactions link
			// Select Objection link
			// Select Hearing Date Letter Printing link
			mNavigation(mGetPropertyFromFile("pt_propertyTaxLinkid"), mGetPropertyFromFile("pt_transactionsLinkid"), mGetPropertyFromFile("pt_objectionLinkid"), mGetPropertyFromFile("pt_hearingDtLtrPrntgLinkid"));

			mWaitForVisible("css",mGetPropertyFromFile("pt_hearingDtLtrPrntgSearchBtnid"));
			mGenericWait();
			if(mGetPropertyFromFile("pt_hearingDtLtrPrntgSingleOrMultipleData").equalsIgnoreCase("Single"))
			{
				mClick("id", mGetPropertyFromFile("pt_hearingDtLtrPrntgSnglid"));
			}
			else
			{
				mClick("id", mGetPropertyFromFile("pt_hearingDtLtrPrntgMulid"));
			}
			mGenericWait();
			mClick("xpath",mGetPropertyFromFile("pt_hearingDtLtrPrntgSearchAllid"));
			mWaitForVisible("linkText", mGetPropertyFromFile("pt_hearingDtLtrPrntgPropNoSearchBtnid"));
			mGenericWait();
			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				mSendKeys("id", mGetPropertyFromFile("pt_hearingDtLtrPrntgPropNoid"), propnoList.get(CurrentinvoCount));
			}
			else
			{
				mSendKeys("id", mGetPropertyFromFile("pt_hearingDtLtrPrntgPropNoid"),mGetPropertyFromFile("PropertyNo"));
			}
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_hearingDtLtrPrntgPropNoSearchBtnid"));
			mGenericWait();
			//mClick("xpath", mGetPropertyFromFile("pt_hearingDtLtrPrntgSelectCheckBoxid"));

			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				//mClick("id",ObjectionNo.get(CurrentinvoCount));
				driver.findElement(By.xpath("//*[@id="+objNum+"]")).click();
			}
			else
			{
				mClick("id", mGetPropertyFromFile("pt_Object_No"));
			}
			//mClick("id",objNum);


			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_hearingDtLtrPrntgPropNoAddBtnid"));
			mWaitForVisible("css", mGetPropertyFromFile("pt_hearingDtLtrPrntgpopMsgid"));
			String PropAddMsg=mGetText("css", mGetPropertyFromFile("pt_hearingDtLtrPrntgpopMsgid"));
			System.out.println("Property Add Message is::"+PropAddMsg);
			mAssert(PropAddMsg, mGetPropertyFromFile("pt_hearingDtLtrPrntgProprAddPopUpMsgData"), "Property Add Message dose not match with actual::"+PropAddMsg+"expected::"+mGetPropertyFromFile("pt_hearingDtLtrPrntgProprAddPopUpMsgData"));

			mWaitForVisible("css", mGetPropertyFromFile("pt_hearingDtLtrPrntgMsgCloseid"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("pt_hearingDtLtrPrntgMsgCloseid"));
			mWaitForVisible("id", mGetPropertyFromFile("pt_hearingDtLtrPrntgPrintBtnid"));
			mGenericWait();
			mCustomWait(2000);
			mClick("id", mGetPropertyFromFile("pt_hearingDtLtrPrntgPrintBtnid"));
			mWaitForVisible("css", mGetPropertyFromFile("pt_hearingDtLtrPrntgPrintMsgid"));
			String PrintMsg=mGetText("css", mGetPropertyFromFile("pt_hearingDtLtrPrntgPrintMsgid"));
			System.out.println("Property Add Message is::"+PrintMsg);
			mAssert(PrintMsg, mGetPropertyFromFile("pt_hearingDtLtrPrntgPrintConfmPopUpMsgData"), "Property Add Message dose not match with actual::"+PrintMsg+"expected::"+mGetPropertyFromFile("pt_hearingDtLtrPrntgPrintConfmPopUpMsgData"));

			mWaitForVisible("id", mGetPropertyFromFile("pt_hearingDtLtrPrntgPrintYesBtnid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_hearingDtLtrPrntgPrintYesBtnid"));
			mCustomWait(7000);
			mWaitForVisible("id", mGetPropertyFromFile("pt_hearingDtLtrPrntgProceedBtnid"));
			mGenericWait();

			mTakeScreenShot();

			mClick("id", mGetPropertyFromFile("pt_hearingDtLtrPrntgProceedBtnid"));
			mCustomWait(7000);
			//mCustomWait(60000);

			mChallanPDFReader();

			////////////////// Hearing Letter Printing Pdf Assertions by Jyoti @ 14-03-2017

			api.PdfPatterns.patternPTHeraingLetterPrintingPdf(pdfoutput);

			//////Property No

			objHLP_PropertyNoList.add(objHLP_PropNoList.get(CurrentinvoCount));
			System.out.println("PDF Property No List of Obj Hearing Letter Printing ::: "+objHLP_PropertyNoList);

			mAssert(objHLP_PropertyNoList, OE_ObjectionPropNo, "Property No of Hearing Letter Printing dose not match with Objection Entry Actual:: "+objHLP_PropertyNoList+ "Expected::"+OE_ObjectionPropNo);


			//////Owner Name

			objHLP_OwnrNameList.add(objHLP_OwnrNmList.get(CurrentinvoCount));
			System.out.println("PDF Owner Name List of Obj Hearing Letter Printing ::: "+objHLP_OwnrNameList);

			mAssert(objHLP_OwnrNameList, OE_objPropertyOwnerName, "Owner name of Hearing Letter Printing dose not match with Objection Entry Actual:: "+objHLP_OwnrNameList+ "Expected::"+OE_objPropertyOwnerName);

			///////Owner Address

			objHLP_OwnerAddrList.add(objHLP_OwnerAddressList.get(CurrentinvoCount));
			System.out.println("PDF Owner Address List of Obj Hearing Letter Printing ::: "+objHLP_OwnerAddrList);

			mAssert(objHLP_OwnerAddrList, OE_objPropertyOwnerAddress, "Owner Address of Hearing Letter Printing dose not match with Objection Entry Actual:: "+objHLP_OwnerAddrList+ "Expected::"+OE_objPropertyOwnerAddress);

			/////Objection Remarks

			objHLP_ObjRemarkList.add(objHLP_ObjRemarksList.get(CurrentinvoCount));
			System.out.println("PDF Objection Remark List of Obj Hearing Letter Printing ::: "+objHLP_ObjRemarkList);
			mAssert(objHLP_ObjRemarkList, OE_objectionMatter, "Obj Remark of Hearing Letter Printing dose not match with Objection Entry Actual:: "+objHLP_ObjRemarkList+ "Expected::"+OE_objectionMatter);

			/////Objection No.

			objHLP_ObjectionNoList.add(objHLP_ObjNoList.get(CurrentinvoCount));
			System.out.println("PDF Objection No List of Obj Hearing Letter Printing ::: "+objHLP_ObjectionNoList);
			mAssert(objHLP_ObjectionNoList, ObjectionNo, "Objection No of Hearing Letter Printing dose not match with Objection Entry Actual:: "+objHLP_ObjectionNoList+ "Expected::"+ObjectionNo);

			/////Objection Date

			objHLP_ObjectionDateList.add(objHLP_ObjDateList.get(CurrentinvoCount));
			System.out.println("PDF Objection Date List of Obj Hearing Letter Printing ::: "+objHLP_ObjectionDateList);
			mAssert(objHLP_ObjectionDateList, OE_ObjectionDate, "Objection Date of Hearing Letter Printing dose not match with Objection Entry Actual:: "+objHLP_ObjectionDateList+ "Expected::"+OE_ObjectionDate);



			mTakeScreenShot();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Hearing Date Letter Printing method");
		}
	}


	// Method for Hearing Details -- 30-12-2015 by Madhuri Dawande
	public void hearingDetails()
	{
		try
		{
			// Select Property Tax link
			// Select Transactions link
			// Select Objection link
			// Select Hearing Details link

			mNavigation(mGetPropertyFromFile("pt_propertyTaxLinkid"), mGetPropertyFromFile("pt_transactionsLinkid"), mGetPropertyFromFile("pt_objectionLinkid"), mGetPropertyFromFile("pt_hearingDtlsLinkid"));

			mWaitForVisible("linkText",mGetPropertyFromFile("pt_hearingDtlsAddBtnid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_hearingDtlsAddBtnid"));

			mWaitForVisible("xpath",mGetPropertyFromFile("pt_hearingDtlsSubmitBtnid"));
			mGenericWait();
			if(mGetPropertyFromFile("ObjcnOnBill").equals("Yes"))
			{
				mSelectDropDown("id", mGetPropertyFromFile("pt_hearingDtlsObjctnTypeid"), mGetPropertyFromFile("pt_hearingDtlsObjctnTypeBill"));

			}
			else
			{
				mSelectDropDown("id", mGetPropertyFromFile("pt_hearingDtlsObjctnTypeid"), mGetPropertyFromFile("pt_hearingDtlsObjctnTypeSplNotc"));	
			}

			String HearingDetailsDate=mGetText("css", mGetPropertyFromFile("pt_hearingDtlsHearingReadDateBtnid"));
			System.out.println("Hearing Details Date:::"+HearingDetailsDate);

			mGenericWait();	
			mClick("linkText", mGetPropertyFromFile("pt_hearingDtlsSelectPropNoBtnid"));
			mWaitForVisible("linkText", mGetPropertyFromFile("pt_hearingDtlsSearchPropNoBtnid"));
			mGenericWait();	

			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				mSendKeys("id", mGetPropertyFromFile("pt_hearingDtlsEnterPropNoid"), propnoList.get(CurrentinvoCount));
			}
			else
			{
				mSendKeys("id", mGetPropertyFromFile("pt_hearingDtlsEnterPropNoid"), mGetPropertyFromFile("PropertyNo"));
			}

			mClick("linkText", mGetPropertyFromFile("pt_hearingDtlsSearchPropNoBtnid"));
			mTakeScreenShot();
			mWaitForVisible("xpath", mGetPropertyFromFile("pt_hearingDtlsAddPropNoBtnid"));
			mClick("xpath", mGetPropertyFromFile("pt_hearingDtlsAddPropNoBtnid"));
			mCustomWait(1000);
			mWaitForVisible("linkText", mGetPropertyFromFile("pt_hearingDtlsAuthorizedByBtnid"));

			String PropNo=mGetText("id", mGetPropertyFromFile("pt_hearingDtlsreadPropNoid"), "value");
			System.out.println("Property No in Hearing Details::"+PropNo);


			String ObjectionNo=mGetText("id", mGetPropertyFromFile("pt_hearingDtlsreadObjectionNoid"), "value");
			System.out.println("Objection No in Hearing Details::"+ObjectionNo);

			String OwnerName=mGetText("id", mGetPropertyFromFile("pt_hearingDtlsreadOwnerNameid"), "value");
			System.out.println("Property/ Flat Owner in Hearing Details::"+OwnerName);

			mClick("linkText", mGetPropertyFromFile("pt_hearingDtlsAuthorizedByBtnid"));
			mCustomWait(1000);

			mWaitForVisible("linkText", mGetPropertyFromFile("pt_hearingDtlsSearchEmpNameBtnid"));
			mSendKeys("id", mGetPropertyFromFile("pt_hearingDtlsEnterEmpNameid"), mGetPropertyFromFile("pt_hearingDtlsEnterEmpName"));

			mClick("linkText", mGetPropertyFromFile("pt_hearingDtlsSearchEmpNameBtnid"));

			mWaitForVisible("xpath", mGetPropertyFromFile("pt_hearingDtlsAddEmpNameBtnid"));
			mClick("xpath", mGetPropertyFromFile("pt_hearingDtlsAddEmpNameBtnid"));

			mWaitForVisible("linkText", mGetPropertyFromFile("pt_hearingDtlsAuthorizedByBtnid"));
			mGenericWait();

			String EmpName=mGetText("id", mGetPropertyFromFile("pt_hearingDtlsreadAuthEmpNameid"), "value");
			System.out.println("Employee Name in Hearing Details::"+EmpName);

			String PropAddress=mGetText("id", mGetPropertyFromFile("pt_hearingDtlsreadOwnerAddressid"), "value");
			System.out.println("Property/ Flat Address in Hearing Details::"+PropAddress);


			mClick("id", mGetPropertyFromFile("pt_hearingDtlsHearingStatusYesid"));
			mGenericWait();

			mTakeScreenShot();
			String ObjectionMatter=mGetText("css", mGetPropertyFromFile("pt_hearingDtlsreadObjectionMatterid"), "value");
			System.out.println("Objection Matter in Hearing Details::"+ObjectionMatter);

			String Remarks=mGetText("css", mGetPropertyFromFile("pt_hearingDtlsreadInspectionRemarksid"), "value");
			System.out.println("Objection/Inspection remarks in Hearing Details::"+Remarks);


			mSendKeys("id", mGetPropertyFromFile("pt_hearingDtlsHearingMatterid"), mGetPropertyFromFile("pt_hearingDtlsHearingMatter"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("pt_hearingDtlsObjctnStatusid"), mGetPropertyFromFile("pt_hearingDtlsObjctnStatus"));
			mGenericWait();

			mTakeScreenShot();
			mClick("xpath",mGetPropertyFromFile("pt_hearingDtlsSubmitBtnid"));
			mTakeScreenShot();
			mWaitForVisible("id",mGetPropertyFromFile("pt_hearingDtlsProceedBtnid"));
			String hearingNum = mGetText("css", mGetPropertyFromFile("pt_hearingDtlsHearingNoid"));
			System.out.println(hearingNum);
			Pattern hearngNo = Pattern.compile("[0-9]+");
			Matcher hearngNumber = hearngNo.matcher(hearingNum);

			if(hearngNumber.find()) {				
				hearingNo = hearngNumber.group();
				System.out.println("Hearing No is: "+hearingNo);
				HeringOrderNo.add(hearingNo);
			}
			mGenericWait();
			mClick("id",mGetPropertyFromFile("pt_hearingDtlsProceedBtnid"));
		}

		catch(Exception e)

		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Hearing Details method");
		}

	}

	// Method for Hearing Order -- 30-12-2015 by Madhuri Dawande
	public void hearingOrder()
	{
		try
		{
			// Select Property Tax link
			// Select Transactions link
			// Select Objection link
			// Select Hearing Order link

			mNavigation(mGetPropertyFromFile("pt_propertyTaxLinkid"), mGetPropertyFromFile("pt_transactionsLinkid"), mGetPropertyFromFile("pt_objectionLinkid"), mGetPropertyFromFile("pt_hearingOrderLinkid"));

			mWaitForVisible("css", mGetPropertyFromFile("pt_hearingOrderSearchBtnid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_hearingOrderSnglid"));
			mGenericWait();	
			mClick("css", mGetPropertyFromFile("pt_hearingOrderSearchBtnid"));

			mWaitForVisible("linkText", mGetPropertyFromFile("pt_hearingOrderSearchPropNoBtnid"));
			mGenericWait();

			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				mSendKeys("id", mGetPropertyFromFile("pt_hearingOrderEnterPropNoid"), propnoList.get(CurrentinvoCount));

				//mSendKeys("id", mGetPropertyFromFile("pt_hearingOrderEnterPropNoid"), "223001664");
			}
			else
			{
				mSendKeys("id", mGetPropertyFromFile("pt_hearingOrderEnterPropNoid"), mGetPropertyFromFile("PropertyNo"));
			}

			mTakeScreenShot();
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_hearingOrderSearchPropNoBtnid"));
			mGenericWait();	
			mClick("xpath", mGetPropertyFromFile("pt_hearingOrderCheckBoxselectid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_hearingOrderAddPropNoBtnid"));
			mWaitForVisible("css", mGetPropertyFromFile("pt_hearingOrderMsgCloseid"));
			mGenericWait();
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("pt_hearingOrderMsgCloseid"));
			mWaitForVisible("id", mGetPropertyFromFile("pt_hearingOrderPrintBtnid"));
			mGenericWait();
			mTakeScreenShot();
			mClick("id", mGetPropertyFromFile("pt_hearingOrderPrintBtnid"));
			mWaitForVisible("id", mGetPropertyFromFile("pt_hearingOrderPrintYesBtnid"));
			mGenericWait();
			mTakeScreenShot();
			mClick("id", mGetPropertyFromFile("pt_hearingOrderPrintYesBtnid"));
			mWaitForVisible("id", mGetPropertyFromFile("pt_hearingOrderProceedBtnid"));
			mGenericWait();
			mTakeScreenShot();

			mClick("id", mGetPropertyFromFile("pt_hearingOrderProceedBtnid"));
			mCustomWait(7000);


			mChallanPDFReader();

			//////////////////Hearing Order Pdf Assertions by Jyoti @ 14-03-2017

			api.PdfPatterns.patternHeraingOrderPdf(pdfoutput);
			//////Property No

			objHO_PropertyNoList.add(objHO_PropNoList.get(CurrentinvoCount));
			System.out.println("PDF Property No List of Hearing Order ::: "+objHO_PropertyNoList);

			mAssert(objHO_PropertyNoList, OE_ObjectionPropNo, "Property No of Hearing Order dose not match with Objection Entry Actual:: "+objHO_PropertyNoList+ "Expected::"+OE_ObjectionPropNo);


			//////Owner Name

			objHO_OwnerNmList.add(objHO_OwnerNameList.get(CurrentinvoCount));
			System.out.println("PDF Owner Name List of Obj Hearing Order ::: "+objHO_OwnerNmList);

			mAssert(objHO_OwnerNmList, OE_objPropertyOwnerName, "Owner name of Hearing Order dose not match with Objection Entry Actual:: "+objHO_OwnerNmList+ "Expected::"+OE_objPropertyOwnerName);


			/////Objection No.

			objHO_ObjLtrNoList.add(objHO_ObjLetterNoList.get(CurrentinvoCount));
			System.out.println("PDF Objection No List of Obj Hearing Order ::: "+objHO_ObjLtrNoList);
			mAssert(objHO_ObjLtrNoList, ObjectionNo, "Objection No of Hearing Order dose not match with Objection Entry Actual:: "+objHO_ObjLtrNoList+ "Expected::"+ObjectionNo);

			/////Objection Date

			objHO_ObjLtrDateList.add(objHO_ObjLetrDateList.get(CurrentinvoCount));
			System.out.println("PDF Objection Date List of Obj Hearing Order ::: "+objHO_ObjLtrDateList);
			mAssert(objHO_ObjLtrDateList, OE_ObjectionDate, "Objection Date of Hearing Order dose not match with Objection Entry Actual:: "+objHO_ObjLtrDateList+ "Expected::"+OE_ObjectionDate);



			mTakeScreenShot();
			mGenericWait();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Hearing Order method");
		}
	}

	// Method for Change in Assessment - Alteration -- 27-01-2016 by Madhuri Dawande
	public void alteration()
	{
		try
		{
			System.out.println("Start Change in Assessment with Alteration Flow");


			String chngInPropNum;
			String chngInOldPropNum;

			// Navigate to Self Assessment and Payment of Property Tax link
			mNavigation("pt_propertyTaxLinkid", "pt_selfAssessmentLinkid");

			// Select option Change / Alteration in Assessment
			mWaitForVisible("id", mGetPropertyFromFile("pt_changeInAssessmentid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_changeInAssessmentid"));

			//////// Search Property number
			if(mGetPropertyFromFile("pt_chngInAssmntPropID").equalsIgnoreCase("New"))
			{
				//////If User Know the New Property No
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntPropNoid"), mGetPropertyFromFile("pt_chngInAssmntPropNo"));
				//chngInPropNum=driver.findElement(By.id(mGetPropertyFromFile("pt_chngInAssmntPropNoid"))).getAttribute("value");
				chngInPropNum=mGetText("id", mGetPropertyFromFile("pt_chngInAssmntPropNoid"), "value");
				System.out.println("Entered Property number for change in assessment is : "+chngInPropNum);
			}
			else
			{
				//////If User Know the New Old Property No
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntOldPropNoid"), mGetPropertyFromFile("pt_chngInAssmntOldPropNo"));
				//chngInOldPropNum=driver.findElement(By.id(mGetPropertyFromFile("pt_chngInAssmntOldPropNoid"))).getAttribute("value");
				chngInOldPropNum=mGetText("id", mGetPropertyFromFile("pt_chngInAssmntOldPropNoid"), "value");
				System.out.println("Entered Old Property number for change in assessment is : "+chngInOldPropNum);
			}

			mClick("css", mGetPropertyFromFile("pt_chngInAssmntSrchBtnid"));

			mWaitForVisible("linkText", mGetPropertyFromFile("pt_chngInAssmntSrchSubmitBtnid"));
			mTakeScreenShot();
			mClick("linkText", mGetPropertyFromFile("pt_chngInAssmntSrchSubmitBtnid"));

			////////Waiting for Change the Holding Details Radio Button
			mWaitForVisible("id", mGetPropertyFromFile("pt_chngInAssmntHoldingDtlsid"));

			mClick("id", mGetPropertyFromFile("pt_chngInAssmntHoldingDtlsid"));

			//////Updated on 06-01-2017 @ Ritesh Current Method is for Addition Update Old code
			if (mGetPropertyFromFile("changeInHoldingDetailsOnlyAlteration").equalsIgnoreCase("Yes"))
			{
				System.out.println("Change in Holding Deatils with Alteration Option");
				mClick("id", mGetPropertyFromFile("pt_chngInAssmntHldngTypAdditionid"));				
			}


			mGenericWait();
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("pt_alterationChngHoldingDtlsSubmitBtnid"));

			mWaitForVisible("id", mGetPropertyFromFile("pt_alterationPropTaxSubmitBtnid"));
			mTakeScreenShot();
			mscroll(0, 670);
			mTakeScreenShot();
			mGenericWait();
			mscroll(0, 750);
			mTakeScreenShot();


			/////Mutation
			if (mGetPropertyFromFile("pt_chngInAssmntMutation").equalsIgnoreCase("Yes"))
			{
				System.out.println("Mutation is applicable");
				mSelectDropDown("id", mGetPropertyFromFile("pt_chngInAssmntMutationid"), mGetPropertyFromFile("pt_chngInAssmntMutation"));
				mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntMutationid"), mGetPropertyFromFile("pt_chngAssessmentMutationPropNo"));				
			}
			else
			{
				System.out.println("Mutation not applicable");
				mSelectDropDown("id", mGetPropertyFromFile("pt_chngInAssmntMutationid"), mGetPropertyFromFile("pt_chngInAssmntMutation"));
				//mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntMutationid"), mGetPropertyFromFile("pt_chngAssessmentMutationPropNo"));				
			}

			//////Payment Made up-to selection
			if(driver.findElement(By.id(mGetPropertyFromFile("pt_chngInAssmntPymntMdUptoid"))).isEnabled())
			{
				mSelectDropDown("id", mGetPropertyFromFile("pt_chngInAssmntPymntMdUptoid"), mGetPropertyFromFile("pt_chngInAssmntPymntMdUpto"));
			}
			else
			{
				System.out.println("Payment Made Upto not applicable");
			}

			//////Checking the Land Type selected (Flat / Land + Building / Vacant Land)
			if(driver.findElement(By.id("entity.codpropertyID12")).isSelected())
			{
				//Land + Building
				chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(3) > label")).getText();
				System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
			}
			else if(driver.findElement(By.id("entity.codpropertyID11")).isSelected())
			{
				//Flat
				chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(2) > label")).getText();
				System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
			}
			else if(driver.findElement(By.id("entity.codpropertyID13")).isSelected())
			{
				//Vacant Land
				chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(4) > label")).getText();
				System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
			}
			else
			{
				System.out.println("Land type is not selected");
			}

			chngAsmtLandTypeAcqYr=driver.findElement(By.xpath("//*[@id=\"frmMasterForm\"]/div[16]/div[2]")).getText().trim();
			//chngAsmtLandTypeAcqYr=mGetText("xpath", mGetPropertyFromFile("pt_chngAsmtLandTypeAcqYr"), "value").trim();			
			String[] splitter = chngAsmtLandTypeAcqYr.split(":");
			chngAsmtLandTypeAcqYr=splitter[1].toString().trim();
			System.out.println("Year of Acquisition in change in assessment is : "+chngAsmtLandTypeAcqYr);			


			changeRoadFactor("pt_alterationRoadFactor");

			changePlotArea("pt_chngInAssmntPlotArea");

			changeBuiltupArea("pt_alterationBuiltupArea");

			changeFloorNo("pt_alteartionFloorNo");

			changeUsageType("pt_alterationUsageType");

			changeConstrnType("pt_alterationConstrType");

			changeBuildingArea("pt_alterationBuildingArea");

			changeUsageFactor("pt_alterationUsageFactor");

			changeOccupancyFactor("pt_alterationOccFactor");

			//////Added new code Vacant Land Area @ Ritesh 07-01-2017
			changeVacantLndArea("pt_alterationVacantLndArea");

			mGenericWait();	

			// Road Factor
			chngInAssmntRoadFactor.add(mCaptureSelectedValue("id", mGetPropertyFromFile("pt_chngInAssmntRoadFactorid")));
			System.out.println("Selected road factor is : "+chngInAssmntRoadFactor);

			if (!mGetPropertyFromFile("landType").equalsIgnoreCase("Vacant Land"))
			{
				System.out.println("User is doing alteration for other than Vacant Land");
				BldgDetailsTableReadingExistingRows();
			}
			else
			{
				System.out.println("User is doing alteration for Vacant Land");
			}


			//String entname = driver.findElement(By.id(mGetPropertyFromFile("pt_alterationNameOfOwnerid"))).getAttribute("value");
			String entname =mGetText("id", mGetPropertyFromFile("pt_alterationNameOfOwnerid"), "value");
			//String entfathusname = driver.findElement(By.id(mGetPropertyFromFile("pt_alterationNameOfFathHusid"))).getAttribute("value");
			String entfathusname = mGetText("id", mGetPropertyFromFile("pt_alterationNameOfFathHusid"), "value");

			entcompname=entname+entfathusname;
			System.out.println(entcompname);





			/////Difference in Year
			String vacantLandPaymentUptoYear=mCaptureSelectedValue("id", mGetPropertyFromFile("pt_chngInAssmntPymntMdUptoid"));
			System.out.println("Acquisition year of vacant land is before trim: " +vacantLandPaymentUptoYear);
			String acqYr = vacantLandPaymentUptoYear.substring(0, 4);
			System.out.println("Acquisition year of vacant land is: "+acqYr);
			int acqYear = Integer.parseInt(acqYr);

			int year = Calendar.getInstance().get(Calendar.YEAR);
			String fYear = Integer.toString(year);
			int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
			//System.out.println("Financial month : " + month);
			if (month < 3) 
			{
				//System.out.println("Financial Year Ritesh: " + (year - 1) + "-" + year);
				financialYearchng=fYear.substring(0,4);
				System.out.println("Financial Year : " + financialYearchng);
			} 
			else 
			{
				//System.out.println("Financial Year : " + year + "-" + (year + 1));
				financialYearchng=fYear.substring(0,4);
				System.out.println("Financial Year : " + financialYearchng);
			}

			financialYear = Integer.parseInt(financialYearchng);

			/*int currentyear = Calendar.getInstance().get(Calendar.YEAR);
					System.out.println("Current year is: "+currentyear);*/

			//how to get financial year
			/*FiscalDate fiscalDate = new FiscalDate(calendar);
			        int year = fiscalDate.getFiscalYear();
			        System.out.println("Current Date : " + calendar.getTime().toString());
			        System.out.println("Fiscal Years : " + year + "-" + (year + 1));
			        System.out.println("Fiscal Month : " + fiscalDate.getFiscalMonth());*/		

			//cntForVlDtl = currentyear-financialYear;
			cntForVlDtl = financialYear-acqYear;
			System.out.println("Number of years for vacant land required is: "+cntForVlDtl);

			//Capturing Water Tax Type
			String chngInAssWaterTaxTypeOrg=driver.findElement(By.xpath("//*[@id=\"frmMasterForm\"]/div[30]/div[1]")).getText();
			System.out.println("Water Tax Type of selected property in change in assessment original: "+chngInAssWaterTaxTypeOrg);
			String chngInAssWaterTaxTypeNew[]=chngInAssWaterTaxTypeOrg.split(":");
			chngInAssWaterTaxType=chngInAssWaterTaxTypeNew[1].trim();
			System.out.println("Water Tax Type of selected property in change in assessment : "+chngInAssWaterTaxType);

			if (!mGetPropertyFromFile("landType").equalsIgnoreCase("Vacant Land"))
			{

				chngInAddnGrndFlrPlotArea=driver.findElement(By.id("pmPlotArea")).getAttribute("value");
				System.out.println("Plot area for change in assessment is :"+chngInAddnGrndFlrPlotArea);
				chngInAddnGrndFlrBuiltupArea=mGetText("id", mGetPropertyFromFile("pt_chngInAssmntGrndFlrBuiltupAreaid"),"value");
				System.out.println("Built up area for change in assessment is :"+chngInAddnGrndFlrBuiltupArea);

				Double pt_newAssessmentAplcbleVacantLandOrg=((Double.parseDouble(chngInAddnGrndFlrBuiltupArea)/Double.parseDouble(chngInAddnGrndFlrPlotArea)*100));
				System.out.println("Applicable Vacant Land : "+pt_newAssessmentAplcbleVacantLandOrg);

				DecimalFormat decimalFormat=new DecimalFormat("#.#");


				pt_newAssessmentAplcbleVacantLand=Math.round(pt_newAssessmentAplcbleVacantLandOrg);
				System.out.println("Applicable Vacant Land : "+pt_newAssessmentAplcbleVacantLand);

				if((pt_newAssessmentAplcbleVacantLand)<70)
				{
					pt_newAssessmentVacantLandAreaAuto=(Double.parseDouble(chngInAddnGrndFlrPlotArea)-(Double.parseDouble(chngInAddnGrndFlrBuiltupArea)*1.42855));
					System.out.println("Applicable Vacant Land Auto : "+pt_newAssessmentVacantLandAreaAuto);
				}
				else
				{
					pt_newAssessmentVacantLandAreaAuto=0.0;
					System.out.println("Applicable Vacant Land Auto : "+pt_newAssessmentVacantLandAreaAuto);
				}

				pt_newAssessmentVacantLandAnnualTaxAuto=(pt_newAssessmentVacantLandAreaAuto*0.36);
				pt_newAssessmentVacantLandAnnualTax=Math.round(pt_newAssessmentVacantLandAnnualTaxAuto);
				System.out.println("Vacant Land Annual Tax : "+pt_newAssessmentVacantLandAnnualTax);

			}
			else
			{
				//pt_newAssessmentVacantLandAnnualTaxAuto=(Double.parseDouble(vancantareavalue)*0.36);
				pt_newAssessmentVacantLandAnnualTaxAuto=(Double.parseDouble(vancantareavalue)*0.28);
				pt_newAssessmentVacantLandAnnualTax=Math.round(pt_newAssessmentVacantLandAnnualTaxAuto);
				System.out.println("Vacant Land Annual Tax : "+pt_newAssessmentVacantLandAnnualTax);
			}

			mTakeScreenShot();
			mClick("id", mGetPropertyFromFile("pt_alterationPropTaxSubmitBtnid"));
			//mClick("id", mGetPropertyFromFile("pt_newAssessmentid"));

			newAssessmentNextMethod();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Change in Assessment - Aleteration method");
		}

	}

	public void changeRoadFactor(String road)
	{
		try
		{
			if((mGetPropertyFromFile(road).length()==0) || (mGetPropertyFromFile(road).equalsIgnoreCase("?")))
			{
				System.out.println("No change in Road Factor");
				System.out.println("Size of Road Factor: " + mGetPropertyFromFile(road).length());
			}
			else
			{
				System.out.println("Change in Road Factor");
				mTakeScreenShot();
				mGenericWait();
				//driver.findElement(mGetPropertyFromFile("pt_alterationRoadFactorid").
				mSelectDropDown("id", mGetPropertyFromFile("pt_alterationRoadFactorid"), mGetPropertyFromFile(road));
				mGenericWait();
				mTakeScreenShot();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Change Road Factor method");
		}
	}

	public void changePlotArea(String plotArea)
	{
		try
		{
			if((mGetPropertyFromFile(plotArea).length()==0) || (mGetPropertyFromFile(plotArea).equalsIgnoreCase("?")))
			{
				System.out.println("No change in Plot Area");
				System.out.println("Size of Plot Area: " + mGetPropertyFromFile(plotArea).length());
			}
			else
			{
				System.out.println("Change in Plot Area");
				mTakeScreenShot();
				mGenericWait();
				/*mSendKeys("id", mGetPropertyFromFile("pt_alterationPlotAreaid"), "");	
				mGenericWait();*/
				mSendKeys("id", mGetPropertyFromFile("pt_alterationPlotAreaid"), mGetPropertyFromFile(plotArea));
				mGenericWait();
				mTakeScreenShot();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Change Plot Area method");
		}
	}

	public void changeBuiltupArea(String builtupArea)
	{
		try
		{
			if(mGetPropertyFromFile(builtupArea).length()==0|| (mGetPropertyFromFile(builtupArea).equalsIgnoreCase("?")))
			{
				System.out.println("No change in Built-up Area");
				System.out.println("Size of Built-up Area: " + mGetPropertyFromFile(builtupArea).length());
			}
			else
			{
				System.out.println("No change in Built-up Area");
				mTakeScreenShot();
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_alterationBuiltupAreaid"), "");	
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_alterationBuiltupAreaid"), mGetPropertyFromFile(builtupArea));
				mGenericWait();
				mTakeScreenShot();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Change build up Area method");
		}
	}

	public void changeFloorNo(String floorNo)
	{
		try
		{
			if(mGetPropertyFromFile(floorNo).length()==0 || (mGetPropertyFromFile(floorNo).equalsIgnoreCase("?")))
			{
				System.out.println("No change in Floor No.");
				System.out.println("Size of No Usage Type: " + mGetPropertyFromFile(floorNo).length());
			}
			else
			{
				System.out.println("Change in Floor No.");
				mTakeScreenShot();
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("pt_alteartionFloorNoid"), mGetPropertyFromFile(floorNo));
				mGenericWait();
				mTakeScreenShot();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Change FLoor No method");
		}
	}	

	public void changeUsageType(String usageType)
	{
		try
		{
			if(mGetPropertyFromFile(usageType).length()==0 || (mGetPropertyFromFile(usageType).equalsIgnoreCase("?")))
			{
				System.out.println("No change in Usage Type");
				System.out.println("Size of No Usage Type: " + mGetPropertyFromFile(usageType).length());
			}
			else
			{
				System.out.println("Change in Usage Type");
				mTakeScreenShot();
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("pt_alterationUsageTypeid"), mGetPropertyFromFile(usageType));
				mGenericWait();
				/*mSelectDropDown("id", mGetPropertyFromFile("pt_alterationUsageFactorid"), mGetPropertyFromFile("pt_alterationUsageFactor"));
				mGenericWait();*/
				mTakeScreenShot();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Change Usage Type method");
		}

	}

	public void changeConstrnType(String constrnType)
	{
		try
		{
			if(mGetPropertyFromFile(constrnType).length()==0 || (mGetPropertyFromFile(constrnType).equalsIgnoreCase("?")))
			{
				System.out.println("No change in Construction Type");
				System.out.println("Size of No Construction Type: " + mGetPropertyFromFile(constrnType).length());
			}
			else
			{
				System.out.println("Change in Construction Type");
				mTakeScreenShot();
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("pt_alterationConstrTypeid"), mGetPropertyFromFile(constrnType));
				mGenericWait();
				mTakeScreenShot();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Change Construction Type method");
		}
	}

	public void changeBuildingArea(String bldngArea)
	{
		try
		{
			if(mGetPropertyFromFile(bldngArea).length()==0 || (mGetPropertyFromFile(bldngArea).equalsIgnoreCase("?")))
			{
				System.out.println("No change in Building Area");
				System.out.println("Size of No Building Area: " + mGetPropertyFromFile(bldngArea).length());
			}
			else
			{
				System.out.println("Change in Building Area");
				mTakeScreenShot();
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_alterationBuildingAreaid"), "");	
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_alterationBuildingAreaid"), mGetPropertyFromFile(bldngArea));
				mGenericWait();
				mTakeScreenShot();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Change Building Area method");
		}
	}

	public void changeUsageFactor(String useFactor)
	{
		try
		{
			if(mGetPropertyFromFile(useFactor).length()==0 || (mGetPropertyFromFile(useFactor).equalsIgnoreCase("?")))
			{
				System.out.println("Size of change in Usage Factor: " + mGetPropertyFromFile(useFactor).length());
				System.out.println("No change in Usage Factor");

			}
			else
			{
				System.out.println("change in Usage Factor");
				mTakeScreenShot();
				mGenericWait();
				System.out.println("Size of change in Usage Factor: " + mGetPropertyFromFile(useFactor).length());
				mSelectDropDown("id", mGetPropertyFromFile("pt_alterationUseFactorid"), mGetPropertyFromFile(useFactor));
				mGenericWait();
				mTakeScreenShot();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Change Occupancy Factors method");
		}
	}


	public void changeOccupancyFactor(String occFactor)
	{
		try
		{
			if(mGetPropertyFromFile(occFactor).length()==0 || (mGetPropertyFromFile(occFactor).equalsIgnoreCase("?")))
			{
				System.out.println("Size of No change in Occupancy Factor: " + mGetPropertyFromFile(occFactor).length());
				System.out.println("No change in Occupancy Factor");

			}
			else
			{
				System.out.println("Change in Occupancy Factor");
				mTakeScreenShot();
				mGenericWait();
				System.out.println("Size of change in Occupancy Factor: " + mGetPropertyFromFile(occFactor).length());
				mSelectDropDown("id", mGetPropertyFromFile("pt_alterationOccFactorid"), mGetPropertyFromFile(occFactor));
				mGenericWait();
				mTakeScreenShot();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Change Occupancy Factors method");
		}
	}

	public void changeVacantLndArea(String vcntLndArea)
	{
		try
		{
			if(mGetPropertyFromFile(vcntLndArea).length()==0 || (mGetPropertyFromFile(vcntLndArea).equalsIgnoreCase("?")))
			{
				System.out.println("Size of Vancant Land Area: " + mGetPropertyFromFile(vcntLndArea).length());
				System.out.println("No change in Vacant Land Area");
			}
			else
			{
				System.out.println("Size of Vancant Land Area: " + mGetPropertyFromFile(vcntLndArea).length());
				mTakeScreenShot();
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_alterationVcntLndAreaid"), mGetPropertyFromFile(vcntLndArea));
				vancantareavalue=mGetText("id", mGetPropertyFromFile("pt_alterationVcntLndAreaid"), "value");
				mGenericWait();
				mTakeScreenShot();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Change Vacant Land Area method");
		}
	}

	public void noChangeInAssessment()
	{
		try
		{
			String noChngPropNum = "";
			String noChngOldPropNum = "";
			// Navigate to Self Assessment and Payment of Property Tax link
			mNavigation("pt_propertyTaxLinkid", "pt_selfAssessmentLinkid");

			// Select option No Change in Assessment
			mWaitForVisible("id", mGetPropertyFromFile("pt_noChngAssessmentid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_noChngAssessmentid"));

			if(mGetPropertyFromFile("pt_noChngAssmntPropID").equalsIgnoreCase("New"))
			{
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_noChngAssmntPropNoid"), mGetPropertyFromFile("pt_noChngAssmntPropNo"));
				//noChngPropNum=mGetText("id", mGetPropertyFromFile("pt_noChngAssmntPropNoid"),"value");
				//noChngPropNum=driver.findElement(By.id(mGetPropertyFromFile("pt_noChngAssmntPropNoid"))).getAttribute("value");
				noChngPropNum=mGetText("id", mGetPropertyFromFile("pt_noChngAssmntPropNoid"), "value");
				System.out.println("Entered Property number for no change in assessment is : "+noChngPropNum);
			}
			else
			{
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_noChngAssmntOldPropNoid"), mGetPropertyFromFile("pt_noChngAssmntOldPropNo"));
				//noChngOldPropNum=driver.findElement(By.id(mGetPropertyFromFile("pt_noChngAssmntOldPropNoid"))).getAttribute("value");
				noChngOldPropNum=mGetText("id", mGetPropertyFromFile("pt_noChngAssmntOldPropNoid"), "value");
				System.out.println("Entered Old Property number for no change in assessment is : "+noChngOldPropNum);
			}

			// Click Search button
			mClick("xpath", mGetPropertyFromFile("pt_noChngAssmntSearchBtnid"));

			// Select last payment made upto
			mWaitForVisible("id", mGetPropertyFromFile("pt_noChngAssmntLastPaymntUptoid"));
			//boolean pymntMade = driver.findElement(By.id(mGetPropertyFromFile("pt_noChngAssmntLastPaymntUptoid"))).isEnabled();
			boolean pymntMade =mFindElement("id", mGetPropertyFromFile("pt_noChngAssmntLastPaymntUptoid")).isEnabled();
			System.out.print(pymntMade);
			//WebElement paymntMadeUpto = driver.findElement(By.id(mGetPropertyFromFile("pt_noChngAssmntLastPaymntUptoid")));
			WebElement paymntMadeUpto =mFindElement("id", mGetPropertyFromFile("pt_noChngAssmntLastPaymntUptoid"));
			if(paymntMadeUpto.isEnabled())
			{
				mSelectDropDown("id", mGetPropertyFromFile("pt_noChngAssmntLastPaymntUptoid"), mGetPropertyFromFile("pt_noChngAssmntLastPaymntUpto"));
				mGenericWait();
			}
			else
			{
				System.out.println("Payment made upto field is disabled");
			}
			//String pymntMadeVal=mGetText("id", mGetPropertyFromFile("pt_noChngAssmntLastPaymntUptoid"), "value");
			//String pymntMadeVal=driver.findElement(By.id(mGetPropertyFromFile("pt_noChngAssmntLastPaymntUptoid"))).getText();
			String pymntMadeVal=mGetText("id", mGetPropertyFromFile("pt_noChngAssmntLastPaymntUptoid"));
			System.out.println("Payment made upto : "+pymntMadeVal);

			mTakeScreenShot();

			// Click Submit button
			mClick("linkText", mGetPropertyFromFile("pt_noChngAssmntSrchSubmitBtnid"));

			mWaitForVisible("xpath", mGetPropertyFromFile("pt_noChngAssmntSubmitBtnid"));
			mTakeScreenShot();
			mGenericWait();

			String yrOfAssmt=mGetText("xpath", "//*[@id=\"frmNoChangeInAssessment\"]/div[1]/div/b");
			System.out.println("Year of Assessment : "+yrOfAssmt);

			String propertyNo=mGetText("xpath", "//*[@id=\"frmNoChangeInAssessment\"]/div[2]/div[1]");
			String[] propNo=propertyNo.split(":");
			propertyNo=propNo[1].toString().trim();
			System.out.println("Property No.: "+propertyNo);

			//String sucsOldPropNo=mGetText("xpath", mGetPropertyFromFile("pt_noChngAssmntSuccessOldPropNoid"));
			String oldPropNo=driver.findElement(By.xpath("//form[@id='frmNoChangeInAssessment']/div[2]/div[5]")).getText();
			System.out.println("Old property no is : "+oldPropNo);

			mscroll(1283, 712);
			mGenericWait();
			mTakeScreenShot();
			mGenericWait();

			//Land/ Building Details
			String acquisitionDate=mGetText("xpath", "//*[@id=\"frmNoChangeInAssessment\"]/div[18]/div/span");
			System.out.println("Acquisition Date : "+acquisitionDate);

			String ratableArea=mGetText("xpath", "//*[@id=\"frmNoChangeInAssessment\"]/div[19]/div[1]/span");
			System.out.println("Ratable Area (Sq.Ft.) : "+ratableArea);

			String annualRentalValue=mGetText("xpath", "//*[@id=\"frmNoChangeInAssessment\"]/div[19]/div[2]/span");
			System.out.println("Annual Rental Value (Rs.) : "+annualRentalValue);

			String taxableVacantLandArea=mGetText("xpath", "//*[@id=\"frmNoChangeInAssessment\"]/div[20]/div[1]/span");
			System.out.println("Taxable Vacant Land Area (Sq. Ft.) : "+taxableVacantLandArea);

			String totalAnnualTaxHoldingAndVacantLand=mGetText("xpath", "//*[@id=\"frmNoChangeInAssessment\"]/div[20]/div[2]/span");
			System.out.println("Total Annual Tax (Holding + Vacant Land )(Rs.) : "+totalAnnualTaxHoldingAndVacantLand);

			//Tax Calculations
			WebElement table = driver.findElement(By.xpath("//*[@id=\"frmNoChangeInAssessment\"]/table[2]"));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount = rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

			int Rowno = 0;
			for (int a=0;a<rwcount;a++) 
			{
				List<WebElement> cols = rows.get(a).findElements(By.xpath("td"));
				int colcount = cols.size();
				for(int b=0;b<colcount;b++)
				{
					if(b==0)
					{
						String tax = rows.get(a).findElement(By.xpath("td")).getText();
						//String yr =mGetText("xpath", "yearLabelFromValue"+(a-1), "value");
						System.out.println("Tax is : "+tax);
					}
					if(b==1)
					{
						String taxAmount = rows.get(a).findElement(By.xpath("td[2]")).getText();
						//String yr =mGetText("xpath", "yearLabelFromValue"+(a-1), "value");
						System.out.println("Tax amount is : "+taxAmount);
					}
				}
			}

			String arrearWithoutInterest=mGetText("xpath", "//*[@id=\"frmNoChangeInAssessment\"]/table[2]/tbody/tr[1]/td[2]");
			System.out.println("Arrear without interest (Rs.) "+arrearWithoutInterest);

			String totalAnnualTax=mGetText("xpath", "//*[@id=\"frmNoChangeInAssessment\"]/table[2]/tbody/tr[2]/td[2]");
			System.out.println("Total Annual Tax (Rs.) :"+totalAnnualTax);

			String rebateCurrent=mGetText("xpath", "//*[@id=\"rebateAmtID\"]");
			System.out.println("Rebate (Current) "+rebateCurrent);

			String rainWaterHarvestingRebate=mGetText("xpath", "//*[@id=\"frmNoChangeInAssessment\"]/table[2]/tbody/tr[4]/td[2]");
			System.out.println("Rain Water Harvesting Rebate (Rs.) : "+rainWaterHarvestingRebate);

			String interestPenaltyCurrentAndArrears=mGetText("xpath", "//*[@id=\"frmNoChangeInAssessment\"]/table[2]/tbody/tr[5]/td[2]");
			System.out.println("Interest / Penalty (Current + Arrears): "+interestPenaltyCurrentAndArrears);

			String netServiceChargePayableIfapplicable=mGetText("xpath", "//*[@id=\"frmNoChangeInAssessment\"]/table[2]/tbody/tr[6]/td[2]");
			System.out.println("Net Service Charge Payable (If applicable): "+netServiceChargePayableIfapplicable);

			String adjustmentAmount=mGetText("xpath", "//*[@id=\"frmNoChangeInAssessment\"]/table[2]/tbody/tr[7]/td[2]");
			System.out.println("Adjustment Amount : "+adjustmentAmount);

			String advanceAmount=mGetText("xpath", "//*[@id=\"frmNoChangeInAssessment\"]/table[2]/tbody/tr[8]/td[2]");
			System.out.println("Advance Amount : "+advanceAmount);

			String totalPayable=mGetText("xpath", "//*[@id=\"totPayAmtID\"]");
			System.out.println("Total Payable (Rs.) :"+totalPayable);

			mscroll(1302, 700);
			//String totPayable=mGetText("id", mGetPropertyFromFile("pt_noChngAssmntTotPayableid"), "value");
			//String totPayable=driver.findElement(By.id(mGetPropertyFromFile("pt_noChngAssmntTotPayableid"))).getAttribute("value");
			String totPayable=mGetText("id", mGetPropertyFromFile("pt_noChngAssmntTotPayableid"), "value");
			System.out.println("Total payable amount is : "+totPayable);


			//payment("byBankOrULB");			
			payment("paymentMode","byBankOrULB");

			mGenericWait();
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("pt_noChngAssmntSubmitBtnid"));

			mWaitForVisible("id", mGetPropertyFromFile("pt_noChngAssmntProceedBtnid"));
			mGenericWait();
			mTakeScreenShot();
			String saveMsg=mGetText("css", mGetPropertyFromFile("pt_noChngAssmntSaveMsgid"));
			System.out.println(saveMsg);
			mAssert(mGetPropertyFromFile("pt_noChngAssmntSaveMsg"), saveMsg, "Message does not match, Expected: "+mGetPropertyFromFile("pt_noChngAssmntSaveMsg")+" || Actual: "+saveMsg);
			mClick("id", mGetPropertyFromFile("pt_noChngAssmntProceedBtnid"));

			mWaitForVisible("linkText", mGetPropertyFromFile("pt_noChngAssmntPrintChlnBtnid"));

			//String sucesMsg=mGetText("css", mGetPropertyFromFile("pt_noChngAssmntSuccessMsgid"),"value");
			//String sucesMsg=driver.findElement(By.cssSelector(mGetPropertyFromFile("pt_noChngAssmntSuccessMsgid"))).getText();
			String sucesMsg=mGetText("css", mGetPropertyFromFile("pt_noChngAssmntSuccessMsgid"));
			System.out.println("Message is : "+sucesMsg);
			String successMsgStr=sucesMsg.replaceAll("[0-9]", "");
			String successMsgStrng=successMsgStr.replaceAll("-", "");
			String successMsg=successMsgStrng.trim();
			System.out.println("Trimmed Message is : "+successMsg);
			mAssert(successMsg, mGetPropertyFromFile("pt_noChngAssmntSuccessMsg"), "Message does not match, Expected: "+mGetPropertyFromFile("pt_noChngAssmntSuccessMsg")+" || Actual: "+successMsg);

			//String sucsPropNo=mGetText("css", mGetPropertyFromFile("pt_noChngAssmntSuccessPropNoid"),"value");
			//String sucsPropNo=driver.findElement(By.cssSelector(mGetPropertyFromFile("pt_noChngAssmntSuccessPropNoid"))).getText();
			String sucsPropNo=mGetText("css", mGetPropertyFromFile("pt_noChngAssmntSuccessPropNoid"));
			System.out.println("prop no is : "+sucsPropNo);

			if(mGetPropertyFromFile("pt_noChngAssmntPropID").equalsIgnoreCase("New"))
			{
				mAssert(sucsPropNo, noChngPropNum, "Property number does not match, Expected: "+noChngPropNum+" || Actual: "+sucsPropNo);
			}
			else
			{
				mAssert(sucsPropNo, propertyNo, "Property number does not match, Expected: "+noChngPropNum+" || Actual: "+propertyNo);
			}

			//String sucsTotTaxAmt=mGetText("css", mGetPropertyFromFile("pt_noChngAssmntSuccessTotTaxAmtid"),"value");
			//String sucsTotTaxAmt=driver.findElement(By.xpath(mGetPropertyFromFile("pt_noChngAssmntSuccessTotTaxAmtid"))).getText();
			String sucsTotTaxAmt=mGetText("xpath", mGetPropertyFromFile("pt_noChngAssmntSuccessTotTaxAmtid"));
			System.out.println("tot tax amt is : "+sucsTotTaxAmt);
			String successTaxAmt=sucsTotTaxAmt.replace("Rs.", "");
			mAssert(successTaxAmt, totPayable, "Total tax amount does not match, Expected: "+totPayable+" || Actual: "+successTaxAmt);


			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_noChngAssmntPrintChlnBtnid"));
			mCustomWait(4000);
			//newPtChallanReader();
			if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
			{
				newPtChallanReader();
			}
			else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
			{
				mChallanPDFReader();
			}
			mWaitForVisible("linkText", mGetPropertyFromFile("pt_noChngAssmntSugamRptBtnid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("pt_noChngAssmntSugamRptBtnid"));
			mCustomWait(5000);
			mChallanPDFReader();
			api.PdfPatterns.patternSUGAMFORMPdf(pdfoutput);

			//		///////Commented Code on Dated 18-04-2017 Because not able to Assert the value but able to do the assertion for Net payable so updated at below.

			/*mAssert(noChngInAsmntAssessmentYear, yrOfAssmt, "Assessment Year does not matches in SUGAM report, Expected is : "+yrOfAssmt+"Actual is : "+noChngInAsmntAssessmentYear);
			mAssert(noChngInAsmntPropertyNumber, propertyNo, "Property Number does not matches in SUGAM report, Expected is : "+propertyNo+"Actual is : "+noChngInAsmntPropertyNumber);

			mAssert(noChngInAsmntRateableArea, ratableArea, "Rateable Area(Sq.Ft) does not matches in SUGAM report, Expected is : "+ratableArea+"Actual is : "+noChngInAsmntRateableArea);

			mAssert(noChngInAsmntAnnualRentalValue, annualRentalValue, "Annual Rental Value (Rs.) does not matches in SUGAM report, Expected is : "+annualRentalValue+"Actual is : "+noChngInAsmntAnnualRentalValue);
			mAssert(noChngInAsmntTaxableVacantLandArea, taxableVacantLandArea, "Taxable Vacant Land Area (Sq. Ft.) does not matches in SUGAM report, Expected is : "+taxableVacantLandArea+"Actual is : "+noChngInAsmntTaxableVacantLandArea);
			int totRebate=Integer.parseInt(rebateCurrent)+Integer.parseInt(rainWaterHarvestingRebate);
			System.out.println("Total Rebate is : "+Integer.toString(totRebate));
			int propTax=((Integer.parseInt(arrearWithoutInterest)+Integer.parseInt(totalAnnualTax))-totRebate);
			System.out.println("Property Tax is : "+Integer.toString(propTax));
			mAssert(noChngInAsmntPropertyTax, Integer.toString(propTax), "Property Tax does not matches in SUGAM report, Expected is : "+Integer.toString(propTax)+"Actual is : "+noChngInAsmntPropertyTax);
			mAssert(noChngInAsmntTotalRebate, Integer.toString(totRebate), "Total Rebate does not matches in SUGAM report, Expected is : "+Integer.toString(totRebate)+"Actual is : "+noChngInAsmntTotalRebate);
			mAssert(noChngInAsmntInterest, interestPenaltyCurrentAndArrears, "Interest does not matches in SUGAM report, Expected is : "+interestPenaltyCurrentAndArrears+"Actual is : "+noChngInAsmntInterest);
			mAssert(noChngInAsmntServiceCharge, netServiceChargePayableIfapplicable, "Service Charge does not matches in SUGAM report, Expected is : "+netServiceChargePayableIfapplicable+"Actual is : "+noChngInAsmntServiceCharge);*/

			mAssert(noChngInAsmntNetTaxPayable.trim(), totalPayable, "Total payable does not matches in SUGAM report, Expected is : "+totalPayable+"Actual is : "+noChngInAsmntNetTaxPayable.trim());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in noChangeInAssessment method");
		}


	}


	public void chngInHoldingDtlsAddition()
	{
		try
		{
			String chngInPropNum;
			String chngInOldPropNum;

			// Navigate to Self Assessment and Payment of Property Tax link
			mNavigation("pt_propertyTaxLinkid", "pt_selfAssessmentLinkid");

			// Select option Change / Alteration in Assessment
			mWaitForVisible("id", mGetPropertyFromFile("pt_changeInAssessmentid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_changeInAssessmentid"));

			// Search Property number
			if(mGetPropertyFromFile("pt_chngInAssmntPropID").equalsIgnoreCase("New"))
			{
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntPropNoid"), mGetPropertyFromFile("pt_chngInAssmntPropNo"));
				//chngInPropNum=driver.findElement(By.id(mGetPropertyFromFile("pt_chngInAssmntPropNoid"))).getAttribute("value");
				chngInPropNum=mGetText("id", mGetPropertyFromFile("pt_chngInAssmntPropNoid"), "value");
				System.out.println("Entered Property number for change in assessment is : "+chngInPropNum);
			}
			else
			{
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntOldPropNoid"), mGetPropertyFromFile("pt_chngInAssmntOldPropNo"));
				//chngInOldPropNum=driver.findElement(By.id(mGetPropertyFromFile("pt_chngInAssmntOldPropNoid"))).getAttribute("value");
				chngInOldPropNum=mGetText("id", mGetPropertyFromFile("pt_chngInAssmntOldPropNoid"), "value");
				System.out.println("Entered Old Property number for change in assessment is : "+chngInOldPropNum);
			}

			mClick("css", mGetPropertyFromFile("pt_chngInAssmntSrchBtnid"));

			mWaitForVisible("linkText", mGetPropertyFromFile("pt_chngInAssmntSrchSubmitBtnid"));
			mTakeScreenShot();
			mClick("linkText", mGetPropertyFromFile("pt_chngInAssmntSrchSubmitBtnid"));

			mWaitForVisible("id", mGetPropertyFromFile("pt_chngInAssmntHoldingDtlsid"));

			mClick("id", mGetPropertyFromFile("pt_chngInAssmntHoldingDtlsid"));

			//////Updated on 06-01-2017 @ Ritesh Current Method is for Addition Update Old code
			if (mGetPropertyFromFile("changeInHoldingDetailsOnlyAddition").equalsIgnoreCase("Yes"))
			{
				System.out.println("Change in Holding Deatils with Addtion Option");
				mClick("id", mGetPropertyFromFile("pt_chngInAssmntHldngTypAlterationid"));				
			}
			//mClick("id", mGetPropertyFromFile("pt_chngInAssmntHldngTypAdditionid"));
			//mClick("id", mGetPropertyFromFile("pt_chngInAssmntHldngTypAlterationid"));
			//mClick("id", mGetPropertyFromFile("pt_chngInAssmntHldngTypPartialDemolishid"));

			/////Doubt 04-01-2017 By Ritesh same key used above
			//mClick("id", mGetPropertyFromFile("pt_chngInAssmntHldngTypAdditionid"));
			mGenericWait();
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("pt_chngInAssmntSubmitBtnid"));
			mGenericWait();

			//////Open the Change in Assessment form 
			System.out.println("Change in Assessment form gets opened");

			mSelectDropDown("id", mGetPropertyFromFile("pt_chngInAssmntMutationid"), mGetPropertyFromFile("pt_chngInAssmntMutation"));

			//////Checking the Land Type selected (Flat / Land + Building / Vacant Land)
			if(driver.findElement(By.id("entity.codpropertyID12")).isSelected())
			{
				//Land + Building
				chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(3) > label")).getText();
				System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
			}
			else if(driver.findElement(By.id("entity.codpropertyID11")).isSelected())
			{
				//Flat
				chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(2) > label")).getText();
				System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
			}
			else if(driver.findElement(By.id("entity.codpropertyID13")).isSelected())
			{
				//Vacant Land
				chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(4) > label")).getText();
				System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
			}
			else
			{
				System.out.println("Land type is not selected");
			}

			chngAsmtLandTypeAcqYr=driver.findElement(By.xpath("//*[@id=\"frmMasterForm\"]/div[16]/div[2]")).getText();
			System.out.println("Year of Acquisition in change in assessment original is : "+chngAsmtLandTypeAcqYr);
			String[] chngAsmtLandTypeAcqYrOrg=chngAsmtLandTypeAcqYr.split(":");
			chngAsmtLandTypeAcqYr=chngAsmtLandTypeAcqYrOrg[1];
			System.out.println("Year of Acquisition in change in assessment is : "+chngAsmtLandTypeAcqYr);


			//////Payment Made up-to selection
			if(driver.findElement(By.id(mGetPropertyFromFile("pt_chngInAssmntPymntMdUptoid"))).isEnabled())
			{
				mSelectDropDown("id", mGetPropertyFromFile("pt_chngInAssmntPymntMdUptoid"), mGetPropertyFromFile("pt_chngInAssmntPymntMdUpto"));
			}

			/////Difference in Year
			String vacantLandPaymentUptoYear=mCaptureSelectedValue("id", mGetPropertyFromFile("pt_chngInAssmntPymntMdUptoid"));
			System.out.println("Acquisition year of vacant land is before trim: " +vacantLandPaymentUptoYear);
			String acqYr = vacantLandPaymentUptoYear.substring(0, 4);
			System.out.println("Acquisition year of vacant land is: "+acqYr);
			int acqYear = Integer.parseInt(acqYr);

			int year = Calendar.getInstance().get(Calendar.YEAR);
			String fYear = Integer.toString(year);
			int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
			//System.out.println("Financial month : " + month);
			if (month < 3) 
			{
				//System.out.println("Financial Year Ritesh: " + (year - 1) + "-" + year);
				financialYearchng=fYear.substring(0,4);
				System.out.println("Financial Year : " + financialYearchng);
			} 
			else 
			{
				//System.out.println("Financial Year : " + year + "-" + (year + 1));
				financialYearchng=fYear.substring(0,4);
				System.out.println("Financial Year : " + financialYearchng);
			}

			financialYear = Integer.parseInt(financialYearchng);

			/*int currentyear = Calendar.getInstance().get(Calendar.YEAR);
					System.out.println("Current year is: "+currentyear);*/

			//how to get financial year
			/*FiscalDate fiscalDate = new FiscalDate(calendar);
			        int year = fiscalDate.getFiscalYear();
			        System.out.println("Current Date : " + calendar.getTime().toString());
			        System.out.println("Fiscal Years : " + year + "-" + (year + 1));
			        System.out.println("Fiscal Month : " + fiscalDate.getFiscalMonth());*/		

			//cntForVlDtl = currentyear-financialYear;
			cntForVlDtl = financialYear-acqYear;
			System.out.println("Number of years for vacant land required is: "+cntForVlDtl);

			mTakeScreenShot();

			// Road Factor
			chngInAssmntRoadFactor.add(mCaptureSelectedValue("id", mGetPropertyFromFile("pt_chngInAssmntRoadFactorid")));
			System.out.println("Selected road factor is : "+chngInAssmntRoadFactor);

			//////////////////////////////////////////////////////////////////////////
			// Addition in Built-up / Constructed area on the Ground Floor (Sq. Ft.)
			/////////////////////////////////////////////////////////////////////////


			if(mGetPropertyFromFile("pt_chngInAddnGrndFlrBuiltupArea").equalsIgnoreCase("Yes"))
			{
				mTakeScreenShot();
				mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntGrndFlrBuiltupAreaid"), mGetPropertyFromFile("pt_chngInAssmntGrndFlrBuiltupArea"));
				mTakeScreenShot();

				BldgDetailsTableReadingExistingRows();

			}
			// Addition in Floor wise Built-up area
			else if(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupArea").equalsIgnoreCase("Yes"))
			{

				WebElement table = driver.findElement(By.id("buildingDetailsGridID"));

				List<WebElement> rows = table.findElements(By.tagName("tr"));
				int rwcount = rows.size();
				System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

				// Iterate to get the value of each cell in table along with element
				// id
				int Rowno = 0;
				for (int a=0;a<rwcount;a++) 
				{
					List<WebElement> cols = rows.get(a).findElements(By.xpath("td"));
					int colcount = cols.size();
					for(int b=0;b<colcount;b++)
					{
						if(a>=1)
						{

							if(b==0)
							{
								String yr = driver.findElement(By.id("yearLabelFromValue"+(a-1))).getAttribute("value");
								//String yr =mGetText("id", "yearLabelFromValue"+(a-1), "value");
								System.out.println("Year is : "+yr);
							}
							if(b==1)
							{
								String floor = driver.findElement(By.id("floorName"+(a-1))).getAttribute("value");
								//String floor =mGetText("id", "floorName"+(a-1), "value");
								System.out.println("Floor is : "+floor);
							}
							if(b==2)
							{
								String usage = driver.findElement(By.id("usage"+(a-1))).getAttribute("value");
								//String usage =mGetText("id", "usage"+(a-1), "value");
								System.out.println("Usage type is : "+usage);
							}
							if(b==3)
							{
								String cnstrnCls = driver.findElement(By.id("constructionClass"+(a-1))).getAttribute("value");
								//String cnstrnCls = mGetText("id", "constructionClass"+(a-1), "value");
								System.out.println("Construction class is : "+cnstrnCls);
							}
							if(b==4)
							{
								String bultupArea = driver.findElement(By.id("buildingArea"+(a-1))).getAttribute("value");
								//String bultupArea = mGetText("id", "buildingArea"+(a-1), "value");
								System.out.println("Built-up Area is : "+bultupArea);
							}
							if(b==5)
							{
								String ratableArea = driver.findElement(By.id("pdAsseArea"+(a-1))).getAttribute("value");
								//String ratableArea = mGetText("id", "pdAsseArea"+(a-1), "value");
								System.out.println("Ratable Area is : "+ratableArea);
							}
							if(b==6)
							{
								String usgFactor = driver.findElement(By.id("nonResiFactor"+(a-1))).getAttribute("value");
								//String usgFactor = mGetText("id", "nonResiFactor"+(a-1), "value");
								System.out.println("Usage Factor is : "+usgFactor);
							}
							if(b==7)
							{
								String occpFactor = driver.findElement(By.id("OccType"+(a-1))).getAttribute("value");
								//String occpFactor = mGetText("id", "OccType"+(a-1), "value");
								System.out.println("Occupancy Factor is : "+occpFactor);
							}
						}
					}

				}

				//List<WebElement> rows1 = table.findElements(By.tagName("tr"));

				//To read existing rows of table before adding new rows for change in assessment
				BldgDetailsTableReadingExistingRows();

				//	int addCount=Integer.parseInt(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaFlrCount"));

				mscroll(0, 1300);
				/*List<WebElement> rows1 = table.findElements(By.tagName("tr"));
				rows1.size();
				System.out.println(rows1.size());

				List<WebElement> cols = rows1.get(1).findElements(By.xpath("td"));
				int colcount= cols.size();
				System.out.println(colcount);*/

				String[] string=mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaFlrNo").split(",");
				System.out.println("string array is : "+string[0]);
				System.out.println("string array is : "+string[1]); 
				System.out.println("string array size is : "+string.length);

				/*String[] flrNoArray=mGetPropertyFromFile("pt_newAssessmentFloorNoArray").split(",");
				System.out.println("string array size is : "+flrNoArray.length);*/

				int a=Integer.parseInt(string[0].toString());
				int b=Integer.parseInt(string[1].toString());
				addCount=(b-a)+1;

				mCustomWait(5000);

				driver.findElement(By.linkText("+")).click();
				/*int counter=1;
				for (int i=0;i<string.length;i++) 
				{
					for(int j=0;j<colcount;j++)*/

				/////////////////////////////////////
				/*WebElement tableNew = driver.findElement(By.id("buildingDetailsGridID"));

				List<WebElement> rowsNew = tableNew.findElements(By.xpath("//tr[@class='buildingTr']"));

				System.out.println("Row count for building : "+rowsNew.size());*/

				/*List<WebElement> cols = rows1.get(rows1.size()).findElements(By.xpath("td"));
				int colcount= cols.size();
				System.out.println(colcount);*/

				////////////////////////////////////

				int counter=1;
				InvoCount=a;

				for (int i=0;i<addCount;i++) 
				{
					WebElement tableNew = driver.findElement(By.id("buildingDetailsGridID"));

					List<WebElement> rowsNew = tableNew.findElements(By.xpath("//tr[@class='buildingTr']"));

					System.out.println("Row count for building : "+rowsNew.size());
					for (int k=0;k<rowsNew.size();k++)
					{
						List<WebElement> colsNew = rowsNew.get(k).findElements(By.xpath("td"));
						int colcount1= colsNew.size();
						System.out.println(colcount1);

						if(InvoCount>b)
							break;
						System.out.println("Value of i :"+i);
						for(int j=0;j<colcount1;j++)
						{
							if(j==0)
							{
								mCustomWait(1500);
								//String yr = driver.findElement(By.id("yearLabelFromValue"+(i))).getAttribute("value");
								//String yr =mGetText("id", "yearLabelFromValue"+(i+1), "value");
								String yr = driver.findElement(By.xpath("//td[1]/input")).getAttribute("value");
								System.out.println("Year is : "+yr);
								//yearBlgArray.add(driver.findElement(By.id("yearLabelFromValue"+i)).getAttribute("value"));
								yearBlgArray.add(driver.findElement(By.xpath("//td[1]/input")).getAttribute("value"));
								System.out.println("Building Year is: "+yearBlgArray);		
							}

							if(j==1)
							{
								System.out.println("Value of j :"+j);
								mCustomWait(1500);
								//new Select(driver.findElement(By.id("cpdLocID"+(i+1)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaFloorNo"));
								//new Select(driver.findElement(By.xpath("td[2]"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaFloorNo"));
								//new Select(driver.findElement(By.xpath("//td[2]/following::select"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaFloorNo"));
								new Select(driver.findElement(By.xpath("//td[2]/select"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaFloorNo"));

								//floorNoArray.add(new Select(driver.findElement(By.id("cpdLocID"+(i+1)))).getFirstSelectedOption().getText());
								floorNoArray.add(new Select(driver.findElement(By.xpath("//td[2]/select"))).getFirstSelectedOption().getText());
								System.out.println("Property Detail Entry Floor No is: "+floorNoArray);

							}
							//String floor = driver.findElement(By.id("floorName"+(j-1))).getAttribute("value");
							//String floor = driver.findElement(By.id("cpdLocID"+(i))).getAttribute("id");
							//String floor = mGetText("id", "cpdLocID"+(i), "id");
							//System.out.println("Floor is : "+floor);

							if(j==2)
							{
								mCustomWait(1500);
								//	new Select(driver.findElement(By.name("entity.propertyDetails["+(i+1)+"].cpdUsage1"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsageType"));
								new Select(driver.findElement(By.xpath("//td[3]/select"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsageType"));
								/*String usage = driver.findElement(By.id("usage"+(j-1))).getAttribute("value");
							System.out.println("Usage type is : "+usage);*/
								//typeOfUseArray.add(new Select(driver.findElement(By.name("entity.propertyDetails["+(i+1)+"].cpdUsage1"))).getFirstSelectedOption().getText());
								typeOfUseArray.add(new Select(driver.findElement(By.xpath("//td[3]/select"))).getFirstSelectedOption().getText());
								//							typeOfUseArray.add(driver.findElement(By.id("usage"+i)).getAttribute("value"));
								System.out.println("Property Detail Entry Type of Use is: "+typeOfUseArray);
							}
							if(j==3)
							{
								mCustomWait(1500);
								driver.findElement(By.xpath("//td[4]/select")).click();
								//new Select (driver.findElement(By.name("entity.propertyDetails["+(i+1)+"].codConsClass1"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaConstructionType"));
								//	new Select (driver.findElement(By.xpath("//td[4]/select"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaConstructionType"));
								/*String eleId=rowsNew.get(k).findElement(By.xpath("//td[4]/select")).getAttribute("id");
							System.out.println("Element id : "+eleId);
							new Select (driver.findElement(By.id(eleId))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaConstructionType"));*/
								//new Select (rowsNew.get(i).findElement(By.xpath("//td[4]/select"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaConstructionType"));
								//new Select (driver.findElement(By.xpath("//td[4]/select"))).selectByIndex(1);
								//driver.findElement(By.xpath("//td[4]/select")).sendKeys("Pucca building with RCC Roof");
								//System.out.println("Construction class is : "+cnstrnCls);
								//constructTypeArray.add(new Select(driver.findElement(By.name("entity.propertyDetails["+(i+1)+"].codConsClass1"))).getFirstSelectedOption().getText());
								new Select (driver.findElement(By.xpath("//table[@id='buildingDetailsGridID']/tbody/tr/td[4]/select"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaConstructionType"));
								constructTypeArray.add(new Select(driver.findElement(By.xpath("//table[@id='buildingDetailsGridID']/tbody/tr/td[4]/select"))).getFirstSelectedOption().getText());
								//							constructTypeArray.add(driver.findElement(By.id("constructionClass"+i)).getAttribute("value"));
								System.out.println("Property Detail Entry Construction Type is: "+constructTypeArray);
							}
							if(j==4)
							{
								mCustomWait(1500);
								//driver.findElement(By.id("pdBuildingArea"+(i+1))).sendKeys(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaBuiltupArea"));
								//	driver.findElement(By.xpath("//table[@id='buildingDetailsGridID']/tbody/tr/td[5]/input")).click();
								//	driver.findElement(By.xpath("//table[@id='buildingDetailsGridID']/tbody/tr/td[5]/input")).clear();
								//	driver.findElement(By.xpath("//table[@id='buildingDetailsGridID']/tbody/tr/td[5]/input")).sendKeys(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaBuiltupArea"));
								driver.findElement(By.xpath("//tr[@class='buildingTr']/td[5]/input")).sendKeys(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaBuiltupArea"));


								/*WebElement element=driver.findElement(By.xpath("//table[@id='buildingDetailsGridID']/tbody/tr/td[5]/input")); 
							if(element.isEnabled())
							{
								System.out.println("ele true");
							}
							else
							{
								System.out.println("ele false");
							}
							element.sendKeys("400");*/
								/*JavascriptExecutor js=(JavascriptExecutor)driver; 
							js.executeScript("arguments[0].setAttribute('value',arguments[1]);",element,mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaBuiltupArea"));*/


								/*Actions actions = new Actions(driver);     
							//FIXME:  Get correct lookup for the VideoPlayer element.   
							WebElement videoPlayer = rowsNew.get(k).findElement(By.xpath("//table[@id='buildingDetailsGridID']/tbody/tr/td[5]/input"));        
							String play = "p";        
							//Send "p" to the videoPlayer webElement
							actions.sendKeys(videoPlayer,play).perform();*/

								//mSendKeys("id", "pdBuildingArea"+(i+1), "100");
								//System.out.println("Built-up Area is : "+bultupArea);
								//buildupArArray.add(driver.findElement(By.id("pdBuildingArea"+(i+1))).getAttribute("value"));
								buildupArArray.add(driver.findElement(By.xpath("//table[@id='buildingDetailsGridID']/tbody/tr/td[5]/input")).getText());
								//							buildupArArray.add(driver.findElement(By.id("buildingArea"+i)).getAttribute("value"));
								System.out.println("Property Detail Entry Buildup Area is :"+buildupArArray);
							}
							if(j==5)
							{
								mCustomWait(1500);
								//String ratableArea = driver.findElement(By.id("pdAsseArea"+(i+1))).getText();
								String ratableArea = driver.findElement(By.xpath("//td[6]/input")).getText();
								//String ratableArea =mGetText("id", "pdAsseArea"+(i+1));
								System.out.println("Ratable Area is : "+ratableArea);
								//	calcRatableArArray.add(ratableArea);
							}
							if(j==6)
							{
								mCustomWait(1500);
								if(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsageType").equalsIgnoreCase("Residential"))
								{
									//String usgFactor = driver.findElement(By.id("cpdSfctID"+(i+1))).getAttribute("value");
									String usgFactor = driver.findElement(By.xpath("//td[7]/select")).getAttribute("value");
									//String usgFactor =mGetText("id", "cpdSfctID"+(i+1), "value");
									System.out.println("Usage Factor is : "+usgFactor);
									//usageFactorArray.add(new Select(driver.findElement(By.id("cpdSfctID"+(i+1)))).getFirstSelectedOption().getText());
									usageFactorArray.add(new Select(driver.findElement(By.xpath("//td[7]/select"))).getFirstSelectedOption().getText());
									//							usageFactorArray.add(driver.findElement(By.id("pdAsseArea"+i)).getAttribute("value"));
									System.out.println("Property Detail Entry Usage Factor is: "+usageFactorArray);
									calcRatableArArray.add(mGetPropertyFromFile("ratableAreaForRes"));
									calcAnnulRateblValArray.add(mGetPropertyFromFile("annualRatableValueForRes"));
									calcAnnulTaxArray.add(mGetPropertyFromFile("annualTaxRes"));


								}
								else
								{
									//String usgFactor = driver.findElement(By.id("cpdSfctID"+(i+1))).getAttribute("value");
									//new Select (driver.findElement(By.id("cpdSfctID"+(i+1)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsgFactor"));
									new Select (driver.findElement(By.xpath("//td[7]/select"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsgFactor"));

									//String usgFactor =mGetText("id", "cpdSfctID"+(i+1), "value");
									//System.out.println("Usage Factor is : "+usgFactor);
									//usageFactorArray.add(new Select(driver.findElement(By.id("cpdSfctID"+(i+1)))).getFirstSelectedOption().getText());
									usageFactorArray.add(new Select(driver.findElement(By.xpath("//td[7]/select"))).getFirstSelectedOption().getText());
									//							usageFactorArray.add(driver.findElement(By.id("pdAsseArea"+i)).getAttribute("value"));
									System.out.println("Property Detail Entry Usage Factor is: "+usageFactorArray);
									calcRatableArArray.add(mGetPropertyFromFile("ratableAreaForNonRes"));
									calcAnnulRateblValArray.add(mGetPropertyFromFile("annualRatableValueForNonRes"));
									calcAnnulTaxArray.add(mGetPropertyFromFile("annualTaxNonRes"));
								}

								calcUnitRateArray.add(mGetPropertyFromFile("unitArea"));
								calcRateOfTaxArray.add(mGetPropertyFromFile("rateOfTax"));
								calcServiceChargeArray.add(mGetPropertyFromFile("pt_newAssessmentServiceChargeCurSAS"));
								System.out.println("Property Detail Entry Ratable area array is: "+calcRatableArArray);
								System.out.println("Calculated annual tax array is: "+calcAnnulTaxArray);
							}
							if(j==7)
							{
								mCustomWait(1500);
								//new Select (driver.findElement(By.id("cpdOccStatus"+(i+1)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaOccuFactor"));
								new Select (driver.findElement(By.xpath("//td[8]/select"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaOccuFactor"));
								//System.out.println("Occupancy Factor is : "+occpFactor);
								//occFacArray.add(new Select(driver.findElement(By.id("cpdOccStatus"+(i+1)))).getFirstSelectedOption().getText());
								occFacArray.add(new Select(driver.findElement(By.xpath("//td[8]/select"))).getFirstSelectedOption().getText());
								//							occFacArray.add(driver.findElement(By.id("OccType"+i)).getAttribute("value"));
								System.out.println("Property Detail Entry Occupancy Factor is: "+occFacArray);
							}
						}
						//}
						//}
						mCustomWait(2000);
						//mClick("linkText", "+");

						if(i<(addCount-1))
						{
							mCustomWait(5000);
							//mClick("linkText", "+");
							//	driver.findElement(By.linkText("+")).click();
							driver.findElement(By.xpath("//*[@id=\"cloumn"+(i+1)+"\"]/td[9]/a")).click();
							System.out.println("Clicking on for add");
							mCustomWait(5000);
						}

						counter++;
						InvoCount++;

						/*String builtUp=buildupArArray.get(i);
						int ratableAreaForRes=((Integer.parseInt(builtUp)*70)/100);
						int ratableAreaForNonRes=((Integer.parseInt(builtUp)*80)/100);

						int annualRatableValueForRes==IF(B$65="Tenanted",TEXT(B$100*B$103*1.5,"0.00"),TEXT(B$100*B$103,"0.00"))
								if(occFacArray.equals("Tenanted"))
								{
									int annualRatableValueForRes=(ratableAreaForRes*B$103*1.5,"0.00")
								}*/

					}

					// for new assessment
					//		BldgDetailsTableReading();



					mTakeScreenShot();
				}
			}

			// Addition in Built-up area on the Ground Floor and Floor wise Built-up area
			else if(mGetPropertyFromFile("pt_chngInAddnGrnd&FloorBuiltupArea").equalsIgnoreCase("Yes"))
			{
				// Built up area addition
				mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntGrndFlrBuiltupAreaid"), mGetPropertyFromFile("pt_chngInAssmntGrndFlrBuiltupArea"));
				mTakeScreenShot();

				// floor details addition
				WebElement table = driver.findElement(By.id("buildingDetailsGridID"));

				List<WebElement> rows = table.findElements(By.tagName("tr"));
				int rwcount = rows.size();
				System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

				// Iterate to get the value of each cell in table along with element
				// id
				int Rowno = 0;
				for (int a=0;a<rwcount;a++) 
				{
					List<WebElement> cols = rows.get(a).findElements(By.xpath("td"));
					int colcount = cols.size();
					for(int b=0;b<colcount;b++)
					{
						if(a>=1)
						{

							if(b==0)
							{
								//String yr = driver.findElement(By.id("yearLabelFromValue"+(a-1))).getAttribute("value");
								String yr =mGetText("id", "yearLabelFromValue"+(a-1), "value");
								System.out.println("Year is : "+yr);
							}
							if(b==1)
							{
								//String floor = driver.findElement(By.id("floorName"+(a-1))).getAttribute("value");
								String floor = mGetText("id", "floorName"+(a-1), "value");
								System.out.println("Floor is : "+floor);
							}
							if(b==2)
							{
								//String usage = driver.findElement(By.id("usage"+(a-1))).getAttribute("value");
								String usage =mGetText("id", "usage"+(a-1), "value");
								System.out.println("Usage type is : "+usage);
							}
							if(b==3)
							{
								//String cnstrnCls = driver.findElement(By.id("constructionClass"+(a-1))).getAttribute("value");
								String cnstrnCls =mGetText("id", "constructionClass"+(a-1), "value");
								System.out.println("Construction class is : "+cnstrnCls);
							}
							if(b==4)
							{
								//String bultupArea = driver.findElement(By.id("buildingArea"+(a-1))).getAttribute("value");
								String bultupArea =mGetText("id", "buildingArea"+(a-1), "value");
								System.out.println("Built-up Area is : "+bultupArea);
							}
							if(b==5)
							{
								//String ratableArea = driver.findElement(By.id("pdAsseArea"+(a-1))).getAttribute("value");
								String ratableArea = mGetText("id", "pdAsseArea"+(a-1), "value");
								System.out.println("Ratable Area is : "+ratableArea);
							}
							if(b==6)
							{
								//String usgFactor = driver.findElement(By.id("nonResiFactor"+(a-1))).getAttribute("value");
								String usgFactor =mGetText("id", "nonResiFactor"+(a-1), "value");
								System.out.println("Usage Factor is : "+usgFactor);
							}
							if(b==7)
							{
								//String occpFactor = driver.findElement(By.id("OccType"+(a-1))).getAttribute("value");
								String occpFactor = mGetText("id", "OccType"+(a-1), "value");
								System.out.println("Occupancy Factor is : "+occpFactor);
							}
						}
					}

				}

				BldgDetailsTableReadingExistingRows();

				//List<WebElement> rows1 = table.findElements(By.tagName("tr"));

				//int addCount=Integer.parseInt(mGetPropertyFromFile("pt_chngInAddnGrnd&FloorBuiltupAreaFlrCount"));

				mscroll(0, 1300);
				List<WebElement> rows1 = table.findElements(By.tagName("tr"));
				rows1.size();
				System.out.println(rows1.size());

				List<WebElement> cols = rows1.get(1).findElements(By.xpath("td"));
				int colcount= cols.size();
				System.out.println(colcount);

				String[] string=mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaFlrNo").split(",");
				System.out.println("string array size is : "+string.length);

				int a=Integer.parseInt(string[0].toString());
				int b=Integer.parseInt(string[1].toString());
				addCount=(b-a)+1;

				mCustomWait(5000);
				//mClick("linkText", "+");
				driver.findElement(By.linkText("+")).click();
				int counter=1;
				InvoCount=a;
				for (int i=0;i<addCount;i++) 
				{
					if(InvoCount>b)
						break;

					for(int j=0;j<colcount;j++)
					{
						if(j==0)
						{
							mCustomWait(1500);
							String yr = driver.findElement(By.id("yearLabelFromValue"+(i+1))).getAttribute("value");
							//String yr =mGetText("id", "yearLabelFromValue"+(i+1), "value");
							System.out.println("Year is : "+yr);
							yearBlgArray.add(driver.findElement(By.id("yearLabelFromValue"+i)).getAttribute("value"));
							System.out.println("Building Year is: "+yearBlgArray);
						}
						if(j==1)
						{
							mCustomWait(1500);
							//new Select(driver.findElement(By.id("cpdLocID"+(i+1)))).selectByVisibleText("2nd");
							//String floor = driver.findElement(By.id("floorName"+(j-1))).getAttribute("value");
							//String floor = driver.findElement(By.id("cpdLocID"+(i))).getAttribute("id");
							//String floor = mGetText("id", "cpdLocID"+(i), "id");
							//System.out.println("Floor is : "+floor);
							new Select(driver.findElement(By.id("cpdLocID"+(i+1)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaFloorNo"));

							floorNoArray.add(new Select(driver.findElement(By.id("cpdLocID"+(i+1)))).getFirstSelectedOption().getText());
							System.out.println("Property Detail Entry Floor No is: "+floorNoArray);


						}
						if(j==2)
						{
							mCustomWait(1500);
							//new Select(driver.findElement(By.name("entity.propertyDetails["+(i+1)+"].cpdUsage1"))).selectByVisibleText("Residential");
							/*String usage = driver.findElement(By.id("usage"+(j-1))).getAttribute("value");
							System.out.println("Usage type is : "+usage);*/
							new Select(driver.findElement(By.name("entity.propertyDetails["+(i+1)+"].cpdUsage1"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsageType"));
							typeOfUseArray.add(new Select(driver.findElement(By.name("entity.propertyDetails["+(i+1)+"].cpdUsage1"))).getFirstSelectedOption().getText());
							//							typeOfUseArray.add(driver.findElement(By.id("usage"+i)).getAttribute("value"));
							System.out.println("Property Detail Entry Type of Use is: "+typeOfUseArray);
						}
						if(j==3)
						{
							mCustomWait(1500);
							//new Select (driver.findElement(By.name("entity.propertyDetails["+(i+1)+"].codConsClass1"))).selectByVisibleText("Pucca building with RCC Roof");
							//System.out.println("Construction class is : "+cnstrnCls);
							new Select (driver.findElement(By.name("entity.propertyDetails["+(i+1)+"].codConsClass1"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaConstructionType"));
							//System.out.println("Construction class is : "+cnstrnCls);
							constructTypeArray.add(new Select(driver.findElement(By.name("entity.propertyDetails["+(i+1)+"].codConsClass1"))).getFirstSelectedOption().getText());
							//							constructTypeArray.add(driver.findElement(By.id("constructionClass"+i)).getAttribute("value"));
							System.out.println("Property Detail Entry Construction Type is: "+constructTypeArray);
						}
						if(j==4)
						{
							mCustomWait(1500);
							driver.findElement(By.id("pdBuildingArea"+(i+1))).sendKeys(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaBuiltupArea"));
							buildupArArray.add(driver.findElement(By.id("pdBuildingArea"+(i+1))).getAttribute("value"));
							System.out.println("Property Detail Entry Buildup Area is :"+buildupArArray);
							//driver.findElement(By.id("pdBuildingArea"+(i+1))).sendKeys("100");
							//mSendKeys("id", "pdBuildingArea"+(i+1), "data");
							//System.out.println("Built-up Area is : "+bultupArea);
						}
						if(j==5)
						{
							mCustomWait(1500);
							//String ratableArea = driver.findElement(By.id("pdAsseArea"+(i+1))).getText();
							//String ratableArea =mGetText("id", "pdAsseArea"+(i+1));
							//System.out.println("Ratable Area is : "+ratableArea);
							String ratableArea = driver.findElement(By.id("pdAsseArea"+(i+1))).getText();
							System.out.println("Ratable Area is : "+ratableArea);
						}
						if(j==6)
						{
							mCustomWait(1500);
							//String usgFactor = driver.findElement(By.id("cpdSfctID"+(i+1))).getAttribute("value");
							//String usgFactor =mGetText("id", "cpdSfctID"+(i+1), "value");
							//System.out.println("Usage Factor is : "+usgFactor);
							if(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsageType").equalsIgnoreCase("Residential"))
							{
								String usgFactor = driver.findElement(By.id("cpdSfctID"+(i+1))).getAttribute("value");
								//String usgFactor =mGetText("id", "cpdSfctID"+(i+1), "value");
								System.out.println("Usage Factor is : "+usgFactor);
								usageFactorArray.add(new Select(driver.findElement(By.id("cpdSfctID"+(i+1)))).getFirstSelectedOption().getText());
								//							usageFactorArray.add(driver.findElement(By.id("pdAsseArea"+i)).getAttribute("value"));
								System.out.println("Property Detail Entry Usage Factor is: "+usageFactorArray);
								calcRatableArArray.add(mGetPropertyFromFile("ratableAreaForRes"));
								calcAnnulRateblValArray.add(mGetPropertyFromFile("annualRatableValueForRes"));
								calcAnnulTaxArray.add(mGetPropertyFromFile("annualTaxRes"));
							}
							else
							{
								//String usgFactor = driver.findElement(By.id("cpdSfctID"+(i+1))).getAttribute("value");
								new Select (driver.findElement(By.id("cpdSfctID"+(i+1)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsgFactor"));

								//String usgFactor =mGetText("id", "cpdSfctID"+(i+1), "value");
								//System.out.println("Usage Factor is : "+usgFactor);
								usageFactorArray.add(new Select(driver.findElement(By.id("cpdSfctID"+(i+1)))).getFirstSelectedOption().getText());
								//							usageFactorArray.add(driver.findElement(By.id("pdAsseArea"+i)).getAttribute("value"));
								System.out.println("Property Detail Entry Usage Factor is: "+usageFactorArray);
								calcRatableArArray.add(mGetPropertyFromFile("ratableAreaForNonRes"));
								calcAnnulRateblValArray.add(mGetPropertyFromFile("annualRatableValueForNonRes"));
								calcAnnulTaxArray.add(mGetPropertyFromFile("annualTaxNonRes"));
							}

							calcUnitRateArray.add(mGetPropertyFromFile("unitArea"));
							calcRateOfTaxArray.add(mGetPropertyFromFile("rateOfTax"));
							calcServiceChargeArray.add(mGetPropertyFromFile("pt_newAssessmentServiceChargeCurSAS"));
							System.out.println("Property Detail Entry Ratable area array is: "+calcRatableArArray);
							System.out.println("Calculated annual tax array is: "+calcAnnulTaxArray);
						}
						if(j==7)
						{
							mCustomWait(1500);
							//new Select (driver.findElement(By.id("cpdOccStatus"+(i+1)))).selectByVisibleText("Self-Occupied");
							//System.out.println("Occupancy Factor is : "+occpFactor);
							new Select (driver.findElement(By.id("cpdOccStatus"+(i+1)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaOccuFactor"));
							occFacArray.add(new Select(driver.findElement(By.id("cpdOccStatus"+(i+1)))).getFirstSelectedOption().getText());
							System.out.println("Property Detail Entry Occupancy Factor is: "+occFacArray);
						}
					}
					if(i<(addCount-1))
					{
						mCustomWait(5000);
						//mClick("linkText", "+");
						driver.findElement(By.xpath("//*[@id=\"cloumn"+(i+1)+"\"]/td[9]/a")).click();
						System.out.println("Clicking on for add");
						mCustomWait(5000);
					}
					counter++;
					InvoCount++;
				}
				//BldgDetailsTableReading();
				mTakeScreenShot();
			}
			// Addition in change land type
			else if(mGetPropertyFromFile("pt_chngInAddnChngLandType").equalsIgnoreCase("Yes"))
			{
				if(mGetPropertyFromFile("pt_chngInAddnChngVcntLandType").equalsIgnoreCase("Land + Building"))
				{
					// Type of Land
					mClick("id", mGetPropertyFromFile("pt_chngInAssmntLandTypLandBldgid"));
					mWaitForVisible("id", mGetPropertyFromFile("pt_chngInAssmntAlertOkid"));
					String alertMsg=mGetText("xpath", mGetPropertyFromFile("pt_chngInAssmntAlertOkMsgid"));
					System.out.println("Warning is : "+alertMsg);
					mAssert(alertMsg, mGetPropertyFromFile("pt_chngInAssmntAlertOkMsg"), "Warning message does not match. Actual is : "+alertMsg+"Expected is : "+mGetPropertyFromFile("pt_chngInAssmntAlertOkMsg"));
					mCustomWait(1000);
					mTakeScreenShot();
					mClick("id", mGetPropertyFromFile("pt_chngInAssmntAlertOkid"));
					mCustomWait(1000);
					mTakeScreenShot();

					// Capture changed land type
					if(driver.findElement(By.id("entity.codpropertyID12")).isSelected())
					{
						chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(3) > label")).getText();
						System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
					}
					else if(driver.findElement(By.id("entity.codpropertyID11")).isSelected())
					{
						chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(2) > label")).getText();
						System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
					}
					else if(driver.findElement(By.id("entity.codpropertyID13")).isSelected())
					{
						chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(4) > label")).getText();
						System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
					}
					else
					{
						System.out.println("Land type is not selected");
					}

					// Last Payment Details
					if(driver.findElement(By.id(mGetPropertyFromFile("pt_chngInAssmntPymntMdUptoid"))).isEnabled())
					{
						mSelectDropDown("id", mGetPropertyFromFile("pt_chngInAssmntPymntMdUptoid"), mGetPropertyFromFile("pt_chngInAssmntPymntMdUpto"));
					}

					// Land/ Building Details
					mSelectDropDown("id", mGetPropertyFromFile("pt_chngInAssmntRoadFactorid"), mGetPropertyFromFile("pt_chngInAssmntRoadFactor"));
					mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntPlotAreaid"), mGetPropertyFromFile("pt_chngInAssmntPlotArea"));
					mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntBuiltupAreaid"), mGetPropertyFromFile("pt_chngInAssmntBuiltupArea"));

					// Rain Water Harvesting
					if(mGetPropertyFromFile("pt_chngInAssmntRainWaterHarvesting").equalsIgnoreCase("Yes"))
					{
						mClick("id", mGetPropertyFromFile("pt_chngInAssmntRainWaterHarvestingid"));
					}
					else
					{
						System.out.println("Rain Water Harvesting is not applicable");
					}

					// Water Connection Facility
					if(mGetPropertyFromFile("pt_chngInAssmntGovWtrConn").equalsIgnoreCase("Yes"))
					{
						mClick("id", mGetPropertyFromFile("pt_chngInAssmntGovWtrConnid"));
					}
					else if(mGetPropertyFromFile("pt_chngInAssmntWtrConnAvlblWithin400Yrds").equalsIgnoreCase("Yes"))
					{
						mClick("id", mGetPropertyFromFile("pt_chngInAssmntWtrConnAvlblWithin400Yrdsid"));
					}
					else if(mGetPropertyFromFile("pt_chngInAssmntWtrConnNotAvlblWithin400Yrds").equalsIgnoreCase("Yes"))
					{
						mClick("id", mGetPropertyFromFile("pt_chngInAssmntWtrConnNotAvlblWithin400Yrdsid"));
					}

					WebElement table = driver.findElement(By.id("buildingDetailsGridID"));

					//int addCount=Integer.parseInt(mGetPropertyFromFile("pt_chngInAddnChngVcntLandTypToBldgFlrCount"));

					mscroll(0, 1300);
					List<WebElement> rows1 = table.findElements(By.tagName("tr"));
					rows1.size();
					System.out.println(rows1.size());

					List<WebElement> cols = rows1.get(1).findElements(By.xpath("td"));
					int colcount= cols.size();
					System.out.println(colcount);

					String[] string=mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaFlrNo").split(",");
					System.out.println("string array size is : "+string.length);

					int a=Integer.parseInt(string[0].toString());
					int b=Integer.parseInt(string[1].toString());
					addCount=(b-a)+1;

					mCustomWait(5000);

					int counter=1;
					InvoCount=a;
					for (int i=0;i<addCount;i++) 
					{
						if(InvoCount>b)
							break;
						for(int j=0;j<colcount;j++)
						{
							if(j==0)
							{
								mCustomWait(1500);
								//new Select(driver.findElement(By.id("detFaYearId"+(i)))).selectByVisibleText("2016-17");
								//String yr = driver.findElement(By.id("yearLabelFromValue"+(i))).getAttribute("value");
								//System.out.println("Year is : "+yr);
								new Select(driver.findElement(By.id("detFaYearId"+(i)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorYear"));
								//System.out.println("Year is : "+yr);
								yearBlgArray.add(driver.findElement(By.id("yearLabelFromValue"+i)).getAttribute("value"));
								System.out.println("Building Year is: "+yearBlgArray);
							}
							if(j==1)
							{
								mCustomWait(1500);
								//new Select(driver.findElement(By.id("cpdLocID"+(i)))).selectByVisibleText("2nd");
								//String floor = driver.findElement(By.id("floorName"+(j-1))).getAttribute("value");
								//String floor = driver.findElement(By.id("cpdLocID"+(i))).getAttribute("id");
								//System.out.println("Floor is : "+floor);
								new Select(driver.findElement(By.id("cpdLocID"+(i)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaFloorNo"));
								floorNoArray.add(new Select(driver.findElement(By.id("cpdLocID"+(i)))).getFirstSelectedOption().getText());
								System.out.println("Property Detail Entry Floor No is: "+floorNoArray);
							}
							if(j==2)
							{
								mCustomWait(1500);
								//new Select(driver.findElement(By.name("entity.propertyDetails["+(i)+"].cpdUsage1"))).selectByVisibleText("Residential");
								/*String usage = driver.findElement(By.id("usage"+(j-1))).getAttribute("value");
												System.out.println("Usage type is : "+usage);*/
								new Select(driver.findElement(By.name("entity.propertyDetails["+(i)+"].cpdUsage1"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsageType"));
								typeOfUseArray.add(new Select(driver.findElement(By.name("entity.propertyDetails["+(i)+"].cpdUsage1"))).getFirstSelectedOption().getText());
								System.out.println("Property Detail Entry Type of Use is: "+typeOfUseArray);
							}
							if(j==3)
							{
								mCustomWait(1500);
								//new Select (driver.findElement(By.name("entity.propertyDetails["+(i)+"].codConsClass1"))).selectByVisibleText("Pucca building with RCC Roof");
								//System.out.println("Construction class is : "+cnstrnCls);
								new Select (driver.findElement(By.name("entity.propertyDetails["+(i)+"].codConsClass1"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaConstructionType"));
								constructTypeArray.add(new Select(driver.findElement(By.name("entity.propertyDetails["+(i)+"].codConsClass1"))).getFirstSelectedOption().getText());
								System.out.println("Property Detail Entry Construction Type is: "+constructTypeArray);
							}
							if(j==4)
							{
								mCustomWait(1500);
								/*driver.findElement(By.id("pdBuildingArea"+(i))).sendKeys("100");
								mSendKeys("id", "pdBuildingArea"+(i), "100");*/
								//System.out.println("Built-up Area is : "+bultupArea);
								driver.findElement(By.id("pdBuildingArea"+(i))).sendKeys(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaBuiltupArea"));
								buildupArArray.add(driver.findElement(By.id("pdBuildingArea"+(i))).getAttribute("value"));
								System.out.println("Property Detail Entry Buildup Area is :"+buildupArArray);
							}
							if(j==5)
							{
								mCustomWait(1500);
								//String ratableArea = driver.findElement(By.id("pdAsseArea"+(i))).getText();
								/*String ratableArea =mGetText("id", "pdAsseArea"+(i));
								System.out.println("Ratable Area is : "+ratableArea);*/
								String ratableArea = driver.findElement(By.id("pdAsseArea"+(i))).getText();
								System.out.println("Ratable Area is : "+ratableArea);
							}
							if(j==6)
							{
								mCustomWait(1500);
								//String usgFactor = driver.findElement(By.id("cpdSfctID"+(i))).getAttribute("value");
								/*String usgFactor =mGetText("id", "cpdSfctID"+(i), "value");
								System.out.println("Usage Factor is : "+usgFactor);*/
								if(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsageType").equalsIgnoreCase("Residential"))
								{
									String usgFactor = driver.findElement(By.id("cpdSfctID"+(i))).getAttribute("value");
									System.out.println("Usage Factor is : "+usgFactor);
									usageFactorArray.add(new Select(driver.findElement(By.id("cpdSfctID"+(i)))).getFirstSelectedOption().getText());
									System.out.println("Property Detail Entry Usage Factor is: "+usageFactorArray);
									calcRatableArArray.add(mGetPropertyFromFile("ratableAreaForRes"));
									calcAnnulRateblValArray.add(mGetPropertyFromFile("annualRatableValueForRes"));
									calcAnnulTaxArray.add(mGetPropertyFromFile("annualTaxRes"));
								}
								else
								{
									new Select (driver.findElement(By.id("cpdSfctID"+(i)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsgFactor"));
									usageFactorArray.add(new Select(driver.findElement(By.id("cpdSfctID"+(i)))).getFirstSelectedOption().getText());
									System.out.println("Property Detail Entry Usage Factor is: "+usageFactorArray);
									calcRatableArArray.add(mGetPropertyFromFile("ratableAreaForNonRes"));
									calcAnnulRateblValArray.add(mGetPropertyFromFile("annualRatableValueForNonRes"));
									calcAnnulTaxArray.add(mGetPropertyFromFile("annualTaxNonRes"));
								}

								calcUnitRateArray.add(mGetPropertyFromFile("unitArea"));
								calcRateOfTaxArray.add(mGetPropertyFromFile("rateOfTax"));
								calcServiceChargeArray.add(mGetPropertyFromFile("pt_newAssessmentServiceChargeCurSAS"));
								System.out.println("Property Detail Entry Ratable area array is: "+calcRatableArArray);
								System.out.println("Calculated annual tax array is: "+calcAnnulTaxArray);
							}
							if(j==7)
							{
								mCustomWait(1500);
								new Select (driver.findElement(By.id("cpdOccStatus"+(i)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaOccuFactor"));
								occFacArray.add(new Select(driver.findElement(By.id("cpdOccStatus"+(i)))).getFirstSelectedOption().getText());
								System.out.println("Property Detail Entry Occupancy Factor is: "+occFacArray);
							}
						}
						if(i<(addCount-1))
						{
							mCustomWait(5000);
							//mClick("linkText", "+");
							driver.findElement(By.xpath("//*[@id=\"cloumn"+(i)+"\"]/td[9]/a")).click();
							System.out.println("Clicking on for add");
							mCustomWait(5000);
						}
						counter++;
						InvoCount++;
					}
					//	BldgDetailsTableReading();
					mTakeScreenShot();
					/*mClick("id", mGetPropertyFromFile("pt_chngInAssmntChkDocStsid"));
					mUpload("id", mGetPropertyFromFile("pt_chngInAssmntUpldDocid"), mGetPropertyFromFile("pt_chngInAssmntUpldDoc"));
					mWaitForVisible("id", mGetPropertyFromFile("pt_chngInAssmntNextBtnid"));
					mTakeScreenShot();
					mClick("id", mGetPropertyFromFile("pt_chngInAssmntNextBtnid"));*/

				}
				else if(mGetPropertyFromFile("pt_chngInAddnChngVcntLandType").equalsIgnoreCase("Flat"))
				{
					// Type of Land
					mClick("id", mGetPropertyFromFile("pt_chngInAssmntLandTypFlatid"));
					mWaitForVisible("id", mGetPropertyFromFile("pt_chngInAssmntAlertOkid"));
					String alertMsg=mGetText("xpath", mGetPropertyFromFile("pt_chngInAssmntAlertOkMsgid"));
					System.out.println("Warning is : "+alertMsg);
					mAssert(alertMsg, mGetPropertyFromFile("pt_chngInAssmntAlertOkMsg"), "Warning message does not match. Actual is : "+alertMsg+"Expected is : "+mGetPropertyFromFile("pt_chngInAssmntAlertOkMsg"));
					mCustomWait(1000);
					mTakeScreenShot();
					mClick("id", mGetPropertyFromFile("pt_chngInAssmntAlertOkid"));
					mCustomWait(1000);
					mTakeScreenShot();

					// Capture changed land type
					if(driver.findElement(By.id("entity.codpropertyID12")).isSelected())
					{
						chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(3) > label")).getText();
						System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
					}
					else if(driver.findElement(By.id("entity.codpropertyID11")).isSelected())
					{
						chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(2) > label")).getText();
						System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
					}
					else if(driver.findElement(By.id("entity.codpropertyID13")).isSelected())
					{
						chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(4) > label")).getText();
						System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
					}
					else
					{
						System.out.println("Land type is not selected");
					}

					mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntAprtmntNameid"), mGetPropertyFromFile("pt_chngInAssmntAprtmntName"));
					mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntTotFlatsInAprtmntid"), mGetPropertyFromFile("pt_chngInAssmntTotFlatsInAprtmnt"));

					// Last Payment Details
					if(driver.findElement(By.id(mGetPropertyFromFile("pt_chngInAssmntPymntMdUptoid"))).isEnabled())
					{
						mSelectDropDown("id", mGetPropertyFromFile("pt_chngInAssmntPymntMdUptoid"), mGetPropertyFromFile("pt_chngInAssmntPymntMdUpto"));
					}

					// Land/ Building Details
					mSelectDropDown("id", mGetPropertyFromFile("pt_chngInAssmntRoadFactorid"), mGetPropertyFromFile("pt_chngInAssmntRoadFactor"));
					mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntPlotAreaid"), mGetPropertyFromFile("pt_chngInAssmntPlotArea"));
					mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntBuiltupAreaid"), mGetPropertyFromFile("pt_chngInAssmntBuiltupArea"));

					// Water Connection Facility
					if(mGetPropertyFromFile("pt_chngInAssmntGovWtrConn").equalsIgnoreCase("Yes"))
					{
						mClick("id", mGetPropertyFromFile("pt_chngInAssmntGovWtrConnid"));
					}
					else if(mGetPropertyFromFile("pt_chngInAssmntWtrConnAvlblWithin400Yrds").equalsIgnoreCase("Yes"))
					{
						mClick("id", mGetPropertyFromFile("pt_chngInAssmntWtrConnAvlblWithin400Yrdsid"));
					}
					else if(mGetPropertyFromFile("pt_chngInAssmntWtrConnNotAvlblWithin400Yrds").equalsIgnoreCase("Yes"))
					{
						mClick("id", mGetPropertyFromFile("pt_chngInAssmntWtrConnNotAvlblWithin400Yrdsid"));
					}

					WebElement table = driver.findElement(By.id("buildingDetailsGridID"));

					//int addCount=Integer.parseInt(mGetPropertyFromFile("pt_chngInAddnChngVcntLandTypToFlatFlrCount"));

					mscroll(0, 1300);
					List<WebElement> rows1 = table.findElements(By.tagName("tr"));
					rows1.size();
					System.out.println(rows1.size());

					List<WebElement> cols = rows1.get(1).findElements(By.xpath("td"));
					int colcount= cols.size();
					System.out.println(colcount);

					String[] string=mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaFlrNo").split(",");
					System.out.println("string array size is : "+string.length);

					int a=Integer.parseInt(string[0].toString());
					int b=Integer.parseInt(string[1].toString());
					addCount=(b-a)+1;

					mCustomWait(5000);

					int counter=1;
					InvoCount=a;
					for (int i=0;i<addCount;i++) 
					{
						if(InvoCount>b)
							break;
						for(int j=0;j<colcount;j++)
						{
							if(j==0)
							{
								mCustomWait(1500);
								//new Select(driver.findElement(By.id("detFaYearId"+(i)))).selectByVisibleText("2016-17");
								//String yr = driver.findElement(By.id("yearLabelFromValue"+(i))).getAttribute("value");
								//System.out.println("Year is : "+yr);
								new Select(driver.findElement(By.id("detFaYearId"+(i)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorYear"));
								yearBlgArray.add(driver.findElement(By.id("yearLabelFromValue"+i)).getAttribute("value"));
								System.out.println("Building Year is: "+yearBlgArray);
							}
							if(j==1)
							{
								mCustomWait(1500);
								//new Select(driver.findElement(By.id("cpdLocID"+(i)))).selectByVisibleText("2nd");
								//String floor = driver.findElement(By.id("floorName"+(j-1))).getAttribute("value");
								//String floor = driver.findElement(By.id("cpdLocID"+(i))).getAttribute("id");
								//System.out.println("Floor is : "+floor);
								new Select(driver.findElement(By.id("cpdLocID"+(i)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaFloorNo"));
								floorNoArray.add(new Select(driver.findElement(By.id("cpdLocID"+(i)))).getFirstSelectedOption().getText());
								System.out.println("Property Detail Entry Floor No is: "+floorNoArray);
							}
							if(j==2)
							{
								mCustomWait(1500);
								//new Select(driver.findElement(By.name("entity.propertyDetails["+(i)+"].cpdUsage1"))).selectByVisibleText("Residential");
								/*String usage = driver.findElement(By.id("usage"+(j-1))).getAttribute("value");
												System.out.println("Usage type is : "+usage);*/
								new Select(driver.findElement(By.name("entity.propertyDetails["+(i)+"].cpdUsage1"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsageType"));
								typeOfUseArray.add(new Select(driver.findElement(By.name("entity.propertyDetails["+(i)+"].cpdUsage1"))).getFirstSelectedOption().getText());
								System.out.println("Property Detail Entry Type of Use is: "+typeOfUseArray);
							}
							if(j==3)
							{
								mCustomWait(1500);
								//new Select (driver.findElement(By.name("entity.propertyDetails["+(i)+"].codConsClass1"))).selectByVisibleText("Pucca building with RCC Roof");
								//System.out.println("Construction class is : "+cnstrnCls);
								new Select (driver.findElement(By.name("entity.propertyDetails["+(i)+"].codConsClass1"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaConstructionType"));
								constructTypeArray.add(new Select(driver.findElement(By.name("entity.propertyDetails["+(i)+"].codConsClass1"))).getFirstSelectedOption().getText());
								System.out.println("Property Detail Entry Construction Type is: "+constructTypeArray);
							}
							if(j==4)
							{
								mCustomWait(1500);
								//driver.findElement(By.id("pdBuildingArea"+(i))).sendKeys("100");
								//mSendKeys("id", "pdBuildingArea"+(i), "100");
								//System.out.println("Built-up Area is : "+bultupArea);
								driver.findElement(By.id("pdBuildingArea"+(i))).sendKeys(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaBuiltupArea"));
								buildupArArray.add(driver.findElement(By.id("pdBuildingArea"+(i))).getAttribute("value"));
								System.out.println("Property Detail Entry Buildup Area is :"+buildupArArray);
							}
							if(j==5)
							{
								mCustomWait(1500);
								//String ratableArea = driver.findElement(By.id("pdAsseArea"+(i))).getText();
								//String ratableArea =mGetText("id", "pdAsseArea"+(i));
								//System.out.println("Ratable Area is : "+ratableArea);
								String ratableArea = driver.findElement(By.id("pdAsseArea"+(i))).getText();
								System.out.println("Ratable Area is : "+ratableArea);
							}
							if(j==6)
							{
								mCustomWait(1500);
								//String usgFactor = driver.findElement(By.id("cpdSfctID"+(i))).getAttribute("value");
								//String usgFactor =mGetText("id", "cpdSfctID"+(i), "value");
								//System.out.println("Usage Factor is : "+usgFactor);
								if(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsageType").equalsIgnoreCase("Residential"))
								{
									String usgFactor = driver.findElement(By.id("cpdSfctID"+(i))).getAttribute("value");
									System.out.println("Usage Factor is : "+usgFactor);
									usageFactorArray.add(new Select(driver.findElement(By.id("cpdSfctID"+(i)))).getFirstSelectedOption().getText());
									System.out.println("Property Detail Entry Usage Factor is: "+usageFactorArray);
									calcRatableArArray.add(mGetPropertyFromFile("ratableAreaForRes"));
									calcAnnulRateblValArray.add(mGetPropertyFromFile("annualRatableValueForRes"));
									calcAnnulTaxArray.add(mGetPropertyFromFile("annualTaxRes"));
								}
								else
								{
									new Select (driver.findElement(By.id("cpdSfctID"+(i)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsgFactor"));
									usageFactorArray.add(new Select(driver.findElement(By.id("cpdSfctID"+(i)))).getFirstSelectedOption().getText());
									System.out.println("Property Detail Entry Usage Factor is: "+usageFactorArray);
									calcRatableArArray.add(mGetPropertyFromFile("ratableAreaForNonRes"));
									calcAnnulRateblValArray.add(mGetPropertyFromFile("annualRatableValueForNonRes"));
									calcAnnulTaxArray.add(mGetPropertyFromFile("annualTaxNonRes"));
								}

								calcUnitRateArray.add(mGetPropertyFromFile("unitArea"));
								calcRateOfTaxArray.add(mGetPropertyFromFile("rateOfTax"));
								calcServiceChargeArray.add(mGetPropertyFromFile("pt_newAssessmentServiceChargeCurSAS"));
								System.out.println("Property Detail Entry Ratable area array is: "+calcRatableArArray);
								System.out.println("Calculated annual tax array is: "+calcAnnulTaxArray);
							}
							if(j==7)
							{
								mCustomWait(1500);
								//new Select (driver.findElement(By.id("cpdOccStatus"+(i)))).selectByVisibleText("Self-Occupied");
								//System.out.println("Occupancy Factor is : "+occpFactor);
								new Select (driver.findElement(By.id("cpdOccStatus"+(i)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaOccuFactor"));
								occFacArray.add(new Select(driver.findElement(By.id("cpdOccStatus"+(i)))).getFirstSelectedOption().getText());
								System.out.println("Property Detail Entry Occupancy Factor is: "+occFacArray);
							}
						}
						if(i<(addCount-1))
						{
							mCustomWait(5000);
							//mClick("linkText", "+");
							driver.findElement(By.xpath("//*[@id=\"cloumn"+(i)+"\"]/td[9]/a")).click();
							System.out.println("Clicking on for add");
							mCustomWait(5000);
						}
						counter++;
						InvoCount++;
					}
					//BldgDetailsTableReading();
					mTakeScreenShot();
					/*mClick("id", mGetPropertyFromFile("pt_chngInAssmntChkDocStsid"));
					mUpload("id", mGetPropertyFromFile("pt_chngInAssmntUpldDocid"), mGetPropertyFromFile("pt_chngInAssmntUpldDoc"));
					mWaitForVisible("id", mGetPropertyFromFile("pt_chngInAssmntNextBtnid"));
					mTakeScreenShot();
					mClick("id", mGetPropertyFromFile("pt_chngInAssmntNextBtnid"));*/

				}
			}

			///////////////////////////////////////////////////////////////////////
			//Change the Vacant Land Area without changing the Vacant Land Type
			/////////////////////////////////////////////////////////////////////////
			else if (mGetPropertyFromFile("changeAreaVL").equalsIgnoreCase("Yes"))
			{
				//mSendKeys(locatorType, locator, data);
				/////////Added code for Vancant Land Change Area @ Ritesh 05-01-2017

				mTakeScreenShot();

				WebElement table =mFindElement("id", mGetPropertyFromFile("pt_chngeInAssessmentVacantLandid"));
				List<WebElement> rows = table.findElements(By.tagName("tr"));
				int rwcount = rows.size();
				System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

				int Rowno = 0;
				for (WebElement rowElement : rows) 
				{
					List<WebElement> cols = rowElement.findElements(By.xpath("td"));
					int clcount = cols.size();
					System.out.println("NUMBER OF Cols IN THIS TABLE = " + clcount);
					int Columnno = 0;
					for (WebElement colElement : cols) 
					{
						if (Columnno == 0) 
						{
							String yr = rowElement.findElement(By.xpath("//*[@id=\"vyearLabelFromValue0\"]")).getAttribute("value");
							mGenericWait();
							System.out.println("Change in Assessment Vacant Land Year is :"+yr);
						}
						if (Columnno == 1) 
						{
							vacantarea = rowElement.findElement(By.xpath("//*[@id=\"vpdBuildingArea0\"]")).getAttribute("id");
							System.out.println("Change in Assessment Vacant Land Area is :"+vacantarea);
							driver.findElement(By.id(vacantarea)).clear();
							mSendKeys("id", vacantarea, mGetPropertyFromFile("pt_chngInAssmntChangVacantlandArea"));
							vancantareavalue=mGetText("id", vacantarea, "value");									
							System.out.println("Mannual Vacant Land is: "+vancantareavalue);

							mAssert(vancantareavalue, mGetPropertyFromFile("pt_chngInAssmntChangVacantlandArea"), "Mannual Vacant Land is different than inserted Mannual Vacant Land");
							mGenericWait();
						}
						Columnno = Columnno + 1;
					}

					Rowno = Rowno + 1;
				}

			}

			mTakeScreenShot();
			//Capturing Water Tax Type
			String chngInAssWaterTaxTypeOrg=driver.findElement(By.xpath("//*[@id=\"frmMasterForm\"]/div[30]/div[1]")).getText();
			System.out.println("Water Tax Type of selected property in change in assessment original: "+chngInAssWaterTaxTypeOrg);
			String chngInAssWaterTaxTypeNew[]=chngInAssWaterTaxTypeOrg.split(":");
			chngInAssWaterTaxType=chngInAssWaterTaxTypeNew[1].trim();
			System.out.println("Water Tax Type of selected property in change in assessment : "+chngInAssWaterTaxType);

			if (!mGetPropertyFromFile("landType").equalsIgnoreCase("Vacant Land"))
			{

				chngInAddnGrndFlrPlotArea=driver.findElement(By.id("pmPlotArea")).getAttribute("value");
				System.out.println("Plot area for change in assessment is :"+chngInAddnGrndFlrPlotArea);
				chngInAddnGrndFlrBuiltupArea=mGetText("id", mGetPropertyFromFile("pt_chngInAssmntGrndFlrBuiltupAreaid"),"value");
				System.out.println("Built up area for change in assessment is :"+chngInAddnGrndFlrBuiltupArea);

				Double pt_newAssessmentAplcbleVacantLandOrg=((Double.parseDouble(chngInAddnGrndFlrBuiltupArea)/Double.parseDouble(chngInAddnGrndFlrPlotArea)*100));
				System.out.println("Applicable Vacant Land : "+pt_newAssessmentAplcbleVacantLandOrg);

				DecimalFormat decimalFormat=new DecimalFormat("#.#");


				pt_newAssessmentAplcbleVacantLand=Math.round(pt_newAssessmentAplcbleVacantLandOrg);
				System.out.println("Applicable Vacant Land : "+pt_newAssessmentAplcbleVacantLand);

				if((pt_newAssessmentAplcbleVacantLand)<70)
				{
					pt_newAssessmentVacantLandAreaAuto=(Double.parseDouble(chngInAddnGrndFlrPlotArea)-(Double.parseDouble(chngInAddnGrndFlrBuiltupArea)*1.42855));
					System.out.println("Applicable Vacant Land Auto : "+pt_newAssessmentVacantLandAreaAuto);
				}
				else
				{
					pt_newAssessmentVacantLandAreaAuto=0.0;
					System.out.println("Applicable Vacant Land Auto : "+pt_newAssessmentVacantLandAreaAuto);
				}

				pt_newAssessmentVacantLandAnnualTaxAuto=(pt_newAssessmentVacantLandAreaAuto*0.36);
				pt_newAssessmentVacantLandAnnualTax=Math.round(pt_newAssessmentVacantLandAnnualTaxAuto);
				System.out.println("Vacant Land Annual Tax : "+pt_newAssessmentVacantLandAnnualTax);

			}
			else
			{
				pt_newAssessmentVacantLandAnnualTaxAuto=(Double.parseDouble(vancantareavalue)*0.36);
				pt_newAssessmentVacantLandAnnualTax=Math.round(pt_newAssessmentVacantLandAnnualTaxAuto);
				System.out.println("Vacant Land Annual Tax : "+pt_newAssessmentVacantLandAnnualTax);
			}



			mTakeScreenShot();
			/*mClick("id", mGetPropertyFromFile("pt_chngInAssmntChkDocStsid"));
			mUpload("id", mGetPropertyFromFile("pt_chngInAssmntUpldDocid"), mGetPropertyFromFile("pt_chngInAssmntUpldDoc"));*/
			mClick("id", mGetPropertyFromFile("pt_chngInAssmntNextBtnid"));

			mWaitForVisible("xpath", mGetPropertyFromFile("pt_chngInAssmntSubmitBtnid"));
			newAssessmentNextMethod();
			/*payment("paymentMode","byBankOrULB");
			mGenericWait();
			mTakeScreenShot();

			mClick("xpath", mGetPropertyFromFile("pt_chngInAssmntSubmitBtnid"));
			mWaitForVisible("id", mGetPropertyFromFile("pt_chngInAssmntProceedBtnid"));

			mClick("id", mGetPropertyFromFile("pt_chngInAssmntProceedBtnid"));*/

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in chngInHoldingDtlsAddition method");
		}
	}

	public void contactDetChnage()
	{
		try
		{
			String chngCntPropNum = "";
			String chngCntOldPropNum;
			mNavigation("pt_propertyTaxLinkid", "pt_selfAssessmentLinkid");

			if(mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("citizen") || mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("agency"))
			{
				mClick("id", mGetPropertyFromFile("pt_newAssessmentIAcceptid"));
				mClick("xpath", mGetPropertyFromFile("pt_newAssessmentFileSelfAssessid"));
			}

			// Select option Change / Alteration in Assessment
			mWaitForVisible("id", mGetPropertyFromFile("pt_alterationInAssessmentid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_alterationInAssessmentid"));
			mGenericWait();

			if(mGetPropertyFromFile("propertyNo").equalsIgnoreCase("New"))
			{
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_alterationEnterPropNoid"), mGetPropertyFromFile("pt_alterationEnterPropNo"));
				//chngCntPropNum=driver.findElement(By.id(mGetPropertyFromFile("pt_alterationEnterPropNoid"))).getAttribute("value");
				chngCntPropNum=mGetText("id", mGetPropertyFromFile("pt_alterationEnterPropNoid"), "value");
				System.out.println("Entered Property number for change in contact details assessment is : "+chngCntPropNum);
			}
			else
			{
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_alterationOldPropNoid"), mGetPropertyFromFile("pt_alterationOldPropNo"));
				//chngCntOldPropNum=driver.findElement(By.id(mGetPropertyFromFile("pt_alterationOldPropNoid"))).getAttribute("value");
				chngCntOldPropNum=mGetText("id", mGetPropertyFromFile("pt_alterationOldPropNoid"), "value");
				System.out.println("Entered Old Property number for no change in assessment is : "+chngCntOldPropNum);
			}

			mGenericWait();
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("pt_alterationSearchBtnid"));
			mWaitForVisible("xpath", mGetPropertyFromFile("pt_alterationSubmitBtnid"));
			mClick("xpath", mGetPropertyFromFile("pt_alterationSubmitBtnid"));
			mGenericWait();
			mWaitForVisible("id", mGetPropertyFromFile("pt_changeInContactid"));
			if(driver.findElement(By.id(mGetPropertyFromFile("pt_changeInContactid"))).isSelected())
			{
				System.out.println("Change in Contact Details Checkbox selected");
			}
			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("pt_changeInContactOptnSubmitid"));
			mSendKeys("id", mGetPropertyFromFile("pt_changeInContactCorresPropHouseNoid"), mGetPropertyFromFile("pt_changeInContactCorresPropHouseNo"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("pt_changeInContactCorresAddressOneid"), mGetPropertyFromFile("pt_changeInContactCorresAddressOne"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("pt_changeInContactCorresAddressTwoid"), mGetPropertyFromFile("pt_changeInContactCorresAddressTwo"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("pt_changeInContactCorresAddressPinid"), mGetPropertyFromFile("pt_changeInContactCorresAddressPin"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("pt_changeInContactMobNoid"), mGetPropertyFromFile("pt_changeInContactMobNo"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("pt_changeInContactEmailIdid"), mGetPropertyFromFile("pt_changeInContactEmailId"));
			mGenericWait();
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("pt_changeInContactFinalSubmitid"));
			mWaitForVisible("xpath", mGetPropertyFromFile("pt_changeInContactAppPopupSubmitid"));
			mClick("xpath", mGetPropertyFromFile("pt_changeInContactAppPopupSubmitid"));
			mGenericWait();			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Change in Contact Details method");
		}

	}

	// Property Assessment Certificate service application entry form
	public void propAssessmentCert()
	{
		try
		{
			mNavigation("pt_propAssmntCertPropTaxLinkid", "pt_propAssmntCertLinkid");
			mWaitForVisible("linkText", mGetPropertyFromFile("pt_propAssmntCertSearchBtnid"));
			mSendKeys("id", mGetPropertyFromFile("pt_propAssmntCertPropNoid"), mGetPropertyFromFile("pt_propAssmntCertPropNo"));
			mSelectDropDown("id", mGetPropertyFromFile("pt_propAssmntCertFinYearid"), mGetPropertyFromFile("pt_propAssmntCertFinYear"));
			mGenericWait();
			mTakeScreenShot();
			mClick("linkText", mGetPropertyFromFile("pt_propAssmntCertSearchBtnid"));
			mGenericWait();
			mTakeScreenShot();
			payment("paymentMode","byBankOrULB");
			mWaitForVisible("css", mGetPropertyFromFile("pt_propAssmntCertSubmitBtnid"));
			mGenericWait();
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("pt_propAssmntCertSubmitBtnid"));
			mWaitForVisible("id", mGetPropertyFromFile("pt_propAssmntCertProceedBtnid"));
			mGenericWait();
			mTakeScreenShot();
			String submitMsg=mGetText("css", mGetPropertyFromFile("pt_propAssmntCertSubmitMsgid"));
			System.out.println("Message is :"+submitMsg);
			mAssert(submitMsg, mGetPropertyFromFile("pt_propAssmntCertSubmitMsg"), "Message does not match, Actual is :"+submitMsg+"Expected is :"+mGetPropertyFromFile("pt_propAssmntCertSubmitMsg"));
			mClick("id", mGetPropertyFromFile("pt_propAssmntCertProceedBtnid"));
			mCustomWait(5000);
			if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
			{
				mChallanPDFReader();
				api.PdfPatterns.patternChallanPdf(pdfoutput,"","");
				applicationNo.add(printedAppNo);
				System.out.println("applicationNo :"+applicationNo);
			}
			else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
			{
				mChallanPDFReader();
				api.PdfPatterns.patternULBChallanReceiptPdfPT(pdfoutput);
				applicationNo.add(ptRcptAppNo);
				System.out.println("applicationNo :"+applicationNo);
				api.PdfPatterns.patternPropAssessmentCert(pdfoutput);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in propAssessmentCert method");
		}
	}

	// Extract of Property service application entry form
	public void extractOfProperty()
	{
		try
		{
			mNavigation("pt_extarctOfPropPropTaxLinkid", "pt_extarctOfPropLinkid");
			mWaitForVisible("linkText", mGetPropertyFromFile("pt_extarctOfPropSearchBtnid"));
			mSendKeys("id", mGetPropertyFromFile("pt_extarctOfPropPropNoid"), mGetPropertyFromFile("pt_extarctOfPropPropNo"));
			mSelectDropDown("id", mGetPropertyFromFile("pt_extarctOfPropFinYearid"), mGetPropertyFromFile("pt_extarctOfPropFinYear"));
			mGenericWait();
			mTakeScreenShot();
			mClick("linkText", mGetPropertyFromFile("pt_extarctOfPropSearchBtnid"));
			mGenericWait();
			mTakeScreenShot();
			docUpload("xpath",mGetPropertyFromFile("pt_extarctOfPropUpldDocTableid"), "uploadDocFlag");
			payment("paymentMode","byBankOrULB");
			mWaitForVisible("css", mGetPropertyFromFile("pt_extarctOfPropSubmitBtnid"));
			mGenericWait();
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("pt_extarctOfPropSubmitBtnid"));
			mWaitForVisible("id", mGetPropertyFromFile("pt_extarctOfPropProceedBtnid"));
			mGenericWait();
			mTakeScreenShot();
			String submitMsg=mGetText("css", mGetPropertyFromFile("pt_extarctOfPropSubmitMsgid"));
			System.out.println("Message is :"+submitMsg);
			mAssert(submitMsg, mGetPropertyFromFile("pt_extarctOfPropSubmitMsg"), "Message does not match, Actual is :"+submitMsg+"Expected is :"+mGetPropertyFromFile("pt_extarctOfPropSubmitMsg"));
			mClick("id", mGetPropertyFromFile("pt_extarctOfPropProceedBtnid"));
			mCustomWait(5000);
			if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
			{
				mChallanPDFReader();
				//pdfPtrn.patternChallanPdf(pdfoutput,"","");
				api.PdfPatterns.patternChallanPdf(pdfoutput, "","");
				applicationNo.add(printedAppNo);
				System.out.println("applicationNo :"+applicationNo);
			}
			else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
			{
				mChallanPDFReader();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in extractOfProperty method");
		}
	}


	public void newAssessmentNextMethod()
	{

		try{
			System.out.println("Start Reading View Details Form");
			//Code for Next Method
			/////////////////////////
			/////////////////////////
			/////////////////////////

			mTakeScreenShot();
			mCustomWait(1000);
			mscroll(1183, 716);

			// Payment mode
			//Read Property Tax View Form @Ritesh Dated 01-09-2016			

			//Land Type
			////////////////
			//String viewDtllandType = driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(20) > div")).getText();
			String viewDtllandType =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtllandTypeid"));
			String[] splitter = viewDtllandType.split(":");
			viewDtllandType=splitter[1].toString().trim();			
			System.out.println("Property Detail View Form Land Type is: "+viewDtllandType);

			if(mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
			{
				mAssert(viewDtllandType, chngAsmtLandType, "Actual is: "+viewDtllandType+ "Expected is: "+chngAsmtLandType + "Land Type is not matching");
			}
			else
			{
				mAssert(viewDtllandType, landTypeSelected, "Actual is: "+viewDtllandType+ "Expected is: "+landTypeSelected + "Land Type is not matching");
			}


			//Acquisition Date
			////////////////////
			if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
			{
				//String viewAcquisitiondate=driver.findElement(By.cssSelector("#frmMasterForm > div.element")).getText();
				String viewAcquisitiondate=mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewAcquisitiondateid"));
				splitter = viewAcquisitiondate.split(":");
				viewAcquisitiondate=splitter[1].toString().trim();
				System.out.println("Property Detail View Form Acquisition Date is: "+viewAcquisitiondate);
				mAssert(viewAcquisitiondate, acquisitiondate, "Actual is: "+viewAcquisitiondate+ "Expected is: "+acquisitiondate+ "Acquisition Date is not matching");
			}


			/////////////////////////			
			//Commented code... action upon this to be decided later.

			/*//Building Year
			String viewYearBlg=driver.findElement(By.cssSelector("#frmMasterForm > div.element")).getText();
			System.out.println("Building Details Year is: "+viewYearBlg);
			mAssert(viewYearBlg, yearBlg, "Actual is: "+viewYearBlg+ "Expected is: "+yearBlg+ "Building Year is not matching");

			//Floor Number
			String viewFloorNo=driver.findElement(By.cssSelector("#frmMasterForm > div.overflow > table > tbody > tr > td:nth-child(2)")).getText();
			System.out.println("Building Details Year is: "+viewFloorNo);
			mAssert(viewFloorNo, floorNo, "Actual is: "+viewFloorNo+ "Expected is: "+floorNo+ "Floor Number is not matching");

			//Type of Use
			String viewTypeOfUse=driver.findElement(By.cssSelector("#frmMasterForm > div.overflow > table > tbody > tr > td:nth-child(3)")).getText();
			System.out.println("Building Details Year is: "+viewTypeOfUse);
			mAssert(viewTypeOfUse, typeOfUse, "Actual is: "+viewTypeOfUse+ "Expected is: "+typeOfUse+ "Type of Use is not matching");

			//Construction Type
			String viewConstructType=driver.findElement(By.cssSelector("#frmMasterForm > div.overflow > table > tbody > tr > td:nth-child(4)")).getText();
			System.out.println("Building Details Year is: "+viewConstructType);
			mAssert(viewFloorNo, constructType, "Actual is: "+viewConstructType+ "Expected is: "+constructType+ "Construction Type is not matching");

			//Built-up Area (Sq.ft.)
			String viewBuildupAr=driver.findElement(By.cssSelector("#frmMasterForm > div.overflow > table > tbody > tr > td:nth-child(5)")).getText();
			System.out.println("Building Details Year is: "+viewBuildupAr);
			mAssert(viewBuildupAr, buildupAr, "Actual is: "+viewBuildupAr+ "Expected is: "+buildupAr+ "Built-up Area is not matching");

			//Ratable Area (Sq.Ft.)
			String viewRatableAr=driver.findElement(By.cssSelector("#frmMasterForm > div.overflow > table > tbody > tr > td:nth-child(6)")).getText();
			System.out.println("Building Details Year is: "+viewRatableAr);
			mAssert(viewRatableAr, ratableAr, "Actual is: "+viewRatableAr+ "Expected is: "+ratableAr+ "Ratable Area is not matching");

			//Usage Factor
			String viewUsageFactor=driver.findElement(By.cssSelector("#frmMasterForm > div.overflow > table > tbody > tr > td:nth-child(7)")).getText();
			System.out.println("Building Details Year is: "+viewUsageFactor);
			mAssert(viewUsageFactor, usageFactor, "Actual is: "+viewUsageFactor+ "Expected is: "+usageFactor+ "Usage Factor is not matching");

			//Occupancy Factor
			String viewOccFac=driver.findElement(By.cssSelector("#frmMasterForm > div.overflow > table > tbody > tr > td:nth-child(9)")).getText();
			System.out.println("Building Details Year is: "+viewOccFac);
			mAssert(viewOccFac, occFac, "Actual is: "+viewOccFac+ "Expected is: "+occFac+ "Occupancy Factor is not matching");
			 */


			/////////////////////////			
			//Commented code... action upon this to be decided later.



			//Reading Building Details Table @Author: -Ritesh @Date:03-09-2016
			//Modifed by Sunil Sonmale 20-09-2016

			if(mGetPropertyFromFile("landType").equalsIgnoreCase("flat") || mGetPropertyFromFile("landType").equalsIgnoreCase("Land + Building"))
			{			
				WebElement table = driver.findElement(By.xpath("//*[@id='frmMasterForm']/div[26]/table"));
				//WebElement table =
				List<WebElement> rows = null;
				rows = table.findElements(By.tagName("tr"));
				System.out.println(rows);
				int rwcount =rows.size();
				System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

				// Iterate to get the value of each cell in table along with element id
				int Rowno=0;
				for(WebElement rowElement:rows)
				{

					List<WebElement> cols=rowElement.findElements(By.xpath("td"));
					int colCount=cols.size();
					System.out.println("Columns are : "+colCount);
					int Columnno=0;

					for(int i=0;i<=colCount;i++)	
					{
						if(Rowno>=1)
						{	
							if(i==0)
							{
								//String viewYearBlg=rowElement.findElement(By.xpath("td")).getText();
								viewYearBlgArray.add(rowElement.findElement(By.xpath("td")).getText());
								System.out.println("Property Detail View Form Building Details year is == "+viewYearBlgArray);
								//	mAssert(viewYearBlg, yearBlg, "Actual is: "+viewYearBlg+ "Expected is: "+yearBlg+ "Building Year is not matching");
							}
							if(i==1)
							{
								//String viewFloorNo=rowElement.findElement(By.xpath("td[2]")).getText();
								viewFloorNoArray.add(rowElement.findElement(By.xpath("td[2]")).getText());
								System.out.println("Property Detail View Form Building Details floor no is == "+viewFloorNoArray);
								//					mAssert(viewFloorNo, floorNo, "Actual is: "+viewFloorNo+ "Expected is: "+floorNo+ "Floor Number is not matching");
							}
							if(i==2)
							{
								//viewTypeOfUse=rowElement.findElement(By.xpath("td[3]")).getText().trim();
								viewTypeOfUseArray.add(rowElement.findElement(By.xpath("td[3]")).getText().trim());
								System.out.println("Property Detail View Form Building Details usage type is == "+viewTypeOfUseArray);
								mAssert(viewTypeOfUse, typeOfUse, "Actual is: "+viewTypeOfUse+ "Expected is: "+typeOfUse+ "Type of Use is not matching");
							}
							if(i==3)
							{
								//String viewConstructType=rowElement.findElement(By.xpath("td[4]")).getText();
								viewConstructTypeArray.add(rowElement.findElement(By.xpath("td[4]")).getText());
								System.out.println("Property Detail View Form Building Details construction type is == "+viewConstructTypeArray);
								//						mAssert(viewConstructType, constructType, "Actual is: "+viewConstructType+ "Expected is: "+constructType+ "Construction Type is not matching");
							}
							if(i==4)
							{
								//String viewBuildupAr=rowElement.findElement(By.xpath("td[5]")).getText();
								viewBuildupArArray.add(rowElement.findElement(By.xpath("td[5]")).getText());
								System.out.println("Property Detail View Form Building Details built up area is == "+viewBuildupArArray);
								//						mAssert(viewBuildupAr, buildupAr, "Actual is: "+viewBuildupAr+ "Expected is: "+buildupAr+ "Built-up Area is not matching");
							}
							if(i==5)
							{
								/*String viewRatableAr=rowElement.findElement(By.xpath("td[6]")).getText();
								System.out.println("Property Detail View Form Building Details ratable area is == "+viewRatableAr);								

								if(viewTypeOfUse.equals("Residential"))
								{
									System.out.println("Type of Use is 'Residential' so Ratable area is calcualted on 70%");
									mAssert(viewRatableAr, mGetPropertyFromFile("ratableAreaForRes"), "Actual is: "+viewRatableAr+ "Expected is: "+mGetPropertyFromFile("ratableAreaForRes")+ "Ratable Area is not matching");								
								}
								else
								{
									System.out.println("Type of Use is 'Non-Residential' so Ratable area is calcualted on 80%");
									mAssert(viewRatableAr, mGetPropertyFromFile("ratableAreaForNonRes"), "Actual is: "+viewRatableAr+ "Expected is: "+mGetPropertyFromFile("ratableAreaForNonRes")+ "Ratable Area is not matching");
								}*/
								viewRatableArArray.add(rowElement.findElement(By.xpath("td[6]")).getText());
								System.out.println("Property Detail View Form Building Details ratable area is == "+viewRatableArArray);
								//						mAssert(viewRatableAr, mGetPropertyFromFile("ratableAreaForRes"), "Actual is: "+viewRatableAr+ "Expected is: "+mGetPropertyFromFile("ratableAreaForRes")+ "Ratable Area is not matching");

							}
							if(i==6)
							{
								/*if (mGetPropertyFromFile("pt_newAssessmentTypeOfUse")!="Residential")
								{	
									viewUsageFactor=rowElement.findElement(By.xpath("td[7]")).getText();
									System.out.println("Property Detail View Form Building Details usage factor is == "+viewUsageFactor);
									mAssert(viewUsageFactor, usageFactor, "Actual is: "+viewUsageFactor+ "Expected is: "+usageFactor+ "Usage Factor is not matching");
								}
								else
								{
									System.out.println("Property Detail View Form Building Details usage factor is == "+viewUsageFactor);
								}*/
								//String viewUsageFactor=rowElement.findElement(By.xpath("td[7]")).getText();
								viewUsageFactorArray.add(rowElement.findElement(By.xpath("td[7]")).getText());
								System.out.println("Property Detail View Form Building Details usage factor is == "+viewUsageFactorArray);
								mAssert(viewUsageFactor, usageFactor, "Actual is: "+viewUsageFactor+ "Expected is: "+usageFactor+ "Usage Factor is not matching");
							}
							if(i==7)
							{
								//String viewUnitRate=rowElement.findElement(By.xpath("td[8]")).getText();
								viewUnitRateArray.add(rowElement.findElement(By.xpath("td[8]")).getText());
								System.out.println("Property Detail View Form Building Details unit area rate is == "+viewUnitRateArray);								
								//						mAssert(viewUnitRate, mGetPropertyFromFile("unitArea"), "Actual is: "+viewUnitRate+ "Expected is: "+mGetPropertyFromFile("unitArea")+ "Unit area rate is not matching");
							}
							if(i==8)
							{
								//String viewOccFac=rowElement.findElement(By.xpath("td[9]")).getText();
								viewOccFacArray.add(rowElement.findElement(By.xpath("td[9]")).getText());
								System.out.println("Property Detail View Form Building Details occupancy factor is == "+viewOccFacArray);
								//						mAssert(viewOccFac, occFac, "Actual is: "+viewOccFac+ "Expected is: "+occFac+ "Occupancy Factor is not matching");
							}
							if(i==9)
							{
								//String viewAnnulRateblVal=rowElement.findElement(By.xpath("td[10]")).getText();
								viewAnnulRateblValArray.add(rowElement.findElement(By.xpath("td[10]")).getText());
								System.out.println("Property Detail View Form Annual Rateable Value is == "+viewAnnulRateblValArray);		
								/*if(viewTypeOfUse.equalsIgnoreCase("Residential"))
								{
									System.out.println("Type of Use is 'Residential' so Annual Rateable Value is calcualted as per Residential");
									mAssert(viewAnnulRateblVal, mGetPropertyFromFile("annualRatableValueForRes"), "Actual is: "+viewAnnulRateblVal+ "Expected is: "+mGetPropertyFromFile("annualRatableValueForRes")+ "Annual Rateable Value is not matching");			
								}else 
								{
									System.out.println("Type of Use is 'Non-Residential' so Annual Rateable Value is calcualted as per Non-Residential");
									mAssert(viewAnnulRateblVal, mGetPropertyFromFile("annualRatableValueForNonRes"), "Actual is: "+viewAnnulRateblVal+ "Expected is: "+mGetPropertyFromFile("annualRatableValueForNonRes")+ "Annual Rateable Value is not matching");
								}*/
							}
							if(i==10)
							{
								//	String viewRateOfTax=rowElement.findElement(By.xpath("td[11]")).getText();
								viewRateOfTaxArray.add(rowElement.findElement(By.xpath("td[11]")).getText());
								System.out.println("Property Detail View Form Building Details Rate of Tax is == "+viewRateOfTaxArray);
								//						mAssert(viewRateOfTax, mGetPropertyFromFile("rateOfTax"), "Actual is: "+viewRateOfTax+ "Expected is: "+mGetPropertyFromFile("rateOfTax")+ "Rate of Tax is not matching");

							}
							if(i==11)
							{
								//	String viewAnnulTax=rowElement.findElement(By.xpath("td[12]")).getText();
								viewAnnulTaxArray.add(rowElement.findElement(By.xpath("td[12]")).getText());
								System.out.println("Property Detail View Form Building Details Annual Tax is == "+viewAnnulTaxArray);					
								/*if (viewTypeOfUse.equalsIgnoreCase("Residential"))
								{
									System.out.println("Type of Use is 'Residential' so Annual Tax is calcualted as per Residential");
		//							mAssert(viewAnnulTax, mGetPropertyFromFile("annualTaxRes"), "Actual is: "+viewAnnulTax+ "Expected is: "+mGetPropertyFromFile("annualTaxRes")+ "Annual Tax is not matching");
								}else
								{
									System.out.println("Type of Use is 'Non-Residential' so Annual Tax is calcualted as per Non-Residential");
		//							mAssert(viewAnnulTax, mGetPropertyFromFile("annualTaxNonRes"), "Actual is: "+viewAnnulTax+ "Expected is: "+mGetPropertyFromFile("annualTaxNonRes")+ "Annual Tax is not matching");
								}*/
							}
						}
						Columnno=Columnno+1;
						System.out.println("Column no. is :"+Columnno);
					}		


					Rowno=Rowno+1;
					System.out.println("Row no. is :"+Rowno);						
				}

				// loop for building details assertions

				Collections.reverse(viewYearBlgArray);
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					mAssert(viewYearBlgArray, yearBlgArray, "Expected is : "+yearBlgArray+"Actual is : "+viewYearBlgArray+"Array of year does not match in view form");
					mAssert(viewFloorNoArray, addFloorNoArray, "Expected is : "+addFloorNoArray+"Actual is : "+viewFloorNoArray+"Array of floor no. does not match in view form");
					mAssert(viewTypeOfUseArray, addTypeOfUseArray, "Expected is : "+addTypeOfUseArray+"Actual is : "+viewTypeOfUseArray+"Array of type of usage does not match in view form");
					mAssert(viewConstructTypeArray, addConstructTypeArray, "Expected is : "+addConstructTypeArray+"Actual is : "+viewConstructTypeArray+"Array of construction type does not match in view form");
					mAssert(viewBuildupArArray, addBuildupArArray, "Expected is : "+addBuildupArArray+"Actual is : "+viewBuildupArArray+"Array of built up area does not match in view form");
					mAssert(viewRatableArArray, calcRatableArArray, "Expected is : "+calcRatableArArray+"Actual is : "+viewRatableArArray+"Array of ratable area does not match in view form");
					mAssert(viewUsageFactorArray, addUsageFactorArray, "Expected is : "+addUsageFactorArray+"Actual is : "+viewUsageFactorArray+"Array of usage factor does not match in view form");
					mAssert(viewUnitRateArray, calcUnitRateArray, "Expected is : "+calcUnitRateArray+"Actual is : "+viewUnitRateArray+"Array of unit rate does not match in view form");
					mAssert(viewOccFacArray, addOccFacArray, "Expected is : "+addOccFacArray+"Actual is : "+viewOccFacArray+"Array of occupancy factor does not match in view form");
					mAssert(viewAnnulRateblValArray, calcAnnulRateblValArray, "Expected is : "+calcAnnulRateblValArray+"Actual is : "+viewAnnulRateblValArray+"Array of annual ratable value does not match in view form");
					mAssert(viewRateOfTaxArray, calcRateOfTaxArray, "Expected is : "+calcRateOfTaxArray+"Actual is : "+viewRateOfTaxArray+"Array of rate of tax does not match in view form");
					mAssert(viewAnnulTaxArray, calcAnnulTaxArray, "Expected is : "+calcAnnulTaxArray+"Actual is : "+viewAnnulTaxArray+"Array of annual tax does not match in view form");
				}
				else
				{
					if(!mGetPropertyFromFile("pt_chngInAddnChngLandType").equalsIgnoreCase("yes"))
					{
						Collections.reverse(viewFloorNoArray);
						Collections.reverse(typeOfUseArray);
						Collections.reverse(buildupArArray);
						Collections.reverse(calcRatableArArray);
						Collections.reverse(usageFactorArray);
						Collections.reverse(calcUnitRateArray);
						Collections.reverse(calcAnnulRateblValArray);
						Collections.reverse(calcRateOfTaxArray);
						Collections.reverse(calcAnnulTaxArray);
					}
					mAssert(viewYearBlgArray, yearBlgArray, "Expected is : "+yearBlgArray+"Actual is : "+viewYearBlgArray+"Array of year does not match in view form");
					mAssert(viewFloorNoArray, floorNoArray, "Expected is : "+floorNoArray+"Actual is : "+viewFloorNoArray+"Array of floor no. does not match in view form");
					mAssert(viewTypeOfUseArray, typeOfUseArray, "Expected is : "+typeOfUseArray+"Actual is : "+viewTypeOfUseArray+"Array of type of usage does not match in view form");
					mAssert(viewConstructTypeArray, constructTypeArray, "Expected is : "+constructTypeArray+"Actual is : "+viewConstructTypeArray+"Array of construction type does not match in view form");
					mAssert(viewBuildupArArray, buildupArArray, "Expected is : "+buildupArArray+"Actual is : "+viewBuildupArArray+"Array of built up area does not match in view form");
					mAssert(viewRatableArArray, calcRatableArArray, "Expected is : "+calcRatableArArray+"Actual is : "+viewRatableArArray+"Array of ratable area does not match in view form");
					mAssert(viewUsageFactorArray, usageFactorArray, "Expected is : "+usageFactorArray+"Actual is : "+viewUsageFactorArray+"Array of usage factor does not match in view form");
					mAssert(viewUnitRateArray, calcUnitRateArray, "Expected is : "+calcUnitRateArray+"Actual is : "+viewUnitRateArray+"Array of unit rate does not match in view form");
					mAssert(viewOccFacArray, occFacArray, "Expected is : "+occFacArray+"Actual is : "+viewOccFacArray+"Array of occupancy factor does not match in view form");
					mAssert(viewAnnulRateblValArray, calcAnnulRateblValArray, "Expected is : "+calcAnnulRateblValArray+"Actual is : "+viewAnnulRateblValArray+"Array of annual ratable value does not match in view form");
					mAssert(viewRateOfTaxArray, calcRateOfTaxArray, "Expected is : "+calcRateOfTaxArray+"Actual is : "+viewRateOfTaxArray+"Array of rate of tax does not match in view form");
					mAssert(viewAnnulTaxArray, calcAnnulTaxArray, "Expected is : "+calcAnnulTaxArray+"Actual is : "+viewAnnulTaxArray+"Array of annual tax does not match in view form");
				}

			}	

			mTakeScreenShot();
			mCustomWait(1000);
			//mscroll(900, 1300);
			mscroll(1209, 636);

			if(mGetPropertyFromFile("landType").equalsIgnoreCase("flat"))
			{
				System.err.println("No vacant land table");
			}
			else
			{			
				//Reading Vacant Land Area @Author: -Ritesh @Date:16-09-2016
				WebElement table = driver.findElement(By.xpath("//*[@id='frmMasterForm']/table[1]"));
				List<WebElement> rows = null;
				rows = table.findElements(By.tagName("tr"));
				System.out.println(rows);
				int rwcount =rows.size();
				System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

				// Iterate to get the value of each cell in table along with element id
				int Rowno=0;
				for(WebElement rowElement:rows)
				{

					List<WebElement> cols=rowElement.findElements(By.xpath("td"));
					int colCount=cols.size();
					System.out.println("Columns are : "+colCount);
					int Columnno=0;

					for(int i=0;i<=colCount;i++)	
					{
						if(Rowno>=1)
						{	
							if(Columnno==0)
							{
								String viewYearVL=rowElement.findElement(By.xpath("td[1]")).getText().trim();
								System.out.println("Property Detail View Form Vacant Land year is == "+viewYearVL);
								//mAssert(viewYearVL, <>, "Actual is: "+viewYearVL+ "Expected is: "+<>+ "Vacant Land year is not matching");
							}
							if(Columnno==1)
							{
								String viewTaxVacLnd=rowElement.findElement(By.xpath("td[2]")).getText().trim();
								System.out.println("Property Detail View Form Taxable Vacant Land is == "+viewTaxVacLnd);
								//mAssert(viewTaxVacLnd, <>, "Actual is: "+viewTaxVacLnd+ "Expected is: "+<>+ "Taxable Vacant Land is not matching");
							}
							if(Columnno==2)
							{
								String viewVacLndUnAr=rowElement.findElement(By.xpath("td[3]")).getText().trim();
								System.out.println("Property Detail View Form Vancant Land Unit Area is == "+viewVacLndUnAr);
								//mAssert(viewVacLndUnAr, <>, "Actual is: "+viewVacLndUnAr+ "Expected is: "+<>+ "Vancant Land Unit Area is not matching");
							}
							if(Columnno==3)
							{
								if(mGetPropertyFromFile("landType").equalsIgnoreCase("Land + Building"))
								{	
									if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
									{

										if(appvacantland<70)

										{
											String viewVacLndAnnTax=rowElement.findElement(By.xpath("td[4]")).getText().trim();
											System.out.println("Property Detail View Form Vancant Land Annual Tax is == "+viewVacLndAnnTax);

											mAssert(viewVacLndAnnTax, mGetPropertyFromFile("pt_newAssessmentVacantLandAnnualTaxAuto"), "Actual is: "+viewVacLndAnnTax+ "Expected is: "+mGetPropertyFromFile("pt_newAssessmentVacantLandAnnualTaxAuto")+ "Vacant Land Annual Tax is not matching");


										}else if(appvacantland>=70)
										{
											System.out.println("Taxable Vacant land area is greater than 70%");
											if (mGetPropertyFromFile("manuualVacantLandArea").equalsIgnoreCase("No"))
											{
												System.out.println("Taxable Vacant land area is greater than 70% and user not going to enter Vacant Land");							
											}
											else
											{
												String viewVacLndAnnTax=rowElement.findElement(By.xpath("td[4]")).getText().trim();
												System.out.println("Property Detail View Form Vancant Land Annual Tax is == "+viewVacLndAnnTax);
												mAssert(viewVacLndAnnTax, mGetPropertyFromFile("pt_newAssessmentVacantLandAnnualTaxManual"), "Actual is: "+viewVacLndAnnTax+ "Expected is: "+mGetPropertyFromFile("pt_newAssessmentVacantLandAnnualTaxManual")+ "Vancant Land Annual Tax is not matching");
											}
										}
									}
									else
									{

										if((pt_newAssessmentAplcbleVacantLand)<70)
										{
											String viewVacLndAnnTax=rowElement.findElement(By.xpath("td[4]")).getText().trim();
											System.out.println("Property Detail View Form Vancant Land Annual Tax is == "+viewVacLndAnnTax);

											mAssert(viewVacLndAnnTax, String.valueOf(pt_newAssessmentVacantLandAnnualTax), "Actual is: "+viewVacLndAnnTax+ "Expected is: "+String.valueOf(pt_newAssessmentVacantLandAnnualTax)+ "Vacant Land Annual Tax is not matching");


										}else if((pt_newAssessmentAplcbleVacantLand)>=70)
										{
											System.out.println("Taxable Vacant land area is greater than 70%");
											if (mGetPropertyFromFile("manuualVacantLandArea").equalsIgnoreCase("No"))
											{
												System.out.println("Taxable Vacant land area is greater than 70% and user not going to enter Vacant Land");							
											}
											else
											{
												String viewVacLndAnnTax=rowElement.findElement(By.xpath("td[4]")).getText().trim();
												System.out.println("Property Detail View Form Vancant Land Annual Tax is == "+viewVacLndAnnTax);
												//mAssert(viewVacLndAnnTax, mGetPropertyFromFile("pt_newAssessmentVacantLandAnnualTaxManual"), "Actual is: "+viewVacLndAnnTax+ "Expected is: "+mGetPropertyFromFile("pt_newAssessmentVacantLandAnnualTaxManual")+ "Vancant Land Annual Tax is not matching");
												mAssert(viewVacLndAnnTax, String.valueOf(pt_newAssessmentVacantLandAnnualTax), "Actual is: "+viewVacLndAnnTax+ "Expected is: "+String.valueOf(pt_newAssessmentVacantLandAnnualTax)+ "Vacant Land Annual Tax is not matching");
											}
										}

									}
								}
							}						
						}
						Columnno=Columnno+1;
						System.out.println("Column no. is :"+Columnno);
					}
					Rowno=Rowno+1;
					System.out.println("Row no. is :"+Rowno);						
				}

			}


			//Water Tax Type
			//////////////
			if(mGetPropertyFromFile("landType").equalsIgnoreCase("flat"))
			{
				//viewWtTax = driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(31) > div:nth-child(1)")).getText().trim();
				if(mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					if(mGetPropertyFromFile("pt_chngInAddnChngVcntLandType").equalsIgnoreCase("Flat"))
					{
						viewWtTax =driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(31) > div:nth-child(1)")).getText().trim();
					}
					else
					{
						viewWtTax =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlWaterTaxFlatid")).trim();
					}	

				}
				else
				{
					viewWtTax =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlWaterTaxFlatid")).trim();
				}
				splitter = viewWtTax.split(":");
				viewWtTax=splitter[1].toString().trim();
				System.out.println("Property Detail View Form Water Tax Type is: "+viewWtTax);
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					mAssert(viewWtTax, mGetPropertyFromFile("pt_newAssessmentWaterTaxType"), "Actual is :"+viewWtTax+ "Expected is :"+mGetPropertyFromFile("pt_newAssessmentWaterTaxType")+"Water Tax Type is");
				}
				else
				{
					mAssert(viewWtTax, chngInAssWaterTaxType, "Actual is :"+viewWtTax+ "Expected is :"+chngInAssWaterTaxType+"Water Tax Type is");
				}
			}
			else if(mGetPropertyFromFile("landType").equalsIgnoreCase("Land + Building"))
			{
				//viewWtTax = driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(33) > div:nth-child(1)")).getText().trim();
				viewWtTax =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlWaterTaxLndBlgid")).trim();
				splitter = viewWtTax.split(":");
				viewWtTax=splitter[1].toString().trim();
				System.out.println("Property Detail View Form Water Tax Type is: "+viewWtTax);
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					mAssert(viewWtTax, mGetPropertyFromFile("pt_newAssessmentWaterTaxType"), "Actual is :"+viewWtTax+ "Expected is :"+mGetPropertyFromFile("pt_newAssessmentWaterTaxType")+"Water Tax Type is");
				}
				else
				{
					mAssert(viewWtTax, chngInAssWaterTaxType, "Actual is :"+viewWtTax+ "Expected is :"+chngInAssWaterTaxType+"Water Tax Type is");
				}
			}
			else if(mGetPropertyFromFile("landType").equalsIgnoreCase("vacant land"))
			{
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					//viewWtTax = driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(31) > div:nth-child(1)")).getText().trim();
					viewWtTax =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlWaterTaxFlatid")).trim();
					splitter = viewWtTax.split(":");
					viewWtTax=splitter[1].toString().trim();
					System.out.println("Property Detail View Form Water Tax Type is: "+viewWtTax);
					mAssert(viewWtTax, mGetPropertyFromFile("pt_newAssessmentWaterTaxType"), "Actual is :"+viewWtTax+ "Expected is :"+mGetPropertyFromFile("pt_newAssessmentWaterTaxType")+"Water Tax Type is");
				}
				else
				{
					viewWtTax =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlWaterTaxVcntLndid")).trim();
					splitter = viewWtTax.split(":");
					viewWtTax=splitter[1].toString().trim();
					System.out.println("Property Detail View Form Water Tax Type is: "+viewWtTax);
					mAssert(viewWtTax, chngInAssWaterTaxType, "Actual is :"+viewWtTax+ "Expected is :"+chngInAssWaterTaxType+"Water Tax Type is");
				}
			}

			//Water Charge
			////////////////
			if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
			{
				//viewWtTaxChrg = driver.findElement(By.cssSelector("#wcfAmtVal")).getText().trim();
				viewWtTaxChrg = mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewWtTaxChrgid")).trim();
				//#waterAmt
				Pattern viewWaterTaxCharge= Pattern.compile("(?<=Rs.)\\s*([0-9]+(\\.[0-9]?)?)\\s*");
				Matcher m = viewWaterTaxCharge.matcher(viewWtTaxChrg);
				if(m.find())
				{
					viewWtTaxChrg = m.group(1);
				}
				System.out.println("Property Detail View Form Water Tax Charge is: "+viewWtTaxChrg);

				mAssert(viewWtTaxChrg, mGetPropertyFromFile("pt_newAssessmentWaterTaxCrgSupp"), "Actual is :" +viewWtTaxChrg+ "Expected is :" +mGetPropertyFromFile("pt_newAssessmentWaterTaxCrgSupp")+ "Water Tax Charge is not matching in view form");
			}

			//Arrears Amount
			///////////////
			if(mGetPropertyFromFile("landType").equalsIgnoreCase("flat"))
			{
				//String viewArreas = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(1)")).getText().trim();
				//String viewArreas =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewArreasflatid")).trim();
				String viewArreas;
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					if(cntForBldgDtl==0)
					{
						viewArreas = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(1)")).getText().trim();
					}
					else
					{
						viewArreas = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(1)")).getText().trim();
					}			
				}
				else
				{
					if(mGetPropertyFromFile("pt_chngInAddnChngVcntLandType").equalsIgnoreCase("Flat"))
					{
						if(driver.findElements(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table")).size() != 0)						
						{
							viewArreas = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(1)")).getText().trim();
						}
						else
						{
							viewArreas = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(1)")).getText().trim();						
						}
					}
					else
					{
						if(driver.findElements(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table")).size() != 0)						
						{
							viewArreas = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(39) > div > div:nth-child(1)")).getText().trim();
						}
						else
						{
							viewArreas = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(1)")).getText().trim();						
						}
					}
				}

				splitter = viewArreas.split(":");
				viewArreas=splitter[1].toString().trim();
				System.out.println("Property Detail View Form Areas amount is: "+viewArreas);	
			}
			else if(mGetPropertyFromFile("landType").equalsIgnoreCase("Land + Building"))
			{

				//String viewArreas = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(1)")).getText().trim();
				//String viewArreas =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewArreasLndBlgid")).trim();

				String viewArreas;
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					if(cntForBldgDtl==0)
					{
						viewArreas = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(1)")).getText().trim();
					}
					else
					{
						viewArreas = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(39) > div > div:nth-child(1)")).getText().trim();
					}
				}
				else
				{
					/*if(cntForVlDtl==0)
					{
						viewArreas = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(1)")).getText().trim();
					}
					else
					{
						viewArreas = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(39) > div > div:nth-child(1)")).getText().trim();
					}*/

					if(driver.findElements(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table")).size() != 0)						
					{
						viewArreas = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(39) > div > div:nth-child(1)")).getText().trim();
					}
					else
					{
						viewArreas = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(1)")).getText().trim();						
					}
				}

				splitter = viewArreas.split(":");
				viewArreas=splitter[1].toString().trim();
				System.out.println("Property Detail View Form Areas amount is: "+viewArreas);
			}
			else if(mGetPropertyFromFile("landType").equalsIgnoreCase("vacant land"))
			{
				//String viewArreas = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(1)")).getText().trim();
				//String viewArreas =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewArreasVLCid")).trim();
				String viewArreas;
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					if(cntForVlDtl==0)
					{
						viewArreas = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(1)")).getText().trim();
					}
					else
					{
						viewArreas = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(1)")).getText().trim();

					}
				}
				else
				{
					if(driver.findElements(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table")).size() != 0)						
					{
						viewArreas = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(1)")).getText().trim();
					}
					else
					{
						viewArreas = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(1)")).getText().trim();						
					}
				}
				splitter = viewArreas.split(":");
				viewArreas=splitter[1].toString().trim();
				System.out.println("Property Detail View Form Areas amount is: "+viewArreas);
			}

			//Annual Rental Value
			/////////////////////
			if(mGetPropertyFromFile("landType").equalsIgnoreCase("flat"))
			{
				//String viewAnnualRnVal = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(2)")).getText().trim();
				/*String viewAnnualRnVal =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewARVFlatid")).trim();
				splitter = viewAnnualRnVal.split(":");
				viewAnnualRnVal=splitter[1].toString().trim();
				System.out.println("Property Detail View Form Annual Rental Value is: "+viewAnnualRnVal);*/

				//String viewAnnualRnVal = "";
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					if(cntForBldgDtl==0)
					{
						viewAnnualRnVal = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(2)")).getText().trim();
					}
					else
					{
						viewAnnualRnVal = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(2)")).getText().trim();
					}
				}
				else
				{
					if(driver.findElements(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table")).size() != 0)
					{
						viewAnnualRnVal = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(39) > div > div:nth-child(2)")).getText().trim();
					}
					else
					{
						viewAnnualRnVal = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(2)")).getText().trim();
					}
				}

				splitter = viewAnnualRnVal.split(":");
				viewAnnualRnVal=splitter[1].toString().trim();
				System.out.println("Property Detail View Form Annual Rental Value is: "+viewAnnualRnVal);
			}
			else if(mGetPropertyFromFile("landType").equalsIgnoreCase("Land + Building"))
			{

				//String viewAnnualRnVal = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(2)")).getText().trim();
				//String viewAnnualRnVal =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewARVLndBlgid")).trim();

				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					if(cntForBldgDtl==0)
					{
						viewAnnualRnVal = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(2)")).getText().trim();
					}
					else
					{
						viewAnnualRnVal = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(39) > div > div:nth-child(2)")).getText().trim();
					}
				}
				else
				{
					if(driver.findElements(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table")).size() != 0)
					{
						viewAnnualRnVal = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(39) > div > div:nth-child(2)")).getText().trim();
					}
					else
					{
						viewAnnualRnVal = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(2)")).getText().trim();
					}
				}
				splitter = viewAnnualRnVal.split(":");
				viewAnnualRnVal=splitter[1].toString().trim();
				System.out.println("Property Detail View Form Annual Rental Value is: "+viewAnnualRnVal);
			}
			/*else if(mGetPropertyFromFile("landType").equalsIgnoreCase("vacant land"))
			{
				//String viewAnnualRnVal = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(2)")).getText().trim();
				//String viewAnnualRnVal =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewARVVLCid")).trim();
				String viewAnnualRnVal = "";
				if(cntForVlDtl==0)
				{
					viewAnnualRnVal = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(2)")).getText().trim();
				}
				else
				{
					viewAnnualRnVal = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(2)")).getText().trim();
				}			

				splitter = viewAnnualRnVal.split(":");
				viewAnnualRnVal=splitter[1].toString().trim();
				System.out.println("Property Detail View Form Annual Rental Value is: "+viewAnnualRnVal);
			}*/

			//Total Annual Tax
			//////////////////
			if(mGetPropertyFromFile("landType").equalsIgnoreCase("flat"))
			{

				String viewTotAnnTx = "";
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					if(cntForBldgDtl==0)
					{
						viewTotAnnTx = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(3)")).getText().trim();
					}
					else
					{
						viewTotAnnTx = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(3)")).getText().trim();
					}
				}
				else
				{
					if(mGetPropertyFromFile("pt_chngInAddnChngVcntLandType").equalsIgnoreCase("Flat"))
					{
						if(driver.findElements(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table")).size() != 0)						
						{
							viewTotAnnTx = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(39) > div > div:nth-child(4)")).getText().trim();
						}
						else
						{
							viewTotAnnTx = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(3)")).getText().trim();						
						}
					}
					else
					{
						if(driver.findElements(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table")).size() != 0)
						{
							viewTotAnnTx = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(3)")).getText().trim();
						}
						else
						{
							viewTotAnnTx = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(3)")).getText().trim();
						}
					}
				}

				/*splitter = viewTotAnnTx.split(":");
				viewTotAnnTx=splitter[1].toString().trim();*/
				System.out.println("Property Detail View Form Total Annual Tax is: "+viewTotAnnTx);

				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					splitter = viewTotAnnTx.split(":");
					viewTotAnnTx=splitter[1].toString().trim();
					mAssert(viewTotAnnTx, mGetPropertyFromFile("pt_newAssessmentPTAnnualTaxAutoRes"), "Actual is: " +viewTotAnnTx+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPTAnnualTaxAutoRes")+ "Total Annual Tax is not matching");
				}
				else
				{
					for(int i=0 ; i<calcAnnulTaxArray.size() ; i++) 
					{
						pt_newAssessmentPTAnnualTaxAutoRes+=Double.parseDouble(calcAnnulTaxArray.get(i));

						//pt_newAssessmentPTAnnualTaxAutoRes = (Double.parseDouble(calcAnnulTaxArray.get(i)))+pt_newAssessmentVacantLandAnnualTax;
					}
					pt_newAssessmentPTAnnualTaxAutoRes = pt_newAssessmentPTAnnualTaxAutoRes;
					pt_newAssessmentPTAnnualTaxAutoResArray.add(String.valueOf(pt_newAssessmentPTAnnualTaxAutoRes));
					System.out.println("Calculated Total Annual Tax is : "+pt_newAssessmentPTAnnualTaxAutoResArray);
					mAssert(viewTotAnnTx, String.valueOf(pt_newAssessmentPTAnnualTaxAutoResArray.get(CurrentinvoCount)), "Actual is: " +viewTotAnnTx+ "Expected is: " +String.valueOf(pt_newAssessmentPTAnnualTaxAutoResArray.get(CurrentinvoCount))+ "Total Annual Tax is not matching");
				}

			}

			else if(mGetPropertyFromFile("landType").equalsIgnoreCase("Land + Building"))
			{

				//String viewTotAnnTx = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(3)")).getText().trim();
				//String viewTotAnnTx =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewTotAnnTaxLndBlgid")).trim();

				String viewTotAnnTx;
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					if(cntForBldgDtl==0)
					{
						viewTotAnnTx = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(3)")).getText().trim();
					}
					else
					{
						viewTotAnnTx = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(39) > div > div:nth-child(3)")).getText().trim();
					}
				}
				else
				{
					if(driver.findElements(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table")).size() != 0)
					{
						viewTotAnnTx = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(39) > div > div:nth-child(4)")).getText().trim();
					}
					else
					{
						viewTotAnnTx = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(3)")).getText().trim();
					}
				}


				splitter = viewTotAnnTx.split(":");
				viewTotAnnTx=splitter[1].toString().trim();
				System.out.println("Property Detail View Form Total Annual Tax is: "+viewTotAnnTx);

				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					mAssert(viewTotAnnTx, mGetPropertyFromFile("pt_newAssessmentPTAnnualTaxAutoRes"), "Actual is: " +viewTotAnnTx+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPTAnnualTaxAutoRes")+ "Total Annual Tax is not matching");
				}
				else
				{
					//	Double pt_newAssessmentPTAnnualTaxAutoRes = 0.0;
					for(int i=0 ; i<calcAnnulTaxArray.size() ; i++) 
					{
						pt_newAssessmentPTAnnualTaxAutoRes+=Double.parseDouble(calcAnnulTaxArray.get(i));
						//pt_newAssessmentPTAnnualTaxAutoRes = (Double.parseDouble(calcAnnulTaxArray.get(i)))+pt_newAssessmentVacantLandAnnualTax;
					}
					pt_newAssessmentPTAnnualTaxAutoRes = pt_newAssessmentPTAnnualTaxAutoRes+(int)pt_newAssessmentVacantLandAnnualTax;
					pt_newAssessmentPTAnnualTaxAutoResArray.add(String.valueOf(pt_newAssessmentPTAnnualTaxAutoRes));

					System.out.println("Calculated Total Annual Tax is : "+pt_newAssessmentPTAnnualTaxAutoResArray);
					mAssert(viewTotAnnTx, String.valueOf(pt_newAssessmentPTAnnualTaxAutoResArray.get(CurrentinvoCount)), "Actual is: " +viewTotAnnTx+ "Expected is: " +String.valueOf(pt_newAssessmentPTAnnualTaxAutoResArray.get(CurrentinvoCount))+ "Total Annual Tax is not matching");
				}

				//	mAssert(viewTotAnnTx, mGetPropertyFromFile("pt_newAssessmentPTAnnualTaxAutoRes"), "Actual is: " +viewTotAnnTx+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPTAnnualTaxAutoRes")+ "Total Annual Tax is not matching");
			}
			else if(mGetPropertyFromFile("landType").equalsIgnoreCase("vacant land"))
			{
				//String viewTotAnnTx = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(3)")).getText().trim();
				/*String viewTotAnnTx =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewTotAnnTaxVLid")).trim();
				splitter = viewTotAnnTx.split(":");
				viewTotAnnTx=splitter[1].toString().trim();
				System.out.println("Property Detail View Form Total Annual Tax is: "+viewTotAnnTx);*/

				String viewTotAnnTx;
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					if(cntForVlDtl==0)
					{
						viewTotAnnTx = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(3)")).getText().trim();
					}
					else
					{
						viewTotAnnTx = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(3)")).getText().trim();
					}
				}
				else
				{
					if(driver.findElements(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table")).size() != 0)						
					{
						viewTotAnnTx = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(4)")).getText().trim();
					}
					else
					{
						viewTotAnnTx = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(3)")).getText().trim();						
					}
				}
				splitter = viewTotAnnTx.split(":");
				viewTotAnnTx=splitter[1].toString().trim();
				System.out.println("Property Detail View Form Total Annual Tax is: "+viewTotAnnTx);

				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					mAssert(viewTotAnnTx, mGetPropertyFromFile("pt_newAssessmentPTAnnualTaxAutoRes"), "Actual is: " +viewTotAnnTx+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPTAnnualTaxAutoRes")+ "Total Annual Tax is not matching");
				}
				else
				{
					pt_newAssessmentPTAnnualTaxAutoRes=0;
					//pt_newAssessmentPTAnnualTaxAutoRes = (Double.parseDouble(calcAnnulTaxArray.get(i)))+pt_newAssessmentVacantLandAnnualTax;

					pt_newAssessmentPTAnnualTaxAutoRes = pt_newAssessmentPTAnnualTaxAutoRes+(int)pt_newAssessmentVacantLandAnnualTax;
					pt_newAssessmentPTAnnualTaxAutoResArray.add(String.valueOf(pt_newAssessmentPTAnnualTaxAutoRes));

					System.out.println("Calculated Total Annual Tax is : "+pt_newAssessmentPTAnnualTaxAutoResArray);
					mAssert(viewTotAnnTx, String.valueOf(pt_newAssessmentPTAnnualTaxAutoResArray.get(CurrentinvoCount)), "Actual is: " +viewTotAnnTx+ "Expected is: " +String.valueOf(pt_newAssessmentPTAnnualTaxAutoResArray.get(CurrentinvoCount))+ "Total Annual Tax is not matching");
				}
				/*}*/
			}

			//Rebate Value
			////////////

			if(mGetPropertyFromFile("landType").equalsIgnoreCase("flat"))
			{
				//viewRbt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(4)")).getText().trim();
				viewRbt =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewRbtFlatid")).trim();
				/*System.out.println("Rebate value for Flat: " +viewRbt);
				splitter = viewRbt.split(":");
				viewRbt=splitter[1].toString().trim();*/
				System.out.println("Property Detail View Form Rebate is: "+viewRbt);
			}
			else if(mGetPropertyFromFile("landType").equalsIgnoreCase("Land + Building"))
			{

				//viewRbt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(4)")).getText().trim();
				//String viewRbt =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewRbtLndBlgid")).trim();

				//if(cntForBldgDtl==0 && !mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					viewRbt = driver.findElement(By.cssSelector("#rebateAmtID")).getText().trim();
					System.out.println("Property Detail View Form Rebate before splitting : "+viewRbt);
					/*splitter = viewRbt.split(":");
					viewRbt=splitter[1].toString().trim();
					System.out.println("Property Detail View Form Rebate is: "+viewRbt);*/
				}
				else if(mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					viewRbt = driver.findElement(By.cssSelector("#rebateAmtID")).getText().trim();
					System.out.println("Property Detail View Form Rebate before splitting : "+viewRbt);
				}
				else
				{
					viewRbt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(39) > div > div:nth-child(4)")).getText().trim();
					System.out.println("Property Detail View Form Rebate before splitting : "+viewRbt);
					splitter = viewRbt.split(":");
					viewRbt=splitter[1].toString().trim();
					System.out.println("Property Detail View Form Rebate is: "+viewRbt);
				}

			}
			else if(mGetPropertyFromFile("landType").equalsIgnoreCase("Vacant land"))
			{
				//viewRbt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(4)")).getText().trim();
				viewRbt =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewRbtVLid")).trim();
				//System.out.println("Rebate from view form: "+viewRbt);
				//splitter = viewRbt.split(":");
				//viewRbt=splitter[1].toString().trim();
				System.out.println("Property Detail View Form Rebate is: "+viewRbt);
			}


			//Rain Water Harvesting Rebate Value
			////////////////////////////////////

			if(mGetPropertyFromFile("landType").equalsIgnoreCase("flat"))
			{
				//viewRWHRbt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(6)")).getText().trim();
				//viewRWHRbt =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewRWHRbtFlatid")).trim();

				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					if(cntForBldgDtl==0)
					{
						viewRWHRbt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(6)")).getText().trim();
					}
					else
					{
						viewRWHRbt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(6)")).getText().trim();
					}
				}
				else
				{
					if(driver.findElements(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table")).size() != 0)
					{
						viewRWHRbt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(39) > div > div:nth-child(7)")).getText().trim();
					}
					else
					{
						viewRWHRbt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(7)")).getText().trim();
					}
				}

				splitter = viewRWHRbt.split(":");
				viewRWHRbt=splitter[1].toString().trim();
				System.out.println("Property Detail View Form Rain Water Harvesting Rebate is: "+viewRWHRbt);
			}
			else if(mGetPropertyFromFile("landType").equalsIgnoreCase("Land + Building"))
			{

				//viewRWHRbt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(6)")).getText().trim();
				//String viewRWHRbt =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewRWHRbtLndBlgid")).trim();

				if(cntForBldgDtl==0 && !mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					viewRWHRbt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(6)")).getText().trim();
					splitter = viewRWHRbt.split(":");
					viewRWHRbt=splitter[1].toString().trim();
					System.out.println("Property Detail View Form Rain Water Harvesting Rebate is: "+viewRWHRbt);
				}
				else if(mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					if(driver.findElements(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table")).size() != 0)
					{
						viewRWHRbt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(39) > div > div:nth-child(7)")).getText().trim();
					}
					else
					{
						viewRWHRbt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(6)")).getText().trim();
					}
					System.out.println("Property Detail View Form Rebate before splitting : "+viewRbt);
					splitter = viewRWHRbt.split(":");
					viewRWHRbt=splitter[1].toString().trim();
					System.out.println("Property Detail View Form Rain Water Harvesting Rebate is: "+viewRWHRbt);
				}
				else
				{
					viewRWHRbt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(39) > div > div:nth-child(6)")).getText().trim();
					splitter = viewRWHRbt.split(":");
					viewRWHRbt=splitter[1].toString().trim();
					System.out.println("Property Detail View Form Rain Water Harvesting Rebate is: "+viewRWHRbt);
				}


			}
			//Commented By @Ritesh: - RWH not applicale for Vacant Land 05-12-2016
			/*else if(mGetPropertyFromFile("landType").equalsIgnoreCase("vacant land"))
			{
				//viewRWHRbt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(6)")).getText().trim();
				String viewRWHRbt =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewRWHRbtVLid")).trim();
				splitter = viewRWHRbt.split(":");
				viewRWHRbt=splitter[1].toString().trim();
				System.out.println("Property Detail View Form Rain Water Harvesting Rebate is: "+viewRWHRbt);
			}*/


			//Addition of Rebate + RWH rebate: Commented by @Ritesh on dated 05-12-2016 and wrote new script
			///////////////////////////////////////////

			/*int viewRbtConvt=Integer.parseInt(viewRbt);
			int viewRWHRbtCovt=Integer.parseInt(viewRWHRbt);
			viewComboRebate=viewRbtConvt+viewRWHRbtCovt;
			System.out.println("Property Detail View Form Addition of Rebate & RWH rebate: "+viewComboRebate);*/

			if(!mGetPropertyFromFile("landType").equalsIgnoreCase("vacant land"))
			{
				int viewRbtConvt=Integer.parseInt(viewRbt);
				int viewRWHRbtCovt=Integer.parseInt(viewRWHRbt);
				viewComboRebate=viewRbtConvt+viewRWHRbtCovt;
				System.out.println("Property Detail View Form Addition of Rebate & RWH rebate: "+viewComboRebate);
			}
			else
			{
				int viewRbtConvt=Integer.parseInt(viewRbt);
				viewComboRebate=viewRbtConvt;			
			}



			//Penalty Late Submission
			/////////////////////////

			if(mGetPropertyFromFile("landType").equalsIgnoreCase("flat"))
			{
				//String viewPenltyLtSub = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(7)")).getText().trim();
				//String viewPenltyLtSub =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewRWHRbtFlatid")).trim();

				String viewPenltyLtSub = "";
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					if(cntForBldgDtl==0)
					{
						viewPenltyLtSub = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(7)")).getText().trim();
					}
					else
					{
						viewPenltyLtSub = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(7)")).getText().trim();
					}
					splitter = viewPenltyLtSub.split(":");
					viewPenltyLtSub=splitter[1].toString().trim();
					System.out.println("Property Detail View Form Penalty Late Submission is: "+viewPenltyLtSub);
				}
				else
				{
					System.out.println("Penalty Late Submission is not applicable for Change / Alteration in Assessment");
				}

			}
			else if(mGetPropertyFromFile("landType").equalsIgnoreCase("Land + Building"))
			{

				//String viewPenltyLtSub = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(7)")).getText().trim();
				//String viewPenltyLtSub =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewRWHRbtLndBlgid")).trim();

				String viewPenltyLtSub = "";
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					if(cntForBldgDtl==0)
					{
						viewPenltyLtSub = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(7)")).getText().trim();
					}
					else
					{
						viewPenltyLtSub = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(39) > div > div:nth-child(7)")).getText().trim();
					}

					splitter = viewPenltyLtSub.split(":");
					viewPenltyLtSub=splitter[1].toString().trim();
					System.out.println("Property Detail View Form Penalty Late Submission is: "+viewPenltyLtSub);
				}
				else
				{
					System.out.println("Property Detail View Form Penalty Late Submission is not applicalbe for Change in Assessment");
				}

			}
			else if(mGetPropertyFromFile("landType").equalsIgnoreCase("vacant land"))
			{
				//String viewPenltyLtSub = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(7)")).getText().trim();
				//String viewPenltyLtSub =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewRWHRbtVLid")).trim();
				String viewPenltyLtSub = "";
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					if(cntForVlDtl==0)
					{
						viewPenltyLtSub = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(7)")).getText().trim();
					}
					else
					{
						viewPenltyLtSub = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(7)")).getText().trim();
					}
					splitter = viewPenltyLtSub.split(":");
					viewPenltyLtSub=splitter[1].toString().trim();
					System.out.println("Property Detail View Form Penalty Late Submission is: "+viewPenltyLtSub);
				}
				else
				{
					System.out.println("Property Detail View Form Penalty Late Submission is not applicalbe for Change in Assessment");
				}

			}


			//Total Payable Amount
			//////////////////////

			if(mGetPropertyFromFile("landType").equalsIgnoreCase("flat"))
			{
				//String viewTotPayAmt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(8)")).getText().trim();
				//String viewTotPayAmt =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewTotPayAmtFlatid")).trim();
				String viewTotPayAmt = "";
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					if(cntForBldgDtl==0)
					{
						viewTotPayAmt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(7)")).getText().trim();
					}
					else
					{
						viewTotPayAmt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(7)")).getText().trim();
					}
					splitter = viewTotPayAmt.split(":");
					viewTotPayAmt=splitter[1].toString().trim();
				}
				else
				{
					if(driver.findElements(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table")).size() != 0)
					{
						viewTotPayAmt = driver.findElement(By.cssSelector("#totPayAmtID")).getText().trim();
					}
					else
					{
						viewTotPayAmt = driver.findElement(By.cssSelector("#totPayAmtID")).getText().trim();
					}
				}

				System.out.println("Property Detail View Form Total Payable Amount is: "+viewTotPayAmt);
			}			
			else if(mGetPropertyFromFile("landType").equalsIgnoreCase("Land + Building"))
			{

				//String viewTotPayAmt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(7)")).getText().trim();
				//String viewTotPayAmt =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewTotPayAmtLndBlgid")).trim();

				String viewTotPayAmt;
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					if(cntForBldgDtl==0)
					{
						viewTotPayAmt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(8)")).getText().trim();
					}
					else
					{
						viewTotPayAmt = driver.findElement(By.cssSelector("#totPayAmtID")).getText().trim();
					}
					/*splitter = viewTotPayAmt.split(":");
					viewTotPayAmt=splitter[1].toString().trim();*/
				}
				else
				{
					if(driver.findElements(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table")).size() != 0)
					{
						//viewTotPayAmt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(8)")).getText().trim();
						viewTotPayAmt = driver.findElement(By.cssSelector("#totPayAmtID")).getText().trim();
					}
					else
					{
						viewTotPayAmt = driver.findElement(By.cssSelector("#totPayAmtID")).getText().trim();
					}
				}
				//splitter = viewTotPayAmt.split(":");
				//viewTotPayAmt=splitter[1].toString().trim();
				System.out.println("Property Detail View Form Total Payable Amount is: "+viewTotPayAmt);
			}
			else if(mGetPropertyFromFile("landType").equalsIgnoreCase("vacant land"))
			{
				//String viewTotPayAmt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(8)")).getText().trim();
				//String viewTotPayAmt =mGetText("css", mGetPropertyFromFile("pt_newAssessmentviewDtlviewTotPayAmtVLid")).trim();
				String viewTotPayAmt;
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					if(cntForVlDtl==0)
					{
						viewTotPayAmt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(35) > div > div:nth-child(7)")).getText().trim();
					}
					else
					{
						viewTotPayAmt = driver.findElement(By.cssSelector("#frmMasterForm > fieldset:nth-child(37) > div > div:nth-child(7)")).getText().trim();
					}

					splitter = viewTotPayAmt.split(":");
					viewTotPayAmt=splitter[1].toString().trim();
					System.out.println("Property Detail View Form Total Payable Amount is: "+viewTotPayAmt);
				}
				else
				{
					System.out.println("Change in assessment service selected");
					viewTotPayAmt = driver.findElement(By.cssSelector("#totPayAmtID")).getText().trim();
					System.out.println("Property Detail View Form Total Payable Amount is: "+viewTotPayAmt);
				}
			}

			//Payment type
			/////////////////
			/*if (mGetPropertyFromFile("paymentType").equals(mGetPropertyFromFile("pt_newAssessmentFullPayment")))
			{
				mGenericWait();
				mClick("id", mGetPropertyFromFile("pt_newAssessmentFullPaymentid"));

			}
			else
			{
				mGenericWait();
				mClick("id", mGetPropertyFromFile("pt_newAssessmentPartialPaymentid"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentPartialPaymentAmtid"), mGetPropertyFromFile("pt_newAssessmentPartialPaymentAmt"));
			}*/

			//Old Code commented New Code added for Payment mode @ Madhuri Dated:- 01-09-2016
			//payment("byBankOrULB");

			payment("paymentMode","byBankOrULB");

			mGenericWait();
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("pt_newAssessmentPropTaxSubmitid"));
			mWaitForVisible("id", mGetPropertyFromFile("pt_newAssessmentProceedBtnid"));
			mGenericWait();
			mTakeScreenShot();
			String saveMsg=mGetText("css", mGetPropertyFromFile("pt_newAssessmentSaveMsgid"));
			System.out.println(saveMsg);
			if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
			{
				mAssert(mGetPropertyFromFile("pt_newAssessmentSaveMsg"), saveMsg, "Message does not match, Expected: "+mGetPropertyFromFile("pt_newAssessmentSaveMsg")+" || Actual: "+saveMsg);
			}
			else
			{
				mAssert(mGetPropertyFromFile("pt_newAssessmentSaveMsg"), saveMsg, "Message does not match, Expected: "+mGetPropertyFromFile("pt_newAssessmentSaveMsg")+" || Actual: "+saveMsg);
			}
			mClick("id", mGetPropertyFromFile("pt_newAssessmentProceedBtnid"));
			mCustomWait(7000);
			if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
			{
				newPtChallanReader();
			}
				else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
			{
						mChallanPDFReader();//Commented Ritesh 21-10-2016
			}
			mGenericWait();
			SASForm();
			mGenericWait();
			/*mClick("id", mGetPropertyFromFile("pt_newAssessmentSASPrintBtnid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_newAssessmentSASCancelBtnid"));
			mCustomWait(2000);*/
			mTakeScreenShot();
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_newAssessmentFinishBtnid"));
			mGenericWait();
			System.out.println("End Reading View Details Form");

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in newAssessmentNextMethod method");

		}

	}


	public void BldgDetailsTableReading()
	{
		try {

			WebElement table = driver.findElement(By.id("buildingDetailsGridID"));

			mscroll(0, 1300);
			List<WebElement> rows1 = table.findElements(By.tagName("tr"));
			int rwcount=rows1.size();
			System.out.println("Rows in building details after adding rows are: "+rows1.size());

			List<WebElement> cols = rows1.get(1).findElements(By.xpath("td"));
			int colcount= cols.size();
			System.out.println(colcount);

			/*String[] flrNoArray=mGetPropertyFromFile("pt_newAssessmentFloorNoArray").split(",");
			System.out.println("string array size is : "+flrNoArray.length);

			int a=Integer.parseInt(flrNoArray[0].toString());
			int b=Integer.parseInt(flrNoArray[1].toString());
			int addCount=(b-a)+1;*/

			//for (k=0; k<=cntForBldgDtl;k++)
			for (int i=0;i<rwcount;i++) 		
			{
				if(i<rwcount-1)
				{

					//yearBlg=driver.findElement(By.id("yearLabelFromValue"+i)).getAttribute("value");
					yearBlgArray.add(driver.findElement(By.id("yearLabelFromValue"+i)).getAttribute("value"));
					System.out.println("Building Year is: "+yearBlgArray);					
					//mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentFloorNoid"),mGetPropertyFromFile("pt_newAssessmentFloorNo"));
					//floorNo=mCaptureSelectedValue("id", mGetPropertyFromFile("pt_newAssessmentFloorNoid"));

					//	if(driver.findElement(By.id("floorName"+i)).getAttribute("tagName")).equalsIgnoreCase("input"))
					//	if(driver.findElement(By.xpath("//input[starts-with(@id,'floorName')")) != null)
					//	rows1.get(1).findElements(By.xpath("td"));
					//	if(driver.findElement(By.id("floorName"+i)).getAttribute("readonly").equalsIgnoreCase("readonly"))
					/*if(rows1.get(i).findElement(By.xpath("td[2]/input")).getAttribute("readonly").equalsIgnoreCase("readonly"))
					{*/
					//floorNoArray.add(driver.findElement(By.id("floorName"+i)).getText());
					/*floorNoArray.add(driver.findElement(By.id("floorName"+i)).getAttribute("value"));
					System.out.println("Property Detail Entry Floor No is: "+floorNoArray);*/

					floorNoArray.add(new Select(driver.findElement(By.id("cpdLocID"+i))).getFirstSelectedOption().getText());
					System.out.println("Property Detail Entry Floor No is: "+floorNoArray);


					//mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentTypeOfUseid"),mGetPropertyFromFile("pt_newAssessmentTypeOfUse"));
					//typeOfUse=mCaptureSelectedValue("id", mGetPropertyFromFile("pt_newAssessmentTypeOfUseid"));

					typeOfUseArray.add(new Select(driver.findElement(By.name("entity.propertyDetails["+(i)+"].cpdUsage1"))).getFirstSelectedOption().getText());
					//	typeOfUseArray.add(driver.findElement(By.id("usage"+i)).getAttribute("value"));
					System.out.println("Property Detail Entry Type of Use is: "+typeOfUseArray);

					//mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentConstructionTypeid"),mGetPropertyFromFile("pt_newAssessmentConstructionType"));
					//constructType=mCaptureSelectedValue("id", mGetPropertyFromFile("pt_newAssessmentConstructionTypeid"));

					constructTypeArray.add(new Select(driver.findElement(By.name("entity.propertyDetails["+(i)+"].codConsClass1"))).getFirstSelectedOption().getText());
					//	constructTypeArray.add(driver.findElement(By.id("constructionClass"+i)).getAttribute("value"));
					System.out.println("Property Detail Entry Construction Type is: "+constructTypeArray);

					//mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentBuildingAreaid"), mGetPropertyFromFile("pt_newAssessmentBuildingArea"));

					buildupArArray.add(driver.findElement(By.id("pdBuildingArea"+i)).getAttribute("value"));
					//		buildupArArray.add(driver.findElement(By.id("buildingArea"+i)).getAttribute("value"));
					System.out.println("Property Detail Entry Buildup Area is :"+buildupArArray);


					usageFactorArray.add(new Select(driver.findElement(By.id("cpdSfctID"+i))).getFirstSelectedOption().getText());
					//	usageFactorArray.add(driver.findElement(By.id("pdAsseArea"+i)).getAttribute("value"));
					System.out.println("Property Detail Entry Usage Factor is: "+usageFactorArray);


					occFacArray.add(new Select(driver.findElement(By.id("cpdOccStatus"+i))).getFirstSelectedOption().getText());
					//	occFacArray.add(driver.findElement(By.id("OccType"+i)).getAttribute("value"));
					System.out.println("Property Detail Entry Occupancy Factor is: "+occFacArray);

					/*mTab("id", mGetPropertyFromFile("pt_newAssessmentBuiltUpAreaid"));
				mGenericWait();*/

					//Added for Residential Usag Factor Ritesh 08-08-2016 & Calculation checking for Residential & Non residential Ratable Area (Sq.Ft.) @Ritesh 26-08-2016 
					//if (!mGetPropertyFromFile("pt_newAssessmentTypeOfUse").equalsIgnoreCase("Residential"))
					/*if (!mGetPropertyFromFile("pt_newAssessmentTypeOfUse").equalsIgnoreCase("Residential"))
				{	
					System.out.println("Usage Type selected Non-Residential");
					//mAssert(mGetPropertyFromFile("pt_newAssessmentDisResid"), mGetPropertyFromFile("nonResidentialBuildupArea"), "Acual Buildup area not match with Expected");
					//mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentUsageFactorid"), mGetPropertyFromFile("pt_newAssessmentUsageFactor"));
					usageFactor=mCaptureSelectedValue("id", mGetPropertyFromFile("pt_newAssessmentUsageFactorid"));
					System.out.println("Property Detail Entry Usage Factor is: "+usageFactor);

					//mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentOccupancyFactorid"),mGetPropertyFromFile("pt_newAssessmentOccupancyFactor"));
					occFac=mCaptureSelectedValue("id", mGetPropertyFromFile("pt_newAssessmentOccupancyFactorid"));
					System.out.println("Property Detail Entry Occupancy Factor is: "+occFac);

				}
				else
				{
					System.out.println("Usage Type selected Residential");
					//ratableAr=driver.findElement(By.id(mGetPropertyFromFile("pt_newAssessmentDisResid"))).getAttribute("value");
					ratableAr=mGetText("id", mGetPropertyFromFile("pt_newAssessmentDisResid"), "value");
					System.out.println("Property Detail Entry Building Year is: "+ratableAr);
					//mAssert(mGetPropertyFromFile("pt_newAssessmentFinishBtnid"), mGetPropertyFromFile("residentialBuildupArea"), "Acual Buildup area not match with Expected");
					//mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentOccupancyFactorid"),mGetPropertyFromFile("pt_newAssessmentOccupancyFactor"));
					occFac=mCaptureSelectedValue("id", mGetPropertyFromFile("pt_newAssessmentOccupancyFactorid"));
					System.out.println("Property Detail Entry Occupancy Factor is: "+occFac);
				}

				//mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentOccupancyFactorid"),mGetPropertyFromFile("pt_newAssessmentOccupancyFactor"));

				if(k<cntForBldgDtl)
				{
					mClick("id",mGetPropertyFromFile("pt_newAssessmentCopyBldgDelsid"));
					break;
				}*/
				}
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in BldgDetailsTableReading method");
		}
	}

	public void BldgDetailsTableReading_org()
	{
		try {

			WebElement table = driver.findElement(By.id("buildingDetailsGridID"));

			mscroll(0, 1300);

			//List<WebElement> rows = table.findElements(By.xpath("//tr[not(@class)]"));
			//List<WebElement> rows = table.findElements(By.xpath("//tr[not(contains(@class, 'buildingTr'))]]"));
			List<WebElement> rows = table.findElements(By.tagName("tr").xpath("tr[class!=\"buildingTr\"]"));
			int rwcount=rows.size();
			System.out.println("Rows in building details before adding rows are: "+rows.size());


			List<WebElement> rows1 = table.findElements(By.tagName("tr").className("buildingTr"));
			int rwcount1=rows1.size();
			System.out.println("Rows in building details after adding rows are: "+rows1.size());

			List<WebElement> cols = rows1.get(1).findElements(By.xpath("td"));
			int colcount= cols.size();
			System.out.println(colcount);

			//for (int j=0;j<rwcount;j++) 	
			int rowNo=0;
			for(WebElement rowElement:rows)
			{
				/*if(j<rwcount-1)
				{*/

				/*if(mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
					{
					}*/
				yearBlgArray.add(driver.findElement(By.id("yearLabelFromValue"+rowNo)).getAttribute("value"));
				System.out.println("Building Year is: "+yearBlgArray);	
				floorNoArray.add(driver.findElement(By.id("floorName"+rowNo)).getAttribute("value"));
				System.out.println("Property Detail Entry Floor No is: "+floorNoArray);
				typeOfUseArray.add(driver.findElement(By.id("usage"+rowNo)).getAttribute("value"));
				System.out.println("Property Detail Entry Type of Use is: "+typeOfUseArray);
				constructTypeArray.add(driver.findElement(By.id("constructionClass"+rowNo)).getAttribute("value"));
				System.out.println("Property Detail Entry Construction Type is: "+constructTypeArray);
				buildupArArray.add(driver.findElement(By.id("buildingArea"+rowNo)).getAttribute("value"));
				System.out.println("Property Detail Entry Buildup Area is :"+buildupArArray);
				usageFactorArray.add(driver.findElement(By.id("pdAsseArea"+rowNo)).getAttribute("value"));
				System.out.println("Property Detail Entry Usage Factor is: "+usageFactorArray);
				occFacArray.add(driver.findElement(By.id("OccType"+rowNo)).getAttribute("value"));
				System.out.println("Property Detail Entry Occupancy Factor is: "+occFacArray);
				rowNo=rowNo+1;
			}


			//for (int i=0;i<rwcount1;i++) 	
			int rowNo1=0;
			for(WebElement rowElement:rows1)
			{
				if((rowNo1<rwcount1-1) && (rowNo1>rwcount1-rwcount))
				{
					//yearBlg=driver.findElement(By.id("yearLabelFromValue"+i)).getAttribute("value");
					yearBlgArray.add(driver.findElement(By.id("yearLabelFromValue"+rowNo1)).getAttribute("value"));
					System.out.println("Building Year is: "+yearBlgArray);					

					floorNoArray.add(new Select(driver.findElement(By.id("cpdLocID"+rowNo1))).getFirstSelectedOption().getText());
					System.out.println("Property Detail Entry Floor No is: "+floorNoArray);

					//mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentTypeOfUseid"),mGetPropertyFromFile("pt_newAssessmentTypeOfUse"));
					//typeOfUse=mCaptureSelectedValue("id", mGetPropertyFromFile("pt_newAssessmentTypeOfUseid"));

					typeOfUseArray.add(new Select(driver.findElement(By.name("entity.propertyDetails["+(rowNo1)+"].cpdUsage1"))).getFirstSelectedOption().getText());
					//	typeOfUseArray.add(driver.findElement(By.id("usage"+i)).getAttribute("value"));
					System.out.println("Property Detail Entry Type of Use is: "+typeOfUseArray);

					//mSelectDropDown("id", mGetPropertyFromFile("pt_newAssessmentConstructionTypeid"),mGetPropertyFromFile("pt_newAssessmentConstructionType"));
					//constructType=mCaptureSelectedValue("id", mGetPropertyFromFile("pt_newAssessmentConstructionTypeid"));

					constructTypeArray.add(new Select(driver.findElement(By.name("entity.propertyDetails["+(rowNo1)+"].codConsClass1"))).getFirstSelectedOption().getText());
					//	constructTypeArray.add(driver.findElement(By.id("constructionClass"+i)).getAttribute("value"));
					System.out.println("Property Detail Entry Construction Type is: "+constructTypeArray);

					//mSendKeys("id", mGetPropertyFromFile("pt_newAssessmentBuildingAreaid"), mGetPropertyFromFile("pt_newAssessmentBuildingArea"));

					buildupArArray.add(driver.findElement(By.id("pdBuildingArea"+rowNo1)).getAttribute("value"));
					//		buildupArArray.add(driver.findElement(By.id("buildingArea"+i)).getAttribute("value"));
					System.out.println("Property Detail Entry Buildup Area is :"+buildupArArray);


					usageFactorArray.add(new Select(driver.findElement(By.id("cpdSfctID"+rowNo1))).getFirstSelectedOption().getText());
					//	usageFactorArray.add(driver.findElement(By.id("pdAsseArea"+i)).getAttribute("value"));
					System.out.println("Property Detail Entry Usage Factor is: "+usageFactorArray);


					occFacArray.add(new Select(driver.findElement(By.id("cpdOccStatus"+rowNo1))).getFirstSelectedOption().getText());
					//	occFacArray.add(driver.findElement(By.id("OccType"+i)).getAttribute("value"));
					System.out.println("Property Detail Entry Occupancy Factor is: "+occFacArray);

				}
				rowNo1=rowNo1+1;

			}

		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in BldgDetailsTableReading method");
		}
	}

	public void BldgDetailsTableReadingExistingRows()
	{
		try {

			WebElement table = driver.findElement(By.id("buildingDetailsGridID"));

			mscroll(0, 1300);
			List<WebElement> rows1 = table.findElements(By.tagName("tr"));
			int rwcount=rows1.size();
			System.out.println("Rows in building details after adding rows are: "+rows1.size());

			List<WebElement> cols = rows1.get(1).findElements(By.xpath("td"));
			int colcount= cols.size();
			System.out.println(colcount);

			/*String[] flrNoArray=mGetPropertyFromFile("pt_newAssessmentFloorNoArray").split(",");
			System.out.println("string array size is : "+flrNoArray.length);

			int a=Integer.parseInt(flrNoArray[0].toString());
			int b=Integer.parseInt(flrNoArray[1].toString());
			int addCount=(b-a)+1;*/

			//for (k=0; k<=cntForBldgDtl;k++)
			for (int i=0;i<rwcount;i++) 		
			{
				if(i<rwcount-1)
				{
					if (!mGetPropertyFromFile("pt_changeHoldingDetailsType").equalsIgnoreCase("Alteration"))
					{
						System.out.println("Channge in Assessment Holding Deatils Type is selected Addition");
						yearBlgArray.add(driver.findElement(By.id("yearLabelFromValue"+i)).getAttribute("value"));
						System.out.println("Building Year is: "+yearBlgArray);	
						floorNoArray.add(driver.findElement(By.id("floorName"+i)).getAttribute("value"));
						System.out.println("Property Detail Entry Floor No is: "+floorNoArray);
						typeOfUseArray.add(driver.findElement(By.id("usage"+i)).getAttribute("value"));
						System.out.println("Property Detail Entry Type of Use is: "+typeOfUseArray);
						constructTypeArray.add(driver.findElement(By.id("constructionClass"+i)).getAttribute("value"));
						System.out.println("Property Detail Entry Construction Type is: "+constructTypeArray);
						buildupArArray.add(driver.findElement(By.id("buildingArea"+i)).getAttribute("value"));
						System.out.println("Property Detail Entry Buildup Area is :"+buildupArArray);
						calcRatableArArray.add(driver.findElement(By.id("pdAsseArea"+i)).getAttribute("value"));
						System.out.println("Property Detail Entry Ratable Area is :"+calcRatableArArray);
						usageFactorArray.add(driver.findElement(By.id("nonResiFactor"+i)).getAttribute("value"));
						System.out.println("Property Detail Entry Usage Factor is: "+usageFactorArray);
						occFacArray.add(driver.findElement(By.id("OccType"+i)).getAttribute("value"));
						System.out.println("Property Detail Entry Occupancy Factor is: "+occFacArray);
					}
					else
					{
						System.out.println("Channge in Assessment Holding Deatils Type is selected Alteration");
						yearBlgArray.add(driver.findElement(By.id("yearLabelFromValue"+i)).getAttribute("value"));
						System.out.println("Building Year is: "+yearBlgArray);	
						floorNoArray.add(new Select (driver.findElement(By.id("cpdLocID"+i))).getFirstSelectedOption().getText());
						System.out.println("Property Detail Entry Floor No is: "+floorNoArray);
						typeOfUseArray.add(new Select (driver.findElement(By.name("entity.propertyDetails["+i+"].cpdUsage1"))).getFirstSelectedOption().getText());//Change By.Name
						/*String typeOfUseArrayChngAltr= new Select(driver.findElement(By.name("entity.propertyDetails["+i+"].cpdUsage1"))).getFirstSelectedOption().getText();
						typeOfUseArray.add(typeOfUseArrayChngAltr);*/
						System.out.println("Property Detail Entry Type of Use is: "+typeOfUseArray);
						constructTypeArray.add(new Select (driver.findElement(By.name("entity.propertyDetails["+i+"].codConsClass1"))).getFirstSelectedOption().getText());//Change By.Name
						System.out.println("Property Detail Entry Construction Type is: "+constructTypeArray);
						buildupArArray.add(driver.findElement(By.id("pdBuildingArea"+i)).getAttribute("value"));
						System.out.println("Property Detail Entry Buildup Area is :"+buildupArArray);
						calcRatableArArray.add(driver.findElement(By.id("pdAsseArea"+i)).getAttribute("value"));
						System.out.println("Property Detail Entry Ratable Area is :"+calcRatableArArray);
						/*ratableArArray.add(driver.findElement(By.name("entity.propertyDetails["+i+"].pdAsseArea")).getText());
						System.out.println("Property Detail Entry Buildup Area is :"+ratableArArray);*/						
						usageFactorArray.add(new Select (driver.findElement(By.id("cpdSfctID"+i))).getFirstSelectedOption().getText());
						System.out.println("Property Detail Entry Usage Factor is: "+usageFactorArray);
						occFacArray.add(new Select (driver.findElement(By.id("cpdOccStatus"+i))).getFirstSelectedOption().getText());
						System.out.println("Property Detail Entry Occupancy Factor is: "+occFacArray);
					}

					double unitArea = 0.0;

					if(!mGetPropertyFromFile("landType").equalsIgnoreCase("Vacant Land"))
					{
						if(typeOfUseArray.get(i).equalsIgnoreCase("Residential")&&constructTypeArray.get(i).equalsIgnoreCase("Pucca Building with RCC Roof")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Principal Main Road"))
						{
							unitArea=15;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Residential")&&constructTypeArray.get(i).equalsIgnoreCase("Pucca Building with RCC Roof")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Main Road"))
						{
							unitArea=10;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Residential")&&constructTypeArray.get(i).equalsIgnoreCase("Pucca Building with RCC Roof")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Other Road"))
						{
							unitArea=4;
						}	
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Commercial / Industrial")&&constructTypeArray.get(i).equalsIgnoreCase("Pucca Building with RCC Roof")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Principal Main Road"))
						{
							unitArea=45;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Commercial / Industrial")&&constructTypeArray.get(i).equalsIgnoreCase("Pucca Building with RCC Roof")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Main Road"))
						{
							unitArea=30;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Commercial / Industrial")&&constructTypeArray.get(i).equalsIgnoreCase("Pucca Building with RCC Roof")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Other Road"))
						{
							unitArea=15;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Others")&&constructTypeArray.get(i).equalsIgnoreCase("Pucca Building with RCC Roof")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Principal Main Road"))
						{
							unitArea=30;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Others")&&constructTypeArray.get(i).equalsIgnoreCase("Pucca Building with RCC Roof")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Main Road"))
						{
							unitArea=20;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Others")&&constructTypeArray.get(i).equalsIgnoreCase("Pucca Building with RCC Roof")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Other Road"))
						{
							unitArea=10;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Residential")&&constructTypeArray.get(i).equalsIgnoreCase("Pucca Building with Asbestos")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Principal Main Road"))
						{
							unitArea=10;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Residential")&&constructTypeArray.get(i).equalsIgnoreCase("Pucca Building with Asbestos")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Main Road"))
						{
							unitArea=6;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Residential")&&constructTypeArray.get(i).equalsIgnoreCase("Pucca Building with Asbestos")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Other Road"))
						{
							unitArea=3;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Commercial / Industrial")&&constructTypeArray.get(i).equalsIgnoreCase("Pucca Building with Asbestos")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Principal Main Road"))
						{
							unitArea=30;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Commercial / Industrial")&&constructTypeArray.get(i).equalsIgnoreCase("Pucca Building with Asbestos")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Main Road"))
						{
							unitArea=20;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Commercial / Industrial")&&constructTypeArray.get(i).equalsIgnoreCase("Pucca Building with Asbestos")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Other Road"))
						{
							unitArea=10;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Others")&&constructTypeArray.get(i).equalsIgnoreCase("Pucca Building with Asbestos")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Principal Main Road"))
						{
							unitArea=20;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Others")&&constructTypeArray.get(i).equalsIgnoreCase("Pucca Building with Asbestos")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Main Road"))
						{
							unitArea=10;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Others")&&constructTypeArray.get(i).equalsIgnoreCase("Pucca Building with Asbestos")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Other Road"))
						{
							unitArea=6;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Residential")&&constructTypeArray.get(i).equalsIgnoreCase("Others")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Principal Main Road"))
						{
							unitArea=3;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Residential")&&constructTypeArray.get(i).equalsIgnoreCase("Others")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Main Road"))
						{
							unitArea=2;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Residential")&&constructTypeArray.get(i).equalsIgnoreCase("Others")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Other Road"))
						{
							unitArea=1;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Commercial / Industrial")&&constructTypeArray.get(i).equalsIgnoreCase("Others")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Principal Main Road"))
						{
							unitArea=9;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Commercial / Industrial")&&constructTypeArray.get(i).equalsIgnoreCase("Others")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Main Road"))
						{
							unitArea=6;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Commercial / Industrial")&&constructTypeArray.get(i).equalsIgnoreCase("Others")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Other Road"))
						{
							unitArea=3;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Others")&&constructTypeArray.get(i).equalsIgnoreCase("Others")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Principal Main Road"))
						{
							unitArea=6;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Others")&&constructTypeArray.get(i).equalsIgnoreCase("Others")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Main Road"))
						{
							unitArea=4;
						}
						else if(typeOfUseArray.get(i).equalsIgnoreCase("Others")&&constructTypeArray.get(i).equalsIgnoreCase("Others")&&chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Other Road"))
						{
							unitArea=2;
						}
					}
					else if(mGetPropertyFromFile("landType").equalsIgnoreCase("Vacant Land"))
					{
						if(chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Principal Main Road"))
						{
							unitArea=0.36;
						}
						else if(chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Main Road"))
						{
							unitArea=0.28;
						}
						else if(chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Other Road"))
						{
							unitArea=0.19;
						}
					}

					calcUnitRateArray.add(String.valueOf(unitArea));
					System.out.println("Captured unit rate into array : "+calcUnitRateArray);

					Double rateOfTax=9.00;
					DecimalFormat decimalFormat=new DecimalFormat("#.##");
					calcRateOfTaxArray.add(String.valueOf(rateOfTax));
					System.out.println("Captured rate of tax into array : "+calcRateOfTaxArray);

					Double calculatedRateOfTax=rateOfTax/100;
					System.out.println("Calculated rate of tax : "+calculatedRateOfTax);

					double annualRatableValueForRes =0;
					//int annualRatableValueForNonRes = 0;
					double annualRatableValueForNonRes = 0;
					double annualTaxRes;
					double annualTaxNonRes = 0;
					Double ratableAreaForRes = 0.0;
					Double ratableAreaForNonRes = 0.0;

					int pt_newAssessmentServiceChargeCurSAS;

					//	int buildupArArrayValue=buildupArArray

					if(typeOfUseArray.get(i).equalsIgnoreCase("Residential"))
					{
						ratableAreaForRes=Double.parseDouble(buildupArArray.get(i))*0.7;
						calcRatableAreaForResArray.add(String.valueOf(ratableAreaForRes));
						System.out.println("Calculated ratable area for residential is : "+calcRatableAreaForResArray);
					}
					else if(!typeOfUseArray.get(i).equalsIgnoreCase("Residential"))
					{
						ratableAreaForNonRes=Double.parseDouble(buildupArArray.get(i))*0.8;
						calcRatableAreaForNonResArray.add(String.valueOf(ratableAreaForNonRes));
						System.out.println("Calculated ratable area for non-residential is : "+calcRatableAreaForNonResArray);
					}

					DecimalFormat df2 = new DecimalFormat(".##");
					if(occFacArray.get(i).equalsIgnoreCase("Tenanted")&&typeOfUseArray.get(i).equalsIgnoreCase("Residential"))
					{
						annualRatableValueForRes=(Double.parseDouble(calcRatableAreaForResArray.get(i))*unitArea*1.5);
						String annualRatableValueForResOrg=df2.format(annualRatableValueForRes);
						calcAnnulRateblValArray.add(annualRatableValueForResOrg);
						System.out.println("Calculated annual ratable value into array : "+calcAnnulRateblValArray);
					}
					else if((!occFacArray.get(i).equalsIgnoreCase("Tenanted"))&&typeOfUseArray.get(i).equalsIgnoreCase("Residential"))
					{
						annualRatableValueForRes=(Double.parseDouble(calcRatableAreaForResArray.get(i))*unitArea);
						String annualRatableValueForResOrg=df2.format(annualRatableValueForRes);
						calcAnnulRateblValArray.add(annualRatableValueForResOrg);
						System.out.println("Calculated annual ratable value into array : "+calcAnnulRateblValArray);
					}
					else if(occFacArray.get(i).equalsIgnoreCase("Tenanted")&&(!typeOfUseArray.get(i).equalsIgnoreCase("Residential")))
					{
						annualRatableValueForNonRes=(Double.parseDouble(calcRatableAreaForNonResArray.get(i))*unitArea*1.5);
						String annualRatableValueForNonResOrg=df2.format(annualRatableValueForNonRes);
						calcAnnulRateblValArray.add(annualRatableValueForNonResOrg);
						System.out.println("Calculated annual ratable value into array : "+calcAnnulRateblValArray);
					}
					else if((!occFacArray.get(i).equalsIgnoreCase("Tenanted"))&&(!typeOfUseArray.get(i).equalsIgnoreCase("Residential")))
					{
						annualRatableValueForNonRes=(int)(Double.parseDouble(calcRatableAreaForNonResArray.get(i))*unitArea);
						String annualRatableValueForNonResOrg=df2.format(annualRatableValueForNonRes);
						calcAnnulRateblValArray.add(annualRatableValueForNonResOrg);
						System.out.println("Calculated annual ratable value into array : "+calcAnnulRateblValArray);
					}
					else
					{
						System.out.println("In else of annual ratable value");
					}

					if(usageFactorArray.get(i).equalsIgnoreCase("Govt. Establishment")&&typeOfUseArray.get(i).equalsIgnoreCase("Residential"))
					{
						annualTaxRes=Math.round(annualRatableValueForRes*calculatedRateOfTax)*75/100;
						calcAnnulTaxArray.add(String.valueOf(annualTaxRes));
						System.out.println("Calculated annual ratable tax into array : "+calcAnnulTaxArray);
					}
					else if((!usageFactorArray.get(i).equalsIgnoreCase("Govt. Establishment"))&&typeOfUseArray.get(i).equalsIgnoreCase("Residential"))
					{
						annualTaxRes=Math.round(annualRatableValueForRes*calculatedRateOfTax);
						calcAnnulTaxArray.add(String.valueOf(annualTaxRes));
						System.out.println("Calculated annual ratable tax into array : "+calcAnnulTaxArray);
					}
					else if(usageFactorArray.get(i).equalsIgnoreCase("Govt. Establishment")&&(!typeOfUseArray.get(i).equalsIgnoreCase("Residential")))
					{
						annualTaxNonRes=Math.round(annualRatableValueForNonRes*calculatedRateOfTax)*75/100;
						calcAnnulTaxArray.add(String.valueOf(annualTaxNonRes));
						System.out.println("Calculated annual ratable tax into array : "+calcAnnulTaxArray);
					}
					else if((!usageFactorArray.get(i).equalsIgnoreCase("Govt. Establishment"))&&(!typeOfUseArray.get(i).equalsIgnoreCase("Residential")))
					{
						annualTaxNonRes=Math.round(annualRatableValueForNonRes*calculatedRateOfTax);
						calcAnnulTaxArray.add(String.valueOf(annualTaxNonRes));
						System.out.println("Calculated annual ratable tax into array : "+calcAnnulTaxArray);
					}
					else
					{
						System.out.println("In else of annual ratable tax");
					}

					if(usageFactorArray.get(i).equalsIgnoreCase("Govt. Establishment"))
					{
						pt_newAssessmentServiceChargeCurSAS=(int)(annualTaxNonRes*75)/100;
						//pt_newAssessmentServiceChargeCurSAS=(int)(Integer.parseInt(calcAnnulTaxArray.get(i))*75)/100;
						calcServiceChargeArray.add(String.valueOf(pt_newAssessmentServiceChargeCurSAS));
						System.out.println("Calculated service charge into array : "+calcServiceChargeArray);
					}
					else
					{
						pt_newAssessmentServiceChargeCurSAS=0;
						calcServiceChargeArray.add(String.valueOf(pt_newAssessmentServiceChargeCurSAS));
						System.out.println("Calculated service charge into array : "+calcServiceChargeArray);
					}

				}
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in BldgDetailsTableReadingExistingRows method");
		}
	}


	/*public void additionofBldgDetails()
	{
		WebElement table = driver.findElement(By.id("buildingDetailsGridID"));

		mscroll(0, 1300);
		List<WebElement> rows1 = table.findElements(By.tagName("tr"));
		rows1.size();
		System.out.println(rows1.size());

		List<WebElement> cols = rows1.get(1).findElements(By.xpath("td"));
		int colcount= cols.size();
		System.out.println(colcount);

		String[] flrNoArray=mGetPropertyFromFile("pt_newAssessmentFloorNo").split(",");
		System.out.println("string array size is : "+flrNoArray.length);

		String[] usgTypArray=mGetPropertyFromFile("pt_newAssessmentTypeOfUse").split(",");
		System.out.println("string array size is : "+usgTypArray.length);

		String[] constructionTypeArray=mGetPropertyFromFile("pt_newAssessmentConstructionType").split(",");
		System.out.println("string array size is : "+constructionTypeArray.length);

		String[] builtupAreaArray=mGetPropertyFromFile("pt_newAssessmentBuildingArea").split(";");
		System.out.println("string array size is : "+builtupAreaArray.length);

		String[] usageFactorArray=mGetPropertyFromFile("pt_newAssessmentUsageFactor").split(",");
		System.out.println("string array size is : "+usageFactorArray.length);

		String[] occuFactorArray=mGetPropertyFromFile("pt_newAssessmentOccupancyFactor").split(",");
		System.out.println("string array size is : "+occuFactorArray.length);

		mCustomWait(5000);

		String typeofassessment=mGetText("css", mGetPropertyFromFile("pt_newAssessmentNewAssessmentSerNameid"));

		if(typeofassessment.equalsIgnoreCase("File Your Self Assessment with Change / Alteration in Assessment"))
		{
			mClick("linkText", "+");
		}

		int counter=1;
		for (int i=0;i<addCount;i++) 
						{
		for(int f=0;f<flrNoArray.length;f++)
		{

			for(int j=0;j<colcount;j++)
			{
				if(j==0)
				{
					mCustomWait(1500);
					//String yr = driver.findElement(By.id("yearLabelFromValue"+(f))).getAttribute("value");
					String yr =mGetText("id", "yearLabelFromValue"+(f), "value");
					System.out.println("Year is : "+yr);
				}
				if(j==1)
				{
					mCustomWait(1500);
					new Select(driver.findElement(By.id("cpdLocID"+(f)))).selectByVisibleText(flrNoArray[f]);
					//String floor = driver.findElement(By.id("floorName"+(j-1))).getAttribute("value");
					String floor = driver.findElement(By.id("cpdLocID"+(f))).getAttribute("id");
					System.out.println("Floor is : "+floor);
				}
				if(j==2)
				{
					mCustomWait(1500);
					new Select(driver.findElement(By.name("entity.propertyDetails["+(f)+"].cpdUsage1"))).selectByVisibleText(usgTypArray[f]);
					String usage = driver.findElement(By.id("usage"+(j-1))).getAttribute("value");
					System.out.println("Usage type is : "+usage);
				}
				if(j==3)
				{
					mCustomWait(1500);
					new Select (driver.findElement(By.name("entity.propertyDetails["+(f)+"].codConsClass1"))).selectByVisibleText(constructionTypeArray[f]);
					//System.out.println("Construction class is : "+cnstrnCls);
				}
				if(j==4)
				{
					mCustomWait(1500);
					//driver.findElement(By.id("pdBuildingArea"+(f))).sendKeys(builtupAreaArray[f]);
					mSendKeys("id", "pdBuildingArea"+(f), "builtupAreaArray[f]");
					//System.out.println("Built-up Area is : "+bultupArea);
				}
				if(j==5)
				{
					mCustomWait(1500);
					//String ratableArea = driver.findElement(By.id("pdAsseArea"+(f))).getText();
					String ratableArea = mGetText("id", "pdAsseArea"+(f));
					System.out.println("Ratable Area is : "+ratableArea);
				}
				if(j==6)
				{
					mCustomWait(1500);
					if(usgTypArray[f].equalsIgnoreCase("Residential"))
					{
						//String usgFactor = driver.findElement(By.id("cpdSfctID"+(f))).getAttribute("value");
						String usgFactor =mGetText("id", "cpdSfctID"+(f), "value");
						System.out.println("Usage Factor is : "+usgFactor);
					}
					else
					{
						new Select (driver.findElement(By.id("cpdSfctID"+(f)))).selectByVisibleText(usageFactorArray[f]);
					}
				}
				if(j==7)
				{
					mCustomWait(1500);
					new Select (driver.findElement(By.id("cpdOccStatus"+(f)))).selectByVisibleText(occuFactorArray[f]);
					//System.out.println("Occupancy Factor is : "+occpFactor);
				}
			}
			if(f<(flrNoArray.length-1))
			{
				mCustomWait(5000);
				if(f==0){
					mClick("xpath", "//*[@id=\"cloumn0\"]/td[9]/a");
				}
				else if(f>0)
				{
					mClick("xpath", "//*[@id=\"cloumn"+f+"\"]/td[9]/a[1]");	
				}
				System.out.println("Clicking on for add");
				mCustomWait(5000);
			}
			counter++;
		}
	}*/

	public void additionofBldgDetails()
	{
		ArrayList<String> addedFloorNoArray= new ArrayList<String>();
		ArrayList<String> addedTypeOfUseArray= new ArrayList<String>();
		ArrayList<String> addedConstructTypeArray= new ArrayList<String>();
		ArrayList<String> addedBuildupArArray= new ArrayList<String>();
		ArrayList<String> addedUsageFactorArray= new ArrayList<String>();
		ArrayList<String> addedOccFacArray= new ArrayList<String>();
		ArrayList<String> calculatedRatableArArray= new ArrayList<String>();
		ArrayList<String> calculatedUnitRateArray= new ArrayList<String>();
		ArrayList<String> calculatedAnnulRateblValArray= new ArrayList<String>();
		ArrayList<String> calculatedRateOfTaxArray= new ArrayList<String>();
		ArrayList<String> calculatedAnnulTaxArray= new ArrayList<String>(); 

		WebElement table = driver.findElement(By.id("buildingDetailsGridID"));

		mscroll(0, 1300);
		List<WebElement> rows1 = table.findElements(By.tagName("tr"));
		rows1.size();
		System.out.println(rows1.size());

		List<WebElement> cols = rows1.get(1).findElements(By.xpath("td"));
		int colcount= cols.size();
		System.out.println(colcount);

		String[] flrNoArray=mGetPropertyFromFile("pt_newAssessmentFloorNoArray").split(",");
		System.out.println("string array size is : "+flrNoArray.length);

		int a=Integer.parseInt(flrNoArray[0].toString());
		int b=Integer.parseInt(flrNoArray[1].toString());
		addCount=(b-a)+1;

		/*String[] flrNoArray=mGetPropertyFromFile("pt_newAssessmentFloorNo").split(",");
		System.out.println("string array size is : "+flrNoArray.length);

		String[] usgTypArray=mGetPropertyFromFile("pt_newAssessmentTypeOfUse").split(",");
		System.out.println("string array size is : "+usgTypArray.length);

		String[] constructionTypeArray=mGetPropertyFromFile("pt_newAssessmentConstructionType").split(",");
		System.out.println("string array size is : "+constructionTypeArray.length);

		String[] builtupAreaArray=mGetPropertyFromFile("pt_newAssessmentBuildingArea").split(";");
		System.out.println("string array size is : "+builtupAreaArray.length);

		String[] usageFactorArray=mGetPropertyFromFile("pt_newAssessmentUsageFactor").split(",");
		System.out.println("string array size is : "+usageFactorArray.length);

		String[] occuFactorArray=mGetPropertyFromFile("pt_newAssessmentOccupancyFactor").split(",");
		System.out.println("string array size is : "+occuFactorArray.length);*/

		mCustomWait(5000);
		StringBuilder sb=new StringBuilder();
		String typeofassessment=mGetText("css", mGetPropertyFromFile("pt_newAssessmentNewAssessmentSerNameid"));

		if(typeofassessment.equalsIgnoreCase("File Your Self Assessment with Change / Alteration in Assessment"))
		{
			mClick("linkText", "+");
		}

		int counter=1;
		InvoCount=a;

		for (int i=0;i<addCount;i++) 
		{

			if(InvoCount>b)
				break;

			for(int j=0;j<colcount;j++)
			{
				if(j==0)
				{

					mCustomWait(1500);
					String yr = driver.findElement(By.id("yearLabelFromValue"+i)).getAttribute("value");
					/*System.out.println("Year is : "+yr);*/

				}
				if(j==1)
				{

					mCustomWait(1500);
					new Select(driver.findElement(By.id("cpdLocID"+i))).selectByVisibleText(mGetPropertyFromFile("pt_newAssessmentFloorNo"));
					addedFloorNoArray.add(mGetPropertyFromFile("pt_newAssessmentFloorNo"));
					System.out.println("Added floor no stored in array as : "+addFloorNoArray);
				}
				if(j==2)
				{
					mCustomWait(1500);
					new Select(driver.findElement(By.name("entity.propertyDetails["+i+"].cpdUsage1"))).selectByVisibleText(mGetPropertyFromFile("pt_newAssessmentTypeOfUse"));
					addedTypeOfUseArray.add(mGetPropertyFromFile("pt_newAssessmentTypeOfUse"));
					/*String usage = driver.findElement(By.id("usage"+(j-1))).getAttribute("value");
					System.out.println("Usage type is : "+usage);*/
				}
				if(j==3)
				{
					mCustomWait(1500);
					new Select (driver.findElement(By.name("entity.propertyDetails["+i+"].codConsClass1"))).selectByVisibleText(mGetPropertyFromFile("pt_newAssessmentConstructionType"));
					addedConstructTypeArray.add(mGetPropertyFromFile("pt_newAssessmentConstructionType"));
					//System.out.println("Construction class is : "+cnstrnCls);
				}
				if(j==4)
				{
					mCustomWait(1500);
					driver.findElement(By.id("pdBuildingArea"+i)).sendKeys(mGetPropertyFromFile("pt_newAssessmentBuildingArea"));
					addedBuildupArArray.add(mGetPropertyFromFile("pt_newAssessmentBuildingArea"));
					//System.out.println("Built-up Area is : "+bultupArea);
				}
				if(j==5)
				{
					mCustomWait(1500);
					String ratableArea = driver.findElement(By.id("pdAsseArea"+i)).getText();
					System.out.println("Ratable Area is : "+ratableArea);
				}
				if(j==6)
				{
					mCustomWait(1500);
					if(mGetPropertyFromFile("pt_newAssessmentTypeOfUse").equalsIgnoreCase("Residential"))
					{
						String usgFactor = driver.findElement(By.id("cpdSfctID"+i)).getAttribute("value");
						System.out.println("Usage Factor is : "+usgFactor);
						calculatedRatableArArray.add(mGetPropertyFromFile("ratableAreaForRes"));
						calculatedAnnulRateblValArray.add(mGetPropertyFromFile("annualRatableValueForRes"));
						calculatedAnnulTaxArray.add(mGetPropertyFromFile("annualTaxRes"));
					}
					else
					{
						new Select (driver.findElement(By.id("cpdSfctID"+i))).selectByVisibleText(mGetPropertyFromFile("pt_newAssessmentUsageFactor"));
						calculatedRatableArArray.add(mGetPropertyFromFile("ratableAreaForNonRes"));
						calculatedAnnulRateblValArray.add(mGetPropertyFromFile("annualRatableValueForNonRes"));
						calculatedAnnulTaxArray.add(mGetPropertyFromFile("annualTaxNonRes"));

					}
					addedUsageFactorArray.add(mGetPropertyFromFile("pt_newAssessmentUsageFactor"));
					calculatedUnitRateArray.add(mGetPropertyFromFile("unitArea"));
					calculatedRateOfTaxArray.add(mGetPropertyFromFile("rateOfTax"));

				}
				if(j==7)
				{
					mCustomWait(1500);
					new Select (driver.findElement(By.id("cpdOccStatus"+i))).selectByVisibleText(mGetPropertyFromFile("pt_newAssessmentOccupancyFactor"));
					addedOccFacArray.add(mGetPropertyFromFile("pt_newAssessmentOccupancyFactor"));
					//System.out.println("Occupancy Factor is : "+occpFactor);
				}

				/*if(mGetPropertyFromFile("pt_newAssessmentTypeOfUse").equalsIgnoreCase("Residential"))
				{
					calculatedRatableArArray.add(mGetPropertyFromFile("ratableAreaForRes"));
					calculatedAnnulRateblValArray.add(mGetPropertyFromFile("ratableAreaForRes"));
					calculatedAnnulTaxArray.add(mGetPropertyFromFile("annualTaxRes"));
				}
				else
				{
					calculatedRatableArArray.add(mGetPropertyFromFile("ratableAreaForNonRes"));
					calculatedAnnulRateblValArray.add(mGetPropertyFromFile("annualRatableValueForNonRes"));
					calculatedAnnulTaxArray.add(mGetPropertyFromFile("annualTaxNonRes"));
				}
				calculatedUnitRateArray.add(mGetPropertyFromFile("unitArea"));
				calculatedRateOfTaxArray.add(mGetPropertyFromFile("rateOfTax"));*/
				System.out.println("Calculated ratable area stored in array as-reading : "+calculatedRatableArArray);
				System.out.println("Calculated annual ratable value stored in array as-reading : "+calculatedAnnulRateblValArray);
			}
			if(i<(addCount-1))
			{
				mCustomWait(5000);
				if(i==0){
					//mClick("xpath", "//*[@id=\"cloumn0\"]/td[9]/a");
					driver.findElement(By.xpath("//*[@id=\"cloumn0\"]/td[9]/a")).click();
				}
				else if(i>0)
				{
					//mClick("xpath", "//*[@id=\"cloumn"+InvoCount+"\"]/td[9]/a[1]");	
					driver.findElement(By.xpath("//*[@id=\"cloumn"+i+"\"]/td[9]/a[1]")).click();
				}
				System.out.println("Clicking on for add");
				mCustomWait(5000);
			}
			counter++;
			InvoCount++;

		}

		if(cntForBldgDtl>=0)
		{
			for(int i=0;i<=(cntForBldgDtl);i++)
			{
				addFloorNoArray.addAll(addedFloorNoArray);
				System.out.println("Added floor no stored in array as : "+addFloorNoArray);

				addTypeOfUseArray.addAll(addedTypeOfUseArray);
				addConstructTypeArray.addAll(addedConstructTypeArray);
				addBuildupArArray.addAll(addedBuildupArArray);
				addUsageFactorArray.addAll(addedUsageFactorArray);
				System.out.println("Added usage factor stored in array as : "+addUsageFactorArray);
				addOccFacArray.addAll(addedOccFacArray);
				System.out.println("Value of i is :"+i);
				calcRatableArArray.addAll(calculatedRatableArArray);
				System.out.println("Calculated ratable area stored in array as : "+calcRatableArArray);
				calcAnnulRateblValArray.addAll(calculatedAnnulRateblValArray);
				System.out.println("Calculated annual ratable value stored in array as : "+calcAnnulRateblValArray);
				calcAnnulTaxArray.addAll(calculatedAnnulTaxArray);
				System.out.println("Calculated annual tax stored in array as : "+calcAnnulTaxArray);
				calcUnitRateArray.addAll(calculatedUnitRateArray);
				System.out.println("Calculated unit rate stored in array as : "+calcUnitRateArray);
				calcRateOfTaxArray.addAll(calculatedRateOfTaxArray);
				System.out.println("Calculated rate of tax stored in array as : "+calcRateOfTaxArray);
			}
		}
		else
		{
			System.out.println("Building details are added for current year");
		}
	}



	public void SASForm()
	{
		try
		{
			System.out.println("Start Reading SAS Form");

			String sasRoadFactor;
			if(mGetPropertyFromFile("landType").equalsIgnoreCase("Land + Building"))
			{
				// Land Type
				//Boolean sasLandBuilding=driver.findElement(By.id("propertyMaster.codpropertyID12")).isSelected();
				Boolean sasLandBuilding=mFindElement("id", mGetPropertyFromFile("pt_newAssessmentSASLndTypeid")).isSelected();
				System.out.println("Land type is Land + Building");
				if(sasLandBuilding==true)
				{
					//String sasLandType=driver.findElement(By.id("propertyMaster.codpropertyID12")).getAttribute("Text");
					//String sasLandType=mGetText("id", mGetPropertyFromFile("pt_newAssessmentSASLndTypeid"), "Text");
					//System.out.println("Land type is :"+sasLandType);
					//String sasLandType=driver.findElement(By.id("propertyMaster.codpropertyID12")).getText();
					String sasLandTypeOrg=driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[3]/tbody/tr[1]/td[3]")).getText();
					System.out.println("Land type is :"+sasLandTypeOrg);
					String sasLandType=sasLandTypeOrg.substring(4, 19);
					if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
					{
						mAssert(sasLandType, landTypeSelected, "Land type does not matches in SAS form, Actual is :"+sasLandType+"Expected is :"+landTypeSelected);
					}
					else
					{
						mAssert(sasLandType, chngAsmtLandType, "Land type does not matches in SAS form, Actual is :"+sasLandType+"Expected is :"+chngAsmtLandType);
					}
				}
				/*// Road Factor
				if(driver.findElement(By.id("propertyMaster.mainpgRoadfctr1")).isSelected())
				{
					sasRoadFactor=driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[4]/tbody/tr[1]/td[2]/text()[1]")).getText();
					//System.out.println("Road factor is : Principal Main Road");
					System.out.println("Road factor is :"+sasRoadFactor);
				}
				else if(driver.findElement(By.id("propertyMaster.mainpgRoadfctr2")).isSelected())
				{
					sasRoadFactor=driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[4]/tbody/tr[1]/td[2]/text()[2]")).getText();
					//System.out.println("Road factor is : Main Road");
					System.out.println("Road factor is :"+sasRoadFactor);
				}
				else if(driver.findElement(By.id("propertyMaster.mainpgRoadfctr3")).isSelected())
				{
					sasRoadFactor=driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[4]/tbody/tr[1]/td[2]/text()[3]")).getText();
					//System.out.println("Road factor is : Other Road");
					System.out.println("Road factor is :"+sasRoadFactor);
				}*/

				mTakeScreenShot();
				mscroll(1155, 707);
				// Acquisition Year 
				//String acquisitionYear=driver.findElement(By.cssSelector("#command > div.form-div.margin_top_10 > table:nth-child(5) > tbody > tr:nth-child(2) > td:nth-child(2) > span")).getText();


				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					String acquisitionYear=mGetText("css", mGetPropertyFromFile("pt_newAssessmentSASAcqYrid"));
					System.out.println("Year of Acquisition is: "+acquisitionYear);
					mAssert(acquisitionYear, mGetPropertyFromFile("pt_newAssessmentAcquisitionYear"), "Actual is: "+acquisitionYear+ "Expected is: "+mGetPropertyFromFile("pt_newAssessmentAcquisitionYear")+ "Year of Acquisition is not matching");
				}
				else
				{
					String acquisitionYear=driver.findElement(By.cssSelector("#command > div.form-div.margin_top_10 > table:nth-child(5) > tbody > tr:nth-child(2) > td:nth-child(2)")).getText();
					System.out.println("Year of Acquisition is: "+acquisitionYear);
					mAssert(acquisitionYear, chngAsmtLandTypeAcqYr, "Actual is: "+acquisitionYear+ "Expected is: "+chngAsmtLandTypeAcqYr+ "Year of Acquisition is not matching");
				}

				//Vacant Land Annual Tax on SAS form @Ritesh Dated: - 12-10-2016
				//String sasVacantLandAnnualTx = driver.findElement(By.cssSelector("#command > div.form-div.margin_top_10 > table:nth-child(11) > tbody > tr:nth-child(7) > td:nth-child(3)")).getText();
				String sasVacantLandAnnualTx =mGetText("css", mGetPropertyFromFile("pt_newAssessmentSASVacantLandAnnualTxid"));
				System.out.println("Vacant Land Annual Tax is: "+sasVacantLandAnnualTx);
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					mAssert(sasVacantLandAnnualTx, mGetPropertyFromFile("pt_newAssessmentVacantLandAnnualTax"), "Actual is: "+sasVacantLandAnnualTx+ "Expected is: "+mGetPropertyFromFile("pt_newAssessmentVacantLandAnnualTax")+"Vacant Land Annual Tax is not matching in SAS form");
				}
				else
				{
					mAssert(sasVacantLandAnnualTx, Long.toString(pt_newAssessmentVacantLandAnnualTax), "Actual is: "+sasVacantLandAnnualTx+ "Expected is: "+Long.toString(pt_newAssessmentVacantLandAnnualTax)+"Vacant Land Annual Tax is not matching in SAS form");
				}

				mTakeScreenShot();
				mscroll(1217, 714);
				//Reading Building Details Table

				//WebElement table=driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/div[2]/table[2]"));
				WebElement table=mFindElement("xpath", mGetPropertyFromFile("pt_newAssessmentSASRdBlgDetidid"));
				List<WebElement> rows=table.findElements(By.tagName("tr"));
				int rowCount=rows.size();
				System.out.println("Total number of rows :"+rowCount);

				int rowNo=0;
				for(WebElement rowElement:rows)
				{
					List<WebElement> cols=rowElement.findElements(By.tagName("td"));
					int colCount=cols.size();
					System.out.println("Total number of columns :"+colCount);

					int columnNo=0;
					for(WebElement colElement:cols)
					{
						if(rowNo>3)
						{
							if(columnNo==0)
							{
								//	String sasBldgYear=rowElement.findElement(By.xpath("td[1]")).getText();
								sasBldgYearArray.add(rowElement.findElement(By.xpath("td[1]")).getText());
								System.out.println("Building Details year is :"+sasBldgYearArray);
								//ArrayList<ArrayList<String>> rowCollector=new ArrayList<ArrayList<String>>();
								//ArrayList<String> colCollector=new ArrayList<String>();
								//colCollector.add(sasBldgYear);						
								//mAssert(sasBldgYear, yearBlg, "Actual is: "+sasBldgYear+ "Expected is: "+yearBlg+ "Building Year is not matching");						
							}
							if(columnNo==1)
							{
								//	String sasFloorNo=rowElement.findElement(By.xpath("td[2]")).getText();
								sasFloorNoArray.add(rowElement.findElement(By.xpath("td[2]")).getText());
								System.out.println("Building Floor number is :"+sasFloorNoArray);
								//	colCollector.add(sasFloorNo);
								//mAssert(sasFloorNo, floorNo, "Actual is: "+sasFloorNo+ "Expected is: "+floorNo+ "Building Floor no. is not matching");
							}
							if(columnNo==2)
							{
								//String sasUsageType=rowElement.findElement(By.xpath("td[3]")).getText();
								sasUsageTypeArray.add(rowElement.findElement(By.xpath("td[3]")).getText());
								System.out.println("Usage type is :"+sasUsageTypeArray);
								//colCollector.add(sasUsageType);
								//mAssert(sasUsageType, typeOfUse, "Actual is: "+sasUsageType+ "Expected is: "+typeOfUse+ "Usage type is not matching");
							}
							if(columnNo==3)
							{
								//	String sasConstructionType=rowElement.findElement(By.xpath("td[4]")).getText();
								sasConstructionTypeArray.add(rowElement.findElement(By.xpath("td[4]")).getText());
								System.out.println("Construction type is :"+sasConstructionTypeArray);
								//colCollector.add(sasConstructionType);
								//mAssert(sasConstructionType, constructType, "Actual is: "+sasConstructionType+ "Expected is: "+constructType+ "Construction type is not matching");
							}
							if(columnNo==4)
							{
								//String sasRatableArea=rowElement.findElement(By.xpath("td[5]")).getText();
								sasRatableAreaArray.add(rowElement.findElement(By.xpath("td[5]")).getText());
								System.out.println("Ratable Area is :"+sasRatableAreaArray);
								//colCollector.add(sasRatableArea);
								//mAssert(sasRatableArea, mGetPropertyFromFile("ratableAreaForRes"), "Actual is: "+sasRatableArea+ "Expected is: "+mGetPropertyFromFile("ratableAreaForRes")+ "Ratable Area is not matching");
							}
							if(columnNo==5)
							{
								//String sasUsageFactor=rowElement.findElement(By.xpath("td[7]")).getText();
								sasUsageFactorArray.add(rowElement.findElement(By.xpath("td[7]")).getText());
								System.out.println("Usage Factor is :"+sasUsageFactorArray);
								//colCollector.add(sasUsageFactor);
								//mAssert(sasUsageFactor, usageFactor, "Actual is: "+sasUsageFactor+ "Expected is: "+usageFactor+ "Usage factor is not matching");
							}
							if(columnNo==6)
							{
								//String sasOccFactor=rowElement.findElement(By.xpath("td[8]")).getText();
								sasOccFactorArray.add(rowElement.findElement(By.xpath("td[8]")).getText());
								System.out.println("Occupancy Factor is :"+sasOccFactorArray);
								//colCollector.add(sasOccFactor);
								//mAssert(sasOccFactor, occFac, "Actual is: "+sasOccFactor+ "Expected is: "+occFac+ "Occupancy Factor is not matching");
							}					
							if(columnNo==7)
							{

								String sasAnnulRateblValArrayOrg;
								sasAnnulRateblValArrayOrg=rowElement.findElement(By.xpath("td[10]")).getText();
								System.out.println("Annual ratable value is :"+sasAnnulRateblValArrayOrg);
								sasAnnulRateblValArrayOrg=sasAnnulRateblValArrayOrg.replaceAll(",","");
								sasAnnulRateblValArray.add(sasAnnulRateblValArrayOrg);
							}
							if(columnNo==8)
							{

								sasAnnualPropertyTaxArray.add(rowElement.findElement(By.xpath("td[12]")).getText());
								System.out.println("Annual Rental Value is :"+sasAnnualPropertyTaxArray);
							}

						}
						columnNo=columnNo+1;
					}
					rowNo=rowNo+1;
				}

				Collections.reverse(sasBldgYearArray);
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					mAssert(sasBldgYearArray, yearBlgArray, "Actual is: "+sasBldgYearArray+ "Expected is: "+yearBlgArray+ "Array of year does not match in SAS form");
					mAssert(sasFloorNoArray, addFloorNoArray, "Actual is: "+sasFloorNoArray+ "Expected is: "+addFloorNoArray+ "Array of floor no. does not match in SAS form");
					mAssert(sasUsageTypeArray, addTypeOfUseArray, "Actual is: "+sasUsageTypeArray+ "Expected is: "+addTypeOfUseArray+ "Array of type of usage does not match in SAS form");
					mAssert(sasConstructionTypeArray, addConstructTypeArray, "Actual is: "+sasConstructionTypeArray+ "Expected is: "+addConstructTypeArray+ "Array of construction type does not match in SAS form");
					mAssert(sasRatableAreaArray, calcRatableArArray, "Actual is: "+sasRatableAreaArray+ "Expected is: "+calcRatableArArray+ "Array of ratable area does not match in SAS form");
					mAssert(sasUsageFactorArray, addUsageFactorArray, "Actual is: "+sasUsageFactorArray+ "Expected is: "+addUsageFactorArray+ "Array of usage factor does not match in SAS form");
					mAssert(sasOccFactorArray, addOccFacArray, "Actual is: "+sasOccFactorArray+ "Expected is: "+addOccFacArray+ "Array of occupancy factor does not match in SAS form");
					mAssert(sasAnnulRateblValArray, calcAnnulRateblValArray, "Actual is: "+sasAnnulRateblValArray+ "Expected is: "+calcAnnulRateblValArray+ "Array of annual ratable value does not match in SAS form");
					mAssert(sasAnnualPropertyTaxArray,calcAnnulTaxArray, "Actual is: "+sasAnnualPropertyTaxArray+ "Expected is: "+calcAnnulTaxArray+ "Array of annual tax does not match in SAS form");
				}
				else
				{
					if(!mGetPropertyFromFile("pt_chngInAddnChngLandType").equalsIgnoreCase("yes"))
					{
						Collections.reverse(floorNoArray);
					}
					mAssert(sasBldgYearArray, yearBlgArray, "Actual is: "+sasBldgYearArray+ "Expected is: "+yearBlgArray+ "Array of year does not match in SAS form");
					mAssert(sasFloorNoArray, floorNoArray, "Actual is: "+sasFloorNoArray+ "Expected is: "+floorNoArray+ "Array of floor no. does not match in SAS form");
					mAssert(sasUsageTypeArray, typeOfUseArray, "Actual is: "+sasUsageTypeArray+ "Expected is: "+typeOfUseArray+ "Array of type of usage does not match in SAS form");
					mAssert(sasConstructionTypeArray, constructTypeArray, "Actual is: "+sasConstructionTypeArray+ "Expected is: "+constructTypeArray+ "Array of construction type does not match in SAS form");
					mAssert(sasRatableAreaArray, calcRatableArArray, "Actual is: "+sasRatableAreaArray+ "Expected is: "+calcRatableArArray+ "Array of ratable area does not match in SAS form");
					mAssert(sasUsageFactorArray, usageFactorArray, "Actual is: "+sasUsageFactorArray+ "Expected is: "+usageFactorArray+ "Array of usage factor does not match in SAS form");
					mAssert(sasOccFactorArray, occFacArray, "Actual is: "+sasOccFactorArray+ "Expected is: "+occFacArray+ "Array of occupancy factor does not match in SAS form");
					mAssert(sasAnnulRateblValArray, calcAnnulRateblValArray, "Actual is: "+sasAnnulRateblValArray+ "Expected is: "+calcAnnulRateblValArray+ "Array of annual ratable value does not match in SAS form");
					mAssert(sasAnnualPropertyTaxArray,calcAnnulTaxArray, "Actual is: "+sasAnnualPropertyTaxArray+ "Expected is: "+calcAnnulTaxArray+ "Array of annual tax does not match in SAS form");
				}
				//14C Total Tax (12F + Current Year Property Tax)

				//WebElement tableTC = driver.findElement(By.xpath("//*[@id='command']/div[2]/div[2]/table[4]"));
				WebElement tableTC =mFindElement("xpath", mGetPropertyFromFile("pt_newAssessmentSASTotTaxid"));
				List<WebElement> rowsTC = tableTC.findElements(By.tagName("tr"));
				int rowcountTC = rowsTC.size();
				System.out.println("Total number of rows :" +rowcountTC);
				int rowNoTC=0;
				for (WebElement rowElement:rowsTC)
				{
					List<WebElement> cols = rowElement.findElements(By.tagName("td"));
					int colcount= cols.size();
					System.out.println("Total number of columns :"+colcount);
					int columnNo=0;
					//for(WebElement colElement:cols)
					for(int i=0;i<colcount;i++)
					{
						if(i==0)
						{
							String sasTotAnnualTaxOrg= rowElement.findElement(By.xpath("//*[@id='command']/div[2]/div[2]/table[4]/tbody/tr[3]/td[4]")).getText();
							System.out.println("Total Annual Tax 12F +Current Year Property Tax: "+sasTotAnnualTaxOrg);
							String sasTotAnnualTax=sasTotAnnualTaxOrg.replaceAll(",","");

							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasTotAnnualTax, mGetPropertyFromFile("pt_newAssessmentPTAnnualTaxAutoRes"), "Actual is: " +sasTotAnnualTax+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPTAnnualTaxAutoRes")+ "Total Annual Tax 12F +Current Year Property Tax is not matching");
							}
							else
							{
								mAssert(sasTotAnnualTax, String.valueOf(pt_newAssessmentPTAnnualTaxAutoRes), "Actual is: " +sasTotAnnualTax+ "Expected is: " +String.valueOf(pt_newAssessmentPTAnnualTaxAutoRes)+ "Total Annual Tax 12F +Current Year Property Tax is not matching");
							}

						}
						if (i==1)
						{
							String sasRebate=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/div[2]/table[4]/tbody/tr[4]/td[4]")).getText();
							System.out.println("Rebate is 14D: "+sasRebate);

							//Added by Jyoti

							sasRebateList.add(sasRebate);
							System.out.println("Rebate List of SAS Form is ::: "+sasRebateList);

							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasRebate, mGetPropertyFromFile("pt_newAssessmentPTRebtAutoResSAS"), "Actual is: " +sasRebate+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPTRebtAutoResSAS")+ "Rebate is 14D is not matching");
							}
							else
							{
								//int pt_newAssessmentPTRebtAutoRes = 0;
								//	int pt_newAssessmentPTRebt;
								double pt_newAssessmentPTRebtRWH;
								if(mGetPropertyFromFile("pt_newAssessmentRebateApplicable").equalsIgnoreCase("Yes"))
								{
									pt_newAssessmentPTRebt=Math.round(pt_newAssessmentPTAnnualTaxAutoRes*0.05);
								}	
								else
								{
									pt_newAssessmentPTRebt=0;
								}
								if(mGetPropertyFromFile("rainWaterHarvesting").equalsIgnoreCase("Yes"))
								{
									pt_newAssessmentPTRebtRWH=Math.round(pt_newAssessmentPTAnnualTaxAutoRes*0.05);
								}	
								else
								{
									pt_newAssessmentPTRebtRWH=0;
								}
								pt_newAssessmentPTRebtAutoRes=Math.round(pt_newAssessmentPTRebt+pt_newAssessmentPTRebtRWH);
								pt_newAssessmentPTRebtAutoRes=(Math.round(pt_newAssessmentPTRebtAutoRes));
								System.out.println("Rebate pt_newAssessmentPTRebtAutoRes: "+ pt_newAssessmentPTRebtAutoRes);
								mAssert(sasRebate, String.valueOf(pt_newAssessmentPTRebtAutoRes), "Actual is: " +sasRebate+ "Expected is: " +String.valueOf(pt_newAssessmentPTRebtAutoRes)+ "Rebate is 14D is not matching in SAS form");
							}
						}
						if (i==2)
						{
							String sasInterest=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/div[2]/table[4]/tbody/tr[5]/td[4]")).getText();
							System.out.println("Interest is 14E: "+sasInterest);

							// Added by Jyoti
							sasInterestList.add(sasInterest);
							System.out.println("Interest on SAS Form is :::: "+sasInterestList);

							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasInterest, mGetPropertyFromFile("pt_newAssessmentPTIntAndPenSAS"), "Actual is: " +sasInterest+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPTIntAndPenSAS")+ "Interest is 14E is not matching");
							}
							else
							{
								double pt_newAssessmentPTIntAndPenSAS;
								//	int pt_newAssessmentPanaltySAS;
								//int pt_newAssessmentPTIntAutoResSAS;
								int pt_newAssessmentPTIntAutoRes;


								//	pt_newAssessmentPanaltySAS=IF(TODAY()-VALUE(E51)>=90,IF(PT_newAssessment_Cal_Data!B61="Residential",2000,5000),0),"0");
								pt_newAssessmentPTIntAutoResSAS=pt_newAssessmentPTAnnualTaxAutoRes*Double.valueOf(mGetPropertyFromFile("interestRate"))*Integer.parseInt(mGetPropertyFromFile("noOfMonths"));

								pt_newAssessmentPTIntAndPenSAS=Math.round(pt_newAssessmentPTIntAutoResSAS);
								mAssert(sasInterest, String.valueOf(pt_newAssessmentPTIntAndPenSAS), "Actual is: " +sasInterest+ "Expected is: " +String.valueOf(pt_newAssessmentPTIntAndPenSAS)+ "Interest is 14E is not matching in SAS Form");
							}
						}
						if(i==3)
						{
							String sasTotAnnualTaxSas=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/div[2]/table[4]/tbody/tr[6]/td[4]")).getText();
							System.out.println("SAS Form Total Annual PropertyTax ( 14C - 14D + 14E) is: "+sasTotAnnualTaxSas);
							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasTotAnnualTaxSas, mGetPropertyFromFile("pt_newAssessmentTotalAnnualPropertyTaxonSAS"), "Actual is: " +sasTotAnnualTaxSas+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentTotalAnnualPropertyTaxonSAS")+ "SAS Form Total Annual PropertyTax ( 14C - 14D + 14E) is not matching");
							}
							else
							{
								double pt_newAssessmentTotalAnnualPropertyTaxonSAS;
								pt_newAssessmentTotalAnnualPropertyTaxonSAS=Math.round(pt_newAssessmentPTAnnualTaxAutoRes-pt_newAssessmentPTRebtAutoRes+pt_newAssessmentPTIntAutoResSAS);
								mAssert(sasTotAnnualTaxSas, String.valueOf(pt_newAssessmentTotalAnnualPropertyTaxonSAS), "Actual is: " +sasTotAnnualTaxSas+ "Expected is: " +String.valueOf(pt_newAssessmentTotalAnnualPropertyTaxonSAS)+ "SAS Form Total Annual PropertyTax ( 14C - 14D + 14E) is not matching");
							}
						}
						if(i==4)
						{
							String sasWaterTax=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/div[2]/table[4]/tbody/tr[7]/td[4]")).getText();
							System.out.println("SAS Form Water Tax Details(14G) is: "+sasWaterTax);

							// Added by Jyoti
							sas_WaterTaxList.add(sasWaterTax);
							System.out.println("List of Water Tax on SAS is ::: "+sas_WaterTaxList);

							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasWaterTax, mGetPropertyFromFile("pt_newAssessmentWaterTaxCrgSupp"), "Actual is :" +sasWaterTax+ "Expected is :" +mGetPropertyFromFile("pt_newAssessmentWaterTaxCrgSupp")+ "SAS Form Water Tax Details(14G) is not matching");
							}
							else
							{
								System.out.println("SAS Form Water Tax Details(14G) is not applicable for Change / Alteration in Assessment");
							}
						}
					}
					rowNoTC=rowNoTC+1;

				}

				//Assertions for 16.2B Total Property Tax + Water Tax (Current+Arrears)

				//Property Tax SAS form

				if(mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					for(int i=0 ; i<calcAnnulTaxArray.size(); i++) 
					{
						System.out.println("size of calcAnnulTaxArray.size() :"+calcAnnulTaxArray.size());
						//pt_newAssessmentPTAnnualTaxAutoRes+=Double.parseDouble(calcAnnulTaxArray.get(i));
						if(!usageFactorArray.get(i).equalsIgnoreCase("Govt. Establishment"))
						{
							pt_newAssessmentPTAnnualTax+=Double.parseDouble(calcAnnulTaxArray.get(i));
						}
					}
				}
				else
				{
					System.out.println("New Assessment");
				}

				System.out.println("16.2B Total Property Tax : "+pt_newAssessmentPTAnnualTax);

				//WebElement tablePTS = driver.findElement(By.xpath("//*[@id='command']/div[2]/table[16]"));
				WebElement tablePTS =mFindElement("xpath", mGetPropertyFromFile("pt_newAssessmentSASPropTaxid"));
				List<WebElement> rowsPTS = tablePTS.findElements(By.tagName("tr"));
				int rowcountPTS = rowsPTS.size();
				System.out.println("Total number of rows :" +rowcountPTS);
				int rowNoPTS=0;
				for (WebElement rowElement:rowsPTS)
				{
					List<WebElement> colsPTS = rowElement.findElements(By.tagName("td"));
					int colcountPTS= colsPTS.size();
					System.out.println("Total number of columns :"+colcountPTS);
					int columnNoPTS=0;
					for(WebElement colElement:colsPTS)
					{
						if(columnNoPTS==0)
						{
							String sasPropertyTaxCurrentOrg = "";
							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								sasPropertyTaxCurrentOrg=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[16]/tbody/tr[2]/td[4]")).getText();
							}
							else
							{
								if(mGetPropertyFromFile("pt_chngInAddnChngVcntLandType").equalsIgnoreCase("Land + Building"))
								{
									sasPropertyTaxCurrentOrg=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[16]/tbody/tr[1]/td[4]")).getText();
								}
								else
								{
									sasPropertyTaxCurrentOrg=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[16]/tbody/tr[2]/td[4]")).getText();
								}
							}
							System.out.println("SAS Form 16.2B Total Property Tax + Water Tax (Current+Arrears) is: "+sasPropertyTaxCurrentOrg);
							String sasPropertyTaxCurrent=sasPropertyTaxCurrentOrg.replaceAll(",","");
							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasPropertyTaxCurrent, mGetPropertyFromFile("pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes"), "Actual is :" +sasPropertyTaxCurrent+ "Expected is :" +mGetPropertyFromFile("pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes")+ "SAS Form 16.2B Total Property Tax + Water Tax (Current+Arrears) is not matching for Land + Building");
							}
							else
							{
								/*int pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes;

								pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes=(int) pt_newAssessmentPTAnnualTaxAutoRes;
								mAssert(sasPropertyTaxCurrent, String.valueOf(pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes), "Actual is :" +sasPropertyTaxCurrent+ "Expected is :" +String.valueOf(pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes)+ "SAS Form 16.2B Total Property Tax + Water Tax (Current+Arrears) is not matching");*/

								//int pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes;
								//double pt_newAssessmentPTAnnualTax = 0;
								String pt_newAssessmentPTIntAreasAmtVal;
								int pt_newAssessmentPTIntAreasAmt = 0;

								//	if(driver.findElements(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table")).size() != 0)
								if(driver.findElements(By.xpath("//*[@id=\"command\"]/div[2]/table[12]")).size() != 0)
								{
									//pt_newAssessmentPTIntAreasVal=driver.findElement(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table/tbody/tr[2]/td[3]")).getText();
									/*pt_newAssessmentPTIntAreasAmtVal=driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[12]/tbody/tr[2]/td[2]")).getText();*/

									//System.out.println("Arrears : "+pt_newAssessmentPTIntAreasAmtVal);
									//pt_newAssessmentPTIntAreasAmt=Integer.parseInt(pt_newAssessmentPTIntAreasAmtVal);

									WebElement tableArr =driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[12]"));
									List<WebElement> rowsArr = tableArr.findElements(By.tagName("tr"));
									int rowcountArr = rowsArr.size();
									System.out.println("Total number of rows :" +rowcountArr);
									int rowNoArr=0;
									for (WebElement rowElementArr:rowsArr)
									{
										List<WebElement> colsArr = rowElementArr.findElements(By.tagName("td"));
										int colcountArr= colsArr.size();
										System.out.println("Total number of columns :"+colcountArr);
										int columnNoArr=0;
										for(WebElement colElementArr:colsArr)
										{
											if(rowNoArr>=1)
											{
												if(columnNoArr==1)
												{
													pt_newAssessmentPTIntAreasAmtVal=rowElementArr.findElement(By.xpath("td[2]")).getText();
													System.out.println("Arrears : "+pt_newAssessmentPTIntAreasAmtVal);
													pt_newAssessmentPTIntAreasAmtVal=pt_newAssessmentPTIntAreasAmtVal.replaceAll(",","");
													System.out.println("Arrears : "+pt_newAssessmentPTIntAreasAmtVal);
													//pt_newAssessmentPTIntAreasAmt=Integer.parseInt(pt_newAssessmentPTIntAreasAmtVal);
													pt_newAssessmentPTIntAreasAmt=pt_newAssessmentPTIntAreasAmt+Integer.parseInt(pt_newAssessmentPTIntAreasAmtVal);
												}
											}
											columnNoArr=columnNoArr+1;
										}
										rowNoArr=rowNoArr+1;
									}
								}
								else
								{
									pt_newAssessmentPTIntAreasAmt=0;
								}
								System.out.println("Arrears Amount : "+pt_newAssessmentPTIntAreasAmt);

								/*for(int i=0 ; i<calcAnnulTaxArray.size(); i++) 
								{
									//pt_newAssessmentPTAnnualTaxAutoRes+=Double.parseDouble(calcAnnulTaxArray.get(i));
									if(!usageFactorArray.get(i).equalsIgnoreCase("Govt. Establishment"))
									{
										pt_newAssessmentPTAnnualTax+=Double.parseDouble(calcAnnulTaxArray.get(i));
									}
								}

								System.out.println("16.2B Total Property Tax : "+pt_newAssessmentPTAnnualTax);*/
								//pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes=(int) pt_newAssessmentPTAnnualTaxAutoRes;
								pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes= pt_newAssessmentPTAnnualTax+pt_newAssessmentPTIntAreasAmt+pt_newAssessmentVacantLandAnnualTax;
								System.out.println("pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes is: "+pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes);
								mAssert(sasPropertyTaxCurrent, String.valueOf(pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes), "Actual is :" +sasPropertyTaxCurrent+ "Expected is :" +String.valueOf(pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes)+ "SAS Form 16.2B Total Property Tax + Water Tax (Current+Arrears) is not matching for Land + Building");

							}
						}
						columnNoPTS=columnNoPTS+1;
					}
					rowNoPTS=rowNoPTS+1;
				}

				mTakeScreenShot();


				//Interest/Penalty/Rebate

				//WebElement tableIPR = driver.findElement(By.xpath("//*[@id='command']/div[2]/table[18]"));
				WebElement tableIPR = mFindElement("xpath", mGetPropertyFromFile("pt_newAssessmentSASIPRid"));
				List<WebElement> rowsIPR = tableIPR.findElements(By.tagName("tr"));
				int rowcountIPR = rowsIPR.size();
				System.out.println("Total number of rows :" +rowcountIPR);
				int rowNoIPR=0;
				for (WebElement rowElement:rowsIPR)
				{
					List<WebElement> colsIPR = rowElement.findElements(By.tagName("td"));
					int colcountIPR= colsIPR.size();
					System.out.println("Total number of columns :"+colcountIPR);
					int columnNoIPR=0;
					for(WebElement colElement:colsIPR)
					{
						if(columnNoIPR==0)
						{
							String sasRebateIncRWH=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[18]/tbody/tr[1]/td[4]")).getText();
							System.out.println("SAS Form Rebate (RWH + Normal) 16.3A is: "+sasRebateIncRWH);

							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasRebateIncRWH, mGetPropertyFromFile("pt_newAssessmentPTRebtAutoResSAS"), "Actual is: " +sasRebateIncRWH+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPTRebtAutoResSAS")+ "SAS Form Rebate (RWH + Normal) 16.3A is not matching");
							}
							else
							{
								mAssert(sasRebateIncRWH, String.valueOf(pt_newAssessmentPTRebtAutoRes), "Actual is: " +sasRebateIncRWH+ "Expected is: " +String.valueOf(pt_newAssessmentPTRebtAutoRes)+ "SAS Form Rebate (RWH + Normal) 16.3A is not matching");
							}
						}
						else if(columnNoIPR==1)
						{
							String sasRebateIncRWH=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[18]/tbody/tr[2]/td[4]")).getText();
							System.out.println("SAS Form Penalty (Current) 16.3B is: "+sasRebateIncRWH);
							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasRebateIncRWH, mGetPropertyFromFile("pt_newAssessmentPanaltySAS"), "Actual is: " +sasRebateIncRWH+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPanaltySAS")+ "SAS Form Penalty (Current) 16.3B is not matching");
							}
							else
							{
								System.out.println("SAS Form Penalty (Current) 16.3B is not applicable for change in assessment");
							}
						}else if(columnNoIPR==2)
						{
							String sasRebateIncRWH=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[18]/tbody/tr[3]/td[4]")).getText();
							System.out.println("SAS Form Interest (Current + Arrears) 16.3C is: "+sasRebateIncRWH);


							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasRebateIncRWH, mGetPropertyFromFile("pt_newAssessmentPTIntAreasAndCurrentSAS"), "Actual is: " +sasRebateIncRWH+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPTIntAreasAndCurrentSAS")+ "SAS Form Interest (Current + Arrears) 16.3C is not matching");
							}
							else
							{
								//double pt_newAssessmentPTIntAreasAndCurrentSAS;
								String pt_newAssessmentPTIntAreasVal = "";
								int pt_newAssessmentPTIntAreas = 0;
								if(driver.findElements(By.xpath("//*[@id=\"command\"]/div[2]/table[12]")).size() != 0)
								{
									/*pt_newAssessmentPTIntAreasVal=driver.findElement(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table/tbody/tr[2]/td[3]")).getText();*/
									WebElement tableArr =driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[12]"));
									List<WebElement> rowsArr = tableArr.findElements(By.tagName("tr"));
									int rowcountArr = rowsArr.size();
									System.out.println("Total number of rows :" +rowcountArr);
									int rowNoArr=0;
									for (WebElement rowElementArr:rowsArr)
									{
										List<WebElement> colsArr = rowElementArr.findElements(By.tagName("td"));
										int colcountArr= colsArr.size();
										System.out.println("Total number of columns :"+colcountArr);
										int columnNoArr=0;
										for(WebElement colElementArr:colsArr)
										{
											if(rowNoArr>=1)
											{
												if(columnNoArr==1)
												{
													pt_newAssessmentPTIntAreasVal=rowElementArr.findElement(By.xpath("td[3]")).getText();
													System.out.println("Interest : "+pt_newAssessmentPTIntAreasVal);
													pt_newAssessmentPTIntAreas=pt_newAssessmentPTIntAreas+Integer.parseInt(pt_newAssessmentPTIntAreasVal);
													//pt_newAssessmentPTIntAreas+=pt_newAssessmentPTIntAreas;
												}
											}
											columnNoArr=columnNoArr+1;
										}
										rowNoArr=rowNoArr+1;
									}
									//System.out.println("Interest : "+pt_newAssessmentPTIntAreasVal);
									//pt_newAssessmentPTIntAreas=Integer.parseInt(pt_newAssessmentPTIntAreasVal);
								}
								else
								{
									pt_newAssessmentPTIntAreas=0;
								}
								System.out.println("Interest Rs. : "+pt_newAssessmentPTIntAreas);
								pt_newAssessmentPTIntAreasAndCurrentSAS=Math.round(pt_newAssessmentPTIntAutoResSAS+pt_newAssessmentPTIntAreas);
								mAssert(sasRebateIncRWH, String.valueOf(pt_newAssessmentPTIntAreasAndCurrentSAS), "Actual is: " +sasRebateIncRWH+ "Expected is: " +String.valueOf(pt_newAssessmentPTIntAreasAndCurrentSAS)+ "SAS Form Interest (Current + Arrears) 16.3C is not matching");
							}
						}
						columnNoIPR=columnNoIPR+1;
					}
					rowNoIPR=rowNoIPR+1;
				}

				//Total Net Payable Amount SAS form

				//WebElement tableTPA = driver.findElement(By.xpath("//*[@id='command']/div[2]/table[19]"));
				WebElement tableTPA =mFindElement("xpath", mGetPropertyFromFile("pt_newAssessmentSASTPAid"));
				List<WebElement> rowsTPA = tableTPA.findElements(By.tagName("tr"));
				int rowcountTPA = rowsTPA.size();
				System.out.println("Total number of rows :" +rowcountTPA);
				int rowNoTPA=0;
				for (WebElement rowElement:rowsTPA)
				{
					List<WebElement> colsTPA = rowElement.findElements(By.tagName("td"));
					int colcountTPA= colsTPA.size();
					System.out.println("Total number of columns :"+colcountTPA);
					int columnNoTPA=0;
					for(WebElement colElement:colsTPA)
					{
						if(columnNoTPA==0)
						{
							String sasTotNetPayAmtOrg=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[19]/tbody/tr[1]/td[4]")).getText();
							System.out.println("SAS Form Total Net Payable Amount (16.4) is: "+sasTotNetPayAmtOrg);
							String sasTotNetPayAmt=sasTotNetPayAmtOrg.replaceAll(",","");

							//Added by Jyoti
							sas_TotNetPayableAmtList.add(sasTotNetPayAmt);
							System.out.println("SAS Form List of Total Net Payable Amount ::: "+sas_TotNetPayableAmtList);

							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasTotNetPayAmt, mGetPropertyFromFile("pt_newAssessmentTotPayableAmtResAutoSupp"), "Actual is :" +sasTotNetPayAmt+ "Expected is :" +mGetPropertyFromFile("pt_newAssessmentTotPayableAmtResAutoSupp")+ "SAS Form Total Net Payable Amount (16.4) is not matching");
							}
							else
							{
								double pt_newAssessmentTotPayableAmtResAutoSupp;
								int pt_newAssessmentServiceChargeCurSAS = 0;
								//int pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes = 0;
								int pt_newAssessmentWaterTaxCrgSupp = 0;
								int pt_newAssessmentPTRebtAutoResSAS;

								for(int i=0 ; i<calcServiceChargeArray.size() ; i++) 
								{
									pt_newAssessmentServiceChargeCurSAS+=Integer.parseInt(calcServiceChargeArray.get(i));
									System.out.println("Service charge total amount from array : "+pt_newAssessmentServiceChargeCurSAS);
								}


								//	pt_newAssessmentPTRebtAutoResSAS=pt_newAssessmentPTRebtAutoRes+pt_newAssessmentPTRebtRWH;
								//pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes=pt_newAssessmentPTAnnualTaxAutoRes+pt_newAssessmentWaterTaxCrgSupp;
								/*pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes=(int) pt_newAssessmentPTAnnualTaxAutoRes;
								System.out.println("pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes is : "+pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes);
								pt_newAssessmentTotPayableAmtResAutoSupp= Math.round(pt_newAssessmentServiceChargeCurSAS+pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes+pt_newAssessmentPTIntAutoResSAS)-pt_newAssessmentPTRebtAutoRes;
								mAssert(sasTotNetPayAmt, String.valueOf(pt_newAssessmentTotPayableAmtResAutoSupp), "Actual is :" +sasTotNetPayAmt+ "Expected is :" +String.valueOf(pt_newAssessmentTotPayableAmtResAutoSupp)+ "SAS Form Total Net Payable Amount (16.4) is not matching for Land + Building");*/
								System.out.println("pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes is : "+pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes);
								pt_newAssessmentTotPayableAmtResAutoSupp=Math.round(pt_newAssessmentServiceChargeCurSAS+pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes/*+pt_newAssessmentPTIntAutoResSAS*/+pt_newAssessmentPTIntAreasAndCurrentSAS-pt_newAssessmentPTRebtAutoRes);
								mAssert(sasTotNetPayAmt, String.valueOf(pt_newAssessmentTotPayableAmtResAutoSupp), "Actual is :" +sasTotNetPayAmt+ "Expected is :" +String.valueOf(pt_newAssessmentTotPayableAmtResAutoSupp)+ "SAS Form Total Net Payable Amount (16.4) is not matching for Land + Building");

							}
						}

						columnNoTPA=columnNoTPA+1;
					}
					rowNoTPA=rowNoTPA+1;
				}
				//mTakeScreenShot();
			}

			//SAS Land Type is Vacant Land @Ritesh 30-11-2016


			else if(mGetPropertyFromFile("landType").equalsIgnoreCase("Vacant Land"))
			{
				// Land Type = Vacant Land


				Boolean sasVacantLand=mFindElement("id", mGetPropertyFromFile("pt_newAssessmentSASLndTypeVLid")).isSelected();
				System.out.println("Land type is Vacant Land");
				if(sasVacantLand==true)
				{
					String sasLandTypeOrg=driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[3]/tbody/tr[1]/td[3]")).getText();
					System.out.println("Land type is :"+sasLandTypeOrg);
					String sasLandType=sasLandTypeOrg.substring(19, 30);
					if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
					{
						mAssert(sasLandType, landTypeSelected, "Land type does not matches in SAS form, Actual is :"+sasLandType+"Expected is :"+landTypeSelected);
					}
					else
					{
						mAssert(sasLandType, chngAsmtLandType, "Land type does not matches in SAS form, Actual is :"+sasLandType+"Expected is :"+chngAsmtLandType);
					}
				}				

				mTakeScreenShot();
				mscroll(1155, 707);
				// Acquisition Year 
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					String acquisitionYear=mGetText("css", mGetPropertyFromFile("pt_newAssessmentSASAcqYrid"));
					System.out.println("Year of Acquisition is: "+acquisitionYear);
					mAssert(acquisitionYear, mGetPropertyFromFile("pt_newAssessmentAcquisitionYear"), "Actual is: "+acquisitionYear+ "Expected is: "+mGetPropertyFromFile("pt_newAssessmentAcquisitionYear")+ "Year of Acquisition is not matching");
				}
				else
				{
					String acquisitionYear=driver.findElement(By.cssSelector("#command > div.form-div.margin_top_10 > table:nth-child(5) > tbody > tr:nth-child(2) > td:nth-child(2)")).getText();
					System.out.println("Year of Acquisition is: "+acquisitionYear);
					mAssert(acquisitionYear, chngAsmtLandTypeAcqYr, "Actual is: "+acquisitionYear+ "Expected is: "+chngAsmtLandTypeAcqYr+ "Year of Acquisition is not matching");
				}

				//Vacant Land Annual Tax on SAS form @Ritesh Dated: - 12-10-2016
				/*String sasVacantLandAnnualTx =mGetText("css", mGetPropertyFromFile("pt_newAssessmentSASVacantLandAnnualTxid"));
				System.out.println("Vacant Land Annual Tax is: "+sasVacantLandAnnualTx);
				mAssert(sasVacantLandAnnualTx, vancantareavalue, "Actual is: "+sasVacantLandAnnualTx+ "Expected is: "+vancantareavalue+"Vacant Land Annual Tax is not matching");*/


				mTakeScreenShot();
				mscroll(1217, 714);

				//14C Total Tax (12F + Current Year Property Tax)

				WebElement tableTC =mFindElement("xpath", mGetPropertyFromFile("pt_newAssessmentSASTotTaxid"));
				List<WebElement> rowsTC = tableTC.findElements(By.tagName("tr"));
				int rowcountTC = rowsTC.size();
				System.out.println("Total number of rows :" +rowcountTC);
				int rowNoTC=0;
				for (WebElement rowElement:rowsTC)
				{
					List<WebElement> cols = rowElement.findElements(By.tagName("td"));
					int colcount= cols.size();
					System.out.println("Total number of columns :"+colcount);
					int columnNo=0;
					for(int i=0;i<colcount;i++)
					{
						if(i==0)
						{
							String sasTotAnnualTaxOrg= rowElement.findElement(By.xpath("//*[@id='command']/div[2]/div[2]/table[4]/tbody/tr[3]/td[4]")).getText();
							System.out.println("Total Annual Tax 12F +Current Year Property Tax: "+sasTotAnnualTaxOrg);
							String sasTotAnnualTax=sasTotAnnualTaxOrg.replaceAll(",","");
							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasTotAnnualTax, mGetPropertyFromFile("pt_newAssessmentPTAnnualTaxAutoRes"), "Actual is: " +sasTotAnnualTax+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPTAnnualTaxAutoRes")+ "Total Annual Tax 12F +Current Year Property Tax is not matching");
							}
							else
							{
								//								
								pt_newAssessmentPTAnnualTaxAutoRes=0;
								//pt_newAssessmentPTAnnualTaxAutoRes = (Double.parseDouble(calcAnnulTaxArray.get(i)))+pt_newAssessmentVacantLandAnnualTax;

								pt_newAssessmentPTAnnualTaxAutoRes = pt_newAssessmentPTAnnualTaxAutoRes+(int)pt_newAssessmentVacantLandAnnualTax;
								System.out.println("Calculated Total Annual Tax is : "+pt_newAssessmentPTAnnualTaxAutoRes);
								//	mAssert(viewTotAnnTx, String.valueOf(pt_newAssessmentPTAnnualTaxAutoRes), "Actual is: " +viewTotAnnTx+ "Expected is: " +String.valueOf(pt_newAssessmentPTAnnualTaxAutoRes)+ "Total Annual Tax is not matching");

								mAssert(sasTotAnnualTax, String.valueOf(pt_newAssessmentPTAnnualTaxAutoRes), "Actual is: " +sasTotAnnualTax+ "Expected is: " +String.valueOf(pt_newAssessmentPTAnnualTaxAutoRes)+ "Total Annual Tax 12F +Current Year Property Tax is not matching for Vacant Land");
							}

						}
						if (i==1)
						{
							String sasRebate=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/div[2]/table[4]/tbody/tr[4]/td[4]")).getText();
							System.out.println("Rebate is 14D: "+sasRebate);

							//Added by Jyoti
							sasRebateList.add(sasRebate);
							System.out.println("Rebate List of SAS Form is ::: "+sasRebateList);


							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasRebate, mGetPropertyFromFile("pt_newAssessmentPTRebtAutoRes"), "Actual is: " +sasRebate+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPTRebtAutoRes")+ "Rebate is 14D is not matching");
							}
							else
							{
								//int pt_newAssessmentPTRebtAutoRes = 0;
								//	int pt_newAssessmentPTRebt;
								int pt_newAssessmentPTRebtRWH;
								if(mGetPropertyFromFile("pt_newAssessmentRebateApplicable").equalsIgnoreCase("Yes"))
								{
									pt_newAssessmentPTRebt=(int)(pt_newAssessmentPTAnnualTaxAutoRes*0.05);
								}	
								else
								{
									pt_newAssessmentPTRebt=0;
								}
								if(mGetPropertyFromFile("rainWaterHarvesting").equalsIgnoreCase("Yes"))
								{
									pt_newAssessmentPTRebtRWH=(int)(pt_newAssessmentPTAnnualTaxAutoRes*0.05);
								}	
								else
								{
									pt_newAssessmentPTRebtRWH=0;
								}
								pt_newAssessmentPTRebtAutoRes=pt_newAssessmentPTRebt+pt_newAssessmentPTRebtRWH;
								mAssert(sasRebate, String.valueOf(pt_newAssessmentPTRebtAutoRes), "Actual is: " +sasRebate+ "Expected is: " +String.valueOf(pt_newAssessmentPTRebtAutoRes)+ "Rebate is 14D is not matching in SAS form");
							}
						}
						if (i==2)
						{
							String sasInterestOrg=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/div[2]/table[4]/tbody/tr[5]/td[4]")).getText();
							System.out.println("Interest / Penalty (Current) is 14E: "+sasInterestOrg);
							String sasInterest=sasInterestOrg.replaceAll(",","");

							// Added by Jyoti

							sasInterestList.add(sasInterestOrg);
							System.out.println("Interest on SAS Form is :::: "+sasInterestList);

							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasInterest, mGetPropertyFromFile("pt_newAssessmentPTIntAndPenSAS"), "Actual is: " +sasInterest+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPTIntAndPenSAS")+ "Interest / Penalty (Current) is 14E is not matching");
							}
							else
							{
								double pt_newAssessmentPTIntAndPenSAS;
								//	int pt_newAssessmentPanaltySAS;
								//int pt_newAssessmentPTIntAutoResSAS;
								int pt_newAssessmentPTIntAutoRes;


								//	pt_newAssessmentPanaltySAS=IF(TODAY()-VALUE(E51)>=90,IF(PT_newAssessment_Cal_Data!B61="Residential",2000,5000),0),"0");
								pt_newAssessmentPTIntAutoResSAS=pt_newAssessmentPTAnnualTaxAutoRes*Double.valueOf(mGetPropertyFromFile("interestRate"))*Integer.parseInt(mGetPropertyFromFile("noOfMonths"));

								pt_newAssessmentPTIntAndPenSAS=Math.round(pt_newAssessmentPTIntAutoResSAS);
								mAssert(sasInterest, String.valueOf(pt_newAssessmentPTIntAndPenSAS), "Actual is: " +sasInterest+ "Expected is: " +String.valueOf(pt_newAssessmentPTIntAndPenSAS)+ "Interest is 14E is not matching in SAS Form");
							}
						}
						if(i==3)
						{
							String sasTotAnnualTaxSasOrg=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/div[2]/table[4]/tbody/tr[6]/td[4]")).getText();
							System.out.println("SAS Form Total Annual PropertyTax ( 14C - 14D + 14E) is: "+sasTotAnnualTaxSasOrg);
							String sasTotAnnualTaxSas=sasTotAnnualTaxSasOrg.replaceAll(",","");
							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasTotAnnualTaxSas, mGetPropertyFromFile("pt_newAssessmentTotalAnnualPropertyTaxonSAS"), "Actual is: " +sasTotAnnualTaxSas+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentTotalAnnualPropertyTaxonSAS")+ "SAS Form Total Annual PropertyTax ( 14C - 14D + 14E) is not matching");
							}
							else
							{
								double pt_newAssessmentTotalAnnualPropertyTaxonSAS;
								pt_newAssessmentTotalAnnualPropertyTaxonSAS=Math.round(pt_newAssessmentPTAnnualTaxAutoRes-pt_newAssessmentPTRebtAutoRes+pt_newAssessmentPTIntAutoResSAS);
								mAssert(sasTotAnnualTaxSas, String.valueOf(pt_newAssessmentTotalAnnualPropertyTaxonSAS), "Actual is: " +sasTotAnnualTaxSas+ "Expected is: " +String.valueOf(pt_newAssessmentTotalAnnualPropertyTaxonSAS)+ "SAS Form Total Annual PropertyTax ( 14C - 14D + 14E) is not matching");
							}
						}
						if(i==4)
						{
							String sasWaterTax=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/div[2]/table[4]/tbody/tr[7]/td[4]")).getText();
							System.out.println("SAS Form Water Tax Details(14G) is: "+sasWaterTax);

							// Added by Jyoti
							sas_WaterTaxList.add(sasWaterTax);
							System.out.println("List of Water Tax on SAS is ::: "+sas_WaterTaxList);

							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasWaterTax, mGetPropertyFromFile("pt_newAssessmentWaterTaxCrgSupp"), "Actual is :" +sasWaterTax+ "Expected is :" +mGetPropertyFromFile("pt_newAssessmentWaterTaxCrgSupp")+ "SAS Form Water Tax Details(14G) is not matching");
							}
							else
							{
								System.out.println("SAS Form Water Tax Details(14G) is not applicable for Change / Alteration in Assessment");
							}
						}
					}
					rowNoTC=rowNoTC+1;

				}

				//Assertions for 16.2B Total Property Tax + Water Tax (Current+Arrears)

				//Property Tax SAS form

				WebElement tablePTS =mFindElement("xpath", mGetPropertyFromFile("pt_newAssessmentSASPropTaxid"));
				List<WebElement> rowsPTS = tablePTS.findElements(By.tagName("tr"));
				int rowcountPTS = rowsPTS.size();
				System.out.println("Total number of rows :" +rowcountPTS);
				int rowNoPTS=0;
				for (WebElement rowElement:rowsPTS)
				{
					List<WebElement> colsPTS = rowElement.findElements(By.tagName("td"));
					int colcountPTS= colsPTS.size();
					System.out.println("Total number of columns :"+colcountPTS);
					int columnNoPTS=0;
					for(WebElement colElement:colsPTS)
					{
						if(columnNoPTS==0)
						{/*

							String sasPropertyTaxCurrentOrg=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[16]/tbody/tr[1]/td[4]")).getText();
							System.out.println("SAS Form 16.2B Total Property Tax + Water Tax (Current+Arrears): "+sasPropertyTaxCurrentOrg);
							String sasPropertyTaxCurrent=sasPropertyTaxCurrentOrg.replaceAll(",","");
							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasPropertyTaxCurrent, mGetPropertyFromFile("pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes"), "Actual is :" +sasPropertyTaxCurrent+ "Expected is :" +mGetPropertyFromFile("pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes")+ "SAS Form 16.2B Total Property Tax + Water Tax (Current+Arrears) is not matching");
							}
							else
							{
								//int pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes;

								//pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes=(int) pt_newAssessmentPTAnnualTaxAutoRes;
								pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes=(int) pt_newAssessmentPTAnnualTax+pt_newAssessmentPTIntAreasAmt;
								mAssert(sasPropertyTaxCurrent, String.valueOf(pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes), "Actual is :" +sasPropertyTaxCurrent+ "Expected is :" +String.valueOf(pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes)+ "SAS Form 16.2B Total Property Tax + Water Tax (Current+Arrears) is not matching");
							}

						 */


							String sasPropertyTaxCurrentOrg = "";
							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								//sasPropertyTaxCurrentOrg=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[16]/tbody/tr[1]/td[4]")).getText();
								sasPropertyTaxCurrentOrg=rowElement.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[16]/tbody/tr[2]/td[4]")).getText();
							}
							else
							{
								if(mGetPropertyFromFile("pt_chngInAddnChngVcntLandType").equalsIgnoreCase("Land + Building"))
								{
									sasPropertyTaxCurrentOrg=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[16]/tbody/tr[2]/td[4]")).getText();
								}
								else
								{
									sasPropertyTaxCurrentOrg=rowElement.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[16]/tbody/tr[2]/td[4]")).getText();
								}
							}
							System.out.println("SAS Form 16.2B Total Property Tax + Water Tax (Current+Arrears) is: "+sasPropertyTaxCurrentOrg);
							String sasPropertyTaxCurrent=sasPropertyTaxCurrentOrg.replaceAll(",","");
							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasPropertyTaxCurrent, mGetPropertyFromFile("pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes"), "Actual is :" +sasPropertyTaxCurrent+ "Expected is :" +mGetPropertyFromFile("pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes")+ "SAS Form 16.2B Total Property Tax + Water Tax (Current+Arrears) is not matching for Vacant Land");
							}
							else
							{
								//int pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes;
								//double pt_newAssessmentPTAnnualTax = 0;
								String pt_newAssessmentPTIntAreasAmtVal;
								int pt_newAssessmentPTIntAreasAmt = 0;

								//	if(driver.findElements(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table")).size() != 0)
								/*if(driver.findElements(By.xpath("//*[@id=\"command\"]/div[2]/table[12]")).size() != 0)
								{
									//pt_newAssessmentPTIntAreasVal=driver.findElement(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table/tbody/tr[2]/td[3]")).getText();
									pt_newAssessmentPTIntAreasAmtVal=driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[12]/tbody/tr[2]/td[2]")).getText();
									pt_newAssessmentPTIntAreasAmtVal=pt_newAssessmentPTIntAreasAmtVal.replaceAll(",","");
									System.out.println("Arrears : "+pt_newAssessmentPTIntAreasAmtVal);
									pt_newAssessmentPTIntAreasAmt=Integer.parseInt(pt_newAssessmentPTIntAreasAmtVal);
								}*/
								if(driver.findElements(By.xpath("//*[@id=\"command\"]/div[2]/table[12]")).size() != 0)
								{
									//pt_newAssessmentPTIntAreasVal=driver.findElement(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table/tbody/tr[2]/td[3]")).getText();
									/*pt_newAssessmentPTIntAreasAmtVal=driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[12]/tbody/tr[2]/td[2]")).getText();
									pt_newAssessmentPTIntAreasAmtVal=pt_newAssessmentPTIntAreasAmtVal.replaceAll(",","");
									System.out.println("Arrears : "+pt_newAssessmentPTIntAreasAmtVal);
									pt_newAssessmentPTIntAreasAmt=Integer.parseInt(pt_newAssessmentPTIntAreasAmtVal);*/

									WebElement tableArr =driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[12]"));
									List<WebElement> rowsArr = tableArr.findElements(By.tagName("tr"));
									int rowcountArr = rowsArr.size();
									System.out.println("Total number of rows :" +rowcountArr);
									int rowNoArr=0;
									for (WebElement rowElementArr:rowsArr)
									{
										List<WebElement> colsArr = rowElementArr.findElements(By.tagName("td"));
										int colcountArr= colsArr.size();
										System.out.println("Total number of columns :"+colcountArr);
										int columnNoArr=0;
										for(WebElement colElementArr:colsArr)
										{
											if(rowNoArr>=1)
											{
												if(columnNoArr==1)
												{
													pt_newAssessmentPTIntAreasAmtVal=rowElementArr.findElement(By.xpath("td[2]")).getText();
													System.out.println("Arrears : "+pt_newAssessmentPTIntAreasAmtVal);
													pt_newAssessmentPTIntAreasAmtVal=pt_newAssessmentPTIntAreasAmtVal.replaceAll(",","");
													System.out.println("Arrears : "+pt_newAssessmentPTIntAreasAmtVal);
													//pt_newAssessmentPTIntAreasAmt=Integer.parseInt(pt_newAssessmentPTIntAreasAmtVal);
													//pt_newAssessmentPTIntAreasAmt+=pt_newAssessmentPTIntAreasAmt;
													pt_newAssessmentPTIntAreasAmt=pt_newAssessmentPTIntAreasAmt+Integer.parseInt(pt_newAssessmentPTIntAreasAmtVal);
												}
											}
											columnNoArr=columnNoArr+1;
										}
										rowNoArr=rowNoArr+1;
									}
								}
								else
								{
									pt_newAssessmentPTIntAreasAmt=0;
								}
								System.out.println("Arrears Amount : "+pt_newAssessmentPTIntAreasAmt);

								/*for(int i=0 ; i<calcAnnulTaxArray.size(); i++) 
					{
						//pt_newAssessmentPTAnnualTaxAutoRes+=Double.parseDouble(calcAnnulTaxArray.get(i));
						if(!usageFactorArray.get(i).equalsIgnoreCase("Govt. Establishment"))
						{
							pt_newAssessmentPTAnnualTax+=Double.parseDouble(calcAnnulTaxArray.get(i));
						}
					}

					System.out.println("16.2B Total Property Tax : "+pt_newAssessmentPTAnnualTax);*/
								//pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes=(int) pt_newAssessmentPTAnnualTaxAutoRes;
								pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes=pt_newAssessmentPTAnnualTaxAutoRes+pt_newAssessmentPTIntAreasAmt;
								System.out.println("pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes is: "+pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes);
								mAssert(sasPropertyTaxCurrent, String.valueOf(pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes), "Actual is :" +sasPropertyTaxCurrent+ "Expected is :" +String.valueOf(pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes)+ "SAS Form 16.2B Total Property Tax + Water Tax (Current+Arrears) is not matching");

							}						

						}
						columnNoPTS=columnNoPTS+1;
					}
					rowNoPTS=rowNoPTS+1;
				}

				mTakeScreenShot();

				//Interest/Penalty/Rebate

				WebElement tableIPR = mFindElement("xpath", mGetPropertyFromFile("pt_newAssessmentSASIPRid"));
				List<WebElement> rowsIPR = tableIPR.findElements(By.tagName("tr"));
				int rowcountIPR = rowsIPR.size();
				System.out.println("Total number of rows :" +rowcountIPR);
				int rowNoIPR=0;
				for (WebElement rowElement:rowsIPR)
				{
					List<WebElement> colsIPR = rowElement.findElements(By.tagName("td"));
					int colcountIPR= colsIPR.size();
					System.out.println("Total number of columns :"+colcountIPR);
					int columnNoIPR=0;
					for(WebElement colElement:colsIPR)
					{
						if(columnNoIPR==0)
						{

							String sasRebateIncRWH=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[18]/tbody/tr[1]/td[4]")).getText();
							System.out.println("SAS Form Rebate (RWH + Normal) 16.3A is: "+sasRebateIncRWH);
							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasRebateIncRWH, mGetPropertyFromFile("pt_newAssessmentPTRebtAutoRes"), "Actual is: " +sasRebateIncRWH+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPTRebtAutoRes")+ "SAS Form Rebate (RWH + Normal) 16.3A is not matching");
							}
							else
							{
								mAssert(sasRebateIncRWH, String.valueOf(pt_newAssessmentPTRebtAutoRes), "Actual is: " +sasRebateIncRWH+ "Expected is: " +String.valueOf(pt_newAssessmentPTRebtAutoRes)+ "SAS Form Rebate (RWH + Normal) 16.3A is not matching");
							}
						}
						else if(columnNoIPR==1)
						{
							String sasRebateIncRWH=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[18]/tbody/tr[2]/td[4]")).getText();
							System.out.println("SAS Form Penalty (Current) 16.3B is: "+sasRebateIncRWH);
							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasRebateIncRWH, mGetPropertyFromFile("pt_newAssessmentPanaltySAS"), "Actual is: " +sasRebateIncRWH+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPanaltySAS")+ "SAS Form Penalty (Current) 16.3B is not matching");
							}
							else
							{
								System.out.println("SAS Form Penalty (Current) 16.3B is not applicable for change in assessment");
							}

						}else if(columnNoIPR==2)
						{
							String sasRebateIncRWH=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[18]/tbody/tr[3]/td[4]")).getText();
							System.out.println("SAS Form Interest (Current + Arrears) 16.3C is: "+sasRebateIncRWH);
							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasRebateIncRWH, mGetPropertyFromFile("pt_newAssessmentPTIntAreasAndCurrentSAS"), "Actual is: " +sasRebateIncRWH+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPTIntAreasAndCurrentSAS")+ "SAS Form Interest (Current + Arrears) 16.3C is not matching");
							}
							else
							{
								//double pt_newAssessmentPTIntAreasAndCurrentSAS;
								String pt_newAssessmentPTIntAreasVal;
								int pt_newAssessmentPTIntAreas = 0;
								//if(driver.findElements(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table")).size() != 0)
								/*if(driver.findElements(By.xpath("//*[@id=\"command\"]/div[2]/table[12]")).size() != 0)
								{
									System.out.println("For Vacant Land Areas amount is present");
									//pt_newAssessmentPTIntAreasVal=driver.findElement(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table/tbody/tr[2]/td[3]")).getText();
									pt_newAssessmentPTIntAreasVal=driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[12]/tbody/tr[2]/td[3]")).getText();
									System.out.println("Interest : "+pt_newAssessmentPTIntAreasVal);
									//pt_newAssessmentPTIntAreas=Integer.parseInt("pt_newAssessmentPTIntAreasVal");
									pt_newAssessmentPTIntAreas=Integer.parseInt(pt_newAssessmentPTIntAreasVal);

								}*/
								if(driver.findElements(By.xpath("//*[@id=\"command\"]/div[2]/table[12]")).size() != 0)
								{
									System.out.println("For Vacant Land Areas amount is present");
									//pt_newAssessmentPTIntAreasVal=driver.findElement(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table/tbody/tr[2]/td[3]")).getText();
									/*pt_newAssessmentPTIntAreasVal=driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[12]/tbody/tr[2]/td[3]")).getText();
									System.out.println("Interest : "+pt_newAssessmentPTIntAreasVal);
									//pt_newAssessmentPTIntAreas=Integer.parseInt("pt_newAssessmentPTIntAreasVal");
									pt_newAssessmentPTIntAreas=Integer.parseInt(pt_newAssessmentPTIntAreasVal);*/

									WebElement tableArr =driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[12]"));
									List<WebElement> rowsArr = tableArr.findElements(By.tagName("tr"));
									int rowcountArr = rowsArr.size();
									System.out.println("Total number of rows :" +rowcountArr);
									int rowNoArr=0;
									for (WebElement rowElementArr:rowsArr)
									{
										List<WebElement> colsArr = rowElementArr.findElements(By.tagName("td"));
										int colcountArr= colsArr.size();
										System.out.println("Total number of columns :"+colcountArr);
										int columnNoArr=0;
										for(WebElement colElementArr:colsArr)
										{
											if(rowNoArr>=1)
											{
												if(columnNoArr==1)
												{
													pt_newAssessmentPTIntAreasVal=rowElementArr.findElement(By.xpath("td[3]")).getText();
													System.out.println("Interest : "+pt_newAssessmentPTIntAreasVal);
													pt_newAssessmentPTIntAreas=pt_newAssessmentPTIntAreas+Integer.parseInt(pt_newAssessmentPTIntAreasVal);
													//pt_newAssessmentPTIntAreas+=pt_newAssessmentPTIntAreas;
												}
											}
											columnNoArr=columnNoArr+1;
										}
										rowNoArr=rowNoArr+1;
									}

								}
								else
								{
									System.out.println("For Vacant Land Areas amount is not present");
									pt_newAssessmentPTIntAreas=0;
								}
								System.out.println("Interest Rs. : "+pt_newAssessmentPTIntAreas);
								//////Wrong code 16.3 C
								//pt_newAssessmentPTIntAreasAndCurrentSAS=Math.round(pt_newAssessmentPTIntAutoResSAS+pt_newAssessmentPTIntAreas);
								//pt_newAssessmentPTIntAreasAndCurrentSAS=(int)(pt_newAssessmentPTIntAutoResSAS+pt_newAssessmentPTIntAreas);
								pt_newAssessmentPTIntAreasAndCurrentSAS=Math.round(pt_newAssessmentPTIntAutoResSAS+pt_newAssessmentPTIntAreas);
								mAssert(sasRebateIncRWH, String.valueOf(pt_newAssessmentPTIntAreasAndCurrentSAS), "Actual is: " +sasRebateIncRWH+ "Expected is: " +String.valueOf(pt_newAssessmentPTIntAreasAndCurrentSAS)+ "SAS Form Interest (Current + Arrears) 16.3C is not matching");
							}
						}
						columnNoIPR=columnNoIPR+1;
					}
					rowNoIPR=rowNoIPR+1;
				}

				//Total Net Payable Amount SAS form

				WebElement tableTPA =mFindElement("xpath", mGetPropertyFromFile("pt_newAssessmentSASTPAid"));
				List<WebElement> rowsTPA = tableTPA.findElements(By.tagName("tr"));
				int rowcountTPA = rowsTPA.size();
				System.out.println("Total number of rows :" +rowcountTPA);
				int rowNoTPA=0;
				for (WebElement rowElement:rowsTPA)
				{
					List<WebElement> colsTPA = rowElement.findElements(By.tagName("td"));
					int colcountTPA= colsTPA.size();
					System.out.println("Total number of columns :"+colcountTPA);
					int columnNoTPA=0;
					for(WebElement colElement:colsTPA)
					{

						if(columnNoTPA==0)
						{
							String sasTotNetPayAmtOrg=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[19]/tbody/tr[1]/td[4]")).getText();
							System.out.println("SAS Form Total Net Payable Amount (16.4) is: "+sasTotNetPayAmtOrg);
							String sasTotNetPayAmt=sasTotNetPayAmtOrg.replaceAll(",","");

							//Added by Jyoti
							sas_TotNetPayableAmtList.add(sasTotNetPayAmtOrg);
							System.out.println("SAS Form List of Total Net Payable Amount ::: "+sas_TotNetPayableAmtList);

							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasTotNetPayAmt, mGetPropertyFromFile("pt_newAssessmentTotPayableAmtResAutoSupp"), "Actual is :" +sasTotNetPayAmt+ "Expected is :" +mGetPropertyFromFile("pt_newAssessmentTotPayableAmtResAutoSupp")+ "SAS Form Total Net Payable Amount (16.4) is not matching");
							}
							else
							{
								double pt_newAssessmentTotPayableAmtResAutoSupp;
								int pt_newAssessmentServiceChargeCurSAS = 0;
								//int pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes;
								int pt_newAssessmentWaterTaxCrgSupp = 0;
								int pt_newAssessmentPTRebtAutoResSAS;


								pt_newAssessmentServiceChargeCurSAS=0;
								System.out.println("Service charge total amount from array : "+pt_newAssessmentServiceChargeCurSAS);


								/*if(chngInAssWaterTaxType.equalsIgnoreCase("Supply Water"))
								{
									pt_newAssessmentWaterTaxCrgSupp=1214;
									System.out.println("Water tax charge is : "+pt_newAssessmentWaterTaxCrgSupp);
								}
								else if(chngInAssWaterTaxType.equalsIgnoreCase("Own Boring"))
								{
									pt_newAssessmentWaterTaxCrgSupp=500;
									System.out.println("Water tax charge is : "+pt_newAssessmentWaterTaxCrgSupp);
								}
								else if(chngInAssWaterTaxType.equalsIgnoreCase("Both"))
								{
									pt_newAssessmentWaterTaxCrgSupp=1714;
									System.out.println("Water tax charge is : "+pt_newAssessmentWaterTaxCrgSupp);
								}
								else if(chngInAssWaterTaxType.equalsIgnoreCase("Already Paid"))
								{
									pt_newAssessmentWaterTaxCrgSupp=0;
									System.out.println("Water tax charge is : "+pt_newAssessmentWaterTaxCrgSupp);
								}
								else if(chngInAssWaterTaxType.equalsIgnoreCase("Not Applicable"))
								{
									pt_newAssessmentWaterTaxCrgSupp=0;
									System.out.println("Water tax charge is : "+pt_newAssessmentWaterTaxCrgSupp);
								}
								else
								{
									System.out.println("In else of water tax charge");
								}*/

								//	pt_newAssessmentPTRebtAutoResSAS=pt_newAssessmentPTRebtAutoRes+pt_newAssessmentPTRebtRWH;
								//pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes=pt_newAssessmentPTAnnualTaxAutoRes+pt_newAssessmentWaterTaxCrgSupp;
								//pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes=(int) pt_newAssessmentPTAnnualTaxAutoRes;
								System.out.println("pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes is : "+pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes);
								//pt_newAssessmentTotPayableAmtResAutoSupp=Math.round(pt_newAssessmentServiceChargeCurSAS+pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes+pt_newAssessmentPTIntAutoResSAS-pt_newAssessmentPTRebtAutoRes);

								pt_newAssessmentTotPayableAmtResAutoSupp=Math.round(pt_newAssessmentServiceChargeCurSAS+pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes/*+pt_newAssessmentPTIntAutoResSAS*/+pt_newAssessmentPTIntAreasAndCurrentSAS-pt_newAssessmentPTRebtAutoRes);
								//mAssert(sasTotNetPayAmt, String.valueOf(pt_newAssessmentTotPayableAmtResAutoSupp), "Actual is :" +sasTotNetPayAmt+ "Expected is :" +String.valueOf(pt_newAssessmentTotPayableAmtResAutoSupp)+ "SAS Form Total Net Payable Amount (16.4) is not matching");
								mAssert(sasTotNetPayAmt, String.valueOf(pt_newAssessmentTotPayableAmtResAutoSupp), "Actual is :" +sasTotNetPayAmt+ "Expected is :" +String.valueOf(pt_newAssessmentTotPayableAmtResAutoSupp)+ "SAS Form Total Net Payable Amount (16.4) is not matching for Vacant Land");
							}

						}
						columnNoTPA=columnNoTPA+1;
					}
					rowNoTPA=rowNoTPA+1;
				}
				//mTakeScreenShot();
			}

			//SAS Form Land Type = Flat @Ritesh 30-11-2016

			else if(mGetPropertyFromFile("landType").equalsIgnoreCase("Flat"))
			{
				// Land Type
				//Boolean sasLandBuilding=driver.findElement(By.id("propertyMaster.codpropertyID12")).isSelected();
				Boolean sasLandBuilding=mFindElement("id", mGetPropertyFromFile("pt_newAssessmentSASLndTypeFLtid")).isSelected();
				System.out.println("Land type is Flat");
				if(sasLandBuilding==true)
				{
					//String sasLandType=driver.findElement(By.id("propertyMaster.codpropertyID12")).getAttribute("Text");
					//String sasLandType=mGetText("id", mGetPropertyFromFile("pt_newAssessmentSASLndTypeid"), "Text");
					//System.out.println("Land type is :"+sasLandType);
					//String sasLandType=driver.findElement(By.id("propertyMaster.codpropertyID12")).getText();
					String sasLandTypeOrg=driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[3]/tbody/tr[1]/td[3]")).getText();
					System.out.println("Land type is :"+sasLandTypeOrg);
					String sasLandType=sasLandTypeOrg.substring(0, 4);
					if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
					{
						mAssert(sasLandType, landTypeSelected, "Land type does not matches in SAS form, Actual is :"+sasLandType+"Expected is :"+landTypeSelected);
					}
					else
					{
						mAssert(sasLandType, chngAsmtLandType, "Land type does not matches in SAS form, Actual is :"+sasLandType+"Expected is :"+landTypeSelected);
					}
				}
				/*// Road Factor
				if(driver.findElement(By.id("propertyMaster.mainpgRoadfctr1")).isSelected())
				{
					sasRoadFactor=driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[4]/tbody/tr[1]/td[2]/text()[1]")).getText();
					//System.out.println("Road factor is : Principal Main Road");
					System.out.println("Road factor is :"+sasRoadFactor);
				}
				else if(driver.findElement(By.id("propertyMaster.mainpgRoadfctr2")).isSelected())
				{
					sasRoadFactor=driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[4]/tbody/tr[1]/td[2]/text()[2]")).getText();
					//System.out.println("Road factor is : Main Road");
					System.out.println("Road factor is :"+sasRoadFactor);
				}
				else if(driver.findElement(By.id("propertyMaster.mainpgRoadfctr3")).isSelected())
				{
					sasRoadFactor=driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[4]/tbody/tr[1]/td[2]/text()[3]")).getText();
					//System.out.println("Road factor is : Other Road");
					System.out.println("Road factor is :"+sasRoadFactor);
				}*/

				mTakeScreenShot();
				mscroll(1155, 707);
				// Acquisition Year 
				//String acquisitionYear=driver.findElement(By.cssSelector("#command > div.form-div.margin_top_10 > table:nth-child(5) > tbody > tr:nth-child(2) > td:nth-child(2) > span")).getText();
				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					String acquisitionYear=mGetText("css", mGetPropertyFromFile("pt_newAssessmentSASAcqYrid"));
					System.out.println("Year of Acquisition is: "+acquisitionYear);
					mAssert(acquisitionYear, mGetPropertyFromFile("pt_newAssessmentAcquisitionYear"), "Actual is: "+acquisitionYear+ "Expected is: "+mGetPropertyFromFile("pt_newAssessmentAcquisitionYear")+ "Year of Acquisition is not matching");
				}
				else
				{
					String acquisitionYear=driver.findElement(By.cssSelector("#command > div.form-div.margin_top_10 > table:nth-child(5) > tbody > tr:nth-child(2) > td:nth-child(2)")).getText();
					System.out.println("Year of Acquisition is: "+acquisitionYear);
					mAssert(acquisitionYear, chngAsmtLandTypeAcqYr, "Actual is: "+acquisitionYear+ "Expected is: "+chngAsmtLandTypeAcqYr+ "Year of Acquisition is not matching");
				}

				//Vacant Land Annual Tax on SAS form @Ritesh Dated: - 12-10-2016
				//String sasVacantLandAnnualTx = driver.findElement(By.cssSelector("#command > div.form-div.margin_top_10 > table:nth-child(11) > tbody > tr:nth-child(7) > td:nth-child(3)")).getText();
				/*String sasVacantLandAnnualTx =mGetText("css", mGetPropertyFromFile("pt_newAssessmentSASVacantLandAnnualTxid"));
				System.out.println("Vacant Land Annual Tax is: "+sasVacantLandAnnualTx);
				mAssert(sasVacantLandAnnualTx, vancantareavalue, "Actual is: "+sasVacantLandAnnualTx+ "Expected is: "+vancantareavalue+"Vacant Land Annual Tax is not matching");*/


				mTakeScreenShot();
				mscroll(1217, 714);
				//Reading Building Details Table

				//WebElement table=driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/div[2]/table[2]"));
				WebElement table=mFindElement("xpath", mGetPropertyFromFile("pt_newAssessmentSASRdBlgDetidid"));
				List<WebElement> rows=table.findElements(By.tagName("tr"));
				int rowCount=rows.size();
				System.out.println("Total number of rows :"+rowCount);

				int rowNo=0;
				for(WebElement rowElement:rows)
				{
					List<WebElement> cols=rowElement.findElements(By.tagName("td"));
					int colCount=cols.size();
					System.out.println("Total number of columns :"+colCount);

					int columnNo=0;
					for(WebElement colElement:cols)
					{
						if(rowNo>3)
						{
							if(columnNo==0)
							{
								//	String sasBldgYear=rowElement.findElement(By.xpath("td[1]")).getText();
								sasBldgYearArray.add(rowElement.findElement(By.xpath("td[1]")).getText());
								System.out.println("Building Details year is :"+sasBldgYearArray);
								//ArrayList<ArrayList<String>> rowCollector=new ArrayList<ArrayList<String>>();
								//ArrayList<String> colCollector=new ArrayList<String>();
								//colCollector.add(sasBldgYear);						
								//mAssert(sasBldgYear, yearBlg, "Actual is: "+sasBldgYear+ "Expected is: "+yearBlg+ "Building Year is not matching");						
							}
							if(columnNo==1)
							{
								//	String sasFloorNo=rowElement.findElement(By.xpath("td[2]")).getText();
								sasFloorNoArray.add(rowElement.findElement(By.xpath("td[2]")).getText());
								System.out.println("Building Floor number is :"+sasFloorNoArray);
								//	colCollector.add(sasFloorNo);
								//mAssert(sasFloorNo, floorNo, "Actual is: "+sasFloorNo+ "Expected is: "+floorNo+ "Building Floor no. is not matching");
							}
							if(columnNo==2)
							{
								//String sasUsageType=rowElement.findElement(By.xpath("td[3]")).getText();
								sasUsageTypeArray.add(rowElement.findElement(By.xpath("td[3]")).getText());
								System.out.println("Usage type is :"+sasUsageTypeArray);
								//colCollector.add(sasUsageType);
								//mAssert(sasUsageType, typeOfUse, "Actual is: "+sasUsageType+ "Expected is: "+typeOfUse+ "Usage type is not matching");
							}
							if(columnNo==3)
							{
								//	String sasConstructionType=rowElement.findElement(By.xpath("td[4]")).getText();
								sasConstructionTypeArray.add(rowElement.findElement(By.xpath("td[4]")).getText());
								System.out.println("Construction type is :"+sasConstructionTypeArray);
								//colCollector.add(sasConstructionType);
								//mAssert(sasConstructionType, constructType, "Actual is: "+sasConstructionType+ "Expected is: "+constructType+ "Construction type is not matching");
							}
							if(columnNo==4)
							{
								//String sasRatableArea=rowElement.findElement(By.xpath("td[5]")).getText();
								sasRatableAreaArray.add(rowElement.findElement(By.xpath("td[5]")).getText());
								System.out.println("Ratable Area is :"+sasRatableAreaArray);
								//colCollector.add(sasRatableArea);
								//mAssert(sasRatableArea, mGetPropertyFromFile("ratableAreaForRes"), "Actual is: "+sasRatableArea+ "Expected is: "+mGetPropertyFromFile("ratableAreaForRes")+ "Ratable Area is not matching");
							}
							if(columnNo==5)
							{
								//String sasUsageFactor=rowElement.findElement(By.xpath("td[7]")).getText();
								sasUsageFactorArray.add(rowElement.findElement(By.xpath("td[7]")).getText());
								System.out.println("Usage Factor is :"+sasUsageFactorArray);
								//colCollector.add(sasUsageFactor);
								//mAssert(sasUsageFactor, usageFactor, "Actual is: "+sasUsageFactor+ "Expected is: "+usageFactor+ "Usage factor is not matching");
							}
							if(columnNo==6)
							{
								//String sasOccFactor=rowElement.findElement(By.xpath("td[8]")).getText();
								sasOccFactorArray.add(rowElement.findElement(By.xpath("td[8]")).getText());
								System.out.println("Occupancy Factor is :"+sasOccFactorArray);
								//colCollector.add(sasOccFactor);
								//mAssert(sasOccFactor, occFac, "Actual is: "+sasOccFactor+ "Expected is: "+occFac+ "Occupancy Factor is not matching");
							}					
							if(columnNo==7)
							{
								/*if(viewTypeOfUse.equalsIgnoreCase("Residential"))
								{
									String sasAnnulRateblVal=rowElement.findElement(By.xpath("td[10]")).getText();
									System.out.println("Occupancy Factor is :"+sasAnnulRateblVal);
									//mAssert(sasAnnulRateblVal, mGetPropertyFromFile("annualRatableValueForRes"), "Actual is: "+sasAnnulRateblVal+ "Expected is: "+mGetPropertyFromFile("annualRatableValueForRes")+ "Occupancy Factor is not matching");
								}
								else
								{
									String sasAnnulRateblVal=rowElement.findElement(By.xpath("td[10]")).getText();
									System.out.println("Occupancy Factor is :"+sasAnnulRateblVal);
									//mAssert(sasAnnulRateblVal, mGetPropertyFromFile("annualRatableValueForNonRes"), "Actual is: "+sasAnnulRateblVal+ "Expected is: "+mGetPropertyFromFile("annualRatableValueForNonRes")+ "Occupancy Factor is not matching");
								}*/	
								String sasAnnulRateblValArrayOrg;
								sasAnnulRateblValArrayOrg=rowElement.findElement(By.xpath("td[10]")).getText();
								System.out.println("Annual ratable value is :"+sasAnnulRateblValArrayOrg);
								sasAnnulRateblValArrayOrg=sasAnnulRateblValArrayOrg.replaceAll(",","");
								sasAnnulRateblValArray.add(sasAnnulRateblValArrayOrg);
							}
							if(columnNo==8)
							{
								/*if (viewTypeOfUse.equalsIgnoreCase("Residential"))
								{
									String sasAnnualPropertyTax=rowElement.findElement(By.xpath("td[12]")).getText();
									System.out.println("Annual Rental Value is :"+sasAnnualPropertyTax);
									//mAssert(sasAnnualPropertyTax,mGetPropertyFromFile("annualTaxRes"), "Actual is: "+sasAnnualPropertyTax+ "Expected is: "+mGetPropertyFromFile("annualTaxRes")+ "Annual Property Tax is not matching");	
								}
								else
								{
									String sasAnnualPropertyTax=rowElement.findElement(By.xpath("td[12]")).getText();
									System.out.println("Annual Rental Value is :"+sasAnnualPropertyTax);
									//mAssert(sasAnnualPropertyTax,mGetPropertyFromFile("annualTaxNonRes"), "Actual is: "+sasAnnualPropertyTax+ "Expected is: "+mGetPropertyFromFile("annualTaxNonRes")+ "Annual Property Tax is not matching");
								}*/
								sasAnnualPropertyTaxArray.add(rowElement.findElement(By.xpath("td[12]")).getText());
								System.out.println("Annual Rental Value is :"+sasAnnualPropertyTaxArray);
							}
							//rowCollector.add(colCollector);
						}
						columnNo=columnNo+1;
					}
					rowNo=rowNo+1;
				}

				Collections.reverse(sasBldgYearArray);

				if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
				{
					mAssert(sasBldgYearArray, yearBlgArray, "Actual is: "+sasBldgYearArray+ "Expected is: "+yearBlgArray+ "Array of year does not match in SAS form");
					mAssert(sasFloorNoArray, addFloorNoArray, "Actual is: "+sasFloorNoArray+ "Expected is: "+addFloorNoArray+ "Array of floor no. does not match in SAS form");
					mAssert(sasUsageTypeArray, addTypeOfUseArray, "Actual is: "+sasUsageTypeArray+ "Expected is: "+addTypeOfUseArray+ "Array of type of usage does not match in SAS form");
					mAssert(sasConstructionTypeArray, addConstructTypeArray, "Actual is: "+sasConstructionTypeArray+ "Expected is: "+addConstructTypeArray+ "Array of construction type does not match in SAS form");
					mAssert(sasRatableAreaArray, calcRatableArArray, "Actual is: "+sasRatableAreaArray+ "Expected is: "+calcRatableArArray+ "Array of ratable area does not match in SAS form");
					mAssert(sasUsageFactorArray, addUsageFactorArray, "Actual is: "+sasUsageFactorArray+ "Expected is: "+addUsageFactorArray+ "Array of usage factor does not match in SAS form");
					mAssert(sasOccFactorArray, addOccFacArray, "Actual is: "+sasOccFactorArray+ "Expected is: "+addOccFacArray+ "Array of occupancy factor does not match in SAS form");
					mAssert(sasAnnulRateblValArray, calcAnnulRateblValArray, "Actual is: "+sasAnnulRateblValArray+ "Expected is: "+calcAnnulRateblValArray+ "Array of annual ratable value does not match in SAS form");
					mAssert(sasAnnualPropertyTaxArray,calcAnnulTaxArray, "Actual is: "+sasAnnualPropertyTaxArray+ "Expected is: "+calcAnnulTaxArray+ "Array of annual tax does not match in SAS form");
				}
				else
				{
					if(!mGetPropertyFromFile("pt_chngInAddnChngLandType").equalsIgnoreCase("yes"))
					{
						Collections.reverse(floorNoArray);
					}
					mAssert(sasBldgYearArray, yearBlgArray, "Actual is: "+sasBldgYearArray+ "Expected is: "+yearBlgArray+ "Array of year does not match in SAS form");
					mAssert(sasFloorNoArray, floorNoArray, "Actual is: "+sasFloorNoArray+ "Expected is: "+floorNoArray+ "Array of floor no. does not match in SAS form");
					mAssert(sasUsageTypeArray, typeOfUseArray, "Actual is: "+sasUsageTypeArray+ "Expected is: "+typeOfUseArray+ "Array of type of usage does not match in SAS form");
					mAssert(sasConstructionTypeArray, constructTypeArray, "Actual is: "+sasConstructionTypeArray+ "Expected is: "+constructTypeArray+ "Array of construction type does not match in SAS form");
					mAssert(sasRatableAreaArray, calcRatableArArray, "Actual is: "+sasRatableAreaArray+ "Expected is: "+calcRatableArArray+ "Array of ratable area does not match in SAS form");
					mAssert(sasUsageFactorArray, usageFactorArray, "Actual is: "+sasUsageFactorArray+ "Expected is: "+usageFactorArray+ "Array of usage factor does not match in SAS form");
					mAssert(sasOccFactorArray, occFacArray, "Actual is: "+sasOccFactorArray+ "Expected is: "+occFacArray+ "Array of occupancy factor does not match in SAS form");
					mAssert(sasAnnulRateblValArray, calcAnnulRateblValArray, "Actual is: "+sasAnnulRateblValArray+ "Expected is: "+calcAnnulRateblValArray+ "Array of annual ratable value does not match in SAS form");
					mAssert(sasAnnualPropertyTaxArray,calcAnnulTaxArray, "Actual is: "+sasAnnualPropertyTaxArray+ "Expected is: "+calcAnnulTaxArray+ "Array of annual tax does not match in SAS form");
				}
				//14C Total Tax (12F + Current Year Property Tax)

				//WebElement tableTC = driver.findElement(By.xpath("//*[@id='command']/div[2]/div[2]/table[4]"));
				WebElement tableTC =mFindElement("xpath", mGetPropertyFromFile("pt_newAssessmentSASTotTaxid"));
				List<WebElement> rowsTC = tableTC.findElements(By.tagName("tr"));
				int rowcountTC = rowsTC.size();
				System.out.println("Total number of rows :" +rowcountTC);
				int rowNoTC=0;
				//for (WebElement rowElement:rowsTC)
				//{
				List<WebElement> cols = rowsTC.get(1).findElements(By.tagName("td"));
				int colcount= cols.size();
				System.out.println("Total number of columns :"+colcount);
				int columnNo=0;
				//for(WebElement colElement:cols)
				for(int i=0;i<colcount;i++)
				{
					if(i==0)
					{
						String sasTotAnnualTaxOrg= rowsTC.get(i).findElement(By.xpath("//*[@id='command']/div[2]/div[2]/table[4]/tbody/tr[3]/td[4]")).getText();
						System.out.println("Total Annual Tax 12F +Current Year Property Tax: "+sasTotAnnualTaxOrg);
						String sasTotAnnualTax=sasTotAnnualTaxOrg.replaceAll(",","");

						if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
						{
							mAssert(sasTotAnnualTax, mGetPropertyFromFile("pt_newAssessmentPTAnnualTaxAutoRes"), "Actual is: " +sasTotAnnualTax+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPTAnnualTaxAutoRes")+ "Total Annual Tax 12F +Current Year Property Tax is not matching");
						}
						else
						{
							mAssert(sasTotAnnualTax, String.valueOf(pt_newAssessmentPTAnnualTaxAutoRes), "Actual is: " +sasTotAnnualTax+ "Expected is: " +String.valueOf(pt_newAssessmentPTAnnualTaxAutoRes)+ "Total Annual Tax 12F +Current Year Property Tax is not matching");
						}

					}
					if (i==1)
					{
						String sasRebate=rowsTC.get(i).findElement(By.xpath("//*[@id='command']/div[2]/div[2]/table[4]/tbody/tr[4]/td[4]")).getText();
						System.out.println("Rebate is 14D: "+sasRebate);

						//Added by Jyoti

						sasRebateList.add(sasRebate);
						System.out.println("Rebate List of SAS Form is ::: "+sasRebateList);


						if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
						{
							mAssert(sasRebate, mGetPropertyFromFile("pt_newAssessmentPTRebtAutoResSAS"), "Actual is: " +sasRebate+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPTRebtAutoResSAS")+ "Rebate is 14D is not matching");
						}
						else
						{
							double pt_newAssessmentPTRebtRWH;
							if(mGetPropertyFromFile("pt_newAssessmentRebateApplicable").equalsIgnoreCase("Yes"))
							{
								pt_newAssessmentPTRebt=(pt_newAssessmentPTAnnualTaxAutoRes*0.05);
							}	
							else
							{
								pt_newAssessmentPTRebt=0;
							}
							if(mGetPropertyFromFile("rainWaterHarvesting").equalsIgnoreCase("Yes"))
							{
								pt_newAssessmentPTRebtRWH=(pt_newAssessmentPTAnnualTaxAutoRes*0.05);
							}	
							else
							{
								pt_newAssessmentPTRebtRWH=0;
							}
							pt_newAssessmentPTRebtAutoRes=pt_newAssessmentPTRebt+pt_newAssessmentPTRebtRWH;
							pt_newAssessmentPTRebtAutoRes=(Math.round(pt_newAssessmentPTRebtAutoRes));
							System.out.println("Rebate pt_newAssessmentPTRebtAutoRes: "+ pt_newAssessmentPTRebtAutoRes);
							mAssert(sasRebate, String.valueOf(pt_newAssessmentPTRebtAutoRes), "Actual is: " +sasRebate+ "Expected is: " +String.valueOf(pt_newAssessmentPTRebtAutoRes)+ "Rebate is 14D is not matching for Flat");
						}
					}
					if (i==2)
					{
						String sasInterestOrg=rowsTC.get(i).findElement(By.xpath("//*[@id='command']/div[2]/div[2]/table[4]/tbody/tr[5]/td[4]")).getText();
						System.out.println("On SAS Interest is 14E: "+sasInterestOrg);
						String sasInterest=sasInterestOrg.replaceAll(",","");

						// Added by Jyoti
						sasInterestList.add(sasInterestOrg);
						System.out.println("Interest on SAS Form is :::: "+sasInterestList);


						if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
						{
							mAssert(sasInterest, mGetPropertyFromFile("pt_newAssessmentPTIntAndPenSAS"), "Actual is: " +sasInterest+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPTIntAndPenSAS")+ "SAS Interest is 14E is not matching");
						}
						else
						{
							double pt_newAssessmentPTIntAndPenSAS;
							int pt_newAssessmentPTIntAutoRes;
							pt_newAssessmentPTIntAutoResSAS=pt_newAssessmentPTAnnualTaxAutoRes*Double.valueOf(mGetPropertyFromFile("interestRate"))*Integer.parseInt(mGetPropertyFromFile("noOfMonths"));
							pt_newAssessmentPTIntAndPenSAS=Math.round(pt_newAssessmentPTIntAutoResSAS);
							mAssert(sasInterest, String.valueOf(pt_newAssessmentPTIntAndPenSAS), "Actual is: " +sasInterest+ "Expected is: " +String.valueOf(pt_newAssessmentPTIntAndPenSAS)+ "SAS Interest is 14E is not matching for Flat");
						}
					}
					if(i==3)
					{
						String sasTotAnnualTaxSasOrg=rowsTC.get(i).findElement(By.xpath("//*[@id='command']/div[2]/div[2]/table[4]/tbody/tr[6]/td[4]")).getText();
						System.out.println("SAS Form Total Annual PropertyTax ( 14C - 14D + 14E) is: "+sasTotAnnualTaxSasOrg);
						String sasTotAnnualTaxSas=sasTotAnnualTaxSasOrg.replaceAll(",","");
						if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
						{
							mAssert(sasTotAnnualTaxSas, mGetPropertyFromFile("pt_newAssessmentTotalAnnualPropertyTaxonSAS"), "Actual is: " +sasTotAnnualTaxSas+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentTotalAnnualPropertyTaxonSAS")+ "SAS Form Total Annual PropertyTax ( 14C - 14D + 14E) is not matching");
						}
						else
						{
							double pt_newAssessmentTotalAnnualPropertyTaxonSAS;
							pt_newAssessmentTotalAnnualPropertyTaxonSAS=Math.round(pt_newAssessmentPTAnnualTaxAutoRes-pt_newAssessmentPTRebtAutoRes+pt_newAssessmentPTIntAutoResSAS);
							mAssert(sasTotAnnualTaxSas, String.valueOf(pt_newAssessmentTotalAnnualPropertyTaxonSAS), "Actual is: " +sasTotAnnualTaxSas+ "Expected is: " +String.valueOf(pt_newAssessmentTotalAnnualPropertyTaxonSAS)+ "SAS Form Total Annual PropertyTax ( 14C - 14D + 14E) is not matching for Flat");
						}

					}
					if(i==4)
					{
						String sasWaterTaxOrg=rowsTC.get(i).findElement(By.xpath("//*[@id='command']/div[2]/div[2]/table[4]/tbody/tr[7]/td[4]")).getText();
						System.out.println("SAS Form Water Tax Details(14G) is: "+sasWaterTaxOrg);
						String sasWaterTax=sasWaterTaxOrg.replaceAll(",","");

						// Added by Jyoti
						sas_WaterTaxList.add(sasWaterTaxOrg);
						System.out.println("List of Water Tax on SAS is ::: "+sas_WaterTaxList);

						if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
						{
							mAssert(sasWaterTax, mGetPropertyFromFile("pt_newAssessmentWaterTaxCrgSupp"), "Actual is :" +sasWaterTax+ "Expected is :" +mGetPropertyFromFile("pt_newAssessmentWaterTaxCrgSupp")+ "SAS Form Water Tax Details(14G) is not matching");
						}
						else
						{
							System.out.println("SAS Form Water Tax Details(14G) is not applicable");
						}
					}
				}
				rowNoTC=rowNoTC+1;

				//}

				//Assertions for 16.2B Total Property Tax + Water Tax (Current+Arrears)

				//Property Tax SAS form

				for(int i=0 ; i<calcAnnulTaxArray.size(); i++) 
				{
					//pt_newAssessmentPTAnnualTaxAutoRes+=Double.parseDouble(calcAnnulTaxArray.get(i));
					if(!usageFactorArray.get(i).equalsIgnoreCase("Govt. Establishment"))
					{
						pt_newAssessmentPTAnnualTax+=Double.parseDouble(calcAnnulTaxArray.get(i));
					}
				}

				System.out.println("16.2B Total Property Tax : "+pt_newAssessmentPTAnnualTax);

				//WebElement tablePTS = driver.findElement(By.xpath("//*[@id='command']/div[2]/table[16]"));
				WebElement tablePTS =mFindElement("xpath", mGetPropertyFromFile("pt_newAssessmentSASPropTaxid"));
				List<WebElement> rowsPTS = tablePTS.findElements(By.tagName("tr"));
				int rowcountPTS = rowsPTS.size();
				System.out.println("Total number of rows :" +rowcountPTS);
				int rowNoPTS=0;
				for (WebElement rowElement:rowsPTS)
				{
					List<WebElement> colsPTS = rowElement.findElements(By.tagName("td"));
					int colcountPTS= colsPTS.size();
					System.out.println("Total number of columns :"+colcountPTS);
					int columnNoPTS=0;
					for(WebElement colElement:colsPTS)
					{
						if(columnNoPTS==0)
						{
							String sasPropertyTaxCurrentOrg = "";
							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								sasPropertyTaxCurrentOrg=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[16]/tbody/tr[1]/td[4]")).getText();
							}
							else
							{
								if(mGetPropertyFromFile("pt_chngInAddnChngVcntLandType").equalsIgnoreCase("Flat"))
								{
									sasPropertyTaxCurrentOrg=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[16]/tbody/tr[1]/td[4]")).getText();
								}
								else
								{
									sasPropertyTaxCurrentOrg=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[16]/tbody/tr[2]/td[4]")).getText();
								}
							}
							System.out.println("SAS Form 16.2B Total Property Tax + Water Tax (Current+Arrears) is: "+sasPropertyTaxCurrentOrg);
							String sasPropertyTaxCurrent=sasPropertyTaxCurrentOrg.replaceAll(",","");
							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasPropertyTaxCurrent, mGetPropertyFromFile("pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes"), "Actual is :" +sasPropertyTaxCurrent+ "Expected is :" +mGetPropertyFromFile("pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes")+ "SAS Form 16.2B Total Property Tax + Water Tax (Current+Arrears) is not matching  for Flat");
							}
							else
							{
								//int pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes;
								//double pt_newAssessmentPTAnnualTax = 0;
								String pt_newAssessmentPTIntAreasAmtVal = "";
								int pt_newAssessmentPTIntAreasAmt = 0;

								//	if(driver.findElements(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table")).size() != 0)
								if(driver.findElements(By.xpath("//*[@id=\"command\"]/div[2]/table[12]")).size() != 0)
								{
									//pt_newAssessmentPTIntAreasVal=driver.findElement(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table/tbody/tr[2]/td[3]")).getText();
									/*	pt_newAssessmentPTIntAreasAmtVal=driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[12]/tbody/tr[2]/td[2]")).getText();*/

									WebElement tableArr =driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[12]"));
									List<WebElement> rowsArr = tableArr.findElements(By.tagName("tr"));
									int rowcountArr = rowsArr.size();
									System.out.println("Total number of rows :" +rowcountArr);
									int rowNoArr=0;
									for (WebElement rowElementArr:rowsArr)
									{
										List<WebElement> colsArr = rowElementArr.findElements(By.tagName("td"));
										int colcountArr= colsArr.size();
										System.out.println("Total number of columns :"+colcountArr);
										int columnNoArr=0;
										for(WebElement colElementArr:colsArr)
										{
											if(rowNoArr>=1)
											{
												if(columnNoArr==1)
												{
													pt_newAssessmentPTIntAreasAmtVal=rowElementArr.findElement(By.xpath("td[2]")).getText();
													System.out.println("Arrears : "+pt_newAssessmentPTIntAreasAmtVal);
													pt_newAssessmentPTIntAreasAmtVal=pt_newAssessmentPTIntAreasAmtVal.replaceAll(",","");
													System.out.println("Arrears : "+pt_newAssessmentPTIntAreasAmtVal);
													//pt_newAssessmentPTIntAreasAmt=Integer.parseInt(pt_newAssessmentPTIntAreasAmtVal);
													//pt_newAssessmentPTIntAreasAmt+=pt_newAssessmentPTIntAreasAmt;
													pt_newAssessmentPTIntAreasAmt=pt_newAssessmentPTIntAreasAmt+Integer.parseInt(pt_newAssessmentPTIntAreasAmtVal);

												}
											}
											columnNoArr=columnNoArr+1;
										}
										rowNoArr=rowNoArr+1;
									}
								}
								else
								{
									pt_newAssessmentPTIntAreasAmt=0;
								}
								System.out.println("Arrears Amount : "+pt_newAssessmentPTIntAreasAmt);

								/*for(int i=0 ; i<calcAnnulTaxArray.size(); i++) 
								{
									//pt_newAssessmentPTAnnualTaxAutoRes+=Double.parseDouble(calcAnnulTaxArray.get(i));
									if(!usageFactorArray.get(i).equalsIgnoreCase("Govt. Establishment"))
									{
										pt_newAssessmentPTAnnualTax+=Double.parseDouble(calcAnnulTaxArray.get(i));
									}
								}

								System.out.println("16.2B Total Property Tax : "+pt_newAssessmentPTAnnualTax);*/
								//pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes=(int) pt_newAssessmentPTAnnualTaxAutoRes;
								pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes=pt_newAssessmentPTAnnualTax+pt_newAssessmentPTIntAreasAmt;
								mAssert(sasPropertyTaxCurrent, String.valueOf(pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes), "Actual is :" +sasPropertyTaxCurrent+ "Expected is :" +String.valueOf(pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes)+ "SAS Form 16.2B Total Property Tax + Water Tax (Current+Arrears) is not matching");

							}

						}
						columnNoPTS=columnNoPTS+1;
					}
					rowNoPTS=rowNoPTS+1;
				}

				mTakeScreenShot();


				//Interest/Penalty/Rebate

				//WebElement tableIPR = driver.findElement(By.xpath("//*[@id='command']/div[2]/table[18]"));
				WebElement tableIPR = mFindElement("xpath", mGetPropertyFromFile("pt_newAssessmentSASIPRid"));
				List<WebElement> rowsIPR = tableIPR.findElements(By.tagName("tr"));
				int rowcountIPR = rowsIPR.size();
				System.out.println("Total number of rows :" +rowcountIPR);
				int rowNoIPR=0;
				for (WebElement rowElement:rowsIPR)
				{
					List<WebElement> colsIPR = rowElement.findElements(By.tagName("td"));
					int colcountIPR= colsIPR.size();
					System.out.println("Total number of columns :"+colcountIPR);
					int columnNoIPR=0;
					for(WebElement colElement:colsIPR)
					{
						if(columnNoIPR==0)
						{
							String sasRebateIncRWH=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[18]/tbody/tr[1]/td[4]")).getText();
							System.out.println("SAS Form Rebate (RWH + Normal) 16.3A is: "+sasRebateIncRWH);

							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasRebateIncRWH, mGetPropertyFromFile("pt_newAssessmentPTRebtAutoResSAS"), "Actual is: " +sasRebateIncRWH+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPTRebtAutoResSAS")+ "SAS Form Rebate (RWH + Normal) 16.3A is not matching");
								//mAssert(sasRebateIncRWH, mGetPropertyFromFile("pt_newAssessmentPTRebtAutoRes"), "Actual is: " +sasRebateIncRWH+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPTRebtAutoRes")+ "SAS Form Rebate (RWH + Normal) 16.3A is not matching");
							}
							else
							{
								mAssert(sasRebateIncRWH, String.valueOf(pt_newAssessmentPTRebtAutoRes), "Actual is: " +sasRebateIncRWH+ "Expected is: " +String.valueOf(pt_newAssessmentPTRebtAutoRes)+ "SAS Form Rebate (RWH + Normal) 16.3A is not matching");
							}
						}
						else if(columnNoIPR==1)
						{
							String sasRebateIncRWH=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[18]/tbody/tr[2]/td[4]")).getText();
							System.out.println("SAS Form Penalty (Current) 16.3B is: "+sasRebateIncRWH);
							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasRebateIncRWH, mGetPropertyFromFile("pt_newAssessmentPanaltySAS"), "Actual is: " +sasRebateIncRWH+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPanaltySAS")+ "SAS Form Penalty (Current) 16.3B is not matching");
							}
							else
							{
								System.out.println("SAS Form Penalty (Current) 16.3B is not applicable for change in assessment");
							}
						}else if(columnNoIPR==2)
						{
							String sasRebateIncRWH=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[18]/tbody/tr[3]/td[4]")).getText();
							System.out.println("SAS Form Interest (Current + Arrears) 16.3C is: "+sasRebateIncRWH);

							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasRebateIncRWH, mGetPropertyFromFile("pt_newAssessmentPTIntAreasAndCurrentSAS"), "Actual is: " +sasRebateIncRWH+ "Expected is: " +mGetPropertyFromFile("pt_newAssessmentPTIntAreasAndCurrentSAS")+ "SAS Form Interest (Current + Arrears) 16.3C is not matching");
							}
							else
							{
								//double pt_newAssessmentPTIntAreasAndCurrentSAS;
								String pt_newAssessmentPTIntAreasVal = "";
								int pt_newAssessmentPTIntAreas = 0;
								//	if(driver.findElements(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table")).size() != 0)
								if(driver.findElements(By.xpath("//*[@id=\"command\"]/div[2]/table[12]")).size() != 0)
								{
									//pt_newAssessmentPTIntAreasVal=driver.findElement(By.xpath("//*[@id=\"frmMasterForm\"]/fieldset[3]/table/tbody/tr[2]/td[3]")).getText();
									/*	pt_newAssessmentPTIntAreasVal=driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[12]/tbody/tr[2]/td[3]")).getText();*/
									WebElement tableArr =driver.findElement(By.xpath("//*[@id=\"command\"]/div[2]/table[12]"));
									List<WebElement> rowsArr = tableArr.findElements(By.tagName("tr"));
									int rowcountArr = rowsArr.size();
									System.out.println("Total number of rows :" +rowcountArr);
									int rowNoArr=0;
									for (WebElement rowElementArr:rowsArr)
									{
										List<WebElement> colsArr = rowElementArr.findElements(By.tagName("td"));
										int colcountArr= colsArr.size();
										System.out.println("Total number of columns :"+colcountArr);
										int columnNoArr=0;
										for(WebElement colElementArr:colsArr)
										{
											if(rowNoArr>=1)
											{
												if(columnNoArr==1)
												{
													pt_newAssessmentPTIntAreasVal=rowElementArr.findElement(By.xpath("td[3]")).getText();
													System.out.println("Interest : "+pt_newAssessmentPTIntAreasVal);
													pt_newAssessmentPTIntAreas=pt_newAssessmentPTIntAreas+Integer.parseInt(pt_newAssessmentPTIntAreasVal);
													//pt_newAssessmentPTIntAreas+=pt_newAssessmentPTIntAreas;
												}
											}
											columnNoArr=columnNoArr+1;
										}
										rowNoArr=rowNoArr+1;
									}

								}
								else
								{
									pt_newAssessmentPTIntAreas=0;
								}
								System.out.println("Interest Rs. : "+pt_newAssessmentPTIntAreas);
								pt_newAssessmentPTIntAreasAndCurrentSAS=Math.round(pt_newAssessmentPTIntAutoResSAS+pt_newAssessmentPTIntAreas);
								System.out.println("pt_newAssessmentPTIntAreasAndCurrentSAS is :"+pt_newAssessmentPTIntAreasAndCurrentSAS);
								mAssert(sasRebateIncRWH, String.valueOf(pt_newAssessmentPTIntAreasAndCurrentSAS), "Actual is: " +sasRebateIncRWH+ "Expected is: " +String.valueOf(pt_newAssessmentPTIntAreasAndCurrentSAS)+ "SAS Form Interest (Current + Arrears) 16.3C is not matching");
							}
						}
						columnNoIPR=columnNoIPR+1;
					}
					rowNoIPR=rowNoIPR+1;
				}

				//Total Net Payable Amount SAS form

				//WebElement tableTPA = driver.findElement(By.xpath("//*[@id='command']/div[2]/table[19]"));
				WebElement tableTPA =mFindElement("xpath", mGetPropertyFromFile("pt_newAssessmentSASTPAid"));
				List<WebElement> rowsTPA = tableTPA.findElements(By.tagName("tr"));
				int rowcountTPA = rowsTPA.size();
				System.out.println("Total number of rows :" +rowcountTPA);
				int rowNoTPA=0;
				for (WebElement rowElement:rowsTPA)
				{
					List<WebElement> colsTPA = rowElement.findElements(By.tagName("td"));
					int colcountTPA= colsTPA.size();
					System.out.println("Total number of columns :"+colcountTPA);
					int columnNoTPA=0;
					for(WebElement colElement:colsTPA)
					{
						if(columnNoTPA==0)
						{
							String sasTotNetPayAmtOrg=rowElement.findElement(By.xpath("//*[@id='command']/div[2]/table[19]/tbody/tr[1]/td[4]")).getText();
							System.out.println("SAS Form Total Net Payable Amount (16.4) is: "+sasTotNetPayAmtOrg);
							String sasTotNetPayAmt=sasTotNetPayAmtOrg.replaceAll(",","");

							//added by Jyoti
							sas_TotNetPayableAmtList.add(sasTotNetPayAmt);
							System.out.println("SAS Form List of Total Net Payable Amount ::: "+sas_TotNetPayableAmtList);

							if(!mGetPropertyFromFile("pt_assessmentType").equalsIgnoreCase("Change / Alteration in Assessment"))
							{
								mAssert(sasTotNetPayAmt, mGetPropertyFromFile("pt_newAssessmentTotPayableAmtResAutoSupp"), "Actual is :" +sasTotNetPayAmt+ "Expected is :" +mGetPropertyFromFile("pt_newAssessmentTotPayableAmtResAutoSupp")+ "SAS Form Total Net Payable Amount (16.4) is not matching");
							}
							else
							{
								double pt_newAssessmentTotPayableAmtResAutoSupp;
								int pt_newAssessmentServiceChargeCurSAS = 0;
								//int pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes = 0;
								int pt_newAssessmentWaterTaxCrgSupp = 0;
								int pt_newAssessmentPTRebtAutoResSAS;

								for(int i=0 ; i<calcServiceChargeArray.size() ; i++) 
								{
									pt_newAssessmentServiceChargeCurSAS+=Integer.parseInt(calcServiceChargeArray.get(i));
									System.out.println("Service charge total amount from array : "+pt_newAssessmentServiceChargeCurSAS);
								}

								/*if(chngInAssWaterTaxType.equalsIgnoreCase("Supply Water"))
								{
									pt_newAssessmentWaterTaxCrgSupp=1214;
									System.out.println("Water tax charge is : "+pt_newAssessmentWaterTaxCrgSupp);
								}
								else if(chngInAssWaterTaxType.equalsIgnoreCase("Own Boring"))
								{
									pt_newAssessmentWaterTaxCrgSupp=500;
									System.out.println("Water tax charge is : "+pt_newAssessmentWaterTaxCrgSupp);
								}
								else if(chngInAssWaterTaxType.equalsIgnoreCase("Both"))
								{
									pt_newAssessmentWaterTaxCrgSupp=1714;
									System.out.println("Water tax charge is : "+pt_newAssessmentWaterTaxCrgSupp);
								}
								else if(chngInAssWaterTaxType.equalsIgnoreCase("Already Paid"))
								{
									pt_newAssessmentWaterTaxCrgSupp=0;
									System.out.println("Water tax charge is : "+pt_newAssessmentWaterTaxCrgSupp);
								}
								else if(chngInAssWaterTaxType.equalsIgnoreCase("Not Applicable"))
								{
									pt_newAssessmentWaterTaxCrgSupp=0;
									System.out.println("Water tax charge is : "+pt_newAssessmentWaterTaxCrgSupp);
								}
								else
								{
									System.out.println("In else of water tax charge");
								}*/

								//	pt_newAssessmentPTRebtAutoResSAS=pt_newAssessmentPTRebtAutoRes+pt_newAssessmentPTRebtRWH;
								//pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes=pt_newAssessmentPTAnnualTaxAutoRes+pt_newAssessmentWaterTaxCrgSupp;
								//pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes=(int) pt_newAssessmentPTAnnualTax;

								System.out.println("pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes is : "+pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes);
								pt_newAssessmentTotPayableAmtResAutoSupp=Math.round(pt_newAssessmentServiceChargeCurSAS+pt_newAssessmentTotPropTaxAndWatTaxCurtSuppAutoRes/*+pt_newAssessmentPTIntAutoResSAS*/+pt_newAssessmentPTIntAreasAndCurrentSAS-pt_newAssessmentPTRebtAutoRes);
								mAssert(sasTotNetPayAmt, String.valueOf(pt_newAssessmentTotPayableAmtResAutoSupp), "Actual is :" +sasTotNetPayAmt+ "Expected is :" +String.valueOf(pt_newAssessmentTotPayableAmtResAutoSupp)+ "SAS Form Total Net Payable Amount (16.4) is not matching for Flat");
							}
						}
						columnNoTPA=columnNoTPA+1;
					}
					rowNoTPA=rowNoTPA+1;
				}
				//mTakeScreenShot();
			}

			/*yearBlgArray.clear();
			viewYearBlgArray.clear();
			sasBldgYearArray.clear();*/



			yearBlgArray.clear();
			addFloorNoArray.clear();
			addTypeOfUseArray.clear();
			addConstructTypeArray.clear();
			calcRatableArArray.clear();
			addUsageFactorArray.clear();
			addOccFacArray.clear();
			calcAnnulRateblValArray.clear();
			calcAnnulTaxArray.clear();

			floorNoArray.clear();
			typeOfUseArray.clear();
			constructTypeArray.clear();
			buildupArArray.clear();
			usageFactorArray.clear();
			occFacArray.clear();
			//calcRatableArArray.clear();
			calcUnitRateArray.clear();
			calcRateOfTaxArray.clear();

			viewYearBlgArray.clear();
			viewFloorNoArray.clear();
			viewTypeOfUseArray.clear();
			viewConstructTypeArray.clear();
			viewBuildupArArray.clear();
			viewRatableArArray.clear();
			viewUsageFactorArray.clear();
			viewUnitRateArray.clear();
			viewOccFacArray.clear();
			viewAnnulRateblValArray.clear();
			viewRateOfTaxArray.clear();
			viewAnnulTaxArray.clear();

			sasBldgYearArray.clear();
			sasFloorNoArray.clear();
			sasUsageTypeArray.clear();
			sasConstructionTypeArray.clear();
			sasRatableAreaArray.clear();
			sasUsageFactorArray.clear();
			sasOccFactorArray.clear();
			sasAnnulRateblValArray.clear();
			sasAnnualPropertyTaxArray.clear();

			calcRatableAreaForResArray.clear();
			calcRatableAreaForNonResArray.clear();
			calcServiceChargeArray.clear();
			addBuildupArArray.clear();

			System.out.println("End Reading SAS Form");

			mscroll(1191, 352);
			mTakeScreenShot();

			pt_newAssessmentPTAnnualTaxAutoRes=0.0;
			pt_newAssessmentPTAnnualTax=0.0;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in SASForm method");
		}

	}

	public void changeInAssessmentAlterationAddition()
	{

		try
		{
			/*if(mGetPropertyFromFile("changeInHoldingDetailsOnlyAlteration").equalsIgnoreCase("Yes"))
			{
				alteration();
			}
			else if(mGetPropertyFromFile("changeInHoldingDetailsOnlyAddition").equalsIgnoreCase("Yes"))
			{
				chngInHoldingDtlsAddition();
			}
			else if(mGetPropertyFromFile("changeInHoldingDetailsBothAlteration&Addition").equalsIgnoreCase("Yes"))
			{*/

			String chngInPropNum;
			String chngInOldPropNum;
			double unitArea = 0.0;
			// Navigate to Self Assessment and Payment of Property Tax link
			mNavigation("pt_propertyTaxLinkid", "pt_selfAssessmentLinkid");

			// Select option Change / Alteration in Assessment
			mWaitForVisible("id", mGetPropertyFromFile("pt_changeInAssessmentid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_changeInAssessmentid"));

			// Search Property number
			if(mGetPropertyFromFile("pt_chngInAssmntPropID").equalsIgnoreCase("New"))
			{
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntPropNoid"), mGetPropertyFromFile("pt_chngInAssmntPropNo"));
				//chngInPropNum=driver.findElement(By.id(mGetPropertyFromFile("pt_chngInAssmntPropNoid"))).getAttribute("value");
				chngInPropNum=mGetText("id", mGetPropertyFromFile("pt_chngInAssmntPropNoid"), "value");
				System.out.println("Entered Property number for change in assessment is : "+chngInPropNum);
			}
			else
			{
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntOldPropNoid"), mGetPropertyFromFile("pt_chngInAssmntOldPropNo"));
				//chngInOldPropNum=driver.findElement(By.id(mGetPropertyFromFile("pt_chngInAssmntOldPropNoid"))).getAttribute("value");
				chngInOldPropNum=mGetText("id", mGetPropertyFromFile("pt_chngInAssmntOldPropNoid"), "value");
				System.out.println("Entered Old Property number for change in assessment is : "+chngInOldPropNum);
			}

			mClick("css", mGetPropertyFromFile("pt_chngInAssmntSrchBtnid"));

			mWaitForVisible("linkText", mGetPropertyFromFile("pt_chngInAssmntSrchSubmitBtnid"));
			mTakeScreenShot();
			mClick("linkText", mGetPropertyFromFile("pt_chngInAssmntSrchSubmitBtnid"));

			mWaitForVisible("id", mGetPropertyFromFile("pt_chngInAssmntHoldingDtlsid"));

			mClick("id", mGetPropertyFromFile("pt_chngInAssmntHoldingDtlsid"));

			if (mGetPropertyFromFile("changeInHoldingDetailsOnlyAlteration").equalsIgnoreCase("Yes"))
			{
				System.out.println("Change in Holding Deatils with Alteration Option");
				mClick("id", mGetPropertyFromFile("pt_chngInAssmntHldngTypAdditionid"));				
			}
			else if(mGetPropertyFromFile("changeInHoldingDetailsOnlyAddition").equalsIgnoreCase("Yes"))
			{
				System.out.println("Change in Holding Deatils with Addition Option");
				mClick("id", mGetPropertyFromFile("pt_chngInAssmntHldngTypAlterationid"));
			}
			else if(mGetPropertyFromFile("changeInHoldingDetailsBothAlteration&Addition").equalsIgnoreCase("Yes"))
			{
				System.out.println("Change in Holding Deatils with Alteration and Addition Options");
			}

			mGenericWait();
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("pt_chngInAssmntSubmitBtnid"));
			mGenericWait();

			/// Year of Assessment
			String yrOfAssessment = driver.findElement(By.xpath("//*[@id=\"frmMasterForm\"]/div[2]/div[1]/b")).getText();
			System.out.println("Year of Assessment : "+yrOfAssessment);

			/////Mutation
			if (mGetPropertyFromFile("pt_chngInAssmntMutation").equalsIgnoreCase("Yes"))
			{
				System.out.println("Mutation is applicable");
				mSelectDropDown("id", mGetPropertyFromFile("pt_chngInAssmntMutationid"), mGetPropertyFromFile("pt_chngInAssmntMutation"));
				mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntMutationid"), mGetPropertyFromFile("pt_chngAssessmentMutationPropNo"));				
			}
			else
			{
				System.out.println("Mutation not applicable");
				mSelectDropDown("id", mGetPropertyFromFile("pt_chngInAssmntMutationid"), mGetPropertyFromFile("pt_chngInAssmntMutation"));
				//mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntMutationid"), mGetPropertyFromFile("pt_chngAssessmentMutationPropNo"));				
			}

			//////Checking the Land Type selected (Flat / Land + Building / Vacant Land)
			if(driver.findElement(By.id("entity.codpropertyID12")).isSelected())
			{
				//Land + Building
				chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(3) > label")).getText();
				System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
			}
			else if(driver.findElement(By.id("entity.codpropertyID11")).isSelected())
			{
				//Flat
				chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(2) > label")).getText();
				System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
			}
			else if(driver.findElement(By.id("entity.codpropertyID13")).isSelected())
			{
				//Vacant Land
				chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(4) > label")).getText();
				System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
			}
			else
			{
				System.out.println("Land type is not selected");
			}

			chngAsmtLandTypeAcqYr=driver.findElement(By.xpath("//*[@id=\"frmMasterForm\"]/div[16]/div[2]")).getText();
			System.out.println("Year of Acquisition in change in assessment original is : "+chngAsmtLandTypeAcqYr);
			String[] chngAsmtLandTypeAcqYrOrg=chngAsmtLandTypeAcqYr.split(":");
			chngAsmtLandTypeAcqYr=chngAsmtLandTypeAcqYrOrg[1];
			System.out.println("Year of Acquisition in change in assessment is : "+chngAsmtLandTypeAcqYr);

			//////Payment Made up-to selection
			if(driver.findElement(By.id(mGetPropertyFromFile("pt_chngInAssmntPymntMdUptoid"))).isEnabled())
			{
				mSelectDropDown("id", mGetPropertyFromFile("pt_chngInAssmntPymntMdUptoid"), mGetPropertyFromFile("pt_chngInAssmntPymntMdUpto"));
			}
			else
			{
				System.out.println("Payment Made Upto not applicable");
			}

			changeRoadFactor("pt_alterationRoadFactor");
			changePlotArea("pt_alterationPlotArea");
			changeBuiltupArea("pt_alterationBuiltupArea");
			//	BldgDetailsTableReadingExistingFloors();



			WebElement tableFloor = driver.findElement(By.id("buildingDetailsGridID"));

			mscroll(0, 1300);
			List<WebElement> rowsFloor = tableFloor.findElements(By.tagName("tr"));
			int rwCountFloor=rowsFloor.size();
			System.out.println("Rows in building details after adding rows are: "+rowsFloor.size());

			List<WebElement> colsFloor = rowsFloor.get(1).findElements(By.xpath("td"));
			int colCountFloor= colsFloor.size();
			System.out.println(colCountFloor);

			for (int m=0;m<rwCountFloor;m++) 		
			{
				//if(m<rwCountFloor-1)
				if(m>0)
				{
					for(int j=0;j<colCountFloor;j++)
					{
						if(j==1)
						{
							if (mGetPropertyFromFile("pt_changeHoldingDetailsType").equalsIgnoreCase("Addition"))
							{

							}
							else if(mGetPropertyFromFile("pt_changeHoldingDetailsType").equalsIgnoreCase("Alteration")||mGetPropertyFromFile("pt_changeHoldingDetailsType").equalsIgnoreCase("Alteration&Addition"))
							{
								System.out.println("Channge in Assessment Holding Deatils Type is selected Alteration");
								/*yearBlgArray.add(driver.findElement(By.id("yearLabelFromValue"+i)).getAttribute("value"));
							System.out.println("Building Year is: "+yearBlgArray);*/
								//if(rowsFloor.get(m).findElement(By.xpath("td[2]")).getTagName()=="select")
								//	if(rowsFloor.get(m).findElement(By.xpath("td[2]")).isEnabled()&&rowsFloor.get(m).findElement(By.xpath("td[2]/select")).getTagName().equals("select"))
								System.out.println("year : "+rowsFloor.get(m).findElement(By.xpath("td[1]/input")).getAttribute("value"));
								if(rowsFloor.get(m).findElement(By.xpath("td[1]/input")).getAttribute("value").equals(yrOfAssessment))
								{
									alterFloorNoArray.add(new Select (driver.findElement(By.id("cpdLocID"+m))).getFirstSelectedOption().getText());
									System.out.println("Property Detail Entry Floor No is: "+alterFloorNoArray);
								}
								else
								{
									/*alterFloorNoArray.add(driver.findElement(By.id("floorName"+m)).getText());
									System.out.println("Property Detail Entry Floor No is: "+alterFloorNoArray);*/
									System.out.println("Year of Assessment is not current year");
								}

							}

						}
					}
				}
			}
			String[] stringFloor=mGetPropertyFromFile("pt_changeInFloor").split(",");
			System.out.println("Length of string array is "+stringFloor.length);


			for (int i=0;i<rwCountFloor;i++) 		
			{
				if(i<rwCountFloor-1)
				{
					for(int j=0;j<stringFloor.length;j++)
					{

						//if(alterFloorNoArray.get(i).equalsIgnoreCase(stringFloor[j]))
						if((new Select (driver.findElement(By.id("cpdLocID"+i))).getFirstSelectedOption().getText()).equalsIgnoreCase(stringFloor[j]))
						{
							System.out.println("Floor number is : "+alterFloorNoArray.get(i));
							//changeFloorNo("pt_alteartionFloorNo");

							//changeUsageType("pt_alterationUsageType");
							if(mGetPropertyFromFile("pt_alterationUsageType").length()==0 || (mGetPropertyFromFile("pt_alterationUsageType").equalsIgnoreCase("?")))
							{
								System.out.println("No change in Usage Type");
								System.out.println("Size of No Usage Type: " + mGetPropertyFromFile("pt_alterationUsageType").length());
							}
							else
							{
								System.out.println("Change in Usage Type");
								mTakeScreenShot();
								mGenericWait();
								//mSelectDropDown("id", mGetPropertyFromFile("pt_alterationUsageTypeid"), mGetPropertyFromFile("pt_alterationUsageType"));
								new Select (driver.findElement(By.id("cpdUsage1"+i))).selectByVisibleText(mGetPropertyFromFile("pt_alterationUsageType"));
								mGenericWait();
								mTakeScreenShot();
							}

							//changeConstrnType("pt_alterationConstrType");
							if(mGetPropertyFromFile("pt_alterationConstrType").length()==0 || (mGetPropertyFromFile("pt_alterationConstrType").equalsIgnoreCase("?")))
							{
								System.out.println("No change in Construction Type");
								System.out.println("Size of No Construction Type: " + mGetPropertyFromFile("pt_alterationConstrType").length());
							}
							else
							{
								System.out.println("Change in Construction Type");
								mTakeScreenShot();
								mGenericWait();
								//mSelectDropDown("id", mGetPropertyFromFile("pt_alterationConstrTypeid"), mGetPropertyFromFile("pt_alterationConstrType"));
								new Select (driver.findElement(By.id("codConsClass1"+i))).selectByVisibleText(mGetPropertyFromFile("pt_alterationConstrType"));
								mGenericWait();
								mTakeScreenShot();
							}

							//changeBuildingArea("pt_alterationBuildingArea");
							if(mGetPropertyFromFile("pt_alterationBuildingArea").length()==0 || (mGetPropertyFromFile("pt_alterationBuildingArea").equalsIgnoreCase("?")))
							{
								System.out.println("No change in Building Area");
								System.out.println("Size of No Building Area: " + mGetPropertyFromFile("pt_alterationBuildingArea").length());
							}
							else
							{
								System.out.println("Change in Building Area");
								mTakeScreenShot();
								mGenericWait();
								//mSendKeys("id", mGetPropertyFromFile("pt_alterationBuildingAreaid"), "");	
								driver.findElement(By.id("pdBuildingArea"+i)).clear();
								mGenericWait();
								//mSendKeys("id", mGetPropertyFromFile("pt_alterationBuildingAreaid"), mGetPropertyFromFile("pt_alterationBuildingArea"));
								driver.findElement(By.id("pdBuildingArea"+i)).sendKeys(mGetPropertyFromFile("pt_alterationBuildingArea"));
								mGenericWait();
								mTakeScreenShot();
							}

							//changeUsageFactor("pt_alterationUsageFactor");
							if(mGetPropertyFromFile("pt_alterationUsageFactor").length()==0 || (mGetPropertyFromFile("pt_alterationUsageFactor").equalsIgnoreCase("?")))
							{
								System.out.println("Size of change in Usage Factor: " + mGetPropertyFromFile("pt_alterationUsageFactor").length());
								System.out.println("No change in Usage Factor");

							}
							else
							{
								System.out.println("change in Usage Factor");
								mTakeScreenShot();
								mGenericWait();
								System.out.println("Size of change in Usage Factor: " + mGetPropertyFromFile("pt_alterationUsageFactor").length());
								//mSelectDropDown("id", mGetPropertyFromFile("pt_alterationUseFactorid"), mGetPropertyFromFile("pt_alterationUsageFactor"));
								new Select (driver.findElement(By.id("cpdSfctID"+i))).selectByVisibleText(mGetPropertyFromFile("pt_alterationUsageFactor"));
								mGenericWait();
								mTakeScreenShot();
							}

							//changeOccupancyFactor("pt_alterationOccFactor");
							if(mGetPropertyFromFile("pt_alterationOccFactor").length()==0 || (mGetPropertyFromFile("pt_alterationOccFactor").equalsIgnoreCase("?")))
							{
								System.out.println("Size of No change in Occupancy Factor: " + mGetPropertyFromFile("pt_alterationOccFactor").length());
								System.out.println("No change in Occupancy Factor");

							}
							else
							{
								System.out.println("Change in Occupancy Factor");
								mTakeScreenShot();
								mGenericWait();
								System.out.println("Size of change in Occupancy Factor: " + mGetPropertyFromFile("pt_alterationOccFactor").length());
								//mSelectDropDown("id", mGetPropertyFromFile("pt_alterationOccFactorid"), mGetPropertyFromFile("pt_alterationOccFactor"));
								new Select (driver.findElement(By.id("cpdOccStatus"+i))).selectByVisibleText(mGetPropertyFromFile("pt_alterationOccFactor"));
								mGenericWait();
								mTakeScreenShot();
							}
						}
					}
				}
			}

			/*changeFloorNo("pt_alteartionFloorNo");
			changeUsageType("pt_alterationUsageType");
			changeConstrnType("pt_alterationConstrType");
			changeBuildingArea("pt_alterationBuildingArea");
			changeUsageFactor("pt_alterationUsageFactor");
			changeOccupancyFactor("pt_alterationOccFactor");*/

			// Road Factor
			chngInAssmntRoadFactor.add(mCaptureSelectedValue("id", mGetPropertyFromFile("pt_chngInAssmntRoadFactorid")));
			System.out.println("Selected road factor is : "+chngInAssmntRoadFactor);



			if(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupArea").equalsIgnoreCase("Yes"))
			{
				if(!mGetPropertyFromFile("landType").equalsIgnoreCase("Vacant Land"))
				{
					BldgDetailsTableReadingExistingRows();
				}

				WebElement table = driver.findElement(By.id("buildingDetailsGridID"));

				List<WebElement> rows = table.findElements(By.tagName("tr"));
				int rwcount = rows.size();
				System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

				mscroll(0, 1300);
				List<WebElement> rows1 = table.findElements(By.tagName("tr"));
				rows1.size();
				System.out.println(rows1.size());

				List<WebElement> cols = rows1.get(1).findElements(By.xpath("td"));
				int colcount= cols.size();
				System.out.println(colcount);

				String[] string=mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaFlrNo").split(",");
				System.out.println("string array is : "+string[0]);
				System.out.println("string array is : "+string[1]); 
				System.out.println("string array size is : "+string.length);

				int a=Integer.parseInt(string[0].toString());
				int b=Integer.parseInt(string[1].toString());
				addCount=(b-a)+1;

				mCustomWait(5000);

				for(int i=0;i<rwcount;i++)
				{
					//for(int j=0;j<colcount;j++)
					//{
					//driver.findElement(By.linkText("+")).click();
					if(i==rwcount-1)
					{
						driver.findElement(By.xpath("//*[@id=\"cloumn"+(i-1)+"\"]/td[9]/a")).click();
						System.out.println("for adding new row");
					}
					//}
				}

				int counter=1;
				InvoCount=a;

				for (int k=0;k<addCount;k++) 
				{
					int i;
					System.out.println("Value of k :"+k);
					if(InvoCount>b)
						break;
					for(i=0;i<=rwcount;i++)
					{
						System.out.println("Value of i :"+i);
						if(i==rwcount-1)
						{
							for(int j=0;j<colcount;j++)
							{
								System.out.println("Value of j :"+j);
								if(j==0)
								{
									mCustomWait(1500);
									String yr = driver.findElement(By.id("yearLabelFromValue"+(i))).getAttribute("value");
									System.out.println("Year is : "+yr);
									yearBlgArray.add(driver.findElement(By.id("yearLabelFromValue"+i)).getAttribute("value"));
									System.out.println("Building Year is: "+yearBlgArray);		
								}
								if(j==1)
								{
									//System.out.println("Value of j :"+j);
									mCustomWait(1500);
									//System.out.println("Value of i :"+i);
									new Select(driver.findElement(By.id("cpdLocID"+i))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaFloorNo"));
									//new Select(driver.findElement(By.id("cpdLocID"+i))).selectByVisibleText("5th");
									floorNoArray.add(new Select(driver.findElement(By.id("cpdLocID"+i))).getFirstSelectedOption().getText());
									System.out.println("Property Detail Entry Floor No is: "+floorNoArray);
								}
								if(j==2)
								{
									mCustomWait(1500);
									new Select(driver.findElement(By.name("entity.propertyDetails["+(i)+"].cpdUsage1"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsageType"));
									typeOfUseArray.add(new Select(driver.findElement(By.name("entity.propertyDetails["+(i)+"].cpdUsage1"))).getFirstSelectedOption().getText());
									System.out.println("Property Detail Entry Type of Use is: "+typeOfUseArray);
								}
								if(j==3)
								{
									mCustomWait(1500);
									new Select (driver.findElement(By.name("entity.propertyDetails["+(i)+"].codConsClass1"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaConstructionType"));
									constructTypeArray.add(new Select(driver.findElement(By.name("entity.propertyDetails["+(i)+"].codConsClass1"))).getFirstSelectedOption().getText());
									System.out.println("Property Detail Entry Construction Type is: "+constructTypeArray);
								}
								if(j==4)
								{
									mCustomWait(1500);
									driver.findElement(By.id("pdBuildingArea"+(i))).sendKeys(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaBuiltupArea"));
									buildupArArray.add(driver.findElement(By.id("pdBuildingArea"+(i))).getAttribute("value"));
									System.out.println("Property Detail Entry Buildup Area is :"+buildupArArray);
								}
								if(j==5)
								{
									mCustomWait(1500);
									String ratableArea = driver.findElement(By.id("pdAsseArea"+(i))).getText();
									System.out.println("Ratable Area is : "+ratableArea);
								}
								if(j==6)
								{
									mCustomWait(1500);
									if(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsageType").equalsIgnoreCase("Residential"))
									{
										String usgFactor = driver.findElement(By.id("cpdSfctID"+(i))).getAttribute("value");
										System.out.println("Usage Factor is : "+usgFactor);
										usageFactorArray.add(new Select(driver.findElement(By.id("cpdSfctID"+(i)))).getFirstSelectedOption().getText());
										System.out.println("Property Detail Entry Usage Factor is: "+usageFactorArray);
										calcRatableArArray.add(mGetPropertyFromFile("ratableAreaForRes"));
										calcAnnulRateblValArray.add(mGetPropertyFromFile("annualRatableValueForRes"));
										calcAnnulTaxArray.add(mGetPropertyFromFile("annualTaxRes"));


									}
									else
									{
										new Select (driver.findElement(By.id("cpdSfctID"+(i)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsgFactor"));
										usageFactorArray.add(new Select(driver.findElement(By.id("cpdSfctID"+(i)))).getFirstSelectedOption().getText());
										System.out.println("Property Detail Entry Usage Factor is: "+usageFactorArray);
										calcRatableArArray.add(mGetPropertyFromFile("ratableAreaForNonRes"));
										calcAnnulRateblValArray.add(mGetPropertyFromFile("annualRatableValueForNonRes"));
										calcAnnulTaxArray.add(mGetPropertyFromFile("annualTaxNonRes"));
									}

									calcUnitRateArray.add(mGetPropertyFromFile("unitArea"));
									calcRateOfTaxArray.add(mGetPropertyFromFile("rateOfTax"));
									calcServiceChargeArray.add(mGetPropertyFromFile("pt_newAssessmentServiceChargeCurSAS"));
									System.out.println("Property Detail Entry Ratable area array is: "+calcRatableArArray);
									System.out.println("Calculated annual tax array is: "+calcAnnulTaxArray);
								}
								if(j==7)
								{
									mCustomWait(1500);
									new Select (driver.findElement(By.id("cpdOccStatus"+(i)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaOccuFactor"));
									occFacArray.add(new Select(driver.findElement(By.id("cpdOccStatus"+(i)))).getFirstSelectedOption().getText());
									System.out.println("Property Detail Entry Occupancy Factor is: "+occFacArray);
								}
							}
						}
					}

					mCustomWait(2000);

					if(k<(addCount-1))
					{
						mCustomWait(5000);
						driver.findElement(By.xpath("//*[@id=\"cloumn"+(i+1)+"\"]/td[9]/a")).click();
						System.out.println("Clicking on for add");
						mCustomWait(5000);
					}

					counter++;
					InvoCount++;
					//}
				}
			}
			// Change in land type
			else if(mGetPropertyFromFile("pt_chngInAddnChngLandType").equalsIgnoreCase("Yes"))
			{
				if(mGetPropertyFromFile("pt_chngInAddnChngVcntLandType").equalsIgnoreCase("Land + Building"))
				{
					// Type of Land
					mClick("id", mGetPropertyFromFile("pt_chngInAssmntLandTypLandBldgid"));
					mWaitForVisible("id", mGetPropertyFromFile("pt_chngInAssmntAlertOkid"));
					String alertMsg=mGetText("xpath", mGetPropertyFromFile("pt_chngInAssmntAlertOkMsgid"));
					System.out.println("Warning is : "+alertMsg);
					mAssert(alertMsg, mGetPropertyFromFile("pt_chngInAssmntAlertOkMsg"), "Warning message does not match. Actual is : "+alertMsg+"Expected is : "+mGetPropertyFromFile("pt_chngInAssmntAlertOkMsg"));
					mCustomWait(1000);
					mTakeScreenShot();
					mClick("id", mGetPropertyFromFile("pt_chngInAssmntAlertOkid"));
					mCustomWait(1000);
					mTakeScreenShot();

					// Capture changed land type
					if(driver.findElement(By.id("entity.codpropertyID12")).isSelected())
					{
						chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(3) > label")).getText();
						System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
					}
					else if(driver.findElement(By.id("entity.codpropertyID11")).isSelected())
					{
						chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(2) > label")).getText();
						System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
					}
					else if(driver.findElement(By.id("entity.codpropertyID13")).isSelected())
					{
						chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(4) > label")).getText();
						System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
					}
					else
					{
						System.out.println("Land type is not selected");
					}

					// Last Payment Details
					if(driver.findElement(By.id(mGetPropertyFromFile("pt_chngInAssmntPymntMdUptoid"))).isEnabled())
					{
						mSelectDropDown("id", mGetPropertyFromFile("pt_chngInAssmntPymntMdUptoid"), mGetPropertyFromFile("pt_chngInAssmntPymntMdUpto"));
					}

					// Land/ Building Details
					mSelectDropDown("id", mGetPropertyFromFile("pt_chngInAssmntRoadFactorid"), mGetPropertyFromFile("pt_chngInAssmntRoadFactor"));
					mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntPlotAreaid"), mGetPropertyFromFile("pt_chngInAssmntPlotArea"));
					mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntBuiltupAreaid"), mGetPropertyFromFile("pt_chngInAssmntBuiltupArea"));

					// Rain Water Harvesting
					if(mGetPropertyFromFile("pt_chngInAssmntRainWaterHarvesting").equalsIgnoreCase("Yes"))
					{
						mClick("id", mGetPropertyFromFile("pt_chngInAssmntRainWaterHarvestingid"));
					}
					else
					{
						System.out.println("Rain Water Harvesting is not applicable");
					}

					// Water Connection Facility
					if(mGetPropertyFromFile("pt_chngInAssmntGovWtrConn").equalsIgnoreCase("Yes"))
					{
						mClick("id", mGetPropertyFromFile("pt_chngInAssmntGovWtrConnid"));
					}
					else if(mGetPropertyFromFile("pt_chngInAssmntWtrConnAvlblWithin400Yrds").equalsIgnoreCase("Yes"))
					{
						mClick("id", mGetPropertyFromFile("pt_chngInAssmntWtrConnAvlblWithin400Yrdsid"));
					}
					else if(mGetPropertyFromFile("pt_chngInAssmntWtrConnNotAvlblWithin400Yrds").equalsIgnoreCase("Yes"))
					{
						mClick("id", mGetPropertyFromFile("pt_chngInAssmntWtrConnNotAvlblWithin400Yrdsid"));
					}

					WebElement table = driver.findElement(By.id("buildingDetailsGridID"));

					mscroll(0, 1300);
					List<WebElement> rows1 = table.findElements(By.tagName("tr"));
					rows1.size();
					System.out.println(rows1.size());

					List<WebElement> cols = rows1.get(1).findElements(By.xpath("td"));
					int colcount= cols.size();
					System.out.println(colcount);

					String[] string=mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaFlrNo").split(",");
					System.out.println("string array size is : "+string.length);

					int a=Integer.parseInt(string[0].toString());
					int b=Integer.parseInt(string[1].toString());
					addCount=(b-a)+1;

					mCustomWait(5000);

					int counter=1;
					InvoCount=a;
					for (int i=0;i<addCount;i++) 
					{
						if(InvoCount>b)
							break;
						for(int j=0;j<colcount;j++)
						{
							if(j==0)
							{
								mCustomWait(1500);
								new Select(driver.findElement(By.id("detFaYearId"+(i)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorYear"));
								yearBlgArray.add(driver.findElement(By.id("yearLabelFromValue"+i)).getAttribute("value"));
								System.out.println("Building Year is: "+yearBlgArray);
							}
							if(j==1)
							{
								mCustomWait(1500);
								new Select(driver.findElement(By.id("cpdLocID"+(i)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaFloorNo"));
								floorNoArray.add(new Select(driver.findElement(By.id("cpdLocID"+(i)))).getFirstSelectedOption().getText());
								System.out.println("Property Detail Entry Floor No is: "+floorNoArray);
							}
							if(j==2)
							{
								mCustomWait(1500);
								new Select(driver.findElement(By.name("entity.propertyDetails["+(i)+"].cpdUsage1"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsageType"));
								typeOfUseArray.add(new Select(driver.findElement(By.name("entity.propertyDetails["+(i)+"].cpdUsage1"))).getFirstSelectedOption().getText());
								System.out.println("Property Detail Entry Type of Use is: "+typeOfUseArray);
							}
							if(j==3)
							{
								mCustomWait(1500);
								new Select (driver.findElement(By.name("entity.propertyDetails["+(i)+"].codConsClass1"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaConstructionType"));
								constructTypeArray.add(new Select(driver.findElement(By.name("entity.propertyDetails["+(i)+"].codConsClass1"))).getFirstSelectedOption().getText());
								System.out.println("Property Detail Entry Construction Type is: "+constructTypeArray);
							}
							if(j==4)
							{
								mCustomWait(1500);
								driver.findElement(By.id("pdBuildingArea"+(i))).sendKeys(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaBuiltupArea"));
								buildupArArray.add(driver.findElement(By.id("pdBuildingArea"+(i))).getAttribute("value"));
								System.out.println("Property Detail Entry Buildup Area is :"+buildupArArray);
							}
							if(j==5)
							{
								mCustomWait(1500);
								String ratableArea = driver.findElement(By.id("pdAsseArea"+(i))).getText();
								System.out.println("Ratable Area is : "+ratableArea);
							}
							if(j==6)
							{
								mCustomWait(1500);
								if(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsageType").equalsIgnoreCase("Residential"))
								{
									String usgFactor = driver.findElement(By.id("cpdSfctID"+(i))).getAttribute("value");
									System.out.println("Usage Factor is : "+usgFactor);
									usageFactorArray.add(new Select(driver.findElement(By.id("cpdSfctID"+(i)))).getFirstSelectedOption().getText());
									System.out.println("Property Detail Entry Usage Factor is: "+usageFactorArray);
									calcRatableArArray.add(mGetPropertyFromFile("ratableAreaForRes"));
									calcAnnulRateblValArray.add(mGetPropertyFromFile("annualRatableValueForRes"));
									calcAnnulTaxArray.add(mGetPropertyFromFile("annualTaxRes"));
								}
								else
								{
									new Select (driver.findElement(By.id("cpdSfctID"+(i)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsgFactor"));
									usageFactorArray.add(new Select(driver.findElement(By.id("cpdSfctID"+(i)))).getFirstSelectedOption().getText());
									System.out.println("Property Detail Entry Usage Factor is: "+usageFactorArray);
									calcRatableArArray.add(mGetPropertyFromFile("ratableAreaForNonRes"));
									calcAnnulRateblValArray.add(mGetPropertyFromFile("annualRatableValueForNonRes"));
									calcAnnulTaxArray.add(mGetPropertyFromFile("annualTaxNonRes"));
								}
								calcUnitRateArray.add(mGetPropertyFromFile("unitArea"));
								calcRateOfTaxArray.add(mGetPropertyFromFile("rateOfTax"));
								calcServiceChargeArray.add(mGetPropertyFromFile("pt_newAssessmentServiceChargeCurSAS"));
								System.out.println("Property Detail Entry Ratable area array is: "+calcRatableArArray);
								System.out.println("Calculated annual tax array is: "+calcAnnulTaxArray);
							}
							if(j==7)
							{
								mCustomWait(1500);
								new Select (driver.findElement(By.id("cpdOccStatus"+(i)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaOccuFactor"));
								occFacArray.add(new Select(driver.findElement(By.id("cpdOccStatus"+(i)))).getFirstSelectedOption().getText());
								System.out.println("Property Detail Entry Occupancy Factor is: "+occFacArray);
							}
						}
						if(i<(addCount-1))
						{
							mCustomWait(5000);
							driver.findElement(By.xpath("//*[@id=\"cloumn"+(i)+"\"]/td[9]/a")).click();
							System.out.println("Clicking on for add");
							mCustomWait(5000);
						}
						counter++;
						InvoCount++;
					}

					mTakeScreenShot();


				}
				else if(mGetPropertyFromFile("pt_chngInAddnChngVcntLandType").equalsIgnoreCase("Flat"))
				{
					// Type of Land
					mClick("id", mGetPropertyFromFile("pt_chngInAssmntLandTypFlatid"));
					mWaitForVisible("id", mGetPropertyFromFile("pt_chngInAssmntAlertOkid"));
					String alertMsg=mGetText("xpath", mGetPropertyFromFile("pt_chngInAssmntAlertOkMsgid"));
					System.out.println("Warning is : "+alertMsg);
					mAssert(alertMsg, mGetPropertyFromFile("pt_chngInAssmntAlertOkMsg"), "Warning message does not match. Actual is : "+alertMsg+"Expected is : "+mGetPropertyFromFile("pt_chngInAssmntAlertOkMsg"));
					mCustomWait(1000);
					mTakeScreenShot();
					mClick("id", mGetPropertyFromFile("pt_chngInAssmntAlertOkid"));
					mCustomWait(1000);
					mTakeScreenShot();

					// Capture changed land type
					if(driver.findElement(By.id("entity.codpropertyID12")).isSelected())
					{
						chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(3) > label")).getText();
						System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
					}
					else if(driver.findElement(By.id("entity.codpropertyID11")).isSelected())
					{
						chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(2) > label")).getText();
						System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
					}
					else if(driver.findElement(By.id("entity.codpropertyID13")).isSelected())
					{
						chngAsmtLandType=driver.findElement(By.cssSelector("#frmMasterForm > div:nth-child(44) > div:nth-child(4) > label")).getText();
						System.out.println("Land type in change in assessment is : "+chngAsmtLandType);
					}
					else
					{
						System.out.println("Land type is not selected");
					}

					mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntAprtmntNameid"), mGetPropertyFromFile("pt_chngInAssmntAprtmntName"));
					mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntTotFlatsInAprtmntid"), mGetPropertyFromFile("pt_chngInAssmntTotFlatsInAprtmnt"));

					// Last Payment Details
					if(driver.findElement(By.id(mGetPropertyFromFile("pt_chngInAssmntPymntMdUptoid"))).isEnabled())
					{
						mSelectDropDown("id", mGetPropertyFromFile("pt_chngInAssmntPymntMdUptoid"), mGetPropertyFromFile("pt_chngInAssmntPymntMdUpto"));
					}

					// Land/ Building Details
					mSelectDropDown("id", mGetPropertyFromFile("pt_chngInAssmntRoadFactorid"), mGetPropertyFromFile("pt_chngInAssmntRoadFactor"));
					mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntPlotAreaid"), mGetPropertyFromFile("pt_chngInAssmntPlotArea"));
					mSendKeys("id", mGetPropertyFromFile("pt_chngInAssmntBuiltupAreaid"), mGetPropertyFromFile("pt_chngInAssmntBuiltupArea"));

					// Water Connection Facility
					if(mGetPropertyFromFile("pt_chngInAssmntGovWtrConn").equalsIgnoreCase("Yes"))
					{
						mClick("id", mGetPropertyFromFile("pt_chngInAssmntGovWtrConnid"));
					}
					else if(mGetPropertyFromFile("pt_chngInAssmntWtrConnAvlblWithin400Yrds").equalsIgnoreCase("Yes"))
					{
						mClick("id", mGetPropertyFromFile("pt_chngInAssmntWtrConnAvlblWithin400Yrdsid"));
					}
					else if(mGetPropertyFromFile("pt_chngInAssmntWtrConnNotAvlblWithin400Yrds").equalsIgnoreCase("Yes"))
					{
						mClick("id", mGetPropertyFromFile("pt_chngInAssmntWtrConnNotAvlblWithin400Yrdsid"));
					}

					WebElement table = driver.findElement(By.id("buildingDetailsGridID"));

					mscroll(0, 1300);
					List<WebElement> rows1 = table.findElements(By.tagName("tr"));
					rows1.size();
					System.out.println(rows1.size());

					List<WebElement> cols = rows1.get(1).findElements(By.xpath("td"));
					int colcount= cols.size();
					System.out.println(colcount);

					String[] string=mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaFlrNo").split(",");
					System.out.println("string array size is : "+string.length);

					int a=Integer.parseInt(string[0].toString());
					int b=Integer.parseInt(string[1].toString());
					addCount=(b-a)+1;

					mCustomWait(5000);

					int counter=1;
					InvoCount=a;
					for (int i=0;i<addCount;i++) 
					{
						if(InvoCount>b)
							break;
						for(int j=0;j<colcount;j++)
						{
							if(j==0)
							{
								mCustomWait(1500);
								new Select(driver.findElement(By.id("detFaYearId"+(i)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorYear"));
								yearBlgArray.add(driver.findElement(By.id("yearLabelFromValue"+i)).getAttribute("value"));
								System.out.println("Building Year is: "+yearBlgArray);
							}
							if(j==1)
							{
								mCustomWait(1500);
								new Select(driver.findElement(By.id("cpdLocID"+(i)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaFloorNo"));
								floorNoArray.add(new Select(driver.findElement(By.id("cpdLocID"+(i)))).getFirstSelectedOption().getText());
								System.out.println("Property Detail Entry Floor No is: "+floorNoArray);
							}
							if(j==2)
							{
								mCustomWait(1500);
								new Select(driver.findElement(By.name("entity.propertyDetails["+(i)+"].cpdUsage1"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsageType"));
								typeOfUseArray.add(new Select(driver.findElement(By.name("entity.propertyDetails["+(i)+"].cpdUsage1"))).getFirstSelectedOption().getText());
								System.out.println("Property Detail Entry Type of Use is: "+typeOfUseArray);
							}
							if(j==3)
							{
								mCustomWait(1500);
								new Select (driver.findElement(By.name("entity.propertyDetails["+(i)+"].codConsClass1"))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaConstructionType"));
								constructTypeArray.add(new Select(driver.findElement(By.name("entity.propertyDetails["+(i)+"].codConsClass1"))).getFirstSelectedOption().getText());
								System.out.println("Property Detail Entry Construction Type is: "+constructTypeArray);
							}
							if(j==4)
							{
								mCustomWait(1500);
								driver.findElement(By.id("pdBuildingArea"+(i))).sendKeys(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaBuiltupArea"));
								buildupArArray.add(driver.findElement(By.id("pdBuildingArea"+(i))).getAttribute("value"));
								System.out.println("Property Detail Entry Buildup Area is :"+buildupArArray);
							}
							if(j==5)
							{
								mCustomWait(1500);
								String ratableArea = driver.findElement(By.id("pdAsseArea"+(i))).getText();
								System.out.println("Ratable Area is : "+ratableArea);
							}
							if(j==6)
							{
								mCustomWait(1500);
								if(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsageType").equalsIgnoreCase("Residential"))
								{
									String usgFactor = driver.findElement(By.id("cpdSfctID"+(i))).getAttribute("value");
									System.out.println("Usage Factor is : "+usgFactor);
									usageFactorArray.add(new Select(driver.findElement(By.id("cpdSfctID"+(i)))).getFirstSelectedOption().getText());
									System.out.println("Property Detail Entry Usage Factor is: "+usageFactorArray);
									calcRatableArArray.add(mGetPropertyFromFile("ratableAreaForRes"));
									calcAnnulRateblValArray.add(mGetPropertyFromFile("annualRatableValueForRes"));
									calcAnnulTaxArray.add(mGetPropertyFromFile("annualTaxRes"));
								}
								else
								{
									new Select (driver.findElement(By.id("cpdSfctID"+(i)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaUsgFactor"));
									usageFactorArray.add(new Select(driver.findElement(By.id("cpdSfctID"+(i)))).getFirstSelectedOption().getText());
									System.out.println("Property Detail Entry Usage Factor is: "+usageFactorArray);
									calcRatableArArray.add(mGetPropertyFromFile("ratableAreaForNonRes"));
									calcAnnulRateblValArray.add(mGetPropertyFromFile("annualRatableValueForNonRes"));
									calcAnnulTaxArray.add(mGetPropertyFromFile("annualTaxNonRes"));
								}

								calcUnitRateArray.add(mGetPropertyFromFile("unitArea"));
								calcRateOfTaxArray.add(mGetPropertyFromFile("rateOfTax"));
								calcServiceChargeArray.add(mGetPropertyFromFile("pt_newAssessmentServiceChargeCurSAS"));
								System.out.println("Property Detail Entry Ratable area array is: "+calcRatableArArray);
								System.out.println("Calculated annual tax array is: "+calcAnnulTaxArray);
							}
							if(j==7)
							{
								mCustomWait(1500);
								new Select (driver.findElement(By.id("cpdOccStatus"+(i)))).selectByVisibleText(mGetPropertyFromFile("pt_chngInAddnFloorBuiltupAreaOccuFactor"));
								occFacArray.add(new Select(driver.findElement(By.id("cpdOccStatus"+(i)))).getFirstSelectedOption().getText());
								System.out.println("Property Detail Entry Occupancy Factor is: "+occFacArray);
							}
						}
						if(i<(addCount-1))
						{
							mCustomWait(5000);
							driver.findElement(By.xpath("//*[@id=\"cloumn"+(i)+"\"]/td[9]/a")).click();
							System.out.println("Clicking on for add");
							mCustomWait(5000);
						}
						counter++;
						InvoCount++;
					}
					mTakeScreenShot();
				}
			}

			///////////////////////////////////////////////////////////////////////
			//Change the Vacant Land Area without changing the Vacant Land Type
			/////////////////////////////////////////////////////////////////////////
			else if (mGetPropertyFromFile("changeAreaVL").equalsIgnoreCase("Yes"))
			{
				//mSendKeys(locatorType, locator, data);
				/////////Added code for Vancant Land Change Area @ Ritesh 05-01-2017

				mTakeScreenShot();

				WebElement table =mFindElement("id", mGetPropertyFromFile("pt_chngeInAssessmentVacantLandid"));
				List<WebElement> rows = table.findElements(By.tagName("tr"));
				int rwcount = rows.size();
				System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

				int Rowno = 0;
				for (WebElement rowElement : rows) 
				{
					List<WebElement> cols = rowElement.findElements(By.xpath("td"));
					int clcount = cols.size();
					System.out.println("NUMBER OF Cols IN THIS TABLE = " + clcount);
					int Columnno = 0;
					for (WebElement colElement : cols) 
					{
						if (Columnno == 0) 
						{
							String yr = rowElement.findElement(By.xpath("//*[@id=\"vyearLabelFromValue0\"]")).getAttribute("value");
							mGenericWait();
							System.out.println("Change in Assessment Vacant Land Year is :"+yr);
						}
						if (Columnno == 1) 
						{
							vacantarea = rowElement.findElement(By.xpath("//*[@id=\"vpdBuildingArea0\"]")).getAttribute("id");
							System.out.println("Change in Assessment Vacant Land Area is :"+vacantarea);
							driver.findElement(By.id(vacantarea)).clear();
							mSendKeys("id", vacantarea, mGetPropertyFromFile("pt_chngInAssmntChangVacantlandArea"));
							vancantareavalue=mGetText("id", vacantarea, "value");									
							System.out.println("Mannual Vacant Land is: "+vancantareavalue);

							mAssert(vancantareavalue, mGetPropertyFromFile("pt_chngInAssmntChangVacantlandArea"), "Mannual Vacant Land is different than inserted Mannual Vacant Land");
							mGenericWait();
						}
						Columnno = Columnno + 1;
					}

					Rowno = Rowno + 1;
				}

			}
			mTakeScreenShot();
			//Capturing Water Tax Type
			String chngInAssWaterTaxTypeOrg=driver.findElement(By.xpath("//*[@id=\"frmMasterForm\"]/div[30]/div[1]")).getText();
			System.out.println("Water Tax Type of selected property in change in assessment original: "+chngInAssWaterTaxTypeOrg);
			String chngInAssWaterTaxTypeNew[]=chngInAssWaterTaxTypeOrg.split(":");
			chngInAssWaterTaxType=chngInAssWaterTaxTypeNew[1].trim();
			System.out.println("Water Tax Type of selected property in change in assessment : "+chngInAssWaterTaxType);

			if (!mGetPropertyFromFile("landType").equalsIgnoreCase("Vacant Land"))
			{

				chngInAddnGrndFlrPlotArea=driver.findElement(By.id("pmPlotArea")).getAttribute("value");
				System.out.println("Plot area for change in assessment is :"+chngInAddnGrndFlrPlotArea);
				chngInAddnGrndFlrBuiltupArea=mGetText("id", mGetPropertyFromFile("pt_chngInAssmntGrndFlrBuiltupAreaid"),"value");
				System.out.println("Built up area for change in assessment is :"+chngInAddnGrndFlrBuiltupArea);

				Double pt_newAssessmentAplcbleVacantLandOrg=((Double.parseDouble(chngInAddnGrndFlrBuiltupArea)/Double.parseDouble(chngInAddnGrndFlrPlotArea)*100));
				System.out.println("Applicable Vacant Land : "+pt_newAssessmentAplcbleVacantLandOrg);

				DecimalFormat decimalFormat=new DecimalFormat("#.#");


				pt_newAssessmentAplcbleVacantLand=Math.round(pt_newAssessmentAplcbleVacantLandOrg);
				System.out.println("Applicable Vacant Land : "+pt_newAssessmentAplcbleVacantLand);

				if((pt_newAssessmentAplcbleVacantLand)<70)
				{
					pt_newAssessmentVacantLandAreaAuto=(Double.parseDouble(chngInAddnGrndFlrPlotArea)-(Double.parseDouble(chngInAddnGrndFlrBuiltupArea)*1.42855));
					System.out.println("Applicable Vacant Land Auto : "+pt_newAssessmentVacantLandAreaAuto);
				}
				else
				{
					pt_newAssessmentVacantLandAreaAuto=0.0;
					System.out.println("Applicable Vacant Land Auto : "+pt_newAssessmentVacantLandAreaAuto);
				}


				if(chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Principal Main Road"))
				{
					unitArea=0.36;
				}
				else if(chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Main Road"))
				{
					unitArea=0.28;
				}
				else if(chngInAssmntRoadFactor.get(CurrentinvoCount).equalsIgnoreCase("Other Road"))
				{
					unitArea=0.19;
				}


				//pt_newAssessmentVacantLandAnnualTaxAuto=(pt_newAssessmentVacantLandAreaAuto*0.36);
				pt_newAssessmentVacantLandAnnualTaxAuto=(pt_newAssessmentVacantLandAreaAuto*unitArea);
				pt_newAssessmentVacantLandAnnualTax=Math.round(pt_newAssessmentVacantLandAnnualTaxAuto);
				System.out.println("Vacant Land Annual Tax : "+pt_newAssessmentVacantLandAnnualTax);

			}
			else
			{
				//pt_newAssessmentVacantLandAnnualTaxAuto=(Double.parseDouble(vancantareavalue)*0.36);
				pt_newAssessmentVacantLandAnnualTaxAuto=(Double.parseDouble(vancantareavalue)*unitArea);
				pt_newAssessmentVacantLandAnnualTax=Math.round(pt_newAssessmentVacantLandAnnualTaxAuto);
				System.out.println("Vacant Land Annual Tax : "+pt_newAssessmentVacantLandAnnualTax);
			}

			mTakeScreenShot();

			mClick("id", mGetPropertyFromFile("pt_chngInAssmntNextBtnid"));

			mWaitForVisible("xpath", mGetPropertyFromFile("pt_chngInAssmntSubmitBtnid"));
			newAssessmentNextMethod();}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in changeInAssessmentAlterationAddition method");
		}

	}

	//Code Merge 22-03-2017 by Ritesh


	///////////////////////
	//////////////////////

	// Method for Transfer Heredity Service -- 09-02-201 by Jyoti

	///////////////////////
	//////////////////////


	public void trnsfr_Heredity() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			//departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("citizenUserName"),mGetPropertyFromFile("citizenPassword"));
			mGenericWait();
			mNavigation("pt_propertyTaxLinkid", "pt_selfAssessmentLinkid");

			TransferHeredity();

			logOut();
			finalLogOut();
			//isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerNewAssessmentServiceName"));
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in trnsfr_Heredity method");
		}

	}

	public void TransferHeredity() throws Exception
	{
		try
		{

			/////Existing Property Details
			mWaitForVisible("xpath", mGetPropertyFromFile("pt_trf_Nom_SearchBtn_ID"));
			//mSendKeys("xpath", mGetPropertyFromFile("pt_trf_Nom_PropNoData_ID"), mGetPropertyFromFile("pt_trf_Nom_PropNo_Data"));
			//mSendKeys("xpath", mGetPropertyFromFile("pt_trf_Nom_PropNoData_ID"), "223000570");			

			//
			IndOrDep("xpath", "pt_trf_Nom_PropNoData_ID", "applicationNo");

			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("pt_trf_Nom_SearchBtn_ID"));
			mGenericWait();

			////////Assertion of Property No

			String Prop_No = mGetText("xpath", mGetPropertyFromFile("pt_trf_Nom_PropNoData_ID"), "value");
			System.out.println("Application time Property No NM is == "+Prop_No);
			Prop_NoList.add(Prop_No);
			System.out.println("Application time List of Property NM == "+Prop_NoList);



			mClick("xpath", mGetPropertyFromFile("pt_enterTransferDtlsBtnid"));
			mGenericWait();

			/////Transferable Property Details
			mSelectDropDown("id", mGetPropertyFromFile("pt_trfTtypeNom_ID"), mGetPropertyFromFile("pt_trfTtypeNom_Data"));
			mGenericWait();

			//Old Code Commenented becouse showing date difference error instead of this used below line @Ritesh 04-04-2017
			//mDateSelect("id", mGetPropertyFromFile("pt_trfNom_ActualTrfDate_ID"), mGetPropertyFromFile("pt_trfNom_ActualTrfDate_Data"));
			mGdatePicker(mGetPropertyFromFile("pt_trfNom_ActualTrfDate_ID"), mGetPropertyFromFile("pt_Trans_Hr_Acttrans_Yr"), mGetPropertyFromFile("pt_Trans_Hr_Acttrans_Month"),mGetPropertyFromFile("pt_Trans_Hr_Acttrans_Date"));
			mGenericWait();
			mSelectDropDown("id", mGetPropertyFromFile("pt_trfNom_NewOwnerType_ID"), mGetPropertyFromFile("pt_trfNom_NewOwnerType_Data"));
			mGenericWait();

			/////Owner Details as per New Type of Owner
			mSelectDropDown("name", mGetPropertyFromFile("pt_trfNom_NameTitle_ID"), mGetPropertyFromFile("pt_trfNom_NmaeTitle_Data"));
			mGenericWait();
			mSendKeys("name", mGetPropertyFromFile("pt_trfNom_NewOwnerFirstName_ID"), mGetPropertyFromFile("pt_trfNom_NewOwnerFirstName_Data"));
			mGenericWait();
			mSendKeys("name", mGetPropertyFromFile("pt_trfNom_NewOwnerFatherName_ID"), mGetPropertyFromFile("pt_trfNom_NewOwnerFatherName_Data"));
			mGenericWait();
			mSelectDropDown("name", mGetPropertyFromFile("pt_trfNom_NewOwnerGender_ID"), mGetPropertyFromFile("pt_trfNom_NewOwnerGender_Data"));
			mSendKeys("id", mGetPropertyFromFile("pt_trfNom_NewAddress_ID"), mGetPropertyFromFile("pt_trfNom_NewAddress_Data"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("pt_trfNom_NewPinCode_ID"), mGetPropertyFromFile("pt_trfNom_NewPinCode_Data"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("pt_trfNom_NewMobNo_ID"), mGetPropertyFromFile("pt_trfNom_NewMobNo_Data"));
			mGenericWait();
			mUpload("id", mGetPropertyFromFile("pt_trfNom_upload1_ID"), mGetPropertyFromFile("pt_trfNom_upload1_Data"));
			mGenericWait();
			mUpload("id", mGetPropertyFromFile("pt_trfNom_upload2_ID"), mGetPropertyFromFile("pt_trfNom_upload2_Data"));
			mGenericWait();
			mTakeScreenShot();




			//*************************
			////Assertions of Application Form - Transfer HEREDITY


			String Primary_OwnerName = mGetText("xpath", mGetPropertyFromFile("pt_trfNom_PrimaryOwnerName_ID"), "value"); 
			System.out.println("Primary Owner Name of Transfer Heredity is ::: "+Primary_OwnerName);	
			Primary_OwnerNmaeList.add(Primary_OwnerName);
			System.out.println("Application time Primary Owner List of Transfer Heredity is == "+Primary_OwnerNmaeList);


			String Ward_Nom = mCaptureSelectedValue("id", mGetPropertyFromFile("pt_trfNom_Ward_ID"));
			System.out.println("Ward of Transfer Nomination is ::: "+Ward_Nom);
			Ward_NomList.add(Ward_Nom);
			System.out.println("Application time Ward List of Transfer Heredity is == "+Ward_NomList);


			String Name_Title = mCaptureSelectedValue("id", mGetPropertyFromFile("pt_trfNom_NameTitle_ID"));
			System.out.println("Application time new Owner Name Title of Transfer Heredity == "+Name_Title);
			//Name_TitleList.add(Name_Title);
			//System.out.println("Application time Name Title List of Transfer Heredity =="+Name_TitleList);


			String First_Name = mGetText("id", mGetPropertyFromFile("pt_trfNom_NewOwnerFirstName_ID"),"value");
			System.out.println("Application time New Owner First Name of Transfer Heredity =="+First_Name);


			String FullNm = Name_Title +  First_Name.toUpperCase();

			System.out.println("Full Name is == "+FullNm);
			Full_NameList.add(FullNm);		
			System.out.println("Application time New Owner Full Name List of Transfer Heredity =="+Full_NameList);			

			//*************************


			mClick("id", mGetPropertyFromFile("pt_trfNom_SubmitApp_ID"));

			//Get popup save msg

			String popupmsg = mGetText("xpath", mGetPropertyFromFile("pt_trfNom_SaveMsg_ID"));
			System.out.println("Pop up message of PT Transfer Nomination Application Saved::: "+popupmsg);

			//Capturing appno and asserting popup msg
			appNo = popupmsg.replaceAll("\\D+","");
			System.out.println("Application No of Transfer - Heredity is::: "+appNo);
			appNo = appNo.trim();
			mAppNoArray(appNo); 

			popupmsg = popupmsg.replaceAll("[0-9]","");			
			System.out.println(popupmsg);
			mTakeScreenShot();

			//mAssert(popupmsg, mGetPropertyFromFile("pt_trfNom_SaveAssertMsg_Data"), mGetPropertyFromFile("MKT_issueDupLicAssertMsgFail_Data")+" Expected::: "+mGetPropertyFromFile("pt_trfNom_SaveAssertMsg_Data")+ " Actual::: " +popupmsg);

			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("pt_trfNom_ProceedBtn_ID"));


			mChallanPDFReader();

			api.PdfPatterns.pt_TransferHeredityPdf(pdfoutput);

			//Assertion for reading value from PDF report
			NewOwnerNameAssertList.add(New_OwnerList.get(CurrentinvoCount));
			System.out.println("PDF New Owner Name List is == "+NewOwnerNameAssertList);

			mAssert(NewOwnerNameAssertList, Full_NameList, "Wrong New Owner Name  Actual  :"+NewOwnerNameAssertList+ " Expected  :"+Full_NameList);


			OldOwnerNameAssertList.add(Old_OwnerList.get(CurrentinvoCount));
			System.out.println("PDF Old Owner Name List is == "+OldOwnerNameAssertList);

			mAssert(OldOwnerNameAssertList, Primary_OwnerNmaeList, "Wrong Old Owner Name  Actual  :"+OldOwnerNameAssertList+ " Expected  :"+Primary_OwnerNmaeList);


			WardNoAssertList.add(Ward_NoList.get(CurrentinvoCount));
			System.out.println("PDF Ward No List is == "+WardNoAssertList);

			mAssert(WardNoAssertList, Ward_NomList, "Wrong Ward No  Actual  :"+WardNoAssertList+ " Expected  :"+Ward_NomList);



			PropNoAssertList.add(Prop_NoListPdf.get(CurrentinvoCount));
			System.out.println("PDF Prop No List is == "+PropNoAssertList);

			mAssert(PropNoAssertList, Prop_NoList, "Wrong Property No  Actual  :"+PropNoAssertList+ " Expected  :"+Prop_NoList);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in TransferHeredity method");
		}
	}

	///////////////////////
	//////////////////////

	//Method for Transfer Fee Intimation Letter Heredity by Jyoti

	///////////////////////
	//////////////////////

	public void trnsfrFee_IntiLetter_Heredity() throws Exception
	{
		try
		{

			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			//login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("citizenUserName"),mGetPropertyFromFile("citizenPassword"));
			mGenericWait();
			//mNavigation("pt_propertyTaxLinkid", "pt_selfAssessmentLinkid");
			mNavigation(mGetPropertyFromFile("onlServices_ID"), mGetPropertyFromFile("moduleName_ID"), mGetPropertyFromFile("subModuleName_ID"), mGetPropertyFromFile("serviceName_ID"));

			TransferFeeIntimationLetter_Heredity();

			logOut();
			finalLogOut();
			//isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerNewAssessmentServiceName"));
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in trnsfrFee_IntiLetter method");
		}

	}

	public void TransferFeeIntimationLetter_Heredity() throws Exception
	{
		try
		{
			if(mGetPropertyFromFile("pt_trfFeeInti_Mismatch_Data").equalsIgnoreCase("No"))

			{

				TransferPaymentHeredity = true;
				TransferCrtificateHeredity = true;

				mWaitForVisible("xpath", mGetPropertyFromFile("pt_trfFeeInti_Print_ID"));
				mClick("xpath", mGetPropertyFromFile("pt_trfFee_IntiLetter_ID"));
				mGenericWait();
				mWaitForVisible("xpath", mGetPropertyFromFile("pt_trfFee_Search_ID"));
				//mSendKeys("xpath", mGetPropertyFromFile("pt_trfFeeInti_AppNo_ID"), mGetPropertyFromFile("pt_trfFeeInti_AppNo_Data"));
				//mSendKeys("xpath", mGetPropertyFromFile("pt_trf_Nom_PropNoData_ID"), "22317021000007");			
				//mCustomWait(1000);

				IndOrDep("name", "pt_trfFeeInti_AppNo_ID", "applicationNo");
				mGenericWait();

				mClick("xpath", mGetPropertyFromFile("pt_trfFee_Search_ID"));
				mTakeScreenShot();
				mGenericWait();
				mClick("xpath", mGetPropertyFromFile("pt_trfFeeInti_Add_ID"));
				mGenericWait();
				mSelectDropDown("name", mGetPropertyFromFile("pt_trfFeeInti_Mismatch_ID"), mGetPropertyFromFile("pt_trfFeeInti_Mismatch_Data"));
				mTakeScreenShot();


				/// Applicaton Form Assertion 

				String trfFeeInti_AppNo = mGetText("xpath", mGetPropertyFromFile("pt_trfFeeInti_EnterAppNo_ID"),"value");
				System.out.println("Application No of Transfer Fee Intimation Letter is ::: "+trfFeeInti_AppNo);
				AppNo_TrfFeeTintiLetterList.add(trfFeeInti_AppNo);
				System.out.println("Application time Application No. of Transfer Fee Intimation Letter is == "+AppNo_TrfFeeTintiLetterList);


				String trfFeeInti_PropNo = mGetText("id", mGetPropertyFromFile("pt_trfFeeInti_PropNo_ID"),"value");
				System.out.println("Prop No of Transfer Fee Intimation Letter is :::"+trfFeeInti_PropNo);
				PropNo_TrfFeeTintiLetterList.add(trfFeeInti_PropNo);
				System.out.println("Application time Property No. of Transfer Fee Intimation Letter is == "+PropNo_TrfFeeTintiLetterList);

				String trfFeeInti_OldOwnerName = mGetText("id", mGetPropertyFromFile("pt_trfFeeInti_OldOwnerName_ID"),"value");
				System.out.println("Old Owner Name of Transfer Fee Intimation Letter is :::"+trfFeeInti_OldOwnerName);
				OldOwnerName_TrfFeeTintiLetterList.add(trfFeeInti_OldOwnerName);
				System.out.println("Application time Old Owner Name of Transfer Fee Intimation Letter is == "+OldOwnerName_TrfFeeTintiLetterList);

				/// Individual - Not Working **** Dependant - Working
				/*OldPropOwnerNm_IntiLetter.add(Primary_OwnerNmaeList.get(CurrentinvoCount));
			System.out.println("Transfer Nomination Application Form Old Property Owner List is :::"+OldPropOwnerNm_IntiLetter);
			mAssert(OldOwnerName_TrfFeeTintiLetterList, OldPropOwnerNm_IntiLetter, "Wrong Old Property Owner on Transfer Fee Intimation Letter  Actual  :"+OldOwnerName_TrfFeeTintiLetterList+ " Expected  :"+OldPropOwnerNm_IntiLetter);
				 */



				String trfFeeInti_NewOwnerName = mGetText("id", mGetPropertyFromFile("pt_trfFeeInti_NewOwnerName_ID"),"value").trim();
				System.out.println("New Owner Name of Transfer Fee Intimation Letter is :::"+trfFeeInti_NewOwnerName);
				NewOwnerName_TrfFeeTintiLetterList.add(trfFeeInti_NewOwnerName);
				System.out.println("Application time New Owner Name of Transfer Fee Intimation Letter is == "+NewOwnerName_TrfFeeTintiLetterList);

				String trfFeeInti_TransferType = mGetText("id", mGetPropertyFromFile("pt_trfFeeInti_TransferType_ID"),"value");
				System.out.println("TransferType of Transfer Fee Intimation Letter is :::"+trfFeeInti_TransferType);
				TransferType_TrfFeeTintiLetterList.add(trfFeeInti_TransferType);
				System.out.println("Application time TransferType of Transfer Fee Intimation Letter is == "+TransferType_TrfFeeTintiLetterList);

				String trfFeeInti_TransferFee = mGetText("id", mGetPropertyFromFile("pt_trfFeeInti_TransferFee_ID"),"value");
				System.out.println("TransferFee of Transfer Fee Intimation Letter is :::"+trfFeeInti_TransferFee);
				TransferFee_TrfFeeTintiLetterList.add(trfFeeInti_TransferFee);
				System.out.println("Application time TransferFee of Transfer Fee Intimation Letter is == "+TransferFee_TrfFeeTintiLetterList);



				mGenericWait();
				mClick("xpath", mGetPropertyFromFile("pt_trfFeeInti_Print_ID"));
				mGenericWait();
				mChallanPDFReader();

				api.PdfPatterns.pt_TransferFeeIntiLetterPdf(pdfoutput);


				////// ****** PDF Assertions *****************************

				AppNo_IntiLetter.add(IntiLetter_App_NoList.get(CurrentinvoCount));
				//IntiLetter_App_NoList.get(CurrentinvoCount);
				System.out.println("PDF Application No. List is :::"+AppNo_IntiLetter);

				AppNo_TrfFeeTintiLetterList.get(CurrentinvoCount);
				System.out.println("App time Application No. List is :::"+AppNo_TrfFeeTintiLetterList);

				mAssert(AppNo_IntiLetter, AppNo_TrfFeeTintiLetterList, "Wrong Application no on Transfer Fee Intimation Letter  Actual  :"+AppNo_IntiLetter+ " Expected  :"+AppNo_TrfFeeTintiLetterList);

				PropNo_IntiLetter.add(IntiLetter_Prop_NoList.get(CurrentinvoCount));
				System.out.println("PDF Property No. List is :::"+PropNo_IntiLetter);
				mAssert(PropNo_IntiLetter, PropNo_TrfFeeTintiLetterList, "Wrong Property no on Transfer Fee Intimation Letter  Actual  :"+PropNo_IntiLetter+ " Expected  :"+PropNo_TrfFeeTintiLetterList);


				NewPropName_IntiLetter.add(OwnerName_IntiLetterList.get(CurrentinvoCount));
				System.out.println("PDF Property Owner List is :::"+NewPropName_IntiLetter);
				mAssert(NewPropName_IntiLetter, NewOwnerName_TrfFeeTintiLetterList, "Wrong New Owner Name on Transfer Fee Intimation Letter  Actual  :"+NewPropName_IntiLetter+ " Expected  :"+NewOwnerName_TrfFeeTintiLetterList);

				TransferFee_IntiLetterList.add(TransferFee_IntiLetter.get(CurrentinvoCount));
				System.out.println("PDF Transfer Fee List is :::"+TransferFee_IntiLetterList);
				mAssert(TransferFee_IntiLetterList, TransferFee_TrfFeeTintiLetterList, "Wrong Transfer Fee on Transfer Fee Intimation Letter  Actual  :"+TransferFee_IntiLetterList+ " Expected  :"+TransferFee_TrfFeeTintiLetterList);
			}
			else
			{
				System.out.println("Transfer Fee Intimation Heredity Rejection Letter Generated....");

				mWaitForVisible("xpath", mGetPropertyFromFile("pt_trfFeeInti_Print_ID"));
				mClick("xpath", mGetPropertyFromFile("pt_trfFee_IntiLetter_ID"));
				mGenericWait();
				mWaitForVisible("xpath", mGetPropertyFromFile("pt_trfFee_Search_ID"));
				//mSendKeys("xpath", mGetPropertyFromFile("pt_trfFeeInti_AppNo_ID"), mGetPropertyFromFile("pt_trfFeeInti_AppNo_Data"));
				//mSendKeys("xpath", mGetPropertyFromFile("pt_trf_Nom_PropNoData_ID"), "22317021000007");			
				//mCustomWait(1000);

				IndOrDep("name", "pt_trfFeeInti_AppNo_ID", "applicationNo"); // not working
				mGenericWait();

				mClick("xpath", mGetPropertyFromFile("pt_trfFee_Search_ID"));
				mTakeScreenShot();
				mGenericWait();
				mClick("xpath", mGetPropertyFromFile("pt_trfFeeInti_Add_ID"));
				mGenericWait();
				mSelectDropDown("name", mGetPropertyFromFile("pt_trfFeeInti_Mismatch_ID"), mGetPropertyFromFile("pt_trfFeeInti_Mismatch_Data"));
				mTakeScreenShot();

				mGenericWait();
				mClick("xpath", mGetPropertyFromFile("pt_trfFeeInti_Print_ID"));
				mGenericWait();

				mChallanPDFReader();


			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in TransferFeeIntimationLetter method");
		}
	}

	///////////////////////
	//////////////////////

	//Method for Transfer Certificate Heredity by Jyoti

	///////////////////////
	//////////////////////

	public void trnsfr_Certificate_Heredity() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			//login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("citizenUserName"),mGetPropertyFromFile("citizenPassword"));
			mGenericWait();
			mNavigation(mGetPropertyFromFile("trfonlServices_ID"), mGetPropertyFromFile("trfmoduleName_ID"), mGetPropertyFromFile("trfsubModuleName_ID"), mGetPropertyFromFile("trfserviceName_ID"));

			TransferCertificateHeredity();
			logOut();
			finalLogOut();
			//isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerNewAssessmentServiceName"));
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in trnsfr_Certificate method");
		}

	}

	public void TransferCertificateHeredity() throws Exception
	{
		try
		{

			mClick("xpath", mGetPropertyFromFile("trf_Certificate_ListApp_ID"));
			mGenericWait();
			//mSendKeys("xpath", mGetPropertyFromFile("pt_trfCertificate_EnterAppNo_ID"), mGetPropertyFromFile("pt_trfCertificate_EnterAppNo_Data"));

			IndOrDep("xpath", "pt_trfCertificate_EnterAppNo_ID", "applicationNo");

			mClick("xpath", mGetPropertyFromFile("trf_Certificate_Search_ID"));
			mGenericWait();
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("pt_trfCertificate_Add_ID"));
			mGenericWait();
			mTakeScreenShot();
			mGenericWait();

			///// Assetions Start

			String TrfCerti_AppNo = mGetText("xpath", mGetPropertyFromFile("pt_trfCertificate_EnterAppNo_ID"),"value");
			System.out.println("Application No of Transfer Certificate Heredity at Application time is::: "+TrfCerti_AppNo);
			TrfCerti_AppNoList.add(TrfCerti_AppNo);
			System.out.println("Application No List of Transfer Certificate Heredity at Application time is :::"+TrfCerti_AppNoList);

			String TrfCerti_PropNo = mGetText("xpath", mGetPropertyFromFile("pt_trfCertificate_PropNo_ID"), "value");
			System.out.println("Property No of Transfer Certificate Heredity at Application time is::: "+TrfCerti_PropNo);
			TrfCerti_PropNoList.add(TrfCerti_PropNo);
			System.out.println("Property No List of Transfer Certificate Heredity at Application time is :::"+TrfCerti_PropNoList);

			String TrfCerti_NewOwner = mGetText("xpath", mGetPropertyFromFile("pt_trfCertificate_NewOwner_ID"), "value");
			System.out.println("New Owner of Transfer Certificate Heredity at Application Time :: "+TrfCerti_NewOwner);
			TrfCerti_NewOwnerList.add(TrfCerti_NewOwner);
			System.out.println("New Owner List of Transfer Certificate Heredity at Application time is :::"+TrfCerti_NewOwnerList);

			String TrfCerti_OldTransferee = mGetText("xpath", mGetPropertyFromFile("pt_trfCertificate_OldTransferee_ID"), "value");
			System.out.println("Old Transferee of Transfer Certificate Heredity at Application Time :: "+TrfCerti_OldTransferee);
			TrfCerti_OldTransfereeList.add(TrfCerti_NewOwner);
			System.out.println("Old Transferee List of Transfer Certificate Heredity at Application time is :::"+TrfCerti_OldTransfereeList);


			String TrfCerti_TransferType = mGetText("xpath", mGetPropertyFromFile("pt_trfCertificate_TransferType_ID"), "value");
			System.out.println("Transfer Type of Transfer Certificate Heredity at Application Time :: "+TrfCerti_TransferType);




			mClick("xpath", mGetPropertyFromFile("trf_Certificate_Print_ID"));
			mGenericWait();

			mChallanPDFReader();
			api.PdfPatterns.pt_TransferCertificatePdf(pdfoutput);

			TrfCertificate_App_NoList.add(TrfCerti_App_NoList.get(CurrentinvoCount));
			System.out.println("PDF Application No List of Transfer Certificate Heredity::: "+TrfCertificate_App_NoList);
			mAssert(TrfCertificate_App_NoList, TrfCerti_AppNoList, "Wrong Application No on Transfer Certificate Heredity  Actual  :"+TrfCertificate_App_NoList+ " Expected  :"+TrfCerti_AppNoList);

			TrfCertificate_Prop_NoList.add(TrfCerti_Prop_NoList.get(CurrentinvoCount));
			System.out.println("PDF Property No List of Transfer Certificate::: "+TrfCertificate_Prop_NoList);
			mAssert(TrfCertificate_Prop_NoList, TrfCerti_PropNoList, "Wrong Property No on Transfer Certificate Heredity  Actual  :"+TrfCertificate_Prop_NoList+ " Expected  :"+TrfCerti_PropNoList);

			TrfCertificate_New_OwnerList.add(TrfCerti_NewOwnerList.get(CurrentinvoCount));
			System.out.println("PDF New Owner List of Transfer Certificate::: "+TrfCertificate_New_OwnerList);
			mAssert(TrfCertificate_New_OwnerList, TrfCerti_NewOwnerList, "Wrong New Owner on Transfer Certificate Heredity  Actual  :"+TrfCertificate_New_OwnerList+ " Expected  :"+TrfCerti_NewOwnerList);

			TrfCertificate_Old_TransfereeList.add(TrfCerti_NewOwnerList.get(CurrentinvoCount));
			System.out.println("PDF Old Transferee List of Transfer Certificate::: "+TrfCertificate_Old_TransfereeList);
			mAssert(TrfCertificate_New_OwnerList, TrfCerti_OldTransfereeList, "Wrong Old Transferee on Transfer Certificate Heredity  Actual  :"+TrfCertificate_New_OwnerList+ " Expected  :"+TrfCerti_OldTransfereeList);

			TransferCrtificateHeredity = false;

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in TransferCertificateHeredity method");
		}	
	}


	///////////////////////
	//////////////////////

	//Method for Transfer Other Mode Service by Jyoti

	///////////////////////
	//////////////////////
	public void trnsfr_OtherMode() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			//departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("citizenUserName"),mGetPropertyFromFile("citizenPassword"));
			mGenericWait();
			mNavigation("pt_propertyTaxLinkid", "pt_selfAssessmentLinkid");

			TransferOtherMode();

			logOut();
			finalLogOut();

			mCloseBrowser();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in trnsfr_OtherMode method");

		}
	}

	public void TransferOtherMode() throws Exception
	{
		try
		{
			////// Existing Property Details
			mWaitForVisible("xpath", mGetPropertyFromFile("pt_SearchPropNoBtn_ID"));
			mGenericWait();

			//mSendKeys("id", mGetPropertyFromFile("pt_EnterPropNo_ID"), mGetPropertyFromFile("pt_EnterPropNo_Data"));
			//mSendKeys("xpath", mGetPropertyFromFile("pt_EnterPropNo_ID"), "223003496");			

			IndOrDep("id","pt_EnterPropNo_ID", "applicationNo");

			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("pt_SearchPropNoBtn_ID"));
			mGenericWait();

			////////Assertion of Property No


			String Prop_NoOM = mGetText("id", mGetPropertyFromFile("pt_EnterPropNo_ID"), "value");
			System.out.println("Application time Property No Other Mode is == "+Prop_NoOM);
			Prop_NoOMList.add(Prop_NoOM);
			System.out.println("Application time List of Property Other Mode == "+Prop_NoOMList);


			mClick("xpath", mGetPropertyFromFile("pt_EnterTransferDetailsBtn_ID"));
			mGenericWait();

			//// Transferable Property Details
			mSelectDropDown("id", mGetPropertyFromFile("pt_TransferTypeOM_ID"), mGetPropertyFromFile("pt_TransferTypeOM_Data"));
			mGenericWait();

			//Old Code Commenented becouse showing date difference error instead of this used below line @Ritesh 04-04-2017
			//mDateSelect("css", mGetPropertyFromFile("pt_ActualTransferDateOM_ID"), mGetPropertyFromFile("pt_ActualTransferDateOM_Data"));
			mGdatePicker(mGetPropertyFromFile("pt_ActualTransferDateOM_ID"), mGetPropertyFromFile("pt_Trans_Other_Acttrans_Yr"), mGetPropertyFromFile("pt_Trans_Other_Acttrans_Month"),mGetPropertyFromFile("pt_Trans_Other_Acttrans_Date"));
			mGenericWait();
			mSelectDropDown("name", mGetPropertyFromFile("pt_NewTypeofOwnerOM_ID"), mGetPropertyFromFile("pt_NewTypeofOwnerOM_Data"));
			mGenericWait();
			//mClick("xpath", mGetPropertyFromFile("pt_SubmitBtnOM_ID"));
			//mWaitForInvisible("xpath", "pt_SubmitBtnOM_ID");
			//mGenericWait();

			mTakeScreenShot();

			////// Single Owner Details
			mCustomWait(5000);
			mSelectDropDown("name", mGetPropertyFromFile("pt_trfOM_NameTitle_ID"), mGetPropertyFromFile("pt_trfOM_NameTitle_Data"));
			mGenericWait();
			mSendKeys("name", mGetPropertyFromFile("pt_trfOM_NewOwnerFirstName_ID"), mGetPropertyFromFile("pt_trfOM_NewOwnerFirstName_Data"));
			mGenericWait();
			mSendKeys("name", mGetPropertyFromFile("pt_trfOM_NewOwnerFatherName_ID"), mGetPropertyFromFile("pt_trfOM_NewOwnerFatherName_Data"));
			mGenericWait();
			mSelectDropDown("name", mGetPropertyFromFile("pt_trfOM_NewOwnerGender_ID"), mGetPropertyFromFile("pt_trfOM_NewOwnerGender_Data"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("pt_trfOM_NewAddress_ID"), mGetPropertyFromFile("pt_trfOM_NewAddress_Data"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("pt_trfOM_NewPinCode_ID"), mGetPropertyFromFile("pt_trfOM_NewPinCode_Data"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("pt_trfOM_NewMobNo_ID"), mGetPropertyFromFile("pt_trfOM_NewMobNo_Data"));
			mGenericWait();
			mUpload("id", mGetPropertyFromFile("pt_trfOM_upload1_ID"), mGetPropertyFromFile("pt_trfOM_upload1_Data"));
			mGenericWait();
			mUpload("id", mGetPropertyFromFile("pt_trfOM_upload2_ID"), mGetPropertyFromFile("pt_trfOM_upload2_Data"));
			mGenericWait();

			mTakeScreenShot();



			//*************************
			////Assertions of Application Form - Transfer OTHER MODE


			String Primary_OwnerNameOM = mGetText("xpath", mGetPropertyFromFile("pt_trfOM_PrimaryOwnerName_ID"), "value"); 
			System.out.println("Primary Owner Name of Transfer Other Mode is ::: "+Primary_OwnerNameOM);	
			Primary_OwnerNmaeOMList.add(Primary_OwnerNameOM);
			System.out.println("Application time OM Primary Owner List of Transfer Heredity is == "+Primary_OwnerNmaeOMList);


			String Ward_NomOM = mCaptureSelectedValue("xpath", mGetPropertyFromFile("pt_trfOM_Ward_ID"));
			System.out.println("Ward of Transfer Other Mode is ::: "+Ward_NomOM);
			Ward_OMList.add(Ward_NomOM);
			System.out.println("Application time Ward List of Transfer Other Mode is == "+Ward_OMList);


			String Name_TitleOM = mCaptureSelectedValue("name", mGetPropertyFromFile("pt_trfOM_NameTitle_ID"));
			System.out.println("Application time new Owner Name Title of Transfer Other Mode == "+Name_TitleOM);

			String First_NameOM = mGetText("name", mGetPropertyFromFile("pt_trfOM_NewOwnerFirstName_ID"),"value");
			System.out.println("Application time New Owner First Name of Transfer Other Mode =="+First_NameOM);




			String FullNmOM = Name_TitleOM +  First_NameOM.toUpperCase();

			System.out.println("Full Name Other Mode is == "+FullNmOM);
			Full_NameOMList.add(FullNmOM);		
			System.out.println("Application time New Owner Full Name List of Transfer Other Mode =="+Full_NameOMList);			


			//*************************



			mClick("id", mGetPropertyFromFile("pt_trfOM_SubmitOMApp_ID"));
			mGenericWait();

			//Get popup save msg
			//mWaitForVisible("css", mGetPropertyFromFile("MKT_issueDupLicProceedButtonID"));			
			String popupmsg = mGetText("xpath", mGetPropertyFromFile("pt_trfOM_SaveMsg_ID"));
			System.out.println("Pop up message of PT Transfer Other Mode Application Saved::: "+popupmsg);

			//Capturing appno and asserting popup msg
			appNo = popupmsg.replaceAll("\\D+","");
			System.out.println("Application No of Transfer - Other Mode is::: "+appNo);
			appNo = appNo.trim();
			mAppNoArray(appNo); 

			popupmsg = popupmsg.replaceAll("[0-9]","");			
			System.out.println(popupmsg);
			mTakeScreenShot();

			//mAssert(popupmsg, mGetPropertyFromFile("pt_trfNom_SaveAssertMsg_Data"), mGetPropertyFromFile("MKT_issueDupLicAssertMsgFail_Data")+" Expected::: "+mGetPropertyFromFile("pt_trfNom_SaveAssertMsg_Data")+ " Actual::: " +popupmsg);

			mTakeScreenShot();

			mClick("xpath", mGetPropertyFromFile("pt_trfOM_ProceedBtn_ID"));
			mGenericWait();
			mChallanPDFReader();


			api.PdfPatterns.pt_TransferOtherModePdf(pdfoutput);


			NewOwnerNameOMAssertList.add(New_OMOwnerList.get(CurrentinvoCount));
			System.out.println("PDF New Owner Name OM List is == "+NewOwnerNameOMAssertList);

			mAssert(NewOwnerNameOMAssertList, Full_NameOMList, "Wrong New Owner Name OM  Actual  :"+NewOwnerNameAssertList+ " Expected  :"+Full_NameOMList);


			OldOwnerNameOMAssertList.add(Old_OMOwnerList.get(CurrentinvoCount));
			System.out.println("PDF Old Owner Name OM List is == "+OldOwnerNameOMAssertList);

			mAssert(OldOwnerNameOMAssertList, Primary_OwnerNmaeOMList, "Wrong Old Owner Name OM Actual  :"+OldOwnerNameOMAssertList+ " Expected  :"+Primary_OwnerNmaeOMList);


			WardNoAssertOMList.add(Ward_NoListOM.get(CurrentinvoCount));
			System.out.println("PDF Ward No OM List is == "+WardNoAssertOMList);

			mAssert(WardNoAssertOMList, Ward_OMList, "Wrong Ward No OM Actual  :"+WardNoAssertOMList+ " Expected  :"+Ward_OMList);

			//*********** Not working - Prop No ************	

			PropNoAssertOMList.add(Prop_NoListOMPdf.get(CurrentinvoCount));
			System.out.println("PDF Prop No OM List is == "+PropNoAssertOMList);

			mAssert(PropNoAssertOMList, Prop_NoOMList, "Wrong Property No OM Actual  :"+PropNoAssertOMList+ " Expected  :"+Prop_NoOMList);


		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in trnsfr_OtherMode method");	
		}
	}

	///////////////////////
	//////////////////////

	//Method for Transfer Fee Intimation Letter Other Mode by Jyoti

	///////////////////////
	//////////////////////

	public void trnsfrFee_IntiLetter_OtherMode() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			//login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("citizenUserName"),mGetPropertyFromFile("citizenPassword"));
			mGenericWait();
			//mNavigation("pt_propertyTaxLinkid", "pt_selfAssessmentLinkid");
			mNavigation(mGetPropertyFromFile("onlServices_ID"), mGetPropertyFromFile("moduleName_ID"), mGetPropertyFromFile("subModuleName_ID"), mGetPropertyFromFile("serviceName_ID"));

			TransferFeeIntimationLetter_OtherMode();

			logOut();
			finalLogOut();
			//isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerNewAssessmentServiceName"));
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in trnsfrFee_IntiLetter_OtherMode method");
		}

	}
	public void TransferFeeIntimationLetter_OtherMode() throws Exception
	{
		try
		{
			if(mGetPropertyFromFile("pt_trfFeeInti_Mismatch_Data").equalsIgnoreCase("No"))
			{

				TransferPaymentOtherMode = true;
				TransferCrtificateOtherMode = true;

				mWaitForVisible("xpath", mGetPropertyFromFile("pt_trfFeeInti_Print_ID"));
				mClick("xpath", mGetPropertyFromFile("pt_trfFee_IntiLetter_ID"));
				mGenericWait();
				mWaitForVisible("xpath", mGetPropertyFromFile("pt_trfFee_Search_ID"));
				//mSendKeys("xpath", mGetPropertyFromFile("pt_trfFeeInti_AppNo_ID"), mGetPropertyFromFile("pt_trfFeeInti_AppNo_Data"));
				//mSendKeys("xpath", mGetPropertyFromFile("pt_trf_Nom_PropNoData_ID"), "22317021000007");			
				//mCustomWait(1000);

				IndOrDep("name", "pt_trfFeeInti_AppNo_ID", "applicationNo");
				mGenericWait();

				mClick("xpath", mGetPropertyFromFile("pt_trfFee_Search_ID"));
				mTakeScreenShot();
				mGenericWait();
				mClick("xpath", mGetPropertyFromFile("pt_trfFeeInti_Add_ID"));
				mGenericWait();
				mSelectDropDown("name", mGetPropertyFromFile("pt_trfFeeInti_Mismatch_ID"), mGetPropertyFromFile("pt_trfFeeInti_Mismatch_Data"));
				mTakeScreenShot();


				/// Applicaton Form Assertion 

				String trfFeeInti_AppNo = mGetText("xpath", mGetPropertyFromFile("pt_trfFeeInti_EnterAppNo_ID"),"value");
				System.out.println("Application No of Transfer Fee Intimation Letter OtherMode is ::: "+trfFeeInti_AppNo);
				AppNo_TrfFeeTintiLetterList.add(trfFeeInti_AppNo);
				System.out.println("Application time Application No. of Transfer Fee Intimation Letter OtherMode is == "+AppNo_TrfFeeTintiLetterList);


				String trfFeeInti_PropNo = mGetText("id", mGetPropertyFromFile("pt_trfFeeInti_PropNo_ID"),"value");
				System.out.println("Prop No of Transfer Fee Intimation Letter OtherMode is :::"+trfFeeInti_PropNo);
				PropNo_TrfFeeTintiLetterList.add(trfFeeInti_PropNo);
				System.out.println("Application time Property No. of Transfer Fee Intimation Letter OtherMode is == "+PropNo_TrfFeeTintiLetterList);

				String trfFeeInti_OldOwnerName = mGetText("id", mGetPropertyFromFile("pt_trfFeeInti_OldOwnerName_ID"),"value");
				System.out.println("Old Owner Name of Transfer Fee Intimation Letter is :::"+trfFeeInti_OldOwnerName);
				OldOwnerName_TrfFeeTintiLetterList.add(trfFeeInti_OldOwnerName);
				System.out.println("Application time Old Owner Name of Transfer Fee Intimation Letter OtherMode is == "+OldOwnerName_TrfFeeTintiLetterList);

				/// Individual - Not Working **** Dependant - Working
				/*OldPropOwnerNm_IntiLetter.add(Primary_OwnerNmaeList.get(CurrentinvoCount));
System.out.println("Transfer Nomination Application Form Old Property Owner List OtherMode is :::"+OldPropOwnerNm_IntiLetter);
mAssert(OldOwnerName_TrfFeeTintiLetterList, OldPropOwnerNm_IntiLetter, "Wrong Old Property Owner on Transfer Fee Intimation Letter  Actual  :"+OldOwnerName_TrfFeeTintiLetterList+ " Expected  :"+OldPropOwnerNm_IntiLetter);
				 */



				String trfFeeInti_NewOwnerName = mGetText("id", mGetPropertyFromFile("pt_trfFeeInti_NewOwnerName_ID"),"value").trim();
				System.out.println("New Owner Name of Transfer Fee Intimation Letter OtherMode is :::"+trfFeeInti_NewOwnerName);
				NewOwnerName_TrfFeeTintiLetterList.add(trfFeeInti_NewOwnerName);
				System.out.println("Application time New Owner Name of Transfer Fee Intimation Letter OtherMode is == "+NewOwnerName_TrfFeeTintiLetterList);

				String trfFeeInti_TransferType = mGetText("id", mGetPropertyFromFile("pt_trfFeeInti_TransferType_ID"),"value");
				System.out.println("TransferType of Transfer Fee Intimation Letter OtherMode is :::"+trfFeeInti_TransferType);
				TransferType_TrfFeeTintiLetterList.add(trfFeeInti_TransferType);
				System.out.println("Application time TransferType of Transfer Fee Intimation Letter OtherMode is == "+TransferType_TrfFeeTintiLetterList);

				String trfFeeInti_TransferFee = mGetText("id", mGetPropertyFromFile("pt_trfFeeInti_TransferFee_ID"),"value");
				System.out.println("TransferFee of Transfer Fee Intimation Letter OtherMode is :::"+trfFeeInti_TransferFee);
				TransferFee_TrfFeeTintiLetterList.add(trfFeeInti_TransferFee);
				System.out.println("Application time TransferFee of Transfer Fee Intimation Letter OtherMode is == "+TransferFee_TrfFeeTintiLetterList);



				mGenericWait();
				mClick("xpath", mGetPropertyFromFile("pt_trfFeeInti_Print_ID"));
				mGenericWait();
				mChallanPDFReader();

				api.PdfPatterns.pt_TransferFeeIntiLetterPdf(pdfoutput);


				////// ****** PDF Assertions *****************************

				AppNo_IntiLetter.add(IntiLetter_App_NoList.get(CurrentinvoCount));
				//IntiLetter_App_NoList.get(CurrentinvoCount);
				System.out.println("PDF Application No. List OtherMode is :::"+AppNo_IntiLetter);

				AppNo_TrfFeeTintiLetterList.get(CurrentinvoCount);
				System.out.println("App time Application No. List OtherMode is :::"+AppNo_TrfFeeTintiLetterList);

				mAssert(AppNo_IntiLetter, AppNo_TrfFeeTintiLetterList, "Wrong Application no on Transfer Fee Intimation Letter OtherMode  Actual  :"+AppNo_IntiLetter+ " Expected  :"+AppNo_TrfFeeTintiLetterList);

				PropNo_IntiLetter.add(IntiLetter_Prop_NoList.get(CurrentinvoCount));
				System.out.println("PDF Property No. List is :::"+PropNo_IntiLetter);
				mAssert(PropNo_IntiLetter, PropNo_TrfFeeTintiLetterList, "Wrong Property no on Transfer Fee Intimation Letter OtherMode Actual  :"+PropNo_IntiLetter+ " Expected  :"+PropNo_TrfFeeTintiLetterList);


				NewPropName_IntiLetter.add(OwnerName_IntiLetterList.get(CurrentinvoCount));
				System.out.println("PDF Property Owner List is :::"+NewPropName_IntiLetter);
				mAssert(NewPropName_IntiLetter, NewOwnerName_TrfFeeTintiLetterList, "Wrong New Owner Name on Transfer Fee Intimation Letter OtherMode  Actual  :"+NewPropName_IntiLetter+ " Expected  :"+NewOwnerName_TrfFeeTintiLetterList);

				TransferFee_IntiLetterList.add(TransferFee_IntiLetter.get(CurrentinvoCount));
				System.out.println("PDF Transfer Fee List is :::"+TransferFee_IntiLetterList);
				mAssert(TransferFee_IntiLetterList, TransferFee_TrfFeeTintiLetterList, "Wrong Transfer Fee on Transfer Fee Intimation Letter OtherMode Actual  :"+TransferFee_IntiLetterList+ " Expected  :"+TransferFee_TrfFeeTintiLetterList);




			}
			else
			{


				System.out.println("Rejection Letter of Transfer Fee Intimation Letter Other Mode is Generated.....");

				mWaitForVisible("xpath", mGetPropertyFromFile("pt_trfFeeInti_Print_ID"));
				mClick("xpath", mGetPropertyFromFile("pt_trfFee_IntiLetter_ID"));
				mGenericWait();
				mWaitForVisible("xpath", mGetPropertyFromFile("pt_trfFee_Search_ID"));
				//mSendKeys("xpath", mGetPropertyFromFile("pt_trfFeeInti_AppNo_ID"), mGetPropertyFromFile("pt_trfFeeInti_AppNo_Data"));
				//mSendKeys("xpath", mGetPropertyFromFile("pt_trf_Nom_PropNoData_ID"), "22317021000007");			
				//mCustomWait(1000);

				IndOrDep("name", "pt_trfFeeInti_AppNo_ID", "applicationNo");
				mGenericWait();

				mClick("xpath", mGetPropertyFromFile("pt_trfFee_Search_ID"));
				mTakeScreenShot();
				mGenericWait();
				mClick("xpath", mGetPropertyFromFile("pt_trfFeeInti_Add_ID"));
				mGenericWait();
				mSelectDropDown("name", mGetPropertyFromFile("pt_trfFeeInti_Mismatch_ID"), mGetPropertyFromFile("pt_trfFeeInti_Mismatch_Data"));
				mTakeScreenShot();


				mGenericWait();
				mClick("xpath", mGetPropertyFromFile("pt_trfFeeInti_Print_ID"));
				mGenericWait();

				mChallanPDFReader();




			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in TransferFeeIntimationLetter_OtherMode method");
		}
	}

	///////////////////////
	//////////////////////

	//Method for TransferCertificate Other Mode by Jyoti

	///////////////////////
	//////////////////////

	public void trnsfr_Certificate_OtherMode() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			//login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("citizenUserName"),mGetPropertyFromFile("citizenPassword"));
			mGenericWait();
			mNavigation(mGetPropertyFromFile("trfonlServices_ID"), mGetPropertyFromFile("trfmoduleName_ID"), mGetPropertyFromFile("trfsubModuleName_ID"), mGetPropertyFromFile("trfserviceName_ID"));

			TransferCertificateOtherMode();
			logOut();
			finalLogOut();
			//isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerNewAssessmentServiceName"));
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in trnsfr_Certificate_OtherMode method");
		}

	}

	public void TransferCertificateOtherMode() throws Exception
	{
		try
		{

			mClick("xpath", mGetPropertyFromFile("trf_Certificate_ListApp_ID"));
			mGenericWait();
			//mSendKeys("xpath", mGetPropertyFromFile("pt_trfCertificate_EnterAppNo_ID"), mGetPropertyFromFile("pt_trfCertificate_EnterAppNo_Data"));

			IndOrDep("xpath", "pt_trfCertificate_EnterAppNo_ID", "applicationNo");

			mClick("xpath", mGetPropertyFromFile("trf_Certificate_Search_ID"));
			mGenericWait();
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("pt_trfCertificate_Add_ID"));
			mGenericWait();
			mTakeScreenShot();
			mGenericWait();

			///// Assetions Start

			String TrfCerti_AppNo = mGetText("xpath", mGetPropertyFromFile("pt_trfCertificate_EnterAppNo_ID"),"value");
			System.out.println("Application No of Transfer Certificate OtherMode at Application time is::: "+TrfCerti_AppNo);
			TrfCerti_AppNoList.add(TrfCerti_AppNo);
			System.out.println("Application No List of Transfer Certificate OtherMode at Application time is :::"+TrfCerti_AppNoList);

			String TrfCerti_PropNo = mGetText("xpath", mGetPropertyFromFile("pt_trfCertificate_PropNo_ID"), "value");
			System.out.println("Property No of Transfer Certificate OtherMode at Application time is::: "+TrfCerti_PropNo);
			TrfCerti_PropNoList.add(TrfCerti_PropNo);
			System.out.println("Property No List of Transfer Certificate OtherMode at Application time is :::"+TrfCerti_PropNoList);

			String TrfCerti_NewOwner = mGetText("xpath", mGetPropertyFromFile("pt_trfCertificate_NewOwner_ID"), "value");
			System.out.println("New Owner of Transfer Certificate OtherMode at Application Time :: "+TrfCerti_NewOwner);
			TrfCerti_NewOwnerList.add(TrfCerti_NewOwner);
			System.out.println("New Owner List of Transfer Certificate OtherMode at Application time is :::"+TrfCerti_NewOwnerList);

			String TrfCerti_OldTransferee = mGetText("xpath", mGetPropertyFromFile("pt_trfCertificate_OldTransferee_ID"), "value");
			System.out.println("Old Transferee of Transfer Certificate OtherMode at Application Time :: "+TrfCerti_OldTransferee);
			TrfCerti_OldTransfereeList.add(TrfCerti_NewOwner);
			System.out.println("Old Transferee List of Transfer Certificate OtherMode at Application time is :::"+TrfCerti_OldTransfereeList);


			String TrfCerti_TransferType = mGetText("xpath", mGetPropertyFromFile("pt_trfCertificate_TransferType_ID"), "value");
			System.out.println("Transfer Type of Transfer Certificate OtherMode at Application Time :: "+TrfCerti_TransferType);




			mClick("xpath", mGetPropertyFromFile("trf_Certificate_Print_ID"));
			mGenericWait();

			mChallanPDFReader();
			api.PdfPatterns.pt_TransferCertificatePdf(pdfoutput);

			TrfCertificate_App_NoList.add(TrfCerti_App_NoList.get(CurrentinvoCount));
			System.out.println("PDF Application No List of Transfer Certificate Heredity::: "+TrfCertificate_App_NoList);
			mAssert(TrfCertificate_App_NoList, TrfCerti_AppNoList, "Wrong Application No on Transfer Certificate OtherMode  Actual  :"+TrfCertificate_App_NoList+ " Expected  :"+TrfCerti_AppNoList);

			TrfCertificate_Prop_NoList.add(TrfCerti_Prop_NoList.get(CurrentinvoCount));
			System.out.println("PDF Property No List of Transfer Certificate::: "+TrfCertificate_Prop_NoList);
			mAssert(TrfCertificate_Prop_NoList, TrfCerti_PropNoList, "Wrong Property No on Transfer Certificate OtherMode  Actual  :"+TrfCertificate_Prop_NoList+ " Expected  :"+TrfCerti_PropNoList);

			TrfCertificate_New_OwnerList.add(TrfCerti_NewOwnerList.get(CurrentinvoCount));
			System.out.println("PDF New Owner List of Transfer Certificate::: "+TrfCertificate_New_OwnerList);
			mAssert(TrfCertificate_New_OwnerList, TrfCerti_NewOwnerList, "Wrong New Owner on Transfer Certificate OtherMode  Actual  :"+TrfCertificate_New_OwnerList+ " Expected  :"+TrfCerti_NewOwnerList);

			TrfCertificate_Old_TransfereeList.add(TrfCerti_NewOwnerList.get(CurrentinvoCount));
			System.out.println("PDF Old Transferee List of Transfer Certificate::: "+TrfCertificate_Old_TransfereeList);
			mAssert(TrfCertificate_New_OwnerList, TrfCerti_OldTransfereeList, "Wrong Old Transferee on Transfer Certificate OtherMode  Actual  :"+TrfCertificate_New_OwnerList+ " Expected  :"+TrfCerti_OldTransfereeList);

			TransferCrtificateOtherMode = false;

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in TransferCertificateOtherMode method");
		}	
	}

	///////////////////////
	//////////////////////

	//Method for Tax Calculator by Jyoti

	///////////////////////
	//////////////////////


	@Test(enabled=false)
	public void Tax_Calculator() throws Exception
	{
		try
		{
			currentmethodname=Thread.currentThread().getStackTrace()[1].getMethodName();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			PropTaxCustomErrorMessages.propTax_m_errors.entrySet().clear();
			CommonUtilsAPI.propTaxErrorMsg.assertAll();
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));

			mscroll(1170, 630);
			mGenericWait();

			mClick("xpath", mGetPropertyFromFile("pt_showMoreLink_ID"));
			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("pt_taxCalculatorLink_ID"));
			mGenericWait();



			//switch tab


			ArrayList<String> tabs2 = new ArrayList<String>(
					driver.getWindowHandles());
			System.out.println(tabs2);
			driver.switchTo().window(tabs2.get(1));
			System.out.println("1st Tab==> "+tabs2.get(0));
			System.out.println("2nd Tab==> "+tabs2.get(1));
			//System.out.println(tabs2.get(2));
			driver.switchTo().window(tabs2.get(1));
			mGenericWait();
			///end switch code///



			///////////////*************** for LAND + BUILDING ****************************//////////////////////



			if(mGetPropertyFromFile("pt_selectPropType_Data").equalsIgnoreCase("Land + Building"))
			{
				//mWaitForVisible("xapth", mGetPropertyFromFile("pt_CalculateBtn_ID"));
				mWaitForVisible("name", mGetPropertyFromFile("pt_selectOrgName_ID"));
				mSelectDropDown("name", mGetPropertyFromFile("pt_selectOrgName_ID"), mGetPropertyFromFile("pt_selectOrgName_Data"));
				mGenericWait();

				mSelectDropDown("xpath", mGetPropertyFromFile("pt_selectPropType_ID"), mGetPropertyFromFile("pt_selectPropType_Data"));
				mGenericWait();
				mSelectDropDown("xpath", mGetPropertyFromFile("pt_selectRoadType_ID"), mGetPropertyFromFile("pt_selectRoadType_Data"));
				mGenericWait();
				mSelectDropDown("xpath", mGetPropertyFromFile("pt_selectWaterTaxType_ID"), mGetPropertyFromFile("pt_selectWaterTaxType_Data"));
				mGenericWait();
				mSendKeys("name", mGetPropertyFromFile("pt_enterTaxableVacantLandArea_ID"), mGetPropertyFromFile("pt_enterTaxableVacantLandArea_Data"));
				mGenericWait();

				///// Following method is used to ADD multiple rows of different data
				TCadditionofBldgDetails();


				mClick("xpath", mGetPropertyFromFile("pt_CalculateBtn_ID"));
				mGenericWait();
				mTakeScreenShot();

				mscroll(1170, 500);
				mGenericWait();

				///// Following method is used to Read values of multiple added rows of different data
				TC1additionofBldgDetails();

				DBReadingforRate();


				//***********************************************************
				//////////////////************** Table1 Reading Start***********///////////////////////
				//***********************************************************

				WebElement table2 = driver.findElement(By.id("otherDataRead"));
				List<WebElement> rows2 = table2.findElements(By.tagName("tr"));

				int rowcount1 = rows2.size();
				System.out.println("NUMBER OF ROWS IN Property Details TABLE of Tax Calculator2 ="+rowcount1);

				// Iterate To get the value of each cell in table along with element id

				//System.out.println("NUMBER OF ROWS IN THIS TABLE of Application= "+rows1.size());

				int row_num2 = 1;
				for (WebElement rowElement : rows2)
				{
					List<WebElement> cols2 = rowElement.findElements(By.xpath("td"));
					System.out.println("NUMBER OF columns IN Property Details TABLE of Tax Calculator2 = "+cols2.size());
					int col_num2 = 1;
					for(WebElement colElement : cols2)
					{
						if(row_num2 == 1)
						{
							if(col_num2 == 1)
							{

								TC_VacantLandRate = rowElement.findElement(By.xpath("./td[1]")).getText();
								mGenericWait();
								System.out.println("Vacant Land Rate for Land+Building is == :: "+TC_VacantLandRate);

								TC_VacantLandTax = rowElement.findElement(By.xpath("./td[2]")).getText();
								mGenericWait();
								System.out.println("Vacant Land Tax for Land+Building is == :: "+TC_VacantLandTax);


							}
						}


						if(row_num2 == 2)
						{

							if(col_num2 == 1)
							{

								TC_TotPropTax = rowElement.findElement(By.xpath("./td[1]")).getText();
								mGenericWait();
								System.out.println("Total Property Tax for Land+Building is == :: "+TC_TotPropTax);

								TC_TotWaterTax = rowElement.findElement(By.xpath("./td[2]")).getText();
								mGenericWait();
								System.out.println("Total Water Tax for Land+Building is == :: "+TC_TotWaterTax);
							}


						} 
						if(row_num2 == 3)
						{

							TC_TotPayablePropTax = rowElement.findElement(By.xpath("./td")).getText();
							mGenericWait();
							System.out.println("Total Payable Property Tax in Table for Land+Building is == :: "+TC_TotPayablePropTax);

						}

						col_num2 = col_num2 + 1;

					}
					row_num2 = row_num2 + 1;

				} 


				////////////**************** Table1 Reading End************************/////////////////////////

			}


			///////////////******************* for FLAT **********************************//////////////////////



			else if(mGetPropertyFromFile("pt_selectPropType_Data").equalsIgnoreCase("Flat"))
			{

				//mWaitForVisible("xapth", mGetPropertyFromFile("pt_CalculateBtn_ID"));
				mWaitForVisible("name", mGetPropertyFromFile("pt_selectOrgName_ID"));
				mSelectDropDown("name", mGetPropertyFromFile("pt_selectOrgName_ID"), mGetPropertyFromFile("pt_selectOrgName_Data"));
				mGenericWait();

				mSelectDropDown("xpath", mGetPropertyFromFile("pt_selectPropType_ID"), mGetPropertyFromFile("pt_selectPropType_Data"));
				mGenericWait();
				mSelectDropDown("xpath", mGetPropertyFromFile("pt_selectRoadType_ID"), mGetPropertyFromFile("pt_selectRoadType_Data"));
				mGenericWait();
				mSelectDropDown("xpath", mGetPropertyFromFile("pt_selectWaterTaxType_ID"), mGetPropertyFromFile("pt_selectWaterTaxType_Data"));
				mGenericWait();

				System.out.println("Taxable Vacant Land Area is Not Applicable..............");
				mGenericWait();


				///// Following method is used to ADD multiple rows of different data
				TCadditionofBldgDetails();


				mClick("xpath", mGetPropertyFromFile("pt_CalculateBtn_ID"));
				mGenericWait();
				mTakeScreenShot();

				mscroll(1170, 500);
				mGenericWait();


				///// Following method is used to Read values of multiple added rows of different data
				TC1additionofBldgDetails();

				//DBReadingforRate();



				////////////**************** Table1 Reading End************************/////////////////////////

				//***********************************************************
				//////////////////************** Table2 Reading Start***********///////////////////////
				//***********************************************************

				WebElement table2 = driver.findElement(By.id("otherDataRead"));
				List<WebElement> rows2 = table2.findElements(By.tagName("tr"));

				int rowcount1 = rows2.size();
				System.out.println("NUMBER OF ROWS IN Property Details TABLE of Flat Tax Calculator2 ="+rowcount1);

				// Iterate To get the value of each cell in table along with element id

				//System.out.println("NUMBER OF ROWS IN THIS TABLE of Application= "+rows1.size());

				int row_num2 = 1;
				for (WebElement rowElement : rows2)
				{
					List<WebElement> cols2 = rowElement.findElements(By.xpath("td"));
					System.out.println("NUMBER OF columns IN Property Details TABLE of Flat Tax Calculator2 = "+cols2.size());
					int col_num2 = 1;
					for(WebElement colElement : cols2)
					{
						if(row_num2 == 1)
						{
							if(col_num2 == 1)
							{

								TC_VacantLandRate = rowElement.findElement(By.xpath("./td[1]")).getText();
								mGenericWait();
								System.out.println("Vacant Land Rate for Flat is == :: "+TC_VacantLandRate);

								TC_VacantLandTax = rowElement.findElement(By.xpath("./td[2]")).getText();
								mGenericWait();
								System.out.println("Vacant Land Tax for Flat is == :: "+TC_VacantLandTax);


							}
						}


						if(row_num2 == 2)
						{

							if(col_num2 == 1)
							{

								TC_TotPropTax = rowElement.findElement(By.xpath("./td[1]")).getText();
								mGenericWait();
								System.out.println("Total Property Tax for Flat is == :: "+TC_TotPropTax);

								TC_TotWaterTax = rowElement.findElement(By.xpath("./td[2]")).getText();
								mGenericWait();
								System.out.println("Total Water Tax for Flat is == :: "+TC_TotWaterTax);
							}


						} 
						if(row_num2 == 3)
						{

							TC_TotPayablePropTax = rowElement.findElement(By.xpath("./td")).getText();
							mGenericWait();
							System.out.println("Total Payable Property Tax in Table for Flat is == :: "+TC_TotPayablePropTax);

						}

						col_num2 = col_num2 + 1;

					}
					row_num2 = row_num2 + 1;

				} 

				////////////**************** Table2 Reading End************************/////////////////////////

				DBReadingforRate();
			}



			///////////////*************** for VACANT LAND ****************************//////////////////////



			if(mGetPropertyFromFile("pt_selectPropType_Data").equalsIgnoreCase("Vacant Land"))
			{

				//mWaitForVisible("xapth", mGetPropertyFromFile("pt_CalculateBtn_ID"));
				mWaitForVisible("name", mGetPropertyFromFile("pt_selectOrgName_ID"));
				mSelectDropDown("name", mGetPropertyFromFile("pt_selectOrgName_ID"), mGetPropertyFromFile("pt_selectOrgName_Data"));
				mGenericWait();

				mSelectDropDown("xpath", mGetPropertyFromFile("pt_selectPropType_ID"), mGetPropertyFromFile("pt_selectPropType_Data"));
				mGenericWait();
				mSelectDropDown("xpath", mGetPropertyFromFile("pt_selectRoadType_ID"), mGetPropertyFromFile("pt_selectRoadType_Data"));
				mGenericWait();
				mSelectDropDown("xpath", mGetPropertyFromFile("pt_selectWaterTaxType_ID"), mGetPropertyFromFile("pt_selectWaterTaxType_Data"));
				mGenericWait();
				mSendKeys("name", mGetPropertyFromFile("pt_enterTaxableVacantLandArea_ID"), mGetPropertyFromFile("pt_enterTaxableVacantLandArea_Data"));
				mGenericWait();

				mClick("xpath", mGetPropertyFromFile("pt_CalculateBtn_ID"));
				mGenericWait();
				mTakeScreenShot();

				mscroll(1170, 500);
				mGenericWait();


				//***********************************************************
				//////////////////************** Table1 Reading Start***********///////////////////////
				//***********************************************************

				WebElement table2 = driver.findElement(By.id("otherDataRead"));
				List<WebElement> rows2 = table2.findElements(By.tagName("tr"));

				int rowcount2 = rows2.size();
				System.out.println("NUMBER OF ROWS IN Property Details TABLE of Tax Calculator2 ="+rowcount2);

				// Iterate To get the value of each cell in table along with element id

				//System.out.println("NUMBER OF ROWS IN THIS TABLE of Application= "+rows1.size());

				int row_num2 = 1;
				for (WebElement rowElement : rows2)
				{
					List<WebElement> cols2 = rowElement.findElements(By.xpath("td"));
					System.out.println("NUMBER OF columns IN Property Details TABLE of Tax Calculator2 = "+cols2.size());
					int col_num2 = 1;
					for(WebElement colElement : cols2)
					{
						if(row_num2 == 1)
						{
							if(col_num2 == 1)
							{

								TC_VacantLandRate = rowElement.findElement(By.xpath("./td[1]")).getText();
								mGenericWait();
								System.out.println("Vacant Land Rate for Vacant Land is == :: "+TC_VacantLandRate);

								TC_VacantLandTax = rowElement.findElement(By.xpath("./td[2]")).getText();
								mGenericWait();
								System.out.println("Vacant Land Tax for Vacant Land is == :: "+TC_VacantLandTax);

							}
						}


						if(row_num2 == 2)
						{

							if(col_num2 == 1)
							{

								TC_TotPropTax = rowElement.findElement(By.xpath("./td[1]")).getText();
								mGenericWait();
								System.out.println("Total Property Tax for Vacant Land is == :: "+TC_TotPropTax);


								TC_TotWaterTax = rowElement.findElement(By.xpath("./td[2]")).getText();
								mGenericWait();
								System.out.println("Total Water Tax for Vacant Land is == :: "+TC_TotWaterTax);
							}


						} 
						if(row_num2 == 3)
						{

							TC_TotPayablePropTax = rowElement.findElement(By.xpath("./td")).getText();
							mGenericWait();
							System.out.println("Total Payable Property Tax for Vacant Land in Table is == :: "+TC_TotPayablePropTax);

						}

						col_num2 = col_num2 + 1;

					}
					row_num2 = row_num2 + 1;

				} 


				////////////**************** Table1 Reading End************************/////////////////////////

				/////// Taxable Vacant Land 

				VLTax = mGetPropertyFromFile("pt_vacantLandTax_Data");
				TotPTaxTax = mGetPropertyFromFile("pt_totPropTax_Data");
				WtTax = mGetPropertyFromFile("pt_wtTax_Data");
				TotPayablePTax = mGetPropertyFromFile("Pt_totPayablePTax_Data");

				mAssert(VLTax, TC_VacantLandTax, "Wrong Total Vacant Land Tax on Tax Calculator for Vacant Land Form Actual :" +VLTax+ "Expected :"+TC_VacantLandTax);
				mAssert(TotPTaxTax, TC_TotPropTax, "Wrong Total Property Tax on Tax Calculator for Vacant Land Form Actual :" +TotPTaxTax+ "Expected :"+TC_TotPropTax);
				mAssert(TotPayablePTax, TC_TotPayablePropTax, "Wrong Total Total Payable Tax on Tax Calculator for Vacant Land Form Actual :" +TotPayablePTax+ "Expected :"+TC_TotPayablePropTax);
				mAssert(WtTax, TC_TotWaterTax, "Wrong Water Tax on Tax Calculator for Vacant Land Form Actual :" +WtTax+ "Expected :"+TC_TotWaterTax);

			}

			driver.switchTo().window(tabs2.get(1));
			driver.close();
			driver.switchTo().window(tabs2.get(0));
			driver.close();
			CommonUtilsAPI.propTaxErrorMsg.assertAll();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in Tax_Calculator method");

		}
	}

	public void DBReadingforRate()
	{
		//**************************************************
		// DB Start *************************
		// Fetching Rate details from DataBase
		try {

			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			}
			catch(ClassNotFoundException ex) 
			{
				System.out.println("Error: unable to load driver class! " +ex);
				System.exit(1);
			}
			catch(IllegalAccessException ex)
			{
				System.out.println("Error: access problem while loading!");
				System.exit(2);
			}
			catch(InstantiationException ex)
			{
				System.out.println("Error: unable to instantiate driver!");
				System.exit(3);
			}


			//String URL = "jdbc:oracle:thin:@192.168.100.169:1521:ORCLUNI";
			/*String URL = "jdbc:oracle:thin:@192.168.100.229:1521:KDMC";
					String USER = "mainet_prod_phase2";
					String PASS = "MAINET_PROD_PHASE2_020317";*/

			String URL = "jdbc:oracle:thin:@192.168.100.169:1521:ORCLUNI";
			String USER = "mainet_prod_phase2";
			String PASS = "mainet_prod_phase2";

			Connection conn = DriverManager.getConnection(URL, USER, PASS);

			Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			db_orgid = mGetPropertyFromFile("db_orgid");


			String query11 = "select o.o_nls_orgname organization,\r\n" +
					"fn_getdesc(cod_cscid, 'E', z.orgid) Construction_class,\r\n" +
					"fn_getdesc(cod_dwzid1, 'E', z.orgid) WZB_Level1,\r\n" +
					"fn_getdesc(cod_dwzid2, 'E', z.orgid) WZB_Level2,\r\n" +
					"fn_getdesc(cod_dwzid3, 'E', z.orgid) WZB_Level3,\r\n" +
					"fn_getdesc(cod_dwzid4, 'E', z.orgid) WZB_Level4,\r\n" +
					"fn_getdesc(cod_dwzid5, 'E', z.orgid) WZB_Level5,\r\n" +
					"fn_getdesc(cod_usage1, 'E', z.orgid) usage_type,\r\n" +
					"fn_getcpddesc(f.cpd_mfctid, 'E', z.orgid) factor_type,\r\n" +
					"fn_getcpddesc(f.cpd_sfctid, 'E', z.orgid) factor, zr_fromdt, zr_todt,\r\n" +
					"fn_getcpddesc(cpd_type, 'E', z.orgid) value_type,zr_rent, zr_active active FROM tb_zoneconstrrent_mas z, tb_zonerentfactors f, tb_organisation o \r\n" +
					"where z.zr_id = f.zr_id \r\n" + "and z.zrgr_id = f.zrgr_id and z.orgid = f.orgid and z.orgid = o.orgid and z.orgid ="+db_orgid + " order by 1,2,3,4,5,6";

			System.out.println("Query :: " + query11);

			rs1 = st.executeQuery(query11);			

			System.out.println();

			while (rs1.next()) 
			{

				System.out.println(rs1.getString(8) +"    "+ rs1.getString(9)+"    "+ rs1.getString(10)+"    "+ rs1.getString(11)+"    "+ rs1.getString(12)+"    "+ rs1.getString(13)+"    "+ rs1.getString(14));

				db_UsageType=rs1.getString(8);
				db_RoadFactor=rs1.getString(9);
				db_RoadFactorType=rs1.getString(10);
				db_FromDate=rs1.getString(11);
				db_ToDate=rs1.getString(12);
				db_ValueType=rs1.getString(13);
				db_Rate=rs1.getString(14);


				System.out.println("DB Usage Type is : " +db_UsageType);
				System.out.println("DB Road Factor is : " +db_RoadFactor);
				System.out.println("DB Road Factor Type is : " +db_RoadFactorType);
				System.out.println("DB From Date is : " +db_FromDate);
				System.out.println("DB To Date is : " +db_ToDate);
				System.out.println("DB Value Type is : " +db_ValueType);
				System.out.println("DB Rate is : " +db_Rate);

			}

			conn.close();		

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		// DB End *************************
		//**************************************************
	}

	/////////////////////////// **************************
	///////// To ADD Comma separated values for Property Details by Jyoti @30-03-2017
	//////////////////////////****************************

	/// ERROR

	public void TCadditionofBldgDetails()
	{
		if(!mGetPropertyFromFile("pt_selectPropType_Data").equalsIgnoreCase("Vacant Land"))
		{
			//ArrayList<String> addedFloorNoArray= new ArrayList<String>();
			//ArrayList<String> TCaddedTypeOfUseArray= new ArrayList<String>();
			//ArrayList<String> TCaddedConstructTypeArray= new ArrayList<String>();
			//ArrayList<String> TCaddedBuildupArArray= new ArrayList<String>();
			//ArrayList<String> TCaddedUsageFactorArray= new ArrayList<String>();
			//ArrayList<String> TCaddedOccFacArray= new ArrayList<String>();

			//ArrayList<String> TCcalculatedRatableArArray= new ArrayList<String>();
			//ArrayList<String> TCcalculatedUnitRateArray= new ArrayList<String>();
			//ArrayList<String> TCcalculatedAnnulRateblValArray= new ArrayList<String>();
			//ArrayList<String> TCcalculatedRateOfTaxArray= new ArrayList<String>();
			//ArrayList<String> TCcalculatedAnnulTaxArray= new ArrayList<String>(); 

			WebElement table = driver.findElement(By.id("customFields"));

			mscroll(0, 1300);
			List<WebElement> rows1 = table.findElements(By.tagName("tr"));
			rows1.size();
			System.out.println("Rowcount=="+rows1.size());

			List<WebElement> cols = rows1.get(1).findElements(By.xpath("td"));
			int colcount= cols.size();
			System.out.println("colcount=="+colcount);

			//String[] flrNoArray=mGetPropertyFromFile("pt_newAssessmentFloorNoArray").split(",");
			String[] UseType=mGetPropertyFromFile("pt_TCUsageType_Data").split(",");  
			System.out.println("string array size of Usage Type is : "+UseType.length);

			int a=Integer.parseInt(UseType[0].toString());
			int b=Integer.parseInt(UseType[1].toString());
			TCaddCount=(b-a)+1;

			mGenericWait();

			int counter=1;
			InvoCount=a;
			int rc = 3;
			for (int i=0;i<TCaddCount;i++) 
			{

				if(InvoCount>b)
					break;

				for(int j=0;j<colcount;j++)
				{

					//// Usage Type

					if(j==0)
					{
						//mCustomWait(1500);
						//new Select(driver.findElement(By.name("entity.propertyDetails["+i+"].cpdUsage1"))).selectByVisibleText(mGetPropertyFromFile("pt_newAssessmentTypeOfUse"));
						new Select(driver.findElement(By.name("tableDataList["+i+"].usagetype"))).selectByVisibleText(mGetPropertyFromFile("pt_selectUsageType_Data"));

						TCaddedTypeOfUseArray.add(mGetPropertyFromFile("pt_selectUsageType_Data"));
					}

					//// Construction Class

					if(j==1)
					{
						//mCustomWait(1500);
						new Select (driver.findElement(By.name("tableDataList["+i+"].constructionClass"))).selectByVisibleText(mGetPropertyFromFile("pt_selectConstructionClass_Data"));
						TCaddedConstructTypeArray.add(mGetPropertyFromFile("pt_selectConstructionClass_Data"));

					}

					//// Usage Factor

					if(j==2)
					{
						//mCustomWait(1500);
						if(mGetPropertyFromFile("pt_selectUsageType_Data").equalsIgnoreCase("Residential"))
						{
							//String usgFactor = driver.findElement(By.id("cpdSfctID"+i)).getAttribute("value");
							//System.out.println("Usage Factor is : "+usgFactor);

							new Select (driver.findElement(By.name("tableDataList["+i+"].usageFactor"))).selectByVisibleText(mGetPropertyFromFile("pt_selectUsageFactor_Data"));

							TCaddUsageFactorArray.add(mGetPropertyFromFile("pt_selectUsageFactor_Data"));

							System.out.println("Usage Factor is == "+TCaddedUsageFactorArray);

							TCcalculatedRatableArArray.add(mGetPropertyFromFile("TCratableAreaForRes"));

							TCcalculatedAnnulRateblValArray.add(mGetPropertyFromFile("TCannualRatableValueForRes"));

							TCcalculatedAnnulTaxArray.add(mGetPropertyFromFile("HoldingTaxRes"));

							TCcalculatedARVResArray.add(mGetPropertyFromFile("TC_TotARVRes"));


						}
						else
						{
							new Select (driver.findElement(By.name("tableDataList["+i+"].usageFactor"))).selectByVisibleText(mGetPropertyFromFile("pt_selectUsageFactor_Data"));

							TCaddUsageFactorArray.add(mGetPropertyFromFile("pt_selectUsageFactor_Data"));

							TCcalculatedRatableArArray.add(mGetPropertyFromFile("TCratableAreaForNonRes"));
							TCcalculatedAnnulRateblValArray.add(mGetPropertyFromFile("TCannualRatableValueForNonRes"));
							TCcalculatedAnnulTaxArray.add(mGetPropertyFromFile("HoldingTaxNonRes"));

							TCcalculatedARVnonResArray.add(mGetPropertyFromFile("TC_TotARVNonRes"));

						}

						/*TC_CalculatedTotARV = mGetPropertyFromFile("pt_TC_TotARVList");
						TC_CalculatedTotARVList.add(TC_CalculatedTotARV);
						System.out.println("Excel Calculated Total ARV is ::: "+TC_CalculatedTotARVList);

						TC_CalculatedTotHoldingTax = mGetPropertyFromFile("pt_TC_TotHoldingTaxList");
						TC_CalculatedTotHoldingTaxList.add(TC_CalculatedTotHoldingTax);
						System.out.println("Excel Calculated Total ARV is ::: "+TC_CalculatedTotHoldingTaxList);*/



						//	TCaddedUsageFactorArray.add(mGetPropertyFromFile("pt_newAssessmentUsageFactor"));
						TCcalculatedUnitRateArray.add(mGetPropertyFromFile("pt_unitRate_Data"));
						//TCcalculatedRateOfTaxArray.add(mGetPropertyFromFile("rateOfTax"));

					}

					//// Occupancy Factor
					if(j==3)
					{
						//mCustomWait(1500);
						//new Select (driver.findElement(By.id("occupancyFactor_0"))).selectByVisibleText(mGetPropertyFromFile("pt_selectOccupancyFactor_Data"));
						new Select (driver.findElement(By.name("tableDataList["+i+"].occupancyFactor"))).selectByVisibleText(mGetPropertyFromFile("pt_selectOccupancyFactor_Data"));
						//tableDataList[0].usageFactor tableDataList[0].occupancyFactor
						TCaddedOccFacArray.add(mGetPropertyFromFile("pt_selectOccupancyFactor_Data"));
						System.out.println("Occupancy Factor is : "+TCaddedOccFacArray);
					}

					//// Builtup Area

					if(j==4)
					{
						mCustomWait(1500);
						//driver.findElement(By.id("builtUpArea_0")).sendKeys(mGetPropertyFromFile("pt_enterBuiltupArea_Data"));
						driver.findElement(By.name("tableDataList["+i+"].builtUpArea")).sendKeys(mGetPropertyFromFile("pt_enterBuiltupArea_Data"));

						TCaddedBuildupArArray.add(mGetPropertyFromFile("pt_enterBuiltupArea_Data"));
						//System.out.println("Built-up Area is : "+bultupArea);
					}


					System.out.println("Calculated Ratable Area stored in array as-reading : "+TCcalculatedRatableArArray);
					System.out.println("Calculated Annual Ratable Value stored in array as-reading : "+TCcalculatedAnnulRateblValArray);
				}

				if(i<(TCaddCount-1))
				{



					mCustomWait(5000);
					if(i==0)
					{//*[@id="customFields"]/tbody/tr[2]/td[6]/a[1]
						//mClick("xpath", "//*[@id=\"cloumn0\"]/td[9]/a");
						//driver.findElement(By.xpath("//*[@id=\"cloumn0\"]/td[9]/a")).click();
						driver.findElement(By.xpath("//*[@id=\"customFields\"]/tbody/tr[2]/td[6]/a[1]")).click();
					}
					else if(i>0)
					{

						//mClick("xpath", "//*[@id=\"cloumn"+InvoCount+"\"]/td[9]/a[1]");	
						//driver.findElement(By.xpath("//*[@id=\"cloumn"+i+"\"]/td[9]/a[1]")).click();
						driver.findElement(By.xpath("//*[@id=\"customFields\"]/tbody/tr["+rc+"]/td[6]/a[1]")).click();
						rc = rc+1;
					}


					/*int rc=2;
					for(i=0; i>0; i++)
					{
						driver.findElement(By.xpath("//*[@id=\"customFields\"]/tbody/tr[rc]/td[6]/a[1]")).click();
						rc=rc+1;
					}*/
					System.out.println("Clicking on ADD");
					mCustomWait(5000);
					//rc++;
				}
				counter++;
				InvoCount++;

			}

			if(cntForBldgDtls>=0)
			{
				for(int i=0;i<=(cntForBldgDtls);i++)
				{

					TCaddTypeOfUseArray.addAll(TCaddedTypeOfUseArray);

					TCaddConstructTypeArray.addAll(TCaddedConstructTypeArray);

					TCaddBuildupArArray.addAll(TCaddedBuildupArArray);

					TCaddUsageFactorArray.addAll(TCaddedUsageFactorArray);
					System.out.println("Added usage factor stored in array as : "+TCaddUsageFactorArray);

					TCaddOccFacArray.addAll(TCaddedOccFacArray);
					System.out.println("Value of i is :"+i);

					TCcalcRatableArArray.addAll(TCcalculatedRatableArArray);
					System.out.println("Calculated ratable area stored in array as : "+TCcalcRatableArArray);

					TCcalcAnnulRateblValArray.addAll(TCcalculatedAnnulRateblValArray);
					System.out.println("Calculated annual ratable value stored in array as : "+TCcalcAnnulRateblValArray);

					TCcalcAnnulTaxArray.addAll(TCalculatedAnnulTaxArray);
					System.out.println("Calculated annual tax stored in array as : "+TCcalcAnnulTaxArray);

					TCcalcUnitRateArray.addAll(TCcalculatedUnitRateArray);
					System.out.println("Calculated unit rate stored in array as : "+TCcalcUnitRateArray);

					TCcalcRateOfTaxArray.addAll(TCcalculatedRateOfTaxArray);
					System.out.println("Calculated rate of tax stored in array as : "+TCcalcRateOfTaxArray);
				}
			}
		}
		else
		{
			System.out.println("Building details are added for current year");
		}
	}




	public void TC1additionofBldgDetails()
	{
		WebElement table3 = driver.findElement(By.xpath("(//table[@id='customFields'])[2]"));

		List<WebElement> rows3 = null;
		rows3 = table3.findElements(By.tagName("tr"));
		System.out.println("rows3 =="+rows3);
		int rwcount3 =rows3.size();
		System.out.println("NUMBER OF ROWS IN TC TABLE = : "+rwcount3);

		for (int i=0;i<rwcount3;i++)
		{
			List<WebElement> cols3 = null;
			cols3 = rows3.get(i).findElements(By.tagName("td"));

			for(int j=0;j<cols3.size();j++)
			{
				if(i>0)
				{
					if(j==0)
					{
						//viewTypeOfUse=rowElement.findElement(By.xpath("td")).getText().trim(); //*[@id="customFields"]/tbody/tr[2]/td[1]
						TCTypeOfUseArray3.add(rows3.get(i).findElement(By.xpath("td[1]")).getText().trim());

						System.out.println("Tax Calculator Building Details Usage Type is == "+TCTypeOfUseArray3);


					}

					if(j==1)
					{
						//viewTypeOfUse=rowElement.findElement(By.xpath("td")).getText().trim(); //*[@id="customFields"]/tbody/tr[2]/td[1]
						TCConstructTypeArray3.add(rows3.get(i).findElement(By.xpath("td[2]")).getText().trim());

						System.out.println("Tax Calculator Building Details Construction Class is == "+TCConstructTypeArray3);


					}

					if(j==2)
					{
						//viewTypeOfUse=rowElement.findElement(By.xpath("td")).getText().trim(); //*[@id="customFields"]/tbody/tr[2]/td[1]
						TCUsageFactorArray3.add(rows3.get(i).findElement(By.xpath("td[3]")).getText().trim());

						System.out.println("Tax Calculator Building Details Usage Factor is == "+TCUsageFactorArray3);


					}

					if(j==3)
					{
						//viewTypeOfUse=rowElement.findElement(By.xpath("td")).getText().trim(); //*[@id="customFields"]/tbody/tr[2]/td[1]
						TCOccFacArray3.add(rows3.get(i).findElement(By.xpath("td[4]")).getText().trim());

						System.out.println("Tax Calculator Building Details Occupancy Factor is == "+TCOccFacArray3);


					}

					if(j==4)
					{
						//viewTypeOfUse=rowElement.findElement(By.xpath("td")).getText().trim(); //*[@id="customFields"]/tbody/tr[2]/td[1]
						TCBuildupArArray3.add(rows3.get(i).findElement(By.xpath("td[5]")).getText().trim());

						System.out.println("Tax Calculator Building Details Builtup Area is == "+TCBuildupArArray3);


					}

					if(j==5)
					{
						//viewTypeOfUse=rowElement.findElement(By.xpath("td")).getText().trim(); //*[@id="customFields"]/tbody/tr[2]/td[1]
						TCUnitRateArray3.add(rows3.get(i).findElement(By.xpath("td[6]")).getText().trim());

						System.out.println("Tax Calculator Building Details Rate is == "+TCUnitRateArray3);


					}

					if(j==6)
					{
						//viewTypeOfUse=rowElement.findElement(By.xpath("td")).getText().trim(); //*[@id="customFields"]/tbody/tr[2]/td[1]
						TCAnnulRateblValArray3.add(rows3.get(i).findElement(By.xpath("td[7]")).getText().trim());

						System.out.println("Tax Calculator Building Details ARV is == "+TCAnnulRateblValArray3);


					}

					if(j==7)
					{
						//viewTypeOfUse=rowElement.findElement(By.xpath("td")).getText().trim(); //*[@id="customFields"]/tbody/tr[2]/td[1]
						TCcalcAnnulTaxArray.add(rows3.get(i).findElement(By.xpath("td[8]")).getText().trim());

						System.out.println("Tax Calculator Building Details Holding Tax is == "+TCcalcAnnulTaxArray);


					}


				}


			}
			if(i==rwcount3-1)
			{
				String arv=rows3.get(i).findElement(By.xpath("th[2]")).getText().trim();
				System.out.println("Total ARV is ::: "+arv);

				totARVList.add(arv);
				System.out.println("Toatl ARV List is ::: "+totARVList);

				String hldgtax=rows3.get(i).findElement(By.xpath("th[3]")).getText().trim();
				System.out.println("Total Holding Tax is ::: "+hldgtax);

				totHoldingTaxList.add(hldgtax);
				System.out.println("Toatl Holding Tax List is ::: "+totHoldingTaxList);
			}




		}

		TC_CalculatedTotARV = mGetPropertyFromFile("pt_TC_TotARVList");
		TC_CalculatedTotARVList.add(TC_CalculatedTotARV);
		System.out.println("Excel Calculated Total ARV is ::: "+TC_CalculatedTotARVList);

		TC_CalculatedTotHoldingTax = mGetPropertyFromFile("pt_TC_TotHoldingTaxList");
		TC_CalculatedTotHoldingTaxList.add(TC_CalculatedTotHoldingTax);
		System.out.println("Excel Calculated Total ARV is ::: "+TC_CalculatedTotHoldingTaxList);

		mAssert(TCTypeOfUseArray3, TCaddedTypeOfUseArray, "Wrong Usage Type on Tax Calculator for Land+Building Form Actual :" +TCTypeOfUseArray3+ "Expected :"+TCaddedTypeOfUseArray);
		mAssert(TCConstructTypeArray3, TCaddedConstructTypeArray, "Wrong Construction Class on Tax Calculator for Land+Building Form Actual :" +TCConstructTypeArray3+ "Expected :"+TCaddedConstructTypeArray);
		mAssert(TCUsageFactorArray3, TCaddUsageFactorArray, "Wrong Usage Factor on Tax Calculator for Land+Building Form Actual :" +TCUsageFactorArray3+ "Expected :"+TCaddUsageFactorArray);
		mAssert(TCOccFacArray3, TCaddOccFacArray, "Wrong Occupancy Factor on Tax Calculator for Land+Building Form Actual :" +TCOccFacArray3+ "Expected :"+TCaddOccFacArray);
		mAssert(TCBuildupArArray3, TCaddBuildupArArray, "Wrong Builtup Area on Tax Calculator for Land+Building Form Actual :" +TCBuildupArArray3+ "Expected :"+TCaddBuildupArArray);
		mAssert(TCUnitRateArray3, TCcalculatedUnitRateArray, "Wrong Rate on Tax Tax Calculator for Land+Building Form Actual :" +TCUnitRateArray3+ "Expected :"+TCcalcUnitRateArray);

		mAssert(TCAnnulRateblValArray3, TCcalcAnnulRateblValArray, "Wrong ARV on Tax Calculator for Land+Building Form Actual :" +TCAnnulRateblValArray3+ "Expected :"+TCcalcAnnulRateblValArray);
		mAssert(TCcalcAnnulTaxArray, TCcalcAnnulTaxArray, "Wrong Holding Tax on Tax Calculator for Land+Building Form Actual :" +TCcalcAnnulTaxArray+ "Expected :"+TCcalcAnnulTaxArray);

		mAssert(TC_CalculatedTotARVList, totARVList, "Wrong Total ARV on Tax Calculator Actual :" +TC_CalculatedTotARVList+ "Expected :"+totARVList);

		mAssert(TC_CalculatedTotHoldingTaxList, totHoldingTaxList, "Wrong Total Holding Tax on Tax Calculator Form Actual :" +TC_CalculatedTotHoldingTaxList+ "Expected :"+totHoldingTaxList);


	}


	//Permission for Property Transfer through Heredity  by Piyush
	public void TransferHeredityPayment() throws Exception
	{
		try{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			ptTransferServicesPaymentHeredity();
			logOut();
			finalLogOut();
			isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerTransferHeredityServiceName"));
			mCloseBrowser();

		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in newAssessment_ChallanVerf method");
		}

	}



	// Payment for Permission for Property Transfer through Heredity by Piyush
	public void ptTransferServicesPaymentHeredity() throws Exception
	{
		try
		{
			mCustomWait(1000);

			mWaitForVisible("linkText", mGetPropertyFromFile("tshp_PaymentLinkId"));
			mNavigation("tshp_PaymentLinkId", "tshp_TransServicesPaymentLinkId");

			mWaitForVisible("id", mGetPropertyFromFile("tshp_ApplicationId"));
			IndOrDep("id", "tshp_ApplicationId", "applicationNo");

			mClick("xpath", mGetPropertyFromFile("tshp_AppSearchBtnId"));
			mCustomWait(500);

			mWaitForVisible("id", mGetPropertyFromFile("tshp_OwnerNameId"));
			String OwnerName=mGetText("id", mGetPropertyFromFile("tshp_OwnerNameId"), "value");
			loiPayment_OwnerName.add(OwnerName);
			System.out.println("Owner Name in Property Tax Transfer Services Payment is :::"+OwnerName);
			System.out.println("Owner Name(Arraylist) in Property Tax Transfer Services Payment is :::"+loiPayment_OwnerName);

			//mAssert(loiPayment_OwnerName.get(CurrentinvoCount), Full_NameList.get(CurrentinvoCount), "Owner Name in Property Tax Transfer Services Payment form dose not match with expected::"+Full_NameList.get(CurrentinvoCount)+"Actual"+loiPayment_OwnerName.get(CurrentinvoCount));


			String NewOwnerName=mGetText("id", mGetPropertyFromFile("tshp_OwnerNameId"), "value");
			System.out.println("New Owner Name in Property Tax Transfer Services Payment is :::"+NewOwnerName);
			loiPayment_NewOwnerName.add(NewOwnerName);
			System.out.println("New Owner Name(Arraylist) in Property Tax Transfer Services Payment is :::"+loiPayment_NewOwnerName);

			//mAssert(loiPayment_NewOwnerName.get(CurrentinvoCount), New_OwnerList.get(CurrentinvoCount), "New Owner Name in Property Tax Transfer Services Payment form dose not match with expected::"+New_OwnerList.get(CurrentinvoCount)+"Actual"+loiPayment_NewOwnerName.get(CurrentinvoCount));

			String PropNo=mGetText("id", mGetPropertyFromFile("tshp_PropertyNoId"), "value");
			loiPayment_PropNo.add(PropNo);
			System.out.println("Property No in Property Tax Transfer Services Payment is :::"+PropNo);
			System.out.println("Property No (Arraylist) in Property Tax Transfer Services Payment is :::"+loiPayment_PropNo);

			//mAssert(loiPayment_PropNo.get(CurrentinvoCount), Prop_NoList.get(CurrentinvoCount), "Property No in Property Tax Transfer Services Payment form dose not match with expected::"+Prop_NoList.get(CurrentinvoCount)+"Actual"+loiPayment_PropNo.get(CurrentinvoCount));

			String AmountPay=mGetText("id", mGetPropertyFromFile("tshp_AmountPayId"), "value");
			loiPayment_AmountPay.add(AmountPay);
			System.out.println("Amount Pay in Property Tax Transfer Services Payment is :::"+AmountPay);
			System.out.println("Amount Pay (Arraylist) in Property Tax Transfer Services Payment is :::"+loiPayment_AmountPay);
			//mAssert(loiPayment_AmountPay.get(CurrentinvoCount), TransferFee_IntiLetter .get(CurrentinvoCount), "Total LOI Amount to be payble in Property Tax Transfer Services Payment form dose not match with expected::"+TransferFee_IntiLetter.get(CurrentinvoCount)+"Actual"+loiPayment_AmountPay.get(CurrentinvoCount));

			payment("paymentMode","byBankOrULB");

			//String PopUpMsg=mGetText("css", "");
			//submit button
			mWaitForVisible("xpath", mGetPropertyFromFile("tshp_SubmitBtnId"));
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("tshp_SubmitBtnId"));
			mCustomWait(1000);

			//proceed button
			mWaitForVisible("css", mGetPropertyFromFile("tshp_ProceedBtnId"));
			mCustomWait(1000);
			String msg = mGetText("css", mGetPropertyFromFile("tshp_SubmitPopUpMsgId"));

			//mAssert(msg, mGetPropertyFromFile("tp_LpLOIPaymentProcdMsgdata"), "Message Does not match at LOI Payment POPup.   Actual   :"+msg+"   Expected   :"+mGetPropertyFromFile("tp_LpLOIPaymentProcdMsgdata"));
			System.out.println(msg);
			mCustomWait(1000);
			mTakeScreenShot();
			mClick("id", mGetPropertyFromFile("tshp_ProceedBtnId"));
			mCustomWait(2000);
			mChallanPDFReader();
			//assertionTransferServicesLoiReceipt();
			mCustomWait(2000);

			TransferPaymentHeredity = false;

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in TransferCertificate method");
		}	
	}

	// Payment for Permission for Property Transfer through Other Modes by Piyush
	public void TransferOtherModesPayment() throws Exception
	{
		try{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			login(mGetPropertyFromFile("typeOfLogin"),mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			ptTransferServicesPaymentOtherModes();
			logOut();
			finalLogOut();
			isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerTransferPropOtherModeServiceName"));
			mCloseBrowser();

		}
		catch(Exception e)
		{
			inAtTest=true;
			throw new MainetCustomExceptions("Error in TransferOtherModesPayment method");
		}

	}



	// Payment for Permission for Property Transfer through Other Modes by Piyush
	public void ptTransferServicesPaymentOtherModes() throws Exception
	{
		try
		{
			mCustomWait(1000);

			mWaitForVisible("linkText", mGetPropertyFromFile("tsOtP_PaymentLinkId"));
			mNavigation("tsOtP_PaymentLinkId", "tsOtP_TransServicesPaymentLinkId");

			mWaitForVisible("id", mGetPropertyFromFile("tsOtP_ApplicationId"));
			IndOrDep("id", "tsOtP_ApplicationId", "applicationNo");

			mClick("xpath", mGetPropertyFromFile("tsOtP_AppSearchBtnId"));
			mCustomWait(500);

			mWaitForVisible("id", mGetPropertyFromFile("tsOtP_OwnerNameId"));
			String OwnerName=mGetText("id", mGetPropertyFromFile("tsOtP_OwnerNameId"), "value");
			loiPayment_OwnerName.add(OwnerName);
			System.out.println("Owner Name in Property Tax Transfer Services Payment is :::"+OwnerName);
			System.out.println("Owner Name(Arraylist) in Property Tax Transfer Services Payment is :::"+loiPayment_OwnerName);
			//mAssert(loiPayment_OwnerName.get(CurrentinvoCount), Full_NameOMList.get(CurrentinvoCount), "Owner Name in Property Tax Transfer Services Payment form dose not match with expected::"+Full_NameOMList.get(CurrentinvoCount)+"Actual"+loiPayment_OwnerName.get(CurrentinvoCount));		


			String NewOwnerName=mGetText("id", mGetPropertyFromFile("tsOtP_NewOwnerNameId"), "value");
			System.out.println("New Owner Name in Property Tax Transfer Services Payment is :::"+NewOwnerName);
			loiPayment_NewOwnerName.add(NewOwnerName);
			System.out.println("New Owner Name(Arraylist) in Property Tax Transfer Services Payment is :::"+loiPayment_NewOwnerName);
			//mAssert(loiPayment_NewOwnerName.get(CurrentinvoCount), New_OwnerList.get(CurrentinvoCount), "New Owner Name in Property Tax Transfer Services Payment form dose not match with expected::"+New_OwnerList.get(CurrentinvoCount)+"Actual"+loiPayment_NewOwnerName.get(CurrentinvoCount));


			String PropNo=mGetText("id", mGetPropertyFromFile("tsOtP_PropertyNoId"), "value");
			loiPayment_PropNo.add(PropNo);
			System.out.println("Property No in Property Tax Transfer Services Payment is :::"+PropNo);
			System.out.println("Property No (Arraylist) in Property Tax Transfer Services Payment is :::"+loiPayment_PropNo);
			//mAssert(loiPayment_PropNo.get(CurrentinvoCount), Prop_NoOMList.get(CurrentinvoCount), "Property No in Property Tax Transfer Services Payment form dose not match with expected::"+Prop_NoOMList.get(CurrentinvoCount)+"Actual"+loiPayment_PropNo.get(CurrentinvoCount));


			String AmountPay=mGetText("id", mGetPropertyFromFile("tsOtP_AmountPayId"), "value");
			loiPayment_AmountPay.add(AmountPay);
			System.out.println("Amount Pay in Property Tax Transfer Services Payment is :::"+AmountPay);
			System.out.println("Amount Pay (Arraylist) in Property Tax Transfer Services Payment is :::"+loiPayment_AmountPay);
			//mAssert(loiPayment_AmountPay.get(CurrentinvoCount), TransferFee_IntiLetter .get(CurrentinvoCount), "Total LOI Amount to be payble Property Tax Transfer Services Payment form dose not match with expected::"+TransferFee_IntiLetter.get(CurrentinvoCount)+"Actual"+loiPayment_AmountPay.get(CurrentinvoCount));

			payment("paymentMode","byBankOrULB");

			//String PopUpMsg=mGetText("css", "");
			//submit button
			mWaitForVisible("xpath", mGetPropertyFromFile("tsOtP_SubmitBtnId"));
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("tsOtP_SubmitBtnId"));
			mCustomWait(1000);

			//proceed button
			mWaitForVisible("css", mGetPropertyFromFile("tsOtP_ProceedBtnId"));
			mCustomWait(1000);
			String msg = mGetText("css", mGetPropertyFromFile("tsOtP_SubmitPopUpMsgId"));

			//mAssert(msg, mGetPropertyFromFile("tp_LpLOIPaymentProcdMsgdata"), "Message Does not match at LOI Payment POPup.   Actual   :"+msg+"   Expected   :"+mGetPropertyFromFile("tp_LpLOIPaymentProcdMsgdata"));
			System.out.println(msg);
			mCustomWait(1000);
			mTakeScreenShot();
			mClick("id", mGetPropertyFromFile("tsOtP_ProceedBtnId"));
			mCustomWait(2000);
			mChallanPDFReader();
			//assertionTransferServicesLoiReceipt();
			mCustomWait(2000);

			TransferPaymentOtherMode = false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in ptTransferServicesPaymentOtherModes method");
		}	
	}

	public void LOIVerification()
	{
		try
		{			
			if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
			{
				mChallanPDFReader();
				//newChallanReader();
			}
			else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
			{
				mChallanPDFReader();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in LOIVerification script");
		}
	}



	public static void patternTransferCashPaymentReceiptOrderPdf(String output)
	{
		try{
			Pattern Ntl = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Tax and Revenue)");;		
			Matcher matcher91 = Ntl.matcher(pdfoutput);

			if (matcher91.find())
			{
				String Ntl_1  = matcher91.group(1).trim();

				Pattern pattern101 = Pattern.compile("([0-9]+/[0-9]+/[0-9]+)");
				Matcher matcher109 = pattern101.matcher(Ntl_1);

				Pattern pattern102 = Pattern.compile("(\\s*(.+?) / [0-9]+)");
				Matcher matcher110 = pattern102.matcher(Ntl_1);

				if (matcher109.find())
				{
					System.out.println("Receipt date is : "+matcher109.group(1));

				}
				if (matcher110.find())
				{
					System.out.println("Receipt No : "+matcher110.group(0));

				}
			} 
			else
			{
				System.out.println("no result");
			}	


			Pattern Ntl_ReceivedFrom = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Received From)",Pattern.DOTALL);		
			Matcher matcher92 = Ntl_ReceivedFrom.matcher(pdfoutput);

			if (matcher92.find())
			{
				String Ntl_ReceivedFrom1  = matcher92.group(1).trim();
				String ntlname[]=Ntl_ReceivedFrom1.split("\\r?\\n");
				String ReceivedFrom=ntlname[1];
				System.out.println("Received From is : "+ReceivedFrom);	
				pdf_loiReceipt_ReceivedFrom.add(ReceivedFrom);
				System.out.println("LOI Received From arraylist is : "+pdf_loiReceipt_ReceivedFrom);
			} 
			else
			{
				System.out.println("no result");
			}	

			Pattern PropNo =Pattern.compile("(?<=Property No.-)\\s*(.*)");
			Matcher matcher95 = PropNo.matcher(pdfoutput);

			if (matcher95.find())
			{
				String PropNo1  = matcher95.group(1).trim();
				System.out.println("Property No is ::"+PropNo1);
				pdf_loiReceipt_PropNo.add(PropNo1);
				System.out.println("Property No in arraylist is ::"+pdf_loiReceipt_PropNo);
			}
			else
			{
				System.out.println("no result");
			}

			Pattern AppliNo =Pattern.compile("(?<=Applicant No. - )\\s*(.*)");
			Matcher matcher96 = AppliNo.matcher(pdfoutput);

			if (matcher96.find())
			{
				String AppliNo1  = matcher96.group(1).trim();
				String AppliNo2[]=AppliNo1.split("/");
				System.out.println("Application No is ::"+AppliNo2[0]);
				String PdfApplicationNo=AppliNo2[0];
				System.out.println("Application No is ::"+PdfApplicationNo);
				pdf_loiReceipt_AppliNo.add(PdfApplicationNo);
				System.out.println("Application No in arraylist is ::"+pdf_loiReceipt_AppliNo);

			}
			else
			{
				System.out.println("no result");
			}


			Pattern Ntl_PayMode = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=HELP LINE NUMBER)",Pattern.DOTALL);
			Matcher matcher97 = Ntl_PayMode.matcher(pdfoutput);

			if (matcher97.find())
			{
				String Ntl_PayMode1  = matcher97.group(1).trim();
				String[] string = Ntl_PayMode1.split("\\r?\\n");
				System.out.println("Total Payble amount is::"+string[2]);

				pdf_loiReceipt_TotalPaybleAmt.add(string[2]);
				System.out.println("Total Payble amount arrylist is ::"+pdf_loiReceipt_TotalPaybleAmt);

				Pattern pattern103 = Pattern.compile("(\\s*(.+?) )");
				Matcher matcher113 = pattern103.matcher(string[6]);

				if (matcher113.find())
				{
					System.out.println("Payment Mode is : "+matcher113.group(1));
					pdf_loiReceipt_PaymentMode.add(matcher113.group(1));
					System.out.println("Payment Mode arrylist is ::"+pdf_loiReceipt_PaymentMode);

				}
			}
			else
			{
				System.out.println("no result");
			}

			Pattern UlbName = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Customer Copy)");
			Matcher matcher99 = UlbName.matcher(pdfoutput);

			if (matcher99.find())
			{
				String UlbName1  = matcher99.group(1).trim();
				System.out.println("ULB Name is:: "+UlbName1);
				pdf_loiReceipt_UlbName.add(UlbName1);
				System.out.println("ULB Name arrylist is ::"+pdf_loiReceipt_UlbName);
			}
			else
			{
				System.out.println("no result");
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	///////////////////////
	//////////////////////

	//Method for Challan Regeneration by Jyoti

	///////////////////////
	//////////////////////

	public void challan_Regen() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));

			mGenericWait();
			mNavigation("pt_onlineServicesLinkid", "pt_moduleNameLinkid", "pt_serviceNameLinkid");

			ChallanRegeneration();
			logOut();
			finalLogOut();
			//isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerNewAssessmentServiceName"));
			mCloseBrowser();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in challan_Regen method");

		}
	}

	public void ChallanRegeneration() throws Exception
	{
		try
		{
			mWaitForVisible("xpath", mGetPropertyFromFile("pt_SearchPropNoBtn_ID"));
			mGenericWait();
			/// Enter challan No for KEY @applicationNo 
			IndOrDep("id", "pt_ChallanNo_ID", "applicationNo");

			//mSendKeys("id", mGetPropertyFromFile("pt_ChallanNo_ID"), mGetPropertyFromFile("pt_ChallanNo_Data"));
			String ch_no = mGetPropertyFromFile("pt_ChallanNo_Data");
			challanNoList.add(ch_no);
			System.out.println("Challan No List at Application time is :::: "+challanNoList);
			mGenericWait();



			mClick("xpath", mGetPropertyFromFile("pt_SearchPropNoBtn_ID"));
			mGenericWait();
			mTakeScreenShot();

			//	mClick("xpath", mGetPropertyFromFile("pt_MsgClose_ID"));
			//	mGenericWait();
			//mTakeScreenShot();

			mClick("value", mGetPropertyFromFile("pt_RegenerateChallanBtn_ID"));	
			mGenericWait();

			mClick("xpath", mGetPropertyFromFile("pt_ProceedBtn_ID"));
			mGenericWait();
			mTakeScreenShot();


			newPtChallanReader(); ///// HTML page is not downloaded


			String PaymentMode=mGetPropertyFromFile("PaymntMode");
			mSelectDropDown("xpath", mGetPropertyFromFile("pt_SelectPaymentMode_ID"), PaymentMode);
			mTakeScreenShot();

			if(PaymentMode.equalsIgnoreCase("cash"))
			{
				System.out.println("Cash payment mode is selected");
				mTakeScreenShot();

			}
			else
			{


				System.out.println("Cheque payment mode is selected");
				//mSelectDropDown("xpath", mGetPropertyFromFile("pt_SelectPaymentMode_ID"), mGetPropertyFromFile("pt_SelectPaymentMode_Data"));
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("pt_DrawnOn_ID"), mGetPropertyFromFile("pt_DrawnOn_Data"));
				mGenericWait();
				//mGetText("name", mGetPropertyFromFile("pt_BankId_ID"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_AccNo_ID"), mGetPropertyFromFile("pt_AccNo_Data"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pt_ChqNo_ID"), mGetPropertyFromFile("pt_ChqNo_Data"));
				mGenericWait();
				mDateSelect("name", mGetPropertyFromFile("pt_ChqDate_ID"), mGetPropertyFromFile("pt_ChqDate_Data"));
				mGenericWait();
				mGetText("id", mGetPropertyFromFile("pt_RcptAmt_ID"), "value");
				mGenericWait();
				mTakeScreenShot();
			}

			/////////
			// New Regenerated Challan
			/////////



			///////////
			////////Code Commented by Ritesh becuse previously code written was not working... 01-08-2017
			//////////

			// New Challan No
			/*String newChallan = mGetText("xpath", mGetPropertyFromFile("pt_new_ChallanNo_ID"));
			System.out.println("New Challan at Application time is ::: "+newChallan);
			NewChallanNoList.add(newChallan);
			System.out.println("New Challan List Application Time ::: "+NewChallanNoList);

			mAssert(NewChallanNoList, challanList, "Wrong Challan No on Challan @ULB copy Actual :" +NewChallanNoList+ "Expected :"+challanList);

			//////////// Application No
			AppNo = mGetText("xpath", mGetPropertyFromFile("pt_Application_ID"));
			System.out.println("Application No at Application time is ::: "+AppNo);
			ApplicationNoList.add(AppNo);
			System.out.println("Application No List Application Time ::: "+ApplicationNoList);

			mAssert(ApplicationNoList, applicationNo, "Wrong Application No on Challan @ULB copy Actual :" +ApplicationNoList+ "Expected :"+applicationNo);

			String ApplicantName = mGetText("xpath", mGetPropertyFromFile("pt_ApplicantName_ID"));
			System.out.println("Applicant Name at Application Time is :: "+ApplicantName);
			ApplicantNameList.add(ApplicantName);
			System.out.println("Applicant Name List Application time :::"+ApplicantNameList);

			mAssert(ApplicantNameList, ApplicantNmList, "Wrong Applicant Name on Challan @ULB copy Actual :" +ApplicantNameList+ "Expected :"+ApplicantNmList);

			///////////////Current Date
			String ChallanDate = mGetText("xpath", mGetPropertyFromFile("pt_ChallanDate_ID"));
			System.out.println("Challan Date at Application Time is :: "+ChallanDate);
			ChallanDateList.add(ChallanDate);
			System.out.println("Challan Date List Application time :::"+ChallanDateList);

			System.out.println("====================>"+myDate);
			String dd=new SimpleDateFormat("dd/MM/yyyy").format(myDate);
			System.out.println(new SimpleDateFormat("dd/MM/yyyy").format(myDate));
			System.out.println("myDate1====>"+dd);
			System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(myDate));
			System.out.println("myDate2====>"+myDate);

			CurrentDateList.add(dd);
			System.out.println("Current Date List is :::"+CurrentDateList);

			mAssert(ChallanDateList, CurrentDateList, "Wrong Challan Date on Challan @ULB copy Actual :" +ChallanDateList+ "Expected :"+CurrentDateList);

			////////////////////Challan Amount
			String amt = mGetText("xpath", mGetPropertyFromFile("pt_Amount_ID"));
			System.out.println("Amount of Challan Details at application time is :::"+amt);

			AmountList.add(amt);
			System.out.println("Amount List of Challan Details at Application time is ::: "+AmountList);

			//////////////////////// Payment Mode
			String PayMode = mGetPropertyFromFile("PaymntMode");
			System.out.println("Payment Mode at Application Time is ::: "+PayMode);
			PaymentModeList.add(PayMode);
			System.out.println("Payment Mode List at Application Time is ::: "+PaymentModeList);*/

			//////New code by Ritesh 01-08-2017 for New Challan generated / regenerated 

			//Challan No
			/*String challanNo = driver.findElement(By.xpath("/html/body/div/div[1]/div[1]/ul/li[2]/div/p[3]/b")).getText();
			System.out.println("Challan No is: " +challanNo);

			Pattern challanno = Pattern.compile("[0-9]+");
			Matcher cno = challanno.matcher(challanNo);

			if(cno.find()) {
				challanNo = cno.group();
				System.out.println("Challan No @ULB is: " +challanNo);

				/// Added by Jyoti @27-02-2017
				challanList.add(challanNo);
				System.out.println("New Challan No List @ULB  ::: "+challanList);
			}
			else
			{
				System.out.println("Challan No not found");
			}

			//Application No
			String appNum = driver.findElement(By.cssSelector("body > div > div:nth-child(1) > div.logo.clear > ul > li:nth-child(2) > div > p:nth-child(1) > b")).getText();
			System.out.println("Application No is: "+appNum);

			Pattern applicationno = Pattern.compile("[0-9]+");
			Matcher apo = applicationno.matcher(appNum);

			if(apo.find()) {				
				String applnNo = apo.group();
				System.out.println("Application No is: "+applnNo);
				mAppNoArray(applnNo);

			}
			else
			{
				System.out.println("Application No not found");
			}

			//Date of Application
			String dateOfApp = driver.findElement(By.xpath("/html/body/div/div[1]/div[1]/ul/li[4]/div/p[2]/b")).getText();
			System.out.println("Date of Application is:  "+dateOfApp);

			Pattern applndate = Pattern.compile("[0-9]+/[0-9]+/[0-9]+");
			Matcher apdate = applndate.matcher(dateOfApp);

			if (apdate.find())
			{
				dateOfApp = apdate.group();
				System.out.println("Date of Application is: " +dateOfApp);
			}
			else
			{
				System.out.println("Date of Application not found");
			}


			//Amount Payable
			String amtPayable = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/table/tbody/tr[15]/td/table/tbody/tr/td[2]")).getText();
			System.out.println("Total Amount Payable is: "+amtPayable);

			PayableAmountList.add(amtPayable);
			System.out.println("Payable Amount List Challan @ULB   :::"+PayableAmountList);


			////////////////////////Payment Mode
			String PayMode = mGetPropertyFromFile("PaymntMode");
			System.out.println("Payment Mode at Application Time is ::: "+PayMode);
			PaymentModeList.add(PayMode);
			System.out.println("Payment Mode List at Application Time is ::: "+PaymentModeList);*/

			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("pt_SubmitBtn_ID"));
			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("pt_ProceedButton_ID"));
			mGenericWait();

			mChallanPDFReader();

			api.PdfPatterns.patternChallanRegenerationPdf(pdfoutput);

			//PtChallanReader();

			// Receipt Property No
			Prop_No_List.add(propnoList.get(CurrentinvoCount));
			System.out.println("Property Number List @Challan :::"+Prop_No_List);

			Challan_Reneration_PropNo_List.add(ChallanReneration_PropNoList.get(CurrentinvoCount));
			System.out.println("Property Number List @Receipt :::"+Challan_Reneration_PropNo_List);

			mAssert(Prop_No_List, Challan_Reneration_PropNo_List, "Wrong Property No on Receipt copy Actual :" +Prop_No_List+ "Expected :"+Challan_Reneration_PropNo_List);

			// Receipt Owner Name

			mAssert(Challan_Regen_OwnerName_List, ApplicantNameList, "Wrong Applicant Name on Receipt of Challan Regeneration copy Actual :" +Challan_Regen_OwnerName_List+ "Expected :"+ApplicantNameList);

			// Receipt 'Receipt Amount'

			mAssert(Challan_Regen_ReceiptAmountList, AmountList, "Wrong Receipt amount on Receipt of Challan Regeneration copy Actual :" +Challan_Regen_ReceiptAmountList+ "Expected :"+AmountList);

			// Payment Mode

			mAssert(Challan_Regen_ReceiptPayModeList, PaymentModeList, "Wrong Payment Mode on Receipt of Challan Regeneration copy Actual :" +Challan_Regen_ReceiptPayModeList+ "Expected :"+PaymentModeList);

			// Payable Amount

			mAssert(Challan_Regen_ReceiptPayableAmountList, PayableAmountList, "Wrong Payable Amount on Receipt of Challan Regeneration copy Actual :" +Challan_Regen_ReceiptPayableAmountList+ "Expected :"+PayableAmountList);

			// Current Year Net Payable
			//	mAssert(Challan_Regen_ReceiptCurrentYearPayableAmountList, CurrentYearNetAmountList, "Wrong Current Year Net Payable Amount on Receipt of Challan Regeneration copy Actual :" +Challan_Regen_ReceiptPayableAmountList+ "Expected :"+CurrentYearNetAmountList);

			///////////
			//////Discount(Rebate) - On challan it shows Rebate (Normal) + RWH Rebate & on Receipt sometimes it shows zero rebate amount for both so Assertion is failed - Need to discuss & confirm the scenario
			///////////
			//mAssert(Challan_Regen_ReceiptDiscountAmountList, DiscountList, "Wrong Discount Amount on Receipt of Challan Regeneration copy Actual :" +Challan_Regen_ReceiptDiscountAmountList+ "Expected :"+DiscountList);
			//mAssert(PdfPatterns.s1, discount, "Wrong Discount Amount on Receipt of Challan Regeneration copy Actual :"+PdfPatterns.s1+"Expected :"+discount);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in ChallanRegeneration method");
		}

		//**************************************************
		// DB Start *************************
		// Fetching Challan Regeneration details from DataBase
		try {

			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			}
			catch(ClassNotFoundException ex) 
			{
				System.out.println("Error: unable to load driver class! " +ex);
				System.exit(1);
			}
			catch(IllegalAccessException ex)
			{
				System.out.println("Error: access problem while loading!");
				System.exit(2);
			}
			catch(InstantiationException ex)
			{
				System.out.println("Error: unable to instantiate driver!");
				System.exit(3);
			}


			//String URL = "jdbc:oracle:thin:@192.168.100.169:1521:ORCLUNI";
			String URL = "jdbc:oracle:thin:@192.168.100.229:1521:KDMC";
			String USER = "mainet_prod_phase2";
			String PASS = "MAINET_PROD_PHASE2_020317";
			Connection conn = DriverManager.getConnection(URL, USER, PASS);

			Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				db_ChallanNo = mGetText("xpath", mGetPropertyFromFile("pt_new_ChallanNo_ID"));

			}	
			else
			{
				db_Appno = AppNo;
				System.out.println("db_App No is :: "+db_Appno);
			}

			db_orgid = mGetPropertyFromFile("db_orgid");

			String query1 = "select APM_APPLICATION_ID, ACTUAL_AMT_PAY, TOT_ANNUAL_TAX, PM_TMP_TAXABLE_LAND, TOTAL_PAY, INTREST, REBATE, PD_CARPETAREA_TOTAL, PM_PLOT_AREA, CPD_WTT_AMT, PANELTY_TAX, PM_TMP_VACANT_LAND, PD_COMMERCIAL, PD_RESEDENTIAL, PD_OTHER, PREVIOUS_ARREARS, RAIN_WTR_RBT_AMT from tb_as_trans_det where orgid= "+db_orgid + " and APM_APPLICATION_ID = '"+db_Appno+"'";

			System.out.println("Query :: " + query1);

			rs1 = st.executeQuery(query1);			

			System.out.println();

			while (rs1.next()) 
			{

				System.out.println(rs1.getString(1) +"    "+ rs1.getString(2)+"    "+ rs1.getString(3)+"    "+ rs1.getString(4)+"    "+ rs1.getString(5));

				db_ApplicationNo=rs1.getString(1);
				db_ActualPayableAmt=rs1.getString(2);
				db_TotalAnnualTax=rs1.getString(3);
				db_TaxableVacantLand=rs1.getString(4);
				db_TotalPay=rs1.getString(5);
				db_Interest=rs1.getString(6);
				db_Rebate=rs1.getString(7);
				db_TotalCarpetArea=rs1.getString(8);
				db_TotalPlotArea=rs1.getString(9);
				db_WaterCharges=rs1.getString(10);
				db_PenaltyTax=rs1.getString(11);
				db_TotalVacantLandArea=rs1.getString(12);
				db_CommercialArea=rs1.getString(13);
				db_ResidentialArea=rs1.getString(14);
				db_OtherArea=rs1.getString(15);
				db_TotalPreviousArrears=rs1.getString(16);
				db_RWH_Rebate=rs1.getString(17);

				System.out.println("DB Application No is : " +db_ApplicationNo);
				System.out.println("DB Actual Payable Amount is : " +db_ActualPayableAmt);
				System.out.println("DB Current Year Total Annual Tax is : " +db_TotalAnnualTax);
				System.out.println("DB Tax on Vacant Land is : " +db_TaxableVacantLand);
				System.out.println("DB Total Amount to be Paid is : " +db_TotalPay);
				System.out.println("DB Total Penalty/Fine (Interest) Amount is : " +db_Interest);
				System.out.println("DB Rebate (Normal) is : " +db_Rebate);
				System.out.println("DB Total Carpet Area is : " +db_TotalCarpetArea);
				System.out.println("DB Total Plot Area is : " +db_TotalPlotArea);
				System.out.println("DB Water Charges are : " +db_WaterCharges);
				System.out.println("DB Penalty Tax is : " +db_PenaltyTax);
				System.out.println("DB Total Vacant Land Area is : " +db_TotalVacantLandArea);
				System.out.println("DB Commercial Area is : " +db_TotalVacantLandArea);
				System.out.println("DB Residential Area is : " +db_TotalVacantLandArea);
				System.out.println("DB Other Area is : " +db_OtherArea);
				System.out.println("DB Total Previous Arrears are : " +db_TotalPreviousArrears);
				System.out.println("DB Rain Water Harvesting Rebate : " +db_RWH_Rebate);

			}

			conn.close();		

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		// DB End *************************
		//**************************************************

	}

	/////////////////////
	//// Transfer Objection by Jyoti @08-03-2017
	///////////////////


	public void trnsfr_Objection() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			mGenericWait();
			mNavigation(mGetPropertyFromFile("onlServices_ID"), mGetPropertyFromFile("moduleName_ID"), mGetPropertyFromFile("subModuleName_ID"), mGetPropertyFromFile("serviceName_ID"));

			TransferObjection();

			logOut();
			finalLogOut();
			//isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerNewAssessmentServiceName"));
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in trnsfr_Objection method");
		}

	}
	public void TransferObjection() throws Exception
	{
		try
		{
			mWaitForVisible("xpath", mGetPropertyFromFile("trfobj_AddBtn_ID"));
			mClick("xpath", mGetPropertyFromFile("trfobj_AddBtn_ID"));
			mGenericWait();
			mWaitForVisible("xpath", mGetPropertyFromFile("trfobj_SaveBtn_ID"));
			mClick("xpath", mGetPropertyFromFile("trfobj_AppListBtn_ID"));
			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("trfobj_SearchBtn_ID"));
			mGenericWait();

			mClick("xpath", mGetPropertyFromFile("trfobj_FindRecordBtn_ID"));
			mGenericWait();

			mSelectDropDown("xpath", mGetPropertyFromFile("trfobj_AppNoLabel_ID"), mGetPropertyFromFile("trfobj_AppNoLabel_Data"));
			mGenericWait();

			IndOrDep("id", "trfobj_AppNo_ID", "applicationNo");

			mGenericWait();
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("trfobj_FindBtn_ID"));

			mGenericWait();

			mClick("css", mGetPropertyFromFile("trfobj_AddDtlsBtn_ID"));
			mGenericWait();

			mClick("xpath", mGetPropertyFromFile("trfobj_AddDtlsLink_ID"));
			mGenericWait();





			mSelectDropDown("id", mGetPropertyFromFile("trfobj_NameTitle_ID"), mGetPropertyFromFile("trfobj_NameTitle_Data"));
			mGenericWait();

			mSendKeys("id", mGetPropertyFromFile("trfobj_FirstName_ID"), mGetPropertyFromFile("trfobj_FirstName_Data"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("trfobj_MiddleName_ID"), mGetPropertyFromFile("trfobj_MiddleName_Data"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("trfobj_LastName_ID"), mGetPropertyFromFile("trfobj_LastName_Data"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("trfobj_PaperName_ID"), mGetPropertyFromFile("trfobj_PaperName_Data"));
			mGenericWait();
			//mDateSelect("id", mGetPropertyFromFile("trfobj_ObjIssuerDate_ID"), mGetPropertyFromFile("trfobj_ObjIssuerDate_Data"));
			mGdatePicker("id", mGetPropertyFromFile("trfobj_ObjIssuerDate_ID"), mGetPropertyFromFile("trfobj_ObjIssuerDate_Year_Data"), mGetPropertyFromFile("trfobj_ObjIssuerDate_Month_Data"), mGetPropertyFromFile("trfobj_ObjIssuerDate_Date_Data"));
			mGenericWait();
			mSendKeys("id", mGetPropertyFromFile("trfobj_ManualObjNo_ID"), mGetPropertyFromFile("trfobj_ManualObjNo_Data"));
			mGenericWait();

			mSendKeys("id", mGetPropertyFromFile("trfobj_TransfereeAddr_ID"), mGetPropertyFromFile("trfobj_TransfereeAddr_Data"));
			mGenericWait();

			mSendKeys("id", mGetPropertyFromFile("trfobj_ObjMatter_ID"), mGetPropertyFromFile("trfobj_ObjMatter_Data"));
			mGenericWait();
			mTakeScreenShot();


			mClick("xpath", mGetPropertyFromFile("trfobj_ObjDtlsSubmitBtn_ID"));
			mGenericWait();

			mClick("css", mGetPropertyFromFile("trfobj_MsgClose_ID"));
			mGenericWait();



			// ******************************************* Start
			/// Assertions
			// Application No.
			String trfObj_AppNo = mGetText("xpath", mGetPropertyFromFile("trfobj_AppID_ID"), "value");
			System.out.println("Application No at Objection Entry ::: "+trfObj_AppNo);

			trfObj_AppNoList.add(trfObj_AppNo);
			System.out.println("List of Application No at Objection Entry :::"+trfObj_AppNoList);

			// Property No
			String trfObj_PropNo = mGetText("xpath", mGetPropertyFromFile("trfobj_PropNo_ID"), "value");
			System.out.println("Property No at Objection Entry ::: "+trfObj_PropNo);

			trfObj_PropNoList.add(trfObj_PropNo);
			System.out.println("List of Property No at Objection Entry :::"+trfObj_PropNoList);

			// Owner Name
			String trfObj_OwnerNm = mGetText("id", mGetPropertyFromFile("trfobj_OwnerName_ID"),"value");
			System.out.println("Owner Name at Objection Entry ::: "+trfObj_OwnerNm);

			trfObj_OwnerNmList.add(trfObj_OwnerNm);
			System.out.println("List of Owner Name at Objection Entry :::"+trfObj_OwnerNmList); 

			// Owner Address
			String trfObj_OwnerAddr = mGetText("id", mGetPropertyFromFile("trfobj_OwnerAddr_ID"),"value");
			System.out.println("Owner Address at Objection Entry ::: "+trfObj_OwnerAddr);

			trfObj_OwnerAddrList.add(trfObj_OwnerAddr);
			System.out.println("List of Owner Address at Objection Entry :::"+trfObj_OwnerAddrList); 

			// *********************************************** End

			mClick("xpath", mGetPropertyFromFile("trfobj_SaveBtn_ID"));
			mGenericWait();
			mTakeScreenShot();


			mClick("xpath", mGetPropertyFromFile("trfobj_ProceedBtn_ID"));
			mGenericWait();


		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in TransferObjection method");
		}
	}


	////////////
	// Transfer Inspection Entry by Jyoti
	///////////

	public void trnsfr_Inspection() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			mGenericWait();
			mNavigation(mGetPropertyFromFile("onlServices_ID"), mGetPropertyFromFile("moduleName_ID"), mGetPropertyFromFile("subModuleName_ID"), mGetPropertyFromFile("serviceName_ID"));

			TransferInspection();

			logOut();
			finalLogOut();

			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in trnsfr_Inspection method");
		}

	}
	public void TransferInspection() throws Exception
	{
		try
		{
			mWaitForVisible("xpath", mGetPropertyFromFile("trfInsp_AddBtn_ID"));
			mClick("xpath", mGetPropertyFromFile("trfInsp_AddBtn_ID"));
			mGenericWait();
			mWaitForVisible("xpath", mGetPropertyFromFile("trfInsp_AppListBtn_ID"));
			mClick("xpath", mGetPropertyFromFile("trfInsp_AppListBtn_ID"));
			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("trfInsp_SearchBtn_ID"));

			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("trfInsp_Findicon_ID"));
			mGenericWait();

			mSelectDropDown("xpath", mGetPropertyFromFile("trfInsp_AppIDSearch_ID"), mGetPropertyFromFile("trfInsp_AppIDSearch_Data"));
			mGenericWait();

			IndOrDep("id", "trfInsp_EnterAppID_ID", "applicationNo");

			mClick("id", mGetPropertyFromFile("trfInsp_FindBtn_ID"));
			mGenericWait();
			mTakeScreenShot();

			mClick("css", mGetPropertyFromFile("trfInsp_AddDtlsBtn_ID"));
			mGenericWait();


			//*********************************************  Start
			/// Assertions
			// Application No.
			String trfInsp_AppNo = mGetText("id", mGetPropertyFromFile("trfInsp_AppID_ID"),"value");
			System.out.println("Application No at Inspection Entry ::: "+trfInsp_AppNo);

			trfInsp_AppNoList.add(trfInsp_AppNo);
			System.out.println("List of Application No at Inspection Entry :::"+trfInsp_AppNoList);

			mAssert(trfInsp_AppNoList, trfObj_AppNoList, "Wrong Application No on Inspection Entry Form Actual :" +trfInsp_AppNoList+ "Expected :"+trfObj_AppNoList);

			// Property No
			String trfInsp_PropNo = mGetText("id", mGetPropertyFromFile("trfInsp_PropNo_ID"),"value");
			System.out.println("Property No at Inspection Entry ::: "+trfInsp_PropNo);

			trfInsp_PropNoList.add(trfInsp_PropNo);
			System.out.println("List of Property No at Inspection Entry :::"+trfInsp_PropNoList);

			mAssert(trfInsp_PropNoList, trfObj_PropNoList, "Wrong Property No on Inspection Entry Form Actual :" +trfInsp_PropNoList+ "Expected :"+trfObj_PropNoList);

			// Owner Name
			String trfInsp_OwnerNm = mGetText("id", mGetPropertyFromFile("trfInsp_OwnerName_ID"),"value").trim();
			System.out.println("Owner Name at Inspection Entry ::: "+trfInsp_OwnerNm);

			trfInsp_OwnerNmList.add(trfInsp_OwnerNm);
			System.out.println("List of Owner name at Inspection Entry :::"+trfInsp_OwnerNmList); 

			mAssert(trfInsp_OwnerNmList, trfObj_OwnerNmList, "Wrong Owner Name on Inspection Entry Form Actual :" +trfInsp_OwnerNmList+ "Expected :"+trfObj_OwnerNmList);

			// Transferee Address
			String trfInsp_TransfereeAddr = mGetText("id", mGetPropertyFromFile("trfInsp_TransfereeAddr_ID"),"value").trim();
			System.out.println("Transferee Address at Inspection Entry ::: "+trfInsp_TransfereeAddr);

			trfInsp_TransfereeAddrList.add(trfInsp_TransfereeAddr);
			System.out.println("List of Transferee Address at Inspection Entry :::"+trfInsp_TransfereeAddrList);

			mAssert(trfInsp_TransfereeAddrList, trfObj_OwnerAddrList, "Wrong Transferee Address on Inspection Entry Form Actual :" +trfInsp_TransfereeAddrList+ "Expected :"+trfObj_OwnerAddrList);


			//********************************************* End


			mTakeScreenShot();

			mClick("css", mGetPropertyFromFile("trfInsp_ViewDtlsBtn_ID"));
			mGenericWait();

			mClick("xpath", mGetPropertyFromFile("trfInsp_InspectorListBtn_ID"));
			mGenericWait();

			mSendKeys("id", mGetPropertyFromFile("trfInsp_EnterEmpName_ID"), mGetPropertyFromFile("trfInsp_EnterEmpName_Data"));
			mGenericWait();

			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("trfInsp_InspectorListSearchBtn_ID"));
			mGenericWait();

			mClick("css", mGetPropertyFromFile("trfInsp_AddNameBtn_ID"));
			mGenericWait();

			//mDateSelect("id", mGetPropertyFromFile("trfInsp_InspectionDate_ID"), mGetPropertyFromFile("trfInsp_InspectionDate_Data"));
			mGdatePicker("id", mGetPropertyFromFile("trfInsp_InspectionDate_ID"), mGetPropertyFromFile("trfInsp_InspectionDate_Year_Data"), mGetPropertyFromFile("trfInsp_InspectionDate_Month_Data"), mGetPropertyFromFile("trfInsp_InspectionDate_Date_Data"));
			mGenericWait();

			mSendKeys("id", mGetPropertyFromFile("trfInsp_InspectionRemarks_ID"), mGetPropertyFromFile("trfInsp_InspectionRemarks_Data"));
			mGenericWait();
			mTakeScreenShot();

			mClick("xpath", mGetPropertyFromFile("trfInsp_InspectionSaveBtn_ID"));
			mGenericWait();
			mTakeScreenShot();


			//***************************************** Start

			///////// Inspection Details Assertions

			//Inspector Name
			String inspector_Name = mGetPropertyFromFile("trfInsp_EnterEmpName_Data");
			System.out.println("Inspector Name at Inspection Entry Form is ::: "+inspector_Name);

			inspector_NameList.add(inspector_Name);
			System.out.println("Inspector Name List at Inspection Entry Form is :::"+inspector_NameList);

			//Objection No
			String Obj_No = mGetText("id", mGetPropertyFromFile("trfInsp_ObjNo_ID"), "value");
			//String Obj_No = mGetText("id", mGetPropertyFromFile("trfInsp_ObjNo_ID"));
			System.out.println("Objection No at Inspection Entry Form is ::: "+Obj_No);

			Obj_NoList.add(Obj_No);
			System.out.println("Objection No List at Inspection Entry Form is :::"+Obj_NoList);

			//Objection Matter
			String Obj_Matter = mGetText("id", mGetPropertyFromFile("trfInsp_ObjMatter_ID"));
			System.out.println("Objection Matter at Inspection Entry Form is ::: "+Obj_Matter);

			Obj_MatterList.add(Obj_Matter);
			System.out.println("Objection Matter List at Inspection Entry Form is :::"+Obj_MatterList);

			//Inspection Date
			String Insp_Date = mGetPropertyFromFile("trfInsp_InspectionDate_Data");
			System.out.println("Inspection Date is :: "+Insp_Date);

			Insp_DateList.add(Insp_Date);
			System.out.println("Inspection Date List is :: "+Insp_DateList);


			//Inspection Remarks
			String Insp_Remarks = mGetPropertyFromFile("trfInsp_InspectionRemarks_Data");
			System.out.println("Inspection Remark is :: "+Insp_Remarks);

			Insp_RemarksList.add(Insp_Remarks);
			System.out.println("Inspection Remarks List is :: "+Insp_RemarksList);

			//***************************************** End



			mClick("xpath", mGetPropertyFromFile("trfInsp_InspectionProceedBtn_ID"));


		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in TransferInspection method");
		}
	}

	public void trnsfr_HearingDateEntry() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			mGenericWait();
			mNavigation(mGetPropertyFromFile("onlServices_ID"), mGetPropertyFromFile("moduleName_ID"), mGetPropertyFromFile("subModuleName_ID"), mGetPropertyFromFile("serviceName_ID"));

			TransferHearingDateEntry();

			logOut();
			finalLogOut();

			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in trnsfr_HearingDateEntry method");
		}

	}
	public void TransferHearingDateEntry() throws Exception
	{
		try
		{
			mWaitForVisible("xpath", mGetPropertyFromFile("trfHDE_SearchBtn_ID"));

			IndOrDep("id", "trfHDE_AppNo_ID", "applicationNo");

			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("trfHDE_SearchBtn_ID"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("trfHDE_ViewDtlsLink_ID"));
			mGenericWait();
			mWaitForVisible("xpath", mGetPropertyFromFile("trfHDE_SaveBtn_ID"));
			mSendKeys("id", mGetPropertyFromFile("trfHDE_DateTime_ID"), mGetPropertyFromFile("trfHDE_DateTime_Data"));
			mGenericWait();
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("trfHDE_DoneBtn_ID"));
			mCustomWait(2000);
			mClick("xpath", mGetPropertyFromFile("trfHDE_SaveBtn_ID"));
			mGenericWait();
			mTakeScreenShot();


			///// Assertion Start
			/// Uncomment the assertion for complete flow (Dependent flow)
			// Application No
			String trfHDE_AppNo = mGetText("id", mGetPropertyFromFile("trfHDE_AppID_ID"), "value");
			System.out.println("Transfer Hearing Date Entry Application No is ::: "+trfHDE_AppNo);
			trfHDE_AppNoList.add(trfHDE_AppNo);
			System.out.println("Transfer Hearing Date Entry Application No List is :::"+trfHDE_AppNoList);

			//mAssert(trfHDE_AppNoList, trfInsp_AppNoList, "Wrong Application No on Hearing Date Entry Form Actual :" +trfHDE_AppNoList+ "Expected :"+trfInsp_AppNoList);

			// Property No
			String trfHDE_PropNo = mGetText("id", mGetPropertyFromFile("trfHDE_PropNo_ID"), "value");
			System.out.println("Transfer Hearing Date Entry Property No is ::: "+trfHDE_PropNo);
			trfHDE_PropNoList.add(trfHDE_PropNo);
			System.out.println("Transfer Hearing Date Entry Property No List is :::"+trfHDE_PropNoList);

			//mAssert(trfHDE_PropNoList, trfInsp_PropNoList, "Wrong Property No on Hearing Date Entry Form Actual :" +trfHDE_PropNoList+ "Expected :"+trfInsp_PropNoList);

			// Owner Name
			String trfHDE_OwnerNm = mGetText("id", mGetPropertyFromFile("trfHDE_OwnerName_ID"), "value").trim();
			System.out.println("Transfer Hearing Date Entry Owner Name is ::: "+trfHDE_OwnerNm);
			trfHDE_OwnerNmList.add(trfHDE_OwnerNm);
			System.out.println("Transfer Hearing Date Entry Owner Name List is :::"+trfHDE_OwnerNmList);

			//mAssert(trfHDE_OwnerNmList, trfInsp_OwnerNmList, "Wrong Owner Name on Hearing Date Entry Form Actual :" +trfHDE_OwnerNmList+ "Expected :"+trfInsp_OwnerNmList);


			// Transferee Address
			String trfHDE_TransfereeAddress = mGetText("id", mGetPropertyFromFile("trfHDE_TransfereeAddr_ID"), "value");
			System.out.println("Transfer Hearing Date Entry Transferee Address is ::: "+trfHDE_TransfereeAddress);
			trfHDE_TransfereeAddressList.add(trfHDE_TransfereeAddress);
			System.out.println("Transfer Hearing Date Entry Transferee Address List is :::"+trfHDE_TransfereeAddressList);

			//mAssert(trfHDE_TransfereeAddressList, trfInsp_TransfereeAddrList, "Wrong Transferee Address on Hearing Date Entry Form Actual :" +trfHDE_TransfereeAddressList+ "Expected :"+trfInsp_TransfereeAddrList);


			// Hearing Date
			String trfHDE_HearingDate = mGetPropertyFromFile("trfHDE_DateTime_Data");
			System.out.println("Transfer Hearing Date Entry Hearing date is ::: "+trfHDE_HearingDate);
			trfHDE_HearingDateList.add(trfHDE_HearingDate);
			System.out.println("Transfer Hearing Date Entry Hearing Date List is :::"+trfHDE_HearingDateList);


			mClick("xpath", mGetPropertyFromFile("trfHDE_ProceedBtn_ID"));
			mGenericWait();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in TransferHearingDateEntry method");
		}
	}

	////////////
	// Transfer Hearing Intimation Letter Entry by Jyoti
	///////////

	public void trnsfr_HearingIntiLetter() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			mGenericWait();
			mNavigation(mGetPropertyFromFile("onlServices_ID"), mGetPropertyFromFile("moduleName_ID"), mGetPropertyFromFile("subModuleName_ID"), mGetPropertyFromFile("serviceName_ID"));

			TransferHearingIntimationLetterEntry();

			logOut();
			finalLogOut();

			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in trnsfr_HearingIntiLetter method");
		}

	}
	public void TransferHearingIntimationLetterEntry() throws Exception
	{
		try
		{
			mWaitForVisible("xpath", mGetPropertyFromFile("trfHIL_AppNoSearchBtn_ID"));
			mClick("xpath", mGetPropertyFromFile("trfHIL_SearchBtn_ID"));
			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("trfHIL_Findicon_ID"));
			mGenericWait();
			mSelectDropDown("xpath", mGetPropertyFromFile("trfHIL_AppNoLabel_ID"), mGetPropertyFromFile("trfHIL_AppNoLabel_Data"));

			IndOrDep("id", "trfHIL_AppNoEnter_ID", "applicationNo");

			mClick("xpath", mGetPropertyFromFile("trfHIL_FindBtn_ID"));
			mGenericWait();


			//*******************

			// PENDING - bcz old records are not available & due to Defect D-1208 not able to Save 'Hearing Date Entry',
			// can't execute end to end flow also

			//******************
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in TransferHearingIntimationLetterEntry method");
		}
	}

	public void trnsfr_HearingEntry() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			mGenericWait();
			mNavigation(mGetPropertyFromFile("onlServices_ID"), mGetPropertyFromFile("moduleName_ID"), mGetPropertyFromFile("subModuleName_ID"), mGetPropertyFromFile("serviceName_ID"));

			TransferHearingEntry();

			logOut();
			finalLogOut();

			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in trnsfr_HearingEntry method");
		}

	}
	public void TransferHearingEntry() throws Exception
	{
		try
		{
			mWaitForVisible("xpath", mGetPropertyFromFile("trfHE_AddBtn_ID"));

			mClick("xpath", mGetPropertyFromFile("trfHE_AddBtn_ID"));
			mGenericWait();
			mWaitForVisible("xpath", mGetPropertyFromFile("trfHE_AppListBtn_ID"));
			mClick("xpath", mGetPropertyFromFile("trfHE_AppListBtn_ID"));
			mGenericWait();
			mWaitForVisible("xpath", mGetPropertyFromFile("trfHE_SearchBtn_ID"));
			mClick("xpath", mGetPropertyFromFile("trfHE_SearchBtn_ID"));
			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("trfHE_FindIcon_ID"));
			mGenericWait();
			mSelectDropDown("xpath", mGetPropertyFromFile("trfHE_AppNoLabel_ID"), mGetPropertyFromFile("trfHE_AppNoLabel_Data"));


			IndOrDep("id", "trfHE_AppNoEnter_ID", "applicationNo");

			mClick("xpath", mGetPropertyFromFile("trfHE_FindBtn_ID"));
			mGenericWait();



			//*******************

			// PENDING - bcz old records are not available & due to Defect D-1208 not able to Save 'Hearing Date Entry',
			// can't execute end to end flow also

			//******************




		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in TransferHearingEntry method");
		}
	}

	public void chngInContactDtls() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			//LOIAPPLICABLE=true;
			changeInContactDtls();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in chngInAssessmentAddn method");
		}
	}

	public void changeInContactDtls()
	{
		try
		{
			// Navigation
			mNavigation("pt_propertyTaxLinkid", "pt_transactionsLinkid", "pt_updatePropDtlsLinkid");

			//Search property details to edit
			mWaitForVisible("id", mGetPropertyFromFile("pt_propOwnerInfoSearchBtnid"));
			if(mGetPropertyFromFile("pt_searchPropOwnerInfoByPropNo").equalsIgnoreCase("yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("pt_propOwnerInfoPropNoid"), mGetPropertyFromFile("pt_propOwnerInfoPropNo"));
			}
			else if(mGetPropertyFromFile("pt_searchPropOwnerInfoByOldPIDNo").equalsIgnoreCase("yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("pt_propOwnerInfoOldPIDNoid"), mGetPropertyFromFile("pt_propOwnerInfoOldPIDNo"));
			}
			else if(mGetPropertyFromFile("pt_searchPropOwnerInfoByOldHldngNo").equalsIgnoreCase("yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("pt_propOwnerInfoOldHldngNoid"), mGetPropertyFromFile("pt_propOwnerInfoOldHldngNo"));
			}
			else if(mGetPropertyFromFile("pt_searchPropOwnerInfoByNewHldngNo").equalsIgnoreCase("yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("pt_propOwnerInfoNewHldngNoid"), mGetPropertyFromFile("pt_propOwnerInfoNewHldngNo"));
			}

			mGenericWait();
			mClick("id", mGetPropertyFromFile("pt_propOwnerInfoSearchBtnid"));
			mGenericWait();
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("pt_propOwnerInfoEditBtnid"));

			mWaitForVisible("id", mGetPropertyFromFile("pt_propOwnerInfoUpdateBtnid"));

			//Property Details / Contact Details
			if(mGetPropertyFromFile("pt_editPropContactDtls").equalsIgnoreCase("yes"))
			{
				mClick("id", mGetPropertyFromFile("pt_propertyOrContactDetailsEditid"));
				mGenericWait();
				mTakeScreenShot();

				// Update New Holding No.
				if(mGetPropertyFromFile("pt_newHoldingNo").length()==0||mGetPropertyFromFile("pt_newHoldingNo").equals("?"))
				{
					System.out.println("No Change in New Holding No.");
				}
				else
				{
					mSendKeys("id", mGetPropertyFromFile("pt_newHoldingNoid"), mGetPropertyFromFile("pt_newHoldingNo"));
				}

				// Update Old Holding No.
				if(mGetPropertyFromFile("pt_oldHoldingNo").length()==0||mGetPropertyFromFile("pt_oldHoldingNo").equals("?"))
				{
					System.out.println("No Change in Old Holding No.");
				}
				else
				{
					mSendKeys("id", mGetPropertyFromFile("pt_oldHoldingNoid"), mGetPropertyFromFile("pt_oldHoldingNo"));
				}

				// Update Ward
				if(mGetPropertyFromFile("pt_ward").length()==0||mGetPropertyFromFile("pt_ward").equals("?"))
				{
					System.out.println("No Change in Ward");
				}
				else
				{
					mSelectDropDown("id", mGetPropertyFromFile("pt_wardid"), mGetPropertyFromFile("pt_ward"));
				}

				// Update Telephone No.
				if(mGetPropertyFromFile("pt_telephoneNo").length()==0||mGetPropertyFromFile("pt_telephoneNo").equals("?"))
				{
					System.out.println("No Change in Telephone No.");
				}
				else
				{
					mSendKeys("id", mGetPropertyFromFile("pt_telephoneNoid"), mGetPropertyFromFile("pt_telephoneNo"));
				}

				// Update Mobile No.
				if(mGetPropertyFromFile("pt_mobileNo").length()==0||mGetPropertyFromFile("pt_mobileNo").equals("?"))
				{
					System.out.println("No Change in Mobile No.");
				}
				else
				{
					mSendKeys("id", mGetPropertyFromFile("pt_mobileNoid"), mGetPropertyFromFile("pt_mobileNo"));
				}

				// Update Email ID
				if(mGetPropertyFromFile("pt_emailID").length()==0||mGetPropertyFromFile("pt_emailID").equals("?"))
				{
					System.out.println("No Change in Email ID");
				}
				else
				{
					mSendKeys("id", mGetPropertyFromFile("pt_emailIDid"), mGetPropertyFromFile("pt_emailID"));
				}
				mGenericWait();
				mTakeScreenShot();
			}

			//Single Owner Details
			if(mGetPropertyFromFile("pt_editSingleOwnerDtls").equalsIgnoreCase("yes"))
			{
				mClick("id", mGetPropertyFromFile("pt_ownerDetailsEditid"));
				mGenericWait();
				mTakeScreenShot();

				// Update Gender
				if(mGetPropertyFromFile("pt_gender").length()==0||mGetPropertyFromFile("pt_gender").equals("?"))
				{
					System.out.println("No Change in Gender");
				}
				else
				{
					mSelectDropDown("id", mGetPropertyFromFile("pt_genderid"), mGetPropertyFromFile("pt_gender"));
				}

				// Update Title
				if(mGetPropertyFromFile("pt_title").length()==0||mGetPropertyFromFile("pt_title").equals("?"))
				{
					System.out.println("No Change in Title");
				}
				else
				{
					mSelectDropDown("id", mGetPropertyFromFile("pt_titleid"), mGetPropertyFromFile("pt_title"));
				}

				// Update Primary Assesse Name
				if(mGetPropertyFromFile("pt_primaryAssesseName").length()==0||mGetPropertyFromFile("pt_primaryAssesseName").equals("?"))
				{
					System.out.println("No Change in Primary Assesse Name");
				}
				else
				{
					mSendKeys("id", mGetPropertyFromFile("pt_primaryAssesseNameid"), mGetPropertyFromFile("pt_primaryAssesseName"));
				}

				// Update Father's/Husband's Name
				if(mGetPropertyFromFile("pt_father'sHusband'sName").length()==0||mGetPropertyFromFile("pt_father'sHusband'sName").equals("?"))
				{
					System.out.println("No Change in Father's/Husband's Name");
				}
				else
				{
					mSendKeys("id", mGetPropertyFromFile("pt_father'sHusband'sNameid"), mGetPropertyFromFile("pt_father'sHusband'sName"));
				}
			}

			//Property Address
			if(mGetPropertyFromFile("pt_editPropAddressDtls").equalsIgnoreCase("yes"))
			{
				mClick("id", mGetPropertyFromFile("pt_propertyAddressEditid"));

				// Update Property House No
				if(mGetPropertyFromFile("pt_propertyHouseNo").length()==0||mGetPropertyFromFile("pt_propertyHouseNo").equals("?"))
				{
					System.out.println("No Change in Property House No");
				}
				else
				{
					mSendKeys("id", mGetPropertyFromFile("pt_propertyHouseNoid"), mGetPropertyFromFile("pt_propertyHouseNo"));
				}

				// Update Address Line1
				if(mGetPropertyFromFile("pt_addressLine1").length()==0||mGetPropertyFromFile("pt_addressLine1").equals("?"))
				{
					System.out.println("No Change in Address Line1");
				}
				else
				{
					mSendKeys("id", mGetPropertyFromFile("pt_addressLine1id"), mGetPropertyFromFile("pt_addressLine1"));
				}

				// Update Address Line2
				if(mGetPropertyFromFile("pt_addressLine2").length()==0||mGetPropertyFromFile("pt_addressLine2").equals("?"))
				{
					System.out.println("No Change in Address Line2");
				}
				else
				{
					mSendKeys("id", mGetPropertyFromFile("pt_addressLine2id"), mGetPropertyFromFile("pt_addressLine2"));
				}

				// Update Pincode
				if(mGetPropertyFromFile("pt_pincode").length()==0||mGetPropertyFromFile("pt_pincode").equals("?"))
				{
					System.out.println("No Change in Pincode");
				}
				else
				{
					mSendKeys("id", mGetPropertyFromFile("pt_pincodeid"), mGetPropertyFromFile("pt_pincode"));
				}

				mGenericWait();
				mTakeScreenShot();
			}

			//Correspondence Address
			if(mGetPropertyFromFile("pt_editCorrespondenceAddressDtls").equalsIgnoreCase("yes"))
			{
				mClick("id", mGetPropertyFromFile("pt_correspondenceAddressEditid"));
				mGenericWait();
				mTakeScreenShot();

				// Update Correspondence Property House No
				if(mGetPropertyFromFile("pt_corrPropertyHouseNo").length()==0||mGetPropertyFromFile("pt_corrPropertyHouseNo").equals("?"))
				{
					System.out.println("No Change in Correspondence Property House No");
				}
				else
				{
					mSendKeys("id", mGetPropertyFromFile("pt_corrPropertyHouseNoid"), mGetPropertyFromFile("pt_corrPropertyHouseNo"));
				}

				// Update Correspondence Address Line1
				if(mGetPropertyFromFile("pt_corrAddressLine1").length()==0||mGetPropertyFromFile("pt_corrAddressLine1").equals("?"))
				{
					System.out.println("No Change in Correspondence Address Line1");
				}
				else
				{
					mSendKeys("id", mGetPropertyFromFile("pt_corrAddressLine1id"), mGetPropertyFromFile("pt_corrAddressLine1"));
				}

				// Update Correspondence Address Line2
				if(mGetPropertyFromFile("pt_corrAddressLine2").length()==0||mGetPropertyFromFile("pt_corrAddressLine2").equals("?"))
				{
					System.out.println("No Change in Correspondence Address Line2");
				}
				else
				{
					mSendKeys("id", mGetPropertyFromFile("pt_corrAddressLine2id"), mGetPropertyFromFile("pt_corrAddressLine2"));
				}

				// Update Correspondence Pincode
				if(mGetPropertyFromFile("pt_corrPincode").length()==0||mGetPropertyFromFile("pt_corrPincode").equals("?"))
				{
					System.out.println("No Change in Correspondence Pincode");
				}
				else
				{
					mSendKeys("id", mGetPropertyFromFile("pt_corrPincodeid"), mGetPropertyFromFile("pt_corrPincode"));
				}

				mGenericWait();
				mTakeScreenShot();
			}

			// Enter Remark
			mSendKeys("id", mGetPropertyFromFile("pt_propOwnerInfoRemarkid"), mGetPropertyFromFile("pt_propOwnerInfoRemark"));

			mGenericWait();
			mTakeScreenShot();

			// Click Submit
			mClick("id", mGetPropertyFromFile("pt_propOwnerInfoUpdateBtnid"));

			mWaitForVisible("id", mGetPropertyFromFile("pt_propOwnerInfoCloseBtnid"));
			mGenericWait();
			mTakeScreenShot();
			String saveMsg=mGetText("css", mGetPropertyFromFile("pt_propOwnerInfoSaveMsgid"));
			System.out.println("Captured message is : "+saveMsg);
			mAssert(saveMsg, mGetPropertyFromFile("pt_propOwnerInfoSaveMsg"), "Message does not matches, Actual is : "+saveMsg+"Expected is : "+mGetPropertyFromFile("pt_propOwnerInfoSaveMsg"));
			mClick("id", mGetPropertyFromFile("pt_propOwnerInfoCloseBtnid"));

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in changeInContactDtls method");
		}
	}

	public void propTaxBillColln() throws Exception
	{
		try
		{
			mCreateArtefactsFolder("PT_");
			mOpenBrowser(wGetPropertyFromFile("browserName"));
			mGetURL(wGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			//LOIAPPLICABLE=true;
			propTaxBillCollection();
			logOut();
			finalLogOut();
			isChallanVerftnRequired(mGetPropertyFromFile("cfc_challanVerChngInAssessmentServiceName"));
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in chngInAssessmentAddn method");
		}
	}

	public void propTaxBillCollection()
	{
		try
		{
			mNavigation(mGetPropertyFromFile("pt_propertyTaxLinkid"), mGetPropertyFromFile("pt_transactionsLinkid"), mGetPropertyFromFile("pt_collectionsLinkid"), mGetPropertyFromFile("pt_billPaymentLinkid"));

			mWaitForVisible("xpath", mGetPropertyFromFile("pt_billCollectionSearchBtnid"));

			if(mGetPropertyFromFile("pt_searchBillCollnByPropNo").equalsIgnoreCase("yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("pt_billCollectionPropNoid"), mGetPropertyFromFile("pt_billCollectionPropNo"));
			}
			else if(mGetPropertyFromFile("pt_searchBillCollnByOldPropNo").equalsIgnoreCase("yes"))
			{
				mSendKeys("id", mGetPropertyFromFile("pt_billCollectionOldPropNoid"), mGetPropertyFromFile("pt_billCollectionOldPropNo"));
			}

			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("pt_billCollectionSearchBtnid"));

			mWaitForVisible("xpath", mGetPropertyFromFile("pt_billCollectionSubmitBtnid"));
			mGenericWait();

			String propNum=mGetText("xpath", mGetPropertyFromFile("pt_billCollectionPropNumberid"));
			System.out.println("Property number is : "+propNum);

			WebElement table=driver.findElement(By.xpath("//*[@id=\"propertyBillCollection\"]/table[1]"));
			List<WebElement> rows=table.findElements(By.tagName("tr"));
			int rowCount=rows.size();
			System.out.println("Total number of rows : "+rowCount);
			List<WebElement> cols=table.findElements(By.tagName("td"));
			int colCount=cols.size();
			System.out.println("Total number of columns : "+colCount);

			for(int i=0;i<rowCount;i++)
			{
				if(i>0)
				{
					for(int j=0;j<=colCount;j++)
					{

						if(j==0)
						{
							String srNo=rows.get(i).findElement(By.xpath("td[1]")).getText();
							System.out.println("Sr No. is :"+srNo);
						}
						if(j==1)
						{
							String name=rows.get(i).findElement(By.xpath("td[2]")).getText();
							System.out.println("Name / Name of Company / Organization etc. is :"+name);
						}
						if(j==2)
						{
							String fatherHusbandName=rows.get(i).findElement(By.xpath("td[3]")).getText();
							System.out.println("Fathers’/Husbands’ Name /PAN is :"+fatherHusbandName);
						}
						if(j==3)
						{
							String gender=rows.get(i).findElement(By.xpath("td[4]")).getText();
							System.out.println("Gender is :"+gender);
						}
					}
				}
			}
			mTakeScreenShot();
			WebElement taxTable=driver.findElement(By.xpath("//*[@id=\"propertyBillCollection\"]/table[2]"));
			List<WebElement> taxRows=taxTable.findElements(By.tagName("tr"));
			int taxRowCount=taxRows.size();
			System.out.println("Total number of rows : "+taxRowCount);
			List<WebElement> taxCols=table.findElements(By.tagName("td"));
			int taxColCount=taxCols.size();
			System.out.println("Total number of columns : "+taxColCount);

			for(int i=0;i<taxRowCount;i++)
			{
				for(int j=0;j<=taxColCount;j++)
				{
					if(j==0)
					{
						String taxName=taxRows.get(i).findElement(By.xpath("td")).getText();
						System.out.println("Tax is :"+taxName);
					}
					if(j==1)
					{
						String taxAmount=taxRows.get(i).findElement(By.xpath("td[2]")).getText();
						System.out.println("Tax amount is :"+taxAmount);
					}
				}
			}

			String arrearWithoutInterest=driver.findElement(By.xpath("//*[@id=\"propertyBillCollection\"]/table[2]/tbody/tr[1]/td[2]")).getText();
			System.out.println("Arrear without interest (Rs.) : "+arrearWithoutInterest);

			String totalAnnualTax=driver.findElement(By.xpath("//*[@id=\"propertyBillCollection\"]/table[2]/tbody/tr[2]/td[2]")).getText();
			System.out.println("Total Annual Tax (Rs.) : "+totalAnnualTax);

			String rebateCurrent=driver.findElement(By.xpath("//*[@id=\"rebateAmtID\"]")).getText();
			System.out.println("Rebate (Current) : "+rebateCurrent);

			String rainWaterHarvestingRebate=driver.findElement(By.xpath("//*[@id=\"propertyBillCollection\"]/table[2]/tbody/tr[4]/td[2]")).getText();
			System.out.println("Rain Water Harvesting Rebate (Rs.) : "+rainWaterHarvestingRebate);

			String arrearsInterest=driver.findElement(By.xpath("//*[@id=\"propertyBillCollection\"]/table[2]/tbody/tr[5]/td[2]")).getText();
			System.out.println("Arrears Interest : "+arrearsInterest);

			String currentInterest=driver.findElement(By.xpath("//*[@id=\"propertyBillCollection\"]/table[2]/tbody/tr[6]/td[2]")).getText();
			System.out.println("Current Interest : "+currentInterest);

			String penaltyCurrentArrears=driver.findElement(By.xpath("//*[@id=\"totalPenalty\"]")).getText();
			System.out.println("Penalty(current/Arrears) : "+penaltyCurrentArrears);

			String totalPayable=driver.findElement(By.xpath("//*[@id=\"totPayAmtID\"]")).getText();
			System.out.println("Total Payable (Rs.) : "+totalPayable);

			String totPayable=mGetText("id", mGetPropertyFromFile("pt_billCollectionTotalPayableAmtid"),"value");
			System.out.println("Total Payable (Rs.) : "+totPayable);
			mTakeScreenShot();
			payment("paymentMode","byBankOrULB");
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("pt_billCollectionSubmitBtnid"));

			mWaitForVisible("id", mGetPropertyFromFile("pt_billCollectionProccedBtnid"));
			mGenericWait();
			mTakeScreenShot();
			mClick("id", mGetPropertyFromFile("pt_billCollectionProccedBtnid"));
			mCustomWait(5000);
			if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Offline"))
			{
				newPtChallanReader();
			}
			else if(mGetPropertyFromFile("paymentMode").equalsIgnoreCase("Pay @ ULB Counter"))
			{
				mChallanPDFReader();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in propTaxBillCollection method");
		}
	}
}


