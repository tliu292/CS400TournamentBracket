package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	private String fileName;
	private ArrayList<String> teamNames;
	private Team[] teams;
	private Team[] winners;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		loadData("teams.txt");
		loadTeams();
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void loadTeams() {
		int index = 0;;
		teams = new Team[teamNames.size()];
		for (String name: teamNames) {
			int rank = index + 1;
			teams[index] = new Team(name, rank);
			index++;
		}
	}
	
	/*
    An implementation of loading files into local data structure 
    */
    public void loadData(String fileName) throws IOException {
        // Opens the given test file and stores the objects each line as a string
    	this.fileName = fileName;
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        teamNames = new ArrayList<>();
        String line = br.readLine();
        while (line != null) {
            teamNames.add(line.trim());
            line = br.readLine();
        }
        br.close();
    }
}
