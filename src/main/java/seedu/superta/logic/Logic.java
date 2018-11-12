package seedu.superta.logic;

import javafx.collections.ObservableList;
import seedu.superta.logic.commands.CommandResult;
import seedu.superta.logic.commands.exceptions.CommandException;
import seedu.superta.logic.parser.exceptions.ParseException;
import seedu.superta.model.student.Student;
import seedu.superta.model.tutorialgroup.TutorialGroup;

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
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Student> getFilteredPersonList();

    /** Returns an unmodifiable view of tutorial groups */
    ObservableList<TutorialGroup> getTutorialGroupList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
