package seedu.address.model;

import static seedu.address.commons.core.Messages.MESSAGE_CONNECTION_FAILURE;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.google.PhotosLibraryClientFactory.BLOCKER;
import static seedu.address.model.google.PhotosLibraryClientFactory.DATA_STORE;
import static seedu.address.model.google.PhotosLibraryClientFactory.TEST_FILE;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import com.oracle.tools.packager.UnsupportedPlatformException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ChangeDirectoryEvent;
import seedu.address.commons.events.ui.HistoryUpdateEvent;
import seedu.address.commons.events.ui.LayerUpdateEvent;
import seedu.address.commons.events.ui.UpdateFilmReelEvent;
import seedu.address.commons.exceptions.IllegalOperationException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.canvas.Canvas;
import seedu.address.model.google.PhotoHandler;
import seedu.address.model.google.PhotosLibraryClientFactory;
import seedu.address.model.transformation.Transformation;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private List<Path> dirImageList;
    private Path currentOriginalImage;
    private PhotoHandler photoLibrary;
    private Canvas canvas;

    private final UserPrefs userPrefs;

    /**
     * Strictly for test mode. Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(UserPrefs userPrefs, boolean isTest) {
        super();
        requireAllNonNull(userPrefs);

        logger.fine("Initializing with user prefs " + userPrefs);

        this.userPrefs = userPrefs;
        this.userPrefs.initImageList();
        dirImageList = this.userPrefs.getCurrImageListBatch();

        setUpForGoogle(isTest);
    }

    public ModelManager() {
        this(new UserPrefs(), true);
    }

    //=========== Directory Image List Accessors =============================================================
    // @@author benedictcss
    /**
     * Returns an array list of the images from the current directory {@code dirImageList}
     * backed by the list of {@code userPrefs}
     */
    @Override
    public List<Path> getDirectoryImageList() {
        this.dirImageList = userPrefs.getCurrImageListBatch();
        return this.dirImageList;
    }

    /**
     * Returns the total number of images in current directory
     */
    @Override
    public int getTotalImagesInDir() {
        return userPrefs.getTotalImagesInDir();
    }

    /**
     * Returns the current number of remaining pictures in {@code UserPrefs}
     */
    @Override
    public int numOfRemainingImagesInDir() {
        return userPrefs.numOfRemainingImagesInDir();
    }

    /**
     * Returns the current batch pointer in {@code UserPrefs}
     */
    @Override
    public int getCurrBatchPointer() {
        return userPrefs.getCurrBatchPointer();
    }

    /**
     * Update entire image list in {@code UserPrefs}
     */
    @Override
    public void updateEntireImageList() {
        userPrefs.initImageList();
        EventsCenter.getInstance().post(new UpdateFilmReelEvent(getDirectoryImageList()));
    }

    /**
     * Updates the batch pointer in {@code UserPrefs}
     */
    @Override
    public void updateImageListNextBatch() {
        userPrefs.updateImageListNextBatch();
        EventsCenter.getInstance().post(new UpdateFilmReelEvent(getDirectoryImageList()));
    }

    /**
     * Updates the batch pointer in {@code UserPrefs}
     */
    public void updateImageListPrevBatch() {
        userPrefs.updateImageListPrevBatch();
        EventsCenter.getInstance().post(new UpdateFilmReelEvent(getDirectoryImageList()));
    }

    /**
     * Remove image from {@code dirImageList} at the given {@code idx}
     */
    @Override
    public void removeImageFromList(int idx) {
        this.dirImageList.remove(idx);
    }

    @Override
    public Path getCurrentOriginalImage() {
        return this.currentOriginalImage;
    }

    /**
     * Update the current displayed original image and
     * reinitialize the previewImageManager with the new image
     */
    @Override
    public void updateCurrentOriginalImage(Image img, Path imgPath) {
        currentOriginalImage = imgPath;
        PreviewImage selectedImage = new PreviewImage(SwingFXUtils.fromFXImage(img, null));
        canvas = new Canvas(selectedImage);

        refreshHistoryList();
        refreshLayerList();
    }

    /**
     * Update the current displayed original image and
     * reinitialize the previewImageManager with the new image, without imgPath
     */
    @Override
    public void updateCurrentOriginalImageForTest(PreviewImage previewImage) {
        canvas = new Canvas(previewImage);
    }
    //@@author

    //=========== GoogleClient Accessors =============================================================
    // @@author chivent
    @Override
    public void setPhotoHandler(PhotoHandler instance) {
        photoLibrary = instance;
    }

    @Override
    public PhotoHandler getPhotoHandler() throws CommandException {
        if (photoLibrary == null) {
            try {
                photoLibrary = PhotosLibraryClientFactory.createClient();
                if (photoLibrary == null) {
                    throw new CommandException(MESSAGE_CONNECTION_FAILURE);
                }
            } catch (Exception e) {
                throw new CommandException(MESSAGE_CONNECTION_FAILURE);
            }
        }
        return photoLibrary;
    }

    @Override
    public String getUserLoggedIn () throws CommandException {
        if (photoLibrary == null) {
            return null;
        }
        return getPhotoHandler().identifyUser();
    }

    @Override
    public void setUpForGoogle(boolean isTest) {
        if (isTest) {
            FileUtil.createDirectoriesIfMissing(DATA_STORE);
            try {
                FileUtil.createIfMissing(TEST_FILE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            FileUtil.deleteIfAvaliable(TEST_FILE);
            if (!BLOCKER.exists()) {
                try {
                    photoLibrary = PhotosLibraryClientFactory.loginUserIfPossible();
                } catch (Exception e) {
                    logger.warning("Unable to log into user account");
                }
            }
        }
    }

    //=========== Undo/Redo =================================================================================
    // @@author ihwk1996
    @Override
    public boolean canUndoPreviewImage() {
        return getCurrentPreviewImage().canUndo();
    }

    @Override
    public boolean canRedoPreviewImage() {
        return getCurrentPreviewImage().canRedo();
    }

    @Override
    public void undoPreviewImage() {
        getCurrentPreviewImage().undo();
        refreshHistoryList();
    }

    @Override
    public void redoPreviewImage() {
        getCurrentPreviewImage().redo();
        refreshHistoryList();
    }

    @Override
    public void undoAllPreviewImage() {
        getCurrentPreviewImage().undoAll();
        refreshHistoryList();
    }

    @Override
    public void redoAllPreviewImage() {
        getCurrentPreviewImage().redoAll();
        refreshHistoryList();
    }

    //=========== get/updating preview image ==========================================================================

    /**
     * Adds a transformation to current layer
     * @param transformation transformation to add
     * @throws ParseException
     * @throws InterruptedException
     * @throws IOException
     */
    public void addTransformation(Transformation transformation) {
        canvas.getCurrentLayer().addTransformation(transformation);
        refreshHistoryList();
    }

    @Override
    public PreviewImage getCurrentPreviewImage() {
        return canvas.getCurrentLayer().getImage();
    }

    @Override
    public void setCurrentPreviewImage(PreviewImage previewImage) {
        setCurrentPreviewImage(previewImage);
    }

    @Override
    public Path getCurrentPreviewImagePath() {
        return getCurrentPreviewImage().getCurrentPath();
    }

    //@@author lancelotwillow
    @Override
    public void updateCurrentPreviewImage(BufferedImage image) {
        try {
            getCanvas().getCurrentLayer().getImage().commit(image);
            refreshHistoryList();
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return userPrefs.equals(other.userPrefs);
    }

    //=========== Update UserPrefs ==========================================================================

    // @@author benedictcss
    @Override
    public void updateCurrDirectory(Path newCurrDirectory) {
        this.userPrefs.updateUserPrefs(newCurrDirectory);
        EventsCenter.getInstance().post(new ChangeDirectoryEvent(getCurrDirectory().toString()));
        EventsCenter.getInstance().post(new UpdateFilmReelEvent(getDirectoryImageList()));
    }

    @Override
    public Path getCurrDirectory() {
        return this.userPrefs.getCurrDirectory();
    }

    //=========== Canvas and layers ==========================================================================

    /**
     * Adds a layer to the canvas, a name is generated automatically.
     * @param i - PreviewImage to add.
     */
    public void addLayer(PreviewImage i) {
        canvas.addLayer(i);
        refreshLayerList();
    }

    /**
     * Overloads the addLayer function to handle an optional name.
     * @param i
     * @param name
     */
    public void addLayer(PreviewImage i, String name) {
        canvas.addLayer(i, name);
        refreshLayerList();
    }

    /**
     * Removes the layer at the given index.
     * @param i - Index of the layer to remove
     * @return
     * @throws IllegalOperationException - thrown if the current layer is being removed
     * or the only layer is being removed.
     */

    public Index removeLayer(Index i) throws IllegalOperationException {
        Index tmp = canvas.removeLayer(i);
        logger.info("refreshing after deleting layers");
        refreshLayerList();
        return tmp;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void saveCanvas(String fileName) throws IOException, InterruptedException, UnsupportedPlatformException {
        ImageMagickUtil.saveCanvas(canvas, userPrefs.getCurrDirectory(), fileName);
    }

    public void setCurrentLayer(Index i) {
        getCanvas().setCurrentLayer(i);
        refreshLayerList();
    }

    /**
     * Swaps two layers.
     * @param to - Layer 1
     * @param from - Layer 2.
     * @throws IllegalOperationException - thrown when the two layers are the same.
     */
    public void swapLayer(Index to, Index from) throws IllegalOperationException {
        getCanvas().swapLayer(to, from);
        refreshLayerList();
    }

    private void refreshHistoryList() {
        EventsCenter.getInstance().post(
                new HistoryUpdateEvent(
                        getCanvas().getCurrentLayer().getImage().getTransformationsAsString()));
    }

    private void refreshLayerList() {
        EventsCenter.getInstance().post(
                new LayerUpdateEvent(
                        getCanvas().getLayerNames(), getCanvas().getCurrentLayerIndex()));
    }
}
