package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.*;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import javax.xml.bind.annotation.XmlElement;
import java.util.*;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedAssignment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Assignment's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String deadline;
    @XmlElement(required = true)
    private String weight;
    @XmlElement(required = true)
    private String maxMark;
    @XmlElement(required = true)
    private String uniqueId;

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAssignment() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedAssignment(String name, String deadline, String weight, String maxMark, String uniqueId) {
        this.name = name;
        this.deadline = deadline;
        this.weight = weight;
        this.maxMark = maxMark;
        this.uniqueId = uniqueId;
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedAssignment(Assignment source) {
        name = source.getName().internalString;
        deadline = source.getDeadline().internalString;
        weight = source.getWeight().internalString;
        maxMark = source.getMaxMark().internalString;
        uniqueId = source.getUniqueId();
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Assignment toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, AssignmentName.class.getSimpleName()));
        }
        if (!AssignmentName.isValid(name)) {
            throw new IllegalValueException(AssignmentName.MESSAGE_CONSTRAINTS);
        }
        final AssignmentName modelName = new AssignmentName(name);

        if (deadline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Deadline.class.getSimpleName()));
        }
        if (!Deadline.isValid(deadline)) {
            throw new IllegalValueException(Deadline.MESSAGE_CONSTRAINTS);
        }
        final Deadline modelDeadline = new Deadline(deadline);

        if (weight == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Weight.class.getSimpleName()));
        }
        if (!Weight.isValid(weight)) {
            throw new IllegalValueException(Weight.MESSAGE_CONSTRAINTS);
        }
        final Weight modelWeight = new Weight(weight);

        if (maxMark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Mark.class.getSimpleName()));
        }
        if (!Mark.isValid(maxMark)) {
            throw new IllegalValueException(Mark.MESSAGE_CONSTRAINTS);
        }
        final Mark modelMaxMark = new Mark(maxMark);

        return new Assignment(modelName, modelWeight, modelDeadline, modelMaxMark, uniqueId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAssignment)) {
            return false;
        }

        XmlAdaptedAssignment otherAssignment = (XmlAdaptedAssignment) other;
        return Objects.equals(name, otherAssignment.name)
                && Objects.equals(weight, otherAssignment.weight)
                && Objects.equals(maxMark, otherAssignment.maxMark)
                && Objects.equals(deadline, otherAssignment.deadline)
                && uniqueId.equals(otherAssignment.uniqueId);
    }
}
