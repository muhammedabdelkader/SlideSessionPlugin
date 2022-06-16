@echo off 

rem del burp.jar
cd out
cd production 
cd slideSessionPlugin
jar cf  ../../../burp.jar ./burp/mainfest.txt ./com/slideSessionPlugin/data/*.class ./burp/*.class ./com/slideSessionPlugin/gui/*.class ./com/slideSessionPlugin/json/*.class ./com/slideSessionPlugin/logic/*.class

