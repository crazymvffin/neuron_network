import java.util.List;

public class ParamsBuilder {
    private List<List<Double>> sequence;
    private double errorValue;
    private double learningRate;
    private int stepsForLearning;
    private int hiddenNeurons;
    private int maxIterations;

    public ParamsBuilder setSequence(List<List<Double>> sequence) {
        this.sequence = sequence;
        return this;
    }

    public ParamsBuilder setErrorValue(double errorValue) {
        this.errorValue = errorValue;
        return this;
    }

    public ParamsBuilder setLearningRate(double learningRate) {
        this.learningRate = learningRate;
        return this;
    }

    public ParamsBuilder setHiddenNeurons(int hiddenNeurons) {
        this.hiddenNeurons = hiddenNeurons;
        return this;
    }

    public Params build() {
        return new Params(sequence, errorValue, learningRate, maxIterations, hiddenNeurons);
    }

    public ParamsBuilder setMaxIterations(int iter){
        this.maxIterations = iter;
        return this;
    }
}