import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	//game variables
	public boolean gameBegun = false;
	public boolean gameOver = false;
	
	private ArrayList<String> moves;
	public GamePiece[] board;
	public GamePiece[] characters;
	
	GamePiece markedPiece = null;
	private int redMax = 0;
	private int blueMax = 0;
	private int redDmg = 0;
	private int blueDmg = 0;
	private String winner;
	private int turn;
	
	public Game() {
		//initializes the game
		moves = new ArrayList<String>(1);
		board = new GamePiece[20];
		turn = 0;
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
		if(turn == 0) {
			turn = 1;
		}
		else {
			turn = 0;
		}
	}
	
	//returns the most recent moves
	public ArrayList<String> getMoves(){
		return moves;
	}
	
	//type 0 is move, type 1 is attack
	public void updateMoves(int x, int y, GamePiece piece, int type) {
		//move
		if(type == 0) {
			moves.add(piece.getTeam() + " moved " + piece.getName() + " from: " + Integer.toString(x) +", " + Integer.toString(y) + " to: " + Integer.toString(piece.getX()) +", " + Integer.toString(piece.getY()));
		}
		//invalid move
		else if(type == 1) {
			moves.add("Invalid move");
		}
		//attack
		else {
			moves.add(piece.getTeam() + " attacked " + getPiece(x, y).getName() + " with " + piece.getName() + " for " + piece.getDamage() + " damage");
		}
		if(moves.size() > 20) {
			moves.remove(0);
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
		board = new GamePiece[20];
		//attempt to load the game
		markedPiece = null;
		redMax = 0;
		blueMax = 0;
		redDmg = 0;
		blueDmg = 0;
		turn = 0;
		winner= null;
		moves.clear();
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
		for(int i = 0; i < 10; i++) {
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
		for(int i = 0; i < 10; i++) {
			sum += board[i].getMaxHealth();
		}
		blueMax = sum;
		return sum;
	}
	
	//returns int of the total remaining health of all red characters
	public int redCurTotalHealth() {
		int sum = 0;
		for(int i = 10; i < 20; i++) {
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
		for(int i = 10; i < 20; i++) {
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
		GamePiece mage = new GamePiece("Mage", "Blue", 2, 1, 130, 20, 1, new Ability("Fireball", null , 1, 1), "images//mage.PNG", 0);
		GamePiece sorcerer = new GamePiece("Sorcerer", "Blue", 2, 1, 110, 23, 1, new Ability("Incantation", null , 1, 1), "images//sorcerer.PNG", 0);
		GamePiece necromancer = new GamePiece("Necromancer", "Blue", 2, 1, 100, 25, 1, new Ability("Curse", null , 1, 1), "images//necromancer.PNG", 0);
		GamePiece knight = new GamePiece("Knight", "Blue", 3, 0, 230, 10, 1, new Ability("Slash", null, 1, 3), "images//knight.PNG", 0);
		GamePiece paladin = new GamePiece("Paladin", "Blue", 3, 0, 250, 8, 1, new Ability("Shield Bash", null, 1, 3), "images//paladin.PNG", 0);
		GamePiece berserker = new GamePiece("Berserker", "Blue", 3, 0, 210, 13, 1, new Ability("Axe Fury", null, 1, 3), "images//Berserker.PNG", 0);
		GamePiece archer = new GamePiece("Archer", "Blue", 4, 0, 140, 20, 1, new Ability("Arrow", null, 1, 2), "images//archer.PNG", 0);
		GamePiece bowman = new GamePiece("Bowman", "Blue", 4, 0, 130, 25, 1, new Ability("Volley", null, 1, 2), "images//bowman.PNG", 0);
		GamePiece sniper = new GamePiece("Sniper", "Blue", 4, 0, 90, 29, 1, new Ability("Snipe", null, 1, 2), "images//sniper.PNG", 0);
		GamePiece lord = new GamePiece("Lord", "Blue", 5, 0, 170, 15, 1, new Ability("Glory", null, 1, 0), "images//lord.PNG", 0);
		GamePiece king = new GamePiece("King","Blue", 5, 0, 180, 13, 1, new Ability("Majesty", null, 1, 0), "images//king.PNG", 0);
		GamePiece emperor = new GamePiece("Emperor","Blue", 5, 0, 190, 11, 1, new Ability("Might", null, 1, 0), "images//emperor.PNG", 0);
		GamePiece assassin = new GamePiece("Assassin", "Blue", 4, 1, 100, 30, 1, new Ability("Knife", null, 1, 0), "images//assassin.PNG", 1);
		GamePiece thief = new GamePiece("Thief","Blue", 4, 1, 90, 34, 1, new Ability("Swipe", null, 1, 0), "images//thief.PNG", 1);
		GamePiece shadow = new GamePiece("Shadow","Blue", 4, 1, 80, 40, 1, new Ability("Consume", null, 1, 0), "images//shadow.PNG", 1);
		
		//enemy pieces
		GamePiece enemyMage = new GamePiece("Enemy Mage","Red", 2, 11, 130, 20, 1, new Ability("Fireball", null, 1, 1), "images//enemyMage.PNG", 0);
		GamePiece enemySorcerer = new GamePiece("Enemy Sorcerer", "Red", 2, 11, 110, 23, 1, new Ability("Incantation", null , 1, 1), "images//enemySorcerer.PNG", 0);
		GamePiece enemyKnight = new GamePiece("Enemy Knight", "Red", 3, 10, 230, 10, 1, new Ability("Slash", null, 1, 3), "images//enemyKnight.PNG", 0);
		GamePiece enemyPaladin = new GamePiece("Enemy Paladin", "Red", 3, 10, 250, 8, 1, new Ability("Shield Bash", null, 1, 3), "images//enemyPaladin.PNG", 0);
		GamePiece enemyArcher = new GamePiece("Enemy Archer", "Red", 4, 10, 140, 20, 1, new Ability("Arrow", null, 1, 2), "images//enemyArcher.PNG", 0);
		GamePiece enemyBowman = new GamePiece("Enemy Bowman", "Red", 4, 10, 130, 25, 1, new Ability("Volley", null, 1, 2), "images//enemyBowman.PNG", 0);
		GamePiece enemyLord = new GamePiece("Enemy Lord", "Red", 5, 10, 170, 15, 1, new Ability("Glory", null, 1, 0), "images//enemyLord.PNG", 0);
		GamePiece enemyKing = new GamePiece("Enemy King","Red", 5, 10, 180, 13, 1, new Ability("Majesty", null, 1, 0), "images//enemyKing.PNG", 0);
		GamePiece enemyAssassin = new GamePiece("Enemy Assassin","Red", 4, 11, 100, 30, 1, new Ability("Knife", null, 1, 0), "images//enemyAssassin.PNG", 1);
		GamePiece enemyThief = new GamePiece("Enemy Thief","Red", 4, 11, 90, 34, 1, new Ability("Swipe", null, 1, 0), "images//enemyThief.PNG", 1);
		
		//sets ability1 descriptions for each type
		mage.updateAbilityDescription(new String[]{"Casts a fireball", "dealing " + mage.getDamage() + " damage to", "the target location", "\n", "Pros: Med. Range & Damage", "Cons: Med. Health"});
		sorcerer.updateAbilityDescription(new String[]{"Summons an arcane bolt", "dealing " + sorcerer.getDamage() + " damage to", "the target location", "\n", "Pros: Med. Range & Damage", "Cons: Med. Health"});
		necromancer.updateAbilityDescription(new String[]{"Casts dark magic", "dealing " + necromancer.getDamage() + " damage to", "the target location", "\n", "Pros: Med. Range & Damage", "Cons: Med. Health"});
		knight.updateAbilityDescription(new String[]{"Cleaves with the sword", "dealing " + knight.getDamage() + " damage to", "the target location", "\n", "Pros: High Health & AOE", "Cons: Low Damage"});
		paladin.updateAbilityDescription(new String[]{"Slams shield on ground", "dealing " + paladin.getDamage() + " damage to", "the target location", "\n", "Pros: High Health & AOE", "Cons: Low Damage"});
		berserker.updateAbilityDescription(new String[]{"Twirls double axes", "dealing " + berserker.getDamage() + " damage to", "the target location", "\n", "Pros: High Health & AOE", "Cons: Low Damage"});
		archer.updateAbilityDescription(new String[]{"Fires an arrow", "dealing " + archer.getDamage() + " damage to", "the target location", "\n", "Pros: High Range", "Cons: Low Health"});
		bowman.updateAbilityDescription(new String[]{"Fires a volley", "dealing " + bowman.getDamage() + " damage to", "the target location", "\n", "Pros: High Range", "Cons: Low Health"});
		sniper.updateAbilityDescription(new String[]{"Takes a shot", "dealing " + sniper.getDamage() + " damage to", "the target location", "\n", "Pros: High Range", "Cons: Low Health"});
		lord.updateAbilityDescription(new String[]{"Calls a rain of glory", "dealing " + lord.getDamage() + " damage to", "the target location", "\n", "Pros: Med. Damage & AOE", "Cons: Med. Health"});
		king.updateAbilityDescription(new String[]{"Strikes down his foes", "dealing " + king.getDamage() + " damage to", "the target location", "\n", "Pros: Med. Damage & AOE", "Cons: Med. Health"});
		emperor.updateAbilityDescription(new String[]{"Rends the earth nearby", "dealing " + emperor.getDamage() + " damage to", "the target location", "\n", "Pros: Med. Damage & AOE", "Cons: Med. Health"});
		assassin.updateAbilityDescription(new String[]{"Stabs with a knife", "dealing " + assassin.getDamage() + " damage to", "the target location", "\n", "Pros: High Damage", "Cons: Low Health"});
		thief.updateAbilityDescription(new String[]{"Lashes out", "dealing " + thief.getDamage() + " damage to", "the target location", "\n", "Pros: High Damage", "Cons: Low Health"});
		shadow.updateAbilityDescription(new String[]{"Takes a bite", "dealing " + shadow.getDamage() + " damage to", "the target location", "\n", "Pros: High Damage", "Cons: Low Health"});
		
		enemyMage.updateAbilityDescription(new String[]{"Casts a fireball", "dealing " + enemyMage.getDamage() + " damage to", "the target location", "\n", "Pros: Med. Range & Damage", "Cons: Med. Health"});
		enemySorcerer.updateAbilityDescription(new String[]{"Summons an arcane bolt", "dealing " + enemySorcerer.getDamage() + " damage to", "the target location", "\n", "Pros: Med. Range & Damage", "Cons: Med. Health"});
		enemyKnight.updateAbilityDescription(new String[]{"Cleaves with the sword", "dealing " + enemyKnight.getDamage() + " damage to", "the target location", "\n", "Pros: High Health & AOE", "Cons: Low Damage"});
		enemyPaladin.updateAbilityDescription(new String[]{"Slams shield on ground", "dealing " + enemyPaladin.getDamage() + " damage to", "the target location", "\n", "Pros: High Health & AOE", "Cons: Low Damage"});
		enemyArcher.updateAbilityDescription(new String[]{"Fires an arrow", "dealing " + enemyArcher.getDamage() + " damage to", "the target location", "\n", "Pros: High Range", "Cons: Low Health"});
		enemyBowman.updateAbilityDescription(new String[]{"Fires a volley", "dealing " + enemyBowman.getDamage() + " damage to", "the target location", "\n", "Pros: High Range", "Cons: Low Health"});
		enemyLord.updateAbilityDescription(new String[]{"Calls a rain of glory", "dealing " + enemyLord.getDamage() + " damage to", "the target location", "\n", "Pros: Med. Damage & AOE", "Cons: Med. Health"});
		enemyKing.updateAbilityDescription(new String[]{"Strikes down his foes", "dealing " + enemyKing.getDamage() + " damage to", "the target location", "\n", "Pros: Med. Damage & AOE", "Cons: Med. Health"});
		enemyAssassin.updateAbilityDescription(new String[]{"Stabs with a knife", "dealing " + enemyAssassin.getDamage() + " damage to", "the target location", "\n", "Pros: High Damage", "Cons: Low Health"});
		enemyThief.updateAbilityDescription(new String[]{"Lashes out", "dealing " + enemyThief.getDamage() + " damage to", "the target location", "\n", "Pros: High Damage", "Cons: Low Health"});
		
		GamePiece[] options = {mage, sorcerer, necromancer, archer, bowman, sniper, knight, paladin, berserker, assassin, thief, shadow, lord, king, emperor, enemyMage, enemySorcerer, enemyKnight, enemyPaladin, enemyArcher, enemyBowman, enemyAssassin, enemyThief, enemyLord, enemyKing};
		characters = options;
		

		//imports the 10 pieces
		FileReader read1 = new FileReader("importPieces.txt");
		Scanner sc1 = new Scanner(read1);
		int index = 0;
		while (sc1.hasNextLine()) {
			String lineArg = sc1.nextLine();
			board[index] = 	options[Integer.parseInt(lineArg)];
			options[Integer.parseInt(lineArg)].select(characters[index],characters, this, board);
			index++;
		}
		sc1.close();
		
		//imports the xp of all the pieces
		FileReader read2 = new FileReader("save.txt");
		Scanner sc2 = new Scanner(read2);
		index = 0;
		while (sc2.hasNextLine()) {
			String lineArg = sc2.nextLine();
			options[index].setXP(Integer.parseInt(lineArg));
			index++;
		}
		sc2.close();
		
		int boardLocationBlue = 3;
		int boardLocationRed = 12;
		//sets the xy coords of all the pieces
		for(GamePiece p: board) {
			if(p.getTeamAsInt() == 0) {
				p.setY(0);
				p.setX(boardLocationBlue);
				boardLocationBlue++;
			}
			else {
				p.setY(11);
				p.setX(boardLocationRed);
				boardLocationRed--;
			}
		}
	}
	
	//sets the marked piece to be the one at the passed coordinates and returns it
	public GamePiece markPiece(int x, int y) {
		markedPiece = getPiece(x,y);
		return markedPiece;
	}
	
	//returns the piece at the coordinates
	public GamePiece getPiece(int x, int y) {
		for(GamePiece p: board) {
			if(p != null && p.isAlive() && p.getX() == ((x-25)/75) && p.getY() == ((y-25)/79)) {
				return p;
			}
		}
		return null;
	}
	
	//moves the markedPiece to the passed coordinates
	public void movePiece(int x, int y) {
		int xOld = markedPiece.getX();
		int yOld = markedPiece.getY();
		if(markedPiece != null && getPiece(x,y) == null) {
			markedPiece.setX(((x-25)/75));
			markedPiece.setY(((y-25)/79));
			updateMoves(xOld,yOld,markedPiece,0);
			markedPiece = null;
		}
	}
	
	//handles all the different attack types and calls attackCoords for each location targeted
	public void attackPiece(int x, int y) {
		//single spot abilities
		if(markedPiece.getAbilityType() == 0 || markedPiece.getAbilityType() == 1 || markedPiece.getAbilityType() == 2) {
			attackCoords(x,y);
		}
		//melee aoe all around central location of the markedPiece
		else if(markedPiece.getAbilityType() == 3) {
			for(int i = -1; i <= 1; ++i) {
				for(int j = -1; j <= 1; ++j) {
					if((markedPiece.getX()*75)+25+75*i >= 0 && (markedPiece.getX()*75)+25+75*i <= 1225 && (markedPiece.getY()*79)+25+79*j >= 0 && (markedPiece.getY()*79)+25+79*j <= 974) {
						x = (markedPiece.getX()*75)+25 +75*i;
						y = (markedPiece.getY()*79)+25+79*j;
						attackCoords(x, y);
					}
				}
			}
		}
		markedPiece = null;
		update();
	}
	
	//attack the piece at the coordinates from the markedPiece
	public void attackCoords(int x, int y) {
		if(markedPiece != null && getPiece(x,y) != null && markedPiece.getTeamAsInt() != getPiece(x,y).getTeamAsInt()) {
			int dmg = markedPiece.getDamage();
			updateMoves(x,y,markedPiece,2);
			if(markedPiece.getTeam().equals("Blue")) {
				blueDmg += dmg;
			}
			else {
				redDmg += dmg;
			}
			GamePiece curr = getPiece(x,y);
			curr.setHealth(curr.getHealth() - dmg);
		}
	}
	
	//returns 0 for blue and 1 for red
	public int getTurn() {
		return turn;
	}
	
	//returns true if the two passed pieces are different colors, false if they are the same color
	public boolean isValidAttack(GamePiece piece1, GamePiece piece2) {
		if(piece1 == null || piece2 == null) {
			return false;
		}
		if(!piece1.getTeam().equals(piece2.getTeam())) {
			//range or melee of 1 or aoe melee of 1
			if((piece1.getAbilityType() == 0 || piece1.getAbilityType() == 3) && Math.abs(piece1.getX() - piece2.getX()) <= 1 && Math.abs(piece1.getY() - piece2.getY()) <= 1) {
				return true;
			}
			//ranged of range 2
			else if(piece1.getAbilityType() == 1 && Math.abs(piece1.getX() - piece2.getX()) <= 2 && Math.abs(piece1.getY() - piece2.getY()) <= 2) {
				return true;
			}
			//ranged of range 3
			else if(piece1.getAbilityType() == 2 && Math.abs(piece1.getX() - piece2.getX()) <= 3 && Math.abs(piece1.getY() - piece2.getY()) <= 3) {
				return true;
			}
			return false;
		}
		return false;
	}

	public void select(int i, int j, GamePiece[] characters) {
		int index = 15*j + (int)Math.round((0.178479 + 0.759665*i));
		if(index < characters.length) {
			if(characters[index].isSelected()) {
				characters[index].deselect(characters[index], characters, this, board);
			}
			else {
				characters[index].select(characters[index], characters, this, board);	
			}
		}
	}

	//returns true if the x,y location is a valid move for the given piece considering the spot is empty already
	public boolean isValidMove(int x, int y, GamePiece piece) {
		x=(x-25)/75;
		y=(y-25)/79;
		if(piece != null) {
			//default moveType of 1 square in any direction
			if(piece.getMoveType() == 0 && Math.abs(piece.getX()-x) <= 1 && Math.abs(piece.getY()-y) <= 1) {
				return true;
			}
			//movetype of 2 squares in any direction// jumps over other pieces
			else if(piece.getMoveType() == 1 && Math.abs(piece.getX()-x) <= 2 && Math.abs(piece.getY()-y) <= 2) {
				return true;
			}
			return false;
		}
		return false;
	}
}