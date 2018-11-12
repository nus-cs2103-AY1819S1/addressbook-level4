package seedu.address.model;

import java.util.UUID;

/**
 * Represents the Objects that need an ID for storage & reference
 */
public abstract class TaggedObject {
    public static final String MESSAGE_INVALID_ID = "Not an valid ID! Please check XML File.";

    private UUID tag;

    protected TaggedObject() {
        tag = null;
    }

    protected TaggedObject(UUID id) {
        this.tag = id;
    }

    public UUID getTag() {
        return tag;
    }

    public void assignTag() {
        tag = UUID.randomUUID();
    }

    public boolean hasSameTag(TaggedObject other) {
        return getTag().equals(other.getTag());
    }
}
