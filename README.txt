###################################################################################
#                                 Projet Boggle                                   #
###################################################################################

-----------------------------------------------------------------------------------

Pour compiler le Boggle à la main, il faut ce placer dans le repertoire du projet 
et effectuer les commandes suivantes :

mkdir classes
find -name "*.java" > sources.txt
javac -sourcepath sources -d classes @sources.txt
cp -R img/ classes/

-----------------------------------------------------------------------------------

Pour éxecuter :

java -classpath classes/:lib/sqlite-jdbc-3.8.11.2.jar boggle.gui.core.Game

-----------------------------------------------------------------------------------

