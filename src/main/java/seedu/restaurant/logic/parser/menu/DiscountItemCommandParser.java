package seedu.restaurant.logic.parser.menu;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_ENDING_INDEX;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_PERCENT;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.util.StringUtil;
import seedu.restaurant.logic.commands.menu.DiscountItemCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ArgumentMultimap;
import seedu.restaurant.logic.parser.util.ArgumentTokenizer;
import seedu.restaurant.logic.parser.util.ParserUtil;

//@@author yican95
/**
 * Parses input arguments and creates a new DiscountItemCommand object
 */
public class DiscountItemCommandParser implements Parser<DiscountItemCommand> {
    public static final String ALL_VALIDATION = "ALL";

    private boolean isAll = false;

    /**
     * Parses the given {@code String} of arguments in the context of the DiscountItemCommand
     * and returns an DiscountItemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DiscountItemCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ENDING_INDEX, PREFIX_PERCENT);

        Index index = Index.fromZeroBased(0); //Dummy value
        if (StringUtil.isNonZeroUnsignedInteger(argMultimap.getPreamble())) {
            index = Index.fromOneBased(Integer.parseInt(argMultimap.getPreamble()));
        } else if (isValidAll(argMultimap.getPreamble())) {
            isAll = true;
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DiscountItemCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getValue(PREFIX_PERCENT).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DiscountItemCommand.MESSAGE_USAGE));
        }
        double percent = ItemParserUtil.parsePercent(argMultimap.getValue(PREFIX_PERCENT).get());



        Index endingIndex = index;
        if (argMultimap.getValue(PREFIX_ENDING_INDEX).isPresent()) {
            try {
                endingIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ENDING_INDEX).get());
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DiscountItemCommand.MESSAGE_USAGE), pe);
            }
        }

        if (endingIndex.getZeroBased() < index.getZeroBased()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DiscountItemCommand.MESSAGE_USAGE));
        }

        return new DiscountItemCommand(index, endingIndex, percent, isAll);
    }

    /**
     * Returns true if a given string is equals to "ALL".
     */
    private static boolean isValidAll(String test) {
        return test.equalsIgnoreCase(ALL_VALIDATION);
    }

}
