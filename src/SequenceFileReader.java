

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SequenceFileReader {

    public List<List<Double>> readSequence(String filePath, int imagesCount, int imageLength) {
        Optional<String> line = Optional.empty();
        try {
            line = Files.readAllLines(Paths.get(filePath)).stream().reduce(String::concat);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final List<Double> sequence = Arrays.stream(line.orElse("").split(","))
                .map(String::trim)
                .map(Double::valueOf)
                .collect(Collectors.toList());
        return convertToImages(sequence, imagesCount, imageLength);
    }

    private List<List<Double>> convertToImages(List<Double> sequence, int imagesCount, int imageLength) {
        final int sequenceSize = sequence.size();
        assert imagesCount + 2 <= sequenceSize;
        assert imageLength + 2 <= sequenceSize;
        int sequencePointer = 0;
        List<List<Double>> images = new ArrayList<>();
        for (int i = 0; i < imagesCount; i++) {
            List<Double> image = new ArrayList<>();
            for (int j = 0; j < imageLength; j++) {
                image.add(sequence.get(sequencePointer));
                sequencePointer++;
            }
            images.add(image);
            sequencePointer -= (imageLength - 1);
        }

        return images;
    }
}
