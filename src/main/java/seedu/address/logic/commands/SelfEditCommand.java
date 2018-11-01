package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.permission.Permission;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.project.Project;


/**
 * Edits the details of an existing person in the address book.
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
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    public SelfEditCommand() {
    }

    @Override
    public CommandResult runBody(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(MESSAGE_EDIT_PERSON_SUCCESS);
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
        return true;
    }
}
