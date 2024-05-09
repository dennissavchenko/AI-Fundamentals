import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
public class CSVReader {

    private final List<String[]> content;

    public CSVReader(String fileName) throws IOException {
        content = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get("/Users/dennissavcenko/Documents/second/NAI/Single-layer network/DATA/" + fileName));
        for(String line : lines) {
            content.add(line.split(",", 2));
        }
    }

    public List<String[]> getContent() {
        return content;
    }
}
