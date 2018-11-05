package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeletePrescriptionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.MedicineName;


/**
 * Parses input arguments and creates a new DeletePrescriptionCommand object
 */
public class DeletePrescriptionCommandParser implements Parser<DeletePrescriptionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeletePrescriptionCommand
     * and returns a DeleteAppointmentCommand object for execution
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePrescriptionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_MEDICINE_NAME);

        int id;

        try {
            id = ParserUtil.parseId(argMultiMap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePrescriptionCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultiMap, PREFIX_MEDICINE_NAME)
                || argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePrescriptionCommand.MESSAGE_USAGE));
        }

        MedicineName medicineName = ParserUtil.parseMedicineName(argMultiMap.getValue(PREFIX_MEDICINE_NAME).get());

        return new DeletePrescriptionCommand(id, medicineName);
    }


    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

