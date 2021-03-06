import java.util.ArrayDeque;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import java.util.Random;

public class Station{
  public double lamda;
  public double mu;
  public double total_time;
  public double system_time;
  // public double rate;
  public int sta_num;
  public int customer_num;
  public int capacity;
  public int finish;
  public int drop;
  public double wq;
  public double ws;
  public double lq;
  public double ls;
  public double in_sta_num;
  public double before_time;
  public double before_time_all;

  public double avg;
  public double ttt;
  public int zero_count;
  public int zero_count2;

  public ArrayDeque<Customer> queue;
  public Customer cur_customer[];

  public ArrayDeque arrival_queue;
  public ArrayDeque service_queue;

  public ExponentialDistribution elamda;
  public ExponentialDistribution emu;
  public UniformIntegerDistribution ulamda;
  public UniformIntegerDistribution umu;

  public boolean serverBusy;


  public Station(double lamda, double mu, int s, int k, int n, int m){
    wq = 0;
    before_time = 0;
    before_time_all = 0;
    in_sta_num = 0;
    zero_count = 0;
    zero_count2 = 0;
    drop = 0;
    system_time = n * 60 * (1/lamda);
    this.lamda = lamda;
    this.mu = mu;
    sta_num = s;
    capacity = k-s;
    if(m == 0){
      queue = new ArrayDeque(capacity);
      cur_customer = new Customer[s];
    }
    else{
      capacity = 10000000;
      queue = new ArrayDeque();
      cur_customer = new Customer[s];
    }

    arrival_queue  = new ArrayDeque();
    service_queue  = new ArrayDeque();
    // ExponentialDistribution
    //m distribution
    elamda = new ExponentialDistribution(1/lamda);
    //g distribution
    // ulamda = new UniformIntegerDistribution(0,(int)(120/lamda));

    if(m == 0){
      emu = new ExponentialDistribution(1/mu);
    }
    // UniformIntegerDistribution
    else{
      // ulamda = new UniformIntegerDistribution(0,(int)(120/lamda));
      umu = new UniformIntegerDistribution(0, (int)(120/mu));
    }
    double arrival_last = 0;
    double new_service = 0;
    Customer born;
    avg = 0;
    customer_num = 0;
    double p = 0;

    for(;;){
      if(arrival_last >= system_time){
        break;
      }
      customer_num++;
      // double a = elamda.sample() * 60;
      if(m == 0){
        // p = ulamda.sample();
        p = elamda.sample() * 60;
        // p = exp(lamda) * 60;
        avg += p;
        arrival_last += p;
        new_service = emu.sample() * 60;
        ttt += new_service;
      }
      else{
        p = elamda.sample() * 60;
        // p = ulamda.sample();
        avg += p;
        arrival_last += p;
        new_service = umu.sample();
      }
      // avg += new_service;
      arrival_queue.add(arrival_last);
      service_queue.add(new_service);
    }
  }
  //when the customer comes, it will call this func to put in the waiting queue.
  //Return False if the queue is full, otherwise return true.
  public void add(double arr, double ser, double time){
    Customer t = new Customer(arr,ser);
    for(int i = 0; i < sta_num; i++){
      if(cur_customer[i] == null){
        update_ls(time);
        in_sta_num++;
        t.inS(time,1);
        // zero_count2++;
        cur_customer[i] = t;
        serverBusy = true;
        return;
      }
    }
    if(queue.size() < capacity){
      update_ls(time);
      lq += (time - before_time) * queue.size();
      before_time = time;
      queue.add(t);
      return;
    }
    drop++;
  }

  public void run(){
    double cur = 0;
    double min = 0;
    serverBusy = false;
    boolean arr_emp = false;
    do{
      double next_arrival = 0;
      arr_emp = arrival_queue.isEmpty();
      if (!arr_emp){
          next_arrival = (double)arrival_queue.peek();
          min = next_arrival;
      }
      double now_customertime;
      if(serverBusy){
        for(int i = 0;i<sta_num;i++){
          if(cur_customer[i] != null){
            now_customertime = cur_customer[i].depart_time;
            if(arr_emp){
              min = now_customertime;
            }
            else if(now_customertime <= min){
              min = now_customertime;
            }
          }
        }
      }
      for(int i = 0;i<sta_num;i++){
        if(cur_customer[i] != null){
          now_customertime = cur_customer[i].depart_time;
          // the packet in the station is the next target
          if(now_customertime == min){
            finish++;
            wq += cur_customer[i].waiting_time;
            ws += cur_customer[i].dwell_time;
            update_ls(now_customertime);
            if(!queue.isEmpty()){
              lq += (now_customertime - before_time) * queue.size();
              before_time = now_customertime;
              before_time_all = now_customertime;
              cur_customer[i] = (Customer)queue.poll();
              cur_customer[i].inS(min,0);
            }
            else{
              cur_customer[i] = null;
              in_sta_num--;
            }
          }
        }
      }
      if (!arr_emp){
        //the coming packet is the next target
        if(next_arrival == min){
          add((double)arrival_queue.poll(), (double)service_queue.poll(), min);
        }
      }
      cur = min;
      serverBusy = false;
      for(int i = 0; i < sta_num;i++){
        if(cur_customer[i] != null){
          serverBusy = true;
        }
      }
      system_time = cur;
    }while( !arrival_queue.isEmpty() || !queue.isEmpty() ||  serverBusy);


  }
  public void update_ls(double time){
    ls += (time - before_time_all) * (queue.size() + in_sta_num);
    before_time_all = time;
  }
  public double exp(double lamda){
    double z = Math.random();
    double x = -(1 / lamda) * Math.log(z);
    return x;
  }

}
