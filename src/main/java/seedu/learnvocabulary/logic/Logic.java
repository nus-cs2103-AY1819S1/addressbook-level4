package seedu.learnvocabulary.logic;

import java.io.IOException;

import javafx.collections.ObservableList;
import seedu.learnvocabulary.logic.commands.CommandResult;
import seedu.learnvocabulary.logic.commands.exceptions.CommandException;
import seedu.learnvocabulary.logic.parser.exceptions.ParseException;
import seedu.learnvocabulary.model.word.Word;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException, IOException;

    /** Returns an unmodifiable view of the filtered list of words */
    ObservableList<Word> getFilteredWordList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
