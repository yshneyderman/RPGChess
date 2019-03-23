import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {
	//starts a new game
	Game game = new Game();
	//background forest
	private ImageIcon forestBackGround = new ImageIcon("images//BG.PNG");
	private ImageIcon startScreen = new ImageIcon("images//StartScreen.PNG");
	private ImageIcon teamSelectScreen = new ImageIcon("images//TeamSelectScreen.PNG");
	
	private ImageIcon whiteHighlight = new ImageIcon("images//whiteHighlight.PNG");
	private ImageIcon victoryBorder = new ImageIcon("images//VictoryBorder.PNG");
	
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
	private boolean highlightStartGame = false;
	private boolean highlightTeamSelect = false;
	private boolean highlightBack = false;
	private boolean ability = false;
	private boolean moveStarted = false;
	private boolean attackStarted = false;
	private boolean teamSelect = false;
	private int spriteAnimation = 0;
	private int velocity = 1;
	Color backGround = new Color(79, 79, 79);
	Color outline = new Color(50, 50, 50);
	private GamePiece markedPiece;
	private boolean markPiece = false;
	private boolean moveOrAttack = false;
	private boolean endgame = false;
	
	//timer actionlistener
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
	//timer with tickrate of 100ms
	private Timer timer= new Timer(100, timeTicked);


	//gamePanel constructor
	public GamePanel() {
		setPreferredSize(new Dimension(1900, 1000));
		setBackground(backGround);
		this.addMouseListener(this);
		addMouseMotionListener(this);
		timer.start();
	}
	
	//bulk of the graphics work - takes in graphics object and paints everything to the canvas
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!game.isBegun() && teamSelect == false) {
			g.drawImage(startScreen.getImage(), 0, 0, 1900, 1000, this);
			if(highlightStartGame) {
				g.drawImage(whiteHighlight.getImage(), 460, 260, 990, 200, this);
			}
			else if(highlightTeamSelect) {
				g.drawImage(whiteHighlight.getImage(), 560, 560, 800, 110, this);
			}
		}
		else if(!game.isBegun() && teamSelect) {
			g.drawImage(teamSelectScreen.getImage(), 0, 0, 1900, 1000, this);
			if(highlightBack) {
				g.drawImage(whiteHighlight.getImage(), 50, 30, 245, 70, this);
			}
			if(highlight) {
				int x = (((highlightX-85)/92)*92) + 75;
				int y = ((highlightY-220)/140)*140 + 210;
				g.drawImage(whiteHighlight.getImage(), x, y, 92, 140, this);
			}
			//starting coords of the first option
			int x = 85;
			int y = 220;
			//draws out all the options
			for(int i = 0; i<20; ++i) {
				GamePiece p =  game.characters[i];
				g.setColor(Color.green);
				g.fillRect(x, y, (p.getHealth()*70)/p.getMaxHealth(), 11);
				g.setColor(Color.black);
				g.setFont(new Font("Arial", 10, 9));
				g.drawString(Integer.toString(p.getHealth()) + " / " + Integer.toString(p.getMaxHealth()), x+2, y+9);
				g.setFont(new Font("Arial", 10, 10));
				g.setColor(Color.white);
				g.drawString(p.getName(), x, 95 + y);
				g.drawString("Lvl: " + Integer.toString(p.getLevel()), x, 106 + y);
				g.drawString("Dmg: " + Integer.toString(p.getDamage()), 30 + x, 106 + y);
				if(p.isSelected()) {
					g.drawImage(whiteHighlight.getImage(), x-10, y-10, 92, 140, this);
				}
				g.drawImage(p.getImage().getImage(),3 + x, 25 + y - spriteAnimation, 60, 60+spriteAnimation, this);
				g.drawString("XP: " + Integer.toString(p.getXP()), x, 117 + y);
				//change this info when adding more units
				if((i+1)%3==0) {
					x = x + 184;
				}
				else {
					x = x + 92;
				}
				if((i+1)%15 == 0) {
					x = 85;
					y = y + 140;
				}
			}
		}
		else if(game.isBegun()) {
			// background forest
			g.drawImage(forestBackGround.getImage(), northWestCornerX, northWestCornerY, boardWidth, boardHeight, this);
			
			//thicker outlines
			g.setColor(outline);
			g.fillRect(northWestCornerX-1, northWestCornerY-1, boardWidth+2, 2); //top side
			g.fillRect(southWestCornerX-1, southWestCornerY-1, boardWidth+2, 2); //bottom side
			g.fillRect(northWestCornerX-1, northWestCornerY-1, 2, boardHeight+2); //left side
			g.fillRect(northEastCornerX, northEastCornerY-1, 2, boardHeight+2); //right side
		
			//character info slot
			g.fillRect(1700, 25, 175, 320);
			g.fillRect(1700, 354, 175, 70);
			
			//move history background
			g.fillRect(1255, 25, 420, 400);
			g.setColor(Color.white);
			g.setFont(new Font("Arial", 12, 12));
			g.drawString("Move History", 1270, 47);
			for(int i = 0; i<game.getMoves().size(); ++i) {
				g.drawString(game.getMoves().get(i), 1270, 63+14*i);
			}
			
			g.setColor(outline);
			
			//scorechart
			g.fillRect(1700, 450, 175, 190);
			g.fillRect(1700, 650, 175, 190);
			g.fillRect(1700, 850, 175, 125);
			g.setColor(Color.white);
			g.setFont(new Font("Arial", 12, 12));
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
			g.setFont(new Font("Arial", 12, 12));
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
				g.setFont(new Font("Arial", 12, 12));
				g.drawString(temp.getName(), 1708, 96);
				g.drawString("Ability: " + temp.getAbilityName(), 1708, 116);
				for(int i = 0; i < temp.getAbilityDescription().length; i++)
				g.drawString(temp.getAbilityDescription()[i], 1708, 129+(12*i));
			}
			
			//healthbars, levels, attack, and characters
			for(GamePiece p: game.board) {
				if(p != null && p.isAlive()) {
					g.setColor(Color.red);
					g.fillRect(32 + p.getX()*75, 29 + p.getY()*79, 60, 11);
					g.setColor(Color.green);
					g.fillRect(32 + p.getX()*75, 29 + p.getY()*79, (p.getHealth()*60)/p.getMaxHealth(), 11);
					g.setColor(Color.black);
					g.setFont(new Font("Arial", 10, 9));
					g.drawString(Integer.toString(p.getHealth()) + " / " + Integer.toString(p.getMaxHealth()), 34 + p.getX()*75, 38 + p.getY()*79);
					g.setFont(new Font("Arial", 9, 9));
					g.setColor(Color.white);
					g.drawString("Lvl: " + Integer.toString(p.getLevel()), 29 + p.getX()*75, 100 + p.getY()*79);
					g.drawString("Dmg: " + Integer.toString(p.getDamage()), 58 + p.getX()*75, 100 + p.getY()*79);
					g.drawImage(p.getImage().getImage(),36 + p.getX()*75, 43 + p.getY()*79 - spriteAnimation, 50, 50+spriteAnimation, this);
				}
			}
		}
		//draw victory border and paint winner
		if(game.isOver()) {
			g.setColor(Color.white);
			g.setFont(new Font("Arial", 100, 100));
			g.drawImage(victoryBorder.getImage(),412, 212, 875, 575, this);
			g.drawString(game.getWinner() + " Wins!", 600, 400);
			if(!endgame) {
				for(GamePiece p: game.board) {
					p.setXP(p.getXP() + 1);
				}
				try {
					game.writeGame();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			endgame = true;
		}
	}
	
	//returns true if the passed coordinates are within the bounds of the 12x16 board
	private boolean onBoard(int x, int y) {
		return(x > northWestCornerX && y > northWestCornerY && x <southEastCornerX && y < southEastCornerY);
	}

	//what to do if mouse is clicked
	public void mouseClicked(MouseEvent e) {
		//if game is not yet begun
		if(!game.isBegun()) {
			//if player clicks start game
			if(!teamSelect && e.getX() > 460 && e.getX() < 1440 && e.getY() > 260 && e.getY() < 465) {
				game.beginGame();
			}
			//if player clicks choose team
			else if(!teamSelect && e.getX() > 560 && e.getX() < 1360 && e.getY() > 560 && e.getY() < 670) {
				//go to choose team page
				teamSelect = true;
			}
			//if exits teamselect
			else if(teamSelect && e.getX() > 50 && e.getX() < 295 && e.getY() > 30 && e.getY() < 100) {
				//sets teamselect to be false
				highlightBack = false;
				teamSelect = false;
			}
			else if(teamSelect && e.getY() < 920 && e.getY() > 220 && e.getX() > 50 && e.getX() < 1850 && (((e.getX()-85)/92)+1)%4 != 0) {
				game.select((((e.getX()-85)/92)), ((e.getY()-220)/140), game.characters);
			}
		}
		//if game is already begun
		else if(game.isBegun()){
			//if player clicks a location on the board with a piece and has not yet marked a piece or started a move
			if(game.getPiece(e.getX(),e.getY()) != null && moveStarted == false && attackStarted == false && game.getPiece(e.getX(),e.getY()).getTeamAsInt() == game.getTurn()) {//player will start the move
				moveOrAttack = true;
				markedPiece = game.markPiece(e.getX(), e.getY());
				markPiece = true;
			}
			//if player clicks location of the move button when he has a piece selected
			else if(moveOrAttack == true && e.getX() >1706 && e.getY() > 372 && e.getX() < 1783 && e.getY() < 417) {
				moveOrAttack = false;
				moveStarted = true;
			}
			//if player clicks location of the attack button when he has a piece selected
			else if(moveOrAttack == true && e.getX() >1791 && e.getY() > 372 && e.getX() < 1868 && e.getY() < 417) {
				moveOrAttack = false;
				attackStarted = true;
			}
			//if player has selected to attack and clicks a valid spot that has an opposite player on it
			else if(moveOrAttack == false && attackStarted == true && game.getPiece(e.getX(),e.getY()) != null && game.isOpposite(markedPiece,game.getPiece(e.getX(),e.getY()))) {
				game.attackPiece(e.getX(), e.getY());
				resetMarkers();
			}
			//if player has selected to move and clicks a valid spot that does not already have a piece on it
			else if(moveOrAttack == false && moveStarted && game.getPiece(e.getX(),e.getY()) == null && onBoard(e.getX(), e.getY())) {
				game.movePiece(e.getX(), e.getY());
				resetMarkers();
			}
		}
		//if game is over
		if(game.isOver()) {
			if(e.getX() > 560 && e.getX() < 1360 && e.getY() > 560 && e.getY() < 670) {
				game.restart();
				endgame = false;
			}
		}
		repaint();
	}
	
	//resets all of the markers for the board state
	public void resetMarkers() {
		moveStarted = false;
		markPiece = false;
		attackStarted = false;
		moveOrAttack = false;
	}
	
	//mouse movement listener
	public void mouseMoved(MouseEvent e) {
		//if moved on board
		if(!game.isBegun()) {
			if(!teamSelect) {
				if(e.getX() > 460 && e.getX() < 1440 && e.getY() > 260 && e.getY() < 465) {
					highlightStartGame = true;
					highlightTeamSelect = false;
				}
				else if(e.getX() > 560 && e.getX() < 1360 && e.getY() > 560 && e.getY() < 670) {
					highlightTeamSelect = true;
					highlightStartGame = false;
				}
				else {
					highlightStartGame = false;
					highlightTeamSelect = false;
				}
			}
			//if on the team select screen
			else if(teamSelect) {
				//if in the character selection area
				if(e.getY() < 920 && e.getY() > 220 && e.getX() > 50 && e.getX() < 1850 && (((e.getX()-85)/92)+1)%4 != 0) {
					highlightX = e.getX();
					highlightY = e.getY();
					highlight = true;
					highlightBack = false;
				}
				//if clicking the button back
				else if(e.getX() > 50 && e.getX() < 295 && e.getY() > 30 && e.getY() < 100) {
					highlightBack = true;
					highlight = false;
					highlightStartGame = false;
					highlightTeamSelect = false;
				}
				else {
					highlight = false;
					highlightBack = false;
				}
			}
		}
		else if(game.isBegun() && onBoard(e.getX(), e.getY())) {
			//if moved over a valid location with character
			if(game.getPiece(e.getX(), e.getY()) != null) {
				abilityX = e.getX();
				abilityY = e.getY();
				ability = true;
			}
			else {
				ability = false;
			}
			//highlight the board square lightly
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
