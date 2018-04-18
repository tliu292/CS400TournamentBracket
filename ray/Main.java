package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Main extends Application {
	private String fileName;
	private ArrayList<String> teamNames;
	private ArrayList<HBox> displayHboxes;
	private Team[] teams;
	private Team[] winners;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		readFile("teams.txt");
		loadTeams();
		displayHboxes = new ArrayList<HBox>();
		
		primaryStage.setTitle("Tournament Bracket");
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,1080,720);
			setUpGUI(primaryStage);
			divideGroup();
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
	
	/*
    An implementation of loading files into local data structure 
    */
    public void readFile(String fileName) throws IOException {
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
    
    public void loadTeams() {
		int index = 0;;
		teams = new Team[teamNames.size()];
		for (String name: teamNames) {
			int rank = index + 1;
			teams[index] = new Team(name, rank);
			index++;
		}
	}
    
    public void setUpGUI(Stage primaryStage) {
    	for (int i = 0; i < teams.length * 2 - 1; i++) {
    		HBox hBox = new HBox(10.0);
    		Scene scene = new Scene(hBox);
    		
    		displayHboxes.add(hBox);
    		Label nameLabel = new Label();
    		nameLabel.setAlignment(Pos.CENTER);
    		nameLabel.setMinHeight(25);
    		
    		TextField gameScore = new TextField();
    		gameScore.setMaxHeight(20); gameScore.setMaxWidth(80);
    		gameScore.setPromptText("Input Score");
    		gameScore.setFocusTraversable(false);
    		
    		hBox.getChildren().addAll(nameLabel, gameScore);
    		primaryStage.setScene(scene);
    		primaryStage.show();
    	}
    }
    
    public void divideGroup() {
    	
    }
    
    public void compete(Team t1, Team t2) {
    	
    }
}
