title trialfortestng
echo off
echo welcome
cd D:\AutomationFramework\ABMSmartScript
set ProjectPath=D:\AutomationFramework\ABMSmartScript
echo %ProjectPath%
set classpath=%ProjectPath%\bin;%ProjectPath%\lib\*
echo %classpath%
java org.testng.TestNG %ProjectPath%\test-output\main-suite%1.xml
ping  1.1.1.1 -n 1 -w 300000 > nul
'java org.testng.TestNG %ProjectPath%\test-output\main-suiteRTI.xml