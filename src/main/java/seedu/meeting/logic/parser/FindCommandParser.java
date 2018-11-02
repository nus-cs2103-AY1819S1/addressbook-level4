package seedu.meeting.logic.parser;

import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.meeting.logic.commands.FindGroupCommand.FIND_GROUP_PARAM;
import static seedu.meeting.logic.commands.FindGroupCommand.FIND_GROUP_PARAM_SHORT;
import static seedu.meeting.logic.commands.FindMeetingCommand.FIND_MEETING_PARAM;
import static seedu.meeting.logic.commands.FindMeetingCommand.FIND_MEETING_PARAM_SHORT;
import static seedu.meeting.logic.commands.FindPersonCommand.FIND_PERSON_PARAM;
import static seedu.meeting.logic.commands.FindPersonCommand.FIND_PERSON_PARAM_SHORT;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_ALL;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_NONE;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_SOME;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.meeting.logic.commands.FindCommand;
import seedu.meeting.logic.commands.FindGroupCommand;
import seedu.meeting.logic.commands.FindMeetingCommand;
import seedu.meeting.logic.commands.FindPersonCommand;
import seedu.meeting.logic.parser.exceptions.ParseException;
import seedu.meeting.model.group.util.GroupTitleContainsKeywordsPredicate;
import seedu.meeting.model.meeting.util.MeetingTitleContainsKeywordsPredicate;
import seedu.meeting.model.person.util.PersonNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindPersonCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    /**
     * The type of FindCommand to create for execution.
     */
    private enum FindCommandType { PERSON, GROUP, MEETING }

    private FindCommandType findCommandType;

    /**
     * Parses the given {@code String} of arguments in the context of the FindPersonCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String[] splitArgs = args.split("\\s*(?=\\s)", 2);
        determineType(splitArgs[0].trim());
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(splitArgs[1], PREFIX_ALL, PREFIX_SOME, PREFIX_NONE);

        if (!argMultimap.areAnyPrefixesPresent(PREFIX_ALL, PREFIX_SOME, PREFIX_NONE)) {
            return parseNoPrefixUsed(argMultimap);
        } else {
            return parsePrefixesUsed(argMultimap);
        }
    }

    /**
     * Determines the findCommandType of FindCommand required.
     * @param findTypeFromUserInput the input from the user that specifies the findCommandType of find command.
     * @throws ParseException when the input is not valid.
     */
    private void determineType(String findTypeFromUserInput) throws ParseException {
        switch (findTypeFromUserInput) {
        case FIND_PERSON_PARAM:
        case FIND_PERSON_PARAM_SHORT:
            findCommandType = FindCommandType.PERSON;
            break;

        case FIND_GROUP_PARAM:
        case FIND_GROUP_PARAM_SHORT:
            findCommandType = FindCommandType.GROUP;
            break;

        case FIND_MEETING_PARAM:
        case FIND_MEETING_PARAM_SHORT:
            findCommandType = FindCommandType.MEETING;
            break;

        default:
            throwParseException();
            break;
        }
    }

    /**
     * Throws a ParseException of this FindCommandParser.
     * @throws ParseException when this method is called.
     */
    private void throwParseException() throws ParseException {
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
    }

    /**
     * Parses the command when no prefix is used. It will search for results matching all keywords by default.
     * @param argMultimap the {@code ArgumentMultimap} containing the keywords.
     * @return a {@code FindPersonCommand} object for execution.
     */
    private FindCommand parseNoPrefixUsed(ArgumentMultimap argMultimap) throws ParseException {
        String preamble = argMultimap.getPreamble();
        if (preamble.isEmpty()) {
            throwParseException();
        }
        return createFindCommand(
                Arrays.asList(preamble.split("\\s+")), Collections.emptyList(), Collections.emptyList());
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
            throwParseException();
        }
        return createFindCommand(allKeywords, someKeywords, noneKeywords);
    }

    /**
     * Constructs a FindCommand from a given set of lists of keywords.
     * @param allKeywords the list of keywords of which results returned must match all keywords.
     * @param someKeywords the list of keywords of which results returned must match at least one of the keywords.
     * @param noneKeywords the list of keywords of which results returned must not match any of the keywords.
     * @return a Find command object for execution.
     * @throws ParseException when the findCommandType of the find command required is not yet specified.
     */
    private FindCommand createFindCommand(List<String> allKeywords, List<String> someKeywords,
                                          List<String> noneKeywords) throws ParseException {
        FindCommand findCommand = null;
        switch (findCommandType) {
        case PERSON:
            findCommand = new FindPersonCommand(new PersonNameContainsKeywordsPredicate(
                allKeywords, someKeywords, noneKeywords));
            break;

        case GROUP:
            findCommand = new FindGroupCommand(new GroupTitleContainsKeywordsPredicate(
                allKeywords, someKeywords, noneKeywords));
            break;

        case MEETING:
            findCommand = new FindMeetingCommand(new MeetingTitleContainsKeywordsPredicate(
                allKeywords, someKeywords, noneKeywords));
            break;

        default:
            throwParseException();
            break;
        }
        return findCommand;
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
