package seedu.superta.commons.events.model;

import seedu.superta.commons.events.BaseEvent;
import seedu.superta.model.ReadOnlySuperTaClient;

/** Indicates the SuperTA client in the model has changed*/
public class SuperTaClientChangedEvent extends BaseEvent {

    public final ReadOnlySuperTaClient data;

    public SuperTaClientChangedEvent(ReadOnlySuperTaClient data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getStudentList().size();
    }
}
