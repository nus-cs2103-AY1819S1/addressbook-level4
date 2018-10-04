package seedu.address.logic.AnakinParser;

import seedu.address.logic.AnakinCommands.AnakinCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser that is able to parse user input into a {@code AnakinCommand} of type {@code T}.
 */
public interface AnakinParserInterface<T extends AnakinCommand> {

    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    T parse(String userInput) throws ParseException;
}
