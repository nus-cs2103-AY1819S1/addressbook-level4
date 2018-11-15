package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_TAGGED_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL_TAGGED;
import static seedu.address.testutil.TypicalPersons.ELLE_TAGGED;
import static seedu.address.testutil.TypicalPersons.GEORGE_TAGGED;
import static seedu.address.testutil.TypicalPersons.HENRY_TAGGED;
import static seedu.address.testutil.TypicalPersons.getTaggedAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.PersonContainsTagPredicate;

//@@author A19Sean
/**
 * Contains integration tests (interaction with the Model) for {@code TagCommand}.
 */
public class TagCommandTest {
    private Model model = new ModelManager(getTaggedAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTaggedAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        PersonContainsTagPredicate firstPredicate =
                new PersonContainsTagPredicate(Collections.singletonList("first"));
        PersonContainsTagPredicate secondPredicate =
                new PersonContainsTagPredicate(Collections.singletonList("second"));

        TagCommand findFirstCommand = new TagCommand(firstPredicate, TagCommand.Action.FIND,
                Collections.singletonList("first"));
        TagCommand findSecondCommand = new TagCommand(secondPredicate, TagCommand.Action.FIND,
                Collections.singletonList("second"));

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        TagCommand findFirstCommandCopy = new TagCommand(firstPredicate, TagCommand.Action.FIND,
                Collections.singletonList("first"));
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different tag -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_TAGGED_PERSONS_LISTED_OVERVIEW, 0);
        PersonContainsTagPredicate predicate = preparePredicate(" ");
        TagCommand command = new TagCommand(predicate, TagCommand.Action.FIND, Collections.singletonList(" "));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_TAGGED_PERSONS_LISTED_OVERVIEW, 4);
        PersonContainsTagPredicate predicate = preparePredicate("Singaporean OCBC important");
        TagCommand command = new TagCommand(predicate, TagCommand.Action.FIND, Arrays.asList("Singaporean", "OCBC",
                "FRIENDS"));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL_TAGGED, ELLE_TAGGED, GEORGE_TAGGED, HENRY_TAGGED),
                model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code PersonContainsTagPredicate}.
     */
    private PersonContainsTagPredicate preparePredicate(String userInput) {
        return new PersonContainsTagPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
