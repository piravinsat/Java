
public class trees {
    
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
        new trees();
    }
    
    public trees() {
        
        double euCallX1 = 0.00;
        double usCallX1 = 0.00;
        double euCallX2 = 0.00;
        double usCallX2 = 0.00;
        double euPutX1 = 0.00;
        double usPutX1 = 0.00;
        double euPutX2 = 0.00;
        double usPutX2 = 0.00;
        
        euCallX1 = call(x1, false);
        euCallX2 = call(x2, false);
        euPutX1 = put(x1, false);
        euPutX2 = put(x2, false);
        
        usCallX1 = call(x1, true);
        usCallX2 = call(x2, true);
        usPutX1 = put(x1, true);
        usPutX2 = put(x2, true);
        
        System.out.println("European options");
        System.out.println(euCallX1);
        System.out.println(euPutX1);
        System.out.println(euCallX2);
        System.out.println(euPutX2);
        
        System.out.println("American options");
        System.out.println(usCallX1);
        System.out.println(usPutX1);
        System.out.println(usCallX2);
        System.out.println(usPutX2);
    }
    
    public double call(double strikePrice, boolean americanOption) {
        
        double sZero = s;
        double x = strikePrice;
        double callPrice = 0.00;
        int deltaT = 1;
        
        double u = Math.exp(vol*Math.sqrt(deltaT));
        double d = Math.exp(-1*vol*Math.sqrt(deltaT));
        
        double eRdeltaT = Math.exp(dailyR*deltaT);
        
        double p = (eRdeltaT - d)/(u - d);
        
        //CREATE THE BINOMIAL PRICE TREE
        node [][] Prices = new node [127][127];
        Prices[0][0] = new node(sZero, 0.00);
        
        int nodesAtRow = 2;
        for(int row= 1; row< 126; row++)
        {
            //int rowone = row-1;
            //System.out.println("row-1 " + rowone);

         for (int col = 0; col< nodesAtRow ; col++) {
             
            // System.out.println("col " + col);

                 
         boolean exists = false;      
         
         for(int i = 0; i < 127; i++) {
             for(int j = 0; j < 127; j++) {
  
         //Adding the upper stock price
         if(Prices[i][j].getStockPrice() == Prices[col][row-1].getStockPrice()*u) {
         Prices[col][row] = new node(Prices[col][row-1].getStockPrice()*u, 0.00);
         }
         }
             
         }
         
         exists = false;
         
         for(int k = 0; k < 127; k++) {
             for(int l = 0; l < 127; l++) {
                 
              if(Prices[k][l].getStockPrice() == Prices[col][row-1].getStockPrice()*u) {
                  
        //Adding the lower stock price
        Prices[col+1][row] = new node(Prices[col][row-1].getStockPrice()*d, 0.00);
             }
             }
         }
         
         nodesAtRow = nodesAtRow*2;
        }
        
        //FIND THE OPTION VALUES FOR EVERY FINAL NODE
            //Row: 126
            for (int col = 0; col < 126; col++ ) {
                
                //Get the stock price at the node
                double stockPriceAtNode = Prices[col][126].getStockPrice();
                
                //Calculate the option price (fu and fd)
                double optionPriceAtNode = Math.max(stockPriceAtNode-x, 0.00);
                
                //Set the option price to node
                Prices[col][126].setOptionPrice(optionPriceAtNode);
                
            }
 
        //FIND THE OPTION FOR EARLIER NODES
            //Row 125 -> 0
            nodesAtRow = 126;
            for (int row = 126; row < 0; row--) {
            for (int col = 0; col < nodesAtRow; col++ ) {
                
                //Get the option price of upper child node
                double fu = Prices[col][row].getOptionPrice();
                
                //Get the option price of lower child node
                double fd = Prices[col+1][row].getOptionPrice(); 
                
                //Calculate the option price (f)
                double optionPriceAtNode = eRdeltaT * (p*fu + (1-p)*fd);
                
                //Set the option price to parent node
                Prices[col][row-1].setOptionPrice(optionPriceAtNode);
                
            } 
                nodesAtRow = nodesAtRow/2;
            }
        
        //If an European option this value below is returned   
        callPrice = Prices[0][0].getOptionPrice();
        
        //If an American option then
        if(americanOption) {

            double highestOptionValue = 0.00;
            double optionPriceAtNode = 0.00;
            
            //FIND THE NODE WITH THE HIGHEST OPTION 
            nodesAtRow = 126;
            
            for (int row = 126; row < 0; row--) {
            for (int col = 0; col < nodesAtRow; col++ ) {
                
                optionPriceAtNode = Prices[row][col].getOptionPrice();
                
                if (highestOptionValue < optionPriceAtNode) {
                    highestOptionValue = optionPriceAtNode;
                }
                
            }   
                nodesAtRow = nodesAtRow/2;
            }
            
            callPrice = highestOptionValue;
            
        }
        
        return callPrice;
        
    }
    
    public double put(double strikePrice, boolean americanOption) {
        
        double sZero = s;
        double x = strikePrice;
        double putPrice = 0.00;
        int deltaT = 1;
        
        double u = Math.exp(vol*Math.sqrt(deltaT));
        double d = Math.exp(-1*vol*Math.sqrt(deltaT));
        
        double eRdeltaT = Math.exp(dailyR*deltaT);
        
        double p = (eRdeltaT - d)/(u - d);
        
        //CREATE THE BINOMIAL PRICE TREE
        node [][] Prices = new node [127][127];
        Prices[0][0] = new node(sZero, 0.00);
        
        int nodesAtRow = 2;
        for(int row= 1; row< 126; row++)
        {

         for (int col = 0; col< nodesAtRow ; col++) {
        //Adding the upper stock price
        Prices[col][row] = new node(Prices[0][0].getStockPrice()*u, 0.00);
        //Adding the lower stock price
        Prices[col+1][row] = new node(Prices[0][0].getStockPrice()*d, 0.00);
         }
         
         nodesAtRow = nodesAtRow*2;
        }
        
        //FIND THE OPTION VALUES FOR EVERY FINAL NODE
            //Row: 126
            for (int col = 0; col < 126; col++ ) {
                
                //Get the stock price at the node
                double stockPriceAtNode = Prices[col][126].getStockPrice();
                
                //Calculate the option price (fu and fd)
                double optionPriceAtNode = Math.max(stockPriceAtNode-x, 0.00);
                
                //Set the option price to node
                Prices[col][126].setOptionPrice(optionPriceAtNode);
                
            }
 
        //FIND THE OPTION FOR EARLIER NODES
            //Row 125 -> 0
            nodesAtRow = 126;
            for (int row = 126; row < 0; row--) {
            for (int col = 0; col < nodesAtRow; col++ ) {
                
                //Get the option price of upper child node
                double fu = Prices[col][row].getOptionPrice();
                
                //Get the option price of lower child node
                double fd = Prices[col+1][row].getOptionPrice(); 
                
                //Calculate the option price (f)
                double optionPriceAtNode = eRdeltaT * (p*fu + (1-p)*fd);
                
                //Set the option price to parent node
                Prices[col][row-1].setOptionPrice(optionPriceAtNode);
                
            } 
                nodesAtRow = nodesAtRow/2;
            }
        
        //If an European option this value below is returned   
        putPrice = Prices[0][0].getOptionPrice();
        
        //If an American option then
        if(americanOption) {

            double highestOptionValue = 0.00;
            double optionPriceAtNode = 0.00;
            
            //FIND THE NODE WITH THE HIGHEST OPTION 
            nodesAtRow = 126;
            
            for (int row = 126; row < 0; row--) {
            for (int col = 0; col < nodesAtRow; col++ ) {
                
                optionPriceAtNode = Prices[row][col].getOptionPrice();
                
                if (highestOptionValue < optionPriceAtNode) {
                    highestOptionValue = optionPriceAtNode;
                }
                
            }   
                nodesAtRow = nodesAtRow/2;
            }
            
            putPrice = highestOptionValue;
            
        }
        
        return putPrice;
    }

}
