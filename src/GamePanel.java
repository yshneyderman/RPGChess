import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {
	
	Game game = new Game();
	private ImageIcon backGround = new ImageIcon("BG.PNG");
	private ImageIcon whiteHighlight = new ImageIcon("whiteHighlight.PNG");
	private ImageIcon victoryBorder = new ImageIcon("VictoryBorder.PNG");
	
	//board info
	private int numCols = 16;
	private int numRows = 11;
	private int boardWidth = 1200;
	private int boardHeight = 949;
	private int colWidth = 75;
	private int rowHeight = 79;
	private int northWestCornerX = 25;
	private int northWestCornerY = 25;
	private int northEastCornerX = 1225;
	private int northEastCornerY = 25;
	private int southWestCornerX = 25;
	private int southWestCornerY = 974;
	private int southEastCornerX = 1225;
	private int southEastCornerY = 974;
	
	private int highlightX = 0;
	private int highlightY = 0;
	private boolean highlight = false;
	private int abilityX = 0;
	private int abilityY = 0;
	private boolean ability = false;
	private boolean moveStarted = false;
	private boolean attackStarted = false;
	private int spriteAnimation = 0;
	private int velocity = 1;
	Color background = new Color(79, 79, 79);
	Color outline = new Color(50, 50, 50);
	private GamePiece markedPiece;
	private boolean markPiece = false;
	private boolean moveOrAttack = false;
	
	ActionListener timeTicked = new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent ae) {
	        if(spriteAnimation == -3) {
	        	velocity = 1;
	        }
	        else if(spriteAnimation == 3){
	        	velocity = -1;
	        }
	        spriteAnimation += velocity;
	        repaint();
	    }
	};
	private Timer timer= new Timer(100, timeTicked);


	public GamePanel() {
		setPreferredSize(new Dimension(1900, 1000));
		setBackground(background);
		this.addMouseListener(this);
		addMouseMotionListener(this);
		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// background forest
		g.drawImage(backGround.getImage(), northWestCornerX, northWestCornerY, boardWidth, boardHeight, this);
		
		//thicker outlines
		g.setColor(outline);
		g.fillRect(northWestCornerX-1, northWestCornerY-1, boardWidth+2, 2); //top side
		g.fillRect(southWestCornerX-1, southWestCornerY-1, boardWidth+2, 2); //bottom side
		g.fillRect(northWestCornerX-1, northWestCornerY-1, 2, boardHeight+2); //left side
		g.fillRect(northEastCornerX, northEastCornerY-1, 2, boardHeight+2); //right side
	
		//character info slot
		g.fillRect(1700, 25, 175, 320);
		g.fillRect(1700, 354, 175, 70);
		
		//scorechart
		g.fillRect(1700, 450, 175, 190);
		g.fillRect(1700, 650, 175, 190);
		g.fillRect(1700, 850, 175, 125);
		g.setColor(Color.white);
		g.setFont(new Font("Ubuntu", 12, 12));
		g.drawString("Blue", 1710, 470);
		g.drawString("Total Damage Dealt: " + game.blueDamageDealt(), 1710, 486);
		g.setColor(Color.red);
		g.fillRect(1710, 492, 152, 15);
		g.setColor(Color.green);
		g.fillRect(1710, 492, (game.blueCurTotalHealth()*152)/game.blueMaxTotalHealth(), 15);
		g.setColor(Color.white);
		g.drawString("Red", 1710, 670);
		g.drawString("Total Damage Dealt: " + game.redDamageDealt(), 1710, 686);
		g.setColor(Color.red);
		g.fillRect(1710, 692, 152, 15);
		g.setColor(Color.green);
		g.fillRect(1710, 692, (game.redCurTotalHealth()*152)/game.redMaxTotalHealth(), 15);
		g.setColor(Color.white);
		
		// grid
		g.setColor(outline); 
		//columns where 100 is the offset for the first box (25+75) --> (NWCorner + colWidth)
		for (int i = 0; i < numCols; i++) {
			g.drawLine(100 + (colWidth * i), northWestCornerY, 100 + (colWidth * i), southWestCornerY);
		}
		//rows where 104 is the offset for the first box (25+79) --> (NWCorner + rowHeight)
		for (int j = 0; j < numRows; j++) {
			g.drawLine(northWestCornerX, 104 + (rowHeight * j), northEastCornerX, 104 + (rowHeight * j));
		}
		
		//draw numbers for reference
		g.setColor(Color.white);
		g.setFont(new Font("Ubuntu", 12, 12));
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 12; j++) {
				g.drawString(i + "," + j, 55 + (75 * i), 70 + (79 * j));
			}
		}
		
		//highlightPlace
		if(highlight) {
			g.drawImage(whiteHighlight.getImage(), northWestCornerX+1 + ((highlightX-northWestCornerX)/colWidth)*colWidth, northWestCornerY+1 + ((highlightY-northWestCornerY)/rowHeight)*rowHeight, colWidth-1, rowHeight-1, this);
		}
		
		//highlightPlace
		if(markPiece) {
			g.drawImage(whiteHighlight.getImage(), 26 + markedPiece.getX()*75, 26 + markedPiece.getY()*79, 74, 78, this);
		}	
		
		//moveOrAttackButtons
		if(moveOrAttack) {
			g.setColor(Color.white);
			g.drawString("Move", 1706, 367);
			g.fillRect(1706, 372, 77, 45);
			g.drawString("Attack", 1791, 367);
			g.fillRect(1791, 372, 77, 45);
		}
		
		
		//ability
		if(ability && game.getPiece(highlightX, highlightY) != null) {
			g.drawImage(whiteHighlight.getImage(), 26 + ((highlightX-25)/75)*75, 26 + ((highlightY-25)/79)*79, 74, 78, this);
			GamePiece temp = game.getPiece(abilityX, abilityY);
			g.drawImage(temp.getImage().getImage(), 1755, 32, 50, 50, this);
			g.setColor(Color.white);
			g.setFont(new Font("Ubuntu", 12, 12));
			g.drawString("Ability: " + temp.getAbilityName(), 1708, 96);
			for(int i = 0; i < temp.getAbilityDescription().length; i++)
			g.drawString(temp.getAbilityDescription()[i], 1708, 110+(12*i));
		}
		
		for(GamePiece p: game.board) {
			if(p != null && p.isAlive()) {
				g.setColor(Color.red);
				g.fillRect(32 + p.getX()*75, 29 + p.getY()*79, 60, 11);
				g.setColor(Color.green);
				g.fillRect(32 + p.getX()*75, 29 + p.getY()*79, (p.getHealth()*60)/p.getMaxHealth(), 11);
				g.setColor(Color.black);
				g.setFont(new Font("Ubuntu", 10, 9));
				g.drawString(Integer.toString(p.getHealth()) + " / " + Integer.toString(p.getMaxHealth()), 32 + p.getX()*75, 37 + p.getY()*79);
				g.setFont(new Font("Ubuntu", 10, 10));
				g.setColor(Color.white);
				g.drawString("Lvl: " + Integer.toString(p.getLevel()), 30 + p.getX()*75, 100 + p.getY()*79);
				g.drawString("Dmg: " + Integer.toString(p.getDamage()), 60 + p.getX()*75, 100 + p.getY()*79);
				g.drawImage(p.getImage().getImage(),36 + p.getX()*75, 43 + p.getY()*79 - spriteAnimation, 50, 50+spriteAnimation, this);
			}
		}
		
		if(game.isOver()) {
			g.setColor(Color.white);
			g.setFont(new Font("Ubuntu", 100, 100));
			g.drawImage(victoryBorder.getImage(),412, 212, 875, 575, this);
			g.drawString(game.getWinner() + " Wins!", 600, 400);
		}

		// save the game
		try {
			game.writeGame();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	private boolean onBoard(int x, int y) {
		return(x > northWestCornerX && y > northWestCornerY && x <southEastCornerX && y < southEastCornerY);
	}

	public void mouseClicked(MouseEvent e) {
		if(game.getPiece(e.getX(),e.getY()) != null && moveStarted == false && attackStarted == false) {//player will start the move
			moveOrAttack = true;
			markedPiece = game.markPiece(e.getX(), e.getY());
			markPiece = true;
		}
		else if(moveOrAttack == true && e.getX() >1706 && e.getY() > 372 && e.getX() < 1783 && e.getY() < 417) {//choose to move
			moveOrAttack = false;
			moveStarted = true;
		}
		else if(moveOrAttack == true && e.getX() >1791 && e.getY() > 372 && e.getX() < 1868 && e.getY() < 417) {//choose to attack
			moveOrAttack = false;
			attackStarted = true;
		}
		else if(moveOrAttack == false && attackStarted == true && game.getPiece(e.getX(),e.getY()) != null && game.isOpposite(markedPiece,game.getPiece(e.getX(),e.getY()))) {//player has selected move
			game.attackPiece(e.getX(), e.getY());
			resetMarkers();
		}
		else if(moveOrAttack == false && moveStarted && game.getPiece(e.getX(),e.getY()) == null && onBoard(e.getX(), e.getY())) {//player has selected move
			game.movePiece(e.getX(), e.getY());
			resetMarkers();
		}
		repaint();
	}
	
	public void resetMarkers() {
		moveStarted = false;
		markPiece = false;
		attackStarted = false;
		moveOrAttack = false;
	}

	public void mouseMoved(MouseEvent e) {
		if(onBoard(e.getX(), e.getY())) {
			if(game.getPiece(e.getX(), e.getY()) != null) {
				abilityX = e.getX();
				abilityY = e.getY();
				ability = true;
			}
			else {
				ability = false;
			}
			highlightX = e.getX();
			highlightY = e.getY();
			highlight = true;
		}
		else {
			highlight = false;
		}
		repaint();
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent arg0) {
	}
}
