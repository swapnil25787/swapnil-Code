package api;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.jasper.tagplugins.jstl.core.If;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DriverProgram  {

	public static ArrayList<String> method= new ArrayList<String>();
	public static ArrayList<String> ExecutionFlag= new ArrayList<String>();
	public static ArrayList<String> EFlag= new ArrayList<String>();
	public static ArrayList<String> Priority= new ArrayList<String>();
	public static ArrayList<String> FC= new ArrayList<String>();
	public static ArrayList<String> TC= new ArrayList<String>();
	public static ArrayList<String> Module= new ArrayList<String>();
	
	//	static String TC[];
	public static ArrayList<String> status= new ArrayList<String>();

	public static ArrayList<Integer> Fcount= new ArrayList<Integer>();
	public static ArrayList<String> newFcount= new ArrayList<String>();
	/*public static ArrayList<String> temp= new ArrayList<String>();*/
	public static ArrayList<Integer> Tcount= new ArrayList<Integer>();
	public static int temp=0;
	public static void maxinvocation()
	{

		for (int i = 0; i < Fcount.size(); i++) {

			if (EFlag.get(i).equalsIgnoreCase("yes")) {
				int diff=Tcount.get(i)-Fcount.get(i)+1;


				if (diff>temp) {
					temp=diff;
				}

			}

		}

		System.out.println("temp==>"+temp);
		
	}

	public static void manupulation() throws Exception
	{

		for (int i = 1; i < FC.size(); i++) {

			int f = Integer.parseInt(FC.get(i));
			int T = Integer.parseInt(TC.get(i));
			Fcount.add(f);
			Tcount.add(T);
			EFlag.add(ExecutionFlag.get(i));

		}
		System.out.println("Fcount"+Fcount);
		System.out.println("Tcount"+Tcount);
		System.out.println("EFlag"+EFlag);
		maxinvocation();
		//for (int k = 0; k < Tcount.size(); k++) {

		for (int k = 0; k < temp; k++) {
			for (int j = 0; j < Fcount.size(); j ++) {
				if (k>0) {
					if (EFlag.get(j).equalsIgnoreCase("yes")) {
						if (Tcount.get(j)>Fcount.get(j)) {
							int oldVal = Fcount.get(j);
							int newVal = oldVal + 1;
							Fcount.set(j, newVal);

						}
						if (Tcount.get(j)<Fcount.get(j)) {
							//int oldVal = Fcount.get(j);
						//	int newVal = oldVal + 1;
							//Fcount.set(j, newVal);
							EFlag.set(j, "No");
						}

					}

				}

			}
			System.out.println("Fcount"+Fcount);
			System.out.println("EFlag"+EFlag);
			writeexcel();
			FileOutputStream writer = new FileOutputStream("D:\\consoleoutput.txt");
			writer.write(("").getBytes());
			writer.close();
			Runtime runtime = Runtime.getRuntime();
			
			    Process p1 = runtime.exec("cmd /c start D:\\AutomationFramework\\ABMSmartScript\\framework\\trialfortestng.bat "+Module.get(1));
			    InputStream is = p1.getInputStream();
			
			   // Runtime.getRuntime().exec(new String[] {"wscript.exe","D:\\AutomationFramework\\ABMSmartScript\\framework\\Scheduler\\search.vbs"});
			   
			    String file="D:\\consoleoutput.txt";
			 // Open the file
			    FileInputStream fstream = new FileInputStream(file);
			    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			    String strLine="1";
do {
	  Thread.sleep(3000l);
	  
}while((strLine = br.readLine())==null);
p1.destroy();

//.contains("Total tests run:")
// while (!((strLine = br.readLine()).contains("Total tests run:")));
			   
			    //Close the input stream
			    br.close();
			    runtime.exec("cmd /c start C:\\Users\\Swapnil.Patil\\Desktop\\batfiletokillprocess.bat");
			    Thread.sleep(5000l);
		}
		
	}

	//}


	public static void writeexcel() throws IOException
	{
		//String FilePath1 = "D:\\AutomationFramework\\ABMSmartScript\\framework\\Scheduler\\Controlfile1.xlsx";
		String FilePath1 = "D:\\AutomationFramework\\ABMSmartScript\\framework\\excelreader\\Legal\\Legal_Controlfile.xlsx";
		FileInputStream fs1 = new FileInputStream(FilePath1);
		Workbook wb1 = null;
		wb1 = new XSSFWorkbook(fs1);
		Sheet Sheet11 = wb1.getSheet("Sheet1");
		int rowCount1 = Sheet11.getLastRowNum()-Sheet11.getFirstRowNum();

		System.out.println("rowCount1===>"+rowCount1);


		for (int i = 0; i < Fcount.size(); i++) {
			Row row1 = Sheet11.getRow(i+1);
			//for(int j = 0; j < row1.getLastCellNum(); j++){
			if (i>=0) {
				for(int j = 2; j < 6; j++){

					//Fill data in row
					if(!(j==3)){
					Cell cell = row1.createCell(j);
					if(j==2){
						
						cell.setCellValue(EFlag.get(i).toString());


					}
					if(j==4){
						

						cell.setCellValue(Fcount.get(i).toString());


					}

					if(j==5){
						

						cell.setCellValue("To Column No");

						cell.setCellValue(Fcount.get(i).toString());

					}

				}
				}
			}


			fs1.close();
			FileOutputStream outputStream = new FileOutputStream(FilePath1);
			wb1.write(outputStream);
			outputStream.close();
		}

	}



	public static void readexcel() throws IOException {

		try{
		
			String FilePath = "D:\\AutomationFramework\\ABMSmartScript\\framework\\Scheduler\\Controlfile_swap.xlsx";
			
			FileInputStream fs = new FileInputStream(FilePath);
			Workbook wb = null;
			wb = new XSSFWorkbook(fs);
			Sheet Sheet1 = wb.getSheet("Sheet1");
			int rowCount = Sheet1.getLastRowNum()-Sheet1.getFirstRowNum();
			System.out.println("rowCount===>"+rowCount);
			int i = 0;
			for (i = 0; i < rowCount; i++) {

				Row row = Sheet1.getRow(i);

				//Create a loop to print cell values in a row
				for (int j = 0; j < row.getLastCellNum(); j++) {

					//Print Excel data in console

					System.out.print(row.getCell(j).getStringCellValue().toString()+"|| ");
					if(j==1)
					{
						method.add(row.getCell(1).getStringCellValue().toString());    
					}
					if(j==2)
					{
						ExecutionFlag.add(row.getCell(2).getStringCellValue().toString());
					}
					if(j==4)
					{
						FC.add(row.getCell(4).getStringCellValue().toString());
					}
					if(j==5)
					{
						TC.add(row.getCell(5).getStringCellValue().toString());
						//TC[0]=(row.getCell(5).getStringCellValue().toString());
						//  System.out.println("TC===>"+ TC[i]);
					}
					if(j==6)
					{
						status.add(row.getCell(8).getStringCellValue().toString());
					}
					 if (j==8) {
						 
						 Module.add(row.getCell(9).getStringCellValue().toString());
					}
					
					
				}
				System.out.println("method+"+method);
				System.out.println("ExecutionFlag+"+ExecutionFlag);
				System.out.println("FC+"+FC);
				System.out.println("TC+"+TC);
				System.out.println("status"+status);
				System.out.println("Module"+Module);
				System.out.println();


			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}


	}










	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		readexcel();
		//writeexcel();
		manupulation();
		//writeexcel();	
	}

}
