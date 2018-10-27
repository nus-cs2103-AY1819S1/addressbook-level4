package seedu.address.testutil;

import seedu.clinicio.model.password.Password;
import seedu.address.model.person.Name;
import seedu.clinicio.model.receptionist.Receptionist;

//@@author jjlee050
/**
 * A utility class to help with building Receptionist objects.
 */
public class ReceptionistBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PASSWORD = "alicepaul";

    private Name name;
    private Password password;

    public ReceptionistBuilder() {
        name = new Name(DEFAULT_NAME);
        password = new Password(DEFAULT_PASSWORD, false);
    }

    /**
     * Initializes the ReceptionistBuilder with the data of {@code personToCopy}.
     */
    public ReceptionistBuilder(Receptionist doctorToCopy) {
        name = doctorToCopy.getName();
        password = doctorToCopy.getPassword();
    }

    /**
     * Sets the {@code Name} of the {@code Receptionist} that we are building.
     */
    public ReceptionistBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code Receptionist} that we are building.
     *
     * @param isHashed Check if the password is hashed password.
     *
     */
    public ReceptionistBuilder withPassword(String password, boolean isHashed) {
        this.password = new Password(password, isHashed);
        return this;
    }

    public Receptionist build() {
        return new Receptionist(name, password);
    }

}
