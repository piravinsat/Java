
public class montecarlo {
    
    int x1 = 100; //Strike Price 100p
    int x2 = 102; //Strike Price 2: 51 x 2 = 102p
    double t = 0.5; //Time to maturity: Six months (1/2 year)
    int s = 80; //Current stock price: 80p
    double vol = 0.03; //Daily volatility: 3%
    double sixMonthVol = vol*Math.pow(126, 2);
    int tradingDays = 252; //Trading days in a year
    int tradingDaysSixMonths = tradingDays/2;
    double r = 0.07; //Annual interest rate with continuous compounding
    double dailyR = (r/tradingDays);

    /**
     * @param args
     */
    public static void main(String[] args) {
       new montecarlo();

    }
    
    public montecarlo() {
        
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
        
        double sZero = s;
        int sampleSize = 22000;
        int j = 0;
        double [] samples = new double[sampleSize];
        double x = strikePrice;
        double bigT = timeToMaturity;
        double callPrice = 0.00;  
        double [] stockPrices = new double[tradingDaysSixMonths+1];
        stockPrices[0] = sZero;
      
        //price = e^-rT (Expectation) payoff(ST)
        
        //Algorithm for Monte Carlo Simulation
        
        //Simulate a random path for S in the risk-netural world
        
        //delta T = 1
        //deltaS = rSdt + varSdz
        
        //Repeat steps 1 and 2 to get many sample values of the payoff
        while (j < sampleSize) {
        for (int i = 0; i < tradingDaysSixMonths; i++) {

        //Random generator a number either +1 or -1 with probability of 0.5
        double epsilon = 0.00;
        double rgn = Math.random();

        if (rgn < 0.5) {
           epsilon = -1.00;
        }
        else if (rgn >= 0.5) {
           epsilon = 1.00;
        }
        
        double deltaS = (dailyR * stockPrices[i]) + (vol * stockPrices[i] * epsilon);
        
        stockPrices[i+1] = stockPrices[i] + deltaS;
        //System.out.println(stockPrices[i+1]);
        
        }

        
        //Calculate the payoff from the derivative
        
        //European call: (St - X)+        
        double payoff = (stockPrices[tradingDaysSixMonths] - x);
        payoff = Math.max(payoff, 0);
        samples[j] = payoff;
        j++;
        }    
        
        
        //4. Calculate the mean to get an estimate of expected payoff
        double sampleSum = 0.00;
        
        for(int i=0; i< sampleSize; i++) {
            sampleSum = sampleSum + samples[i];
        }
        
        double expectation = sampleSum/sampleSize;
        
        //5. Calculate the PV of the expected payoff (at the risk-free rate)
        
        //price = e^-rT (Expectation) payoff(ST)
        callPrice = Math.exp(-1*dailyR*bigT)*expectation;
        
        
        return callPrice;
    }
    
    public double put(double strikePrice, double timeToMaturity) {
        
        double sZero = s;
        int sampleSize = 22000;
        int j = 0;
        double [] samples = new double[sampleSize];
        double x = strikePrice;
        double bigT = timeToMaturity;
        double putPrice = 0.00;  
        double [] stockPrices = new double[tradingDaysSixMonths+1];
        stockPrices[0] = sZero;
        
        //Algorithm for Monte Carlo Simulation
        
        //Simulate a random path for S in the risk-netural world
        
        //delta T = 1
        //deltaS = rSdt + varSdz
        
        //Repeat steps 1 and 2 to get many sample values of the payoff
        while (j < sampleSize) {
        for (int i = 0; i < tradingDaysSixMonths; i++) {

        //Random generator a number either +1 or -1 with probability of 0.5
        double epsilon = 0.00;
        double rgn = Math.random();
        
        if (rgn < 0.5) {
           epsilon = -1.00;
        }
        else if (rgn >= 0.5) {
           epsilon = 1.00;
        }
        
        double deltaS = (dailyR * stockPrices[i]) + (vol * stockPrices[i] * epsilon);
        
        stockPrices[i+1] = stockPrices[i] + deltaS;
        
        }

        
        //Calculate the payoff from the derivative
        
        //European put: (X - St)+       
        double payoff = (x - stockPrices[tradingDaysSixMonths]);
        payoff = Math.max(payoff, 0);
        samples[j] = payoff;
        j++;
        }    
        
        
        //4. Calculate the mean to get an estimate of expected payoff
        double sampleSum = 0.00;
        
        for(int i=0; i< sampleSize; i++) {
            sampleSum = sampleSum + samples[i];
        }
        
        double expectation = sampleSum/sampleSize;
        
        //5. Calculate the PV of the expected payoff (at the risk-free rate)
        
        //price = e^-rT (Expectation) payoff(ST)
        putPrice = Math.exp(-1*dailyR*bigT)*expectation;
        
        
        return putPrice;
    }

}
