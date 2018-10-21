package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.model.person.Friend;

/**
 * JAXB-friendly adapted version of the Friend.
 *
 * @author agendazhang
 */
public class XmlAdaptedFriend {

    @XmlValue
    private String friendAttributes;

    /**
     * Constructs an XmlAdaptedFriend.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedFriend() {}

    /**
     * Constructs a {@code XmlAdaptedFriend} with the given {@code friendAttributes}.
     */
    public XmlAdaptedFriend(String friendAttributes) {
        this.friendAttributes = friendAttributes;
    }

    /**
     * Converts a given Friend into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedFriend(Friend source) {
        friendAttributes = source.friendAttributes;
    }

    /**
     * Converts this jaxb-friendly adapted friend object into the model's Friend object.
     */
    public Friend toModelType() {
        return new Friend(friendAttributes);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedFriend)) {
            return false;
        }

        return friendAttributes.equals(((XmlAdaptedFriend) other).friendAttributes);
    }
}
