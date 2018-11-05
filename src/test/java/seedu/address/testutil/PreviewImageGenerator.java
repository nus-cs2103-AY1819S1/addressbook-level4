package seedu.address.testutil;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ModelManager;
import seedu.address.model.PreviewImage;
import seedu.address.model.transformation.Transformation;
import seedu.address.model.transformation.TransformationSet;

//@@author ihwk1996

/**
 * A utility class to generate PreviewImages for testing.
 */
public class PreviewImageGenerator {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private PreviewImageGenerator() {
    } // prevents instantiation

    /**
     * Returns a dummy image read from resources (original.png).
     */
    public static BufferedImage getABufferedImage() {
        BufferedImage originalImage = null;
        try {
            ClassLoader classLoader = PreviewImageGenerator.class.getClassLoader();
            originalImage = ImageIO.read(classLoader.getResource("testimgs/original.png"));
        } catch (IOException e) {
            logger.warning("Error getting default model" + e);
        }
        return originalImage;
    }

    /**
     * Returns a dummy image read from resources (original.png).
     */
    public static PreviewImage getPreviewImage(String fileName) {
        BufferedImage image = null;
        try {
            ClassLoader classLoader = PreviewImageGenerator.class.getClassLoader();
            image = ImageIO.read(classLoader.getResource("testimgs/" + fileName));
        } catch (IOException e) {
            logger.warning("Error getting default model" + e);
        }
        return new PreviewImage(image);
    }

    /**
     * Returns a dummy transformation (blur).
     */
    public static Transformation getATransformation() {
        return new Transformation("blur", "0x8");
    }

    /**
     * Executes a transformation on a previewImage.
     */
    public static PreviewImage executeATransformation(PreviewImage previewImage) {
        previewImage.commit(getABufferedImage());
        return previewImage;
    }

    /**
     * Returns a default initialised PreviewImage.
     */
    public static PreviewImage getDefaultPreviewImage() {
        BufferedImage image = getABufferedImage();
        return new PreviewImage(image, new TransformationSet());
    }

    /**
     * Returns a default initialised PreviewImage with its secondary constructor.
     */
    public static PreviewImage getDefaultPreviewImageWithSecondaryConstructor() {
        BufferedImage image = getABufferedImage();
        return new PreviewImage(image);
    }

    /**
     * Returns a PreviewImage with 1 transformation done.
     */
    public static PreviewImage getPreviewImageWithOneTransformation() {
        PreviewImage previewImage = getDefaultPreviewImage();
        previewImage.commit(getABufferedImage());
        return previewImage;
    }

    /**
     * Returns a PreviewImage with 2 transformations done.
     */
    public static PreviewImage getPreviewImageWithTwoTransformations() {
        PreviewImage previewImage = getPreviewImageWithOneTransformation();
        previewImage.commit(getABufferedImage());
        return previewImage;
    }

    /**
     * Returns a PreviewImage with 3 transformations done.
     */
    public static PreviewImage getPreviewImageWithThreeTransformations() {
        PreviewImage previewImage = getPreviewImageWithTwoTransformations();
        previewImage.commit(getABufferedImage());
        return previewImage;
    }

    /**
     * Returns a PreviewImage at index 0, size 4 (3 undone states).
     */
    public static PreviewImage getPreviewImageWithUndoneStates() {
        PreviewImage previewImage = getPreviewImageWithThreeTransformations();
        previewImage.undo();
        previewImage.undo();
        previewImage.undo();
        return previewImage;
    }
}
