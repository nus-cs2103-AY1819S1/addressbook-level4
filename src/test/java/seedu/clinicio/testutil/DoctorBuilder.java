package seedu.clinicio.testutil;

import seedu.clinicio.model.staff.Staff;
import seedu.clinicio.model.staff.Password;
import seedu.clinicio.model.person.Name;

//@@author jjlee050
/**
 * A utility class to help with building Staff objects.
 */
public class DoctorBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PASSWORD = "alicepaul";

    private Name name;
    private Password password;

    public DoctorBuilder() {
        name = new Name(DEFAULT_NAME);
        password = new Password(DEFAULT_PASSWORD, false);
    }

    /**
     * Initializes the DoctorBuilder with the data of {@code personToCopy}.
     */
    public DoctorBuilder(Staff staffToCopy) {
        name = staffToCopy.getName();
        password = staffToCopy.getPassword();
    }

    /**
     * Sets the {@code Name} of the {@code Staff} that we are building.
     */
    public DoctorBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code Staff} that we are building.
     *
     * @param isHashed Check if the password is hashed password.
     *
     */
    public DoctorBuilder withPassword(String password, boolean isHashed) {
        this.password = new Password(password, isHashed);
        return this;
    }

    public Staff build() {
        return new Staff(name, password);
    }

}
