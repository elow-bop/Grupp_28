package se.su.inlupp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.File;
import java.util.Optional;

public class Gui extends Application {

  public void start(Stage stage) {
    stage.setTitle("Route Planner");

    //List-vyn
    ObservableList<String> cities = FXCollections.observableArrayList();
    ListView<String> listCities = new ListView<>(cities);
    listCities.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    //Connection
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

    //Search-pane
      Pane searchPane = new Pane();
      Label start = new Label("Add city");

      TextField startField = new TextField();

      //Search: skapa scen för att visa noderna och vägar mellan:
      Pane routePane = new Pane();

      Button searchButton = new Button("Search");
      searchButton.setOnAction(
              (arg) -> {
                  root.setCenter(routePane);
              });

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
                          routePane.getChildren().add(new VisualNode(textInput,100,100));
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
                  addConnectionConfirmation.setHeaderText("Add connection between " + selected.get(0) + " and " +
                          selected.get(1) + "while here, also enter name and distance");
                  addConnectionConfirmation.setContentText("Name");
                  addConnectionConfirmation.setContentText("Distance?");
                  addConnectionConfirmation.getDialogPane().setContent(connectionPane);

                  Optional<String> result = addConnectionConfirmation.showAndWait();

                  if (result.isPresent()) {
                      //koppla ihop två noder graph.connect(selected,x )
                      int distance = Integer.parseInt(connectionDistance.getText());
                      graph.connect(selected.get(0), selected.get(1), connectionName.getText(), distance);

                      System.out.print(graph.getEdgeBetween(selected.get(0), selected.get(1)));

                      selected.clear();

                      root.setCenter(searchPane);
                  }

              });

      //searchButton.setOnAction(new searchHandler());
      searchPane.getChildren().addAll(backgroundViewSearch, start, startField, searchButton, addButton,
              removeButton, addConnectionButton, listCities);

      start.relocate( 200, 100);
      startField.relocate(200, 120);
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
    FileChooser fileChooser = new FileChooser();
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
    MenuItem open = new MenuItem("Open");
      open.setOnAction(
              (arg) -> {
                  File openFile = fileChooser.showOpenDialog(stage);
                  System.out.println(openFile);
              });
      MenuItem save = new MenuItem("Save");
      save.setOnAction(
              (arg) -> {
                  stage.close();
              });
    MenuItem exit = new MenuItem("Exit");
    exit.setOnAction(
            (arg) -> {
                stage.close();
            });
    menu.getItems().addAll(searchRoute, settings, home, exit, save, open);


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
