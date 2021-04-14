package clubbedapi;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import api.CommonUtilsAPI;
import api.PdfPatterns;


/**
 * @author Tejas.Kotekar
 *
 */
public class ClubbedAPI extends CommonUtilsAPI{


	public static String lgl_RefCaseNo;
	int rowCount;
	int dataFoundFlag=0;
	String refCaseNo=mGetPropertyFromFile("LGL_roOdrUlbRefCaseNoData");

	public static ArrayList<String> BillGeneration = new ArrayList<String>();
	public static ArrayList<ArrayList> ArrayofBillGeneration = new ArrayList<ArrayList>();
	public static ArrayList<String> Errormsg = new ArrayList<String>();
	public static ArrayList<ArrayList> ArrayofErrormsg = new ArrayList<ArrayList>();
	public  ArrayList<String> BillPrint = new ArrayList<String>();
	public static ArrayList<ArrayList<String>> ArrayofBillPrint=new ArrayList<ArrayList<String>>();;
	public static ArrayList<String> RentPaySchedule = new ArrayList<String>();
	public static ArrayList<ArrayList> ArrayofRentPaySchedule = new ArrayList<ArrayList>();
	public static ArrayList<String> PaymentHistory = new ArrayList<String>();
	public static ArrayList<ArrayList> ArrayofPaymentHistory = new ArrayList<ArrayList>();
	public static int dueCount=0;
	PdfPatterns pdfpattern= new PdfPatterns();

	//////////////>ArrayofRentPaySchedule
	/*public static ArrayList<String> RentPaySchedule = new ArrayList<String>();
	public static ArrayList<ArrayList> ArrayofRentPaySchedule = new ArrayList<ArrayList>();
*/


	////////////////
	/*public static ArrayList<String> BillGeneration = new ArrayList<String>();
	public static ArrayList<ArrayList> ArrayofBillGeneration = new ArrayList<ArrayList>();
	public static int dueCount=0;
*/
	/////////////////////

	/*public static ArrayList<String> PaymentHistory = new ArrayList<String>();
	public static ArrayList<ArrayList> ArrayofPaymentHistory = new ArrayList<ArrayList>();*/

	/////////////////////

	/*public  ArrayList<String> BillPrint = new ArrayList<String>();
	public static ArrayList<ArrayList<String>> ArrayofBillPrint=new ArrayList<ArrayList<String>>();*/

	/////////////////////////
	/*public static ArrayList<String> Errormsg = new ArrayList<String>();
	public static ArrayList<ArrayList> ArrayofErrormsg = new ArrayList<ArrayList>();
*/




	//Main function for grid verification since 14-08-2015 by Tejas K.
	public void genericMAINetGridVerification(WebElement gridId, WebElement headerId, String data, int columnNo, boolean hit){

		paginateMAINetGrid(gridId,data,headerId,columnNo);

	}

	//Pagination
	private void paginateMAINetGrid(WebElement gridId, String data, WebElement headerId,int columnNo){

		String gridID=gridId.getAttribute("id");
		System.out.println("Grid ID::: "+gridID);

		//Check if grid exists
		try{
			System.out.println("Grid exists");

			//Verify column count
			verifyColumnCount(gridId,columnNo);

			//Get page count
			//int pageCount=getGridPageCount(pageCountId);


			//Verify Grid Data for page count, with option for first hit or all hits
			List<WebElement> gridData = verifyMAINetGridData(gridId,data,columnNo);

			//Call headers
			getGridHeaders(headerId, gridData);

		}catch(Exception e)
		{
			e.printStackTrace();
		}


	}

	//Method to verify column count since 14-08-2015 by Tejas K.
	private void verifyColumnCount(WebElement gridId, int columnNo){

		try{

			String gridID=gridId.getAttribute("id");
			List<WebElement> cols = null;

			//Verify column count
			WebElement table = gridId.findElement(By.id(gridID));
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			rowCount=rows.size();
			System.out.println("Row count::: "+rowCount);

			for(WebElement rowElement:rows)
			{
				//Get columns count 
				cols=rowElement.findElements(By.tagName("td"));

			}
			int columnCount=cols.size();
			System.out.println("Total columns in grid::: "+columnCount);

			if(columnNo<=columnCount)
			{

				System.out.println("Correct column number");

			}
			else{
				System.err.println("Entered column number is invalid!! Please enter a valid column number..");
				System.out.println("Exiting...");

			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	//Method to count number of pages the grid contains	 since 14-08-2015 by Tejas K.
	/*	private int getGridPageCount(WebElement pageCountId){

		String pageCountID=pageCountId.getAttribute("id");
		System.out.println("Page count ID::: "+pageCountID);
		String totpages=pageCountId.findElement(By.id(pageCountID)).getText();

		int pgCount=Integer.valueOf(totpages);
		System.out.println("Number of pages = "+pgCount);

		return pgCount;
	}*/

	//Return header values and print data since 14-08-2015 by Tejas K.
	private List<WebElement> getGridHeaders(WebElement headerId, List<WebElement> gridData){

		List<WebElement> gridHeadersList = new ArrayList<WebElement>();

		try{

			gridHeadersList=headerId.findElements(By.tagName("tr"));

			for(WebElement headers:gridHeadersList){

				System.err.print(headers.getText()+"::: ");

				for(WebElement rowData:gridData){

					int headerIndex=gridHeadersList.indexOf(headers);
					int rowIndex=gridData.indexOf(rowData);
					if(headerIndex==rowIndex){
						//						System.out.println("Row data is " +rowData);
					}
					if(dataFoundFlag!=1){
						break;
					}
				}
				if(dataFoundFlag!=1){
					break;
				}
			}

		}
		catch(Exception ex){
			ex.printStackTrace();

		}

		return gridHeadersList;
	}





	//Search for input data since 14-08-2015 by Tejas K.
	private List<WebElement> verifyMAINetGridData(WebElement gridId,String data,int columnNo){

		String cellText=null;
		//int flag=0;
		List<WebElement> gridDataList = new ArrayList<WebElement>();

		try{
			//Iterate through grid pages
			//for(int loopPages=0;loopPages<gridPageCount;loopPages++){

			List<WebElement> rows = gridId.findElements(By.tagName("tr"));
			int gridRowCount=rows.size();
			//Iterate through rows of a grid
			for(int row=0;row<gridRowCount;row++){

				gridDataList=rows.get(row).findElements(By.tagName("td"));

				//Iterate through columns of a grid
				for(int column=0;column<=columnNo;column++){

					if(columnNo==column){


						cellText=gridDataList.get(columnNo).getText();

						while(cellText.equals(data)){

							for(WebElement rowData:gridDataList){
								//System.out.println(rowData.getText());
								dataFoundFlag=1;
							}
							break;
						}

						if(dataFoundFlag==1){
							break;
						}
					}//if end


				}//column end

				if(dataFoundFlag==1){
					break;
				}
			}//row end

			if(dataFoundFlag!=1){

				//nextBtnId.findElement(By.cssSelector(props.getProperty("nextBtnID"))).click();
				//driver.findElement(By.cssSelector(mGetPropertyFromFile("nextBtnID"))).click();
				System.out.println("No data found");

			}
			/*else{
					break;
				}

				int pageNoIncr = loopPages+1;
				System.out.println("No data found on "+pageNoIncr);
			 */

			//}//Page count loop end

		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return gridDataList;
	} 



	//  adding number of RowObjects for case party details

	private void lgl_wDynamicAddRowObjects(String tableId,String btnId,String name, String address,String rCount){

		String pltfnm = "";
		String pltfaddr="";
		String plaintiffname = "";  
		String plaintiffaddr = ""; 
		String plaintiffnameval = "";
		String plaintiffaddrval= "";

		try{
			int count=Integer.parseInt(rCount);	

			// changes done by Hiren Kathiria on 31-5-2016	

			// label for the outer Forloop
			mainloop:

				// Forloop for entering number of plaintiff and defendant details 	

				for(int a=0;a<count;a++)
				{
					WebElement table = driver.findElement(By.id(tableId));

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
							plaintiffname=cols.get(0).findElement(By.tagName("input")).getAttribute("id");
							pltfnm =plaintiffname.substring(0, 8);
							plaintiffnameval=cols.get(0).findElement(By.tagName("input")).getAttribute("Value");
							plaintiffaddr=cols.get(1).findElement(By.tagName("textarea")).getAttribute("id");
							pltfaddr =plaintiffaddr.substring(0, 8);
							plaintiffaddrval=cols.get(1).findElement(By.tagName("textarea")).getAttribute("value");
							if (Columnno==0)
							{
								System.out.println("Row "+Rowno+" Column "+Columnno+" Value "+plaintiffnameval+ " Id is " +plaintiffname);
							}

							else
							{
								System.out.println("Row "+Rowno+" Column "+Columnno+" Value "+plaintiffaddrval+ " Id is " +plaintiffaddr);
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

						// changes done by Hiren Kathiria on 31-5-2016	
						// break the Forloop if the limit of adding plaintiff and defendant details exceeds
						if(rwcount>100)
						{
							System.out.println("Breaking");
							System.out.println("The limit of adding the party details is exceeded and the last value is : "+rwcount);
							break mainloop;
						}
						driver.findElement(By.id(btnId)).click();
						driver.findElement(By.id(plaintiffnameid)).sendKeys(name);
						driver.findElement(By.id(plaintiffaddrid)).sendKeys(address);					
					}

				}
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}



	public void lgl_mDynamicAddRowObjects(String tableId, String btnId, String name,String address, String rCount) {

		try {
			//		preAction("Performing dynamic row operaion");
			lgl_wDynamicAddRowObjects(tableId, btnId, name, address,rCount);
			//		postAction("Performed dynamic row operaion");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}


	//  removing number of RowObjects for case party details
	// created by Hiren Kathiria on 6-6-2016


	private void lgl_wDynamicRemoveRowObjects(String tableId,String btnId,String rCount){

		String pltfnm = "";		
		String plaintiffname = "";  		
		String plaintiffnameval = "";		

		try{
			int count=Integer.parseInt(rCount);	
			int count1=Integer.parseInt(mGetPropertyFromFile("lgl_CasePartyDetPlaintiffDetailsAddCount"));
			int count2=Integer.parseInt(mGetPropertyFromFile("lgl_CasePartyDetDefendantDetailsAddCount"));
			int count3=Integer.parseInt(mGetPropertyFromFile("lgl_CasePartyDetPlaintiffDetailsRemoveCount"));
			int count4=Integer.parseInt(mGetPropertyFromFile("lgl_CasePartyDetDefendantDetailsRemoveCount"));

			// label for the outer Forloop
			mainloop:

				// Forloop for removing number of party details rows 	

				for(int a=0;a<count;a++)
				{
					WebElement table = driver.findElement(By.id(tableId));

					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int colcnt = cols.size();
						//				System.out.println("Number of columns " +colcnt);
						int Columnno=0;
						for(WebElement colElement:cols)
						{	
							plaintiffname=cols.get(0).findElement(By.tagName("input")).getAttribute("id");
							pltfnm =plaintiffname.substring(0, 8);
							plaintiffnameval=cols.get(0).findElement(By.tagName("input")).getAttribute("value");
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}

					// Check for 1st row and then select values
					if ((rwcount<=2) && (!plaintiffnameval.equals("0")))
					{
						System.out.println("The name of party details is " +plaintiffnameval);
					}


					// If it is not 1st row then click on add and then select values
					else
					{   
						System.out.println("row count is " +rwcount);
						Rowno=rwcount-1;
						String plaintiffnameid=pltfnm+Rowno;
						System.out.println("Id is " +plaintiffnameid);

						// break the Forloop if the limit of removing attendees exceeds
						if(count1<count3 || count2<count4)
						{
							System.out.println("Breaking");
							System.out.println("The remove count cannot be greater than add count ");
							break mainloop;
						}

						mCustomWait(1500);
						driver.findElement(By.id(btnId)).click();
						System.out.println("last removed id is " +plaintiffnameid );					
					}

				}
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}



	public void lgl_mDynamicRemoveRowObjects(String tableId, String btnId,String rCount) {

		try {
			//			preAction("Performing dynamic remove operation");
			lgl_wDynamicRemoveRowObjects(tableId, btnId,rCount);
			//			postAction("Performed dynamic remove operation");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}


	// DynamicRowObjects for reset script of case party details

	public void lgl_DynamicRowObjects(String tableId,String rCount){

		String pltfnm = "";
		String pltfaddr="";
		String plaintiffname = "";  
		String plaintiffaddr = ""; 
		String plaintiffnameval = "";
		String plaintiffaddrval= "";

		try{
			int count=Integer.parseInt(rCount);	


			// label for the outer Forloop
			mainloop:

				// Forloop for entering number of plaintiff and defendant details 	

				for(int a=0;a<count;a++)
				{
					WebElement table = driver.findElement(By.id(tableId));

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
							plaintiffname=cols.get(0).findElement(By.tagName("input")).getAttribute("id");
							pltfnm =plaintiffname.substring(0, 8);
							plaintiffnameval=cols.get(0).findElement(By.tagName("input")).getAttribute("Value");
							plaintiffaddr=cols.get(1).findElement(By.tagName("textarea")).getAttribute("id");
							pltfaddr =plaintiffaddr.substring(0, 8);
							plaintiffaddrval=cols.get(1).findElement(By.tagName("textarea")).getAttribute("value");
							if (Columnno==0)
							{
								System.out.println("Row "+Rowno+" Column "+Columnno+" Value "+plaintiffnameval+ " Id is " +plaintiffname);
							}

							else
							{
								System.out.println("Row "+Rowno+" Column "+Columnno+" Value "+plaintiffaddrval+ " Id is " +plaintiffaddr);
							}
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;

						if((plaintiffnameval.isEmpty()))
						{
							String plaintiffnameid=pltfnm+Rowno;
							String plaintiffaddrid=pltfaddr+Rowno;
							System.out.println("Name id is "+plaintiffnameid);
							System.out.println("Addr id is "+plaintiffaddrid);	

							String Pname=mGetText("id", plaintiffnameid);
							mAssert(Pname, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual Number :"+Pname+" Expected Number :"+ "");
							System.out.println("Actual is : " +Pname+ " Expected is : " + "");

							String Padd=mGetText("id", plaintiffaddrid);
							mAssert(Padd, "",mGetPropertyFromFile("lgl_IsDataMatch")+" Actual Number :"+Padd+" Expected Number :"+ "");
							System.out.println("Actual is : " +Padd+ " Expected is : " + "");
						}	
					}	
				}
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}



	//  adding number of attendees for case hearing entry details
	// created by Hiren Kathiria on 6-6-2016


	private void lgl_wDynamicAddAttendees(String tableId,String btnId,String name,String rCount){

		String attendnm = "";		
		String attendeename = "";  		
		String attendeenameval = "";		

		try{
			int count=Integer.parseInt(rCount);	

			// label for the outer Forloop
			mainloop:

				// Forloop for adding number of attendees 	

				for(int a=0;a<count;a++)
				{
					WebElement table = driver.findElement(By.id(tableId));

					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int colcnt = cols.size();
						//			System.out.println("Number of columns " +colcnt);
						int Columnno=0;
						for(WebElement colElement:cols)
						{	
							attendeename=cols.get(0).findElement(By.tagName("select")).getAttribute("id");
							attendnm =attendeename.substring(0, 9);
							attendeenameval=cols.get(0).findElement(By.tagName("select")).getAttribute("value");
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}

					// Check for 1st row and then select values
					if ((rwcount<=2) && (attendeenameval.equals("0")))
					{
						new Select(driver.findElement(By.id(attendeename))).selectByVisibleText(name);
					}


					// If it is not 1st row then click on add and then select values
					else
					{
						Rowno=Rowno-1;
						String attendeenameid=attendnm+Rowno;
						System.out.println("Id is " +attendeenameid);
						// break the Forloop if the limit of adding attendees exceeds
						if(rwcount>10)
						{
							System.out.println("Breaking");
							System.out.println("The limit of adding the attendees is exceeded and the last value is : "+rwcount);
							break mainloop;
						}

						driver.findElement(By.id(btnId)).click();
						mCustomWait(1000);
						new Select(driver.findElement(By.id(attendeenameid))).selectByVisibleText(name);				
					}

				}
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}


	public void lgl_mDynamicAddAttendees(String tableId, String btnId, String name,String rCount) {

		try {
			//		preAction("Performing dynamic add operaion");
			lgl_wDynamicAddAttendees(tableId, btnId, name,rCount);
			//		postAction("Performed dynamic add operaion");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}


	//removing number of attendees for case hearing entry details
	// created by Hiren Kathiria on 6-6-2016

	private void lgl_wDynamicRemoveAttendees(String tableId,String btnId,String rCount){

		String attendnm = "";		
		String attendeename = "";  		
		String attendeenameval = "";		

		try{
			int count=Integer.parseInt(rCount);	


			// label for the outer Forloop
			mainloop:

				// Forloop for removing number of attendees 	

				for(int a=0;a<count;a++)
				{
					WebElement table = driver.findElement(By.id(tableId));

					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;
					for(WebElement rowElement:rows)
					{
						List<WebElement> cols=rowElement.findElements(By.xpath("td"));
						int colcnt = cols.size();
						//				System.out.println("Number of columns " +colcnt);
						int Columnno=0;
						for(WebElement colElement:cols)
						{	
							attendeename=cols.get(0).findElement(By.tagName("select")).getAttribute("id");
							attendnm =attendeename.substring(0, 9);
							attendeenameval=cols.get(0).findElement(By.tagName("select")).getAttribute("value");
							Columnno=Columnno+1;
						}
						Rowno=Rowno+1;
					}


					// Check for 1st row and then select values
					if ((rwcount<=2) && (!attendeenameval.equals("0")))
					{
						System.out.println("The name of attendee is " +attendeenameval);
					}

					// If it is not 1st row then click on add and then select values
					else
					{   
						System.out.println("row count is " +rwcount);
						Rowno=rwcount-1;
						String attendeenameid=attendnm+Rowno;
						System.out.println("Id is " +attendeenameid);

						// break the Forloop if the limit of adding attendees exceeds
						if(rwcount<3)
						{
							System.out.println("Breaking");
							System.out.println("The limit of removing the attendees is exceeded and the last value is : "+rwcount);
							break mainloop;
						}

						//				new Select(driver.findElement(By.id(attendeenameid))).selectByVisibleText(name);
						mCustomWait(1500);
						driver.findElement(By.id(btnId)).click();
						System.out.println("last removed id is " +attendeenameid );					
					}

				}
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}



	public void lgl_mDynamicRemoveAttendees(String tableId, String btnId,String rCount) {

		try {
			//			preAction("Performing dynamic remove operaion");
			lgl_wDynamicRemoveAttendees(tableId, btnId,rCount);
			//			postAction("Performed dynamic remove operaion");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}


	// created by Hiren Kathiria on 8-6-2016
	//  adding a row for case hearing date entry 

	private void lgl_wDynamicAddRow(String tableId,String btnId,String hdate,String data,String rCount){

		String date = "";
		String prepare="";
		String sts="";
		String hearingdate = ""; 
		String preparation = ""; 
		String status="";
		String hearingdateval = "";
		String preparationval= "";
		String statusval="";

		try{
			int count=Integer.parseInt(rCount);	

			// Forloop for adding number of attendees 	

			for(int a=0;a<count;a++)
			{
				WebElement table = driver.findElement(By.id(tableId));

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
						hearingdate=cols.get(0).findElement(By.tagName("input")).getAttribute("id");
						date =hearingdate.substring(0,12);
						hearingdateval=cols.get(0).findElement(By.tagName("input")).getAttribute("Value");
						status=cols.get(1).findElement(By.tagName("input")).getAttribute("id");
						sts =hearingdate.substring(0,12);
						statusval=cols.get(1).findElement(By.tagName("input")).getAttribute("Value");
						preparation=cols.get(2).findElement(By.tagName("textarea")).getAttribute("id");
						prepare =preparation.substring(0, 10);
						preparationval=cols.get(2).findElement(By.tagName("textarea")).getAttribute("value");
						if (Columnno==0)
						{
							System.out.println("Row "+Rowno+" Column "+Columnno+" Value "+hearingdateval+ " Id is " +hearingdate);
						}

						else if(Columnno==1)
						{
							System.out.println("Row "+Rowno+" Column "+Columnno+" Value "+statusval+ " Id is " +status);
						}

						else 
						{
							System.out.println("Row "+Rowno+" Column "+Columnno+" Value "+preparationval+ " Id is " +preparation);
						}						
						Columnno=Columnno+1;						
					}

					Rowno=Rowno+1;
				}


				// Check for 1st row and then enter values
				if ((rwcount==2) && preparationval.isEmpty())
				{					
					//selecting hearing date
				//	String tokens[] =mGetPropertyFromFile("LGL_heHearingDateData").split("/");
					String tokens[] =hdate.split("/");
					System.out.println("i m selecting a date");
					String strtempdate= tokens[0];
					int cuDate=Integer.parseInt(strtempdate);
					String strnDate=Integer.toString(cuDate);
					System.out.println(tokens[2]);
					System.out.println(tokens[1]);
					System.out.println(strnDate);
					mGdatePicker(mGetPropertyFromFile("LGL_heHearingDateid"),tokens[2],tokens[1],strnDate);					
					mSendKeys("id", mGetPropertyFromFile("LGL_heImpNoteid"), mGetPropertyFromFile("LGL_heImpNoteData"));
					mCustomWait(1000);
				}


				// If it is not 1st row then click on add and then enter values
				else
				{	
					Rowno=Rowno-1;
					String hearingdateid=date+Rowno;
					String preparationid=prepare+Rowno;	
					driver.findElement(By.id(btnId)).click();
					mWaitForVisible("id",hearingdate);
					mCustomWait(1000);
					String tokens[] =hdate.split("/");
					System.out.println("i m selecting a date");
					String strtempdate= tokens[0];
					int cuDate=Integer.parseInt(strtempdate);
					String strnDate=Integer.toString(cuDate);
					System.out.println(tokens[2]);
					System.out.println(tokens[1]);
					System.out.println(strnDate);
					mGdatePicker(hearingdateid,tokens[2],tokens[1],strnDate);
					driver.findElement(By.id(preparationid)).clear();
					driver.findElement(By.id(preparationid)).sendKeys(data);							
				}

			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}


	public void lgl_mDynamicAddRow(String tableId, String btnId, String name,String data,String rCount) {

		try {
			//			preAction("Performing dynamic row operaion");
			lgl_wDynamicAddRow(tableId, btnId, name, data,rCount);
			//			postAction("Performed dynamic row operaion");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}


	// created by Hiren Kathiria on 8-6-2016
	//  Removing a row for case hearing date entry 

	private void lgl_wDynamicRemoveRow(String tableId,String btnId,String rCount){

		String date = "";					
		String hearingdate = ""; 					
		String hearingdateval = "";					

		try{
			int count=Integer.parseInt(rCount);	
			int count1=Integer.parseInt(mGetPropertyFromFile("LGL_AddRowCount"));
			int count2=Integer.parseInt(mGetPropertyFromFile("LGL_RemoveRowCount"));

			// label for the outer Forloop
			mainloop:

				// Forloop for removing number of attendees 	

				for(int a=0;a<count;a++)
				{
					WebElement table = driver.findElement(By.id(tableId));

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
							hearingdate=cols.get(0).findElement(By.tagName("input")).getAttribute("id");
							date =hearingdate.substring(0,12);
							hearingdateval=cols.get(0).findElement(By.tagName("input")).getAttribute("Value");															
							Columnno=Columnno+1;								
						}
						Rowno=Rowno+1;
					}		

					System.out.println("row count is " +rwcount);
					Rowno=rwcount-1;
					String hearingdateid=date+Rowno;
					System.out.println("Id is " +hearingdateid);

					// break the Forloop if the limit of adding attendees exceeds
					if(count1<count2)
					{
						System.out.println("Breaking");
						System.out.println("The remove count cannot be greater than add count ");
						break mainloop;
					}

					//					new Select(driver.findElement(By.id(attendeenameid))).selectByVisibleText(name);
					mCustomWait(1500);
					if(rwcount>2)
					{
						driver.findElement(By.id(btnId)).click();						
						System.out.println("last removed id is " +hearingdateid );	
					}				
				}
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}



	public void lgl_mDynamicRemoveRow(String tableId, String btnId,String rCount) {

		try {
			//					preAction("Performing dynamic row operaion");
			lgl_wDynamicRemoveRow(tableId, btnId,rCount);
			//					postAction("Performed dynamic row operaion");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}



	// created by Hiren Kathiria on 9-6-2016
	//  Adding Reference Evidences for Reference Evidences and Legal Opinion 


	private void lgl_wDynamicAddRefEvi(String rCount){

		try{

			int count=Integer.parseInt(rCount);	

			// Forloop for adding number of Reference Evidence 	

			for(int a=0;a<count;a++)
			{
				mWaitForVisible("css", mGetPropertyFromFile("LGL_roAddRefEvLinkid"));
				mCustomWait(2000);
				mClick("css", mGetPropertyFromFile("LGL_roAddRefEvLinkid"));

				//Type of Reference
				mWaitForVisible("id", mGetPropertyFromFile("LGL_roRefTypeid"));
				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("LGL_roRefTypeid"), mGetPropertyFromFile("LGL_roRefTypeData"));

				//selecting reference date
				mWaitForVisible("id",mGetPropertyFromFile("LGL_roRefDateid"));
				String tokens[] =mGetPropertyFromFile("LGL_roRefDateData").split("/");
				System.out.println("i m selecting a date");
				String strtempdate= tokens[0];
				int cuDate=Integer.parseInt(strtempdate);
				String strnDate=Integer.toString(cuDate);
				System.out.println(tokens[2]);
				System.out.println(tokens[1]);
				System.out.println(strnDate);
				mGdatePicker(mGetPropertyFromFile("LGL_roRefDateid"),tokens[2],tokens[1],strnDate);

				//ULB or Other ULB
				if(mGetPropertyFromFile("LGL_roRefTypeData").equals("ULB"))
				{
					//selecting reference case number
					mWaitForVisible("id", mGetPropertyFromFile("LGL_roRefCaseNoid"));
					mCustomWait(1000);
					mSelectDropDown("id",mGetPropertyFromFile("LGL_roRefCaseNoid"), mGetPropertyFromFile("LGL_roRefCaseNoData"));
				}
				else
				{
					//sending reference number
					mWaitForVisible("id", mGetPropertyFromFile("LGL_roOdrUlbRefCaseNoid"));
					mCustomWait(1000);
					//						mSendKeys("id", mGetPropertyFromFile("LGL_roOdrUlbRefCaseNoid"), lgl_RefCaseNo);
					mSendKeys("id", mGetPropertyFromFile("LGL_roOdrUlbRefCaseNoid"), mGetPropertyFromFile("LGL_roOdrUlbRefCaseNoData"));
				}

				//sending description
				mWaitForVisible("id", mGetPropertyFromFile("LGL_roMultDescid"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("LGL_roMultDescid"), mGetPropertyFromFile("LGL_roMultDescData"));

				//upload file
				//mWaitForVisible("id", mGetPropertyFromFile("LGL_roRefEvUploadid"));
				mCustomWait(1000);
				mUpload("id", mGetPropertyFromFile("LGL_roRefEvUploadid"), mGetPropertyFromFile("LGL_roRefEvUploadData"));

				//References Submit
				mWaitForVisible("xpath", mGetPropertyFromFile("LGL_roRefEvDetailsSubBtnid"));
				mCustomWait(2000);
				mClick("xpath", mGetPropertyFromFile("LGL_roRefEvDetailsSubBtnid"));
				mCustomWait(2000);
				mTakeScreenShot();;

				mWaitForVisible("css", mGetPropertyFromFile("LGL_roRefEvPopUpBoxid"));
				//References Submit PopUp
				mWaitForVisible("css", mGetPropertyFromFile("LGL_roRefEvDetailsFancyCloseid"));
				mCustomWait(1000);
				//String Ref1msg = mGetText("css", mGetPropertyFromFile("LGL_roRefEvPopUpAssertMsgid"));
				//mCustomWait(1000);
				//mAssert(Ref1msg, mGetPropertyFromFile("LGL_roRefEvPopUpAssertMsgData"), "Msg At References Submit :::: Actual :"+Ref1msg+" Expected :"+mGetPropertyFromFile("LGL_roRefEvPopUpAssertMsgData"));
				mCustomWait(1000);
				mTakeScreenShot();;
				mCustomWait(2000);
				mClick("css", mGetPropertyFromFile("LGL_roRefEvDetailsFancyCloseid"));
				mWaitForInvisible("css", mGetPropertyFromFile("LGL_roRefEvPopUpBoxid"));
				mCustomWait(1000);
				//System.out.println(Ref1msg);			
				System.out.println("a is = "+a);

				WebElement gridId=driver.findElement(By.id(mGetPropertyFromFile("LGL_roRefEviGridid")));
				WebElement headerId=driver.findElement(By.xpath(mGetPropertyFromFile("LGL_roRefEviHeaderid")));
				//					genericMAINetGridVerification(gridId,headerId,lgl_RefCaseNo,1,true);
				genericMAINetGridVerification(gridId,headerId,refCaseNo,1,true);

			}

		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	public void lgl_mDynamicAddRefEvi(String rCount) {

		try {
			//					preAction("Performing dynamic row operation");
			lgl_wDynamicAddRefEvi(rCount);
			//					postAction("Performed dynamic row operation");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}



	// created by Hiren Kathiria on 9-6-2016
	//  Adding Legal Opinion for Reference Evidences and Legal Opinion 

	private void lgl_wDynamicAddLglOpi(String rCount){

		try{

			int count=Integer.parseInt(rCount);	

			// Forloop for adding number of attendees 	

			for(int a=0;a<count;a++)
			{
				//Add legal opinion link
				mWaitForVisible("linkText", mGetPropertyFromFile("LGL_roAddLegalOpinionBtnid"));
				mCustomWait(2000);
				mClick("linkText", mGetPropertyFromFile("LGL_roAddLegalOpinionBtnid"));

				//Opinion by 
				mWaitForVisible("id", mGetPropertyFromFile("LGL_roOpinionByid"));
				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("LGL_roOpinionByid"), mGetPropertyFromFile("LGL_roOpinionByData"));

				mCustomWait(1000);
				if(mGetPropertyFromFile("LGL_roOpinionByData").equals("Empanelment Lawyer"))
				{
					//Advocate Name
					mWaitForVisible("id", mGetPropertyFromFile("LGL_roAttorneyNameid"));
					mCustomWait(1000);
					mSelectDropDown("id", mGetPropertyFromFile("LGL_roAttorneyNameid"), mGetPropertyFromFile("LGL_roAttorneyNameData"));
				}
				else
				{
					//sending Other Lawyer Name
					mWaitForVisible("id", mGetPropertyFromFile("LGL_roOtherLawyerNameid"));
					mCustomWait(1000);
					mSendKeys("id", mGetPropertyFromFile("LGL_roOtherLawyerNameid"),mGetPropertyFromFile("LGL_roOtherLawyerNameData"));
				}

				//selecting opinion date
				mWaitForVisible("id", mGetPropertyFromFile("LGL_roOpinionDateid"));
				mCustomWait(1000);
				String tokens1[] =mGetPropertyFromFile("LGL_roOpinionDateData").split("/");
				System.out.println("i m selecting a date");
				String strtempdate1= tokens1[0];
				int cuDate1=Integer.parseInt(strtempdate1);
				String strnDate1=Integer.toString(cuDate1);
				System.out.println(tokens1[2]);
				System.out.println(tokens1[1]);
				System.out.println(strnDate1);
				mGdatePicker(mGetPropertyFromFile("LGL_roOpinionDateid"),tokens1[2],tokens1[1],strnDate1);

				//sending opinion
				mWaitForVisible("id", mGetPropertyFromFile("LGL_roOpinionid"));
				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("LGL_roOpinionid"), mGetPropertyFromFile("LGL_roOpinionData"));

				//upload document
				//mWaitForVisible("id", mGetPropertyFromFile("LGL_roLegalOpinionUploadid"));
				mCustomWait(2000);
			//	mUpload("id",  mGetPropertyFromFile("LGL_roLegalOpinionUploadid"), mGetPropertyFromFile("LGL_roLegalOpinionUploadData"));

				//Add Legal Opinion Submit Btn
				mWaitForVisible("xpath", mGetPropertyFromFile("LGL_roLegalOpinionDetailsSubBtnid"));
				mCustomWait(2000);
				mTakeScreenShot();;
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("LGL_roLegalOpinionDetailsSubBtnid"));
				mCustomWait(2000);
				mTakeScreenShot();;

				mWaitForVisible("css", mGetPropertyFromFile("LGL_roLglOpPopUpBoxid"));
				//Getting text for asserting Pop up Message 
				mWaitForVisible("css", mGetPropertyFromFile("LGL_roLglOpnPopMsgFancyCloseid"));
				mCustomWait(1000);
				mTakeScreenShot();;
				//String Ref2msg = mGetText("css", mGetPropertyFromFile("LGL_roLglOpnPopAssertMsgid"));
				mCustomWait(1000);
				//System.out.println(Ref2msg);
			//	mAssert(Ref2msg, mGetPropertyFromFile("LGL_roLglOpnPopAssertMsgData"), "Msg at Legal Opinion Entry:::: Actual : "+Ref2msg+" Expected : "+mGetPropertyFromFile("LGL_roLglOpnPopAssertMsgData"));
				mCustomWait(2000);					
				mClick("css", mGetPropertyFromFile("LGL_roLglOpnPopMsgFancyCloseid"));
				mCustomWait(2000);					
				System.out.println("a is = "+a);
			}

			WebElement gridId=driver.findElement(By.id(mGetPropertyFromFile("LGL_roLglOpiGridid")));
			WebElement headerId=driver.findElement(By.xpath(mGetPropertyFromFile("LGL_roLglOpiHeaderid")));
			genericMAINetGridVerification(gridId,headerId,"ok",1,true);		

		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	public void lgl_mDynamicAddLglOpi(String rCount) {

		try {
			//	preAction("Performing dynamic row operaion");
			lgl_wDynamicAddLglOpi(rCount);
			//	postAction("Performed dynamic row operaion");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}



	//  adding number of RowObjects for add estate

	private void rnl_wDynamicAddRowObjects(String tableId,String name, String date,String date1,String Edit,String rCount){

		String estdesc = "";
		String estdate="";
		String estdate1="";
		String estadd="";		
		String estdescription = "";  
		String eststartdate = ""; 
		String estenddate = ""; 
		String estaddbtn = ""; 
		String estdescriptionval = "";  
		String eststartdateval = ""; 
		String estenddateval = "";
		String estaddbtnval = ""; 
		boolean enddate=false;

		try{
			int count=Integer.parseInt(rCount);	

			mainloop:

				// Forloop for entering number of estate details 	

				for(int a=0;a<count;a++)
				{
					WebElement table = driver.findElement(By.id(tableId));

					List<WebElement> rows = table.findElements(By.tagName("tr"));
					int rwcount =rows.size();
					System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

					// Iterate to get the value of each cell in table along with element id
					int Rowno=0;

					innerloop:

						for(int i=0;i<1;i++)
						{
							List<WebElement> cols=rows.get(i+1).findElements(By.xpath("td"));
							int Columnno=0;
							for(WebElement colElement:cols)
							{

								if (Columnno==0)
								{								
									estdescription=rows.get(i+1).findElement(By.xpath("./td[1]/input")).getAttribute("id");
									estdesc =estdescription.replaceAll("[0-9]", "");
									System.out.println("Row "+Rowno+" Column "+Columnno+" Value "+estdesc+ " Id is " +estdescription);
									boolean desc = driver.findElement(By.id(estdescription)).isEnabled();								
									if(desc)  {								
										mSelectAutoCompleteText("id",estdescription,name );								
									}										
								}

								if (Columnno==1)
								{								
									eststartdate=rows.get(i+1).findElement(By.xpath("./td[2]/input")).getAttribute("id");
									estdate =eststartdate.replaceAll("[0-9]", "");
									String Sdate = rows.get(i+1).findElement(By.xpath("./td[2]/input")).getAttribute("class");
									if(!Sdate.equalsIgnoreCase("width120 disablefield")) 
									{
										mDateSelect("id",eststartdate, date);		
									}																
									System.out.println("Row "+Rowno+" Column "+Columnno+" Value "+estdate+ " Id is " +eststartdate);
								}

								if(Columnno==2)
								{
									estenddate = rows.get(i+1).findElement(By.xpath("./td[3]/input")).getAttribute("id");
									estdate1 =estenddate.replaceAll("[0-9]", "");
									System.out.println(" id is " + estenddate);							

									String Edate = rows.get(i+1).findElement(By.xpath("./td[3]/input")).getAttribute("class").replaceAll("\\s+","");
									Edate=Edate.substring(0, 18);
									System.out.println("Edate is  " + Edate); 
									if(!Edate.equalsIgnoreCase("input2disablefield")) 
									{
										mDateSelect("id",estenddate, date1);		
									}								
								}

								if (Columnno==3)
								{
									estaddbtn=rows.get(i+1).findElement(By.xpath("./td[4]/a")).getAttribute("id");
									estadd =estaddbtn.replaceAll("[0-9]", "");
									System.out.println("estadd is :::: "+estaddbtn);

									if(rwcount>=2)
									{
										System.out.println("Breaking innerloop");									
										break innerloop;
									}
									driver.findElement(By.id(estaddbtn)).click();								
								}

								Columnno=Columnno+1;
								System.out.println("Column no. is :"+Columnno);
							}

							Rowno=Rowno+1;
							System.out.println("Row no. is :"+Rowno);
						}

					String[] string=mGetPropertyFromFile("rnl_LEMGrpEstDescpData").split(",");
					System.out.println("string array is : "+string[0]);
					System.out.println("string array is : "+string[1]); 
					System.out.println("string array size is : "+string.length);


					for(int b = 1;b<=count-1;b++)
					{
						Rowno=Rowno-1;
						String estdescid=estdesc+b;
						String estdateid=estdate+b;
						String estdate1id=estdate1+b;
						String estaddid=estadd+b;

						boolean desc = driver.findElement(By.id(estdescid)).isEnabled();								
						if(desc)  
						{								
							mSelectAutoCompleteText("id",estdescid,string[b]);								
						}

						String Sdate = driver.findElement(By.id(estdate1id)).getAttribute("class");
						if(!Sdate.equalsIgnoreCase("width120 disablefield")) 
						{
							mDateSelect("id",estdateid, date);		
						}

						if(Edit.equalsIgnoreCase("yes"))
						{ 
							String Edate = driver.findElement(By.id(estdate1id)).getAttribute("class").replaceAll("\\s+","");
							System.out.println("edate is : "+Edate); 
							if(!Edate.equalsIgnoreCase("input2validdisablefield")) 
							{
								mDateSelect("id",estdate1id, date1);
							}						
						}

						else
						{
							String Edate = driver.findElement(By.id(estdate1id)).getAttribute("class").replaceAll("\\s+","");
							if(!Edate.equalsIgnoreCase("input2disablefield")) 
							{
								mDateSelect("id",estdate1id, date1);
							}	
						}

						driver.findElement(By.id(estaddid)).click();

						if(rwcount==count+1)
						{
							System.out.println("Breaking mainloop");									
							break mainloop;
						}												
					}					
				}
		}

		catch(Exception e){
			e.printStackTrace();
		}

	}


	public void rnl_mDynamicAddRowObjects(String tableId, String name,String date,String date1,String Edit,String rCount) {

		try {
			//		preAction("Performing dynamic row operaion");
			rnl_wDynamicAddRowObjects(tableId, name, date,date1,Edit,rCount);
			//		postAction("Performed dynamic row operaion");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}


	//  editing number of RowObjects for tenant master

	private void rnl_wDynamicEditRowObjects(String tableId){

		try{				
			WebElement table = driver.findElement(By.xpath(tableId));
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount =rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

			if(rwcount==1)
			{
				mCustomWait(1000);
				mClick("xpath", mGetPropertyFromFile("rnl_TMTenantEditBtnID"));

				mCustomWait(1000);
				mSelectDropDown("id", mGetPropertyFromFile("rnl_TMOwnerTitleID"),mGetPropertyFromFile("rnl_TMEditOwnerTitleData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMOwnerNameID"),mGetPropertyFromFile("rnl_TMEditOwnerNameData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMOwnerAddressID"),mGetPropertyFromFile("rnl_TMEditOwnerAddressData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMOwnerContactNoID"),mGetPropertyFromFile("rnl_TMEditOwnerContactNoData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMOwnerMobileNoID"),mGetPropertyFromFile("rnl_TMEditOwnerMobileNoData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMOwnerMailID"),mGetPropertyFromFile("rnl_TMEditOwnerMailData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMOwnerPanNoID"),mGetPropertyFromFile("rnl_TMEditOwnerPanNoData"));

				mCustomWait(1000);
				mSendKeys("id", mGetPropertyFromFile("rnl_TMOwnerAadharNoID"),mGetPropertyFromFile("rnl_TMEditOwnerAadharNoData"));
				mTakeScreenShot();

				mCustomWait(1500);
				mClick("css", mGetPropertyFromFile("rnl_TMTenantDetailsSubmitID"));						
			}
		}

		catch(Exception e){
			e.printStackTrace();
		}

	}


	public void rnl_mDynamicEditRowObjects(String tableId) {

		try {
			//		preAction("Performing dynamic row operaion");
			rnl_wDynamicEditRowObjects(tableId);
			//		postAction("Performed dynamic row operaion");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}


	//  amount bifurcation of RowObjects for tenant contract

	private void rnl_wDynamicAmountBifurcation(String tableId){

		String taxdes = "";		
		String taxdescription = "";  		
		String taxdescriptionval = "";		
		String taxamt = "";		
		String taxamount = "";  		
		String taxamountval = "";
		String addbtn="";
		String addbutton = "";  		

		try{

			WebElement table = driver.findElement(By.id(tableId));
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount =rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

			// Iterate to get the value of each cell in table along with element id
			int Rowno=0;
			for(WebElement rowElement:rows)
			{
				List<WebElement> cols=rowElement.findElements(By.xpath("td"));
				int colcnt = cols.size();
				int Columnno=0;
				for(WebElement colElement:cols)
				{	
					taxdescription=cols.get(0).findElement(By.tagName("input")).getAttribute("id");
					taxdes =taxdescription.substring(0, 8);
					taxdescriptionval=cols.get(0).findElement(By.tagName("input")).getAttribute("value");
					Columnno=Columnno+1;

					taxamount=cols.get(1).findElement(By.tagName("input")).getAttribute("id");
					taxamt =taxamount.substring(0, 6);
					taxamountval=cols.get(1).findElement(By.tagName("input")).getAttribute("value");
					Columnno=Columnno+1;

					addbutton=cols.get(2).findElement(By.tagName("input")).getAttribute("id");
				}
				Rowno=Rowno+1;
			}


			System.out.println("row count is " +rwcount);
			Rowno=Rowno-1;
			String taxdescriptionid=taxdes+Rowno;
			String taxamountid=taxamt+Rowno;

			loop:
				for(int i=0;i<=2;i++)
				{
					driver.findElement(By.xpath("./td/a")).click();
					mCustomWait(1000);
					driver.findElement(By.id(taxdescriptionid)).sendKeys(mGetPropertyFromFile("rnl_TgTaxDescrptnData"+i));
					driver.findElement(By.id(taxamountid)).sendKeys(mGetPropertyFromFile("rnl_TgTaxAmountData"+i));	

					if(i>2)
					{
						System.out.println("the loop is breaking"); 
						break loop;
					}
				}

		}

		catch(Exception e){
			e.printStackTrace();
		}

	}


	public void rnl_mDynamicAmountBifurcation(String tableId) {

		try {
			//		preAction("Performing dynamic row operaion");
			rnl_wDynamicAmountBifurcation(tableId);
			//		postAction("Performed dynamic row operaion");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	// author hiren kathiria on 2/9/2016
	//  reading the grid table for RNL bill generation 

	/*private void rnl_wDynamicReadbillgenGrid(String tableId){

		String chkbox = "";		String contno = "";		String tenName = "";  String tenNo = "";

		try{			
			WebElement tablea = driver.findElement(By.id(tableId));
			List<WebElement> rows = null;
			rows = tablea.findElements(By.tagName("tr"));
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
				//				for(WebElement colElement:cols)


				for(int i=1;i<=colCount;i++)	
				{
					if(Columnno==0)
					{
						chkbox=rowElement.findElement(By.xpath("td["+i+"]")).getAttribute("id");  
						System.out.println("chkbox id is == "+chkbox);
					}	

					if(Columnno==1)
					{
						contno=rowElement.findElement(By.xpath("td["+i+"]")).getText();
						System.out.println("contno is == "+contno);
						BillGeneration.add(contno);
					}

					if(Columnno==2)
					{
						tenName=rowElement.findElement(By.xpath("td["+i+"]")).getText();
						System.out.println("tenName is == "+tenName);
						BillGeneration.add(tenName);
					}

					if(Columnno==3)
					{
						tenNo=rowElement.findElement(By.xpath("td["+i+"]")).getText();
						System.out.println("tenNo is == "+tenNo);
						BillGeneration.add(tenNo);
					}

					Columnno=Columnno+1;
					System.out.println("Column no. is :"+Columnno);
				}			

				System.out.println(BillGeneration); 			

				Rowno=Rowno+1;
				System.out.println("Row no. is :"+Rowno);				
			}
			ArrayofBillGeneration.add(BillGeneration);
			System.out.println(ArrayofBillGeneration); 
		}

		catch(Exception e){
			e.printStackTrace();
		}

	}*/



/*	public void rnl_mDynamicReadbillgenGrid(String tableId) {

		try {
			rnl_wDynamicReadbillgenGrid(tableId);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}*/


	// author hiren kathiria on 3/9/2016
	//  reading the show error table for RNL bill generation  

	/*private void rnl_wDynamicReadShowErrorGrid(String tableId,int number){

		String contno = "";		String errormsg = "";  String tenNo = "";

		try{			
			WebElement tablea = driver.findElement(By.id(tableId));
			List<WebElement> rows = null;
			rows = tablea.findElements(By.tagName("tr"));
			System.out.println(rows);
			int rwcount =rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
			if(number==rwcount-1)
			{
				System.out.println("the count of error msg matches"); 
			}

			// Iterate to get the value of each cell in table along with element id
			int Rowno=0;
			for(WebElement rowElement:rows)
			{
				List<WebElement> cols=rowElement.findElements(By.xpath("td"));
				int colCount=cols.size();
				System.out.println("Columns are : "+colCount);
				int Columnno=0;

				for(int i=1;i<=colCount;i++)	
				{
					if(Rowno>=1)
					{	
						if(Columnno==0)
						{
							contno=rowElement.findElement(By.xpath("td["+i+"]")).getText();
							System.out.println("contno is == "+contno);
							log.info(contno);
							Errormsg.add(contno);
						}	

						if(Columnno==1)
						{
							tenNo=rowElement.findElement(By.xpath("td["+i+"]")).getText();
							System.out.println("tenNo is == "+tenNo);
							log.info(tenNo);
							Errormsg.add(tenNo);
						}

						if(Columnno==2)
						{
							errormsg=rowElement.findElement(By.xpath("td["+i+"]")).getText();
							System.out.println("tenNo is == "+errormsg);
							log.info(errormsg);
							Errormsg.add(errormsg);
						}
					}
					Columnno=Columnno+1;
					System.out.println("Column no. is :"+Columnno);
				}			

				System.out.println(Errormsg);		
				Rowno=Rowno+1;
				System.out.println("Row no. is :"+Rowno);	

			}
			ArrayofErrormsg.add(Errormsg);
			System.out.println(ArrayofErrormsg); 
		}

		catch(Exception e){
			e.printStackTrace();
		}
	}

*/

	/*public void rnl_mDynamicReadShowErrorGrid(String tableId,int number) {

		try {
			rnl_wDynamicReadShowErrorGrid(tableId,number);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}*/



	// author hiren kathiria on 3/9/2016
	// changes done on 6/9/2016  
	// changes done on 8/9/2016

	//  reading the print table for RNL bill printing  

	/*private void rnl_wDynamicReadBillPrintGrid(String tableId,String data1){

		String contno = "";	String tenNo = ""; String tenName = ""; String billNo = ""; String fromDate = ""; String toDate = "";

		try{

			int count=Integer.parseInt(data1);	
			mClick("id", mGetPropertyFromFile("rnl_BprGridTableContNoSortid"));	
			mCustomWait(3000); 

			for(int a=0;a<count;a++)
			{										
				WebElement tablea = driver.findElement(By.id(tableId));
				List<WebElement> rows = null;
				rows = tablea.findElements(By.tagName("tr"));
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

					BillPrint = new ArrayList<String>();

					for(int i=0;i<=colCount;i++)	
					{	

						if(Rowno>=1)
						{		

							if(Columnno==3)
							{
								contno=rowElement.findElement(By.xpath("td[3]")).getText();
								System.out.println("contno is == "+contno);
								BillPrint.add(contno);			  
							}	

							if(Columnno==4)
							{
								tenNo=rowElement.findElement(By.xpath("td[4]")).getText();
								System.out.println("tenNo is == "+tenNo);							
								BillPrint.add(tenNo);
							}

							if(Columnno==5)
							{
								tenName=rowElement.findElement(By.xpath("td[5]")).getText();
								System.out.println("tenName is == "+tenName);					
								BillPrint.add(tenName);
							}

							if(Columnno==6)
							{
								billNo=rowElement.findElement(By.xpath("td[6]")).getText();
								System.out.println("billNo is == "+billNo);			
								BillPrint.add(billNo);
							}	

							if(Columnno==7)
							{
								fromDate=rowElement.findElement(By.xpath("td[7]")).getText();
								System.out.println("fromDate is == "+fromDate);		
								BillPrint.add(fromDate);
							}

							if(Columnno==8)
							{
								toDate=rowElement.findElement(By.xpath("td[8]")).getText();
								System.out.println("toDate is == "+toDate);		
								BillPrint.add(toDate);
							}
						}
						Columnno=Columnno+1;
						System.out.println("Column no. is :"+Columnno);
					}		

					System.out.println("BillPrint array is : "+BillPrint);		

					if(Rowno>0)
					{					
						ArrayofBillPrint.add(BillPrint);
						System.out.println("ArrayofBillPrint arraylist is : "+ArrayofBillPrint);
					}
					//System.out.println("ArrayofBillPrint arraylist is : "+ArrayofBillPrint);

					Rowno=Rowno+1;
					System.out.println("Row no. is :"+Rowno);						

				}				

				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("rnl_BprPrintBillChkBoxid"));					


				//Print Bill
				mWaitForVisible("id", mGetPropertyFromFile("rnl_BprGenerateBillBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				//				mClick("id", mGetPropertyFromFile("rnl_BprGenerateBillBtnid"));
				mCustomWait(1500);	
				mChallanPDFReader();
				//				pdfpattern.rnl_BillPrintingPDF(pdfoutput,TCtenantName,TCcontractNo,TCcontractFromDate,TCtenantNo,TCcontractToDate,TCcontractAmount);
				mCustomWait(2500);	

				if(count>1)
				{
					mClick("css", mGetPropertyFromFile("rnl_BprGridTableNextPageid"));	
				}

			}	

			System.out.println("BillPrint size is : "+BillPrint.size());
			System.out.println("ArrayofBillPrint size is : "+ArrayofBillPrint.size());			
		}

		catch(Exception e){
			e.printStackTrace();
		}
	}
*/


	/*public void rnl_mDynamicReadBillPrintGrid(String tableId,String data1) {

		try {
			rnl_wDynamicReadBillPrintGrid(tableId,data1);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}*/





	// author hiren kathiria on 6/9/2016
	//  reading the print table for RNL rent payment schedule  

	/*private void rnl_wDynamicRentPayScdhGrid(String tableId){

		String schPeriod=""; String schAmnt=""; String dueDate=""; String fromDate=""; 

		String toDate=""; String billAmnt=""; String dueAmnt="";

		try{			
			WebElement tablea = driver.findElement(By.id(tableId));
			List<WebElement> rows = null;
			rows = tablea.findElements(By.tagName("tr"));
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
						if(Columnno==2)
						{
							schPeriod=rowElement.findElement(By.xpath("td[2]")).getText();
							System.out.println("schPeriod is == "+schPeriod); 					
							RentPaySchedule.add(schPeriod);
						}										

						if(Columnno==3)
						{
							schAmnt=rowElement.findElement(By.xpath("td[3]")).getText();
							System.out.println("schAmnt is == "+schAmnt); 					
							RentPaySchedule.add(schAmnt);
						}	

						if(Columnno==4)
						{
							dueDate=rowElement.findElement(By.xpath("td[4]")).getText();
							System.out.println("dueDate is == "+dueDate);							
							RentPaySchedule.add(dueDate);
						}

						if(Columnno==5)
						{
							fromDate=rowElement.findElement(By.xpath("td[5]")).getText();
							System.out.println("fromDate is == "+fromDate);					
							RentPaySchedule.add(fromDate);
						}

						if(Columnno==6)
						{
							toDate=rowElement.findElement(By.xpath("td[6]")).getText();
							System.out.println("toDate is == "+toDate);			
							RentPaySchedule.add(toDate);
						}	

						if(Columnno==7)
						{
							billAmnt=rowElement.findElement(By.xpath("td[7]")).getText();
							System.out.println("billAmnt is == "+billAmnt);		
							RentPaySchedule.add(billAmnt);
						}

						if(Columnno==8)
						{
							dueAmnt=rowElement.findElement(By.xpath("td[8]")).getText();
							System.out.println("dueAmnt is == "+dueAmnt);
							if(dueAmnt.equals("0"))
							{
								dueCount=dueCount+1;
							}
							RentPaySchedule.add(dueAmnt);
						}
					}
					Columnno=Columnno+1;
					System.out.println("Column no. is :"+Columnno);
				}		

				System.out.println(RentPaySchedule);
				Rowno=Rowno+1;
				System.out.println("Row no. is :"+Rowno);						
			}
			ArrayofRentPaySchedule.add(RentPaySchedule);
			System.out.println(ArrayofRentPaySchedule);						
		}

		catch(Exception e){
			e.printStackTrace();
		}
	}

*/

	/*public void rnl_mDynamicRentPayScdhGrid(String tableId) {

		try {
			rnl_wDynamicRentPayScdhGrid(tableId);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}*/



	/*// author hiren kathiria on 7/9/2016
	//  reading the print table for RNL payment history  

	private void rnl_wDynamicRPSPaymentHistoryGrid(String tableId){

		String ReceiptNo=""; String ReceiptDate=""; String ReceiptAmount=""; 

		try{			
			WebElement tablea = driver.findElement(By.id(tableId));
			List<WebElement> rows = null;
			rows = tablea.findElements(By.tagName("tr"));
			System.out.println(rows);
			int rwcount =rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

			if(rwcount==dueCount+1)
			{
				System.out.println("both the count matches : "+(rwcount==dueCount+1)); 

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
							if(Columnno==2)
							{
								ReceiptNo=rowElement.findElement(By.xpath("td[2]")).getText();
								System.out.println("ReceiptNo is == "+ReceiptNo); 					
								PaymentHistory.add(ReceiptNo);
							}										

							if(Columnno==3)
							{
								ReceiptDate=rowElement.findElement(By.xpath("td[3]")).getText();
								System.out.println("ReceiptDate is == "+ReceiptDate); 					
								PaymentHistory.add(ReceiptDate);
							}	

							if(Columnno==4)
							{
								ReceiptAmount=rowElement.findElement(By.xpath("td[4]")).getText();
								System.out.println("ReceiptAmount is == "+ReceiptAmount);							
								PaymentHistory.add(ReceiptAmount);
							}

						}
						Columnno=Columnno+1;
						System.out.println("Column no. is :"+Columnno);
					}		

					System.out.println(PaymentHistory);
					Rowno=Rowno+1;
					System.out.println("Row no. is :"+Rowno);						
				}
				ArrayofPaymentHistory.add(PaymentHistory);
				System.out.println(ArrayofPaymentHistory);	
			}

			else
			{
				System.out.println("the count of due amount does not match");				  			
			}

		}

		catch(Exception e){
			e.printStackTrace();
		}
	}
*/


	/*public void rnl_mDynamicRPSPaymentHistoryGrid(String tableId) {

		try {
			rnl_wDynamicRPSPaymentHistoryGrid(tableId);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}
*/



	//	reading the charge table for LOI Generation of town planning  
//hiren kathiria

	/*private void tp_wDynamicLOIChargeTableGrid(String tableId){

		try{
			WebElement table = driver.findElement(By.className(tableId));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount =rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

			List<WebElement> cols = rows.get(1).findElements(By.xpath("td"));
			int colcount= cols.size();
			System.out.println(colcount);

			for(int a=0;a<rwcount-2;a++)			
			{

				// Iterate to get the value of each cell in table along with element id

				for(int b=0;b<colcount;b++)
				{
					if(b==0)
					{	
						mCustomWait(1000); 																						
						driver.findElement(By.id("selectFlag"+(a))).click();
						System.out.println("Chkbox id is : "+driver.findElement(By.id("selectFlag"+(a))));
						mCustomWait(1000); 															
					}	
					//System.out.println("col no. is: "+b);
				}	
				//System.out.println("row no. is: "+a);
			}								
		}

		catch(Exception e){
			e.printStackTrace();
		}
	}

*/

	/*public void tp_mDynamicLOIChargeTableGrid(String tableId) {

		try {
			tp_wDynamicLOIChargeTableGrid(tableId);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}
*/




	//	reading the charge table for LOI Generation of town planning  
	//hiren kathiria

	/*private void tp_wDynamicUsageTypeTableGrid(String tableId,String data,String data1){

		try{
			WebElement table = driver.findElement(By.id(tableId));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount =rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

			List<WebElement> cols = rows.get(1).findElements(By.xpath("td"));
			int colcount= cols.size();
			System.out.println(colcount);

			String[] string=data.split(",");
			System.out.println("usage type array is : "+string[0]);
			System.out.println("usage type array size is : "+string.length);

			String[] string1=data1.split(",");
			System.out.println("covered area array is : "+string1[0]);
			System.out.println("covered area array size is : "+string1.length);

			if(string.length<=rwcount)	
			{	
				for(int a=0;a<rwcount-1;a++)
				{

					// Iterate to get the value of each cell in table along with element id

					for(int b=0;b<colcount;b++)							
					{
						if(b==0)
						{	
							mSelectDropDown( "id", "tppUsageType1"+(a+0)+(a+1),string[a]); 
							String value=mCaptureSelectedValue("id", "tppUsageType1"+(a+0)+(a+1));
							System.out.println("valueis : "+value);
							mCustomWait(1000); 															
						}									
						if(b==1)
						{																								
							mSendKeys("id", "tppSplitArea"+a, string1[a]);							
							String value1=mGetText("id", "tppSplitArea"+a, "value");
							System.out.println("value1is : "+value1);
							mCustomWait(1000); 															
						}							
					}								
				}	
			}	

			else
			{
				for(int a=0;a<string.length;a++)
				{

					// Iterate to get the value of each cell in table along with element id

					for(int b=0;b<colcount;b++)							
					{
						if(b==0)
						{	
							mSelectDropDown( "id", "tppUsageType1"+(a+0)+(a+1),string[a]); 
							String value=mCaptureSelectedValue("id", "tppUsageType1"+(a+0)+(a+1));
							System.out.println("valueis : "+value);
							mCustomWait(1000); 															
						}									
						if(b==1)
						{																								
							mSendKeys("id", "tppSplitArea"+a, string1[a]);
							String value1=mGetText("id", "tppSplitArea"+a, "value");
							System.out.println("value1is : "+value1);
							mCustomWait(1000); 															
						}						
					}	
					if(a<string.length-1)
					{	
					mClick("id","addRow");
					}
				}	
			}	
		}

		catch(Exception e){
			e.printStackTrace();
		}
	}
*/


	/*public void tp_mDynamicUsageTypeTableGrid(String tableId,String data,String data1) {

		try {
			tp_wDynamicUsageTypeTableGrid(tableId,data,data1); 
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}*/


	//	reading the charge table for LOI Generation of town planning  
	//hiren kathiria

	private void tp_wDynamicLOIChargeTableGrid(String tableId){

		try{
			WebElement table = driver.findElement(By.className(tableId));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount =rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

			List<WebElement> cols = rows.get(1).findElements(By.xpath("td"));
			int colcount= cols.size();
			System.out.println(colcount);

			for(int a=0;a<rwcount-2;a++)			
			{

				// Iterate to get the value of each cell in table along with element id

				for(int b=0;b<colcount;b++)
				{
					if(b==0)
					{																								
						driver.findElement(By.id("selectFlag"+(a))).click();
						System.out.println("Chkbox id is : "+driver.findElement(By.id("selectFlag"+(a))));
						mCustomWait(1000); 															
					}	
					//System.out.println("col no. is: "+b);
				}	
				//System.out.println("row no. is: "+a);
			}								
		}

		catch(Exception e){
			e.printStackTrace();
		}
	}



	public void tp_mDynamicLOIChargeTableGrid(String tableId) {

		try {
			tp_wDynamicLOIChargeTableGrid(tableId);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}





	//	reading the charge table for LOI Generation of town planning  
	//hiren kathiria

	private void tp_wDynamicUsageTypeTableGrid(String tableId,String data,String data1){

		try{
			WebElement table = driver.findElement(By.id(tableId));

			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int rwcount =rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

			List<WebElement> cols = rows.get(1).findElements(By.xpath("td"));
			int colcount= cols.size();
			System.out.println(colcount);

			String[] string=data.split(",");
			System.out.println("usage type array is : "+string[0]);
			System.out.println("usage type array size is : "+string.length);

			String[] string1=data1.split(",");
			System.out.println("covered area array is : "+string1[0]);
			System.out.println("covered area array size is : "+string1.length);

			if(string.length<=rwcount)	
			{	
				for(int a=0;a<rwcount-1;a++)
				{

					// Iterate to get the value of each cell in table along with element id

					for(int b=0;b<colcount;b++)							
					{
						if(b==0)
						{	
							mSelectDropDown( "id", "tppUsageType1"+(a+0)+(a+1),string[a]); 
							String value=mCaptureSelectedValue("id", "tppUsageType1"+(a+0)+(a+1));
							System.out.println("valueis : "+value);
							mCustomWait(1000); 															
						}									
						if(b==1)
						{																								
							mSendKeys("id", "tppSplitArea"+a, string1[a]);							
							String value1=mGetText("id", "tppSplitArea"+a, "value");
							System.out.println("value1is : "+value1);
							mCustomWait(1000); 															
						}							
					}								
				}	
			}	

			else
			{
				for(int a=0;a<string.length;a++)
				{

					// Iterate to get the value of each cell in table along with element id

					for(int b=0;b<colcount;b++)							
					{
						if(b==0)
						{	
							mSelectDropDown( "id", "tppUsageType1"+(a+0)+(a+1),string[a]); 
							String value=mCaptureSelectedValue("id", "tppUsageType1"+(a+0)+(a+1));
							System.out.println("valueis : "+value);
							mCustomWait(1000); 															
						}									
						if(b==1)
						{																								
							mSendKeys("id", "tppSplitArea"+a, string1[a]);
							String value1=mGetText("id", "tppSplitArea"+a, "value");
							System.out.println("value1is : "+value1);
							mCustomWait(1000); 															
						}						
					}	
					if(a<string.length-1)
					{	
						mClick("id","addRow");
					}
				}	
			}	
		}

		catch(Exception e){
			e.printStackTrace();
		}
	}



	public void tp_mDynamicUsageTypeTableGrid(String tableId,String data,String data1) {

		try {
			tp_wDynamicUsageTypeTableGrid(tableId,data,data1); 
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}


	// author hiren kathiria on 6/9/2016
	//  reading the print table for RNL rent payment schedule  

	private void rnl_wDynamicRentPayScdhGrid(String tableId){

		String schPeriod=""; String schAmnt=""; String dueDate=""; String fromDate=""; 

		String toDate=""; String billAmnt=""; String dueAmnt="";

		try{			
			WebElement tablea = driver.findElement(By.id(tableId));
			List<WebElement> rows = null;
			rows = tablea.findElements(By.tagName("tr"));
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
						if(Columnno==2)
						{
							schPeriod=rowElement.findElement(By.xpath("td[2]")).getText();
							System.out.println("schPeriod is == "+schPeriod); 					
							RentPaySchedule.add(schPeriod);
						}										

						if(Columnno==3)
						{
							schAmnt=rowElement.findElement(By.xpath("td[3]")).getText();
							System.out.println("schAmnt is == "+schAmnt); 					
							RentPaySchedule.add(schAmnt);
						}	

						if(Columnno==4)
						{
							dueDate=rowElement.findElement(By.xpath("td[4]")).getText();
							System.out.println("dueDate is == "+dueDate);							
							RentPaySchedule.add(dueDate);
						}

						if(Columnno==5)
						{
							fromDate=rowElement.findElement(By.xpath("td[5]")).getText();
							System.out.println("fromDate is == "+fromDate);					
							RentPaySchedule.add(fromDate);
						}

						if(Columnno==6)
						{
							toDate=rowElement.findElement(By.xpath("td[6]")).getText();
							System.out.println("toDate is == "+toDate);			
							RentPaySchedule.add(toDate);
						}	

						if(Columnno==7)
						{
							billAmnt=rowElement.findElement(By.xpath("td[7]")).getText();
							System.out.println("billAmnt is == "+billAmnt);		
							RentPaySchedule.add(billAmnt);
						}

						if(Columnno==8)
						{
							dueAmnt=rowElement.findElement(By.xpath("td[8]")).getText();
							System.out.println("dueAmnt is == "+dueAmnt);
							if(dueAmnt.equals("0"))
							{
								dueCount=dueCount+1;
							}
							RentPaySchedule.add(dueAmnt);
						}
					}
					Columnno=Columnno+1;
					System.out.println("Column no. is :"+Columnno);
				}		

				System.out.println(RentPaySchedule);
				Rowno=Rowno+1;
				System.out.println("Row no. is :"+Rowno);						
			}
			ArrayofRentPaySchedule.add(RentPaySchedule);
			System.out.println(ArrayofRentPaySchedule);						
		}

		catch(Exception e){
			e.printStackTrace();
		}
	}



	public void rnl_mDynamicRentPayScdhGrid(String tableId) {

		try {
			rnl_wDynamicRentPayScdhGrid(tableId);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}








	// author hiren kathiria on 7/9/2016
	//  reading the print table for RNL payment history  

	private void rnl_wDynamicRPSPaymentHistoryGrid(String tableId){

		String ReceiptNo=""; String ReceiptDate=""; String ReceiptAmount=""; 

		try{			
			WebElement tablea = driver.findElement(By.id(tableId));
			List<WebElement> rows = null;
			rows = tablea.findElements(By.tagName("tr"));
			System.out.println(rows);
			int rwcount =rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);

			if(rwcount==dueCount+1)
			{
				System.out.println("both the count matches : "+(rwcount==dueCount+1)); 

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
							if(Columnno==2)
							{
								ReceiptNo=rowElement.findElement(By.xpath("td[2]")).getText();
								System.out.println("ReceiptNo is == "+ReceiptNo); 					
								PaymentHistory.add(ReceiptNo);
							}										

							if(Columnno==3)
							{
								ReceiptDate=rowElement.findElement(By.xpath("td[3]")).getText();
								System.out.println("ReceiptDate is == "+ReceiptDate); 					
								PaymentHistory.add(ReceiptDate);
							}	

							if(Columnno==4)
							{
								ReceiptAmount=rowElement.findElement(By.xpath("td[4]")).getText();
								System.out.println("ReceiptAmount is == "+ReceiptAmount);							
								PaymentHistory.add(ReceiptAmount);
							}

						}
						Columnno=Columnno+1;
						System.out.println("Column no. is :"+Columnno);
					}		

					System.out.println(PaymentHistory);
					Rowno=Rowno+1;
					System.out.println("Row no. is :"+Rowno);						
				}
				ArrayofPaymentHistory.add(PaymentHistory);
				System.out.println(ArrayofPaymentHistory);	
			}

			else
			{
				System.out.println("the count of due amount does not match");				  			
			}

		}

		catch(Exception e){
			e.printStackTrace();
		}
	}



	public void rnl_mDynamicRPSPaymentHistoryGrid(String tableId) {

		try {
			rnl_wDynamicRPSPaymentHistoryGrid(tableId);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}







	// author hiren kathiria on 2/9/2016
	//  reading the grid table for RNL bill generation 

	private void rnl_wDynamicReadbillgenGrid(String tableId){

		String chkbox = "";		String contno = "";		String tenName = "";  String tenNo = "";

		try{			
			WebElement tablea = driver.findElement(By.id(tableId));
			List<WebElement> rows = null;
			rows = tablea.findElements(By.tagName("tr"));
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
				//				for(WebElement colElement:cols)


				for(int i=1;i<=colCount;i++)	
				{
					if(Columnno==0)
					{
						chkbox=rowElement.findElement(By.xpath("td["+i+"]")).getAttribute("id");  
						System.out.println("chkbox id is == "+chkbox);
					}	

					if(Columnno==1)
					{
						contno=rowElement.findElement(By.xpath("td["+i+"]")).getText();
						System.out.println("contno is == "+contno);
						BillGeneration.add(contno);
					}

					if(Columnno==2)
					{
						tenName=rowElement.findElement(By.xpath("td["+i+"]")).getText();
						System.out.println("tenName is == "+tenName);
						BillGeneration.add(tenName);
					}

					if(Columnno==3)
					{
						tenNo=rowElement.findElement(By.xpath("td["+i+"]")).getText();
						System.out.println("tenNo is == "+tenNo);
						BillGeneration.add(tenNo);
					}

					Columnno=Columnno+1;
					System.out.println("Column no. is :"+Columnno);
				}			

				System.out.println(BillGeneration); 			

				Rowno=Rowno+1;
				System.out.println("Row no. is :"+Rowno);				
			}
			ArrayofBillGeneration.add(BillGeneration);
			System.out.println(ArrayofBillGeneration); 
		}

		catch(Exception e){
			e.printStackTrace();
		}

	}



	public void rnl_mDynamicReadbillgenGrid(String tableId) {

		try {
			rnl_wDynamicReadbillgenGrid(tableId);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}






	// author hiren kathiria on 3/9/2016
	//  reading the show error table for RNL bill generation  

	private void rnl_wDynamicReadShowErrorGrid(String tableId,int number){

		String contno = "";		String errormsg = "";  String tenNo = "";

		try{			
			WebElement tablea = driver.findElement(By.id(tableId));
			List<WebElement> rows = null;
			rows = tablea.findElements(By.tagName("tr"));
			System.out.println(rows);
			int rwcount =rows.size();
			System.out.println("NUMBER OF ROWS IN THIS TABLE = "+rwcount);
			if(number==rwcount-1)
			{
				System.out.println("the count of error msg matches"); 
			}

			// Iterate to get the value of each cell in table along with element id
			int Rowno=0;
			for(WebElement rowElement:rows)
			{
				List<WebElement> cols=rowElement.findElements(By.xpath("td"));
				int colCount=cols.size();
				System.out.println("Columns are : "+colCount);
				int Columnno=0;

				for(int i=1;i<=colCount;i++)	
				{
					if(Rowno>=1)
					{	
						if(Columnno==0)
						{
							contno=rowElement.findElement(By.xpath("td["+i+"]")).getText();
							System.out.println("contno is == "+contno);
							log.info(contno);
							Errormsg.add(contno);
						}	

						if(Columnno==1)
						{
							tenNo=rowElement.findElement(By.xpath("td["+i+"]")).getText();
							System.out.println("tenNo is == "+tenNo);
							log.info(tenNo);
							Errormsg.add(tenNo);
						}

						if(Columnno==2)
						{
							errormsg=rowElement.findElement(By.xpath("td["+i+"]")).getText();
							System.out.println("tenNo is == "+errormsg);
							log.info(errormsg);
							Errormsg.add(errormsg);
						}
					}
					Columnno=Columnno+1;
					System.out.println("Column no. is :"+Columnno);
				}			

				System.out.println(Errormsg);		
				Rowno=Rowno+1;
				System.out.println("Row no. is :"+Rowno);	

			}
			ArrayofErrormsg.add(Errormsg);
			System.out.println(ArrayofErrormsg); 
		}

		catch(Exception e){
			e.printStackTrace();
		}
	}



	public void rnl_mDynamicReadShowErrorGrid(String tableId,int number) {

		try {
			rnl_wDynamicReadShowErrorGrid(tableId,number);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}








	// author hiren kathiria on 3/9/2016
	// changes done on 6/9/2016  
	// changes done on 8/9/2016

	//  reading the print table for RNL bill printing  

	private void rnl_wDynamicReadBillPrintGrid(String tableId,String data1){

		String contno = "";	String tenNo = ""; String tenName = ""; String billNo = ""; String fromDate = ""; String toDate = "";

		try{

			int count=Integer.parseInt(data1);	
			mClick("id", mGetPropertyFromFile("rnl_BprGridTableContNoSortid"));	
			mCustomWait(3000); 

			for(int a=0;a<count;a++)
			{										
				WebElement tablea = driver.findElement(By.id(tableId));
				List<WebElement> rows = null;
				rows = tablea.findElements(By.tagName("tr"));
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

					BillPrint = new ArrayList<String>();

					for(int i=0;i<=colCount;i++)	
					{	

						if(Rowno>=1)
						{		

							if(Columnno==3)
							{
								contno=rowElement.findElement(By.xpath("td[3]")).getText();
								System.out.println("contno is == "+contno);
								BillPrint.add(contno);			  
							}	

							if(Columnno==4)
							{
								tenNo=rowElement.findElement(By.xpath("td[4]")).getText();
								System.out.println("tenNo is == "+tenNo);							
								BillPrint.add(tenNo);
							}

							if(Columnno==5)
							{
								tenName=rowElement.findElement(By.xpath("td[5]")).getText();
								System.out.println("tenName is == "+tenName);					
								BillPrint.add(tenName);
							}

							if(Columnno==6)
							{
								billNo=rowElement.findElement(By.xpath("td[6]")).getText();
								System.out.println("billNo is == "+billNo);			
								BillPrint.add(billNo);
							}	

							if(Columnno==7)
							{
								fromDate=rowElement.findElement(By.xpath("td[7]")).getText();
								System.out.println("fromDate is == "+fromDate);		
								BillPrint.add(fromDate);
							}

							if(Columnno==8)
							{
								toDate=rowElement.findElement(By.xpath("td[8]")).getText();
								System.out.println("toDate is == "+toDate);		
								BillPrint.add(toDate);
							}
						}
						Columnno=Columnno+1;
						System.out.println("Column no. is :"+Columnno);
					}		

					System.out.println("BillPrint array is : "+BillPrint);		

					if(Rowno>0)
					{					
						ArrayofBillPrint.add(BillPrint);
						System.out.println("ArrayofBillPrint arraylist is : "+ArrayofBillPrint);
					}
					//System.out.println("ArrayofBillPrint arraylist is : "+ArrayofBillPrint);

					Rowno=Rowno+1;
					System.out.println("Row no. is :"+Rowno);						

				}				

				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("rnl_BprPrintBillChkBoxid"));					


				//Print Bill
				mWaitForVisible("id", mGetPropertyFromFile("rnl_BprGenerateBillBtnid"));
				mCustomWait(1000);
				mTakeScreenShot();
				mCustomWait(1000);
				mClick("id", mGetPropertyFromFile("rnl_BprGenerateBillBtnid"));
				mCustomWait(1500);	
				mChallanPDFReader();
				//pdfpattern.rnl_BillPrintingPDF(pdfoutput,TCtenantName,TCcontractNo,TCcontractFromDate,TCtenantNo,TCcontractToDate,TCcontractAmount);
				mCustomWait(2500);	
 
				if(count>1)
				{
					mClick("css", mGetPropertyFromFile("rnl_BprGridTableNextPageid"));	
				}

			}	

			System.out.println("BillPrint size is : "+BillPrint.size());
			System.out.println("ArrayofBillPrint size is : "+ArrayofBillPrint.size());			
		}

		catch(Exception e){
			e.printStackTrace();
		}
	}



public void rnl_mDynamicReadBillPrintGrid(String tableId,String data1) {

		try {
			rnl_wDynamicReadBillPrintGrid(tableId,data1);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}






}




