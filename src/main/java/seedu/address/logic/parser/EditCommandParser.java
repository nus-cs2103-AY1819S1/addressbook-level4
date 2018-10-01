package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
<<<<<<< HEAD
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAR_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAR_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREE_PARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOTS_AVAILABLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NIGHT_PARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHORT_TERM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_LOTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE_PARK;
=======
import static seedu.address.logic.parser.CliSyntax.PREFIX_CARPARK_NO;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOTS_AVAILABLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOT_TYPE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_LOTS;
>>>>>>> master

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

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
<<<<<<< HEAD
                ArgumentTokenizer.tokenize(args, PREFIX_ADDRESS, PREFIX_CAR_NUM, PREFIX_CAR_TYPE, PREFIX_COORD,
                        PREFIX_FREE_PARK, PREFIX_LOTS_AVAILABLE, PREFIX_NIGHT_PARK, PREFIX_SHORT_TERM,
                        PREFIX_TOTAL_LOTS, PREFIX_TYPE_PARK, PREFIX_TAG);
=======
                ArgumentTokenizer.tokenize(args, PREFIX_CARPARK_NO, PREFIX_LOTS_AVAILABLE, PREFIX_LOT_TYPE, PREFIX_TOTAL_LOTS, PREFIX_ADDRESS, PREFIX_TAG);
>>>>>>> master

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.EditCarparkDescriptor editCarparkDescriptor = new EditCommand.EditCarparkDescriptor();
<<<<<<< HEAD
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editCarparkDescriptor.setAddress(
                    ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_CAR_NUM).isPresent()) {
            editCarparkDescriptor.setCarparkNumber(
                    ParserUtil.parseCarparkNumber(argMultimap.getValue(PREFIX_CAR_NUM).get()));
        }
        if (argMultimap.getValue(PREFIX_CAR_TYPE).isPresent()) {
            editCarparkDescriptor.setCarparkType(
                    ParserUtil.parseCarparkType(argMultimap.getValue(PREFIX_CAR_TYPE).get()));
        }
        if (argMultimap.getValue(PREFIX_COORD).isPresent()) {
            editCarparkDescriptor.setCoordinate(
                    ParserUtil.parseCoordinate(argMultimap.getValue(PREFIX_COORD).get()));
        }
        if (argMultimap.getValue(PREFIX_FREE_PARK).isPresent()) {
            editCarparkDescriptor.setFreeParking(
                    ParserUtil.parseFreeParking(argMultimap.getValue(PREFIX_FREE_PARK).get()));
        }
        if (argMultimap.getValue(PREFIX_LOTS_AVAILABLE).isPresent()) {
            editCarparkDescriptor.setLotsAvailable(
                    ParserUtil.parseLotsAvailable(argMultimap.getValue(PREFIX_LOTS_AVAILABLE).get()));
        }
        if (argMultimap.getValue(PREFIX_NIGHT_PARK).isPresent()) {
            editCarparkDescriptor.setNightParking(
                    ParserUtil.parseNightParking(argMultimap.getValue(PREFIX_NIGHT_PARK).get()));
        }
        if (argMultimap.getValue(PREFIX_SHORT_TERM).isPresent()) {
            editCarparkDescriptor.setShortTerm(
                    ParserUtil.parseShortTerm(argMultimap.getValue(PREFIX_SHORT_TERM).get()));
        }
        if (argMultimap.getValue(PREFIX_TOTAL_LOTS).isPresent()) {
            editCarparkDescriptor.setTotalLots(
                    ParserUtil.parseTotalLots(argMultimap.getValue(PREFIX_TOTAL_LOTS).get()));
        }
        if (argMultimap.getValue(PREFIX_TYPE_PARK).isPresent()) {
            editCarparkDescriptor.setTypeOfParking(
                    ParserUtil.parseTypeOfParking(argMultimap.getValue(PREFIX_TYPE_PARK).get()));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editCarparkDescriptor::setTags);

=======
        if (argMultimap.getValue(PREFIX_CARPARK_NO).isPresent()) {
            editCarparkDescriptor.setCarparkNumber(ParserUtil.parseCarparkNumber(argMultimap.getValue(PREFIX_CARPARK_NO).get()));
        }
        if (argMultimap.getValue(PREFIX_LOT_TYPE).isPresent()) {
            editCarparkDescriptor.setLotType(ParserUtil.parseLotsType(argMultimap.getValue(PREFIX_LOT_TYPE).get()));
        }
        if (argMultimap.getValue(PREFIX_LOTS_AVAILABLE).isPresent()) {
            editCarparkDescriptor.setLotsAvailable(ParserUtil.parseLotsAvailable(argMultimap.getValue(PREFIX_LOTS_AVAILABLE).get()));
        }
        if (argMultimap.getValue(PREFIX_TOTAL_LOTS).isPresent()) {
            editCarparkDescriptor.setTotalLots(ParserUtil.parseTotalLots(argMultimap.getValue(PREFIX_TOTAL_LOTS).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editCarparkDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editCarparkDescriptor::setTags);

>>>>>>> master
        if (!editCarparkDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editCarparkDescriptor);
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
