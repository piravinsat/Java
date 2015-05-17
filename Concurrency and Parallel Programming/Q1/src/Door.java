/**
 * Door Runnable which three instance is created from it
 * 
 * @author 6651
 * 
 */
public class Door implements Runnable {

    private int door;
    private Tally museum;
    private boolean isRunning = false;
    private int delay = 1;

    /**
     * Constructor for Door class
     * 
     * @param doorNum
     *            Door Number to specify if able to enter or exit
     * @param aCount
     *            Number of people simulated
     * @param aMuseum
     *            Instance of Tally class
     */
    public Door(int doorNum, Tally aMuseum) {

        door = doorNum;
        isRunning = true;
        museum = aMuseum;
    }

    /**
     * Not using a runnable interface without the run method
     */
    public void run() {

        try {
            while (isRunning) {
                // Random generate if want to come or go
                double rgn = Math.random();
                boolean enter;

                if (rgn <= 0.5) {
                    enter = false;
                } else {
                    enter = true;
                }

                if (enter) { // Decides to enter
                    if (door != 2) { // If door is not the exit only door
                        museum.entry();
                    } // Else it has to take another door
                } else if (!enter) {// Decides to exit
                    if (door != 1) { // If door is not the entrance only door
                        museum.exit();
                    } // Else it has to take another door
                }

                Thread.sleep(delay);
            }
        } catch (InterruptedException exception) {
        }

    }

}
