package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.MESSAGE_EDIT_PERSON_SUCCESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

public class RemarkCommand extends Command {
    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_REMARK + "NAME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "random remark";

    public static final String MESSAGE_REMARK_ADD_SUCCESS = "Added remark to Person: ";

    private final Index index;
    private final Remark remark;

    public RemarkCommand(Index index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if(index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person updatedRemarkPerson = createUpdatedRemarkPerson(personToEdit, this.remark);

        model.updatePerson(personToEdit,updatedRemarkPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_REMARK_ADD_SUCCESS,updatedRemarkPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * with updated Remark.
     */
    private static Person createUpdatedRemarkPerson(Person personToEdit, Remark remark) {
        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), remark, personToEdit.getTags());
    }

    @Override
    public boolean equals(Object other) {
        if(other == this) {
            return true;
        }

        if(!(other instanceof RemarkCommand)) {
            return false;
        }

        if(((RemarkCommand) other).remark.equals(this.remark) &&
                ((RemarkCommand) other).index.equals(index)
        ) {
            return true;
        }

        return false;
    }
}
