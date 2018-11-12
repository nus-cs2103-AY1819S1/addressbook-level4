package seedu.address.logic.parser;

import seedu.address.logic.commands.OpenEndedAnswerCommand;

/**
 * Parses in user's answer and create a new OpenEndedAnswerCommand to record the answer
 */
public class OpenEndedAnswerParser implements Parser<OpenEndedAnswerCommand> {

    public OpenEndedAnswerCommand parse(String userInput) {
        return new OpenEndedAnswerCommand(userInput.trim());
    }

}
