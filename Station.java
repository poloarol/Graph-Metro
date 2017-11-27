import java.util.*;

public class Station{
 private int station, transfer;
 private String name;
 private ArrayList<Station> transferList;
 private Map<String, Route> routes;

 public Station(int number, String name){
  station = number;
  this.name = name;
  transfer = 0;
  transferList = new ArrayList<Station>();
  routes = new HashMap<String, Route>();
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
  transferList.add(station);
 }

 public boolean equals(Station station){
  return station.getStationName().equals(this.getStationName());
 }

 public boolean hasTransfer(){
  return getTransfer() == -1;
 }

 public Route getRouteTo(String destination){
  if(routes.containsKey(destination)){
   return routes.get(destination);
  }else{
   throw new NoSuchElementException("Missing Connection: " +  " to " + "destination");
  }
 }

 public ArrayList<Station> getDestination(){
  ArrayList<Station> destination = new ArrayList<Station>();
  for(Route routes : routes.values()){
   destination.add(routes.getDestination());
  }
  return destination;
 }

 public void addRoute(Route route){
  routes.put(route.getDestination().name, route);
 }

 @Override
 public int hashCode(){
  final int prime = 31;
  int result = 1;
  result = prime * result + ((name == null) ? 0 : name.hashCode());
  return result;
 }
}
