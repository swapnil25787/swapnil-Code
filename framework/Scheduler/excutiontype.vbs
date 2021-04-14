dim myxl,mysheet,flag,Row,Col,i
i=0
Set myxl = createobject("excel.application")

myxl.Workbooks.Open "D:\AutomationFramework\ABMSmartScript\framework\Scheduler\Controlfile.xlsx"
myxl.Application.Visible = true
set mysheet = myxl.ActiveWorkbook.Worksheets("Sheet1")
Row=mysheet.UsedRange.Rows.Count
Col=mysheet.UsedRange.columns.count
Wscript.Echo Row
Wscript.Echo Col
Dim TC(Row)

TC(0)="start"
Dim FC(Row)
FC(0)="start"
Dim Methodname(Row)
Methodname(0)="start"
Dim ISHEET(Row)
ISHEET(0)="start"
Dim DSHEET(Row)
DSHEET(0)="start"
Dim priority(Row)
priority(0)="start"
For  i= 1 to Row
flag=mysheet.cells(i,3).value
'if flag = "yes" Then
Methodname(i)=mysheet.cells(i,2).value
priority(i)=mysheet.cells(i,4).value
FC(i)=mysheet.cells(i,5).value

Wscript.Echo FC 
TC(i)=mysheet.cells(i,6).value
ISHEET(i)=mysheet.cells(i,7).value
DSHEET(i)=mysheet.cells(i,8).value
Wscript.Echo Methodname 
Wscript.Echo FC 
Wscript.Echo TC 
Wscript.Echo ISHEET 
Wscript.Echo DSHEET 
'mysheet.cells(i,8).value ="Scheduled"
'End If



Next

myxl.ActiveWorkbook.Save
myxl.ActiveWorkbook.Close
myxl.Application.Quit
Set mysheet =nothing
Set myxl = nothing