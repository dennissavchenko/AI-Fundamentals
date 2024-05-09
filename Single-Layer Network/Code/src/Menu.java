import java.io.IOException;
import java.util.Scanner;

public class Menu {

    private static Network network;

    public static void start() {
        System.out.println("Welcome to single-layer network classifier!");
        changeDataset();
    }
    private static void changeDataset() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Provide training and testing datasets' names!");

        System.out.print("Training dataset: ");
        String train_set = scanner.next();

        System.out.print("Testing dataset: ");
        String test_set = scanner.next();

        try {
            network = new Network(train_set, test_set);
        } catch (IOException e) {
            System.out.println("Files were not found. Try again!");
            changeDataset();
        }

        train();
        callMenu();

    }

    private static void train() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Network training: ");
        System.out.print("Enter number of epochs: ");
        int epochs = scanner.nextInt();
        System.out.print("Enter value of learning rate: ");
        double l = scanner.nextDouble();
        network.retrain(epochs, l);
    }

    private static void callMenu() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to single-layer network classifier menu!");
        System.out.println("1 -> Classify text.");
        System.out.println("2 -> Retrain network.");
        System.out.println("3 -> Get the test dataset accuracy.");
        System.out.println("Any other key -> Change dataset");

        switch (scanner.next()) {
            case "1" -> {
                System.out.println("Answer: " + network.askUser());
                callMenu();
            }
            case "2" -> {
                train();
                callMenu();
            }
            case "3" -> {
                System.out.println("Network accuracy: " + ((1 - network.test()) * 100) + "%");
                callMenu();
            }
            default -> changeDataset();
        }
    }

}

