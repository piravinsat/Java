import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Carries information on if it has been picked up and by whom if it is.
 * 
 * @author 6651
 * 
 */
public class Fork {

    private int philosopher;
    private boolean hasPickedUp;
    private Lock forkChangeLock;
    private Condition waitForForkCondition;
    private int fork;

    /**
     * Constructor of Fork
     */
    public Fork(int forkNum) {
        fork = forkNum;
        hasPickedUp = false;
        philosopher = 0;
        forkChangeLock = new ReentrantLock();
        waitForForkCondition = forkChangeLock.newCondition();
    }

    /**
     * Method where fork is picked up by certain phil.
     * 
     * @param phil
     *            Number of Philosopher
     * @throws InterruptedException
     */
    public void pickUp(int phil) throws InterruptedException {

        forkChangeLock.lock();
        try {
            while (hasPickedUp) {
                waitForForkCondition.await();
            }
            System.out.println("Fork " + fork
                    + " has been picked up by philosopher " + phil);
            philosopher = phil;
            hasPickedUp = true;

        } finally {
            forkChangeLock.unlock();
        }

    }

    /**
     * Method where fork is put down by certain phil.
     * 
     * @param phil
     *            Number of Philosopher
     */
    public void putDown(int phil) {

        forkChangeLock.lock();
        try {

            System.out.println("Fork " + fork
                    + " has been put down by philosopher " + phil);
            philosopher = 0;
            hasPickedUp = false;
            waitForForkCondition.signalAll();
        } finally {
            forkChangeLock.unlock();
        }

    }

    public boolean getHasPickedUp() {
        return hasPickedUp;
    }

    public int getPhilosopher() {
        return philosopher;

    }

}
