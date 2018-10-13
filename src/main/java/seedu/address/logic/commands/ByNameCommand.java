package seedu.address.logic.commands;

import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;

/**
 * Encapsulates a command that was called using a client's name, and not the index number of a list, as an argument.
 */
public abstract class ByNameCommand extends Command {

    protected final Person person;

    /**
     * Creates a ByNameCommand to perform a command on a specified {@code Person}.
     * @param person The person this command acts on.
     */
    public ByNameCommand(Person person) {
        requireNonNull(person);

        this.person = person;
    }
}
