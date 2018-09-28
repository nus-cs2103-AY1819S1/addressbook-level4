package seedu.address.logic.commands.eventcommands;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;

public class AddPollOptionCommandTest {
    private static Index INDEX = Index.fromOneBased(1);
    private static String OPTION_NAME = "Generic option";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_acceptedAddPollOption() {
        AddPollOptionCommand command = new AddPollOptionCommand(INDEX, OPTION_NAME);
        EventBuilder eventBuilder = new EventBuilder();
        Event event = eventBuilder.withPoll().build();
        event.getPoll(INDEX);
        commandHistory.setSelectedEvent(event);
        String expectedMessage = String.format(command.MESSAGE_SUCCESS, OPTION_NAME, INDEX.getOneBased());
        expectedModel.commitAddressBook();
        expectedModel.updateEvent(event, event);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noEventAddPollOption() {
        VoteCommand command = new VoteCommand(INDEX, OPTION_NAME);
        String expectedMessage = String.format(Messages.MESSAGE_NO_EVENT_SELECTED);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_noPollAddPollOption() {
        VoteCommand command = new VoteCommand(INDEX, OPTION_NAME);
        EventBuilder eventBuilder = new EventBuilder();
        Event event = eventBuilder.build();
        commandHistory.setSelectedEvent(event);
        String expectedMessage = String.format(Messages.MESSAGE_NO_POLL_AT_INDEX);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }
}
