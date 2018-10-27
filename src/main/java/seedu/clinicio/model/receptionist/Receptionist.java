package seedu.clinicio.model.receptionist;

import static seedu.clinicio.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;

import seedu.clinicio.model.password.Password;
import seedu.clinicio.model.person.Address;
import seedu.clinicio.model.person.Email;
import seedu.clinicio.model.person.Name;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.person.Phone;

//@@author jjlee050
/**
 * Represents a Receptionist in the ClinicIO.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Receptionist extends Person {

    // Identity fields
    private final Password password;

    /**
     * Every field must be present and not null.
     */
    public Receptionist(Name name, Password password) {
        super(name, new Phone("999"), new Email("alice@live.com"), new Address("1"), new HashSet<>());
        requireAllNonNull(name, password);
        this.password = password;
    }

    public Password getPassword() {
        return password;
    }

    /**
     * Returns true if both receptionists of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two receptionists.
     */
    public boolean isSameReceptionist(Receptionist otherReceptionist) {
        if (otherReceptionist == this) {
            return true;
        }

        return otherReceptionist != null
                && (otherReceptionist.getName().equals(getName())
                || otherReceptionist.getPassword().equals(getPassword()));
    }

    /**
     * Returns true if both receptionists have the same identity and data fields.
     * This defines a stronger notion of equality between two receptionists.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Receptionist)) {
            return false;
        }

        Receptionist otherDoctor = (Receptionist) other;
        return otherDoctor.getName().equals(getName())
                && otherDoctor.getPassword().equals(getPassword());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(password);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Receptionist-Name: ")
                .append(getName())
                .append(" Password: ")
                .append(getPassword());
        return builder.toString();
    }

}
