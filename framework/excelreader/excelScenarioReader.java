package excelreader;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import common.CommonMethods;
import api.CommonUtilsAPI;


public class excelScenarioReader extends CommonUtilsAPI {

	public static WebDriver driver;
	static XSSFWorkbook workbook;
	static XSSFSheet sheet = null;
	static XSSFRow row =null;

	static XSSFCell keyCell = null;
	static XSSFCell valueCell = null;
	static String sheetName = null;
	static int NumberOfRows = 0;
	static String key;
	static String value;
	static Boolean isEmpty = false;

	//static Iterator<Cell> cellIterator = null;

	static CommonMethods common =new CommonMethods();
	
	ArrayList<String> avalue=new ArrayList<>();


	public void trialexceltohashscenariomap()
	{
		try
		{

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {

			//	System.out.println(hscenariomap);

				row = (XSSFRow) rowIterator.next();

				Iterator <Cell> cellIterator = row.cellIterator();


				avalue=new ArrayList<>();

				for(int j=0;j<=(NoOfColumns-1);j++)
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
							}
						//	System.out.println("value is:::"+value);

							if(j==1)
							{
								//System.out.println("value of j is:"+j);
								//System.out.println("value is:"+value);
								key = value.trim();
							}
							if(j>1 && j<=(NoOfColumns-1))
							{
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
				//System.out.println("values are:"+avalue);				

				hscenariomap.put(key, avalue);
			//	System.out.println("hscenariomap===>"+hscenariomap);

				key = null;
				//System.out.println("after calling key as nullhscenariomap"+hscenariomap);

			//	System.out.println("values at hashscenariomap are::::"+hscenariomap.get("birth_RegistrationWithCertificate"));
			//	System.out.println("values of url hashscenariomap are::::"+hscenariomap.get("birthRegWithCert_BirthRegAuthorization"));

			}


			for (Entry<String, ArrayList<String>> entry : common.hscenariomap.entrySet()) {
				String key1 = entry.getKey();
				ArrayList<String> value1 = entry.getValue();
			//	System.out.println("Key is ::"+key1 );
			//	System.out.println("Value is::::::: "+value1);
				// do stuff
			}

			//System.out.println("values at hashscenariomap are::::"+common.hscenariomap.get("browserName"));
			//System.out.println("values of url hashscenariomap are::::"+common.hscenariomap.get("url"));
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	public void excelScenarioreader(String filename)	
	{
		try{


			current = new java.io.File( "." ).getCanonicalPath();
			
			current = current.replaceAll(current, "\\");
			//current = current.toString();
		//	System.out.println("Current dir:"+current);
			current =current + filename;

		//	System.out.println("FILE For common data is "+current);


			//ArrayList<String> avalue;
			FileInputStream fis=new FileInputStream(current);
			//FileInputStream fis=new FileInputStream("D:\\AutomationFramework\\ABMSmartScript\\framework\\excelreader\\CommonID .xlsx");
			workbook =(XSSFWorkbook) WorkbookFactory.create(fis);
			sheetName = workbook.getSheetName(0);
			sheet = (XSSFSheet) workbook.getSheet(sheetName);
			NumberOfRows = sheet.getLastRowNum();
		//	System.out.println("Number of rows are :"+NumberOfRows);

			row = sheet.getRow(0);
			NoOfColumns = row.getLastCellNum();	
			log.info("no of columns in scenario reader is "+NoOfColumns);
			//System.out.println("Number of columns are mutidie:"+NumberOfColumns);
			System.out.println();

			trialexceltohashscenariomap();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


}






