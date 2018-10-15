package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * A new tag value within our address book. Represents a dynamic value that can
 * be associated with a key based on user input.
 * TODO: to be phased into the new addressbook, replacing the old tagging system.
 */
public class TagValue {
    public static final String MESSAGE_TAGVALUE_CONSTRAINTS = "Tag values should be alphanumeric.";
    public static final String TAGVALUE_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String tagValue;

    public TagValue(String tagValue) {
        requireNonNull(tagValue);
        checkArgument(isValidTagValue(tagValue), MESSAGE_TAGVALUE_CONSTRAINTS);
        this.tagValue = tagValue;
    }

    public boolean isValidTagValue(String test) {
        return test.matches(TAGVALUE_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                ||
                (other instanceof TagValue &&
                 tagValue.equals(((TagValue)other).tagValue));
    }

    @Override
    public int hashCode() {
        return tagValue.hashCode();
    }

    @Override
    public String toString() {
        return '[' + tagValue + ']';
    }
}
