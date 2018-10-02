package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * An list of users that is serializable to XML format
 */
@XmlRootElement(name = "users")
public class XmlSerializableUsers {

    public static final String MESSAGE_DUPLICATE_PERSON = "User list contains duplicate User(s).";

    @XmlElement
    private List<XmlAdaptedUser> user;

    /**
     * Creates an empty XmlSerializableUsers.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableUsers() {
        user = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableUsers(HashMap<Username, User> usernameUserHashMap) {
        this();
        usernameUserHashMap.forEach((key, value) -> user.add(new XmlAdaptedUser(value)));
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedRestaurant}.
     */
    public HashMap<Username, User> toModelType() throws IllegalValueException {
        HashMap<Username, User> usernameUserHashMap = new HashMap<>();
        for (XmlAdaptedUser u : user) {
            User user = u.toModelType();
            if (usernameUserHashMap.containsKey(user.getUsername())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            usernameUserHashMap.put(user.getUsername(), user);
        }
        return usernameUserHashMap;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableUsers)) {
            return false;
        }
        return user.equals(((XmlSerializableUsers) other).user);
    }
}
