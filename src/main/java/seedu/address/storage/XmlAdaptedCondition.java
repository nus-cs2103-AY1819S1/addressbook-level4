package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.Condition;

/**
 * JAXB-friendly adapted version of the Condition.
 */
public class XmlAdaptedCondition {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Condition's %s field is missing!";

    @XmlValue
    String condition;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCondition() {}

    /**
     * Constructs an XmlAdaptedCondition.
     * The argument is String.
     */
    public XmlAdaptedCondition(String condition) {
        this.condition = condition;
    }

    /**
     * Constructs an XmlAdaptedTag.
     *
     * @param source future changes to this will not affect the created.
     */
    public XmlAdaptedCondition(Condition source) {
        condition = source.condition;
    }


    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Condition toModelType() throws IllegalValueException {
        if (!Condition.isValidCondition(condition)) {
            throw new IllegalValueException(Condition.MESSAGE_CONDITION_CONSTRAINTS);
        }
        return new Condition(condition);

    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTag)) {
            return false;
        }

        return condition.equals(((XmlAdaptedCondition) other).condition);
    }

}
