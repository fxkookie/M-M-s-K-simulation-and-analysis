import java.lang.Math;

public class Analysis{
  double lamda;
  double mu;
  double lo;
  int s;
  int k;
  int m; // 0 = mmsk, 1 = mg1
  double lq;
  double ls;
  double wq;
  double ws;

  double p0;
  double pn;
  double sigma;
  double lamdaeff;
  // M/M/s/K
  public Analysis(double lamda, double mu, int s, int k){
    this.lamda = lamda;
    this.mu = mu;
    this.s = s;
    this.m = 0;
    this.k = k;
    this.lo = lamda/mu;
    this.p0 = cal_p0();
    this.lamdaeff = lamda * (1-cal_pn(k));
    this.lq = cal_lq();
    this.ls = cal_ls();
  }
  // M/M/1
  public Analysis(double lamda, double mu){
    this.lamda = lamda;
    this.mu = mu;
    this.m = 1;
    this.s = 1;
    this.lo = lamda/mu;
    this.sigma = (2/mu) * (2/mu) / 12;
    // System.out.printf("%.2f\n",sigma);
    // this.p0 = 1-lo;
    this.lq = cal_lq();
    this.ls = cal_ls();
    this.wq = cal_wq();
    this.ws = cal_ws();
  }
  public double cal_p0(){
    double sum = 1;
    for(int i = 1;i<s;i++){
      sum += ( Math.pow(lo,i) / factorial(i) );
    }
    double temp = Math.pow(lo,s) / factorial(s);
    double temp2 = 0;
    for(int j = s;j < k+1 ;j++){
      temp2 += Math.pow( lamda / (s * mu) ,(j-s));
    }
    sum += temp * temp2;
    return 1/sum;
  }

  public double cal_pn(int n){
    if(m == 0){
      if(n < s){
        return (Math.pow(lo,n)/factorial(n)) * p0;
      }
      else if(n <= k){
        return (Math.pow(lo,n)/ (factorial(s) * Math.pow(s,n-s)) ) * p0;
      }
      else{
        return 0;
      }
    }
    else{
      return pn = p0 * Math.pow(lo,n);
    }

  }

  public double cal_lq(){
    if(m == 0){
      double res = 0;
      for(int i = s;i<k+1;i++){
        res += cal_pn(i) * (i-s);
      }
      return res;
    }
    else{
      double temp = 1-lo;
      double temp2 = lamda * lamda * this.sigma;
      return (temp2 + (lo * lo)) / (2 * temp);
    }
  }

  public double cal_ls(){
    if(m == 0){
      double res = lq;
      double temp = 0;
      for(int i = 0;i<s;i++){
        double pn = cal_pn(i);
        res += i * pn;
        temp += pn;
      }
      res += s * (1-temp);
      return res;
    }
    else{
      return lq + lo;
    }
  }

  public double cal_ws(){
    if(m == 0){
        return ls / lamdaeff;
    }
    else{
      return wq + (1/mu);
    }
  }

  public double cal_wq(){
    if(m == 0){
      return lq / lamdaeff;
    }
    else{
      return lq / lamda;
    }
  }

  public double factorial(double m){
    double res = 1;
		for(int i = 1;i <= m;i++){
      res *= i;
    }
    return res;
  }
}
