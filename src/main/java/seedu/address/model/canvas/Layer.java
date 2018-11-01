package seedu.address.model.canvas;

//@@author j-lum
import static java.util.Objects.requireNonNull;

import java.io.IOException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.PreviewImage;
import seedu.address.model.transformation.Transformation;

/**
 * Represents a layer in a canvas.
 * Guarantees that the image in the layer is not null.
 */

public class Layer {

    private final PreviewImage image;
    private int x;
    private int y;
    private int height;
    private int width;
    private String name;
    private Boolean isLocked;

    public Layer(PreviewImage image, String name) {
        this.image = requireNonNull(image);
        this.x = 0;
        this.y = 0;
        this.height = image.getHeight();
        this.width = image.getWidth();
        this.name = name;

        isLocked = false;
    }

    /**
     * .
     * @param t
     */
    public void addTransformation(Transformation t) throws ParseException, InterruptedException, IOException {
        image.addTransformation(t);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void lock() {
        isLocked = true;
    }

    public void unlock() {
        isLocked = false;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public PreviewImage getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        //return String.format("-page %dx%d+%d+%d %s", height, width, x, y, image.getUrl());
        // TODO : keep track of the path in PreviewImage
        return "";
    }
}
