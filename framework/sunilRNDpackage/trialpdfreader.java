 package sunilRNDpackage;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.htmlunit.corejs.javascript.EcmaError;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


public class trialpdfreader {

	public static void main(String[] args) throws AWTException, InterruptedException, IOException ,IndexOutOfBoundsException,ArrayIndexOutOfBoundsException{
		// TODO Auto-generated method stub

		WebDriver driver; 
		
		trialpdfreader tpr = new trialpdfreader();
		
				
/*		final FirefoxProfile firefoxProfile = new FirefoxProfile();
		firefoxProfile.setPreference("xpinstall.signatures.required", false);
		driver = new FirefoxDriver(firefoxProfile);
*/		
	
		System.setProperty("webdriver.chrome.driver", "D:\\AutomationFramework\\ABMSmartScript\\functional\\config\\chromedriver_2.28.exe");

		ChromeOptions options = new ChromeOptions();
		// options.addArguments("-incognito");
		options.addArguments("--disable-popup-blocking");
		options.addArguments("--disable-extensions");

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);

		capabilities.setCapability(ChromeOptions.CAPABILITY, options);

		driver = new ChromeDriver(capabilities);
		
		
		
		//driver.get("http://182.18.168.246:8080/BIHAR_PROJ");
		
		driver.manage().window().maximize();
		
	//	String urlll = "D:\\199_1472188377350.pdf";
	//	String urlll = "D:\\APIRejectionLetter.pdf";
		//String urlll = "D:\\RTIStatusRejectionLetter.pdf";
		//String urlll =	"D:\\RTIforwardLetter.pdf";
	//	String urlll =	"D:\\piorejected.pdf";
	String urlll =	"D:\\Test_Artefacts\\RTI_15-06-2017 13.27\\rti_Response\\Documents\\c.pdf";
	
		setClipboardDataPDF(urlll);
		
				
		Robot robot = new Robot();

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_L);
		// CTRL+L is now pressed (You should now move to the browser address bar.)
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
		
		Thread.sleep(2000);
		
		URL ur = new URL(driver.getCurrentUrl());
		System.out.println(ur);
		
		
		BufferedInputStream fileToParse = new BufferedInputStream(
		ur.openStream());

		PDFParser parser = new PDFParser(fileToParse);
		parser.parse();

		String pdfoutput;
		pdfoutput = new PDFTextStripper().getText(parser.getPDDocument());
		System.out.println(pdfoutput);

	

		////////////////////////////////////////


//Pattern chalappno = Pattern.compile("(?<=Application No./LOI No. :)\\s*([0-9]+)\\s*(|\n)");

/*	Pattern appchalno = Pattern.compile("(?<=Chall`an No. :)\\s*([0-9]+)\\s*(|\n)");

	Pattern appcntname = Pattern.compile("(?<=Applicant Name :)\\s*(.+?)\\s*(?=Applicant Name :)");

	//Pattern chalnamt = Pattern.compile("(?<=Amount Payable In Words :)\\s*([0-9]+)\\s*(|\n)");

	Pattern chalnamt = Pattern.compile("(?<=Amount Payable In Words :)\\s*([0-9]+(\\.[0-9][0-9]?)?)\\s*(|\n)");
*/
	//Matcher matcher = chalappno.matcher(pdfoutput);
//	Matcher matcher1 = appchalno.matcher(output);
//	Matcher matcher2 = appcntname.matcher(output);
//	Matcher matcher3 = chalnamt.matcher(output);

		Pattern challanno=Pattern.compile("(?<=Challan No. : )\\s*([0-9]+)\\s*(|\n)");
		Matcher matcher111=challanno.matcher(pdfoutput);
	
	if (matcher111.find()) {

		
		String challannoa=matcher111.group(1);
		System.out.println("challannoa=>"+challannoa);
		}
		else
		{
			System.out.println("Application Number Does Not Match");
		}	

		
		
		
		
	}

		
		

	
	public static void setClipboardDataPDF(String string) {
		//StringSelection is a class that can be used for copy and paste operations.
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}
	
	
}
