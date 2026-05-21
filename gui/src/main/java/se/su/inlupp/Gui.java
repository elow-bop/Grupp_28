package se.su.inlupp;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Gui extends Application {

  public void start(Stage stage) {
    Graph<String> graph = new ListGraph<String>();
//    String javaVersion = System.getProperty("java.version");
//    String javafxVersion = System.getProperty("javafx.version");
//    Label label =
//        new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

//    VBox root = new VBox(30, label);
//    root.setAlignment(Pos.CENTER);
//    Scene scene = new Scene(root, 640, 480);
//    stage.setScene(scene);
//    stage.show();

    BorderPane root = new BorderPane();

    VBox vbox = new VBox();
    FlowPane flowPane = new FlowPane();

    Menu menu = new Menu("Menu");
    MenuBar menuBar = new MenuBar();
    vbox.getChildren().add(menuBar);
    menuBar.getMenus().add(menu);
    MenuItem search = new MenuItem("Search route");
    MenuItem newCity = new MenuItem("Add city");
    MenuItem removeCity = new MenuItem("Remove city");
    menu.getItems().addAll(search, newCity, removeCity);

//    Button saveButton = new Button("Save");

    Label start = new Label ("Start");
    start.setAlignment(Pos.BASELINE_RIGHT);
    flowPane.getChildren().add(start);
    vbox.getChildren().add(flowPane);
    
    root.getChildren().addAll(vbox, flowPane);




    Scene scene = new Scene(vbox, 640, 480);
    stage.setScene(scene);
    stage.show();

  }

  public static void main(String[] args) {
    launch(args);
  }
}
