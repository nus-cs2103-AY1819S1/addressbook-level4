package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.PersonContainsTagPredicate;

//@@author A19Sean
/**
 * Parses input arguments and creates a new TagCommand object
 */
public class TagCommandParser implements Parser<TagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns a TagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] tagKeywords = trimmedArgs.split("\\s+");
        if (trimmedArgs.isEmpty() || (tagKeywords[0].equalsIgnoreCase("edit") && tagKeywords.length != 3)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        TagCommand.Action action;
        if (tagKeywords[tagKeywords.length - 1].equalsIgnoreCase("delete")) {
            action = TagCommand.Action.DELETE;
        } else if (tagKeywords[0].equalsIgnoreCase("edit")) {
            action = TagCommand.Action.EDIT;
        } else {
            action = TagCommand.Action.FIND;
        }

        if (action == TagCommand.Action.DELETE || action == TagCommand.Action.FIND) {
            return new TagCommand(new PersonContainsTagPredicate(Arrays.asList(tagKeywords)), action,
                    Arrays.asList(tagKeywords));
        } else {
            return new TagCommand(new PersonContainsTagPredicate(Arrays.asList(tagKeywords[1])), action,
                    Arrays.asList(tagKeywords));
        }
    }

}
