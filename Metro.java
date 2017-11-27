import java.io.*;
import java.util.*;
import net.datastructures.*;

public class Metro{

 private Map<String,Station> map = new HashMap<String,Station>();
 // File which reads the metro.txt file
 private Graph<String,Integer> graph;

 private ArrayList<Travel> print (String fileName) throws Exception, IOException{
  // prints all info on file i.e. station names, start, stop, time
  ArrayList<Travel> a = new ArrayList<Travel>();
  ArrayList<Station> b = new ArrayList<Station>();
  //ArrayList<Transfer> c = new ArrayList<Transfer>();

  Station station, depart, end;
  Transfer transfer;
  Travel travel;
  StringTokenizer token;
  String line;
  String number, name, start, stop, time, change, numberOfStations, numberOfTransits;
  int count = 0;

  BufferedReader metro = new BufferedReader(new FileReader(fileName));
  metro.readLine(); // this read the first read line
  while((line = metro.readLine()) != null){
   // starts from second read line

   if(line.contains("$"))
    count++;

   if(!line.contains("$")){
    token = new StringTokenizer(line);
    if(count == 0){
     number = token.nextToken();
     name = token.nextToken();
     station = new Station(getNumber(number),name);
     //System.out.println(station.toString());
     b.add(getNumber(number),station);
    }
    if(count == 1){
     start = token.nextToken();
     depart = b.get(getNumber(start));
     stop = token.nextToken();
     end = b.get(getNumber(stop));
     time = token.nextToken();
     travel = new Travel(depart,end,getNumber(time));
     a.add(travel);
     //System.out.println(travel.toString());
    }
    if(count == 2){
     start = token.nextToken();
     depart = b.get(getNumber(start));
     stop = token.nextToken();
     end = b.get(getNumber(stop));
     change = token.nextToken();
     placeTransfer(depart,end);
     transfer = new Transfer(depart, end, getNumber(change));
     //System.out.println(transfer.toString());
    }
   }
  }
  return a;
 }

  public Metro(String fileName){
   graph = new AdjacencyMapGraph<String,Integer>();
   print(fileName);
   readMetro(fileName);
  }

 public void readMetro(String fileName) throws Exception, IOException{
  BufferedReader graph = new BufferedReader(new FileReader());

  String line;
  int count = 0;
  StringTokenizer token;
  while((line = graph.readLine()) != null){
    if(line.contains("$"))
     count++;
    if(!line.contains("$"){

    }
   }
  }

 }

 }

 private int getNumber(String s){
  // private method which takes in a string and returns a number
  return Integer.parseInt(s);
 }

 private void placeTransfer(Station a, Station b){
  // private method which takes in two stations and places the transfer mark on them
  if(!a.hasTransfer()){
   a.setTransfer(-1);
  }
  if(!b.hasTransfer()){
   b.setTransfer(-1);
  }
  a.addTransfer(b);
 }

 public static void main(String[] args) throws IOException, Exception {
  Metro metro = new Metro();
  ArrayList<Travel> f = metro.read("metro.txt");
  for (int i=0; i< f.size(); i++) {
   //System.out.println(f.get(i).toString());
  }
 }

}
