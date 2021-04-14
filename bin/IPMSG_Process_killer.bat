title IPMSG Process Killer
echo off
echo welcome
taskkill /f /im "ipmsg.exe"
ping  1.1.1.1 -n 1 -w 5000 > nul
exit
