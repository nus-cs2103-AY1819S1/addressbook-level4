package seedu.address.model.canvas;

import java.util.ArrayList;

import javafx.scene.image.Image;

//@author Jeffry
/**
 * Represents a working canvas.
 * Guarantees that there is at least one layer remaining on the canvas
 * and that the canvas area must be larger than zero.
 */
public class Canvas {

    private ArrayList<Layer> layers = new ArrayList<>();

    public Canvas(Image base){

    }
}
