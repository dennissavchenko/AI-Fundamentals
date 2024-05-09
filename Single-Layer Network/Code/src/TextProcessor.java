import java.util.*;

public class TextProcessor {

    private final List<String[]> content;

    private final char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private final List<String> languages;

    private final List<List<Double>> values;

    public TextProcessor(List<String[]> content) {

        this.content = content;
        this.values = new ArrayList<>();
        languages = new ArrayList<>();

        defineLanguages();

        defineValues();

    }

    private void defineLanguages() {
        for(String[] line : content) {
            if(!languages.contains(line[0])) languages.add(line[0]);
        }
    }

    private void defineValues() {
        for (String[] line : content) {
            List<Double> list = new ArrayList<>(getTextAnalise(line[1]));
            list.add((double) languages.indexOf(line[0]));
            values.add(list);
        }
    }

    private List<Double> normalize(List<Integer> list) {
        double length = 0.0;

        List<Double> result = new ArrayList<>();
        for(int element : list) {
            length += Math.pow(element, 2);
        }

        length = Math.sqrt(length);

        for(int element : list) {
            result.add((double) element / length);
        }

        return result;
    }

    public List<Double> getTextAnalise(String text) {
        Map<Character, Integer> map = new HashMap<>();

        for(char letter : alphabet) {
            map.put(letter, 0);
        }
        text = text.toLowerCase();
        char[] letters = text.toCharArray();
        for(char letter : letters) {
            if(map.containsKey(letter)) map.put(letter, map.get(letter) + 1);
        }
        return normalize(map.values().stream().toList());
    }

    public List<List<Double>> getValues() {
        return values;
    }

    public List<String> getLanguages() {
        return languages;
    }
}
