import java.util.List;

public class Params {
    private List<List<Double>> sequence;
    private double errorValue;
    private double learningRate;
    private int stepsForLearning;
    private int maxIterations;
    private int hiddenNeurons;

    public Params(List<List<Double>> sequence, double errorValue, double learningRate, int maxIterations, int hiddenNeurons) {
        this.sequence = sequence;
        this.errorValue = errorValue;
        this.learningRate = learningRate;
        this.hiddenNeurons = hiddenNeurons;
        this.maxIterations = maxIterations;
    }

    public List<List<Double>> getSequence() {
        return sequence;
    }

    public double getErrorValue() {
        return errorValue;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public int getHiddenNeurons() {
        return hiddenNeurons;
    }

    public void setMaxIteraions(int iter) {
        this.maxIterations = iter;
    }

    public int getMaxIterations(){
        return maxIterations;
    }
}
