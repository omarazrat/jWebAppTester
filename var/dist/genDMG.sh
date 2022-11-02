#Archivo de instalacion para Macintosh
VERSION=0.3.10
VERSION_FULL=$VERSION-SNAPSHOT
export M2_HOME=~/work/apache-maven-3.8.6
PATH="${M2_HOME}/bin:${PATH}"
export PATH
rm -fr image
mkdir image
cd ../../WebAppTester
mvn -Dmaven.test.skip=true install dependency:copy-dependencies -DoutputDirectory=../var/dist/image
cp target/WebAppTester-$VERSION_FULL.jar ../var/dist/image
cd ../var/dist
cp drivers/* image/
jpackage --verbose -n jWebAppTester --java-options "-Duser.dir=\$HOME" --app-version 1$VERSION --description "selenium Web forms Tester" --icon ../../WebAppTester/src/main/resources/icons/selenium.icns --vendor "Nestor Arias" --input image --main-jar WebAppTester-$VERSION_FULL.jar --mac-package-identifier bf2f74b3-7683-40b0-91ee-74ff0afc40a5 -t dmg
rm -fr image
mv jWebAppTester-1$VERSION.dmg jWebAppTester-${VERSION}_Mac64.dmg
