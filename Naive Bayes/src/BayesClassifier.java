import java.io.IOException;
import java.util.*;

public class BayesClassifier {

    private final CSVReader train;

    private final CSVReader test;

    private final List<List<Map<String, Integer>>> probabilities;

    public BayesClassifier(String train, String test) throws IOException {
        this.train = new CSVReader(train);
        this.test = new CSVReader(test);
        this.probabilities = new ArrayList<>();
    }

//    public void print() {
//        for(List<Map<String, Integer>> list : probabilities) {
//            for(Map<String, Integer> map : list) {
//                for (String key : map.keySet()) {
//                    System.out.print(key + " -> " + map.get(key) + " ");
//                }
//                System.out.println();
//            }
//        }
//    }

    public String askUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter text to classify inr format (a,b,c,d,......): ");
        String[] attributes = scanner.nextLine().split(",");
        return predict(Arrays.asList(attributes));
    }

    public String predict(List<String> list) {
        List<Double> results = new ArrayList<>();
        for(int j = 0; j < probabilities.size(); j++) {
            double result = 1;
            List<Map<String, Integer>> mapList = probabilities.get(j);
            result *= mapList.get(0).get(train.getClasses().get(j)) / (double) train.getContent().size();
            for(int i = 0; i < list.size(); i++) {
                if(mapList.get(i + 1).containsKey(list.get(i))) {
                    result *= mapList.get(i + 1).get(list.get(i)) / (double) mapList.get(0).get(train.getClasses().get(j));
                } else {
                    double counter = 0;
                    for(int k = 0; k < probabilities.size(); k++) {
                        if(k != j) {
                            if(probabilities.get(k).get(i + 1).containsKey(list.get(i))) counter += probabilities.get(k).get(i + 1).get(list.get(i));
                        }
                    }
                    result *= (1 / ((double) mapList.get(0).get(train.getClasses().get(j)) + counter));
                }
            }
            results.add(result);
        }
        double maxResult = results.get(0);
        int maxIndex = 0;
        for(int i = 1; i < results.size(); i++) {
            if(results.get(i) > maxResult) {
                maxResult = results.get(i);
                maxIndex = i;
            }
        }
        return train.getClasses().get(maxIndex);
    }

    public double test() {
        double counter = 0;
        for(String[] line : test.getContent()) {
            if(line[0].equals(predict(Arrays.asList(line).subList(1, line.length)))) counter++;
        }
        return counter / test.getContent().size();
    }

    public void train() {
        for(String class_ : train.getClasses()) {
            List<Map<String, Integer>> list = new ArrayList<>();
             for(int i = 0; i < train.getContent().get(0).length; i++) {
                 Map<String, Integer> map = new HashMap<>();
                 for(int j = 0; j < train.getContent().size(); j++) {
                     if(train.getContent().get(j)[0].equals(class_)) {
                         String curr = train.getContent().get(j)[i];
                         if(!map.containsKey(curr)) {
                             map.put(curr, 1);
                         } else {
                             map.put(curr, map.get(curr) + 1);
                         }
                     }
                 }
                 list.add(map);
             }
             probabilities.add(list);
         }
    }

}
