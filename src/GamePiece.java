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
	final int maxHealth;
	private String imageURL;
	private String name;
	private int xp;
	private boolean selected;
	
	public GamePiece(String name, String team, int x, int y, int health, int damage, int level, Ability ability, String imageURL) {
		this.team = team;
		this.name = name;
		this.health = health;
		this.maxHealth = health;
		this.level = level;
		this.damage = damage;
		this.x = x;
		this.y = y;
		this.ability = ability;
		this.imageURL = imageURL;
		this.xp = 0;
		this.selected = false;
	}
	
	public String getName() {
		return name;
	}
	public Ability getAbility() {
		return ability;
	}
	
	public int getAbilityType() {
		return ability.getType();
	}
	
	public String getAbilityName() {
		return ability.getName();
	}
	
	public String[] getAbilityDescription() {
		return ability.getDescription();
	}
	
	public void updateAbilityDescription(String[] description) {
		ability.updateDescription(description);
	}
	
	public int getHealth(){
		return health;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public ImageIcon getImage() {
		return new ImageIcon(imageURL);
	}
	
	public void setImage(String imageURL) {
		this.imageURL = imageURL;
	}
	
	public boolean isAlive() {
		return (getHealth() > 0);
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setXY(int x2, int y2) {
		this.x = x2;
		this.y = y2;
	}

	public String getTeam() {
		return team;
	}
	
	public int getXP() {
		return xp;
	}
	
	public void setXP(int xp) {
		this.xp = xp;
		if(xp<180) {
			this.setLevel(xp/20);
		}
		else {
			this.setLevel(9);
		}
	}
	
	public boolean isSelected() {
		return selected;
	}
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
	
	//writes the selected to a file
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
