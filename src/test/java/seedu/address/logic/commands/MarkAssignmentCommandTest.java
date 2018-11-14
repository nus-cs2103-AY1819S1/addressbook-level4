package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalAddressbook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.common.EditPersonDescriptor;
import seedu.address.model.common.Mark;
import seedu.address.model.person.Person;

public class MarkAssignmentCommandTest {
    private static final Mark VALID_MARK = new Mark("0");

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validArgumentsUnfilteredList_success() {

        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Assignment assignmentToMark = model.getFilteredAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased());
        MarkAssignmentCommand markAssignmentCommand = new MarkAssignmentCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_ASSIGNMENT, VALID_MARK);

        String expectedMessage = String.format(MarkAssignmentCommand.MESSAGE_MARK_PERSON_SUCCESS, personToMark);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Map<String, Mark> updatedMarks = new HashMap<>(personToMark.getMarks());
        updatedMarks.put(assignmentToMark.getUniqueId(), VALID_MARK);
        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        descriptor.setMarks(updatedMarks);
        Person markedPerson = descriptor.createEditedPerson(personToMark);
        expectedModel.updatePerson(personToMark, markedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(markAssignmentCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validArgumentsFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Assignment assignmentToMark = model.getFilteredAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased());
        MarkAssignmentCommand markAssignmentCommand = new MarkAssignmentCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_ASSIGNMENT, VALID_MARK);

        String expectedMessage = String.format(MarkAssignmentCommand.MESSAGE_MARK_PERSON_SUCCESS, personToMark);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Map<String, Mark> updatedMarks = new HashMap<>(personToMark.getMarks());
        updatedMarks.put(assignmentToMark.getUniqueId(), VALID_MARK);
        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        descriptor.setMarks(updatedMarks);
        Person markedPerson = descriptor.createEditedPerson(personToMark);
        expectedModel.updatePerson(personToMark, markedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(markAssignmentCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MarkAssignmentCommand markAssignmentCommand = new MarkAssignmentCommand(outOfBoundIndex,
                INDEX_FIRST_ASSIGNMENT, VALID_MARK);

        assertCommandFailure(markAssignmentCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MarkAssignmentCommand markAssignmentCommand = new MarkAssignmentCommand(outOfBoundIndex,
                INDEX_FIRST_ASSIGNMENT, VALID_MARK);

        assertCommandFailure(markAssignmentCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidAssignmentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAssignmentList().size() + 1);
        MarkAssignmentCommand markAssignmentCommand = new MarkAssignmentCommand(INDEX_FIRST_PERSON,
                outOfBoundIndex, VALID_MARK);

        assertCommandFailure(markAssignmentCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidAssignmentIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAssignmentList().size() + 1);
        MarkAssignmentCommand markAssignmentCommand = new MarkAssignmentCommand(INDEX_FIRST_PERSON,
                outOfBoundIndex, VALID_MARK);

        assertCommandFailure(markAssignmentCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MarkAssignmentCommand markFirstPersonFirstAssignmentCommand =
                new MarkAssignmentCommand(INDEX_FIRST_PERSON, INDEX_FIRST_ASSIGNMENT, VALID_MARK);
        MarkAssignmentCommand markSecondPersonFirstAssignmentCommand =
                new MarkAssignmentCommand(INDEX_SECOND_PERSON, INDEX_FIRST_ASSIGNMENT, VALID_MARK);
        MarkAssignmentCommand markFirstPersonSecondAssignmentCommand =
                new MarkAssignmentCommand(INDEX_FIRST_PERSON, INDEX_SECOND_ASSIGNMENT, VALID_MARK);

        // same object -> returns true
        assertTrue(markFirstPersonFirstAssignmentCommand.equals(markFirstPersonFirstAssignmentCommand));

        // same values -> returns true
        MarkAssignmentCommand markmarkFirstPersonFirstAssignmentCommand =
                new MarkAssignmentCommand(INDEX_FIRST_PERSON, INDEX_FIRST_ASSIGNMENT, VALID_MARK);
        assertTrue(markFirstPersonFirstAssignmentCommand.equals(markFirstPersonFirstAssignmentCommand));

        // different types -> returns false
        assertFalse(markFirstPersonFirstAssignmentCommand.equals(1));

        // null -> returns false
        assertFalse(markFirstPersonFirstAssignmentCommand.equals(null));

        // different person -> returns false
        assertFalse(markFirstPersonFirstAssignmentCommand.equals(markSecondPersonFirstAssignmentCommand));

        // different assignment -> returns false
        assertFalse(markFirstPersonFirstAssignmentCommand.equals(markFirstPersonSecondAssignmentCommand));
    }
}
