package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.AddBulletCommand.MESSAGE_ADDBULLET_DUPLICATE_BULLET;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BULLET_FINANCIAL_HACK;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEntryDescription.FINANCIAL_HACK_AWARD;
import static seedu.address.testutil.TypicalEntrys.WORK_FACEBOOK_WITH_DESC;
import static seedu.address.testutil.TypicalEntrys.getTypicalEntryBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.EntryBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.awareness.Awareness;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.util.EntryBuilder;

public class AddBulletCommandTest {

    private Model model = new ModelManager(getTypicalEntryBook(), new UserPrefs(), new Awareness());
    private CommandHistory commandHistory = new CommandHistory();

    /**
     * Get an entry book with one typical entry with description.
     */
    private EntryBook getEntrybookWithDescription() {
        EntryBook entryBook = new EntryBook();
        entryBook.addEntry(WORK_FACEBOOK_WITH_DESC);
        return entryBook;
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        ResumeEntry editedEntry = new EntryBuilder().withCategory("work")
                .withTitle("Facebook").withDuration("2010 - 2013")
                .withSubHeader("software engineering intern")
                .withTags("java")
                .addBulletToDescription(DESC_BULLET_FINANCIAL_HACK)
                .build();
        AddBulletCommand addBulletCommand = new AddBulletCommand(INDEX_FIRST_PERSON, DESC_BULLET_FINANCIAL_HACK);

        String expectedMessage = String.format(AddBulletCommand.MESSAGE_ADDBULLET_SUCCESS, DESC_BULLET_FINANCIAL_HACK);

        Model expectedModel = new ModelManager(new EntryBook(model.getEntryBook()), new UserPrefs(), new Awareness());

        expectedModel.updateEntry(model.getFilteredEntryList().get(0), editedEntry);
        expectedModel.commitEntryBook();

        assertCommandSuccess(addBulletCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEntryIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEntryList().size() + 1);
        AddBulletCommand addBulletCommand = new AddBulletCommand(outOfBoundIndex, DESC_BULLET_FINANCIAL_HACK);

        assertCommandFailure(addBulletCommand, model, commandHistory, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
    }

    @Test
    public void executeAddDuplicateBullets() {
        Model modelTemp = new ModelManager(getEntrybookWithDescription(), new UserPrefs(), new Awareness());
        Index index = Index.fromOneBased(1);
        String duplicateBullet = FINANCIAL_HACK_AWARD;
        AddBulletCommand addBulletCommand = new AddBulletCommand(index, duplicateBullet);

        assertCommandFailure(addBulletCommand, modelTemp, commandHistory, MESSAGE_ADDBULLET_DUPLICATE_BULLET);
    }


    @Test
    public void equals() {
        final AddBulletCommand standardCommand = new AddBulletCommand(INDEX_FIRST_PERSON, DESC_BULLET_FINANCIAL_HACK);

        // same values -> returns true
        AddBulletCommand commandWithSameValues = new AddBulletCommand(INDEX_FIRST_PERSON, DESC_BULLET_FINANCIAL_HACK);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new HelpCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddBulletCommand(INDEX_SECOND_PERSON, DESC_BULLET_FINANCIAL_HACK)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new AddBulletCommand(INDEX_FIRST_PERSON, "")));
    }
}
