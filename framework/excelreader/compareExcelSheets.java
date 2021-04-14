package excelreader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;



public class compareExcelSheets {

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




	public static XSSFRow row1 =null;










	ArrayList<String> compareValue=new ArrayList<>();
	String vvalue;

	public static Map<String, ArrayList<String>> compareOne = new LinkedHashMap<String, ArrayList<String>>();
	public static Map<String, ArrayList<String>> compareTwo = new LinkedHashMap<String, ArrayList<String>>();

	public static Map<String, ArrayList<String>> datawritten = new LinkedHashMap<String, ArrayList<String>>();
	public static ArrayList<String> datatobewritten = new ArrayList<String>();
	int NoOfColumns;
	ResultSet rs1;


	public static void main(String [] args)
	{


		compareExcelSheets ces =  new compareExcelSheets();
		/*ces.compareExcelOne("C:\\Users\\sunil.sonmale\\Desktop\\Sheet01forComparison.xlsx");
		ces.compareExcelTwo("C:\\Users\\sunil.sonmale\\Desktop\\Sheet02forComparison.xlsx");
		if(compareOne.equals(compareTwo))
		{
			System.err.println("Maps Equal");			
		}
		else{
			System.err.println("Maps Not Equal");

			for (Entry<String, ArrayList<String>> entry : compareOne.entrySet()) {
				String key = entry.getKey();
				System.out.println("key " + key + ": "
						+ entry.getValue().equals(compareTwo.get(key)));
			}
		}*/

		ces.dbtesting();
	}



	public void compareExcelOne(String filename)	
	{
		try{

			FileInputStream inputStream = new FileInputStream(new File(filename));

			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();
			int i=1;

			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();

				compareValue=new ArrayList<>();



				while (cellIterator.hasNext()) {
					//valueCell = row.getCell(Integer.valueOf(j),Row.CREATE_NULL_AS_BLANK);
					XSSFCell cell = (XSSFCell) cellIterator.next();

					switch(cell.getCellType())
					{

					case XSSFCell.CELL_TYPE_BLANK :
						//value = valueCell.getRawValue();
						value = "?";
						break;

					case XSSFCell.CELL_TYPE_NUMERIC :
						value = cell.getRawValue();
						break;

					case XSSFCell.CELL_TYPE_STRING :
						value = (cell.getStringCellValue());
						break;
					}
					System.out.println("value is:::"+value);
					System.out.print(" - ");

					compareValue.add(value);


				}
				System.out.println();



				compareOne.put(Integer.toString(i), compareValue);

				i++;
			}

			workbook.close();
			inputStream.close();

			for (Entry<String, ArrayList<String>> entry : compareOne.entrySet()) {
				String key1 = entry.getKey();
				ArrayList<String> value1 = entry.getValue();
				System.out.println("Key is ::"+key1 );
				System.out.println("Value is::::::: "+value1);
				// do stuff
			}


		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void compareExcelTwo(String filename)	
	{
		try{

			FileInputStream inputStream = new FileInputStream(new File(filename));

			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();
			int i=1;

			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();

				compareValue=new ArrayList<>();



				while (cellIterator.hasNext()) {
					//valueCell = row.getCell(Integer.valueOf(j),Row.CREATE_NULL_AS_BLANK);
					XSSFCell cell = (XSSFCell) cellIterator.next();

					switch(cell.getCellType())
					{

					case XSSFCell.CELL_TYPE_BLANK :
						//value = valueCell.getRawValue();
						value = "?";
						break;

					case XSSFCell.CELL_TYPE_NUMERIC :
						value = cell.getRawValue();
						break;

					case XSSFCell.CELL_TYPE_STRING :
						value = (cell.getStringCellValue());
						break;
					}
					System.out.println("value is:::"+value);
					System.out.print(" - ");

					compareValue.add(value);


				}
				System.out.println();



				compareTwo.put(Integer.toString(i), compareValue);

				i++;
			}

			workbook.close();
			inputStream.close();

			for (Entry<String, ArrayList<String>> entry : compareTwo.entrySet()) {
				String key1 = entry.getKey();
				ArrayList<String> value1 = entry.getValue();
				System.out.println("Key is ::"+key1 );
				System.out.println("Value is::::::: "+value1);
				// do stuff
			}


		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public ResultSet dbtesting()
	{
		try {

			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			}
			catch(ClassNotFoundException ex) {
				System.out.println("Error: unable to load driver class! " +ex);
				System.exit(1);
			}
			catch(IllegalAccessException ex) {
				System.out.println("Error: access problem while loading!");
				System.exit(2);
			}
			catch(InstantiationException ex) {
				System.out.println("Error: unable to instantiate driver!");
				System.exit(3);
			}

			String URL = "jdbc:oracle:thin:@103.241.183.246:1521:rac";
			String USER = "live_PATNA_PROD_TEST_read";
			String PASS = "live_PATNA_PROD_TEST_read";
			Connection conn = DriverManager.getConnection(URL, USER, PASS);

			//	Connection conn = DriverManager.getConnection("jdbc:odbc:https://192.168.33.101:1521:rac");
			Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


			/*String query = " select\r\n" + 
					"         a.app_name, concat(concat(a.rti_no, '-'), to_char(a.appln_date, 'DD/MM/YYYY')) as rti_noDATE,\r\n" + 
					"         a.subject, c.CPD_RTI_STATUS,\r\n" + 
					"         concat(concat(b.loi_no, '-'), to_char(b.loi_date, 'DD/MM/YYYY')) as loi_noDATE,\r\n" + 
					"         b.onlineofflinecheck,\r\n" + 
					"         concat(concat(a.challannumber, '-'), to_char(a.updated_date, 'DD/MM/YYYY HH:MM')) as challan_noDATE,\r\n" + 
					"        c.forward_pio_name, \r\n" + 
					"        concat(concat(c.otr_reason, '-'), to_char(c.replied_on, 'DD/MM/YYYY')) as reply_remarksDATE,\r\n" + 
					"        concat(concat(a.finaldispatchmode, '-'), to_char(a.dispatchdate, 'DD/MM/YYYY')) as dispatchmode_modeDATE\r\n" + 
					"        \r\n" + 
					"        \r\n" + 
					"      from\r\n" + 
					"          TB_RTI_APPLICATION_MAS a \r\n" + 
					"      left outer join\r\n" + 
					"       tb_rti_loi_mas b on\r\n" + 
					"             a.rti_id=b.RTI_ID \r\n" + 
					"      inner join \r\n" + 
					"         TB_RTI_APPLICATION_STATUS c on\r\n" + 
					"              a.RTI_ID=c.RTI_ID \r\n" + 
					"      where\r\n" + 
					"          c.CPD_RTI_STATUS in ('189516', '189517','189518','189519','189520', '189521', '189522', \r\n" + 
					"          '189523', '189524', '189525', '248451', '252134', '266526','266527')  \r\n" + 
					"          and a.appln_date between '01-Aug-2015' and '01-Aug-2016'\r\n" + 
					"         and a.orgid=711\r\n" + 
					"          and a.isdeleted='N' \r\n" + 
					"      order by\r\n" + 
					"          a.appln_date asc";*/


			String query = "select\r\n" + 
					"\r\n" + 
					"a.app_name,\r\n" + 
					"a.rti_no,\r\n" + 
					"to_char(a.appln_date, 'DD/MM/YYYY'),\r\n" + 
					"a.subject,\r\n" + 
					"c.cpd_rti_status,\r\n" + 
					"b.loi_no,\r\n" + 
					"to_char(b.loi_date, 'DD/MM/YYYY'),\r\n" + 
					"a.onlineofflinecheck,\r\n" + 
					"f.challan_no,\r\n" + 
					"to_char(f.challan_date, 'DD/MM/YYYY HH:MM'),\r\n" + 
					"to_char(f.challan_date, 'DD/MM/YYYY'),\r\n" + 
					"c.forward_pio_name,\r\n" + 
					"c.replied_on,\r\n" + 
					"c.otr_reason,\r\n" + 
					"a.finaldispatchmode,\r\n" + 
					"to_char(a.dispatchdate, 'DD/MM/YYYY HH:MM'),\r\n" + 
					"e.cpd_value,\r\n" + 
					"to_char(c.updated_date,'DD/MM/YYYY'),\r\n" + 
					"c.subrule8,\r\n" + 
					"c.partialapprovalflag,\r\n" + 
					"c.subrule9,\r\n" + 
					"a.loiapplicable,\r\n" + 
					"g.empname,\r\n" + 
					"e.cpd_desc,\r\n" + 
					"h.cpd_desc,\r\n" + 
					"i.tran_cm_id,\r\n" + 
					"to_char(i.lmoddate, 'DD/MM/YYYY HH:MI'),\r\n" + 
					"b.onlineofflinecheck,\r\n" + 
					"b.loipaymentflag,\r\n" + 
					"l.rl_rcptno,\r\n" + 
					"to_char(l.rl_date, 'YYYY-MM-DD HH:MI:SS')\r\n" + 
					"\r\n" + 
					"                \r\n" + 
					"      from\r\n" + 
					"          TB_RTI_APPLICATION_MAS a \r\n" + 
					"      left outer join\r\n" + 
					"       tb_rti_loi_mas b on\r\n" + 
					"             a.rti_id=b.RTI_ID \r\n" + 
					"      inner join \r\n" + 
					"         TB_RTI_APPLICATION_STATUS c on\r\n" + 
					"              a.RTI_ID=c.RTI_ID \r\n" + 
					"              \r\n" + 
					"              left outer join\r\n" + 
					"              \r\n" + 
					"              tb_comparam_det e \r\n" + 
					"              on c.cpd_rti_status = e.cpd_id \r\n" + 
					"              \r\n" + 
					"              \r\n" + 
					"              left outer join\r\n" + 
					"              \r\n" + 
					"              tb_comparam_det h \r\n" + 
					"              on a.finaldispatchmode=h.cpd_id\r\n" + 
					"              \r\n" + 
					"              \r\n" + 
					"              left outer join \r\n" + 
					"              \r\n" + 
					"              tb_challan_master f\r\n" + 
					"              \r\n" + 
					"              on a.rti_no = f.apm_application_id\r\n" + 
					"              \r\n" + 
					"              left outer join \r\n" + 
					"              \r\n" + 
					"              employee g \r\n" + 
					"              \r\n" + 
					"              on c.updated_by=g.empid\r\n" + 
					"              \r\n" + 
					"              left outer join \r\n" + 
					"              \r\n" + 
					"              tb_cm_onl_tran_mas i\r\n" + 
					"              \r\n" + 
					"              on a.rti_no=i.apm_application_id\r\n" + 
					"              \r\n" + 
					"              left outer join \r\n" + 
					"              \r\n" + 
					"              tb_rti_app_rcpt_mas l\r\n" + 
					"              \r\n" + 
					"              on a.rti_id=l.rti_no\r\n" + 
					"              \r\n" + 
					"              \r\n" + 
					"      where\r\n" + 
					"          c.CPD_RTI_STATUS in ('189516', '189517','189518','189519','189520', '189521', '189522', \r\n" + 
					"          '189523', '189524', '189525', '248451', '252134', '266526','266527')  \r\n" + 
					"          and a.appln_date between '01-Aug-2015' and '01-Aug-2016'\r\n" + 
					"         and a.orgid=711\r\n" + 
					"          and a.isdeleted='N' \r\n" + 
					"      order by\r\n" + 
					"          a.appln_date asc";





			System.out.println("Query :: " + query);

			rs1 = st.executeQuery(query);

			Pattern querypattern = Pattern.compile("(?<=select)\\s*(.*)\\s*(?=from)");

			query = query.replace("\n", "");
			query = query.replace("\r", "");
			Matcher matcher = querypattern.matcher(query);

			String[] parts=null;

			if(matcher.find())
			{
				String substring = matcher.group(1);
				System.out.println(substring);

				//substring.s

				parts = substring.split(",");
				System.out.println(parts.length);
			}


			FileInputStream fis = new FileInputStream(new File("C:\\Users\\sunil.sonmale\\Desktop\\Sheet02forComparison.xlsx"));
			XSSFWorkbook workbook = new XSSFWorkbook (fis);
			XSSFSheet sheet = workbook.getSheetAt(0);

			int xyz=1;

			System.out.println();

			//rs1.afterLast();
			int count = 0;
			boolean failureoccured=false;

			while(rs1.next())
			{
				//System.out.println(rs1.getString(2));
				System.out.println("count :: " + count++);
				datatobewritten = new ArrayList<>();

				//Adding serial no in arraylist
				////
				////
				datatobewritten.add(Integer.toString(xyz));

				//Adding Applicant name to arraylist
				////
				////
				datatobewritten.add(rs1.getString(1).trim());

				//Adding rti no and application date to arraylist
				////
				////
				String rtinoanddate = rs1.getString(2)+"-"+rs1.getString(3);
				datatobewritten.add(rtinoanddate);

				//Adding subject to arraylist
				////
				datatobewritten.add(rs1.getString(4));

				//Adding final status to arraylist
				////
				String status=rs1.getString(5);

				if(status.equals("189522"))
				{
					datatobewritten.add("Application still in 'Open' status");
				}
				else if(status.equals("189516"))
				{
					if(rs1.getString(20).equalsIgnoreCase("F"))
					{
						datatobewritten.add(rs1.getString(18)+"-"+"Fully Approved");
					}
					else
					{
						datatobewritten.add(rs1.getString(18)+"-"+"Partially Approved");
					}
				}

				else if(status.equals("189518"))
				{
					datatobewritten.add(rs1.getString(18)+"-"+"FA-Open");
				}
				else if(status.equals("248451"))
				{
					datatobewritten.add(rs1.getString(18)+"-Reopen");
				}
				else if(status.equals("189520"))
				{
					datatobewritten.add("Forward To Relevant Officer");
				}
				else if(status.equals("189525"))
				{
					if(rs1.getString(21)!=null)
					{
						datatobewritten.add(rs1.getString(18)+" - Rejected"+" -"+"Based on "+rs1.getString(19)+" of Section 8" +","+rs1.getString(21)+" of Section 9 "+". " +rs1.getString(14));
					}
					else{
						datatobewritten.add(rs1.getString(18)+" - Rejected"+" -"+"Based on "+rs1.getString(19)+" of Section 8" +"." +rs1.getString(14));
					}
				}
				else if(status.equals("189517"))
 				{
					datatobewritten.add(rs1.getString(11)+"-Closed");
				}

				else
				{
					System.out.println("No value found");
				}

				//Entering LOI No and date 

				if(status.equals("189522")||status.equals("189520")||status.equals("189525"))
				{
					datatobewritten.add("N.A.");
				}
				else if(rs1.getString(6)==null&&rs1.getString(7)==null)
				{
					if(rs1.getString(22)==null)
					{
						datatobewritten.add("");
					}
					else if(rs1.getString(22).equalsIgnoreCase("ap"))
					{
						datatobewritten.add("Failure Occured");
						failureoccured=true;
					}

					else{
						//datatobewritten.add(rs1.getString(6)+"-"+rs1.getString(7));
						datatobewritten.add("");
					}
				}
				else if(status.equals("189517")||(status.equals("189516")&&rs1.getString(22).equalsIgnoreCase("NA")))
				{
					datatobewritten.add("");
				}
				else
				{
					//datatobewritten.add(" ");
					datatobewritten.add(rs1.getString(6)+"-"+rs1.getString(7));
				}

				//Entering LOI No and date 

				if(rs1.getString(22)==null)
				{
					datatobewritten.add("N.A.");
				}

				else if(rs1.getString(22).equalsIgnoreCase("AP"))
				{
					if(rs1.getString(6)==null ||rs1.getString(7)==null)
					{
						datatobewritten.add("N.A.");
					}
					else{
						datatobewritten.add(rs1.getString(6)+"-"+rs1.getString(7));
					}
				}
				else if(rs1.getString(22).equalsIgnoreCase("NA"))
				{
					datatobewritten.add("");
				}
				else if(rs1.getString(22).equalsIgnoreCase("0"))
				{
					datatobewritten.add("N.A.");
				}



				////Payment mode

				if(rs1.getString(22)==null || rs1.getString(22).equalsIgnoreCase("NA"))
				{
					if(status.equals("189525"))
					{
						datatobewritten.add("N.A.");	
					}
					if(rs1.getString(8)==null)
					{
						datatobewritten.add("No Payment");	
					}
					else if(rs1.getString(8).equalsIgnoreCase("N"))
					{
						datatobewritten.add("Offline");	
					}
					else if(rs1.getString(8).equalsIgnoreCase("Y"))
					{
						datatobewritten.add("Online");	
					}
					else if(rs1.getString(8).equalsIgnoreCase("P"))
					{
						datatobewritten.add("Pay @ ULB Counter");	
					}
					else if(rs1.getString(8).equalsIgnoreCase("S"))
					{
						datatobewritten.add("No Payment");	
					}
					
				}
				else if(rs1.getString(22).equalsIgnoreCase("AP"))
				{
					if(rs1.getString(28)==null)
					{
						datatobewritten.add("No Payment");	
					}
					else if(rs1.getString(28).equalsIgnoreCase("N"))
					{
						datatobewritten.add("Challan");	
					}
					else if(rs1.getString(28).equalsIgnoreCase("Y"))
					{
						datatobewritten.add("Online");	
					}
					else if(rs1.getString(28).equalsIgnoreCase("P"))
					{
						datatobewritten.add("Pay @ ULB Counter");	
					}
				}
				else if(rs1.getString(22).equalsIgnoreCase("0"))
				{
					if(rs1.getString(8)==null)
					{
						datatobewritten.add("No Payment");		
					}
				   else if(rs1.getString(8).equalsIgnoreCase("N"))
					{
						datatobewritten.add("Offline");	
					}
				   else	if(rs1.getString(8).equalsIgnoreCase("Y"))
					{
						datatobewritten.add("Online");	
					}
					else if(rs1.getString(8).equalsIgnoreCase("P"))
					{
						datatobewritten.add("Pay @ ULB Counter");	
					}
				}


				///Transaction/Challan no and date
				if(status.equals("189525"))
				{
					datatobewritten.add("N.A.");	
				}
				else if(status.equals("189517"))
				{
					datatobewritten.add("N.A.");	
				}

				else if	(rs1.getString(22)==null)
				{
					/*if(status=="189525")
					{
						datatobewritten.add("N.A.");	
					}*/

					if(rs1.getString(8)==null)
					{
						datatobewritten.add("N.A.");	
					}
					else if(rs1.getString(8).equalsIgnoreCase("Y"))
					{

						if(rs1.getString(26)==null ||rs1.getString(27)==null)
						{
							datatobewritten.add("Failure Occured");
						}
						else{
							datatobewritten.add(rs1.getString(26)+"-"+rs1.getString(27));
						}

					}
					else if(rs1.getString(8).equalsIgnoreCase("N"))
					{
						if(rs1.getString(9)==null ||rs1.getString(10)==null)
						{
							datatobewritten.add("N.A.");
						}								
						else
						{
							datatobewritten.add(rs1.getString(9)+"-"+rs1.getString(10));
						}
					}
					else if(rs1.getString(8).equalsIgnoreCase("S"))
					{

						datatobewritten.add("N.A.");

					}
					else if(rs1.getString(8).equalsIgnoreCase("P"))
					{						
						datatobewritten.add(rs1.getString(30)+"/"+rs1.getString(31));						
					}


				}
				else if(rs1.getString(22)!=null) {

					if(rs1.getString(22).equalsIgnoreCase("0"))
					{
						datatobewritten.add(rs1.getString(9)+"-"+rs1.getString(10));
					}
					if(rs1.getString(22).equalsIgnoreCase("NA"))
					{
						datatobewritten.add("N.A.");
					}

					if(rs1.getString(22).equalsIgnoreCase("AP"))
					{
						if(rs1.getString(28)==null&&rs1.getString(29)==null)
						{
							datatobewritten.add("Failure Occured");

						}
						else if(rs1.getString(28).equalsIgnoreCase("N")&&rs1.getString(28).equalsIgnoreCase("Y"))
						{
							datatobewritten.add(rs1.getString(9)+"-"+rs1.getString(10));
						}
						else if(rs1.getString(28).equalsIgnoreCase("N")&&rs1.getString(28).equalsIgnoreCase("N"))
						{
							datatobewritten.add(rs1.getString(9)+"-"+rs1.getString(10));
						}
						else /*(rs1.getString(28).equals("")&&rs1.getString(28).equals(""))*/								
						{
							datatobewritten.add("Payment Pending");
						}
						/*}
					datatobewritten.add(rs1.getString(9)+"-"+rs1.getString(10));*/
					}
				}

				//// PIO Name

				if(rs1.getString(23)==null)
				{
					datatobewritten.add("N.A.");
				}
				else{
					datatobewritten.add(rs1.getString(23));
				}

				//PIO reply date and remarks
				if(datatobewritten.get(4).equals("Application still in 'Open' status"))
				{
					datatobewritten.add("N.A.");
				}
				else{
					datatobewritten.add(datatobewritten.get(4));
				}

				//Dispatch date and remarks
				if(status.equals("189516"))
				{
					if(rs1.getString(15)==null || rs1.getString(16)==null)
					{
						datatobewritten.add("N.A.");
					}
					else if(rs1.getString(15).equals("266723")){
						datatobewritten.add("");
					}
					else{
						datatobewritten.add(rs1.getString(25)+"-"+rs1.getString(16));	
					}
				}
				else if(status.equals("189517"))
				{
					if(rs1.getString(15)==null || rs1.getString(16)==null)
					{
						datatobewritten.add("N.A.");
					}
					else if(rs1.getString(15).equals("266723")){
						datatobewritten.add("");
					}
					else{
						datatobewritten.add(rs1.getString(25)+"-"+rs1.getString(16));	
					}
				}

				else if(status.equals("248451"))
				{
					if(rs1.getString(15)==null || rs1.getString(16)==null)
					{
						datatobewritten.add("N.A.");
					}
					else if(rs1.getString(15).equals("266723")){
						datatobewritten.add("");
					}
					else{
						datatobewritten.add(rs1.getString(25)+"-"+rs1.getString(16));	
					}
				}

				else {
					datatobewritten.add("N.A.");
				}



				datawritten.put(Integer.toString(xyz).trim(), datatobewritten);

				System.out.println(datawritten);
				xyz++;
			}			

			System.out.println("Count of rs1"+rs1.getRow());

			for (Entry<String, ArrayList<String>> entry : datawritten.entrySet()) {
				String key1 = entry.getKey();
				ArrayList<String> value1 = entry.getValue();

				row1= sheet.createRow(Integer.valueOf(key1)-1);
				System.out.println("Key is ::"+key1 );
				System.out.println("Value is::::::: "+value1);

				for(int i=0;i<datatobewritten.size();i++)

				{	
					XSSFCellStyle cellStyle = workbook.createCellStyle();
					cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
					cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
					cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);

					XSSFCell r1c1 = row1.createCell(i);

					r1c1.setCellValue(value1.get(i));

					r1c1.setCellStyle(cellStyle);
				}

			}

			fis.close();
			FileOutputStream fos =new FileOutputStream(new File("C:\\Users\\sunil.sonmale\\Desktop\\Sheet02forComparison.xlsx"));
			workbook.write(fos);
			fos.close();
			System.out.println("Done"); 
			conn.close();		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e);	
		}
		return rs1;
	}

}
