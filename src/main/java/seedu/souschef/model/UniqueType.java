package seedu.souschef.model;

/**
 * Type to be stored in UniqueList
 */
public abstract class UniqueType {
    public abstract boolean isSame(UniqueType uniqueType);

    @Override
    public abstract boolean equals(Object o);
}
