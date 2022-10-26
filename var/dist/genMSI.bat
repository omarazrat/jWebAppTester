title Instalacion WebAppTester
set "VERSION=0.3.10"
set "VERSION_FULL=%VERSION%-SNAPSHOT"
del /Q /S image
mkdir image
cd ..\..\WebAppTester
call mvn -Dmaven.test.skip=true install dependency:copy-dependencies -DoutputDirectory=../var/dist/image
echo on
copy target\WebAppTester-%VERSION_FULL%.jar ..\var\dist\image
cd ..\var\dist
jpackage --verbose -n jWebAppTester --app-version %VERSION% --description "selenium Web forms Tester" --icon ..\..\WebAppTester\src\main\resources\icons\selenium.ico --vendor "Nestor Arias" --input image --app-content "drivers\chromedriver_win32\chromedriver.exe,drivers\edgedriver_win64\msedgedriver.exe,drivers\geckodriver-v0.32.0-win64\geckodriver.exe,drivers\operadriver_win64\operadriver.exe" --main-jar WebAppTester-%VERSION_FULL%.jar --win-dir-chooser --win-menu --win-per-user-install --win-shortcut-prompt -t msi
del /Q /S image
@pause