package net.net16.jeremiahlowe.drivestation3.assets;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class AssetGetter {
	public static final String ASSET_DIRECTORY = "/net/net16/jeremiahlowe/drivestation3/assets/";
	public static Image getImage(String name){
		return Toolkit.getDefaultToolkit().getImage(AssetGetter.class.getResource(ASSET_DIRECTORY + name));
	}
	public static ImageIcon getIcon(String name){
		return new ImageIcon(name);
	}
}
