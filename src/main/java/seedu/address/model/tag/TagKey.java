package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * A new tag key within our addressbook. Allows the user to dynamically assign
 * a key to a value based on user input.
 * TODO phase out the old tagging system with the new one.
 */
public class TagKey {
    public static final String MESSAGE_TAGVALUE_CONSTRAINTS = "Tag keys should be alphanumeric with no spaces.";
    public static final String TAGVALUE_VALIDATION_REGEX = "^[a-zA-Z0-9]*$";

    public final String tagKey;

    public TagKey() {
        this.tagKey = "";
    }

    public TagKey(String tagKey) {
        requireNonNull(tagKey);
        checkArgument(isValidTagKey(tagKey), MESSAGE_TAGVALUE_CONSTRAINTS);
        this.tagKey = tagKey;
    }

    public boolean isValidTagKey(String test) {
        return test.matches(TAGVALUE_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // Shortcircuit if the object comparing to is the same.
                || (other instanceof TagKey &&
                    tagKey.equals(((TagKey)other).tagKey));
    }

    @Override
    public int hashCode() {
        return tagKey.hashCode();
    }

    @Override
    public String toString() {
        return '[' + tagKey + ']';
    }
}
