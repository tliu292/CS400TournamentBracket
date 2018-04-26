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
        team1HBox.getStyleClass().add("team");
        HBox team2HBox = setUpTeamHBox(team2);
        team2HBox.getStyleClass().add("team");
        Button submit = new Button();
        submit.setText("Submit");
        submit.getStyleClass().add("button-submit");
        submit.setOnMouseEntered(e -> {submit.setStyle("-fx-text-fill: #f7f7f7;");});
        submit.setOnMouseExited(e -> {submit.setStyle("-fx-text-fill: #c5050c;");});
        Label blank1 = new Label();
        blank1.getStyleClass().add("vbox-blank1");
        Label blank2 = new Label();
        blank2.getStyleClass().add("vbox-blank1");
        if (getNumber() == Main.teams.size() - 1) {
            /*
             *this method is called if there's only one game within the tournament
     		 * pop out an alert box if the two teams have the same score
     	 	 * otherwise, pop out an information box with the top three winners
             */

            submit.setOnAction(e -> {
            try{
                Main.teams.get(team1).setScore(Integer
                            .parseInt(((TextField) team1HBox.getChildren().get(1)).getText()));
            Main.teams.get(team2).setScore(Integer
                            .parseInt(((TextField) team2HBox.getChildren().get(1)).getText()));
            } catch(Exception ex) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("The scores entered have illegal content!");
                alert.setContentText("Please enter scores as integers.\n");
                alert.showAndWait();
                ((TextField)((HBox)vBox.getChildren().get(0)).getChildren().get(1)).clear();
                ((TextField)((HBox)vBox.getChildren().get(4)).getChildren().get(1)).clear();
                return;
            }
            int score = Main.teams.get(team1).getScore() - Main.teams.get(team2).getScore();
            int winner = team1;
            int loser = team2;
            if (score < 0) {
                winner = team2;
                loser = team1;
            }
            // Case #1: tie
            if (score == 0) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Two teams have the same score!");
                alert.setContentText("Please let those two teams have a rematch and then retype their scores.");
                alert.showAndWait().filter(response -> response == ButtonType.OK);
                //clean those two textfields
                ((TextField)((HBox)vBox.getChildren().get(0)).getChildren().get(1)).clear();
                ((TextField)((HBox)vBox.getChildren().get(4)).getChildren().get(1)).clear();
            }
            // Case #2: one team wins
            //disable the submit button
            submit.setDisable(true);
            //change those two textfields into labels
            String temp1 = ((TextField)((HBox)vBox.getChildren().get(0)).getChildren().get(1)).getText();
            Label scoreLabel1 = new Label(temp1);
            scoreLabel1.getStyleClass().add("score-label");
            ((HBox)vBox.getChildren().get(0)).getChildren().remove(1);
            ((HBox)vBox.getChildren().get(0)).getChildren().add(1, scoreLabel1);
            
            String temp2 = ((TextField)((HBox)vBox.getChildren().get(4)).getChildren().get(1)).getText();
            Label scoreLabel2 = new Label(temp2);
            scoreLabel2.getStyleClass().add("score-label");
            ((HBox)vBox.getChildren().get(4)).getChildren().remove(1);
            ((HBox)vBox.getChildren().get(4)).getChildren().add(1, scoreLabel2);
            
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
        } else {
        /*
    	 * this is how a regular button functions
    	 * or when there's more than 1 team in the tournament
    	 * proceed to the next round if 1 team has higher score than the other
   	  	 * pop out a confirmation dialog when two teams have the same score 
   	  	 */
            submit.setOnAction(e -> {
            try {
            Main.teams.get(team1).setScore(Integer
                            .parseInt(((TextField) team1HBox.getChildren().get(1)).getText()));
            Main.teams.get(team2).setScore(Integer
                            .parseInt(((TextField) team2HBox.getChildren().get(1)).getText()));
        } catch(Exception ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("The scores entered have illegal content!");
            alert.setContentText("Please enter scores as integers.\n");
            alert.showAndWait();
            //clean two textfields
            ((TextField)((HBox)vBox.getChildren().get(0)).getChildren().get(1)).clear();
            ((TextField)((HBox)vBox.getChildren().get(4)).getChildren().get(1)).clear();
            return;
        }
            int score = Main.teams.get(team1).getScore() - Main.teams.get(team2).getScore();
            Game nextGame = Main.games.get((getNumber() - 1) / 2 + Main.teams.size() / 2);
            if (score > 0) {
                nextGame.update(getNumber() % 2 == 0 ? 2 : 1, team1);
                setTeam1Wins(true);
                submit.setDisable(true);//disable the submit button
                
                //set two textfields to labels
                String temp1 = ((TextField)((HBox)vBox.getChildren().get(0)).getChildren().get(1)).getText();
                Label scoreLabel1 = new Label(temp1);
                scoreLabel1.getStyleClass().add("score-label");
                ((HBox)vBox.getChildren().get(0)).getChildren().remove(1);
                ((HBox)vBox.getChildren().get(0)).getChildren().add(1, scoreLabel1);
                
                String temp2 = ((TextField)((HBox)vBox.getChildren().get(4)).getChildren().get(1)).getText();
                Label scoreLabel2 = new Label(temp2);
                scoreLabel2.getStyleClass().add("score-label");
                ((HBox)vBox.getChildren().get(4)).getChildren().remove(1);
                ((HBox)vBox.getChildren().get(4)).getChildren().add(1, scoreLabel2);
                
            } else if (score < 0) {
                nextGame.update(getNumber() % 2 == 0 ? 2 : 1, team2);
                setTeam1Wins(false);
                submit.setDisable(true);
                
                String temp1 = ((TextField)((HBox)vBox.getChildren().get(0)).getChildren().get(1)).getText();
                Label scoreLabel1 = new Label(temp1);
                scoreLabel1.getStyleClass().add("score-label");
                ((HBox)vBox.getChildren().get(0)).getChildren().remove(1);
                ((HBox)vBox.getChildren().get(0)).getChildren().add(1, scoreLabel1);
                
                String temp2 = ((TextField)((HBox)vBox.getChildren().get(4)).getChildren().get(1)).getText();
                Label scoreLabel2 = new Label(temp2);
                scoreLabel2.getStyleClass().add("score-label");
                ((HBox)vBox.getChildren().get(4)).getChildren().remove(1);
                ((HBox)vBox.getChildren().get(4)).getChildren().add(1, scoreLabel2);
                
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Two teams have the same score!");
                alert.setContentText("Please let those two teams have a rematch and then retype their scores.");
                alert.showAndWait().filter(response -> response == ButtonType.OK);
                //clear two textfields
                ((TextField)((HBox)vBox.getChildren().get(0)).getChildren().get(1)).clear();
                ((TextField)((HBox)vBox.getChildren().get(4)).getChildren().get(1)).clear();
            }
        });
        }
        vBox.getChildren().addAll(team1HBox, blank1, submit, blank2, team2HBox);
        this.vBox = vBox;
        vBox.getStyleClass().add("game");
        return this.vBox;
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
        ((Label) ((HBox) this.getVBox().getChildren().get(i == 1 ? 0 : 4)).getChildren().get(0))
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
        teamProperty.getStyleClass().add("label-names");
        if (team == -1) {
            teamProperty.setFont(new Font("Algerian", 11));
            teamProperty.setText("Winner TBD");
        } else {
            teamProperty.setFont(new Font("Algerian", 11));
            teamProperty.setText(
                            Main.teams.get(team).getRank() + " " + Main.teams.get(team).getName());
        }
        TextField score = new TextField();
        score.setMaxWidth(40);
        score.setFont(new Font("Algerian", 11));
        score.setPromptText("Score");
        
        hBox.getChildren().addAll(teamProperty, score);

        return hBox;
    }


}
    