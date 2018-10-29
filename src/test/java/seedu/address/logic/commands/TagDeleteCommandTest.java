package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_TAG_DELETED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL_UNTAGGED;
import static seedu.address.testutil.TypicalPersons.ELLE_UNTAGGED;
import static seedu.address.testutil.TypicalPersons.GEORGE_UNTAGGED;
import static seedu.address.testutil.TypicalPersons.HENRY_POSB;
import static seedu.address.testutil.TypicalPersons.HENRY_SINGAPOREAN;
import static seedu.address.testutil.TypicalPersons.getTaggedAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.tag.PersonContainsTagPredicate;

//@@author A19Sean
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code TagCommand}'s delete functionality.
 */
public class TagDeleteCommandTest {
    private Model model = new ModelManager(getTaggedAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        PersonContainsTagPredicate firstPredicate =
                new PersonContainsTagPredicate(Collections.singletonList("Singaporean"));
        PersonContainsTagPredicate secondPredicate =
                new PersonContainsTagPredicate(Collections.singletonList("OCBC"));

        TagCommand deleteFirstCommand = new TagCommand(firstPredicate, TagCommand.Action.DELETE,
                Collections.singletonList("Singaporean"));
        TagCommand deleteSecondCommand = new TagCommand(secondPredicate, TagCommand.Action.DELETE,
                Collections.singletonList("OCBC"));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        TagCommand deleteFirstCommandCopy = new TagCommand(firstPredicate, TagCommand.Action.DELETE,
                Collections.singletonList("Singaporean"));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different tag -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noTagDeleted() {
        String expectedMessage = String.format(MESSAGE_TAG_DELETED_OVERVIEW, 0);
        PersonContainsTagPredicate predicate = preparePredicate(" ");
        TagCommand command = new TagCommand(predicate, TagCommand.Action.DELETE, Collections.singletonList(" "));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.commitAddressBook();

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleKeyword_singleTagDeleted() {
        String expectedMessage = String.format(MESSAGE_TAG_DELETED_OVERVIEW, 1);
        PersonContainsTagPredicate predicate = preparePredicate("POSB");
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(HENRY_SINGAPOREAN.getName().toString());
        TagCommand command = new TagCommand(predicate, TagCommand.Action.DELETE, Collections.singletonList("POSB"));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.updatePerson(expectedModel.getFilteredPersonList().get(0), HENRY_SINGAPOREAN);
        expectedModel.updateFilteredPersonList(namePredicate);
        expectedModel.commitAddressBook();

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(HENRY_SINGAPOREAN), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multipleTagsDeleted() {
        String expectedMessage = String.format(MESSAGE_TAG_DELETED_OVERVIEW, 4);
        PersonContainsTagPredicate predicate = preparePredicate("Singaporean OCBC important");
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(
                CARL_UNTAGGED.getName().toString() + " "
                        + ELLE_UNTAGGED.getName().toString() + " "
                        + GEORGE_UNTAGGED.getName().toString() + " "
                        + HENRY_POSB.getName().toString());
        TagCommand command = new TagCommand(predicate, TagCommand.Action.DELETE, Arrays.asList("Singaporean",
                "OCBC", "important"));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.updatePerson(expectedModel.getFilteredPersonList().get(0), CARL_UNTAGGED);
        expectedModel.updatePerson(expectedModel.getFilteredPersonList().get(0), ELLE_UNTAGGED);
        expectedModel.updatePerson(expectedModel.getFilteredPersonList().get(0), GEORGE_UNTAGGED);
        expectedModel.updatePerson(expectedModel.getFilteredPersonList().get(0), HENRY_POSB);
        expectedModel.updateFilteredPersonList(namePredicate);
        expectedModel.commitAddressBook();

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL_UNTAGGED, ELLE_UNTAGGED, GEORGE_UNTAGGED, HENRY_POSB),
                model.getFilteredPersonList());
    }

    @Test
    public void executeUndoRedo_tagDelete_success() {
        PersonContainsTagPredicate predicate = preparePredicate("POSB");
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(HENRY_SINGAPOREAN.getName().toString());
        TagCommand command = new TagCommand(predicate, TagCommand.Action.DELETE, Collections.singletonList("POSB"));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.updatePerson(expectedModel.getFilteredPersonList().get(0), HENRY_SINGAPOREAN);
        expectedModel.updateFilteredPersonList(namePredicate);
        expectedModel.commitAddressBook();

        command.execute(model, commandHistory);
        expectedModel.undoAddressBook();
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Parses {@code userInput} into a {@code PersonContainsTagPredicate}.
     */
    private PersonContainsTagPredicate preparePredicate(String userInput) {
        return new PersonContainsTagPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
