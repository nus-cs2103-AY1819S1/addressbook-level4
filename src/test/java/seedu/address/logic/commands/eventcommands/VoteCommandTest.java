//@@author theJrLinguist
package seedu.address.logic.commands.eventcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getAddressBookWithParticipant;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.NoEventSelectedException;
import seedu.address.logic.commands.exceptions.NoUserLoggedInException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.UserNotJoinedEventException;
import seedu.address.model.event.polls.Poll;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalIndexes;

public class VoteCommandTest {
    private static final String OPTION_NAME = "Generic option";

    private Model model = new ModelManager(getAddressBookWithParticipant(), new UserPrefs());
    private Model expectedModel = new ModelManager(getAddressBookWithParticipant(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_acceptedVoteOption() throws UserNotJoinedEventException, NoUserLoggedInException,
            NoEventSelectedException {
        Index index = TypicalIndexes.INDEX_FIRST;
        VoteCommand command = new VoteCommand(index, OPTION_NAME);
        model.setCurrentUser(ALICE);
        expectedModel.setCurrentUser(ALICE);
        model.setSelectedEvent(model.getEvent(index));
        expectedModel.setSelectedEvent(expectedModel.getEvent(index));
        model.addPoll("Generic poll");
        expectedModel.addPoll("Generic poll");
        model.addPollOption(index, OPTION_NAME);
        expectedModel.addPollOption(index, OPTION_NAME);
        String expectedMessage = String.format(command.MESSAGE_SUCCESS, OPTION_NAME, index.getOneBased());
        expectedModel.voteOption(index, OPTION_NAME);
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noEventVoteOption() {
        Person user = new PersonBuilder().build();
        model.setCurrentUser(user);
        VoteCommand command = new VoteCommand(TypicalIndexes.INDEX_FIRST, OPTION_NAME);
        String expectedMessage = String.format(Messages.MESSAGE_NO_EVENT_SELECTED);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_noPollVoteOption() {
        VoteCommand command = new VoteCommand(TypicalIndexes.INDEX_FIRST, OPTION_NAME);
        model.setCurrentUser(ALICE);
        EventBuilder eventBuilder = new EventBuilder();
        eventBuilder.withParticipant().withOrganiser(ALICE);
        Event event = eventBuilder.build();
        model.setSelectedEvent(event);
        String expectedMessage = String.format(Messages.MESSAGE_NO_POLL_AT_INDEX);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_noOptionVoteOption() {
        VoteCommand command = new VoteCommand(TypicalIndexes.INDEX_FIRST, OPTION_NAME);
        model.setCurrentUser(ALICE);
        EventBuilder eventBuilder = new EventBuilder();
        eventBuilder.withParticipant().withOrganiser(ALICE);
        Event event = eventBuilder.withPoll().build();
        model.setSelectedEvent(event);
        String expectedMessage = String.format(Messages.MESSAGE_NO_SUCH_OPTION);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_noUserVoteOption() {
        VoteCommand command = new VoteCommand(TypicalIndexes.INDEX_FIRST, OPTION_NAME);
        EventBuilder eventBuilder = new EventBuilder();
        Event event = eventBuilder.withPoll().build();
        model.setSelectedEvent(event);
        String expectedMessage = String.format(Messages.MESSAGE_NO_USER_LOGGED_IN);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_haveNotJoinedVoteOption() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Index index = TypicalIndexes.INDEX_FIRST;
        VoteCommand command = new VoteCommand(index, OPTION_NAME);
        Person user = new PersonBuilder().build();
        model.setCurrentUser(user);
        Event event = model.getFilteredEventList().get(0);
        event.addPoll("Generic poll");
        Poll poll = (Poll) event.getPoll(index);
        poll.addOption(OPTION_NAME);
        event.setParticipantList(new ArrayList<>());
        model.setSelectedEvent(event);
        String expectedMessage = String.format(Messages.MESSAGE_HAVE_NOT_JOINED);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_haveAlreadyVotedOption() throws UserNotJoinedEventException, NoUserLoggedInException,
            NoEventSelectedException {
        Index index = TypicalIndexes.INDEX_FIRST;
        VoteCommand command = new VoteCommand(index, OPTION_NAME);
        model.setCurrentUser(ALICE);
        model.setSelectedEvent(model.getEvent(index));
        model.addPoll("Generic poll");
        model.addPollOption(index, OPTION_NAME);
        model.voteOption(index, OPTION_NAME);
        String expectedMessage = String.format(Messages.MESSAGE_HAVE_ALREADY_VOTED);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }
}
