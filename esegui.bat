set TOMCAT_HOME=c:\\xampp\\tomcat
set CLASS_DIR=WebContent/WEB-INF/classes
set SRC_DIR=src
set PATH=%PATH%;C:/Program Files/java/jdk-21/bin;
javac -cp %TOMCAT_HOME%/lib/servlet-api.jar;./%SRC_DIR% -d %CLASS_DIR% %SRC_DIR%/servlet/*.java
javac -Xlint -d %CLASS_DIR% -cp ./%SRC_DIR% %SRC_DIR%/librerie/*.java
jar cfv deploy/ScuolaServletApp.war -C WebContent .
copy .\\deploy\\ScuolaServletApp.war %TOMCAT_HOME%\\webapps\\ScuolaServletApp.war