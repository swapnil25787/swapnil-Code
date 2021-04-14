package api;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class TestNgInvocationListener extends CommonUtilsAPI implements IInvokedMethodListener {

	@Override
	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
	
		System.out.println("In transform report");
		CurrentinvoCount = arg0.getTestMethod().getCurrentInvocationCount(); 
		
		
		//System.out.println("Invocation Count:::: "+CurrentinvoCount);o
		//System.out.println("Total invocation count is::: "+arg0.getTestResult().getMethod().getInvocationCount());
		
	}

	@Override
	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
		// TODO Auto-generated method stub
		
		//System.out.println("Number of columns are beforeinvoker:"+NumberOfColumns);
		CurrentinvoCount = arg0.getTestMethod().getCurrentInvocationCount(); 
		CurrentMethodName = arg0.getTestResult().getMethod().getMethodName();
	//System.out.println("CurrentinvoCount===>"+CurrentinvoCount);
	//System.out.println("CurrentMethodName===>"+CurrentMethodName);
		
		////System.out.println("Invocation Count is:::  "+arg0.getTestMethod().getCurrentInvocationCount());
		//System.out.println("Invoked method name is:: "+arg0.getTestResult().getMethod().getMethodName());
		
	}

	
	
	
}