# ProjetLong_GenieLogiciel

##Compiler et executer le Boggle à la main


`find -name "*.java" > sources.txt`


`javac -sourcepath sources -d classes @sources.txt`


`java -classpath classes/:lib/sqlite-jdbc-3.8.11.2.jar boggle.gui.core.Game`


##TODO

- [ ] Enregistrer dans la BDD les scores à la fin
- [ ] Enregistrer les options dans le fichier
- [ ] Corriger l'IA niveau 2
- [ ] Faire un readme pour la compilation et l'execution du programme
- [ ] Ajouter captures d'écrans sur le manuel utilisateur HTML
- [ ] Régler probleme temps, déroule trop vite quand multijoueur
- [ ] POurvoir changer taille grille
