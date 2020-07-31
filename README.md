# TP-StudentsChecker

Documentation TP-StudentChecker
# Principe de l'application

L'application permet de gérer la présence des élèves durant les cours à distance. 
Ainsi il y a des interface conçues pour les élèves et pour les administrateurs, celles-ci seront détaillées plus bas. 

## Les interfaces

Afin d'accéder à l'application, nous devons nous connecter avec les login/mot de passe adéquats. 
Les élèves auront leur propre interface se résumant à la lecture voir à la modification de données de son profil. 
Les administrateurs, eux, auront tous les droits d'un CRUD, ils pourront créer, modifier, lire, supprimer les élèves, mais également ils pourront également administrer les classes. 

### Interface élève
Une fois qu'un élève s'est connecté à l'application, il ne pourra voir que son profil et ne pourra modifier que ses données, il ne pourra pas voir la liste des élèves, ni même accéder aux autres profils que le sien. 
Ainsi, s'il clique sur son nom dans la liste il verra les informations suivantes : 
- Son nom 
- Son prénom
- Sa date de naissance
- Sa classe
- Son adresse mail 
- Son mot de passe 

S'il souhaite pouvoir modifier ses informations, il n'aura qu'à cliquer sur le bouton "Modifier les informations". 
Dès lors, une nouvelle fenêtre s'ouvrira avec un récapitulatif des informations de l'élève, il pourra alors les modifier, afin d'enregistrer les changements il devra cliquer sur le bouton "Enregistrer". 

### Interface Administrateur 
Une fois qu’un administrateur s’est connecté à l’application, il pourra alors manager l’ensemble des données à savoir : 
- Les classes
- Les élèves

Il pourra alors, créer, modifier, supprimer des classes/élèves mais également lire les informations relatives à la classe et/ou l’élève. 

À savoir : 
Les classes sont composées : 
- D’un libellé 
- D’une période

Les élèves sont définis comme ayant : 
- Un nom
- Un prénom 
- Une date de naissance
- Un email 
- Un mot de passe
- Un ID de la classe dans laquelle l’élève est. 

Ainsi un administrateur pourra ajouter un élève, une classe, mais également modifier l’ensemble des informations, ou les supprimer. 

# Technologie 
Afin de créer l'application jointe, nous avons utilisé uniquement la technologie JAVA. 
