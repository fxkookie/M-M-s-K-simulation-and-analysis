
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import java.util.Random;
import java.security.SecureRandom;




public class Main{


  public static void print_Info(Analysis a, Station s, double lamda, double mu){
    double s_wq = s.wq/ (s.customer_num - s.drop);
    double s_ws =  s.ws/ (s.customer_num - s.drop);
    double s_lq = s.lq/s.system_time;
    double s_ls = s.ls/s.system_time;
    double s_drop = (double)s.drop / (double)s.customer_num;

    double a_wq = a.cal_wq() * 60;
    double a_ws = a .cal_ws() * 60;
    double a_lq = a.cal_lq();
    double a_ls = a.cal_ls();
    // System.out.printf("avg: %.5f\n", ( (double)s.drop / (double)s.customer_num));
    System.out.println("======================================");
    System.out.printf("lamda: %.2f and mu: %.2f\n", lamda, mu);
    System.out.printf("a_wq: %.4f, w_wq: %.4f\n", a_wq, s_wq);
    System.out.printf("a_ws: %.4f, w_ws: %.4f\n", a_ws, s_ws);
    System.out.printf("a_lq: %.4f, w_lq: %.4f\n", a_lq, s_lq);
    System.out.printf("a_ls: %.4f, w_ls: %.4f\n", a_ls, s_ls);
    System.out.println("======================================");

  }
  public static void main(String[] args) {

    double count = 0;
    // Station s = new Station(2, 1.5, 2, 5, 1000000, 0);
    // Analysis analysis = new Analysis(2,1.5,2,5);
    Station simulation;
    Analysis analysis;
    for(double j = 0.5; j<3.5; j += 1){
      for(double i = 0.5;i<3.5; i += 1){
        simulation = new Station(j, i, 2, 5, 100000, 0);
        analysis = new Analysis(j, i, 2, 5);
        simulation.run();
        print_Info(analysis, simulation, j, i);
      }
    }

  }
}
