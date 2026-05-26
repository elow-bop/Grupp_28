package se.su.inlupp;

import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class VisualNode extends BorderPane {
    private String name;
    private double startX, startY;

    public VisualNode(String name, double x, double y) {
        relocate(x,y);
        this.name = name;

        Label nodeName = new Label(name);
        setCenter(nodeName);
        setPrefSize(50, 50);
        setBackground(Background.fill(Color.AQUA));

        setOnMouseDragged(new DragHandler());
        setOnMousePressed(new StartDragHandler);
        setOnMouseClicked(new ClickHandler());
    }

    public String getName(){
        return name;
    }

    public DoubleBinding coordinateX(){
        return layoutXProperty().add(getPrefWidth()/2.0);
    }

    public DoubleBinding coordinateY(){
        return layoutXProperty().add(getPrefHeight()/2.0);
    }

    class DragHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event){
            double newX = getLayoutX() + event.getX();
            double newY = getLayoutY() + event.getY();
            relocate(newX, newY);
        }
    }

    class ClickHandler implements EventHandler<MouseEvent>{
        public void handle(MouseEvent event){
            Label selected = new Label("SELECTED");
            setRight(selected);
        }
    }

    class StartDragHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
            startX = event.getX();
            startY = event.getY();
        }
    }
}
