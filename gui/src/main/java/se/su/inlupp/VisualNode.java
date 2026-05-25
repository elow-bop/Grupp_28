package se.su.inlupp;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class VisualNode extends BorderPane {
    private String name;
    public VisualNode(String name, double x, double y) {
        this.name = name;
        relocate(x,y);
        Label nodeName = new Label(name);
        setCenter(nodeName);
        setPrefSize(50, 50);
        setBackground(Background.fill(Color.AQUA));
        //hanterare som hanterar om vi drar musen
        setOnMouseDragged(new DragHandler());

    }

    public String getName(){
        return name;
    }
    class DragHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event){
            double newX = getLayoutX() + event.getX();
            double newY = getLayoutY() + event.getY();
            relocate(newX, newY);
        }
    }
}
