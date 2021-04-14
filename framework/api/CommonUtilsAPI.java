package api;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ISuite;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.abm.mainet.adh.AdvertisingAndHoardingCustomErrorMessages;
import com.abm.mainet.audit.AUDITCustomErrorMessages;
import com.abm.mainet.bnd.BndCustomErrorMessages;
import com.abm.mainet.eip.EIPAppCustomErrorMessages;
import com.abm.mainet.legal.LegalCustomErrorMessages;
import com.abm.mainet.mkt.MarketCustomErrorMessages;
import com.abm.mainet.pt.PropTaxCustomErrorMessages;
import com.abm.mainet.rnl.RentAndLeaseCustomErrorMessages;
import com.abm.mainet.rti.RTIAppCustomErrorMessages;
import com.abm.mainet.tp.TPCustomErrorMessages;
import com.google.common.base.Throwables;

import errorhandling.MainetCustomExceptions;

/**
 * @since 20.08.2015
 * 
 */

public class CommonUtilsAPI {

	public static Logger log = Logger.getLogger(CommonUtilsAPI.class.getName());

	public static WebDriver driver;
	public static String appNo;
	public static String challannosearch="false";
	public static String rtiapplication="false";
	public static String current;
	public static boolean chllanForRTIServices = false;
	public static boolean chllanForBNDServices = false;
	public static boolean chllanForTpServices = false;
	public static boolean challanForPROPERTYServices = false;
	public static boolean challanForRNLServices = false;
	public static String ULBName;
	public static boolean challanForMARKETServices = false;
	public static String swapchallnno;
	public static boolean chllanVerReqForServices = false;

	public static String tpandrnl="false"; 
	public static String licenseNum ;

	public static String licensenum_newtrade;

	public static String exception;

	static boolean status = true;
	public String date;
	public static long dateIncr;
	URL url;
	BufferedInputStream fileToParse;
	PDFParser parser;
	public static boolean isByPost = false;

	public static boolean isForme = false;

	public static String pdfoutput;

	public String pdfoutput1;
	// no of copy
	public static int noOfCopyForBNDCombo = 0;
	// String appcntName;
	public static boolean isOrganiztn = false;
	public static boolean aplbpl = false;
	public static boolean LOIAPPLICABLE = false;

	public static String moduleClipboardata;
	public static String documentsPath;
	public static String imagepath;
	public static String screenShotsPath;
	public static String finalScrShotPath;
	public static String finalDocPath;

	public static String LOINO;
	public static String applnno;
	public static String modeForChallan;
	public static int tempTransCount = 0;
	public static String licenseNo;
	public static int scrupdatedpayble_list;

	public static String pdfService;

	// Creating variable to get form name
	public static String formName;
	protected boolean bnd_appStatusRejectedDueToInadequateInformationAndDocuments=false;
	public static boolean bnd_docAuthorised=false;
	/*
	 * protected String printedAppNo; protected String applcntName;
	 */

	public static String thisClassName;
	public static String myClassName;
	public static String thisMethodName;
	public static String entcompname;
	public static String RNLContractNo;

	protected String printedAppNo;
	protected String applcntName;



	//PDF related strings
	public static String firstpdf;
	public static String secondpdf;
	ArrayList<String> tabs3;
	Robot robot;
	public static String output;
	public static String name;

	// public static String ApplicantName;

	public static String propNo;
	protected boolean newAssessment=false;

	ArrayList<String> tabs2;

	String moduleTimestamp;

	public static String compApplicantName;

	public static String ApplicantName;

	public static String startTime = mStartExecution();

	public static String startTestTime;
	public static String endTestTime;
	public static String totalExecTime;
	private static Random random = new Random((new Date()).getTime());

	public static String lgl_SuiteNo;

	/* Sequence generator */
	DocumentBuilderFactory docFactory;
	DocumentBuilder docBuilder;
	Document seqDoc;
	public String seqString;
	public int seqNo;
	int nodeCounter;
	String prefixStr;
	String attrName;
	Node sequenceName;
	NamedNodeMap attributes;
	public static String landOrEstateNo;


	//for Excel reading
	static XSSFWorkbook workbook;
	static XSSFSheet sheet = null;
	static XSSFRow row =null;

	static XSSFCell keyCell = null;
	static XSSFCell valueCell = null;
	static String sheetName = null;
	static int NumberOfRows = 0;
	//static int NumberOfColumnss = 0; 
	/////////////////////////////

	

	public static String[] newcheckListVerifn;
	public static ArrayList<Integer> approvechekindexes = new ArrayList<Integer>();
	

	
	
	
	
	
	
	
	/////adh arraylist updated
	
	public static ArrayList<String>Adh_ModeOfPaymentAppTime= new ArrayList<String>();
	
	
	public static ArrayList<String> skipcanssus= new ArrayList<String>(); 
	public static ArrayList<Integer> clearchekindexes= new ArrayList<Integer>();
	
	public static ArrayList<String> chekindexesinspction = new ArrayList<String>();

	public static ArrayList<String> SKIPSTATUS = new ArrayList<String>();
	public static ArrayList<String> adhHearingOrdeNo_UNS = new ArrayList<String>();
	
	
	
	
	
	public static ArrayList<Integer> scr_bookingOfEstate = new ArrayList<Integer>(); 
	
	public static ArrayList<Integer> scr_renewalofrent = new ArrayList<Integer>();
	
	
	
	
	///////////////////////////////////
	//Agency Contract Arraylist
	public static ArrayList<String> Agc_TaxDescription = new ArrayList<String>();
	public static ArrayList<String> Agc_TaxAmount = new ArrayList<String>();
	public static ArrayList<String> AgC_DiscountDescription = new ArrayList<String>();
	public static ArrayList<String> Agc_PaymentSceduleMethod = new ArrayList<String>();
	public static ArrayList<String> Agc_PaymentDueDate = new ArrayList<String>();
	public static ArrayList<String> Agc_PayAmount = new ArrayList<String>();
	public static ArrayList<String> Agc_PaymentScheduleAmount = new ArrayList<String>();
	public static ArrayList<String> Agc_HoardingCode = new ArrayList<String>();
	public static ArrayList<String> Agc_AgencyName = new ArrayList<String>();
	
	public static ArrayList<String> Acg_PayableAmount= new ArrayList<String>();
	


	//Contract Bill Payment Form Arraylist
	public static ArrayList<String> ContractBillPayment_ContractNo= new ArrayList<String>();
	public static ArrayList<String> ContractBillPayment_totalPaybleAmt= new ArrayList<String>();

	//Cancellation of Agency and Contract form
	

	
	//Suspension of Agency and Contract Form
	

	//////////Abhishek// ArrayList//////// ADH //Inspection Form


	///////// ArrayList//////// ADH //Inspection Letter
	


	//Show Cause Notice Form Arraylist///
	
	////Pdf Show Cause Notice arraylist
	


	//Hearing Entr Formy//////////
	

	//Hearing Letter////
	


	//ADH Bill Printing//
	public static ArrayList<String>Adh_LOIDate= new ArrayList<String>();

	//ADH Contract Note 
	public static ArrayList<String> pdf_ContractNote_ContractNo= new ArrayList<String>();
	public static ArrayList<String> pdf_ContractNote_ContractgenDate= new ArrayList<String>();
	public static ArrayList<String> pdf_ContractNote_ContractAmt= new ArrayList<String>();
	public static ArrayList<String> pdf_ContractNote_ContractFrom= new ArrayList<String>();
	public static ArrayList<String> pdf_ContractNote_ApplicantName= new ArrayList<String>();
	public static ArrayList<String> pdf_ContractNote_ApplicantAddreess= new ArrayList<String>();
	public static ArrayList<String> pdf_ContractNote_HoardingCode= new ArrayList<String>();
	public static ArrayList<String> pdf_ContractNote_HoardingDescription= new ArrayList<String>();


	//ADH Contract Bill Payment receipt 
	public static ArrayList<String> pdf_ContractBillpayment_rceiptNo= new ArrayList<String>();
	public static ArrayList<String> pdf_ContractBillpayment_rceiptDate= new ArrayList<String>();
	public static ArrayList<String> pdf_ContractBillpayment_referenceNo= new ArrayList<String>();
	public static ArrayList<String> pdf_ContractBillpayment_receivedFrom= new ArrayList<String>();
	public static ArrayList<String> pdf_ContractBillpayment_receivedAmt= new ArrayList<String>();
	public static ArrayList<String> pdf_ContractBillpayment_modeOfPayment= new ArrayList<String>();
	public static ArrayList<String> pdf_ContractBillpayment_contractNo= new ArrayList<String>();

	//ADH Renewal Of Contract hoarding Approval Letter
	public static ArrayList<String> Pdf_RoA_Approval_NewContractNo= new ArrayList<String>();
	public static ArrayList<String> Pdf_RoA_Approval_ApplicantName= new ArrayList<String>();
	public static ArrayList<String> Pdf_RoA_Approval_ApplicantAddress= new ArrayList<String>();
	public static ArrayList<String> Pdf_RoA_Approval_ApplicantionNo= new ArrayList<String>();
	public static ArrayList<String> Pdf_RoA_Approval_ApplicantionDate= new ArrayList<String>();
	public static ArrayList<String> Pdf_RoA_Approval_ContractNo= new ArrayList<String>();
	public static ArrayList<String> Pdf_RoA_Approval_ContractToDate= new ArrayList<String>();
	public static ArrayList<String> Pdf_RoA_Approval_RenewalDate= new ArrayList<String>();
	public static ArrayList<String> Pdf_RoA_Approval_RenewedfromDate= new ArrayList<String>();
	public static ArrayList<String> Pdf_RoA_Approval_RenewedToDate= new ArrayList<String>();


	//Set Up Hoarding Arraylist Approval Letter
	public static ArrayList<String> Pdf_SetUp_Approval_ApplicationNo = new ArrayList<String>();
	public static ArrayList<String> Pdf_SetUp_Approval_ContractDate = new ArrayList<String>();
	public static ArrayList<String> Pdf_SetUp_Approval_ContractNo = new ArrayList<String>();
	public static ArrayList<String> Pdf_SetUp_Approval_Location= new ArrayList<String>();
	public static ArrayList<String> Pdf_SetUp_Approval_ApplicantAddress= new ArrayList<String>();
	public static ArrayList<String> Pdf_SetUp_Approval_ApplicantName= new ArrayList<String>();

	/////////////////////////////////////END ADH  module//////////////////////////////////////////
	
	
	
	
	
	
	
	
	
	///////////////adh end of array;lis
	
	

	// string array for checklist verification
	public static String[] checkListVerifn;
	public static ArrayList<String> rejected = new ArrayList<String>(); 
	public static ArrayList<String> onhold = new ArrayList<String>();
	public static boolean resubmission = false;

	public static ArrayList<String> tenantname= new ArrayList<String>();
	public static ArrayList<String> contractno= new ArrayList<String>();
	public static ArrayList<String> fromdate= new ArrayList<String>();
	public static ArrayList<String> todate= new ArrayList<String>();
	public static ArrayList<String> tenantno= new ArrayList<String>();

	////////////////////rnlswap & adhswap///////////////////////////////

	//public static ArrayList<String> rnl_rowstobefilled= new ArrayList<String>();
	public static ArrayList<Integer> rnl_rowstobefilled= new ArrayList<Integer>();
//	public static ArrayList<Integer> updatedpayble_list= new ArrayList<Integer>();
	public static ArrayList<Integer> indexneedtobesckipped= new ArrayList<Integer>();
	public static ArrayList<Integer> adh_rowstobefilled= new ArrayList<Integer>();
	public static int updatedpayble_list;
	//////////////////////endofrnlswap/////////////
////rti payment chaalnno list list
	public static ArrayList<String> RTIapplicationchaalonno= new ArrayList<String>();
	public static ArrayList<String> RTILOIchaalonno= new ArrayList<String>();
	///////////////////
	
	public static ArrayList<String> challanNumber= new ArrayList<String>();
	/////////////////////////////////////ADH  module//////////////////////////////////////////

	//ADH General Arraylist
	public static ArrayList<String> ARR_ServiceNameADH = new ArrayList<String>();
	public static ArrayList<String> ApplicationNoADH = new ArrayList<String>();
	public static ArrayList<String> adhHearingOrdeNo= new ArrayList<String>();
	public static ArrayList<String> rrcontract_ApplicantFullname= new ArrayList<String>();
	///rti challn no
	public static ArrayList<String> swapchallnnolist = new ArrayList<String>();
	///end rti
	//ADH_Arrylist_Scrutiny form of Displyed of Hoarding
	public static ArrayList<String> BH_Scr_adhApptitle = new ArrayList<String>();
	public static ArrayList<String> BH_scr_adhHoardingFname= new ArrayList<String>();
	public static ArrayList<String> BH_scr_adhHoardingMname= new ArrayList<String>();
	public static ArrayList<String> BH_scr_adhHoardingLname= new ArrayList<String>();
	public static ArrayList<String> BH_scr_adhHoardingLFullname= new ArrayList<String>();
	public static ArrayList<String> BH_scr_adhHoardingMobNo= new ArrayList<String>();
	public static ArrayList<String> BH_scr_adhHoardingEmail= new ArrayList<String>();
	public static ArrayList<String> BH_scr_adhHoardingRemarks= new ArrayList<String>();
	public static ArrayList<String> BH_scr_adhHoardingFromDate= new ArrayList<String>();
	public static ArrayList<String> BH_scr_adhHoardingToDate= new ArrayList<String>();
	public static ArrayList<String> BH_scr_adhHoardingApplicationNo= new ArrayList<String>();
	public static ArrayList<String> BH_scr_adhHoardingContractAmt= new ArrayList<String>();
	public static ArrayList<String> BH_scr_adhHoardingDiscountAmt= new ArrayList<String>();
	public static ArrayList<String> BH_scr_adhHoardingPaybleAmt= new ArrayList<String>();
	public static ArrayList<String> BH_scr_adhHoardingRoundOffAmt= new ArrayList<String>();

	//ADH_Arrylist_Scrutiny form of Renewal of Advt Contract
	public static ArrayList<String> RAC_Scr_adhApptitle = new ArrayList<String>();
	public static ArrayList<String> RAC_scr_adhHoardingFname= new ArrayList<String>();
	public static ArrayList<String> RAC_scr_adhHoardingMname= new ArrayList<String>();
	public static ArrayList<String> RAC_scr_adhHoardingLname= new ArrayList<String>();
	public static ArrayList<String> RAC_scr_adhHoardingLFullname= new ArrayList<String>();
	public static ArrayList<String> RAC_scr_adhHoardingMobNo= new ArrayList<String>();
	public static ArrayList<String> RAC_scr_adhHoardingEmail= new ArrayList<String>();
	public static ArrayList<String> RAC_src_ApplicantEmail= new ArrayList<String>();
	public static ArrayList<String> RAC_src_ApplicantAddOne= new ArrayList<String>();
	public static ArrayList<String> RAC_src_ApplicantAddTwo= new ArrayList<String>();
	public static ArrayList<String> RAC_src_ApplicantPinCode= new ArrayList<String>();
	public static ArrayList<String> RAC_scr_adhHoardingRenewalDate= new ArrayList<String>();
	public static ArrayList<String> RAC_scr_adhHoardingRemarks= new ArrayList<String>();
	public static ArrayList<String> RAC_scr_adhHoardingFromDate= new ArrayList<String>();
	public static ArrayList<String> RAC_scr_adhHoardingToDate= new ArrayList<String>();
	public static ArrayList<String> RAC_scr_adhHoardingApplicationNo= new ArrayList<String>();
	public static ArrayList<String> RAC_scr_adhHoardingContractAmt= new ArrayList<String>();
	public static ArrayList<String> RAC_scr_adhHoardingDiscountAmt= new ArrayList<String>();
	public static ArrayList<String> RAC_scr_adhHoardingPaybleAmt= new ArrayList<String>();
	public static ArrayList<String> RAC_scr_adhHoardingRoundOffAmt= new ArrayList<String>();

	//ADH_Arrylist_Scrutiny form of Setup of Hoarding for display of Advertisement
	public static ArrayList<String> scr_suoh_ApplicantTitle= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_ApplicantFname= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_ApplicantMname= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_ApplicantLname= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_ApplicantFullname = new ArrayList<String>();
	public static ArrayList<String> scr_suoh_ApplicantMbNo= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_ApplicantEmail= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_ApplicantionNo= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_ApplicantPinCode= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_ApplicantRemarks= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_BookingfromDate= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_BookingtoDate= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_Ward= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_RoadType= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_EastLandmark= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_WestLandmark= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_SouthLandmark= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_NorthLandmark= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_GISNo= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_PlotNo= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_HouseNo= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_Area= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_SocietyName= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_RoadName= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_BuildingName= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_NewPinCode= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_HoardingDescription= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_HoardingType= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_HoardingType1= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_Length= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_Breadth= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_Elevation= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_GISNOPOPUP= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_AmountPopUp= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_LocationAddress= new ArrayList<String>();

	public static ArrayList<String> scr_suoh_Amount= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_Discount= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_PaybleAmount= new ArrayList<String>();
	public static ArrayList<String> scr_suoh_RoundOffAmount= new ArrayList<String>();

	public static ArrayList<String> scr_suoh_LocationCode= new ArrayList<String>();








	//ADH_Arrylist_Application form of Displyed of Hoarding

	public static ArrayList<String> adh_HoardingType1 = new ArrayList<String>();


	//ADH_Arrylist_Application form of Setup of Hoarding for display of Advertisement


	//ADH_Arrylist_Application form of Renewal of Advt Contract


	public static ArrayList<String> Acg_TenderRef= new ArrayList<String>();
	public static ArrayList<String> Acg_TenderDate = new ArrayList<String>();
	public static ArrayList<String> Acg_ContractDate= new ArrayList<String>();
	public static ArrayList<String> Acg_ContractFromDate= new ArrayList<String>();
	public static ArrayList<String> Acg_ContractToDate = new ArrayList<String>();
	public static ArrayList<String> Acg_ContractAmount= new ArrayList<String>();
	public static ArrayList<String> Acg_DiscountAmount = new ArrayList<String>();
	public static ArrayList<String> adhContractNo= new ArrayList<String>();


	//Cancellation of Agency and Contract form
	public static ArrayList<String> CoAC_HearingOrderNo= new ArrayList<String>();
	public static ArrayList<String> CoAC_HearingOrderDate = new ArrayList<String>();
	public static ArrayList<String> CoAC_contractNo= new ArrayList<String>();
	public static ArrayList<String> CoAC_AgencyorApplicationNo= new ArrayList<String>();
	public static ArrayList<String> CoAC_AgencyorApplicantName= new ArrayList<String>();
	public static ArrayList<String> CoAC_CancellationType= new ArrayList<String>();
	public static ArrayList<String> CoAC_CancellationDate= new ArrayList<String>();
	public static ArrayList<String> CoAC_Cancellationdecision= new ArrayList<String>();
	public static ArrayList<String> CoAC_CanceledBy= new ArrayList<String>();
	public static ArrayList<String> CoAC_CancellationRemarks= new ArrayList<String>();
	public static ArrayList<String> CoAC_CancellationNumber= new ArrayList<String>();


	//Cancellation of Agency and Contract letter
	public static ArrayList<String> Pdf_CoAC_HearingOrderNo= new ArrayList<String>();
	public static ArrayList<String> Pdf_CoAC_HearingOrderDate = new ArrayList<String>();
	public static ArrayList<String> Pdf_CoAC_contractNo= new ArrayList<String>();
	public static ArrayList<String> Pdf_CoAC_AgencyorApplicantName= new ArrayList<String>();
	public static ArrayList<String> Pdf_CoAC_CancellationDate= new ArrayList<String>();
	public static ArrayList<String> Pdf_CoAC_HoardingDescription= new ArrayList<String>();
	public static ArrayList<String> Pdf_CoAC_HoardingNo= new ArrayList<String>();
	public static ArrayList<String> Pdf_CoAC_ContractDate= new ArrayList<String>();


	//Suspension of Agency and Contract Form
	public static ArrayList<String> SoAC_HearingOrderNo= new ArrayList<String>();
	public static ArrayList<String> SoAC_HearingOrderDate = new ArrayList<String>();
	public static ArrayList<String> SoAC_contractNo= new ArrayList<String>();
	public static ArrayList<String> SoAC_AgencyorApplicationNo= new ArrayList<String>();
	public static ArrayList<String> SoAC_AgencyorApplicantName= new ArrayList<String>();
	public static ArrayList<String> SoAC_SuspensionDate= new ArrayList<String>();
	public static ArrayList<String> SoAC_Suspensiondecision= new ArrayList<String>();
	public static ArrayList<String> SoAC_SuspendedBy= new ArrayList<String>();
	public static ArrayList<String> SoAC_SuspensionRemarks= new ArrayList<String>();
	public static ArrayList<String> SoAC_SuspensionNumber= new ArrayList<String>();
	public static ArrayList<String> SoAC_SuspensionFromDate= new ArrayList<String>();
	public static ArrayList<String> SoAC_SuspensionToDate= new ArrayList<String>();

	//Suspension of Agency and Contract Form
	public static ArrayList<String> Pdf_SoAC_HearingOrderNo= new ArrayList<String>();
	public static ArrayList<String> Pdf_SoAC_HearingOrderDate = new ArrayList<String>();
	public static ArrayList<String> Pdf_SoAC_contractNo= new ArrayList<String>();
	public static ArrayList<String> Pdf_SoAC_contractDate= new ArrayList<String>();
	public static ArrayList<String> Pdf_SoAC_AgencyorApplicantName= new ArrayList<String>();
	public static ArrayList<String> Pdf_SoAC_SuspensionFromDate= new ArrayList<String>();
	public static ArrayList<String> Pdf_SoAC_SuspensionToDate= new ArrayList<String>();
	public static ArrayList<String> Pdf_SoAC_HoardingNo= new ArrayList<String>();
	public static ArrayList<String> Pdf_SoAC_HoardingDescription= new ArrayList<String>();

	//////////Abhishek// ArrayList//////// ADH //Inspection Form
	public static ArrayList<String> Ins_hoardingNo= new ArrayList<String>();
	public static ArrayList<String> Ins_hoardingDesc= new ArrayList<String>();
	public static ArrayList<String> Ins_ContractID= new ArrayList<String>();
	public static ArrayList<String> Ins_Contractstatus= new ArrayList<String>();
	public static ArrayList<String> Ins_applicationNo= new ArrayList<String>();
	public static ArrayList<String> Ins_agencyName= new ArrayList<String>();
	public static ArrayList<String> Ins_contractFormDate= new ArrayList<String>();
	public static ArrayList<String> Ins_contractToDate= new ArrayList<String>();
	public static ArrayList<String> Ins_Inspectiontype= new ArrayList<String>();
	public static ArrayList<String> Ins_Inspectiondate= new ArrayList<String>();
	public static ArrayList<String> Ins_InspectionERemark= new ArrayList<String>();
	public static ArrayList<String> Ins_InspectedBy= new ArrayList<String>();
	public static ArrayList<String> Ins_Inpresenceof= new ArrayList<String>();
	public static ArrayList<String> Ins_Status= new ArrayList<String>();
	public static ArrayList<String> Ins_inpectionNo= new ArrayList<String>();

	
	
	////////////////////////////adh arraylist piyush
	
	public static ArrayList<String> Pdf_BP_ApplicantName= new ArrayList<String>();
	public static ArrayList<String> Pdf_BP_ApplicantAddress= new ArrayList<String>();
	public static ArrayList<String> Pdf_BP_ContractFromDate= new ArrayList<String>();
	public static ArrayList<String> Pdf_BP_ContractToDate= new ArrayList<String>();
	public static ArrayList<String> Pdf_BP_ContractNo= new ArrayList<String>();
	public static ArrayList<String> Pdf_BP_AgencyNo= new ArrayList<String>();
	public static ArrayList<String> Pdf_BP_DiscountAmount= new ArrayList<String>();
	public static ArrayList<String> Pdf_BP_TotalPaybleAmt= new ArrayList<String>();
	public static ArrayList<String> Pdf_Acceptance_TotalAmt= new ArrayList<String>();
	public static ArrayList<String> pdf_ApplicationTime_Receipt_ModeOfPayment= new ArrayList<String>();
	public static ArrayList<String> pdf_ApplicationTime_Receipt_ApplicantName= new ArrayList<String>();
	public static ArrayList<String> Pdf_Acceptance_TotalPaybleAmt= new ArrayList<String>();
	public static ArrayList<String> Pdf_Acceptance_LOINo= new ArrayList<String>();
	public static ArrayList<String> Pdf_Acceptance_LOIDate= new ArrayList<String>();
	public static ArrayList<String> Pdf_Acceptance_AppliantName= new ArrayList<String>();
	/*public static ArrayList<String> scrupdatedpayble_list= new ArrayList<String>();*/
	public static ArrayList<String> Pdf_Approval_ApplicantName= new ArrayList<String>();
	public static ArrayList<String> Pdf_Approval_ApplicantionNo= new ArrayList<String>();
	public static ArrayList<String> Pdf_Approval_ContractToDate= new ArrayList<String>();
	public static ArrayList<String> Pdf_Approval_ContractFromDate= new ArrayList<String>();
	public static ArrayList<String> Pdf_Approval_ContractDate= new ArrayList<String>();
	public static ArrayList<String> pdf_Loi_Receipt_ModeOfPayment= new ArrayList<String>();
	public static ArrayList<String> pdf_Loi_Receipt_TotalReceivedAmt= new ArrayList<String>();
	public static ArrayList<String> pdf_Loi_Receipt_ApplicantName= new ArrayList<String>();
	
	
	public static ArrayList<String> pdf_Loi_Receipt_LOIDate= new ArrayList<String>();
	public static ArrayList<String> pdf_Loi_Receipt_LOINo= new ArrayList<String>();
	public static ArrayList<String> pdf_Loi_Receipt_BookingNo= new ArrayList<String>();	
	public static ArrayList<String> pdf_Loi_Receipt_ContractNo= new ArrayList<String>();

	
	
	public static ArrayList<String> Pdf_Approval_ContractNo= new ArrayList<String>();
	///////// ArrayList//////// ADH //Inspection Letter
	public static ArrayList<String> Ins_Pdf_hoardingNo= new ArrayList<String>();
	public static ArrayList<String> Ins_Pdf_hoardingDesc= new ArrayList<String>();
	public static ArrayList<String> Ins_Pdf_ContractID= new ArrayList<String>();
	public static ArrayList<String> Ins_Pdf_Inspectiondate= new ArrayList<String>();
	public static ArrayList<String> Ins_Pdf_InspectedBy= new ArrayList<String>();
	public static ArrayList<String> Ins_Pdf_inpectionNo= new ArrayList<String>();
	public static ArrayList<String> Ins_Pdf_agencyName= new ArrayList<String>();



	//Show Cause Notice Form Arraylist///
	public static ArrayList<String> Show_NoticeNo= new ArrayList<String>();
	public static ArrayList<String>Show_contractNo= new ArrayList<String>();
	public static ArrayList<String>Show_AgencyorApplicantName= new ArrayList<String>();
	public static ArrayList<String>Show_AgencyorApplicationNo= new ArrayList<String>();
	public static ArrayList<String>Show_ContractFromDate= new ArrayList<String>();
	public static ArrayList<String>Show_ContractToDate= new ArrayList<String>();
	public static ArrayList<String>Show_ContrcatStatus= new ArrayList<String>();
	public static ArrayList<String>Show_NoticeType= new ArrayList<String>();
	public static ArrayList<String>Show_NoticeDate= new ArrayList<String>();
	public static ArrayList<String>Show_NoticesendBy= new ArrayList<String>();
	public static ArrayList<String>Show_NoticeRemarks= new ArrayList<String>();
	public static ArrayList<String>Show_HearingDate= new ArrayList<String>();
	public static ArrayList<String>Show_OfficiatedBy= new ArrayList<String>();
	////Pdf Show Cause Notice arraylist
	public static ArrayList<String>Pdf_Show_NoticeNo= new ArrayList<String>();
	public static ArrayList<String>Pdf_Show_contractNo= new ArrayList<String>();
	public static ArrayList<String>Pdf_Show_NoticeType= new ArrayList<String>();
	public static ArrayList<String>Pdf_NoticeDate= new ArrayList<String>();
	public static ArrayList<String>Pdf_Show_HoardingNo= new ArrayList<String>();
	public static ArrayList<String>Pdf_Show_InspectionNo= new ArrayList<String>();


	//Hearing Entr Formy//////////
	public static ArrayList<String> He_HearingeNo = new ArrayList<String>();
	public static ArrayList<String> He_HearingStatus = new ArrayList<String>();
	public static ArrayList<String> He_HearingRemarks = new ArrayList<String>();
	public static ArrayList<String> He_HearingDesicion = new ArrayList<String>();
	public static ArrayList<String> He_HearingdBy = new ArrayList<String>();
	public static ArrayList<String> He_HearingDate= new ArrayList<String>();
	public static ArrayList<String> He_AgencyorApplicationNo= new ArrayList<String>();
	public static ArrayList<String> He_AgencyorApplicantName= new ArrayList<String>();
	public static ArrayList<String> He_NoticedhearingDate= new ArrayList<String>();
	public static ArrayList<String> He_ContractNo= new ArrayList<String>();
	public static ArrayList<String> He_NoticedDate= new ArrayList<String>();
	public static ArrayList<String> He_NoticeType= new ArrayList<String>();
	public static ArrayList<String> He_NoticeNo= new ArrayList<String>();

	//Hearing Letter////
	public static ArrayList<String> Pdf_He_HearingeNo = new ArrayList<String>();
	public static ArrayList<String> Pdf_He_HearingDesicion = new ArrayList<String>();
	public static ArrayList<String> Pdf_He_NoticedDate= new ArrayList<String>();
	public static ArrayList<String> Pdf_He_HoardingNo= new ArrayList<String>();
	public static ArrayList<String> Pdf_He_NoticeNo= new ArrayList<String>();
	public static ArrayList<String> Pdf_He_InspectionNo= new ArrayList<String>();
	public static ArrayList<String> Pdf_He_AgencyorApplicantName= new ArrayList<String>();


	/////////////////////////////////////END ADH  module//////////////////////////////////////////


	////////////////////////jyoti & swap merjed market arraylist//////////////////////////////
	public static ArrayList<String> Intimation_ChngBusiNm_HolderNm= new ArrayList<String>();

	public static ArrayList<String> HolderName = new ArrayList<String>();
	public static ArrayList<String> HolderAddress = new ArrayList<String>();
	public static ArrayList<String> HolderBusinessName = new ArrayList<String>();
	public static ArrayList<String> HolderOrganizationName = new ArrayList<String>();
	public static ArrayList<String> duplicate_bname_list = new ArrayList<String>();
	public static ArrayList<String>duplicate_holder_list = new ArrayList<String>();
	public static ArrayList<String> duplicate_HolderName= new ArrayList<String>();
	public static ArrayList<String> duplicate_loi_holdernamelist= new ArrayList<String>();
	public static ArrayList<String>duplicate_loiHolderBusinessName= new ArrayList<String>();
	public static ArrayList<String>duplicate_rejHolderBusinessName= new ArrayList<String>();
	public static ArrayList<String>duplicate_rejformHolderBusinessName= new ArrayList<String>();
	public static ArrayList<String> duplicate_HolderBusinessName= new ArrayList<String>();
	public static ArrayList<String> duplicate_HolderOrganizationName= new ArrayList<String>();
	public static ArrayList<String> pdf_new_holdername= new ArrayList<String>();
	public static ArrayList<String> LOI_Receipt_DateList= new ArrayList<String>();

	public static ArrayList<String> LOI_Receipt_DateNo= new ArrayList<String>();
	public static ArrayList<String> LOI_ReceivedFrom= new ArrayList<String>();
	public static ArrayList<String> LOI_Discount= new ArrayList<String>();
	public static ArrayList<String> LOI_TotalReceivedAmt= new ArrayList<String>();
	public static ArrayList<String> LOI_LicenseNo= new ArrayList<String>();

	//pdf duplicate arraylist
	public static ArrayList<String> LOI_PaymentMode= new ArrayList<String>();
	public static ArrayList<String> LOI_Date= new ArrayList<String>();
	public static ArrayList<String> LOI_LicenseFee= new ArrayList<String>();
	public static ArrayList<String> LOI_HolderNameList= new ArrayList<String>();
	public static ArrayList<String> LOIReceipt_ReceivedFromList= new ArrayList<String>();
	public static ArrayList<String> LOIReceipt_TotalReceivedAMountList= new ArrayList<String>();
	public static ArrayList<String> LOI_TotalReceivedAmtList= new ArrayList<String>();
	public static ArrayList<String> LOIReceipt_PaymentModeList= new ArrayList<String>();
	public static ArrayList<String> LOI_PaymentModeList= new ArrayList<String>();
	public static ArrayList<String> LOIReceipt_DiscountList= new ArrayList<String>();

	public static ArrayList<String> LOI_DiscountList= new ArrayList<String>();
	public static ArrayList<String> LOIReceipt_LOINoList= new ArrayList<String>();
	public static ArrayList<String> LOI_LOINoList= new ArrayList<String>();
	public static ArrayList<String> LOI_LOINo= new ArrayList<String>();
	public static ArrayList<String> LOINumberList= new ArrayList<String>();
	public static ArrayList<String> chngBusiNm_licenseno_list= new ArrayList<String>();
	public static ArrayList<String> ChhngBusiNm_Submit_Msg= new ArrayList<String>();
	public static ArrayList<String> TUN_licenseno_list= new ArrayList<String>();
	/*public static ArrayList<String> pdf_tlun_address= new ArrayList<String>();
	public static ArrayList<String> pdf_tlun_address= new ArrayList<String>();
	public static ArrayList<String> pdf_tlun_address= new ArrayList<String>();
	public static ArrayList<String> pdf_tlun_address= new ArrayList<String>();
	public static ArrayList<String> pdf_tlun_address= new ArrayList<String>();
	public static ArrayList<String> pdf_tlun_address= new ArrayList<String>();
	public static ArrayList<String> pdf_tlun_address= new ArrayList<String>();
	 */

	public static String servicenameprintergrid;

	//inspection form
	public static ArrayList<String> inspection_licenseno_list= new ArrayList<String>();
	public static ArrayList<String> inspection_date_list= new ArrayList<String>();
	public static ArrayList<String> inspection_Remarks_list= new ArrayList<String>();
	public static ArrayList<String> Inspection_inspecter_name_list= new ArrayList<String>();
	public static ArrayList<String> Inspection_presense_name_list= new ArrayList<String>();




	//inspection report
	public static ArrayList<String> report_inspection_licenseno_list= new ArrayList<String>();
	public static ArrayList<String> report_inspection_date_list= new ArrayList<String>();
	public static ArrayList<String> report_inspection_Remarks_list= new ArrayList<String>();

	/*public static ArrayList<String> pdf_renewalfromdate= new ArrayList<String>();
	public static ArrayList<String> pdf_renewal_todate= new ArrayList<String>();
	public static ArrayList<String> pdf_renewal_holdername= new ArrayList<String>();
	public static ArrayList<String> pdf_renwal_businessname= new ArrayList<String>();
	public static ArrayList<String> pdf_renewal_tfees= new ArrayList<String>();

	public static ArrayList<String> pdf_renewal_jowner_pdf= new ArrayList<String>();
	public static ArrayList<String> pdf_renewal_address= new ArrayList<String>();


	//Showcause
	public static ArrayList<String> showcause_report_address_list= new ArrayList<String>();*/
	//public static ArrayList<String> showcause_report_address_list= new ArrayList<String>();


	public static ArrayList<String> Fees_new_list= new ArrayList<String>();
	public static ArrayList<String> RenewalToDate_list= new ArrayList<String>();
	public static ArrayList<String> Fees_list= new ArrayList<String>();
	public static ArrayList<String> RenewalDate_list= new ArrayList<String>();
	public static ArrayList<String> Remarks_list= new ArrayList<String>();
	public static ArrayList<String> totalamt_list= new ArrayList<String>();
	public static ArrayList<String> Ren_scrutiny_Licensenolist= new ArrayList<String>();
	public static ArrayList<String> Ren_loi_holdername_list= new ArrayList<String>();
	public static ArrayList<String> Renewal_loi_Holder_Name_list= new ArrayList<String>();
	public static ArrayList<String> Renew_loi_businessname_list= new ArrayList<String>();

	public static ArrayList<String> Ren_loi_business_name_list= new ArrayList<String>();
	public static ArrayList<String> Ren_loi_totalamt_list= new ArrayList<String>();
	public static ArrayList<String> tax_amount_list= new ArrayList<String>();
	public static ArrayList<String> applicant_name_list= new ArrayList<String>();
	public static ArrayList<String> applicant_name_service_list= new ArrayList<String>();
	public static ArrayList<String>Scrutiny_rej_remark_list=new ArrayList<String>();
	public static ArrayList<String>tlun_busineess_name_list=new ArrayList<String>();
	public static ArrayList<String>clv_Service_Name_list=new ArrayList<String>();
	public static ArrayList<String>clv_ApplicantName_list=new ArrayList<String>();
	public static ArrayList<String>clv_Application_list=new ArrayList<String>();
	public static ArrayList<String>clv_ApplicationDate_list=new ArrayList<String>();
	public static ArrayList<String>tlun_mainholder_list=new ArrayList<String>();
	public static ArrayList<String>tlun_licenseno_list=new ArrayList<String>();
	public static ArrayList<String>tlun_scrutiny_licenseno_list=new ArrayList<String>();
	public static ArrayList<String>tlun_scrutiny_loi_list=new ArrayList<String>();
	public static ArrayList<String> tlunholdername_list= new ArrayList<String>();
	public static ArrayList<String> tlun_scrutiny_loi_holdename_list= new ArrayList<String>();
	public static ArrayList<String> mkt_scrutiny_loi_businessname_no_list= new ArrayList<String>();
	public static ArrayList<String> mkt_service_holdername_no_list= new ArrayList<String>();
	public static ArrayList<String> mkt_service_businessname_no_list= new ArrayList<String>();
	public static ArrayList<String> tlun_reg_holdername_list= new ArrayList<String>();
	public static ArrayList<String> tlun_reg_mobileno_list= new ArrayList<String>();
	public static ArrayList<String> tlun_reg_holdername_scrutiny_list= new ArrayList<String>();
	public static ArrayList<String> tlunholdername_mobile_list= new ArrayList<String>();
	public static ArrayList<String> tlunholdername_mobile__loreglist= new ArrayList<String>();
	//showcause
	public static ArrayList<String> showcause_lettertype_pdf= new ArrayList<String>();
	public static ArrayList<String> showcause_holdername_pdf= new ArrayList<String>();
	public static ArrayList<String> showcause_notice_no_pdf= new ArrayList<String>();
	public static ArrayList<String> showcause_subject_pdf= new ArrayList<String>();
	public static ArrayList<String> showcause_ulbname_pdf= new ArrayList<String>();
	public static ArrayList<String> showcause_noticedate_pdf= new ArrayList<String>();
	public static ArrayList<String> showcause_noticereason_pdf= new ArrayList<String>();
	public static ArrayList<String> showcause_noticapplnno_pdf= new ArrayList<String>();
	public static ArrayList<String> showcause_applnnodate_pdf= new ArrayList<String>();
	public static ArrayList<String> showcause_businessname_pdf= new ArrayList<String>();
	public static ArrayList<String> showcause_InspectorName_pdf= new ArrayList<String>();
	public static ArrayList<String> showcause_license_no_list= new ArrayList<String>();
	public static ArrayList<String> showcause_noticetype_list= new ArrayList<String>();
	public static ArrayList<String> showcause_holdername_list= new ArrayList<String>();
	public static ArrayList<String> showcause_businessname_list= new ArrayList<String>();
	public static ArrayList<String> showcause_orgname_list= new ArrayList<String>();
	public static ArrayList<String> showcase_Noticedate_list= new ArrayList<String>();
	public static ArrayList<String> showcase_remarks_list= new ArrayList<String>();
	public static ArrayList<String> showcase_inspector_name_list= new ArrayList<String>();
	public static ArrayList<String> showcase_date_list= new ArrayList<String>();
	public static ArrayList<String> showcase_Reason_list= new ArrayList<String>();
	//hearingdate

	public static ArrayList<String> hearing_date_licensceno_list= new ArrayList<String>();
	public static ArrayList<String> hearingdate_noticeno_list= new ArrayList<String>();
	public static ArrayList<String> hearingdate_noticedate_list= new ArrayList<String>();
	public static ArrayList<String> hearingdate_hearingdate_list= new ArrayList<String>();
	public static ArrayList<String> hearingdate_Inspection_Date_list= new ArrayList<String>();
	public static ArrayList<String> hearingdate_Show_Cuase_reason_list= new ArrayList<String>();


	public static ArrayList<String> hearingdate_pdf_NoticeNo= new ArrayList<String>();
	public static ArrayList<String> hearingdate_pdf_Noticedate= new ArrayList<String>();
	public static ArrayList<String> HDE_HearingDate_list= new ArrayList<String>();
	public static ArrayList<String> HDE_AppName_list= new ArrayList<String>();
	///hearing details
	public static ArrayList<String> hearingdetails_notice_type_list= new ArrayList<String>();
	public static ArrayList<String> hearingdetails_License_no_list= new ArrayList<String>();
	public static ArrayList<String> hearingdetails_hearingdate_list= new ArrayList<String>();
	public static ArrayList<String> hearingdetails_ownername_list= new ArrayList<String>();

	public static ArrayList<String> hearingdetails_hearingstatus_list= new ArrayList<String>();
	public static ArrayList<String> mkt_clbf_hearingdetails_inspector_list= new ArrayList<String>();

	public static ArrayList<String> mkt_clbf_hearingdetails_Reason_list= new ArrayList<String>();
	public static ArrayList<String> mkt_clbf_hearingdetails_Remark_list= new ArrayList<String>();
	//cbf
	public static ArrayList<String> cbf_licenseno_list= new ArrayList<String>();
	public static ArrayList<String> cbf_canclationdate_list= new ArrayList<String>();
	public static ArrayList<String> cbf_cancereason_list= new ArrayList<String>();
	public static ArrayList<String> cbf_canceledby_list= new ArrayList<String>();
	public static ArrayList<String> CBF_CancellationNo_pdf_list= new ArrayList<String>();
	public static ArrayList<String> CBF_Reason_pdf_list= new ArrayList<String>();
	public static ArrayList<String> CBF_LicenceNo_pdf_list= new ArrayList<String>();
	public static ArrayList<String> CBF_ScNoticedate_list= new ArrayList<String>();
	public static ArrayList<String> CBF_BuisnessName__pdf_list= new ArrayList<String>();





	public static ArrayList<String> hearingdetails_noticeno_no_list= new ArrayList<String>();
	/*public static ArrayList<String> TCtenantName= new ArrayList<String>();
	public static ArrayList<String> TCtenantNo= new ArrayList<String>();
	public static ArrayList<String> TCcontractNo= new ArrayList<String>();
	public static ArrayList<String> TCcontractFromDate= new ArrayList<String>();
	public static ArrayList<String> TCcontractToDate= new ArrayList<String>(); 
	public static ArrayList<String> TCcontractAmount= new ArrayList<String>();
	public static ArrayList<String> TCdiscountAmount= new ArrayList<String>();
	public static ArrayList<String> TCtotalPayableAmount= new ArrayList<String>();*/
	public static ArrayList<String> PaymentHistory = new ArrayList<String>();

	public static String clvapplicationdate;

	//Array List for Market


	//public static ArrayList<String> HolderBusinessName = new ArrayList<String>();


	public static ArrayList<String>  NewTradeFeeTypeList_MKTLicFee = new ArrayList<String>();


	public static ArrayList<String> MarketLicense_HolderFee = new ArrayList<String>();
	public static ArrayList<String> Rebate_HolderFee = new ArrayList<String>();
	public static ArrayList<String> Rebate_HolderFeeType = new ArrayList<String>(); 
	public static ArrayList<String> MarketLicense_HolderFeeType = new ArrayList<String>();












	public static ArrayList<String> Scrutiny_MKT_HolderFeeType= new ArrayList<String>();
	public static ArrayList<String> Scrutiny_Rebate_HolderFeeType= new ArrayList<String>();
	public static ArrayList<String> MKTLicense_FeeTypeList_app= new ArrayList<String>();
	public static ArrayList<String> Rebate_FeeTypeList_app= new ArrayList<String>();
	public static ArrayList<String> Scrutiny_MKT_HolderFee= new ArrayList<String>();
	public static ArrayList<String> MKTLicense_FeeList_app= new ArrayList<String>();
	public static ArrayList<String> Rebate_FeeList_app= new ArrayList<String>();
	/*public static ArrayList<String> report_inspection_Remarks_list= new ArrayList<String>();*/



	//Showcause

	public static ArrayList<String> Inti_NewLicense_HolderNm_List= new ArrayList<String>();


	public static ArrayList<String> TotalPayAmountList= new ArrayList<String>();
	public static ArrayList<String> TotalPayableAmount= new ArrayList<String>();
	public static ArrayList<String> NewLOITotalPayableAmt= new ArrayList<String>();
	public static ArrayList<String> ScrutinyLOITotalPayableList= new ArrayList<String>();
	public static ArrayList<String> Scrutiny_LOITotalAmountList= new ArrayList<String>();
	public static ArrayList<String> NewLOITotalAmt= new ArrayList<String>();
	public static ArrayList<String> NewLOIRebateAmt= new ArrayList<String>();
	public static ArrayList<String> Scrutiny_LOIRebateList= new ArrayList<String>();
	public static ArrayList<String> Application_NewLicense_MarketLicenseFee= new ArrayList<String>();
	public static ArrayList<String> Total_MarketLicenseFee= new ArrayList<String>();

	public static ArrayList<String> Total_RebateFee= new ArrayList<String>();

	public static ArrayList<String> Application_NewLicense_RebateFee= new ArrayList<String>();
	public static ArrayList<String> Intimation_MKTLicenseFee= new ArrayList<String>();
	public static ArrayList<String> Intimation_MKTRebate= new ArrayList<String>();
	public static ArrayList<String>Inti_LOINo=new ArrayList<String>();
	public static ArrayList<String>pdf_new_holdername_List=new ArrayList<String>();
	public static ArrayList<String>pdf_Total_Fees_List=new ArrayList<String>();
	public static ArrayList<String>pdf_new_holder_BusinessName=new ArrayList<String>();
	public static ArrayList<String>Scrutiny_Msg_LOINoList=new ArrayList<String>();
	/*public static ArrayList<String>clv_ApplicationDate_list=new ArrayList<String>();
			public static ArrayList<String>tlun_mainholder_list=new ArrayList<String>();
			public static ArrayList<String>tlun_licenseno_list=new ArrayList<String>();
			public static ArrayList<String>tlun_scrutiny_licenseno_list=new ArrayList<String>();
			public static ArrayList<String>tlun_scrutiny_loi_list=new ArrayList<String>();
			public static ArrayList<String> tlunholdername_list= new ArrayList<String>();
			public static ArrayList<String> tlun_scrutiny_loi_holdename_list= new ArrayList<String>();
			public static ArrayList<String> mkt_scrutiny_loi_businessname_no_list= new ArrayList<String>();
			public static ArrayList<String> mkt_service_holdername_no_list= new ArrayList<String>();
			public static ArrayList<String> mkt_service_businessname_no_list= new ArrayList<String>();
			public static ArrayList<String> tlun_reg_holdername_list= new ArrayList<String>();
			public static ArrayList<String> tlun_reg_mobileno_list= new ArrayList<String>();
			public static ArrayList<String> tlun_reg_holdername_scrutiny_list= new ArrayList<String>();*/
	/*	public static ArrayList<String> tlunholdername_mobile_list= new ArrayList<String>();
			public static ArrayList<String> tlunholdername_mobile__loreglist= new ArrayList<String>();*/

	public static ArrayList<String> RenewalFromDate_list= new ArrayList<String>();
	public static ArrayList<String> Renewal_licenseNo_list= new ArrayList<String>();
	public static ArrayList<String> Renew_businessname_list= new ArrayList<String>();
	public static ArrayList<String> renew_orgname= new ArrayList<String>();
	public static ArrayList<String> renewal_address= new ArrayList<String>();
	public static ArrayList<String> Renewal_Holder_Name_list= new ArrayList<String>();
	public static ArrayList<String> IntimationLetter_Renew_HolderName= new ArrayList<String>();
	public static ArrayList<String> Inti_RenewLicense_TotalFee= new ArrayList<String>();
	public static ArrayList<String> IntiLetter_Renew_TotalAmountApp= new ArrayList<String>();
	public static ArrayList<String> IntimationLetter_Renew_TotalAmount= new ArrayList<String>();
	public static ArrayList<String> IntimationLetter_tun_HolderName= new ArrayList<String>();
	public static ArrayList<String> IntiLetter_tun_HolderNameApp= new ArrayList<String>();
	public static ArrayList<String> IntiLetter_Duplicate_HolderNameApp= new ArrayList<String>();
	public static ArrayList<String> Inti_Duplicate_HolderNm= new ArrayList<String>();
	public static ArrayList<String> DupTotalFeesList= new ArrayList<String>();
	public static ArrayList<String> IntiLetter_Duplicate_TotalFeesApp= new ArrayList<String>();
	public static ArrayList<String> IntiLetter_Duplicate_TotalFees= new ArrayList<String>();


	////////////////////////end of jyotis array list

	/////////////////////////////end of mejed of arraylist mkt////////////////////////////////////////



	public static ArrayList<String> Homepage_imagename_list= new ArrayList<String>();
	public static ArrayList<String> Homepage_logname_list= new ArrayList<String>();
	public static ArrayList<String> Downloded_Homepage_imagename_list= new ArrayList<String>();
	public static ArrayList<String> typeoffield_list= new ArrayList<String>();
	public static ArrayList<String> adslables_list= new ArrayList<String>();
	public static ArrayList<String> field_labellist= new ArrayList<String>();





	////////////////////EIP/////////////////////////////////////////////////////////////










	///////////////RTI/////////////////////
	///RTI RESPONSE/////////////////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<String> RTIResponse_RTIapplicationno= new ArrayList<String>();
	public static ArrayList<String> RTIResponse_Date= new ArrayList<String>();
	public static ArrayList<String> RTIResponse_TypeofApplicant= new ArrayList<String>();
	public static ArrayList<String> RTIResponse_ApplicantName= new ArrayList<String>();
	public static ArrayList<String> RTIResponse_MobileNo= new ArrayList<String>();
	public static ArrayList<String> RTIResponse_Email= new ArrayList<String>();
	public static ArrayList<String> RTIResponse_AlertS= new ArrayList<String>();
	public static ArrayList<String> RTIResponse_bpl= new ArrayList<String>();
	public static ArrayList<String> RTIResponse_Adddet= new ArrayList<String>();
	public static ArrayList<String> RTIResponse_Pincode= new ArrayList<String>();
	public static ArrayList<String> RTIResponse_Adddet1= new ArrayList<String>();
	public static ArrayList<String> RTIResponse_Pincode1= new ArrayList<String>();
	public static ArrayList<String> RTIResponse_Subject= new ArrayList<String>();
	public static ArrayList<String> RTIResponse_partiinfo= new ArrayList<String>();
	public static ArrayList<String> RTIResponse_mediatype= new ArrayList<String>();
	public static ArrayList<String> RTIResponse_Circle= new ArrayList<String>();
	public static ArrayList<String> RTIResponse_action_application= new ArrayList<String>();
	public static ArrayList<String> RTIResponse_fullpartial= new ArrayList<String>();
	public static ArrayList<String> RTI_response_status_list= new ArrayList<String>();
	/////////////////////////////////////first appeal//
	public static ArrayList<String> RTIfirstappeal_RTIapplicationno= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeal_Date= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeal_TypeofApplicant= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeal_ApplicantName= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeal_MobileNo= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeal_Email= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeal_AlertS= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeal_bpl= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeal_Adddet= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeal_Pincode= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeal_Adddet1= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeal_Pincode1= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeal_Subject= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeal_partiinfo= new ArrayList<String>();
	public static ArrayList<String>RTIfirstappeal_mediatype= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeal_Circle= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeal_action_application= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeal_fullpartial= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeal_status_list= new ArrayList<String>();
	public static ArrayList<String> applicationNoForFirstAppeal_list= new ArrayList<String>();

	//////////////////////////first appeal list////////
	/////////////////////////////////////first appeal//
	public static ArrayList<String> RTIfirstappeallist_RTIapplicationno= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeallist_Date= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeallist_TypeofApplicant= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeallist_ApplicantName= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeallist_MobileNo= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeallist_Email= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeallist_AlertS= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeallist_bpl= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeallist_Adddet= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeallist_Pincode= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeallist_Adddet1= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeallist= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeallist_Subject= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeallist_partiinfo= new ArrayList<String>();
	public static ArrayList<String>RTIfirstappeallist_mediatype= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeallist_Circle= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeallist_action_application= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeallist_fullpartial= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeallist_status_list= new ArrayList<String>();
	public static ArrayList<String> RTIfirstappeallist_hearStatus_list= new ArrayList<String>();

	//////////////////////////////////////hearing process
	public static ArrayList<String> RTIhearing_RTIapplicationno= new ArrayList<String>();
	public static ArrayList<String> RTIhearing_Date= new ArrayList<String>();
	public static ArrayList<String> RTIhearing_TypeofApplicant= new ArrayList<String>();
	public static ArrayList<String> RTIhearing_ApplicantName= new ArrayList<String>();
	public static ArrayList<String> RTIhearing_MobileNo= new ArrayList<String>();
	public static ArrayList<String> RTIhearing_Email= new ArrayList<String>();
	public static ArrayList<String> RTIhearing_AlertS= new ArrayList<String>();
	public static ArrayList<String> RTIhearing_bpl= new ArrayList<String>();
	public static ArrayList<String> RTIhearing_Adddet= new ArrayList<String>();
	public static ArrayList<String> RTIhearing_Pincode= new ArrayList<String>();
	public static ArrayList<String> RTIhearing_Adddet1= new ArrayList<String>();
	public static ArrayList<String> RTIhearing= new ArrayList<String>();
	public static ArrayList<String> RTIhearing_Subject= new ArrayList<String>();
	public static ArrayList<String> RTIhearing_partiinfo= new ArrayList<String>();
	public static ArrayList<String>RTIhearing_mediatype= new ArrayList<String>();
	public static ArrayList<String> RTIhearing_Circle= new ArrayList<String>();
	public static ArrayList<String> RTIhearing_action_application= new ArrayList<String>();
	public static ArrayList<String> RTIhearing_fullpartial= new ArrayList<String>();
	public static ArrayList<String> RTIhearing_status_list= new ArrayList<String>();
	public static ArrayList<String> RTIhearing_hearStatus_list= new ArrayList<String>();

	////////second appeal//////////////////hearing process//////////////

	public static ArrayList<String> RTIsecondappeal_RTIapplicationno= new ArrayList<String>();
	public static ArrayList<String> RTIsecondappeal_Date= new ArrayList<String>();
	public static ArrayList<String> RTIsecondappeal_TypeofApplicant= new ArrayList<String>();
	public static ArrayList<String> RTIsecondappeal_ApplicantName= new ArrayList<String>();
	public static ArrayList<String> RTIsecondappeal_MobileNo= new ArrayList<String>();
	public static ArrayList<String> RTIsecondappeal_Email= new ArrayList<String>();
	public static ArrayList<String> RTIsecondappeal_AlertS= new ArrayList<String>();
	public static ArrayList<String> RTIsecondappeal_bpl= new ArrayList<String>();
	public static ArrayList<String> RTIsecondappeal_Adddet= new ArrayList<String>();
	public static ArrayList<String> RTIsecondappeal_Pincode= new ArrayList<String>();
	public static ArrayList<String> RTIsecondappeal_Adddet1= new ArrayList<String>();
	public static ArrayList<String> RTIsecondappeal= new ArrayList<String>();
	public static ArrayList<String> RTIsecondappeal_Subject= new ArrayList<String>();
	public static ArrayList<String> RTIsecondappeal_partiinfo= new ArrayList<String>();
	public static ArrayList<String>RTIsecondappeal_mediatype= new ArrayList<String>();
	public static ArrayList<String> RTIsecondappeal_Circle= new ArrayList<String>();
	public static ArrayList<String> RTIsecondappeal_action_application= new ArrayList<String>();
	public static ArrayList<String> RTIsecondappeal_fullpartial= new ArrayList<String>();
	public static ArrayList<String> RTIsecondappeal_status_list= new ArrayList<String>();
	public static ArrayList<String> RTIsecondappeal_hearStatus_list= new ArrayList<String>();
	////////RTI application/////////
	public static ArrayList<String> RTIApplication_orgind= new ArrayList<String>();
	public static ArrayList<String> RTIApplication_orgname= new ArrayList<String>();
	public static ArrayList<String> RTI_fullname_list= new ArrayList<String>();
	public static ArrayList<String> RTI_pincode_list= new ArrayList<String>();
	public static ArrayList<String> rti_subject_list= new ArrayList<String>();
	public static ArrayList<String> rti_rtiappdetail_list= new ArrayList<String>();
	public static ArrayList<String> rti_apptype_form_list= new ArrayList<String>();
	public static ArrayList<String> rti_mediaType_list= new ArrayList<String>();

	//////aparejection pdf////


	public static ArrayList<String>pdf_Application_date_list=new ArrayList<String>();
	public static ArrayList<String>pdf_Application_appealno_list=new ArrayList<String>();
	public static ArrayList<String>pdf_Application_address_list=new ArrayList<String>();
	public static ArrayList<String>pdf_Application_applicantname_list=new ArrayList<String>();
	public static ArrayList<String>pdf_Application_applicantname_reason=new ArrayList<String>();

	////RTIStatusRejectionLetter/////
	public static ArrayList<String>pdf_rtiapplicationnumber_list=new ArrayList<String>();
	public static ArrayList<String>pdf_rtiapplicationdate_list=new ArrayList<String>();
	public static ArrayList<String>pdf_rtiapplicationaddress_list=new ArrayList<String>();
	public static ArrayList<String>pdf_applicantname_list=new ArrayList<String>();

	//////forwardlist////
	public static ArrayList<String>pdffwrtinumber_list=new ArrayList<String>();
	public static ArrayList<String>pdffwdate_list=new ArrayList<String>();
	public static ArrayList<String>pdffwaddress1_list=new ArrayList<String>();
	public static ArrayList<String>pdf_fwmobile1_list=new ArrayList<String>();
	public static ArrayList<String>pdf_fwemail2_list=new ArrayList<String>();

	/////////////piorejecction/////////////////////////////
	public static ArrayList<String>pdf_piorejaddress_list=new ArrayList<String>();
	public static ArrayList<String>pdf_rejapplicantname_fetched_list=new ArrayList<String>();
	public static ArrayList<String>pdf_rejnortinumber_list=new ArrayList<String>();
	public static ArrayList<String>pdf_rejdate_list=new ArrayList<String>();
	public static ArrayList<String>pdf_rejapaname1_list=new ArrayList<String>();
	public static ArrayList<String>pdf_rejapaaddress1_list=new ArrayList<String>();
	public static ArrayList<String>pdf_subrule8_list=new ArrayList<String>();
	public static ArrayList<String>pdf_subrule9_list=new ArrayList<String>();


	///////////////////////loi report////////////////////////////////
	public static ArrayList<String>pdf_loi_RTI_no=new ArrayList<String>();
	public static ArrayList<String>pdf_loi_Date=new ArrayList<String>();
	public static ArrayList<String>pdf_loi_Applicantname=new ArrayList<String>();
	public static ArrayList<String>pdf_loi_addresspincode=new ArrayList<String>();
	public static ArrayList<String>pdf_loi_lidate=new ArrayList<String>();
	public static ArrayList<String>pdf_loi_pdfname=new ArrayList<String>();
	public static ArrayList<String>pdf_loi_totalpayable=new ArrayList<String>();
	public static ArrayList<String>pdf_loi_loinumber=new ArrayList<String>();
	//////////////////////////LOIFORM//////////////////////////
	public static ArrayList<String>loi_form_RTINO_list=new ArrayList<String>();
	public static ArrayList<String>loi_form_LOINO_list=new ArrayList<String>();
	public static ArrayList<String>loi_form_applicantname_list=new ArrayList<String>();
	public static ArrayList<String>loi_form_loiamount_list=new ArrayList<String>();




	///////////////////////////////////////////////////////////////////
	public int rwcountRPSPaymentHistory;

	public static String license_printing_servicename;
	// protected String entcompname;

	/* Assertions */
	public static SoftAssert softAssert = new SoftAssert();

	public static BndCustomErrorMessages bndRegErrorMsg = new BndCustomErrorMessages();
	public static RTIAppCustomErrorMessages rtiAppErrorMsg = new RTIAppCustomErrorMessages();
	public static PropTaxCustomErrorMessages propTaxErrorMsg = new PropTaxCustomErrorMessages();
	public static TPCustomErrorMessages tpErrorMsg = new TPCustomErrorMessages();
	public static MarketCustomErrorMessages marketErrorMsg = new MarketCustomErrorMessages();
	public static LegalCustomErrorMessages lglErrorMsg = new LegalCustomErrorMessages();
	public static EIPAppCustomErrorMessages eipApp_m_errors=new EIPAppCustomErrorMessages();

	public static RentAndLeaseCustomErrorMessages rnlErrorMsg = new RentAndLeaseCustomErrorMessages();
	public static AdvertisingAndHoardingCustomErrorMessages adhErrorMsg = new AdvertisingAndHoardingCustomErrorMessages();
	public static HashMap<String, String> map = new HashMap<String, String>();

	//////////////////////////
	public static Map<String, ArrayList<String>> hmapforformula = new LinkedHashMap<String, ArrayList<String>>();
	public static Map<String, ArrayList<String>> hmap = new LinkedHashMap<String, ArrayList<String>>();
	//public static Map<String, String> hmapid = new LinkedHashMap<String, String>();
	public static Map<String, ArrayList<String>> hmapid = new LinkedHashMap<String, ArrayList<String>>();
	////////////////////////
	public static HashMap<String, ArrayList<String>> hscenariomap = new LinkedHashMap<String, ArrayList<String>>();


	public static AUDITCustomErrorMessages adtErrorMsg = new AUDITCustomErrorMessages();
	//public static MarketCustomErrorMessages marketErrorMsg = new MarketCustomErrorMessages();


	//jyoti's arraylist

	/*
	public static ArrayList<String> TCtenantName= new ArrayList<String>();
	public static ArrayList<String> TCtenantNo= new ArrayList<String>();
	public static ArrayList<String> TCcontractNo= new ArrayList<String>();
	public static ArrayList<String> TCcontractFromDate= new ArrayList<String>();
	public static ArrayList<String> TCcontractToDate= new ArrayList<String>();
	public static ArrayList<String> TCcontractAmount= new ArrayList<String>();
	public static ArrayList<String> TCdiscountAmount= new ArrayList<String>();
	public static ArrayList<String> TCtotalPayableAmount= new ArrayList<String>();
	public static ArrayList<String> PaymentHistory = new ArrayList<String>();
	 */

	//Array List for Market
	//public static ArrayList<String> HolderName = new ArrayList<String>();
	public static ArrayList<String> NewTradeHolderList = new ArrayList<String>();
	public static ArrayList<String> ScrutinyNewHolderList = new ArrayList<String>();

	//public static ArrayList<String> HolderBusinessName = new ArrayList<String>();
	public static ArrayList<String> NewTradeHolderBusiness = new ArrayList<String>();
	public static ArrayList<String> ScrutinyBusinessName = new ArrayList<String>();

	public static ArrayList<String> HolderFeeType = new ArrayList<String>();
	public static ArrayList<String> NewTradeFeeTypeList = new ArrayList<String>();
	public static ArrayList<String> ScrutinyFeeTypeList = new ArrayList<String>();

	public static ArrayList<String> HolderFees = new ArrayList<String>();
	public static ArrayList<String> NewTradeFeesList = new ArrayList<String>();
	public static ArrayList<String> ScrutinyFeesList = new ArrayList<String>();

	public static ArrayList<String> HolderTotalAmount = new ArrayList<String>();
	public static ArrayList<String> NewTradeTotalAmountList = new ArrayList<String>();
	public static ArrayList<String> ScrutinyTotalAmountList = new ArrayList<String>();

	//public static ArrayList<String> ScrutinyLOIFeeTypeList = new ArrayList<String>();
	//public static ArrayList<String> ScrutinyLOIFeesList = new ArrayList<String>();
	//public static ArrayList<String> ScrutinyLOITotalAmountList = new ArrayList<String>();

	public static ArrayList<String> NewLOITradeHolderBusiness = new ArrayList<String>();
	public static ArrayList<String> ScrutinyLOIBusinessName = new ArrayList<String>();

	public static ArrayList<String> NewLOITradeHolderName = new ArrayList<String>();
	public static ArrayList<String> ScrutinyLOIHolderNameList = new ArrayList<String>();

	public static ArrayList<String> NewLOITradeFeeTypeList = new ArrayList<String>();
	public static ArrayList<String> ScrutinyLOIFeeTypeList = new ArrayList<String>();

	public static ArrayList<String> ChangeBusinessNameHolderName = new ArrayList<String>();
	public static ArrayList<String> ChangeBusinessOldBusinessName = new ArrayList<String>();
	public static ArrayList<String> ChangeBusinessNewBusinessName = new ArrayList<String>();

	public static ArrayList<String> ChangeinBusinessHolderList = new ArrayList<String>();
	public static ArrayList<String> ChangeinBusinessScrutinyHolderList = new ArrayList<String>();

	public static ArrayList<String> ChangeBusinessScrutinyOldBusinessName = new ArrayList<String>();
	public static ArrayList<String> ChangeBusinessScrutinyOldBusinessNameList = new ArrayList<String>();

	public static ArrayList<String> ChangeBusinessScrutinyNewBusinessName = new ArrayList<String>();
	public static ArrayList<String> ChangeBusinessScrutinyNewBusinessNameList = new ArrayList<String>();

	public static ArrayList<String> ChangeinBusinessLOIHolderList = new ArrayList<String>();
	public static ArrayList<String> ChangeinBusinessScrutinyLOIHolderList = new ArrayList<String>();

	public static ArrayList<String> ChangeinBusinessLOIoldBusinessnameList = new ArrayList<String>();
	public static ArrayList<String> ChangeBusinessScrutinyLOIOldBusinessNameList = new ArrayList<String>();

	public static ArrayList<String> TransferOtherModeHolderName = new ArrayList<String>();
	public static ArrayList<String> TransferOtherModeLicenseNo = new ArrayList<String>();
	public static ArrayList<String> TransferOtherModeBusinessName = new ArrayList<String>();

	public static ArrayList<String> TransferOtherModeLOILicenseList = new ArrayList<String>();
	public static ArrayList<String> TransferOtherModeScrutinyLOILicenseNoList = new ArrayList<String>();

	public static ArrayList<String> TransferOtherModeLOIHoldername = new ArrayList<String>();
	public static ArrayList<String> TransferOtherModeScrutinyLOIHolderNameList = new ArrayList<String>();

	public static ArrayList<String> IntiLetter_ChngBusiNm_AppHolderNm = new ArrayList<String>();
	public static ArrayList<String> IntiLetter_ChngBusiNm_HolderNm = new ArrayList<String>();
	public static ArrayList<String> IntiLetter_ChngBusiNm_HolderNm_Assert = new ArrayList<String>();

	public static ArrayList<String> IntiLetter_ChngBusiNameTotalAmount = new ArrayList<String>();
	public static ArrayList<String> IntiLetter_ChngBusiNameTotalAmount_Assert = new ArrayList<String>();

	public static ArrayList<String> Application_NewLicense_HolderNm = new ArrayList<String>();
	public static ArrayList<String> Inti_NewLicense_HolderNm = new ArrayList<String>();

	public static ArrayList<String> IntiLetter_TOM_HolderName = new ArrayList<String>();

	public static ArrayList<String> IntiLetter_HolderNameList = new ArrayList<String>();
	public static ArrayList<String> TransferOtherModeScrutinyLOITotalFeesList = new ArrayList<String>();

	public static ArrayList<String> IntiLetter_TOM_TotalFees = new ArrayList<String>();
	public static ArrayList<String> IntiLetter_TotalAmountList = new ArrayList<String>();

	public static ArrayList<String> IntiLetter_NewTradeHolderName = new ArrayList<String>();
	public static ArrayList<String> ChangeinBusinessScrutinyLOITotalAmount = new ArrayList<String>();

	public static ArrayList<String> IntiLetter_ChngBusiNm_LOITotalAmount = new ArrayList<String>();

	public static ArrayList<String> Application_NewLicense_TotalAmount = new ArrayList<String>();
	public static ArrayList<String> Intimation_NewTrade_TotalAmountList = new ArrayList<String>();

	public static ArrayList<String> IntiLetter_Renew_HolderName = new ArrayList<String>();

	//pdf duplicate arraylist
	public static ArrayList<String> pdf_license_duplicate_licenseNum= new ArrayList<String>();
	public static ArrayList<String> pdf_license_duplicate_holdername= new ArrayList<String>();
	public static ArrayList<String> pdf_license_duplicate_businessname= new ArrayList<String>();
	public static ArrayList<String> pdf_license_tlun_jowner= new ArrayList<String>();
	public static ArrayList<String> pdf_fromdate= new ArrayList<String>();
	public static ArrayList<String> pdf_todate= new ArrayList<String>();
	public static ArrayList<String> pdf_tlun_holdername= new ArrayList<String>();
	public static ArrayList<String> pdf_tlun_businessname= new ArrayList<String>();
	public static ArrayList<String> pdf_tlun_tfees= new ArrayList<String>();
	public static ArrayList<String> application_number_list= new ArrayList<String>();
	public static ArrayList<String> pdf_tlun_jowner_pdf= new ArrayList<String>();
	public static ArrayList<String> pdf_tlun_address= new ArrayList<String>();

	//inspection form
	/*public static ArrayList<String> inspection_licenseno_list= new ArrayList<String>();
		public static ArrayList<String> inspection_date_list= new ArrayList<String>();
		public static ArrayList<String> inspection_Remarks_list= new ArrayList<String>();
		public static ArrayList<String> Inspection_inspecter_name_list= new ArrayList<String>();
		public static ArrayList<String> Inspection_presense_name_list= new ArrayList<String>();*/




	//inspection report
	/*public static ArrayList<String> report_inspection_licenseno_list= new ArrayList<String>();
		public static ArrayList<String> report_inspection_date_list= new ArrayList<String>();
		public static ArrayList<String> report_inspection_Remarks_list= new ArrayList<String>();*/

	public static ArrayList<String> pdf_renewalfromdate= new ArrayList<String>();
	public static ArrayList<String> pdf_renewal_todate= new ArrayList<String>();
	public static ArrayList<String> pdf_renewal_holdername= new ArrayList<String>();
	public static ArrayList<String> pdf_renwal_businessname= new ArrayList<String>();
	public static ArrayList<String> pdf_renewal_tfees= new ArrayList<String>();

	public static ArrayList<String> pdf_renewal_jowner_pdf= new ArrayList<String>();
	public static ArrayList<String> pdf_renewal_address= new ArrayList<String>();


	//Showcause
	public static ArrayList<String> showcause_report_address_list= new ArrayList<String>();
	//public static ArrayList<String> showcause_report_address_list= new ArrayList<String>();




	////////////////////////end of jyotis array list
	////////////////////////RTI Array LIST//////////////////////////
	public static ArrayList<String> RTI_applicant_name_list= new ArrayList<String>();
	public static ArrayList<String> permant_address_list= new ArrayList<String>();

	public static ArrayList<String> RTI_APLBPL_status_list= new ArrayList<String>();
	public static ArrayList<String> RTI_BPL_num_list= new ArrayList<String>();
	public static ArrayList<String> subScription_type_list= new ArrayList<String>();
	public static ArrayList<String> mobile_list= new ArrayList<String>();
	public static ArrayList<String> email_list= new ArrayList<String>();
	public static ArrayList<String> circle_list= new ArrayList<String>();
	public static ArrayList<String> RTI_deliveryMode_list= new ArrayList<String>();
	public static ArrayList<String> RTI_Application_list= new ArrayList<String>();
	public static ArrayList<String> RTI_firstappeal_Application_list= new ArrayList<String>();
	//Declaration related to the data driven
	public static String currentmethodname;
	public static int NumberOfColumns;
	public static int NoOfColumns;
	public static int NumOfColumnss;
	public static int CurrentinvoCount;
	public static int InvoCount;
	public static String CurrentMethodName;
	public static ArrayList<String> applicationNo = new ArrayList<String>();
	public static ArrayList<String> registraionNo = new ArrayList<String>();
	public static int tocolumn;
	public static int fromcolumn;
	public static String currenttestmethod;


	public static String actualKey;


	//Declaration related to the Error Handling


	//public static StringBuilder result = new StringBuilder();
	public static ArrayList<StringBuilder>  resultset = new ArrayList<StringBuilder>();
	public static StringBuilder resultskipped= new StringBuilder();
	public static String stackTrace;
	public static int lineNO;
	public static String classnamereport;
	public static boolean continueExec;
	public static boolean skippedtests;
	public static boolean inAtTest;
	Properties props= new Properties();

	public static String lgl_RefCaseNo;
	public static ArrayList<String> loiNumber = new ArrayList<String>();
	public static int NumberOfRowsforWriting;
	public static String scenariosheetpath;
	public static String path; 

	ISuite suite;
	public static String suitename;

	//RNL Commit variables 29082015
	public boolean RNLBOEServices = false;
	public boolean BOE_LOI_Collection = false;

	////scrutiny variable
	//////////////////
	public static int scrutinylevelcounter;

	public static String textid="";
	public static String imageid="";
	public static boolean docuploadboolean=false;

	//Declaration related to the patternSUGAMFORMPdf
	public static String noChngInAsmntAssessmentYear;
	public static String noChngInAsmntPropertyNumber;
	public static String noChngInAsmntRateableArea;
	public static String noChngInAsmntAnnualRentalValue;
	public static String noChngInAsmntTaxableVacantLandArea;
	public static String noChngInAsmntPropertyTax;
	public static String noChngInAsmntTotalRebate;
	public static String noChngInAsmntInterest;
	public static String noChngInAsmntServiceCharge;
	public static String noChngInAsmntNetTaxPayable;


	//TownPlanning Arraylist for Application for Land Dev

	public static ArrayList<String> ApplicantMobileNo= new ArrayList<String>();
	public static ArrayList<String> ApplicantEmailId= new ArrayList<String>();
	public static ArrayList<String> ownertitle= new ArrayList<String>();
	public static ArrayList<String> ownerfname= new ArrayList<String>();
	public static ArrayList<String> ownermname= new ArrayList<String>();
	public static ArrayList<String> ownerlname= new ArrayList<String>();


	public static ArrayList<String> Addline1= new ArrayList<String>();
	public static ArrayList<String> Addline2= new ArrayList<String>();
	public static ArrayList<String> Pincode= new ArrayList<String>();

	////////////////////////////////////	swapnil//eip////////////////////////////////
	public static List<WebElement> links= new ArrayList<WebElement>(); 
	public static List<WebElement> ides= new ArrayList<WebElement>();
	public static List<WebElement> ides1= new ArrayList<WebElement>();
	public static List<WebElement> ides2= new ArrayList<WebElement>();
	public static ArrayList<String> imgdoclist= new ArrayList<String>();

	public static List<WebElement> faqids=new ArrayList<WebElement>();	
	public static ArrayList<String> faqqustionanswerlist= new ArrayList<String>();
	public static ArrayList<String> helpdocumentname= new ArrayList<String>();
	public static ArrayList<Integer> helpdocumentsize= new ArrayList<Integer>();

	////////////////////////////end swapnil////////////////////////////////////////
	public static ArrayList<String> tpAddOwnertitle= new ArrayList<String>();
	public static ArrayList<String> tpAddOwnerFName= new ArrayList<String>();
	public static ArrayList<String> tpAddOwnerMName= new ArrayList<String>();
	public static ArrayList<String> tpAddOwnerlName= new ArrayList<String>();

	public static ArrayList<String> AddOwnername= new ArrayList<String>();
	public static ArrayList<String> AddEmailId= new ArrayList<String>();
	public static ArrayList<String> AddMobileNo= new ArrayList<String>();

	public static ArrayList<String> ward= new ArrayList<String>();
	public static ArrayList<String> PlotKhasraNo= new ArrayList<String>(); 
	public static ArrayList<String> MaujaNo= new ArrayList<String>();
	public static ArrayList<String> KhataNo= new ArrayList<String>();
	public static ArrayList<String> PlotNoMSP = new ArrayList<String>();	
	public static ArrayList<String> HoldingNo= new ArrayList<String>();	
	public static ArrayList<String> TojiNo= new ArrayList<String>();
	public static ArrayList<String> Village= new ArrayList<String>();


	public static ArrayList<String> CoveredAreainTotalHect= new ArrayList<String>();
	public static ArrayList<String> CoveredAreainTotalft2= new ArrayList<String>();
	//rnl cheklist
	public static ArrayList<Integer> chekindexes= new ArrayList<Integer>();
	public static ArrayList<Integer> scrutinyindexes= new ArrayList<Integer>();
	
//end of rnl cheklist
	
	public static ArrayList<String> ParkingAreamt2= new ArrayList<String>();
	public static ArrayList<String> ParkingAreaft2= new ArrayList<String>();
	public static ArrayList<String> wideningofroadmt= new ArrayList<String>();
	public static ArrayList<String> wideningofroadft= new ArrayList<String>(); 
	public static ArrayList<String> ProjectName= new ArrayList<String>();
	public static ArrayList<String> SchemeName= new ArrayList<String>();

	//Application for Building permission


	public static ArrayList<String> NatureofConstr= new ArrayList<String>();
	public static ArrayList<String> CaseID= new ArrayList<String>();
	public static ArrayList<String> PrincipalUsageType= new ArrayList<String>();	
	public static ArrayList<String> CategoryofBuilding= new ArrayList<String>();	
	public static ArrayList<String> Area= new ArrayList<String>();


	public static ArrayList<String> PlotWidthavg= new ArrayList<String>();
	public static ArrayList<String> PlotWidthavgft= new ArrayList<String>();
	public static ArrayList<String> HeightofBuilding= new ArrayList<String>();
	public static ArrayList<String> HeightofBuildingft= new ArrayList<String>();
	public static ArrayList<String> PlotLengthAvg= new ArrayList<String>();
	public static ArrayList<String> PlotLengthAvgft= new ArrayList<String>();

	public static ArrayList<String> PlotArea= new ArrayList<String>();
	public static ArrayList<String> bprPlotAreaFt= new ArrayList<String>();

	public static ArrayList<String> HoldingRights= new ArrayList<String>(); 
	public static ArrayList<String> GISRoadId = new ArrayList<String>();

	public static ArrayList<String> ExistingRoadWidth= new ArrayList<String>();
	public static ArrayList<String> ExistingRoadWidthft= new ArrayList<String>();
	public static ArrayList<String> RWidthasperMasterPlan = new ArrayList<String>();
	public static ArrayList<String> RWidthasperMasterPlanft = new ArrayList<String>();


	public static ArrayList<String> Arealeftchkbox = new ArrayList<String>();
	public static ArrayList<String> ArealeftforRoadWidening = new ArrayList<String>();
	public static ArrayList<String> ArealeftforRoadWideningft = new ArrayList<String>();


	public static ArrayList<String> ParkingAreachkbox = new ArrayList<String>();
	public static ArrayList<String> ParkingArea = new ArrayList<String>();
	public static ArrayList<String> ParkingAreaft = new ArrayList<String>();


	public static ArrayList<String> IsAirZonechkbox = new ArrayList<String>();
	public static ArrayList<String> DistfrmnearestrunwayEnd = new ArrayList<String>();
	public static ArrayList<String> DistofInnerboundary  = new ArrayList<String>();


	public static ArrayList<String> NoofFloors = new ArrayList<String>();
	public static ArrayList<String> BuiltupareaSqMt2 = new ArrayList<String>();
	public static ArrayList<String> BuiltupareaSqft2 = new ArrayList<String>();

	public static ArrayList<String> FARAppliedfor  = new ArrayList<String>();
	public static ArrayList<String> PermissibleFAR  = new ArrayList<String>();

	public static ArrayList<String> LocPlotNorth = new ArrayList<String>();
	public static ArrayList<String> LocPlotEast = new ArrayList<String>();
	public static ArrayList<String> LocPlotSouth = new ArrayList<String>();
	public static ArrayList<String> LocPlotWest = new ArrayList<String>();


	public static ArrayList<String> AvgDepthofPlot = new ArrayList<String>();
	public static ArrayList<String> AvgDepthofPlotft = new ArrayList<String>();
	public static ArrayList<String> AvgWidthofPlot = new ArrayList<String>();
	public static ArrayList<String> AvgWidthofPlotft = new ArrayList<String>();

	public static ArrayList<String> FrontSetBackApplied = new ArrayList<String>();
	public static ArrayList<String> FrontSetBackAppliedft = new ArrayList<String>();
	public static ArrayList<String> FrontSetBackPermissible = new ArrayList<String>();
	public static ArrayList<String> FrontSetBackPermissibleft = new ArrayList<String>();


	public static ArrayList<String> LeftSetBackApplied = new ArrayList<String>();
	public static ArrayList<String> LeftSetBackAppliedft = new ArrayList<String>();
	public static ArrayList<String> LeftSetBackPermissible = new ArrayList<String>();
	public static ArrayList<String> LeftSetBackPermissibleft = new ArrayList<String>();

	public static ArrayList<String> RearSetBackApplied = new ArrayList<String>();
	public static ArrayList<String> RearSetBackAppliedft = new ArrayList<String>();
	public static ArrayList<String> RearSetBackPermissible = new ArrayList<String>();
	public static ArrayList<String> RearSetBackPermissibleft = new ArrayList<String>();

	public static ArrayList<String> RightSetBackApplied = new ArrayList<String>();
	public static ArrayList<String> RightSetBackAppliedft = new ArrayList<String>();
	public static ArrayList<String> RightSetBackPermissible = new ArrayList<String>();
	public static ArrayList<String> RightSetBackPermissibleft = new ArrayList<String>();


	public static ArrayList<String> Bpr_Applicanttitle= new ArrayList<String>();
	public static ArrayList<String> Bpr_ApplicantFName= new ArrayList<String>();
	public static ArrayList<String> Bpr_ApplicantMName= new ArrayList<String>();
	public static ArrayList<String> Bpr_ApplicantLName= new ArrayList<String>();
	public static ArrayList<String> Bpr_ApplicantMobileNo= new ArrayList<String>();
	public static ArrayList<String> Bpr_ApplicantEmailId= new ArrayList<String>();
	public static ArrayList<String> Bpr_Ownertitle= new ArrayList<String>();
	public static ArrayList<String> Bpr_OwnerFName= new ArrayList<String>();
	public static ArrayList<String> Bpr_OwnerMName= new ArrayList<String>();
	public static ArrayList<String> Bpr_OwnerLName= new ArrayList<String>();
	public static ArrayList<String> Bpr_Nameofowner= new ArrayList<String>();	
	public static ArrayList<String> Bpr_OwnerMobileNo= new ArrayList<String>();	
	public static ArrayList<String> Bpr_OwnerEmailId= new ArrayList<String>();
	public static ArrayList<String> Bpr_Addline1= new ArrayList<String>();
	public static ArrayList<String> Bpr_Addline2= new ArrayList<String>();
	public static ArrayList<String> Bpr_Pincode= new ArrayList<String>();
	public static ArrayList<String> Bpr_ward= new ArrayList<String>();
	public static ArrayList<String> Bpr_PlotKhasraNo= new ArrayList<String>(); 
	public static ArrayList<String> Bpr_MaujaNo= new ArrayList<String>();
	public static ArrayList<String> Bpr_KhataNo= new ArrayList<String>();
	public static ArrayList<String> Bpr_PlotNoMSP = new ArrayList<String>();	
	public static ArrayList<String> Bpr_HoldingNo= new ArrayList<String>();	
	public static ArrayList<String> Bpr_TojiNo= new ArrayList<String>();
	public static ArrayList<String> Bpr_Village= new ArrayList<String>();


	public static ArrayList<String> Bpr_CoveredAreainTotalHect= new ArrayList<String>();
	public static ArrayList<String> Bpr_CoveredAreainTotalft2= new ArrayList<String>();


	public static ArrayList<String> Bpr_UsageType= new ArrayList<String>();
	public static ArrayList<String> Bpr_CoveredAreainHect= new ArrayList<String>();
	public static ArrayList<String> Bpr_CoveredAreainft2= new ArrayList<String>();

	public static ArrayList<String> Bpr_ParkingAreamt2= new ArrayList<String>();
	public static ArrayList<String> Bpr_ParkingAreaft2= new ArrayList<String>();
	public static ArrayList<String> Bpr_wideningofroadmt= new ArrayList<String>();
	public static ArrayList<String> Bpr_wideningofroadft= new ArrayList<String>(); 

	public static ArrayList<String> Bpr_TechPerName= new ArrayList<String>();
	public static ArrayList<String> Bpr_TechPerType= new ArrayList<String>(); 


	public static ArrayList<String> Bpr_ProjectName= new ArrayList<String>();
	public static ArrayList<String> Bpr_SchemeName= new ArrayList<String>();

	public static ArrayList<String> TechPerName= new ArrayList<String>();
	public static ArrayList<String> TechPerType= new ArrayList<String>();
	public static ArrayList<String> UsageType= new ArrayList<String>();
	public static ArrayList<String> CoveredAreainHect= new ArrayList<String>();
	public static ArrayList<String> CoveredAreainft2= new ArrayList<String>();
	public static ArrayList<String> OwnerMobileNo= new ArrayList<String>();	
	public static ArrayList<String> OwnerEmailId= new ArrayList<String>();
	public static ArrayList<String> AlD_Nameofowner= new ArrayList<String>();	

	public static ArrayList<String> AppName = new ArrayList<String>(); 
	public static ArrayList<String> RTI_temp_response= new ArrayList<String>();	
	public static ArrayList<String> RTI_temp_firstappeal= new ArrayList<String>();
	public static boolean TrutiPatraGenerated;
	public static boolean HearingDated;
	////////////////////////////////piyush RNL & townplanning assertionarraylist/////////////////////

	//RNL Arraylist Tenant master Entry Piyush 06 jan 2017 
	//Town Planning scrutiny Arraylist


	public static ArrayList<String> clv_ApplicantName = new ArrayList<String>();
	public static ArrayList<String> clv_ApplicanyionNo = new ArrayList<String>();
	public static ArrayList<String> clv_ServicenameName = new ArrayList<String>();
	public static ArrayList<String> clv_ApplicantionDate = new ArrayList<String>();




	public static ArrayList<String> SCRLD_appmbno = new ArrayList<String>();
	public static ArrayList<String> SCRLD_appEmailId= new ArrayList<String>();
	public static ArrayList<String> SCRLD_OwnMbNo= new ArrayList<String>();
	public static ArrayList<String> SCRLD_OwnEmailId= new ArrayList<String>();
	public static ArrayList<String> SCRLD_AddLine1= new ArrayList<String>();
	public static ArrayList<String> SCRLD_AddLine2= new ArrayList<String>();
	public static ArrayList<String> SCRLD_Pincode= new ArrayList<String>();
	public static ArrayList<String> SCRLD_Ward= new ArrayList<String>();
	public static ArrayList<String> SCRLD_PlotKhasaraNo= new ArrayList<String>();
	public static ArrayList<String> SCRLD_PlotMSP= new ArrayList<String>();
	public static ArrayList<String> SCRLD_MaujaNo = new ArrayList<String>();
	public static ArrayList<String> SCRLD_HoldingNo= new ArrayList<String>();
	public static ArrayList<String> SCRLD_KhataNo= new ArrayList<String>();
	public static ArrayList<String> SCRLD_Tojino= new ArrayList<String>();
	public static ArrayList<String> SCRLD_Village= new ArrayList<String>();
	public static ArrayList<String> SCRLD_TotalCoverHect= new ArrayList<String>();
	public static ArrayList<String> SCRLD_TotalCoverft2= new ArrayList<String>();
	public static ArrayList<String> SCRLD_ParkingAreamt2= new ArrayList<String>();
	public static ArrayList<String> SCRLD_ParkingAreaft2= new ArrayList<String>();
	public static ArrayList<String> SCRLD_wideningofroadmt= new ArrayList<String>();
	public static ArrayList<String> SCRLD_wideningofroadft= new ArrayList<String>();
	public static ArrayList<String> SCRLD_projectName= new ArrayList<String>();
	public static ArrayList<String> SCRLD_SchemeName= new ArrayList<String>();
	public static ArrayList<String> TechPerName1= new ArrayList<String>();
	public static ArrayList<String> TechPerType1= new ArrayList<String>();
	public static ArrayList<String> UsageType1= new ArrayList<String>();
	public static ArrayList<String> CoveredAreainHect1= new ArrayList<String>();
	public static ArrayList<String> CoveredAreainft21= new ArrayList<String>();
	public static ArrayList<String> OwnerMobileNo1= new ArrayList<String>();	
	public static ArrayList<String> OwnerEmailId1= new ArrayList<String>();
	public static ArrayList<String> Nameofowner1= new ArrayList<String>();	
	public static ArrayList<String> FloorNo1= new ArrayList<String>();	
	public static ArrayList<String> FloorUsageType1= new ArrayList<String>();
	public static ArrayList<String> FloorBuiltupArea1= new ArrayList<String>();	

	//Town Planning Building Permissionscrutiny Arraylist


	public static String RNLserviceName;
	public static ArrayList<String> Applicationdate = new ArrayList<String>();
	public static ArrayList<String> ServiceNameforTownPlanning =new ArrayList<String>();

	public static ArrayList<String> Applictitle= new ArrayList<String>();
	public static ArrayList<String> Applicfname= new ArrayList<String>();
	public static ArrayList<String> Applicmname= new ArrayList<String>();
	public static ArrayList<String> Appliclname= new ArrayList<String>();
	public static ArrayList<String> ApplicantFullname = new ArrayList<String>();
	public static ArrayList<String> ApplicationdateinYear = new ArrayList<String>();
	public static ArrayList<String> AppfullnmWdTitle =new ArrayList<String>();

	public static ArrayList<String> AddOwnerFullname= new ArrayList<String>();
	public static ArrayList<String> ApplicationNoforLandDev =new ArrayList<String>();
	public static ArrayList<String> Bpr_PlotWidthavg= new ArrayList<String>();
	public static ArrayList<String> Bpr_OwnerFullName= new ArrayList<String>();


	public static ArrayList<String> Nameofowner= new ArrayList<String>();
	public static ArrayList<String> BPR_NoofFloors = new ArrayList<String>();
	public static ArrayList<String> BPR_FloorNo = new ArrayList<String>();
	public static ArrayList<String> BPR_Builtuparea = new ArrayList<String>();
	public static ArrayList<String> BPR_UsageType = new ArrayList<String>();
	public static ArrayList<String> Bpr_AdditionalownerName = new ArrayList<String>();
	public static ArrayList<String> Bpr_AdditionalownermbNo = new ArrayList<String>();
	public static ArrayList<String> Bpr_AdditionalownerEmail = new ArrayList<String>();
	public static ArrayList<String> BPR_BuiltupareaSqMt2 = new ArrayList<String>();
	public static ArrayList<String> BPR_BuiltupareaSqft2 = new ArrayList<String>();

	public static ArrayList<String> AddownerFullname= new ArrayList<String>();

	public static ArrayList<String> RNL_Array_ServiceName= new ArrayList<String>();
	public static ArrayList<String> Ass_ULBName = new ArrayList<String>();
	public static ArrayList<String> TCtenantName= new ArrayList<String>();
	public static ArrayList<String> TCtenantNo= new ArrayList<String>();
	public static ArrayList<String> TCcontractNo= new ArrayList<String>();
	public static ArrayList<String> TCcontractFromDate= new ArrayList<String>();
	public static ArrayList<String> TCcontractToDate= new ArrayList<String>();
	public static ArrayList<String> TCcontractAmount= new ArrayList<String>();
	public static ArrayList<String> TCdiscountAmount= new ArrayList<String>();
	public static ArrayList<String> TCtotalPayableAmount= new ArrayList<String>();

	//RNL Land Estate Master entry Arraylist form by Piyush 06/01/2017
	public static ArrayList<String> LEM_EstateType= new ArrayList<String>();
	public static ArrayList<String> LEM_EstateSubType= new ArrayList<String>();
	public static ArrayList<String> LEM_Ward= new ArrayList<String>();
	public static ArrayList<String> LEM_EstateDesc = new ArrayList<String>();
	public static ArrayList<String> LEM_Road= new ArrayList<String>();
	public static ArrayList<String> LEM_RegistrationDate= new ArrayList<String>();
	public static ArrayList<String> LEM_AddressLineOne= new ArrayList<String>();
	public static ArrayList<String> LEM_AddressLineTwo = new ArrayList<String>();
	public static ArrayList<String> LEMPincode= new ArrayList<String>();
	public static ArrayList<String> LEM_EstateCategory= new ArrayList<String>();
	public static ArrayList<String> LEM_EstateHandoverDate= new ArrayList<String>();
	public static ArrayList<String> LEM_EstateLength = new ArrayList<String>();
	public static ArrayList<String> LEM_EstateWidth= new ArrayList<String>();
	public static ArrayList<String> LEM_EstateHeight = new ArrayList<String>();
	public static ArrayList<String> LEM_EstateArea= new ArrayList<String>();
	public static ArrayList<String> LEM_FloorNo = new ArrayList<String>();
	public static ArrayList<String> LEM_EstateNoSearch = new ArrayList<String>();

	//Booking Of Estate Form Arraylist 06 Jan 2017
	public static ArrayList<String> RPS_ChallNo= new ArrayList<String>();//For Rent Payment schedule 
	public static ArrayList<String> ChallannoRNL = new ArrayList<String>();
	public static ArrayList<String> challanno = new ArrayList<String>();
	public static ArrayList<String> Rnl_EstateDesciption = new ArrayList<String>();
	public static ArrayList<String> ServiceNameRNL= new ArrayList<String>();
	public static ArrayList<String> Rnl_ApplicantTitle= new ArrayList<String>();
	public static ArrayList<String> Rnl_ApplicantFname= new ArrayList<String>();
	public static ArrayList<String> Rnl_ApplicantMname= new ArrayList<String>();
	public static ArrayList<String> Rnl_ApplicantLname = new ArrayList<String>();
	public static ArrayList<String> Rnl_ApplicantFullname= new ArrayList<String>();
	public static ArrayList<String> Rnl_ApplicantMbNo = new ArrayList<String>();
	public static ArrayList<String> Rnl_ApplicantEmail= new ArrayList<String>();
	public static ArrayList<String> Rnl_ApplicantAddOne = new ArrayList<String>();
	public static ArrayList<String> Rnl_ApplicantAddTwo = new ArrayList<String>();
	public static ArrayList<String> Rnl_ApplicantPinCode= new ArrayList<String>();
	public static ArrayList<String> Rnl_ApplicantRemarks = new ArrayList<String>();
	public static ArrayList<String> Rnl_BookingfromDate= new ArrayList<String>();
	public static ArrayList<String> Rnl_BookingtoDate = new ArrayList<String>();
	public static ArrayList<String> Rnl_EstateName = new ArrayList<String>();
	public static ArrayList<String> Rnl_EstateAddress= new ArrayList<String>();
	public static ArrayList<String> Rnl_EstateNo = new ArrayList<String>();
	public static ArrayList<String> Rnl_EstateType= new ArrayList<String>();
	public static ArrayList<String> Rnl_BookingDate= new ArrayList<String>();

	//Tenant Master entry Arraylist  06 Jan 2017
	public static ArrayList<String> TM_TenantNo = new ArrayList<String>();
	public static ArrayList<String> TM_RegDate = new ArrayList<String>();
	public static ArrayList<String> TM_TenantTitle= new ArrayList<String>();
	public static ArrayList<String> TM_TenantName = new ArrayList<String>();
	public static ArrayList<String> TM_TenantAddress= new ArrayList<String>();
	public static ArrayList<String> TM_TenantContactNo = new ArrayList<String>();
	public static ArrayList<String> TM_TenantMobileNo = new ArrayList<String>();
	public static ArrayList<String> TM_TenantMail = new ArrayList<String>();
	public static ArrayList<String> TM_TenantPanNo = new ArrayList<String>();
	public static ArrayList<String> TM_TenantAadharNo = new ArrayList<String>();
	public static ArrayList<String> TM_AddTenantTitle = new ArrayList<String>();
	public static ArrayList<String> TM_AddTenantName = new ArrayList<String>();
	public static ArrayList<String> TM_AddTenantAddress = new ArrayList<String>();
	public static ArrayList<String> TM_AddTenantContactNo = new ArrayList<String>();
	public static ArrayList<String> TM_AddTenantMobileNo = new ArrayList<String>();
	public static ArrayList<String> TM_AddTenantMail = new ArrayList<String>();
	public static ArrayList<String> TM_AddTenantPanNo = new ArrayList<String>();
	public static ArrayList<String> TM_AddTenantAadharNo = new ArrayList<String>();
	public static ArrayList<String> TM_BillGenerationdate = new ArrayList<String>();
	public static ArrayList<String> TM_BillGenerationRemarks = new ArrayList<String>();

	//Contract Details  06 Jan 2017
	public static ArrayList<String> TC_TenderRef = new ArrayList<String>();
	public static ArrayList<String> TC_TenderDate = new ArrayList<String>();
	public static ArrayList<String> TC_ContractDate = new ArrayList<String>();
	public static ArrayList<String> TC_ContractFromDate= new ArrayList<String>();
	public static ArrayList<String> TC_ContractToDate = new ArrayList<String>();
	public static ArrayList<String> TC_ContractAmount = new ArrayList<String>();
	public static ArrayList<String> TC_DiscountAmount = new ArrayList<String>();
	public static ArrayList<String> TC_PayableAmount = new ArrayList<String>();
	public static ArrayList<String> TC_RoundOffAmount = new ArrayList<String>();
	public static ArrayList<String> TC_ContractRemarks = new ArrayList<String>();
	public static ArrayList<String> TC_TaxAmount = new ArrayList<String>();
	public static ArrayList<String> TC_DiscountDescription = new ArrayList<String>();
	public static ArrayList<String> TC_PaymentSceduleMethod = new ArrayList<String>();
	public static ArrayList<String> TC_PaymentScheduleAmount = new ArrayList<String>();
	public static ArrayList<String> TC_PayAmount = new ArrayList<String>();
	public static ArrayList<String> TC_PaymentDueDate = new ArrayList<String>();
	public static ArrayList<String> TC_EstateCode = new ArrayList<String>();
	public static ArrayList<String> DepositNum = new ArrayList<String>();
	public static ArrayList<String> DepositDate = new ArrayList<String>();
	public static ArrayList<String> SecurityDepositType = new ArrayList<String>();
	public static ArrayList<String> ChequeDDDate = new ArrayList<String>();
	public static ArrayList<String> DepositAmount = new ArrayList<String>();
	public static ArrayList<String> DraweeBankName = new ArrayList<String>();
	public static ArrayList<String> MicrNum = new ArrayList<String>();
	public static ArrayList<String> ChequeDDNo = new ArrayList<String>();
	public static ArrayList<String> SecurityDepositamt = new ArrayList<String>();
	public static ArrayList<String> DepositRemarks = new ArrayList<String>();

	//Contract no.Bill generation Arraylist  06 Jan 2017
	public static ArrayList<String> CBG_BillDateOfAppctn = new ArrayList<String>();
	public static ArrayList<String> CBG_RemarksOnAppctn = new ArrayList<String>();

	//Scrutiny View Form Approval details 

	/*public static ArrayList<String> Rnl_EstateDesciption = new ArrayList<String>();
		public static ArrayList<String> ServiceNameRNL= new ArrayList<String>();*/
	public static ArrayList<String> Scr_Rnl_ApplicantTitle= new ArrayList<String>();
	public static ArrayList<String> Scr_Rnl_ApplicantFname= new ArrayList<String>();
	public static ArrayList<String> Scr_Rnl_ApplicantMname= new ArrayList<String>();
	public static ArrayList<String> Scr_Rnl_ApplicantLname = new ArrayList<String>();
	public static ArrayList<String> Scr_Rnl_ApplicantFullname= new ArrayList<String>();
	public static ArrayList<String> Scr_Rnl_ApplicantMbNo = new ArrayList<String>();
	public static ArrayList<String> Scr_Rnl_ApplicantEmail= new ArrayList<String>();
	public static ArrayList<String> Scr_Rnl_ApplicantAddOne = new ArrayList<String>();
	public static ArrayList<String> Scr_Rnl_ApplicantAddTwo = new ArrayList<String>();
	public static ArrayList<String> Scr_Rnl_ApplicantPinCode= new ArrayList<String>();
	public static ArrayList<String> Scr_Rnl_ApplicantRemarks = new ArrayList<String>();
	public static ArrayList<String> Scr_Rnl_BookingfromDate= new ArrayList<String>();
	public static ArrayList<String> Scr_Rnl_BookingtoDate = new ArrayList<String>();
	public static ArrayList<String> Scr_Rnl_EstateName = new ArrayList<String>();
	public static ArrayList<String> Scr_Rnl_EstateAddress= new ArrayList<String>();
	public static ArrayList<String> Scr_Rnl_EstateNo = new ArrayList<String>();
	public static ArrayList<String> Scr_Rnl_EstateType= new ArrayList<String>();
	public static ArrayList<String> Scr_Rnl_BookingDate= new ArrayList<String>();
	public static ArrayList<String> Scr_Rnl_RenewalDate = new ArrayList<String>();
	public static ArrayList<String> Scr_Rnl_ContractNo= new ArrayList<String>();
	public static ArrayList<String> Scr_TC_ContractAmount = new ArrayList<String>();
	public static ArrayList<String> Scr_TC_DiscountAmount = new ArrayList<String>();
	public static ArrayList<String> Scr_TC_PayableAmount = new ArrayList<String>();
	public static ArrayList<String> Scr_TC_RoundOffAmount = new ArrayList<String>();
	public static ArrayList<String> Scr_TC_ContractRemarks = new ArrayList<String>();
	public static ArrayList<String> Scr_TC_TaxAmount = new ArrayList<String>();
	public static ArrayList<String> Scr_TC_DiscountDescription = new ArrayList<String>();
	public static ArrayList<String> Scr_PaymentScheduleAmount = new ArrayList<String>();
	public static ArrayList<String> Scr_EstateCode = new ArrayList<String>();
	public static ArrayList<String> Scr_DepositNum = new ArrayList<String>();
	public static ArrayList<String> Scr_DepositDate = new ArrayList<String>();
	public static ArrayList<String> Scr_SecurityDepositType = new ArrayList<String>();
	public static ArrayList<String> Scr_ChequeDDDate = new ArrayList<String>();
	public static ArrayList<String> Scr_DepositAmount = new ArrayList<String>();
	public static ArrayList<String> Scr_DraweeBankName = new ArrayList<String>();
	public static ArrayList<String> Scr_MicrNum = new ArrayList<String>();
	public static ArrayList<String> Scr_ChequeDDNo = new ArrayList<String>();
	public static ArrayList<String> Scr_SecurityDepositamt = new ArrayList<String>();
	public static ArrayList<String> Scr_DepositRemarks = new ArrayList<String>();

	public static ArrayList<String> BE_Scr_rnlApptitle = new ArrayList<String>();
	public static ArrayList<String> BE_scr_rnlEstateFname = new ArrayList<String>();
	public static ArrayList<String> BE_scr_rnlEstateMname = new ArrayList<String>();
	public static ArrayList<String> BE_scr_rnlEstateLname = new ArrayList<String>();
	public static ArrayList<String> BE_scr_rnlEstateLFullname = new ArrayList<String>();
	public static ArrayList<String> BE_scr_rnlEstateMobNo = new ArrayList<String>();
	public static ArrayList<String> BE_scr_rnlEstateEmail = new ArrayList<String>();
	public static ArrayList<String> BE_scr_rnlEstateRemarks = new ArrayList<String>();

	public static ArrayList<String> BE_scr_rnlEstateFromDate = new ArrayList<String>();
	public static ArrayList<String> BE_scr_rnlEstateToDate = new ArrayList<String>();
	public static ArrayList<String> ApplicationNoRNL = new ArrayList<String>();

	//PDF Patterns arraylist on 11 jan 2017 piyush RNL
	public static ArrayList<String> pdf_ApplicationNo= new ArrayList<String>();
	public static ArrayList<String> pdf_ApplicantName= new ArrayList<String>();
	public static ArrayList<String> pdf_Address= new ArrayList<String>();
	public static ArrayList<String> pdf_TenantName= new ArrayList<String>();
	public static ArrayList<String> pdf_TenantNo= new ArrayList<String>();
	public static ArrayList<String> pdf_ContractNo= new ArrayList<String>();
	public static ArrayList<String> pdf_Applicationdate= new ArrayList<String>();
	public static ArrayList<String> pdf_ServiceName= new ArrayList<String>();
	public static ArrayList<String> pdf_ULBName= new ArrayList<String>();
	public static ArrayList<String> pdf_LetterSubject= new ArrayList<String>();
	public static ArrayList<String> pdf_Remarks= new ArrayList<String>();
	public static ArrayList<String> pdf_LOINo= new ArrayList<String>();
	public static ArrayList<String> pdf_LOIDate= new ArrayList<String>();
	public static ArrayList<String> pdf_TotalPaybleAmt= new ArrayList<String>();
	public static ArrayList<String> pdf_WithinNoOfDays= new ArrayList<String>();
	public static ArrayList<String> pdf_Feetype1= new ArrayList<String>();
	public static ArrayList<String> pdf_Feetype2= new ArrayList<String>();
	public static ArrayList<String> pdf_Feetype3= new ArrayList<String>();
	public static ArrayList<String> pdf_Feetype4= new ArrayList<String>();
	public static ArrayList<String> pdf_Feetypeamt1= new ArrayList<String>();
	public static ArrayList<String> pdf_Feetypeamt2= new ArrayList<String>();
	public static ArrayList<String> pdf_Feetypeamt3= new ArrayList<String>();
	public static ArrayList<String> pdf_Feetypeamt4= new ArrayList<String>();
	public static ArrayList<String> pdf_BillNo= new ArrayList<String>();
	public static ArrayList<String> pdf_BillDate= new ArrayList<String>();
	public static ArrayList<String> pdf_BillingFromDate= new ArrayList<String>();
	public static ArrayList<String> pdf_BillingToDate= new ArrayList<String>();
	public static ArrayList<String> pdf_BillingDueDate= new ArrayList<String>();
	public static ArrayList<String> pdf_DiscountAmt= new ArrayList<String>();
	public static ArrayList<String> pdf_DueDate= new ArrayList<String>();
	public static ArrayList<String> pdf_ToDate= new ArrayList<String>();
	public static ArrayList<String> pdf_FromDate= new ArrayList<String>();
	public static ArrayList<String> pdf_ContractFromDate= new ArrayList<String>();
	public static ArrayList<String> pdf_ContractToDate= new ArrayList<String>();
	public static ArrayList<String> pdf_ContractgenDate= new ArrayList<String>();
	public static ArrayList<String> pdf_ContractAmt= new ArrayList<String>();
	public static ArrayList<String> pdf_ContractPaymentFrequency= new ArrayList<String>();
	public static ArrayList<String> pdf_EstateNo= new ArrayList<String>();
	public static ArrayList<String> pdf_EstateDescription= new ArrayList<String>();
	public static ArrayList<String> pdf_Estateaddress= new ArrayList<String>();
	public static ArrayList<String> pdf_ReceiptNo= new ArrayList<String>();
	public static ArrayList<String> pdf_ReceiptDate= new ArrayList<String>();
	public static ArrayList<String> pdf_RejectionNo= new ArrayList<String>();
	public static ArrayList<String> pdf_RejectionRemarks= new ArrayList<String>();
	public static ArrayList<String> pdf_RejectionDate= new ArrayList<String>();
	public static ArrayList<String> pdf_PaymentMode= new ArrayList<String>();


	//Town Planning Building Permissionscrutiny Arraylist
	public static ArrayList<String> SCRBPR_SchemeName = new ArrayList<String>();
	public static ArrayList<String> SCRBPR_projectName= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_Village= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_TojiNo= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_KhataNo= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_HoldingNo= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_MaujaNo= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_PlotMSP= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_PlotKhasaraNo= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_Ward= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_Pincode = new ArrayList<String>();
	public static ArrayList<String> SCRBPR_AddLine2= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_AddLine1= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_OwnEmailId= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_OwnMbNo= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_appEmailId= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_appmbno= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_RightSetBackPermissibleft= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_RightSetBackPermissible= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_RightSetBackAppliedft= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_RightSetBackApplied= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_RearSetBackPermissible= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_RearSetBackPermissibleft = new ArrayList<String>();
	public static ArrayList<String> SCRBPR_RearSetBackAppliedft= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_RearSetBackApplied= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_LeftSetBackPermissibleft= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_LeftSetBackPermissible = new ArrayList<String>();
	public static ArrayList<String> SCRBPR_LeftSetBackAppliedft= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_LeftSetBackApplied= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_FrontSetBackPermissibleft= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_FrontSetBackPermissible= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_FrontSetBackAppliedft= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_FrontSetBackApplied= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_AvgWidthofPlotft= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_AvgWidthofPlot= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_AvgDepthofPlotft= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_AvgDepthofPlot= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_LocPlotSouth= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_LocPlotWest = new ArrayList<String>();
	public static ArrayList<String> SCRBPR_LocPlotEast= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_LocPlotNorth= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_PermissibleFAR= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_FARAppliedfor = new ArrayList<String>();
	public static ArrayList<String> SCRBPR_BuiltupareaSqft2= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_BuiltupareaSqMt2= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_NoOfFloor= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_NatureofConstr= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_CaseNo= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_PrinUsagetype= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_CategoryofBuilding= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_Areatype= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_PlotWidth= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_PlotWidthft= new ArrayList<String>();
	public static ArrayList<String> scrutiny_LocPlotSouth= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_BuildingHeight = new ArrayList<String>();
	public static ArrayList<String> SCRBPR_BuildingHeightft= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_PlotLength= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_PlotLengthInFt= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_PlotArea = new ArrayList<String>();
	public static ArrayList<String> SCRBPR_PlotAreaft= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_HoldingRights= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_GISRoad= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_ExtRoadWidth= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_ExtRoadWidthft= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_WidthAsprMastrPln= new ArrayList<String>();
	public static ArrayList<String> SCRBPR_WidthAsprMastrPlnft= new ArrayList<String>();

	public static ArrayList<String> Bpr_AppfullnmWdTitle= new ArrayList<String>();
	public static ArrayList<String> Bpr_CaseNo= new ArrayList<String>();
	public static ArrayList<String> Bpr_Scr_floorType= new ArrayList<String>();	
	public static ArrayList<String> Bpr_Scr_FloorUsagetype= new ArrayList<String>();
	public static ArrayList<String> Bpr_Scr_FloorBuiltupArea= new ArrayList<String>();


	//Occupancy Certificate Arraylist////////////////
	public static ArrayList<String> OC_Caseid= new ArrayList<String>();
	public static ArrayList<String> OC_AppNo= new ArrayList<String>();
	public static ArrayList<String> Oc_EmailID = new ArrayList<String>();
	public static ArrayList<String> Oc_CompletionDate= new ArrayList<String>();
	public static ArrayList<String> OC_NatureOfConstruction= new ArrayList<String>();
	public static ArrayList<String> Oc_PrincipleUsagetYpe = new ArrayList<String>();
	public static ArrayList<String> Oc_ApplicantNamewdoutTitle = new ArrayList<String>();
	public static ArrayList<String> Oc_Ownername = new ArrayList<String>();
	public static ArrayList<String> Oc_MobileNo= new ArrayList<String>();
	public static ArrayList<String> OC_Applicationdate= new ArrayList<String>();
	public static ArrayList<String> Oc_BuildingApprovalDate = new ArrayList<String>();
	public static ArrayList<String> Oc_BuildingApprovalValidityDate = new ArrayList<String>();

	//Occupancy Scrutiny form arraylist
	public static ArrayList<String> SCr_OC_Caseid= new ArrayList<String>();
	public static ArrayList<String> SCr_OC_AppNo= new ArrayList<String>();
	public static ArrayList<String> SCr_Oc_EmailID = new ArrayList<String>();
	public static ArrayList<String> SCr_Oc_CompletionDate= new ArrayList<String>();
	public static ArrayList<String> Scr_OC_NatureOfConstruction= new ArrayList<String>();
	public static ArrayList<String> SCr_Oc_PrincipleUsagetYpe = new ArrayList<String>();
	public static ArrayList<String> SCr_Oc_MobileNo= new ArrayList<String>();
	public static ArrayList<String> SCr_OC_Applicationdate= new ArrayList<String>();
	public static ArrayList<String> Scr_OC_ApplicantTitle= new ArrayList<String>();
	public static ArrayList<String> SCr_Oc_ApplicantFName = new ArrayList<String>();
	public static ArrayList<String> SCr_Oc_ApplicantMName= new ArrayList<String>();
	public static ArrayList<String> SCr_OC_ApplicantLName= new ArrayList<String>();
	public static ArrayList<String> Scr_OC_OwnerTitle= new ArrayList<String>();
	public static ArrayList<String> SCr_Oc_OwnerFName = new ArrayList<String>();
	public static ArrayList<String> SCr_Oc_OwnerMName= new ArrayList<String>();
	public static ArrayList<String> SCr_OC_OwnerLName= new ArrayList<String>();
	public static ArrayList<String> SCr_Oc_BuildingApprovalDate = new ArrayList<String>();
	public static ArrayList<String> SCr_Oc_BuildingApprovalValidityDate = new ArrayList<String>();
	public static ArrayList<String> SCr_Oc_ComplitiondateDate = new ArrayList<String>();


	//Scrutiny Arraylist //Common for all assertion ArrayList by Piysuh 07 jan 2016
	public static ArrayList<String> Scrutiny_ApplicantName= new ArrayList<String>();
	public static ArrayList<String> Scrutiny_ApplicantionNo= new ArrayList<String>();
	public static ArrayList<String> Scrutiny_Servicename= new ArrayList<String>();
	public static ArrayList<String> Scrutiny_LoiDate= new ArrayList<String>();
	public static ArrayList<String> Scrutiny_LoiNo= new ArrayList<String>();
	public static ArrayList<String> Scrutiny_Totalamt= new ArrayList<String>();
	public static ArrayList<String> Scrutiny_LoiRemarks= new ArrayList<String>();
	public static ArrayList<String> SCR_SiteInspectorName= new ArrayList<String>();
	public static ArrayList<String> SCR_SiteInspectiondate = new ArrayList<String>();
	public static ArrayList<String> SCR_SiteInspectorRemarks = new ArrayList<String>();
	public static ArrayList<String> SCR_RejectionRemarks = new ArrayList<String>();
	public static ArrayList<String> SCR_LOIGenLOIRemarks = new ArrayList<String>();
	public static ArrayList<String> SCR_SiteInspectEmpName = new ArrayList<String>();
	public static ArrayList<String> SCR_SiteInspectionEmpDate = new ArrayList<String>();
	public static ArrayList<String> SCR_TpAssitantEnggName = new ArrayList<String>();
	public static ArrayList<String> SCR_TrutiPatraRemarks = new ArrayList<String>();
	public static ArrayList<String> TrutiHearingScheduleDate = new ArrayList<String>();
	public static ArrayList<String> TrutiViewdocRemarks = new ArrayList<String>();
	public static ArrayList<String> HearingReasonRemarks = new ArrayList<String>();
	public static ArrayList<String> HearingScheduleSchedule = new ArrayList<String>();

	//LOI Payment Forms Arraylist Piyush 11 jan 2017 For Payment Receipt Assertion 
	public static ArrayList<String> Loi_TotalPaybleamt= new ArrayList<String>();
	public static ArrayList<String> Loi_LoiNo= new ArrayList<String>();
	public static ArrayList<String> Loi_Loidate= new ArrayList<String>();
	public static ArrayList<String> Loi_ModeofPayment= new ArrayList<String>();
	public static ArrayList<String> Loi_BankName= new ArrayList<String>();
	public static ArrayList<String> Loi_AccountNo= new ArrayList<String>();
	public static ArrayList<String> Loi_chqnoDdNo= new ArrayList<String>();
	public static ArrayList<String> Loi_chqDdDate= new ArrayList<String>();

	//Querry Letter ArrayList
	public static ArrayList<String> Querry_ApplicantName= new ArrayList<String>();
	public static ArrayList<String> Querry_ulb= new ArrayList<String>();
	public static ArrayList<String> Querry_letter= new ArrayList<String>();
	public static ArrayList<String> Querry_ApplicationNo= new ArrayList<String>();
	public static ArrayList<String> Querry_Applicationdate= new ArrayList<String>();
	public static ArrayList<String> Querry_Remark= new ArrayList<String>();
	public static ArrayList<String> Querry_Remark2a= new ArrayList<String>();
	public static ArrayList<String> Querry_sub= new ArrayList<String>();


	//Intimation Letter for Town Planning Module Common Arrylist
	public static ArrayList<String> inti_Subject1= new ArrayList<String>();
	public static ArrayList<String> inti_ApplNo1= new ArrayList<String>();
	public static ArrayList<String> inti_Appdatedate1= new ArrayList<String>();
	public static ArrayList<String> inti_ApplicantName1= new ArrayList<String>();
	public static ArrayList<String> inti_Applprntdate1= new ArrayList<String>();
	public static ArrayList<String> inti_TotalFee1= new ArrayList<String>();
	public static ArrayList<String> inti_PaidDays1= new ArrayList<String>();

	// Approval Letter - Land Development Permit Piyush 03-Jan-2017 Arraylist
	public static ArrayList<String> LDAL_APPLNO= new ArrayList<String>();
	//public static ArrayList<String> LDAL_APPROVALDATE= new ArrayList<String>();
	public static ArrayList<String> LDAL_OWNERNAME= new ArrayList<String>();
	public static ArrayList<String> LDAL_APPLIDATE= new ArrayList<String>();
	public static ArrayList<String> LDAP_WARD= new ArrayList<String>();
	public static ArrayList<String> LDAP_KHATANO= new ArrayList<String>();
	public static ArrayList<String> LDAP_HOLDINGNO= new ArrayList<String>();
	public static ArrayList<String> LDAP_MSPKHASARA= new ArrayList<String>();
	public static ArrayList<String> LDAP_PLOTMSP= new ArrayList<String>();
	//public static ArrayList<String> LDAP_APPROVAL= new ArrayList<String>();
	public static ArrayList<String> LDAP_VILLAGE= new ArrayList<String>();

	//TP Payment Details
	public static ArrayList<String> PaymentMode= new ArrayList<String>();
	public static ArrayList<String> PayAt= new ArrayList<String>();
	public static ArrayList<String> ChallanVerfiDate= new ArrayList<String>();
	public static ArrayList<String> BankName= new ArrayList<String>();
	public static ArrayList<String> chqDdNo= new ArrayList<String>();
	public static ArrayList<String> ChqDDDate= new ArrayList<String>();
	public static ArrayList<String> AccountNo= new ArrayList<String>();
	//public static ArrayList<String> BPAL_ApprovalNo1= new ArrayList<String>();
	//public static ArrayList<String> BPAL_VILLAGE= new ArrayList<String>();*/


	//Building Permit Approval letter arraylist
	public static ArrayList<String> BPAL_APPLICATIONNO= new ArrayList<String>();
	//public static ArrayList<String> BPAL_APPROVALDATE= new ArrayList<String>();
	public static ArrayList<String> BPAL_OWNERNAME= new ArrayList<String>();
	public static ArrayList<String> BPAL_APPLIDATE= new ArrayList<String>();
	public static ArrayList<String> BPAL_WARD= new ArrayList<String>();
	public static ArrayList<String> BPAL_KHATANO= new ArrayList<String>();
	public static ArrayList<String> BPAL_HOLDINGNO= new ArrayList<String>();
	public static ArrayList<String> BPAL_PLOTNOMSP= new ArrayList<String>();
	public static ArrayList<String> BPAL_VILLAGE= new ArrayList<String>();


	//  Occupancy Certificate approval letter Arraylist Report
	public static ArrayList<String> OCAL_APPLNO= new ArrayList<String>();
	public static ArrayList<String> OCAL_OWNERNAME= new ArrayList<String>();
	public static ArrayList<String> OCAL_ADDITIONALOWNERNAME= new ArrayList<String>();
	public static ArrayList<String> OCAL_WARD= new ArrayList<String>();
	public static ArrayList<String> OCAL_PLOTNOCS= new ArrayList<String>();
	public static ArrayList<String> OCAL_PLOTNOMSP= new ArrayList<String>();
	public static ArrayList<String> OCAL_KHATANO= new ArrayList<String>();
	public static ArrayList<String> OCAL_HOLDINGNO= new ArrayList<String>();
	public static ArrayList<String> OCAL_VILLAGE= new ArrayList<String>();
	public static ArrayList<String> OCAL_ApprovalNo1= new ArrayList<String>();
	public static ArrayList<String> OCAL_AppliDate1= new ArrayList<String>();
	public static ArrayList<String> OCAL_TechPerName1= new ArrayList<String>();

	//public static String Tploi_LOINo;
	//public static String Tploi_LOIDate;

	public static ArrayList<String> SCRBPR_ConstructionCost= new ArrayList<String>();
	public static ArrayList<String> Bpr_ConstructionCost= new ArrayList<String>();
	public static ArrayList<String> APPlicantNameForAssertion= new ArrayList<String>();
	public static ArrayList<String> TPLOI_AppliNo= new ArrayList<String>();
	public static ArrayList<String> TPLOI_ReceiptDate= new ArrayList<String>();
	public static ArrayList<String> TPLOI_ReceiptNo= new ArrayList<String>();
	public static ArrayList<String> TPLOI_ReceivedFromName= new ArrayList<String>();
	public static ArrayList<String> TPLOI_TotalRcvdAmt1= new ArrayList<String>();
	public static ArrayList<String> TPLOI_LOINo= new ArrayList<String>();
	public static ArrayList<String> TPLOI_LOIDate= new ArrayList<String>();
	public static ArrayList<String> TPLOI_PaymentMode= new ArrayList<String>();
	public static ArrayList<String> TPLOI_ModeDate= new ArrayList<String>();
	public static ArrayList<String> TPLOI_ModeNo= new ArrayList<String>();
	public static ArrayList<String> TPLOI_ModeBankName= new ArrayList<String>();

	//Truti Patra Pdf Arraylist
	public static ArrayList<String> Truti_ApplNo= new ArrayList<String>();
	public static ArrayList<String> Truti_AppDate= new ArrayList<String>();

	// land development site inspection letter Arraylist
	public static ArrayList<String> SIL_appname= new ArrayList<String>();
	public static ArrayList<String> SIL_serviceName= new ArrayList<String>();
	public static ArrayList<String> SIL_ApplicationNo= new ArrayList<String>();
	public static ArrayList<String> SIL_Inspectiondate= new ArrayList<String>();
	public static ArrayList<String> SIL_ulbname= new ArrayList<String>();
	public static ArrayList<String> SIL_officer= new ArrayList<String>();
	public static ArrayList<String> SIL_ward1= new ArrayList<String>();
	public static ArrayList<String> SIL_plotkhasaranocs= new ArrayList<String>();
	public static ArrayList<String> SIL_maujano= new ArrayList<String>();
	public static ArrayList<String> SIL_KhataNo= new ArrayList<String>();
	public static ArrayList<String> SIL_PlotNoMSP= new ArrayList<String>();
	public static ArrayList<String> SIL_TojiNo= new ArrayList<String>();
	public static ArrayList<String> SIL_Locality= new ArrayList<String>();
	public static ArrayList<String> SIL_HoldingNo= new ArrayList<String>();


	//Letter of Intimation Letter for pdf pattern arraylist
	public static ArrayList<String> BP_Intim_AppNo= new ArrayList<String>();
	public static ArrayList<String> BP_Intim_AppDate= new ArrayList<String>();
	public static ArrayList<String> BP_Intim_AppName= new ArrayList<String>();
	public static ArrayList<String> BP_Intim_Print_date= new ArrayList<String>();
	public static ArrayList<String> BP_Intim_TotalPaybleamt= new ArrayList<String>();
	//public static ArrayList<String> BP_Intim_AppName= new ArrayList<String>();

	////////////////////////////////END piyush RNL & townplanning assertionarraylist/////////////////////
	///Ritesh audit variable////
	// string array for audit module
	////////////////////////////////////ADH  module//////////////////////////////////////////

	//ADH_Arrylist_Application form of Displyed of Hoarding
	public static ArrayList<String> adh_HoardingName= new ArrayList<String>();
	public static ArrayList<String> adh_HoardingAddress = new ArrayList<String>();
	public static ArrayList<String> adh_HoardingType = new ArrayList<String>();
	public static ArrayList<String> adh_HoardingNo = new ArrayList<String>();
	public static ArrayList<String> adh_ApplicantTitle= new ArrayList<String>();
	public static ArrayList<String> adh_ApplicantFname= new ArrayList<String>();
	public static ArrayList<String> adh_ApplicantMname= new ArrayList<String>();
	public static ArrayList<String> adh_ApplicantLname= new ArrayList<String>();
	public static ArrayList<String> adh_ApplicantFullname = new ArrayList<String>();
	public static ArrayList<String> adh_ApplicantMbNo= new ArrayList<String>();
	public static ArrayList<String> adh_ApplicantEmail= new ArrayList<String>();
	public static ArrayList<String> adh_ApplicantAddOne= new ArrayList<String>();
	public static ArrayList<String> adh_ApplicantAddTwo= new ArrayList<String>();
	public static ArrayList<String> adh_ApplicantPinCode= new ArrayList<String>();
	public static ArrayList<String> adh_ApplicantRemarks= new ArrayList<String>();
	public static ArrayList<String> adh_BookingfromDate= new ArrayList<String>();
	public static ArrayList<String> adh_BookingtoDate= new ArrayList<String>();

	//ADH_Arrylist_Application form of Setup of Hoarding for display of Advertisement
	public static ArrayList<String> suoh_ApplicantTitle= new ArrayList<String>();
	public static ArrayList<String> suoh_ApplicantFname= new ArrayList<String>();
	public static ArrayList<String> suoh_ApplicantMname= new ArrayList<String>();
	public static ArrayList<String> suoh_ApplicantLname= new ArrayList<String>();
	public static ArrayList<String> suoh_ApplicantFullname = new ArrayList<String>();
	public static ArrayList<String> suoh_ApplicantMbNo= new ArrayList<String>();
	public static ArrayList<String> suoh_ApplicantEmail= new ArrayList<String>();
	public static ArrayList<String> suoh_ApplicantAddOne= new ArrayList<String>();
	public static ArrayList<String> suoh_ApplicantAddTwo= new ArrayList<String>();
	public static ArrayList<String> suoh_ApplicantPinCode= new ArrayList<String>();
	public static ArrayList<String> suoh_ApplicantRemarks= new ArrayList<String>();
	public static ArrayList<String> suoh_BookingfromDate= new ArrayList<String>();
	public static ArrayList<String> suoh_BookingtoDate= new ArrayList<String>();
	public static ArrayList<String> suoh_Ward= new ArrayList<String>();
	public static ArrayList<String> suoh_RoadType= new ArrayList<String>();
	public static ArrayList<String> suoh_EastLandmark= new ArrayList<String>();
	public static ArrayList<String> suoh_WestLandmark= new ArrayList<String>();
	public static ArrayList<String> suoh_SouthLandmark= new ArrayList<String>();
	public static ArrayList<String> suoh_NorthLandmark= new ArrayList<String>();
	public static ArrayList<String> suoh_GISNo= new ArrayList<String>();
	public static ArrayList<String> suoh_PlotNo= new ArrayList<String>();
	public static ArrayList<String> suoh_HouseNo= new ArrayList<String>();
	public static ArrayList<String> suoh_Area= new ArrayList<String>();
	public static ArrayList<String> suoh_SocietyName= new ArrayList<String>();
	public static ArrayList<String> suoh_RoadName= new ArrayList<String>();
	public static ArrayList<String> suoh_BuildingName= new ArrayList<String>();
	public static ArrayList<String> suoh_NewPinCode= new ArrayList<String>();
	public static ArrayList<String> suoh_HoardingDescription= new ArrayList<String>();
	public static ArrayList<String> suoh_HoardingType= new ArrayList<String>();
	public static ArrayList<String> suoh_HoardingType1= new ArrayList<String>();
	public static ArrayList<String> suoh_Length= new ArrayList<String>();
	public static ArrayList<String> suoh_Breadth= new ArrayList<String>();
	public static ArrayList<String> suoh_Elevation= new ArrayList<String>();
	public static ArrayList<String> suoh_GISNOPOPUP= new ArrayList<String>();
	public static ArrayList<String> suoh_AmountPopUp= new ArrayList<String>();
	public static ArrayList<String> suoh_LocationAddress= new ArrayList<String>();
	public static ArrayList<String> suoh_LocationCode= new ArrayList<String>();

	//ADH_Arrylist_Application form of Renewal of Advt Contract
	public static ArrayList<String> adh_rc_HoardingName= new ArrayList<String>();
	public static ArrayList<String> adh_rc_HoardingAddress = new ArrayList<String>();
	public static ArrayList<String> adh_rc_HoardingType = new ArrayList<String>();
	public static ArrayList<String> adh_rc_HoardingNo = new ArrayList<String>();
	public static ArrayList<String> adh_rc_ApplicantTitle= new ArrayList<String>();
	public static ArrayList<String> adh_rc_ApplicantFname= new ArrayList<String>();
	public static ArrayList<String> adh_rc_ApplicantMname= new ArrayList<String>();
	public static ArrayList<String> adh_rc_ApplicantLname= new ArrayList<String>();
	public static ArrayList<String> adh_rc_ApplicantFullname = new ArrayList<String>();
	public static ArrayList<String> adh_rc_ApplicantMbNo= new ArrayList<String>();
	public static ArrayList<String> adh_rc_ApplicantEmail= new ArrayList<String>();
	public static ArrayList<String> adh_rc_ApplicantAddOne= new ArrayList<String>();
	public static ArrayList<String> adh_rc_ApplicantAddTwo= new ArrayList<String>();
	public static ArrayList<String> adh_rc_ApplicantPinCode= new ArrayList<String>();
	public static ArrayList<String> adh_rc_ApplicantRemarks= new ArrayList<String>();
	public static ArrayList<String> adh_rc_BookingfromDate= new ArrayList<String>();
	public static ArrayList<String> adh_rc_BookingtoDate= new ArrayList<String>();
	public static ArrayList<String> adh_rc_RenewalDate = new ArrayList<String>();


	/////////////////////////////////////END ADH  module//////////////////////////////////////////


	public static String[] HODReply1;
	public static String[] HODReply2;
	public static String[] HODReply3;
	public static String[] AuditorReply;
	////end of audit //////////

	
	
	
//	@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@PT VARIBLES@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
////Tax Calculator Assertion List by Jyoti @30-03-2017

	public static ArrayList<String> TCaddedTypeOfUseArray = new ArrayList<>();
	public static ArrayList<String> TCaddedConstructTypeArray = new ArrayList<>();
	public static ArrayList<String> TCcalculatedRatableArArray = new ArrayList<>();
	public static ArrayList<String> TCcalculatedAnnulRateblValArray = new ArrayList<>();
	public static ArrayList<String> TCcalculatedAnnulTaxArray = new ArrayList<>();
	
	public static ArrayList<String> TCaddTypeOfUseArray = new ArrayList<>();
	public static ArrayList<String> TCaddConstructTypeArray = new ArrayList<>();
	public static ArrayList<String> TCaddBuildupArArray = new ArrayList<>();
	public static ArrayList<String> TCaddUsageFactorArray = new ArrayList<>();
	public static ArrayList<String> TCaddOccFacArray = new ArrayList<>();
	
	public static ArrayList<String> TCcalcRatableArArray = new ArrayList<>();
	public static ArrayList<String> TCcalcAnnulRateblValArray = new ArrayList<>();
	public static ArrayList<String> TCcalcAnnulTaxArray = new ArrayList<>();
	public static ArrayList<String> TCcalcUnitRateArray = new ArrayList<>();
	public static ArrayList<String> TCcalcRateOfTaxArray = new ArrayList<>();
	public static ArrayList<String> TCUsageTypeList = new ArrayList<>();
	public static ArrayList<String> TCconstructionClassList = new ArrayList<>();
	public static ArrayList<String> TCBuiltupAreaList = new ArrayList<>();
	public static ArrayList<String> TCOccupancyFactorList = new ArrayList<>();
	public static ArrayList<String> TCaddedUsageFactorRESArray = new ArrayList<>();
	public static ArrayList<String> TCaddedUsageFactorNRArray = new ArrayList<>();
	public static ArrayList<String> TCalculatedAnnulTaxArray = new ArrayList<>();
	
	public static ArrayList<String> totARVList = new ArrayList<>();
	public static ArrayList<String> totHoldingTaxList = new ArrayList<>();
	
	public static ArrayList<String> TCcalculatedARVResArray = new ArrayList<>();
	public static ArrayList<String> TCcalculatedARVnonResArray = new ArrayList<>();
	public static ArrayList<String> TC_CalculatedTotARVList = new ArrayList<>();
	

	//// Bill Printing Assertion List by Jyoti @23-03-2017

	public static ArrayList<String> sas_TotNetPayableAmtList = new ArrayList<>();
	public static ArrayList<String> billPrint_totPayAmtList = new ArrayList<>();
	public static ArrayList<String> propNumberList = new ArrayList<>();
	public static ArrayList<String> sasRebateList = new ArrayList<>();
	public static ArrayList<String> billPrint_TotalRebateAmtList = new ArrayList<>();
	public static ArrayList<String> sas_WaterTaxList = new ArrayList<>();
	public static ArrayList<String> billPrint_TotalWaterTaxAmtList = new ArrayList<>();
	public static ArrayList<String> sasInterestList = new ArrayList<>();
	public static ArrayList<String> SAS_InterestList = new ArrayList<>();
	public static ArrayList<String> billPrint_TotIntAmtList = new ArrayList<>();
	//public static ArrayList<String> sas_TotNetPayableAmtList = new ArrayList<>();
	//public static ArrayList<String> sas_TotNetPayableAmtList = new ArrayList<>();
	//public static ArrayList<String> sas_TotNetPayableAmtList = new ArrayList<>();
	//public static ArrayList<String> sas_TotNetPayableAmtList = new ArrayList<>();
	//public static ArrayList<String> sas_TotNetPayableAmtList = new ArrayList<>();



	/// Transfer Objection ArrayList by Jyoti
	public static ArrayList<String> trfObj_AppNoList = new ArrayList<String>();
	public static ArrayList<String> trfObj_PropNoList = new ArrayList<String>();
	public static ArrayList<String> trfObj_OwnerNmList = new ArrayList<String>();
	public static ArrayList<String> trfObj_TransfereeAddrList = new ArrayList<String>();
	public static ArrayList<String> trfInsp_AppNoList = new ArrayList<String>();
	public static ArrayList<String> trfInsp_PropNoList = new ArrayList<String>();
	public static ArrayList<String> trfInsp_OwnerNmList = new ArrayList<String>();
	public static ArrayList<String> trfObj_OwnerAddrList = new ArrayList<String>();
	public static ArrayList<String> trfObj_FirstNmList = new ArrayList<String>();
	public static ArrayList<String> trfObj_MiddleNmList = new ArrayList<String>();
	public static ArrayList<String> trfObj_LastNmList = new ArrayList<String>();
	public static ArrayList<String> trfObj_MatterList = new ArrayList<String>();
	public static ArrayList<String> trfObj_DateList = new ArrayList<String>();
	public static ArrayList<String> inspector_NameList = new ArrayList<String>();
	public static ArrayList<String> Obj_NoList = new ArrayList<String>();
	public static ArrayList<String> Obj_MatterList = new ArrayList<String>();
	public static ArrayList<String> Insp_RemarksList = new ArrayList<String>();
	public static ArrayList<String> Insp_DateList = new ArrayList<String>();
	public static ArrayList<String> trfInsp_TransfereeAddrList = new ArrayList<String>();
	public static ArrayList<String> trfHDE_AppNoList = new ArrayList<String>();
	public static ArrayList<String> trfHDE_PropNoList = new ArrayList<String>();
	public static ArrayList<String> trfHDE_OwnerNmList = new ArrayList<String>();
	public static ArrayList<String> trfHDE_TransfereeAddressList = new ArrayList<String>();
	public static ArrayList<String> trfHDE_HearingDateList = new ArrayList<String>();

	////////////// Objection Array List Start - Jyoti

	public static ArrayList<String> objHLP_OwnerAddressList = new ArrayList<String>();
	public static ArrayList<String> objHLP_OwnrNmList = new ArrayList<String>();
	public static ArrayList<String> objHLP_PropNoList = new ArrayList<String>();
	public static ArrayList<String> objHLP_ObjNoList = new ArrayList<String>();
	public static ArrayList<String> objHLP_ObjDateList = new ArrayList<String>();
	public static ArrayList<String> Obj_Hearing_DateList = new ArrayList<String>();
	public static ArrayList<String> objHLP_ObjRemarksList = new ArrayList<String>();
	public static ArrayList<String> objHLP_OwnrNameList = new ArrayList<String>();
	public static ArrayList<String> objHLP_OwnerAddrList = new ArrayList<String>();
	public static ArrayList<String> objHLP_ObjRemarkList = new ArrayList<String>();
	public static ArrayList<String> objHLP_ObjectionNoList = new ArrayList<String>();
	public static ArrayList<String> objHLP_ObjectionDateList = new ArrayList<String>();
	public static ArrayList<String> objHLP_PropertyNoList = new ArrayList<String>();
	public static ArrayList<String> objHO_OwnerNameList = new ArrayList<String>();
	public static ArrayList<String> objHO_ObjLetterNoList = new ArrayList<String>();
	public static ArrayList<String> objHO_ObjLetrDateList = new ArrayList<String>();
	public static ArrayList<String> objHO_PropNoList = new ArrayList<String>();
	public static ArrayList<String> objHO_PropertyNoList = new ArrayList<String>();
	public static ArrayList<String> objHO_OwnerNmList = new ArrayList<String>();
	public static ArrayList<String> objHO_ObjLtrNoList = new ArrayList<String>();
	public static ArrayList<String> objHO_ObjLtrDateList = new ArrayList<String>();


	////////Transfer Services Arraylist End - Jyoti

	//Property Tax Arraylist LOI pAyment Form By Piyush on 24 feb 2017
	public static ArrayList<String> loiPayment_OwnerName = new ArrayList<String>(); 
	public static ArrayList<String> loiPayment_NewOwnerName = new ArrayList<String>(); 
	public static ArrayList<String> loiPayment_PropNo = new ArrayList<String>(); 
	public static ArrayList<String> loiPayment_AmountPay = new ArrayList<String>(); 
	//Property Tax Arraylist LOI pAyment pdf Report recept Form By Piyush on 24 feb 2017
	public static ArrayList<String> pdf_loiReceipt_ReceivedFrom= new ArrayList<String>(); 
	public static ArrayList<String> pdf_loiReceipt_PropNo= new ArrayList<String>(); 
	public static ArrayList<String> pdf_loiReceipt_AppliNo= new ArrayList<String>(); 
	public static ArrayList<String> pdf_loiReceipt_TotalPaybleAmt= new ArrayList<String>(); 
	public static ArrayList<String> pdf_loiReceipt_PaymentMode= new ArrayList<String>(); 
	public static ArrayList<String> pdf_loiReceipt_UlbName= new ArrayList<String>(); 

	//////////////Bill Printing Array List Start - Jyoti

	public static ArrayList<String> billPrint_PropNoList = new ArrayList<String>();
	public static ArrayList<String> billPrint_BillNoList = new ArrayList<String>();
	public static ArrayList<String> billPrint_BillDateList = new ArrayList<String>();
	public static ArrayList<String> billPrint_UsageTypeList = new ArrayList<String>();
	public static ArrayList<String> billPrint_WardNoList = new ArrayList<String>();
	public static ArrayList<String> billPrint_NameList = new ArrayList<String>();
	public static ArrayList<String> billPrint_AddressList = new ArrayList<String>();
	public static ArrayList<String> billPrint_BillDueDateList = new ArrayList<String>();
	public static ArrayList<String> billPrint_HoldingTaxArrAmtList = new ArrayList<String>();
	public static ArrayList<String> billPrinting_InterestonArrAmountList = new ArrayList<String>();
	public static ArrayList<String> billPrint_WaterTaxAmtList = new ArrayList<String>();
	public static ArrayList<String> billPrint_RWHAmountList = new ArrayList<String>();
	public static ArrayList<String> billPrint_HTaxCurrAmtList = new ArrayList<String>();
	public static ArrayList<String> billPrinting_InterestonCurrAmountList = new ArrayList<String>();
	public static ArrayList<String> billPrint_CurrWaterTaxAmtList = new ArrayList<String>();
	public static ArrayList<String> billPrint_CurrRWHAmountList = new ArrayList<String>();
	public static ArrayList<String> billPrint_TotRWHAmountList = new ArrayList<String>();
	public static ArrayList<String> billPrint_CurrRebateAmtList = new ArrayList<String>();
	public static ArrayList<String> billPrint_TotRebateAmtList = new ArrayList<String>();
	public static ArrayList<String> billPrint_TotalHTaxAmtList = new ArrayList<String>();
	public static ArrayList<String> billPrinting_TotalInterestAmountList = new ArrayList<String>();
	public static ArrayList<String> billPrint_TotWaterTaxAmtList = new ArrayList<String>();
	public static ArrayList<String> billPrint_TotPayableAmtList = new ArrayList<String>();
	public static ArrayList<String> billPrint_ArrTotBillList = new ArrayList<String>();
	public static ArrayList<String> billPrint_CurrTotBillList = new ArrayList<String>();
	public static ArrayList<String> billPrint_TotBillAmtList = new ArrayList<String>();
	public static ArrayList<String> billPrint_PropertyNumberList = new ArrayList<String>();


	
	
	
////////Transfer Services Arraylist Start - 

public static ArrayList<String> Primary_OwnerNmaeList = new ArrayList<String>();
public static ArrayList<String> Ward_NomList = new ArrayList<String>();
public static ArrayList<String> Prop_NoList = new ArrayList<String>();
public static ArrayList<String> Name_TitleList = new ArrayList<String>();
public static ArrayList<String> First_NameList = new ArrayList<String>();
public static ArrayList<String> Full_NameList = new ArrayList<String>();
public static ArrayList<String> New_OwnerList = new ArrayList<String>();
public static ArrayList<String> NewOwnerNameAssertList = new ArrayList<String>();
public static ArrayList<String> Old_OwnerList = new ArrayList<String>();
public static ArrayList<String> Prop_NoListPdf = new ArrayList<String>();
public static ArrayList<String> Ward_NoList = new ArrayList<String>();
public static ArrayList<String> OldOwnerNameAssertList = new ArrayList<String>();
public static ArrayList<String> WardNoAssertList = new ArrayList<String>();
public static ArrayList<String> PropNoAssertList = new ArrayList<String>();
public static ArrayList<String> New_OMOwnerList = new ArrayList<String>();
public static ArrayList<String> Old_OMOwnerList = new ArrayList<String>();
public static ArrayList<String> Ward_NoListOM = new ArrayList<String>();
public static ArrayList<String> Prop_NoListOMPdf = new ArrayList<String>();
public static ArrayList<String> Primary_OwnerNmaeOMList = new ArrayList<String>();

public static ArrayList<String> Ward_OMList = new ArrayList<String>();
public static ArrayList<String> Prop_NoOMList = new ArrayList<String>();
public static ArrayList<String> NewOwnerNameOMAssertList = new ArrayList<String>();
public static ArrayList<String> Full_NameOMList = new ArrayList<String>();
public static ArrayList<String> OldOwnerNameOMAssertList = new ArrayList<String>();
public static ArrayList<String> WardNoAssertOMList = new ArrayList<String>();
public static ArrayList<String> PropNoAssertOMList = new ArrayList<String>();
public static ArrayList<String> AppNo_TrfFeeTintiLetterList = new ArrayList<String>();
public static ArrayList<String> PropNo_TrfFeeTintiLetterList = new ArrayList<String>();
public static ArrayList<String> OldOwnerName_TrfFeeTintiLetterList = new ArrayList<String>();
public static ArrayList<String> NewOwnerName_TrfFeeTintiLetterList = new ArrayList<String>();
public static ArrayList<String> TransferType_TrfFeeTintiLetterList = new ArrayList<String>();
public static ArrayList<String> TransferFee_TrfFeeTintiLetterList = new ArrayList<String>();
public static ArrayList<String> IntiLetter_App_NoList = new ArrayList<String>();
public static ArrayList<String> AppNo_IntiLetter = new ArrayList<String>();
public static ArrayList<String> PropNo_IntiLetter = new ArrayList<String>();
public static ArrayList<String> IntiLetter_Prop_NoList = new ArrayList<String>();
public static ArrayList<String> OldPropOwner_IntiLetter = new ArrayList<String>();
public static ArrayList<String> OldPropOwnerNm_IntiLetter = new ArrayList<String>();
public static ArrayList<String> OwnerName_IntiLetterList = new ArrayList<String>();
public static ArrayList<String> NewPropName_IntiLetter = new ArrayList<String>();
public static ArrayList<String> TransferFee_IntiLetter = new ArrayList<String>();
public static ArrayList<String> TransferFee_IntiLetterList = new ArrayList<String>();
public static ArrayList<String> TrfCerti_App_NoList = new ArrayList<String>();
public static ArrayList<String> TrfCerti_Prop_NoList = new ArrayList<String>();
public static ArrayList<String> TrfCerti_AppNoList = new ArrayList<String>();
public static ArrayList<String> TrfCertificate_App_NoList = new ArrayList<String>();
public static ArrayList<String> TrfCerti_PropNoList = new ArrayList<String>();
public static ArrayList<String> TrfCertificate_Prop_NoList = new ArrayList<String>();
public static ArrayList<String> TrfCerti_NewOwnerList = new ArrayList<String>();
public static ArrayList<String> TrfCertificate_New_OwnerList = new ArrayList<String>();
public static ArrayList<String> TrfCerti_New_OwnerList = new ArrayList<String>();
public static ArrayList<String> TrfCerti_OldTransfereeList = new ArrayList<String>();
public static ArrayList<String> TrfCertificate_Old_TransfereeList = new ArrayList<String>();
public static ArrayList<String> TrfCerti_Old_OwnerList = new ArrayList<String>();
public static ArrayList<String> challanList = new ArrayList<String>();
public static ArrayList<String> challanNoList = new ArrayList<String>();
public static ArrayList<String> NewChallanNoList = new ArrayList<String>();
public static ArrayList<String> ApplicationNoList = new ArrayList<String>();
public static ArrayList<String> ApplicantNameList = new ArrayList<String>();
public static ArrayList<String> ApplicantNmList = new ArrayList<String>();
public static ArrayList<String> ChallanDateList = new ArrayList<String>();
public static ArrayList<String> CurrentDateList = new ArrayList<String>();
public static ArrayList<String> AmountList = new ArrayList<String>();


	
	public static ArrayList<String> propnoList = new ArrayList<String>();
	public static ArrayList<String> Prop_No_List = new ArrayList<String>();
	public static ArrayList<String> ChallanReneration_PropNoList = new ArrayList<String>();
	public static ArrayList<String> Challan_Reneration_PropNo_List = new ArrayList<String>();
	public static ArrayList<String> Challan_Regen_OwnerName_List = new ArrayList<String>();
	public static ArrayList<String> Challan_Regen_ReceiptAmountList = new ArrayList<String>();
	public static ArrayList<String> PaymentModeList = new ArrayList<String>();
	public static ArrayList<String> Challan_Regen_ReceiptPayModeList = new ArrayList<String>();
	public static ArrayList<String> PayableAmountList = new ArrayList<String>();
	public static ArrayList<String> Challan_Regen_ReceiptPayableAmountList = new ArrayList<String>();
	public static ArrayList<String> CurrentYearNetAmountList = new ArrayList<String>();
	public static ArrayList<String> Challan_Regen_ReceiptCurrentYearPayableAmountList = new ArrayList<String>();
	public static ArrayList<String> DiscountList = new ArrayList<String>();
	public static ArrayList<String> Challan_Regen_ReceiptDiscountAmountList = new ArrayList<String>();
	public static ArrayList<String> Challan_Regen_Owner_Name_List = new ArrayList<String>();
	public static ArrayList<String> Challan_Regen_ReceiptRainWaterHarvestingDiscountAmountList = new ArrayList<String>();
	

	//Objection Entry Form Arraylist By Piyush on 24 feb 2017
	public static ArrayList<String> OE_objectionIssuer= new ArrayList<String>(); 
	public static ArrayList<String> OE_objectionType= new ArrayList<String>(); 
	public static ArrayList<String> OE_ObjectionPropNo= new ArrayList<String>(); 
	public static ArrayList<String> OE_ObjectionDate= new ArrayList<String>(); 
	public static ArrayList<String> OE_objName= new ArrayList<String>(); 
	public static ArrayList<String> OE_objDepartmentDesc= new ArrayList<String>(); 
	public static ArrayList<String> OE_objectionMatter= new ArrayList<String>(); 
	public static ArrayList<String> OE_objPropertyOwnerAddress= new ArrayList<String>(); 
	public static ArrayList<String> OE_objPropertyOwnerName= new ArrayList<String>(); 
	public static ArrayList<String> ObjectionNo = new ArrayList<String>(); 
	//Objection Inspection form Arraylist By Piyush on 24 feb 2017
	public static ArrayList<String> Insp_ObjectionIssuer = new ArrayList<String>();
	public static ArrayList<String> Insp_ObjectionDate = new ArrayList<String>();
	public static ArrayList<String> Insp_PropertyNo = new ArrayList<String>();
	public static ArrayList<String> Insp_ObjectionName= new ArrayList<String>();
	public static ArrayList<String> Insp_ObjectionDeptName= new ArrayList<String>();
	public static ArrayList<String> Insp_OwnerName= new ArrayList<String>();
	public static ArrayList<String> Insp_OwnerAddress= new ArrayList<String>();
	public static ArrayList<String> Insp_InsObjectionMatter = new ArrayList<String>();
	public static ArrayList<String> Insp_Inspectionremarks= new ArrayList<String>();
	public static ArrayList<String> Insp_InspectionEmpName= new ArrayList<String>();
	public static ArrayList<String> Insp_InspectionDate= new ArrayList<String>();
	//Objection & Hearing Details form Arraylist By Piyush on 24 feb 2017
	public static ArrayList<String> HDE_PropertyNo= new ArrayList<String>();
	public static ArrayList<String> HDE_ObjNo= new ArrayList<String>();
	public static ArrayList<String> HDE_ObjDate= new ArrayList<String>();
	public static ArrayList<String> HDE_HearingDate= new ArrayList<String>();
	//Hearing Letter Pdf Report pattrn form Arraylist By Piyush on 24 feb 2017
	public static ArrayList<String> Pdf_HearlingLet_LetterNo = new ArrayList<String>();
	public static ArrayList<String> Pdf_HearlingLet_LetterPrintDate = new ArrayList<String>();
	public static ArrayList<String> Pdf_HearlingLet_ULBName= new ArrayList<String>(); 
	public static ArrayList<String> Pdf_HearlingLet_OwnerAdd= new ArrayList<String>();
	public static ArrayList<String> Pdf_HearlingLet_ObjNo= new ArrayList<String>();
	public static ArrayList<String> Pdf_HearlingLet_OwnerName= new ArrayList<String>();
	public static ArrayList<String> Pdf_HearlingLet_PropNo= new ArrayList<String>();
	public static ArrayList<String> Pdf_HearlingLet_ApplicationDate= new ArrayList<String>();
	//Hearing Order Pdf Report pattrn form Arraylist By Piyush on 24 feb 2017
	public static ArrayList<String> pdf_HO_Objectiondate= new ArrayList<String>();
	public static ArrayList<String> pdf_HO_PropertyNo= new ArrayList<String>();
	public static ArrayList<String> pdf_HO_HearingOrderNo= new ArrayList<String>();
	public static ArrayList<String> pdf_HO_OwnerName= new ArrayList<String>();
	public static ArrayList<String> pdf_HO_UlbName= new ArrayList<String>();
	public static ArrayList<String> HeringOrderNo= new ArrayList<String>();
	public static String ptRcptAppNo;
	
	
	
	
	
	
//	@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@END PT VARIBLES@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	WebElement element;

	public CommonUtilsAPI() {

		//System.out.println("In common utils");

		mReadFromXML("D:/AutomationFramework/ABMSmartScript/functional/config/config.xml");
		//continueExec = Boolean.parseBoolean(mGetPropertyFromFile("ContinueExecutionOnError"));

		//System.out.println("The Class Name is:"+this.getClass().getName());
		//System.out.println("The Class Name is:"+this.getClass().getSimpleName());

	}

	// Action before the event takes place
	private void preAction(String message) {

		try{
		log.info(message);
		}
		catch(Exception e)
		{
			throw new MainetCustomExceptions("Error in preAction method");
		}
	}

	// Action after the event took place
	private void postAction(String message) {

		try{
		log.info(message);
		}
		catch(Exception e)
		{
			throw new MainetCustomExceptions("Error in postAction method");
		}
	}


	// Wrapper method for opening the browser
	private void wOpenBrowser(String browserName) {

		try {

			//mKillIPMSG();
			mGenericWait();

			if (browserName.equalsIgnoreCase("Firefox")) {

				FirefoxProfile firefoxProfile = new FirefoxProfile();
				firefoxProfile.setPreference("reader.parse-on-load.enabled",false);

				firefoxProfile.setPreference("browser.startup.homepage_override.mstone", "ignore");
				firefoxProfile.setPreference("startup.homepage_welcome_url", "about:blank");
				firefoxProfile.setPreference("webdriver.log.file", "d:\\firefox.log");
				firefoxProfile.setPreference("webdriver.log.browser.ignore", "false");
				firefoxProfile.setPreference("webdriver.log.driver.ignore", "false");
				firefoxProfile.setPreference("webdriver.log.profiler.ignore", "false");

				driver = new FirefoxDriver(firefoxProfile);
			}

			else if (browserName.equalsIgnoreCase("Chrome")) {

				System.setProperty("webdriver.chrome.driver",
						mGetPropertyFromFile("chromeDriverPath"));

				ChromeOptions options = new ChromeOptions();
				// options.addArguments("-incognito");
				options.addArguments("--disable-popup-blocking");
				options.addArguments("--disable-extensions");
				options.addArguments("disable-infobars");
			
				options.setExperimentalOption("useAutomationExtension", false);
				options.setExperimentalOption("excludeSwitches", 
				Collections.singletonList("enable-automation"));
			
				  options.addArguments(Arrays.asList("--no-sandbox","--ignore-certificate-errors","--homepage=about:blank","--no-first-run"));

				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,
						true);

				capabilities.setCapability(ChromeOptions.CAPABILITY, options);

				//capabilities.setPlatform(Platform.ANY); //swap
				//driver=new RemoteWebDriver(new URL("http://192.168.100.164:5566/wd/hub"), capabilities);//swap
				driver = new ChromeDriver(capabilities);

			}

			else if (browserName.equalsIgnoreCase("ie"))
			{

				/*DesiredCapabilities capabilities = new DesiredCapabilities().internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); 
				capabilities.setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS, true); 
				//capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
				capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "about:blank");
				//capabilities.setCapability("nativeEvents",false);
				//capabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
				 */


				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();

				ieCapabilities.setCapability("nativeEvents", false);    
				ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
				ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
				ieCapabilities.setCapability("disable-popup-blocking", true);
				ieCapabilities.setCapability("enablePersistentHover", true);

				//driver = new InternetExplorerDriver(ieCapabilities);


				System.setProperty("webdriver.ie.driver",mGetPropertyFromFile("ieDriverPath"));
				driver = new InternetExplorerDriver(ieCapabilities);

			}

		}catch(Exception e){
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in wOpenBrowser method");

		}

	}

	// Master method for opening the browser
	public void mOpenBrowser(String browserName) {


		try
		{

			preAction("Starting browser " + browserName);
			wOpenBrowser(browserName);
			postAction(browserName + " Browser started");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mOpenBrowser method");
		}

	}

	// Wrapper for hitting the URL provided
	private void wGetURL(String url) throws IOException {

		try {

			// Get the URL
			driver.get(url);

			//	driver.navigate().to(url);

			//driver.get("javascript:document.getElementById('overridelink').click();");

			mGenericWait();
			// Maximize the browser window


			driver.manage().window().maximize();

			mGenericWait();
			// english language selection

			if (driver.findElements(By.linkText("English")).size() != 0) {
				mWaitForVisible("linkText", "English");
				driver.findElement(By.linkText("English")).click();
				mGenericWait();
			}
		} catch (Exception e) {

			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in wGetURL method");

		}
	}



	//Master method for hitting the URL provided
	public void mGetURL(String url){
		try{
			preAction("Navigating to url "+url);
			wGetURL(url);
			postAction("URL opened");

		} catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mGetURL method");
		}
	}


	//Generic method for locators

	public By locatorSelector(String locatorType, String locator) {
		try {

			By by;
			switch (locatorType) {

			case "id":
				by = By.id(locator);
				break;
			case "name":
				by = By.name(locator);
				break;
			case "xpath":
				by = By.xpath(locator);
				break;
			case "css":
				by = By.cssSelector(locator);
				break;
			case "linkText":
				by = By.linkText(locator);
				break;
			case "partialLinkText":
				by = By.partialLinkText(locator);
				break;
			case "className":
				by = By.className(locator);
				break;
			default:
				by = null;
				break;
				// Log.error("Locator value not found");

			}
			return by;

		} catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in Locator selector method ");

		}
	}

	// Wrapper for reading from XML file
	private void wReadFromXML(String filepath) {

		try {

			File file = new File(filepath);

			FileInputStream inputStream = new FileInputStream(file);
			props.loadFromXML(inputStream);

		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error wReadFromXML method");
		}

	}

	public void mReadFromXML(String filepath) {

		try{
			preAction("Reading from XML");
			wReadFromXML(filepath);
			postAction("Reading done from XML : " + filepath);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mReadFromXML method");
		}
	}

	// Generic wait
	// Wrapper for generic wait
	private void wGenericWait() {

		try {

			String genericWaitTime = props.getProperty("genericWaitTimeout");
			int genericWatiTimeInt = Integer.parseInt(genericWaitTime);

			Thread.sleep(genericWatiTimeInt);

		} catch (InterruptedException e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wGenericWait method");
		}
	}

	// Master for generic wait
	public void mGenericWait() {

		try {
			preAction("Waiting for 5 sec...");
			wGenericWait();
			postAction("Waited for 5 sec...");
		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mGenericWait method");
		}

	}

	// Wrapper for custom wait
	private void wCustomWait(int ms) {
		try {
			Thread.sleep(ms);
		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wCustomWait method");
		}

	}

	// Master for custom wait
	public void mCustomWait(int ms) {

		try {

			preAction("Waiting for" + ms + " miliseconds");
			wCustomWait(ms);
			postAction("Waiting for" + ms + " miliseconds");
		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mCustomWait method");
		}
	}

	// Wrapper for Wait for the element to be visible
	private void wWaitforVisible(String locatorType, String locator) {
		try {

			mReadFromXML(mGetPropertyFromFile("configFilePath"));

			String time = props.getProperty("waitForVisibleTimeout");
			int timeOut = Integer.parseInt(time);

			// Wait for element to be visible...
			WebDriverWait wait = new WebDriverWait(driver, timeOut);

			if(imageid.equals("") && textid.equals(""))
			{
				wait.until(ExpectedConditions
						.visibilityOfElementLocated((locatorSelector(hmapid.get(actualKey).get(0).toString().trim(),
								locator))));
			}
			else if(!imageid.equals(null))
			{
				wait.until(ExpectedConditions
						.visibilityOfElementLocated((locatorSelector("css",
								imageid))));
			}
			else if(!textid.equals(null))
			{
				wait.until(ExpectedConditions
						.visibilityOfElementLocated((locatorSelector("id",
								textid))));
			}
			/*
			 * for (int second = 0;; second++) { if (second >= timeOut){
			 * fail(locatorType, locator); break; } else{ try { if
			 * (driver.findElement(locatorSelector(locatorType,
			 * locator)).isDisplayed()) break; } catch (Exception e) { //throw
			 * new MainetCustomExceptions("Null value found"); }
			 * //mGenericWait(); Thread.sleep(1000); } }
			 */

		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wWaitforVisible method");

		}

	}

	private void fail(String locatorType, String locator) {

		try{
			log.info("Following element not found:: Locator type: " + locatorType
					+ " Locator: " + locator + "\nExiting...closing browser.");

		}
		catch(Exception e)
		{
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in fail method");
		}
	}

	// Master for wait for visible
	public void mWaitForVisible(String locatorType, String locator) {

		try {
			preAction("Waitng for the element to be visible");
			wWaitforVisible(locatorType, locator);
			postAction("Element visible");
		} catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mWaitForVisible method");
		}
	}

	// Wrapper for wait for invisible
	private void wWaitforInvisible(String locatorType, String locator) {

		try {

			mReadFromXML(mGetPropertyFromFile("configFilePath"));

			String time = props.getProperty("waitForVisibleTimeout");
			int timeOut = Integer.parseInt(time);

			// Wait for element to be invisible...
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions
					.invisibilityOfElementLocated((locatorSelector(hmapid.get(actualKey).get(0).toString().trim(),
							locator))));

		}
		catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wWaitForInvisible method");
		}
	}

	// Master for wait for visible
	public void mWaitForInvisible(String locatorType, String locator) {

		try {
			preAction("Waitng for the element to be Invisible");
			wWaitforInvisible(locatorType, locator);
			postAction("Element Invisible");
		} catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mWaitForInvisible method");

		}
	}

	// Wrapper method to read from input stream file
	private void wReadFromInputStream(String idFilePath, String dataFilePath) {

		try {

			File readBndIdFile = new File(idFilePath);
			File readBndDataFile = new File(dataFilePath);

			FileInputStream idInputStream = new FileInputStream(readBndIdFile);
			FileInputStream dataInputStream = new FileInputStream(readBndDataFile);

			props.load(idInputStream);
			props.load(dataInputStream);

		} catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wReadFromInputStream method of two parameters");
		}
	}


	// Overloaded wrapper method for loading property file
	private void wReadFromInputStream(String filePath) {

		try {

			File readFile = new File(filePath);

			FileInputStream inputStream = new FileInputStream(readFile);

			props.load(inputStream);

		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wReadFromInputStream method of single parameters");
		}

	}

	// Master method to read from input stream file
	public void mReadFromInputStream(String idFilePath, String dataFilePath) {
		try
		{
			preAction("Reading from properties");
			wReadFromInputStream(idFilePath, dataFilePath);
			postAction("Reading done");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mReadFromInputStream method of two parameters");
		}
	}

	// Overloaded master method for loading property file
	public void mReadFromInputStream(String filePath) {

		try{
			preAction("Reading from properties");
			wReadFromInputStream(filePath);
			postAction("Reading done");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mReadFromInputStream method of single parameter");
		}
	}

	// Wrapper for getting a property or a value from a file based on a key
	// provided


	//Wrapper for getting a property or a value from a file based on a key provided
	public String wGetPropertyFromFile(String key){
		String value = "";

		try{

			if (props.getProperty(key) !=null)
			{
				value = props.getProperty(key);


			}
			else if(hmapid.get(key) != null)
			{
				//value = hmapid.get(key);
				actualKey=key;
				value = hmapid.get(key).get(1);

				System.err.println("This is the key "+actualKey+" and this is the value of key "+value);
			}
			else if(hmap.get(key) != null)
			{
				value = hmap.get(key).get(CurrentinvoCount);
			}
			else if(hmapforformula.get(key) != null)
			{
				value = hmapforformula.get(key).get((InvoCount-1));
			}

		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wGetPropertyFromFile method");

		}

		return value;
	}


	//Master for getting a property or a value from a file based on a key provided
	public String mGetPropertyFromFile(String key){
		String propValue="";
		try{
			preAction("Reading a value associated with key: "+ key);
			propValue=wGetPropertyFromFile(key);
			postAction("Reading done, value: "+propValue);

			/*if(!(key.startsWith("#")) && propValue.equals(null)){ 
				throw new MainetCustomExceptions("Null value found");
			}*/


		} catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mGetPropertyFromFile method");
		}

		return propValue;
	}

	// Master for finding element
	public WebElement mFindElement(String locatorType, String locator) {
		try {

			preAction("Finding the element");
			driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator));
			postAction("Element found");

		} catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mFindElement method");
		}

		return driver.findElement(locatorSelector(locatorType, locator));
	}

	private String wGetApplicationNo(String locatorType, String locator) {

		try {

			mGetText(locatorType, locator);
		} catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wGetApplicationNo method");
		}

		return mGetText(locatorType, locator);

	}
	
	

	public String mGetApplicationNo(String locatorType, String locator) {

		String appNo = "";
		try {

			preAction("Selecting application no");
			appNo = wGetApplicationNo(locatorType, locator);
			postAction("Captured application no:: " + appNo);
		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mGetApplicationNo method");
		}
		return appNo;
	}

	private String wGetText(String locatorType, String locator) {

		try {

			driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator)).getText();

		} catch (Exception e) {

			e.printStackTrace(); 
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wGetText method");
		}
		return driver.findElement(locatorSelector(locatorType, locator))
				.getText();
	}

	// Master for getting the text
	public String mGetText(String locatorType, String locator) {
		String text = "";
		try {

			preAction("Getting text");
			text = wGetText(locatorType, locator);
			postAction("Text captured");
		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mGetText method");
		}
		return text;

	}

	// /overriding method for get text

	private String wGetText(String locatorType, String locator, String value) {

		try {

			driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator))
			.getAttribute(value);


		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wGetText method using getattribute value");
		}
		return driver.findElement(locatorSelector(locatorType, locator))
				.getAttribute(value);
	}

	// Master for getting the text
	public String mGetText(String locatorType, String locator, String value) {
		String text = "";
		try {

			preAction("Getting text");
			text = wGetText(locatorType, locator, value);
			postAction("Text captured");
		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mGetText method using getattribute value");
		}
		return text;

	}

	// Wrapper for Selenium click
	private void wClick(String locatorType, String locator) {

		try {

			if(imageid.equals(""))
			{
				driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator)).click();

			}
			else
			{
				driver.findElement(locatorSelector("css", imageid)).click();
				imageid="";

			}

		} catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wClick method");
		}

	}

	// Master for Selenium click
	public void mClick(String locatorType, String locator) {

		try {
			preAction("Clicking on element");
			wClick(locatorType, locator);
			postAction("Element clicked");
		} catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mClick method");
		}

	}

	// Wrapper for Selenium sendKeys
	private void wSendKeys(String locatorType, String locator, String data) {
		try {

			int ct=data.length();
			//System.out.println("Charecter Entered in specifed textbox==>"+ct);
			//System.out.println("enterd data ===========>"+data);

			if(textid.equals("")) 
			{
				//System.out.println("iam in text id double");
				driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator)).clear();
				Thread.sleep(300);
				///swap copde to remove skip blank field or ? 
				if (data.equalsIgnoreCase("")||data.equalsIgnoreCase("?")) {
				driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator)).sendKeys("");
				}else {
					driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator)).sendKeys(data);
				}
				//swap
			}else
			{
				//System.out.println("iam in text id data");
			
				driver.findElement(locatorSelector("id", textid)).clear();
				Thread.sleep(300);
				driver.findElement(locatorSelector("id", textid)).sendKeys(data);
				textid="";

			}	
					
		}
		catch (Exception e) {
			
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wSendKeys method");
		}

	}

	// Master for Selenium sendKeys
	public void mSendKeys(String locatorType, String locator, String data) {

		try {
			preAction("Entering data:::" +locator);
			wSendKeys(locatorType, locator, data);
			postAction("Data entered:::"+locator);
		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mSendKeys method");

		}
	}

	//Wrapper for upload
	private void wUpload(String locatorType, String locator, String data){

		try{

			current = new java.io.File( "." ).getCanonicalPath();
			//System.out.println(current);
			current = current.replaceAll(current, "\\");
			//System.out.println("current no 1 is:::"+current);



			//System.out.println("Current dir:"+current);

			current = current + mGetPropertyFromFile("uploadfromxml")+ data;

			//System.out.println("Current upload path is:::: "+current);

			//current = current.replaceAll("\\", "\");

			File file =new File(current);

			if(file.exists()){

				long bytes = file.length();
				double kilobytes = (bytes / 1024);
				double megabytes = (kilobytes / 1024);
				double gigabytes = (megabytes / 1024);
				double terabytes = (gigabytes / 1024);
				double petabytes = (terabytes / 1024);
				double exabytes = (petabytes / 1024);
				double zettabytes = (exabytes / 1024);
				double yottabytes = (zettabytes / 1024);

				//System.out.println("bytes : " + bytes);
				//System.out.println("kilobytes : " + kilobytes);

				//System.out.println("megabytes : " + megabytes);

				DecimalFormat df = new DecimalFormat("#.##");      
				megabytes = Double.valueOf(df.format(megabytes));

				//System.out.println("new megabytes : " + megabytes);

				/*//System.out.println("gigabytes : " + gigabytes);
					//System.out.println("terabytes : " + terabytes);
					//System.out.println("petabytes : " + petabytes);
					//System.out.println("exabytes : " + exabytes);
					//System.out.println("zettabytes : " + zettabytes);
					//System.out.println("yottabytes : " + yottabytes);*/
			}
			else
			{
				//System.out.println("File does not exists!");
			}

			if(docuploadboolean)
			{
				driver.findElement(locatorSelector(locatorType, locator)).sendKeys(current);
			}
			else{
				//driver.findElement(locatorSelector(locatorType, locator)).sendKeys(mGetPropertyFromFile("uploadfromxml"));
				//driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator)).sendKeys(current);
				driver.findElement(locatorSelector(locatorType, locator)).sendKeys(current);
			}
			//System.out.println(current);
		}
		catch(Exception e){
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wUpload method");
		}
	}

	//Master for upload
	public void mUpload(String locatorType, String locator, String data){

		try{

			preAction("Uploading document");
			wUpload(locatorType, locator, data);
			postAction("Document uploaded");
		}
		catch(Exception e){
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mUpload method");			
		}
	}
	// Wrapper for Selenium Select from drop down
	private void wSelectDropDown(String locatorType, String locator, String data) {

		try {

			if(mGetPropertyFromFile("browserName").equalsIgnoreCase("firefox"))
			{
				driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator)).sendKeys(data);
				mTakeScreenShot();
			}
			else
			{
				new Select(driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator))).selectByVisibleText(data);
				mTakeScreenShot();
			}

			//driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator)).sendKeys(Keys.TAB);

		} catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wSelectDropDown method");

		}
	}

	// Master for Selenium Select from drop down
	public void mSelectDropDown(String locatorType, String locator, String data) {

		try {
			preAction("Selecting from dropdown");
			wSelectDropDown(locatorType, locator, data);
			postAction("Selected from dropdown");

		} catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mSelectDropDown method");
		}

	}



	// Wrapper for selecting autocomplete text
	/*private void wSelectAutoCompleteText(String locatorType, String locator,
			String data) {


		try {

			driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator)).clear();
			mGenericWait();
			element = driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator));
			element.sendKeys(data);
			mGenericWait();
			element.sendKeys(Keys.DOWN);
			mGenericWait();
			element.sendKeys(Keys.ENTER);
			// driver.findElement(By.xpath("//*[text()='"+data+"']")).click();

		}

		catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wSelectAutoCompleteText method");
		}

	}*/

	/*// Wrapper for selecting autocomplete text
		private void wSelectAutoCompleteText(String locatorType, String locator,
				String data) {


			try {

				driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator)).clear();
				mGenericWait();
				element = driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator));
				element.sendKeys(data);
				mGenericWait();


				char[] cArray = data.toCharArray();
				driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator)).clear();
				int i;
				//System.out.println("Length of String is: " + data.length());

				for (i = 0; i <= data.length() - 1; i++) {
					//System.out.println(cArray[i]);
					String finalData = Character.toString(cArray[i]);
					Thread.sleep(150);
					driver.findElement(locatorSelector(locatorType, locator)).sendKeys(finalData);				
					Thread.sleep(500);				
				}

				element = driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator));
				mGenericWait();
				mCustomWait(2000);
				element.sendKeys(Keys.DOWN);
				mGenericWait();
				element.sendKeys(Keys.ENTER);

				element.sendKeys(Keys.DOWN);
				mGenericWait();
				element.sendKeys(Keys.ENTER);
				// driver.findElement(By.xpath("//*[text()='"+data+"']")).click();

			}

			catch (Exception e) {
				e.printStackTrace();
				exception = e.toString();			
				stackTrace = Throwables.getStackTraceAsString(e);
				throw new MainetCustomExceptions("Error in wSelectAutoCompleteText method");
			}

		}*/

	// Master for selecting autocomplete text
	/*public void mSelectAutoCompleteText(String locatorType, String locator,
			String data) {

		try {

			preAction("Selecting from autocomplete textbox");
			wSelectAutoCompleteText(locatorType, locator, data);
			postAction("Element selected");

		}

		catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mSelectAutoCompleteText method");
		}

	}*/

	// Wrapper method for tab
	private void wTab(String locatorType, String locator) {

		try {

			element = driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator));
			element.sendKeys(Keys.TAB);

		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wTab method");
		}

	}

	// Master method for tab
	public void mTab(String locatorType, String locator) {

		try {

			preAction("Performing tab");
			wTab(locatorType, locator);
			postAction("Tab performed");

		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mTab method");
		}

	
	}

	// Wrapper for Hindi text conversion
	private void wHindiTextConversion(String locatorType, String locator,
			String data) {

		try {

			char[] cArray = data.toCharArray();
			driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator)).clear();
			int i;
			//System.out.println("Length of String is: " + data.length());

			for (i = 0; i <= data.length() - 1; i++) {
				//System.out.println(cArray[i]);
				String finalData = Character.toString(cArray[i]);
				Thread.sleep(150);
				driver.findElement(locatorSelector(locatorType, locator))
				.sendKeys(finalData);
				Thread.sleep(150);
			}

		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wHindiTextConversion method");
		}
	}

	// Master for Hindi text conversion
	public void mHindiTextConversion(String locatorType, String locator,
			String data) {

		try {
			preAction("Converting to Hindi");
			wHindiTextConversion(locatorType, locator, data);
			postAction("Hindi conversion done");
		} catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mHindiTextConversion method");
		}
	}

	// Wrapper for switching tabs
	// Method to switch between browser tabs/windows
	private void wSwitchTabs() {

		try {
			ArrayList<String> tabs2 = new ArrayList<String>(
					driver.getWindowHandles());
			//System.out.println(tabs2);
			driver.switchTo().window(tabs2.get(1));
			//System.out.println(tabs2.get(0));
			//System.out.println(tabs2.get(1));
			//System.out.println(tabs2.get(2));
			driver.switchTo().window(tabs2.get(2));
			mGenericWait();
			driver.close();
			driver.switchTo().window(tabs2.get(0));
			driver.switchTo().window(tabs2.get(1));

		} catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wSwitchTabs method");
		}
	}

	// Master for switching tabs
	public void mSwitchTabs() {
		try {

			preAction("Switching tabs");
			wSwitchTabs();
			postAction("Back to original window");
		} catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mSwitchTabs method");
		}
	}

	// Wrapper for switching tabs
	// Method to switch between browser tabs/windows
	private void SwitchTab(int y) {

		try {
			ArrayList<String> tabs2 = new ArrayList<String>(
					driver.getWindowHandles());
			//System.out.println("No of Windows is: " + tabs2.size());

			//System.out.println("Following are the window ID " + tabs2);

			for (int i = 0; i < tabs2.size() - 1; i++) {
				//System.out.println(tabs2.get(i));
			}

			// driver.switchTo().window(tabs2.get(x));

			// //System.out.println("Driver is currently on window no: "+y);

			driver.switchTo().window(tabs2.get(y));

			//System.out.println("Driver is currently on window no: " + y);

		} catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in SwitchTab method");

		}
	}

	// Master for switching tabs
	public void mSwitchTab(int y) {
		try {

			preAction("Switching to " + y + " tab");
			SwitchTab(y);
			postAction("Switched to " + y + " tab");
		} catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mSwitchTab method");
		}
	}

	// Wrapper for closing the browser
	private void wCloseBrowser() {

		try {

			driver.quit();
			mKillChromeDriver();

		} catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wCloseBrowser method");
		}
	}

	// Master for closing the browser
	public void mCloseBrowser() {
		try {
			preAction("Closing the browser");
			mCustomWait(4000);
			wCloseBrowser();
			postAction("Browser closed");
		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mCloseBrowser method");
		}

	}

	/*// By Sneha Solaskar

	// Wrapper for selecting date
	private void wDatePicker(String dtPickerId, String year, String month,String day) {
		try {

			// These coordinates are screen coordinates
			int xCoord = 1300;
			int yCoord = 220;

			// Move the cursor
			Robot robot = new Robot();
			robot.mouseMove(xCoord, yCoord);

			driver.findElement(By.id(dtPickerId)).click();
			int days;
			//System.out.println("selected date is  " + day + "/" + month + "/"
					+ year);
			int currentyear = Calendar.getInstance().get(Calendar.YEAR);
			int numyear = Integer.parseInt(year);

			//

			if ((numyear < (currentyear - 100) || numyear > (currentyear))) {
				//System.out.println("please enter valid year");
			}

			boolean isLeapYear = ((numyear % 400 == 0) || ((numyear % 4 == 0) && (numyear % 100 != 0)));
			int d = Integer.parseInt(day);

			// to calculate no of days in month
			if (month.equals("Apr") || month.equals("Jun")
					|| month.equals("Sep") || month.equals("Nov")) {
				days = 30;

				if (d > days || d < 1) {

					System.out
					.println("Date can not be greater than 30 or less than 1");
				}
			} else if (month.equals("Jan") || month.equals("Mar")
					|| month.equals("May") || month.equals("Jul")
					|| month.equals("Aug") || month.equals("Oct")
					|| month.equals("Dec")) {
				days = 31;

				if (d > days || d < 1) {
					System.out
					.println("Date can not be greater than 31 or less than 1");
				}
			} else if (month.equals("Feb")) {
				if (isLeapYear) {
					days = 29;

					if (d > days || d < 1) {
						System.out
						.println("Date can not be greater than 29 or less than 1");
					}
				} else {
					days = 28;

					if (d > days || d < 1) {
						System.out
						.println("Date can not be greater than 28 or less than 1");
					}
				}
			} else {
				//System.out.println("invalid month");
			}
			mGenericWait();
			// To select year

			driver.findElement(By.cssSelector("select.ui-datepicker-year"))
			.click();
			mGenericWait();
			Select dropdown = new Select(driver.findElement(By
					.cssSelector("select.ui-datepicker-year")));
			dropdown.selectByVisibleText(year);
			driver.findElement(By.cssSelector("select.ui-datepicker-year"))
			.sendKeys(Keys.ENTER);
			mGenericWait();

			// To select month
			driver.findElement(By.cssSelector("select.ui-datepicker-month"))
			.click();
			Select dropdown1 = new Select(driver.findElement(By
					.cssSelector("select.ui-datepicker-month")));
			dropdown1.selectByVisibleText(month);
			driver.findElement(By.cssSelector("select.ui-datepicker-month"))
			.sendKeys(Keys.ENTER);
			mGenericWait();

			mGenericWait();

			// To select date
			int intDate = Integer.parseInt(day);
			String strDate = Integer.toString(intDate);
			driver.findElement(By.linkText(strDate)).click();
			//driver.findElement(By.linkText(strDate)).sendKeys(Keys.ENTER);

			mGenericWait();

			//System.out.println(status);
			driver.findElement(By.id(dtPickerId)).sendKeys(Keys.TAB);

			date = driver.findElement(By.id(dtPickerId)).getAttribute("value");
			//System.out.println(date);

		} catch (Exception e) {
			e.printStackTrace();
			status = false;
			//System.out.println("date is not in correct format");
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wDatePicker method");
		}

	}

	public void mDatePicker(String dtPickerId, String year, String month,
			String day) throws InterruptedException {
		try {
			preAction("Selecting a date");
			wDatePicker(dtPickerId, year, month, day);
			postAction("Date selected " + date);
		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mDatePicker method");
		}

	}*/

	//method to select a date
	private void wDateSelect(String locatorType,String dtPickerId,String dtPickerData)
	{
		try
		{


			//mWaitForVisible(locatorType,dtPickerId);

			String tokens[] =dtPickerData.split("/");
			//System.out.println("i m selecting a date");
			String strtempdate= tokens[0];
			int cuDate=Integer.parseInt(strtempdate);
			String strnDate=Integer.toString(cuDate);
			//System.out.println(tokens[2]);
			//System.out.println(tokens[1]);
			//System.out.println(strnDate);
			mGdatePicker(locatorType,dtPickerId,tokens[2],tokens[1],strnDate);
			mdifferenceindays();
		}catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wDateSelect method");
		}
	}

	//m method for date selection
	public void mDateSelect(String locatorType,String dtPickerId,String dtPickerData)
	{
		try
		{
			preAction("Selecting a date...");
			wDateSelect(locatorType,dtPickerId,dtPickerData);
			postAction("Date selected... ");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mDateSelect method");
		}
	}



	//Wrapper for generic date picker
	private void wGdatePicker(String dtPickerId, String year,String month,String day){
		try
		{
			//to move cursor to another location

			// These coordinates are screen coordinates
			int xCoord = 1300;
			int yCoord = 220;

			// Move the cursor
			Robot robot = new Robot();
			robot.mouseMove(xCoord, yCoord);
			mGenericWait();

			String gDate;
			int days;

			driver.findElement(By.id(dtPickerId)).click();

			//System.out.println("selected date is  " + day + "/" + month + "/"+ year);
			int currentyear = Calendar.getInstance().get(Calendar.YEAR);
			int numyear = Integer.parseInt(year);

			if ((numyear < (currentyear - 100) || numyear > (currentyear))) {
				//System.out.println("please enter valid year");
			}

			boolean isLeapYear = ((numyear % 400 == 0) || ((numyear % 4 == 0) && (numyear % 100 != 0)));
			int d = Integer.parseInt(day);

			// to calculate no of days in month
			if (month.equals("Apr") || month.equals("Jun")
					|| month.equals("Sep") || month.equals("Nov")) {
				days = 30;

				if (d > days || d < 1) {

					System.out
					.println("Date can not be greater than 30 or less than 1");
				}
			} else if (month.equals("Jan") || month.equals("Mar")
					|| month.equals("May") || month.equals("Jul")
					|| month.equals("Aug") || month.equals("Oct")
					|| month.equals("Dec")) {
				days = 31;

				if (d > days || d < 1) {
					System.out
					.println("Date can not be greater than 31 or less than 1");
				}
			} else if (month.equals("Feb")) {
				if (isLeapYear) {
					days = 29;

					if (d > days || d < 1) {
						System.out
						.println("Date can not be greater than 29 or less than 1");
					}
				} else {
					days = 28;

					if (d > days || d < 1) {
						System.out
						.println("Date can not be greater than 28 or less than 1");
					}
				}
			} else {
				//System.out.println("invalid month");
			}
			mGenericWait();
			// To select year

			driver.findElement(By.cssSelector("select.ui-datepicker-year"))
			.click();
			mGenericWait();
			Select dropdown = new Select(driver.findElement(By
					.cssSelector("select.ui-datepicker-year")));
			dropdown.selectByVisibleText(year);
			driver.findElement(By.cssSelector("select.ui-datepicker-year"))
			.sendKeys(Keys.ENTER);
			mGenericWait();

			// To select month
			driver.findElement(By.cssSelector("select.ui-datepicker-month"))
			.click();
			Select dropdown1 = new Select(driver.findElement(By
					.cssSelector("select.ui-datepicker-month")));
			dropdown1.selectByVisibleText(month);
			driver.findElement(By.cssSelector("select.ui-datepicker-month"))
			.sendKeys(Keys.ENTER);
			mGenericWait();

			mGenericWait();

			// To select date
			int intDate = Integer.parseInt(day);
			String strDate = Integer.toString(intDate);
			driver.findElement(By.linkText(strDate)).click();
			driver.findElement(By.linkText(strDate)).sendKeys(Keys.ENTER);

			mGenericWait();

			//System.out.println(status);
			driver.findElement(By.id(dtPickerId)).sendKeys(Keys.TAB);

			gDate = driver.findElement(By.id(dtPickerId)).getAttribute("value");
			//System.out.println(gDate);

		} catch (Exception e) {
			e.printStackTrace();
			status = false;
			//System.out.println("date is not in correct format");
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wGdatePicker method");

		}

	}

	// Master for Generic date picker
	public void mGdatePicker(String dtPickerId, String year, String month,
			String day) {
		try {
			preAction("Selecting a date");
			wGdatePicker(dtPickerId, year, month, day);
			postAction("Date selected");
		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mGdatePicker method");
		}

	}

	/*// Method for uploading file -by Sneha Solaskar
	private void wUploadBirthRegistration(String uploadPath) {
		try {
			// To get system date
			int currentyear = Calendar.getInstance().get(Calendar.YEAR);
			int currentmonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
			int currentday = Calendar.getInstance().get(Calendar.DATE);

			Calendar calendar1 = Calendar.getInstance();
			Calendar calendar2 = Calendar.getInstance();

			calendar1.set(Integer.parseInt(date.substring(6, 10)),
					Integer.parseInt(date.substring(3, 5)),
					Integer.parseInt(date.substring(0, 2)));
			calendar2.set(currentyear, currentmonth, currentday);

			long miliSecondForDate1 = calendar1.getTimeInMillis();
			long miliSecondForDate2 = calendar2.getTimeInMillis();

			// To calculate difference in days
			long diffInMilis = miliSecondForDate2 - miliSecondForDate1;
			long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);
			dateIncr = diffInDays + 2;
			// Actual difference in days
			//System.out.println("Difference in Days : " + diffInDays);

			if (dateIncr >= 0 && dateIncr <= 21) {
				mGenericWait();
				// driver.findElement(By.id("entity.cfcAttachments0.attPath")).clear();
				// mGenericWait();
				driver.findElement(By.id("entity.cfcAttachments0.attPath"))
				.sendKeys(uploadPath);
				// method to delete file

	 * for (int second = 0;; second++) { if (second >= 60)
	 * fail("timeout"); try { if
	 * (driver.findElement(By.id("0_file_0")).isDisplayed()) break;
	 * } catch (Exception e) {} Thread.sleep(1000); }
	 * driver.findElement(By.id("0_file_0")).click();

				// mGenericWait();
				// driver.findElement(By.id("entity.cfcAttachments1.attPath")).clear();

			}

			if (dateIncr >= 22 && dateIncr <= 30) {
				mGenericWait();
				// driver.findElement(By.id("entity.cfcAttachments2.attPath")).clear();
				// mGenericWait();
				driver.findElement(By.id("entity.cfcAttachments1.attPath"))
				.sendKeys(uploadPath);
				mGenericWait();

			}

			if (dateIncr >= 31 && dateIncr <= 365) {
				mGenericWait();
				// driver.findElement(By.id("entity.cfcAttachments4.attPath")).clear();
				// mGenericWait();
				driver.findElement(By.id("entity.cfcAttachments2.attPath"))
				.sendKeys(uploadPath);
				mGenericWait();

				driver.findElement(By.id("entity.cfcAttachments4.attPath"))
				.sendKeys(uploadPath);
				mGenericWait();
			}

			if (dateIncr >= 365 && dateIncr <= 999999999) {
				mGenericWait();
				// driver.findElement(By.id("entity.cfcAttachments5.attPath")).clear();
				// mGenericWait();
				driver.findElement(By.id("entity.cfcAttachments3.attPath"))
				.sendKeys(uploadPath);
				mGenericWait();

				driver.findElement(By.id("entity.cfcAttachments5.attPath"))
				.sendKeys(uploadPath);
				mGenericWait();
			}
			if (dateIncr > 999999999) {
				//System.out.println("invalid date to upload");
			}

			// To display list of files uploaded
			mGenericWait();
			List<WebElement> allElements0 = driver.findElements(By
					.id("file_list_0"));
			for (WebElement element0 : allElements0) {
				//System.out.println(element0.getText());
			}

			mGenericWait();
			List<WebElement> allElements1 = driver.findElements(By
					.id("file_list_1"));
			for (WebElement element1 : allElements1) {
				//System.out.println(element1.getText());
			}

			mGenericWait();
			List<WebElement> allElements2 = driver.findElements(By
					.id("file_list_2"));
			for (WebElement element2 : allElements2) {
				//System.out.println(element2.getText());
			}

			mGenericWait();
			List<WebElement> allElements3 = driver.findElements(By
					.id("file_list_3"));
			for (WebElement element3 : allElements3) {
				//System.out.println(element3.getText());
			}

			mGenericWait();
			List<WebElement> allElements4 = driver.findElements(By
					.id("file_list_4"));
			for (WebElement element4 : allElements4) {
				//System.out.println(element4.getText());
			}

			mGenericWait();
			List<WebElement> allElements5 = driver.findElements(By
					.id("file_list_5"));
			for (WebElement element5 : allElements5) {
				//System.out.println(element5.getText());
			}
		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wUploadBirthRegistration method");

		}
	}*/

	/*// Master for birth registration upload
	public void mUploadBirthRegistration(String uploadPath) {
		try {
			preAction("Uploading document");
			wUploadBirthRegistration(uploadPath);
			postAction("Document uploaded");
		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mUploadBirthRegistration method");
		}

	}

	// Wrapper upload method for birth registration with certificate
	private void wUploadBirthRegAndCert(String uploadPath) {
		try {
			// To get system date
			int currentyear = Calendar.getInstance().get(Calendar.YEAR);
			int currentmonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
			int currentday = Calendar.getInstance().get(Calendar.DATE);

			Calendar calendar1 = Calendar.getInstance();
			Calendar calendar2 = Calendar.getInstance();

			calendar1.set(Integer.parseInt(date.substring(6, 10)),
					Integer.parseInt(date.substring(3, 5)),
					Integer.parseInt(date.substring(0, 2)));
			calendar2.set(currentyear, currentmonth, currentday);

			long miliSecondForDate1 = calendar1.getTimeInMillis();
			long miliSecondForDate2 = calendar2.getTimeInMillis();

			// To calculate difference in days
			long diffInMilis = miliSecondForDate2 - miliSecondForDate1;
			long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);
			dateIncr = diffInDays + 2;

			//System.out.println("Difference in Days : " + diffInDays);
			if (dateIncr >= 0 && dateIncr <= 21) {
				// mGenericWait();
				// driver.findElement(By.id("entity.cfcAttachments0.attPath")).clear();
				mGenericWait();
				//driver.findElement(By.id("entity.cfcAttachments3.attPath")).sendKeys(uploadPath);
				//mGenericWait();
				driver.findElement(By.id("entity.cfcAttachments0.attPath")).sendKeys(uploadPath);

				// method to delete file

	 * for (int second = 0;; second++) { if (second >= 60)
	 * fail("timeout"); try { if
	 * (driver.findElement(By.id("0_file_0")).isDisplayed()) break;
	 * } catch (Exception e) {} Thread.sleep(1000); }
	 * driver.findElement(By.id("0_file_0")).click();

			}

			if (dateIncr >= 22 && dateIncr <= 30) {

	 * mGenericWait();
	 * driver.findElement(By.id("entity.cfcAttachments1.attPath"
	 * )).clear();

				mGenericWait();
				driver.findElement(By.id("entity.cfcAttachments1.attPath")).sendKeys(uploadPath);
				mGenericWait();
			}

			if (dateIncr >= 31 && dateIncr <= 365) {

	 * mGenericWait();
	 * driver.findElement(By.id("entity.cfcAttachments2.attPath"
	 * )).clear();

				mGenericWait();
				mUpload("id", "entity.cfcAttachments2.attPath", uploadPath);
				//driver.findElement(By.id("entity.cfcAttachments2.attPath")).sendKeys(uploadPath);
				mGenericWait();
			}

	 * if (dateIncr>=31 && dateIncr<=999999999) { mGenericWait();
	 * driver.
	 * findElement(By.id("entity.cfcAttachments4.attPath")).clear();
	 * mGenericWait();
	 * driver.findElement(By.id("entity.cfcAttachments4.attPath"
	 * )).sendKeys(uploadPath); mGenericWait(); }


			if (dateIncr >= 366 && dateIncr <= 999999999) {

	 * mGenericWait();
	 * driver.findElement(By.id("entity.cfcAttachments3.attPath"
	 * )).clear();

				mGenericWait();

				driver.findElement(By.id("entity.cfcAttachments3.attPath")).sendKeys(uploadPath);
				mGenericWait();
			}

			// To display list of files uploaded
			mGenericWait();
			List<WebElement> allElements0 = driver.findElements(By
					.id("file_list_0"));
			for (WebElement element0 : allElements0) {
				//System.out.println(element0.getText());
			}

			mGenericWait();
			List<WebElement> allElements1 = driver.findElements(By
					.id("file_list_1"));
			for (WebElement element1 : allElements1) {
				//System.out.println(element1.getText());
			}

			mGenericWait();
			List<WebElement> allElements2 = driver.findElements(By
					.id("file_list_2"));
			for (WebElement element2 : allElements2) {
				//System.out.println(element2.getText());
			}

			mGenericWait();
			List<WebElement> allElements3 = driver.findElements(By
					.id("file_list_3"));
			for (WebElement element3 : allElements3) {
				//System.out.println(element3.getText());
			}

			mGenericWait();
			List<WebElement> allElements4 = driver.findElements(By
					.id("file_list_4"));
			for (WebElement element4 : allElements4) {
				//System.out.println(element4.getText());
			}
		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wUploadBirthRegAndCert method");
		}
	}*/

	/*// Master upload for birth registration with certificate
	public void mUploadBirthRegAndCert(String uploadPath)
			throws InterruptedException {
		try {
			preAction("Uploading document");
			wUploadBirthRegAndCert(uploadPath);
			postAction("Document uploaded");
		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mUploadBirthRegAndCert method");
		}

	}*/

	/*// Wrapper upload for death registration
	private void wUploadDeathRegistration(String uploadPath) {
		try {
			// To get system date
			int currentyear = Calendar.getInstance().get(Calendar.YEAR);
			int currentmonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
			int currentday = Calendar.getInstance().get(Calendar.DATE);

			Calendar calendar1 = Calendar.getInstance();
			Calendar calendar2 = Calendar.getInstance();

			calendar1.set(Integer.parseInt(date.substring(6, 10)),
					Integer.parseInt(date.substring(3, 5)),
					Integer.parseInt(date.substring(0, 2)));
			calendar2.set(currentyear, currentmonth, currentday);

			long miliSecondForDate1 = calendar1.getTimeInMillis();
			long miliSecondForDate2 = calendar2.getTimeInMillis();

			// To calculate difference in days
			long diffInMilis = miliSecondForDate2 - miliSecondForDate1;
			long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);
			dateIncr = diffInDays + 2;

			//System.out.println("Difference in Days : " + diffInDays);
			//
			if (dateIncr >= 0 && dateIncr <= 21) {

	 * mGenericWait();
	 * driver.findElement(By.id("entity.cfcAttachments0.attPath"
	 * )).clear();

				mGenericWait();
				driver.findElement(By.id("entity.cfcAttachments0.attPath"))
				.sendKeys(uploadPath);
				mGenericWait();

				mGenericWait();
				driver.findElement(By.id("entity.cfcAttachments1.attPath"))
				.sendKeys(uploadPath);
				mGenericWait();

				// method to delete file

	 * for (int second = 0;; second++) { if (second >= 60)
	 * fail("timeout"); try { if
	 * (driver.findElement(By.id("0_file_0")).isDisplayed()) break;
	 * } catch (Exception e) {} Thread.sleep(1000); }
	 * driver.findElement(By.id("0_file_0")).click();


			}

			if (dateIncr >= 22 && dateIncr <= 30) {
				//

				mGenericWait();
				driver.findElement(By.id("entity.cfcAttachments2.attPath"))
				.sendKeys(uploadPath);
				mGenericWait();
				//

				mGenericWait();
				driver.findElement(By.id("entity.cfcAttachments3.attPath"))
				.sendKeys(uploadPath);
				mGenericWait();
				//


	 * mGenericWait();
	 * driver.findElement(By.id("entity.cfcAttachments4.attPath"
	 * )).sendKeys(uploadPath); mGenericWait();

			}

			if (dateIncr >= 31 && dateIncr <= 365) {

				mGenericWait();
				driver.findElement(By.id("entity.cfcAttachments4.attPath"))
				.sendKeys(uploadPath);
				mGenericWait();
			}

			if (dateIncr >= 366 && dateIncr <= 999999999) {

				mGenericWait();
				driver.findElement(By.id("entity.cfcAttachments5.attPath"))
				.sendKeys(uploadPath);
				mGenericWait();
			}

			if (dateIncr > 999999999) {
				//System.out.println("invalid date to upload");
			}
			// To display list of files uploaded
			mGenericWait();
			List<WebElement> allElements0 = driver.findElements(By
					.id("file_list_0"));
			for (WebElement element0 : allElements0) {
				//System.out.println(element0.getText());
			}

			mGenericWait();
			List<WebElement> allElements3 = driver.findElements(By
					.id("file_list_3"));
			for (WebElement element3 : allElements3) {
				//System.out.println(element3.getText());
			}

			mGenericWait();
			List<WebElement> allElements4 = driver.findElements(By
					.id("file_list_4"));
			for (WebElement element4 : allElements4) {
				//System.out.println(element4.getText());
			}

			mGenericWait();
			List<WebElement> allElements5 = driver.findElements(By
					.id("file_list_5"));
			for (WebElement element5 : allElements5) {
				//System.out.println(element5.getText());
			}

			mGenericWait();
			List<WebElement> allElements6 = driver.findElements(By
					.id("file_list_6"));
			for (WebElement element6 : allElements6) {
				//System.out.println(element6.getText());
			}

			mGenericWait();
			List<WebElement> allElements7 = driver.findElements(By
					.id("file_list_7"));
			for (WebElement element7 : allElements7) {
				//System.out.println(element7.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("files not uploaded");
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wUploadDeathRegistration method");
		}
	}

	// Master upload for death registration
	public void mUploadDeathRegistration(String uploadPath) {
		try {

			preAction("Uploading document");
			wUploadDeathRegistration(uploadPath);
			postAction("Document uploaded");

		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mUploadDeathRegistration method");
		}
	}

	// Wrapper upload for death registration with certificate
	private void wUploadDeathRegAndCert(String uploadPath) {
		try {
			// To get system date
			int currentyear = Calendar.getInstance().get(Calendar.YEAR);
			int currentmonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
			int currentday = Calendar.getInstance().get(Calendar.DATE);

			Calendar calendar1 = Calendar.getInstance();
			Calendar calendar2 = Calendar.getInstance();

			calendar1.set(Integer.parseInt(date.substring(6, 10)),
					Integer.parseInt(date.substring(3, 5)),
					Integer.parseInt(date.substring(0, 2)));
			calendar2.set(currentyear, currentmonth, currentday);
			long miliSecondForDate1 = calendar1.getTimeInMillis();
			long miliSecondForDate2 = calendar2.getTimeInMillis();

			// To calculate difference in days
			long diffInMilis = miliSecondForDate2 - miliSecondForDate1;
			long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);
			dateIncr = diffInDays + 2;

			//System.out.println("Difference in Days : " + diffInDays);
			//
			if (dateIncr >= 0 && dateIncr <= 21) {

				mGenericWait();
				driver.findElement(By.id("entity.cfcAttachments0.attPath"))
				.sendKeys(uploadPath);
				mGenericWait();
				// method to delete file

	 * for (int second = 0;; second++) { if (second >= 60)
	 * fail("timeout"); try { if
	 * (driver.findElement(By.id("0_file_0")).isDisplayed()) break;
	 * } catch (Exception e) {} mGenericWait(); }
	 * driver.findElement(By.id("0_file_0")).click();

			}

			if (dateIncr >= 22 && dateIncr <= 30) {

				mGenericWait();
				driver.findElement(By.id("entity.cfcAttachments1.attPath"))
				.sendKeys(uploadPath);
				mGenericWait();

			}

			if (dateIncr >= 31 && dateIncr <= 365) {

				mGenericWait();
				driver.findElement(By.id("entity.cfcAttachments2.attPath"))
				.sendKeys(uploadPath);
				mGenericWait();
			}

			if (dateIncr >= 366 && dateIncr <= 999999999) {

				mGenericWait();
				driver.findElement(By.id("entity.cfcAttachments3.attPath"))
				.sendKeys(uploadPath);
				mGenericWait();
			}
			// method to display uploaded files
			mGenericWait();
			List<WebElement> allElements0 = driver.findElements(By
					.id("file_list_0"));
			for (WebElement element0 : allElements0) {
				//System.out.println(element0.getText());
			}

			mGenericWait();
			List<WebElement> allElements1 = driver.findElements(By
					.id("file_list_1"));
			for (WebElement element1 : allElements1) {
				//System.out.println(element1.getText());
			}

			mGenericWait();
			List<WebElement> allElements2 = driver.findElements(By
					.id("file_list_2"));
			for (WebElement element2 : allElements2) {
				//System.out.println(element2.getText());
			}

			mGenericWait();
			List<WebElement> allElements3 = driver.findElements(By
					.id("file_list_3"));
			for (WebElement element3 : allElements3) {

				//System.out.println(element3.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("files not uploaded");
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wUploadDeathRegAndCert method");
		}
	}

	// Master upload for death registration with certificate
	public void mUploadDeathRegAndCert(String uploadPath) {

		try {

			preAction("Uploading document");
			wUploadDeathRegAndCert(uploadPath);
			postAction("Document uploaded");

		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mUploadDeathRegAndCert method");
		}
	}*/

	// Wrapper method for creating module wise folder
	// Creates a module wise folder(with time-stamp) and sub folders, accepts a
	// string as module name
	private void wCreateModuleFolder(String moduleName, String className) {


		try{
			// Part of Method for creating a folder and path
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH.mm");
			moduleTimestamp = dateFormat.format(cal.getTime());

			//System.out.println(props.getProperty("modulePath") + moduleName+ moduleTimestamp);


			File file = new File(props.getProperty("modulePath") + moduleName
					+ moduleTimestamp);

			if (!file.exists()) {
				if (file.mkdir()) {
					//System.out.println("Directory is created!");
				} else {
					//System.out.println("Failed to create directory!");
				}
			} else {
				//System.out.println("Dir Exists");
			}


			//System.out.println("After File Creation");

			// Part of Method for creating a folder and path

			moduleClipboardata = props.getProperty("modulePath") + moduleName
					+ moduleTimestamp + "\\";

		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wCreateModuleFolder method");
		}

	}

	// Master method for creating module folder
	public void mCreateModuleFolder(String moduleName, String className) {

		try {
			preAction("Creating module folder");
			wCreateModuleFolder(moduleName, className);
			postAction("Module folder created");

		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mCreateModuleFolder method");
		}
	}

	// Wrapper method for creating artefacts folders such as documents and
	// screenshots
	private void wCreateArtefactsFolder(String moduleName) {

		try {
int s=CurrentinvoCount+1;
			String methodNamePath = mGetPropertyFromFile("modulePath")
					+ moduleName + moduleTimestamp + "\\" + thisMethodName
					+ "\\";

			// Create folders for artefacts and screenshots
			//System.out.println(methodNamePath + "Documents");
			//System.out.println(methodNamePath + "Screenshots"+s);
			//System.out.println(methodNamePath + "Images");
			try {
				File docfile = new File(methodNamePath + "Documents");
				
				//swap change
				
				File scrfile = new File(methodNamePath + "Screenshots");
				//for eip swapnil 
				File scrfile1 = new File(methodNamePath+ "\\" + "Screenshots" + "Screenshots"+s);
				File imgfile=new File(methodNamePath + "Images");
				
				if (!docfile.exists() && !scrfile.exists() && !imgfile.exists()) {
					if (docfile.mkdirs() && scrfile.mkdirs() && imgfile.mkdirs()) {
						System.out.println("Directories created!");
						File docfile1 = new File(methodNamePath+ "\\" + "Documents"+ "\\" +"Documents"+s);	
						docfile1.mkdir();
					} else {
						System.out.println("Failed to create directories!");
					}
				} else {
					System.out.println("Dir Exists");
					File docfile1 = new File(methodNamePath+ "\\" + "Documents"+ "\\" +"Documents"+s);	
					docfile1.mkdir();
				}
			} catch (Exception e) {
				System.out.println(e.toString());

			}
			

			screenShotsPath = methodNamePath + "Screenshots"+s + "\\";
			documentsPath = methodNamePath + "Documents"+s+ "\\";
			imagepath=methodNamePath + "Images" + "\\";

			System.out.println("Method Name path:::" + methodNamePath);
			System.out.println("documentsPath"+documentsPath);


		} catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wCreateArtefactsFolder method");
		}

	}

	// Master method for creating artefacts folder
	public void mCreateArtefactsFolder(String moduleName) {

		try {

			preAction("Creating artefacts folder");
			wCreateArtefactsFolder(moduleName);
			postAction("Created artefacts folder");

		} catch (Exception e) {

			e.printStackTrace();
			//System.out.println("Failed to create artefacts folder");
			throw new MainetCustomExceptions("Error in mCreateArtefactsFolder method");
		}

	}

	// Sunil D Sonmale 10-09-2015
	// Wrapper method for Downlaoding a PDF from browser at a specific path

	private void wDownloadFile(String className) {
		try {

			Thread.sleep(4000);
int s2=CurrentinvoCount+1;
			// Part of Method for creating a folder and path
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"dd-MM-yyyy HH.mm");
			String dateStr = dateFormat.format(cal.getTime());
			// finalDocPath=moduleClipboardata+getClassNames().get(methodCounter)+
			// "\\"+ "Documents"+ "\\";
			//swapp download in new folder
			finalDocPath = moduleClipboardata + thisMethodName + "\\"
					+ "Documents" + "\\"+ "Documents"+s2+"\\";

			// //System.out.println("METHOD COUNT::"+methodCounter);
			//System.out.println("FINAL DOC PATH:::" + finalDocPath);

			//System.out.println("After File Creation");

			// Part of Method for creating a folder and path

			String clipboardata = finalDocPath;

			// Setting clipboard with file location
			setClipboardData(clipboardata);
			// native key strokes for CTRL, V and ENTER keys

			robot = new Robot();

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_S);
			// CTRL+Z is now pressed (receiving application should see a
			// "key down" event.)
			robot.keyRelease(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			Thread.sleep(3000);


			robot.keyPress(KeyEvent.VK_HOME);
			robot.keyRelease(KeyEvent.VK_HOME);

			robot.keyPress(KeyEvent.VK_HOME);
			robot.keyRelease(KeyEvent.VK_HOME);


			Thread.sleep(1000);


			/*robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);

			/*robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
>>>>>>> .r366

				Thread.sleep(1000);

				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);

				Thread.sleep(1000);

				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);

				Thread.sleep(1000);

				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);

				Thread.sleep(1000);

				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);

<<<<<<< .mine
<<<<<<< .mine
				Thread.sleep(1000);*/



			Thread.sleep(1000);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);

			// Thread.sleep(1000);

			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			Thread.sleep(1000);

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);


			/*for (int i = 0; i < 8; i++) {
					Thread.sleep(1000);
=======
			/*for (int i = 0; i < 8; i++) {
				Thread.sleep(1000);
>>>>>>> .r366

					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
				}

				Thread.sleep(1000);

<<<<<<< .mine
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);*/

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

			Thread.sleep(2000);


		} catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wDownloadFile method");
		}
	}

	public static void setClipboardData(String string) {

		try{
			// StringSelection is a class that can be used for copy and paste
			// operations.
			StringSelection stringSelection = new StringSelection(string);
			Toolkit.getDefaultToolkit().getSystemClipboard()
			.setContents(stringSelection, null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in setClipboardData method");
		}
	}

	// Master method for Downlaoding a PDF from browser at a specific path

	public void mDownloadFile(String className) {

		try {
			preAction("Downloading file");
			wDownloadFile(className);
			postAction("File downloaded");

		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mDownloadFile method");
		}

	}

	private void wTakeScreenShot() throws IOException {

		try{
int s1=CurrentinvoCount+1;
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			// String currentDir = System.getProperty("Path");
			// Part of Method for creating a folder and path
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH.mm.ss");
			String dateStr = dateFormat.format(cal.getTime());

			//System.out.println("After File Creation");

			// finalScrShotPath=moduleClipboardata+"\\"+getClassNames().get(methodCounter)+
			// "\\"+ "Screenshots"+ "\\";
			//swap change to save screenshot in path of excution
			
			finalScrShotPath = moduleClipboardata + "\\" + thisMethodName +  "\\"+ "Screenshots"+"\\"+ "Screenshots"+s1 + "\\";
			// //System.out.println("METHOD COUNT::"+methodCounter);

			//System.out.println("FINAL SCREENSHOT PATH:::" + finalScrShotPath);
			FileUtils.copyFile(scrFile, new File(finalScrShotPath + dateStr+ ".png"));
			mCustomWait(1000);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wTakeScreenShot method");
		}

	}


	public void mTakeScreenShot() throws IOException{

		try{
			preAction("Taking screenshot");
			wTakeScreenShot();
			postAction("Screenshot taken");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mTakeScreenShot method");
		}
	}

	// sneha s
	// method to scroll

	private void wscroll(int strtpara, int endpara) {
		try{
			JavascriptExecutor jse;
			jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(" + strtpara + "," + endpara + ")",
					"");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wscroll method");
		}
	}

	//
	public void mscroll(int strtpara, int endpara) {

		try{
			preAction("Scrolling down");
			wscroll(strtpara, endpara);
			postAction("Scrolled down");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mscroll method");
		}
	}
	// Methods for PDF reading
	// Wrapper for challan PDF reader
	// Sunil D Sonmale 16-01-2015
	/*private void wChallanPDFReader() {

		String name = " ";
		int flag = 0;
		// MethodName="ChallanPDFreader";
		try {

			// Pdf reading starts
			Thread.sleep(25000);

			// Create object to handle windows
			tabs3 = new ArrayList<String>(driver.getWindowHandles());

			// Print array of all windows
			//System.out.println(tabs3);

			// Get and print no of windows
			int noOfTabs = tabs3.size();

			//System.out.println("No of Tabs is: " + noOfTabs);

			// If no of windows is 3 this executes
			if (noOfTabs == 2) {
				tabs3 = new ArrayList<String>(driver.getWindowHandles());

				driver.switchTo().window(tabs3.get(1));

				firstpdf = driver.getCurrentUrl();

				// //System.out.println("PDF url is: "+url);
				String[] arr = firstpdf.split("/PDFReport/");
				//System.out.println(arr[1]);

				String[] arr1 = arr[1].split(".pdf");
				//System.out.println(arr1[0]);

				firstpdf = arr[0] + "/PDFReport/" + arr1[0] + ".pdf";
				// //System.out.println(firstpdf);
				//System.out.println("PDF url is: " + firstpdf);

				driver.switchTo().window(tabs3.get(1)).close();

				driver.switchTo().window(tabs3.get(0));

				newdownloadmethod(firstpdf);

				pdfoutput = output;
				//System.out.println("Output of single pdf is: " + pdfoutput);

			}
			// If no of windows is 3 this executes
			if (noOfTabs == 3) {
				tabs3 = new ArrayList<String>(driver.getWindowHandles());

				driver.switchTo().window(tabs3.get(2));
mCustomWait(8000);
				firstpdf = driver.getCurrentUrl();

				// //System.out.println("PDF url is: "+url);
				String[] arr = firstpdf.split("/PDFReport/");
				//System.out.println(arr[1]);

				String[] arr1 = arr[1].split(".pdf");
				//System.out.println(arr1[0]);

				firstpdf = arr[0] + "/PDFReport/" + arr1[0] + ".pdf";
				// //System.out.println(firstpdf);
				//System.out.println("PDF url is: " + firstpdf);

				driver.switchTo().window(tabs3.get(2)).close();

				mGenericWait();
				driver.switchTo().window(tabs3.get(1));

				newdownloadmethod(firstpdf);

				pdfoutput = output;
				//System.out.println("Output of single pdf is: " + pdfoutput);

			}

			// If no of windows is 4 this executes
			if (noOfTabs == 4) {
				tabs3 = new ArrayList<String>(driver.getWindowHandles());
				// Going to 3rd tab

				Thread.sleep(4000);

				driver.switchTo().window(tabs3.get(2));

				firstpdf = driver.getCurrentUrl();

				//System.out.println("Second PDF url is: " + url);
				String[] arr = firstpdf.split("/PDFReport/");
				//System.out.println(arr[1]);

				String[] arr1 = arr[1].split(".pdf");
				//System.out.println(arr1[0]);

				firstpdf = arr[0] + "/PDFReport/" + arr1[0] + ".pdf";
				//System.out.println(firstpdf);
				//System.out.println("First PDF url is: " + firstpdf);

				// getProperPdfurl(firstpdf);

				// Going to 4th tab
				Thread.sleep(4000);

				driver.switchTo().window(tabs3.get(3));

				// getProperPdfurl(secondpdf);

				secondpdf = driver.getCurrentUrl();

				//System.out.println("Second PDF url is: " + url);
				arr = secondpdf.split("/PDFReport/");
				//System.out.println(arr[1]);

				arr1 = arr[1].split(".pdf");
				//System.out.println(arr1[0]);

				secondpdf = arr[0] + "/PDFReport/" + arr1[0] + ".pdf";
				//System.out.println(secondpdf);
				//System.out.println("First PDF url is: " + secondpdf);

				driver.switchTo().window(tabs3.get(2)).close();

				Thread.sleep(2000);

				driver.switchTo().window(tabs3.get(3)).close();

				Thread.sleep(2000);

				driver.switchTo().window(tabs3.get(1));

				// //System.out.println("Please see "+secondpdf);

				newdownloadmethod(secondpdf);

				pdfoutput = output;

				driver.switchTo().window(tabs3.get(1));

				newdownloadmethod(firstpdf);

				pdfoutput1 = output;

				//System.out.println("Output of first pdf is:\n " + pdfoutput);
				//System.out.println("Output of second pdf is:\n " + pdfoutput1);
			}

		} catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wChallanPDFReader method");
		}
	}*/

	private void wChallanPDFReader() {

		String name = " ";
		int flag = 0;
		// MethodName="ChallanPDFreader";
		try {
System.err.println("iam in challan reader method===>");
			// Pdf reading starts
			Thread.sleep(5000);

			// Create object to handle windows
			tabs3 = new ArrayList<String>(driver.getWindowHandles());

			// Print array of all windows
			//System.out.println(tabs3);

			// Get and print no of windows
			int noOfTabs = tabs3.size();

			//System.out.println("No of Tabs is: " + noOfTabs);

			// If no of windows is 3 this executes
			if (noOfTabs == 2) {
				tabs3 = new ArrayList<String>(driver.getWindowHandles());
				System.err.println("iam in challan reader method===>"+"no of tab 2");
				driver.switchTo().window(tabs3.get(1));

				firstpdf = driver.getCurrentUrl();

				// //System.out.println("PDF url is: "+url);
				String[] arr = firstpdf.split("/PDFReport/");
				//System.out.println(arr[1]);

				String[] arr1 = arr[1].split(".pdf");
				//System.out.println(arr1[0]);

				firstpdf = arr[0] + "/PDFReport/" + arr1[0] + ".pdf";
				// //System.out.println(firstpdf);
				//System.out.println("PDF url is: " + firstpdf);

				driver.switchTo().window(tabs3.get(1)).close();

				driver.switchTo().window(tabs3.get(0));

				newdownloadmethod(firstpdf);

				pdfoutput = output;
				//System.out.println("Output of single pdf is: " + pdfoutput);

			}
			// If no of windows is 3 this executes
			if (noOfTabs == 3) {
				tabs3 = new ArrayList<String>(driver.getWindowHandles());
				System.err.println("iam in challan reader method===>"+"no of tab 3");
				driver.switchTo().window(tabs3.get(2));

				firstpdf = driver.getCurrentUrl();

				// //System.out.println("PDF url is: "+url);
				String[] arr = firstpdf.split("/PDFReport/");
				//System.out.println(arr[1]);

				String[] arr1 = arr[1].split(".pdf");
				//System.out.println(arr1[0]);

				firstpdf = arr[0] + "/PDFReport/" + arr1[0] + ".pdf";
				// //System.out.println(firstpdf);
				//System.out.println("PDF url is: " + firstpdf);

				driver.switchTo().window(tabs3.get(2)).close();

				mGenericWait();
				driver.switchTo().window(tabs3.get(1));

				newdownloadmethod(firstpdf);

				pdfoutput = output;
				System.out.println("Output of single pdf is: " + pdfoutput);

			}

			// If no of windows is 4 this executes
			if (noOfTabs == 4) {
				tabs3 = new ArrayList<String>(driver.getWindowHandles());
				// Going to 3rd tab
				System.err.println("iam in challan reader method===>"+"no of tab 4");
				Thread.sleep(4000);

				driver.switchTo().window(tabs3.get(2));

				firstpdf = driver.getCurrentUrl();

				//System.out.println("Second PDF url is: " + url);
				String[] arr = firstpdf.split("/PDFReport/");
				//System.out.println(arr[1]);

				String[] arr1 = arr[1].split(".pdf");
				//System.out.println(arr1[0]);

				firstpdf = arr[0] + "/PDFReport/" + arr1[0] + ".pdf";
				//System.out.println(firstpdf);
				//System.out.println("First PDF url is: " + firstpdf);

				// getProperPdfurl(firstpdf);

				// Going to 4th tab
				Thread.sleep(4000);

				driver.switchTo().window(tabs3.get(3));

				// getProperPdfurl(secondpdf);

				secondpdf = driver.getCurrentUrl();

				//System.out.println("Second PDF url is: " + url);
				arr = secondpdf.split("/PDFReport/");
				//System.out.println(arr[1]);

				arr1 = arr[1].split(".pdf");
				//System.out.println(arr1[0]);

				secondpdf = arr[0] + "/PDFReport/" + arr1[0] + ".pdf";
				//System.out.println(secondpdf);
				//System.out.println("First PDF url is: " + secondpdf);

				driver.switchTo().window(tabs3.get(2)).close();

				Thread.sleep(2000);

				driver.switchTo().window(tabs3.get(3)).close();

				Thread.sleep(2000);

				driver.switchTo().window(tabs3.get(1));

				// //System.out.println("Please see "+secondpdf);

				newdownloadmethod(secondpdf);

				pdfoutput = output;

				driver.switchTo().window(tabs3.get(1));

				newdownloadmethod(firstpdf);

				pdfoutput1 = output;

				//System.out.println("Output of first pdf is:\n " + pdfoutput);
				//System.out.println("Output of second pdf is:\n " + pdfoutput1);
			}

		} catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wChallanPDFReader method");
		}
	}

	public void newdownloadmethod(String url)  {

		try{

			// Code to open a new tab and enter the required PDF url and download
			// that url
			// Starts

			//System.out.println("inside new downloadmethod");



			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
			robot = new Robot();

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			// CTRL+L is now pressed (You should now move to the browser address
			// bar.)
			robot.keyRelease(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);


			//System.out.println("prssed control t");

			Thread.sleep(3000);

			tabs3 = new ArrayList<String>(driver.getWindowHandles());

			// to be change
			driver.switchTo().window(tabs3.get(1));
			driver.switchTo().window(tabs3.get(2));

			setClipboardData(url);

			robot = new Robot();

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_L);
			// CTRL+L is now pressed (You should now move to the browser address
			// bar.)
			robot.keyRelease(KeyEvent.VK_L);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			Thread.sleep(2000);

			robot.keyPress(KeyEvent.VK_BACK_SPACE);
			robot.keyRelease(KeyEvent.VK_BACK_SPACE);

			Thread.sleep(2000);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			// CTRL+Z is now pressed (Entering the newly created url)
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			Thread.sleep(2000);

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

			Thread.sleep(8000);

			downloadreadpdf();
			Thread.sleep(4000);
			driver.close();

			// to be change
			// driver.switchTo().window(tabs3.get(0));
			driver.switchTo().window(tabs3.get(1));

			// ENDS

		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in newdownloadmethod method");
		}
	}

	// Method to enter proper pdf url in b
	public void getProperPdfurl(String url)

	{
		try{
			url = driver.getCurrentUrl();

			//System.out.println("Second PDF url is: " + url);
			String[] arr = url.split("/PDFReport/");
			//System.out.println(arr[1]);

			String[] arr1 = arr[1].split(".pdf");
			//System.out.println(arr1[0]);

			url = arr[0] + "/PDFReport/" + arr1[0] + ".pdf";
			//System.out.println(url);
			//System.out.println("First PDF url is: " + url);
			// return url;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in getProperPdfurl method");
		}
	}

	public void downloadreadpdf() {
		// Part of downloading and reading a pdf
		try {

			URL url = new URL(driver.getCurrentUrl());
			//System.out.println(url);

			String urll = url.toString();
			//System.out.println("hello");
			//System.out.println(urll);

			String[] arr = urll.split("/PDFReport/");
			urll = arr[1];
			//System.out.println("This is the name of the PDF file" + urll);

			// Downloading the pdf fill

			mDownloadFile(myClassName);

			String finalDocDownPath = finalDocPath + urll;
			//System.out.println(finalDocDownPath);

			driver.get(finalDocDownPath);

			mGenericWait();

			/*
			 * setClipboardData(finalDocDownPath);
			 * 
			 * robot = new Robot();
			 * 
			 * robot.keyPress(KeyEvent.VK_CONTROL);
			 * robot.keyPress(KeyEvent.VK_L); // CTRL+L is now pressed (You
			 * should now move to the browser address bar.)
			 * robot.keyRelease(KeyEvent.VK_L);
			 * robot.keyRelease(KeyEvent.VK_CONTROL);
			 * 
			 * Thread.sleep(2000);
			 * 
			 * robot.keyPress(KeyEvent.VK_BACK_SPACE);
			 * robot.keyRelease(KeyEvent.VK_BACK_SPACE);
			 * 
			 * Thread.sleep(2000);
			 * 
			 * robot.keyPress(KeyEvent.VK_CONTROL);
			 * robot.keyPress(KeyEvent.VK_V); // CTRL+Z is now pressed (Entering
			 * the newly created url) robot.keyRelease(KeyEvent.VK_V);
			 * robot.keyRelease(KeyEvent.VK_CONTROL);
			 * 
			 * Thread.sleep(2000);
			 * 
			 * robot.keyPress(KeyEvent.VK_ENTER);
			 * robot.keyRelease(KeyEvent.VK_ENTER);
			 * 
			 * Thread.sleep(8000);
			 */

			// New code for handling https ENDS here
			URL ur = new URL(driver.getCurrentUrl());
			//System.out.println(ur);

			BufferedInputStream fileToParse = new BufferedInputStream(
					ur.openStream());

			PDFParser parser = new PDFParser(fileToParse);
			parser.parse();

			output = new PDFTextStripper().getText(parser.getPDDocument());
			// //System.out.println("Output of PDF is:\n"+output);
			// pdf reading ENDS
			
		}

		catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in downloadreadpdf method");
		}

	}

	// public void mChallanPDFReader(String pdfService,String appNo, String
	// applicantName,String birthRegNo, String dateofBirth, String childNm,
	// String parentsNm){

	public void mChallanPDFReader() {

		try{
			preAction("Reading challan PDF");

			wChallanPDFReader();

			postAction("Reading done");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mChallanPDFReader method");
		}
	}


	// Wrapper method to download the uploaded documents in BND and verify them
	// and mark them as authorized
	// By Madhuri Dawande
	private void wBndViewAndVerifyDocs(String docAutho) {

		try {

			WebElement table = driver.findElement(By.className("gridtable"));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount = rows.size();
			//System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

			// Iterate to get the value of each cell in table along with element
			// id
			int Rowno = 0;
			for (WebElement rowElement : rows) {
				List<WebElement> cols = rowElement.findElements(By.xpath("td"));
				int Columnno = 0;
				for (WebElement colElement : cols) {
					if (Columnno == 0) 
					{
						String srNo = rowElement.findElement(By.xpath("./td")).getText();
						mGenericWait();
						//System.out.println("Sr. No. is :"+srNo);
					}
					if (Columnno == 1) 
					{
						String docStatus = rowElement.findElement(By.xpath("./td[2]")).getText();
						mGenericWait();
						//System.out.println("Document is mandatory or not :"+docStatus);
					}
					if (Columnno == 2) 
					{
						rowElement.findElement(By.tagName("a")).click();
						mGenericWait();
						mDownloadFile(myClassName);
						mSwitchTabs();
						mGenericWait();
					}
					if (Columnno == 3) 
					{
						if(docAutho.equalsIgnoreCase("Yes"))
						{
							/*String abc = cols.get(2).findElement(By.tagName("input")).getAttribute("id");
						driver.findElement(By.id(abc)).click();
						mGenericWait();*/
							String abc=rowElement.findElement(By.xpath("./td/span/input")).getAttribute("id");
							//System.out.println("Id for verify is :"+abc);
							driver.findElement(By.id(abc)).click();
							mGenericWait();
							bnd_docAuthorised=true;
						}
						else
						{
							String abc=rowElement.findElement(By.xpath("./td/span[2]/input")).getAttribute("id");
							//System.out.println("Id for verify is :"+abc);
							driver.findElement(By.id(abc)).click();
							mGenericWait();
						}
					}
					if (Columnno == 4) {
						if(docAutho.equalsIgnoreCase("Yes"))
						{
							String xyz = rowElement.findElement(By.xpath("./td/input")).getAttribute("id");
							driver.findElement(By.id(xyz)).sendKeys("Approved");
							mGenericWait();

						}
						else
						{
							String xyz = rowElement.findElement(By.xpath("./td/input")).getAttribute("id");
							driver.findElement(By.id(xyz)).sendKeys("Rejected");
							mGenericWait();
						}
					}

					Columnno = Columnno + 1;
				}
				Rowno = Rowno + 1;
			}
			/*
			 * // Check for 1st row and then enter values if ((rwcount<=2) &&
			 * (plaintiffnameval.isEmpty())) {
			 * driver.findElement(By.id(plaintiffname)).clear();
			 * driver.findElement
			 * (By.id(plaintiffname)).sendKeys("Mr. Om Prakash Mehra");
			 * driver.findElement(By.id(plaintiffaddr)).clear();
			 * driver.findElement(By.id(plaintiffaddr)).sendKeys("Bhagalpur"); }
			 * // If it is not 1st row then click on add and then enter values
			 * else { Rowno=Rowno-1; String plaintiffnameid=pltfnm+Rowno; String
			 * plaintiffaddrid=pltfaddr+Rowno;
			 * driver.findElement(By.id(elementId)).click();
			 * driver.findElement(By
			 * .id(plaintiffnameid)).sendKeys("Mr. Rakesh Sharma");
			 * driver.findElement(By.id(plaintiffaddrid)).sendKeys("Bhagalpur");
			 * }
			 */

		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wBndViewAndVerifyDocs method");
		}
	}

	// Wrapper method to download the uploaded documents in BND and verify them
	// and mark them as authorized
	// By Madhuri Dawande
	/*
	 * private void wBndViewAndVerifyDocs(){
	 * 
	 * 
	 * try{
	 * 
	 * WebElement table = driver.findElement(By.className("gridtable"));
	 * //WebElement table =
	 * driver.findElement(By.xpath("\"//*[@id=\"1\"]/td[2]")); List<WebElement>
	 * rows = table.findElements(By.tagName("tr")); int rwcount =rows.size();
	 * //System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
	 * 
	 * // Iterate to get the value of each cell in table along with element id
	 * int Rowno=0; for(WebElement rowElement:rows) { List<WebElement>
	 * cols=rowElement.findElements(By.xpath("td")); int Columnno=0;
	 * //System.out.println("i m above columns"); for(WebElement colElement:cols)
	 * { if(Columnno==1) { //System.out.println("i m above a");
	 * driver.findElement(By.tagName("a")).click();
	 * 
	 * //cols.get(1).findElement(By.tagName("a")).click(); mGenericWait();
	 * //mDownloadFile(myClassName); // mGenericWait(); //mSwitchTabs();
	 * if(Columnno==2) { String
	 * abc=cols.get(2).findElement(By.tagName("input")).getAttribute("id");
	 * driver.findElement(By.id(abc)).click(); mGenericWait(); } if(Columnno==3)
	 * { String
	 * xyz=cols.get(3).findElement(By.tagName("input")).getAttribute("id");
	 * driver.findElement(By.id(xyz)).sendKeys("Approved"); mGenericWait(); }
	 * 
	 * Columnno=Columnno+1; } Rowno=Rowno+1; }
	 * 
	 * // Check for 1st row and then enter values if ((rwcount<=2) &&
	 * (plaintiffnameval.isEmpty())) {
	 * driver.findElement(By.id(plaintiffname)).clear();
	 * driver.findElement(By.id
	 * (plaintiffname)).sendKeys("Mr. Om Prakash Mehra");
	 * driver.findElement(By.id(plaintiffaddr)).clear();
	 * driver.findElement(By.id(plaintiffaddr)).sendKeys("Bhagalpur"); } // If
	 * it is not 1st row then click on add and then enter values else {
	 * Rowno=Rowno-1; String plaintiffnameid=pltfnm+Rowno; String
	 * plaintiffaddrid=pltfaddr+Rowno;
	 * driver.findElement(By.id(elementId)).click();
	 * driver.findElement(By.id(plaintiffnameid)).sendKeys("Mr. Rakesh Sharma");
	 * driver.findElement(By.id(plaintiffaddrid)).sendKeys("Bhagalpur"); }
	 * 
	 * 
	 * } //mSwitchTabs(); }catch(Exception e){
	 * 
	 * e.printStackTrace(); } }
	 */

	public void mBndViewAndVerifyDocs(String docAutho) {

		try {

			preAction("Downloading and verifying document");
			wBndViewAndVerifyDocs(docAutho);
			postAction("Downloaded and verified document");

		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mBndViewAndVerifyDocs method");
		}

	}

	// Methods for testNg

	private void wAssert(String actual, String expected, String message) {

		try { 

			if(actual.equals(expected)){

				System.err.println("Actual=============>"+actual);
				System.err.println("Expected==>"+expected);
				System.err.println("Result================>"+"Pass");

			}else
			{
				System.err.println("Actual=============>"+actual);
				System.err.println("Expected==>"+expected);
				System.err.println("Result================>"+"Fail");
			}


			bndRegErrorMsg.assertEquals(actual, expected, message);

			rtiAppErrorMsg.assertEquals(actual, expected, message);

			propTaxErrorMsg.assertEquals(actual, expected, message);

			tpErrorMsg.assertEquals(actual, expected, message);

			rnlErrorMsg.assertEquals(actual, expected, message);

			marketErrorMsg.assertEquals(actual, expected, message);

			lglErrorMsg.assertEquals(actual, expected, message);

			eipApp_m_errors.assertEquals(actual, expected, message);



			mCustomWait(100);

			//mTakeScreenShot();

		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wAssert method");

		}
	}

	public void mAssert(String actual, String expected, String message) {

		try {
			preAction("Performing assertion");
			wAssert(actual, expected, message);
			postAction("Assertion performed");

		} catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mAssert method");
		}
	}


	private void wAssert(ArrayList<String> actual, ArrayList<String> expected, String message) {

		try {
			//System.err.println("Actual=============>"+actual+"Expected==>"+expected+"Result================>"+"Pass");
			//	//System.out.println("Actual==>"+actual+"Expected==>"+expected+"Result==>"+"Pass");
			String a=actual.get(CurrentinvoCount).toString();
			String e=expected.get(CurrentinvoCount).toString();
			if(a.equals(e)){
				System.err.println("Actual=============>"+a);
				System.err.println("Expected==>"+e);
				System.err.println("Result================>"+"Pass");
				////System.out.println("Actual==>"+a+"Expected==>"+e+"Result==>"+"Pass");

			}else
			{
				System.err.println("Actual=============>"+a);
				System.err.println("Expected==>"+e);
				System.err.println("Result================>"+"Fail");

			}

			bndRegErrorMsg.assertEquals(actual, expected, message);

			rtiAppErrorMsg.assertEquals(actual, expected, message);

			propTaxErrorMsg.assertEquals(actual, expected, message);

			tpErrorMsg.assertEquals(actual, expected, message);

			rnlErrorMsg.assertEquals(actual, expected, message);

			marketErrorMsg.assertEquals(actual, expected, message);

			lglErrorMsg.assertEquals(actual, expected, message);

			mCustomWait(100);

		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wAssert method");

		}
	}

	public void mAssert(ArrayList<String> actual, ArrayList<String> expected, String message) {

		try {
			preAction("Performing assertion");
			wAssert(actual, expected, message);
			postAction("Assertion performed");

		} catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mAssert method");
		}
	}

	/*public void arraylistAssert(ArrayList<String> actual, ArrayList<String> expected, String message) 
	{
		try
		{
			propTaxErrorMsg.assertEquals(actual, expected, message);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in arraylistAssert method");
		}
	}*/

	private void wDownloadPDFFile() {
		try {

			// New Code for Reading from pdf
			// Start
			// Get window handles
			/*
			 * Set<String> winids = driver.getWindowHandles(); Iterator<String>
			 * iterate = winids.iterator(); String firstwindow=iterate.next();
			 * String secondwindow = iterate.next();
			 * //System.out.println(firstwindow);
			 * //System.out.println(secondwindow); //switch to pdf window
			 * //driver.switchTo().window(secondwindow);
			 * driver.switchTo().window(tabs2.get(2)); mGenericWait();
			 * System.err.println("IN PDF READER"); Thread.sleep(3000);
			 */

			Thread.sleep(4000);

			// Part of Method for creating a folder and path
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"dd-MM-yyyy HH.mm");
			String dateStr = dateFormat.format(cal.getTime());
			// finalDocPath=moduleClipboardata+getClassNames().get(methodCounter)+
			// "\\"+ "Documents"+ "\\";
			finalDocPath = moduleClipboardata + thisMethodName + "\\"
					+ "Documents" + "\\";
			// //System.out.println("METHOD COUNT::"+methodCounter);
			//System.out.println("FINAL DOC PATH:::" + finalDocPath);

			/*
			 * try{ File file = new File(props.getProperty("reportPathBND") +
			 * dateStr); //method get time
			 * 
			 * if (!file.exists()) { if (file.mkdir()) {
			 * //System.out.println("Directory is created!"); } else {
			 * //System.out.println("Failed to create directory!"); } } else{
			 * //System.out.println("Dir Exists"); } } catch(Exception e){
			 * //System.out.println(e.toString()); }
			 */
			//System.out.println("After File Creation");

			// Part of Method for creating a folder and path

			String clipboardata = finalDocPath;

			// Setting clipboard with file location
			setClipboardData(clipboardata);
			// native key strokes for CTRL, V and ENTER keys

			Robot robot = new Robot();

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_S);
			// CTRL+Z is now pressed (receiving application should see a
			// "key down" event.)
			robot.keyRelease(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			Thread.sleep(3000);

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			Thread.sleep(1000);

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			Thread.sleep(1000);

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			Thread.sleep(1000);

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			Thread.sleep(1000);

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			Thread.sleep(1000);

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

			Thread.sleep(1000);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);

			// Thread.sleep(1000);

			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			Thread.sleep(1000);

			/*
			 * robot.keyRelease(KeyEvent.VK_RIGHT);
			 * robot.keyRelease(KeyEvent.VK_RIGHT);
			 * 
			 * 
			 * robot.keyPress(KeyEvent.VK_BACK_SLASH);
			 * robot.keyRelease(KeyEvent.VK_BACK_SLASH);
			 * 
			 * Thread.sleep(1000);
			 * 
			 * clipboardata = "Test_Artefacts"; setClipboardData(clipboardata);
			 * 
			 * robot.keyPress(KeyEvent.VK_CONTROL);
			 * robot.keyPress(KeyEvent.VK_V);
			 * 
			 * // Thread.sleep(1000);
			 * 
			 * robot.keyRelease(KeyEvent.VK_V);
			 * robot.keyRelease(KeyEvent.VK_CONTROL);
			 * 
			 * 
			 * Thread.sleep(1000);
			 * 
			 * robot.keyRelease(KeyEvent.VK_RIGHT);
			 * robot.keyRelease(KeyEvent.VK_RIGHT);
			 */

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

			for (int i = 0; i < 8; i++) {
				Thread.sleep(1000);

				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
			}

			Thread.sleep(1000);

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

			Thread.sleep(8000);

			Thread.sleep(4000);

			driver.close();

			Thread.sleep(2000);

			// Switch to
			// driver.switchTo().window(firstwindow);

			driver.switchTo().window(tabs2.get(1));

		} catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wDownloadPDFFile method");
		}
	}

	public static void setClipboardDataPDF(String string) {

		try{
			// StringSelection is a class that can be used for copy and paste
			// operations.
			StringSelection stringSelection = new StringSelection(string);
			Toolkit.getDefaultToolkit().getSystemClipboard()
			.setContents(stringSelection, null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in setClipboardDataPDF method");
		}
	}

	// Master method for Downlaoding a PDF from browser at a specific path

	public void mDownloadPDFFile(/* String className */) {

		try {
			preAction("Downloading file");
			wDownloadPDFFile();
			postAction("File downloaded");

		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mDownloadPDFFile method");
		}
	}

	static Date start;
	static Date end;
	static long startExecTime;
	static long endExecTime;

	private static String wStartExecution() {
		String startDateStr = "";
		try {
//System.out.println("start=================");
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"dd/MM/yyyy HH:mm:ss");

			startDateStr = dateFormat.format(cal.getTime());

			log.info("Execution Start Time is " + startDateStr);

			dateFormat = new SimpleDateFormat("HH:mm:ss");

			// startExecTime=start.getTime();

		} catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wStartExecution method");
		}
		return startDateStr;

	}

	// master method to get time

	public static String mStartExecution() {

		try {
			// preAction("Getting Execution Start Time");
			mDeleteTempPropertyFiles();
			wStartExecution();
			// postAction("Got Execution Start Time");

		} catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mStartExecution method");
		}
		return wStartExecution();
	}

	private static String wEndExecution() {
		String endDateStr = "";
		try {

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			endDateStr = dateFormat.format(cal.getTime());
			log.info("Execution End Time is " + endDateStr);
			// //System.out.println("Path::::"+moduleClipboardata);
			// Runtime.getRuntime().exec("explorer.exe /select,"
			// +moduleClipboardata);

			// endExecTime=end.getTime();
			//return endDateStr;

		} catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wEndExecution Method");
		}
		return endDateStr;

	}

	// master method to get time

	public static String mEndExecution() {

		try {

			wEndExecution();

		} catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mEndExecution method");
		}
		return wEndExecution();
	}

	private String getTotalTime() {

		long totalTime;

		try {

			totalTime = startExecTime - endExecTime;
			long min = totalTime / 60;
			long sec = totalTime % 60;

		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in getTotalTime method");
		}
		return "";
	}

	// Sunil D Sonmale
	// 18-12-2015
	// Method to kill chromedriver.exe process after each execution. the method
	// is added in closebrowser method

	private void wKillChromeDriver() {
		Runtime runtime = Runtime.getRuntime();
		try {

			String current = new java.io.File(".").getCanonicalPath();
			/*
			 * current = current.replaceAll(current, "\\");
			 * //System.out.println("Current dir:"+current);
			 */
			Process p1 = runtime.exec("cmd /c start "
					+ mGetPropertyFromFile("killProcessBatFilePath"));
			// Process p1 =
			// runtime.exec("cmd /c start "+current+""+mGetPropertyFromFile("killProcessBatFilePath"));
			//System.out.println(current + ""+ mGetPropertyFromFile("killProcessBatFilePath"));
			InputStream is = p1.getInputStream();
			int i = 0;
			while ((i = is.read()) != -1) {
				System.out.print("this is what i wanted to search" + (char) i);
			}
		} catch (Exception e) {
			//System.out.println(e.getMessage());
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wKillChromeDriver method");

		}
	}

	// Sunil D Sonmale
	// 18-12-2015
	// Method to kill chromedriver.exe process after each execution. the method
	// is added in closebrowser method
	public void mKillChromeDriver() {

		try {
			preAction("Killing ChromeDriver.exe");
			wKillChromeDriver();
			postAction("ChromeDriver.exe Killed");

		} catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mKillChromeDriver method");
		}
	}

	// Sunil D Sonmale
	// 16-01-2015
	// Method to kill ipmsg.exe process before start of each execution. the
	// method is added in closebrowser method

	private void wKillIPMSG() {
		Runtime runtime = Runtime.getRuntime();
		try {

			String current = new java.io.File(".").getCanonicalPath();
			/*
			 * current = current.replaceAll(current, "\\");
			 * //System.out.println("Current dir:"+current);
			 */
			Process p1 = runtime.exec("cmd /c start "
					+ mGetPropertyFromFile("IPMSGprocessKillbatFilePath"));
			// Process p1 =
			// runtime.exec("cmd /c start "+current+""+mGetPropertyFromFile("killProcessBatFilePath"));
			//System.out.println(current + ""+ mGetPropertyFromFile("IPMSGprocessKillbatFilePath"));
			InputStream is = p1.getInputStream();
			int i = 0;
			while ((i = is.read()) != -1) {
				System.out.print("this is what i wanted to search" + (char) i);
			}
		} catch (Exception e) {
			//System.out.println(e.getMessage());
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wKillIPMSG method");
		}
	}

	// Sunil D Sonmale
	// 16-01-2015
	// Method to kill ipmsg.exe process before start of each execution. the
	// method is added in closebrowser method
	public void mKillIPMSG() {

		try {
			preAction("Killing IPMSG.exe");
			wKillIPMSG();
			postAction("IPMSG.exe Killed");

		} catch (Exception e) {
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mKillIPMSG method");
		}
	}

	private static void wDeleteTempPropertyFiles() {

		try {

			File fin = new File(
					"D:/AutomationFramework/ABMSmartScript/functional/properties/");



			for (File file : fin.listFiles()) {

				FileDeleteStrategy.FORCE.delete(file);

			}

		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wDeleteTempPropertyFiles method");
		}

	}

	public static void mDeleteTempPropertyFiles() {

		try {

			//System.out.println("Deleting temporary property files...");
			wDeleteTempPropertyFiles();

		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mDeleteTempPropertyFiles method");

		}

	}

	private String wGetTestStartTime() {

		String startTimeStr = "";
		try {

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

			startTimeStr = dateFormat.format(cal.getTime());

			log.info("Test case start Time is " + startTimeStr);

		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wGetTestStartTime method");
		}
		return startTimeStr;

	}

	public String mGetTestStartTime() {

		try {

			wGetTestStartTime();

		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mGetTestStartTime method");
		}

		return wGetTestStartTime();

	}

	private String wGetTestEndTime() {

		String endTimeStr = "";
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

			endTimeStr = dateFormat.format(cal.getTime());

			log.info("Test case end Time is " + endTimeStr);

		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wGetTestEndTime method");
		}
		return endTimeStr;
	}

	public String mGetTestEndTime() {
		try {

			wGetTestEndTime();

		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mGetTestEndTime method");
		}

		return wGetTestEndTime();
	}

	/**
	 * @author tejas.kotekar
	 * @since 8-03-2015 Generates a random number of specified length, we can
	 *        append a prefix of our choice to make it alphanumeric.
	 */

	private String wGenerateAplhanumericString(String prefix,int length) {




		String out = "";
		String alphanumeric = "";
		try {
			/*
			 * char[] values = {'a','b','c','d','e','f','g','h','i','j',
			 * 'k','l','m','n','o','p','q','r','s','t',
			 * 'u','v','w','x','y','z','0','1','2','3',
			 * '4','5','6','7','8','9'};
			 */
			char[] values = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };



			for (int i=0;i<length;i++) {
				int idx=random.nextInt(values.length);
				out += values[idx];
			}
			alphanumeric=prefix+out;
			//return alphanumeric;


		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wGenerateAplhanumericString method");
		}
		return alphanumeric;
	}

	// Master method to generate aplhanumeric string
	public String mGenerateAplhanumericString(String prefix, int length) {
		String aplhanumeric = "";
		try {
			preAction("Generating alphanumeric string");
			aplhanumeric = wGenerateAplhanumericString(prefix, length);
			postAction("Generated aplhanumeric stirng :" + aplhanumeric);

		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mGenerateAplhanumericString method");

		}
		return aplhanumeric;

	}

	/*private void wDynamicRowObjects(String tableId, String btnId, String name,String address) {

		String pltfnm = "";
		String pltfaddr = "";
		String plaintiffname = "";
		String plaintiffaddr = "";
		String plaintiffnameval = "";
		String plaintiffaddrval = "";

		try {
			WebElement table = driver.findElement(By.id(tableId));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount = rows.size();
			//System.out.println("NUMBER OF ROWS IN THIS TABLE = " + rwcount);

			// Iterate to get the value of each cell in table along with element
			// id
			int Rowno = 0;
			for (WebElement rowElement : rows) {
				List<WebElement> cols = rowElement.findElements(By.xpath("td"));
				int Columnno = 0;
				for (WebElement colElement : cols) {
					plaintiffname = cols.get(0)
							.findElement(By.tagName("input"))
							.getAttribute("id");
					pltfnm = plaintiffname.substring(0, 8);
					plaintiffnameval = cols.get(0)
							.findElement(By.tagName("input"))
							.getAttribute("Value");
					plaintiffaddr = cols.get(1)
							.findElement(By.tagName("textarea"))
							.getAttribute("id");
					pltfaddr = plaintiffaddr.substring(0, 8);
					plaintiffaddrval = cols.get(1)
							.findElement(By.tagName("textarea"))
							.getAttribute("value");
					if (Columnno == 0) {
						//System.out.println("Row " + Rowno + " Column "
								+ Columnno + " Value " + plaintiffnameval
								+ " Id is " + plaintiffname);
					} else {
						//System.out.println("Row " + Rowno + " Column "
								+ Columnno + " Value " + plaintiffaddrval
								+ " Id is " + plaintiffaddr);

		}catch(Exception e){

			e.printStackTrace();
		}

	}
	 */
	//Master method to generate aplhanumeric string
	/*	public String mGenerateAplhanumericString(String prefix,int length) {
		String aplhanumeric = "";
		try{
			preAction("Generating alphanumeric string");
			aplhanumeric=wGenerateAplhanumericString(prefix,length);
			postAction("Generated aplhanumeric stirng :"+aplhanumeric);

		}
		catch(Exception e){

			e.printStackTrace();

		}
		return aplhanumeric;

	}*/

	private void wDynamicRowObjects(String tableId,String btnId,String name, String address){

		String pltfnm = "";
		String pltfaddr="";
		String plaintiffname = "";  
		String plaintiffaddr = ""; 
		String plaintiffnameval = "";
		String plaintiffaddrval= "";

		try{
			WebElement table = driver.findElement(By.id(tableId));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount =rows.size();
			//System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

			// Iterate to get the value of each cell in table along with element id
			int Rowno=0;
			for(WebElement rowElement:rows)
			{
				List<WebElement> cols=rowElement.findElements(By.xpath("td"));
				int Columnno=0;
				for(WebElement colElement:cols)
				{
					plaintiffname=cols.get(0).findElement(By.tagName("input")).getAttribute("id");
					pltfnm =plaintiffname.substring(0, 8);
					plaintiffnameval=cols.get(0).findElement(By.tagName("input")).getAttribute("Value");
					plaintiffaddr=cols.get(1).findElement(By.tagName("textarea")).getAttribute("id");
					pltfaddr =plaintiffaddr.substring(0, 8);
					plaintiffaddrval=cols.get(1).findElement(By.tagName("textarea")).getAttribute("value");
					if (Columnno==0)
					{
						//System.out.println("Row "+Rowno+" Column "+Columnno+" Value "+plaintiffnameval+ " Id is " +plaintiffname);

					}

					else
					{
						//System.out.println("Row "+Rowno+" Column "+Columnno+" Value "+plaintiffaddrval+ " Id is " +plaintiffaddr);
					}
					Columnno=Columnno+1;

				}

				Rowno=Rowno+1;

			}


			// Check for 1st row and then enter values
			if ((rwcount<=2) && (plaintiffnameval.isEmpty()))
			{
				driver.findElement(By.id(plaintiffname)).clear();
				driver.findElement(By.id(plaintiffname)).sendKeys(name);
				driver.findElement(By.id(plaintiffaddr)).clear();
				driver.findElement(By.id(plaintiffaddr)).sendKeys(address);

			}


			// If it is not 1st row then click on add and then enter values
			else
			{
				Rowno=Rowno-1;
				String plaintiffnameid=pltfnm+Rowno;
				String plaintiffaddrid=pltfaddr+Rowno;
				driver.findElement(By.id(btnId)).click();
				driver.findElement(By.id(plaintiffnameid)).sendKeys(name);
				driver.findElement(By.id(plaintiffaddrid)).sendKeys(address);
			}

		}

		catch(Exception e){
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wDynamicRowObjects method");
		}

	}


	public void mDynamicRowObjects(String tableId, String btnId, String name,
			String address) {

		try {

			preAction("Performing dynamic row operaion");
			wDynamicRowObjects(tableId, btnId, name, address);
			postAction("Performed dynamic row operaion");

		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mDynamicRowObjects method");
		}

	}


	private String wGetSequenceNo(String seqName) {

		String prefixStr = "";
		String seqString = "";
		String suffixStr = "";
		try {

			String filepath = mGetPropertyFromFile("sequenceGenXML");
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			seqDoc = docBuilder.parse(filepath);


			Node nodeAttr;

			NodeList nodeList = seqDoc.getElementsByTagName("sequence-name");

			for (nodeCounter = 0; nodeCounter < nodeList.getLength(); nodeCounter++) {

				sequenceName = seqDoc.getElementsByTagName("sequence-name").item(nodeCounter);
				attributes = sequenceName.getAttributes();

				nodeAttr = attributes.getNamedItem("name");
				attrName = nodeAttr.getTextContent();
				if (seqName.equals(attrName)) {
					break;
				}
			}


			Node number = seqDoc.getElementsByTagName("current-num").item(nodeCounter);
			Node prefix = seqDoc.getElementsByTagName("prefix").item(nodeCounter);
			Node suffix = seqDoc.getElementsByTagName("suffix").item(nodeCounter);


			seqNo = Integer.parseInt(number.getTextContent());
			prefixStr = prefix.getTextContent();
			suffixStr = suffix.getTextContent();
			seqString = prefixStr + seqNo + suffixStr;


			//System.out.println(number.getTextContent());
			//System.out.println(seqString);

		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wGetSequenceNo method");
		}

		return seqString;

	}

	public String mGetSequenceNo(String seqName) {

		try {

			preAction("Getting sequence string");
			seqString = wGetSequenceNo(seqName);
			postAction("Got sequence string : " + seqString);
			// mUpdateSequenceNo();

		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mGetSequenceNo method");

		}
		return seqString;
	}

	private void wUpdateSequenceNo() {

		int nextNo;
		nextNo = seqNo + 1;
		String prefixStr;
		//System.out.println(nextNo);
		try {

			String filepath = mGetPropertyFromFile("sequenceGenXML");

			Node number = seqDoc.getElementsByTagName("current-num").item(nodeCounter);
			Node prefix = seqDoc.getElementsByTagName("prefix").item(nodeCounter);

			String nextNum = String.valueOf(nextNo);

			prefixStr = prefix.getTextContent();

			number.setTextContent(nextNum);

			//System.out.println(prefixStr + nextNum);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(seqDoc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);

		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wUpdateSequenceNo method");
		}

	}

	public void mUpdateSequenceNo() {

		try {
			preAction("Updating sequence number");
			wUpdateSequenceNo();
			postAction("Sequence number updated");

		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mUpdateSequenceNo method");
		}

	}


	public void mNavigation(String moduleName, String serviceName) {
		try{
			preAction("Performing navigation from " + moduleName + " to "
					+ serviceName + "...");
			wNavigation(moduleName, serviceName);
			postAction("Performed navigation operation...");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mNavigation method of 2 paramters");
		}
	}

	///piyush method////////////////

	private void wNavigation(String moduleName, String serviceName) {

		try{
			formName=mGetPropertyFromFile(serviceName);
			//System.out.println("formname is : "+formName);

			if(mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("Agency"))
			{

				mNavigation("onlineServicesLinkid", moduleName, serviceName);

				//mNavigation("Online Services", moduleName, serviceName);

			}
			else if(mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("Department"))
			{
				mNavigation("onlineServicesLinkid", moduleName+"xpath", serviceName);
			}
			else
			{

				mWaitForVisible("linkText", mGetPropertyFromFile(moduleName));
				mCustomWait(2000);
				mClick("linkText", mGetPropertyFromFile(moduleName));

				mWaitForVisible("linkText", mGetPropertyFromFile(serviceName));
				mCustomWait(2000);
				mClick("linkText", mGetPropertyFromFile(serviceName));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wNavigation method of 2 parameters");
		}
	}


	////////////////////////////////////////
	/*	private void wNavigation(String moduleName, String serviceName) {

		try{
			formName=mGetPropertyFromFile(serviceName);
			//System.out.println("formname is : "+formName);

			if(mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("Agency"))
			{
				mNavigation("Online Services", moduleName, serviceName);
			}
			else if(mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("Department"))
			{
				mNavigation("onlineservicenavi", moduleName+"xpath", serviceName);
			}
			else
			{
				mWaitForVisible("linkText", mGetPropertyFromFile(moduleName));
				mCustomWait(2000);
				mClick("linkText", mGetPropertyFromFile(moduleName));

				mWaitForVisible("linkText", mGetPropertyFromFile(serviceName));
				mCustomWait(2000);
				mClick("linkText", mGetPropertyFromFile(serviceName));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wNavigation method of 2 parameters");
		}
	}*/




	public void mNavigation(String onlServices, String moduleName,
			String serviceName) {

		try
		{
			preAction("Performing navigation from " + onlServices + " to "
					+ moduleName + " to " + serviceName + "...");
			wNavigation(onlServices, moduleName, serviceName);
			postAction("Performed navigation operation...");
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mNavigation method of 3 parameters");
		}
	}

	private void wNavigation(String onlServices, String moduleName,String serviceName) 
	{
		try{

			mWaitForVisible("linkText",mGetPropertyFromFile(onlServices));
			mCustomWait(5000);

			mClick("linkText",mGetPropertyFromFile(onlServices));
			if((mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("Department"))&&(onlServices.equalsIgnoreCase("Online Services")))

				mClick("linkText", mGetPropertyFromFile(onlServices));
			if((mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("Department"))&&(mGetPropertyFromFile(onlServices).equalsIgnoreCase("Online Services"))||(mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("Department"))&&(mGetPropertyFromFile(onlServices).equalsIgnoreCase("Online Department Services")))

			{
				mWaitForVisible("xpath", mGetPropertyFromFile(moduleName));
				mCustomWait(2000);
				mClick("xpath", mGetPropertyFromFile(moduleName));
				mWaitForVisible("linkText", mGetPropertyFromFile(serviceName));
			}
			else
			{
				mWaitForVisible("linkText", mGetPropertyFromFile(moduleName));
				mCustomWait(2000);
				mClick("linkText", mGetPropertyFromFile(moduleName));
				mWaitForVisible("linkText", mGetPropertyFromFile(serviceName));
			}
			mCustomWait(2000);
			mClick("linkText", mGetPropertyFromFile(serviceName));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wNavigation method 3 parameters");
		}
	}

	public void mNavigation(String onlServices, String moduleName,
			String subModuleName, String serviceName) {

		try{
			preAction("Performing navigation from " + onlServices + " to "
					+ moduleName + " to " + subModuleName + " to " + serviceName
					+ "...");
			wNavigation(onlServices, moduleName, subModuleName, serviceName);
			postAction("Performed navigation operation...");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mNavigation method of 4 parameters");
		}
	}

	private void wNavigation(String onlServices, String moduleName,
			String subModuleName, String serviceName) {

		try{

			mWaitForVisible("linkText", onlServices);
			mCustomWait(2000);
			mClick("linkText", onlServices);
			mWaitForVisible("linkText", moduleName);
			mCustomWait(2000);
			mClick("linkText", moduleName);
			mWaitForVisible("linkText", subModuleName);
			mCustomWait(2000);
			mClick("linkText", subModuleName);
			mWaitForVisible("linkText", serviceName);
			mCustomWait(2000);
			mClick("linkText", serviceName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wNavigation method of 4 parameters");
		}
	}

	///piyush method
	/*public void mNavigation(String moduleName, String serviceName) {
		try{
			preAction("Performing navigation from " + moduleName + " to "
					+ serviceName + "...");
			wNavigation(moduleName, serviceName);
			postAction("Performed navigation operation...");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mNavigation method of 2 paramters");
		}
	}


	private void wNavigation(String moduleName, String serviceName) {

		try{
			formName=mGetPropertyFromFile(serviceName);
			//System.out.println("formname is : "+formName);

			if(mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("Agency"))
			{

				mNavigation("onlineServicesLinkid", moduleName, serviceName);

				//mNavigation("Online Services", moduleName, serviceName);

			}
			else if(mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("Department"))
			{
				mNavigation("onlineServicesLinkid", moduleName+"xpath", serviceName);
			}
			else
			{

				mWaitForVisible("linkText", mGetPropertyFromFile(moduleName));
				mCustomWait(2000);
				mClick("linkText", mGetPropertyFromFile(moduleName));

				mWaitForVisible("linkText", mGetPropertyFromFile(serviceName));
				mCustomWait(2000);
				mClick("linkText", mGetPropertyFromFile(serviceName));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wNavigation method of 2 parameters");
		}
	}




	public void mNavigation(String onlServices, String moduleName,
			String serviceName) {

		try
		{
			preAction("Performing navigation from " + onlServices + " to "
					+ moduleName + " to " + serviceName + "...");
			wNavigation(onlServices, moduleName, serviceName);
			postAction("Performed navigation operation...");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mNavigation method of 3 parameters");
		}
	}

	private void wNavigation(String onlServices, String moduleName,String serviceName) 
	{
		try{

			mWaitForVisible("linkText", mGetPropertyFromFile(onlServices));
			mCustomWait(5000);
			mClick("linkText", mGetPropertyFromFile(onlServices));
			if((mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("Department"))&&(mGetPropertyFromFile(onlServices).equalsIgnoreCase("Online Services"))||(mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("Department"))&&(mGetPropertyFromFile(onlServices).equalsIgnoreCase("Online Department Services")))
			{
				mWaitForVisible("xpath", mGetPropertyFromFile(moduleName));
				mCustomWait(2000);
				mClick("xpath", mGetPropertyFromFile(moduleName));
				mWaitForVisible("linkText", mGetPropertyFromFile(serviceName));
			}
			else
			{
				mWaitForVisible("linkText", mGetPropertyFromFile(moduleName));
				mCustomWait(2000);
				mClick("linkText", mGetPropertyFromFile(moduleName));
				mWaitForVisible("linkText", mGetPropertyFromFile(serviceName));
			}
			mCustomWait(2000);
			mClick("linkText", mGetPropertyFromFile(serviceName));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wNavigation method 3 parameters");
		}
	}

	public void mNavigation(String onlServices, String moduleName,
			String subModuleName, String serviceName) {

		try{
			preAction("Performing navigation from " + onlServices + " to "
					+ moduleName + " to " + subModuleName + " to " + serviceName
					+ "...");
			wNavigation(onlServices, moduleName, subModuleName, serviceName);
			postAction("Performed navigation operation...");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mNavigation method of 4 parameters");
		}
	}

	private void wNavigation(String onlServices, String moduleName,
			String subModuleName, String serviceName) {

		try{

			mWaitForVisible("linkText", onlServices);
			mCustomWait(2000);
			mClick("linkText", onlServices);
			mWaitForVisible("linkText", moduleName);
			mCustomWait(2000);
			mClick("linkText", moduleName);
			mWaitForVisible("linkText", subModuleName);
			mCustomWait(2000);
			mClick("linkText", subModuleName);
			mWaitForVisible("linkText", serviceName);
			mCustomWait(2000);
			mClick("linkText", serviceName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wNavigation method of 4 parameters");
		}
	}*/
	public String mStringManipulation(String id, String string)
	{
		String mapAppNo = "";
		try
		{
			preAction("Manipulating String...");
			mapAppNo = wStringManipulation(id, string);
			postAction("String manipulated...");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mStringManipulation method");
		}
		return mapAppNo;
	}

	public String wStringManipulation(String id, String string)
	{
		String mapAppNo = "";
		try
		{
			String numberOnly= string.replaceAll("[^0-9]", "");
			//System.out.println(numberOnly);

			int intContractNum = 0;
			char[] charArray;
			charArray = numberOnly.toCharArray();

			for(int i=0;i<=charArray.length-1;i++)
			{
				int nonZeroIndex =0;
				nonZeroIndex = Character.getNumericValue(charArray[i]);
				if (nonZeroIndex>0)
				{
					intContractNum = i;
					break;
				}
			}
			for(int i=intContractNum;i<=charArray.length-1;i++)
			{
				mapAppNo = mapAppNo+charArray[i];
				//System.out.println();
				//System.out.println(charArray[i]);
			}

			//System.out.println("Manipulated Application Number :"+mapAppNo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wStringManipulation method");
		}
		return id+mapAppNo;
	}



	public void numberOfColumns(String filename)
	{
		try
		{
			CommonUtilsAPIXML();

			current = new java.io.File( "." ).getCanonicalPath();
			current = current.replaceAll(current, "\\");
			//current = current.toString();
			//System.out.println("Current dir:"+current);
			current = current + filename;

			//System.out.println("FILE For common data is "+current);


			FileInputStream fis=new FileInputStream(current);
			//FileInputStream fis=new FileInputStream("D:\\AutomationFramework\\ABMSmartScript\\framework\\excelreader\\CommonID .xlsx");
			workbook =(XSSFWorkbook) WorkbookFactory.create(fis);
			sheetName = workbook.getSheetName(0);
			sheet = (XSSFSheet) workbook.getSheet(sheetName);
			NumberOfRows = sheet.getLastRowNum();
			//System.out.println("Number of rows are :"+NumberOfRows);

			row = sheet.getRow(0);
			NumberOfColumns = row.getLastCellNum();	
			//System.out.println("Number of columns are mutidie:"+NumberOfColumns);
			NumberOfColumns = NumberOfColumns-1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in numberOfColumns method");
		}		
	}

	public void CommonUtilsAPIXML()
	{
		try{
			//System.out.println("In common utils");

			mReadFromXML("D:\\AutomationFramework\\ABMSmartScript\\functional\\config\\config.xml");

		}
		catch (Exception e)
		{
			throw new MainetCustomExceptions("Error in CommonUtilsAPIXML method");
		}
	}


	//Method which uses a parametter from excel sheet to decide if a scenario is to be run independently or is dependent on the previous scenario
	//Sunil D Sonmale
	public void IndOrDep (String locatortype, String locator, String data)
	{
		try{

			String tagname = mGetText(locatortype, mGetPropertyFromFile(locator), "tagName");
			//System.out.println("This is the tagname"+tagname);

			log.info("Getting Type of Execution :::: ");


			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				mGenericWait();

				if(tagname.equalsIgnoreCase("input"))
				{
					mSendKeys(locatortype, mGetPropertyFromFile(locator), applicationNo.get(CurrentinvoCount));
				}
				else
				{
					mSelectDropDown(locatortype, mGetPropertyFromFile(locator), applicationNo.get(CurrentinvoCount));	
				}
			}

			else if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual"))
			{		
				mGenericWait();

				if(tagname.equalsIgnoreCase("input"))
				{
					mSendKeys(locatortype, mGetPropertyFromFile(locator), mGetPropertyFromFile(data));
				}
				else
				{
					mSelectDropDown(locatortype, mGetPropertyFromFile(locator), mGetPropertyFromFile(data));	
				}
			}

			log.info("Type of Execution is :::: "+mGetPropertyFromFile("TypeOfExecution"));

		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in IndOrDep method");
		}
	}

	//Method to collect all the application numbers into an array
	//Sunil D Sonmale
	public void mAppNoArray (String locatortype, String locator)
	{
		try{
			log.info("Capturing appno into the array");

			appNo=mGetApplicationNo(locatortype, locator);
			applicationNo.add(appNo);

			log.info("Captured appno into the array"+applicationNo);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mAppNoArray method");

		}
	}




	// Method for documents upload
	//Madhuri Dawande


	/*docuploadboolean=true;

			//String formName=mGetText("css", "#heading_bredcrum > ul > li.active");
			String docsReqd;
			String docStatus = "";
			String upldDoc;
			List<WebElement> rows;
			//WebElement table = driver.findElement(By.className("gridtable"));
			WebElement table = driver.findElement(By.xpath(tableName));

			//List<WebElement> rows = table.findElements(By.tagName("tr"));
			//List<WebElement> rows = table.findElements(By.xpath("//table/tbody/tr[(contains(@style,'display: table-row;'))]"));



			if((myClassName.equals("com.abm.mainet.bnd.BirthAndDeathServices")))
			{						
				if((formName.equalsIgnoreCase("Death Registration Correction and Issue Certificate"))||(formName.equalsIgnoreCase("Birth Registration Correction and Issue Certificate")||(bnd_appStatusRejectedDueToInadequateInformationAndDocuments)))
				{
					rows = table.findElements(By.tagName("tr"));
					//System.out.println("formname is "+formName);
				}
				else
				{
					rows = table.findElements(By.xpath("//table/tbody/tr[(contains(@style,'display: table-row;'))]"));
				}

			}
			else
			{
				rows = table.findElements(By.tagName("tr"));
				//System.out.println("doc upload else");
			}
			int rwcount =rows.size();
			//System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
			int count=0;
			for(WebElement row: rows){
				if(row.isDisplayed())
					count++;
			}
			//System.out.println("The number of rows that are visible is: "+ count);



			int Rowno = 0;
			//for(int i=1;i<=count;i++)
			for (WebElement rowElement : rows)
			{
				List<WebElement> cols = rowElement.findElements(By.xpath("td"));	 
				int colcount =cols.size();
				//System.out.println("NUMBER OF COLUMNS IN THIS TABLE = "+colcount);
				//for(int j=1;j<=colcount;j++)
				int Columnno = 0;
				for (WebElement colElement : cols)
				{
					if(Columnno==0)
					{
						docsReqd=rowElement.findElement(By.tagName("td")).getText();
						//System.out.println("Required document is :"+docsReqd);

					}
					if(Columnno==1)
					{
						docStatus=rowElement.findElement(By.xpath("./td[2]")).getText();
						//System.out.println("Status of Required document is :"+docStatus);

					}
					if(Columnno==2)
					{
						if(mGetPropertyFromFile(upldDocStatus).equalsIgnoreCase(docStatus))
						{
							upldDoc=rowElement.findElement(By.xpath("./td/div/span/input")).getAttribute("id");
							//System.out.println("Id for upload doc is :"+upldDoc);
							mUpload("id", upldDoc, mGetPropertyFromFile("uploadPath2"));
							mCustomWait(2000);	
						}
						else if(mGetPropertyFromFile(upldDocStatus).equalsIgnoreCase("All"))
						{
							upldDoc=rowElement.findElement(By.xpath("./td/div/span/input")).getAttribute("id");
							//System.out.println("Id for upload doc is :"+upldDoc);
							mUpload("id", upldDoc, mGetPropertyFromFile("uploadPath2"));
							mCustomWait(2000);	
						}
						else
						{
							//System.out.println("Document is optional");
						}
					}
					Columnno = Columnno + 1;
					//System.out.println("Column no. is :"+Columnno);
				}
				Rowno = Rowno + 1;
				//System.out.println("Row no. is :"+Rowno);
			}

			docuploadboolean=false;

		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in docUpload method");
		}
	}*/




	public void docUpload(String locatorType,String tableName,String upldDocStatus)
	{
		try
		{
			formName =formName.trim();
			//System.out.println(formName);
			//System.out.println(locatorType +" "+tableName+" " +upldDocStatus);
			//String formName=mGetText("css", "#heading_bredcrum > ul > li.active");
			String docsReqd;
			String docStatus = "";
			String upldDoc;
			List<WebElement> rows;
			//WebElement table = driver.findElement(By.className("gridtable"));
			WebElement table = /*driver.findElement(By.xpath(tableName));*/	driver.findElement(locatorSelector(locatorType, tableName));

			//System.out.println("My clasname is :::"+myClassName);

			if((myClassName.equals("com.abm.mainet.bnd.BirthAndDeathServices")))
			{						
				if((formName.equalsIgnoreCase("Death Registration Correction and Issue Certificate"))||(formName.equalsIgnoreCase("Birth Registration Correction and Issue Certificate")))
				{

					System.err.println("am i in this if condition");

					rows = table.findElements(By.tagName("tr"));
					//System.out.println("formname is "+formName);
				}
				else
				{
					rows = table.findElements(By.xpath("//table/tbody/tr[(contains(@style,'display: table-row;'))]"));
					System.err.println("am i in this else condition");
				}

			}
			else
			{
				rows = table.findElements(By.tagName("tr"));
				//System.out.println("doc upload else");
			}
			int rwcount =rows.size();
			//System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
			int count=0;
			for(WebElement row: rows){
				if(row.isDisplayed())
					count++;
			}
			//System.out.println("The number of rows that are visible is: "+ count);



			int Rowno = 0;
			//for(int i=1;i<=count;i++)
			for (WebElement rowElement : rows)
			{
				List<WebElement> cols = rowElement.findElements(By.xpath("td"));	 
				int colcount =cols.size();
				//System.out.println("NUMBER OF COLUMNS IN THIS TABLE = "+colcount);
				//for(int j=1;j<=colcount;j++)
				int Columnno = 0;
				for (WebElement colElement : cols)
				{
					if(Columnno==0)
					{
						docsReqd=rowElement.findElement(By.tagName("td")).getText();
						//System.out.println("Required document is :"+docsReqd);

					}
					if(Columnno==1)
					{
						docStatus=rowElement.findElement(By.xpath("./td[2]")).getText();
						//System.out.println("Status of Required document is :"+docStatus);

					}
					if(Columnno==2)
					{
						if(mGetPropertyFromFile(upldDocStatus).equalsIgnoreCase(docStatus))
						{
							upldDoc=rowElement.findElement(By.xpath("./td/div/span/input")).getAttribute("id");
							//System.out.println("Id for upload doc is :"+upldDoc);
							mUpload("id", upldDoc, mGetPropertyFromFile("uploadPath"+"_"+Rowno));
							mCustomWait(2000);	
						}
						else if(mGetPropertyFromFile(upldDocStatus).equalsIgnoreCase("All"))
						{
							upldDoc=rowElement.findElement(By.xpath("./td/div/span/input")).getAttribute("id");
							//System.out.println("Id for upload doc is :"+upldDoc);
							mUpload("id", upldDoc, mGetPropertyFromFile("uploadPath"+"_"+Rowno));
							mCustomWait(2000);	
						}
						else
						{
							//System.out.println("Document is optional");
						}
					}
					Columnno = Columnno + 1;
					//System.out.println("Column no. is :"+Columnno);
				}
				Rowno = Rowno + 1;
				//System.out.println("Row no. is :"+Rowno);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in docUpload method");
		}
	}



	///Method for wait for page to get loaded completely
	//Sunil D Sonmale 	

	public void waitForPageLoaded() 
	{
		try{
			ExpectedCondition<Boolean> expectation = new
					ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
				}
			};
			try {
				Thread.sleep(1000);
				WebDriverWait wait = new WebDriverWait(driver, 30);
				wait.until(expectation);

				System.err.println("Waiting for page to load");
			} catch (Throwable error) {
				Assert.fail("Timeout waiting for Page Load Request to complete.");
				throw new MainetCustomExceptions("Error in internal try/catch block of waitForPageLoaded method");	
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in waitForPageLoaded method");
		}


	}

	// Wrapper for Selenium Capture Selected Value from drop down

	public String wCaptureSelectedValue(String locatortype, String locator){

		String CapturedValue="";
		try {
			CapturedValue=new Select(driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator))).getFirstSelectedOption().getText();

		}

		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return CapturedValue;
	}


	// Master for Selenium Capture Selected Value from drop down
	public String mCaptureSelectedValue(String locatorType, String locator) {

		String CapturedValue="";
		try {
			preAction("Selecting from dropdown");
			CapturedValue=wCaptureSelectedValue(locatorType, locator);
			postAction("Selected from dropdown");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return CapturedValue;
	}

	public void mAppNoArray (String data)
	{
		log.info("Capturing appno into the array");

		appNo=data;
		applicationNo.add(appNo);

		log.info("Captured appno into the array"+applicationNo);
	}






	//Wrapper for generic date picker
	private void wGdatePicker(String locatorType, String dtPickerId, String year,String month,String day){
		try
		{
			//to move cursor to another location

			// These coordinates are screen coordinates
			int xCoord = 1300;
			int yCoord = 220;

			// Move the cursor
			Robot robot = new Robot();
			robot.mouseMove(xCoord, yCoord);
			mGenericWait();

			String gDate;
			int days;


			//driver.findElement(By.id(dtPickerId)).click();
			driver.findElement(locatorSelector(locatorType,dtPickerId)).click();
			//driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), dtPickerId)).click();

			//System.out.println("selected date is  " + day + "/" + month + "/"					+ year);
			int currentyear = Calendar.getInstance().get(Calendar.YEAR);
			int numyear = Integer.parseInt(year);

			if ((numyear < (currentyear - 100) || numyear > (currentyear))) {
				//System.out.println("please enter valid year");
			}

			boolean isLeapYear = ((numyear % 400 == 0) || ((numyear % 4 == 0) && (numyear % 100 != 0)));
			int d = Integer.parseInt(day);

			// to calculate no of days in month
			if (month.equals("Apr") || month.equals("Jun")
					|| month.equals("Sep") || month.equals("Nov")) {
				days = 30;

				if (d > days || d < 1) {

					System.out
					.println("Date can not be greater than 30 or less than 1");
				}
			} else if (month.equals("Jan") || month.equals("Mar")
					|| month.equals("May") || month.equals("Jul")
					|| month.equals("Aug") || month.equals("Oct")
					|| month.equals("Dec")) {
				days = 31;

				if (d > days || d < 1) {
					System.out
					.println("Date can not be greater than 31 or less than 1");
				}
			} else if (month.equals("Feb")) {
				if (isLeapYear) {
					days = 29;

					if (d > days || d < 1) {
						System.out
						.println("Date can not be greater than 29 or less than 1");
					}
				} else {
					days = 28;

					if (d > days || d < 1) {
						System.out
						.println("Date can not be greater than 28 or less than 1");
					}
				}
			} else {
				//System.out.println("invalid month");
			}
			mGenericWait();
			// To select year

			driver.findElement(By.cssSelector("select.ui-datepicker-year"))
			.click();
			mGenericWait();
			Select dropdown = new Select(driver.findElement(By
					.cssSelector("select.ui-datepicker-year")));
			dropdown.selectByVisibleText(year);
			driver.findElement(By.cssSelector("select.ui-datepicker-year"))
			.sendKeys(Keys.ENTER);
			mGenericWait();

			// To select month
			driver.findElement(By.cssSelector("select.ui-datepicker-month"))
			.click();
			Select dropdown1 = new Select(driver.findElement(By
					.cssSelector("select.ui-datepicker-month")));
			dropdown1.selectByVisibleText(month);
			driver.findElement(By.cssSelector("select.ui-datepicker-month"))
			.sendKeys(Keys.ENTER);
			mGenericWait();

			mGenericWait();

			// To select date
			int intDate = Integer.parseInt(day);
			String strDate = Integer.toString(intDate);
			driver.findElement(By.linkText(strDate)).click();
			//driver.findElement(By.linkText(strDate)).sendKeys(Keys.ENTER);

			mGenericWait();

			//System.out.println(status);
			//				driver.findElement(By.id(dtPickerId)).sendKeys(Keys.TAB);
			driver.findElement(locatorSelector(locatorType, dtPickerId)).sendKeys(Keys.TAB);	
			//				gDate = driver.findElement(By.id(dtPickerId)).getAttribute("value");
			date = driver.findElement(locatorSelector(locatorType, dtPickerId)).getAttribute("value");
			//System.out.println(date);

		} catch (Exception e) {
			e.printStackTrace();
			status = false;
			//System.out.println("date is not in correct format");
		}
	}

	// Master for Generic date picker 5 parameter
	public void mGdatePicker(String locatorType,String dtPickerId, String year, String month, String day) {
		try {
			preAction("Selecting a date");
			wGdatePicker(locatorType, dtPickerId, year, month, day);
			postAction("Date selected");
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	//Robot Class method to hover on any element
	//Sunil D Sonmale 30-07-2016
	public void mouseOverOnElementUsingRobot(By by) {
		try {
			Point coordinates = driver.findElement(By.cssSelector(mGetPropertyFromFile("loginid"))).getLocation();
			Robot robot = new Robot();

			robot.mouseMove(coordinates.getX()+30, coordinates.getY() + 80);

			/*
			 * WebDriver provide document coordinates, where as Robot class is
			 * based on Screen coordinates, so I have added +60 to compensate
			 * the browser header. You can even adjust if needed.
			 */

		} catch (AWTException e) {
			log.error("Failed to mouseover on the element '" + by + "'. " + e);
		}
	}


	public void wdifferenceindays()
	{
		try{
			// To get system date
			int currentyear = Calendar.getInstance().get(Calendar.YEAR);
			int currentmonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
			int currentday = Calendar.getInstance().get(Calendar.DATE);

			Calendar calendar1 = Calendar.getInstance();
			Calendar calendar2 = Calendar.getInstance();

			calendar1.set(Integer.parseInt(date.substring(6, 10)),
					Integer.parseInt(date.substring(3, 5)),
					Integer.parseInt(date.substring(0, 2)));
			calendar2.set(currentyear, currentmonth, currentday);

			long miliSecondForDate1 = calendar1.getTimeInMillis();
			long miliSecondForDate2 = calendar2.getTimeInMillis();

			// To calculate difference in days
			long diffInMilis = miliSecondForDate2 - miliSecondForDate1;
			long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);
			dateIncr = diffInDays + 2;
			// Actual difference in days
			//System.out.println("Difference in Days : " + diffInDays);
		}

		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in wdifferenceindays method");
		}
	}

	public void mdifferenceindays()
	{
		try{
			preAction("Calculating difference in days");
			wdifferenceindays();
			postAction("Calculated difference in days");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mdifferenceindays method");
		}
	}


	public void  mNegativeAssert(){
		try{
			preAction("Performing Negative Assert");
			wNegativeAssert();
			postAction("Performed Negative Assert");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mNegativeAssert method");
		}
	}

	public void  wNegativeAssert() {
		try{

			String val=driver.findElement(By.className(mGetPropertyFromFile("errordivID"))).getText();
			//System.out.println(val);

			mAssert(val,mGetPropertyFromFile("negativeassert") ,"Negative Assert Failed");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in wNegativeAssert method");
		}
	}

	public void clearAppNoArray()
	{
		try
		{
			if(CurrentinvoCount==0)
			{
				log.info("Clearing applicationNo array"+applicationNo);
				applicationNo.clear();
				log.info("Cleared applicationNo array"+applicationNo);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in clearAppNoArray method");
		}
	}

	//Piyush 25-Nov-2016 

	public void mDownloadFileImg(String className) {

		try {
			ArrayList<String> tabs3 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs3.get(2));
			mCustomWait(2000);
			mDownloadFile(className);
			mCustomWait(2000);
			driver.switchTo().window(tabs3.get(2)).close();
			mCustomWait(2000);
			driver.switchTo().window(tabs3.get(1));
			mCustomWait(2000);
		} catch (Exception e) {

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in mDownloadFileImg method");
		}

	}

	public void errorTakeScreenShot() throws IOException {

		try{

			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			// String currentDir = System.getProperty("Path");
			// Part of Method for creating a folder and path
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH.mm.ss");
			String dateStr = dateFormat.format(cal.getTime());
			String errorscren="Error"+"time"+" "+dateStr;
			//System.out.println("After File Creation");

			// finalScrShotPath=moduleClipboardata+"\\"+getClassNames().get(methodCounter)+
			// "\\"+ "Screenshots"+ "\\";
			finalScrShotPath = moduleClipboardata + "\\" + thisMethodName + "\\"+ "Screenshots" + "\\";
			// //System.out.println("METHOD COUNT::"+methodCounter);

			//System.out.println("FINAL SCREENSHOT PATH:::" + finalScrShotPath);
			FileUtils.copyFile(scrFile, new File(finalScrShotPath +errorscren+ ".png"));
			mCustomWait(1000);
			throw new Exception(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wTakeScreenShot method");
		}

	}

	public void mSelectAutoCompleteText(String locatorType, String locator,
            String data) {

     try {

            preAction("Selecting from autocomplete textbox");
            wSelectAutoCompleteText(locatorType, locator, data);
            postAction("Element selected");

     }

     catch (Exception e) {
            e.printStackTrace();
            throw new MainetCustomExceptions("Error in mSelectAutoCompleteText method");
     }

}




//Wrapper for selecting autocomplete text
private void wSelectAutoCompleteText(String locatorType, String locator,
            String data) {


     try {

            driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator)).clear();
            mGenericWait();
            element = driver.findElement(locatorSelector(hmapid.get(actualKey).get(0).toString().trim(), locator));
            element.sendKeys(data);
            mCustomWait(1000);
            element.sendKeys(Keys.DOWN);
            mCustomWait(1000);
            element.sendKeys(Keys.ENTER);
            mCustomWait(2000);
            

     }

     catch (Exception e) {
            e.printStackTrace();
            exception = e.toString();                
            stackTrace = Throwables.getStackTraceAsString(e);
            throw new MainetCustomExceptions("Error in wSelectAutoCompleteText method");
     }

}



//Method for final logout
public void finalLogOut()
{
     try
     {
            // Click Ok
            mGenericWait();
            mCustomWait(500);
            mWaitForVisible("id", mGetPropertyFromFile("logoutOkid"));
            
            mGenericWait();
            mCustomWait(500);
            mClick("id", mGetPropertyFromFile("logoutOkid"));
            mGenericWait();
            mWaitForVisible("css", mGetPropertyFromFile("loginid"));

            

     }
     catch(Exception e)
     {
            e.printStackTrace();
            throw new MainetCustomExceptions("Error in finallogOut method");
     }
}




//Method for logout
public void logOut()
{
     try
     {
            
   mCustomWait(500);
            mWaitForVisible("linkText", mGetPropertyFromFile("logoutid"));
            mCustomWait(500);
            mGenericWait();
            mClick("linkText", mGetPropertyFromFile("logoutid"));

     }
     catch(Exception e)
     {
            e.printStackTrace();
            throw new MainetCustomExceptions("Error in logOut method");
     }
}

	
	
	
}