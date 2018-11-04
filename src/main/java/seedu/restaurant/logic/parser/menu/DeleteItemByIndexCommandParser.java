package seedu.restaurant.logic.parser.menu;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_ENDING_INDEX;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.commands.menu.DeleteItemByIndexCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ArgumentMultimap;
import seedu.restaurant.logic.parser.util.ArgumentTokenizer;
import seedu.restaurant.logic.parser.util.ParserUtil;

//@@author yican95
/**
 * Parses input arguments and creates a new DeleteItemByIndexCommand object
 */
public class DeleteItemByIndexCommandParser implements Parser<DeleteItemByIndexCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteItemByIndexCommand
     * and returns an DeleteItemByIndexCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteItemByIndexCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ENDING_INDEX);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteItemByIndexCommand.MESSAGE_USAGE), pe);
        }

        Index endingIndex = index;
        if (argMultimap.getValue(PREFIX_ENDING_INDEX).isPresent()) {
            try {
                endingIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ENDING_INDEX).get());
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteItemByIndexCommand.MESSAGE_USAGE), pe);
            }
        }

        if (endingIndex.getZeroBased() < index.getZeroBased()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteItemByIndexCommand.MESSAGE_USAGE));
        }

        return new DeleteItemByIndexCommand(index, endingIndex);
    }

}
