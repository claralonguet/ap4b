package generals;

import java.awt.*;
import javax.swing.*;

public class FormattedLabel extends JLabel{
    public FormattedLabel(String text, Color fgColor, String fontName, int fontSize){
        super(text);
        setForeground(fgColor);
        setHorizontalAlignment(SwingConstants.CENTER);
        
        GameFont f = new GameFont(fontName);
        f = f.resize(fontSize);
        setFont(f.getFont()); 
    }
}
