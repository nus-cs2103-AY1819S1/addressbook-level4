package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SuggestionCommand;
import seedu.address.logic.commands.SuggestionCommandByIndex;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Give Suggestions.
 */
public class SuggestionCommandByIndexParser implements Parser<SuggestionCommandByIndex> {

    /**
     * SuggestionCommand
     * @param args
     * @return
     * @throws ParseException
     */
    public SuggestionCommandByIndex parse(String args) throws ParseException {


        String stringCommand = args.trim();
        int index = Integer.parseInt(stringCommand);
        if (index <= 0) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuggestionCommand.MESSAGE_USAGE));
        }
        if (stringCommand.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuggestionCommand.MESSAGE_USAGE));
        }
        return new SuggestionCommandByIndex(stringCommand);
    }
}
