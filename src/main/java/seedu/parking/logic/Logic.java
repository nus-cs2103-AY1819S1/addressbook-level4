package seedu.parking.logic;

import javafx.collections.ObservableList;
import seedu.parking.logic.commands.CommandResult;
import seedu.parking.logic.commands.exceptions.CommandException;
import seedu.parking.logic.parser.exceptions.ParseException;
import seedu.parking.model.carpark.Carpark;

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

    /** Returns an unmodifiable view of the filtered list of car parks */
    ObservableList<Carpark> getFilteredCarparkList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
