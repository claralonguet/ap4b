package generals;

import java.awt.*;
import javax.swing.*;
import game.Joueur;

import java.awt.event.WindowEvent;

public class GameScreen extends JFrame{
    protected Joueur joueur;
    
    public GameScreen(String title, int width, int height, Joueur joueur){

        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(width, height);
        this.joueur = joueur;
    }

    public GridBagConstraints generateGBC(double wx, double wy){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = wx; // wx% de la largeur
        gbc.weighty = wy; // wy% de la hauteur
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }

    public GridBagConstraints modifyGBC(GridBagConstraints gbc, int gx, int gy, double wx, double wy){
        gbc.weightx = wx; 
        gbc.weighty = wy;
        gbc.gridx = gx; 
        gbc.gridy = gy; 
        return gbc; 
    }

    protected void kill(){
        setVisible(false);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSED));
        dispose();
    }
}
