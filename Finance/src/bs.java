/**
 * Using the Black-Scholes formula to 
 * find the prices of the European 
 * call and put options in six months
 * @author 6651
 */
public class bs {
    
    int x1 = 100; //Strike Price 100p
    int x2 = 102; //Strike Price 2: 51 x 2 = 102p
    double t = 0.5; //Time to maturity: Six months (1/2 year)
    int s = 80; //Current stock price: 80p
    double vol = 0.03; //Daily volatility: 3%
    double annualVol = Math.sqrt(252)*vol;
    int tradingDays = 252; //Trading days in a year
    int tradingDaysSixMonths = tradingDays/2;
    double r = 0.07; //Annual interest rate with continuous compounding
    //double sixMonthR = (r/tradingDays)*tradingDaysSixMonths;

    /**
     * @param args
     */
    public static void main(String[] args) {
        new bs();
    }
    
    public bs() {
        
        double euCallX1 = 0.00;
        double euCallX2 = 0.00;
        double euPutX1 = 0.00;
        double euPutX2 = 0.00;
        
        euCallX1 = call(x1, t);
        euCallX2 = call(x2, t);
        euPutX1 = put(x1, t);
        euPutX2 = put(x2, t);
        
        System.out.println(euCallX1);
        System.out.println(euPutX1);
        System.out.println(euCallX2);
        System.out.println(euPutX2);
    }
    
    public double call(double strikePrice, double timeToMaturity) {

        double x = strikePrice;
        double bigT = timeToMaturity;
        double smallT = 0; //Current Time at t=0
        double callPrice = 0.00;    
        
        //SN(d1)
        double d1 = (Math.log(s/x) + (r + (Math.pow(annualVol, 2)/2))*(bigT - smallT))/(annualVol*Math.sqrt(bigT - smallT));
        double sNd1 = s*CNDF(d1);
        
        //Xe^r(T-t)
        double XerTt = x*Math.exp(-1*r*(bigT-smallT));
        
        //N(d2)
        double d2 = d1 - annualVol*Math.sqrt(bigT-smallT);
        double Nd2 = CNDF(d2);
        
        //SN(d1) - Xe^-r(T-t) * N(d2)
        callPrice = sNd1 - XerTt*Nd2;
        
        return callPrice;
    }
    
    public double put(double strikePrice, double timeToMaturity) {
        
        double x = strikePrice;
        double bigT = timeToMaturity;
        double smallT = 0; //Current Time at t=0
        double putPrice = 0.00;
        
        //Xe^r(T-t)
        double XerTt = x*Math.exp(-1*r*(bigT-smallT));
        
        //SN(-d1)
        double d1 = (Math.log(s/x) + (r + (Math.pow(annualVol, 2)/2))*(bigT - smallT))/(annualVol*Math.sqrt(bigT - smallT));
        double sNd1 = s*CNDF(-d1);
        
        //N(-d2)
        double d2 = d1 - annualVol*Math.sqrt(bigT-smallT);
        double Nd2 = CNDF(-d2);
        
        //X^e-r(T-t) * N(d2) - SN(-d1)
        putPrice = XerTt*Nd2 - sNd1;

        return putPrice;
    }
    
  public static double CNDF(double x)
  {
      int neg = (x < 0d) ? 1 : 0;
      if ( neg == 1) 
      x *= -1d;
      
      double k = (1d / ( 1d + 0.2316419 * x));
      double y = (((( 1.330274429 * k - 1.821255978) * k + 1.781477937) *
           k - 0.356563782) * k + 0.319381530) * k;
      y = 1.0 - 0.398942280401 * Math.exp(-0.5 * x * x) * y;
      
      return (1d - neg) * y + neg * (1d - y);
  }

}
