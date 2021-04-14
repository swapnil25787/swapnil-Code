package api;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.gargoylesoftware.htmlunit.javascript.host.SimpleArray;
import com.google.common.io.Files;
import com.thoughtworks.selenium.webdriven.commands.IsTextPresent;

import errorhandling.MainetCustomExceptions;


/**
 * @author Tejas.Kotekar
 *
 */
public class TestReport extends CommonUtilsAPI implements IReporter{

	public static String testCaseName;
	static String endTime;
	String scrLink="";
	String docLink="";
	String startTimeStr;
	String endTimeStr;
	Calendar cal = Calendar.getInstance();

	//Declrations relatd to new changes Sunil D Sonmale;
	int resultsetcount=0;
	
	public TestReport() {

		System.out.println("In test report");

		//mReadFromInputStream("D:\\AutomationFramework\\ABMSmartScript\\functional\\report.properties", "D:\\AutomationFramework\\ABMSmartScript\\functional\\report.properties");
		//mReadFromXML("D:\\AutomationFramework\\ABMSmartScript\\functional\\config\\config.xml");
		mReadFromInputStream(mGetPropertyFromFile("testReportPropertiesPath"));

	}

	//CommonUtilsAPI cmon = new CommonUtilsAPI();
	private PrintWriter out;
	Properties props = new Properties();
	

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		endTime=mEndExecution();
		try{			
			
			outputDirectory=moduleClipboardata;
			String cssSource= "";
			String passedImgSrc="";
			String failedImgSrc="";
			String skippedImgSrc="";

			new File(outputDirectory).mkdirs();
			try{
				/*File file= new File(outputDirectory, "custom-report.html");
				BufferedWriter buffWriter= new BufferedWriter(new FileWriter(file));*/
				out = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDirectory, "test-report.html"))));
				cssSource = mGetPropertyFromFile("reportCSSPath");
				passedImgSrc = mGetPropertyFromFile("passedImagePath");
				failedImgSrc = mGetPropertyFromFile("failedImagePath");
				skippedImgSrc = mGetPropertyFromFile("skippedImagePath");
				FileUtils.copyFileToDirectory(new File(cssSource), new File(outputDirectory));
				FileUtils.copyFileToDirectory(new File(passedImgSrc), new File(outputDirectory));
				FileUtils.copyFileToDirectory(new File(failedImgSrc), new File(outputDirectory));
				FileUtils.copyFileToDirectory(new File(skippedImgSrc), new File(outputDirectory));

			}
			catch (IOException e) {
				e.printStackTrace();
			}





			/*for (ISuite suite : suites) {
				//Following code gets the suite name
				String suiteName = suite.getName();
				ITestNGMethod methodname = suite.getAllMethods().get(0);
				//Getting the results for the said suite
				//  Map<String, ISuiteResult> result = suite.getResults();

				Map<String, ISuiteResult>  suiteResults = suite.getResults();
				for (ISuiteResult sr : suiteResults.values()) {
					ITestContext tc = sr.getTestContext();

					//System.out.println("Passed tests for suite '" + suiteName +
							"' is:" + tc.getPassedTests().getAllResults().size());
					//System.out.println("Failed tests for suite '" + suiteName +
							"' is:" + 
							tc.getFailedTests().getAllResults().size());
					//System.out.println("Skipped tests for suite '" + suiteName +
							"' is:" + 
							tc.getSkippedTests().getAllResults().size());
				}
			}*/



			//mCloseBrowser();


			int srNo = 1;

			print("<html>");
			print("<head>");

			print("<link href=.\\report-css.css rel=stylesheet type=text/css />");

			print("<link href=https://www.google.com/fonts#UsePlace:use/Collection:Open+Sans rel=stylesheet type=text/css />");
			
			print("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js\">");
			print("</script>");
			print("<script>");
			
			print("$(document).ready(function(){");
			print("$(\"button\").click(function(){");
			print("        $(\"p\").toggle();");
			print("});");
			print("});");
			
			print("</script>");
			print("</head>");

			print("<div id = main>");
			print("<div class = main_head>");

			print("<h1>"+mGetPropertyFromFile("rpt_topHeading")+"</h1>");
			print("</div>");

			print("<div class =sub_head>");
			for(ISuite suite : suites){

				print("<h3>"+mGetPropertyFromFile("rpt_testSuite")+suite.getName()+"</h3>");
				print("<center>");
				print("<table border=0 class=headTable>");
				print("<tr>");
				print("<td><h3 class=left>"+mGetPropertyFromFile("rpt_startTime")+startTime+"</h3></td>"+"\n");
				print("<td><h3 class=right>"+mGetPropertyFromFile("rpt_browserUsed")+mGetPropertyFromFile("rpt_browserName")+"</h3></td>"+"\n");
				print("</tr>");
				print("<tr>");
				print("<td><h3 class=left>"+mGetPropertyFromFile("rpt_endTime")+endTime+"</h3></td>"+"\n");
				print("<td><h3 class=right>"+mGetPropertyFromFile("rpt_URL")+ mGetPropertyFromFile("rpt_url")+"</h3></td>"+"\n");
				print("</tr>");
				print("</table>");
				print("</center>");
			}


			print("</div>");

			//print("<h3>"+"Class "+ getTestClass()+"</h3>"+"\n");

			//Total no of suites
			//print("Suites run::: "+suites.size());
			print("<br>");
			print("<table border=0 cellpadding=0 cellspacing=0 class=gridtable>");
			print("<tr>");
			print("<th>"+ "Sr No." +"</th>");
			print("<th>"+ "Method Name" +"</th>");
			print("<th>"+ "Start Time" +"</th>");
			print("<th>"+ "End Time" +"</th>");
			print("<th>"+ "Duration (HH:MM:SS)" +"</th>");
			print("<th>"+ "Status" +"</th>");
			print("<th>"+ "Message" +"</th>");
			//print("<th>"+ "Message" +"</th>");
			print("<th>"+ "ScreenShot" +"</th>");
			print("<th>"+ "Artefacts" +"</th>");

			print("</tr>");








			for(ISuite suite : suites){

				SimpleDateFormat dateFormat;
				Map<String, ISuiteResult> suiteResults = suite.getResults();

				for(String testName : suiteResults.keySet()){


					ISuiteResult suiteResult = suiteResults.get(testName);
					ITestContext testContext = suiteResult.getTestContext();

					/*			out.println("<tr>");
					out.print("<td>"+ "<b>Failed</b>"+ "</td>");
					out.print("<td>" +testContext.getFailedTests().size()+ "</td>");
				out.println("</tr>");
					 */
					IResultMap failedResult = testContext.getFailedTests();


					Set<ITestResult> testsFailed = failedResult.getAllResults();






					for (ITestResult testResult : testsFailed) {

						print("<tr>");

						out.print("<td>"+srNo+ "</td>");

						testCaseName=testResult.getName();
						print("<td>"+ testResult.getName()+ "</td>");

						/*if(testResult.getStatus() == ITestResult.FAILURE){
							print("<td>" +"Failed"+"<img src=.\\failed_img.png>"+ "</td>");
						}*/

						cal.setTimeInMillis(testResult.getStartMillis());
						dateFormat = new SimpleDateFormat("hh:mm:ss a");

						startTimeStr = dateFormat.format(cal.getTime());//Start time
						cal.setTimeInMillis(testResult.getEndMillis());
						endTimeStr = dateFormat.format(cal.getTime());//End time

						print("<td>"+startTimeStr+"</td>");
						print("<td>"+endTimeStr+"</td>");

						Date date1 = dateFormat.parse(startTimeStr);
						Date date2 = dateFormat.parse(endTimeStr);
						long difference = date2.getTime() - date1.getTime();

						//System.out.println("Difference in time :" +difference);

						long days = (int) (difference / (1000*60*60*24));  
						long hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60)); 
						long min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
						long sec = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours) - (1000*60*min)) / (1000);

						/*		long timeDifSeconds = difference / 1000;
				        long timeDifMinutes = difference / (60 * 1000);
				        long timeDifHours = difference / (60 * 60 * 1000);
				        long timeDifDays = difference / (24 * 60 * 60 * 1000);*/


						//	//System.out.println("Total time :" +timeDifMinutes);
						System.out.printf("%02d:%02d", hours, min);
						//String display = String.format("%02d:%02d", hours, min);
						String display = String.format("%02d:%02d:%02d",hours, min,sec);

						print("<td>"+display+"</td>");

						if(testResult.getStatus() == ITestResult.FAILURE){
							print("<td>" +"Failed"+"<img src=.\\failed_img.png>"+ "</td>");
						}

						String result=testResult.getThrowable().toString();
						//System.out.println(result);
						Pattern p = Pattern.compile("(?<=#####)\\s*(.+?)\\s*(?=-----)(|\n)");
						Matcher matcher1 = p.matcher(result);
						String throwable;
						List<String> finalThrowable= new ArrayList<String>();


						while(matcher1.find()){

							throwable = matcher1.group();

							//System.out.println(throwable);
							int i = finalThrowable.size();
							int k;
							for(k=0;k<i;k++){

								//System.out.println(k);
							}

							finalThrowable.add(testResult.getTestClass().getName()+" "+throwable+ "<br>");

							//System.out.println(finalThrowable);

						}

						print("<td>" +"<font color=red>" +result+ ""  +finalThrowable+"</font>"+"</td>");

						//finalThrowable.add(testResult.getTestClass().getName());

						//print(testResult.getTestClass().getName()+" ("+lineNo+") ");
						//print("<td>"+"<font color=red>"+finalThrowable+"</font>"+"</td>");


						scrLink=".\\"+testCaseName+"\\" +"Screenshots";
						docLink=".\\"+testCaseName+"\\"+"Documents";


						print("<td>"+"<a href='"+scrLink+"'>Link to screenshots</a>"+"</td>");
						print("<td>"+"<a href='"+docLink+"'>Link to artefacts</a>"+"</td>");
						srNo++;

					}


					IResultMap passResult = testContext.getPassedTests();
					Set<ITestResult> testsPassed = passResult.getAllResults();

					for(ITestResult testResult : testsPassed){

						out.print("<tr>");
						//print("<td>"+"Method name"+ "</td>");
						out.print("<td>"+srNo+ "</td>");

						print("<td>"+ testResult.getName()+ "</td>");

						cal.setTimeInMillis(testResult.getStartMillis());
						dateFormat = new SimpleDateFormat("hh:mm:ss a");

						startTimeStr = dateFormat.format(cal.getTime());//Start time
						cal.setTimeInMillis(testResult.getEndMillis());
						endTimeStr = dateFormat.format(cal.getTime());//End time


						print("<td>"+startTimeStr+ "</td>");
						print("<td>"+endTimeStr+ "</td>");

						Date date1 = dateFormat.parse(startTimeStr);
						Date date2 = dateFormat.parse(endTimeStr);
						long difference = date2.getTime() - date1.getTime();

						//System.out.println("Difference in time :" +difference);

						long days = (int) (difference / (1000*60*60*24));  
						long hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60)); 
						long min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
						long sec = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours) - (1000*60*min)) / (1000);

						/*		long timeDifSeconds = difference / 1000;
				        long timeDifMinutes = difference / (60 * 1000);
				        long timeDifHours = difference / (60 * 60 * 1000);
				        long timeDifDays = difference / (24 * 60 * 60 * 1000);*/


						//	//System.out.println("Total time :" +timeDifMinutes);
						System.out.printf("%02d:%02d", hours, min);
						//String display = String.format("%02d:%02d", hours, min);
						String display = String.format("%02d:%02d:%02d",hours, min,sec);
						print("<td>"+display+"</td>");

						testCaseName=testResult.getName();
						if(testResult.getStatus() == ITestResult.SUCCESS){

							print("<td>" +"Passed"+"<img src=.\\passed_img.png>"+"</td>");

						}

						print("<td>" +"None"+ "</td>");


						scrLink=".\\"+testCaseName+"\\"+ "Screenshots"+ "\\";
						docLink=".\\"+testCaseName+"\\"+ "Documents"+ "\\";

						print("<td>"+"<a href='"+scrLink+"'>Link to screenshots</a>"+"</td>");
						print("<td>"+"<a href='"+docLink+"'>Link to artefacts</a>"+"</td>");

						out.print("</tr>");

						srNo++;
					}


					IResultMap skipResult = testContext.getSkippedTests();
					Set<ITestResult> testsSkipped = skipResult.getAllResults();


					for(ITestResult testResult : testsSkipped){

						out.print("<tr>");
						//print("<td>"+"Method name"+ "</td>");
						out.print("<td>"+srNo+ "</td>");

						print("<td>"+ testResult.getName()+ "</td>");

						cal.setTimeInMillis(testResult.getStartMillis());
						dateFormat = new SimpleDateFormat("hh:mm:ss a");

						startTimeStr = dateFormat.format(cal.getTime());//Start time
						cal.setTimeInMillis(testResult.getEndMillis());
						endTimeStr = dateFormat.format(cal.getTime());//End time


						print("<td>"+startTimeStr+ "</td>");
						print("<td>"+endTimeStr+ "</td>");

						Date date1 = dateFormat.parse(startTimeStr);
						Date date2 = dateFormat.parse(endTimeStr);
						long difference = date2.getTime() - date1.getTime();

						//System.out.println("Difference in time :" +difference);

						long days = (int) (difference / (1000*60*60*24));  
						long hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60)); 
						long min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
						long sec = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours) - (1000*60*min)) / (1000);

						/*		long timeDifSeconds = difference / 1000;
				        long timeDifMinutes = difference / (60 * 1000);
				        long timeDifHours = difference / (60 * 60 * 1000);
				        long timeDifDays = difference / (24 * 60 * 60 * 1000);*/


						//	//System.out.println("Total time :" +timeDifMinutes);
						System.out.printf("%02d:%02d", hours, min);
						//String display = String.format("%02d:%02d", hours, min);
						String display = String.format("%02d:%02d:%02d",hours, min,sec);
						print("<td>"+display+"</td>");

						testCaseName=testResult.getName();
						if(testResult.getStatus() == ITestResult.SKIP){

							print("<td>" +"Skipped"+"<img src=.\\skipped_img.png>"+"</td>");

						}
						//MainetCustomExceptions custom = new MainetCustomExceptions("");
						//StackTraceElement[] result1=testResult.getThrowable().getStackTrace();
						/*StringBuilder result = new StringBuilder();
						for (StackTraceElement element : custom.getStackTrace()){
							result.append(element);
							result.append("\n");
						}*/
						//print("<td>" +result+ "</td>");
						
						/*if(cmon.skippedtests)
						{
						print("<td>"+"<font color=\"#6A5ACD\">"+exception+ "<br>" +resultskipped+ "</font>" "<p>"+stackTrace+ "</p>"+ "<button> Show/Hide Custom Stacktrace </button>"+"</td>");
						}
						
						else {*/
						
						//int resultsetcount=0;
						
						
						
						//System.out.println("resultset is ::: "+resultset.size());
						//System.out.println(resultset);
						/*//System.out.println(resultsetcount);
						//System.out.println("resultset index is ::: "+resultset.get(resultsetcount));*/
						
						print("<td>"+"<font color=\"#FF8C00\">"/*+exception+ "<br>" */+resultset.get(resultsetcount)+ "</font>" + "<p>"+stackTrace+ "</p>"+ "<button> Show/Hide Custom Stacktrace </button>"+"</td>");
						
						resultsetcount++;
						//System.out.println(resultsetcount);
						//}
						
						scrLink=".\\"+testCaseName+"\\"+ "Screenshots"+ "\\";
						docLink=".\\"+testCaseName+"\\"+ "Documents"+ "\\";

						print("<td>"+"<a href='"+scrLink+"'>Link to screenshots</a>"+"</td>");
						print("<td>"+"<a href='"+docLink+"'>Link to artefacts</a>"+"</td>");

						out.print("</tr>");
						srNo++;
					}

				}

			}

			print("</table>");
			print("</div>");

			out.print("<br>");


		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			out.flush();
			out.close();
		}

	}


	private void print(String text){

		out.print(text);

	}





}

