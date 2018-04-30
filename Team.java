package application;

///////////////////////////////////////////////////////////////////////////////
// Assignment Name: TournamentBracket
// Author: D-Team 99 / A-Team 100
// Jiazhi Yang ( jyang436@wisc.edu )
// Junqi Kou ( jkou3@wisc.edu )
// Kesong Cao ( kcao22@wisc.edu )
// Chentao Wang ( cwang556@wisc.edu )
// Tz-Ruei Liu ( tliu292@wisc.edu )
// Due Date: May 3, 2018
// Other Source Credits: None
// Known Bugs: None, to the best of my knowledge
///////////////////////////////////////////////////////////////////////////////


/**
 * Team class contains information about the team's name, its specified ranking, and also the
 * gameScore after a particular game
 * 
 */
public class Team {
    private String name;
    private int rank;
    private int score;

    /**
     * Constructor of a Team object
     * 
     * @param name is the name of the team
     * @param rank is the rank of the team
     * @param score is the score of the team
     */
    public Team(String name, int rank, int score) {
        this.name = name;
        this.rank = rank;
        this.score = score;
    }

    /**
     * Get the name of the team
     * 
     * @return the name of the team
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the team
     * 
     * @param name is the team's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the rank of the team
     * 
     * @return the rank of the team
     */
    public int getRank() {
        return rank;
    }

    /**
     * Set the rank of the team
     * 
     * @param rank is the rank of the team
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * Get the score of the team
     * 
     * @return the score of the team
     */
    public int getScore() {
        return score;
    }

    /**
     * Set the score of the team
     * 
     * @param score is the score of the team
     */
    public void setScore(int score) {
        this.score = score;
    }

}
