package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.TriviaAnsCommand;
import seedu.address.model.person.Meaning;
import seedu.address.logic.parser.exceptions.ParseException;

public class TriviaAnsCommandParser implements Parser<TriviaAnsCommand> {


    public TriviaAnsCommand parse(String args) throws ParseException {
        try {
            Meaning meaning = ParserUtil.parseMeaning(args);
            return new TriviaAnsCommand(meaning.toString());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TriviaAnsCommand.MESSAGE_USAGE), pe);
        }
    }
}
