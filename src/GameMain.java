import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameMain{
 public static void main(String[] args){
	 
	JFrame frame = new JFrame("RPGChess");
	ImageIcon img = new ImageIcon("Mage.PNG");
	frame.setIconImage(img.getImage());
	Container c = frame.getContentPane();
	GamePanel p = new GamePanel();
	c.add(p);
	frame.pack();
	//frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

 	}
}