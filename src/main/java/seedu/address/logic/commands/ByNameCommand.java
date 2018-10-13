package seedu.address.logic.commands;

import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;

/**
 * Encapsulates a command that was called using a client's name, and not the index number of a list, as an argument.
 */
public abstract class ByNameCommand extends Command {

    protected final String personIdentifier;

    /**
     * Creates a ByNameCommand to perform a command on a {@code Person} with a name that can be identified
     * by the {@Code String personIdentifier}.
     * @param personIdentifier A string to identify the person this command acts on.
     */
    public ByNameCommand(String personIdentifier) {
        requireNonNull(personIdentifier);

        this.personIdentifier = personIdentifier;
    }
}
