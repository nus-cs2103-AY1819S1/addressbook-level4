package seedu.scheduler.logic.commands;

import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.scheduler.testutil.TypicalEvents.getTypicalScheduler;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.UserPrefs;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalScheduler(), new UserPrefs());
    }

    @Test
    public void execute_newEvent_success() {
        Event validEvent = new EventBuilder().build();

        Model expectedModel = new ModelManager(model.getScheduler(), new UserPrefs());
        expectedModel.addEvents(List.of(validEvent));
        expectedModel.commitScheduler();

        assertCommandSuccess(new AddCommand(validEvent), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validEvent.getEventName()), expectedModel);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Event eventInList = model.getScheduler().getEventList().get(0);
        assertCommandFailure(new AddCommand(eventInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_EVENT);
    }

}
