public class Route{
 Station start, stop;
 int time;
 StringBuffer a, b, c;

 public Route(Station departure, Station arrival, int time){
  start = departure;
  stop = arrival;
  this.time = time;
 }

 private String getStart(){
  a = new StringBuffer();
  a.append(start.getStationName());
  return a.toString();
 }

 private String getStop(){
  b = new StringBuffer();
  b.append(stop.getStationName());
  return b.toString();
 }

 public Station getSource(){
  return start;
 }

 public Station getDestination(){
  return stop;
 }

 public int getTime(){
  return this.time;
 }

 public String toString(){
  c = new StringBuffer();
  c.append("Your transit begins at " + getStart() + " : Ends at " + getStop() + " : Takes " + this.time);
  return c.toString();
 }

 @Override
 public int hashCode(){
  final int prime = 31;
  int result = 1;
  result = prime * ((stop == null) ? 0 : stop.hashCode());
  result = prime * result + this.time;
  result = prime * result + ((start == null) ? 0 : start.hashCode());
  return result;
 }

 public boolean equals(Route s){
  if(this == s){
   return true;
  }
  if(s == null){
   return false;
  }
  if(stop == null){
   if(s.stop != null){
    return false;
   }else if(!stop.equals(s.stop)){
    return false;
   }
   if(this.time != s.time){
    return false;
   }
   if(start == null){
    if(s.start != null){
     return false;
    }else if(!start.equals(s.start)){
     return false;
    }
   }
  }
  return true;
 }
}
