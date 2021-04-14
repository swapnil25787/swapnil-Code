package common;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import api.CommonUtilsAPI;

import com.abm.mainet.mkt.MarketLicenseServices;
import com.abm.mainet.rnl.RentAndLeaseServices;
import com.abm.mainet.tp.TownPlanningServices;

import errorhandling.MainetCustomExceptions;
import excelreader.ExcelToProperties;
//import com.abm.mainet.rti.RTIServices;
/**
 * @author Madhuri.dawande
 * @since 21-08-2015
 * 
 */
 

public class CommonMethods extends CommonUtilsAPI {

	String ulbName;
	String appNoInChallanVer;
	String LOINumber;
	String challanAmt;
	protected String Rcptamt;
	ExcelToProperties excelToProp = new ExcelToProperties();
	
	

	// Method for ULB selection

	public void selectUlb()
	{
		try
		{		

			System.out.println("Select ULB Class:::"+myClassName);

			mCustomWait(1500);

			//mSelectDropDown("id", mGetPropertyFromFile("municipalityid"),mGetPropertyFromFile("municipality"));
			mClick("id", mGetPropertyFromFile("municipalityid"));
			mCustomWait(500);

			mSendKeys("css", mGetPropertyFromFile("municipalitytextbox_id"), mGetPropertyFromFile("municipality"));

			mCustomWait(1500);

			driver.findElement(By.cssSelector(mGetPropertyFromFile("municipalitytextbox_id"))).sendKeys(Keys.ENTER);

			mClick("css", mGetPropertyFromFile("ulbSubmitid"));

			mCustomWait(4000);

			waitForPageLoaded();
//window
			/*
			 * ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
			 * System.out.println(tabs2);
			 * 
			 * Thread.sleep(2000); driver.switchTo().window(tabs2.get(1));
			 */

			Thread.sleep(2000);

			if(mGetPropertyFromFile("browserName").equalsIgnoreCase("ie"))
			{
				driver.manage().window().maximize();
			}

			//english language selection
			if(	driver.findElements(By.linkText("English")).size() != 0)
			{
				mWaitForVisible("linkText", "English");
				driver.findElement(By.linkText("English")).click();
				mGenericWait();
			}
			else if (driver.findElements(By.linkText("à¤‡à¤‚à¤—à¥?à¤°à¤œà¥€")).size() != 0)
			{
				mWaitForVisible("linkText", "à¤‡à¤‚à¤—à¥?à¤°à¤œà¥€");
				driver.findElement(By.linkText("à¤‡à¤‚à¤—à¥?à¤°à¤œà¥€")).click();
				mGenericWait();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in selectUlb method");
		}
	}

	// Method for Citizen Services Login/Agency Login/Department Login
	public void login(String loginType,String uName, String pwd)
	{
		try
		{



			if(loginType.equalsIgnoreCase("Citizen"))
			{
				citizenLogin();				
			}
			else if(loginType.equalsIgnoreCase("Agency"))
			{
				agencyLogin();
			}
			else if(loginType.equalsIgnoreCase("Department"))
			{
				departmentLogin(uName, pwd);
			}
			else
			{
				System.out.println("Please check entered type of login");
			}



		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in login method");
		}
	}

	// Method for citizen login
	// Method for citizen login
		public void citizenLogin()
		{
			try
			{

				System.out.println("My class name in cit login::"+myClassName);

				mouseOverOnElementUsingRobot(By.cssSelector(mGetPropertyFromFile("loginid")));

				Thread.sleep(2000);

				// Click 'Citizen Services Login
				mClick("linkText", mGetPropertyFromFile("citizenLoginid"));


				// Wait for visible 'User ID'
				mWaitForVisible("id",mGetPropertyFromFile("citizenUserNameid"));

				mGenericWait();
				// Enter user name

				mSendKeys("id",mGetPropertyFromFile("citizenUserNameid"),mGetPropertyFromFile("citizenUserName"));

				// Wait for visible 'Password'
				mWaitForVisible("id",mGetPropertyFromFile("citizenPasswordid"));
				mGenericWait();

				// Enter password
				mSendKeys("id",mGetPropertyFromFile("citizenPasswordid"),mGetPropertyFromFile("citizenPassword"));

				// Wait for visible 'Verification Code'
				mWaitForVisible("id",mGetPropertyFromFile("verificationCodeid"));
				mGenericWait();

				// Enter verification code
				mSendKeys("id",mGetPropertyFromFile("verificationCodeid"),mGetPropertyFromFile("verificationCode"));
				mGenericWait();

				//mTakeScreenShot("com.abm.mainet.bnd.BirthAndDeathServices");
				// Click 'Submit' button on citizen login window
				mClick("xpath",mGetPropertyFromFile("citizenSubmitid"));

				// Wait for visible 'Citizen Services'
				//	mWaitForVisible("css",mGetPropertyFromFile("bnd_citizenservicesid"));

				// Wait for visible 'pie chart'

				/*mWaitForVisible("css",mGetPropertyFromFile("pieChartDraftsid"));
				mWaitForVisible("css",mGetPropertyFromFile("pieChartCompletedid"));

				mWaitForVisible("css",mGetPropertyFromFile("pieChartRejectedid"));
				mWaitForVisible("css",mGetPropertyFromFile("pieChartProcessid"));*/
				mTakeScreenShot();

				//New code for checking if page has loaded... trial sunil d sonmale

				waitForPageLoaded();

				Robot robot = new Robot();
				robot.mouseMove(100, 200);

			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw new MainetCustomExceptions("Error in citizenLogin method");
			} 
		}
	// Method for department login
	public void departmentLogin(String uName, String pwd)
	{
		try
		{

			mouseOverOnElementUsingRobot(By.cssSelector(mGetPropertyFromFile("loginid")));

			Thread.sleep(2000);

			mClick("linkText", mGetPropertyFromFile("departmentLoginid"));


			System.out.println("My class name in dept login::"+myClassName);

			// Wait for visible 'User ID'
			mWaitForVisible("id", mGetPropertyFromFile("deptUserNameid"));
			mGenericWait();

			// Enter user name
			mSendKeys("id", mGetPropertyFromFile("deptUserNameid"), uName);

			// Wait for visible 'Password'
			mWaitForVisible("id", mGetPropertyFromFile("deptPasswordid"));
			mGenericWait();

			// Enter password

			mSendKeys("id", mGetPropertyFromFile("deptPasswordid"), pwd);
			mGenericWait();

			// Click 'Submit' button on department login window
			mClick("xpath", mGetPropertyFromFile("deptSubmitid"));
		/*	if (driver.findElement(By.xpath("//*[@id='alertify-logs']/article")).isDisplayed()) {
				System.out.println(driver.findElement(By.xpath("//*[@id='alertify-logs']/article")).getText());
			}//*[@id="alertify"]/article
			
*/			System.out.println();


			//New code for checking if page has loaded... trial sunil d sonmale

			waitForPageLoaded();

			Robot robot = new Robot();
			robot.mouseMove(100, 200);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in departmentLogin method");
		}

	}


	public void agencyLogin()
	{
		try{

			System.out.println("My class name in cit login::"+myClassName);
			////////////////////////

			mouseOverOnElementUsingRobot(By.cssSelector(mGetPropertyFromFile("loginid")));

			Thread.sleep(2000);

			// Click 'Agency Login'
			mClick("linkText", mGetPropertyFromFile("agencyLoginid"));

			// Wait for visible 'Type of Agency'
			mWaitForVisible("id", mGetPropertyFromFile("agencyTypeid"));
			mGenericWait();

			// Select type of agency
			mSelectDropDown("id", mGetPropertyFromFile("agencyTypeid"), mGetPropertyFromFile("agencyType"));

			// Enter User ID
			mSendKeys("id", mGetPropertyFromFile("agencyUserNameid"), mGetPropertyFromFile("agencyUserName"));			

			// Enter Password
			mSendKeys("id", mGetPropertyFromFile("agencyPasswordid"), mGetPropertyFromFile("agencyPassword"));	

			mTakeScreenShot();
			// Click 'Submit' button on agency login window
			mClick("css",mGetPropertyFromFile("agencySubmitid"));


			//New code for checking if page has loaded... trial sunil d sonmale

			waitForPageLoaded();

			Robot robot = new Robot();
			robot.mouseMove(100, 200);


		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in agencyLogin method");
		}
	}

	// Method for logout
	public void logOut()
	{
		try
		{
			mGenericWait();

			// Click 'Logout'

			mWaitForVisible("linkText", mGetPropertyFromFile("logoutid"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("logoutid"));

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in logOut method");
		}
	}

	// Method for final logout
	public void finalLogOut()
	{
		try
		{
			// Click Ok
			mGenericWait();
			mCustomWait(500);
			mWaitForVisible("id", mGetPropertyFromFile("logoutOkid"));
			mTakeScreenShot();
			mCustomWait(500);
			mClick("id", mGetPropertyFromFile("logoutOkid"));
			mTakeScreenShot();
			mCustomWait(500);
			mWaitForVisible("css", mGetPropertyFromFile("loginid"));

			/*// Wait for visible 'Citizen login'
			mWaitForVisible("xpath",mGetPropertyFromFile("citizenLoginid"));

			// Wait for visible 'Agency login'
			mWaitForVisible("xpath",mGetPropertyFromFile("agencyLoginid"));

			// Wait for visible 'Department login'
			mWaitForVisible("xpath",mGetPropertyFromFile("departmentLoginid"));
			mGenericWait();*/

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in finallogOut method");
		}
	}


	//Sneha Solaskar
	// Common method to select offline payment mode i.e. bank or ulb

	public void payment(String paymntMode,String mode) 	{
		try
		{ 
			if(mGetPropertyFromFile(paymntMode).equalsIgnoreCase("Online"))
			{
				onlinePayment();
			}
			else if(mGetPropertyFromFile(paymntMode).equalsIgnoreCase("Offline"))
			{
				modeForChallan=mode;

				mGenericWait();
				//	mWaitForVisible("id", mGetPropertyFromFile("LOIofflinePaymentId"));
				//clicking on offline mode
				if (mGetPropertyFromFile("rti_rtiAppLinkId").equalsIgnoreCase("RTI Application Entry")) 
				{
					mClick("id", mGetPropertyFromFile("RTIOfflinePaymentId"));
				}
				else
				{
					mClick("id", mGetPropertyFromFile("LOIofflinePaymentId"));
				}


				mGenericWait();

				mSelectDropDown("id", mGetPropertyFromFile("offlinePaymentModeId"), mGetPropertyFromFile(mode));
				mGenericWait();
				if(mGetPropertyFromFile(mode).equals("Pay by Challan@Bank"))
				{   
					/*if(driver.findElements(By.id(mGetPropertyFromFile("widOutLOIBankId"))).size() != 0)
					{
						mSelectDropDown("id", mGetPropertyFromFile("widOutLOIBankId"), mGetPropertyFromFile("widOutLOINameOfBank"));
						mGenericWait();
						mTakeScreenShot();
					}
					else if(driver.findElements(By.id(mGetPropertyFromFile("widLOIBankId"))).size() != 0)
					{
						mSelectDropDown("id", mGetPropertyFromFile("widLOIBankId"), mGetPropertyFromFile("widLOINameOfBank"));
						mGenericWait();
						mTakeScreenShot();
					}*/
					if(LOIAPPLICABLE)
					{
						mGenericWait();
						mSelectDropDown("id", mGetPropertyFromFile("widLOIBankId"), mGetPropertyFromFile("widLOINameOfBank"));
						mGenericWait();
						mTakeScreenShot();
						/*						if(chllanForRTIServices)
						{
							//proceed button
							mWaitForVisible("id",mGetPropertyFromFile("proceedButtonId"));
							mGenericWait();
							mClick("id", mGetPropertyFromFile("proceedButtonId"));
							mGenericWait();
						}
						 */
						if(chllanForRTIServices)
						{
							//submit button
							mClick("css", mGetPropertyFromFile("bankSubmitButtonId"));
							mGenericWait();
							mTakeScreenShot();
							String SavedMsg = mGetApplicationNo("css",mGetPropertyFromFile("rti_isApplnSavedAssert"));
							String Actmsg = "Proceed to challan generation";
							mAssert(SavedMsg, Actmsg, "Actual Message : "+SavedMsg+"   Expected Message :"+Actmsg);

							//proceed button
							mWaitForVisible("id",mGetPropertyFromFile("proceedButtonId"));
							mGenericWait();
							mClick("id", mGetPropertyFromFile("proceedButtonId"));
							mGenericWait();

						}
						System.out.println();
					}
					else
					{
						mGenericWait();
						//mSelectDropDown("id", mGetPropertyFromFile("widOutLOIBankId"), mGetPropertyFromFile("widOutLOINameOfBank"));
						mSelectDropDown("id", mGetPropertyFromFile("widOutLOIBankId"), mGetPropertyFromFile("widOutLOINameOfBank"));					mGenericWait();

						mGenericWait();
						mTakeScreenShot();

						//submit button

						/*if(chllanForRTIServices)
						{
							mClick("css", mGetPropertyFromFile("bankSubmitButtonId"));
						}

						mTakeScreenShot();
						mGenericWait();

						if(chllanForRTIServices)
						{

							mTakeScreenShot();
							String SavedMsg = mGetApplicationNo("css",mGetPropertyFromFile("rti_isApplnSavedAssert"));
							String Actmsg = "Your RTI Application is saved successfully.";
							mAssert(SavedMsg, Actmsg, "Actual Message : "+SavedMsg+"   Expected Message :"+Actmsg);
						}
					}

					if(chllanForRTIServices)
					{
						//proceed button
						mWaitForVisible("id",mGetPropertyFromFile("proceedButtonId"));
						mGenericWait();
						mClick("id", mGetPropertyFromFile("proceedButtonId"));
						mGenericWait();
					}*/

					}
				}
				//else if(mGetPropertyFromFile(mode).equals("PAY BY CHALLAN@ULB"))
				else if(mGetPropertyFromFile(mode).equalsIgnoreCase("Pay by Challan@Ulb"))
				{
					mGenericWait();

					//submit button 
					if(LOIAPPLICABLE)
					{
						mGenericWait();
						mTakeScreenShot();
						//mWaitForVisible("css",mGetPropertyFromFile("ULBSubmitButtonId"));
						if(chllanForRTIServices)
						{
							mWaitForVisible("css",mGetPropertyFromFile("bankSubmitButtonId"));
							mGenericWait();
							mClick("css", mGetPropertyFromFile("bankSubmitButtonId"));

							//mClick("css", mGetPropertyFromFile("ULBSubmitButtonId"));
							mGenericWait();

							mTakeScreenShot();
							String SavedMsg = mGetApplicationNo("css",mGetPropertyFromFile("rti_isApplnSavedAssert"));
							String Actmsg = "Proceed to challan generation";
							mAssert(SavedMsg, Actmsg, "Actual Message :"+SavedMsg+"  Expected Message :"+Actmsg);

							// proceed button
							mWaitForVisible("id",mGetPropertyFromFile("proceedButtonId"));
							mGenericWait();
							mClick("id", mGetPropertyFromFile("proceedButtonId"));
						}

					}
					else if(chllanForRTIServices) 
					{
						/*mGenericWait();
						mTakeScreenShot();
						//mWaitForVisible("css",mGetPropertyFromFile("ULBSubmitButtonId"));
						mWaitForVisible("css",mGetPropertyFromFile("bankSubmitButtonId"));
						mGenericWait();
						mClick("css", mGetPropertyFromFile("bankSubmitButtonId"));

						//mClick("css", mGetPropertyFromFile("ULBSubmitButtonId"));
						mGenericWait();					

						mTakeScreenShot();
						String SavedMsg = mGetApplicationNo("css",mGetPropertyFromFile("rti_isApplnSavedAssert"));
						String Actmsg = "Your RTI Application is saved successfully.";
						mAssert(SavedMsg, Actmsg, "Actual Message :"+SavedMsg+"  Expected Message :"+Actmsg);

						// proceed button
						mWaitForVisible("id",mGetPropertyFromFile("proceedButtonId"));
						mGenericWait();
						mClick("id", mGetPropertyFromFile("proceedButtonId"));

						//print challan button
						mWaitForVisible("css",mGetPropertyFromFile("rti_printChallanBtnId"));
						mGenericWait();
						mClick("css", mGetPropertyFromFile("rti_printChallanBtnId"));
						mCustomWait(6000);
						newChallanReader();*/
   
					}
					else
					{

					}
				}
				else
				{
					System.out.println("please enter challan payment mode BANK or ULB");
				}
				chllanVerReqForServices=true;
			}
			else if((mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("Department") || mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("Agency")) && mGetPropertyFromFile(paymntMode).equalsIgnoreCase("Pay @ ULB Counter"))
			{
				//Clicking on Pay @ ULB Counter
				mGenericWait();
				mWaitForVisible("id",mGetPropertyFromFile("payULBCounterid"));
				mClick("id", mGetPropertyFromFile("payULBCounterid"));

				mWaitForVisible("id",mGetPropertyFromFile("ulbCounterPaymntModeid"));
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("ulbCounterPaymntModeid"), mGetPropertyFromFile("ulbCounterPaymntMode"));

				String cntrPaymentMode=mGetPropertyFromFile("ulbCounterPaymntMode");
				mGenericWait();
				if(cntrPaymentMode.equalsIgnoreCase("Cash"))
				{
					System.out.println("Cash payment mode is selected");
				}
				else if(cntrPaymentMode.equalsIgnoreCase("Indian Postal Order"))
				{
					mWaitForVisible("id", mGetPropertyFromFile("payToid"));
					mSendKeys("id", mGetPropertyFromFile("payToid"), mGetPropertyFromFile("payTo"));
					mSendKeys("id", mGetPropertyFromFile("atPostOfficeid"), mGetPropertyFromFile("atPostOffice"));
					mClick("name",mGetPropertyFromFile("IPODateid"));
					mDateSelect("name",mGetPropertyFromFile("IPODateid"), mGetPropertyFromFile("IPODate"));
					mSendKeys("id", mGetPropertyFromFile("IPONoid"), mGetPropertyFromFile("IPONo"));
				}
				else
				{
					// for selcting a bank
					mGenericWait();

					//clicking on list of bank
					mClick("id", mGetPropertyFromFile("payAtULBCounterBankid"));

					//selecting a name of bank
					mGenericWait();
					mSelectDropDown("id", mGetPropertyFromFile("payAtULBCounterBankid"),mGetPropertyFromFile("payAtULBCounterBank"));
					mGenericWait();
					String drawnOnbank = mGetPropertyFromFile("payAtULBCounterBank");

					//for selecting account number
					mGenericWait();
					mSendKeys("id",mGetPropertyFromFile("payAtULBCounterAccNoid"),mGetPropertyFromFile("payAtULBCounterAccNo"));
					String accntNo=driver.findElement(By.id(mGetPropertyFromFile("payAtULBCounterAccNoid"))).getAttribute("value");

					mGenericWait();
					mSendKeys("id",mGetPropertyFromFile("payAtULBCounterChqNoid"),mGetPropertyFromFile("payAtULBCounterChqNo"));
					String chqDDPONo=driver.findElement(By.id(mGetPropertyFromFile("payAtULBCounterChqNoid"))).getAttribute("value");

					/// for selecting date

					mTab("id",mGetPropertyFromFile("payAtULBCounterChqNoid"));

					mGenericWait();

					mDateSelect("name",mGetPropertyFromFile("payAtULBCounterChqDtid"), mGetPropertyFromFile("payAtULBCounterChqDt"));
					String chqDDPODate=driver.findElement(By.name(mGetPropertyFromFile("payAtULBCounterChqDtid"))).getAttribute("value");
					driver.findElement(By.name("offline.bmChqDDDate")).sendKeys(Keys.TAB);
					mGenericWait();
				}
				chllanForRTIServices=false;

			}
			else if((mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("Department") || mGetPropertyFromFile("typeOfLogin").equalsIgnoreCase("Agency")) && mGetPropertyFromFile(paymntMode).equalsIgnoreCase("Pay @ Non judicial stamp paper"))
			{
				mClick("id", mGetPropertyFromFile("payAtNonJudclStmpPaperid"));
				mWaitForVisible("id", mGetPropertyFromFile("payAtNonJudclStmpPaperNoid"));
				mSendKeys("id", mGetPropertyFromFile("payAtNonJudclStmpPaperNoid"), mGetPropertyFromFile("payAtNonJudclStmpPaperNo"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("payAtNonJudclStmpPaperAmtid"), mGetPropertyFromFile("payAtNonJudclStmpPaperAmt"));
				mGenericWait();
				mUpload("id", mGetPropertyFromFile("payAtNonJudclStmpPaperScnndCpyUpldid"), mGetPropertyFromFile("payAtNonJudclStmpPaperScnndCpyUpld"));
				chllanForRTIServices=false;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in payment method");
		}

	}

	//Sneha Solaskar
	//method to check whether challan verification is required or not
	public void isChallanVerftnRequired() throws InterruptedException
	{
		try
		{
			//for RTI Services
			if(chllanForRTIServices)
			{
				if(!aplbpl||isOrganiztn)
				{
					//	 int c = 0;
					departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
					challanVerification(modeForChallan,mGetPropertyFromFile("rtiAppFeeId"), "chlanAtULBTypeOfPayModeId", "chlanAtULbNameOfBankId","rti_chlanVerftnAccNo","rti_chlanVerftnCheDDNo","rti_chlanVerftnDate");
					if(modeForChallan.equals("byULB"))
					{

					}
					logOut();
					finalLogOut();
					aplbpl=true;
					isOrganiztn=false;
				}
				else
				{
					System.out.println("challan verification is not required");
				}
			}
			/*
			//For BND services
			if(chllanForBNDServices)
			{
				System.out.println("Class Name is:::"+myClassName);
				if(dateIncr>=0 && dateIncr<=21)
				{
					System.out.println("challan verification is not required");
				}
				else
				{
					departmentLogin("deptUserName","deptPassword");
					challanVerification(modeForChallan,"cfc_challanVerBirthRegCertServiceName", "chlanAtULBPayOrderId", "chlanAtULbNameOfBankId","rti_chlanVerftnAccNo","rti_chlanVerftnCheDDNo","rti_chlanVerftnDate");

					logOut();
					finalLogOut();

				}
			}

			 */
			// for Property tax
			/*if(challanForPROPERTYServices)
			{
				departmentLogin("deptUserName","deptPassword");
				challanVerification(modeForChallan,"cfc_challanVerBirthRegCertServiceName", "chlanAtULBPayOrderId", "chlanAtULbNameOfBankId","rti_chlanVerftnAccNo","rti_chlanVerftnCheDDNo","rti_chlanVerftnDate");
				logOut();
				finalLogOut();
			}*/


		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	//Sneha Solaskar
	//method to check whether challan verification is required or not with service name
	public void isChallanVerftnRequired(String Service) throws InterruptedException
	{
		try
		{

			//For Rent and lease services
			//if(challanForRNLServices)
			if(chllanVerReqForServices)
			{
				departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
				challanVerification(modeForChallan, Service, "chlanAtULBTypeOfPayModeId", "chlanAtULbNameOfBankId","rti_chlanVerftnAccNo","rti_chlanVerftnCheDDNo","rti_chlanVerftnDate");
				System.out.println("modeForChallan"+modeForChallan);
				chllanVerReqForServices=false;
				challanNumber.remove(swapchallnno);
				System.out.println("Challan number arraylist after removing challan number : "+challanNumber);
				logOut();
				finalLogOut();
			}

			/*if(challanForPROPERTYServices)
							{
				departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
				challanVerification(modeForChallan, Service, "chlanAtULBPayOrderId", "chlanAtULbNameOfBankId","rti_chlanVerftnAccNo","rti_chlanVerftnCheDDNo","rti_chlanVerftnDate");
				System.out.println("modeForChallan"+modeForChallan);
				logOut();
				finalLogOut();
			}

			if(challanForMARKETServices)

			{
				departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
				challanVerification(modeForChallan, Service, "chlanAtULBPayOrderId", "chlanAtULbNameOfBankId","rti_chlanVerftnAccNo","rti_chlanVerftnCheDDNo","rti_chlanVerftnDate");
				System.out.println("modeForChallan"+modeForChallan);
				logOut();
				finalLogOut();
			}

			if(chllanForTpServices)
				{
				departmentLogin(mGetPropertyFromFile("tp_MBADeptName"),mGetPropertyFromFile("tp_MBADeptPassword"));
				challanVerification(modeForChallan, Service, "chlanAtULBTypeOfPayModeId", "chlanAtULbNameOfBankId","rti_chlanVerftnAccNo","rti_chlanVerftnCheDDNo","rti_chlanVerftnDate");
				System.out.println("modeForChallan"+modeForChallan);
				logOut();
				finalLogOut();
			}

			//For BND services
			else if(chllanForBNDServices)
			{
				//Check if delivery is by post
				if(isByPost)
				{
					departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
					challanVerification(modeForChallan, Service, "chlanAtULBTypeOfPayModeId", "chlanAtULbNameOfBankId","rti_chlanVerftnAccNo","rti_chlanVerftnCheDDNo","rti_chlanVerftnDate");
					System.out.println("modeForChallan"+modeForChallan);
					noOfCopyForBNDCombo=0;
					isByPost = false;
					//chllanForBNDServices=false;
					chllanVerReqForServices=false;
					logOut();
					finalLogOut();

				}
				else
				{
					if((dateIncr>=0 && dateIncr<=21))
					{
						if(noOfCopyForBNDCombo<2)
						{
							System.out.println("modeForChallan"+modeForChallan);
							System.out.println("challan verification is not required");
						}
						else
						{
							departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
							challanVerification(modeForChallan, Service, "chlanAtULBTypeOfPayModeId", "chlanAtULbNameOfBankId","rti_chlanVerftnAccNo","rti_chlanVerftnCheDDNo","rti_chlanVerftnDate");
							System.out.println("modeForChallan"+modeForChallan);
							noOfCopyForBNDCombo=0;
							//chllanForBNDServices=false;
							chllanVerReqForServices=false;
							logOut();
							finalLogOut();
						}
					}
					else
					{
						departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
						challanVerification(modeForChallan, Service, "chlanAtULBTypeOfPayModeId", "chlanAtULbNameOfBankId","rti_chlanVerftnAccNo","rti_chlanVerftnCheDDNo","rti_chlanVerftnDate");
						System.out.println("modeForChallan"+modeForChallan);
						noOfCopyForBNDCombo=0;
						//chllanForBNDServices=false;
						chllanVerReqForServices=false;
						logOut();
						finalLogOut();
					}
				}

			}*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in isChallanVerftnRequired method");
		}

	}


	//18-11-2015
	//Sunil D Sonmale
	//HTML Challan Reader BND, RTI
	public void newChallanReader()
	{
		try {

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

			if(mGetPropertyFromFile("byBankOrULB").equals("Pay by Challan@Bank"))
			{ 
				// Application No 
				String appno = driver.findElement(By.cssSelector("td.break-word")).getText();
				System.out.println("Application no is " +appno);

				// Challan No
				String challanno = driver.findElement(By.xpath("//tr[2]/td/table/tbody/tr/td")).getText();
				System.out.println("Challan No is"+challanno);
				swapchallnno=challanno;
				swapchallnnolist.add(swapchallnno);
				challanNumber.add(swapchallnno);
				System.out.println("Challan number array : "+challanNumber);

				//Date of application
				String dateofapp = driver.findElement(By.xpath("//td[2]")).getText();
				System.out.println("Date of application is "+dateofapp);

				// Applicant Name
				String applicantname = driver.findElement(By.xpath("//tr[3]/td/table/tbody/tr/td")).getText();
				System.out.println("Name of applicant is "+applicantname);

				String applicantnamecomp = applicantname.replaceAll("\\s", "");

				//Assertion for applicant name
				mAssert(applicantnamecomp, entcompname,"Applicant name does not match"+" Actual Name :"+applicantnamecomp+"  Expected Name:"+entcompname);


				// Challan Amount
				String challanamt = driver.findElement(By.xpath("//tr[6]/td/table/tbody/tr/td")).getText();
				System.out.println("Challan amount is "+challanamt);

				mAssert(appno,appNo,"Application number does not match"+" Actual Number :"+appno+"  Expected Number : "+appNo);

				//mAssert(appNo,appno,"Application number does not match"+"Actual Number :"+appno+"  Expected Number :"+appNo);
				//mAssert(RTIServices.ApplicantName,applicantname,"Application name does not match"+" Actual Name :"+applicantname+"  Expected Name :"+RTIServices.ApplicantName);
				//mAssert("ABCD",applicantname,"Application name does not match"+" Actual Name :"+applicantname+"  Expected Name :"+RTIServices.ApplicantName);

				//	driver.findElement(By.id("btnPrint")).click();
			}
			// Added by Madhuri Dawande 25-05-2016
			else if(mGetPropertyFromFile("byBankOrULB").equals("Pay by Challan@Ulb"))
			{
				// Application No 
				String appno = driver.findElement(By.xpath("/html/body/div/div[1]/table/tbody/tr[1]/td/table/tbody/tr/td[1]")).getText();
				System.out.println("Application no is " +appno);

				// Challan No
				String challanno = driver.findElement(By.xpath("/html/body/div/div[1]/table/tbody/tr[2]/td/table/tbody/tr/td[1]")).getText();
				System.out.println("Challan No is"+challanno);
				swapchallnno=challanno;
				swapchallnnolist.add(swapchallnno);
				challanNumber.add(swapchallnno);
				System.out.println("Challan number array : "+challanNumber);
				
				//Date of application
				String dateofapp = driver.findElement(By.xpath("/html/body/div/div[1]/table/tbody/tr[1]/td/table/tbody/tr/td[2]")).getText();
				System.out.println("Date of application is "+dateofapp);

				// Applicant Name
				String applicantname = driver.findElement(By.xpath("/html/body/div/div[1]/table/tbody/tr[3]/td/table/tbody/tr/td")).getText();
				System.out.println("Name of applicant is "+applicantname);

				// Challan Amount
				challanAmt = driver.findElement(By.xpath("/html/body/div/div[1]/table/tbody/tr[6]/td/table/tbody/tr/td")).getText();
				System.out.println("Challan amount is "+challanAmt);

			}
			driver.close();

			driver.switchTo().window(secondwindow);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in newChallanReader method");
		}
	}

	//Sunil D Sonmale
	//18-11-2015
	////HTML Challan Reader PT

	public void newPtChallanReader()
	{

		try
		{
			Thread.sleep(3000);

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
			System.err.println("In HTML Challan");
			Thread.sleep(5000);	

			mTakeScreenShot();

			Thread.sleep(3000);

			//Challan for Offline-Bank for Property Tax
			if(mGetPropertyFromFile("byBankOrULB").equalsIgnoreCase("Pay by Challan@Bank"))
			{
				//Application No
				String appNum = driver.findElement(By.cssSelector("p")).getText();
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

				//Property No
				String propNum = driver.findElement(By.xpath("//p[2]")).getText();
				System.out.println("Property No is: "+propNum);

				Pattern propertyno = Pattern.compile("[0-9]+");
				Matcher pno = propertyno.matcher(propNum);

				if (pno.find()) {
					propNo = pno.group();
					System.out.println("Property No is: "+propNo);
				}
				else
				{
					System.out.println("Property No not found");
				}

				//Challan No
				String challanNo = driver.findElement(By.xpath("//p[3]")).getText();
				System.out.println("Challan No is: " +challanNo);

				Pattern challanno = Pattern.compile("[0-9]+");
				Matcher cno = challanno.matcher(challanNo);

				if(cno.find()) {
					challanNo = cno.group();
					System.out.println("Challan No is: " +challanNo);
				}
				else
				{
					System.out.println("Challan No not found");
				}

				//Date of Application
				String dateOfApp = driver.findElement(By.xpath("//li[3]/div/p[2]")).getText();
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

				//Applicant Name
				String applicantName = driver.findElement(By.xpath("//tr[3]/td/table/tbody/tr/td")).getText();
				System.out.println("Applicant name is: "+applicantName);

				Pattern nameapp = Pattern.compile("(?<=. )\\s*(.*)\\s*(|\n)");
				Matcher appname = nameapp.matcher(applicantName);

				if (appname.find()) {
					applicantName = appname.group();
					System.out.println("Applicant Name is: " +applicantName);
				}

				//Father Name
				String fatherName = driver.findElement(By.xpath("//tr[4]/td/table/tbody/tr/td")).getText();
				System.out.println("Father Name is: "+fatherName);


				//Code for verifying applicant name and father name

				String compAppName = applicantName+fatherName;

				compAppName = compAppName.replaceAll("\\s", "");

				System.out.println("Complete applicant name is: "+compAppName);

				if(newAssessment)
				{
					entcompname = entcompname.replaceAll("\\s", "");

					System.out.println("Entered name is: " +entcompname);
					System.out.println("Name Printed on Challan is: "+compAppName);


					if (compAppName.equals(entcompname))
					{
						System.out.println("Applicant name matches");				
					}
					else
					{
						System.out.println("Applicant name does not match");
					}

					//mAssert(compApplicantName, entcompname, "Applicant name does not match"+" Actual Name :"+compApplicantName+"  Expected Name: "+entcompname);
					mAssert(compAppName, entcompname, "Applicant name does not match"+" Actual Name :"+compAppName+"  Expected Name: "+entcompname);
				}

				//mAssert(appno,applnno,"Application number does not match"+" Actual Number :"+appno+"  Expected Number : "+applnno);


				//Current Year Net tax due
				String currentYearNet = driver.findElement(By.xpath("//tr[13]/td/table/tbody/tr/td")).getText();
				System.out.println("Current Year Net Dues is: "+currentYearNet);

				//Discount
				String discount = driver.findElement(By.xpath("//tr[14]/td/table/tbody/tr/td")).getText();
				System.out.println("Discount Given is: "+discount);

				//Total Tax Due
				String totalTaxDues = driver.findElement(By.xpath("//tr[15]/td/table/tbody/tr/td")).getText();
				System.out.println("Total Tax Due is: "+totalTaxDues);

				//One Time Water Charges
				String otWaterCharges = driver.findElement(By.xpath("//tr[16]/td/table/tbody/tr/td")).getText();
				System.out.println("One Time Water Charges are: "+otWaterCharges);

				//Amount Payable
				String amtPayable = driver.findElement(By.xpath("//tr[15]/td/table/tbody/tr/td[2]")).getText();
				System.out.println("Total Amount Payable is: "+amtPayable);
			}
			//Challan for Offline-ULB for Property Tax @Ritesh 17-08-2016
			else if(mGetPropertyFromFile("byBankOrULB").equalsIgnoreCase("Pay by Challan@Ulb"))
			{
				//Application No
				String appNum = driver.findElement(By.cssSelector("body > div > div:nth-child(1) > div.logo.clear > ul > li:nth-child(2) > div > p:nth-child(1)")).getText();
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

				//Property No
				String propNum = driver.findElement(By.xpath("/html/body/div/div[1]/div[1]/ul/li[2]/div/p[2]")).getText();
				System.out.println("Property No is: "+propNum);

				Pattern propertyno = Pattern.compile("[0-9]+");
				Matcher pno = propertyno.matcher(propNum);

				if (pno.find()) {
					propNo = pno.group();
					System.out.println("Property No is: "+propNo);
				}
				else
				{
					System.out.println("Property No not found");
				}

				//Challan No
				String challanNo = driver.findElement(By.xpath("/html/body/div/div[1]/div[1]/ul/li[2]/div/p[3]")).getText();
				System.out.println("Challan No is: " +challanNo);

				Pattern challanno = Pattern.compile("[0-9]+");
				Matcher cno = challanno.matcher(challanNo);

				if(cno.find()) {
					challanNo = cno.group();
					System.out.println("Challan No is: " +challanNo);
				}
				else
				{
					System.out.println("Challan No not found");
				}

				//Date of Application
				String dateOfApp = driver.findElement(By.xpath("/html/body/div/div[1]/div[1]/ul/li[4]/div/p[2]")).getText();
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

				//Applicant Name
				String applicantName = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td")).getText();
				System.out.println("Applicant name is: "+applicantName);

				Pattern nameapp = Pattern.compile("(?<=. )\\s*(.*)\\s*(|\n)");
				Matcher appname = nameapp.matcher(applicantName);

				if (appname.find()) {
					applicantName = appname.group();
					System.out.println("Applicant Name is: " +applicantName);
				}

				//Father Name
				String fatherName = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/table/tbody/tr[4]/td/table/tbody/tr/td")).getText();
				System.out.println("Father Name is: "+fatherName);


				//Code for verifying applicant name and father name

				String compAppName = applicantName+fatherName;

				compAppName = compAppName.replaceAll("\\s", "");

				System.out.println("Complete applicant name is: "+compAppName);

				if(newAssessment)
				{

					entcompname = entcompname.replaceAll("\\s", "");

					System.out.println("Entered name is: " +entcompname);
					System.out.println("Name Printed on Challan is: "+compAppName);


					if (compAppName.equals(entcompname))
					{
						System.out.println("Applicant name matches");				
					}
					else
					{
						System.out.println("Applicant name does not match");
					}


					//mAssert(compApplicantName, entcompname, "Applicant name does not match"+" Actual Name :"+compApplicantName+"  Expected Name: "+entcompname);
					mAssert(compAppName, entcompname, "Applicant name does not match"+" Actual Name :"+compAppName+"  Expected Name: "+entcompname);

				}
				//mAssert(appno,applnno,"Application number does not match"+" Actual Number :"+appno+"  Expected Number : "+applnno);


				//Current Year Net tax due
				String currentYearNet = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/table/tbody/tr[13]/td/table/tbody/tr/td[1]")).getText();
				System.out.println("Current Year Net Dues is: "+currentYearNet);

				//Discount
				String discount = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/table/tbody/tr[14]/td/table/tbody/tr/td[1]")).getText();
				System.out.println("Discount Given is: "+discount);

				//Total Tax Due
				String totalTaxDues = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/table/tbody/tr[15]/td/table/tbody/tr/td[1]")).getText();
				System.out.println("Total Tax Due is: "+totalTaxDues);

				//One Time Water Charges
				String otWaterCharges = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/table/tbody/tr[16]/td/table/tbody/tr/td[1]")).getText();
				System.out.println("One Time Water Charges are: "+otWaterCharges);

				//Amount Payable
				String amtPayable = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/table/tbody/tr[15]/td/table/tbody/tr/td[2]")).getText();
				System.out.println("Total Amount Payable is: "+amtPayable);
			}

			newAssessment=false;

			//Close Challan Window
			driver.close();

			//Switch to Previous Window
			driver.switchTo().window(secondwindow);
		}

		catch (Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("newPtChallanReader");
		}

	}

	/*//Sneha Solaskar
	// method for challan verification at bank and ulb
	public void challanVerification(String mode,String Service,String payMode ,String nameOfBank,String accountNo,String ChequeDDNo,String cDate) throws InterruptedException, IOException
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
			//Navigation from CFC link to Transaction to challan verification link
			mNavigation(mGetPropertyFromFile("chlanVerifctnLinkId"), mGetPropertyFromFile("chlanTransactionsId"), mGetPropertyFromFile("challanVerificationId"));

			//new code
			mGenericWait();
			mWaitForVisible("id", mGetPropertyFromFile("chlanServiceId"));
			mClick("id", mGetPropertyFromFile("chlanServiceId"));
<<<<<<< .mine

<<<<<<< .mine

			//	mSelectDropDown("id", mGetPropertyFromFile("chlanServiceId"), Service);

=======
			mSelectDropDown("id", mGetPropertyFromFile("chlanServiceId"), Service);
=======

>>>>>>> .r268
			mSelectDropDown("id", mGetPropertyFromFile("chlanServiceId"), mGetPropertyFromFile(Service));
<<<<<<< .mine

=======
>>>>>>> .r259
>>>>>>> .r268
			mGenericWait();

			mTakeScreenShot();
			if(mGetPropertyFromFile(Service).equals("RTI Application"))
<<<<<<< .mine
=======

			mTakeScreenShot();
			if(mGetPropertyFromFile(Service).equals("RTI Application"))

=======
>>>>>>> .r259

>>>>>>> .r252
			{
				RTI_Application_Fee=true;
				System.out.println("RTI Application");
			}
			else if(mGetPropertyFromFile(Service).equals("RTI LOI Collection"))
			{
				RTI_LOI_Collection=true;
			}
			else
			{
				//System.out.println("i m in BND Services");
			}

			//search button
			mWaitForVisible("css",mGetPropertyFromFile("chlanSearchBtnId"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("chlanSearchBtnId"));
			//mTakeScreenShot();

			mGenericWait();
			mWaitForVisible("id",mGetPropertyFromFile("chlanLeftFormId"));

			//icon 
			mWaitForVisible("css",mGetPropertyFromFile("chlanIconSearchId"));
			mGenericWait();
			mClick("css", mGetPropertyFromFile("chlanIconSearchId"));

			mGenericWait();
			mSelectDropDown("css", mGetPropertyFromFile("chlanSearchById"),mGetPropertyFromFile("chLanByAppId"));

			//final search
			mGenericWait();
			//mClick("css", mGetPropertyFromFile("chlanIconId"));

			mGenericWait();
			driver.findElement(By.id("jqg2")).clear();

			if(chllanForBNDServices)
			{
				mGenericWait();
				mWaitForVisible("id", mGetPropertyFromFile("chalanFindByNoId"));
				mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),BirthAndDeathServices.appNo);
				//mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),"71116042600003");
				mGenericWait();
				mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
				TransctnId=BirthAndDeathServices.appNo;
			}
			if(RTI_LOI_Collection)
			{
				mGenericWait();
				//mSendKeys("id", mGetPropertyFromFile("chalanFindByNoId"),"90");
				mSendKeys("id", mGetPropertyFromFile("chalanFindByNoId"),RTIServices.LOINO);
				mGenericWait();
				mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
				tempTransCount = tempTransCount+1;
				String StrTransCount = String.valueOf(tempTransCount);
				TransctnId = appNo+StrTransCount;	
				RTI_LOI_Collection=false;
			}
<<<<<<< .mine

=======
<<<<<<< .mine

=======
>>>>>>> .r259
>>>>>>> .r268
			if(RTI_Application_Fee)
			{
				mGenericWait();
				System.out.println("Application no is:: ");
				mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),appNo);
				mGenericWait();
				mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
				RTI_Application_Fee=false;
				TransctnId=appNo;
			}
			//for town planning services
			if(chllanForTpServices)
			{
				mGenericWait();
				mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),appNo);
				mGenericWait();
				mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
				TransctnId=appNo;
			}
			if(challanForPROPERTYServices)
			{
				mGenericWait();

				//	mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),"51515122200006");
				mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),appNo);
				mGenericWait();
				mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
				TransctnId=appNo;	
			}
			if(challanForMARKETServices)
			{
				mGenericWait();
				//mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),"51515122200006");
				mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),appNo);
				mGenericWait();
				mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
				TransctnId=appNo;	
			}
<<<<<<< .mine
=======
			if(challanForRNLServices)
			{
				mGenericWait();
				mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),appNo);
				//mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"), "12");
				mGenericWait();
				mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
				TransctnId=appNo;
			}
>>>>>>> .r252


			///My code temporary
			///Sunil D Sonmale 14032016

			mGenericWait();
			mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),"");
			mGenericWait();
			mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
			TransctnId="71116031100003";

			/////////////////////
			/////////////////////
			////////

<<<<<<< .mine

=======
<<<<<<< .mine


=======
>>>>>>> .r259
>>>>>>> .r268
			//search button to search all entries related to selected service
			mGenericWait();
			mWaitForVisible("id", mGetPropertyFromFile("chlanFindIconId"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("chlanFindIconId"));
			mTakeScreenShot();
			mGenericWait();

			//icon 
			mWaitForVisible("css",mGetPropertyFromFile("chlanViewDetailimgId"));
			mGenericWait();
			mTakeScreenShot();
			mClick("css",mGetPropertyFromFile("chlanViewDetailimgId"));

			mGenericWait();
			mSendKeys("id",mGetPropertyFromFile("chlanBankTransId"),TransctnId);

			mTab("id",mGetPropertyFromFile("chlanBankTransId"));
			mGenericWait();

			mGenericWait();
			String tokens[] = mGetPropertyFromFile(cDate).split("/");
			System.out.println("i am selecting a date");
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
			mTakeScreenShot();
			mClick("id",mGetPropertyFromFile("chalanProceedBtnId"));

		}
		else if(mGetPropertyFromFile(mode).contains("ULB")||mGetPropertyFromFile(mode).contains("ulb")||mGetPropertyFromFile(mode).contains("Ulb"))
		{
			//Navigation from CFC link to Transaction to challan verification link
			mNavigation(mGetPropertyFromFile("chlanVerifctnLinkId"), mGetPropertyFromFile("chlanTransactionsId"), mGetPropertyFromFile("chlanAtULBCounterlinkId"));
<<<<<<< .mine

<<<<<<< .mine

=======
			//c f c link
			mWaitForVisible("linkText",mGetPropertyFromFile("chlanVerifctnLinkId"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("chlanVerifctnLinkId"));
=======
>>>>>>> .r259

<<<<<<< .mine
			//transaction link
			mWaitForVisible("linkText",mGetPropertyFromFile("chlanTransactionsId"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("chlanTransactionsId"));

			//challan at ULB link
			mWaitForVisible("linkText",mGetPropertyFromFile("chlanAtULBCounterlinkId"));
			mGenericWait();
			mClick("linkText", mGetPropertyFromFile("chlanAtULBCounterlinkId"));

=======
>>>>>>> .r259
>>>>>>> .r268
			//searching for RTI Services
			if(chllanForRTIServices){
				if(LOIAPPLICABLE)
				{
					mGenericWait();
					mSendKeys("id",mGetPropertyFromFile("chlanAtUlbAppNoId"),RTIServices.LOINO);
					LOIAPPLICABLE=false;
					mGenericWait();
				}
				else
				{	
					mGenericWait();
					mSendKeys("id",mGetPropertyFromFile("chlanAtUlbAppNoId"),appNo);
					mGenericWait();
				}
			}

			//Searching for BND Services
			if(chllanForBNDServices){
				mGenericWait();
				mSendKeys("id",mGetPropertyFromFile("chlanAtUlbAppNoId"),BirthAndDeathServices.appNo);
				mGenericWait();
			}

			//for Town Planning services
			if(chllanForTpServices)
			{
				mGenericWait();
				mSendKeys("id",mGetPropertyFromFile("chlanAtUlbAppNoId"),appNo);
				mGenericWait();
			}

			//searching for Property services
			if(challanForPROPERTYServices)
			{
				mGenericWait();
				mSendKeys("id",mGetPropertyFromFile("chlanAtUlbAppNoId"),appNo);
				mGenericWait();
			}

			//for Rent and Lease services
			if(challanForRNLServices)
			{
				mGenericWait();
				mSendKeys("id",mGetPropertyFromFile("chlanAtUlbAppNoId"),appNo);
				//mSendKeys("id", mGetPropertyFromFile("chlanAtUlbAppNoId"), "71116042200001");
				mGenericWait();
			}

			mClick("css", mGetPropertyFromFile("chlanAtULBsearchBtnId"));
			mGenericWait();
			mTakeScreenShot();

			//selecting payment mode for ULB
			mGenericWait();
			mSelectDropDown("id",mGetPropertyFromFile("chlanAtULBpayModeInId"),mGetPropertyFromFile(payMode));
			mGenericWait();
			if(mGetPropertyFromFile(payMode).equals("Cash"))
			{
				System.out.println("no data required for cash payment");
			}
			else
			{
				//for selcting a bank
				mGenericWait();

				//clicking on list of bank
				mClick("id", mGetPropertyFromFile("chlanAtULBBanklinkId"));

				//selecting a name of bank
				mGenericWait();
				mSelectDropDown("id", mGetPropertyFromFile("chlanAtULBBanklinkId"),mGetPropertyFromFile(nameOfBank));
				mGenericWait();

				//for selecting account number
				mGenericWait();
				mSendKeys("id",mGetPropertyFromFile("chlanAtULBAccNoId"),mGetPropertyFromFile(accountNo));

				mGenericWait();
				mSendKeys("id",mGetPropertyFromFile("chlanAtULBChkId"),mGetPropertyFromFile(ChequeDDNo));

				//for selecting date
				mGenericWait();
				mClick("id", mGetPropertyFromFile("chlanDatPickrByULBId"));
				mGenericWait();

				String[] tokens = mGetPropertyFromFile(cDate).split("/");
				int cuDate=Integer.parseInt(tokens[0]);
				String strDate=Integer.toString(cuDate);

				mGdatePicker(mGetPropertyFromFile("chlanDatPickrByULBId"),tokens[2],tokens[1],strDate);
				mTab("id",mGetPropertyFromFile("chlanDatPickrByULBId"));
				driver.findElement(By.id("offline.bmChqDDDate")).sendKeys(Keys.TAB);
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
			//not to be commented
			mChallanPDFReader(appNo, RTIServices.ApplicantName);
			mGenericWait();
			mSwitchTabs();
			mCustomWait(10000);
		}
		else
		{
			System.out.println("invalid input");
		}
		//mSwitchTabs();
		System.out.println("Mode of challan is :"+mode);
	}*/


	//Sneha Solaskar
	// method for challan verification at bank and ulb
	/*public void challanVerification(String mode,String Service,String payMode ,String nameOfBank,String accountNo,String ChequeDDNo,String cDate) throws InterruptedException, IOException
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

				//mClick("id", mGetPropertyFromFile("chlanServiceId"));



				mSelectDropDown("id", mGetPropertyFromFile("chlanServiceId"), Service);



				if(Service.equals("RTI LOI Collection")) 
				{ 
					RTI_LOI_Collection=true;
				}

				System.out.println("RTI_LOI_Collection value is:: "+RTI_LOI_Collection);
				mGenericWait();
				mTakeScreenShot();

				//search button
				mWaitForVisible("css",mGetPropertyFromFile("chlanSearchBtnId"));
				mGenericWait();
				mClick("css", mGetPropertyFromFile("chlanSearchBtnId"));

				mCustomWait(8000);

				mTakeScreenShot();

				mGenericWait();
				//				mWaitForVisible("id",mGetPropertyFromFile("chlanLeftFormId"));

				//icon 
				mWaitForVisible("css",mGetPropertyFromFile("chlanIconSearchId"));
				mGenericWait();
				mClick("css", mGetPropertyFromFile("chlanIconSearchId"));


				mGenericWait();
				mSelectDropDown("css", mGetPropertyFromFile("chlanSearchById"),mGetPropertyFromFile("chLanByAppId"));

				//final search
				mGenericWait();
				//mClick("css", mGetPropertyFromFile("chlanIconId"));

				driver.findElement(By.id("jqg2")).clear();

				if(chllanVerReqForServices)
				{
					mGenericWait();
					//mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),BirthAndDeathServices.appNo);


					Robot robot=new Robot();
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					//mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
					//TransctnId=BirthAndDeathServices.appNo;



					//if(Service.equals("RTI LOI Collection")) 
					if(LOIAPPLICABLE)//swap
					{
						if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
						{
							System.out.println("RTI_LOI_Collection value is:: "+RTI_LOI_Collection);
							System.out.println("LOI NO IS "+LOINO);
							//mSendKeys("id", mGetPropertyFromFile("rti_Response2SearchByNoId"), loiNumber.get(CurrentinvoCount));
							mSendKeys("id", mGetPropertyFromFile("rti_Response2SearchByNoId"), LOINO);
						}
						else if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual"))
						{									
							mSendKeys("id", mGetPropertyFromFile("rti_ResponseSearchByNoId"), mGetPropertyFromFile("applicationNo"));
						}

						Robot robot=new Robot();
						robot.keyPress(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_TAB);
					}

					else
					{
						IndOrDep("id", "chalanFindByNoId", "applicationNo");

						Robot robot=new Robot();
						robot.keyPress(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_TAB);

						mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
						//TransctnId=BirthAndDeathServices.appNo;



						if(mGetPropertyFromFile("TypeOfExecution").equals("dependent"))
						{
							TransctnId=applicationNo.get(CurrentinvoCount);
						}
						else
						{
							TransctnId=mGetPropertyFromFile("applicationNo");
						}
					}


				}
				if(RTI_LOI_Collection)
				{

					mGenericWait();
					//mSendKeys("id", mGetPropertyFromFile("chalanFindByNoId"),"90");
					//	mSendKeys("id", mGetPropertyFromFile("chalanFindByNoId"),RTIServices.LOINO); 

					System.out.println("Loi number array is"+loiNumber);

										if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
					{
						mSendKeys("id", mGetPropertyFromFile("rti_ResponseSearchByNoId"), loiNumber.get(CurrentinvoCount));
					}
					else if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual"))
					{		
						mSendKeys("id", mGetPropertyFromFile("rti_ResponseSearchByNoId"), mGetPropertyFromFile("applicationNo"));
					}
					 					
					//mSendKeys("id", mGetPropertyFromFile("chalanFindByNoId"),LOINO);

					Robot robot=new Robot();
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);

					mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
					tempTransCount = tempTransCount+1;
					String StrTransCount = String.valueOf(tempTransCount);
					TransctnId = applicationNo.get(CurrentinvoCount)+StrTransCount;	
					RTI_LOI_Collection=false;
				}
				if(RTI_Application_Fee)
				{
					mGenericWait();
					//mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),RTIServices.applnno);
					IndOrDep("id", "chalanFindByNoId", "applicationNo");
					mGenericWait();
					mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
					RTI_Application_Fee=false;
					//TransctnId=RTIServices.applnno;
					TransctnId=applicationNo.get(CurrentinvoCount);
					if(mGetPropertyFromFile("TypeOfExecution").equals("dependent"))
					{
						TransctnId=applicationNo.get(CurrentinvoCount);
					}
					else
					{
						TransctnId=mGetPropertyFromFile("applicationNo");
					}
				}
				//for town planning services
					if(chllanForTpServices)
				{
					mGenericWait();
					mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),appNo);
					mGenericWait();
					mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
					TransctnId=appNo;
				}

				if(challanForPROPERTYServices)
				{
					mGenericWait();

					//	mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),"51515122200006");

					//mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),appNo);
					IndOrDep("id", "chalanFindByNoId", "applicationNo");
					mGenericWait();
					mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
					//TransctnId=appNo;
					TransctnId=applicationNo.get(CurrentinvoCount);
					if(mGetPropertyFromFile("TypeOfExecution").equals("dependent"))
					{
						TransctnId=applicationNo.get(CurrentinvoCount);
					}
					else
					{
						TransctnId=mGetPropertyFromFile("applicationNo");
					}

				}

				if(challanForMARKETServices)
				{
					mGenericWait();

					//	mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),"51515122200006");

					mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"), appNo);
					mGenericWait();
					mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
					TransctnId=appNo;	
				}


				//search button to search all entries related to selected service
				mGenericWait();
				mWaitForVisible("id", mGetPropertyFromFile("chlanFindIconId"));
				mGenericWait();
				mClick("id", mGetPropertyFromFile("chlanFindIconId"));
				mTakeScreenShot();
				mGenericWait();

				//icon 
				//mWaitForVisible("css",mGetPropertyFromFile("chlanViewDetailimgId"));
				mCustomWait(2000);
				mTakeScreenShot();
				System.out.println("this is the excel id which is creating problem.. "+mGetPropertyFromFile("chlanViewDetailimgId"));
				mClick("xpath", mGetPropertyFromFile("chlanViewDetailimgId"));

				mGenericWait();
				mSendKeys("id",mGetPropertyFromFile("chlanBankTransId"),TransctnId);

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
				if(chllanForRTIServices){
					if(LOIAPPLICABLE)
					{
						mGenericWait();
						mSendKeys("id",mGetPropertyFromFile("chlanAtUlbAppNoId"),LOINO);

						LOIAPPLICABLE=false;
						mGenericWait();

					}
					else
					{	
						mGenericWait();
						//mSendKeys("id",mGetPropertyFromFile("chlanAtUlbAppNoId"),RTIServices.applnno);
						IndOrDep("id", "chlanAtUlbAppNoId", "applicationNo");
						mGenericWait();
					}
				}

				//Searching for BND Services
				if(chllanVerReqForServices){
					mGenericWait();
					//mSendKeys("id",mGetPropertyFromFile("chlanAtUlbAppNoId"),BirthAndDeathServices.appNo);
					IndOrDep("id", "chlanAtUlbAppNoId", "applicationNo");
					//	driver.findElement(By.id("applicationNo")).sendKeys(appNo);
					mGenericWait();
				}

				//for Town Planning services
				if(chllanForTpServices)
				{
					mGenericWait();
					mSendKeys("id",mGetPropertyFromFile("chlanAtUlbAppNoId"),appNo);
					mGenericWait();
				}

				//searching for Property services
				if(challanForPROPERTYServices){
					mGenericWait();
					//mSendKeys("id",mGetPropertyFromFile("chlanAtUlbAppNoId"),appNo);
					IndOrDep("id", "chlanAtUlbAppNoId", "applicationNo");
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

					//clicking on list of bank
					//mClick("id", mGetPropertyFromFile("chlanAtULBBanklinkId"));

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
					//mWaitForVisible("id", mGetPropertyFromFile("chlanDatPickrByULBId"));
					//mClick("id", mGetPropertyFromFile("chlanDatPickrByULBId"));
					mGenericWait();

					String[] tokens = mGetPropertyFromFile(cDate).split("/");

					/*String[] tokens = mGetPropertyFromFile(cDate).split("/");

				int cuDate=Integer.parseInt(tokens[0]);
				String strDate=Integer.toString(cuDate);

				mGdatePicker(mGetPropertyFromFile("chlanDatPickrByULBId"),tokens[2],tokens[1],strDate);
				mTab("id",mGetPropertyFromFile("chlanDatPickrByULBId"));
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
				//not to be commented
				mChallanPDFReader(RTIServices.applnno, RTIServices.ApplicantName);
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
	}*/
	
	
	public void challanVerification(String mode,String Service,String payMode ,String nameOfBank,String accountNo,String ChequeDDNo,String cDate) throws InterruptedException, IOException
	{	

		try
		{
			System.out.println(mGetPropertyFromFile(cDate));
			boolean RTI_Application_Fee = false;
			boolean RTI_LOI_Collection = false;
			String TransctnId = null;
			String TransctnIdDummy="A";
			//modeForChallan=mode;
			// for bank
			System.out.println("Class Name is:::"+myClassName);
			if(mGetPropertyFromFile(mode).contains("BANK")||mGetPropertyFromFile(mode).contains("bank")||mGetPropertyFromFile(mode).contains("Bank"))
			{

				mNavigation("chlanVerifctnLinkId", "chlanTransactionsId", "challanVerificationId");

				//new code
				mGenericWait();
				mWaitForVisible("id", mGetPropertyFromFile("chlanServiceId"));
				//mWaitForInvisible("id",mGetPropertyFromFile("Challllanid"));
				//mClick("id", mGetPropertyFromFile("chlanServiceId"));
				
				if(myClassName.equals("RTIServices"))
				{
				if (rtiapplication.equalsIgnoreCase("true")) {
					System.out.println("newchallanereader method is used==================");					
					rtiapplication="false";
				}
				/*else {
					api.PdfPatterns.patternChallanPdf(output);					
				}*/



				///selecting by challn no
				if (mGetPropertyFromFile("Searchbychaalnnobank").equalsIgnoreCase("yes")) {
					System.out.println("swapchallnno=>"+swapchallnno);
					driver.findElement(By.id("entity.challanNo")).sendKeys(swapchallnno);
					//	mSendKeys("id",mGetPropertyFromFile("Challllanid"),swapchallnno);
					System.out.println("swapchallnno=>"+swapchallnno);
					mWaitForVisible("css",mGetPropertyFromFile("chlanSearchBtnId"));
					mGenericWait();
					mClick("css", mGetPropertyFromFile("chlanSearchBtnId"));

					mCustomWait(8000);

					mTakeScreenShot();
					challannosearch="true";

					mGenericWait();
					if(Service.equals("RTI LOI Collection")) 
					{ 
						RTI_LOI_Collection=true;
					}
					mWaitForVisible("css",mGetPropertyFromFile("chalnfindbtn"));
					mGenericWait();
					mClick("css", mGetPropertyFromFile("chalnfindbtn"));

					mGenericWait();

					mGenericWait();
					mSelectDropDown("css", mGetPropertyFromFile("chlanSearchById"),mGetPropertyFromFile("chLanBychalanid"));

					//final search
					mGenericWait();


				}else {	
					///selecting challan dropdown

					mSelectDropDown("id", mGetPropertyFromFile("chlanServiceId"), Service);



					if(Service.equals("RTI LOI Collection")) 
					{ 
						RTI_LOI_Collection=true;
					}

					System.out.println("RTI_LOI_Collection value is:: "+RTI_LOI_Collection);
					mGenericWait();
					mTakeScreenShot();
					mWaitForVisible("css",mGetPropertyFromFile("chlanSearchBtnId"));
					mGenericWait();
					mClick("css", mGetPropertyFromFile("chlanSearchBtnId"));

					mCustomWait(8000);

					mTakeScreenShot();

					mGenericWait();

					mGenericWait();
					//////swap
					mWaitForVisible("css",mGetPropertyFromFile("chalnfindbtn"));
					mGenericWait();
					mClick("css", mGetPropertyFromFile("chalnfindbtn"));

					mGenericWait();

					mGenericWait();
					//mSelectDropDown("css", mGetPropertyFromFile("chlanSearchById"),mGetPropertyFromFile("chLanBychalanid"));

					
					
					
					
					mSelectDropDown("css", mGetPropertyFromFile("chlanSearchById"),mGetPropertyFromFile("chLanByAppId"));

					//final search
					mGenericWait();
					//mClick("css", mGetPropertyFromFile("chlanIconId"));

					driver.findElement(By.id("jqg2")).clear();
				}
				//search button

				//				mWaitForVisible("id",mGetPropertyFromFile("chlanLeftFormId"));

				//icon 
				/*mWaitForVisible("css",mGetPropertyFromFile("chlanIconSearchId"));
				mGenericWait();
				mClick("css", mGetPropertyFromFile("chlanIconSearchId"));*/

				}

				if(chllanVerReqForServices)
				{
					mGenericWait();
					//mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),BirthAndDeathServices.appNo);


					/*Robot robot=new Robot();
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);*/
					//mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
					//TransctnId=BirthAndDeathServices.appNo;



					//if(Service.equals("RTI LOI Collection")) 
					if(LOIAPPLICABLE)//swap
					{
						if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
						{
							System.out.println("RTI_LOI_Collection value is:: "+RTI_LOI_Collection);
							System.out.println("LOI NO IS "+LOINO);
							//mSendKeys("id", mGetPropertyFromFile("rti_Response2SearchByNoId"), loiNumber.get(CurrentinvoCount));

							if (challannosearch.equalsIgnoreCase("true")) {

								mSendKeys("id", mGetPropertyFromFile("rti_Response21SearchByNoId"), swapchallnno);
							}else {
								mSendKeys("id", mGetPropertyFromFile("rti_Response2SearchByNoId"), LOINO);	
							}





						}
						else if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual"))
						{									
							mSendKeys("id", mGetPropertyFromFile("rti_ResponseSearchByNoId"), mGetPropertyFromFile("applicationNo"));
						}

						Robot robot=new Robot();
						robot.keyPress(KeyEvent.VK_TAB);
						robot.keyRelease(KeyEvent.VK_TAB);
					}

					else
					{

				//		if (challannosearch.equalsIgnoreCase("true")) 
							if (mGetPropertyFromFile("Searchbychaalnnobank").equalsIgnoreCase("yes"))
						{
						//	IndOrDep("id", "rti_Response21SearchByNoId", "applicationNo");
						mSendKeys("id", mGetPropertyFromFile("bankChallanNoid"), swapchallnno);
							mTab("id",mGetPropertyFromFile("bankChallanNoid"));
							Robot robot=new Robot();
							robot.keyPress(KeyEvent.VK_TAB);
							robot.keyRelease(KeyEvent.VK_TAB);

							//	mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
						//	mTab("id",mGetPropertyFromFile("rti_Response21SearchByNoId"));
							
							if(mGetPropertyFromFile("typeOfExecution").equals("dependent"))
							{
								//TransctnId=applicationNo.get(CurrentinvoCount);
								TransctnId=challanNumber.get(CurrentinvoCount).toString();
								System.out.println("TransctnId is : "+TransctnId);
								TransctnId=TransctnId+""+TransctnIdDummy;
								System.out.println("TransctnId is : "+TransctnId);
							}
							else
							{
								TransctnId=mGetPropertyFromFile("applicationNo");
							}
							
							mClick("css", mGetPropertyFromFile("chlanSearchBtnId"));
							
							
						}else {
							
							mSelectDropDown("id", mGetPropertyFromFile("chlanServiceId"), Service);
							mGenericWait();

							mTakeScreenShot();
							
							//search button
							mWaitForVisible("css",mGetPropertyFromFile("chlanSearchBtnId"));
							mGenericWait();
							mClick("css", mGetPropertyFromFile("chlanSearchBtnId"));
							//mTakeScreenShot();

							/*mGenericWait();
							mWaitForVisible("id",mGetPropertyFromFile("chlanLeftFormId"));*/

							//icon 
							mWaitForVisible("css",mGetPropertyFromFile("chlanIconSearchId"));
							mGenericWait();
							mClick("css", mGetPropertyFromFile("chlanIconSearchId"));


							mGenericWait();
							mSelectDropDown("css", mGetPropertyFromFile("chlanSearchById"),mGetPropertyFromFile("chLanByAppId"));

							//final search
							mGenericWait();
							//mClick("css", mGetPropertyFromFile("chlanIconId"));

							mGenericWait();
							driver.findElement(By.id("jqg2")).clear();
							
							IndOrDep("id", "chalanFindByNoId", "applicationNo");
							Robot robot=new Robot();
							robot.keyPress(KeyEvent.VK_TAB);
							robot.keyRelease(KeyEvent.VK_TAB);

							mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
							//mTab("id",mGetPropertyFromFile("rti_Response21SearchByNoId"));
							if(mGetPropertyFromFile("TypeOfExecution").equals("dependent"))
							{
								//TransctnId=applicationNo.get(CurrentinvoCount);
								TransctnId=challanNumber.get(CurrentinvoCount).toString();
								TransctnId=TransctnId+""+TransctnIdDummy;
							}
							else
							{
								TransctnId=mGetPropertyFromFile("applicationNo");
							}
							
							//search button to search all entries related to selected service
							mGenericWait();
							mWaitForVisible("id", mGetPropertyFromFile("chlanFindIconId"));
							mGenericWait();
							mClick("id", mGetPropertyFromFile("chlanFindIconId"));
							mTakeScreenShot();
							mGenericWait();
							//	mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
						}


						//IndOrDep("id", "chalanFindByNoId", "applicationNo");
						//


						//TransctnId=BirthAndDeathServices.appNo;

						

						
					}


				}
				if(RTI_LOI_Collection)
				{

					mGenericWait();
					//mSendKeys("id", mGetPropertyFromFile("chalanFindByNoId"),"90");
					//	mSendKeys("id", mGetPropertyFromFile("chalanFindByNoId"),RTIServices.LOINO); 

					System.out.println("Loi number array is"+loiNumber);

					/*					if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
					{
						mSendKeys("id", mGetPropertyFromFile("rti_ResponseSearchByNoId"), loiNumber.get(CurrentinvoCount));
					}
					else if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("individual"))
					{		
						mSendKeys("id", mGetPropertyFromFile("rti_ResponseSearchByNoId"), mGetPropertyFromFile("applicationNo"));
					}
					 */					
					//mSendKeys("id", mGetPropertyFromFile("chalanFindByNoId"),LOINO);

					Robot robot=new Robot();
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					if (challannosearch.equalsIgnoreCase("true")) {

						mTab("id",mGetPropertyFromFile("rti_Response21SearchByNoId"));
					}else {
						mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
					}

					tempTransCount = tempTransCount+1;
					String StrTransCount = String.valueOf(tempTransCount);
					TransctnId = applicationNo.get(CurrentinvoCount)+StrTransCount;	
					RTI_LOI_Collection=false;
				}
				if(RTI_Application_Fee)
				{
					mGenericWait();
					//mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),RTIServices.applnno);
					if (challannosearch.equalsIgnoreCase("true")) {
						System.out.println("swapchallnno==>"+swapchallnno);
						mSendKeys("id", mGetPropertyFromFile("rti_Response21SearchByNoId"),swapchallnno);
						mTab("id",mGetPropertyFromFile("rti_Response21SearchByNoId"));
					}else {
						System.out.println("im in else part");
						IndOrDep("id", "chalanFindByNoId", "applicationNo");
						mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
					}
					
					mGenericWait();
					mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
					RTI_Application_Fee=false;
					//TransctnId=RTIServices.applnno;
					TransctnId=applicationNo.get(CurrentinvoCount);
					if(mGetPropertyFromFile("typeOfExecution").equals("dependent"))
					{
						TransctnId=applicationNo.get(CurrentinvoCount).toString();
						TransctnId=TransctnId+""+TransctnIdDummy;
						
					}
					else
					{
						TransctnId=mGetPropertyFromFile("applicationNo");
					}
				}
				//for town planning services
				/*	if(chllanForTpServices)
				{
					mGenericWait();
					mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),appNo);
					mGenericWait();
					mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
					TransctnId=appNo;
				}

				if(challanForPROPERTYServices)
				{
					mGenericWait();

					//	mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),"51515122200006");

					//mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),appNo);
					IndOrDep("id", "chalanFindByNoId", "applicationNo");
					mGenericWait();
					mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
					//TransctnId=appNo;
					TransctnId=applicationNo.get(CurrentinvoCount);
					if(mGetPropertyFromFile("TypeOfExecution").equals("dependent"))
					{
						TransctnId=applicationNo.get(CurrentinvoCount);
					}
					else
					{
						TransctnId=mGetPropertyFromFile("applicationNo");
					}

				}

				if(challanForMARKETServices)
				{
					mGenericWait();

					//	mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"),"51515122200006");

					mSendKeys("id",  mGetPropertyFromFile("chalanFindByNoId"), appNo);
					mGenericWait();
					mTab("id",mGetPropertyFromFile("chalanFindByNoId"));
					TransctnId=appNo;	
				}*/


				/*//search button to search all entries related to selected service
				mGenericWait();
				mWaitForVisible("id", mGetPropertyFromFile("chlanFindIconId"));
				mGenericWait();
				mClick("id", mGetPropertyFromFile("chlanFindIconId"));
				mTakeScreenShot();
				mGenericWait();*/

				//icon 
				//mWaitForVisible("css",mGetPropertyFromFile("chlanViewDetailimgId"));
				mCustomWait(2000);
				mTakeScreenShot();
				System.out.println("this is the excel id which is creating problem.. "+mGetPropertyFromFile("chlanViewDetailimgId"));
				mClick("xpath", mGetPropertyFromFile("chlanViewDetailimgId"));

				mGenericWait();
				mSendKeys("id",mGetPropertyFromFile("chlanBankTransId"),TransctnId);

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
				if(chllanForRTIServices){
					if(LOIAPPLICABLE)
					{
						
						if (rtiapplication.equalsIgnoreCase("true")) {
							System.out.println("swapchallnno==>"+swapchallnno);
							mSendKeys("id", mGetPropertyFromFile("RTI_ulbchalan_id"),swapchallnno);
						
						}else {
							System.out.println("im in else part");

							mGenericWait();
							mSendKeys("id",mGetPropertyFromFile("chlanAtUlbAppNoId"),LOINO);
						}
						
						LOIAPPLICABLE=false;
						mGenericWait();

					}
					else
					{	
						mGenericWait();
						//mSendKeys("id",mGetPropertyFromFile("chlanAtUlbAppNoId"),RTIServices.applnno);

						if (rtiapplication.equalsIgnoreCase("true"))//swap challan
						{
							System.out.println("iam in search by challan at loi time");
							System.out.println("swapchallnno==>"+swapchallnno);
							mSendKeys("id", mGetPropertyFromFile("RTI_ulbchalan_id"),swapchallnno);
							chllanVerReqForServices=false;
						
						}else {
							System.out.println("im in else part");

							mGenericWait();
							//mSendKeys("id",mGetPropertyFromFile("chlanAtUlbAppNoId"),LOINO);
							IndOrDep("id", "chlanAtUlbAppNoId", "applicationNo");
						}
											
						mGenericWait();
					}
				}

				//Searching for BND Services
				if(chllanVerReqForServices){
					mGenericWait();
					
					/*System.out.println("====================================@@@@@@============================");
					//mSendKeys("id",mGetPropertyFromFile("chlanAtUlbAppNoId"),BirthAndDeathServices.appNo);
					IndOrDep("id", "chlanAtUlbAppNoId", "applicationNo");
					//	driver.findElement(By.id("applicationNo")).sendKeys(appNo);
					mGenericWait();
				}
				///swap code to clear app and search by chaal rti specific
				if (chllanVerReqForServices && mGetPropertyFromFile("Searchbychaalnnobank").equalsIgnoreCase("yes")) {
					driver.findElement(By.id(mGetPropertyFromFile("chlanAtUlbAppNoId"))).clear();
					System.out.println("challan at ulb loi RTI time search by challan no@");
					api.PdfPatterns.patternChallanPdf(output);
					System.out.println("swapchallnno==>"+swapchallnno);
					mSendKeys("id", mGetPropertyFromFile("RTI_ulbchalan_id"),swapchallnno);
				}*/
				
				if (mGetPropertyFromFile("Searchbychaalnnobank").equalsIgnoreCase("yes"))
				{
					System.out.println("swapchallnno"+swapchallnno);
					mSendKeys("id", mGetPropertyFromFile("RTI_ulbchalan_id"),swapchallnno);
					//driver.findElement(By.id(mGetPropertyFromFile("RTI_ulbchalan_id"))).sendKeys(swapchallnno);
				}
				else
				{
					IndOrDep("id", "chlanAtUlbAppNoId", "applicationNo");
					//	driver.findElement(By.id("applicationNo")).sendKeys(appNo);
					mGenericWait();
				}
				
			}

				/*//for Town Planning services
				if(chllanForTpServices)
				{
					mGenericWait();
					mSendKeys("id",mGetPropertyFromFile("chlanAtUlbAppNoId"),appNo);
					mGenericWait();
				}

				//searching for Property services
				if(challanForPROPERTYServices){
					mGenericWait();
					//mSendKeys("id",mGetPropertyFromFile("chlanAtUlbAppNoId"),appNo);
					IndOrDep("id", "chlanAtUlbAppNoId", "applicationNo");
					mGenericWait();
				}*/


				mClick("css", mGetPropertyFromFile("chlanAtULBsearchBtnId"));
				mCustomWait(8000);
				mGenericWait();
				mTakeScreenShot();

				//selecting payment mode for ULB
				mGenericWait(); 
				System.out.println("========>"+mGetPropertyFromFile("chlanAtULBpayModeInId"));
				System.out.println("========>"+mGetPropertyFromFile(payMode));
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

					//clicking on list of bank
					//mClick("id", mGetPropertyFromFile("chlanAtULBBanklinkId"));

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
					//mWaitForVisible("id", mGetPropertyFromFile("chlanDatPickrByULBId"));
					//mClick("id", mGetPropertyFromFile("chlanDatPickrByULBId"));
					mGenericWait();

					/*String[] tokens = mGetPropertyFromFile(cDate).split("/");

					/*String[] tokens = mGetPropertyFromFile(cDate).split("/");

				int cuDate=Integer.parseInt(tokens[0]);
				String strDate=Integer.toString(cuDate);

				mGdatePicker(mGetPropertyFromFile("chlanDatPickrByULBId"),tokens[2],tokens[1],strDate);
				mTab("id",mGetPropertyFromFile("chlanDatPickrByULBId"));*/
					mCustomWait(2000);
					mDateSelect("name",mGetPropertyFromFile("chlanDatPickrByULBId"), mGetPropertyFromFile(cDate));
					String chqDDPODate=driver.findElement(By.name(mGetPropertyFromFile("chlanDatPickrByULBId"))).getAttribute("value");
					driver.findElement(By.name("offline.bmChqDDDate")).sendKeys(Keys.TAB);
					mGenericWait();
				}
				mTakeScreenShot();
				mscroll(768, 795);
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
				//not to be commented
				/*mChallanPDFReader(RTIServices.applnno, RTIServices.ApplicantName);*/
				mGenericWait();
				//mSwitchTabs();
				mCustomWait(10000);
				mChallanPDFReader();
				

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

	//upload documents
	public void uploaddocument()
	{
		try
		{
			if(	driver.findElements(By.id(mGetPropertyFromFile("cmUploadDoc0id"))).size() != 0)
			{
				mGenericWait();
				mUpload("id",mGetPropertyFromFile("cmUploadDoc0id"),mGetPropertyFromFile("uploadPath2"));
			}
			mGenericWait();

			if(	driver.findElements(By.id(mGetPropertyFromFile("cmUploadDoc1id"))).size() != 0)
			{
				mUpload("id",mGetPropertyFromFile("cmUploadDoc1id"),mGetPropertyFromFile("uploadPath2"));
			}
			mGenericWait();

			if(	driver.findElements(By.id(mGetPropertyFromFile("cmUploadDoc2id"))).size() != 0)
			{
				mUpload("id",mGetPropertyFromFile("cmUploadDoc2id"),mGetPropertyFromFile("uploadPath2"));
			}
			mGenericWait();

			if(	driver.findElements(By.id(mGetPropertyFromFile("cmUploadDoc3id"))).size() != 0)
			{
				mUpload("id",mGetPropertyFromFile("cmUploadDoc3id"),mGetPropertyFromFile("uploadPath2"));
			}
			mGenericWait();

			if(	driver.findElements(By.id(mGetPropertyFromFile("cmUploadDoc4id"))).size() != 0)
			{
				mUpload("id",mGetPropertyFromFile("cmUploadDoc4id"),mGetPropertyFromFile("uploadPath2"));
			}
			mGenericWait();
			if(	driver.findElements(By.id(mGetPropertyFromFile("cmUploadDoc5id"))).size() != 0)
			{
				mUpload("id",mGetPropertyFromFile("cmUploadDoc5id"),mGetPropertyFromFile("uploadPath2"));
			}
			mGenericWait();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in uploaddocument method");
		}
	}


	//method to print LOI
	public void cfcPrinterCounter()
	{
		try
		{
			//C F C link
			mGenericWait();
			mWaitForVisible("linkText", mGetPropertyFromFile("cfc_PcCFCLinkid"));
			mCustomWait(2000);
			mClick("linkText", mGetPropertyFromFile("cfc_PcCFCLinkid"));


			//Transaction link
			mCustomWait(2000);
			mWaitForVisible("linkText", mGetPropertyFromFile("cfc_PcTransactionLinkid"));
			mClick("linkText", mGetPropertyFromFile("cfc_PcTransactionLinkid"));
			mCustomWait(2000);

			//Printer Counter link
			mWaitForVisible("linkText", mGetPropertyFromFile("cfc_PcPrinterCounterLinkid"));
			mClick("linkText", mGetPropertyFromFile("cfc_PcPrinterCounterLinkid"));
			mCustomWait(2000);

			//seach application
			mWaitForVisible("css", mGetPropertyFromFile("cfc_PcFIndAppIconid"));
			mCustomWait(2000);
			mClick("css", mGetPropertyFromFile("cfc_PcFIndAppIconid"));

			//sending application for search
			mWaitForVisible("id", mGetPropertyFromFile("cfc_PcSendingAppNoTextid"));

			IndOrDep("id", "cfc_PcSendingAppNoTextid", "applicationNo");
			
			/*mSendKeys("id", mGetPropertyFromFile("cfc_PcSendingAppNoTextid"), appNo);
			//mSendKeys("id", mGetPropertyFromFile("cfc_PcSendingAppNoTextid"), "10116040700002");
*/
			mCustomWait(2000);
			mTab("id", mGetPropertyFromFile("cfc_PcSendingAppNoTextid"));
			mCustomWait(2000);
			mTakeScreenShot();
			mCustomWait(2000);

			//finding application
			mWaitForVisible("id", mGetPropertyFromFile("cfc_PcGridFormSearchBtnid"));
			mClick("id", mGetPropertyFromFile("cfc_PcGridFormSearchBtnid"));
			mCustomWait(2000);

			//print application Link
			mWaitForVisible("css", mGetPropertyFromFile("cfc_PcPrintApplicationid"));
			mCustomWait(2000);
			mClick("css", mGetPropertyFromFile("cfc_PcPrintApplicationid"));
			mGenericWait();
			mChallanPDFReader();

		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in cfcPrinterCounter method");
		}
	}



	public void paymentGateway(String pymtGtway)
	{
		try
		{
			if (pymtGtway.equals("MAHAONLINE"))
			{

				mWaitForVisible("id", mGetPropertyFromFile("pg_selectPymtGtwayid"));
				mSelectDropDown("id", mGetPropertyFromFile("pg_selectPymtGtwayid"), mGetPropertyFromFile("pg_mahaonlineSelectPymtGtway"));
				mGenericWait();
				mClick("id", mGetPropertyFromFile("pg_payButtonid"));
				mWaitForVisible("id", mGetPropertyFromFile("pg_yesBankNetBankingid"));
				mClick("id", mGetPropertyFromFile("pg_yesBankNetBankingid"));

				mClick("id", mGetPropertyFromFile("pg_proceedForPaymentBtnid"));

				mClick("linkText", mGetPropertyFromFile("pg_netBankingLinkid"));
				mSelectDropDown("id", mGetPropertyFromFile("pg_selectBankid"), mGetPropertyFromFile("pg_selectBank"));
				driver.findElement(By.id("paymentProcess")).click();
				mClick("id", mGetPropertyFromFile("pg_payNowBtnid"));
				driver.findElement(By.name("success")).click();
				mClick("name", mGetPropertyFromFile("pg_successOptBtnid"));

				mClick("css", mGetPropertyFromFile("pg_clickToTransferFundsBtnid"));
				//assertTrue(closeAlertAndGetItsText().matches("^url : http://203\\.114\\.240\\.183/paynetz/atom[\\s\\S]ITC=341963&BID=3419631&ClientCode=007&amt=8\\.0000&Status=S$"));
				driver.findElement(By.linkText("Finish")).click();


			}
			else if(pymtGtway.equals("TECHPROCESS"))
			{
				mWaitForVisible("id", mGetPropertyFromFile("pg_selectPymtGtwayid"));
				mSelectDropDown("id", mGetPropertyFromFile("pg_selectPymtGtwayid"), mGetPropertyFromFile("pg_techProcessSelectPymtGtway"));
				mGenericWait();
				mClick("id", mGetPropertyFromFile("pg_payButtonid"));
				mGenericWait();
				mWaitForVisible("id", mGetPropertyFromFile("pg_selectNetBankid"));
				mSelectDropDown("id", mGetPropertyFromFile("pg_selectNetBankid"), mGetPropertyFromFile("pg_selectNetBank"));
				mGenericWait();
				mClick("id", mGetPropertyFromFile("pg_payNowButtonid"));
				mGenericWait();
				mWaitForVisible("id", mGetPropertyFromFile("pg_confirmButtonid"));
				mClick("id", mGetPropertyFromFile("pg_confirmButtonid"));
				mGenericWait();
				mWaitForVisible("id", mGetPropertyFromFile("pg_submitButtonid"));
				mSendKeys("id", mGetPropertyFromFile("pg_customerIDid"), mGetPropertyFromFile("pg_customerID"));
				mGenericWait();
				mSendKeys("id", mGetPropertyFromFile("pg_tranPasswordid"), mGetPropertyFromFile("pg_tranPassword"));
				mGenericWait();
				mClick("id", mGetPropertyFromFile("pg_submitButtonid"));
			}
			else if(pymtGtway.equals("PAYU"))
			{

				mWaitForVisible("id", mGetPropertyFromFile("pg_selectPymtGtwayid"));
				mSelectDropDown("id", mGetPropertyFromFile("pg_selectPymtGtwayid"), mGetPropertyFromFile("pg_payuSelectPymtGtway"));
				mGenericWait();
				mClick("id", mGetPropertyFromFile("pg_payButtonid"));

				mClick("id", mGetPropertyFromFile("pg_creditCardLinkid"));

				mClick("id", mGetPropertyFromFile("pg_creditCardTypeVisaid"));

				mSendKeys("id", mGetPropertyFromFile("pg_creditCardNumberid"), mGetPropertyFromFile("pg_creditCardNumber"));

				mSendKeys("id", mGetPropertyFromFile("pg_creditCardHolderNameid"), mGetPropertyFromFile("pg_creditCardHolderName"));

				mSendKeys("id", mGetPropertyFromFile("pg_creditCardCVVNoid"), mGetPropertyFromFile("pg_creditCardCVVNo"));

				mSelectDropDown("id", mGetPropertyFromFile("pg_creditCardExpiryDtMonthid"), mGetPropertyFromFile("pg_creditCardExpiryDtMonth"));

				mSelectDropDown("id", mGetPropertyFromFile("pg_creditCardExpiryDtYearid"), mGetPropertyFromFile("pg_creditCardExpiryDtYear"));

				mClick("id", mGetPropertyFromFile("pg_creditCardPayNowBtnid"));

			}
			else if(pymtGtway.equals("HDFC"))
			{

				mWaitForVisible("id", mGetPropertyFromFile("pg_selectPymtGtwayid"));
				mSelectDropDown("id", mGetPropertyFromFile("pg_selectPymtGtwayid"), mGetPropertyFromFile("pg_hdfcSelectPymtGtway"));
				mGenericWait();
				mClick("id", mGetPropertyFromFile("pg_payButtonid"));

				mClick("id", mGetPropertyFromFile("pg_netBankingid"));

				mSelectDropDown("id", mGetPropertyFromFile("pg_hdfcSelectNetBankid"), mGetPropertyFromFile("pg_hdfcSelectNetBank"));

				mClick("id", mGetPropertyFromFile("pg_netBankingSubmitid"));
				driver.findElement(By.id("textfield2")).clear();
				driver.findElement(By.id("textfield2")).sendKeys("test");

				driver.findElement(By.id("textfield3")).clear();
				driver.findElement(By.id("textfield3")).sendKeys("test");
				driver.findElement(By.id("button")).click();
				driver.findElement(By.id("button")).click();

			}
		}
		catch(Exception e)
		{
			throw new MainetCustomExceptions("Error in paymentGateway method");
		}
	}



	/*public void ChecklistVerification(String uName, String pwd)
	{
		try
		{
			//mCreateArtefactsFolder(module);
			System.out.println("uname and pwd are "+uName +pwd);
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(uName, pwd);
			checkListVerifn=mGetPropertyFromFile("ClvAppApprovedata").split(",");
			if(checkListVerifn[0].equalsIgnoreCase("approve"))
			{
				checklistVerification(checkListVerifn[0]);
				System.out.println("the checklist verification of the application is Approved");
			}	
			else 
			{
				if(checkListVerifn[0].equalsIgnoreCase("hold"))
				{
					checklistVerification(checkListVerifn[0]);
					System.out.println("the checklist verification of the application is Hold");
					Resubmission();
					checklistVerification(checkListVerifn[1]);
					System.out.println("the checklist verification of the application is Approved");
				}
				else if(checkListVerifn[0].equalsIgnoreCase("hold&reject"))
				{
					checklistVerification(checkListVerifn[0]);
					System.out.println("the checklist verification of the application is Hold"); 
					Resubmission();
					checklistVerification(checkListVerifn[1]);
					System.out.println("the checklist verification of the application is rejected");
				}
				else if(checkListVerifn[0].equalsIgnoreCase("reject"))
				{
					checklistVerification(checkListVerifn[0]);
					System.out.println("the checklist verification of the application is rejected"); 
			    	
				}					
			}

			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in ChecklistVerification method");
		}
	}
*/

	//ChecklistVerification
	/*public void checklistVerification(String data)
	{

		try
		{
			mGenericWait();
			// navigate to checklist verification link

			mNavigation("ChcklstVerfctnLinkid", "ClvTransactionLinkid", "ClvChecklistVerificationLinkid");
			mCustomWait(1000);
			mTakeScreenShot();


			//sending application number
			mCustomWait(3000);
			//mSendKeys("id", mGetPropertyFromFile("ClvApplctnNoid"), appNo);

			//mSendKeys("id", mGetPropertyFromFile("ClvApplctnNoid"), "71116050900009");

			IndOrDep("id", "ClvApplctnNoid", "applicationNo");

			mCustomWait(1000);

			//finding application
			mClick("css", mGetPropertyFromFile("ClvFindAppBtnid"));
			mGenericWait();

			//finding application image ink
			mWaitForVisible("xpath", mGetPropertyFromFile("ClvFindAppImgid"));
			mCustomWait(1000);
//)
			
			mTakeScreenShot();
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("ClvFindAppImgid"));
			mCustomWait(3000);
			// clvapplicationdate=mGetText("css", mGetPropertyFromFile("ClvApplicationdateid"));

			mTakeScreenShot();
			
				



			ChecklistTableGrid(data);
			///cheklist assertion common method
			
			assertchecklistVerification();


			if(data.equalsIgnoreCase("reject"))
			{
				mCustomWait(1000); 
				mClick("id", mGetPropertyFromFile("ClvAppRejectid"));	
				rejected.add("true"); 
			}

			else if(data.equalsIgnoreCase("hold"))
			{
				mCustomWait(1000); 
				mClick("id", mGetPropertyFromFile("ClvAppHoldid"));
				onhold.add("true"); 			
			}		

			else 	
			{						
				mCustomWait(1000); 
				mClick("id", mGetPropertyFromFile("ClvAppApproveid"));
			}

			mCustomWait(1000);
			mTakeScreenShot();

			//submit button
			mCustomWait(1000);
			mClick("xpath", mGetPropertyFromFile("ClvChcklistVerifictnSubBtnid"));	
			mGenericWait();

			//waiing for proceed button
			mWaitForVisible("id", mGetPropertyFromFile("ClvChcklistVerifictnProcdBtnid"));
			String MsgAftrChcklstVerfctn = mGetText("css", mGetPropertyFromFile("ClvChcklistVerifictnMsgid"));
			System.out.println(MsgAftrChcklstVerfctn);
			mTakeScreenShot();
			mCustomWait(2000);
			mAssert(MsgAftrChcklstVerfctn, mGetPropertyFromFile("ClvProcdAssertData"), "Actual   :"+MsgAftrChcklstVerfctn+"  Expected  :"+mGetPropertyFromFile("ClvProcdAssertData"));
			mCustomWait(1000);
			mClick("id", mGetPropertyFromFile("ClvChcklistVerifictnProcdBtnid"));
			mCustomWait(2000);
			
			if(data.equalsIgnoreCase("reject") || data.equalsIgnoreCase("hold"))
			{
				mCustomWait(3500); 
				mChallanPDFReader();
				mCustomWait(1000);
			} 

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in checklistVerification method");
		}
	}
*/
	//LOI Payment
	public void before_LoiPayment(){

		excelToProp.convertExcelToProperties(mGetPropertyFromFile("tpLOIPaymentID_Excel"),mGetPropertyFromFile("tpLOIPaymentID_Excel"));
		mReadFromInputStream(mGetPropertyFromFile("tpLOIPaymentID_Prop_Path"),mGetPropertyFromFile("tpLOIPaymentID_Prop_Path"));

	}

	public void loiPayment(String module, String Service) throws InterruptedException, IOException
	{
		try
		{
			mCreateArtefactsFolder(module);
			//chllanForTpServices = true;
			//before_LoiPayment();
			mOpenBrowser("Chrome");
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			LOIPayment(module);
			logOut();
			finalLogOut();
			System.out.println("This is service for which loi payment will be made"+Service);
			isChallanVerftnRequired(Service);
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
	/*public void LOIPayment(String module)
	{
		try
		{
			mNavigation("mkt_onlserviceLinkid","mkt_PaymentLinkid", "mkt_loiPaymentid"); //08-11-2016 - Jyoti
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

			//sending LOI number
			//mWaitForVisible("id", mGetPropertyFromFile("LpSearchByLOINumid"));
			//mCustomWait(2000);
			//mSendKeys("id", mGetPropertyFromFile("LpSearchByLOINumid"), LOINumber);
			//mSendKeys("id", mGetPropertyFromFile("LpSearchByLOINumid"), "115");


			//sending application number
			mWaitForVisible("id", mGetPropertyFromFile("LpSearchByAppNumid"));
			mCustomWait(2000);

			mSendKeys("id", mGetPropertyFromFile("LpSearchByAppNumid"), appNo);

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
			//mAssert(ApplicationNum, appNo, "Application Number does not match at LOI Payment.  Actual  :"+ApplicationNum+"    Expected    :"+appNo);



			//String ApplicationNum = driver.findElement(By.id("applicationNo")).getText();
			String LOINUm = driver.findElement(By.id(mGetPropertyFromFile("LpAssertLOINoid"))).getAttribute("value");
			System.out.println(LOINUm);
			mCustomWait(2000);


			//mAssert(LOINUm, "100", "LOI Number does not match at LOI Payment.  Actual  :"+LOINUm+"   Expected   :100");
			mAssert(LOINUm, LOINumber, "LOI Number does not match at LOI Payment.  Actual  :"+LOINUm+"   Expected   :"+LOINumber);

			//      check assert for LOI
			//		mAssert(LOINUm, "100", "LOI Number does not match at LOI Payment.  Actual  :"+LOINUm+"   Expected   :100");

			//mAssert(LOINUm, LOINumber, "LOI Number does not match at LOI Payment.  Actual  :"+LOINUm+"   Expected   :"+LOINumber);


			mscroll(0, 250);


			//new
			LOIAPPLICABLE= true;
			payment("paymentMode","byBankOrULB");
			//String LOINUm = driver.findElement(By.id("loiNo")).getText();

			
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
			mCustomWait(12000);
			mSwitchTabs();
			//Not to be commented
			//mChallanPDFReader();

			mGenericWait();
			//Not to be commented
			//LOI Payment verification with assertions
			//LOIVerification();
			//mGenericWait();

		}catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in LOIPayment method");
		}
	}*/
	//LOI Payment
		public void LOIPayment(String module)
		{
			try
			{
			//mNavigation("mkt_onlserviceLinkid","mkt_PaymentLinkid", "mkt_loiPaymentid"); //08-11-2016 - Jyoti
			mNavigation("LOIPaymentOnlineServiceLinkid","LOIPaymentLinkid", "LOIPaymentServicesLinkid"); //08-11-2016 - Jyoti
				/*
				//Payment Link
				mGenericWait();
				mWaitForVisible("linkText", mGetPropertyFromFile("LOIPaymentLinkid"));
				mClick("linkText", mGetPropertyFromFile("LOIPaymentLinkid"));
				mCustomWait(2000);

				//LOI payment Services link
				mWaitForVisible("linkText", mGetPropertyFromFile("LOIPaymentServicesLinkid"));
				mClick("linkText", mGetPropertyFromFile("LOIPaymentServicesLinkid"));
				mCustomWait(2000);*/

				//Select department from dropdown
				mWaitForVisible("id", mGetPropertyFromFile("LOIPaymentdeptId"));
				mSelectDropDown("id", mGetPropertyFromFile("LOIPaymentdeptId"), module);

				//sending LOI number
				//mWaitForVisible("id", mGetPropertyFromFile("LpSearchByLOINumid"));
				//mCustomWait(2000);
				//mSendKeys("id", mGetPropertyFromFile("LpSearchByLOINumid"), LOINumber);
				//mSendKeys("id", mGetPropertyFromFile("LpSearchByLOINumid"), "115");


				//sending application number
				mWaitForVisible("id", mGetPropertyFromFile("LpSearchByAppNumid"));
				mCustomWait(1000);
				
				
				
				IndOrDep("id", "LpSearchByAppNumid", "applicationNo");
			//	mSendKeys("id", mGetPropertyFromFile("LpSearchByAppNumid"), appNo);
				mCustomWait(1000);
				
				

				//mSendKeys("id", mGetPropertyFromFile("LpSearchByAppNumid"),"22316110800014");

				//Search Button
				mWaitForVisible("xpath", mGetPropertyFromFile("LpSearchAppBtnid"));
				mClick("xpath", mGetPropertyFromFile("LpSearchAppBtnid"));
				mCustomWait(2000);

				//View Details Button
				mWaitForVisible("css", mGetPropertyFromFile("LpViewDetailsImgid"));
				mClick("css", mGetPropertyFromFile("LpViewDetailsImgid"));
				mCustomWait(2000);

				//getting application number for assert
				String ApplicationNum =  driver.findElement(By.id(mGetPropertyFromFile("LpAssertAppNoid"))).getAttribute("value");
				System.out.println("Application Number is::::"+ApplicationNum);
				mCustomWait(2000);

				mAssert(ApplicationNum, appNo, "Application Number does not match at LOI Payment.  Actual  :"+ApplicationNum+"    Expected    :"+appNo);
				//mAssert(ApplicationNum, appNo, "Application Number does not match at LOI Payment.  Actual  :"+ApplicationNum+"    Expected    :"+appNo);



				//String ApplicationNum = driver.findElement(By.id("applicationNo")).getText();
				String LOINUm = driver.findElement(By.id(mGetPropertyFromFile("LpAssertLOINoid"))).getAttribute("value");
				System.out.println(LOINUm);
				mCustomWait(2000);


				//mAssert(LOINUm, "100", "LOI Number does not match at LOI Payment.  Actual  :"+LOINUm+"   Expected   :100");
				mAssert(LOINUm, LOINumber, "LOI Number does not match at LOI Payment.  Actual  :"+LOINUm+"   Expected   :"+LOINumber);

				//      check assert for LOI
				//		mAssert(LOINUm, "100", "LOI Number does not match at LOI Payment.  Actual  :"+LOINUm+"   Expected   :100");

				//mAssert(LOINUm, LOINumber, "LOI Number does not match at LOI Payment.  Actual  :"+LOINUm+"   Expected   :"+LOINumber);


				mscroll(0, 250);


				//new
				LOIAPPLICABLE= true;
				payment("paymentMode","byBankOrULB");
				//String LOINUm = driver.findElement(By.id("loiNo")).getText();

				//submit button 
				mWaitForVisible("xpath", mGetPropertyFromFile("LpLOIPaymentSubBtnid"));
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
				mCustomWait(12000);
				mSwitchTabs();
				//Not to be commented
				//mChallanPDFReader();

				mGenericWait();
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

	//Using printer grid method of bnd services created by sneha solaskar
	//Sunil D Sonmale
	public void CFCprintergrid(String uName, String pwd)
	{
		try{
			mCreateArtefactsFolder("CFC_");
			//chllanForTpServices = true;
			//before_LoiPayment();
	
			/*mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			common.selectUlb();
			//common.citizenLogin();

			common.departmentLogin(mGetPropertyFromFile("deptUserName"),mGetPropertyFromFile("deptPassword"));
			*/
			
			
			
			
			
			
			
			
			
			
			mOpenBrowser(mGetPropertyFromFile("browserName"));
			mGetURL(mGetPropertyFromFile("url"));
			selectUlb();
			
			
			
			
			
			/*mOpenBrowser("Chrome");
			mGetURL(mGetPropertyFromFile("url"));*/
		//	selectUlb();
			departmentLogin(uName, pwd);
			cfcPrinterGrid();
			logOut();
			finalLogOut();
			mCloseBrowser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in CFCprintergrid method");
		}
	}

	//Printer Grid
	public void cfcPrinterGrid()
	{//======================
		try
		{

			mNavigation("cfc_CFCLinkid", "cfc_transactionsLinkid", "cfc_printerCounterLinkid");

			mCustomWait(5000);
			//wait for find records 
			mWaitForVisible("css", mGetPropertyFromFile("cfc_printerFindRecordsid"));
			mGenericWait();
			mCustomWait(5000);

			//Click on find records
			mClick("css", mGetPropertyFromFile("cfc_printerFindRecordsid"));
			mGenericWait();
			mSelectDropDown("css", mGetPropertyFromFile("cfc_printerSelectAppNoid"), mGetPropertyFromFile("cfc_printerSelectAppNo"));
			mSelectDropDown("css", mGetPropertyFromFile("cfc_printerSelectEqualid"), mGetPropertyFromFile("cfc_printerSelectEqual"));

			IndOrDep("id","cfc_printerEnterAppNoid", "applicationNo");

			//wait for find
			mWaitForVisible("id", mGetPropertyFromFile("cfc_printerFindButtonid"));
			mGenericWait();
			mClick("id", mGetPropertyFromFile("cfc_printerFindButtonid"));

			mTakeScreenShot();

			//wait for print report
			mWaitForVisible("xpath", mGetPropertyFromFile("cfc_printerPrintReportid"));
			mGenericWait();
			mClick("xpath", mGetPropertyFromFile("cfc_printerPrintReportid"));
			mCustomWait(10000);

			mChallanPDFReader();
			
		}
				catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in cfcPrinterGrid method");			
		}
	}
	

	// common method to select online payment mode
	public void onlinePayment()
	{
		try
		{
		mWaitForVisible("id", mGetPropertyFromFile("LOIonlinePaymentId"));

		mClick("id", mGetPropertyFromFile("LOIonlinePaymentId"));

		mGenericWait();
		//For RNL service to be added in common method with key from excels
		if(mGetPropertyFromFile("rnl_RentPaymentScheduleLinkid").equalsIgnoreCase("Rent Payment Schedule"))
		{
			mWaitForVisible("css", mGetPropertyFromFile("RNL_LOIonlinePaymenduesbtntId"));
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("RNL_LOIonlinePaymenduesbtntId"));
		}
		else
		{
			mWaitForVisible("css", mGetPropertyFromFile("LOIonlinePaymentSubmitBtnId"));
			mTakeScreenShot();
			mClick("css", mGetPropertyFromFile("LOIonlinePaymentSubmitBtnId")); 
		}
		/*mWaitForVisible("css", mGetPropertyFromFile("LOIonlinePaymentSubmitBtnId"));
		mTakeScreenShot();
		mClick("css", mGetPropertyFromFile("LOIonlinePaymentSubmitBtnId"));*/

		mWaitForVisible("id", mGetPropertyFromFile("LOIonlinePaymentProceedBtnId"));
		mGenericWait();
		mTakeScreenShot();
		mCustomWait(1000);
		mClick("id", mGetPropertyFromFile("LOIonlinePaymentProceedBtnId"));

		mWaitForVisible("css", mGetPropertyFromFile("LOIonlinePaymentPayBtnId"));
		mGenericWait();

		///Capturing fields for verification

		//Amount
		String amounttobepaid=mGetText("id", mGetPropertyFromFile("LOIonlinePaymentDueAmountId"));
		System.out.println(amounttobepaid);

		//Mobile no
		String mobileNo=mGetText("id", mGetPropertyFromFile("LOIonlinePaymentMobileNoId"));
		System.out.println(mobileNo);

		//Email id
		String emailID= mGetText("id",mGetPropertyFromFile("LOIonlinePaymentMailId"));
		System.out.println(emailID);

		//Selecting Payment gateway
		mGenericWait();
		mSelectDropDown("id", mGetPropertyFromFile("LOIonlinePaymentPaymentGatewayId"), mGetPropertyFromFile("LOIonlinePaymentPaymentGatewayData"));
		mTakeScreenShot();
		//click pay
		mClick("css", mGetPropertyFromFile("LOIonlinePaymentPayBtnId"));			
	
		mWaitForVisible("css", "#main-message > h1");

		String a;
		if((a=driver.findElement(locatorSelector("css", "#main-message > h1")).getText()).equals("This site can’t be reached"))
		{
		mCustomWait(1500); 	
		driver.navigate().refresh();
		mCustomWait(12000);
		} 		
		

		//Wait for payu page to appear

		mWaitForVisible("id", mGetPropertyFromFile("LOIonlinePaymentPayNowBtnId"));		
		mGenericWait();
					
		//Enter card details
		mHindiTextConversion("id", mGetPropertyFromFile("LOIonlinePaymentCardNumberId"), mGetPropertyFromFile("LOIonlinePaymentCardNumberData"));
		mGenericWait();

		mSendKeys("id", mGetPropertyFromFile("LOIonlinePaymentCardNameId"),mGetPropertyFromFile("LOIonlinePaymentCardNameData"));
		mGenericWait();

		mSendKeys("id",mGetPropertyFromFile("LOIonlinePaymentCardCvvNumberId"),mGetPropertyFromFile("LOIonlinePaymentCardCvvNumberData"));
		mGenericWait();

		mSelectDropDown("id", mGetPropertyFromFile("LOIonlinePaymentCardExpiryMonthId"), mGetPropertyFromFile("LOIonlinePaymentCardExpiryMonthData"));
		mGenericWait();

		mSelectDropDown("id", mGetPropertyFromFile("LOIonlinePaymentCardExpiryYearId"),mGetPropertyFromFile("LOIonlinePaymentCardExpiryYearData"));
		mGenericWait();
		mTakeScreenShot();
		mClick("id", mGetPropertyFromFile("LOIonlinePaymentPayNowBtnId"));

		mWaitForVisible("css", mGetPropertyFromFile("LOIonlinePaymentNameOfServiceId"));

		
		
		mCustomWait(5000); 

		//Capturing transaction details which received as response from PAYU
		//
		/////////////////////////////////////////////////////////////////////


		/*appNo=mGetText("css", "#content > div > div:nth-child(1) > div:nth-child(1) > span > font");*/

/*		mAppNoArray("css", mGetPropertyFromFile("LOIonlinePaymentApplicationNoId"));
		System.err.println(applicationNo);
*/
		
		if(!mGetPropertyFromFile("paymentMode").equalsIgnoreCase("online")){
			appNo=mGetText("css", mGetPropertyFromFile("LOIApplicationNoonlinePaymentId"));
			System.out.println(appNo);
			mAppNoArray("css", mGetPropertyFromFile("LOIApplicationNoonlinePaymentId"));
			System.err.println(applicationNo);
		}
		
		
		String nameOfService=mGetText("css", mGetPropertyFromFile("LOIonlinePaymentNameOfServiceId"));
		System.err.println(nameOfService);

		String nameOfApplicant=mGetText("css",mGetPropertyFromFile("LOIonlinePaymentNameOfApplicantId"));
		System.err.println(nameOfApplicant);

		String mobileNumber=mGetText("css",mGetPropertyFromFile("LOIonlinePaymentMobileNumberId"));
		System.err.println(mobileNumber);

		String emailId=mGetText("css",mGetPropertyFromFile("LOIonlinePaymentEmailId"));
		System.err.println(emailId);

		String amountPaid=mGetText("css",mGetPropertyFromFile("LOIonlinePaymentAmountPaidId"));
		System.err.println(amountPaid);

		String orderNumber=mGetText("css",mGetPropertyFromFile("LOIonlinePaymentOrderNumberId"));
		System.err.println(orderNumber);

		String transactionReferenceNo=mGetText("css",mGetPropertyFromFile("LOIonlinePaymentTransactionReferenceNoId"));
		System.err.println(transactionReferenceNo);

		String transactionDateTime=mGetText("css",mGetPropertyFromFile("LOIonlinePaymentTransactionDateTimeId"));
		System.err.println(transactionDateTime);

		String transactionStatus=mGetText("css",mGetPropertyFromFile("LOIonlinePaymentTransactionStatusId"));
		System.err.println(transactionStatus);

		String errorStatus=mGetText("css",mGetPropertyFromFile("LOIonlinePaymentErrorStatusId"));
		System.err.println(errorStatus);
		
		mCustomWait(2000); 
		mTakeScreenShot();
		mClick("id", mGetPropertyFromFile("LOIonlinePaymentFinalSubmitBtnId")); 
		mCustomWait(2000); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MainetCustomExceptions("Error in onlinePayment method");	
		}
	}

	// for reading checklist verification table for approving/rejecting/holding application

		/*public void ChecklistTableGrid(String data)
		{
			try{

				WebElement table = driver.findElement(By.className("gridtable"));

				List<WebElement> rows = table.findElements(By.tagName("tr"));
				int rwcount =rows.size();
				System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
				for(int i=2;i<=rwcount;i++)
				{
					for(int j=4;j<=7;j++)
					{
						if(j==4)
						{							
							String frstDoc1 = driver.findElement(By.xpath("//*[@id=\"frmMaster\"]/table/tbody/tr["+i+"]/td["+j+"]/div/a")).getText();
							System.out.println(frstDoc1);
							driver.findElement(By.linkText(frstDoc1)).click();
							mDownloadFile(myClassName);
							mCustomWait(4000);
							mSwitchTabs();									
						}
						
						if(j==5)
						{
							mUpload("css", mGetPropertyFromFile("ClvUploadid")+(i-2)+"']", mGetPropertyFromFile("ClvUploaddata"+i));
							mGenericWait();
						}

						if(j==6)
						{
							if(data.equalsIgnoreCase("approve"))
							{				 
								System.out.println("inside approve");
								mClick("id", mGetPropertyFromFile("ClvVerificationCheckBoxid")+(i-2));
								mCustomWait(1500);	
							}	
						}

						if(j==7)
						{
							System.out.println("data : "+data);
							if(data.equalsIgnoreCase("reject") || data.equalsIgnoreCase("hold"))
							{
								System.out.println("inside reject");
								mSendKeys("id",mGetPropertyFromFile("ClvVerificationRejectionRemarkid")+(i-2),mGetPropertyFromFile("ClvVerificationRejectionRemarkData")); 
								mCustomWait(1500);	
							}	
						}
					}
					mTakeScreenShot();
				}
			}
        
    
			catch(Exception e)
			{
				e.printStackTrace();
				throw new MainetCustomExceptions("Error in grid reading method of checklistVerification");
			}

		}
*/
		/*public void Resubmission()
		{
			try{
				mGenericWait();
				// navigate to document resubmission link

				mNavigation("ChcklstVerfctnLinkid", "ClvTransactionLinkid","ClvDocResubmissionLinkid");

				mWaitForVisible("id",mGetPropertyFromFile("ClvDocResubmissionAppNoid"));
				mCustomWait(1000);
				IndOrDep("id", "ClvDocResubmissionAppNoid", "applicationNo");
				mCustomWait(1000);
				mTakeScreenShot();

				mClick("xpath", mGetPropertyFromFile("ClvDocResubmissionSearchBtnid"));
				mCustomWait(1000);

			
				mCustomWait(1000);

				ResubmissionChecklistTableGrid(checkListVerifn[1]);

				mWaitForVisible("css",mGetPropertyFromFile("ClvDocResubmissionSubmitBtnid"));
				mClick("css", mGetPropertyFromFile("ClvDocResubmissionSubmitBtnid"));

				mWaitForVisible("id",mGetPropertyFromFile("ClvDocResubmissionProcdBtnid"));

				String msg=mGetText("css", mGetPropertyFromFile("ClvDocResubmissionMsgid"));
				mAssert(msg, mGetPropertyFromFile("ClvDocResubmissionProcdAssertData"), "Actual  :"+msg+" Expected  :"+mGetPropertyFromFile("ClvDocResubmissionProcdAssertData"));
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("ClvDocResubmissionProcdBtnid"));
				mCustomWait(1500); 
			}


			catch(Exception e)
			{
				e.printStackTrace();
				throw new MainetCustomExceptions("Error in grid reading method of checklistVerification");
			}
		}
		*/
		
		//New Method wrote for Document Resubmission on 12/04/2017 Piyush 
		/*public void ResubmissionChecklistTableGrid(String data)
		{
			try{

				WebElement table = driver.findElement(By.className("gridtable"));

				List<WebElement> rows = table.findElements(By.tagName("tr"));
				int rwcount =rows.size();
				System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
				for(int i=2;i<=rwcount;i++)
				{
					for(int j=4;j<=7;j++)
					{
						if(j==4)
						{							
							String frstDoc1 = driver.findElement(By.xpath("//*[@id=\"frmMasterForm\"]/table/tbody/tr["+i+"]/td["+j+"]/div/a")).getText();
							System.out.println(frstDoc1);
							driver.findElement(By.linkText(frstDoc1)).click();
							mDownloadFile(myClassName);
							mCustomWait(4000);
							mSwitchTabs();									
						}

						if(j==5)
						{
							mUpload("css", mGetPropertyFromFile("ClvUploadid")+(i-2)+"']", mGetPropertyFromFile("ClvUploaddata"+i));
							mGenericWait();
						}


					}
				}
				mTakeScreenShot();

			}


			catch(Exception e)
			{
				e.printStackTrace();
				throw new MainetCustomExceptions("Error in grid reading method of checklistVerification");
			}

		}*/


		
		
		
		
		
		
		public void assertchecklistVerification()
		{
			try{ 

				String clv_Service_Name=mGetText("xpath",mGetPropertyFromFile("clv_ServiceName_id"));
				clv_Service_Name_list.add(clv_Service_Name);
				System.out.println("clv_Service_Name"+clv_Service_Name);



				System.out.println("=====================================================");
				System.out.println("clv_Service_Name_list"+clv_Service_Name_list);
				


				MarketLicenseServices chklist=new MarketLicenseServices();
				TownPlanningServices tpmodule=new TownPlanningServices();
				RentAndLeaseServices rnl=new RentAndLeaseServices();

				switch(clv_Service_Name) 
				{ 

				case "New Trade License" :
					System.out.println("New Trade License");
					chklist.mktassertchecklistVerification();
					break;


				case "Transfer of License Under Nomination" :
					chklist.mktassertchecklistVerification();
					break;


				case "Cancellation of Trade License":
					chklist.mktassertchecklistVerification();
					break;
					//Not applicable for khagual
				case "Renewal of Trade License" :
					chklist.mktassertchecklistVerification();
					break;

				case "Duplicate Trade License" :
					chklist.mktassertchecklistVerification();
					break;
				
				case "Application for Development Plan" :
					System.out.println("Application for Development Plan"); 
					//tpmodule.TP_checklist_verification_asserion();
					break;
				case "Application for Building Permit" :
					
					//tpmodule.TP_checklist_verification_asserion();
					break;
				
                case "Booking of Estate" :
					
                	rnl.RNL_checklist_verification_asserion();
					break;
                case "Rent Renewal Contract" :
                	rnl.RNL_checklist_verification_asserion();
	                break;
			
	
				}
				

			}catch(Exception e)
			{
				e.printStackTrace();
				throw new MainetCustomExceptions("Error in cheklist verification assert method method");			
			}
		}
		public void ChecklistVerification(String uName, String pwd)
	       {
	              try
	              {
	                     
	                     System.out.println("uname and pwd are "+uName +pwd);
	                     mOpenBrowser(mGetPropertyFromFile("browserName"));
	                     mGetURL(mGetPropertyFromFile("url"));
	                     selectUlb();
	                     departmentLogin(uName, pwd);
	                     checkListVerifn=mGetPropertyFromFile("ClvAppApprovedata").split(",");
	                     if(checkListVerifn[0].equalsIgnoreCase("approve"))
	                     {
	                           checklistVerification(checkListVerifn[0]);
	                     }      
	                     else 
	                     {
	                           if(checkListVerifn[0].equalsIgnoreCase("hold"))
	                           {
	                                  checklistVerification(checkListVerifn[0]);
	                                  Resubmission();
	                                  checklistVerification(checkListVerifn[1]);
	                           }
	                           else if(checkListVerifn[0].equalsIgnoreCase("hold&reject"))
	                           {
	                                  checklistVerification(checkListVerifn[0]);
	                                  Resubmission();
	                                  checklistVerification(checkListVerifn[1]);
	                                  System.out.println("the checklist verification of the application is rejected");
	                                  
	                           }
	                           else if(checkListVerifn[0].equalsIgnoreCase("reject"))
	                           {
	                                  checklistVerification(checkListVerifn[0]);
	                                  System.out.println("the checklist verification of the application is rejected");
	                                  
	                           }                                 
	                     }

	                     logOut();
	                     finalLogOut();
	                     mCloseBrowser();
	              }
	              catch(Exception e)
	              {
	                     e.printStackTrace();
	                     throw new MainetCustomExceptions("Error in ChecklistVerification method");
	              }
	       }


	       //ChecklistVerification
	       public void checklistVerification(String data)
	       {

	              try
	              {
	                     mGenericWait();
	                     // navigate to checklist verification link

	                     mNavigation("ChcklstVerfctnLinkid", "ClvTransactionLinkid", "ClvChecklistVerificationLinkid");
	                     mCustomWait(1000);
	                     mTakeScreenShot();


	                     //sending application number
	                     mCustomWait(3000);
	                     

	                     IndOrDep("id", "ClvApplctnNoid", "applicationNo");

	                     mCustomWait(1000);

	                     //finding application
	                     mClick("css", mGetPropertyFromFile("ClvFindAppBtnid"));
	                     mGenericWait();

	                     //finding application image ink
	                     mWaitForVisible("xpath", mGetPropertyFromFile("ClvFindAppImgid"));
	                     mCustomWait(1000);
	//)
	                     
	                     mTakeScreenShot();
	                     mCustomWait(1000);
	                     mClick("xpath", mGetPropertyFromFile("ClvFindAppImgid"));
	                     mCustomWait(3000);
	                     
	                     mTakeScreenShot();
	                     
	                     ChecklistTableGrid(data);
	                     ///cheklist assertion common method
	                     
	                     //assertchecklistVerification();
	            mscroll(0, 350);

	                     if(data.equalsIgnoreCase("reject"))
	                     {
	                           mCustomWait(1000); 
	                           mClick("id", mGetPropertyFromFile("ClvAppRejectid"));  
	                           rejected.add("true"); 
	                     }

	                     else if(data.equalsIgnoreCase("hold"))
	                     {
	                           mCustomWait(1000); 
	                           mClick("id", mGetPropertyFromFile("ClvAppHoldid"));
	                           onhold.add("true");               
	                     }             

	                     else   
	                     {                                        
	                           mCustomWait(1000); 
	                           mClick("id", mGetPropertyFromFile("ClvAppApproveid"));
	                     }

	                     mCustomWait(1000);
	                     mTakeScreenShot();

	                     //submit button
	                     mCustomWait(1000);
	                     mClick("xpath", mGetPropertyFromFile("ClvChcklistVerifictnSubBtnid"));     
	                     mGenericWait();

	                     //waiing for proceed button
	                     mWaitForVisible("id", mGetPropertyFromFile("ClvChcklistVerifictnProcdBtnid"));
	                     String MsgAftrChcklstVerfctn = mGetText("css", mGetPropertyFromFile("ClvChcklistVerifictnMsgid"));
	                     System.out.println(MsgAftrChcklstVerfctn);
	                     mTakeScreenShot();
	                     mCustomWait(2000);
	                     mAssert(MsgAftrChcklstVerfctn, mGetPropertyFromFile("ClvProcdAssertData"), "Actual   :"+MsgAftrChcklstVerfctn+"  Expected  :"+mGetPropertyFromFile("ClvProcdAssertData"));
	                     mCustomWait(1000);
	                     mClick("id", mGetPropertyFromFile("ClvChcklistVerifictnProcdBtnid"));
	                     mCustomWait(2000);
	                     
	                     if(data.equalsIgnoreCase("reject") || data.equalsIgnoreCase("hold"))
	                     {
	                           mCustomWait(3000); 
	                           mChallanPDFReader();
	                           mCustomWait(2000);
	                     } 

	              }
	              catch(Exception e)
	              {
	                     e.printStackTrace();
	                     throw new MainetCustomExceptions("Error in checklistVerification method");
	              }
	       }




	// for reading checklist verification table for approving/rejecting/holding application

	              public void ChecklistTableGrid(String data)
	              {
	                     try{

	                           WebElement table = driver.findElement(By.className("gridtable"));

	                           List<WebElement> rows = table.findElements(By.tagName("tr"));
	                           int rwcount =rows.size();
	                           System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
	                           for(int i=2;i<=rwcount;i++)
	                           {
	                                  for(int j=4;j<=7;j++)
	                                  {
	                                         if(j==2)
	                                         {                                               
	                                                String frstDoc1 = driver.findElement(By.xpath("//*[@id=\"frmMaster\"]/table/tbody/tr["+i+"]/td["+j+"]/div/a")).getText();
	                                                System.out.println(frstDoc1);
	                                                driver.findElement(By.linkText(frstDoc1)).click();
	                                                mDownloadFile(myClassName);
	                                                mCustomWait(4000);
	                                                mSwitchTabs();                                                              
	                                         }
	                                         
	                                         if(j==5)
	                                         {
	                                                mUpload("css", mGetPropertyFromFile("ClvUploadid")+(i-2)+"']", mGetPropertyFromFile("ClvUploaddata"+i));
	                                                mscroll(0, 150);
	                                                mGenericWait();
	                                         }

	                                         if(j==6)
	                                         {
	                                                if(data.equalsIgnoreCase("approve"))
	                                                {                          
	                                                       System.out.println("inside approve");
	                                                       mClick("id", mGetPropertyFromFile("ClvVerificationCheckBoxid")+(i-2));
	                                                       mCustomWait(1500);   
	                                                }      
	                                         }

	                                         if(j==7)
	                                         {
	                                                System.out.println("data : "+data);
	                                                if(data.equalsIgnoreCase("reject") || data.equalsIgnoreCase("hold"))
	                                                {
	                                                       System.out.println("inside reject");
	                                                       mSendKeys("id",mGetPropertyFromFile("ClvVerificationRejectionRemarkid")+(i-2),mGetPropertyFromFile("ClvVerificationRejectionRemarkData")); 
	                                                       mCustomWait(1500);   
	                                                       
	                                                }      
	                                         }
	                                  }
	                                  mTakeScreenShot();
	                           }
	                     }
	        
	    
	                     catch(Exception e)
	                     {
	                           e.printStackTrace();
	                           throw new MainetCustomExceptions("Error in grid reading method of checklistVerification");
	                     }

	              }

	              
	              //Updated code on 12/04/2017  
	              public void Resubmission()
	              {
	                     try{
	                           mGenericWait();
	                           // navigate to document resubmission link

	                           mNavigation("ChcklstVerfctnLinkid", "ClvTransactionLinkid","ClvDocResubmissionLinkid");

	                           mWaitForVisible("id",mGetPropertyFromFile("ClvDocResubmissionAppNoid"));
	                           mCustomWait(1000);
	                           IndOrDep("id", "ClvDocResubmissionAppNoid", "applicationNo");
	                           mCustomWait(1000);
	                           mTakeScreenShot();

	                           mClick("xpath", mGetPropertyFromFile("ClvDocResubmissionSearchBtnid"));
	                           mWaitForVisible("css",mGetPropertyFromFile("ClvDocResubmissionServiceNameid"));
	                           mCustomWait(1000);

	                           
	                           mCustomWait(1000);
	                           
	                           ResubmissionChecklistTableGrid(checkListVerifn[1]);

	                           mWaitForVisible("css",mGetPropertyFromFile("ClvDocResubmissionSubmitBtnid"));
	                           mClick("css", mGetPropertyFromFile("ClvDocResubmissionSubmitBtnid"));

	                           mWaitForVisible("id",mGetPropertyFromFile("ClvDocResubmissionProcdBtnid"));

	                           String msg=mGetText("css", mGetPropertyFromFile("ClvDocResubmissionMsgid"));
	                           mAssert(msg, mGetPropertyFromFile("ClvDocResubmissionProcdAssertData"), "Actual  :"+msg+" Expected  :"+mGetPropertyFromFile("ClvDocResubmissionProcdAssertData"));
	                           mTakeScreenShot();
	                           mCustomWait(1000);
	                           mClick("id", mGetPropertyFromFile("ClvDocResubmissionProcdBtnid"));
	                           mCustomWait(1500); 
	                     }


	                     catch(Exception e)
	                     {
	                           e.printStackTrace();
	                           throw new MainetCustomExceptions("Error in grid reading method of checklistVerification");
	                     }

	              }
	              
	              //New Method wrote for Document Resubmission on 12/04/2017 Piyush 
	              public void ResubmissionChecklistTableGrid(String data)
	              {
	                     try{

	                           WebElement table = driver.findElement(By.className("gridtable"));

	                           List<WebElement> rows = table.findElements(By.tagName("tr"));
	                           int rwcount =rows.size();
	                           System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
	                           for(int i=2;i<=rwcount;i++)
	                           {
	                                  for(int j=4;j<=7;j++)
	                                  {
	                                         if(j==4)
	                                         {                                               
	                                                String frstDoc1 = driver.findElement(By.xpath("//*[@id=\"frmMasterForm\"]/table/tbody/tr["+i+"]/td["+j+"]/div/a")).getText();
	                                                System.out.println(frstDoc1);
	                                                driver.findElement(By.linkText(frstDoc1)).click();
	                                                mDownloadFile(myClassName);
	                                                mCustomWait(4000);
	                                                mSwitchTabs();                                                              
	                                         }
	                                         
	                                         if(j==5)
	                                         {
	                                                mUpload("css", mGetPropertyFromFile("ClvUploadid")+(i-2)+"']", mGetPropertyFromFile("ClvUploaddata"+i));
	                                                mGenericWait();
	                                         }

	                                         
	                                         }
	                                  }
	                                  mTakeScreenShot();
	                     
	                     }
	        
	    
	                     catch(Exception e)
	                     {
	                           e.printStackTrace();
	                           throw new MainetCustomExceptions("Error in grid reading method of checklistVerification");
	                     }

	              }
	              


	public static void main(String[] args) {

	}

}
