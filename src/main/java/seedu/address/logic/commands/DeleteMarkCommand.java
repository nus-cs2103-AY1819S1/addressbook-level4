package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MARK;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.common.EditPersonDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Deletes a mark for an existing person in the address book.
 * TODO: Currently DeleteMarkCommand deletes marks as Tags in the storage model,
 * this will be changed to a new Mark storage model.
 */
public class DeleteMarkCommand extends EditCommand {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a mark entry for the person identified "
            + "by the index number used in the displayed person list. \n"
            + "Parameters: INDEX (must be a positive integer) "
             + PREFIX_MARK + "MARK \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MARK + "75 ";

    public static final String MESSAGE_MARK_DELETE_SUCCESS = "Deleted mark.";
    public static final String MESSAGE_MARK_NOT_DELETED = "Invalid mark provided.";

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public DeleteMarkCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        super(index, editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        super.execute(model, history);
        return new CommandResult(MESSAGE_MARK_DELETE_SUCCESS);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    protected Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;
        Set<Tag> updatedTags = new HashSet<>();
        updatedTags.addAll(personToEdit.getTags());
        editPersonDescriptor.getTags().ifPresent(updatedTags::removeAll);
        editPersonDescriptor.setTags(updatedTags);

        return editPersonDescriptor.createEditedPerson(personToEdit);
    }
}
