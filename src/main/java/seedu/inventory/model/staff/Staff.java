package seedu.inventory.model.staff;

import static seedu.inventory.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Staff for user management in the system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Staff {
    private Username username;
    private Password password;
    private StaffName staffName;
    private Role role;

    /**
     * This is a enum for staff's role.
     */
    public enum Role {
        admin, manager, user;

        public static final String MESSAGE_ROLE_CONSTRAINTS = "Role should only be either user, manager or admin.";

        public static Role role(String token) {
            return Role.valueOf(token);
        }

        public static String token(Role t) {
            return t.name();
        }
    }

    /**
     * Every field must be present and not null.
     */
    public Staff(Username username, Password password, StaffName staffName, Role role) {
        requireAllNonNull(username, password, staffName, role);
        this.username = username;
        this.password = password;
        this.staffName = staffName;
        this.role = role;
    }

    public Username getUsername() {
        return this.username;
    }

    public Password getPassword() {
        return this.password;
    }

    public StaffName getStaffName() {
        return this.staffName;
    }

    public Role getRole() {
        return this.role;
    }

    /**
     * Returns true if both staffs of the same staffName and username have
     * at least one other identity field that is the same.
     * This defines a weaker notion of equality between two staffs.
     */
    public boolean isSameStaff(Staff otherStaff) {
        if (otherStaff == this) {
            return true;
        }

        return otherStaff != null
                && otherStaff.getUsername().equals(getUsername())
                && otherStaff.getPassword().equals(getPassword())
                && (otherStaff.getStaffName().equals(getStaffName())
                || otherStaff.getRole().equals(getRole()));
    }

    /**
     * Returns true if both staffs have the same identity and data fields.
     * This defines a stronger notion of equality between two staffs.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Staff)) {
            return false;
        }

        if (other == this) {
            return true;
        }

        Staff otherStaff = (Staff) other;
        return otherStaff.getUsername().equals(getUsername())
                && otherStaff.getPassword().equals(getPassword())
                && otherStaff.getStaffName().equals(getStaffName())
                && otherStaff.getRole().equals(getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, staffName, role);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getUsername())
                .append(" Staff Name: ")
                .append(getStaffName())
                .append(" Role: ")
                .append(getRole());
        return builder.toString();
    }
}
