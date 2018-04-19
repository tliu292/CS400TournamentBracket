package application;

public class Team {
	private String name;
	private Integer rank;
	private Integer gameScore;
	
	public Team(String name, Integer rank) {
		this.name = name;
		this.rank = rank;
	}
	
	public void setGameScore (Integer gameScore) {
		this.gameScore = gameScore;
	}
	
	public String getName() { return name; }
	public Integer getRank() { return rank;}
}
