package seedu.superta.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.superta.model.student.Feedback;

/**
 * JAXB-friendly adapted version of the Feedback.
 */
public class XmlAdaptedFeedback {

    @XmlValue
    private String feedback;

    /**
     * Constructs an XmlAdaptedFeedback.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedFeedback() {}

    /**
     * Constructs a {@code XmlAdaptedFeedback} with the given {@code feedback}.
     */
    public XmlAdaptedFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Converts a given Feedback into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedFeedback(Feedback source) {
        feedback = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted feedback object into the model's Feedback object.
     */
    public Feedback toModelType() {
        return new Feedback(feedback);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedFeedback)) {
            return false;
        }

        return feedback.equals(((XmlAdaptedFeedback) other).feedback);
    }
}
