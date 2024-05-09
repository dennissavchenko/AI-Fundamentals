import java.util.List;
public class Perceptron {

    private final List<List<Double>> train_dataset;

    private final int currentClass;

    private final double[] w;

    private double theta;

    public Perceptron(List<List<Double>> train_dataset, int currentClass) {
        this.train_dataset = train_dataset;
        this.currentClass = currentClass;
        w = new double[train_dataset.get(0).size() - 1];
        initialize();
    }

    public void initialize() {
        for(int i = 0; i < w.length; i++) w[i] = Math.random();
        theta = Math.random();
    }

    private double activate(double net) {
        return net;
    }

    public double predict(List<Double> x) {
        double net = 0;
        for(int i = 0; i < w.length; i++) {
            net += x.get(i) * w[i];
        }
        net -= theta;
        return activate(net);
    }

    private void update(List<Double> x, double error, double alfa) {
        for(int i = 0; i < w.length; i++) w[i] += error * x.get(i) * alfa;
        theta -= error * alfa;
    }

    public void train(int epochs, double alfa) {
        double globalError = 1;
        for(int epoch = 0; epoch < epochs && globalError > 0.01; epoch++) {
            globalError = 0;
            for(List<Double> cs : train_dataset) {
                double d = cs.get(cs.size() - 1) == (double) currentClass ? 1.0 : 0.0;
                double y = predict(cs);
                double error = d - y;
                update(cs, error, alfa);
                globalError += Math.abs(d - y);
            }
            globalError /= train_dataset.size();
        }
    }

}

