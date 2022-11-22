import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Perceptron {
    private double[] inputCharsPercentage;
    private double[] weights;
    private final int numberOfInputs = 26;
    private String language;
    private double alfa;
    private double sgn;
    private double t;

    public Perceptron(String language, double alfa) {
        this.alfa = alfa;
        this.language = language;
        this.weights = new double[numberOfInputs];
        for(int i = 0; i < weights.length; i++) {
            weights[i] = Math.random();
        }
        this.weights = normalization(weights);
        this.t = ThreadLocalRandom.current().nextDouble(-1.0,1.0);
    }

    public void teach(int iteration) {
        double sum = vectorMultiply(inputCharsPercentage, weights);

        for(int j = 0; j < iteration; j++) {

            if (sum >= t) {
                double[] arr = new double[weights.length];
                for (int i = 0; i < weights.length; i++) {
                    arr[i] = weights[i] - alfa * inputCharsPercentage[i];
                }
                this.weights = arr;
                this.t = this.t - alfa * (-1);

            } else {
                double[] arr = new double[weights.length];
                for (int i = 0; i < weights.length; i++) {
                    arr[i] = weights[i] + alfa * inputCharsPercentage[i];
                }
                this.weights = arr;
                this.t = this.t - alfa * (-1);

            }
        }

        activationFunction(inputCharsPercentage);


    }

    public void activationFunction(double[] inputs) {
        double net = vectorMultiply(inputs, weights);
        net -= this.t;
        this.sgn = 1 / (1 + Math.exp(-net));
    }

    public double test(List<Double> textFromGUIRatio) {
        double wtx = 0;
        for (int i = 0; i < weights.length; i++) {
            wtx += weights[i] * textFromGUIRatio.get(i);
        }

        return 1/(1 + Math.exp((-1) * wtx));
    }

    public String getLanguage() {
        return language;
    }


    public void scanFile(String path) {
        inputCharsPercentage = new double[numberOfInputs];
        for(int i = 0; i < inputCharsPercentage.length; i++) {
            inputCharsPercentage[i] = 0;
        }
        int counter = 0;

        try {
            Scanner scanner = new Scanner(new File(path));
            String line;
            while (scanner.hasNext()) {
                line = scanner.nextLine().toLowerCase(Locale.ROOT);

                for (char c : line.toCharArray()) {
                    if(c > 96 && c < 123) { //find only ASCII
                        inputCharsPercentage[c - 97]++;
                        counter++;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < numberOfInputs; i++) {
            inputCharsPercentage[i] /= counter;
        }

    }

    private double vectorMultiply(double[] inputs, double[] weights) {
        if(inputs.length != weights.length) {
            throw new IllegalArgumentException("Different sizes of vectors!");
        }
        double sum = 0;
        for(int i = 0; i < weights.length; i++) {
            sum += (weights[i] * inputs[i]);
        }
        return sum;
    }

    private double[] normalization(double[] weights) {
        double value = 0;

        for (int i = 0; i < weights.length; i++) {
            value += Math.pow(weights[i],2);
        }

        value = Math.sqrt(value);
        for(int i = 0; i < weights.length; i++) {
            weights[i] = weights[i] / value;
        }

        return weights;
    }
}
