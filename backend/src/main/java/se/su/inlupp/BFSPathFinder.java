package se.su.inlupp;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BFSPathFinder<T> implements PathFinder<T> {
//implementerar pathFinder med metoden findPath
    // FEL? använder sig av PathClass för att returnera en klass med snabbaste vägen som bestå av kanter

    //man skickar även här med graph som ska gås igenom för att hitta snabbaste vägen
    @Override
    public Path<T> findPath(Graph<T> graph, T from, T to) {
        if (graph.hasNode(from) && graph.hasNode(to)) {
            Map<T, T> connections = new HashMap<>();
            connections.put(from, null); //börja med att koppla startnoden till null
            LinkedList<T> queue = new LinkedList<>(); // här ska vi lägga in de noder som ska besökas.

            //vi måste ju returnera en PATHCLASS
            PathClass<T> pathTest = new PathClass<>(from, to);

//Starta med from, lägg sedan in next:
            queue.add(from);

            while (!queue.isEmpty()) {
                T current = queue.poll(); //ta ut och ta bort från kön, den är klar

                for (Edge<T> edge : graph.getEdgesFrom(current)) { //metod i ListGraph som ger alla kanter kopplade till grafen
                    T next = edge.getDestination(); //destinationsnoden via en av kanterna
                    if (!connections.containsKey(next)) {
                        connections.put(next, current);
                        queue.add(next);
                    }
                }
            }
            //vi har nu kommit till slutnoden
            //dags att lägga in kanterna i vår path variabel, ska vi skapa en inre metod??
            T current = to;
            //kolla att to finns i connections map!!
            if (connections.containsKey(to)) {
                while (current != null && !current.equals(from)) {
                    //problem, current, to, kan vara kopplad till null!
                    T next = connections.get(current); //ta fram noden som current är kopplad till
                    Edge<T> edge = graph.getEdgeBetween(next, current); //ska returnera kant mellan de två noderna ovan, men säger att nod saknas!
                    pathTest.path.addFirst(edge);
                    current = next;
                }

            } else{
                return null;
            }
            if (pathTest != null) {
                return pathTest;
            }
        }
        return null;
    }
}

