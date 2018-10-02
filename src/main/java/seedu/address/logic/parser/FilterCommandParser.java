package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
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

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.carpark.Address;
import seedu.address.model.carpark.Carpark;
import seedu.address.model.carpark.CarparkNumber;
import seedu.address.model.carpark.CarparkType;
import seedu.address.model.carpark.Coordinate;
import seedu.address.model.carpark.FreeParking;
import seedu.address.model.carpark.LotsAvailable;
import seedu.address.model.carpark.NightParking;
import seedu.address.model.carpark.ShortTerm;
import seedu.address.model.carpark.TotalLots;
import seedu.address.model.carpark.TypeOfParking;
import seedu.address.model.tag.Tag;


public class FilterCommandParser {


    public FilterCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FilterCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }


}
