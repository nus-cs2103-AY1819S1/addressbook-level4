package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_TAG_EDITED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL_EDITED;
import static seedu.address.testutil.TypicalPersons.ELLE_EDITED;
import static seedu.address.testutil.TypicalPersons.HENRY_BANKER;
import static seedu.address.testutil.TypicalPersons.HENRY_BUDDY;
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
 * {@code TagCommand}'s edit functionality.
 */
public class TagEditCommandTest {
    private Model model = new ModelManager(getTaggedAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        PersonContainsTagPredicate firstPredicate =
                new PersonContainsTagPredicate(Collections.singletonList("Singaporean"));
        PersonContainsTagPredicate secondPredicate =
                new PersonContainsTagPredicate(Collections.singletonList("OCBC"));

        TagCommand editFirstCommand = new TagCommand(firstPredicate, TagCommand.Action.EDIT,
                Arrays.asList("Singaporean", "buddies"));
        TagCommand editSecondCommand = new TagCommand(secondPredicate, TagCommand.Action.EDIT,
                Arrays.asList("OCBC", "bank"));

        // same object -> returns true
        assertTrue(editFirstCommand.equals(editFirstCommand));

        // same values -> returns true
        TagCommand deleteFirstCommandCopy = new TagCommand(firstPredicate, TagCommand.Action.EDIT,
                Arrays.asList("Singaporean", "buddies"));
        assertTrue(editFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(editFirstCommand.equals(1));

        // null -> returns false
        assertFalse(editFirstCommand.equals(null));

        // different edits -> returns false
        assertFalse(editFirstCommand.equals(editSecondCommand));
    }

    @Test
    public void execute_noTagsChanged_success() {
        String expectedMessage = String.format(MESSAGE_TAG_EDITED_OVERVIEW, 0, "doesNotExist", "dummyTag");
        PersonContainsTagPredicate predicate = preparePredicate("doesNotExist");
        TagCommand command = new TagCommand(predicate, TagCommand.Action.EDIT, Arrays.asList("doesNotExist",
                "dummyTag"));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.commitAddressBook();

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleTagChanged_success() {
        String expectedMessage = String.format(MESSAGE_TAG_EDITED_OVERVIEW, 1, "POSB", "banker");
        PersonContainsTagPredicate predicate = preparePredicate("POSB");
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(HENRY_BANKER.getName().toString());
        TagCommand command = new TagCommand(predicate, TagCommand.Action.EDIT, Arrays.asList("POSB", "banker"));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.updatePerson(expectedModel.getFilteredPersonList().get(0), HENRY_BANKER);
        expectedModel.updateFilteredPersonList(namePredicate);
        expectedModel.commitAddressBook();

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(HENRY_BANKER), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleTagsChanged_success() {
        String expectedMessage = String.format(MESSAGE_TAG_EDITED_OVERVIEW, 3, "Singaporean", "buddies");
        PersonContainsTagPredicate predicate = preparePredicate("Singaporean");
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(
                CARL_EDITED.getName().toString() + " "
                + ELLE_EDITED.getName().toString() + " "
                + HENRY_BUDDY.getName().toString()
        );
        TagCommand command = new TagCommand(predicate, TagCommand.Action.EDIT, Arrays.asList("Singaporean", "buddies"));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.updatePerson(expectedModel.getFilteredPersonList().get(0), CARL_EDITED);
        expectedModel.updatePerson(expectedModel.getFilteredPersonList().get(0), ELLE_EDITED);
        expectedModel.updatePerson(expectedModel.getFilteredPersonList().get(0), HENRY_BUDDY);
        expectedModel.updateFilteredPersonList(namePredicate);
        expectedModel.commitAddressBook();

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL_EDITED, ELLE_EDITED, HENRY_BUDDY),
                model.getFilteredPersonList());
    }

    @Test
    public void executeUndoRedo_tagEdit_success() {
        PersonContainsTagPredicate predicate = preparePredicate("POSB");
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(HENRY_BANKER.getName().toString());
        TagCommand command = new TagCommand(predicate, TagCommand.Action.EDIT, Arrays.asList("POSB", "banker"));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.updatePerson(expectedModel.getFilteredPersonList().get(0), HENRY_BANKER);
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
