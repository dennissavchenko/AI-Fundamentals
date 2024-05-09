import java.io.IOException;
import java.util.Scanner;

public class Menu {

    private static BayesClassifier bayesClassifier;

    public static void start() {
        System.out.println("Welcome to Bayes Classifier!");
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
            bayesClassifier = new BayesClassifier(train_set, test_set);
        } catch (IOException e) {
            System.out.println("Files were not found. Try again!");
            changeDataset();
        }

        bayesClassifier.train();
        callMenu();

    }

    private static void callMenu() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Bayes Classifier menu!");
        System.out.println("1 -> Classify case.");
        System.out.println("2 -> Get the test dataset accuracy.");
        System.out.println("Any other key -> Change dataset");

        switch (scanner.next()) {
            case "1" -> {
                System.out.println("Answer: " + bayesClassifier.askUser());
                callMenu();
            }
            case "2" -> {
                System.out.println("Accuracy: " + bayesClassifier.test() * 100 + "%");
                callMenu();
            }
            default -> changeDataset();
        }
    }

}

