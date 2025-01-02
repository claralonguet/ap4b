package generals;

import java.awt.*;
import javax.swing.*;

public class GameScreen extends JFrame{
    private String title;
    private int width; 
    private int height; 
    private String playerPseudo;
    private String playerSkin; 
    
    public GameScreen(String title, int width, int height, String pseudo, String skin){
        this.title = title; 
        this.width = width;
        this.height = height; 
        this.playerPseudo = pseudo;
        this.playerSkin = skin; 

        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(width, height);
    }

    public String getSkin(){
        return playerSkin;
    }

    public String getPseudo(){
        return playerPseudo; 
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
}
