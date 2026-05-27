package se.su.inlupp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

            //Set<T> nodesChecked = new HashSet<>();
            for(T node : graph.getNodes()){
                for(Edge<T> edge : graph.getEdgesFrom(node)){
//                    if(nodesChecked.contains(node) && nodesChecked.contains(edge.getDestination())){
//                        continue;
//                    }
//                    nodesChecked.add(node);
//                    nodesChecked.add(edge.getDestination());
                    writer.write(edge.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
