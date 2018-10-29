package seedu.address.model.topic;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a topic in 3VIA.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTopicName(String)}
 */
public class Topic {

    public static final String MESSAGE_TOPIC_CONSTRAINTS = "Topic names should be alphanumeric";
    public static final String TOPIC_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String topicName;

    /**
     * Constructs a {@code Topic}.
     *
     * @param topicName A valid topic name.
     */
    public Topic(String topicName) {
        requireNonNull(topicName);
        checkArgument(isValidTopicName(topicName), MESSAGE_TOPIC_CONSTRAINTS);
        this.topicName = topicName;
    }

    /**
     * Returns true if a given string is a valid topic name.
     */
    public static boolean isValidTopicName(String test) {
        return test.matches(TOPIC_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Topic // instanceof handles nulls
                && topicName.equals(((Topic) other).topicName)); // state check
    }

    @Override
    public int hashCode() {
        return topicName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + topicName + ']';
    }

}
