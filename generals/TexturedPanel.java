package generals;

import javax.swing.*;
import java.awt.*;

public class TexturedPanel extends JPanel {
    private GameTexture texture;

    public TexturedPanel(GameTexture texture) {
        this.texture = texture;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (texture != null) {
            // Taille du panel
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            // Taille de l'image
            int imageWidth = texture.getWidth();
            int imageHeight = texture.getHeight();

            // Calcul des proportions pour conserver le ratio
            double scaleX = (double) panelWidth / imageWidth;
            double scaleY = (double) panelHeight / imageHeight;
            double scale = Math.min(scaleX, scaleY);

            // Nouvelle taille
            int newWidth = (int) (imageWidth * scale);
            int newHeight = (int) (imageHeight * scale);

            // Dessiner l'image redimensionnée et centrée
            int x = (panelWidth - newWidth) / 2;
            int y = (panelHeight - newHeight) / 2;
            g.drawImage(texture.getImage(), x, y, newWidth, newHeight, this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (texture != null) {
            return new Dimension(texture.getWidth(), texture.getHeight());
        } else {
            return super.getPreferredSize();
        }
    }
}