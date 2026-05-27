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
import javafx.stage.WindowEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Gui extends Application {

  public void start(Stage stage) {
    stage.setTitle("Route Planner");
    Controller controller = new Controller();

    //List-vyn
    ObservableList<String> cities = FXCollections.observableArrayList();
    ListView<String> listCities = new ListView<>(cities);
    listCities.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    //Connection
    HBox connectionPane = new HBox();
    TextField connectionName = new TextField();
    TextField connectionDistance = new TextField();

    connectionPane.getChildren().addAll(connectionName, connectionDistance);

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

      ListView<String> listCitiesRoute = new ListView<>(cities);
      listCitiesRoute.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

      Button showDFS = new Button("Show connection DFS");
      showDFS.setOnAction(
              (arg) -> {
                  ObservableList<String> selectedRoute = listCitiesRoute.getSelectionModel().getSelectedItems();
                  String node1 = selectedRoute.get(0);
                  String node2 = selectedRoute.get(1);
                  Path<String> pathDFS = controller.pathFinderDFS(node1,node2);
                  Alert alert = new Alert(Alert.AlertType.INFORMATION, pathDFS.toString());
                  alert.showAndWait();

              });

      Button showBFS = new Button("Show connection BFS");
      showBFS.setOnAction(
              (arg) -> {
                  ObservableList<String> selectedRoute = listCitiesRoute.getSelectionModel().getSelectedItems();
                  String node1 = selectedRoute.get(0);
                  String node2 = selectedRoute.get(1);
                  Path<String> pathBFS = controller.pathFinderBFS(node1,node2);
                  Alert alert = new Alert(Alert.AlertType.INFORMATION, pathBFS.toString());
                  alert.showAndWait();
              });

      routePane.getChildren().addAll(showDFS, showBFS, listCitiesRoute);
      showDFS.relocate(40, 370);
      showBFS.relocate(40, 400);
      listCitiesRoute.relocate(54, 210);
      listCitiesRoute.setPrefHeight(140);
      listCitiesRoute.setPrefWidth(110);

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
                          cities.add(textInput);
                          routePane.getChildren().add(controller.addNode(textInput));
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
                  controller.removeNode(selected);
                  routePane.getChildren().remove(controller.getVisualNode(selected));
              });

      //addConnection
      Button addConnectionButton = new Button("add connection");
      addConnectionButton.setOnAction(
              (arg) -> {
                  ObservableList<String> selected = listCities.getSelectionModel().getSelectedItems();
                  String node1 = selected.get(0);
                  String node2 = selected.get(1);

                  TextInputDialog addConnectionDialog = new TextInputDialog("Confirm");
                  addConnectionDialog.setTitle("Confirm");
                  addConnectionDialog.setHeaderText("Add connection between " + node1 + " and " +
                          node2 + "while here, also enter name and distance");
                  addConnectionDialog.setContentText("Name");
                  addConnectionDialog.setContentText("Distance?");
                  addConnectionDialog.getDialogPane().setContent(connectionPane);
                  Optional<String> result = addConnectionDialog.showAndWait();

                  if (result.isPresent()) {
                      //koppla ihop två noder graph.connect(selected,x )
                      int distance = Integer.parseInt(connectionDistance.getText());

                      controller.addConnection(node1, node2, connectionName.getText(), distance);
                      routePane.getChildren().add(new VisualEdge(controller.getVisualNode(node1), controller.getVisualNode(node2)));

                      selected.clear();
                      connectionDistance.clear();
                      connectionName.clear();

                      root.setCenter(searchPane);
                  }

              });

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
    MenuItem settings = new MenuItem("Settings");
    settings.setOnAction(
            (arg) -> {
                root.setCenter(settingsPane);
            });
    MenuItem home = new MenuItem("Home");
    home.setOnAction(
            (arg) -> {
                  root.setCenter(searchPane);
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

                  File saveFile = fileChooser.showSaveDialog(stage);
                  if (saveFile != null) {
                      try {
                          FileWriter filewriter = new FileWriter(saveFile);
                          BufferedWriter writer = new BufferedWriter(filewriter);
                          writer.write("{BAKGRUND}");
                          writer.newLine();
                          if(newBackground != null) {
                              //ska flytta ut newbackground
                              String bildUrl = newBackground.getUrl();
                              writer.write(bildUrl);
                          }else{
                              writer.write("/se.su.inlupp/bild.jpg");
                          }
                          writer.newLine();
                          writer.write("{NODES}");
                          writer.newLine();

                          for(String node : graph.getNodes()) {
                              writer.write(node);
                              writer.newLine();
                          }

                          writer.write("{EDGES}");
                          writer.newLine();
                          for(String edge : cities){
                              writer.write(edge);
                              writer.newLine();
                          }

                          writer.close();

                          Alert alert = new Alert(Alert.AlertType.INFORMATION, "File saved");
                          alert.showAndWait();
                          hasChanges = false;

                      } catch (IOException e) {
                          Alert alert = new Alert(Alert.AlertType.ERROR, "Could Not Save file " + e.getMessage());
                          alert.showAndWait();
                      }

                  }

              });

      MenuItem exit = new MenuItem("Exit");
      exit.setOnAction(
              (arg) -> {
                  if(hasChanges) {
                      stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                  }
                  stage.close();
              });
      stage.setOnCloseRequest(event -> {
          if(hasChanges) {
              Alert error = new Alert(Alert.AlertType.CONFIRMATION);
              error.setTitle("New Data Not Saved");
              error.setHeaderText("Close anyway? ");
              Optional<ButtonType> solution = error.showAndWait();
              if(solution.isPresent() && solution.get() == ButtonType.OK){
                  stage.close();
              }else{
                  event.consume();
              }
          }
      });
    menu.getItems().addAll(home, settings, save, open, exit);


    //Stoppa in i root
    root.setTop(vboxMenu);
    root.setCenter(searchPane);

    Scene scene = new Scene(root, 735, 490);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
