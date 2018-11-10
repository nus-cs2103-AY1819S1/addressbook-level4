package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_EVENT_REMINDER_DURATION;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

import seedu.scheduler.commons.core.LogsCenter;
import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.commons.core.index.Index;
import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.logic.parser.Flag;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.ReminderDurationList;

public class PostponeReminderCommand extends EditCommand {
    public static final String COMMAND_WORD = "postponeReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Postpone all reminders of the event identified by the index number by the duration entered\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_EVENT_REMINDER_DURATION + "REMINDER DURATION]\n"
            + "Optional Flags (Only one at a time):\n"
            + "-u: edit all upcoming events\n" + "-a: edit all similar repeating events.\n"
            + "Example: " + COMMAND_WORD + " 1 "  + PREFIX_EVENT_REMINDER_DURATION + "1h ";

    public static final String MESSAGE_POSTPONE_REMINDER_SUCCESS = "Postpone reminders of Event: %1$s";
    public static final String MESSAGE_MULTIPLE_POSTPONE_DURATION = "Please enter only 1 duration to postpone.";

    private static final Logger logger = LogsCenter.getLogger(PostponeReminderCommand.class);

    private final Duration durationToPostpone;


    public PostponeReminderCommand(Index index, Duration durationToPostpone, Flag... flags) {
        super(index, new EditCommand.EditEventDescriptor(), flags);
        this.durationToPostpone = durationToPostpone;

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            logger.info(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        //Set up event to be edited and edited event according to user input
        logger.info("Creating event to be edited.");
        Event eventToEdit;
        eventToEdit = lastShownList.get(index.getZeroBased());
        ReminderDurationList reminderDurationListToEdit = eventToEdit.getReminderDurationList();
        reminderDurationListToEdit.postpone(durationToPostpone);
        editEventDescriptor.setReminderDurationList(reminderDurationListToEdit);
        super.execute(model, history);

        return new CommandResult(String.format(MESSAGE_POSTPONE_REMINDER_SUCCESS, eventToEdit.getEventName()));

    }
}
