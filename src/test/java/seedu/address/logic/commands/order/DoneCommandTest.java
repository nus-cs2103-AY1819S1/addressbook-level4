package seedu.address.logic.commands.order;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalDeliverymen.getTypicalDeliverymenList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalOrders.ALICE;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.user.TypicalUsers.getTypicalUsersList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;
import seedu.address.testutil.OrderBuilder;

public class DoneCommandTest {

    private Model model = new ModelManager(getTypicalOrderBook(), getTypicalUsersList(),
            getTypicalDeliverymenList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private Set<Index> ordersSet = new HashSet<>(Arrays.asList(INDEX_FIRST));

    @Test
    public void execute_validIndexUnfilteredList_success() throws CommandException {
        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST, ordersSet);
        assignCommand.execute(model, commandHistory);

        Order expectedOrder = new OrderBuilder(ALICE).withStatus("COMPLETED").build();

        String expectedMessage = String.format(DoneCommand.MESSAGE_COMPLETED_ORDER_SUCCESS, expectedOrder);
        DoneCommand doneCommand = new DoneCommand(INDEX_FIRST);
        assertCommandSuccess(doneCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidIndexUnfliteredList_failure() throws CommandException {
        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST, ordersSet);
        assignCommand.execute(model, commandHistory);

        Index last = Index.fromZeroBased(model.getFilteredOrderList().size());
        DoneCommand doneCommand = new DoneCommand(last);

        String expectedMessage = Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX;
        assertCommandFailure(doneCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_validIndexUnfliteredList_pendingFailure() throws CommandException {
        Index last = Index.fromZeroBased(2);
        DoneCommand doneCommand = new DoneCommand(last);

        String expectedMessage = DoneCommand.MESSAGE_ONGOING_ORDER;
        assertCommandFailure(doneCommand, model, commandHistory, expectedMessage);
    }

    /**
     * Check if the command runs successfully.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }
}
