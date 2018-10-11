package seedu.address.storage.deliveryman;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.model.person.Name;

/**
 * Represents the XML for storage of Deliveryman
 */
public class XmlAdaptedDeliveryman {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Deliveryman's %s field is missing!";

    @XmlElement(required = true)
    private String name;

    public XmlAdaptedDeliveryman() {}

    /**
     * Constructs an {@code XmlAdapterDeliveryman} with the given person details.
     */
    public XmlAdaptedDeliveryman(String name) {
        this.name = name;
    }

    /**
     * Converts a given deliveryman into this class for JAXB use.
     *
     * @param source
     */
    public XmlAdaptedDeliveryman(Deliveryman source) {
        name = source.getName().fullName;
    }

    /**
     * Converts this jaxb-friendly adapted deliveryman object into the model's Deliveryman object.
     *
     * @throws IllegalValueException If there were any data constraints violated.
     */
    public Deliveryman toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        return new Deliveryman(modelName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedDeliveryman)) {
            return false;
        }

        XmlAdaptedDeliveryman otherDman = (XmlAdaptedDeliveryman) other;
        return Objects.equals(name, otherDman.name);
    }
}
