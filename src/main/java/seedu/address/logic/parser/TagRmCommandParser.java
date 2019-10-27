package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagRmCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new TagRmCommand object
 */
public class TagRmCommandParser implements Parser<TagRmCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the TagRmCommand
     * and returns an TagRmCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagRmCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (args.trim().equals("help")) {
            throw new ParseException(TagRmCommand.MESSAGE_USAGE);
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        Index index;
        Set<Tag> tags;

        try {
            index = ParserUtil.parseIndex(args.trim().split(" ")[0]);
            tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagRmCommand.MESSAGE_USAGE), pe);
        }

        return new TagRmCommand(index, tags);
    }
}


