package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.Phone;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Restaurant.
 */
public class XmlAdaptedRestaurant {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Restaurant's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedRestaurant.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRestaurant() {}

    /**
     * Constructs an {@code XmlAdaptedRestaurant} with the given restaurant details.
     */
    public XmlAdaptedRestaurant(String name, String phone, String address, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Restaurant into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedRestaurant
     */
    public XmlAdaptedRestaurant(Restaurant source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        address = source.getAddress().value;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted restaurant object into the model's Restaurant object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted restaurant
     */
    public Restaurant toModelType() throws IllegalValueException {
        final List<Tag> restaurantTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            restaurantTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(restaurantTags);
        return new Restaurant(modelName, modelPhone, modelAddress, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedRestaurant)) {
            return false;
        }

        XmlAdaptedRestaurant otherRestaurant = (XmlAdaptedRestaurant) other;
        return Objects.equals(name, otherRestaurant.name)
                && Objects.equals(phone, otherRestaurant.phone)
                && Objects.equals(address, otherRestaurant.address)
                && tagged.equals(otherRestaurant.tagged);
    }
}
