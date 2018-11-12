package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FEEDBACK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FeedbackCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Feedback;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class FeedbackCommandSystemTest extends SsenisubSystemTest {

    @Test
    public void feedback() {
        Model model = getModel();

        /* -------------- Performing feedback operation while an unfiltered list is being shown ------------------- */

        /* Case: feedback field, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_PERSON;
        String command = " " + FeedbackCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + PREFIX_FEEDBACK + "wow u cool";
        Person editedPerson = new PersonBuilder(ALICE).withFeedback("wow u cool").build();
        assertCommandSuccess(command, index, editedPerson);

        /* Case: undo editing the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last person in the list -> last person edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updatePerson(
                getModel().getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), editedPerson);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* --------------- Performing feedback operation while a filtered list is being shown --------------------- */

        /* Case: filtered person list, feedback index within bounds of address book and person list -> edited */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        command = FeedbackCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + PREFIX_FEEDBACK
                + "you are superb!";
        Person personToEdit = getModel().getFilteredPersonList().get(index.getZeroBased());
        editedPerson = new PersonBuilder(personToEdit).withFeedback("you are superb!").build();
        assertCommandSuccess(command, index, editedPerson);

        /* Case: filtered person list, feedback index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getSsenisub().getPersonList().size();
        assertCommandFailure(FeedbackCommand.COMMAND_WORD + "  " + invalidIndex + " "
                        + PREFIX_FEEDBACK + "lame",
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* ------------------------------ Performing invalid feedback operation ----------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(FeedbackCommand.COMMAND_WORD + " 0 " + PREFIX_FEEDBACK + "haha",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FeedbackCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(FeedbackCommand.COMMAND_WORD + " -1 " + PREFIX_FEEDBACK + "haha",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FeedbackCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredPersonList().size() + 1;
        assertCommandFailure(FeedbackCommand.COMMAND_WORD + " " + invalidIndex + " "
                        + PREFIX_FEEDBACK + "haha",
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(FeedbackCommand.COMMAND_WORD + " " + PREFIX_FEEDBACK + "haha",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FeedbackCommand.MESSAGE_USAGE));

        /* Case: missing feedback field -> rejected */
        assertCommandFailure(FeedbackCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FeedbackCommand.MESSAGE_USAGE));

        /* Case: feedback has profanity -> rejected */
        String feedbackWithProfanity = "you are an asshole.";
        assertCommandFailure(FeedbackCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + " " + PREFIX_FEEDBACK + feedbackWithProfanity,
                String.format(Feedback.MESSAGE_PROFANITY_FOUND
                        + new Feedback.ProfanityFilter().findProfanity(feedbackWithProfanity)));
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Person, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see FeedbackCommandSystemTest#assertCommandSuccess(String, Index, Person, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Person editedPerson) {
        assertCommandSuccess(command, toEdit, editedPerson, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code FeedbackCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the person at index {@code toEdit} being
     * updated to values specified {@code editedPerson}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see FeedbackCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Person editedPerson,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.updatePerson(expectedModel.getFilteredPersonList().get(toEdit.getZeroBased()), editedPerson);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(command, expectedModel,
                String.format(FeedbackCommand.MESSAGE_FEEDBACK_PERSON_SUCCESS, editedPerson),
                expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see FeedbackCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code SsenisubSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see SsenisubSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code SsenisubSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see SsenisubSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
