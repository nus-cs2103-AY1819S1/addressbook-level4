package seedu.address.model.canvas;

//@@author j-lum
import static java.util.Objects.requireNonNull;

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

    public Layer(PreviewImage image, String name) {
        this.image = requireNonNull(image);
        this.x = 0;
        this.y = 0;
        this.height = image.getHeight();
        this.width = image.getWidth();
        this.name = name;
    }

    /**
     * Adds a transformation to the layer.
     * @param  t - the transformation to add.
     */
    public void addTransformation(Transformation t) {
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

    public PreviewImage getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
