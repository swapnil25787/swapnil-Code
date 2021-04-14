package common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import clubbedapi.ClubbedAPI;

import com.abm.mainet.adh.AdvertisingAndHoardingServices;
import com.abm.mainet.mkt.MarketLicenseServices;
import com.abm.mainet.rnl.RentAndLeaseServices;
import com.abm.mainet.tp.TownPlanningServices;

import errorhandling.MainetCustomExceptions;
import excelreader.ExcelWriting;
public class ScrutinyProcess  extends CommonMethods {



	String strippedSaveString;
	String LOINum;
	public static String ServiceName;
	public static String abc;


	//Srutiny generic method variable declarations
	//////////////////////////////////////////////
	public static boolean startcollecting=false;
	public static boolean stopcollecting=false;

	String srNo;
	String scrutinyQuestion;

	String scrutinyAnswer;
	String view="";
	ArrayList<String> scrutinyIDs;
	public static HashMap<String, ArrayList<String>> scrutinyhashmap = new LinkedHashMap<String, ArrayList<String>>();
	/*public static String textid="";
	public static String imageid="";*/

	ClubbedAPI ClubbedUtils = new ClubbedAPI();
	ExcelWriting EW =new ExcelWriting();



	//Scrutiny method generic
	//Sunil D Sonmale 18-10-2016
	public void ScrutinyProcess()
	{
		try
		{

			mCreateArtefactsFolder("TP_");
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("ScrutinyUsername_Level_"+scrutinylevelcounter),mGetPropertyFromFile("ScrutinyPassword_Level_"+scrutinylevelcounter));
			scrutinyProcess();
			logOut();
			finalLogOut();
			mCloseBrowser();				

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in ScrutinyProcess method");
		}
	}





	public void searchAppnForScrutinyProcess()
	{
		try
		{

			mNavigation("Sc_cfcLinkid","Sc_transactionsLinkid","Sc_scrutinyProcessLinkid");

			mCustomWait(1000);

			mWaitForVisible("name", mGetPropertyFromFile("Sc_scrutinyProcessAppnNoid"));

			IndOrDep("name","Sc_scrutinyProcessAppnNoid","applicationNo");

			mTakeScreenShot();
			mCustomWait(1000);

			mClick("xpath", mGetPropertyFromFile("Sc_scrutinyProcessSearchBtnid"));

			mClick("xpath", mGetPropertyFromFile("Sc_scrutinyProcessSearchBtnid"));
			mCustomWait(500);

			abc=driver.findElement(By.xpath("//*[@id=\"0\"]/td[7]")).getText();
			Scrutiny_Servicename.add(abc);
			System.out.println("name of service is : "+abc); 
			
			//viesandScrutiny();
			



			mWaitForVisible("css", mGetPropertyFromFile("Sc_scrutinyProcessViewScrutinyid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("Sc_scrutinyProcessViewScrutinyid"));


		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void scrutinyProcess()
	{
		try
		{
			searchAppnForScrutinyProcess();

			mCustomWait(2000);
			//#content > div > div:nth-child(4) > div:nth-child(1) > div.col-9.padding_5 > span

			//getting service name
		mWaitForVisible("css", mGetPropertyFromFile("Sc_scrutinyProcessServiceNameid"));         
			mCustomWait(1000);
		ServiceName = mGetText("css", mGetPropertyFromFile("Sc_scrutinyProcessServiceNameid"));  
			System.out.println("Service Name is : "+ServiceName);
			mCustomWait(1000);
			Scrutiny();
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	//sent to printer grid
	public void sentToPrinterGrid()
	{
		try
		{
			//sending application number
			mWaitForVisible("name", mGetPropertyFromFile("Sc_scrutinyProcessAppnNoid"));
			mCustomWait(1000);

			//			mSendKeys("css", mGetPropertyFromFile("Sc_scrutinyProcessAppnNoid"), "71116092900041");

			IndOrDep("name","Sc_scrutinyProcessAppnNoid","applicationNo");

			//Search Button
			mWaitForVisible("xpath", mGetPropertyFromFile("Sc_scrutinyProcessSearchBtnid"));
			mCustomWait(1000);
			mTakeScreenShot();
			mClick("xpath", mGetPropertyFromFile("Sc_scrutinyProcessSearchBtnid"));

			//send to image 
			mWaitForVisible("xpath", mGetPropertyFromFile("Sc_scrutinyProcessSentToImgid"));
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("Sc_scrutinyProcessSentToImgid"));

			//Pop up
			mWaitForVisible("name", mGetPropertyFromFile("Sc_scrutinyProcessSendToPrinterImgid"));
			mCustomWait(1000);
			String MsgAtSendinAppToPrinter = mGetText("css", mGetPropertyFromFile("Sc_SentToPrinterPopAssertMsgid"));
			mCustomWait(1000);
			mAssert(MsgAtSendinAppToPrinter, mGetPropertyFromFile("Sc_SentToPrinterPopAssertMsgData"), "Actual msg::::"+MsgAtSendinAppToPrinter+" Expected::::"+mGetPropertyFromFile("Sc_SentToPrinterPopAssertMsgData"));
			System.out.println(MsgAtSendinAppToPrinter);
			mCustomWait(1000);
			mTakeScreenShot();
			mClick("name", mGetPropertyFromFile("Sc_scrutinyProcessSendToPrinterImgid"));
			mCustomWait(1000);			
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}




	public void Scrutiny()
	{
		try
		{

			log.info("Clearing hashmap"+scrutinyhashmap);
			scrutinyhashmap.clear();
			log.info("Hashmap cleared"+scrutinyhashmap);

			WebElement table_element = driver.findElement(By.className("gridtable"));

			List<WebElement> tr_collection=table_element.findElements(By.tagName("tr"));

			System.out.println("NUMBER OF ROWS IN THIS TABLE = "+tr_collection.size());
			int row_num,col_num;
			row_num=1;

			startcollecting=false;
			stopcollecting=false;
			for(WebElement trElement : tr_collection)
			{

				String capturedexception ="";
				List<WebElement> td_collection=trElement.findElements(By.tagName("td"));
				//System.out.println("NUMBER OF COLUMNS="+td_collection.size());
				col_num=1;
				for(WebElement tdElement : td_collection)
				{
					// System.out.println("row # "+row_num+", col # "+col_num+ "text="+tdElement.getText());

					if(col_num==1)
					{
						srNo=trElement.findElement(By.tagName("td")).getText();
						System.err.println("Sr. No. is :"+srNo);

					}

					if(col_num==2)
					{
						scrutinyQuestion =tdElement.getText();
						System.err.println("scrutiny Question is :"+scrutinyQuestion);

					}
					if(col_num==3)
					{
						//trElement.findElement(By.xpath("td/input"));

						if(startcollecting==false)
						{
							try
							{
								if(trElement.findElement(By.xpath("td/input")).isDisplayed())
								{	
									startcollecting=true;
									scrutinyAnswer=trElement.findElement(By.xpath("td/input")).getAttribute("id");
									System.out.println("scrutinyAnswer"+scrutinyAnswer);
								}

							}
							catch(Exception e){
								capturedexception =e.toString();
							}

							if(capturedexception.contains("no such element: Unable to locate element: {\"method\":\"xpath\",\"selector\":\"td/input\"}"))
							{
								break;
							}
						}
						else if(startcollecting==true)							
						{
							try{
								if(trElement.findElement(By.xpath("td/input")).isDisplayed())
								{									
									scrutinyAnswer=trElement.findElement(By.xpath("td/input")).getAttribute("id");
									System.out.println("scrutinyAnswer"+scrutinyAnswer);
								}

							}
							catch(Exception e){
								capturedexception =e.toString();
							}

							if(capturedexception.contains("no such element: Unable to locate element: {\"method\":\"xpath\",\"selector\":\"td/input\"}"))
							{
								stopcollecting=true;
								System.out.println("stopcollecting"+stopcollecting);
								break;
							}
						}


					}

					if(col_num==4)
					{
						if(startcollecting)
						{
							try{
								if(trElement.findElement(By.xpath("td/div/a")).isDisplayed())
								{									
									view=trElement.findElement(By.xpath("td/div/a")).getAttribute("id");
									System.out.println("view::"+view);
								}

							}
							catch(Exception e){
								capturedexception =e.toString();
							}

							if(capturedexception.contains("no such element: Unable to locate element: {\"method\":\"xpath\",\"selector\":\"td/div/a\"}"))
							{
								break;
							}
						}

						else if(stopcollecting)
						{
							break;
						}
					}

					if(stopcollecting)
					{
						break;
					}
					col_num++;

				}
				if(stopcollecting)
				{
					break;
				}

				if(startcollecting==true&&stopcollecting==false)
				{
					String key = ServiceName+"_"+scrutinyQuestion;


					scrutinyIDs = new ArrayList<>();

					scrutinyIDs.add(scrutinyAnswer);

					scrutinyIDs.add(view);

					scrutinyhashmap.put(key, scrutinyIDs);
				}

				row_num++;
			}


			System.out.println("Printing scrutiny hashmap");
			System.out.println(scrutinyhashmap);

			for (Entry<String, ArrayList<String>> entry : scrutinyhashmap.entrySet()) {
				String key = entry.getKey();
				System.err.println("Key is::::"+key);
				Object value = entry.getValue();
				System.err.println("Value is:::"+value);

				textid=scrutinyhashmap.get(key).get(0).toString().trim();
				imageid="#"+scrutinyhashmap.get(key).get(1).toString().trim()+" > img";
				System.out.println("Text id is:::::::"+textid);
				System.out.println("Image id is::::::"+imageid);
				// ...

				ScrutinySwitchQuestions(key);

			}



			log.info("the value of textid is : "+textid); 
			log.info("the value of imageid is : "+imageid); 

			textid="";			
			imageid="";


			log.info("the value of textid is : "+textid); 
			log.info("the value of imageid is : "+imageid); 


			UploadDocs();

			//Scrutiny Submit Button
			mWaitForVisible("css", mGetPropertyFromFile("Sc_FinalSubmitButtonid"));    
			mCustomWait(1000);
			mClick("css", mGetPropertyFromFile("Sc_FinalSubmitButtonid"));

			//getting PopUp msg for assertion
			mWaitForVisible("id", mGetPropertyFromFile("Sc_ProceedButtonid"));
			mCustomWait(1000);
			if(ServiceName.equalsIgnoreCase("Application for Development Plan")||ServiceName.equalsIgnoreCase("Occupancy Certificate")||ServiceName.equalsIgnoreCase("Application for Building Permit"))
			{
				TownPlanningServices tp1= new TownPlanningServices(); 
				tp1.scrutinyPopUpmsg();
			}
			else{
				String scrutinyPopUpMsg = mGetText("css", mGetPropertyFromFile("rnl_LOIGenAppMsgid"));
				mCustomWait(1000);
				scrutinyPopUpMsg=scrutinyPopUpMsg.replaceAll("[^0-9]", "");
				Scrutiny_Msg_LOINoList.add(scrutinyPopUpMsg);
				System.out.println("Final Scrutiny LOI Generation Message LOI No is =="+Scrutiny_Msg_LOINoList);
				//mAssert(scrutinyPopUpMsg, mGetPropertyFromFile("rnl_LOIGenafterLOIGenMsgdata"), "Actual msg::::"+scrutinyPopUpMsg+" Expected::::"+mGetPropertyFromFile("rnl_LOIGenafterLOIGenMsgdata"));
				mAssert(scrutinyPopUpMsg, mGetPropertyFromFile("mkt_Scrutiny_LOINo_GenMsgdata"), "Actual msg::::"+Scrutiny_Msg_LOINoList+" Expected::::"+mGetPropertyFromFile("mkt_Scrutiny_LOINo_GenMsgdata"));

				System.out.println(scrutinyPopUpMsg);
			}
			mTakeScreenShot();
			mCustomWait(1000);

			mClick("id", mGetPropertyFromFile("Sc_ProceedButtonid"));
			mCustomWait(1000);

			////////	///////////////applicable for tp& rnl/////////////////////
			if(ServiceName.equalsIgnoreCase("Application for Development Plan")||ServiceName.equalsIgnoreCase("Occupancy Certificate")||ServiceName.equalsIgnoreCase("Application for Building Permit"))
			{
				TP_Scrutiny_LetterDownload();
			}
			else
			{
				System.out.println("No Letter Download becauser name of service is:::"+ServiceName);
			}

			if(scrutinylevelcounter<Integer.parseInt(mGetPropertyFromFile("noOfScrutinyLevels")))
			{
				twlsSendNextLevel();
			}
			else if(scrutinylevelcounter==Integer.parseInt(mGetPropertyFromFile("noOfScrutinyLevels")))
			{
				sentToPrinterGrid();
			}
			else{
				System.out.println("");
			}
		} 

		catch(Exception e)
		{
			e.printStackTrace();

		}
	}



	public void TrutiPatraVerificationp()
	{
		try
		{
			Pattern appleNum = Pattern.compile("(?<=application no. )\\s*(.+?)\\s*(?=dated)");
			//	(?<=Approval No: )\\s*(.+?)\\s*(?= Approval Date:)
			java.util.regex.Matcher matcher = appleNum.matcher(pdfoutput);

			if (matcher.find()) {
				mCustomWait(2000);
				String appNum = matcher.group(1);
				System.out.println();
				System.out.println();
				System.out.println("Application Number is:"+appNum);
				mCustomWait(2000);
				mAssert(appNum, appNo, "Actual  :"+appNum+"  Expected   :"+appNo+"at truti patra verification");
				mCustomWait(2000);

			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}





	/////////////////////////////'
	//Scrutiny switch case method
	/////////////////////////////

	public void ScrutinySwitchQuestions(String key)
	{
		try
		{
			AdvertisingAndHoardingServices adh=new AdvertisingAndHoardingServices();
			TownPlanningServices tp1=new TownPlanningServices();
			RentAndLeaseServices rnl=new RentAndLeaseServices();
			MarketLicenseServices NTL = new MarketLicenseServices();
			switch(key) 
			{
			/////////////////////////////////piush rnl & tp question//////////////////////////
			////Uder this method//////////////////////////////////////////////////////////////////////////////////////////////////////
			///Town Planning Questions public void ScrutinySwitchQuestions(String key)
			///////////////////////////////////////////////////////////////////////////////////////////////////////



			case "Application for Development Plan_Name of Junior Engineer visited the Site":

				Scrutiny_EnterText(mGetPropertyFromFile("tp_SiteInspectJrengineerVisitdata")); 
				break;

			case "Application for Development Plan_Date of the Junior Engineer visited the Site":

				Scrutiny_EnterDate(mGetPropertyFromFile("tp_SiteInspectVisitDatedata")); 

				break;

			case "Application for Development Plan_Do you want to generate Site Inspection Letter ?":

				tp1.tp_Gen_SiteInsp_Letter();
				break;

			case "Application for Development Plan_Detail Remark":

				Scrutiny_EnterText(mGetPropertyFromFile("Sc_Remarkdata")); 
				break;

			case "Application for Development Plan_Assistant Engineer Name to check Layout as per Building ByeLaws - 2014":

				Scrutiny_EnterText(mGetPropertyFromFile("tp_TrutiPatraGenAsstEngineerNamedata")); 
				break;					

			case "Application for Development Plan_Do you want to GenerateTruti Patra \"YES\"/\"NO\"":

				Scrutiny_EnterText(mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraYNdata")); 
				SCR_TpAssitantEnggName.add(mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraYNdata"));
				break;

			case "Application for Development Plan_If Yes then Type \"YES\" to generated Truti Patra":

				if(mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraYNdata").equalsIgnoreCase("yes"))
				{
					tp1.tp_Gen_TrutiPatra();
				}
				break;

			case "Application for Development Plan_If No then Type \"NO\" to generated Truti Patra":

				if(mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraYNdata").equalsIgnoreCase("no") && mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraNodata").equalsIgnoreCase("no"))
				{
					Scrutiny_EnterText(mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraNodata"));
				}
				break;

			case "Application for Development Plan_Submitted information as per Truti Patra \"YES\"/\"NO\"":

				Scrutiny_EnterText(mGetPropertyFromFile("tp_LOIGenTrutiPatraVerifictndata")); 
				break;

			case "Application for Development Plan_If Yes then type \"YES\"":

				mscroll(0,300); 
				if(mGetPropertyFromFile("tp_LOIGenTrutiPatraVerifictndata").equalsIgnoreCase("yes"))
				{
					tp1.tp_InfoasPer_TrutiPatra();
				}
				break;

			case "Application for Development Plan_If No then type \"NO\"":

				if(mGetPropertyFromFile("tp_LOIGenTrutiPatraVerifictndata").equalsIgnoreCase("no"))
				{			
					mSendKeys("id", textid, mGetPropertyFromFile("tp_LOIGenTrutiPatraNotVerifddata"));
				}
				break;

			case "Application for Development Plan_Do you want to Edit Service Information then type \"YES\"":

				Scrutiny_EnterText(mGetPropertyFromFile("tp_LOIGenEditServicedata")); 
				break;

			case "Application for Development Plan_If Edit Service Information is \"YES\" then Type \"YES\" to Edit Service Information":
				if(mGetPropertyFromFile("tp_LOIGenEditServicedata").equalsIgnoreCase("yes"))
				{		
					tp1.tp_EditServiceInfo();
				}
				break;

			case "Application for Development Plan_Final Decision \"YES\"/\"NO\"":

				Scrutiny_EnterText(mGetPropertyFromFile("tp_LOIGenFinalDecisiondata")); 
				break;

			case "Application for Development Plan_If Decision is \"YES\" then Type \"YES\" to generate LOI":
				tp1.tp_Gen_LOILetter();
				break;

			case "Application for Development Plan_If Decision is \"NO\" then Type \"NO\" to generate Rejection Letter":
				tp1.tp_Gen_RejectionLetter();
				break;	

				/////// Building Permission Scrutiny Question set : Piyush 19/10/2016

			case "Application for Building Permit_Name of Junior Engineer visited the Site":
				Scrutiny_EnterText(mGetPropertyFromFile("tp_SiteInspectJrengineerVisitdata")); 
				break;

			case "Application for Building Permit_Date of the Junior Engineer visited the Site":
				Scrutiny_EnterDate(mGetPropertyFromFile("tp_SiteInspectVisitDatedata"));
				break;

			case "Application for Building Permit_Do you want to generate Site Inspection Letter ?":
				tp1.tp_Gen_SiteInsp_Letter();
				break;

			case "Application for Building Permit_Detail Remark":
				Scrutiny_EnterText(mGetPropertyFromFile("Sc_Remarkdata"));
				break;

			case "Application for Building Permit_Assistant Engineer Name to check Layout as per Building ByeLaws - 2014":
				Scrutiny_EnterText(mGetPropertyFromFile("tp_TrutiPatraGenAsstEngineerNamedata"));
				break;	

			case "Application for Building Permit_Do you want to GenerateTruti Patra \"YES\"/\"NO\"":
				Scrutiny_EnterText(mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraYNdata"));
				break;

			case "Application for Building Permit_If Yes then Type \"YES\" to generated Truti Patra":
				if(mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraYNdata").equalsIgnoreCase("yes"))
				{
					tp1.tp_Gen_TrutiPatra();
				}
				break;

			case "Application for Building Permit_If No then Type \"NO\" to generated Truti Patra":
				if(mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraYNdata").equalsIgnoreCase("no") && mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraNodata").equalsIgnoreCase("no"))
				{
					Scrutiny_EnterText(mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraNodata"));
				}
				break;

			case "Application for Building Permit_Submitted information as per Truti Patra \"YES\"/\"NO\"":
				Scrutiny_EnterText(mGetPropertyFromFile("tp_LOIGenTrutiPatraVerifictndata"));
				break;

			case "Application for Building Permit_If Yes then type \"YES\"":
				tp1.tp_InfoasPer_TrutiPatra();
				break;

			case "Application for Building Permit_If No then type \"NO\"":

				if(mGetPropertyFromFile("tp_LOIGenTrutiPatraVerifictndata").equalsIgnoreCase("no"))
				{			
					mSendKeys("id", textid, mGetPropertyFromFile("tp_LOIGenTrutiPatraNotVerifddata"));
				}
				break;

			case "Application for Building Permit_Do you want to Edit Service Information then type \"YES\"":
				Scrutiny_EnterText(mGetPropertyFromFile("tp_LOIGenEditServicedata")); 
				break;

				//Changes done 30 jan 2017
			case "Application for Building Permit_If Edit Service Information is \"YES\" then Type \"YES\" to Edit Service Information":

				//mSendKeys("id", textid, mGetPropertyFromFile("tp_LOIGenEditServiceYesdata"));

				if(mGetPropertyFromFile("tp_LOIGenEditServicedata").equalsIgnoreCase("yes"))
				{		
					if(ServiceName.equalsIgnoreCase("Application for Development Plan"))
					{	
						tp1.tp_EditServiceInfo();
					}
					else
					{
						mSendKeys("id", textid, mGetPropertyFromFile("tp_LOIGenEditServiceYesdata"));
						mCustomWait(1200); 
						mClick("css", imageid);	
						mCustomWait(1000);
						mTakeScreenShot();
						mCustomWait(1000);
						mscroll(0, 400);
						mCustomWait(1000);
						mTakeScreenShot();
						mClick("xpath", mGetPropertyFromFile("tp_LOIGenEditServiceBPSubmitBtnid"));
						mCustomWait(2000);
					}
				}
				break;

			case "Application for Building Permit_Final Decision \"YES\"/\"NO\"":
				Scrutiny_EnterText(mGetPropertyFromFile("tp_LOIGenFinalDecisiondata")); 
				break;

			case "Application for Building Permit_If Decision is \"YES\" then Type \"YES\" to generate LOI":
				tp1.tp_Gen_LOILetter();
				break;

			case "Application for Building Permit_If Decision is \"NO\" then Type \"NO\" to generate Rejection Letter":
				tp1.tp_Gen_RejectionLetter();
				break;	

				////// Occupancy Scrutiny Question set///	

			case "Occupancy Certificate_Name of Junior Engineer visited the Site":
				Scrutiny_EnterText(mGetPropertyFromFile("tp_SiteInspectJrengineerVisitdata")); 
				break;

			case "Occupancy Certificate_Date of the Junior Engineer visited the Site":
				Scrutiny_EnterDate(mGetPropertyFromFile("tp_SiteInspectVisitDatedata"));
				break;

			case "Occupancy Certificate_Do you want to generate Site Inspection Letter ?":
				tp1.tp_Gen_SiteInsp_Letter();
				break;

			case "Occupancy Certificate_Detail Remark":
				Scrutiny_EnterText(mGetPropertyFromFile("Sc_Remarkdata"));
				break;

			case "Occupancy Certificate_Assistant Engineer Name to check Layout as per Building ByeLaws - 2014":
				Scrutiny_EnterText(mGetPropertyFromFile("tp_TrutiPatraGenAsstEngineerNamedata"));
				break;	

			case "Occupancy Certificate_Do you want to GenerateTruti Patra \"YES\"/\"NO\"":
				Scrutiny_EnterText(mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraYNdata"));
				break;

			case "Occupancy Certificate_If Yes then Type \"YES\" to generated Truti Patra":
				if(mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraYNdata").equalsIgnoreCase("yes"))
				{
					tp1.tp_Gen_TrutiPatra();
				}
				break;

			case "Occupancy Certificate_If No then Type \"NO\" to generated Truti Patra":
				if(mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraYNdata").equalsIgnoreCase("no") && mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraNodata").equalsIgnoreCase("no"))
				{
					Scrutiny_EnterText(mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraNodata"));
				}
				break;

			case "Occupancy Certificate_Submitted information as per Truti Patra \"YES\"/\"NO\"":
				Scrutiny_EnterText(mGetPropertyFromFile("tp_LOIGenTrutiPatraVerifictndata"));
				break;

			case "Occupancy Certificate_If Yes then type \"YES\"":
				tp1.tp_InfoasPer_TrutiPatra();
				break;

			case "Occupancy Certificate_If No then type \"NO\"":

				if(mGetPropertyFromFile("tp_LOIGenTrutiPatraVerifictndata").equalsIgnoreCase("no"))
				{			
					mSendKeys("id", textid, mGetPropertyFromFile("tp_LOIGenTrutiPatraNotVerifddata"));
				}
				break;

			case "Occupancy Certificate_Do you want to Edit Service Information then type \"YES\"":
				Scrutiny_EnterText(mGetPropertyFromFile("tp_LOIGenEditServicedata")); 
				break;
 

			case "Occupancy Certificate_If Edit Service Information is \"YES\" then Type \"YES\" to Edit Service Information":
				if(mGetPropertyFromFile("tp_LOIGenEditServicedata").equalsIgnoreCase("yes"))
				{		
					tp1.tp_EditServiceInfo();
				}
				break;

			case "Occupancy Certificate_Final Decision \"YES\"/\"NO\"":
				Scrutiny_EnterText(mGetPropertyFromFile("tp_LOIGenFinalDecisiondata")); 
				break;

			case "Occupancy Certificate_If Decision is \"YES\" then Type \"YES\" to generate LOI":
				tp1.tp_Gen_LOILetter();
				break;

			case "Occupancy Certificate_If Decision is \"NO\" then Type \"NO\" to generate Rejection Letter":
				tp1.tp_Gen_RejectionLetter();
				break;	

				// RNL Module scrutiny question

			case "Booking of Estate_Date of Inspection":

				Scrutiny_EnterDate(mGetPropertyFromFile("rnl_Inspectiondatedata"));
				SCR_SiteInspectiondate.add(mGetPropertyFromFile("rnl_InspectionDoneBydata"));
				break;

			case "Booking of Estate_Inspection done by":

				Scrutiny_EnterText(mGetPropertyFromFile("rnl_InspectionDoneBydata"));
				SCR_SiteInspectorName.add(mGetPropertyFromFile("rnl_InspectionDoneBydata"));
				break;

			case "Booking of Estate_Remarks":

				Scrutiny_EnterText(mGetPropertyFromFile("Sc_Remarkdata"));
				SCR_SiteInspectorRemarks.add(mGetPropertyFromFile("Sc_Remarkdata"));
				break;

				
			case "Booking of Estate_Final Decision \"YES\"/\"NO\"":

System.out.println("==================================================");
				Scrutiny_EnterText(mGetPropertyFromFile("rnl_LOIGenFinalDecisiondata")); 
				break;

			case "Booking of Estate_If Decision is \"YES\" then Type \"YES\" to generate LOI":

				if(mGetPropertyFromFile("rnl_LOIGenFinalDecisiondata").equalsIgnoreCase("yes"))
				{						

					rnl.rnl_Gen_LOILetter();
				}
				break;					

			case "Booking of Estate_If Decision is \"NO\" then Type \"NO\" to generate Rejection Letter":

				if(mGetPropertyFromFile("rnl_LOIGenFinalDecisiondata").equalsIgnoreCase("no"))
				{	

					rnl.rnl_Gen_RejectionLetter();
				}
				break;


			case "Rent Renewal Contract_Date of Inspection":

				Scrutiny_EnterDate(mGetPropertyFromFile("rnl_Inspectiondatedata")); 
				SCR_SiteInspectiondate.add(mGetPropertyFromFile("rnl_Inspectiondatedata"));
				break;

			case "Rent Renewal Contract_Inspection done by":

				Scrutiny_EnterText(mGetPropertyFromFile("rnl_InspectionDoneBydata"));	
				SCR_SiteInspectorName.add(mGetPropertyFromFile("rnl_InspectionDoneBydata"));
				break;

			case "Rent Renewal Contract_Remarks":

				Scrutiny_EnterText(mGetPropertyFromFile("Sc_Remarkdata"));
				SCR_SiteInspectorRemarks.add(mGetPropertyFromFile("Sc_Remarkdata"));
				break;

			case "Rent Renewal Contract_View and modify Application details":

				Scrutiny_EnterText(mGetPropertyFromFile("rnl_View_ModifyApplicationdata")); 
				if(mGetPropertyFromFile("rnl_View_ModifyApplicationdata").equalsIgnoreCase("yes"))
				{

					rnl.rnl_View_ModifyApp();

				}
				break;

			case "Rent Renewal Contract_Final Decision \"YES\"/\"NO\"":

				Scrutiny_EnterText(mGetPropertyFromFile("rnl_LOIGenFinalDecisiondata")); 
				break;

			case "Rent Renewal Contract_If Decision is \"YES\" then Type \"YES\" to generate LOI":

				if(mGetPropertyFromFile("rnl_LOIGenFinalDecisiondata").equalsIgnoreCase("yes"))
				{	

					rnl.rnl_Gen_LOILetter();
				}
				break;					

			case "Rent Renewal Contract_If Decision is \"NO\" then Type \"NO\" to generate Rejection Letter":

				if(mGetPropertyFromFile("rnl_LOIGenFinalDecisiondata").equalsIgnoreCase("no"))
				{	

					rnl.rnl_Gen_RejectionLetter();
				}
				break;
				/////////////////////////////////end of questions TP & RNL//////////////////
				//////////////Market license case statements - Questions by Jyoti 24-10-2016

			case "New Trade License_Date of Inspection":


				Scrutiny_EnterDate(mGetPropertyFromFile("mkt_InspectiondDatedata"));

				break;

			case "New Trade License_Inspection done by":

				Scrutiny_EnterText(mGetPropertyFromFile("mkt_InspectorNamedata")); 
				break;

			case "New Trade License_Remarks":

				Scrutiny_EnterText(mGetPropertyFromFile("mkt_InspectionRemarksdata")); 
				break;

				// 2nd Level Scrutiny Questions of 'Executive Officer'

			case "New Trade License_View and modify Application details":

				//MarketLicenseServices NTL_ViewandModify = new MarketLicenseServices();
				NTL.ViewandModify_Application();



				break;

			case "New Trade License_IF Final Decision is YES then type Yes and generate LOIFinal Decision \"YES\"/\"NO\"":

			//	MarketLicenseServices NTL_Final_Decision = new MarketLicenseServices();
				NTL.Final_Decision();


				break;

			case "New Trade License_If Decision is \"YES\" then Type \"YES\" to generate LOI":

				if(mGetPropertyFromFile("mkt_FinalDecisiondata").equalsIgnoreCase("Yes"))
				{
					//MarketLicenseServices NTL_FinalDecision = new MarketLicenseServices();
					NTL.Generate_LOI();

				}

				//Generate_LOI(); 

				break;


			case "New Trade License_If Decision is \"NO\" then Type \"NO\" to generate Rejection Letter":

				//MarketLicenseServices NTL_Reject_Scrutiny = new MarketLicenseServices();
				NTL.Reject_Scrutiny();

				break;


				// Change in Name of Business

			case "Change in Name of Business_Whether original copy of the Market License is of the same number as mentioned in application?":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_ChngBusiName_originalcopydata")); 
				break;

			case "Change in Name of Business_Remarks of the Market Inspector":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_ChngBusiName_Remarksdata")); 
				break;

			case "Change in Name of Business_Decision (Whether the applicant is allowed for change in name of business? )":

				MarketLicenseServices ChBusiNm_Decision = new MarketLicenseServices();
				ChBusiNm_Decision.ChngBusiName_Decision();


				break;

			case "Change in Name of Business_View Application Form":

				MarketLicenseServices ChBusiNm_ViewAppForm = new MarketLicenseServices();
				ChBusiNm_ViewAppForm.ChngBusiName_ViewAppForm(); 

				break;

			case "Change in Name of Business_IF Final Decision is YES then type Yes and generate LOI":

				MarketLicenseServices ChBusiName_GenerateLOI = new MarketLicenseServices();
				ChBusiName_GenerateLOI.ChngBusiName_GenerateLOI(); 

				break;

			case "Change in Name of Business_IF Final Decision is NO then type No and Generate Rejection":

				MarketLicenseServices ChBusiNm_Reject_Scrutiny = new MarketLicenseServices();
				ChBusiNm_Reject_Scrutiny.ChngBusiName_Reject_Scrutiny(); 

				break;

			case "Transfer of License Other Mode_Date of the Inspection":

				Scrutiny_EnterDate(mGetPropertyFromFile("mkt_InspectiondDatedata"));

				break;

			case "Transfer of License Other Mode_Name of the Market Inspector":

				Scrutiny_EnterText(mGetPropertyFromFile("mkt_issuerenewal_data")); 

				break;

			case "Transfer of License Other Mode_Whether the receipt of the paid property tax for the year is enclosed or not?":

				Scrutiny_EnterText(mGetPropertyFromFile("mkt_issuerenewal_data")); 

				break;

			case "Transfer of License Other Mode_Is there any arrears of Water Bill?":

				Scrutiny_EnterText(mGetPropertyFromFile("mkt_issuerenewal_data")); 

				break;
			case "Transfer of License Other Mode_Remarks of the Market Inspector":

				Scrutiny_EnterText(mGetPropertyFromFile("mkt_issuerenewal_data")); 

				break;
			case "Transfer of License Other Mode_Whether the license fees to be paid is correct or not?":

				Scrutiny_EnterText(mGetPropertyFromFile("mkt_issuerenewal_data")); 

				break;
			case "Transfer of License Other Mode_Whether the renewal fees to be paid & received is accepted or not ?":

				Scrutiny_EnterText(mGetPropertyFromFile("mkt_issuerenewal_data")); 

				break;
			case "Transfer of License Other Mode_Whether the concerned license number is renewed for the year or not?":

				Scrutiny_EnterText(mGetPropertyFromFile("mkt_issuerenewal_data")); 

				break;
			case "Transfer of License Other Mode_Whether the professional certificate enclosed or not (if applicable)":

				Scrutiny_EnterText(mGetPropertyFromFile("mkt_issuerenewal_data")); 

				break;
			case "Transfer of License Other Mode_Chief Market Inspector's suggestions and remarks":

				Scrutiny_EnterText(mGetPropertyFromFile("mkt_issuerenewal_data")); 

				break;
			case "Transfer of License Other Mode_Do you agree with the remarks of Market Inspector?":

				Scrutiny_EnterText(mGetPropertyFromFile("mkt_issuerenewal_data")); 

				break;
			case "Transfer of License Other Mode_Decision (whether the license should be transfered ? )":

				MarketLicenseServices TOM_Decision = new MarketLicenseServices();
				TOM_Decision.transfr_Decision(); 


				break;

			case "Transfer of License Other Mode_If final decision is \"Yes\" then type Yes and generate LOI":
				MarketLicenseServices TOM_GenerateLOI = new MarketLicenseServices();
				TOM_GenerateLOI.transfr_GenerateLOI(); 


				break;

			case "Transfer of License Other Mode_If final decision is \"No\" then type No and give reasons":
				MarketLicenseServices TOM_Reject_Scrutiny = new MarketLicenseServices();
				TOM_Reject_Scrutiny.transfr_Reject_Scrutiny(); 

				break;
				// Questioanry for Market Scrutiny END by Jyoti 19-10-2016

				//? //////////////Market Renewal scrutiny case statesments by swapnil 10-11-2016

			case "Renewal of Trade License_Do you want to issue Renewal Certificate":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_issuerenewal_data")); 
				break;
				//2nd level renewal

			case "Renewal of Trade License_Decision( Whether the identification details of license holder correct?)":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_Renewal_Decision_data")); 
				break;

			case "Renewal of Trade License_if identification details of license holder correct- generate loi":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_Renewal_LOI_data"));
				if(mGetPropertyFromFile("mkt_Renewal_LOI_data").equalsIgnoreCase("YES"))
				{
					mClick("css", imageid);

					MarketLicenseServices Renew_GenerateLOI = new MarketLicenseServices();
					Renew_GenerateLOI.Renewal_Market_LOI_Generation(); 

				}else {
					System.out.println("LOI is not applicable");
				}
				break;
			case"Renewal of Trade License_if identification details of license holder not correct- generate rejection":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_Renewal_Rejection_data"));
				if(mGetPropertyFromFile("mkt_Renewal_Rejection_data").equalsIgnoreCase("No"))
				{
					mClick("css", imageid);

					MarketLicenseServices Renew_Rejection = new MarketLicenseServices();
					Renew_Rejection.Renewal_rejection_letter(); 

				}else {
					System.out.println("Rejection letter is not applicable");
				}
				break;

				///////////
				///////cancellation first level scrutiny
				///////////////////

			case "Cancellation of Trade License_Date of Inspection":

				Scrutiny_EnterDate(mGetPropertyFromFile("mkt_cancel_InspectiondDatedata"));

				break;

			case "Cancellation of Trade License_Inspection done by": 
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_cancellation_name_data"));
				break;

			case "Cancellation of Trade License_Whether the license holder paid license fees till date or not ?":	
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_cancellation_feepaid_data"));
				break;


			case "Cancellation of Trade License_Remarks for cancellation of license":	
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_cancellation_remark_data"));
				break;

				//second level scrutiny 
			case "Cancellation of Trade License_After verification or inspection, license is cancelled or not?":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_cancellation_afterverification_data"));
				break;
			case "Cancellation of Trade License_View Application details and update the remarks.":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_cancellation_viewmodifi_data"));

				MarketLicenseServices Cancel_ViewApplication = new MarketLicenseServices();
				Cancel_ViewApplication.viewandmodifi(); 


				break;
			case "Cancellation of Trade License_Final Decision \"YES\"/\"NO\"":  
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_cancellation_final_data"));

				break; 

			case "Cancellation of Trade License_If Decision is \"YES\" then Type \"YES\" to generate LOI":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_cancellation_genloi_data"));
				if(mGetPropertyFromFile("mkt_cancellation_genloi_data").equalsIgnoreCase("YES"))
				{
					MarketLicenseServices Cancel_Decision = new MarketLicenseServices();
					Cancel_Decision.cancellation_marketLOIGeneration(); 

				}
				break; 

			case "Cancellation of Trade License_If Decision is \"NO\" then Type \"NO\" to generate Rejection Letter":

				Scrutiny_EnterText(mGetPropertyFromFile("mkt_cancellation_Reject_data"));
				break; 

				//////////////////
				//duplicate first level scrutiny swapnil16-11-2016
				///////////////////

			case "Duplicate Trade License_Do You want Duplicate certificate?":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_duplicate_certificate_data"));
				break;

			case "Duplicate Trade License_Decision":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_duplicate_Decision_data"));
				break;

			case "Duplicate Trade License_Detail Remark":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_duplicate_Detail_Remark_data"));
				break;

				//duplicate second level scrutiny swapnil16-11-2016

			case "Duplicate Trade License_Decision( Whether the identification details of license holder correct?)":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_duplicate_identification_data"));
				break;

			case "Duplicate Trade License_if identification details of license holder correct- generate LOI":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_duplicate_LOI_data"));
				if(mGetPropertyFromFile("mkt_duplicate_LOI_data").equalsIgnoreCase("yes"))
				{
					mClick("css", imageid);

					//MarketLicenseServices Dupli_DuplicateLOI = new MarketLicenseServices();
					NTL.duplicate_LOI();

				}

				break;

			case "Duplicate Trade License_if identification details of license holder not correct- generate rejection":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_duplicate_rejection_data"));
				if(mGetPropertyFromFile("mkt_duplicate_rejection_data").equalsIgnoreCase("no"))
				{
					mClick("css", imageid);

					MarketLicenseServices Dupli_Scrutiny_Rejection = new MarketLicenseServices();
					Dupli_Scrutiny_Rejection.Duplicate_Rejection();

				}
				break;

				//  Transfer Under Nomination first level scrutiny swapnil16-11-2016
			case "Transfer of License Under Nomination_Date of the Inspection":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_Inspection_data"));
				break;

			case "Transfer of License Under Nomination_Name of the Market Inspector":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_tlun_Inspector_data"));
				break;
			case "Transfer of License Under Nomination_Whether the receipt of the paid property tax for the year is enclosed or not?":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_tlun_property_data"));
				break;
			case "Transfer of License Under Nomination_Is there any arrears of Water Bill?":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_tlun_Water_data"));
				break;

			case "Transfer of License Under Nomination_Remarks of the Market Inspector":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_tlun_Remarks_data"));
				break;

			case "Transfer of License Under Nomination_Whether the License Fees to be paid is correct or not?":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_tlun_Fees_data"));
				break;
			case "Transfer of License Under Nomination_Whether the renewal Fees to be paid & received is accepted or not ?":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_tlun_accepted_data"));
				break;
			case "Transfer of License Under Nomination_Whether the concerned License No. is renewed for the year or not?":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_tlun_renewed_data"));
				break;

			case "Transfer of License Under Nomination_Whether the professional certificate enclosed or not (if applicable)":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_tlun_professional_data"));
				break;

				////transfer under nomination second level scutiny 
			case "Transfer of License Under Nomination_Chief Market Inspector suggestions and remarks.":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_tlun_suggestions_data"));
				break;	
			case "Transfer of License Under Nomination_Are you agree with the remarks of Market Inspector?":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_tlun_agree_data"));
				break;
			case "Transfer of License Under Nomination_Decision (Whether the license should be Transfer ? )":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_tlun_Desion_data"));
				break;


			case "Transfer of License Under Nomination_IF Final Decision is YES then type Yes and generate LOI":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_tlun_LOI_data"));
				if(mGetPropertyFromFile("mkt_tlun_LOI_data").equalsIgnoreCase("yes"))
				{mClick("css", imageid);

				MarketLicenseServices TUN_LOI = new MarketLicenseServices();
				TUN_LOI.tlun_loi_generation();

				}
				else {

				}
				break;

			case "Transfer of License Under Nomination_IF Final Decision is NO then type No and give reasons":
				Scrutiny_EnterText(mGetPropertyFromFile("mkt_tlun_no_data"));
				if(mGetPropertyFromFile("mkt_tlun_no_data").equalsIgnoreCase("NO"))
				{
					mClick("css", imageid);

					MarketLicenseServices TUN_Scrutiny_Rejection = new MarketLicenseServices();
					TUN_Scrutiny_Rejection.tlun_rejection();

				}
				else 
				{
					System.out.println("Rejection letter is not applicable");
				}
				break;
				
				
				/// Question for Booking of Hoarding for Display of Advertisement in scrutiny process
			case "Booking of Hoarding for Display of Advertisement_Date of Inspection":
				Scrutiny_EnterDate(mGetPropertyFromFile("adh_Inspectiondatedata"));
				SCR_SiteInspectiondate.add(mGetPropertyFromFile("adh_InspectionDoneBydata"));
				break;
				
			
			case "Booking of Hoarding for Display of Advertisement_Inspection done by":
				Scrutiny_EnterText(mGetPropertyFromFile("adh_InspectionDoneBydata"));
				SCR_SiteInspectorName.add(mGetPropertyFromFile("adh_InspectionDoneBydata"));
				break;
				
			case "Booking of Hoarding for Display of Advertisement_Remarks":
				Scrutiny_EnterText(mGetPropertyFromFile("Sc_Remarkdata"));
				SCR_SiteInspectorRemarks.add(mGetPropertyFromFile("Sc_Remarkdata"));
				break;
			case "Booking of Hoarding for Display of Advertisement_View and modify Application details":
				Scrutiny_EnterText(mGetPropertyFromFile("adh_View_ModifyApplicationdata")); 
				if(mGetPropertyFromFile("adh_View_ModifyApplicationdata").equalsIgnoreCase("yes"))
				{
					adh.adh_View_ModifyApp();
				}
				break;
			
			
			case "Booking of Hoarding for Display of Advertisement_Final Decision \"YES\"/\"NO\"":
				Scrutiny_EnterText(mGetPropertyFromFile("adh_LOIGenFinalDecisiondata")); 
				break;
				
			case "Booking of Hoarding for Display of Advertisement_If Decision is \"YES\" then Type \"YES\" to generate LOI":
				if(mGetPropertyFromFile("adh_LOIGenFinalDecisiondata").equalsIgnoreCase("yes"))
				{						
					adh.adh_Gen_LOILetter();
				}
				break;	
			case "Booking of Hoarding for Display of Advertisement_If Decision is \"NO\" then Type \"NO\" to generate Rejection Letter":

				if(mGetPropertyFromFile("adh_LOIGenFinalDecisiondata").equalsIgnoreCase("no"))
				{	

					adh.adh_Gen_RejectionLetter();
				}
				break;
				// Question for Renewal of Advertising Contract in scrutiny process
			case "Renewal of Advertising Contract_Date of Inspection":
				Scrutiny_EnterDate(mGetPropertyFromFile("adh_Inspectiondatedata"));
				SCR_SiteInspectiondate.add(mGetPropertyFromFile("adh_InspectionDoneBydata"));
				break;
				
			
			case "Renewal of Advertising Contract_Inspection done by":
				Scrutiny_EnterText(mGetPropertyFromFile("adh_InspectionDoneBydata"));
				SCR_SiteInspectorName.add(mGetPropertyFromFile("adh_InspectionDoneBydata"));
				break;
				
			case "Renewal of Advertising Contract_Remarks":
				Scrutiny_EnterText(mGetPropertyFromFile("Sc_Remarkdata"));
				SCR_SiteInspectorRemarks.add(mGetPropertyFromFile("Sc_Remarkdata"));
				break;
			case "Renewal of Advertising Contract_View and modify Application details":
				Scrutiny_EnterText(mGetPropertyFromFile("adh_View_ModifyApplicationdata")); 
				if(mGetPropertyFromFile("adh_View_ModifyApplicationdata").equalsIgnoreCase("yes"))
				{

					adh.adh_View_ModifyApp_For_renewalofAdvtContract();

				}
				break;
			
			
			case "Renewal of Advertising Contract_Final Decision \"YES\"/\"NO\"":
				Scrutiny_EnterText(mGetPropertyFromFile("adh_LOIGenFinalDecisiondata")); 
				break;
				
			case "Renewal of Advertising Contract_If Decision is \"YES\" then Type \"YES\" to generate LOI":
				if(mGetPropertyFromFile("adh_LOIGenFinalDecisiondata").equalsIgnoreCase("yes"))
				{						
					adh.adh_Gen_LOILetter();
				}
				break;	
			case "Renewal of Advertising Contract_If Decision is \"NO\" then Type \"NO\" to generate Rejection Letter":

				if(mGetPropertyFromFile("adh_LOIGenFinalDecisiondata").equalsIgnoreCase("no"))
				{	

					adh.adh_Gen_RejectionLetter();
				}
				break;
			// Question for Setup of Hoarding for Display of Advertisement in scrutiny process
			case "Setup of Hoarding for Display of Advertisement_Date of Inspection":
				Scrutiny_EnterDate(mGetPropertyFromFile("adh_Inspectiondatedata"));
				SCR_SiteInspectiondate.add(mGetPropertyFromFile("adh_InspectionDoneBydata"));
				break;
				
			
			case "Setup of Hoarding for Display of Advertisement_Inspection done by":
				Scrutiny_EnterText(mGetPropertyFromFile("adh_InspectionDoneBydata"));
				SCR_SiteInspectorName.add(mGetPropertyFromFile("adh_InspectionDoneBydata"));
				break;
				
			case "Setup of Hoarding for Display of Advertisement_Remarks":
				Scrutiny_EnterText(mGetPropertyFromFile("Sc_Remarkdata"));
				SCR_SiteInspectorRemarks.add(mGetPropertyFromFile("Sc_Remarkdata"));
				break;
				
			case "Setup of Hoarding for Display of Advertisement_View and modify Application details":
				Scrutiny_EnterText(mGetPropertyFromFile("adh_View_ModifyApplicationdata")); 
				if(mGetPropertyFromFile("adh_View_ModifyApplicationdata").equalsIgnoreCase("yes"))
				{
					adh.adh_View_ModifyApp_For_renewalofAdvtContract();
				}
				break;
		
			case "Setup of Hoarding for Display of Advertisement_Final Decision \"YES\"/\"NO\"":
				Scrutiny_EnterText(mGetPropertyFromFile("adh_LOIGenFinalDecisiondata")); 
				break;
				
			case "Setup of Hoarding for Display of Advertisement_If Decision is \"YES\" then Type \"YES\" to generate LOI":
				if(mGetPropertyFromFile("adh_LOIGenFinalDecisiondata").equalsIgnoreCase("yes"))
				{						
					adh.adh_Gen_LOILetter();
				}
				break;	
			case "Setup of Hoarding for Display of Advertisement_If Decision is \"NO\" then Type \"NO\" to generate Rejection Letter":

				if(mGetPropertyFromFile("adh_LOIGenFinalDecisiondata").equalsIgnoreCase("no"))
				{	

					adh.adh_Gen_RejectionLetter();
				}
				break;	
				
				
				
				
				
				
				
				
				
			}

		}

		catch(Exception e)
		{
			e.printStackTrace();

		}

	}



	///////////////////////
	//Generic method for uploading at the end of scrutiny form
	// Created by hiren kathiria on 17/10/2016 
	// modified on 18/10/2016
	////////////////////

	public void UploadDocs()
	{
		try
		{

			int count=Integer.parseInt(mGetPropertyFromFile("Sc_UploadDocumentcount"));	

			if(count>2)
			{
				log.info("Entered value for Sc_UploadDocumentcount is greater than 3");
			}

			else
			{
				for(int a=0;a<count;a++)
				{
					//uploading document
					mUpload("id", mGetPropertyFromFile("Sc_UploadDocumentPart1id")+a+mGetPropertyFromFile("Sc_UploadDocumentPart2id"), mGetPropertyFromFile("Sc_UploadDocumentdata"+a));
					mCustomWait(1000);	
					mTakeScreenShot(); 
					mSendKeys("id", mGetPropertyFromFile("Sc_UploadDocumentRemarkid")+a, mGetPropertyFromFile("Sc_UploadDocumentRemarkdata"+a));
					mCustomWait(1000);
					mTakeScreenShot(); 
					if((a>=0 && count>1) && a<=2)
					{
						mClick("id", mGetPropertyFromFile("Sc_UploadDocumentAddButtonid"));
						mTakeScreenShot(); 
					}	
					if(a==2 && count==3)
					{
						mWaitForVisible("css", mGetPropertyFromFile("Sc_MaxUploadDocumentPopUpCloseid")); 
						mCustomWait(500); 
						mTakeScreenShot(); 
						String Msg=mGetText("css", mGetPropertyFromFile("Sc_MaxUploadDocumentPopUpMsgid"));
						mAssert(Msg, mGetPropertyFromFile("Sc_MaxUploadDocumentPopUpMsgdata"), "Actual:  "+Msg+"  Expected  :"+mGetPropertyFromFile("Sc_MaxUploadDocumentPopUpMsgdata"));
						mCustomWait(750); 
						mWaitForVisible("css", mGetPropertyFromFile("Sc_MaxUploadDocumentPopUpCloseid")); 
						mClick("css", mGetPropertyFromFile("Sc_MaxUploadDocumentPopUpCloseid"));
						mCustomWait(750);
					}
				}
				mTakeScreenShot(); 
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	// Second level ScrutinyProcess(TP)
	public void trutiPatraGenratn(String YesNo)
	{
		try
		{

			//waiting for truti patra to be open
			mWaitForVisible("xpath", mGetPropertyFromFile("tp_TrutiPatraGenApplicationNoAtFormid"));
			String ApplicationNo = mGetText("xpath", mGetPropertyFromFile("tp_TrutiPatraGenApplicationNoAtFormid"));
			mCustomWait(1000);
			System.out.println(ApplicationNo);
			//String ApplicationNo = driver.findElement(By.xpath("//form[@id='frmTrutiPatra']/div[3]/div/div[2]")).getText();

			//selecting All remarks
			mCustomWait(1000);
			mWaitForVisible("id",  mGetPropertyFromFile("tp_TrutiPatraGenDetailRemarksid"));
			mClick("id", mGetPropertyFromFile("tp_TrutiPatraGenDetailRemarksid"));
			mCustomWait(1000);

			mSendKeys("id", mGetPropertyFromFile("tp_TrutiPatraGenRemarkFieldid"),mGetPropertyFromFile("tp_TrutiPatraGenRemarkFielddata"));
			mCustomWait(1000);

			//selecting hearing yes or no
			if(mGetPropertyFromFile("tp_TrutiPatraGenHearingYesNodata").equalsIgnoreCase("Yes"))
			{
				mClick("id", mGetPropertyFromFile("tp_TrutiPatraGenHearingYesid"));
				mCustomWait(1000);

				//sending hearing date
				mWaitForVisible("id", mGetPropertyFromFile("tp_TrutiPatraGenYesHearingDateid"));
				mDateSelect("id",mGetPropertyFromFile("tp_TrutiPatraGenYesHearingDateid"), mGetPropertyFromFile("tp_TrutiPatraGenYesHearingDatedata"));
				mClick("xpath", mGetPropertyFromFile("tp_TrutiPatraGenHearingDateTimeDoneid"));	
				mCustomWait(1000);

				//selcting approve id
				mClick("id", mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraApprvid"));

				//sending detail remark
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("tp_TrutiPatraGenViewDocRemarksid"), mGetPropertyFromFile("tp_TrutiPatraGenViewDocRemarksdata"));
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

				//selcting approve id
				mCustomWait(2000);
				mClick("id", mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraApprvid"));

				//sending detail remark
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("tp_TrutiPatraGenViewDocRemarksid"), mGetPropertyFromFile("tp_TrutiPatraGenViewDocRemarksdata"));
				mCustomWait(1000);
				mTakeScreenShot();

				//truti patra form submit button
				mCustomWait(1000);
				mClick("css", mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraApplnSubBtnid"));
				mGenericWait();
			}		  
		}

		catch(Exception e)
		{
			e.printStackTrace();

		}
	}




	// Third level ScrutinyProcess(TP)
	public void trutiPatraVerification()
	{
		try
		{

			mWaitForVisible("id", mGetPropertyFromFile("tp_TrutiPatraGenVerifyDocidid"));
			//verify Documents checkbox
			mClick("id", mGetPropertyFromFile("tp_TrutiPatraGenVerifyDocidid"));
			mCustomWait(1000);

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
		}

		catch(Exception e)
		{
			e.printStackTrace();

		}
	}



	/////////////////////////////////
	public void twlsSendNextLevel()
	{
		try
		{
			//sending application number
			mWaitForVisible("name", mGetPropertyFromFile("Sc_scrutinyProcessAppnNoid"));

			//			mSendKeys("css", mGetPropertyFromFile("Sc_scrutinyProcessAppnNoid"), "71116092900041");

			IndOrDep("name", "Sc_scrutinyProcessAppnNoid", "applicationNo");

			//search application Button
			mGenericWait();
			mWaitForVisible("xpath", mGetPropertyFromFile("Sc_scrutinyProcessSearchBtnid"));
			mClick("xpath", mGetPropertyFromFile("Sc_scrutinyProcessSearchBtnid"));

			//sentTo button
			mGenericWait();
			mWaitForVisible("xpath", mGetPropertyFromFile("Sc_scrutinyProcessSentToNextLevelImgid"));
			mClick("xpath", mGetPropertyFromFile("Sc_scrutinyProcessSentToNextLevelImgid"));

			//sending employee name
			mWaitForVisible("id", mGetPropertyFromFile("Sc_SentToNextLevelEmployeeid"));
			mCustomWait(1000);
			mSelectDropDown("id",  mGetPropertyFromFile("Sc_SentToNextLevelEmployeeid"), mGetPropertyFromFile("Sc_SentToNextLevelEmployeedata_"+(scrutinylevelcounter+1)));

			mCustomWait(1200);
			mTakeScreenShot();

			//closing pop-up
			mWaitForVisible("name", mGetPropertyFromFile("Sc_SentToNextLevelEmployeeClosePopUpid"));
			mCustomWait(2000);
			mClick("name", mGetPropertyFromFile("Sc_SentToNextLevelEmployeeClosePopUpid"));

			//final close
			mWaitForVisible("xpath", mGetPropertyFromFile("Sc_NextLevelAftrPopUpCloseid"));
			mCustomWait(2000);

			String msgAftrSendinToSecScrutiny = mGetText("css", mGetPropertyFromFile("Sc_NextLevelMsgAftrsentToEmployeepopupid"));
			System.out.println(msgAftrSendinToSecScrutiny);
			String numberOnly= msgAftrSendinToSecScrutiny.replaceAll("[^0-9]", "");

			System.out.println(numberOnly);
			String delimiter = numberOnly.toString();
			String msg[]=msgAftrSendinToSecScrutiny.split(delimiter);
			System.out.println(msg[1].trim());

			if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
			{
				mAssert(numberOnly, applicationNo.get(CurrentinvoCount), " Actual   :"+numberOnly+"    Expected    :"+applicationNo.get(CurrentinvoCount)+"  At sendToemployee");
				mCustomWait(1000);
			}			
			else
			{
				mAssert(numberOnly, mGetPropertyFromFile("applicationNo"), " Actual   :"+numberOnly+"    Expected    :"+mGetPropertyFromFile("applicationNo")+"  At sendToemployee");
				mCustomWait(1000);
			}

			mAssert(msg[1].trim(), mGetPropertyFromFile("Sc_AftrsentToNextLevelEmployeepopupdata"), "Actual   :"+msg[1].trim() +"   Expected    :"+mGetPropertyFromFile("Sc_AftrsentToNextLevelEmployeepopupdata"));

			System.out.println(msgAftrSendinToSecScrutiny);
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("Sc_NextLevelAftrPopUpCloseid"));
			mCustomWait(1000);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}



	/////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////
	/////////////////////new hiren methods
	/////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////

	public void tp_JrEngg_Name()
	{
		try
		{
			mSendKeys("id",textid, mGetPropertyFromFile("tp_SiteInspectJrengineerVisitdata"));
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	public void Scrutiny_EnterText(String data)
	{
		try
		{
			mSendKeys("id",textid, data);			
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	public void Scrutiny_EnterDate(String data)
	{
		try
		{
			mDateSelect("id",textid, data);

		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	//Scrutiny Process Letter Downloading for Town Planning module by piyush modified and upadtesd on 02 feb 2017
	
	//Scrutiny Process Letter Downloading for Town Planning module by piyush modified and upadtesd on 02 feb 2017
		public void TP_Scrutiny_LetterDownload()
		{
			try{
				TownPlanningServices tp1=new TownPlanningServices();
				if(!ServiceName.equalsIgnoreCase("Booking of Estate") && !ServiceName.equalsIgnoreCase("Renewal Of Rent Contract"))
				{
					
					if(mGetPropertyFromFile("tp_FirstlsSiteInspectionYNdata").equalsIgnoreCase("yes") && scrutinylevelcounter==1)
					{  
						if(ServiceName.equalsIgnoreCase("Application for Development Plan")){
							mChallanPDFReader();
							api.PdfPatterns.landDevSiteInspLetter(pdfoutput);
							tp1.TPSiteInspectionLetter_Application_Dev_plan();
						}else if(ServiceName.equalsIgnoreCase("Application for Building Permit")){
							mChallanPDFReader();
							api.PdfPatterns.landDevSiteInspLetter(pdfoutput);
							tp1.TPSiteInspectionLetter_Building_Permit();
							System.out.println("Site Inspection letter of buiding permission");
						}
						else{
							System.out.println("Occupancy Certificate genetared");
						}
					}
					if(mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraYNdata").equalsIgnoreCase("yes") && scrutinylevelcounter==2)
					{	mChallanPDFReader();
						tp1.TPtrutiPatraLetter();
					}
					if(mGetPropertyFromFile("tp_TrutiPatraGenTrutiPatraYNdata").equalsIgnoreCase("yes") && mGetPropertyFromFile("tp_LOIGenEditServicedata").equalsIgnoreCase("yes") && mGetPropertyFromFile("tp_LOIGenGenerateLoidata").equalsIgnoreCase("yes") && scrutinylevelcounter==3)
					{	mChallanPDFReader();
						tp1.TPtrutiPatraLetter();
					}
				}

			}
			catch(Exception e){
				e.printStackTrace();
				throw new MainetCustomExceptions("Error in letter downloading and assertion method during scrutiny");

			}
		}






	public void viesandScrutiny()
		{
			try
			{

				if(abc.equalsIgnoreCase("Application for Building Permit"))
				{
					mWaitForVisible("css", mGetPropertyFromFile("Sc_scrutinyProcessView&ScrutinyBtnid"));
					mCustomWait(500);
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("Sc_scrutinyProcessView&ScrutinyBtnid"));

					TownPlanningServices tp1=new  TownPlanningServices();
					tp1.bpr_ViewandAssertform();

					mWaitForVisible("css", mGetPropertyFromFile("Sc_scrutinyProcessScrutinyBtnid"));			
					mTakeScreenShot();
					mscroll(0,500);
					mCustomWait(500);
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("Sc_scrutinyProcessScrutinyBtnid"));			
				}	
				else if(abc.equalsIgnoreCase("Occupancy Certificate"))
				{
					mWaitForVisible("css", mGetPropertyFromFile("Sc_scrutinyProcessView&ScrutinyBtnid"));
					mCustomWait(500);
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("Sc_scrutinyProcessView&ScrutinyBtnid"));

					TownPlanningServices tp1=new  TownPlanningServices();
					tp1.OccupancyCertificateViewScrutinyAssertion();

					mWaitForVisible("css", mGetPropertyFromFile("Sc_scrutinyProcessScrutinyBtnid"));			
					mTakeScreenShot();
					mscroll(0,500);
					mCustomWait(500);
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("Sc_scrutinyProcessScrutinyBtnid"));		
				}
				else if(abc.equalsIgnoreCase("Application for Development Plan"))
				{
					/*mWaitForVisible("css", mGetPropertyFromFile("Sc_scrutinyProcessViewScrutinyid"));
					mCustomWait(1000);
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("Sc_scrutinyProcessViewScrutinyid"));*/


					mWaitForVisible("css", mGetPropertyFromFile("Sc_scrutinyProcessView&Scrutinyid"));
					mCustomWait(500);
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("Sc_scrutinyProcessView&Scrutinyid"));

					TownPlanningServices tp1=new  TownPlanningServices();
					tp1.Ald_ViewandAssertform();

					mWaitForVisible("css", mGetPropertyFromFile("Sc_scrutinyProcessScrutinyBtnid"));			
					mTakeScreenShot();
					mscroll(0,500);
					mCustomWait(500);
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("Sc_scrutinyProcessScrutinyBtnid"));	
				}	
				else if(abc.equalsIgnoreCase("Booking of Estate"))
				{
					mWaitForVisible("css", mGetPropertyFromFile("Sc_scrutinyProcessView&ScrutinyBtnid"));
					mCustomWait(500);
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("Sc_scrutinyProcessView&ScrutinyBtnid"));

					RentAndLeaseServices RnL=new RentAndLeaseServices();
					RnL.RNL_ScrutinyView_Assertion();

					mWaitForVisible("css", mGetPropertyFromFile("Sc_scrutinyProcessScrutinyBtnid"));			
					mTakeScreenShot();
					mscroll(0,500);
					mCustomWait(500);
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("Sc_scrutinyProcessScrutinyBtnid"));		

				}
				else if(abc.equalsIgnoreCase("Rent Renewal Contract"))
				{
					mWaitForVisible("css", mGetPropertyFromFile("Sc_scrutinyProcessView&ScrutinyBtnid"));
					mCustomWait(500);
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("Sc_scrutinyProcessView&ScrutinyBtnid"));

					RentAndLeaseServices RnL=new RentAndLeaseServices();
					RnL.RNL_RenewalofRent_ScrutinyView_Assertion();

					mWaitForVisible("css", mGetPropertyFromFile("Sc_scrutinyProcessScrutinyBtnid"));			
					mTakeScreenShot();
					mscroll(0,500);
					mCustomWait(500);
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("Sc_scrutinyProcessScrutinyBtnid"));		

				}
				
				else if(abc.equalsIgnoreCase("Booking of Hoarding for Display of Advertisement"))
				{
					mWaitForVisible("css", mGetPropertyFromFile("Sc_scrutinyProcessView&ScrutinyBtnid"));
					mCustomWait(500);
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("Sc_scrutinyProcessView&ScrutinyBtnid"));

					AdvertisingAndHoardingServices adh=new AdvertisingAndHoardingServices();
					adh.adh_Booking_Of_Hoarding_ScrutinyView_Assertion();

					mWaitForVisible("css", mGetPropertyFromFile("Sc_scrutinyProcessScrutinyBtnid"));			
					mTakeScreenShot();
					mscroll(0,500);
					mCustomWait(500);
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("Sc_scrutinyProcessScrutinyBtnid"));		

				}
				
				else if(abc.equalsIgnoreCase("Renewal of Advertising Contract"))
				{
					mWaitForVisible("css", mGetPropertyFromFile("Sc_scrutinyProcessView&ScrutinyBtnid"));
					mCustomWait(500);
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("Sc_scrutinyProcessView&ScrutinyBtnid"));

					AdvertisingAndHoardingServices adh=new AdvertisingAndHoardingServices();
					adh.adh_Renewal_of_Advertising_Contract_ScrutinyView_Assertion();

					mWaitForVisible("css", mGetPropertyFromFile("Sc_scrutinyProcessScrutinyBtnid"));			
					mTakeScreenShot();
					mscroll(0,500);
					mCustomWait(500);
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("Sc_scrutinyProcessScrutinyBtnid"));		

				}
				else if(abc.equalsIgnoreCase("Setup of Hoarding for display of Advertisement"))
				{
					mWaitForVisible("css", mGetPropertyFromFile("Sc_scrutinyProcessView&ScrutinyBtnid"));
					mCustomWait(500);
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("Sc_scrutinyProcessView&ScrutinyBtnid"));

					AdvertisingAndHoardingServices adh=new AdvertisingAndHoardingServices();
					adh.adh_Set_Up_Of_Hoarding_ScrutinyView_Assertion();

					mWaitForVisible("css", mGetPropertyFromFile("Sc_scrutinyProcessScrutinyBtnid"));			
					mTakeScreenShot();
					mscroll(0,500);
					mCustomWait(500);
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("Sc_scrutinyProcessScrutinyBtnid"));		
				}
				
				else
				{
					mWaitForVisible("css", mGetPropertyFromFile("Sc_scrutinyProcessViewScrutinyid"));
					mCustomWait(1000);
					mTakeScreenShot();
					mClick("css", mGetPropertyFromFile("Sc_scrutinyProcessViewScrutinyid"));
				}
			}
			catch(Exception e){
				e.printStackTrace();
				throw new MainetCustomExceptions("Error in this method viesandScrutiny");
			}
		}
	

} 
//Final End











