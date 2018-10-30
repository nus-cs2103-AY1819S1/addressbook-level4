package seedu.address.model.canvas;

import java.util.ArrayList;

import seedu.address.model.PreviewImage;


//@@author j-lum
/**
 * Represents a working canvas.
 * Guarantees that there is at least one layer remaining on the canvas
 * and that the canvas area must be larger than zero.
 */
public class Canvas {
    private static final String LAYER_NAME = "Layer %d";

    private ArrayList<Layer> layers = new ArrayList<>();

    private Boolean isCanvasAuto;
    private int height;
    private int width;

    public Canvas() {
        isCanvasAuto = true;
    }

    /**
     * Constructor for a canvas that has the size of the initial image.
     * Auto-resizing of the canvas defaults to false.
     * * @param initial
     */
    public Canvas(Layer initial){
        height = initial.getImage().getImage().getHeight();
        width = initial.getImage().getImage().getHeight();
        isCanvasAuto = false;
    }

    //Layer operations

    public void addLayer(Layer l) {
        layers.add(l);
    }

    public void addLayer(PreviewImage i, String name) {
        layers.add(new Layer(i, String.format(LAYER_NAME, layers.size())));
    }

    /**
     * Function to swap two layers if neither of them are locked.
     * Throws an {@code IndexOutOfBounds}  if indexes provided are not valid.
     * Throws an {@code IllegalOperationException} if any of the layers are locked.
     * @param to A zero-based index within bounds
     * @param from A zero-based index within bounds
     */
    public void swapLayer(int to, int from) throws IllegalOperationException {
        if (!layers.get(to).isLocked() && !layers.get(from).isLocked()) {
            Collections.swap(layers, to, from);
        } else {
            throw new IllegalOperationException("One or more layers are locked!");
        }
    }

    // Canvas operations

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

    public Boolean isCanvasAuto() {
        return isCanvasAuto;
    }

    public void setCanvasAuto(Boolean isCanvasAuto) {
        this.isCanvasAuto = isCanvasAuto;
    }
}
