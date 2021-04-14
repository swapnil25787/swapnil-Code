Dim Ftime,ST,strVariable1,strVariable2,strVariable3,myxl,mysheet,objWSHShell,Row,Col
dim j
j=4


strVariable1 = "CD /d D:\AutomationFramework\ABMSmartScript\framework" 
strVariable2 = "trialfortestng.bat " 
Set objWSHShell = WScript.CreateObject("WScript.Shell") 


    strVariable3 = "Legal" 

  


    
   


   objWSHShell.Run "cmd.exe", 1, False 
    WScript.Sleep 1000 
    objWSHShell.AppActivate "C:\Windows\system32\cmd.exe" 
 WScript.Sleep 1000 
    objWSHShell.SendKeys strVariable1 
 WScript.Sleep 1000 
    objWSHShell.SendKeys "{ENTER}" 
 WScript.Sleep 1000 
    objWSHShell.SendKeys strVariable2 & strVariable3 
 WScript.Sleep 1000 
 WScript.Sleep 1000 
    objWSHShell.SendKeys "{ENTER}" 

