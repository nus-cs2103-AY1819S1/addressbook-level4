package seedu.address.logic.parser.deliveryman;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.deliveryman.DeliverymanCommand;
import seedu.address.logic.commands.deliveryman.DeliverymanFindCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deliveryman.DeliverymanNameContainsKeywordsPredicate;

/**
 * Parses the given {@code String} of arguments in the context of the DeliverymanFindCommand
 * and returns an DeliverymanFindCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class DeliverymanFindCommandParser implements Parser<DeliverymanCommand> {
    public static final String MESSAGE_EMPTY_NAME_FIELD = "n/ cannot be empty";

    /**
     * Parses the given {@code String} of arguments in the context of the DeliverymanFindCommand
     * and returns an DeliverymanFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeliverymanCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliverymanFindCommand.MESSAGE_USAGE));
        }

        String name = argMultimap.getValue(PREFIX_NAME).get().trim();

        if (isEmptyField(name)) {
            throw new ParseException(MESSAGE_EMPTY_NAME_FIELD);
        }

        return new DeliverymanFindCommand(new DeliverymanNameContainsKeywordsPredicate(name));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static boolean isEmptyField(String field) {
        return field.equals("");
    }
}
