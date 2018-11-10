package seedu.address.testutil;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PreviewImage;
import seedu.address.model.UserPrefs;
import seedu.address.model.transformation.Transformation;
import seedu.address.model.transformation.TransformationSet;

//@@author ihwk1996

/**
 * A utility class to generate models for testing.
 */
public class ModelGenerator {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private ModelGenerator() {
    } // prevents instantiation

    /**
     * Returns a dummy image read from resources (original.png).
     */
    public static BufferedImage getABufferedImage() {
        BufferedImage originalImage = null;
        try {
            ClassLoader classLoader = ModelGenerator.class.getClassLoader();
            originalImage = ImageIO.read(classLoader.getResource("testimgs/original.png"));
        } catch (IOException e) {
            logger.warning("Error getting default model" + e);
        }
        return originalImage;
    }

    /**
     * Returns a dummy transformation (blur).
     */
    public static Transformation getATransformation() {
        return new Transformation("blur", "0x8");
    }

    /**
     * Executes a transformation on a model's previewImage.
     */
    public static Model executeATransformation(Model model) {
        model.updateCurrentPreviewImage(getABufferedImage());
        return model;
    }

    /**
     * Returns a default initialised Model.
     */
    public static Model getDefaultModel() {
        BufferedImage image = getABufferedImage();
        Model model = new ModelManager(new UserPrefs(), true);
        PreviewImage previewImage = new PreviewImage(image, new TransformationSet());
        model.updateCurrentOriginalImageForTest(previewImage);
        return model;
    }

    /**
     * Returns a Model with 1 transformation done.
     */
    public static Model getModelWithOneTransformation() {
        Model model = getDefaultModel();
        model.addTransformation(getATransformation());
        model.updateCurrentPreviewImage(getABufferedImage());
        return model;
    }

    /**
     * Returns a Model with 2 transformations done.
     */
    public static Model getModelWithTwoTransformations() {
        Model model = getModelWithOneTransformation();
        model.addTransformation(getATransformation());
        model.updateCurrentPreviewImage(getABufferedImage());
        return model;
    }

    /**
     * Returns a Model with 3 transformations done.
     */
    public static Model getModelWithThreeTransformations() {
        Model model = getModelWithTwoTransformations();
        model.addTransformation(getATransformation());
        model.updateCurrentPreviewImage(getABufferedImage());
        return model;
    }

    /**
     * Returns a Model at index 0, size 4 (3 undone states).
     */
    public static Model getModelWithUndoneStatesPointingAtStart() {
        Model model = getModelWithThreeTransformations();
        model.undoPreviewImage();
        model.undoPreviewImage();
        model.undoPreviewImage(); // Now at original state
        return model;
    }

    /**
     * Returns a Model at index 1, size 4 (2 undone states).
     */
    public static Model getModelWithUndoneStatesPointingAtMid() {
        Model model = getModelWithThreeTransformations();
        model.undoPreviewImage();
        model.undoPreviewImage();
        return model;
    }

    /**
     * Returns a Model with current directory testimgs.
     */
    public static Model getModelWithTestImgDirectory() {
        Model model = new ModelManager(new UserPrefs(), true);
        model.updateCurrDirectory(Paths.get(MainApp.MAIN_PATH + "/src/test/resources/testimgs"));
        return model;
    }

    /**
     * Returns a Model with 3 layers.
     */
    public static Model getModelWithPopulatedCanvas() {
        Model model = getDefaultModel();
        model.getCanvas().addLayer(PreviewImageGenerator.getPreviewImage(TypicalImages.FULL_LIST[2]), "Layer 2");
        model.getCanvas().addLayer(PreviewImageGenerator.getPreviewImage(TypicalImages.FULL_LIST[3]), "Layer 3");
        return model;
    }

    /**
     * Returns a Model with a test img.
     */
    public static Model getModelWithTestImg() {
        Model model = getDefaultModel();
        model.setCurrentOriginalImage(Paths.get("src", "test", "data", "sandbox", "test.jpg"));
        return model;
    }
}
