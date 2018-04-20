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

public class Game {
	private int gameNumber;
	private Team team1;
	private Team team2;
	
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
	
	public VBox setUpGame() {
		HBox team1HBox = setUpTeamHBox(team1);		
		HBox team2HBox = setUpTeamHBox(team2);
		VBox vBox = new VBox();
		Button submit = new Button();
		submit.setText("Submit");
		submit.setFont(new Font("Algerian", 11));
		vBox.getChildren().addAll(team1HBox, submit, team2HBox);
		return vBox;
	}
	
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
