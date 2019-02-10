import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	public boolean gameBegun = false;
	public boolean gameOver = false;
	
	public GamePiece[] board;
	
	GamePiece markedPiece = null;
	private int redMax = 0;
	private int blueMax = 0;
	private int redDmg = 0;
	private int blueDmg = 0;
	private String winner;
	
	public Game() {
		
		GamePiece mage = new GamePiece("Blue", 2, 1, 130, 20, 1, new Ability("Fireball", null , 1, null), "mage.PNG");
		GamePiece knight = new GamePiece("Blue", 3, 0, 230, 10, 1, new Ability("Slash", null, 1, null), "knight.PNG");
		GamePiece archer = new GamePiece("Blue", 4, 0, 140, 20, 1, new Ability("Arrow", null, 1, null), "archer.PNG");
		GamePiece lord = new GamePiece("Blue", 5, 0, 170, 15, 1, new Ability("Glory", null, 1, null), "lord.PNG");
		GamePiece assassin = new GamePiece("Blue", 4, 1, 100, 30, 1, new Ability("Knife", null, 1, null), "assassin.PNG");
		GamePiece enemyMage = new GamePiece("Red", 2, 11, 130, 20, 1, new Ability("Fireball", null, 1, null), "enemyMage.PNG");
		GamePiece enemyKnight = new GamePiece("Red", 3, 10, 230, 10, 1, new Ability("Slash", null, 1, null), "enemyKnight.PNG");
		GamePiece enemyArcher = new GamePiece("Red", 4, 10, 140, 20, 1, new Ability("Arrow", null, 1, null), "enemyArcher.PNG");
		GamePiece enemyLord = new GamePiece("Red", 5, 10, 170, 15, 1, new Ability("Glory", null, 1, null), "enemyLord.PNG");
		GamePiece enemyAssassin = new GamePiece("Red", 4, 11, 100, 30, 1, new Ability("Knife", null, 1, null), "enemyAssassin.PNG");
		
		mage.updateAbilityDescription(new String[]{"Casts a fireball", "dealing " + mage.getDamage() + " damage to", "the target location"});
		knight.updateAbilityDescription(new String[]{"Casts a fireball", "dealing " + knight.getDamage() + " damage to", "the target location"});
		archer.updateAbilityDescription(new String[]{"Casts a fireball", "dealing " + archer.getDamage() + " damage to", "the target location"});
		lord.updateAbilityDescription(new String[]{"Casts a fireball", "dealing " + lord.getDamage() + " damage to", "the target location"});
		assassin.updateAbilityDescription(new String[]{"Casts a fireball", "dealing " + assassin.getDamage() + " damage to", "the target location"});
		enemyMage.updateAbilityDescription(new String[]{"Casts a fireball", "dealing " + mage.getDamage() + " damage to", "the target location"});
		enemyKnight.updateAbilityDescription(new String[]{"Casts a fireball", "dealing " + knight.getDamage() + " damage to", "the target location"});
		enemyArcher.updateAbilityDescription(new String[]{"Casts a fireball", "dealing " + archer.getDamage() + " damage to", "the target location"});
		enemyLord.updateAbilityDescription(new String[]{"Casts a fireball", "dealing " + lord.getDamage() + " damage to", "the target location"});
		enemyAssassin.updateAbilityDescription(new String[]{"Casts a fireball", "dealing " + assassin.getDamage() + " damage to", "the target location"});
		
		board = new GamePiece[10];
		board[0] = mage;
		board[1] = knight;
		board[2] = archer;
		board[3] = lord;
		board[4] = assassin;
		board[5] = enemyMage;
		board[6] = enemyKnight;
		board[7] = enemyArcher;
		board[8] = enemyLord;
		board[9] = enemyAssassin;
	}
	
	public void update() {
		for(int i = 0; i < 10; i++) {
			if(board[i] != null && board[i].getHealth() <= 0) {
				board[i] = null;
			}
		}
		if(blueCurTotalHealth() <= 0) {
			endGame();
			winner = "Red";
		}
		else if(redCurTotalHealth() <= 0) {
			endGame();
			winner = "Blue";
		}
	}
	
	public String getWinner() {
		return winner;
	}

	public void beginGame() {
		gameBegun = true;
		
	}

	public boolean isBegun() {
		return gameBegun;
	}

	public void endGame() {
		gameOver = true;
	}

	public boolean isOver() {
		return gameOver;
	}
	
	public int blueDamageDealt() {
		return blueDmg;
	}
	
	public int redDamageDealt() {
		return redDmg;
	}
	
	public int blueCurTotalHealth() {
		int sum = 0;
		for(int i = 0; i < 5; i++) {
			if(board[i] != null && board[i].isAlive()) {
				sum += board[i].getHealth();
			}
		}
		return sum;
	}
	
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
	
	public int redCurTotalHealth() {
		int sum = 0;
		for(int i = 5; i < 10; i++) {
			if(board[i] != null && board[i].isAlive()) {
				sum += board[i].getHealth();
			}
		}
		return sum;
	}
	
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
	
	public void writeGame() throws FileNotFoundException {
		PrintWriter write = new PrintWriter("save.txt");
		write.write(1);
		write.close();
	}
	
	public void loadGame() throws FileNotFoundException {
		FileReader read = new FileReader("save.txt");
		ArrayList<String> array = new ArrayList<String>();
		Scanner sc = new Scanner(read);
		while (sc.hasNextLine()) {
			array.add(sc.nextLine());
		}
		for(int i = 0; i < 10; i++) {
	
		}
	}

	public GamePiece markPiece(int x, int y) {
		markedPiece = getPiece(x,y);
		return markedPiece;
	}

	public GamePiece getPiece(int x, int y) {
		for(GamePiece p: board) {
			if(p != null && p.getX() == ((x-25)/75) && p.getY() == ((y-25)/79)) {
				return p;
			}
		}
		return null;
	}
	
	public void movePiece(int x, int y) {
		if(markedPiece != null && getPiece(x,y) == null) {
			markedPiece.setX(((x-25)/75));
			markedPiece.setY(((y-25)/79));
			markedPiece = null;
		}
	}

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

	public boolean isOpposite(GamePiece piece1, GamePiece piece2) {
		return(!piece1.getTeam().equals(piece2.getTeam()));
	}
}