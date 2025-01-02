package generals;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import errors.*;

public class GameTexture {
    private BufferedImage image;
    private String skinName; 
    private String textureType; 

    private String folder = "textures";
    
    final private String imageExtention = ".jpg"; 

    public GameTexture(String skin, String type) throws IOException, WrongSkinNameException, WrongTextureTypeException{
        if( ! (skin == "clara" || skin == "enzo" || skin == "joshua" || skin == "pierre")){
            throw new WrongSkinNameException("There exists no such skin named '" + skin + "'"); 
        }
        if( ! (type == "idle1" || type == "idle2" || type == "ok" || type == "notok" || type == "defeat" || type == "victory" || type == "profile")){
            throw new WrongTextureTypeException("There exists no such type called '" + type + "'"); 
        }
        this.skinName = skin; 
        this.textureType = type; 

        String filePath = folder + "/" + skinName + "_" + textureType + imageExtention;
        this.image = ImageIO.read(new File(filePath));
    }

    public GameTexture(String filename, boolean criteria) throws IOException{
        if(criteria){
            this.folder = "criteria"; 
        }
        String filePath = folder + "/" +  filename + imageExtention;
        this.image = ImageIO.read(new File(filePath));
    }

    //Surcharge du constructeur pour l'utiliser au sein de la classe
    private GameTexture(BufferedImage image) {
        this.image = image;
    }

    //Redimensionner l'image
    public GameTexture resize(int width, int height) {
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        return new GameTexture(resizedImage);
    }

    //Pivoter l'image d'un certain angle (en degrés)
    public GameTexture rotate(double angle) throws IOException {
        double radians = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
    
        // Calculer les nouvelles dimensions
        int newWidth = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
        int newHeight = (int) Math.floor(image.getWidth() * sin + image.getHeight() * cos);
    
        // Créer une nouvelle image vide avec les nouvelles dimensions
        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
    
        Graphics2D g2d = rotatedImage.createGraphics();
    
        // Définir une interpolation de haute qualité
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
        // Définir le point de rotation au centre
        int x = (newWidth - image.getWidth()) / 2;
        int y = (newHeight - image.getHeight()) / 2;
    
        // Appliquer la rotation
        g2d.translate(x, y);
        g2d.rotate(radians, image.getWidth() / 2.0, image.getHeight() / 2.0);
        g2d.drawRenderedImage(image, null);
        g2d.dispose();
    
        return new GameTexture(rotatedImage);
    }

    public GameTexture rescaleToFit(Dimension targetSize) throws IOException {
        int targetWidth = (int)targetSize.getWidth();
        int targetHeight = (int)targetSize.getHeight();
    
        double scalex = (double) targetWidth / image.getWidth();
        double scaley = (double) targetHeight / image.getHeight();
        double scale = Math.min(scalex, scaley);
    
        int newWidth = (int) (image.getWidth() * scale);
        int newHeight = (int) (image.getHeight() * scale);
        
        return resize(newWidth, newHeight);
    }


    public ImageIcon convertToImageIcon() {
        return new ImageIcon(image);
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getWidth(){
        return getImage().getWidth();
    }

    public int getHeight(){
        return getImage().getHeight();
    }

    public void setFolder(String f){
        this.folder = f; 
    }
}
