package seedu.meeting.model.meeting;

import static java.util.Objects.requireNonNull;
import static seedu.meeting.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.meeting.model.meeting.exceptions.MeetingNotFoundException;

// @@author jeffreyooi
/**
 * A list of meetings that does not allow nulls and is paired with groups that holds it.
 * A meeting is considered duplicate if there exist another meeting that belongs to the same owner and both have same
 * identity fields. The identities are compared by {@code Meeting#isSameMeeting(Meeting)}.
 *
 * Supports a minimal set of list operations.
 */
public class UniqueMeetingList implements Iterable<Meeting> {

    private final ObservableList<Meeting> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent meeting as the given argument.
     */
    public boolean contains(Meeting toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMeeting);
    }

    /**
     * Adds a meeting to the list.
     * The meeting can be already exist in the list but they belong to different groups.
     * TODO find a way to identify meetings of same fields but belong to different groups
     */
    public void add(Meeting toAdd) {
        requireAllNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the meeting {@code target} in the map with {@code editedMeeting}.
     * {@code target} must exist in the map.
     * The meeting identity of {@code editedMeeting} must not be the same as another existing meeting that has the same
     * owner.
     */
    public void setMeeting(Meeting target, Meeting editedMeeting) {
        int index = -1;
        if (target != null) {
            index = internalList.indexOf(target);
            internalList.remove(target);
        }

        if (editedMeeting != null) {
            if (index == -1) {
                internalList.add(editedMeeting);
            } else {
                internalList.add(index, editedMeeting);
            }
        }

        // restore ordering
        Collections.sort(internalList);
    }

    /**
     * Removes the equivalent meeting from the map.
     * The meeting must exist in the map.
     */
    public void remove(Meeting toRemove) {
        requireAllNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MeetingNotFoundException();
        }

        // restore ordering
        Collections.sort(internalList);
    }

    /**
     * Creates a list and return as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Meeting> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    public void setMeetings(List<Meeting> meetings) {
        requireNonNull(meetings);
        internalList.setAll(meetings);

        // restore ordering
        Collections.sort(internalList);
    }

    @Override
    public Iterator<Meeting> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueMeetingList // instanceof handles nulls
                && internalList.equals(((UniqueMeetingList) other).internalList));
    }
}
