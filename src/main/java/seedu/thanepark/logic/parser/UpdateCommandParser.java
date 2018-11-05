package seedu.thanepark.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_MAINTENANCE;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_WAITING_TIME;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_ZONE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.thanepark.commons.core.index.Index;
import seedu.thanepark.logic.commands.UpdateCommand;
import seedu.thanepark.logic.commands.UpdateCommand.UpdateRideDescriptor;
import seedu.thanepark.logic.parser.exceptions.ParseException;
import seedu.thanepark.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class UpdateCommandParser implements Parser<UpdateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME,
                        PREFIX_MAINTENANCE, PREFIX_WAITING_TIME, PREFIX_ZONE, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE), pe);
        }

        UpdateRideDescriptor updateRideDescriptor = new UpdateRideDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            updateRideDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_MAINTENANCE).isPresent()) {
            updateRideDescriptor.setMaintenance(
                    ParserUtil.parseMaintenance(argMultimap.getValue(PREFIX_MAINTENANCE).get()));
        }
        if (argMultimap.getValue(PREFIX_WAITING_TIME).isPresent()) {
            updateRideDescriptor.setWaitTime(ParserUtil.parseWaitingTime(argMultimap
                    .getValue(PREFIX_WAITING_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_ZONE).isPresent()) {
            updateRideDescriptor.setZone(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ZONE).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(updateRideDescriptor::setTags);

        if (!updateRideDescriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateCommand.MESSAGE_NOT_UPDATED);
        }

        return new UpdateCommand(index, updateRideDescriptor);
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
