
package api;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.ISuite;
import org.testng.annotations.ITestAnnotation;
import org.testng.annotations.Parameters;
import org.testng.internal.annotations.IAnnotationTransformer;
import org.testng.internal.annotations.ITest;

import excelreader.excelScenarioReader;
import api.CommonUtilsAPI;





public class MyTransformer extends CommonUtilsAPI implements IAnnotationTransformer
{
	public static int counter =1;
	excelScenarioReader ESR = new excelScenarioReader();

	@Override
	public void transform(ITestAnnotation annotation, Class testClass,
			Constructor testConstructor, Method testMethod) {  

		NumberOfColumns = 0;
		currenttestmethod=null;
		System.out.println("In transform report");
		////System.out.println("currenttestmethod+"+currenttestmethod);
		tocolumn=0;
		fromcolumn=0;
		
		log.info("I am in My Transformer");

		currenttestmethod = testMethod.getName();
		////System.out.println("currenttestmethod+"+currenttestmethod);
		String currentclassname = testMethod.getDeclaringClass().toString();
		
		////System.out.println("currentclassname=>"+currentclassname);
		
		Pattern classname = Pattern.compile("(?<=class com.abm.mainet.)\\s*(.*)\\s*(?=\\.)");
		
		Matcher matcher = classname.matcher(currentclassname);
		
		if(matcher.find())
		{
			////System.out.println("classsname=>"+matcher.group(1));
		}
		
		////System.out.println("classsname upper case=>"+matcher.group(1).toUpperCase());
		
		if(counter==1)
		{
			ESR.excelScenarioreader(mGetPropertyFromFile(matcher.group(1).toUpperCase()));
		}
		
		////System.out.println("current test method is::: "+currenttestmethod);

		String tocol = hscenariomap.get(testMethod.getName()).get(3);
		tocolumn = Integer.parseInt(tocol);
		
		/*String tocol = hscenariomap.get(testMethod.getName()).get(3);
		tocolumn = Integer.parseInt(tocol);*/
		
		String tocol1 = hscenariomap.get(testMethod.getName()).get(3);
		tocolumn = Integer.parseInt(tocol1);
		
		////System.out.println("tocolumn"+tocolumn);
		String fromcol = hscenariomap.get(testMethod.getName()).get(2);
		fromcolumn = Integer.parseInt(fromcol);
		////System.out.println("fromcolumn"+fromcolumn);

		NumberOfColumns = (tocolumn-fromcolumn)+1;

		counter++;
		////System.out.println(counter);
		////System.out.println("currenttestmethod+"+currenttestmethod);
		if (currenttestmethod.equals(testMethod.getName())) {

			Boolean enable =false;

			////System.out.println("No of columns using new method is::: "+NumberOfColumns);

			//annotation.setInvocationCount(NumberOfColumns);
			annotation.setInvocationCount(NumberOfColumns);
			if(hscenariomap.get(testMethod.getName()).get(0).toString().equalsIgnoreCase("Yes"))
			{
				annotation.setEnabled(true);

				int prpty = Integer.parseInt(hscenariomap.get(testMethod.getName()).get(1));

				annotation.setPriority(prpty);

				System.err.println("priority set "+prpty+" for method "+testMethod.getName());
			}

			else
			{
				////System.out.println("Test method not to be executed "+testMethod.getName());
			}
		}
	}
}
