
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import java.util.Random;
import java.security.SecureRandom;




public class Main{


  public static void print_Info(Analysis a, Station s, double lamda, double mu, int x, int y){
    double s_wq = s.wq/ (s.customer_num - s.drop);
    double s_ws = s.ws/ (s.customer_num - s.drop);
    double s_lq = s.lq/s.system_time;
    double s_ls = s.ls/s.system_time;
    double s_drop = (double)s.drop / (double)s.customer_num;

    double a_wq = a.cal_wq() * 60;
    double a_ws = a.cal_ws() * 60;
    double a_lq = a.cal_lq();
    double a_ls = a.cal_ls();
    System.out.println("======================================");

    System.out.printf("drop_s: %.4f\n", s_drop );
    System.out.printf("drop_a: %.4f\n", (lamda - a.lamdaeff)  / lamda);

    System.out.printf("lamda: %.2f and mu: %.2f, s:%d, K:%d\n", lamda, mu, x, y);
    System.out.printf("a_wq: %.4f, s_wq: %.4f\n", a_wq, s_wq);
    System.out.printf("a_ws: %.4f, s_ws: %.4f\n", a_ws, s_ws);
    System.out.printf("a_lq: %.4f, s_lq: %.4f\n", a_lq, s_lq);
    System.out.printf("a_ls: %.4f, s_ls: %.4f\n", a_ls, s_ls);

  }
  public static void print_InfoG(Analysis a, Station s, double lamda, double mu){
    double s_wq = s.wq / (s.customer_num - s.drop);
    double s_ws = s.ws / (s.customer_num - s.drop);
    double s_lq = s.lq/s.system_time;
    double s_ls = s.ls/s.system_time;
    double s_drop = (double)s.drop / (double)s.customer_num;

    double a_wq = a.wq * 60;
    double a_ws = a.ws * 60;
    double a_lq = a.lq;
    double a_ls = a.ls;
    System.out.printf("avg: %.5f\n", ( (double)s.drop / (double)s.customer_num));
    System.out.println("======================================");
    System.out.printf("lamda: %.2f and mu: %.2f\n", lamda, mu);
    System.out.printf("a_wq: %.4f, s_wq: %.4f\n", a_wq, s_wq);
    System.out.printf("a_ws: %.4f, s_ws: %.4f\n", a_ws, s_ws);
    System.out.printf("a_lq: %.4f, s_lq: %.4f\n", a_lq, s_lq);
    System.out.printf("a_ls: %.4f, s_ls: %.4f\n", a_ls, s_ls);
  }


  public static void main(String[] args) {

    double count = 0;
    // Station simulation = new Station(2, 1.5, 2, 5, 1000000, 0);
    // Analysis analysis = new Analysis(2,1.5,2,5);
    Station simulation;
    Analysis analysis;
    System.out.printf("M/M/s/K, lamda = 6, mu = 2, K = 8\n");

    for(int j = 3; j<=8; j += 1){
      // for(double i = 0.5;i<=6; i += 0.5){
        simulation = new Station(6, 2, j, 8, 100000000, 0);
        analysis = new Analysis(6, 2, j, 8);
        // analysis = new Analysis(2, 6);
        simulation.run();
        print_Info(analysis, simulation, 6, 2, j, 8);
        // print_InfoG(analysis, simulation, 2, j);

      // }
    }

    /************************************************************
    simulation = new Station(1.5, 6, 1, 0, 1000000, 1);
    analysis = new Analysis(1.5, 6);
    simulation.run();
    // System.out.printf("%.2f\n",simulation.system_time);
    print_InfoG(analysis, simulation, 1.5, 6);
    *************************************************************/

    // for(double j = 0.5; j < 2; j += 0.5){
    //   for(double i = 2.5;i <= 6; i += 0.5){
    //     simulation = new Station(2, i, 1, 0, 10000000, 1);
    //     analysis = new Analysis(2, i);
    //     simulation.run();
    //     print_InfoG(analysis, simulation, 2, i);
    //   }
    // }

  }
}
