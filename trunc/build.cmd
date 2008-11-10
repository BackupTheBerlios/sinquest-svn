@echo off
setlocal

set ANT_HOME=C:\Develop\apache-ant-1.7.1
set JAVA_HOME=C:\Program Files\Java\jdk1.6.0_10
set PATH=%PATH%;%ANT_HOME%\bin

ant %*