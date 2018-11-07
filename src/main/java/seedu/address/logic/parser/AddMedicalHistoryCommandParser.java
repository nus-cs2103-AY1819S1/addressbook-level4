package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddMedicalHistoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;


/**
 * Parses input arguments and creates a new AddMedicalHistoryCommand object
 */
public class AddMedicalHistoryCommandParser implements Parser<AddMedicalHistoryCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddMedicalHistoryCommand
     * and returns an AddMedicalHistoryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMedicalHistoryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALLERGY, PREFIX_CONDITION, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddMedicalHistoryCommand.MESSAGE_USAGE));
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_ALLERGY) && !arePrefixesPresent(argMultimap, PREFIX_CONDITION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddMedicalHistoryCommand.MESSAGE_USAGE));
        }
        String nameStr = argMultimap.getValue(PREFIX_NAME).get();
        String allergy = "";
        String condition = "";
        if (arePrefixesPresent(argMultimap, PREFIX_ALLERGY)){
            allergy = argMultimap.getValue(PREFIX_ALLERGY).get();
        }
        if (arePrefixesPresent(argMultimap, PREFIX_CONDITION)){
            condition = argMultimap.getValue(PREFIX_CONDITION).get();
        }
        Name name = new Name(nameStr);

        return new AddMedicalHistoryCommand(name, allergy, condition);
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
