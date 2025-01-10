package screens;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import generals.*;
import game.*;

public class PronosticScreen extends GameScreen{
    private FormattedLabel textLabel;

    private FormattedButton dataButton; 
    private FormattedButton okButton;
    private FormattedButton notOkButton;

    private JPanel buttonPanel; 
    private JPanel globalPanel;

    public PronosticScreen(String title, int w, int h, Joueur joueur){
        super(title, w, h, joueur);

        //Global
        globalPanel = new JPanel(new GridBagLayout());
        GridBagConstraints globalPanelBGC = generateGBC(1.0,0.4);

        //Texte
        textLabel = new FormattedLabel("Pensez-vous avoir trouvé le code ?", Color.BLACK, "typewritter", 52);
        globalPanel.add(textLabel, globalPanelBGC);
        globalPanelBGC = modifyGBC(globalPanelBGC, 0, 1, 1.0, 0.5);

        //Boutons ok et non ok
        buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints buttonPanelGBC = generateGBC(0, 0);
        buttonPanelGBC.insets = new Insets(0, 100, 0, 100);

        Dimension iconDim = new Dimension(300,300);

        okButton = createValidationButton("ok", iconDim);
        okButton.addActionListener(new OkButtonListener());

        notOkButton = createValidationButton("notok", iconDim); 

        buttonPanel.add(okButton, buttonPanelGBC); 
        buttonPanelGBC = modifyGBC(buttonPanelGBC, 1, 0, 0, 0);
        buttonPanel.add(notOkButton, buttonPanelGBC); 

        globalPanel.add(buttonPanel, globalPanelBGC);
        globalPanelBGC = modifyGBC(globalPanelBGC, 0, 2, 1.0, 0.2);
        globalPanelBGC.fill = GridBagConstraints.CENTER;

        //Bouton de données
        dataButton = new FormattedButton("Informations", "cloneMachine", 25);
        dataButton.addActionListener(new DataButtonListener());
        globalPanel.add(dataButton, globalPanelBGC); 
        
        add(globalPanel);
    }

    private FormattedButton createValidationButton(String type, Dimension iconDim){
        FormattedButton button = new FormattedButton(joueur.getSkin(), type, iconDim);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }

    private class DataButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            DataScreen dataScreen = new DataScreen("Informations de " + joueur.getNom(), 1000, 800, joueur);
            dataScreen.setVisible(true);
        }
    }

    private class OkButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            CodeGuessScreen codeGuessScreen = new CodeGuessScreen("Choisissez un code", getWidth(), getHeight(), joueur);
            codeGuessScreen.setVisible(true);
            setVisible(false);
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            PronosticScreen pronosticScreen = new PronosticScreen("Turing Machine", 1000,800,new Joueur("josh", "joshua")); 
            pronosticScreen.setVisible(true);
        });
    }

    
}
