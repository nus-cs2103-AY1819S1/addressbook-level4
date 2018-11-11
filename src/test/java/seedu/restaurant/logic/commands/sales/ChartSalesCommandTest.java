package seedu.restaurant.logic.commands.sales;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.sales.ChartSalesCommand.DISPLAYING_CHART_MESSAGE;
import static seedu.restaurant.logic.commands.sales.ChartSalesCommand.EMPTY_RECORD_LIST_MESSAGE;
import static seedu.restaurant.testutil.sales.TypicalRecords.RECORD_ONE;
import static seedu.restaurant.testutil.sales.TypicalRecords.RECORD_TWO;

import org.junit.Rule;
import org.junit.Test;

import seedu.restaurant.commons.events.ui.sales.DisplaySalesChartEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.ui.testutil.EventsCollectorRule;

public class ChartSalesCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_recordListEmpty_throwsCommandException() {
        assertCommandFailure(new ChartSalesCommand(), model, commandHistory, EMPTY_RECORD_LIST_MESSAGE);
        assertFalse(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DisplaySalesChartEvent);
    }

    @Test
    public void execute_recordListNotEmpty_success() {
        model.addRecord(RECORD_ONE);
        model.addRecord(RECORD_TWO);
        expectedModel.addRecord(RECORD_ONE);
        expectedModel.addRecord(RECORD_TWO);
        assertCommandSuccess(new ChartSalesCommand(), model, commandHistory, DISPLAYING_CHART_MESSAGE, expectedModel);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DisplaySalesChartEvent);
    }

    @Test
    public void equals() {
        ChartSalesCommand chartSalesCommand = new ChartSalesCommand();

        // same object -> returns true
        assertTrue(chartSalesCommand.equals(chartSalesCommand));

        // different types -> returns false
        assertFalse(chartSalesCommand.equals(1));

        // null -> returns false
        assertFalse(chartSalesCommand.equals(null));
    }
}
