package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_EVENT_REMINDER_DURATION;

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


/**
 * Add reminders to an event identified using it's displayed index from the scheduler.
 */
public class AddReminderCommand extends EditCommand {

    public static final String COMMAND_WORD = "addReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add Reminders  the event identified by the index number used in the displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_EVENT_REMINDER_DURATION + "REMINDER DURATION]...\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_EVENT_REMINDER_DURATION + "1h "
            + PREFIX_EVENT_REMINDER_DURATION + "30m";


    public static final String MESSAGE_ADD_REMINDER_SUCCESS = "Add non-repeated reminders to Event: %1$s";
    public static final String MESSAGE_DO_NOT_SUPPORT_RECURRING = COMMAND_WORD
            + " current do not support recurring events. Coming in v2.0";
    public static final String MESSAGE_NO_CHANGE_TO_REMINDERS = "No changes to the reminders of Event: %1$s";
    private static final Logger logger = LogsCenter.getLogger(AddReminderCommand.class);

    private final ReminderDurationList durationsToAdd;

    public AddReminderCommand(Index index, ReminderDurationList durationsToAdd, Flag... flags) {
        super(index, new EditCommand.EditEventDescriptor(), flags);
        this.durationsToAdd = durationsToAdd;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            logger.info(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        if (flags.length > 0) {
            logger.info(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
            throw new CommandException(MESSAGE_DO_NOT_SUPPORT_RECURRING);
        }

        //Set up event to be edited and edited event according to user input
        logger.info("Creating event to be edited.");
        Event eventToEdit = lastShownList.get(index.getZeroBased());
        ReminderDurationList reminderDurationListToEdit = eventToEdit.getReminderDurationList();
        Boolean isChanged = reminderDurationListToEdit.addAll(durationsToAdd);
        editEventDescriptor.setReminderDurationList(reminderDurationListToEdit);
        super.execute(model, history);
        if (isChanged) {
            return new CommandResult(String.format(MESSAGE_ADD_REMINDER_SUCCESS, eventToEdit.getEventName()));
        } else {
            return new CommandResult(String.format(MESSAGE_NO_CHANGE_TO_REMINDERS, eventToEdit.getEventName()));
        }

    }

}
