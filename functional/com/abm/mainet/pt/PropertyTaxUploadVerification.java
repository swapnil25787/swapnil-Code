package com.abm.mainet.pt;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.ibm.icu.text.DateFormat;

import api.CommonUtilsAPI;
import common.CommonMethods;
import excelreader.ExcelToProperties;


public class PropertyTaxUploadVerification extends CommonUtilsAPI{

	/*public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
	 */

	ResultSet rs1;

	List<String> newpropar = new ArrayList<String>();
	List<String> oldpropar = new ArrayList<String>();
	static Map<String, String> oldnewpropno = new HashMap<String, String>();
	Map.Entry me;
	//XSSFRow row;
	String newholdingno;
	DataFormatter df;

	//WebDriver driver = new HtmlUnitDriver();

	String oldholdingno, ward, rcircleno, ownertype, title, soname, fhname, gender, add1, add2, pincode, sadd,
	mobno, emailid, landtype, aptname, totalflatapp, acqdate, roadfactor, totalarea, builtuparea, waterconnfac, wtt, lastselfyearass;
	double oldpid;
	String newOldpid;
	String olldpid;
	String newwOldpid;
	String newwholdingno;
	String totalrecordsuploaded;

	//Strings for flat information sheet
	String floorno, usagetype, constructiontype, buituparea, usagefactor, occupanccyfactor, autoarv, manualarv;
	String ratarea;

	//Strings for joint owner sheet

	String jointownertitle, jointownername, jointfhname, jointgender;

	//Strings for Bill information sheet

	String taxname, outstandingamount, billdate;

	//Declaration for workbook

	String excelFilePath = "C:\\Users\\sunil.sonmale\\Desktop\\Property_Master_Template_Eng.xls";
	FileInputStream inputStream;
	HSSFRow row;


	public static void main(String[] args)
	{

	}

	static CommonMethods common =new CommonMethods();
	ExcelToProperties excelToProp = new ExcelToProperties();

	@BeforeSuite
	public void beforeSuite(){

		thisClassName=this.getClass().getCanonicalName();
		myClassName = thisClassName;

		excelToProp.convertExcelToProperties(mGetPropertyFromFile("CommonID"),mGetPropertyFromFile("CommonID"));
		excelToProp.convertExcelToProperties(mGetPropertyFromFile("CommonData"),mGetPropertyFromFile("CommonData_Muzaffarpur"),mGetPropertyFromFile("excelColumn"));
		mReadFromInputStream(mGetPropertyFromFile("CommonID_Properties_Path"), mGetPropertyFromFile("CommonData_Properties_Path"));
		mReadFromInputStream(mGetPropertyFromFile("CommonID_Properties_Path"), mGetPropertyFromFile("CommonData_Muzaffarpur_Properties_Path"));
		//mReadFromInputStream(mGetPropertyFromFile("CommonID_Properties_Path"), mGetPropertyFromFile("CommonData_Bhagalpur_Properties_Path"));
		System.out.println("beforeSuite Complete!!!");
	}

	public void beforePropTaxDataUpload (){

	}

	@Test
	public void PropTaxDataUpload ()
	{
		System.out.println("Before create folder");
		mCreateModuleFolder("PT_",myClassName);
		mCreateArtefactsFolder("PT_");
		mOpenBrowser(wGetPropertyFromFile("browserName"));
		mGetURL(wGetPropertyFromFile("url"));
		common.selectUlb();
		common.departmentLogin("deptUserName","deptPassword" );
		PropTaxUpload();
		common.logOut();
		common.finalLogOut();
		mCloseBrowser();
		readDataFromExcel();
		CommonUtilsAPI.propTaxErrorMsg.assertAll();
	}

	public void readDataFromExcel()
	{
		HSSFWorkbook workbook=null;
		try{
			System.out.println("In method");
			//String excelFilePath = "C:\\Users\\sunil.sonmale\\Desktop\\Property_Master_Template_Eng.xls";
			inputStream = new FileInputStream(excelFilePath);

			workbook = new HSSFWorkbook(inputStream);

			HSSFSheet  firstSheet = (HSSFSheet) workbook.getSheetAt(1);
			System.out.println(firstSheet);

			int count;
			int NumberOfColumns=0;



			for(count = 2;count<=firstSheet.getLastRowNum();count++){
				row = firstSheet.getRow(count);

				NumberOfColumns = row.getLastCellNum();
				System.out.println(NumberOfColumns);

				df  = new DataFormatter();

				for(int i=0; i<=NumberOfColumns-1;i++)
				{
					//System.out.println("Reading excel data of row:: "+i+ " and data is:: " + row.getCell(i).toString());
					if(row.getCell(0)==null)
					{	
						break;
					}
				}

				if(count==2)
				{
					outputfirstsheet();
					flatinformationsheet();
					billinformationsheet();
					PropUploadVerification();
				}
				else if(count>=3)
				{					
					outputfirstsheet();
					flatinformationsheet();
					billinformationsheet();
				}
			}

			count = count-2;

			System.out.println("No of columns is:: " +NumberOfColumns+ "\nNo of rows is:: "+count);
			System.out.println(newholdingno);

			workbook.close();
			inputStream.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void jointownersheet()
	{
		HSSFWorkbook workbook=null;
		try{
			System.out.println("In method joint owner sheet");
			//String excelFilePath = "C:\\Users\\sunil.sonmale\\Desktop\\Property_Master_Template_Eng.xls";
			FileInputStream inputStream = new FileInputStream(excelFilePath);

			workbook = new HSSFWorkbook(inputStream);

			HSSFSheet  secondsheet = (HSSFSheet) workbook.getSheetAt(2);
			System.out.println(secondsheet);

			int count;
			int NumberOfColumns=0;

			for(count = 2;count<=secondsheet.getLastRowNum();count++){

				//System.out.println(secondsheet.getLastRowNum());

				HSSFRow row = secondsheet.getRow(count);

				NumberOfColumns = row.getLastCellNum();

				//System.out.println(NumberOfColumns);

				df  = new DataFormatter();

				for(int i=0; i<=NumberOfColumns-1;i++)
				{
					//System.out.println(df.formatCellValue(row.getCell(0)));

					//System.out.println("Reading excel data of row of joint owner sheet:: "+i+ " and data is:: " + row.getCell(i).toString());
					if((df.formatCellValue(row.getCell(0))).equals(newOldpid))
					{	
						System.out.println(NumberOfColumns);
						System.out.println("current row no is: "+count);

						System.out.println("Joint owner for property no: " +newOldpid);

						jointownertitle = row.getCell(1).toString();
						System.out.println("Joint owner title" +jointownertitle);

						jointownername = row.getCell(2).toString();
						System.out.println("Joint owner name"+jointownername);

						jointfhname = row.getCell(3).toString();
						System.out.println("Joint owner father name "+jointfhname);

						jointgender = row.getCell(4).toString();
						System.out.println("joint owner gender"+jointgender);
						break;
					}				
					else
					{
						break;
						//System.out.println("No data found for the joint owner");
					}
				}
			}
		}
		catch (Exception e)
		{

		}
	}

	public void flatinformationsheet()
	{
		HSSFWorkbook workbook=null;
		try{
			System.out.println("In method flat information sheet");
			//String excelFilePath = "C:\\Users\\sunil.sonmale\\Desktop\\Property_Master_Template_Eng.xls";
			FileInputStream inputStream = new FileInputStream(excelFilePath);

			workbook = new HSSFWorkbook(inputStream);

			HSSFSheet  thirdsheet = (HSSFSheet) workbook.getSheetAt(3);
			System.out.println(thirdsheet);

			int count;
			int NumberOfColumns=0;

			for(count = 2;count<=thirdsheet.getLastRowNum();count++){

				//System.out.println(secondsheet.getLastRowNum());

				HSSFRow row = thirdsheet.getRow(count);

				NumberOfColumns = row.getLastCellNum();

				//System.out.println(NumberOfColumns);

				df  = new DataFormatter();

				for(int i=0; i<=NumberOfColumns-1;i++)
				{
					//System.out.println(df.formatCellValue(row.getCell(0)));

					//System.out.println("Reading excel data of row of joint owner sheet:: "+i+ " and data is:: " + row.getCell(i).toString());
					if((df.formatCellValue(row.getCell(0))).equals(newOldpid))
					{	
						System.out.println(NumberOfColumns);
						System.out.println("current row no is: "+count);

						System.out.println("Joint owner for property no: " +newOldpid);

						floorno = row.getCell(1).toString();
						System.out.println("Floor number is: " +floorno);

						usagetype = row.getCell(2).toString();
						System.out.println("Usage type is:: "+usagetype);

						constructiontype = row.getCell(3).toString();
						System.out.println("Construction type is:: "+constructiontype);

						buituparea = df.formatCellValue(row.getCell(4));
						System.out.println("Built up area is:: "+buituparea);

						ratarea = row.getCell(5).toString();
						System.out.println("Ratable area is: "+ratarea);

						usagefactor = row.getCell(6).toString();
						System.out.println("Usage factor is: "+usagefactor);

						occupanccyfactor = row.getCell(7).toString();
						System.out.println("Occupancy factor is: "+occupanccyfactor);

						autoarv = row.getCell(8).toString();
						System.out.println("Auto ARV flag is : "+autoarv);

						manualarv = row.getCell(9).toString();
						System.out.println("Manual ARV flag is: "+manualarv);

						break;
					}				
					else
					{
						break;
						//System.out.println("No data found for the joint owner");
					}
				}
			}
		}
		catch (Exception e)
		{

		}
	}


	public void billinformationsheet()
	{
		HSSFWorkbook workbook=null;
		try{
			System.out.println("In method bill information sheet");
			//String excelFilePath = "C:\\Users\\sunil.sonmale\\Desktop\\Property_Master_Template_Eng.xls";
			FileInputStream inputStream = new FileInputStream(excelFilePath);

			workbook = new HSSFWorkbook(inputStream);

			HSSFSheet  fourthsheet = (HSSFSheet) workbook.getSheetAt(4);
			System.out.println(fourthsheet);

			int count;
			int NumberOfColumns=0;

			for(count = 2;count<=fourthsheet.getLastRowNum();count++){

				//System.out.println(secondsheet.getLastRowNum());

				HSSFRow row = fourthsheet.getRow(count);

				NumberOfColumns = row.getLastCellNum();

				//System.out.println(NumberOfColumns);

				df  = new DataFormatter();

				for(int i=0; i<=NumberOfColumns-1;i++)
				{
					//System.out.println(df.formatCellValue(row.getCell(0)));

					//System.out.println("Reading excel data of row of joint owner sheet:: "+i+ " and data is:: " + row.getCell(i).toString());
					if((df.formatCellValue(row.getCell(0))).equals(newOldpid))
					{	
						System.out.println(NumberOfColumns);
						System.out.println("current row no is: "+count);

						System.out.println("Bill information for property no: " +newOldpid);

						taxname = row.getCell(1).toString();
						System.out.println("Tax Name is: " +taxname);

						outstandingamount = df.formatCellValue(row.getCell(2));
						System.out.println("Outstanding amount is:: "+outstandingamount);

						billdate = row.getCell(3).toString();
						System.out.println(billdate);


						SimpleDateFormat newDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
						Date myDate = newDateFormat.parse(billdate);
						newDateFormat.applyPattern("dd/MM/yyyy");
						billdate = newDateFormat.format(myDate);

						System.out.println("Bill Date is:: "+billdate);

						break;
					}				
					else
					{
						break;
						//System.out.println("No data found for the joint owner");
					}
				}
			}
		}
		catch (Exception e)
		{

		}
	}

	public void PropTaxUpload()
	{
		// Click Property Tax module
		//	
		mWaitForVisible("linkText", "Property Tax");
		mGenericWait();
		mClick("linkText", "Property Tax");

		//Wait for transactions and click on transactions
		//
		mWaitForVisible("linkText", "Transactions");
		mGenericWait();
		mClick("linkText", "Transactions");

		//Wait for property master upload and click on that
		//
		mWaitForVisible("linkText", "Property Master Upload");
		mGenericWait();
		mClick("linkText", "Property Master Upload");

		/////////////////////////////////////////////////////////////
		//Upload to temporary table
		///////////////////////////////////////////////////////////

		//Wait for Excel upload link and select Temporary Table from dropdown
		//
		mWaitForVisible("id", "excelUploadDTO.excelUploadFile");
		mGenericWait();
		mSelectDropDown("id", "excelUploadDTO.uploadType", "Upload Data to Temporary Table");

		// Upload Excel Sheet and Wait for Process button to be visible to ensure excel sheet has been loaded
		//
		mGenericWait();
		mWaitForVisible("id", "excelUploadDTO.excelUploadFile");
		mUpload("id", "excelUploadDTO.excelUploadFile", "C:\\Users\\sunil.sonmale\\Desktop\\Property_Master_Template_Eng.xls");

		mWaitForVisible("xpath", "//*[@id='frmMasterForm']/fieldset[1]/div[3]/input[1]");
		mGenericWait();

		//Select Property Information Sheet
		//
		mGenericWait();
		mClick("id", "sheetNoTick1");

		//Select Joint Owner Sheet
		//
		mGenericWait();		
		mClick("id", "sheetNoTick2");

		//Select Flat Information Sheet
		//
		mGenericWait();		
		mClick("id", "sheetNoTick3");

		//Select Bill Sheet
		//
		mGenericWait();
		mClick("id", "sheetNoTick4");

		//Click Process Data
		//
		mGenericWait();
		mClick("xpath", "//*[@id='frmMasterForm']/fieldset[1]/div[3]/input[1]");

		mWaitForVisible("xpath", "//*[@id='logDiv']/div/table");
		mGenericWait();		
		String xyz = mGetText("xpath", "//*[@id='logDiv']/div/table");
		//printing text of Log results
		System.out.println("captured string is : "+xyz);

		//click download log
		//
		mGenericWait();
		mWaitForVisible("css","u");
		mClick("css","u");

		//switching to 3rd tab and capturing text
		//
		mGenericWait();		
		mSwitchTab(2);
		mGenericWait();

		//Capturing body text
		//
		String bodytext = driver.findElement(By.tagName("body")).getText();
		System.err.println(bodytext);
		mCustomWait(3000);
		driver.close();
		mCustomWait(2000);
		mSwitchTab(1);

		//click reset...
		//
		mGenericWait();
		mWaitForVisible("xpath","//*[@id='frmMasterForm']/fieldset[1]/div[3]/input[2]");
		mClick("xpath","//*[@id='frmMasterForm']/fieldset[1]/div[3]/input[2]");

		//Wait for reset button to be invisible... 

		mWaitForInvisible("xpath", "//*[@id='frmMasterForm']/fieldset[1]/div[3]/input[2]");

		//Wait for upload button to be visible.. 
		//
		mWaitForVisible("xpath", "//*[@id='excelUploadDTO.excelUploadFile']");
		mCustomWait(3000);

		/////////////////////////////////////////////////////////////
		//Upload to MAIN table
		///////////////////////////////////////////////////////////

		//Select Main table
		//
		mWaitForVisible("id", "excelUploadDTO.uploadType");
		mGenericWait();
		mSelectDropDown("id", "excelUploadDTO.uploadType", "Upload Data to Main Table");
		mWaitForVisible("xpath", "//*[@id='excelUploadDTO.mainUploadType']");

		//click fetch data
		//
		mWaitForVisible("xpath","//*[@id='frmMasterForm']/fieldset[2]/div[1]/input[3]");
		mGenericWait();
		mClick("xpath","//*[@id='frmMasterForm']/fieldset[2]/div[1]/input[3]");
		mWaitForVisible("xpath", "//*[@id='pager_gridPropertyTaxRegistration_right']/div");
		mGenericWait();
		mGenericWait();
		mGetText("xpath", "//*[@id='pager_gridPropertyTaxRegistration_right']/div");

		System.out.println("Following is the text of the table:: "+mGetText("xpath", "//*[@id='pager_gridPropertyTaxRegistration_right']/div"));

		//Click Validate ///
		//
		mWaitForVisible("xpath","//*[@id='frmMasterForm']/fieldset[2]/div[1]/input[4]");
		mGenericWait();
		mClick("xpath","//*[@id='frmMasterForm']/fieldset[2]/div[1]/input[4]");
		System.err.println("Clicked validate");

		//Wait for Proceed
		//
		mCustomWait(100000);
		mWaitForVisible("id", "btnNo");
		System.err.println("Proceed found");
		mGenericWait();
		mClick("id", "btnNo");
		System.err.println("Clicked on Proceed");

		//Wait for Process button to disappear and reset button to be visible and then click on reset
		//
		mWaitForInvisible("xpath","//*[@class='css_btn' and @value='Process Data']");
		System.err.println("Proceed data invisible");
		mWaitForVisible("xpath","//*[@class='css_btn' and @value='Reset']");
		System.err.println("Reset visible");
		mGenericWait();
		mClick("xpath","//*[@class='css_btn' and @value='Reset']");
		System.err.println("Click reset");

		//Select Upload to main table option again
		//
		mWaitForVisible("id", "excelUploadDTO.uploadType");
		mGenericWait();
		mSelectDropDown("id", "excelUploadDTO.uploadType", "Upload Data to Main Table");

		//Select Property Upload from drop down
		//
		mWaitForVisible("id", "excelUploadDTO.mainUploadType");
		mGenericWait();
		mSelectDropDown("id", "excelUploadDTO.mainUploadType", "Property Upload");		

		//Click Process Data
		//
		mWaitForVisible("xpath", "//*[@id='frmMasterForm']/fieldset[2]/div[1]/input[1]");
		mGenericWait();
		mClick("xpath", "//*[@id='frmMasterForm']/fieldset[2]/div[1]/input[1]");

		//Confirm Process data
		//
		mWaitForVisible("id", "btnYes");
		mGenericWait();
		mClick("id", "btnYes");

		//Click proceed on confirmation of excel loaded message
		//
		mWaitForVisible("id", "btnNo");
		mGenericWait();
		mClick("id", "btnNo");

		//Wait for proceed button to disappear
		//
		mWaitForInvisible("id", "btnNo");
		mGenericWait();
		String logtext = mGetText("xpath", "//*[@id='logDiv']/div/table/tbody/tr[3]/td/font");
		System.out.println("Log text is: "+logtext);

		//Click download
		//		
		mWaitForVisible("css", "u");
		mGenericWait();
		mClick("css", "u");

		//switching to 3rd tab and capturing text
		//
		mCustomWait(3000);		
		mSwitchTab(2);
		mCustomWait(3000);

		//Capturing body text
		//
		bodytext = driver.findElement(By.tagName("body")).getText();
		System.err.println(bodytext);
		mCustomWait(3000);

		//Pattern firstpropertyno = Pattern;
		Pattern oldpropertyno = Pattern.compile("(?<=Old PID )\\s*([0-9]+)\\s*(?=successfully submitted)");
		Pattern newpropertyno = Pattern.compile("(?<=New Property No is)\\s*([0-9]+)\\s*(|\n)");

		////////////////////////////////////////////////////////////
		//
		Pattern actinsertrec =  Pattern.compile("\\s*([0-9]+)\\s*(?=Records Inserted out of)");
		Pattern totalrecordinsert = Pattern.compile("(?<=Records Inserted out of)\\s*([0-9]+)\\s*(|\n)");


		Matcher matcher3 = actinsertrec.matcher(bodytext);
		Matcher matcher4 = totalrecordinsert.matcher(bodytext);
		if(matcher3.find() && matcher4.find())
		{
			System.out.println(matcher3.group().trim());
			System.out.println(matcher4.group().trim());
		}

		if(matcher3.group().trim().equals(matcher4.group().trim()))
		{
			System.out.println("All records inserted");
		}		
		else
		{
			System.out.println("only " +matcher3.group().trim()+" records of " +matcher4.group().trim()+ "inserted");
		}

		/////////////////
		//////////////////////////////////////////////////////


		Matcher matcher = newpropertyno.matcher(bodytext);
		Matcher matcher1 = oldpropertyno.matcher(bodytext);

		int countt = 1;
		while(matcher.find() && matcher1.find()) {
			//System.out.println(matcher.group());
			oldnewpropno.put(matcher1.group().toString().trim(), matcher.group().toString().trim());
			newpropar.add(matcher.group().trim());
			oldpropar.add(matcher1.group().trim());
		}

		// Get a set of the entries
		Set<?> set = oldnewpropno.entrySet();

		// Get an iterator
		Iterator<?> ir = set.iterator();
		// Display elements
		while(ir.hasNext()) {

			me = (Map.Entry)ir.next();
			System.out.println(me.getKey() + ": "+ me.getValue());

		}
		String[] allmatchesnewpropno = newpropar.toArray(new String[0]);
		String[] allmatchesoldpropno = oldpropar.toArray(new String[0]);

		for (int i = 0; i < allmatchesnewpropno.length; i++) {
			System.out.println(allmatchesnewpropno [i]);
		}

		for (int i = 0; i < allmatchesoldpropno.length; i++) {
			System.out.println(allmatchesoldpropno [i]);
		}

		//System.out.println("Following are the captured property nos::: " +ar);
		driver.close();
		mCustomWait(2000);
		mSwitchTab(1);

		//Click Reset
		//
		mWaitForVisible("xpath", "//*[@id='frmMasterForm']/fieldset[2]/div[1]/input[1]");
		mGenericWait();
		mClick("xpath", "//*[@id='frmMasterForm']/fieldset[2]/div[1]/input[1]");

		//Wait for Reset button to disappear and upload button to reappear
		//
		mWaitForInvisible("xpath", "//*[@id='frmMasterForm']/fieldset[2]/div[1]/input[1]");
		mGenericWait();
		mWaitForVisible("id", "excelUploadDTO.excelUploadFile");

		//Select upload to main table again from dropdown..
		//
		mWaitForVisible("id", "excelUploadDTO.uploadType");
		mGenericWait();
		mSelectDropDown("id", "excelUploadDTO.uploadType", "Upload Data to Main Table");

		//Select bill upload
		//
		mWaitForVisible("id", "excelUploadDTO.mainUploadType");
		mGenericWait();
		mSelectDropDown("id", "excelUploadDTO.mainUploadType", "Bill Upload");

		//Click Process data
		//
		mWaitForVisible("xpath", "//*[@id='frmMasterForm']/fieldset[2]/div[1]/input[1]");
		mGenericWait();
		mClick("xpath", "//*[@id='frmMasterForm']/fieldset[2]/div[1]/input[1]");

		//Click proceed
		//
		mWaitForVisible("id", "btnYes");
		mGenericWait();
		mClick("id", "btnYes");

		//Click Proceed
		//
		mWaitForVisible("id", "btnNo");
		mGenericWait();
		mClick("id", "btnNo");

		//Click Download
		//
		mWaitForVisible("css", "u");
		mGenericWait();
		mClick("css", "u");

		//switching to 3rd tab and capturing text
		//
		mCustomWait(3000);		
		mSwitchTab(2);
		mCustomWait(3000);

		//Capturing body text
		//
		bodytext = driver.findElement(By.tagName("body")).getText();
		System.out.println(bodytext);
		mCustomWait(3000);
		driver.close();
		mCustomWait(2000);
		mSwitchTab(1);

		//Click Flush temp table
		//
		mWaitForVisible("xpath", "//*[@id='frmMasterForm']/fieldset[2]/div[1]/input[4]");
		mGenericWait();
		mClick("xpath", "//*[@id='frmMasterForm']/fieldset[2]/div[1]/input[4]");

		//Click Proceed
		//
		mWaitForVisible("id", "btnYes");
		mGenericWait();
		mClick("id", "btnYes");

	}

	public void PropUploadVerification()
	{
		mOpenBrowser(wGetPropertyFromFile("browserName"));
		mGetURL(wGetPropertyFromFile("url"));
		common.selectUlb();
		common.departmentLogin("deptUserName","deptPassword" );

		// Click Property Tax module
		//	
		mWaitForVisible("linkText", "Property Tax");
		mGenericWait();
		mClick("linkText", "Property Tax");

		//Wait for transactions and click on transactions
		//
		mWaitForVisible("linkText", "Transactions");
		mGenericWait();
		mClick("linkText", "Transactions");

		//Wait for property master upload and click on that
		//
		mWaitForVisible("linkText", "Property Master Upload");
		mGenericWait();
		mClick("linkText", "Self Assessment - Department Entry");

		//Wait for radio buttons to appear and click
		mWaitForVisible("id", "optionType3");
		mGenericWait();
		mClick("id", "optionType3");

		//Wait for property no textbox to appear
		mWaitForVisible("id", "OptionPropNo");
		mGenericWait();

		System.out.println(oldnewpropno.get(newOldpid));
		//String key = oldnewpropno.get((Object)newwOldpid);

		mHindiTextConversion("id", "OptionPropNo", oldnewpropno.get(newOldpid));

		//Wait for submit button to appear and then click..
		mWaitForVisible("xpath", "//*[@id='optionButtonID']/a");
		mGenericWait();
		mClick("xpath", "//*[@id='optionButtonID']/a");

		//Wait for submit and click on submit
		mWaitForVisible("xpath", "//*[@id='frmNoChangeInAssessment']/div[26]/input[1]");
		mGenericWait();

		////////Part to capture text and perform assertions
		//////////////////////////////////////////////////

		//Year of Assessment
		String YearOfAssessment = mGetText("xpath", "//*[@id='frmNoChangeInAssessment']/div[1]/div/b");
		System.out.println("Year of Assessment::::"+YearOfAssessment);



		//New Property No 

		String newPropertyNo = mGetText("xpath", "//*[@id='frmNoChangeInAssessment']/div[2]/div[1]").trim();
		newPropertyNo = newPropertyNo.replaceAll("\\D+","");
		System.out.println("New Property No ::::"+newPropertyNo);

		mAssert(newPropertyNo, oldnewpropno.get(newOldpid), "New Property No does not match");


		//New Holding No

		String newHoldingNo = mGetText("xpath", "//*[@id='frmNoChangeInAssessment']/div[2]/div[2]").trim();
		System.out.println("New Holding No::::"+newHoldingNo);

		Pattern nhno = Pattern.compile("(?<=New Holding No. :)\\s*(.*)\\s*(|\n)");

		Matcher matcher = nhno.matcher(newHoldingNo);
		if(matcher.find())
		{
			newHoldingNo = matcher.group(0).toString().trim();
			System.out.println(newHoldingNo);

		}


		mAssert(newHoldingNo, newholdingno, "New Holding No does not match");



		//Old Holding No

		String oldHoldingNo = mGetText("xpath", "//*[@id='frmNoChangeInAssessment']/div[2]/div[3]").trim();
		System.out.println("Old Holding No::::"+oldHoldingNo);

		Pattern ohno = Pattern.compile("(?<=Old Holding No. :)\\s*(.*)\\s*(|\n)");

		Matcher matcher1 = ohno.matcher(oldHoldingNo);
		if(matcher1.find())
		{
			oldHoldingNo = matcher1.group(0).toString().trim();
			System.out.println(oldHoldingNo);

		}


		mAssert(oldHoldingNo, oldholdingno, "New Holding No does not match");



		//Old PID No

		String oldPIDNo = mGetText("xpath", "//*[@id='frmNoChangeInAssessment']/div[2]/div[5]").trim();
		oldPIDNo = oldPIDNo.replaceAll("\\D+","");
		System.out.println("Old PID No::::"+oldPIDNo);

		mAssert(oldPIDNo, newOldpid, "OLD PID no does not match");

		//Ward 

		String Ward = mGetText("xpath", "//*[@id='frmNoChangeInAssessment']/div[2]/div[6]/span").trim();
		System.out.println("Ward::::"+ward);

		mAssert(Ward, ward, "WARD does not match");

		//Name

		String name = mGetText("xpath", "//*[@id='frmNoChangeInAssessment']/table[1]/tbody/tr[2]/td[2]").trim();
		System.out.println("Name::::"+name);

		mAssert(name, title+soname, "Name of Applicant does not match");

		//Father Name

		String fathername = mGetText("xpath", "//*[@id='frmNoChangeInAssessment']/table[1]/tbody/tr[2]/td[3]").trim();
		System.out.println("Father Name::::"+fathername);

		mAssert(fathername, fhname, "Father name does not match");


		//Gender

		String Gender = mGetText("xpath", "//*[@id='frmNoChangeInAssessment']/table[1]/tbody/tr[2]/td[4]").trim();
		System.out.println("Gender::::"+Gender);

		mAssert(Gender, gender, "Gender of applicant does not match");


		//Add1

		String ADD1 = mGetText("xpath", "//*[@id='frmNoChangeInAssessment']/div[6]/div[2]").trim();
		System.out.println("Add1::::"+ADD1);

		mAssert(ADD1, add1, "Address line 1 of property does not match");

		//Add2

		String ADD2 = mGetText("xpath", "//*[@id='frmNoChangeInAssessment']/div[7]/div[2]").trim();
		System.out.println("Add2::::"+ADD2);

		mAssert(ADD2, add2, "Address line 2 of property does not match");

		//Pincode

		String Pincode = mGetText("xpath", "//*[@id='frmNoChangeInAssessment']/div[8]/div[2]").trim();
		System.out.println("Pincode::::"+Pincode);


		mAssert(Pincode, pincode, "Pincode of property does not match");

		//Mobile No

		String mobileno = mGetText("xpath", "//*[@id='frmNoChangeInAssessment']/div[15]/div[2]").trim();
		System.out.println("Mobile No::::"+mobileno);		
		mobileno = mobileno.replaceAll("\\D+","");		
		mobileno = mobileno.substring(2, 12);
		System.out.println(mobileno);

		mAssert(mobileno, mobno, "Mobile no does not match");


		//Email ID

		String emailID = mGetText("xpath", "//*[@id='frmNoChangeInAssessment']/div[15]/div[3]").trim();
		System.out.println("Email ID::::"+emailID);

		Pattern emID = Pattern.compile("(?<=Email ID :)\\s*(.*)\\s*(|\n)");

		Matcher matcher3 = emID.matcher(emailID);
		if(matcher3.find())
		{
			emailID = matcher3.group(0).toString().trim();
			System.out.println(emailID);

		}

		mAssert(emailID, emailid, "Email ID does not match");

		//Acquisition Date 

		String acquisitionDate = mGetText("xpath", "//*[@id='frmNoChangeInAssessment']/div[18]/div/span").trim();
		System.out.println("Acquisition Date::::"+acquisitionDate);

		mAssert(acquisitionDate, acqdate, "Acquisition Date does not match");



		//Ratable Area

		String ratablearea = mGetText("xpath", "//*[@id='frmNoChangeInAssessment']/div[19]/div[1]").trim();
		System.out.println("Ratable Area::::"+ratablearea);

		Pattern ratrea = Pattern.compile("(?<=Ratable Area (Sq.Ft.) :)\\s*([0-9]+)\\s*(|\n)");

		Matcher matcher4 = ratrea.matcher(ratablearea);
		if(matcher4.find())
		{
			ratablearea = matcher4.group(0).toString().trim();
			System.out.println(ratablearea);

		}

		//double rarea;

		//double rarea = Integer.parseInt(ratablearea);

		//ratablearea = Integer.toString((int) rarea);

		//System.out.println(ratablearea);
		System.out.println(ratarea);
		ratarea = String.valueOf(ratarea);
		mAssert(ratablearea, ratarea, "Ratable area does not match");


		//Annual rental value

		String annualrentalvalue = mGetText("xpath", "//*[@id='frmNoChangeInAssessment']/div[19]/div[2]/span").trim();
		System.out.println("Annual rental value::::"+annualrentalvalue);

		//mAssert(annualrentalvalue, ratarea, "Annual rent value does not match");


		//Taxable vacant land

		String taxablevacantland = mGetText("xpath", "//*[@id='frmNoChangeInAssessment']/div[20]/div[1]/span").trim();
		System.out.println("Taxable vacant land::::"+taxablevacantland);

		//Arrears

		String arrears = mGetText("xpath", "//*[@id='frmNoChangeInAssessment']/table[2]/tbody/tr[1]/td[2]").trim();
		System.out.println("Arrears::::"+arrears);

		mAssert(arrears, outstandingamount, "Arrears do not match");

		mClick("xpath", "//*[@id='frmNoChangeInAssessment']/div[26]/input[1]");

		common.logOut();
		common.finalLogOut();
		mCloseBrowser();

	}




	public ResultSet dbtesting()
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

			String URL = "jdbc:oracle:thin:@103.241.183.246:1521:rac";
			String USER = "live_PATNA_PROD_TEST_read";
			String PASS = "live_PATNA_PROD_TEST_read";
			Connection conn = DriverManager.getConnection(URL, USER, PASS);

			//	Connection conn = DriverManager.getConnection("jdbc:odbc:https://192.168.33.101:1521:rac");
			Statement st=conn.createStatement();

			rs1 = st.executeQuery("select * from tb_prop_mas where orgid=570 order by 1 desc");

			while (rs1.next()) 
			{		
				System.out.println(rs1.getString(1)+"---- "+rs1.getInt(2) + "---- " + rs1.getString(3));
			}
			conn.close();

		}
		catch(Exception e)
		{
			System.out.println(e);	
		}
		return rs1;
	}

	public void outputfirstsheet() throws ParseException
	{
		//New holding no
		newholdingno= df.formatCellValue(row.getCell(0));

		//Old Holding No
		oldholdingno= row.getCell(1).toString().trim();

		//old PID					
		newOldpid = df.formatCellValue(row.getCell(2)).trim();

		//Ward
		ward = row.getCell(3).toString().trim();

		//Rcircle no
		rcircleno = row.getCell(8).toString();

		//Owner Type
		ownertype =row.getCell(13).toString();

		if(ownertype.equals("Joint Owners"))
		{
			//method to go to joint owner sheet will come here...
			jointownersheet();
		}

		//Title
		title =row.getCell(14).toString().trim(); 

		//Name of Aplicant
		soname = row.getCell(15).toString().trim();

		//Name of Father
		fhname = row.getCell(16).toString().trim();

		//Gender
		gender = row.getCell(17).toString().trim();

		//Address line 1
		add1 = row.getCell(21).toString().trim();

		//Address line 2
		add2 = row.getCell(22).toString().trim();

		//Pincode					
		pincode = df.formatCellValue(row.getCell(23)).trim();

		//Same as address
		sadd = row.getCell(24).toString().trim();

		// Mobile No				
		mobno = df.formatCellValue(row.getCell(30)).trim();

		//Email ID
		emailid = row.getCell(31).toString();
		//Land Type
		landtype = row.getCell(32).toString();
		//Apartment Name
		aptname = row.getCell(33).toString();
		//Total Flats
		totalflatapp = df.formatCellValue(row.getCell(34));
		//Total Flats
		acqdate = row.getCell(35).toString();

		SimpleDateFormat newDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date myDate = newDateFormat.parse(acqdate);
		newDateFormat.applyPattern("dd/MM/yyyy");
		acqdate = newDateFormat.format(myDate);

		System.out.println("Bill Date is:: "+acqdate);




		//Road Factor
		roadfactor = row.getCell(36).toString();
		//Total Area
		totalarea = df.formatCellValue(row.getCell(37));
		//Built Up Area
		builtuparea = df.formatCellValue(row.getCell(38));
		//Water connection facility
		waterconnfac = row.getCell(40).toString();
		//Water Tax
		wtt = row.getCell(43).toString();
		//Last self assessment year
		lastselfyearass = row.getCell(46).toString();

		//method to go to 

		System.out.println(" " +newholdingno+ " " + oldholdingno+ " " + newOldpid + " " + ward + " " +rcircleno + " " +ownertype+ " " + title+ " " + soname
				+ " " + fhname + " " + gender + " " + add1 + " " + add2 + " " +pincode + " " + sadd + " " + mobno + " " + emailid + " " + landtype + " " + aptname + " " + totalflatapp + " " + acqdate + " " + roadfactor + " " + totalarea + " " + builtuparea + " " + waterconnfac + " " +
				wtt + " " +lastselfyearass);

	}


}
