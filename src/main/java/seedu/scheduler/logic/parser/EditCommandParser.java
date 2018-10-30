package seedu.scheduler.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_EVENT_REMINDER_DURATION;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_REPEAT_TYPE;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_REPEAT_UNTIL_DATE_TIME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.Iterables;

import seedu.scheduler.commons.core.index.Index;
import seedu.scheduler.logic.commands.EditCommand;
import seedu.scheduler.logic.commands.EditCommand.EditEventDescriptor;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME, PREFIX_START_DATE_TIME, PREFIX_END_DATE_TIME,
                        PREFIX_DESCRIPTION, PREFIX_VENUE, PREFIX_REPEAT_TYPE,
                        PREFIX_REPEAT_UNTIL_DATE_TIME, PREFIX_TAG, PREFIX_EVENT_REMINDER_DURATION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        Set<Flag> flags = ParserUtil.parseFlags(argMultimap.getFlags());
        if (flags.size() > 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }


        /*
        if (flags.size() == 0
                && (argMultimap.getValue(PREFIX_REPEAT_TYPE).isPresent()
                || argMultimap.getValue(PREFIX_REPEAT_UNTIL_DATE_TIME).isPresent())) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_SINGLE_EVENT_FAIL));
        }
        */


        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();
        if (argMultimap.getValue(PREFIX_EVENT_NAME).isPresent()) {
            editEventDescriptor.setEventName(ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_START_DATE_TIME).isPresent()) {
            editEventDescriptor.setStartDateTime(
                    ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START_DATE_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_END_DATE_TIME).isPresent()) {
            editEventDescriptor.setEndDateTime(
                    ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_END_DATE_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editEventDescriptor.setDescription(
                    ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_VENUE).isPresent()) {
            editEventDescriptor.setVenue(ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get()));
        }
        if (argMultimap.getValue(PREFIX_REPEAT_TYPE).isPresent()) {
            editEventDescriptor.setRepeatType(
                    ParserUtil.parseRepeatType(argMultimap.getValue(PREFIX_REPEAT_TYPE).get()));
        }
        if (argMultimap.getValue(PREFIX_REPEAT_UNTIL_DATE_TIME).isPresent()) {
            editEventDescriptor.setRepeatUntilDateTime(
                    ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_REPEAT_UNTIL_DATE_TIME).get()));
        }
        if (!argMultimap.getAllValues(PREFIX_EVENT_REMINDER_DURATION).isEmpty()) {
            editEventDescriptor.setReminderDurationList(
                    ParserUtil.parseReminderDurations(argMultimap.getAllValues(PREFIX_EVENT_REMINDER_DURATION)));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editEventDescriptor::setTags);
        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editEventDescriptor, Iterables.toArray(flags, Flag.class));
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
