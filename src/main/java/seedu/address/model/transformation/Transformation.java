package seedu.address.model.transformation;

//@@uthor j-lum

import java.util.ArrayList;
import java.util.Arrays;

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
        String result = operation;
        for (int i = 0; i < args.length; i++) {
            result += " " + args[i];
        }
        return result; //whoever is in charge do this
    }

    //@@author lancelotwillow
    /**
     * return a list of String that contains all the arguments and the operation
     * @return
     */
    public ArrayList<String> toList() {
        ArrayList<String> list = new ArrayList<>();
        list.add(operation);
        list.addAll(Arrays.asList(args));
        return list;
    }

    public String getOperation() {
        return operation;
    }

    @Override
    public boolean equals(Object object) {
        Transformation transformation = (Transformation) object;
        if (transformation == this) {
            return true;
        } else {
            return operation.equals(transformation.operation)
                    && Arrays.equals(args, transformation.args);
        }
    }
}
