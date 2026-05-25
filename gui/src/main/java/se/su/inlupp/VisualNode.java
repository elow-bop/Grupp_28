package se.su.inlupp;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class VisualNode extends BorderPane {

    public VisualNode(String name, double x, double y) {
        relocate(x,y);
        TextArea nodeName = new TextArea();
        setCenter(nodeName);
        setPrefSize(200, 200);
        this.setBackground(Background.fill(Color.AQUA));



    }
}
