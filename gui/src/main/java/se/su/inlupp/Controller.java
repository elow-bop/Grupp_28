package se.su.inlupp;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Controller {
    private Graph<String> graph = new ListGraph<String>();
    Set<VisualNode> visualNodes = new HashSet<>();

    public VisualNode addNode(String node){
        graph.add(node);
        VisualNode visualNode = new VisualNode(node, 100, 100);
        visualNodes.add(visualNode);
        return visualNode;
    }

    public void removeNode(String node){
        graph.remove(node);
        visualNodes.remove(node);
    }

    public void addConnection(String node1, String node2, String transportation, int distance ){
        graph.connect(node1, node2, transportation, distance);
    }

    public VisualNode getVisualNode(String name){
        for(VisualNode visualNode : visualNodes){
            if(visualNode.getName().equals(name)){
                return visualNode;
            }
        }
        return null;
    }

    public Path<String> pathFinderDFS(String startNode, String endNode){
        DFSPathFinder pathFinder = new DFSPathFinder();
        return pathFinder.findPath(graph, startNode, endNode);

    }

    public Path<String> pathFinderBFS(String startNode, String endNode ){
        BFSPathFinder pathFinder = new BFSPathFinder();
        return pathFinder.findPath(graph, startNode, endNode);
    }


}

