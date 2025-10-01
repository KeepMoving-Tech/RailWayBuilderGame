package UI;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    // Konstruktor, amely betölti a képet
    public BackgroundPanel(String imagePath) {
        loadImage(imagePath);
    }

    // Kép betöltését végző metódus
    public void loadImage(String imagePath) {
        try {
            backgroundImage = ResourceLoader.loadImage(imagePath);  // Kép betöltése
            repaint();  // Újrarajzolás a kép frissítése után
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
