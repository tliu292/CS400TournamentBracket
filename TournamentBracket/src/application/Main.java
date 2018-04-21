package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

///////////////////////////////////////////////////////////////////////////////
//Assignment Name: TournamentBracket
//Author: Jiazhi Yang (jyang436@wisc.edu)
//        Kesong Cao (kcao22@wisc.edu)
//        Tz-Ruei Liu (tliu292@wisc.edu)
//Due Date: May 3, 2018
//Other Source Credits: None
//Known Bugs: None, to the best of my knowledge
///////////////////////////////////////////////////////////////////////////////

/**
 * Main class loads the file into teams arraylist,
 * precomputes the ranking of each team and place them
 * at the appropriate position within the tournament bracket
 * 
 * @extends Application
 *
 */
public class Main extends Application {
	static final int WIDTH = 1200;
	static final int HEIGHT = 700;

	static ArrayList<Team> teams = new ArrayList<Team>();
	static ArrayList<Game> games = new ArrayList<Game>();
	static Integer gameCount;
	static HBox currentPage;

	static Stage stage = new Stage();
	static VBox left;
	static HBox center;
	static BorderPane root;

	/**
	 * load the specified file within command line
	 * if nothing is typed, load the default file
	 * @param path
	 */
	private static void loadDefaultFile(String path) {
		if (path == null) {
			readFile("src" + File.separator + "teams.txt", null);
		} else {
			readFile(path, null);
		}
	}

	/**
	 * read the file and load the teams and their corresponding ranking
	 * into the teams arrayList
	 * throw an alert box if the number of teams is illegal
	 * 
	 * @throws IOException
	 * @param fileName
	 * @param file
	 */
	static void readFile(String fileName, File file) {
		try {
			if (!(fileName == null))
				file = new File(fileName);
			BufferedReader br;
			br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			int rank = 0;
			while (line != null) {
				teams.add(new Team(line.trim(), ++rank));
				line = br.readLine();
			}
			br.close();
			if (!(rank == 0 || rank == 1 || rank == 2 || rank == 4 || rank == 8 || rank == 16 || rank == 32)) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("The number of teams is illegal!");
				alert.setContentText("Please select a new file that contains legal number of teams.\n"
						+ "Accepted numbers are 0, 1, 2, 4, 8, 16, and 32.\n" + "The program will now TERMINATE.");
				alert.showAndWait();
				System.exit(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * precompute the ranking so that the first seed will
	 * compete with the last seed, and so on. Also the highest two seeds
	 * should meet at the final game
	 */
	static void preComputationRanking() {
		int[] left = new int[teams.size() / 2];
		int[] right = new int[teams.size() / 2];
		ArrayList<Team> ranking = new ArrayList<Team>();
		int indexLeft = 0;
		int indexRight = 0;
		for (int i = 0; i < teams.size(); i++) {
			if ((i % 4 == 0) || (i % 4 == 3)) {
				left[indexLeft] = i;
				indexLeft++;
			} else {
				right[indexRight] = i;
				indexRight++;
			}
		}
		int i = 0;
		int j = teams.size() / 4 - 1;
		while (i < j) {
			ranking.add(teams.get(left[i]));
			ranking.add(teams.get(left[teams.size() / 2 - i - 1]));
			ranking.add(teams.get(left[j]));
			ranking.add(teams.get(left[teams.size() / 2 - j - 1]));
			i++;
			j--;
		}
		i = 0;
		j = teams.size() / 4 - 1;
		while (i < j) {
			ranking.add(teams.get(right[i]));
			ranking.add(teams.get(right[teams.size() / 2 - i - 1]));
			ranking.add(teams.get(right[j]));
			ranking.add(teams.get(right[teams.size() / 2 - j - 1]));
			i++;
			j--;
		}
		teams = ranking;
	}
	
	/**
	 * add all games and their corresponding gameIndex into the games
	 * arraylist; for the first teams.size()/2 games, insert them with
	 * the corresponding teams; for the later games, insert them with corresponding
	 * gameIndex, but with not teams, since teams(winners) are not decided yet
	 */
	static void setGames() {
		int gameIndex = 1;
		for (int i = 0; i < teams.size() / 2; i++) {
			games.add(new Game(teams.get(i*2), teams.get(i*2 + 1), gameIndex));
			gameIndex++;
		}
		for (int i = 0; i < teams.size() / 2 - 1; i++) {
			games.add(new Game(gameIndex));
			gameIndex++;
		}
	}

	static void computeWinners() {
		ArrayList<Team> winners = new ArrayList<Team>();
		for (int i = 0; i < teams.size() / 2; i++) {
			int result = teams.get(2 * i).getGameScore() - teams.get(2 * i + 1).getGameScore();
			if (result < 0)
				winners.add(teams.get(2 * i + 1));
			else if (result > 0)
				winners.add(teams.get(2 * i));
			else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText("Two teams have the same score!");
				alert.setContentText("Choose your winning team of this game between\n" + "A: Team "
						+ teams.get(2 * i).getName() + "\n and\n" + "B: Team " + teams.get(2 * i + 1).getName());

				ButtonType buttonTypeOne = new ButtonType("A");
				ButtonType buttonTypeTwo = new ButtonType("B");

				alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

				Optional<ButtonType> choice = alert.showAndWait();
				if (choice.get() == buttonTypeOne) {
					winners.add(teams.get(2 * i));
				} else {
					winners.add(teams.get(2 * i + 1));
				}
			}
		}
		teams = winners;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			root = new BorderPane();
			root = GUI.setupGUI(root);
			Scene scene = new Scene(root, WIDTH, HEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("Tournament Bracket");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args != null && args.length > 0)
			loadDefaultFile(args[0]);
		else
			loadDefaultFile(null);
		launch(args);
	}
}
