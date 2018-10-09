package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Deletes a patient from health book.
 */
public class DeletePatientCommand extends Command {

    public static final String COMMAND_WORD = "delete-patient";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the patient identified by name and phone. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 ";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_INVALID_DELETE_PERSON = "This patient does not exist in the HealthBook";

    private final Name name;
    private final Phone phone;

    public DeletePatientCommand(Name name, Phone phone) {
        this.name = name;
        this.phone = phone;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToDelete = null;

        for (Person person : lastShownList) {
            if (person.getName().equals(name) && person.getPhone().equals(phone)) {
                personToDelete = person;
                break;
            }
        }
        if (personToDelete == null) {
            throw new CommandException(MESSAGE_INVALID_DELETE_PERSON);
        }

        model.deletePerson(personToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePatientCommand // instanceof handles nulls
                && name.equals(((DeletePatientCommand) other).name)
                && phone.equals(((DeletePatientCommand) other).phone)); // state check
    }
}
