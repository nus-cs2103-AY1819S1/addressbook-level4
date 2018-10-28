package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_GROUPS_FOUND_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.group.util.GroupTitleContainsKeywordsPredicate;
import seedu.address.model.person.util.PersonNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindPersonCommand}.
 */
public class FindGroupCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        GroupTitleContainsKeywordsPredicate firstPredicate = new GroupTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.singletonList("first"), Collections.emptyList());
        GroupTitleContainsKeywordsPredicate secondPredicate = new GroupTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.singletonList("second"), Collections.emptyList());

        FindGroupCommand findFirstCommand = new FindGroupCommand(firstPredicate);
        FindGroupCommand findSecondCommand = new FindGroupCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindGroupCommand findFirstCommandCopy = new FindGroupCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noGroupFound() {
        String expectedMessage = String.format(MESSAGE_GROUPS_FOUND_OVERVIEW, 0);
        GroupTitleContainsKeywordsPredicate predicate = preparePredicate(" ", " ", " ");
        FindGroupCommand command = new FindGroupCommand(predicate);
        expectedModel.updateFilteredGroupList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredGroupList());
    }
//
//    @Test
//    public void execute_multipleKeywords_somePrefix() {
//        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
//        PersonNameContainsKeywordsPredicate predicate = preparePredicate(" ",
//            "Kurz Elle Kunz", " ");
//        FindPersonCommand command = new FindPersonCommand(predicate);
//        expectedModel.updateFilteredPersonList(predicate);
//        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
//        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
//    }
//
//    @Test
//    public void execute_multipleKeywords_allPrefix() {
//        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
//        PersonNameContainsKeywordsPredicate predicate = preparePredicate(KEYWORD_MATCHING_MEIER, " ", " ");
//        FindPersonCommand command = new FindPersonCommand(predicate);
//        expectedModel.updateFilteredPersonList(predicate);
//        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
//        assertEquals(Arrays.asList(BENSON, DANIEL), model.getFilteredPersonList());
//    }
//
//    @Test
//    public void execute_multipleKeywords_nonePrefix() {
//        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);
//        PersonNameContainsKeywordsPredicate predicate = preparePredicate(" ", " ", KEYWORD_MATCHING_MEIER);
//        FindPersonCommand command = new FindPersonCommand(predicate);
//        expectedModel.updateFilteredPersonList(predicate);
//        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
//        assertEquals(Arrays.asList(ALICE, CARL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
//    }

    /**
     * Parses {@code userInput} into a {@code GroupTitleContainsKeywordsPredicate}.
     */
    private GroupTitleContainsKeywordsPredicate preparePredicate(String userInputForAllPrefix, String userInputForSomePrefix,
                                   String userInputForNonePrefix) {
        return new GroupTitleContainsKeywordsPredicate(Arrays.asList(userInputForAllPrefix.split("\\s+")),
            Arrays.asList(userInputForSomePrefix.split("\\s+")),
            Arrays.asList(userInputForNonePrefix.split("\\s+")));
    }
}
