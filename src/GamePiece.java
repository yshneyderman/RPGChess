import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class GamePiece {
	private int health;
	private int level;
	private int x;
	private int y;
	private Ability ability;
	private int damage;
	private String team;
	private int maxHealth;
	private String imageURL;
	private String name;
	private int xp;
	private boolean selected;
	
	//GamePiece constructor
	public GamePiece(String name, String team, int x, int y, int health, int damage, int level, Ability ability, String imageURL) {
		this.team = team;
		this.name = name;
		this.health = health + health*(level/4);
		this.maxHealth = health + health*(level/4);
		this.level = level;
		this.damage = damage+damage*(level/3);
		this.x = x;
		this.y = y;
		this.ability = ability;
		this.imageURL = imageURL;
		this.xp = 0;
		this.selected = false;
	}
	
	//return the name of the piece (ie. "Archer")
	public String getName() {
		return name;
	}
	
	//returns the ability of the piece as an Ability object
	public Ability getAbility() {
		return ability;
	}
	
	//returns an integer representation of the ability type
	public int getAbilityType() {
		return ability.getType();
	}
	
	//returns the name of the ability
	public String getAbilityName() {
		return ability.getName();
	}
	
	//returns a formatted String array of the ability description
	public String[] getAbilityDescription() {
		return ability.getDescription();
	}
	
	//updates the ability description of the piece
	public void updateAbilityDescription(String[] description) {
		ability.updateDescription(description);
	}
	
	//returns an integer representation of the health of the piece
	public int getHealth(){
		return health;
	}
	
	//returns an integer representation of the level of the piece
	public int getLevel() {
		return level;
	}
	
	//updates the level of the piece to the int passed to it (from 1 to 9)
	public void setLevel(int level) {
		this.level = level;
	}
	
	//returns the x coordinate from 0 to 15 of the piece on the board
	public int getX(){
		return x;
	}
	
	//returns the y coordinate from 0 to 11 of the piece on the board
	public int getY() {
		return y;
	}
	
	//returns the image of the piece
	public ImageIcon getImage() {
		return new ImageIcon(imageURL);
	}
	
	//sets the image of the piece (used if we decide to change images to represent leveling up)
	public void setImage(String imageURL) {
		this.imageURL = imageURL;
	}
	
	//returns true if the health of the piece is strictly greater than 0
	public boolean isAlive() {
		return (getHealth() > 0);
	}

	//updates the health of the piece
	public void setHealth(int health) {
		this.health = health;
	}

	//updates the x coordinates of the piece on the board
	public void setX(int x) {
		this.x = x;
	}
	
	//updates the y coordinates of the piece on the board
	public void setY(int y) {
		this.y = y;
	}
	
	//returns the damage of the piece
	public int getDamage() {
		return damage;
	}
	
	//updates the damage of the piece
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	//returns the int starting/max health of the piece - used for the healthbars
	public int getMaxHealth() {
		return maxHealth;
	}

	//sets the x and y coordinates of the piece(used for moving) from 0 to 15 on the x and 0 to 11 on the y
	public void setXY(int x2, int y2) {
		this.x = x2;
		this.y = y2;
	}

	//returns a string representation of the team the piece is on: "Red" or "Blue"
	public String getTeam() {
		return team;
	}
	
	//returns an integer representation of the team the piece is on: 0 for blue and 1 for red
	public int getTeamAsInt() {
		if(team.equals("Red")) {
			return 1;
		}
		return 0;
	}
	
	//returns the xp as an int
	public int getXP() {
		return xp;
	}
	
	//sets the xp of the piece and updates its health and damage based on that score
	public void setXP(int xp) {
		this.xp = xp;
		if(xp<160) {
			this.setLevel(1 + xp/20);
			this.setDamage(damage+damage*(level/3));
			this.setHealth(health+health*(level/4));
			maxHealth = this.getHealth();
		}
		else {
			this.setLevel(9);
			this.setDamage(damage+damage*(level/3));
			this.setHealth(health+health*(level/4));
			maxHealth = this.getHealth();
		}
	}
	
	//returns true if the piece is selected
	public boolean isSelected() {
		return selected;
	}
	
	//selects the piece for import if it was deselected from the "select team" screen
	public void select(GamePiece piece, GamePiece[] characters, Game game, GamePiece[] board) {
		selected = true;
		try {
			writeSelection(piece, characters);
			reImport(piece, characters, board);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//deselects the piece from the import if it has already been selected on the "select team" screen
	public void deselect(GamePiece piece, GamePiece[] characters, Game game, GamePiece[] board) {
		selected = false;
		try {
			writeSelection(piece, characters);
			reImport(piece, characters, board);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//reimports the pieces after new ones have been selected or deselected
	public void reImport(GamePiece piece, GamePiece[] characters, GamePiece[] board) {
		//imports the 10 pieces
		FileReader read1;
		try {
			read1 = new FileReader("importPieces.txt");
			Scanner sc1 = new Scanner(read1);
			int index = 0;
			while (sc1.hasNextLine() && index<10) {
				String lineArg = sc1.nextLine();
				board[index] = 	characters[Integer.parseInt(lineArg)];
				characters[Integer.parseInt(lineArg)].selected = true;
				index++;
			}
			sc1.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//writes the selected pieces to a file
	public void writeSelection(GamePiece piece, GamePiece[] characters) throws FileNotFoundException {
		PrintWriter write = new PrintWriter("importPieces.txt");
		for(int i = 0; i < characters.length; ++i){
			if(characters[i].isSelected()) {
				write.write(Integer.toString(i) + "\n");
			}
		}
		write.close();
		}
}
