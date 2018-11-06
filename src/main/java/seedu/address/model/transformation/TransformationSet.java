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
     *
     */
    public TransformationSet(LinkedList<Transformation> transformations) {
        this.transformations = transformations;
        cursor = Index.fromOneBased(transformations.size());
    }

    /**
     * @param t
     * Adds transformations to transformation set
     */
    public void addTransformations(Transformation t) {
        transformations.addLast(t);
        cursor = Index.fromOneBased(transformations.size());
    }

    /**
     * @param steps
     * Removes transformations from transformation set
     */
    public void removeTransformations(int steps) {
        if (steps > transformations.size()) {
            throw new IndexOutOfBoundsException("TODO: refactor message somewhere else");
        }
        for (int i = 0; i < steps; i++) {
            transformations.pollLast();
        }
        cursor = Index.fromOneBased(transformations.size());
    }

    public LinkedList<Transformation> getTransformations() {
        return transformations;
    }

}
