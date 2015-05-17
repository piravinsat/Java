import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tally {

    private int tally;
    private Lock museumCapacityChangeLock;
    private Condition museumCapacityLimitCondition;
    private Condition museumEmptyCondition;

    /**
     * The tally class is constructed at before the opening of the doors of the
     * Museum with no visitors.
     */
    public Tally() {
        tally = 0;
        museumCapacityChangeLock = new ReentrantLock();
        museumCapacityLimitCondition = museumCapacityChangeLock.newCondition();
        museumEmptyCondition = museumCapacityChangeLock.newCondition();
    }

    /**
     * Adds a person to the museum count
     * 
     * @throws InterruptedException
     */
    public void entry() throws InterruptedException {

        museumCapacityChangeLock.lock();
        try {
            while (tally == 1000) { // Limit of 1000 people at any given
                // time
                museumCapacityLimitCondition.await();
            }

            System.out.println("Person entered");
            int clickerClicked = tally + 1; // Adds one to the staff
            // clicker
            System.out.println("Museum capacity: " + clickerClicked);
            tally = clickerClicked;
            museumEmptyCondition.signalAll();
        } finally {
            museumCapacityChangeLock.unlock();
        }

    }

    /**
     * Removes a person to the museum count
     * 
     * @throws InterruptedException
     */
    public void exit() throws InterruptedException {

        museumCapacityChangeLock.lock();
        try {
            while (tally == 0) { // Stops having negative amount of
                // visitors
                museumEmptyCondition.await();
            }
            System.out.println("Person left");
            int clickerClicked = tally - 1; // Subtracts one to the staff
            // clicker
            System.out.println("Museum capacity: " + clickerClicked);
            tally = clickerClicked;
            museumCapacityLimitCondition.signalAll();
        } finally {
            museumCapacityChangeLock.unlock();
        }

    }

    /**
     * Gets the current tally.
     * 
     * @return tally
     */
    public int getTally() {
        return tally;
    }

}
