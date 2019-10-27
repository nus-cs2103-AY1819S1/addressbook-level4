package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.TagListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class TagListCommandParser implements Parser<TagListCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the TagListCommand
     * and returns an TagListCommand object for execution.
     * @throws ParseException for help
     */
    public TagListCommand parse(String args) throws ParseException {
        if (args.trim().equals("help")) {
            throw new ParseException(TagListCommand.MESSAGE_USAGE);
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_TAG);

        Optional<String> categoryArg = argMultimap.getValue(PREFIX_CATEGORY);

        String category = "";
        if (categoryArg.isPresent()) {
            category = ParserUtil.parseCategory(categoryArg.get()).cateName;
        }

        Set<Tag> tagSet = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        return new TagListCommand(category, tagSet.stream().map(tag -> tag.tagName).collect(Collectors.toList()));
    }
}
