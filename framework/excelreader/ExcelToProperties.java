package excelreader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import api.CommonUtilsAPI;

/**
 * @author Tejas.Kotekar
 *
 */

//This class converts the excel workbook to properties file
public class ExcelToProperties extends CommonUtilsAPI{

	public ExcelToProperties() {
		
		//mReadFromXML("D:\\AutomationFramework\\ABMSmartScript\\functional\\config\\config.xml");
		
	}

	public static void main(String[] args) {
		
		ExcelToProperties excelProp = new ExcelToProperties();


		excelProp.convertExcelToProperties("CommonID","","");
		excelProp.convertExcelToProperties("TP_AplictnFrBuildinPrmsn_Data","TP_AplictnFrBuildinPrmsn_Data","3");

	}
	
/*
 	This method accepts excel file name,sheet name and properties file name as parameters, iterates through excel sheets
 	and writes the keys and values to a properties file.
 */
	
	
	public void convertExcelToProperties(String fileName,String sheetName,String colNum){
		
		XSSFWorkbook workbook;
		XSSFSheet sheet = null;
		XSSFRow row;
		XSSFCell keyCell = null;
		XSSFCell valueCell = null;
		int totalSheets;
		Properties prop = new Properties();
		Writer writer =null;
		String value = null;
		String key = null;
		String sheetNames = null;
		Iterator<Cell> cellIterator = null;

		try {
			FileInputStream fis = new FileInputStream(mGetPropertyFromFile("Excel_Path")+fileName+".xlsx");
			//File file = new File(mGetPropertyFromFile("Properties_Path")+fileName+".properties");
			File file;
				
			workbook =  new XSSFWorkbook(fis);
			
			totalSheets = workbook.getNumberOfSheets();
			
			for(int i = 0;i<totalSheets;i++){
				
				sheetNames = workbook.getSheetName(i);
				sheet = workbook.getSheetAt(i);
				if(sheetNames.equals(sheetName)){
					file = new File(mGetPropertyFromFile("Properties_Path")+sheetNames+".properties");
					writer = new BufferedWriter(new FileWriter(file));
					break;
				}
			}
			
			

			//for(int i=0; i<totalSheets; i++){
				
				//sheet = workbook.getSheetAt(i);
				sheet = workbook.getSheet(sheetName);
				
				Iterator<Row> rowIterator = sheet.rowIterator();
				
				while(rowIterator.hasNext()){
					
					row = (XSSFRow) rowIterator.next();

					cellIterator = row.cellIterator();
					
					writer.write("\n");
					valueCell =   (XSSFCell) cellIterator.next();
					value = valueCell.getStringCellValue().toString();
					
					
					writer.write(value+" = ");
					
					//if (valueCell.getStringCellValue() != null &&  valueCell.getStringCellValue().length() != 0){
					
					while(cellIterator.hasNext()){
							
						
						
							//keyCell = (XSSFCell) cellIterator.next();
						
							keyCell = row.getCell(Integer.valueOf(colNum),Row.CREATE_NULL_AS_BLANK);
					
							switch(keyCell.getCellType()){
							
								case XSSFCell.CELL_TYPE_NUMERIC :
									//key = keyCell.getRichStringCellValue().toString();
									key = keyCell.getRawValue();
									break;
							
								case XSSFCell.CELL_TYPE_STRING :
									key =  keyCell.getStringCellValue();
									break;
							}
							
							
						
						//value = valueCell.getStringCellValue().toString();
						System.out.println("Key::>"+value +"\t"+ "Value::>"+key);
					
							//writer.write(value+" = ");
							writer.write((String.valueOf(key)));
							key="";
							break;
							//writer.write("\n");
						}
					
					
					//}
			
				}
				//}
			
			writer.write("\n");
			prop.store(writer, null);
			writer.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
	}	
	
	
	public void convertExcelToProperties(String fileName,String sheetName){
		
		XSSFWorkbook workbook;
		XSSFSheet sheet = null;
		XSSFRow row;
		XSSFCell keyCell = null;
		XSSFCell valueCell = null;
		int totalSheets;
		Properties prop = new Properties();
		Writer writer =null;
		String value = null;
		String key = null;
		String sheetNames = null;
		Iterator<Cell> cellIterator = null;

		try {
			FileInputStream fis = new FileInputStream(mGetPropertyFromFile("Excel_Path")+fileName+".xlsx");
			//File file = new File(mGetPropertyFromFile("Properties_Path")+fileName+".properties");
			File file;
			workbook =  new XSSFWorkbook(fis);
			
			totalSheets = workbook.getNumberOfSheets();
			for(int i = 0;i<totalSheets;i++){
				sheetNames = workbook.getSheetName(i);
				sheet = workbook.getSheetAt(i);
				if(sheetNames.equals(sheetName)){
					file = new File(mGetPropertyFromFile("Properties_Path")+sheetNames+".properties");
					writer = new BufferedWriter(new FileWriter(file));
					break;
				}
			}
			
			

			//for(int i=0; i<totalSheets; i++){
				
				//sheet = workbook.getSheetAt(i);
				sheet = workbook.getSheet(sheetName);
				
				Iterator<Row> rowIterator = sheet.rowIterator();
				
				while(rowIterator.hasNext()){
					
					row = (XSSFRow) rowIterator.next();

					cellIterator = row.cellIterator();
					
					writer.write("\n");
					valueCell =   (XSSFCell) cellIterator.next();
					value = valueCell.getStringCellValue().toString();
					
					
					writer.write(value+" = ");
					
					//if (valueCell.getStringCellValue() != null &&  valueCell.getStringCellValue().length() != 0){
					
					while(cellIterator.hasNext()){
							
							//keyCell = (XSSFCell) cellIterator.next();
							
							keyCell = row.getCell(1,Row.CREATE_NULL_AS_BLANK);
							switch(keyCell.getCellType()){
							
								case XSSFCell.CELL_TYPE_NUMERIC :
									//key = keyCell.getRichStringCellValue().toString();
									key = keyCell.getRawValue();
									break;
							
								case XSSFCell.CELL_TYPE_STRING :
									key =  keyCell.getStringCellValue();
									break;
							}
							
							
						
						//value = valueCell.getStringCellValue().toString();
						System.out.println("Key::>"+value +"\t"+ "Value::>"+key);
					
							//writer.write(value+" = ");
							writer.write((String.valueOf(key)));
							break;
							//writer.write("\n");
						}
					
					
					//}
			
				}
				//}
			
			writer.write("\n");
			prop.store(writer, null);
			writer.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
	}	
	
	
	
	
}
