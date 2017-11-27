import java.io.*;
import java.util.*;

import net.datastructures.AdjacencyMapGraph;
import net.datastructures.Dijkstra;
import net.datastructures.Edge;
import net.datastructures.Graph;
import net.datastructures.GraphAlgorithms;
import net.datastructures.Map;
import net.datastructures.Vertex;

public class Metro{

 private Map<Integer,Vertex> vertices;
 private ArrayList<Route> a;
 private ArrayList<Station> b;
 private Graph<Integer,Station> graph;
 //ArrayList<Transfer> c;
 private Station station, depart, end;
 private Route transit;
 private StringTokenizer token;
 private String line;
 private String number, name, start, stop, time, transfer;
 private int count = 0;


 public Metro(){
  a = new ArrayList<Route>();
  b = new ArrayList<Station>();
  graph = new AdjacencyMapGraph<Integer,Station>(false);
 }

 private void createStation(int stationNumber, String stationName){
  station = new Station(stationNumber,stationName);
  b.add(stationNumber,station);
 }

 private void createRoute(int stationStart, int stationStop, int secs){
  depart = b.get(stationStart);
  end = b.get(stationStop);
  transit = new Route(depart,end,secs);
 }

 private void placeTransfer(int stationStart, int stationStop, int transfer){
  depart = b.get(stationStart);
  end = b.get(stationStop);
  if(!depart.hasTransfer()){
   depart.setTransfer(-1);
  }
  if(!end.hasTransfer()){
   end.setTransfer(-1);
  }
  depart.addTransfer(end);
 }

 private int getNumber(String s){
  // private method which takes in a string and returns a number
  return Integer.parseInt(s);
 }

 private void buildGraph(){
  vertices = new HashMap<Integer,Vertex>();
  for(){
   int k = transit.getTime();
   Vertex<Station> dv = vertices.get(transit.getSource());
   if(dv == null){
    // source vertex not in graph -- insert
    graph.insertVertex(dv);
    vertices.put(transit.getSource().getStationNumber(),dv);
   }
   Vertex<Station> dx = vertices.get(transit.getDestination());
   if(dx == null){
    // destination vertex not in graph -- insert
    dx = graph.insertVertex(dx);
    vertices.put(transit.getDestination().gegetStationNumber(),dv);
   }
   // check if edge is already in graph
   if(graph.getEdge(dv, dx) == null){
    Edge<Station> e = graph.insertEdge(dv,dx,time);
   }
  }
 }


 public ArrayList<Route> readMetro (String fileName) throws Exception, IOException{

  BufferedReader metro = new BufferedReader(new FileReader(fileName));
  metro.readLine(); // this read the first read line
  while((line = metro.readLine()) != null){
   // starts from second read line
   token = new StringTokenizer(line);

   if(line.contains("$"))
    count++;

   if(!line.contains("$")){
    token = new StringTokenizer(line);
     if(count == 0){
      number = token.nextToken();
      name = token.nextToken();
      createStation(getNumber(number),name);
     }else if(count == 1){
      start = token.nextToken();
      stop = token.nextToken();
      time = token.nextToken();
      if(getNumber(time) == -1){
       placeTransfer(getNumber(start), getNumber(stop), getNumber(time));
      }else{
       createRoute(getNumber(start), getNumber(stop), getNumber(time));
      }
     }
    }
   }
   return a;
  }

  public static void main(String[] args) throws IOException, Exception {
   Metro metro = new Metro();
   ArrayList<Route> f = metro.readMetro("metro.txt");
   for (int i=0; i< f.size(); i++) {
    //System.out.println(f.get(i).toString());
   }
  }

}
