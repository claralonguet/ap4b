package generals;

import java.awt.*;
import java.io.*;

public class GameFont{
    private Font font;

    public GameFont(String fontName){
        try {
            String ext = ".ttf";
            if (fontName == "cloneMachine"){
                ext = ".otf";
            }
            font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/" + fontName + ext));

        } catch (FontFormatException | IOException e) {
            font = new Font("Arial", Font.PLAIN, 10);
            System.out.println("Couldn't load font named : '" + fontName + "'.");
        }
    }

    private GameFont(Font f){
        this.font = f; 
    }

    public GameFont resize(int size){
        font = font.deriveFont(Font.PLAIN, size); 
        return new GameFont(font); 
    }

    public Font getFont(){
        return font; 
    }
}
