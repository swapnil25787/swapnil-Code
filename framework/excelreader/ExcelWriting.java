package excelreader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import errorhandling.MainetCustomExceptions;
import api.CommonUtilsAPI;


public class ExcelWriting extends CommonUtilsAPI {


	public static WebDriver driver;
	static XSSFWorkbook workbook;
	static XSSFSheet sheet = null;
	public static XSSFRow row1 =null;

	static XSSFCell keyCell = null;
	static XSSFCell valueCell = null;
	static String sheetName = null;
	static int NumberOfRows = 0;
	//static int NumberOfColumns = 0;
	static String key;
	static String value;
	static Boolean isEmpty = false;
	//public static int NumberOfRowsforWriting;
	public static int NumberOfRowsforWriting;

	public static ArrayList<String> datatobewritten = new ArrayList<String>();

	public static void main (String[] args)throws Exception{




		FileInputStream fis = new FileInputStream(new File("D:\\AutomationFramework\\TMCconfig\\ExportExcel.xlsx"));
		XSSFWorkbook workbook = new XSSFWorkbook (fis);
		XSSFSheet sheet = workbook.getSheetAt(0);


		//sheet = (XSSFSheet) workbook.getSheet(sheetName);

		if(CurrentinvoCount==0)
		{
		NumberOfRows = sheet.getLastRowNum();

		System.out.println(NumberOfRows);

		//Create First Row
		XSSFRow row1 = sheet.createRow(NumberOfRows+2);
		XSSFCell r1c1 = row1.createCell(0);
		r1c1.setCellValue("xyz");
		XSSFCell r1c2 = row1.createCell(1);
		r1c2.setCellValue("ykdkds");
		XSSFCell r1c3 = row1.createCell(2);
		r1c3.setCellValue("my way");
		fis.close();
		FileOutputStream fos =new FileOutputStream(new File("D:\\xyz.xlsx"));
		workbook.write(fos);
		fos.close();
		System.out.println("Done");  
		}


	}

	public void excelWriting (String filename, String key, String variable)
	{

		try{

			FileInputStream fis = new FileInputStream(new File(filename));
			XSSFWorkbook workbook = new XSSFWorkbook (fis);
			XSSFSheet sheet = workbook.getSheetAt(0);



			if(CurrentinvoCount==0)
			{

				NumberOfRows = sheet.getLastRowNum();

				System.out.println("numberof rows in excel writting====>"+NumberOfRows);

				NumberOfRowsforWriting=NumberOfRows+2;
				System.out.println("no of rows for writing in if "+NumberOfRowsforWriting);

				datatobewritten.add(key);
				datatobewritten.add(variable);


			}
			else
			{
				System.out.println("numberof rows in else ===excel writting====>"+NumberOfRows);
				datatobewritten.add(variable);
			}

			row1= sheet.createRow(NumberOfRowsforWriting);

			datatobewritten.size();

			//for(int i=0;i<=datatobewritten.size()-1;i++)
			for(int i=0;i<=datatobewritten.size()-1;i++)

			{
				XSSFCell r1c1 = row1.createCell(i);
				r1c1.setCellValue(datatobewritten.get(i));

			}

			fis.close();
			FileOutputStream fos =new FileOutputStream(new File(filename));
			workbook.write(fos);
			fos.close();
			System.out.println("Done");  

		}
		catch(Exception e)
		{
			throw new MainetCustomExceptions("Error in ExcelWriting Method");
		}
	}
	
		public void excelWriting (String filename, String key, String variable, String variable1)
		{

			try{

				FileInputStream fis = new FileInputStream(new File(filename));
				XSSFWorkbook workbook = new XSSFWorkbook (fis);
				XSSFSheet sheet = workbook.getSheetAt(0);



				if(CurrentinvoCount==0)
				{
					int foo = Integer.parseInt(variable1);
					NumberOfRows = foo;

					System.out.println("numberof rows in excel writting====>"+NumberOfRows);

					NumberOfRowsforWriting=NumberOfRows+2;
					System.out.println("no of rows for writing in if "+NumberOfRowsforWriting);

					datatobewritten.add(key);
					datatobewritten.add(variable);
					System.out.println("Arraylist invocount 0for writing===****"+datatobewritten);

				}
				else
				{
					System.out.println("numberof rows in else ===excel writting====>"+NumberOfRows);
					datatobewritten.add(variable);
					System.out.println("Arraylist for invocount more than one writing===****"+datatobewritten);
				}
System.out.println("Arraylist for writing===****"+datatobewritten);
				row1= sheet.createRow(NumberOfRowsforWriting);

				datatobewritten.size();
				
				System.out.println("datatobewritten=>"+datatobewritten);
			
				//for(int i=0;i<=datatobewritten.size()-1;i++)
				int s=fromcolumn;
				for(int i=0;i<=datatobewritten.size()-1;i++)

				{
					if(i==0)
					{
					XSSFCell r1c1 = row1.createCell(i);
					r1c1.setCellValue(datatobewritten.get(i));
					
					}else{
						
						XSSFCell r1c1 = row1.createCell(s);
						r1c1.setCellValue(datatobewritten.get(i));
						s=s+1;
					}
					
				}

				fis.close();
				FileOutputStream fos =new FileOutputStream(new File(filename));
				workbook.write(fos);
				fos.close();
				System.out.println("Done");  

			}
			catch(Exception e)
			{
				throw new MainetCustomExceptions("Error in ExcelWriting Method");
			}

	}


}


