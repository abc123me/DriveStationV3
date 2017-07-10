package net.net16.jeremiahlowe.drivestation3.gui.driving;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JToggleButton;

import net.net16.jeremiahlowe.drivestation3.gui.driving.schemes.ControlScheme;

public class KeyboardController extends JPanel implements KeyListener{
	private static final long serialVersionUID = 1L;
	private JToggleButton btnW, btnA, btnS, btnD, btnQ, btnE;
	private ControlScheme cs;
	public KeyboardController(ControlScheme cs) {
		this.cs = cs;
		setFocusable(true);
		GridBagLayout gbl_keyboardControl = new GridBagLayout();
		gbl_keyboardControl.columnWidths = new int[]{43, 43, 43, 0};
		gbl_keyboardControl.rowHeights = new int[]{0, 0, 0};
		gbl_keyboardControl.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_keyboardControl.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gbl_keyboardControl);
		btnW = new JToggleButton(" W ");
		btnW.setEnabled(false);
		btnA = new JToggleButton(" A ");
		btnA.setEnabled(false);
		btnS = new JToggleButton(" S ");
		btnS.setEnabled(false);
		btnD = new JToggleButton(" D ");
		btnD.setEnabled(false);
		btnQ = new JToggleButton(" Q ");
		btnQ.setEnabled(false);
		btnE = new JToggleButton(" E ");
		btnE.setEnabled(false);
		GridBagConstraints gbc_tglbtnQ = new GridBagConstraints();
		gbc_tglbtnQ.fill = GridBagConstraints.BOTH;
		gbc_tglbtnQ.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnQ.gridx = 0;
		gbc_tglbtnQ.gridy = 0;
		add(btnQ, gbc_tglbtnQ);
		GridBagConstraints gbc_btnW = new GridBagConstraints();
		gbc_btnW.fill = GridBagConstraints.BOTH;
		gbc_btnW.insets = new Insets(0, 0, 5, 5);
		gbc_btnW.gridx = 1;
		gbc_btnW.gridy = 0;
		add(btnW, gbc_btnW);
		GridBagConstraints gbc_tglbtnE = new GridBagConstraints();
		gbc_tglbtnE.fill = GridBagConstraints.BOTH;
		gbc_tglbtnE.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnE.gridx = 2;
		gbc_tglbtnE.gridy = 0;
		add(btnE, gbc_tglbtnE);
		GridBagConstraints gbc_btnA = new GridBagConstraints();
		gbc_btnA.fill = GridBagConstraints.BOTH;
		gbc_btnA.insets = new Insets(0, 0, 0, 5);
		gbc_btnA.gridx = 0;
		gbc_btnA.gridy = 1;
		add(btnA, gbc_btnA);
		GridBagConstraints gbc_btnS = new GridBagConstraints();
		gbc_btnS.fill = GridBagConstraints.BOTH;
		gbc_btnS.insets = new Insets(0, 0, 0, 5);
		gbc_btnS.gridx = 1;
		gbc_btnS.gridy = 1;
		add(btnS, gbc_btnS);
		GridBagConstraints gbc_btnD = new GridBagConstraints();
		gbc_btnD.fill = GridBagConstraints.BOTH;
		gbc_btnD.gridx = 2;
		gbc_btnD.gridy = 1;
		add(btnD, gbc_btnD);
		addKeyListener(this);
	} 
	public void clearButtons(){
		btnW.setSelected(false);
		btnS.setSelected(false);
		btnA.setSelected(false);
		btnD.setSelected(false);
		btnQ.setSelected(false);
		btnE.setSelected(false);
	}
	@Override
	public void keyPressed(KeyEvent ke) {
		int w = KeyEvent.VK_W, s = KeyEvent.VK_S, a = KeyEvent.VK_A, d = KeyEvent.VK_D;
		boolean wasd = ke.getKeyCode() == w || ke.getKeyCode() == s || ke.getKeyCode() == a || ke.getKeyCode() == d;
		if(wasd && !btnQ.isSelected() && !btnE.isSelected()){
			btnW.setSelected(false);
			btnS.setSelected(false);
			btnA.setSelected(false);
			btnD.setSelected(false);
			switch(ke.getKeyCode()){
				case KeyEvent.VK_W: 
					cs.onForward();
					btnW.setSelected(true); 
					break;
				case KeyEvent.VK_A: 
					cs.onLeft();
					btnA.setSelected(true); 
					break;
				case KeyEvent.VK_S: 
					cs.onBackward();
					btnS.setSelected(true); 
					break;
				case KeyEvent.VK_D: 
					cs.onRight();
					btnD.setSelected(true); 
					break;
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent ke) {
		int w = KeyEvent.VK_W, s = KeyEvent.VK_S, a = KeyEvent.VK_A, d = KeyEvent.VK_D;
		boolean wasd = ke.getKeyCode() == w || ke.getKeyCode() == s || ke.getKeyCode() == a || ke.getKeyCode() == d;
		if(!btnQ.isSelected() && !btnE.isSelected()){
			if(wasd){
				cs.onStop();
				switch(ke.getKeyCode()){
					case KeyEvent.VK_W: btnW.setSelected(false); break;
					case KeyEvent.VK_A: btnA.setSelected(false); break;
					case KeyEvent.VK_S: btnS.setSelected(false); break;
					case KeyEvent.VK_D: btnD.setSelected(false); break;
				}
			}
		}
		switch(ke.getKeyCode()){
			case KeyEvent.VK_Q: 
				if(!btnE.isSelected()){
					btnQ.setSelected(!btnQ.isSelected());
					btnS.setSelected(btnQ.isSelected());
					btnW.setSelected(false);
					if(!wasd){
						if(btnQ.isSelected()) cs.onBackward();
						else cs.onStop();
					}
				}
				break;
			case KeyEvent.VK_E: 
				if(!btnQ.isSelected()){
					btnE.setSelected(!btnE.isSelected());
					btnS.setSelected(false);
					btnW.setSelected(btnE.isSelected());
					if(!wasd){
						if(btnE.isSelected()) cs.onForward();
						else cs.onStop();
					}
				}
				break;
		}
	}
	@Override
	public void keyTyped(KeyEvent ke) {}
}
