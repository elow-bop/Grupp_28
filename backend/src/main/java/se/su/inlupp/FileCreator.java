package se.su.inlupp;

import java.io.*;

public class FileCreator<T> {
    private Graph<T> graph;
    private File fileName;
    private String imageString;
    private String imageToGUI;

    public FileCreator(Graph<T> graph, File fileName, String imageString) throws IOException {
        this.graph = graph;
        this.fileName = fileName;
        this.imageString = imageString;

    }

    public void fileWriter() throws IOException {
       try (FileWriter filewriter = new FileWriter(fileName);
        BufferedWriter writer = new BufferedWriter(filewriter))
        {

            writer.write("{BACKGROUND}");
            writer.newLine();
            writer.write(imageString);
            writer.newLine();

            writer.write("{NODES}");
            writer.newLine();
            for (T node : graph.getNodes()) {
                writer.write(node.toString());
                writer.newLine();
            }

            writer.write("{EDGES}");
            writer.newLine();
            for (T node : graph.getNodes()) {
                for (Edge<T> edge : graph.getEdgesFrom(node)) {
                    String rad = node.toString() + ";" + edge.getDestination().toString() +
                            ";" + edge.getName() + ";" + edge.getWeight();
                    writer.write(rad);
                    writer.newLine();
                }
            }
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public Graph<T> fileReader() throws IOException {
       try (FileReader fileReader = new FileReader(fileName);
        BufferedReader reader = new BufferedReader(fileReader))

       {
           String line;
           String current = "";

           while((line = reader.readLine()) != null){
               if(line.trim().isEmpty()){
                   continue;
               } else if(line.equals("{BACKGROUND}")){
                   current = "background";
                   continue;
               } else if(line.equals("{NODES}")){
                   current = "nodes";
                   continue;
               } else if (line.equals("{EDGES}")){
                   current = "edges";
                   continue;
               }

               switch(current){
                   case "background":
                       this.imageToGUI = line;
                       break;
                   case "nodes":
                       graph.add((T) line);
                        break;

                   case "edges":
                       String[] edge = line.split(";");
                       T fromNode = (T) edge[0];
                       T toNode = (T) edge[1];
                       String name = edge[2];
                       int weight = Integer.parseInt(edge[3]);

                       if(graph.getEdgeBetween(fromNode, toNode) == null){
                           graph.connect(fromNode, toNode, name, weight);

                       }
                       break;
               }

        }

    } catch (FileNotFoundException e) {
           System.out.print(e.getMessage());
    } catch (IOException e) {
        e.printStackTrace();
    }
        return graph;
    }

    public String imageSender(){
        return imageToGUI;
    }


}
