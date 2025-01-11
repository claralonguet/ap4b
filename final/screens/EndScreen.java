package screens;

import errors.WrongSkinNameException;
import errors.WrongTextureTypeException;

import java.awt.*;

import javax.swing.*;

import java.io.IOException;

import generals.*;
import game.*;

public class EndScreen extends GameScreen{
    private JPanel globalPanel; 

    private TexturedPanel imagePanel;

    private FormattedLabel textLabel;
    private FormattedLabel subtextLabel;
    private FormattedLabel codeAnswerLabel;
    private FormattedLabel quitGameLabel;

    private FormattedButton quitButton; 

    private GameTexture background; 

    public EndScreen(String title, int w, int h, Joueur joueur, String type, Code code){
        super(title, w, h, joueur);

        String text = "Victoire !";
        String subtext = "Bravo, vous avez gagné !";
        String quitText = "Félicitation, n'hésitez pas à réessayer.";
        if (type == "defeat") {
            text = "Défaite...";
            subtext = "Malheureusement c'est perdu.";
            quitText = "Dommage, retentez !";
        }

        //Charger l'image
        try{
            background = new GameTexture(joueur.getSkin(), type);
            background = background.rotate(90);
        } catch (IOException | WrongSkinNameException | WrongTextureTypeException e){
            System.out.println(e.getMessage());
        }
        imagePanel = new TexturedPanel(background);

        //Global
        globalPanel = new JPanel(new GridBagLayout());
        GridBagConstraints globalPanelGBC = generateGBC(1.0, 0.1);

        //Texte
        textLabel = new FormattedLabel(text, Color.BLACK, "typewritter", 35);
        subtextLabel = new FormattedLabel(subtext, Color.DARK_GRAY, "typewritter", 25);

        globalPanel.add(textLabel, globalPanelGBC);

        globalPanelGBC = modifyGBC(globalPanelGBC, 0, 1, 1.0, 0.1);

        globalPanel.add(subtextLabel, globalPanelGBC);

        //Image
        globalPanelGBC = modifyGBC(globalPanelGBC, 0, 2, 1.0, 0.6);
        globalPanel.add(imagePanel, globalPanelGBC); 

        //Code réponse
        globalPanelGBC = modifyGBC(globalPanelGBC, 0, 3, 1.0, 0.1);
        String c = code.toString();
        codeAnswerLabel = new FormattedLabel("Le code correct était : " + c, Color.BLUE, "typewritter", 23);
        globalPanel.add(codeAnswerLabel, globalPanelGBC);

        //Bouton de fin
        globalPanelGBC = modifyGBC(globalPanelGBC, 0, 4, 1.0, 0.1);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        quitGameLabel = new FormattedLabel(quitText, Color.BLACK, "typewritter", 23);
        bottomPanel.add(Box.createGlue());
        bottomPanel.add(quitGameLabel);
        bottomPanel.add(Box.createRigidArea(new Dimension(100,0)));
        quitButton = new FormattedButton("OK", "cloneMachine",23);
        quitButton.addActionListener(e->{
            kill();
        });
        bottomPanel.add(quitButton);
        bottomPanel.add(Box.createGlue());
        globalPanel.add(bottomPanel, globalPanelGBC);

        add(globalPanel); 
    }

    public static void main(String[] args) {
        EndScreen e = new EndScreen("t", 1000, 800, new Joueur("joshua", "joshua"), "defeat", new Code(1,2,3));
        e.setVisible(true);
    }
}
