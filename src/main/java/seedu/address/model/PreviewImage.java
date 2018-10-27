package seedu.address.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

import seedu.address.MainApp;
import seedu.address.model.transformation.Transformation;
import seedu.address.model.transformation.TransformationSet;


//@@author ihwk1996

/**
 * Wraps the image and transformation set for preview.
 */
public class PreviewImage {
    private static final String TESTPATH = MainApp.MAIN_PATH + "/src/main/java/seedu/address/storage/cache";
    private final TransformationSet transformationSet;
    private int height;
    private int width;
    private int currentIndex;
    private int currentSize; // num of saved images

    public PreviewImage(BufferedImage image) {
        this.currentSize = 0;
        this.currentIndex = -1;
        this.height = image.getHeight();
        this.width = image.getWidth();
        commit(image);
        this.transformationSet = new TransformationSet();
    }

    public PreviewImage(BufferedImage image, TransformationSet transformationSet) {
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
     * Increment size and current index, then cache the image.
     */
    public void commit(BufferedImage image) {
        try {
            currentSize++;
            currentIndex++;
            File out = new File(TESTPATH + "/Layer0-" + currentIndex + ".png");
            ImageIO.write(image, "png", out);
        } catch (IOException e) {
            System.out.println("Exception occ :" + e.getMessage());
        }
        System.out.println("Caching successful");
    }

    /**
     * Get the current image state from cache.
     */
    public BufferedImage getImage() {
        BufferedImage imageFromCache = null;
        try {
            File in = new File(TESTPATH + "/Layer0-" + currentIndex + ".png");
            imageFromCache = ImageIO.read(in);
        } catch (IOException e) {
            System.out.println("Exception occ :" + e.getMessage());
        }
        System.out.println("Retrieve successful");
        return imageFromCache;
    }

    /**
     * Get the current image path from cache.
     */
    public Path getCurrentPath() {
        File f = new File(TESTPATH + "/Layer0-" + currentIndex + ".png");
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
}
