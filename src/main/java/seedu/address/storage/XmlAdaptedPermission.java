package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.permission.Permission;

/**
 * JAXB-friendly adapted version of the Permission.
 */
public class XmlAdaptedPermission {

    @XmlValue
    private String permissionName;

    private String messageInvalidPermission = "Value not in Permission Enum";

    /**
     * Constructs an XmlAdaptedPermission.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPermission() {
    }

    /**
     * Constructs a {@code XmlAdaptedPermission} with the given {@code permissionName}.
     */
    public XmlAdaptedPermission(String permissionName) {
        this.permissionName = permissionName;
    }

    /**
     * Converts a given Permission into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedPermission(Permission source) {
        permissionName = source.name();
    }

    /**
     * Converts this jaxb-friendly adapted Permission object into the model's Permission object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Permission toModelType() throws IllegalValueException {
        if (!Permission.isValidPermission(permissionName)) {
            throw new IllegalValueException(messageInvalidPermission);
        }

        return Permission.valueOf(permissionName);

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPermission)) {
            return false;
        }

        return permissionName.equals(((XmlAdaptedPermission) other).permissionName);
    }
}
