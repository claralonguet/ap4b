package generals;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.*;
import java.util.Map;

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

    public void setStrikeThrough(){
        @SuppressWarnings("unchecked")
        Map<TextAttribute, Object> attributes = (Map<TextAttribute, Object>) font.getAttributes();
        attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        this.font = font.deriveFont(attributes);
    }

    public GameFont resize(int size){
        font = font.deriveFont(Font.PLAIN, size); 
        return new GameFont(font); 
    }

    public Font getFont(){
        return font; 
    }
}
