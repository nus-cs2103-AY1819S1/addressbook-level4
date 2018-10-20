package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyClinicIo;

/** 
 * Indicates the ClinicIO in the model has changed
 */
public class ClinicIoChangedEvent extends BaseEvent {

    public final ReadOnlyClinicIo data;

    public ClinicIoChangedEvent(ReadOnlyClinicIo data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size();
    }
}
