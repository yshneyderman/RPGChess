import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameMain{
 public static void main(String[] args){
	 
	//title of the window
	JFrame frame = new JFrame("RPGChess");
	//taskbar Icon
	ImageIcon img = new ImageIcon("images//Mage.PNG");
	frame.setIconImage(img.getImage());
	
	//JFrame stuff
	Container c = frame.getContentPane();
	GamePanel p = new GamePanel();
	c.add(p);
	frame.pack();
	//fullscreen if desired
	//frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

 	}
}