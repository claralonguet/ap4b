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

    private JPanel buttonPanel; 
    private JPanel globalPanel;

    private boolean pronostic; 

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

        String[] types = {"ok", "notok"};
        for (int i = 0; i < types.length; i++) {
            buttonPanelGBC = modifyGBC(buttonPanelGBC, i, 0, 0, 0);
            FormattedButton button = createValidationButton(types[i], iconDim);
            button.addActionListener(new ButtonListener());
            buttonPanel.add(button, buttonPanelGBC);
        }  

        globalPanel.add(buttonPanel, globalPanelBGC);
        

        //Bouton de données
        globalPanelBGC = modifyGBC(globalPanelBGC, 0, 2, 1.0, 0.2);
        globalPanelBGC.fill = GridBagConstraints.CENTER;

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

    private class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            FormattedButton sourceButton = (FormattedButton) e.getSource();

            pronostic = sourceButton.getType() == "ok";
            
            kill();
        }
    }

    public Boolean getPronostic(){
        return this.pronostic;
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            PronosticScreen pronosticScreen = new PronosticScreen("Turing Machine", 1000,800,new Joueur("josh", "joshua")); 
            pronosticScreen.setVisible(true);
        });
    }

    
}
