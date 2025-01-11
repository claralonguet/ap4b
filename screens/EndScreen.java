package screens;
import errors.WrongSkinNameException;
import errors.WrongTextureTypeException;
import game.Joueur;

import java.awt.*;
import javax.swing.*;

import java.io.IOException;

import generals.*;

public class EndScreen extends GameScreen{
    private JPanel globalPanel; 

    private TexturedPanel imagePanel;

    private FormattedLabel textLabel;
    private FormattedLabel subtextLabel;
    private FormattedLabel codeAnswerLabel; 

    private GameTexture background; 

    public EndScreen(String title, int w, int h, Joueur joueur, String type, int[] code){
        super(title, w, h, joueur);

        String text = "Victoire !";
        String subtext = "Bravo, vous avez gagné !";
        if (type == "defeat") {
            text = "Défaite...";
            subtext = "Malheureusement c'est perdu.";
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
        globalPanelGBC = modifyGBC(globalPanelGBC, 0, 2, 1.0, 0.7);
        globalPanel.add(imagePanel, globalPanelGBC); 

        //Code réponse
        globalPanelGBC = modifyGBC(globalPanelGBC, 0, 3, 1.0, 0.1);
        String c = Integer.toString(code[0]) + Integer.toString(code[1]) + Integer.toString(code[2]);
        codeAnswerLabel = new FormattedLabel("Le code correct était : " + c, Color.BLUE, "typewritter", 23);
        globalPanel.add(codeAnswerLabel, globalPanelGBC);

        add(globalPanel); 
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            int[] code = {3,4,5};
            EndScreen endScreen = new EndScreen("Turing Machine", 1000, 800, new Joueur("josh", "joshua"), "victory", code);
            endScreen.setVisible(true);
        });
    }
}
