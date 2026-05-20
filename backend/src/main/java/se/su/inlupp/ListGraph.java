package se.su.inlupp;

import java.util.*;

public class ListGraph<T> implements Graph<T> {
    Map<T, List<Edge<T>>> nodeMap = new HashMap<>();

    //Klar
    @Override
    public void add(T node) {
        nodeMap.putIfAbsent(node, new ArrayList<>());
    }

    @Override
    //Klar
    public void remove(T node) throws NoSuchElementException {

        if (!nodeMap.containsKey(node)) {
            throw new NoSuchElementException("Noden finns inte");
        }
        for (Edge<T> edge : getEdgesFrom(node)) {
            disconnect(node, edge.getDestination());
        }
        nodeMap.remove(node);
    }


    @Override
    //Klar
    public boolean hasNode(T node) {
        return nodeMap.containsKey(node);
    }

    @Override
    //Klar
    public void connect(T node1, T node2, String name, int weight) {
        List<Edge<T>> node1edges = nodeMap.get(node1);

        if (!(hasNode(node1)) || !(hasNode(node2))) {
            throw new NoSuchElementException("Ingen sådan nod");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Vikten är negativ");
        }

        for (Edge<T> edge : node1edges) {
            if (edge.getDestination().equals(node2)) {
                throw new IllegalStateException("Kanten finns redan mellan noderna");
            }
        }


        EdgeClass<T> edge1 = new EdgeClass(node2, name, weight);
        EdgeClass<T> edge2 = new EdgeClass(node1, name, weight);

        node1edges.add(edge1);

        List<Edge<T>> node2edges = nodeMap.get(node2);
        node2edges.add(edge2);

    }

    @Override
    //Klar
    public void disconnect(T node1, T node2) {
        if (!(hasNode(node1) || !(hasNode(node2)))) {
            throw new NoSuchElementException("Någon eller båda av noderna finns inte");
        }
        if ((getEdgeBetween(node1, node2) == null)) {
            throw new IllegalStateException("Ingen kant existerar mellan noderna");
        }

        nodeMap.get(node1).remove(getEdgeBetween(node1, node2));
        nodeMap.get(node2).remove(getEdgeBetween(node2, node1));

//      for(Edge<T> edge : nodeMap.get(node1)) {
//          if (edge.getDestination().equals(node2)){
//            nodeMap.get(node1).remove(edge);
//            nodeMap.get(node2).remove();
//          }else{
//            throw new IllegalStateException("Ingen kant existerar mellan noderna");
//          }
//      }
    }

    @Override
    //Klar
    public Collection<Edge<T>> getEdgesFrom(T node) {
        if (!hasNode(node)) {
            throw new NoSuchElementException("Ingen sådan nod");
        }
        List<Edge<T>> edgesFrom = null;
        edgesFrom = (nodeMap.get(node));
        return Collections.unmodifiableList(edgesFrom);
    }

    @Override
    //Klar
    public Edge<T> getEdgeBetween(T node1, T node2) {
        if (!(hasNode(node1)) || !(hasNode(node2))) {
            throw new NoSuchElementException("Nod saknas!");
        }
        for (Edge<T> edge : nodeMap.get(node1)) {
            if (edge.getDestination().equals(node2)) {
                return edge;
            }
        }
        return null;
    }

    @Override
    //Klar
    public void setConnectionWeight(T node1, T node2, int weight) {

        if ((!(hasNode(node1)) || !(hasNode(node2)) || (getEdgeBetween(node1, node2) == null))) {
            throw new NoSuchElementException("Någon eller båda av noderna finns inte");
        }

        if (weight < 0) {
            throw new IllegalArgumentException("Vikten är negativ");
        }

        Edge<T> edge1 = getEdgeBetween(node1, node2);
        Edge<T> edge2 = getEdgeBetween(node2, node1);
        edge1.setWeight(weight);
        edge2.setWeight(weight);
    }

    @Override
    //Klar
    public Set<T> getNodes() {
        return Collections.unmodifiableSet(nodeMap.keySet());
    }

    @Override
    //Klar
    public Iterator<T> iterator() {
        return getNodes().iterator();
    }

    @Override
    //klar
    public String toString() {
        return (nodeMap.toString());
    }

}
//inre klass som används i DFS och BFS
class PathClass<T> implements Path<T>{
    private final T startNode;
    private final T endNode;

    LinkedList<Edge<T>> path = new LinkedList<>();

    public PathClass(T startNode, T endNode){
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public T getStart(){
        return startNode;
    }

    public T getEnd(){
        return endNode;
    }

    //returnera totalvikten av kanterna som leder från start till slut nod
    public int getTotalWeight() {
        int totalWeight = 0;
        for (Edge<T> edge : path) {
            totalWeight += edge.getWeight();
        }
        return totalWeight;
    }
    //måste vi inte returnera en unmodifiable, eller loopa igenom? eller en referens? kan inte...
    public List<Edge<T>> getEdges(){
        return path;
    }

    public List<T> getNodes(){
        ArrayList<T> nodes = new ArrayList<>();
        nodes.add(startNode);

        for(Edge<T> edge : path){
            nodes.add(edge.getDestination());
        }
        return nodes;
    }

    @Override
    public Iterator<Edge<T>> iterator() {
        return getEdges().iterator();
    }
}





