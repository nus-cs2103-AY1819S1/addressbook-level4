package ssp.scheduleplanner.model.tag;

import static java.util.Objects.requireNonNull;
import static ssp.scheduleplanner.commons.util.AppUtil.checkArgument;


/**
 * Represents a Tag in the Schedule Planner.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric\n"
            + "Please add t/ in front of each tag name! \n"
            + "Example: t/Game t/Steam t/Overwatch";
    public static final String MESSAGE_TAG_NONEXISTENT = "Tag not found. Please add tag first.";
    public static final String TAG_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_TAG_CONSTRAINTS);
        this.tagName = tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(TAG_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && tagName.equals(((Tag) other).tagName)); // state check
    }

    /**
     * @param other
     * @return true if two tags have the same name.
     */
    public boolean isSameTag(Tag other) {
        if (other == this) {
            return true;
        }
        return other != null
                && this.equals(other);

    }

    /**
     * Getter method for the Tag's name in String format
     * @return String representation of Tag's name
     */
    public String getTagName() {
        return this.tagName;
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
