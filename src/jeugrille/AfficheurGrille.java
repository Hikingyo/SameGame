package jeugrille;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.LinkedList;
import javax.swing.JComponent;

/**
 * composant pour l'affichage d'une grille de jeu
 *
 * @author billaud
 */
final class AfficheurGrille extends JComponent {

    // taille de la grille
    int nbl, nbc;
    int[][] etat;
    int coups;
    int score;

    // positions, tailles, marges...
    int grille_gauche = 80, grille_haut = 80,
            case_largeur = 28,
            case_gauche = 30, // décalage par rapport au bord. ou case gauche
            case_hauteur = 28,
            case_haut = 30,
            message_haut = 50,
            message_gauche = 80;

    static final Color[] couleur
            = {Color.black, Color.blue, Color.red, Color.green, Color.yellow};

    final Font police;

    // ------------------------------------------------
    AfficheurGrille(int nbColonnes, int nbLignes) {
        police = new Font("Courier New", Font.PLAIN, 18);
        nbc = nbColonnes;
        nbl = nbLignes;
        etat = new int[nbc][nbl];
        nouvellePartie();
    }

    /**
     * remplissage de la grille avec des nombres aléatoires de 1 à 4 et remise à
     * zéro du compteur de coups
     */
    void nouvellePartie() {
        coups = 0;
        score = 0;
        for (int c = 0; c < nbc; c++) {
            for (int l = 0; l < nbl; l++) {
                etat[c][l] = 1 + (int) (4 * Math.random());
            }
        }
        repaint();
    }

    /**
     * conversion des coordonnées graphiques en colonne/ligne et action
     * éventuelle sur la grille
     */
    void clic_coordonnees(int x, int y) {
        int c = (x - grille_gauche) / case_gauche;
        int l = (y - grille_haut) / case_haut;

        // si dans la grille
        if (0 <= c && c < nbc && 0 <= l && l < nbl) {
            clic_case(c, l);
        }
    }

    /**
     * action d'un clic sur une case
     *
     * @param c numéro de colonne
     * @param l numéro de ligne
     */
    private void clic_case(int c, int l) {
        if (etat[c][l] != 0) {
            coups++;
        }
        // Recherche des cases accéssibles de même couleur
        // Suprression des cases
        recherche_et_destruction(c, l);
        // Tassement verticale et horizontale
        tassement();
        repaint();
    }

    /**
     * affichage du composant de dessin
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // tracé d'un message
        g.setColor(Color.GRAY);
        g.setFont(police);
        String message = String.format("Vous avez joué %d coups.", coups);
        g.drawString(message, message_gauche, message_haut);
        // Score
        String messageScore = String.format("Score : %d", score);
        g.drawString(messageScore, 5, 20);

        // et de la grille 
        for (int c = 0; c < nbc; c++) {
            for (int l = 0; l < nbl; l++) {
                g.setColor(couleur[etat[c][l]]);
                g.fillRect(grille_gauche + c * case_gauche,
                        grille_haut + l * case_haut,
                        case_largeur, case_hauteur);
            }
        }
    }

    /**
     * REcherche des case contigue de même couleur. Si plusieurs cases sont
     * concernées, on les met en noir.
     *
     * @param c numéro de colonne de la case cliquée
     * @param l numéro de ligne de la case cliquée
     */
    private void recherche_et_destruction(int c, int l) {
        // flag de supression des cases
        boolean supprimer = false;
        // nombre de cases supprimées
        int nbcs = 0;
        //stockage de la couleur de la case choisie
        int couleurv = etat[c][l];
        // Tableau des positionArrayList<Position> voisins = new ArrayList<>();
        int[] dirC = {0, 1, 0, -1};
        int[] dirL = {-1, 0, 1, 0};
        // Recherche des cases accéssibles de même couleurs
        // Liste des cases à visiter
        LinkedList<Position> case_a_voir = new LinkedList<>();
        // On ajoute la case cliquée à la liste si elle n'est pas noire
        if (etat[c][l] != 0) {
            case_a_voir.add(new Position(c, l));
        }
        // Tant qu'il y a des cases à visiter
        while (!case_a_voir.isEmpty()) {
            Position p = case_a_voir.removeFirst();
            // On cherche les voisines qui on la même couleur
            for (int i = 0; i < 4; i++) {
                // coordonnées de la case voisine
                int cv = p.getColonne() + dirC[i];
                int lv = p.getLigne() + dirL[i];
                //Si les coordonnées correspondent à une case de même couleure
                if (0 <= cv && cv < nbc
                        && 0 <= lv && lv < nbl
                        && etat[cv][lv] == couleurv) {
                    // S'il y a au moins une case voisine de même couleur
                    supprimer = true;
                    // Ajout dans cases à visiter
                    Position k = new Position(cv, lv);
                    case_a_voir.add(k);
                    // Changement d'état de la case visitée
                    etat[cv][lv] = 0;
                    // ajout au nombre de cases supprimées
                    nbcs ++;
                }
            }
        }
        // Changement d'état de la case choisie
        if (supprimer) {
            etat[c][l] = 0;
            nbcs ++;
        }
        // Gestion du score
        score += nbcs * nbcs;
    }

    private void tassement() {
        //tassement vertical
        // Pour chaque colonne
        for (int c = 0; c < nbc; c++) {
            int curseur = nbl - 1;
            // Pour chaque ligne de la colonne
            for (int l = nbl - 1; l >= 0; l--) {
                if (etat[c][l] != 0) {
                    etat[c][curseur] = etat[c][l];
                    curseur--;
                }
            }
            for (int cl = curseur; cl >= 0; cl--) {
                etat[c][cl] = 0;
            }
        }
        //tassement horizontal
        int curseur = 0;
        // Pour chaque colonne
        for (int c = 0; c < nbc; c++) {
            //Si la colonne n'est pas vide
            if (etat[c][nbl - 1] != 0) {
                // On la copie à l'endroit du curseur
                int[] c_tmp = etat[c];
                etat[curseur] = c_tmp;
                // On avance le curseur
                curseur++;
            }
        }
        // Pour chaque colonne vide
        for (int cv = curseur; cv < nbc; cv++) {
            // On remplit la colonne de case vide
            int[] c_tmp = new int[nbl];
            etat[cv] = c_tmp;
        }
    }
}
