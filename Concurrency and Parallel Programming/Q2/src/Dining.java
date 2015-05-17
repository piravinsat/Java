import java.util.Scanner;

/**
 * Main class Dining
 * 
 * @author 6651
 * 
 */
public class Dining {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Type in max number of philosophers that can dine ");
        int Nphil = scan.nextInt();

        System.out.println("Would you like it to deadlock? Type in 99 ");
        int deadlockAns = scan.nextInt();

        scan.close();

        Fork forks[] = new Fork[Nphil];

        for (int i = 0; i < Nphil; i++) {

            forks[i] = new Fork(i);
        }

        Thread threads[] = new Thread[Nphil];

        if (deadlockAns == 99) {
            // DEADLOCK OPTION
            for (int i = 0; i < Nphil; i++) {
                threads[i] = new Thread(new Philosopher(forks[i], forks[(i + 1)
                        % Nphil], i + 1));
                threads[i].start();
            }
        } else {
            // NON-DEADLOCK OPTION
            for (int i = 0; i < Nphil; i++) {
                if (i == 0) {
                    threads[0] = new Thread(new Philosopher(forks[1], forks[0],
                            1));
                } else {
                    threads[i] = new Thread(new Philosopher(forks[i],
                            forks[(i + 1) % Nphil], i + 1));
                }

                threads[i].start();
            }
        }
    }
}
