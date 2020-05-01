package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.util.List;
import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.EventHandler;

public class Main extends Application {
  // store any command-line arguments that were entered.
  // NOTE: this.getParameters().getRaw() will get these also
  private List<String> args;

  private static final int WINDOW_WIDTH = 800;
  private static final int WINDOW_HEIGHT = 400;
  private static final String APP_TITLE = "MILK WEIGHT PROGRAM";
  //private DataManager df;
  boolean fileLoaded = false;

  @Override
  public void start(Stage primaryStage) throws Exception {
    args = this.getParameters().getRaw();
    Label progLabel = new Label("MILK WEIGHT PROGRAM");
    progLabel.setFont(new Font("Arial", 30));
    BorderPane root = new BorderPane();
    
    
    //create info labels
    VBox bottomSection = new VBox();
    Label min = new Label("minimum: 0");
    Label avg = new Label("average: 0");
    Label max = new Label("maximum: 0");
    Label farmShare = new Label("");
    bottomSection.getChildren().addAll(min, avg, max, farmShare);
    bottomSection.setAlignment(Pos.TOP_CENTER);

    // LEFT PANE COMPONENTS
    VBox leftCol = new VBox();
    HBox leftRow1 = new HBox();
    Label queryLabel = new Label("QUERY TYPE: ");
    String query_types[] = {"Month", "Date Range"};
    ComboBox query_type = new ComboBox(FXCollections.observableArrayList(query_types));
    query_type.getSelectionModel().select(1);
    leftRow1.getChildren().addAll(queryLabel, query_type);
    HBox leftRow2 = new HBox();
    TextField queryDataFarmID = new TextField("Farm ID (Farm 32)");
    TextField queryDataTime = new TextField("Month (Format: 4)");
    query_type.setOnAction((e) -> {
    	String curr = query_type.getValue().toString();
    	if (curr.equals("Month"))
    		queryDataTime.setText("Month (4)");
    	if (curr.equals("Date Range"))
    		queryDataTime.setText("Date Range (2020-4-3,2020-12-10)");
    });
    queryDataFarmID.setOnKeyPressed(new EventHandler<KeyEvent>() {
        public void handle(KeyEvent ke1) {
            if (ke1.getCode().equals(KeyCode.ENTER)) {
            	if (fileLoaded) {
	            	String other = queryDataTime.getText();
	            	if (!(other.equals("Month (Format: 4)") || 
	            			other.equals("Month (4)") || 
	            			other.equals("Date Range (2020-4-3,2020-12-10)"))) {
	            		String queryString = query_type.getValue().toString();
	            		if (queryString.equals("Month")) {
		            		String thisText = queryDataFarmID.getText();
	            			min.setText(df.getMonthlyMinForFarm(other, thisText));
	            			avg.setText(df.getMonthlyAvgForFarm(other, thisText));
	            			max.setText(df.getMonthlyMaxForFarm(other, thisText));
	            			farmShare.setText(String.format("%.2f", (
        					Double.parseDouble(
        							df.getMonthlyAvgForFarm(thisText, other))
        					/ Double.parseDouble(
        							df.getMonthlyAverage())))
        					.toString() + "% of total");
//	            			System.out.println("min");
//	            			System.out.println("avg");
//	            			System.out.println("max");
	            		} else {
	            			String[] dateRanges = other.split(",");
	            			min.setText(df.getMinInDateRange(dateRanges[0], dateRanges[1]));
	            			avg.setText(df.getAverageInDateRange(dateRanges[0], dateRanges[1]));
	            			max.setText(df.getMaxInDateRange(dateRanges[0], dateRanges[1]));
	            			farmShare.setText("");
//	            			System.out.println("min");
//	            			System.out.println("avg");
//	            			System.out.println("max");
	            		}
	            	}
            	} else {
            		queryDataFarmID.setText("Please load file first");
            	}
            }
        }
    });
    queryDataTime.setOnKeyPressed(new EventHandler<KeyEvent>() {
        public void handle(KeyEvent ke2) {
            if (ke2.getCode().equals(KeyCode.ENTER)) {
            	if (fileLoaded) {
	            	String other = queryDataFarmID.getText();
	            	if (!(other.equals("Farm ID (Farm 32)"))) {
	            		String queryString = query_type.getValue().toString();
	            		if (queryString.equals("Month")) {
		            		String thisText = queryDataTime.getText();
	            			min.setText(df.getMonthlyMinForFarm(thisText, other));
	            			avg.setText(df.getMonthlyAvgForFarm(thisText, other));
	            			max.setText(df.getMonthlyMaxForFarm(thisText, other));
	            			farmShare.setText(String.format("%.2f", (
	            					Double.parseDouble(
	            							df.getMonthlyAvgForFarm(thisText, other))
	            					/ Double.parseDouble(
	            							df.getMonthlyAverage())))
	            					.toString() + "% of total");
//	            			System.out.println("min");
//	            			System.out.println("avg");
//	            			System.out.println("max");
	            		} else {
	            			String[] dateRanges = other.split(",");
	            			min.setText(df.getMinInDateRange(dateRanges[0], dateRanges[1]));
	            			avg.setText(df.getAverageInDateRange(dateRanges[0], dateRanges[1]));
	            			max.setText(df.getMaxInDateRange(dateRanges[0], dateRanges[1]));
	            			farmShare.setText("");
//	            			System.out.println("min");
//	            			System.out.println("avg");
//	            			System.out.println("max");
	            		}
	            	}
            	} else {
            		queryDataTime.setText("Please load file first");
            	}
            }
        }
    });
    queryDataFarmID.setStyle("-fx-padding: 4;");
    leftRow2.getChildren().addAll(queryDataFarmID, queryDataTime);
    leftCol.getChildren().addAll(leftRow1, leftRow2);
    leftCol.setStyle("-fx-padding: 10;" + "-fx-border-width: 5;");

    // MIDDLE PANE COMPONENTS
    VBox midCol = new VBox();
    VBox midLeftCol = new VBox();
    VBox midRightCol = new VBox();
    final ToggleGroup group = new ToggleGroup();
    RadioButton rb1 = new RadioButton("ADD");
    RadioButton rb2 = new RadioButton("REMOVE");
    rb1.setToggleGroup(group);
    rb1.setSelected(true);
    rb2.setToggleGroup(group);
    midLeftCol.setAlignment(Pos.BASELINE_RIGHT);
    midRightCol.setAlignment(Pos.BASELINE_RIGHT);
    midLeftCol.getChildren().add(rb1);
    midRightCol.getChildren().add(rb2);
    midLeftCol.setStyle("-fx-padding: 4;");
    midRightCol.setStyle("-fx-padding: 4;");
    midCol.getChildren().addAll(midLeftCol, midRightCol);
    midCol.setAlignment(Pos.TOP_CENTER);
    midCol.setStyle("-fx-padding: 10;" + "-fx-border-width: 5;");


    // RIGHT PANE COMPONENTS
    VBox rightCol = new VBox();
    HBox rightRow1 = new HBox();
    Label manageFile = new Label("MANAGE FILES");
    manageFile.setFont(new Font("Arial", 16));
    String data_types[] = {"File", "Entry"};
    ComboBox data_type = new ComboBox(FXCollections.observableArrayList(data_types));
    data_type.getSelectionModel().select(0);
    rightRow1.getChildren().addAll(data_type);
    HBox rightRow2 = new HBox();
    TextField addData = new TextField("File Path");
    addData.setOnKeyPressed(new EventHandler<KeyEvent>() {
        public void handle(KeyEvent ke3) {
            if (ke3.getCode().equals(KeyCode.ENTER)) {
            	if (data_type.getValue().toString().equals("File")) {
            		if (rb1.isSelected()) {
            			try {
	            			df = new DataManager(addData.getText());
	            			addData.setText("New file loaded!");
	            			fileLoaded = true;
            			} catch (Exception e) {
            				addData.setText("File not found");
            			}
            		} else {
            			addData.setText("File cannot be removed");
            		}
            	} else {
            		String[] addDataSplit = addData.getText().split(",");
            		if (addDataSplit.length == 3) {
                		if (rb1.isSelected()) {
                			try {
	                			df.insertData(addDataSplit);
//	                			System.out.println("ADD DATA");
                			} catch (Exception e) {
                				addData.setText("Incorrect format");
                			}
                		} else {
                			try {
                    			df.removeData(addDataSplit);
//                    			System.out.println("REMOVE DATA");
                			} catch (Exception e) {
                				addData.setText("Incorrect format");
                			}
                		}
            		}
            	}
            }
        }
    });
    data_type.setOnAction((e) -> {
    	String curr = data_type.getValue().toString();
    	if (curr.equals("File")) {
    		addData.setText("File Path");
    	} else {
    		addData.setText("Farm ID,Date,Pounds (Farm 32,2020-4-30,5)");
    	}
    });
    addData.setStyle("-fx-padding: 4;");
    rightRow2.getChildren().addAll(addData);
    rightCol.getChildren().addAll(rightRow1, rightRow2);
    rightCol.setStyle("-fx-padding: 10;" + "-fx-border-width: 5;");
    


    // create graph stand-in
    ImageView imageView = new ImageView(new Image(new FileInputStream("graph.png")));
    imageView.setPreserveRatio(true);
    imageView.setFitWidth(800);
    VBox graphImg = new VBox(imageView);
    graphImg.setAlignment(Pos.BASELINE_CENTER);


    root.setBottom(bottomSection);
    root.setTop(progLabel);
    root.setAlignment(progLabel, Pos.CENTER);
    root.setLeft(leftCol);
    root.setCenter(midCol);
    root.setRight(rightCol);



    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
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
