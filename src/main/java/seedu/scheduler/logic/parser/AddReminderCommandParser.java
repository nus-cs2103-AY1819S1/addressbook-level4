package seedu.scheduler.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_EVENT_REMINDER_DURATION;

import java.util.Set;

import com.google.common.collect.Iterables;

import seedu.scheduler.commons.core.index.Index;
import seedu.scheduler.logic.commands.AddReminderCommand;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.event.ReminderDurationList;

public class AddReminderCommandParser implements Parser<AddReminderCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddReminderCommand parse(String args) throws ParseException {

            requireNonNull(args);
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_REMINDER_DURATION);

            Index index;

            try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddReminderCommand.MESSAGE_USAGE), pe);
            }

            Set<Flag> flags = ParserUtil.parseFlags(argMultimap.getFlags());
            if (flags.size() > 1) {
            throw new ParseException(
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));
            }


            ReminderDurationList durationsToAdd = ParserUtil.parseReminderDurations(
            argMultimap.getAllValues(PREFIX_EVENT_REMINDER_DURATION));


            return new AddReminderCommand(index, durationsToAdd, Iterables.toArray(flags, Flag.class));
    }
}
