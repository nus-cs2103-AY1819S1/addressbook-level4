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

    public static final String OUTPUT_ERROR_CURRENT_LAYER = "You cannot remove the layer you're currently working on!";
    public static final String OUTPUT_ERROR_ONLY_LAYER = "You cannot remove the only layer in a canvas!";

    private static final String LAYER_NAME = "Layer %d";
    private String backgroundColor = "none";
    private ArrayList<Layer> layers = new ArrayList<>();
    private Layer currentLayer;
    private Index currentLayerIndex;
    private Boolean isCanvasAuto;
    private int layerNameCounts;
    private int height;
    private int width;

    /**
     * Constructor for a canvas that has the size of the initial image.
     * Auto-resizing of the canvas defaults to false.
     * @param initial - The image which the first layer will be created with. The canvas will take the height and width
     *                of this image.
     */
    public Canvas(PreviewImage initial) {
        height = initial.getImage().getHeight();
        width = initial.getImage().getWidth();
        addLayer(initial);
        layerNameCounts = 1;
        currentLayerIndex = Index.fromZeroBased(0);
        currentLayer = layers.get(currentLayerIndex.getZeroBased());
        isCanvasAuto = false;
    }

    //Layer operations

    public ArrayList<Layer> getLayers() {
        return layers;
    }

    /**
     * Returns the list of layer names as an {@code ArrayList<String>} for the LayerPanel.
     * * @return a {@code ArrayList<String>} of all the layers in the canvas in order.
     */

    public ArrayList<String> getLayerNames() {
        ArrayList<String> names = new ArrayList<>();
        int i = 1;
        for (Layer l : layers) {
            names.add(i + ": " + l.getName());
            i++;
        }
        return names;
    }

    public void addLayer(PreviewImage i, String name) {
        layers.add(new Layer(i, name));
    }

    /**
     * Adds a layer without a specified name. A non-unique name will be generated.
     * @param i - PreviewImage of the new Layer.
     */
    public void addLayer(PreviewImage i) {
        layerNameCounts++;
        layers.add(new Layer(i, String.format(LAYER_NAME, layerNameCounts)));
    }

    /**
     * Removes a layer from the canvas. If the only layer left is being removed,
     * throws an {@code IllegalOperationException}.
     * @param i - Index of the layer to remove.
     */

    public void setCurrentLayer(Index i) {
        currentLayerIndex = i;
        currentLayer = layers.get(currentLayerIndex.getZeroBased());
    }

    /**
     * Removes a layer from the canvas. If the only layer left is being remove, throws an IllegalOperationException.
     * @param i - Index of the layer to remove
     */

    public Index removeLayer(Index i) throws IllegalOperationException {
        if (layers.size() <= 1) {
            throw new IllegalOperationException(OUTPUT_ERROR_ONLY_LAYER);
        }
        if (i.getZeroBased() == currentLayerIndex.getZeroBased()) {
            throw new IllegalOperationException(OUTPUT_ERROR_CURRENT_LAYER);
        }
        layers.remove(i.getZeroBased());
        if (i.getZeroBased() < currentLayerIndex.getZeroBased()) {
            currentLayerIndex = Index.fromZeroBased(currentLayerIndex.getZeroBased() - 1);
        }
        return currentLayerIndex;
    }


    /**
     * Function to swap two layers if neither of them are locked.
     * Throws an {@code IndexOutOfBounds}  if indexes provided are not valid.
     * Throws an {@code IllegalOperationException} if any of the layers are locked.
     * @param to A zero-based index within bounds
     * @param from A zero-based index within bounds
     */
    public void swapLayer(Index to, Index from) throws IllegalOperationException {
        if (!to.equals(from)) {
            if (currentLayerIndex.getZeroBased() == to.getZeroBased()) {
                currentLayerIndex = Index.fromZeroBased(from.getZeroBased());
            } else if (currentLayerIndex.getZeroBased() == from.getZeroBased()) {
                currentLayerIndex = Index.fromZeroBased(to.getZeroBased());
            }

            Collections.swap(layers, to.getZeroBased(), from.getZeroBased());
        } else {
            throw new IllegalOperationException("Invalid indexes provided!");
        }
    }

    // Misc accessors
    public Layer getCurrentLayer() {
        return currentLayer;
    }

    public void setCurrentLayerPosition(int x, int y) {
        currentLayer.setPosition(x, y);
    }

    public Index getCurrentLayerIndex() {
        return currentLayerIndex;
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
