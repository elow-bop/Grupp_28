package se.su.inlupp;

import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.util.Objects;

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

        setFocusTraversable(true);

        setOnMouseDragged(new DragHandler());
        setOnMousePressed(new StartDragHandler());
        setOnMouseClicked(new ClickHandler());

        focusedProperty().addListener((observable, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                // What happens when SELECTED
                setBackground(Background.fill(Color.ORANGE));
            } else {
                // What happens when DESELECTED
                setBackground(Background.fill(Color.AQUA));
            }
        });
    }

    public String getName(){
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof VisualNode)) {
            return false;
        }

        VisualNode otherNode = (VisualNode) other;
        return Objects.equals(name, otherNode.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    public DoubleBinding coordinateX(){
        return layoutXProperty().add(getPrefWidth()/2);
    }

    public DoubleBinding coordinateY(){
        return layoutYProperty().add(getPrefHeight()/2);
    }

    class DragHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event){
            double newX = getLayoutX() + event.getX() - startX;
            double newY = getLayoutY() + event.getY() - startY;
            relocate(newX, newY);
        }
    }

    class ClickHandler implements EventHandler<MouseEvent>{
        public void handle(MouseEvent event){
            requestFocus();
        }
    }

    class StartDragHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
            startX = event.getX();
            startY = event.getY();
        }
    }
}
