package seedu.address.logic.commands;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SAVED_AMOUNT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SAVED_AMOUNT_BOB;
import static seedu.address.logic.commands.SaveCommand.MESSAGE_USAGE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WISH;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_WISH;
import static seedu.address.testutil.TypicalWishes.getTypicalWishBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.wish.Remark;
import seedu.address.model.wish.SavedAmount;
import seedu.address.testutil.TypicalIndexes;

public class SaveCommandTest {
    Model model = new ModelManager(getTypicalWishBook(), new UserPrefs());

    @Test
    public void execute() {
        SavedAmount savedAmount = new SavedAmount(VALID_SAVED_AMOUNT_AMY);
        CommandTestUtil.assertCommandFailure(new SaveCommand(INDEX_FIRST_WISH, savedAmount), model, new CommandHistory(), MESSAGE_USAGE);
    }

    @Test
    public void equals() {
        final SavedAmount savedAmountAmy = new SavedAmount(VALID_SAVED_AMOUNT_AMY);
        final SavedAmount savedAmountBob = new SavedAmount(VALID_SAVED_AMOUNT_BOB);
        final SaveCommand saveCommand1a = new SaveCommand(INDEX_FIRST_WISH, savedAmountAmy);
        final SaveCommand saveCommand1b = new SaveCommand(INDEX_FIRST_WISH, savedAmountAmy);
        final SaveCommand saveCommand2 = new SaveCommand(INDEX_FIRST_WISH, savedAmountBob);

        // same object
        assertTrue(saveCommand1a.equals(saveCommand1a));

        // same values
        assertTrue(saveCommand1b.equals(saveCommand1a));

        // different values
        assertFalse(saveCommand1a.equals(saveCommand2));

        // null
        assertFalse(saveCommand1a.equals(null));

        // different command
        assertFalse(saveCommand1a.equals(new ClearCommand()));
    }
}
