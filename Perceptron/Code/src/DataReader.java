import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    private final String[] names = new String[2];

    private final List<double[]> result = new ArrayList<>();

    public DataReader(String filePath) throws IOException {
        List<String> list = Files.readAllLines(Paths.get(filePath));
        String[] firstCase = list.get(0).split(",");
        String firstClass = firstCase[firstCase.length - 1];
        names[0] = firstClass;
        for(String line : list) {
            String[] items = line.split(",");
            double[] cs = new double[items.length];
            for(int i = 0; i < items.length; i++) {
                if(i == items.length - 1) {
                    if(items[i].equals(firstClass)) cs[i] = 0.0;
                    else if(names[1] == null) {
                        names[1] = items[i];
                        cs[i] = 1.0;
                    } else cs[i] = 1.0;
                } else cs[i] = Double.parseDouble(items[i]);
            }
            result.add(cs);
        }
    }

    public List<double[]> getResult() {
        return result;
    }

    public void printResult() {
        for(double[] cs : result) {
            for (double el : cs) {
                System.out.print(el + "\t");
            }
            System.out.println();
        }
    }
    public String[] getNames() {
        return names;
    }

}
