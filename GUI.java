package application;

import java.io.File;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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
// Known Bugs: None, to the best of our knowledge
///////////////////////////////////////////////////////////////////////////////

/**
 * GUI class sets up the scene and panels for user interface the entire GUI is a borderPane, with a
 * VBox on the left side and a huge HBox at the center. The huge HBox contains multiple VBox, and
 * each VBox contains multiple games according to its column position
 */
public class GUI {
    static final int WIDTH_OF_HBOX = 945;

    /**
     * the initial appearance of GUI when it's run, which is a huge BorderPane
     * 
     * the bottom contains a picture the left contains a VBox with a load button and a list of teams
     * the center contains a huge hBox, with a list of VBox each VBox contains number of games
     * according to the VBox index (column number)
     * 
     * @param root
     * @return BorderPane
     */
    public static BorderPane setupGUI(BorderPane root) {
        root.setLeft(setupLeft());
        root.getLeft().setId("vbox-left");
        Main.computeRanking();
        Main.setupGames();
        root.setCenter(setupCenter());
        root.getCenter().setId("root");
        return root;
    }

    /**
     * check number of teams within the file if there's no team, directly pop out an alert box if
     * there's only one team, pop out an alert box saying the specified team is the winner if
     * there's more than 1 team, call setupCenterRegular() method
     * 
     * @return HBox
     */
    private static HBox setupCenter() {
        HBox center = new HBox();
        if (Main.teams.size() > 1) {
            center = setupCenterRegular();
        } else {
            if (Main.teams.size() == 0) {
                Alert warning = new Alert(AlertType.WARNING);
                warning.setTitle("TEAM NUMBER WARNING");
                warning.setHeaderText("There is no team in this file. ");
                warning.setContentText(
                                "Please load another file that contains legal numbers of team \n");
                warning.showAndWait();
            } else if (Main.teams.size() == 1) {
                Alert info = new Alert(AlertType.INFORMATION);
                info.setTitle("WINNER WINNER! CHICKEN DINNER!");
                info.setHeaderText("Winner: " + Main.teams.get(0).getName());
                info.setContentText("There is only one team in this file. It wins automatically. ");
                info.showAndWait();
                Label teamLabel = new Label();
                teamLabel.setText(Main.teams.get(0).getRank() + "  " + Main.teams.get(0).getName());
                teamLabel.getStyleClass().add("team-only");
                center.getChildren().add(teamLabel);
                center.setStyle("-fx-alignment: center;");
            }
        }
        return center;
    }

    /**
     * set up the center of BorderPane with a list of VBox each VBox contains a specific number of
     * games according to its VBox index number within VBoxList (or its column number)
     * 
     * @return HBox
     */
    private static HBox setupCenterRegular() {
        HBox center = new HBox();
        ArrayList<VBox> vBoxList = new ArrayList<VBox>();
        int index = 0; // refers to the index within games arrayList

        // vBoxNum refers to the number of vBox within vBoxList (columns)
        // eg. 7 vBox/columns are required within a 16-team-tournament
        int vBoxNum = (int) (Math.log10(Main.teams.size()) / Math.log10(2)) * 2 - 1;

        // add a number of vBox according to vBoxNum into vBoxList first
        for (int i = 0; i < vBoxNum; i++) {
            VBox temp = new VBox();
            temp.setPrefWidth(WIDTH_OF_HBOX / (double) vBoxNum);
            temp.setId("vbox-big");
            vBoxList.add(temp);
        }

        // add a blank vbox
        VBox blank = new VBox();
        blank.getStyleClass().add("vbox-blank");

        // set up the final game (the middle game) first in it corresponding vBox
        // eg. game #15 is the final game in a 16-team-tournament
        // and this game is within VBox #3 (the middle VBox)
        VBox temp = Main.games.get(Main.games.size() - 1).setUpVBox();
        temp.setPrefWidth(WIDTH_OF_HBOX / (double) vBoxNum - 15);
        vBoxList.get(vBoxNum / 2).getChildren().addAll(temp, blank);

        // set up other vBox with games except the middle vBox
        for (int i = 0; i < vBoxNum / 2; i++) {
            // the inner for loop inserts games into each vBox
            // the number of games is related to its vBox index (or the column number)

            // this loop inserts from the left side of vBoxList
            for (int j = 0; j < (int) Math.pow(2, vBoxNum / 2 - 1 - i); j++) {
                // add a blank vbox
                VBox blank1 = new VBox();
                blank1.getStyleClass().add("vbox-blank");
                VBox temp1 = Main.games.get(index).setUpVBox();
                temp1.setPrefWidth(WIDTH_OF_HBOX / (double) vBoxNum - 15);
                vBoxList.get(i).getChildren().addAll(temp1, blank1);
                index++;
            }
            // this loop inserts from the right side of vBoxList
            for (int j = 0; j < (int) Math.pow(2, vBoxNum / 2 - 1 - i); j++) {
                // add a blank vbox
                VBox blank2 = new VBox();
                blank2.getStyleClass().add("vbox-blank");
                VBox temp2 = Main.games.get(index).setUpVBox();
                temp2.setPrefWidth(WIDTH_OF_HBOX / (double) vBoxNum - 15);
                vBoxList.get(vBoxList.size() - i - 1).getChildren().addAll(temp2, blank2);
                index++;
            }

        }

        for (int i = 0; i < vBoxList.size(); i++)
            center.getChildren().add(vBoxList.get(i));

        return center;
    }

    /**
     * set up the left side of BorderPane which contains a load button and a list of teams
     * 
     * @return VBox
     */
    private static VBox setupLeft() {
        VBox left = new VBox();
        Main.teamList = setupTeamList();
        left.getChildren().addAll(setupLoadButton(), Main.teamList);
        return left;
    }

    /**
     * return a VBox with list of teams
     * 
     * @param teamList
     * @return VBOx
     */
    private static VBox setupTeamList() {
        VBox teamList = new VBox();
        /* labelHead is a title with text "Tournament" */
        Label labelHead = new Label();
        labelHead.setText("Tournament");
        labelHead.setFont(new Font("Algerian", 15));
        labelHead.setId("label-head");
        teamList.getChildren().add(labelHead);

        /* following is a vBox with a list of teams */
        for (Team team : Main.teams) {
            Label label = new Label();
            label.setText(team.getRank() + "  " + team.getName());
            label.setFont(new Font("Algerian", 13));
            label.getStyleClass().add("label-names");
            teamList.getChildren().add(label);
        }
        teamList.setId("vbox-names");
        return teamList;
    }

    /**
     * return the load button loads the new file with the new team list into GUI also, clear the
     * previous team list within GUI
     * 
     * @return Button
     */
    private static Button setupLoadButton() {
        Button loadButton = new Button();
        loadButton.setText("Load");
        loadButton.setFont(new Font("Algerian", 13));
        loadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
                            new ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showOpenDialog(Main.stage);
            if (selectedFile != null) {
                Main.teams.clear();
                Main.games.clear();
                Main.readFile(null, selectedFile);
                Main.root = GUI.setupGUI(Main.root);
            }
        });
        loadButton.setId("load-button");
        loadButton.setOnMouseEntered(e -> {
            loadButton.setStyle("-fx-text-fill: #f7f7f7;");
        });
        loadButton.setOnMouseExited(e -> {
            loadButton.setStyle("-fx-text-fill: #c5050c;");
        });
        return loadButton;
    }
}
