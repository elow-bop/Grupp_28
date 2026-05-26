package se.su.inlupp;

import javafx.scene.shape.Line;

public class VisualEdge extends Line {
    public VisualEdge(VisualNode a, VisualNode b){
        setStrokeWidth(3);

        startXProperty().bind(a.coordinateX());
        startYProperty().bind(a.coordinateY());
        endXProperty().bind(b.coordinateX());
        endXProperty().bind(b.coordinateY());


    }
}
