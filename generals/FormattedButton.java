package generals;

import javax.swing.*;

import java.awt.image.BufferedImage;

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
            GameTexture texture = new GameTexture(filename, criteria, "jpg"); 

            texture = texture.rescaleToFit(size);

            setIcon(texture.convertToImageIcon()); 
        } catch (IOException e) {
            System.out.println("Couldn't load image named '" + filename + "'. - " + e.getMessage());
        }
    }

    public void setBlocked(boolean isTinted){
        Icon icon = getIcon();
        setEnabled(false);
        if(!isTinted){
            setDisabledIcon(icon);
        } else {
            setDisabledIcon(null);
        }
    }

    public void setUnblocked(){
        setEnabled(true);
    }

    public void setVerification(boolean isVerified){
        Color color = new Color(180, 0, 0, 100);
        if(isVerified){
            color = new Color(0, 120, 0, 100);
        }
        
        Icon icon = getIcon();
        if(icon instanceof ImageIcon){
            setIcon(createTintedIcon((ImageIcon) icon, color));
        } else {
            System.out.println("Cannot convert Icon to ImageIcon.");
        }
    }

    private static Icon createTintedIcon(ImageIcon originalIcon, Color tint) {
        // Convertir l'icône en BufferedImage
        Image originalImage = originalIcon.getImage();
        BufferedImage tintedImage = new BufferedImage(
                originalImage.getWidth(null),
                originalImage.getHeight(null),
                BufferedImage.TRANSLUCENT);

        Graphics2D g2d = tintedImage.createGraphics();

        // Dessiner l'image d'origine
        g2d.drawImage(originalImage, 0, 0, null);

        // Appliquer un filtre de couleur
        g2d.setComposite(AlphaComposite.SrcAtop);
        g2d.setColor(tint);
        g2d.fillRect(0, 0, tintedImage.getWidth(), tintedImage.getHeight());

        g2d.dispose();
        return new ImageIcon(tintedImage);
    }
}
