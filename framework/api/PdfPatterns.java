package api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.border.MatteBorder;

import common.CommonMethods;
import errorhandling.MainetCustomExceptions;
import excelreader.ExcelWriting;

/**
 * @author Tejas.Kotekar
 * @since  14.10.2015
 *
 */
public class PdfPatterns extends CommonMethods {

	public static String Challanno;

	public static String name;
	//	public static String licenseNum ;
	////////////////////
	public static String billPrint_Usage_Type;
	public static String printedAppNo;
	public static String applcntName; 
	public static String Rcptamt;

	public static String ChlnRegenRecpt_DiscountAmt;
	public static String ChlnRegenRecpt_RainWaterDiscountAmt;
	public static String totRebate;
	
	

	//public static String Rbt;
	public static Integer TotalRebate = new Integer(0);
	public static String s1;
	public static String objHLP_HearingDate1=null;
	public static String Obj_HearingDate2=null;
	public static String Obj_Hearing_Date;

	public static String ptRcptamt;
	public static String ptRcptPropNo;
	/////////////////////

	public static void patternChallanPdf(String output,String appNo, String applicantName){

		ExcelWriting EW =new ExcelWriting();
		//Define patterns where expected data is expected
		Pattern chalappno = Pattern.compile("(?<=Application No./LOI No. :)\\s*([0-9]+)\\s*(|\n)");

		Pattern appchalno = Pattern.compile("(?<=Chall`an No. :)\\s*([0-9]+)\\s*(|\n)");

		Pattern appcntname = Pattern.compile("(?<=Applicant Name :)\\s*(.+?)\\s*(?=Applicant Name :)");

		//Pattern chalnamt = Pattern.compile("(?<=Amount Payable In Words :)\\s*([0-9]+)\\s*(|\n)");

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

	}	
	public static void patternChallanPdf(String output){

		Pattern challanno=Pattern.compile("(?<=Challan No. : )\\s*([0-9]+)\\s*(|\n)");
		Matcher matcher111=challanno.matcher(pdfoutput);
	
	if (matcher111.find()) {

		
		String challannoa=matcher111.group(1);
		System.out.println("challannoa=>"+challannoa);
		swapchallnno=challannoa;
		challanNumber.add(swapchallnno);
		System.out.println("challanNumber+"+challanNumber);
		}
		else
		{
			System.out.println("Application Number Does Not Match");
		}	


	}	
	public static void patternBirthCertPdf(String output,String birthRegNo, String dateofBirth, String childNm, String parentsNm){

		String name =" ";
		int flag=0;


		Pattern chldnm = Pattern.compile("(?<=FemaleName: Sex:)(.*)\\s*");

		Pattern parentsnm = Pattern.compile("(?<=Name of Father:Name of Mother :)(.*)\\s*");

		Pattern birthdt = Pattern.compile("(?<=Date of Birth:)\\s*([0-9]+.[0-9]+.[0-9]+)\\s*(|\n)");

		Pattern birthregno = Pattern.compile("\\s*([0-9]+.[0-9]+.[0-9]+)\\s*");

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


	}

	public static void patternDeathCertPdf(String output,String deathRegNo, String dateofDeath, String deceasedNm, String relativeNm){


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
	}

	public static void patternRevisedSpecialNoticePdf(String output,String propNo,String propAddrs){

		int flag=0;
		Pattern noticeNumber = Pattern.compile("(?<=Notice Date :)\\s*(.*)");

		Pattern propNumber = Pattern.compile("(?<=Property No :)\\s*(.*)");

		Pattern dueDate = Pattern.compile("(?<=Due Date     :)\\s*(.*)");

		Pattern propOwnerName = Pattern.compile("(?<=To,)\\s*(.*)");

		Pattern jointPropOwnerName = Pattern.compile("(?<=Additional Property Owner :)\\s*(.*)");

		Pattern ward = Pattern.compile("\\s*(.+?)\\s*(?=Sir / Madam,)");

		Pattern balancePayable = Pattern.compile("(?<=Balance Payable)\\s*(.*)");

		Pattern afterAuthoAmt = Pattern.compile("\\s*(.+?)\\s*(?=Ward :)");

		Pattern beforeAuthoAmt = Pattern.compile("(?<=Total Tax Amount :)\\s*(.*)");

		//Pattern propOwnerAddr = Pattern.compile("(.+?),(.+?)");
		Pattern propOwnerAddr = Pattern.compile("(.+?),(.*)");

		Pattern taxDetails = Pattern.compile("(?<=Before Authorization)\\s*(.*)");

		Pattern befAuthoRatableArea = Pattern.compile("\\s*(.+?)\\s*(?=Total Tax Amount :)");

		Pattern aftAuthoRatableArea = Pattern.compile("\\s*(.+?)\\s*(?=Total :)");

		Pattern befAuthoUsageType = Pattern.compile("(?<=Property Details - Self Assessment/Department Entry)\\s*(.*)",Pattern.DOTALL);

		Pattern aftAuthoUsageType = Pattern.compile("(?<=Property Details - After Authorization of Self Assessment/Department Entry)\\s*(.*)",Pattern.DOTALL);

		Pattern ARV = Pattern.compile("(?s)Total :\\s(.*)Due Date     :");

		/*Pattern deathregno = Pattern.compile("\\s*([0-9]+)\\s*(?=([0-9]+.[0-9]+.[0-9]+))");

		Pattern deathcertno = Pattern.compile("(?<=Certificate No.)\\s*(.*)");*/


		Matcher matcher9 = noticeNumber.matcher(output);
		Matcher matcher10 = propNumber.matcher(output);
		Matcher matcher11 = dueDate.matcher(output);
		Matcher matcher12 = propOwnerName.matcher(output);
		Matcher matcher13 = jointPropOwnerName.matcher(output);
		Matcher matcher14 = ward.matcher(output);
		Matcher matcher15 = balancePayable.matcher(output);
		Matcher matcher16 = afterAuthoAmt.matcher(output);
		Matcher matcher17 = beforeAuthoAmt.matcher(output);
		Matcher matcher18 = propOwnerAddr.matcher(output);
		Matcher matcher19 = taxDetails.matcher(output);
		Matcher matcher20 = befAuthoRatableArea.matcher(output);
		Matcher matcher21 = aftAuthoRatableArea.matcher(output);
		Matcher matcher22 = befAuthoUsageType.matcher(output);
		Matcher matcher23 = aftAuthoUsageType.matcher(output);
		Matcher matcher24 = ARV.matcher(output);
		/*Matcher matcher12 = deathregno.matcher(output);
		Matcher matcher13 = deathcertno.matcher(output);
		 */

		if (matcher9.find())
		{
			String noticeNum = matcher9.group(1);
			System.out.println("Notice number printed in Revised Special Notice is : " + noticeNum);

		}

		if (matcher10.find())
		{
			String propNum = matcher10.group(1);
			System.out.println("Property number printed in Revised Special Notice is : " + propNum);
		}

		/*if (propNo.equals(propNum))
			{
				System.out.println("Property number matches");
			}
			else
			{
				System.out.println("Property number does not matches");
			}*/

		if (matcher11.find())
		{
			String dueDt = matcher11.group(1);

			System.out.println("Due Date of Special Notice printed in Revised Special Notice is : " + dueDt);

		}

		if (matcher12.find())
		{
			String propOwnerNm = matcher12.group(1);

			System.out.println("Property owner name printed in Revised Special Notice is : " + propOwnerNm);

		}

		if (matcher13.find())
		{
			String jointPropOwnerNm = matcher13.group(1);

			System.out.println("Additional Property owner name printed in Revised Special Notice is : " + jointPropOwnerNm);

		}

		if (matcher14.find())
		{
			String wardId = matcher14.group(1);

			System.out.println("Ward ID printed in Revised Special Notice is : " + wardId);

		}

		if (matcher15.find())
		{
			String balPayable = matcher15.group(1);

			System.out.println("Balance Payable printed in Revised Special Notice is : " + balPayable);
		}

		if (matcher16.find())
		{
			String afterAuthoAmount = matcher16.group(1);

			System.out.println("After Authorization Amount printed in Revised Special Notice is : " + afterAuthoAmount);
		}

		if (matcher17.find())
		{
			String beforeAuthoAmount = matcher17.group(1);

			System.out.println("Before Authorization Amount printed in Revised Special Notice is : " + beforeAuthoAmount);

		}

		while (matcher18.find())
		{

			String propOwnerAddrs = matcher18.group(0);
			String propOwnerAdrs = propOwnerAddrs.replaceAll(",", "");
			String propOwnerAddress = propOwnerAdrs.replaceAll(" ","");
			System.out.println("Property Owner Address printed in Revised Special Notice is : " + propOwnerAddress);
			/*if (propAddrs.equals(propOwnerAddress))
			{
				System.out.println("Property Owner Address matches");
				flag=1;
			}
			else
			{
				System.out.println("Property Owner Address does not matches");
			}
			if 	(flag == 1)
				break;*/

		}

		if (matcher19.find())
		{
			String taxName = matcher19.group(1);

			//System.out.println("Tax details printed in Revised Special Notice is : " + taxName);

			String[] txNM = taxName.split("(\\d+)");
			System.out.println("Tax details printed in Revised Special Notice is : " + txNM[0]);
		}

		if (matcher20.find())
		{

			String befAuthoRatableAr = matcher20.group(1);

			//System.out.println("Ratable Area and ARV before authorization printed in Revised Special Notice is : " + befAuthoRatableAr);

			String[] bfRateAreaARV = befAuthoRatableAr.split("(\\s+)");
			System.out.println("Ratable Area before authorization printed in Revised Special Notice is : " + bfRateAreaARV[0]+" and ARV is : "+bfRateAreaARV[1]);

		}

		if (matcher21.find())
		{

			String aftAuthoRatableAr = matcher21.group(1);

			//System.out.println("Ratable Area and ARV before authorization printed in Revised Special Notice is : " + aftAuthoRatableAr);

			String[] afRateAreaARV = aftAuthoRatableAr.split("(\\s+)");
			System.out.println("Ratable Area after authorization printed in Revised Special Notice is : " + afRateAreaARV[0]+" and ARV is : "+afRateAreaARV[1]);

		}

		if (matcher22.find())
		{
			String bfUsgType = matcher22.group(0);
			//System.out.println("Usage type before authorization printed in Revised Special Notice is : " + usgType);

			String[] bfUsgTyp = bfUsgType.split("\\r?\\n");
			System.out.println("Usage types before authorization printed in Revised Special Notice are : " + bfUsgTyp[1]+" and "+bfUsgTyp[2]);
			System.out.println("Ratable Area for Vacant Land before authorization printed in Revised Special Notice is :"+bfUsgTyp[3]);
			System.out.println("Locations before authorization printed in Revised Special Notice are : "+bfUsgTyp[5]+" and "+bfUsgTyp[6]);
			System.out.println("Construction Class before authorization printed in Revised Special Notice are : "+bfUsgTyp[7]+" and "+bfUsgTyp[8]);
		}

		if (matcher23.find())
		{
			String afUsgType = matcher23.group(0);
			//System.out.println("Usage type before authorization printed in Revised Special Notice is : " + usgType);

			String[] afUsgTyp = afUsgType.split("\\r?\\n");
			System.out.println("Usage types after authorization printed in Revised Special Notice are : " + afUsgTyp[1]+" and : "+afUsgTyp[2]);
			System.out.println("Ratable Area for Vacant Land after authorization printed in Revised Special Notice is :"+afUsgTyp[3]);
			System.out.println("Locations after authorization printed in Revised Special Notice are : "+afUsgTyp[5]+" and "+afUsgTyp[6]);
			System.out.println("Construction Class after authorization printed in Revised Special Notice are : "+afUsgTyp[7]+" and "+afUsgTyp[8]);
		}

		if (matcher24.find())
		{
			String ARVofVacantLand = matcher24.group(1);

			//System.out.println("ARV for Vacant Land before Authorization printed in Revised Special Notice is : " + ARVofVacantLand);
			String[] txNM = ARVofVacantLand.split("\\r?\\n");
			System.out.println("ARV for Vacant Land before Authorization printed in Revised Special Notice is : " + txNM[12]+ " and after authorization is : "+txNM[13]);

		}


		/*if (matcher12.find())
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
		 */
		/*if (matcher13.find())
			{
				//System.out.println(matcher13.group(1));		

				String xxx = matcher13.group(1);

				Pattern pattern1 = Pattern.compile("(?<=/)\\s*([0-9]+)");
				Matcher matcher14 = pattern1.matcher(xxx);

				if (matcher14.find())
				{
					System.out.println("Certificate no. is "+matcher14.group(1));
				}
			}*/


		else
		{
			System.out.println("Regex failed");
		}

	}


	public static void patternNewMarketLicensePdf(String output,String licHolderName,String busiName)

	{
		int flag=0;

		Pattern licenseNumber = Pattern.compile("(?<=Business Address)\\s*(.*)");
		Pattern licenseValidity = Pattern.compile("(?<=License Valid From)\\s*(.*)");
		Pattern licHoldrName = Pattern.compile("(?<=Business Address)\\s*(.*)",Pattern.DOTALL);
		//Pattern licHoldrName = Pattern.compile("(?<=Business Address)\\s*(.*)\\s*(|\n)*(?=Address Line 1 : )",Pattern.DOTALL);
		Pattern address=Pattern.compile("(?<=Address Line 1 :)\\s*");
		Pattern totalFees = Pattern.compile("(?<=Total Fees )\\s*(.*)");
		System.out.println("totalFees==>"+totalFees);
		Pattern service =Pattern.compile("(?<=Trade License Department)\\s(.*)");
		Pattern jowner=Pattern.compile("(?<=Joint Owner :)\\s(.*)");
		System.out.println("service==>"+service);

		Matcher matcher1 = licenseNumber.matcher(pdfoutput);
		Matcher matcher2 = licenseValidity.matcher(pdfoutput);
		Matcher matcher3 = licHoldrName.matcher(pdfoutput);
		Matcher matcher4 = totalFees.matcher(pdfoutput);
		Matcher matcher7=jowner.matcher(pdfoutput);
		Matcher matcher8=address.matcher(pdfoutput);



		if (matcher1.find())
		{
			String licenseNum = matcher1.group(1);

			System.out.println("License number printed in License copy is :--> " + licenseNum);
			licensenum_newtrade=licenseNum;



			System.out.println("******************************************************************************");


		}
		if (matcher2.find())
		{
			String licenseValid = matcher2.group(0);
			System.out.println("License validity printed in License copy is :--> " + licenseValid);
			String[] fromto=licenseValid.split("To");
			System.out.println(fromto[0]);
			System.out.println(fromto[1]);

		}

		while (matcher3.find())
		{   
			////////////////Holder Name on License Print PDF

			String licenseHolderNm = matcher3.group(0);

			String[] licHolderNm = licenseHolderNm.split("\\n");
			System.out.println("License Holder Name array==>>>"+licHolderNm.length);

			String Lic_name = licHolderNm[3];
			System.out.println("License holder's name printed in License copy is : " +Lic_name);

			pdf_new_holdername.add(Lic_name);
			System.out.println("Holder Name on License Print is : " +pdf_new_holdername);

			////////////////Holder Business Name on License Print PDF
			System.out.println("Business name printed in License copy is : " + licHolderNm[4]);

			pdf_new_holder_BusinessName.add(licHolderNm[4].trim());

			System.out.println("Business Address on PDF of License Print is== "+pdf_new_holder_BusinessName);

		}

		////////////////Holder Total Fees on License Print PDF
		if (matcher4.find())
		{
			String totalLicFees = matcher4.group(1);
			Pattern pattern5 = Pattern.compile("(\\d+.\\d+)");
			Matcher matcher5 = pattern5.matcher(totalLicFees);

			if (matcher5.find())
			{
				System.out.println("Total License fee printed in License copy is :--> "+matcher5.group(1));
				String tfees=matcher5.group(1);
				System.out.println("Total License fees on pdf of Licence Print is ==>"+tfees);

				pdf_Total_Fees_List.add(tfees);
				System.out.println("List of Total Fees on PDF of License Print is"+pdf_Total_Fees_List);
			}
		}

		if(matcher7.find())
		{
			String jowner_pdf=matcher7.group(0);
			System.out.println("joint owner name on report is--> "+jowner_pdf);

		}

		if(matcher8.find())
		{
			String pdf_address=matcher8.group(0);
			System.out.println("=================="+pdf_address);
		}

		else
		{
			System.out.println("Regex failed");
		}
	}

	public static void pattern_NewTrade_IntimationPdf(String output)
	{
		Pattern Intimation_HolderName = Pattern.compile("(?<=To,)\\s*(.*)");
		Matcher matcher9 = Intimation_HolderName.matcher(pdfoutput);
		Pattern Intimation_AppLetterDate = Pattern.compile("(?<=LOI No. )\\s*(.*)");
		Matcher matcher10 = Intimation_AppLetterDate.matcher(pdfoutput);
		Pattern Intimation_TotalAmount = Pattern.compile("(?<=Date)\\s*(.*)");
		Matcher matcher11 = Intimation_TotalAmount.matcher(pdfoutput);
		Pattern Intimation_ApplicationNo = Pattern.compile("(?<=Application No.)\\s*(.*)(|\n)*(?= and)"); // To capture Application No.Left boundary word 'Application No.' & Right boundary word 'and'
		Matcher matcher12 = Intimation_ApplicationNo.matcher(pdfoutput);
		Pattern Intimation_ApplicationDate = Pattern.compile("(?<=dated  )\\s*(.*)");
		Matcher matcher13 = Intimation_ApplicationDate.matcher(pdfoutput);
		Pattern Intimation_LicenseFee = Pattern.compile("(?<=Market License Fee )\\s*(.*)(|\n)*(?=1)");
		Matcher matcher14 = Intimation_LicenseFee.matcher(pdfoutput);
		Pattern Intimation_Discount = Pattern.compile("(?<=)\\s*(.*)(?=Discount:)"); // To capture 'Discount' using only Right boundary
		Matcher matcher15 = Intimation_Discount.matcher(pdfoutput);
		Pattern Intimation_LOIno = Pattern.compile("(?<=)\\s*(.*)(?=LOI No.)"); // To capture 'Discount' using only Right boundary
		Matcher matcher16 = Intimation_LOIno.matcher(pdfoutput);
		Pattern Intimation_LetterDate = Pattern.compile("(?<=LOI No.)\\s*(.*)");
		Matcher matcher17 = Intimation_LetterDate.matcher(pdfoutput);
		Pattern Intimation_ServiceName = Pattern.compile("(?<=Discount:)\\s*(.*)");
		Matcher matcher18 = Intimation_ServiceName.matcher(pdfoutput);

		if (matcher9.find())
		{
			String IntiHolder = matcher9.group(0);

			System.out.println("Intimation Holder Name printed in License copy is :--> " + IntiHolder);


			Inti_NewLicense_HolderNm.add(IntiHolder);
			System.out.println("Intimation New Trade Holder Name List = "+Inti_NewLicense_HolderNm);


			System.out.println("******************************************************************************");
		}

		if (matcher10.find())
		{
			String IntiLetter = matcher10.group(0);

			System.out.println("Intimation Letter printed in License copy is :--> " + IntiLetter);

			System.out.println("******************************************************************************");
		}

		if (matcher11.find())
		{
			String IntiTotalAmount = matcher11.group(1);

			System.out.println("Intimation Total Amount printed in License copy is :--> " + IntiTotalAmount);

			Intimation_NewTrade_TotalAmountList.add(IntiTotalAmount);

			System.out.println("Intimation time Total Amount is=="+Intimation_NewTrade_TotalAmountList);

			System.out.println("******************************************************************************");
		}

		if (matcher12.find())
		{
			String IntiAppNo = matcher12.group(1);

			System.out.println("Intimation Application Numbar printed in License copy is :--> " + IntiAppNo);

			System.out.println("******************************************************************************");
		}

		if (matcher13.find())
		{
			String IntiAppDate = matcher13.group(1);

			System.out.println("Intimation Application Date printed in License copy is :--> " + IntiAppDate);

			System.out.println("******************************************************************************");
		}
		if (matcher14.find())
		{
			String IntiLicenseFee = matcher14.group(1);

			System.out.println("Intimation Market License Fee printed in License copy is :--> " + IntiLicenseFee);

			Intimation_MKTLicenseFee.add(IntiLicenseFee);

			System.out.println("******************************************************************************");
		}
		if (matcher15.find())
		{
			String IntiLicenseDiscount = matcher15.group(1);

			System.out.println("Intimation Market License Discount printed in License copy is :--> " + IntiLicenseDiscount);

			Intimation_MKTRebate.add(IntiLicenseDiscount);

			System.out.println("******************************************************************************");
		}
		if (matcher16.find())
		{
			String IntiLicenseLOIno = matcher16.group(1);

			System.out.println("Intimation LOI No. printed in License copy is :--> " + IntiLicenseLOIno);

			Inti_LOINo.add(IntiLicenseLOIno);

			System.out.println("******************************************************************************");
		}
		if (matcher17.find())
		{
			String IntiLetterDate = matcher17.group(1);

			System.out.println("Intimation Letter Date printed in License copy is :--> " + IntiLetterDate);

			System.out.println("******************************************************************************");
		}
		if (matcher18.find())
		{
			String IntiServiceName = matcher18.group(1);

			System.out.println("Intimation Service Name printed in License copy is :--> " + IntiServiceName);

			System.out.println("******************************************************************************");

		}
	}

	public static void pattern_ChngBusiNm_IntimationPdf(String output)
	{

		Pattern IntiChngBusiNm_HolderName = Pattern.compile("(?<=To,)\\s*(.*)(|\n)*(?= :)");
		Matcher matcher19 = IntiChngBusiNm_HolderName.matcher(output);
		Pattern IntiChngBusiNm_TotalAmount = Pattern.compile("(?<=Fee )\\s*(.*)(|\n)*(?=1)");
		Matcher matcher21 = IntiChngBusiNm_TotalAmount.matcher(output);
		Pattern IntiChngBusiNm_ApplicationNo = Pattern.compile("(?<=Application No.)\\s*(.*)(|\n)*(?= and)"); // To capture Application No.Left boundary word 'Application No.' & Right boundary word 'and'
		Matcher matcher22 = IntiChngBusiNm_ApplicationNo.matcher(output);
		Pattern IntiChngBusiNm_ApplicationDate = Pattern.compile("(?<=dated  )\\s*(.*)");
		Matcher matcher23 = IntiChngBusiNm_ApplicationDate.matcher(output);
		Pattern IntiChngBusiNm_LicenseFee = Pattern.compile("(?<=Market License Fee )\\s*(.*)(|\n)*(?=1)");
		Matcher matcher24 = IntiChngBusiNm_LicenseFee.matcher(output);
		Pattern IntiChngBusiNm_Discount = Pattern.compile("(?<=)\\s*(.*)(?=Discount:)"); // To capture 'Discount' using only Right boundary
		Matcher matcher25 = IntiChngBusiNm_Discount.matcher(output);
		Pattern IntiChngBusiNm__LOIno = Pattern.compile("(?<=)\\s*(.*)(?=LOI No.)"); // To capture 'Discount' using only Right boundary
		Matcher matcher26 = IntiChngBusiNm__LOIno.matcher(output);
		Pattern IntiChngBusiNm_LetterDate = Pattern.compile("(?<=LOI No.)\\s*(.*)");
		Matcher matcher27 = IntiChngBusiNm_LetterDate.matcher(output);
		Pattern IntiChngBusiNm_ServiceName = Pattern.compile("(?<=Discount:)\\s*(.*)");
		Matcher matcher28 = IntiChngBusiNm_ServiceName.matcher(output);
		Pattern IntiChngBusiNm_JointOwnerName = Pattern.compile("(?<=Joint Owner: )\\s*(.*)");
		Matcher matcher29 = IntiChngBusiNm_JointOwnerName.matcher(output);
		System.out.println("patterns=========================== Change in Business Name ===============================>");

		if (matcher19.find())
		{
			String IntiChngBusiNameHolder = matcher19.group(0);
			System.out.println("Intimation Change in Business Name Holder Name printed in License copy is :--> " + IntiChngBusiNameHolder);

			IntiLetter_ChngBusiNm_HolderNm.add(IntiChngBusiNameHolder);
			System.out.println("ArrayList of Intimation Change in Business Name Holder Name printed in License copy is :--> " + IntiLetter_ChngBusiNm_HolderNm);
			System.out.println("******************************************************************************");
		}



		if (matcher21.find())
		{
			String IntiChngBusiNameTotalAmount = matcher21.group(1);

			System.out.println("Intimation Change in Business Name Total Amount printed in License copy is :--> " + IntiChngBusiNameTotalAmount);

			IntiLetter_ChngBusiNameTotalAmount.add(IntiChngBusiNameTotalAmount);

			System.out.println("******************************************************************************");
		}

		if (matcher22.find())
		{
			String IntiChngBusiNameAppNo = matcher22.group(1);

			System.out.println("Intimation Change in Business Name Application Numbar printed in License copy is :--> " + IntiChngBusiNameAppNo);

			System.out.println("******************************************************************************");
		}

		if (matcher23.find())
		{
			String IntiChngBusiNameAppDate = matcher23.group(1);

			System.out.println("Intimation Change in Business Name Application Date printed in License copy is :--> " + IntiChngBusiNameAppDate);

			System.out.println("******************************************************************************");
		}
		if (matcher24.find())
		{
			String IntiChngBusiNameLicenseFee = matcher24.group(1);

			System.out.println("Intimation Change in Business Name Market License Fee printed in License copy is :--> " + IntiChngBusiNameLicenseFee);

			System.out.println("******************************************************************************");
		}
		if (matcher25.find())
		{
			String IntiChngBusiNameLicenseDiscount = matcher25.group(1);

			System.out.println("Intimation Change in Business Name Market License Discount printed in License copy is :--> " + IntiChngBusiNameLicenseDiscount);

			System.out.println("******************************************************************************");
		}
		if (matcher26.find())
		{
			String IntiChngBusiNameLicenseLOIno = matcher26.group(1);

			System.out.println("Intimation Change in Business Name LOI No. printed in License copy is :--> " + IntiChngBusiNameLicenseLOIno);



			System.out.println("******************************************************************************");
		}
		if (matcher27.find())
		{
			String IntiChngBusiNameLetterDate = matcher27.group(1);

			System.out.println("Intimation Change in Business Name Letter Date printed in License copy is :--> " + IntiChngBusiNameLetterDate);

			System.out.println("******************************************************************************");
		}
		if (matcher28.find())
		{
			String IntiChngBusiNameServiceName = matcher28.group(1);

			System.out.println("Intimation Change in Business Name Service Name printed in License copy is :--> " + IntiChngBusiNameServiceName);

			System.out.println("******************************************************************************");
		}
		/*if (matcher29.find())
		{
			String IntiChngBusiNameJointOwnerName = matcher29.group(1);

			System.out.println("Intimation Change in Business Name Joint Owner Name printed in License copy is :--> " + IntiChngBusiNameJointOwnerName);

			System.out.println("******************************************************************************");
		}*/


	}


	public static void pattern_RenewalLicIntimationLetter(String output)
	{
		try
		{
			Pattern Rl_Subject = Pattern.compile("(?<=Discount:)\\s*(.*)\\s*(|\n)*(?=Application No.)");		
			Matcher matcher81 = Rl_Subject.matcher(output);

			if (matcher81.find())
			{
				String Rl_Subject1  = matcher81.group(1).trim();
				System.out.println("Letter Subject is : "+Rl_Subject1);			
			} 
			else
			{
				System.out.println("no result");
			}	

			Pattern Rl_LetterType = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Trade License Department)");;		
			Matcher matcher89 = Rl_LetterType.matcher(output);

			if (matcher89.find())
			{
				String Rl_LetterType1  = matcher89.group(1).trim();
				System.out.println("Letter Type is : "+Rl_LetterType1);			
			} 
			else
			{
				System.out.println("no result");
			}	

			Pattern Rl_ApplNo = Pattern.compile("(?<=Application No.)\\s*(.*)\\s*(|\n)*(?=and)");		
			Matcher matcher82 = Rl_ApplNo.matcher(output);

			if (matcher82.find())
			{
				String Rl_ApplNo1  = matcher82.group(1).trim();
				System.out.println("Application No of intimitaion Letter is : "+Rl_ApplNo1);
			}
			else
			{
				System.out.println("no result");
			}

			Pattern Rl_Appdatedate = Pattern.compile("(?<=and dated)\\s*(.*)");		
			Matcher matcher83 = Rl_Appdatedate.matcher(output);

			if (matcher83.find())
			{
				String Rl_Appdatedate1  = matcher83.group(1).trim();
				System.out.println("Inspection date is : "+Rl_Appdatedate1);
			}
			else
			{
				System.out.println("no result");
			}


			Pattern Rl_loiNo = Pattern.compile("(?<=:)\\s*(.*)(?=LOI No.)");
			Matcher matcher84 = Rl_loiNo.matcher(output);

			if (matcher84.find())
			{
				String Rl_loiNo1  = matcher84.group(1).trim();
				System.out.println("LOI No is "+Rl_loiNo1);

			}
			else
			{
				System.out.println("no result");
			}

			Pattern Rl_ApplicantName = Pattern.compile("(?<=To,)\\s*(.*)");
			Matcher matcher85 = Rl_ApplicantName.matcher(output);

			if (matcher85.find())
			{
				String Rl_ApplicantName1  = matcher85.group(1).trim();
				System.out.println("Initimation Letter generated Applicant Name "+Rl_ApplicantName1);

			}
			else
			{
				System.out.println("no result");
			}

			Pattern Rl_Applprntdate = Pattern.compile("(?<=LOI No.)\\s*(.*)");
			Matcher matcher86 = Rl_Applprntdate.matcher(output);

			if (matcher86.find())
			{
				String Rl_Applprntdate1  = matcher86.group(1).trim();
				System.out.println("Initimation Letter generated date "+Rl_Applprntdate1);

			}
			else
			{
				System.out.println("no result");
			}

			Pattern Rl_JointOwnerName = Pattern.compile("(?<=Joint Owner:)\\s*(.*)");
			Matcher matcher87 = Rl_JointOwnerName.matcher(output);

			if (matcher87.find())
			{
				String Rl_JointOwnerName1  = matcher87.group(1).trim();
				System.out.println("Joint Owner Name is "+Rl_JointOwnerName1);

			}
			else
			{
				System.out.println("no result");
			}

			Pattern Rl_TotalFee = Pattern.compile("\\s*(.*)\\s*(|\n)*(?=Authorized Signatory)");
			Matcher matcher88 = Rl_TotalFee.matcher(output);

			if (matcher88.find())
			{
				String Rl_TotalFee1  = matcher88.group(1).trim();
				System.out.println("Total Fee is "+Rl_TotalFee1);
				Inti_RenewLicense_TotalFee.add(Rl_TotalFee1);
				System.out.println("Intimation Reneewal of License Total Fee List = "+Inti_RenewLicense_TotalFee);

			}
			else
			{
				System.out.println("no result");
			}


			Pattern Rl_Days = Pattern.compile("(?<=paid within)\\s*(.*)(?=days and license)");
			Matcher matcher90 = Rl_Days.matcher(output);

			if (matcher90.find())
			{
				String Rl_Days1  = matcher90.group(1).trim();
				System.out.println("Intimation letter charge(s) (if applicable) should be paid within "+Rl_Days1);

			}
			else
			{
				System.out.println("no result");
			}



			//For two fees in table
			Pattern Rl_FeeType = Pattern.compile("(?<=license will be issued)\\s*(.*)(?=To,)",Pattern.DOTALL);
			Matcher matcher90a = Rl_FeeType.matcher(output);

			if (matcher90a.find())
			{
				String Rl_FeeType1  = matcher90a.group(1).trim();
				System.out.println(Rl_FeeType1);
				String[] fee1 = Rl_FeeType1.split("\\r?\\n");
				System.out.println("first fee Is "+fee1[0]);
				String n= fee1[0];

				Pattern pattern104 = Pattern.compile("(\\s*(.+?) s*(.+?) s*(.+?) )");
				Matcher matcher114 = pattern104.matcher(n);

				if (matcher114.find())
				{
					String u = matcher114.group(1);
					System.out.println(u); // Use Variable "u" for fee type name if 1 fee available
				}

				Pattern pattern105 = Pattern.compile("([0-9]+)");
				Matcher matcher115 = pattern105.matcher(n);

				if (matcher115.find())
				{
					String v = matcher115.group(1);
					System.out.println(v);   // Use Variable "v" for fee type amount for  1st fee available
				}



				//Second Fee (if applicable) code
				System.out.println("Second Fee is "+fee1[1]);
				String m= fee1[1];
				Pattern pattern106 = Pattern.compile("(\\s*(.+?) s*(.+?) s*(.+?) )");
				Matcher matcher116 = pattern106.matcher(m);

				if (matcher116.find())
				{
					String w = matcher116.group(1);
					System.out.println(w);  // Use Variable "w" for fee type name if 2 fee available
				}

				Pattern pattern107 = Pattern.compile("([0-9]+)");
				Matcher matcher117 = pattern107.matcher(m);

				if (matcher117.find())
				{
					String x = matcher117.group(1);
					System.out.println(x); // Use Variable "x" for fee type amount for 2nd fee available
				}

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




	//Piyush Other mode intimation Letter 30/12/2016 
	public static void pattern_InitLetterOtherModePDF(String output)
	{
		try
		{

			Pattern ILOM_LetterType = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Trade License Department)");;		
			Matcher matcher81 = ILOM_LetterType.matcher(output);

			if (matcher81.find())
			{
				String ILOM_LetterType1  = matcher81.group(1).trim();
				System.out.println("Letter Type is : "+ILOM_LetterType1);			
			} 
			else
			{
				System.out.println("no result");
			}	



			Pattern ILOM_ApplNo = Pattern.compile("(?<=Application No.)\\s*(.*)\\s*(|\n)*(?=and)");		
			Matcher matcher82 = ILOM_ApplNo.matcher(output);

			if (matcher82.find())
			{
				String ILOM_ApplNo1  = matcher82.group(1).trim();
				System.out.println("Application No of intimitaion Letter is : "+ILOM_ApplNo1);
			}
			else
			{
				System.out.println("no result");
			}

			Pattern ILOM_Appdatedate = Pattern.compile("(?<=and dated)\\s*(.*)");		
			Matcher matcher83 = ILOM_Appdatedate.matcher(output);

			if (matcher83.find())
			{
				String ILOM_Appdatedate1  = matcher83.group(1).trim();
				System.out.println("Inspection date is : "+ILOM_Appdatedate1);
			}
			else
			{
				System.out.println("no result");
			}


			Pattern ILOM_loiNo = Pattern.compile("(?<=:)\\s*(.*)(?=LOI No.)");
			Matcher matcher84 = ILOM_loiNo.matcher(output);

			if (matcher84.find())
			{
				String ILOM_loiNo1  = matcher84.group(1).trim();
				System.out.println("LOI No is "+ILOM_loiNo1);

			}
			else
			{
				System.out.println("no result");
			}

			Pattern ILOM_ApplicantName = Pattern.compile("(?<=To,)\\s*(.*)");
			Matcher matcher85 = ILOM_ApplicantName.matcher(output);



			if (matcher85.find())
			{
				String ILOM_ApplicantName1  = matcher85.group(1).trim();
				System.out.println("Initimation Letter generated Applicant Name "+ILOM_ApplicantName1);
				IntiLetter_HolderNameList.add(ILOM_ApplicantName1);
				System.out.println("Transfer Other Mode Intimation time List of Holder:: "+IntiLetter_HolderNameList);

			}
			else
			{
				System.out.println("no result");
			}

			Pattern ILOM_Applprntdate = Pattern.compile("(?<=LOI No.)\\s*(.*)");
			Matcher matcher86 = ILOM_Applprntdate.matcher(output);



			if (matcher86.find())
			{
				String ILOM_Applprntdate1  = matcher86.group(1).trim();
				System.out.println("Initimation Letter generated date "+ILOM_Applprntdate1);

			}
			else
			{
				System.out.println("no result");
			}

			Pattern ILOM_JointOwnerName = Pattern.compile("(?<=Joint Owner:)\\s*(.*)");
			Matcher matcher87 = ILOM_JointOwnerName.matcher(output);

			if (matcher87.find())
			{
				String ILOM_JointOwnerName1  = matcher87.group(1).trim();
				System.out.println("Joint Owner Name is "+ILOM_JointOwnerName1);

			}
			else
			{
				System.out.println("no result");
			}

			Pattern ILOM_TotalFee = Pattern.compile("\\s*(.*)\\s*(|\n)*(?=Authorized Signatory)");
			Matcher matcher88 = ILOM_TotalFee.matcher(output);

			if (matcher88.find())
			{
				String ILOM_TotalFee1  = matcher88.group(1).trim();
				System.out.println("Joint Owner Name is "+ILOM_TotalFee1);

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

	public static void patternduplicateMarketLicensePdf(String output)
	{
		int flag=0;
		System.out.println("patterns===================================================================>");
		Pattern licenseNumber = Pattern.compile("(?<=Business Address)\\s*(.*)");
		Pattern licenseValidity = Pattern.compile("(?<=License Valid From)\\s*(.*)");
		Pattern licHoldrName = Pattern.compile("(?<=Business Address)\\s*(.*)",Pattern.DOTALL);
		Pattern totalFees = Pattern.compile("(?<=Total License Fees)\\s*(.*)");
		System.out.println("totalFees==>"+totalFees);
		Pattern service =Pattern.compile("(?<=Trade License Department)\\s(.*)");
		System.out.println("service==>"+service);
		Matcher matcher1 = licenseNumber.matcher(output);
		Matcher matcher2 = licenseValidity.matcher(output);
		Matcher matcher3 = licHoldrName.matcher(output);
		Matcher matcher4 = totalFees.matcher(output);
		Matcher matcher6=service.matcher(output);
		if (matcher1.find())
		{
			licenseNum = matcher1.group(1);
			pdf_license_duplicate_licenseNum.add(licenseNum);
			System.out.println("License number printed in License copy is :--> " + licenseNum);

			System.out.println("******************************************************************************");
			System.out.println("======================== License number--->"+pdf_license_duplicate_licenseNum);


		}
		if (matcher2.find())
		{
			String licenseValid = matcher2.group(1);
			System.out.println("License validity printed in License copy is :--> " + licenseValid);

		}
		while (matcher3.find())
		{   
			//String licHolderName="Mr. Kiran  Kumar";
			//String busiName="K K Traders";
			String licenseHolderNm = matcher3.group(0);

			String[] licHolderNm = licenseHolderNm.split("\\n");
			System.out.println("License holder's name printed in License copy is : " + licHolderNm[3]);
			System.out.println("Business name printed in License copy is : " + licHolderNm[4]);
			pdf_license_duplicate_holdername.add(licHolderNm[3]);

			pdf_license_duplicate_businessname.add(licHolderNm[4]);
			System.out.println("======================== holder name--->"+pdf_license_duplicate_holdername);


			//}
			System.out.println("======================== businessname name--->"+pdf_license_duplicate_businessname);


		}

		if (matcher4.find())
		{
			String totalLicFees = matcher4.group(1);
			Pattern pattern5 = Pattern.compile("(\\d+.\\d+)");
			Matcher matcher5 = pattern5.matcher(totalLicFees);

			if (matcher5.find())
			{
				String Dup_totalFees = matcher5.group(1);
				System.out.println("Total License fee printed in License copy is :--> "+Dup_totalFees);
				DupTotalFeesList.add(Dup_totalFees);
			}
		}
		if(matcher6.find())
		{
			//String licenseValid = matcher2.group(1);
			System.out.println("service name on duplicate report is--> "+matcher6.group(0));
		}
		else
		{
			System.out.println("Regex failed");
		}
	}


	public void rentRenewalContract(String output)
	{
		try
		{
			String App_No;

			Pattern chalappno = Pattern.compile("(?<=LOI Date :)\\s*(.*)\\s*(|\n)");
			Matcher matcher = chalappno.matcher(output);
			if (matcher.find())
			{
				LOINO  = matcher.group(1);
				System.out.println("LOI Number is :"+LOINO);
			}
			Pattern chalappno1 = Pattern.compile("(?<=Application No. )\\s*(.*)\\s*(|\n)*(?= Dated)");
			Matcher matcher1 = chalappno1.matcher(output);

			if (matcher1.find())
			{
				App_No  = matcher1.group(1);
				System.out.println("Application Number is :"+App_No);
			}

			Pattern fullname = Pattern.compile("(?<=To)\\s*(.*)\\s*");
			Matcher matcher2 = fullname.matcher(output);

			if (matcher2.find())
			{
				App_No  = matcher2.group(1).replaceAll("[^A-Za-z]", " ").trim();
				System.out.println("Applicant name is :"+App_No);
				//				mAssert(App_No,ApplicantName,"Final message does not match"+" Actual msg :"+App_No+"  Expected msg :"+ApplicantName);
			}

			//mAssert("","","");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void patternRnl_BOE_RRCAcceptanceLetter(String output,String servicename,String applicantname,String appno){
		try
		{
			Pattern fullname = Pattern.compile("(?<=\\(Acceptance Letter\\))\\s*(.*)\\s*(?=\\(Rs\\.\\))",Pattern.DOTALL);
			Matcher matcher1 = fullname.matcher(pdfoutput);

			if (matcher1.find())
			{					
				String bill_details  = matcher1.group(1);
				String[] string = bill_details.split("\\r?\\n");
				String service=string[0].replaceAll("\\s", "").trim();
				String name=string[1]; 
				String[] b=name.split("\\.");
				mAssert(service,servicename, "Msg at Acceptance/Rejection letter does not match Actual :"+service+" Expected :"+servicename);
				mAssert(b[1],applicantname, "Msg at Acceptance/Rejection letter does not match Actual :"+b[1]+" Expected :"+applicantname);
				System.out.println("service name is : "+service); 
				System.out.println("applicant name is : "+ b[1]); 			 
			}
			else
			{	
				System.out.println("no result");    
			}

			Pattern loi = Pattern.compile("(?<=LOI Date :)\\s*(.*)\\s*(|\n)*(?=Ref.)",Pattern.DOTALL);		
			Matcher matcher2 = loi.matcher(pdfoutput);

			if (matcher2.find())
			{
				String no  = matcher2.group(1).trim();
				String[] string = no.split("\\r?\\n");
				System.out.println("loi no is :"+string[0]);			
			} 
			else
			{
				System.out.println("no result");
			}


			Pattern app_no= Pattern.compile("(?<=Application No.)\\s*(.*)\\s*(|\n)*(?=Dated)");		
			Matcher matcher3 = app_no.matcher(pdfoutput);

			if (matcher3.find())
			{
				String no  = matcher3.group(1).trim();
				System.out.println("appno is :"+no);	
				mAssert(no,appno, "Msg at Acceptance/Rejection letter does not match Actual :"+no+" Expected :"+appno);
			}
			else
			{
				System.out.println("no result");
			}


			Pattern municipality = Pattern.compile("\\s*(.*)\\s*(|\n)*(?=Rent and Lease)");		
			Matcher matcher4 = municipality.matcher(pdfoutput);

			if (matcher4.find())
			{
				String no  = matcher4.group(1).trim();
				System.out.println("ulb is :"+no);			
			}
			else
			{
				System.out.println("no result");
			}

		}
		catch(Exception e){

			e.printStackTrace();	
		}	
	}


	public void patternRnl_BOE_RRCApprovalLetter(String output,String servicename,String applicantname,String appno){
		try
		{
			Pattern fullname2 = Pattern.compile("(?<=Reference :-)\\s*(.*)\\s*(|\n)*(?=Approval Letter)",Pattern.DOTALL);
			Matcher matcher21 = fullname2.matcher(pdfoutput);

			if (matcher21.find())
			{					
				String bill_details  = matcher21.group(1);
				String[] string = bill_details.split("\\r?\\n");
				String service=string[0].trim();
				String name=string[1]; 
				String[] b=name.split("\\.");
				System.out.println("applicant name is :"+b[1]); 
				mAssert(b[1],applicantname, "Msg at approval letter does not match Actual :"+b[1]+" Expected :"+applicantname);
			}
			else
			{	
				System.out.println("no result");    
			}

			Pattern sub = Pattern.compile("(?<=Approval for)\\s*(.*)\\s*(|\n)*(?=\\.)");		
			Matcher matcher22 = sub.matcher(pdfoutput);

			if (matcher22.find())
			{
				String no  = matcher22.group(1).trim();
				System.out.println("subject is :"+no);	
				mAssert(no,servicename, "Msg at approval letter does not match Actual :"+no+" Expected :"+servicename);
			} 
			else
			{
				System.out.println("no result");
			}


			Pattern app_no2 = Pattern.compile("(?<=Application No.)\\s*(.*)\\s*(|\n)*(?=for)");		
			Matcher matcher23 = app_no2.matcher(pdfoutput);

			if (matcher23.find())
			{
				String no  = matcher23.group(1).trim();
				System.out.println("appno is :"+no);
				mAssert(no,appno, "Msg at approval letter does not match Actual :"+no+" Expected :"+appno);
			}
			else
			{
				System.out.println("no result");
			}


			Pattern ulb2 = Pattern.compile("\\s*(.*)\\s*(|\n)*(?=Rent and Lease)");		
			Matcher matcher24 = ulb2.matcher(pdfoutput);

			if (matcher24.find())
			{
				String no  = matcher24.group(1).trim();
				System.out.println("ulb is :"+no);			
			}
			else
			{
				System.out.println("no result");
			}

			Pattern estate = Pattern.compile("(?<=are as below :-)\\s*(.*)\\s*(|\n)*(?=Estate )");		
			Matcher matcher25 = estate.matcher(pdfoutput);

			if (matcher25.find())
			{
				String bill_details  = matcher25.group(1);
				String[] string = bill_details.split("\\r?\\s");
				System.out.println("estate no,name,add is :"+string[0]); 				
			}
			else
			{

				System.out.println("no result");
			}			
		}
		catch(Exception e){

			e.printStackTrace();	
		}	
	}



	public void patternRnl_BOE_RRCRejectionLetter(String output,String servicename,String applicantname,String appno){
		try
		{
			Pattern subject = Pattern.compile("(?<=Rejection for)\\s*(.*)\\s*(|\n)*(?=Service\\.)");		
			Matcher matcher1 = subject.matcher(pdfoutput);

			if (matcher1.find())
			{
				String no  = matcher1.group(1).replaceAll("\\s", "").trim();
				System.out.println("subject is :"+no);	
				mAssert(no,servicename, "Msg at rejection letter Actual :"+no+" Expected :"+servicename);
			}
			else
			{
				System.out.println("no result");
			}


			Pattern fullname = Pattern.compile("(?<=To,)\\s*(.*)\\s*");
			Matcher matcher2 = fullname.matcher(pdfoutput);

			if (matcher2.find())
			{
				String no  = matcher2.group(1).replaceAll("[^A-Za-z]", " ").trim();
				System.out.println("Applicant name is :"+no);	
				mAssert(no,applicantname, "Msg at rejection letter Actual :"+no+" Expected :"+applicantname);
			}
			else
			{
				System.out.println("no result");
			}


			Pattern app_no = Pattern.compile("(?<=Application No.)\\s*(.*)\\s*(|\n)*(?=Dated)");		
			Matcher matcher3 = app_no.matcher(pdfoutput);

			if (matcher3.find())
			{
				String no  = matcher3.group(1).trim();
				System.out.println("appno is :"+no);
				mAssert(no,appno, "Msg at rejection letter Actual :"+no+" Expected :"+appno);
			}
			else
			{
				System.out.println("no result");
			}


			Pattern municipality = Pattern.compile("\\s*(.*)\\s*(|\n)*(?=Rent and Lease)");		
			Matcher matcher4 = municipality.matcher(pdfoutput);

			if (matcher4.find())
			{
				String no  = matcher4.group(1).trim();
				System.out.println("ulb is :"+no);		
			}
			else
			{
				System.out.println("no result");
			}			

		}
		catch(Exception e){

			e.printStackTrace();	
		}	
	}



	public void rnl_BillPrintingPDF(String output,String data1,String data2,String data3,String data4,String data5,String data6,String data7,String data8)
	{
		try
		{						
			Pattern fullname1 = Pattern.compile("(?<=Excess Adjusted Amount :)\\s*(.*)\\s*(|\n)*(?=From Date To Date)",Pattern.DOTALL);
			Matcher matcher1 = fullname1.matcher(pdfoutput);

			if (matcher1.find())
			{
				String bill_details  = matcher1.group(1);
				String[] string = bill_details.split("\\r?\\n");	
				String a=string[0];
				String[] b=a.split("\\.");
				String tenantname=b[1]; 

				String contractno=string[1]; String fromdate=string[2];  			 
				String tenantno=string[3];  String todate=string[4];
				System.out.println("Tenant name is :"+tenantname+"\n"+"Contract no is :"+contractno+"\n"+"From date is :"+fromdate+"\n"+"Tenant no is :"+tenantno+"\n"+"To date is :"+todate);
				mAssert(tenantname,data1, "Msg at Bill Printing. Actual :"+tenantname+" Expected :"+data1);
				mAssert(contractno,data2, "Msg at Bill Printing. Actual :"+contractno+" Expected :"+data2);
				mAssert(fromdate,data3, "Msg at Bill Printing. Actual :"+fromdate+" Expected :"+data3);
				mAssert(tenantno,data4, "Msg at Bill Printing. Actual :"+tenantno+"Expected :"+data4);
				mAssert(todate,data5, "Msg at Bill Printing. Actual :"+todate+"Expected :"+data5);
			}		
			else
			{
				System.out.println("no result");
			}

			//			Pattern fullname2 = Pattern.compile("(?<=Contract Fee)\\s*(.*)\\s*(|\n)*(?= )");
			Pattern fee = Pattern.compile("(?<=Contract Fee)\\s*(.*)\\s*(|\n)*(?=Tax Description Current DemandArrears Amount)",Pattern.DOTALL);
			Matcher matcher2 = fee.matcher(pdfoutput);

			if (matcher2.find())
			{
				String bill_details  = matcher2.group(1);			
				//				System.out.println("contract fee is :"+bill_details);	
				String[] string = bill_details.split("\\r?\\n");	
				String contractfee=string[1]; 
				System.out.println("Contract fee is :"+contractfee);
				mAssert(bill_details,data6, "Msg at Bill Printing. Actual :"+bill_details+"Expected :"+data6);
			}		
			else
			{
				System.out.println("no result");
			}


			Pattern disc_amnt = Pattern.compile("(?<=Contract No.)\\s*(.*)\\s*(|\n)*(?=Discount Amount :)");
			Matcher matcher3 = disc_amnt.matcher(pdfoutput);

			if (matcher3.find())
			{
				String bill_details  = matcher3.group(1);			
				System.out.println("disc amount is :"+bill_details);
				mAssert(bill_details,data7, "Msg at Bill Printing. Actual :"+bill_details+"Expected :"+data7);
			}		
			else
			{
				System.out.println("no result");
			}

			Pattern totalPay_amnt = Pattern.compile("(?<= Rent & Lease Bill)\\s*(.*)\\s*(|\n)*(?=Balance Excess Amount :)",Pattern.DOTALL);
			Matcher matcher4 = totalPay_amnt.matcher(pdfoutput);

			if (matcher4.find())
			{
				String bill_details  = matcher4.group(1);
				String[] string = bill_details.split("\\r?\\n");	
				String totalamount=string[1];
				System.out.println("total pay amount is :"+totalamount);
				mAssert(totalamount,data8, "Msg at Bill Printing. Actual :"+totalamount+"Expected :"+data8);
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



	public void rentPaymentSchedulePDF(String output)
	{
		try
		{
			Pattern applicationNum = Pattern.compile("(?<=Application No./LOI No. : )\\s*([0-9]+)\\s*(|\n)(?=Contact No. :)");
			Matcher matcher = applicationNum.matcher(output);

			if (matcher.find()) {

				appNo = matcher.group(1);
				System.out.println("Captured String for Application no is "+appNo);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	// Created method for Receipt PDF verification by Madhuri Dawande 25-05-2016
	public static void patternULBChallanReceiptPdf(String output)
	{
		int flag=0;

		Pattern amount = Pattern.compile("(?<=Mode Rupees Pay Order No. Pay Order Date   Bank Name)\\s*([0-9]+.[0-9]+)");

		Matcher matcher1 = amount.matcher(output);


		if (matcher1.find())
		{
			Rcptamt = matcher1.group(1);
			System.out.println("Amount printed in Receipt is : " + Rcptamt);


		}

		else
		{
			System.out.println("Regex failed");
		}

	}

	public void rnl_BillPrintingPDF(String output,String data1,String data2,String data3,String data4,String data5,String data6)
	{
		try
		{						
			Pattern ulb = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Rent and Lease)");
			Matcher matcher11 = ulb.matcher(pdfoutput);

			if (matcher11.find())
			{
				name  = matcher11.group(1).trim();
				System.out.println("ulb name is : "+name);	
				mAssert(name,ULBName, "Msg at Bill Printing. Actual :"+name+"Expected :"+ULBName);

				String[] fullname = pdfoutput.split("Rent and Lease\r\n" + "Bihar Municipal Act 2007");			
				int lengthofarray = fullname.length;

				for(int i=0;i<lengthofarray;i++)
				{
					if(i>0)
					{
						System.out.println();	 
						System.out.println("the result is : "+fullname[i].toString());

						Pattern fullname1 = Pattern.compile("(?<=Excess Adjusted Amount :)\\s*(.*)\\s*(|\n)*(?=From Date To Date)",Pattern.DOTALL);
						Matcher matcher2 = fullname1.matcher(fullname[i].toString());

						if (matcher2.find())
						{
							String bill_details  = matcher2.group(1);
							String[] string = bill_details.split("\\r?\\n");	
							String a=string[0];
							String[] b=a.split("\\.");
							String tenantname=b[1];
							String contractno=string[1]; String fromdate=string[2];  			 
							String tenantno=string[3];  String todate=string[4];
							System.out.println("Tenant name is :"+tenantname+"\n"+"Contract no is :"+contractno+"\n"+"From date is :"+fromdate+"\n"+"Tenant no is :"+tenantno+"\n"+"To date is :"+todate);
							mAssert(tenantname,data1, "Msg at Bill Printing. Actual :"+tenantname+" Expected :"+data1);
							mAssert(contractno,data2, "Msg at Bill Printing. Actual :"+contractno+" Expected :"+data2);
							mAssert(fromdate,data3, "Msg at Bill Printing. Actual :"+fromdate+" Expected :"+data3);
							mAssert(tenantno,data4, "Msg at Bill Printing. Actual :"+tenantno+"Expected :"+data4);
							mAssert(todate,data5, "Msg at Bill Printing. Actual :"+todate+"Expected :"+data5);
						}		
						else
						{
							System.out.println("no result");
						}			

						Pattern fullname2 = Pattern.compile("(?<=Fee)\\s*(.*)\\s*(|\n)*(?=Tax Description Current DemandArrears Amount)",Pattern.DOTALL);
						Matcher matcher3 = fullname2.matcher(fullname[i].toString());

						if (matcher3.find())
						{
							String bill_details  = matcher3.group(1);			
							String[] string = bill_details.split("\\s");	
							String contractfee=string[0];  
							if(!string[0].equalsIgnoreCase("Roundoff"))
							{
								System.out.println("Contract fee is :"+contractfee);
							}
							if(string[0].equalsIgnoreCase("Roundoff"))
							{ 
								Pattern fullname20 = Pattern.compile("(?<=Roundoff Tax)\\s*(.*)\\s*(|\n)*(?=Tax Description Current DemandArrears Amount)",Pattern.DOTALL);
								Matcher matcher30 = fullname20.matcher(fullname[i].toString());
								if (matcher30.find())
								{
									String bill_details1  = matcher3.group(1);			
									String[] string1 = bill_details1.split("\\n");	
									String contractfee1=string1[1];  
									System.out.println("Contract fee is :"+contractfee1.trim());	
									System.out.println("Roundoff Tax is :"+string1[2].trim());
								}
							}

						}

						else
						{
							System.out.println("no result");
						}

						Pattern disc_amnt = Pattern.compile("(?<=Contract No.)\\s*(.*)\\s*(|\n)*(?=Discount Amount :)");
						Matcher matcher4 = disc_amnt.matcher(fullname[i].toString());

						if (matcher4.find())
						{
							String bill_details  = matcher4.group(1);			
							System.out.println("disc amount is :"+bill_details);					
						}		
						else
						{
							System.out.println("no result");
						}

						Pattern totalPay_amnt = Pattern.compile("(?<= Rent & Lease Bill)\\s*(.*)\\s*(|\n)*(?=Balance Excess Amount :)",Pattern.DOTALL);
						Matcher matcher5 = totalPay_amnt.matcher(fullname[i].toString());

						if (matcher5.find())
						{
							String bill_details  = matcher5.group(1);
							String[] string = bill_details.split("\\r?\\n");	
							String totalamount=string[1];
							System.out.println("total pay amount is :"+totalamount);
						}		
						else
						{
							System.out.println("no result");
						}	

						Pattern billNo = Pattern.compile("(?<= Bill No.   :)\\s*(.*)\\s*(|\n)*(?=Bill Date :)",Pattern.DOTALL);
						Matcher matcher6 = billNo.matcher(fullname[i].toString());

						if (matcher6.find())
						{
							String bill_dets  = matcher6.group(1);						
							System.out.println("Bill no is :"+bill_dets);
						}		
						else
						{
							System.out.println("no result");
						}

					}			
				}
			}

			else
			{
				System.out.println("ULB does not match");
			}

		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	//	public static void patternduplicateMarketLicensePdf(String output)
	//	{
	//		int flag=0;
	//		System.out.println("patterns===================================================================>");
	//		Pattern licenseNumber = Pattern.compile("(?<=Business Address)\\s*(.*)");
	//		Pattern licenseValidity = Pattern.compile("(?<=License Valid From)\\s*(.*)");
	//		Pattern licHoldrName = Pattern.compile("(?<=Business Address)\\s*(.*)",Pattern.DOTALL);
	//		Pattern totalFees = Pattern.compile("(?<=Total License Fees)\\s*(.*)");
	//		System.out.println("totalFees==>"+totalFees);
	//		Pattern service =Pattern.compile("(?<=Trade License Department)\\s(.*)");
	//		System.out.println("service==>"+service);
	//		Matcher matcher1 = licenseNumber.matcher(output);
	//		Matcher matcher2 = licenseValidity.matcher(output);
	//		Matcher matcher3 = licHoldrName.matcher(output);
	//		Matcher matcher4 = totalFees.matcher(output);
	//Matcher matcher6=service.matcher(output);
	//		if (matcher1.find())
	//		{
	//			licenseNum = matcher1.group(1);
	//			pdf_license_duplicate_licenseNum.add(licenseNum);
	//			System.out.println("License number printed in License copy is :--> " + licenseNum);
	//			
	//			System.out.println("******************************************************************************");
	//			System.out.println("======================== License number--->"+pdf_license_duplicate_licenseNum);
	//			
	//
	//		}
	//		if (matcher2.find())
	//		{
	//			String licenseValid = matcher2.group(1);
	//			System.out.println("License validity printed in License copy is :--> " + licenseValid);
	//
	//		}
	//		while (matcher3.find())
	//		{   
	//			//String licHolderName="Mr. Kiran  Kumar";
	//			//String busiName="K K Traders";
	//			String licenseHolderNm = matcher3.group(0);
	//
	//			String[] licHolderNm = licenseHolderNm.split("\\n");
	//			System.out.println("License holder's name printed in License copy is : " + licHolderNm[3]);
	//			System.out.println("Business name printed in License copy is : " + licHolderNm[4]);
	//			pdf_license_duplicate_holdername.add(licHolderNm[3]);
	//			pdf_license_duplicate_businessname.add(licHolderNm[4]);
	//			System.out.println("======================== holder name--->"+pdf_license_duplicate_holdername);
	//			
	//			
	//			//}
	//			System.out.println("======================== businessname name--->"+pdf_license_duplicate_businessname);
	//			
	//			
	//		}
	//
	//		if (matcher4.find())
	//		{
	//			String totalLicFees = matcher4.group(1);
	//			Pattern pattern5 = Pattern.compile("(\\d+.\\d+)");
	//			Matcher matcher5 = pattern5.matcher(totalLicFees);
	//
	//			if (matcher5.find())
	//			{
	//				System.out.println("Total License fee printed in License copy is :--> "+matcher5.group(1));
	//			}
	//		}
	//		if(matcher6.find())
	//		{
	//			//String licenseValid = matcher2.group(1);
	//			System.out.println("service name on duplicate report is--> "+matcher6.group(0));
	//		} 
	//		else
	//		{
	//			System.out.println("Regex failed");
	//		}
	//	}
	public static void patterntlunMarketLicensePdf(String output)
	{
		int flag=0;
		System.out.println("patterns===================================================================>");
		Pattern licenseNumber = Pattern.compile("(?<=Business Address)\\s*(.*)");
		Pattern licenseValidity = Pattern.compile("(?<=License Valid From)\\s*(.*)");
		//Pattern licHoldrName = Pattern.compile("(?<=Business Address)\\s*(.*)",Pattern.DOTALL);
		Pattern licHoldrName = Pattern.compile("(?<=Business Address)\\s*(.*)\\s*(|\n)*(?=Address Line 1 : )",Pattern.DOTALL);
		Pattern address=Pattern.compile("(?<=Address Line 1 :)\\s*(.*)");
		Pattern totalFees = Pattern.compile("(?<=Total Fees )\\s*(.*)");
		System.out.println("totalFees==>"+totalFees);
		Pattern service =Pattern.compile("(?<=Trade License Department)\\s(.*)");
		Pattern jowner=Pattern.compile("(?<=Joint Owner :)\\s(.*)");
		System.out.println("service==>"+service);
		Matcher matcher1 = licenseNumber.matcher(pdfoutput);
		Matcher matcher2 = licenseValidity.matcher(pdfoutput);
		Matcher matcher3 = licHoldrName.matcher(pdfoutput);
		Matcher matcher4 = totalFees.matcher(pdfoutput);
		Matcher matcher7=jowner.matcher(pdfoutput);
		Matcher matcher8=address.matcher(pdfoutput);
		if (matcher1.find())
		{
			String licenseNum = matcher1.group(1);

			System.out.println("License number printed in License copy is :--> " + licenseNum);

			licensenum_newtrade=licenseNum;

			System.out.println("******************************************************************************");


		}
		if (matcher2.find())
		{
			String licenseValid = matcher2.group(0);
			System.out.println("License validity printed in License copy is :--> " + licenseValid);
			String[] fromto=licenseValid.split("To");
			System.out.println(fromto[0]);
			System.out.println(fromto[1]);
			String fromdate=fromto[0];
			String todate=fromto[1];
			pdf_fromdate.add(fromdate);
			pdf_todate.add(fromdate);
			System.out.println("fromdate"+pdf_fromdate);
			System.out.println("todate"+pdf_todate);


		}



		while (matcher3.find())
		{   
			//String licHolderName="Mr. Kiran  Kumar";
			//String busiName="K K Traders";
			String licenseHolderNm = matcher3.group(0);

			String[] licHolderNm = licenseHolderNm.split("\\n");
			System.out.println("aray==>>>"+licHolderNm.length);

			System.out.println("License holder's name printed in License copy is : " + licHolderNm[3]);
			System.out.println("Business name printed in License copy is : " + licHolderNm[4]);
			pdf_tlun_holdername.add(licHolderNm[3]);
			System.out.println("===holder name===="+pdf_tlun_holdername);
			pdf_tlun_businessname.add(licHolderNm[4]);




		}

		if (matcher4.find())
		{
			String totalLicFees = matcher4.group(1);
			Pattern pattern5 = Pattern.compile("(\\d+.\\d+)");
			Matcher matcher5 = pattern5.matcher(totalLicFees);

			if (matcher5.find())
			{
				System.out.println("Total License fee printed in License copy is :--> "+matcher5.group(1));
				String tfees=matcher5.group(1);
				System.out.println("Total fees ==>"+tfees);
				pdf_tlun_tfees.add(tfees);


			}
		}

		if(matcher7.find())
		{
			String jowner_pdf=matcher7.group(0);
			System.out.println("joint owner name on report is--> "+matcher7.group(0));
			pdf_tlun_jowner_pdf.add(jowner_pdf);
		}

		if(matcher8.find())
		{
			String pdf_address=matcher8.group(0);
			System.out.println("=================="+pdf_address);
			pdf_tlun_address.add(pdf_address);


		}

		else
		{
			System.out.println("Regex failed");
		}

	}
	public static void NtlShowCauseNoticePDF(String output)
	{
		try
		{

			Pattern ntl_add = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=C.S.N Number :)");
			Matcher matcher35 = ntl_add.matcher(pdfoutput);


			if (matcher35.find())
			{
				String ntl_add1  = matcher35.group(1).trim();
				System.out.println("Address is : "+ntl_add1);		

				//mAssert(ulbname,ULBName, "Msg at Occupancy Certificate. Actual :"+ulbname+"Expected :"+ULBName);
			}
			else
			{
				System.out.println("no result");
			}

			Pattern ntl_Lettertype = Pattern.compile("(?<=Bihar Municipal Act 2007)\\s*(.*)\\s*(|\n)*(?=Sir/Madam,)");		
			Matcher matcher30 = ntl_Lettertype.matcher(pdfoutput);

			if (matcher30.find())
			{
				String ntl_Lettertype1  = matcher30.group(1).trim();
				System.out.println("Letter Type is : "+ntl_Lettertype1);		
				showcause_lettertype_pdf.add(ntl_Lettertype1);
			} 
			else
			{
				System.out.println("no result");
			}		

			Pattern ntl_Name = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Khagaul Nagar Parishad)");		
			Matcher matcher31 = ntl_Name.matcher(pdfoutput);

			if (matcher31.find())
			{
				String ntl_Name1  = matcher31.group(1).trim();
				System.out.println("Name is : "+ntl_Name1);		
				showcause_holdername_pdf.add(ntl_Name1);
			} 
			else
			{
				System.out.println("no result");
			}	

			Pattern ntl_NoticeNo = Pattern.compile("\\s*(.*)\\s*(|\\n)*");
			Matcher matcher2 = ntl_NoticeNo.matcher(pdfoutput);

			if (matcher2.find())
			{
				String ntl_NoticeNo1 = matcher2.group(1).trim();
				System.out.println("Notice No is : "+ntl_NoticeNo1);	
				showcause_notice_no_pdf.add(ntl_NoticeNo1);
				//mAssert(ulbname,ULBName, "Msg at Occupancy Certificate. Actual :"+ulbname+"Expected :"+ULBName);
			}
			else
			{
				System.out.println("no result");
			}

			Pattern ntl_sub = Pattern.compile("(?<=Subject     :)\\s*(.*)\\s*(|\n)*(?=Reference)");		
			Matcher matcher25 = ntl_sub.matcher(pdfoutput);

			if (matcher25.find())
			{
				String ntl_no  = matcher25.group(1).trim();
				System.out.println("Letter Subject is : "+ntl_no);
				showcause_subject_pdf.add(ntl_no);
			} 
			else
			{
				System.out.println("no result");
			}		

			Pattern ntl_ulb = Pattern.compile("\\s*(.*)\\s*(|\n)*(?=To,)");		
			Matcher matcher24 = ntl_ulb.matcher(pdfoutput);

			if (matcher24.find())
			{
				String ntl_ulb1  = matcher24.group(1).trim();
				System.out.println("ULB Name is : "+ntl_ulb1);

				showcause_ulbname_pdf.add(ntl_ulb1);
			}
			else
			{
				System.out.println("no result");
			}

			Pattern ntl_NoticeDate = Pattern.compile("(?<=30)\\s*(.*)\\s*(|\n)*(?=Property No.)");		
			Matcher matcher23 = ntl_NoticeDate.matcher(pdfoutput);

			if (matcher23.find())
			{
				String ntl_NoticeDate1  = matcher23.group(1).trim();
				System.out.println("Notice Date is : "+ntl_NoticeDate1);
				showcause_noticedate_pdf.add(ntl_NoticeDate1);
			}
			else
			{
				System.out.println("no result");
			}

			Pattern ntl_NoticeReason = Pattern.compile("(?<=Authorised Signature)\\s*(.*)\\s*(|\n)*(?=Sr.no)");		
			Matcher matcher28 = ntl_NoticeReason.matcher(pdfoutput);

			if (matcher28.find())
			{
				String ntl_NoticeReason1  = matcher28.group(1).trim();
				System.out.println("Notice Reason is : "+ntl_NoticeReason1);
				showcause_noticereason_pdf.add(ntl_NoticeReason1);
			}
			else
			{
				System.out.println("no result");
			}

			Pattern ntl_ApplicationNo = Pattern.compile("(?<=Application No.)\\s*(.*)\\s*(|\n)*(?=   and)");		
			Matcher matcher36 = ntl_ApplicationNo.matcher(pdfoutput);

			if (matcher36.find())
			{
				String ntl_ApplicationNo1  = matcher36.group(1).trim();
				System.out.println("Application No : "+ntl_ApplicationNo1);
				showcause_noticapplnno_pdf.add(ntl_ApplicationNo1);
			}
			else
			{
				System.out.println("no result");
			}

			Pattern ntl_ApplicationDate = Pattern.compile("(?<=dated)\\s*(.*)\\s*(|\n)*(?=Khagaul)");		
			Matcher matcher37 = ntl_ApplicationDate.matcher(pdfoutput);

			if (matcher37.find())
			{
				String ntl_ApplicationDate1  = matcher37.group(1).trim();
				System.out.println("Application Date : "+ntl_ApplicationDate1);
				showcause_applnnodate_pdf.add(ntl_ApplicationDate1);

			}
			else
			{
				System.out.println("no result");
			}
			Pattern ntl_BuisnessName = Pattern.compile("(?<=carried out of M/S )\\s*(.*)\\s*(|\n)*(?=on)");
			//Pattern ntl_BuisnessName = Pattern.compile("(?<=carried out of)\\s*(.*)\\s*(|\n)*(?=on)");
			Matcher matcher39 = ntl_BuisnessName.matcher(pdfoutput);

			if (matcher39.find())
			{
				String ntl_BuisnessName1  = matcher39.group(1).trim();
				System.out.println("Buisness Name  is : "+ntl_BuisnessName1);		
				showcause_businessname_pdf.add(ntl_BuisnessName1);
				//mAssert(ulbname,ULBName, "Msg at Occupancy Certificate. Actual :"+ulbname+"Expected :"+ULBName);
			}
			else
			{
				System.out.println("no result");
			}

			Pattern ntl_InspectorName = Pattern.compile("(?<=by Tax Collector-)\\s*(.*)\\s*(|\n)*(?=was found some)");
			Matcher matcher40 = ntl_InspectorName.matcher(pdfoutput);

			if (matcher40.find())
			{
				String ntl_InspectorName1  = matcher40.group(1).trim();
				System.out.println("Inspector Name  is : "+ntl_InspectorName1);		
				//mAssert(ulbname,ULBName, "Msg at Occupancy Certificate. Actual :"+ulbname+"Expected :"+ULBName);
				showcause_InspectorName_pdf.add(ntl_InspectorName1);
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

	//inspection Details
	public static void NtlInspectionDetailsPDF(String output)
	{
		try
		{

			Pattern Inspdet_LicenceNo = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Khagaul Nagar Parishad)");		
			Matcher matcher41 = Inspdet_LicenceNo.matcher(pdfoutput);

			if (matcher41.find())
			{
				String Inspdet_LicenceNo1  = matcher41.group(1).trim();
				System.out.println("Licence No is : "+Inspdet_LicenceNo1);	
				report_inspection_licenseno_list.add(Inspdet_LicenceNo1);

			} 
			else
			{
				System.out.println("no result");
			}	



			Pattern Inspdet_Reason = Pattern.compile("(?<=Sir/Madam,)\\s*(.*)\\s*(|\n)*(?=Authorised)");		
			Matcher matcher42 = Inspdet_Reason.matcher(pdfoutput);

			if (matcher42.find())
			{
				String Inspdet_Reason1  = matcher42.group(1).trim();
				System.out.println("Reason is : "+Inspdet_Reason1);
				report_inspection_Remarks_list.add(Inspdet_Reason1);




			}
			else
			{
				System.out.println("no result");
			}

			Pattern Inspdet_Inspectiondate = Pattern.compile("(?<=inspected on)\\s*(.*)\\s*(|\n)*(?=, and)");		
			Matcher matcher43 = Inspdet_Inspectiondate.matcher(pdfoutput);

			if (matcher43.find())
			{
				String Inspdet_Inspectiondate1  = matcher43.group(1).trim();
				System.out.println("Inspection date is : "+Inspdet_Inspectiondate1);
				report_inspection_date_list.add(Inspdet_Inspectiondate1);
			}
			else
			{
				System.out.println("no result");
			}

			//Pattern Inspdet_ApplName = Pattern.compile("\\s*(.*)\\s*(|\\n)*");
			//Pattern Inspdet_ApplName = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Sir/Madam,)");
			Pattern Inspdet_ApplName = Pattern.compile("(?<=To,)\\s*(.*)\\s*(|\n)*(?=Sir/Madam,)",Pattern.DOTALL);
			//Pattern Inspdet_ApplName = Pattern.compile("(?<=To,)\\s*(.*)\\s*(|\n)*(?=Sir/Madam,)");		
			Matcher matcher44 = Inspdet_ApplName.matcher(pdfoutput);

			if (matcher44.find())
			{
				String Inspdet_ApplName1  = matcher44.group(1).trim();
				System.out.println("Application Name and Address : "+Inspdet_ApplName1);
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
	public static void patternrenewalMarketLicensePdf(String output)
	{
		int flag=0;
		System.out.println("patterns===================================================================>");
		Pattern licenseNumber = Pattern.compile("(?<=Business Address)\\s*(.*)");
		Pattern licenseValidity = Pattern.compile("(?<=License Valid From)\\s*(.*)");
		//Pattern licHoldrName = Pattern.compile("(?<=Business Address)\\s*(.*)",Pattern.DOTALL);
		Pattern licHoldrName = Pattern.compile("(?<=Business Address)\\s*(.*)\\s*(|\n)*(?=Address Line 1 : )",Pattern.DOTALL);
		Pattern address=Pattern.compile("(?<=Address Line 1 :)\\s*(.*)");
		Pattern totalFees = Pattern.compile("(?<=Total Fees )\\s*(.*)");
		System.out.println("totalFees==>"+totalFees);
		Pattern service =Pattern.compile("(?<=Trade License Department)\\s(.*)");
		Pattern jowner=Pattern.compile("(?<=Joint Owner :)\\s(.*)");
		System.out.println("service==>"+service);
		Matcher matcher1 = licenseNumber.matcher(pdfoutput);
		Matcher matcher2 = licenseValidity.matcher(pdfoutput);
		Matcher matcher3 = licHoldrName.matcher(pdfoutput);
		Matcher matcher4 = totalFees.matcher(pdfoutput);
		Matcher matcher7=jowner.matcher(pdfoutput);
		Matcher matcher8=address.matcher(pdfoutput);
		if (matcher1.find())
		{
			String licenseNum = matcher1.group(1);

			System.out.println("License number printed in License copy is :--> " + licenseNum);

			System.out.println("******************************************************************************");


		}
		if (matcher2.find())
		{
			String licenseValid = matcher2.group(0);
			System.out.println("License validity printed in License copy is :--> " + licenseValid);
			String[] fromto=licenseValid.split("To");
			System.out.println(fromto[0]);
			System.out.println(fromto[1]);
			String fromdate=fromto[0];
			String todate=fromto[1];
			pdf_renewalfromdate.add(fromdate);
			pdf_renewal_todate.add(fromdate);
			System.out.println("fromdate"+pdf_renewalfromdate);
			System.out.println("todate"+pdf_renewal_todate);


		}



		while (matcher3.find())
		{   
			//String licHolderName="Mr. Kiran  Kumar";
			//String busiName="K K Traders";
			String licenseHolderNm = matcher3.group(0);

			String[] licHolderNm = licenseHolderNm.split("\\n");
			System.out.println("aray==>>>"+licHolderNm.length);

			System.out.println("License holder's name printed in License copy is : " + licHolderNm[3]);
			System.out.println("Business name printed in License copy is : " + licHolderNm[4]);

			pdf_renewal_holdername.add(licHolderNm[3]);
			System.out.println("===holder name===="+pdf_renewal_holdername);

			pdf_renwal_businessname.add(licHolderNm[4]);




		}

		if (matcher4.find())
		{
			String totalLicFees = matcher4.group(1);
			Pattern pattern5 = Pattern.compile("(\\d+.\\d+)");
			Matcher matcher5 = pattern5.matcher(totalLicFees);

			if (matcher5.find())
			{
				System.out.println("Total License fee printed in License copy is :--> "+matcher5.group(1));
				String tfees=matcher5.group(1);
				System.out.println("Total fees ==>"+tfees);
				pdf_renewal_tfees.add(tfees);


			}
		}


		if(matcher8.find())
		{
			String pdf_address=matcher8.group(0);
			System.out.println("=================="+pdf_address);
			pdf_renewal_address.add(pdf_address);


		}

		else
		{
			System.out.println("Regex failed");
		}

	}



	public static void tlunmpdploi(String output)
	{

		Pattern Intimation_HolderName = Pattern.compile("(?<=To,)\\s*(.*)");
		Matcher matcher9 = Intimation_HolderName.matcher(pdfoutput);
		Pattern Intimation_AppLetterDate = Pattern.compile("(?<=LOI No. )\\s*(.*)");
		Matcher matcher10 = Intimation_AppLetterDate.matcher(pdfoutput);
		Pattern Intimation_TotalAmount = Pattern.compile("(?<=Date)\\s*(.*)");
		Matcher matcher11 = Intimation_TotalAmount.matcher(pdfoutput);
		Pattern Intimation_ApplicationNo = Pattern.compile("(?<=Application No.)\\s*(.*)(|\n)*(?= and)"); // To capture Application No.Left boundary word 'Application No.' & Right boundary word 'and'
		Matcher matcher12 = Intimation_ApplicationNo.matcher(pdfoutput);
		Pattern Intimation_ApplicationDate = Pattern.compile("(?<=dated  )\\s*(.*)");
		Matcher matcher13 = Intimation_ApplicationDate.matcher(pdfoutput);
		Pattern Intimation_LicenseFee = Pattern.compile("(?<=Market License Fee )\\s*(.*)(|\n)*(?=1)");
		Matcher matcher14 = Intimation_LicenseFee.matcher(pdfoutput);
		Pattern Intimation_Discount = Pattern.compile("(?<=)\\s*(.*)(?=Discount:)"); // To capture 'Discount' using only Right boundary
		Matcher matcher15 = Intimation_Discount.matcher(pdfoutput);
		Pattern Intimation_LOIno = Pattern.compile("(?<=)\\s*(.*)(?=LOI No.)"); // To capture 'Discount' using only Right boundary
		Matcher matcher16 = Intimation_LOIno.matcher(pdfoutput);
		Pattern Intimation_LetterDate = Pattern.compile("(?<=LOI No.)\\s*(.*)");
		Matcher matcher17 = Intimation_LetterDate.matcher(pdfoutput);
		Pattern Intimation_ServiceName = Pattern.compile("(?<=Discount:)\\s*(.*)");
		Matcher matcher18 = Intimation_ServiceName.matcher(pdfoutput);

		if (matcher9.find())
		{
			String IntiHolder = matcher9.group(0);

			System.out.println("Intimation Holder Name printed in License copy of Transfer Under Nomination is :--> " + IntiHolder);

			System.out.println("******************************************************************************");
		}

		if (matcher10.find())
		{
			String IntiLetter = matcher10.group(0);

			System.out.println("Intimation Letter printed in License copy is :--> " + IntiLetter);

			System.out.println("******************************************************************************");
		}

		if (matcher11.find())
		{
			String IntiTotalAmount = matcher11.group(1);

			System.out.println("Intimation Total Amount printed in License copy is :--> " + IntiTotalAmount);

			System.out.println("******************************************************************************");
		}

		if (matcher12.find())
		{
			String IntiAppNo = matcher12.group(1);

			System.out.println("Intimation Application Numbar printed in License copy is :--> " + IntiAppNo);

			System.out.println("******************************************************************************");
		}

		if (matcher13.find())
		{
			String IntiAppDate = matcher13.group(1);

			System.out.println("Intimation Application Date printed in License copy is :--> " + IntiAppDate);

			System.out.println("******************************************************************************");
		}
		if (matcher14.find())
		{
			String IntiLicenseFee = matcher14.group(1);

			System.out.println("Intimation Market License Fee printed in License copy is :--> " + IntiLicenseFee);

			System.out.println("******************************************************************************");
		}
		if (matcher15.find())
		{
			String IntiLicenseDiscount = matcher15.group(1);

			System.out.println("Intimation Market License Discount printed in License copy is :--> " + IntiLicenseDiscount);

			System.out.println("******************************************************************************");
		}
		if (matcher16.find())
		{
			String IntiLicenseLOIno = matcher16.group(1);

			System.out.println("Intimation LOI No. printed in License copy is :--> " + IntiLicenseLOIno);

			System.out.println("******************************************************************************");
		}
		if (matcher17.find())
		{
			String IntiLetterDate = matcher17.group(1);

			System.out.println("Intimation Letter Date printed in License copy is :--> " + IntiLetterDate);

			System.out.println("******************************************************************************");
		}
		if (matcher18.find())
		{
			String IntiServiceName = matcher18.group(1);

			System.out.println("Intimation Service Name printed in License copy is :--> " + IntiServiceName);
			servicenameprintergrid=matcher18.group(1);
			System.out.println("Intimation Service Name printed in License copy is servicenameprintergrid :--> " + servicenameprintergrid);
			System.out.println("******************************************************************************");
		}


	}
	public static void tlunmpdploi1(String output)
	{

		Pattern Intimation_HolderName = Pattern.compile("(?<=To,)\\s*(.*)");
		Matcher matcher9 = Intimation_HolderName.matcher(pdfoutput);
		Pattern Intimation_AppLetterDate = Pattern.compile("(?<=LOI No. )\\s*(.*)");
		Matcher matcher10 = Intimation_AppLetterDate.matcher(pdfoutput);
		Pattern Intimation_TotalAmount = Pattern.compile("(?<=Date)\\s*(.*)");
		Matcher matcher11 = Intimation_TotalAmount.matcher(pdfoutput);
		Pattern Intimation_ApplicationNo = Pattern.compile("(?<=Application No.)\\s*(.*)(|\n)*(?= and)"); // To capture Application No.Left boundary word 'Application No.' & Right boundary word 'and'
		Matcher matcher12 = Intimation_ApplicationNo.matcher(pdfoutput);
		Pattern Intimation_ApplicationDate = Pattern.compile("(?<=dated  )\\s*(.*)");
		Matcher matcher13 = Intimation_ApplicationDate.matcher(pdfoutput);
		Pattern Intimation_LicenseFee = Pattern.compile("(?<=Market License Fee )\\s*(.*)(|\n)*(?=1)");
		Matcher matcher14 = Intimation_LicenseFee.matcher(pdfoutput);
		Pattern Intimation_Discount = Pattern.compile("(?<=)\\s*(.*)(?=Discount:)"); // To capture 'Discount' using only Right boundary
		Matcher matcher15 = Intimation_Discount.matcher(pdfoutput);
		Pattern Intimation_LOIno = Pattern.compile("(?<=)\\s*(.*)(?=LOI No.)"); // To capture 'Discount' using only Right boundary
		Matcher matcher16 = Intimation_LOIno.matcher(pdfoutput);
		Pattern Intimation_LetterDate = Pattern.compile("(?<=LOI No.)\\s*(.*)");
		Matcher matcher17 = Intimation_LetterDate.matcher(pdfoutput);
		Pattern Intimation_ServiceName = Pattern.compile("(?<=Discount:)\\s*(.*)");
		Matcher matcher18 = Intimation_ServiceName.matcher(pdfoutput);


		if (matcher18.find())
		{
			String IntiServiceName = matcher18.group(1);

			System.out.println("Intimation Service Name printed in License copy is :--> " + IntiServiceName);
			servicenameprintergrid=matcher18.group(1);
			System.out.println("Intimation Service Name printed in License copy is servicenameprintergrid :--> " + servicenameprintergrid);
			System.out.println("******************************************************************************");
		}


	}


	//Piyush Hearing Notice By Force 29/12/2016
	public static void NtlHearingNoticePDF(String output)
	{
		try
		{


			Pattern HDE_NoticeNo =Pattern.compile("(?<=Notice No. )\\s*(.*)\\s*(|\n)*(?=Dated)");
			//Pattern HDE_NoticeNo =Pattern.compile("(?<=Notice No. )\\s*(.*)");
			Matcher matcher71 = HDE_NoticeNo.matcher(pdfoutput);

			if (matcher71.find())
			{
				String HDE_NoticeNo1  = matcher71.group(1).trim();
				System.out.println("Show cause Notice No.  is "+HDE_NoticeNo1);


			}
			else
			{
				System.out.println("no result");
			}

			Pattern HDE_NoticeDate =Pattern.compile("(?<=Dated on )\\s*(.*)\\s*(|\n)*(?=This)");
			//Pattern HDE_NoticeNo =Pattern.compile("(?<=Notice No. )\\s*(.*)");
			Matcher matcher72 = HDE_NoticeDate.matcher(pdfoutput);

			if (matcher72.find())
			{
				String HDE_NoticeDate1  = matcher72.group(1).trim();
				System.out.println("Show cause Notice Date is "+HDE_NoticeDate1);
			}
			else
			{
				System.out.println("no result");
			}

			Pattern HDE_HearingDate =Pattern.compile("(?<=office on )\\s*(.*)\\s*(|\n)*(?=at)");
			Matcher matcher73 = HDE_HearingDate.matcher(pdfoutput);

			if (matcher73.find())
			{
				String HDE_HearingDate1  = matcher73.group(1).trim();
				System.out.println("Hearing date is "+HDE_HearingDate1);
			}
			else
			{
				System.out.println("no result");
			}

			//Pattern HDE_AppName =Pattern.compile("\\s*(.*)\\s*(|\\n)*");
			//Pattern HDE_AppName =Pattern.compile("\\s*(.*)\\s*(|\\n)*");
			//Pattern HDE_AppName =Pattern.compile("(?<=Hearing Notice)\\s*(.*)\\s*(|\n)*");
			Pattern HDE_AppName =Pattern.compile("(?<=Hearing Notice)\\s*(.*)\\s*(|\n)*(?=Address Line 1 :)",Pattern.DOTALL);
			Matcher matcher74 = HDE_AppName.matcher(pdfoutput);

			if (matcher74.find())
			{
				String HDE_AppName1  = matcher74.group(1);
				String[] HDE_AppNameN = HDE_AppName1.split("\\r?\\n");
				System.out.println("Hearing Person Name is "+HDE_AppNameN[5]);
				String a= HDE_AppNameN[5];
				System.out.println("Hearing Person Name is "+a);
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

	//Piyush Cancellation By Force 29/12/2016
	public static void NtlCancellationByForcePDF(String output)
	{
		try
		{

			Pattern CBF_CancellationNo =Pattern.compile("(?<=Cancellation No. :)\\s*(.*)");
			Matcher matcher65 = CBF_CancellationNo.matcher(pdfoutput);

			if (matcher65.find())
			{
				String CBF_CancellationNo1  = matcher65.group(1).trim();
				System.out.println("Cancellation No. is "+CBF_CancellationNo1);
				CBF_CancellationNo_pdf_list.add(CBF_CancellationNo1);


			}
			else
			{
				System.out.println("no result");
			}

			Pattern CBF_Reason =Pattern.compile("(?<=Adulturation act 1962.)\\s*(.*)\\s*(|\n)*(?=Show cause notice)",Pattern.DOTALL);
			Matcher matcher66 = CBF_Reason.matcher(pdfoutput);

			if (matcher66.find())
			{
				String CBF_Reason1  = matcher66.group(1).trim();
				System.out.println("Cancellation Reason is "+CBF_Reason1);
				CBF_Reason_pdf_list.add(CBF_Reason1);
			}
			else
			{
				System.out.println("no result");
			}


			Pattern CBF_Addressl1 =Pattern.compile("(?<=Address Line 1 :)\\s*(.*)\\s*(|\n)*(?=Address Line 2 :)");
			//Pattern CBF_Address =Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Address Line 1)");
			Matcher matcher67 = CBF_Addressl1.matcher(pdfoutput);

			if (matcher67.find())
			{
				String CBF_Addressl11  = matcher67.group(1).trim();
				System.out.println("Address line 1 is "+CBF_Addressl11);
			}
			else
			{
				System.out.println("no result");
			}


			Pattern CBF_Addressl2 =Pattern.compile("(?<=Address Line 2 :)\\s*(.*)");
			//Pattern CBF_Address =Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Address Line 1)");
			Matcher matcher68 = CBF_Addressl2.matcher(pdfoutput);

			if (matcher68.find())
			{
				String CBF_Addressl21  = matcher68.group(1).trim();
				System.out.println("Address line 2 is"+CBF_Addressl21);
			}
			else
			{
				System.out.println("no result");
			}




			Pattern CBF_LicenceNo =Pattern.compile("(?<=Market License no.)\\s*(.*)\\s*(|\n)*(?=has)");
			Matcher matcher61 = CBF_LicenceNo.matcher(pdfoutput);

			if (matcher61.find())
			{
				String CBF_LicenceNo1  = matcher61.group(1).trim();
				System.out.println("Cancelled Market License No "+CBF_LicenceNo1);

				CBF_LicenceNo_pdf_list.add(CBF_LicenceNo1);
			}
			else
			{
				System.out.println("no result");
			}

			Pattern CBF_ScNoticedate =Pattern.compile("(?<=dated)\\s*(.*)\\s*(|\n)*(?=despatch.)");
			Matcher matcher62 = CBF_ScNoticedate.matcher(pdfoutput);

			if (matcher62.find())
			{
				String CBF_ScNoticedate1  = matcher62.group(1).trim();
				System.out.println("Show cause notice date "+CBF_ScNoticedate1);
				CBF_ScNoticedate_list.add(CBF_ScNoticedate1);

			}
			else
			{
				System.out.println("no result");
			}

			Pattern CBF_BuisnessName =Pattern.compile("(?<=business)\\s*(.*)\\s*(|\n)*(?=has)");
			Matcher matcher63 = CBF_BuisnessName.matcher(pdfoutput);

			if (matcher63.find())
			{
				String CBF_BuisnessName1  = matcher63.group(1).trim();
				System.out.println("Business Name is "+CBF_BuisnessName1);
				CBF_BuisnessName__pdf_list.add(CBF_BuisnessName1);

			}
			else
			{
				System.out.println("no result");
			}

			Pattern CBF_InspectionDate =Pattern.compile("(?<=Market Inspector on)\\s*(.*)\\s*(|\n)*(?=and found)",Pattern.DOTALL);
			//Pattern CBF_InspectionDate =Pattern.compile("(?<=Market Inspector on)\\s*(.*)\\s*(|\n)*(?=and)");
			Matcher matcher64 = CBF_InspectionDate.matcher(pdfoutput);

			if (matcher64.find())
			{
				String CBF_InspectionDate1  = matcher64.group(1).trim();
				System.out.println("Inspected by the Market Inspector on "+CBF_InspectionDate1);
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

	// Common Method CASH for New trade License,Change in Buisness Transfer by other mode and Under nomination and Duplicate License LOI payment for 1 type of fee
	// LOI Payment Cash/DD/PO/Cheque

	public static void NewTradeLicenseLOIPayment(String output)
	{
		try
		{

			Pattern Ntl = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Trade License Department)");;		
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
					LOI_Receipt_DateList.add(matcher109.group(1));
					System.out.println("LOI Receipt Date on Cash Receipt is:: " + LOI_Receipt_DateList);

				}
				if (matcher110.find())
				{
					System.out.println("Receipt No : "+matcher110.group(0));
					LOI_Receipt_DateNo.add(matcher110.group(1));
					System.out.println("LOI Receipt No on Cash Receipt is:: " + LOI_Receipt_DateNo);
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

				LOI_ReceivedFrom.add(ReceivedFrom);
				System.out.println("LOI Received From List is : "+LOI_ReceivedFrom);
			} 
			else
			{
				System.out.println("no result");
			}	

			Pattern Ntl_Discount = Pattern.compile("(?<=Discount: )\\s*(.*)");
			Matcher matcher93 = Ntl_Discount.matcher(pdfoutput);

			if (matcher93.find())
			{
				String Ntl_Discount1  = matcher93.group(1).trim();
				System.out.println("Discount is "+Ntl_Discount1);

				LOI_Discount.add(Ntl_Discount1);
				System.out.println("LOI Discount List is:: "+LOI_Discount);

			}
			else
			{
				System.out.println("no result");
			}

			Pattern Ntl_TotalRcvdAmt = Pattern.compile("(?<=Reference No.)\\s*(.*)");
			Matcher matcher94 = Ntl_TotalRcvdAmt.matcher(pdfoutput);

			if (matcher94.find())
			{
				String Ntl_TotalRcvdAmt1  = matcher94.group(1).trim();
				System.out.println("Total Received Amount is "+Ntl_TotalRcvdAmt1);

				LOI_TotalReceivedAmt.add(Ntl_TotalRcvdAmt1);
				System.out.println("LOI Total Received Amount List is ::"+LOI_TotalReceivedAmt);

			}
			else
			{
				System.out.println("no result");
			}



			Pattern Ntl_LicenceNo = Pattern.compile("(?<=Related To CFC Ref.)\\s*(.*)");
			Matcher matcher96 = Ntl_LicenceNo.matcher(pdfoutput);

			if (matcher96.find())
			{
				String Ntl_LicenceNo1  = matcher96.group(1).trim();
				System.out.println("License No. is "+Ntl_LicenceNo1);

				LOI_LicenseNo.add(Ntl_LicenceNo1);
				System.out.println("LOI License No  List is ::"+LOI_LicenseNo);

			}
			else
			{
				System.out.println("no result");
			}

			Pattern Ntl_PayMode = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Received From)");
			Matcher matcher97 = Ntl_PayMode.matcher(pdfoutput);

			if (matcher97.find())
			{
				String Ntl_PayMode1  = matcher97.group(1).trim();
				//System.out.println("License No. is "+Ntl_PayMode1);

				Pattern pattern103 = Pattern.compile("(\\s*(.+?) )");
				Matcher matcher113 = pattern103.matcher(Ntl_PayMode1);

				if (matcher113.find())
				{
					System.out.println("Payment Mode is : "+matcher113.group(1));
					LOI_PaymentMode.add(matcher113.group(1).trim());
					System.out.println("LOI Payment Mode List is ::"+LOI_PaymentMode);

				}


			}
			else
			{
				System.out.println("no result");
			}


			Pattern Ntl_LoiDate = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Loi No. Loi Date.)");
			Matcher matcher98 = Ntl_LoiDate.matcher(pdfoutput);

			if (matcher98.find())
			{
				String Ntl_LoiDate1  = matcher98.group(1).trim();
				System.out.println("LOI date "+Ntl_LoiDate1);

				LOI_Date.add(Ntl_LoiDate1);
				System.out.println("LOI Date List is ::"+LOI_Date);
			}
			else
			{
				System.out.println("no result");
			}


			Pattern Ntl_Fee = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Customer Copy)");
			Matcher matcher99 = Ntl_Fee.matcher(pdfoutput);

			if (matcher99.find())
			{
				String Ntl_LitransFee1  = matcher99.group(1).trim();
				System.out.println("LOI License Fee is:: "+Ntl_LitransFee1);

				LOI_LicenseFee.add(Ntl_LitransFee1);
				System.out.println("LOI License Fee List is ::"+Ntl_LitransFee1);


				//Pattern pattern104 = Pattern.compile("(\\s*(.+?) s*(.+?) s*(.+?) s*(.+?) s*(.+?))");
				Pattern pattern104 = Pattern.compile("((?<=([0-9]+\\.[0-9][0-9]))\\s*(.+?)\\s*(?=[0-9]+.[0-9][0-9]))");
				Matcher matcher114 = pattern104.matcher(Ntl_LitransFee1);

				if (matcher114.find())
				{
					String u = matcher114.group(1);
					System.out.println(u); // Use Variable "u" for fee type name if 1 fee available
				}

				Pattern pattern105 = Pattern.compile("([0-9]+)");
				Matcher matcher115 = pattern105.matcher(Ntl_LitransFee1);

				if (matcher115.find())
				{
					String v = matcher115.group(1);
					System.out.println(v);   // Use Variable "v" for fee type amount for  1st fee available
				}
			}
			else
			{
				System.out.println("no result");
			}

			Pattern Ntl_LoiNo =Pattern.compile("(?<=Office Copy)\\s*(.*)\\s*(?=License No.)",Pattern.DOTALL);
			Matcher matcher95 = Ntl_LoiNo.matcher(pdfoutput);

			if (matcher95.find())
			{
				String Ntl_LoiNo1  = matcher95.group(1).trim();
				String[] NtlLoiNo2 = Ntl_LoiNo1.split("\\r?\\n");
				String LOI_No= NtlLoiNo2[27];
				System.out.println("Cash Receipt Loi No is "+LOI_No);

				LOI_LOINo.add(LOI_No);
				System.out.println("Receipt LOI No List is ::"+LOI_LOINo);
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

	///////////////////////RTI print reprint//////////////////////////////////////////////////////
	//////////////////////////////////////APA REJECTEION//////////////////hearing apa rejected/////////////


	public static void APIRejectionLetter()
	{
		try
		{
			Pattern CBF_CancellationNo =Pattern.compile("(?<=With reference to your application Dated:)\\s*(.*)");
			Matcher matcher65 = CBF_CancellationNo.matcher(pdfoutput);

			if (matcher65.find())
			{
				String CBF_CancellationNo1  = matcher65.group(1).trim();
				System.out.println("Cancellation No. is ==>"+CBF_CancellationNo1);
				String[] data=CBF_CancellationNo1.split("\\s+");
				System.out.println("length=>"+data.length);
				/*for(int reg=0;reg<6;reg++)
{
System.out.println(""+reg+" element=>"+data[reg]);
}*/
				System.out.println("Application date element=>"+data[0]);
				String pdf_Application_date=data[0];
				pdf_Application_date_list.add(pdf_Application_date);
				System.out.println("3 element=>"+data[5]);
				String pdf_Application_appealno=data[0];
				pdf_Application_appealno_list.add(pdf_Application_appealno);
				//data[]= CBF_CancellationNo1.split(" ");
				System.out.println("pdf_Application_date_list"+pdf_Application_date_list);
				System.out.println("pdf_Application_appealno_list"+pdf_Application_appealno_list);
			}
			else
			{
				System.out.println("no result");
			}

			///////////////////////////////2nd/////////////'
			Pattern pdf_address =Pattern.compile("(?<=Address )\\s*(.*)");
			Matcher matcher66 = pdf_address.matcher(pdfoutput);

			if (matcher66.find())
			{
				String address  = matcher66.group(1).trim();
				System.out.println("address is ==>"+address);

				pdf_Application_address_list.add(address);
				System.out.println("pdf_Application_address_list"+pdf_Application_address_list);

			}
			else
			{
				System.out.println("no result");
			}

			///////////////////////////////2nd/////////////'
			Pattern pdf_applicant_name =Pattern.compile("(?<=Shri/Smt/Kum.)\\s*(.*)");
			Matcher matcher67 = pdf_applicant_name.matcher(pdfoutput);

			if (matcher67.find())
			{
				String pdf_applicantname_fetched  = matcher67.group(1);
				System.out.println("applicant name is ==>"+pdf_applicantname_fetched);

				pdf_Application_applicantname_list.add(pdf_applicantname_fetched);
				System.out.println("pdf_Application_applicantname_list"+pdf_Application_applicantname_list);

			}
			else
			{
				System.out.println("no result");
			}

			////////////////////////////////////////
			Pattern pdf_reason =Pattern.compile("(?<=1.The information requested cannot be provided for the following reasons:)\\s*(.*)");

			Matcher matcher68 = pdf_reason.matcher(pdfoutput);

			if (matcher68.find())
			{
				String pdf_reason_fetched  = matcher68.group(1);
				System.out.println("reason ==>"+pdf_reason_fetched);
				pdf_Application_applicantname_reason.add(pdf_reason_fetched);
				System.out.println("pdf_Application_applicantname_reason"+pdf_Application_applicantname_reason);
			}
			else
			{
				System.out.println("no result");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}


	//////////RTIStatusRejectionLetter////////////////secondappeallist/////////////////



	public static void RTIStatusRejectionLetter()
	{
		try
		{
			Pattern CBF_CancellationNo =Pattern.compile("(?<=RTI Application No.:)\\s*(.*)");
			Matcher matcher65 = CBF_CancellationNo.matcher(pdfoutput);

			if (matcher65.find())
			{
				String CBF_CancellationNo1  = matcher65.group(1).trim();
				System.out.println("Cancellation No. is ==>"+CBF_CancellationNo1);
				String[] data=CBF_CancellationNo1.split("\\s+");
				System.out.println("length=>"+data.length);
				/*	for(int reg=0;reg<data.length;reg++)
{
System.out.println(""+reg+" element=>"+data[reg]);
}*/
				System.out.println("Application date element=>"+data[0]);
				String ss=data[0];
				pdf_rtiapplicationnumber_list.add(ss);
				System.out.println("3 element=>"+data[2]);
				String sss=data[2];
				pdf_rtiapplicationdate_list.add(sss);
				//data[]= CBF_CancellationNo1.split(" ");
			}
			else
			{
				System.out.println("no result");
			}

			///////////////////////////////2nd/////////////'
			Pattern pdf_address =Pattern.compile("(?<=Address )\\s*(.*)");
			Matcher matcher66 = pdf_address.matcher(pdfoutput);

			if (matcher66.find())
			{
				String address  = matcher66.group(1).trim();
				System.out.println("address is ==>"+address);
				pdf_rtiapplicationaddress_list.add(address);
			}
			else
			{
				System.out.println("no result");
			}

			///////////////////////////////2nd/////////////'
			Pattern pdf_applicant_name =Pattern.compile("(?<=Shri/Smt/Kum .:)\\s*(.*)");
			Matcher matcher67 = pdf_applicant_name.matcher(pdfoutput);

			if (matcher67.find())
			{
				String pdf_applicantname_fetched  = matcher67.group(1);
				System.out.println("applicant name is ==>"+pdf_applicantname_fetched);
				pdf_applicantname_list.add(pdf_applicantname_fetched);
			}
			else
			{
				System.out.println("no result");
			}


		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	////////////////////forward//////////////////////////////////////////fwpio///

	public static void RTIforwardLetter()
	{
		try
		{
			Pattern forwardno=Pattern.compile("(?<=No:)\\s(.*).*");
			Matcher matcher111=forwardno.matcher(pdfoutput);
			if (matcher111.find()) {
				String fwno=matcher111.group(1).trim();
				System.out.println("RTI application Number=>"+fwno);
				String [] rtinodate=fwno.split("\\s+");
				/*	for (int i = 0; i < rtinodate.length; i++) {
	System.out.println(i+"RTI details=>"+rtinodate[i]);
}*/
				String fwrtinumber=rtinodate[0];
				System.out.println("RTI application Number=>"+fwrtinumber);
				pdffwrtinumber_list.add(fwrtinumber);
				System.out.println("pdffwrtinumber_list=>"+pdffwrtinumber_list);
				String fwdate=rtinodate[2];
				System.out.println("Date=>"+fwdate);
				pdffwdate_list.add(fwdate);
				System.out.println("pdffwdate_list=>"+pdffwdate_list);
			}
			String fwaddress1="";
			Pattern fwaddress=Pattern.compile("(?<=Address:)\\s(.*).*");
			Matcher matcher112=fwaddress.matcher(pdfoutput);
			for(int i=0;i<2;i++)
			{
				if (matcher112.find()) {
					fwaddress1=matcher112.group(0).trim();
					System.out.println("RTI applicant address=>"+fwaddress1);


				}

			}
			pdffwaddress1_list.add(fwaddress1);
			System.out.println("pdffwaddress1_list=>"+pdffwaddress1_list);
			/////////////////////////////////////////////////////////////////////
			Pattern fwmobile=Pattern.compile("(?<=Mobile No:)\\s(.*).*");
			Matcher matcher113=fwmobile.matcher(pdfoutput);
			if (matcher113.find()) {
				String fwmobile1=matcher113.group(1).trim();
				System.out.println("RTI applicant mobile=>"+fwmobile1);
				pdf_fwmobile1_list.add(fwmobile1);
			}
			////////////////////////////


			String fwemail2="";
			Pattern fwemail=Pattern.compile("(?<=Email:)\\s(.*).*");
			Matcher matcher114=fwemail.matcher(pdfoutput);
			for(int j=0;j<2;j++)
			{
				if (matcher114.find()) {
					fwemail2=matcher114.group(1).trim();
					System.out.println("RTI applicant email=>"+fwemail2);


				}
			}	

			pdf_fwemail2_list.add(fwemail2);

		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}




	////////////////////////pioRejectionLetter.pdf////////////////////////////pio



	public static void piorejected()
	{
		try
		{

			Pattern pdf_piorejaddress =Pattern.compile("(?<=Address : )\\s*(.*)");
			Matcher matcher66 = pdf_piorejaddress.matcher(pdfoutput);

			if (matcher66.find())
			{
				String piorejaddress  = matcher66.group(1).trim();
				System.out.println("address is ==>"+piorejaddress);
				pdf_piorejaddress_list.add(piorejaddress);

			}
			else
			{
				System.out.println("no result");
			}

			///////////////////////////////2nd/////////////'
			Pattern pdf_rejapplicant_name =Pattern.compile("(?<=Shri/Smt/Kum .:)\\s*(.*)");
			Matcher matcher67 = pdf_rejapplicant_name.matcher(pdfoutput);

			if (matcher67.find())
			{
				String pdf_rejapplicantname_fetched  = matcher67.group(1);
				System.out.println("applicant name is ==>"+pdf_rejapplicantname_fetched);

				pdf_rejapplicantname_fetched_list.add(pdf_rejapplicantname_fetched);
			}
			else
			{
				System.out.println("no result");
			}

			////////////////////////////////////////

			Pattern forwardno=Pattern.compile("(?<=RTI Application No.:)\\s(.*).*");
			Matcher matcher111=forwardno.matcher(pdfoutput);
			if (matcher111.find()) {
				String rejno=matcher111.group(1).trim();
				//System.out.println("RTI application Number=>"+fwno);
				String [] rtinodate=rejno.split("\\s+");
				/*	for (int i = 0; i < rtinodate.length; i++) {
System.out.println(i+"RTI details=>"+rtinodate[i]);
}*/
				String rejnortinumber=rtinodate[0];
				System.out.println("RTI application Number=>"+rejnortinumber);
				pdf_rejnortinumber_list.add(rejnortinumber);
				System.out.println("rejnortinumber_list"+pdf_rejnortinumber_list);
				String rejdate=rtinodate[2];
				System.out.println("pio rej dateDate=>"+rejdate);
				pdf_rejdate_list.add(rejdate);
				System.out.println("rejdate"+pdf_rejdate_list);
			}	
			////////////////////////////////////////////////////////////////
			Pattern apaname=Pattern.compile("(?<=First Appellate Authority Name : )\\s(.*).*");
			Matcher matcher555=apaname.matcher(pdfoutput);
			if (matcher555.find()) {
				String rejapaname1=matcher555.group(1).trim();
				System.out.println("First Appellate Authority Name : =>"+rejapaname1);
				pdf_rejapaname1_list.add(rejapaname1);
				System.out.println("pdf_rejapaname1_list=>"+pdf_rejapaname1_list);
			}	


			////////////////////////////////////////////////////////////
			Pattern apaaddress=Pattern.compile("(?<=First Appellate Authority Address : )\\s(.*).*");
			Matcher matcher556=apaaddress.matcher(pdfoutput);
			if (matcher556.find()) {
				String rejapaaddress1=matcher556.group(1).trim();
				System.out.println("First Appellate Authority Address :  =>"+rejapaaddress1);
				pdf_rejapaaddress1_list.add(rejapaaddress1);
				System.out.println("pdf_rejapaaddress1_list=>"+pdf_rejapaaddress1_list);
			}

			//////////////////////// ////////////////////


			Pattern pioreasons=Pattern.compile("(?<=The information requested falls within the exempted categories under sub-rule)\\s(.*).*",Pattern.DOTALL);

			Matcher matcher557=pioreasons.matcher(pdfoutput);
			if (matcher557.find()) {
				String pioreasons1=matcher557.group(1).trim();
				String [] piorejectionreasons=pioreasons1.split("\\n");
				//System.out.println("pio resons  =>"+pioreasons1);
				String subrule8=piorejectionreasons[0];
				String subrule9=piorejectionreasons[1];
				System.out.println("piorejection subrule 8==>"+subrule8);
				pdf_subrule8_list.add(subrule8);
				System.out.println("piorejection subrule 9==>"+subrule9);
				pdf_subrule9_list.add(subrule8);
				System.out.println(subrule8+"===="+subrule9);
			}




		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	////////////////////////LOI.pdf////////////////////////////pio



	public static void PIOLOI()
	{
		try
		{
			////////////////////////////////////////

			Pattern forwardno=Pattern.compile("(?<=RTI Application No.:)\\s(.*).*");
			Matcher matcher111=forwardno.matcher(pdfoutput);
			if (matcher111.find()) {
				String rejno=matcher111.group(1).trim();
				//System.out.println("RTI application Number=>"+fwno);
				String [] rtinodate=rejno.split("\\s+");
				/*	for (int i = 0; i < rtinodate.length; i++) {
	System.out.println(i+"RTI details=>"+rtinodate[i]);
}*/
				String rejnortinumber=rtinodate[0].replace(',', ' ');

				System.out.println("RTI application Number=>"+rejnortinumber.trim());
				pdf_loi_RTI_no.add(rejnortinumber.trim());
				System.out.println("RTI application Number LIst=>"+pdf_loi_RTI_no);

				String rejdate=rtinodate[2];

				System.out.println("pio rej dateDate=>"+rejdate);
				pdf_loi_Date.add(rejdate);
				System.out.println("pdf_loi_Date"+pdf_loi_Date);
			}	
			////////////////////////////////////////////////////////////////
			Pattern apaname=Pattern.compile("(?<=First Appellate Authority Name : )\\s(.*).*");
			Matcher matcher555=apaname.matcher(pdfoutput);
			if (matcher555.find()) {
				String rejapaname1=matcher555.group(1).trim();
				System.out.println("First Appellate Authority Name : =>"+rejapaname1);
				//
			}	
			////////////////////////////////////////////////////////////
			Pattern apaaddress=Pattern.compile("(?<=First Appellate Authority Address : )\\s(.*).*");
			Matcher matcher556=apaaddress.matcher(pdfoutput);
			if (matcher556.find()) {
				String rejapaaddress1=matcher556.group(1).trim();
				System.out.println("First Appellate Authority Address :  =>"+rejapaaddress1);
			}

			////////////////////////////////////////////


			Pattern pioreasons=Pattern.compile("(?<=Application for seeking information under RTI Act, 2015)\\s*(.*)",Pattern.DOTALL);

			Matcher matcher657=pioreasons.matcher(pdfoutput);
			if (matcher657.find()) {
				String pioreasons1=matcher657.group(1).trim();
				String [] piorejectionreasons=pioreasons1.split("\\n");
				for(int i=0;i<piorejectionreasons.length;i++)
				{
					System.out.println(i+"===>"+piorejectionreasons[i]);

				}

				System.out.println("Applicant name==>"+piorejectionreasons[6]);
				pdf_loi_Applicantname.add(piorejectionreasons[6]);
				System.out.println("Adress & pincode==>"+piorejectionreasons[8]);
				pdf_loi_addresspincode.add(piorejectionreasons[8]);
				System.out.println("Adress & pincode==>"+pdf_loi_addresspincode);
				System.out.println("LOI DATE==>"+piorejectionreasons[9]);

				pdf_loi_lidate.add(piorejectionreasons[9]);
				System.out.println("pdf_loi_lidate=>"+pdf_loi_lidate);
				System.out.println("pdf name==>"+piorejectionreasons[10]);
				pdf_loi_pdfname.add(piorejectionreasons[10]);
				System.out.println("pdf_loi_pdfname=>"+pdf_loi_pdfname);

				System.out.println("Total Payable Amount(Rs.)==>"+piorejectionreasons[3]);
				pdf_loi_totalpayable.add(piorejectionreasons[3]);
				System.out.println("LOI NUMBER==>"+piorejectionreasons[5]);
				pdf_loi_loinumber.add(piorejectionreasons[5]);
				System.out.println("pdf_loi_loinumber=>"+pdf_loi_loinumber);
			}		
		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	//Final End of class "PdfPatterns"
	/////////////////////////////////////////piyush pdf patterns for tp & rnl/////////////////////////
	// land development loi letter	
	// hiren kathiria on 17/9/2016
	// changes done on 19/9/2016
	//Changes done by piyush on 05/01/2016 
	public void LandDevelopmentLOIletter(String output,String Owner,String ulbName,String Applino,String loinum)
	{
		try
		{			
			Pattern ulb = Pattern.compile("(?<=Authorised Signature)\\s*(.*)\\s*(|\\n)*(?=Please inform to)");
			Matcher matcher11 = ulb.matcher(pdfoutput);

			if (matcher11.find())
			{
				String ulbname1  = matcher11.group(1).trim();
				System.out.println("ulb name is : "+ulbname1);	
				//mAssert(ulbname,ulbName, "ulbname doesn't match Land Development loi letter. Actual :"+ulbname+"Expected :"+ulbName);			
			}
			else
			{
				System.out.println("no result");
			}

			Pattern servicename = Pattern.compile("(?<=Letter of Intimation for)\\s*(.*)\\s*(|\n)*(?=Note :-)");
			Matcher matcher4 = servicename.matcher(pdfoutput);

			if (matcher4.find())
			{
				String servicename1  = matcher4.group(1).trim();			
				System.out.println("name of service is : "+servicename1);	
			}
			else
			{
				System.out.println("no result");
			}

			Pattern applicationNo = Pattern.compile("(?<=Application  No.)\\s*(.*)\\s*(|\n)*(?=and)");
			Matcher matcher5 = applicationNo.matcher(pdfoutput);

			if (matcher5.find())
			{
				String appno  = matcher5.group(1).trim();			
				System.out.println("application no is : "+appno.trim());		
				mAssert(appno.trim(),Applino.trim(), "applicationNo doesn't match Land Development loi letter. Actual :"+appno.trim()+"Expected :"+Applino.trim());		
			}		
			else
			{
				System.out.println("no result");
			}

			Pattern loino = Pattern.compile("(?<=LOI No.   :-)\\s*(.*)\\s*(|\n)*(?=LOI Date :-)");
			Matcher matcher0 = loino.matcher(pdfoutput);

			if (matcher0.find())
			{
				String loi  = matcher0.group(1).trim();			
				System.out.println("loi no is : "+loi);		
				mAssert(loi,loinum, "loino doesn't match Land Development loi letter. Actual :"+loi+"Expected :"+loinum);			}		
			else
			{
				System.out.println("no result");
			}				

			Pattern name = Pattern.compile("(?<=To,)\\s*(.*)\\s*(|\n)*(?=After receiving)");
			Matcher matcher6 = name.matcher(pdfoutput);

			if (matcher6.find())
			{	 			    
				String name1  = matcher6.group(1).trim();
				String[] string = name1.split("/ |,|\\sand");
				System.out.println("size is "+string.length); 
				for(int a=0;a<string.length;a++)
				{	
					if(a<string.length)
					{	
						System.out.println((a+1)+ ". " + "owner name is : "+string[a].replaceAll("\\s", ""));
						string[a]=string[a].replaceAll("\\s", " ");
						mAssert(string[0],Owner, "owner name doesn't match Land Development loi letter. Actual :"+string[0]+"Expected :"+Owner);			
						/*if(a==1)
					{
					mAssert(string[1],Owner1, "owner name1 doesn't match Land Development loi letter. Actual :"+string[1]+"Expected :"+Owner1);			
					}*/
					}
					else if(a==string.length-1 && string.length>3)
					{			
						System.out.println((a+1)+ ". " + "owner name is : "+string[a].replaceAll("\\s", ""));	
						mAssert(string[a],"others.", "owner name doesn't match Land Development loi letter. Actual :"+string[a]+"Expected :"+"others.");			
					}
				}
			}		
			else
			{	
				System.out.println("no result");    
			}		

			Pattern developmentCharge = Pattern.compile("(?<=Please inform to ULB if any correction)\\s*(.*)\\s*(|\n)*(?=Total Payable Amount  :-)",Pattern.DOTALL);
			Matcher matcher7 = developmentCharge.matcher(pdfoutput);

			if (matcher7.find())
			{
				String charge  = matcher7.group(1).trim();	
				String[] data1=charge.split("\\r?\\n");
				System.out.println("size is : " +data1.length);
				for(int a=0;a<data1.length-1;a++)
				{	
					System.out.println((a+1)+ ". " + "development Charge is : "+data1[a].replaceAll("\\s", "").trim());	  
				}
			}			
			else
			{
				System.out.println("no result");
			}		

			Pattern totalamnt = Pattern.compile("(?<=Total Payable Amount  :-)\\s*(.*)\\s*(|\n)*");
			Matcher matcher8 = totalamnt.matcher(pdfoutput);

			if (matcher8.find())
			{
				String amount  = matcher8.group(1).trim();	
				System.out.println("total amount is : "+amount);		
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



	// Building Permit loi letter	
	// hiren kathiria on 17/9/2016
	//Changes done by piyush on 05/01/2016 
	public void BuildingPermitLOIletter(String output)
	{
		try
		{			
			Pattern ulb = Pattern.compile("(?<=Authorised Signature)\\s*(.*)\\s*(|\\n)*(?=Please inform to)");
			Matcher matcher11 = ulb.matcher(pdfoutput);

			if (matcher11.find())
			{
				String ulbname  = matcher11.group(1).trim();
				System.out.println("ulb name is : "+ulbname);	
				mAssert(ulbname,ULBName, "Msg at Building Permit loi letter. Actual :"+ulbname+"Expected :"+ULBName);			
			}
			else
			{
				System.out.println("no result");
			}

			Pattern servicename = Pattern.compile("(?<=Letter of Intimation for)\\s*(.*)\\s*(|\n)*(?=Note :-)");
			Matcher matcher4 = servicename.matcher(pdfoutput);

			if (matcher4.find())
			{
				String service  = matcher4.group(1).trim();			
				System.out.println("name of service is : "+service);	
			}		
			else
			{
				System.out.println("no result");
			}

			Pattern applicationNo = Pattern.compile("(?<=Application  No.)\\s*(.*)\\s*(|\n)*(?=and)");
			Matcher matcher5 = applicationNo.matcher(pdfoutput);

			if (matcher5.find())
			{
				String appno  = matcher5.group(1).trim();			
				System.out.println("application no is : "+appno);		
				mAssert(appno,appNo, "Msg at Building Permit loi letter. Actual :"+appNo+"Expected :"+mGetPropertyFromFile("tp_UlbName"));			}		
			else
			{
				System.out.println("no result");
			}

			Pattern loino = Pattern.compile("(?<=LOI No.   :-)\\s*(.*)\\s*(|\n)*(?=LOI Date :-)");
			Matcher matcher0 = loino.matcher(pdfoutput);

			if (matcher0.find())
			{
				String loi  = matcher0.group(1).trim();			
				System.out.println("loi no is : "+loi);		
				//mAssert(loi,LOINumber, "Msg at Building Permit loi letter. Actual :"+loi+"Expected :"+LOINumber);		
			}		
			else
			{
				System.out.println("no result");
			}				

			Pattern name = Pattern.compile("(?<=To,)\\s*(.*)\\s*(|\n)*(?=After receiving)");
			Matcher matcher6 = name.matcher(pdfoutput);

			if (matcher6.find())
			{				    
				String name1  = matcher6.group(1).trim();
				String[] string = name1.split("\\r?\\n");	
				System.out.println("owner name is :  "+string[0].replaceAll("\\s", "").trim());  
				mAssert(string[0],mGetPropertyFromFile("tp_ownerName"), "Msg at Building Permit loi letter. Actual :"+string[0]+"Expected :"+mGetPropertyFromFile("tp_ownerName"));			}
			else
			{	
				System.out.println("no result");    
			}		

			Pattern developmentCharge = Pattern.compile("(?<=Please inform to ULB if any correction)\\s*(.*)\\s*(|\n)*(?=Total Payable Amount  :-)",Pattern.DOTALL);
			Matcher matcher7 = developmentCharge.matcher(pdfoutput);

			if (matcher7.find())
			{
				String data  = matcher7.group(1).trim();	
				String[] data1=data.split("\\r?\\n");
				System.out.println("size is : " +data1.length);
				for(int a=0;a<data1.length-1;a++)
				{	
					System.out.println((a+1)+ ". " + "development Charge is : "+data1[a].replaceAll("\\s", "").trim());	  
				}
			}		
			else
			{
				System.out.println("no result");
			}		

			Pattern totalamnt = Pattern.compile("(?<=Total Payable Amount  :-)\\s*(.*)\\s*(|\n)*");
			Matcher matcher8 = totalamnt.matcher(pdfoutput);

			if (matcher8.find())
			{
				String data  = matcher8.group(1).trim();	
				System.out.println("total amount is : "+data);		
				mAssert(data,mGetPropertyFromFile("tp_totalamount"), "Msg at Building Permit loi letter. Actual :"+data+"Expected :"+mGetPropertyFromFile("tp_totalamount"));			
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


	//  Occupancy Certificate approval letter
	// hiren kathiria on 16/9/2016
	public static void OccupancyCertificateApprovalPDF(String output)
	{
		try
		{
			Pattern ulb = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Building Permission)");
			Matcher matcher1 = ulb.matcher(pdfoutput);

			if (matcher1.find())
			{
				String OcAL_ulbname1  = matcher1.group(1).trim();
				System.out.println("ulb name is : "+OcAL_ulbname1);	

				//mAssert(ulbname,ULBName, "Msg at Occupancy Certificate. Actual :"+ulbname+"Expected :"+ULBName);

			}
			else
			{
				System.out.println("no result");
			}

			Pattern servicename = Pattern.compile("(?<=\n)\\s*(.*)\\s*(|\n)*(?=Name and Signature of Authorised Officer)");
			Matcher matcher4 = servicename.matcher(pdfoutput);

			if (matcher4.find())
			{
				String OcAL_servicename1  = matcher4.group(1).trim();			
				System.out.println("servicename is :"+OcAL_servicename1);					
				//mAssert(service,mGetPropertyFromFile("tp_ServiceName"), "Msg at Occupancy Certificate. Actual :"+service+"Expected :"+mGetPropertyFromFile("tp_ServiceName"));			
			}		
			else
			{
				System.out.println("no result");
			}

			Pattern applicationNo = Pattern.compile("(?<=Application No)\\s*(.*)\\s*(|\n)*(?=dated)");
			Matcher matcher5 = applicationNo.matcher(pdfoutput);

			if (matcher5.find())
			{
				String OcAL_appno1  = matcher5.group(1).trim();			
				System.out.println("appno is :"+OcAL_appno1);	
				OCAL_APPLNO.add(OcAL_appno1);
				//mAssert(appno,appNo, "Msg at Occupancy Certificate. Actual :"+appno+"Expected :"+appNo);
			}		
			else
			{
				System.out.println("no result");
			}

			Pattern name = Pattern.compile("(?<=With respect to your Application No.)\\s*(.*)\\s*(|\n)*(?=\n)",Pattern.DOTALL);
			Matcher matcher6 = name.matcher(pdfoutput);

			if (matcher6.find())
			{				    
				String name1  = matcher6.group(1);
				String[] string = name1.split("\\r?\\n");	
				System.out.println("owner name is ::::  "+string[1].replaceAll("\\s", " "));  			    
				String OcAL_name1=string[1].trim();
				OCAL_OWNERNAME.add(OcAL_name1);

			}
			else
			{	
				System.out.println("no result");    
			}

			Pattern addownername = Pattern.compile("(?<=Additional Owner: )\\s*(.*)\\s*(|\n)*");
			Matcher matcher11 = addownername.matcher(pdfoutput);

			if (matcher11.find())
			{				    
				String OcAL_addownername1  = matcher11.group(1).trim();
				//String[] string = name1.split("\\r?\\n");	
				System.out.println("owner name is ::::  "+OcAL_addownername1);  			    
				//mAssert(string[0],mGetPropertyFromFile("tp_OwnerName"), "Msg at Occupancy Certificate. Actual :"+string[0]+"Expected :"+mGetPropertyFromFile("tp_OwnerName"));
				OCAL_ADDITIONALOWNERNAME.add(OcAL_addownername1);
			}
			else
			{	
				System.out.println("no result");    
			}


			Pattern data = Pattern.compile("(?<=respect of Ward)\\s*(.*)\\s*(|\n)*");
			Matcher matcher7 = data.matcher(pdfoutput);


			if (matcher7.find())
			{				    
				String name1  = matcher7.group(1).trim();	
				String[] string = name1.split("\\s");
				System.out.println("size is ::: " +string.length); 
				System.out.println("ward name is ::::  "+string[0]+string[1].trim()); 
				String OcAL_Ward1=string[0]+string[1].trim();
				OCAL_WARD.add(OcAL_Ward1);
				//mAssert((string[0]+string[1]),mGetPropertyFromFile("tp_wardname"), "Msg at Occupancy Certificate. Actual :"+(string[0]+string[1])+"Expected :"+mGetPropertyFromFile("tp_wardname"));
				System.out.println("Plot No (CS)/ is ::::  "+string[5].trim()); 	
				String OcAL_PlotNocs1=string[5].trim();
				OCAL_PLOTNOCS.add(OcAL_PlotNocs1);
				//mAssert(string[5],mGetPropertyFromFile("tp_PlotNo(CS)"), "Msg at Occupancy Certificate. Actual :"+string[5]+"Expected :"+mGetPropertyFromFile("tp_PlotNo(CS)"));
			}				 

			else
			{	
				System.out.println("no result");    
			}		

			Pattern msp = Pattern.compile("\\s*(.*)\\s*(|\n)*(?=Khata No.)");
			Matcher matcher12 = msp.matcher(pdfoutput);

			if (matcher12.find())
			{				    
				String OcAL_Plotmsp1  = matcher12.group(1);
				System.out.println("Plot No (MSP) is ::::  "+OcAL_Plotmsp1);
				OCAL_PLOTNOMSP.add(OcAL_Plotmsp1);
				//mAssert(name1,mGetPropertyFromFile("tp_PlotNo(MSP)"), "Msg at Occupancy Certificate. Actual :"+name1+"Expected :"+mGetPropertyFromFile("tp_PlotNo(MSP)"));
			}
			else
			{	
				System.out.println("no result");    
			}						

			Pattern data1 = Pattern.compile("(?<=Khata No.)\\s*(.*)\\s*(|\n)*");
			Matcher matcher8 = data1.matcher(pdfoutput);


			if (matcher8.find())
			{				    
				String name1  = matcher8.group(1);	
				String[] string = name1.split("\\s");
				//System.out.println("size is ::: " +string.length); 
				System.out.println("khata no is ::::  "+string[0]);  
				String OcAL_KhataNo1=string[0];
				OCAL_KHATANO.add(OcAL_KhataNo1);

				//mAssert(string[0],mGetPropertyFromFile("tp_khataNo"), "Msg at Occupancy Certificate. Actual :"+string[0]+"Expected :"+mGetPropertyFromFile("tp_khataNo"));
				System.out.println("holding no is ::::  "+string[3]);
				String OcAL_HoldingNo1=string[3];
				OCAL_HOLDINGNO.add(OcAL_HoldingNo1);

				//mAssert(string[3],mGetPropertyFromFile("tp_holdingNo"), "Msg at Occupancy Certificate. Actual :"+string[3]+"Expected :"+mGetPropertyFromFile("tp_holdingNo"));
				System.out.println("village name is ::::  "+string[5]);
				String OcAL_Village1=string[5];
				OCAL_VILLAGE.add(OcAL_Village1);
				//mAssert(string[5],mGetPropertyFromFile("tp_VillageName"), "Msg at Occupancy Certificate. Actual :"+string[5]+"Expected :"+mGetPropertyFromFile("tp_VillageName"));
			}				 			
			else
			{	
				System.out.println("no result");    
			}
			//Modified and Updated by piyush 2 jan 2017
			Pattern ApprovalNo = Pattern.compile("(?<=approval no. )\\s*(.*)\\s*(|\n)*(?=dated)");
			Matcher matcher13 = ApprovalNo.matcher(pdfoutput);

			if (matcher13.find())
			{				    
				String OcAL_ApprovalNo1  = matcher13.group(1);
				System.out.println("Approval no./Letter No is ::::  "+OcAL_ApprovalNo1);

				//mAssert(name1,mGetPropertyFromFile("tp_PlotNo(MSP)"), "Msg at Occupancy Certificate. Actual :"+name1+"Expected :"+mGetPropertyFromFile("tp_PlotNo(MSP)"));
			}
			else
			{	
				System.out.println("no result");    
			}		

			Pattern AppliDate = Pattern.compile("(?<=dated)\\s*(.*)\\s*(|\n)*(?=has been)");
			Matcher matcher14 = AppliDate.matcher(pdfoutput);

			if (matcher14.find())
			{				    
				String OcAL_AppliDate1  = matcher14.group(1);
				System.out.println("Application Date is ::::  "+OcAL_AppliDate1);  			    
				//mAssert(name1,mGetPropertyFromFile("tp_PlotNo(MSP)"), "Msg at Occupancy Certificate. Actual :"+name1+"Expected :"+mGetPropertyFromFile("tp_PlotNo(MSP)"));
			}
			else
			{	
				System.out.println("no result");    
			}		

			Pattern TechPerName = Pattern.compile("(?<=Technical Person)\\s*(.*)\\s*(|\n)*(?=as per the notice of)");
			Matcher matcher15 = TechPerName.matcher(pdfoutput);

			if (matcher15.find())
			{				    
				String TechPerName1  = matcher15.group(1).trim();

				Pattern pattern104 = Pattern.compile("(\\s*(.+?) s*(.+?) s*(.+?) s*(.+?) )");
				Matcher matcher114 = pattern104.matcher(TechPerName1);

				if (matcher114.find())
				{
					String OcAL_TechPerName1 = matcher114.group(1).trim();
					System.out.println(OcAL_TechPerName1); // Use Variable "u" for Tech Technical Person Name
				}
				//mAssert(name1,mGetPropertyFromFile("tp_PlotNo(MSP)"), "Msg at Occupancy Certificate. Actual :"+name1+"Expected :"+mGetPropertyFromFile("tp_PlotNo(MSP)"));
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


	public static void TP_trutipatraPdf(String output){
		try{

			Pattern Truti_ApplNo1 = Pattern.compile("(?<=application no.)\\s*(.*)\\s*(|\n)*(?=dated)");		
			Matcher matcher82 = Truti_ApplNo1.matcher(pdfoutput);
			if (matcher82.find())
			{
				String Bpr_ApplNo1  = matcher82.group(1).trim();
				System.out.println("Application No in Truti patra is :"+Bpr_ApplNo1);
				Truti_ApplNo.add(Bpr_ApplNo1);
			}
			else
			{
				System.out.println("no result");
			}

			Pattern Truti_AppDate1 = Pattern.compile("(?<=dated)\\s*(.*)\\s*(|\n)*(?=for Application For Development)");		
			Matcher matcher83 = Truti_AppDate1.matcher(pdfoutput);

			if (matcher83.find())
			{
				String Bpr_Date1  = matcher83.group(1).trim();
				Truti_AppDate.add(Bpr_Date1);
				System.out.println("Application No is : "+Bpr_Date1);
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



	// Approval Letter - Land Development Permit Piyush 03-Jan-2017
	public static void LandDevApprovalPDF(String output)
	{
		try
		{
			Pattern ulb = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Building Permission)");
			Matcher matcher1 = ulb.matcher(pdfoutput);

			if (matcher1.find())
			{
				String LdAL_ulbname1  = matcher1.group(1).trim();
				System.out.println("ulb name is : "+LdAL_ulbname1);	

			}
			else
			{
				System.out.println("no result");
			}

			Pattern servicename = Pattern.compile("(?<=Building Permission)\\s*(.*)\\s*(|\n)*(?=With respect to your)");
			Matcher matcher4 = servicename.matcher(pdfoutput);

			if (matcher4.find())
			{
				String LdAL_servicename1  = matcher4.group(1).trim();			
				System.out.println("servicename is :"+LdAL_servicename1);	
				//mAssert(service,mGetPropertyFromFile("tp_ServiceName"), "Msg at Occupancy Certificate. Actual :"+service+"Expected :"+mGetPropertyFromFile("tp_ServiceName"));			
			}		
			else
			{
				System.out.println("no result");
			}

			Pattern applicationNo = Pattern.compile("(?<=Application No)\\s*(.*)\\s*(|\n)*(?=dated)");
			Matcher matcher5 = applicationNo.matcher(pdfoutput);

			if (matcher5.find())
			{
				String LdAL_applicationNo1  = matcher5.group(1).trim();			
				System.out.println("appno is :"+LdAL_applicationNo1);
				LDAL_APPLNO.add(LdAL_applicationNo1);
				//mAssert(appno,appNo, "Msg at Occupancy Certificate. Actual :"+appno+"Expected :"+appNo);
			}		
			else
			{
				System.out.println("no result");
			}

			Pattern ApprovalDate = Pattern.compile("(?<=Approval Date:)\\s*(.*)\\s*(|\n)*(?=Additional Owner's:)");
			Matcher matcher15 = ApprovalDate.matcher(pdfoutput);

			if (matcher15.find())
			{				    
				String LdAL_ApprovalDate1  = matcher15.group(1).trim();
				System.out.println("Land Development Permit  Approval Letter Date is ::::  "+LdAL_ApprovalDate1);
			}
			else
			{	
				System.out.println("no result");    
			}		

			Pattern name = Pattern.compile("(?<=With respect to your Application No.)\\s*(.*)\\s*(|\n)*(?=\n)",Pattern.DOTALL);
			Matcher matcher6 = name.matcher(pdfoutput);

			if (matcher6.find())
			{				    
				String name1  = matcher6.group(1).trim();
				String[] string = name1.split("\\r?\\n");	
				System.out.println("owner name is ::::  "+string[1].replaceAll("\\s", " ")); 
				String LdAL_OwnerName1=string[1].replaceAll("\\s", " ");
				LDAL_OWNERNAME.add(LdAL_OwnerName1);
				//mAssert(string[0],mGetPropertyFromFile("tp_OwnerName"), "Msg at Occupancy Certificate. Actual :"+string[0]+"Expected :"+mGetPropertyFromFile("tp_OwnerName"));
			}
			else
			{	
				System.out.println("no result");    
			}

			Pattern date = Pattern.compile("(?<=dated)\\s*(.*)\\s*(|\n)*(?=, permission)");
			Matcher matcher7 = date.matcher(pdfoutput);

			if (matcher7.find())
			{				    
				String LdAL_date1  = matcher7.group(1).trim();	
				System.out.println("Land Development Permit  Application date is ::::  "+LdAL_date1);  
				LDAL_APPLIDATE.add(LdAL_date1);
				//mAssert((string[0]+string[1]),mGetPropertyFromFile("tp_wardname"), "Msg at Occupancy Certificate. Actual :"+(string[0]+string[1])+"Expected :"+mGetPropertyFromFile("tp_wardname"));
			}				 
			else
			{	
				System.out.println("no result");    
			}		

			Pattern ward = Pattern.compile("(?<=respect of Ward)\\s*(.*)\\s*(|\n)*(?=Plot No \\(CS\\))");
			Matcher matcher12 = ward.matcher(pdfoutput);

			if (matcher12.find())
			{				    
				String LdAL_ward1  = matcher12.group(1).trim();
				System.out.println("Ward is ::::  "+LdAL_ward1);
				LDAP_WARD.add(LdAL_ward1);
				//mAssert(name1,mGetPropertyFromFile("tp_PlotNo(MSP)"), "Msg at Occupancy Certificate. Actual :"+name1+"Expected :"+mGetPropertyFromFile("tp_PlotNo(MSP)"));
			}
			else
			{	
				System.out.println("no result");    
			}						

			Pattern data = Pattern.compile("(?<=Khata No.)\\s*(.*)\\s*(|\n)*");
			Matcher matcher8 = data.matcher(pdfoutput);


			if (matcher8.find())
			{				    
				String data1  = matcher8.group(1).trim();	
				String[] string = data1.split("\\s");
				System.out.println("size is ::: " +string.length); 
				System.out.println("khata no is ::::  "+string[0].trim());  
				String LdAL_KhataNo1=string[0].trim();
				LDAP_KHATANO.add(LdAL_KhataNo1);

				//mAssert(string[0],mGetPropertyFromFile("tp_khataNo"), "Msg at Occupancy Certificate. Actual :"+string[0]+"Expected :"+mGetPropertyFromFile("tp_khataNo"));
				System.out.println("holding no is ::::  "+string[3].trim());
				String LdAL_HoldingNo1=string[3].trim();
				LDAP_HOLDINGNO.add(LdAL_HoldingNo1);

				//mAssert(string[3],mGetPropertyFromFile("tp_holdingNo"), "Msg at Occupancy Certificate. Actual :"+string[3]+"Expected :"+mGetPropertyFromFile("tp_holdingNo"));

			}				 			
			else
			{	
				System.out.println("no result");    
			}

			Pattern mspkhsara = Pattern.compile("(?<=Plot No \\(CS\\)/Khasra No)\\s*(.*)\\s*(|\n)*(?=Plot No \\(MSP\\))");
			Matcher matcher17 = mspkhsara.matcher(pdfoutput);


			if (matcher17.find())
			{				    
				String LdAL_mspkhsara1  = matcher17.group(1).trim();	
				System.out.println("Plot No (CS)/Khasra No ::: " +LdAL_mspkhsara1); 
				LDAP_MSPKHASARA.add(LdAL_mspkhsara1);

			}				 			
			else
			{	
				System.out.println("no result");    
			}

			Pattern msp = Pattern.compile("(?<=Plot No \\(MSP\\))\\s*(.*)\\s*(|\n)*(?=Khata No)");
			Matcher matcher18 = msp.matcher(pdfoutput);


			if (matcher18.find())
			{				    
				String LdAL_msp1  = matcher18.group(1).trim();	
				System.out.println("Plot No (MSP) ::: " +LdAL_msp1); 
				LDAP_PLOTMSP.add(LdAL_msp1);


			}				 			
			else
			{	
				System.out.println("no result");    
			}


			Pattern ApprovalNo = Pattern.compile("(?<=Approval No: )\\s*(.*)\\s*(|\n)*(?=Approval Date: )");
			Matcher matcher13 = ApprovalNo.matcher(pdfoutput);

			if (matcher13.find())
			{				    
				String LdAL_ApprovalNo1  = matcher13.group(1).trim();
				System.out.println("Approval no./Letter No is ::::  "+LdAL_ApprovalNo1); 
				//LDAP_APPROVAL.add(LdAL_ApprovalNo1);
				//mAssert(name1,mGetPropertyFromFile("tp_PlotNo(MSP)"), "Msg at Occupancy Certificate. Actual :"+name1+"Expected :"+mGetPropertyFromFile("tp_PlotNo(MSP)"));
			}
			else
			{	
				System.out.println("no result");    
			}		

			Pattern Village = Pattern.compile("(?<=Village/Mohalla)\\s*(.*)\\s*(|\n)*(?=of Gaya Municipal)");
			Matcher matcher14 = Village.matcher(pdfoutput);

			if (matcher14.find())
			{				    
				String LdAL_Village1  = matcher14.group(1).trim();
				System.out.println("Village/Mohalla is ::::  "+LdAL_Village1);  		
				LDAP_VILLAGE.add(LdAL_Village1);
				//mAssert(name1,mGetPropertyFromFile("tp_PlotNo(MSP)"), "Msg at Occupancy Certificate. Actual :"+name1+"Expected :"+mGetPropertyFromFile("tp_PlotNo(MSP)"));
			}
			else
			{	
				System.out.println("no result");    
			}		

			Pattern addownername = Pattern.compile("(?<=Additional Owner's: 1.)\\s*(.*)\\s*(|\n)*(?=Municipal Corporation)",Pattern.DOTALL);
			Matcher matcher11 = addownername.matcher(pdfoutput);

			if (matcher11.find())
			{				    
				String addownername1  = matcher11.group(1).trim();
				//System.out.println("owner name is ::::  "+addownername1);
				String[] string = addownername1.split("\\r?\\n");	
				System.out.println("Additional owner name 1 is ::::  "+string[1]); 
				System.out.println("Additional owner name 2 is ::::  "+string[2]); 
				//mAssert(string[0],mGetPropertyFromFile("tp_OwnerName"), "Msg at Occupancy Certificate. Actual :"+string[0]+"Expected :"+mGetPropertyFromFile("tp_OwnerName"));
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


	//Building Permit Approval letter
	public static void BuildingPermitApprovalPDF(String output)
	{
		try
		{
			Pattern ulb = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Building Permission)");
			Matcher matcher1 = ulb.matcher(pdfoutput);

			if (matcher1.find())
			{
				String Bpr_ulbname1  = matcher1.group(1).trim();
				System.out.println("ulb name is : "+Bpr_ulbname1);	
				//mAssert(ulbname,ULBName, "Msg at Occupancy Certificate. Actual :"+ulbname+"Expected :"+ULBName);
			}
			else
			{
				System.out.println("no result");
			}

			Pattern servicename = Pattern.compile("(?<=Building Permission)\\s*(.*)\\s*(|\n)*(?=With respect to your)");
			Matcher matcher4 = servicename.matcher(pdfoutput);

			if (matcher4.find())
			{
				String Bp_servicename11  = matcher4.group(1).trim();			
				System.out.println("servicename is :"+Bp_servicename11);

				//mAssert(service,mGetPropertyFromFile("tp_ServiceName"), "Msg at Occupancy Certificate. Actual :"+service+"Expected :"+mGetPropertyFromFile("tp_ServiceName"));			
			}		
			else
			{
				System.out.println("no result");
			}

			Pattern applicationNo = Pattern.compile("(?<=Application No)\\s*(.*)\\s*(|\n)*(?=dated)");
			Matcher matcher5 = applicationNo.matcher(pdfoutput);

			if (matcher5.find())
			{
				String Bpr_applicationNo1  = matcher5.group(1).trim();			
				System.out.println("appno is :"+Bpr_applicationNo1);
				BPAL_APPLICATIONNO.add(Bpr_applicationNo1);
				//mAssert(appno,appNo, "Msg at Occupancy Certificate. Actual :"+appno+"Expected :"+appNo);
			}		
			else
			{
				System.out.println("no result");
			}

			Pattern ApprovalDate = Pattern.compile("(?<=Approval Date:)\\s*(.*)\\s*(|\n)*(?=for)");
			Matcher matcher15 = ApprovalDate.matcher(pdfoutput);

			if (matcher15.find())
			{				    
				String Bpr_ApprovalDate1  = matcher15.group(1).trim();
				System.out.println("Building Permit Approval Letter Date is ::::  "+Bpr_ApprovalDate1);  			    
			}
			else
			{	
				System.out.println("no result");    
			}		



			Pattern name = Pattern.compile("(?<=With respect to your Application No.)\\s*(.*)\\s*(|\n)*(?=\n)",Pattern.DOTALL);
			Matcher matcher6 = name.matcher(pdfoutput);

			if (matcher6.find())
			{				    
				String name1  = matcher6.group(1).trim();
				String[] string = name1.split("\\r?\\n");	
				System.out.println("owner name is ::::  "+string[1].replaceAll("\\s", " ")); 
				String Bpr_OwnerName1=string[1].replaceAll("\\s", " ");
				BPAL_OWNERNAME.add(Bpr_OwnerName1);
				//mAssert(string[0],mGetPropertyFromFile("tp_OwnerName"), "Msg at Occupancy Certificate. Actual :"+string[0]+"Expected :"+mGetPropertyFromFile("tp_OwnerName"));
			}
			else
			{	
				System.out.println("no result");    
			}


			Pattern date = Pattern.compile("(?<=dated)\\s*(.*)\\s*(|\n)*(?=, permission)");
			Matcher matcher7 = date.matcher(pdfoutput);


			if (matcher7.find())
			{				    
				String Bpr_Applidate1  = matcher7.group(1).trim();	
				System.out.println("Building Permit Application date is ::::  "+Bpr_Applidate1);  
				//mAssert((string[0]+string[1]),mGetPropertyFromFile("tp_wardname"), "Msg at Occupancy Certificate. Actual :"+(string[0]+string[1])+"Expected :"+mGetPropertyFromFile("tp_wardname"));
			}				 

			else
			{	
				System.out.println("no result");    
			}		

			Pattern ward = Pattern.compile("(?<=respect of Ward)\\s*(.*)\\s*(|\n)*(?=Plot No \\(CS\\))");
			Matcher matcher12 = ward.matcher(pdfoutput);

			if (matcher12.find())
			{				    
				String Bpr_ward1  = matcher12.group(1).trim();
				System.out.println("Ward is ::::  "+Bpr_ward1);  
				BPAL_WARD.add(Bpr_ward1);
				//mAssert(name1,mGetPropertyFromFile("tp_PlotNo(MSP)"), "Msg at Occupancy Certificate. Actual :"+name1+"Expected :"+mGetPropertyFromFile("tp_PlotNo(MSP)"));
			}
			else
			{	
				System.out.println("no result");    
			}						

			Pattern data = Pattern.compile("(?<=Khata No.)\\s*(.*)\\s*(|\n)*");
			Matcher matcher8 = data.matcher(pdfoutput);


			if (matcher8.find())
			{				    
				String data1  = matcher8.group(1).trim();	
				String[] string = data1.split("\\s");
				//System.out.println("size is ::: " +string.length); 
				System.out.println("khata no is ::::  "+string[0].trim()); 
				String Bp_KhataNo=string[0].trim();
				BPAL_KHATANO.add(Bp_KhataNo);
				//mAssert(string[0],mGetPropertyFromFile("tp_khataNo"), "Msg at Occupancy Certificate. Actual :"+string[0]+"Expected :"+mGetPropertyFromFile("tp_khataNo"));
				System.out.println("holding no is ::::  "+string[3].trim());  
				String Bp_HoldingNo=string[3].trim();
				BPAL_HOLDINGNO.add(Bp_HoldingNo);
				//mAssert(string[3],mGetPropertyFromFile("tp_holdingNo"), "Msg at Occupancy Certificate. Actual :"+string[3]+"Expected :"+mGetPropertyFromFile("tp_holdingNo"));

			}				 			
			else
			{	
				System.out.println("no result");    
			}

			Pattern ApprovalNo = Pattern.compile("(?<=Approval No: )\\s*(.*)\\s*(|\n)*(?=Approval Date: )");
			Matcher matcher13 = ApprovalNo.matcher(pdfoutput);

			if (matcher13.find())
			{				    
				String Bp_ApprovalNo1  = matcher13.group(1).trim();
				System.out.println("Approval no./Letter No is ::::  "+Bp_ApprovalNo1);  			    
				//mAssert(name1,mGetPropertyFromFile("tp_PlotNo(MSP)"), "Msg at Occupancy Certificate. Actual :"+name1+"Expected :"+mGetPropertyFromFile("tp_PlotNo(MSP)"));
			}
			else
			{	
				System.out.println("no result");    
			}		

			Pattern Village = Pattern.compile("(?<=Village/Mohalla)\\s*(.*)\\s*(|\n)*(?=of Gaya Municipal)");
			Matcher matcher14 = Village.matcher(pdfoutput);

			if (matcher14.find())
			{				    
				String Bp_Village1  = matcher14.group(1).trim();
				System.out.println("Village/Mohalla is ::::  "+Bp_Village1);  
				BPAL_VILLAGE.add(Bp_Village1);
				//mAssert(name1,mGetPropertyFromFile("tp_PlotNo(MSP)"), "Msg at Occupancy Certificate. Actual :"+name1+"Expected :"+mGetPropertyFromFile("tp_PlotNo(MSP)"));
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

	//Town Planning Querry Letter
	public static void QueryLetterPDF(String output)
	{ try{
		Pattern ApplicantName = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Query Letter)");
		Matcher matcher1 = ApplicantName.matcher(pdfoutput);

		if (matcher1.find())
		{
			String Querry_ApplicantName1  = matcher1.group(1).trim();
			System.out.println("Applicant Name is : "+Querry_ApplicantName1);
			Querry_ApplicantName.add(Querry_ApplicantName1);

		}
		else
		{
			System.out.println("no result");
		}


		Pattern ulb = Pattern.compile("\\s*(.*)\\s*(|\\n)*");
		Matcher matcher2 = ulb.matcher(pdfoutput);

		if (matcher2.find())
		{
			String Querry_ulb1 = matcher2.group(1).trim();
			System.out.println("ULB Name is : "+Querry_ulb1);


		}
		else
		{
			System.out.println("no result");
		}


		Pattern letter = Pattern.compile("\\s*(.*)\\s*(|\n)*(?=To,)");		
		Matcher matcher24 = letter.matcher(pdfoutput);

		if (matcher24.find())
		{
			String Querry_letter1  = matcher24.group(1).trim();
			System.out.println("Letter type is : "+Querry_letter1);
		}
		else
		{
			System.out.println("no result");
		}


		Pattern ApplicationNo = Pattern.compile("(?<=Application No.)\\s*(.*)\\s*(|\n)*(?=dated)");		
		Matcher matcher23 = ApplicationNo.matcher(pdfoutput);

		if (matcher23.find())
		{
			String Querry_ApplicationNo1  = matcher23.group(1).trim();
			System.out.println("Application No: is : "+Querry_ApplicationNo1);
			Querry_ApplicationNo.add(Querry_ApplicationNo1);
		}
		else
		{
			System.out.println("no result");
		}


		Pattern Applicationdate = Pattern.compile("(?<=dated)\\s*(.*)\\s*(|\n)*(?=Sr. No. Document Name)",Pattern.DOTALL);		
		Matcher matcher26 = Applicationdate.matcher(pdfoutput);

		if (matcher26.find())
		{
			String Querry_Applicationdate1 = matcher26.group(1).trim();
			System.out.println("Application Date  is : "+Querry_Applicationdate1);
			Querry_Applicationdate.add(Querry_Applicationdate1);
		}
		else
		{
			System.out.println("no result");
		}


		Pattern remark = Pattern.compile("(?<=1 )\\s*(.*)\\s*(|\n)*(?=2 )",Pattern.DOTALL);
		Matcher matcher21 = remark.matcher(pdfoutput);

		if (matcher21.find())
		{					
			String Querry_remark1  = matcher21.group(1).trim();
			System.out.println("Document 1. : "+Querry_remark1); 
		}
		else
		{	
			System.out.println("no result");    
		}

		Pattern remark2 = Pattern.compile("(?<=2 )\\s*(.*)\\s*(|\n)*(?=Year)",Pattern.DOTALL);
		Matcher matcher22 = remark2.matcher(pdfoutput);

		if (matcher22.find())
		{					
			String Querry_remark2a  = matcher22.group(1).trim();
			System.out.println("Document 1. : "+Querry_remark2a+"Year"); 
		}
		else
		{	
			System.out.println("no result");    
		}

		Pattern sub = Pattern.compile("(?<=your Application.)\\s*(.*)\\s*(|\n)*(?=Mr. Rohan s Khoche)");		
		Matcher matcher28 = sub.matcher(pdfoutput);

		if (matcher28.find())
		{
			String Querry_sub1  = matcher28.group(1).trim();
			System.out.println("Letter Subject is : "+Querry_sub1);			
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

	// land development site inspection letter	(Updated by Piyush  2 jan 2017)
	// hiren kathiria on 18/11/2016
	public static void landDevSiteInspLetter(String output)
	{
		try
		{
			Pattern fullname2 = Pattern.compile("(?<=Authorised Signature)\\s*(.*)\\s*(|\n)*(?=Site Inspection Letter)",Pattern.DOTALL);
			Matcher matcher21 = fullname2.matcher(pdfoutput);

			if (matcher21.find())
			{					
				String LdSIL_appname  = matcher21.group(1).trim(); 		
				System.out.println("applicant name is : "+LdSIL_appname.trim()); 
				SIL_appname.add(LdSIL_appname);

			}
			else
			{	
				System.out.println("no result");    
			}		

			Pattern sub = Pattern.compile("(?<=Subject:)\\s*(.*)\\s*(|\n)*(?=Reference:)");		
			Matcher matcher22 = sub.matcher(pdfoutput);

			if (matcher22.find())
			{
				String LdSIL_serviceName  = matcher22.group(1).trim();
				System.out.println("service name is : "+LdSIL_serviceName);	
				SIL_serviceName.add(LdSIL_serviceName);

			} 
			else
			{
				System.out.println("no result");
			}		


			Pattern app_no2 = Pattern.compile("(?<=Application No.)\\s*(.*)\\s*(|\n)*(?= Application Date)");		
			Matcher matcher23 = app_no2.matcher(pdfoutput);

			if (matcher23.find())
			{
				String LdSIL_ApplicationNo  = matcher23.group(1).trim();
				System.out.println("application no is : "+LdSIL_ApplicationNo);
				SIL_ApplicationNo.add(LdSIL_ApplicationNo);

				/*if(mGetPropertyFromFile("TypeOfExecution").equalsIgnoreCase("dependent"))
				{	
					mAssert(no,applicationNo.get(CurrentinvoCount), "Msg at landDevSiteInspLetter. Actual :"+no+"Expected :"+applicationNo.get(CurrentinvoCount));
				}

				else
				{	
					mAssert(no,mGetPropertyFromFile("applicationNo"), "Msg at landDevSiteInspLetter. Actual :"+no+"Expected :"+mGetPropertyFromFile("applicationNo"));
				}*/
			}
			else
			{
				System.out.println("no result");
			}		


			Pattern date1 = Pattern.compile("(?<=for inspection on)\\s*(.*)\\s*(|\n)*(?= at )",Pattern.DOTALL);		
			Matcher matcher26 = date1.matcher(pdfoutput);

			if (matcher26.find())
			{
				String dates = matcher26.group(1).trim();
				String[] string = dates.split("\\r?\\s");
				String LdSIL_Inspectiondate=string[0].replace(".", "/");
				System.out.println("Inspection date  is : "+LdSIL_Inspectiondate.trim());

				SIL_Inspectiondate.add(LdSIL_Inspectiondate.trim());

				//mAssert(LdSIL_Inspectiondate,officername, "Msg at landDevSiteInspLetter. Actual :"+LdSIL_Inspectiondate +"Expected :"+officername);
			}
			else
			{
				System.out.println("no result");
			}


			Pattern ulb2 = Pattern.compile("\\s*(.*)\\s*(|\n)*(?=Building Permission)");		
			Matcher matcher24 = ulb2.matcher(pdfoutput);

			if (matcher24.find())
			{
				String LdSIL_ulbname  = matcher24.group(1).trim();
				System.out.println("ulb is : "+LdSIL_ulbname);			
			}
			else
			{
				System.out.println("no result");
			}

			Pattern name = Pattern.compile("(?<=Our officer)\\s*(.*)\\s*(|\n)*(?=will visit)");		
			Matcher matcher25 = name.matcher(pdfoutput);

			if (matcher25.find())
			{
				String LdSIL_officer  = matcher25.group(1).trim();
				System.out.println("Inspection officer is : "+LdSIL_officer);
				SIL_officer.add(LdSIL_officer);
				System.out.println("Inspection officer in arraylist is : "+SIL_officer);
				//mAssert(LdSIL_officer,officername, "Msg at landDevSiteInspLetter. Actual :"+LdSIL_officer +"Expected :"+officername);
			}
			else{				
				System.out.println("no result");
			}

			Pattern ward = Pattern.compile("(?<=Ward : )\\s*(.*)\\s*(|\n)*");
			Matcher matcher27 = ward.matcher(pdfoutput);

			if (matcher27.find())
			{
				String LdSIL_ward1  = matcher27.group(1).trim();
				System.out.println("Ward is : "+LdSIL_ward1);
				SIL_ward1.add(LdSIL_ward1);
				//mAssert(ward1,ward, "Msg at landDevSiteInspLetter. Actual :"+officer +"Expected :"+mGetPropertyFromFile("tp_SiteInspectEmpNmdata"));
			}
			else
			{				
				System.out.println("no result");
			}

			Pattern LetterNo = Pattern.compile("(?<=To,)\\s*(.*)\\s*(|\n)*(?=No.)");		
			Matcher matcher28 = LetterNo.matcher(pdfoutput);

			if (matcher28.find())
			{
				String LdSIL_LetterNo1  = matcher28.group(1).trim();
				System.out.println("Inspection Letter no is : "+LdSIL_LetterNo1);
				//mAssert(officer,mGetPropertyFromFile("tp_SiteInspectEmpNmdata"), "Msg at landDevSiteInspLetter. Actual :"+officer +"Expected :"+mGetPropertyFromFile("tp_SiteInspectEmpNmdata"));
			}
			else
			{				
				System.out.println("no result");
			}


			Pattern PlotNo = Pattern.compile("(?<=Address Details:)\\s*(.*)\\s*(|\n)*(?=Ward :)",Pattern.DOTALL);		
			Matcher matcher29 = PlotNo.matcher(pdfoutput);

			if (matcher29.find())
			{
				String PlotNo1  = matcher29.group(1).trim();
				//System.out.println("Plot No.(CS)/Khasra No: is : "+PlotNo1);
				String plotkhasarano[]=PlotNo1.split("\\r?\\n");
				System.out.println("Plot No.(CS)/Khasra No: is : "+plotkhasarano[0].trim());
				String LdSIL_plotkhasaranocs=plotkhasarano[0].trim();
				SIL_plotkhasaranocs.add(LdSIL_plotkhasaranocs);
				System.out.println("Mauja No. is : "+plotkhasarano[1].trim());
				String LdSIL_maujano=plotkhasarano[1].trim();
				SIL_maujano.add(LdSIL_maujano);
				//mAssert(officer,mGetPropertyFromFile("tp_SiteInspectEmpNmdata"), "Msg at landDevSiteInspLetter. Actual :"+officer +"Expected :"+mGetPropertyFromFile("tp_SiteInspectEmpNmdata"));
			}
			else
			{				
				System.out.println("no result");
			}

			//Piyush : Details written 02/01/2017
			Pattern details = Pattern.compile("(?<=Toji No.:)\\s*(.*)\\s*(|\n)*(?=Plot No.)",Pattern.DOTALL);		
			Matcher matcher30 = details.matcher(pdfoutput);

			if (matcher30.find())
			{
				String details1  = matcher30.group(1).trim();
				//System.out.println("Plot No.(CS)/Khasra No: is : "+details1);
				String plotkhasarano[]=details1.split("\\r?\\n");
				System.out.println("Khata No.: is : "+plotkhasarano[0].trim());
				String LdSIL_KhataNo=plotkhasarano[0].trim();
				SIL_KhataNo.add(LdSIL_KhataNo);
				System.out.println("Holding No.: is : "+plotkhasarano[1].trim());
				String LdSIL_HoldingNo=plotkhasarano[1].trim();
				SIL_HoldingNo.add(LdSIL_HoldingNo);
				System.out.println("Locality/Area: is : "+plotkhasarano[2].trim());
				String LdSIL_Locality=plotkhasarano[2].trim();
				SIL_Locality.add(LdSIL_Locality);
				System.out.println("Toji No.: is : "+plotkhasarano[8].trim());
				String LdSIL_TojiNo=plotkhasarano[8].trim();
				SIL_TojiNo.add(LdSIL_TojiNo);
				System.out.println("Plot No.(MSP): is : "+plotkhasarano[9]);
				String LdSIL_PlotNoMSP =plotkhasarano[9];
				SIL_PlotNoMSP.add(LdSIL_PlotNoMSP);
				//mAssert(officer,mGetPropertyFromFile("tp_SiteInspectEmpNmdata"), "Msg at landDevSiteInspLetter. Actual :"+officer +"Expected :"+mGetPropertyFromFile("tp_SiteInspectEmpNmdata"));
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


	//Town Planning LOI receipt for CASH/DD/Checque By Piyush 03/01/2016
	public static void TPLOIReceiptPDF(String output)
	{
		try
		{

			Pattern Tploi_AppLiNo = Pattern.compile("(?<=Received From)\\s*(.*)\\s*(|\\n)*");
			Matcher matcher95 = Tploi_AppLiNo.matcher(pdfoutput);

			if (matcher95.find())
			{
				String Tploi_AppLiNo1  = matcher95.group(1).trim();
				//System.out.priTploin("Total Received Amount is "+Tploi_AppLiNo1);
				String[] string = Tploi_AppLiNo1.split("\\s");
				//System.out.priTploin("Size is "+Tploi_AppLiNo1.length());
				System.out.println("Application No is: "+(string[5]).trim());
				String Tploi_ApplicationNo=string[5].trim();
				System.out.println(TPLOI_AppliNo.add(Tploi_ApplicationNo));


			}
			else
			{
				System.out.println("no result");
			}


			Pattern Tploi = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Building Permission)");;		
			Matcher matcher91 = Tploi.matcher(pdfoutput);

			if (matcher91.find())
			{
				String Tploi_1  = matcher91.group(1).trim();

				Pattern pattern101 = Pattern.compile("([0-9]+/[0-9]+/[0-9]+)");
				Matcher matcher109 = pattern101.matcher(Tploi_1);

				Pattern pattern102 = Pattern.compile("(\\s*(.+?) / [0-9]+)");
				Matcher matcher110 = pattern102.matcher(Tploi_1);

				if (matcher109.find())
				{
					System.out.println("Receipt date is : "+matcher109.group(1));
					String Tploi_ReceiptDate=matcher109.group(1);
					//TPLOI_ReceiptDate.add(Tploi_ReceiptDate);
				}
				if (matcher110.find())
				{
					System.out.println("Receipt No : "+matcher110.group(0));
					String Tploi_ReceiptNo=matcher110.group(0);
					//TPLOI_ReceiptNo.add(Tploi_ReceiptNo);
				}
			} 
			else
			{
				System.out.println("no result");
			}	


			Pattern Tploi_ReceivedFrom = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Received From)",Pattern.DOTALL);		
			Matcher matcher92 = Tploi_ReceivedFrom.matcher(pdfoutput);

			if (matcher92.find())
			{
				String Tploi_ReceivedFrom1  = matcher92.group(1).trim();
				String Tploiname[]=Tploi_ReceivedFrom1.split("\\r?\\n");
				String Tploi_receivedfromname=Tploiname[1].trim();
				System.out.println("Received From is : "+Tploi_receivedfromname);	
				TPLOI_ReceivedFromName.add(Tploi_receivedfromname);
			} 
			else
			{
				System.out.println("no result");
			}	



			Pattern Tploi_TotalRcvdAmt = Pattern.compile("(?<=Reference No.)\\s*(.*)");
			Matcher matcher94 = Tploi_TotalRcvdAmt.matcher(pdfoutput);

			if (matcher94.find())
			{
				String Tploi_TotalRcvdAmt1  = matcher94.group(1).trim();
				System.out.println("Total Received Amount is "+Tploi_TotalRcvdAmt1);
				TPLOI_TotalRcvdAmt1.add(Tploi_TotalRcvdAmt1);

			}
			else
			{
				System.out.println("no result");
			}


			Pattern Tploi_PayMode = Pattern.compile("(?<=Total Received AmountAmount In Words)\\s*(.*)\\s*(|\\n)*(?=Payment Mode)",Pattern.DOTALL);
			Matcher matcher97 = Tploi_PayMode.matcher(pdfoutput);

			if (matcher97.find())
			{
				String Tploi_PayMode1  = matcher97.group(1).trim();
				//System.out.priTploin("License No. is "+Tploi_PayMode1);
				String string[]=Tploi_PayMode1.split("\\r?\\n");
				System.out.println("LOI No. is "+string[1].trim());
				String Tploi_LOINo=string[1];
				TPLOI_LOINo.add(Tploi_LOINo);
				System.out.println("LOI Date. is "+string[3].trim());
				String Tploi_LOIDate=string[3];
				TPLOI_LOIDate.add(Tploi_LOIDate);
				String a = string[4];
				Pattern pattern103 = Pattern.compile("(\\s*(.+?) )");
				Matcher matcher113 = pattern103.matcher(a);

				if (matcher113.find())
				{
					System.out.println("Payment Mode is : "+matcher113.group(1).trim());
					String Tploi_PaymentMode=matcher113.group(1).trim();
					TPLOI_PaymentMode.add(Tploi_PaymentMode);
				}
			}
			else
			{
				System.out.println("no result");
			}


			Pattern PayMode_Details = Pattern.compile("(?<=Total Received AmountAmount In Words)\\s*(.*)\\s*(|\\n)*(?=Payment Mode)",Pattern.DOTALL);
			Matcher matcher98 = PayMode_Details.matcher(pdfoutput);

			if (matcher98.find())
			{
				String Tploi_PayMode1  = matcher98.group(1).trim();
				//System.out.priTploin("License No. is "+Tploi_PayMode1);
				String[] string = Tploi_PayMode1.split("\\s");
				//System.out.priTploin("size is ::: " +string.length);

				System.out.println("Demand Draft Date/Cheque/Pay Order date: "+(string[17]).trim());
				String Tploi_modedate=string[17];
				TPLOI_ModeDate.add(Tploi_modedate);
				System.out.println("Demand Draft Date/Cheque/Pay Order No: "+(string[16]).trim());
				String Tploi_modeNo=string[16];
				TPLOI_ModeNo.add(Tploi_modeNo);
				System.out.println("Bank Name: "+(string[18]+" "+string[19]+" "+string[20]+" "+string[21]).trim());
				String Tploi_modeBankName=(string[18]+" "+string[19]+" "+string[20]+" "+string[21]).trim();
				TPLOI_ModeBankName.add(Tploi_modeBankName);
			}
			else
			{
				System.out.println("no result");
			}

			Pattern Tploi_Fee = Pattern.compile("(?<=Receipt No.  Date   Related To CFC Ref.)\\s*(.*)\\s*(|\\n)*(?=Customer Copy)",Pattern.DOTALL);
			Matcher matcher99 = Tploi_Fee.matcher(pdfoutput);

			if (matcher99.find())
			{
				String Tploi_LitransFee1  = matcher99.group(1).trim();
				//System.out.priTploin("License Transfer Fee (Nomination) is "+Tploi_LitransFee1);
				String string[]=Tploi_LitransFee1.split("\\r?\\n");
				System.out.println("Development Permit Fee 1 "+string[0].trim());
				System.out.println("Development Permit Fee 2 "+string[1].trim());
				System.out.println("Development Permit Fee 3 "+string[2].trim());
				System.out.println("Development Permit Fee 4 "+string[3].trim());
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




	//Intimation Letter for Town Planning Module Common
	public static void IntimationTownPlanningPDF(String output)
	{
		try
		{

			Pattern Bpr_Subject = Pattern.compile("(?<=charges has to be paid \\(if pending\\).)\\s*(.*)\\s*(|\n)*(?=Note :-)");		
			Matcher matcher501 = Bpr_Subject.matcher(pdfoutput);

			if (matcher501.find())
			{
				String inti_Subject1  = matcher501.group(1).trim();
				System.out.println("Letter Subject is : "+inti_Subject1);			
			} 
			else
			{
				System.out.println("no result");
			}	

			Pattern Bpr_ApplNo = Pattern.compile("(?<=Application  No. )\\s*(.*)\\s*(|\n)*(?=and Application date)");		
			Matcher matcher82 = Bpr_ApplNo.matcher(pdfoutput);

			if (matcher82.find())
			{
				String inti_ApplNo1  = matcher82.group(1).trim();
				System.out.println("Application No is : "+inti_ApplNo1);
				BP_Intim_AppNo.add(inti_ApplNo1);
			}
			else
			{
				System.out.println("no result");
			}

			Pattern Bpr_Appdatedate = Pattern.compile("(?<=Application date )\\s*(.*)\\s*(|\n)*(?=Reference :)");		
			Matcher matcher83 = Bpr_Appdatedate.matcher(pdfoutput);

			if (matcher83.find())
			{
				String inti_Appdatedate1  = matcher83.group(1).trim();
				System.out.println("Application date is : "+inti_Appdatedate1);
				BP_Intim_AppDate.add(inti_Appdatedate1);

			}
			else
			{
				System.out.println("no result");
			}


			Pattern Bpr_ApplicantName = Pattern.compile("(?<=To,)\\s*(.*)");
			Matcher matcher85 = Bpr_ApplicantName.matcher(pdfoutput);

			if (matcher85.find())
			{
				String inti_ApplicantName1  = matcher85.group(1).trim();
				System.out.println("Initimation Letter generated Applicant Name "+inti_ApplicantName1);
				BP_Intim_AppName.add(inti_ApplicantName1);
			}
			else
			{
				System.out.println("no result");
			}

			Pattern Bpr_Applprntdate = Pattern.compile("(?<=LOI Date :-)\\s*(.*)");
			Matcher matcher86 = Bpr_Applprntdate.matcher(pdfoutput);

			if (matcher86.find())
			{
				String inti_Applprntdate1  = matcher86.group(1).trim();
				System.out.println("Initimation Letter generated date "+BP_Intim_Print_date.add(inti_Applprntdate1));

			}
			else
			{
				System.out.println("no result");
			}


			Pattern Bpr_TotalFee = Pattern.compile("(?<=Total Payable Amount  :-)\\s*(.*)");
			Matcher matcher88 = Bpr_TotalFee.matcher(pdfoutput);

			if (matcher88.find())
			{
				String inti_TotalFee1  = matcher88.group(1).trim();
				System.out.println("Total Payable Amount :-  "+BP_Intim_TotalPaybleamt.add(inti_TotalFee1));
			}
			else
			{
				System.out.println("no result");
			}

			Pattern Bpr_PaidDays = Pattern.compile("(?<=valid period of)\\s*(.*)\\s*(|\n)*(?=days.)");		
			Matcher matcher90 = Bpr_PaidDays.matcher(pdfoutput);

			if (matcher90.find())
			{
				String inti_PaidDays1  = matcher90.group(1).trim();
				System.out.println("mentioned charges should be paid within a valid period of : "+inti_PaidDays1);
			}
			else
			{
				System.out.println("no result");
			}


			Pattern Bpr_FeeTypeamt = Pattern.compile("(?<=Please inform to ULB if any correction)\\s*(.*)\\s*(|\n)*(?=Subject     :)",Pattern.DOTALL);		
			Matcher matcher84 = Bpr_FeeTypeamt.matcher(pdfoutput);

			if (matcher84.find())
			{
				String Bpr_FeeTypeamt1  = matcher84.group(1).trim();
				//System.out.println("Application date is : "+Bpr_FeeTypeamt1);
				String a[] = Bpr_FeeTypeamt1.split("\\r?\\n");
				System.out.println("Scrutiny Charges  Building Permit: "+a[0]);
				System.out.println("Scrutiny Charges  Building Permit: "+a[1]);
				//System.out.println("Scrutiny Charges  Building Permit: "+a[2]);
			}
			else
			{
				System.out.println("no result");
			}

			Pattern Bpr_FeeType = Pattern.compile("(?<=if payment is delayed.)\\s*(.*)\\s*(|\n)*(?=Description Amount)",Pattern.DOTALL);		
			Matcher matcher89 = Bpr_FeeType.matcher(pdfoutput);

			if (matcher89.find())
			{
				String Bpr_FeeType1  = matcher89.group(1).trim();
				//System.out.println("Application date is : "+Bpr_FeeType1);
				String a[] = Bpr_FeeType1.split("\\r?\\n");
				System.out.println("First Scrutiny Charges : "+a[0]);
				System.out.println("Second Scrutiny Charges : "+a[1]);

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

	//RNL (Common New) Accepatance letter for Booking of Estate and Rent Renewal Contract by Piyush on 09 jan 2016

	public static void RNL_Acceptance_Letter_PDF(String output){
		try{
			Pattern fullname = Pattern.compile("(?<=\\(Acceptance Letter\\))\\s*(.*)\\s*(?=\\(Rs\\.\\))",Pattern.DOTALL);
			Matcher matcher1 = fullname.matcher(pdfoutput);

			if (matcher1.find())
			{					
				String bill_details  = matcher1.group(1).trim();
				String[] string = bill_details.split("\\r?\\n");
				String service=string[0].replaceAll("\\s", " ").trim();
				String name=string[1]; 
				String[] b=name.split("\\.");
				System.out.println("Service Name is : "+service);
				pdf_ServiceName.add(service);
				System.out.println("Applicant Name is : "+ b[1]); 
				pdf_ApplicantName.add(b[1]);
			}
			else
			{	
				System.out.println("no result");    
			}

			Pattern loi = Pattern.compile("(?<=LOI Date :)\\s*(.*)\\s*(|\n)*(?=Ref.)",Pattern.DOTALL);		
			Matcher matcher2 = loi.matcher(pdfoutput);

			if (matcher2.find())
			{
				String no  = matcher2.group(1).trim();
				String[] string = no.split("\\r?\\n");
				System.out.println("LOI no is :"+string[0]);
				pdf_LOINo.add(string[0]);
				System.out.println("LOI Date is :"+string[1]);
				pdf_LOIDate.add(string[1]);
			} 
			else
			{
				System.out.println("no result");
			}


			Pattern app_no= Pattern.compile("(?<=Application No.)\\s*(.*)\\s*(|\n)*(?=Dated)");		
			Matcher matcher3 = app_no.matcher(pdfoutput);

			if (matcher3.find())
			{
				String no  = matcher3.group(1).trim();
				System.out.println("AppLication No is :"+no);	
				pdf_ApplicationNo.add(no);

			}
			else
			{
				System.out.println("no result");
			}


			Pattern app_date= Pattern.compile("(?<=Dated)\\s*(.*)\\s*(|\n)*");		
			Matcher matcher8 = app_date.matcher(pdfoutput);

			if (matcher8.find())
			{
				String no  = matcher8.group(1).trim();
				System.out.println("AppLication date is :"+no);	
				pdf_Applicationdate.add(no);

			}
			else
			{
				System.out.println("no result");
			}

			Pattern municipality = Pattern.compile("\\s*(.*)\\s*(|\n)*(?=Rent and Lease)");		
			Matcher matcher4 = municipality.matcher(pdfoutput);

			if (matcher4.find())
			{
				String no  = matcher4.group(1).trim();
				System.out.println("ULB is :"+no);
				pdf_ULBName.add(no);
			}
			else
			{
				System.out.println("no result");
			}

			Pattern noofDays = Pattern.compile("(?<=paid within)\\s*(.*)\\s*(|\n)*(?=days.)");		
			Matcher matcher5 = noofDays.matcher(pdfoutput);

			if (matcher5.find())
			{
				String noinDays  = matcher5.group(1).trim();
				System.out.println("Charges should be paid within :"+noinDays);
				pdf_WithinNoOfDays.add(noinDays);

			}
			else
			{
				System.out.println("no result");
			}

			Pattern charges = Pattern.compile("\\s*(.*)\\s*(|\n)*(?=Sr. No. Charge Description Amount)");		
			Matcher matcher6 = charges.matcher(pdfoutput);

			if (matcher6.find())
			{
				String data  = matcher6.group(1).trim();
				System.out.println("Charges and Amount is  :"+data);
				Pattern pattern103 = Pattern.compile("(\\s*(.+?) (.+?) (.+?) )");
				Matcher matcher113 = pattern103.matcher(data);

				if (matcher113.find())
				{
					System.out.println("Charge Description is : "+matcher113.group(1).trim());
					pdf_Feetype1.add(matcher113.group(1).trim());

				}

				Pattern pattern104 = Pattern.compile("([0-9]+)");
				Matcher matcher114 = pattern104.matcher(data);

				if (matcher114.find())
				{
					System.out.println("Charge Amount is : "+matcher114.group(1).trim());
					pdf_Feetypeamt1.add(matcher114.group(1).trim());

				}
			}
			else
			{
				System.out.println("no result");
			}


			Pattern Totpaybleamount = Pattern.compile("\\s*(.*)\\s*(|\n)*(?=Total Payable Amount)");		
			Matcher matcher7 = Totpaybleamount.matcher(pdfoutput);

			if (matcher7.find())
			{
				String amt  = matcher7.group(1).trim();

				Pattern pattern105 = Pattern.compile("([0-9]+)");
				Matcher matcher115 = pattern105.matcher(amt);

				if (matcher115.find())
				{
					System.out.println("Total Payable Amount : "+matcher115.group(1).trim());
					pdf_TotalPaybleAmt.add(matcher115.group(1).trim());

				}

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


	//RNL (Common New) Rejection letter for Booking of Estate and Rent Renewal Contract by Piyush on 09 jan 2016
	public static void RNL_Rejection_letter_PDF(String output){
		try{
			Pattern subject = Pattern.compile("(?<=Rejection for)\\s*(.*)\\s*(|\n)*(?=Service\\.)");		
			Matcher matcher1 = subject.matcher(pdfoutput);

			if (matcher1.find())
			{
				String no  = matcher1.group(1).replaceAll("\\s", " ").trim();
				System.out.println("Rejection Letter Subject is :"+no);	
				pdf_LetterSubject.add(no);
			}
			else
			{
				System.out.println("no result");
			}


			Pattern fullname = Pattern.compile("(?<=To,)\\s*(.*)\\s*");
			Matcher matcher2 = fullname.matcher(pdfoutput);

			if (matcher2.find())
			{
				String no  = matcher2.group(1).replaceAll("[^A-Za-z]", " ").trim();
				System.out.println("Applicant name is :"+no);	
				pdf_ApplicantName.add(no);

			}
			else
			{
				System.out.println("no result");
			}


			Pattern app_no = Pattern.compile("(?<=Application No.)\\s*(.*)\\s*(|\n)*(?=Dated)");		
			Matcher matcher3 = app_no.matcher(pdfoutput);

			if (matcher3.find())
			{
				String no  = matcher3.group(1).trim();
				System.out.println("Application No is :"+no);
				pdf_ApplicationNo.add(no);

			}
			else
			{
				System.out.println("no result");
			}


			Pattern municipality = Pattern.compile("\\s*(.*)\\s*(|\n)*(?=Rent and Lease)");		
			Matcher matcher4 = municipality.matcher(pdfoutput);

			if (matcher4.find())
			{
				String no  = matcher4.group(1).trim();
				System.out.println("ULB is :"+no);	
				pdf_ULBName.add(no);

			}
			else
			{
				System.out.println("no result");
			}			

			Pattern RejNo = Pattern.compile("(?<=Rejection No.      :-)\\s*(.*)\\s*(|\n)*");	
			Matcher matcher5 = RejNo.matcher(pdfoutput);

			if (matcher5.find())
			{
				String no  = matcher5.group(1).trim();
				System.out.println("Rejection No is :"+no);	
				pdf_RejectionNo.add(no);

			}
			else
			{
				System.out.println("no result");
			}			

			Pattern RejDate = Pattern.compile("(?<=Rejection Date   :-)\\s*(.*)\\s*(|\n)*");;		
			Matcher matcher6 = RejDate.matcher(pdfoutput);

			if (matcher6.find())
			{
				String date  = matcher6.group(1).trim();
				System.out.println("Rejection Date is :"+date);	
				pdf_RejectionDate.add(date);

			}
			else
			{
				System.out.println("no result");
			}		


			Pattern RejReason = Pattern.compile("\\s*(.*)\\s*(|\n)*(?=Rejection Reasons  :)");		
			Matcher matcher7 = RejReason.matcher(pdfoutput);

			if (matcher7.find())
			{
				String Reason  = matcher7.group(1).trim();
				System.out.println("Rejection Reason is :"+Reason);	
				pdf_RejectionRemarks.add(Reason);

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

	//RNL Bill Report by Piyush on 09 jan 2016
	public static void RNL_Bill_Pdf(String output){
		try{
			Pattern municipality = Pattern.compile("\\s*(.*)\\s*(|\n)*(?=Rent and Lease)");		
			Matcher matcher4 = municipality.matcher(pdfoutput);

			if (matcher4.find())
			{
				String no  = matcher4.group(1).trim();
				System.out.println("ULB is :"+no);	
				pdf_ULBName.add(no);
			}
			else
			{
				System.out.println("no result");
			}	


			Pattern billNo = Pattern.compile("(?<=Bill No.   :)\\s*(.*)\\s*(|\n)*");		
			Matcher matcher5 = billNo.matcher(pdfoutput);

			if (matcher5.find())
			{
				String no  = matcher5.group(1).trim();
				System.out.println("Bill No is :"+no);
				pdf_BillNo.add(no);
			}
			else
			{
				System.out.println("no result");
			}	

			Pattern billDate = Pattern.compile("(?<=Bill Date :)\\s*(.*)\\s*(|\n)*");		
			Matcher matcher6 = billDate.matcher(pdfoutput);

			if (matcher6.find())
			{
				String no  = matcher6.group(1).trim();
				System.out.println("Bill Date is :"+no);
				pdf_BillDate.add(no);
			}
			else
			{
				System.out.println("no result");
			}	

			Pattern ValidityDate = Pattern.compile("(?<=Billing Period From Date To Date)\\s*(.*)\\s*(|\n)*(?=Bill Details)");		
			Matcher matcher7 = ValidityDate.matcher(pdfoutput);

			if (matcher7.find())
			{
				String Vdate  = matcher7.group(1).trim();
				String[] string = Vdate.split("\\s");
				System.out.println("Billing Period From Date is ::::  "+string[0]);
				pdf_BillingFromDate.add(string[0]);
				System.out.println("Billing Period To Date is ::::  "+string[1]);
				pdf_BillingToDate.add(string[1]);


			}
			else
			{
				System.out.println("no result");
			}	



			Pattern dueDate = Pattern.compile("(?<=Bill Due Date :)\\s*(.*)\\s*(|\n)*");		
			Matcher matcher8 = dueDate.matcher(pdfoutput);

			if (matcher8.find())
			{
				String BillingDuedate  = matcher8.group(1).trim();
				System.out.println("Billing Due Date is :"+BillingDuedate);	
				pdf_BillingDueDate.add(BillingDuedate);


			}
			else
			{
				System.out.println("no result");
			}	

			Pattern data = Pattern.compile("(?<=Excess Adjusted Amount :)\\s*(.*)\\s*(|\n)*(?=Contract No.)",Pattern.DOTALL);		
			Matcher matcher9 = data.matcher(pdfoutput);

			if (matcher9.find())
			{
				String data1  = matcher9.group(1).trim();
				System.out.println("Billing Due Date is :"+data1);
				String string[]=data1.split("\\r?\\s");
				String x[]=data1.split("\\r?\\n");
				System.out.println("Tenant name is :"+string[0]+" "+string[1]);
				pdf_TenantName.add(string[0]+" "+string[1]);
				System.out.println("Contract No. is :"+x[1]);
				pdf_ContractNo.add(x[1]);
				System.out.println("Tenant No. is :"+x[3]);
				pdf_TenantNo.add(x[3]);
			}
			else
			{
				System.out.println("no result");
			}	


			Pattern fees = Pattern.compile("(?<=Please inform to ULB if any correction)\\s*(.*)\\s*(|\n)*(?=Tenant No.)",Pattern.DOTALL);		
			Matcher matcher1 = fees.matcher(pdfoutput);

			if (matcher1.find())
			{
				String fees1  = matcher1.group(1).trim();
				String x[]=fees1.split("\\r?\\n");
				System.out.println("First fee type is :"+x[0]);
				pdf_Feetype1.add(x[0]);
				System.out.println("Second fee type is :"+x[1]);
				pdf_Feetype2.add(x[1]);
				System.out.println("First fee type amount is :"+x[2]);
				pdf_Feetypeamt1.add(x[2]);
				System.out.println("Second fee type amount is :"+x[3]);
				pdf_Feetypeamt2.add(x[3]);

			}
			else
			{
				System.out.println("no result");
			}	


			Pattern totpayamt = Pattern.compile("(?<=Total Bill Amount  :)\\s*(.*)\\s*(|\n)*");		
			Matcher matcher2 = totpayamt.matcher(pdfoutput);

			if (matcher2.find())
			{
				String BillingDuedate  = matcher2.group(1).trim();
				String[] string = BillingDuedate.split("\\r?\\s");
				String string1=string[0].replaceAll(",", "").trim();
				String string2=string1.replaceAll(".00", "").trim();
				System.out.println("Total Payable Amount :"+string2);
				pdf_TotalPaybleAmt.add(string2);

			}
			else
			{
				System.out.println("no result");
			}	

			Pattern discount = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Discount Amount :)");		
			Matcher matcher3 = discount.matcher(pdfoutput);

			if (matcher3.find())
			{
				String discount1  = matcher3.group(1).trim();
				System.out.println("Discount amount is :"+discount1);		
				pdf_DiscountAmt.add(discount1);

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



	//RNL Rent contract note Pening due to defect logged displaying ???? in place of marathi words
	public static void RNL_RentContractNote_PDF(String output){
		try{
			Pattern municipality = Pattern.compile("\\s*(.*)\\s*(|\n)*(?=Rent and Lease)");		
			Matcher matcher4 = municipality.matcher(pdfoutput);

			if (matcher4.find())
			{
				String no  = matcher4.group(1).trim();
				System.out.println("ULB is :"+no);	
				pdf_ULBName.add(no);
			}
			else
			{
				System.out.println("no result");
			}	


			Pattern ApplicantName = Pattern.compile("(?<=To,)\\s*(.*)\\s*(|\n)*");		
			Matcher matcher5 = ApplicantName.matcher(pdfoutput);

			if (matcher5.find())
			{
				String no  = matcher5.group(1).trim();
				System.out.println("Applicant name is :"+no);	
				pdf_ApplicantName.add(no);
			}
			else
			{
				System.out.println("no result");
			}	

			Pattern Sub = Pattern.compile("(?<=Subject :-)\\s*(.*)\\s*(|\n)*");		
			Matcher matcher6 = Sub.matcher(pdfoutput);

			if (matcher6.find())
			{
				String no  = matcher6.group(1).trim();
				System.out.println("Letter Subject is is :"+no);
				pdf_LetterSubject.add(no);
			}
			else
			{
				System.out.println("no result");
			}	


			Pattern date = Pattern.compile("(?<=Due Date Amount)\\s*(.*)\\s*(|\n)*(?=Sr. No. Contract Terms)");		
			Matcher matcher2 = date.matcher(pdfoutput);

			if (matcher2.find())
			{
				String date1  = matcher2.group(1).trim();
				//System.out.println("Payment Due Date is :"+date1);
				String data1[]=date1.split("\\r?\\s");		
				System.out.println("Payment Due Date is :"+data1[1]);
				pdf_DueDate.add(data1[1]);

			}
			else
			{
				System.out.println("no result");
			}	


			Pattern amt = Pattern.compile("(?<=Total Amount :           )\\s*(.*)\\s*(|\n)*");		
			Matcher matcher3 = amt.matcher(pdfoutput);

			if (matcher3.find())
			{
				String amt1  = matcher3.group(1).trim();
				System.out.println("Total payble amount is :"+amt1);
				String a=amt1.replaceAll(".00", "").trim();
				pdf_TotalPaybleAmt.add(a);

			}
			else
			{
				System.out.println("no result");
			}	


			Pattern data = Pattern.compile("(?<=Sir/Madam,)\\s*(.*)\\s*(|\n)*(?=.)",Pattern.DOTALL);		
			Matcher matcher1 = data.matcher(pdfoutput);

			if (matcher1.find())
			{
				String data1  = matcher1.group(1).trim();
				//System.out.println("Lenght is : "+data1.length());
				String a[]=data1.split("\\r?\\s");
				System.out.println("Contract No. is : "+a[2]);
				pdf_ContractNo.add(a[2]);
				System.out.println("Contract No. generated on : "+a[6]);
				pdf_ContractgenDate.add(a[6]);
				System.out.println("Contract No. Duration from date is : "+a[11]);
				pdf_ContractFromDate.add(a[11]);
				System.out.println("Contract No. Duration To date is : "+a[13]);
				pdf_ContractToDate.add(a[13]);
				System.out.println("Contract No. Contract amount is Rs. : "+a[18]);
				pdf_ContractAmt.add(a[18]);
				System.out.println("Contract payment frequency is Monthly is : "+a[23]);
				pdf_ContractPaymentFrequency.add(a[23]);
			} 
			else
			{
				System.out.println("no result");
			}	

			Pattern esno = Pattern.compile("(?<=Please inform to ULB if any correction)\\s*(.*)\\s*(|\n)*(?=Estate No Estate Type Estate Description)");		
			Matcher matcher82 = esno.matcher(pdfoutput);

			if (matcher82.find())
			{
				String EstateNo1  = matcher82.group(1).trim();

				Pattern pattern105 = Pattern.compile("(\\s*(.+?) )");
				Matcher matcher115 = pattern105.matcher(EstateNo1);

				if (matcher115.find())
				{
					System.out.println("Estate No is : "+matcher115.group(1).trim());
					pdf_EstateNo.add(matcher115.group(1).trim());
				}
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

	//RNL Common Approval letter for Booking of Estate and Renewal of rent Contract Modified and updated by Piyush on 10 jan 2016
	public static void RNL_ApprovalLetter(String output){
		try
		{
			Pattern fullname2 = Pattern.compile("(?<=Reference :-)\\s*(.*)\\s*(|\n)*(?=Approval Letter)",Pattern.DOTALL);
			Matcher matcher21 = fullname2.matcher(pdfoutput);

			if (matcher21.find())
			{					
				String bill_details  = matcher21.group(1);
				String[] string = bill_details.split("\\r?\\n");
				String service=string[0].trim();
				String name=string[1]; 
				String[] b=name.split("\\.");
				System.out.println("applicant name is : "+b[1]);
				pdf_ApplicantName.add(b[1]);
				//mAssert(b[1],applicantname, "Msg at approval letter does not match Actual :"+b[1]+" Expected :"+applicantname);
			}
			else
			{	
				System.out.println("no result");    
			}

			Pattern sub = Pattern.compile("(?<=Approval for)\\s*(.*)\\s*(|\n)*(?=\\.)");		
			Matcher matcher22 = sub.matcher(pdfoutput);

			if (matcher22.find())
			{
				String no  = matcher22.group(1).trim();
				System.out.println("service name is : "+no);	
				pdf_ServiceName.add(no);
				//mAssert(no,servicename, "Msg at approval letter does not match Actual :"+no+" Expected :"+servicename);
			} 
			else
			{
				System.out.println("no result");
			}


			Pattern app_no2 = Pattern.compile("(?<=Application No.)\\s*(.*)\\s*(|\n)*(?=for)");		
			Matcher matcher23 = app_no2.matcher(pdfoutput);

			if (matcher23.find())
			{
				String no  = matcher23.group(1).trim();
				System.out.println("appno is : "+no);
				pdf_ApplicationNo.add(no);
				//mAssert(no,appno, "Msg at approval letter does not match Actual :"+no+" Expected :"+appno);
			}
			else
			{
				System.out.println("no result");
			}


			Pattern date = Pattern.compile("(?<=is from)\\s*(.*)\\s*(|\n)*(?=.)",Pattern.DOTALL);		
			Matcher matcher26 = date.matcher(pdfoutput);

			if (matcher26.find())
			{
				String dates = matcher26.group(1);
				String[] string = dates.split("\\r?\\s");
				System.out.println("from date is : "+string[0]); 
				pdf_FromDate.add(string[0]);
				String a=string[2].replace(".", "");
				System.out.println("to date  is : "+a);
				pdf_ToDate.add(a);
			}
			else
			{
				System.out.println("no result");
			}



			Pattern ulb2 = Pattern.compile("\\s*(.*)\\s*(|\n)*(?=Rent and Lease)");		
			Matcher matcher24 = ulb2.matcher(pdfoutput);

			if (matcher24.find())
			{
				String no  = matcher24.group(1).trim();
				System.out.println("ulbname is : "+no);
				pdf_ULBName.add(no);
				//mAssert(no,ULBName, "Msg at approval letter does not match Actual :"+no+" Expected :"+ULBName);
			}
			else
			{
				System.out.println("no result");
			}

			Pattern estate = Pattern.compile("(?<=are as below :-)\\s*(.*)\\s*(|\n)*(?=Estate )");		
			Matcher matcher25 = estate.matcher(pdfoutput);

			if (matcher25.find())
			{
				String bill_details  = matcher25.group(1);
				String[] string = bill_details.split("\\r?\\s");
				System.out.println("estate no is : "+string[0]);
				pdf_EstateNo.add(string[0]);
				System.out.println("estate name is : "+string[1]);
				pdf_EstateDescription.add(string[1]);
				System.out.println("estate add is : "+string[2]);	
				pdf_Estateaddress.add(string[2]);
			}
			else
			{

				System.out.println("no result");
			}	

			//Added on 10 jan 2017 by Piyush
			Pattern subject = Pattern.compile("(?<=Subject     :-)\\s*(.*)\\s*(|\n)*");		
			Matcher matcher2 = subject.matcher(pdfoutput);

			if (matcher2.find())
			{
				String no  = matcher2.group(1).trim();
				System.out.println("Letter Subject is : "+no);	
				pdf_LetterSubject.add(no);
				//mAssert(no,servicename, "Msg at approval letter does not match Actual :"+no+" Expected :"+servicename);
			} 
			else
			{
				System.out.println("no result");
			}

		}
		catch(Exception e){

			e.printStackTrace();	
		}	
	}


	public static void RNL_Common_LOi_Receipt_Cash(String output){
		try{
			Pattern Ntl = Pattern.compile("\\s*(.*)\\s*(|\\n)*");
			Matcher matcher91 = Ntl.matcher(pdfoutput);

			if (matcher91.find())
			{
				String Ntl_1  = matcher91.group(1).trim();

				Pattern pattern101 = Pattern.compile("([0-9]+/[0-9]+/[0-9]+)");
				Matcher matcher109 = pattern101.matcher(Ntl_1);

				Pattern pattern102 = Pattern.compile("(\\s*(.+?)/[0-9]+)");
				Matcher matcher110 = pattern102.matcher(Ntl_1);

				if (matcher109.find())
				{
					System.out.println("Receipt date is : "+matcher109.group(1).trim());
					pdf_ReceiptDate.add(matcher109.group(1).trim());

				}
				if (matcher110.find())
				{
					System.out.println("Receipt No : "+matcher110.group(0).trim());
					pdf_ReceiptNo.add(matcher110.group(0).trim());
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
				String q=ntlname[1];
				pdf_ApplicantName.add(q);
				System.out.println("Received From is : "+q);	
			} 
			else
			{
				System.out.println("no result");
			}	


			Pattern Ntl_TotalRcvdAmt = Pattern.compile("(?<=Related To CFC Ref.)\\s*(.*)");
			Matcher matcher94 = Ntl_TotalRcvdAmt.matcher(pdfoutput);

			if (matcher94.find())
			{
				String Ntl_TotalRcvdAmt1  = matcher94.group(1).trim();
				System.out.println("Total Received Amount is "+Ntl_TotalRcvdAmt1);
				pdf_TotalPaybleAmt.add(Ntl_TotalRcvdAmt1);

			}
			else
			{
				System.out.println("no result");
			}


			Pattern PayMode = Pattern.compile("(?<=Counter Ref.)\\s*(.*)\\s*(|\\n)*(?=Bihar Municipal Act 2007)",Pattern.DOTALL);
			Matcher matcher97 = PayMode.matcher(pdfoutput);

			if (matcher97.find())
			{
				String PayMode1  = matcher97.group(1).trim();
				//System.out.println("License No. is "+PayMode1);
				String p[]=PayMode1.split("\\r?\\n");
				Pattern pattern103 = Pattern.compile("(\\s*(.+?) )");
				Matcher matcher113 = pattern103.matcher(p[5]);

				if (matcher113.find())
				{
					System.out.println("Payment Mode is : "+matcher113.group(1).trim());
					pdf_PaymentMode.add(matcher113.group(1).trim());
				}
			}
			else
			{
				System.out.println("no result");
			}


			Pattern LoiDate = Pattern.compile("(?<=LOI Date)\\s*(.*)\\s*(|\n)*(?=Please inform to ULB if any correction)",Pattern.DOTALL);
			Matcher matcher98 = LoiDate.matcher(pdfoutput);

			if (matcher98.find())
			{
				String LoiDate1  = matcher98.group(1).trim();
				//System.out.println("LOI date "+LoiDate1);
				String s[]=LoiDate1.split("\\r?\\n");
				System.out.println("LOI date is ::"+s[0]);
				pdf_LOIDate.add(s[0]);
				System.out.println("LOI No is ::"+s[2]);
				pdf_LOINo.add(s[2]);
			}
			else
			{
				System.out.println("no result");
			}


			Pattern Ntl_Fee = Pattern.compile("\\s*(.*)\\s*(|\\n)*(?=Customer Copy)");
			Matcher matcher99 = Ntl_Fee.matcher(pdfoutput);

			if (matcher99.find())
			{
				String Ntl_LitransFee1  = matcher99.group(1).trim();
				//System.out.println("License Transfer Fee (Nomination) is "+Ntl_LitransFee1);
				//Pattern pattern104 = Pattern.compile("(\\s*(.+?) s*(.+?) s*(.+?) s*(.+?) s*(.+?))");
				Pattern pattern104 = Pattern.compile("((?<=([0-9]+\\.[0-9][0-9]))\\s*(.+?)\\s*(?=[0-9]+.[0-9][0-9]))");
				Matcher matcher114 = pattern104.matcher(Ntl_LitransFee1);

				if (matcher114.find())
				{
					String u = matcher114.group(1);
					System.out.println(u); // Use Variable "u" for fee type name if 1 fee available
				}

				Pattern pattern105 = Pattern.compile("([0-9]+)");
				Matcher matcher115 = pattern105.matcher(Ntl_LitransFee1);

				if (matcher115.find())
				{
					String v = matcher115.group(1);
					System.out.println(v);   // Use Variable "v" for fee type amount for  1st fee available
				}
			}
			else
			{
				System.out.println("no result");
			}

			Pattern Ntl_LoiNo =Pattern.compile("(?<=Office Copy)\\s*(.*)\\s*(?=License No.)",Pattern.DOTALL);
			Matcher matcher95 = Ntl_LoiNo.matcher(pdfoutput);

			if (matcher95.find())
			{
				String Ntl_LoiNo1  = matcher95.group(1).trim();
				String[] NtlLoiNo1 = Ntl_LoiNo1.split("\\r?\\n");
				String b= NtlLoiNo1[27];
				System.out.println("Loi No is "+b);
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


	public static void rnlpatternChallanPdf(String output){
		try{
			Pattern appchalno = Pattern.compile("(?<=Challan No. : )\\s*(.+?)\\s*(?= Challan No. :)");
			Matcher matcher1 = appchalno.matcher(pdfoutput);


			if (matcher1.find())
			{
				Challanno  = matcher1.group(1).trim();
				System.out.println("Challan No is : "+Challanno);	
				ChallannoRNL.add(Challanno);
				//mAssert(no,servicename, "Msg at approval letter does not match Actual :"+no+" Expected :"+servicename);
			} 
			else
			{
				System.out.println("no result");
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	///////////////////////////////////////END piyush pdf patterns for tp & rnl/////////////////////////
	////////////////////////////////////////////////ADH//////////////////////////////////////
	//Common Method for Bill Due generated while bill printing
	public static void ADH_BillDues_Letter_Pdf(String output){
		try {
			Pattern data=Pattern.compile("(?<=Bihar Municipal Act 2007)\\s*(.*)",Pattern.DOTALL);
			Matcher matcher657=data.matcher(pdfoutput);
			if (matcher657.find()) {
				String data1=matcher657.group(1).trim();
				String [] CollectionDate=data1.split("\\n");
				for(int i=0;i<CollectionDate.length;i++)
				{
					//System.out.println(i+"===>"+CollectionDate[i]);
				}

				Pdf_BP_ApplicantName.add(CollectionDate[25].trim());
				System.out.println("Applicant Name==>"+Pdf_BP_ApplicantName);


				System.out.println("ULB Name==>"+CollectionDate[2].trim());

				Pdf_BP_ApplicantAddress.add(CollectionDate[8].trim());
				System.out.println("Applicant Address==>"+Pdf_BP_ApplicantAddress);


				String[] Date=CollectionDate[13].split(" ");
				Pdf_BP_ContractFromDate.add(Date[0].trim());
				System.out.println("Contract From Date ==>"+Pdf_BP_ContractFromDate);

				Pdf_BP_ContractToDate.add(Date[1].trim());
				System.out.println("Contract To Date ==>"+Pdf_BP_ContractToDate);

				String[] BillNo=CollectionDate[10].split(":");	
				System.out.println("Bill No ==>"+BillNo[1].trim());
				String[] BillDate=CollectionDate[11].split(":");
				System.out.println("Bill Date ==>"+BillDate[1].trim());

				Pdf_BP_TotalPaybleAmt.add(CollectionDate[16].trim());
				System.out.println("Total Payble Amount==>"+Pdf_BP_TotalPaybleAmt);
				//System.out.println("Discount Amount==>"+CollectionDate[20].trim());

				Pdf_BP_DiscountAmount.add(CollectionDate[20].trim());
				System.out.println("Discount Amount==>"+Pdf_BP_DiscountAmount);

				Pdf_BP_ContractNo.add(CollectionDate[26].trim());
				System.out.println("Contract No==>"+Pdf_BP_ContractNo);

				Pdf_BP_AgencyNo.add(CollectionDate[28].trim());
				System.out.println("Agency No==>"+Pdf_BP_AgencyNo);
				String[] BillDueDate=CollectionDate[33].split(":");
				System.out.println("Bill Date ==>"+BillDueDate[1].trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	//Apllication Time  Payment Receipt ::: Cash ::: Booking of Hoarding:::: LOI PAYMENT METHOD RECEIPT
	public static void ADH_ApplicationTimePaymentReceipt_Letter_Pdf(String output){

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
				pdf_ApplicationTime_Receipt_ApplicantName.add(CollectionDate[1]);
				System.out.println("Total Received Amount===>"+CollectionDate[13].trim());
				String[] Data1=CollectionDate[24].split(" ");
				System.out.println("Mode Of Payment ==>"+Data1[0].trim());
				pdf_ApplicationTime_Receipt_ModeOfPayment.add(Data1[0].trim());
			}

			Pattern ContractDate = Pattern.compile("(?<=mentioned below :)\\s*(.*)\\s*(|\n)*(?=Total Payable Amount)");
			Matcher matcher9 = ContractDate.matcher(pdfoutput);
			if (matcher9.find())
			{
				String ContractDate1 = matcher9.group(1).trim();
				System.out.println("Total Payble Amount==>"+ContractDate1);

			} 
			else
			{
				System.out.println("no result");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//Apllication Time  Payment Receipt ::: Cheque/PayOrder ::: Booking of Hoarding:::: LOI PAYMENT METHOD RECEIPT
	public static void ADH_ChecqueApplicationTimePaymentReceipt_Letter_Pdf(String output){
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
				pdf_ApplicationTime_Receipt_ApplicantName.add(CollectionDate[1]);
				System.out.println("Total Received Amount===>"+CollectionDate[13].trim());
				String[] Data1=CollectionDate[24].split(" ");
				System.out.println("Mode Of Payment ==>"+Data1[0].trim());
				pdf_ApplicationTime_Receipt_ModeOfPayment.add(Data1[0].trim());
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

			}

			Pattern ContractDate = Pattern.compile("(?<=mentioned below :)\\s*(.*)\\s*(|\n)*(?=Total Payable Amount)");
			Matcher matcher9 = ContractDate.matcher(pdfoutput);
			if (matcher9.find())
			{
				String ContractDate1 = matcher9.group(1).trim();
				System.out.println("Charge Description  Total Payble Amount==>"+ContractDate1);
				Pdf_Acceptance_TotalPaybleAmt.add(ContractDate1);
			} 
			else
			{
				System.out.println("no result");
			}

			Pattern TotalpaybleAmt = Pattern.compile("(?<=Total Amount :           )\\s*(.*)\\s*(|\n)*(?=Sr. No. )");
			Matcher matcher19 = TotalpaybleAmt.matcher(pdfoutput);
			if (matcher19.find())
			{
				String TotalpaybleAmt1 = matcher19.group(1).trim();
				System.out.println("Contract Terms Charge Total Payble Amount==>"+TotalpaybleAmt1);
				Pdf_Acceptance_TotalAmt.add(TotalpaybleAmt1);
			} 
			else
			{
				System.out.println("no result");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Adh Contract Bill Payment Cash
		

	
		
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
		//ADH Renewal Of Advertisement Contract Approval Letter
		public static void ADH_RenewalOfAdvertisment_Approval_Letter_Pdf(String output){
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

					System.out.println("Applicant Name in Approval of Renewal of Advertisement Contract==>"+CollectionDate[11].trim());
					Pdf_RoA_Approval_ApplicantName.add(CollectionDate[11].trim());
					System.out.println("Applicant Name==>"+Pdf_RoA_Approval_ApplicantName);

					System.out.println("Applicant Address in Approval of Renewal of Advertisement Contract==>"+CollectionDate[12].trim());
					Pdf_RoA_Approval_ApplicantAddress.add(CollectionDate[12].trim());
					System.out.println("Applicant Address==>"+Pdf_RoA_Approval_ApplicantAddress);
				}

				Pattern ApplicationNo = Pattern.compile("(?<=application no. )\\s*(.*)\\s*(|\n)*(?= dated)");
				Matcher matcher1 = ApplicationNo.matcher(pdfoutput);
				if (matcher1.find())
				{
					String ApplicationNo1 = matcher1.group(1).trim();
					System.out.println("Application No in Approval of Renewal of Advertisement Contract is==>"+ApplicationNo1);
					Pdf_RoA_Approval_ApplicantionNo.add(ApplicationNo1);
				} 
				else
				{
					System.out.println("no result");
				}

				Pattern ApplicationDate = Pattern.compile("(?<=dated )\\s*(.*)\\s*(|\n)*(?= for Renewal)");
				Matcher matcher2 = ApplicationDate.matcher(pdfoutput);
				if (matcher2.find())
				{
					String ApplicationDate1 = matcher2.group(1).trim();
					System.out.println("Application Date in Approval of Renewal of Advertisement Contract is==>"+ApplicationDate1);
					Pdf_RoA_Approval_ApplicantionDate.add(ApplicationDate1);
				} 
				else
				{
					System.out.println("no result");
				}

				Pattern ContractNo = Pattern.compile("(?<=Contract No. )\\s*(.*)\\s*(|\n)*(?= dated)");
				Matcher matcher3 = ContractNo.matcher(pdfoutput);
				if (matcher3.find())
				{
					String ContractNo1 = matcher3.group(1).trim();
					System.out.println("Contract No in Approval of Renewal of Advertisement Contract is==>"+ContractNo1);
					Pdf_RoA_Approval_ContractNo.add(ContractNo1);
				} 
				else
				{
					System.out.println("no result");
				}


				Pattern ContractToDate = Pattern.compile("(?<=dated )\\s*(.*)\\s*(|\n)*(?= has been)");
				Matcher matcher4 = ContractToDate.matcher(pdfoutput);
				if (matcher4.find())
				{
					String ContractToDate1 = matcher4.group(1).trim();
					System.out.println("Contract generation in Approval of Renewal of Advertisement Contract is==>"+ContractToDate1);
					Pdf_RoA_Approval_ContractToDate.add(ContractToDate1);
				} 
				else
				{
					System.out.println("no result");
				}


				Pattern RenewedDate = Pattern.compile("(?<=renewed on )\\s*(.*)\\s*(|\n)*(?= for the new)");
				Matcher matcher5 = RenewedDate.matcher(pdfoutput);
				if (matcher5.find())
				{
					String RenewedDate1 = matcher5.group(1).trim();
					System.out.println("Renewal Date in Approval of Renewal of Advertisement Contract is==>"+RenewedDate1);
					Pdf_RoA_Approval_RenewalDate.add(RenewedDate1);
				} 
				else
				{
					System.out.println("no result");
				}


				Pattern RenewedfromDate = Pattern.compile("(?<=period from )\\s*(.*)\\s*(|\n)*(?= to)");
				Matcher matcher6 = RenewedfromDate.matcher(pdfoutput);
				if (matcher6.find())
				{
					String RenewedfromDate1 = matcher6.group(1).trim();
					System.out.println("Renewal From Date in Approval of Renewal of Advertisement Contract is==>"+RenewedfromDate1);
					Pdf_RoA_Approval_RenewedfromDate.add(RenewedfromDate1);
				} 
				else
				{
					System.out.println("no result");
				}


				Pattern RenewedToDate = Pattern.compile("(?<=to )\\s*(.*)\\s*(|\n)*(?=. Your new)");
				Matcher matcher7 = RenewedToDate.matcher(pdfoutput);
				if (matcher7.find())
				{
					String RenewedToDate1 = matcher7.group(1).trim();
					System.out.println("Renewal To Date in Approval of Renewal of Advertisement Contract is==>"+RenewedToDate1);
					Pdf_RoA_Approval_RenewedToDate.add(RenewedToDate1);
				} 
				else
				{
					System.out.println("no result");
				}

				Pattern NewContractNo = Pattern.compile("(?<=Contract No. is  )\\s*(.*)\\s*(|\n)*(?=Khagaul Nagar Parishad)");
				Matcher matcher8 = NewContractNo.matcher(pdfoutput);
				if (matcher8.find())
				{
					String NewContractNo1 = matcher8.group(1).trim();
					System.out.println("Renewal To Date in Approval of Renewal of Advertisement Contract is==>"+NewContractNo1);
					Pdf_RoA_Approval_NewContractNo.add(NewContractNo1);
				} 
				else
				{
					System.out.println("no result");
				}

			} catch (Exception e) {

				e.printStackTrace();
			}
		}


		//ADH Agency Contract Note
		public static void ADH_Agency_Contract_Note_Pdf(String output){

			try {

				Pattern ContractNo = Pattern.compile("(?<=Contract No. )\\s*(.*)\\s*(|\n)*(?= is generated)");		
				Matcher matcher5 = ContractNo.matcher(pdfoutput);
				if (matcher5.find())
				{
					String ContractNo1  = matcher5.group(1).trim();
					System.out.println("Contract No is :"+ContractNo1);
					pdf_ContractNote_ContractNo.add(ContractNo1);
				}
				else
				{
					System.out.println("no result");
				}	

				Pattern ContractgenDate = Pattern.compile("(?<=generated on )\\s*(.*)\\s*(|\n)*(?= Contract duration)");		
				Matcher matcher6 = ContractgenDate.matcher(pdfoutput);
				if (matcher6.find())
				{
					String ContractgenDate1  = matcher6.group(1).trim();
					System.out.println("Contract genrated is :"+ContractgenDate1);
					pdf_ContractNote_ContractgenDate.add(ContractgenDate1);
				}
				else
				{
					System.out.println("no result");
				}	

				Pattern ContractAmt = Pattern.compile("(?<=is Rs. )\\s*(.*)\\s*(|\n)*(?=. Contract)");		
				Matcher matcher7 = ContractAmt.matcher(pdfoutput);
				if (matcher7.find())
				{
					String ContractAmt1  = matcher7.group(1).trim();
					System.out.println("Contract Amount is :"+ContractAmt1);
					pdf_ContractNote_ContractAmt.add(ContractAmt1);
				}
				else
				{
					System.out.println("no result");
				}	

				Pattern ContractFrom = Pattern.compile("(?<=duration is from )\\s*(.*)\\s*(|\n)*(?= to)");		
				Matcher matcher8 = ContractFrom.matcher(pdfoutput);
				if (matcher8.find())
				{
					String ContractFrom1  = matcher8.group(1).trim();
					System.out.println("Contract From is :"+ContractFrom1);
					pdf_ContractNote_ContractFrom.add(ContractFrom1);
				}
				else
				{
					System.out.println("no result");
				}	

				Pattern data=Pattern.compile("(?<=Advertising and Hoarding)\\s*(.*)",Pattern.DOTALL);
				Matcher matcher9=data.matcher(pdfoutput);
				if (matcher9.find()) {
					String data1=matcher9.group(1).trim();
					String [] CollectionDate=data1.split("\\n");
					for(int i=0;i<CollectionDate.length;i++)
					{
						System.out.println(i+"===>"+CollectionDate[i]);
					}
					System.out.println("Applicant Name==>"+CollectionDate[34].trim());
					pdf_ContractNote_ApplicantName.add(CollectionDate[34].trim());
					System.out.println("Applicant Name==>"+pdf_ContractNote_ApplicantName);

					System.out.println("Applicant Addreess==>"+CollectionDate[35].trim());
					pdf_ContractNote_ApplicantAddreess.add(CollectionDate[35].trim());
					System.out.println("Applicant Addreess==>"+pdf_ContractNote_ApplicantAddreess);

					System.out.println("Hoarding Code==>"+CollectionDate[5].trim());
					pdf_ContractNote_HoardingCode.add(CollectionDate[5].trim());
					System.out.println("Hoarding Code==>"+pdf_ContractNote_HoardingCode);

					System.out.println("Hoarding Description==>"+CollectionDate[7].trim());
					pdf_ContractNote_HoardingDescription.add(CollectionDate[7].trim());
					System.out.println("Hoarding Description==>"+pdf_ContractNote_HoardingDescription);
				}


			} catch (Exception e) {

				e.printStackTrace();
			}
		}


		//Adh Contract Bill Payment Cash
		public static void ADH_Cash_ContractBillPayment_Receipt_Letter_Pdf(String output){
			try {
				Pattern data=Pattern.compile("(?<=Received From)\\s*(.*)",Pattern.DOTALL);
				Matcher matcher657=data.matcher(pdfoutput);
				if (matcher657.find()) {
					String data1=matcher657.group(1).trim();
					String [] CollectionDate=data1.split("\\n");
					for(int i=0;i<CollectionDate.length;i++)
					{
						System.out.println(i+"===>"+CollectionDate[i]);
					}

					System.out.println("Receipt No ==>"+CollectionDate[31].trim());
					pdf_ContractBillpayment_rceiptNo.add(CollectionDate[31].trim());
					System.out.println("Receipt Date==>"+CollectionDate[34].trim());
					pdf_ContractBillpayment_rceiptDate.add(CollectionDate[34].trim());
					System.out.println("Reference No===>"+CollectionDate[69].trim());
					pdf_ContractBillpayment_referenceNo.add(CollectionDate[69].trim());
					System.out.println("Received From Applicant Name===>"+CollectionDate[73].trim());
					pdf_ContractBillpayment_receivedFrom.add(CollectionDate[73].trim());
					System.out.println("Total Received Amount===>"+CollectionDate[106].trim());
					pdf_ContractBillpayment_receivedAmt.add(CollectionDate[106].trim());
					System.out.println("Mode Of Payment ==>"+(CollectionDate[17].trim()));
					pdf_ContractBillpayment_modeOfPayment.add(CollectionDate[17].trim());
				}

				Pattern data3=Pattern.compile("(?<=Contract No.)\\s*(.*)\\s*(|\n)*(?=Contract No.)",Pattern.DOTALL);
				Matcher matcher8=data3.matcher(pdfoutput);
				if (matcher8.find()) {
					String data4=matcher8.group(1).trim();
					String [] CollectionDate=data4.split("\\n");
					for(int i=0;i<CollectionDate.length;i++)
					{
						//System.out.println(i+"===>"+CollectionDate[i]);
					}
					System.out.println("Contract No ==>"+CollectionDate[0].trim());
					pdf_ContractBillpayment_contractNo.add(CollectionDate[0].trim());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}



		//
		public static void ADH_SetUp_Advertisment_Approval_Letter_Pdf(String output){
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
					System.out.println("Applicant Name in Setup of Hoarding for Display of Advertisement==>"+CollectionDate[12].trim());
					Pdf_SetUp_Approval_ApplicantName.add(CollectionDate[12].trim());

					System.out.println("Applicant Address in Setup of Hoarding for Display of Advertisement==>"+CollectionDate[13].trim());
					Pdf_SetUp_Approval_ApplicantAddress.add(CollectionDate[13].trim());

					System.out.println("Location in Setup of Hoarding for Display of Advertisement==>"+CollectionDate[16].trim());
					Pdf_SetUp_Approval_Location.add(CollectionDate[16].trim());

				}

				Pattern ContractNo = Pattern.compile("(?<=Contract No. )\\s*(.*)\\s*(|\n)*(?= dated)");
				Matcher matcher9 = ContractNo.matcher(pdfoutput);
				if (matcher9.find())
				{
					String ContractNo1 = matcher9.group(1).trim();
					System.out.println("Contract No in Setup of Hoarding for Display of Advertisement is==>"+ContractNo1);
					Pdf_SetUp_Approval_ContractNo.add(ContractNo1);
				} 
				else
				{
					System.out.println("no result");
				}

				Pattern ContractDate = Pattern.compile("(?<=dated )\\s*(.*)\\s*(|\n)*(?=.Refer)");
				Matcher matcher10 = ContractDate.matcher(pdfoutput);
				if (matcher10.find())
				{
					String ContractDate1 = matcher10.group(1).trim();
					System.out.println("Contract  Date in Setup of Hoarding for Display of Advertisement is==>"+ContractDate1);
					Pdf_SetUp_Approval_ContractDate.add(ContractDate1);
				} 
				else
				{
					System.out.println("no result");
				}


				Pattern ApplicationNo = Pattern.compile("(?<=application no. )\\s*(.*)\\s*(|\n)*(?= for Setup of Hoarding)");
				Matcher matcher3 = ApplicationNo.matcher(pdfoutput);
				if (matcher3.find())
				{
					String ApplicationNo1 = matcher3.group(1).trim();
					System.out.println("Application No in Setup of Hoarding for Display of Advertisementis==>"+ApplicationNo1);
					Pdf_SetUp_Approval_ApplicationNo.add(ApplicationNo1);
				} 
				else
				{
					System.out.println("no result");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}



		// Loi Payment Receipt ::: Cash :::LOI PAYMENT METHOD RECEIPT
		public static void ADH_Cash_LOiPaymentReceipt_Letter_Pdf(String output){
			try {

				Pattern data=Pattern.compile("\\s*(.*)\\s*(|\n)*(?=Bihar Municipal Act 2007)",Pattern.DOTALL);
				Matcher matcher657=data.matcher(pdfoutput);
				if (matcher657.find()) {
					String data1=matcher657.group(1).trim();
					String [] CollectionDate=data1.split("\\n");
					for(int i=0;i<CollectionDate.length;i++)
					{
						System.out.println(i+"===>"+CollectionDate[i]);
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
					System.out.println("Booking No ==>"+CollectionDate[10].trim());
					pdf_Loi_Receipt_BookingNo.add(CollectionDate[10].trim());
					System.out.println("Contract No ==>"+CollectionDate[12].trim());
					pdf_Loi_Receipt_ContractNo.add(CollectionDate[12].trim());
				}


				Pattern LoiNo = Pattern.compile("(?<=LOI No.)\\s*(.*)\\s*(|\n)*(?=LOI Date)");
				Matcher matcher9 = LoiNo.matcher(pdfoutput);
				if (matcher9.find())
				{
					String LoiNo1 = matcher9.group(1).trim();
					System.out.println("LOI No===>"+LoiNo1);
					pdf_Loi_Receipt_LOINo.add(LoiNo1);
				} 
				else
				{
					System.out.println("no result");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		///////////////////////////////////////END piyush pdf patterns for tp & rnl/////////////////////////

		
		
		
		
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@PT@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		
		// PT Transfer New Bill Printing Pdfpattern by Jyoti
		public static void patternNewBillPrintingPdf(String output)
		{
			try
			{{
				//Property No
				Pattern billPrint_PropNo=Pattern.compile("(?<=Old Property No.)\\s*(.*)");
				Matcher matcher42=billPrint_PropNo.matcher(pdfoutput);
				
				
				while (matcher42.find())
				{   

					String billPrint_PropNumber = matcher42.group(1);

					String[] billPrintPropertyNo = billPrint_PropNumber.split(":");

					String billPrint_PropertyNo = billPrintPropertyNo[0];
					System.out.println("Property No on Pdf of Bill Printing is : " +billPrint_PropertyNo);

					billPrint_PropNoList.add(billPrint_PropertyNo);
					System.out.println("Bill Printing Property No is  : => " +billPrint_PropNoList);
								
				}
				//Bill No
				Pattern billPrint_BillNo=Pattern.compile("(?<=Usage Type MIxed:)\\s*(.*)");
				Matcher matcher43=billPrint_BillNo.matcher(pdfoutput);
				if (matcher43.find()) 
				{
					String billPrint_BillNumber=matcher43.group(1);
					System.out.println("Bill No on Pdf of Bill Printing  : =>" +billPrint_BillNumber);
					billPrint_BillNoList.add(billPrint_BillNumber);
					System.out.println("Bill Printing Bill No is  : =>"+billPrint_BillNoList);
				}
				//Bill Date
				Pattern billPrint_BillDate=Pattern.compile("(?<=Usage Type MIxed:)\\s*(.*)",Pattern.DOTALL);
				Matcher matcher44=billPrint_BillDate.matcher(pdfoutput);
				
				

				while (matcher44.find())
				{   

					String billPrint_BillDt = matcher44.group(0);

					String[] billPrintBillDt = billPrint_BillDt.split("\\n");

					String BillPrinting_BillDate = billPrintBillDt[2];
					System.out.println("Bill Date on Pdf of Bill Printing is : " +BillPrinting_BillDate);

					billPrint_BillDateList.add(BillPrinting_BillDate);
					System.out.println("Bill Printing Bill Date is : " +billPrint_BillDateList);

				}
				
				//Usage Type
				Pattern billPrint_UsageType=Pattern.compile("(?<=Usage Type )\\s*(.*)(?=:)");
				Matcher matcher45=billPrint_UsageType.matcher(pdfoutput);
				if (matcher45.find()) 
				{
					billPrint_Usage_Type=matcher45.group(1);
					System.out.println("Usage Type on Pdf of Bill Printing  : =>" +billPrint_Usage_Type);
				billPrint_UsageTypeList.add(billPrint_Usage_Type);
					System.out.println("Bill Printing Usage Type is  : =>"+billPrint_UsageTypeList);
				}
				
				//Ward No
						Pattern billPrint_WardNo=Pattern.compile("(?<=Tax Bill)\\s*(.*)(?=Ward :)");
						Matcher matcher46=billPrint_WardNo.matcher(pdfoutput);
						if (matcher46.find()) 
						{
							String billPrint_WardNumber=matcher46.group(1);
							System.out.println("Ward No on Pdf of Bill Printing  : =>" +billPrint_WardNumber);
							billPrint_WardNoList.add(billPrint_WardNumber);
							System.out.println("Bill Printing Ward No is  : =>"+billPrint_WardNoList);
						}

						// Name
						Pattern billPrint_Name=Pattern.compile("(?<=Name     : )\\s*(.*)");
						Matcher matcher47=billPrint_Name.matcher(pdfoutput);
						if (matcher47.find()) 
						{
							String billPrint_Nm=matcher47.group(1);
							System.out.println("Name on Pdf of Bill Printing  : =>" +billPrint_Nm);
							billPrint_NameList.add(billPrint_Nm);
							System.out.println("Bill Printing Name is  : =>"+billPrint_NameList);
						}
						// Address
						Pattern billPrint_Address=Pattern.compile("(?<=Name     : )\\s*(.*)(?=Address :)",Pattern.DOTALL);
						Matcher matcher48=billPrint_Address.matcher(pdfoutput);
						
						while (matcher48.find())
						{   

							String billPrint_Addr = matcher48.group(0);

							String[] billPrintAddress = billPrint_Addr.split("\\n");

							String BillPrinting_Address = billPrintAddress[1];
							System.out.println("Bill Address on Pdf of Bill Printing is : " +BillPrinting_Address);

							billPrint_AddressList.add(BillPrinting_Address);
							System.out.println("Bill Printing Bill Address is : " +billPrint_AddressList);

						}
						//Bill Due Date
						Pattern billPrint_BillDueDate=Pattern.compile("(?<=Bill Due Date  : )\\s*(.*)");
						Matcher matcher49=billPrint_BillDueDate.matcher(pdfoutput);
						if (matcher49.find()) 
						{
							String billPrint_BillDueDt=matcher49.group(1);
							System.out.println("Bill Due Date on Pdf of Bill Printing  : =>" +billPrint_BillDueDt);
							billPrint_BillDueDateList.add(billPrint_BillDueDt);
							System.out.println("Bill Printing Bill Due Date is  : =>"+billPrint_BillDueDateList);
						}
						
						//Holding Tax Arrears Amount
						Pattern billPrint_HTaxArrAmt=Pattern.compile("(?<=Water Tax)\\s*(.*)");
						Matcher matcher50=billPrint_HTaxArrAmt.matcher(pdfoutput);
						if (matcher50.find()) 
						{
							String billPrint_HoldingTaxArrAmt=matcher50.group(1);
							System.out.println("Holding Tax Arrears Amount on Pdf of Bill Printing  : =>" +billPrint_HoldingTaxArrAmt);
							billPrint_HoldingTaxArrAmtList.add(billPrint_HoldingTaxArrAmt);
							System.out.println("Bill Printing Holding Tax Arrears Amount is  : =>"+billPrint_HoldingTaxArrAmtList);
						}
						
						//Interest on Arrears Bill
						Pattern billPrint_InterestonArrAmount=Pattern.compile("(?<=Water Tax)\\s*(.*)",Pattern.DOTALL);
						Matcher matcher51=billPrint_InterestonArrAmount.matcher(pdfoutput);
						
						
						while (matcher51.find())
						{   
							String interest_onArrears = matcher51.group(0);

							String[] interestonArrears = interest_onArrears.split("\\n");

							String billPrinting_InterestonArrAmount = interestonArrears[2];
							System.out.println("Interest on Arrears Amount on Pdf of Bill Printing is : " +billPrinting_InterestonArrAmount);

							billPrinting_InterestonArrAmountList.add(billPrinting_InterestonArrAmount);
							System.out.println("Bill Printing Interest on Arrears Amount is : " +billPrinting_InterestonArrAmountList);
						}
						
						//Water Tax on Arrears Bill
						Pattern billPrint_WaterTax=Pattern.compile("(?<=Water Tax)\\s*(.*)",Pattern.DOTALL);
						Matcher matcher52=billPrint_WaterTax.matcher(pdfoutput);
						
						
						while (matcher52.find())
						{   
							String water_Tax = matcher52.group(0);

							String[] waterTax = water_Tax.split("\\n");

							String billPrint_WaterTaxAmt = waterTax[3];
							System.out.println("Water Tax on Pdf of Bill Printing is : " +billPrint_WaterTaxAmt);

							billPrint_WaterTaxAmtList.add(billPrint_WaterTaxAmt);
							System.out.println("Water Tax on Arrears Amount is : " +billPrint_WaterTaxAmtList);
						}
						
						//Rain Water Harvesting (RWH) on Arrears Bill
						Pattern billPrint_RWHAmt=Pattern.compile("(?<=Rain Wate Harvesting Rebate Amount )\\s*(.*)",Pattern.DOTALL);
						Matcher matcher53=billPrint_RWHAmt.matcher(pdfoutput);
						while (matcher53.find())
						{   
							String RWH = matcher53.group(0);

							String[] RWHRebate = RWH.split(" ");

							String billPrint_RWHAmount = RWHRebate[0];
							System.out.println("RWH on Pdf of Bill Printing is : " +billPrint_RWHAmount);

							billPrint_RWHAmountList.add(billPrint_RWHAmount);
							System.out.println("RWH on Arrears Amount is : " +billPrint_RWHAmountList);
						}
						
						//Holding Tax  on Current Bill
					
						Pattern billPrint_HTaxCurrAmt=Pattern.compile("(?<=Water Tax)\\s*(.*)",Pattern.DOTALL);
						Matcher matcher54=billPrint_HTaxCurrAmt.matcher(pdfoutput);
						while (matcher54.find())
						{   
							String HTax_onCurrent = matcher54.group(0);

							String[] currentHTax = HTax_onCurrent.split("\\n");

							String billPrinting_CurrHTax = currentHTax[4];
							System.out.println("Holding Tax on Current Amount on Pdf of Bill Printing is : " +billPrinting_CurrHTax);

							billPrint_HTaxCurrAmtList.add(billPrinting_CurrHTax);
							System.out.println("Bill Printing Holding Tax on Current Amount is : " +billPrint_HTaxCurrAmtList);
						}
						
						//Interest on Current Bill
						Pattern billPrint_InterestonCurrAmount=Pattern.compile("(?<=Water Tax)\\s*(.*)",Pattern.DOTALL);
						Matcher matcher55=billPrint_InterestonCurrAmount.matcher(pdfoutput);
						
						
						while (matcher55.find())
						{   
							String interest_onCurr = matcher55.group(0);

							String[] interestonCurrent = interest_onCurr.split("\\n");

							String billPrinting_InterestonCurrAmount = interestonCurrent[5];
							System.out.println("Interest on Current Amount on Pdf of Bill Printing is : " +billPrinting_InterestonCurrAmount);

							billPrinting_InterestonCurrAmountList.add(billPrinting_InterestonCurrAmount);
							System.out.println("Bill Printing Interest on Current Amount is : " +billPrinting_InterestonCurrAmountList);
						}
						
						//Water Tax on Current Bill
						Pattern billPrint_CurrWaterTax=Pattern.compile("(?<=Water Tax)\\s*(.*)",Pattern.DOTALL);
						Matcher matcher56=billPrint_CurrWaterTax.matcher(pdfoutput);
						
						
						while (matcher56.find())
						{   
							String Currwater_Tax = matcher56.group(0);

							String[] CurrwaterTax = Currwater_Tax.split("\\n");

							String billPrint_CurrWaterTaxAmt = CurrwaterTax[6];
							System.out.println("Current Water Tax on Pdf of Bill Printing is : " +billPrint_CurrWaterTaxAmt);

							billPrint_CurrWaterTaxAmtList.add(billPrint_CurrWaterTaxAmt);
							System.out.println("Water Tax on Current Amount is : " +billPrint_CurrWaterTaxAmtList);
						}
						
						
						
						//Rain Water Harvesting (RWH) on Current Bill
						Pattern billPrint_CurrRWHAmt=Pattern.compile("(?<=Rain Wate Harvesting Rebate Amount )\\s*(.*)",Pattern.DOTALL);
						Matcher matcher57=billPrint_CurrRWHAmt.matcher(pdfoutput);
						while (matcher57.find())
						{   
							String CurrRWH = matcher57.group(0);

							String[] CurrRWHRebate = CurrRWH.split(" ");

							String billPrint_CurrRWHAmount = CurrRWHRebate[1];
							System.out.println("Current RWH on Pdf of Bill Printing is : " +billPrint_CurrRWHAmount);

							billPrint_CurrRWHAmountList.add(billPrint_CurrRWHAmount);
							System.out.println("Current RWH on Arrears Amount is : " +billPrint_CurrRWHAmountList);
						}

						//Total Rain Water Harvesting (RWH)
						Pattern billPrint_TotRWHAmt=Pattern.compile("(?<=Rain Wate Harvesting Rebate Amount )\\s*(.*)",Pattern.DOTALL);
						Matcher matcher58=billPrint_TotRWHAmt.matcher(pdfoutput);
						while (matcher58.find())
						{   
							String TotRWH = matcher58.group(0);

							String[] TotRWHRebate = TotRWH.split(" ");

							String billPrint_TotRWHAmount = TotRWHRebate[2];
							System.out.println("Total RWH on Pdf of Bill Printing is : " +billPrint_TotRWHAmount);

							billPrint_TotRWHAmountList.add(billPrint_TotRWHAmount);
							System.out.println("Total RWH on Amount on Bill Print is : " +billPrint_TotRWHAmountList);
						}
						
						//Current Rebate Amount
						Pattern billPrint_CurrRebateAmt=Pattern.compile("(?<=Rebate Amount  )\\s*(.*)",Pattern.DOTALL);
						Matcher matcher59=billPrint_CurrRebateAmt.matcher(pdfoutput);
						while (matcher59.find())
						{   
							String CurrRebate = matcher59.group(0);

							String[] CurrRebateAmt = CurrRebate.split(" ");

							String billPrint_CurrRebate = CurrRebateAmt[0];
							System.out.println("Current Rebate on Pdf of Bill Printing is : " +billPrint_CurrRebate);

							billPrint_CurrRebateAmtList.add(billPrint_CurrRebate);
							System.out.println("Rebate Amount on Current Amount is : " +billPrint_CurrRebateAmtList);
						}
						
						//Total Rebate Amount
						Pattern billPrint_TotRebateAmt=Pattern.compile("(?<=Rebate Amount  )\\s*(.*)",Pattern.DOTALL);
						Matcher matcher60=billPrint_TotRebateAmt.matcher(pdfoutput);
						while (matcher60.find())
						{   
							String TotRebateAmt = matcher60.group(1);

							String[] TotRebate = TotRebateAmt.split(" ");

							String billPrint_TotRebate = TotRebate[0];
							System.out.println("Total Rebate on Pdf of Bill Printing is : " +billPrint_TotRebate);

							billPrint_TotRebateAmtList.add(billPrint_TotRebate);
							System.out.println("Total Rebate Amount on Bill Print is : " +billPrint_TotRebateAmtList);
						}
						
					
						//Total Holding Tax
						
						Pattern billPrint_TotHTaxAmt=Pattern.compile("(?<=Water Tax)\\s*(.*)",Pattern.DOTALL);
						Matcher matcher61=billPrint_TotHTaxAmt.matcher(pdfoutput);
						while (matcher61.find())
						{   
							String TotHTax = matcher61.group(0);

							String[] TotalHTax = TotHTax.split("\\n");

							String billPrinting_TotHTax = TotalHTax[7];
							System.out.println("Total Holding Tax on Pdf of Bill Printing is : " +billPrinting_TotHTax);

							billPrint_TotalHTaxAmtList.add(billPrinting_TotHTax);
							System.out.println("Bill Printing Total Holding Tax is : " +billPrint_TotalHTaxAmtList);
						}
						
						//Total Interest
						Pattern billPrint_TotInterest=Pattern.compile("(?<=Water Tax)\\s*(.*)",Pattern.DOTALL);
						Matcher matcher62=billPrint_TotInterest.matcher(pdfoutput);
						
						
						while (matcher62.find())
						{   
							String TotalInterest = matcher62.group(0);

							String[] totInt = TotalInterest.split("\\n");

							String billPrinting_TotInterestonAmount = totInt[8];
							System.out.println("Total Interest Amount on Pdf of Bill Printing is : " +billPrinting_TotInterestonAmount);

							billPrinting_TotalInterestAmountList.add(billPrinting_TotInterestonAmount);
							System.out.println("Bill Printing Total Interest Amount is : " +billPrinting_TotalInterestAmountList);
						}
						
						//Total Water Tax
						Pattern billPrint_TotWaterTax=Pattern.compile("(?<=Water Tax)\\s*(.*)",Pattern.DOTALL);
						Matcher matcher63=billPrint_TotWaterTax.matcher(pdfoutput);
						
						
						while (matcher63.find())
						{   
							String Totwater_Tax = matcher63.group(0);

							String[] TotwaterTax = Totwater_Tax.split("\\n");

							String billPrint_TotWaterTaxAmt = TotwaterTax[9];
							System.out.println("Total Water Tax on Pdf of Bill Printing is : " +billPrint_TotWaterTaxAmt);

							billPrint_TotWaterTaxAmtList.add(billPrint_TotWaterTaxAmt);
							System.out.println("Total Water Tax Amount is : " +billPrint_TotWaterTaxAmtList);
						}
						
						//Total Payable Amount
						Pattern billPrint_TotPayAmt=Pattern.compile("(?<=Total Payable Amount  )\\s*(.*)");
						Matcher matcher64=billPrint_TotPayAmt.matcher(pdfoutput);
						if (matcher64.find()) 
						{
							String billPrint_TotPayableAmt=matcher64.group(1);
							System.out.println("Total Payable Amount on Pdf of Bill Printing  : =>" +billPrint_TotPayableAmt);
							billPrint_TotPayableAmtList.add(billPrint_TotPayableAmt);
							System.out.println("Bill Printing Total Payable Amount is  : =>"+billPrint_TotPayableAmtList);
						}
						
						//Arrears Total Bill Amount
						Pattern billPrint_ArrTotBillAmt=Pattern.compile("(?<=Total Bill Amount  )\\s*(.*)",Pattern.DOTALL);
						Matcher matcher65=billPrint_ArrTotBillAmt.matcher(pdfoutput);
						while (matcher65.find())
						{   
							String ArrTotBill = matcher65.group(0);

							String[] Arr_TotBill = ArrTotBill.split(" ");

							String billPrint_ArrTotBill = Arr_TotBill[0];
							System.out.println("Arrears Total Bill Amount on Pdf of Bill Printing is : " +billPrint_ArrTotBill);

							billPrint_ArrTotBillList.add(billPrint_ArrTotBill);
							System.out.println("Total bill Amount of Arrears is : " +billPrint_ArrTotBillList);
						}
						
						//Current Total Bill Amount
						Pattern billPrint_CurrTotBillAmt=Pattern.compile("(?<=Total Bill Amount  )\\s*(.*)",Pattern.DOTALL);
						Matcher matcher66=billPrint_CurrTotBillAmt.matcher(pdfoutput);
						while (matcher66.find())
						{   
							String CurrTotBill = matcher66.group(0);

							String[] Curr_TotBill = CurrTotBill.split("  ");

							String billPrint_CurrTotBill = Curr_TotBill[1];
							System.out.println("Current Total Bill Amount on Pdf of Bill Printing is : " +billPrint_CurrTotBill);

							billPrint_CurrTotBillList.add(billPrint_CurrTotBill);
							System.out.println("Total bill Amount of Current is : " +billPrint_CurrTotBillList);
						}
						// Total Bill Amount
						Pattern billPrint_TotBill=Pattern.compile("(?<=Total Bill Amount  )\\s*(.*)");
						Matcher matcher67=billPrint_TotBill.matcher(pdfoutput);
						while (matcher67.find())
						{   
							String TotBill = matcher67.group(0);

							String[] TotalBill = TotBill.split("  ");

							String billPrint_TotBillAmt = TotalBill[2];
							System.out.println("Total Bill Amount on Pdf of Bill Printing is : " +billPrint_TotBillAmt);

							billPrint_TotBillAmtList.add(billPrint_TotBillAmt);
							System.out.println("Total bill Amount is : " +billPrint_TotBillAmtList);
						}
				
			}

				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}


		// PT Hearing Letter Printing Pdfpattern by Jyoti
		public static void patternPTHeraingLetterPrintingPdf(String output)
		{
			try
			{
				Pattern objHLP_OwnerNm=Pattern.compile("(?<=Note : Please inform to ULB if any correction)\\s*(.*)",Pattern.DOTALL);
				Matcher matcher30=objHLP_OwnerNm.matcher(pdfoutput);

				while (matcher30.find())
				{   

					String objHLP_OwnrNm = matcher30.group(0);

					String[] objHLPOwnrNm = objHLP_OwnrNm.split("\\n");

					String Owner_name = objHLPOwnrNm[2];
					System.out.println("Owner name Obj Hearing Letter is : " +Owner_name);

					objHLP_OwnrNmList.add(Owner_name);
					System.out.println("Owner Name List Obj Hearing Letter is : " +objHLP_OwnrNmList);

				}


				Pattern objHLP_OwnerAddr=Pattern.compile("(?<=mailk ka pta)\\s*(.*)");
				Matcher matcher31=objHLP_OwnerAddr.matcher(pdfoutput);
				if (matcher31.find()) 
				{
					String objHLP_OwnerAddress=matcher31.group(1);
					System.out.println("Owner Address on Pdf of Hearing Letter Printing  : =>" +objHLP_OwnerAddress);
					objHLP_OwnerAddressList.add(objHLP_OwnerAddress);
					System.out.println("Owner Address List on Pdf of Hearing Letter Printing  :::"+objHLP_OwnerAddressList);

				}

				Pattern objHLP_PropNo=Pattern.compile("(?<=Property No.)\\s*(.*)(?=   along with)");
				Matcher matcher32=objHLP_PropNo.matcher(pdfoutput);
				if (matcher32.find()) 
				{
					String objHLP_PropNumber=matcher32.group(1);
					System.out.println("Property No on Pdf of Hearing Letter Printing  : =>" +objHLP_PropNumber);
					objHLP_PropNoList.add(objHLP_PropNumber);
					System.out.println("Property No List on Pdf of Hearing Letter Printing  :::"+objHLP_PropNoList);

				}

				Pattern objHLP_ObjNo=Pattern.compile("(?<=Objection No.)\\s*(.*)(?=  Dated)");
				Matcher matcher33=objHLP_ObjNo.matcher(pdfoutput);
				if (matcher33.find()) 
				{
					String objHLP_ObjNumber=matcher33.group(1);
					System.out.println("Objection No on Pdf of Hearing Letter Printing  : =>" +objHLP_ObjNumber);
					objHLP_ObjNoList.add(objHLP_ObjNumber);
					System.out.println("Objection No List on Pdf of Hearing Letter Printing  :::"+objHLP_ObjNoList);

				}
				//// Objection Date
				Pattern objHLP_ObjDt=Pattern.compile("(?<=  Dated)\\s*(.*)");
				Matcher matcher34=objHLP_ObjDt.matcher(pdfoutput);
				if (matcher34.find()) 
				{
					String objHLP_ObjDate=matcher34.group(1);
					System.out.println("Objection Date on Pdf of Hearing Letter Printing  : =>" +objHLP_ObjDate);
					objHLP_ObjDateList.add(objHLP_ObjDate);
					System.out.println("Objection Date List on Pdf of Hearing Letter Printing  :::"+objHLP_ObjDateList);

				}

				////// Hearing Date
				Pattern objHLP_HearingDt1=Pattern.compile("(?<=office on )\\s*(.*)");
				Matcher matcher35=objHLP_HearingDt1.matcher(pdfoutput);
				if (matcher35.find()) 
				{
					objHLP_HearingDate1=matcher35.group(1);
					//System.out.println("Objection Hearing Date2 on Pdf of Hearing Letter Printing  : =>" +objHLP_HearingDate1);


				}

				Pattern objHLP_HearingDt2=Pattern.compile("(?<=office on )\\s*(.*)(?= at)",Pattern.DOTALL);
				Matcher matcher36=objHLP_HearingDt2.matcher(pdfoutput);

				while (matcher36.find())
				{   

					String objHLP_HearingDatet2 = matcher36.group(0);

					String[] objHLPHearingDt2 = objHLP_HearingDatet2.split("\\n");

					Obj_HearingDate2 = objHLPHearingDt2[1];
					//System.out.println("Hearing Date on Obj Hearing Date Letter is : " +Obj_HearingDate2);


				}


				Obj_Hearing_Date = objHLP_HearingDate1 + Obj_HearingDate2;
				System.out.println("Hearing Date on Pdf is :::::"+Obj_Hearing_Date);
				Obj_Hearing_DateList.add(Obj_Hearing_Date);
				System.out.println("Hearing Date List on Obj Hearing Date Letter is :"+Obj_Hearing_DateList);


				Pattern objHLP_ObjRemark=Pattern.compile("(?<=)\\s*(.*)(?=1)");
				Matcher matcher37=objHLP_ObjRemark.matcher(pdfoutput);
				if (matcher37.find()) 
				{
					String objHLP_ObjRemarks=matcher37.group(1);
					System.out.println("Objection Remarks on Pdf of Hearing Letter Printing  : =>" +objHLP_ObjRemarks);
					objHLP_ObjRemarksList.add(objHLP_ObjRemarks);
					System.out.println("Objection Remarks List on Pdf of Hearing Letter Printing  : =>"+objHLP_ObjRemarksList);


				}

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		
		
		// PT Transfer Hearing Order Pdfpattern by Jyoti
		public static void patternHeraingOrderPdf(String output)
		{
			try
			{
				Pattern objHO_PropNo=Pattern.compile("(?<=Property No. )\\s*(.*)");
				Matcher matcher38=objHO_PropNo.matcher(pdfoutput);
				if (matcher38.find()) 
				{
					String objHO_PropertyNo=matcher38.group(1).trim();
					System.out.println("Property No on Pdf of Hearing Order  : =>" +objHO_PropertyNo);
					objHO_PropNoList.add(objHO_PropertyNo);
					System.out.println("Property No List on Pdf of Hearing Order  : =>"+objHO_PropNoList);
				}

				Pattern objHO_OwnerNm=Pattern.compile("(?<=Bihar Municipal Act 2007)\\s*(.*)");
				Matcher matcher39=objHO_OwnerNm.matcher(pdfoutput);
				if (matcher39.find()) 
				{
					String objHO_OwnerName=matcher39.group(1);
					System.out.println("Owner Name on Pdf of Hearing Order  : =>" +objHO_OwnerName);
					objHO_OwnerNameList.add(objHO_OwnerName);
					System.out.println("Owner Name List on Pdf of Hearing Order  : =>"+objHO_OwnerNameList);
				}

				Pattern objHO_ObjLetrNo=Pattern.compile("(?<=Objection Letter No. )\\s*(.*)(?= Dated)");
				Matcher matcher40=objHO_ObjLetrNo.matcher(pdfoutput);
				if (matcher40.find()) 
				{
					String objHO_ObjLetterNo=matcher40.group(1);
					System.out.println("Objection Letter No on Pdf of Hearing Order  : =>" +objHO_ObjLetterNo);
					objHO_ObjLetterNoList.add(objHO_ObjLetterNo);
					System.out.println("Objection Letter No List on Pdf of Hearing Order  : =>"+objHO_ObjLetterNoList);
				}

				Pattern objHO_ObjLetrDt=Pattern.compile("(?<=Dated )\\s*(.*)");
				Matcher matcher41=objHO_ObjLetrDt.matcher(pdfoutput);
				if (matcher41.find()) 
				{
					String objHO_ObjLetrDate=matcher41.group(1);
					System.out.println("Objection Letter Date on Pdf of Hearing Order  : =>" +objHO_ObjLetrDate);
					objHO_ObjLetrDateList.add(objHO_ObjLetrDate);
					System.out.println("Objection Letter Date List on Pdf of Hearing Order  : =>"+objHO_ObjLetrDateList);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		
		public static void patternSUGAMFORMPdf(String output)
		{
			try
			{/*
			
				/////////Commented Code on Dated 18-04-2017 Because not able to Assert the value but able to do the assertion for Net payable so updated at below.
				Pattern assYr = Pattern.compile("(?<=Transaction Number / ID :)\\s*(.*)\\s*(|\n)",Pattern.DOTALL);
				Matcher matcher1 = assYr.matcher(output);
				Pattern ownr = Pattern.compile("(?<=4\\. Name / Name of Company / Organization 5\\. Fathers’/Husbands’ Name /PAN Gender \\(M/F\\))\\s*(.*)");
				Matcher matcher2 = ownr.matcher(output);
				Pattern netTax = Pattern.compile("(?<=Net Tax Payable \\(Rs\\.\\)    :)\\s*(.*)");
				Matcher matcher3 = netTax.matcher(output);
				Pattern paymntMode = Pattern.compile("(?<=Mode of Payment :)\\s*(.*)");
				Matcher matcher4 = paymntMode.matcher(output);
				Pattern chqDDNo = Pattern.compile("(?<=Cheque / DD number :)\\s*(.*)");
				Matcher matcher5 = chqDDNo.matcher(output);
				Pattern drawnOn = Pattern.compile("(?<=Drawn On :)*$");
				Matcher matcher6 = drawnOn.matcher(output);
				Pattern payablAt = Pattern.compile("(?<=Payable at :)\\s*(.*)\\s*(?=Drawn On :)");
				Matcher matcher7 = payablAt.matcher(output);
				Pattern servChrg = Pattern.compile("(?<=Service Charge\\(Rs\\.\\) :)\\s*(.*)\\s*(?=\\(In Word :  \\))");
				Matcher matcher8 = servChrg.matcher(output);

				if (matcher1.find())
				{
					String assYear = matcher1.group(0);
					String string[]=assYear.split("\\r?\\n");
					String strRatableAreaNARV[] = string[4].split("\\s+");
					
					System.out.println("Rateable Area(Sq.Ft) is : " + strRatableAreaNARV[0]);
					//System.out.println("Annual Rental Value (Rs.) is : " + strRatableAreaNARV[1]);
					
					noChngInAsmntAssessmentYear=string[1];
					noChngInAsmntPropertyNumber=string[2];
					
					noChngInAsmntRateableArea=strRatableAreaNARV[0];
					
					if(strRatableAreaNARV.length > 1)
					{
					noChngInAsmntAnnualRentalValue=strRatableAreaNARV[1];
					}
					
					//noChngInAsmntTaxableVacantLandArea=string[9];
					noChngInAsmntPropertyTax=string[8];
					noChngInAsmntTotalRebate=string[7];
					noChngInAsmntInterest=string[9];
					
					System.out.println("Transaction Number / ID is : " + string[0]);
					System.out.println("Assessment Year is : " + string[1]);
					
					//Old Code commented by Ritesh on Dated: - 12-04-2017 because add new paragraph in SUGAM Report
					System.out.println("Property Id is : " + string[3]);
					System.out.println("Rateable Area(Sq.Ft) is : " + string[8]);
					System.out.println("Annual Rental Value (Rs.) is : " + string[10]);
					System.out.println("Taxable Vacant Land Area (Sq. Ft.) is : " + string[9]);
					System.out.println("Vacant land tax (Rs.) is : " + string[10]);
					System.out.println("Property Tax Rate (%) is : " + string[11]);
					System.out.println("Property Tax is : " + string[14]);
					System.out.println("Total Rebate (Rs.) : " + string[13]);
					System.out.println("Interest (Rs.) : " + string[15]);
					
					//New Code
					System.out.println("Property Id is : " + string[2]);
					System.out.println("Rateable Area(Sq.Ft) is : " + string[4]);
					
					System.out.println("Rateable Area(Sq.Ft) is : " + strRatableAreaNARV[0]);
					System.out.println("Annual Rental Value (Rs.) is : " + strRatableAreaNARV[1]);
					
					noChngInAsmntRateableArea=strRatableAreaNARV[0];
					noChngInAsmntAnnualRentalValue=strRatableAreaNARV[1];
					
					System.out.println("Taxable Vacant Land Area (Sq. Ft.) is : " + string[8]); //Need to log defect
					
					//Only read the value
					System.out.println("Vacant land tax (Rs.) is : " + string[5]);
					System.out.println("Property Tax Rate (%) is : " + string[6]);
					System.out.println("Property Tax is : " + string[8]);
					System.out.println("Total Rebate (Rs.) : " + string[7]);
					System.out.println("Interest (Rs.) : " + string[9]);
				}
				
				if (matcher2.find())
				{
					String owner = matcher2.group(1);
					if(owner.contains("Female"))
					{
						String string=owner.replace("Female", "");
						System.out.println("Owner is : " + string);
					}
					else if(owner.contains("Male"))
					{
						String string=owner.replace("Male", "");
						System.out.println("Owner is : " + string);
					}
				}

				if (matcher3.find())
				{
					noChngInAsmntNetTaxPayable = matcher3.group(0);
					System.out.println("Net Tax Payable (Rs.) is : " + noChngInAsmntNetTaxPayable);
				}

				if (matcher4.find())
				{
					String paymentMode = matcher4.group(0);
					System.out.println("Mode of Payment : " + paymentMode);
				}

				if (matcher5.find())
				{
					String chqDDNum = matcher5.group(0);
					System.out.println("Cheque / DD number : " + chqDDNum);
				}

				if (matcher6.find())
				{
					String drawnBank = matcher6.group(0);
					System.out.println("Drawn On : " + drawnBank);
				}

				if (matcher7.find())
				{
					String payableAt = matcher7.group(0);
					System.out.println("Payable at : " + payableAt);
				}
				if (matcher8.find())
				{
					noChngInAsmntServiceCharge = matcher8.group(0);
					System.out.println("Service Charge(Rs.) : " + noChngInAsmntServiceCharge);
				}
				else
				{
					System.out.println("Regex failed");
				}
			*/
				
				//New Code 18-04-2017 @ Ritesh 
				
				Pattern netTax = Pattern.compile("(?<=Net Tax Payable \\(Rs\\.\\)    :)\\s*(.*)");
				Matcher matcher3 = netTax.matcher(output);
				
				if (matcher3.find())
				{
					noChngInAsmntNetTaxPayable = matcher3.group(0);
					System.out.println("Net Tax Payable (Rs.) is : " + noChngInAsmntNetTaxPayable);
				}		
				else
				{
					System.out.println("Regex failed");
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		
		public static void patternULBChallanReceiptPdfPT(String output)
		{
			try
			{
				int flag=0;
				Pattern amount = Pattern.compile("\\s*(.+?)\\s*(?=Total Received Amt.)");
				Pattern ptAppNo = Pattern.compile("(?<=Applicant ID - )\\s*(.*)\\s*(|\n)*(?= / Property No.)");
				Pattern ptPropNo = Pattern.compile("(?<=Property No.-)\\s*(.*)\\s*(|\n)");

				Matcher matcher1 = amount.matcher(output);
				Matcher matcher2 = ptAppNo.matcher(output);
				Matcher matcher3 = ptPropNo.matcher(output);

				if (matcher1.find())
				{
					ptRcptamt = matcher1.group(1);
					System.out.println("Amount printed in Receipt is : " + ptRcptamt);
				}
				if (matcher2.find())
				{
					ptRcptAppNo = matcher2.group(1);
					System.out.println("Application number printed in Receipt is : " + ptRcptAppNo);
				}
				if (matcher3.find())
				{
					ptRcptPropNo = matcher3.group(1);
					System.out.println("Property number printed in Receipt is : " + ptRcptPropNo);
				}

				else
				{
					System.out.println("Regex failed");
				}

			}

			catch(Exception e)
			{
				throw new MainetCustomExceptions("Error in patternULBChallanReceiptPdfPT method");
			}
		}

		
		public static void patternPropAssessmentCert(String output)
		{
			try{
				Pattern certNo = Pattern.compile("(?<=Carpet Area)\\s*(.*)\\s*(|\n)");
				Pattern ptAppNo = Pattern.compile("(?<=Referenc 1. Your Application No. )\\s*(.*)\\s*(|\n)*(?= dated)");
				Pattern ptPropNo = Pattern.compile("(?<=This is to certify that the details of the premises, assessed under Property No. )\\s*(.*)\\s*(|\n)*(?= are as follow.)");
				//	Pattern ptPropAssmtDtls = Pattern.compile("(?<=6.)\\s*(.*)\\s*(|\n)*(?=This Certificate is issued at the request)");
				Pattern ptPropAssmtDtls = Pattern.compile("(?<=Carpet Area)\\s*(.*)",Pattern.DOTALL);

				Matcher matcher1 = certNo.matcher(output);
				Matcher matcher2 = ptAppNo.matcher(output);
				Matcher matcher3 = ptPropNo.matcher(output);
				Matcher matcher4 = ptPropAssmtDtls.matcher(output);

				if (matcher1.find())
				{
					String propAssmtCertNo = matcher1.group(1);
					System.out.println("Property Assessment Certificate is : " + propAssmtCertNo);
				}
				if (matcher2.find())
				{
					String propAssmtCertAppNo = matcher2.group(1);
					System.out.println("Application number printed is : " + propAssmtCertAppNo);
				}
				if (matcher3.find())
				{
					String propAssmtCertPropNo = matcher3.group(1);
					System.out.println("Property number printed is : " + propAssmtCertPropNo);
				}
				if (matcher4.find())
				{
					String ptPropAssmntDtls = matcher4.group(0);
					String[] ptPropAsmntDtls = ptPropAssmntDtls.split("\\r?\\n");
					System.out.println("Property Owner : " + ptPropAsmntDtls[10]);
					System.out.println("Property Type : " + ptPropAsmntDtls[11]);
					System.out.println("Usage Type : " + ptPropAsmntDtls[12]);
					System.out.println("ALV : " + ptPropAsmntDtls[13]);
					System.out.println("Construction Class : " + ptPropAsmntDtls[14]);
					System.out.println("Carpet Area : " + ptPropAsmntDtls[15]);
					//System.out.println("Property assessment details : " + ptPropAssmntDtls);
				}

				else
				{
					System.out.println("Regex failed");
				}

			}
			catch(Exception e)
			{
				throw new MainetCustomExceptions("Error in patternPropAssessmentCert method");
			}
		}

	

		//// Jyoti
		// PT Transfer Heredity Pdfpattern by Jyoti

		public static void pt_TransferHeredityPdf(String output)
		{
			try
			{
				Pattern NewOwnerName=Pattern.compile("(?<=TO WHOMSOEVER IT MAY CONCERN)\\s*(.*)(?=has applied for)");
				Matcher matcher1=NewOwnerName.matcher(pdfoutput);
				if (matcher1.find()) 
				{
					String New_OwnerName=matcher1.group(1).trim();
					System.out.println("NM New Owner Name is : =>" +New_OwnerName);

					New_OwnerList.add(New_OwnerName);
					System.out.println("NM New Owner List on Pdf is == "+New_OwnerList);
				}

				Pattern OldOwnerName=Pattern.compile("(?<=from)\\s*(.*)(?=in)");
				Matcher matcher2=OldOwnerName.matcher(pdfoutput);
				if (matcher2.find()) 
				{
					String Old_OwnerNmae=matcher2.group(1).trim();
					System.out.println("NM Pdf Old Owner Name is : =>" +Old_OwnerNmae);

					Old_OwnerList.add(Old_OwnerNmae);
					System.out.println("NM Old Owner List on Pdf is == "+Old_OwnerList);

				}

				Pattern PropNo=Pattern.compile("(?<=Property No. )\\s*(.*)(?= having)");
				Matcher matcher3=PropNo.matcher(pdfoutput);
				if (matcher3.find()) 
				{
					String Prop_NoNM=matcher3.group(1);
					System.out.println("NM Pdf Property No is : =>" +Prop_NoNM);

					Prop_NoListPdf.add(Prop_NoNM);
					System.out.println("NM Property No List on Pdf is == "+Prop_NoListPdf);

				}

				Pattern Ward=Pattern.compile("(?<=Ward :)\\s*(.*)");
				Matcher matcher4=Ward.matcher(pdfoutput);
				if (matcher4.find()) 
				{
					String Ward_No=matcher4.group(1).trim();
					System.out.println("NM Pdf Ward is : =>" +Ward_No);

					Ward_NoList.add(Ward_No);
					System.out.println("NM Ward No List on Pdf is == "+Ward_NoList);

				}

				Pattern NoticeAppDate=Pattern.compile("(?<=dated )\\s*(.*)");
				Matcher matcher5=NoticeAppDate.matcher(pdfoutput);
				if (matcher5.find()) 
				{
					String NoticeApp_Date=matcher5.group(1);
					System.out.println("NM Pdf Application Date is : =>" +NoticeApp_Date);

				}

			}


			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		// PT Transfer Other Mode  Pdfpattern by Jyoti
		public static void pt_TransferOtherModePdf(String output)
		{
			try
			{
				Pattern NewOwnerName=Pattern.compile("(?<=TO WHOMSOEVER IT MAY CONCERN)\\s*(.*)(?=has applied for)");
				Matcher matcher6=NewOwnerName.matcher(pdfoutput);
				if (matcher6.find()) 
				{
					String New_OMOwnerNmae=matcher6.group(1).trim();
					System.out.println("OM Pdf New Owner Name is : =>" +New_OMOwnerNmae);

					New_OMOwnerList.add(New_OMOwnerNmae);
					System.out.println("OM New Owner List on Pdf is == "+New_OMOwnerList);

				}

				Pattern OldOwnerName=Pattern.compile("(?<=from)\\s*(.*)(?=in)");
				Matcher matcher7=OldOwnerName.matcher(pdfoutput);
				if (matcher7.find()) 
				{
					String Old_OMOwnerNmae=matcher7.group(1).trim();
					System.out.println("OM Pdf Old Owner Name is : =>" +Old_OMOwnerNmae);


					Old_OMOwnerList.add(Old_OMOwnerNmae);
					System.out.println("OM Old Owner List on Pdf is == "+Old_OMOwnerList);

				}

				Pattern Ward=Pattern.compile("(?<=Ward :)\\s*(.*)");
				Matcher matcher8=Ward.matcher(pdfoutput);
				if (matcher8.find()) 
				{
					String Ward_NoOM=matcher8.group(1).trim();
					System.out.println("OM Pdf Ward is : =>" +Ward_NoOM);

					Ward_NoListOM.add(Ward_NoOM);
					System.out.println("OM Ward No List on Pdf is == "+Ward_NoListOM);

				}

				Pattern PropNo=Pattern.compile("(?<=Property No. )\\s*(.*)(?= having)");
				Matcher matcher9=PropNo.matcher(pdfoutput);
				if (matcher9.find()) 
				{
					String Prop_NoOM=matcher9.group(1);
					System.out.println("OM Pdf Property No is : =>" +Prop_NoOM);

					Prop_NoListOMPdf.add(Prop_NoOM);
					System.out.println("OM Property No List on Pdf is == "+Prop_NoListOMPdf);

				}

				Pattern NoticeAppDate=Pattern.compile("(?<=dated )\\s*(.*)");
				Matcher matcher10=NoticeAppDate.matcher(pdfoutput);
				if (matcher10.find()) 
				{
					String NoticeApp_Date=matcher10.group(1);
					System.out.println("OM Pdf Application Date is : =>" +NoticeApp_Date);

				}
			}


			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		// PT Transfer Fee Intimation Letter Pdfpattern by Jyoti

		public static void pt_TransferFeeIntiLetterPdf(String output)
		{
			try
			{
				Pattern IntiLetter_OwnerName=Pattern.compile("(?<=Khagaul Nagar Parishad)\\s*(.*)");
				Matcher matcher11=IntiLetter_OwnerName.matcher(pdfoutput);
				if (matcher11.find()) 
				{
					String IntiLetr_OwnerName=matcher11.group(1).trim();
					System.out.println("Pdf Intimation Letter Owner Name is : =>" +IntiLetr_OwnerName);

					OwnerName_IntiLetterList.add(IntiLetr_OwnerName);
					System.out.println("Pdf Intimation Letter Owner Name List is : =>"+OwnerName_IntiLetterList);

				}

				Pattern IntiLetter_Ward=Pattern.compile("(?<= Ward:   )\\s*(.*)");
				Matcher matcher12=IntiLetter_Ward.matcher(pdfoutput);
				if (matcher12.find()) 
				{
					String IntiLetter_WardName=matcher12.group(1);
					System.out.println("Pdf Intimation Letter Ward Name is : =>" +IntiLetter_WardName);

				}

				Pattern IntiLetter_PropNo=Pattern.compile("(?<=2. Property Number: )\\s*(.*)");
				Matcher matcher13=IntiLetter_PropNo.matcher(pdfoutput);
				if (matcher13.find()) 
				{
					String IntiLetter_Prop_No=matcher13.group(1).trim();
					System.out.println("Pdf Intimation Letter Property Number is : =>" +IntiLetter_Prop_No);

					IntiLetter_Prop_NoList.add(IntiLetter_Prop_No);
					System.out.println("Pdf Intimation Letter Property Number List is : =>" +IntiLetter_Prop_NoList);

				}

				Pattern IntiLetter_AppNo=Pattern.compile("(?<=1. Application No.  )\\s*(.*)(?= dated)");
				Matcher matcher14=IntiLetter_AppNo.matcher(pdfoutput);
				if (matcher14.find()) 
				{
					String IntiLetter_App_No=matcher14.group(1);
					System.out.println("Pdf Intimation Letter Application Number is : =>" +IntiLetter_App_No);

					IntiLetter_App_NoList.add(IntiLetter_App_No);
					System.out.println("Pdf Intimation Letter Application Number List is : =>" +IntiLetter_App_NoList);
				}

				Pattern IntiLetter_TransferCharges=Pattern.compile("(?<=TRANSFER CHARGES  )\\s*(.*)");
				Matcher matcher15=IntiLetter_TransferCharges.matcher(pdfoutput);
				if (matcher15.find()) 
				{
					String IntiLetter_Transfer_Fees=matcher15.group(1);
					System.out.println("Pdf Intimation Letter Transfer Charges : =>" +IntiLetter_Transfer_Fees);

					TransferFee_IntiLetter.add(IntiLetter_Transfer_Fees);
					System.out.println("Pdf Transfer Fee is:::"+TransferFee_IntiLetter);

				}

				Pattern IntiLetter_TotalCharges=Pattern.compile("(?<=Total Payable Amount  )\\s*(.*)");
				Matcher matcher16=IntiLetter_TotalCharges.matcher(pdfoutput);
				if (matcher16.find()) 
				{
					String IntiLetter_Total_Charges=matcher16.group(1);
					System.out.println("Pdf Intimation Letter Total Charges : =>" +IntiLetter_Total_Charges);

				}

				Pattern IntiLetterDate=Pattern.compile("(?<=Date      : )\\s*(.*)");
				Matcher matcher17=IntiLetterDate.matcher(pdfoutput);
				if (matcher17.find()) 
				{
					String IntiLetter_Date=matcher17.group(1);
					System.out.println("Pdf Intimation Letter Date : =>" +IntiLetter_Date);

				}
			}


			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		// PT Transfer Certificate Pdfpattern by Jyoti
		public static void pt_TransferCertificatePdf(String output)
		{
			try
			{
				Pattern TrfCerti_AppNo=Pattern.compile("(?<=application No. )\\s*(.*)(?=,    Dated)");
				Matcher matcher18=TrfCerti_AppNo.matcher(pdfoutput);
				if (matcher18.find()) 
				{
					String TransferCertificate_App_No=matcher18.group(1);
					System.out.println("Pdf Transfer Certificate Application Number is : =>" +TransferCertificate_App_No);

					TrfCerti_App_NoList.add(TransferCertificate_App_No);
					System.out.println("Pdf Transfer Certificate Application Number List is : =>" +TrfCerti_App_NoList);

				}
				Pattern TrfCerti_PropNo=Pattern.compile("(?<=Property No. )\\s*(.*)");
				Matcher matcher19=TrfCerti_PropNo.matcher(pdfoutput);
				if (matcher19.find()) 
				{
					String TransferCertificate_Prop_No=matcher19.group(1).trim();
					System.out.println("Pdf Transfer Certificate Property Number is : =>" +TransferCertificate_Prop_No);

					TrfCerti_Prop_NoList.add(TransferCertificate_Prop_No);
					System.out.println("Pdf Transfer Certificate Property Number List is : =>" +TrfCerti_Prop_NoList);

				}

				Pattern TrfCerti_NewOwnerName=Pattern.compile("(?<=name of  )\\s*(.*)(?=   has)");
				Matcher matcher20=TrfCerti_NewOwnerName.matcher(pdfoutput);
				if (matcher20.find()) 
				{
					String TransferCertificate_NewOwnerName=matcher20.group(1);
					System.out.println("Pdf Transfer Certificate New Owner Name is : =>" +TransferCertificate_NewOwnerName);

					TrfCerti_New_OwnerList.add(TransferCertificate_NewOwnerName);
					System.out.println("Pdf Transfer Certificate New Owner List is : =>" +TrfCerti_NewOwnerList);


				}

				Pattern TrfCerti_OldOwnerName=Pattern.compile("(?<=name of  )\\s*(.*)(?=  ,     for)");
				Matcher matcher21=TrfCerti_OldOwnerName.matcher(pdfoutput);
				if (matcher21.find()) 
				{
					String TransferCertificate_OldOwnerName=matcher21.group(1);
					System.out.println("Pdf Transfer Certificate Old Owner Name is : =>" +TransferCertificate_OldOwnerName);

					TrfCerti_Old_OwnerList.add(TransferCertificate_OldOwnerName);
					System.out.println("Pdf Transfer Certificate Old Owner List is : =>" +TrfCerti_Old_OwnerList);



				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		
		
		// by piyush
		public static void patternChallanRegenerationPdf(String output)
		{
			try
			{

				Pattern Challan_RegenPropNo=Pattern.compile("(?<=PID :- )\\s*(.*)");
				Matcher matcher22=Challan_RegenPropNo.matcher(pdfoutput);
				if (matcher22.find()) 
				{
					String Challan_RegenPropNoList=matcher22.group(1);
					System.out.println("Pdf Challan Regeneration Property No List is @Receipt: =>" +Challan_RegenPropNoList);

					ChallanReneration_PropNoList.add(Challan_RegenPropNoList);
					System.out.println("PDF Challan Regeneration List of Property No @Receipt:::"+ChallanReneration_PropNoList);

				}

				Pattern Challan_RegenOwnrNm=Pattern.compile("(?<=,   )\\s*(.*)(?=, Property No.)");
				Matcher matcher23=Challan_RegenOwnrNm.matcher(pdfoutput);
				if (matcher23.find()) 
				{
					String Challan_RegenOwnerNmList=matcher23.group(1).trim();
					System.out.println("Pdf Challan Regeneration Owner Name List is at Receipt : =>" +Challan_RegenOwnerNmList);
					Challan_Regen_OwnerName_List.add(Challan_RegenOwnerNmList);
					System.out.println("PDF Challan Regeneration List of Owner Name at Receipt :::"+Challan_Regen_OwnerName_List);

				}

				Pattern Challan_RegenReceiptAmt=Pattern.compile("(?<=Total Received Amt. :  )\\s*(.*)");
				Matcher matcher24=Challan_RegenReceiptAmt.matcher(pdfoutput);
				if (matcher24.find()) 
				{
					String Challan_RegenRecptAmtList=matcher24.group(1);
					System.out.println("Pdf Challan Regeneration Total Received Amount List is at Receipt : =>" +Challan_RegenRecptAmtList);
					Challan_Regen_ReceiptAmountList.add(Challan_RegenRecptAmtList);
					System.out.println("PDF Challan Regeneration List of Total Received Amount at Receipt :::"+Challan_Regen_ReceiptAmountList);

				}

				Pattern ChlnRegenReceipt_PayMode=Pattern.compile("(?<=Cheque Date)\\s*(.*)(|\n)(?=  )");
				Matcher matcher25=ChlnRegenReceipt_PayMode.matcher(pdfoutput);
				if (matcher25.find()) 
				{
					String ChlnRegenRecpt_PaymentMode=matcher25.group(1);
					System.out.println("Pdf Challan Regeneration Payment Mode List is at Receipt : =>" +ChlnRegenRecpt_PaymentMode);
					Challan_Regen_ReceiptPayModeList.add(ChlnRegenRecpt_PaymentMode);
					System.out.println("PDF Challan Regeneration List of Payment Mode at Receipt :::"+Challan_Regen_ReceiptPayModeList);

				}

				Pattern ChlnRegenReceipt_PayableAmt=Pattern.compile("(?<=Total Received Amt. :  )\\s*(.*)");
				Matcher matcher26=ChlnRegenReceipt_PayableAmt.matcher(pdfoutput);
				if (matcher26.find()) 
				{
					String ChlnRegenRecpt_PayableAmt=matcher26.group(1);
					System.out.println("Pdf Challan Regeneration Payable Amount List is at Receipt : =>" +ChlnRegenRecpt_PayableAmt);
					Challan_Regen_ReceiptPayableAmountList.add(ChlnRegenRecpt_PayableAmt);
					System.out.println("PDF Challan Regeneration List of Payable Amount at Receipt :::"+Challan_Regen_ReceiptPayableAmountList);

				}
				Pattern ChlnRegenReceipt_CurrentYrNetPayableAmt=Pattern.compile("(?<=Total Amount  )\\s*(.*)");
				Matcher matcher27=ChlnRegenReceipt_CurrentYrNetPayableAmt.matcher(pdfoutput);
				if (matcher27.find()) 
				{
					String ChlnRegenRecpt_CurrYrNetPayableAmt=matcher27.group(1);
					System.out.println("Pdf Challan Regeneration Current Year Net Payable Amount List is at Receipt : =>" +ChlnRegenRecpt_CurrYrNetPayableAmt);
					Challan_Regen_ReceiptCurrentYearPayableAmountList.add(ChlnRegenRecpt_CurrYrNetPayableAmt);
					System.out.println("PDF Challan Regeneration List of Current Year Payable Amount at Receipt :::"+Challan_Regen_ReceiptCurrentYearPayableAmountList);

				}

				Pattern ChlnRegenReceipt_DiscountAmt=Pattern.compile("(?<=Rebate Amt :  )\\s*(.*)");
				Matcher matcher28=ChlnRegenReceipt_DiscountAmt.matcher(pdfoutput);
				if (matcher28.find()) 
				{
					ChlnRegenRecpt_DiscountAmt=matcher28.group(1);
					System.out.println("Pdf Challan Regeneration Discount Amount List is at Receipt : =>" +ChlnRegenRecpt_DiscountAmt);
					Challan_Regen_ReceiptDiscountAmountList.add(ChlnRegenRecpt_DiscountAmt);
					System.out.println("PDF Challan Regeneration List of Discount Amount at Receipt :::"+Challan_Regen_ReceiptDiscountAmountList);

				}

				Pattern ChlnRegenReceipt_RainWaterDiscountAmt=Pattern.compile("(?<=Rain Water Harvesting Rebate. :  )\\s*(.*)");
				Matcher matcher29=ChlnRegenReceipt_RainWaterDiscountAmt.matcher(pdfoutput);
				if (matcher29.find()) 
				{
					ChlnRegenRecpt_RainWaterDiscountAmt=matcher29.group(1);
					System.out.println("Pdf Challan Regeneration Rain Water Harvesting Discount Amount List is at Receipt : =>" +ChlnRegenRecpt_RainWaterDiscountAmt);
					Challan_Regen_ReceiptRainWaterHarvestingDiscountAmountList.add(ChlnRegenRecpt_RainWaterDiscountAmt);
					System.out.println("PDF Challan Regeneration List of Rain Water Harvesting Discount Amount at Receipt :::"+Challan_Regen_ReceiptRainWaterHarvestingDiscountAmountList);

				}

				// s1 = new Integer(TotalRebate).toString();
				//Rbt= Integer.toString(TotalRebate);

				//int TotalRebate = Integer.parseInt(s1);

				s1=TotalRebate.toString();

				s1 = ChlnRegenRecpt_DiscountAmt + ChlnRegenRecpt_RainWaterDiscountAmt;
				System.out.println("Total Rebate (Normal + RWH) @ Receipt is ::: "+s1);

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		
		
		
		
		
		
		
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@end@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		

}






