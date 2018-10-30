package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
        model.updateCurrentPreviewImage(getABufferedImage(), getATransformation());
        return model;
    }

    /**
     * Returns a default initialised Model.
     */
    public static Model getDefaultModel() {
        BufferedImage image = getABufferedImage();
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        PreviewImage previewImage = new PreviewImage(image, new TransformationSet());
        model.setCurrentPreviewImage(previewImage);
        return model;
    }

    /**
     * Returns a Model with 1 transformation done.
     */
    public static Model getModelWithOneTransformation() {
        Model model = getDefaultModel();
        model.updateCurrentPreviewImage(getABufferedImage(), getATransformation());
        return model;
    }

    /**
     * Returns a Model with 2 transformations done.
     */
    public static Model getModelWithTwoTransformations() {
        Model model = getModelWithOneTransformation();
        model.updateCurrentPreviewImage(getABufferedImage(), getATransformation());
        return model;
    }

    /**
     * Returns a Model with 3 transformations done.
     */
    public static Model getModelWithThreeTransformations() {
        Model model = getModelWithTwoTransformations();
        model.updateCurrentPreviewImage(getABufferedImage(), getATransformation());
        return model;
    }

    /**
     * Returns a Model with current directory testimgs.
     */
    public static Model getModelWithTestImgDirectory() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.updateCurrDirectory(Paths.get(MainApp.MAIN_PATH + "/src/test/resources/testimgs"));
        return model;
    }
}
