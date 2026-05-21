package se.su.inlupp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Gui extends Application {

  public void start(Stage stage) {
    stage.setTitle("Route Planner");
    Graph<String> graph = new ListGraph<String>();

    BorderPane root = new BorderPane();

    Image background = new Image(Gui.class.getResourceAsStream("se.su.inlupp/bild.jpg"));
    ImageView backgroundView = new ImageView(background);

    root.setCenter(backgroundView);

    //Settings
      Pane settingsPane = new Pane();
      Label backgroundLabel = new Label("Change background");

      backgroundLabel.relocate( 200, 100);

      settingsPane.getChildren().add(backgroundLabel);


      //Home-text
      Label homeLabel = new Label ("Home");

    //Search-button
      Button searchButton = new Button("Search");
      searchButton.setOnAction(new searchHandler());

    //Lägga till-pane
      Pane searchPane = new Pane();
      Label start = new Label("Start");
      Label stop = new Label("Stop");

      TextField startField = new TextField();
      TextField stopField = new TextField();

      //Add-button
      Button addButton = new Button("Add");
      //searchButton.setOnAction(new searchHandler());

      start.relocate( 200, 100);
      stop.relocate(200, 200);
      startField.relocate(200, 120);
      stopField.relocate(200, 220);
      searchButton.relocate(430, 300);
      addButton.relocate(370, 220);


      searchPane.getChildren().addAll(start, stop, startField, stopField, searchButton, addButton);

      //Meny
    VBox vboxMenu = new VBox();
    Menu menu = new Menu("Menu");
    MenuBar menuBar = new MenuBar();
    vboxMenu.getChildren().add(menuBar);
    menuBar.getMenus().add(menu);
    MenuItem searchRoute = new MenuItem("Search route");
    searchRoute.setOnAction(
            (arg) -> {
                root.setCenter(searchPane);
            });
    MenuItem settings = new MenuItem("Settings");
    settings.setOnAction(
            (arg) -> {
                  root.setCenter(settingsPane);
            });
    MenuItem home = new MenuItem("Home");
    home.setOnAction(
            (arg) -> {
                  root.setCenter(homeLabel);
            });

    menu.getItems().addAll(searchRoute, settings, home);

    //Stoppa in i root
    root.setTop(vboxMenu);
    root.setCenter(homeLabel);

    Scene scene = new Scene(root, 640, 480);
    stage.setScene(scene);
    stage.show();

  }

  class searchRouteHandler implements EventHandler<ActionEvent>{
      @Override
      public void handle(ActionEvent event){

      }
  }

  class searchHandler implements EventHandler<ActionEvent>{
      @Override
      public void handle(ActionEvent event){

      }
    }



  public static void main(String[] args) {
    launch(args);
  }
}
