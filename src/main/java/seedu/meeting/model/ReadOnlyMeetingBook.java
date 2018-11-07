package seedu.meeting.model;

import javafx.collections.ObservableList;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.meeting.Meeting;
import seedu.meeting.model.person.Person;

/**
 * Unmodifiable view of an MeetingBook
 */
public interface ReadOnlyMeetingBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the group list.
     * This list will not contain any duplicate groups.
     */
    ObservableList<Group> getGroupList();

    /**
     * Returns an unmodifiable view of the meeting list.
     */
    ObservableList<Meeting> getMeetingList();
}
