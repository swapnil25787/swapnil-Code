package excelreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Tejas.Kotekar
 *
 */

//This program converts properties file to an excel workbook
public class PropertiesToExcel {
	String propertyString;
	String key;
	String value;
	
	
	
	public static void main(String[] args) {
		
		PropertiesToExcel propToExcel = new PropertiesToExcel();
		propToExcel.convertPropertiesToExcel("D:\\AutomationFramework\\ABMSmartScript\\functional\\CommonID.properties", "D:\\AutomationFramework\\ABMSmartScript\\framework\\excelreader\\CommonID.xlsx");
		
		
	}
	
	/*
	 	This method accepts the properties file and excel file paths, reads the property file, splits each line into key 
	 	and value pair based on the regex "=" and puts them into a  map. This map is then used to set the excel workbook
	 	with those keys and values.
	 */
	public void convertPropertiesToExcel(String propFilePath, String excelFilePath){


		try {
			
			int lhs = 0;
			int rhs = 1;
			int mapSize = 0;
			
			String[] pair = null;
			XSSFWorkbook workbook = null;
			XSSFSheet sheet;
			
			XSSFRow rowOne = null;
			XSSFCell cellZero = null;
			XSSFCell cellOne = null;
			FileOutputStream fout;
			
			FileInputStream fis = new FileInputStream( new File(propFilePath));
			fout =  new FileOutputStream(new File(excelFilePath));
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			
			Map<String,String> map = new LinkedHashMap<String,String>();
			Map<String,String> propMap = new LinkedHashMap<String,String>();
			
			workbook = new XSSFWorkbook();
			sheet =  workbook.createSheet("rtiData");
			sheet.createRow(0);
			
			//Reads the property file line by line
			while((propertyString=reader.readLine())!=null){
				//!propertyString.startsWith("#") &&
				if(null != propertyString && !propertyString.isEmpty()){
					
					/*Split the string on regex "=" and set the limit to 2 to avoid "=" effect in value*/
					pair = propertyString.trim().split("=".trim(),2);
					
					if(pair.length > 1 ){
					
						map.put(pair[lhs].trim(), pair[rhs].trim());
							
						System.out.print(pair[lhs]+" = ");
						System.out.println(map.get(pair[lhs].trim()));
						
						propMap.put(pair[lhs],map.get(pair[lhs].trim()));
						
						rowOne = sheet.createRow(sheet.getLastRowNum()+1);
						cellZero = rowOne.createCell(lhs);
						cellOne = rowOne.createCell(rhs);
						
						key=pair[lhs];
						value=pair[rhs].trim();
						
						cellZero.setCellValue(new XSSFRichTextString(key));
						cellOne.setCellValue(new XSSFRichTextString(value));
						
				
						
					} else {
						
						rowOne = sheet.createRow(sheet.getLastRowNum()+1);
						cellZero = rowOne.createCell(0);
						cellZero.setCellValue(pair[0]);
						
					}
					
					
				
				}
				
			}
			mapSize = propMap.size();
			for(int i=1;i<mapSize+1;i++){
				sheet.shiftRows(i, i, -1);
			}
			reader.close();
			workbook.write(fout);
			workbook.close();
			fout.flush();
			fout.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
	}

}
