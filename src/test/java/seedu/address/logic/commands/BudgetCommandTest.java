package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.AddCommand.MESSAGE_NON_EXISTENT_CCA;
import static seedu.address.logic.commands.BudgetCommand.SHOWING_BUDGET_MESSAGE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCcas.BASKETBALL;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowBudgetViewEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author ericyjw
public class BudgetCommandTest {
    @Rule
    public EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_budget_success() {
        assertCommandSuccess(new BudgetCommand(), model, commandHistory, SHOWING_BUDGET_MESSAGE, expectedModel);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowBudgetViewEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_budgetSpecificCca_success() {
        model.addCca(BASKETBALL);
        model.commitBudgetBook();
        expectedModel.addCca(BASKETBALL);
        expectedModel.commitBudgetBook();
        eventsCollectorRule = new EventsCollectorRule();
        assertCommandSuccess(new BudgetCommand(BASKETBALL.getName()), model, commandHistory,
            SHOWING_BUDGET_MESSAGE, expectedModel);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowBudgetViewEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_budgetSpecificCca_failure() {
        assertCommandFailure(new BudgetCommand(BASKETBALL.getName()), model, commandHistory,
            MESSAGE_NON_EXISTENT_CCA);
    }
}
