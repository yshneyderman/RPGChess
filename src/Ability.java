
public class Ability {
	
	private String[] description;
	private String name;
	private int level;
	private int type;
	
	//Ability constructor
	public Ability(String name, String[] description, int level, int type) {
		this.name = name;
		this.description = description;
		this.level = level;
		this.type = type;
	}
	
	//returns String containing the name
	public String getName(){
		return name;
	}
	
	//return int representing the type of ability
	public int getType() {
		return type;
	}
	
	//returns String containing description
	public String[] getDescription(){
		return description;
	}
	
	//sets the description to the passed String array
	public void updateDescription(String[] description) {
		this.description = description;
	}
	
}
