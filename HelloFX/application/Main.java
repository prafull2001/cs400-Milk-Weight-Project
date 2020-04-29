package application;

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
  private static final int WINDOW_HEIGHT = 600;
  private static final String APP_TITLE = "MILK WEIGHT PROGRAM";

  @Override
  public void start(Stage primaryStage) throws Exception {
    args = this.getParameters().getRaw();
    Label progLabel = new Label("MILK WEIGHT PROGRAM");
    progLabel.setFont(new Font("Arial", 30));
    BorderPane root = new BorderPane();

    // LEFT PANE COMPONENTS
    VBox leftCol = new VBox();
    HBox leftRow1 = new HBox();
    Label queryLabel = new Label("QUERY TYPE: ");
    String query_types[] = {"Year", "Month", "Date Range"};
    ComboBox query_type = new ComboBox(FXCollections.observableArrayList(query_types));
    query_type.getSelectionModel().select(1);
    leftRow1.getChildren().addAll(queryLabel, query_type);
    HBox leftRow2 = new HBox();
    // Label inputLabel = new Label("INPUT DATA: ");
    TextField queryDataFarmID = new TextField("Farm ID");
    TextField queryDataTime = new TextField("Month");
    query_type.setOnAction((e) -> {
      queryDataTime.setText(query_type.getValue().toString());
    });
    // inputLabel.setStyle("-fx-padding: 4;");
    queryDataFarmID.setStyle("-fx-padding: 4;");
    leftRow2.getChildren().addAll(/* inputLabel, */queryDataFarmID, queryDataTime);
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
    // Label dataTypeLabel = new Label("DATA TYPE: ");
    Label manageFile = new Label("MANAGE FILES");
    manageFile.setFont(new Font("Arial", 16));
    String data_types[] = {"File", "Entry"};
    ComboBox data_type = new ComboBox(FXCollections.observableArrayList(data_types));
    data_type.getSelectionModel().select(0);
    rightRow1.getChildren().addAll(/* dataTypeLabel, */data_type);
    HBox rightRow2 = new HBox();
    // dataTypeLabel.setStyle("-fx-padding: 4;");
    // inputLabel.setStyle("-fx-padding: 4;");
    TextField addData = new TextField("File Path");
    data_type.setOnAction((e) -> {
      if (query_type.getValue().toString().contentEquals("File")) {
        addData.setText("File Path");
      } else {
        addData.setText("Farm ID, Date, Pounds");
      }
    });
    addData.setStyle("-fx-padding: 4;");
    rightRow2.getChildren().addAll(/* dataTypeLabel, */addData);
    rightCol.getChildren().addAll(rightRow1, rightRow2);
    rightCol.setStyle("-fx-padding: 10;" + "-fx-border-width: 5;");

    // create graph standin
    ImageView imageView = new ImageView(new Image(new FileInputStream("graph.png")));
    imageView.setPreserveRatio(true);
    imageView.setFitWidth(800);
    VBox graphImg = new VBox(imageView);
    graphImg.setAlignment(Pos.BASELINE_CENTER);


    root.setBottom(graphImg);
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
