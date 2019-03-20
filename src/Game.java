import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	//game variables
	public boolean gameBegun = false;
	public boolean gameOver = false;
	
	public GamePiece[] board;
	public GamePiece[] characters;
	
	GamePiece markedPiece = null;
	private int redMax = 0;
	private int blueMax = 0;
	private int redDmg = 0;
	private int blueDmg = 0;
	private String winner;
	
	public Game() {
		//initializes the game
		board = new GamePiece[10];
		//attempt to load the game
		try {
			importPieces(board);
		} catch (FileNotFoundException e) {
			//default
			e.printStackTrace();
		}
	}
	
	//checks updates conditions to move towards endgame
	public void update() {
		if(blueCurTotalHealth() <= 0) {
			endGame();
			winner = "Red";
		}
		else if(redCurTotalHealth() <= 0) {
			endGame();
			winner = "Blue";
		}
	}
	
	//returns the winner of the game as a String
	public String getWinner() {
		return winner;
	}

	//begins the game
	public void beginGame() {
		gameBegun = true;
		
	}
	
	//restarts gane
	public void restart() {
		gameBegun = false;
		gameOver = false;
		board = new GamePiece[10];
		//attempt to load the game
		markedPiece = null;
		redMax = 0;
		blueMax = 0;
		redDmg = 0;
		blueDmg = 0;
		winner= null;
		try {
			importPieces(board);
		} catch (FileNotFoundException e) {
			//default
			e.printStackTrace();
		}
	}
	
	//returns true if game has started, false otherwise
	public boolean isBegun() {
		return gameBegun;
	}
	
	//ends the current game
	public void endGame() {
		gameOver = true;
	}

	//returns true if the game is over, false otherwise
	public boolean isOver() {
		return gameOver;
	}
	
	//returns int of the total damage all blue characters have dealt during the game
	public int blueDamageDealt() {
		return blueDmg;
	}
	
	//returns int of the total damage all red characters have dealt during the game
	public int redDamageDealt() {
		return redDmg;
	}
	
	//returns int of the total remaining health of all blue characters
	public int blueCurTotalHealth() {
		int sum = 0;
		for(int i = 0; i < 5; i++) {
			if(board[i] != null && board[i].isAlive()) {
				sum += board[i].getHealth();
			}
		}
		return sum;
	}
	
	//returns int of the total max health of all blue characters
	public int blueMaxTotalHealth() {
		if(blueMax != 0) {
			return blueMax;
		}
		int sum = 0;
		for(int i = 0; i < 5; i++) {
			sum += board[i].getMaxHealth();
		}
		blueMax = sum;
		return sum;
	}
	
	//returns int of the total remaining health of all red characters
	public int redCurTotalHealth() {
		int sum = 0;
		for(int i = 5; i < 10; i++) {
			if(board[i] != null && board[i].isAlive()) {
				sum += board[i].getHealth();
			}
		}
		return sum;
	}

	//returns int of the total max health of all red characters	
	public int redMaxTotalHealth() {
		if(redMax != 0) {
			return redMax;
		}
		int sum = 0;
		for(int i = 5; i < 10; i++) {
			sum += board[i].getMaxHealth();
		}
		redMax = sum;
		return sum;
	}
	
	//writes the game stats to a file
	public void writeGame() throws FileNotFoundException {
		PrintWriter write = new PrintWriter("save.txt");
		for(GamePiece p: characters) {
			write.write(Integer.toString(p.getXP()) + "\n");
		}
		write.close();
	}
	
	//imports the chosen pieces
	public void importPieces(GamePiece[] board) throws FileNotFoundException {
		//all possible gamePiece types
		GamePiece mage = new GamePiece("Mage", "Blue", 2, 1, 130, 20, 1, new Ability("Fireball", null , 1, 0), "mage.PNG");
		GamePiece sorcerer = new GamePiece("Sorcerer", "Blue", 2, 1, 110, 200, 1, new Ability("Incantation", null , 1, 0), "sorcerer.PNG");
		GamePiece knight = new GamePiece("Knight", "Blue", 3, 0, 230, 10, 1, new Ability("Slash", null, 1, 0), "knight.PNG");
		GamePiece paladin = new GamePiece("Paladin", "Blue", 3, 0, 250, 8, 1, new Ability("Shield Bash", null, 1, 0), "paladin.PNG");
		GamePiece archer = new GamePiece("Archer", "Blue", 4, 0, 140, 20, 1, new Ability("Arrow", null, 1, 0), "archer.PNG");
		GamePiece bowman = new GamePiece("Bowman", "Blue", 4, 0, 130, 25, 1, new Ability("Volley", null, 1, 0), "bowman.PNG");
		GamePiece lord = new GamePiece("Lord", "Blue", 5, 0, 170, 15, 1, new Ability("Glory", null, 1, 0), "lord.PNG");
		GamePiece king = new GamePiece("King","Blue", 5, 0, 180, 15, 1, new Ability("Majesty", null, 1, 0), "king.PNG");
		GamePiece assassin = new GamePiece("Assassin", "Blue", 4, 1, 100, 30, 1, new Ability("Knife", null, 1, 0), "assassin.PNG");
		GamePiece thief = new GamePiece("Thief","Blue", 4, 1, 100, 30, 1, new Ability("Swipe", null, 1, 0), "thief.PNG");
		
		GamePiece enemyMage = new GamePiece("Enemy Mage","Red", 2, 11, 130, 20, 1, new Ability("Fireball", null, 1, 0), "enemyMage.PNG");
		GamePiece enemyKnight = new GamePiece("Enemy Knight", "Red", 3, 10, 230, 10, 1, new Ability("Slash", null, 1, 0), "enemyKnight.PNG");
		GamePiece enemyArcher = new GamePiece("Enemy Archer", "Red", 4, 10, 140, 20, 1, new Ability("Arrow", null, 1, 0), "enemyArcher.PNG");
		GamePiece enemyLord = new GamePiece("Enemy Lord", "Red", 5, 10, 170, 15, 1, new Ability("Glory", null, 1, 0), "enemyLord.PNG");
		GamePiece enemyAssassin = new GamePiece("Enemy Assassin","Red", 4, 11, 100, 30, 1, new Ability("Knife", null, 1, 0), "enemyAssassin.PNG");
		
		//sets ability descriptions for each type
		mage.updateAbilityDescription(new String[]{"Casts a fireball", "dealing " + mage.getDamage() + " damage to", "the target location", "\n", "Pros: Med. Range & Damage", "Cons: Med. Health"});
		sorcerer.updateAbilityDescription(new String[]{"Summons an arcane bolt", "dealing " + sorcerer.getDamage() + " damage to", "the target location", "\n", "Pros: Med. Range & Damage", "Cons: Med. Health"});
		knight.updateAbilityDescription(new String[]{"Cleaves with the sword", "dealing " + knight.getDamage() + " damage to", "the target location", "\n", "Pros: High Health & AOE", "Cons: Low Damage"});
		paladin.updateAbilityDescription(new String[]{"Slams shield on ground", "dealing " + paladin.getDamage() + " damage to", "the target location", "\n", "Pros: High Health & AOE", "Cons: Low Damage"});
		archer.updateAbilityDescription(new String[]{"Fires an arrow", "dealing " + archer.getDamage() + " damage to", "the target location", "\n", "Pros: High Range", "Cons: Low Health"});
		bowman.updateAbilityDescription(new String[]{"Fires a volley", "dealing " + bowman.getDamage() + " damage to", "the target location", "\n", "Pros: High Range", "Cons: Low Health"});
		lord.updateAbilityDescription(new String[]{"Calls a rain of glory", "dealing " + lord.getDamage() + " damage to", "the target location", "\n", "Pros: Med. Damage & AOE", "Cons: Med. Health"});
		king.updateAbilityDescription(new String[]{"Strikes down his foes", "dealing " + king.getDamage() + " damage to", "the target location", "\n", "Pros: Med. Damage & AOE", "Cons: Med. Health"});
		assassin.updateAbilityDescription(new String[]{"Stabs with a knife", "dealing " + assassin.getDamage() + " damage to", "the target location", "\n", "Pros: High Damage", "Cons: Low Health"});
		thief.updateAbilityDescription(new String[]{"Lashes out", "dealing " + thief.getDamage() + " damage to", "the target location", "\n", "Pros: High Damage", "Cons: Low Health"});
		
		enemyMage.updateAbilityDescription(new String[]{"Casts a fireball", "dealing " + mage.getDamage() + " damage to", "the target location", "\n", "Pros: Med. Range & Damage", "Cons: Med. Health"});
		enemyKnight.updateAbilityDescription(new String[]{"Cleaves with the sword", "dealing " + knight.getDamage() + " damage to", "the target location", "\n", "Pros: High Health & AOE", "Cons: Low Damage"});
		enemyArcher.updateAbilityDescription(new String[]{"Fires an arrow", "dealing " + archer.getDamage() + " damage to", "the target location", "\n", "Pros: High Range", "Cons: Low Health"});
		enemyLord.updateAbilityDescription(new String[]{"Calls a rain of glory", "dealing " + lord.getDamage() + " damage to", "the target location", "\n", "Pros: Med. Damage & AOE", "Cons: Med. Health"});
		enemyAssassin.updateAbilityDescription(new String[]{"Stabs with a knife", "dealing " + assassin.getDamage() + " damage to", "the target location", "\n", "Pros: High Damage", "Cons: Low Health"});
		
		GamePiece[] options = {mage, sorcerer, archer, bowman, knight, paladin, assassin, thief, lord, king, enemyMage, enemyKnight, enemyArcher, enemyLord, enemyAssassin};
		characters = options;
		

		//imports the 10 pieces
		FileReader read1 = new FileReader("importPieces.txt");
		Scanner sc1 = new Scanner(read1);
		int index = 0;
		while (sc1.hasNextLine()) {
			String lineArg = sc1.nextLine();
			board[index] = 	options[Integer.parseInt(lineArg)];
			options[Integer.parseInt(lineArg)].select();
			index++;
		}
		sc1.close();
		
		//imports thhe xp of all the pieces
		FileReader read2 = new FileReader("save.txt");
		Scanner sc2 = new Scanner(read2);
		index = 0;
		while (sc2.hasNextLine()) {
			String lineArg = sc2.nextLine();
			options[index].setXP(Integer.parseInt(lineArg));
			index++;
		}
		sc2.close();
	}
	
	//sets the marked piece to be the one at the passed coordinates and returns it
	public GamePiece markPiece(int x, int y) {
		markedPiece = getPiece(x,y);
		return markedPiece;
	}
	
	//returns the piece at the coordinates
	public GamePiece getPiece(int x, int y) {
		for(GamePiece p: board) {
			if(p != null && p.getX() == ((x-25)/75) && p.getY() == ((y-25)/79)) {
				return p;
			}
		}
		return null;
	}
	
	//moves the markedPiece to the passed coordinates
	public void movePiece(int x, int y) {
		if(markedPiece != null && getPiece(x,y) == null) {
			markedPiece.setX(((x-25)/75));
			markedPiece.setY(((y-25)/79));
			markedPiece = null;
		}
	}
	
	//attack the piece at the coordinates from the markedPiece
	public void attackPiece(int x, int y) {
		if(markedPiece != null && getPiece(x,y) != null) {
			int dmg = markedPiece.getDamage();
			if(markedPiece.getTeam().equals("Blue")) {
				blueDmg += dmg;
			}
			else {
				redDmg += dmg;
			}
			GamePiece curr = getPiece(x,y);
			curr.setHealth(curr.getHealth() - dmg);
			update();
			markedPiece = null;
		}
	}
	
	//returns true if the two passed pieces are different colors, false if they are the same color
	public boolean isOpposite(GamePiece piece1, GamePiece piece2) {
		return(!piece1.getTeam().equals(piece2.getTeam()));
	}

	public void select(int i, int j) {
		int index = 10*j + i-(i/3);
		System.out.println(Integer.toString(index));
		if(characters[index].isSelected()) {
			characters[index].deselect();
		}
		else {
			characters[index].select();	
		}
	}
}