package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main class loads the file into teams arraylist, precomputes the ranking of each team and place
 * them at the appropriate position within the tournament bracket
 * 
 * @extends Application is the library set up in javaFX by default
 *
 */
public class Main extends Application {

    static final int WIDTH = 1080;
    static final int HEIGHT = 720;

    static ArrayList<Team> teams = new ArrayList<Team>();
    static ArrayList<Game> games = new ArrayList<Game>();
    static VBox teamList;

    static Stage stage;
    static BorderPane root;

    /**
     * Start to setup the java FX program
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            root = new BorderPane();
            root = GUI.setupGUI(root);
            Scene scene = new Scene(root, WIDTH, HEIGHT);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Bowlby+One+SC");
            scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Black+Han+Sans");
            scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Ubuntu");
            stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Tournament Bracket (by CS400 A-Team 100 / D-Team 99)");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * main function to load files and launch the javaFX program
     */
    public static void main(String[] args) {
        String path = "teams.txt";
        if (args != null && args.length > 0)
            path = args[0];
        readFile(path, null);
        launch(args);// launch javaFX
    }

    /**
     * read the file and load the teams and their corresponding ranking into the teams arrayList
     * throw an alert box if the number of teams is illegal
     * 
     * @throws IOException reports if the filename is illegal
     * @param fileName is the name of the file
     * @param file is the file with fileName
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
                teams.add(new Team(line.trim(), ++rank, -1));
                line = br.readLine();
            }
            br.close();
            if (!(rank == 0 || rank == 1 || rank == 2 || rank == 4 || rank == 8 || rank == 16
                            || rank == 32)) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("The number of teams is illegal!");
                alert.setContentText(
                                "Please select a new file that contains legal number of teams.\n"
                                                + "Accepted numbers are 0, 1, 2, 4, 8, 16, and 32.\n"
                                                + "The program will now TERMINATE.");
                alert.showAndWait();
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * precompute the ranking so that the first seed will compete with the last seed, and so on.
     * Also the highest two seeds should meet at the final game
     */
    static void computeRanking() {
        int[] left = new int[teams.size() / 2];
        int[] right = new int[teams.size() / 2];
        ArrayList<Team> ranking = new ArrayList<Team>();
        int indexLeft = 0;
        int indexRight = 0;
        if (teams.size() == 1)
            return;
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
        if (i > j) {
            for (int k = 0; k < teams.size(); k++)
                ranking.add(teams.get(k));
        }
        while (i <= j) {
            ranking.add(teams.get(left[i]));
            ranking.add(teams.get(left[teams.size() / 2 - i - 1]));
            if (teams.size() > 4) {
                ranking.add(teams.get(left[j]));
                ranking.add(teams.get(left[teams.size() / 2 - j - 1]));
            }
            i++;
            j--;
        }
        i = 0;
        j = teams.size() / 4 - 1;
        while (i <= j) {
            ranking.add(teams.get(right[i]));
            ranking.add(teams.get(right[teams.size() / 2 - i - 1]));
            if (teams.size() > 4) {
                ranking.add(teams.get(right[j]));
                ranking.add(teams.get(right[teams.size() / 2 - j - 1]));
            }
            i++;
            j--;
        }
        teams = ranking;
    }

    /**
     * add all games and their corresponding gameIndex into the games arraylist; for the first
     * teams.size()/2 games, insert them with the corresponding teams; for the later games, insert
     * them with corresponding gameIndex, but with not teams, since teams(winners) are not decided
     * yet
     */
    static void setupGames() {
        int gameIndex = 1;
        for (int i = 0; i < teams.size() / 2; i++)
            games.add(new Game(gameIndex++, i * 2, i * 2 + 1, null));
        for (int i = 0; i < teams.size() / 2 - 1; i++)
            games.add(new Game(gameIndex++, -1, -1, null));
    }
}
