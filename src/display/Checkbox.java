package be.sioxox.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import be.sioxox.main.Main;

public class Checkbox extends JButton {

	private static final long serialVersionUID = 1L;
	private Image img;
	Main display;

	String file[];
	int state = 0;

	public Checkbox(int x, int y, int w, int h, String... file) {
		setBounds(x, y, w, h);

		this.file = file;

		try {
			img = ImageIO.read((new URL(file[0])));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		this.addActionListener(display);
		
		setBackground(new Color(0, 0, 0, 0));
		setBorderPainted(false);
		setVisible(true);
	}
	
	public void setState(int state) {
		if(state == 0 || state == 1)
			this.state = state;
	}
	
	public int getState() {
		return state;
	}
	
	public void setTexture(int numberTexture) {
		try {
			img = ImageIO.read(new URL(file[numberTexture]));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}

}