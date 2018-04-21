package application;

///////////////////////////////////////////////////////////////////////////////
//Assignment Name: TournamentBracket
//Author: D-Team 99 / A-Team 100
//Jiazhi Yang ( jyang436@wisc.edu )
//Junqi Kou ( jkou3@wisc.edu )
//Kesong Cao ( kcao22@wisc.edu )
//Chentao Wang ( cwang556@wisc.edu )
//Tz-Ruei Liu ( tliu292@wisc.edu )
//Due Date: May 3, 2018
//Other Source Credits: None
//Known Bugs: None, to the best of my knowledge
///////////////////////////////////////////////////////////////////////////////

/**
 * Team class contains information about the team's name,
 * its specified ranking, and also the gameScore after
 * a particular game
 * 
 */
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
