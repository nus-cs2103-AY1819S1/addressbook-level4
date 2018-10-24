package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SOME;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.util.PersonNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindPersonCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPersonCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ALL, PREFIX_SOME, PREFIX_NONE);

        if (!argMultimap.areAnyPrefixesPresent(PREFIX_ALL, PREFIX_SOME, PREFIX_NONE)) {
            // the preamble will need to be changed when we add the functionality to find by group or meetings.
            // as the preamble will contain those parameters. //TODO
            return parseNoPrefixUsed(argMultimap);
        } else {
            return parsePrefixesUsed(argMultimap);
        }
    }

    /**
     * Parses the command when no prefix is used. It will search for results matching all keywords by default.
     * @param argMultimap the {@code ArgumentMultimap} containing the keywords.
     * @return a {@code FindPersonCommand} object for execution.
     */
    private FindCommand parseNoPrefixUsed(ArgumentMultimap argMultimap) throws ParseException {
        String preamble = argMultimap.getPreamble();
        return new FindPersonCommand(
            new PersonNameContainsKeywordsPredicate(
                Arrays.asList(preamble.split("\\s+")),
                Collections.emptyList(),
                Collections.emptyList()
            )
        );
    }

    /**
     * Parses the command when prefixes are used.
     * @param argMultimap the {@code ArgumentMultimap} containing the keywords.
     * @return a {@code FindPersonCommand} object for execution.
     */
    private FindCommand parsePrefixesUsed(ArgumentMultimap argMultimap) throws ParseException {
        List<String> allKeywords = parseStringKeywordsToList(argMultimap.getValue(PREFIX_ALL));
        List<String> someKeywords = parseStringKeywordsToList(argMultimap.getValue(PREFIX_SOME));
        List<String> noneKeywords = parseStringKeywordsToList(argMultimap.getValue(PREFIX_NONE));
        if (!argMultimap.getPreamble().isEmpty()
            || (allKeywords.isEmpty() && someKeywords.isEmpty() && noneKeywords.isEmpty())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
        }
        return new FindPersonCommand(new PersonNameContainsKeywordsPredicate(allKeywords, someKeywords, noneKeywords));
    }

    /**
     * Parses an {@code Optional<String>} of keywords separated by whitespace into a list of strings.
     * @param keywords the keywords to be parsed.
     * @return a {@code List<String>} containing the keywords.
     */
    private static List<String> parseStringKeywordsToList(Optional<String> keywords) {
        return keywords.map(string -> Arrays.asList(string.split("\\s+")))
            .orElse(Collections.emptyList());
    }
}
