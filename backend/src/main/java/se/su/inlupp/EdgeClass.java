package se.su.inlupp;

public class EdgeClass<T> implements Edge<T>{
    private final String name;
    private final T node;
    private int weight;

    public EdgeClass(T node, String name, int weight){
        this.node = node;
        this.name = name;
        this.weight = weight;
    }

    @Override
    public int getWeight(){
        return weight;
    }

    @Override

    public void setWeight(int weight){
        if(weight < 0){
            throw new IllegalArgumentException("Vikten får inte vara negativ");
        }
        this.weight = weight;
    }

    @Override
    public T getDestination(){
        return node;
    }
    @Override
    public String getName(){
        return name;
    }
    @Override
    public String toString(){
        return  getName();
    }
}
