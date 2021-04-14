package com.abm.mainet.legal;

import java.io.PrintWriter;
import java.util.Map;

import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;
import org.testng.collections.Maps;

public class LegalCustomErrorMessages extends SoftAssert{
	
	public PrintWriter out;
	public static Map<AssertionError, IAssert<?>> legal_m_errors = Maps.newLinkedHashMap();
	
	protected void doAssert(IAssert<?> a) {
	    onBeforeAssert(a);
	    try {
	      a.doAssert();
	      onAssertSuccess(a);
	    } catch (AssertionError ex) {
	      onAssertFailure(a, ex);
	      legal_m_errors.put(ex, a);
	    } finally {
	    	
	      onAfterAssert(a);
      
	    }
	  }
	 
	
	public void assertAll() {
		String message;
		if(!legal_m_errors.isEmpty()){
			
			 StringBuilder sb = new StringBuilder("");
			 
		      boolean first = true;
		      for (Map.Entry<AssertionError, IAssert<?>> ae : legal_m_errors.entrySet()) {
		    	  
		        if (first) {
		        	
		          first = false;
		          
		        }
		        else {
		          sb.append("");
		        }
		        sb.append("#####");
		        
		        message = ae.getValue().getMessage();
		        System.out.println("MESSAGE::::"+message);
		        if (message != null) {
		          sb.append(message).append("-----");
		       
		        }
		       
		        //noinspection ThrowableResultOfMethodCallIgnored
		        //sb.append(ae.getKey().getMessage());
		       //birthReg_m_errors.clear();
		      }
		      String message2="";
		      message=message2;
		      System.out.println("New message:::::"+message);
		      throw new AssertionError(sb.toString());
		      
		}
		
	}
	

}
