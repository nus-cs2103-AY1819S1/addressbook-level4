package seedu.address.model.transformation;

//@@uthor j-lum
/**
 * Represents a single transformation to a single layer in a canvas.
 */
public class Transformation {

    private String operation;
    private String[] args;

    public Transformation(String operation, String... args) {
        this.operation = operation;
        this.args = args;
    }

    //@@author lancelotwillow
    @Override
    public String toString() {
        return super.toString(); //whoever is in charge do this
    }
}
