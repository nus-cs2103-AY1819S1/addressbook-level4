package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_TAGGED_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_TAG_DELETED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.OPENHEIMER;
import static seedu.address.testutil.TypicalPersons.OPENHEIMER_UNTAGGED;
import static seedu.address.testutil.TypicalPersons.PERCY;
import static seedu.address.testutil.TypicalPersons.PERCY_UNTAGGED;
import static seedu.address.testutil.TypicalPersons.QUINE;
import static seedu.address.testutil.TypicalPersons.QUINE_UNTAGGED;
import static seedu.address.testutil.TypicalPersons.RICHARD;
import static seedu.address.testutil.TypicalPersons.RICHARD_UNTAGGED;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.PersonContainsTagPredicate;

//@@author A19Sean
public class TagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
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
        TagCommand deleteFirstCommand = new TagCommand(firstPredicate, TagCommand.Action.DELETE,
                Collections.singletonList("first"));
        TagCommand deleteSecondCommand = new TagCommand(secondPredicate, TagCommand.Action.DELETE,
                Collections.singletonList("second"));

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        TagCommand findFirstCommandCopy = new TagCommand(firstPredicate, TagCommand.Action.FIND,
                Collections.singletonList("first"));
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));
        TagCommand deleteFirstCommandCopy = new TagCommand(firstPredicate, TagCommand.Action.DELETE,
                Collections.singletonList("first"));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));
        assertFalse(deleteFirstCommand.equals(null));

        // different tag -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
        assertTrue(findFirstCommand.equals(deleteFirstCommand));
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
                "important"));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(OPENHEIMER, PERCY, QUINE, RICHARD), model.getFilteredPersonList());
    }

    @Test
    public void execute_zeroKeywords_noTagDeleted() {
        String expectedMessage = String.format(MESSAGE_TAG_DELETED_OVERVIEW, 0);
        PersonContainsTagPredicate predicate = preparePredicate(" ");
        TagCommand command = new TagCommand(predicate, TagCommand.Action.DELETE, Collections.singletonList(" "));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multipleTagsDeleted() {
        String expectedMessage = String.format(MESSAGE_TAG_DELETED_OVERVIEW, 4);
        PersonContainsTagPredicate predicate = preparePredicate("Singaporean OCBC important");
        TagCommand command = new TagCommand(predicate, TagCommand.Action.DELETE, Arrays.asList("friends", "owesMoney",
                "important"));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(OPENHEIMER_UNTAGGED, PERCY_UNTAGGED, QUINE_UNTAGGED, RICHARD_UNTAGGED),
                model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code PersonContainsTagPredicate}.
     */
    private PersonContainsTagPredicate preparePredicate(String userInput) {
        return new PersonContainsTagPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
