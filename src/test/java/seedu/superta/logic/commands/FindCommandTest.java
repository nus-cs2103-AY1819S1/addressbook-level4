package seedu.superta.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.superta.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.superta.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.superta.testutil.TypicalSuperTaClient.CARL;
import static seedu.superta.testutil.TypicalSuperTaClient.DANIEL;
import static seedu.superta.testutil.TypicalSuperTaClient.ELLE;
import static seedu.superta.testutil.TypicalSuperTaClient.FIONA;
import static seedu.superta.testutil.TypicalSuperTaClient.getTypicalSuperTaClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.superta.logic.CommandHistory;
import seedu.superta.model.Model;
import seedu.superta.model.ModelManager;
import seedu.superta.model.UserPrefs;
import seedu.superta.model.student.EmailContainsKeywordsPredicate;
import seedu.superta.model.student.NameContainsKeywordsPredicate;
import seedu.superta.model.student.PhoneContainsKeywordsPredicate;
import seedu.superta.model.student.StudentidContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        Predicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"))
                        .or(new PhoneContainsKeywordsPredicate(Collections.singletonList("first"))
                        .or(new EmailContainsKeywordsPredicate(Collections.singletonList("first")))
                        .or(new StudentidContainsKeywordsPredicate(Collections.singletonList("first"))));
        Predicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"))
                        .or(new PhoneContainsKeywordsPredicate(Collections.singletonList("second"))
                        .or(new EmailContainsKeywordsPredicate(Collections.singletonList("second")))
                        .or(new StudentidContainsKeywordsPredicate(Collections.singletonList("second"))));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_oneParameter_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        Predicate predicate = preparePredicate("Bernara");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredStudentList());
    }

    @Test
    public void execute_oneParameter_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        Predicate predicate = preparePredicate("Kurz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredStudentList());
    }

    @Test
    public void execute_multipleParameters_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        Predicate predicate = preparePredicate("Meyer 87652533 heinz@example.com A1820123Y");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, DANIEL, ELLE, FIONA), model.getFilteredStudentList());
    }

    /**
     * Parses {@code userInput} into a {@code Predicate}.
     */
    private Predicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")))
                .or(new PhoneContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))))
                .or(new EmailContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))))
                .or(new StudentidContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
    }
}
