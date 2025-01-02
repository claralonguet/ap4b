import java.awt.*;
import javax.swing.*;

import errors.WrongSizeException;
import errors.WrongSkinNameException;
import errors.WrongTextureTypeException;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import java.util.Random;

import generals.*;

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

    private HashMap<String, String> dictionary = new HashMap<>();

    private final ArrayList<String> skins = new ArrayList<>();

    private int nbrPlayer = 2;

    private Random rand = new Random(); 

    public LaunchScreen(String title, int w, int h, String pseudo, String skin){
        super(title, w, h, pseudo, skin);

        //Initialisation de la liste de skins
        String[] s = {"clara","enzo","joshua","pierre"}; 
        for(String name : s){
            skins.add(name);
        }
        Collections.shuffle(skins, rand);
        
 
        //Global
        globalPanel = new JPanel();
        globalPanel.setLayout(new GridBagLayout());
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
            LaunchScreen launchScreen = new LaunchScreen("Turing Machine",800,600,"Moumoute","joshua"); 
            launchScreen.setVisible(true);
        });
    }

    private ArrayList<JTextArea> updateTextAreas(int n){

        ArrayList<JTextArea> areas = new ArrayList<>();
        dictionary.clear();
        
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

                dictionary.put(skins.get(i), null);

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
            //Vérifie la taille des éléments
            try {
                verifySize(dictionary, pseudoInputsArray);
            } catch (WrongSizeException exception) {
                System.out.println("Error : " + exception.getMessage());
            }

            //Ajoute à la hashmap les pseudos liés par la clé étant le skin
            for(int i = 0; i < pseudoInputsArray.size(); i++){
                dictionary.put(skins.get(i), pseudoInputsArray.get(i).getText());                
            }

            //Bloque les boutons radio
            for(JRadioButton b : buttonsArray){
                b.setEnabled(false);
            }
            
            //Bloque les zones de texte
            for(JTextArea t : pseudoInputsArray){
                t.setEditable(false);
            }

            for(String k : dictionary.keySet()){
                System.out.println( k + " : " + dictionary.get(k));
            }
        }
    }

    private <T, U> void verifySize(HashMap<T,T> hash, ArrayList<U> array) throws WrongSizeException{
        if(hash.size() != array.size()){
            throw new WrongSizeException("Size exception, size of hashmap : " + Integer.toString(hash.size()) + ", array : " + Integer.toString(array.size()));
        }
    }
}