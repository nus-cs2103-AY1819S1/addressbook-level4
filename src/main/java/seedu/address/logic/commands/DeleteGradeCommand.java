package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Education;
import seedu.address.model.person.Email;
import seedu.address.model.person.Grades;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Time;
import seedu.address.model.tag.Tag;


/**
 * Analyze grades of students
 */
public class DeleteGradeCommand extends Command {
    public static final String COMMAND_WORD = "deleteGrade";

    private static final String MESSAGE_DELETE_GRADE_SUCCESS = "Successfully delete the grade of %1$s from %2$s success.";
    private static final String MESSAGE_PERSON_NOT_FOUND = "This person does not exists in the address book";
    private static final String MESSAGE_EXAM_NAME_FOUND = "This exam name is not found in the records of this student.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an exam grade of a person. "
            + "Parameters: INDEX (must be a positive integer) "
            + " NAME_OF_EXAM "
            + "Example: " + COMMAND_WORD + " 1 "
            + " Y1819S1_Mid ";

    private final String toDelete;

    private final Index index;

    /**
     * Creates a DeleteGradeCommand to delete the specified {@code grade} in grades attribute
     */
    public DeleteGradeCommand(Index index, String examName) {
        requireNonNull(index);
        requireNonNull(examName);
        toDelete = examName;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person targetPerson = lastShownList.get(index.getZeroBased());

        if (!targetPerson.getGrades().containsKey(toDelete)) {
            throw new CommandException(MESSAGE_EXAM_NAME_FOUND);
        }
        if (!model.hasPerson(targetPerson)) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        Person editedPerson = createEditedPerson(targetPerson);
        editedPerson.deleteGrade(toDelete);

        model.updatePerson(targetPerson, editedPerson);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_GRADE_SUCCESS, toDelete, editedPerson));
    }

    /**
     * copy a person with a new grades HashMap
     *
     * @param personToEdit origin person
     * @return
     */
    private static Person createEditedPerson(Person personToEdit) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();

        Address updatedAddress = personToEdit.getAddress();
        Education updatedEducation = personToEdit.getEducation();

        HashMap<String, Grades> updatedGrades = new HashMap<>(personToEdit.getGrades());
        ArrayList<Time> updatedTime = personToEdit.getTime();
        Set<Tag> updatedTags = personToEdit.getTags();

        return new Person(updatedName, updatedPhone, updatedEmail,
                updatedAddress, updatedEducation, updatedGrades, updatedTime, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGradeCommand // instanceof handles nulls
                && toDelete.equals(((DeleteGradeCommand) other).toDelete))
                && index.equals(((DeleteGradeCommand) other).index);
    }
}



