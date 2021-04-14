title Chromedriver Process Killer
echo off
echo welcome



taskkill /f /im "s2.exe"
taskkill /f /im "chromedriver36.exe"
taskkill /f /im "chrome.exe"
del %TEMP%\*.* /f /s /q
ping  1.1.1.1 -n 1 -w 4000 > nul
 
exit
