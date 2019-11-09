import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class Neuron {

    private Function<Double, Double> activatingFunction;
    private int inputsNumber;
    private List<Double> weights;
    private double threshold;
    private double sum;

    public Neuron(int inputsNumber, Function<Double, Double> activatingFunction) {
        this.inputsNumber = inputsNumber;
        this.weights = new ArrayList<>(inputsNumber);
        randomizeWeights();
        this.activatingFunction = activatingFunction;
        threshold = 0;
    }

    public List<Double> getWeights() {
        return weights;
    }

    public double getSum() {
        return sum;
    }

    public void setWeights(List<Double> weights) {
        this.weights = weights;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public Double calculate(List<Double> inputs) {
        assert inputs.size() == this.inputsNumber;
        Double sum = 0d;
        for (int i = 0; i < inputs.size(); i++) {
            sum += inputs.get(i) * weights.get(i);
        }
        this.sum = sum;
        return activatingFunction.apply(sum) - threshold;
    }

    private void randomizeWeights() {
        for (int i = 0; i < inputsNumber; i++) {
            weights.add(new Random().nextDouble() * 2 - 1);
        }
    }
}
