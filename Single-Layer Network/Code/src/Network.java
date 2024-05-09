import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Network {

    private final List<Perceptron> layer;

    private final TextProcessor train;

    private final CSVReader test;

    public Network(String train, String test) throws IOException {
        layer = new ArrayList<>();
        this.train = new TextProcessor(new CSVReader(train).getContent());
        this.test = new CSVReader(test);
        List<List<Double>> values = this.train.getValues();
        List<String> languages = this.train.getLanguages();
        for(String language : languages) {
            Perceptron perceptron = new Perceptron(values, languages.indexOf(language));
            layer.add(perceptron);
        }
    }

    public String askUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("This perceptron can classify text in ");
        List<String> languages = train.getLanguages();
        for(int i = 0; i < languages.size(); i++) System.out.print(languages.size() - 1 == i ? languages.get(i) + "." : languages.size() - 2 == i ? languages.get(i) + " and " : languages.get(i) + ", ");
        System.out.println();
        System.out.print("Enter text to classify: ");
        return predict(scanner.nextLine());
    }

    public void retrain(int epochs, double alfa) {
        for(Perceptron perceptron : layer) {
            perceptron.initialize();
            perceptron.train(epochs, alfa);
        }
    }

    public String predict(String text) {
        List<Double> cs = train.getTextAnalise(text);
        double maxPrediction = layer.get(0).predict(cs);
        int maxIndex = 0;
        for(int i = 1; i < layer.size(); i++) {
            double prediction = layer.get(i).predict(cs);
            if(prediction > maxPrediction) {
                maxPrediction = prediction;
                maxIndex = i;
            }
        }
        return train.getLanguages().get(maxIndex);
    }

    public double test() {
        double error = 0;
        for(String[] cs : test.getContent()) {
            if(!cs[0].equals(predict(cs[1]))) {
                error++;
                System.out.println(cs[1] + " -> " + predict(cs[1]) + " (" + cs[0] + ")");
            }
        }
        error /= test.getContent().size();
        return error;
    }

}
