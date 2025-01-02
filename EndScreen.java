import errors.WrongSkinNameException;
import errors.WrongTextureTypeException;

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

    public EndScreen(String title, int w, int h, String pseudo, String skin, String type, String text, String subtext, int[] code){
        super(title, w, h, pseudo, skin);

        //Charger l'image
        try{

            background = new GameTexture(skin, type);
            background = background.rotate(90);

        } catch (IOException | WrongSkinNameException | WrongTextureTypeException e){

            System.err.println(e.getMessage());

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
            EndScreen endScreen = new EndScreen("Turing Machine", 1000, 800, "joshua", "joshua", "defeat", "Défaite...", "Malheureusement vous avez perdu...", code);
            endScreen.setVisible(true);
        });
    }
}
