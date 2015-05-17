/**
 * The main class Museum which runs the threads of the Door object
 * 
 * @author 6651
 */
public class Museum {

    public static void main(String[] args) {

        Tally staffClicker = new Tally();

        Door d1 = new Door(1, staffClicker); // Entry Only
        Door d2 = new Door(2, staffClicker); // Exit Only
        Door d3 = new Door(3, staffClicker); // Entry and Exit

        Thread t1 = new Thread(d1); // Door 1
        Thread t2 = new Thread(d2); // Door 2
        Thread t3 = new Thread(d3); // Door 3

        t1.start();
        t2.start();
        t3.start();

    }

}
