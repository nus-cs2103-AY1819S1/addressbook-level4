package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Name;
import seedu.address.model.group.Group;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * JAXB-friendly version of Group.
 */
public class XmlAdaptedGroup {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";

    @XmlElement(required = true)
    private String groupName;
    @XmlElement
    private List<XmlAdaptedUsername> pendingUsers = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedUsername> acceptedUsers = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedGroup.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedGroup() {}

    /**
     * Constructs an {@code XmlAdaptedGroup} with the given group details.
     */
    public XmlAdaptedGroup(String groupName, List<XmlAdaptedUsername> acceptedUsers,
                           List<XmlAdaptedUsername> pendingUsers) {
        this.groupName = groupName;
        if (acceptedUsers != null) {
            this.acceptedUsers = acceptedUsers;
        }
        if (pendingUsers != null) {
            this.pendingUsers = pendingUsers;
        }
    }

    /**
     * Converts a given Group into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdadptedGroup
     */
    public XmlAdaptedGroup(Group source) {
        groupName = source.getGroupName().toString();
        acceptedUsers = source.getAcceptedUsers().stream()
                .map(user -> new XmlAdaptedUsername(user.getUsername()))
                .collect(Collectors.toList());
        pendingUsers = source.getPendingUsers().stream()
                .map(user -> new XmlAdaptedUsername(user.getUsername()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedGroup)) {
            return false;
        }

        XmlAdaptedGroup otherGroup = (XmlAdaptedGroup) other;
        return Objects.equals(groupName, otherGroup.groupName);
    }

    /**
     * Converts this jaxb-friendly adapted group object into the model's Group object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted group
     */
    public Group toModelType(HashMap<Username, User> usernameUserHashMap) throws IllegalValueException {
        if (groupName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Group.class.getSimpleName()));
        }

        if (!Name.isValidName(groupName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }

        final Name modelName = new Name(groupName);
        final List<User> modelAcceptedUsers = new ArrayList<>();
        final List<User> modelPendingUsers = new ArrayList<>();

        for (XmlAdaptedUsername u: acceptedUsers) {
            Username username = u.toModelType();
            modelAcceptedUsers.add(usernameUserHashMap.get(username));
        }

        for (XmlAdaptedUsername u: pendingUsers) {
            Username username = u.toModelType();
            modelPendingUsers.add(usernameUserHashMap.get(username));
        }

        return new Group(modelName, modelAcceptedUsers, modelPendingUsers);
    }

}

