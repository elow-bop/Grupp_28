package se.su.inlupp;

public class Controller {
    private Graph<String> graph = new ListGraph<String>();

    public void addNode(String name){
        graph.add(name);
    }

    public void removeNode(String name){
        graph.remove(name);
    }

    public void addConnection(){}

    public VisualNode getVisualNode(String name){
        return null;
    }
}

