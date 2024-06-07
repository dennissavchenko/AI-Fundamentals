import java.io.IOException;
import java.util.Scanner;

public class Menu {

    private static KNNClassifier knnClassifier;

    public static void start() {
        System.out.println("Welcome to k-NN classifier!");
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
            knnClassifier = new KNNClassifier(train_set, test_set);
        } catch (IOException e) {
            System.out.println("Files were not found. Try again!");
            changeDataset();
        }

        callMenu();

    }

    private static void callMenu() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to k-NN classifier menu!");
        System.out.println("1 -> Classify case.");
        System.out.println("2 -> Test 'k' accuracy.");
        System.out.println("3 -> Get accuracy table.");
        System.out.println("Any other key -> Change dataset");

        switch (scanner.next()) {
            case "1" -> {
                System.out.println("Answer: " + knnClassifier.askUser());
                callMenu();
            }
            case "2" -> {
                System.out.print("Enter value of k: ");
                int k = scanner.nextInt();
                System.out.println(knnClassifier.testAccuracy(k));
                callMenu();
            }
            case "3" -> {
                System.out.print("Enter value of the first k: ");
                int fK = scanner.nextInt();
                System.out.print("Enter value of the last k: ");
                int lK = scanner.nextInt();
                knnClassifier.getAccuracyTable(fK, lK);
                callMenu();
            }
            default -> changeDataset();
        }
    }

}
