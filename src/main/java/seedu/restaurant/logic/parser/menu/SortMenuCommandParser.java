package seedu.restaurant.logic.parser.menu;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.restaurant.logic.commands.menu.SortMenuCommand;
import seedu.restaurant.logic.commands.menu.SortMenuCommand.SortMethod;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;

//@@author yican95
/**
 * Parses input arguments and creates a new SortMenuCommand object
 */
public class SortMenuCommandParser implements Parser<SortMenuCommand> {

    private static final String SORT_METHOD_NAME = "NAME";
    private static final String SORT_METHOD_PRICE = "PRICE";

    /**
     * Parses the given {@code String} of arguments in the context of the SortMenuCommand
     * and returns an SortMenuCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortMenuCommand parse(String args) throws ParseException {
        requireNonNull(args);
        SortMethod sortMethod = parseSortMethod(args);
        return new SortMenuCommand(sortMethod);
    }

    /**
     * Parse the user input and return a SortMethod Enum.
     * @param userInput
     * @throws ParseException if the user input is not valid
     */
    private SortMethod parseSortMethod(String userInput) throws ParseException {
        String input = userInput.toUpperCase().trim();
        switch (input) {
        case SORT_METHOD_NAME:
            return SortMethod.NAME;
        case SORT_METHOD_PRICE:
            return SortMethod.PRICE;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortMenuCommand.MESSAGE_USAGE));
        }
    }


}
