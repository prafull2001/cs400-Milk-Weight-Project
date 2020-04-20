package application;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends Application {
    // store any command-line arguments that were entered.
    // NOTE: this.getParameters().getRaw() will get these also
    private List<String> args;

    private static final int WINDOW_WIDTH = 300;
    private static final int WINDOW_HEIGHT = 200;
    private static final String APP_TITLE = "CS400 My First JavaFX Program";

    @Override
    public void start(Stage primaryStage) throws Exception {
        args = this.getParameters().getRaw();
        BorderPane root = new BorderPane();
        
        String week_days[] = { "Apples", "Oranges", "Bananas", "Pears", "Peaches" }; 
        ComboBox combo_box = new ComboBox(FXCollections.observableArrayList(week_days)); 
        
        Image doggie = new Image("doggie.jpg");
        ImageView img = new ImageView(doggie);
        img.setFitHeight(300);
        img.setFitWidth(300);
        
        Button btn = new Button("Done");
        
        
        CheckBox cb1 = new CheckBox("Check if you are having fun!");
        
        root.setTop(new Label("CS400 My First JavaFX Program"));
        root.setLeft(combo_box);
        root.setCenter(img);
        root.setBottom(btn);
        root.setRight(cb1);
        
        
        
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