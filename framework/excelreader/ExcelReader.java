package excelreader;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import common.CommonMethods;
import api.CommonUtilsAPI;

/// Class for reading excel for data driven
// Sunil D Sonmale


public class ExcelReader extends CommonUtilsAPI {

	public static WebDriver driver;
	static XSSFWorkbook workbook;
	static FormulaEvaluator formulaEval;
	static XSSFSheet sheet = null;
	static XSSFRow row =null;

	static XSSFCell keyCell = null;
	static XSSFCell valueCell = null;
	static String sheetName = null;
	static int NumberOfRows = 0;
	//static int NumberOfColumns = 0;
	static String key;
	static String value;
	static Boolean isEmpty = false;

	//static Iterator<Cell> cellIterator = null;

	//static CommonMethods common =new CommonMethods();
	//	static RTIServices rtiservices = new RTIServices();
	//	static ExcelToProperties excelToProp = new ExcelToProperties();
	//static MultiExecution me = new MultiExecution();
	ArrayList<String> avalue=new ArrayList<>();
	ArrayList<String> xvalue=new ArrayList<>();
	ArrayList<String> vvalue = new ArrayList<>();
	//String vvalue; 
	//int fromcol, tocol;

	//ExcelReader ER = new ExcelReader();

	/*public void trialexceltohashmap()
	{
		try
		{

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {

				System.out.println(hmapid);

				row = (XSSFRow) rowIterator.next();

				Iterator <Cell> cellIterator = row.cellIterator();

				avalue=new ArrayList<>();

				for(int j=0;j<=(NumberOfColumns-1);j++)
				{
					while (cellIterator.hasNext())
					{
						try{
							valueCell = row.getCell(Integer.valueOf(j),Row.CREATE_NULL_AS_BLANK);

							switch(valueCell.getCellType())
							{

							case XSSFCell.CELL_TYPE_BLANK :
								//value = valueCell.getRawValue();
								value = "?";
								break;

							case XSSFCell.CELL_TYPE_NUMERIC :
								value = valueCell.getRawValue().toString();
								break;

							case XSSFCell.CELL_TYPE_STRING :
								value = (valueCell.getStringCellValue());
								break;

								case XSSFCell.CELL_TYPE_FORMULA:
								value = (valueCell. getStringCellValue());
								break;
							}
							System.out.println("value is:::"+value);

							if(j==0)
							{
								System.out.println("value of j is:"+j);
								System.out.println("value is:"+value);
								key = value.trim();
							}
							if(j==1)
							{
								System.out.println("value of j is at value:"+j);
								System.out.println("value is at value:"+value);
								vvalue =value.trim();
							}

							break;
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				}


				System.out.println("key is:"+key);
				System.out.println("values are:"+vvalue);				

				hmapid.put(key, vvalue);
				System.out.println(hmapid);

				key = null;
				System.out.println(hmapid);

			}


			for (Entry<String, String> entry : hmapid.entrySet()) {
				String key1 = entry.getKey();
				String value1 = entry.getValue();
				System.out.println("Key is ::"+key1 );
				System.out.println("Value is::::::: "+value1);
				// do stuff
			}

		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
*/
	
	///New method for identifier parameterization
	
	public void trialexceltohashmap()
	{
		try
		{

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {

			//	System.out.println(hmapid);

				row = (XSSFRow) rowIterator.next();

				Iterator <Cell> cellIterator = row.cellIterator();

				vvalue=new ArrayList<>();

				for(int j=0;j<=(NumberOfColumns-1);j++)
				{
					while (cellIterator.hasNext())
					{
						try{
							valueCell = row.getCell(Integer.valueOf(j),Row.CREATE_NULL_AS_BLANK);

							switch(valueCell.getCellType())
							{

							case XSSFCell.CELL_TYPE_BLANK :
								//value = valueCell.getRawValue();
								///placed chiness charecter by sunil.......replaced with 
								value = "字"; 
								break;

							case XSSFCell.CELL_TYPE_NUMERIC :
								value = valueCell.getRawValue().toString();
								break;

							case XSSFCell.CELL_TYPE_STRING :
								value = (valueCell.getStringCellValue());
								break;

								/*case XSSFCell.CELL_TYPE_FORMULA:
								value = (valueCell. getStringCellValue());
								break;*/
							}
						//	System.out.println("value is:::"+value);

							if(j==0)
							{
								//System.out.println("value of j is:"+j);
								//System.out.println("value is:"+value);
								key = value.trim();
							}
							if(j==1 || j==2)
							{
							//	System.out.println("value of j is at value:"+j);
							//	System.out.println("value is at value:"+value);
								vvalue.add(value.trim());
							}
							
							break;
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				}


				//System.out.println("key is:"+key);
		//		System.out.println("values are:"+vvalue);				

				hmapid.put(key, vvalue);
				//System.out.println(hmapid);

				key = null;
				//log.info(hmapid);

			}

/*
			for (Entry<String, String> entry : hmapid.entrySet()) {
				String key1 = entry.getKey();
				String value1 = entry.getValue();
				System.out.println("Key is ::"+key1 );
				System.out.println("Value is::::::: "+value1);
				// do stuff
			}*/

			for (Entry<String, ArrayList<String>> entry : hmapid.entrySet()) {
				String key1 = entry.getKey();
				ArrayList<String> value1 = entry.getValue();
			//	System.out.println("Key is ::"+key1 );
				//System.out.println("Value is::::::: "+value1);
				// do stuff
			}
			
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void trialexceltohashmap(String xyz)
	{
		try
		{

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {

				//System.out.println(hmap);

				row = (XSSFRow) rowIterator.next();

				Iterator <Cell> cellIterator = row.cellIterator();

				avalue=new ArrayList<>();

				for(int j=0;j<=(NumberOfColumns-1);j++)
				{
					while (cellIterator.hasNext())
					{
						try{
							valueCell = row.getCell(Integer.valueOf(j),Row.CREATE_NULL_AS_BLANK);

							switch(valueCell.getCellType())
							{

							case XSSFCell.CELL_TYPE_BLANK :
								//value = valueCell.getRawValue();
								value = "?";
								break;

							case XSSFCell.CELL_TYPE_NUMERIC :
								value = valueCell.getRawValue();
								break;

							case XSSFCell.CELL_TYPE_STRING :
								value = (valueCell.getStringCellValue());
								break;

							case XSSFCell.CELL_TYPE_FORMULA:  
							{  
								// Evaluting cell  
								CellValue c=formulaEval.evaluate(valueCell);  
							//	System.out.println(c.getStringValue());
								if(c.getStringValue()==null)
								{
									value="?";	
								}
								else{
									value=(c.getStringValue());
								}
								break;
							}  

							}
							//System.out.println("value is:::"+value);

							if(j==0)
							{
						//		System.out.println("value of j is:"+j);
							//	System.out.println("value is:"+value);
								key = value.trim();
							}
							//if(j<=tocol && j>=fromcol)
							if(j>0)	
							{

								//System.out.println("value of j inside 2nd loop is:::"+j);


							//	System.out.println("value of j is at value:"+j);
							//	System.out.println("value is at value:"+value);
								avalue.add(value.trim());
							}

							break;
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				}


		//		System.out.println("key is:"+key);
			//	System.out.println("values are:"+avalue);		


				hmapforformula.put(key, avalue);
			//	System.out.println(hmapforformula);

				key = null;
			///	System.out.println(hmapforformula);

			}


			for (Entry<String, ArrayList<String>> entry : hmapforformula.entrySet()) {
				String key1 = entry.getKey();
				ArrayList<String> value1 = entry.getValue();
			//	System.out.println("Key is ::"+key1 );
				//System.out.println("Value is::::::: "+value1);
				// do stuff
			}

		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	
	
	
	public void trialexceltohashmap(int fromcol, int tocol)
	{
		try
		{

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {

			//	System.out.println(hmap);

				row = (XSSFRow) rowIterator.next();

				Iterator <Cell> cellIterator = row.cellIterator();

				avalue=new ArrayList<>();

				for(int j=0;j<=(NumberOfColumns-1);j++)
				{
					while (cellIterator.hasNext())
					{
						try{
							valueCell = row.getCell(Integer.valueOf(j),Row.CREATE_NULL_AS_BLANK);

							switch(valueCell.getCellType())
							{

							case XSSFCell.CELL_TYPE_BLANK :
								//value = valueCell.getRawValue();
								value = "?";
								break;

							case XSSFCell.CELL_TYPE_NUMERIC :
								value = valueCell.getRawValue();
								break;

							case XSSFCell.CELL_TYPE_STRING :
								value = (valueCell.getStringCellValue());
								break;

							case XSSFCell.CELL_TYPE_FORMULA:  
							{  
								// Evaluting cell  
								CellValue c=formulaEval.evaluate(valueCell);  
							//	System.out.println(c.getStringValue());
								if(c.getStringValue()==null)
								{
									value="?";	
								}
								else{
									value=(c.getStringValue());
								}
								break;
							}  

							}
					//		System.out.println("value is:::"+value);

							if(j==0)
							{
								//System.out.println("value of j is:"+j);
							//	System.out.println("value is:"+value);
								key = value.trim();
							}
							//if(j<=tocol && j>=fromcol)
							if(j>=fromcol && j<=tocol)	
							{

							//	System.out.println("value of j inside 2nd loop is:::"+j);


							//	System.out.println("value of j is at value:"+j);
								//System.out.println("value is at value:"+value);
								avalue.add(value.trim());
							}

							break;
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				}


			//	System.out.println("key is:"+key);
			//	System.out.println("values are:"+avalue);		


				hmap.put(key, avalue);
				System.err.println("hmap==>"+hmap);
			//	System.out.println(hmap);

				key = null;
				//System.out.println(hmap);

			}


			for (Entry<String, ArrayList<String>> entry : hmap.entrySet()) {
				String key1 = entry.getKey();
				ArrayList<String> value1 = entry.getValue();
			//	System.out.println("Key is ::"+key1 );
			//	System.out.println("Value is::::::: "+value1);
				// do stuff
			}

		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void excelreader(String filename)	
	{
		try{

			current = filename;

	//		System.out.println("FILE For common data is "+current);


			//ArrayList<String> avalue;
			FileInputStream fis=new FileInputStream(current);
			//FileInputStream fis=new FileInputStream("D:\\AutomationFramework\\ABMSmartScript\\framework\\excelreader\\CommonID .xlsx");
			workbook =(XSSFWorkbook) WorkbookFactory.create(fis);
			sheetName = workbook.getSheetName(0);
			sheet = (XSSFSheet) workbook.getSheet(sheetName);
			NumberOfRows = sheet.getLastRowNum();
			System.out.println("Number of rows are :"+NumberOfRows);



			row = sheet.getRow(0);
			NumberOfColumns = row.getLastCellNum();	
			System.out.println("Number of columns are mutidie:"+NumberOfColumns);
			System.out.println();

			trialexceltohashmap();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void excelreader(String filename, int fromcol, int tocol)	
	{
		try{
			current = filename;

			System.out.println("FILE For common data is "+current);

			//ArrayList<String> avalue;
			FileInputStream fis=new FileInputStream(current);
			//FileInputStream fis=new FileInputStream("D:\\AutomationFramework\\ABMSmartScript\\framework\\excelreader\\CommonID .xlsx");
			workbook =(XSSFWorkbook) WorkbookFactory.create(fis);
			sheetName = workbook.getSheetName(0);

			// Creating formula evaluator object  
			formulaEval = workbook.getCreationHelper().createFormulaEvaluator();  

			sheet = (XSSFSheet) workbook.getSheet(sheetName);
			NumberOfRows = sheet.getLastRowNum();
			System.out.println("Number of rows are :"+NumberOfRows);

			row = sheet.getRow(0);
			NumberOfColumns = row.getLastCellNum();	
			System.out.println("Number of columns are mutidie:"+NumberOfColumns);
			System.out.println();

			trialexceltohashmap(fromcol, tocol);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void excelreader()	
	{
		try{
			

			System.out.println("FILE For common data is "+current);

			//ArrayList<String> avalue;
			FileInputStream fis=new FileInputStream(current);
			//FileInputStream fis=new FileInputStream("D:\\AutomationFramework\\ABMSmartScript\\framework\\excelreader\\CommonID .xlsx");
			workbook =(XSSFWorkbook) WorkbookFactory.create(fis);
			sheetName = workbook.getSheetName(1);

			// Creating formula evaluator object  
			formulaEval = workbook.getCreationHelper().createFormulaEvaluator();  

			sheet = (XSSFSheet) workbook.getSheet(sheetName);
			NumberOfRows = sheet.getLastRowNum();
	//		System.out.println("Number of rows are :"+NumberOfRows);

			row = sheet.getRow(0);
			NumberOfColumns = row.getLastCellNum();	
		//	System.out.println("Number of columns are mutidie:"+NumberOfColumns);
			System.out.println();

			trialexceltohashmap("xyz");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	

	//Sunil D Sonmale
	//Actual data reader method which needs to be called using object of excel reader

	public void datareader()
	{
		log.info(hscenariomap);

		fromcolumn = Integer.parseInt(hscenariomap.get(currentmethodname).get(2));
		tocolumn = Integer.parseInt(hscenariomap.get(currentmethodname).get(3));
System.err.println("from==>"+fromcolumn);
System.err.println("TO==>"+tocolumn);
		System.out.println("from column and to columns are "+fromcolumn+"and and and "+tocolumn);

		if(CurrentinvoCount==0)
		{		
			hmap.clear();
			hmapid.clear();

			////////////////////////
			String idsheets = hscenariomap.get(currentmethodname).get(4);
			//log.info("idsheets::: "+idsheets);
			String[] idsheetsarray = idsheets.split(",");

			//System.out.println(idsheetsarray);

			int idsheetlength = idsheetsarray.length;
	//		System.out.println(idsheetlength);

			for (int i=0; i<idsheetlength; i++)
			{
				excelreader(idsheetsarray[i].trim());
			}

			///////////////////////////////////////////////

			String datasheets = hscenariomap.get(currentmethodname).get(5);

			log.info("datasheets:::"+datasheets);

			String[] datasheetsarray = datasheets.split(",");

	//		System.out.println(datasheetsarray);

			int datasheetlength = datasheetsarray.length;
		//	System.out.println(datasheetlength);

			for (int i=0; i<datasheetlength; i++)
			{
				excelreader(datasheetsarray[i].trim(), fromcolumn, tocolumn);
				excelreader();
			}

			//log.info(hmap);
		//	log.info(hmapid);
			//log.info("xyz at abc for sunil trial"+hmapforformula);
		}
		
		
		
	}
}
