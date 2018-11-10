package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PersonModuleCodeContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindPersonCommand object
 */
public class FindPersonCommandParser implements Parser<FindPersonCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindPersonCommand
     * and returns a new FindPersonCommand object of the given parameters.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPersonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_MODULECODE);
        if (!anyPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_MODULECODE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String[] nameKeyWords = argMultimap.getValue(PREFIX_NAME).get().trim().split("\\s+");
            return new FindPersonCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeyWords)));
        } else if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String[] phoneKeyWords = argMultimap.getValue(PREFIX_PHONE).get().trim().split("\\s+");
            return new FindPersonCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(phoneKeyWords)));
        } else if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String[] emailKeyWords = argMultimap.getValue(PREFIX_EMAIL).get().trim().split("\\s+");
            return new FindPersonCommand(new EmailContainsKeywordsPredicate(Arrays.asList(emailKeyWords)));
        } else if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String[] addressKeyWords = argMultimap.getValue(PREFIX_ADDRESS).get().trim().split("\\s+");
            return new FindPersonCommand(new AddressContainsKeywordsPredicate(Arrays.asList(addressKeyWords)));
        } else {
            String[] moduleCodeKeyWords = argMultimap.getValue(PREFIX_MODULECODE).get().trim().split("\\s+");
            return new FindPersonCommand(
                    new PersonModuleCodeContainsKeywordsPredicate(Arrays.asList(moduleCodeKeyWords)));
        }

    }

    /**
     * Returns true if at least one of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
