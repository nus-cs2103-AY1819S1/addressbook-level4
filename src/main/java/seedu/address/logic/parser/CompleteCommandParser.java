package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LABEL;

import java.util.Arrays;
import java.util.List;

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
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CompleteCommand parse(String args) throws ParseException {
        // Split up symbols into tokens delimited by whitespace
        List<String> tokens = Arrays.asList(args.split("\\s+"));
        // Take note,
        // ArgumentTokenizer requires one whitespace before the argument otherwise it would fail in detecting prefixes
        // Spent some time debugging this when args.trim() was used to remove whitespaces
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_LABEL);

        boolean isLabelSymbol = argMultimap.getValue(PREFIX_LABEL).isPresent();
        boolean isSingleIndexSymbol = isSingleIndexSymbol(tokens);


        // CompleteCommand does not support label and index based symbols in the same command
        if (isLabelSymbol && !isSingleIndexSymbol) {
            return parseLabel(argMultimap);
        } else if (isSingleIndexSymbol && !isLabelSymbol) {
            // No need to filter for a single token since it has been guaranteed to only hold one Index symbol
            return parseIndex(args);
        } else {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Checks if the list of tokens only has a single occurence of an index symbol
     * Returns true if yes, else false.
     */
    private boolean isSingleIndexSymbol(List<String> tokens) {
        int numberOfMatches = 0;
        for (String token : tokens) {
            if (StringUtil.isNonZeroUnsignedInteger(token)) {
                numberOfMatches++;
            }
        }
        return numberOfMatches == 1;
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
     *
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
