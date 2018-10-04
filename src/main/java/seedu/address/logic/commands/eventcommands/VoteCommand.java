//@@author theJrLinguist
package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLL_OPTION;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.DisplayPollEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.NoEventSelectedException;
import seedu.address.logic.commands.exceptions.NoUserLoggedInException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.Poll;
import seedu.address.model.event.UserNotJoinedEventException;
import seedu.address.model.person.Person;

/**
 * Command adds a vote to the specified poll and option.
 */
public class VoteCommand extends Command {

    public static final String COMMAND_WORD = "voteOption";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": User adds vote to the option of the specified poll.\n"
            + "Parameters: "
            + PREFIX_INDEX + "INDEX "
            + PREFIX_POLL_OPTION + "OPTION ";

    public static final String MESSAGE_SUCCESS = "Voted for option '%1$s' of poll %2$s.";

    private final Index pollIndex;
    private final String optionName;

    /**
     * Creates an VoteCommand to add the specified {@code Event}
     */
    public VoteCommand(Index pollIndex, String optionName) {
        requireNonNull(pollIndex, optionName);
        this.pollIndex = pollIndex;
        this.optionName = optionName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            Event event = model.getSelectedEvent();
            Person person = model.getCurrentUser();
            event.addVoteToPoll(pollIndex, person, optionName);
            model.commitAddressBook();
            model.updateEvent(event, event);
            String result = String.format(MESSAGE_SUCCESS, optionName, pollIndex.getOneBased());
            String pollDisplayResult = event.displayPoll(pollIndex);
            EventsCenter.getInstance().post(new DisplayPollEvent(pollDisplayResult));
            return new CommandResult(result);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_NO_POLL_AT_INDEX);
        } catch (IllegalArgumentException e) {
            throw new CommandException(Messages.MESSAGE_NO_SUCH_OPTION);
        } catch (NoUserLoggedInException e) {
            throw new CommandException(Messages.MESSAGE_NO_USER_LOGGED_IN);
        } catch (NoEventSelectedException e) {
            throw new CommandException(Messages.MESSAGE_NO_EVENT_SELECTED);
        } catch (UserNotJoinedEventException e) {
            throw new CommandException(Messages.MESSAGE_HAVE_NOT_JOINED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VoteCommand // instanceof handles nulls
                && pollIndex.equals(((VoteCommand) other).pollIndex)
                && optionName.equals(((VoteCommand) other).optionName));
    }
}
