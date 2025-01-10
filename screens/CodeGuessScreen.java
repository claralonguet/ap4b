package screens;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import game.Joueur;
import generals.*;

public class CodeGuessScreen extends GameScreen{
    private JPanel globalPanel; 
    private CodeInput inputPanel;

    private FormattedLabel textLabel;

    private FormattedButton submitButton; 

    public CodeGuessScreen(String screenTitle, int w, int h, Joueur joueur){
        super(screenTitle, w, h, joueur);

        globalPanel = new JPanel();
        globalPanel.setLayout(new GridBagLayout());
        GridBagConstraints globalPanelGBC = generateGBC(1.0, 0.2);
        
        //Texte
        textLabel = new FormattedLabel("Entrez le code que vous pensez être le bon", Color.BLACK, "typewritter", 30);
        globalPanel.add(textLabel, globalPanelGBC); 
        
        //Code
        globalPanelGBC = modifyGBC(globalPanelGBC, 0, 1, 1.0, 0.6);
        inputPanel = new CodeInput();
        globalPanel.add(inputPanel, globalPanelGBC); 

        //Bouton
        globalPanelGBC = modifyGBC(globalPanelGBC, 0, 2, 1.0, 0.2);
        globalPanelGBC.fill = GridBagConstraints.CENTER;
        submitButton = new FormattedButton("Soumettre", "cloneMachine", 30);
        submitButton.addActionListener(e -> {
            inputPanel.setInputBlocking();
            this.isRunning = false;
        }); 
        globalPanel.add(submitButton, globalPanelGBC);

        add(globalPanel);
    }

    public ArrayList<Integer> getCode(){
        return inputPanel.getCode();
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            CodeGuessScreen cgscreen = new CodeGuessScreen("Turing Machine", 800,600,new Joueur("pseudo","joshua")); 
            cgscreen.setVisible(true);
        });
    }
}
