///////////////////////////////////////////////////////////////////////////////
//Assignment Name: TournamentBracket
//Author: Jiazhi Yang (jyang436@wisc.edu)
//Tz-Ruei Liu (tliu292@wisc.edu)
//Jerry Kou (jkou3@wisc.edu)
//Due Date: May 3, 2018
//Other Source Credits: None
//Known Bugs: None, to the best of my knowledge
///////////////////////////////////////////////////////////////////////////////

package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
/**
 * Game class stores information of each particular game
 * each game contains a gameNumber, differentiating one game from anther
 * and also team1 and team2 for competition
 */
public class Game {
    private int gameNumber;
    private Team team1;
    private Team team2;
    private VBox gameBoard;
    
    public Game(int gameNumber) {
        this.gameNumber = gameNumber;
        this.team1 = null;
        this.team2 = null;
    }
    
    public Game(Team team1, Team team2, int gameNumber) {
        this.gameNumber = gameNumber;
        this.team1 = team1;
        this.team2 = team2;
    }
    
    /**
	 * set up each game, with a team1HBox, a submit button, 
	 * and a team2HBox
	 * 
	 * @return VBox
	 */
    public VBox setUpGame() {
        HBox team1HBox = setUpTeamHBox(team1);    
        team1HBox.getStyleClass().add("team");
        HBox team2HBox = setUpTeamHBox(team2);
        team2HBox.getStyleClass().add("team");
        VBox vBox = new VBox();
        Button submit = new Button();
        submit.getStyleClass().add("button-submit");
        submit.setText("Submit");
        submit.setFont(new Font("Algerian", 11));
        submit.setOnAction(new EventHandler<ActionEvent>()
            {
                public void handle(ActionEvent arg0) {
                    team1.setGameScore(Integer.parseInt(((TextField)team1HBox.getChildren().get(1)).getText()));
                    team2.setGameScore(Integer.parseInt(((TextField)team2HBox.getChildren().get(1)).getText()));
                    int score = team1.getGameScore() - team2.getGameScore();
                    if (score > 0) {
                        Main.games.get((gameNumber-1)/2+Main.teams.size()/2).gameBoard = 
                        	Main.games.get((gameNumber-1)/2+Main.teams.size()/2).
                        	addTeam1(team1);
                    }
                }
                
            }
            );
        vBox.getChildren().addAll(team1HBox, submit, team2HBox);
        this.gameBoard = vBox;
        return gameBoard;
    }
    
    /**
	 * set up an individual teamHBox
	 * with a label specifying the team's ranking and name
	 * and also a text field to type in the game's score
	 * 
	 * @param team
	 * @return HBox
	 */
    private HBox setUpTeamHBox(Team team) {
        Label teamProperty = new Label();
        
        if (team == null) {
            teamProperty.setFont(new Font("Algerian", 13));
            teamProperty.setText("Winner TBD");
        } else {
            teamProperty.setFont(new Font("Algerian", 13));
            teamProperty.setText(team.getRank() + "  " + team.getName());
        }
        
        TextField score = new TextField();
        score.setMaxWidth(50);
        score.setFont(new Font("Algerian", 12));
        score.setPromptText("Score");
        
        HBox hBox = new HBox();
        hBox.getChildren().addAll(teamProperty, score);
        
        return hBox;
    }
    
    public VBox addTeam1(Team team1) {
        this.team1 = team1;
        return setUpGame();
    }
    
    public VBox addTeam2(Team team2) {
        this.team2 = team2;
        return setUpGame();
    }
    
    public int getGameNumber() {
        return gameNumber;
    }
}
