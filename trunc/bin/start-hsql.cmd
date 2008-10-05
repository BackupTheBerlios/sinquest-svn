@echo off
setlocal

java -classpath "..\lib\hsqldb.jar" org.hsqldb.Server -port 9003 -database ..\dba\hypersonic\hsqlDB"

if "%1"=="CLOSECMD" exit

endlocal
