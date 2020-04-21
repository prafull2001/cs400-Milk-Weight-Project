package application;

import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends Application {
    // store any command-line arguments that were entered.
    // NOTE: this.getParameters().getRaw() will get these also
    private List<String> args;

    private static final int WINDOW_WIDTH = 300;
    private static final int WINDOW_HEIGHT = 200;
    private static final String APP_TITLE = "MILK WEIGHT PROGRAM";

    @Override
    public void start(Stage primaryStage) throws Exception {
        args = this.getParameters().getRaw();
        Label progLabel = new Label("MILK WEIGHT PROGRAM");
        progLabel.setFont(new Font("Arial", 30));
        BorderPane root = new BorderPane();
        
        //LEFT PANE COMPONENTS 
        /*
         * creates a VBox object (vertical box) to add multiple HBox components.
         * HBox lets you add elements in a row, and VBox lets you add multiple elements in
         * a column 
         */
        VBox leftCol = new VBox();
        HBox leftRow1 = new HBox();
        Label queryLabel = new Label("QUERY TYPE: ");
        String week_days[] = { "Farm", "Annual", "Monthly", "Date Range"}; 
        ComboBox query_type = new ComboBox(FXCollections.observableArrayList(week_days));
        leftRow1.getChildren().addAll(queryLabel, query_type);
        HBox leftRow2 = new HBox();
        Label inputLabel = new Label("INPUT DATA: ");
        TextField data = new TextField("input data here..."); 
        leftRow2.getChildren().addAll(inputLabel, data);
        leftCol.getChildren().addAll(leftRow1, leftRow2);
        
        //RIGHT PANE COMPONENTS
        VBox rightCol = new VBox();
        RadioButton rb1 = new RadioButton("ADD DATA");
        RadioButton rb2 = new RadioButton("REMOVE DATA");
        HBox rightRow1 = new HBox();
        Label dataTypeLabel = new Label("DATA TYPE: ");
        String data_types[] = { "File", "Entry"}; 
        ComboBox data_type = new ComboBox(FXCollections.observableArrayList(data_types));
        rightRow1.getChildren().addAll(dataTypeLabel, data_type);
        HBox rightRow2 = new HBox();
        rightRow2.getChildren().addAll(inputLabel, data);
        rightCol.getChildren().addAll(rb1, rb2, rightRow1, rightRow2);

        
        
        
        
        
        CheckBox cb1 = new CheckBox("Check if you are having fun!");
        
        root.setTop(progLabel);
        root.setAlignment(progLabel,Pos.CENTER);
        root.setLeft(leftCol);
        root.setRight(rightCol);
        
        
        
        Scene scene = new Scene (root, 400, 300);
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}