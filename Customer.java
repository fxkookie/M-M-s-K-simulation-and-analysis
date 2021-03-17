public class Customer{
  double arrival_time;
  double service_time;
  double waiting_time;
  double depart_time;
  double dwell_time;
  double in_queue;
  double in_service;


  public Customer(double ar, double ser){
    arrival_time = ar;
    service_time = ser;
    in_queue = arrival_time;
  }

  // into station
  public void inS(double time, int index){
    in_service = time;
    if(index == 0){
      waiting_time = in_service - in_queue;
    }
    else{
      waiting_time = 0;
    }
    dwell_time = waiting_time + service_time;
    depart_time = in_service + service_time;
  }

  public double unfinished_waiting_time(double time){
    waiting_time = time - in_queue;
    return waiting_time;
  }

}
