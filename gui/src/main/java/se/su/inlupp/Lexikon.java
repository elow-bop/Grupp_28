//package se.su.inlupp;
//
//import javafx.application.Application;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.FlowPane;
//import javafx.scene.layout.VBox;
//import javafx.stage.FileChooser;
//import javafx.stage.Stage;
//import javafx.stage.WindowEvent;
//
//import java.io.*;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//public class Lexikon extends Application {
//    private Stage stage;
//    private Map<String, String> lexikon = new HashMap<>();
//    private TextField searchField;
//    private TextArea display;
//    private ListView<String> listView;
//    private Button saveButton;
//    private FileChooser fileChooser = new FileChooser();
//    private ChangeListener<String> displayListener = new DisplayChanged();
//    private boolean changed = false;
//
//    public void start(Stage primaryStage) {
//        stage = primaryStage;
//        BorderPane root = new BorderPane();
//
//        VBox vbox = new VBox();
//        MenuBar menuBar = new MenuBar();
//        FlowPane controls = new FlowPane();
//
//        vbox.getChildren().add(menuBar);
//        vbox.getChildren().add(controls);
//
//        controls.setAlignment(Pos.CENTER);
//        controls.setPadding(new Insets(5));
//        controls.setHgap(5);
//        searchField = new TextField();
//        Button searchButton = new Button("Search");
//        searchButton.setOnAction(new SearchHandler());
//        Button insertButton = new Button("Insert");
//        insertButton.setOnAction(new InsertHandler());
//        saveButton = new Button("Save");
//        saveButton.setOnAction(new SaveButtonHandler());
//        saveButton.setVisible(false);
//        controls.getChildren().addAll(searchField, searchButton, insertButton, saveButton);
//
//        Menu arkivmeny = new Menu("Arkiv");
//        MenuItem openItem = new MenuItem("Open");
//        arkivmeny.getItems().add(openItem);
//        openItem.setOnAction(new OpenHandler());
//        MenuItem saveItem = new MenuItem("Save");
//        arkivmeny.getItems().add(saveItem);
//        saveItem.setOnAction(new SaveHandler());
//        MenuItem exitItem = new MenuItem("Exit");
//        arkivmeny.getItems().add(exitItem);
//        exitItem.setOnAction(new ExitItemHandler());
//        menuBar.getMenus().add(arkivmeny);
//
//        display = new TextArea();
//        display.setWrapText(true);
//        display.textProperty().addListener(displayListener);
//
//        listView = new ListView<>();
//        listView.setPrefWidth(100);
//        listView.getSelectionModel().selectedItemProperty().addListener(new SelectWord());
//
//        root.setTop(vbox);
//        root.setCenter(display);
//        root.setLeft(listView);
//
//        Scene scene = new Scene(root, 500, 300);
//        stage.setTitle("Lexikon");
//        stage.setScene(scene);
//        stage.setOnCloseRequest(new ExitHandler());
//        stage.show();
//    }
//    private void open(String fileName){
//        try {
//            FileInputStream file = new FileInputStream(fileName);
//            ObjectInputStream in = new ObjectInputStream(file);
//            lexikon = (Map) in.readObject();
//            ObservableList<String> theList =  FXCollections.observableArrayList(lexikon.keySet());
//            FXCollections.sort(theList);
//            listView.setItems(theList);
//        } catch (FileNotFoundException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR, "Can't open file " + fileName + "!");
//            alert.showAndWait();
//        } catch (IOException e){
//            Alert alert = new Alert(Alert.AlertType.ERROR, "IO-error " + e.getMessage());
//            alert.showAndWait();
//        } catch (ClassNotFoundException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR, "Can't find class " + e.getMessage());
//            alert.showAndWait();
//        }
//    }
//    private void save(String fileName){
//        try {
//            FileOutputStream file = new FileOutputStream(fileName);
//            ObjectOutputStream out = new ObjectOutputStream(file);
//            out.writeObject(lexikon);
//            out.close();
//            file.close();
//        } catch (FileNotFoundException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR, "Can't open file!");
//            alert.showAndWait();
//        } catch (IOException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR, "IO-error " + e.getMessage());
//            alert.showAndWait();
//        }
//    }
//    class OpenHandler implements EventHandler<ActionEvent> {
//        public void handle(ActionEvent event) {
//            File file = fileChooser.showOpenDialog(stage);
//            if (file != null) {
//                open(file.getAbsolutePath());
//                changed = false;
//            }
//        }
//    }
//    class SelectWord implements ChangeListener<String> {
//        public void changed(ObservableValue obs, String oldValue, String newValue) {
//            saveButton.setVisible(false);
//            String word = listView.getSelectionModel().getSelectedItem();
//            String def = lexikon.get(word);
//
//            //Förändring av texten i display, men ingen egentlig förändring
//            //så vi tar bort displayListener
//            display.textProperty().removeListener(displayListener);
//            display.setText(def);
//            display.textProperty().addListener(displayListener);
//        }
//    }
//    class SaveButtonHandler implements EventHandler<ActionEvent> {
//        public void handle(ActionEvent event) {
//            String word = listView.getSelectionModel().getSelectedItem();
//            lexikon.put(word, display.getText());
//            saveButton.setVisible(false);
//            changed = true;
//        }
//    }
//    class DisplayChanged implements ChangeListener<String> {
//        public void changed(ObservableValue obs, String oldValue, String newValue) {
//            saveButton.setVisible(true);
//            changed = true;
//        }
//    }
//    class SaveHandler implements EventHandler<ActionEvent> {
//        public void handle(ActionEvent event){
//            File file = fileChooser.showSaveDialog(stage);
//            if (file != null) {
//                save(file.getAbsolutePath());
//                changed = false;
//            }
//        }
//    }
//    class SearchHandler implements EventHandler<ActionEvent> {
//        public void handle (ActionEvent event) {
//            String word = searchField.getText();
//            if (word.strip().isEmpty())
//                return;
//
//            String def = lexikon.get(word);
//            display.textProperty().removeListener(displayListener);
//            if (def == null) {
//                listView.getSelectionModel().clearSelection();
//                display.setText("Nothing found!");
//            } else {
//                listView.getSelectionModel().select(word);
//                display.setText(def);
//            }
//            display.textProperty().addListener(displayListener);
//        }
//    }
//    class InsertHandler implements EventHandler<ActionEvent> {
//        public void handle(ActionEvent event) {
//            String word = searchField.getText();
//            lexikon.put(word, "");
//
//            //Stoppa in det nya namnet i sorteringsordning
//            //Om ordet inte hittas returnerar BinarySearch-metoden
//            //positionen där ordet borde ha varit fast på formen -pos-1
//            int index = Collections.binarySearch((listView.getItems()), word);
//            if (index < 0) {
//                listView.getItems().add(-index - 1, word);
//                changed = true;
//            }
//        }
//    }
//    class ExitItemHandler implements EventHandler<ActionEvent> {
//        public void handle(ActionEvent event){
//            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
//        }
//    }
//    class ExitHandler implements EventHandler<WindowEvent> {
//        public void handle(WindowEvent event) {
//            if (changed) {
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setContentText("Osparade ändringar, vill du avsluta ändå?");
//                Optional<ButtonType> res = alert.showAndWait();
//                if (res.isPresent() && res.get().equals(ButtonType.CANCEL)){
//                    event.consume();
//                }
//            }
//        }
//    }
//}

