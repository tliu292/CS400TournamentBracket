package application;

///////////////////////////////////////////////////////////////////////////////
//Assignment Name: TournamentBracket
//Author: Kesong Cao (kcao22@wisc.edu)
//        Tz-Ruei Liu (tliu292@wisc.edu)
//Due Date: May 3, 2018
//Other Source Credits: None
//Known Bugs: None, to the best of my knowledge
///////////////////////////////////////////////////////////////////////////////

public class Team {
	private String name;
	private Integer rank;
	private Integer gameScore;

	public Team(String name, Integer rank) {
		this.name = name;
		this.rank = rank;
	}

	public void setGameScore(Integer gameScore) {
		this.gameScore = gameScore;
	}

	public Integer getGameScore() {
		return gameScore;
	}

	public String getName() {
		return name;
	}

	public Integer getRank() {
		return rank;
	}
}
