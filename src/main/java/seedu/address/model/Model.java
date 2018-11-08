package seedu.address.model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.oracle.tools.packager.UnsupportedPlatformException;
import javafx.scene.image.Image;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalOperationException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.canvas.Canvas;
import seedu.address.model.google.PhotoHandler;
import seedu.address.model.transformation.Transformation;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * Get PhotoHandler, directs user to login if yet to be logged in.
     */
    PhotoHandler getPhotoHandler() throws CommandException;

    /**
     * Set PhotoHandler.
     */
    void setPhotoHandler(PhotoHandler instance);

    /**
     * Gets currently logged in user
     */
    String getUserLoggedIn() throws CommandException;

    /**
     * Performs necessary set up for current model (test or actual)
     */
    void setUpForGoogle(boolean isTest);

    /**
     * Returns true if the current layer's PreviewImage has undone states to restore.
     */
    boolean canUndoPreviewImage();

    /**
     * Returns true if the current layer's PreviewImage states to restore.
     */
    boolean canRedoPreviewImage();

    /**
     * Undoes one state in the current layer's previewImage.
     */
    void undoPreviewImage();

    /**
     * Redoes one state in the current layer's previewImage.
     */
    void redoPreviewImage();

    /**
     * Undoes all states in the current layer's previewImage.
     */
    void undoAllPreviewImage();

    /**
     * Redoes all states in the current layer's previewImage.
     */
    void redoAllPreviewImage();

    /**
     * Updates the userPrefs current directory.
     */
    void updateCurrDirectory(Path newCurrDirectory);

    /**
     * Retrieves the userPrefs current directory.
     */
    Path getCurrDirectory();

    /**
     * Retrieves the list of images in current directory.
     */
    List<Path> getDirectoryImageList();

    /**
     * Returns the total number of images in current directory
     */
    int getTotalImagesInDir();

    /**
     * Returns the current batch pointer
     */
    int numOfRemainingImagesInDir();

    /**
     * Returns the current batch pointer in {@code UserPrefs}
     */
    int getCurrBatchPointer();

    /**
     * Updates entire image list.
     */
    void updateEntireImageList();

    /**
     * Updates the batch pointer to the next 10 images.
     */
    void updateImageListNextBatch();

    /**
     * Updates the batch pointer to the previous 10 images.
     */
    void updateImageListPrevBatch();

    /**
     * Removes the image of the given index in the list.
     */
    void removeImageFromList(int idx);

    /**
     * Retrieves the current displayed original image.
     */
    Path getCurrentOriginalImage();

    /**
     * Retrieves the current displayed preview image.
     */
    PreviewImage getCurrentPreviewImage();

    /**
     * Sets the current model's preview image.
     */
    void setCurrentPreviewImage(PreviewImage previewImage);

    /**
     * Retrieves the current displayed preview image.
     */
    Path getCurrentPreviewImagePath();

    /**
     * update the preview image stored in the model
     * @param image
     */
    void updateCurrentPreviewImage(BufferedImage image);

    /**
     * Update the current displayed original image.
     * @param img
     * @param imgPath
     */
    void updateCurrentOriginalImage(Image img, Path imgPath);

    /**
     * Update the current displayed original image for test.
     * @param previewImage
     */
    void updateCurrentOriginalImageForTest(PreviewImage previewImage);

    /**
     * update the transformationSet of the current image
     * @param transformation
     */
    void addTransformation(Transformation transformation);

    void addLayer(PreviewImage i, String name);

    void addLayer(PreviewImage i);

    Index removeLayer(Index i) throws IllegalOperationException;

    void setCurrentLayer(Index i);

    void swapLayer(Index to, Index from) throws IllegalOperationException;

    Canvas getCanvas();

    void saveCanvas(String fileName) throws IOException, InterruptedException, UnsupportedPlatformException;

}
