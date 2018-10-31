package seedu.address.model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
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
     * Returns true if the model has previous PreviewImage states to restore.
     */
    boolean canUndoPreviewImage();

    /**
     * Returns true if the model has undone PreviewImage states to restore.
     */
    boolean canRedoPreviewImage();

    /**
     * Restores the model's PreviewImage to its previous state.
     */
    void undoPreviewImage();

    /**
     * Restores the model's PreviewImage to its previously undone state.
     */
    void redoPreviewImage();

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
    ArrayList<Path> getDirectoryImageList();

    /**
     * Updates the list of images with the current image list.
     */
    void updateImageList(ArrayList<Path> dirImageList);

    /**
     * Removes the image of the given index in the list.
     */
    void removeImageFromList(int idx);

    /**
     * Get preview image list (first 10 images in imageList)
     */
    List<Path> returnPreviewImageList();

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
     * @param transformation
     */
    void updateCurrentPreviewImage(BufferedImage image, Transformation transformation);

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
    void addTransformation(Transformation transformation) throws ParseException, InterruptedException, IOException;

    void addLayer(PreviewImage i, String name);

    void addLayer(PreviewImage i);

    void removeLayer(Index i);

    Canvas getCanvas();
}
