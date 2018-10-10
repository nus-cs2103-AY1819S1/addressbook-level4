package seedu.address.model.transformation;

//@author Jeffry

import java.util.Arrays;
import java.util.List;

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

    //@author tianyang
    /**
     * print out the transformation
     * @return
     */
    @Override
    public String toString() {
        String result = operation;
        for (int i = 0; i < args.length; i++) {
            result += "\n -" + args[i];
        }
        return result; //whoever is in charge do this
    }

    //@author tianyang
    /**
     * return a list of String that contains all the arguments and the operation
     * @return
     */
    public List<String> toList() {
        List<String> list = Arrays.asList(args);
        list.add(0, operation);
        return list;
    }
}
