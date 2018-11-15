package seedu.planner.logic;

import javafx.collections.ObservableList;
import seedu.planner.logic.commands.CommandResult;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.module.Module;

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

    /** Returns an unmodifiable view of the list of taken modules for a specific index*/
    ObservableList<Module> getTakenModulesForIndex(int index);

    /** Returns an unmodifiable view of the list of available modules */
    ObservableList<Module> getAvailableModules();

    /** Returns an unmodifiable view of the list of taken modules */
    ObservableList<Module> getTakenModules();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
