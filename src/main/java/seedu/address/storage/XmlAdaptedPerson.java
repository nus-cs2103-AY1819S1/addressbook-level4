package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Description;
import seedu.address.model.person.DueDate;
import seedu.address.model.person.Name;
import seedu.address.model.person.PriorityValue;
import seedu.address.model.person.Task;
import seedu.address.model.tag.Label;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {
    }

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(Task source) {
        name = source.getName().fullName;
        phone = source.getDueDate().value;
        email = source.getPriorityValue().value;
        address = source.getDescription().value;
        tagged = source.getLabels().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final List<Label> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, DueDate.class.getSimpleName()));
        }
        if (!DueDate.isValidDueDate(phone)) {
            throw new IllegalValueException(DueDate.MESSAGE_DUEDATE_CONSTRAINTS);
        }
        final DueDate modelPhone = new DueDate(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PriorityValue.class.getSimpleName()));
        }
        if (!PriorityValue.isValidPriorityValue(email)) {
            throw new IllegalValueException(PriorityValue.MESSAGE_PRIORITYVALUE_CONSTRAINTS);
        }
        final PriorityValue modelEmail = new PriorityValue(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(address)) {
            throw new IllegalValueException(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        final Description modelAddress = new Description(address);

        final Set<Label> modelTags = new HashSet<>(personTags);
        return new Task(modelName, modelPhone, modelEmail, modelAddress, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedPerson otherPerson = (XmlAdaptedPerson) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(email, otherPerson.email)
                && Objects.equals(address, otherPerson.address)
                && tagged.equals(otherPerson.tagged);
    }
}
