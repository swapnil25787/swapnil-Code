package errorhandling;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.SkipException;

import api.CommonUtilsAPI;


public class MainetCustomExceptions extends RuntimeException {

	CommonUtilsAPI cmon = new CommonUtilsAPI();
	public static StringBuilder result;

	public MainetCustomExceptions(String message){

		System.err.println("in mainet custom exceptions");

		cmon.mReadFromXML("D:/AutomationFramework/ABMSmartScript/functional/config/config.xml");

		cmon.lineNO =  Thread.currentThread().getStackTrace()[2].getLineNumber();
		cmon.classnamereport = Thread.currentThread().getStackTrace()[2].getClassName();


		result.append(message);
		result.append(" At line No "+Integer.toString(cmon.lineNO));
		result.append(" In class "+cmon.classnamereport);
		result.append("<br>");
		System.out.println(result);

		if(cmon.inAtTest)
		{			

			Pattern excp = Pattern.compile("\\s*(.+?)\\s*(?=\\(Session info:)");

			Matcher matcher = excp.matcher(cmon.exception);

			if(matcher.find())
			{
				System.out.println("xyz at abc:::"+matcher.group(1));
			}

			else
			{
				System.out.println("no match found");
			}
			
			if(Boolean.valueOf(cmon.mGetPropertyFromFile("ContinueExecutionOnError")) == false && InvokedMethodListener.alreadypastedexception==1)
			{
				System.out.println("PRinted exception");
				result.insert(0, "+++++"+"<font color=\"#33A2FF\">"+matcher.group(1)+"</font>"+"----<br>");
			}

			else if(Boolean.valueOf(cmon.mGetPropertyFromFile("ContinueExecutionOnError")) == true)
			{
				System.out.println("PRinted exception");
				result.insert(0, "+++++"+"<font color=\"#33A2FF\">"+matcher.group(1)+"</font>"+"----<br>");
			}
			else
			{
				System.out.println("dont print exception");
				//result.insert(0, "+++++"+"<font color=\"#33A2FF\">"+matcher.group(1)+"</font>"+"----<br>");
			}
			cmon.resultset.add(result);

			System.out.println("cmon.resultset output is:::::: "+cmon.resultset);

			cmon.driver.quit();
		}

		cmon.continueExec = true;
		System.out.println(cmon.continueExec);

		throw new SkipException(message);

	}



}
