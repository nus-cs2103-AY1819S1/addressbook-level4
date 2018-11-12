package seedu.scheduler.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_EVENT_REMINDER_DURATION;

import java.util.Set;

import com.google.common.collect.Iterables;

import seedu.scheduler.commons.core.index.Index;
import seedu.scheduler.logic.commands.DeleteReminderCommand;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.event.ReminderDurationList;

/**
 * Parses input arguments and creates a new DeleteReminderCommand object
 */
public class DeleteReminderCommandParser implements Parser<DeleteReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteReminderCommand
     * and returns an DeleteReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteReminderCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_REMINDER_DURATION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteReminderCommand.MESSAGE_USAGE), pe);
        }

        Set<Flag> flags = ParserUtil.parseFlags(argMultimap.getFlags());
        if (flags.size() > 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReminderCommand.MESSAGE_USAGE));
        }


        ReminderDurationList durationsToAdd = ParserUtil.parseReminderDurations(
            argMultimap.getAllValues(PREFIX_EVENT_REMINDER_DURATION));


        return new DeleteReminderCommand(index, durationsToAdd, Iterables.toArray(flags, Flag.class));
    }
}
