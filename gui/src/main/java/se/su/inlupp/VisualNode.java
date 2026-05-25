package se.su.inlupp;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class VisualNode extends VBox {
    private Label nameLabel;
    private Circle circle;

    public VisualNode(String name){
        this.nameLabel = new Label(name);
        this.circle = new Circle(300);
        circle.setFill(Color.AQUA);
        nameLabel.relocate(100, 100);

        this.setShape(circle);

        this.getChildren().addAll(circle, nameLabel);
    }

}
