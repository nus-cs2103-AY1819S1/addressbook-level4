package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LABEL;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.CompleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.LabelMatchesKeywordPredicate;

/**
 * Parses input arguments and creates a new CompleteCommand object
 */
public class CompleteCommandParser implements Parser<CompleteCommand> {

    /**
     * Parses the given {@code args} and decides if {@code args} contains {@code Index} symbols
     * or {@code Label} symbols, then returning an appropriate CompleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CompleteCommand parse(String args) throws ParseException {
        // Sanitize arguments so that boolean checks don't trip on whitespaces
        String sanitizedArgs = args.trim();

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(sanitizedArgs, PREFIX_LABEL);
        boolean isLabelSymbol = argMultimap.getValue(PREFIX_LABEL).isPresent();
        boolean isIndexSymbol = StringUtil.isNonZeroUnsignedInteger(sanitizedArgs);

        // CompleteCommand does not support label and index based symbols in the same command
        if (isLabelSymbol && !isIndexSymbol) {
            return parseLabel(argMultimap);
        } else if (isIndexSymbol && !isLabelSymbol) {
            return parseIndex(sanitizedArgs);
        } else {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }
    }


    /**
     * Parses the given {@code String} of arguments in the context of a single {@code Index}
     * based CompleteCommand and returns an CompleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    private CompleteCommand parseIndex(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CompleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE), pe);
        }
    }


    /**
     * Precondition: argMultiMaps contains args mapped to PREFIX_LABEL
     * Parses the given {@code String} of arguments in the context of the batch-based {@code Task<Predicate>}
     * consuming CompleteCommand and returns an CompleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    private CompleteCommand parseLabel(ArgumentMultimap argMultiMap) {
        String labelArg = argMultiMap.getValue(PREFIX_LABEL).get();

        return new CompleteCommand(new LabelMatchesKeywordPredicate(labelArg));
    }

}
