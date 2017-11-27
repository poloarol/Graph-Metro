public class Transfer{
 Station start, stop;
 int transfer;

 public Transfer(Station departure, Station arrival, int transfer){
  start = departure;
  stop = arrival;
  this. transfer = transfer;
  start.setTransfer(transfer);
  stop.setTransfer(transfer);
 }

 public Station getTransfer(Station station){
  // gives the transfer of the station
  return null;
 }

 public String toString(){
  StringBuffer s = new StringBuffer();
  s.append("You'll need a transfer from " + start.toString() + " to " + stop.toString());
  return s.toString();
 }
}
