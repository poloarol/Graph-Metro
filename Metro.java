import java.io.*;
import java.util.StringTokenizer;
import java.util.Hashtable;
import java.util.Iterator;

import net.datastructures.*;

public class Metro{

 private ArrayList<Route> a;
 private ArrayList<Station> b;
 private Graph<Station,Integer> graph;
 private Hashtable<Station, Vertex> vertices;
 private Station station, depart, end;
 private Route transit;
 private StringTokenizer token;
 private String line;
 private String number, name, start, stop, time;

 public Metro(String fileName) throws Exception, IOException{
  a = new ArrayList<Route>();
  b = new ArrayList<Station>();
  vertices = new Hashtable<Station,Vertex>();
  graph = new AdjacencyMapGraph<Station,Integer>(true);
  readMetro(fileName);
 }

 /*

  Private method which allows the creation of stations
  @params
   stationNumber : Integer identifying the station
   stationName : String which is the name of the station

 */

 private void createStation(int stationNumber, String stationName){
  station = new Station(stationNumber,stationName);
  b.add(stationNumber,station);
 }

 /*

  Private method which serves the purpose of cresting routes between stations
  @params
   stationStart : Station where the transit begins
   stationStop : Station where the transit ends

 */

 private void createRoute(int stationStart, int stationStop, int secs){
  int i = 0;
  transit = new Route(b.get(stationStart),b.get(stationStop),secs);
  a.add(i,transit);
  b.get(stationStart).addRoute(transit);
  i++;
 }

 /*

  Private method which indicates transfers between identical stations.
  This stations vary by their station # but not station name
  @params
   stationStart : Arrival station in transit
   stationStop : Station which serves as transfer for transit

 */

 private void placeTransfer(int stationStart, int stationStop){
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

 /**
  * Helper routine to get a Vertex (Position) from a string naming
  * the vertex
  */

 protected Vertex<Station> getVertex(Station s) throws Exception{
  for(Vertex<Station> vs : graph.vertices()){
   if(s.equals(vs.getElement())){
    return vs;
   }
  }
  throw new Exception("Vertex not in graph");
 }

 /** Helper method:
  * Read a String representing a vertex from the console
  */

 public static String readVertex() throws IOException{
  System.out.print("[Input] Vertex: ");
  BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

  return reader.readLine();

 }

 void print(){
  System.out.println("Vertices: " + graph.numVertices() + " Edges: " + graph.numEdges());
  for(Vertex<Station> vs : graph.vertices()){
   System.out.println(vs.getElement().getStationName());
  }
  for(Edge<Integer> es : graph.edges()){
   System.out.println(es.getElement());
  }
 }

 /**

  Private method which serves in the building of a graph by creating adjacent vertexes
  and linking them through the use of edges

 */

 private void buildGraph(Station start, Station stop, int time){

  //Get the edges and insert them
  Vertex<Station> sv = vertices.get(start);
  if(sv == null){
   // Source vertex is not in graph -- insert
   sv = graph.insertVertex(start);
   vertices.put(start,sv);
  }
  Vertex<Station> dv = vertices.get(stop);
  if(dv == null){
   // Destination vertex is not in graph -- insert
   dv = graph.insertVertex(stop);
   vertices.put(stop,dv);
  }
  // check if edge is already in graph
  if(graph.getEdge(sv,dv) == null){
   Edge<Integer> e = graph.insertEdge(sv,dv,time);
  }
 }

 /**

  method which takes the start and stop number of two stations and
  determines the fastest path to get to the stop station from the start station

 */

 public void uniformCostSearch(int... numbers) throws Exception{

  if(numbers.length == 3){
    delete(b.get(numbers[2]));
  }

  Vertex<Station> source = getVertex(b.get(numbers[0]));

  while(!source.equals(numbers[1])){
   System.out.println(source.getElement().getStationNumber() + " : " + source.getElement().getStationName());
   source = nextStop(source, getVertex(b.get(numbers[1])));
   if(source == null || source.equals(numbers[1]))
    break;
  }
  System.out.println(b.get(numbers[1]).getStationNumber() + " : " + b.get(numbers[1]).getStationName());
 }

 private Stack<Vertex> getAllNeighbours(Vertex<Station> s){
  Stack<Vertex> n = new LinkedStack();
  Iterable<Edge<Integer>> e = graph.outgoingEdges(s);
  for(Edge<Integer> v : e){
   n.push(graph.opposite(s,v));
  }
  return n;
 }

 private void delete(Station broken){
   vertices.remove(broken);
 }


 /**
  recurisve method which takes in a stack containing the neighbours
  of a start vertex and finds the shortest path to get to the stop vertex
  and return a Stack of vertexes to the path
 */

 private Vertex<Station> nextStop(Vertex<Station> start, Vertex<Station> stop){
  int shortest = 0;
  Vertex<Station> source, closest = null;
  Stack<Vertex> sk = getAllNeighbours(start);

  while(!sk.isEmpty()){
   source = sk.pop();
   if(!source.getElement().isVisited()){
    source.getElement().setVisited(true);

    GraphAlgorithms dj = new GraphAlgorithms();
    Map<Vertex<Station>,Integer> result = dj.shortestPathLengths(graph, source);

    for(Vertex<Station> goal : graph.vertices()){
      if(goal.equals(stop)){
        if(!source.getElement().equals(start.getElement())){
          if(shortest == 0){
            closest = source;
            shortest = result.get(goal);
          }
          if(result.get(goal) < shortest){
            shortest = result.get(goal);
            closest = source;
          }
        }
      }
      if(source.getElement().equals(stop.getElement()))
        return null;
    }
   }
  }
  //System.out.println(closest.getElement().getStationName());
  return closest;
 }


 public void printAllShortestDistances(int ver) throws Exception{
  Station s = b.get(ver);
  Vertex<Station> vSource = getVertex(s);

  GraphAlgorithms dj = new GraphAlgorithms();
  Map<Vertex<Station>,Integer> result = dj.shortestPathLengths(graph, vSource);

  for(Vertex<Station> goal : graph.vertices()){
   System.out.println(s.getStationName() + " -> " + goal.getElement().getStationName() + "takes " + result.get(goal));
  }
 }

 /**

  method which is used to read a file and create stations and call
  accessory methods to build the graph

 */

 public void readMetro(String fileName) throws Exception, IOException{
  BufferedReader metro = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF8"));
  metro.readLine(); // this read the first read line
  int count = 0;
  while((line = metro.readLine()) != null){
   // starts from second read line
   token = new StringTokenizer(line, " ");

   if(line.contains("$"))
    count++;

   if(!line.contains("$")){
    token = new StringTokenizer(line);
     if(count == 0){
      number = token.nextToken();
      name = token.nextToken();
      if(token.countTokens() != 0){
       while(token.hasMoreTokens()){
        name = name + " " + token.nextToken();
       }
      }
      createStation(getNumber(number),name);
     }else if(count == 1){
      start = token.nextToken();
      stop = token.nextToken();
      time = token.nextToken();
      if(getNumber(time) == -1){
       placeTransfer(getNumber(start), getNumber(stop));
       buildGraph(b.get(getNumber(start)),b.get(getNumber(stop)), 90);
      }else{
       createRoute(getNumber(start), getNumber(stop), getNumber(time));
       buildGraph(b.get(getNumber(start)),b.get(getNumber(stop)),getNumber(time));
      }
     }
    }
   }
  }



 public static void main(String[] args){
   if(args.length < 1){
    System.exit(-1);
   }
   try{
    Metro metro = new Metro(args[0]);
    //metro.print();
    //metro.printAllShortestDistances(7);
    //metro.uniformCostSearch(7,201);
    metro.uniformCostSearch(25,201,330);
   }catch(Exception e){
    e.printStackTrace();
   }
 }
}
