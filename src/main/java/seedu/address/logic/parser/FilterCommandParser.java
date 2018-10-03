package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_CAR_NUM;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_CAR_TYPE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_COORD;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_FREE_PARK;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_LOTS_AVAILABLE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_NIGHT_PARK;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_SHORT_TERM;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_LOTS;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE_PARK;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
//import seedu.address.model.carpark.Address;
//import seedu.address.model.carpark.Carpark;
//import seedu.address.model.carpark.CarparkNumber;
//import seedu.address.model.carpark.CarparkType;
//import seedu.address.model.carpark.Coordinate;
//import seedu.address.model.carpark.FreeParking;
//import seedu.address.model.carpark.LotsAvailable;
//import seedu.address.model.carpark.NightParking;
//import seedu.address.model.carpark.ShortTerm;
//import seedu.address.model.carpark.TotalLots;
//import seedu.address.model.carpark.TypeOfParking;
//import seedu.address.model.tag.Tag;

/**
 * To be added
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * To be added
     */
    public FilterCommand parse(String args) throws ParseException {

        //return new FilterCommand(false);
        try {
            String[] flags = ParserUtil.parseFlags(args);

            //System.out.println(flags[0]);   // [space]
            //System.out.println(flags[1]);   // flag 1
            //System.out.println(flags[2]);   // flag 2

            return new FilterCommand(flags);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE), pe);
        }
    }

}
