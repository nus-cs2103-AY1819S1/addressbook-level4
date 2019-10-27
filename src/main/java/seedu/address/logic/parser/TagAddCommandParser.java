package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.Category;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddBulletCommand object
 */
public class TagAddCommandParser implements Parser<TagAddCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the TagAddCommand
     * and returns an TagAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagAddCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (args.trim().equals("help")) {
            throw new ParseException(TagAddCommand.MESSAGE_USAGE);
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_TAG);

        Index index;
        Set<Tag> tags;
        Category category = null;

        try {
            index = ParserUtil.parseIndex(args.trim().split(" ")[0]);
            tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
                category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAddCommand.MESSAGE_USAGE), pe);
        }

        return new TagAddCommand(index, category, tags);
    }
}


