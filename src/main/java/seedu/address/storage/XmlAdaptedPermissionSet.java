package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.permission.Permission;
import seedu.address.model.permission.PermissionSet;
import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.Set;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedPermissionSet {

    @XmlValue
    private Set<Permission> permissionSet = new HashSet<>();

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPermissionSet() {
    }

    /**
     * Constructs a {@code XmlAdaptedTag} with the given {@code tagName}.
     */
    public XmlAdaptedPermissionSet(Permission... pList) {
        for (Permission p : pList) {
            this.permissionSet.add(p);
        }
    }

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedPermissionSet(Set<Permission> source) {
        this.permissionSet = source;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public PermissionSet toModelType() throws IllegalValueException {
        PermissionSet newPermissionSet = new PermissionSet();
        boolean isAdded = true;
        for (Permission p : permissionSet) {
            isAdded = newPermissionSet.addPermission(p);
            if (!isAdded) {
                throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
            }
        }
        return newPermissionSet;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPermissionSet)) {
            return false;
        }

        return permissionSet.equals(((XmlAdaptedPermissionSet) other).permissionSet);
    }
}
