package generals;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class FormattedComboBox<E> extends JComboBox<E> {

    public FormattedComboBox(E[] items, Color fg, Color bg, Color boxBorder) {
        super(items);

        setBorder(BorderFactory.createLineBorder(boxBorder));

        setBackground(bg);

        setForeground(fg);
        
        GameFont font = new GameFont("cloneMachine"); 
        font = font.resize(20); 
        setFont(font.getFont());

        setUI(new FormattedComboBoxUI());
    }

    // Classe interne pour personnaliser le BasicComboBoxUI
    private static class FormattedComboBoxUI extends BasicComboBoxUI {

        @Override
        protected JButton createArrowButton() {
            // Remplacer la flèche par un composant vide ou null
            return null;
        }
    
        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            // Supprime le surlignement de l'élément sélectionné si nécessaire
            g.setColor(Color.RED);
            g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }
}