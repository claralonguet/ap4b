package generals;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import errors.*;

public class FormattedButton extends JButton{

    public FormattedButton(String text, String fontName, int textSize){
        super(text);
        GameFont f = new GameFont(fontName);
        f = f.resize(textSize);
        setFont(f.getFont());
    }

    public FormattedButton(String iconSkin, String iconType, Dimension size){
        try {
            GameTexture texture = new GameTexture(iconSkin, iconType);
        
            texture = texture.rescaleToFit(size);

            setIcon(texture.convertToImageIcon());

        } catch (IOException | WrongSkinNameException | WrongTextureTypeException e) {
            System.out.println(e.getMessage());
            System.out.println("The texture does not exist.");
        }
    }

    public FormattedButton(String filename, Dimension size, boolean criteria){
        try {
            GameTexture texture = new GameTexture(filename, criteria); 

            texture = texture.rescaleToFit(size);

            setIcon(texture.convertToImageIcon()); 
        } catch (IOException e) {
            System.out.println("Couldn't load image named '" + filename + "'. - " + e.getMessage());
        }
    }

    public void setBlocked(){
        setEnabled(false);
    }

    public void setUnblocked(){
        setEnabled(true);
    }
}
