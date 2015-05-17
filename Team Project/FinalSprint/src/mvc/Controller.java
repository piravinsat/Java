package mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class for the Supermarket Simulation, part of the MVC pattern.
 * <p>
 * This class acts as an interface between the View (GUI and user interactions)
 * and the Model (back-end, program logic). It also contains nested classes
 * for the relevant object listeners for different buttons in the GUI.
 * <p>
 * @author Luigi Pardey
 * @see Model
 * @see View
 */
public class Controller {
    /**
     * The model.
     */
    private Model model;
    /**
     * The view.
     */
    private View view;
    /**
     * Creates a new Controller.
     * <p>
     * The Controller will hold the instances of Model and View to act
     * as the interface between them, so the instantiated objects must
     * be provided via the constructor's parameters.
     * <p>
     * @param m the instance of the Model
     * @param v the instance of the View
     */
    public Controller(final Model m, final View v) {
        model = m;
        view = v;
        view.addRunListener(new RunListener());
    }
    /**
     * Action Listener for running the simulation.
     * <p>
     * This class contains the methods necessary to detect an action in the
     * View, and act upon them accordingly. The action being listened for is
     * a button action.
     * <p>
     * @author Luigi Pardey
     *
     */
    private class RunListener implements ActionListener {
        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
         */
        @Override
        public final void actionPerformed(final ActionEvent e) {
            model.setSingleQueue(view.isSingleQueue());
            try {
                int customers = Integer.parseInt(view.getCustomers());
                int checkouts = Integer.parseInt(view.getCheckouts());
                int days = Integer.parseInt(view.getDays());
                model.setCustomers(customers);
                model.setCheckouts(checkouts);
                model.setDays(days);
            } catch (NumberFormatException nFE) {
                // TODO error message
                return;
            }
            model.simulate();
            view.populateHistogram(
                    model.getTimesShopping(), model.getTimesQueueing());
        }
    }
}
