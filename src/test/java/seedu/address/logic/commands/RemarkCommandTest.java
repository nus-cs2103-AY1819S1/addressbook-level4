package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.SAMPLE_REMARK_1;
import static seedu.address.logic.commands.CommandTestUtil.SAMPLE_REMARK_2;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.RemarkCommand.MESSAGE_REMARK_ADD_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WISH;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_WISH;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.WishBook;
import seedu.address.model.wish.Remark;
import seedu.address.model.wish.Wish;
import seedu.address.testutil.TypicalWishes;

public class RemarkCommandTest {
    private Model model = new ModelManager(
            TypicalWishes.getTypicalWishBook(), TypicalWishes.getTypicalWishTransaction(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_WISH, SAMPLE_REMARK_1);
        final RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_WISH, SAMPLE_REMARK_1);

        // same values -> returns true
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(prepareCommand(INDEX_SECOND_WISH, SAMPLE_REMARK_1)));

        // different remark -> returns false
        assertFalse(standardCommand.equals(prepareCommand(INDEX_FIRST_WISH, SAMPLE_REMARK_2)));
    }

    /**
     * Returns a {@code RemarkCommand} with parameters {@code index} and {@code remark}
     */
    private RemarkCommand prepareCommand(Index index, Remark remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, remark);
        //remarkCommand.setData(model, new CommandHistory());
        return remarkCommand;
    }

    @Test
    public void execute_addValidRemark_success() {
        Index index = INDEX_FIRST_WISH;
        Remark remark = SAMPLE_REMARK_1;

        Model expectedModel = new ModelManager(
                new WishBook(model.getWishBook()), model.getWishTransaction(), new UserPrefs());

        RemarkCommand remarkCommandToTest = new RemarkCommand(index, remark);

        Wish wishToEdit = expectedModel.getFilteredSortedWishList().get(index.getZeroBased());
        Wish updatedRemarkWish = new Wish(wishToEdit.getName(), wishToEdit.getPrice(), wishToEdit.getDate(),
                wishToEdit.getUrl(), wishToEdit.getSavedAmount(), remark, wishToEdit.getTags(), wishToEdit.getId());
        expectedModel.updateWish(wishToEdit, updatedRemarkWish);
        expectedModel.commitWishBook();

        String expectedMessage = String.format(MESSAGE_REMARK_ADD_SUCCESS, index.getOneBased(), remark.toString());

        assertCommandSuccess(remarkCommandToTest, model, commandHistory, expectedMessage, expectedModel);
    }
}
