package seedu.address.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.transformation.Transformation;
import seedu.address.model.transformation.TransformationSet;

//@@author ihwk1996

/**
 * Wraps the image and transformation set for preview.
 */
public class PreviewImage {

    private static final String CACHE_PATH;
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final TransformationSet transformationSet;
    private int height;
    private int width;
    private int currentIndex;
    private int currentSize; // Number of saved images
    private long layerId;

    static {
        File cache = new File("cache");
        cache.mkdir();
        CACHE_PATH = cache.getPath();
    }

    public PreviewImage(BufferedImage image) {
        this.layerId = System.currentTimeMillis();
        this.currentSize = 0;
        this.currentIndex = -1;
        this.height = image.getHeight();
        this.width = image.getWidth();
        commit(image);
        this.transformationSet = new TransformationSet();
    }

    public PreviewImage(BufferedImage image, TransformationSet transformationSet) {
        this.layerId = System.currentTimeMillis();
        this.currentSize = 0;
        this.currentIndex = -1;
        this.height = image.getHeight();
        this.width = image.getWidth();
        commit(image);
        this.transformationSet = transformationSet;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    /**
     * Check if have previous states to undo.
     */
    public boolean canUndo() {
        return currentIndex > 0;
    }

    /**
     * Check if have previous undone states to redo.
     */
    public boolean canRedo() {
        return currentIndex < currentSize - 1;
    }

    /**
     * Decrement current index if able to undo.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentIndex--;
    }

    /**
     * Increment current index if able to redo.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentIndex++;
    }

    /**
     * Decrement current index if able to undo.
     */
    public void undoAll() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentIndex = 0;
    }

    /**
     * Increment current index if able to redo.
     */
    public void redoAll() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentIndex = currentSize - 1;
    }

    /**
     * Determine if history needs to be purged before committing.
     */
    public void commit(BufferedImage image) {
        if (currentIndex == currentSize - 1) {
            normalCommit(image);
        } else {
            purgeAndCommit(image);
        }
    }

    /**
     * Increment size and current index, then cache the image.
     */
    private void normalCommit(BufferedImage image) {
        try {
            currentSize++;
            currentIndex++;
            File out = new File(CACHE_PATH + "/Layer" + layerId + "-" + currentIndex + ".png");
            ImageIO.write(image, "png", out);
        } catch (IOException e) {
            logger.warning("Exception while caching :" + e.getMessage());
        }
        logger.info("Caching successful");
    }

    /**
     * Purge redundant images, then do a normal commit.
     */
    private void purgeAndCommit(BufferedImage image) {
        int numDeleted = 0;
        for (int i = currentIndex + 1; i < currentSize; i++) {
            File toDelete = new File(CACHE_PATH + "/Layer" + layerId + "-" + i + ".png");
            toDelete.delete();
            numDeleted++;
        }
        // Reduce the current size depending on the number of images deleted.
        currentSize = currentSize - numDeleted;

        normalCommit(image);
    }

    /**
     * Get the current image state from cache.
     */
    public BufferedImage getImage() {
        BufferedImage imageFromCache = null;
        try {
            File in = new File(CACHE_PATH + "/Layer" + layerId + "-" + currentIndex + ".png");
            imageFromCache = ImageIO.read(in);
        } catch (IOException e) {
            logger.warning("Error reading from cache.");
        }
        logger.info("Reading from cache successful.");
        return imageFromCache;
    }

    /**
     * Get the current image path from cache.
     */
    public Path getCurrentPath() {
        File f = new File(CACHE_PATH + "/Layer" + layerId + "-" + currentIndex + ".png");
        return f.toPath();
    }

    public TransformationSet getTransformationSet() {
        return transformationSet;
    }

    public void addTransformation(Transformation t) {
        transformationSet.addTransformations(t);
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of previewImageState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of previewImageState list, unable to redo.");
        }
    }

    public ArrayList<String> getTransformationsAsString() {
        ArrayList<String> output = new ArrayList<>();
        LinkedList<Transformation> sub =
                new LinkedList<> (transformationSet.getTransformations().subList(0, currentIndex));
        for (Transformation t : sub) {
            output.add(t.toString());
        }
        return output;
    }
}
