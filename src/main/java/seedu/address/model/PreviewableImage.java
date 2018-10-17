package seedu.address.model;

import java.awt.image.BufferedImage;
import seedu.address.model.transformation.TransformationSet;

//@author Ivan

/**
 * Unmodifiable view of a preview image.
 */
public interface PreviewableImage {

    /**
     * Returns an unmodifiable image.
     */
    BufferedImage getImage();

    /**
     * Returns an unmodifiable transformation set.
     */
    TransformationSet getTransformationSet();

}
