package seedu.address.commons.events.model;

import java.nio.file.Path;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

/** Indicates the User Prefs have been changed */
public class UserPrefsChangeEvent extends BaseEvent {

    public final UserPrefs userPrefs;
    public final ReadOnlyAddressBook data;
    public final Path oldPath;
    public final Path newPath;

    public UserPrefsChangeEvent(UserPrefs userPrefs, ReadOnlyAddressBook data, Path oldPath, Path newPath) {
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
