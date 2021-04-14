'Dim infoResult
'infoResult = "Not Found"
'Sub outputMessage()
'CONST ForReading = 1
'Dim File1
'Dim FileToRead
'Dim strLine

'Dim strDir, objFile, returnvalue
'strDir = "d:\"
'File1 = "consoleoutput.txt"
'FileToRead = strDir & File1
'Set objFSO = CreateObject("Scripting.FileSystemObject")
'Set objFile = objFSO.OpenTextFile(FileToRead, ForReading)
'Do Until (infoResult = "Not Found")
'Do Until objFile.AtEndOfStream
'    strLine = objFile.ReadLine
    
      '  if instr(strLine, "Total tests run:") <> 0 then
'          if instr(strLine, "Total tests run:") <> 0 then
'                infoResult = "Found"
           
'                exit do
           ' end if
           
'        else
'            infoResult = "Not Found"
         
            
        '   Set objFile = objFSO.OpenTextFile(FileToRead, ForReading) 
'        end if
'Loop
'Loop
'objFile.Close
'Wscript.Echo infoResult     

'End Sub

'If (infoResult = "Not Found") Then
'Wscript.Echo infoResult  

'Call outputMessage()
'Wscript.Echo "hi"
' ElseIf (infoResult = "Found") Then
'           Wscript.Echo infoResult   
'        End If

'  If (infoResult = "Not Found") Then
'            Call outputMessage()
'            End If


 
 
 
 
 
 
 
 
 
 
 
 
 
 '''''''''''''''''''''''''''
 CONST ForReading = 1
Dim File1
Dim FileToRead
Dim strLine
Dim infoResult
infoResult = "Not Found"
Dim strDir, objFile, returnvalue
strDir = "d:\"
File1 = "consoleoutput.txt"
FileToRead = strDir & File1
Do Until infoResult = "Found"
Set objFSO = CreateObject("Scripting.FileSystemObject")
Set objFile = objFSO.OpenTextFile(FileToRead, ForReading)

Do Until objFile.AtEndOfStream
    strLine = objFile.ReadLine
    Wscript.Echo strLine 
        if instr(strLine, "Total tests run:") <> 0 then
          if instr(strLine, "Total tests run:") <> 0 then
                infoResult = "Found"
            Wscript.Echo infoResult    
             Wscript.Echo infoResult    
                exit Do
                exit do
            end if
           
        else
            infoResult = "Not Found"
            
        end If
        
Loop
objFile.Close
 'Wscript.Echo infoResult     
Set objFSO = Nothing
Set objFile = Nothing
'Wscript.sleep 60000
Loop