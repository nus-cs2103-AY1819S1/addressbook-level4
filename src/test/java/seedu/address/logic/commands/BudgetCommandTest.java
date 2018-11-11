package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.AddCommand.MESSAGE_NON_EXISTENT_CCA;
import static seedu.address.logic.commands.BudgetCommand.SHOWING_BUDGET_MESSAGE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCcas.BASKETBALL;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.MainApp;
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
        model.initialiseBudgetBook();
        Path ccaXslFilePath = Paths.get("data", "ccabook.xsl");
        requireNonNull(ccaXslFilePath);

        if (!Files.exists(ccaXslFilePath)) {

            try {
                InputStream is = MainApp.class.getResourceAsStream("/docs/ccabook.xsl");

                File dir = new File("data");
                dir.mkdirs();

                Path p = Paths.get("data", "ccabook.xsl");

                Files.copy(is, p);
            } catch (IOException e) {
                System.out.println("IO Error");
            } catch (NullPointerException e) {
                System.out.println("Null pointer Exception");
            }

        }

        expectedModel.initialiseBudgetBook();
        expectedModel.readXslFile();

        Command c = new BudgetCommand();

        assertCommandSuccess(c, model, commandHistory, SHOWING_BUDGET_MESSAGE, expectedModel);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowBudgetViewEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_budgetSpecificCca_success() {
        model.initialiseBudgetBook();
        model.readXslFile();

        expectedModel.initialiseBudgetBook();
        expectedModel.readXslFile();

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
