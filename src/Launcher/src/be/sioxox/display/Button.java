package be.sioxox.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import be.sioxox.main.Main;

public class Button extends JButton implements MouseListener {

	private static final long serialVersionUID = 1L;
	private Image img;
	Main display;

	String file[];
	int state = 0;

	public Button(boolean type, int x, int y, int w, int h, String... file) {
		setBounds(x, y, w, h);

		this.file = file;

		try {
			img = ImageIO.read((new URL(file[0])));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		if(type)
			addMouseListener(this);
		
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

	public void mouseEntered(MouseEvent e) {
		try {
			img = ImageIO.read(new URL(file[1]));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void mouseExited(MouseEvent e) {
		try {
			img = ImageIO.read(new URL(file[0]));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void mouseClicked(MouseEvent e) {
		
	}
	public void mousePressed(MouseEvent e) {
		
	}
	public void mouseReleased(MouseEvent e) {
		
	}

}