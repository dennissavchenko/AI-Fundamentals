import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Perceptron {

    private final double[] w;
    private double theta;
    private double learningRate;
    private final DataReader train;
    private final DataReader test;

    public Perceptron(String train_set, String test_set) throws IOException {
        String path = "/Users/dennissavcenko/Documents/second year/NAI/Perceptron PROJECT/Perceptron DATA/";
        train = new DataReader(path + train_set);
        test = new DataReader(path + test_set);
        w = new double[train.getResult().get(0).length - 1];
        initialize();
    }

    public void initialize() {
        for (int i = 0; i < w.length; i++) {
            w[i] = Math.random();
        }
        theta = Math.random();
    }

    public int predict(double[] inputs) {
        double sum = 0.0;
        for (int i = 0; i < w.length; i++) {
            //System.out.print(w[i] + "\t");
            sum += w[i] * inputs[i];
        }
        //System.out.print(theta + "\t");
        sum -= theta;
        return sum >= 0 ? 1 : 0;
    }

    public double test() {
        int errorSum = 0;
        for(double[] cs : test.getResult()) {
            int predict = predict(cs);
            errorSum += Math.abs((int)cs[cs.length - 1] - predict);
            //System.out.println(predict);
        }
        return (double) errorSum / (double) test.getResult().size();
    }

    public void train(int epochs, double l) {
        learningRate = l;
        List<double[]> inputs = train.getResult();
        for (int epoch = 0; epoch < epochs; epoch++) {
            //double errorSum = 0;
            for (int i = 0; i < train.getResult().size(); i++) {
                //System.out.print(i + ". ");
                double prediction = predict(inputs.get(i));
                double error = inputs.get(i)[inputs.get(i).length - 1] - prediction;
                //System.out.println(error);
                update(inputs.get(i), error);
                //errorSum += Math.abs(error);
            }
            //double totalError = errorSum / inputs.size();
            //System.out.println(totalError);
        }
    }

    private void update(double[] input, double error) {
        for (int i = 0; i < w.length; i++) w[i] += learningRate * error * input[i];
        theta -= learningRate * error;
    }

    public String askUser() {
        Scanner scanner = new Scanner(System.in);
        int numAttr = train.getResult().get(0).length - 1;
        double[] newCase = new double[numAttr];
        System.out.print("Enter " + numAttr + " attributes: ");
        for(int i = 0; i < numAttr; i++) newCase[i] = scanner.nextDouble();
        return train.getNames()[predict(newCase)];
    }

}

