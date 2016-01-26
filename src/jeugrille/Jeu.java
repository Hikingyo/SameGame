package jeugrille;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author billaud
 */
class Jeu {

    JFrame fenetre;

    public Jeu() 
    {
        fenetre = new JFrame();
        fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        final AfficheurGrille afficheur = new AfficheurGrille(8, 10);

        afficheur.setPreferredSize(new Dimension(400, 400));
        fenetre.getContentPane().add(afficheur, BorderLayout.CENTER);

        // les boutons
        // création et positionnement dans la fenêtre
        JPanel buttonsPanel = new JPanel();
        JButton playAgainButton = new JButton("recommencer");
        buttonsPanel.add(playAgainButton);
        fenetre.getContentPane().add(buttonsPanel,
                BorderLayout.SOUTH);

        // actions associées    
        playAgainButton.addActionListener(
                (ActionEvent e) -> {
                    afficheur.nouvellePartie();
                });
        
        afficheur.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                afficheur.clic_coordonnees(e.getX(), e.getY());
            }
        });
       }    

    void run() {
        fenetre.pack();
        fenetre.setVisible(true);
    }
}
