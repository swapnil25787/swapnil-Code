package com.abm.mainet.bnd;

import java.util.Map;

import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;
import org.testng.collections.Maps;

public class DeathRegCertCustomErrorMessages extends SoftAssert{
	
	
	  public static Map<AssertionError, IAssert<?>> deathReg_m_errors = Maps.newLinkedHashMap();
	

	  
	  protected void doAssert(IAssert<?> a) {
		    onBeforeAssert(a);
		    try {
		      a.doAssert();
		      onAssertSuccess(a);
		    } catch (AssertionError ex) {
		      onAssertFailure(a, ex);
		      deathReg_m_errors.put(ex, a);
		    } finally {
		    
		      onAfterAssert(a);
		     
		     
		    }
		  }
		 
	  

	  public void assertAll(){
		  String message;
			if(!deathReg_m_errors.isEmpty()){
				
				 StringBuilder sb = new StringBuilder("");
				 
				/*boolean check;
				deathReg_m_errors.clear();
				check=DeathRegCertCustomErrorMessges.deathReg_m_errors.isEmpty();
				System.out.println("Check:::::::::::::::::::: "+check);*/

				 
			      boolean first = true;
			     
			      for (Map.Entry<AssertionError, IAssert<?>> ae : deathReg_m_errors.entrySet()) {
			    
			    	  
			        if (first) {
			        	
			          first = false;
			          
			        } 
			        else {
			        	
			          sb.append("");
			        }
			       
			        sb.append("#####");
			        
			      
			        message = ae.getValue().getMessage();
			        if (message != null) {
			          sb.append(message).append("-----");
			          
			        }
			        
			        //noinspection ThrowableResultOfMethodCallIgnored
			        //sb.append(ae.getKey().getMessage());
			       // deathReg_m_errors.clear();
			        //deathReg_m_errors.remove(ae);
			        
			      }
			    
			      sb.append("");
			      throw new AssertionError(sb.toString());
			      
			      
			}
			
			
		}

}
