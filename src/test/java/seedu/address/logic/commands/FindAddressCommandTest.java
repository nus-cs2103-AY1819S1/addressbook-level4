//@@author LZYAndy
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.AddressContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindEmailCommand}.
 */
public class FindAddressCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        AddressContainsKeywordsPredicate firstPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("first"));
        AddressContainsKeywordsPredicate secondPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("second"));

        FindAddressCommand findFirstAddressCommand = new FindAddressCommand(firstPredicate);
        FindAddressCommand findSecondAddressCommand = new FindAddressCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstAddressCommand.equals(findFirstAddressCommand));

        // same values -> returns true
        FindAddressCommand findFirstAddressCommandCopy = new FindAddressCommand(firstPredicate);
        assertTrue(findFirstAddressCommand.equals(findFirstAddressCommandCopy));

        // different types -> returns false
        assertFalse(findFirstAddressCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstAddressCommand == null);

        // different person -> returns false
        assertFalse(findFirstAddressCommand.equals(findSecondAddressCommand));
    }

    @Test
    public void executeMultipleKeywordsMultiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        AddressContainsKeywordsPredicate predicate = preparePredicate("123, Jurong West Ave 6, #08-111");
        FindAddressCommand command = new FindAddressCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code EmailContainsKeywordsPredicate}.
     */
    private AddressContainsKeywordsPredicate preparePredicate(String userInput) {
        return new AddressContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
//@@author LZYAndy
