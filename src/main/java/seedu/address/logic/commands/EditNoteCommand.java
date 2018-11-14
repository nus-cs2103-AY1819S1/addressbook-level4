package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.ValidatorUtil.checkIfIndexValid;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Edits a note about the specified student, uses {@code DeleteNoteCommand} and {@code NoteCommand} functionality.
 */
public class EditNoteCommand extends Command implements NoteDeleter {

    public static final String COMMAND_WORD = "editnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Edits the note of the student specified by the given index. "
        + "Parameters: INDEX (must be a positive integer) "
        + "TEXT\n"
        + "Example: " + COMMAND_WORD + " 1" + " motivated student";

    public static final String MESSAGE_SUCCESS = "Edited note of %1$s";

    /**
     * Guaranteed to be a positive integer, {@code EditNoteCommandParser}.
     */
    private final Index studentIndex;
    private String textToAdd;

    /**
     * Creates an EditNoteCommand to add the specified {@code text} as a note about the student.
     */
    public EditNoteCommand(Index index, String text) {
        requireAllNonNull(index, text);
        studentIndex = index;
        textToAdd = text;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        int index = studentIndex.getZeroBased();
        assert(index >= 0);

        List<Person> lastShownList = model.getFilteredPersonList();
        checkIfIndexValid(index, lastShownList);

        Person studentToReplace = lastShownList.get(index);
        removeNoteFromStudentAtIndexInModel(studentToReplace, model);

        new NoteCommand(studentIndex, textToAdd).execute(model, history);

        return new CommandResult(String.format(MESSAGE_SUCCESS, studentToReplace));
    }

    /**
     * Checks if same object or fields are the same.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditNoteCommand // instanceof handles nulls
                && studentIndex.equals(((EditNoteCommand) other).studentIndex)
                && textToAdd.equals(((EditNoteCommand) other).textToAdd));
    }
}
