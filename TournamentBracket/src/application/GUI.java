package application;

import java.io.File;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

///////////////////////////////////////////////////////////////////////////////
//Assignment Name: TournamentBracket
//Author: Jiazhi Yang (jyang436@wisc.edu)
//        Kesong Cao (kcao22@wisc.edu)
//	      Tz-Ruei Liu (tliu292@wisc.edu)
//		  Jerry Kou (jkou3@wisc.edu)
//Due Date: May 3, 2018
//Other Source Credits: None
//Known Bugs: None, to the best of my knowledge
///////////////////////////////////////////////////////////////////////////////

/**
 * GUI class sets up the scene and panels for Graphic User Interface
 * the entire GUI is a borderPane, with a VBox on the left side and a
 * huge HBox at the center.
 * The huge HBox contains multiple VBox, and each VBox contains multiple
 * games according to its column position
 */
public class GUI {

	/**
	 * the initial appearance of GUI when it's run, 
	 * which is a huge BorderPane
	 * 
	 * @param root
	 * @return BorderPane
	 */
	public static BorderPane setupGUI(BorderPane root) {
		Main.gameCount = 0;
		
		Image image = new Image("march-madness-pic.png",350,350,true,true);
		ImageView imageView = new ImageView(image);
		BorderPane bp = new BorderPane();
		root.setBottom(bp);
		bp.setRight(new ImageView(image));
			
		Main.left = new VBox();
		Main.center = new HBox();

		// Left side of BorderPane is a VBox
		// which stores the entire team list with rankings
		preComputationLeft();
		root.setLeft(Main.left);

		Main.preComputationRanking();
		Main.setGames();
		
		// Center of BorderPane is a big HBox
		// and this large HBox contains number of VBox
		// and each VBox contains multiple games according to its column number
		preComputationCenter();
		root.setCenter(Main.center);

		return root;
	}

	/**
	 * precomputes the left side of BorderPane
	 * a vBox contaning all teams and their corresponding ranking
	 * it's called in setUpGUI() method
	 */
	private static void preComputationLeft() {
		// a load file button is at the top of the left BorderPane
		Button loadFile = new Button();
		loadFile = setupLoadFile(loadFile);
		Main.left.getChildren().add(loadFile);
		
		// under the load button follows a teamList
		// which is contained within a VBox
		VBox teamList = new VBox();
		teamList = setupTeamList(teamList);
		Main.left.getChildren().add(teamList);
	}

	/**
	 * return a VBox with list of teams
	 * 
	 * @param teamList
	 * @return VBOx
	 */
	private static VBox setupTeamList(VBox teamList) {
		// labelHead is a title with text "League Rank"
		Label labelHead = new Label();
		labelHead.setText("League Rank");
		labelHead.setFont(new Font("Algerian", 15));
		teamList.getChildren().add(labelHead);
		
		// following is a vBox with a list of teams
		for (Team team : Main.teams) {
			Label label = new Label();
			label.setText(team.getRank() + "  " + team.getName());
			label.setFont(new Font("Algerian", 13));
			teamList.getChildren().add(label);
		}
		return teamList;
	}

	/**
	 * set up the loading file button
	 * loads the specified teams within the file to the GUI
	 * it's located on the left side of BorderPane
	 * 
	 * @param loadFile
	 * @return Button
	 */
	private static Button setupLoadFile(Button loadFile) {
		loadFile.setText("Load");
		loadFile.setFont(new Font("Algerian", 13));
		loadFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Resource File");
				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
						new ExtensionFilter("All Files", "*.*"));
				File selectedFile = fileChooser.showOpenDialog(Main.stage);
				if (selectedFile != null) {
					Main.teams.clear();
					Main.readFile(null, selectedFile);
					Main.root = GUI.setupGUI(Main.root);
				}
			}
		});
		return loadFile;
	}

	/**
	 * precomputes the center of BorderPane, which is a huge HBox
	 * when there's only one team left, the window pops out an AlertBox
	 * specifying the top 3 winners of the tournament
	 * it's called within setUpGUI() method
	 */
	private static void preComputationCenter() {
		Main.currentPage = new HBox();
		// set up a regular HBox at the center if there is
		// more than 1 team left
		if (Main.teams.size() > 1) {
			Main.currentPage = setupPageRegular();
		} else {

		}
		Main.center.getChildren().add(Main.currentPage);
	}

	/**
	 * set up mutliple vBox within the center HBox
	 * each vBox contains many games according to its column
	 * index number within vBoxList
	 * 
	 * @return HBox
	 */
	private static HBox setupPageRegular() {
		ArrayList<VBox> vBoxList = new ArrayList<VBox>();
		int index = 0; // refers to the index within game arrayList
		
		// vBoxNum refers to the number of vBox within vBoxList
		// eg. 7 vBox required within a 16-team-tournament
		int vBoxNum = (int)(Math.log10(Main.teams.size())/Math.log10(2)) * 2 - 1;
		
		// add a number of vBox according to vBoxNum into vBoxList first
		for (int i = 0; i < vBoxNum; i++) {
			vBoxList.add(new VBox());
		}
		
		// set up the final game (the middle game) first in it corresponding vBox
		// eg. game #15 is the final in a 16-team-tournament
		// and this game is within vBox #3
		vBoxList.get(vBoxNum/2).getChildren().add(Main.games.get(
									Main.games.size() - 1).setUpGame());
		
		// set up other vBox with games except the middle vBox
		// eg. vBox #0 and #6 corresponds to i = 0
		// vBox #1 and #5 corresponds to i = 1
		// vBox #2 and #4 corresponds to i = 2
		for (int i = 0; i < vBoxNum / 2; i++) {
			// the inner for loop inserts games into each vBox
			// the number of games is related to its vBox index (or the column number)
			
			// this loop inserts from the left side of vBoxList
			for (int j = 0; j < (int) Math.pow(2, vBoxNum / 2 - 1 - i); j++) {
				vBoxList.get(i).getChildren().add(Main.games.get(index).setUpGame());
				index++;
			}
			// this loop inserts from the right side of vBoxList
			for (int j = 0; j < (int) Math.pow(2, vBoxNum / 2 - 1 - i); j++) {
				vBoxList.get(vBoxList.size() - i - 1).getChildren().
									add(Main.games.get(index).setUpGame());
				index++;
			}
			
		}
		
		HBox page = new HBox();
		for (int i = 0; i < vBoxList.size(); i++)
			page.getChildren().add(vBoxList.get(i));
		return page;
	}

//	private static HBox setupOneTeam(HBox oneTeam, Team team) {
//		Label teamProperty = new Label();
//		teamProperty.setFont(new Font("Algerian", 12));
//		teamProperty.setText(team.getRank() + "  " + team.getName());
//
//		TextField score = new TextField();
//		score.setMaxWidth(50);
//		score.setFont(new Font("Algerian", 12));
//		score.setPromptText("Score");
//
//		oneTeam.getChildren().add(teamProperty);
//		oneTeam.getChildren().add(score);
//		return oneTeam;
//	}

//	private static void progressRegular() {
//		if (saveCurrentScores() == -1)
//			return;
//		Main.computeWinners();
//		generateNewPage();
//	}

//	private static void generateNewPage() {
//		HBox root = (HBox) Main.center.getChildren().get(0);
//		for (int i = 0; i < Main.gameCount; i++)
//			root = (HBox) root.getChildren().get(1);
//		Main.gameCount++;
//		Main.currentPage = new HBox();
//		Main.currentPage = setupPageRegular(Main.currentPage);
//		root.getChildren().set(1, Main.currentPage);
//	}

	private static int saveCurrentScores() {
		VBox leftSide = (VBox) Main.currentPage.getChildren().get(0);
		for (int i = 0; i < leftSide.getChildren().size(); i++) {
			try {
				int score = Integer.parseInt(
						((TextField) (((HBox) (leftSide).getChildren().get(i)).getChildren().get(1))).getText());
				Main.teams.get(i).setGameScore(score);
				((TextField) (((HBox) (leftSide).getChildren().get(i)).getChildren().get(1))).setEditable(false);
			} catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("The scores entered have illegal content!");
				alert.setContentText("Please enter scores as integers.\n");
				alert.showAndWait();
				return -1;
			}
		}

		VBox rightSide = (VBox) Main.currentPage.getChildren().get(2);
		for (int i = 0; i < rightSide.getChildren().size(); i++) {
			try {
				int score = Integer.parseInt(
						((TextField) (((HBox) (rightSide).getChildren().get(i)).getChildren().get(1))).getText());
				Main.teams.get(rightSide.getChildren().size() + i).setGameScore(score);
			} catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("The scores entered have illegal content!");
				alert.setContentText("Please enter scores as integers.\n");
				alert.showAndWait();
				return -1;
			}
		}

		for (int i = 0; i < leftSide.getChildren().size(); i++)
			((TextField) (((HBox) (leftSide).getChildren().get(i)).getChildren().get(1))).setEditable(false);
		for (int i = 0; i < rightSide.getChildren().size(); i++)
			((TextField) (((HBox) (rightSide).getChildren().get(i)).getChildren().get(1))).setEditable(false);

		return 0;
	}

	private static void winningTeam() {

	}
}
