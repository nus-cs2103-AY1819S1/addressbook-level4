package seedu.meeting.commons.events.model;

import java.nio.file.Path;

import seedu.meeting.commons.events.BaseEvent;
import seedu.meeting.model.ReadOnlyMeetingBook;
import seedu.meeting.model.UserPrefs;

/** Indicates the User Prefs have been changed */
public class UserPrefsChangeEvent extends BaseEvent {

    public final UserPrefs userPrefs;
    public final ReadOnlyMeetingBook data;
    public final Path oldPath;
    public final Path newPath;

    public UserPrefsChangeEvent(UserPrefs userPrefs, ReadOnlyMeetingBook data, Path oldPath, Path newPath) {
        this.userPrefs = userPrefs;
        this.data = data;
        this.oldPath = oldPath;
        this.newPath = newPath;
    }

    @Override
    public String toString() {
        return "Change path: " + newPath;
    }
}
