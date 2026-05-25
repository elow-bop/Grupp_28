package se.su.inlupp;

import java.util.HashSet;
import java.util.Set;

public class Controller {
    private Graph<String> graph = new ListGraph<String>();
    Set<VisualNode> visualNodes = new HashSet<>();

    public void addNode(String node){
        graph.add(node);
    }

    public void removeNode(String node){
        graph.remove(node);
    }

    public void addConnection(String node1, String node2, String transportation, int distance ){
        graph.connect(node1, node2, transportation, distance);
    }

    public VisualNode addVisualNode(String name){
        VisualNode visualNode = new VisualNode(name, 100, 100);
        visualNodes.add(visualNode);
        return visualNode;
    }



    public VisualNode getVisualNode(String name){
        for(VisualNode visualNode : visualNodes){
            if(visualNode.get)
        }
    }
}

