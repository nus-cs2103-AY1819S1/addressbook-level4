package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.parseWithMatcher;

import java.util.regex.Matcher;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new NoteCommand object
 */
public class NoteCommandParser implements Parser<NoteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code NoteCommand}
     * and returns a NoteCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public NoteCommand parse(String args) throws ParseException {

        // split first integer from rest, throws ParseException if first is not just an integer
        String regex = "(\\s)(\\d+)(\\s)(.*)";

        Matcher matcher;
        try {
            matcher = parseWithMatcher(regex, args);
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));
        }

        // Takes care of nulls
        Index index;
        try {
            index = ParserUtil.parseIndex(matcher.group(2));
        } catch (ParseException e) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));
        }
        String text = matcher.group(4);

        // text is non-empty
        assert(!text.equals(""));
        return new NoteCommand(index, text);
    }
}
