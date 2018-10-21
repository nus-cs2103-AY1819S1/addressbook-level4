package seedu.address.logic.parser;

import seedu.address.logic.commands.CommandToDo;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser that is able to parse user input into a {@code CommandToDo} of type {@code T}.
 */
public interface ParserToDo<T extends CommandToDo> {
    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    T parse(String userInput) throws ParseException;
}
