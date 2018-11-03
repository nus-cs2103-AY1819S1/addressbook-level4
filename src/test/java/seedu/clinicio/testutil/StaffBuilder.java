package seedu.clinicio.testutil;

import static seedu.clinicio.model.staff.Role.DOCTOR;

import seedu.clinicio.model.person.Name;
import seedu.clinicio.model.staff.Password;
import seedu.clinicio.model.staff.Role;
import seedu.clinicio.model.staff.Staff;

//@@author jjlee050
/**
 * A utility class to help with building Staff objects.
 */
public class StaffBuilder {

    public static final Role DEFAULT_ROLE = DOCTOR;
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PASSWORD = "alicepaul";

    private Role role;
    private Name name;
    private Password password;

    public StaffBuilder() {
        role = DEFAULT_ROLE;
        name = new Name(DEFAULT_NAME);
        password = new Password(DEFAULT_PASSWORD, false);
    }

    /**
     * Initializes the StaffBuilder with the data of {@code personToCopy}.
     */
    public StaffBuilder(Staff staffToCopy) {
        role = staffToCopy.getRole();
        name = staffToCopy.getName();
        password = staffToCopy.getPassword();
    }

    /**
     * Sets the {@code Role} of the {@code Staff} that we are building.
     */
    public StaffBuilder withRole(Role role) {
        this.role = role;
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Staff} that we are building.
     */
    public StaffBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code Staff} that we are building.
     *
     * @param isHashed Check if the password is hashed password.
     *
     */
    public StaffBuilder withPassword(String password, boolean isHashed) {
        this.password = new Password(password, isHashed);
        return this;
    }

    public Staff build() {
        return new Staff(role, name, password);
    }

}
