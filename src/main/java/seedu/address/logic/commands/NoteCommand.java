package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.ValidatorUtil.checkIfIndexValid;
import static seedu.address.logic.commands.ValidatorUtil.checkIfTextValid;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.common.EditPersonDescriptor;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;

/**
 * Adds a note about the specified student if there is none, adds to end with whitespace-prefix if it already exists.
 */
public class NoteCommand extends Command {

    public static final String COMMAND_WORD = "note";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adds a note about the student specified by the given index. "
        + "Parameters: INDEX (must be a positive integer) "
        + "TEXT\n"
        + "Example: " + COMMAND_WORD + " 1" + " hardworking student";

    public static final String MESSAGE_SUCCESS = "New note added to %1$s";

    /**
     * Guaranteed to be a positive integer, {@code NoteCommandParser}.
     */
    private final Index studentIndex;
    private String textToAdd;

    /**
     * Creates a NoteCommand to add the specified {@code text} as a note about the student.
     */
    public NoteCommand(Index index, String text) {
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
        checkIfTextValid(textToAdd);

        Person studentToReplace = lastShownList.get(index);
        textToAdd = addSpaceIfTextExisting(textToAdd, studentToReplace);

        addNoteToStudentAtIndexInModel(studentToReplace, model);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, studentToReplace));
    }

    /**
     * Replaces student at index with copied student with new note.
     * @param studentToReplace removed from {@code model} and replaced with new Student object.
     */
    private void addNoteToStudentAtIndexInModel(Person studentToReplace, Model model) {
        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        Note updatedNote = studentToReplace.getNote().add(textToAdd);
        descriptor.setNote(updatedNote);
        Person newStudent = descriptor.createEditedPerson(studentToReplace);
        model.updatePerson(studentToReplace, newStudent);
    }

    /**
     * Adds whitespace in front of text if student has an existing note.
     * @param textToAdd to be edited if needed.
     * @param student to be checked.
     * @return new String that could either be the same or have a whitespace at front.
     */
    private String addSpaceIfTextExisting(String textToAdd, Person student) {
        if (student.hasNote()) {
            return " " + textToAdd;
        }
        return textToAdd;
    }

    /**
     * Checks if same object or fields are the same.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NoteCommand // instanceof handles nulls
                && studentIndex.equals(((NoteCommand) other).studentIndex)
                && textToAdd.equals(((NoteCommand) other).textToAdd));
    }
}
