import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataReader {
    public static List<String[]> getData(String filePath) throws IOException {
            List<String> list = Files.readAllLines(Paths.get(filePath));
            List<String[]> result = new ArrayList<>();
            for(String line : list) result.add(line.split(","));
            return result;
    }

    public static List<String> getOutputs(List<String[]> cases) {
        List<String> result = new ArrayList<>();
        for(String[] cs : cases) {
            result.add(cs[cs.length - 1]);
        }
        return result;
    }

    public static List<double[]> getInputs(List<String[]> cases) {
        List<double[]> result = new ArrayList<>();
        for(String[] cs : cases) {
            double[] list = new double[cs.length - 1];
            for(int i = 0; i < cs.length - 1; i++) {
                list[i] = Double.parseDouble(cs[i]);
            }
            result.add(list);
        }
        return result;
    }

}
