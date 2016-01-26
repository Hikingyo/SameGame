/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeugrille;

/**
 *
 * @author hikingyo
 */
class Position {
    private int colonne, ligne;

    public int getColonne() {
        return colonne;
    }

    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    public int getLigne() {
        return ligne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }
    
    public Position(int c, int l){
        colonne = c;
        ligne = l;
    }
}
