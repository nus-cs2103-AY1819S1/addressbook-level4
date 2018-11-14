package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.ValidatorUtil.checkIfIndexValid;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes the note of the student identified using it's displayed index from the student list.
 */
public class DeleteNoteCommand extends Command implements NoteDeleter {

    public static final String COMMAND_WORD = "delnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the note of the student identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Deleted Note of Student: %1$s";

    /**
     * Guaranteed to be a positive integer, {@code DeleteNoteCommandParser}.
     */
    private final Index targetIndex;

    public DeleteNoteCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        int index = targetIndex.getZeroBased();
        assert(index >= 0);

        List<Person> lastShownList = model.getFilteredPersonList();
        checkIfIndexValid(index, lastShownList);

        Person studentToReplace = lastShownList.get(index);
        removeNoteFromStudentAtIndexInModel(studentToReplace, model);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS, studentToReplace));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteNoteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteNoteCommand) other).targetIndex)); // state check
    }
}
