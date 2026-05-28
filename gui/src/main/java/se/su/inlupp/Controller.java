package se.su.inlupp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Controller {
    private Graph<String> graph = new ListGraph<String>();
    private Set<VisualNode> visualNodes = new HashSet<>();
    private ObservableList<String> cities = FXCollections.observableArrayList();

    public void addNode(String node){
        graph.add(node);
    }

    public Set<VisualNode> createVisualNodes(Graph<String> graph){
        for(String s : graph){
            VisualNode visualNode = new VisualNode(s, 100, 100);
            visualNodes.add(visualNode);
        }
        return visualNodes;
    }

    public void clearVisualNodes(){
        visualNodes.clear();
    }

    public Set<VisualNode> getVisualNodes(){
        return visualNodes;
    }


    public void removeNode(String node){
        graph.remove(node);
    }

    public void removeNodeFromCities(String node){
        cities.remove(node);
    }

    public void addConnection(String node1, String node2, String transportation, int distance ){
        graph.connect(node1, node2, transportation, distance);
    }

//    public VisualEdge addVisualConnection(String node1, String node2){
//        return new VisualEdge(node1, node2);
//    }

    public VisualNode getVisualNode(String name){
        for(VisualNode visualNode : visualNodes){
            if(visualNode.getName().equals(name)){
                return visualNode;
            }
        }
        return null;
    }

    public void addCities(Graph<String> graph){
        for(String s : graph.getNodes()){
            if(!cities.contains(s)){
                cities.add(s);
            }
        }
    }

    public ObservableList<String> getCities(){
        return cities;
    }

    public Graph<String> getGraph (){
        return graph;
    }

    public Path<String> pathFinderDFS(String startNode, String endNode){
        DFSPathFinder pathFinder = new DFSPathFinder();
        return pathFinder.findPath(graph, startNode, endNode);
    }

    public Path<String> pathFinderBFS(String startNode, String endNode ){
        BFSPathFinder pathFinder = new BFSPathFinder();
        return pathFinder.findPath(graph, startNode, endNode);
    }

    public void fileSaver(File fileName) throws IOException{

        try{
            FileCreator<String> fc = new FileCreator<>(graph, fileName);
            fc.fileWriter();
        } catch (IOException e){
            e.getMessage();
        }
    }

    public void fileReader(File fileName) throws IOException{
        try{
            FileCreator<String> fc = new FileCreator<>(graph, fileName);
            fc.fileReader();
        }catch(IOException e){
            e.getMessage();
        }
    }


}

