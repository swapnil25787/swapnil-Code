package errorhandling;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import api.CommonUtilsAPI;

public class InvokedMethodListener implements IInvokedMethodListener {

	CommonUtilsAPI cmon = new CommonUtilsAPI();
	public static int alreadypastedexception=1;

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub

		cmon.inAtTest=false;

		MainetCustomExceptions.result = new StringBuilder();

		if(Boolean.valueOf(cmon.mGetPropertyFromFile("ContinueExecutionOnError")) == false && cmon.continueExec)
		{
			cmon.inAtTest=true;
			alreadypastedexception++;

			System.out.println("Skipping Execution due to Exception");
			throw new MainetCustomExceptions("Skipping Execution as Continue on Execution Flag set to FALSE");

		}

		else
		{
			System.out.println("Continue with Execution even if Exception occurs");
		}


	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub

	}

}
