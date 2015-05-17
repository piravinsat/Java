/**
 * Philosopoher's runnable class where the phil. repeatedly sits down, pick up
 * forks, put them down and then leaves.
 * 
 * @author 6651
 * 
 */
public class Philosopher implements Runnable {

    private Fork leftFork;
    private Fork rightFork;
    private int phil;
    private boolean isRunning = false;
    private int eating = 1;
    private int leaves = 10;

    public Philosopher(Fork left, Fork right, int philNum) {

        leftFork = left;
        rightFork = right;
        phil = philNum;
        isRunning = true;
    }

    public void run() {

        try {
            while (isRunning) {
                System.out.println("Philosopher " + phil + " has sat down");
                leftFork.pickUp(phil);
                rightFork.pickUp(phil);
                System.out.println("Philosopher " + phil + " is eating");
                Thread.sleep(eating);
                leftFork.putDown(phil);
                rightFork.putDown(phil);
                System.out.println("Philosopher " + phil + " is leaving");
                Thread.sleep(leaves);
            }
        } catch (InterruptedException exception) {
        }
    }
}
