import javax.swing.*;
import generals.*;

import java.awt.*;

public class PronosticScreen extends GameScreen{
    private FormattedLabel textLabel;

    private FormattedButton dataButton; 
    private FormattedButton okButton;
    private FormattedButton notOkButton;

    private JPanel buttonPanel; 
    private JPanel globalPanel; 

    public PronosticScreen(String title, int w, int h, String pseudo, String skin){
        super(title, w, h, pseudo, skin);

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
        okButton = new FormattedButton(skin, "ok", iconDim); 
        notOkButton = new FormattedButton(skin, "notok", iconDim);
        okButton.setBorderPainted(false);
        okButton.setContentAreaFilled(false);
        notOkButton.setBorderPainted(false);
        notOkButton.setContentAreaFilled(false);

        buttonPanel.add(okButton, buttonPanelGBC); 
        buttonPanelGBC = modifyGBC(buttonPanelGBC, 1, 0, 0, 0);
        buttonPanel.add(notOkButton, buttonPanelGBC); 

        globalPanel.add(buttonPanel, globalPanelBGC);
        globalPanelBGC = modifyGBC(globalPanelBGC, 0, 2, 1.0, 0.2);

        //Bouton de données
        dataButton = new FormattedButton("Informations", "cloneMachine", 25);
        globalPanel.add(dataButton, globalPanelBGC); 
        
        add(globalPanel);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            PronosticScreen pronosticScreen = new PronosticScreen("Turing Machine", 1000,800,"Frank Getcher","joshua"); 
            pronosticScreen.setVisible(true);
        });
    }
}
