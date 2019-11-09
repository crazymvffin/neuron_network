import java.util.ArrayList;
import java.util.List;

public class JordanElmanNetwork {
    private List<Neuron> hiddenLayer = new ArrayList<>();
    private Params Params;
    private Neuron exitNeuron;
    private int contextsNumber;
    private List<Double> context;

    public JordanElmanNetwork(Params Params) {
        this.Params = Params;
        contextsNumber = Params.getHiddenNeurons() + 1;
        int inputs = Params.getHiddenNeurons() + contextsNumber;
        prepareHiddenLayer(inputs, Params.getHiddenNeurons());
        exitNeuron = new Neuron(Params.getHiddenNeurons(), x -> x);
        prepareContext();
    }


    private Double activatingFunction(Double S) {
        return Math.log(S + Math.sqrt(S * S + 1));
    }
    private Double derivativeFunction(Double S) {
        return 1 / (Math.sqrt(S * S + 1));
    }



    public Double adaptWeights(List<Double> inputs) {
        Double expectedValue = inputs.remove(inputs.size() - 1);
        List<Double> hiddenResult = new ArrayList<>();
        inputs.addAll(copyArray(context));
        context.clear();

        for (Neuron neuron1 : hiddenLayer) {
            hiddenResult.add(neuron1.calculate(inputs));
        }
        context = hiddenResult;

        final Double calculatedValue = exitNeuron.calculate(hiddenResult);
        context.add(calculatedValue);
        final Double deltaValue = expectedValue - calculatedValue;

        final List<Double> oldExitWeights = exitNeuron.getWeights();
        final List<Double> newExitWeights = new ArrayList<>();

        final double deltaExitThreshold = Params.getLearningRate() * deltaValue;

        for (int i = 0; i < oldExitWeights.size(); i++) {
            double exitWeightDelta = -deltaExitThreshold * hiddenResult.get(i);
            newExitWeights.add(oldExitWeights.get(i) - exitWeightDelta);
        }

        exitNeuron.setThreshold(exitNeuron.getThreshold() + deltaExitThreshold);
        exitNeuron.setWeights(newExitWeights);

        for (int i = 0; i < hiddenLayer.size(); i++) {
            final Neuron neuron = hiddenLayer.get(i);
            final List<Double> oldWeights = neuron.getWeights();
            final List<Double> newWeights = new ArrayList<>();
            final double hiddenThreshold = Params.getLearningRate() * deltaValue * oldExitWeights.get(i);
            double hiddenWeightDelta = -hiddenThreshold * derivativeFunction(neuron.getSum()) * inputs.get(i);
            for (Double oldWeight : oldWeights) {
                newWeights.add(oldWeight - hiddenWeightDelta);
            }
            neuron.setThreshold(neuron.getThreshold() + hiddenThreshold);
            neuron.setWeights(newWeights);
           // System.out.println("Weights neuron №: " + i + newWeights);
        }
       // System.out.println("Weights of exit neuron  " + exitNeuron.getWeights());

        return calculatedValue;
    }

    public Double learning(List<List<Double>> inputs) {
        Double error = Double.MAX_VALUE;
        Double predictedValue = 0d;
        int iteration = 0;
        while (Params.getErrorValue() < error) {
            iteration++;
            for (List<Double> input : inputs) {
                predictedValue = adaptWeights(copyArray(input));
            }
            error = calculateError(inputs);
            System.out.println("Итерация      : " + iteration);
            System.out.println("Ожидаемое значение : " + getExpectedValue(inputs));
            System.out.println("Предсказанное : " + predictedValue);
            System.out.println("Ошибка          : " + error);
            System.out.println("------------------------------\n");
            if(iteration == Params.getMaxIterations()){
                System.out.println("Достигнута макс итерация: " + Params.getMaxIterations());
            }
        }
        System.out.println(inputs);

        return predictedValue;
    }

    private Double getExpectedValue(List<List<Double>> inputs) {
        final List<Double> lastPortion = inputs.get(inputs.size() - 1);
        return lastPortion.get(lastPortion.size() - 1);
    }

    private void prepareHiddenLayer(int inputs, int hiddenNeurons) {
        for (int i = 0; i < hiddenNeurons; i++) {
            hiddenLayer.add(new Neuron(inputs, this::activatingFunction));
        }
    }

    private void prepareContext() {
        context = new ArrayList<>();
        for (int i = 0; i < contextsNumber; i++) {
            context.add(0d);
        }
    }

    private Double calculateError(Double deltaValue) {
        return deltaValue * deltaValue / 2;
    }

    private Double calculateError(List<List<Double>> inputs) {
        Double error = 0d;
        for (List<Double> input : inputs) {
            final List<Double> inputCopy = copyArray(input);
            Double expectedValue = inputCopy.remove(inputCopy.size() - 1);
            final Double deltaValue = expectedValue - calculateY(inputCopy);
            error += calculateError(deltaValue);
        }
        return error;
    }

    private Double calculateY(List<Double> inputs) {
        inputs.addAll(context);
        List<Double> hiddenResult = new ArrayList<>();

        for (Neuron neuron : hiddenLayer) {
            hiddenResult.add(neuron.calculate(inputs));
        }
        context = hiddenResult;

        final Double y = exitNeuron.calculate(hiddenResult);
        context.add(y);
        return y;
    }

    private <T> List<T> copyArray(List<T> target) {
        List<T> list = new ArrayList<>();
        list.addAll(target);
        return list;
    }


}
