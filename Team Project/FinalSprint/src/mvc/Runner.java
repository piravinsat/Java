package mvc;

/**
 * Utility class that executes the program.
 * <p>
 * @author Luigi Pardey
 *
 */
public class Runner {

    /**
     * Creates a Runner object.
     * <p>
     * Protected to restrict its creation to this package.
     */
    protected Runner() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Runs the program.
     * <p>
     * @param args command-line arguments for this program.
     */
    public static void main(final String[] args) {
        // Single Queue
        Model model = new Model();
        View view = new View();
        new Controller(model, view);

        view.setVisible(true);
    }

}
