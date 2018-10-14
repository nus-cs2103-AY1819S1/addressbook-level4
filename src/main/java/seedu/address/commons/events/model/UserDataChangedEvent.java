package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.UserData;

/** Indicates the UserData in the model has changed*/
public class UserDataChangedEvent extends BaseEvent {

    public final UserData data;

    public UserDataChangedEvent(UserData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of users " + data.getUsernameUserHashMap().size();
    }
}
