import java.io.*;
import java.util.StringTokenizer;
import java.util.Hashtable;
import java.util.Iterator;

import net.datastructures.*;

public class Metro{

 private ArrayList<Route> a;
 private ArrayList<Station> b;
 private Graph<Station,Integer> graph;
 private Hashtable<Station, Vertex> vertices, hash;
 //ArrayList<Transfer> c;
 private Station station, depart, end;
 private Route transit;
 private StringTokenizer token;
 private String line;
 private String number, name, start, stop, time, transfer;
 private int count;

 public Metro(String fileName) throws Exception, IOException{
  a = new ArrayList<Route>();
  b = new ArrayList<Station>();
  vertices = new Hashtable<Station,Vertex>();
  graph = new AdjacencyMapGraph<Station,Integer>(true);
  count = 0;
  readMetro(fileName);
 }

 private void createStation(int stationNumber, String stationName){
  station = new Station(stationNumber,stationName);
  b.add(stationNumber,station);
 }

 private void createRoute(int stationStart, int stationStop, int secs){
  int i = 0;
  depart = b.get(stationStart);
  end = b.get(stationStop);
  transit = new Route(depart,end,secs);
  a.add(i,transit);
  i++;
 }

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

 protected Vertex<Station> getVertex(Station s) throws Exception{
  for(Vertex<Station> vs : graph.vertices()){
   if(s.equals(vs.getElement())){
    return vs;
   }
  }
  throw new Exception("Vertex not in graph");
 }

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

 public void uniformCostSearch(int start, int stop) throws Exception{

  Stack<Vertex> sk = new LinkedStack<Vertex>();
  Vertex<Station> source = getVertex(b.get(start));
  sk.push(source);

  GraphAlgorithms dj = new GraphAlgorithms();
  Map<Vertex<Station>,Integer> result = dj.shortestPathLengths(graph,source);

  Iterable<Edge<Integer>> e = graph.outgoingEdges(source);
  for(Edge<Integer> v : e){
   if((e.getElement() == 90) && (b.get(start) != b.get(stop)){
    System.out.println(v.getElement());
    sk.push(graph.opposite(source, v));
   }
  }
  uniformCostSearch(sk,getVertex(b.get(stop)));
 }

 private void uniformCostSearch(Stack k,Vertex<Station> b){

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

 public void readMetro(String fileName) throws Exception, IOException{
  BufferedReader metro = new BufferedReader(new FileReader(fileName));
  metro.readLine(); // this read the first read line
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
       placeTransfer(getNumber(start), getNumber(stop), getNumber(time));
       buildGraph(b.get(getNumber(start)),b.get(getNumber(stop)));
      }else{
       createRoute(getNumber(start), getNumber(stop), getNumber(time));
       Station s = b.get(getNumber(start));
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
    metro.uniformCostSearch(7,19);
   }catch(Exception e){
    System.err.println("Not working");
   }
 }
}
