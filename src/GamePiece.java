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
	
	public GamePiece(String team, int x, int y, int health, int damage, int level, Ability ability, String imageURL) {
		this.team = team;
		this.health = health;
		this.maxHealth = health;
		this.level = level;
		this.damage = damage;
		this.x = x;
		this.y = y;
		this.ability = ability;
		this.imageURL = imageURL;
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
}
