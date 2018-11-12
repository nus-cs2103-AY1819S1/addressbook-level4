package seedu.lostandfound.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.lostandfound.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.lostandfound.logic.parser.CliSyntax.PREFIX_OWNER;

import seedu.lostandfound.commons.core.index.Index;
import seedu.lostandfound.logic.commands.ResolveCommand;
import seedu.lostandfound.logic.parser.exceptions.ParseException;
import seedu.lostandfound.model.article.Name;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class ResolveCommandParser implements Parser<ResolveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ResolveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_OWNER);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResolveCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_OWNER).isPresent()) {
            throw new ParseException(ResolveCommand.MESSAGE_NOT_RESOLVED);
        }

        Name owner = ParserUtil.parseName(argMultimap.getValue(PREFIX_OWNER).get());
        return new ResolveCommand(index, owner);
    }

}
