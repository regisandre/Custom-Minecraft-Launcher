package be.sioxox.display;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class PasswordField extends JPasswordField {
	 
    private Icon icon;
    
    public PasswordField() {
    	setOpaque(false);
    	setBorder(null);
    	
    	addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && !e.isConsumed()) {
					e.consume();
					selectAll();
				}
			}
		});
    }
 
    public void setIcon(Icon icon){
        this.icon = icon;
    }
    
    protected void paintComponent(Graphics g) {
        if(icon != null)
            icon.paintIcon(this, g, 0, 0);
        super.paintComponent(g);
    }
}