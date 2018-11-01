package seedu.address.model.canvas;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalOperationException;
import seedu.address.model.PreviewImage;


//@@author j-lum
/**
 * Represents a working canvas.
 * Guarantees that there is at least one layer remaining on the canvas
 * and that the canvas area must be larger than zero.
 */
public class Canvas {
    private static final String LAYER_NAME = "Layer %d";
    private String backgroundColor = "none";
    private ArrayList<Layer> layers = new ArrayList<>();
    private Layer currentLayer;
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
    public Canvas(PreviewImage initial) {
        height = initial.getImage().getHeight();
        width = initial.getImage().getHeight();
        addLayer(initial);
        currentLayer = layers.get(0);
        isCanvasAuto = false;
    }

    //Layer operations

    public ArrayList<Layer> getLayers() {
        return layers;
    }

    public void addLayer(PreviewImage i, String name) {
        layers.add(new Layer(i, name));
    }

    public void addLayer(PreviewImage i) {
        layers.add(new Layer(i, String.format(LAYER_NAME, layers.size())));
    }

    public Layer getCurrentLayer() {
        return currentLayer;
    }

    public void setCurrentLayer(Index i) {
        currentLayer = layers.get(i.getOneBased());
    }

    /**
     * Removes a layer from the canvas. If the only layer left is being remove, throws an IllegalOperationException.
     * @param i
     */

    public void removeLayer(Index i) {
        if (layers.size() <= 1) {
            new IllegalOperationException("You cannot remove the only layer in a canvas!");
        }
        layers.remove(i.getZeroBased());
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

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String color) {
        backgroundColor = color;
    }

}
