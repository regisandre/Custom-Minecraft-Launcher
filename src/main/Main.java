package be.sioxox.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import be.sioxox.display.Button;
import be.sioxox.display.Checkbox;
import be.sioxox.display.PasswordField;
import be.sioxox.display.TextField;

public class Main extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;

	Image bg, logo;
	
	Button quit = new Button(false, 270, 10, 20, 20, Constants.defaultSite + "img/close.png");
	Button reduce = new Button(false, 245, 10, 20, 20, Constants.defaultSite + "img/reduce.png");
	Button options = new Button(false, 220, 10, 20, 20, Constants.defaultSite + "img/options.png", Constants.defaultSite + "img/home.png");
	Button launch = new Button(false, 190, 320, 100, 35, Constants.defaultSite + "img/launch1.png");
	
	Checkbox auth = new Checkbox(15, 320, 20, 20, Constants.defaultSite + "img/uncheckedbox.png", Constants.defaultSite + "img/checkedbox.png");
	Checkbox saveIDs = new Checkbox(15, 320, 20, 20, Constants.defaultSite + "img/uncheckedbox.png", Constants.defaultSite + "img/checkedbox.png");
	
	public TextField login = new TextField();
	PasswordField password = new PasswordField();
	
	static JFrame frame;
	Point point;
	
	JProgressBar progressBar = new JProgressBar(0, 100);
	
	public static void main(String args[]) throws MalformedURLException, IOException, FontFormatException {
		frame = new JFrame(Constants.name);
		frame.setSize(Constants.size);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(Constants.seeWindow);
		frame.add(new Main());
		frame.setVisible(true);
		frame.setBackground(new Color(0, 0, 0, 0));
		frame.setIconImage(ImageIO.read(new URL(Constants.defaultSite + "img/icon.png")));
	}
	
	public Main() throws MalformedURLException, IOException, FontFormatException {
		setLayout(null);

		bg = ImageIO.read(new URL(Constants.defaultSite + "img/background.png"));
		logo = ImageIO.read(new URL(Constants.defaultSite + "img/logo.png"));

		add(quit);
		add(reduce);
		add(options);
		add(launch);
		
		add(auth);
		add(saveIDs);
		
		add(login);
		add(password);
		
		quit.addActionListener(this);
		options.addActionListener(this);
		auth.addActionListener(this);
		saveIDs.addActionListener(this);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				point = e.getPoint();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {

				int thisX = frame.getLocation().x;
				int thisY = frame.getLocation().y;
				int xMoved = (thisX + e.getX()) - (thisX + point.x);
				int yMoved = (thisY + e.getY()) - (thisY + point.y);
				frame.setLocation(thisX + xMoved, thisY + yMoved);
			}
		});

		//Border border = BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK);
		URL fontUrl = new URL(Constants.defaultSite + "Minecraft.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream()).deriveFont(Font.CENTER_BASELINE, 14);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		
		login.setIcon(new ImageIcon(ImageIO.read(new URL(Constants.defaultSite + "img/textfield.png"))));
		login.setBounds(200, 200, 100, 20);
		login.setText("Test");
		login.setFont(font);
		login.setForeground(Color.WHITE);
		login.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent fe) {
			}

			public void focusGained(FocusEvent fe) {
				password.selectAll();
			}
		});
	}
	
	public String getLogin() {
		return login.getText();
	}
	
	@SuppressWarnings("deprecation")
	public String getPassword( ) {
		return password.getText();
	}

	protected void paintComponent(Graphics g) {
		g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
		g.drawImage(logo, 15, 30, 275, 50, this);
		frame.repaint();
	}

	public void actionPerformed(ActionEvent e) {
			if (e.getSource() == quit) {
				System.exit(0);
			} else if (e.getSource() == reduce) {
				frame.setState(JFrame.ICONIFIED);
			} else if (e.getSource() == launch) {
				
			} else if (e.getSource() == options) {
				if(options.getState() == 0) {
					options.setTexture(1);
					options.setState(1);
				} else {
					options.setTexture(0);
					options.setState(0);
				}
				
			} else if (e.getSource() == login) {
				
			} else if (e.getSource() == password) {
				
			} else if (e.getSource() == auth) {
				if(auth.getState() == 0) {
					auth.setTexture(1);
					auth.setState(1);
				} else {
					auth.setTexture(0);
					auth.setState(0);
				}
			} else if (e.getSource() == saveIDs) {
				if(saveIDs.getState() == 0) {
					saveIDs.setTexture(1);
					saveIDs.setState(1);
				} else {
					saveIDs.setTexture(0);
					saveIDs.setState(0);
				}
			}
			
			/*} else if (e.getSource() == website) {
				Desktop.getDesktop().browse(new URL("http://galkins.fr/").toURI());
			} else if (e.getSource() == openfolder) {
				Desktop.getDesktop().open(new File(Constants.directory + ""));
			}*/
	}
}