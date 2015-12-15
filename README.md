# ProjetLong_GenieLogiciel

##Compiler et executer le Boggle à la main


`find -name "*.java" > sources.txt`


`javac -sourcepath sources -d classes @sources.txt`


`java -classpath classes/:lib/sqlite-jdbc-3.8.11.2.jar boggle.gui.core.Game`
