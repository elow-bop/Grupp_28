package se.su.inlupp;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import javax.xml.validation.Validator;
import java.lang.runtime.SwitchBootstraps;
import java.util.Optional;

public class Gui extends Application {
  ObservableList<String> cities;
  ListView<String> listCities = new ListView<>(cities);

  public void start(Stage stage) {

    //Läsa in vad användren skriver




    stage.setTitle("Route Planner");
    Graph<String> graph = new ListGraph<String>();

    BorderPane root = new BorderPane();

    //Skapa bild
    Image background = new Image(Gui.class.getResourceAsStream("/se.su.inlupp/bild.jpg"));
    ImageView backgroundView = new ImageView(background);
    ImageView backgroundViewHome = new ImageView(background);
    ImageView backgroundViewSearch = new ImageView(background);

    //Settings
      Pane settingsPane = new Pane();
      Label backgroundLabel = new Label("Change background");

      backgroundLabel.relocate( 200, 100);

      settingsPane.getChildren().addAll(backgroundViewHome, backgroundLabel);

      //Home-text
      Label homeLabel = new Label ("Home");

    //Search-button
      Button searchButton = new Button("Search");

    //Search-pane
      Pane searchPane = new Pane();
      Label start = new Label("Start");
      Label stop = new Label("Stop");

      TextField startField = new TextField();
      TextField stopField = new TextField();

      //Add-button
      Button addButton = new Button("Add");
      addButton.setOnAction(
              (arg) -> {
                  String textInput = startField.getText();

                  if(textInput == null || textInput.isEmpty()){
                      Alert error = new Alert(Alert.AlertType.ERROR);
                      error.setHeaderText("Error");
                      error.setTitle("Do not put in empty, stupid fuck");
                      Optional<ButtonType> solution = error.showAndWait();
                      if(solution.isPresent() && solution.get() == ButtonType.OK){
                          root.setCenter(searchPane);
                      }

                  }else{
                      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                      alert.setTitle("Confirm");
                      alert.setHeaderText("Add new city " + startField.getText());
                      alert.setContentText("Correct city?");
                      Optional<ButtonType> answer = alert.showAndWait();
                      if (answer.isPresent() && answer.get() == ButtonType.OK) {
                          root.setCenter(searchPane);
                      }
                  }
              });

      //Button för dropdown curtain välja stad
      Button buttonShowCities = new Button("Show list of cities");
      buttonShowCities.setOnAction(
              (arg) -> {
                  searchPane.getChildren().add(listCities);
              });

      //searchButton.setOnAction(new searchHandler());
      searchPane.getChildren().addAll(backgroundViewSearch, start, stop, startField, stopField, searchButton, addButton, buttonShowCities);

      start.relocate( 200, 100);
      stop.relocate(200, 200);
      startField.relocate(200, 120);
      stopField.relocate(200, 220);
      searchButton.relocate(430, 300);
      addButton.relocate(370, 220);
      buttonShowCities.relocate(70, 120);

      listCities.setPrefHeight(100);
      listCities.setPrefWidth(110);

      listCities.relocate(70, 145);

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
                  root.setCenter(backgroundView);
            });
    MenuItem exit = new MenuItem("Exit");
    exit.setOnAction(
            (arg) -> {
                stage.close();
            });
    menu.getItems().addAll(searchRoute, settings, home, exit);

    //Stoppa in i root
    root.setTop(vboxMenu);
    root.setCenter(backgroundView);

    Scene scene = new Scene(root, 640, 480);
    stage.setScene(scene);
    stage.show();

  }

  public static void main(String[] args) {
    launch(args);
  }
}
