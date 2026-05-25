package se.su.inlupp;

import javafx.application.Application;
import javafx.collections.FXCollections;
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
import java.lang.invoke.VarHandle;
import java.lang.runtime.SwitchBootstraps;
import java.util.Optional;

public class Gui extends Application {

  public void start(Stage stage) {
    stage.setTitle("Route Planner");
    ObservableList<String> cities = FXCollections.observableArrayList();
    ListView<String> listCities = new ListView<>(cities);

    // denna verkar inte funka!?!!? arrghhhh
    listCities.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    HBox connectionPane = new HBox();
    TextField connectionName = new TextField();
      TextField connectionDistance = new TextField();

      connectionPane.getChildren().addAll(connectionName, connectionDistance);

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
                  startField.clear();

                  if(textInput == null || textInput.isEmpty()){ //här vill vi sen också kolla om staden redan är tillagd eller inte
                      Alert error = new Alert(Alert.AlertType.ERROR);
                      error.setTitle("Error");
                      error.setHeaderText("Error: ");
                      Optional<ButtonType> solution = error.showAndWait();
                      if(solution.isPresent() && solution.get() == ButtonType.OK){
                          root.setCenter(searchPane);
                      }

                  }else{
                      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                      alert.setTitle("Confirm");
                      alert.setHeaderText("Add new city \"" + textInput + "\"");
                      alert.setContentText("Correct city?");
                      Optional<ButtonType> answer = alert.showAndWait();
                      if (answer.isPresent() && answer.get() == ButtonType.OK) {
                          graph.add(textInput);
                          cities.add(textInput);
                          root.setCenter(searchPane);
                      }
                  }
              });

      //removeButton
      Button removeButton = new Button("remove from list");
      removeButton.setOnAction(
              (arg) -> {
                  String selected = listCities.getSelectionModel().getSelectedItem();
                  cities.remove(selected);
                  graph.remove(selected);
              });

      //addConnection
      Button addConnectionButton = new Button("add connection");
      addConnectionButton.setOnAction(
              (arg) -> {
                  ObservableList<String> selected = listCities.getSelectionModel().getSelectedItems();

                  TextInputDialog addConnectionConfirmation = new TextInputDialog("Confirm");
                  addConnectionConfirmation.setTitle("Confirm");
                  addConnectionConfirmation.setHeaderText("Add connection between " + selected.get(0) + " and " + selected.get(1) + "while here, also enter name and distance");
                  addConnectionConfirmation.setContentText("Name");
                  addConnectionConfirmation.setContentText("Distance?");
                  addConnectionConfirmation.getDialogPane().setContent(connectionPane);

                  Optional<ButtonType> result = addConnectionConfirmation.showAndWait();

                  selected.clear();
                  if (result.isPresent() && result.get() == ButtonType.OK) {
                      //koppla ihop två noder graph.connect(selected,x )
                      int distance = Integer.parseInt(connectionDistance.getText();
                      graph.connect(selected.get(0), selected.get(1), connectionName.getText(), distance);
                      root.setCenter(searchPane);
                  }

              });

      //searchButton.setOnAction(new searchHandler());
      searchPane.getChildren().addAll(backgroundViewSearch, start, stop, startField, stopField, searchButton, addButton,
              removeButton, addConnectionButton, listCities);

      start.relocate( 200, 100);
      stop.relocate(200, 200);
      startField.relocate(200, 120);
      stopField.relocate(200, 220);
      searchButton.relocate(430, 300);
      addButton.relocate(370, 120);
      removeButton.relocate(70, 250);
      addConnectionButton.relocate(70, 280);

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

    Scene scene = new Scene(root, 735, 490);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
