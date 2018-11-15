package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_FOCUS_AREA;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MAJOR;

import java.util.HashSet;
import java.util.Set;

import seedu.planner.commons.util.StringUtil;
import seedu.planner.logic.commands.SetUpCommand;
import seedu.planner.logic.parser.exceptions.ParseException;

/**
 * A parser that parses input arguments and creates a SetUpCommand object.
 */
public class SetUpCommandParser implements Parser<SetUpCommand> {

    @Override
    public SetUpCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_MAJOR, PREFIX_FOCUS_AREA);

        if (!argMultimap.containsAllPrefixes(PREFIX_MAJOR) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetUpCommand.MESSAGE_USAGE));
        }

        String major = ParserUtil.parseMajor(argMultimap.getValue(PREFIX_MAJOR).get());
        Set<String> focusAreas = ParserUtil.parseFocusAreas(argMultimap.getAllValues(PREFIX_FOCUS_AREA));

        String formattedMajor = StringUtil.capitalizeSentence(major);
        Set<String> formattedFocusAreas = formatFocusAreas(focusAreas);

        return new SetUpCommand(formattedMajor, formattedFocusAreas);
    }

    /**
     * Capitalizes the first letter of each word in each focus area in {@code focusAreas}.
     *
     * @param focusAreas The focus areas
     * @return Formatted focus areas
     */
    private static Set<String> formatFocusAreas(Set<String> focusAreas) {
        Set<String> formattedFocusAreas = new HashSet<>();

        for (String focusArea : focusAreas) {
            formattedFocusAreas.add(StringUtil.capitalizeSentence(focusArea));
        }

        return formattedFocusAreas;
    }
}
