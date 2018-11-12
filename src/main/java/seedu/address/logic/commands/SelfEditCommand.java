package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;


/**
 * Edits the details of an existing person in the address book.
 * This command makes use of EditCommand's classes to perform it's work.
 */
public class SelfEditCommand extends Command {

    public static final String COMMAND_WORD = "myself";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of yourself, the currently logged "
        + "in person. Existing values will be overwritten by the input values.\n"
        + "Parameters: "
        + "[" + PREFIX_PHONE + " PHONE] "
        + "[" + PREFIX_EMAIL + " EMAIL] "
        + "[" + PREFIX_ADDRESS + " ADDRESS] "
        + "[" + PREFIX_PROJECT + " PROJECT]...\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_PHONE + " 91234567 "
        + PREFIX_EMAIL + " johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String ADMIN_EDIT_ERROR = "You're currently logged in as admin, not as an employee!\nAdmin"
        + " cannot be edited. Perhaps you meant edit instead?";

    private EditCommand.EditPersonDescriptor editPersonDescriptor;

    /**
     * @param editPersonDescriptor details to edit the person with
     */
    public SelfEditCommand(EditCommand.EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(editPersonDescriptor);

        this.editPersonDescriptor = new EditCommand.EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult runBody(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getLoggedInUser().isAdminUser()) {
            throw new CommandException(ADMIN_EDIT_ERROR);
        }

        Person personToEdit = model.getLoggedInUser().getPerson();
        Person editedPerson = EditCommand.createEditedPerson(personToEdit, editPersonDescriptor);

        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SelfEditCommand)) {
            return false;
        }

        // state check
        SelfEditCommand e = (SelfEditCommand) other;
        return editPersonDescriptor.equals(((SelfEditCommand) other).editPersonDescriptor);
    }
}
