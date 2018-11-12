package seedu.saveit.logic.parser;

import static seedu.saveit.commons.util.StringUtil.arePrefixesPresent;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_SOLUTION_LINK;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_STATEMENT;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.stream.Collectors;

import seedu.saveit.commons.core.Messages;
import seedu.saveit.logic.commands.FindByTagCommand;
import seedu.saveit.logic.parser.exceptions.ParseException;
import seedu.saveit.model.issue.IssueHasTagsPredicate;
import seedu.saveit.model.issue.Tag;

/**
 * Parses input arguments and creates a new FindByTagCommand object
 */
public class FindByTagCommandParser implements Parser<FindByTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindByTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (arePrefixesPresent(args, PREFIX_STATEMENT, PREFIX_DESCRIPTION, PREFIX_SOLUTION_LINK, PREFIX_REMARK)) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindByTagCommand.MESSAGE_USAGE));
        }

        List<String> filteredKeywords = argMultimap.getAllValues(PREFIX_TAG).stream()
                .filter(keyword -> Tag.isValidTagName(keyword.trim()))
                .collect(Collectors.toList());

        if (filteredKeywords.size() == 0) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindByTagCommand.EMPTY_TAGS_ERROR_MESSAGE));
        }

        return new FindByTagCommand(new IssueHasTagsPredicate(filteredKeywords));
    }

}
