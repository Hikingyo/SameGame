# Same Game
## Objectifs
Réaliser un jeu similaire au Same Game en Java en utilisant la librairie Swing :
- Lorsqu'on clique sur une case, toutes les cases adjacents de la même couleur doivent disparaître.
- Lorsqu'une case disparaît, les cases qui se trouvent au dessus se décalent vers le bas pour combler le trou.
- Lorsqu'une colonne ne comporte plus de cases, celle-ci disparaît et les colonnes à sa droite, se décalent pour combler le trou.
- Un compteur est incrémenté à chaque coup réalisé.

## Solution proposée
Le programme est composé de 4 classes :
- La classe `JeuGrille` qui va crée une instance de Jeu.
- La classe `Jeu` qui va créé une fenêtre graphique.
- La classe `AfficheurGrille` qui gère le fonctionnement du jeu.
- La classe `Position` qui sert à manipuler la position d'une case;

### JeuGrille
`JeuGrille` est la classe principale du projet. Elle va instancier la classe Jeu.

Méthodes              | Description
--------------------- | --------------------------------------------------------------
`main(String[] args)` | C'est la fonction principale, c'est elle qui va lancer le jeu.

### Jeu
`Jeu` gère l'affichage de la fenètre graphique.

Attributs        | Description
---------------- | -----------
`JFrame fenetre` |

Méthodes | Description
-------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
`Jeu()`  | Constructeur de classe. Il définit les propriétés de la fenetre graphique : dimensions, taille de la grille de cellules, ainsi la création et le positionnement des boutons.

### AfficheurGrille
`AfficheurGrille` gère le fonctionnement du jeu.

Attributs              | Description
---------------------- | ----------------------------------------------------------------------------
`int nbl`              | Nombre de ligne
`int nbc`              | Nombre de colonne
`int[][] etat`         | Tableau à deux dimensions stockant l'état (la couleur) de chaque case_a_voir
`int coups`            | Nombre de coups effectué par le joueur
`int score`            | Score du joueur
`static final Color[]` | Tableau contenant l'ensemble des couleurs qui peuvent prendre les cases.
`final Font police`    | Police d'écriture
`case_largeur`         | Largeur d'une case
`case_hauteur`         | Hauteur d'une case
`grille_gauche`        | Marge gauche
`grille_haut`          | Marge haut
`case_gauche`          | Décalage de la case à largeur
`case_haut`            | Décalage de la case en hauteur

Méthodes                                        | Description
----------------------------------------------- | ----------------------------------------------------------------------------------------------------
`AfficheurGrille(int nbColonnes, int nbLignes)` | Constructeur de la classe
`nouvellePartie()`                              | Remplissage de la grille de façon aléatoire
`clic_coordonnees(int x, int y)`                | Conversion des coordonnées graphiques en colonne/ligne.
`clic_case(int c, int l)`                       | Action d'un clic sur une case.
`paintComponent(Graphics g)`                    | Affichage des composants de dessin.
`recherche_et_destruction(int c, int l)`        | Recherche des case contigue de même couleur. Si plusieurs cases sont concernées, on les met en noir.
`tassement()`                                   | Tassement vertical des cases et horizontal des colonnes.
`fin_partie()`                                  | Gestion de la fin de partie.

#### Structure de données
Le fonctionnement du jeu est géré à travers un tableau à deux dimensions `etat[][]` stockant l'état (et donc la couleur) d'une case. La première dimension de ce tableau représente la coordonnée en X de la case, et la deuxième dimension, la coordonnée en Y, sachant que l'origine de la grille correspond au coin supérieur gauche de la grille.

On dispose également d'une liste `voisins` regroupant les cases voisines d'une case donnée et d'une autre liste `case_a_voir` regroupant les cases à supprimer.

Le booléen `supprimer` sert de flag et permet d'empêcher que les cases cliquées par le joueur n'ayant pas de voisins soient supprimés.

#### Algorithmique
Remplissage de la grille de façon aléatoire.

```
Pour i de 0 à NombreColonne - 1 avec un pas de 1
Faire
  Pour j de 0 à NombreLigne - 1 avec un pas de 1
  Faire
    etat[i][j] <- random de 1 à 4
```

Conversion des coordonnées graphiques en colonne/ligne.

```
Entier NumeroColonne <- (x - MargeHaut) / TailleCase;
Entier NumeroLigne <- (y - MargeGauche) / TailleCase;
```

Action d'un clic sur une case.

```
Si etat[c][l] est différent de 0
Alors
  Incrémente le compteur de coups++;

Appel de la fonction de recherche et de destruction des cases
Appel de la fonction de tassement
Appel de la fonction rafraichissement graphique
```

Affichage des composants de dessin.

```
// Affichage de la grille

Pour i de 0 à NombreColonne - 1 avec un pas de 1
Faire
  Pour j de 0 à NombreLigne - 1 avec un pas de 1
  Faire
    Colorier case[i][j]

// Fin de partie

Si flag est à false
Alors
  Affichage message de fin de partie
```

Tassement vertical des cases et horizontal des colonnes.

```
// Tassement vertical
Pour c de 0 à NombreColonne
Faire
  Entier Curseur <- NombreLigne - 1

  Pour l de NombreLigne - 1 à 0
  Faire
    Si etat[c][l] est différent de 0
    Alors
      etat[c][curseur] <- etat[c][l]
      curseur <- curseur - 1

  Pour cl de curseur à 0
  Faire
    etat[c][cl] <- 0

// Tassement horizontal
Entier curseur
Pour c de 0 à NombreColonne
Faire
  Si colonne n'est pas vide
  Alors
    On copie à l'endroit du curseur

Pour chaque colonne vide
Faire
  Remplir la colonne de case vide
```

Gestion de la fin de partie.

```
Pour i de 0 à NombreColonne - 1 avec un pas de 1
Faire
  Pour j de 0 à NombreLigne - 1 avec un pas de 1
  Faire
    Si la case n'est pas detruite
    Alors
      Si la case voisine est dans la grille
      Alors
        Si la case à gauche ou en dessous est de même couleur
        Alors
          La partie continue

fin de la partie
```

### Position
`Position` est la classe représentant la position d'une case. Elle possède les caractéristiques suivantes :
- Une position en X;
- Une position en Y;

Attributs     | Description
------------- | -------------
`int colonne` | Position en X
`int ligne`   | Position en Y

Méthodes                           | Description
---------------------------------- | -------------------------------
`Position(int ligne, int colonne)` | Constructeur de classe
`getColonne()`                     | Retourne la valeur de `colonne`
`setColonne(int colonne)`          | Modifie la valeur de `colonne`
`getLigne()`                       | Retourne la valeur de `ligne`
`setLigne(int ligne)`              | Modifie la valeur de `ligne`
