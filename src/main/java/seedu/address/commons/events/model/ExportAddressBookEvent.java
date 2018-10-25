package seedu.address.commons.events.model;

import java.nio.file.Path;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

//@@author kengwoon
public class ExportAddressBookEvent extends BaseEvent {

    public Path path;
    public ReadOnlyAddressBook addressBook;

    public ExportAddressBookEvent(ReadOnlyAddressBook addressBook, Path path) {
        this.addressBook = addressBook;
        this.path = path;
    }
    @Override
    public String toString() {
        return path.toString();
    }
}
