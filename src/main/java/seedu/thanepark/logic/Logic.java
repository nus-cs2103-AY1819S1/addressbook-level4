package seedu.thanepark.logic;

import javafx.collections.ObservableList;
import seedu.thanepark.logic.commands.CommandResult;
import seedu.thanepark.logic.commands.exceptions.CommandException;
import seedu.thanepark.logic.parser.exceptions.ParseException;
import seedu.thanepark.model.ride.Ride;

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

    /** Returns an unmodifiable view of the filtered list of rides */
    ObservableList<Ride> getFilteredRideList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
