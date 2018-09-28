package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MINIMUM_STOCK_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE_PER_UNIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STOCK;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddMedicineCommand;
import seedu.address.logic.parser.exceptions.ParseException;


public class AddMedicineCommandParser implements Parser<AddMedicineCommand> {
    @Override
    public AddMedicineCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MEDICINE_NAME, PREFIX_MINIMUM_STOCK_QUANTITY,
                        PREFIX_PRICE_PER_UNIT, PREFIX_SERIAL_NUMBER, PREFIX_STOCK);

        if (!arePrefixesPresent(argMultimap, PREFIX_MEDICINE_NAME, PREFIX_MINIMUM_STOCK_QUANTITY,
                PREFIX_PRICE_PER_UNIT, PREFIX_SERIAL_NUMBER, PREFIX_STOCK)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedicineCommand.MESSAGE_USAGE));
        }

        MedicineName name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        IcNumber icNumber = ParserUtil.parseIcNumber(argMultimap.getValue(PREFIX_ICNUMBER).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Patient patient = new Patient(name, icNumber, phone, email, address, tagList);

        return new AddCommand(patient);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
