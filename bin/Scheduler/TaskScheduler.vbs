dim myxl,mysheet,flag,Row,Col,i,j,SC,ST,SD,TN,TR,parameters,objShell,a,objWSHShell
Set objShell = WScript.CreateObject ("WScript.shell")
Set objWSHShell = WScript.CreateObject("WScript.Shell") 
Set myxl = createobject("excel.application")

myxl.Workbooks.Open "D:\AutomationFramework\ABMSmartScript\framework\Scheduler\Task.xlsx"
myxl.Application.Visible = true
set mysheet = myxl.ActiveWorkbook.Worksheets("Sheet1")
Row=mysheet.UsedRange.Rows.Count
Col=mysheet.UsedRange.columns.count
For  i= 3 to Row
flag=mysheet.cells(i,2).value
if flag = "Y" Then
SC=mysheet.cells(i,3).value
ST=mysheet.cells(i,4).value
SD=mysheet.cells(i,5).value
TN=mysheet.cells(i,6).value
TR=mysheet.cells(i,7).value


mysheet.cells(i,8).value ="Scheduled"
parameters = "/create /sc "& SC &" /ST "& ST &" /sd " & SD & " /TN " & TN & " /tr "& TR &" /F "
objShell.Run "C:\Windows\System32\schtasks.exe " & parameters
  WScript.Sleep 5000 


   'a="SCHTASKS"
   'objWSHShell.Run "cmd.exe", 1, False 
    WScript.Sleep 500 
   ' objWSHShell.AppActivate "C:\Windows\system32\cmd.exe" 
    'objWSHShell.SendKeys strVariable1 
    'objWSHShell.SendKeys "{ENTER}" 
   ' objWSHShell.SendKeys a &parameters
    'objWSHShell.SendKeys "{ENTER}" 

End If



Next

myxl.ActiveWorkbook.Save
myxl.ActiveWorkbook.Close
myxl.Application.Quit
Set mysheet =nothing
Set myxl = nothing