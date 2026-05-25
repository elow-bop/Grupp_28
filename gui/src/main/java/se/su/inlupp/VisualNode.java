package se.su.inlupp;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class VisualNode extends BorderPane {

    public VisualNode(String name, double x, double y) {
        relocate(x,y);
        Label nodeName = new Label(name);
        setCenter(nodeName);
        setPrefSize(50, 50);
        setBackground(Background.fill(Color.ORANGE));
        //hanterare som hanterar om vi drar musen
        setOnMouseDragged(new DragHandler());

    }
    class DragHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event){
            double newX = getLayoutX() + event.getX();
            double newY = getLayoutY() + event.getY();
            relocate(newX, newY);
        }
    }
}
