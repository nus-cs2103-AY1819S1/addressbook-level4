package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;


import java.util.stream.Stream;

/**
 * Parses input arguments and creates a new AddMedicalHistoryCommand object
 */
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddMedicalHistoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new AddMedicalHistoryCommand object
 */
public class AddMedicalHistoryCommandParser implements Parser<AddMedicalHistoryCommand> {

    public AddMedicalHistoryCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALLERGY, PREFIX_CONDITION);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddMedicalHistoryCommand.MESSAGE_USAGE), pe);
        }


        String allergy = argMultimap.getValue(PREFIX_ALLERGY).get();
        String condition = argMultimap.getValue(PREFIX_CONDITION).get();

        return new AddMedicalHistoryCommand(index, allergy, condition);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}
