import java.io.File;
import java.util.*;

public class Network {

    private Perceptron[] perceptrons;
    private int iterations;
    private double alfa;
    private double min = 0.01;
    private double max = 1;

    public Network(double alfa, int iterations) {
        this.alfa = Math.random() * (max - min) + min;
        this.iterations = 2;
    }

    public Network(File path) {
        File[] languages = path.listFiles();
        this.perceptrons = new Perceptron[languages.length];

        for(int i = 0; i < perceptrons.length; i++) {
            perceptrons[i] = new Perceptron(languages[i].getName(), alfa);

            for(int j = 0; j < languages.length; j++) {
                File[] examples = languages[i].listFiles();

                for(File file : examples) {
                    perceptrons[i].scanFile(file.getPath());
                    perceptrons[i].teach(iterations);
                }
            }
        }

    }

    private List<Double> getRatios(String file) {
        List<Double> result = new ArrayList<>(26);

        Map<Character, Integer> counts = new LinkedHashMap<>();
        int countAll = 0;
        for(int i = 0; i < file.length(); i++) {
            char c = file.charAt(i);
            if(c >= 'a' && c <= 'z') c -= 32;

            // policz tylko znaki A-Z
            if(!(c >= 'A' && c <= 'Z')) continue;

            countAll++;
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }

        Map<Character, Integer> sortedCounts = new TreeMap<>(counts);
        for(char ch = 'A'; ch <= 'Z'; ch++) {
            result.add((double)sortedCounts.getOrDefault(ch, 0) / countAll);
        }

        return result;
    }

    public String resultLanguage(String textAreaLine) {
        double max = 0;
        String resultLanguage = "";
        List<Double> inputCharsPercentage = getRatios(textAreaLine);

        for(Perceptron perceptron : perceptrons) {
            double x = perceptron.test(inputCharsPercentage);

            if (max < x) {
                max = x;
                resultLanguage = perceptron.getLanguage();
            }
        }

        return resultLanguage;
    }
}
