package seedu.address.logic.parser;

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
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOTS_AVAILABLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_LOTS;
>>>>>>> master

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
<<<<<<< HEAD
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
=======
import seedu.address.model.carpark.Carpark;
import seedu.address.model.carpark.CarparkNumber;
import seedu.address.model.carpark.LotType;
import seedu.address.model.carpark.LotsAvailable;
import seedu.address.model.carpark.TotalLots;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
>>>>>>> master
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
<<<<<<< HEAD
                ArgumentTokenizer.tokenize(args, PREFIX_ADDRESS, PREFIX_CAR_NUM, PREFIX_CAR_TYPE, PREFIX_COORD,
                        PREFIX_FREE_PARK, PREFIX_LOTS_AVAILABLE, PREFIX_NIGHT_PARK, PREFIX_SHORT_TERM,
                        PREFIX_TOTAL_LOTS, PREFIX_TYPE_PARK, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_ADDRESS, PREFIX_CAR_NUM, PREFIX_CAR_TYPE, PREFIX_COORD,
                PREFIX_FREE_PARK, PREFIX_LOTS_AVAILABLE, PREFIX_NIGHT_PARK, PREFIX_SHORT_TERM,
                PREFIX_TOTAL_LOTS, PREFIX_TYPE_PARK, PREFIX_TAG)
=======
                ArgumentTokenizer.tokenize(args, PREFIX_CARPARK_NO, PREFIX_LOTS_AVAILABLE, PREFIX_LOT_TYPE, PREFIX_TOTAL_LOTS, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_CARPARK_NO, PREFIX_LOTS_AVAILABLE, PREFIX_LOT_TYPE, PREFIX_TOTAL_LOTS, PREFIX_ADDRESS)
>>>>>>> master
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

<<<<<<< HEAD
=======
//        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
//        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
//        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        CarparkNumber carparknumber = ParserUtil.parseCarparkNumber(argMultimap.getValue(PREFIX_CARPARK_NO).get());
        LotsAvailable lotsavailable = ParserUtil.parseLotsAvailable(argMultimap.getValue(PREFIX_LOTS_AVAILABLE).get());
        LotType lottype = ParserUtil.parseLotsType(argMultimap.getValue(PREFIX_LOT_TYPE).get());
        TotalLots totallots = ParserUtil.parseTotalLots(argMultimap.getValue(PREFIX_TOTAL_LOTS).get());
>>>>>>> master
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        CarparkNumber carparkNumber = ParserUtil.parseCarparkNumber(argMultimap.getValue(PREFIX_CAR_NUM).get());
        CarparkType carparkType = ParserUtil.parseCarparkType(argMultimap.getValue(PREFIX_CAR_NUM).get());
        Coordinate coordinate = ParserUtil.parseCoordinate(argMultimap.getValue(PREFIX_COORD).get());
        FreeParking freeParking = ParserUtil.parseFreeParking(argMultimap.getValue(PREFIX_FREE_PARK).get());
        LotsAvailable lotsAvailable = ParserUtil.parseLotsAvailable(argMultimap.getValue(PREFIX_LOTS_AVAILABLE).get());
        NightParking nightParking = ParserUtil.parseNightParking(argMultimap.getValue(PREFIX_NIGHT_PARK).get());
        ShortTerm shortTerm = ParserUtil.parseShortTerm(argMultimap.getValue(PREFIX_SHORT_TERM).get());
        TotalLots totalLots = ParserUtil.parseTotalLots(argMultimap.getValue(PREFIX_TOTAL_LOTS).get());
        TypeOfParking typeOfParking = ParserUtil.parseTypeOfParking(argMultimap.getValue(PREFIX_TYPE_PARK).get());

<<<<<<< HEAD
        Set<Tag> tagList = ParserUtil.parseTags(
                argMultimap.getAllValues(PREFIX_TAG));
=======
        Carpark person = new Carpark(carparknumber, totallots, lotsavailable, lottype, address, tagList);
>>>>>>> master

        Carpark carpark = new Carpark(address, carparkNumber, carparkType, coordinate, freeParking,
                lotsAvailable, nightParking, shortTerm, totalLots, typeOfParking, tagList);

        return new AddCommand(carpark);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
