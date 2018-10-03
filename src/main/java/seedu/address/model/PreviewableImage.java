package seedu.address.model;

import javafx.scene.image.Image;
import seedu.address.model.transformation.TransformationSet;

//@author Ivan

/**
 * Unmodifiable view of a preview image.
 */
public interface PreviewableImage {

    /**
     * Returns an unmodifiable image.
     */
    Image getImage();

    /**
     * Returns an unmodifiable transformation set.
     */
    TransformationSet getTransformationSet();

}
