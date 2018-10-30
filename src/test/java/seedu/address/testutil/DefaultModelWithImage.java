package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PreviewImage;
import seedu.address.model.UserPrefs;
import seedu.address.model.transformation.TransformationSet;



/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class DefaultModelWithImage {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static BufferedImage originalImage = null;

    private DefaultModelWithImage() {

    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static Model getDefaultModelWithImage() {
        try {
            ClassLoader classLoader = DefaultModelWithImage.class.getClassLoader();
            originalImage = ImageIO.read(classLoader.getResource("testimgs/original.png"));
        } catch (IOException e) {
            logger.warning("Error getting default model" + e);
        }
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        PreviewImage previewImage = new PreviewImage(originalImage, new TransformationSet());
        model.setCurrentPreviewImage(previewImage);
        return model;
    }
}
