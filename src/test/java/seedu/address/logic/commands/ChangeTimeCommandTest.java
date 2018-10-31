package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.EducationFilterPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class ChangeTimeCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        String first = "Alice 0 Bob 0";
        String second = "alice";


        ChangeTimeCommand ChangeTimeFirstCommand = new ChangeTimeCommand(first);
        ChangeTimeCommand ChangeTimeSecondCommand = new ChangeTimeCommand(second);

        // same object -> returns true
        assertTrue(ChangeTimeFirstCommand.equals(ChangeTimeFirstCommand));

        // same values -> returns true
        ChangeTimeCommand ChangeTimeCommandCopy = new ChangeTimeCommand(first);
        assertTrue(ChangeTimeFirstCommand.equals(ChangeTimeCommandCopy));

        // different types -> returns false
        assertFalse(ChangeTimeFirstCommand.equals(1));


        // different person -> returns false
        assertFalse(ChangeTimeFirstCommand.equals(ChangeTimeSecondCommand));
    }

    @Test
    public void executeZeroKeywordsNoPersonFound() {
        String expectedMessage = String.format("Cannot find the student, please enter valid name");
        ChangeTimeCommand command = new ChangeTimeCommand("Alllice 100 Bob 0");
        assertEquals(expectedMessage,command.execute(model,commandHistory).feedbackToUser);
    }


    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    public NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
