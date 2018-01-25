import net.datastructures.*;
import java.util.Hashtable;

public class Station{
 private int station, transfer, counts, countr;
 private String name;
 private ArrayList<Station> transferList;
 private Hashtable<String, Route> routes;
 private boolean visit;

 public Station(int number, String name){
  station = number;
  this.name = name;
  transfer = counts = countr = 0;
  transferList = new ArrayList<Station>();
  routes = new Hashtable<String, Route>();
  visit = false;
 }

 public int getStationNumber(){
  return station;
 }

 public String getStationName(){
  return this.name;
 }

 public String toString(){
  StringBuffer s = new StringBuffer();
  s.append("Station Number: " + getStationNumber() + " Station Name: " + getStationName());
  return s.toString();
 }

 public void setTransfer(int change){
  transfer = change;
 }

 public int getTransfer(){
  return transfer;
 }

 public void addTransfer(Station station){
  transferList.add(counts, station);
  counts++;
 }

 public boolean equals(Station station){
   return this.getStationNumber() == station.getStationNumber();
 }

 public boolean hasTransfer(){
  return getTransfer() == -1;
 }

 public Route getRouteTo(String destination){
  if(routes.containsKey(destination)){
   return routes.get(destination);
  }/*else{
   //throw new NoSuchElementException("Missing Connection: " +  " to " + "destination");
  }*/
  return null;
 }

 public ArrayList<Station> getDestination(){
  ArrayList<Station> destination = new ArrayList<Station>();
  for(Route routes : routes.values()){
   destination.add(countr, routes.getDestination());
  }
  countr++;
  return destination;
 }

 public void addRoute(Route route){
  routes.put(route.getDestination().name, route);
 }

 public void setVisited(boolean t){
  visit = t;
 }

 public boolean isVisited(){
  return visit;
 }
}
