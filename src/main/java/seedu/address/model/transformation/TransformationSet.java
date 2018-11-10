package seedu.address.model.transformation;

import java.util.LinkedList;

import seedu.address.commons.core.index.Index;

//@@author j-lum

/**
 * Represents a set of transformations that can be applied to any layer.
 */
public class TransformationSet {
    private LinkedList<Transformation> transformations;
    private Index cursor;

    /**
     * Default constructor.
     */
    public TransformationSet() {
        transformations = new LinkedList<>();
        cursor = Index.fromZeroBased(0);
    }

    /**
     * @param t
     * Adds transformations to transformation set
     */
    public void addTransformations(Transformation t) {
        transformations.addLast(t);
        cursor = Index.fromOneBased(transformations.size());
    }

    public LinkedList<Transformation> getTransformations() {
        return transformations;
    }

}
