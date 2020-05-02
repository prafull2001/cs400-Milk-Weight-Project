package application;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
  // store any command-line arguments that were entered.
  // NOTE: this.getParameters().getRaw() will get these also
  private List<String> args;

  //window settings
  private static final int WINDOW_WIDTH = 700;
  private static final int WINDOW_HEIGHT = 250;
  private static final String APP_TITLE = "MILK WEIGHT PROGRAM";
  
  //class variables used for calculations
  boolean fileLoaded = false;
  private DataManager df = new DataManager();

  /**
   * GUI handling method
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
	//set initial settings
    args = this.getParameters().getRaw();
    Label progLabel = new Label("MILK WEIGHT PROGRAM");
    progLabel.setFont(new Font("Arial", 30));
    BorderPane root = new BorderPane();

    // create data info labels
    VBox bottomSection = new VBox();
    Label pre = new Label("");
    Label min = new Label("");
    Label avg = new Label("");
    Label max = new Label("");
    Label farmShare = new Label("");
    bottomSection.getChildren().addAll(pre, min, avg, max, farmShare);
    bottomSection.setAlignment(Pos.TOP_CENTER);

    //Query components
    VBox leftCol = new VBox();
    HBox leftRow1 = new HBox();
    Label queryLabel = new Label("QUERY TYPE: ");
    String query_types[] = {"Month", "Date Range"};
    ComboBox query_type = new ComboBox(FXCollections.observableArrayList(query_types));
    query_type.getSelectionModel().select(0); //default combobox selection
    leftRow1.getChildren().addAll(queryLabel, query_type);
    HBox leftRow2 = new HBox();
    TextField queryDataFarmID = new TextField("Farm ID (Farm 32)");
    TextField queryDataTime = new TextField("Month (Format: 4)");
    
    //Change left textfield values with change in ComboBox selection
    query_type.setOnAction((e) -> {
      String curr = query_type.getValue().toString();
      if (curr.equals("Month"))
        queryDataTime.setText("Month (4)");
      if (curr.equals("Date Range"))
        queryDataTime.setText("Date Range (2020-4-3,2020-12-10)");
      queryDataFarmID.setText("Farm ID (Farm 32)");
    });
    
    //Query data when enter is pressed in farmID textfield
    queryDataFarmID.setOnKeyPressed(new EventHandler<KeyEvent>() {
      public void handle(KeyEvent ke1) {
        if (ke1.getCode().equals(KeyCode.ENTER)) {
          if (fileLoaded) { //check if data has been entered
        	//get the data from 2 relevant textboxes
            String other = queryDataTime.getText(); 
            String thisText = queryDataTime.getText();
            //check that other textbox is not default
            if (!(other.equals("Month (Format: 4)") || other.equals("Month (4)")
                || other.equals("Date Range (2020-4-3,2020-12-10)"))) {
              String queryString = query_type.getValue().toString(); //get query type
              if (queryString.equals("Month")) { //Month query
                try { //try to get analysis
                  pre.setText("This farm contributed, in this month:");
                  min.setText("minimum " + df.getMonthlyMinForFarm(other, thisText) + " units");
                  avg.setText("average " + df.getMonthlyAverageForFarm(other, thisText) + " units");
                  max.setText("maximum " + df.getMonthlyMaxForFarm(other, thisText) + " units");
                  farmShare
                      .setText(String
                          .format("%.2f",
                              ((Double.parseDouble(df.getMonthlyAverageForFarm(other, thisText))
                                  * 100)
                                  / (Double.parseDouble(df.getMonthlyAverage(other)) * df.size())))
                          .toString() + "% of total units");
                  //if analysis is default values, there is an invalid input
                  if (min.getText().equals("minimum 2147483647 units")
                      && max.getText().equals("maximum 0 units"))
                    throw new IllegalArgumentException("invalid input");
                } catch (IllegalArgumentException | NullPointerException e) {
                  //if there is invalid input, hide all analysis and ask for valid input
                  pre.setText("");
                  min.setText("");
                  avg.setText("");
                  max.setText("");
                  queryDataFarmID.setText("Please enter valid input");
                  farmShare.setText("");
                }
              } else { //Date Range query
                try { //try to do analysis
                  String[] dateRanges = other.split(","); //get both dates
                  min.setText(
                      "minimum " + df.getMinInDateRange(dateRanges[0], dateRanges[1]) + " units by all farms in this range");
                  avg.setText("average " + df.getAverageInDateRange(dateRanges[0], dateRanges[1])
                      + " units by all farms in this range");
                  max.setText(
                      "maximum " + df.getMaxInDateRange(dateRanges[0], dateRanges[1]) + " units by all farms in this range");
                  farmShare.setText(String
                          .format("%.2f",
                        		  ((df.getFarmAverageinRange(queryDataFarmID.getText(), dateRanges[0], dateRanges[1]) * 100))
                        				  / (Double.parseDouble(df.getAverageInDateRange(dateRanges[0], dateRanges[1])) * df.size()))
                        		  .toString() + "% of total units for the farm selected");
                  //if analysis is default values, there is an invalid input
                  if (min.getText().equals("minimum 2147483647 units by all farms in this range")
                          && max.getText().equals("maximum 0 units by all farms in this range"))
                    throw new IllegalArgumentException("invalid input");
                } catch (Exception e) {
                  //if there is invalid input, hide all analysis and ask for valid input
                  pre.setText("");
                  min.setText("");
                  avg.setText("");
                  max.setText("");
                  queryDataTime.setText("Please enter valid input.");
                  farmShare.setText("");
                }
              }
            }
          } else {
            queryDataFarmID.setText("Please load file first"); //if no data, ask for data
          }
        }
      }
    });
    
    //Query data when enter is pressed in time textfield
    queryDataTime.setOnKeyPressed(new EventHandler<KeyEvent>() {
      public void handle(KeyEvent ke2) {
        if (ke2.getCode().equals(KeyCode.ENTER)) {
          if (fileLoaded) { //check if data has been entered
          	//get the data from 2 relevant textboxes
            String other = queryDataFarmID.getText();
            String thisText = queryDataTime.getText();
            //check that other textbox is not default
            if (!(other.equals("Farm ID (Farm 32)"))) {
              String queryString = query_type.getValue().toString(); //get query type
              if (queryString.equals("Month")) { //Month query
                try { //try to get analysis
                  pre.setText("This farm contributed, in this month:");
                  min.setText("minimum " + df.getMonthlyMinForFarm(thisText, other) + " units");
                  avg.setText("average " + df.getMonthlyAverageForFarm(thisText, other) + " units");
                  max.setText("maximum " + df.getMonthlyMaxForFarm(thisText, other) + " units");
                  farmShare.setText(String
                      .format("%.2f",
                          ((Double.parseDouble(df.getMonthlyAverageForFarm(thisText, other)) * 100)
                              / (Double.parseDouble(df.getMonthlyAverage(thisText)) * df.size())))
                      .toString() + "% of total units");
                  //if analysis is default values, there is an invalid input
                  if (min.getText().equals("minimum 2147483647 units")
                      && max.getText().equals("maximum 0 units"))
                    throw new IllegalArgumentException("invalid input");
                } catch (IllegalArgumentException | NullPointerException e) {
                  //if there is invalid input, hide all analysis and ask for valid input
                  pre.setText("");
                  min.setText("");
                  avg.setText("");
                  max.setText("");
                  queryDataTime.setText("Please enter valid input.");
                  farmShare.setText("");
                }
              } else { //Date Range query
                try { //try to do analysis
                  String[] dateRanges = thisText.split(","); //get both dates
                  min.setText(
                      "minimum " + df.getMinInDateRange(dateRanges[0], dateRanges[1]) + " units by all farms in this range");
                  avg.setText("average " + df.getAverageInDateRange(dateRanges[0], dateRanges[1])
                      + " units by all farms in this range");
                  max.setText(
                      "maximum " + df.getMaxInDateRange(dateRanges[0], dateRanges[1]) + " units by all farms in this range");
                  farmShare.setText(String
                          .format("%.2f",
                        		  ((df.getFarmAverageinRange(queryDataFarmID.getText(), dateRanges[0], dateRanges[1]) * 100))
                        				  / (Double.parseDouble(df.getAverageInDateRange(dateRanges[0], dateRanges[1])) * df.size()))
                        		  .toString() + "% of total units for the farm selected");
                  //if analysis is default values, there is an invalid input                  
                  if (min.getText().equals("minimum 2147483647 units by all farms in this range")
                      && max.getText().equals("maximum 0 units by all farms in this range"))
                    throw new IllegalArgumentException("invalid input");
                } catch (Exception e) {
                  //if there is invalid input, hide all analysis and ask for valid input
                  pre.setText("");
                  min.setText("");
                  avg.setText("");
                  max.setText("");
                  queryDataTime.setText("Please enter valid input.1");
                  farmShare.setText("");
                }
              }
            }
          } else {
            queryDataTime.setText("Please load file first"); //if no data, ask for data
          }
        }
      }
    });
    
    //left side component settings
    queryDataFarmID.setStyle("-fx-padding: 4;");
    leftRow2.getChildren().addAll(queryDataFarmID, queryDataTime);
    leftCol.getChildren().addAll(leftRow1, leftRow2);
    leftCol.setStyle("-fx-padding: 10;" + "-fx-border-width: 5;");

    // Radio button settings
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


    // Right side ComboBox and textfield settings
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
    
    //Add/remove data when enter is pressed in textfield
    addData.setOnKeyPressed(new EventHandler<KeyEvent>() {
      public void handle(KeyEvent ke3) {
        if (ke3.getCode().equals(KeyCode.ENTER)) {
          if (data_type.getValue().toString().equals("File")) { //Insert/Remove File
            if (rb1.isSelected()) { //Add File
              try {
            	//load file
                df.readInsertData(addData.getText());
                addData.setText("New file loaded!");
                fileLoaded = true;
                //set left side to default values to remind of format
                String curr = query_type.getValue().toString();
                if (curr.equals("Month"))
                  queryDataTime.setText("Month (Format: 4)");
                if (curr.equals("Date Range"))
                  queryDataTime.setText("Date Range (2020-4-3,2020-12-10)");
                queryDataFarmID.setText("Farm ID (Farm 32)");
              } catch (Exception e) {
                addData.setText("File not found"); //invalid file path
              }
            } else { //Remove file
              try {
            	//remove file
                df.readRemoveData(addData.getText());
                addData.setText("File removed!");
                //set left side to default values to remind of format
                String curr = query_type.getValue().toString();
                if (curr.equals("Month"))
                  queryDataTime.setText("Month (Format: 4)");
                if (curr.equals("Date Range"))
                  queryDataTime.setText("Date Range (2020-4-3,2020-12-10)");
                queryDataFarmID.setText("Farm ID (Farm 32)");
                //remove analysis as it may no longer be true 
                pre.setText("");
                min.setText("");
                avg.setText("");
                max.setText("");
                farmShare.setText("");
              } catch (Exception e) {
                addData.setText("File not found"); //invalid file path
              }
            }
          } else { //Insert/Remove one line of data
            String[] addDataSplit = addData.getText().split(","); //data should be formatted like csv
            if (addDataSplit.length == 3) { //data should have 3 fields
              if (rb1.isSelected()) { //Add line of data
                try {
                  //insert data
                  df.insertData(addDataSplit);
                  addData.setText("Added entry!");
                } catch (Exception e) {
                  //data failed error checks
                  addData.setText("Incorrect format");
                }
              } else { //remove line of data
                try {
                  //remove data
                  df.removeData(addDataSplit);
                  addData.setText("Removed entry!");
                } catch (Exception e) {
                  //data failed error checks or may not be there
                  addData.setText("Incorrect format");
                }
              }
            }
          }
        }
      }
    });
    
    //Change right textfield values with change in ComboBox selection
    data_type.setOnAction((e) -> {
      String curr = data_type.getValue().toString();
      if (curr.equals("File")) {
        addData.setText("File Path");
      } else {
        addData.setText("Farm ID,Date,Pounds (2020-4-30,Farm 32,5)");
      }
    });
    
    //right side component settings
    addData.setStyle("-fx-padding: 4;");
    rightRow2.getChildren().addAll(addData);
    rightCol.getChildren().addAll(rightRow1, rightRow2);
    rightCol.setStyle("-fx-padding: 10;" + "-fx-border-width: 5;");

    //add Hboxes/Vboxes to root
    root.setBottom(bottomSection);
    root.setTop(progLabel);
    root.setLeft(leftCol);
    root.setCenter(midCol);
    root.setRight(rightCol);
    
    //add all compnents to GUI
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
