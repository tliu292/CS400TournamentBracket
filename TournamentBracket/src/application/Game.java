package application;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

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
 * Game class stores information of each particular game
 * each game contains a gameNumber, differentiating one game from another
 * and also two teams compete when after clicking the submit button
 */
public class Game {
    private int gameNumber;
    private int team1;
    private int team2;
    private VBox vBox;
    private boolean isTeam1Wins;

    public Game(int number, int team1, int team2, VBox vBox) {
        this.gameNumber = number;
        this.team1 = team1;
        this.team2 = team2;
        this.vBox = vBox;
    }

    public boolean isTeam1Wins() {
        return isTeam1Wins;
    }

    public void setTeam1Wins(boolean isTeam1Wins) {
        this.isTeam1Wins = isTeam1Wins;
    }

    public int getNumber() {
        return gameNumber;
    }

    public void setNumber(int number) {
        this.gameNumber = number;
    }

    public int getTeam1() {
        return team1;
    }

    public void setTeam1(int team1) {
        this.team1 = team1;
    }

    public int getTeam2() {
        return team2;
    }

    public void setTeam2(int team2) {
        this.team2 = team2;
    }

    public VBox getVBox() {
        return vBox;
    }

    public void setVBox(VBox vBox) {
        this.vBox = vBox;
    }

    /**
     * sets up the VBox with two team HBox and one submit button
     * the submit button functionality changes if there's only one game
     * left in the tournament
     * 
     * @return VBox
     */
    public VBox setUpVBox() {
        VBox vBox = new VBox();
        HBox team1HBox = setUpTeamHBox(team1);
        HBox team2HBox = setUpTeamHBox(team2);
        Button submit = new Button();
        submit.setText("Submit");
        submit.setFont(new Font("Algerian", 11));
        if (getNumber() == Main.teams.size() - 1) {
            submit.setOnAction(setupFinalButton(team1HBox, team2HBox));
        } else {
            submit.setOnAction(setupRegularButton(team1HBox, team2HBox));
        }
        vBox.getChildren().addAll(team1HBox, submit, team2HBox);
        this.vBox = vBox;
        return this.vBox;
    }

    /**
     * this method is called if there's only one game within the tournament
     * pop out an alert box if the two teams have the same score
     * otherwise, pop out an information box with the top three winners
     * 
     * @param team1HBox
     * @param team2HBox
     * @return EventHandler<ActionEvent>
     */
    private EventHandler<ActionEvent> setupFinalButton(HBox team1HBox, HBox team2HBox) {
        return (e -> {
            Main.teams.get(team1).setScore(Integer
                            .parseInt(((TextField) team1HBox.getChildren().get(1)).getText()));
            Main.teams.get(team2).setScore(Integer
                            .parseInt(((TextField) team2HBox.getChildren().get(1)).getText()));
            int score = Main.teams.get(team1).getScore() - Main.teams.get(team2).getScore();
            int winner = team1;
            int loser = team2;
            if (score < 0) {
                winner = team2;
                loser = team1;
            }
            // Case #1: tie
            if (score == 0) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Two teams have the same score!");
                alert.setContentText("Choose your winning team of this game between\n" + "A: Team "
                                + Main.teams.get(team1).getName() + "\n and\n" + "B: Team "
                                + Main.teams.get(team2).getName());

                ButtonType buttonTypeOne = new ButtonType("A");
                ButtonType buttonTypeTwo = new ButtonType("B");

                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

                Optional<ButtonType> choice = alert.showAndWait();
                if (choice.get() == buttonTypeOne) {
                } else {
                    winner = team2;
                    loser = team1;
                }
            }
            // Case #2: one team wins
            Alert info = new Alert(AlertType.INFORMATION);
            info.setTitle("WINNER WINNER! CHICKEN DINNER!");
            info.setHeaderText("Winner: " + Main.teams.get(winner).getName());
            if (getNumber() == 1) {
                info.setContentText("The following are the top two teams:\n" + "1st -> "
                                + Main.teams.get(winner).getName() + "\n2nd -> "
                                + Main.teams.get(loser).getName());
            } else {
            int team3 = Main.games.get(getNumber() - 2).isTeam1Wins()
                            ? Main.games.get(getNumber() - 2).getTeam2()
                            : Main.games.get(getNumber() - 2).getTeam1();
            int team4 = Main.games.get(getNumber() - 3).isTeam1Wins()
                            ? Main.games.get(getNumber() - 3).getTeam2()
                            : Main.games.get(getNumber() - 3).getTeam1();
            
            int score2 = Main.teams.get(team3).getScore() - Main.teams.get(team4).getScore();
            int third = team3;
            if (score2 < 0)
                third = team4;
            if (score2 == 0)
                third = -1;
            info.setContentText("The following are the top three teams:\n" + "1st -> "
                            + Main.teams.get(winner).getName() + "\n2nd -> "
                            + Main.teams.get(loser).getName() + "\n3rd -> "
                            + (third == -1 ? Main.teams.get(team3).getName() + " and "
                                            + Main.teams.get(team4).getName()
                                            : Main.teams.get(third).getName()));
            }
            info.showAndWait();
        });
    }

    /**
     * this is how a regular button functions
     * or when there's more than 1 team in the tournament
     * proceed to the next round if 1 team has higher score than the other
     * pop out a confirmation dialog when two teams have the same score 
     * 
     * @param team1HBox
     * @param team2HBox
     * @return EventHandler<ActionEvent> 
     */
    private EventHandler<ActionEvent> setupRegularButton(HBox team1HBox, HBox team2HBox) {
        return (e -> {
            Main.teams.get(team1).setScore(Integer
                            .parseInt(((TextField) team1HBox.getChildren().get(1)).getText()));
            Main.teams.get(team2).setScore(Integer
                            .parseInt(((TextField) team2HBox.getChildren().get(1)).getText()));
            int score = Main.teams.get(team1).getScore() - Main.teams.get(team2).getScore();
            Game nextGame = Main.games.get((getNumber() - 1) / 2 + Main.teams.size() / 2);
            if (score > 0) {
                nextGame.update(getNumber() % 2 == 0 ? 2 : 1, team1);
                setTeam1Wins(true);
            } else if (score < 0) {
                nextGame.update(getNumber() % 2 == 0 ? 2 : 1, team2);
                setTeam1Wins(false);
            } else {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Two teams have the same score!");
                alert.setContentText("Choose your winning team of this game between\n" + "A: Team "
                                + Main.teams.get(team1).getName() + "\n and\n" + "B: Team "
                                + Main.teams.get(team2).getName());

                ButtonType buttonTypeOne = new ButtonType("A");
                ButtonType buttonTypeTwo = new ButtonType("B");

                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

                Optional<ButtonType> choice = alert.showAndWait();
                if (choice.get() == buttonTypeOne) {
                    nextGame.update(getNumber() % 2 == 0 ? 2 : 1, team1);
                    setTeam1Wins(true);
                } else {
                    nextGame.update(getNumber() % 2 == 0 ? 2 : 1, team2);
                    setTeam1Wins(false);
                }
            }
        });
    }

    /**
     * update the team to the next round by refreshing the label
     * with the new team's ranking and its name
     * 
     * @param i
     * @param team
     */
    private void update(int i, int team) {
        if (i == 1)
            team1 = team;
        else
            team2 = team;
        ((Label) ((HBox) this.getVBox().getChildren().get(i == 1 ? 0 : 2)).getChildren().get(0))
                        .setText(Main.teams.get(team).getRank() + " " +
                        		 Main.teams.get(team).getName());
    }

    /**
     * set up one team's HBox with a label and a textfield
     * 
     * @param team
     * @return HBox
     */
    private HBox setUpTeamHBox(int team) {
        HBox hBox = new HBox();
        Label teamProperty = new Label();
        if (team == -1) {
            teamProperty.setFont(new Font("Algerian", 13));
            teamProperty.setText("Winner TBD");
        } else {
            teamProperty.setFont(new Font("Algerian", 13));
            teamProperty.setText(
                            Main.teams.get(team).getRank() + " " + Main.teams.get(team).getName());
        }
        TextField score = new TextField();
        score.setMaxWidth(50);
        score.setFont(new Font("Algerian", 12));
        score.setPromptText("Score");

        hBox.getChildren().addAll(teamProperty, score);

        return hBox;
    }


}
