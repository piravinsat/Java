/**
 * 
 */

/**
 * @author Piravin
 *
 */
public class node {
    
    private double stockPrice;
    private double optionPrice;
    
    public node(double sZero, double d) {
        stockPrice = sZero;
        optionPrice = d;
    }  
    
    public double getStockPrice() {
        return stockPrice;    
    }
    
    public double getOptionPrice() {
        return optionPrice;
    }
    
    public void setStockPrice(double s) {
        stockPrice = s;
    }
    
    public void setOptionPrice(double o) {
        optionPrice = o;
    }
    
    
}