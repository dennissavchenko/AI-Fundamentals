import java.io.IOException;
import java.util.*;

public class KNNClassifier {
    private final List<String[]> train_set;
    private final List<String[]> test_set;

    public KNNClassifier(String train_set, String test_set) throws IOException {
        String path = "/Users/dennissavcenko/Documents/second year/NAI/k-NN PROJECT/k-NN DATA/";
        this.train_set = DataReader.getData(path + train_set);
        this.test_set = DataReader.getData(path + test_set);
    }

    private String getResponse(int k, double[] newCase) {

        List<String> outputs = DataReader.getOutputs(train_set);
        List<double[]> inputs = DataReader.getInputs(train_set);

        List<String[]> results = new ArrayList<>();

        for(int i = 0; i < outputs.size(); i++) {
            String[] pare = new String[2];
            double result = 0;
            for(int j = 0; j < inputs.get(i).length; j++) {
                result += Math.pow(newCase[j] - inputs.get(i)[j], 2);
            }
            pare[0] = String.valueOf(result);
            pare[1] = outputs.get(i);
            results.add(pare);
        }

        Map<String, Integer> map = new HashMap<>();
        for(int i = 0; i < k; i++) {
            double min = Double.parseDouble(results.get(0)[0]);
            int minIndex = 0;
            for(int j = 1; j < results.size(); j++) {
                if(Double.parseDouble(results.get(j)[0]) < min) {
                    min = Double.parseDouble(results.get(j)[0]);
                    minIndex = j;
                }
            }
            String minClass = results.get(minIndex)[1];
            if(!map.containsKey(minClass)) map.put(minClass, 1);
            else map.put(minClass, map.get(minClass) + 1);
            results.remove(minIndex);
        }

        String maxClass = "";
        int max = 0;

        for(String key : map.keySet()) {
            if(map.get(key) > max) {
                max = map.get(key);
                maxClass = key;
            }
        }
        return maxClass;
    }

    public String askUser() {
        Scanner scanner = new Scanner(System.in);
        int numAttr = train_set.get(0).length - 1;
        double[] newCase = new double[numAttr];
        System.out.print("Enter " + numAttr + " attributes: ");
        for(int i = 0; i < numAttr; i++) newCase[i] = scanner.nextDouble();
        System.out.print("Enter value of k: ");
        int k = scanner.nextInt();
        return getResponse(k, newCase);
    }

    public double testAccuracy(int k) {
        List<String> testOutputs = DataReader.getOutputs(test_set);
        List<double[]> testInputs = DataReader.getInputs(test_set);
        double counter = 0;
        for (int i = 0; i < test_set.size(); i++) {
            if(testOutputs.get(i).equals(getResponse(k, testInputs.get(i)))) counter++;
        }
        return counter / test_set.size();
    }

    // getting accuracy for k in range [startK; endK]
    public void getAccuracyTable(int startK, int endK) {
        for(int i = startK; i <= endK; i++) {
            System.out.println(i + "\t" + testAccuracy(i));
        }
    }

}
