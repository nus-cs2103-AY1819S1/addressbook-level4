package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SuggestionCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Give Suggestions.
 */
public class SuggestionCommandParser implements Parser<SuggestionCommand> {

    /**
     * SuggestionCommand
     * @param args
     * @return
     * @throws ParseException
     */
    public SuggestionCommand parse(String args) throws ParseException {


        String stringCommand = args.trim();
        if (stringCommand.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuggestionCommand.MESSAGE_USAGE));
        }
        return new SuggestionCommand(stringCommand);
    }
}
