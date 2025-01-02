import java.awt.*;
import javax.swing.*;

import generals.*;

public class CodeGuessScreen extends GameScreen{
    private JPanel panel; 
    private CodeInput inputPanel;

    private FormattedLabel textLabel;

    private FormattedButton submitButton; 

    public CodeGuessScreen(String screenTitle, int w, int h, String pseudo, String skin){
        super(screenTitle, w, h, pseudo, skin);

        panel = new JPanel();
        panel.setLayout(new GridLayout(3,1));
        
        //Texte
        textLabel = new FormattedLabel("Entrez le code que vous pensez être le bon", Color.BLACK, "typewritter", 30);
        panel.add(textLabel); 
        
        //Code
        inputPanel = new CodeInput();
        panel.add(inputPanel); 

        //Bouton
        submitButton = new FormattedButton("Soumettre", "cloneMachine", 30);
        submitButton.addActionListener(e -> {
            inputPanel.setInputBlocking();
        }); 
        panel.add(submitButton);

        add(panel);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            CodeGuessScreen cgscreen = new CodeGuessScreen("Turing Machine", 800,600,"joshua","joshua"); 
            cgscreen.setVisible(true);
        });
    }
}
