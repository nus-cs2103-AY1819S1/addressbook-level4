package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.topic.Topic;

/**
 * JAXB-friendly adapted version of the Topic.
 */
public class XmlAdaptedTopic {

    @XmlValue
    private String topicName;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTopic() {}

    /**
     * Constructs a {@code XmlAdaptedTag} with the given {@code topicName}.
     */
    public XmlAdaptedTopic(String topicName) {
        this.topicName = topicName;
    }

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTopic(Topic source) {
        topicName = source.topicName;
    }

    /**
     * Converts this jaxb-friendly adapted topic object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Topic toModelType() throws IllegalValueException {
        if (!Topic.isValidTopicName(topicName)) {
            throw new IllegalValueException(Topic.MESSAGE_TOPIC_CONSTRAINTS);
        }
        return new Topic(topicName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTopic)) {
            return false;
        }

        return topicName.equals(((XmlAdaptedTopic) other).topicName);
    }
}
