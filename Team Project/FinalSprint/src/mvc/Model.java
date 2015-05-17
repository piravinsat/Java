package mvc;

import java.util.ArrayList;

import simulation.MultiQueue;
import simulation.Simulation;
import simulation.SingleQueue;
import database.Database;

/**
 * Model class for the Supermarket Simulation, part of the MVC pattern.
 * <p>
 * The Model corresponds to the highest-level class on the program's back-end,
 * with only the necessary functions to run the simulation and get data from it
 * once the simulation finishes running.
 * <p>
 * @author Luigi Pardey
 * @see Simulation
 */
public class Model {
    /**
     * Reference to the simulation being run.
     * <p>
     * This varialbe will take the reference to either the object
     * <code>singleQueue</code> or the object <code>multiQueue</code>.
     */
    private Simulation simulation;
    /**
     * Models the supermarket as having one queue for all the checkouts.
     */
    private Simulation singleQueue;
    /**
     * Models the supermarket as having one queue per checkout.
     */
    private Simulation multiQueue;
    /**
     * Number of checkouts to model.
     */
    private int checkouts;
    /**
     * Number of customers to model.
     */
    private int customers;
    /**
     * Number of runs to perform.
     * <p>
     * One run is equivalent to one day, regardless of the time it takes
     * to complete.
     */
    private int days;
    /**
     * Whether the simulation models a single queue or multiple queues.
     */
    private boolean singleQ;
    /**
     * List of all the shopping times of this simulation run.
     */
    private ArrayList<Integer> shoppingTimes;
    /**
     * List of all the queueing times of this simulation run.
     */
    private ArrayList<Integer> queueingTimes;
    /**
     * Creates a new Model object.
     * <p>
     * Variables are initialised to default values, but it is encouraged
     * to override the defaults with custom values.
     */
    public Model() {
        Database db = new Database("Supermarket");
        String query = "DELETE FROM purchases";
        db.updateData(query);
        query = "DELETE FROM daily_purchases";
        db.updateData(query);
        final int defCheckouts = 1;
        final int defCustomers = 100;
        final int defDays = 1;
        final boolean defQueue = true;
        checkouts = defCheckouts;
        customers = defCustomers;
        days = defDays;
        singleQ = defQueue;
        shoppingTimes = new ArrayList<Integer>();
        queueingTimes = new ArrayList<Integer>();
    }
    /**
     * Converts the data of an array of integers into an array of doubles.
     * <p>
     * This method converts an array of integer waiting times measured in
     * simulation time-steps, into an array of double precision floating-point
     * numbers corresponding to waiting times in seconds.
     * <p>
     * @param array the array of simulation time-steps.
     * @return array of real time values in seconds.
     */
    protected final double[] convertArray(final int[] array) {
        double [] data = new double[array.length];
        for (int index = 0; index < array.length; index++) {
            int original = array[index];
            double value = (double) original / Simulation.CONVERSION_FACTOR;
            data[index] = value;
        }
        return data;
    }
    /**
     * Returns the average time a customer had to wait in the queue.
     * <p>
     * @return the average queueing time.
     */
    public final double getAverageQueueing() {
        return simulation.getAverageQueueing();
    }
    /**
     * Returns the average time a customer spent in the supermarket.
     * <p>
     * @return the average shopping time.
     */
    public final double getAverageShopping() {
        return simulation.getAverageWaiting();
    }
    /**
     * Returns an unordered array of integers with the values for every
     * customer's queueing time.
     * <p>
     * @return the array of queueing times.
     */
    public final double[] getTimesQueueing() {
        int[] queueing = new int[queueingTimes.size()];
        for (int i = 0; i < queueing.length; i++) {
            queueing[i] = queueingTimes.get(i);
        }
        return convertArray(queueing);
    }
    /**
     * Returns an unordered array of integers with the values for every
     * customer's shopping time.
     * <p>
     * @return the array of shopping times.
     */
    public final double[] getTimesShopping() {
        int[] shopping = new int[shoppingTimes.size()];
        for (int i = 0; i < shopping.length; i++) {
            shopping[i] = shoppingTimes.get(i);
        }
        return convertArray(shopping);
    }
    /**
     * Sets the number of customers to model in the simulation.
     * <p>
     * @param c the number of customers to set.
     */
    public final void setCustomers(final int c) {
        customers = c;
    }
    /**
     * Sets the number of checkouts to model in the simulation.
     * <p>
     * @param c the number of checkouts to set.
     */
    public final void setCheckouts(final int c) {
        checkouts = c;
    }
    /**
     * Sets the number of days to model in the simulation.
     * <p>
     * @param d the number of days to set.
     */
    public final void setDays(final int d) {
        days = d;
    }
    /**
     * Sets whether the simulation should model a single queue for all the
     * checkouts.
     * <p>
     * @param q true to model a single queue.
     * @see SingleQueue
     * @see MultiQueue
     */
    public final void setSingleQueue(final boolean q) {
        singleQ = q;
    }
    /**
     * Runs the simulation.
     */
    public final void simulate() {
        shoppingTimes.clear();
        queueingTimes.clear();
        if (singleQ) {
            singleQueue = new SingleQueue(customers, checkouts);
            simulation = singleQueue;
        } else {
            multiQueue = new MultiQueue(customers, checkouts);
            simulation = multiQueue;
        }
        for (int day = 0; day < days; day++) {
            Simulation.setDay(day);
            System.out.println("Day: " + day);
            simulation.resetSimulation();
            simulation.runSimulation();
            simulation.detectPotentialThieves();
            simulation.report();
            for (int i : simulation.getWaitingTimes()) {
                shoppingTimes.add(i);
            }
            for (int j : simulation.getQueueTimes()) {
                queueingTimes.add(j);
            }
        }
        simulation.theftDetectionReport();
    }
}
