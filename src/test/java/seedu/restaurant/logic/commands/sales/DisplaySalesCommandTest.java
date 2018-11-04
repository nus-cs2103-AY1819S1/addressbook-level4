package seedu.restaurant.logic.commands.sales;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_DATE_RECORD_ONE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_DATE_RECORD_TWO;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.sales.DisplaySalesCommand.DISPLAYING_REPORT_MESSAGE;
import static seedu.restaurant.logic.commands.sales.DisplaySalesCommand.NO_SUCH_DATE_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.restaurant.commons.events.ui.sales.DisplaySalesReportEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.sales.Date;
import seedu.restaurant.model.sales.SalesRecord;
import seedu.restaurant.testutil.Assert;
import seedu.restaurant.testutil.salesrecords.RecordBuilder;
import seedu.restaurant.ui.testutil.EventsCollectorRule;

public class DisplaySalesCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DisplaySalesCommand(null));
    }

    @Test
    public void execute_dateExistInList_success() {
        Date date = new Date(VALID_DATE_RECORD_ONE);
        SalesRecord record = new RecordBuilder().withDate(VALID_DATE_RECORD_ONE).build();
        model.addRecord(record);
        expectedModel.addRecord(record);
        assertCommandSuccess(new DisplaySalesCommand(date), model, commandHistory,
                String.format(DISPLAYING_REPORT_MESSAGE, date.toString()), expectedModel);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DisplaySalesReportEvent);
    }

    @Test
    public void execute_dateDoesNotExistInList_throwsCommandException() {
        Date date = new Date(VALID_DATE_RECORD_ONE);
        assertCommandFailure(new DisplaySalesCommand(date), model, commandHistory, String.format(NO_SUCH_DATE_MESSAGE
                , date.toString()));
        assertFalse(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DisplaySalesReportEvent);
    }

    @Test
    public void equals() {
        Date date1 = new Date(VALID_DATE_RECORD_ONE);
        Date date2 = new Date(VALID_DATE_RECORD_TWO);

        DisplaySalesCommand displayDate1SalesReportCommand = new DisplaySalesCommand(date1);
        DisplaySalesCommand displayDate2SalesReportCommand = new DisplaySalesCommand(date2);

        // same object -> returns true
        assertTrue(displayDate1SalesReportCommand.equals(displayDate1SalesReportCommand));

        // same values -> returns true
        DisplaySalesCommand displayDate1SalesReportCommandCopy = new DisplaySalesCommand(date1);
        assertTrue(displayDate1SalesReportCommand.equals(displayDate1SalesReportCommandCopy));

        // different types -> returns false
        assertFalse(displayDate1SalesReportCommand.equals(1));

        // null -> returns false
        assertFalse(displayDate1SalesReportCommand.equals(null));

        // different dates -> returns false
        assertFalse(displayDate1SalesReportCommand.equals(displayDate2SalesReportCommand));
    }
}
