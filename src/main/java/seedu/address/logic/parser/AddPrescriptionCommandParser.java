package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSUMPTION_PER_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddPrescriptionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.ConsumptionPerDay;
import seedu.address.model.appointment.Dosage;
import seedu.address.model.appointment.MedicineName;
import seedu.address.model.appointment.Prescription;

/**
 * Parses input arguments and creates a new AddPrescriptionCommand object
 */
public class AddPrescriptionCommandParser implements Parser<AddPrescriptionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPrescriptionCommand
     * and returns an AddPrescriptionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPrescriptionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MEDICINE_NAME, PREFIX_DOSAGE,
                        PREFIX_CONSUMPTION_PER_DAY);

        int id;

        try {
            id = ParserUtil.parseId(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPrescriptionCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_MEDICINE_NAME, PREFIX_DOSAGE,
                PREFIX_CONSUMPTION_PER_DAY) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPrescriptionCommand.MESSAGE_USAGE));
        }

        MedicineName medicineName = ParserUtil.parseMedicineName(argMultimap.getValue(PREFIX_MEDICINE_NAME).get());
        Dosage dosage = ParserUtil.parseDosage(argMultimap.getValue(PREFIX_DOSAGE).get());
        ConsumptionPerDay consumptionPerDay = ParserUtil.parseConsumptionPerDay(
                argMultimap.getValue(PREFIX_CONSUMPTION_PER_DAY).get());

        Prescription prescription = new Prescription(id, medicineName, dosage, consumptionPerDay);

        return new AddPrescriptionCommand(id, prescription);

    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
