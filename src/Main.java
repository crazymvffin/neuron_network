import java.util.List;

public class Main {
    public static void main(String[] args) {
        int hiddenNeurons = 3;
        final List<List<Double>> seqFile = new SequenceFileReader()
                .readSequence("sequence3.txt", 4, hiddenNeurons + 1);
        Params Params = new ParamsBuilder()
                .setSequence(seqFile)
                .setLearningRate(0.001d)
                .setErrorValue(0.01)
                .setHiddenNeurons(hiddenNeurons)
                .setMaxIterations(1000000)
                .build();
        final JordanElmanNetwork network = new JordanElmanNetwork(Params);
        System.out.println(seqFile);
        System.out.println(network.learning(seqFile));
    }
}
