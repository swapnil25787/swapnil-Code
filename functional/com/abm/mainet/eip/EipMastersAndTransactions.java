package com.abm.mainet.eip;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
//import javafx.scene.transform.Translate;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import com.google.common.base.Throwables;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import common.CommonMethods;
import errorhandling.MainetCustomExceptions;
import excelreader.ExcelReader;
import excelreader.ExcelWriting;

public class EipMastersAndTransactions extends CommonMethods {

	ExcelReader ER = new ExcelReader();
	ExcelWriting EW =new ExcelWriting();
	CommonMethods common=new CommonMethods();
	public String Image_url;
	public String captex="";
	String modulenamenew;
	String linknamenew;

	@BeforeSuite
	public void beforeSuite(){

		System.out.println("Starting before suite");
		thisClassName=this.getClass().getCanonicalName();
		myClassName = thisClassName;
		System.out.println("This is the current class name: "+myClassName );
		mCreateModuleFolder("EIP_",myClassName);

	}

	@Test(enabled=false)
	public void EIP_Quick_Link_Master()
	{
		try{
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
			ER.datareader();
			thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			//System.err.println(fromcolumn+"SWAPNIL===>"+wGetPropertyFromFile1("Quick_link_order1"));
			
			quick_link_master();
			//mCloseBrowser();

		}catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in quick_link_master method");
		}
	}
	public void quick_link_master()
	{

		try{

			mCreateArtefactsFolder("EIP_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			mCustomWait(2000);
			/*driver.findElement(By.id("menu-toggle")).click();
			mCustomWait(2000);
			
driver.findElement(By.linkText("Sitemap")).click();			
			
mCustomWait(2000);

List<WebElement> links = driver.findElements(By.tagName("input"));
for (int i = 0; i < links.size(); i++) {
	String s=links.get(i).getAttribute("value");
	System.out.println("s====>"+s);
}*/
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			productdeptselect();
			int result = Integer.parseInt(mGetPropertyFromFile("no_of_quick_link"));
			for (int j = 1; j <=result; j++) {
				quick_link_master_script(j);
			}
			
		//	mCloseBrowser();
			


		}catch(Exception e){

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in quick_link_master");
		}
	}

	
	

	public void productdeptselect()
	{
		try{
			mWaitForVisible("id", mGetPropertyFromFile("Product_menuid"));
			mClick("id", mGetPropertyFromFile("Product_menuid"));  
			/*mWaitForVisible("id", mGetPropertyFromFile("ulb_name_id"));
			Select drpCountry = new Select(driver.findElement(By.id(mGetPropertyFromFile("ulb_name_id"))));
			drpCountry.selectByVisibleText(mGetPropertyFromFile("ulb_name_data"));
			
			mClick("id", mGetPropertyFromFile("submitMunci_id"));
			*//*mCustomWait(2000);
			driver.findElement(By.cssSelector("#text-resize > header.site-header > nav > div.top-line.navbar-fixed-top > div > div.col-md-10.col-sm-12.col-xs-12.text-right > div > span:nth-child(13) > a")).click();
			System.err.println("777");*/
			mWaitForVisible("id", mGetPropertyFromFile("Product_menuid"));
	 		mClick("id", mGetPropertyFromFile("Product_menuid"));
			mCustomWait(2000);
			
			mWaitForVisible("xpath", mGetPropertyFromFile("Dept_user_prod_id"));
			mClick("xpath", mGetPropertyFromFile("Dept_user_prod_id"));
			mWaitForVisible("id", mGetPropertyFromFile("emploginname_id"));
			mSendKeys("id", mGetPropertyFromFile("emploginname_id"), mGetPropertyFromFile("emploginname_data"));
			mSendKeys("id", mGetPropertyFromFile("emppassword_id"), mGetPropertyFromFile("emppassword_data"));
			//mSendKeys("id", mGetPropertyFromFile("captcha_id"), mGetPropertyFromFile("captcha_data"));
			mCustomWait(5000);
			mClick("css", mGetPropertyFromFile("submit_prod_id"));
			
			
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in quick_link_master method");
		}
	}
	


	
	






	


	public void quick_link_master_script(int i)
	{

		try{
			
			mWaitForVisible("id", mGetPropertyFromFile("Product_menuid"));
			mClick("id", mGetPropertyFromFile("Product_menuid"));
			WebDriverWait wait = new WebDriverWait(driver, 100);
			WebElement element = wait.until(
			        ExpectedConditions.visibilityOfElementLocated(By.linkText("Add Quick Link")));

			driver.findElement(By.linkText("Add Quick Link")).click();
			
			 element = wait.until(
			        ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Add")));
		driver.findElement(By.partialLinkText("Add")).click();
		mWaitForVisible("css", mGetPropertyFromFile("Quick_submit_id"));
		driver.findElement(By.id("linkOrder")).clear();
		driver.findElement(By.id("linkOrder")).sendKeys(mGetPropertyFromFile("Quick_link_order"+i));
		
		driver.findElement(By.id("linkTitleEg")).sendKeys(mGetPropertyFromFile("linkTitleEg_data"+i));
		driver.findElement(By.id("linkTitleReg")).sendKeys(mGetPropertyFromFile("linkTitleReg_data"+i));
		
		driver.findElement(By.id("linkRadioButton2")).click();
		driver.findElement(By.id("linkPath")).sendKeys("google.com");
		
		
		Select drpCountry1 = new Select(driver.findElement(By.id("isLinkModify")));
		drpCountry1.selectByVisibleText(mGetPropertyFromFile("higligt_data"));
		
		
		
		mClick("css", mGetPropertyFromFile("Quick_submit_id"));
		mWaitForVisible("css", mGetPropertyFromFile("btnNo_id"));
		mClick("css", mGetPropertyFromFile("btnNo_id"));
		mWaitForVisible("id", mGetPropertyFromFile("Product_menuid"));
		mClick("id", mGetPropertyFromFile("Product_menuid"));
		
		mWaitForVisible("linkText", mGetPropertyFromFile("pd_Quick_approveid"));
		mClick("linkText", mGetPropertyFromFile("pd_Quick_approveid"));
		mWaitForVisible("id", mGetPropertyFromFile("findrecord_id"));
		/*mClick("id", mGetPropertyFromFile("findrecord_id"));
		mWaitForVisible("id", mGetPropertyFromFile("findtn_id"));
		mWaitForVisible("css", mGetPropertyFromFile("finddropid"));
		
		Select drpCountry12 = new Select(driver.findElement(By.xpath(mGetPropertyFromFile("finddropid"))));
		drpCountry12.selectByVisibleText("Title (English)");
		
		mSendKeys("css", mGetPropertyFromFile("text_pb_id"), mGetPropertyFromFile("linkTitleEg_data"+i));
		mCustomWait(3000);
		mClick("id", mGetPropertyFromFile("findtn_id"));*/
		
		
		mWaitForVisible("css", mGetPropertyFromFile("prd_grid_edit_id"));
		mClick("css", mGetPropertyFromFile("prd_grid_edit_id"));
		
		
		mWaitForVisible("css", mGetPropertyFromFile("chekersubmitid"));
		mCustomWait(2000);
		driver.findElement(By.id("entity.chekkerflag1")).click();
		
		mClick("css", mGetPropertyFromFile("chekersubmitid"));
		mWaitForVisible("css", mGetPropertyFromFile("btnNo_id"));
		mClick("css", mGetPropertyFromFile("btnNo_id"));
		
		}
		catch(Exception e){

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in quick_link_master script");
		}
	}

	
	
	

	




	

	




	/////////////////////////////////////////addlink//////////////////////////////

	@Test(enabled=false)
	public void EIP_Add_Link_Master()
	{
		try{	
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			
			
			
			mCreateArtefactsFolder("EIP_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			productdeptselect();
			int result = Integer.parseInt(mGetPropertyFromFile("noofsublink"));
			for (int k = 1; k <=result; k++) {
			EIP_Add_Link_Master_script(k);
			}
//mCloseBrowser();



		}catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in EIP_Add_Link_Master method");
		}
	}

	
	public void EIP_Add_Link_Master_script(int k)
	{

		try{
			
			mWaitForVisible("id", mGetPropertyFromFile("Product_menuid"));
			mClick("id", mGetPropertyFromFile("Product_menuid"));
			WebDriverWait wait = new WebDriverWait(driver, 100);
			WebElement element = wait.until(
			        ExpectedConditions.visibilityOfElementLocated(By.linkText("Add Links")));

			driver.findElement(By.linkText("Add Links")).click();
			
			 element = wait.until(
			        ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Add New Section")));
			 
			 driver.findElement(By.partialLinkText("Add New Section")).click();
			 mWaitForVisible("xpath", mGetPropertyFromFile("save&continuedid"));
			 mCustomWait(2000);
			 Select drpCountry123 = new Select(driver.findElement(By.id(mGetPropertyFromFile("ModuleName_id"))));
				drpCountry123.selectByVisibleText(mGetPropertyFromFile("ModuleName_data"+k).trim());
				
				
				
								
				mSendKeys("id", mGetPropertyFromFile("subLinkNameEn_id"), mGetPropertyFromFile("SectionName_eng"+k).trim());
				mSendKeys("id", mGetPropertyFromFile("subLinkNameRg_data"), mGetPropertyFromFile("SectionName_hindi"+k).trim());
				mSendKeys("id", mGetPropertyFromFile("subLinkOrder_id"), mGetPropertyFromFile("SubLinkOrder_data"+k));
				
				 Select drpCountry122 = new Select(driver.findElement(By.id(mGetPropertyFromFile("hilhligt_link_id"))));
					drpCountry122.selectByVisibleText("False");
				
			 
					Select drpCountry1222 = new Select(driver.findElement(By.id(mGetPropertyFromFile("sectionType_id"))));
					drpCountry1222.selectByVisibleText(mGetPropertyFromFile("section_name"));
			
			 mClick("xpath", mGetPropertyFromFile("save&continuedid"));
			 
			 element = wait.until(
				        ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Add New Element")));
				 mCustomWait(2000);
				 driver.findElement(By.partialLinkText("Add New Element")).click();
			 
				 
			 
				 mWaitForVisible("xpath", mGetPropertyFromFile("addelement_id"));
				 
				 
				 
				 mCustomWait(2000);
				 driver.findElement(By.id("subLinkFieldMapping.subLinkFieldlist0.fieldNameEn")).sendKeys(mGetPropertyFromFile("SectionName_eng"+k));
				 driver.findElement(By.id("subLinkFieldMapping.subLinkFieldlist0.fieldNameRg")).sendKeys(mGetPropertyFromFile("SectionName_hindi"+k));
				 
				 Select drpCountry1234 = new Select(driver.findElement(By.id(mGetPropertyFromFile("TypeofField_id"))));
					drpCountry1234.selectByVisibleText("HTMLTextArea");
				 
				 
				 mClick("xpath", mGetPropertyFromFile("addelement_id"));
				 
				 
				
				 /////add code elemts attachment and photo
				 
				 element = wait.until(
					        ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Add New Element")));
					 mCustomWait(2000);
					 driver.findElement(By.partialLinkText("Add New Element")).click();
					
					 
				 
					 mWaitForVisible("xpath", mGetPropertyFromFile("addelement_id"));
					 
					
					 
					 mCustomWait(2000);
					 
					 driver.findElement(By.id("subLinkFieldMapping.subLinkFieldlist0.fieldNameEn")).sendKeys("Upload File");
					 driver.findElement(By.id("subLinkFieldMapping.subLinkFieldlist0.fieldNameRg")).sendKeys("अपलोड दस्तावेज़");
					 
					 Select drpCountry1234f = new Select(driver.findElement(By.id(mGetPropertyFromFile("TypeofField_id"))));
						drpCountry1234f.selectByVisibleText("Attachment");
					 
					 
					 mClick("xpath", mGetPropertyFromFile("addelement_id"));
					 mCustomWait(2000);
					 mWaitForVisible("xpath", mGetPropertyFromFile("save_ids"));
					 
					 
					 ///second element
					 
					 element = wait.until(
						        ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Add New Element")));
						 mCustomWait(2000);
						 driver.findElement(By.partialLinkText("Add New Element")).click();
					 
					 
						 mWaitForVisible("xpath", mGetPropertyFromFile("addelement_id"));
						 
						 
						 
						 mCustomWait(2000);
						 driver.findElement(By.id("subLinkFieldMapping.subLinkFieldlist0.fieldNameEn")).sendKeys("Upload Image");
						 driver.findElement(By.id("subLinkFieldMapping.subLinkFieldlist0.fieldNameRg")).sendKeys("तस्वीर डालिये");
						 
						 Select drpCountry1234ff = new Select(driver.findElement(By.id(mGetPropertyFromFile("TypeofField_id"))));
							drpCountry1234ff.selectByVisibleText("Photo");
						 
						 
						 mClick("xpath", mGetPropertyFromFile("addelement_id"));
						 mCustomWait(2000);
					
				 ///////////////////////////////////////////////////////////////
				 mWaitForVisible("xpath", mGetPropertyFromFile("save_ids"));
				 
				 mClick("xpath", mGetPropertyFromFile("save_ids"));
 mWaitForVisible("xpath", mGetPropertyFromFile("btnNo_ids1"));
				 
				 mClick("xpath", mGetPropertyFromFile("btnNo_ids1"));
				 
				 
				 
				 
				 //cheker
				 
				 mWaitForVisible("id", mGetPropertyFromFile("Product_menuid"));
					mClick("id", mGetPropertyFromFile("Product_menuid"));
					 element = wait.until(
					        ExpectedConditions.visibilityOfElementLocated(By.linkText("Approve Links")));

					driver.findElement(By.linkText("Approve Links")).click();
					
					 //element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='search_gridSectionEntryForm']/div/span")));
					 mCustomWait(2000);
/*				 driver.findElement(By.xpath("//*[@id='search_gridSectionEntryForm']/div/span")).click();
				 mWaitForVisible("xpath", mGetPropertyFromFile("dropfindadd_id"));
				 Select drpCountrys = new Select(driver.findElement(By.xpath(mGetPropertyFromFile("dropfindadd_id"))));
					drpCountrys.selectByVisibleText("Link Name (English)");
				 driver.findElement(By.id("jqg2")).sendKeys((mGetPropertyFromFile("SectionName_eng"+k).trim()).trim());
				 driver.findElement(By.id("fbox_gridSectionEntryForm_search")).click();*/
				 mWaitForVisible("css", mGetPropertyFromFile("prd_grid_edit_id"));
				 mClick("css", mGetPropertyFromFile("prd_grid_edit_id"));
				 mWaitForVisible("xpath", mGetPropertyFromFile("save&continuedid"));
			 	 
				 element = wait.until(
					        ExpectedConditions.visibilityOfElementLocated(By.id("entity.chekkerflag1")));
				 mCustomWait(2000);
				 driver.findElement(By.id("entity.chekkerflag1")).click();
				 mClick("xpath", mGetPropertyFromFile("save&continuedid"));
				 mWaitForVisible("xpath", mGetPropertyFromFile("save_ids_ch1"));
				 
				 mClick("xpath", mGetPropertyFromFile("save_ids_ch1"));
				 mWaitForVisible("xpath", mGetPropertyFromFile("btnNo_ids1"));
								 
								 mClick("xpath", mGetPropertyFromFile("btnNo_ids1"));
								 
								 
								 
								 
								 
								 
								 ///////////////////////
								 
								 
								 
								 
								 
								 
								 
								 
				
		
		}
		catch(Exception e){

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in quick_link_master script");
		}
	}


	
	
	
	
	
	
	@Test(enabled=false)
	public void EIP_Add_section_details()
	{
		try{	
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			
			
			
			mCreateArtefactsFolder("EIP_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			productdeptselect();
			
//mCloseBrowser();
			
				EIP_Add_section_details_script();
			
			



		}catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in EIP_Add_Link_Master method");
		}
	}
	public void EIP_Add_section_details_script()
	{

		try{
			
			mWaitForVisible("id", mGetPropertyFromFile("Product_menuid"));
			mClick("id", mGetPropertyFromFile("Product_menuid"));
			WebDriverWait wait = new WebDriverWait(driver, 100);
			WebElement element = wait.until(
			        ExpectedConditions.visibilityOfElementLocated(By.linkText("Add Section Details")));

			driver.findElement(By.linkText("Add Section Details")).click();
			
			
			 
		mCustomWait(1000);
			 mWaitForVisible("css", mGetPropertyFromFile("Section_find_btn"));
			 mClick("css", mGetPropertyFromFile("Section_find_btn"));
			 mCustomWait(1000);
			 Select drpCountry123 = new Select(driver.findElement(By.cssSelector("#fbox_gridSectionDetails > table > tbody > tr:nth-child(3) > td.columns > select")));
				drpCountry123.selectByVisibleText("Link Name (English)");
				
				
				driver.findElement(By.id("jqg2")).sendKeys(mGetPropertyFromFile("Linknameeng_data"));
				mCustomWait(1000);
				driver.findElement(By.id("fbox_gridSectionDetails_search")).click();
				 mWaitForVisible("css", mGetPropertyFromFile("prd_editbtn"));
				 mClick("css", mGetPropertyFromFile("prd_editbtn"));
				 
				 mWaitForVisible("id", mGetPropertyFromFile("AdminAdd_id"));
				 mClick("id", mGetPropertyFromFile("AdminAdd_id"));
				 //submit visiblity
				 mWaitForVisible("css", mGetPropertyFromFile("Submit_sec_id"));
				 
				 mWaitForVisible("id", mGetPropertyFromFile("cke_id"));
				 mCustomWait(1000);
				 mClick("id", mGetPropertyFromFile("cke_id"));
				 
				 
				 mWaitForVisible("css", mGetPropertyFromFile("CKE1_edit_id"));
				 mCustomWait(1000);
				 mClick("css", mGetPropertyFromFile("CKE1_edit_id"));
				 
				 String str=null;
			    // FileReader fr = new FileReader("C:\\Users\\Swapnil.patil\\Desktop\\ss.txt");
			    // InputStream in = new FileInputStream(File("C:\\Users\\Swapnil.patil\\Desktop\\ss.txt"));
			   //  System.err.println("in"+in);
			   
			     
			    	// driver.findElement(By.cssSelector(mGetPropertyFromFile("CKE1_edit_id"))).sendKeys(str);
			    	
				
			    	 WebElement IdEditorToPaste = driver.findElement(By.cssSelector(mGetPropertyFromFile("CKE1_edit_id")));
			    	 IdEditorToPaste.clear();
			    	 //((Object) IdEditorToPaste).SendKeys(Keys.CONTROL, 'v');
			    	 //IdEditorToPaste.sendKeys(Keys.CONTROL,"v");
			    	// IdEditorToPaste.sendKeys();
			    	 //IdEditorToPaste.sendKeys(readFile("C:\\Users\\Swapnil.patil\\Desktop\\ss.txt"));
			    	 File file = new File("C:\\Users\\Swapnil.patil\\Desktop\\n1.txt.html");
			    	 
					 String content = FileUtils.readFileToString(file);
			    	 //IdEditorToPaste.sendKeys(content); working
			    	 setClipboardContents(content);
				
			    	 IdEditorToPaste.sendKeys(Keys.CONTROL + "v");
				 
			    	// element.sendKeys(Keys.CONTROL + "v");
				 mCustomWait(1000);
			    	 mClick("css", mGetPropertyFromFile("cke_id"));
			
			    	 mWaitForVisible("css", mGetPropertyFromFile("cke_idhindi"));
					 mCustomWait(1000);
					 mClick("css", mGetPropertyFromFile("cke_idhindi"));
					 
					 WebElement IdEditorToPaste1 = driver.findElement(By.cssSelector(mGetPropertyFromFile("CKE2_edit_id")));
			    	 IdEditorToPaste1.clear();
			    	 IdEditorToPaste1.sendKeys(Keys.CONTROL + "v");
			    	 mCustomWait(1000);
					 mClick("id", mGetPropertyFromFile("cke_idhindi"));
					 
					 
					 
					 mClick("css", mGetPropertyFromFile("Submit_sec_id"));
					 
					/*// mCustomWait(2000);
					// driver.findElement(By.partialLinkText("Select Language")).click();
					// driver.findElement(By.id(":0.targetLanguage").linkText("Hindi")).click();
					 mCustomWait(2000);
					 Actions actions = new Actions(driver);
					 WebElement mainMenu = driver.findElement(By.linkText("Hindi"));
					 
					 
					 actions.moveToElement(mainMenu);
					 actions.click().build().perform();
					 
					 Actions actions = new Actions(driver);
					 WebElement mainMenu = driver.findElement(By.partialLinkText("Select Language"));
					 actions.moveToElement(mainMenu);
					 actions.click().build().perform();
					 mCustomWait(2000);
					 WebElement subMenu = driver.findElement(By.linkText("Hindi"));
					 
					 actions.moveToElement(subMenu);
					 actions.click().build().perform();
					 
					 
					 Actions action = new Actions(driver);
					 WebElement mainMenu = driver.findElement(By.partialLinkText("Select Language"));
					 ((Actions) action.click(mainMenu).build()).moveToElement(driver.findElement(By.xpath("//*[@id=':1.menuBody']/table/tbody/tr/td[7]/a[5]/div/span[2]"))).click().build().perform();
					 //action.moveToElement(mainMenu).moveToElement(driver.findElement(By.xpath("//*[@id=':1.menuBody']/table/tbody/tr/td[7]/a[5]/div/span[2]"))).click().build().perform();
					 
					
					 
					 Select drpCountry12355 = new Select(driver.findElement(By.id(":0.targetLanguage")));
					 mCustomWait(2000);
						drpCountry12355.selectByVisibleText("Hindi");
					 
					driver.findElement(By.partialLinkText("Select Language")).click();
					 
					 mCustomWait(2000);
					 driver.findElement(By.xpath("//*[@id=':1.menuBody']/table/tbody/tr/td[7]/a[5]/div/span[2]")).click();
					
					// driver.findElement(By.cssSelector("#\3a 1\2e menuBody > table > tbody > tr > td:nth-child(7) > a:nth-child(5) > div > span.text")).click();
					 WebElement elements = driver.findElement(By.linkText("Hindi"));
					 JavascriptExecutor executor = (JavascriptExecutor)driver;
					 executor.executeScript("arguments[0].click();", elements);
					 
					 
					 Select drpCountry123 = new Select(driver.findElement(By.cssSelector("#fbox_gridSectionDetails > table > tbody > tr:nth-child(3) > td.columns > select")));
						drpCountry123.selectByVisibleText("Link Name (English)");*/
					 mWaitForVisible("id", mGetPropertyFromFile("Product_menuid"));
						mClick("id", mGetPropertyFromFile("Product_menuid"));
					//	driver.findElement(By.xpath("//*[@id='navigation']/div/div/div[2]/div/div[2]")).click();
						driver.get("http://182.18.168.246:8140/SectionDetails.html?AdminFaqCheker");
						
						
						/*JavaScriptExecutor js = (JavaScriptExecutor) driver;
						WebElement hiddenElement = js.executeScript("return document.getElementBy.id());
						hiddenElement.click();*/
						/*mscroll(56, 510);
						WebDriverWait wait1 = new WebDriverWait(driver, 100);
						
						//*[@id="24"]/a
						WebElement element1 = wait1.until(
						        ExpectedConditions.visibilityOfElementLocated(By.linkText("Approve Section Details")));

						driver.findElement(By.linkText("Approve Section Details")).click();*/
						
						
						 
					mCustomWait(2000);
						 mWaitForVisible("css", mGetPropertyFromFile("Section_find_btn"));
						 mClick("css", mGetPropertyFromFile("Section_find_btn"));
						 mCustomWait(2000);
						  element = wait.until(
							        ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#fbox_gridSectionDetails > table > tbody > tr:nth-child(3) > td.columns > select")));
						 Select drpCountry123gg = new Select(driver.findElement(By.cssSelector("#fbox_gridSectionDetails > table > tbody > tr:nth-child(3) > td.columns > select")));
						 drpCountry123gg.selectByVisibleText("Link Name (English)");
							
							
							driver.findElement(By.id("jqg2")).sendKeys(mGetPropertyFromFile("Linknameeng_data"));
							mCustomWait(1000);
							driver.findElement(By.id("fbox_gridSectionDetails_search")).click();
							 mWaitForVisible("css", mGetPropertyFromFile("prd_editbtn"));
							 mClick("css", mGetPropertyFromFile("prd_editbtn"));
							 
							 mWaitForVisible("css", mGetPropertyFromFile("edit_sec_id"));
							 mCustomWait(2000);
							 mClick("css", mGetPropertyFromFile("edit_sec_id"));
							 //submit visiblity
							 mWaitForVisible("css", mGetPropertyFromFile("Submit_sec_id"));
							 
							 mCustomWait(2000);
							 driver.findElement(By.id("entity.chekkerflag1")).click();
							 mClick("css", mGetPropertyFromFile("Submit_sec_id"));
							
		}
		catch(Exception e){

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in quick_link_master script");
		}
	}
	
	
	String readFile(String fileName) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
	
	public static void setClipboardContents(String text) {
		  StringSelection stringSelection = new StringSelection( text );
		  Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		  clipboard.setContents(stringSelection, null);
		}
	
	
	public String wGetPropertyFromFile1(String key){
		String value = "";

		try{

			
			 if(hmapid.get(key) != null)
			{
				//value = hmapid.get(key);
				actualKey=key;
				value = hmapid.get(key).get(1);
				System.out.println("value"+value);

				System.err.println("This is the key "+actualKey+" and this is the value of key "+value);
			}
			else if(hmap.get(key) != null)
			{
				value = hmap.get(key).get(CurrentinvoCount);
				System.out.println("value"+value);
			}
			else if(hmapforformula.get(key) != null)
			{
				value = hmapforformula.get(key).get((InvoCount-1));
				System.out.println("value"+value);
			}
			 
		} catch (Exception e) {

			e.printStackTrace();
			exception = e.toString();			
			stackTrace = Throwables.getStackTraceAsString(e);
			throw new MainetCustomExceptions("Error in wGetPropertyFromFile method");

		}
		return value;

	
	}
	
	
	/////////////////////////correction purpose
	@Test(enabled=false)
	public void EIP_Add_Link_Master_correction()
	{
		try{	
			currentmethodname = Thread.currentThread().getStackTrace()[1].getMethodName();
		ER.datareader();
		thisMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			
			
			
			mCreateArtefactsFolder("EIP_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			productdeptselect();
			
			int result = Integer.parseInt(mGetPropertyFromFile("noofsublink"));
			for (int k = 1; k <=result; k++) {
				
				EIP_Add_Link_Master_script_correc(k);
				
				
			}
//mCloseBrowser();



		}catch(Exception e)
		{
			e.printStackTrace();
			inAtTest = true;
			throw new MainetCustomExceptions("Error in EIP_Add_Link_Master method");
		}
	}

	
	
	public void EIP_Add_Link_Master_script_correc(int k)
	{

		try{
			
			mWaitForVisible("id", mGetPropertyFromFile("Product_menuid"));
			mClick("id", mGetPropertyFromFile("Product_menuid"));
			WebDriverWait wait = new WebDriverWait(driver, 100);
			WebElement element = wait.until(
			        ExpectedConditions.visibilityOfElementLocated(By.linkText("Add Links")));

			driver.findElement(By.linkText("Add Links")).click();
			
			 element = wait.until(
			        ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Add New Section")));
			 element = wait.until(
				        ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='search_gridSectionEntryForm']/div/span")));
				 mCustomWait(2000);
			 driver.findElement(By.xpath("//*[@id='search_gridSectionEntryForm']/div/span")).click();
			 mWaitForVisible("xpath", mGetPropertyFromFile("dropfindadd_id"));
			 Select drpCountrys = new Select(driver.findElement(By.xpath(mGetPropertyFromFile("dropfindadd_id"))));
				drpCountrys.selectByVisibleText("Link Name (English)");
			 driver.findElement(By.id("jqg2")).sendKeys(mGetPropertyFromFile("SectionName_eng"+k).trim());
			 driver.findElement(By.id("fbox_gridSectionEntryForm_search")).click();
			 mWaitForVisible("css", mGetPropertyFromFile("prd_grid_edit_id"));
			 mClick("css", mGetPropertyFromFile("prd_grid_edit_id"));
			 mWaitForVisible("xpath", mGetPropertyFromFile("save&continuedid"));
			 mCustomWait(1000);
			 //Table Grid,Section Grid
			/* String text=driver.findElement(By.id("sectionType0")).getText().trim();
			 System.out.println("============================================>"+text);*/
			 Select drpCountrys1s = new Select(driver.findElement(By.id("sectionType0")));
			 String text1=drpCountrys1s.getFirstSelectedOption().getText();
			 System.out.println("============================================>"+text1);
			 if (text1.equalsIgnoreCase("Table Grid")||text1.equalsIgnoreCase("Section Grid")) {
				 mClick("xpath", mGetPropertyFromFile("save&continuedid"));
					// mWaitForVisible("xpath", mGetPropertyFromFile("save_ids_ch"));
					 element = wait.until(
						        ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Add New Element")));
						 mCustomWait(2000);
						 driver.findElement(By.partialLinkText("Add New Element")).click();
						
						 
					 
						 mWaitForVisible("xpath", mGetPropertyFromFile("addelement_id"));
						 
						
						 
						 mCustomWait(2000);
						 
						 driver.findElement(By.id("subLinkFieldMapping.subLinkFieldlist0.fieldNameEn")).sendKeys("Upload File");
						 driver.findElement(By.id("subLinkFieldMapping.subLinkFieldlist0.fieldNameRg")).sendKeys("अपलोड दस्तावेज़");
						 
						 Select drpCountry1234 = new Select(driver.findElement(By.id(mGetPropertyFromFile("TypeofField_id"))));
							drpCountry1234.selectByVisibleText("Attachment");
						 
						 
						 mClick("xpath", mGetPropertyFromFile("addelement_id"));
						 mCustomWait(2000);
						 mWaitForVisible("xpath", mGetPropertyFromFile("save_ids"));
						 
						 
						 ///second element
						 
						 element = wait.until(
							        ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Add New Element")));
							 mCustomWait(2000);
							 driver.findElement(By.partialLinkText("Add New Element")).click();
						 
						 
							 mWaitForVisible("xpath", mGetPropertyFromFile("addelement_id"));
							 
							 
							 
							 mCustomWait(2000);
							 driver.findElement(By.id("subLinkFieldMapping.subLinkFieldlist0.fieldNameEn")).sendKeys("Upload Image");
							 driver.findElement(By.id("subLinkFieldMapping.subLinkFieldlist0.fieldNameRg")).sendKeys("तस्वीर डालिये");
							 
							 Select drpCountry1234f = new Select(driver.findElement(By.id(mGetPropertyFromFile("TypeofField_id"))));
								drpCountry1234f.selectByVisibleText("Photo");
							 
							 
							 mClick("xpath", mGetPropertyFromFile("addelement_id"));
							 mCustomWait(2000);
							 mWaitForVisible("xpath", mGetPropertyFromFile("save_ids"));
						 				 
						 
						 mClick("xpath", mGetPropertyFromFile("save_ids"));
		                 mWaitForVisible("xpath", mGetPropertyFromFile("btnNo_ids1"));
						 
						 mClick("xpath", mGetPropertyFromFile("btnNo_ids1"));
					 
					 
					 
					
						 
						 //cheker
						 
						 mWaitForVisible("id", mGetPropertyFromFile("Product_menuid"));
							mClick("id", mGetPropertyFromFile("Product_menuid"));
							 element = wait.until(
							        ExpectedConditions.visibilityOfElementLocated(By.linkText("Approve Links")));

							driver.findElement(By.linkText("Approve Links")).click();
						
							 element = wait.until(
							        ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='search_gridSectionEntryForm']/div/span")));
							 mCustomWait(2000);
						 driver.findElement(By.xpath("//*[@id='search_gridSectionEntryForm']/div/span")).click();
						 mWaitForVisible("xpath", mGetPropertyFromFile("dropfindadd_id"));
						 Select drpCountrys1 = new Select(driver.findElement(By.xpath(mGetPropertyFromFile("dropfindadd_id"))));
							drpCountrys1.selectByVisibleText("Link Name (English)");
						 driver.findElement(By.id("jqg2")).sendKeys(mGetPropertyFromFile("SectionName_eng"+k).trim());
						 driver.findElement(By.id("fbox_gridSectionEntryForm_search")).click();
						 mWaitForVisible("css", mGetPropertyFromFile("prd_grid_edit_id"));
						 mClick("css", mGetPropertyFromFile("prd_grid_edit_id"));
						 mWaitForVisible("xpath", mGetPropertyFromFile("save&continuedid"));
					 	 
						 element = wait.until(
							        ExpectedConditions.visibilityOfElementLocated(By.id("entity.chekkerflag1")));
						 mCustomWait(2000);
						 driver.findElement(By.id("entity.chekkerflag1")).click();
						 mClick("xpath", mGetPropertyFromFile("save&continuedid"));
						 mWaitForVisible("xpath", mGetPropertyFromFile("save_ids_ch1"));
						 
						 mClick("xpath", mGetPropertyFromFile("save_ids_ch1"));
						 mWaitForVisible("xpath", mGetPropertyFromFile("btnNo_ids1"));
										 
										 mClick("xpath", mGetPropertyFromFile("btnNo_ids1"));
			}else {
				 element = wait.until(
					        ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Back")));
				driver.findElement(By.linkText("Back")).click();
			}
			 
				
		
		}
		catch(Exception e){

			e.printStackTrace();
			throw new MainetCustomExceptions("Error in quick_link_master script");
		}
	}


	
	
}