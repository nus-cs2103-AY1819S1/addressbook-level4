package seedu.restaurant.logic.parser.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_DATE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_PAX;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_TAG;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_TIME;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.commands.reservation.EditReservationCommand;
import seedu.restaurant.logic.commands.reservation.EditReservationCommand.EditReservationDescriptor;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ArgumentMultimap;
import seedu.restaurant.logic.parser.util.ArgumentTokenizer;
import seedu.restaurant.logic.parser.util.ParserUtil;
import seedu.restaurant.model.tag.Tag;

//@@author m4dkip
/**
 * Parses input arguments and creates a new EditReservationCommand object
 */
public class EditReservationCommandParser implements Parser<EditReservationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditReservationCommand
     * and returns an EditReservationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditReservationCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PAX, PREFIX_DATE, PREFIX_TIME, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditReservationCommand.MESSAGE_USAGE), pe);
        }

        EditReservationDescriptor editReservationDescriptor = new EditReservationDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editReservationDescriptor.setName(ReservationParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PAX).isPresent()) {
            editReservationDescriptor.setPax(ReservationParserUtil.parsePax(argMultimap.getValue(PREFIX_PAX).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editReservationDescriptor.setDate(ReservationParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            editReservationDescriptor.setTime(ReservationParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editReservationDescriptor::setTags);

        if (!editReservationDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditReservationCommand.MESSAGE_NOT_EDITED);
        }

        return new EditReservationCommand(index, editReservationDescriptor);
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
