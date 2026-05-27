package se.su.inlupp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

public class FileCreator<T> {
    public FileCreator(Graph<T> graph, String filename){
        try (FileWriter filewriter = new FileWriter(filename);
            BufferedWriter writer = new BufferedWriter(filewriter))
        {
            writer.write("{NODES}");
            writer.newLine();
            for(T node : graph.getNodes()){
                writer.write(node.toString());
                writer.newLine();
            }

            writer.write("{EDGES}");
            writer.newLine();
            for(T node : graph.getNodes()){
                graph.getEdgesFrom(node);
                writer.write(edge.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
