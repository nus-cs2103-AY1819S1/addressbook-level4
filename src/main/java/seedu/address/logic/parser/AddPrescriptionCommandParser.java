package seedu.address.logic.parser;

import seedu.address.logic.commands.AddPrescriptionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Prescription;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRUG;

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
    @Override
    public AddPrescriptionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DRUG, PREFIX_AMOUNT, PREFIX_COUNT);

        if (!arePrefixesPresent(argMultimap, PREFIX_DRUG, PREFIX_AMOUNT, PREFIX_COUNT)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPrescriptionCommand.MESSAGE_USAGE));
        }

        String drug = argMultimap.getValue(PREFIX_DRUG).get();
        int amount = Integer.parseInt(argMultimap.getValue(PREFIX_AMOUNT).get());
        int count = Integer.parseInt(argMultimap.getValue(PREFIX_COUNT).get());

        return new AddPrescriptionCommand(drug, amount,count);

    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
