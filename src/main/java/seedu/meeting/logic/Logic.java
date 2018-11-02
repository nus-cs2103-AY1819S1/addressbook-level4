package seedu.meeting.logic;

import javafx.collections.ObservableList;
import seedu.meeting.logic.commands.CommandResult;
import seedu.meeting.logic.commands.exceptions.CommandException;
import seedu.meeting.logic.parser.exceptions.ParseException;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.meeting.Meeting;
import seedu.meeting.model.person.Person;

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
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of groups */
    ObservableList<Group> getFilteredGroupList();

    /** Returns an unmodifiable view of the sorted list of persons */
    ObservableList<Person> getSortedPersonList();

    /** Returns an unmodifiable view of the filtered list of meetings */
    ObservableList<Meeting> getFilteredMeetingList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
