package net.net16.jeremiahlowe.drivestation3.gui.misc;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

public class ImagePanel extends JComponent {
	private Image image;
	public ImagePanel() {
		this(null);
	}
	public ImagePanel(Image image) {
		this.image = image;
	}
	@Override
	public void paintComponent(Graphics g){
		if(image != null) g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		else g.drawString("Missing image!", getWidth() / 2, getHeight() / 2);
	}
}
