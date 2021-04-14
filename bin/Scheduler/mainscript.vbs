Dim Ftime,ST,strVariable1,strVariable2,strVariable3,myxl,mysheet,objWSHShell,Row,Col
dim j
j=4

FTime = Right("0" & Hour(Time), 2) & ":" & Right("0" & Minute(Time), 2) 
'MsgBox FTime
strVariable1 = "CD /d D:\AutomationFramework\ABMSmartScript\framework" 
strVariable2 = "trialfortestng.bat " 
Set objWSHShell = WScript.CreateObject("WScript.Shell") 

Set myxl = createobject("excel.application")
 
myxl.Workbooks.Open "D:\AutomationFramework\ABMSmartScript\framework\Scheduler\Task.xlsx"
 
myxl.Application.Visible = true

set mysheet = myxl.ActiveWorkbook.Worksheets("Sheet1")

 
Row=mysheet.UsedRange.Rows.Count


Col=mysheet.UsedRange.columns.count
 

For  i= 3 to Row-1
    
ST=mysheet.cells(i,4).value
SD=mysheet.cells(i,5).value
flag=mysheet.cells(i,2).value
'MsgBox ST
'If FTime=ST AND flag = "Y" Then
If (FTime=ST AND flag = "Y") Then


    strVariable3 =  mysheet.cells(i,1).value
'MsgBox strVariable3
  

End If
    
   
Next

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
mysheet.cells(i,9).value ="executed"
myxl.ActiveWorkbook.Save
 

myxl.ActiveWorkbook.Close
 

myxl.Application.Quit
 
Set mysheet =nothing
Set myxl = nothing


'AND DT=SD