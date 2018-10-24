package seedu.address.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import seedu.address.MainApp;
import seedu.address.model.transformation.Transformation;
import seedu.address.model.transformation.TransformationSet;


//@author Ivan

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

    public boolean canUndo() {
        return currentIndex > 0;
    }

    public boolean canRedo() {
        return currentIndex < currentSize - 1;
    }

    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentIndex--;
    }

    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentIndex++;
    }

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
