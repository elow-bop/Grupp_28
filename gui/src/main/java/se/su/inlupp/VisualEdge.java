package se.su.inlupp;

import javafx.scene.shape.Line;

public class VisualEdge extends Line {
    public VisualEdge(VisualNode a, VisualNode b){

        setStartX(a.coordinateX());
        setStartY(a.coordinateY());
        setEndX(b.coordinateX());
        setEndY(b.coordinateX());
    }
}
