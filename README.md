# Projet F1 - Bootcamp de Clyfton 

Voici les explications sur mon projet F1.

## Démarrage

Pour commencer, j'ai fait un peu de configuration pour travailler proprement :
- J'ai créé mon propre repo GitHub (`projet-F1_Clyfton`).
- J'ai enlevé le lien vers votre dépôt pour ne pas faire de bêtises.
- J'ai tout pushé sur mon nouveau repo en me connectant avec le CLI GitHub.

Et aussi je n'ai absolument pas touché au fichier de données brut (`donnees/resultats.csv`), comme demandé dans les consignes.

## Etape 1 : Nettoyage en Python

Ensuite, je suis passé au premier maillon en Python. L'idée c'était juste de lire les données et de les nettoyer pour que le programme Java puisse s'en servir après.

Ce que j'ai fait dans le notebook :
- J'ai écrit une petite fonction pour convertir les temps (qui étaient sous forme de texte, genre "1:33.614") en secondes.
- J'ai lu le fichier CSV, en faisant bien attention de transformer les abandons en position "0".
- Et enfin, j'ai sauvegardé tout ça dans un nouveau fichier `courses_propres.csv` du côté du dossier Java. 

Les tests passent tous sans problème, donc c'est validé pour le Python !

## Etape 2 : Le moteur de calcul en Java

Une fois les données propres, je suis passé au maillon Java. C'est ici que j'ai fait tous les calculs de la saison.
Dans le fichier `Classement.java` :
- J'ai fait une méthode pour attribuer les points selon la position. Si le pilote est hors du top 10 ou s'il a abandonné (position 0), je lui mets directement 0 point.
- Pour faire le classement des pilotes, j'ai utilisé une `Map` pour regrouper les scores de chacun. J'additionne leurs points, je compte les victoires et les deuxièmes places.