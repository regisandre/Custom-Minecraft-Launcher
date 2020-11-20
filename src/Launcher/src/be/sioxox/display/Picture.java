package be.sioxox.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class Picture extends JComponent {

	private static final long serialVersionUID = 1L;
	private Image img;

	public Picture(int x, int y, int w, int h, String file) {
		setBounds(x, y, w, h);

		try {
			img = ImageIO.read((new URL(file)));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		setBackground(new Color(0, 0, 0, 0));
		setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
		setVisible(true);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}
}
