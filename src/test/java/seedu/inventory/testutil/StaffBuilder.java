package seedu.inventory.testutil;

import seedu.inventory.model.staff.Password;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.model.staff.StaffName;
import seedu.inventory.model.staff.Username;

/**
 * An utility class to help with building Staff objects.
 */
public class StaffBuilder {

    public static final String DEFAULT_USERNAME = "johnd";
    public static final String DEFAULT_PASSWORD = "johndoe1";
    public static final String DEFAULT_NAME = "John Doe";
    public static final Staff.Role DEFAULT_ROLE = Staff.Role.user;

    private Username username;
    private Password password;
    private StaffName staffName;
    private Staff.Role role;

    public StaffBuilder() {
        username = new Username(DEFAULT_USERNAME);
        password = new Password(DEFAULT_PASSWORD);
        staffName = new StaffName(DEFAULT_NAME);
        role = DEFAULT_ROLE;
    }

    /**
     * Initializes the StaffBuilder with the data of {@code staffToCopy}
     * @param staffToCopy the staff to be copied
     */
    public StaffBuilder(Staff staffToCopy) {
        username = staffToCopy.getUsername();
        password = staffToCopy.getPassword();
        staffName = staffToCopy.getStaffName();
        role = staffToCopy.getRole();
    }

    /**
     * Sets the {@code Username} of the {@code Staff} that we are building.
     */
    public StaffBuilder withUsername(String username) {
        this.username = new Username(username);
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code Staff} that we are building.
     */
    public StaffBuilder withPassword(String password) {
        this.password = new Password(password);
        return this;
    }

    /**
     * Sets the {@code StaffName} of the {@code Staff} that we are building.
     */
    public StaffBuilder withName(String name) {
        this.staffName = new StaffName(name);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Staff} that we are building.
     */
    public StaffBuilder withRole(Staff.Role role) {
        this.role = role;
        return this;
    }

    public Staff build() {
        return new Staff(username, password, staffName, role);
    }
}
