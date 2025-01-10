package screens;

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.IOException;

import java.util.*;

import generals.*;
import game.*;
import errors.*;

public class LaunchScreen extends GameScreen{
    private JPanel globalPanel; 
    private JPanel playersPanel; 
    private JPanel playerNumberPanel; 
    private JPanel playerNamesPanel;

    private FormattedButton launchButton; 

    private FormattedLabel titleLabel;
    private FormattedLabel playerNumberLabel;

    private ButtonGroup group;

    private ArrayList<JRadioButton> buttonsArray; 

    private ArrayList<JTextArea> pseudoInputsArray;

    private ArrayList<Joueur> players = new ArrayList<>();

    private final ArrayList<String> skins = new ArrayList<>();

    private int nbrPlayer = 2;

    private Random rand = new Random(); 

    public LaunchScreen(String title, int w, int h, Joueur joueur){
        super(title, w, h, joueur);

        //Initialisation de la liste de skins
        String[] s = {"clara","enzo","joshua","pierre"}; 
        for(String name : s){
            skins.add(name);
        }
        Collections.shuffle(skins, rand);
 
        //Global
        globalPanel = new JPanel(new GridBagLayout());
        GridBagConstraints globalPanelGBC = generateGBC(1.0,0.2);
        
        //Titre du jeu
        titleLabel = new FormattedLabel("Turing Machine", Color.black, "typewritter", 56);
        globalPanel.add(titleLabel, globalPanelGBC); 

        //Nombre de joueurs 
        globalPanelGBC = modifyGBC(globalPanelGBC, 0, 1, 1.0, 0.3);
        playersPanel = new JPanel(new GridLayout(1,2));
        playerNumberPanel = new JPanel(new GridLayout(1,4));

        playerNumberLabel = new FormattedLabel("Nombre de joueurs : ", Color.BLACK, "typewritter", 35);
        playersPanel.add(playerNumberLabel); 

        group = new ButtonGroup();
        buttonsArray = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            JRadioButton button = new JRadioButton(Integer.toString(i + 1));
            buttonsArray.add(button); 
            button.addActionListener(new ChangeNumberOfPlayersListener());
            GameFont f = new GameFont("cloneMachine");
            button.setFont(f.resize(20).getFont());
            if(i == 1){
                button.setSelected(true);
            }
            group.add(button);
            playerNumberPanel.add(button);
        }
        playersPanel.add(playerNumberPanel);         
        globalPanel.add(playersPanel ,globalPanelGBC); 

        //Choix des pseudos et skin aléatoire
        globalPanelGBC = modifyGBC(globalPanelGBC, 0, 2, 1.0, 0.3);
        playerNamesPanel = new JPanel(); 
        pseudoInputsArray = updateTextAreas(nbrPlayer);
        globalPanel.add(playerNamesPanel, globalPanelGBC);
        
        //Bouton de lancement du jeu
        globalPanelGBC = modifyGBC(globalPanelGBC, 0, 3, 1.0, 0.2);
        globalPanelGBC.fill = GridBagConstraints.CENTER;
        launchButton = new FormattedButton("Lancer", "cloneMachine",35);
        launchButton.addActionListener(new LaunchButtonListener());
        globalPanel.add(launchButton, globalPanelGBC);

        add(globalPanel); 
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            LaunchScreen launchScreen = new LaunchScreen("Turing Machine",800,600,new Joueur("abc","joshua")); 
            launchScreen.setVisible(true);
        });
    }

        
    public ArrayList<Joueur> getPlayers(){
        return players;
    }

    private ArrayList<JTextArea> updateTextAreas(int n){

        ArrayList<JTextArea> areas = new ArrayList<>();
        players.clear();
        
        playerNamesPanel.removeAll();
        playerNamesPanel.setLayout(new GridLayout(1,n));

        for(int i = 0; i < n; i++){
            //Joueur i
            FormattedLabel playerNum = new FormattedLabel("J" + Integer.toString(i + 1), Color.BLACK, "cloneMachine", 20);

            //Pseudo
            JTextArea pseudoTextArea = new JTextArea();

            JPanel pseudoSelectionPanel = new JPanel(new GridBagLayout());

            GridBagConstraints gbc = generateGBC(0.2,1.0);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0, 10, 0, 10);

            pseudoSelectionPanel.add(playerNum, gbc); 

            gbc = modifyGBC(gbc, 1, 0, 0.5, 1.0);

            pseudoSelectionPanel.add(pseudoTextArea, gbc);

            //Skin
            gbc = modifyGBC(gbc, 2, 0, 0.3, 1.0);

            try {
                GameTexture profile = new GameTexture(skins.get(i),"profile");
                TexturedPanel skinPanel = new TexturedPanel(profile);
                gbc.fill = GridBagConstraints.BOTH;
                pseudoSelectionPanel.add(skinPanel, gbc);

            } catch (IOException | WrongSkinNameException | WrongTextureTypeException e) {
                System.out.println("Could not load profile picture.");
                System.out.println(e.getMessage());
                pseudoSelectionPanel.add(new JLabel("An error has occured."));
            }

            playerNamesPanel.add(pseudoSelectionPanel);

            areas.add(pseudoTextArea);
        }
        playerNamesPanel.revalidate();
        playerNamesPanel.repaint();

        return areas;
    }

    private class ChangeNumberOfPlayersListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            for(JRadioButton r : buttonsArray){
                if(r.isSelected()){
                    nbrPlayer = Integer.parseInt(r.getText());
                    pseudoInputsArray = updateTextAreas(nbrPlayer);
                }
            }
        }
    }

    private class LaunchButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            
            //Ajoute à la liste des joueurs leur pseudo et skin
            for(int i = 0; i < pseudoInputsArray.size(); i++){
                players.add(new Joueur(pseudoInputsArray.get(i).getText(), skins.get(i)));                
            }

            //Bloque les boutons radio
            for(JRadioButton b : buttonsArray){
                b.setEnabled(false);
            }
            
            //Bloque les zones de texte
            for(JTextArea t : pseudoInputsArray){
                t.setEditable(false);
            }

            for(Joueur j : players){
                System.out.println( j.getSkin() + " : " + j.getNom());
            }
        }
    }
}