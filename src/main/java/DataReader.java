

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    private final static Logger LOGGER = LoggerFactory.getLogger(DataReader.class);

    public static final String INPUT_IMAGE_PATH = "resources/train-images.idx3-ubyte";
    public static final String INPUT_LABEL_PATH = "resources/train-labels.idx1-ubyte";

    public static final String INPUT_IMAGE_PATH_TEST_DATA = "resources/t10k-images.idx3-ubyte";
    public static final String INPUT_LABEL_PATH_TEST_DATA = "resources/t10k-labels.idx1-ubyte";

    public static final int VEC_DIMENSION = 784;

    public static List<LabeledImage> loadData(final int size) {
        return getLabeledImages(INPUT_IMAGE_PATH, INPUT_LABEL_PATH, size);
    }
    
    public static List<LabeledImage> loadTestData(final int size) {
        return getLabeledImages(INPUT_IMAGE_PATH_TEST_DATA, INPUT_LABEL_PATH_TEST_DATA, size);
    }

    private static List<LabeledImage> getLabeledImages(final String inputImagePath,
                                                       final String inputLabelPath,
                                                       final int amountOfDataSet) {

        final List<LabeledImage> labeledImageArrayList = new ArrayList<>(amountOfDataSet);

        try (FileInputStream inImage = new FileInputStream(inputImagePath);
             FileInputStream inLabel = new FileInputStream(inputLabelPath)) {

            double[] imgPixels = new double[VEC_DIMENSION];

            for (int i = 0; i < amountOfDataSet; i++) {
                for (int index = 0; index < VEC_DIMENSION; index++) {
                    imgPixels[index] = inImage.read();
                }
                int label = inLabel.read();
                labeledImageArrayList.add(new LabeledImage(label, imgPixels));
                if (i % 500 == 0) {
                    LOGGER.info("Number of images extracted: " + i);
                }
            }
            LOGGER.info("Images Loaded");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return labeledImageArrayList;
    }

}