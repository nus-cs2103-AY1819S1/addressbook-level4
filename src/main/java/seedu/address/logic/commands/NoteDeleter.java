package seedu.address.logic.commands;

import static seedu.address.model.person.Note.MESSAGE_NOTE_EMPTY;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.common.EditPersonDescriptor;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;

/**
 * Class that takes care of the deletion of a note from a student.
 */
public interface NoteDeleter {

    /**
     * Returns a {@code Person} with a deleted note.
     * @param student whose fields are copied into the returned Person, other than his/her note.
     * @throws CommandException that indicates the deletion failed due to the student not having a note to begin with.
     */
    default Person copyWithoutNote(Person student) throws CommandException {
        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        Note note = student.getNote();
        // check if note is unchanged
        if (!note.hasChanged()) {
            throw new CommandException(MESSAGE_NOTE_EMPTY);
        }
        Note updatedNote = note.delete();
        descriptor.setNote(updatedNote);
        return descriptor.createEditedPerson(student);
    }

    /**
     * Replaces student at index with copied student without a note.
     * @param studentToReplace removed from {@code model} and replaced with new Student object.
     */
    default void removeNoteFromStudentAtIndexInModel(Person studentToReplace, Model model) throws CommandException {
        Person studentDeleted = copyWithoutNote(studentToReplace);
        model.updatePerson(studentToReplace, studentDeleted);
    }
}
