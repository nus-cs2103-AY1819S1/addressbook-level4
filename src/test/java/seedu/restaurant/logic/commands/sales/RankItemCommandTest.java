package seedu.restaurant.logic.commands.sales;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.sales.RankItemCommand.DISPLAYING_RANK_ITEM_MESSAGE;
import static seedu.restaurant.logic.commands.sales.RankItemCommand.EMPTY_RECORD_LIST_MESSAGE;
import static seedu.restaurant.testutil.sales.TypicalRecords.RECORD_ONE;
import static seedu.restaurant.testutil.sales.TypicalRecords.RECORD_TWO;

import org.junit.Rule;
import org.junit.Test;

import seedu.restaurant.commons.events.ui.sales.DisplayRankingEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.ui.testutil.EventsCollectorRule;

//@@author HyperionNKJ
public class RankItemCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_recordListEmpty_throwsCommandException() {
        assertCommandFailure(new RankItemCommand(), model, commandHistory, EMPTY_RECORD_LIST_MESSAGE);
        assertFalse(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DisplayRankingEvent);
    }

    @Test
    public void execute_recordListNotEmpty_success() {
        model.addRecord(RECORD_ONE);
        model.addRecord(RECORD_TWO);
        expectedModel.addRecord(RECORD_ONE);
        expectedModel.addRecord(RECORD_TWO);
        assertCommandSuccess(new RankItemCommand(), model, commandHistory, DISPLAYING_RANK_ITEM_MESSAGE, expectedModel);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DisplayRankingEvent);
    }

    @Test
    public void equals() {
        RankItemCommand rankItemCommand = new RankItemCommand();

        // same object -> returns true
        assertTrue(rankItemCommand.equals(rankItemCommand));

        // different types -> returns false
        assertFalse(rankItemCommand.equals(1));

        // null -> returns false
        assertFalse(rankItemCommand.equals(null));
    }
}
