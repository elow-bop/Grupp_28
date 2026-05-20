package se.su.inlupp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
//test push
public class DFSPathFinder<T> implements PathFinder<T> {

    //la in path klassen som en klass i listgraph istället för en egen.
    @Override
    public Path<T> findPath(Graph<T> graph, T start, T end) { //graph som skickas in är ett objekt som användaren skapat. Alltså alla noder som vi ska söka efter dne snabbaste vägen i
        PathClass<T> pathObj = new PathClass<>(start, end); //vi skapar ett path objekt, där den tar emot start och slut och skapar en pathlista av kanter
        Map<T, T> connections = new HashMap<>();

        //anropar direkt metod för att sätta kopplingarna i listan connections
        explorer(graph, start, null, connections);//första noden, nyckeln, ska alltid vara kopplad till null

        T current = end;
        if(!connections.containsKey(end)){
            return null;
        }
        while (current != null) {
            T next = connections.get(current);
            if (next == null)
                break;
            Edge<T> edge = graph.getEdgeBetween(next, current);
            pathObj.path.addFirst(edge);
            current = next;
        }

        return pathObj;
    }

    private void explorer(Graph<T> graph, T current, T from, Map<T, T> connections) {
        //from är i starten null
        //vi kopplar samman current(som är topp-nod i starten) och null:
        connections.put(current, from);
        //hämta alla kanter från nyckelnoden
        for (Edge<T> edge : graph.getEdgesFrom(current)) {
            T destination = edge.getDestination(); //nästa nod
            if (!connections.containsKey(destination)) { //om listan av noder inte innehåller nästa nod, fortsätt leta
                explorer(graph, destination, current, connections); //så länge vi hittar noder vi inte känner igen, gör om metoden
            }
        }
    }

}

