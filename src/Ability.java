
public class Ability {
	
	private String[] description;
	private String name;
	private int level;
	private String type;

	public Ability(String name, String[] description, int level, String type) {
		this.name = name;
		this.description = description;
		this.level = level;
		this.type = type;
	}
	
	public String getName(){
		return name;
	}
	
	public String[] getDescription(){
		return description;
	}
	
	public void updateDescription(String[] description) {
		this.description = description;
	}
	
}
